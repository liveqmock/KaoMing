<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>

<html>
<head>
<title>newsis</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/styles.css" rel="stylesheet" type="text/css" />
<script language=javascript src="js/inputValidation.js"></script>
<script language=javascript src="js/checkValid.js"></script>
<script language=javascript src="js/ajax.js"></script>
</head>
<%
		String idRepeat=(String)request.getAttribute("idRepeat")==null?"":(String)request.getAttribute("idRepeat");

%>
<body>
<html:form action="factoryInfoAction.do?method=factoryEdit" method="post" >
<br>
<table width="99%" border="0" align="center" cellpadding="2" cellspacing="1" class="content12">
  <tr>
    <td>前台&gt;厂商维护&gt;厂商修改</td>
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
    
    <td>厂商ID：<font color="red">*</font></td>
    <td colspan="2" ><html:text  styleClass="form" property="factoryId"  maxlength="4"  size="20"  readonly="true"/> </td>
    <td>厂商名称：<font color="red">*</font></td>
    <td colspan="3" ><html:text  styleClass="form" property="factoryName"  maxlength="32"  size="16" /> </td>
    
  </tr>
  <tr class="tableback1"> 
    <td>邮政编码 ：</td>
    <td><html:text  styleClass="form" property="postCode"  maxlength="16"  size="20"  onkeydown="input_postcode" /></td>
  	<td>电话：</td>
    <td ><html:text  styleClass="form" property="phone"  maxlength="32"  size="16"  /></td>
    <td>手机：</td>
    <td ><html:text  styleClass="form" property="mobile"  maxlength="32"  size="16"  /></td>
  </tr>
   <tr class="tableback2"> 
    <td>传真 ：</td>
    <td><html:text  styleClass="form" property="fax"  maxlength="32"  size="20"  /></td>
  	<td>电子邮件：</td>
    <td ><html:text  styleClass="form" property="email"  maxlength="32"  size="16"  /></td>
    <td>联系人：</td>
    <td ><html:text  styleClass="form" property="linkman"  maxlength="16"  size="16"  /> </td>
    
  </tr>
  <tr class="tableback1"> 
    <td>省份 ：</td>
    <td><html:text  styleClass="form" property="provinceName"  maxlength="8"  size="20"  /></td>
  	<td>城市：</td>
    <td ><html:text  styleClass="form" property="cityName"  maxlength="16"  size="16"  /></td>
    <td>地址：</td>
    <td ><html:text  styleClass="form" property="address"  maxlength="200"  size="32"  /></td>
  </tr>
  <tr class="tableback2"> 
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
		document.forms[0].action="factoryInfoAction.do?method=updateFactory"; 
		document.forms[0].submit();
	}
}


function checkIsNull() {
	
	if(f_isNull(document.forms[0].factoryId,'厂商ID')&&f_isNull(document.forms[0].factoryName,'厂商名称')
    	&&verifyEmail(document.forms[0].email)&&f_maxLength(document.forms[0].remark,'备注',256)){
    	
    	return true;
	}else{
			return false;
	}
	
}

function checkId(){
	var factoryId=document.forms[0].factoryId.value;
	if(factoryId!=null&&factoryId!=''){
		ajaxChk(factoryId);
	}else{
		alert("请先输入厂商ID");
	}
}


var ajax = new sack(); // 创建ajax对象

function ajaxChk(code){ // 调用ajax
	ajax.setVar("custId",code); 			//设置需要传到后台的参数
	ajax.setVar("method", "ajaxChkId");		//调用Action中的方法
	ajax.requestFile = "factoryInfoAction.do";		//调用Action
	ajax.method = "GET";				 //提交类型
	ajax.onCompletion = selectResult;	 	//ajax交互完需要执行的函数
	
	ajax.runAJAX();                                    //启动ajax
}


function selectResult(){  //ajax从后台传回数据后的处理函数
	
	var returnXml = ajax.responseXML;			//ajax返回的xml格式字符串，如果只需要传回无格式文本，则是responseText
	
	var myisEnable = returnXml.getElementsByTagName("ifUse")[0].firstChild.nodeValue;
			
	if((eval(myisEnable))){
		t1.innerHTML="<font color='blue'>该id可以使用</font>";
	}else{
		t1.innerHTML="<font color='red'>该id已经被使用</font>";
	}
		
		
}

</SCRIPT>