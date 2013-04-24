
// 功能说明:得到屏幕及IE位置参数

var posLib = {
		
	getIeBox:		function (el) {
		return this.ie && el.document.compatMode != "CSS1Compat";
	},
	
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

	// relative canvas/document (outer borders of canvas/document,
	// inside borders of element)
	getInnerLeft:	function (el) {
		return this.getLeft(el) + this.getBorderLeftWidth(el);
	},

	getInnerTop:	function (el) {
		return this.getTop(el) + this.getBorderTopWidth(el);
	},

	// width and height (outer, border-box)
	getWidth:	function (el) {
		return el.offsetWidth;
	},

	getHeight:	function (el) {
		return el.offsetHeight;
	},

	getCanvasElement:	function (el) {
		var doc = el.ownerDocument || el.document;	// IE55 bug
		if (doc.compatMode == "CSS1Compat")
			return doc.documentElement;
		else
			return doc.body;
	},

	getBorderLeftWidth:	function (el) {
		return el.clientLeft;
	},

	getBorderTopWidth:	function (el) {
		return el.clientTop;
	},

	// added by steve_gu at 2004-02-05
	// 解决iframe的bug
	topGettedByMouse:  0,
	leftGettedByMouse: 0,

	getScreenLeft:	function (el) {
		var doc = el.ownerDocument || el.document;	// IE55 bug
		var w = doc.parentWindow;
		if (w.parent != w) {
			//return posLib.leftGettedByMouse;
		}
		return w.screenLeft + this.getBorderLeftWidth(this.getCanvasElement(el)) +
			this.getClientLeft(el);
	},

	getScreenTop:	function (el) {
		var doc = el.ownerDocument || el.document;	// IE55 bug
		var w = doc.parentWindow;
		//return w.screenTop + this.getBorderTopWidth(this.getCanvasElement(el)) + this.getClientTop(el);
		//return w.screenTop + doc.body.clientTop + el.offsetTop + el.clientTop;				
		// added by steve at 2004-02-04 
		// 如果是iframe
		//window.status = "w.screentTop = " + w.screenTop;
		if (w.parent != w){
			//window.parent.frames[0]
			// alert("非顶层窗口");
			//alert(window == w);
			return posLib.topGettedByMouse;
		} 
		
		return w.screenTop + this.getBorderTopWidth(this.getCanvasElement(el)) + this.getClientTop(el);
			
	}
};

posLib.ua =		navigator.userAgent;posLib.opera =	/opera [56789]|opera\/[56789]/i.test(posLib.ua);
posLib.ie =		(!posLib.opera) && /MSIE/.test(posLib.ua);posLib.ie6 =	posLib.ie && /MSIE [6789]/.test(posLib.ua);posLib.moz =	!posLib.opera && /gecko/i.test(posLib.ua);

////////////////////////////////////////////////////////////////////////////////////
// menuCache
//
var MenuConstant={
	w:0,
	h:0,
	showCount:0,
	menuBarLayoutDirection: "horizontal"
}
var menuCache = {
	_count:		0,
	_idPrefix:	"-menu-cache-",

	getId:	function () {
		return this._idPrefix + this._count++;
	},

	remove:	function (o) {
		// 关联数组
		delete this[o.id];
	}
};

////////////////////////////////////////////////////////////////////////////////////
// Menu
//

function Menu() {
	// instance property
	/** MenuItem[], read only. An array containing the MenuItem objects for the menu */	
	this.items = [];
	/** Menu, read only, the parent menu */
	this.parentMenu = null;
	/** MenuItem, read only, if this menu is a submenu */
	this.parentMenuItem = null;
	/** Window, read only, the popup window used to display the menu */
	this.popup = null;
	/** Menu, read only, if any submenu is shown, this will point to that menu */	
	this.shownSubMenu = null;
	/** Number, read only, the index of the selected menu item, use getter and setter instead. */
	this.selectedIndex = -1;

	// local instance property
	this._aboutToShowSubMenu = false;	
	this._drawn = false;
	this._scrollingMode = false;
	this._showTimer = null;
	this._closeTimer = null;

	this._onCloseInterval = null;
	// 是否为关闭
	this._closed = true;
	this._closedAt = 0;

	this._cachedSizes = {};
	this._measureInvalid = true;
	// 用来表示该页面元素
	this.id = menuCache.getId();
	// 关联数组存储该menu对象	
	menuCache[this.id] = this;
}

// prototype's property
/** String, the url of the css file */ 
//Menu.prototype.cssFile = "";

Menu.prototype.cssFile = Ces_Library_path + "css/menu.css";
/** String, the text between the <style> and </style> */
Menu.prototype.cssText = null;
/** Boolean, where the mouse can hover on the disabled menuitem */
Menu.prototype.mouseHoverDisabled = true;
/** Number, the time in ms to wait before showing a menu */
Menu.prototype.showTimeout = 100;
/** Number, the time in ms to wait before hiding a menu */
Menu.prototype.closeTimeout = 250;

/**
 * added by steve_gu at 2004-01-18
 * Class method, set the cssFile for all menu and menu-bar instance.
 */
Menu.setCSSFile = function (cssFile) {
	Menu.prototype.cssFile = cssFile;
	document.write("<link href='" + cssFile + "' rel='stylesheet'>");
}

/**
 * added by steve_gu at 2004-01-18
 * Instance method, set the cssFile for specific menu instance.
 */
Menu.prototype.setCSSFile = function(cssFile) {
	this.cssFile = cssFile;
	document.write("<link href='" + cssFile + "' rel='stylesheet'>");
}

/**
 * add the menu item last to the menu or before the specified menu item
 */
Menu.prototype.add = function (mi, beforeMi) {
	if (beforeMi != null) {
		var items = this.items;
		var l = items.length;
		var i = 0;
		// 得到要插入的MenuItem
		for ( ; i < l; i++) {
			if (items[i] == beforeMi)
				break;
		}
		// you can see the array operation in <JavaScript: The Definitive Guide>.
		// 重写items数组，中间插入mi对象
		this.items = items.slice(0, i).concat(mi).concat(items.slice(i, l));
		// update itemIndex
		for (var j = i; j < l + 1; j++) {		
			// 重写加入位置后面的所有MenuItem的itemIndex.
			this.items[j].itemIndex = j;
		}
	}
	else {
		// add the itemMenu in the end
		this.items.push(mi);
		mi.itemIndex = this.items.length - 1;
	}
	// 设置该mi的parentMenu
	mi.parentMenu = this;
	// 如果mi有子菜单，设置子菜单的parentMenu和parentMenuItem
	if (mi.subMenu) {
		mi.subMenu.parentMenu = this;
		mi.subMenu.parentMenuItem = mi;
	}
	return mi;
};

/**
 * remove a menu item specified by the menu item object
 */
Menu.prototype.remove = function (mi) {
	var res = [];
	var items = this.items;
	var l = items.length;
	for (var i = 0; i < l; i++) {
		if (items[i] != mi) {
			res.push( items[i] );
			items[i].itemIndex = res.length - 1;
		}
	}
	this.items = res;
	// 取消mi与menu的关联
	mi.parentMenu = null;
	return mi;
};

/**
 * actually draw the menu's MenuItem.
 */
Menu.prototype.toHTML = function () {
	var items = this.items;
	// 子菜单项的个数
	var l = items.length
	// alert("items.length=" + items.length);
	var itemsHTML = new Array(l);
	for (var i = 0; i < l; i++) {
		itemsHTML[i] = items[i].toHTML();
	}
	// 必须加<html><head><link....， 因为此处代码是加入弹出窗口中的
	return    "<html><head>" +  
	(this.cssText == null ?/* support tow css solution, cssText is prior to cssFile*/
		"<link type='text/css' id='cssLink' rel='StyleSheet' href='" + this.cssFile + "' />" :
		"<style type='text/css'>" + this.cssText + "</style>") + "</head>" +
	    "<body class='menu-body' style='margin:0'>" +
			"<div class='outer-border' id='outer-border'>" + 
				"<div class='inner-border'>" +
					/* 实际菜单项 */
					"<div id='scroll-container'>" +
						"<table id='menu-table' cellspacing='0' border=0>" +	itemsHTML.join("") + "</table>" +
					"</div>" +
				"</div>" + 
			"</div>"  +
		"</body>" +
	"</html>";
};

/**
 * create the popup object used for the menu
 */
