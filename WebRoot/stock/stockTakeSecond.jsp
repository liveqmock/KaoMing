<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri=  "/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ page import="java.util.ArrayList"%> 
<%@ page import="com.dne.sie.common.tools.DicInit"%>
 <%@ page import="com.dne.sie.common.tools.CommonSearch"%> 

<html>
<head>
<title>stockOutOperate_list</title><SCRIPT language="javascript" src="js/screen.js"></SCRIPT>
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/styles2.css">
<script language=javascript src="js/popPartInfo.js"></script>
<script language=javascript src="js/common.js"></script>
<script language=javascript src="js/ajax.js"></script>
<script language=javascript src="js/inputValidation.js"></script>

</head>
<%
    ArrayList stockInfoList = (ArrayList)request.getAttribute("secondList");
    
    String flowId = (String)request.getAttribute("flowId");  
    String flowTime = (String)request.getAttribute("flowTime");  
    String flowUser = (String)request.getAttribute("flowUser");  
    String takeId = (String)request.getAttribute("takeId")==null?"-1":(String)request.getAttribute("takeId"); 
    
    CommonSearch cs = CommonSearch.getInstance();
    if(flowUser!=null) flowUser=cs.findUserNameByUserId(new Long(flowUser));

%>

<body bgcolor="#ffffff" leftmargin="0" topmargin="0" rightmargin="0" bottommargin="0" marginwidth="0" marginheight="0" >
 <html:form action="stockTakeAction.do?method=takeInit" method="post" >
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
                        <td class="content12"><strong>二次盘点输入</strong></td>
                      </tr>
                    </table>
                    <br>
                    <table width="90%" border="0" align="center" cellpadding="0" cellspacing="0" class="content12">
			  <tr> 
    				<td ><p>盘点流水号： <%=flowId%>&nbsp;&nbsp;&nbsp;&nbsp;盘点人： <%=flowUser%>&nbsp;&nbsp;&nbsp;&nbsp;盘点开始日期： <%=flowTime%> </p></td>
  			  </tr>
		    </table>
                    <br>
                    <table width="100%" border="0" cellpadding="0" cellspacing="1" class="content12">
                    <tr><font color="blue">盘点记录数：<%=stockInfoList.size()%></font></tr>
                      <tr bgcolor="#CCCCCC"> 
                        <td><strong> 零件料号</strong></td>
                        <td><strong> 零件编号</strong></td>
                        <td><strong> 仓位号</strong></td>
                        <td><strong>库存数量</strong></td>
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
          <td ><%=temp[2]==null?"":temp[2]%></td>
       	  <td ><%=temp[3]==null?"":temp[3]%></td>
       	  <td ><%=temp[1]==null?"":temp[1]%></td>
       	  <td ><%=temp[4]==null?"":temp[4]%></td>
       	  <td ><input class="form" name="takeNum" size="6" value="<%=temp[5]==null?"":temp[5]%>" onkeydown="f_onlynumber()" ></td>
       	  
        </tr>
      
      <%}}%> 
      
      <tr> 
                  <td height="2" colspan="10" bgcolor="#ffffff"></td>
                </tr>
                <tr> 
                  <td height="1" colspan="10" bgcolor="#677789"></td>
                </tr>
     </table></td>
              </tr>
              
              <tr> 
                  <td>
                    <%if(stockInfoList!=null&&stockInfoList.size()>0){%>
                    	<input type="button" class="button2" onClick="f_tempSave()" value="暂存"> 
                    	<input type="button" class="button4" onClick="diffCompare()" value="差异比较"> 
                    	<!--<input type="button" class="button4" onClick="f_diffAdjust()" value="库调申请">-->
                    <%}%>
                    
                    <input id="confirm" type="button" class="button4" onClick="f_confirm()"value="盘点确认"> 
                    <input id="cancel" type="button" class="button4" onClick="f_cancel()"value="盘点取消"> 
                  </td>
                </tr>
                
                </table></TD>
		</tr>
            </table></td>
  </tr>
</table>
</html:form>
</body>
<!-- InstanceEnd --></html>
 
<SCRIPT language=JAVASCRIPT1.2>

var takeNum="";
var ids="";

var ajax = new sack(); 
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
	
}


	function saveCompleted(){  
		var returnXml = ajax.responseXML;
		var saveFlag = returnXml.getElementsByTagName("saveFlag")[0].firstChild.nodeValue; 
		if(!eval(saveFlag)){
			alert("暂存失败！");
		}else{
			alert("暂存成功");
		}
		
	}
	
function diffCompare() {
    if(f_saveChk()){
    		document.forms[0].action="stockTakeAction.do?method=diffCompare&flag=2";
				document.forms[0].takeNumAll.value=takeNum;
				document.forms[0].idsAll.value=ids;
				document.forms[0].target="_blank";
				document.forms[0].submit(); 
    	
    }
}

function f_saveChk() {
    takeNum="";
    ids="";
    if(document.forms[0].takeNum!=null&&document.forms[0].takeNum!='undefined'){
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
    }
    return true;
   	
}


function f_cancel(){
	document.getElementById("cancel").disabled=true;
    location="stockTakeAction.do?method=takeCancel&stockTakeId="+document.forms[0].stockTakeId.value;

}

function f_confirm(){
    if(f_saveChk()){
    	document.getElementById("confirm").disabled=true;
    	
    	document.forms[0].action="stockTakeAction.do?method=takeSecondConfirm";
		document.forms[0].takeNumAll.value=takeNum;
		document.forms[0].target="_self";
		document.forms[0].submit(); 
    }
}

function f_autoTransfer(){
    if(f_saveChk()){
    	document.forms[0].action="stockTakeAction.do?method=autoTransfer";
				document.forms[0].takeNumAll.value=takeNum;
				document.forms[0].target="_self";
				document.forms[0].submit(); 
    	
    }
}

function f_diffAdjust(){
    if(f_saveChk()){
        if(confirm("每次盘点只能申请一次库调，是否确认？")){
    	    document.forms[0].action="stockTakeAction.do?method=diffAdjust";
			    document.forms[0].takeNumAll.value=takeNum;
			    document.forms[0].target="_self";
			    document.forms[0].submit(); 
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