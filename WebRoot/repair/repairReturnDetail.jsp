<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri=  "/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri=  "/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri=  "/WEB-INF/tlds/common.tld" prefix="comtld" %>
<%@ page import="java.util.*"%> 
<%@ page import="com.dne.sie.repair.form.*"%>
<%@ page import="com.dne.sie.common.tools.*"%>
<%@ page import="com.dne.sie.maintenance.form.AttachedInfoForm"%>
<%@ page import="com.dne.sie.maintenance.form.CustomerInfoForm"%>

<%
	
try{
	String strTr=""; // Table row change color string
	RepairServiceForm rsf = (RepairServiceForm)request.getAttribute("repairServiceForm");
		
	ArrayList repairAttachment = (ArrayList)request.getAttribute("repairAttachment");
	List partsList = (List)request.getAttribute("partsList");
	List loanList = (List)request.getAttribute("loanList");
	List toolsList = (List)request.getAttribute("toolsList");
	List repairManList = (List)request.getAttribute("repairManList");
	//List feeList = (List)request.getAttribute("feeList");
	
	CustomerInfoForm rcif = rsf.getCustomInfoForm();
	
	Set repairManSetInfoSet = rsf.getRepairManSetInfo();
	Set serviceStatusSet = rsf.getServiceStatusSet();
	
	//List urList=(ArrayList) DicInit.SYS_CODE_MAP.get("PAGE_UNREPAIR_REASON");
	//List unQucikStatusList=(ArrayList) DicInit.SYS_CODE_MAP.get("UNCOMPLETE_QUICK_STATUS");
	//List crReasonList=(ArrayList) DicInit.SYS_CODE_MAP.get("CR_REASON");
	//List unQuickReasonList=(ArrayList) DicInit.SYS_CODE_MAP.get("UNQUICK_REASON");
	List repairConditionList=(ArrayList) DicInit.SYS_CODE_MAP.get("REPAIR_CONDITION");
	
%>
<html>
<head>
<title>�ɹ���ϸ</title>
<link href="<%= request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/css/autocomplete.css" /> 
<script language=javascript src="<%= request.getContextPath()%>/js/ajax.js"></script>
<script language=javascript src="<%= request.getContextPath()%>/js/common.js"></script>
<script language=javascript src="<%= request.getContextPath()%>/js/popvpl.js"></script>
<script language=javascript src="<%= request.getContextPath()%>/js/jquery.js"></script>
<script language=javascript src="<%= request.getContextPath()%>/js/popPartInfo.js"></script>
<script language=javascript src="<%= request.getContextPath()%>/js/popCalendar.js"></script>
<script language=javascript src="<%= request.getContextPath()%>/js/checkValid.js"></script>
<script language=javascript src="<%= request.getContextPath()%>/js/inputValidation.js"></script>
<script language=javascript src="<%= request.getContextPath()%>/js/repair.js"></script>
<SCRIPT language="JScript.Encode" src="js/commonSelect.js"></SCRIPT>
<script language="JavaScript" type="text/JavaScript">
<!--
function highlightButton(s) {
	if ("INPUT"==event.srcElement.tagName)
		event.srcElement.className=s;
}
function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}
function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
function MM_openBrWindow(theURL,winName,features) { //v2.0
  window.open(theURL,winName,features);
}
function MM_showHideLayers() { //v6.0
  var i,p,v,obj,args=MM_showHideLayers.arguments;
  for (i=0; i<(args.length-2); i+=3) if ((obj=MM_findObj(args[i]))!=null) { v=args[i+2];
    if (obj.style) { obj=obj.style; v=(v=='show')?'visible':(v=='hide')?'hidden':v; }
    obj.visibility=v; }
}

function repeatedReceivePrint(id,printType){//���´�ӡ�ӻ���
  window.open("receiveAction.do?method=repeatedReceivePrint&repairNo="+id+"&repeate=true&printType="+printType,"","height=700,width=860,scrollbars=yes");
}

var repairMans = "";
var ajaxChk = new sack();
function returnEnd(){
	if(!f_chk()&&!f_chkAjaxAdd()){
		ajaxChk.setVar("repairNo", document.forms[0].repairNo.value);
		ajaxChk.setVar("method", "checkReturnPart");
		ajaxChk.requestFile = "repairHandleAction.do";
		ajaxChk.method = "GET";
		ajaxChk.onCompletion = repairReturnEnd;
		ajaxChk.runAJAX();
	}
}

