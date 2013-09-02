<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri=  "/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri=  "/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ page import="java.util.ArrayList"%> 
<%@ page import="java.util.List"%> 
<%@ page import="com.dne.sie.reception.form.SaleInfoForm"%>
<%@ page import="com.dne.sie.repair.form.RepairFeeInfoForm"%>
<%@ page import="com.dne.sie.common.tools.*"%>

<HTML>
<HEAD>
<title>���ۺ�����ϸ</title>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/styles.css" rel="stylesheet" type="text/css" />
<script language=javascript src="js/inputValidation.js"></script>
<script language=javascript src="js/ajax.js"></script>

</head>
<%
		ArrayList vtrData = (ArrayList)request.getAttribute("detailList");
		String tag = (String)request.getAttribute("tag");
		
		SaleInfoForm sif = (SaleInfoForm)request.getAttribute("salesInfoForm");
		List repairFeeList = (List)request.getAttribute("repairFeeList");
		
		Long userId = (Long)session.getAttribute("userId");
    
%>
<body  bgcolor="#ffffff" leftmargin="0" topmargin="0" rightmargin="0" bottommargin="0" marginwidth="0" marginheight="0" onload="init_vat17()">

<html:form method="post" action="saleInfoAction.do?method=inquiryDetail">
<html:hidden property="saleNo"/>

<table width="100%" border="0" align="center" cellpadding="2" cellspacing="1" class="content12">
  <tr>
    <td>�������&gt;���ۺ�����ϸ</td>
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
    <td width="5%"> ѯ�۵��ţ�</td>
    <td width="10%"><bean:write property="saleNo" name="salesInfoForm" /></td>
    <td width="5%"> �ͻ����ƣ�</td>
    <td width="20%"><bean:write property="customerName" name="salesInfoForm" /></td>
    <input type="hidden" name="customerId" value="<bean:write property='customerId' name='salesInfoForm' />">
    <td width="5%">������ַ��</td>
    <td width="25%"><bean:write property="shippingAddress" name="salesInfoForm" /></td>
   </tr>
   <tr class="tableback1"> 
    <td width="5%"> ά�޵��ţ�</td>
    <td width="10%"><bean:write property="serviceSheetNo" name="salesInfoForm" />
    	<%if("I".equals(sif.getWarrantyType())){ %> <font color="red">(����)</font><%} %>
    </td>
    <td width="5%"> ��ע��</td>
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
    	<input type="button"  value="�ͻ�����" onClick="javascript:cust_price('<bean:write property="customerId" name="salesInfoForm" />')">
      <input type="button"  value="���۵���ӡ" onClick="javascript:f_print()">
    </td>
  </tr>
</table>
<br>

<%if("-1".equals(tag)){%>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
<tr><td align="left">
<strong><font color="red">�����������δ��д�Ľ��</font></strong>
</td></tr>
</table>		
<%}else if("mergeComplete".equals(tag)){%>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
<tr><td align="left">
<strong><font color="blue">�ϲ���ϣ�ԭѯ�۵��Զ�ɾ��</font></strong>
</td></tr>
</table>		
<%}%>

