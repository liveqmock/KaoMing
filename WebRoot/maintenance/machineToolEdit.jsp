<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>

<html>
<head>
<title>kaoming</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/styles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/autocomplete.css" /> 
<script language=javascript src="js/inputValidation.js"></script>
<script language=javascript src="js/checkValid.js"></script>
<script language=javascript src="js/ajax.js"></script>
<script language=javascript src="js/popCalendar.js"></script>
<script type="text/javascript" src="js/prototype.js"></script>
<script type="text/javascript" src="js/autocomplete.js"></script>
</head>
<%
		
try{
%>
<body>
<html:form action="machineToolAction.do?method=machineToolEdit" method="post" >
<html:hidden  property="machineId" /> 
<html:hidden  property="delFlag" /> 
<br>
<table width="99%" border="0" align="center" cellpadding="2" cellspacing="1" class="content12">
  <tr>
    <td>ǰ̨&gt;������Ϣ&gt;����������Ϣ��ϸ</td>
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
       <tr class="tableback1"> 
          <td width="80">�ͻ����ƣ�<font color='red'>*</font></td>
                        <html:hidden  property="customerId" />
          <td width="25%"><html:text property="customerName" styleClass="form" size="30" /> </td>
          <td >��ϵ�ˣ�</td>
          <td colspan="3"><html:text property="linkman"  styleClass="form" size="20"   maxlength="50"/></td>
        </tr>
       
        <tr class="tableback2"> 
          <td width="80"> �������ƣ�</td>
          <td ><html:text property="machineName"  styleClass="form" size="30"   maxlength="50"/></td>
          
          <td width="98"> ���ͣ�</td>
          <td width="126"><html:text property="modelCode"  styleClass="form" size="20" maxlength="50" /></td>
           
          <td width="98">������룺</td>
          <td width="121"><html:text property="serialNo"  styleClass="form" size="16" maxlength="50" /></td>
          
        </tr>
       
	  <tr class="tableback1"> 
	    <td width="80">�������ţ�</td>
          <td><html:text property="warrantyCardNo"  styleClass="form" size="30"  maxlength="40" /></td>
          
	    <td>�������ڣ�</td>
	    <td><html:text  styleClass="form" property="purchaseDateStr"  maxlength="8"  size="20"  onkeydown='javascript:input_date();' />
	    	<a onClick="event.cancelBubble=true;" href="javascript:showCalendar('imageCalendar3',true,'purchaseDateStr');">
				<img id="imageCalendar3" width="18" height="18" src="<%= request.getContextPath()%>/images/i_colock.gif" border="0" align="absmiddle">
			</a>
	    </td>
	  	<td>�ӱ����ڣ�</td>
	    <td><html:text  styleClass="form" property="extendedWarrantyDateStr"  maxlength="8"  size="20"  onkeydown='javascript:input_date();' />
	    	<a onClick="event.cancelBubble=true;" href="javascript:showCalendar('imageCalendar3',true,'extendedWarrantyDateStr');">
				<img id="imageCalendar3" width="18" height="18" src="<%= request.getContextPath()%>/images/i_colock.gif" border="0" align="absmiddle">
			</a>
	    </td>
	  </tr>
	   <tr> <td id="t1" colspan=6>&nbsp;</td> </tr>
  
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

</html:form>
</body>
<%
}catch(Exception e){
	e.printStackTrace();
}%>
</html>

<SCRIPT >
var ajax = new sack(); 
function f_save(){
	if(checkIsNull()){
		ajax.setVar("serialNo",document.forms[0].serialNo.value);
		ajax.setVar("machineId",document.forms[0].machineId.value);			 
		ajax.setVar("method", "ajaxChkSerial");		 
		ajax.requestFile = "machineToolAction.do";		 
		ajax.method = "GET";				 
		ajax.onCompletion = selectResult;
		
		ajax.runAJAX();   
	}
}


function checkIsNull() {
	
	if(f_isNull(document.forms[0].customerId,'�ͻ�ID')&&f_isNull(document.forms[0].machineName,'��������')
    	&&f_isNull(document.forms[0].modelCode,'����')&&f_isNull(document.forms[0].serialNo,'�������')
    	&&f_isNull(document.forms[0].warrantyCardNo,'��������')
    	&&f_isNull(document.forms[0].purchaseDateStr,'��������')&&f_isNull(document.forms[0].extendedWarrantyDateStr,'�ӱ�����')){
    	
    	return true;
	}else{
		return false;
	}
	
}






function selectResult(){  //ajax�Ӻ�̨�������ݺ�Ĵ�����
	
	var returnXml = ajax.responseXML;			//ajax���ص�xml��ʽ�ַ��������ֻ��Ҫ�����޸�ʽ�ı�������responseText
	
	var myisEnable = returnXml.getElementsByTagName("ifUse")[0].firstChild.nodeValue;
			
	if((eval(myisEnable))){
		if(document.forms[0].machineId.value==null||document.forms[0].machineId.value==0){
			document.forms[0].action="machineToolAction.do?method=insertMachineTool"; 
		}else{
			document.forms[0].action="machineToolAction.do?method=updateMachineTool"; 
		}
		
		document.forms[0].submit();
	}else{
		t1.innerHTML="<font color='red'>�û�������Ѿ�ע��!</font>";
	}
		
		
}

new AutoTip.AutoComplete("customerName", function() {
	 return "customerInfoAction.do?method=getCustInfo&inputValue=" + escape(this.text.value);
});

</script>

</SCRIPT>