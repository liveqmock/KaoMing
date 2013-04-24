
//===============Tree==================================


var treeHandler = {
	idCounter : 0,
	// modified by steve_gu at 2004-06-17
	// Ϊ�˽ڵ���߼�id��������js����
	// idPrefix  : "tree-object-",
	idPrefix  : "treeObject",
	// end modify
	all       : {},
	behavior  : null,
	recursionChecked: true,
	selected  : null,
	onSelect  : null, /* should be part of tree, not handler */
	getId     : function() { return this.idPrefix + this.idCounter++; },
	toggle    : function (oItem) { this.all[oItem.id.replace('-plus','')].toggle(); },
	select    : function (oItem) { this.all[oItem.id.replace('-icon','')].select(); },
	focus     : function (oItem) { this.all[oItem.id.replace('-anchor','')].focus(); },
	blur      : function (oItem) { this.all[oItem.id.replace('-anchor','')].blur(); },
	keydown   : function (oItem, e) { return this.all[oItem.id].keydown(e.keyCode); },
	cookies   : new WebFXCookie(),
	// ѡ�е�����Id�� added by steve_gu at 2004-02-19
	checkedIds: [],
	// �������Ƶ�TreeAbsstractNode�У���Ϊʵ����صģ������ͬһ��ҳ�����ж��tree������
	// end add
	searchId  : "", // �������Ľڵ�Id��anchor�� added by steve_gu at 2004-03-02
	insertHTMLBeforeEnd	:	function (oElement, sHTML) {
		if (oElement.insertAdjacentHTML != null) {
			oElement.insertAdjacentHTML("BeforeEnd", sHTML)
			return;
		}
		var df;	// DocumentFragment
		var r = oElement.ownerDocument.createRange();
		r.selectNodeContents(oElement);
		r.collapse(false);
		df = r.createContextualFragment(sHTML);
		oElement.appendChild(df);
	},
	lastCheckedNode:null
};

/*
 * ===========================WebFXCookie class===============================
 */
function WebFXCookie() {
	if (document.cookie.length) { this.cookies = ' ' + document.cookie; }
}

WebFXCookie.prototype.setCookie = function (key, value) {
	document.cookie = key + "=" + escape(value);
}

WebFXCookie.prototype.getCookie = function (key) {
	if (this.cookies) {
		var start = this.cookies.indexOf(' ' + key + '=');
		if (start == -1) { return null; }
		var end = this.cookies.indexOf(";", start);
		if (end == -1) { end = this.cookies.length; }
		end -= start;
		var cookie = this.cookies.substr(start,end);
		return unescape(cookie.substr(cookie.indexOf('=') + 1, cookie.length - cookie.indexOf('=') + 1));
	}
	else { return null; }
}

/*
 * TreeAbstractNode class
 */
function TreeAbstractNode(sText,sValue, sAction) {
	this.childNodes  = [];
	this.id     = treeHandler.getId();
	this.value = sValue;
	this.text   = sText || treeConfig.defaultText;
	this.action = sAction || treeConfig.defaultAction;
	this._last  = false;
	/* added by steve_gu at 2004-02-27 */
	this.level = 0;
	/* end add */
	treeHandler.all[this.id] = this;
	// added by steve_gu at 2004-06-17
	// ���һ��ҳ�����ж����,getCheckedValues()��getCheckedTexts()����ȷ�����
	if (this instanceof Tree) {
		// alert(this.id);
	}	
	// end add
}

/*
 * To speed thing up if you're adding multiple nodes at once (after load)
 * use the bNoIdent parameter to prevent automatic re-indentation and call
 * the obj.ident() method manually once all nodes has been added.
 */
TreeAbstractNode.prototype.add = function (node, bNoIdent) {
	node.parentNode = this;	
	this.childNodes[this.childNodes.length] = node;
	var root = this;
	// ����Ϊlast�Ľڵ��Ϊ��last
	if (this.childNodes.length >= 2) {
		this.childNodes[this.childNodes.length - 2]._last = false;
	}
	/* added by steve_gu at 2004-02-27 */
	// ��Ϊ�����ӵĽڵ㲻�Ǹ��ڵ㣬��level������Ϊ1
	node.level++;	
	while (root.parentNode) { 
		root = root.parentNode; 
		node.level++;
	}
	

	// added by steve_gu at 2004-06-17,
	// ����ÿ���ڵ�ĸ��ڵ����ԣ��Դ�������ҳ���ж�����еĽڵ㡣
	this.rootNode = root;
	// ��Ϊ�ӽڵ�(�ܿ���ΪҶ�ӽڵ�)�����ô�add����������ҲҪ����childNodes����Ԥ�����úá�
	/*for (var i = 0; i < this.childNodes.length; i++) {
		this.childNodes[i].rootNode = root;
	}*/
	// �ݹ����ã�����Ӱ��Ч�ʣ������ɲ��ݹ�ģ���Ҫ���Ǹ��ӹ�ϵ��add�������ǴӲ�����(���ڵ�)��
	// ����ε͵Ľڵ�˳��������������Ǹ��ڵ���(������)���ӹ�ϵ�ȹ����������������ڵ㣬����
	// �����ݹ飬�Ǹ��ڵ��ǵĵĵڶ����rootNode�Ͳ�Ϊ���ڵ㣬������Ҫ���ˡ�
	function setRootNode(parentNode) {
		for (var i = 0; i < parentNode.childNodes.length; i++) {
			parentNode.childNodes[i].rootNode = root;
			setRootNode(parentNode.childNodes[i]);
		}		
	}	
	setRootNode(this);
	
	// end add
	/* end add */
	if (root.rendered) {
		if (this.childNodes.length >= 2) {
			document.getElementById(this.childNodes[this.childNodes.length - 2].id + '-plus').src = ((this.childNodes[this.childNodes.length -2].folder)?((this.childNodes[this.childNodes.length -2].open)?treeConfig.tMinusIcon:treeConfig.tPlusIcon):treeConfig.tIcon);
			this.childNodes[this.childNodes.length - 2].plusIcon = treeConfig.tPlusIcon;
			this.childNodes[this.childNodes.length - 2].minusIcon = treeConfig.tMinusIcon;
			this.childNodes[this.childNodes.length - 2]._last = false;
		}
		this._last = true;
		var foo = this;
		while (foo.parentNode) {
			for (var i = 0; i < foo.parentNode.childNodes.length; i++) {
				if (foo.id == foo.parentNode.childNodes[i].id) { break; }
			}
			if (i == foo.parentNode.childNodes.length - 1) { foo.parentNode._last = true; }
			else { foo.parentNode._last = false; }
			foo = foo.parentNode;
		}
		treeHandler.insertHTMLBeforeEnd(document.getElementById(this.id + '-cont'), node.toString());
		if ((!this.folder) && (!this.openIcon)) {
			this.icon = treeConfig.folderIcon;
			this.openIcon = treeConfig.openFolderIcon;
		}
		if (!this.folder) { this.folder = true; this.collapse(true); }
		if (!bNoIdent) { this.indent(); }
	}
	return node;
}

TreeAbstractNode.prototype.toggle = function() {
	if (this.folder) {
		if (this.open) this.collapse();
		else this.expand();
	}
}

TreeAbstractNode.prototype.select = function() {
	document.getElementById(this.id + '-anchor').focus();
}

TreeAbstractNode.prototype.deSelect = function() {
	document.getElementById(this.id + '-anchor').className = '';
	treeHandler.selected = null;
}

TreeAbstractNode.prototype.focus = function() {
	if ((treeHandler.selected) && (treeHandler.selected != this)) { treeHandler.selected.deSelect(); }
	treeHandler.selected = this;
	if ((this.openIcon) && (treeHandler.behavior != 'classic')) { document.getElementById(this.id + '-icon').src = this.openIcon; }
	document.getElementById(this.id + '-anchor').className = 'selected';
	document.getElementById(this.id + '-anchor').focus();
	if (treeHandler.onSelect) { treeHandler.onSelect(this); }
}

TreeAbstractNode.prototype.blur = function() {
	if ((this.openIcon) && (treeHandler.behavior != 'classic')) { document.getElementById(this.id + '-icon').src = this.icon; }
	document.getElementById(this.id + '-anchor').className = 'selected-inactive';
}

TreeAbstractNode.prototype.doExpand = function() {
	if (this.childNodes.length == 0) return; // added by steve_gu at 2004-02-27: if leaf, no expand
	if (treeHandler.behavior == 'classic') { document.getElementById(this.id + '-icon').src = this.openIcon; }
	if (this.childNodes.length) {  document.getElementById(this.id + '-cont').style.display = 'block'; }
	this.open = true;
	if (treeConfig.usePersistence) {
		treeHandler.cookies.setCookie(this.id, '1');
	}	
}

TreeAbstractNode.prototype.doCollapse = function() {
	if (this.childNodes.length == 0) return; // added by steve_gu at 2004-02-27: if leaf, no collapse
	if (treeHandler.behavior == 'classic') { document.getElementById(this.id + '-icon').src = this.icon; }
	if (this.childNodes.length) { document.getElementById(this.id + '-cont').style.display = 'none'; }
	this.open = false;
	if (treeConfig.usePersistence) {
		treeHandler.cookies.setCookie(this.id, '0');
	}	
}

TreeAbstractNode.prototype.expandAll = function() {
	this.expandChildren();
	if ((this.folder) && (!this.open)) { this.expand(); }
}

