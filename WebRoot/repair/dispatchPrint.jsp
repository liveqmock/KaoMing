<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.dne.sie.repair.form.RepairServiceForm"%>
<%@ page import="java.util.*"%>
<%@ page import="com.dne.sie.maintenance.form.CustomerInfoForm"%>
<%@ page import="com.dne.sie.repair.form.*"%>
<%@ page import="com.dne.sie.common.tools.Operate"%>
<%@ page import="com.dne.sie.common.tools.DicInit"%>
<%
try {
	RepairServiceForm rsf = (RepairServiceForm) request.getAttribute("repair");
	List partsList = (List)request.getAttribute("partsList");
	List loanList = (List)request.getAttribute("loanList");
	List toolsList = (List)request.getAttribute("toolsList");
	
	int rowspanPart=partsList==null?6:partsList.size()+1;
	int rowspanLoan=loanList==null?6:loanList.size()+1;
	int rowspanTool=toolsList==null?6:toolsList.size()+1;
	
	
	CustomerInfoForm cif = rsf.getCustomInfoForm();
	Set<RepairManInfoForm> repairManSetInfo = rsf.getRepairManSetInfo();
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>�ɹ���</title>
<link href="<%= request.getContextPath()%>/repair/css/reset.css" rel="stylesheet" />
<link href="<%= request.getContextPath()%>/repair/css/css.css" rel="stylesheet" />
</head>

<body>
<div class="main" >
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
		<h1>�� �� ά �� �� �� ί �� �� ¼ ��</h1>
		<table border="1" cellspacing="1">
			<tr>
				<td width="75" style="white-space:nowrap;"><b>�ͻ����ƣ�</b></td>
				<td colspan="4"><%=cif.getCustomerName()==null?"":cif.getCustomerName()%></td>
				<td width="75"><b>�绰��</b></td>
				<td><%=cif.getPhone()==null?"":cif.getPhone()%></td>
				<td width="75"><b>��ϵ�ˣ�</b></td>
				<td><%=cif.getLinkman()==null?"":cif.getLinkman()%></td>
			</tr>
		
			<tr>
				<td width="75"><b>��ַ��</b></td>
				<td colspan="4"><%=cif.getAddress()==null?"":cif.getAddress()%></td>
				<td width="75" style="white-space:nowrap;"><b>��λ�ϰ�ʱ�䣺</b></td>
				<td><%=cif.getWorkHours()==null?"":cif.getWorkHours()%></td>
				<td width="75" style="white-space:nowrap;"><b>˫�ݣ�</b></td>
				<td><%=cif.getWeekEnd()==null?"":cif.getWeekEnd()%></td>
			</tr>
			<tr>
				<td width="75"><b>���ͣ�</b></td>
				<td colspan="2"><%=rsf.getModelCode()==null?"":rsf.getModelCode()%></td>
				<td width="75" style="white-space:nowrap;"><b>������ţ�</b></td>
				<td><%=rsf.getSerialNo()==null?"":rsf.getSerialNo()%></td>
				<td width="75" style="white-space:nowrap;"><b>�������ڣ�</b></td>
				<td></td>
				<td width="75"><b>�����ڣ�</b></td>
				<td><%=DicInit.getSystemName("WARRANTY_TYPE",rsf.getWarrantyType())%></td>
			</tr>
			<%  int row = 1;
				if(repairManSetInfo!=null && repairManSetInfo.size()>0){
					row = repairManSetInfo.size()+1;
				}
			%>
			<tr>
				<td width="75" rowspan="<%=row %>"><b>������Ա</b></td>
				<td width="75" colspan="2" ><b>����</b></td>
				<td width="75" colspan="2"><b>�绰</b></td>
				<td width="75" colspan="2" style="white-space:nowrap;"><b>��������</b></td>
				<td width="75" colspan="2" style="white-space:nowrap;"><b>�������</b></td>
			</tr>
			<%
				if(repairManSetInfo!=null && repairManSetInfo.size()>0){
					String strTr="";
					int i=0;
					Iterator rmIterator = repairManSetInfo.iterator();
					while(rmIterator.hasNext()){
			 	  		RepairManInfoForm rm = (RepairManInfoForm)rmIterator.next();
						strTr=i%2==0?"tableback1":"tableback2";
						i++;
			%>
			
			<tr>
				<td width="75" colspan="2"><%=rm.getRepairManName()==null?"":rm.getRepairManName()%></td>
				<td width="75" colspan="2"><%=rm.getRepairManPhone()==null?"":rm.getRepairManPhone()%></td>
				<td width="75" colspan="2"><%=Operate.trimDate(rm.getDepartDate())%></td>
				<td width="75" colspan="2"><%=Operate.trimDate(rm.getReturnDate())%></td>
			</tr>
			 <%} }%>
			<tr>
				<td style="height:72px !important;"><b>��������</b></td>
				<td colspan="8"><%=rsf.getCustomerTrouble() %></td>
			</tr>
			<tr>
				<td width="75"><b>���</b></td>
				<td width="75"><b>�ǣ�&nbsp;��&nbsp;</b></td>
				<td width="75">ԭ��</td>
				<td width="75" colspan="2"><%=rsf.getDzReason()==null?"":rsf.getDzReason()%></td>
				<td width="75" style="white-space:nowrap;"><b>������ϵ�ˣ�</b></td>
				<td width="75"><%=rsf.getDzLinkman()==null?"":rsf.getDzLinkman()%></td>
				<td width="75"><b>�绰��</b></td>
				<td width="75"><%=rsf.getDzPhone()==null?"":rsf.getDzPhone()%></td>
			</tr>
			<tr>
				<td style="height:72px !important;"><b>������</b></td>
				<td colspan="8"></td>
			</tr>
			<tr>
				<td width="75"><b>����</b></td>
				<td width="75"><b>Ԥ�ƣ�</b></td>
				<td width="75"><%=Operate.trimDate(rsf.getEstimateRepairDate()) %></td>
				<td width="75"><b>ʵ�ʣ�</b></td>
				<td width="75"><%=Operate.trimDate(rsf.getActualRepairedDate()) %></td>
				<td width="75" style="white-space:nowrap;"><b>��ע��</b></td>
				<td width="75" colspan="3"><%=rsf.getDzRemark()==null?"":rsf.getDzRemark()%></td>
			</tr>
		</table>
		<div class="root">��ע��ά���г������Ϲ���״�������鴦��</div>
		<%if(!rsf.getRepairProperites().equals("T")){ %>
		<table border="1" cellspacing="1">
			<tr>
				<td rowspan=<%=rowspanPart %> width="25" align="center"><b>�������</b></td>
				<td width="25%" align="center"><b>�� �� ��</b></td>
				<td align="center"><b>�� �� �� ��</b></td>
				<td width="25%" align="center"><b>����</b></td>
			</tr>
		<%
		 if(partsList!=null && partsList.size()>0){
		 	for(int i=0;i<partsList.size();i++){
		 		RepairPartForm part = (RepairPartForm)partsList.get(i);
	  	%>
			<tr>
				<td><%=part.getStuffNo() %></td>
				<td><%=part.getSkuCode() %></td>
				<td><%=part.getApplyQty() %></td>
			</tr>
			<%}}else{ %>
			<tr>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			 <%} %>
		</table>
		
		<div class="root"></div>

		<table border="1" cellspacing="1">
			<tr>
				<td rowspan=<%=rowspanLoan %> width="25" align="center"><b>Я�����</b></td>
				<td width="25%" align="center"><b>�� �� ��</b></td>
				<td  align="center"><b>�� �� �� ��</b></td>
				<td width="25%" align="center"><b>����</b></td>
			</tr>
		<%
		 if(loanList!=null && loanList.size()>0){
		 	for(int i=0;i<loanList.size();i++){
		 		RepairPartForm part = (RepairPartForm)loanList.get(i);
	  	%>
			<tr>
				<td><%=part.getStuffNo() %></td>
				<td><%=part.getSkuCode() %></td>
				<td><%=part.getApplyQty() %></td>
			</tr>
			<%}}else{ %>
			<tr>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			 <%} %>
		</table>
		<%} %>
		<div class="root"></div>
		<table border="1" cellspacing="1">
			<tr>
				<td rowspan=<%=rowspanTool %> width="25" align="center"><b>Я������</b></td>
				<td width="25%" align="center"><b>�� �� ��</b></td>
				<td align="center"><b>�� �� �� ��</b></td>
				<td width="25%" align="center"><b>����</b></td>
			</tr>
		<%
		 if(toolsList!=null && toolsList.size()>0){
		 	for(int i=0;i<toolsList.size();i++){
		 		RepairPartForm part = (RepairPartForm)toolsList.get(i);
	  	%>
			<tr>
				<td><%=part.getStuffNo() %></td>
				<td><%=part.getSkuCode() %></td>
				<td><%=part.getApplyQty() %></td>
			</tr>
			</tr>
			<%}}else{ %>
			<tr>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			 <%} %>
		</table>
		<div class="root"></div>
		<table border="1">
			<tr>
				<td rowspan="4" width="25" align="center"><strong>��������</strong></td>
				<td><%=rsf.getLeaveProblem()==null?"":rsf.getLeaveProblem() %></td>
			</tr>
		</table>
		<div class="root">
			<span class="fyy-fr">�ͻ�ȷ�ϣ�&nbsp;</span>
		</div>
	</div>
	<div class="footer">
		<div class="copyright">KAO MING SHANG HAI</div>
	</div>
</div>
</body>
</html>
<%} catch (Exception e) {
	e.printStackTrace();
}%>