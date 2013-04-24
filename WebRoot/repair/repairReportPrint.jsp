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
<title>维修报告</title>
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
			<p>地址：上海市静安区镇宁路200号东峰9楼A座</p>
		</div>
	</div>
	<div class="content">
		<h1>高 明 精 机 上 海 办 工 作 报 告</h1>
		<table border="1" cellspacing="1">
			<tr>
				<td colspan="2"><strong>客户名称:<%=cif.getCustomerName() %></strong></td>
				<td><strong>联系人:<%=cif.getLinkman()==null?"":cif.getLinkman() %></strong></td>
				<td><strong>电话:<%=cif.getPhone()==null?"":cif.getPhone() %></strong></td>
			</tr>
			<tr>
				<td colspan="4"><strong>地址:<%=cif.getAddress()==null?"":cif.getAddress() %></strong></td>
			</tr>
			<tr>
				<td width="140"><strong>机型:<%=rsf.getModelCode()==null?"":rsf.getModelCode() %></strong></td>
				<td><strong>机台编码:<%=rsf.getSerialNo()==null?"":rsf.getSerialNo() %></strong></td>
				<td><strong>制造日期</strong></td>
				<td><strong>保固期:<%=DicInit.getSystemName("WARRANTY_TYPE",rsf.getWarrantyType())%></strong></td>
			</tr>
		</table>
		<table border="1" cellspacing="1">
			<tr>
				<td rowspan="4" width="25" align="center"><strong>电诊结果</strong></td>
				<td colspan="3"><%=rsf.getDzResult()==null?"":rsf.getDzResult() %></td>
			</tr>
		</table>
		<%if(!rsf.getRepairProperites().equals("T")&&irisInfo!=null){ %>
		<table border="1" cellspacing="1">
			<tr >
				<td align="center" ><strong>电气部分</strong></td>
				<td align="center"><strong>机械部分</strong></td>
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
				<td rowspan="4" width="25" align="center"><strong>激光检测报告</strong></td>
				<td colspan="3"><%=rsf.getCrReason()==null?"":Operate.tranCRLF(rsf.getCrReason()) %></td>
			</tr>
			
		</table>
		<%} %>
		<table border="1" cellspacing="1">
			<tr>
				<td rowspan="4" width="25" align="center"><strong>遗留问题</strong></td>
				<td colspan="3"><%=rsf.getLeaveProblem()==null?"":rsf.getLeaveProblem() %></td>
			</tr>
		</table>
		<table border="1" cellspacing="1">
			<tr>
				<td rowspan="2" width="25" align="center"><strong>电诊结果</strong></td>
				<td><strong>跟踪</strong></td>
				<td><strong>转下次</strong></td>
			</tr>
			<tr>
				<td style="height:68px;"></td>
				<td><%=rsf.getTobeMatter()==null?"":rsf.getTobeMatter() %></td>
			</tr>
		</table>
		<div class="root"></div>
		<div class="root">
			<span class="fyy-fl">工程师：&nbsp;</span>
			<span class="fyy-fr">总工程师：&nbsp;</span>
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