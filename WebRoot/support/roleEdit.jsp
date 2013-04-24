<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ page import="java.util.ArrayList"%> 

<html>
<head>
<title>KM</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/styles.css" rel="stylesheet" type="text/css" />
<script language=javascript src="js/checkValid.js"></script>
<script language=javascript src="js/common.js"></script>

</head>
<%

  Long userId=(Long)session.getAttribute("userId");
	String strFlag=(String)request.getAttribute("state")==null?"":(String)request.getAttribute("state");
	String strUserName=(String)request.getAttribute("strUserName")==null?"":(String)request.getAttribute("strUserName");
	
	try{
%>

<body>
<html:form method="post" action="roleAction.do?method=roleAdd">
<html:hidden property="id"/>
<html:hidden property="delFlag"/>
<br>
<table width="90%" border="0" align="center" cellpadding="2" cellspacing="1" class="content12">
  <tr>
    <td>管理&gt;用户权限管理&gt;角色配置</td>
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
  <tr class="tableback1"> 
    <td width="20%"> 角色名称 ：</td>
    <td > <html:text property="roleName" styleClass="form" size="32" maxlength="25"/><font color="red">*</font> </td>
  </tr>
  
  <tr class="tableback2"> 
    <td width="20%"> 备注 ：</td>
    <td colspan="4"> <html:text property="remark" styleClass="form" size="56"/> </td>
  </tr>
  
   <%if(strFlag.equals("detail")&&!strUserName.equals("")){%>
  <tr class="tableback1"> 
    <td width="20%"> 角色成员 ：</td>
    <td > <html:text property="userName" styleClass="form" readonly="true" size="56"/><a href="javascript:f_userMove()">[用户修改]</a> </td>
  </tr>
  <%}%>
  
  
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

<SCRIPT language=JAVASCRIPT1.2>

function f_submit(){
    <%if(strFlag.equals("detail")){%>
	document.forms[0].action="roleAction.do?method=roleModify";
    <%}%>

    if(f_check()){
    	document.forms[0].submit();
    }
    
}

function f_check(){
    var retFlag=false;
    
    if(f_isNull(document.forms[0].roleName,'角色名称')&&f_maxLength(document.forms[0].remark,'备注',256)){

			retFlag =  true;
    }
    return retFlag;
}


function f_userMove(){
	
	child=middleOpen("userAction.do?method=userSelect&flag=role&id="+document.forms[0].id.value,"","width=650,height=300,left=50,top=10,menubar=no,toolbar=no,resizable=yes,scrolling=yes,scrollbars=yes");

}


</SCRIPT>
<%
} catch(Exception e) {
			e.printStackTrace();
		}
 %>