Menu.prototype.createPopup = function () {
	var w;
	var pm = this.parentMenu;
	if ( pm == null ) {
		// alert("if");
		w = window;
	}
	else {
		// alert("else");
		// 得到弹出窗口的父窗口，可能为当前页面窗口(window)或者父弹出窗口
		w = pm.getDocument().parentWindow;		
		// w = window;
	}
	this.popup = w.createPopup();
};

/**
 * get the popup object
 */
Menu.prototype.getPopup = function () {
	if (this.popup == null) this.createPopup();
	return this.popup;
};

/**
 * mark the menu as dirty, the next time the menu is shown it is first redraw
 */ 
Menu.prototype.invalidate = function () {
	if (this._drawn) {
		// do some memory cleanup
		var items = this.items;
		var l = items.length;
		var mi;
		for (var i = 0; i < l; i++) {
			mi = items[i];
			mi._htmlElement_menuItem = null;
			mi._htmlElement = null;
		}
		this.detachEvents();
	}
	this._drawn = false;
	this.resetSizeCache();
	this._measureInvalid = true;
};


/**
 * redraw the menu， 这个方法基本不用
 */
Menu.prototype.redrawMenu = function () {
	this.invalidate();
	this.drawMenu();
};

/** 
 * draws the menu inside the popup object. this function does nothing if the menu is not ivalidated
 */
Menu.prototype.drawMenu = function() {
	// 如果已画
	if (this._drawn) return;
	// 保证得到popup对象
	var oPopup = this.getPopup();
	// d为当前菜单的popup.document
	/*var d = this.getDocument();
	// 打开一个流，以便写入菜单内容
	d.open("text/html", "replace");
	d.write(this.toHTML());*/
	//var linkEl = d.getElementsByTagName("LINK")[0];
	//var linkE = oPopup.document.createElement("<link id='cssLink'>");
	//<link href="skins/winclassic.css" rel="stylesheet">
	//linkE.href = this.cssFile;//"skins/winclassic.css";
	//linkE.rel = "stylesheet";
	//var styleE = oPopup.document.createElement("style");
	//styleE.type="text/css";
	//styleE.innerText = ".menu-body {background-color:Menu;color:MenuText;cursor:hand;}";	
	/*oPopup.document.appendChild(linkE);	
	var oPupupBody = oPopup.document.body;
	oPupupBody.innerHTML = this.toHTML();
	oPupupBody.className = 'menu-body';*/
	// modified by steve_gu at 2004-01-16:document.appendChild()方法IE5.5不支持
	var d = this.getDocument();
	d.open("text/html", "replace");
	d.write(this.toHTML());
	d.close();

	this._drawn = true;

	var scrollContainer = oPopup.document.getElementById("scroll-container");
	// bind menu items to the table rows
	// 双向关联，设置每一个table row的_menuItem属性，同时设置关联每一个MenuItem的_htmlElement到实际的html元素
	var rows = scrollContainer.firstChild.tBodies[0].rows;
	var items = this.items;
	var mi;
	for (var i = 0; i < rows.length; i++ ) {
		mi = items[i];
		rows[i]._menuItem = mi;
		mi._htmlElement = rows[i];
	}
	// hook up mouse
	//alert(this.toHTML());
	this.hookupMenu(oPopup.document);
};


var interval="";
var oThis;
var winLeft = 0;
var winTop = 0;
var baseObj = "";

/**
 * Shows the menu at the given location and with the given size
 */
Menu.prototype.show = function (left,top,o) {
	var pm = this.parentMenu;
	if (pm)// 关闭除自己的菜单
		pm.closeAllSubs(this);

	var wasShown = this.isShown();
	// 预留的onbeforeshow事件
	if (typeof this.onbeforeshow == "function" && !wasShown) this.onbeforeshow();

	this.drawMenu();
//	alert(this.popup.document.body.innerHTML);
	if (left == null) left = 0;
	if (top == null) top = 0;
	// added by steve_gu at 2004-01-08	
	var w = 0;
	var h = 0;
	// added by steve_gu at 2004-01-14
	if (this._isCssFileLoaded()) {		
		if (interval!=""){ 
			window.clearInterval(interval);
			interval="";
		}
		this.popup.show(left,top,0,0);		
		w = this.popup.document.body.scrollWidth;
		h = this.popup.document.body.scrollHeight;

		this.popup.hide();	
		if (o) {			
			this.popup.show(left,top,w,h,o);
		}
		else {
			this.popup.show(left,top,w,h);
		}
	}
	else {
		oThis = this;
		winLeft = left;
		winTop = top;

		if(interval == ""){
			if (o) {
				baseObj = o;
		 		interval = window.setInterval("oThis.show(winLeft,winTop,baseObj)",1);
		 	}
		 	else {
		 		interval = window.setInterval("oThis.show(winLeft,winTop)",1);
		 	}
		}
	}
	//return;
	
/*	MenuConstant.showCount++;
	if(MenuConstant.showCount>1){
		MenuConstant.w=w;
		MenuConstant.h=h;
	}
*/

	// work around a bug that sometimes occured with large pages when
	// opening the first menu
/*	if ( this.getPreferredWidth() == 0 ) {
		this.invalidate();
		this.show(left,top,w,h);
		return;
	}*/

	// clear selected item
	if (this.selectedIndex != -1) {
		if (this.items[this.selectedIndex] )
			this.items[this.selectedIndex].setSelected(false);
	}

	if (pm) {
		pm.shownSubMenu = this;
		pm._aboutToShowSubMenu = false;
	}

	window.clearTimeout(this._showTimer );
	window.clearTimeout(this._closeTimer );

	this._closed = false;
	// added by steve_gu at 2004-01-29，因为关闭子菜单不触发事件，周期地判断是否子菜单已关闭
	 this._startClosePoll();
	// 预留的onshow事件处理
	if (typeof this.onshow == "function" && !wasShown && this.isShown()) this.onshow();
};

Menu.prototype.hide = function() {
	if (this.popup) {
		this.popup.hide();		
	}
}

// poll close state and when closed call _onclose
Menu.prototype._startClosePoll = function () {
	var oThis = this;
	window.clearInterval( this._onCloseInterval );
	this._onCloseInterval = window.setInterval(
		"eventListeners.menu.oncloseinterval(\"" + this.id + "\")", 100 );
};

/**
 * return a document that can be used to measure the content inside the menu
 */ 
Menu.prototype.getMeasureDocument = function () {
	if (this.isShown() && this._drawn) {
		return this.getDocument();
	}

	var mf = Menu._measureFrame;
	if (mf == null) {
		// should be top document
		mf = Menu._measureFrame = document.createElement("IFRAME");
		var mfs = mf.style;
		mfs.position = "absolute";
		mfs.visibility = "hidden";
		/*mfs.left = "-100px";
		mfs.top = "-100px";
		mfs.width = "10px";
		mfs.height = "10px";
		mf.frameBorder = 0;*/
		document.body.appendChild(mf);
	}
	
	var d = mf.contentWindow.document
	
	if (Menu._measureMenu == this && !this._measureInvalid) return d;

	d.open("text/HTML", "replace");
	d.write(this.toHTML());
	d.close();

	Menu._measureMenu = this;
	this._measureInvalid = false;

	return d;
};

/**
 * return the document inside the popup used for the menu
 */
Menu.prototype.getDocument = function () {
	if (this.popup) {
		return this.popup.document;
	}
	else {
		return null;
	}
};


/**
 *  Closes all menus in the menu tree.
 */
Menu.prototype.closeAllMenus = function () {
	// recusive
	if (this.parentMenu) this.parentMenu.closeAllMenus();
	else this.close();
};

/**
 * Closes the menu.
 */
Menu.prototype.close = function () {
	// 首先关闭所有子菜单
	this.closeAllSubs();
	window.clearTimeout(this._showTimer);
	window.clearTimeout(this._closeTimer);
	var pm = this.parentMenu;
	if (pm && pm.shownSubMenu == this) pm.shownSubMenu = null;
	if (this.popup) {
		this.popup.hide();
	}
	this.setSelectedIndex(-1);
	this._checkCloseState();
};

/**
 * Closes all the sub menus except the provided one. If no argument is passed all * * * subs are closed.
 */
Menu.prototype.closeAllSubs = function (oNotThisSub) {
	// go through items and check for sub menus
	var items = this.items;
	var l = items.length;
	for (var i = 0; i < l; i++) {
		if (items[i].subMenu != null && items[i].subMenu != oNotThisSub)  {
			// 可能recursive
			items[i].subMenu.close();
		}
	}
};

