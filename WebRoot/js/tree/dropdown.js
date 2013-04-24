//-----------------------
// Extra v2.0
// Developer: bao yilei
// July, 2002
//-----------------------
// 版本记录 
// 2004-03-31： 去除了无用的dropDown_connName属性，统一通过数据库连接池的数据库配置文件来定

// added by steve_gu at 2004-01-30
/** todo **/
// 获得位置的通用函数，来自menu，这一部分最后应该与menu里面的合并放在基础工具JS包里
var posTool = {
	// relative client viewport (outer borders of viewport)
	getClientLeft:	function (el) {
		var r = el.getBoundingClientRect();
		return r.left - this.getBorderLeftWidth(this.getCanvasElement(el));
	},

	getClientTop:	function (el) {
		var r = el.getBoundingClientRect();
		return r.top - this.getBorderTopWidth(this.getCanvasElement(el));
	},

	// relative canvas/document (outer borders of canvas/document,
	// outside borders of element)
	getLeft:	function (el) {
		return this.getClientLeft(el) + this.getCanvasElement(el).scrollLeft;
	},

	getTop:	function (el) {
		return this.getClientTop(el) + this.getCanvasElement(el).scrollTop;
	},

	// width and height (outer, border-box)
	getWidth:	function (el) {
		return el.offsetWidth;
	},

	getHeight:	function (el) {
		return el.offsetHeight;
	},

	getScreenLeft:	function (el) {
		//var doc = el.ownerDocument || el.document;	// IE55 bug
		//var w = doc.parentWindow;
		return window.screenLeft;
		return w.screenLeft + this.getBorderLeftWidth(this.getCanvasElement(el)) +
			this.getClientLeft(el);
	},

	getScreenTop:	function (el) {
		//var doc = el.ownerDocument || el.document;	// IE55 bug
		//var w = doc.parentWindow;
		return window.screenTop;
		return w.screenTop + this.getBorderTopWidth(this.getCanvasElement(el)) +
			this.getClientTop(el);
	}
}

var _dropdown_parentwindow=null;
var _dropdown_parentbox=null;
// 下拉box对象
var _dropdown_box=null;
var _dropdown_table=null;
var _dropdown_frame=null;
var _dropdown_dataset=null;
var _date_dropdown_box=null;

var _calendarControl=null;
var _tmp_dataset_date=null;

// added by steve_gu at 2004-01-30
var oPopup = window.createPopup();
var oPopupBody = oPopup.document.body;

function initDropDownBox(dropDown_mode){
	_document_loading=true;
	switch (dropDown_mode){
		case "sql":{
			_dropdown_div.onkeydown=_dropdown_onkeydown;
		}

		case "custom":{
//			status+=("in custom");
			_dropdown_parentwindow=window.parent;
			_dropdown_parentbox=_dropdown_parentwindow._dropdown_box;
			_dropdown_parentwindow._dropdown_window=window;
			if (!_dropdown_parentbox || _dropdown_parentbox.style.visibility=="hidden") return;

			var editor=_dropdown_parentbox.editor;
			_dropdown_div.style.width=
				(_dropdown_parentbox.offsetWidth>editor.offsetWidth)?_dropdown_parentbox.offsetWidth:editor.offsetWidth;

			_dropdown_parentwindow.sizeDropDownBox();

			with (_dropdown_parentwindow._dropdown_frame){
				width="100%";
				if (filters.blendTrans.status!=2) {
					if (getIEVersion()<"5.5"){
						style.visibility="visible";
					}
					else{
						filters.blendTrans.apply();
						style.visibility="visible";
						filters.blendTrans.play();
					}
				}
			}

			hideStatusLabel(_dropdown_parentwindow);
			break;
		}

		case "date":{
			_dropdown_parentwindow=window;
			_dropdown_parentbox=_dropdown_parentwindow._dropdown_box;
			_dropdown_parentwindow._dropdown_window=window;
			sizeDropDownBox();	
			if ((getIEVersion()>="5.5") &&
				_dropdown_parentbox.filters.blendTrans.status!=2)
				_dropdown_parentbox.filters.blendTrans.play();
			break;
		}
		case "yearmonth":{		
			_dropdown_parentwindow=window;
			_dropdown_parentbox=_dropdown_parentwindow._dropdown_box;
			_dropdown_parentwindow._dropdown_window=window;
			sizeDropDownBox();	
			if ((getIEVersion()>="5.5") &&
				_dropdown_parentbox.filters.blendTrans.status!=2)
				_dropdown_parentbox.filters.blendTrans.play();
			break;
		}
		default:{
//			status+=("in default");
			_dropdown_parentwindow=window;
			_dropdown_parentbox=_dropdown_parentwindow._dropdown_box;
			_dropdown_parentwindow._dropdown_window=window;
			_dropdown_dataset=getElementDataset(_dropdown_table);
			sizeDropDownBox();
			if ((getIEVersion()>="5.5") &&
				_dropdown_parentbox.filters.blendTrans.status!=2)
				_dropdown_parentbox.filters.blendTrans.play();
			break;
		}
	}

	_dropdown_parentbox.prepared=true;
	var editor=_dropdown_parentbox.editor;

	if (editor) dropDownLocate();
	_document_loading=false;

}


