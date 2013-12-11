<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri=  "/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ page import="java.util.ArrayList"%> 
<%@ page import="com.dne.sie.common.tools.DicInit"%>
 
<html>
<head>
<title>stockOutOperate_list</title><SCRIPT language="javascript" src="js/screen.js"></SCRIPT>
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/styles2.css">
<script language=javascript src="js/popPartInfo.js"></script>
<script language=javascript src="js/checkValid.js"></script>
<script language=javascript src="js/common.js"></script>
<script language=javascript src="js/ajax.js"></script>
<script language=javascript src="js/inputValidation.js"></script>
<script language=javascript src="js/popPartInfo.js"></script>


</head>
<%
    ArrayList stockInfoList = (ArrayList)request.getAttribute("stockInfoList");
  
 
    String takeId = (String)request.getAttribute("takeId")==null?"-1":(String)request.getAttribute("takeId"); 
    String strDisabled="disabled";
%>

<body bgcolor="#ffffff" leftmargin="0" topmargin="0" rightmargin="0" bottommargin="0" marginwidth="0" marginheight="0" >
<%if(!takeId.equals("-1")){
try{
%>
 <html:form action="stockTakeAction.do?method=takePage" method="post" >
 <html:hidden property="stockTakeId" value="<%=takeId%>" />
 <input type="hidden" name="takeNumAll">
 <input type="hidden" name="idsAll"> 
        <table width="96%" height="90%" border="0" cellpadding="0" cellspacing="6" class="content12">
          <tr> 
            <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0" class="content12">
              <tr>
                <td height="2" bgcolor="#677789"></td>
              </tr>
              <tr>
                <td height="6" bgcolor="#CECECE"></td>
              </tr>
              <tr>
                <td valign="top"> <br>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td class="content12"><strong>一次盘点输入</strong></td>
                      </tr>
                    </table>
                    <br>
                    <table width="100%" border="0" cellpadding="0" cellspacing="1" class="content12">
           
                      
                      <tr> 
                        <td width="98">零件料号：<font color="red">*</font></td>
                        <td width="121"><input class="form" name="stuffNo" size="14" > </td>
                        <td width="98">零件名称： <font color="red">*</font></td>
                        <td width="170"><input class="form" name="skuCode" size="14" >&nbsp;
                        	<a href="javascript:showPartInfo('skuCode')"><img src="googleImg/icon/search.gif" align="absmiddle" border="0"></a>
                        <td width="98">单位：</td>
                        <td><input class="form" name="skuUnit" size="14" > </td>
                      </tr>
                      
                      <tr> 
                        <td width="98">数量：<font color="red"><a onclick="temp()">* </a></font></td>
                        <td><html:text styleClass="form" property="skuNum" size="14" value="1"  maxlength="6" onkeydown="f_onlynumber()"/> </td>
                        <td></td>
                  			<td></td>
                  			<td></td>
                  			<td></td>
                      </tr>
                      <tr class="tableback2"> 
                        <td valign="top">备注：</td>
                        <td colspan="5" valign="top"> 
                        	<html:textarea property="remark" rows="2" cols="8" styleClass="form" style="width:100%" ></html:textarea>
                        </td>
                 			</tr>
                      <tr> 
                        <td colspan="6"> <input type="button" class="button2" onclick="f_add()" value='添加'></td>
                      </tr>
                    </table>
                    <br>
                    <table id="table1" width="100%" border="0" cellpadding="0" cellspacing="1" class="content12">
                      <tr><td  width="35"><font color="blue">数量:</font></td>
                      	 <td id="allNum" ><font color="blue"><%=stockInfoList.size()%></font></tr>
                      <tr bgcolor="#CCCCCC"> 
                        <td><strong>序列</strong></td>
                        <td><strong>零件料号</strong></td>
                        <td><strong>零件名称</strong></td>
                        <td><strong>单位</strong></td>
                        <td><strong>盘点数量</strong></td>
                        
                      </tr>
         
       <%if(stockInfoList!=null){
       	    String strTr="";
      	    for(int i=0;i<stockInfoList.size();i++){
      	        if(i%2==0) strTr="tableback2";
      	        else strTr="tableback1";
      		String[] temp=(String[])stockInfoList.get(i);
      		
      %>
      
      <tr class="<%=strTr%>"> 
      	  <input type="hidden" name="stockTakeDetailId" value="<%=temp[0]%>">
      	  <td ><%=i+1%></td>
          <td ><%=temp[2]==null?"":temp[2]%></td>
          <td ><%=temp[3]==null?"":temp[3]%></td>
          <td ><%=temp[6]==null?"":temp[6]%></td>
          <td ><input class="form" name="takeNum" size="6" value="<%=temp[5]==null?"":temp[5]%>" onkeydown="f_onlynumber()" ></td>
       	  
        </tr>
      
      <%}}%>
      <tr> 
                  <td height="2" colspan="9" bgcolor="#ffffff"></td>
                </tr>
                <tr> 
                  <td height="1" colspan="9" bgcolor="#677789"></td>
                </tr>
                
     </table></td>
              </tr>
              
              <tr> 
                  <td>
                    <%if(stockInfoList!=null&&stockInfoList.size()>0){
                    	strDisabled="";
                      }
                    %>
                    <input id="tempSave" type="button" class="button2" onClick="f_tempSave()" <%=strDisabled%> value="暂存"> 
                    <!--<input id="diffCompare" type="button" class="button4" onClick="f_diffCompare()" <%=strDisabled%> value="差异比较"> -->
                    <input id="confirm" type="button" class="button4" onClick="f_confirm()" <%=strDisabled%> value="盘点确认">
                    <input type="button" class="button4" onClick="f_cancel()" value="盘点取消"> 
                  </td>
                </tr>
                
                </table></TD>
		</tr>
  </tr>
</table>
</html:form>
<%
}catch(Exception e){
	e.printStackTrace();
}
}%>
</body>
<!-- InstanceEnd --></html>
 
