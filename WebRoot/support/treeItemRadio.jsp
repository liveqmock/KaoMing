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
			menu.add(new MenuItem("ȫ��չ��",expandAll));
			menu.add(new MenuItem("ȫ���۵�",collapseAll));
			menu.add(new MenuSeparator());
		}		
		//menu.add(new MenuItem("�� ʾ ��",function() {alert(tree.getSelected().text);}));
		//menu.add(new MenuItem("�� �� ֵ",function() {alert(tree.getSelected().value);}));
		//menu.add(new MenuSeparator());
		//menu.add(new MenuItem("����ʾ������", function() {tree.findNodeByText(prompt("������Ҫ�����Ľڵ�����",""));}));
		//menu.add(new MenuItem("���ڲ�ֵ����", function() {tree.findNodeByValue(prompt("������Ҫ�����Ľڵ��ڲ�ֵ",""));}));
		//menu.add(new MenuSeparator());
		//menu.add(new MenuItem("ѡ�еĽڵ�����",function() {alert("��ѡ���ˣ�\n" + tree.getCheckedTexts());}));
		//menu.add(new MenuItem("ѡ�еĽڵ�ֵ",function() {alert("��ѡ���ˣ�\n" + tree.getCheckedValues());}));
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