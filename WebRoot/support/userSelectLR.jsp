<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri=  "/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ page import="java.util.ArrayList"%> 

<html>
<head>
<title>user_choose</title>
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/styles2.css">
<SCRIPT language="javascript" src="js/commonSelect.js"></SCRIPT>
</head>
<%
try{
	
	ArrayList[] userList=(ArrayList[])request.getAttribute("userList");
	ArrayList reptUserList=new ArrayList();
	ArrayList deptUserList=new ArrayList();
	
	if(userList!=null){ 
		reptUserList=userList[0];
		deptUserList=userList[1];
	}
	
	String orgType="";
%>
 
<body bgcolor="#ffffff" leftmargin="0" topmargin="0" rightmargin="0" bottommargin="0" marginwidth="0" marginheight="0" >

<html:form method="post" action="userAction.do?method=lrUser">


<br>
<table  width="540" height="360" border="0" align="center" cellpadding="2" cellspacing="1" class="content12">
  <tr> 
    <td height="2" colspan="4" bgcolor="#677789"></td>
  </tr>
  <tr> 
    <td height="6" align="center" bgcolor="#CECECE"><font color="#FFFFFF">ѡ���б�</font></td>
    <td height="6" bgcolor="#CECECE"></td>
    <td height="6" align="center" bgcolor="#CECECE"><font color="#FFFFFF">ѡ��</font></td>
    <td height="6" bgcolor="#CECECE"><font color="#FFFFFF">����</font></td>
  </tr>
  
  <!--
  <tr class="tableback2"> 
    <td colspan="4"><font color="#FF6600"> 
      <select name="select16" class="form" style="width:100">
        <option value="��ɫ">��ɫ</option>
      </select>
      </font> </td>
  </tr>
  -->
  <tr>
		<td width="40%"  rowspan="6" valign="top">
			<div id="select1" class="selectDiv" style="width:220;height:300">
				<%for(int i=0;i<deptUserList.size();i++){
					String temp[]=(String[])deptUserList.get(i);
					if(i>0&&temp[3]!=null&&!orgType.equals(temp[3])){
				%>
					<span> &nbsp;===========<br></span>
				<%}%>
					<span onmouseover="this.className='item-selected';" onmouseout="this.className='';" >
					<input name="chk" type=checkbox id="<%=temp[1]%>" value='<%=temp[0]%>'>
					<%if(temp[2]!=null&&temp[2].equals("A")){%> <font color="blue"><%=temp[1]%> </font>
					<%}else if(temp[2]!=null&&temp[2].equals("B")){%> <font color="red"><%=temp[1]%> </font>
					<%}else if(temp[2]!=null&&temp[2].equals("C")){%> <%=temp[1]%> 
					<%}else if(temp[2]!=null&&!temp[2].equals("")){%> <%=temp[1]%> - <%=temp[2]%> 
					<%}else{%> <%=temp[1]%> <%}%><br></span>
				<%
					orgType=temp[3];
				}%>
				
			</div>
		</td>
		<td width="15%">&nbsp;</td>
		<td width="40%"  rowspan="6" valign="top">
		
			<div id="select2"  class="selectDiv" style="width:200;height:300">
				<%for(int i=0;i<reptUserList.size();i++){
					String temp[]=(String[])reptUserList.get(i);
				%>
					<span onmouseover="this.className='item-selected';" onmouseout="this.className='';">
					<input name="chk2" type=checkbox id="selectVal" value="<%=temp[0]%>_<%=temp[1]%>">
					<%if(temp[2]!=null&&temp[2].equals("A")){%> <font color="blue"><%=temp[1]%> </font>
					<%}else if(temp[2]!=null&&temp[2].equals("B")){%> <font color="red"><%=temp[1]%> </font>
					<%}else if(temp[2]!=null&&temp[2].equals("C")){%> <%=temp[1]%> 
					<%}else if(temp[2]!=null&&!temp[2].equals("")){%> <%=temp[1]%> - <%=temp[2]%> 
					<%}else{%> <%=temp[1]%> <%}%><br></span>
					
				<%}%>
			</div>
		</td>
	</tr>
  	<tr>
		<td width="10%" align="center"><input class="button2" type=button   value="4"  style="font-family:Marlett;" onClick="moveToRight();"></td>
		<td width="10%"><input class="button2" style="width:20px"  type=button  value="7" style="font-family:Marlett;filter:Flipv;"  onClick="moveToPrevious('first');"></td>
	</tr>
	<tr>
		<td width="10%" align="center"><input class="button2" type=button  value="44"  style="font-family:Marlett;"   onClick="moveAllToRight();"></td>
		<td width="10%"><input class="button2" style="width:20px" type=button  value="5" style="font-family:Marlett;" onClick="moveToPrevious();"></td>
	</tr>
	<tr>
		<td width="10%" align="center"><input class="button2"   type=button value="3"  style="font-family:Marlett;" onClick="moveToLeft();"></td>
		<td width="10%"><input class="button2" style="width:20px" type=button  value="6" style="font-family:Marlett;" onClick="moveToNext();"></td>
	</tr>
	<tr>
		<td width="10%" align="center"><input class="button2"   type=button value="33"  style="font-family:Marlett;" onClick="moveAllToLeft();"></td>
		<td width="10%"><input class="button2" onClick="moveToNext('last');" style="width:20px" type=button value="7" style="font-family:Marlett;"></td>
	</tr>
	<tr>
		<td width="10%">��</td>
	</tr>
	
	
  
  <tr> 
    <td height="1" colspan="4" bgcolor="#ffffff"></td>
  </tr>
  <tr> 
    <td height="1" colspan="4" bgcolor="#677789"></td>
  </tr>
</table>
<br>
<table width="480" border="0" align="center" cellpadding="0" cellspacing="0" class="content12">
  <tr> 
    <td align="center"><font color="#FF6600"> 
      <input  type="button" class="button2" value="ȷ��" onclick="f_submit()">
      <input  type="button" class="button2" value="ȡ��" onclick="self.close()">
      </font></td>
  </tr>
</table>

</html:form>
</body>
</html>
<%}catch(Exception e){
  e.printStackTrace();
}%>
<SCRIPT language=JAVASCRIPT1.2>

