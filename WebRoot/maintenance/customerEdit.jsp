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

<body>
<html:form action="customerInfoAction.do?method=customerEdit" method="post" >
<br>
<table width="99%" border="0" align="center" cellpadding="2" cellspacing="1" class="content12">
  <tr>
    <td>前台&gt;客户维护&gt;客户修改</td>
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
    
    <td>客户ID：<font color="red">*</font></td>
    <td  ><html:text  styleClass="form" property="customerId"  maxlength="4"  size="20"  readonly="true"/> </td>
    <td>客户名称：<font color="red">*</font></td>
    <td colspan="3" ><html:text  styleClass="form" property="customerName"  maxlength="32"  size="45" /> </td>
    
  </tr>
  <tr class="tableback1"> 
    <td>邮政编码 ：</td>
    <td><html:text  styleClass="form" property="postCode"  maxlength="16"  size="20"  onkeydown="input_postcode" /></td>
  	<td>电话：<font color="red">*</font></td>
    <td ><html:text  styleClass="form" property="phone"  maxlength="32"  size="16"  /></td>
    <td>手机：</td>
    <td ><html:text  styleClass="form" property="mobile"  maxlength="32"  size="16"  /></td>
  </tr>
   <tr class="tableback2"> 
    <td>传真 ：</td>
    <td><html:text  styleClass="form" property="fax"  maxlength="32"  size="20"  /></td>
  	<td>电子邮件：</td>
    <td ><html:text  styleClass="form" property="email"  maxlength="32"  size="16"  /></td>
    <td>联系人：<font color="red">*</font></td>
    <td ><html:text  styleClass="form" property="linkman"  maxlength="16"  size="16"  /> </td>
    
  </tr>
  <tr class="tableback1"> 
    <td>省份 ：<font color="red">*</font></td>
    <td><html:text  styleClass="form" property="provinceName"  maxlength="8"  size="20"  /></td>
  	<td>城市：<font color="red">*</font></td>
    <td ><html:text  styleClass="form" property="cityName"  maxlength="16"  size="16"  /></td>
    <td></td>
    <td ></td>
  </tr>
  
   <tr class="tableback1"> 
    <td>上班时间：</td>
    <td ><html:text  styleClass="form" property="workHours"  maxlength="20"  size="16"  /></td>
    <td>双休：</td>
    <td ><html:text  styleClass="form" property="weekEnd"  maxlength="20"  size="16"  /></td>
    <td></td>
    <td ></td>
  </tr>
  
  <tr class="tableback2"> 
    <td valign="top">地址：<font color="red">*</font></td>
    <td > <html:text  styleClass="form" property="address"  maxlength="200"  size="40"  /></td>
    <td>开户行 ：</td>
    <td><html:text  styleClass="form" property="bank"  maxlength="45"  size="30"  /></td>
  	<td>银行账号：</td>
    <td ><html:text  styleClass="form" property="bankAccount"  maxlength="45"  size="30"  /></td>
  
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
      </div></td>
  </tr>
</table>
</body>
</html:form>
</html>

<SCRIPT >
function f_save(){
	if(checkIsNull()){
		document.forms[0].action="customerInfoAction.do?method=updateCustomer"; 
		document.forms[0].submit();
	}
}


function checkIsNull() {
	
	if(f_isNull(document.forms[0].customerId,'客户ID')&&f_isNull(document.forms[0].customerName,'客户名称')
		&&f_isNull(document.forms[0].linkman,'联系人')&&f_isNull(document.forms[0].phone,'电话')
		&&f_isNull(document.forms[0].provinceName,'省份')&&f_isNull(document.forms[0].cityName,'城市')
		&&f_isNull(document.forms[0].address,'地址')
    	&&verifyEmail(document.forms[0].email)&&f_maxLength(document.forms[0].remark,'备注',256)){
    	
    	return true;
	}else{
			return false;
	}
	
}

function checkId(){
	var customerId=document.forms[0].customerId.value;
	if(customerId!=null&&customerId!=''){
		ajaxChk(customerId);
	}else{
		alert("请先输入客户ID");
	}
}


var ajax = new sack(); // 创建ajax对象

function ajaxChk(code){ // 调用ajax
	ajax.setVar("custId",code); 			//设置需要传到后台的参数
	ajax.setVar("method", "ajaxChkId");		//调用Action中的方法
	ajax.requestFile = "customerInfoAction.do";		//调用Action
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