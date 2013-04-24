<%@ page contentType="text/html; charset=utf-8" %>
<HTML><HEAD><TITLE>errorMessage</TITLE>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
<STYLE type=text/css>BODY {
	FONT-WEIGHT: bold; FONT-SIZE: 14px; BACKGROUND: #ffe; MARGIN: 50px; COLOR: #121c46; FONT-FAMILY: Verdana, Tahoma, Arial, sans-serif; TEXT-ALIGN: left
}
</STYLE>

<%
	String errorMsg=(String)request.getAttribute("errorMsg");
	String mess=(String)request.getAttribute("mess");
%>

<META content="MSHTML 6.00.2900.2722" name=GENERATOR></HEAD>
<BODY >&gt;.操作失败！<BR>
<%if(mess!=null){ %>&gt;.<%=mess %><BR><%} %>
<!--  
<%=errorMsg %>

-->
</BODY></HTML>