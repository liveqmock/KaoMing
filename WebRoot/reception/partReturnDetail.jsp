<%@ page contentType="text/html;charset=GBK"%>
<html>
<head>
<title>partReturn</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link href="<%= request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css">
<script language=javascript src="js/inputValidation.js"></script>
 <%
	String stuffNo = (String)request.getAttribute("stuffNo");
%>
<SCRIPT LANGUAGE="JavaScript">
<!--
function returnIrisValue(){
	returnValue=document.forms[0].returnNum.value;;
	
	window.close();
}

//-->
</SCRIPT>
</head>
<body>

<form name="partReturnForm">
<table width="90%" border="0" align="center" cellpadding="2" cellspacing="1" class="content12">
  <tr> 
    <td height="2" colspan="6" bgcolor="#677789"></td>
  </tr>
  <tr> 
    <td height="6"  colspan="6" bgcolor="#CECECE"></td>
  </tr> 
   
  <tr class="tableback1"> 
    <td >退回零件: <%=stuffNo %> ,请输入退回数量:</td>
  </tr>
    <tr class="tableback2"> 
    <td >
    	<input name="returnNum" type="text" class="form" size="10" maxlength="4" onkeydown="javascript:f_onlynumber();" >
    </td>
  </tr>
  <tr> 
    <td height="2" colspan="6" bgcolor="#ffffff"></td>
  </tr>
  <tr> 
    <td height="1" colspan="6" bgcolor="#677789"></td>
  </tr>
  <tr> 
    <td  colspan="6"><div align="right">
        <input type="button" name="save" value="确定"  onclick="returnIrisValue()">
        <input type="button"  value="关闭"  onclick="self.close();">
      </div></td>
  </tr>
</table>


</form>
</body>
</html>