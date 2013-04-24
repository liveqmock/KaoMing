<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri=  "/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri=  "/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ page import="java.util.ArrayList"%> 

<HTML>
<HEAD>
<title>询价单明细</title>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/styles.css" rel="stylesheet" type="text/css" />

</head>
<%
		ArrayList vtrData = (ArrayList)request.getAttribute("detailList");
    
%>

<body  bgcolor="#ffffff" leftmargin="0" topmargin="0" rightmargin="0" bottommargin="0" marginwidth="0" marginheight="0">

<html:form method="post" action="saleInfoAction.do?method=inquiryDetail">
<html:hidden property="saleNo"/>
<table width="100%" border="0" align="center" cellpadding="2" cellspacing="1" class="content12">
  <tr>
    <td>零件销售&gt;询价单明细</td>
  </tr>
</table>
<br>
<table width="100%" border="0" align="center" cellpadding="2" cellspacing="1" class="content12">
  <tr> 
    <td height="2" colspan="9" bgcolor="#677789"></td>
  </tr>
  <tr> 
    <td height="6"  colspan="9" bgcolor="#CECECE"></td>
  </tr>
  <tr class="tableback2"> 
    <td width="7%"> 询价单号：</td>
    <td width="10%"><bean:write property="saleNo" name="salesInfoForm" /></td>
    <td width="7%"> 客户名称：</td>
    <td width="10%"><bean:write property="customerName" name="salesInfoForm" /></td>
   </tr>
   <tr class="tableback1"> 
    <td width="7%">发货地址：</td>
    <td width="15%"><bean:write property="shippingAddress" name="salesInfoForm" /></td>
    <td width="7%"> 备注：</td>
    <td width="15%"><bean:write property="remark" name="salesInfoForm" /></td>
  </tr>
  <tr class="tableback2"> 
  </tr>
  
  
  <tr> 
    <td height="2" colspan="9" bgcolor="#ffffff"></td>
  </tr>
  <tr> 
    <td height="1" colspan="9" bgcolor="#677789"></td>
  </tr>
</table>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td align="left">
    
    	<input type="button"  value="客户历史报价" onClick="javascript:cust_price('<bean:write property="customerId" name="salesInfoForm" />')">
    
    </td>
  </tr>
</table>
<br>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="content12">
  <tr bgcolor="#CCCCCC"> 
    
    <td align="center" ><strong>项次</strong></td>
    <td><strong>机型</strong></td>
    <td><strong>机身编码</strong></td>
    <td><strong>零件名称</strong></td>
    <td><strong>料号</strong></td>
    <td><strong>数量</strong></td>
    <td  align="center" ><strong>历史价</strong></td>
    <td  align="center" ><strong>附件</strong></td>
  </tr>
  
     <%if(vtrData!=null){
       	    String strTr="";
      	    for(int i=0;i<vtrData.size();i++){
      	        if(i%2==0) strTr="tableback2";
      	        else strTr="tableback1";
      		String[] temp=(String[])vtrData.get(i);
      		
      %>
      
      <tr class="<%=strTr%>"> 
          <td align=center><%=i+1%></td>
					<td ><%=temp[1]==null?"":temp[1]%></td>
          <td ><%=temp[2]==null?"":temp[2]%></td>
          <td ><%=temp[3]==null?"":temp[3]%></td>
          <td ><%=temp[4]==null?"":temp[4]%></td>
          <td ><%=temp[5]==null?"":temp[5]%></td>
          <td align="center"><a href="javascript:history_price('<%=temp[4]%>')" style="cursor: hand"><img src="googleImg/icon/search.gif" border=0 title="历史报价" ></a></td>
          <td align="center"><a href="javascript:up_affix(<%=temp[0]%>)" style="cursor: hand"><img src="googleImg/icon/writely.gif" border=0 title="附件" ></a></td>
        </tr>
      
      <%}}%> 
       

  <tr> 
    <td height="2" colspan="16" bgcolor="#ffffff"></td>
  </tr>
  <tr> 
    <td height="2" colspan="16"  bgcolor="#677789"></td>
  </tr>
</table>

<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td align="left">
    	<input type="button"  value="询价单打印" onClick="javascript:f_print()">
    	<input type="button"  value="确认" onClick="javascript:f_confirm()">

    </td>
  </tr>
</table>

</html:form>

</body>
</html>
<SCRIPT>
	function f_print(){
    	window.open("saleInfoAction.do?method=salePartsPrint&flag=inquiryPrint&custId=TWKM&saleNo="+document.forms[0].saleNo.value);
	}
	
	function history_price(stuffNo){
    	window.open("saleInfoAction.do?method=historyPrice&stuffNo="+stuffNo,"","width=700,height=300,left=50,top=10,menubar=no,toolbar=no,resizable=yes,scrolling=yes,scrollbars=yes");
	}
	
	function cust_price(customerId){
    	window.open("saleInfoAction.do?method=custPrice&customerId="+customerId,"","width=700,height=300,left=50,top=10,menubar=no,toolbar=no,resizable=yes,scrolling=yes,scrollbars=yes");
	}
	
	function up_affix(detailId){
			window.showModalDialog("attachedInfoAction.do?method=affixList&saleDetailId="+detailId+"&Rnd="+Math.random(),"","dialogHeight: 220px; dialogWidth: 620px; edge: Sunken; center: Yes; help: No; resizable: No; status: Yes;");

	}
	
	function f_confirm(){
			document.forms[0].action="saleInfoAction.do?method=saleAskConfirmList";
			document.forms[0].submit();
	}
	
</SCRIPT>