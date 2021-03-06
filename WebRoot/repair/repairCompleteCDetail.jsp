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
	String[] irisTree = (String[])request.getAttribute("irisTree");
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
<title>修复明细</title>
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
<SCRIPT  src="js/tree/client_ini.js"></SCRIPT>
<SCRIPT  src="js/tree/xtree.js"></SCRIPT> 
<SCRIPT language="javascript" src="js/commonSelect.js"></SCRIPT>
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

function repairComplete(){
	if(f_chk('Y')){
		if(confirm("确定修复完成吗？")){
			
			showWaitDiv(800,1000);
			document.forms[0].action="repairHandleAction.do?method=repairEnd&unCompleteQuickStatus=N";
			document.forms[0].submit();	
		}
	}
}

function repairUnComplete(){
	if(f_chk('N')){
		if(confirm("本次修理完成但有遗留问题？")){
			showWaitDiv(800,1000);
			document.forms[0].action="repairHandleAction.do?method=repairEnd&unCompleteQuickStatus=Y";
			document.forms[0].submit();	
		}
	}
}



function f_chk(flag){ 
		
	if(document.forms[0].repairContent.value == ""){ //判断是否填写维修内容
		alert("必须填写维修内容！");
		MM_showHideLayers('Layer1','','show','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','hide');
		document.forms[0].repairContent.focus();
		return false;
	}	
	if(document.forms[0].actualOnsiteDateStr.value == ""){ 
		alert("必须填写实际上门日期！");
		MM_showHideLayers('Layer1','','show','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','hide');
		document.forms[0].actualOnsiteDateStr.focus();
		return false;
	}
	if(document.forms[0].actualRepairedDateStr.value == ""){ 
		alert("必须填写实际修复日期！");
		MM_showHideLayers('Layer1','','show','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','hide');
		document.forms[0].actualRepairedDateStr.focus();
		return false;
	}
	if(!checkInputDate(document.forms[0].actualOnsiteDateStr)){
		MM_showHideLayers('Layer1','','show','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','hide');
		document.forms[0].actualOnsiteDateStr.focus();
		return false;
	}
	if(!checkInputDate(document.forms[0].actualRepairedDateStr)){
		MM_showHideLayers('Layer1','','show','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','hide');
		document.forms[0].actualRepairedDateStr.focus();
		return false;
	}
	
	if(document.forms[0].receptionRemark.value != ""){
		if(!f_maxLength(document.forms[0].receptionRemark,'备注',800)){
			return false;
		}
	}
	
	if(flag=='N'){
		if(document.forms[0].leaveProblem.value == ""){ 
			alert("必须填写遗留问题！");
			MM_showHideLayers('Layer1','','show','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','hide');
			document.forms[0].leaveProblem.focus();
			return false;
		}	
		if(document.forms[0].tobeMatter.value == ""){
			alert("必须填写未尽事宜！");
			MM_showHideLayers('Layer1','','show','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','hide');
			document.forms[0].tobeMatter.focus();
			return false;
		}	
	}
	
	 var allValue="";
   	 <%for(int i=0;i<irisTree.length-1;i++){   %>
		var retValue<%=i%>=tree<%=i%>.getCheckedValues(); 
   	 	if(retValue<%=i%>!='') allValue+=","+retValue<%=i%>;
   	 <%}%>
   	 //alert(allValue);
   	 if(allValue!=''){
   	 	allValue=allValue.substring(1);
   	 	document.forms[0].irisIds.value=allValue;
   	 }else{
   	 	MM_showHideLayers('Layer1','','show','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','hide');
   	 	alert("请选先确认障位置！");
   	 	return false;
   	 }
   	 
   	createIrisJson();
		
	return true;
}


function f_save(){
	var allValue="";
   	 <%for(int i=0;i<irisTree.length-1;i++){   %>
		var retValue<%=i%>=tree<%=i%>.getCheckedValues(); 
   	 	if(retValue<%=i%>!='') allValue+=","+retValue<%=i%>;
   	 <%}%>
   	 
   	 if(allValue!=''){
   	 	allValue=allValue.substring(1);
   	 	document.forms[0].irisIds.value=allValue;
   	 }
   	 createIrisJson();
	
	showWaitDiv(800,1000);
	
	document.forms[0].action="repairHandleAction.do?method=saveRepair&flag=cpSave&repairNo="+document.forms[0].repairNo.value+"&version="+document.forms[0].version.value;
	document.forms[0].submit();	
}