TreeAbstractNode.prototype.expandChildren = function() {
	for (var i = 0; i < this.childNodes.length; i++) {
		this.childNodes[i].expandAll();
	} 
}

TreeAbstractNode.prototype.collapseAll = function() {
	this.collapseChildren();
	if ((this.folder) && (this.open)) { this.collapse(true); }
}

TreeAbstractNode.prototype.collapseChildren = function() {
	for (var i = 0; i < this.childNodes.length; i++) {
		this.childNodes[i].collapseAll();
	} 
}

TreeAbstractNode.prototype.indent = function(lvl, del, last, level, nodesLeft) {
	/*
	 * Since we only want to modify items one level below ourself,
	 * and since the rightmost indentation position is occupied by
	 * the plus icon we set this to -2
	 */
	if (lvl == null) { lvl = -2; }
	var state = 0;
	for (var i = this.childNodes.length - 1; i >= 0 ; i--) {
		state = this.childNodes[i].indent(lvl + 1, del, last, level);
		if (state) { return; }
	}
	if (del) {
		if ((level >= this._level) && (document.getElementById(this.id + '-plus'))) {
			if (this.folder) {
				document.getElementById(this.id + '-plus').src = (this.open)?treeConfig.lMinusIcon:treeConfig.lPlusIcon;
				this.plusIcon = treeConfig.lPlusIcon;
				this.minusIcon = treeConfig.lMinusIcon;
			}
			else if (nodesLeft) { document.getElementById(this.id + '-plus').src = treeConfig.lIcon; }
			return 1;
	}	}
	var foo = document.getElementById(this.id + '-indent-' + lvl);
	if (foo) {
		if ((foo._last) || ((del) && (last))) { foo.src =  treeConfig.blankIcon; }
		else { foo.src =  treeConfig.iIcon; }
	}
	return 0;
}

/** modified by steve_gu at 2004-02-25 */
TreeAbstractNode.prototype.getSelected = function() {
	if (treeHandler.selected) { return treeHandler.selected; }
	else { return null; }
}

TreeAbstractNode.prototype.getSelectedText= function() {
	if(this.getSelected()) {
		return this.getSelected().text;
	}
	else {
		alert("��ѡ�нڵ㣡��");
		return null;
	}
}

TreeAbstractNode.prototype.getSelectedValue= function() {
	if(this.getSelected()) {
		return this.getSelected().value;
	}
	else {
		alert("��ѡ�нڵ㣡��");
		return null;
	}
}
/** end modified */

/*
 * Tree class
 * added scheckbox by zxh 
 */
function Tree(sText,sValue,sAction,sBehavior, sIcon, sOpenIcon,bDisplay, sTitle) {
	this.base = TreeAbstractNode;
	this.base(sText,sValue,sAction);
	this.icon      = sIcon || treeConfig.rootIcon;
	this.openIcon  = sOpenIcon || treeConfig.openRootIcon;
	this.display = bDisplay || treeConfig.defaultDisplayRoot;
	this.title = sTitle;
	if(!bDisplay) {this.display=bDisplay}
	//�ж�bDisplay�����Ƿ����
	if(bDisplay==NaN || bDisplay == null){this.display=true}

	/* Defaults to open */
	if (treeConfig.usePersistence) {
		this.open  = (treeHandler.cookies.getCookie(this.id) == '0')?false:true;
	} else { this.open  = true; }
	this.folder    = true;
	this.rendered  = false;
	this.onSelect  = null;
	if (!treeHandler.behavior) {  treeHandler.behavior = sBehavior || treeConfig.defaultBehavior; }
	
	this.checkedIds = [];
	// ͨ��һ���ۼ�
	/*
	var checkedIds
	var myId = {
	checkedIds     :[],
	searchedId     : 0 
	};
	*/
}

Tree.prototype = new TreeAbstractNode();

//�����Ƿ�ݹ�ѡ�й��� added by zxh
Tree.prototype.setRecursionChecked = function (sRecursionChecked) {
	treeHandler.recursionChecked =  sRecursionChecked;
};

Tree.prototype.setPicPath = function (sPicPath) {
	treePicPath =  sPicPath;
};

Tree.prototype.setBehavior = function (sBehavior) {
	treeHandler.behavior =  sBehavior;
};

Tree.prototype.getBehavior = function (sBehavior) {
	return treeHandler.behavior;
};

Tree.prototype.remove = function() { }

Tree.prototype.expand = function() {
	this.doExpand();
}

Tree.prototype.collapse = function(b) {
	if (!b) { this.focus(); }
	this.doCollapse();
}

Tree.prototype.getFirst = function() {
	return null;
}

Tree.prototype.getChildNodes = function() {
	return null;
}

Tree.prototype.getLast = function() {
	return null;
}

Tree.prototype.getNextSibling = function() {
	return null;
}

Tree.prototype.getPreviousSibling = function() {
	return null;
}

Tree.prototype.keydown = function(key) {
	if (key == 39) {
		if (!this.open) { this.expand(); }
		else if (this.childNodes.length) { this.childNodes[0].select(); }
		return false;
	}
	if (key == 37) { this.collapse(); return false; }
	if ((key == 40) && (this.open) && (this.childNodes.length)) { this.childNodes[0].select(); return false; }
	return true;
}
//ȥ��<form name=frmTree>
Tree.prototype.toString = function() {
	
	/** modified by steve_gu at 2004-04-09 ��ģʽ���������ڵ�û��action���򲻲��ó�����*/
	if ( (this.action == null || this.action.indexOf("javascript:void(0)") != -1 || this.action == "")||(treeConfig.noActionNoAnchor && treeConfig.noActionNoAnchor == true )) {
		var str = "<div onmousedown=\"treeHandler.select(this);treeHandler.focus(this);\" id=\"" + this.id + "\" style=\"display: " + ((this.display)?'block':'none') + ";\" ondblclick=\"treeHandler.toggle(this);\" class=\"tree-item\" onkeydown=\"return treeHandler.keydown(this, event)\">" +
		"<img id=\"" + this.id + "-icon\" class=\"tree-icon\" src=\"" + ((treeHandler.behavior == 'classic' && this.open)?this.openIcon:this.icon) + "\" onclick=\"treeHandler.select(this);\">" +
		"<span" + (this.title ? " title=\"" + this.title + "\"" : "") + " id=\"" + this.id + "-anchor\" onfocus=\"treeHandler.focus(this);\" onblur=\"treeHandler.blur(this);\"" +	">" + this.text + "</span></div>" +
		"<div id=\"" + this.id + "-cont\" class=\"tree-container\" style=\"display: " + ((this.open)?'block':'none') + ";\">";		
	}
	else {
		var str = "<div onmousedown=\"treeHandler.select(this);\" id=\"" + this.id + "\" style=\"display: " + ((this.display)?'block':'none') + ";\" ondblclick=\"treeHandler.toggle(this);\" class=\"tree-item\" onkeydown=\"return treeHandler.keydown(this, event)\">" +
		"<img id=\"" + this.id + "-icon\" class=\"tree-icon\" src=\"" + ((treeHandler.behavior == 'classic' && this.open)?this.openIcon:this.icon) + "\" onclick=\"treeHandler.select(this);\">" +
		"<a href=\"" + this.action + "\" id=\"" + this.id + "-anchor\" onfocus=\"treeHandler.focus(this);\" onblur=\"treeHandler.blur(this);\"" +
		(this.target ? " target=\"" + this.target + "\"" : "") + (this.title ? " title=\"" + this.title + "\"" : "") + 
		">" + this.text + "</a></div>" +
		"<div id=\"" + this.id + "-cont\" class=\"tree-container\" style=\"display: " + ((this.open)?'block':'none') + ";\">";
	}

	var sb = [];
	for (var i = 0; i < this.childNodes.length; i++) {
		sb[i] = this.childNodes[i].toString(i, this.childNodes.length);
	}
	this.rendered = true;
	return str + sb.join("") + "</div>";
};

/**
 * �õ���ѡ�еĽڵ�Id���� added by steve_gu at 2004-02-19
 */
Tree.prototype.getCheckedIds = function() {
	return this.rootNode.checkedIds;
}

/** 
 * ���ó�ʼ��ʱ����ʾ���� added by steve_gu at 2004-02-27 
 */
Tree.prototype.setInitDisplayLevel = function(iLevel) {
	function setChildNodeDisplay(oTreeNode) {
		for (var i = 0; i < oTreeNode.childNodes.length; i++) {
			var jsObjNode = treeHandler.all[oTreeNode.childNodes[i].id];
			if (jsObjNode.level < iLevel) jsObjNode.open = true;
			else jsObjNode.open = false;
			// �ݹ�
			setChildNodeDisplay(oTreeNode.childNodes[i]);
		}	
	}
	if (iLevel <= 0) {
		this.open = false;
	}
	setChildNodeDisplay(this);	
}

Tree.prototype.displayCheckedNodes = function() {
	function displayChecked(oTreeNode) {
		for (var i = 0; i < oTreeNode.childNodes.length; i++) {			
			var jsObjNode = treeHandler.all[oTreeNode.childNodes[i].id];			
			if ((jsObjNode instanceof CheckBoxTreeItem || jsObjNode instanceof RadioTreeItem) && (jsObjNode._checked == true || jsObjNode._checked =="true")) {				
				while(jsObjNode.parentNode) {
					jsObjNode.parentNode.open = true;
					jsObjNode = jsObjNode.parentNode;
				}		
			}
			// recursive
			displayChecked(oTreeNode.childNodes[i]);
		}		
	}
	displayChecked(this);
}
/**
 * ������ʾ���������ڵ� added by steve_gu at 2004-03-01
 */