function repairReturnEnd(){
	var returnXml = ajaxChk.responseXML;
	var result = returnXml.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(result=="broken"){
		alert("�л���δ������");
	}else if(result=="loanPart"){
		alert("��Я�����δ������");
	}else if(result=="tool"){
		alert("��Я������δ������");
	}else if(result=="succ"){
	
		if(confirm("ȷ������������")){
			showWaitDiv(800,1000);
			document.forms[0].action="repairHandleAction.do?method=repairReturnEnd";
			document.forms[0].submit();	
		}
	}else{
		alert("У���������ϵ����Ա��");
	}
}


	function f_chk(){ 
		var moneyVal = new Array();

		moneyVal[0]=document.forms[0].arrivalDate;
		moneyVal[1]=document.forms[0].returnDate;
		moneyVal[2]=document.forms[0].travelFee;
		moneyVal[3]=document.forms[0].laborCosts;

		for(var x=0;x<moneyVal.length;x++){
    		var len = moneyVal[x].length;
			if(len>1){
				for(var i=0;i<len;i++){
						if(moneyVal[x][i].value==''){
							moneyVal[x][i].focus();
							alert("���������ݣ�");
							return true;
						}else if(x<=1){
							if(!checkInputDate(moneyVal[x][i])){
								MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','show','Layer8','','hide');
								moneyVal[x][i].focus();
								return true;
							}
						}
				}
			} else {
				if(moneyVal[x].value==''){
					moneyVal[x].focus();
					alert("���������ݣ�");
					return true;
				}else if(x<=1){
					if(!checkInputDate(moneyVal[x])){
						MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','show','Layer8','','hide');
						moneyVal[x].focus();
						return true;
					}
				}
			}
			
		}
		
		
		return false;
	}



var ajaxD2 = new sack();
function tranAPart(partsId,version){
	if(confirm("ȷ��Ҫ��������ת������")){
		ajaxD2.setVar("partsId",partsId);
		ajaxD2.setVar("version",version);
		ajaxD2.setVar("method", "transferLoanPart");
		ajaxD2.requestFile = "repairHandleAction.do";
		ajaxD2.method = "GET";
		ajaxD2.onCompletion = tranLoanPartCompleted;
		ajaxD2.runAJAX();
		globalButton = event.srcElement;
		globalButton.disabled = true;
		showWaitDiv(800,1000);
	}
}
function tranLoanPartCompleted(){
	var returnXml = ajaxD2.responseXML;
	var flag = returnXml.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(flag=='true'){
		var partsId = returnXml.getElementsByTagName("partsId")[0].firstChild.nodeValue;
		deleteLoanPartRowEnd(partsId);
	}else if(flag=='versionErr'){
		alert("�������ѱ��޸ģ���ˢ�º������²���");
	}else{
		alert("ת����ʧ�ܣ�����ϵ����Ա��");
	}
	globalButton.disabled = false;
	globalButton = null;
	hideWaitDiv();
}
function deleteLoanPartRowEnd(partsId){
	alert("ת�������!");
	deleteLoanPartRow(partsId);
}
function deleteLoanPartRow(partsId){
	//alert("deletePartRow.partsId:"+partsId);
	var deleteTr = eval("PART_ROW2_"+partsId);
	if(partLoanTable.tBodies[0].rows.length>0){
		for(var p=0;p<partLoanTable.tBodies[0].rows.length;p++){
			if(partLoanTable.tBodies[0].rows[p].id == "PART_ROW2_"+partsId){
				//alert("partLoanTable.tBodies[0].rows[p].id:"+partLoanTable.tBodies[0].rows[p].id);
				partLoanTable.tBodies[0].removeChild(deleteTr);
			}
		}
	}
	deleteTr.style.display = "none";
}



function f_add_rm_line(){

	var oBody = repairManTable.tBodies[0] ;
	var oNewRow=oBody.insertRow();
	oNewRow.className = "tableback1";
	//oNewRow.id = "PART_ROW2_"+partsId;
	var i=0;
	
	
	var selectRM = "<select name='repairManAjaxAdd' class='form'>";
	selectRM+="<option value='' selected>==��ѡ��==</option>";
	<%
	for(int j=0;repairManList!=null&&j<repairManList.size();j++){
		String[] rs=(String[])repairManList.get(j);
	%>
		selectRM += "<option value='<%=rs[0]%>'><%=rs[1]%></option>";
	<%}%>
	selectRM+="</select>";
	
	var selectCondition = "<select name='repairConditionAjaxAdd' class='form'>";
	<%
	for(int j=0;repairConditionList!=null&&j<repairConditionList.size();j++){
		String[] rs=(String[])repairConditionList.get(j);
		if(!rs[0].equals("P")){
	%>
		selectCondition += "<option value='<%=rs[0]%>'><%=rs[1]%></option>";
	<%}}%>
	selectCondition+="</select>";
	
	oNewRow.insertCell(i++).innerHTML=selectRM;
	oNewRow.insertCell(i++).innerHTML="<input name='departDateAjaxAdd' type='text' class='form' size='10' onkeydown='javascript:input_date();'>";
	oNewRow.insertCell(i++).innerHTML="<input name='arrivalDateAjaxAdd' type='text' class='form' size='10' onkeydown='javascript:input_date();'>";
	oNewRow.insertCell(i++).innerHTML="<input name='returnDateAjaxAdd' type='text' class='form' size='10' onkeydown='javascript:input_date();'>";
	oNewRow.insertCell(i++).innerHTML="<input name='travelFeeAjaxAdd' type='text' class='form' size='10' onkeydown='javascript:f_onlymoney();'>";
	oNewRow.insertCell(i++).innerHTML="<input name='laborCostsAjaxAdd' type='text' class='form' size='10' onkeydown='javascript:f_onlymoney();'>";
	oNewRow.insertCell(i++).innerHTML=selectCondition;
	oNewRow.insertCell(i++).innerHTML="<input name='remarkAjaxAdd' type='text' class='form' size='20'>";
	oNewRow.insertCell(i++).innerHTML="<input name='travelIdAjaxAdd' type='hidden' value=0>";
			
	
}