function showIrisDialog(content,desc,name,id){
	var irisContent=window.showModalDialog("repairAction.do?method=irisContent&flag=add&irisContent="+(content)+
			"&desc="+desc+"&name="+name+"&id="+id+"&Rnd="+Math.random(),"","dialogHeight: 180px; dialogWidth: 500px; edge: Sunken; center: Yes; help: No; resizable: No; status: Yes;");

	if(irisContent!=null){
		eval("document.forms[0].iris_"+irisContent[1]).value=irisContent[0];
	}

}

function createIrisJson(){
	
	var jsonVal = '{ "iristree": [{"14": "'+document.forms[0].iris_14.value+'", "15":"'+document.forms[0].iris_15.value+'", "17": "'+
		document.forms[0].iris_17.value+'", "18": "'+document.forms[0].iris_18.value+'", "49": "'+
		document.forms[0].iris_49.value+'", "50": "'+document.forms[0].iris_50.value+'", "62": "'+
		document.forms[0].iris_62.value+'", "63": "'+document.forms[0].iris_63.value+'" }]}';
	
	document.forms[0].irisValues.value = jsonVal;
	//alert(jsonVal);
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

function getFileType(){
	return document.forms[0].fileType.value;
}


var addRmFlag = false;
function f_add_rm_line(){
	if(addRmFlag==true){
		alert("请先保存已添加的数据");
		return;
	} 
	var oBody = repairManTable.tBodies[0] ;
	var oNewRow=oBody.insertRow();
	oNewRow.className = "tableback1";
	//oNewRow.id = "PART_ROW2_"+partsId;
	var i=0;
	
	
	var selectRM = "<select name='repairManAjaxAdd' class='form'>";
	selectRM+="<option value='' selected>==请选择==</option>";
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
	oNewRow.insertCell(i++).innerHTML="0";
	oNewRow.insertCell(i++).innerHTML="<input name='workingHoursActualAjaxAdd' type='text' class='form' size='10' onkeydown='javascript:f_onlynumber();'>";
	
	oNewRow.insertCell(i++).innerHTML="<input name='travelFeeAjaxAdd' type='text' class='form' size='10' onkeydown='javascript:f_onlymoney();'>";
	oNewRow.insertCell(i++).innerHTML="0";
	oNewRow.insertCell(i++).innerHTML="<input name='laborCostsActualAjaxAdd' type='text' class='form' size='10' onkeydown='javascript:f_onlymoney();'>";
	oNewRow.insertCell(i++).innerHTML=selectCondition;
	oNewRow.insertCell(i++).innerHTML="<input name='remarkAjaxAdd' type='text' class='form' size='20'>";
	oNewRow.insertCell(i++).innerHTML="<td><input type='button' class='button2' onClick='f_insert_rm()' value='保存'></td>";
	oNewRow.insertCell(i++).innerHTML="&nbsp;";
			
	
	addRmFlag = true;
}


var ajaxChk = new sack();
function f_insert_rm(){
	if(f_chk()){
		ajaxChk.setVar("repairNo", document.forms[0].repairNo.value);
		ajaxChk.setVar("repairManAjaxAdd", document.forms[0].repairManAjaxAdd.value);
		ajaxChk.setVar("departDateAjaxAdd", document.forms[0].departDateAjaxAdd.value);
		ajaxChk.setVar("arrivalDateAjaxAdd", document.forms[0].arrivalDateAjaxAdd.value);
		ajaxChk.setVar("returnDateAjaxAdd", document.forms[0].returnDateAjaxAdd.value);
		ajaxChk.setVar("workingHoursActualAjaxAdd", document.forms[0].workingHoursActualAjaxAdd.value);
		ajaxChk.setVar("travelFeeAjaxAdd", document.forms[0].travelFeeAjaxAdd.value);
		ajaxChk.setVar("laborCostsActualAjaxAdd", document.forms[0].laborCostsActualAjaxAdd.value);
		ajaxChk.setVar("repairConditionAjaxAdd", document.forms[0].repairConditionAjaxAdd.value);
		ajaxChk.setVar("remarkAjaxAdd", document.forms[0].remarkAjaxAdd.value);
		
		ajaxChk.setVar("method", "saveRepairManAjax");
		ajaxChk.requestFile = "repairHandleAction.do";
		ajaxChk.method = "POST";
		ajaxChk.onCompletion = f_save_rm_end;
		ajaxChk.runAJAX();
	}
}

function f_save_rm_end(){
	var returnXml = ajaxChk.responseXML;
	var result = returnXml.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(result=="true"){
		alert("保存成功！");
		location.reload();
	}else{
		alert("保存出错，请联系管理员！");
	}
}


function f_chk(){ 
	if(f_isNull(document.forms[0].repairManAjaxAdd,'维修员')
			&&f_isNull(document.forms[0].departDateAjaxAdd,'出发日期')&&checkInputDate(document.forms[0].departDateAjaxAdd)
			&&f_isNull(document.forms[0].arrivalDateAjaxAdd,'到达日期')&&checkInputDate(document.forms[0].arrivalDateAjaxAdd)
			&&f_isNull(document.forms[0].returnDateAjaxAdd,'返回日期')&&checkInputDate(document.forms[0].returnDateAjaxAdd)
			&&f_isNull(document.forms[0].workingHoursActualAjaxAdd,'实际工时')
			&&f_isNull(document.forms[0].travelFeeAjaxAdd,'车船票')
			&&f_isNull(document.forms[0].laborCostsActualAjaxAdd,'实际人工费')
		){
		return true;
	}
	return false;
}


function f_del_rm(id){
	ajaxChk.setVar("id", id);
	ajaxChk.setVar("method", "delRepairManAjax");
	ajaxChk.requestFile = "repairHandleAction.do";
	ajaxChk.method = "GET";
	ajaxChk.onCompletion = f_del_rm_end;
	ajaxChk.runAJAX();
	
}

function f_del_rm_end(){
	var returnXml = ajaxChk.responseXML;
	var result = returnXml.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(result=="true"){
		alert("删除成功！");
		location.reload();
	}else{
		alert("删除出错，请联系管理员！");
	}
}


function f_update_rm(id){
	
	if(document.forms[0].travelId==null){
 		return;
    }
	
	ajaxChk.setVar("id", id);
	
	var len = document.forms[0].travelId.length;
	if(len>1){
	    var tag = 0;
		for(var i=0;i<len;i++){
			if(document.forms[0].travelId[i].value==id){   
				ajaxChk.setVar("repairMan", document.forms[0].repairMan[i].value);
				ajaxChk.setVar("arrivalDate", document.forms[0].arrivalDate[i].value);
				ajaxChk.setVar("returnDate", document.forms[0].returnDate[i].value);
				ajaxChk.setVar("workingHoursActual", document.forms[0].workingHoursActual[i].value);
				ajaxChk.setVar("travelFee", document.forms[0].travelFee[i].value);
				ajaxChk.setVar("laborCostsActual", document.forms[0].laborCostsActual[i].value);
				ajaxChk.setVar("repairCondition", document.forms[0].repairCondition[i].value);
				ajaxChk.setVar("remark", document.forms[0].remark[i].value);		
				break;
			}
		}
	} else {
		if(document.forms[0].travelId.value==id){
			ajaxChk.setVar("repairMan", document.forms[0].repairMan.value);
			ajaxChk.setVar("arrivalDate", document.forms[0].arrivalDate.value);
			ajaxChk.setVar("returnDate", document.forms[0].returnDate.value);
			ajaxChk.setVar("workingHoursActual", document.forms[0].workingHoursActual.value);
			ajaxChk.setVar("travelFee", document.forms[0].travelFee.value);
			ajaxChk.setVar("laborCostsActual", document.forms[0].laborCostsActual.value);
			ajaxChk.setVar("repairCondition", document.forms[0].repairCondition.value);
			ajaxChk.setVar("remark", document.forms[0].remark.value);
	 	}
	}
	
	
	
	ajaxChk.setVar("method", "updateRepairManAjax");
	ajaxChk.requestFile = "repairHandleAction.do";
	ajaxChk.method = "POST";
	ajaxChk.onCompletion = f_update_rm_end;
	ajaxChk.runAJAX();
}

function f_update_rm_end(){
	var returnXml = ajaxChk.responseXML;
	var result = returnXml.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(result=="true"){
		alert("修改成功！");
		location.reload();
	}else{
		alert("修改出错，请联系管理员！");
	}
}

//-->
</script>
</head>

<body bgcolor="#ffffff" leftmargin="0" topmargin="0" rightmargin="0" bottommargin="0" marginwidth="0" marginheight="0" >

<jsp:include page=  "/common/waitDiv.jsp" flush="true" />
<jsp:include page=  "/common/countDiv.jsp" flush="true" />
<html:form action="repairAction.do?method=repairCompleteCDetail" method="post">
<html:hidden property="repairNo"/>
<input name="irisIds" type="hidden">
<input name="version" type="hidden" value="<%=rsf.getVersion() %>">
<input name="irisValues" type="hidden">

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
			<td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','show','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="维修信息"></td>
			<td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','show','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="维修零件信息"></td>
			<td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','show')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="携带零件/工具"></td>
			<td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','show','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="维修派工信息"></td>
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
				          <td><bean:write property="warrantyCardNo"  name="repairServiceForm"/></td>
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
         <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','show','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="维修信息"></td>
         <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','show','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="维修零件信息"></td>
         <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','show')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="携带零件/工具"></td>
		 <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','show','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="维修派工信息"></td>
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
        
       </table>
       <br>
       <table HEIGHT="170" width="100%" cellspacing="0" cellpadding="0" border="0" id="repairStatusTable"><!--start of repairStatusParentTable-->
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
                  <td width="30"><input name="labelButton" type="button" class="button_b"  value="维修信息"></td>
                  <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','show','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="维修零件信息"></td>
                  <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','show')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="携带零件/工具"></td>
                  <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','show','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="维修派工信息"></td>

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
                  <td width="20%"><bean:write property="dzLinkman" name="repairServiceForm" /></td>
                  <td width="10%">电诊电话：</td>
                  <td width="20%"><bean:write property="dzPhone"  name="repairServiceForm"/></td>
            
                </tr>
                <tr class="tableback2"> 
                  <td width="10%">预计工期：</td>
                  <td width="20%"><bean:write property="expectedDuration"   name="repairServiceForm"/></td>
                  <td width="10%">电诊备注：</td>
                  <td ><bean:write property="dzRemark"  name="repairServiceForm"/></td>
                  
                </tr>
                <tr class="tableback1"> 
                  <td valign="top">电诊原因：</td>
                  <td colspan="3"><bean:write property="dzReason"  name="repairServiceForm"/></td>
                </tr>
                <tr class="tableback2"> 
                  <td valign="top">电诊结果：</td>
                  <td colspan="3"><bean:write property="dzResult" name="repairServiceForm"/></td>
                </tr>
       			
       			<tr> 
	              <td height="3" colspan="10" background="<%= request.getContextPath()%>/images/i_line.gif"></td>
	            </tr>
	            
	            <tr class="tableback1"> 
                  <td valign="top">修理内容：<font color='red'>*</font></td>
                  <td colspan="3"><html:textarea property="repairContent" rows="6" cols="8" styleClass="form" style="width:100%" ></html:textarea></td>
                </tr>
                
               <tr class="tableback2">
                	<td>实际上门日期：<font color='red'>*</font></td>
                	<td><html:text	property="actualOnsiteDateStr" styleClass="form" size="16" onkeydown='javascript:input_date();' /></td>
                    <td>实际修复日期：<font color='red'>*</font></td>
                    <td><html:text	property="actualRepairedDateStr" styleClass="form" size="16" onkeydown='javascript:input_date();' />  </td>
                </tr>
                
                <tr class="tableback1"> 
                  <td valign="top">遗留问题：</td>
                  <td colspan="3"><html:textarea property="leaveProblem" rows="2" cols="8" styleClass="form" style="width:100%" ></html:textarea></td>
                </tr>
                <tr class="tableback2"> 
                  <td valign="top">未尽事宜：</td>
                  <td colspan="3"><html:textarea property="tobeMatter" rows="2" cols="8" styleClass="form" style="width:100%" ></html:textarea></td>
                </tr>
            	<tr class="tableback1"> 
                  <td valign="top">备注：</td>
                  <td colspan="3"><html:textarea property="receptionRemark" rows="2" cols="8" styleClass="form" style="width:100%" ></html:textarea></td>
                </tr>
            
					 
			  <tr> 
	            <td height="2" colspan="8" bgcolor="#ffffff"></td>
	          </tr>
	          <tr> 
	            <td height="1" colspan="8" bgcolor="#677789"></td>
	          </tr>
	          </table>
	          <%if("X".equals(rsf.getCurrentStatus())){ %>
	          <br>
	          <table width="100%" border="0" cellpadding="1" cellspacing="1" class="content12">
	            <tr> 
	              <td height="1" colspan="10" background="<%= request.getContextPath()%>/images/i_line.gif"></td>
	            </tr>
	            
	            <tr class="tableback1"> 
                  <td width="10%"><font color="red">审批意见：</font></td>
                  <td colspan="3"><xmp><bean:write property="confirmSymptom"  name="repairSearchForm" /></xmp></td>
                </tr>
	            
           		<tr> 
	            <td height="2" colspan="8" bgcolor="#ffffff"></td>
	          </tr>
	          <tr> 
	            <td height="1" colspan="8" bgcolor="#677789"></td>
	          </tr>
   			</table>
   			<%} %>
   			 <br>
	        <table width="100%" border="0" cellpadding="1" cellspacing="1" class="content12">
	            <tr> 
	              <td height="1" colspan="10" background="<%= request.getContextPath()%>/images/i_line.gif"></td>
	            </tr>
           
   			</table>
	
	
	<table  width="100%" cellspacing="0" cellpadding="0" border="0" id="irisTree">
	
	
	
                <tr > 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0" >
                      <tr> 
                        <td width="350" height="129" valign="top"> <table width="90%" border="0" cellpadding="0" cellspacing="1" class="content12">
                            <tr> 
                              <td height="4" bgcolor="#677789"></td>
                            </tr>
                            <tr bgcolor="#CCCCCC"> 
                              <td><strong>电气</strong></td>
                            </tr>
                            <tr class="tableback2" > <td >
		<SCRIPT LANGUAGE="JavaScript">
			treeConfig.openRootIcon='images/xp/rootIcon.png';
			
                            <%
                            out.println(irisTree[0]);
                            %>
                            
		</SCRIPT>
                            </td></tr>
                            <tr> 
                              <td height="2" bgcolor="#ffffff"></td>
                            </tr>
                            <tr> 
                              <td height="1" bgcolor="#677789"></td>
                            </tr>
                          </table></td>
                          <td width="1" background="images/i_line_2.gif"></td>
                        <td width="350" align="center" valign="top"> <table width="90%" border="0" cellpadding="0" cellspacing="1" class="content12">
                            <tr> 
                              <td height="4" bgcolor="#677789"></td>
                            </tr>
                            <tr bgcolor="#CCCCCC"> 
                              <td><strong>机械</strong></td>
                            </tr>
                            <tr class="tableback2"> <td >
		<SCRIPT LANGUAGE="JavaScript">
			
                            <%
                            out.println(irisTree[1]);
                            %>
                            
		</SCRIPT>
                            </td></tr>
                            <tr> 
                              <td height="2" bgcolor="#ffffff"></td>
                            </tr>
                            <tr> 
                              <td height="1" bgcolor="#677789"></td>
                            </tr>
                          </table></td>
                          
                       
                      </tr>
                      
	</table>
		
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
			<td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','show','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="维修信息"></td>
			<td width="30"><input name="labelButton" type="button" class="button_b" value="维修零件信息"></td>
			<td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','show')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="携带零件/工具"></td>
			<td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','show','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="维修派工信息"></td>
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
      	<tr><td><span class="content14"><strong>维修零件信息</strong></span>
			<tr> 
              <td height="1" colspan="10" background="images/i_line.gif"></td>
            </tr>
    <table height="280" width="100%" cellspacing="0" cellpadding="1" class="content12" border="0" id="partApplyedParentTable"><!--start of partApplyedParentTable-->
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
         <td><strong>状态</strong></td>
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
			<td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','show','Layer10','','hide','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="机器信息"></td>
			<td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','show','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="客户信息"></td>
			<td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','show','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="维修信息"></td>
			<td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','show','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="维修零件信息"></td>
			<td width="30"><input name="labelButton" type="button" class="button_b" value="携带零件/工具"></td>
			<td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','show','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="维修派工信息"></td>
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
      <tr><td><span class="content14"><strong>携带零件</strong></span> </td></tr>
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
         <td><strong>零件料号</strong></td>
         <td><strong>零件名称</strong></td>
         <td><strong>规格</strong></td>
         <td><strong>单位</strong></td>
         <td><strong>数量</strong></td>
         <td><strong>状态</strong></td>
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
      <tr><td><span class="content14"><strong>携带工具</strong></span> </td></tr>
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
         				<td ><strong>&nbsp;工具料号</strong></td>
                        <td><strong>工具名称</strong></td>
                        <td ><strong>规格</strong></td>
                        <td><strong>单位</strong></td>
                        <td ><strong>数量</strong></td>
                        <td ><strong>状态</strong></td>
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
         <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','show','Layer10','','hide','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="机器信息"></td>
         <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','show','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="客户信息"></td>
         <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','show','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="维修信息"></td>
         <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','show','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="维修零件信息"></td>
         <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','show')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="携带零件/工具"></td>
         <td width="30"><input name="labelButton" type="button" class="button_b" value="维修派工信息"></td>
        
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
       <br> <span class="content14"><b>维修员信息</b></span><br> 
	<table height="180" width="100%" cellspacing="0" cellpadding="1" class="content12" border="0" id="irisParentTable">
	<tr><td width="100%">
	<div class="scrollDiv" id="IRISScrollDiv">
       <table width="100%" border="0" cellpadding="0" cellspacing="1" class="content12" id="repairManTable">
        <thead>
        <tr bgcolor="#CCCCCC"> 
         <td height="18"><b>维修员</b></td>
         <td><b>出发日期</b></td>
         <td><b>到达日期</b></td>
         <td><b>返回日期</b></td>
         
         <td><b>预计工时</b></td>
         <td><b>实际工时</b></td>
        
         <td><b>车船票</b></td>
         <td><b>预计人工费</b></td>
         <td><b>实际人工费</b></td>
         <td><b>维修情况</b></td>
         <td><b>备注</b></td>
         <%if(rsf.getCurrentStatus().equals("X")){ %><td><b>&nbsp;</b></td><td><b>&nbsp;</b></td><%} %>
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
			if(!rsf.getCurrentStatus().equals("X")){
%>        
        <tr class="<%=strTr%>" > 
         <td><%=rmi.getRepairManName()%> </td>
         <td><%=rmi.getDepartDate()==null?"":Operate.formatYMDDate(rmi.getDepartDate())%></td>
         <td><%=rmi.getArrivalDate()==null?"":Operate.formatYMDDate(rmi.getArrivalDate())%></td>
         <td><%=rmi.getReturnDate()==null?"":Operate.formatYMDDate(rmi.getReturnDate())%></td>
         <td><%=rmi.getWorkingHours()==null?"":rmi.getWorkingHours()%></td>
         <td><%=rmi.getWorkingHoursActual()==null?"":rmi.getWorkingHoursActual()%></td>
         <td><%=rmi.getTravelFee()==null?"":Operate.toFix(rmi.getTravelFee(), 2)%></td>
         <td><%=rmi.getLaborCosts()==null?"":Operate.toFix(rmi.getLaborCosts(), 2)%></td>
         <td><%=rmi.getLaborCostsActual()==null?"":Operate.toFix(rmi.getLaborCostsActual(),2)%></td>
         <td><%=DicInit.getSystemName("REPAIR_CONDITION",rmi.getRepairCondition())%></td>
         <td><%=rmi.getRemark()==null?"":rmi.getRemark()%></td>
        </tr>
        <%}else{ %>
        
        <tr class="<%=strTr%>" > 
         <td><input type="hidden" name="travelId" value="<%=rmi.getTravelId()%>">
         	<select name="repairMan" class="form" >
         	<%
       		for(int j=0;repairManList!=null&&j<repairManList.size();j++){
       			String[] rs=(String[])repairManList.get(j);
       		%>
       		<option value="<%=rs[0]%>" <%if(rmi.getRepairMan().longValue()==Long.parseLong(rs[0])){ %>selected <%} %>><%=rs[1]%></option>
       		<%}%>
      	 	</select>
         </td>
         <td><%=rmi.getDepartDate()==null?"":Operate.formatYMDDate(rmi.getDepartDate())%></td>
         <td><input name="arrivalDate" type="text" class="form" size="10" value="<%=rmi.getArrivalDate()==null?"":Operate.formatYMDDate(rmi.getArrivalDate())%>" onkeydown='javascript:input_date();'></td>
         <td><input name="returnDate" type="text" class="form" size="10" value="<%=rmi.getReturnDate()==null?"":Operate.formatYMDDate(rmi.getReturnDate())%>" onkeydown='javascript:input_date();'></td>
         <td><%=rmi.getWorkingHours()==null?"":rmi.getWorkingHours()%></td>
         <td><input name="workingHoursActual" type="text" class="form" size="6" value="<%=rmi.getWorkingHoursActual()==null?"":rmi.getWorkingHoursActual()%>" onkeydown='javascript:f_onlynumber();'></td>
         <td><input name="travelFee" type="text" class="form" size="10" value="<%=rmi.getTravelFee()==null?"":Operate.toFix(rmi.getTravelFee(), 2)%>" onkeydown='javascript:f_onlymoney();'></td>
         <td><%=rmi.getLaborCosts()==null?"":Operate.toFix(rmi.getLaborCosts(), 2)%></td>
         <td><input name="laborCostsActual" type="text" class="form" size="10" value="<%=rmi.getLaborCostsActual()==null?"":Operate.toFix(rmi.getLaborCostsActual(),2)%>" onkeydown='javascript:f_onlymoney();'></td>
         <td><select name="repairCondition" class="form">
         	<%
       		for(int j=0;repairConditionList!=null&&j<repairConditionList.size();j++){
       			String[] rs=(String[])repairConditionList.get(j);
       			if(!rs[0].equals("P")){
       		%>
       		<option value="<%=rs[0]%>" <%if(rs[0].equals(rmi.getRepairCondition())){ %>selected <%} %>><%=rs[1]%></option>
       		<%}}%>
      	 </select></td>
      	 <td><input name="remark" type="text" class="form" size="20" value="<%=rmi.getRemark()==null?"":rmi.getRemark()%>"></td>
      	 <td><input  type="button" class="button2" onClick="f_update_rm(<%=rmi.getTravelId()%>)" value="保存"></td>
      	 <td><input  type="button" class="button2" onClick="f_del_rm(<%=rmi.getTravelId()%>)" value="删除"></td>
        </tr>
        
        
<%}}}%>

        </tbody>
       </table>
	</div> <!--end of IRISScrollDiv-->
	</td></tr>
	</table> <!--end of irisParentTable-->
	   </td>
     </tr>
        <%if(rsf.getCurrentStatus().equals("X")){ %>
        <tr>
        	<td align="left"><input name="partAddButton" type="button" class="button2" value="添加" onclick="f_add_rm_line()"></td>
        </tr>
        <%} %>
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
	       <td width="30%">维修>维修返还>维修单明细</td>
	       
	    </tr>
	</table>
	<div align="center"  style="position:absolute; left:35;  width:912px;  z-index:1; "> 
		<table border="0" width="100%" cellpadding="0" cellspacing="0" class="content12">
		  <tr> 
		 		
			  <td align="right"> 
			    <input name="endRepairButton" type="button" onClick="repairComplete()" class="button4" value="修复完成">
			    <input name="endRepairButton" type="button" onClick="repairUnComplete()" class="button6" value="有遗留问题">
			    <input name="saveForm" type="button" class="button2" onClick="f_save()" value="暂存">
			  	<input name="closeButton" type="button" class="button2" value="关闭" onclick="window.close()"> 
			  	
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