Tree.prototype.findNodeByText = function(sText) {
	var oTree = this;
	var searched = false;
	if (this.text == sText) {
		treeHandler.searchId = this.id + "-anchor";				
		window.setTimeout("document.getElementById(treeHandler.searchId).focus();",0);
		searched = true;
		return;
	}
	searchChildNodes(tree);
	function searchChildNodes(oTreeNode) {
		for (var i = 0; i < oTreeNode.childNodes.length; i++) {
			var jsObjNode = treeHandler.all[oTreeNode.childNodes[i].id];
			if (jsObjNode.text == sText) {
				var objNode = jsObjNode;
				// ��֤�������Ľڵ��ܹ���ʾ
				while(objNode.parentNode) {					
					objNode.parentNode.open = true;
					objNode.parentNode.expand();
					objNode = objNode.parentNode;
				}								
				treeHandler.searchId = oTreeNode.childNodes[i].id + "-anchor";		
				window.setTimeout("document.getElementById(treeHandler.searchId).focus();",0);
				searched = true;
				return;
			}			
			// �ݹ�
			searchChildNodes(oTreeNode.childNodes[i]);
		}	
	}
	if (searched == false) {
		alert("�Բ���û���ҵ��������Ľڵ㣡");	
	}
}

/**
 * ���ݽڵ��ڲ�ֵ�����ڵ� added by steve_gu at 2004-03-02
 */
Tree.prototype.findNodeByValue = function(sValue) {
	var oTree = this;
	var searched = false;
	if (this.value == sValue) {
		treeHandler.searchId = this.id + "-anchor";				
		window.setTimeout("document.getElementById(treeHandler.searchId).focus();",0);
		searched = true;
		return;
	}
	searchChildNodes(tree);
	function searchChildNodes(oTreeNode) {
		for (var i = 0; i < oTreeNode.childNodes.length; i++) {
			var jsObjNode = treeHandler.all[oTreeNode.childNodes[i].id];
			if (jsObjNode.value == sValue) {
				var objNode = jsObjNode;
				// ��֤�������Ľڵ��ܹ���ʾ
				while(objNode.parentNode) {					
					objNode.parentNode.open = true;
					objNode.parentNode.expand();
					objNode = objNode.parentNode;
				}								
				treeHandler.searchId = oTreeNode.childNodes[i].id + "-anchor";		
				window.setTimeout("document.getElementById(treeHandler.searchId).focus();",0);
				searched = true;
				return;
			}			
			// �ݹ�
			searchChildNodes(oTreeNode.childNodes[i]);
		}	
	}
	if (searched == false) {
		alert("�Բ���û���ҵ��������Ľڵ㣡");	
	}
}


/*
 * ==================TreeItem class==============
 */


function TreeItem(sText, sValue,sAction, eParent, sIcon, sOpenIcon,sDisable,sTarget,sTitle) {
	this.base = TreeAbstractNode;
	this.base(sText,sValue,sAction);

	/* Defaults to close */
	if (treeConfig.usePersistence) {
		this.open = (treeHandler.cookies.getCookie(this.id) == '1')?true:false;
	} else { this.open = false; }
	if (sIcon) { this.icon = sIcon; }
	if (sOpenIcon) { this.openIcon = sOpenIcon; }
	if (eParent && (eParent instanceof Tree || eParent instanceof TreeItem || eParent instanceof LoadTreeItem)) { eParent.add(this); }
	if (sDisable) { this._disabled=sDisable; }
	if(sTarget){this.target=sTarget;}
	if (sTitle) this.title = sTitle;	
}


TreeItem.prototype = new TreeAbstractNode;

TreeItem.prototype.remove = function() {
	var iconSrc = document.getElementById(this.id + '-plus').src;
	var parentNode = this.parentNode;
	var prevSibling = this.getPreviousSibling(true);
	var nextSibling = this.getNextSibling(true);
	var folder = this.parentNode.folder;
	var last = ((nextSibling) && (nextSibling.parentNode) && (nextSibling.parentNode.id == parentNode.id))?false:true;
	if (this.getPreviousSibling()) {
		this.getPreviousSibling().focus();
	}
	this._remove();
	if (parentNode.childNodes.length == 0) {
		document.getElementById(parentNode.id + '-cont').style.display = 'none';
		parentNode.doCollapse();
		parentNode.folder = false;
		parentNode.open = false;
	}
	if (!nextSibling || last) { parentNode.indent(null, true, last, this._level, parentNode.childNodes.length); }
	if ((prevSibling == parentNode) && !(parentNode.childNodes.length)) {
		prevSibling.folder = false;
		prevSibling.open = false;
		iconSrc = document.getElementById(prevSibling.id + '-plus').src;
		iconSrc = iconSrc.replace('minus', '').replace('plus', '');
		document.getElementById(prevSibling.id + '-plus').src = iconSrc;
		document.getElementById(prevSibling.id + '-icon').src = treeConfig.fileIcon;
	}

	if (prevSibling && document.getElementById(prevSibling.id + '-plus')) {
		if (parentNode == prevSibling.parentNode) {
			iconSrc = iconSrc.replace('minus', '').replace('plus', '');
			document.getElementById(prevSibling.id + '-plus').src = iconSrc;
		}	
	}	
}

TreeItem.prototype._remove = function() {
	for (var i = this.childNodes.length - 1; i >= 0; i--) {
		this.childNodes[i]._remove();
 	}
	for (var i = 0; i < this.parentNode.childNodes.length; i++) {
		if (this == this.parentNode.childNodes[i]) {
			for (var j = i; j < this.parentNode.childNodes.length; j++) {
				this.parentNode.childNodes[j] = this.parentNode.childNodes[j+1];
			}
			this.parentNode.childNodes.length -= 1;
			if (i + 1 == this.parentNode.childNodes.length) { this.parentNode._last = true; }
			break;
	}	}
	treeHandler.all[this.id] = null;
	var tmp = document.getElementById(this.id);
	if (tmp) { tmp.parentNode.removeChild(tmp); }
	tmp = document.getElementById(this.id + '-cont');
	if (tmp) { tmp.parentNode.removeChild(tmp); }
}

TreeItem.prototype.expand = function() {
	if (!this.folder) return; // added by steve_gu at 2004-02-27: if leaf, no expand
	this.doExpand();
	document.getElementById(this.id + '-plus').src = this.minusIcon;
}

TreeItem.prototype.collapse = function(b) {
	if (!b) { this.focus(); }
	if (!this.folder) return; // added by steve_gu at 2004-02-27: if leaf, no collapse
	this.doCollapse();
	document.getElementById(this.id + '-plus').src = this.plusIcon;
}

TreeItem.prototype.getFirst = function() {
	return this.childNodes[0];
}

//�õ������ӽڵ� added by zxh 
TreeItem.prototype.getChildNodes = function() {
	var childNodes="";
	for (var i = 0; i < this.childNodes.length; i++) {
		childNodes+=","+this.childNodes[i];
	}
	if(childNodes!="")childNodes=childNodes.substring(1);
	return childNodes;
}

TreeItem.prototype.getLast = function() {
	if (this.childNodes[this.childNodes.length - 1].open) { return this.childNodes[this.childNodes.length - 1].getLast(); }
	else { return this.childNodes[this.childNodes.length - 1]; }
}

TreeItem.prototype.getNextSibling = function() {
	for (var i = 0; i < this.parentNode.childNodes.length; i++) {
		if (this == this.parentNode.childNodes[i]) { break; }
	}
	// modified by steve_gu at 2004-03-07 ͳһǰһ�ڵ����һ�ڵ��Ϊ�ֵܽڵ�
	if (++i == this.parentNode.childNodes.length) { return null; }//this.parentNode.getNextSibling(); }
	else { return this.parentNode.childNodes[i]; }
}

TreeItem.prototype.getPreviousSibling = function(b) {
	for (var i = 0; i < this.parentNode.childNodes.length; i++) {
		if (this == this.parentNode.childNodes[i]) { break; }
	}
	// modified by steve_gu at 2004-03-07 ͳһǰһ�ڵ����һ�ڵ��Ϊ�ֵܽڵ�
	if (i == 0) { return null;}//this.parentNode; }
	else {
		/*if ((this.parentNode.childNodes[--i].open) || (b && this.parentNode.childNodes[i].folder)) { return this.parentNode.childNodes[i].getLast(); }
		else { return this.parentNode.childNodes[i]; }*/
		return this.parentNode.childNodes[--i];
	}
}

TreeItem.prototype.keydown = function(key) {
	if ((key == 39) && (this.folder)) {
		if (!this.open) { this.expand(); }
		else { this.getFirst().select(); }
		return false;
	}
	else if (key == 37) {
		if (this.open) { this.collapse(); }
		else { this.parentNode.select(); }
		return false;
	}
	else if (key == 40) {
		if (this.open) { this.getFirst().select(); }
		else {
			var sib = this.getNextSibling();
			if (sib) { sib.select(); }
		}
		return false;
	}
	else if (key == 38) { this.getPreviousSibling().select(); return false; }
	return true;
}

