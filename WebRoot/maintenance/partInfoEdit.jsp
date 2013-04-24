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
<script language=javascript src="js/ajax.js"></script>

<%
		String stuffNo=(String)request.getAttribute("stuffNo");
		ArrayList partTypeList=(ArrayList) DicInit.SYS_CODE_MAP.get("PART_TYPE");
%>
</head>
<body onload="f_init()">
<html:form action="partInfoAction.do?method=editPartInfo" method="post" >
<input type="hidden" name="oldStuffNo" value="<%=stuffNo%>">
<br>
<table width="99%" border="0" align="center" cellpadding="2" cellspacing="1" class="content12">
  <tr>
    <td>库存&gt;零件维护&gt;零件修改</td>
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
    <td><html:text  styleClass="form" property="buyCost"  maxlength="8"  size="20"  /></td>
    <td>报价：</td>
    <td><html:text  styleClass="form" property="saleCost"  maxlength="8"  size="16"  /></td>
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
        <input type="button" name="save" value="保存"  onclick="f_save()">
        <input type="button"  value="料号修改"  onclick="f_stuffNo_update()">
      </div></td>
  </tr>
</table>
</body>
</html:form>
</html>

<SCRIPT >
function f_init(){
	if(document.all.buyCost.value!=''){
		document.all.buyCost.value=new Number(document.all.buyCost.value).toFixed(2);
	}
	if(document.all.saleCost.value!=''){
		document.all.saleCost.value=new Number(document.all.saleCost.value).toFixed(2);
	}

}
function f_save(){
	if(checkIsNull()){
		document.forms[0].action="partInfoAction.do?method=updatePartInfo"; 
		document.forms[0].submit();
	}
}

var ajax = new sack();
function f_stuffNo_update(){
	if(confirm("销售单和库存的该料号也会一起修改，是否确认？")){

		if(document.all.stuffNo.value!=document.all.oldStuffNo.value){
					ajax.setVar("stuffNo",document.all.stuffNo.value);
					ajax.setVar("method", "chkStuffNo");	
					ajax.requestFile = "partInfoAction.do";
					ajax.method = "GET";
					ajax.onCompletion = chkCompleted;	
					ajax.runAJAX();  
					
		}else{
			alert("料号未变化!");
		}
	}
}

function chkCompleted(){ 
		var returnXml = ajax.responseXML;			
		var flag = returnXml.getElementsByTagName("flag")[0].firstChild.nodeValue;
		if((eval(flag))){
				document.forms[0].action="partInfoAction.do?method=updateStuffNo"; 
				document.forms[0].submit();
				
		}else{
				alert("该料号已经存在");
				document.all.stuffNo.focus();
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