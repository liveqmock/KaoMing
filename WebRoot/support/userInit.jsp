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
<script language=javascript src="js/popCalendar.js"></script>
<script language=javascript src="js/common.js"></script>
    <SCRIPT language="javascript" src="js/commonSelect.js"></SCRIPT>
    <script language=javascript src="js/checkValid.js"></script>
<script language=javascript src="js/ajax.js"></script>
<script language=javascript src="js/inputValidation.js"></script>

</head>
<%
		String idRepeat=(String)request.getAttribute("idRepeat")==null?"":(String)request.getAttribute("idRepeat");
		ArrayList sexList = (ArrayList)DicInit.SYS_CODE_MAP.get("SEX");
		
		ArrayList<String[]> empList = (ArrayList<String[]>)request.getAttribute("empList");
        ArrayList<String[]> roleList = (ArrayList<String[]>)request.getAttribute("roleList");


%>
<body>
<html:form method="post" action="userAction.do?method=userAdd">
<html:hidden property="id"/>
<html:hidden property="roleCode"/>
<html:hidden property="userName"/>
<br>
<table width="90%" border="0" align="center" cellpadding="2" cellspacing="1" class="content12">
  <tr>
    <td>����&gt;�û�&gt;�û�����</td>
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
    
    <td>�û�ID��<font color="red">*</font></td>
    <td colspan="3"><html:text  styleClass="form" property="employeeCode"  maxlength="20"  size="16"  />
    <A href="javascript:checkId()">[У��]</a> <font color="red">*</font> 
    	 <%if(idRepeat.equals("idRepeat")){%><font color='red'>��id�Ѿ���ʹ��</font> <%}%>
    	 <font color='blue' size="1">&nbsp;&nbsp;ֻ��ʹ����ĸ�����ֺ��»���</font></td>
     <td colspan="2" id="t1">&nbsp;</td>
  </tr>
  <tr class="tableback1"> 
    <td>�û����ƣ�<font color="red">*</font></td>
    <td><html:select property="employeeId"  styleClass="form" onchange="f_username(this)">	
    	<html:option value="">===��ѡ��===</html:option>
		<%for(int i=0;empList!=null&&i<empList.size();i++){
                  String[] temp=empList.get(i);
                 %>
                  <html:option value="<%=temp[0]%>"><%=temp[1]%></html:option>
                <%}%>
      	</html:select> </td>
  	<td width="97">�û����룺<font color="red">*</font></td>
    <td colspan="3"><input name="password" type="password" class="form" size="16" maxlength="20" onkeydown="f_pwck()">
     <font color='blue'>&nbsp;&nbsp;ֻ��ʹ����ĸ�����ֺ��»���</font></td>
  </tr>
   <tr class="tableback2">
    <td>�����ʼ���</td>
    <td><html:text property="email"  styleClass="form" size="16" maxlength="30"/></td>
    <td>�绰��</td>
    <td><html:text property="phone"  styleClass="form" size="16" maxlength="30"/></td>
    <td > �Ա� ��</td>
    <td > <html:select property="sex"  styleClass="form">	
		<%for(int i=0;sexList!=null&&i<sexList.size();i++){
                  String[] temp=(String[])sexList.get(i);
                 %>
                  <html:option value="<%=temp[0]%>"><%=temp[1]%></html:option>
                <%}%>
      	</html:select> </td>
  </tr>
 <tr class="tableback1"> 
    <td width="97"> ��ɫѡ�� ��<font color="red">*</font></td>
    <td colspan="5">
        <%for(int i = 0; i< roleList.size();i++){
            String[] temp = roleList.get(i);
        %>
        <input type="checkbox" name="chk" value="<%=temp[0]%>">&nbsp;<%=temp[1]%>

        <%}%>
    </td>
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
        <input type="button" name="save" value="����"  onclick="f_submit()">
        <input type="button"  value="�ر�"  onclick="self.close();">
      </div></td>
  </tr>
</table>
</body>
</html:form>
</html>

<SCRIPT >

function f_submit(){

    if(f_check()){
    	document.forms[0].submit(); 
    }
}

function f_check(){
    var retFlag=false;
    if(f_isNull(document.forms[0].employeeCode,'�û�ID')&&f_isNumChar2(document.forms[0].employeeCode,'�û�ID')
    	&&f_isNull(document.forms[0].userName,'�û�����')&&verifyEmail(document.forms[0].email)
    	&&f_isNull(document.forms[0].password,'�û�����')&&f_isNumChar2(document.forms[0].password,'�û�����')
    	&&f_maxLength(document.forms[0].remark,'��ע',50)){
 
	var roleCode=chk();
	var pw=document.forms[0].password.value;
	var uName=document.forms[0].userName.value;
	uName=uName.replaceAll(" ","");
	uName=uName.replaceAll("	","");
	document.forms[0].userName.value=uName;
		
	if(pw!=null&&pw!=''&&pw.length<6){
		alert("���볤����С6λ");
	}else if(!chkChar(pw)){
		
	}else if(roleCode==null||roleCode==''){
		alert("��ѡ���ɫ��");
	}else{
        document.forms[0].roleCode.value=roleCode;
		retFlag =  true;
	}
    }
    return retFlag;
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
		if(charCount<2||numCount<2) {
		    alert("��������2λ��ĸ��2λ����"); 
	  }else{
	    	flag = true;
	 	}
	}		
	return flag;
}


function checkId(){
	var employeeCode=document.forms[0].employeeCode.value;
	if(employeeCode!=null&&employeeCode!=''){
		ajaxChk(employeeCode);
	}else{
		alert("���������û�ID");
	}
}

var ajax = new sack(); // ����ajax����

function ajaxChk(code){ // ����ajax
	ajax.setVar("empCode",code); 			//������Ҫ������̨�Ĳ���
	ajax.setVar("method", "ajaxChk");		//����Action�еķ���
	ajax.requestFile = "userAction.do";		//����Action
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


function f_username(obj){

	document.forms[0].userName.value=obj.options[obj.selectedIndex].text;
	if(document.forms[0].userName.value=='===��ѡ��==='){
		document.forms[0].userName.value='';
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

String.prototype.replaceAll  = function(s1,s2){    
	return this.replace(new RegExp(s1,"gm"),s2);    
}    

String.prototype.trim = function()
{
	return this.replace( /(^\s*)|(\s*$)/g, '' ) ;
}
</SCRIPT>