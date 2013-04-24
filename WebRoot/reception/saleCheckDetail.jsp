<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri=  "/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri=  "/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ page import="java.util.ArrayList"%> 
<%@ page import="java.util.List"%> 
<%@ page import="com.dne.sie.reception.form.SaleInfoForm"%>
<%@ page import="com.dne.sie.repair.form.RepairFeeInfoForm"%>
<%@ page import="com.dne.sie.common.tools.Operate"%>

<HTML>
<HEAD>
<title>报价核算明细</title>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/styles.css" rel="stylesheet" type="text/css" />
<script language=javascript src="js/inputValidation.js"></script>
<script language=javascript src="js/ajax.js"></script>
<script language=javascript src="js/common.js"></script>

</head>
<%
try{
		ArrayList vtrData = (ArrayList)request.getAttribute("detailList");
		SaleInfoForm sif = (SaleInfoForm)request.getAttribute("salesInfoForm");
		String tag = (String)request.getAttribute("tag");
		List repairFeeList = (List)request.getAttribute("repairFeeList");
    
%>
<body  onload="setTitle('报价核算');init_vat17();">

<html:form method="post" action="saleInfoAction.do?method=inquiryDetail">
<html:hidden property="saleNo"/>

<table width="100%" border="0" align="center" cellpadding="2" cellspacing="1" class="content12">
  <tr>
    <td>零件销售&gt;报价核算明细</td>
  </tr>
</table>
<br>
<table width="100%" border="0" align="center" cellpadding="2" cellspacing="1" class="content12">
  <tr> 
    <td height="2" colspan="6" bgcolor="#677789"></td>
  </tr>
  <tr> 
    <td height="6"  colspan="6" bgcolor="#CECECE"></td>
  </tr>
  <tr class="tableback2"> 
    <td width="5%"> 询价单号：</td>
    <td width="10%"><bean:write property="saleNo" name="salesInfoForm" /></td>
    <td width="5%"> 客户名称：</td>
    <td width="20%"><bean:write property="customerName" name="salesInfoForm" /></td>
    <input type="hidden" name="customerId" value="<bean:write property='customerId' name='salesInfoForm' />">
    <td width="5%">发货地址：</td>
    <td width="25%"><bean:write property="shippingAddress" name="salesInfoForm" /></td>
   </tr>
   <tr class="tableback1"> 
    <td width="5%"> 维修单号：</td>
    <td width="10%"><bean:write property="serviceSheetNo" name="salesInfoForm" />
    	<%if("I".equals(sif.getWarrantyType())){ %> <font color="red">(保内)</font><%} %>
    </td>
    <td width="5%"> 备注：</td>
    <td colspan=3><bean:write property="remark" name="salesInfoForm" /></td>
  </tr>
  <tr class="tableback2"> 
  </tr>
  
  
  <tr> 
    <td height="2" colspan="6" bgcolor="#ffffff"></td>
  </tr>
  <tr> 
    <td height="1" colspan="6" bgcolor="#677789"></td>
  </tr>
</table>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td align="left">
    	<input type="button"  value="客户报价" onClick="javascript:cust_price('<bean:write property="customerId" name="salesInfoForm" />')">
      <input type="button"  value="报价单打印" onClick="javascript:f_print()">
    </td>
  </tr>
</table>
<br>

<%if("-1".equals(tag)){%>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
<tr><td align="left">
<strong><font color="red">请检查输入框，有未填写的金额</font></strong>
</td></tr>
</table>		
<%}else if("mergeComplete".equals(tag)){%>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
<tr><td align="left">
<strong><font color="blue">合并完毕，原询价单自动删除</font></strong>
</td></tr>
</table>		
<%}%>

<%if(vtrData!=null&&!vtrData.isEmpty()){ %>
<table border="1" width="100%" align="center" id="tblsales" name="tblsales">
	<tr>
		<td align="center" rowspan="2">
		<p align="center">料号</td>
		<td align="center" rowspan="2" >
		<p align="center">数量</td>
		<td rowspan="2" align="center">
		<p align="center"><font color="blue">进价(单)</font></td>
		<td colspan="2" align="center">关税</td>
		<td align="center">海关增税</td>
		<td align="center" rowspan="2">代理费</td>
		<td  align="center">运输费</td>
		<td  align="center"><font color="blue">成本</font></td>
		<td  align="center">销售金额</td>
		<!-- <td  align="center">维修费</td> -->
		<td  align="center"><font color="blue">利润</font></td>
	</tr>
	<tr>
		<td>金额</td>
		<td>税率</td>
		<td>金额</td>
		<td>计划</td>
		<td>计划</td>
		<td>报价(单)</td>
		<!-- <td>计划</td> -->
		<td>计划</td>
	</tr>

<%
		for(int i=0;i<vtrData.size();i++){
				String[] temp=(String[])vtrData.get(i);
%>
	<tr>
		<td title="<%=temp[3]%>"><%=temp[4]%><input name="detailIds" type="hidden" value="<%=temp[0]%>"><input name="version" type="hidden" value="<%=temp[23]%>"> </td>
		<td><input class="form" name="partNumT" value="<%=temp[5]%>" size="2" onkeydown="javascript:f_onlynumber();" maxlength="10" onblur="f_math_num(this)" onchange="f_chg_flag()"></td>
		<td><input class="form" name="purchasePriceT" value="<%=temp[10]%>" size="6" onkeydown="javascript:f_onlymoney();" maxlength="10" onblur="f_math(this)" ></td>
		<td><input class="form" name="tariffAmountT" value="<%=temp[24]%>" size="6" readonly ></td>		
		<td><input class="form" name="tariffRatT" value="<%=temp[25]%>" size="2" onkeydown="javascript:f_onlymoney();" maxlength="10" onblur="f_rate(this)">%</td>		
		<td><input class="form" name="vatT" value="<%=temp[11]%>" size="6" onkeydown="javascript:f_onlymoney();" maxlength="10" onblur="f_math(this)"></td>
		<td><input class="form" name="customsChargesRealT" value="<%=temp[13]%>" size="6" onkeydown="javascript:f_onlymoney();" maxlength="10" onblur="f_math(this)"></td>
		<td><input class="form" name="domesticFreightPlanT" value="<%=temp[14]%>" size="6" onkeydown="javascript:f_onlymoney();" maxlength="10" onblur="f_math(this)"></td>
		<td><input class="form" name="costPlanT" value="<%=temp[16]%>" size="6" onkeydown="javascript:f_onlymoney();" maxlength="10" readonly ></td>
		<td><input class="form" name="totalQuoteT" value="<%=temp[6]%>" size="6" onkeydown="javascript:f_onlymoney();" maxlength="10" onblur="f_math(this)" onchange="f_chg_flag()"></td>
		<!-- <td>&nbsp;</td> -->
		<td><input class="form" name="profitPlanT" value="<%=temp[19]%>" size="6" onkeydown="javascript:f_onlymoney();" maxlength="10" readonly ></td>
	</tr>
<%}%>
	

	<tr>
		<td>　</td>
		<td>　</td>
		<td>　</td>
		<td>　</td>
		<td>　</td>
		<td>　</td>
		<td>　</td>
		<td>　</td>
		<td>　</td>
		<td>　</td>
		<!-- <td>　</td> -->
		<td>　</td>

	</tr>
	<tr>
		<td>合计</td>
		<td id="partNum"><bean:write property="partNum" name="salesInfoForm" /></td><input name="partNum" type="hidden" value="<bean:write property='partNum' name='salesInfoForm' />">
		<td id="purchasePrice"><bean:write property="purchasePrice" name="salesInfoForm" /></td><input name="purchasePrice" type="hidden" value="<bean:write property='purchasePrice' name='salesInfoForm' />">
		<td id="tariffAmount"><bean:write property="tariffAmount" name="salesInfoForm" /></td><input name="tariffAmount" type="hidden" value="<bean:write property='tariffAmount' name='salesInfoForm' />">
		<td id="tariffRat">&nbsp;</td>
		<td id="vat"><bean:write property="vat" name="salesInfoForm" /></td><input name="vat" type="hidden" value="<bean:write property='vat' name='salesInfoForm' />">
		<td id="customsChargesReal"><bean:write property="customsChargesReal" name="salesInfoForm" /></td><input name="customsChargesReal" type="hidden" value="<bean:write property='customsChargesReal' name='salesInfoForm' />">
		<td id="domesticFreightPlan"><bean:write property="domesticFreightPlan" name="salesInfoForm" /></td><input name="domesticFreightPlan" type="hidden" value="<bean:write property='domesticFreightPlan' name='salesInfoForm' />">
		<td id="costPlan"><bean:write property="costPlan" name="salesInfoForm" /></td><input name="costPlan" type="hidden" value="<bean:write property='costPlan' name='salesInfoForm' />">
		<td id="totalQuote"><bean:write property="totalQuote" name="salesInfoForm" /></td><input name="totalQuote" type="hidden" value="<bean:write property='totalQuote' name='salesInfoForm' />">
		<%-- <td id="repairFeePlan"><input class="form" name="repairFeePlan" value="<bean:write property="repairFeePlan" name="salesInfoForm" />" size="6" onkeydown="javascript:f_onlymoney();" maxlength="10" onblur="f_cg_profit()"></td> --%>
		<input type="hidden" name="repairFeePlan" value=0>
		<td id="profitPlan"><bean:write property="profitPlan" name="salesInfoForm" /></td><input name="profitPlan" type="hidden" value="<bean:write property='profitPlan' name='salesInfoForm' />">
	</tr>
	<tr>
		<td>　</td>
		<td colspan="8">　</td>
		<td colspan="2" id="vat17">增值税：</td>
	</tr>
	<tr>
		<td>　</td>
		<td colspan="8">　</td>
		<td colspan="2" id="quoteAll">零件合计：</td>
	</tr>
</table>
 <br>
 <%} %>

		
<%if(repairFeeList!=null){ %>
<table width="100%" border="0" align="center" cellpadding="2" cellspacing="1" class="content12">  
<tr>
    <td>维修费用</td>
  </tr>
  <tr> 
    <td height="2" colspan="6" bgcolor="#ffffff"></td>
  </tr>
  <tr> 
    <td height="1" colspan="6" bgcolor="#677789"></td>
  </tr>
</table>
 <table border="1" width="100%" align="center" id="tblRepair" name="tblRepair">
 		
     	<tr>
		<td>人数</td>
		<td>工时</td>
		<td><font color="blue">差旅费</font></td>
		<td>车船票</td>
		<td>人工费</td>
		<td><font color="blue">税金</font></td>
		<td><font color="blue">总成本</font></td>
		<td>维修报价</td>
		<td width="15%"><font color="blue">维修利润</font></td>
	</tr>

<%
		for(int i=0;i<repairFeeList.size();i++){
			RepairFeeInfoForm rfi=(RepairFeeInfoForm)repairFeeList.get(i);
			if(rfi.getFeeType().equals("P")){	//plan
			String tax = "0";
			String cost = "0";
			if(rfi.getWarrantyType().equals("O")){
				tax = Operate.toFix(rfi.getTaxes(),2);
				cost = Operate.toFix(rfi.getTotalCost(),2);
			}
%>
	<tr>
		<td id="repairmanNum"><input class="form" name="repairmanNum" value="<%=rfi.getRepairmanNum()%>" size="6" onkeydown="javascript:f_onlymoney();" maxlength="10" onblur="f_repair_cost(this,'<%=rfi.getWarrantyType() %>')" onchange="f_chg_flag2()"></td>	
		<td id="workingHours"><input class="form" name="workingHours" value="<%=rfi.getWorkingHours() %>" size="6" onkeydown="javascript:f_onlymoney();" maxlength="10" onblur="f_repair_cost(this,'<%=rfi.getWarrantyType() %>')" onchange="f_chg_flag2()"></td>
		<td id="travelCosts"><input class="form" name="travelCosts" value="<%=Operate.toFix(rfi.getTravelCosts(),2) %>" size="6" readonly ></td>
		<td id="ticketsAllCosts"><input class="form" name="ticketsAllCosts" value="<%=Operate.toFix(rfi.getTicketsAllCosts(),2) %>" size="6" onkeydown="javascript:f_onlymoney();" maxlength="10" onblur="f_repair_cost(this,'<%=rfi.getWarrantyType() %>')" onchange="f_chg_flag2()"></td>	
		<td id="laborCosts"><input class="form" name="laborCosts" value="<%=Operate.toFix(rfi.getLaborCosts(),2) %>" size="6" onkeydown="javascript:f_onlymoney();" maxlength="10" onblur="f_repair_cost(this,'<%=rfi.getWarrantyType() %>')" onchange="f_chg_flag2()"></td>
		<td id="taxes"><input class="form" name="taxes" value="<%=tax %>" size="6" readonly ></td>
		<td id="totalCost"><input class="form" name="totalCost" value="<%=cost %>" size="6" readonly ></td>
		<td id="repairQuote"><input class="form" name="repairQuote" value="<%=Operate.toFixD(rfi.getRepairQuote(),2) %>" size="6" onkeydown="javascript:f_onlymoney();" maxlength="10" onblur="f_repair_quote(this)" onchange="f_chg_flag2()"></td>
		<td id="repairProfit"><input class="form" name="repairProfit" value="<%=Operate.toFixD(rfi.getRepairProfit(),2) %>" size="6" readonly ></td>
	</tr>
	<tr>
		<td>　</td>
		<td colspan="6">　</td>
		<td colspan="2" id="vat17Repair">增值税：</td>
	</tr>
	<tr>
		<td>　</td>
		<td colspan="6">　</td>
		<td colspan="2" id="quoteAllRepair">维修合计：</td>
	</tr>
<%}}%>
		
		
		
 </table>
 <%} %>
 <br>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td align="left">
    <%if("D".equals(sif.getSaleStatus())){%>
    	<input type="button" name="bt" value="暂存" onClick="javascript:f_order_confirm('save')">
    	<input type="button" name="bt" value="订单确认" onclick="f_order_confirm('confirm')">
    	<input type="button" name="bt" value="订单取消" onClick="javascript:f_order_confirm('cancel')">
    	
    	<%if(sif.getRepairNo()!=null){%>
    	<input type="button" name="bt" value="维修订单合并" onClick="javascript:f_mergeWithRepair(<%=sif.getRepairNo()%>)">
    	<%}else{ %>
    	<input type="button" name="bt" value="订单合并" onClick="javascript:f_merge()">
    	<%} %>
    <%} %>	
    </td>
  </tr>
</table>
</html:form>

</body>

</html>
<SCRIPT>
	function f_print(){
		//单价、数量 不为空，其必须保存过
		if(f_chk_flag()){
				window.open("saleInfoAction.do?method=salePartsPrint&flag=quotePrint&custId=<bean:write property="customerId" name="salesInfoForm" />&saleNo="+document.forms[0].saleNo.value+"&repairNo=<%=sif.getRepairNo()%>");
		}
	}
	
	
	function cust_price(customerId){
    	window.open("saleInfoAction.do?method=custPrice&customerId="+customerId,"","width=700,height=300,left=50,top=10,menubar=no,toolbar=no,resizable=yes,scrolling=yes,scrollbars=yes");
	}
	
	
	var ajax = new sack();
	function f_merge(){
			var saleNoF=window.showModalDialog("saleInfoAction.do?method=saleCheckList&Rnd="+Math.random()+"&dsaleNo="+document.forms[0].saleNo.value+"&customerId="+document.forms[0].customerId.value+"&flag=merge","","dialogHeight: 300px; dialogWidth: 800px; edge: Sunken; center: Yes; help: No; resizable: No; status: Yes;");

			if(saleNoF!=null){
			
				ajax.setVar("saleNoF",saleNoF);
				ajax.setVar("saleNoT",document.forms[0].saleNo.value);
				ajax.setVar("customerId",document.forms[0].customerId.value);
				ajax.setVar("method", "mergeDetail");	
				ajax.requestFile = "saleInfoAction.do";
				ajax.method = "GET";			
				ajax.onCompletion = mergeResult;	
				ajax.runAJAX();      
				
				
			}

	}
	
	function mergeResult(){ 
		var returnXml = ajax.responseXML;			
		var myisEnable = returnXml.getElementsByTagName("ifUse")[0].firstChild.nodeValue;
		if((eval(myisEnable))){
				document.forms[0].action="saleInfoAction.do?method=inquiryDetail&flag=mergeComplete";
				document.forms[0].submit();
		}else{
				alert("该单没有零件或异常");
		}
	
	}
	
	
	function f_mergeWithRepair(repairNo){
		
	
	}
	
	
	function f_rate(obj){
		var len=document.forms[0].tariffRatT.length;
		if(len>1){
				for(var i=0;i<len;i++){
						var tObj=document.forms[0].tariffRatT[i];
						if(tObj==obj){
								var trt=document.forms[0].tariffRatT[i].value;
								var ppt=document.forms[0].purchasePriceT[i].value;
								document.forms[0].tariffAmountT[i].value=(new Number(ppt) * new Number(trt) / 100).toFixed(2);
	
								f_math_cost(obj);
								f_math_total(document.forms[0].tariffAmountT[i]);
								
						}
				}
		}else{
				var trt=document.forms[0].tariffRatT.value;
				var ppt=document.forms[0].purchasePriceT.value;
				document.forms[0].tariffAmountT.value=(new Number(ppt) * new Number(trt) / 100).toFixed(2);
				
				f_math_cost(obj);
				f_math_total(document.forms[0].tariffAmountT);
				
		}
	}
	
	
	/**
	 * 成本利润计算
	 * @version 1.0.2010.0619
 	 */
	function f_math(obj){
			if(obj.name=="tariffAmountT"){
				var len=document.forms[0].purchasePriceT.length;
				if(len>1){
					for(var i=0;i<len;i++){
							var tObj=document.forms[0].tariffAmountT[i];
							if(tObj==obj){
							
									var ppt=document.forms[0].purchasePriceT[i].value;
									var tat=document.forms[0].tariffAmountT[i].value;
									
									if(ppt!=0){
										document.forms[0].tariffRatT[i].value=(new Number(tat) / new Number(ppt) * 100).toFixed(2);
									}else{
										document.forms[0].tariffRatT[i].value=0;
									}
									document.forms[0].vatT[i].value= ((new Number(tat)+new Number(ppt))*0.17).toFixed(2);
							}
					}
				}else{
					var ppt=document.forms[0].purchasePriceT.value;
					var tat=document.forms[0].tariffAmountT.value;
					if(ppt!=0){
						document.forms[0].tariffRatT.value=(new Number(tat) / new Number(ppt) * 100).toFixed(2);
					}else{
						document.forms[0].tariffRatT.value=0;
					}
					
					document.forms[0].vatT.value= ((new Number(tat)+new Number(ppt))*0.17).toFixed(2);
				}
			}
			
			//成本&利润
			f_math_cost(obj);
			//总价
			f_math_total(obj);
			
		
	}
	
	function f_math_num(obj){
		if(obj.value==''){
			alert("请输入数量");
			obj.focus();
		}else{
			f_cout_num();
		
			f_math_cost(obj);
			
			var moneyVal = new Array();
			moneyVal[0]=document.forms[0].purchasePriceT;
			moneyVal[1]=document.forms[0].vatT;
			moneyVal[2]=document.forms[0].tariffAmountT;
			moneyVal[3]=document.forms[0].customsChargesRealT;
			moneyVal[4]=document.forms[0].domesticFreightPlanT;
			//moneyVal[5]=document.forms[0].domesticFreightRealT;
			moneyVal[5]=document.forms[0].totalQuoteT;
			//moneyVal[7]=document.forms[0].saleTotalPriceT;
			
			for(var i=0;i<moneyVal.length;i++){
				var len = moneyVal[i].length;
				if(len>1){
						f_math_total(moneyVal[i][0]);
				}else{
						f_math_total(moneyVal[i]);
				}
			}
			
		
			
		}
	}
	
	function f_cout_num(){
			var pNum=document.forms[0].partNumT;
    	var len = pNum.length;
			if(len>1){
					var count=0;
					for(var i=0;i<len;i++){
							count += new Number(pNum[i].value);
					}
					document.forms[0].partNum.value=count;
			} else {
					document.forms[0].partNum.value=pNum.value;
					
			}
			partNum.innerHTML=document.forms[0].partNum.value;
	}
	
	
	/**
	 * 单成本=进价 + 关税（金额） + 海关增税 + 代理费 + 运输费 (costPlan = purchasePrice + tariffAmount + vat + customsChargesReal + domesticFreightPlan)
	 * 单利润=报价-成本 (profitPlan = perQuote - costPlan )
	 * @version 1.0.2010.0619
 	 */
	function f_math_cost(obj){
			var perName=obj.name;
			var len = eval("document.forms[0]."+perName).length;
			if(len>1){
					for(var i=0;i<len;i++){
							var tObj=eval("document.forms[0]."+perName+"["+i+"]");
							
							if(tObj==obj){
									//单成本 - 计划
									var ppT=new Number(document.forms[0].purchasePriceT[i].value);
									var taT=new Number(document.forms[0].tariffAmountT[i].value);
									var vatV=new Number(document.forms[0].vatT[i].value);
									var ccpP=new Number(document.forms[0].customsChargesRealT[i].value);
									var dfpP=new Number(document.forms[0].domesticFreightPlanT[i].value);
									//单成本 - 实际
									//var dfpR=new Number(document.forms[0].domesticFreightRealT[i].value);
									
									var cP=ppT + taT + vatV + ccpP + dfpP;	//计划
									//var cR=ppT + taT + vatV + ccpP + dfpR;	//实际

									document.forms[0].costPlanT[i].value=cP.toFixed(2);	
									//document.forms[0].costRealT[i].value=cR.toFixed(2);
									f_math_total(document.forms[0].costPlanT[i]);
									//f_math_total(document.forms[0].costRealT[i]);
									
									//单利润 - 计划
									if(document.forms[0].totalQuoteT[i].value!=''){
										var tqP=new Number(document.forms[0].totalQuoteT[i].value);
										var pfP=tqP-cP;	//计划
										document.forms[0].profitPlanT[i].value=pfP.toFixed(2);
									}else{
										document.forms[0].profitPlanT[i].value='';
									}
									
							}
					}
			} else {
					//单成本 - 计划
					var ppT=new Number(document.forms[0].purchasePriceT.value);
					var taT=new Number(document.forms[0].tariffAmountT.value);
					var vatV=new Number(document.forms[0].vatT.value);
					var ccpP=new Number(document.forms[0].customsChargesRealT.value);
					var dfpP=new Number(document.forms[0].domesticFreightPlanT.value);
					//单成本 - 实际
					//var dfpR=new Number(document.forms[0].domesticFreightRealT.value);
					
					var cP=ppT + taT + vatV + ccpP + dfpP;	//计划
					//var cR=ppT + taT + vatV + ccpP + dfpR;	//实际
					document.forms[0].costPlanT.value=cP.toFixed(2);	
					//document.forms[0].costRealT.value=cR.toFixed(2);
					
					f_math_total(document.forms[0].costPlanT);
					//f_math_total(document.forms[0].costRealT);
									
					
					//单利润 - 计划
					if(document.forms[0].totalQuoteT.value!=''){
						var tqP=new Number(document.forms[0].totalQuoteT.value);
						var pfP=tqP-cP;	//计划
						document.forms[0].profitPlanT.value=pfP.toFixed(2);
					}else{
						document.forms[0].profitPlanT.value='';
					}
					
					
			}
	}
	
	/**
	 * 总利润=总报价-总成本-维修费 (profitPlan = totalQuote - costPlan - repairFeePlan )
	 *  2010-10-08  由于维修费向客户收取，总利润不再扣除维修费
	 * @version 1.0.2010.0619
 	 */
	function f_cg_profit(){
			var pqP=new Number(document.forms[0].totalQuote.value);			//总报价
			var cpP=new Number(document.forms[0].costPlan.value);				//总成本_计划	
			
			//var spP=new Number(document.forms[0].saleTotalPrice.value);	//销售总价
			//var cR=new Number(document.forms[0].costReal.value);				//成本_实际	
			
			//var rfP=new Number(document.forms[0].repairFeePlan.value);	//维修费_计划
			//var rfR=new Number(document.forms[0].repairFeeReal.value);	//维修费_实际
			
			document.forms[0].profitPlan.value=(pqP-cpP).toFixed(2);
			//document.forms[0].profitReal.value=(spP-cR-rfR).toFixed(2);
			profitPlan.innerHTML=document.forms[0].profitPlan.value;
			//profitReal.innerHTML=document.forms[0].profitReal.value;
	}
	
	/**
	 * 总价 = 单价1 * 数量 + 单价2 * 数量 + ……
	 * @version 1.0.2010.0619
 	 */
	function f_math_total(obj){
			var perName=obj.name;
			var totalName=perName.substring(0,perName.length-1);

			var totalObj=eval("document.forms[0]."+totalName);
			var tNum=0;
			var len = eval("document.forms[0]."+perName).length;
			if(len>1){
					for(var i=0;i<len;i++){
							var tObj=eval("document.forms[0]."+perName+"["+i+"]");
							var partNb=new Number(document.forms[0].partNumT[i].value);
							if(tObj.value!=''){
									tNum += (new Number(tObj.value) * partNb);
							}
					}
			} else {
					var partNb=new Number(document.forms[0].partNumT.value);
					tNum=new Number(obj.value)*partNb;
					
			}

			totalObj.value=tNum.toFixed(2);
			eval(totalName).innerHTML=totalObj.value;
			//总利润
			f_cg_profit();
			init_vat17();
	}
	
	
	
	
	function f_order_confirm(flag){
			if(flag=='confirm'&&f_chk()){
				return; 
			}
			var len=document.forms[0].bt.length;
			for(var i=0;i<len;i++){
					document.forms[0].bt[i].disabled=true;
			}
    		document.forms[0].action="saleInfoAction.do?method=saleCheckConfirm&flag="+flag;
			document.forms[0].submit();
	}
	
	function f_chk(){
		<%if(vtrData!=null&&!vtrData.isEmpty()){ %>
		var moneyVal = new Array();
		moneyVal[0]=document.forms[0].purchasePriceT;
		moneyVal[1]=document.forms[0].vatT;
		moneyVal[2]=document.forms[0].tariffAmountT;
		moneyVal[3]=document.forms[0].customsChargesRealT;
		moneyVal[4]=document.forms[0].domesticFreightPlanT;
		moneyVal[5]=document.forms[0].costPlanT;
		moneyVal[6]=document.forms[0].totalQuoteT;
		moneyVal[7]=document.forms[0].profitPlanT;
		moneyVal[8]=document.forms[0].partNumT;
	
		for(var x=0;x<moneyVal.length;x++){
    		var len = moneyVal[x].length;
			if(len>1){
					for(var i=0;i<len;i++){
							if(moneyVal[x][i].value==''){
									moneyVal[x][i].focus();
									alert("请输入金额！");
									return true;
							}
					}
			} else {

					if(moneyVal[x].value==''){
							moneyVal[x].focus();
							alert("请输入金额！");
							return true;
					}
			}
			
		}
		
		<%}%>
		
		<%if(repairFeeList!=null){ %>
		var repairVal = new Array();
		repairVal[0]=document.forms[0].repairmanNum;
		repairVal[1]=document.forms[0].workingHours;
		repairVal[2]=document.forms[0].ticketsAllCosts;
		repairVal[3]=document.forms[0].laborCosts;
		for(var x=0;x<repairVal.length;x++){
			if(repairVal[x].value==''){
				repairVal[x].focus();
				alert("请输入维修费用！");
				return true;
			}
		}
		<%}%>
		return false;
	}
	
	var chgFlag=true;
	function f_chg_flag(){
			chgFlag=false;
			init_vat17();
	}
	
	function f_chg_flag2(){
		chgFlag=false;
	}
	
	function f_chk_flag(){
		<%if(vtrData!=null&&!vtrData.isEmpty()){ %>
		var quoteP=document.forms[0].totalQuoteT;
    	var len = quoteP.length;
		if(len>1){
				for(var i=0;i<len;i++){
						if(quoteP[i].value==''){
							quoteP[i].focus();
							alert("请输入报价！");
							return false;
					}
				}
		}else {
				if(quoteP.value==''){
						quoteP.focus();
						alert("请输入报价！");
						return false;
				}
		}
		
		if(!chgFlag){
				alert("请先暂存数据！");
				return false;
		}
		<%}%>
		<%if(repairFeeList!=null){ %>
		var rmn=document.forms[0].repairmanNum;
		var wh=document.forms[0].workingHours;
		var tac=document.forms[0].ticketsAllCosts;
		var lc=document.forms[0].laborCosts;
		var tc=document.forms[0].travelCosts;
		var tax=document.forms[0].taxes;
		var totalCost=document.forms[0].totalCost;
		if(rmn.value==''){
			rmn.focus();
			alert("请输入维修人数！");
			return false;
		}
		if(wh.value==''){
			wh.focus();
			alert("请输入维修工时！");
			return false;
		}
		if(tac.value==''){
			tac.focus();
			alert("请输入差旅费！");
			return false;
		}
		if(lc.value==''){
			lc.focus();
			alert("请输入车船票！");
			return false;
		}
		if(tc.value==''){
			tc.focus();
			alert("请输入人工费！");
			return false;
		}
		if(tax.value==''){
			tax.focus();
			alert("请输入税金！");
			return false;
		}
		if(totalCost.value==''){
			totalCost.focus();
			alert("请输入维修总成本！");
			return false;
		}
		<%}%>
		return true;
	}
	
	
	function f_repair_quote(obj){
			var len=document.forms[0].repairmanNum.length;
			if(len>1){
				
			}else{
				var tc=new Number(document.forms[0].totalCost.value);
				var rq=new Number(document.forms[0].repairQuote.value);
				document.forms[0].repairProfit.value=(new Number(rq)-new Number(tc)).toFixed(2);
				
			}
			init_vat17();
	}
	
	function init_vat17(){
		<%if(vtrData!=null&&!vtrData.isEmpty()){ %>
			var tq=new Number(document.forms[0].totalQuote.value);
			vat17.innerHTML="增值税：" + (tq* 0.17).toFixed(2);
			quoteAll.innerHTML="零件合计："+(tq * 1.17).toFixed(2);
		<%}%>
		<%if(repairFeeList!=null){ %>
			var rq=new Number(document.forms[0].repairQuote.value);
			vat17Repair.innerHTML="增值税：" + (rq* 0.17).toFixed(2);
			quoteAllRepair.innerHTML="维修合计："+(rq * 1.17).toFixed(2);
		<%}%>
	}
	
	var travelAllowance = <%=Operate.getReportPath()[3]%>;
	function f_repair_cost(obj,iw){
		//if(obj.name=="repairmanNum"){
			var len=document.forms[0].repairmanNum.length;
			if(len>1){
				
			}else{
				var rmn=new Number(document.forms[0].repairmanNum.value);
				var wh=new Number(document.forms[0].workingHours.value);
				var tac=new Number(document.forms[0].ticketsAllCosts.value);
				var lc=new Number(document.forms[0].laborCosts.value);
				
				//差旅费 = 人数 * 工时  * 250
				var temp = (rmn * wh * travelAllowance).toFixed(2);
				document.forms[0].travelCosts.value = temp;
				if(iw=='I'){
					document.forms[0].taxes.value  = 0;
					document.forms[0].totalCost.value  = 0;
				}else{
					//税金 = （差旅费+车船票+人工费）* 0.17
					document.forms[0].taxes.value  = ( (new Number(temp) + tac + lc)*0.17).toFixed(2);
					//总成本 = 差旅费+车船票+人工费 +税金
					document.forms[0].totalCost.value  = ( new Number(temp) + tac + lc + new Number(document.forms[0].taxes.value)).toFixed(2);
				}
			}
		//}
		
	}
	
	
</SCRIPT>
<%
}catch(Exception e){
	e.printStackTrace();
}%>