TreeItem.prototype.toString = function (nItem, nItemCount) {
	var foo = this.parentNode;
	var indent = '';
	if (nItem + 1 == nItemCount) { this.parentNode._last = true; }
	var i = 0;
	while (foo.parentNode) {
		foo = foo.parentNode;
		indent = "<img id=\"" + this.id + "-indent-" + i + "\" src=\"" + ((foo._last)?treeConfig.blankIcon:treeConfig.iIcon) + "\">" + indent;
		i++;
	}
	this._level = i;
	if (this.childNodes.length) { this.folder = 1; }
	else { this.open = false; }
	if ((this.folder) || (treeHandler.behavior != 'classic')) {
		if (!this.icon) { this.icon = treeConfig.folderIcon; }
		if (!this.openIcon) { this.openIcon = treeConfig.openFolderIcon; }
	}
	else if (!this.icon) { this.icon = treeConfig.fileIcon; }
	/** modified by steve_gu at 2004-04-07 */
	//var label = this.text.replace(/</g, '&lt;').replace(/>/g, '&gt;');
	var label = this.text;
	/** end modify */
	if (treeConfig.noActionNoAnchor && treeConfig.noActionNoAnchor == true && (this.action == null || this.action.indexOf("javascript:void(0)") != -1 || this.action == "")) {
		var str = "<div onclick=\"treeHandler.select(this);treeHandler.focus(this);\" id=\"" + this.id + "\" ondblclick=\"treeHandler.toggle(this);\" class=\"tree-item\" onkeydown=\"return treeHandler.keydown(this, event)\">";	
	}
	else {
		var str = "<div onclick=\"treeHandler.select(this);\" id=\"" + this.id + "\" ondblclick=\"treeHandler.toggle(this);\" class=\"tree-item\" onkeydown=\"return treeHandler.keydown(this, event)\">";
	}
	str+=
		indent +
		"<img id=\"" + this.id + "-plus\" src=\"" + ((this.folder)?((this.open)?((this.parentNode._last)?treeConfig.lMinusIcon:treeConfig.tMinusIcon):((this.parentNode._last)?treeConfig.lPlusIcon:treeConfig.tPlusIcon)):((this.parentNode._last)?treeConfig.lIcon:treeConfig.tIcon)) + "\" onclick=\"treeHandler.toggle(this);\">" +
		"<img id=\"" + this.id + "-icon\" class=\"tree-icon\" src=\"" + ((treeHandler.behavior == 'classic' && this.open)?this.openIcon:this.icon) + "\" onclick=\"treeHandler.select(this);\">" ;
		if(!eval(this._disabled)){
			/** modified by steve_gu at 2004-04-09 ��ģʽ���������ڵ�û��action���򲻲��ó�����*/
			if ((this.action == null || this.action.indexOf("javascript:void(0)") != -1 || this.action == "")||(treeConfig.noActionNoAnchor && treeConfig.noActionNoAnchor == true )) {
				str+="<span href=\"" + this.action + "\" id=\"" + this.id + "-anchor\" style=\"disabled: " + ((this._disabled)?'block':'none') + ";\" onmousedown=\"treeHandler.focus(this);\" onblur=\"treeHandler.blur(this);\"" +
				(this.target ? " target=\"" + this.target + "\"" : "") + (this.title ? " title=\"" + this.title + "\"" : "") +
				">" + label + "</span>" ;			
			}
			else {
				str+="<a href=\"" + this.action + "\" id=\"" + this.id + "-anchor\" style=\"disabled: " + ((this._disabled)?'block':'none') + ";\" onfocus=\"treeHandler.focus(this);\" onblur=\"treeHandler.blur(this);\"" +
				(this.target ? " target=\"" + this.target + "\"" : "") + (this.title ? " title=\"" + this.title + "\"" : "") + 
				">" + label + "</a>" ;
			}
		}else{
			str+="<a id=\"" + this.id + "-anchor\"  onfocus=\"treeHandler.focus(this);\" onblur=\"treeHandler.blur(this);\" style=\"color:gray;\" >"+label+"</a>";
		}
		str+="</div><div id=\"" + this.id + "-cont\" class=\"tree-container\" style=\"display: " + ((this.open)?'block':'none') + ";\">";
	var sb = [];
	for (var i = 0; i < this.childNodes.length; i++) {
		sb[i] = this.childNodes[i].toString(i,this.childNodes.length);
	}
	this.plusIcon = ((this.parentNode._last)?treeConfig.lPlusIcon:treeConfig.tPlusIcon);
	this.minusIcon = ((this.parentNode._last)?treeConfig.lMinusIcon:treeConfig.tMinusIcon);
	return str + sb.join("") + "</div>";
}

//=============================CheckBoxTreeItem===========================/

//add param sValue 
function CheckBoxTreeItem(sText,sValue,sAction, bChecked, eParent, sIcon, sOpenIcon,sDisable,sTarget, sTitle) {	
	
	this.base = TreeItem;
	this.base(sText, sValue,sAction, eParent, sIcon, sOpenIcon);
	this._checked = bChecked;

	if (sDisable) { this._disabled=sDisable; }
	if(sTarget){this.target=sTarget;}
	if (sTitle) this.title = sTitle;
}


CheckBoxTreeItem.prototype = new TreeItem;

CheckBoxTreeItem.prototype.toString = function (nItem, nItemCount) {
	//if (!this.forGroup) this.rootNode.checkedIds.push(this.id);
	//window.status = window.status + ":" + this.rootNode;
	if (this._checked == true) {
		 //this.rootNode.checkedIds.push(this.id);
	}
	else {
		//alert("false");
	}
	var foo = this.parentNode;
	var indent = '';
	if (nItem + 1 == nItemCount) { this.parentNode._last = true; }
	var i = 0;
	while (foo.parentNode) {
		foo = foo.parentNode;
		indent = "<img id=\"" + this.id + "-indent-" + i + "\" src=\"" + ((foo._last)?treeConfig.blankIcon:treeConfig.iIcon) + "\">" + indent;
		i++;
	}
	this._level = i;
	if (this.childNodes.length) { this.folder = 1; }
	else { this.open = false; }
	if ((this.folder) || (treeHandler.behavior != 'classic')) {
		if (!this.icon) { this.icon = treeConfig.folderIcon; }
		if (!this.openIcon) { this.openIcon = treeConfig.openFolderIcon; }
	}
	else if (!this.icon) { this.icon = treeConfig.fileIcon; }
	//var label = this.text.replace(/</g, '&lt;').replace(/>/g, '&gt;');
	var label = this.text;
	if (treeConfig.noActionNoAnchor && treeConfig.noActionNoAnchor == true && (this.action == null || this.action.indexOf("javascript:void(0)") != -1 || this.action == "")) {
		var str = "<div onmousedown=\"treeHandler.select(this);treeHandler.focus(this);\" id=\"" + this.id + "\" ondblclick=\"treeHandler.toggle(this);\" class=\"tree-item\" onkeydown=\"return treeHandler.keydown(this, event)\">";	
	}
	else {
		var str = "<div onmousedown=\"treeHandler.select(this);\" id=\"" + this.id + "\" ondblclick=\"treeHandler.toggle(this);\" class=\"tree-item\" onkeydown=\"return treeHandler.keydown(this, event)\">";
	}
	str += indent;
	str += "<img id=\"" + this.id + "-plus\" src=\"" + ((this.folder)?((this.open)?((this.parentNode._last)?treeConfig.lMinusIcon:treeConfig.tMinusIcon):((this.parentNode._last)?treeConfig.lPlusIcon:treeConfig.tPlusIcon)):((this.parentNode._last)?treeConfig.lIcon:treeConfig.tIcon)) + "\" onclick=\"treeHandler.toggle(this);\">"
	
	// insert check box
	// modified by steve_gu at 2004-05-31 ����disable
	if (!eval(this._disabled)) {
		str += "<input type=\"checkbox\"" +
			" class=\"tree-check-box\"" +
			(this._checked ? " checked=\"checked\"" : "") +
			" value=\""+this.value+"\" name=\""+this.parentNode.id+"_chk\" style=\"Disabled:true;\" id=\""+this.id+"-checkbox\" onclick=\"treeHandler.all[this.id.replace('-checkbox','')].check(this.checked,treeHandler.recursionChecked)\"" +
			" />";	
	}
	else {
		str += "<input type=\"checkbox\"" +
			" disabled=true class=\"tree-check-box\"" +
			(this._checked ? " checked=\"checked\"" : "") +
			" value=\""+this.value+"\" name=\""+this.parentNode.id+"_chk\" style=\"Disabled:true;\" id=\""+this.id+"-checkbox\" onclick=\"treeHandler.all[this.id.replace('-checkbox','')].check(this.checked,treeHandler.recursionChecked)\"" +
			" />";	
	}
	// end modify
	/*
	if(eval(this._disabled)){
		str += "<SCRIPT LANGUAGE=\"JavaScript\">document.getElementById(\""+this.id+"-checkbox\").disabled=true;</script>"		
	}*/
	// end insert checkbox  	
	str += "<img id=\"" + this.id + "-icon\" class=\"tree-icon\" src=\"" + ((treeHandler.behavior == 'classic' && this.open)?this.openIcon:this.icon) + "\" onclick=\"treeHandler.select(this);\">";
	if(!eval(this._disabled)){
			/** modified by xt  ��ģʽ���������ڵ�û��action���򲻲��ó�����*/
			if ( (this.action == null || this.action.indexOf("javascript:void(0)") != -1 || this.action == "")) {
				str+="<span" + (this.title ? " title=\"" + this.title + "\"" : "") + " id='"  + this.id + "-anchor' style=\"disabled: " + ((this._disabled)?'block':'none') + ";\" onblur=\"treeHandler.blur(this);\"" +
				">" + label + "</span>" ;			
			}
			else {
				str+="<a href=\"" + this.action + "\" id=\"" + this.id + "-anchor\" style=\"disabled: " + ((this._disabled)?'block':'none') + ";\" onfocus=\"treeHandler.focus(this);\" onblur=\"treeHandler.blur(this);\"" +
				(this.target ? " target=\"" + this.target + "\"" : "") + (this.title ? " title=\"" + this.title + "\"" : "") + 
				">" + label + "</a>" ;
			}
		}else{
			str+="<a id=\"" + this.id + "-anchor\"  onfocus=\"treeHandler.focus(this);\" onblur=\"treeHandler.blur(this);\" style=\"color:gray;\" >"+label+"</a>";
	}

	str += "</div><div id=\"" + this.id + "-cont\" class=\"tree-container\" style=\"display: " + ((this.open)?'block':'none') + ";\">";
	for (var i = 0; i < this.childNodes.length; i++) {
		str += this.childNodes[i].toString(i,this.childNodes.length);
	}
	str += "</div>";
	this.plusIcon = ((this.parentNode._last)?treeConfig.lPlusIcon:treeConfig.tPlusIcon);
	this.minusIcon = ((this.parentNode._last)?treeConfig.lMinusIcon:treeConfig.tMinusIcon);
	// added by steve_gu at 2004-06-18
	if(this._checked && (this._checked == true || this._checked == "true")) {
		this.rootNode.checkedIds.push(this.id);
		// added by steve_gu at 2004-09-22��������ǰ�Ľӿ�
		treeHandler.checkedIds.push(this.id);
		// end add
	}
	// end add
	return str;
}