/**
 * Returns the index of the selected menu item
 */
Menu.prototype.getSelectedIndex = function () {
	return this.selectedIndex;
};

/**
 * Selects the menu item with the provided index
 */
Menu.prototype.setSelectedIndex = function (nIndex) {
	if (this.selectedIndex == nIndex) return;

	if (nIndex >= this.items.length) nIndex = -1;

	var mi;

	// deselect old
	if (this.selectedIndex != -1) {
		mi = this.items[this.selectedIndex];
		mi.setSelected(false);
	}

	this.selectedIndex = nIndex;
	mi = this.items[this.selectedIndex];
	if (mi != null) mi.setSelected(true);
};

/**
 * Returns the width of the menu，实际的宽度，区别于分辨率
 */
Menu.prototype.getWidth = function () {
	var d = this.getDocument();
	if (d != null)
		return d.body.offsetWidth;
	else
		return 0;
};

/**
 * Returns the height of the menu，实际的高度，区别于分辨率
 */
Menu.prototype.getHeight = function () {
	var d = this.getDocument();
	if (d != null) {
		return d.body.offsetHeight;
	}
	else {
		return 0;
	}
};

/**
 * Return the preffed width of the menu, this is the width of the widest of the menu item
 */
Menu.prototype.getPreferredWidth = function () {
	this.updateSizeCache();
	return this._cachedSizes.preferredWidth;
};

/**
 * Return the preffed height of the menu, this is the sum of all items and asuming that no scroll buttons are needed.
 */
Menu.prototype.getPreferredHeight = function () {
	this.updateSizeCache();
	return this._cachedSizes.preferredHeight;
};

/**
 * Return the left position of the menu
 */
Menu.prototype.getLeft = function () {
	var d = this.getDocument();
	if (d != null)
		return d.parentWindow.screenLeft;
	else
		return 0;
};

/**
 * Return the top position of the menu
 */
Menu.prototype.getTop = function () {
	var d = this.getDocument();
	if (d != null)
		return d.parentWindow.screenTop;
	else
		return 0;
};

/**
 * Return the distance between the outer left edge of the menu and the menu items
 */
Menu.prototype.getInsetLeft = function () {
	this.updateSizeCache();
	return this._cachedSizes.insetLeft;
};

/**
 * Return the distance between the outer right edge of the menu and the menu items
 */
Menu.prototype.getInsetRight = function () {
	this.updateSizeCache();
	return this._cachedSizes.insetRight;
};

/**
 * Return the distance between the outer upper edge of the menu and the first menu * * item.
 */
Menu.prototype.getInsetTop = function () {
	this.updateSizeCache();
	return this._cachedSizes.insetTop;
};

/**
 * Return the distance betweeen the lower outer edge of the menu and the last menu * * items.
 */
Menu.prototype.getInsetBottom = function () {
	this.updateSizeCache();
	return this._cachedSizes.insetBottom;
};

/**
 * Return whether the sizes are cached.
 */
Menu.prototype.areSizesCached = function () {
	var cs = this._cachedSizes;
	return this._drawn &&
		"preferredWidth" in cs &&
		"preferredHeight" in cs &&
		"insetLeft" in cs &&
		"insetRight" in cs &&
		"insetTop" in cs &&
		"insetBottom" in cs;
};

// depreciated
Menu.prototype.cacheSizes = function (bForce) {
	return updateSizeCache(bForce);
};

/**
 * reset cachedSizes
 */
Menu.prototype.resetSizeCache = function () {
	this._cachedSizes = {};
};

/**
 * updates the cached sizes if needed.
 */
Menu.prototype.updateSizeCache = function (bForce) {
	if (this.areSizesCached() && !bForce) return;
	var d = this.getMeasureDocument();
	var body = d.body;
	var cs = this._cachedSizes = {};	// reset
	var scrollContainer = d.getElementById("scroll-container");
	// preferred width
	cs.preferredWidth = d.body.scrollWidth;
	// preferred height
	cs.preferredHeight = body.scrollHeight;
	scrollContainer.style.overflow = "visible";
	scrollContainer.style.overflow = "hidden";
	// inset left
	cs.insetLeft = posLib.getLeft(scrollContainer);
	// inset right
	cs.insetRight = body.scrollWidth - posLib.getLeft(scrollContainer) -
					scrollContainer.offsetWidth;
	// inset top
	cs.insetTop = posLib.getTop(scrollContainer);
	// inset bottom
	cs.insetBottom = body.scrollHeight - posLib.getTop(scrollContainer) - scrollContainer.offsetHeight;	
};


Menu.prototype.makeEventListeners = function () {
	if (this.eventListeners != null) return;
	this.eventListeners = {
		onscroll:			new Function("eventListeners.menu.onscroll('" + this.id + "')"),
		onmouseover:		new Function("eventListeners.menu.onmouseover('" + this.id + "')"),
		onmouseout:			new Function("eventListeners.menu.onmouseout('" + this.id + "')"),
		onmouseup:			new Function("eventListeners.menu.onmouseup('" + this.id + "')"),
		onmousewheel:		new Function("eventListeners.menu.onmousewheel('" + this.id + "')"),
		onreadystatechange:	new Function("eventListeners.menu.onreadystatechange('" + this.id + "')"),
		onkeydown:			new Function("eventListeners.menu.onkeydown('" + this.id + "')"),
		oncontextmenu:		new Function("eventListeners.menu.oncontextmenu('" + this.id + "')"),
		onunload:			new Function("eventListeners.menu.onunload('" + this.id + "')")
	};
};

Menu.prototype.detachEvents = function () {
	if (this.eventListeners == null) return;
	
	var d = this.getDocument();
	var w = d.parentWindow;
	var scrollContainer = d.getElementById("scroll-container");

	scrollContainer.detachEvent("onscroll", this.eventListeners.onscroll);
	d.detachEvent("onmouseover", this.eventListeners.onmouseover);
	d.detachEvent("onmouseout", this.eventListeners.onmouseout);
	d.detachEvent("onmouseup", this.eventListeners.onmouseup);
	d.detachEvent("onmousewheel", this.eventListeners.onmousewheel);
	/*if (this.cssText == null) {
		var linkEl = d.getElementsByTagName("LINK")[0];
		linkEl.detachEvent("onreadystatechange", this.eventListeners.onreadystatechange);
	}*/
	 d.detachEvent( "onkeydown", this.eventListeners.onkeydown);
	d.detachEvent("oncontextmenu", this.eventListeners.oncontextmenu);
	// prevent IE to keep menu open when navigating away
	window.detachEvent("onunload", this.eventListeners.onunload);
}

/**
 * Hookups events and connections between the HTML DOM and the JavaScript objects
 */
Menu.prototype.hookupMenu = function (d) {
	this.detachEvents();
	this.makeEventListeners();
	var oThis = this;
	var d = this.getDocument();
	var w = d.parentWindow;
	var scrollContainer = d.getElementById("scroll-container");

	// listen to the onscroll
	// scrollContainer.attachEvent("onscroll", this.eventListeners.onscroll);
	d.attachEvent("onmouseover", this.eventListeners.onmouseover);
	d.attachEvent("onmouseout", this.eventListeners.onmouseout);
	d.attachEvent("onmouseup", this.eventListeners.onmouseup);
	d.attachEvent("onmousewheel", this.eventListeners.onmousewheel);

	// if css file is not loaded we need to wait for it to load.
	// Once loaded fix the size
	/*if (this.cssText == null) {
		var linkEl = d.getElementsByTagName("LINK")[0];
		if (linkEl.readyState != "complete") {
			linkEl.attachEvent("onreadystatechange", this.eventListeners.onreadystatechange );
		}
	}*/

	d.attachEvent( "onkeydown", this.eventListeners.onkeydown );
	d.attachEvent("oncontextmenu", this.eventListeners.oncontextmenu);
	// prevent IE to keep menu open when navigating away
	window.attachEvent("onunload", this.eventListeners.onunload);
	for (var i = 0; i < d.all.length; i++) d.all[i].unselectable = "on";
};

Menu.prototype._isCssFileLoaded = function () {
	if (this.cssText != null) return true;
	var d = this.getPopup().document;// this.getMeasureDocument();
	//var l = d.getElementsByTagName("LINK")[0];
	var l = d.getElementById('cssLink');
	//while(!(l.readyState == "complete")){
	//}
	return l.readyState == "complete";
};

