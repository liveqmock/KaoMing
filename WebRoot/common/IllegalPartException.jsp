<%@ page contentType="text/html; charset=GBK" %>
<HTML><HEAD><TITLE>���ݰ汾�쳣</TITLE>
<META http-equiv=Content-Type content="text/html; charset=gb2312">
<STYLE type=text/css>BODY {
	FONT-WEIGHT: bold; FONT-SIZE: 14px; BACKGROUND: #ffe; MARGIN: 50px; COLOR: #121c46; FONT-FAMILY: Verdana, Tahoma, Arial, sans-serif; TEXT-ALIGN: left
}
</STYLE>

<%
		String errStuffNo = (String)request.getAttribute("errStuffNo");
%>
<META content="MSHTML 6.00.2900.2722" name=GENERATOR></HEAD>
<BODY >&gt; �ôβ������Ϻ���ϵͳ��δ���� <%if(errStuffNo!=null){%>�� <%=errStuffNo%> <%}%> <BR>
</BODY></HTML>