<%if(vtrData!=null&&!vtrData.isEmpty()){ %>
<table border="1" width="100%" align="center" id="tblsales" name="tblsales">
	<tr>
		<td align="center" rowspan="2">
		<p align="center">�Ϻ�</td>
		<td align="center" rowspan="2" >
		<p align="center">����</td>
		<td colspan="2" align="center">����(��)</td>
		<td colspan="2" align="center">��˰</td>
		<td align="center">������˰</td>
		<td align="center" rowspan="2">�����</td>
		<td colspan="2" align="center">�����</td>
		<td colspan="2" align="center">�ɱ�</td>
		<td colspan="2" align="center">���۽��</td>
		<td colspan="2" align="center">����</td>
	</tr>
	<tr>
	 	<td>����</td>
	 	<td>����</td>
		<td>���</td>
		<td>˰��</td>
		<td>���</td>
		<td>�ƻ�</td>
		<td><font color="#0000FF">ʵ��</font></td>
		<td>�ƻ�</td>
		<td><font color="#0000FF">ʵ��</font></td>
		<td>����(��)</td>
		<td><font color="#0000FF">ʵ���տ�</font></td>
		
		<td>�ƻ�</td>
		<td><font color="#0000FF">ʵ��</font></td>
	</tr>

<%
		for(int i=0;i<vtrData.size();i++){
				String[] temp=(String[])vtrData.get(i);
%>
	<tr>
		
		<td title="<%=temp[3]%>"><%=temp[4]%><input name="detailIds" type="hidden" value="<%=temp[0]%>"><input name="version" type="hidden" value="<%=temp[23]%>"> </td>
		<td size="10"><%=temp[5]%><input type="hidden" name="partNumT" value="<%=temp[5]%>"  ></td>
		
		<td ><%=temp[10]%><input type="hidden" name="purchasePriceT" value="<%=temp[10]%>"   ></td>
		<td><%=temp[30]%><input type="hidden" name="stockCostT" value="<%=temp[30]%>"   ></td>
		<td><input class="form" name="tariffAmountT" value="<%=temp[24]%>" size="6" readonly ></td>		
		<td><input class="form" name="tariffRatT" value="<%=temp[25]%>" size="2" onkeydown="javascript:f_onlymoney();" maxlength="10" onblur="f_rate(this)">%</td>		
		<td><input class="form" name="vatT" value="<%=temp[11]%>" size="6" onkeydown="javascript:f_onlymoney();" maxlength="10" onblur="f_math(this)"></td>
		<td><input class="form" name="customsChargesRealT" value="<%=temp[13]%>" size="6" onkeydown="javascript:f_onlymoney();" maxlength="10" onblur="f_math(this)"></td>
		<td><%=temp[14]%><input type="hidden" name="domesticFreightPlanT" value="<%=temp[14]%>" size="6" onkeydown="javascript:f_onlymoney();" maxlength="10" onblur="f_math(this)"></td>
		<td><input class="form" name="domesticFreightRealT" value="<%=temp[15]%>" size="6" onkeydown="javascript:f_onlymoney();" maxlength="10" onblur="f_math(this)"></td>

		<td><%=temp[16]%><input type="hidden" name="costPlanT" value="<%=temp[16]%>" size="6" onkeydown="javascript:f_onlymoney();" maxlength="10" readonly ></td>
		<td><input class="form" name="costRealT" value="<%=temp[17]%>" size="6" onkeydown="javascript:f_onlymoney();" maxlength="10" readonly ></td>
	
		<td><%=temp[6]%><input type="hidden" name="totalQuoteT" value="<%=temp[6]%>" size="6" onkeydown="javascript:f_onlymoney();" maxlength="10" onblur="f_math(this)" onchange="f_chg_flag()"></td>
		<td>&nbsp;<%-- <input class="form" name="saleTotalPriceT" value="<%=temp[18]%>" size="6" onkeydown="javascript:f_onlymoney();" maxlength="10" onblur="f_math(this)" > --%></td>
	
		<td><%=temp[19]%><input type="hidden" name="profitPlanT" value="<%=temp[19]%>" size="6" onkeydown="javascript:f_onlymoney();" maxlength="10" readonly ></td>
		<td>&nbsp;<%-- <input class="form" name="profitRealT" value="<%=temp[20]%>" size="6" onkeydown="javascript:f_onlymoney();" maxlength="10" readonly > --%></td>
	
	</tr>
<%}%>
	

	<tr>
		<td>��</td>
		<td>��</td>
		<td>��</td>
		<td>��</td>
		<td>��</td>
		<td>��</td>
		<td>��</td>
		<td>��</td>
		<td>��</td>
		<td>��</td>
		<td>��</td>
		<td>��</td>
		<td>��</td>
		<td >��</td>
		<td >��</td>
		<td >��</td>
	</tr>
	<tr>
		<td>�ϼ�</td>
		<td id="partNum"><bean:write property="partNum" name="salesInfoForm" /></td><input name="partNum" type="hidden" value="<bean:write property='partNum' name='salesInfoForm' />" >
		<td id="purchasePrice"><bean:write property="purchasePrice" name="salesInfoForm" /></td><input name="purchasePrice" type="hidden" value="<bean:write property='purchasePrice' name='salesInfoForm' />">
		<td id="stockCost"><bean:write property="stockCost" name="salesInfoForm" /></td><input name="stockCost" type="hidden" value="<bean:write property='stockCost' name='salesInfoForm' />">
		<td id="tariffAmount"><bean:write property="tariffAmount" name="salesInfoForm" /></td><input name="tariffAmount" type="hidden" value="<bean:write property='tariffAmount' name='salesInfoForm' />">
		<td id="tariffRat">&nbsp;</td>
		<td id="vat"><bean:write property="vat" name="salesInfoForm" /></td><input name="vat" type="hidden" value="<bean:write property='vat' name='salesInfoForm' />">
		<td id="customsChargesReal"><bean:write property="customsChargesReal" name="salesInfoForm" /></td><input name="customsChargesReal" type="hidden" value="<bean:write property='customsChargesReal' name='salesInfoForm' />">
		<td id="domesticFreightPlan"><bean:write property="domesticFreightPlan" name="salesInfoForm" /></td><input name="domesticFreightPlan" type="hidden" value="<bean:write property='domesticFreightPlan' name='salesInfoForm' />">
		<td id="domesticFreightReal"><bean:write property="domesticFreightReal" name="salesInfoForm" /></td><input name="domesticFreightReal" type="hidden" value="<bean:write property='domesticFreightReal' name='salesInfoForm' />">
		<td id="costPlan"><bean:write property="costPlan" name="salesInfoForm" /></td><input name="costPlan" type="hidden" value="<bean:write property='costPlan' name='salesInfoForm' />">
		<td id="costReal"><bean:write property="costReal" name="salesInfoForm" /></td><input name="costReal" type="hidden" value="<bean:write property='costReal' name='salesInfoForm' />">
		<td id="totalQuote"><bean:write property="totalQuote" name="salesInfoForm" /></td><input name="totalQuote" type="hidden" value="<bean:write property='totalQuote' name='salesInfoForm' />">
		<td>��<bean:write property='totalPay' name='salesInfoForm' /></td>
		<td id="profitPlan"><bean:write property="profitPlan" name="salesInfoForm" /></td><input name="profitPlan" type="hidden" value="<bean:write property='profitPlan' name='salesInfoForm' />">
		<td id="profitReal"><bean:write property="profitReal" name="salesInfoForm" /></td><input name="profitReal" type="hidden" value="<bean:write property='profitReal' name='salesInfoForm' />">
	</tr>
	<tr>
		<td>��</td>
		<td  align="right" colspan="11">��ֵ˰��</td>
		<td  id="vat17"</td>
		<td colspan="3" id="vatReal">&nbsp;��</td>
	</tr>
	<tr>
		<td >��</td>
		<td align="right" colspan="11">˰��ϼƣ�</td>
		<td  id="quoteAll"></td>
		<td id="totalPay"><bean:write property="totalPay" name="salesInfoForm" /></td><input name="totalPay" type="hidden" value="<bean:write property='totalPay' name='salesInfoForm' />">
		<td >&nbsp;��</td>
		<td id="profitRealWithoutVat">&nbsp;��</td>
	</tr>
</table>
 <%} %>

		
