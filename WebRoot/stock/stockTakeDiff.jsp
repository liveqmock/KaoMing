<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri=  "/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ page import="java.util.ArrayList"%> 
<%@ page import="com.dne.sie.common.tools.CommonSearch"%> 
 
<html>
<head>
<title>stockOutOperate_list</title>
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/styles2.css">

</head>
<%
    ArrayList stockInfoList = (ArrayList)request.getAttribute("diffList");
    String flowId = (String)request.getAttribute("flowId");
    String flowUser = (String)request.getAttribute("flowUser");
    String flowTime = (String)request.getAttribute("flowTime");
    String flag = (String)request.getAttribute("flag");
    String strTitle="�����̵�";
    if("1".equals(flag)) strTitle="һ���̵�";
    
    CommonSearch cs = CommonSearch.getInstance();
    if(flowUser!=null) flowUser=cs.findUserNameByUserId(new Long(flowUser));
%>

<body bgcolor="#ffffff" leftmargin="0" topmargin="0" rightmargin="0" bottommargin="0" marginwidth="0" marginheight="0" >
<input type="hidden" name="stockTakeId" value="<%=flowId%>" >
 <html:form action="stockTakeAction.do?method=takeSave" method="post" >

  <table width="90%" border="0" align="center" cellpadding="2" cellspacing="1" class="content12">
  <tr>
    <td>������&gt;<%=strTitle%>&gt;���������б�</td>
  </tr>
</table>
<br>
<table width="90%" border="0" align="center" cellpadding="0" cellspacing="0" class="content12">
  <tr> 
    <td ><p>�̵���ˮ�ţ� <%=flowId%>&nbsp;&nbsp;&nbsp;&nbsp;�̵��ˣ� <%=flowUser%>&nbsp;&nbsp;&nbsp;&nbsp;�̵㿪ʼ���ڣ� <%=flowTime%> </p></td>
  </tr>
  <tr><font color="blue">����������<%=stockInfoList.size()%></font></tr>
</table>
<br>
<%if(flag.equals("out")){%>
<tr>
<p><font color="red">�����̵�����������1000�����������̵�</font></p>
</tr>
<%}else{%>
<table width="90%" border="0" align="center" cellpadding="0" cellspacing="1" class="content12">

<%if(stockInfoList!=null&&stockInfoList.size()>0){%>
  <tr bgcolor="#CCCCCC"> 
    <td><strong> ����Ϻ�</strong></td>
    <td><strong> ������</strong></td>
    <td><strong> ��λ��</strong></td>
    <td><strong>�������</strong></td>
    <td><strong>�̵�����</strong></td>
  </tr>
         
       <%
       	    String strTr="";
      	    for(int i=0;i<stockInfoList.size();i++){
      	        if(i%2==0) strTr="tableback2";
      	        else strTr="tableback1";
      		String[] temp=(String[])stockInfoList.get(i);
      		
      %>
      
      <tr class="<%=strTr%>"> 
          <td ><%=temp[2]==null?"":temp[2]%></td>
       	  <td ><%=temp[3]==null?"":temp[3]%></td>
       	  <td ><%=temp[1]==null?"":temp[1]%></td>
       	  <td ><%=temp[4]==null?"":temp[4]%></td>
       	  <td ><%=temp[5]==null?"":temp[5]%></td>
        </tr>
      
      <%}}else{%>
      	  <tr> 
    		<td ><p>û�в�������</p></td>
  	</tr>
      
      <%}%> 
      <tr> 
    <td height="2" colspan="9" bgcolor="#ffffff"></td>
  </tr>
  <tr> 
    <td height="1" colspan="9"  bgcolor="#677789"></td>
  </tr>
</table>
<table width="90%" border="0" align="center" cellpadding="2" cellspacing="1" class="content12">
  <tr> 
    <td><div align="right"> 
    <%if(stockInfoList!=null&&stockInfoList.size()>0){%>
        <input type="button" class="button6" value="��ӡ�����" onclick="f_print()">
        <%}%>
        <input type="button" class="button2" onclick="self.close();" value="�ر�">
      </div></td>
  </tr>
</table>
<%}%>
</body>
</html:form>
</html>
<!-- InstanceEnd --></html>
 
<SCRIPT language=JAVASCRIPT1.2>


function f_print(){
    document.forms[0].action="stockTakeAction.do?method=diffExcelCreate";
    document.forms[0].submit();
}


</SCRIPT>