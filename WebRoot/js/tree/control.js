
// 处理静态下拉框，使其提交的值是真实的值。
//-----------------------
var _activeElement=null;
var _activeEditor=null;
var _activeTable=null;
var _dropdown_window=null;

var _app_id="", _page_id="";

var _document_loading=false;
var _document_loaded = false;
var _stored_element=null;
var _array_dataset=new Array();
var _tabpage_list=new Array();

var _skip_activeChanged=false;

/**
 * 得到操作系统信息，如Win32
 */
function getPlatform(){
	return window.clientInformation.platform;
}

/**
 * 得到IE版本，如6.0
 */
function getIEVersion(){
	var index=window.clientInformation.userAgent.indexOf("MSIE");
	if (index<0){
		return "";
	}
	else{
		return window.clientInformation.userAgent.substring(index+5, index+8);
	}
}

/**
 * 根据table里的td元素得到tr元素
 * @param cell td元素
 * @return tr元素
 */
function getRowByCell(cell){
	return cell.parentElement;
}

/**
 * 根据table里的td元素得到table元素
 * @param cell td元素
 * @return table元素
 */
function getTableByCell(cell){
	var tbody=getRowByCell(cell).parentElement;
	if (tbody) return tbody.parentElement;
}

/**
 * 根据table里的tr元素得到table元素
 * @param cell tr元素
 * @return table元素
 */
function getTableByRow(row){
	var tbody=row.parentElement;
	if (tbody) return tbody.parentElement;
}

function getElementEventName(element, eventName){
	var result="";
	if (element.attrib!="dockeditor")
		result=element.id+"_"+eventName;
	else{
		var holder=element.editorHolder;
		if (holder) result=holder.id+"_"+eventName;
	}
	return result;
}

/**
 * 判断一变量是否为用户自定义事件
 * @param function_name 待判断的变量
 * @return true or false
 */
function isUserEventDefined(function_name){
	if (function_name=="") return false;
	var result;
	eval("result=(typeof("+function_name+")!=\"undefined\");");
	return result;
}

/**
 * 触发用户自定义事件
 * @param function_name  事件函数
 * @param param          参数数组
 */
function fireUserEvent(function_name, param){
	var result;
	var paramstr="";
	for(i=0; i<param.length; i++) {
		if (i==0) paramstr="param["+i+"]";
		else paramstr=paramstr+",param["+i+"]";
	}
	if (isUserEventDefined(function_name)) eval("result="+function_name+"("+paramstr+");");
	return result;
}

/**
 * do something when the active element changed.
 * @param activeElement 当前获得焦点的活动对象
 */
function processActiveElementChanged(activeElement){
	/** 内部函数 */
	function isChildofTable(obj) {
		var result=null;
		var tmpObj;

		if (obj.getAttribute("attrib")=="dockeditor")
			tmpObj=obj.editorHolder;
		else
			tmpObj=obj;

		if (tmpObj.getAttribute("attrib")=="tablecell") result=getTableByCell(tmpObj);
		return result;
	}
	
	/** 内部函数，设置当前active的输入框，同时对刚刚失去焦点的输入框执行清理工作 */
	function set_activeEditor(editor){
		// 如果当前active editor不是上次的
		if (_activeEditor!=editor){
			// 处理上一个元素失去焦点需要的操作
			if (_activeEditor){
				if (needUpdateEditor){					
					if (_activeEditor.window==window) {
						// 更新输入框
						updateEditorInput(_activeEditor);
					}
					else {
						_activeEditor.window.updateEditorInput(_activeEditor);
					}
				}
				// 焦点移开后，隐藏下拉框的按纽
				if (typeof(hideDropDownBtn)!="undefined") hideDropDownBtn();
				
				switch (_activeEditor.getAttribute("attrib")){
					case "editor":{
						// 设置为失去焦点后的风格
						_activeEditor.className="editor";
						if(_activeEditor.getAttribute("isTwin") != null){
							//setElementValue(_activeEditor,_activeEditor.value);
						}
						break;


					}
					case "dockeditor":{
						hideDockEditor(_activeEditor);
						break;
					}
				}
				_activeEditor.use_keyField=false;
				refreshElementValue(_activeEditor);


			}

			if (editor && !editor.readOnly){
				var field=getElementField(editor);
				if (editor.getAttribute("attrib")=="editor"){
					// 设置激活状态的输入框风格
					editor.className="active_editor";
					if (field){
						editor.dataType=field.dataType;
						editor.editorType=field.editorType;
					}
				}

				if (field) editor.maxLength=(field.size>0)?field.size:2147483647;
				if (getValidStr(editor.getAttribute("dropDown_mode"))=="" &&
					(editor.getAttribute("dataType")=="date" || editor.getAttribute("dataType")=="datetime"))
					editor.dropDown_mode="date";
				if (getValidStr(editor.getAttribute("dropDown_mode"))=="" &&
					(editor.getAttribute("dataType")=="yearmonth"))
					editor.dropDown_mode="yearmonth";
				// 设置是否只读（下拉框只能选择不能输入）
				editor.contentEditable=(!isTrue(editor.getAttribute("dropDown_fixed")));

				editor.use_keyField=true;
				refreshElementValue(editor);

				if (!isTrue(editor.getAttribute("dropDown_fixed")) && !compareText(editor.type, "checkbox")) editor.select();
				if (typeof(showDropDownBtn)!="undefined"){
					// 这里执行显示下拉按钮操作
					showDropDownBtn(editor);
					// 自动显示下拉列表
					if (isTrue(editor.getAttribute("autoDropDown"))) showDropDownBox(editor);
				}
			}
			// _activeEditor为当前的editor
			_activeEditor = editor;
		}
	}
	
	/** 
	 * 处理前一个元素失去焦点的操作
	 */
	function processElementBlur(){
		// activeElement:下一个获得焦点的元素
		// _activeElement:失去焦点的元素
		// 保证只alert错误信息一次
		var alerted = false;
		var doblur=(activeElement!=_activeEditor );
		//changed by jdh at 20040209
		// add judge for dropdown list,when activeElement is td,
		// ignore the validation
		if(activeElement.tagName=="TD"){
			if(activeElement.dropdown_cached) doblur=false;
		}
		//end changed 
		if (_activeElement){
			if (typeof(_dropdown_btn)!="undefined" && _dropdown_btn){
				doblur=doblur && (_activeElement!=_dropdown_btn) &&
					(activeElement!=_dropdown_btn);
			}

			if (typeof(_dropdown_box)!="undefined" && _dropdown_box){
				var editor=_dropdown_box.editor;
				doblur=doblur && (activeElement!=editor) &&
					(!isChild(activeElement, _dropdown_box));
			}

			if (doblur){
				if (_activeEditor && _activeEditor.dropDown_visible){
					if (typeof(hideDropDownBox)!="undefined") hideDropDownBox();
					hideStatusLabel(window);
				}				
				set_activeEditor(null);
			}
			// added by steve_gu at 2004-05-14 增加了控制对单个输入框失去焦点时是否进行验证
			if (validateConfig.validateOnBlur && validateConfig.validateOnBlur == true) {		
			// added by steve_gu at 2003-12-31
			// 非空验证
				if(doblur){
					if (_activeElement.getAttribute("noempty") != null && _activeElement.value.length==0) {
						//if (_activeElement.getAttribute("noempty") == "true") {
							alert(constErrNoEmpty);
							alerted = true;					
							_activeElement.focus();
							activeElement = _activeElement;		
					}
				}
				// added by steve_gu at 2004-01-06
				// 整数验证，虽然若为整型，在输入时已屏蔽掉了键盘输入，但为了防止通过粘贴的方法或中文输入，在失去焦点时还得进一步验证
				if (_activeElement.getAttribute("datatype") == "int") {
					var intValue = _activeElement.value;
					// modified by steve_gu at 2004-05-21,采用正则表达式中的模式匹配,替换”每一段字符串都能解析成整数“方法
					var pattern = /[^0-9]/;
					if (pattern.exec(intValue) != null) {
						if (alerted == false) {
							alert(constErrInt);
							alerted = true;
						}
						_activeElement.focus();
						activeElement = _activeElement;	
					}
					// end modify
				}	
				
				// 浮点型验证，虽然若为浮点型，在输入时已屏蔽掉了键盘输入，但为了防止通过粘贴的方法或中文输入，在失去焦点时还得进一步验证
				if (_activeElement.getAttribute("datatype") == "float") {
					var floatValue = _activeElement.value;
					// modified by steve_gu at 2004-05-21,采用正则表达式中的模式匹配,替换”每一段字符串都能解析成小数“方法
					var pattern = /[^01234569789.]/;
					if (pattern.exec(floatValue) != null) {
						if (alerted == false) {
							alert(constErrFloat);
							alerted = true;
						}
						_activeElement.focus();
						activeElement = _activeElement;	
					}
					// end modify					
				}	
				
				if (_activeElement.getAttribute("datatype") == "email") {
					var emailValue = _activeElement.value;
					if (emailValue.indexOf("@") == -1 || emailValue.indexOf("@") == 0 || emailValue.indexOf(".") == -1 || emailValue.lastIndexOf(".") == emailValue.length - 1 || emailValue.split("@").length > 2) {
						if (alerted == false) {
							alert(constErrEmail);
							alerted = true;
						}
						_activeElement.focus();
						activeElement = _activeElement;
					}
				}			
												
				// added by steve_gu at 2004-01-02
				// 若设了format属性,则二次开发人员format的设置必须为??.???或*.??或??.*格式
				if (_activeElement.getAttribute("datatype") =="float" && _activeElement.getAttribute("format")) {				
					// modified by steve_gu at 2004-04-22 固定格式的浮点型也可为空
					if (_activeElement.value != "") {
					
					var arrInputValue = _activeElement.value.split(".");				
					var floatFormat = _activeElement.getAttribute("format");
					var arrFloatFormat = floatFormat.split(".");
					// 多于一个"."						
					if (arrInputValue.length >= 3) {
						if (alerted == false) {
							alert(constErrFloatType);
							alerted = true;
						}
						_activeElement.focus();
						activeElement = _activeElement;					
					}
					// 如果小数位固定，自动补足或截取
					if (arrFloatFormat[1].substring(0,1) == "?") {
						// 没有小数位,自动补足
						if (arrInputValue.length == 1) {
							_activeElement.value += "." ;
							for (var i = 0; i <= arrFloatFormat[1].length - 1; i++) {
								_activeElement.value += "0";
							}
						}
						// 有小数位，但可能不全，自动补足；或过多，则截取
						if (arrInputValue.length == 2) {
							if ( arrInputValue[1].length < arrFloatFormat[1].length) {
								for (var i = 0; i < arrFloatFormat[1].length - arrInputValue[1].length;i++) {
									_activeElement.value += "0";
								}
							}
							if ( arrInputValue[1].length > arrFloatFormat[1].length) {
								var offset = arrInputValue[1].length - arrFloatFormat[1].length;
								_activeElement.value = _activeElement.value.substring(0, _activeElement.value.length - offset);
							}
						}
					}
					// 补足或截取以后再判断整数位和小数位为?还是*
					// 如果整数位和小数位格式均为???
					if (arrFloatFormat[0].substring(0,1) == "?" && arrFloatFormat[1].substring(0,1) == "?") {
						if (_activeElement.value.split(".")[0].length != arrFloatFormat[0].length || _activeElement.value.split(".")[1].length != arrFloatFormat[1].length) {
							var temp = constErrFloatFormat.replace("%intCount", arrFloatFormat[0].length);
							temp = temp.replace("%decimalCount", arrFloatFormat[1].length);
							if (alerted == false) {
								alert(temp);
								alerted = true;
							}
							_activeElement.focus();
							activeElement = _activeElement;						
						}
					}
					if (arrFloatFormat[0].substring(0,1) == "?" && arrFloatFormat[1].substring(0,1) == "*") {
						if (_activeElement.value.split(".")[0].length != arrFloatFormat[0].length) {
							var temp = constErrFloatFormat.replace("%intCount", arrFloatFormat[0].length);
							temp = temp.replace("%decimalCount", "不限");
							if (alerted == false) {
								alert(temp);
								alerted = true;
							}
							_activeElement.focus();
							activeElement = _activeElement;
						}
					}
					// 因为固定的小数位已补足或截取，所以不可能出现格式为（不固定的整数位，固定的小数位）而长度不满足的情况
					}				
				}// end of format			
				
				// 最小长度验证
				if (_activeElement.getAttribute("minlength")) {
					var minLength = _activeElement.getAttribute("minlength");
					if (_activeElement.value.length < minLength) {
						if (alerted == false) {
							alert(constErrMinlength.replace("%minLength",minLength));
							alerted = true;
						}
						_activeElement.focus();
						activeElement = _activeElement;						
					}
				}

				// 最大长度验证
				if (_activeElement.getAttribute("maxlength")) {
					var maxLength = _activeElement.getAttribute("maxlength");
					if (_activeElement.value.length > maxLength) {
						if (alerted == false) {
							alert(constErrMaxlength.replace("%maxLength",maxLength));
							alerted = true;
						}
						_activeElement.focus();
						activeElement = _activeElement;						
					}
				}

				// added by steve_gu at 2004-01-06
				// 日期时间验证
				if (_activeElement.getAttribute("datatype") == "date" && _activeElement.value != "" && _activeElement.value != null) {
					if (_activeElement.getAttribute("separator")) {				
						var tmpValue = _activeElement.value.replace(_activeElement.getAttribute("separator"), "/");
						tmpValue = tmpValue.replace(_activeElement.getAttribute("separator"),"/");
					}
					else {
						var tmpValue = _activeElement.value.replace("-", "/");
						tmpValue = tmpValue.replace("-","/");
					}
					var _date = new Date(tmpValue);
					if (isNaN(_date)) {
						if (alerted == false) {
							alert(constErrTypeDate.replace("%s", _activeElement.value));
							alerted = true;
						}
						_activeElement.focus();
						activeElement = _activeElement;					
					}
					// added by steve_gu at 2004-01-30，因为js中判断一个字符串是否为有效日期型很宽松，所以要限制日期输入的长度
					if (tmpValue.length != 0 && tmpValue.length > 10) {
						if (alerted == false) {
							alert(constErrTypeDate.replace("%s", _activeElement.value));
							alerted = true;
						}
						_activeElement.focus();
						activeElement = _activeElement;	
					}
				}
				if (_activeElement.getAttribute("datatype") == "datetime" && _activeElement.value != "" && _activeElement.value != null) {
					if (_activeElement.getAttribute("separator")) {				
						var tmpValue = _activeElement.value.replace(_activeElement.getAttribute("separator"), "/");
						tmpValue = tmpValue.replace(_activeElement.getAttribute("separator"),"/");
					}
					else {
						var tmpValue = _activeElement.value.replace("-", "/");
						tmpValue = tmpValue.replace("-","/");
					}
					//var tmpValue = _activeElement.value.replace("-", "/");
					//tmpValue = tmpValue.replace("-","/");				
					var _date = new Date(tmpValue);
					if (isNaN(_date)) {
						if (alerted == false) {
							alert(constErrTypeDateTime.replace("%s",_activeElement.value));
							alerted = true;
						}
						_activeElement.focus();
						activeElement = _activeElement;
					} 
					if (tmpValue.length != 0 && tmpValue.length > 19) {
						if (alerted == false) {
							alert(constErrTypeDate.replace("%s", _activeElement.value));
							alerted = true;
						}
						_activeElement.focus();
						activeElement = _activeElement;	
					}
				}
			}// end if (defaltvalue.validateOnBlur == true)
		}
		else{
			doblur=false;
		}

		if (activeElement==document.body && _skip_activeChanged){
			_skip_activeChanged=false;
			return;
		}

		if ((doblur || !_activeEditor)){
			var activeTable=isChildofTable(activeElement);
			if (_activeTable!=activeTable){
				if (_activeTable){
					_activeTable.focused=false;

					var row=_activeTable.activeRow;
					if (row) refreshTableRowStyle(row);

					var eventName=getElementEventName(_activeTable, "onBlur");
					fireUserEvent(eventName, [_activeTable]);
				}

				_activeTable=activeTable;

				if (_activeTable){
					_activeTable.focused=true;

					var row=_activeTable.activeRow;
					if (row) refreshTableRowStyle(row);

					var eventName=getElementEventName(_activeTable, "onFocus");
					fireUserEvent(eventName, [_activeTable]);
				}
			}
		}
		
	}
	
	// 最外层函数的真正执行处
	if (window.closed) return;
	// 如果焦点没有改变，不执行任何操作
	if (activeElement==_activeElement) return;

	if (activeElement){
		// 处理元素失去焦点的操作
		processElementBlur();

		switch (activeElement.getAttribute("attrib")){
			case "tablecell":{
				var row = getRowByCell(activeElement);
				var table = getTableByRow(row);
				var dataset = getElementDataset(activeElement);

				table._activeRow = row;
				table._activeCell = activeElement;
				table._activeCellIndex = activeElement.cellIndex;
				if (row.record){
					if (dataset.window==window)	_dataset_setRecord(dataset, row.record);
					else dataset.window._dataset_setRecord(dataset, row.record);
				}
				setActiveTableCell(row, activeElement.cellIndex);
				table._activeRow=null;
				break;
			}
			case "editor":; //注意没有break
			case "dockeditor":{
				set_activeEditor(activeElement);
				break;
			}
		}
	}
	_activeElement=activeElement;
}