function f_chkAjaxAdd(){ 
	if(document.forms[0].travelIdAjaxAdd==null) return false;
	
	var len = document.forms[0].travelIdAjaxAdd.length;
	
	if(len>1){
		for(var i=0;i<len;i++){
			if(f_isNull((document.forms[0].repairManAjaxAdd)[i],'ά��Ա')
				&&f_isNull(document.forms[0].departDateAjaxAdd[i],'��������')&&checkInputDate(document.forms[0].departDateAjaxAdd[i])
				&&f_isNull(document.forms[0].arrivalDateAjaxAdd[i],'��������')&&checkInputDate(document.forms[0].arrivalDateAjaxAdd[i])
				&&f_isNull(document.forms[0].returnDateAjaxAdd[i],'��������')&&checkInputDate(document.forms[0].returnDateAjaxAdd[i])
				&&f_isNull(document.forms[0].travelFeeAjaxAdd[i],'����Ʊ')
				&&f_isNull(document.forms[0].laborCostsAjaxAdd[i],'ʵ���˹���')
				){
				
			}else{
				return true;
			}
		}
	}else{
		if(f_isNull(document.forms[0].repairManAjaxAdd,'ά��Ա')
				&&f_isNull(document.forms[0].departDateAjaxAdd,'��������')&&checkInputDate(document.forms[0].departDateAjaxAdd)
				&&f_isNull(document.forms[0].arrivalDateAjaxAdd,'��������')&&checkInputDate(document.forms[0].arrivalDateAjaxAdd)
				&&f_isNull(document.forms[0].returnDateAjaxAdd,'��������')&&checkInputDate(document.forms[0].returnDateAjaxAdd)
				&&f_isNull(document.forms[0].travelFeeAjaxAdd,'����Ʊ')
				&&f_isNull(document.forms[0].laborCostsAjaxAdd,'ʵ���˹���')
				){
				
		}else{
			return true;
		}
	}
	return false;
}


//-->
</script>
</head>

<body bgcolor="#ffffff" leftmargin="0" topmargin="0" rightmargin="0" bottommargin="0" marginwidth="0" marginheight="0" >

<jsp:include page=  "/common/waitDiv.jsp" flush="true" />
<jsp:include page=  "/common/countDiv.jsp" flush="true" />
<html:form action="repairAction.do?method=repairReturnDetail" method="post">
<html:hidden property="repairNo"/>
<input name="currentStatus" type="hidden" value="<%=rsf.getCurrentStatus() %>">
<input name="version" type="hidden" value="<%=rsf.getVersion() %>">
<input name="repairMans" type="hidden">


