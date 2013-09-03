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
	List toolsList = (List)request.getAttribute("toolsList");
	
	CustomerInfoForm rcif = rsf.getCustomInfoForm();
	
	Set repairManSetInfoSet = rsf.getRepairManSetInfo();
	Set serviceStatusSet = rsf.getServiceStatusSet();
	
	List repairConditionList=(ArrayList) DicInit.SYS_CODE_MAP.get("REPAIR_CONDITION");
	List fileTypeList = (ArrayList) DicInit.SYS_CODE_MAP.get("FILE_TYPE");
	
%>
<html>
<head>
<title>派工明细</title>
<link href="<%= request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/css/autocomplete.css" /> 
<script language=javascript src="<%= request.getContextPath()%>/js/ajax.js"></script>
<script language=javascript src="<%= request.getContextPath()%>/js/common.js"></script>
<script language=javascript src="<%= request.getContextPath()%>/js/commonSelect.js"></script>
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


var repairMans = "";
var atSign='';

var ajaxChk = new sack();
function atComplete(){
	if(f_chk()&&!f_chkDispatch()){
		ajaxChk.setVar("repairNo", document.forms[0].repairNo.value);
		ajaxChk.setVar("method", "checkDispatchPart");
		ajaxChk.requestFile = "repairHandleAction.do";
		ajaxChk.method = "GET";
		ajaxChk.onCompletion = atCompleteEnd;
		ajaxChk.runAJAX();
	}
}

function atCompleteEnd(){
	var returnXml = ajaxChk.responseXML;
	var result = returnXml.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(result=="part"){
		alert("有携带零件申请未出库！");
	}else if(result=="tool"){
		alert("有携带工具申请未出库！");
	}else if(result=="succ"){
	
		if(confirm("确定安调完成吗？")){
			document.forms[0].atTrain.value=chk();
			
			showWaitDiv(800,1000);
			document.forms[0].action="repairTurningAction.do?method=atComplete";
			document.forms[0].submit();	
		}
	}else{
		alert("校验出错，请联系管理员！");
	}
}



function f_chk(){
	if(atSign==''){
		alert("请选择验收单是否签订");
		return false;
	}else{
		document.forms[0].atSign.value=atSign;
	}
	
	if(document.forms[0].atPrecision.value==''){
		alert("请填几何精度");
		document.forms[0].atPrecision.focus();
		return false;
	}
	
	
	return true;
}


