<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>

<html>
<head>
<title>toolsAdjustInDetail</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/css/autocomplete.css" /> 
<script type="text/javascript" src="<%= request.getContextPath()%>/js/prototype.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/js/autocomplete.js"></script>
<script language=javascript src="js/inputValidation.js"></script>

<%
		String stuffNo=(String)request.getAttribute("stuffNo");

%>
</head>
<body >
<html:form action="stockToolsAction.do?method=stockToolsList" method="post" >
<br>
<table width="99%" border="0" align="center" cellpadding="2" cellspacing="1" class="content12">
  <tr>
    <td>库存信息&gt;维修工具查询&gt;工具调入</td>
  </tr>
</table>
<br>
<table width="99%" border="0" align="center" cellpadding="2" cellspacing="1" class="content12">
  <tr> 
    <td height="2" colspan="6" bgcolor="#677789"></td>
  </tr>
  <tr> 
    <td height="6"  colspan="6" bgcolor="#CECECE"></td>
  </tr> 
   <tr class="tableback2">
    <td>工具料号：<font color="red">*</font></td>
    <td><html:text  styleClass="form" property="stuffNo"  maxlength="32"  size="24"  /></td>
    <td>工具名称：</td>
    <td><html:text  styleClass="form" property="skuCode"  maxlength="32"  size="44"  readonly="true"/> </td>
    <td>单位：</td>
    <td ><html:text  styleClass="form" property="skuUnit"  maxlength="4"  size="20" readonly="true" /></td>
  </tr>
  <tr class="tableback1"> 
    
  	<td>数量：<font color="red">*</font></td>
    <td ><html:text  styleClass="form" property="skuNum"  maxlength="4"  size="24"  onkeydown="javascript:f_onlynumber();" /></td>
    <td >规格：</td>
    <td ><html:text  styleClass="form" property="standard"  maxlength="32"  size="44" readonly="true" /></td>
    <td >所有者：</td>
    <td ><html:text  styleClass="form" property="owner"  maxlength="32"  size="20"   /></td>
  </tr>
  
  <tr class="tableback1"> 
    <td valign="top">备注：</td>
    <td colspan="5">
    	<html:textarea property="remark" style="width:90% " styleClass="form"></html:textarea> </td>
  </tr>
  <tr> 
    <td height="2" colspan="6" bgcolor="#ffffff"></td>
  </tr>
  <tr> 
    <td height="1" colspan="6" bgcolor="#677789"></td>
  </tr>
  <tr> 
    <td  colspan="6"><div align="right"> 
        <input type="button" name="save" value="调入"  onclick="f_save()">
      </div></td>
  </tr>
</table>
</body>
</html:form>
</html>

<SCRIPT >
new AutoTip.AutoComplete("stuffNo", function() {
	 return "partInfoAction.do?method=getToolInfo&inputValue=" + escape(this.text.value);
});

function f_save(){
	if(checkIsNull()){
		document.forms[0].action="stockToolsAction.do?method=toolsInOperate"; 
		document.forms[0].submit();
	}
}



function checkIsNull() {
	var stuffNo = document.all.stuffNo.value;
	var skuNum = document.all.skuNum.value;
	
	if (stuffNo==null||stuffNo.trim() == "") {
		alert("料号不能为空!");
		return false;
	}
	

	if (skuNum==null||skuNum.trim() == "") {
		alert("数量不能为空!");
		return false;
	}
	
	document.all.stuffNo.value=stuffNo.trim();
	
	return true;
}

String.prototype.trim = function()
{
	return this.replace( /(^\s*)|(\s*$)/g, '' ) ;
}



</SCRIPT>