<%@ page contentType="text/html; charset=GBK" %>

<html>
<head>
<link rel="stylesheet" href="<%= request.getContextPath()%>/css/style.css">
<title>upload�ļ�</title>

</head>


<%
try{
	String strFlag=request.getAttribute("flag")==null?"":(String)request.getAttribute("flag");
	String strTag=request.getAttribute("tag")==null?"-1":(String)request.getAttribute("tag");
	String tempName=request.getAttribute("tempName")==null?"-1":(String)request.getAttribute("tempName");
	
	int tag=Integer.parseInt(strTag);
	    
%>
<BODY >

<H1>�ļ��ϴ�</H1>
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
	�����ļ��ϴ����ܾ����������Ա��ϵ��<BR>
	<%}else if(tag==1010){%>
	�����ļ������ϴ����������Ա��ϵ��<BR>
	<%}else if(tag==1105){%>
	���ϴ����ļ�:�����ļ��������ߴ�����(10M)��<BR>
	<%}else if(tag==1110){%>
	���ϴ����ļ��������ߴ����ƣ�<BR>
	<%}else{%>
     	 �ļ��ϴ�ʧ�ܣ�����ϵͳ����Ա��ϵ<BR>
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
