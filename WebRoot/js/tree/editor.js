//-----------------------
// Extra v2.0
// Developer: bao yilei
// July, 2002
//-----------------------

var needUpdateEditor=true;

function setEditorReadonly(editor, _readOnly){
	with (editor){
		if (_readOnly){
			editor.readOnly=true;
			style.color="dimgray";
			style.backgroundColor="whitesmoke";
		}
		else{
			editor.readOnly=false;
			style.color="black";
			style.backgroundColor="";
		}
	}
}


function checkFieldEditable(editor, dataset){
	var editable=true;
	if (dataset){
		var field=getElementField(editor);
		if (field){
			editable=!(isTrue(dataset.readOnly) || isTrue(field.readOnly));
		}
		else
			editable=true;
	}

	return editable;
}

function processDropDownSelected(editor, record, fireBySystem){
	var eventName=getElementEventName(editor, "onDropDownSelect");
	needAbort=(isUserEventDefined(eventName) && !fireUserEvent(eventName, [editor, record, fireBySystem]));

	if (needAbort) return;

	if (record){
		switch (editor.getAttribute("dropDown_mode")){
			case "list":{
				setElementValue(editor, record.getValue("value"));
				break;
			}
			case "date":{
				if (editor.getAttribute("separator")) {
					setElementValue(editor, formatDateTime(new Date(record.getValue("value")), editor.getAttribute("dataType"),editor.getAttribute("separator")));
				}
				else {
					setElementValue(editor, formatDateTime(new Date(record.getValue("value")), editor.getAttribute("dataType"),"-"));
				}
				break;
			}
			case "yearmonth": {
				//setElementValue(editor, record.getValue("value"));
				if (editor.getAttribute("separator")) {
					setElementValue(editor, formatDateTime(new Date(record.getValue("value")), editor.getAttribute("dataType"),editor.getAttribute("separator")).substring(0,7));
				}
				else {
					setElementValue(editor, formatDateTime(new Date(record.getValue("value")), editor.getAttribute("dataType"),"-").substring(0,7));
				}
				break;
			}
			case "custom": {
				//alert("custom");
				break;
			}
			default:{
				var dataset=getElementDataset(editor);
				var dataField=editor.getAttribute("dataField");
				var keyField=editor.getAttribute("keyField");
				var dropDown_dataField=editor.getAttribute("dropDown_dataField");
				var dropDown_keyField=editor.getAttribute("dropDown_keyField");
				if (!dropDown_dataField) dropDown_dataField=dataField;
				if (!dropDown_keyField) dropDown_keyField=keyField;

				if (dataset){
					if (dropDown_keyField){
						if (fireBySystem)
							editor.keyValue=editor.value;
						else
							editor.keyValue=record.getValue(dropDown_keyField);

						if (dataset.getField(keyField)){
							dataset.setValue(keyField, editor.keyValue);
						}
						else{
							editor.value=editor.keyValue;
						}
					}
					dataset.setValue(dataField, record.getValue(dropDown_dataField));
				}
				else{
					//changed by jdh at 20040216 for keyvalue
//					if(keyField){
						editor.keyValue=record.getValue(keyField);
//					}else if(editor.value){
//						editor.keyValue=editor.value;
//					}
					
					//end changed
					editor.value=record.getValue(dataField);
				}
			}
			//added by jdh at 20040216 for sql
			if(editor.getAttribute("dropDown_mode")=="sql"){
				setSqlElementValue(editor,editor.value);	
			}
			//end added
		}
	}
	else{
		switch (editor.getAttribute("dropDown_mode")){
			case "sql": ;
			case "custom":{
				setElementValue(editor, "");
				var dataset=getElementDataset(editor);
				// modified by steve_gu at 2004-03-15, 解决刘赣提出的当custom输入框变为空的小BUG
				if (editor.getAttribute("keyField") != null) dataset.setValue(editor.getAttribute("dataField"), "");
				if (editor.getAttribute("dataField") != null && !isNaN(editor.getAttribute("dataField"))) {				
					dataset.setValue(editor.getAttribute("keyField"), "");
				}
			}
		}
	}

	editor.dropDown_selectedValue=editor.value;
}