Menu.prototype.destroy = function () {
	var l = this.items.length;
	for (var i = l -1; i >= 0; i--) this.items[i].destroy();
	this.detachEvents();
	this.items = [];
	this.parentMenu = null;
	this.parentMenuItem = null;
	this.shownSubMenu = null;
	this._cachedSizes = null;
	this.eventListeners = null;

	if (this.popup != null) {
		var d = this.popup.document;
		d.open("text/plain", "replace");
		d.write("");
		d.close();
		this.popup = null;
	}

	if ( Menu._measureMenu == this ) {
		Menu._measureMenu = null;
		var d = Menu._measureFrame.contentWindow.document;
		d.open("text/plain", "replace");
		d.write("");
		d.close();
		Menu._measureFrame.parentNode.removeChild(Menu._measureFrame);
		Menu._measureFrame = null;
	}
	menuCache.remove(this);
};

/**
 * Returns whether the menu is shown or not
 */
Menu.prototype.isShown = function () {
	this._checkCloseState();
	// isOpen是popup的保留属性
	return this.popup != null && this.popup.isOpen;
};

Menu.prototype._checkCloseState = function () {
	var closed = (this.popup == null || !this.popup.isOpen);	
	// 如果实际已经closed, 而变量_closed还没有置为false
	if (closed && this._closed != closed) {
		this._closed = closed;
		this._closedAt = new Date().valueOf();
		window.clearInterval(this._onCloseInterval);
		if (typeof this._onclose == "function") {
			var e = this.getDocument().parentWindow.event;
			if (e != null && e.keyCode == 27)
				this._closeReason = "escape";
			else
				this._closeReason = "unknown";
			this._onclose();
		}
		if (typeof this.onclose == "function")
			this.onclose();
	}
};


////////////////////////////////////////////////////////////////////////////////////
// MenuItem class
//

function MenuItem(sLabelText, fAction, sIconSrc, oSubMenu) {
	// instance property
	// public
	this.icon = sIconSrc || "";
	this.text = sLabelText;
	this.action = fAction;
	this.subMenu = oSubMenu;
	this.parentMenu = null;

	// private
	this._selected = false;
	this._useInsets = true;	// Whether insets be taken into account when showing sub menu

	this.id = menuCache.getId();
	menuCache[this.id] = this;
}

MenuItem.prototype.subMenuDirection = "horizontal";
MenuItem.prototype.disabled = false;
MenuItem.prototype.mnemonic = null;
MenuItem.prototype.shortcut = null;
MenuItem.prototype.toolTip = "";
MenuItem.prototype.target = null;
MenuItem.prototype.visible = true;

MenuItem.prototype.toHTML = function () {
	var cssClass = this.getCSSClass();
	var toolTip = this.getToolTip();
	// One menuItem is a row
	return	"<tr" +
			(cssClass != "" ? " class='" + cssClass + "'" : "") +
			(toolTip != "" ? " title='" + toolTip + "'" : "") +
			(!this.visible ? " style='display: none'" : "") +
			">" +
			this.getIconCellHTML() +
			this.getTextCellHTML() +
			/*this.getShortcutCellHTML() +*/
			this.getSubMenuArrowCellHTML() +
			"</tr>";
};
//-------------------------------------------------//
MenuItem.prototype.getTextHTML = function () {
	var s = this.text;
	//if (!s || !this.mnemonic)
		return s;

	// replace character with <u> character </u>
	// /^(((<([^>]|MNEMONIC)+>)|[^MNEMONIC])*)(MNEMONIC)/i
	var re = new RegExp( "^(((<([^>]|" + this.mnemonic + ")+>)|[^<" +
						this.mnemonic + "])*)(" + this.mnemonic + ")", "i" );
	re.exec( s );
	if ( RegExp.index != -1 && RegExp.$5 != "" )
		return RegExp.$1 + "<u>" + RegExp.$5 + "</u>" + RegExp.rightContext;
	else
		return s;
};

MenuItem.prototype.getTextCellHTML = function () {
	return "<td class='label-cell' nowrap='nowrap'>" + this.getTextHTML() + "</td>";
			/*this.makeDisabledContainer(
				this.getTextHTML()
			) +
			"</td>";*/
};
//-------------------------------------------------//
MenuItem.prototype.getIconHTML = function () {
	return this.icon != "" ? "<img src='" + this.icon + "'>" : "<span>&nbsp;</span>";
};

MenuItem.prototype.getIconCellHTML = function () {
	return "<td class='" +
			(this.icon != "" ? "icon-cell" : "empty-icon-cell") +
			"'>" +
			this.makeDisabledContainer(
				this.getIconHTML()
			) +
			"</td>";
};
//-------------------------------------------------//
/**
 * 根据MenuItem的不同状态设置不同风格
 */
MenuItem.prototype.getCSSClass = function () {
	if ( this.disabled && this._selected )
		return "disabled-hover";
	else if ( this.disabled )
		return "disabled";
	else if ( this._selected )
		return "hover";

	return "";
};

MenuItem.prototype.getToolTip = function () {
	return this.toolTip;
};
//-------------------------------------------------//
MenuItem.prototype.getShortcutHTML = function () {
	if ( this.shortcut == null )
		return "&nbsp;";
	return this.shortcut;
};

MenuItem.prototype.getShortcutCellHTML = function () {
	return "<td class='shortcut-cell' nowrap='nowrap'>" +
			this.makeDisabledContainer(
				this.getShortcutHTML()
			) +
			"</td>";
};
//-------------------------------------------------//
MenuItem.prototype.getSubMenuArrowHTML = function () {
	if ( this.subMenu == null )
		return "&nbsp;";

	return 4;	// right arrow using the marlett (or webdings) font
};

MenuItem.prototype.getSubMenuArrowCellHTML = function () {
	return "<td class='arrow-cell'>" +
			this.makeDisabledContainer(
				this.getSubMenuArrowHTML()
			) +
			"</td>";
};
//-------------------------------------------------//

MenuItem.prototype.makeDisabledContainer = function (s) {
	if (this.disabled)
		return	"<span class='disabled-container'><span class='disabled-container'>" +
				s + "</span></span>";
	return s;
};

MenuItem.prototype.dispatchAction = function () {
	if (this.disabled) return;

	this.setSelected(true);
	// 如果有子菜单，没有function动作
	if (this.subMenu) {
		if (!this.subMenu.isShown())
			// modified by steve_gu at 2003-12-23
			// window.status = "show.......";
			this.showSubMenu(true);
		return;
	}

	if (typeof this.action == "function") {
		this.setSelected( false);
		this.parentMenu.closeAllMenus();
		this.action();
	}
	else if (typeof this.action == "string") {	// href
		this.setSelected( false );
		this.parentMenu.closeAllMenus();
		if (this.target != null) window.open(this.action, this.target);
		else document.location.href = this.action;
	}
};

/**
 * Selects or deselects the menu item.
 */
MenuItem.prototype.setSelected = function (bSelected) {
	// 若选中标志没改变，不执行任何操作
	if (this._selected == bSelected) return;
	
	this._selected = Boolean(bSelected);

	var tr = this._htmlElement;
	
	if (tr)
		tr.className = this.getCSSClass();
	// 若从选中到不选中，关闭其所有子菜单
	if (!this._selected) this.closeSubMenu(true);

	var pm = this.parentMenu;
	if (bSelected) {
		pm.setSelectedIndex(this.itemIndex);
		// select item in parent menu as well，recursive.
		if (pm.parentMenuItem) pm.parentMenuItem.setSelected(true);
	}
	else  {
		pm.setSelectedIndex(-1);
	}
};

/**
 * Return whether the menu item is selected or not.
 */
MenuItem.prototype.getSelected = function () {
	return this.itemIndex == this.parentMenu.selectedIndex;
};

/**
 * If the menu item has submenu this is shown
 */
MenuItem.prototype.showSubMenu = function (bDelayed) {
	var sm = this.subMenu;
	var pm = this.parentMenu;
	// the menu item has submenu and is not diabled.
	// h1.innerText = h1.innerText + " aboutToShowSubMenu";
	
	if (sm && !this.disabled) {
		pm._aboutToShowSubMenu = true;
		//window.clearTimeout( sm._showTimer );
		//window.clearTimeout( sm._closeTimer );
		var showTimeout = bDelayed ? sm.showTimeout : 0;
		//window.status = "showTimeout = " + showTimeout;
		showTimeout = 0;
		var oThis = this;
		
		sm._showTimer = window.setTimeout(
			"eventListeners.menuItem.onshowtimer('" + this.id + "')",
			showTimeout);
		
		//eval("eventListeners.menuItem.onshowtimer('" + this.id + "')");
	}
};

