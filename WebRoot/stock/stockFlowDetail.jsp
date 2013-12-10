<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri=  "/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ page import="java.util.ArrayList"%> 
<%@ page import="java.util.List"%> 
<%@ page import="com.dne.sie.common.tools.DicInit"%> 
<%@ page import="com.dne.sie.stock.form.StockFlowForm"%> 
<html>
<head>
<title>flowDetail</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/styles.css" rel="stylesheet" type="text/css" />
<script language=javascript src="js/inputValidation.js"></script>
<script language=javascript src="js/checkValid.js"></script>
<script language=javascript src="js/ajax.js"></script>
</head>
<%
	StockFlowForm sff = (StockFlowForm)request.getAttribute("stockFlowForm");
	String createByName = (String)request.getAttribute("createByName");

%>
<body>
<html:form action="stockFlowAction.do?method=flowDetail" method="post" >
<html:hidden property="flowId"/>
<br>
<table width="99%" border="0" align="center" cellpadding="2" cellspacing="1" class="content12">
  <tr>
    <td>���&gt;������ˮ�嵥&gt;������ˮ��ϸ</td>
  </tr>
</table>
<br>
<table width="99%" border="0" align="center" cellpadding="2" cellspacing="1" class="content12">
  <tr> 
    <td height="2" colspan="6" bgcolor="#677789"></td>
  </tr>
  <tr> 
    <td height="6"  colspan="6" bgcolor="#CECECE"></td>
  </tr> 
   
  <tr class="tableback1"> 
    <td witdh="10%">������� ��</td>
    <td witdh="20%"><bean:write property="skuCode" name="stockFlowForm" /></td>
  	<td witdh="10%">���</td>
    <td witdh="20%"><bean:write property="standard" name="stockFlowForm" /></td>
    <td witdh="10%">�Ϻţ�</td>
    <td witdh="20%"><bean:write property="stuffNo" name="stockFlowForm" /></td>
  </tr>
  <tr class="tableback2"> 
    <td>��λ ��</td>
    <td><bean:write property="skuUnit" name="stockFlowForm" /></td>
  	<td>����������</td>
    <td ><bean:write property="skuNum" name="stockFlowForm" /></td>
    <td>ʣ��������</td>
    <td ><bean:write property="restNum" name="stockFlowForm" /> </td>
  </tr>
  
  <tr class="tableback1"> 
    <td>�ɱ�����(RMB)	��</td>
    <td><bean:write property="perCost" name="stockFlowForm" /></td>
  	<td>����($)��</td>
    <td ><bean:write property="orderDollar" name="stockFlowForm" /></td>
    <td>�˷�(RMB)��</td>
    <td ><bean:write property="freightTW" name="stockFlowForm" /></td>
  </tr>
  <tr class="tableback2"> 
    <td>���䷽ʽ��</td>
    <td><%=DicInit.getSystemName("TRANSPORT_MODE",sff.getTransportMode()) %></td>
  	<td>�ͻ���</td>
    <td ><bean:write property="customerName" name="stockFlowForm" /></td>
    <td>�������Ŀ��</td>
    <td ><%=DicInit.getSystemName("FLOW_ITEM",sff.getFlowItem())%> </td>
  </tr>
  
  <tr class="tableback1"> 
  	<td>�����ˣ�</td>
    <td ><%=createByName %></td>
    <td>�������ڣ�</td>
    <td ><bean:write property="createDate" name="stockFlowForm" /></td>
    
  	<td>ҵ�񵥺ţ�</td>
    <td ><bean:write property="formNo" name="stockFlowForm" /></td>
 
  </tr>
  <tr class="tableback2"> 
  	<td>��Ʊ�� ��</td>
    <%if(sff.getInvoiceNo()!=null&&sff.getInvoiceNo().startsWith("TEMP")&&sff.getFlowType().equals("I")){%>
    <td><html:text property="invoiceNo"  styleClass="form" size="16" maxlength="30"/></td>
    <%}else{ %>
    <td ><bean:write property="invoiceNo" name="stockFlowForm" /></td>
    <%} %>
    <td>��ע��</td>
    <td  colspan="3"><bean:write property="remark" name="stockFlowForm" /></td>
  </tr>

 
  <tr> 
    <td height="2" colspan="6" bgcolor="#ffffff"></td>
  </tr>
  <tr> 
    <td height="1" colspan="6" bgcolor="#677789"></td>
  </tr>
  <%if(sff.getInvoiceNo()!=null&&sff.getInvoiceNo().startsWith("TEMP")&&sff.getFlowType().equals("I")){%>
  <tr> 
    <td  colspan="6"><div align="right"> 
        <input type="button" name="save" value="����"  onclick="f_save()">
      </div></td>
  </tr>
  <%} %>
</table>
</body>
</html:form>
</html>

<SCRIPT >
function f_save(){
		document.forms[0].action="stockFlowAction.do?method=updateInvoiceNo"; 
		document.forms[0].submit();
	
}


</SCRIPT>