CheckBoxTreeItem.prototype.check=function(bChecked,sRecursionChecked){
	this._checked = bChecked;
	var htmlObj = document.getElementById(this.id +"-checkbox");
	// ������ѡ������checkedIds
	if(htmlObj){
		if(this._checked){			
			// add checked :added by steve_gu at 2004-02-19
			// modified by steve_gu at 2004-04-14
			// added by steve_gu at 2004-08-30
			if (!this.forGroup) {
				this.rootNode.checkedIds.push(this.id);
				treeHandler.checkedIds.push(this.id);
			}
			/*
			if (this.parentNode && this.parentNode instanceof CheckBoxTreeItem) {
				var checkParent = true;
				for (var i = 0; i < this.parentNode.childNodes.length; i++) {					
					if (!this.parentNode.childNodes[i]._checked) checkParent = false;					
				}
				if (checkParent) {
					this.parentNode.check(true);				
					var nodeDiv = document.getElementById(this.parentNode.id);
					for (var i = 0; i < nodeDiv.children.length; i++) {
						if (nodeDiv.children[i].type=="checkbox") {
							nodeDiv.children[i].checked=true;							
						}
					}
				}
			}
			*/
			// end add
		}
		else{
			// delete unchecked :added by steve_gu at 2004-02-19
			var res = [];
			for (var i = 0; i < this.rootNode.checkedIds.length; i++) {
				if (this.rootNode.checkedIds[i] != this.id) {
					res.push(this.rootNode.checkedIds[i]);			
				} 
			}
			this.rootNode.checkedIds = res;
			// added by steve_gu at 2004-09-22
			var res1 = [];
			for (var i = 0; i < treeHandler.checkedIds.length; i++) {
				if (treeHandler.checkedIds[i] != this.id) {
					res1.push(treeHandler.checkedIds[i]);			
				} 
			}
			treeHandler.checkedIds = res1;
		}

		// ���Ӹ���ѡ�й�ϵ
		if (this.parentNode && this.parentNode instanceof CheckBoxTreeItem) {
			var checkedCount = 0;
			var childNodesCount = this.parentNode.childNodes.length;
			for (var i = 0; i < childNodesCount; i++) {					
				if (this.parentNode.childNodes[i]._checked) checkedCount++;
			}
			var nodeDiv = document.getElementById(this.parentNode.id);
			var nodeDivCheckbox = null;
			for (var i = 0; i < nodeDiv.children.length; i++) {
				if (nodeDiv.children[i].type=="checkbox") {
					nodeDivCheckbox = nodeDiv.children[i];//.checked=true;	
					break;
				}
			}
			if (nodeDivCheckbox) {	
			
				if (checkedCount == 0) {
					if (this.parentNode._checked == true) {
						this.parentNode.check(false);
						nodeDivCheckbox.checked = false;						
					}
					nodeDivCheckbox.style.backgroundColor="";
				}
				else if (checkedCount == childNodesCount) {
					if (this.parentNode._checked == false) {
						this.parentNode.check(true);
						nodeDivCheckbox.checked = true;
					}									
					nodeDivCheckbox.style.backgroundColor="";
				}
				else {	
					if (this.parentNode._checked == false) {
						this.parentNode.check(true);
						nodeDivCheckbox.checked = true;
					}					
					nodeDivCheckbox.style.background="#333366";					
				}			
			}
		}
		// end add
	}
	// �ݹ�ѡ��
	if(this.childNodes.length !=0 && sRecursionChecked == true){
		for(var i=0;i<this.childNodes.length;i++){	
			var childNode = document.getElementById(this.childNodes[i].id +"-checkbox");
			if((this.childNodes[i] instanceof TreeItem)){
				// �����Ϊ�ݹ���ı�
				if (this.childNodes[i]._checked != bChecked) {
					// ͬʱ�ı�HTML�������Ժ�JS�������ԣ��ҵݹ�
					childNode.checked = bChecked;
					this.childNodes[i].check(bChecked,treeHandler.recursionChecked);	
				}				
			}				
		}
	}
	treeHandler.lastCheckedNode = this;
	if (typeof CheckBoxTreeItem.oncheck == "function") {
		CheckBoxTreeItem.oncheck();
	}
}

//CheckBoxTreeItem.prototype.toString

//=============================RadioTreeItem===========================/
function RadioTreeItem(sText,sValue, sAction, bChecked, eParent, sIcon, sOpenIcon,sRadioName,bDisabled,sTitle) {
	this.base = TreeItem;
	this.base(sText, sValue, sAction, eParent, sIcon, sOpenIcon);
	this._checked = bChecked;
	this._radioName=sRadioName;	
	if (bDisabled) this._disabled = bDisabled;
	if (sTitle) this.title = sTitle;
}

RadioTreeItem.prototype = new TreeItem;

