<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="java.util.ArrayList"%> 

<html>
<head>
<title>stockDetail</title>
<link href="css/styles.css" rel="stylesheet" type="text/css" />

</head>

<body bgcolor="#ffffff" leftmargin="0" topmargin="0" rightmargin="0" bottommargin="0" marginwidth="0" marginheight="0" >
<%
	try{
		ArrayList stockFlowList = (ArrayList)request.getAttribute("skuFlowList");

		if(stockFlowList!=null&&stockFlowList.size()>1){
			int count=Integer.parseInt((String)stockFlowList.get(0));
		
			ArrayList skuInfoList = (ArrayList)request.getAttribute("skuInfoList");
			String[] skuInfos=(String[])skuInfoList.get(0);


%>

<form name="stockInfoForm" method="post" >

<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="6" class="content12">
	<tr>
		<td valign="top">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr> 
					<td class="content12"><strong>�����Ϣ�嵥&gt;��ϸ</strong></td>
				</tr>
			</table>		
			<br>

		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="content12">
                <tr> 
                  <td height="2" colspan="8" bgcolor="#677789"></td>
                </tr>
                <tr> 
                  <td height="6"  colspan="8" bgcolor="#CECECE"></td>
                </tr>
                	<td width="10%">�Ϻţ�</td>
	                <td width="20%"><%=skuInfos[2]%></td>
					<td width="12%">������ƣ�</td>
	                <td width="23%"><%=skuInfos[0]%></td>
	                <td width="10%">���ͣ�</td>
	                <td width="15%"><%=skuInfos[1]==null?"":skuInfos[1]%></td>
	                
		</tr>
		
                <tr> 
                  <td height="2" colspan="8" bgcolor="#ffffff"></td>
                </tr>
                <tr> 
                  <td height="1" colspan="8" bgcolor="#677789"></td>
                </tr>
              </table> 

	<br>

         <table width="100%" border="0" cellpadding="0" cellspacing="1" class="content12">
	 				<tr><font color="blue">��¼������ <%=count%></font></tr>
	 				<tr bgcolor="#CCCCCC">
			<td ><strong>����</strong></td>
			<td ><strong>��λ</strong></td>
			<td><strong>�ɱ���</strong></td>
            <td><strong>����$</strong></td>
			<td ><strong>ժҪ</strong></td>
			<td ><strong>��������</strong></td>
			<td ><strong>��������</strong></td>
			<td ><strong>�������</strong></td>
            </tr>
         
              <%
       if(stockFlowList!=null){
       	    String strTr="";
      	    for(int i=stockFlowList.size()-1;i>0;i--){
      	        if(i%2==0) strTr="tableback2";
      	        else strTr="tableback1";
      		String[] temp=(String[])stockFlowList.get(i);
      %>      
      <tr class="<%=strTr%>"> 
          <td ><%=temp[1]==null?"":temp[1]%></td> 
          <td ><%=temp[2]==null?"":temp[2]%></td>
          <td ><%=temp[3]==null?"":temp[3]%></td> 
          <td ><%=temp[9]==null?"":temp[9]%></td> 
          <td ><%=temp[4]==null?"":temp[4]%></td>
          <td ><%=temp[5]==null?"":temp[5]%></td> 
          <td ><%=temp[6]==null?"":temp[6]%></td> 
          <td ><%=temp[7]==null?"":temp[7]%></td> 
        </tr>      
      <%}}%> 
      
              <tr>
                <td colspan="11" height="2" bgcolor="#ffffff"></td>
              </tr>
              <tr>
                <td colspan="11" height="1" bgcolor="#677789"></td>
              </tr>
	<tr><td><input name="mybutton" type="button" class="button2" value="�ر�" onClick="self.close();"  ></td></tr>
  </TABLE>  
			  
                  <tr>
                    <td class="content_yellow">��</td>
                  </tr>
       </table>
</form>



</html>
<%}else{%>

<table width="100%" height="100%" border="0" align="center" cellpadding="10" cellspacing="10" bgcolor="#B6C4E3">
  <tr >
    <td align="center" bgcolor="#FFFFFF" ><img src="googleImg/patent.gif"><br>
      <br>
      <table width="50%" border="0" cellspacing="2" cellpadding="4" class="content12">
        <tr> 
          <td height="1" background="googleImg/i_line.gif"></td>
        </tr>
		<tr> 
          <td class="tableback1" >�����û����ˮ��Ϣ</td>
        </tr>
        <tr> 
          <td height="1" background="googleImg/i_line.gif"></td>
        </tr>
      </table>
      <br>
      <input name="mybutton" type="button" class="button2" value="�ر�" onClick="self.close();" />
       </td>
  </tr>
</table>
<%}%>
</body>
<%
}catch(Exception e){
	e.printStackTrace();
}%>