<table width="90%" height="96%" border="0" align="center" cellpadding="0" cellspacing="6" class="content12">
  <tr> 
            
    <td height="556" align="right" valign="top"> 
    
    
   <div align="center" id="Layer9" style="position:absolute; left:35; top:80; width:912px; height:390px; z-index:1; visibility: hidden; "> 
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="content12">
     <tr> 
      <td valign="bottom"><table border="0" cellpadding="0" cellspacing="0">
		<tr> 
			<td width="30"><input name="labelButton" type="button" class="button_b" value="������Ϣ"></td>
			<td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','show','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="�ͻ���Ϣ"></td>
			<td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','show','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="������Ϣ"></td>
			<td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','show','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="ά�������Ϣ"></td>
			<td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','show')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="Я�����/����"></td>
			<td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','show','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="ά���ɹ���Ϣ"></td>
        </tr>
       </table></td>
     </tr>
     <tr> 
      <td height="2" colspan="2" bgcolor="#677789"></td>
     </tr>
     <tr> 
      <td height="6" colspan="2" bgcolor="#CECECE"></td>
     </tr>
     <tr> 
      <td valign="top" colspan="2">
      	<table  width="100%" cellspacing="0" cellpadding="0" border="0" id="URSParentTable"><!--start of URSParentTable-->
					<tr>
						<td width="100%">
      				     
				      	<table width="100%" border="0" cellspacing="1" cellpadding="1" class="content12">
				         <tr class="tableback2"> 
				          <td width="15%">ά�޵��ţ�</td>
				          <td width="20%">
				          	<%=rsf.getServiceSheetNo()==null?"":rsf.getServiceSheetNo()%>
				          	<input type="hidden" name="serviceSheetNo" value="<%=rsf.getServiceSheetNo()%>">
				          </td>
				          
				          <td width="15%">ά�����ʣ�</td>
				          <td width="20%"><%=DicInit.getSystemName("REPAIR_PROPERITES",rsf.getRepairProperites())%></td>
				          <td width="10%">���������ͣ�</td>
				          <td width="20%"><%=DicInit.getSystemName("WARRANTY_TYPE",rsf.getWarrantyType())%></td>
				          
				         </tr>
						 <tr class="tableback1"> 
				          <td width="111">���ͣ�</td>
				          <td width="130"><%=rsf.getModelCode()==null?"":rsf.getModelCode()%></td>
				          <td width="110">����ţ�</td>
				          <td width="150"><%=rsf.getSerialNo()==null?"":rsf.getSerialNo()%></td>
						  <td colspan=2> <span class="content11"><b><%=DicInit.getSystemName("RR90",rsf.getRr90())%></b></span></td>
						  
				         </tr>
				      
						 <tr class="tableback2"> 
				          <td>�������ڣ�</td>
				          <td><%=rsf.getPurchaseDate()==null?"":Operate.formatYMDDate(rsf.getPurchaseDate())%></td>
				          
				          <td>�������ţ�</td>
				          <td><bean:write property="warrantyCardNo"  name="repairServiceForm"/></td>
				          <td>�������̣�</td>
				          <td><%=rsf.getManufacture()==null?"":rsf.getManufacture()%></td>
						 </tr>
				        
						<tr class="tableback1"> 
				          <td>�ͻ�Ҫ�󵽴����ڣ�</td>
				          <td><%=rsf.getCustomerVisitDate()==null?"":Operate.formatYMDDate(rsf.getCustomerVisitDate())%></td>
				          
				          <td>Ԥ���޸����ڣ�</td>
				          <td colspan="3"><%=rsf.getEstimateRepairDate()==null?"":Operate.formatYMDDate(rsf.getEstimateRepairDate())%></td>
				        </tr>
				        
						
				        <tr class="tableback2"> 
				          <td valign="top">�ͻ��������ϣ�</td>
				          <td colspan="5"><%=rsf.getCustomerTrouble()==null?"":rsf.getCustomerTrouble()%></td>
				        </tr>
					   <tr> 
					      <td height="2" bgcolor="#ffffff" colspan="10" ></td>
					     </tr>
					     <tr> 
					      <td height="1" bgcolor="#677789" colspan="10" ></td>
					     </tr>
			         
				        </table>
				        <br>
								<table  width="100%" cellspacing="0" cellpadding="0" border="0" id="UpFileParentTable"> <!--start of UpFileParentTable-->
									<tr> 
							      		<td valign="top"> <table width="100%" border="0" cellpadding="1" cellspacing="1" class="content12">
							      		<tr><td><span class="content14"><strong>��Ƭ��Ϣ</strong></span> </td>
							      	</tr>
							      	<tr> 
							      		<td height="1" colspan="10" background="<%= request.getContextPath()%>/images/i_line.gif"></td>
        							</tr>
							
									<tr>
										<td colspan="3">
											<table id="uploadCompleteTable" width="100%" border="0" cellspacing="1" cellpadding="1" class="content12">
												<tr class="tableback1">
													<td width="5%"><B>���</B></td>
													<td width="10%"><B>ɾ��</B></td>
													<td width="60%"><B>�ļ�</B></td>
													<td width="10%"><B>�ļ�����</B></td>
													<td width="15%"><B>�ϴ�ʱ��</B></td>
												</tr>
												<%
													if(repairAttachment!=null){
														for(int i=repairAttachment.size()-1;i>=0;i--){
												 	  	String[] temp=(String[])repairAttachment.get(i);
															strTr=i%2==0?"tableback2":"tableback1";
												%>
															<tr class="<%=strTr%>">
															  <td><%=i+1%></td>
															  <td>&nbsp;&nbsp;</td>
															  <td><a href="<%=temp[2]%>" style="cursor: hand"  ><%=temp[1]%></a></td>
															  <td><%=DicInit.getSystemName("FILE_TYPE",temp[3])%><input type="hidden" name="attachId" value="<%=temp[0]%>"></td>
															  <td><%=temp[4]%></td>
															</tr>
												<%}}%>
											</table>
										</td>
									</tr>
									</table>
									</td></tr>
									
								</table> <!--end of UpFileParentTable-->
						
					</td>
				</tr>
			</table>
		</td>
     </tr>
     <tr> 
      <td height="2" colspan="2" bgcolor="#ffffff"></td>
     </tr>
     <tr> 
      <td height="1" colspan="2" bgcolor="#677789"></td>
     </tr>
    </table>
   </div>
   <div align="center" id="Layer10" style="position:absolute; left:35; top:80; width:912px; height:390px; z-index:1; visibility: hidden;"> 
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="content12">
     <tr> 
      <td valign="bottom"> <table border="0" cellpadding="0" cellspacing="0">
        <tr> 
         <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','show','Layer10','','hide','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="������Ϣ"></td>
         <td width="30"><input name="labelButton" type="button" class="button_b" value="�ͻ���Ϣ"></td>
         <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','show','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="������Ϣ"></td>
         <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','show','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="ά�������Ϣ"></td>
         <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','show')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="Я�����/����"></td>
		 <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','show','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="ά���ɹ���Ϣ"></td>
        </tr>
       </table></td>
     </tr>
     <tr> 
      <td height="2" colspan="2" bgcolor="#677789"></td>
     </tr>
     <tr> 
      <td height="6" colspan="2" bgcolor="#CECECE"></td>
     </tr>
     <tr> 
      <td valign="top" colspan="2"><table width="100%" border="0" cellspacing="1" cellpadding="1" class="content12">
        <tr class="tableback2"> 
         <td width="88">�ͻ�ID��</td>
         <td width="140"><%=rcif.getCustomerId()==null?"":rcif.getCustomerId().toString()%></td>
         <td width="99">�ͻ����ƣ� </td>
         <td width="242"><%=rcif.getCustomerName()==null?"":rcif.getCustomerName()%></td>
         <td width="98">��ϵ�ˣ�</td>
         <td width="174"><%=rsf.getLinkman()==null?"":rsf.getLinkman()%></td>
        </tr>
        <tr class="tableback1"> 
         <td>�ֻ���</td>
         <td><%=rcif.getMobile()==null?"":rcif.getMobile()%></td>
         <td>�绰��</td>
         <td><%=rsf.getPhone()==null?"":rsf.getPhone()%></td>
         <td>���棺</td>
         <td><%=rsf.getFax()==null?"":rsf.getFax()%></td>
        </tr>
         <tr class="tableback2"> 
         <td>ʡ�ݣ�</td>
         <td><%=rcif.getProvinceName()==null?"":rcif.getProvinceName()%></td>
         <td>���У�</td>
         <td><%=rcif.getCityName()==null?"":rcif.getCityName()%></td>
         <td>�������룺</td>
         <td><%=rcif.getPostCode()==null?"":rcif.getPostCode()%></td>
        <tr class="tableback1"> 
         <td>�����ʼ���</td>
         <td><%=rcif.getEmail()==null?"":rcif.getEmail()%></td>
         <td>��ϵ��ַ��</td>
         <td colspan="3"><%=rcif.getAddress()==null?"":rcif.getAddress()%></td>
        </tr>
        <tr class="tableback2"> 
         <td valign="top">��ע��</td>
         <td colspan="5"><%=rcif.getRemark()==null?"":rcif.getRemark()%></td>
        </tr>
        
       </table></td>
     </tr>
     <tr> 
      <td height="2" colspan="2" bgcolor="#ffffff"></td>
     </tr>
     <tr> 
      <td height="1" colspan="2" bgcolor="#677789"></td>
     </tr>
     
       
    </table>
   </div>
    
    <div align="center" id="Layer1" style="position:absolute; left:35; top:80; width:912px; height:390px; z-index:1; "> 
        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="content12">
          <tr> 
            <td valign="bottom"> <table border="0" cellpadding="0" cellspacing="0">
                <tr> 
                  <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','show','Layer10','','hide','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="������Ϣ"></td>
                  <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','show','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="�ͻ���Ϣ"></td>
                  <td width="30"><input name="labelButton" type="button" class="button_b"  value="������Ϣ"></td>
                  <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','show','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="ά�������Ϣ"></td>
                  <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','show')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="Я�����/����"></td>
                  <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','show','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="ά���ɹ���Ϣ"></td>

                </tr>
              </table></td>
          </tr>
          <tr> 
            <td height="2" bgcolor="#677789"></td>
          </tr>
          <tr> 
            <td height="6" bgcolor="#CECECE"></td>
          </tr>
          <tr> 
            <td valign="top"> <table width="100%" border="0" cellpadding="1" cellspacing="1" class="content12">
                
               
                <tr class="tableback1"> 
                  <td width="10%">������ϵ�ˣ�</td>
                  <td width="20%"><bean:write property="dzLinkman" name="repairServiceForm" /></td>
                  <td width="10%">����绰��</td>
                  <td width="20%"><bean:write property="dzPhone"  name="repairServiceForm"/></td>
                  <td width="10%"></td>
                  <td width="20%"></td>
                </tr>
                <tr class="tableback2"> 
                  <td width="10%">Ԥ�ƹ��ڣ�</td>
                  <td width="20%"><bean:write property="expectedDuration"   name="repairServiceForm"/></td>
                  <td width="10%">���ﱸע��</td>
                  <td colspan=3><bean:write property="dzRemark"  name="repairServiceForm"/></td>
                  
                </tr>
                <tr class="tableback1"> 
                  <td valign="top">����ԭ��<font color="red">*</font></td>
                  <td colspan="5"><bean:write property="dzReason"  name="repairServiceForm"/></td>
                </tr>
                <tr class="tableback2"> 
                  <td valign="top">��������<font color="red">*</font></td>
                  <td colspan="5"><bean:write property="dzResult" name="repairServiceForm"/></td>
                </tr>
       			
					 
				<tr> 
            <td height="2" colspan="8" bgcolor="#ffffff"></td>
          </tr>
          <tr> 
            <td height="1" colspan="8" bgcolor="#677789"></td>
          </tr>
          </table>
          <br>
          <table width="100%" border="0" cellpadding="1" cellspacing="1" class="content12">
            <tr> 
              <td height="1" colspan="10" background="<%= request.getContextPath()%>/images/i_line.gif"></td>
            </tr>
           
   		</table>
	<table HEIGHT="120" width="100%" cellspacing="0" cellpadding="0" border="0" id="repairStatusTable"><!--start of repairStatusParentTable-->
	<tr><td width="100%">
	<div class="scrollDiv" id="repairStatusScrollDiv"><!--start of repairStatusScrollDiv-->
		<table width="100%" border="0" cellpadding="0" cellspacing="1" class="content12">
			<tr bgcolor="#CCCCCC"> 
				<td width="120" height="18"><b>ά��״̬</b></td>
				<td><b>״̬��ʼ����</b></td>
				<td><b>״̬������ע</b></td>
			</tr>