RadioTreeItem.prototype.toString = function (nItem, nItemCount) {
	var foo = this.parentNode;
	// added by steve_gu at 2004-02-19 ͬһ�����radioTreeItemĬ��Ӧ��ȡ��ͬ��һ�����ƣ���������radionName, ���趨��������
	/*
	if (this.parentNode && !this._radioName){
		this._radioName= this.parentNode._radioName + "1";//sRadioName;
	}
	else {
		if (!this._radioName) {
			this._radioName= "root";
		}		
	}
	*/
	/** modified by steve_gu at 2004-04-12, ʹ���߼��������� */
	if (!this._radioName) {
		if (this.parentNode) {
			this._radioName= this.parentNode._radioName + "1";
		}
		else {
			this._radioName= "root";
		}
	}
	/** end modify */
	// end add
	var indent = '';
	if (nItem + 1 == nItemCount) { this.parentNode._last = true; }
	var i = 0;
	while (foo.parentNode) {
		foo = foo.parentNode;
		indent = "<img id=\"" + this.id + "-indent-" + i + "\" src=\"" + ((foo._last)?treeConfig.blankIcon:treeConfig.iIcon) + "\">" + indent;
		i++;
	}
	this._level = i;
	if (this.childNodes.length) { this.folder = 1; }
	else { this.open = false; }
	if ((this.folder) || (treeHandler.behavior != 'classic')) {
		if (!this.icon) { this.icon = treeConfig.folderIcon; }
		if (!this.openIcon) { this.openIcon = treeConfig.openFolderIcon; }
	}
	else if (!this.icon) { this.icon = treeConfig.fileIcon; }

	// var label = this.text.replace(/</g, '&lt;').replace(/>/g, '&gt;');
	var label = this.text;

	if (treeConfig.noActionNoAnchor && treeConfig.noActionNoAnchor == true && (this.action == null || this.action.indexOf("javascript:void(0)") != -1 || this.action == "")) {
		var str = "<div onmousedown=\"treeHandler.select(this);treeHandler.focus(this);\" id=\"" + this.id + "\" ondblclick=\"treeHandler.toggle(this);\" class=\"tree-item\" onkeydown=\"return treeHandler.keydown(this, event)\">";	
	}
	else {
		var str = "<div onmousedown=\"treeHandler.select(this);\" id=\"" + this.id + "\" ondblclick=\"treeHandler.toggle(this);\" class=\"tree-item\" onkeydown=\"return treeHandler.keydown(this, event)\">";
	}
	str += indent;
	str += "<img id=\"" + this.id + "-plus\" src=\"" + ((this.folder)?((this.open)?((this.parentNode._last)?treeConfig.lMinusIcon:treeConfig.tMinusIcon):((this.parentNode._last)?treeConfig.lPlusIcon:treeConfig.tPlusIcon)):((this.parentNode._last)?treeConfig.lIcon:treeConfig.tIcon)) + "\" onclick=\"treeHandler.toggle(this);\">"
	
	// insert radio box
	// modified by steve_gu at 2004-05-31 ����disable
	if (!eval(this._disabled)) {
		str += "<input type=\"radio\"" +
			" class=\"tree-check-box\"" +
			(this._checked ? " checked=\"checked\"" : "") +
			" value=\""+this.value+"\"  name=\""+this._radioName+"\" id=\""+this.id+"-radiobutton\"  " +
			"onclick=\"treeHandler.all[this.id.replace('-radiobutton','')].check(this.checked)\"/>";
	}
	else { // disabled
		str += "<input type=\"radio\"" +
			" disabled=true class=\"tree-check-box\"" +
			(this._checked ? " checked=\"checked\"" : "") +
			" value=\""+this.value+"\"  name=\""+this._radioName+"\" id=\""+this.id+"-radiobutton\"  " +
			"onclick=\"treeHandler.all[this.id.replace('-radiobutton','')].check(this.checked)\"/>";
	}
	// end modify
	// end insert radio box
	

	str += "<img id=\"" + this.id + "-icon\" class=\"tree-icon\" src=\"" + ((treeHandler.behavior == 'classic' && this.open)?this.openIcon:this.icon) + "\" onclick=\"treeHandler.select(this);\">";

	if(!eval(this._disabled)){
		/** modified by steve_gu at 2004-04-09 ��ģʽ���������ڵ�û��action���򲻲��ó�����*/
		if (treeConfig.noActionNoAnchor && treeConfig.noActionNoAnchor == true && (this.action == null || this.action.indexOf("javascript:void(0)") != -1 || this.action == "")) {
			str += "<span " + (this.title ? " target=\"" + this.title + "\"" : "") + " id=\"" + this.id + "-anchor\" onblur=\"treeHandler.blur(this);\">" + label + "</span></div>";			
		}
		else {
			str += "<a " + (this.title ? " target=\"" + this.title + "\"" : "") + " href=\"" + this.action + "\" id=\"" + this.id + "-anchor\" onfocus=\"treeHandler.focus(this);\" onblur=\"treeHandler.blur(this);\">" + label + "</a></div>";
		}
	}else{
		str+="<a id=\"" + this.id + "-anchor\"  onfocus=\"treeHandler.focus(this);\" onblur=\"treeHandler.blur(this);\" style=\"color:gray;\" >"+label+"</a></div>";
	}	
	str += "<div id=\"" + this.id + "-cont\" class=\"tree-container\" style=\"display: " + ((this.open)?'block':'none') + ";\">";
	for (var i = 0; i < this.childNodes.length; i++) {
		str += this.childNodes[i].toString(i,this.childNodes.length);
	}
	str += "</div>";
	this.plusIcon = ((this.parentNode._last)?treeConfig.lPlusIcon:treeConfig.tPlusIcon);
	this.minusIcon = ((this.parentNode._last)?treeConfig.lMinusIcon:treeConfig.tMinusIcon);
	// added by steve_gu at 2004-06-18
	if(this._checked && (this._checked == true || this._checked == "true")) {
		this.rootNode.checkedIds.push(this.id);
		treeHandler.checkedIds.push(this.id); // added by steve_gu at 2004-09-22
	}
	// end add
	return str;
}

/**
 * added by steve_gu at 2004-02-19
 */
RadioTreeItem.prototype.check = function(bChecked){
	// ��ʵ���bCheckedʼ����true
	this._checked = bChecked;
	
	// ������ѡ������checkedIds
	if(this._checked){
		// add checked :added by steve_gu at 2004-02-19
		if (this.rootNode.checkedIds.toString().indexOf(this.id) == -1) {
			this.rootNode.checkedIds.push(this.id);
			treeHandler.checkedIds.push(this.id); // added by steve_gu at 2004-09-22
		}
	}
	// ͬʱ������֮�����radioTreeItem

	/** modified by steve_gu at 2004-04-12 */
	// ͬʱ������֮�����radioTreeItem
	var res = [];
	for (var i = 0; i < this.rootNode.checkedIds.length; i++) {
		if (this.id != this.rootNode.checkedIds[i] && this._radioName == treeHandler.all[this.rootNode.checkedIds[i]]._radioName && treeHandler.all[this.rootNode.checkedIds[i]] instanceof RadioTreeItem) {			
			// ����Ĳ�����
			treeHandler.all[this.rootNode.checkedIds[i]]._checked = false;
		}
		else {
			res.push(this.rootNode.checkedIds[i]);
		}
	}
	this.rootNode.checkedIds = res;
	//// added by steve_gu at 2004-09-22
	var res1 = [];
	for (var i = 0; i < treeHandler.checkedIds.length; i++) {
		if (this.id != treeHandler.checkedIds[i] && this._radioName == treeHandler.all[treeHandler.checkedIds[i]]._radioName && treeHandler.all[treeHandler.checkedIds[i]] instanceof RadioTreeItem) {			
			// ����Ĳ�����
			//treeHandler.all[this.rootNode.checkedIds[i]]._checked = false;
		}
		else {
			res1.push(treeHandler.checkedIds[i]);
		}
	}
	treeHandler.checkedIds = res1; 
	// end add
	/** end modify */
	// added by steve_gu at 2004-08-27
	treeHandler.lastCheckedNode = this;
	if (typeof RadioTreeItem.oncheck == "function") {
		RadioTreeItem.oncheck();
	}
}

/**
 * @deprecated ����Tree.prototype.getCheckedValues����
 */
function getCheckedValues(){
	
	var UserSelectRadioValue="";
	var hiddenValues = [];
	
	for (var i = 0;i < treeHandler.checkedIds.length; i++) {
		hiddenValues.push(treeHandler.all[treeHandler.checkedIds[i]].value);
	}
	return hiddenValues;
} 


/**
 * @deprecated ����Tree.prototype.getCheckedTexts����
 */
function  getCheckedTexts(){
	var UserSelectRadioValue="";
	var hiddenValues = [];
	for (var i = 0;i < treeHandler.checkedIds.length; i++) {
		hiddenValues.push(treeHandler.all[treeHandler.checkedIds[i]].text);
	}
	return hiddenValues;
} 

// �õ�ѡ�нڵ��ֵ,����ǰһ���汾
/**
 * @deprecated ����Tree.prototype.getCheckedValues����
 */
function getHidden(){
	return getCheckedValues().join(",");
}

/**
 * �õ�ѡ�нڵ��ֵ����ӦCheckboxTreeItem��RadioTreeItem�еڶ���������ֵ��
 */
Tree.prototype.getCheckedValues = function() {
    var hiddenValues = [];
    if(this.rootNode!=undefined){
	for (var i = 0;i < this.rootNode.checkedIds.length; i++) {
		hiddenValues.push(treeHandler.all[this.rootNode.checkedIds[i]].value);
	}	
    }
	return hiddenValues;
}
/**
 * �Ƚ�Node����(arr)���Ƿ����Node(obj),����������ڵ�ѡ�еĻ������ӽڵ�
 **/
function containsNode(arr,obj){
	for(var i=0;i<arr.length;i++){
		if(arr[i]===obj){
			return true;
		}
		var objParent=obj.parentNode;
		while(objParent!=null){
			if(arr[i]===objParent){
				return true;
			}
			objParent=objParent.parentNode;
		}
	}
	return false;
}
/**
 * for checkedIds reload, add By Sin@2010-5-26
 **/
function nodeIdAsc(x,y){
	x=parseInt(x.replace(treeHandler.idPrefix,""));
	y=parseInt(y.replace(treeHandler.idPrefix,""));
	return x==y?0:(x>y?1:-1);
	
}
/**
 * ��ȡ����ѡ�еĽڵ�ֵ, add By Sin@2010-5-26
 * @param lvFilter ����Node����
 **/
Tree.prototype.getRealCheckedValues=function(){
	var hiddenValues=[];
	var hiddenNode=[];
	var chkChecks=this.rootNode.checkedIds;
	chkChecks.sort(nodeIdAsc);
	if(this.rootNode!=undefined){
		for (var i = 0;i < chkChecks.length; i++) {
			var nodeDiv=document.getElementById(this.rootNode.checkedIds[i]);
			var nodeDivCheckbox = null;
			for (var j = 0; j < nodeDiv.children.length; j++) {
				if (nodeDiv.children[j].type=="checkbox") {
					nodeDivCheckbox = nodeDiv.children[j];//.checked=true;	
					break;
				}
			}
			if(""==nodeDivCheckbox.style.background){
				if(!containsNode(hiddenNode,treeHandler.all[this.rootNode.checkedIds[i]].parentNode) && (arguments.length>0 && treeHandler.all[this.rootNode.checkedIds[i]].level!=parseInt(arguments[0]))){
					hiddenValues.push(treeHandler.all[this.rootNode.checkedIds[i]].value);
					hiddenNode.push(treeHandler.all[this.rootNode.checkedIds[i]]);
				}
			}
		}	
    }
	return hiddenValues;
}
/**
 * ��ȡ����ѡ�еĽڵ�Text, add By Sin@2010-5-26
 * @param lvFilter ����Node����
 **/
