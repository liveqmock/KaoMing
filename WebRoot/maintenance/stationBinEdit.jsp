<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ page import="java.util.ArrayList"%> 
<%@ page import="com.dne.sie.common.tools.DicInit"%>

<html>
<head>
<title>stationBin</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/styles.css" rel="stylesheet" type="text/css" />
<script language=javascript src="js/inputValidation.js"></script>

<%
	String action = (String)request.getAttribute("action");
 %>
</head>
<body>
<html:form action="stationBinAction.do?method=addPartInfo" method="post" >
<br>
<table width="90%" border="0" align="center" cellpadding="2" cellspacing="1" class="content12">
  <tr>
    <td>���&gt;�ֿ�ά��&gt;��λ����</td>
  </tr>
</table>
<br>
<table width="90%" border="0" align="center" cellpadding="2" cellspacing="1" class="content12">
  <tr> 
    <td height="2" colspan="6" bgcolor="#677789"></td>
  </tr>
  <tr> 
    <td height="6"  colspan="6" bgcolor="#CECECE"></td>
  </tr> 
  
 <tr class="tableback1">
    <td >��λ��</td>
    <td ><html:text  styleClass="form" property="binCode"  maxlength="32"  size="40"  /></td>
  	
  <tr> 
    <td height="2" colspan="6" bgcolor="#ffffff"></td>
  </tr>
  <tr> 
    <td height="1" colspan="6" bgcolor="#677789"></td>
  </tr>
  <tr> 
    <td  colspan="6"><div align="right"> 
        <input type="button" name="save" value="����"  onclick="f_save()">
        <input type="button"  value="�ر�"  onclick="history.back();">
      </div></td>
  </tr>
</table>
</body>
</html:form>
</html>

<SCRIPT >
function f_save(){
	if(checkIsNull()){
		document.forms[0].action="stationBinAction.do?method=<%=action%>";
		document.forms[0].submit();
	}
}


function checkIsNull() {
	var binCode = document.all.binCode.value;

	if (binCode==null||binCode.trim() == "") {
		alert("��λ����Ϊ��!");
		return false;
	}
	
	
	document.all.binCode.value=binCode.trim();

	return true;
}

String.prototype.trim = function()
{
	return this.replace( /(^\s*)|(\s*$)/g, '' ) ;
}


</SCRIPT>