function sizeDropDownBox(){
	// 根据iframe的大小设置iframe外层的div(_dropdown_box)的大小
	function _sizeDropDownBox(new_width, new_height){
		with (_dropdown_box){
			var editor=_dropdown_box.editor;
			// modified by steve_gu at 2004-06-04
			// 先判断二次开发中是否显式地设置了dropdown_height值
			var maxHeight = parseInt(editor.getAttribute("dropdown_height"));
			// 默认maxHeight为220
			if (isNaN(maxHeight) || maxHeight<20) maxHeight=220;
			// end add

			var pos = getAbsPosition(editor, document.body);
			var _posLeft = pos[0];
			var _posTop = pos[1] + editor.offsetHeight + 1;
			// 如果要完全显示该页面的高度大于最大值，自动出现垂直滚动条
			if (new_height > maxHeight/* &&
				!(editor.getAttribute("dropdown_mode")=="sql" && getInt(editor.getAttribute("dropDown_pageSize"))>0)*/){
				new_height = maxHeight;
				// 因为width取iframe显示实际需要的宽度（不变），而自动出现的垂直滚动条
			    // 增加了滚动条的宽度16，所以要将其加上
				new_width += 16;
				if (!(getIEVersion()<"5.5")) style.overflowY="scroll";
				else style.overflowY="visible";
			}
			else{
				style.overflowY="hidden";
			}			

			var document_width=document.body.clientWidth + document.body.scrollLeft;
			var document_height=document.body.clientHeight + document.body.scrollTop;
			// 如果超出页面右边边界，向左移
			if (_posLeft + new_width >document_width && document_width > new_width) _posLeft=document_width-new_width;			
			// 如果超出页面下边界
			if (_posTop + new_height > document_height) {
				//window.status += "  document_height = " + document_height;
				// 如果页面翻上去不会超过上边界
				if (pos[1] > new_height) {
					 _posTop=pos[1]-new_height-1;
				}
				// added by steve_gu at 2004-06-04
				else {
					// 如果二次开发中没有显式设置dropdown_height属性(显式设置要确保下拉
					// 框不超出下边界，将_dropdown_box的高度设为可以显示的高度，这样下拉
					// 外边缘可以显示，里面的内容靠滚动。此外要排除掉日期组件，因为若日
					// 期下拉框也有滚动条的话不友好				
					if (isNaN(parseInt(editor.getAttribute("dropdown_height"))) && editor.getAttribute("datatype") != "date" && editor.getAttribute("datatype") != "datetime" && editor.getAttribute("datatype") != "yearmonth") {
						//window.status = "sss";
						new_height = document_height - _posTop - 2;
					}
				}
				// end add
			}
			style.posLeft=_posLeft;
			style.posTop=_posTop;
			style.posHeight=new_height+4;
			if (new_width>style.posWidth) style.posWidth=new_width+4;
			style.borderWidth="2px";
		}
	}
	// 如果已经显示了，返回
	if (!isDropdownBoxVisible()) return;

	try{
		var _width, _height;
		switch (_dropdown_box.getAttribute("dropDown_mode")){
			case "sql":;
			case "custom":{
				with (_dropdown_frame){
					// iframe中的页面（下拉页面）要显示全部内容所需要的高度，这个不能改，不然可能显示不全，因为我们将iframe这个元素设为不可滚动的，要滚动也只能滚动src的jsp页面的body
					_height = _dropdown_window._dropdown_div.offsetHeight;
					
					//alert("_dropdown_window._dropdown_div.offsetHeight = " + _dropdown_window._dropdown_div.offsetHeight);
					// end add
					if (_dropdown_window._dropdown_div.offsetWidth>_dropdown_box.clientWidth){
						_width=_dropdown_window._dropdown_div.offsetWidth;
					}
					else{
						_width=_dropdown_box.clientWidth;
					}
					style.posWidth=_width;
					style.posHeight=_height;
				}
				break;
			}

			case "date":{
				_width=CalendarTable.offsetWidth;
				_height=CalendarTable.offsetHeight;
				break;
			}
			case "yearmonth": {
				_width=CalendarTable.offsetWidth;
				_height=CalendarTable.offsetHeight;
				break;
			}
			default:{
				_width=_dropdown_table.offsetWidth;
				_height=_dropdown_table.offsetHeight;
				break;
			}
		}
		_sizeDropDownBox(_width, _height);
	}
	catch(e){
		//do nothing
	}
}

function canDropDown(editor){
	return (editor.getAttribute("dropDown_mode") &&
		!compareText(editor.type, "checkbox"));
}

function getDropDownCacheSaver(editor){
	if (editor.getAttribute("attrib")=="editor"){
		return editor;
	}
	else{
		var table=getTableByCell(editor.editorHolder);
		if (!table[editor.editorHolder.id]) table[editor.editorHolder.id]=new Object();
		return table[editor.editorHolder.id];
	}
}

/**
 * 得到dropDownBox
 */
function getDropDownBox(editor){
	var needCreate=true;
	// 此处即为editor对象
	var dropdown_saver=getDropDownCacheSaver(editor);

	if (editor.getAttribute("dropDown_mode")=="date" || editor.getAttribute("dropDown_mode")=="yearmonth") {
		needCreate=false;
		// 此处为undefined.		
		_dropdown_box=_date_dropdown_box;
	}
	else if (isTrue(editor.getAttribute("dropDown_cached")) &&
		dropdown_saver.cached_dropdown_mode==editor.getAttribute("dropDown_mode")){
		_dropdown_box=dropdown_saver.cached_dropdownbox;
		switch (editor.getAttribute("dropDown_mode")){
			case "list":{
				needCreate=(dropdown_saver.cached_dropdown_items!=editor.getAttribute("dropdown_items"));
				break;
			}
			case "dataset":{
				needCreate=(dropdown_saver.cached_dropdown_dataset!=editor.getAttribute("dropdown_dataset"));
				break;
			}
			case "sql":{
				needCreate=(dropdown_saver.cached_dropdown_connName!="222"/*editor.getAttribute("dropdown_connName")*/ ||
					dropdown_saver.cached_dropdown_sql!=editor.getAttribute("dropdown_sql"));
				break;
			}
			case "custom":{
				needCreate=(dropdown_saver.cached_dropdown_url!=editor.getAttribute("dropdown_url"));
				break;
			}
		}
	}

	// 实际生成的_dropdown_box在这儿, 但只生成了设置了style属性的div，没有里面的实际内容
	if (needCreate || !_dropdown_box){
		_dropdown_box=document.createElement("<DIV name='dropdown' class='dropdownboxdiv'></DIV>");
		document.body.appendChild(_dropdown_box);
	}
}

function getDropDownBtn(){
	if  (typeof(_dropdown_btn)=="undefined"){
		// _dropdown_btn is defined here, as the same time, the event is registerd.
		/*obj=document.createElement("<INPUT id=_dropdown_btn type=button tabindex=-1 value=6 hidefocus=true"+
			" style='position: absolute; visibility: hidden; border: whitesmoke 1px solid; font-family: Marlett; font-size: 10px; cursor: hand; z-index: 9999'"+
			" LANGUAGE=javascript onmousedown='return _dropdown_btn_onmousedown(this)' onfocus='return _dropdown_btn_onfocus(this)'>");
		obj.style.background = "url("+Ces_Library_path+"images/dropdown_button.gif)";*/
		/* modified by steve_gu at 2004-02-18  */
		// 只生成一次
		obj=document.createElement("<INPUT id=_dropdown_btn type=button tabindex=-1 value=6 hidefocus=true"+
			" class=dropdownButton "+
			" LANGUAGE=javascript onmousedown='return _dropdown_btn_onmousedown(this)' onfocus='return _dropdown_btn_onfocus(this)'>");
		/* end modify */
		document.body.appendChild(obj);
		return obj
	}
	else{
		return _dropdown_btn;
	}
}

function HideElement(elmID) {
 for (i = 0; i < document.all.tags(elmID).length; i++)
 {
  obj = document.all.tags(elmID)[i];
  if (! obj || ! obj.offsetParent)
  continue;
  objLeft=obj.offsetLeft;
  objWidth=obj.offsetWidth;
  objTop=obj.offsetTop;
  objHeight=obj.offsetHeight;
  objParent=obj.offsetParent;
  while (objParent.tagName.toUpperCase() != "BODY"){
   objLeft+=objParent.offsetLeft;
   objTop+=objParent.offsetTop;
   objParent=objParent.offsetParent;
  }
  if (objTop > (_dropdown_box.offsetTop+_dropdown_box.offsetHeight+60));
  else if (objLeft>(_dropdown_box.offsetLeft+_dropdown_box.offsetWidth));
       else if ((objLeft+objWidth)<_dropdown_box.offsetLeft);
            else{
             obj.style.visibility="hidden";
            }
 	}
}
function ShowElement(elmID){
 for (i = 0; i < document.all.tags(elmID).length; i++){
  obj = document.all.tags(elmID)[i];
  obj.style.visibility = "";
 }
}
/**
 * 显示下拉弹出的Box
 * _editor, 弹出box所处于的对象，如<INPUT name="text_find_birthday"  
 *          attrib="editor" datatype="date" dropdown_mode="date">，这里为该input对象.
 */