/**
 * document中的属性改变触发的事件，这里主要是统一处理页面元素焦点转移的操作
 */
function _document_onpropertychange() {
	if (event.propertyName=="activeElement") {
		// document的内置属性的改变，表示焦点转移,该属性readonly,并不能由代码显式地改变，
		processActiveElementChanged(document.activeElement);
	}
}

/**
 * 页面按键事件
 */
function _document_onkeydown(){
	switch (event.keyCode){
		case 123:{
			if (event.altKey && event.ctrlKey && event.shiftKey){
				eval(window.prompt("调试", ""));
			}
			break;
		}
	}
}

/*
function _document_oncontextmenu(){
	event.returnValue=(!disableSystemContextMenu);
	if (typeof(_array_menu)=="undefined") return;
	for(var i=0; i<_array_menu.length; i++){
		var strHolders=_array_menu[i].popupHolders;
		if (getValidStr(strHolders)!=""){
			var arrayHolder=strHolders.split(",");
			for(var j=0; j<arrayHolder.length; j++){
				if (arrayHolder[j]=="") continue;
				var needPopup;
				eval("needPopup=isChild(event.srcElement,"+arrayHolder[j]+")");
				if (needPopup){
					showPopupMenu(_array_menu[i]);
					event.returnValue=false;
					return;
				}
			}
		}
	}
}
*/

/**
 * 得到前一个tab页
 * param obj
 */
function getPriorTabElement(obj){
	var i=obj.sourceIndex-1;
	var elementCount=document.all.length
	var tmpObj=null;
	while (i<elementCount){
		tmpObj=document.all[i];
		if (tmpObj!=obj)
		{
			switch (tmpObj.tagName.toLowerCase())
			{
			case "input":
			case "textarea":
			case "button":
				if (tmpObj.tabIndex!=-1 && !tmpObj.disabled && !tmpObj.readOnly)
				{
					return tmpObj;
				}
			case "td":
				if (tmpObj.attrib=="tablecell" && !tmpObj.readOnly)
				{
					return tmpObj;
				}
			}
		}
		i--;
	}
}

/**
 * 得到后一个tab页
 * param obj
 */
function getNextTabElement(obj){
	var i=obj.sourceIndex+1;
	var elementCount=document.all.length
	var tmpObj=null;
	while (i<elementCount){
		tmpObj=document.all[i];
		if (tmpObj!=obj)
		{
			switch (tmpObj.tagName.toLowerCase())
			{
			case "input":
			case "textarea":
			case "button":
				if (tmpObj.tabIndex!=-1 && !tmpObj.disabled && !tmpObj.readOnly)
				{
					return tmpObj;
				}
			case "td":
				if (tmpObj.attrib=="tablecell" && !tmpObj.readOnly)
				{
					return tmpObj;
				}
			}
		}
		i++;
	}
}

/**
 * 控制按键事件
 */
function _control_onkeydown() {
	//alert("keydown");
	function getCell(element){
		if (element.getAttribute("attrib")=="tablecell")
			return element;
		else if (element.in_table)
			return element.editorHolder;
	}

	function processTab(element){
		var obj=null;
		if (element.in_table){
			obj=element.editorHolder;
		}
		else{
			obj=element;
		}
		if (!obj) return;
		if (event.shiftKey)
			obj=getPriorTabElement(obj);
		else
			obj=getNextTabElement(obj);

		try
		{
			if (obj) obj.focus();
			event.returnValue=false;
		}
		catch (e)
		{
			// do nothing
		}		
	}

	element=event.srcElement;
	if (isDropdownBoxVisible()){
		if (_dropdown_window) _dropdown_window.processDropDownKeyDown(event.keyCode);
		event.returnValue=true;
	}
	else{
		var rowindex, colindex;
		switch (event.keyCode) {
			//Tab
			case 9:{
				processTab(element);				
				break;
			}
			//Enter
			case 13:{
				if (processEnterAsTab && !compareText(element.tagName, "textarea") || event.shiftKey || event.ctrlKey || event.altKey){
					var cell=getCell(element);
					if (cell && !event.shiftKey){
						var row=getRowByCell(cell);
						var table=getTableByRow(row);
						var maxIndex=checkTableCellIndex(table, 9999, 9999);
						if (row.rowIndex==maxIndex[0] && cell.cellIndex==maxIndex[1] && !isTrue(table.getAttribute("readOnly"))){
							var dataset=getElementDataset(element);
							dataset.insertRecord("end");
							dataset.modified=false;
							setActiveTableCell(table.activeRow, 0);
						}
						else{
							processTab(element);
						}
					}
					else{
						processTab(element);
					}
				}
				break;
			}
			//ESC
			case 27:{
				if (!element.modified){
					var dataset=getElementDataset(element);
					if (!dataset || dataset.state=="none") break;

					var cell=getCell(element);
					if (cell && !isTrue(getTableByCell(cell).getAttribute("readOnly"))){
						if (isTrue(getTableByCell(cell).getAttribute("confirmCancel"))){
							if (confirm(constDatasetConfirmCancel)){
								dataset.cancelRecord();
							}
						}
						else{
							dataset.cancelRecord();
						}
					}
				}
				else{
					setElementValue(element, element.old_value);
				}
				event.returnValue=false;
				break;
			}
			//Left
			case 37:{
				var cell=getCell(element);
				if (cell){
					if ((event.ctrlKey) || (event.altKey)){
						var table=getTableByCell(cell);
						var rowIndex=getRowByCell(cell).rowIndex;
						var cellIndex=cell.cellIndex;
						cellIndex--;
						setFocusTableCell(table, rowIndex, cellIndex);
						event.returnValue=false;
					}
				}
				break;
			}
			//Up
			case 38:{
				var cell=getCell(element);
				if (cell){
					var dataset=getElementDataset(element);
					if (dataset){
						dataset.movePrev();
						event.returnValue=false;
					}
				}
				else{
					switch (element.getAttribute("dropDown_mode")){
						case "list":{
							var fieldName=(element.getAttribute("dropDown_mapValue"))?"name":"value";
							var tempDataset=getDropDownItems(element);
							var record=tempDataset.locate(fieldName, element.value);
							if (record){
								tempDataset.setRecord(record);
								tempDataset.movePrev();
							}
							processDropDownSelected(element, tempDataset.record, true);
							event.returnValue=false;
							break;
						}

						case "dataset":{
							var tempDataset=element.getAttribute("dropDown_dataset");
							if (typeof(tempDataset)=="string") tempDataset=getDatasetByID(tempDataset);
							if (tempDataset) tempDataset.movePrev();
							processDropDownSelected(element, tempDataset.record, true);
							event.returnValue=false;
							break;
						}
					}
				}
				break;
			}
			//Right
			case 39:{
				var cell=getCell(element);
				if (cell){
					if ((event.ctrlKey) || (event.altKey)){
						var table=getTableByCell(cell);
						var rowIndex=getRowByCell(cell).rowIndex;
						var cellIndex=cell.cellIndex;
						cellIndex++;
						setFocusTableCell(table, rowIndex, cellIndex);
						event.returnValue=false;
					}
				}
				break;
			}
			//Down
			case 40:{
				if (event.altKey){
					showDropDownBox(element);
				}
				else{
					var cell=getCell(element);
					if (cell){
						var table=getTableByCell(cell);
						var dataset=getElementDataset(element);
						if (dataset){
							dataset.moveNext();
							if (dataset.eof && !isTrue(table.getAttribute("readOnly")) && !isTrue(dataset.readOnly)){
								dataset.insertRecord("end");
								dataset.modified=false;
							}
							event.returnValue=false;
						}
					}
					else{
						switch (element.getAttribute("dropDown_mode")){
							case "list":{
								var fieldName=(element.getAttribute("dropDown_mapValue"))?"name":"value";
								var tempDataset=getDropDownItems(element);
								var record=tempDataset.locate(fieldName, element.value);
								if (record){
									tempDataset.setRecord(record);
									tempDataset.moveNext();
								}
								processDropDownSelected(element, tempDataset.record, true);
								event.returnValue=false;
								break;
							}

							case "dataset":{
								var tempDataset=element.getAttribute("dropDown_dataset");
								if (typeof(tempDataset)=="string") tempDataset=getDatasetByID(tempDataset);
								if (tempDataset) tempDataset.moveNext();
								processDropDownSelected(element, tempDataset.record, true);
								event.returnValue=false;
								break;
							}
						}
					}
				}
				break;
			}
			//Insert
			case 45:{
				var cell=getCell(element);
				if (cell && !isTrue(getTableByCell(cell).getAttribute("readOnly"))){
					var dataset=getElementDataset(element);
					if (!isTrue(dataset.readOnly)){
						dataset.insertRecord("before");
						dataset.modified=false;
					}
				}
				break;
			}
			//Delete
			case 46:{
				var cell=getCell(element);
				if (cell && !isTrue(getTableByCell(cell).getAttribute("readOnly"))){
					if (event.ctrlKey){
						if (isTrue(getTableByCell(cell).getAttribute("confirmDelete"))){
							var dataset=getElementDataset(element);
							if (!isTrue(dataset.readOnly) && confirm(constDatasetConfirmDelete)){
								dataset.deleteRecord();
							}
						}
						else{
							var dataset=getElementDataset(element);
							dataset.deleteRecord();
						}
						event.returnValue=false;
					}
				}
				break;
			}
			//Home
			case 36:{
				var cell=getCell(element);
				if (cell){
					if ((event.ctrlKey) || (event.altKey)){
						var row=getRowByCell(cell);
						setActiveTableCell(row, 0);
						event.returnValue=false;
					}
				}
				break;
			}
			//End
			case 35:{
				var cell=getCell(element);
				if (cell){
					if ((event.ctrlKey) || (event.altKey)){
						var row=getRowByCell(cell);
						setActiveTableCell(row, 99999);
						event.returnValue=false;
					}
				}
				break;
			}
			//Page Up
			case 33:{
				var cell=getCell(element);
				if (cell && !isTrue(getTableByCell(cell).getAttribute("readOnly"))){
					var dataset=getElementDataset(element);
					var pageIndex=(dataset.record)?dataset.record.pageIndex-1:1;
					dataset.moveToPage(pageIndex);
				}
				break;
			}
			//Page Down
			case 34:{
				var cell=getCell(element);
				if (cell && !isTrue(getTableByCell(cell).getAttribute("readOnly"))){
					var dataset=getElementDataset(element);
					var pageIndex=(dataset.record)?dataset.record.pageIndex+1:1;
					dataset.moveToPage(pageIndex);
				}
				break;
			}
			//F2
			case 113:;
			//F7
			case 118:{
				showDropDownBox(element);
				break;
			}
		}
	}
}

