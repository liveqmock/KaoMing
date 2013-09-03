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
	
	List historyRepairList = (List)request.getAttribute("historyRepairList");
	
	CustomerInfoForm rcif = rsf.getCustomInfoForm();
	
	//Set repairManSetInfoSet = rsf.getRepairManSetInfo();
	Set serviceStatusSet = rsf.getServiceStatusSet();
	
	//List urList=(ArrayList) DicInit.SYS_CODE_MAP.get("PAGE_UNREPAIR_REASON");
	//List unQucikStatusList=(ArrayList) DicInit.SYS_CODE_MAP.get("UNCOMPLETE_QUICK_STATUS");
	//List crReasonList=(ArrayList) DicInit.SYS_CODE_MAP.get("CR_REASON");
	//List unQuickReasonList=(ArrayList) DicInit.SYS_CODE_MAP.get("UNQUICK_REASON");
	//List repairConditionList=(ArrayList) DicInit.SYS_CODE_MAP.get("REPAIR_CONDITION");
	
%>
<html>
<head>
<title>电诊明细</title>
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
<script type="text/javascript" src="<%= request.getContextPath()%>/js/prototype.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/js/autocomplete.js"></script>
<script language=javascript src="js/iris_map.js"></script>
<script language=javascript src="js/checkIris.js"></script>
<script language=javascript src="<%= request.getContextPath()%>/js/customerTemp.js"></script>
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

function repeatedReceivePrint(id,printType){//重新打印接机单
  window.open("receiveAction.do?method=repeatedReceivePrint&repairNo="+id+"&repeate=true&printType="+printType,"","height=700,width=860,scrollbars=yes");
}



function f_save(){
	
	showWaitDiv(800,1000);

	document.forms[0].action="repairHandleAction.do?method=saveRepair&flag=dzSave&repairNo="+document.forms[0].repairNo.value+"&version="+document.forms[0].version.value;
	document.forms[0].submit();	
}

function prepareDoNotRepair(){
	if(document.forms[0].dzReason.value == ""){ //判断是否填写维修内容
		alert("必须填写电诊原因后才能修理完成！");
		MM_showHideLayers('Layer1','','show','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','hide');
		document.forms[0].dzReason.focus();
		return false;
	}	
	
	if(document.forms[0].dzResult.value == ""){ //判断是否填写维修内容
		alert("必须填写电诊结果后才能修理完成！");
		MM_showHideLayers('Layer1','','show','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','hide');
		document.forms[0].dzResult.focus();
		return false;
	}
	if(confirm("您真的需要进行不修理操作吗？")){
		showWaitDiv(800,1000);
		document.forms[0].action="repairHandleAction.do?method=doNotRepair&repairNo="+document.forms[0].repairNo.value+"&version="+document.forms[0].version.value;
		document.forms[0].submit();	
	}
}


function transferSale(){

	<%if(AtomRoleCheck.checkRepairIW(rsf.getWarrantyType())){ %>
		if(document.forms[0].warrantyCardNo.value == ""){
			alert("必须填写保固书编号！");
			MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','show','Layer10','','hide','Layer11','','hide','Layer8','','hide');
			document.forms[0].warrantyCardNo.focus();
			return false;
		}
	<%}%>
	
	if(document.forms[0].dzReason.value == ""){ //判断是否填写维修内容
		alert("必须填写电诊原因后才能修理完成！");
		MM_showHideLayers('Layer1','','show','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','hide');
		document.forms[0].dzReason.focus();
		return false;
	}	
	if(document.forms[0].dzResult.value == ""){ //判断是否填写维修内容
		alert("必须填写电诊结果后才能修理完成！");
		MM_showHideLayers('Layer1','','show','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','hide');
		document.forms[0].dzResult.focus();
		return false;
	}
	
	if(document.forms[0].repairmanNum.value == ""){ //判断是否填写维修内容
		alert("必须填写预计人数后才能修理完成！");
		MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','show','Layer8','','hide');
		document.forms[0].repairmanNum.focus();
		return false;
	}
	if(document.forms[0].workingHours.value == ""){ //判断是否填写维修内容
		alert("必须填写工时(单)后才能修理完成！");
		MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','show','Layer8','','hide');
		document.forms[0].workingHours.focus();
		return false;
	}
	if(document.forms[0].ticketsAllCosts.value == ""){ //判断是否填写维修内容
		alert("必须填写车船票后才能修理完成！");
		MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','show','Layer8','','hide');
		document.forms[0].ticketsAllCosts.focus();
		return false;
	}
	if(document.forms[0].laborCosts.value == ""){ //判断是否填写维修内容
		alert("必须填写人工费后才能修理完成！");
		MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','show','Layer8','','hide');
		document.forms[0].laborCosts.focus();
		return false;
	}
	
	
	if(confirm("确定电诊结束,进入销售流程吗？")){
		showWaitDiv(800,1000);
		document.forms[0].action="repairHandleAction.do?method=transferSale&repairNo="+document.forms[0].repairNo.value+"&version="+document.forms[0].version.value;
		document.forms[0].submit();	
	}

}


function dzComplete(){
	
	if(document.forms[0].dzReason.value == ""){ //判断是否填写维修内容
		alert("必须填写电诊原因后才能修理完成！");
		MM_showHideLayers('Layer1','','show','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','hide');
		document.forms[0].dzReason.focus();
		return false;
	}	
	
	if(document.forms[0].dzResult.value == ""){ //判断是否填写维修内容
		alert("必须填写电诊结果后才能修理完成！");
		MM_showHideLayers('Layer1','','show','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','hide');
		document.forms[0].dzResult.focus();
		return false;
	}
	
	if(confirm("确定修理结束吗？")){
		showWaitDiv(800,1000);
		document.forms[0].action="repairHandleAction.do?method=repairDZComplete&repairNo="+document.forms[0].repairNo.value+"&version="+document.forms[0].version.value;
		document.forms[0].submit();	
	}

}




function f_filter(){
	var key = event.keyCode;

	//ctrl+c,ctrl+v
	if ((key==67||key==86) && event.ctrlKey) {
		event.returnValue = false;
	}
	if ((key==50||key==51) && event.shiftKey) {
		event.returnValue = false;
	}
	
}

