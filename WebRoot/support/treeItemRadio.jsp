<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.util.ArrayList"%> 

<html>
<head>
<link rel="stylesheet" href="css/treeStyle.css">	
<SCRIPT  src="js/tree/client_ini.js"></SCRIPT>
<SCRIPT  src="js/tree/menu.js"></SCRIPT>
<SCRIPT  src="js/tree/xtree.js"></SCRIPT> 
<SCRIPT  src="js/common.js"></SCRIPT>
</head>

<%    
	String strTree = (String)request.getAttribute("tree");

 %>
<body>
<form name=form1>
<TABLE>

<SCRIPT LANGUAGE="JavaScript">
	//treeConfig.openRootIcon='images/xp/rootIcon.png';
	var data = new Array();
	try{
	
<%
	out.println(strTree);
%>
	
		for (var i = 0; i < data.length; i++) {
			if (data[i][3] == "0") {				
				eval("var node" + data[i][0] + "= new Tree('" + data[i][1] + "','" + data[i][2] + "')");
				eval("var tree=node" + data[i][0]);
			}else {
				eval("var node" + data[i][0] + "=new RadioTreeItem('" + data[i][1] + "','" + data[i][2] + "',null,"+data[i][4]+",node" + data[i][3] + ",null,null,null,null,"+data[i][0]+")");
				
			}
		}
		tree.display = false;
		//function RadioTreeItem(sText,sValue, sAction, bChecked, eParent, sIcon, sOpenIcon,sRadioName,bDisabled,sTitle) {}
		RadioTreeItem.oncheck = function() {
			f_return(tree.getSelected().text,tree.getSelected().value,tree.getSelected().title);
		};
		tree.displayCheckedNodes();
	}catch (e){
	}
</SCRIPT>

</TABLE>
<div id="treeContainer">
		<SCRIPT LANGUAGE="JavaScript">
		<!--
			document.write(tree);
		//-->
		</SCRIPT>		
		</div>
</form>
</body>

</html>

<SCRIPT LANGUAGE="JavaScript">
<!--
//marketDep.expand();
	function createPopupMenu() {
		function collapse() {
			tree.getSelected().collapse();
		}
		function expand() {
			tree.getSelected().expand();
		}
		function collapseAll() {
			tree.getSelected().collapseAll();
		}
		function expandAll() {
			tree.getSelected().expandAll();
		}

		var menu = new Menu();
		if (tree.getSelected().childNodes.length > 0) {
			menu.add(new MenuItem("全部展开",expandAll));
			menu.add(new MenuItem("全部折叠",collapseAll));
			menu.add(new MenuSeparator());
		}		
		//menu.add(new MenuItem("显 示 名",function() {alert(tree.getSelected().text);}));
		//menu.add(new MenuItem("内 部 值",function() {alert(tree.getSelected().value);}));
		//menu.add(new MenuSeparator());
		//menu.add(new MenuItem("按显示名查找", function() {tree.findNodeByText(prompt("请输入要搜索的节点名称",""));}));
		//menu.add(new MenuItem("按内部值查找", function() {tree.findNodeByValue(prompt("请输入要搜索的节点内部值",""));}));
		//menu.add(new MenuSeparator());
		//menu.add(new MenuItem("选中的节点名称",function() {alert("你选中了：\n" + tree.getCheckedTexts());}));
		//menu.add(new MenuItem("选中的节点值",function() {alert("你选中了：\n" + tree.getCheckedValues());}));
		return menu;			
	}

	document.getElementById("treeContainer").oncontextmenu = function() {
		createPopupMenu().show(event.screenX, event.screenY);
		return false;
	};
	
	function f_return(subName,allName,code) {
		//alert("allName="+allName);
		//alert("subName="+subName);
		//alert("code="+code);
		var treeInfo=new Array(3);
		treeInfo[0]=code;
		treeInfo[1]=subName;
		treeInfo[2]=allName;
		returnValue=treeInfo;
		
		self.close();
	}
	 
//-->
</SCRIPT>