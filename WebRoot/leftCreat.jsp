<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="com.dne.sie.support.userRole.bo.FunctionBo"%>

<%try{%>
<html>
<head>
<title>kaoming</title>
<link href="css/style.css" rel="stylesheet" type="text/css">
<script language=javascript src="js/common.js"></script>
<SCRIPT LANGUAGE="JavaScript">

function hrefContent(){
	<%
		String doRefresh = request.getParameter("doRefresh");
		if("off".equals(doRefresh)){
			// do nothing
		}else{
	%>
			var firstHrefLinkObj = getChildByTagNameTimes(rightDIV,"A",1);
			if(firstHrefLinkObj == null){
				alert("您没有查看此模块的权限！");
			}else{			
				window.parent.content.location = firstHrefLinkObj;
			}
	<%
		}
	%>
}



</SCRIPT>
</head>

<%

	Long userId=(Long)session.getAttribute("userId");
	String typeId=(String)request.getAttribute("typeId")==null?"1":(String)request.getAttribute("typeId");
	String roleFlag="page"+userId;
	
	String[][] htmlTree=(String[][])session.getAttribute(roleFlag);
	
	String strHtml="";
%>

<body bgcolor="#ffffff" background="images/nav_back.gif" leftmargin="0" topmargin="0" rightmargin="0" bottommargin="0" marginwidth="0" marginheight="0" onload="hrefContent()">
<div align="right" id="rightDIV"> 


  <table width="92%" border="0" cellpadding="2" cellspacing="1" class="content12">
    <tr> 
      <td>&nbsp;</td>
    </tr>
    
    <%  
    if(htmlTree==null){
    	FunctionBo fbo=FunctionBo.getInstance();
    	htmlTree=fbo.modelBuild(userId);
    	session.setAttribute(roleFlag,htmlTree);
    	 
    }
    
    int intType=Integer.parseInt(typeId)-1;
    strHtml=htmlTree[0][intType];

    	if(strHtml!=null&&!strHtml.equals("")){
    		out.println(strHtml);
    	}else{
    		out.println("<br>您没有该模块权限");
    	}
    %>
    
    
  </table>
    
  <br>
  <table width="92%" border="0" cellpadding="2" cellspacing="1" class="content12">
  	<tr> 
      <td width=30></td>
        <td> <a href="common/roleFalse.jsp" ></a></td>
    </tr>
    <tr> 
      <td width=30><img src="googleImg/icon/aim.gif"   align="absmiddle"></td>
        <td> <a href="login.jsp"  target="_parent"><font color="#FF6600"> 退出登录 </font></a></td>
    </tr>
    <tr> 
      <td background="images/i_line.gif" height="1" colspan="2"></td>
    </tr>

  </table>
</div>
      <p>&nbsp;</p>
</body>
</html>

<%}catch(Exception e){e.printStackTrace();}%>