/**
 * 得到绝对位置
 * param obj 
 * param offsetObj
 */
function getAbsPosition(obj, offsetObj){
	var _offsetObj=(offsetObj)?offsetObj:document.body;
	var x=obj.offsetLeft;
	var y=obj.offsetTop;
	var tmpObj=obj.offsetParent;
	//alert("tmpObj is "+tmpObj.tagName);
	while ((tmpObj!=_offsetObj) && tmpObj){
		x+=tmpObj.offsetLeft+tmpObj.clientLeft-tmpObj.scrollLeft;
		y+=tmpObj.offsetTop+tmpObj.clientTop-tmpObj.scrollTop;
		tmpObj=tmpObj.offsetParent;
		//alert("tmpObj is in while"+tmpObj.tagName);
	}
	return ([x, y]);
}

/**
 * 判断一个对象是否是另外一个对象孩子，不管是直接孩子，还是任意后代
 * @param obj 待判断的对象
 * @param parentObj 父对象
 * @param true or false
 */
function isChild(obj, parentObj) {
	var tmpObj = obj;
	var result = false;
	if (parentObj) {
		while (tmpObj) {
			if (tmpObj == parentObj){
				result = true;
				break;
			}
			tmpObj = tmpObj.parentElement;
		}
	}
	return result;
}

/**
 * 使页面中的所有元素失效，采用加个z-index属性很大的div覆盖其他元素，然后利用滤镜灰色透明显示下面的元素
 * 但<select>元素盖不住，另，window参数没有用到
 */
function disableDocument(window){
	if (typeof(_over_label)=="undefined"){
		document.body.insertAdjacentHTML("beforeEnd", "<div id=_over_label language=javascript"+
			" style=\"position: absolute; background-color: black; left:0; top:0; z-index: 9999; filter:alpha(opacity=30)\"></div>");
	}
	document.body._documentDisabled=true;
	_over_label.style.width=document.body.clientWidth + document.body.scrollLeft;
	_over_label.style.height=document.body.clientHeight + document.body.scrollTop;
	_over_label.style.visibility="visible";
}

/**
 * 使页面元素有效，是disableDocument(window)的逆操作
 */
function enableDocument(window){
	if(_over_label) _over_label.style.visibility="hidden";
	document.body._documentDisabled=false;
}

/** 
 * 判断页面是否有效
 */
function isDocumentEnable(){
	return (!isTrue(document.body._documentDisabled));
}

/**
 * 利用元素中的dataset属性，初始化元素的dataset
 * @param element 要初始化的元素
 */
function initElementDataset(element){
	var dataset=element.getAttribute("dataset");
	if (dataset) setElementDataset(element, dataset);
}

/**
 * init one element
 * @param element 要初始化的页面元素
 */
function initElement(element){
//	alert(element.value);
	var initChildren=true;
	var _attrib=element.getAttribute("attrib");

	if (_attrib){
		switch (_attrib){
			case "fieldlabel":{
				if (!element.className) element.className=_attrib;

				var dataset;
				var _dataset=element.getAttribute("dataset");
				if (typeof(_dataset)=="string"){
					dataset=getDatasetByID(_dataset);
				}
				else{
					dataset=_dataset;
				}
				element.dataset=dataset;
				refreshElementValue(element);
				initChildren=false;
				break;
			}
			case "columnheader":{
				if (!element.className) element.className=_attrib;
				element.noWrap=true;
				element.onclick=_table_head_onclick;
				element.onmouseover=_table_head_onmouseover;
				element.onmouseout=_table_head_onmouseout;
				refreshElementValue(element);
				initChildren=false;
				break;
			}
			case "columnfooter":{
				if (!element.className) element.className=_attrib;
				refreshElementValue(element);
				initChildren=false;
				break;
			}
			case "datalabel":{
				if (!element.className) element.className=_attrib;
				initElementDataset(element);
				initChildren=false;
				break;
			}
			// usally input control, such as text, date and datetime.
			// modified by steve_gu at 2004-01-02
			case "editor":{
				if(compareText(element.getAttribute("dropDown_mode"), "sql")){
				}
				/** commented by steve_gu at 2004-05-24 现在已不需要
				// changed by jdh  at 20040202 for init icon
				if(element.dropdown_mode){
					// 判断对于下拉框是否初始显示下拉图片
					if(_show_drop_down_btn=="true"){
						if (typeof(showDropDownBtn)!="undefined"){
							// 这里执行显示下拉按钮操作
							_showDropDownBtn(element);							
						}	
					}
				}
				// end changed
				*/
				
				//-----------------------
				// changed by jdh from ztp at 20040202
				// 处理静态下拉框，绑定一个隐藏域，并使用克隆技术克隆属性。
				if (element.getAttribute("isTwin") == null && element.getAttribute("binded") == null && (element.getAttribute("dropdown_mode")!=null) ){
					//added by jdh at 20040212
					//hide dropdownbtn
					/*
					// 隐藏已经自动显示的下拉按钮，自动显示功能在后续版本中已经不推荐使用，故这个操作基本没用
					if(_show_drop_down_btn=="true"){
						if(element.bind_btn){
							//hideDropDownBtn(element);
							element.bind_btn.style.visibility="hidden";
						}
					}
					*/
					var twin_element = document.createElement("<input>");
					// 克隆
					twin_element = element.cloneNode(true);
					// 孪生兄弟
					twin_element.isTwin = true;	
					with (twin_element){	
						//绑定两个元素
						setAttribute("twins", element);
						// 其克隆者，相当于它的父亲, added by steve_gu at 2004-05-13，预留属性
						setAttribute("cloner", element);
						// added for readonly，if user
						// set attribute readOnly,mute all key event,
						// but ,cannot mute "Ctrl+v" and Chinese input
						// 不需要这个属性了，要想只读，用dropDown_fixed属性替代即可
						if(element.readOnly){
							removeAttribute("readOnly");
							setAttribute("read",true);
						}
						// end added
						//var pos = getAbsPosition(element);
						//style.posLeft = 100;
						//style.posTop = pos[1] + 1 ;

						// 以前的版本 modified by steve_gu at 2004-05-24 
						// 根据配置文件决定是用新版本还是老版本
						if (typeof(cescomGlobalConfig) == "undefined" || typeof (cescomGlobalConfig.needCompatibility) == "undefined" || cescomGlobalConfig.needCompatibility == true) {
							id = element.id + "_ces";	
						}
						else {
							id = element.id + "_displayValue";
						}
						if(element.name) {
							removeAttribute("name");// 防止提交时冲突
							setAttribute("name", element.name + "_displayValue");
						}
						// end modify
						// added by steve_gu at 2004-05-19 处理默认值						
						if (element.dropdown_defaultValue) {							
							var defaultValue = element.dropdown_defaultValue;
							var arrayDefaultValue = defaultValue.split("=");
							if (arrayDefaultValue && arrayDefaultValue.length == 2) {		
								element.setAttribute("displayValue", arrayDefaultValue[0]);// 显示值
								element.setAttribute("value", arrayDefaultValue[1]);// 内部值
								setAttribute("value", arrayDefaultValue[0]);
							}
						}
						else {
							element.setAttribute("displayValue", "");// 显示值
							element.setAttribute("value", "");// 内部值
						}
						// end add
					}
					// 页面上原有的元素用binded属性代表它克隆的元素
					// 被克隆的元素用twins属性代表页面上原有的元素
					element.setAttribute("binded", twin_element);
					// 被克隆者，相当于它的儿子, added by steve_gu at 2004-05-13，预留属性,这样的属性更容易理解
					element.setAttribute("clonee", twin_element);
					if(compareText(element.getAttribute("dropDown_mode"), "list")){						
						setElementValue(twin_element, element.value);
					}else if(compareText(element.getAttribute("dropDown_mode"), "sql")){
						//twin_element.value="";
						setSqlElementValue(twin_element,element.value);
					}
					// 隐藏页面原有的元素
					// modified by steve_gu at 2004-06-01，将visibility改为display，防止获得焦点
					element.style.display = "none";
					// 调整位置
					element.style.width=0;// 此处可将原有的元素还原出来
					element.insertAdjacentElement("afterEnd",twin_element);
				}
				//-----------------------
				// end changed by jdh 
				// added by steve_gu at 2004-01-07
				if (element.getAttribute("datatype") == "date" || element.getAttribute("datatype") == "datetime") {
				}
				if (element.getAttribute("noempty") != null) {
					// 在非空字段后面加一红色*号
					if(element.binded){
					}else{
						if(element.getAttribute("bind_btn")==null){
							// 第一次初始化页面时
							//if (!_document_loaded) element.insertAdjacentHTML("afterEnd","<font color=red>&nbsp;&nbsp;*</font>"); 
							if (element.nextSibling && element.nextSibling.tagName == "FONT") {
							}
							else element.insertAdjacentHTML("afterEnd","<font color=red>&nbsp;&nbsp;*</font>"); 
						}else{
							//if (!_document_loaded) element.bind_btn.insertAdjacentHTML("afterEnd","<font color=red>*</font>"); 
							if (element.nextSibling && element.nextSibling.tagName == "FONT") {
							}
							else element.bind_btn.insertAdjacentHTML("afterEnd","<font color=red>&nbsp;&nbsp;*</font>"); 
						}						
					}
				}
				// end add
				// added by steve_gu at 2004-03-25
				if (element.getAttribute("datatype") == "int" || element.getAttribute("datatype") == "float" || element.getAttribute("datatype") == "date" || element.getAttribute("datatype") == "datetime" || element.getAttribute("datatype") == "yearmonth") {
					// 屏蔽输入法					
					element.style.imeMode = "disabled";
				}
				// end add
				//alert("id = " + element.id + " | element.dropdown_items = " + element.dropdown_items);
				// 注意这里没有加break
			}
			case "dockeditor":{				
				if (!element.className) element.className=_attrib;
				if (getValidStr(element.getAttribute("dropdown_cached"))=="" && getIEVersion()>"5.0")
					element.dropdown_cached=true;
				// 如果有dataset属性的话，初始化Dataset
				initElementDataset(element);
				with (element){
					if (tagName.toLowerCase()=="input" && compareText(type, "checkbox")){
						style.borderColor="window";
						onclick=_checkbox_onclick;
					}
					language="javascript";
					// 注册事件
					onkeydown=_control_onkeydown;
					onkeypress=_editor_onkeypress;
					onpropertychange=_editor_onpropertychange;
				}
				initChildren=false;
				break;
			}
			case "datatable":{
				if (isTrue(element.getAttribute("isDropDownTable"))){
					if (!element.className) element.className="dropdowntable";
				}
				else{
					if (!element.className) element.className="datatable";
				}

				initElementDataset(element);
				initDataTable(element, !isTrue(element.getAttribute("skipRebuild")));
				element.onkeydown=_control_onkeydown;
				break;
			}
			case "tablecell":{
				if (!element.className) element.className=_attrib;
				initChildren=false;
				break;
			}
			case "datapilot":{
				if (!element.className) element.className=_attrib;
				initElementDataset(element);
				initDataPilot(element);
				break;
			}
			case "button":{
				if (!element.className) element.className=_attrib;
				element.hideFocus=true;
				setButtonDown(element, element.getAttribute("down"))
				element.onmousedown=_button_onmousedown;
				element.onmouseover=_button_onmouseover;
				element.onmouseout=_button_onmouseout;
				initChildren=false;
				break;
			}
			case "tree":{
				if (!element.className) element.className=_attrib;
				initTree(element);
				initChildren=false;
				break;
			}
			case "tabpage":{
				if (!element.className) element.className=_attrib;
				// here, element is a table
				initTabPage(element);
				initChildren=false;
				break;
			}
			case "datascrollbar":{
				if (!element.className) element.className=_attrib;
				initElementDataset(element);
				element.onlosecapture=_scrollbar_change;
				initChildren = false;
				break;
			}
			default:{
				if (element.className &&_attrib) element.className=_attrib;
				break;
			}
		}
		// 父window
		element.window=window;
		// 没有定义document_onInitElement这个函数，预留
		fireUserEvent("document_onInitElement", [element, _attrib]);
	}	
	return initChildren;
}

