<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="java.util.ArrayList"%> 

<html>
<head>
<title>��ʷ����</title>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/styles.css" rel="stylesheet" type="text/css" />

</head>

<%
	try{
		ArrayList historyPriceList = (ArrayList)request.getAttribute("historyPriceList");
		int count=0;
		String stuffNo="",skuCode="";
		if(historyPriceList!=null&&historyPriceList.size()>0){
			count=historyPriceList.size();
			String[] temp = (String[])historyPriceList.get(0);
			stuffNo=temp[0];
			skuCode=temp[1];
		}


%>
<body  >
  <table width="98%" border="0" align="center" cellpadding="2" cellspacing="1" class="content12">
  <tr>
    <td>�������&gt;ѯ�۵���ϸ&gt;�����ʷ����</td>
  </tr>
</table>
<br>
<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" class="content12">
  <tr> 
    <td ><p>�Ϻţ� <%=stuffNo%> &nbsp;&nbsp;&nbsp;&nbsp;������ƣ� <%=skuCode%></p></td>
  </tr>
</table>
<br>

<table width="98%" border="0" align="center" cellpadding="0" cellspacing="1" class="content12">


  <tr bgcolor="#CCCCCC"> 
       <td><strong>�ͻ�����</strong></td>
       <td><strong>����</strong></td>
       <td><strong>�������</strong></td>
       <td><strong>����</strong></td>
       <td><strong>̨�屨�ۣ�(��)</strong></td>
       <td><strong>���۵���</strong></td>
       <td><strong>���󵥼�</strong></td>
       <td><strong>������</strong></td>
       <td><strong>ѯ��ʱ��</strong></td>
      <td><strong>���۵�</strong></td>
      <td><strong>����</strong></td>
  </tr>
         
       
      
    <%
       if(historyPriceList!=null){
       	    String strTr="";
      	    for(int i=0;i<historyPriceList.size();i++){
      	        if(i%2==0) strTr="tableback2";
      	        else strTr="tableback1";
      		String[] temp=(String[])historyPriceList.get(i);
      %>      
      	<tr class="<%=strTr%>"> 
          <%for(int j=2;j<temp.length;j++){%>
          <td ><%=temp[j]==null?"":temp[j]%></td>
          <%}%>
        </tr>      
      <%}}else{%> 
	<tr class="tableback1"></tr>
      <%}%>
      
      
       
       <tr> 
      <td height="1" bgcolor="#677789" colspan="11"></td>
    </tr>
        

     
</table>

</body>
<%
}catch(Exception e){
	e.printStackTrace();
}%>

</html>