function showDropDownBox(_editor){
	try{
		if (!canDropDown(_editor)) return;
		if (!isDropdownBoxVisible()){
			// 一般为_editor的id + "_" + "beforeDropDown"
			var eventName=getElementEventName(_editor, "beforeDropDown");
			/** 
			 * eventName:要执行的函数名  [_editor]:对象参数数组
			 * 返回：函数的执行结果
			 */
			var event_result=fireUserEvent(eventName, [_editor]);
		    // 这里没有执行，只是预留一个在beforeDropDown时的处理工作
			if (event_result) throw event_result;

			getDropDownBox(_editor);

			_dropdown_box.prepared=false;
			if (_dropdown_box.filters.blendTrans.status==2) return;
			var dataset=getElementDataset(_editor);
			if (dataset){
				if (!dataset.record) dataset.insertRecord();
			}
	
			with (_dropdown_box){
				style.overflowY="hidden";
				setAttribute("editor", _editor);

				var dropDown_mode=_editor.getAttribute("dropDown_mode");
				setAttribute("dropDown_mode", dropDown_mode);

				switch (dropDown_mode){
					case "sql":;
					case "custom":{
						style.visibility="visible";
						if (_editor.offsetWidth>128)
							style.width=editor.offsetWidth
						else
							style.width=128;
						break;
					}

					default:{
						if (filters.blendTrans.status!=2) {
							if (!(getIEVersion()<"5.5")) filters.blendTrans.apply();
							style.visibility="visible";
						}
						break;
					}
				}
				var dataType = _editor.getAttribute("datatype");		
				if (!_dropdown_box.cached){
					switch (dropDown_mode){
						case "sql":{
							showStatusLabel(window, constDownLoadingData, _editor);
							var sql=_editor.getAttribute("dropdown_sql");
							var connName = editor.getAttribute("dropdown_connname");
							// added by steve_gu at 2004-05-17
							var displayFields = editor.getAttribute("displayfields");
							var haspilotbar = editor.getAttribute("haspilotbar");
							// end add
							if (!connName && dataset) connName=dataset.connName;
							if (sql && connName){
								/*
								_dropdown_box.innerHTML="<IFRAME height=0 frameborder=0 marginheight=0 marginwidth=0 scrolling=no"+
									" src='"+Ces_Library_Common_path+"dbdropdown.jsp?sql="+escape(sql)+"&connName="+escape(connName)+"&tableName="+_editor.getAttribute("dropDown_tableName")+"&pageSize="+getInt(editor.getAttribute("dropdown_pageSize"))+
									"&fields="+getValidStr(_editor.getAttribute("dropDown_fields"))+"&datafield="+getValidStr(_editor.getAttribute("datafield"))+"&value="+_editor.value+"'"+
									" style='position:absolute; visibility:hidden; border-style: none; filter: blendTrans(duration=0.3)'></IFRAME>";
								*/
								_dropdown_box.innerHTML="<IFRAME height=0 frameborder=0 marginheight=0 marginwidth=0 scrolling=no"+
									" src='"+Ces_Library_Common_path+"dbdropdown.jsp?sql="+escape(sql)+"&connName="+escape(connName)+"&pageSize="+getInt(editor.getAttribute("dropdown_pagesize"))+
									"&fields="+getValidStr(_editor.getAttribute("dropDown_fields"))+"&datafield="+getValidStr(_editor.getAttribute("datafield"))+"&value="+_editor.value+"&displayfields=" + displayFields + "&haspilotbar=" + haspilotbar + "'"+
									" style='position:absolute; visibility:hidden; border-style: none; filter: blendTrans(duration=0.3)'></IFRAME>";
								_dropdown_frame=_dropdown_box.firstChild;
								// debugged by steve_gu at 2004-06-04
								/*
								_dropdown_box.style.border = "1px solid red";
								alert("_dropdown_box.tagName = " + _dropdown_box.tagName);
								alert("_dropdown_frame = " + _dropdown_frame.tagName);
								*/
								// end debug
							}
							break;
						}

						case "custom":{
							//initDropDownBox(dropDown_mode);
							showStatusLabel(window, constDownLoadingData, _editor);
							
							_dropdown_box.innerHTML="<IFRAME height=0 frameborder=0 marginheight=0 marginwidth=0 scrolling=no"+
								" src='"+_editor.getAttribute("dropDown_url")+"'"+
								" style='overflow: hidden; position:absolute; visibility:hidden; border-style: none; filter: blendTrans(duration=0.3)'></IFRAME>";
							_dropdown_frame=_dropdown_box.firstChild;
							// debugged by steve_gu at 2004-06-04
							//_dropdown_box.style.border = "1px solid red";							
							// alert("_dropdown_box.tagName = " + _dropdown_box.tagName);
							// alert("_dropdown_frame = " + _dropdown_frame.tagName);
							// end debug
							/*****
							_dropdown_box.innerHTML="<IFRAME height=100 frameborder=0 marginheight=0 marginwidth=0 scrolling=no"+
								" src='"+_editor.getAttribute("dropDown_url")+"?value="+_editor.value+"'"+
								" style='overflow: hidden; position:absolute; visibility:true; border-style: none; filter: blendTrans(duration=0.3)'></IFRAME>";
							_dropdown_box.innerHTML+="sssssssss";
							******/
							break;
						}

						case "date":{
							// 只执行一次，然后cached.
							createCalendar(_dropdown_box);
							initDropDownBox(dropDown_mode);
							// _dropdown_box.onkeydown=_calendar_onkeydown;
							break;
						}

						case "yearmonth": {
							createYearMonth(_dropdown_box);
							initDropDownBox(dropDown_mode);							
							break;
						}

						default:{
							style.width=_editor.offsetWidth;
							createListTable(_dropdown_box);
							_dropdown_table.onkeydown=_dropdown_onkeydown;

							if (dropDown_mode=="list")
								_dataset=getDropDownItems(editor);
							else{
								_dataset=editor.getAttribute("dropDown_dataset");
								if (typeof(_dataset)=="string") _dataset=getDatasetByID(_dataset);
							}

							if (_dataset){
								setElementDataset(_dropdown_table, _dataset);
								_dropdown_table.fields=editor.getAttribute("dropDown_fields");
								initElements(_dropdown_table);
								refreshTableData(_dropdown_table);
							}
							initDropDownBox(dropDown_mode);
							break;
						}
					}

				}
				else{
					var dropdown_saver=getDropDownCacheSaver(_editor);					
					switch (dropDown_mode){
						case "sql":;
						case "custom":{
							_dropdown_frame=_dropdown_box.firstChild;
							var _window=dropdown_saver.cached_dropdown_window;
							_window.initDropDownBox(dropDown_mode);
							break;
						}
						// added by steve_gu at 2004-02-02 每一次都刷新
						case "date": {
							createCalendar(_dropdown_box);
							initDropDownBox(dropDown_mode);
							break;
						}
						case "yearmonth": {
							createYearMonth(_dropdown_box);
							initDropDownBox(dropDown_mode);							
							break;
						}
						default:{
							if (getIEVersion()<"5.5"){
								for (var i=0; i<_dropdown_box.children.length; i++){
									_dropdown_box.children[i].style.visibility="visible";
								}
							}
							_dropdown_table=dropdown_saver.cached_dropdown_table;
							initDropDownBox(dropDown_mode);
							break;
						}
					}

				}
			}
			// added by steve_gu at 2003-12-25
			var textTime = document.getElementById("text_time");
			if (textTime != "undefined" && dataType=="date"){				
				textTime.style.display = "none";
			}
			if (textTime != "undefined" && dataType=="datetime"){	
				textTime.style.display = "";
			}
			//下拉按钮可见
			_editor.dropDown_visible=true;
			//HideElement("select");
			if  (typeof(_dropdown_btn)!="undefined") _dropdown_btn.value="5";
		}
	}
	catch(e){
		processException(e);
	}
}

