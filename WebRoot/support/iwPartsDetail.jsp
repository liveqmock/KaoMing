<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri=  "/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri=  "/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ page import="java.util.ArrayList"%> 
<%@ page import="com.dne.sie.reception.form.SaleInfoForm"%>
<%@ page import="com.dne.sie.common.tools.DicInit"%>

<HTML>
<HEAD>
<title>ѯ�۵���ϸ</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/styles.css" rel="stylesheet" type="text/css" />
<script language=javascript src="js/inputValidation.js"></script>
</head>
<%
		ArrayList vtrData = (ArrayList)request.getAttribute("detailList");
		SaleInfoForm sif = (SaleInfoForm)request.getAttribute("salesInfoForm");
    
%>

<body  bgcolor="#ffffff" leftmargin="0" topmargin="0" rightmargin="0" bottommargin="0" marginwidth="0" marginheight="0">

<html:form method="post" action="salePartsApproveAction.do?method=iwPartsDetail">
<html:hidden property="saleNo"/>
<html:hidden property="saleStatus"/>
<table width="100%" border="0" align="center" cellpadding="2" cellspacing="1" class="content12">
  <tr>
    <td>��������&gt;�����������</td>
  </tr>
</table>
<br>
<table width="100%" border="0" align="center" cellpadding="2" cellspacing="1" class="content12">
  <tr> 
    <td height="2" colspan="9" bgcolor="#677789"></td>
  </tr>
  <tr> 
    <td height="6"  colspan="9" bgcolor="#CECECE"></td>
  </tr>
  <tr class="tableback2"> 
    <td width="7%"> ѯ�۵��ţ�</td>
    <td width="10%"><bean:write property="saleNo" name="salesInfoForm" /></td>
    <td width="7%"> �ͻ����ƣ�</td>
    <td width="10%"><bean:write property="customerName" name="salesInfoForm" /></td>
    <td width="7%"> �ͻ��绰��</td>
    <td width="10%"><bean:write property="customerPhone" name="salesInfoForm" /></td>
   </tr>
   <tr class="tableback1"> 
    <td width="7%">������ַ��</td>
    <td width="15%"><bean:write property="shippingAddress" name="salesInfoForm" /></td>
    <td width="7%"> ά�޵��ţ�</td>
    <td width="15%"><bean:write property="serviceSheetNo" name="salesInfoForm" />
    	<%if("I".equals(sif.getWarrantyType())){ %> <font color="red">(����)</font><%} %>
    </td>
    <td width="7%"> ��Ԫ���ʣ�</td>
    <td width="15%"><bean:write property='exchangeRate' name='salesInfoForm' />  </td>
  </tr>
  <tr class="tableback2"> 
     <td width="7%"> ��ע��</td>
    <td colspan="5"><bean:write property="remark" name="salesInfoForm" /></td>
   </tr>
  <tr class="tableback2"> 
  </tr>
  
  
  <tr> 
    <td height="2" colspan="9" bgcolor="#ffffff"></td>
  </tr>
  <tr> 
    <td height="1" colspan="9" bgcolor="#677789"></td>
  </tr>
</table>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td align="left">
    
    	<input type="button"  value="�ͻ���ʷ����" onClick="javascript:cust_price('<bean:write property="customerId" name="salesInfoForm" />')">
    
    </td>
  </tr>
</table>
<br>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="content12">
  <tr bgcolor="#CCCCCC"> 
    <td><strong>����</strong></td>
    <td><strong>�������</strong></td>
    <td><strong>�������</strong></td>
    <td><strong>�Ϻ�</strong></td>
    <td><strong>����</strong></td>
    <td  align="center" ><strong>��ʷ��</strong></td>
    <td  align="center" ><strong>����</strong></td>
    <td><strong>̨�屨��$(��)</strong></td>
    <td><strong>������ʼʱ��</strong></td>
    <td><strong>��������ʱ��</strong></td>
    <td><strong>��������</strong></td>
    <td><strong>��������</strong></td>
  </tr>
  
     <%if(vtrData!=null){
       	    String strTr="";
      	    for(int i=0;i<vtrData.size();i++){
      	        if(i%2==0) strTr="tableback2";
      	        else strTr="tableback1";
      		String[] temp=(String[])vtrData.get(i);
      		
      %>
      
      <tr class="<%=strTr%>"> 
          <input name="saleDetailId" type="hidden" value="<%=temp[0]%>">
					<td ><%=temp[1]==null?"":temp[1]%></td>
          <td ><%=temp[2]==null?"":temp[2]%></td>
          <td ><%=temp[3]==null?"":temp[3]%></td>
          <td ><%=temp[4]==null?"":temp[4]%></td>
          <td ><%=temp[5]==null?"":temp[5]%></td>
          <td align="center"><a href="javascript:history_price('<%=temp[4]%>')" style="cursor: hand"><img src="googleImg/icon/search.gif" border=0 title="��ʷ����" ></a></td>
          <td align="center"><a href="javascript:up_affix(<%=temp[0]%>)" style="cursor: hand"><img src="googleImg/icon/writely.gif" border=0 title="����" ></a></td>
        	<td ><%=temp[27]==null?"":temp[27]%></td>
        	<td ><%=temp[28]==null?"":temp[28]%></td>
        	<td ><%=temp[29]==null?"":temp[29]%></td>
			<td ><%=DicInit.getSystemName("ORDER_TYPE",temp[9])%></td>
			<td ><%=DicInit.getSystemName("WARRANTY_TYPE",temp[26])%></td>
        </tr>
      
      <%}}%> 
      
  <tr> 
    <td height="2" colspan="16" bgcolor="#ffffff"></td>
  </tr>
  <tr> 
    <td height="2" colspan="16"  bgcolor="#677789"></td>
  </tr>
</table>

<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td align="left">
    	<input type="button"  value="ͬ��" onClick="javascript:f_confirm('D')">
    	<input type="button"  value="�ܾ�" onClick="javascript:f_confirm('J')">
    </td>
  </tr>
  <tr>
    <td align="left">
    	������ע��<textarea name="remark" cols="8" rows="2" class="form" style="width:80%"></textarea>
    </td>
  </tr>
</table>

</html:form>

</body>
</html>
<SCRIPT>
	
	
	function f_confirm(flag){
	    if(flag=='N'&&document.forms[0].remark.value==''){
	    	alert("����д�ܾ�ԭ��");
	    }else{
	    	document.forms[0].saleStatus.value=flag;
	    	document.forms[0].action="salePartsApproveAction.do?method=iwPartApprove";
			document.forms[0].submit();
	    }
		
	}
	
	function history_price(stuffNo){
    	window.open("saleInfoAction.do?method=historyPrice&stuffNo="+stuffNo,"","width=700,height=300,left=50,top=10,menubar=no,toolbar=no,resizable=yes,scrolling=yes,scrollbars=yes");
	}
	
	function cust_price(customerId){
    	window.open("saleInfoAction.do?method=custPrice&customerId="+customerId,"","width=700,height=300,left=50,top=10,menubar=no,toolbar=no,resizable=yes,scrolling=yes,scrollbars=yes");
	}
	
	function up_affix(detailId){
			window.showModalDialog("attachedInfoAction.do?method=affixList&saleDetailId="+detailId+"&Rnd="+Math.random(),"","dialogHeight: 220px; dialogWidth: 620px; edge: Sunken; center: Yes; help: No; resizable: No; status: Yes;");
	
	}


	
</SCRIPT>