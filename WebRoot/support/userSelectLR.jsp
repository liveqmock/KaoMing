<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri=  "/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ page import="java.util.ArrayList"%> 

<html>
<head>
<title>user_choose</title><SCRIPT language="javascript" src="js/screen.js"></SCRIPT>
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
    <td height="6" align="center" bgcolor="#CECECE"><font color="#FFFFFF">选择列表</font></td>
    <td height="6" bgcolor="#CECECE"></td>
    <td height="6" align="center" bgcolor="#CECECE"><font color="#FFFFFF">选中</font></td>
    <td height="6" bgcolor="#CECECE"><font color="#FFFFFF">排序</font></td>
  </tr>
  
  <!--
  <tr class="tableback2"> 
    <td colspan="4"><font color="#FF6600"> 
      <select name="select16" class="form" style="width:100">
        <option value="角色">角色</option>
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
					<span onmouseover="this.className='item-selected';" onmouseout="this.className='';"><input name="chk" type=checkbox id="<%=temp[1]%>" value='<%=temp[0]%>'>
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
					<span onmouseover="this.className='item-selected';" onmouseout="this.className='';"><input name="chk2" type=checkbox id="<%=temp[1]%>" value='<%=temp[0]%>'>
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
		<td width="10%">　</td>
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
      <input  type="button" class="button2" value="确定" onclick="f_submit()">
      <input  type="button" class="button2" value="取消" onclick="self.close()">
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
	//先顺序增加，同时记录下需要删除的节点，再批量删除，免除了select1.children.length不断变小的影响
	for (var i = 0; i <= select1.children.length - 1; i++) {
		if (select1.children[i].tagName == "SPAN") {
			// 加入一个循环，因为由于每个span中的元素个数不定，需要准确定位到checkbox
			for (var j = 0; j <= select1.children[i].children.length - 1; j++) {
				if (select1.children[i].children[j].tagName == "INPUT" && select1.children[i].children[j].checked) {			
					var span = document.createElement("span");
					//alert(select1.children[i].attributes.getNamedItem("onmouseover"));
					span.innerHTML = select1.children[i].innerHTML;
					//alert(select1.children[i].getAttribute("onmouseover"));
					span.setAttribute("onmouseover", select1.children[i].getAttribute("onmouseover"));
					span.setAttribute("onmouseout", select1.children[i].getAttribute("onmouseout"));
					//var checkBox = document.createElement(select1.children[i].innerHTML);
					//span.appendChild(checkBox);
					select2.appendChild(span);
					nodesIndexToBeMoved[arrayIndex] = i;
					arrayIndex++;
					
					//select1.removeChild(select1.children[i]);
				}
			}
		}
	}

	// now, moved together	
	for (var i = nodesIndexToBeMoved.length - 1; i >=0 ; i--) {
		select1.removeChild(select1.children[nodesIndexToBeMoved[i]]);
	}
	
	for (var i = 0; i <= select2.children.length - 1; i++) {
	    for (var j = 0; j <= select2.children[i].children.length - 1; j++) {
		select2.children[i].children[j].checked=false;
	    }
	}
}

function moveToLeft() {
	var nodesIndexToBeMoved = new Array();
	var arrayIndex = 0;

	for (var i = 0; i <= select2.children.length - 1; i++) {
		if (select2.children[i].tagName == "SPAN") {

			for (var j = 0; j <= select2.children[i].children.length - 1; j++) {
				if (select2.children[i].children[j].tagName == "INPUT" && select2.children[i].children[j].checked) {			
					var span = document.createElement("span");
					span.innerHTML = select2.children[i].innerHTML;					
					span.setAttribute("onmouseover", select2.children[i].getAttribute("onmouseover"));
					span.setAttribute("onmouseout", select2.children[i].getAttribute("onmouseout"));

					select1.appendChild(span);
					nodesIndexToBeMoved[arrayIndex] = i;
					arrayIndex++;
					//select1.removeChild(select1.children[i]);
				}
			}
		}
	}

	// now, moved together	
	for (var i = nodesIndexToBeMoved.length - 1; i >=0 ; i--) {
		select2.removeChild(select2.children[nodesIndexToBeMoved[i]]);
	}
	
	for (var i = 0; i <= select1.children.length - 1; i++) {
	    for (var j = 0; j <= select1.children[i].children.length - 1; j++) {
		select1.children[i].children[j].checked=false;
	    }
	}
}

