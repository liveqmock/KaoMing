<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri=  "/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri=  "/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ page import="java.util.ArrayList"%> 
<%@ page import="java.util.List"%> 
<%@ page import="com.dne.sie.common.tools.DicInit"%> 
<%@ page import="com.dne.sie.reception.form.SaleInfoForm"%> 
<%@ page import="com.dne.sie.common.tools.Operate"%>
<%@ page import="com.dne.sie.common.tools.AtomRoleCheck"%>

<HTML>
<HEAD>
<title>订购单查询</title>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/styles.css" rel="stylesheet" type="text/css" />
<script language=javascript src="js/ajax.js"></script>
<script language=javascript src="js/checkValid.js"></script>
<script language=javascript src="js/inputValidation.js"></script>
<script language=javascript src="js/common.js"></script>

</head>

<%
try{
		Long userId = (Long)session.getAttribute("userId");
		ArrayList vtrData = (ArrayList)request.getAttribute("detailList");
		String viewFlag = (String)request.getAttribute("flag");
		SaleInfoForm sif = (SaleInfoForm)request.getAttribute("salesInfoForm");
		
		ArrayList payTypeList = (ArrayList)DicInit.SYS_CODE_MAP.get("PAY_TYPE");
		ArrayList payVarietyList = (ArrayList)DicInit.SYS_CODE_MAP.get("PAY_VARIETY");
		//ArrayList invoiceTypeList = (ArrayList)DicInit.SYS_CODE_MAP.get("INVOICE_TYPE");
		
		List[] salePayedList = (List[])request.getAttribute("salePayedList");
		List payList=salePayedList[0];
		List invoiceList=salePayedList[1];
		
		Float saleBalanceDue = (Float)salePayedList[2].get(0);
		boolean isChange=false;
		//核算后，复核前
		if(!"view".equals(viewFlag)&&sif.getSaleStatus().compareTo("F")>=0&&!"Z".equals(sif.getSaleStatus())){
				isChange=true;
		}
		
		
		
		String totalQuteWithTax = sif.getTotalQuteWithTax().toString();
		
		
		
    
%>
<body  bgcolor="#ffffff" leftmargin="0" topmargin="0" rightmargin="0" bottommargin="0" onload="addUserPaidTd(<%=saleBalanceDue%>);" >

<html:form method="post" action="saleInfoAction.do?method=saleInfoDetail">
<input name="saleDetailId" type="hidden" >
<table width="100%" border="0" align="center" cellpadding="2" cellspacing="1" class="content12">
  <tr>
    <td>零件订购清单&gt;明细</td>
  </tr>
</table>
<br>
<table width="100%" border="0" align="center" cellpadding="2" cellspacing="1" class="content12">
  <tr> 
    <td height="2" colspan="11" bgcolor="#677789"></td>
  </tr>
  <tr> 
    <td height="6"  colspan="11" bgcolor="#CECECE"></td>
  </tr>
  <tr class="tableback1"> 
    <td > 销售单号码：</td>
    <td ><bean:write property="saleNo" name="salesInfoForm" /></td>
    <td > 客户名称：</td>
    <td><bean:write property="customerName" name="salesInfoForm" /></td>
    <td > 客户电话：</td>
    <td ><bean:write property="customerPhone" name="salesInfoForm" /></td>
    <td>日期：</td>
    <td><bean:write property="orderMonth" name="salesInfoForm" /></td>
    <td> 申请单状态：</td>
    <td> <font color="#FF6600"><%=DicInit.getSystemName("SALE_STATUS", sif.getSaleStatus())%></font></td>
  </tr>
  <tr class="tableback2">
    
    <td>零件报价：</td>
    <td><%=sif.getTotalQuote()==null?"":Operate.toFix(sif.getTotalQuote()*1.17,2) %></td>
    <td>零件利润：</td>
    <td><bean:write property="profitPlan" name="salesInfoForm" /></td>
    <td>维修报价：</td>
    <td><bean:write property="repairQuote" name="salesInfoForm" /></td>
    <td>维修利润：</td>
    <td><bean:write property="repairProfit" name="salesInfoForm" /></td>
    <td>总报价：</td>
    <td><%=totalQuteWithTax %></td>
    
    
    
  </tr>
  <tr class="tableback1">
  	<td width="8%"> 维修单号：</td>
    <td width="15%"><bean:write property="serviceSheetNo" name="salesInfoForm" />
    	<%if("I".equals(sif.getWarrantyType())){ %> <font color="red">(保内)</font><%} %>
    </td>
    <td>备注：</td>
    <td colspan="7"><bean:write property="remark" name="salesInfoForm" /></td>
  </tr>
  
  <tr> 
    <td height="2" colspan="11" bgcolor="#ffffff"></td>
  </tr>
  <tr> 
    <td height="1" colspan="11" bgcolor="#677789"></td>
  </tr>
</table>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td align="left">
			<!--<input type="button"  value="打印发票" onclick="javascript:printInvoince()">-->
    </td>
  </tr>
</table>
<br>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="content12">
  <tr bgcolor="#CCCCCC"> 
    <td><strong>料号</strong></td>
    <td><strong>零件名称</strong></td>
    <td><strong>机型</strong></td>
    <td><strong>机身编码</strong></td>
    <td><strong>数量</strong></td>
    <td><strong>来源</strong></td>
    <td><strong>类型</strong></td>
    <td><strong>成本（单）</strong></td>
    <td><strong>销售价（单）</strong></td>
    <td><strong>利润（单）</strong></td>
    <td><strong>状态</strong></td>
    <td  align="center" ><strong>附件</strong></td>
    <td><strong>取消</strong></td>
    
  </tr>
  
     <%if(vtrData!=null){
       	    String strTr="";
      	    for(int i=0;i<vtrData.size();i++){
      	        if(i%2==0) strTr="tableback2";
      	        else strTr="tableback1";
      		String[] temp=(String[])vtrData.get(i);
      		String dsb="disabled";
      		if(!"view".equals(viewFlag)&&temp[21].compareTo("N")<=0){
      				dsb="";
      		}
      %>
      
      <tr class="<%=strTr%>"> 
          <td ><%=temp[4]==null?"":temp[4]%></td>
          <td ><%=temp[3]==null?"":temp[3]%></td>
		  <td ><%=temp[1]==null?"":temp[1]%></td>
          <td ><%=temp[2]==null?"":temp[2]%></td>
          <td ><%=temp[5]==null?"":temp[5]%></td>
          <td ><%=DicInit.getSystemName("ORDER_TYPE", temp[9])%></td>
          <td ><%=DicInit.getSystemName("WARRANTY_TYPE", temp[26])%></td>
          <td ><%=temp[16]==null?"":temp[16]%></td>
          <td ><%=temp[18]==null?"":temp[18]%></td>
          <td ><%=temp[19]==null?"":temp[19]%></td>
          <td ><%=DicInit.getSystemName("SALE_STATUS", temp[21])%></td>
          <td align="center"><a href="javascript:up_affix(<%=temp[0]%>)" style="cursor: hand"><img src="googleImg/icon/writely.gif" border=0 title="附件" ></a></td>          
          <td ><input type="button"  value="取消" onclick="f_cancel('<%=temp[0]%>',this)" <%=dsb%>></td>
        </tr>
      
      <%}}%> 
       

  <tr> 
    <td height="2" colspan="16" bgcolor="#ffffff"></td>
  </tr>
  <tr> 
    <td height="1" colspan="16"  bgcolor="#677789"></td>
  </tr>
</table>
<br>

<%if(isChange){%>

<table width="100%" border="0" align="center" cellpadding="2" cellspacing="1" class="content12">
 <tr> 
    <td height="2" colspan="16"  bgcolor="green"></td>
  </tr>

        <tr class="tableback2"> 
         <td>收款金额：<font color="#FF6600">*</font></td>
         <td ><input name="payAmount" type="text" class="form" size="20" maxlength="12" onkeydown="javascript:f_onlymoney();" ></td>
         <td>收款类型：<font color="#FF6600">*</font></td>
         <td ><select class="form" name="payVariety"  >	
					<%for(int i=0;payVarietyList!=null&&i<payVarietyList.size();i++){
                  	String[] temp=(String[])payVarietyList.get(i);
                	%>
                		<option value="<%=temp[0]%>" ><%=temp[1]%></option>
                 	<%}%>
      						</select></td>
      						
      	 <td>收款方式：<font color="#FF6600">*</font></td>
         <td ><select class="form" name="payType" onchange="f_chg_pt(this.value)" >	
					<%for(int i=0;payTypeList!=null&&i<payTypeList.size();i++){
                  	String[] temp=(String[])payTypeList.get(i);
                	%>
                		<option value="<%=temp[0]%>" ><%=temp[1]%></option>
                 	<%}%>
      						</select></td>
        </tr>
        <tr class="tableback1"> 
         <td width="10%">客户银行：</td>
         <td width="20%" ><input name="clientBank" type="text" class="form" size="20" maxlength="32"></td>
         <td width="20%">客户支票/转账号码：</td>
         <td width="20%" ><input name="paymentCardNo" type="text" class="form" size="30" maxlength="50"></td>
         <td width="10%" > </td>
         <td width="20%" ></td>
        </tr>
  			
        <tr >
         <td colspan="6"><input name="addPaymentButton" type="button"  value="确认收费" onclick="addPayment()"></td>
        </tr>
        
       </table>
	   <br>
<%}%>	   
    <table width="100%" border="0" align="center" cellpadding="2" cellspacing="1" class="content12">
   
	    <tr> 
         <td>客户应付总额：</td>
         <td id="USER_NEED_PAY_TOTAL_TD"><font color="blue"><B><%=totalQuteWithTax %></B></font></td>
         <td>客户已付总额：</td>
         <td id="USER_PAID_TD"><font color="green"><B></B></font></td>
         <td>客户尚需付：</td>
         <td id="USER_NEED_PAY_TD"><font color="red"><B></B></font></td>
        </tr>
         <tr> 
		   <td height="3" colspan="10" background="<%= request.getContextPath()%>/images/i_line.gif"></td>
		</tr>
	   </table>
	  
	<table  width="100%" border="0" align="center" cellspacing="2" cellpadding="1" class="content12" border="0" id="paidParentTable">
	 <tr>
    <td><B>收款信息</B></td>
     </tr>
	<tr><td width="100%">
	<div class="scrollDiv" id="PAIDScrollDiv">
       <table width="100%" border="0" cellpadding="0" cellspacing="1" class="content12" id="paidTable">
	    <THEAD>
        <tr bgcolor="#CCCCCC">
         <td><B>收款日期</B></td>
         <td><B>收款金额</B></td>
         <td><B>收款类型</B></td>
         <td><B>收款方式</B></td>
         <td><B>客户银行</B></td>
         <td><B>支票/转账号码</B></td>
         <td>&nbsp;</td>
        </tr>
      </THEAD>
      <TBODY>
      <%for(int i=0;i<payList.size();i++){
      		String[] temp=(String[])payList.get(i);
      %>
         <tr class="tableback1" id="PART_ROW_<%=temp[0]%>">
         <td><%=temp[1]%></td>
         <td><%=temp[2]%></td>
         <td><%=temp[6]%></td>
         <td><%=temp[3]%></td>
         <td><%=temp[4]%></td>
         <td><%=temp[5]%></td>
         <%if(AtomRoleCheck.checkRole(userId, "MANAGER")){ %>
         <td width="6%" align="center"><input name="delPartButton" type="button" class="button2" value="删除" onclick="deleteAPart('PART_ROW_<%=temp[0]%>')"></td>
         <%}else{ %>
         <td>&nbsp;</td>
         <%} %>
        </tr>
      <%}%>
	   </TBODY>
       </table>
       </div> <!--end of PAIDScrollDiv-->
</td></tr>
</table> <!--end of paidParentTable-->

<br>
<%if(isChange||AtomRoleCheck.checkRole(userId, "MANAGER")){%>
<table width="100%" border="0" align="center" cellpadding="2" cellspacing="1" class="content12">
 <tr> 
    <td height="2" colspan="16"  bgcolor="green"></td>
  </tr>

        <tr class="tableback1"> 
         <td>开票零件：<font color="#FF6600">*</font></td>
         <td ><SELECT name="stuffNo" class="form">
      		<%
      		for(int i=0;vtrData!=null&&i<vtrData.size();i++){
      			String[] temp=(String[])vtrData.get(i);
      		%>
      		<option value="<%=temp[4]%>" ><%=temp[3]%></option>
      		<%}%>
      		<%if(sif.getRepairFeePlan()!=null){ %>
      		<option value="RepairFee" >维修费</option>
      		<%} %>
        	</SELECT></td>
         <td>开票金额：<font color="#FF6600">*</font></td>
         <td ><input name="billingMoney" type="text" class="form" size="20" onkeydown="javascript:f_onlymoney();" ></td>
         
         <input name="invoiceType" type="hidden" value="V">
         
      	 <td>发票号：<font color="#FF6600">*</font></td>
         <td ><input name="invoiceNo" type="text" class="form" size="20" style="text-transform:uppercase" onblur="this.value=this.value.toUpperCase()"></td>					
        </tr>
        
  			
        <tr >
         <td colspan="6"><input name="addInvoiceButton" type="button"  value="确认开票"  onclick="addInvoice()"></td>
        </tr>
        
       </table>
       <%}%>
	<table  width="100%" border="0" align="center" cellspacing="2" cellpadding="1" class="content12" border="0" id="invoiceParentTable">
	<tr> 
		<td height="3" colspan="10" background="<%= request.getContextPath()%>/images/i_line.gif"></td>
	</tr>
	 <tr>
    <td><B>开票信息</B></td>
     </tr>
	<tr><td width="100%">
	<div class="scrollDiv" id="InvoiceScrollDiv">
       <table width="100%" border="0" cellpadding="0" cellspacing="1" class="content12" id="invoiceTable">
	    <THEAD>
        <tr bgcolor="#CCCCCC">
         <td><B>开票日期</B></td>
         <td><B>开票零件</B></td>
         <td><B>开票金额</B></td>
         <td><B>开票类型</B></td>
         <td><B>发票号</B></td>
        </tr>
      </THEAD>
      <TBODY>
         <%for(int i=0;i<invoiceList.size();i++){
      		String[] temp=(String[])invoiceList.get(i);
      %>
         <tr class="tableback1">
         <td width="20%" ><%=temp[1]%></td>
         <td><%=temp[5]%></td>
         <td><%=temp[2]%></td>
         <td><%=temp[3]%></td>
         <td><%=temp[4]%></td>
        </tr>
      <%}%>
      </TBODY>
      </table>
</div> <!--end of InvoiceScrollDiv-->
</td></tr>
</table> <!--end of invoiceParentTable-->

</html:form>

<script LANGUAGE="JAVASCRIPT">
	var NEED_PAY=0;
	function f_chg_pt(payTp){
			if(payTp=='B'){	//现金
					document.forms[0].clientBank.disabled=true;
					document.forms[0].paymentCardNo.disabled=true;
					document.forms[0].clientBank.value="";
					document.forms[0].paymentCardNo.value="";
			}else{
					document.forms[0].clientBank.disabled=false;
					document.forms[0].paymentCardNo.disabled=false;
			}
	
	}
	
	
	
	var ajax = new sack();
	function addPayment(){
			var chkPay=false;

			if(f_isNull(document.forms[0].payAmount,'付费金额')&&f_isNull(document.forms[0].payType,'付费方式')&&f_isNull(document.forms[0].payVariety,'付费类型')){
				if(document.forms[0].payType.value!='B'){
						if(f_isNull(document.forms[0].clientBank,'客户银行')&&f_isNull(document.forms[0].paymentCardNo,'客户支票/转账号码')){
								chkPay=true;
						}
				}else{
						chkPay=true;
				}
			}
			
			if(chkPay){
					document.forms[0].addPaymentButton.disabled=true;
					var totalQuote='<bean:write property="totalQuote" name="salesInfoForm" />';
					if(totalQuote=='') totalQuote=0;
					ajax.setVar('totalQuote',totalQuote);
					ajax.setVar('saleNo','<bean:write property="saleNo" name="salesInfoForm" />');
					ajax.setVar("payAmount",document.forms[0].payAmount.value);
					ajax.setVar("payType",document.forms[0].payType.value);
					ajax.setVar("payVariety",document.forms[0].payVariety.value);
					ajax.setVar("clientBank",escape(document.forms[0].clientBank.value));
					ajax.setVar("paymentCardNo",escape(document.forms[0].paymentCardNo.value));
					ajax.setVar("dataType","P");
					ajax.setVar("method", "addPayment");	
					ajax.requestFile = "saleInfoAction.do";
					ajax.method = "GET";
					ajax.onCompletion = addPaymentCompleted;	
					ajax.runAJAX();  
			}			

	}
	
	function addPaymentCompleted(){ 
		var returnXml = ajax.responseXML;			
		var flag = returnXml.getElementsByTagName("flag")[0].firstChild.nodeValue;
		if((eval(flag))){
				var paymentDate = returnXml.getElementsByTagName("paymentDate")[0].firstChild.nodeValue;
				var allPayAmount = returnXml.getElementsByTagName("allPayAmount")[0].firstChild.nodeValue;
				var payAmount = returnXml.getElementsByTagName("payAmount")[0].firstChild.nodeValue;
				var payType = returnXml.getElementsByTagName("payType")[0].firstChild.nodeValue;
				var payVariety = returnXml.getElementsByTagName("payVariety")[0].firstChild.nodeValue;

				addUserPaidTd(allPayAmount);
				addPaidTr(allPayAmount,paymentDate,payAmount,unescape(payType),unescape(payVariety));
				
				alert("付费完成");
		}else{
				alert("在添加付费记录时异常，请联系管理员");
		}
		document.forms[0].addPaymentButton.disabled=false;
	}
	
	function addPaidTr(allPayAmount,paymentDate,payAmount,payType,payVariety){
		//alert("进入 addpaidtr");
		var oBody = paidTable.tBodies[0] ;
		var oNewRow=oBody.insertRow();
		oNewRow.className = "tableback1";
		
		var oNewCell=oNewRow.insertCell(0).innerHTML=paymentDate;
		var oNewCell=oNewRow.insertCell(1).innerHTML=payAmount;
		var oNewCell=oNewRow.insertCell(2).innerHTML=payVariety;
		var oNewCell=oNewRow.insertCell(3).innerHTML=payType;
		var oNewCell=oNewRow.insertCell(4).innerHTML=document.forms[0].clientBank.value;
		var oNewCell=oNewRow.insertCell(5).innerHTML=document.forms[0].paymentCardNo.value;
	}
	
	function addUserPaidTd(allPayAmount){	//计算已付费信息
		
		var balanceDue = new Number(<%=totalQuteWithTax %>)-new Number(allPayAmount);
		
		USER_PAID_TD.innerHTML = "<font color=\"green\"><B>"+new Number(allPayAmount).toFixed(2)+"</B></font>";
		//alert(<bean:write property="totalQuote" name="salesInfoForm" />+"-"+allPayAmount+"="+balanceDue);
		if(balanceDue<0) balanceDue=0;
		USER_NEED_PAY_TD.innerHTML = "<font color=\"red\"><B>"+balanceDue.toFixed(2)+"</B></font>";
		NEED_PAY=balanceDue.toFixed(2);
		
	}
	
	
	var ajaxInvoice = new sack();
	function addInvoice(){
			
			if(f_isNull(document.forms[0].billingMoney,'开票金额')&&f_isNull(document.forms[0].invoiceNo,'发票号')){
					document.forms[0].addInvoiceButton.disabled=true;
					ajaxInvoice.setVar('saleNo','<bean:write property="saleNo" name="salesInfoForm" />');
					ajaxInvoice.setVar("billingMoney",document.forms[0].billingMoney.value);
					ajaxInvoice.setVar("stuffNo",document.forms[0].stuffNo.value);
					ajaxInvoice.setVar("invoiceType",document.forms[0].invoiceType.value);
					ajaxInvoice.setVar("invoiceNo",document.forms[0].invoiceNo.value);
					ajaxInvoice.setVar("dataType","V");
					ajaxInvoice.setVar("method", "addInvoice");
					ajaxInvoice.requestFile = "saleInfoAction.do";
					ajaxInvoice.method = "GET";
					ajaxInvoice.onCompletion = addInvoiceCompleted;	
					ajaxInvoice.runAJAX();  
			}			
	}
	
	
	
	function addInvoiceCompleted(){ 
		var returnXml = ajaxInvoice.responseXML;			
		var flag = returnXml.getElementsByTagName("flag")[0].firstChild.nodeValue;
		if((eval(flag))){
				var paymentDate = returnXml.getElementsByTagName("paymentDate")[0].firstChild.nodeValue;
				var billingMoney = returnXml.getElementsByTagName("billingMoney")[0].firstChild.nodeValue;
				var payType = returnXml.getElementsByTagName("payType")[0].firstChild.nodeValue;
				var stuffNo = returnXml.getElementsByTagName("stuffNo")[0].firstChild.nodeValue;
				var oBody = invoiceTable.tBodies[0] ;
				var oNewRow=oBody.insertRow();
				oNewRow.className = "tableback1";
				
				var oNewCell=oNewRow.insertCell(0).innerHTML=paymentDate;
				var oNewCell=oNewRow.insertCell(1).innerHTML=stuffNo;
				var oNewCell=oNewRow.insertCell(2).innerHTML=billingMoney;
				var oNewCell=oNewRow.insertCell(3).innerHTML=unescape(payType);
				var oNewCell=oNewRow.insertCell(4).innerHTML=document.forms[0].invoiceNo.value;
				
				alert("开票完成");
		}else{
				alert("在添加开票记录时异常，请联系管理员");
		}
		document.forms[0].addInvoiceButton.disabled=false;
	}
	
	function f_cancel(detailId,obj){
		if(confirm("确定取消该零件吗？")){
			obj.disabled=true;
			document.forms[0].saleDetailId.value=detailId;
			document.forms[0].action="saleInfoAction.do?method=partCancel";
			document.forms[0].submit();
		}
	}
	
	function up_affix(detailId){
			window.showModalDialog("attachedInfoAction.do?method=affixList&saleDetailId="+detailId+"&viewFlag=<%=viewFlag%>&Rnd="+Math.random(),"","dialogHeight: 220px; dialogWidth: 620px; edge: Sunken; center: Yes; help: No; resizable: No; status: Yes;");

	}
	
	
	
	var ajaxD = new sack();
	function deleteAPart(partsIdTRRow){
		var feeId = partsIdTRRow.substr(9);// 从9开始截是因为前面有"PART_ROW_"开头
		ajaxD.setVar("feeId",feeId);
		ajaxD.setVar("method", "deleteSalePayment");
		ajaxD.requestFile = "saleInfoAction.do";
		ajaxD.method = "GET";
		ajaxD.onCompletion = deletePartCompleted;
		ajaxD.runAJAX();
		globalButton = event.srcElement;
		globalButton.disabled = true;
	}
	function deletePartCompleted(){
		var returnXml = ajaxD.responseXML;
		var flag = returnXml.getElementsByTagName("flag")[0].firstChild.nodeValue;
		if(eval(flag)){
			var feeId = returnXml.getElementsByTagName("feeId")[0].firstChild.nodeValue;
			deletePartRowEnd(feeId);
		}else{
			alert("删除失败，请联系管理员！");
		}
		globalButton.disabled = false;
		globalButton = null;
	}
	function deletePartRowEnd(partsId){
		deletePartRow(partsId);
	}
	function deletePartRow(partsId){
		//alert("deletePartRow.partsId:"+partsId);
		var deleteTr = eval("PART_ROW_"+partsId);
		if(paidTable.tBodies[0].rows.length>0){
			for(var p=0;p<paidTable.tBodies[0].rows.length;p++){
				if(paidTable.tBodies[0].rows[p].id == "PART_ROW_"+partsId){
					//alert("paidTable.tBodies[0].rows[p].id:"+paidTable.tBodies[0].rows[p].id);
					paidTable.tBodies[0].removeChild(deleteTr);
				}
			}
		}
		deleteTr.style.display = "none";
	}

</script>
</body>
<%
}catch(Exception e){
	e.printStackTrace();
}%>
</html>
