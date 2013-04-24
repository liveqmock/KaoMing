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
<title>派工单</title>
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
			<p>地址：上海市静安区镇宁路200号东峰9楼A座</p>
		</div>
	</div>
	<div class="content">
		<h1>客 户 维 修 服 务 委 派 记 录 表</h1>
		<table border="1" cellspacing="1">
			<tr>
				<td width="75" style="white-space:nowrap;"><b>客户名称：</b></td>
				<td colspan="4"><%=cif.getCustomerName()==null?"":cif.getCustomerName()%></td>
				<td width="75"><b>电话：</b></td>
				<td><%=cif.getPhone()==null?"":cif.getPhone()%></td>
				<td width="75"><b>联系人：</b></td>
				<td><%=cif.getLinkman()==null?"":cif.getLinkman()%></td>
			</tr>
		
			<tr>
				<td width="75"><b>地址：</b></td>
				<td colspan="4"><%=cif.getAddress()==null?"":cif.getAddress()%></td>
				<td width="75" style="white-space:nowrap;"><b>单位上班时间：</b></td>
				<td><%=cif.getWorkHours()==null?"":cif.getWorkHours()%></td>
				<td width="75" style="white-space:nowrap;"><b>双休：</b></td>
				<td><%=cif.getWeekEnd()==null?"":cif.getWeekEnd()%></td>
			</tr>
			<tr>
				<td width="75"><b>机型：</b></td>
				<td colspan="2"><%=rsf.getModelCode()==null?"":rsf.getModelCode()%></td>
				<td width="75" style="white-space:nowrap;"><b>机床编号：</b></td>
				<td><%=rsf.getSerialNo()==null?"":rsf.getSerialNo()%></td>
				<td width="75" style="white-space:nowrap;"><b>制造日期：</b></td>
				<td></td>
				<td width="75"><b>保固期：</b></td>
				<td><%=DicInit.getSystemName("WARRANTY_TYPE",rsf.getWarrantyType())%></td>
			</tr>
			<%  int row = 1;
				if(repairManSetInfo!=null && repairManSetInfo.size()>0){
					row = repairManSetInfo.size()+1;
				}
			%>
			<tr>
				<td width="75" rowspan="<%=row %>"><b>派修人员</b></td>
				<td width="75" colspan="2" ><b>姓名</b></td>
				<td width="75" colspan="2"><b>电话</b></td>
				<td width="75" colspan="2" style="white-space:nowrap;"><b>派修日期</b></td>
				<td width="75" colspan="2" style="white-space:nowrap;"><b>完成日期</b></td>
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
				<td style="height:72px !important;"><b>报修内容</b></td>
				<td colspan="8"><%=rsf.getCustomerTrouble() %></td>
			</tr>
			<tr>
				<td width="75"><b>电诊：</b></td>
				<td width="75"><b>是：&nbsp;否：&nbsp;</b></td>
				<td width="75">原因：</td>
				<td width="75" colspan="2"><%=rsf.getDzReason()==null?"":rsf.getDzReason()%></td>
				<td width="75" style="white-space:nowrap;"><b>电诊联系人：</b></td>
				<td width="75"><%=rsf.getDzLinkman()==null?"":rsf.getDzLinkman()%></td>
				<td width="75"><b>电话：</b></td>
				<td width="75"><%=rsf.getDzPhone()==null?"":rsf.getDzPhone()%></td>
			</tr>
			<tr>
				<td style="height:72px !important;"><b>处理结果</b></td>
				<td colspan="8"></td>
			</tr>
			<tr>
				<td width="75"><b>工期</b></td>
				<td width="75"><b>预计：</b></td>
				<td width="75"><%=Operate.trimDate(rsf.getEstimateRepairDate()) %></td>
				<td width="75"><b>实际：</b></td>
				<td width="75"><%=Operate.trimDate(rsf.getActualRepairedDate()) %></td>
				<td width="75" style="white-space:nowrap;"><b>备注：</b></td>
				<td width="75" colspan="3"><%=rsf.getDzRemark()==null?"":rsf.getDzRemark()%></td>
			</tr>
		</table>
		<div class="root">备注：维修中超出以上故障状况，酌情处理！</div>
		<%if(!rsf.getRepairProperites().equals("T")){ %>
		<table border="1" cellspacing="1">
			<tr>
				<td rowspan=<%=rowspanPart %> width="25" align="center"><b>销售零件</b></td>
				<td width="25%" align="center"><b>零 件 号</b></td>
				<td align="center"><b>零 件 名 称</b></td>
				<td width="25%" align="center"><b>数量</b></td>
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
				<td rowspan=<%=rowspanLoan %> width="25" align="center"><b>携带零件</b></td>
				<td width="25%" align="center"><b>零 件 号</b></td>
				<td  align="center"><b>零 件 名 称</b></td>
				<td width="25%" align="center"><b>数量</b></td>
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
				<td rowspan=<%=rowspanTool %> width="25" align="center"><b>携带工具</b></td>
				<td width="25%" align="center"><b>零 件 号</b></td>
				<td align="center"><b>零 件 名 称</b></td>
				<td width="25%" align="center"><b>数量</b></td>
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
				<td rowspan="4" width="25" align="center"><strong>其他问题</strong></td>
				<td><%=rsf.getLeaveProblem()==null?"":rsf.getLeaveProblem() %></td>
			</tr>
		</table>
		<div class="root">
			<span class="fyy-fr">客户确认：&nbsp;</span>
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