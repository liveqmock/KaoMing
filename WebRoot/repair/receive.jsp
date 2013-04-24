<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ page import="java.util.ArrayList"%> 
<%@ page import="java.util.List"%> 
<%@ page import="com.dne.sie.common.tools.CommonSearch"%>
<%@ page import="com.dne.sie.common.tools.DicInit"%>

<html>
<head>
<title>receive</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/autocomplete.css" /> 
<script language=javascript src="js/ajax.js"></script>
<SCRIPT language=javascript src="js/commonSelect.js"></SCRIPT>
<script language=javascript src="js/common.js"></script>
<script type="text/javascript" src="js/prototype.js"></script>
<script type="text/javascript" src="js/autocomplete.js"></script>
<script language=javascript src="js/inputValidation.js"></script>
<script language=javascript src="js/checkValid.js"></script>
<script language=javascript src="js/popPartInfo.js"></script>
<script language=javascript src="js/popCalendar.js"></script>

<%
try{
		
	//List factoryList=CommonSearch.getInstance().getFactoryList();
	List repairProperitesList = (ArrayList) DicInit.SYS_CODE_MAP.get("REPAIR_PROPERITES");
	List warrantyList = (ArrayList) DicInit.SYS_CODE_MAP.get("WARRANTY_TYPE");
	String serviceSheetNo = (String)request.getAttribute("serviceSheetNo");
	List repairSourceList=(ArrayList) DicInit.SYS_CODE_MAP.get("REPAIR_SOURCE");
	
%>
<SCRIPT>

function highlightButton(s) {
    if ("INPUT"==event.srcElement.tagName)
      event.srcElement.className=s
}

function MM_showHideLayers() { //v6.0
  var i,p,v,obj,args=MM_showHideLayers.arguments;
  for (i=0; i<(args.length-2); i+=3) if ((obj=MM_findObj(args[i]))!=null) { v=args[i+2];
    if (obj.style) { obj=obj.style; v=(v=='show')?'visible':(v=='hide')?'hidden':v; }
    obj.visibility=v; }
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
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



var rownumber = 9;	//tr��
var rowid = 1;
function doAddAttached(){
	var path = uploadNew.getMyfileName();

	if(path.length == 0){
		alert("��ѡ��Ҫ��ӵĸ���!");
		return;
	}
	 
	uploadNew.doSave();
		
}
function getFileType(){
	return document.forms[0].fileType.value;
}

var allAddAttacheId = new Array();//�ϴ�������Ӧ��ID��������ϴ���������ID
var allDeleteAttacheId = new Array();//�ϴ�������Ӧ��ID�������ɾ�������е�ID

function selectResult(path,attacheId,createDate,filePath){//�����ϴ����˵��ļ��б�
	var maxRow=7;
	
	//-------�������е���
	filetable.rows(filetable.rows.length-1).id = "row" + rowid;//���浱ǰ��File
	document.forms[0].attached.id = "attached" + rowid;//���ĵ�ǰ��File��ID
	//��0λ�ò���һ���µ�File
	filetable.insertRow().insertCell(0).innerHTML="<input type=\"file\" id=attached name=\"file\" onchange=\"myfileName.value=this.value\" hidefocus>";

	table5.insertRow(maxRow).insertCell(0).innerHTML='��Ƭ��';
	//table5.rows[maxRow].insertCell(1).innerHTML= document.forms[0].fileType.options(document.forms[0].fileType.selectedIndex).text;
	//table5.rows[maxRow].insertCell(1).innerHTML= + (rownumber - 6) + "��";
	table5.rows[maxRow].id = "row" + rowid;
	if(rownumber % 2 == 0){
		table5.rows[maxRow].className = "tableback2";
	}else{
		table5.rows[maxRow].className = "tableback1";
	}
	var tempid = "row" + rowid;
	table5.rows[maxRow].insertCell(1).innerHTML='<a onClick=delRow("' + tempid + '","'+attacheId+'") style="cursor: hand"><u><b>&nbsp;ɾ��</b></u></a>'+'&nbsp;&nbsp;<a href='+filePath+' style=\"cursor: hand\"  ><u><b>'+path+'&nbsp;</b></u></a>';
	table5.rows[maxRow].cells(1).colSpan = 3;
	table5.rows[maxRow].insertCell(2).innerHTML=createDate;
	
	allAddAttacheId.push(attacheId);
	rownumber++;
	rowid++;
}

function delRow(rowId,attacheId){
	var maxRow=7;

	//alert("rowID:" + rowId + "   +++ attacheId:" + attacheId);
	for(var i=maxRow;i<rownumber;i++){
		if(table5.rows(i).id == rowId){
			table5.deleteRow(i);
			break;
		}
	}
	for(var i=0;i<filetable.rows.length;i++){
		if(filetable.rows(i).id == rowId){
			filetable.deleteRow(i);
			break;
		}
	}
	rownumber--;
	//for(var i=maxRow;i<rownumber;i++){
	//	table5.rows(i).cells(2).innerHTML='ƾ֤' + (rownumber-i) + "��";
	//}
	
	fileDel(attacheId);
	allDeleteAttacheId.push(attacheId);
}

function fileDel(attacheId){
	var ajax2 = new sack(); 
	ajax2.setVar("attacheId",attacheId); 		//������Ҫ������̨�Ĳ���
	ajax2.setVar("method", "fileDel");		//����Action�еķ���
	ajax2.requestFile = "attachedInfoAction.do";		//����Action
	ajax2.method = "GET";				 //�ύ����
	//ajax2.onCompletion = delResult;	 	//ajax��������Ҫִ�еĺ���
	ajax2.runAJAX(); 
}
function fileAddFailed(failedCode){
	//globalButton.disabled = false;
	//globalButton = null;
	//hideWaitDiv();	
}

function check(){
	if(f_trimIsNull(document.forms[0].modelCode)){
		alert("��������ͺ��룡");
		MM_showHideLayers('Layer1','','hide','Layer2','','show','Layer3','','hide');
		document.forms[0].modelCode.focus();
		return false;
	}
	if(f_trimIsNull(document.forms[0].serialNo)){
		alert("�����������룡");
		MM_showHideLayers('Layer1','','hide','Layer2','','show','Layer3','','hide');
		document.forms[0].serialNo.focus();
		return false;
	}
	
	if(f_trimIsNull(document.forms[0].customerTrouble)){
		alert("������ͻ��������ϣ�");
		MM_showHideLayers('Layer1','','hide','Layer2','','show','Layer3','','hide');
		document.forms[0].customerTrouble.focus();
		return false;
	}
	
	if(f_trimIsNull(document.forms[0].customerName)){
		alert("������ͻ����ƣ�");
		MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer3','','show');
		document.forms[0].customerName.focus();
		return false;
	}
	
	if(document.forms[0].warrantyType.value=='I'){
		if(f_trimIsNull(document.forms[0].warrantyCardNo)){
			alert("�����뱣�����ţ�");
			MM_showHideLayers('Layer1','','hide','Layer2','','show','Layer3','','hide');
			document.forms[0].warrantyCardNo.focus();
			return false;
		}
	
		if(confirm("ȷ������ά���Ǳ�����")){
			
		}else{
			return false;
		}
	}
	
	return true;
}

function doSubmit(){
	if(check()){
		//ȡ���ϴ��ļ�����ЧID�б������ӵģ�ȥ��ɾ���ģ��õ���Ч��ID�б�
		var tempAttacheId = new Array();
		for(var i=0;i<allAddAttacheId.length;i++){
			var haveDelete = false;
			for(var j=0;j<allDeleteAttacheId.length;j++){
				if(allAddAttacheId[i] == allDeleteAttacheId[j]){//�����ӵļ�¼��������ɾ���ļ�¼�У���ü�¼��Ч������ӵ���Ч������
					haveDelete = true;
					break;
				}
			}
			
			if(haveDelete){
				continue;
			}else{
				tempAttacheId.push(allAddAttacheId[i]);
			}
		}
		document.forms[0].attacheIds.value = tempAttacheId;

		document.forms[0].save1.disabled=true;
		document.forms[0].save2.disabled=true;
		document.forms[0].save3.disabled=true;
		
		//document.forms[0].target="_blank";
		document.forms[0].submit();
		
		document.forms[0].reset();
		document.forms[0].save1.disabled=false;
		document.forms[0].save2.disabled=false;
		document.forms[0].save3.disabled=false;
	}
}
	
	function repairOnChange(){
		for(var i=document.forms[0].warrantyType.options.length-1;i>-1;i--){//ɾ��ԭ����Ϣ
			document.forms[0].warrantyType.options.remove(i);
		}
		
		if("T" == document.forms[0].repairProperites.value){	//����
			<%for(int i=0;warrantyList!=null&&i<warrantyList.size();i++){
				String[] temp = (String[])warrantyList.get(i);
				if(temp[0].compareTo("E")<0){
			%>
			var oOption = document.createElement("OPTION");
			document.forms[0].warrantyType.options.add(oOption);
			oOption.innerText ="<%=temp[1]%>";
			oOption.value = "<%=temp[0]%>";
			<%}}%>
		}else if("Y" == document.forms[0].repairProperites.value){	//����
			<%for(int i=0;warrantyList!=null&&i<warrantyList.size();i++){
				String[] temp = (String[])warrantyList.get(i);
				if(temp[0].equals("Y")){
			%>
			var oOption = document.createElement("OPTION");
			document.forms[0].warrantyType.options.add(oOption);
			oOption.innerText ="<%=temp[1]%>";
			oOption.value = "<%=temp[0]%>";
			<%}}%>
		}else{	//ά��
			<%for(int i=0;warrantyList!=null&&i<warrantyList.size();i++){
				String[] temp = (String[])warrantyList.get(i);
				if(temp[0].compareTo("E")>=0 && !temp[0].equals("Y")){
			%>
			var oOption = document.createElement("OPTION");
			document.forms[0].warrantyType.options.add(oOption);
			oOption.innerText ="<%=temp[1]%>";
			oOption.value = "<%=temp[0]%>";
			<%}}%>
			
		}
		
	
	}

</script>
</head>
<body bgcolor="#ffffff" leftmargin="0" topmargin="0" rightmargin="0" bottommargin="0" marginwidth="0" marginheight="0" >
<jsp:include page="/common/countDiv.jsp" flush="true" />
<html:form method="post" action="repairHandleAction.do?method=serviceAdd">
<input type="hidden" name="attacheIds" value="">
<table width="99%" height="100%" border="0" align="left" cellpadding="0" cellspacing="0" class="content12" id="table1">
   <tr>
		<td valign="top"> 
		<div align="center" id="Layer2" style="position: absolute; left: 20px; top: 10; width: 100%; height: 390px; z-index: 2">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="content12" id="table2">
			<tr>
				<td valign="bottom">
				<table border="0" cellpadding="0" cellspacing="0" id="table3">
					<tr>
						<td width="30"><input name="Submit2" type="button" class="button_b" value="������Ϣ"></td>
						<td width="30"><input name="Submit3" id="customerInfo" type="button" class="button_g"
							onclick="MM_showHideLayers('Layer2','','hide','Layer3','','show','Layer1','','hide')"
							onmouseover="highlightButton('button_b')" onmouseout="highlightButton('button_g')" value="�ͻ���Ϣ"></td>
						<!-- <td width="30"><input name="Submit4" id="customerInfo" type="button" class="button_g"
							onclick="MM_showHideLayers('Layer2','','hide','Layer3','','hide','Layer1','','show')"
							onmouseover="highlightButton('button_b')" onmouseout="highlightButton('button_g')" value="������Ϣ"></td> -->
					</tr>
				</table>
				</td>
				<td align="right">
				
				<table border="0" cellpadding="0" cellspacing="0" id="infotable1">
					<tr>
					<TD>
						<input name="save1" type="button" class="button4"	onclick="doSubmit()" value="����">
					</TD>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td height="2" colspan="2" bgcolor="#677789"></td>
			</tr>
			<tr>
				<td height="6" colspan="2" bgcolor="#CECECE"></td>
			</tr>
			<tr>
				<td valign="top" colspan="2">
                    
                    <table width="100%" border="0" cellspacing="1" cellpadding="1" class="content12" id="table5">
						<tr class="tableback2">
						<td width="15%">ά�޵��ţ�</td>
						<td width="20%"><%=serviceSheetNo %></td>
						<td width="15%">ά�����ʣ�</td>
						<td width="20%"><select name="repairProperites" class="form" style="width: 100" size="1"  onChange="repairOnChange()">
							<%if(repairProperitesList!=null){
									for(int i=0;i<repairProperitesList.size();i++){
									String[] temp = (String[])repairProperitesList.get(i);
									if(!"T".equals(temp[0])){
							%>
								<option value="<%=temp[0]%>" <%if("C".equals(temp[0])){%>selected="selected"<%}%> ><%=temp[1]%></option>
							<%
									}}
								}
							%>
						</select></td>
						
						<td width="10%">�������ͣ�</td>
							<td width="20%"><select name="warrantyType" class="form" style="width: 80" size="1" >
							<%if(warrantyList!=null){
									for(int i=0;i<warrantyList.size();i++){
										String[] temp = (String[])warrantyList.get(i);
										if(!temp[0].equals("Y")){
							%>
								<option value="<%=temp[0]%>"  ><%=temp[1]%></option>
							<%
									}}}
							%>
							
						</select></td>		
						
					</tr>
					<tr class="tableback1">
						<td >���ͣ� <font color="#FF6600">*</font></td>
						<td > <input name="modelCode" type="text" class="form" size="16" ></td>
						<td >����ţ�<font color="#FF6600">*</font></td>
						<td ><input name="serialNo" type="text" class="form" size="16" ></td>
						<td id="serialAlert"></td>
						<td ><input type="checkbox" name="rr90" value="T" > 
							<span class="content11">ԭ���Ϸ���</span></td>
							
					</tr>
					
					<tr class="tableback2">
						<td >�������ڣ�</td>
						<td><input name="purchaseDateStr" type="text" class="form" size="16"  onkeydown='javascript:input_date();'>
						<a onClick="event.cancelBubble=true;" href="javascript:showCalendar('imageCalendar1',true,'purchaseDateStr',true);">
						<img id="imageCalendar1" width="18" height="18" src="<%= request.getContextPath()%>/images/i_colock.gif" border="0" align="absmiddle">
						</a></td>
						
						<td >�������ţ�</td>
						<td><input name="warrantyCardNo" type="text" class="form" size="16"></td>
						<td>�������̣�</td>
						<td><input name="manufacture" type="text" class="form" size="30"></td>
						
					</tr>
					
					
					<tr class="tableback2">
						<td >�ͻ�Ҫ�󵽴����ڣ�</td>
						<td><input name="customerVisitDateStr" type="text" class="form"
							size="16" onkeydown='javascript:input_date();'> <a onClick="event.cancelBubble=true;"
							href="javascript:showCalendar('imageCalendar3',false,'customerVisitDateStr');">
						<img id="imageCalendar3" width="18" height="18"
							src="<%= request.getContextPath()%>/images/i_colock.gif" border="0" align="absmiddle">
						</a></td>
						
						<td >Ԥ���޸����ڣ�</td>
						<td colspan="3"><input name="estimateRepairDateStr" type="text" class="form"
							size="16" onkeydown='javascript:input_date();' > <a onClick="event.cancelBubble=true;"
							href="javascript:showCalendar('imageCalendar2',false,'estimateRepairDateStr');">
						<img id="imageCalendar2" width="18" height="18"
							src="<%= request.getContextPath()%>/images/i_colock.gif" border="0" align="absmiddle">
						</a></td>
					</tr>
					<tr class="tableback1">
						<td >�ͻ��������ϣ�<font color="#FF6600">*</font></td>
						<td colspan="5"><textarea name="customerTrouble" cols="8" rows="2"
							class="form" style="width: 90%" onpropertychange="showCountDiv(this,1000)" onfocus="showCountDiv(this,1000)" onblur="hideCountDiv(this,1000)"></textarea></td>
					</tr>
					<tr class="tableback2">
						
						<td >�����ţ�</td>
						<td><input name="alarmNo" type="text" class="form" size="16" maxLength="20"></td>
						<td> ���ϲ�λ��</td>
						<td colspan="3"><input name="troublePlace" type="text" class="form" size="80" maxLength="200"></td>
						
					</tr>
				
					
					
					<tr class="tableback1">
					<input type="hidden" name="fileType" value="P">
						<!--    
						<td>ƾ֤���ͣ�</td>
						<td><select name="fileType" class="form" style="width: 150" size="1">
							<option value="I">������Ʊ</option>
							<option value="W">���޿�</option>
							<option value="A">��ǰί�б�</option>
							<option value="O">����ƾ֤</option>
						</select></td>
						-->
						<td>�ϴ���Ƭ��(100M)</td>
						<td colspan="3"  height="20" valign="top">
							<table width="100%">
								<tr valign="top">
								<td width="57%">
							<IFRAME name="uploadNew" MARGINHEIGHT=0 MARGINWIDTH=0 FRAMEBORDER=0 SCROLLING=no  width="90%" height="20" SRC="<%= request.getContextPath()%>/common/fileAdd.jsp"></IFRAME>
								</td>
								<td align="left">
      	    						<input type="button" class="button2" value="���" onClick="doAddAttached()">
      	    						</td>
      	    						</tr>
      	    				</table>
      	    				</td>
      	    				<td colspan="3"> </td>
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
		<div align="center" style="position: absolute; width: 195px; height: 91px; z-index: 4; left: 285px; top: 305px;  visibility: hidden" id="Layer4">
		<table id="filetable">
			<tr>
				<td><input type="file" id=attached name="file" hidefocus><td>
			</tr>
		</table>

		</div>
		
		

		<div align="center" style="position: absolute; left: 20px; top: 10; width: 100%; height: 390px; z-index: 1; visibility: hidden" id="Layer1" >
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="content12" id="table6">
			<tr>
				<td valign="bottom">
				<table border="0" cellpadding="0" cellspacing="0" id="table7">
					<tr>
						<td width="30"><input name="Submit51" type="button" class="button_g" onclick="MM_showHideLayers('Layer2','','show','Layer3','','hide','Layer1','','hide')"
							onmouseover="highlightButton('button_b')" onmouseout="highlightButton('button_g')" value="������Ϣ"></td>
						<td width="30"><input name="Submit52" id="customerInfo" type="button" class="button_g" onclick="MM_showHideLayers('Layer2','','hide','Layer3','','show','Layer1','','hide')"
							onmouseover="highlightButton('button_b')" onmouseout="highlightButton('button_g')" value="�ͻ���Ϣ"></td>	
						<td width="30"><input name="Submit53" type="button" class="button_b" value="������Ϣ"></td>
					</tr>
				</table>
				</td>
				<td align="right">
				<table border="0" cellpadding="0" cellspacing="0" id="infotable3">
					<tr>
						<TD>
   							<input name="save3" type="button" class="button4"	onclick="doSubmit()" value="����">
						</TD>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td height="2" colspan="2" bgcolor="#677789"></td>
			</tr>
			<tr>
				<td height="6" colspan="2" bgcolor="#CECECE"></td>
			</tr>
			<tr>
				<td valign="top" colspan="2">
				
                    <table width="100%" border="0" cellspacing="1" cellpadding="1" class="content12" >
					  <tr class="tableback2"> 
                        <td width="120">���۵��ţ�</td>
                        <td colspan="5"><input name="saleNo" type="text" class="form" size="30" > </td>
                        <input type="hidden" name="partsFee">
                      </tr>
                      <tr class="tableback1">
                      	<td width="100">���������</td>
                        <td width="200"><input name="partNum" type="text" class="form" size="16" readonly  > </td>
                        <td width="100">���ۣ�</td>
                        <td width="200"><input name="totalQuote" type="text" class="form" size="16" readonly ></td>
                        <td width="120">�����ˣ�</td>
                        <td width="200"><input name="createBy" type="text" class="form" size="16"  readonly > </td> 
                      </tr>
                      <tr class="tableback2"> 
                        <td width="100">���۵�״̬��</td>
                        <td width="200"><input name="saleStatus" type="text" class="form" size="16"  readonly > </td> 
                        <td width="100">����״̬��</td>
                        <td width="200"><input name="payStatus" type="text" class="form" size="16" readonly > </td>
                        <td width="120">�ͻ��Ѹ��ܶ</td>
                        <td ><input name="totalPay" type="text" class="form" size="16" readonly> </td>
                      </tr>
                      <tr class="tableback1"> 
                        <td width="120">��Ʊ״̬��</td>
                        <td width="200"><input name="billingStatus" type="text" class="form" size="16" readonly > </td>
                        <td width="100">��Ʊ��</td>
                        <td colspan="3"><input name="billingMoney" type="text" class="form" size="16" readonly> </td>
                      </tr>
                      <tr class="tableback2">
						<td valign="top">���۱�ע��</td>
						<td colspan="5"><textarea name="remark" cols="8" rows="2" class="form" style="width: 100%" readonly ></textarea></td>
					  </tr>
                      <tr> 
					     <td height="2" colspan="6" bgcolor="#ffffff"></td>
					  </tr>
					  <tr> 
					     <td height="1" colspan="6"  bgcolor="#677789"></td>
					  </tr>
					
            		</table>
            		
              
            </table>
            </div>
            
            <div align="center" style="position: absolute; left: 20px; top: 10; width: 100%; height: 390px; z-index: 1; visibility: hidden" id="Layer3">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="content12" id="table6">
			<tr>
				<td valign="bottom">
				<table border="0" cellpadding="0" cellspacing="0" id="table7">
					<tr>
						<td width="30"><input name="Submit23" type="button" class="button_g" onclick="MM_showHideLayers('Layer1','','hide','Layer2','','show','Layer3','','hide')"
							onmouseover="highlightButton('button_b')" onmouseout="highlightButton('button_g')" value="������Ϣ"></td>
						<td width="30"><input name="Submit33" type="button" class="button_b" value="�ͻ���Ϣ"></td>
						<!-- <td width="30"><input name="Submit34" id="customerInfo" type="button" class="button_g" onclick="MM_showHideLayers('Layer1','','show','Layer2','','hide','Layer3','','hide')"
							onmouseover="highlightButton('button_b')" onmouseout="highlightButton('button_g')" value="������Ϣ"></td> -->
					</tr>
				</table>
				</td>
				<td align="right">
				<table border="0" cellpadding="0" cellspacing="0" id="infotable2">
					<tr>
						<TD>
   							<input name="save2" type="button" class="button4"	onclick="doSubmit()" value="����">
						</TD>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td height="2" colspan="2" bgcolor="#677789"></td>
			</tr>
			<tr>
				<td height="6" colspan="2" bgcolor="#CECECE"></td>
			</tr>
			<tr>
				<td valign="top" colspan="2">
                    <table width="100%" border="0" cellpadding="0" cellspacing="1" class="content12">
          			  <tr class="tableback2"> 
                        <td width="120">�ͻ����ƣ�<font color='red'>*</font></td>
                        <input type="hidden" name="customerId">
                        <td colspan="4"><input name="customerName" type="text" class="form" size="30" > </td>
                        
                      </tr>
                      <tr class="tableback1">
                      	<td width="100">��ϵ�ˣ�</td>
                        <td width="200"><input name="linkman" type="text" class="form" size="16" readonly > </td>
                        <td width="100">��ϵ�绰��</td>
                        <td width="200"><input name="phone" type="text" class="form" size="16" readonly ></td>
                        <td width="120">���棺</td>
                        <td width="200"><input name="fax" type="text" class="form" size="16" readonly > </td> 
                      </tr>
                      <tr class="tableback2"> 
                        <td width="120">�ֻ���</td>
                        <td width="200"><input name="mobile" type="text" class="form" size="16" readonly > </td>
                        <td width="100">ʡ�ݣ�</td>
                        <td width="200"><input name="provinceName" type="text" class="form" size="16" readonly ></td>
                        <td width="120">���У�</td>
                        <td width="200"><input name="cityName" type="text" class="form" size="16" readonly > </td> 
                      </tr>
                	  <tr class="tableback1">
						<td valign="top">��ַ��</td><input type="hidden" name="shippingAddress">
						<td colspan="5"><textarea name="address" cols="8" rows="2" class="form" style="width: 100%" readonly ></textarea></td>
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
		
        </table>
      
</html:form>

<script>
new AutoTip.AutoComplete("customerName", function() {
	 return "customerInfoAction.do?method=getCustInfo&inputValue=" + escape(this.text.value);
});

new AutoTip.AutoComplete("saleNo", function() {
	 return "saleInfoAction.do?method=getSaleInfo&inputValue=" + (this.text.value);
});

new AutoTip.AutoComplete("serialNo", function() {
	if(document.forms[0].customerId.value==''){
		serialAlert.innerHTML="<font color='red'>����ѡ��ͻ���</font>";
	}else{
		serialAlert.innerHTML="";
		return "machineToolAction.do?method=getSerialInfo&inputValue=" + (this.text.value);
	}
	 
});
</script>
</body>


<%
}catch(Exception e){
	e.printStackTrace();
}%>

</html>