<%
	if(serviceStatusSet!=null){
		strTr="";
		int i=0;
		Iterator rssfIterator = serviceStatusSet.iterator();
		while(rssfIterator.hasNext()){
 	  		RepairServiceStatusForm rssf = (RepairServiceStatusForm)rssfIterator.next();
			strTr=i%2==0?"tableback2":"tableback1";
			i++;
%>
			<tr class="<%=strTr%>"> 
			  	<td><%=DicInit.getSystemName("CURRENT_STATUS",rssf.getRepairStatus())%></td>
			  	<td><%=rssf.getBeginDate()==null?"":Operate.formatDate(rssf.getBeginDate())%></td>
			  	<td><%=rssf.getStatusRemark()==null?"":rssf.getStatusRemark()%></td>
			</tr>
<%}}%>
			<tr> 
				<td height="1" colspan="3" background="<%= request.getContextPath()%>/images/i_line.gif"></td>
			</tr>
		</table>
	</div> <!--end of repairStatusScrollDiv-->
	</td></tr>
	</table> <!--end of repairStatusParentTable-->
		</td>
     </tr>
         
     
    </table>
   </div>
   
   <div align="center" id="Layer2" style="position:absolute; left:35; top:80; width:912px; height:390px; z-index:1; visibility: hidden;"> 
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="content12">
     <tr> 
      <td valign="bottom"><table border="0" cellpadding="0" cellspacing="0">
        <tr> 
			<td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','show','Layer10','','hide','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="������Ϣ"></td>
			<td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','show','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="�ͻ���Ϣ"></td>
			<td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','show','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="������Ϣ"></td>
			<td width="30"><input name="labelButton" type="button" class="button_b" value="ά�������Ϣ"></td>
			<td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','show')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="Я�����/����"></td>
			<td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','show','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="ά���ɹ���Ϣ"></td>
        </tr>
       </table></td>
     </tr>
     <tr> 
      <td height="2" bgcolor="#677789"></td>
     </tr>
     <tr> 
      <td height="6" bgcolor="#CECECE"></td>
     </tr>
     			 
     
     <tr> 
      	<td valign="top"> <table width="100%" border="0" cellpadding="1" cellspacing="1" class="content12">
      	<tr><td><span class="content14"><strong>ά�������Ϣ</strong></span>
			<tr> 
              <td height="1" colspan="10" background="images/i_line.gif"></td>
            </tr>
    <table height="280" width="100%" cellspacing="0" cellpadding="1" class="content12" border="0" id="partApplyedParentTable"><!--start of partApplyedParentTable-->
	<tr><td width="100%">
	<div class="scrollDiv" id="partApplyedScrollDiv"><!--start of partApplyedScrollDiv-->
       <table width="100%" border="0" cellpadding="0" cellspacing="1" class="content12" id="partApplyTable">
        <thead>
        <tr bgcolor="#CCCCCC"> 
         <td><strong>����Ϻ�</strong></td>
         <td><strong>�������</strong></td>
         <td><strong>���</strong></td>
         <td><strong>��λ</strong></td>
         <td><strong>����</strong></td>
         <td><strong>����</strong></td>
         <td><strong>״̬</strong></td>
        </tr>
        </thead>
        <tbody>