var ajax = new sack();
function insertRepairPartInfo(){// 零件保存
	if(document.forms[0].applyQty.value == null){
		alert("请输入零件数量！");
		document.forms[0].applyQty.focus();
	}else if(document.forms[0].applyQty.value <= 0||document.forms[0].applyQty.value >= 10000){
		alert("请填写合理的零件数量！");
		document.forms[0].applyQty.select();
		document.forms[0].applyQty.focus();
	}else{
		var feeTypeStr = (document.forms[0].warrantyType.value=="I"||document.forms[0].warrantyType.value=="E")?"免费":"收费";
		if(confirm("确定以\""+feeTypeStr+"\"这种类型申请此零件？")){
			ajax.reset();
			ajax.setVar("repairNo", document.forms[0].repairNo.value);
			ajax.setVar("stuffNo", document.forms[0].stuffNo.value);
			ajax.setVar("warrantyType", document.forms[0].warrantyType.value);
			ajax.setVar("applyQty", document.forms[0].applyQty.value);
			
			ajax.setVar("method", "insertRepairPartInfo");
			ajax.requestFile = "repairHandleAction.do";
			ajax.method = "GET";
			ajax.onCompletion = insertRepairPartInfoCompleted;
			ajax.runAJAX();
			globalButton = event.srcElement;
			globalButton.disabled = true;
			showWaitDiv(800,1000);
		}
	}
}

function insertRepairPartInfoCompleted(){ 
	var returnXml = ajax.responseXML;
	//var xmlObj = returnXml.getElementsByTagName("xml")[0];
	//alert("xmlObj:"+xmlObj);
	var flag = returnXml.getElementsByTagName("flag")[0].firstChild.nodeValue;
	
	if(eval(flag)){
		var partRows = returnXml.getElementsByTagName("partRow");
		
		for(var counter=0;counter<partRows.length;counter++){
			var partsId = returnXml.getElementsByTagName("partsId")[counter].firstChild.nodeValue;
			var stuffNo = returnXml.getElementsByTagName("stuffNo")[counter].firstChild.nodeValue;
			var skuCode = unescape(returnXml.getElementsByTagName("skuCode")[counter].firstChild.nodeValue);
			var standard = unescape(returnXml.getElementsByTagName("standard")[counter].firstChild.nodeValue);
			var skuUnit = unescape(returnXml.getElementsByTagName("skuUnit")[counter].firstChild.nodeValue);
			var warrantyType = unescape(returnXml.getElementsByTagName("warrantyType")[counter].firstChild.nodeValue);
			
			var applyQty = returnXml.getElementsByTagName("applyQty")[counter].firstChild.nodeValue;
			
			addAPartRowEnd(partsId,stuffNo,skuCode,standard,skuUnit,warrantyType,applyQty);
		}
	}else{
		var result = returnXml.getElementsByTagName("result")[0].firstChild.nodeValue;
		if(result=="part"){
			alert("零件料号不存在！");
		}else{
			alert("零件添加失败，请联系管理员！");
		}
		
	}
	globalButton.disabled = false;
	globalButton = null;
	hideWaitDiv();
	resetPartApplyForm();
}
function resetPartApplyForm(){
	document.forms[0].stuffNo.value = "";
	document.forms[0].skuCode.value = "";
	document.forms[0].skuUnit.value = "";
	document.forms[0].applyQty.value = "";
	document.forms[0].partAddButton.disabled = false;
}
function addAPartRowEnd(partsId,stuffNo,skuCode,standard,skuUnit,warrantyType,applyQty){
	var oBody = partApplyTable.tBodies[0] ;
	var oNewRow=oBody.insertRow();
	oNewRow.className = "tableback1";
	oNewRow.id = "PART_ROW_"+partsId;
	var i=0;
	
	//var oNewCell=oNewRow.insertCell(0).innerHTML="<input type=\"checkbox\" name=\"partsId\" value=\""+partsId+"\">";
	oNewRow.insertCell(i++).innerHTML=stuffNo;
	oNewRow.insertCell(i++).innerHTML=skuCode=="null"?"":skuCode;
	oNewRow.insertCell(i++).innerHTML=standard=="null"?"":standard;
	oNewRow.insertCell(i++).innerHTML=skuUnit;
	oNewRow.insertCell(i++).innerHTML=warrantyType;
	oNewRow.insertCell(i++).innerHTML=applyQty;
	var oNewCell=oNewRow.insertCell(i++);
	oNewCell.align="center";
	oNewCell.innerHTML="<input name=\"DelPartButton\" type=\"button\" class=\"button2\" value=\"删除\" onclick=\"deleteAPart('PART_ROW_"+partsId+"')\">";
	
}

var ajaxD = new sack();
function deleteAPart(partsIdTRRow){
	var partsId = partsIdTRRow.substr(9);// 从9开始截是因为前面有"PART_ROW_"开头
	ajaxD.setVar("partsId",partsId);
	ajaxD.setVar("method", "deletePartStatus");
	ajaxD.requestFile = "repairHandleAction.do";
	ajaxD.method = "GET";
	ajaxD.onCompletion = deletePartCompleted;
	ajaxD.runAJAX();
	globalButton = event.srcElement;
	globalButton.disabled = true;
	showWaitDiv(800,1000);
}
function deletePartCompleted(){
	var returnXml = ajaxD.responseXML;
	var flag = returnXml.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(eval(flag)){
		var partsId = returnXml.getElementsByTagName("partsId")[0].firstChild.nodeValue;
		deletePartRowEnd(partsId);
	}else{
		alert("删除失败，请联系管理员！");
	}
	globalButton.disabled = false;
	globalButton = null;
	hideWaitDiv();
}
function deletePartRowEnd(partsId){
	deletePartRow(partsId);
}
function deletePartRow(partsId){
	//alert("deletePartRow.partsId:"+partsId);
	var deleteTr = eval("PART_ROW_"+partsId);
	if(partApplyTable.tBodies[0].rows.length>0){
		for(var p=0;p<partApplyTable.tBodies[0].rows.length;p++){
			if(partApplyTable.tBodies[0].rows[p].id == "PART_ROW_"+partsId){
				//alert("partApplyTable.tBodies[0].rows[p].id:"+partApplyTable.tBodies[0].rows[p].id);
				partApplyTable.tBodies[0].removeChild(deleteTr);
			}
		}
	}
	deleteTr.style.display = "none";
}