function moveAllToRight() {
	var childrenCount = select2.children.length;
	for (var i = 0; i <= select1.children.length - 1; i++) {
		if (select1.children[i].tagName == "SPAN") {			
			var span = document.createElement("span");
			span.innerHTML = select1.children[i].innerHTML;

			span.setAttribute("onmouseover", select1.children[i].getAttribute("onmouseover"));
			span.setAttribute("onmouseout", select1.children[i].getAttribute("onmouseout"));

			select2.appendChild(span);
			//select1.removeChild(select1.children[i]);
		}
	}
	// remove together
	for (var i = select1.children.length - 1; i >= 0; i--) {
		if (select1.children[i].tagName == "SPAN") {			
			select1.removeChild(select1.children[i]);
		}
	}
}

function moveAllToLeft() {
	for (var i = 0; i <= select2.children.length - 1; i++) {
		if (select2.children[i].tagName == "SPAN") {
			var span = document.createElement("span");
			span.innerHTML = select2.children[i].innerHTML ;

			span.setAttribute("onmouseover", select2.children[i].getAttribute("onmouseover"));
			span.setAttribute("onmouseout", select2.children[i].getAttribute("onmouseout"));

			select1.appendChild(span);
		}
	}	
	// remove together
	for (var i = select2.children.length - 1; i >= 0 ; i--) {
		if (select2.children[i].tagName == "SPAN") {
			select2.removeChild(select2.children[i]);
		}
	}
}

function moveToNext(last) {
	var nodesIndexToBeMoved = new Array();
	var arrayIndex = 0;
	var lastSelected = -1;

	for (var i = 0; i <= select2.children.length - 1; i++) {
		if (select2.children[i].tagName == "SPAN") {

			for (var j = 0; j <= select2.children[i].children.length - 1; j++) {
				if (select2.children[i].children[j].tagName == "INPUT" && select2.children[i].children[j].checked) {			
					//var span = document.createElement("span");
					//span.innerHTML = select2.children[i].innerHTML ;
					//var checkBox = document.createElement(select1.children[i].innerHTML);
					//span.appendChild(checkBox);
					// select1.appendChild(span);
					nodesIndexToBeMoved[arrayIndex] = i;
					lastSelected = i + 1;
					arrayIndex++;
					//select1.removeChild(select1.children[i]);
				}
			}
		}
	}
	if (last == "last") {
		lastSelected = select2.children.length - 1;
	}
	var inserted = false;
	// 为了保持原来的顺序
	for (var i = nodesIndexToBeMoved.length -1; i >= 0; i--) {		
		var span = document.createElement("span");
		span.innerHTML = select2.children[nodesIndexToBeMoved[i]].innerHTML ;
		span.setAttribute("onmouseover", select2.children[i].getAttribute("onmouseover"));
		span.setAttribute("onmouseout", select2.children[i].getAttribute("onmouseout"));
		if (lastSelected != -1 && lastSelected <= select2.children.length - 1) {
			inserted = true;
			select2.children[lastSelected].insertAdjacentElement("AfterEnd",span);
		}
	}
	
	// now, if inserted, removed 
	if (inserted == true) { 
		for (var i = nodesIndexToBeMoved.length - 1; i >=0 ; i--) {
			select2.removeChild(select2.children[nodesIndexToBeMoved[i]]);
		}	
	}
}

function moveToPrevious(first) {
	var nodesIndexToBeMoved = new Array();
	var arrayIndex = 0;
	var firstSelected = -1;
	// 记录下需要删除的节点，再批量插入和删除
	for (var i = select2.children.length - 1; i >= 0; i--) {
		if (select2.children[i].tagName == "SPAN") {

			for (var j = 0; j <= select2.children[i].children.length - 1; j++) {
				if (select2.children[i].children[j].tagName == "INPUT" && select2.children[i].children[j].checked) {
					nodesIndexToBeMoved[arrayIndex] = i;
					firstSelected = i - 1;
					arrayIndex++;
					//select1.removeChild(select1.children[i]);
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
		span.innerHTML = select2.children[nodesIndexToBeMoved[i]].innerHTML;
		span.setAttribute("onmouseover", select2.children[i].getAttribute("onmouseover"));
		span.setAttribute("onmouseout", select2.children[i].getAttribute("onmouseout"));
		if (firstSelected >= 0) {
			inserted = true;
			select2.children[firstSelected].insertAdjacentElement("BeforeBegin",span);
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
			select2.removeChild(select2.children[nodesIndexToBeMoved[i]]);
		}	
	}
}

//-->

</SCRIPT>


<SCRIPT language=JAVASCRIPT1.2>

function f_submit(){
    var childrens=select2.all;
    var returnId=new Array(2);
    var reptId="",reptName="";
    if(childrens.length>0){
	for (var i = 0; i <= childrens.length - 1; i++) {
		if(childrens[i].tagName=='INPUT'){
			reptId+=","+childrens[i].value;
			reptName+=","+childrens[i].id;
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