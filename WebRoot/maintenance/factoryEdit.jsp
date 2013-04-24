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
    <td>ǰ̨&gt;����ά��&gt;�����޸�</td>
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
    
    <td>����ID��<font color="red">*</font></td>
    <td colspan="2" ><html:text  styleClass="form" property="factoryId"  maxlength="4"  size="20"  readonly="true"/> </td>
    <td>�������ƣ�<font color="red">*</font></td>
    <td colspan="3" ><html:text  styleClass="form" property="factoryName"  maxlength="32"  size="16" /> </td>
    
  </tr>
  <tr class="tableback1"> 
    <td>�������� ��</td>
    <td><html:text  styleClass="form" property="postCode"  maxlength="16"  size="20"  onkeydown="input_postcode" /></td>
  	<td>�绰��</td>
    <td ><html:text  styleClass="form" property="phone"  maxlength="32"  size="16"  /></td>
    <td>�ֻ���</td>
    <td ><html:text  styleClass="form" property="mobile"  maxlength="32"  size="16"  /></td>
  </tr>
   <tr class="tableback2"> 
    <td>���� ��</td>
    <td><html:text  styleClass="form" property="fax"  maxlength="32"  size="20"  /></td>
  	<td>�����ʼ���</td>
    <td ><html:text  styleClass="form" property="email"  maxlength="32"  size="16"  /></td>
    <td>��ϵ�ˣ�</td>
    <td ><html:text  styleClass="form" property="linkman"  maxlength="16"  size="16"  /> </td>
    
  </tr>
  <tr class="tableback1"> 
    <td>ʡ�� ��</td>
    <td><html:text  styleClass="form" property="provinceName"  maxlength="8"  size="20"  /></td>
  	<td>���У�</td>
    <td ><html:text  styleClass="form" property="cityName"  maxlength="16"  size="16"  /></td>
    <td>��ַ��</td>
    <td ><html:text  styleClass="form" property="address"  maxlength="200"  size="32"  /></td>
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
		document.forms[0].action="factoryInfoAction.do?method=updateFactory"; 
		document.forms[0].submit();
	}
}


function checkIsNull() {
	
	if(f_isNull(document.forms[0].factoryId,'����ID')&&f_isNull(document.forms[0].factoryName,'��������')
    	&&verifyEmail(document.forms[0].email)&&f_maxLength(document.forms[0].remark,'��ע',256)){
    	
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
		alert("�������볧��ID");
	}
}


var ajax = new sack(); // ����ajax����

function ajaxChk(code){ // ����ajax
	ajax.setVar("custId",code); 			//������Ҫ������̨�Ĳ���
	ajax.setVar("method", "ajaxChkId");		//����Action�еķ���
	ajax.requestFile = "factoryInfoAction.do";		//����Action
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