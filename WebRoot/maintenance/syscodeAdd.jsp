<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>

<html>
<head>
<title>newsis</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/styles.css" rel="stylesheet" type="text/css" />
<script language=javascript src="js/inputValidation.js"></script>


</head>
<body>
<html:form action="syscodeAction.do?method=addSyscode" method="post" >
<br>
<table width="90%" border="0" align="center" cellpadding="2" cellspacing="1" class="content12">
  <tr>
    <td>���&gt;�ֵ��ά��&gt;�ֵ������</td>
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
   <tr class="tableback2">
    
    <td>������<font color="red">*</font></td>
    <td><html:text  styleClass="form" property="systemDesc"  maxlength="32"  size="20"  /> </td>
    <td>�ֶ�����<font color="red">*</font></td>
    <td ><html:text  styleClass="form" property="systemType"  maxlength="20"  size="16"  /> </td>
  </tr>
  <tr class="tableback1"> 
    <td>״̬����<font color="red">*</font></td>
    <td><html:text  styleClass="form" property="systemName"  maxlength="50"  size="20"  /></td>
  	<td>״ֵ̬��<font color="red">*</font></td>
    <td ><html:text  styleClass="form" property="systemCode"  maxlength="9"  size="16"  /></td>
  </tr>
   
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
		document.forms[0].action="syscodeAction.do?method=insertSyscode"; 
		document.forms[0].submit();
	}
}


function checkIsNull() {
	var systemDesc = document.all.systemDesc.value;
	var systemType = document.all.systemType.value;
	var systemName = document.all.systemName.value;
	var systemCode = document.all.systemCode.value;
	
	if (systemDesc==null||systemDesc.trim() == "") {
		alert("��������Ϊ��!");
		return false;
	}
	if (systemType==null||systemType.trim() == "") {
		alert("�ֶ�������Ϊ��!");
		return false;
	}
	if (systemName==null||systemName.trim() == "") {
		alert("״̬������Ϊ��!");
		return false;
	}
	if (systemCode==null||systemCode.trim() == "") {
		alert("״ֵ̬����Ϊ��!");
		return false;
	}
	
	document.all.systemDesc.value=systemDesc.trim();
	document.all.systemType.value=systemType.trim();
	document.all.systemName.value=systemName.trim();
	document.all.systemCode.value=systemCode.trim();
	
	return true;
}

String.prototype.trim = function()
{
	return this.replace( /(^\s*)|(\s*$)/g, '' ) ;
}

</SCRIPT>