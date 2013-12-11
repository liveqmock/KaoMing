<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri=  "/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ page import="java.util.ArrayList"%>


<html>
<head>
<title>cipErr</title><SCRIPT language="javascript" src="js/screen.js"></SCRIPT>
<link rel="stylesheet" href="<%= request.getContextPath()%>/css/style.css">
<link rel="stylesheet" href="<%= request.getContextPath()%>/css/styles2.css">

</head>
<%
	
    ArrayList dataList = (ArrayList)request.getAttribute("dataList");
    int tag=((Integer)dataList.get(0)).intValue();
   
    
%>
<body bgcolor="#ffffff" leftmargin="0" topmargin="0" rightmargin="0" bottommargin="0" marginwidth="0" marginheight="0" >
<form>
<table width="100%" height="100%" border="0"  cellpadding="10" cellspacing="10" bgcolor="#B6C4E3">
  <tr >
    <td align="center" bgcolor="#FFFFFF" >
      <br>
      <table width="90%" border="0" cellspacing="2" cellpadding="4" class="content12">
      <%if(tag>=0){%>
      <tr> 
          <p><strong>操作成功，初始化数据 <%=tag%> 条</strong></p>
        </tr>
      <%}else if(tag==-1){%>
      <tr> 
          <p><strong>操作失败!</strong></p>
        </tr>
      <%}else if(tag==-3){
      	 	String[] dataErr=(String[])dataList.get(1);
      
      %>
        <tr> 
          <p><strong>Excel文件中的非法数据所在行数</strong></p>
        </tr>
        
         <%if(dataErr[0]!=null){%>
        <tr> <td style="word-break:break-all">
          <p>进价:<%out.println(dataErr[0]);%></p>
        </td></tr>
        <%}%>
		   <%if(dataErr[1]!=null){%>
        <tr> <td style="word-break:break-all">
          <p>报价:<%out.println(dataErr[1]);%></p>
        </td></tr>
        <%}%>
        <%}else if(tag==-2){
        	String[] dataNull=(String[])dataList.get(1);
        %>
        <tr> 
          <p><strong>Excel文件中的空数据所在行数</strong></p>
        </tr>
        
         <%if(dataNull[0]!=null){%>
        <tr> <td style="word-break:break-all">
          <p>料号:<%out.println(dataNull[0]);%></p>
        </td></tr>
        <%}%>
        <%if(dataNull[1]!=null){%>
        <tr> 
          <p>零件名称:<%out.println(dataNull[1]);%></p>
        </tr>
        <%}%>
         <%if(dataNull[2]!=null){%>
        <tr> <td style="word-break:break-all">
          <p>简称:<%out.println(dataNull[2]);%></p>
        </td></tr>
        <%}%>
        
        <%}%>
        <tr> 
          <td height="1" background="images/i_line.gif"></td>
        </tr>
      </table>
       </td>
  </tr>
</table>
</form>
</body>
</html>