/**
 * init all elements, include child elements.
 */ 
function initElements(element){
	if (compareText(element.getAttribute("attrib"), "tabpage")){
		// 加入到_tabpage_list数组中
		_tabpage_list[_tabpage_list.length]=element;
	}
	else{
		if (!initElement(element)) return;// 没有子节点，不用递归
	}
	// recursive
	for (var i=0; i<element.children.length; i++){
		initElements(element.children[i]);
	}
}

/**
 * 初始化元素的逆操作，主要是将有dataset属性邦定的dataset置空
 * @param element 
 */
function uninitElement(element){
	var _attrib=element.getAttribute("attrib");
	switch (_attrib){
		case "datalabel":;
		case "editor":;
		case "dockeditor":;
		case "datatable":;
		case "tablecell":;
		case "datapilot":;
		case "datascrollbar":{
			if (typeof(setElementDataset)!="undefined") setElementDataset(element, null);
			break;
		}
	}
}

/**
 * uninitial all elements
 * @param element
 */
function uninitElements(element){
	for(var i=0; i<_array_dataset.length; i++){
		var dataset=_array_dataset[i];
		if (dataset.window==window) dataset.setMasterDataset(null);
	}
	if (!element) element=document.body;
	for (var i=0; i<element.children.length; i++){
		// recursive
		uninitElements(element.children[i]);
	}
	uninitElement(element);
}

/**
 * window onunload时执行uninitElements()操作
 */
function _window_onunload() {
	uninitElements();
}

/**
 * 初始化所有tabpase
 */
function initTabPages(){
	for (var i=0; i<_tabpage_list.length; i++){
		initElement(_tabpage_list[i]);
	}
}

/**
 * 初始化整个页面
 */
function initDocument() {
	if (getIEVersion()<"5.0") alert(constErrUnsupportBrowser);
	_document_loading=true;
	try{
		// 改变作用域链
		with (document){			
			for(var i=0; i<_array_dataset.length; i++){
				initDataset(_array_dataset[i]);
			}
			if (typeof(_setElementsProperties)!="undefined") _setElementsProperties();
	
			// 初始化页面上的所有元素
			initElements(body);

			for(var i=0; i<_array_dataset.length; i++){
				var dataset=_array_dataset[i];
				if (dataset.masterDataset && dataset.masterLinks){
					dataset.setMasterDataset(dataset.masterDataset, dataset.masterLinks, dataset.detailSql);
				}
				dataset.refreshControls();
			}

			setTimeout("initTabPages()", 0);

			language = "javascript";
			// 注册页面上所有元素属性改变的事件
			onpropertychange = _document_onpropertychange;
			// 注册页面上在所有元素上按键事件
			onkeydown = _document_onkeydown;
			//oncontextmenu = _document_oncontextmenu;
		}
		// 如果没有在<body>元素里显式地设置，则注册默认onunload事件
		if (!window.onunload) window.onunload=_window_onunload;

		if (typeof(sizeDockEditor)!="undefined")  {
			setInterval("adjustControlsSize();", 300);
		}
		// 第一次document.activeElement.tagName为body;
		processActiveElementChanged(document.activeElement);
	}
	finally{
		_document_loading=false;
		_document_loaded = true;	
		// added by steve_gu at 2004-05-25 处理custom下拉框, 在下拉出该jsp页面装载后
		// 必须要执行initDropDownBox方法，但因为custom下拉框是另外一个页面，必须显式
		// 地执行，有冗余，但不不知道在用<iframe src="xxx.jsp"></iframe>时候，如何
		// 在xxx.jsp中得到父窗体引用该jsp页面的iframe对象。
		/*
		if(compareText(element.getAttribute("dropDown_mode"), "custom")){			
			initDropDownBox("custom");
		}
		*/	
		initDropDownBox("custom");
		// end add
	}
}

var _ad_box = null;
var _ad_interval = 50;
var _ad_count = _ad_interval;

// 不知该函数有什么用

function adjustControlsSize(){
	function showAD(){
		_ad_count++;
		if (!_ad_box || _ad_count>_ad_interval){
			if (_ad_box) _ad_box.removeNode(true);
			_ad_box=document.createElement("<A target='_blank' href='http://www.eraysoft.com' style='color: blue; font-size: 9pt; position: absolute'></A>");
			_ad_box.innerText="www.eraysoft.com";
			document.body.appendChild(_ad_box);
			
			_ad_count=0;
			_ad_interval=45+Math.random()*10;
		}
		_ad_box.style.left=document.body.clientWidth+document.body.scrollLeft-_ad_box.offsetWidth-6;	
		_ad_box.style.top=document.body.clientHeight+document.body.scrollTop-_ad_box.offsetHeight-3;
	}
	
	if (typeof(sizeDockEditor)!="undefined"){
		sizeDockEditor();
		if (typeof(sizeDropDownBtn)!="undefined" && _activeEditor) sizeDropDownBtn(_activeEditor);
		if (typeof(sizeDropDownBox)!="undefined") sizeDropDownBox();
	}
	
	// showAD();
}

/**
 * 得到元素的记录集
 * param element 元素
 * return 记录集
 */
function getElementDataset(element){
	switch (element.getAttribute("attrib")){
		case "tablecell":{
			var table=getTableByCell(element);
			if (table){
				return table.getAttribute("dataset");
			}
			break;
		}
		case "tablerow":{
			var table=getTableByRow(element);
			if (table){
				return table.getAttribute("dataset");
			}
			break;
		}
		case "dockeditor":{
			var holder=element.editorHolder;
			if (holder){
				return getElementDataset(holder);
			}
			break;
		}
		default:{
			return element.getAttribute("dataset");
			break;
		}
	}
}

/**
 * 得到editor中对应的dataset中的dataField
 */
function getElementField(element){
	var dataset = getElementDataset(element);
	if (!dataset) return;
	return dataset.getField(element.getAttribute("dataField"));
}

function getElementValue(element){
	var eventName = getElementEventName(element, "onGetValue");
	if (isUserEventDefined(eventName)){
		var event_result = fireUserEvent(eventName, [element, value]);
		return event_result;
	}

	switch (element.getAttribute("attrib")){
		case "editor":;
		case "dockeditor":{
			switch (element.type.toLowerCase()){
				case "checkbox":{
					return element.checked;
					break;
				}
				default:{
					var result;
					if (compareText(element.getAttribute("dropDown_mode"), "list") && isTrue(element.getAttribute("dropDown_mapValue"))){
						var items=getDropDownItems(element);
						if (items){
							var item=items.find(["name"], [element.value]);
							if (item) result=item.getText("value");
						}
					}
					else
						result=element.value;
					return result;
					break;
				}
			}
			break;
		}

		case "datascrollbar":{
			return element.Value;
			break;
		}

		default:{
			return element.value;
			break;
		}
	}
}

// added by jdh at 20040216 for sql
/**
 * @param value 内部值
 */
function setSqlElementValue(element,value){
	if (compareText(element.getAttribute("dropDown_mode"), "sql")){
		// 处理静态下拉框，当用户选择的值没有变化时，不执行赋值操作，
		// 从而也就不会触发propertychange事件，这是为了解决onchangde
		// 的问题。
		if(element.getAttribute("isTwin") != null){		
			var cloner = element.cloner; // modified by steve_gu at 2004-05-18
			if (cloner) {
				if(element.keyValue){
					cloner.displayValue = element.value;
					cloner.keyValue=element.value;
					if(cloner.value != element.keyValue) cloner.value = element.keyValue;
				}
			}			
			if (cloner.displayValue) element.value = cloner.displayValue;
		}	
	}
}
//end added

/**
 * @param value 内部值
 */
