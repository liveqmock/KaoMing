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
<title>��������ѯ</title>
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
		//����󣬸���ǰ
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
    <td>��������嵥&gt;��ϸ</td>
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
    <td > ���۵����룺</td>
    <td ><bean:write property="saleNo" name="salesInfoForm" /></td>
    <td > �ͻ����ƣ�</td>
    <td><bean:write property="customerName" name="salesInfoForm" /></td>
    <td > �ͻ��绰��</td>
    <td ><bean:write property="customerPhone" name="salesInfoForm" /></td>
    <td>���ڣ�</td>
    <td><bean:write property="orderMonth" name="salesInfoForm" /></td>
    <td> ���뵥״̬��</td>
    <td> <font color="#FF6600"><%=DicInit.getSystemName("SALE_STATUS", sif.getSaleStatus())%></font></td>
  </tr>
  <tr class="tableback2">
    
    <td>������ۣ�</td>
    <td><%=sif.getTotalQuote()==null?"":Operate.toFix(sif.getTotalQuote()*1.17,2) %></td>
    <td>�������</td>
    <td><bean:write property="profitPlan" name="salesInfoForm" /></td>
    <td>ά�ޱ��ۣ�</td>
    <td><bean:write property="repairQuote" name="salesInfoForm" /></td>
    <td>ά������</td>
    <td><bean:write property="repairProfit" name="salesInfoForm" /></td>
    <td>�ܱ��ۣ�</td>
    <td><%=totalQuteWithTax %></td>
    
    
    
  </tr>
  <tr class="tableback1">
  	<td width="8%"> ά�޵��ţ�</td>
    <td width="15%"><bean:write property="serviceSheetNo" name="salesInfoForm" />
    	<%if("I".equals(sif.getWarrantyType())){ %> <font color="red">(����)</font><%} %>
    </td>
    <td>��ע��</td>
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
			<!--<input type="button"  value="��ӡ��Ʊ" onclick="javascript:printInvoince()">-->
    </td>
  </tr>
</table>
<br>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="content12">
  <tr bgcolor="#CCCCCC"> 
    <td><strong>�Ϻ�</strong></td>
    <td><strong>�������</strong></td>
    <td><strong>����</strong></td>
    <td><strong>�������</strong></td>
    <td><strong>����</strong></td>
    <td><strong>��Դ</strong></td>
    <td><strong>����</strong></td>
    <td><strong>�ɱ�������</strong></td>
    <td><strong>���ۼۣ�����</strong></td>
    <td><strong>���󣨵���</strong></td>
    <td><strong>״̬</strong></td>
    <td  align="center" ><strong>����</strong></td>
    <td><strong>ȡ��</strong></td>
    
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
          <td align="center"><a href="javascript:up_affix(<%=temp[0]%>)" style="cursor: hand"><img src="googleImg/icon/writely.gif" border=0 title="����" ></a></td>          
          <td ><input type="button"  value="ȡ��" onclick="f_cancel('<%=temp[0]%>',this)" <%=dsb%>></td>
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
         <td>�տ��<font color="#FF6600">*</font></td>
         <td ><input name="payAmount" type="text" class="form" size="20" maxlength="12" onkeydown="javascript:f_onlymoney();" ></td>
         <td>�տ����ͣ�<font color="#FF6600">*</font></td>
         <td ><select class="form" name="payVariety"  >	
					<%for(int i=0;payVarietyList!=null&&i<payVarietyList.size();i++){
                  	String[] temp=(String[])payVarietyList.get(i);
                	%>
                		<option value="<%=temp[0]%>" ><%=temp[1]%></option>
                 	<%}%>
      						</select></td>
      						
      	 <td>�տʽ��<font color="#FF6600">*</font></td>
         <td ><select class="form" name="payType" onchange="f_chg_pt(this.value)" >	
					<%for(int i=0;payTypeList!=null&&i<payTypeList.size();i++){
                  	String[] temp=(String[])payTypeList.get(i);
                	%>
                		<option value="<%=temp[0]%>" ><%=temp[1]%></option>
                 	<%}%>
      						</select></td>
        </tr>
        <tr class="tableback1"> 
         <td width="10%">�ͻ����У�</td>
         <td width="20%" ><input name="clientBank" type="text" class="form" size="20" maxlength="32"></td>
         <td width="20%">�ͻ�֧Ʊ/ת�˺��룺</td>
         <td width="20%" ><input name="paymentCardNo" type="text" class="form" size="30" maxlength="50"></td>
         <td width="10%" > </td>
         <td width="20%" ></td>
        </tr>
  			
        <tr >
         <td colspan="6"><input name="addPaymentButton" type="button"  value="ȷ���շ�" onclick="addPayment()"></td>
        </tr>
        
       </table>
	   <br>
