<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ page import="java.util.ArrayList"%> 
<%@ page import="com.dne.sie.common.tools.DicInit"%> 

<html>
<head>
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
		ArrayList stockToolsList = (ArrayList)request.getAttribute("stockToolsList");
		int count=0;
		if(stockToolsList!=null){
			count=Integer.parseInt((String)stockToolsList.get(0));
		}

		ArrayList stockStatus = (ArrayList)DicInit.SYS_CODE_MAP.get("STOCK_STATUS");	
		


%>
<body >
<html:form action="stockToolsAction.do?method=stockToolsList" method="post" >

<table align=center width="99%">
				
                <tr class="tableback1"> 
                  <td width="70">工具料号：</td>
                  <td width="121"><html:text  styleClass="form" property="stuffNo"  maxlength="32"  size="16"  /></td>
                  <td width="70">工具名称：</td>
                  <td width="126"><html:text  styleClass="form" property="skuCode"  maxlength="32"  size="16"  /></td>
                  <td width="70">工具单位：</td>
                  <td width="121"><html:text  styleClass="form" property="skuUnit"  maxlength="4"  size="16"  /></td>
                </tr>
                <tr class="tableback2"> 
                
                  <td width="70">库存状态：</td>
                  <td colspan=5>
                  	<html:select  styleClass="form" property="stockStatus"    style="width:100">
                  		<html:option value="">全部</html:option>
                  		<%for(int i=0; i<stockStatus.size(); i++ ){
                            	String[] temp=(String[])stockStatus.get(i);
                        %>
	                        <html:option value="<%=temp[0]%>"><%=temp[1]%></html:option>
                        <%}%>
                  	</html:select></td>
                  	
                  </tr>
               
                <tr class="tableback2"> 
                        <td valign="top">备注：</td>
                        <td colspan="5" valign="top"> 
                        	<html:textarea property="remark" rows="2" cols="8" styleClass="form" style="width:100%" ></html:textarea>
                        </td>
                 </tr>
                </table>
	<h3 class="underline"><p align="left">&nbsp;&nbsp;<input name="query" type="button" value="查询" onclick="f_query()">
	&nbsp;&nbsp;<input name="excel" type="button" value="工具调入" onclick="f_in()"></p></h3>

	 <table width="99%" border="0" cellpadding="0" cellspacing="1" class="content12">
	  <tr><font color="blue">记录条数： <%=count%></font> </tr>
	
                      <tr bgcolor="#CCCCCC"> 
                        <td width="15%"><strong>&nbsp;工具料号</strong></td>
                        <td width="25%"><strong>工具名称</strong></td>
                        <td width="10%"><strong>规格</strong></td>
                        <td width="5%"><strong>单位</strong></td>
                        <td width="5%"><strong>数量</strong></td>
                        <td width="10%" ><strong>状态</strong></td>
                        <td width="15%"><strong>所有者</strong></td>
                        <td width="15%"><strong>备注</strong></td>
                        <!-- <td width="10%"><strong>出入库流水</strong></td> -->
                      </tr>
         
        <%
       if(stockToolsList!=null){
       	    String strTr="";
      	    for(int i=1;i<stockToolsList.size();i++){
      	        if(i%2==0) strTr="tableback2";
      	        else strTr="tableback1";
      		String[] temp=(String[])stockToolsList.get(i);
      %>      
      <tr class="<%=strTr%>"> 
          <%-- <td ><A href="javascript:view('<%=temp[0]%>')"><%=temp[1]%></A></td> --%>
          <td ><%=temp[1]==null?"":temp[1]%></td>
          <td ><%=temp[2]==null?"":temp[2]%></td>
          <td ><%=temp[3]==null?"":temp[3]%></td>
          <td ><%=temp[8]==null?"":temp[8]%></td>
          <td ><%=temp[4]==null?"":temp[4]%></td>
          <td ><%=temp[5]==null?"":temp[5]%></td>
          <td ><%=temp[7]==null?"":temp[7]%></td>
          <td ><%=temp[6]==null?"":temp[6]%></td>
       	  <%-- <td align="center"><a href="javascript:flow('<%=temp[0]%>')"><font face="Webdings" color="blue">8</font></a></td> --%>
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
	document.forms[0].action="stockToolsAction.do?method=stockToolsList"; 
	document.forms[0].submit();
}

function view(stuffNo){
    window.open("stockToolsAction.do?method=stockInfoView&stuffNo="+stuffNo,"","width=750,height=400,left=50,top=10,menubar=no,toolbar=no,resizable=yes,scrolling=yes,scrollbars=yes");
   
}
function flow(stuffNo){
    window.open("stockToolsAction.do?method=stockFlowView&stuffNo="+stuffNo,"","width=750,height=400,left=50,top=10,menubar=no,toolbar=no,resizable=yes,scrolling=yes,scrollbars=yes");

}

function f_in(){
    document.forms[0].action="stockToolsAction.do?method=toolsAdjustInDetail"; 
	document.forms[0].submit();
}


</SCRIPT>

<%
}catch(Exception e){
	e.printStackTrace();
}%>