<!--

function moveToRight() {	
	var nodesIndexToBeMoved = new Array();
	var arrayIndex = 0;
	//��˳�����ӣ�ͬʱ��¼����Ҫɾ���Ľڵ㣬������ɾ���������document.getElementById("select1").children.length���ϱ�С��Ӱ��
	for (var i = 0; i <= document.getElementById("select1").children.length - 1; i++) {
		if (document.getElementById("select1").children[i].tagName == "SPAN") {
			// ����һ��ѭ������Ϊ����ÿ��span�е�Ԫ�ظ�����������Ҫ׼ȷ��λ��checkbox
			for (var j = 0; j <= document.getElementById("select1").children[i].children.length - 1; j++) {
				if (document.getElementById("select1").children[i].children[j].tagName == "INPUT" && document.getElementById("select1").children[i].children[j].checked) {			
					var span = document.createElement("span");
					//alert(document.getElementById("select1").children[i].attributes.getNamedItem("onmouseover"));
					span.innerHTML = document.getElementById("select1").children[i].innerHTML;
					//alert(document.getElementById("select1").children[i].getAttribute("onmouseover"));
					span.setAttribute("onmouseover", document.getElementById("select1").children[i].getAttribute("onmouseover"));
					span.setAttribute("onmouseout", document.getElementById("select1").children[i].getAttribute("onmouseout"));
					//var checkBox = document.createElement(document.getElementById("select1").children[i].innerHTML);
					//span.appendChild(checkBox);
					document.getElementById("select2").appendChild(span);
					nodesIndexToBeMoved[arrayIndex] = i;
					arrayIndex++;
					///alert(document.getElementById("select2").children[i].innerHTML);
					//document.getElementById("select1").removeChild(document.getElementById("select1").children[i]);
				}
			}
		}
	}

	// now, moved together	
	for (var i = nodesIndexToBeMoved.length - 1; i >=0 ; i--) {
		document.getElementById("select1").removeChild(document.getElementById("select1").children[nodesIndexToBeMoved[i]]);
	}
	
	for (var i = 0; i <= document.getElementById("select2").children.length - 1; i++) {
	    for (var j = 0; j <= document.getElementById("select2").children[i].children.length - 1; j++) {
		document.getElementById("select2").children[i].children[j].checked=false;
	    }
	}
}

