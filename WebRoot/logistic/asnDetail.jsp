<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri=  "/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri=  "/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ page import="java.util.ArrayList"%> 

<html>
<head>
<title>stockDetail</title>
<link href="css/styles.css" rel="stylesheet" type="text/css" />

</head>

<%
	try{
		ArrayList detailList = (ArrayList)request.getAttribute("detailList");
		int count=0;

		if(detailList!=null)	count=detailList.size();


%>
<body bgcolor="#ffffff" leftmargin="0" topmargin="0" rightmargin="0" bottommargin="0" marginwidth="0" marginheight="0" >
<html:form action="asnListAction.do?method=asnDetail" method="post" >

<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="6" class="content12">
	<tr>
		<td valign="top">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr> 
					<td class="content12"><strong>出货列表&gt;明细</strong></td>
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
									<td width="12%">出货单号：</td>
	                <td width="23%"><bean:write property="asnNo" name="asnForm" /></td>
	                <td width="10%">客户名称：</td>
	                <td width="15%"><bean:write property="customerName" name="asnForm" /></td>
	                <td width="10%">出货备注：</td>
	                <td width="20%"><bean:write property="shippingRemark" name="asnForm" /></td>
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
                <td><strong>销售单号</strong></td>
								<td><strong>料号</strong></td>
						    <td><strong>零件名称</strong></td>
						    <td><strong>机型</strong></td>
						    <td><strong>机身编码</strong></td>
						    <td><strong>数量</strong></td>
						    <td><strong>成本（单）</strong></td>
						    <td><strong>销售价（单）</strong></td>
						    <td><strong>利润（单）</strong></td>
						    <td><strong>状态</strong></td>
            </tr>
         
      <%
       if(detailList!=null){
       	    String strTr="";
      	    for(int i=0;i<detailList.size();i++){
      	        if(i%2==0) strTr="tableback2";
      	        else strTr="tableback1";
      		String[] temp=(String[])detailList.get(i);
      %>      
      <tr class="<%=strTr%>"> 
          <td ><%=temp[0]==null?"":temp[0]%></td>
          <td ><%=temp[1]==null?"":temp[1]%></td> 
          <td ><%=temp[2]==null?"":temp[2]%></td>
          <td ><%=temp[3]==null?"":temp[3]%></td>
          <td ><%=temp[4]==null?"":temp[4]%></td>
          <td ><%=temp[5]==null?"":temp[5]%></td> 
          <td ><%=temp[6]==null?"":temp[6]%></td> 
          <td ><%=temp[7]==null?"":temp[7]%></td> 
          <td ><%=temp[8]==null?"":temp[8]%></td> 
          <td ><%=temp[9]==null?"":temp[9]%></td> 
          
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
</html:form>


</body>
</html>
<%
}catch(Exception e){
	e.printStackTrace();
}%>