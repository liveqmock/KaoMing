<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="java.util.ArrayList"%> 

<html>
<head>
<title>stockDetail</title>
<link href="css/styles.css" rel="stylesheet" type="text/css" />

</head>

<%
	try{
		ArrayList stockInfoList = (ArrayList)request.getAttribute("skuViewList");
		int count=Integer.parseInt((String)stockInfoList.get(0));

		ArrayList skuInfoList = (ArrayList)request.getAttribute("skuInfoList");
		String[] skuInfos=(String[])skuInfoList.get(0);


%>
<body bgcolor="#ffffff" leftmargin="0" topmargin="0" rightmargin="0" bottommargin="0" marginwidth="0" marginheight="0" >
<form name="stockInfoForm" method="post" >

<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="6" class="content12">
	<tr>
		<td valign="top">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr> 
					<td class="content12"><strong>库存信息清单&gt;明细</strong></td>
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
                <tr>
                	<td width="10%">料号：</td>
	                <td width="20%"><%=skuInfos[2]%></td>
					<td width="12%">零件名称：</td>
	                <td width="23%"><%=skuInfos[0]%></td>
	                <td width="10%">简称：</td>
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
	 				<tr><font color="blue">记录条数： <%=count%></font></tr>
            <tr bgcolor="#CCCCCC">
			<td ><strong>零件数量</strong></td>
			<td ><strong>单位</strong></td>
			<td><strong>成本￥</strong></td>
            <td ><strong>规格</strong></td>
			<td ><strong>库存状态</strong></td>
			<td ><strong>备注</strong></td>
			<td ><strong>销售单号</strong></td>
			<td ><strong>申请客户</strong></td>
			<td ><strong>发票号</strong></td>
            </tr>
         
              <%
       if(stockInfoList!=null){
       	    String strTr="";
      	    for(int i=1;i<stockInfoList.size();i++){
      	        if(i%2==0) strTr="tableback2";
      	        else strTr="tableback1";
      		String[] temp=(String[])stockInfoList.get(i);
      %>      
      <tr class="<%=strTr%>"> 
          <td ><%=temp[16]%></td>
          <td ><%=temp[5]==null?"":temp[5]%></td> 
          <td ><%=temp[17]%></td> 
          <td ><%=temp[7]==null?"":temp[7]%></td> 
          <td ><%=temp[9]==null?"":temp[9]%></td> 
          <td ><%=temp[10]==null?"":temp[10]%></td> 
          <td ><%=temp[12]==null?"":temp[12]%></td>
          <td ><%=temp[13]==null?"":temp[13]%></td>
          <td ><%=temp[15]==null?"":temp[15]%></td>
        </tr>      
      <%}}%> 
      
            

              <tr>
                <td colspan="11" height="2" bgcolor="#ffffff"></td>
              </tr>
              <tr>
                <td colspan="11" height="1" bgcolor="#677789"></td>
              </tr>

  </TABLE>  
			  
                  <tr>
                    <td class="content_yellow">　</td>
                  </tr>
       </table>
</form>


</body>
</html>
<%
}catch(Exception e){
	e.printStackTrace();
}%>