/**
 * If the menu item has submenu, then this will be closed.
 */
MenuItem.prototype.closeSubMenu = function (bDelay) {
	var sm = this.subMenu;
	if (sm) {
		window.clearTimeout(sm._showTimer);
		window.clearTimeout(sm._closeTimer);

		if (sm.popup) {
			if (!bDelay)
				sm.close();
			else {
				var oThis = this;
				sm._closeTimer = window.setTimeout(
					"eventListeners.menuItem.onclosetimer('" + this.id + "')",
					sm.closeTimeout );
			}
		}
	}
};

/**
 * Positions the sub menu relative to the menu item. The size and location as well as 
 * the property subMenuDirection is taken into account.
 */
MenuItem.prototype.positionSubMenu = function () {
	var dir = this.subMenuDirection;
	var el = this._htmlElement;
	var useInsets = this._useInsets;
	var sm = this.subMenu;

	var oThis = this;

	sm.drawMenu();
	sm.popup.show(0,0);
	//alert(sm.popup.document.body.scrollWidth);

	// find parent item rectangle
	var rect = {
		left:	posLib.getScreenLeft(el),
		top:	posLib.getScreenTop(el),
		width:	el.offsetWidth,
		height:	el.offsetHeight
	};

	var menuRect = {
		left:		sm.getLeft(),
		top:		sm.getTop(),
	    // 因为用到popup的scrollWidth，所以这儿不需要width和height
		//width:		sm.getPreferredWidth(),
		//height:		sm.getPreferredHeight(),
		width:			sm.popup.document.body.scrollWidth,
		height:         sm.popup.document.body.scrollHeight,
	//	insetLeft:		useInsets ? sm.getInsetLeft() : 0,
	//	insetRight:		useInsets ? sm.getInsetRight() : 0,
	//	insetTop:		useInsets ? sm.getInsetTop() : 0,
	//	insetBottom:	useInsets ? sm.getInsetBottom() : 0
		insetLeft:		useInsets ? 0 : 0,
		insetRight:		useInsets ? 3 : 0,
		insetTop:		useInsets ? 3 : 0,
		insetBottom:	useInsets ? 3 : 0
	};
	//sm.hide();
	// work around for buggy graphics drivers that screw up the screen.left
	var screenWidth = screen.width;
	
	var screenHeight = screen.height;
	while ( rect.left > screenWidth ) {
		screenWidth += screen.width;
	}
	while ( rect.top > screenHeight ) {
		screenHeight += screen.height;
	}

	var left, top, width = menuRect.width, height = menuRect.height;
	//alert(menuRect.width);
	if (dir == "vertical") {		
		if (rect.left + menuRect.width - menuRect.insetLeft <= screenWidth) {
			left = rect.left - menuRect.insetLeft;
		}
		else if ( screenWidth >= menuRect.width ) {
			left = screenWidth - menuRect.width;
		}
		else {
			// alert("0");
			left = 0;
		}

		if (rect.top + rect.height + menuRect.height - menuRect.insetTop <= screenHeight){		
			top = rect.top + rect.height - menuRect.insetTop;			
		}else if (rect.top - menuRect.height + menuRect.insetBottom >= 0)
			top = rect.top - menuRect.height + menuRect.insetBottom;
		else {	// use largest and resize
			var sizeAbove = rect.top + menuRect.insetBottom;
			var sizeBelow = screenHeight - rect.top - rect.height + menuRect.insetTop;
			if (sizeBelow >= sizeAbove) {
				top = rect.top + rect.height - menuRect.insetTop;
				height = sizeBelow;
			}
			else {
				top = 0;
				height = sizeAbove;
			}
		}
	}
	else {
		// alert("else");
		// 弹出菜单
		if (rect.top + menuRect.height - menuRect.insetTop <= screenHeight) {
			top = rect.top - menuRect.insetTop;
			//alert("if");
		}
		else if ( rect.top + rect.height - menuRect.height + menuRect.insetBottom >= 0) {
			top = rect.top + rect.height - menuRect.height + menuRect.insetBottom;
			//alert("else 1");
		}
		else if (screenHeight >= menuRect.height) {
			top = screenHeight - menuRect.height;
			//alert("else 2");
		}
		else {
			top = 0;
			height = screenHeight
			// alert("0");
		}

		if (rect.left + rect.width + menuRect.width - menuRect.insetLeft <= screenWidth) {
			left = rect.left + rect.width - menuRect.insetLeft;
			// alert("if 2");
		}
		else if (rect.left - menuRect.width + menuRect.insetRight >= 0) {
			left = rect.left - menuRect.width + menuRect.insetRight;
			//alert("2 else 1");
		}
		else if (screenWidth >= menuRect.width) {
			left = screenWidth - menuRect.width;
			//alert("2 else 2");
		}
		else {
			left = 0;
		}
	}
	// alert("left = " + left + "   top = " + top);
	 sm.show(left, top);
	//sm.show(0,0);

	//var scrollBefore = sm._scrollingMode;
	// show子菜单，不管是由MenuButton还是MenuItem展开而来
	//sm.show(left,top);
	//alert("sm._scrollingMode = " + sm._scrollingMode);
	// 如果show之后发现需要滚动或不需要滚动
	/*if ( sm._scrollingMode != scrollBefore ) {
		this.positionSubMenu();
	}*/
};

MenuItem.prototype.destroy = function () {
	if (this.subMenu != null) this.subMenu.destroy();

	this.subMenu = null;
	this.parentMenu = null;
	
	var el = this._htmlElement
	if (el != null) el._menuItem = null;
	this._htmlElement = null;
	menuCache.remove(this);
};

///////////////////////////////////////////////////////////////////////////////
// CheckBoxMenuItem extends MenuItem
//
function CheckBoxMenuItem(sLabelText, bChecked, fAction) {

	this.MenuItem = MenuItem;
	// no icon
	this.MenuItem(sLabelText, fAction, null);
	// public
	this.checked = bChecked;

}

CheckBoxMenuItem.prototype = new MenuItem;

/**
 * Override method
 */
CheckBoxMenuItem.prototype.getIconHTML = function () {
	return "<span class='check-box'>" +
		(this.checked ? "a" : "&nbsp;") +
		"</span>";
};

/**
 * Override method
 */
CheckBoxMenuItem.prototype.getIconCellHTML = function () {
	return "<td class='icon-cell'>" +
			this.makeDisabledContainer(
				this.getIconHTML()
			) +
			"</td>";
};

/**
 * Override method
 */
CheckBoxMenuItem.prototype.getCSSClass = function () {
	var s = (this.checked ? " checked" : "");
	if (this.disabled && this._selected)
		return "disabled-hover" + s;
	else if (this.disabled)
		return "disabled" + s;
	else if (this._selected)
		return "hover" + s;

	return s;
};

CheckBoxMenuItem.prototype._menuItem_dispatchAction =
	MenuItem.prototype.dispatchAction;

/**
 * Override method
 */
CheckBoxMenuItem.prototype.dispatchAction = function () {
	if (!this.disabled) {
		this.checked = !this.checked;
		this._menuItem_dispatchAction();
		this.parentMenu.invalidate();
		this.parentMenu.closeAllMenus();
	}
};

CheckBoxMenuItem.prototype.setChecked = function(bChecked) {
	if (bChecked) {
		this.checked = true;
	} else {
		this.checked = false;
	}
	this._menuItem_dispatchAction();
	this.parentMenu.invalidate();
	this.parentMenu.closeAllMenus();
}

///////////////////////////////////////////////////////////////////////////////
// RadioButtonMenuItem extends MenuItem
//
function RadioButtonMenuItem(sLabelText,bChecked,sRadioGroupName,fAction) {
	this.MenuItem = MenuItem;
	this.MenuItem(sLabelText,fAction,null);

	// public
	this.checked = bChecked;
	this.radioGroupName = sRadioGroupName;
}

RadioButtonMenuItem.prototype = new MenuItem;

/**
 * Override method
 */
RadioButtonMenuItem.prototype.getIconHTML = function () {
	return "<span class='radio-button'>" +
		(this.checked ? "n" : "&nbsp;") +
		"</span>";
};