<%
	if(partsList!=null){
		int i=0;
		Iterator partIterator = partsList.iterator();
		while(partIterator.hasNext()){
 	  		RepairPartForm part = (RepairPartForm)partIterator.next();
			strTr=i%2==0?"tableback1":"tableback2";
			i++;
							
%>        
        <tr class="<%=strTr%>" id="PART_ROW_<%=part.getPartsId()%>"> 
         <td><%=part.getStuffNo()%></td>
         <td><%=part.getSkuCode()%></td>
         <td><%=part.getStandard()==null?"":part.getStandard()%></td>
       	 <td><%=part.getSkuUnit()%></td>
         <td><%=DicInit.getSystemName("WARRANTY_TYPE",part.getWarrantyType())%></td>
         <td><%=part.getApplyQty()%></td>
         <td><%=DicInit.getSystemName("REPAIR_PART_STATUS",part.getRepairPartStatus())%></td>
<%}}%>
        </tbody>
       </table>
	</div> <!--end of partApplyedScrollDiv-->
	</td></tr>
	</table> <!--end of partApplyedParentTable-->
      </td>
     </tr>
     
     <tr> 
      <td height="2" bgcolor="#ffffff"></td>
     </tr>
     <tr> 
      <td height="1" bgcolor="#677789"></td>
     </tr>
    </table>
    
     
	
   </div>
   
   
   <div align="center" id="Layer8" style="position:absolute; left:35; top:80; width:912px; height:390px; z-index:1; visibility: hidden;"> 
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="content12">
     <tr> 
      <td valign="bottom"><table border="0" cellpadding="0" cellspacing="0">
        <tr> 
			<td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','show','Layer10','','hide','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="������Ϣ"></td>
			<td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','show','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="�ͻ���Ϣ"></td>
			<td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','show','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="������Ϣ"></td>
			<td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','show','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="ά�������Ϣ"></td>
			<td width="30"><input name="labelButton" type="button" class="button_b" value="Я�����/����"></td>
			<td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','show','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="ά���ɹ���Ϣ"></td>
        </tr>
       </table></td>
     </tr>
     <tr> 
      <td height="2" bgcolor="#677789"></td>
     </tr>
     <tr> 
      <td height="6" bgcolor="#CECECE"></td>
     </tr>
    			 
     <tr> 
      <td valign="top"> <table width="100%" border="0" cellpadding="1" cellspacing="1" class="content12">
      <tr><td><span class="content14"><strong>Я�����</strong></span> </td></tr>
      <tr> 
              <td height="1" colspan="10" background="<%= request.getContextPath()%>/images/i_line.gif"></td>
            </tr>
        
       </table>
			
    <table height="240" width="100%" cellspacing="0" cellpadding="1" class="content12" border="0" id="partLoanParentTable">
	<tr><td width="100%">
	<div class="scrollDiv" id="partLoanScrollDiv">
       <table width="100%" border="0" cellpadding="0" cellspacing="1" class="content12" id="partLoanTable">
        <thead>
        <tr bgcolor="#CCCCCC"> 
         <td><strong>����Ϻ�</strong></td>
         <td><strong>�������</strong></td>
         <td><strong>���</strong></td>
         <td><strong>��λ</strong></td>
         <td><strong>����</strong></td>
         <td><strong>״̬</strong></td>
         <td><strong>&nbsp;</strong></td>
        </tr>
        </thead>
        <tbody>
