<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri=  "/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri=  "/WEB-INF/tlds/common.tld" prefix="comtld" %>
<%@ page import="java.util.ArrayList"%> 
<%@ page import="java.util.List"%> 
<%@ page import="com.dne.sie.common.tools.DicInit"%> 
<%@ page import="com.dne.sie.reception.bo.SaleInfoBo"%> 

<HTML>
<HEAD>
<title>订购单查询</title>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/styles2.css" rel="stylesheet" type="text/css" />
<script language=javascript src="js/common.js"></script>

</HEAD>
<%
	try{
		ArrayList saleList = (ArrayList)request.getAttribute("saleList");
		int count=0;
		if(saleList!=null){
			count=Integer.parseInt((String)saleList.get(0));
		}
		ArrayList saleStatusList = (ArrayList)DicInit.SYS_CODE_MAP.get("SALE_STATUS");
		ArrayList payStatusList = (ArrayList)DicInit.SYS_CODE_MAP.get("PAY_STATUS");
		ArrayList billingStatusList = (ArrayList)DicInit.SYS_CODE_MAP.get("BILLING_STATUS");
		//ArrayList invoiceTypeList = (ArrayList)DicInit.SYS_CODE_MAP.get("INVOICE_TYPE");
		
		
		List monthList=SaleInfoBo.getInstance().getSaleDateList();
		List createByList=SaleInfoBo.getInstance().getCreateByList();

%>
<body onload="setTitle('客户订购查询')">

<html:form action="saleInfoAction.do?method=saleInfoList" method="post" >

 <table width="100%" border="0" cellpadding="0" cellspacing="1" class="content12">  
   
                <tr class="tableback2"> 
                  <td width="13%"> 询价单号：</td>
                  <td ><html:text property="saleNo"  styleClass="form" size="16" /></td>
                   
                  <td width="13%">客户名称：</td>
                  <td width="17%"><html:text property="customerName"  styleClass="form" size="16" /></td>
                  <td width="98"> 客户电话：</td>
                  <td width="126"><html:text property="customerPhone"  styleClass="form" size="16" /></td>
                </tr>
               
               <tr class="tableback1"> 
                  <td width="13%"> 维修单号：</td>
                  <td ><html:text property="serviceSheetNo"  styleClass="form" size="16" /></td>
                  <td > 机型：</td>
                  <td ><html:text property="modelCode"  styleClass="form" size="16" /></td>
                  <td > 机身编码：</td>
                  <td ><html:text property="modelSerialNo"  styleClass="form" size="16" /></td>
                </tr>
                
                <tr class="tableback2"> 
                  <td > 料号：</td>
                  <td ><html:text property="stuffNo"  styleClass="form" size="16" /></td>
                  <td >零件名称：</td>
                  <td ><html:text property="skuCode"  styleClass="form" size="16" /></td>
                  
                </tr>
      
				     <tr class="tableback1"> 
				     	 <td width="80">申请单状态：</td>
		     		   <td width="120"><html:select styleClass="form" property="saleStatus"  >	
									<html:option  value="">全部</html:option>
									<%for(int i=0;saleStatusList!=null&&i<saleStatusList.size();i++){
                  	String[] temp=(String[])saleStatusList.get(i);
                	%>
                		<html:option value="<%=temp[0]%>" ><%=temp[1]%></html:option>
                 	<%}%>
      						</html:select>
     		   		</td>
     		   		<td > 付款状态：</td>
                  <td ><html:select styleClass="form" property="payStatus"  >	
									<html:option  value="">全部</html:option>
									<%for(int i=0;payStatusList!=null&&i<payStatusList.size();i++){
                  	String[] temp=(String[])payStatusList.get(i);
                	%>
                		<html:option value="<%=temp[0]%>"><%=temp[1]%></html:option>
                 	<%}%>
      						</html:select>
     		   		</td>
     		   		<td > 票据状态：</td>
                  <td ><html:select styleClass="form" property="billingStatus"  >	
									<html:option  value="">全部</html:option>
									<%for(int i=0;billingStatusList!=null&&i<billingStatusList.size();i++){
                  	String[] temp=(String[])billingStatusList.get(i);
                	%>
                		<html:option value="<%=temp[0]%>"><%=temp[1]%></html:option>
                 	<%}%>
      						</html:select>
     		   		</td>
          	</tr>
                
     				 <tr class="tableback2"> 
                <td >发票号码：</td>
                <td width="160"><html:text property="invoiceNo1"  styleClass="form" size="9" style="text-transform:uppercase" /> - 
                <html:text property="invoiceNo2"  styleClass="form" size="9" style="text-transform:uppercase" onblur="this.value=this.value.toUpperCase()"/></td>
                 
                
                 <td>经办人：</td>
                <td ><html:select styleClass="form" property="createBy"  >
	     		           <html:option value="">全部</html:option>
	     		           <%for(int i=0;createByList!=null&&i<createByList.size();i++){
	                  	Object[] temp=(Object[])createByList.get(i);
	                	%>
	                		<html:option value="<%=temp[0].toString()%>"><%=temp[1]%></html:option>
	                 	<%}%>
	     		           </html:select></td>
	     		           
	     		           
                  <td> 订单月份：</td>
                  <td ><html:select styleClass="form" property="orderMonth"  >
	     		           <html:option value="">全部</html:option>
	     		           <%for(int i=0;monthList!=null&&i<monthList.size();i++){
	                  	String temp=(String)monthList.get(i);
	                	%>
	                		<html:option value="<%=temp%>"><%=temp%></html:option>
	                 	<%}%>
	     		           </html:select></td>
              </tr>

                </table>
	<h3 class="underline"><p align="left">&nbsp;&nbsp;<input type="submit" value="查 询"></p></h3>
<br>
  <table width="100%" border="0" cellpadding="0" cellspacing="1" class="content12">
      <tr bgcolor="#CCCCCC"> 
        <td><strong>询价单号</strong></td>
  			<td><strong>客户名称</strong></td>
  			<td><strong>客户电话</strong></td>
  			<td><strong>交货地点</strong></td>
  			<td><strong>维修单号</strong></td>
  			<td><strong>维修类型</strong></td>
  			<td><strong>询价时间</strong></td>
  			<td><strong>经办人</strong></td>
  			<td><strong>零件报价</strong></td>
	        <td><strong>维修报价</strong></td>
	        <td><strong>零件状态</strong></td>
	        <td><strong>付款状态</strong></td>
	        <td><strong>票据状态</strong></td>
                
      </tr>

	<%
       if(saleList!=null){
       	    String strTr="";
      	    for(int i=1;i<saleList.size();i++){
      	        if(i%2==0) strTr="tableback2";
      	        else strTr="tableback1";
      		String[] temp=(String[])saleList.get(i);
      %>      
      <tr class="<%=strTr%>"> 
		  		<td style="cursor: hand"><A href="javascript:view('<%=temp[0]%>')"><%=temp[0]%></A></td>
          <td ><%=temp[1]==null?"":temp[1]%></td>
          <td ><%=temp[2]==null?"":temp[2]%></td>
          <td ><%=temp[10]==null?"":temp[10]%></td>
          <td ><%=temp[12]==null?"":temp[12]%></td>
           <td ><%=temp[13]==null?"":temp[13]%></td>
          <td ><%=temp[3]==null?"":temp[3]%></td>
          <td ><%=temp[11]==null?"":temp[11]%></td>
          <td >&nbsp;<%=temp[15]==null?"":temp[15]%></td>
          <td >&nbsp;<%=temp[16]==null?"":temp[16]%></td>
          <td ><%=temp[7]==null?"":temp[7]%></td>
          <td ><%=temp[8]==null?"":temp[8]%></td>
          <td ><%=temp[9]==null?"":temp[9]%></td>
        </tr>      
      <%}}else{%> 
	<tr class="tableback1"></tr>
      <%}%>
      
    <tr> 
      <td height="1" bgcolor="#677789" colspan="13"></td>
    </tr>
    <comtld:pageControl numOfRcds="<%=count%>">
	</comtld:pageControl>
    </table>
    
</html:form>
<script language="JavaScript">

	function view(id){
			document.forms[0].saleNo.value=id;
			document.forms[0].action="saleInfoAction.do?method=saleInfoDetail";
			document.forms[0].submit();
	}

	
</script>
</BODY>
<%
}catch(Exception e){
	e.printStackTrace();
}%>
</HTML>