RadioButtonMenuItem.prototype.getIconCellHTML = function () {
	return "<td class='icon-cell'>" +
			this.makeDisabledContainer(
				this.getIconHTML()
			) +
			"</td>";
};

RadioButtonMenuItem.prototype.getCSSClass = function () {
	var s = (this.checked ? " checked" : "");
	if ( this.disabled && this._selected )
		return "disabled-hover" + s;
	else if ( this.disabled )
		return "disabled" + s;
	else if ( this._selected )
		return "hover" + s;

	return s;
};

RadioButtonMenuItem.prototype._menuItem_dispatchAction = MenuItem.prototype.dispatchAction;
RadioButtonMenuItem.prototype.dispatchAction = function () {
	if (!this.disabled) {
		if (!this.checked) {
			// loop through items in parent menu
			var items = this.parentMenu.items;
			var l = items.length;
			for (var i = 0; i < l; i++) {
				if (items[i] instanceof RadioButtonMenuItem) {
					if (items[i].radioGroupName == this.radioGroupName) {
						items[i].checked = items[i] == this;
					}
				}
			}
			this.parentMenu.invalidate();
		}

		this._menuItem_dispatchAction();
		this.parentMenu.closeAllMenus();
	}
};

/* added by steve_gu at 2004-02-12 */
RadioButtonMenuItem.prototype.setChecked = function(bChecked) {
	if (bChecked) {
		this.checked = true;
	} else {
		this.checked = false;
	}
	//this._menuItem_dispatchAction();
	this.parentMenu.invalidate();
	this.parentMenu.closeAllMenus();	
}


///////////////////////////////////////////////////////////////////////////////
// MenuSeparator extends MenuItem
//
function MenuSeparator() {
	this.MenuItem = MenuItem;
	this.MenuItem();
}

MenuSeparator.prototype = new MenuItem;
/**
 * Override method
 */
MenuSeparator.prototype.toHTML = function () {
	return "<tr class='" + this.getCSSClass() + "'" +
			(!this.visible ? " style='display: none'" : "") +
			"><td colspan='4'>" +
			"<div class='separator-line'></div>" +
			"</td></tr>";
};

MenuSeparator.prototype.getCSSClass = function () {
	return "separator";
};



////////////////////////////////////////////////////////////////////////////////////
// MenuBar extends Menu
//

function MenuBar() {
	this.items = [];
	this.parentMenu = null;
	this.parentMenuItem = null;
	this.shownSubMenu = null;
	this._aboutToShowSubMenu = false;	
	this.id = menuCache.getId();
	menuCache[this.id] = this;
}
MenuBar.prototype = new Menu;
MenuBar.prototype._document = null;
// class property
MenuBar.leftMouseButton = 1;

MenuBar.background = "";
MenuBar.setBackground = function(background) {
	MenuBar.background = background;
}

MenuBar.prototype.toHTML = function () {
	var items = this.items;
	var l = items.length;
	var itemsHTML = new Array( l );
	for (var i = 0; i < l; i++ )// each menuButton's content
		itemsHTML[i] = items[i].toHTML();
	
	// modifid by steve_gu at 2003-12-23,Menu Bar和Menu采用统一的css
	tmpHTML = "";
	// commented by steve_gu at 2004-01-07
	// 因为MenuBar直接写在当前窗口中，所以可在显示MenuBar的页面中写<link href = ...>
	if (this.cssText == null) {
		//tmpHTML = tmpHTML + "<link type='text/css' rel = 'stylesheet' href = '" + this.cssFile + "' />";
		//alert(tmpHTML);
	}
	else {
		//tmpHTML = tmpHTML +  "<style type = 'text/css'>" + this.cssText + "</style>";		
	}
	// modified by steve_gu at 2004-01-18
	// tmpHTML = tmpHTML + "<div class='menu-bar' id='" + this.id + "'>";

	if (MenuBar.background != "") {
		tmpHTML = tmpHTML + "<div class='menu-bar' style='background:URL(\"" + MenuBar.background + "\")' id='" + this.id + "'>";
	} else {
		tmpHTML = tmpHTML + "<div class='menu-bar' id='" + this.id + "'>";
	}
	tmpHTML = tmpHTML + itemsHTML.join("") + "</div>";
	//alert("tmpHTML = " + tmpHTML);	
	return tmpHTML;
};

// 将公用的eventlistener注册到当前的MenuBar对象中
MenuBar.prototype.makeEventListeners = function () {
	if (this.eventListeners != null) return;

	this.eventListeners = {
		// eventListeners.menuBar.onmouseover('" + this.id + "') 为函数直接量
		onmouseover:		new Function("eventListeners.menuBar.onmouseover('" + this.id + "')"),
		onmouseout:		new Function("eventListeners.menuBar.onmouseout('" + this.id + "')"),
		onmousedown:		new Function("eventListeners.menuBar.onmousedown('" + this.id + "')"),
		onkeydown:			new Function( "eventListeners.menuBar.onkeydown('" + this.id + "')"),
		onunload:		new Function("eventListeners.menuBar.onunload('" + this.id + "')")
		// added by steve_gu at 2004-05-20
		//onclick:		new Function("eventListeners.menuBar.onclick('" + this.id + "')")
		// end add
	};
};

MenuBar.prototype.detachEvents = function () {
	if (this.eventListeners == null) return;
	this._htmlElement.attachEvent("onmouseover", this.eventListeners.onmouseover );
	this._htmlElement.attachEvent("onmouseout", this.eventListeners.onmouseout );
	this._htmlElement.attachEvent("onmousedown", this.eventListeners.onmousedown );
	// added by steve_gu 
	//this._htmlElement.attachEvent("onclick", this.eventListeners.onclick );
	// end add
	this._document.attachEvent( "onkeydown", this.eventListeners.onkeydown );
	window.detachEvent("onunload", this.eventListeners.onunload);
}

/**
 * 邦定事件
 * element:外层的menu-bar的div
 */
MenuBar.prototype.hookupMenu = function (element) {
	if (!this._document) this._document = element.document;

	//this.detachEvents();
	this.makeEventListeners();
	// create shortcut to HTML element
	this._htmlElement = element;
	element.unselectable = "on";
	// and same for menu buttons
	var cs = element.childNodes;
	//var items = this.items;
	for (var i = 0; i < cs.length; i++) {
		// 这里是引用而不是值
		this.items[i]._htmlElement = cs[i];
		cs[i]._menuItem = this.items[i];
	}
	// hookup events
	element.attachEvent("onmouseover",	this.eventListeners.onmouseover);
	element.attachEvent("onmouseout", this.eventListeners.onmouseout);
	element.attachEvent("onmousedown", this.eventListeners.onmousedown);
	// added by steve_gu at 2004-05-20
	//element.attachEvent("onclick", this.eventListeners.onclick);
	// end add
	 this._document.attachEvent("onkeydown", this.eventListeners.onkeydown);
	window.attachEvent("onunload", this.eventListeners.onunload);	
};
// override method, 始终isShow
MenuBar.prototype.isShown = function () {return true;};

// globle function
function getMenuItemElement(el) {
	while (el != null && el._menuItem == null)
		el = el.parentNode;
	return el;
}

function getTrElement(el) {
	while ( el != null && el.tagName != "TR" )
		el = el.parentNode;
	return el;
}

MenuBar.prototype.write = function (oDocument) {
	this._document = oDocument || document;
	// 默认为window.document.write
	this._document.write(this.toHTML());	
	var el = this._document.getElementById(this.id);
	// 这里的el为外层menu-bar的div
	this.hookupMenu(el);
};

MenuBar.prototype.create = function (oDocument) {
	this._document = oDocument || document;
	var dummyDiv = this._document.createElement("DIV");
	dummyDiv.innerHTML = this.toHTML();
	var el = dummyDiv.removeChild(dummyDiv.firstChild);
	this.hookupMenu(el);
	return el;
};

MenuBar.prototype._menu_setSelectedIndex = Menu.prototype.setSelectedIndex;

MenuBar.prototype.setSelectedIndex = function (nIndex) {
	this._menu_setSelectedIndex(nIndex);
	this.active = nIndex != -1;
};

MenuBar.prototype.setActive = function (bActive) {
	if (this.active != bActive) {
		this.active = Boolean(bActive);
		if (this.active) {
			this.setSelectedIndex(0);
			this.backupFocused();
			window.focus();
		}
		else {
			this.setSelectedIndex(-1);
			this.restoreFocused();
		}
	}
};