<SCRIPT language=JAVASCRIPT1.2>

var takeNum="";
var ids="";
var globalButton = null;


var ajax = new sack(); 
function f_add(){
    if(f_check()){
    	ajax.setVar("skuCode", escape(document.forms[0].skuCode.value)); 
    	ajax.setVar("stuffNo", document.forms[0].stuffNo.value); 
    	ajax.setVar("skuNum", document.forms[0].skuNum.value); 
    	ajax.setVar("stockTakeId", document.forms[0].stockTakeId.value); 
    	
			ajax.setVar("method", "takeFirstAdd");		
			ajax.requestFile = "stockTakeAction.do";		
			ajax.method = "GET";				
			ajax.onCompletion = whenCompleted;	
			ajax.runAJAX();     
    }
}

function f_check(){
    var retFlag=false;
    
    if(f_isNull(document.forms[0].skuCode,'零件编号')&&f_isNull(document.forms[0].stuffNo,'零件料号')
    	&&f_isNull(document.forms[0].skuNum,'数量') &&f_isValidInt(document.forms[0].skuNum,'数量')){
				retFlag=true;
    }
    return retFlag;
}

function whenCompleted(){
	var returnXml = ajax.responseXML;

	var addFlag = returnXml.getElementsByTagName("addFlag")[0].firstChild.nodeValue; 
	if(!eval(addFlag)){
		alert("添加出错！");
	}else{
		var pkId = returnXml.getElementsByTagName("pkId")[0].firstChild.nodeValue;
		if(pkId==-2){
				alert("该次操作的料号在系统内未定义 ："+document.forms[0].stuffNo.value);
		}else{
				alert("添加成功！");
				
				var allNum=new Number(document.getElementById("allNum").innerText);
				document.getElementById("allNum").innerText=allNum+1;
				
				var newrow=table1.insertRow(2);
				newrow.className="tableback1";
				newrow.insertCell(0).innerHTML="0";
				newrow.insertCell(1).innerHTML="<input type='hidden' name='stockTakeDetailId' value='"+pkId+"'>"+document.forms[0].stuffNo.value;
				newrow.insertCell(2).innerHTML=document.forms[0].skuCode.value;
				newrow.insertCell(3).innerHTML="<input type='text' class='form' name='takeNum' size='6' value='"
					+document.forms[0].skuNum.value+"'>";
					
				document.getElementById("tempSave").disabled=false;
				//document.getElementById("diffCompare").disabled=false;
				document.getElementById("confirm").disabled=false;
		}
	}
	
}
	