function setElementValue(element, value){
	// 内部函数，得到显示的值
	function getEditorValue(element, value){
		// 具有内部值的静态下拉框
		if (compareText(element.getAttribute("dropDown_mode"), "list")
			&& isTrue(element.getAttribute("dropDown_mapValue")) ){
			// 这里的value更确切的说是keyValue
			element.keyValue=value;
			//alert("value = " + value);
			var result = (value==null)?"":value;
			var items = getDropDownItems(element);
			
			if (items){
				var item = items.find(["value"], [value]);				
				if (item) result=item.getText("name");
			}
			//-----------------------
			// changed by jdh from ztp at 20040202
			// 处理静态下拉框，当用户选择的值没有变化时，不执行赋值操作，
			// 从而也就不会触发propertychange事件，这是为了解决onchange
			// 的问题。
			if(element.getAttribute("isTwin") != null){
				var cloner = element.cloner;
				//alert(value);
				if (cloner){
					// added by steve_gu at 2004-06-17，
					// 解决自定义的id加_onChange的事件中只能得到选中后的内部值
					// 而字面值还是以前的值问题
					if (cloner.clonee && cloner.clonee.value) {
						cloner.clonee.value = result;
						cloner.displayValue = result;
					}
					// end add
					if(cloner.value != value) cloner.value = value;					
					//processActiveElementChanged();
				}
			}
			//-----------------------
			// end changed by jdh 
			return result;
		}else if (compareText(element.getAttribute("datatype"), "date")){
			return getValidStr(value);// added by steve_gu at 2004-03-07 很奇怪的现象
		}
		else return getValidStr(value);
	}

	switch (element.getAttribute("attrib")){
		case "fieldlabel":;
		case "columnfooter":;
		case "columnheader":{
			var eventName=getElementEventName(element, "onSetValue");
			if (isUserEventDefined(eventName)){
				if (!fireUserEvent(eventName, [element, value])) break;
			}
			element.innerHTML=value;
			break;
		}

		case "datalabel":{
			var eventName=getElementEventName(element, "onSetValue");
			if (isUserEventDefined(eventName)){
				if (!fireUserEvent(eventName, [element, value])) break;
			}
			element.innerText=value;
			break;
		}

		case "editor":;
		case "dockeditor":{
			var eventName=getElementEventName(element, "onSetValue");
			if (isUserEventDefined(eventName)){
				if (!fireUserEvent(eventName, [element, value])) break;
			}

			switch (element.type.toLowerCase()){
				case "checkbox":{
					element.checked=isTrue(value);
					break;
				}
				default:{					
					element.value=getEditorValue(element, value);
					// added by steve_gu at 2004-05-29
					if(element.getAttribute("isTwin") != null){
						var cloner = element.cloner;
						cloner.displayValue = element.value;
					}
					// end add
					break;
				}
			}
			break;
		}

		case "tablecell":{
			var eventName=getElementEventName(element, "onSetValue");
			if (isUserEventDefined(eventName)){
				if (!fireUserEvent(eventName, [element, value])) break;
			}

			var tmpHTML;
			switch (element.getAttribute("editorType")){
				case "checkbox":{
					if (isTrue(value)){
						tmpHTML="<font face=Marlett size=2>a</font>";
					}
					else{
						tmpHTML="<font face=Webdings size=1 color=silver>c</font>";
					}
					element.innerHTML=tmpHTML;
					break;
				}
				default:{
					tmpHTML=getEditorValue(element, value);
//					alert(tmpHTML);
					if (tmpHTML=="") tmpHTML=" ";
					element.innerText=tmpHTML;
				}
			}
			break;
		}

		case "treenode":{
			var node=element.node;
			var canceled=false;
			var eventName=getElementEventName(getTableByCell(element), "onSetValue");
			if (isUserEventDefined(eventName)){
				canceled=(!fireUserEvent(eventName, [element, value]));
			}
			if (!canceled) element.innerHTML=value;

			var tmpHTML="";
			if (node.imageUrl){
				if (node.hasChild && node.expanded && node.expandImageUrl)
					tmpHTML="<img src=\""+node.expandImageUrl+"\" style=\"margin-right: 4px\">";
				else
					tmpHTML="<img src=\""+node.imageUrl+"\" style=\"margin-right: 4px\">";
				element.insertAdjacentHTML("afterBegin", tmpHTML);
			}

			var record=node.data;
			var button
			if (node.hasChild){
				var button_img=(node.expanded)?"collapse.gif":"expand.gif";
				button=document.createElement("<img id=_button_expand hideFocus=true src=\""+Ces_Library_path+"images/"+button_img+"\""+
					" language=javascript onclick=\"return _tree_expendclick(this);\" style=\"cursor: hand; margin-top: 3px; margin-left: 4px; margin-right: 4px\">");

				button.treenode=element;
				element.insertAdjacentElement("afterBegin", button);
			}
			else{
				element.insertAdjacentHTML("afterBegin", "<img id=_button_expand hideFocus=true src=\""+Ces_Library_path+"images/NoChild.gif\""+
					" style=\"cursor: hand; margin-top: 3px; margin-left: 4px; margin-right: 4px\">");
			}

			tmpHTML="";
			element.button=button;
			for(var i=1; i<node.level; i++){
				tmpHTML+="&nbsp;&nbsp;&nbsp;&nbsp;"
			}
			element.insertAdjacentHTML("afterBegin", tmpHTML);
			break;
		}

		case "datascrollbar":{
			element.Value=value;
			element.old_value=element.Value;
			break;
		}

		default:{
			element.value=value;
		}
	}
}

/**
 * 更新元素的值
 * @param element 待更新的元素
 */
function refreshElementValue(element){
	var dataset;
	var _attrib=element.getAttribute("attrib");

	switch (_attrib){
		case "fieldlabel":{
			var label=element.getAttribute("dataField");
			var field=getElementField(element);
			if (field){
				label=field.label;
				if (isTrue(field.notNull) && !isTrue(field.readOnly) && !isTrue(field.dataset.readOnly)){
						label="<font color=red>*</font>"+label;
				}
			}
			setElementValue(element, label);
			break;
		}

		case "columnheader":;
		case "columnfooter":{
			var label=getValidStr(element.getAttribute("label"));
			var field=getElementField(element);
			if (label==""){
				if (field){
					label=field.label;
					if (isTrue(field.notNull) && !isTrue(field.readOnly) && !isTrue(field.dataset.readOnly)){
							label="<font color=red>*</font>"+label;
					}
				}
				else{
					label=getValidStr(element.getAttribute("dataField"));
				}
			}
			setElementValue(element, label);
			break;
		}

		case "tablecell":{
			var row=getRowByCell(element);
			var record=row.record;
			var dataField=element.getAttribute("dataField");
			if (dataField=="select") break;

			if (record)
				setElementValue(element, record.getText(dataField));
			else
				setElementValue(element, "");
			break;
		}

		case "treenode":{
			var node=element.node;

			if (node)
				setElementValue(element, node.label);
			else
				setElementValue(element, "");
			break;
		}

		case "datascrollbar":{
			dataset=getElementDataset(element);

			if (dataset){
				if (dataset.bof){
					setElementValue(element, element.Min);
				}
				else if (dataset.eof){
					setElementValue(element, element.Max);
				}
				else{
					setElementValue(element, element.Max/2);
				}
				element.old_value=getElementValue(element);
			}
			break;
		}

		default:{
			dataset=getElementDataset(element);

			var value="";
			if (dataset){
				var fieldName;
				if (element.use_keyField && element.getAttribute("keyField")){
					fieldName=element.getAttribute("keyField");
					if (fieldName && dataset.getField(fieldName))
						value=dataset.getText(fieldName);
					else if (element.keyValue)
						value=element.keyValue;
				}
				else{
					fieldName=element.getAttribute("dataField");
					if (fieldName) value=dataset.getText(fieldName);
				}
				setElementValue(element, value);
			}			
			element.old_value=getElementValue(element);
			element.modified=false;
			break;
		}
	}
}

/**
 * 设置button的显示
 */
function refreshButtonColor(button){
	if (isTrue(button.getAttribute("down"))){
		button.className="button_down";
	}
	else{
		button.className="button";
	}
}

/**
 * 设置button
 */
function setButtonDown(button, down){
	button.down=isTrue(down);
	refreshButtonColor(button);
}

/**
 * button的onmousedown事件，用于下拉菜单，这个功能我们不用
 */
function _button_onmousedown(){
	var button=event.srcElement;
	var menu=button.getAttribute("menu");

	if (typeof(menu)=="string" && menu!=""){
		eval("menu="+menu);
		button.menu=menu;
	}

	if (menu){
		showPopupMenu(menu, button);
	}
}

/**
 * button的onmouseover事件
 */
function _button_onmouseover(){
	try{
		var button=event.srcElement;
		if (button.disabled) return;
		if (_menu_frame){
			var old_button=_menu_frame.button;
			if (old_button){
				var menu=button.getAttribute("menu");

				if (typeof(menu)=="string"){
					eval("menu="+menu);
					button.menu=menu;
				}

				if (button==old_button){
					clearTimeout(_menu_frame.timeout_id);
				}
				else if (menu){
					showPopupMenu(menu, button);
					button.focus();
				}
			}
		}
	}
	catch(e){
		//do nothing
	}
}

/**
 * button的onmouseout事件
 */
function _button_onmouseout(){
//	return;
	try{
		var button=event.srcElement;
		if (button.disabled) return;
		refreshButtonColor(button);

		if (button.menu_opened) hidePopupMenu();
	}
	catch(e) {
		// do nothing
	}
}

/**
 * 滚动条移动事件
 */
function _scrollbar_change() {
	var scrollbar=event.srcElement;
	var dataset=scrollbar.getAttribute("dataset");
	var oldValue=scrollbar.old_value;
	if (scrollbar.Value!=oldValue) {
		dataset.move(scrollbar.Value-oldValue);
		refreshElementValue(scrollbar);
	}
}

/**
 * 得到dropdownitems内容
 * @param editor 编辑框
 */
function getDropDownItems(editor){
	var items=editor._dropDown_items;
	if (!items) {
		initDropDownItems(editor);
		items=editor._dropDown_items;
	}
	return items;
}

/**
 * 设置dropdownitems内容
 * @param editor 编辑框
 * @param items  待设置的内容
 */
function setDropDownItems(editor, items){
	// 重新设置外部属性（dropDown_items，二次开发中涉及到的），并将内部属性（_dropDown_items）
	// 置空，以便在以后调用getDropDownItems(editor)时取更新过的值
	editor.dropDown_items=items;
	editor._dropDown_items=null;
}

/**
 * 内部方法，供initDropDownItems（）调用
 * @param itemsStr 由外部属性dropDown_items得到的一字符串
 * @param mapValue 是否为键值映射
 */
function _initDropDownItems(itemsStr, mapValue) {
	if (!itemsStr) return null;
	var splitStr=";";
	var arrayItem=createDataset();
	arrayItem.id="_dropDown_items";
	arrayItem.readOnly=true;

	if (mapValue){
		var field;
		field=arrayItem.addField("name");
		field=arrayItem.addField("value");
		field.visible=false;

		var tmp=itemsStr.split(splitStr);
		var index;
		for (var i=0; i<tmp.length; i++ ){
			index=tmp[i].indexOf("=");
			record=new Array();
			record[0]=getDecodeStr(tmp[i].substr(0, index));
			record[1]=getDecodeStr(tmp[i].substr(index+1));
			pArray_insert(arrayItem, "end", null, record);
		}

	}
	else{
		arrayItem.addField("value");

		var tmp=itemsStr.split(splitStr);
		for (var i=0; i<tmp.length; i++ ){
			record=new Array();
			record[0]=getDecodeStr(tmp[i]);
			pArray_insert(arrayItem, "end", null, record);
		}
	}
	return arrayItem;
}

/**
 * 将dropDown_items属性的内容(若有值)加到editor._dropDown_items属性中
 * @param editor 待加入的editor元素
 */
function initDropDownItems(editor){
	var dropDown_items = editor.getAttribute("dropDown_items");
	if (!dropDown_items) return;
	var items = _initDropDownItems(dropDown_items, isTrue(editor.getAttribute("dropDown_mapValue")));
	if (!items) return;
	initDataset(items);
	editor._dropDown_items=items;
}

/**
 * dropdownbox是否可见
 */
function isDropdownBoxVisible() {
	if (typeof(_dropdown_box)!="undefined" && _dropdown_box)
		return (_dropdown_box.style.visibility=="visible")
	else
		return false;
}

/**
 * 得到状态信息
 */
function getStatusLabel(text) {
	if (typeof(_status_label)=="undefined"){
		document.body.insertAdjacentHTML("beforeEnd", "<DIV id=_status_label nowrap style=\"position: absolute; visibility: hidden;"+
			" padding-left: 16px; padding-right: 16px; height: 22px; font-size: 9pt; background-color: #ffffcc; border: 1 solid silver; padding-top:3; z-index: 10000;  filter:alpha(opacity=80)\"></DIV>");
	}
	_status_label.innerHTML=text;
}

