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
		String rnd=(String)request.getAttribute("Rnd");

%>
<body>
<html:form action="customerInfoAction.do?method=customerInit" method="post" >
<input type="hidden" name="Rnd" value="<%=rnd%>">
<br>
<table width="99%" border="0" align="center" cellpadding="2" cellspacing="1" class="content12">
  <tr>
    <td>ǰ̨&gt;�ͻ�ά��&gt;�ͻ�����</td>
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
    
    <td>�ͻ�ID��<font color="red">*</font></td>
    <td colspan="2" ><html:text  styleClass="form" property="customerId"  maxlength="4"  size="20" 
    onkeydown="f_charAndNumber()" style="text-transform:uppercase" onblur="this.value=this.value.toUpperCase()"/> 
    	<A href="javascript:checkId()">[У��]</a>
    	<%if(idRepeat.equals("idRepeat")){%><font color='red'>��id�Ѿ���ʹ��</font> <%}%></td>
    <td id="t1">&nbsp;</td>
    <td>�ͻ����ƣ�<font color="red">*</font></td>
    <td ><html:text  styleClass="form" property="customerName"  maxlength="32"  size="32" /> </td>
    
  </tr>
 <tr class="tableback1"> 
    <td>�������� ��</td>
    <td><html:text  styleClass="form" property="postCode"  maxlength="16"  size="20"  onkeydown="input_postcode" /></td>
  	<td>�绰��<font color="red">*</font></td>
    <td ><html:text  styleClass="form" property="phone"  maxlength="32"  size="16"  /></td>
    <td>�ֻ���</td>
    <td ><html:text  styleClass="form" property="mobile"  maxlength="32"  size="16"  /></td>
  </tr>
   <tr class="tableback2"> 
    <td>���� ��</td>
    <td><html:text  styleClass="form" property="fax"  maxlength="32"  size="20"  /></td>
  	<td>�����ʼ���</td>
    <td ><html:text  styleClass="form" property="email"  maxlength="32"  size="16"  /></td>
    <td>��ϵ�ˣ�<font color="red">*</font></td>
    <td ><html:text  styleClass="form" property="linkman"  maxlength="16"  size="16"  /> </td>
    
  </tr>
  <tr class="tableback1"> 
    <td>ʡ�� ��<font color="red">*</font></td>
    <td><html:text  styleClass="form" property="provinceName"  maxlength="8"  size="20"  /></td>
  	<td>���У�<font color="red">*</font></td>
    <td ><html:text  styleClass="form" property="cityName"  maxlength="16"  size="16"  /></td>
    <td>��ַ��<font color="red">*</font></td>
    <td ><html:text  styleClass="form" property="address"  maxlength="200"  size="40"  /></td>
  </tr>

    <tr class="tableback2">
        <td>�ϰ�ʱ�䣺</td>
        <td ><html:text  styleClass="form" property="workHours"  maxlength="20"  size="16"  /></td>
        <td>˫�ݣ�</td>
        <td ><html:text  styleClass="form" property="weekEnd"  maxlength="20"  size="16"  /></td>
        <td></td>
        <td ></td>
    </tr>
  <tr class="tableback1">
    <td>������ ��</td>
    <td><html:text  styleClass="form" property="bank"  maxlength="45"  size="30"  /></td>
  	<td>�����˺ţ�</td>
    <td ><html:text  styleClass="form" property="bankAccount"  maxlength="45"  size="30"  /></td>
    <td>˰�ţ�</td>
    <td><html:text  styleClass="form" property="taxNumber"  maxlength="45"  size="30"  /></td>
  </tr>
  <tr class="tableback2">
    <td valign="top">��ע��</td>
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
        <input type="button" name="save" value="����"  onclick="f_save()">
      </div></td>
  </tr>
</table>
</body>
</html:form>
</html>