MenuBar.prototype.toggleActive = function () {
	if (this.getActiveState() == "active")
		this.setActive(false);
	else if (this.getActiveState() == "inactive")
		this.setActive(true);
};

// returns active, inactive or open
MenuBar.prototype.getActiveState = function () {
	if (this.shownSubMenu != null || this._aboutToShowSubMenu)
		return "open";
	else if (this.active)
		return "active";
	else
		return "inactive";
};

MenuBar.prototype.backupFocused = function () {
	this._activeElement = this._document.activeElement;
};

MenuBar.prototype.restoreFocused = function () {
	try {
		this._activeElement.focus();
	}
	catch (ex) {}
	delete this._activeElement;
};

MenuBar.prototype.getDocument = function () {
	return this._document;
};

MenuBar.prototype.destroy = function () {
	var l = this.items.length;
	for ( var i = l -1; i >= 0; i-- )
		this.items[i].destroy();

	this.detachEvents();
	this._activeElement = null;
	this._htmlElement = null;
	this._document = null;
	this.items = [];
	this.shownSubMenu = null;
	this.eventListeners = null;

	menuCache.remove( this );
};

/**
 * added by steve_gu at 2004-02-26 
 */
MenuBar.prototype.setLayoutDirection = function(sDirection) {
	if (sDirection == "horizontal") {
		MenuButton.prototype.subMenuDirection = "vertical";
	}
	if (sDirection == "vertical") {
		MenuButton.prototype.subMenuDirection = "horizontal";
	}
	MenuConstant.menuBarLayoutDirection = sDirection;
}

////////////////////////////////////////////////////////////////////////////////////
// MenuButton class extends MenuItem
//
// modified by steve_gu at 2004-05-20 增加了一个action属性，用于菜单条按纽没有下拉菜单
// 只执行操作的情况
function MenuButton(sLabelText, oSubMenu, action, sIconSrc) {
	this.MenuItem = MenuItem;	
	if (action) {
		this.MenuItem(sLabelText,null, sIconSrc);
		this.menuButtonAction = action;
	}
	else {
		// no action
		this.MenuItem(sLabelText, null, sIconSrc, oSubMenu);
	}
	
	// private
	this._hover = false;
	this._useInsets = false;	// should insets be taken into account when showing sub menu

	var oThis = this;
	if(this.subMenu) {this.subMenu._onclose = new Function("eventListeners.menuButton.onclose('" + this.id + "')")};
}

// set MenuButton as the MenuItem's subclass.
MenuButton.prototype = new MenuItem;
MenuButton.prototype.subMenuDirection = "vertical";

// added by steve_gu at 2004-01-18
MenuButton.background = "";
MenuButton.setBackground = function(background) {
	MenuButton.background = background;
}

MenuButton.prototype.toHTML = function () {
	var cssClass = this.getCSSClass();
	var toolTip = this.getToolTip();
	// added by steve_gu at 2004-02-26 
	if (MenuConstant.menuBarLayoutDirection == "vertical") {
		var tmpHTML = "<span style='display:block' unselectable='on' " +
					(cssClass != "" ? " class='" + cssClass + "'" : "") +
					(toolTip != "" ? " title='" + toolTip + "'" : "");	
	}
	else {
		var tmpHTML = "<span unselectable='on' " +
					(cssClass != "" ? " class='" + cssClass + "'" : "") +
					(toolTip != "" ? " title='" + toolTip + "'" : "");	
	}
	// end add
	if (!this.visible) {
		tmpHTML = tmpHTML + " style='display: none'";
	}
	else {
		if (MenuButton.background != ""){
			tmpHTML = tmpHTML + " style='background:URL(" + MenuButton.background + ")'";
		}
	}
	// modified by zhangtianping at 2003-02-03 将span改为table的td
	/*
	tmpHTML = tmpHTML + 	">" + "<table><tr>" +  
					"<td unselectable='on' class='left'></td>" +
					"<td unselectable='on' class='middle'>" +
						this.getTextHTML() +
					"</td>" +
					"<td unselectable='on' class='right'></td>" +"</tr></table>" + 
				"</span>";	
	*/
	// modified by steve_gu at 2004-09-02, add icon 
	tmpHTML = tmpHTML + 	">" + "<table><tr>" +  ((this.icon == "")? "<td unselectable='on' class='left'></td>":this.getIconCellHTML()) +
					"<td unselectable='on' class='middle'>" +
						this.getTextHTML() +
					"</td>" +
					"<td unselectable='on' class='right'></td>" +"</tr></table>" + 
				"</span>";	
	return tmpHTML;

};

MenuButton.prototype.getCSSClass = function () {
	if (this.disabled && this._selected) return "menu-button disabled-hover";
	else if (this.disabled) return "menu-button disabled";
	else if (this._selected) {
		if (this.parentMenu.getActiveState() == "open") {
			return "menu-button active";
		}
		else {
			return "menu-button hover";
		}
	}
	else if (this._hover) return "menu-button hover";
	return "menu-button";
};

MenuButton.prototype.subMenuClosed = function () {	
	if (this.subMenu._closeReason == "escape") {
		this.setSelected(true);
	}
	else {
		this.setSelected(false);
	}
	if (this.parentMenu.getActiveState() == "inactive")	this.parentMenu.restoreFocused();
};

MenuButton.prototype.setSelected = function (bSelected) {
	var oldSelected = this._selected;
	this._selected = Boolean(bSelected);

	var tr = this._htmlElement;
	if (tr) tr.className = this.getCSSClass();
	if (this._selected == oldSelected) return;
	if (!this._selected) this.closeSubMenu( true );
	if (bSelected) {
		this.parentMenu.setSelectedIndex(this.itemIndex);		
	}
	else this.parentMenu.setSelectedIndex(-1);
};