var ajax2 = new sack();
function insertLoanPartInfo(){// 零件保存
	if(document.forms[0].applyQty2.value == null){
		alert("请输入零件数量！");
		document.forms[0].applyQty2.focus();
	}else if(document.forms[0].applyQty2.value <= 0||document.forms[0].applyQty2.value >= 10000){
		alert("请填写合理的零件数量！");
		document.forms[0].applyQty2.select();
		document.forms[0].applyQty2.focus();
	}else{
		
			ajax2.reset();
			ajax2.setVar("repairNo", document.forms[0].repairNo.value);
			ajax2.setVar("stuffNo", document.forms[0].stuffNo2.value);
			ajax2.setVar("applyQty", document.forms[0].applyQty2.value);
			ajax2.setVar("warrantyType", "<%=rsf.getWarrantyType()%>");
			ajax2.setVar("method", "insertLoanPartInfo");
			ajax2.requestFile = "repairHandleAction.do";
			ajax2.method = "GET";
			ajax2.onCompletion = insertLoanPartInfoCompleted;
			ajax2.runAJAX();
			globalButton = event.srcElement;
			globalButton.disabled = true;
			showWaitDiv(800,1000);
		
	}
}

function insertLoanPartInfoCompleted(){ 
	var returnXml = ajax2.responseXML;
	//var xmlObj = returnXml.getElementsByTagName("xml")[0];
	//alert("xmlObj:"+xmlObj);
	var flag = returnXml.getElementsByTagName("flag")[0].firstChild.nodeValue;
	
	if(eval(flag)){
			var counter=0;
			var partsId = returnXml.getElementsByTagName("partsId")[counter].firstChild.nodeValue;
			var stuffNo = returnXml.getElementsByTagName("stuffNo")[counter].firstChild.nodeValue;
			
			var skuCode = unescape(returnXml.getElementsByTagName("skuCode")[counter].firstChild.nodeValue);
			var standard = unescape(returnXml.getElementsByTagName("standard")[counter].firstChild.nodeValue);
			var skuUnit = unescape(returnXml.getElementsByTagName("skuUnit")[counter].firstChild.nodeValue);
			var applyQty = returnXml.getElementsByTagName("applyQty")[counter].firstChild.nodeValue;
			var repairPartStatus = unescape(returnXml.getElementsByTagName("repairPartStatus")[counter].firstChild.nodeValue);
			var partVersion = returnXml.getElementsByTagName("partVersion")[0].firstChild.nodeValue;

			addLoanPartRowEnd(partsId,stuffNo,skuCode,standard,skuUnit,applyQty,repairPartStatus,partVersion);
		
	}else{
		//var result = returnXml.getElementsByTagName("result")[0].firstChild.nodeValue;
		alert("该零件无库存或数量不足！");
		
		
	}
	globalButton.disabled = false;
	globalButton = null;
	hideWaitDiv();
	resetLoanPartForm();
}
function resetLoanPartForm(){
	document.forms[0].stuffNo2.value = "";
	document.forms[0].skuCode2.value = "";
	document.forms[0].skuUnit2.value = "";
	document.forms[0].applyQty2.value = "";
	document.forms[0].loanPartAddButton.disabled = false;
}
function addLoanPartRowEnd(partsId,stuffNo,skuCode,standard,skuUnit,applyQty,repairPartStatus,partVersion){

	var oBody = partLoanTable.tBodies[0] ;
	var oNewRow=oBody.insertRow();
	oNewRow.className = "tableback1";
	oNewRow.id = "PART_ROW2_"+partsId;
	var i=0;
	
	//var oNewCell=oNewRow.insertCell(0).innerHTML="<input type=\"checkbox\" name=\"partsId\" value=\""+partsId+"\">";
	oNewRow.insertCell(i++).innerHTML=stuffNo;
	oNewRow.insertCell(i++).innerHTML=skuCode=="null"?"":skuCode;
	oNewRow.insertCell(i++).innerHTML=standard=="null"?"":standard;
	oNewRow.insertCell(i++).innerHTML=skuUnit;
	oNewRow.insertCell(i++).innerHTML=applyQty;
	oNewRow.insertCell(i++).innerHTML=repairPartStatus;
	var oNewCell=oNewRow.insertCell(i++);
	oNewCell.align="center";
	oNewCell.innerHTML="<input name=\"DelPartButton\" type=\"button\" class=\"button2\" value=\"取消\" onclick=\"cancelLoanPart("+partsId+",'"+partVersion+"')\">";
	
}



