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
	String strFlag = (String)request.getAttribute("flag")==null?"":(String)request.getAttribute("flag");
	//System.out.println("********strTree="+strTree);
	
 %>
<body>
<form name=form1>
<TABLE>
<TR>
<TD>
<SCRIPT LANGUAGE="JavaScript">
	treeConfig.openRootIcon='images/xp/rootIcon.png';
	var data = new Array();
	try{
	
<%
	out.println(strTree);
%>
	
	for (var i = 0; i < data.length; i++) {
		if(data[i]!=undefined){
			if (data[i][3] == "0") {				
				eval("var node" + data[i][0] + "= new Tree('" + data[i][1] + "','" + data[i][2] + "')");
				eval("var tree=node" + data[i][0]);
			}else {
			    <%if(strFlag.equals("checkBox")){%>
				eval("var node" + data[i][0] + "=new CheckBoxTreeItem('  " + data[i][1] + "','" + data[i][2] + "',null,false,node" + data[i][3] + ",'','','','" + data[i][5] + "','" + data[i][4] + "')");

			    <%}else{%>
				eval("var node" + data[i][0] + "=new TreeItem('" + data[i][1] + "','" + data[i][2] + "','" + data[i][4] + "',node" + data[i][3] + ",'" + data[i][5] + "','" + data[i][6] + "'," + data[i][7] + ",'" + data[i][8] + "','" + data[i][9] + "')");
			    <%}%>
			}
		}
	}

	tree.expandAll();
	
	
	document.write("<div id='treeContainer' oncontextmenu='showPopup();return false;'>" + tree + "</div>");
	
	}catch (e){}
</SCRIPT>

</TD>

</TR>
<tr>
<td id="t1">&nbsp;<td>
</tr>

</TABLE>
</form>
</body>

</html>

<SCRIPT LANGUAGE="JavaScript">
<!--

	function expand() {
		if (tree.getSelected() && !tree.getSelected().open && tree.getSelected().childNodes.length != 0) {			
			tree.getSelected().expand();
		}
	}
	function collapse() {
		if (tree.getSelected() && tree.getSelected().open && tree.getSelected().childNodes.length != 0) {
			tree.getSelected().collapse();
		}
	}
	function expandAll() {
		if (tree.getSelected() && tree.getSelected().childNodes.length != 0) {
			tree.getSelected().expandAll();
		}
	}
	function collapseAll() {
		if (tree.getSelected() && tree.getSelected().childNodes.length != 0) {
			tree.getSelected().collapseAll();
		}
	}
	
	
	function addNode() {
		var treeId=tree.getSelectedValue();
		var treeName=tree.getSelected().text;
		treeName=replaceAll0(escape(treeName),"%u","@");
		var title=tree.getSelected().title;
		if(title==null||title==''){
			title="1";
		}else{
			var layer=title.substring(0,title.indexOf(" "));
			title=new Number(layer)+1 ;
		}
		//alert(tree.getSelected().text);
		//alert(tree.getSelected().action);
		//alert(tree.getSelected().target);
		
		
		parent.main.location="accountAction.do?method=addInit&parentId="+treeId+"&treeName="+treeName+"&layer="+title;
		  
	}
	
	function deleteNode() {
		var layer=tree.getSelected().title;
		
		if(layer!=null&&layer!=''){
			if(confirm("��ͬʱɾ�������²��Ŀ���Ƿ�ȷ��ɾ����")){
				var treeId=tree.getSelectedValue();
				parent.main.location="accountAction.do?method=subDelete&id="+treeId;
			    
			}
		}else{
			alert("�ܿ�Ŀ����ɾ����");
		}
	}
	function circleTreeUp() {
		var treeId=tree.getSelectedValue();
		alert(treeId);
		parent.main.location="accountAction.do?method=circleTree&flag=up&id="+treeId;
	}
	function circleTreeDown() {
		var treeId=tree.getSelectedValue();
		parent.main.location="accountAction.do?method=circleTree&flag=down&id="+treeId;
	}

	function circleTree() {}
	
	var popupMenu = new Menu();
	//popupMenu.add(new MenuItem("չ���ڵ�", expand));
	//popupMenu.add(new MenuItem("�۵��ڵ�", collapse));
	popupMenu.add(new MenuItem("ȫ��չ��", expandAll));
	popupMenu.add(new MenuItem("ȫ���۵�", collapseAll));
	
	popupMenu.add(new MenuSeparator());
	popupMenu.add(new MenuItem("�����¼���Ŀ", addNode));
	popupMenu.add(new MenuItem("ɾ����Ŀ", deleteNode));
	
	
	popupMenu.add(new MenuSeparator());
	popupMenu.add(new MenuItem("�� ��",circleTreeUp));
	popupMenu.add(new MenuItem("�� ��",circleTreeDown));
	function showPopup() {
		popupMenu.show(window.event.screenX, window.event.screenY);		
	}
	
	
	 
//-->
</SCRIPT>