function hideDropDownBox(){
	if (!_dropdown_box) return;
	if (isDropdownBoxVisible()){
		_skip_activeChanged=true;
		var editor=_dropdown_box.editor;
		if (_dropdown_box.prepared && (isTrue(editor.getAttribute("dropDown_cached")) ||
			_dropdown_box.getAttribute("dropDown_mode")=="date" ||  _dropdown_box.getAttribute("dropDown_mode")=="yearmonth")){
			var dropdown_saver=getDropDownCacheSaver(editor);
			dropdown_saver.cached_dropdown_mode=_dropdown_box.getAttribute("dropDown_mode");
			dropdown_saver.cached_dropdownbox=_dropdown_box;
			_dropdown_box.cached=true;
			switch (_dropdown_box.getAttribute("dropDown_mode")){
				case "list":{
					dropdown_saver.cached_dropdown_items=editor.getAttribute("dropdown_items");
					dropdown_saver.cached_dropdown_table=_dropdown_table;
					break;
				}
				case "dataset":{
					dropdown_saver.cached_dropdown_dataset=editor.getAttribute("dropdown_dataset");
					dropdown_saver.cached_dropdown_table=_dropdown_table;
					break;
				}
				case "sql":{
					dropdown_saver.cached_dropdown_connName="222"//editor.getAttribute("dropdown_connName");
					dropdown_saver.cached_dropdown_sql=editor.getAttribute("dropdown_sql");
					dropdown_saver.cached_dropdown_window=_dropdown_window;
					break;
				}
				case "custom":{
					dropdown_saver.cached_dropdown_url=editor.getAttribute("dropdown_url");
					dropdown_saver.cached_dropdown_window=_dropdown_window;
					break;
				}
				case "date":{
					_date_dropdown_box=_dropdown_box;
					break;
				}
				case "yearmonth":{
					_date_dropdown_box=_dropdown_box;
					break;
				}
			}

			if (getIEVersion()<"5.5"){
				for (var i=0; i<_dropdown_box.children.length; i++){
					_dropdown_box.children[i].style.visibility="hidden"
				}
			}
			_dropdown_box.style.visibility="hidden";
			_dropdown_window=null;
		}
		else{
			_dropdown_box.editor=null;
			switch (_dropdown_box.getAttribute("dropDown_mode")){
				case "list":
				case "dataset":{
					setElementDataset(_dropdown_table, null);
					break;
				}
				case "sql":;
				case "custom":{
					if (typeof(_dropdown_frame)!="undefined" && _dropdown_frame){
						//_dropdown_frame.style.visibility="hidden";
						_dropdown_frame.removeNode(true);
					}
					break;
				}
			}
			editor.cached_dropdownbox=null;
			_dropdown_window=null;

			if (getIEVersion()<"5.5"){
				for (var i=0; i<_dropdown_box.children.length; i++){
					_dropdown_box.children[i].style.visibility="hidden"
				}
			}
			_dropdown_box.style.visibility="hidden";
			_dropdown_box.removeNode(true);
			_dropdown_box=null;
		}

		editor.dropDown_visible=false;
		if  (typeof(_dropdown_btn)!="undefined") _dropdown_btn.value="6";
	}
}

function isDropDownBtnVisible(){
	if  (typeof(_dropdown_btn)!="undefined")
		return (_dropdown_btn.style.visibility=="visible")
	else
		return false;
}
//added by jdh at 20040202
function _sizeDropDownBtn(_editor){
	
	//if (!isDropDownBtnVisible()) return;
	if(!_editor.bind_btn) return;
	with (_editor.bind_btn){
		var pos=getAbsPosition(_editor);

		style.height=_editor.offsetHeight-5;
		style.width=16;
		style.posLeft=pos[0]+_editor.offsetWidth-offsetWidth-1;
		style.posTop=pos[1]+1;
	}
}
//end added 
function sizeDropDownBtn(_editor){
	
	if (!isDropDownBtnVisible()) return;
	with (_dropdown_btn){
		var pos=getAbsPosition(_editor);

		style.height=_editor.offsetHeight-2;
		style.width=16;
		style.posLeft=pos[0]+_editor.offsetWidth-offsetWidth-1;
		style.posTop=pos[1]+1;
	}
}


//added by jdh at 20040202
//function _getStar(element){
//	if(element.bind_star){
//		return element.bind_star;
//	}else{
//		obj=document.createElement("<div");
//	}
//}
function _getDropDownBtn(element){
	if  (element.bind_btn){
		return element.bind_btn;
	}
	else{
		// _dropdown_btn is defined here, as the same time, the event is registerd.
		 var btn_id=element.id+"_btn";
		obj=document.createElement("<INPUT id="+btn_id+" type=button tabindex=-1 value=6 hidefocus=true"+
			" style='position: absolute; visibility: hidden; border: whitesmoke 1px solid; font-family: Marlett; font-size: 10px; cursor: hand; z-index: 9999'"+
			" LANGUAGE=javascript onmousedown='return _dropdown_btn_onmousedown(this)' onfocus='return _dropdown_btn_onfocus(this)'>");
		obj.style.background = "url("+Ces_Library_path+"images/dropdown_button.gif)";
		document.body.appendChild(obj);
		element.insertAdjacentElement("afterEnd",obj);
		return obj;
	}
}
function _showDropDownBtn(_editor){
	if (!canDropDown(_editor)) {
		return;
	}
	// 保证_dropdown_btn不为空
	var _bind_dropdown_btn=_getDropDownBtn(_editor);
	if (typeof(_bind_dropdown_btn)=="undefined") return;
	//bind btn and editor
	_editor.bind_btn=_bind_dropdown_btn;
	with (_bind_dropdown_btn){
		//if (!isDropDownBtnVisible()){
			setAttribute("editor", _editor);
			
			style.visibility="visible";

			_sizeDropDownBtn(_editor);
//
			var oldWidth=_editor.offsetWidth;
			_editor.style.borderRightWidth=18;
			_editor.style.width=oldWidth;
		//}
	}
}
//end added 
function showDropDownBtn(_editor){
	//alert("show");
	if (!canDropDown(_editor)) {
		return;
	}
	// 保证_dropdown_btn不为空
	getDropDownBtn();
	if (typeof(_dropdown_btn)=="undefined") return;
	with (_dropdown_btn){
		if (!isDropDownBtnVisible()){
			setAttribute("editor", _editor);
			style.visibility="visible";
			// added by steve_gu at 2004-06-01
			style.display = "inline";
			// end add
			sizeDropDownBtn(_editor);
			var oldWidth=_editor.offsetWidth;
			_editor.style.borderRightWidth=18;
			_editor.style.width=oldWidth;
		}
	}
}

