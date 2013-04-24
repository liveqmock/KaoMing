<%@ page contentType="text/html; charset=GBK" %>

<html>
<head>
<link rel="stylesheet" href="<%= request.getContextPath()%>/css/style.css">
<title>upload文件</title>

</head>


<%
try{
	String strFlag=request.getAttribute("flag")==null?"":(String)request.getAttribute("flag");
	String strTag=request.getAttribute("tag")==null?"-1":(String)request.getAttribute("tag");
	String tempName=request.getAttribute("tempName")==null?"-1":(String)request.getAttribute("tempName");
	
	int tag=Integer.parseInt(strTag);
	    
%>
<BODY >

<H1>文件上传</H1>
<HR>

<table cellspacing="0" cellpadding="0">
  <tr> 
    <td width="12%"></td>
    <td width="88%">
    <br>
      <%if(tag==1){%>
		<%if("0".equals(strFlag)){%>
      	<form action="stockInfoListAction.do?method=stockInitialParse" method="post">
     	<%}else if("1".equals(strFlag)){%>
      	<form action="stockInfoListAction.do?method=partInitialParse" method="post">
      <%}else if("2".equals(strFlag)){%>
      	<form action="stockInfoListAction.do?method=stockFlowInitialParse" method="post">
     	<%}%>
      	<input type="hidden" name="filePath" value="<%=tempName%>">
      	
      </form>
      <div id="info-content">
      <SCRIPT>
      	document.forms[0].submit();
      </SCRIPT>
      <br>
      </div>
      <%}else{%>
      <div id="info-content">
        <%if(tag==1015){%>
	此类文件上传被拒绝，请与管理员联系！<BR>
	<%}else if(tag==1010){%>
	此类文件不能上传，请与管理员联系！<BR>
	<%}else if(tag==1105){%>
	您上传的文件:单个文件超过最大尺寸限制(10M)！<BR>
	<%}else if(tag==1110){%>
	您上传的文件超过最大尺寸限制！<BR>
	<%}else{%>
     	 文件上传失败，请与系统管理员联系<BR>
     	 <%}%>
      </div>
      <%}%>
      </td>
  </tr>
</table>


</BODY>
<%}catch(Exception e){
	e.printStackTrace();
}%>
</HTML>