function f_tempSave(){
    takeNum="";
    ids="";
    var len = document.forms[0].takeNum.length;
    if(len>1){
	    for(var i=0;i<len;i++){
	        var tempNum=document.forms[0].takeNum[i].value;
	        var tempId=document.forms[0].stockTakeDetailId[i].value;
	        if(tempNum!='') {
	            takeNum+=","+tempNum;
	            ids+=","+tempId;
	        }
	    }
	takeNum=takeNum.substring(1);
    	ids=ids.substring(1);
    }else{
    	takeNum=document.forms[0].takeNum.value;
    	ids=document.forms[0].stockTakeDetailId.value;
    }
   
        ajax.setVar("takeNumAll", takeNum); 
    	ajax.setVar("stockTakeDetailId", ids); 
    	
	ajax.setVar("method", "tempSave");		
	ajax.requestFile = "stockTakeAction.do";		
	ajax.method = "POST";				
	ajax.onCompletion = saveCompleted;	
	ajax.runAJAX();     
	globalButton = event.srcElement;
	globalButton.disabled = true;
}

	function saveCompleted(){  
		var returnXml = ajax.responseXML;
		var saveFlag = returnXml.getElementsByTagName("saveFlag")[0].firstChild.nodeValue; 
		if(!eval(saveFlag)){
			alert("暂存失败！");
		}else{
			alert("暂存成功");
		}
		
		globalButton.disabled = false;
		globalButton = null;
		
	}

function f_diffCompare() {
    if(f_saveChk()){
    	document.forms[0].action="stockTakeAction.do?method=diffCompare&flag=1";
				document.forms[0].takeNumAll.value=takeNum;
				document.forms[0].idsAll.value=ids;
				document.forms[0].target="_blank";
				document.forms[0].submit(); 
	
    }
}

function f_saveChk() {
    takeNum="";
    ids="";
    var len = document.forms[0].takeNum.length;
    if(len>1){
	    for(var i=0;i<len;i++){
	        var tempNum=document.forms[0].takeNum[i].value;
	        var tempId=document.forms[0].stockTakeDetailId[i].value;
	        
	        if(tempNum=='') {
	            alert("第"+(i+1)+"行 数量不能为空");
	            takeNum="";
	    	    ids="";
	            return false;
	        }else{
	            takeNum+=","+tempNum;
	            ids+=","+tempId;
	        }
	    }
	takeNum=takeNum.substring(1);
    	ids=ids.substring(1);
    }else{
    	var tempNum=document.forms[0].takeNum.value;
	var tempId=document.forms[0].stockTakeDetailId.value;
	if(tempNum=='') {
	    alert("数量不能为空");
	    takeNum="";
	    ids="";
	    return false;
	}
	takeNum=tempNum;
    	ids=tempId;
    }

    return true;
   	
}


function f_cancel(){
    globalButton = event.srcElement;
    globalButton.disabled = true;
    location="stockTakeAction.do?method=takeCancel&stockTakeId="+document.forms[0].stockTakeId.value;
}

function f_confirm(){
    if(f_saveChk()){
    	globalButton = event.srcElement;
	globalButton.disabled = true;
	
	document.forms[0].action="stockTakeAction.do?method=takeFirstConfirm";
	document.forms[0].takeNumAll.value=takeNum;
	//document.forms[0].idsAll.value=ids;
	document.forms[0].target="_self";
	document.forms[0].submit(); 
    	
    }
}


function f_property(){
    var parts=document.forms[0].hiddenPartCode.value;
    var partsArray=parts.split(unescape('%02'));
    document.forms[0].partDescCn.value=partsArray[1];
    document.forms[0].partDescEn.value=partsArray[2];
}


function temp(){
    if(f_isNull(document.forms[0].skuNum,'数量')&&f_isValidInt(document.forms[0].skuNum,'数量')){
    	var tempNum1=document.forms[0].skuNum.value;
    	var len = document.forms[0].takeNum.length;
	    if(len>1){
		    for(var i=0;i<len;i++){
		        document.forms[0].takeNum[i].value=tempNum1;
		    }
		
	    }else{
	    	document.forms[0].takeNum.value=tempNum1;
	    }
    }
}

function f_chkSnLot(tNum,sn,lot,place){

    	if(formatFloat(tNum)>1){
    		if(sn!=null&&sn!=''&&sn!='null'){
    		    alert("有sn号的零件数量不能大于1");
    		    f_clearNum(place);
    		}else if(lot!=null&&lot!=''&&lot!='null'){
    		    alert("有lot号的零件数量不能大于1");
    		    f_clearNum(place);
    		}
    	}
}


function f_clearNum(place){
    var len = document.forms[0].takeNum.length;
    if(len>1){
	document.forms[0].takeNum[place].value='';
    }else{
    	document.forms[0].takeNum.value='';
    }
}
</SCRIPT>