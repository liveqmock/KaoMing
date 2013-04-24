<%@ page contentType="text/html; charset=GBK" %>	
<%@ page import="java.util.ArrayList"%> 

<html>
<head>

<link href="css/style.css" rel="stylesheet" type="text/css" />
<SCRIPT language="JavaScript" src="js/common.js"></SCRIPT>
<script language="JavaScript" src="js/ajax.js"></script>

</HEAD>

<%
	try{
		ArrayList attachedInfoList = (ArrayList)request.getAttribute("affixList");
		String foreignId = (String)request.getAttribute("foreignId");
		String viewFlag = (String)request.getAttribute("viewFlag");

%>
<body>  
<form>
<table width="90%" border="0" align="center" cellpadding="2" cellspacing="1" class="content12">
<br>
  <tr>
    <td><font size="4"><strong>附件信息</strong></font></td>
  </tr>
</table>
<br>
<table width="99%" border="0" align="center" cellpadding="2" cellspacing="1" class="content12" >
<%if(!"view".equals(viewFlag)){%>
  <tr class="tableback1"> 
    <td> 附件名称： </td>
		<td height="45">
			<IFRAME name="uploadNew" MARGINHEIGHT=0 MARGINWIDTH=0 FRAMEBORDER=0 SCROLLING=no  width="70%" height="50%" SRC="common/fileAdd.jsp"></IFRAME>
			<input type="button" value="添加" onClick="doAddAttached()"><input type="hidden" name="fileType" value="S" />
		</td>
  </tr>
 <%}%> 
  
<%if(attachedInfoList!=null&&attachedInfoList.size()>0){
	%>
  <tr class="tableback2" validateSerialNo> 
  <td id="comText" width="100"> 已上传附件：</td>
	<td >

	<table class="content12" width="100%" cellpadding="2" cellspacing="1" id="uploadCompleteTbl">
		
       <%
       	    String strTr="";
      	    for(int i=0;i<attachedInfoList.size();i++){
      	        if((i+1)%2==0) strTr="tableback2";
      	        else strTr="tableback1";
      				String[] temp=(String[])attachedInfoList.get(i);
      					
      %>
      
      <tr class="<%=strTr%>">
      		<%if(!"view".equals(viewFlag)){%>
          <td width="20" id="dc"><a onClick="delCompleteRow(this,'<%=temp[0]%>')" style="cursor: hand">
          		<img src="googleImg/icon/dialog_close_box.gif"  border=0 alt="删除"></a></td>
          <%}else{%>
          <td width="20"></td>
          <%}%>
          <td><a href="affix/fileInfo/<%=temp[2]%>"><%=temp[1]%></a></td>
      </tr>
      <%}%>
	</table>    </td>
  </tr>
<%}%>



  <tr class="tableback1"> 
  <td height="2">  </td>
    <td height="2"  bgcolor="#ffffff">
	<table id='table5' class="content12" width="100%" cellpadding="2" cellspacing="1">
	</table>	</td>
  </tr>
  <tr> 
    <td height="2" colspan="8" bgcolor="#ffffff"></td>
  </tr>
  <tr> 
    <td height="1" colspan="8" bgcolor="#677789"></td>
  </tr>
  
</table>  
		<div style="position: absolute; width: 195px; height: 91px; z-index: 4; left: 285px; top: 305px;  visibility: hidden">
		<table id="filetable">
			<tr>
				<td><input type="file" id=attached name="file" hidefocus><td>
			</tr>
		</table>
		</div>
		<h3 class="underline"><p align="left"> <input name="take" type="button" value="关闭"  onclick="self.close()"></p></h3>
</form>
</body>  

<script>
var ajax = new sack();
var ii=1;
var rownumber = 0;
var rowid = 1;
var globalButton = null;
function doAddAttached(){
	globalButton = event.srcElement;
	var path = uploadNew.getMyfileName();

	if(path.length == 0){
		alert("请选择要添加的附件!");
		return;
	}
	uploadNew.doSaveWithForeignKey(<%=foreignId%>);
	
	
}


function selectResult(realName,attacheId,createDate,filePath){
		
	//-------操作层中的项
	filetable.rows(filetable.rows.length-1).id = "row" + rowid;//保存当前的File
	document.forms[0].attached.id = "attached" + rowid;//更改当前的File的ID
	//在0位置插入一行新的File
	filetable.insertRow().insertCell(0).innerHTML="<input type=\"file\" id=attached name=\"file\" onchange=\"myfileName.value=this.value\" hidefocus>";
	//---------
	table5.insertRow(rownumber).insertCell(0).innerHTML="<input type='hidden' name='attchedId' value='"+attacheId+"' />";
	ii++;
	table5.rows(rownumber).id = "row" + rowid;
	if(rownumber % 2 == 0){
		table5.rows[rownumber].className = "tableback2";
	}else{
		table5.rows[rownumber].className = "tableback1";
	}
	var tempid = "row" + rowid;
	table5.rows[rownumber].insertCell(0).innerHTML='<a onClick=delRow("' + tempid + '","'+attacheId+'") style="cursor: hand"> <img src=\"googleImg/icon/dialog_close_box.gif\"  border=0 alt=\"删除\"></a>'+'&nbsp;&nbsp;<a  href=\"'+filePath+'\" style="cursor: hand"  ><u><b>'+realName+'&nbsp;</b></u></a>';
	
	var tbls=table5.rows;
	//tbls[rownumber].cells[0].width="10%";
	//tbls[rownumber].cells[1].width="90%";

	rownumber++;
	
	rowid++;
}

function getFileType(){
	return document.forms[0].fileType.value;
}

function delCompleteRow(obj,attacheId){
	var delTr = getParentByTagName(obj,"TR");
	for(k=0;k<uploadCompleteTbl.rows.length;k++){
		if(uploadCompleteTbl.rows(k) == delTr){
			uploadCompleteTbl.deleteRow(k);
			if(uploadCompleteTbl.rows.length<1){
				comText.innerText="";
			}

			break;
		}
	}
	fileDel(attacheId);
}

function delRow(rowId,attacheId){
	for(var i=0;i<rownumber;i++){
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
	for(var i=0;i<rownumber;i++){
		table5.rows(i).cells(0).innerHTML='附件' + (i - 13) + "：";
	}
	fileDel(attacheId);
}

function fileDel(attacheId){
	ajax.setVar("attacheId",attacheId); 		//设置需要传到后台的参数
	ajax.setVar("method", "fileDel");		//调用Action中的方法
	ajax.requestFile = "attachedInfoAction.do";		//调用Action
	ajax.method = "GET";				 //提交类型
	//ajax.onCompletion = delResult;	 	//ajax交互完需要执行的函数

	ajax.runAJAX();
	
}

function fileAddFailed(failedCode){
	globalButton.disabled = false;
	globalButton = null;
}
</script>

<%
}catch(Exception e){
	e.printStackTrace();
}%>

</html>