function hideDropDownBtn(){
	if  (typeof(_dropdown_btn)=="undefined") return;

	if (isDropDownBtnVisible()){
		var _editor=_dropdown_btn.editor;
		if (_editor){
			var oldWidth=_editor.offsetWidth;
			_editor.style.borderRightWidth=1;
			_editor.style.width=oldWidth;
		}
		_dropdown_btn.style.visibility="hidden";
		_dropdown_btn.editor=null;
	}
}

function _dropdown_btn_onmousedown(button){
	// 即为dropdown_btn所属的编辑框， 参见setAttribute("editor", _editor); in showDropDownBtn()
	var obj=button.editor;
	if (!isDropdownBoxVisible()){		
		if (obj) showDropDownBox(obj);
	}
	else {
		hideDropDownBox();
	}
}

function _dropdown_btn_onfocus(button){
	var obj=button.editor;
	if (obj) obj.focus();
}

function createListTable(parent_element){
	_dropdown_table=document.createElement("<table attrib=datatable isDropDownTable=true readOnly=true width=100% "+
		" border=1 bordercolor=silver cellspacing=0 cellpadding=2 rules=all ></table>");

	if (parent_element)
		parent_element.appendChild(_dropdown_table);
	else
		document.body.appendChild(_dropdown_table);
}

function dropDownLocate(){
	
	var editor=_dropdown_parentbox.editor;
	switch (editor.getAttribute("dropDown_mode")){
		case "date":{
			// modified by steve_gu at 2004-02-17
			var editorValue = editor.value;
			if (editor.getAttribute("separator")) {
				editorValue = editorValue.replace(editor.getAttribute("separator"),"/");
			}
			else {
				editorValue = editorValue.replace("-","/");
			}
			var _date=new Date(editorValue);
			if (!isNaN(_date)) setCalendarDate(_date);
			break;
		}
		case "yearmonth":{
			var editorValue = editor.value;
			if (editor.getAttribute("separator")) {
				editorValue = editorValue.replace(editor.getAttribute("separator"),"/");
			}
			else {
				editorValue = editorValue.replace("-","/");
			}
			// added by steve_gu at 2004-03-05
			editorValue += "/01";
			var _date = new Date(editorValue);
			if (!isNaN(_date)) setYearMonth(_date);
			break;
		}
		default:{
			if (_dropdown_dataset){
				var fieldName;

				if (editor.getAttribute("dropDown_mode")=="list"){
					fieldName=(editor.getAttribute("dropDown_mapValue"))?"name":"value";
				}
				else{
					if (editor.use_keyField && editor.getAttribute("keyField")){
						fieldName=editor.getAttribute("dropDown_keyField");
						if (!fieldName) fieldName=editor.getAttribute("keyField");
					}
					else{
						fieldName=editor.getAttribute("dropDown_dataField");
						if (!fieldName) fieldName=editor.getAttribute("dataField");
					}
				}
				var value=editor.value;
				// modified by steve_gu at 2004-05-19
				if (_dropdown_dataset.locate) var record=_dropdown_dataset.locate(fieldName, value);
				if (record) _dropdown_dataset.setRecord(record);
			}
			break;
		}
	}
}

function hideDropDown() {
	var editor=_dropdown_parentbox.editor;
	_dropdown_parentwindow.hideDropDownBox();
	editor.focus();
}

function _standard_dropdown_keyDown(keycode){
	switch(keycode){
		//PageUp
		case 33:{
			var pageIndex=(_dropdown_dataset.record)?_dropdown_dataset.record.pageIndex-1:1;
			_dropdown_dataset.moveToPage(pageIndex);
			break;
		}
		//PageDown
		case 34:{
			var pageIndex=(_dropdown_dataset.record)?_dropdown_dataset.record.pageIndex+1:1;
			_dropdown_dataset.moveToPage(pageIndex);
			break;
		}
		//Up
		case 38:{
			if (_dropdown_dataset){
				_dropdown_dataset.movePrev();
			}
			break;
		}
		//Down
		case 40:{
			if (_dropdown_dataset){
				_dropdown_dataset.moveNext();
			}
			break;
		}
	}
}

function processDropDownKeyDown(keycode) {
	switch(keycode){
		//Enter
		case 13:{
			dropDownSelected();
			break;
		}
		//ESC
		case 27:{
			hideDropDown();
			break;
		}
		//F2
		case 113:{
			hideDropDown();
			break;
		}
		//F7
		case 118:{
			hideDropDown();
			break;
		}
		default:{
			var editor=_dropdown_parentbox.editor;
			switch (editor.getAttribute("dropDown_mode")){
				case "list":
				case "dataset":
				case "sql":{
					_standard_dropdown_keyDown(keycode);
					break;
				}
				case "date":{
					_calendar_onkeydown();
					break;
				}
				default:{
					if (typeof(dropDown_onKeyDown)!="undefined") dropDown_onKeyDown(keycode);
					break;
				}
			}
		}
	}
}
//added by jdh at 20040117 
//for event of list after selected
var DropDownHandler={};

/***
 * 用于给隐藏域赋值后的操作
 * jdh 20040117
 * 隐藏域的值
 * **/
 DropDownHandler.dropDownAfterSelected=function(obj){
 	/** todo  **/
 }
/**
 * 下拉框的内容被选中执行的操作
 */
