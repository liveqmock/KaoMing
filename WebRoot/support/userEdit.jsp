<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri=  "/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ page import="java.util.ArrayList"%> 
<%@ page import="com.dne.sie.common.tools.AtomRoleCheck"%> 
<%@ page import="com.dne.sie.common.tools.DicInit"%>

<html>
<head>
<title>newsis</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/styles.css" rel="stylesheet" type="text/css" />
<script language=javascript src="js/popCalendar.js"></script>
<script language=javascript src="js/common.js"></script>
<script language=javascript src="js/checkValid.js"></script>
<script language=javascript src="js/inputValidation.js"></script>

</head>
<%
		String strUserId=(String)request.getAttribute("strUserId")==null?"":(String)request.getAttribute("strUserId");
		ArrayList sexList = (ArrayList)DicInit.SYS_CODE_MAP.get("SEX");
		
		boolean chkUser=false;
		Long userId=(Long)session.getAttribute("userId");
		if((userId!=null&&userId.toString().equals(strUserId))||AtomRoleCheck.checkRole(userId,"admin")){
			chkUser=true;
		}
		
		ArrayList<String[]> empList = (ArrayList<String[]>)request.getAttribute("empList");

%>
<body>
<html:form method="post" action="userAction.do?method=userModify">
<html:hidden property="id"/>
<html:hidden property="delFlag"/>
<html:hidden property="roleCode"/>
<html:hidden property="userName"/>
<br>
<table width="90%" border="0" align="center" cellpadding="2" cellspacing="1" class="content12">
  <tr>
    <td>管理&gt;用户&gt;用户修改</td>
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
    
    <td>用户ID：<font color="red">*</font></td>
    <td colspan="5"><html:text property="employeeCode"  styleClass="form" size="16" readonly="true"/></td>
    
  </tr>
  <tr class="tableback1"> 
    <td>用户名称：<font color="red">*</font></td>
    <td><html:select property="employeeId"  styleClass="form" onchange="f_username(this)">	
    	<option value="<bean:write property="employeeId" name="userForm" />"><bean:write property="userName" name="userForm" /></option>
		<%for(int i=0;empList!=null&&i<empList.size();i++){
                  String[] temp=empList.get(i);
                 %>
                  <html:option value="<%=temp[0]%>"><%=temp[1]%></html:option>
                <%}%>
      	</html:select>
      </td>
  	
    <td colspan="4">&nbsp;<%if(chkUser){%><a href="javascript:f_pw()"><font color='blue'>[密码修改]</font></a><%}%>
    <html:hidden property="password"/></td>
  </tr>
   <tr class="tableback2">
    <td>电子邮件：</td>
    <td><html:text property="email"  styleClass="form" size="16" maxlength="30"/></td>
    <td>电话：</td>
    <td><html:text property="phone"  styleClass="form" size="16" maxlength="30"/></td>
    <td > 性别 ：</td>
    <td > <html:select property="sex"  styleClass="form">	
		<%for(int i=0;sexList!=null&&i<sexList.size();i++){
                  String[] temp=(String[])sexList.get(i);
                 %>
                  <html:option value="<%=temp[0]%>"><%=temp[1]%></html:option>
                <%}%>
      	</html:select> </td>
  </tr>
 <tr class="tableback1"> 
    <td width="97"> 角色选择 ：</td>
    <td colspan="5"><html:text property="roleName" readonly="true" styleClass="form" size="56"/> <a href="javascript:f_role()">[选择]</a> <font color="red">*</font></td>
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
        <input type="button" name="save" value="保存"  onclick="f_submit()">
        <input type="button"  value="关闭"  onclick="self.close();">
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
    
    if(f_isNull(document.forms[0].employeeCode,'用户ID')&&f_isNumChar2(document.forms[0].employeeCode,'用户ID')
    	&&f_isNull(document.forms[0].userName,'用户名称')&&verifyEmail(document.forms[0].email)
    	&&f_maxLength(document.forms[0].remark,'备注',50)){
 
	var roleName=document.forms[0].roleName.value;
	
	var uName=document.forms[0].userName.value;
	uName=uName.replaceAll(" ","");
	uName=uName.replaceAll("	","");
	document.forms[0].userName.value=uName;
		
	if(roleName==null||roleName==''){
		alert("请选择角色！");
	}else{
		retFlag =  true;
	}
    }
    return retFlag;
}

	
function f_role(){
	var userId=document.forms[0].id.value;
	var varUser=window.showModalDialog("roleAction.do?method=userRole&id="+userId,"","dialogHeight: 500px; dialogWidth: 600px; edge: Sunken; center: Yes; help: No; resizable: No; status: Yes;");

	if(varUser!=null){
		var retValue=varUser[0];
		var retName=varUser[1];
		document.forms[0].roleName.value=retName;
		document.forms[0].roleCode.value=retValue;
	}
}



function f_pw(){ 
	var varPassword=window.showModalDialog("support/passwordEdit.jsp?id="+document.forms[0].id.value,"","dialogHeight: 300px; dialogWidth: 420px; edge: Sunken; center: Yes; help: No; resizable: No; status: Yes;");
	if(varPassword!=undefined){ 
		alert("密码已重置");
		self.close();
	}

}


function f_username(obj){
	document.forms[0].userName.value=obj.options[obj.selectedIndex].text;
}

function f_filt(){
	var src = event.srcElement;
	var key = event.keyCode;

	//不能输入空格，tab  (tabspace = 9,space = 32)
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