<%if(repairFeeList!=null){ %>
<table width="100%" border="0" align="center" cellpadding="2" cellspacing="1" class="content12">  
<tr>
    <td>ά�޷���</td>
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
     	<td>&nbsp;</td>
		<td>����</td>
		<td title="����˹�ʱ">��ʱ</td>
		<td>���÷�</td>
		<td>����Ʊ</td>
		<td>�˹���</td>
		<td>˰��</td>
		<td>�ܳɱ�</td>
		<td>ά�ޱ���</td>
		<td width="15%">ά������</td>
	</tr>

<%
		for(int i=0;i<repairFeeList.size();i++){
			RepairFeeInfoForm rfi=(RepairFeeInfoForm)repairFeeList.get(i);
%>
	<tr>
		<td><%=DicInit.getSystemName("REPAIR_FEE_TYPE",rfi.getFeeType())%></td>
		<td id="repairmanNum"><%=rfi.getRepairmanNum()%> </td>	
		<td id="workingHours"><%=rfi.getWorkingHours() %></td>	
		<td id="travelCosts"><%=Operate.toFix(rfi.getTravelCosts(),2) %></td>
		<td id="ticketsAllCosts"><%=Operate.toFix(rfi.getTicketsAllCosts(),2) %></td>		
		<td id="laborCosts"><%=Operate.toFix(rfi.getLaborCosts(),2) %></td>	
		<td id="taxes"><%=Operate.toFix(rfi.getTaxes(),2) %></td>
		<td id="totalCost"><%=Operate.toFix(rfi.getTotalCost(),2) %></td>
		<td id="repairQuote">&nbsp;<%=Operate.toFixD(rfi.getRepairQuote(),2) %>
			<%if(i==0){ %>
			<input name="repairQuote" type="hidden" value="<%=Operate.toFixD(rfi.getRepairQuote(),2) %>">
			<%} %>
		</td>
		<td id="repairProfit">&nbsp;<%=Operate.toFixD(rfi.getRepairProfit(),2) %></td>
		
	
	<%} %>
	</tr>
		<tr>
		<td>��</td>
		<td  align="right" colspan="7">��ֵ˰����</td>
		<td colspan="2" id="vat17Repair"></td>
	</tr>
	<tr>
		<td>��</td>
		<td  align="right" colspan="7">ά�޺ϼƣ���</td>
		<td colspan="2" id="quoteAllRepair"</td>
	</tr>
 </table>
 <%} %>
 <br>