function dropDownSelected(){
	var record;
	var editor=_dropdown_parentbox.editor;
	// added by steve_gu at 2003-12-26
	var dataType = editor.getAttribute("datatype");
	switch (editor.getAttribute("dropDown_mode")){
		case "list":{
			/**** added by jdh 20040117 ****/
			//var name=_dropdown_parentbox.editor.name;
			//name=name.replace("Select","")
			//eval("document.all."+name).value=_dropdown_dataset.record.getValue("value");
			//DropDownHandler.dropDownAfterSelected(_dropdown_dataset.record.getValue("value"));
		}
		case "dataset":
		case "sql":{
			if (_dropdown_dataset) record=_dropdown_dataset.record;
			break;
		}
		case "date":{			

			_tmp_dataset_date=createDataset("_tmp_dataset_date");
			_tmp_dataset_date.addField("value");
			initDataset(_tmp_dataset_date);
			_tmp_dataset_date.insertRecord();
			// _tmp_dataset_date.setValue("value", new Date(_calendarControl.year, _calendarControl.month, _calendarControl.day));
			// modified by steve_gu at 2003-12-26
			// 增加上下限验证
			var strMinDate = editor.getAttribute("minDate");
			var strMaxDate = editor.getAttribute("maxDate");
			var minDate = null;
			var maxDate = null;
			var selectedDate = new Date(_calendarControl.year, _calendarControl.month, _calendarControl.day,_calendarControl.hour, _calendarControl.minute, _calendarControl.second);			
			
			var strSeparate = "-";
			if(editor.getAttribute("separator")){
				strSeparate = editor.getAttribute("separator");
			}

			if (strMinDate != null) {
				if (dataType == "date") {
				    // 将yyyy-mm-dd变为yyyy/mm/dd
				    // todo:采用正则表达式
					strMinDate = strMinDate.replace(strSeparate,"/");
					strMinDate = strMinDate.replace(strSeparate,"/");
					minDate = new Date(strMinDate);
					//minDate = minDate.setDate(minDate.getDate + 1);					
				} 
				if (dataType == "datetime") {
					//new Date("August 25, 1975 12:30:00")
					//转换yyyy-mm-dd hh:mm:ss为month day, year hours:minutes:seconds格式
				     var minMonth = "pre" + strMinDate.substring(5,7);
				     var strMinDatetime = constMonthMapping[minMonth] + " " + strMinDate.substring(8, 10) + " " +
				     	strMinDate.substring(0,4) + " " + strMinDate.substring(11);
				     minDate = new Date(strMinDatetime);
				}			
				if (selectedDate < minDate) {			
					alert(constErrDateOutOfLowerRange.replace("%minDate", minDate.toLocaleString()));
					hideDropDown();
					return;				
				}
			}			

			if (strMaxDate != null) {
				if (dataType == "date") {
				    // 将yyyy-mm-dd变为yyyy/mm/dd
				    // todo:采用正则表达式
					strMaxDate = strMaxDate.replace(strSeparate,"/");
					strMaxDate = strMaxDate.replace(strSeparate,"/");
					maxDate = new Date(strMaxDate);
					// added by steve_gu at 
					// maxDate = maxDate.setDate(maxDate.getDate + 1);
				}
				if (dataType == "datetime") {
					//new Date("August 25, 1975 12:30:00")
					//转换yyyy-mm-dd hh:mm:ss为month day, year hours:minutes:seconds格式
				     var maxMonth = "pre" + strMaxDate.substring(5,7);
				     var strMaxDatetime = constMonthMapping[maxMonth] + " " + strMaxDate.substring(8, 10) + " " +
				     	strMaxDate.substring(0,4) + " " + strMaxDate.substring(11);
				     maxDate = new Date(strMaxDatetime);				
				}
				
				if (selectedDate > maxDate) {					
					alert(constErrDateOutOfUpperRange.replace("%maxDate", maxDate.toLocaleString()));
					hideDropDown();
					return;				
				}				
			}
			// end of add
			_tmp_dataset_date.setValue("value", new Date(_calendarControl.year, _calendarControl.month, _calendarControl.day,_calendarControl.hour, _calendarControl.minute, _calendarControl.second));
			_tmp_dataset_date.updateRecord();
			record=_tmp_dataset_date.record;
			break;
		}
		case "yearmonth": {
			_tmp_dataset_date=createDataset("_tmp_dataset_date");
			_tmp_dataset_date.addField("value");
			initDataset(_tmp_dataset_date);
			_tmp_dataset_date.insertRecord();		
			_tmp_dataset_date.setValue("value", new Date(_calendarControl.year, _calendarControl.month -1,1,1,1,1));
			_tmp_dataset_date.updateRecord();			
			record=_tmp_dataset_date.record;
			break;
		}
		default:{
			if (typeof(dropDown_onGetRecord)!="undefined") record=dropDown_onGetRecord();
			break;
		}
	}

	if (record){
		// set the selected value into the editor
		_dropdown_parentwindow.processDropDownSelected(_dropdown_parentbox.editor, record, false);
		hideDropDown();
	}
	if (_tmp_dataset_date) freeDataset(_tmp_dataset_date);
	// added by steve_gu at 2004-02-14 选中后隐藏popup
	oPopup.show();
}

function _dropdown_onkeydown(){
	processDropDownKeyDown(event.keyCode);
}

function _dropdown_onclick(){
	dropDownSelected();
	event.cancelBubble=true;
}

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------*/

var _calendar_days;

function _calendar_year_onpropertychange(){
	if (!_calender_year.processing && event.propertyName=="value"){
		if (_calender_year.value.length==4){
			_calender_year.processing=true;	
			changeCalendarDate(getInt(_calender_year.value), _calendarControl.month);
			_calender_year.processing=false;	
		}
	}
}

function _calendar_month_onpropertychange(){
	if (!_calender_month.processing && _activeElement==_calender_month && event.propertyName=="value"){
		if (_calender_month.value.length>0){	
			_calender_month.processing=true;	
			changeCalendarDate(_calendarControl.year, getInt(_calender_month.value-1));
			_calender_month.processing=false;
		}
	}
}

var _calendarControl;

function calendar(){
	var today=new Date()
	this.todayDay=today.getDate();
	this.todayMonth=today.getMonth();
	this.todayYear=today.getFullYear();
	// added by steve_gu at 2003-12-26, add the time
	this.thisHour = today.getHours();
	this.thisMinute = today.getMinutes();
	this.thisSecond = today.getSeconds();
	// 
	this.activeCellIndex=0;
}

function createCalendar(parent_element){
	_calendar_days=new Array(constSunday, constMonday, constTuesday, constWednesday, constThursday, constFriday, constSaturday);
	_calendarControl=new calendar();

	var tmpHTML="";
	
	tmpHTML+="<TABLE border=0 id='CalendarTable' class='calendar' rule=all>";
		// 年月导航
		tmpHTML+="<TR class=title><TD>";			
			tmpHTML+="<TABLE>";
				tmpHTML+="<TR><TD class='buttonLastYear'>";
				tmpHTML+="<INPUT type=button id='buttonLastYear' attrib=button value=3 title='"+constLastYear+"'  onclick='changeCalendarDate(_calendarControl.year-1,_calendarControl.month)'>";
				tmpHTML+="</TD><TD class='textYear'>";
				tmpHTML+="<INPUT id='_calender_year' type=text class=editor size=4 maxlength=4 onpropertychange='return _calendar_year_onpropertychange()'>";
				tmpHTML+="</TD><TD class='buttonNextYear'>";
				tmpHTML+="<INPUT type=button id='buttonNextYear' attrib=button value=4 title='"+constNextYear+"' onclick='changeCalendarDate(_calendarControl.year+1,_calendarControl.month)'>";
				tmpHTML+="</TD>";
				tmpHTML+="<TD class='buttonLastMonth'>";
				tmpHTML+="<INPUT type=button id='buttonLastMonth' attrib=button value=3 title='"+constLastMonth+"' onclick='changeCalendarDate(_calendarControl.preYear,_calendarControl.preMonth)'>";
				tmpHTML+="</TD><TD width=1>";
				tmpHTML+="<INPUT id='_calender_month' type=text class=editor size=2 maxlength=2 onpropertychange='return _calendar_month_onpropertychange()'>";
				tmpHTML+="</TD><TD class='buttonNextMonth'>";
				tmpHTML+="<INPUT type=button id='buttonNextMonth' attrib=button value=4 title='"+constNextMonth+"' onclick='changeCalendarDate(_calendarControl.nextYear,_calendarControl.nextMonth)'>";
				tmpHTML+="</TD></TR>";
			tmpHTML+="</TABLE>"
		tmpHTML+="</TD></TR>";
		// end of 年月导航
		// 星期和日子
		tmpHTML+="<TR class=data><TD>";
			// 所有的日子对象通过id为calendarData的table的cell来获得
			tmpHTML+="<TABLE id='calendarData' class='calendarCell'";
				tmpHTML+="onclick='_calendar_cell_onclick(event.srcElement)'>";
				tmpHTML+="<TR class='calendarWeek'>";
				// week
				for (var i=0;i<=6;i++){
					tmpHTML+="<TD>"+_calendar_days[i]+"</TD>";
				}
				tmpHTML+="</TR>";
				// Days, 6 rows, 7 cols 
				for(var i=0;i<=5;i++){
					tmpHTML+="<TR class='calendarDay'>";
					for(var j=0;j<=6;j++){
						tmpHTML+="<TD></TD>";
					}
					tmpHTML+="</TR>";
				}
			tmpHTML+="</TABLE>";
		tmpHTML+="</TD></TR>";
		// end of 星期和日子
		// modified by steve_gu at 2003-12-25
		var today = new Date();
		var curTime = formatDateTime(today, "time");
		//tmpHTML+="<tr id='text_time' style='display=yes'><td>" + constTime + "<INPUT value='00:00:00' size='8' attrib='editor' datatype='int'></td></tr>";
		tmpHTML+="<tr id='text_time' class=footer style='display=yes'><td class='time'>" + constTime + "<INPUT id='input_time' value='" + curTime + "' size='8' attrib='editor' onchange='processTimeChanged();'></td></tr>";
		// end of modify
		// today
		tmpHTML+="<TR class=footer><TD>";
		tmpHTML+="<INPUT class='button' type=button id='buttonToday' value='"+constToday+" "+_calendarControl.todayYear+"-"+(_calendarControl.todayMonth+1)+"-"+_calendarControl.todayDay+"' onclick='_calendar_today_onclick()' ";
		tmpHTML+="</TD></TR>";
	tmpHTML+="</TABLE>";
	if (parent_element)
		parent_element.innerHTML=tmpHTML;
	else
		document.body.innerHTML=tmpHTML;
	// init 5 button's face
	initElements(CalendarTable);
	// 第一次打开时设为今天的年月日
	changeCalendarDate(_calendarControl.todayYear,_calendarControl.todayMonth,_calendarControl.todayDay)
	// added by steve_gu at 2003-12-26
	changeCalendarTime(_calendarControl.thisHour, _calendarControl.thisMinute, _calendarControl.thisSecond);
}