Tree.prototype.getRealCheckedTexts=function(){
	var hiddenValues=[];
	var hiddenNode=[];
	if(this.rootNode!=undefined){
		for (var i = 0;i < this.rootNode.checkedIds.length; i++) {
			var nodeDiv=document.getElementById(this.rootNode.checkedIds[i]);
			var nodeDivCheckbox = null;
			for (var j = 0; j < nodeDiv.children.length; j++) {
				if (nodeDiv.children[j].type=="checkbox") {
					nodeDivCheckbox = nodeDiv.children[j];//.checked=true;	
					break;
				}
			}
			if(""==nodeDivCheckbox.style.background){
				if(!containsNode(hiddenNode,treeHandler.all[this.rootNode.checkedIds[i]].parentNode) && (arguments.length>0 && treeHandler.all[this.rootNode.checkedIds[i]].level!=parseInt(arguments[0]))){
					hiddenValues.push(treeHandler.all[this.rootNode.checkedIds[i]].text);
					hiddenNode.push(treeHandler.all[this.rootNode.checkedIds[i]]);
				}
			}
		}	
    }
	return hiddenValues;
}
/**
 * ��ȡ����ѡ�еĽڵ�Target, add By Sin@2010-5-26
 * @param lvFilter ����Node����
 **/
Tree.prototype.getRealCheckedTargets=function(){
	var hiddenValues=[];
	var hiddenNode=[];
	if(this.rootNode!=undefined){
		for (var i = 0;i < this.rootNode.checkedIds.length; i++) {
			var nodeDiv=document.getElementById(this.rootNode.checkedIds[i]);
			var nodeDivCheckbox = null;
			for (var j = 0; j < nodeDiv.children.length; j++) {
				if (nodeDiv.children[j].type=="checkbox") {
					nodeDivCheckbox = nodeDiv.children[j];//.checked=true;	
					break;
				}
			}
			if(""==nodeDivCheckbox.style.background){
				if(!containsNode(hiddenNode,treeHandler.all[this.rootNode.checkedIds[i]].parentNode) && (arguments.length>0 && treeHandler.all[this.rootNode.checkedIds[i]].level!=parseInt(arguments[0]))){
					hiddenValues.push(treeHandler.all[this.rootNode.checkedIds[i]].target);
					hiddenNode.push(treeHandler.all[this.rootNode.checkedIds[i]]);
				}
			}
		}	
    }
	return hiddenValues;
}
Tree.prototype.getLeafCheckedValues = function() {
	var hiddenValues = [];
	if (this.rootNode==null)
	{
		return hiddenValues;
	};
	for (var i = 0;i < this.rootNode.checkedIds.length; i++) {
		if (treeHandler.all[this.rootNode.checkedIds[i]].childNodes.length == 0)
		hiddenValues.push(treeHandler.all[this.rootNode.checkedIds[i]].value);
	}	
	return hiddenValues;
}

Tree.prototype.getLeafCheckedTitles = function() {
	var hiddenValues = [];
	if (this.rootNode==null)
	{
		return hiddenValues;
	};
	for (var i = 0;i < this.rootNode.checkedIds.length; i++) {
		if (treeHandler.all[this.rootNode.checkedIds[i]].childNodes.length == 0)
		hiddenValues.push(treeHandler.all[this.rootNode.checkedIds[i]].title);
	}	
	return hiddenValues;
}

Tree.prototype.getLeafCheckedTexts = function() {
	var hiddenValues = [];
	if (this.rootNode==null)
	{
		return hiddenValues;
	};
	for (var i = 0;i < this.rootNode.checkedIds.length; i++) {
		if (treeHandler.all[this.rootNode.checkedIds[i]].childNodes.length == 0)
		hiddenValues.push(treeHandler.all[this.rootNode.checkedIds[i]].text);
	}	
	return hiddenValues;
}


/**
 * �õ�ѡ�нڵ��ֵ����ӦCheckboxTreeItem��RadioTreeItem�еڶ���������ֵ��
 */
Tree.prototype.getCheckedTitles = function() {
	var hiddenValues = [];
	for (var i = 0;i < this.rootNode.checkedIds.length; i++) {
		hiddenValues.push(treeHandler.all[this.rootNode.checkedIds[i]].title);
	}	
	return hiddenValues;
}

Tree.prototype.getCheckedTargets = function() {
	var hiddenValues = [];
	for (var i = 0;i < this.rootNode.checkedIds.length; i++) {
		hiddenValues.push(treeHandler.all[this.rootNode.checkedIds[i]].target);
	}	
	return hiddenValues;
}


/**
 * �õ�ѡ�нڵ����ʾ������ӦCheckboxTreeItem��RadioTreeItem�е�һ��������ֵ��
 */
Tree.prototype.getCheckedTexts = function() {
	var hiddenValues = [];
	for (var i = 0;i < this.rootNode.checkedIds.length; i++) {
		hiddenValues.push(treeHandler.all[this.rootNode.checkedIds[i]].text);
	}
	return hiddenValues;
} 

/**
 * added by steve_gu at 2004-08-05
 * �õ�ѡ�нڵ���󣬴���������
 */
Tree.prototype.getCheckedObjects = function() {
	var checkedObjects = [];
	for (var i = 0;i < this.rootNode.checkedIds.length; i++) {
		checkedObjects.push(treeHandler.all[this.rootNode.checkedIds[i]]);
	}
	return checkedObjects;
} 

///////////////////// added by steve_gu at 2004-06-23 //////////////////////
/**
 * Ϊ����ǰ��getHidden()����ֵ�ĸ�ʽһ��(�Զ��ŷָ����ַ���)���ṩ��ƥ�Է���
 */
Tree.prototype.getCheckedStringValues = function() {
	var values = "";
	var first = true;
	for (var i = 0;i < this.rootNode.checkedIds.length; i++) {
		if (first) {
			values = treeHandler.all[this.rootNode.checkedIds[i]].value;
			first = false;
		}
		else {
			values = values + "," + treeHandler.all[this.rootNode.checkedIds[i]].value;
		}
	}
	return values;
}

Tree.prototype.getCheckedStringTexts = function() {
	var texts = "";
	var first = true;
	for (var i = 0;i < this.rootNode.checkedIds.length; i++) {
		if (first) {
			texts = treeHandler.all[this.rootNode.checkedIds[i]].text;
			first = false;
		}
		else {
			texts = values + "," + treeHandler.all[this.rootNode.checkedIds[i]].text;
		}
	}
	return texts;
}
///////////////////// end add ///////////////////////////////////////////////


/**************************** added by steve_gu at 2004-06-09 ****************/
/**************************** below is loadtree specific code ****************/
// treeConfig.loadingText = "Loading...";
// treeConfig.loadErrorTextTemplate = "Error loading \"%1%\"";
// treeConfig.emptyErrorTextTemplate = "Error \"%1%\" does not contain any tree items";

/*
 * LoadTree class
 */
/*
function LoadTree(sText, sXmlSrc, sAction, sBehavior, sIcon, sOpenIcon) {
	// call super
	this.Tree = Tree;
	this.Tree(sText, sAction, sBehavior, sIcon, sOpenIcon);

	// setup default property values
	this.src = sXmlSrc;
	this.loading = false;
	this.loaded = false;
	this.errorText = "";

	// check start state and load if open
	if (this.open)
		_startLoadXmlTree(this.src, this);
	else {
		// and create loading item if not
		// modified by steve_gu at 2004-06-09
		// this._loadingItem = new TreeItem(treeConfig.loadingText);
		// this.add(this._loadingItem);
		// end modify
	}
}

LoadTree.prototype = new Tree;

// override the expand method to load the xml file
LoadTree.prototype._tree_expand = Tree.prototype.expand;
LoadTree.prototype.expand = function() {
	if (!this.loaded && !this.loading) {
		// load
		_startLoadXmlTree(this.src, this);
	}
	this._tree_expand();
};
*/

/*
 * LoadTreeItem class
 */

function LoadTreeItem(sText, sXmlSrc, sAction, eParent, sIcon, sOpenIcon) {
	// call super
	this.TreeItem = TreeItem;
	//function TreeItem(sText, sValue,sAction, eParent, sIcon, sOpenIcon,sDisable,sTarget,sTitle) {
	this.TreeItem(sText,"",sAction, eParent, sIcon, sOpenIcon);

	// setup default property values
	this.src = sXmlSrc;
	this.loading = false;
	this.loaded = false;
	this.errorText = "";

	// check start state and load if open
	if (this.open) {
		//alert("open");
		_startLoadXmlTree(this.src, this);
	}
	else {
		// and create loading item if not�����û�д򿪣���Ԥ����һ���ӽڵ㣬�Ա�չ��
		// �ýڵ��ڱ�չ����������ɾ��
		//alert("2");
		this._loadingItem = new TreeItem(treeConfig.loadingText);
		this.add(this._loadingItem);
	}
}

LoadTreeItem.prototype = new TreeItem;