function moveToLeft() {
	var nodesIndexToBeMoved = new Array();
	var arrayIndex = 0;

	for (var i = 0; i <= document.getElementById("select2").children.length - 1; i++) {
		if (document.getElementById("select2").children[i].tagName == "SPAN") {

			for (var j = 0; j <= document.getElementById("select2").children[i].children.length - 1; j++) {
				if (document.getElementById("select2").children[i].children[j].tagName == "INPUT" && document.getElementById("select2").children[i].children[j].checked) {			
					var span = document.createElement("span");
					span.innerHTML = document.getElementById("select2").children[i].innerHTML;					
					span.setAttribute("onmouseover", document.getElementById("select2").children[i].getAttribute("onmouseover"));
					span.setAttribute("onmouseout", document.getElementById("select2").children[i].getAttribute("onmouseout"));

					document.getElementById("select1").appendChild(span);
					nodesIndexToBeMoved[arrayIndex] = i;
					arrayIndex++;
					//document.getElementById("select1").removeChild(document.getElementById("select1").children[i]);
				}
			}
		}
	}

	// now, moved together	
	for (var i = nodesIndexToBeMoved.length - 1; i >=0 ; i--) {
		document.getElementById("select2").removeChild(document.getElementById("select2").children[nodesIndexToBeMoved[i]]);
	}
	
	for (var i = 0; i <= document.getElementById("select1").children.length - 1; i++) {
	    for (var j = 0; j <= document.getElementById("select1").children[i].children.length - 1; j++) {
		document.getElementById("select1").children[i].children[j].checked=false;
	    }
	}
}

function moveAllToRight() {
	var childrenCount = document.getElementById("select2").children.length;
	for (var i = 0; i <= document.getElementById("select1").children.length - 1; i++) {
		if (document.getElementById("select1").children[i].tagName == "SPAN") {			
			var span = document.createElement("span");
			span.innerHTML = document.getElementById("select1").children[i].innerHTML;

			span.setAttribute("onmouseover", document.getElementById("select1").children[i].getAttribute("onmouseover"));
			span.setAttribute("onmouseout", document.getElementById("select1").children[i].getAttribute("onmouseout"));

			document.getElementById("select2").appendChild(span);
			alert(document.getElementById("select2").children);
			//document.getElementById("select1").removeChild(document.getElementById("select1").children[i]);
		}
	}
	// remove together
	for (var i = document.getElementById("select1").children.length - 1; i >= 0; i--) {
		if (document.getElementById("select1").children[i].tagName == "SPAN") {			
			document.getElementById("select1").removeChild(document.getElementById("select1").children[i]);
		}
	}
}

function moveAllToLeft() {
	for (var i = 0; i <= document.getElementById("select2").children.length - 1; i++) {
		if (document.getElementById("select2").children[i].tagName == "SPAN") {
			var span = document.createElement("span");
			span.innerHTML = document.getElementById("select2").children[i].innerHTML ;

			span.setAttribute("onmouseover", document.getElementById("select2").children[i].getAttribute("onmouseover"));
			span.setAttribute("onmouseout", document.getElementById("select2").children[i].getAttribute("onmouseout"));

			document.getElementById("select1").appendChild(span);
		}
	}	
	// remove together
	for (var i = document.getElementById("select2").children.length - 1; i >= 0 ; i--) {
		if (document.getElementById("select2").children[i].tagName == "SPAN") {
			document.getElementById("select2").removeChild(document.getElementById("select2").children[i]);
		}
	}
}

function moveToNext(last) {
	var nodesIndexToBeMoved = new Array();
	var arrayIndex = 0;
	var lastSelected = -1;

	for (var i = 0; i <= document.getElementById("select2").children.length - 1; i++) {
		if (document.getElementById("select2").children[i].tagName == "SPAN") {

			for (var j = 0; j <= document.getElementById("select2").children[i].children.length - 1; j++) {
				if (document.getElementById("select2").children[i].children[j].tagName == "INPUT" && document.getElementById("select2").children[i].children[j].checked) {			
					//var span = document.createElement("span");
					//span.innerHTML = document.getElementById("select2").children[i].innerHTML ;
					//var checkBox = document.createElement(document.getElementById("select1").children[i].innerHTML);
					//span.appendChild(checkBox);
					// document.getElementById("select1").appendChild(span);
					nodesIndexToBeMoved[arrayIndex] = i;
					lastSelected = i + 1;
					arrayIndex++;
					//document.getElementById("select1").removeChild(document.getElementById("select1").children[i]);
				}
			}
		}
	}
	if (last == "last") {
		lastSelected = document.getElementById("select2").children.length - 1;
	}
	var inserted = false;
	// Ϊ�˱���ԭ����˳��
	for (var i = nodesIndexToBeMoved.length -1; i >= 0; i--) {		
		var span = document.createElement("span");
		span.innerHTML = document.getElementById("select2").children[nodesIndexToBeMoved[i]].innerHTML ;
		span.setAttribute("onmouseover", document.getElementById("select2").children[i].getAttribute("onmouseover"));
		span.setAttribute("onmouseout", document.getElementById("select2").children[i].getAttribute("onmouseout"));
		if (lastSelected != -1 && lastSelected <= document.getElementById("select2").children.length - 1) {
			inserted = true;
			document.getElementById("select2").children[lastSelected].insertAdjacentElement("AfterEnd",span);
		}
	}
	
	// now, if inserted, removed 
	if (inserted == true) { 
		for (var i = nodesIndexToBeMoved.length - 1; i >=0 ; i--) {
			document.getElementById("select2").removeChild(document.getElementById("select2").children[nodesIndexToBeMoved[i]]);
		}	
	}
}

