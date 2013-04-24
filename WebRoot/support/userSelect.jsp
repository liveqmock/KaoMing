<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri=  "/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri=  "/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ page import="java.util.ArrayList"%> 
 
<html>
<head>
<title>user_list</title><SCRIPT language="JScript.Encode" src="js/screen.js"></SCRIPT>
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/styles2.css">
<SCRIPT language="JScript.Encode" src="js/commonSelect.js"></SCRIPT>
<base target="_self">
</head>
<%
    ArrayList vtrData = (ArrayList)request.getAttribute("vtrData");
    int count=vtrData==null?0:vtrData.size();
    String flag = (String)request.getAttribute("flag");
    String deptRoleId = (String)request.getAttribute("deptRoleId");
    
%>

<body bgcolor="#ffffff" leftmargin="0" topmargin="0" rightmargin="0" bottommargin="0" marginwidth="0" marginheight="0" >

 <html:form action="userAction.do?method=userList" method="post" >
 <html:hidden property="id"/>
 <input type="hidden" name="deptRoleId" value="<%=deptRoleId%>">
  
        <table width="96%" height="90%" border="0" cellpadding="0" cellspacing="6" class="content12">
          <tr> 
            <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0" class="content12">
              <tr>
                <td height="2" bgcolor="#677789"></td>
              </tr>
              <tr>
                <td height="6" bgcolor="#CECECE"></td>
              </tr>
              <tr>
                <td valign="top"> <br>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td class="content12"><strong>用户列表（用户数： <%=count%>）</strong></td>
                      </tr>
                    </table>
             
                    <table width="100%" border="0" cellpadding="0" cellspacing="1" class="content12">
                      <tr bgcolor="#CCCCCC">
                        <td width="10"><input name="allbox" type="checkbox" onClick="checkAll(this);"></td>
                        <td><strong>ID</strong></td>
                        <td><strong>名称</strong></td>
                        <td><strong>用户权限</strong></td>
                      </tr>
         
       <%if(vtrData!=null){
       	    String strTr="";
      	    for(int i=0;i<vtrData.size();i++){
      	        if(i%2==0) strTr="tableback2";
      	        else strTr="tableback1";
      		String[] temp=(String[])vtrData.get(i);
      		
      %>
      
      <tr class="<%=strTr%>"> 
          <td align=center><input type="checkbox" name="chk" value="<%=temp[0]%>"></td>
          <A href="javascript:view('<%=temp[0]%>')"><td style="cursor: hand"><%=temp[1]%></td></A>
          <td ><%=temp[2]==null?"":temp[2]%></td>
          <td ><%=temp[3]==null?"":temp[3]%></td>
        </tr>
      
      <%}}%> 
        
     </table></td>
              </tr>
              <tr align="left">
                    <td class="content_yellow">
                      <input type="button" class="button4" onclick="f_del()" value='移除用户'>
                      <input type="button" class="button2" onclick="f_close()" value='关闭'>
                
                    </td>
                  </tr>
              <tr>
                <td height="2" bgcolor="#ffffff"></td>
              </tr>
              <tr>
                <td height="1" bgcolor="#677789"></td>
              </tr>
			  
                  <tr>
                    <td class="content_yellow">　</td>
                  </tr>
                </table></TD>
		</tr>
            </table></td>
  </tr>
</table>
</html:form>
</body>
<!-- InstanceEnd --></html>
 
<SCRIPT language=JAVASCRIPT1.2>
<!--

function f_del(){
if(confirm("是否确认移除?")){
    if(chk()!=''&&chk()!=null){
    	document.forms[0].action="userAction.do?method=userMove&flag=<%=flag%>";
    	document.forms[0].id.value=chk();
	document.forms[0].submit();
    }else{
	alert('请先选择记录');
    }
}
}

function f_close(){
	//window.opener.location.reload();
	self.close();
}

-->
</SCRIPT>