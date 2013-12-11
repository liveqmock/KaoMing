<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ page import="java.util.ArrayList"%> 
<%@ page import="com.dne.sie.common.tools.DicInit"%> 

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>stockQueryList</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/styles2.css" rel="stylesheet" type="text/css" />
<SCRIPT language="javascript" src="js/commonSelect.js"></SCRIPT>
<script language=javascript src="js/popCalendar.js"></script>
<script language=javascript src="js/inputValidation.js"></script>
<script language=javascript src="js/common.js"></script>
</head>
<%
	try{
		ArrayList stockInfoList = (ArrayList)request.getAttribute("stockInfoList");
		int count=0;
		if(stockInfoList!=null){
			count=Integer.parseInt((String)stockInfoList.get(0));
		}

		ArrayList stockStatus = (ArrayList)DicInit.SYS_CODE_MAP.get("STOCK_STATUS");	
		
		String reportFileName=(String)request.getAttribute("reportFileName");
		ArrayList skuTypeList = (ArrayList)DicInit.SYS_CODE_MAP.get("SKU_TYPE");

		ArrayList binCodes = (ArrayList)session.getAttribute("binCodes");

%>
<body onload="showQueryDateTR('dateScope',3);">
<html:form action="stockInfoListAction.do?method=stockInfoList" method="post" >

<table align=center width="99%">
				
                <tr class="tableback1"> 
                  <td width="98">料号：</td>
                  <td width="121"><html:text  styleClass="form" property="stuffNo"  maxlength="32"  size="16"  /></td>
                  <td width="98"> 零件名称：</td>
                  <td width="126"><html:text  styleClass="form" property="skuCode"  maxlength="32"  size="16"  /></td>
                  <td width="98">仓位：</td>
                  <td width="121"><html:select styleClass="form" property="binCode"    style="width:100">
		  				<option value="">==请选择==</option>
                        <%for(int j=0; binCodes!=null&&j<binCodes.size(); j++ ){
                            	String bin=(String)binCodes.get(j);
                        %>
	                        <html:option value="<%=bin%>"><%=bin%></html:option>
                        <%}%>
	          </html:select></td>
                </tr>
                <tr class="tableback2"> 
                <td width="97">零件数量：</td>
                  <td width="126"><html:text  styleClass="form" property="strSkuNum"  maxlength="6"  size="16"  onkeydown="f_onlynumber()" /></td>
                  <td width="98"> 类型：</td>
                  <td width="126">
							<html:select property="skuType" styleClass="form">
	                          <option value="">全部</option>
								<%
								  for(int i=0;skuTypeList!=null&&i<skuTypeList.size();i++){
									String[] temp=(String[])skuTypeList.get(i);
									if(!temp[0].equals("L")&&!temp[0].equals("S")){
								%>
								 <html:option value="<%=temp[0]%>"><%=temp[1]%></html:option>
								<%}}%>
      						</html:select></td>
                  <td width="98">库存状态：</td>
                  <td width="121">
                  	<html:select  styleClass="form" property="stockStatus"    style="width:100">
                  		<html:option value="">全部</html:option>
                  		<%for(int i=0; i<stockStatus.size(); i++ ){
                            	String[] temp=(String[])stockStatus.get(i);
                        %>
	                        <html:option value="<%=temp[0]%>"><%=temp[1]%></html:option>
                        <%}%>
                  	</html:select></td>
                  </tr>
                <tr class="tableback1"> 
                  	<td width="80">入库时间：</td>
        			<td width="120"><html:select property="dateScope" styleClass="form" style="width:100" onchange="changeDateDisplay(this,'inDate1','inDate2')">
                            	<html:option value="4">全部</html:option>
                            	<html:option value="0">当日</html:option>
                            	<html:option value="3">日期范围</html:option>
            					<html:option value="1">当月</html:option>
            					<html:option value="2">本年</html:option>
                           </html:select>  </td>
                    <td width="80">起始日期： </td>
                    <td width="120"><html:text property="inDate1" styleId="inDate1" styleClass="form" readonly="true" size="14"/> 
           			<a onclick="event.cancelBubble=true;" href="javascript:showCalendar('imageOne',true,'inDate1');">
            			<img src="googleImg/icon/calendar.gif" id="imageOne" width="18" height="18" border="0" align="absmiddle">
           			</a>
        		</td>
                        
                        <td width="80">终止日期：</td>
                        <td width="120"><html:text property="inDate2" styleId="inDate2" styleClass="form" readonly="true" size="14"/> 
          			<a onclick="event.cancelBubble=true;" href="javascript:showCalendar('imageTwo',true,'inDate2');">  
             			<img src="googleImg/icon/calendar.gif" id="imageTwo" width="18" height="18" border="0" align="absmiddle">
           			</a>
        		</td>
                </tr>
                <tr class="tableback2"> 
                        <td valign="top">备注：</td>
                        <td colspan="5" valign="top"> 
                        	<html:textarea property="remark" rows="2" cols="8" styleClass="form" style="width:100%" ></html:textarea>
                        </td>
                 </tr>
                </table>
	<h3 class="underline"><p align="left">&nbsp;&nbsp;<input name="query" type="button" value="查询" onclick="f_query()">
	&nbsp;&nbsp;<input name="excel" type="button" value="生成Excel" onclick="f_excel()"></p></h3>

	 <table width="99%" border="0" cellpadding="0" cellspacing="1" class="content12">
	  <tr><font color="blue">记录条数： <%=count%></font> </tr>
	 <tr>
	 <%if(reportFileName!=null){%>&nbsp;&nbsp;<a href="<%=reportFileName%>" target="_blank"><%=reportFileName.substring(reportFileName.lastIndexOf('/')+1)%></a><%}%>
	 </tr>
                      <tr bgcolor="#CCCCCC"> 
                        <td width="30%"><strong>&nbsp;料号</strong></td>
                        <td width="30%"><strong>零件名称</strong></td>
                        <td><strong>类型</strong></td>
                        <td><strong>总数量</strong></td>
                        <td><strong>平均成本￥</strong></td>
                        <td><strong>平均进价$</strong></td>
                        <td width="10%"><strong>出入库流水</strong></td>
                      </tr>
         
        <%
       if(stockInfoList!=null){
       	    String strTr="";
      	    for(int i=1;i<stockInfoList.size();i++){
      	        if(i%2==0) strTr="tableback2";
      	        else strTr="tableback1";
      		String[] temp=(String[])stockInfoList.get(i);
      %>      
      <tr class="<%=strTr%>"> 
          <td ><A href="javascript:view('<%=temp[0]%>')"><%=temp[0]%></A></td>
          <td ><%=temp[1]==null?"":temp[1]%></td>
          <td ><%=temp[5]%></td>
          <td ><font <%if("0".equals(temp[2])){%>size="3" color="red"<%}%>><%=temp[2]==null?"":temp[2]%></font></td>
          <td ><%=temp[3]==null?"":temp[3]%></td>
          <td ><%=temp[4]==null?"":temp[4]%></td>
       	  <td align="center"><a href="javascript:flow('<%=temp[0]%>')"><font face="Webdings" color="blue">8</font></a></td>
        </tr>      
      <%}}else{%> 
	<tr class="tableback1"></tr>
      <%}%>
      <tr> 
      <td height="1" bgcolor="#677789" colspan="11"></td>
    </tr>

        
        </table>

	
</html:form>
</body>
</html>

<SCRIPT language=JAVASCRIPT1.2>
function f_query(){
	document.forms[0].query.disabled = true;
	document.forms[0].action="stockInfoListAction.do?method=stockInfoList"; 
	document.forms[0].submit();
}

function view(stuffNo){
    window.open("stockInfoListAction.do?method=stockInfoView&stuffNo="+stuffNo,"","width=750,height=400,left=50,top=10,menubar=no,toolbar=no,resizable=yes,scrolling=yes,scrollbars=yes");
   
}
function flow(stuffNo){
    window.open("stockInfoListAction.do?method=stockFlowView&stuffNo="+stuffNo,"","width=750,height=400,left=50,top=10,menubar=no,toolbar=no,resizable=yes,scrolling=yes,scrollbars=yes");

}

function f_excel(){
    document.forms[0].excel.disabled = true;
	document.forms[0].action="stockInfoListAction.do?method=stockInfoListCreate";
	document.forms[0].submit();
	setTimeout("doRefresh()",3000);
}
function doRefresh(){
	document.forms[0].excel.disabled = false;
}


</SCRIPT>

<%
}catch(Exception e){
	e.printStackTrace();
}%>