<%
	if(loanList!=null){
		int i=0;
		Iterator partIterator = loanList.iterator();
		while(partIterator.hasNext()){
 	  		RepairPartForm part = (RepairPartForm)partIterator.next();
			strTr=i%2==0?"tableback1":"tableback2";
			i++;
			String partStatus = part.getRepairPartStatus();			
%>        
        <tr class="<%=strTr%>" id="PART_ROW2_<%=part.getPartsId()%>"> 
         <td><%=part.getStuffNo()%></td>
         <td><%=part.getSkuCode()%></td>
         <td><%=part.getStandard()==null?"":part.getStandard()%></td>
       	 <td><%=part.getSkuUnit()%></td>
         <td><%=part.getApplyQty()%></td>
         <td><%=DicInit.getSystemName("REPAIR_PART_STATUS",part.getRepairPartStatus())%></td>
         <td width="6%" align="center">
         <%if("X".equals(partStatus)){ %>
         <input name="tranPartButton" type="button" class="button2" value="ת����" onclick="tranAPart('<%=part.getPartsId()%>',<%=part.getVersion()%>)"></td>
		 <%} else{%>
         	&nbsp;
         <%} %>
         </td>
	<%}}%>
        </tbody>
       </table>

	</div> 
	</td></tr>
	</table>
      </td>
     </tr>
     
     <tr> 
      <td height="2" bgcolor="#ffffff"></td>
     </tr>
     <tr> 
      <td height="1" bgcolor="#677789"></td>
     </tr>
    </table>
    
    <br>
	<table HEIGHT="60" width="100%" cellspacing="0" cellpadding="0" border="0" id="repairToolsTable">
	<tr> 
      <td valign="top"> <table width="100%" border="0" cellpadding="1" cellspacing="1" class="content12">
      <tr><td><span class="content14"><strong>Я������</strong></span> </td></tr>
      <tr> 
              <td height="1" colspan="10" background="<%= request.getContextPath()%>/images/i_line.gif"></td>
            </tr>
        
        
       </table>
    <table height="120" width="100%" cellspacing="0" cellpadding="1" class="content12" border="0" id="toolsLoanParentTable">
	<tr><td width="100%">
	<div class="scrollDiv" id="toolsLoanScrollDiv">
       <table width="100%" border="0" cellpadding="0" cellspacing="1" class="content12" id="toolsLoanTable">
        <thead>
        <tr bgcolor="#CCCCCC"> 
         				<td ><strong>&nbsp;�����Ϻ�</strong></td>
                        <td><strong>��������</strong></td>
                        <td ><strong>���</strong></td>
                        <td><strong>��λ</strong></td>
                        <td ><strong>����</strong></td>
                        <td ><strong>״̬</strong></td>
        </tr>
        </thead>
        <tbody>