<%}%>	   
    <table width="100%" border="0" align="center" cellpadding="2" cellspacing="1" class="content12">
   
	    <tr> 
         <td>�ͻ�Ӧ���ܶ</td>
         <td id="USER_NEED_PAY_TOTAL_TD"><font color="blue"><B><%=totalQuteWithTax %></B></font></td>
         <td>�ͻ��Ѹ��ܶ</td>
         <td id="USER_PAID_TD"><font color="green"><B></B></font></td>
         <td>�ͻ����踶��</td>
         <td id="USER_NEED_PAY_TD"><font color="red"><B></B></font></td>
        </tr>
         <tr> 
		   <td height="3" colspan="10" background="<%= request.getContextPath()%>/images/i_line.gif"></td>
		</tr>
	   </table>
	  
	<table  width="100%" border="0" align="center" cellspacing="2" cellpadding="1" class="content12" border="0" id="paidParentTable">
	 <tr>
    <td><B>�տ���Ϣ</B></td>
     </tr>
	<tr><td width="100%">
	<div class="scrollDiv" id="PAIDScrollDiv">
       <table width="100%" border="0" cellpadding="0" cellspacing="1" class="content12" id="paidTable">
	    <THEAD>
        <tr bgcolor="#CCCCCC">
         <td><B>�տ�����</B></td>
         <td><B>�տ���</B></td>
         <td><B>�տ�����</B></td>
         <td><B>�տʽ</B></td>
         <td><B>�ͻ�����</B></td>
         <td><B>֧Ʊ/ת�˺���</B></td>
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
         <td width="6%" align="center"><input name="delPartButton" type="button" class="button2" value="ɾ��" onclick="deleteAPart('PART_ROW_<%=temp[0]%>')"></td>
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
         <td>��Ʊ�����<font color="#FF6600">*</font></td>
         <td ><SELECT name="stuffNo" class="form">
      		<%
      		for(int i=0;vtrData!=null&&i<vtrData.size();i++){
      			String[] temp=(String[])vtrData.get(i);
      		%>
      		<option value="<%=temp[4]%>" ><%=temp[3]%></option>
      		<%}%>
      		<%if(sif.getRepairFeePlan()!=null){ %>
      		<option value="RepairFee" >ά�޷�</option>
      		<%} %>
        	</SELECT></td>
         <td>��Ʊ��<font color="#FF6600">*</font></td>
         <td ><input name="billingMoney" type="text" class="form" size="20" onkeydown="javascript:f_onlymoney();" ></td>
         
         <input name="invoiceType" type="hidden" value="V">
         
      	 <td>��Ʊ�ţ�<font color="#FF6600">*</font></td>
         <td ><input name="invoiceNo" type="text" class="form" size="20" style="text-transform:uppercase" onblur="this.value=this.value.toUpperCase()"></td>					
        </tr>
        
  			
        <tr >
         <td colspan="6"><input name="addInvoiceButton" type="button"  value="ȷ�Ͽ�Ʊ"  onclick="addInvoice()"></td>
        </tr>
        
       </table>
       <%}%>
	<table  width="100%" border="0" align="center" cellspacing="2" cellpadding="1" class="content12" border="0" id="invoiceParentTable">
	<tr> 
		<td height="3" colspan="10" background="<%= request.getContextPath()%>/images/i_line.gif"></td>
	</tr>
	 <tr>
    <td><B>��Ʊ��Ϣ</B></td>
     </tr>
	<tr><td width="100%">
	<div class="scrollDiv" id="InvoiceScrollDiv">
       <table width="100%" border="0" cellpadding="0" cellspacing="1" class="content12" id="invoiceTable">
	    <THEAD>
        <tr bgcolor="#CCCCCC">
         <td><B>��Ʊ����</B></td>
         <td><B>��Ʊ���</B></td>
         <td><B>��Ʊ���</B></td>
         <td><B>��Ʊ����</B></td>
         <td><B>��Ʊ��</B></td>
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
			if(payTp=='B'){	//�ֽ�
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

			if(f_isNull(document.forms[0].payAmount,'���ѽ��')&&f_isNull(document.forms[0].payType,'���ѷ�ʽ')&&f_isNull(document.forms[0].payVariety,'��������')){
				if(document.forms[0].payType.value!='B'){
						if(f_isNull(document.forms[0].clientBank,'�ͻ�����')&&f_isNull(document.forms[0].paymentCardNo,'�ͻ�֧Ʊ/ת�˺���')){
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
				
				alert("�������");
		}else{
				alert("����Ӹ��Ѽ�¼ʱ�쳣������ϵ����Ա");
		}
		document.forms[0].addPaymentButton.disabled=false;
	}
	
	function addPaidTr(allPayAmount,paymentDate,payAmount,payType,payVariety){
		//alert("���� addpaidtr");
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
	
	function addUserPaidTd(allPayAmount){	//�����Ѹ�����Ϣ
		
		var balanceDue = new Number(<%=totalQuteWithTax %>)-new Number(allPayAmount);
		
		USER_PAID_TD.innerHTML = "<font color=\"green\"><B>"+new Number(allPayAmount).toFixed(2)+"</B></font>";
		//alert(<bean:write property="totalQuote" name="salesInfoForm" />+"-"+allPayAmount+"="+balanceDue);
		if(balanceDue<0) balanceDue=0;
		USER_NEED_PAY_TD.innerHTML = "<font color=\"red\"><B>"+balanceDue.toFixed(2)+"</B></font>";
		NEED_PAY=balanceDue.toFixed(2);
		
	}
	
	
	var ajaxInvoice = new sack();
	function addInvoice(){
			
			if(f_isNull(document.forms[0].billingMoney,'��Ʊ���')&&f_isNull(document.forms[0].invoiceNo,'��Ʊ��')){
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
				
				alert("��Ʊ���");
		}else{
				alert("����ӿ�Ʊ��¼ʱ�쳣������ϵ����Ա");
		}
		document.forms[0].addInvoiceButton.disabled=false;
	}
	
	function f_cancel(detailId,obj){
		if(confirm("ȷ��ȡ���������")){
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
		var feeId = partsIdTRRow.substr(9);// ��9��ʼ������Ϊǰ����"PART_ROW_"��ͷ
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
			alert("ɾ��ʧ�ܣ�����ϵ����Ա��");
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