/**
 * 显示状态信息
 * @param parent_window window
 * @param text 状态信息
 * @param control 状态信息放置的容器
 */
function showStatusLabel(parent_window, text, control){
	parent_window.getStatusLabel(text);
	parent_window._status_label.style.visibility="visible";
	if (control){
		var pos=getAbsPosition(control);
		locateStatusLabel(pos[0]+(control.offsetWidth-_status_label.offsetWidth)/2-1, pos[1]+control.offsetHeight+1);
	}
	else{
		parent_window._status_label.style.posLeft=(document.body.clientWidth - _status_label.offsetWidth) / 2;
		parent_window._status_label.style.posTop=(document.body.clientHeight - _status_label.offsetHeight) / 2;
		parent_window.document.onmousemove=null;
	}
		
}

/**
 * 隐藏状态信息
 */
function hideStatusLabel(parent_window){
	if (!parent_window.closed && parent_window._status_label){
		parent_window.document.onmousemove=null;
		parent_window._status_label.style.visibility="hidden";
	}
}

function locateStatusLabel(x, y){
	if (x==0 && y==0) return;

	var posX=document.body.clientWidth + document.body.scrollLeft - _status_label.offsetWidth;
	var posY=document.body.clientHeight + document.body.scrollTop - _status_label.offsetHeight;
	posX=(x<posX)?x:posX;
	posY=(y<posY)?y:posY;

	_status_label.style.posLeft=posX + 1;
	_status_label.style.posTop=posY + 1;
}

function initDataPilot(dataPilot){
	if (!dataPilot.getAttribute("pageSize")){
		var dataset=getElementDataset(dataPilot);
		if (dataset) dataPilot.pageSize=dataset.pageSize;
	}
	var pageSize=dataPilot.getAttribute("pageSize");

	for(var i=0; i<dataPilot.tBodies[0].rows.length; i++){
		var row=dataPilot.tBodies[0].rows[i];
		row.removeNode(true);
	}

	var buttons_str=getValidStr(dataPilot.getAttribute("buttons"));
	if (buttons_str=="" || compareText(buttons_str, "default"))
		buttons_str="movefirst,prevpage,moveprev,movenext,nextpage,movelast,appendrecord,deleterecord,cancelrecord,updaterecord";
	else if (compareText(buttons_str, "readonly"))
		buttons_str="movefirst,prevpage,moveprev,movenext,nextpage,movelast";
	buttons_str=buttons_str.toLowerCase();
	var buttons=buttons_str.split(",");

	var row=dataPilot.tBodies[0].insertRow();
	row.align="center";
	for(var i=0; i<buttons.length; i++){
		btn=document.createElement("<input type=button class=button hideFocus=true style=\"height: 18px\">");

		btn.tabIndex=-1;
		btn.onmouseover=_button_onmouseover;
		btn.onmouseout=_button_onmouseout;
		btn.onclick=_datapilot_onclick;

		btn.dataset=dataPilot.getAttribute("dataset");
		btn.buttonType=buttons[i];
		btn.datapiolt=dataPilot;

		switch(buttons[i]){
			case "movefirst":{
				btn.style.fontFamily="Webdings";
				btn.value="9";
				btn.title=constDatasetMoveFirst;
				btn.style.width=30;
				break;
			}
			case "prevpage":{
				btn.style.fontFamily="Webdings";
				btn.value="7";
				btn.title=constDatasetPrevPage;
				btn.style.width=30;
				break;
			}
			case "moveprev":{
				btn.style.fontFamily="Webdings";
				btn.value="3";
				btn.title=constDatasetMovePrev;
				btn.style.width=30;
				break;
			}
			case "movenext":{
				btn.style.fontFamily="Webdings";
				btn.value="4";
				btn.title=constDatasetMoveNext;
				btn.style.width=30;
				break;
			}
			case "nextpage":{
				btn.style.fontFamily="Webdings";
				btn.value="8";
				btn.title=constDatasetNextPage;
				btn.style.width=30;
				break;
			}
			case "movelast":{
				btn.style.fontFamily="Webdings";
				btn.value=":";
				btn.title=constDatasetMoveLast;
				btn.style.width=30;
				break;
			}
			case "insertrecord":{
				btn.value=constBtnInsertRecord;
				btn.title=constDatasetInsertRecord;
				btn.style.width=45;
				break;
			}
			case "appendrecord":{
				btn.value=constBtnAppendRecord;
				btn.title=constDatasetAppendRecord;
				btn.style.width=45;
				break;
			}
			case "deleterecord":{
				btn.value=constBtnDeleteRecord;
				btn.title=constDatasetDeleteRecord;
				btn.style.width=45;
				break;
			}
			case "editrecord":{
				btn.value=constBtnEditRecord;
				btn.title=constDatasetEditRecord;
				btn.style.width=45;
				break;
			}
			case "cancelrecord":{
				btn.value=constBtnCancelRecord;;
				btn.title=constDatasetCancelRecord;
				btn.style.width=45;
				break;
			}
			case "updaterecord":{
				btn.value=constBtnUpdateRecord;
				btn.title=constDatasetUpdateRecord;
				btn.style.width=45;
				break;
			}
		}
		btn.id=dataPilot.id+"_"+btn.buttonType;
		row.insertCell().appendChild(btn);
	}

	refreshDataPilot(dataPilot);
}

function setDataPilotButtons(dataPilot, buttons){
	dataPilot.buttons=buttons;
	initDataPilot(dataPilot);
}

function refreshDataPilot(dataPilot){

	function refreshButton(btn, enable){
		btn.disabled=!enable;
		btn.style.background = "url("+Ces_Library_path+"images/button.gif)";
		if (enable){
		}
		else{
		}
	}

	var dataset=getElementDataset(dataPilot);

	var row=dataPilot.rows[0];
	for(var i=0; i<row.cells.length; i++){
		var btn=row.cells[i].children[0];
		switch(btn.buttonType){
			case "movefirst":;
			case "moveprev":{
				refreshButton(btn, (dataset && !dataset.bof));
				break;
			}
			case "prevpage":{
				refreshButton(btn, (dataset && dataset.record && dataset.record.pageIndex>1));
				break;
			}
			case "movenext":;
			case "movelast":{
				refreshButton(btn, (dataset && !dataset.eof));
				break;
			}
			case "nextpage":{
				refreshButton(btn, (dataset && dataset.record && dataset.record.pageIndex<dataset.pageCount));
				break;
			}
			case "insertrecord":;
			case "appendrecord":{
				refreshButton(btn, (dataset && !dataset.readOnly));
				break;
			}
			case "editrecord":{
				refreshButton(btn, (dataset && !(dataset.bof && dataset.eof) && !dataset.readOnly));
				break;
			}
			case "deleterecord":{
				refreshButton(btn, (dataset && !(dataset.bof && dataset.eof) && !dataset.readOnly));
				break;
			}
			case "cancelrecord":;
			case "updaterecord":{
				refreshButton(btn, (dataset && (dataset.state=="insert" || dataset.state=="modify") && !dataset.readOnly));
				break;
			}
		}

		fireUserEvent(getElementEventName(dataPilot, "onRefreshButton"), [dataPilot, btn, btn.buttonType, dataset]);
	}
}

function _datapilot_onclick(){
	if (event.srcElement.disabled) return;
	var datapiolt=event.srcElement.datapiolt;
	var dataset=getElementDataset(datapiolt);

	var eventName=getElementEventName(datapiolt, "onButtonClick");
	if (isUserEventDefined(eventName)){
		var event_result=fireUserEvent(eventName, [datapiolt, event.srcElement, event.srcElement.buttonType, dataset]);
		if (!event_result) return;
	}

	var pageSize=datapiolt.getAttribute("pageSize");
	switch(event.srcElement.buttonType){
		case "movefirst":{
			dataset.moveFirst();
			break;
		}
		case "prevpage":{
			var pageIndex=(dataset.record)?dataset.record.pageIndex-1:1;
			dataset.moveToPage(pageIndex);
			break;
		}
		case "moveprev":{
			dataset.movePrev();
			break;
		}
		case "movenext":{
			dataset.moveNext();
			break;
		}
		case "nextpage":{
			var pageIndex=(dataset.record)?dataset.record.pageIndex+1:1;
			dataset.moveToPage(pageIndex);
			break;
		}
		case "movelast":{
			dataset.moveLast();
			break;
		}
		case "insertrecord":{
			dataset.insertRecord("before");
			break;
		}
		case "appendrecord":{
			dataset.insertRecord("end");
			break;
		}
		case "editrecord":{
			dataset_setState(dataset, "modify");
			break;
		}
		case "deleterecord":{
			if (isTrue(datapiolt.getAttribute("confirmDelete"))){
				if (confirm(constDatasetDeleteRecord)) dataset.deleteRecord();
			}
			else
				dataset.deleteRecord();
			break;
		}
		case "cancelrecord":{
			if (isTrue(datapiolt.getAttribute("confirmCancel"))){
				if (confirm(constDatasetCancelRecord)) dataset.cancelRecord();
			}
			else
				dataset.cancelRecord();
			break;
		}
		case "updaterecord":{
			dataset.updateRecord();
			break;
		}
	}
}

/**
 * render the tabpage
 * @param tabpage element whose tagName is "TABLE"
 */
function initTabPage(tabpage){
	var tabItems = tabpage.getAttribute("tabItems");
	if (!tabItems) return;
	var tabs=tabItems.split(";");
	// clean original body content of the table 
	for(var i = 0; i < tabpage.tBodies[0].rows.length; i++){
		var row = tabpage.tBodies[0].rows[i];
		row.removeNode(true);
	}
	
	var row = tabpage.tBodies[0].insertRow();
	// start cell
	var cell = row.insertCell();
	// cell.height = 5;
	cell.firstcell=true;
	cell.innerHTML = "<img src=\"" + Ces_Library_path + "images/tab_start.gif\">";
	var label, tabcode, index;
	for(i=0; i < tabs.length; i++){
		// modified by steve_gu at 2004-06-07, add srcPage property
		var srcPage = "";
		/* index=tabs[i].indexOf("=");
		// aquire the label and tabcode respectively
		if (index >= 0){
			label = tabs[i].substr(0, index);
			tabcode = tabs[i].substr(index+1);
		}
		else{
			label = tabs[i];
			tabcode = tabs[i];
		}*/
		var eachTab = tabs[i].split("=");
		if (eachTab.length == 1) {
			label = eachTab[0];
			tabcode = eachTab[0];
		}
		if (eachTab.length == 2) {
			label = eachTab[0];
			tabcode = eachTab[1];
		}
		if (eachTab.length == 3) {
			label = eachTab[0];
			tabcode = eachTab[1];
			srcPage = eachTab[2];
		}
		// end modify
		// label cell
		cell = row.insertCell();
		//cell.height = 5;
		cell.background = Ces_Library_path+"images/tab_button.gif";
		cell.tab_index = i;
		cell.tab_code = tabcode;
		// added by steve_gu at 2004-06-07
		cell.srcPage = srcPage;
		// end add

		btn = document.createElement("<DIV hideFocus=true nowrap class=tabpagebtn></DIV>");
		btn.innerText=label;
		btn.tabIndex = -1;		
		btn.onclick = _tabpage_onclick;
		btn.onmouseover = _tabpage_onmouseover;
		btn.onmouseout = _tabpage_onmouseout;
		// btn's parent element is cell(td)
		btn.tab = cell;
		cell.appendChild(btn);
		// bounddary cell
		cell = row.insertCell();
		//cell.height = 5;
		// determine whether the tab is the last cell
		if (i != tabs.length-1){
			cell.innerHTML="<img src=\""+Ces_Library_path+"images/tab_interval.gif\">";
		}
		else{
			cell.lastcell=true;
			cell.innerHTML="<img src=\""+Ces_Library_path+"images/tab_end.gif\">";
		}

		eval("if (typeof("+tabpage.id+"_"+tabcode+")!=\"undefined\"){ "+
			tabpage.id+"_"+tabcode+".style.visibility=\"hidden\";"+
			tabpage.id+"_"+tabcode+".style.position=\"absolute\";}");
	}
	// end cell
	cell = row.insertCell();
	cell.width = "100%";
	// cell.background = Ces_Library_path+"images/tab_blank.gif";
	// set the first tabpage to active page
	setActiveTabIndex(tabpage, 0);
}