/**
 * added by steve_gu at 2004-03-03
 */
function createYearMonth(parent_element) {
	_calendar_days=new Array(constSunday, constMonday, constTuesday, constWednesday, constThursday, constFriday, constSaturday);
	_calendarControl=new calendar();

	var tmpHTML="";
	
	tmpHTML+="<TABLE border=0 id='CalendarTable' class='calendar' rule=all>";
		// 年月导航
		tmpHTML+="<TR class=title><TD>";			
			tmpHTML+="<TABLE>";
				tmpHTML+="<TR><TD class='buttonLastYear'>";
				tmpHTML+="<INPUT type=button id='buttonLastYear' attrib=button value=3 title='"+constLastYear+"'  onclick='var newValue=_calender_year.value - 1; _calender_year.value=newValue;'>";
				tmpHTML+="</TD><TD class='textYear'>";
				tmpHTML+="<INPUT id='_calender_year' type=text size=12 class=editor  maxlength=4 >";
				tmpHTML+="</TD><TD class='buttonNextYear'>";
				tmpHTML+="<INPUT type=button id='buttonNextYear' attrib=button value=4 title='"+constNextYear+"' onclick='var newValue=_calender_year.value-1+2; _calender_year.value=newValue;'>";			
				tmpHTML+="</TD></TR>";
			tmpHTML+="</TABLE>"
		tmpHTML+="</TD></TR>";
		// end of 年月导航
		// 星期和日子
		tmpHTML+="<TR class=data><TD>";
			// 所有的日子对象通过id为calendarData的table的cell来获得
			tmpHTML+="<TABLE id='calendarData' class='monthCell'";
				tmpHTML+="onclick='_month_cell_onclick(event.srcElement)'>";
				tmpHTML+="<tr class='calendarMonth'><td>一月</td><td>二月</td><td>三月</td><td>四月</td></tr>";
				tmpHTML+="<tr class='calendarMonth'><td>五月</td><td>六月</td><td>七月</td><td>八月</td></tr>";
				tmpHTML+="<tr class='calendarMonth'><td>九月</td><td>十月</td><td>十一月</td><td>十二月</td></tr>";
				
			tmpHTML+="</TABLE>";
		tmpHTML+="</TD></TR>";
		// end of 星期和日子

		// today
		tmpHTML+="<TR class=footer><TD>";
		tmpHTML+="<INPUT class='button' type=button id='buttonToday' value='"+"本年月"+" "+_calendarControl.todayYear+"-"+(_calendarControl.todayMonth+1)+"' onclick='_yearmonth_today_onclick()' ";
		tmpHTML+="</TD></TR>";
	tmpHTML+="</TABLE>";
	if (parent_element)
		parent_element.innerHTML=tmpHTML;
	else
		document.body.innerHTML=tmpHTML;
	// init 5 button's face
	initElements(CalendarTable);
	// 第一次打开时设为今天的年月
	changeYearMonth(_calendarControl.todayYear,_calendarControl.todayMonth);	
}

function changeYearMonth(year, month) {
	//var tmpMonth = month - 1;
	if (_calendarControl.year==year && _calendarControl.month==month) return;

	if (_calendarControl.year!=year || _calendarControl.month!=month){
		_calendarControl.year=year;
		_calendarControl.month=month;
		// 设置年的内容
		_calender_year.value=_calendarControl.year;
		var datenum=0;
		// 设置月份风格
		for (var i=0;i<=11;i++){
			var cell = calendarData.cells[i];			
			cell.className = "cur";	
		}
	}	
	setActiveMonthCell(calendarData.cells[_calendarControl.month]);
}

function setActiveMonthCell(cell){
	if (cell.tagName.toLowerCase()!="td") return;
	var _activeCellIndex=cell.parentElement.rowIndex*4+cell.cellIndex;
	cell.className = "selected";
	_calendarControl.year = document.getElementById("_calender_year").value;
	_calendarControl.month = _activeCellIndex + 1;
	_calendarControl.activeCellIndex=_activeCellIndex;
}

function setCalendarDate(date){
	changeCalendarDate(date.getFullYear(),date.getMonth(),date.getDate());
}

function setYearMonth(date) {
	changeYearMonth(date.getFullYear(),date.getMonth());
}

/**
 * added by steve_gu at 2003-12-26
 */
function changeCalendarTime(hour, minute, second) {
	_calendarControl.hour = hour;
	_calendarControl.minute = minute;
	_calendarControl.second = second;
}

/**
 * added by steve_gu at 2003-12-26
 */
function processTimeChanged() {
	var temValue = document.all["input_time"].value;
	// todo: 有效性验证
	//23:24:12
	_calendarControl.hour = temValue.substring(0, 2);
	_calendarControl.minute = temValue.substring(3, 5);
	_calendarControl.second = temValue.substring(6, 8);
}

