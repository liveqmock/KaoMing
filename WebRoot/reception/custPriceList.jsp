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
		ArrayList custPriceList = (ArrayList)request.getAttribute("custPriceList");
		int count=0;
		String custName="";
		if(custPriceList!=null&&custPriceList.size()>0){
			count=custPriceList.size();
			String[] temp = (String[])custPriceList.get(0);
			custName=temp[0];
		}


%>
<body  >
  <table width="98%" border="0" align="center" cellpadding="2" cellspacing="1" class="content12">
  <tr>
    <td>�������&gt;ѯ�۵���ϸ&gt;�ͻ�����</td>
  </tr>
</table>
<br>
<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" class="content12">
  <tr> 
    <td ><p>�ͻ����ƣ� <%=custName%> </p></td>
  </tr>
</table>
<br>

<table width="98%" border="0" align="center" cellpadding="0" cellspacing="1" class="content12">


  <tr bgcolor="#CCCCCC"> 
       <td><strong>�Ϻ�</strong></td>
       <td><strong>�������</strong></td>
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
       if(custPriceList!=null){
       	    String strTr="";
      	    for(int i=0;i<custPriceList.size();i++){
      	        if(i%2==0) strTr="tableback2";
      	        else strTr="tableback1";
      		String[] temp=(String[])custPriceList.get(i);
      %>      
      	<tr class="<%=strTr%>"> 
          <%for(int j=1;j<temp.length;j++){%>
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
