<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.dne.sie.repair.form.RepairServiceForm"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.dne.sie.maintenance.form.CustomerInfoForm"%>
<%@ page import="com.dne.sie.common.tools.Operate"%>
<%@ page import="com.dne.sie.common.tools.DicInit"%>
<%
try {
	RepairServiceForm rsf = (RepairServiceForm) request.getAttribute("repair");
	String[] irisInfo = (String[]) request.getAttribute("irisInfo");
	
	
	CustomerInfoForm cif = rsf.getCustomInfoForm();
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>ά�ޱ���</title>
<link href="<%= request.getContextPath()%>/repair/css/reset.css" rel="stylesheet" />
<link href="<%= request.getContextPath()%>/repair/css/css.css" rel="stylesheet" />
</head>

<body>
<div class="main">
	<div class="head">
		<div class="logo"><img src="<%= request.getContextPath()%>/repair/images/logo.jpg" width="189" height="93" /></div>
		<div class="about">
			<p>KAO MING MACHINERY INDUSRTIAL CO.,LTD</p>
			<p>&nbsp;</p>
			<p>E-MAIL:  <a href="#">KAOMING2005@163.COM</a></p>
			<p>TELL:  021-62128864   FAX:021-62128854</p>
			<p>��ַ���Ϻ��о���������·200�Ŷ���9¥A��</p>
		</div>
	</div>
	<div class="content">
		<h1>�� �� �� �� �� �� �� �� �� �� ��</h1>
		<table border="1" cellspacing="1">
			<tr>
				<td colspan="2"><strong>�ͻ�����:<%=cif.getCustomerName() %></strong></td>
				<td><strong>��ϵ��:<%=cif.getLinkman()==null?"":cif.getLinkman() %></strong></td>
				<td><strong>�绰:<%=cif.getPhone()==null?"":cif.getPhone() %></strong></td>
			</tr>
			<tr>
				<td colspan="4"><strong>��ַ:<%=cif.getAddress()==null?"":cif.getAddress() %></strong></td>
			</tr>
			<tr>
				<td width="140"><strong>����:<%=rsf.getModelCode()==null?"":rsf.getModelCode() %></strong></td>
				<td><strong>��̨����:<%=rsf.getSerialNo()==null?"":rsf.getSerialNo() %></strong></td>
				<td><strong>��������</strong></td>
				<td><strong>������:<%=DicInit.getSystemName("WARRANTY_TYPE",rsf.getWarrantyType())%></strong></td>
			</tr>
		</table>
		<table border="1" cellspacing="1">
			<tr>
				<td rowspan="4" width="25" align="center"><strong>������</strong></td>
				<td colspan="3"><%=rsf.getDzResult()==null?"":rsf.getDzResult() %></td>
			</tr>
		</table>
		<%if(!rsf.getRepairProperites().equals("T")&&irisInfo!=null){ %>
		<table border="1" cellspacing="1">
			<tr >
				<td align="center" ><strong>��������</strong></td>
				<td align="center"><strong>��е����</strong></td>
			</tr>
			<tr class="height1">
				<td style="height:370px;"><%=irisInfo[0]%></td>
				<td><%=irisInfo[1]%></td>
			</tr>
		</table>
		<%}else if(rsf.getWarrantyType().equals("A")){ %>
		
		<%}else if(rsf.getWarrantyType().equals("B")){ %>
		
		<%}else if(rsf.getWarrantyType().equals("C")){ %>
		<table border="1" cellspacing="1">
			<tr>
				<td rowspan="4" width="25" align="center"><strong>�����ⱨ��</strong></td>
				<td colspan="3"><%=rsf.getCrReason()==null?"":Operate.tranCRLF(rsf.getCrReason()) %></td>
			</tr>
			
		</table>
		<%} %>
		<table border="1" cellspacing="1">
			<tr>
				<td rowspan="4" width="25" align="center"><strong>��������</strong></td>
				<td colspan="3"><%=rsf.getLeaveProblem()==null?"":rsf.getLeaveProblem() %></td>
			</tr>
		</table>
		<table border="1" cellspacing="1">
			<tr>
				<td rowspan="2" width="25" align="center"><strong>������</strong></td>
				<td><strong>����</strong></td>
				<td><strong>ת�´�</strong></td>
			</tr>
			<tr>
				<td style="height:68px;"></td>
				<td><%=rsf.getTobeMatter()==null?"":rsf.getTobeMatter() %></td>
			</tr>
		</table>
		<div class="root"></div>
		<div class="root">
			<span class="fyy-fl">����ʦ��&nbsp;</span>
			<span class="fyy-fr">�ܹ���ʦ��&nbsp;</span>
		</div>
	</div>
	<div class="footer">
		<div class="copyright">KAO MING SHANG HAI</div>
	</div>
</div>
</body>
</html>

<%}catch(Exception e){
	e.printStackTrace();
}%> 