function changeCalendarDate(year, month, day){
	if (_calendarControl.year==year && _calendarControl.month==month && (!day || _calendarControl.day==day)) return;

	if (_calendarControl.year!=year || _calendarControl.month!=month){
		_calendarControl.year=year;
		_calendarControl.month=month;

		if (month==0){
			 _calendarControl.preMonth=11
			 _calendarControl.preYear=_calendarControl.year-1
		}else{
			 _calendarControl.preMonth=_calendarControl.month-1
			 _calendarControl.preYear=_calendarControl.year
		}
		if (month==11){
			_calendarControl.nextMonth=0
			_calendarControl.nextYear=_calendarControl.year+1
		}else{
			_calendarControl.nextMonth=_calendarControl.month+1
			_calendarControl.nextYear=_calendarControl.year

		}
		_calendarControl.startday=(new Date(year,month,1)).getDay()
		if (_calendarControl.startday==0) _calendarControl.startday=7
		var curNumdays=getNumberOfDays(_calendarControl.month,_calendarControl.year)
		var preNumdays=getNumberOfDays(_calendarControl.preMonth,_calendarControl.preYear)
		var nextNumdays=getNumberOfDays(_calendarControl.nextMonth,_calendarControl.nextYear)
		var startDate=preNumdays-_calendarControl.startday+1
		var endDate=42-curNumdays-_calendarControl.startday
		// 设置月的内容
		_calender_month.value=(_calendarControl.month+1);
		// 设置年的内容
		_calender_year.value=_calendarControl.year;
		var datenum=0;

		// 设置本月之前的日子及风格
		for (var i=startDate;i<=preNumdays;i++){
			var cell = calendarData.cells[datenum+7];
			cell.monthAttribute="pre";
			// modified by steve_gu at 2004-02-18, 全部用css来动态控制
			cell.className = "pre";
			cell.innerText=i;
			datenum++;
		}
		//var index = 0;
		// 设置本月的日子及风格
		for (var i=1;i<=curNumdays;i++){
			var cell = calendarData.cells[datenum+7];
			cell.monthAttribute="cur";
			if (datenum != _calendarControl.activeCellIndex){
				cell.className = "cur";
			}
			else {
			}
			cell.innerText=i;
			datenum++;
		}

		// 设置本月之后的日子及风格
		for (var i=1;i<=endDate;i++){
			var cell = calendarData.cells[datenum+7];
			cell.monthAttribute="next";
			cell.className = "next";
			cell.innerText=i;
			datenum++;
		}
	}

	if (day) _calendarControl.day=day;
	setCalendarActiveCell(calendarData.cells[_calendarControl.day+_calendarControl.startday-1+7]);
}

/**
 * 设置当前的日子的风格,这里的cell为table中的td
 */
function setCalendarActiveCell(cell){

	function setActiveCell(cellIndex){
		var cell = calendarData.cells[_calendarControl.activeCellIndex+7];		
		if (cell.monthAttribute=="cur"){
			cell.className = "cur";
		}
		else{
			// pre和next风格一致
			cell.className = "pre";
		}
		
		var cell = calendarData.cells[cellIndex+7];
		cell.className = "selected";
		_calendarControl.activeCellIndex=cellIndex;
	}

	if (cell.tagName.toLowerCase()!="td") return;
	var _activeCellIndex=cell.parentElement.rowIndex*7+cell.cellIndex-7;

	with(_calendarControl){
		if (activeCellIndex==_activeCellIndex) return;

		var monthAttribute=cell.monthAttribute;
		switch (monthAttribute){
			case "pre":{
				changeCalendarDate(preYear,preMonth,getNumberOfDays(preMonth,preYear)-startday+_activeCellIndex+1);
				setActiveCell(startday+day-1);
				break
			}
			case "cur":{
				changeCalendarDate(year,month,_activeCellIndex-startday+1);
				setActiveCell(_activeCellIndex);
				break
			}
			case "next":{
				changeCalendarDate(nextYear,nextMonth,_activeCellIndex-getNumberOfDays(month,year)-startday+1);
				setActiveCell(startday+day-1);
				break
			}
		}
	}
}

/** 
 * added by steve_gu at 2004-03-04
 */
function _month_cell_onclick(cell){
	setActiveMonthCell(cell);
	dropDownSelected();
}


/**
 * 点击了日子后执行的操作
 */
function _calendar_cell_onclick(cell){
	setCalendarActiveCell(cell)
	dropDownSelected()
}

function _calendar_onkeydown(){
	switch(event.keyCode){
		case 33:{//PgUp
			if (event.ctrlKey){
				changeCalendarDate(_calendarControl.year-1,_calendarControl.month)
			}else{
				changeCalendarDate(_calendarControl.preYear,_calendarControl.preMonth)
			}
			break
		}
		case 34:{//PgDn
			if (event.ctrlKey){
				 changeCalendarDate(_calendarControl.year+1,_calendarControl.month)
			}else{
				 changeCalendarDate(_calendarControl.nextYear,_calendarControl.nextMonth)
			}
			break
		}
		case 35:{//End
		    	var index=getNumberOfDays(_calendarControl.month,_calendarControl.year) +_calendarControl.startday-1
			setCalendarActiveCell(calendarData.cells[index+7+7])
			break
		}
		case 36:{//Home
			setCalendarActiveCell(calendarData.cells[_calendarControl.startday+7+7])
			break
		}
		case 37:{//<--
			var index=_calendarControl.activeCellIndex-1;
			if (index<0) index=0;
			setCalendarActiveCell(calendarData.cells[index+7])
			break
		}
		case 38:{//上箭头
			if (_calendarControl.activeCellIndex<14){
				var day=getNumberOfDays(_calendarControl.preMonth,_calendarControl.preYear)+_calendarControl.day-7;
				setCalendarDate(new Date(_calendarControl.preYear, _calendarControl.preMonth, day));
			}
			else{
				var index=_calendarControl.activeCellIndex-7;
				setCalendarActiveCell(calendarData.cells[index+7]);
			}
			break
		}
		case 39:{//
			var index=_calendarControl.activeCellIndex+1;
			if (index>=calendarData.cells.length-7) index=calendarData.cells.length-8;
			setCalendarActiveCell(calendarData.cells[index+7])
			break
		}
		case 40:{//下箭头
			if (_calendarControl.activeCellIndex>41){
				var day=7-(getNumberOfDays(_calendarControl.month,_calendarControl.year)-_calendarControl.day);
				setCalendarDate(new Date(_calendarControl.nextYear, _calendarControl.nextMonth, day));
			}
			else{
				var index=_calendarControl.activeCellIndex+7;
				setCalendarActiveCell(calendarData.cells[index+7]);
			}
			break
		}
	}
}

function _calendar_today_onclick(){
	changeCalendarDate(_calendarControl.todayYear,_calendarControl.todayMonth,_calendarControl.todayDay)
	var index=_calendarControl.todayDay+_calendarControl.startday-1;
	setCalendarActiveCell(calendarData.cells[index+7]);
	dropDownSelected();
}

function _yearmonth_today_onclick(){
	changeYearMonth(_calendarControl.todayYear,_calendarControl.todayMonth)
	var index=_calendarControl.todayMonth;
	setActiveMonthCell(calendarData.cells[index]);
	dropDownSelected();
}

function getNumberOfDays(month,year){
	var numDays=new Array(31,28,31,30,31,30,31,31,30,31,30,31)
	n=numDays[month]
	if (month==1 && (year%4==0 && year%100!=0 || year%400==0)) n++
	return n
}