<%
	if(toolsList!=null){
		int i=0;
		Iterator partIterator = toolsList.iterator();
		while(partIterator.hasNext()){
 	  		RepairPartForm part = (RepairPartForm)partIterator.next();
			strTr=i%2==0?"tableback1":"tableback2";
			i++;
			String partStatus = part.getRepairPartStatus();			
%>        
        <tr class="<%=strTr%>" id="PART_ROW3_<%=part.getPartsId()%>"> 
         <td><%=part.getStuffNo()%></td>
         <td><%=part.getSkuCode()%></td>
         <td><%=part.getStandard()==null?"":part.getStandard()%></td>
       	 <td><%=part.getSkuUnit()%></td>
         <td><%=part.getApplyQty()%></td>
         <td><%=DicInit.getSystemName("REPAIR_PART_STATUS",part.getRepairPartStatus())%></td>

         </tr>
	<%}}%>
        </tbody>
       </table>

	</div> 
	</td></tr>
	</table>
      </td>
     </tr>
	</table> 
	
   </div>
   
   
   
   <div align="center" id="Layer11" style="position:absolute; left:35; top:80; width:912px; height:390px; z-index:1;visibility: hidden;"> 
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="content12">
     <tr> 
      <td valign="bottom"><table border="0" cellpadding="0" cellspacing="0">
        <tr> 
         <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','show','Layer10','','hide','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="������Ϣ"></td>
         <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','show','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="�ͻ���Ϣ"></td>
         <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','show','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="������Ϣ"></td>
         <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','show','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="ά�������Ϣ"></td>
         <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','show')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="Я�����/����"></td>
         <td width="30"><input name="labelButton" type="button" class="button_b" value="ά���ɹ���Ϣ"></td>
        
        </tr>
       </table></td>
     </tr>
     <tr> 
      <td height="2" bgcolor="#677789"></td>
     </tr>
     <tr> 
      <td height="6" bgcolor="#CECECE"></td>
     </tr>
     <tr> 
      <td valign="top"> <table width="100%" border="0" cellpadding="1" cellspacing="1" class="content12">
        
      
       </table>
       <br> <span class="content14"><b>ά��Ա��Ϣ</b></span><br> 
	<table height="180" width="100%" cellspacing="0" cellpadding="1" class="content12" border="0" id="irisParentTable">
	<tr><td width="100%">
	<div class="scrollDiv" id="IRISScrollDiv">
       <table width="100%" border="0" cellpadding="0" cellspacing="1" class="content12" id="repairManTable">
        <thead>
        <tr bgcolor="#CCCCCC"> 
         <td height="18"><b>ά��Ա</b></td>
         <td><b>��������</b></td>
         
         <td><b>��������</b></td>
         <td><b>��������</b></td>
         
         <td><b>����Ʊ</b></td>
         <td><b>�˹���</b></td>
         <td><b>ά�����</b></td>
         <td><b>��ע</b></td>
        </tr>
     	</thead>
        <tbody>
        
<%
	if(repairManSetInfoSet!=null){
		int i=0;
		Iterator irisIterator = repairManSetInfoSet.iterator();
		while(irisIterator.hasNext()){
 	  		RepairManInfoForm rmi = (RepairManInfoForm)irisIterator.next();
			strTr=i%2==0?"tableback1":"tableback2";
			i++;
%>        
        <tr class="<%=strTr%>" > 
         <td><%=rmi.getRepairManName()%> <input type="hidden" name="travelId" value="<%=rmi.getTravelId()%>"></td>
         <td><%=rmi.getDepartDate()==null?"":Operate.formatYMDDate(rmi.getDepartDate())%></td>
         <td><input name="arrivalDate" type="text" class="form" size="10" onkeydown='javascript:input_date();'></td>
         <td><input name="returnDate" type="text" class="form" size="10" onkeydown='javascript:input_date();'></td>
         <td><input name="travelFee" type="text" class="form" size="10" onkeydown='javascript:f_onlymoney();'></td>
         <td><input name="laborCosts" type="text" class="form" size="10" onkeydown='javascript:f_onlymoney();'></td>
         <td><select name="repairCondition" class="form">
         	<%
       		for(int j=0;repairConditionList!=null&&j<repairConditionList.size();j++){
       			String[] rs=(String[])repairConditionList.get(j);
       			if(!rs[0].equals("P")){
       		%>
       		<option value="<%=rs[0]%>"><%=rs[1]%></option>
       		<%}}%>
      	 </select></td>
         <td><%=rmi.getRemark()==null?"":rmi.getRemark()%></td>
        </tr>
<%}}%>
        

        </tbody>
       </table>
	</div> <!--end of IRISScrollDiv-->
	</td></tr>
	</table> <!--end of irisParentTable-->
	   </td>
     </tr>
     <tr>
        	<td align="left"><input name="partAddButton" type="button" class="button2" value="���" onclick="f_add_rm_line()"></td>
        </tr>
     <tr> 
      <td height="2" bgcolor="#ffffff"></td>
     </tr>
     <tr> 
      <td height="1" bgcolor="#677789"></td>
     </tr>
    </table>
   </div>
   
   <br>
   <br>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="content12">
	    <tr>
	       <td width="30%">ά��>ά�޷���>ά�޵���ϸ</td>
	       
	    </tr>
	</table>
	<div align="center"  style="position:absolute; left:35;  width:912px;  z-index:1; "> 
		<table border="0" width="100%" cellpadding="0" cellspacing="0" class="content12">
		  <tr> 
		 		
			  <td align="right"> 
			    <input name="endRepairButton" type="button" onClick="returnEnd()" class="button4" value="�������">
			  	<input name="closeButton" type="button" class="button2" value="�ر�" onclick="window.close()"> 
			  	
			  </td>
			</tr>
	  </table>
	</div>   

  </td>
   </tr>
  </table></html:form>

</body>

</html>

<%}catch(Exception e){
	e.printStackTrace();
}%> 