<%@ page contentType="text/html;charset=GBK"%>
<html>
<head>
<title>IRIS</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link href="<%= request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css">
<%
	String content = request.getAttribute("irisContent")==null?"": (String)request.getAttribute("irisContent");
	String desc = (String)request.getAttribute("desc");
	String name = (String)request.getAttribute("name");
	String flag = (String)request.getAttribute("flag");
	String id = (String)request.getAttribute("id");
 %>
 
<SCRIPT LANGUAGE="JavaScript">
<!--
function returnIrisValue(){
	var irisInfo = new Array();
	irisInfo[0]=document.forms[0].irisContent.value;
	irisInfo[1]="<%=id%>";
	returnValue=irisInfo;
	
	window.close();
}

//-->
</SCRIPT>
</head>
<body>

<form name="irisMapForm">
<table width="90%" border="0" align="center" cellpadding="2" cellspacing="1" class="content12">
  <tr> 
    <td height="2" colspan="6" bgcolor="#677789"></td>
  </tr>
  <tr> 
    <td height="6"  colspan="6" bgcolor="#CECECE"></td>
  </tr> 
   
  <tr class="tableback1"> 
    <td valign="top"  width="15%"><%=name %>：</td>
    <td colspan="5">
    	<textarea name="irisContent"  cols="8" rows="6" style="width:90% " class="form"><%=content %></textarea></td>
  </tr>
  <tr> 
    <td height="2" colspan="6" bgcolor="#ffffff"></td>
  </tr>
  <tr> 
    <td height="1" colspan="6" bgcolor="#677789"></td>
  </tr>
  <tr> 
    <td  colspan="6"><div align="right"> 
    	<%if(!"view".equals(flag)){ %>
        <input type="button" name="save" value="确定"  onclick="returnIrisValue()">
        <%} %>
        <input type="button"  value="关闭"  onclick="self.close();">
      </div></td>
  </tr>
</table>


</form>
</body>
</html>