<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri=  "/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ page import="java.util.ArrayList"%> 
<%@ page import="com.dne.sie.common.tools.CommonSearch"%> 
 
<html>
<head>
<title>stockOutOperate_list</title>
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/styles2.css">

</head>
<%
    ArrayList stockInfoList = (ArrayList)request.getAttribute("diffList");
    String flowId = (String)request.getAttribute("flowId");
    String flowUser = (String)request.getAttribute("flowUser");
    String flowTime = (String)request.getAttribute("flowTime");
    String flag = (String)request.getAttribute("flag");
    String strTitle="二次盘点";
    if("1".equals(flag)) strTitle="一次盘点";
    
    CommonSearch cs = CommonSearch.getInstance();
    if(flowUser!=null) flowUser=cs.findUserNameByUserId(new Long(flowUser));
%>

<body bgcolor="#ffffff" leftmargin="0" topmargin="0" rightmargin="0" bottommargin="0" marginwidth="0" marginheight="0" >
<input type="hidden" name="stockTakeId" value="<%=flowId%>" >
 <html:form action="stockTakeAction.do?method=takeSave" method="post" >

  <table width="90%" border="0" align="center" cellpadding="2" cellspacing="1" class="content12">
  <tr>
    <td>库存管理&gt;<%=strTitle%>&gt;差异数据列表</td>
  </tr>
</table>
<br>
<table width="90%" border="0" align="center" cellpadding="0" cellspacing="0" class="content12">
  <tr> 
    <td ><p>盘点流水号： <%=flowId%>&nbsp;&nbsp;&nbsp;&nbsp;盘点人： <%=flowUser%>&nbsp;&nbsp;&nbsp;&nbsp;盘点开始日期： <%=flowTime%> </p></td>
  </tr>
  <tr><font color="blue">差异数量：<%=stockInfoList.size()%></font></tr>
</table>
<br>
<%if(flag.equals("out")){%>
<tr>
<p><font color="red">二次盘点差异零件超过1000个，请重新盘点</font></p>
</tr>
<%}else{%>
<table width="90%" border="0" align="center" cellpadding="0" cellspacing="1" class="content12">

<%if(stockInfoList!=null&&stockInfoList.size()>0){%>
  <tr bgcolor="#CCCCCC"> 
    <td><strong> 零件料号</strong></td>
    <td><strong> 零件编号</strong></td>
    <td><strong> 仓位号</strong></td>
    <td><strong>库存数量</strong></td>
    <td><strong>盘点数量</strong></td>
  </tr>
         
       <%
       	    String strTr="";
      	    for(int i=0;i<stockInfoList.size();i++){
      	        if(i%2==0) strTr="tableback2";
      	        else strTr="tableback1";
      		String[] temp=(String[])stockInfoList.get(i);
      		
      %>
      
      <tr class="<%=strTr%>"> 
          <td ><%=temp[2]==null?"":temp[2]%></td>
       	  <td ><%=temp[3]==null?"":temp[3]%></td>
       	  <td ><%=temp[1]==null?"":temp[1]%></td>
       	  <td ><%=temp[4]==null?"":temp[4]%></td>
       	  <td ><%=temp[5]==null?"":temp[5]%></td>
        </tr>
      
      <%}}else{%>
      	  <tr> 
    		<td ><p>没有差异数据</p></td>
  	</tr>
      
      <%}%> 
      <tr> 
    <td height="2" colspan="9" bgcolor="#ffffff"></td>
  </tr>
  <tr> 
    <td height="1" colspan="9"  bgcolor="#677789"></td>
  </tr>
</table>
<table width="90%" border="0" align="center" cellpadding="2" cellspacing="1" class="content12">
  <tr> 
    <td><div align="right"> 
    <%if(stockInfoList!=null&&stockInfoList.size()>0){%>
        <input type="button" class="button6" value="打印差异表" onclick="f_print()">
        <%}%>
        <input type="button" class="button2" onclick="self.close();" value="关闭">
      </div></td>
  </tr>
</table>
<%}%>
</body>
</html:form>
</html>
<!-- InstanceEnd --></html>
 
<SCRIPT language=JAVASCRIPT1.2>


function f_print(){
    document.forms[0].action="stockTakeAction.do?method=diffExcelCreate";
    document.forms[0].submit();
}


</SCRIPT>