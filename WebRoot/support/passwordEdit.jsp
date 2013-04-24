<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="com.dne.sie.common.tools.AtomRoleCheck"%> 

<html>
<head>
<title>passwordEdit</title>
<link rel="stylesheet" href="<%= request.getContextPath()%>/css/style.css">
<script language=javascript src="<%= request.getContextPath()%>/js/ajax.js"></script>
<script language=javascript src="<%= request.getContextPath()%>/js/inputValidation.js"></script>
</head>

<%
		Long userId=(Long)session.getAttribute("userId");
		String id=request.getParameter("id")==null?"":request.getParameter("id");
		if(id.equals("")){
				id=userId.toString();
		}
		boolean chkRole=true;
		if(AtomRoleCheck.checkRole(userId,"admin")){
			chkRole=false;
		}
%>
<body bgcolor="#ffffff" leftmargin="0" topmargin="0" rightmargin="0" bottommargin="0" marginwidth="0" marginheight="0" >

<form>
<table width="90%" border="0" align="center" cellpadding="2" cellspacing="1" class="content12">
  <tr> 
    <td>�����޸�</td>
  </tr>
</table>
<br>
<table width="98%" border="0" align="center" cellpadding="2" cellspacing="1" class="content12">
  <tr> 
    <td height="2" colspan="6" bgcolor="#677789"></td>
  </tr>
  <tr> 
    <td height="6"  colspan="6" bgcolor="#CECECE"></td>
  </tr>
  <%
  
  
  if(chkRole){%>
  <tr class="tableback1"> 
    <td width="22%"> ԭ����:</td>
    <td > <input type="password" name="oldPw" size="18" maxlength="50" class="form" ><font color="red">*</font> </td>
  </tr>
  <%}else{%>
  <input type="hidden" name="oldPw">
  <%}%>
  <tr class="tableback2"> 
    <td width="22%"> ������:</td>
    <td > <input type="password" name="newPw" size="18" maxlength="50" class="form" onkeydown="f_pwck()"><font color="red">*</font> </td>
  </tr>
  
  <tr class="tableback1"> 
    <td width="22%"> ������ȷ��:</td>
    <td > <input type="password" name="newPw2" size="18" maxlength="50" class="form" onkeydown="f_pwck()"><font color="red">*</font> </td>
  </tr>
  
  <tr>
	<td colspan="2"><font color='blue'>&nbsp;&nbsp;&nbsp;&nbsp;ֻ��ʹ����ĸ�����ֺ��»��ߣ���������6λ�����ٰ���2λ���ֺ�2λ��ĸ�����ִ�Сд</font><td>
  </tr>
  <tr>
	<td colspan="2" id="t1">&nbsp;<td>
  </tr>
  <tr> 
    <td height="2" colspan="6" bgcolor="#ffffff"></td>
  </tr>
  <tr> 
    <td height="1" colspan="6" bgcolor="#677789"></td>
  </tr>
  <tr align="left"> 
    <td colspan="6">
    	<input type="button" class="button2" value="ȷ��" onclick="f_pwchk()">
    </td>
  </tr>
  <tr> 
    <td height="10"></td>
  </tr>
 
</table>
</form>
</body>
</html>

<SCRIPT language=JAVASCRIPT1.2>

var ajax = new sack(); // ����ajax����
function f_pwchk() {
	var oldPw = document.forms[0].oldPw.value;
	var newPw = document.forms[0].newPw.value;
	var newPw2 = document.forms[0].newPw2.value;
	
	if(newPw == null || newPw == ""){
		alert("�����벻����Ϊ�գ�");
		document.forms[0].newPw.select();
	}else if(newPw2 == null || newPw2 == ""){
		alert("�����벻����Ϊ�գ�");
		document.forms[0].newPw2.select();
	}else if(newPw!=newPw2){
		t1.innerHTML="<font color='red'>�����������벻һ�£�����������</font>";
		document.forms[0].newPw.value="";
		document.forms[0].newPw2.value="";
	}else if(newPw.length<6){
		alert("���볤����С6λ");
		document.forms[0].newPw.value="";
		document.forms[0].newPw2.value="";
	}else if(!chkChar(newPw)){
		document.forms[0].newPw.value="";
		document.forms[0].newPw2.value="";
	}
	<%if(chkRole){%>
	else if(oldPw==''){
		alert("������ԭ���룡");
		document.forms[0].newPw.value="";
		document.forms[0].newPw2.value="";
	}
	else if(oldPw==newPw){
		alert("ԭ����������벻��һ����");
		document.forms[0].oldPw.value="";
		document.forms[0].newPw.value="";
		document.forms[0].newPw2.value="";
	}
	<%}%>
	else{
	
	<%if(chkRole){%>
		ajax.setVar("oldPw",oldPw); 			//������Ҫ������̨�Ĳ���
	<%}%>	
		ajax.setVar("newPw",newPw); 
		
		
		ajax.setVar("id","<%=id%>");
		ajax.setVar("method", "ajaxChkPw");		
		
		ajax.requestFile = "<%= request.getContextPath()%>/userAction.do";		//����Action
		ajax.method = "GET";				//�ύ����
		ajax.onCompletion = selectResult;	 	//ajax��������Ҫִ�еĺ���
		ajax.runAJAX();   
	}
}

function selectResult(){  
	
	var returnXml = ajax.responseXML;			

	var myisEnable = returnXml.getElementsByTagName("ifUse")[0].firstChild.nodeValue;
	if((eval(myisEnable))){
			returnValue="Y";
			self.close();
	}else{
		t1.innerHTML="<font color='red'>ԭ�����������������</font>";
		document.forms[0].oldPw.value="";
		document.forms[0].newPw.value="";
		document.forms[0].newPw2.value="";
	}
		
}

function chkChar(etext){	
    var flag = false;
    if(etext!=null&&etext!=''){
	var elen=etext.length;
	var aa;
	var charCount=0;
	var numCount=0;

	for (var i=0;i<elen;i++){
		aa=etext.charCodeAt(i);
		//* A~Z = 65~90 , a~z = 97~122
		if ((aa>=65&&aa<=90)||(aa>=97&&aa<=122)){
			charCount++;
		}else if(aa>=48&&aa<=57){	//0~9[)~!] = 48~57
			numCount++;
		}
	}
	if(charCount<2) {
	    alert("��������2λ��ĸ");  
    	}else if(numCount<2){
	    alert("��������2λ����");  
    	}else{
    	    flag = true;
    	}
    }		
    return flag;
}
</SCRIPT>