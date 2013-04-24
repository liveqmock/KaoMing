<%@ page contentType="text/html; charset=GBK" %>

<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>Login</title>
<link href="css/style.css" rel="stylesheet" type="text/css">

<script language="JavaScript" type="text/JavaScript">
function submitLogin() {
	if(document.loginform.loginUser.value.length == 0){
  	alert("请输入用户名！");
    document.loginform.loginUser.focus();
		return;
	}
	if(document.loginform.passwd.value.length == 0){
  	alert("请输入密码！");
  	document.loginform.passwd.focus();
		return;
	}
	document.loginform.submit();
}

function init(){

		this.name = Math.random();
	
		if(window.parent !=null && window.parent.name != this.name){
			window.parent.location ="login.jsp";
		}else{
			document.forms[0].loginUser.focus();
		}
}




function myenterKey(){
	document.cookie = "login=true;";
  if (window.event.keyCode==13)
  {
  		submitLogin();		
  }
}
</script>
</head>
<%
		String strError=request.getAttribute("flag")==null?"":(String)request.getAttribute("flag");
		session.removeAttribute("employeeCode");
		for(int i=0;i<10;i++){
			session.removeAttribute("page"+i);
		}
		session.invalidate();
%>
<body background="images/login_back.gif" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onload="init()">
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" class="content12">
 
  <tr> 
    <td><div align="center">
        <table border="0" cellpadding="0" cellspacing="0" class="content12"> 
          <tr> 
            <td height="349"  background="images/login_pic.jpg" ><table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" class="content12">
                <tr>
                  <td width="28%" height="220"></td>
                  <td width="72%"></td>
                </tr>
                
                <tr>
                  <td></td>
                  <td valign="top"><table width="320" border="0" cellpadding="0" cellspacing="2" class="content12">
                   <form action="<%= request.getContextPath()%>/indexAction.do?method=loginConfirm" method="post" name="loginform">
                    <tr>
                    <%if("err".equals(strError)){%><font color="red">用户名或密码错误！</font><%}%>
                    </tr>
                      <tr>
                        <td width="44">用户名:</td>
                        <td width="270">
						<input name="loginUser" type="text" class="form" size="18" onkeydown="myenterKey()" tabindex="1"></td>
                      </tr>
                      <tr>
                        <td width="44">密&nbsp;码:</td>
                        <td>
						<input name="passwd" type="password" class="form" size="18" onkeydown="myenterKey()" tabindex="2"></td>
                      </tr>
                      <tr>
                        <td width="44"></td>
                        <td>
												<input name="Submit" type="BUTTON" onClick="submitLogin()" value="登 录">
												</td>
                      </tr>
                      </form>
                    </table></td>
                </tr>
                
              </table></td>
            <td  rowspan="2" valign="bottom"> </td>
          </tr>
          <tr> 
            <td width="566" height="14"  ></td>
          </tr>
        </table>
        <br>
        <br>
        <br>
      </div></td>
  </tr>
</table>
</body>
</html>