function validEditorInput(editor){
	if (!editor.value || (compareText(editor.getAttribute("dropDown_mode"), "list") && isTrue(editor.getAttribute("dropDown_mapValue")))) return;

	switch (editor.getAttribute("dataType")){
		case "int":{
			if (isNaN(parseInt(editor.value)))
				//alert(constErrTypeInt.replace("%s", editor.value));
			break;
		}
		case "float":{
			if (isNaN(parseFloat(editor.value)))
				//alert(constErrTypeNumber.replace("%s", editor.value));
			break;
		}
		case "date":{
			var _date=new Date(editor.value);
			if (isNaN(_date)) {
			// added by steve_gu at 2004-01-06
				//valid = false;
				// commented by steve_gu at 2004-01-07 
				// 采用自己的失去焦点的判断法
				//throw constErrTypeDate.replace("%s", editor.value);
			}
			else{
				valid = true;
				if (editor.getAttribute("separator")) {
					editor.value=formatDateTime(_date, "date",editor.getAttribute("separator"));
				}
				else {
					editor.value=formatDateTime(_date, "date","-");
				}
			}
			break;
		}
		case "datetime":{
			var _date=new Date(editor.value);
			if (isNaN(_date)) {
				// added by steve_gu at 2004-01-06
				//valid = false;
				// commented by steve_gu at 2004-01-07 
				// 采用自己的失去焦点的判断法
				//throw constErrTypeDateTime.replace("%s", editor.value);
			}
			else{
				valid = true;
				if (editor.getAttribute("separator")) {
					editor.value=formatDateTime(_date, "datetime",editor.getAttribute("separator"));
				}
				else {
					editor.value=formatDateTime(_date, "datetime","-");
				}
			}
			break;
		}
		case "time":{
			var _date=new Date("1900/1/1 "+editor.value);
			if (isNaN(_date))
				throw constErrTypeTime.replace("%s", editor.value);
			else{
				if (editor.getAttribute("separator")) {
					editor.value=formatDateTime(_date, "time",editor.getAttribute("separator"));
				}
				else {
					editor.value=formatDateTime(_date, "time","-");
				}
			}
			break;
		}
	}
}
//-----------------------
// changed by jdh from ztp at 20040202
// 处理静态下拉框，增加一个方法，解决onchange
// 的问题（由于对input对象执行赋值操作时不会触发onchange事件，
// 所以要提供一个接口，供用户实现，然后当值变化时执行用户实现的方法）。
function valueChanged(element){
	//注意：这里采用“onChange”只是表明解决的是onchange事件，并非
	//页面对象的onchange事件，即与"onchange='alert(11)';"不同，
	//用户需在页面内写一段代码，函数名为：对象id+"_"+onChange,
	//就象这样：function user_id_onChange(){}，其中user_id为input
	//的id.
	var eventName=getElementEventName(element, "onChange");
	if (isUserEventDefined(eventName)){
		var event_result=fireUserEvent(eventName, [element]);
		return event_result;
	}
}
//-----------------------
// end changed by jdh 

/**
 * 更新输入框，失去焦点时用
 * @param 待更新的输入框对象
 */
function updateEditorInput(editor){
	//try{
		if (window.closed) return;
		
		editor.modified = (getElementValue(editor)!=editor.old_value);
		if (editor.modified){
			// 有效性验证
			validEditorInput(editor);

			var dataset=getElementDataset(editor);
			var editorValue=getElementValue(editor);
			var dataField=editor.getAttribute("dataField");
			var keyField=editor.getAttribute("keyField");

			var eventName=getElementEventName(editor, "onUpdate");
			var event_result=fireUserEvent(eventName, [editor]);
			if (event_result) throw event_result;

			if (editor.dropDown_selectedValue!=editor.value
				&& !isTrue(editor.getAttribute("dropDown_fixed"))){
				if (editor.value!=""){
					var notInList=false;
					switch (editor.getAttribute("dropDown_mode")){
						case "list":{
							var items=getDropDownItems(editor);
							if (items){
								notInList=(items.find(["value"], [editorValue])==null);
							}
							break;
						}
						case "dataset":{
							var tmp_dataset=editor.getAttribute("dropDown_dataset");
							if (tmp_dataset){
								if (typeof(tmp_dataset)=="string") tmp_dataset=getDatasetByID(tmp_dataset);
								if (dataset){
									var keyField=editor.getAttribute("keyField");
									if (!keyField) keyField=editor.getAttribute("dataField");
									if (keyField){
										var record=tmp_dataset.find([keyField], [editor.value]);
										notInList=(record==null);
										if (!notInList)	processDropDownSelected(editor, record, true);
									}
								}
							}
							break;
						}
						case "sql":;
						case "custom":{
							notInList=true;
							var retrieveSql=editor.getAttribute("retrieveSql");
							if (retrieveSql){
								retrieveSql=retrieveSql.replace("[value]", editor.value);
								/** modified by steve_gu at 2004-03-31 */
								var connName="222";//editor.getAttribute("dropDown_connName");
								/** end modify */
								if (!connName && dataset) connName=dataset.connName;

								if (retrieveSql && connName){
									var tmp_dataset=execSQL(retrieveSql, connName, true);
									if (tmp_dataset){
										if (tmp_dataset.record){
											processDropDownSelected(editor, tmp_dataset.record, true);
											notInList=false;
											return;
										}
									}
								}
							}
							break;
						}
					}

					if (notInList && isTrue(editor.getAttribute("dropDown_restrict"))){
						throw constErrOutOfDropDownList;
					}
				}
				else{
					switch (editor.getAttribute("dropDown_mode")){
						case "sql":;
						case "custom":{
							processDropDownSelected(editor, null, true);
							break;
						}
					}
				}
			}
			editor.dropDown_selectedValue=editor.value;
			if (dataset && dataset.record){
				if (dataset.window==window){
					if (keyField){
						_record_setValue(dataset.record, keyField, trimStr(editorValue));
					}
					else{
						_record_setValue(dataset.record, dataField, trimStr(editorValue));
					}
				}
				else{
					if (keyField){
						dataset.window._record_setValue(dataset.record, keyField, trimStr(editorValue));
					}
					else{
						dataset.window._record_setValue(dataset.record, dataField, trimStr(editorValue));
					}
				}
			}
		}
	//}
	/*catch(e){
		alert("error");
		processException(e);
		refreshElementValue(editor);
		editor.focus();
		throw "abort";
	}*/
}

