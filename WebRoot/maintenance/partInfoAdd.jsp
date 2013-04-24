<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ page import="java.util.ArrayList"%> 
<%@ page import="com.dne.sie.common.tools.DicInit"%>

<html>
<head>
<title>newsis</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/styles.css" rel="stylesheet" type="text/css" />
<script language=javascript src="js/inputValidation.js"></script>

<%
	ArrayList partTypeList=(ArrayList) DicInit.SYS_CODE_MAP.get("PART_TYPE");
 %>
</head>
<body>
<html:form action="partInfoAction.do?method=addPartInfo" method="post" >
<br>
<table width="90%" border="0" align="center" cellpadding="2" cellspacing="1" class="content12">
  <tr>
    <td>库存&gt;零件维护&gt;零件新增</td>
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
    
    <td>零件名称：<font color="red">*</font></td>
    <td><html:text  styleClass="form" property="skuCode"  maxlength="32"  size="20"  /> </td>
    <td >简称：<font color="red">*</font></td>
    <td ><html:text  styleClass="form" property="shortCode"  maxlength="16"  size="16"  /> </td>
  </tr>
  <tr class="tableback1"> 
    <td>料号：<font color="red">*</font></td>
    <td><html:text  styleClass="form" property="stuffNo"  maxlength="32"  size="20"  /></td>
  	<td>单位：<font color="red">*</font></td>
    <td ><html:text  styleClass="form" property="skuUnit"  maxlength="4"  size="16"  /></td>
  </tr>
   <tr class="tableback2">
    <td>进价：</td>
    <td><html:text  styleClass="form" property="buyCost"  maxlength="8"  size="20"  onkeydown="f_onlymoney()"/></td>
    <td>报价：</td>
    <td><html:text  styleClass="form" property="saleCost"  maxlength="8"  size="16"  onkeydown="f_onlymoney()"/></td>
  </tr>
 <tr class="tableback1">
    <td >规格：</td>
    <td ><html:text  styleClass="form" property="standard"  maxlength="32"  size="40"  /></td>
  	<td >类型：</td>
    <td ><html:select property="partType" styleClass="form">
	<%
			for(int i=0;partTypeList!=null&&i<partTypeList.size();i++){
		String[] temp=(String[])partTypeList.get(i);
	%>
	<html:option value="<%=temp[0]%>"><%=temp[1]%></html:option>
	<%}%>
	</html:select></td>
  </tr>
  <tr class="tableback1"> 
    <td valign="top">备注：</td>
    <td colspan="5">
    <textarea name="remark" style="width:90% " class="form"></textarea> </td>
  </tr>
  <tr> 
    <td height="2" colspan="6" bgcolor="#ffffff"></td>
  </tr>
  <tr> 
    <td height="1" colspan="6" bgcolor="#677789"></td>
  </tr>
  <tr> 
    <td  colspan="6"><div align="right"> 
        <input type="button" name="save" value="保存"  onclick="f_save()">
        <input type="button"  value="关闭"  onclick="history.back();">
      </div></td>
  </tr>
</table>
</body>
</html:form>
</html>

<SCRIPT >
function f_save(){
	if(checkIsNull()){
		document.forms[0].action="partInfoAction.do?method=insertPartInfo"; 
		document.forms[0].submit();
	}
}


function checkIsNull() {
	var stuffNo = document.all.stuffNo.value;
	var skuCode = document.all.skuCode.value;
	var shortCode = document.all.shortCode.value;
	var skuUnit = document.all.skuUnit.value;
	
	if (stuffNo==null||stuffNo.trim() == "") {
		alert("料号不能为空!");
		return false;
	}
	if (skuCode==null||skuCode.trim() == "") {
		alert("零件名称不能为空!");
		return false;
	}
	if (shortCode==null||shortCode.trim() == "") {
		alert("简称不能为空!");
		return false;
	}
	if (skuUnit==null||skuUnit.trim() == "") {
		alert("单位不能为空!");
		return false;
	}
	if(isChineseString(stuffNo)){
		alert("料号不能有中文!");
		return false;
	}
	
	document.all.stuffNo.value=stuffNo.trim();
	document.all.skuCode.value=skuCode.trim();
	document.all.shortCode.value=shortCode.trim();
	document.all.skuUnit.value=skuUnit.trim();

	return true;
}

String.prototype.trim = function()
{
	return this.replace( /(^\s*)|(\s*$)/g, '' ) ;
}


</SCRIPT>