function setTabs(tabpage, tabs){
	tabpage.tabs=tabs;
	initTabPage(tabpage);
}

/*
 * set the active tab's style
 * @param cell the tab's parent element, is a td
 */
function _setActiveTab(cell){
	try{
		var row = getRowByCell(cell);
		var tabpage = getTableByRow(row);
		var selectCell = tabpage.selectTab;
		// if we clicked the same tabpage, return
		if (selectCell == cell) return;
		var oldCode = (selectCell)?selectCell.tab_code:"";
		var newCode = cell.tab_code;
		// 预留的一个事件
		var eventName = getElementEventName(tabpage, "beforeTabChange");
		var event_result = fireUserEvent(eventName, [tabpage, oldCode, newCode]);
		if (event_result) throw event_result;

		if (selectCell){
			var prevCell = row.cells[selectCell.cellIndex-1];
			var nextCell = row.cells[selectCell.cellIndex+1];
			selectCell.background=Ces_Library_path+"images/tab_button.gif";
			if (prevCell.firstcell)	prevCell.firstChild.src=Ces_Library_path+"images/tab_start.gif";
			else prevCell.firstChild.src=Ces_Library_path+"images/tab_interval.gif";

			if (nextCell.lastcell) nextCell.firstChild.src=Ces_Library_path+"images/tab_end.gif";
			else nextCell.firstChild.src=Ces_Library_path+"images/tab_interval.gif";
			eval("if (typeof("+tabpage.id+"_"+oldCode+")!=\"undefined\") "+tabpage.id+"_"+oldCode+".style.visibility=\"hidden\"");
		}

		var prevCell = row.cells[cell.cellIndex-1];
		var nextCell = row.cells[cell.cellIndex+1];

		cell.background = Ces_Library_path+"images/tab_active_button.gif";

		if (prevCell.firstcell)	prevCell.firstChild.src=Ces_Library_path+"images/tab_active_start.gif";
		else prevCell.firstChild.src=Ces_Library_path+"images/tab_active1.gif";

		if (nextCell.lastcell) nextCell.firstChild.src=Ces_Library_path+"images/tab_active_end.gif";
		else nextCell.firstChild.src=Ces_Library_path+"images/tab_active2.gif";
		eval("if (typeof("+tabpage.id+"_"+newCode+")!=\"undefined\") "+tabpage.id+"_"+newCode+".style.visibility=\"\"");

		tabpage.selectTab = cell;
		tabpage.selectCode = cell.tab_code;
		tabpage.selectIndex = cell.tab_index;
		// 预留的一个事件
		var eventName = getElementEventName(tabpage, "afterTabChange");
		fireUserEvent(eventName, [tabpage, oldCode, newCode]);
		// added by steve_gu at 2004-06-07
		// document.body.insertAdjacentHTML("beforeEnd", "<div id=_over_label language=javascript"+
		// "style=\"position: absolute; background-color: black; left:0; top:0; z-index: 9999; filter:alpha(opacity=30)\"></div>");

		// default width
		var tabwidth = "100%";
		if (tabpage.style.width) {
			tabwidth = tabpage.style.width;
		}
		// the tabpage.width's priority is the highest
		if (tabpage.width) {			
			tabwidth = tabpage.width;
		}
		if (tabpage.tabheight) {
			var tabHeight = tabpage.tabheight;
		}
		// if we adapt the .jsp mode(each tabpage has a jsp page respectively)
		if (cell.srcPage.indexOf(".") >= 0) {
			// del the original tab src page
			if(tabpage.nextSibling && tabpage.nextSibling.tagName == "IFRAME" && tabpage.nextSibling.className == "tabpageframe") {
				// tabpage.parentNode.removeChild(tabpage.parentNode.lastChild);//childNodes[tabpage.parentNode.childNodes.length - 1];	
				tabpage.parentNode.removeChild(tabpage.nextSibling);//childNodes[tabpage.parentNode.childNodes.length - 1];	
			}
			if (tabHeight) {
				tabpage.insertAdjacentHTML("afterEnd", "<iframe id='tabIframe' class='tabpageframe' name='tabIframe' style='width:" + tabwidth + ";height:" + tabHeight + ";'></iframe>");
			}
			else {
				tabpage.insertAdjacentHTML("afterEnd", "<iframe id='tabIframe' class='tabpageframe' name='tabIframe' style='width:" + tabwidth + ";'></iframe>");
			}			
			if (cell.srcPage && cell.srcPage != "") {
				document.getElementById('tabIframe').src = cell.srcPage;
			}				
		}
		// we adapt the div mode(all tabpage share the only one page)
		else {
			if (cell.srcPage && cell.srcPage != "") {
				if (tabHeight) {
					if (selectCell) {
						document.getElementById(selectCell.srcPage).className = "tabpagediv";
					}
					document.getElementById(cell.srcPage).className = "tabpageselectdiv";					
				}
				else {				
					if (selectCell) {
						document.getElementById(selectCell.srcPage).className = "tabpagediv";
					}
					document.getElementById(cell.srcPage).className = "tabpageselectdiv";
				}
			}			
		}
		// end add
	}
	catch(e){
		processException(e);
	}
}

/*
 * set the active tab's style determined by tabcode
 * @param table the parent table
 * @param tabcode 
 */
function setActiveTab(table, tabcode){
	if (!tabcode) return;
	for(var i = 0; i < table.cells.length; i++){
		if (table.cells[i].tab_code == tabcode){
			_setActiveTab(table.cells[i]);
			break;
		}
	}
}

/*
 * set the active tab's style determined by index
 * @param table the parent table
 * @param index 
 */
function setActiveTabIndex(table, index){
	for(var i=0; i<table.cells.length; i++){
		if (table.cells[i].tab_index == index){
			_setActiveTab(table.cells[i]);
			break;
		}
	}
}

/*
 * set the active tab's style determined by the srcElement
 */
function _tabpage_onclick(){
	// the tagName of event.srcElement.tab is td, see btn.tab = cell;
	_setActiveTab(event.srcElement.tab);
}

function _tabpage_onmouseover(){
	event.srcElement.style.color = "blue";
	event.srcElement.style.textDecorationUnderline = true;
}

function _tabpage_onmouseout(){
	event.srcElement.style.color = "black";
	event.srcElement.style.textDecorationUnderline = false;
}

/**
 * 在form提交之前对所有的元素进行统一有效性校验，最常见的用法是form的
 * onsubmit="return cescom_js_global_validate(this,true);",表示对form的所有输入框进行统一校验
 * @param htmlControl 要检验的输入框的父元素容器（html对象），如form，body等
 * @param displayAllError 若有多个校验未通过的输入框，true表示一次显示所有的非法输入信息，false
 *       表示逐个显示，逐个纠正
 * @return true or false true:验证通过，提交表单；false:验证未通过，不提交表单
 */