function moveToPrevious(first) {
	var nodesIndexToBeMoved = new Array();
	var arrayIndex = 0;
	var firstSelected = -1;
	// ��¼����Ҫɾ���Ľڵ㣬�����������ɾ��
	for (var i = document.getElementById("select2").children.length - 1; i >= 0; i--) {
		if (document.getElementById("select2").children[i].tagName == "SPAN") {

			for (var j = 0; j <= document.getElementById("select2").children[i].children.length - 1; j++) {
				if (document.getElementById("select2").children[i].children[j].tagName == "INPUT" && document.getElementById("select2").children[i].children[j].checked) {
					nodesIndexToBeMoved[arrayIndex] = i;
					firstSelected = i - 1;
					arrayIndex++;
					//document.getElementById("select1").removeChild(document.getElementById("select1").children[i]);
				}
			}
		}
	}
	if (first == "first") {
		firstSelected = 0;
	}
	var inserted = false;
	// insert
	for (var i = nodesIndexToBeMoved.length -1; i >= 0; i--) {		
		var span = document.createElement("span");
		span.innerHTML = document.getElementById("select2").children[nodesIndexToBeMoved[i]].innerHTML;
		span.setAttribute("onmouseover", document.getElementById("select2").children[i].getAttribute("onmouseover"));
		span.setAttribute("onmouseout", document.getElementById("select2").children[i].getAttribute("onmouseout"));
		if (firstSelected >= 0) {
			inserted = true;
			document.getElementById("select2").children[firstSelected].insertAdjacentElement("BeforeBegin",span);
			// 
			firstSelected++;
			for (var j = nodesIndexToBeMoved.length -1; j >= 0; j--) {
				nodesIndexToBeMoved[j]++;
			}
		}
	}
	
	// now, if inserted, removed 
	if (inserted == true) { 
		for (var i = 0; i <= nodesIndexToBeMoved.length - 1 ; i++) {
			document.getElementById("select2").removeChild(document.getElementById("select2").children[nodesIndexToBeMoved[i]]);
		}	
	}
}

//-->

</SCRIPT>


<SCRIPT language=JAVASCRIPT1.2>

function f_submit(){
    var childrens=document.getElementById("select2").children;
    
    var returnId=new Array(2);
    var reptId="",reptName="";
    if(childrens.length>0){
	for (var i = 0; i <= childrens.length - 1; i++) {
		
		if(childrens[i].tagName=='INPUT'){
			//alert("aaa="+childrens[i].value);
			reptId+=","+childrens[i].value;
			reptName+=","+childrens[i].id;
		}else{
			var temp2 = childrens[i].innerHTML;
			var tempId = temp2.substring(temp2.indexOf('id="')+4);
			tempId = tempId.substring(0,tempId.indexOf('"'));
			
			var tempValue = temp2.substring(temp2.indexOf('value="')+7);
			tempValue = tempValue.substring(0,tempValue.indexOf('"'));
			reptId+=","+tempValue;
			reptName+=","+tempId;
		}
	}
	if(reptId.indexOf(',')!=-1) reptId=reptId.substring(1);
	if(reptName.indexOf(',')!=-1) reptName=reptName.substring(1);
	returnId[0]=reptId;
	returnId[1]=reptName;
	
	returnValue=returnId;
    }else{
    	returnId[0]="";
	returnId[1]="";
    	returnValue=returnId;
    }
    self.close();
}


</SCRIPT>