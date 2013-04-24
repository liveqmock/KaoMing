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
    <td>密码修改</td>
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
    <td width="22%"> 原密码:</td>
    <td > <input type="password" name="oldPw" size="18" maxlength="50" class="form" ><font color="red">*</font> </td>
  </tr>
  <%}else{%>
  <input type="hidden" name="oldPw">
  <%}%>
  <tr class="tableback2"> 
    <td width="22%"> 新密码:</td>
    <td > <input type="password" name="newPw" size="18" maxlength="50" class="form" onkeydown="f_pwck()"><font color="red">*</font> </td>
  </tr>
  
  <tr class="tableback1"> 
    <td width="22%"> 新密码确认:</td>
    <td > <input type="password" name="newPw2" size="18" maxlength="50" class="form" onkeydown="f_pwck()"><font color="red">*</font> </td>
  </tr>
  
  <tr>
	<td colspan="2"><font color='blue'>&nbsp;&nbsp;&nbsp;&nbsp;只能使用字母、数字和下划线，长度至少6位，至少包含2位数字和2位字母，区分大小写</font><td>
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
    	<input type="button" class="button2" value="确认" onclick="f_pwchk()">
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

var ajax = new sack(); // 创建ajax对象
function f_pwchk() {
	var oldPw = document.forms[0].oldPw.value;
	var newPw = document.forms[0].newPw.value;
	var newPw2 = document.forms[0].newPw2.value;
	
	if(newPw == null || newPw == ""){
		alert("新密码不可以为空！");
		document.forms[0].newPw.select();
	}else if(newPw2 == null || newPw2 == ""){
		alert("新密码不可以为空！");
		document.forms[0].newPw2.select();
	}else if(newPw!=newPw2){
		t1.innerHTML="<font color='red'>两次密码输入不一致，请重新输入</font>";
		document.forms[0].newPw.value="";
		document.forms[0].newPw2.value="";
	}else if(newPw.length<6){
		alert("密码长度最小6位");
		document.forms[0].newPw.value="";
		document.forms[0].newPw2.value="";
	}else if(!chkChar(newPw)){
		document.forms[0].newPw.value="";
		document.forms[0].newPw2.value="";
	}
	<%if(chkRole){%>
	else if(oldPw==''){
		alert("请输入原密码！");
		document.forms[0].newPw.value="";
		document.forms[0].newPw2.value="";
	}
	else if(oldPw==newPw){
		alert("原密码和新密码不能一样！");
		document.forms[0].oldPw.value="";
		document.forms[0].newPw.value="";
		document.forms[0].newPw2.value="";
	}
	<%}%>
	else{
	
	<%if(chkRole){%>
		ajax.setVar("oldPw",oldPw); 			//设置需要传到后台的参数
	<%}%>	
		ajax.setVar("newPw",newPw); 
		
		
		ajax.setVar("id","<%=id%>");
		ajax.setVar("method", "ajaxChkPw");		
		
		ajax.requestFile = "<%= request.getContextPath()%>/userAction.do";		//调用Action
		ajax.method = "GET";				//提交类型
		ajax.onCompletion = selectResult;	 	//ajax交互完需要执行的函数
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
		t1.innerHTML="<font color='red'>原密码错误，请重新输入</font>";
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
	    alert("密码至少2位字母");  
    	}else if(numCount<2){
	    alert("密码至少2位数字");  
    	}else{
    	    flag = true;
    	}
    }		
    return flag;
}
</SCRIPT>