function processEditorValueChanged(editor){
	var dataset=getElementDataset(editor);
	if (dataset){
		var value=editor.value;
		if (!dataset.record && editor.value!=""){
			dataset.insertRecord("end");
			if (dataset.state=="insert") editor.value=value;
		}
	}

	editor.modified=(getElementValue(editor)!=editor.old_value);

	if (editor.dropDown_visible && _dropdown_window)
		_dropdown_window.dropDownLocate();
}

function _editor_onpropertychange() {
	if (event.propertyName=="value"){
		var editor=event.srcElement;
		if (_activeEditor==editor) processEditorValueChanged(editor);
		//-----------------------
		// changed by jdh from ztp at 20040202
		// 处理静态下拉框，解决onchange事件。
		if(editor.getAttribute("binded") != null){
			valueChanged(editor);
		}
		//-----------------------
		// end changed by jdh 
	}
}


function _checkbox_onclick() {
	processEditorValueChanged(event.srcElement);
}

function _editor_onkeypress() {
	if (event.srcElement.readOnly || !event.srcElement.contentEditable){	
		event.returnValue=false;
		return;
	}

	var result=true;
	//added by jdh at 20040202
	//如果用户设置了readOnly属性，就屏蔽掉键盘事件
	if(event.srcElement.getAttribute("read")){
		result=false;
		event.returnValue=result;
		return ;
	}
	//end  added 
	switch (event.srcElement.getAttribute("dataType")){
		
		case "number":{
			result=(event.keyCode>=48 && event.keyCode<=57);
			break;
		}
		case "int":{
			result=(event.keyCode == 45 || (event.keyCode>=48 && event.keyCode<=57));
			break;
		}
		case "float":{
			result=(event.keyCode == 45 || event.keyCode == 46 || (event.keyCode>=48 && event.keyCode<=57));
			break;
		}
		case "date":{
			// modified by steve_gu at 2004-01-13, 允许"-"与"/"的键盘输入
			result=(event.keyCode == 47 || (event.keyCode>=48 && event.keyCode<=57) || event.keyCode == 45 || event.keyCode == 191);
			break;
		}
		case "datetime":{
			// modified by steve_gu at 2004-01-13, 允许"-"与"/"的键盘输入
			result=(event.keyCode == 47 || event.keyCode == 58 || event.keyCode == 32 || (event.keyCode>=48 && event.keyCode<=57)  || event.keyCode == 45 || event.keyCode == 191);
			break;
		}
		case "time":{
			result=(event.keyCode == 58 || (event.keyCode>=48 && event.keyCode<=57));
			break;
		}
	}
	event.returnValue=result;
}

function sizeDockEditor(editor) {
	var _editor=(editor)?editor:_activeEditor;
	if (!_editor) return;

	var holder=_editor.editorHolder;
	if (!holder) return;

	var pos=getAbsPosition(holder);

	with (_editor){
		if (!compareText(type, "checkbox")){
			style.posLeft=pos[0]-1;
			style.posTop=pos[1]-1;
			style.width=holder.offsetWidth+1;
			style.height=holder.offsetHeight+1;
		}
		else{
			style.posLeft=pos[0];
			style.posTop=pos[1];
			style.width=holder.clientWidth;
			style.height=holder.clientHeight;

			if (offsetWidth>18){
				style.borderLeftWidth=(offsetWidth-18)/2;
				style.borderRightWidth=(offsetWidth-18)/2;
			}
		}
	}
}

