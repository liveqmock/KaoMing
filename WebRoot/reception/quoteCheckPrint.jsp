<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.util.List"%> 
<%@ page import="java.util.ArrayList"%> 
<%@ page import="com.dne.sie.common.tools.Operate"%> 
<%@ page import="com.dne.sie.reception.form.SaleDetailForm"%> 
<%@ page import="com.dne.sie.reception.form.SaleInfoForm"%> 
<%@ page import="com.dne.sie.repair.form.RepairFeeInfoForm"%> 

<html>
<head>
<title>���۵�</title>

<style type="text/css">
<!--
table
{
	font-size: 12pt;
	
}
-->
</style>
<style media=print>
.Noprint{display:none;}
.PageNext{page-break-after: always;}
</style>
</head>
<%
try{
		String[] kmInfo = (String[])request.getAttribute("kmInfo");
		String[] chenranInfo = (String[])request.getAttribute("chenranInfo");
		
		List[] salePartsInfoList = (List[])request.getAttribute("salePartsInfoList");
		SaleInfoForm sif = null;
		if(salePartsInfoList[0]!=null&&!salePartsInfoList[0].isEmpty()){
			sif=(SaleInfoForm)(salePartsInfoList[0].get(0));
		}
		
		
		List repairFeeList = (List)request.getAttribute("repairFeeList");
		
		String userName = (String) session.getAttribute("userName");
		Double vat17Repair=0D,quoteAllRepair=0D;
		
%>
<body >
<form>
<OBJECT  id=WebBrowser  classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2  height=0  width=0 VIEWASTEXT>
 </OBJECT> 


<table border="0" width="98%" height="162">
	<tr>
		<td height="150" width="182"><b><i><font size="8" color="purple">&nbsp;&nbsp;KAO</font></i></b><p style="line-height:10px;">
		<b><i><font size="8" color="purple">&nbsp;&nbsp;MING</font></i></b></td>
		<td height="150" width="575">
		<p>&nbsp;</p>
		<p align="right" style="line-height:3px;"><b><font size="4">KAO MING MACHINERY INDUSTRIAL CO., LTD.</font></b></p>
		<p align="right" style="line-height:3px;">����������ҵ�ɷ����޹�˾�Ϻ�Ӫҵ��&nbsp; </p>
		<p align="right" style="line-height:3px;">�Ϻ���Ƚ�������޹�˾&nbsp;</p>
		<p align="right" style="line-height:3px;"><b>E-MAIL��kaoming2005@163.com</b></p>
		<p align="right" style="line-height:3px;">�绰:<b>021-62128864</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
		����:<b>021-62128854</b></p>
		<p align="right" style="line-height:15px;">�Ϻ��о���������·<b> 200 </b>�� ����<b> 9A </b>��
		</td>
	</tr>
	 <tr> 
    <td height="2" colspan="6" bgcolor="#677789"></td>
  </tr>
</table>

<table border="0" width="98%" height="117">

	<tr>
		<td  width="388"><font  color="purple">
		<p style="line-height:18px;"><b>&nbsp;To:</b>&nbsp;&nbsp;&nbsp; <%=sif.getCustomerName()%></p>
		<p style="line-height:8px;"><b>&nbsp;Attn:</b>&nbsp;&nbsp;&nbsp;<%=kmInfo[2]%></p>
		<p style="line-height:8px;"><b>&nbsp;Cc:</b>&nbsp;&nbsp;&nbsp; <%=kmInfo[0]%></p>
		<p style="line-height:8px;"><b>&nbsp;Fax:</b>&nbsp;&nbsp; <%=kmInfo[1]%></font></td>
		
		<td height="111" width="460"><font  color="purple">
		<p style="line-height:18px;"><b>From:</b>&nbsp; ����������ҵ�ɷ����޹�˾�Ϻ�Ӫҵ��</p>
		<p style="line-height:8px;"><b>Date:</b>&nbsp;&nbsp; <%=Operate.toSqlDate()%></p>
		<p style="line-height:8px;"><b>Page:</b>&nbsp;&nbsp; 1</p>
		<p style="line-height:8px;">��ּ:&nbsp;&nbsp; ����</font></td>
	</tr>
	<tr> 
    <td height="1" colspan="6" bgcolor="#677789"></td>
  </tr>
</table>
 
<p align="center"><b><font size="6">���۵�</font></b></p>
<p>�� �� �� �ţ� <%=sif.getSaleNo()%><br>
�� �� �� �ƣ� <%=sif.getCustomerName()%><br>
�� �� ʱ �䣺 <br>
�� �� �� �㣺 <%=sif.getShippingAddress()==null?"":sif.getShippingAddress()%><br>
�� �� �� ʽ�� ���<br>
�� Ч �ڣ�60��</p>
<p>��    ע��1. �����ܼ����ݺ���˰���˷�<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2. 17%��ֵ˰������<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.��˾���ƣ��Ϻ���Ƚ�������޹�˾<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�������У�<%=chenranInfo[3] %><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�ʺţ�<%=chenranInfo[4] %></p>
<table border="1" width="96%" id="table1">
	<%if(salePartsInfoList[1]!=null&&!salePartsInfoList[1].isEmpty()){ %>
	<tr>
		<td  align="center" colspan="8">
		<p align="left"><strong>����ѣ�</strong></td>
	</tr>
	
	<tr>
		<td width="59" align="center">���</td>
		<td align="center">�������</td>
		<td width="160" align="center">�Ϻ�</td>
		<td align="center">����</td>
		<td align="center">�������</td>
		<td align="center">����</td>
		<td align="center">����</td>
		<td align="center">�۸�(����˰)</td>
	</tr>
	
	<%for(int i=0;i<salePartsInfoList[1].size();i++){
			SaleDetailForm sdf=(SaleDetailForm)salePartsInfoList[1].get(i);
	%>
	<tr>
		<td align="center"><%=(i+1)%></td>
		<td ><%=sdf.getSkuCode()%></td>
		<td ><%=sdf.getStuffNo()%></td>
		<td ><%=sdf.getModelCode()==null?"":sdf.getModelCode()%></td>
		<td ><%=sdf.getModelSerialNo()==null?"":sdf.getModelSerialNo()%></td>
		<td align="center"><%=Operate.toFix(sdf.getPerQuote(),2)%></td>
		<td align="center"><%=sdf.getPartNum()%></td>
		<td align="center"><%=Operate.toFix(sdf.getPerQuote()*sdf.getPartNum(),2)%></td>
	</tr>
	<%}%>
	
	<tr>
		<td  align="center" colspan="7">
		<p align="right">С�ƣ�</td>
		<td align="center"><%=Operate.toFix(sif.getTotalQuote(),2)%></td>
	</tr>
	<tr>
		<td  align="center" colspan="7">
		<p align="right">��ֵ˰��</td>
		<td align="center"><%=Operate.toFix((sif.getTotalQuote())*0.17,2)%></td>
	</tr>
	<tr>
		<td  align="center" colspan="7">
		<p align="right">����ϼƣ�</td>
		<td align="center"><%=Operate.toFix((sif.getTotalQuote())*1.17,2)%></td>
	</tr>
	<%}%>
	<%if(repairFeeList!=null&&!repairFeeList.isEmpty()){ %>
	<tr>
		<td  align="center" colspan="8">
		<p align="left"><strong>ά�޷ѣ�</strong></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>����</td>
		<td>��ʱ</td>
		<td>���÷�</td>
		<td>����Ʊ</td>
		<td>�˹���</td>
		<td>˰��</td>
		<td align="center">��ά�޷�</td>
	</tr>
	<tr>
	<%
		
		for(int i=0;i<repairFeeList.size();i++){
			RepairFeeInfoForm rfi=(RepairFeeInfoForm)repairFeeList.get(i);
			if(rfi.getFeeType().equals("P")){	//plan
				vat17Repair=rfi.getRepairQuote()==null?0:rfi.getRepairQuote()*0.17;
				quoteAllRepair=rfi.getRepairQuote()==null?0:rfi.getRepairQuote()*1.17;
	%>
		<td>&nbsp;</td>
		<td><%=rfi.getRepairmanNum()%></td>
		<td><%=rfi.getWorkingHours() %></td>
		<td><%=Operate.toFix(rfi.getTravelCosts(),2) %></td>
		<td><%=Operate.toFix(rfi.getTicketsAllCosts(),2) %></td>
		<td><%=Operate.toFix(rfi.getLaborCosts(),2) %></td>
		<td><%=Operate.toFix(rfi.getTaxes(),2) %></td>
		<td align="center"><%=Operate.toFix(rfi.getRepairQuote()==null?0:rfi.getRepairQuote(),2) %></td>
		<%}} %>
	</tr>
	<tr>
		<td  align="center" colspan="7">
		<p align="right">��ֵ˰��</td>
		<td align="center"><%=Operate.toFix(vat17Repair,2)%></td>
	</tr>
	<tr>
		<td  align="center" colspan="7">
		<p align="right">ά�޺ϼƣ�</td>
		<td align="center"><%=Operate.toFix(quoteAllRepair,2)%></td>
	</tr>
	<%} %>
	
	<tr>
		<td  align="center" colspan="7">
		<p align="right"><strong>�ܼƣ�</strong></td>
		<td align="center"><strong><%=Operate.toFix(sif.getTotalQuteWithTax(),2) %></strong></td>
	</tr>
</table>

<p><font size="4">���ɶ�������ǩ�ֻش�ȷ�ϡ�</font></p>
<p align="right"><font size="4">������ǩ���� <%=userName%> </font><font size="4">&nbsp; </font>&nbsp;&nbsp;&nbsp;&nbsp;
</p>
 

    <table>
    

  
</table>
  
</form>
</body>
<%
}catch(Exception e){
	e.printStackTrace();
}%>
</html>