// override the expand method to load the xml file
LoadTreeItem.prototype._treeitem_expand = TreeItem.prototype.expand;
LoadTreeItem.prototype.expand = function() {
	if (!this.loaded && !this.loading) {
		// load
		_startLoadXmlTree(this.src, this);
	}
	this._treeitem_expand();
};
/*
// reloads the src file if already loaded
LoadTree.prototype.reload =
LoadTreeItem.prototype.reload = function () {
	// if loading do nothing
	if (this.loaded) {
		var open = this.open;
		// remove
		while (this.childNodes.length > 0)
			this.childNodes[this.childNodes.length - 1].remove();

		this.loaded = false;
		// modified by steve_gu at 2004-06-09
		//this._loadingItem = new TreeItem(treeConfig.loadingText);
		//this.add(this._loadingItem);
		// showStatusLabel(window, constDownLoadingData, _editor);

		if (open)
			this.expand();
	}
	else if (this.open && !this.loading)
		_startLoadXmlTree(this.src, this);
};
*/

/*
 * Helper functions
 */

// creates the xmlhttp object and starts the load of the xml document
function _startLoadXmlTree(sSrc, jsNode) {
	if (jsNode.loading || jsNode.loaded)
		return;
	jsNode.loading = true;
	
	var xmlHttp = XmlHttp.create();
	//alert("sSrc = " + sSrc);
	xmlHttp.open("GET", sSrc, true);	// async
	xmlHttp.onreadystatechange = function () {
		if (xmlHttp.readyState == 4) {
			//alert("xmlHttp.responseXML = " + xmlHttp.responseXML);
			//alert("xmlHttp.responseXML.documentElement = " + xmlHttp.responseXML.documentElement);
				
			var xmlDoc = XmlDocument.create();
			var s = "" + xmlHttp.responseText + "";
			//alert("s:\n" + s);
			//s = "<tree text='text1' />";
			xmlDoc.loadXML(s);			
			

			var testEls = xmlDoc.getElementsByTagName("tree");
			//alert("testEls.length = " + testEls.length);
			for (var i = 0; i < testEls.length; i++)
			alert(testEls[i].getAttribute("text"));

			_xmlFileLoaded(xmlHttp.responseXML, jsNode);
		}
	};
	// call in new thread to allow ui to update
	window.setTimeout(function () {
		xmlHttp.send(null);
	}, 10);

}

// Converts an xml tree to a js tree. See article about xml tree format
function _xmlTreeToJsTree(oNode) {
	// retreive attributes
	var text = oNode.getAttribute("text");
	var action = oNode.getAttribute("action");
	var parent = null;
	var icon = oNode.getAttribute("icon");
	var openIcon = oNode.getAttribute("openIcon");
	var src = oNode.getAttribute("src");
	var target = oNode.getAttribute("target");
	// create jsNode
	var jsNode;
	if (src != null && src != "")
		jsNode = new LoadTreeItem(text, src, action, parent, icon, openIcon);
	else
		jsNode = new TreeItem(text,"",action, parent, icon, openIcon);

	if (target != "")
		jsNode.target = target;

	// go through childNOdes
	var cs = oNode.childNodes;
	var l = cs.length;
	for (var i = 0; i < l; i++) {
		if (cs[i].tagName == "tree")
			jsNode.add( _xmlTreeToJsTree(cs[i]), true );
	}

	return jsNode;
}

// Inserts an xml document as a subtree to the provided node
function _xmlFileLoaded(oXmlDoc, jsParentNode) {
	if (jsParentNode.loaded) return;
	var bIndent = false;
	var bAnyChildren = false;
	jsParentNode.loaded = true;
	jsParentNode.loading = false;
	// alert("oXmlDoc = " + (oXmlDoc==null));
	// alert("oXmlDoc.documentElement = " + (oXmlDoc.documentElement==null));

	// check that the load of the xml file went well
	if( oXmlDoc == null || oXmlDoc.documentElement == null) {
		// alert(oXmlDoc.xml);
		alert("û�����ݣ��������������Ƿ񱻹رգ�");
		//jsParentNode.errorText = parseTemplateString(treeConfig.loadErrorTextTemplate,jsParentNode.src);
	}
	else {
		// there is one extra level of tree elements
		var root = oXmlDoc.documentElement;

		// loop through all tree children
		var cs = root.childNodes;
		var l = cs.length;
		for (var i = 0; i < l; i++) {
			if (cs[i].tagName == "tree") {
				bAnyChildren = true;
				bIndent = true;
				jsParentNode.add( _xmlTreeToJsTree(cs[i]), true);
			}
		}

		// if no children we got an error
		if (!bAnyChildren){
			jsParentNode.errorText = parseTemplateString(treeConfig.emptyErrorTextTemplate,jsParentNode.src);
		}
	}

	// remove dummy
	if (jsParentNode._loadingItem != null) {
		// alert((jsParentNode._loadingItem) instanceof TreeItem);
		jsParentNode._loadingItem.remove();
		bIndent = true;
	}

	if (bIndent) {
		// indent now that all items are added
		jsParentNode.indent();
	}

	// show error in status bar
	if (jsParentNode.errorText != "")
		window.status = jsParentNode.errorText;
}

// parses a string and replaces %n% with argument nr n
function parseTemplateString(sTemplate) {
	var args = arguments;
	var s = sTemplate;

	s = s.replace(/\%\%/g, "%");

	for (var i = 1; i < args.length; i++)
		s = s.replace( new RegExp("\%" + i + "\%", "g"), args[i] )

	return s;
}

/******************* below is xmlextras.js *********************/
//<script>
//////////////////
// Helper Stuff //
//////////////////

// used to find the Automation server name
function getDomDocumentPrefix() {
	if (getDomDocumentPrefix.prefix)
		return getDomDocumentPrefix.prefix;
	
	var prefixes = ["MSXML2", "Microsoft", "MSXML", "MSXML3"];
	var o;
	for (var i = 0; i < prefixes.length; i++) {
		try {
			// try to create the objects
			o = new ActiveXObject(prefixes[i] + ".DomDocument");
			return getDomDocumentPrefix.prefix = prefixes[i];
		}
		catch (ex) {};
	}
	
	throw new Error("Could not find an installed XML parser");
}

function getXmlHttpPrefix() {
	if (getXmlHttpPrefix.prefix)
		return getXmlHttpPrefix.prefix;
	
	var prefixes = ["MSXML2", "Microsoft", "MSXML", "MSXML3"];
	var o;
	for (var i = 0; i < prefixes.length; i++) {
		try {
			// try to create the objects
			o = new ActiveXObject(prefixes[i] + ".XmlHttp");
			return getXmlHttpPrefix.prefix = prefixes[i];
		}
		catch (ex) {};
	}
	
	throw new Error("Could not find an installed XML parser");
}

//////////////////////////
// Start the Real stuff //
//////////////////////////


// XmlHttp factory
function XmlHttp() {}

XmlHttp.create = function () {
	try {
		if (window.XMLHttpRequest) {
			var req = new XMLHttpRequest();
			
			// some versions of Moz do not support the readyState property
			// and the onreadystate event so we patch it!
			if (req.readyState == null) {
				req.readyState = 1;
				req.addEventListener("load", function () {
					req.readyState = 4;
					if (typeof req.onreadystatechange == "function")
						req.onreadystatechange();
				}, false);
			}
			
			return req;
		}
		if (window.ActiveXObject) {
			return new ActiveXObject(getXmlHttpPrefix() + ".XmlHttp");
		}
	}
	catch (ex) {}
	// fell through
	throw new Error("Your browser does not support XmlHttp objects");
};

// XmlDocument factory
function XmlDocument() {}

XmlDocument.create = function () {
	try {
		// DOM2
		if (document.implementation && document.implementation.createDocument) {
			var doc = document.implementation.createDocument("", "", null);
			
			// some versions of Moz do not support the readyState property
			// and the onreadystate event so we patch it!
			if (doc.readyState == null) {
				doc.readyState = 1;
				doc.addEventListener("load", function () {
					doc.readyState = 4;
					if (typeof doc.onreadystatechange == "function")
						doc.onreadystatechange();
				}, false);
			}
			
			return doc;
		}
		if (window.ActiveXObject)
			return new ActiveXObject(getDomDocumentPrefix() + ".DomDocument");
	}
	catch (ex) {}
	throw new Error("Your browser does not support XmlDocument objects");
};

// Create the loadXML method and xml getter for Mozilla
if (window.DOMParser &&
	window.XMLSerializer &&
	window.Node && Node.prototype && Node.prototype.__defineGetter__) {

	// XMLDocument did not extend the Document interface in some versions
	// of Mozilla. Extend both!
	XMLDocument.prototype.loadXML = 
	Document.prototype.loadXML = function (s) {
		
		// parse the string to a new doc	
		var doc2 = (new DOMParser()).parseFromString(s, "text/xml");
		
		// remove all initial children
		while (this.hasChildNodes())
			this.removeChild(this.lastChild);
			
		// insert and import nodes
		for (var i = 0; i < doc2.childNodes.length; i++) {
			this.appendChild(this.importNode(doc2.childNodes[i], true));
		}
	};
		
	/*
	 * xml getter
	 *
	 * This serializes the DOM tree to an XML String
	 *
	 * Usage: var sXml = oNode.xml
	 *
	 */
	// XMLDocument did not extend the Document interface in some versions
	// of Mozilla. Extend both!
	XMLDocument.prototype.__defineGetter__("xml", function () {
		return (new XMLSerializer()).serializeToString(this);
	});
	Document.prototype.__defineGetter__("xml", function () {
		return (new XMLSerializer()).serializeToString(this);
	});
}