<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td align="left">
    	<%if("T".equals(sif.getSaleStatus())||AtomRoleCheck.checkRole(userId, "MANAGER")){%>
    	<input type="button" name="bt" value="�ݴ�" onClick="javascript:f_order_confirm('saveEnd')">
   		<input type="button" name="bt" value="����ȷ��" onClick="javascript:f_order_confirm('confirmEnd')"></td>
   		<%}%>
  </tr>
</table>
</html:form>

</body>
</html>
<SCRIPT>
	function f_print(){
		//���ۡ����� ��Ϊ�գ�����뱣���
		if(f_chk_flag()){
				window.open("saleInfoAction.do?method=salePartsPrint&repairNo=<%=sif.getRepairNo()%>&flag=quotePrint&custId=<bean:write property="customerId" name="salesInfoForm" />&saleNo="+document.forms[0].saleNo.value);
		}
	}
	
	
	function cust_price(customerId){
    	window.open("saleInfoAction.do?method=custPrice&customerId="+customerId,"","width=700,height=300,left=50,top=10,menubar=no,toolbar=no,resizable=yes,scrolling=yes,scrollbars=yes");
	}
	
	
	
	
	function f_rate(obj){
		var len=document.forms[0].tariffRatT.length;
		if(len>1){
				for(var i=0;i<len;i++){
						var tObj=document.forms[0].tariffRatT[i];
						if(tObj==obj){
								var trt=document.forms[0].tariffRatT[i].value;
								var ppt=document.forms[0].stockCostT[i].value;
								document.forms[0].tariffAmountT[i].value=(new Number(ppt) * new Number(trt) / 100).toFixed(2);
	
								f_math_cost(obj);
								f_math_total(document.forms[0].tariffAmountT[i]);
								//f_cg_profit();
						}
				}
		}else{
				var trt=document.forms[0].tariffRatT.value;
				var ppt=document.forms[0].stockCostT.value;
				document.forms[0].tariffAmountT.value=(new Number(ppt) * new Number(trt) / 100).toFixed(2);
				
				f_math_cost(obj);
				f_math_total(document.forms[0].tariffAmountT);
				//f_cg_profit();
		}
	}
	
	
	/**
	 * �ɱ��������
	 * @version 1.0.2010.0619
 	 */
	function f_math(obj){
			if(obj.name=="tariffAmountT"){
				var len=document.forms[0].stockCostT.length;
				if(len>1){
					for(var i=0;i<len;i++){
							var tObj=document.forms[0].tariffAmountT[i];
							if(tObj==obj){
							
									var ppt=document.forms[0].stockCostT[i].value;
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
					var ppt=document.forms[0].stockCostT.value;
					var tat=document.forms[0].tariffAmountT.value;
					if(ppt!=0){
						document.forms[0].tariffRatT.value=(new Number(tat) / new Number(ppt) * 100).toFixed(2);
					}else{
						document.forms[0].tariffRatT.value=0;
					}
					
					document.forms[0].vatT.value= ((new Number(tat)+new Number(ppt))*0.17).toFixed(2);
				}
			}
			
			//�ɱ�&����
			f_math_cost(obj);
			//�ܼ�
			f_math_total(obj);
			//������
			//f_cg_profit();
		
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
	 * ���ɱ�=���� + ��˰���� + ������˰ + ����� + ����� (costPlan = purchasePrice + tariffAmount + vat + customsChargesReal + domesticFreightReal)
	 * ������=����-�ɱ� (profitPlan = perQuote - costPlan )
	 * @version 1.0.2010.0619
 	 */
	function f_math_cost(obj){
			var perName=obj.name;
			var len = eval("document.forms[0]."+perName).length;
			if(len>1){
					for(var i=0;i<len;i++){
							var tObj=eval("document.forms[0]."+perName+"["+i+"]");
							
							if(tObj==obj){
									//���ɱ� - �ƻ�
									var ppT=new Number(document.forms[0].purchasePriceT[i].value);
									var taT=new Number(document.forms[0].tariffAmountT[i].value);
									var vatV=new Number(document.forms[0].vatT[i].value);
									var ccpP=new Number(document.forms[0].customsChargesRealT[i].value);
									var dfpP=new Number(document.forms[0].domesticFreightPlanT[i].value);
									//���ɱ� - ʵ��
									var dfpR=new Number(document.forms[0].domesticFreightRealT[i].value);
									var sct=new Number(document.forms[0].stockCostT[i].value);
									
									var cP=ppT + taT + vatV + ccpP + dfpP;	//�ƻ�
									var cR=sct + taT + vatV + ccpP + dfpR;	//ʵ��

									//document.forms[0].costPlanT[i].value=cP.toFixed(2);	
									document.forms[0].costRealT[i].value=cR.toFixed(2);
									//f_math_total(document.forms[0].costPlanT[i]);
									f_math_total(document.forms[0].costRealT[i]);
									
									//������ - �ƻ�
									if(document.forms[0].totalQuoteT[i].value!=''){
										var tqP=new Number(document.forms[0].totalQuoteT[i].value);
										var pfP=tqP-cP;	//�ƻ�
										document.forms[0].profitPlanT[i].value=pfP.toFixed(2);
									}else{
										document.forms[0].profitPlanT[i].value='';
									}
									//������ - ʵ��
									//if(document.forms[0].saleTotalPriceT[i].value!=''){
									//	var tqR=new Number(document.forms[0].saleTotalPriceT[i].value);
									//	var pfR=tqR-cR;	//ʵ��
									//	document.forms[0].profitRealT[i].value=pfR.toFixed(2);
									//}else{
									//	document.forms[0].profitRealT[i].value='';
									//}
							}
					}
			} else {
					//���ɱ� - �ƻ�
					var ppT=new Number(document.forms[0].purchasePriceT.value);
					var taT=new Number(document.forms[0].tariffAmountT.value);
					var vatV=new Number(document.forms[0].vatT.value);
					var ccpP=new Number(document.forms[0].customsChargesRealT.value);
					var dfpP=new Number(document.forms[0].domesticFreightPlanT.value);
					//���ɱ� - ʵ��
					var dfpR=new Number(document.forms[0].domesticFreightRealT.value);
					var sct=new Number(document.forms[0].stockCostT.value);
					
					var cP=ppT + taT + vatV + ccpP + dfpP;	//�ƻ�
					var cR=sct + taT + vatV + ccpP + dfpR;	//ʵ��
					//document.forms[0].costPlanT.value=cP.toFixed(2);	
					document.forms[0].costRealT.value=cR.toFixed(2);
					
					//f_math_total(document.forms[0].costPlanT);
					f_math_total(document.forms[0].costRealT);
									
					
					//������ - �ƻ�
					if(document.forms[0].totalQuoteT.value!=''){
						var tqP=new Number(document.forms[0].totalQuoteT.value);
						var pfP=tqP-cP;	//�ƻ�
						document.forms[0].profitPlanT.value=pfP.toFixed(2);
					}else{
						document.forms[0].profitPlanT.value='';
					}
					//������ - ʵ��
					//if(document.forms[0].saleTotalPriceT.value!=''){
					//	var tqR=new Number(document.forms[0].saleTotalPriceT.value);
					//	var pfR=tqR-cR;	//ʵ��
					//	document.forms[0].profitRealT.value=pfR.toFixed(2);
					//}else{
					//	document.forms[0].profitRealT.value='';
					//}
					
			}
	}
	
	/**
	 * ������=�ܱ���-�ܳɱ�-ά�޷� (profitPlan = totalQuote - costPlan - repairFeePlan )
	 * 2010-10-08  ����ά�޷���ͻ���ȡ���������ٿ۳�ά�޷�
	 * @version 1.0.2010.0619
 	 */
	function f_cg_profit(){
			//var pqP=new Number(document.forms[0].totalQuote.value);			//�ܱ���
			//var cpP=new Number(document.forms[0].costPlan.value);				//�ܳɱ�_�ƻ�	
			
			var spP=new Number(document.forms[0].totalPay.value);			//�����ܼ�
			var cR=new Number(document.forms[0].costReal.value);			//�ɱ�_ʵ��	

			//var rfP=new Number(document.forms[0].repairFeePlan.value);	//ά�޷�_�ƻ�
			//var rfR=new Number(document.forms[0].repairFeeReal.value);	//ά�޷�_ʵ��
			
			//document.forms[0].profitPlan.value=(pqP-cpP).toFixed(2);
			document.forms[0].profitReal.value=(spP-cR).toFixed(2);
			//profitPlan.innerHTML=document.forms[0].profitPlan.value;
			profitReal.innerHTML=document.forms[0].profitReal.value;
			
			
			var ttp=new Number(document.forms[0].totalPay.value);
			var vatR = (ttp/1.17 * 0.17).toFixed(2);
			vatReal.innerHTML=vatR;
			
			var pfR = new Number(document.forms[0].profitReal.value);
			profitRealWithoutVat.innerHTML= (pfR - vatR).toFixed(2);
	}
	
	/**
	 * �ܼ� = ����1 * ���� + ����2 * ���� + ����
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
			
			f_cg_profit();
			init_vat17();
	}
	
	
	
	
	function f_order_confirm(flag){
			if(flag=='confirmEnd'&&f_chk()){
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

		moneyVal[0]=document.forms[0].tariffAmountT;
		moneyVal[1]=document.forms[0].tariffRatT;
		moneyVal[2]=document.forms[0].vatT;
		moneyVal[3]=document.forms[0].customsChargesRealT;
		moneyVal[4]=document.forms[0].domesticFreightRealT;
		moneyVal[5]=document.forms[0].costRealT;

		for(var x=0;x<moneyVal.length;x++){
    	var len = moneyVal[x].length;
			if(len>1){
					for(var i=0;i<len;i++){
							if(moneyVal[x][i].value==''){
									moneyVal[x][i].focus();
									alert("�������");
									return true;
							}
					}
			} else {
					if(moneyVal[x].value==''){
							moneyVal[x].focus();
							alert("�������");
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
				alert("������ά�޷��ã�");
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
							alert("�����뱨�ۣ�");
							return false;
					}
				}
		}else {
				if(quoteP.value==''){
						quoteP.focus();
						alert("�����뱨�ۣ�");
						return false;
				}
		}
		
		if(!chgFlag){
				alert("�����ݴ����ݣ�");
				return false;
		}
		<%}%>
		return true;
	}
	
	
	function init_vat17(){
	
		<%if(vtrData!=null&&!vtrData.isEmpty()){ %>
			var tq=new Number(document.forms[0].totalQuote.value);
			vat17.innerHTML= (tq* 0.17).toFixed(2);
			quoteAll.innerHTML=(tq * 1.17).toFixed(2);
		<%}%>
		<%if(repairFeeList!=null){ %>
			var rq=new Number(document.forms[0].repairQuote.value);
			vat17Repair.innerHTML=(rq* 0.17).toFixed(2);
			quoteAllRepair.innerHTML=(rq * 1.17).toFixed(2);
		<%}%>

	}
	
	function f_math_num(obj){
		
			//f_cout_num();
		
			//f_math_cost(obj);
			
			var moneyVal = new Array();
			moneyVal[0]=document.forms[0].tariffAmountT;
			moneyVal[1]=document.forms[0].vatT;
			moneyVal[2]=document.forms[0].customsChargesRealT;
			moneyVal[3]=document.forms[0].domesticFreightRealT;
			moneyVal[4]=document.forms[0].costRealT;
			moneyVal[5]=document.forms[0].stockCostT;
			
			
			var len = document.forms[0].tariffRatT.length;
			for(var i=0;i<moneyVal.length;i++){
				
				if(len>1){
						f_math_total(moneyVal[i][0]);
				}else{
						f_math_total(moneyVal[i]);
				}
			}
			//f_cg_profit();
			
		
	}
	
	
	
	
</SCRIPT>