<SCRIPT >
var ajaxRt = new sack(); // ����ajax����
function f_save(){
	if(checkIsNull()){
		<%if(rnd==null){%>
		document.forms[0].action="customerInfoAction.do?method=insertCustomer"; 
		document.forms[0].submit();
		<%}else{%>
		
		ajaxRt.setVar("customerId",document.forms[0].customerId.value);
		ajaxRt.setVar("customerName",escape(document.forms[0].customerName.value));
		ajaxRt.setVar("postCode",document.forms[0].postCode.value);
		ajaxRt.setVar("phone",document.forms[0].phone.value);
		ajaxRt.setVar("mobile",document.forms[0].mobile.value);
		ajaxRt.setVar("fax",document.forms[0].fax.value);
		ajaxRt.setVar("email",document.forms[0].email.value);
		ajaxRt.setVar("linkman",escape(document.forms[0].linkman.value));
		ajaxRt.setVar("provinceName",escape(document.forms[0].provinceName.value));
		ajaxRt.setVar("cityName",escape(document.forms[0].cityName.value));
		ajaxRt.setVar("address",escape(document.forms[0].address.value));
		ajaxRt.setVar("remark",escape(document.forms[0].remark.value));
	
		ajaxRt.setVar("method", "insertCustomerWithTurning");		
		ajaxRt.requestFile = "customerInfoAction.do";		
		ajaxRt.method = "POST";				
		ajaxRt.onCompletion = custReturn;	 
		ajaxRt.runAJAX();  
		<%}%>
	}
}


function custReturn(){  
	
	var returnXml = ajaxRt.responseXML;	
	var myisEnable = returnXml.getElementsByTagName("ifUse")[0].firstChild.nodeValue;

	if(myisEnable!='false'){
		returnValue=myisEnable;
		self.close();
	}else{
		alert("���ʧ�ܣ�");
	}
		
		
}


function checkIsNull() {
	
	if(f_isNull(document.forms[0].customerId,'�ͻ�ID')&&f_isNull(document.forms[0].customerName,'�ͻ�����')
		&&f_isNull(document.forms[0].linkman,'��ϵ��')&&f_isNull(document.forms[0].phone,'�绰')
		&&f_isNull(document.forms[0].provinceName,'ʡ��')&&f_isNull(document.forms[0].cityName,'����')
		&&f_isNull(document.forms[0].address,'��ַ')
    	&&verifyEmail(document.forms[0].email)&&f_maxLength(document.forms[0].remark,'��ע',256)){
    	if(document.forms[0].customerId.value.length<3){
    		alert("�ͻ�ID���ȱ���3λ");
    		return false;
    	}else{
    		return true;
    	}
	}else{
			return false;
	}
	
}


function checkId(){
	var customerId=document.forms[0].customerId.value;
	if(customerId!=null&&customerId!=''){
		ajaxChk(customerId);
	}else{
		alert("��������ͻ�ID");
	}
}


var ajax = new sack(); // ����ajax����

function ajaxChk(code){ // ����ajax
	ajax.setVar("custId",code); 			//������Ҫ������̨�Ĳ���
	ajax.setVar("method", "ajaxChkId");		//����Action�еķ���
	ajax.requestFile = "customerInfoAction.do";		//����Action
	ajax.method = "GET";				 //�ύ����
	ajax.onCompletion = selectResult;	 	//ajax��������Ҫִ�еĺ���
	
	ajax.runAJAX();                                    //����ajax
}


function selectResult(){  //ajax�Ӻ�̨�������ݺ�Ĵ�����
	
	var returnXml = ajax.responseXML;			//ajax���ص�xml��ʽ�ַ��������ֻ��Ҫ�����޸�ʽ�ı�������responseText
	
	var myisEnable = returnXml.getElementsByTagName("ifUse")[0].firstChild.nodeValue;
			
	if((eval(myisEnable))){
		t1.innerHTML="<font color='blue'>��id����ʹ��</font>";
	}else{
		t1.innerHTML="<font color='red'>��id�Ѿ���ʹ��</font>";
	}
		
		
}

</SCRIPT>