function showDockEditor(holder){
	try{
		if (isTrue(holder.getAttribute("readOnly"))) throw "abort";
		if (!checkFieldEditable(holder, getElementDataset(holder))) throw "abort";

		var eventName=getElementEventName(holder, "beforeLoadEditor");
		var event_result=fireUserEvent(eventName, [holder]);
		if (event_result) throw event_result;

		editor=getDockEditor(holder);
		if (editor.editorHolder==holder) return

		with (editor){
			if (style.visiblity!="visible"){
				editor.editorHolder=holder;
				editor.dataType=holder.getAttribute("dataType");
				editor.editorType=holder.getAttribute("editorType");
				editor.dataField=holder.getAttribute("dataField");
				editor.keyField=holder.getAttribute("keyField");
				editor.autoDropDown=holder.getAttribute("autoDropDown");
				editor.dropDown_mode=holder.getAttribute("dropDown_mode");
				editor.dropDown_items=holder.getAttribute("dropDown_items");
				editor._dropDown_items=holder._dropDown_items;
				editor.dropDown_dataset=holder.getAttribute("dropDown_dataset");
				editor.dropDown_dataField=holder.getAttribute("dropDown_dataField");
				editor.dropDown_keyField=holder.getAttribute("dropDown_keyField");
				editor.dropDown_fields=holder.getAttribute("dropDown_fields");
				editor.dropDown_fixed=holder.getAttribute("dropDown_fixed");
				editor.dropDown_cached=holder.getAttribute("dropDown_cached");
				editor.dropDown_restrict=holder.getAttribute("dropDown_restrict");
				editor.dropDown_mapValue=holder.getAttribute("dropDown_mapValue");
				editor.dropDown_url=holder.getAttribute("dropDown_url");
				editor.dropDown_sql=holder.getAttribute("dropDown_sql");
				/** modified by steve_gu at 2004-03-31 */
				editor.dropDown_connName="222";//holder.getAttribute("dropDown_connName");
				/* end modify */
				editor.dropDown_tableName=holder.getAttribute("dropDown_tableName");
				editor.dropdown_pageSize=holder.getAttribute("dropdown_pageSize");
				editor.dropdown_height=holder.getAttribute("dropdown_height");
				editor.retrieveSql=holder.getAttribute("retrieveSql");

				var _dataset=getElementDataset(holder);
				if (compareText(holder.tagName, "td")){
					var table=getTableByCell(holder);
					if (table) table.editor=editor;
					editor.in_table=true;
				}
				else
					editor.in_table=false;

				setElementDataset(editor, _dataset);

				sizeDockEditor(editor);
				style.visibility="visible";
			}

			editor.focus();
		}
	}
	catch(e){
		processException(e)
	}
}

function hideDockEditor(editor){
	if (editor.style.visibility=="visible"){
		_skip_activeChanged=true;
		editor.style.visibility="hidden";
		setElementDataset(editor, null);

		var holder=editor.editorHolder;
		if (holder){
			if (compareText(holder.tagName, "td")){
				var table=getTableByCell(holder);
				if (table) table.editor=null;
			}
			editor.editorHolder=null;
		}
	}
}

function getDockEditor(holder){
	var result=null;
	var editorType=holder.getAttribute("editorType");

	switch (editorType){
		case "textarea":{
			if  (typeof(_table_textarea)=="undefined"){
				result=document.createElement("<TEXTAREA id=_table_textarea attrib=dockeditor tabindex=-1"+
					" style=\"position: absolute; visibility: hidden\"></TEXTAREA>");
				initElement(result);
				document.body.appendChild(result);
			}
			else{
				result=_table_textarea;
			}
			break;
		}
		case "checkbox":{
			if  (typeof(_table_checkbox)=="undefined"){
				result=document.createElement("<INPUT id=_table_checkbox type=checkbox hidefocus=false attrib=dockeditor tabindex=-1"+
					" style=\"position: absolute; visibility: hidden; background-color: window;\">");
				initElement(result);
				document.body.appendChild(result);
			}
			else{
				result=_table_checkbox;
			}
			break;
		}
		default:{
			if  (typeof(_table_texteditor)=="undefined"){
				result=document.createElement("<INPUT id=_table_texteditor attrib=dockeditor tabindex=-1"+
					" style=\"position: absolute; visibility: hidden\">");
				initElement(result);
				document.body.appendChild(result);

			}
			else{
				result=_table_texteditor;
			}
			break;
		}
	}

	return result;
}

function setFocusTableCell(table, rowIndex, cellIndex){
	_rowIndex=rowIndex;
	_cellIndex=cellIndex;
	if (_rowIndex==-1) _rowIndex=table.activeRowIndex;
	if (_cellIndex==-1) _cellIndex=table.activeCellIndex;
	var index=checkTableCellIndex(table, _rowIndex, _cellIndex);
	table.rows[index[0]].cells[index[1]].focus();
}

function isEmptyRow(row){
	function getTableRowState(row){
		var record=row.record;
		if (record)
			return record.recordstate
		else
			return "";
	}

	return (getTableRowState(row)=="new" && !getTableRowModified(row));
}