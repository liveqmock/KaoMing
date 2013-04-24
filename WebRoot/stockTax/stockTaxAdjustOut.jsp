<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ page import="java.util.ArrayList"%> 
<%@ page import="java.util.List"%> 
<%@ page import="com.dne.sie.common.tools.CommonSearch"%> 

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>stockQueryList</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/styles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/autocomplete.css" /> 
<script type="text/javascript" src="js/prototype.js"></script>
<script type="text/javascript" src="js/autocomplete.js"></script>
<SCRIPT language="javascript" src="js/commonSelect.js"></SCRIPT>
<script language=javascript src="js/popCalendar.js"></script>
<script language=javascript src="js/inputValidation.js"></script>
<script language=javascript src="js/common.js"></script>
<script language=javascript src="js/popPartInfo.js"></script>

</head>
<%
	try{
		String tag = request.getAttribute("tag")==null?"":(String)request.getAttribute("tag");
		List customerList=CommonSearch.getInstance().getCustomerList();
%>
<script>		
var tag="<%=tag%>";
if(tag=="1") alert("操作成功");
else if(tag=="-1") alert("操作失败");
</script>	
<%
		ArrayList stockInfoList = (ArrayList)request.getAttribute("stockInfoList");
		int count=0;
		if(stockInfoList!=null){
			count=Integer.parseInt((String)stockInfoList.get(0));
		}


%>
<body >
<html:form action="stockOutOperateAction.do?method=stockAdjustOutList" method="post" >
  <input name="ids" type="hidden">
  <input name="adOutNum" type="hidden">
  <input name="adOutCost" type="hidden">
  
<table align=center width="99%">
				
                <tr class="tableback2"> 
                  <td width="98">料号：</td>
                  <td width="121"><html:text  styleClass="form" property="stuffNo"  maxlength="32"  size="16"  /></td>
                  <td width="98">零件名称：</td>
                  <td width="170"><html:text  styleClass="form" property="skuCode"  maxlength="32"  size="22"   />&nbsp;
                        	<a href="javascript:showPartInfo('skuCode')"><img src="googleImg/icon/search.gif" align="absmiddle" border="0"></a></td>
                  <td width="98">单价：</td>
                  <td width="121"><html:text  styleClass="form" property="strPerCost"    maxlength="8" size="16" onkeydown="f_onlymoney()"/></td>
                </tr>
             
               
                
                </table>
	<h3 class="underline"><p align="left">&nbsp;&nbsp;<input name="query" type="button" value="查询" onclick="f_query()"></p></h3>

	 <table width="99%" border="0" cellpadding="0" cellspacing="1" class="content12">
	 <tr><font color="blue">记录条数： <%=count%></font> </tr>
                      <tr bgcolor="#CCCCCC"> 
                        <td width="10"><input name="allbox" type="checkbox" onClick="checkAll(this);"></td>
                        <td width="15"><strong>行</strong></td>
                        <td width="13%"><strong>&nbsp;料号</strong></td>
                        <td width="18%"><strong>零件名称</strong></td>
                        <td><strong>数量</strong></td>
                        <td><strong>成本单价(RMB)</strong></td>
                        <td><strong>数量（税）</strong></td>
                        <td ><strong>成本单价（税）(RMB)</strong></td>
                      </tr>
         
        <%
       if(stockInfoList!=null){
       		char SPLIT=0x0001;
       	    String strTr="";
      	    for(int i=1;i<stockInfoList.size();i++){
      	        if(i%2==0) strTr="tableback2";
      	        else strTr="tableback1";
      			String[] temp=(String[])stockInfoList.get(i);
      %>      
      <tr class="<%=strTr%>"> 
          <td align=center><input type="checkbox" name="chk" value="<%=temp[0]+SPLIT+i%>"></td>
          <td ><font color="blue"><%=i%>&nbsp;</font></td>
          <td ><%=temp[1]==null?"":temp[1]%></td>
          <td ><%=temp[2]==null?"":temp[2]%></td>
          <td ><font <%if("0".equals(temp[3])){%>size="3" color="red"<%}%>><%=temp[3]==null?"":temp[3]%></font></td>
          
          <td ><%=temp[4]==null?"":temp[4]%></td>
       	  <td > 
       	       <input name="skuNumTax" class="form" size="5" value="<%=temp[5]%>"  maxlength="6" onkeydown="f_onlynumber()" onchange="f_ckbox('<%=temp[0]+SPLIT+i%>')">
    	  </td>
    	  <td > 
       	       <input name="perCostTax" class="form" size="10" value="<%=temp[6]%>"  maxlength="10" onkeydown="f_onlymoney()" >
    	  </td>
        </tr>      
        
      <%}}else{%> 
	<tr class="tableback1"></tr>
      <%}%>
      
   
        
        <tr> 
      <td height="1" bgcolor="#677789" colspan="11"></td>
    </tr>
        
        </table>
         <tr align="left">
    		<td class="content_yellow">
              <input name="outConfirm" type="button"  value='确认调整' onclick="f_confirm()">
             </td>
           </tr>

	
</html:form>
</body>
</html>
       
      
<script language="JavaScript">
function f_query(){
	document.forms[0].query.disabled = true;
	document.forms[0].action="stockOutOperateAction.do?method=stockTaxAdjustOutList"; 
	document.forms[0].submit();
}
	
function f_ckbox(val){
	var theForm = document.forms[0];
		for(var i = 0; i < theForm.length; i++) {
	      if (theForm[i].type == "checkbox" && theForm[i].disabled==false&&theForm[i].value==val) {
	         theForm[i].checked=true;
	         break;
		  }
	   }
	
}
	
function f_confirm(){
	var ids=chk();
	
	if(ids==null||ids==''){
		alert('请先选择零件!');
	
	}else{
		if(getAdOutNums()){
			document.forms[0].outConfirm.disabled = true;
			document.forms[0].action="stockOutOperateAction.do?method=stockAdjustTaxOutConfirm";
			document.forms[0].submit();
		}
	}
	
}


function getAdOutNums(){

 	<%
    String strChk="chk",tempSeq="";
    if(count>=2) {
    	strChk="chk[i]";
    	tempSeq="[tempSeq]";
    }
    %>
    
    var chkId = '';
    var chkOutNum = '';
    var chkOutRemark = '';
    var spliter = unescape('%01');
    var len = document.forms[0].chk.length;
	if(len>1){
	    var tag = 0;
		for(var i=0;i<len;i++){
		    if(document.forms[0].<%=strChk%>.checked){   
		        var chkVal=document.forms[0].<%=strChk%>.value;
		        var tempId=chkVal.substring(0,chkVal.indexOf(spliter));
		        var tempSeq=chkVal.substring(chkVal.indexOf(spliter)+1)-1;

		        var tempONum=new Number(document.forms[0].skuNumTax<%=tempSeq%>.value);
		        var tempORemark=document.forms[0].perCostTax<%=tempSeq%>.value;
		        
		        if(tempONum==null||tempONum==''){
		            alert("请输入第"+(i+1)+"行记录的数量");
		            document.forms[0].skuNumTax<%=tempSeq%>.focus();
		            return false;
		        }else if(tempORemark==null||tempORemark==''){
		            alert("请输入第"+(i+1)+"行记录的成本");
		            document.forms[0].perCostTax<%=tempSeq%>.focus();
		            return false;
		        
		        }else{
		        	
		            if (tag==0) {
		            	chkId = tempId;
		            	chkOutNum = tempONum;
		            	chkOutRemark = tempORemark;
		            	tag = 1;
		            } else {
		            	chkId = chkId + ","+tempId;
		            	chkOutNum = chkOutNum + ","+tempONum;
		            	chkOutRemark = chkOutRemark + ","+tempORemark;
		            }           
		    	}				
		    }
		}
	} else {
		if(document.forms[0].chk.checked){
		    var chkVal=document.forms[0].<%=strChk%>.value;
		    var tempId=chkVal.substring(0,chkVal.indexOf(spliter));
		    var tempSeq=chkVal.substring(chkVal.indexOf(spliter)+1);
		    
		    var tempONum=new Number(document.forms[0].skuNumTax<%=tempSeq%>.value);
		    var tempORemark=document.forms[0].perCostTax<%=tempSeq%>.value;
		    
		    if(tempONum==null||tempONum==''){
		        alert("请输入数量");
		        document.forms[0].skuNumTax<%=tempSeq%>.focus();
		        return false;
		    }else if(tempORemark==null||tempORemark==''){
		   	 	document.forms[0].perCostTax<%=tempSeq%>.focus();
		        alert("请输入成本");
		        return false;
		    }else{
		    	
				chkId=tempId;
		        chkOutNum = tempONum;
		        chkOutRemark = tempORemark;
		    }
	 	}
	}
	
	document.forms[0].ids.value=chkId;
	document.forms[0].adOutNum.value=chkOutNum;
	document.forms[0].adOutCost.value=chkOutRemark;
	
	return true;
}

new AutoTip.AutoComplete("stuffNo", function() {
	 return "partInfoAction.do?method=getPartInfo&inputValue=" + escape(this.text.value);
});
	
</script>


<%
}catch(Exception e){
	e.printStackTrace();
}%>