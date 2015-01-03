<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ page import="com.dne.sie.common.tools.DicInit"%>
<%@ page import="java.util.ArrayList"%> 

<html>
<head>
<title>employeeInfo</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/styles.css" rel="stylesheet" type="text/css" />
<script language=javascript src="js/inputValidation.js"></script>
<script language=javascript src="js/checkValid.js"></script>
<script language=javascript src="js/popCalendar.js"></script>
<script language=javascript src="js/ajax.js"></script>
</head>
<%
	String idRepeat=(String)request.getAttribute("idRepeat")==null?"":(String)request.getAttribute("idRepeat");
	ArrayList sexList = (ArrayList)DicInit.SYS_CODE_MAP.get("SEX");
	ArrayList positionList = (ArrayList)DicInit.SYS_CODE_MAP.get("POSITION");
	String flag=(String)request.getAttribute("flag")==null?"":(String)request.getAttribute("flag");
	
	
%>
<body>
<html:form action="employeeInfoAction.do?method=employeeInit" method="post" >
<html:hidden property="employeeCode"/>
<html:hidden property="delFlag"/>
<br>
<table width="99%" border="0" align="center" cellpadding="2" cellspacing="1" class="content12">
  <tr>
    <td>����&gt;������Ϣ&gt;Ա����Ϣ</td>
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
    
    <td >Ա��������<font color="red">*</font></td>
    <td colspan="4" ><html:text  styleClass="form" property="employeeName"  maxlength="45"  size="20" onkeydown="f_filt()"/> 
    	<A href="javascript:checkId()">[У��]</a>
    	<%if(idRepeat.equals("idRepeat")){%><font color='red'>�������Ѵ���</font> <%}%></td>
    <td id="t1">&nbsp;</td>
    
    
  </tr>
 <tr class="tableback1"> 
    <td>���� ��</td>
    <td><html:text  styleClass="form" property="birthday" styleId="birthday" readonly="true" size="14"  />
    <a onclick="event.cancelBubble=true;" href="javascript:showCalendar('imageOne',true,'birthday');">
		<img src="googleImg/icon/calendar.gif" id="imageOne" width="18" height="18" border="0" align="absmiddle">
	</a>
    </td>
    <td>��ְ���ڣ�</td>
    <td ><html:text  styleClass="form" property="employedDate" styleId="employedDate" readonly="true" size="14" />
    <a onclick="event.cancelBubble=true;" href="javascript:showCalendar('imageOne',true,'employedDate');">
		<img src="googleImg/icon/calendar.gif" id="imageOne" width="18" height="18" border="0" align="absmiddle">
	</a>
    </td>
  	<td>�Ա�<font color="red">*</font></td>
    <td ><html:select property="sex"  styleClass="form">	
		<%for(int i=0;i<sexList.size();i++){
                  String[] temp=(String[])sexList.get(i);
                 %>
                  <html:option value="<%=temp[0]%>"><%=temp[1]%></html:option>
                <%}%>
      	</html:select> </td>
  </tr>
  <tr class="tableback2"> 
  <td>QQ��</td>
   <td ><html:text  styleClass="form" property="qq"  maxlength="32"  size="16" onkeydown="f_onlynumber()" /></td>
   <td>MSN��</td>
   <td ><html:text  styleClass="form" property="msn"  maxlength="45"  size="20"  /></td>
   <td>���䣺</td>
    <td ><html:text  styleClass="form" property="email"  maxlength="45"  size="24"  /></td>
  </tr>
  
  <tr class="tableback1"> 
   <td>�绰��</td>
   <td ><html:text  styleClass="form" property="phone"  maxlength="32"  size="16"  /></td>
   <td>���֤�ţ�</td>
   <td ><html:text  styleClass="form" property="identityCard"  maxlength="32"  size="20"  /></td>
   <td>ְ��</td>
   <td ><html:select property="position"  styleClass="form">	
		<%for(int i=0;i<positionList.size();i++){
                  String[] temp=(String[])positionList.get(i);
                 %>
                  <html:option value="<%=temp[0]%>"><%=temp[1]%></html:option>
                <%}%>
      	</html:select></td>
   
  </tr>
  <tr class="tableback2"> 
  <td>��ַ ��</td>
    <td colspan="5"><html:text  styleClass="form" property="address"  maxlength="256"  size="24"  /></td>
    </tr>
  <tr class="tableback1"> 
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
function f_save(){
	
	if(checkIsNull()){
		<%if("init".equals(flag)){%>
		document.forms[0].action="employeeInfoAction.do?method=insertEmployee"; 
		<%}else{%>
		document.forms[0].action="employeeInfoAction.do?method=updateEmployee"; 
		<%}%>
		document.forms[0].submit();
	}
}


function checkIsNull() {
	
	if(f_isNull(document.forms[0].employeeName,'Ա������')
    	&&verifyEmail(document.forms[0].email)&&f_maxLength(document.forms[0].remark,'��ע',256)){
    		var empName=document.forms[0].employeeName.value;
			document.forms[0].employeeName.value=empName.trim();
    		return true;
	}else{
			return false;
	}
	
}


function checkId(){
	var empName=document.forms[0].employeeName.value;
	if(empName!=null&&empName!=''){
		ajaxChk(empName);
	}else{
		alert("��������Ա������");
	}
}


var ajax = new sack(); // ����ajax����

function ajaxChk(code){ // ����ajax
	ajax.setVar("empName",escape(code)); 			//������Ҫ������̨�Ĳ���
	ajax.setVar("method", "ajaxChkName");		//����Action�еķ���
	ajax.requestFile = "employeeInfoAction.do";		//����Action
	ajax.method = "GET";				 //�ύ����
	ajax.onCompletion = selectResult;	 	//ajax��������Ҫִ�еĺ���
	
	ajax.runAJAX();                                    //����ajax
}


function selectResult(){  //ajax�Ӻ�̨�������ݺ�Ĵ�����
	
	var returnXml = ajax.responseXML;			//ajax���ص�xml��ʽ�ַ��������ֻ��Ҫ�����޸�ʽ�ı�������responseText
	
	var myisEnable = returnXml.getElementsByTagName("ifUse")[0].firstChild.nodeValue;
			
	if((eval(myisEnable))){
		t1.innerHTML="<font color='blue'>OK</font>";
	}else{
		t1.innerHTML="<font color='red'>�������Ѵ���</font>";
	}
		
		
}

function f_filt(){
	var src = event.srcElement;
	var key = event.keyCode;

	//��������ո�tab  (tabspace = 9,space = 32)
	if (key<8||key==9||key==32){
		event.returnValue = false;
	}

}

String.prototype.trim = function()
{
	return this.replace( /(^\s*)|(\s*$)/g, '' ) ;
}
</SCRIPT>