////////////////////////////////////////////////////////////////////////////////////
// event listener object
//
var testCount = 0;
var eventListeners = {
	menu: {
		onkeydown:	function (id) {
			var oThis = menuCache[id];
			var w = oThis.getDocument().parentWindow;
			//oThis.handleKeyEvent( w.event );
		},
		onunload:	function (id) {
			if (id in menuCache) {
				menuCache[id].closeAllMenus();
				menuCache[id].destroy();
			}
			// else already destroyed
		},
		oncontextmenu:	function (id) {
			var oThis = menuCache[id];
			var w = oThis.getDocument().parentWindow;
			w.event.returnValue = false;
		},

		onscroll:	function (id) {
			menuCache[id].fixScrollEnabledState();
		},

		onmouseover:	function (id) {			
			// h1.innerText = testCount++;
			var oThis = menuCache[id];
			var w = oThis.getDocument().parentWindow;
			posLib.topGettedByMouse = w.event.screenY - w.event.offsetY;
			posLib.leftGettedByMouse = w.event.screenX - w.event.offsetX;
			var fromEl	= getTrElement(w.event.fromElement);
			var toEl	= getTrElement(w.event.toElement);

			if (toEl != null && toEl != fromEl) {
				var mi = toEl._menuItem;
				if (mi) {
					// h1.innerText = h1.innerText + " mi true";
					if (!mi.disabled || oThis.mouseHoverDisabled) {
						mi.setSelected(true);
						// 尝试showSubMenu,
						mi.showSubMenu(true);
					}
				}
			}
		},

		onmouseout:	function (id) {
			testCount++;
			// h2.innerText = "out" + testCount;
			var oThis = menuCache[id];
			var w = oThis.getDocument().parentWindow;
			var fromEl	= getTrElement(w.event.fromElement);
			var toEl	= getTrElement(w.event.toElement);
			if (fromEl != null && toEl != fromEl) {
				var id = fromEl.parentNode.parentNode.id;
				var mi = fromEl._menuItem;		
				if (mi && (toEl != null || mi.subMenu == null || mi.disabled)) {
					// h2.innertText = "false";					
					mi.setSelected(false);
				}
			}
		},
		
		// 由于弹出窗口特有的点击其外部区域则自动hide的特性，若有子菜单，应该在moueup时候恢复过来
		onmouseup:	function (id) {
			var oThis = menuCache[id];
			var w = oThis.getDocument().parentWindow;
			var srcEl	= getMenuItemElement(w.event.srcElement);
			if (srcEl != null) {
				var id = srcEl.parentNode.parentNode.id;
				oThis.selectedIndex = srcEl.rowIndex;
				var menuItem = oThis.items[oThis.selectedIndex];
				menuItem.dispatchAction();
			}
		},

		onmousewheel:	function ( id ) {
			/*
			var oThis = menuCache[id];
			var d = oThis.getDocument();
			var w = d.parentWindow;
			var scrollContainer = d.getElementById("scroll-container");
			scrollContainer.scrollTop -= 3 * w.event.wheelDelta / 120 * ScrollButton.scrollAmount;
			*/
		},

		onreadystatechange:	function ( id ) {
			var oThis = menuCache[id];
			var d = oThis.getDocument();
			var linkEl = d.getElementsByTagName("LINK")[0];
			
			
			if ( linkEl.readyState == "complete" ) {
			
			
				//oThis.resetSizeCache();	// reset sizes
				
				
				// oThis.fixSize();
				//oThis.fixScrollButtons();
			}
		},

		oncloseinterval:	function (id) {
			 menuCache[id]._checkCloseState();
		}
	},


	menuItem:	{
		onshowtimer:	function (id) {
			var oThis = menuCache[id];
			var sm = oThis.subMenu;
			var pm = oThis.parentMenu;
			var selectedIndex = sm.getSelectedIndex();
			// 关闭所有其他的子菜单
			//pm.closeAllSubs(sm);
			//window.setTimeout("eventListeners.menuItem.onshowtimer2('" + id + "')", 1);
			eval("eventListeners.menuItem.onshowtimer2('" + id + "')");
		},

		onshowtimer2:	function (id) {
			var oThis = menuCache[id];
			var sm = oThis.subMenu;
			var selectedIndex = sm.getSelectedIndex();
			oThis.positionSubMenu();
			//sm.setSelectedIndex(selectedIndex);
			// commented by steve_gu at 2004-01-08，重复了，这么罗嗦干吗？
			// oThis.setSelected(true);
		},

		onclosetimer:	function ( id ) {
			var oThis = menuCache[id];
			var sm = oThis.subMenu;
			sm.close();
		},

		onpositionsubmenutimer:	function ( id ) {
			var oThis = menuCache[id];
			var sm = oThis.subMenu;
			sm.resetSizeCache();	// reset sizes
			oThis.positionSubMenu();
			sm.setSelectedIndex( 0 );
		}
	},

	menuBar:	{
		onmouseover:	function (id) {
			//window.status = "onmouseover in menubar";
			var oThis = menuCache[id];
			//var e = oThis.getDocument().parentWindow.event;
			e = event;
			if (e.srcElement.tagName == "DIV") return;
			
			var fromEl = getMenuItemElement(e.fromElement);
			var toEl = getMenuItemElement(e.toElement);
			var yy = e.screenY - e.offsetY;
			var xx = e.screenX - e.offsetX;
			//window.status = e.toElement.tagName;
			// toEl始终为span
			/** added by steve_gu at 2004-02-26  纠正在iframe中偏差 */
			if (e.srcElement.tagName == "SPAN") {
				posLib.topGettedByMouse = e.screenY - e.offsetY - 2;
			}
			if (e.srcElement.tagName == "TABLE") {
				posLib.topGettedByMouse = e.screenY - e.offsetY - 3;
			}
			if (e.srcElement.tagName == "TD") {
				posLib.topGettedByMouse = e.screenY - e.offsetY - 5;
			}

			if (e.srcElement.tagName == "SPAN") {
				posLib.leftGettedByMouse = e.screenX - e.offsetX -1;
			}
			if (e.srcElement.tagName == "TABLE") {
				posLib.leftGettedByMouse = e.screenX - e.offsetX - 1 - 4;
			}
			if (e.srcElement.tagName == "TD") {
				posLib.leftGettedByMouse = e.screenX - e.offsetX - 1 - 6;
			}
			/**   end add */
			if (toEl != null && toEl != fromEl) {
				var mb = toEl._menuItem;
				// 此处m为menuBar
				var m = mb.parentMenu;
				if (m.getActiveState() == "open" ) {
					// 如果menuBar已经处于打开状态，则鼠标切换到另外一个menuButton上面时自动打开菜单
					// alert("open");
					/*
					window.setTimeout(function () {
						mb.dispatchAction();
					}, 0);
					*/
					mb.dispatchAction();
				}
				else if (m.getActiveState() == "active") {
					// 从一个活动MenuButton中转移到另一个MenuButton上
					mb.setSelected(true);
				}
				else {					
					// 第一次点击MenuButton， 只改变按钮风格（使按钮突起），而不展开菜单
					mb._hover = true;
					toEl.className = mb.getCSSClass();
				}
			}
		},

		onmouseout:	function (id) {
			//window.status = "onmouseout in menubar";
			var oThis = menuCache[id];

			var e = oThis.getDocument().parentWindow.event;
			var fromEl = getMenuItemElement(e.fromElement);
			var toEl = getMenuItemElement(e.toElement);

			if (fromEl != null && toEl != fromEl) {
				var mb = fromEl._menuItem;
				mb._hover = false;
				// alert("false");
				fromEl.className = mb.getCSSClass();
			}
		},

		onmousedown:	function (id) {	
			//window.status = "onmousedown in menubar";
			var oThis = menuCache[id];
			var e = oThis.getDocument().parentWindow.event;
			// 如果不是左键，则返回
			if (e.button != MenuBar.leftMouseButton || e.srcElement.tagName == "DIV") {
				return;
			}
			// window.status = "onmousedown in menubar";
			/** added by steve_gu at 2004-02-26  */
			if (e.srcElement.tagName == "SPAN") {
				posLib.topGettedByMouse = e.screenY - e.offsetY - 2;
			}
			if (e.srcElement.tagName == "TABLE") {
				posLib.topGettedByMouse = e.screenY - e.offsetY - 3;
			}
			if (e.srcElement.tagName == "TD") {
				posLib.topGettedByMouse = e.screenY - e.offsetY - 5;
			}

			if (e.srcElement.tagName == "SPAN") {
				posLib.leftGettedByMouse = e.screenX - e.offsetX -1;
			}
			if (e.srcElement.tagName == "TABLE") {
				posLib.leftGettedByMouse = e.screenX - e.offsetX - 1 - 4;
			}
			if (e.srcElement.tagName == "TD") {
				posLib.leftGettedByMouse = e.screenX - e.offsetX - 1 - 6;
			}
			/**   end add */

			var el = getMenuItemElement(e.srcElement);
			if (el != null) {
				var mb = el._menuItem;
				if (mb.subMenu) {
					// 打开子菜单，复用MenuItem的dispatchAction()方法					
					//mb.dispatchAction();		
					// 下面的代码是控制点击MenuButton后展开下拉菜单，再点击时应收缩菜单
					mb.subMenu._checkCloseState();
										
					if (new Date() - mb.subMenu._closedAt > 100) {	// longer than the time do the hide					
						//window.status = "expand: " + (new Date() - mb.subMenu._closedAt);												
						mb.dispatchAction();
					}
					else {
						// window.status = "collaps: " + (new Date() - mb.subMenu._closedAt);
						mb._hover = true;
						mb._htmlElement.className = mb.getCSSClass();
					}
									
				}
				// added by steve_gu at 2004-05-20，处理MenuButton没有下拉菜单有动作的情况
				if (mb.menuButtonAction && typeof(mb.menuButtonAction) == "function") {
					mb.menuButtonAction();
				}
				if (mb.menuButtonAction && typeof(mb.menuButtonAction) == "string") {	// href
						if (mb.target != null) window.open(mb.menuButtonAction, mb.target);
						else document.location.href = mb.menuButtonAction;
				}
				// end add
			}
		},

		onkeydown:	function (id) {
			var oThis = menuCache[id];
			var e = oThis.getDocument().parentWindow.event;
			//oThis.handleKeyEvent(e);
		},

		onunload:	function (id) {
			//window.status = "onunload in menubar";
			menuCache[id].destroy();
		},

		ongotonextmenuitem:	function (id) {
			var oThis = menuCache[id];
			var mi = oThis.items[oThis.getSelectedIndex()];
			mi.dispatchAction();
			if (mi.subMenu) mi.subMenu.setSelectedIndex(0);
		},

		ongotopreviousmenuitem:	function (id) {
			var oThis = menuCache[id];
			var mi = oThis.items[oThis.getSelectedIndex()];
			mi.dispatchAction();
			if (mi.subMenu) mi.subMenu.setSelectedIndex(0);
		},
		// added by steve_gu at 2004-05-20
		onclick: function (id) {
		}
		// end add
	},

	menuButton: {
		onclose:	function (id) {
			menuCache[id].subMenuClosed();
		}
	}

}