function cescom_js_global_validate(htmlControl, displayAllError) {
	if (!displayAllError) {
		displayAllError = validateConfig.displayAllErrorMessage;
	}
	// 默认认为所有的提交数据有效
	var dataValid = true;
	// 错误信息
	var errorMessage = "";
	// 错误序号
	var errorId = 0;
	// 存放password的内容，为了验证多个password内容是否匹配
	var passTextArray = new Array();
	var passNO = 0;
	// 第一个错误输入框对象，当第二个参数displayAllError为true时，最后弹出全部错误信息后焦点转移到该对象
	var firstErrorControl;
	for (var i = 0; i < htmlControl.all.length; i++) {
		var eachChild = htmlControl.all[i];		
		if (eachChild.getAttribute("attrib") == "editor" && (eachChild.tagName == "INPUT" || eachChild.tagName=="TEXTAREA")) {

			if (eachChild.getAttribute("noempty") != null && eachChild.value.length==0) {				
				if (!displayAllError) {
					// 直接返回，焦点移到该无效输入框
					alert(constErrNoEmpty);					
					eachChild.focus();				
					return false;	
				}
				// 如果还没有出现无效输入，该对象为第一个输入
				if (dataValid == true) firstErrorControl = eachChild;
				// 标记有无效数据出现
				dataValid = false;
				// 错误序号递增
				errorId++;
				// 得到该输入框的标签
				var label = eachChild.getAttribute("label");
				if (label) errorMessage = errorMessage + errorId + ". " + "[" + label + "] " + constErrNoEmpty + "\n";
				else errorMessage = errorMessage + errorId + ". " + constErrNoEmpty + "\n";
			}
			// added by steve_gu at 2004-05-24 密码匹配校验
			if (eachChild.getAttribute("type") == "password") {
				var passText = eachChild.value;
				passTextArray[passNO] = passText;
				passNO++;
				for (var j = 0; j < passTextArray.length; j++) {
					if (passText != passTextArray[j]) {
						if (!displayAllError) {
							// 直接返回，焦点移到该无效输入框
							alert(constErrPasswordNotMatchable);					
							eachChild.focus();				
							return false;	
						}
						// 如果还没有出现无效输入，该对象为第一个输入
						if (dataValid == true) firstErrorControl = eachChild;
						// 标记有无效数据出现
						dataValid = false;
						// 错误序号递增
						errorId++;
						// 得到该输入框的标签
						var label = eachChild.getAttribute("label");
						if (label) errorMessage = errorMessage + errorId + ". " + "[" + label + "] " + constErrPasswordNotMatchable + "\n";
						else errorMessage = errorMessage + errorId + ". " + constErrPasswordNotMatchable + "\n";
						break;
					}		
				}
			}
			// end add
			if (eachChild.getAttribute("datatype")) {
				var datatype = eachChild.getAttribute("datatype");
				if (datatype == "int"){							
					var intValue = eachChild.value;
					// 每一段字符串都能解析成整数
					var validInt = true;
					// modified by steve_gu at 2004-05-21,采用正则表达式中的模式匹配,替换”每一段字符串都能解析成整数“方法
					var pattern = /[^0-9]/;
					if (pattern.exec(intValue) != null) {
						validInt = false;
					}
					// end modify

					if (validInt == false) {
						if (!displayAllError) {
							// 直接返回，焦点移到该无效输入框
							alert(constErrInt);					
							eachChild.focus();				
							return false;	
						}
						// 如果还没有出现无效输入，该对象为第一个输入
						if (dataValid == true) firstErrorControl = eachChild;
						// 标记有无效数据出现
						dataValid = false;
						// 错误序号递增
						errorId++;
						// 得到该输入框的标签
						var label = eachChild.getAttribute("label");
						if (label) errorMessage = errorMessage + errorId + ". " + "[" + label + "] " + constErrInt + "\n";
						else errorMessage = errorMessage + errorId + ". " + constErrInt + "\n";
					}
				}
				if (datatype == "float") {	
					var floatValue = eachChild.value;
					var validFloat = true;
					// modified by steve_gu at 2004-05-21,采用正则表达式中的模式匹配,替换”每一段字符串都能解析成小数“方法
					var pattern = /[^01234569789.]/;
					if (pattern.exec(floatValue) != null) {
						validFloat = false;
					}
					// end modify
					if (validFloat == false) {
						if (!displayAllError) {
							// 直接返回，焦点移到该无效输入框
							alert(constErrFloat);					
							eachChild.focus();				
							return false;	
						}
						// 如果还没有出现无效输入，该对象为第一个输入
						if (dataValid == true) firstErrorControl = eachChild;				
						// 标记有无效数据出现
						dataValid = false;
						// 错误序号递增
						errorId++;
						// 得到该输入框的标签
						var label = eachChild.getAttribute("label");
						if (label) errorMessage = errorMessage + errorId + ". " + "[" + label + "] " + constErrFloat + "\n";
						else errorMessage = errorMessage + errorId + ". " + constErrFloat + "\n";
					}
					if (eachChild.getAttribute("format")) {// if fixed ormat float
						if (eachChild.value != "") {							
							var arrInputValue = eachChild.value.split(".");	
							var floatFormat = eachChild.getAttribute("format");
							var arrFloatFormat = floatFormat.split(".");
							// 多于一个"."						
							if (arrInputValue.length >= 3) {

								if (!displayAllError) {
									// 直接返回，焦点移到该无效输入框
									alert(constErrFloatType);					
									eachChild.focus();				
									return false;	
								}
								// 如果还没有出现无效输入，该对象为第一个输入
								if (dataValid == true) firstErrorControl = eachChild;				
								// 标记有无效数据出现
								dataValid = false;
								// 错误序号递增
								errorId++;
								// 得到该输入框的标签
								var label = eachChild.getAttribute("label");
								if (label) errorMessage = errorMessage + errorId + ". " + "[" + label + "] " + constErrFloatType + "\n";
								else errorMessage = errorMessage + errorId + ". " + constErrFloatType + "\n";
								// 如果小数位固定，自动补足或截取
								if (arrFloatFormat[1].substring(0,1) == "?") {
									// 没有小数位,自动补足
									if (arrInputValue.length == 1) {
										eachChild.value += "." ;
										for (var i = 0; i <= arrFloatFormat[1].length - 1; i++) {
											eachChild.value += "0";
										}
									}
									// 有小数位，但可能不全，自动补足；或过多，则截取
									if (arrInputValue.length == 2) {
										if ( arrInputValue[1].length < arrFloatFormat[1].length) {
											for (var i = 0; i < arrFloatFormat[1].length - arrInputValue[1].length;i++) {
												eachChild.value += "0";
											}
										}
										if ( arrInputValue[1].length > arrFloatFormat[1].length) {
											var offset = arrInputValue[1].length - arrFloatFormat[1].length;
											eachChild.value = eachChild.value.substring(0, eachChild.value.length - offset);
										}
									}
								}
						    }

							// 补足或截取以后再判断整数位和小数位为?还是*
							// 如果整数位和小数位格式均为???
							if (arrFloatFormat[0].substring(0,1) == "?" && arrFloatFormat[1].substring(0,1) == "?") {
								if (eachChild.value.split(".")[0].length != arrFloatFormat[0].length || eachChild.value.split(".")[1].length != arrFloatFormat[1].length) {
									var temp = constErrFloatFormat.replace("%intCount", arrFloatFormat[0].length);
									temp = temp.replace("%decimalCount", arrFloatFormat[1].length);
									if (!displayAllError) {
										// 直接返回，焦点移到该无效输入框
										alert(temp);					
										eachChild.focus();				
										return false;	
									}
									// 如果还没有出现无效输入，该对象为第一个输入
									if (dataValid == true) firstErrorControl = eachChild;				
									// 标记有无效数据出现
									dataValid = false;
									// 错误序号递增
									errorId++;
									// 得到该输入框的标签
									var label = eachChild.getAttribute("label");
									if (label) errorMessage = errorMessage + errorId + ". " + "[" + label + "] " + temp + "\n";
									else errorMessage = errorMessage + errorId + ". " + temp + "\n";
								}
							}
							if (arrFloatFormat[0].substring(0,1) == "?" && arrFloatFormat[1].substring(0,1) == "*") {
								if (eachChild.value.split(".")[0].length != arrFloatFormat[0].length) {
									var temp = constErrFloatFormat.replace("%intCount", arrFloatFormat[0].length);
									temp = temp.replace("%decimalCount", "不限");
									if (!displayAllError) {
										// 直接返回，焦点移到该无效输入框
										alert(temp);					
										eachChild.focus();				
										return false;	
									}
									// 如果还没有出现无效输入，该对象为第一个输入
									if (dataValid == true) firstErrorControl = eachChild;				
									// 标记有无效数据出现
									dataValid = false;
									// 错误序号递增
									errorId++;
									// 得到该输入框的标签
									var label = eachChild.getAttribute("label");
									if (label) errorMessage = errorMessage + errorId + ". " + "[" + label + "] " + temp + "\n";
									else errorMessage = errorMessage + errorId + ". " + temp + "\n";
								}
							}
							// 因为固定的小数位已补足或截取，所以不可能出现格式为（不固定的整数位，固定的小数位）而长度不满足的情况
						}						
					}					
				}

				if (eachChild.getAttribute("datatype") == "email") {
					var emailValue = eachChild.value;
					if (emailValue.indexOf("@") == -1 || emailValue.indexOf("@") == 0 || emailValue.indexOf(".") == -1 || emailValue.lastIndexOf(".") == emailValue.length - 1 || emailValue.split("@").length > 2) {
						if (!displayAllError) {
							// 直接返回，焦点移到该无效输入框
							alert(constErrEmail);					
							eachChild.focus();				
							return false;	
						}
						// 如果还没有出现无效输入，该对象为第一个输入
						if (dataValid == true) firstErrorControl = eachChild;				
						// 标记有无效数据出现
						dataValid = false;
						// 错误序号递增
						errorId++;
						// 得到该输入框的标签
						var label = eachChild.getAttribute("label");
						if (label) errorMessage = errorMessage + errorId + ". " + "[" + label + "] " + constErrEmail + "\n";
						else errorMessage = errorMessage + errorId + ". " + constErrEmail + "\n";
					}
				}
				
				// 日期时间验证
				if (eachChild.getAttribute("datatype") == "date" && eachChild.value != "" && eachChild.value != null) {
					if (eachChild.getAttribute("separator")) {				
						var tmpValue = eachChild.value.replace(eachChild.getAttribute("separator"), "/");
						tmpValue = tmpValue.replace(eachChild.getAttribute("separator"),"/");
					}
					else {
						var tmpValue = eachChild.value.replace("-", "/");
						tmpValue = tmpValue.replace("-","/");
					}
					var _date = new Date(tmpValue);
					if (isNaN(_date)) {
						if (!displayAllError) {
							// 直接返回，焦点移到该无效输入框
							alert(constErrTypeDate.replace("%s", _activeElement.value));					
							eachChild.focus();				
							return false;	
						}
						// 如果还没有出现无效输入，该对象为第一个输入
						if (dataValid == true) firstErrorControl = eachChild;				
						// 标记有无效数据出现
						dataValid = false;
						// 错误序号递增
						errorId++;
						// 得到该输入框的标签
						var label = eachChild.getAttribute("label");
						if (label) errorMessage = errorMessage + errorId + ". " + "[" + label + "] " + constErrTypeDate.replace("%s", _activeElement.value) + "\n";
						else errorMessage = errorMessage + errorId + ". " + constErrTypeDate.replace("%s", _activeElement.value) + "\n";
					}

					// added by steve_gu at 2004-01-30，因为js中判断一个字符串是否为有效日期型很宽松，所以要限制日期输入的长度
					if (tmpValue.length != 0 && tmpValue.length > 10) {
						if (!displayAllError) {
							// 直接返回，焦点移到该无效输入框
							alert(constErrTypeDate.replace("%s", _activeElement.value));					
							eachChild.focus();				
							return false;	
						}
						// 如果还没有出现无效输入，该对象为第一个输入
						if (dataValid == true) firstErrorControl = eachChild;				
						// 标记有无效数据出现
						dataValid = false;
						// 错误序号递增
						errorId++;
						// 得到该输入框的标签
						var label = eachChild.getAttribute("label");
						if (label) errorMessage = errorMessage + errorId + ". " + "[" + label + "] " + constErrTypeDate.replace("%s", _activeElement.value) + "\n";
						else errorMessage = errorMessage + errorId + ". " + constErrTypeDate.replace("%s", _activeElement.value) + "\n";
					}
				}
				if (eachChild.getAttribute("datatype") == "datetime" && eachChild.value != "" && eachChild.value != null) {
					if (eachChild.getAttribute("separator")) {				
						var tmpValue = eachChild.value.replace(eachChild.getAttribute("separator"), "/");
						tmpValue = tmpValue.replace(eachChild.getAttribute("separator"),"/");
					}
					else {
						var tmpValue = eachChild.value.replace("-", "/");
						tmpValue = tmpValue.replace("-","/");
					}
					//var tmpValue = _activeElement.value.replace("-", "/");
					//tmpValue = tmpValue.replace("-","/");				
					var _date = new Date(tmpValue);
					if (isNaN(_date)) {
						if (!displayAllError) {
							// 直接返回，焦点移到该无效输入框
							alert(constErrTypeDateTime.replace("%s", _activeElement.value));					
							eachChild.focus();				
							return false;	
						}
						// 如果还没有出现无效输入，该对象为第一个输入
						if (dataValid == true) firstErrorControl = eachChild;				
						// 标记有无效数据出现
						dataValid = false;
						// 错误序号递增
						errorId++;
						// 得到该输入框的标签
						var label = eachChild.getAttribute("label");
						if (label) errorMessage = errorMessage + errorId + ". " + "[" + label + "] " + constErrTypeDateTime.replace("%s", _activeElement.value) + "\n";
						else errorMessage = errorMessage + errorId + ". " + constErrTypeDateTime.replace("%s", _activeElement.value) + "\n";
					} 
					if (tmpValue.length != 0 && tmpValue.length > 19) {
						if (!displayAllError) {
							// 直接返回，焦点移到该无效输入框
							alert(constErrTypeDate.replace("%s", _activeElement.value));					
							eachChild.focus();				
							return false;	
						}
						// 如果还没有出现无效输入，该对象为第一个输入
						if (dataValid == true) firstErrorControl = eachChild;				
						// 标记有无效数据出现
						dataValid = false;
						// 错误序号递增
						errorId++;
						// 得到该输入框的标签
						var label = eachChild.getAttribute("label");
						if (label) errorMessage = errorMessage + errorId + ". " + "[" + label + "] " + constErrTypeDate.replace("%s", _activeElement.value) + "\n";
						else errorMessage = errorMessage + errorId + ". " + constErrTypeDate.replace("%s", _activeElement.value) + "\n";
					}
				}
			}// end datatype

			// 最小长度验证
			if (eachChild.getAttribute("minlength")) {
				var minLength = eachChild.getAttribute("minlength");
				if (eachChild.value.length < minLength) {
					if (!displayAllError) {
						// 直接返回，焦点移到该无效输入框
						alert(constErrMinlength.replace("%minLength",minLength));					
						eachChild.focus();				
						return false;	
					}
					// 如果还没有出现无效输入，该对象为第一个输入
					if (dataValid == true) firstErrorControl = eachChild;				
					// 标记有无效数据出现
					dataValid = false;
					// 错误序号递增
					errorId++;
					// 得到该输入框的标签
					var label = eachChild.getAttribute("label");
					if (label) errorMessage = errorMessage + errorId + ". " + "[" + label + "] " + constErrMinlength.replace("%minLength",minLength) + "\n";
					else errorMessage = errorMessage + errorId + ". " + constErrMinlength.replace("%minLength",minLength) + "\n";
				}
			}

			// 最大长度验证
			if (eachChild.getAttribute("maxlength")) {
				var maxLength = eachChild.getAttribute("maxlength");
				if (eachChild.value.length > maxLength) {			
					if (!displayAllError) {
						// 直接返回，焦点移到该无效输入框
						alert(constErrMaxlength.replace("%maxLength",maxLength));					
						eachChild.focus();				
						return false;	
					}
					// 如果还没有出现无效输入，该对象为第一个输入
					if (dataValid == true) firstErrorControl = eachChild;				
					// 标记有无效数据出现
					dataValid = false;
					// 错误序号递增
					errorId++;
					// 得到该输入框的标签
					var label = eachChild.getAttribute("label");
					if (label) errorMessage = errorMessage + errorId + ". " + "[" + label + "] " + constErrMaxlength.replace("%maxLength",maxLength) + "\n";
					else errorMessage = errorMessage + errorId + ". " + constErrMaxlength.replace("%maxLength",maxLength) + "\n";
				}
			}
		}// end attrib			
	}// end for
	if (dataValid == false) {
		//errorMessage = errorMessage.substring(0,errorMessage.length-1);
		errorMessage += constErrRectify;
		alert(errorMessage);
		if (firstErrorControl) {
			firstErrorControl.focus();
		}
		return false;
	}
	else {
		return true;
	}
}