var ajaxD2 = new sack();
function cancelLoanPart(partsId,version){
	if(confirm("确定要取消这个零件吗？")){
		ajaxD2.setVar("partsId",partsId);
		ajaxD2.setVar("version",version);
		ajaxD2.setVar("method", "cancelLoanPart");
		ajaxD2.requestFile = "repairHandleAction.do";
		ajaxD2.method = "GET";
		ajaxD2.onCompletion = deleteLoanPartCompleted;
		ajaxD2.runAJAX();
		globalButton = event.srcElement;
		globalButton.disabled = true;
		showWaitDiv(800,1000);
	}
}
function deleteLoanPartCompleted(){
	var returnXml = ajaxD2.responseXML;
	var flag = returnXml.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(flag=='true'){
		var partsId = returnXml.getElementsByTagName("partsId")[0].firstChild.nodeValue;
		deleteLoanPartRowEnd(partsId);
	}else if(flag=='versionErr'){
		alert("该数据已被修改，请刷新后再重新操作");
	}else{
		alert("删除失败，请联系管理员！");
	}
	globalButton.disabled = false;
	globalButton = null;
	hideWaitDiv();
}
function deleteLoanPartRowEnd(partsId){
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






var ajax3 = new sack();
function insertLoanToolInfo(){// 零件保存
	if(document.forms[0].applyQty3.value == null){
		alert("请输入零件数量！");
		document.forms[0].applyQty3.focus();
	}else if(document.forms[0].applyQty3.value <= 0||document.forms[0].applyQty3.value >= 1000){
		alert("请填写合理的零件数量！");
		document.forms[0].applyQty3.select();
		document.forms[0].applyQty3.focus();
	}else{
		
			ajax3.reset();
			ajax3.setVar("repairNo", document.forms[0].repairNo.value);
			ajax3.setVar("stuffNo", document.forms[0].stuffNo3.value);
			ajax3.setVar("applyQty", document.forms[0].applyQty3.value);
			ajax3.setVar("warrantyType", "<%=rsf.getWarrantyType()%>");
			ajax3.setVar("method", "insertLoanToolInfo");
			ajax3.requestFile = "repairHandleAction.do";
			ajax3.method = "GET";
			ajax3.onCompletion = insertLoanToolInfoCompleted;
			ajax3.runAJAX();
			globalButton = event.srcElement;
			globalButton.disabled = true;
			showWaitDiv(800,1000);
		
	}
}

function insertLoanToolInfoCompleted(){ 
	var returnXml = ajax3.responseXML;
	//var xmlObj = returnXml.getElementsByTagName("xml")[0];
	//alert("xmlObj:"+xmlObj);
	var flag = returnXml.getElementsByTagName("flag")[0].firstChild.nodeValue;
	
	if(eval(flag)){
			var counter=0;
			var partsId = returnXml.getElementsByTagName("partsId")[counter].firstChild.nodeValue;
			var stuffNo = returnXml.getElementsByTagName("stuffNo")[counter].firstChild.nodeValue;
			var owner = unescape(returnXml.getElementsByTagName("owner")[counter].firstChild.nodeValue);
			var skuCode = unescape(returnXml.getElementsByTagName("skuCode")[counter].firstChild.nodeValue);
			var standard = unescape(returnXml.getElementsByTagName("standard")[counter].firstChild.nodeValue);
			var skuUnit = unescape(returnXml.getElementsByTagName("skuUnit")[counter].firstChild.nodeValue);
			var applyQty = returnXml.getElementsByTagName("applyQty")[counter].firstChild.nodeValue;
			var repairPartStatus = unescape(returnXml.getElementsByTagName("repairPartStatus")[counter].firstChild.nodeValue);
			var partVersion = returnXml.getElementsByTagName("partVersion")[0].firstChild.nodeValue;

			addLoanToolRowEnd(partsId,stuffNo,skuCode,standard,skuUnit,applyQty,repairPartStatus,partVersion,owner);
		
	}else{
		//var result = returnXml.getElementsByTagName("result")[0].firstChild.nodeValue;
		alert("该零件无库存或数量不足！");
		
		
	}
	globalButton.disabled = false;
	globalButton = null;
	hideWaitDiv();
	resetLoanToolForm();
}
function resetLoanToolForm(){
	document.forms[0].stuffNo3.value = "";
	document.forms[0].skuCode3.value = "";
	document.forms[0].skuUnit3.value = "";
	document.forms[0].applyQty3.value = "";
	document.forms[0].loanToolAddButton.disabled = false;
}
function addLoanToolRowEnd(partsId,stuffNo,skuCode,standard,skuUnit,applyQty,repairPartStatus,partVersion,owner){

	var oBody = toolsLoanTable.tBodies[0] ;
	var oNewRow=oBody.insertRow();
	oNewRow.className = "tableback1";
	oNewRow.id = "PART_ROW3_"+partsId;
	var i=0;
	
	//var oNewCell=oNewRow.insertCell(0).innerHTML="<input type=\"checkbox\" name=\"partsId\" value=\""+partsId+"\">";
	oNewRow.insertCell(i++).innerHTML=stuffNo;
	oNewRow.insertCell(i++).innerHTML=skuCode=="null"?"":skuCode;
	oNewRow.insertCell(i++).innerHTML=standard=="null"?"":standard;
	oNewRow.insertCell(i++).innerHTML=skuUnit;
	oNewRow.insertCell(i++).innerHTML=applyQty;
	oNewRow.insertCell(i++).innerHTML=repairPartStatus;
	//oNewRow.insertCell(i++).innerHTML=owner;
	var oNewCell=oNewRow.insertCell(i++);
	oNewCell.align="center";
	oNewCell.innerHTML="<input name=\"DelPartButton\" type=\"button\" class=\"button2\" value=\"取消\" onclick=\"cancelLoanTool("+partsId+",'"+partVersion+"')\">";
	
}



var ajaxD3 = new sack();
function cancelLoanTool(partsId,version){
	if(confirm("确定要取消这个零件吗？")){
		ajaxD3.setVar("partsId",partsId);
		ajaxD3.setVar("version",version);
		ajaxD3.setVar("method", "cancelLoanTool");
		ajaxD3.requestFile = "repairHandleAction.do";
		ajaxD3.method = "GET";
		ajaxD3.onCompletion = deleteLoanToolCompleted;
		ajaxD3.runAJAX();
		globalButton = event.srcElement;
		globalButton.disabled = true;
		showWaitDiv(800,1000);
	}
}
function deleteLoanToolCompleted(){
	var returnXml = ajaxD3.responseXML;
	var flag = returnXml.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(flag=='true'){
		var partsId = returnXml.getElementsByTagName("partsId")[0].firstChild.nodeValue;
		deleteLoanToolRowEnd(partsId);
	}else if(flag=='versionErr'){
		alert("该数据已被修改，请刷新后再重新操作");
	}else{
		alert("删除失败，请联系管理员！");
	}
	globalButton.disabled = false;
	globalButton = null;
	hideWaitDiv();
}
function deleteLoanToolRowEnd(partsId){
	deleteLoanToolRow(partsId);
}
function deleteLoanToolRow(partsId){
	//alert("deletePartRow.partsId:"+partsId);
	var deleteTr = eval("PART_ROW3_"+partsId);
	if(toolsLoanTable.tBodies[0].rows.length>0){
		for(var p=0;p<toolsLoanTable.tBodies[0].rows.length;p++){
			if(toolsLoanTable.tBodies[0].rows[p].id == "PART_ROW3_"+partsId){
				//alert("toolsLoanTable.tBodies[0].rows[p].id:"+toolsLoanTable.tBodies[0].rows[p].id);
				toolsLoanTable.tBodies[0].removeChild(deleteTr);
			}
		}
	}
	deleteTr.style.display = "none";
}




function doAddAttached(){
	var path = uploadNew.getMyfileName();
	if(path.length == 0){
		alert("请选择要添加的附件!");
		return;
	}
	globalButton = event.srcElement;
	globalButton.disabled = true;
	showWaitDiv(800,1000);
	uploadNew.doSaveWithForeignKey(<%=rsf.getRepairNo()%>);
}

function getFileType(){
	return document.forms[0].fileType.value;
}

function selectResult(path,attacheId,createDate,filePath){
	var oNewRow = uploadCompleteTable.insertRow(1);
	var oNewCell = oNewRow.insertCell(0);
	oNewCell.width="5%";
	oNewCell.innerHTML=uploadCompleteTable.rows.length -1;
	if(uploadCompleteTable.rows.length % 2 == 0){
		oNewRow.className = "tableback1";
	}else{
		oNewRow.className = "tableback2";
	}
	oNewCell = oNewRow.insertCell(1);
	oNewCell.width="5%";
	oNewCell.innerHTML="<a onClick=delRow(this,"+attacheId+") style=\"cursor: hand\"><u><b>删除&nbsp;&nbsp;</b></u></a>";
	oNewCell = oNewRow.insertCell(2);
	oNewCell.width="65%";
	oNewCell.innerHTML="<a href="+filePath+" style=\"cursor: hand\"  >"+path+"</a>";
	oNewCell = oNewRow.insertCell(3);
	oNewCell.width="10%";
	oNewCell.innerHTML= "维修附件";
	oNewCell = oNewRow.insertCell(4);
	oNewCell.width="15%";
	oNewCell.innerText= createDate;

	globalButton.disabled = false;
	globalButton = null;
	hideWaitDiv();
}



function delRow(obj,attacheId){
	//event.srcElement.disabled = true;
	var delTr = getParentByTagName(obj,"TR");
	for(k=0;k<uploadCompleteTable.rows.length;k++){
		if(uploadCompleteTable.rows(k) == delTr){
			uploadCompleteTable.deleteRow(k);
			break;
		}
	}
	fileDel(attacheId);
}

function fileDel(attacheId){
	var ajax2 = new sack(); 
	ajax2.setVar("attacheId",attacheId); 		//设置需要传到后台的参数
	ajax2.setVar("method", "fileDel");		//调用Action中的方法
	ajax2.requestFile = "attachedInfoAction.do";		//调用Action
	ajax2.method = "GET";				 //提交类型
	//ajax2.onCompletion = delResult;	 	//ajax交互完需要执行的函数
	ajax2.runAJAX(); 
}


//添加附件失败的时候回调的方法，其中filedCode为失败原因代码，具体参考/common/fileAdd.jsp中信息
function fileAddFailed(failedCode){
	globalButton.disabled = false;
	globalButton = null;
	hideWaitDiv();	
}

//-->
</script>
</head>

<body bgcolor="#ffffff" leftmargin="0" topmargin="0" rightmargin="0" bottommargin="0" marginwidth="0" marginheight="0" >

<jsp:include page=  "/common/waitDiv.jsp" flush="true" />
<jsp:include page=  "/common/countDiv.jsp" flush="true" />
<html:form action="repairAction.do?method=repairDetail" method="post">
<html:hidden property="repairNo"/>
<input name="currentStatus" type="hidden" value="<%=rsf.getCurrentStatus() %>">
<input name="version" type="hidden" value="<%=rsf.getVersion() %>">

<table width="90%" height="96%" border="0" align="center" cellpadding="0" cellspacing="6" class="content12">
  <tr> 
            
    <td height="556" align="right" valign="top"> 
    
    
   <div align="center" id="Layer9" style="position:absolute; left:35; top:80; width:912px; height:390px; z-index:1; visibility: hidden; "> 
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="content12">
     <tr> 
      <td valign="bottom"><table border="0" cellpadding="0" cellspacing="0">
		<tr> 
			<td width="30"><input name="labelButton" type="button" class="button_b" value="机器信息"></td>
			<td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','show','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="客户信息"></td>
			<td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','show','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="电诊信息"></td>
			<td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','show','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="维修零件信息"></td>
			<td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','show')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="携带零件/工具"></td>
			<td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','show','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="预计派工"></td>
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
				          <td width="15%">维修单号：</td>
				          <td width="20%">
				          	<%=rsf.getServiceSheetNo()==null?"":rsf.getServiceSheetNo()%>
				          	<input type="hidden" name="serviceSheetNo" value="<%=rsf.getServiceSheetNo()%>">
				          </td>
				          
				          <td width="15%">维修性质：</td>
				          <td width="20%"><%=DicInit.getSystemName("REPAIR_PROPERITES",rsf.getRepairProperites())%></td>
				          <td width="10%">保修期类型：</td>
				          <td width="20%"><%=DicInit.getSystemName("WARRANTY_TYPE",rsf.getWarrantyType())%></td>
				          
				         </tr>
						 <tr class="tableback1"> 
				          <td width="111">机型：</td>
				          <td width="130"><%=rsf.getModelCode()==null?"":rsf.getModelCode()%></td>
				          <td width="110">机身号：</td>
				          <td width="150"><%=rsf.getSerialNo()==null?"":rsf.getSerialNo()%></td>
						  <td colspan=2> <span class="content11"><b><%=DicInit.getSystemName("RR90",rsf.getRr90())%></b></span></td>
						  
				         </tr>
				      
						 <tr class="tableback2"> 
				          <td>验收日期：</td>
				          <td><%=rsf.getPurchaseDate()==null?"":Operate.formatYMDDate(rsf.getPurchaseDate())%></td>
				          
				          <td>保固书编号：</td>
				          <td><html:text property="warrantyCardNo"  styleClass="form" size="16" maxlength="30"/></td>
				          <td>生产厂商：</td>
				          <td><%=rsf.getManufacture()==null?"":rsf.getManufacture()%></td>
						 </tr>
				        
						<tr class="tableback1"> 
				          <td>客户要求到达日期：</td>
				          <td><%=rsf.getCustomerVisitDate()==null?"":Operate.formatYMDDate(rsf.getCustomerVisitDate())%></td>
				          
				          <td>预定修复日期：</td>
				          <td colspan="3"><%=rsf.getEstimateRepairDate()==null?"":Operate.formatYMDDate(rsf.getEstimateRepairDate())%></td>
				        </tr>
				        
						
				        <tr class="tableback2"> 
				          <td valign="top">客户描述故障：</td>
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
							      		<tr><td><span class="content14"><strong>照片信息</strong></span> </td>
							      	</tr>
							      	<tr> 
							      		<td height="1" colspan="10" background="<%= request.getContextPath()%>/images/i_line.gif"></td>
        							</tr>
									<tr >
										<input type="hidden" name="fileType" value="P">
										
										<td width="20%">上传照片：(100M)</td>
										<td width="80%"  height="20" valign="top">
											<table width="100%">
												<tr valign="top">
												<td width="57%">
											<IFRAME name="uploadNew" MARGINHEIGHT=0 MARGINWIDTH=0 FRAMEBORDER=0 SCROLLING=no  width="90%" height="20" SRC="<%= request.getContextPath()%>/common/fileAdd.jsp"></IFRAME>
												</td>
												<td align="left">
				      	    						<input type="button" class="button2" value="添加" onClick="doAddAttached()">
				      	    						</td>
				      	    						</tr>
				      	    				</table>
				      	    				</td>
									</tr>
									<tr>
										<td colspan="3">
											<table id="uploadCompleteTable" width="100%" border="0" cellspacing="1" cellpadding="1" class="content12">
												<tr class="tableback1">
													<td width="5%"><B>序号</B></td>
													<td width="10%"><B>删除</B></td>
													<td width="60%"><B>文件</B></td>
													<td width="10%"><B>文件类型</B></td>
													<td width="15%"><B>上传时间</B></td>
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
						<br>
								<table  width="100%" cellspacing="0" cellpadding="0" border="0" id="UpFileParentTable"> <!--start of UpFileParentTable-->
									<tr> 
							      		<td valign="top"> <table width="100%" border="0" cellpadding="1" cellspacing="1" class="content12">
							      		<tr><td><span class="content14"><strong>关联修理记录</strong></span> </td>
							      	</tr>
							      	<tr> 
							      		<td height="1" colspan="10" background="<%= request.getContextPath()%>/images/i_line.gif"></td>
        							</tr>
        							<%if(historyRepairList!=null&&historyRepairList.size()>1){ %>
        								<tr bgcolor="#CCCCCC"> 
				                        <TH><b>维修单号</b></TH>
				                        <TH><b>客户</b></TH>
				                        <TH ><b>电诊员</b></TH>
				                        <TH ><b>维修员</b></TH>
				                        <TH ><b>返修</b></TH>
										<TH ><b>类型</b></TH>
										<TH ><b>登记日期</b></TH>
				                        <TH ><b>修复日期</b></TH>
				                        <TH ><b>遗留问题</b></TH>
				                        <TH ><b>维修状态</b></TH>
				                      </tr>
        							<%
        							for(int i=1;i<historyRepairList.size();i++){
							  	        if(i%2==0) strTr="tableback2";
							  	        else strTr="tableback1";
							  			String[] temp=(String[])historyRepairList.get(i);
							  			String leave = "";
							  			if(!temp[13].isEmpty()){
							  				leave = temp[13].equals("Y")?"有":"无";
							  			}
        							 %>
        								<tr class="<%=strTr%>" > 
									      <td><%=temp[1]%></td>
									      <td ><%=temp[2]==null?"":temp[2]%></td>
									      <td ><%=temp[15]%></td>
									      <td ><%=temp[6]==null?"":temp[6]%></td>
									      <td ><%=temp[7].equals("null")?"":DicInit.getSystemName("RR90",temp[7])%></td>
									      <% if(!"T".equals(temp[16])){%>
									      <td ><%=DicInit.getSystemName("WARRANTY_TYPE",temp[8])%></td>
									      <%}else{ %>
									      <td ><%=DicInit.getSystemName("TUNING_TYPE",temp[8])%></td>
									      <%} %>
									      <td ><%=temp[14]%></td>
									      <td ><%=temp[10]%></td>
									      <td ><%=leave%></td>
									      <td ><%=DicInit.getSystemName("CURRENT_STATUS",temp[12])%></td>
									    </tr>
        							<%}} %>
        							</table>
									</td></tr>
        							</table>
        							
        							
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
         <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','show','Layer10','','hide','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="机器信息"></td>
         <td width="30"><input name="labelButton" type="button" class="button_b" value="客户信息"></td>
         <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','show','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="电诊信息"></td>
         <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','show','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="维修零件信息"></td>
         <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','show')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="携带零件/工具"></td>
		 <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','show','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="预计派工"></td>
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
         <td width="88">客户ID：</td>
         <td width="140"><%=rcif.getCustomerId()==null?"":rcif.getCustomerId().toString()%></td>
         <td width="99">客户名称： </td>
         <td width="242"><%=rcif.getCustomerName()==null?"":rcif.getCustomerName()%></td>
         <td width="98">联系人：</td>
         <td width="174"><%=rsf.getLinkman()==null?"":rsf.getLinkman()%></td>
        </tr>
        <tr class="tableback1"> 
         <td>手机：</td>
         <td><%=rcif.getMobile()==null?"":rcif.getMobile()%></td>
         <td>电话：</td>
         <td><%=rsf.getPhone()==null?"":rsf.getPhone()%></td>
         <td>传真：</td>
         <td><%=rsf.getFax()==null?"":rsf.getFax()%></td>
        </tr>
         <tr class="tableback2"> 
         <td>省份：</td>
         <td><%=rcif.getProvinceName()==null?"":rcif.getProvinceName()%></td>
         <td>城市：</td>
         <td><%=rcif.getCityName()==null?"":rcif.getCityName()%></td>
         <td>邮政编码：</td>
         <td><%=rcif.getPostCode()==null?"":rcif.getPostCode()%></td>
        <tr class="tableback1"> 
         <td>电子邮件：</td>
         <td><%=rcif.getEmail()==null?"":rcif.getEmail()%></td>
         <td>联系地址：</td>
         <td colspan="3"><%=rcif.getAddress()==null?"":rcif.getAddress()%></td>
        </tr>
        <tr class="tableback2"> 
         <td valign="top">备注：</td>
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
                  <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','show','Layer10','','hide','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="机器信息"></td>
                  <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','show','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="客户信息"></td>
                  <td width="30"><input name="labelButton" type="button" class="button_b"  value="电诊信息"></td>
                  <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','show','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="维修零件信息"></td>
                  <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','show')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="携带零件/工具"></td>
                  <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','show','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="预计派工"></td>

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
                  <td width="10%">电诊联系人：</td>
                  <td width="20%"><html:text property="dzLinkman"  styleClass="form" size="16" maxlength="30"/></td>
                  <td width="10%">电诊电话：</td>
                  <td width="20%"><html:text property="dzPhone"  styleClass="form" size="16" maxlength="30"/></td>
                  <td width="10%"></td>
                  <td width="20%"></td>
                </tr>
                <tr class="tableback2"> 
                  <td width="10%">预计工期：</td>
                  <td width="20%"><html:text property="expectedDuration"  styleClass="form" size="16" maxlength="3" onkeydown="javascript:f_onlynumber();"/></td>
                  <td width="10%">电诊备注：</td>
                  <td colspan=3><html:text property="dzRemark"  styleClass="form" size="80" maxlength="500"/></td>
                  
                </tr>
                <tr class="tableback1"> 
                  <td valign="top">电诊原因：<font color="red">*</font></td>
                  <td colspan="5"><html:textarea property="dzReason" rows="2" cols="8" styleClass="form" style="width:90%" ></html:textarea></td>
                </tr>
                <tr class="tableback2"> 
                  <td valign="top">电诊结果：<font color="red">*</font></td>
                  <td colspan="5"><html:textarea property="dzResult" rows="2" cols="8" styleClass="form" style="width:90%" ></html:textarea></td>
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
				<td width="120" height="18"><b>维修状态</b></td>
				<td><b>状态开始日期</b></td>
				<td><b>状态产生备注</b></td>
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
			<td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','show','Layer10','','hide','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="机器信息"></td>
			<td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','show','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="客户信息"></td>
			<td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','show','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="电诊信息"></td>
			<td width="30"><input name="labelButton" type="button" class="button_b" value="维修零件信息"></td>
			<td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','show')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="携带零件/工具"></td>
			<td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','show','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="预计派工"></td>
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
        <tr class="tableback2"> 
          <td width="160">料号：<font color='red'>*</font></td>
          <td width="200"><input name="stuffNo" type="text" class="form" size="25" ></td>	
          <td width="120">零件名称：</td>
          <td width="140"><input name="skuCode" type="text" class="form" size="16"  >&nbsp;
          	<a href="javascript:showPartInfo('skuCode')"><img src="googleImg/icon/search.gif" align="absmiddle" border="0"></a></td>
          <td width="100">计量单位：</td>
          <td width="400"><input name="skuUnit" type="text" class="form" size="16" readonly ><input name="shortCode" type="hidden"><input name="standard" type="hidden"> </td>
          
        </tr>
        <tr class="tableback1"> 
  	  		<td width="160">数量：<font color='red'>*</font></td>
          	<td width="200"><input name="applyQty" type="text" class="form" size="16" maxlength="20" onkeydown="f_onlynumber()"> </td>
  	  		<td width="160">零件类型：<font color='red'>*</font></td>
          	<td width="200"><select name="warrantyType" class="form" style="width:80" >
          			<%if(!AtomRoleCheck.checkRepairIW(rsf.getWarrantyType())){ %>
					<option value="O">保外</option>
					<%} %>
					<option value="I">保内</option>
				 </select> </td>
          	<td width="160"></td>
          	<td width="200"><input name="partAddButton" type="button" class="button2" value="添加" onclick="insertRepairPartInfo()"></td>
        </tr>
        
      
       </table>
       <br>
			<span class="content14"><strong>维修零件信息</strong></span> <br>
    <table height="120" width="100%" cellspacing="0" cellpadding="1" class="content12" border="0" id="partApplyedParentTable"><!--start of partApplyedParentTable-->
	<tr><td width="100%">
	<div class="scrollDiv" id="partApplyedScrollDiv"><!--start of partApplyedScrollDiv-->
       <table width="100%" border="0" cellpadding="0" cellspacing="1" class="content12" id="partApplyTable">
        <thead>
        <tr bgcolor="#CCCCCC"> 
         <td><strong>零件料号</strong></td>
         <td><strong>零件名称</strong></td>
         <td><strong>规格</strong></td>
         <td><strong>单位</strong></td>
         <td><strong>类型</strong></td>
         <td><strong>数量</strong></td>
         <td><strong>删除</strong></td>
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
         <td width="6%" align="center"><input name="delPartButton" type="button" class="button2" value="删除" onclick="deleteAPart('PART_ROW_<%=part.getPartsId()%>')"></td>
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
			<td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','show','Layer10','','hide','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="机器信息"></td>
			<td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','show','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="客户信息"></td>
			<td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','show','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="电诊信息"></td>
			<td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','show','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="维修零件信息"></td>
			<td width="30"><input name="labelButton" type="button" class="button_b" value="携带零件/工具"></td>
			<td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','show','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="预计派工"></td>
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
      <tr><td><span class="content14"><strong>携带零件申请</strong></span> </td></tr>
      <tr> 
              <td height="1" colspan="10" background="<%= request.getContextPath()%>/images/i_line.gif"></td>
            </tr>
        <tr class="tableback2"> 
          <td width="160">料号：<font color='red'>*</font></td>
          <td width="200"><input name="stuffNo2" type="text" class="form" size="25" ></td>	
          <td width="120">零件名称：</td>
          <td width="140"><input name="skuCode2" type="text" class="form" size="16"  >&nbsp;
          	<a href="javascript:showPartInfoWithStock('skuCode2','P')"><img src="googleImg/icon/search.gif" align="absmiddle" border="0"></a></td>
          <td width="100">计量单位：</td>
          <td width="400"><input name="skuUnit2" type="text" class="form" size="16" readonly > </td>
        </tr>
        <tr class="tableback1"> 
  	  		<td width="160">携带数量：<font color='red'>*</font></td>
          	<td width="200"><input name="applyQty2" type="text" class="form" size="16" maxlength="20" onkeydown="f_onlynumber()"> </td>
  	  		<td >规格：</td>
    		<td ><input class="form" name="standard2"  maxlength="32"  size="16" readonly  ></td>
          	<td width="160"></td>
          	<td width="200"  align="right"><input name="loanPartAddButton" type="button" class="button2" value="添加" onclick="insertLoanPartInfo()"></td>
        </tr>
        
       </table>
       <br>
			
    <table height="150" width="100%" cellspacing="0" cellpadding="1" class="content12" border="0" id="partLoanParentTable">
	<tr><td width="100%">
	<div class="scrollDiv" id="partLoanScrollDiv">
       <table width="100%" border="0" cellpadding="0" cellspacing="1" class="content12" id="partLoanTable">
        <thead>
        <tr bgcolor="#CCCCCC"> 
         <td><strong>零件料号</strong></td>
         <td><strong>零件名称</strong></td>
         <td><strong>规格</strong></td>
         <td><strong>单位</strong></td>
         <td><strong>数量</strong></td>
         <td><strong>状态</strong></td>
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
         <%if("L".equals(partStatus)){ %>
         <input name="returnButton" type="button" class="button2" value="取消" onclick="cancelLoanPart(<%=part.getPartsId()%>,<%=part.getVersion()%>)">
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
      <tr><td><span class="content14"><strong>携带工具申请</strong></span> </td></tr>
      <tr> 
              <td height="1" colspan="10" background="<%= request.getContextPath()%>/images/i_line.gif"></td>
            </tr>
        <tr class="tableback2"> 
          <td width="160">工具料号：<font color='red'>*</font></td>
          <td width="200"><input name="stuffNo3" type="text" class="form" size="25" ></td>	
          <td width="120">工具名称：</td>
          <td width="140"><input name="skuCode3" type="text" class="form" size="16"  >&nbsp;
          	<a href="javascript:showPartInfoWithStock('skuCode3','T')"><img src="googleImg/icon/search.gif" align="absmiddle" border="0"></a>
          </td>
          <td width="100">工具单位：</td>
          <td width="400"><input name="skuUnit3" type="text" class="form" size="16" readonly > </td>
        </tr>
        <tr class="tableback1"> 
  	  		<td width="160">携带数量：<font color='red'>*</font></td>
          	<td width="200"><input name="applyQty3" type="text" class="form" size="16" maxlength="20" onkeydown="f_onlynumber()"> </td>
    		<td >规格：</td>
    		<td ><input class="form" name="standard3"  maxlength="32"  size="16" readonly  ></td>
          	<td width="160"></td>
          	<td width="200" align="right"><input name="loanToolAddButton" type="button" class="button2" value="添加" onclick="insertLoanToolInfo()"></td>
        </tr>
        
       </table>
       <br>
    <table height="120" width="100%" cellspacing="0" cellpadding="1" class="content12" border="0" id="toolsLoanParentTable">
	<tr><td width="100%">
	<div class="scrollDiv" id="toolsLoanScrollDiv">
       <table width="100%" border="0" cellpadding="0" cellspacing="1" class="content12" id="toolsLoanTable">
        <thead>
        <tr bgcolor="#CCCCCC"> 
         				<td ><strong>&nbsp;工具料号</strong></td>
                        <td><strong>工具名称</strong></td>
                        <td ><strong>规格</strong></td>
                        <td><strong>单位</strong></td>
                        <td ><strong>数量</strong></td>
                        <td ><strong>状态</strong></td>
         <td><strong>&nbsp;</strong></td>
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

         <td width="6%" align="center">
         <%if("L".equals(partStatus)){ %>
         <input name="returnButton2" type="button" class="button2" value="取消" onclick="cancelLoanTool(<%=part.getPartsId()%>,<%=part.getVersion()%>)">
         <%} else{%>
         	&nbsp;
         <%} %>
         </td>
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
         <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','show','Layer10','','hide','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="机器信息"></td>
         <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','show','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="客户信息"></td>
         <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','show','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="电诊信息"></td>
         <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','show','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="维修零件信息"></td>
         <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','show')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="携带零件/工具"></td>
         <td width="30"><input name="labelButton" type="button" class="button_b" value="预计派工"></td>
        
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
        <tr class="tableback2"> 
         <td width="100">预计人数：<font color="red">*</font></td>
         <td width="262"><html:text property="repairmanNum" styleClass="form" size="12" maxlength="3" onkeydown="javascript:f_onlynumber();"/></td>
         <td width="100">工时(单)：<font color="red">*</font></td>
         <td ><html:text property="workingHours" styleClass="form" size="12"  maxlength="4" onkeydown="javascript:f_onlynumber();"/></td>
        </tr>
        <tr class="tableback1"> 
         <td>车船票：<font color="red">*</font></td>
         <td><html:text property="ticketsAllCosts" styleClass="form" size="12" onkeydown="javascript:f_onlymoney();"  maxlength="8"/></td>
         <td>人工费：<font color="red">*</font></td>
         <td><html:text property="laborCosts" styleClass="form" size="12" maxlength="8" onkeydown="javascript:f_onlymoney();"/></td>
        </tr>
     
        
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
   </div>
   
   <br>
   <br>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="content12">
	    <tr>
	       <td width="30%">维修>电话诊断>维修单明细</td>
	       
	    </tr>
	</table>
	<div align="center"  style="position:absolute; left:35;  width:912px;  z-index:1; "> 
		<table border="0" width="100%" cellpadding="0" cellspacing="0" class="content12">
		  <tr> 
		 		
			  <td align="right"> 
			    <input name="endRepairButton" type="button" onClick="transferSale()" class="button4" value="转销售单">
			  	<input name="endRepairButton" type="button" onClick="dzComplete()" class="button4" value="电诊解决">
			  	<input name="noRepairButton" type="button" onClick="prepareDoNotRepair()" class="button4" value="不修理"> 
				<input name="saveForm" type="button" class="button2" onClick="f_save()" value="暂存">
			  	<input name="closeButton" type="button" class="button2" value="关闭" onclick="window.close()"> 
			  </td>
			</tr>
	  </table>
	</div>   
   <br>
  </td>
   </tr>
  </table></html:form>

</body>
<script>
new AutoTip.AutoComplete("stuffNo", function() {
	 return "partInfoAction.do?method=getPartInfo&inputValue=" + escape(this.text.value);
});
new AutoTip.AutoComplete("stuffNo2", function() {
	 return "partInfoAction.do?method=getPartInfo4Loan&inputValue=" + escape(this.text.value);
});
new AutoTip.AutoComplete("stuffNo3", function() {
	 return "partInfoAction.do?method=getToolInfo4Loan&inputValue=" + escape(this.text.value);
});
</script>
</html>

<%}catch(Exception e){
	e.printStackTrace();
}%> 