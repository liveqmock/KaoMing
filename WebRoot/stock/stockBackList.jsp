<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri=  "/WEB-INF/tlds/common.tld" prefix="comtld" %>
<%@ page import="java.util.ArrayList"%> 
<%@ page import="java.util.List"%> 
<%@ page import="com.dne.sie.common.tools.DicInit"%> 
<%@ page import="com.dne.sie.stock.bo.StockBackBo"%> 

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>stockBackList</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/styles2.css" rel="stylesheet" type="text/css" />
<SCRIPT language="javascript" src="js/commonSelect.js"></SCRIPT>
<script language=javascript src="js/popCalendar.js"></script>
<script language=javascript src="js/inputValidation.js"></script>
<script language=javascript src="js/common.js"></script>
</head>
<%
	try{
		ArrayList stockBackList = (ArrayList)request.getAttribute("stockBackList");
	
		ArrayList backItemList = (ArrayList)DicInit.SYS_CODE_MAP.get("STOCK_BACK_ITEM");	
		List applyUserList = StockBackBo.getInstance().backApplyUserList();
		
		ArrayList binCodes = (ArrayList)session.getAttribute("binCodes");

%>
<body >
<html:form action="stockBackAction.do?method=stockBackList" method="post" >
<input name="partsIds" type="hidden">
<html:hidden property="stockBackItemBak"/>
<html:hidden property="binCode"/>
<table align=center width="99%">
				
                <tr class="tableback2"> 
                  <td width="98">销售单/维修单：</td>
                  <td width="126"><html:text  styleClass="form" property="formNo"  maxlength="32"  size="16"  /></td>
                     
                  <td width="98">料号：</td>
                  <td width="121"><html:text  styleClass="form" property="stuffNo"  maxlength="32"  size="16"  /></td>
                   
                  <td width="98">零件名称：</td>
                  <td width="121"><html:text  styleClass="form" property="skuCode"  maxlength="64"  size="16" /></td>
                    
                </tr>
                <tr class="tableback1"> 
                  <td width="98">回库类型：</td>
                  <td width="126"><html:select  styleClass="form" property="stockBackItem"    style="width:100">
                  		<%for(int i=0; backItemList!=null&&i<backItemList.size(); i++ ){
                            	String[] temp=(String[])backItemList.get(i);
                        %>
	                        <html:option value="<%=temp[0]%>"><%=temp[1]%></html:option>
                        <%}%>
                  	</html:select></td>
                   
                  <td width="97">回库申请人：</td>
                  <td width="172"><html:select  styleClass="form" property="applyUser"    style="width:100">
                  		<html:option value="0">全部</html:option>
                  		<%for(int i=0; applyUserList!=null&&i<applyUserList.size(); i++ ){
                            	String[] temp=(String[])applyUserList.get(i);
                        %>
	                        <html:option value="<%=temp[0]%>"><%=temp[1]%></html:option>
                        <%}%>
                  	</html:select>
                   </td>
               
                  <td width="97"></td>
                  <td width="172"></td>
                </tr>
                
                </table>
	<h3 class="underline"><p align="left">&nbsp;&nbsp;<input name="query" type="button" value="查询" onclick="f_query()"></p></h3>
	 <table width="99%" border="0" cellpadding="0" cellspacing="1" class="content12">
                      <tr bgcolor="#CCCCCC"> 
          				<td><strong>销售单/维修单</strong></td>   
                        <td><strong>料号</strong></td>
                        <td><strong>零件名称</strong></td>
                        <td><strong>数量</strong></td>
                        <td><strong>单位</strong></td>
                        <td><strong>申请人</strong></td>
                        <td><strong>申请日期</strong></td>
                        <td><strong>回库类型</strong></td>
                        <td><strong>仓位</strong></td>
                        <td><strong>操作</strong></td>
                      </tr>
         
       
        <%
       if(stockBackList!=null){
       	    String strTr="";
      	    for(int i=0;i<stockBackList.size();i++){
      	        if(i%2==0) strTr="tableback2";
      	        else strTr="tableback1";
      		String[] temp=(String[])stockBackList.get(i);
      		
      %>      
      <tr class="<%=strTr%>"> 
		  <td ><%=temp[1]==null?"":temp[1]%></td>    
          <td ><%=temp[2]==null?"":temp[2]%></td>
          <td ><%=temp[3]==null?"":temp[3]%></td>
          <td ><%=temp[4]==null?"":temp[4]%></td>
		  <td ><%=temp[5]==null?"":temp[5]%></td>
		  <td ><%=temp[6]==null?"":temp[6]%></td>
		  <td ><%=temp[7]==null?"":temp[7]%></td>
		  <td ><%=DicInit.getSystemName("STOCK_BACK_ITEM",temp[8])%></td>
		  <td><select id="<%=temp[0] %>" name="binCodes" style="width:100" class="form">
		  				<option value="">==请选择==</option>
                        <%for(int j=0; binCodes!=null&&j<binCodes.size(); j++ ){
                            	String bin=(String)binCodes.get(j);
                        %>
	                        <option value="<%=bin%>"><%=bin%></option>
                        <%}%>
	          </select>
	      </td>
	      <td ><input name="back" type="button"  value='回库' onclick="f_back('<%=temp[0]%>')"></td>
        </tr>      
      <%}}else{%> 
	<tr class="tableback1"></tr>
      <%}%>
      
        <tr> 
      <td height="1" bgcolor="#677789" colspan="11"></td>
    </tr>
    </table>
    
              <!-- <tr align="left">
                    <td class="content_yellow">
                     <input name="back" type="button"  value='回库' onclick="f_back()">
                    </td>
                  </tr> -->

	
	  
</body>
</html:form>
</html>


<SCRIPT language=JAVASCRIPT1.2>
function f_query(){
	document.forms[0].query.disabled = true;
	document.forms[0].action="stockBackAction.do?method=stockBackList"; 
	document.forms[0].submit();
}

function f_back(ids){
	
	if(ids!=''&&ids!=null){
		var binCode = document.getElementById(ids).value;
		if(binCode==""){
			alert("请先选择仓位");
			return;
		}

		document.forms[0].back.disabled = true;
		document.forms[0].binCode.value=binCode;
		document.forms[0].partsIds.value=ids;
		document.forms[0].action="stockBackAction.do?method=stockBackOperate"; 
		document.forms[0].submit();
	}
}


</SCRIPT>

<%
}catch(Exception e){
	e.printStackTrace();
}%>