function f_chkDispatch(){ 
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
						alert("请输入派工数据！");
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
				alert("请输入派工数据！");
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

function f_as(val){
	atSign=val;
}



function doAddAttached2(){
	var path = uploadNew2.getMyfileName();
	if(path.length == 0){
		alert("请选择要添加的附件!");
		return;
	}
	globalButton = event.srcElement;
	globalButton.disabled = true;
	showWaitDiv(800,1000);
	uploadNew2.doSaveWithForeignKey2(<%=rsf.getRepairNo()%>,'G');
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

function selectResult(path,attacheId,createDate,filePath,affixType){
	var oNewRow = uploadCompleteTable.insertRow(1);
	if(affixType=='G'){
		oNewRow = uploadCompleteTable2.insertRow(1);
	}
	var oNewCell = oNewRow.insertCell(0);
	oNewCell.width="5%";
	if(affixType=='G'){
		oNewCell.innerHTML=uploadCompleteTable2.rows.length -1;
	}else{
		oNewCell.innerHTML=uploadCompleteTable.rows.length -1;
	}	
	oNewRow.className = "tableback1";
	oNewCell = oNewRow.insertCell(1);
	oNewCell.width="10%";
	if(affixType=='G'){
		oNewCell.innerHTML="<a onClick=delRow2(this,"+attacheId+") style=\"cursor: hand\"><u><b>删除&nbsp;&nbsp;</b></u></a>";
	}else{
		oNewCell.innerHTML="<a onClick=delRow(this,"+attacheId+") style=\"cursor: hand\"><u><b>删除&nbsp;&nbsp;</b></u></a>";
	}
	oNewCell = oNewRow.insertCell(2);
	oNewCell.width="50%";
	oNewCell.innerHTML="<a href="+filePath+" style=\"cursor: hand\"  >"+path+"</a>";
	oNewCell = oNewRow.insertCell(3);
	oNewCell.width="20%";
	if(affixType=='G'){
		oNewCell.innerHTML= "安调质量";
	}else{
		oNewCell.innerHTML= document.forms[0].fileType.options(document.forms[0].fileType.selectedIndex).text;
	}
	oNewCell = oNewRow.insertCell(4);
	oNewCell.width="15%";
	oNewCell.innerText= createDate;

	globalButton.disabled = false;
	globalButton = null;
	hideWaitDiv();
}


function delRow2(obj,attacheId){
	//event.srcElement.disabled = true;
	var delTr = getParentByTagName(obj,"TR");
	for(var k=0;k<uploadCompleteTable2.rows.length;k++){
		if(uploadCompleteTable2.rows(k) == delTr){
			uploadCompleteTable2.deleteRow(k);
			break;
		}
	}
	fileDel(attacheId);
}
function delRow(obj,attacheId){
	//event.srcElement.disabled = true;
	var delTr = getParentByTagName(obj,"TR");
	for(var k=0;k<uploadCompleteTable.rows.length;k++){
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

function setRMName(obj){
	document.forms[0].repairManName.value=obj.options[obj.selectedIndex].text;
}
function setRCName(obj){
	document.forms[0].repairConditionName.value=obj.options[obj.selectedIndex].text;
}


var travelId =1 ;
function addRepairMan(){ 
	var repairMan=document.forms[0].repairMan.value;
	var repairManName=document.forms[0].repairManName.value;
	var departDate=document.forms[0].departDate.value;
	var workingHours=document.forms[0].workingHours.value;
	var travelFee=document.forms[0].ticketsAllCosts.value;
	var laborCosts=document.forms[0].laborCosts.value;
	var remark=document.forms[0].rm_remark.value;
	
	if(f_isNull(document.forms[0].repairMan,'维修员')&&f_isNull(document.forms[0].departDate,'出发日期')&&checkInputDate(document.forms[0].departDate)){
		
		var oBody = irisListTable.tBodies[0] ;
		var oNewRow=oBody.insertRow();
		oNewRow.className = "tableback2";
		oNewRow.id = "IRIS_ROW_"+ (travelId++);
		i=0;
		
		var oNewCell=oNewRow.insertCell(i++).innerHTML=repairManName=="null"?"":repairManName;
		repairMans+= "@"+repairMan;
		var oNewCell=oNewRow.insertCell(i++).innerHTML=departDate=="null"?"":departDate;
		repairMans+= "#"+departDate;
		var oNewCell=oNewRow.insertCell(i++).innerHTML=workingHours;
		repairMans+= "#"+workingHours;
		
		var oNewCell=oNewRow.insertCell(i++).innerHTML=travelFee;
		repairMans+= "#"+travelFee;
		var oNewCell=oNewRow.insertCell(i++).innerHTML=laborCosts;
		repairMans+= "#"+laborCosts;
		var oNewCell=oNewRow.insertCell(i++).innerHTML=remark=="null"?"":remark;
		repairMans+= "#"+remark;
		
			//var oNewCell=oNewRow.insertCell(i++);
			//oNewCell.align="center";
			//oNewCell.innerHTML="<input name=\"DelIRISButton\" type=\"button\" class=\"button2\" value=\"删除\" onclick=\"deleteIRISStatus('IRIS_ROW_"+travelId+"')\">";
		
		document.forms[0].repairMans.value = repairMans;
		resetRMInputForm();
	}
}
function resetRMInputForm(){
	
	//document.forms[0].departDate.value="";
	
	document.forms[0].rm_remark.value="";
}

 

//-->
</script>
</head>

<body bgcolor="#ffffff" leftmargin="0" topmargin="0" rightmargin="0" bottommargin="0" marginwidth="0" marginheight="0" >

<jsp:include page=  "/common/waitDiv.jsp" flush="true" />
<jsp:include page=  "/common/countDiv.jsp" flush="true" />
<html:form action="repairTurningAction.do?method=atCompleteDetail" method="post">
<html:hidden property="repairNo"/>
<input name="currentStatus" type="hidden" value="<%=rsf.getCurrentStatus() %>">
<input name="version" type="hidden" value="<%=rsf.getVersion() %>">
<input name="warrantyType" type="hidden" value="<%=rsf.getWarrantyType() %>">
<input name="repairMans" type="hidden">
<input name="atTrain" type="hidden">


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
			<td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','show','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="安调信息"></td>
			<!-- <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','show','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="维修零件信息"></td> -->
			<td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','show')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="携带工具"></td>
			<td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','show','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="安调派工信息"></td>
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
				          <td width="20%"><%=DicInit.getSystemName("TUNING_TYPE",rsf.getWarrantyType())%></td>
				          
				         </tr>
						 <tr class="tableback1"> 
				          <td width="111">机型：</td>
				          <td width="130"><%=rsf.getModelCode()==null?"":rsf.getModelCode()%></td>
				          <td width="110">机身号：</td>
				          <td width="150"><%=rsf.getSerialNo()==null?"":rsf.getSerialNo()%></td>
						  <td>保固书编号：</td>
				          <td><bean:write property="warrantyCardNo"  name="repairServiceForm"/></td>
						  
				         </tr>
				      
						 <tr class="tableback2"> 
				          <td>验收日期：</td>
				          <td><%=rsf.getPurchaseDate()==null?"":Operate.formatYMDDate(rsf.getPurchaseDate())%></td>
				          <td>延保日期：</td>
				          <td><%=rsf.getExtendedWarrantyDate()==null?"":Operate.formatYMDDate(rsf.getExtendedWarrantyDate())%></td>
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
									
									<tr>
										<td colspan="3">
											<table  width="100%" border="0" cellspacing="1" cellpadding="1" class="content12">
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
															if(temp[3].equals("P")){
												%>
															<tr class="<%=strTr%>">
															  <td><%=i+1%></td>
															  <td>&nbsp;&nbsp;</td>
															  <td><a href="<%=temp[2]%>" style="cursor: hand"  ><%=temp[1]%></a></td>
															  <td><%=DicInit.getSystemName("FILE_TYPE",temp[3])%><input type="hidden" name="attachId" value="<%=temp[0]%>"></td>
															  <td><%=temp[4]%></td>
															</tr>
												<%}}}%>
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
         <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','show','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="安调信息"></td>
         <!-- <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','show','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="维修零件信息"></td> -->
         <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','show')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="携带工具"></td>
		 <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','show','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="安调派工信息"></td>
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

<tr><td valign="top">
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
    
    
    <div align="center" id="Layer1" style="position:absolute; left:35; top:80; width:912px; height:390px; z-index:1;"> 
        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="content12">
          <tr> 
            <td valign="bottom"> <table border="0" cellpadding="0" cellspacing="0">
                <tr> 
                  <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','show','Layer10','','hide','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="机器信息"></td>
                  <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','show','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="客户信息"></td>
                  <td width="30"><input name="labelButton" type="button" class="button_b"  value="安调信息"></td>
                  <!-- <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','show','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="维修零件信息"></td> -->
                  <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','show')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="携带工具"></td>
                  <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','show','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="安调派工信息"></td>

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
                  <td >安调联系人：</td>
                  <td ><bean:write property="dzLinkman" name="repairServiceForm" /></td>
                  <td>安调电话：</td>
                  <td colspan=3><bean:write property="dzPhone"  name="repairServiceForm"/></td>
            
                </tr>
                <tr class="tableback2"> 
                  <td >预计工期：</td>
                  <td ><bean:write property="expectedDuration"   name="repairServiceForm"/></td>
                  <td >安调备注：</td>
                  <td colspan=3><bean:write property="dzRemark"  name="repairServiceForm"/></td>
                  
                </tr>
                <!-- <tr class="tableback1"> 
                  <td valign="top">电诊原因：</td>
                  <td colspan="3"><bean:write property="dzReason"  name="repairServiceForm"/></td>
                </tr>
                <tr class="tableback2"> 
                  <td valign="top">电诊结果：</td>
                  <td colspan="3"><bean:write property="dzResult" name="repairServiceForm"/></td>
                </tr> -->
       			
       			<tr> 
	              <td height="3" colspan="10" background="<%= request.getContextPath()%>/images/i_line.gif"></td>
	            </tr>
	            
	            <tr class="tableback1"> 
                  <td colspan="6"><b>出厂质量问题</b></td>
                </tr>
                <tr class="tableback1"> 
                  <td  width="11%">PLC：</td>
                  <td  width="22%"><html:text property="atPlc"  styleClass="form" size="16" maxlength="100" /></td>
                  <td  width="11%">机械缺失配件：</td>
                  <td width="22%"><html:text property="atMissParts"  styleClass="form" size="16" maxlength="100" /></td>
                  <td width="11%">电路：</td>
                  <td ><html:text property="atCircuit"  styleClass="form" size="16" maxlength="100" /></td>
                </tr>
                <tr class="tableback1"> 
                  <td >耗用工时(天)：</td>
                  <td><html:text property="actualDuration"  styleClass="form" size="16" maxlength="3" onkeydown='javascript:f_onlynumber();' /></td>
                  <td >其他：</td>
                  <td colspan="3"><html:text property="atOthers"  styleClass="form" size="80" maxlength="200" /></td>
                  
                </tr>
                
                <tr class="tableback2">
					<td>附件：</td>
					<td>安调质量</td>
					<td>上传附件：(100M)</td>
					<td colspan="3"  height="20" valign="top">
						<table width="100%">
							<tr valign="top">
							<td width="65%">
						<IFRAME name="uploadNew2" MARGINHEIGHT=0 MARGINWIDTH=0 FRAMEBORDER=0 SCROLLING=no  width="90%" height="20" SRC="<%= request.getContextPath()%>/common/fileAdd.jsp"></IFRAME>
							</td>
							<td align="left">
     	    						<input type="button" class="button2" value="添加" onClick="doAddAttached2()">
     	    						</td>
     	    						</tr>
     	    				</table>
     	    				</td>
     	    		
				</tr>
				<tr>
					<td colspan="6">
						<table id="uploadCompleteTable2" width="100%" border="0" cellspacing="1" cellpadding="1" class="content12">
							<tr class="tableback1">
								<td width="5%"><B>序号</B></td>
								<td width="10%"><B>删除</B></td>
								<td width="50%"><B>文件</B></td>
								<td width="20%"><B>文件类型</B></td>
								<td width="15%"><B>上传时间</B></td>
							</tr>
					<%
						if(repairAttachment!=null){
							for(int i=repairAttachment.size()-1;i>=0;i--){
					 	  	String[] temp=(String[])repairAttachment.get(i);
								strTr=i%2==0?"tableback2":"tableback1";
								if(temp[3].equals("G")){
					%>
								<tr class="<%=strTr%>">
								  <td><%=i+1%></td>
								  <td>&nbsp;&nbsp;</td>
								  <td><a href="<%=temp[2]%>" style="cursor: hand"  ><%=temp[1]%></a></td>
								  <td><%=DicInit.getSystemName("FILE_TYPE",temp[3])%><input type="hidden" name="attachId" value="<%=temp[0]%>"></td>
								  <td><%=temp[4]%></td>
								</tr>
					<%}}}%>
						</table>
					</td>
				</tr>
					
	            <tr class="tableback2"> 
                  <td colspan="6"><B>安调结果</B></td>
                </tr>
                <tr class="tableback2"> 
                  <td >几何精度：<font color='red'>*</font></td>
                  <td colspan="5"><html:text property="atPrecision"  styleClass="form" size="80" maxlength="200" /></td>
                </tr>
                <tr class="tableback2">
                  <td >头的精度：</td>
                  <td colspan="2">0&deg;位&nbsp;&nbsp;&nbsp;下母线</td>
                  <td><html:text property="atUHW0"  styleClass="form" size="16" maxlength="10" /></td>
                  <td >0&deg;位&nbsp;&nbsp;&nbsp;侧母线</td>
                  <td><html:text property="atSHW0"  styleClass="form" size="16" maxlength="10" /></td>
                </tr>
				<tr class="tableback2">
                  <td ></td>
                  <td colspan="2">90&deg;位&nbsp;&nbsp;&nbsp;下母线</td>
                  <td><html:text property="atUHW90"  styleClass="form" size="16" maxlength="10" /></td>
                  <td >90&deg;位&nbsp;&nbsp;&nbsp;侧母线</td>
                  <td><html:text property="atSHW90"  styleClass="form" size="16" maxlength="10" /></td>
                </tr>
                <tr class="tableback2">
                  <td ></td>
                  <td colspan="2">180&deg;位&nbsp;&nbsp;&nbsp;下母线</td>
                  <td><html:text property="atUHW180"  styleClass="form" size="16" maxlength="10" /></td>
                  <td >180&deg;位&nbsp;&nbsp;&nbsp;侧母线</td>
                  <td><html:text property="atSHW180"  styleClass="form" size="16" maxlength="10" /></td>
                </tr>
                <tr class="tableback2">
                  <td ></td>
                  <td colspan="2">270&deg;位&nbsp;&nbsp;&nbsp;下母线</td>
                  <td><html:text property="atUHW270"  styleClass="form" size="16" maxlength="10" /></td>
                  <td >270&deg;位&nbsp;&nbsp;&nbsp;侧母线</td>
                  <td><html:text property="atSHW270"  styleClass="form" size="16" maxlength="10" /></td>
                </tr>
                
                <tr class="tableback2">
						  
					<td>机床资料：</td>
					<td><select name="fileType" class="form" style="width: 150" size="1">
						<%
			       		for(int i=0;fileTypeList!=null&&i<fileTypeList.size();i++){
			       			String[] rs=(String[])fileTypeList.get(i);
			       			if(rs[0].equals("H")||rs[0].equals("I")||rs[0].equals("J")||rs[0].equals("K")){
			       		%>
			       		<option value="<%=rs[0]%>"><%=rs[1]%></option>
			       		<%}}%>
					</select></td>
					
					<td>上传附件：(100M)</td>
					<td colspan="3"  height="20" valign="top">
						<table width="100%">
							<tr valign="top">
							<td width="65%">
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
					<td colspan="6">
						<table id="uploadCompleteTable" width="100%" border="0" cellspacing="1" cellpadding="1" class="content12">
							<tr class="tableback1">
								<td width="5%"><B>序号</B></td>
								<td width="10%"><B>删除</B></td>
								<td width="50%"><B>文件</B></td>
								<td width="20%"><B>文件类型</B></td>
								<td width="15%"><B>上传时间</B></td>
							</tr>
					<%
						if(repairAttachment!=null){
							for(int i=repairAttachment.size()-1;i>=0;i--){
					 	  	String[] temp=(String[])repairAttachment.get(i);
								strTr=i%2==0?"tableback2":"tableback1";
								if(temp[3].equals("H")||temp[3].equals("I")||temp[3].equals("J")||temp[3].equals("K")){
					%>
								<tr class="<%=strTr%>">
								  <td><%=i+1%></td>
								  <td>&nbsp;&nbsp;</td>
								  <td><a href="<%=temp[2]%>" style="cursor: hand"  ><%=temp[1]%></a></td>
								  <td><%=DicInit.getSystemName("FILE_TYPE",temp[3])%><input type="hidden" name="attachId" value="<%=temp[0]%>"></td>
								  <td><%=temp[4]%></td>
								</tr>
					<%}}}%>
						</table>
					</td>
				</tr>
				
				<tr class="tableback1">
                	<td>培训：</td>
                    <td colspan="5">
                    	<input type="checkbox" name="chk" value="A" >机床结构&nbsp;
                    	<input type="checkbox" name="chk" value="B" >使用操作（刀库、头库、机床）&nbsp;
                    	<input type="checkbox" name="chk" value="C" >保养要点&nbsp;
                    	<input type="checkbox" name="chk" value="D" >基本故障处理&nbsp;
                    	<input type="checkbox" name="chk" value="E" >机床配件操作讲解
                    	<input type="checkbox" name="chk" value="F" >特殊配件培训
                    </td>
                    
                </tr>
                <tr class="tableback2">
                	<td>验收单：</td>
                    <td colspan="5">
                    	<input type="radio" name="atSign" value="A" onclick="f_as(this.value)">签订&nbsp;
                    	<input type="radio" name="atSign" value="B" onclick="f_as(this.value)">未签说明：&nbsp;<html:text property="atUnsignRemark"  styleClass="form" size="70" maxlength="100" />
                    </td>
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
			<td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','show','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="安调信息"></td>
			<td width="30"><input name="labelButton" type="button" class="button_b" value="维修零件信息"></td>
			<td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','show')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="携带工具"></td>
			<td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','show','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="安调派工信息"></td>
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
        </tr>
        </thead>
        <tbody>

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
			<td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','show','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="安调信息"></td>
			<!-- <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','show','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="维修零件信息"></td> -->
			<td width="30"><input name="labelButton" type="button" class="button_b" value="携带工具"></td>
			<td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','show','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="安调派工信息"></td>
        </tr>
       </table></td>
     </tr>
     <tr> 
      <td height="2" bgcolor="#677789"></td>
     </tr>
     <tr> 
      <td height="6" bgcolor="#CECECE"></td>
     </tr>
    			 
     
    </table>
    
    <br>
	<table HEIGHT="60" width="100%" cellspacing="0" cellpadding="0" border="0" id="repairToolsTable">
	<tr> 
      <td valign="top"> <table width="100%" border="0" cellpadding="1" cellspacing="1" class="content12">
      <tr><td><span class="content14"><strong>已申请携带工具</strong></span> </td></tr>
      <tr> 
              <td height="1" colspan="10" background="<%= request.getContextPath()%>/images/i_line.gif"></td>
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
   
   
   <div align="center" id="Layer11" style="position:absolute; left:35; top:80; width:912px; height:390px; z-index:1;  visibility: hidden;"> 
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="content12">
     <tr> 
      <td valign="bottom"><table border="0" cellpadding="0" cellspacing="0">
        <tr> 
         <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','show','Layer10','','hide','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="机器信息"></td>
         <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','show','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="客户信息"></td>
         <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','show','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="安调信息"></td>
         <!-- <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','show','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="维修零件信息"></td> -->
         <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer9','','hide','Layer10','','hide','Layer11','','hide','Layer8','','show')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="携带工具"></td>
         <td width="30"><input name="labelButton" type="button" class="button_b" value="安调派工信息"></td>
        
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
       <table width="100%" border="0" cellpadding="0" cellspacing="1" class="content12" id="irisListTable">
        <thead>
        <tr bgcolor="#CCCCCC"> 
         <td height="18"><b>维修员</b></td>
         <td><b>出发日期</b></td>
         
         <td><b>到达日期</b></td>
         <td><b>返回日期</b></td>
         
         <td><b>车船票</b></td>
         <td><b>人工费</b></td>
         <td><b>维修情况</b></td>
         <td><b>备注</b></td>
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
	       <td width="30%">安调>安调报告>维修单明细</td>
	       
	    </tr>
	</table>
	<div align="center"  style="position:absolute; left:35;  width:912px;  z-index:1; "> 
		<table border="0" width="100%" cellpadding="0" cellspacing="0" class="content12">
		  <tr> 
		 		
			  <td align="right"> 
			    <input name="endRepairButton" type="button" onClick="atComplete()" class="button4" value="安调完成">
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