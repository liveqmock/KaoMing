<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="java.util.ArrayList"%> 

<html>
<head>
<title>历史报价</title>
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
    <td>零件销售&gt;询价单明细&gt;零件历史报价</td>
  </tr>
</table>
<br>
<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" class="content12">
  <tr> 
    <td ><p>料号： <%=stuffNo%> &nbsp;&nbsp;&nbsp;&nbsp;零件名称： <%=skuCode%></p></td>
  </tr>
</table>
<br>

<table width="98%" border="0" align="center" cellpadding="0" cellspacing="1" class="content12">


  <tr bgcolor="#CCCCCC"> 
       <td><strong>客户名称</strong></td>
       <td><strong>机型</strong></td>
       <td><strong>机身编码</strong></td>
       <td><strong>数量</strong></td>
       <td><strong>台湾报价￥(单)</strong></td>
       <td><strong>销售单价</strong></td>
       <td><strong>利润单价</strong></td>
       <td><strong>经办人</strong></td>
       <td><strong>询价时间</strong></td>
      <td><strong>销售单</strong></td>
      <td><strong>类型</strong></td>
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
