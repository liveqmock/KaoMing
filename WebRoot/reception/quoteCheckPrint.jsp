<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.util.List"%> 
<%@ page import="java.util.ArrayList"%> 
<%@ page import="com.dne.sie.common.tools.Operate"%> 
<%@ page import="com.dne.sie.reception.form.SaleDetailForm"%> 
<%@ page import="com.dne.sie.reception.form.SaleInfoForm"%> 
<%@ page import="com.dne.sie.repair.form.RepairFeeInfoForm"%> 

<html>
<head>
<title>报价单</title>

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
		<p align="right" style="line-height:3px;">高明精机工业股份有限公司上海营业处&nbsp; </p>
		<p align="right" style="line-height:3px;">上海晨冉机电有限公司&nbsp;</p>
		<p align="right" style="line-height:3px;"><b>E-MAIL：kaoming2005@163.com</b></p>
		<p align="right" style="line-height:3px;">电话:<b>021-62128864</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
		传真:<b>021-62128854</b></p>
		<p align="right" style="line-height:15px;">上海市静安区镇宁路<b> 200 </b>号 东峰<b> 9A </b>座
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
		<p style="line-height:18px;"><b>From:</b>&nbsp; 高明精机工业股份有限公司上海营业处</p>
		<p style="line-height:8px;"><b>Date:</b>&nbsp;&nbsp; <%=Operate.toSqlDate()%></p>
		<p style="line-height:8px;"><b>Page:</b>&nbsp;&nbsp; 1</p>
		<p style="line-height:8px;">主旨:&nbsp;&nbsp; 报价</font></td>
	</tr>
	<tr> 
    <td height="1" colspan="6" bgcolor="#677789"></td>
  </tr>
</table>
 
<p align="center"><b><font size="6">报价单</font></b></p>
<p>报 价 单 号： <%=sif.getSaleNo()%><br>
客 户 名 称： <%=sif.getCustomerName()%><br>
交 货 时 间： <br>
交 货 地 点： <%=sif.getShippingAddress()==null?"":sif.getShippingAddress()%><br>
付 款 方 式： 汇款<br>
有 效 期：60天</p>
<p>备    注：1. 报价总价内容含关税、运费<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2. 17%增值税金额另加<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.公司名称：上海晨冉机电有限公司<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;开户银行：<%=chenranInfo[3] %><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;帐号：<%=chenranInfo[4] %></p>
<table border="1" width="96%" id="table1">
	<%if(salePartsInfoList[1]!=null&&!salePartsInfoList[1].isEmpty()){ %>
	<tr>
		<td  align="center" colspan="8">
		<p align="left"><strong>零件费：</strong></td>
	</tr>
	
	<tr>
		<td width="59" align="center">项次</td>
		<td align="center">零件名称</td>
		<td width="160" align="center">料号</td>
		<td align="center">机型</td>
		<td align="center">机身编码</td>
		<td align="center">单价</td>
		<td align="center">数量</td>
		<td align="center">价格(不含税)</td>
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
		<p align="right">小计：</td>
		<td align="center"><%=Operate.toFix(sif.getTotalQuote(),2)%></td>
	</tr>
	<tr>
		<td  align="center" colspan="7">
		<p align="right">增值税：</td>
		<td align="center"><%=Operate.toFix((sif.getTotalQuote())*0.17,2)%></td>
	</tr>
	<tr>
		<td  align="center" colspan="7">
		<p align="right">零件合计：</td>
		<td align="center"><%=Operate.toFix((sif.getTotalQuote())*1.17,2)%></td>
	</tr>
	<%}%>
	<%if(repairFeeList!=null&&!repairFeeList.isEmpty()){ %>
	<tr>
		<td  align="center" colspan="8">
		<p align="left"><strong>维修费：</strong></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>人数</td>
		<td>工时</td>
		<td>差旅费</td>
		<td>车船票</td>
		<td>人工费</td>
		<td>税金</td>
		<td align="center">总维修费</td>
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
		<p align="right">增值税：</td>
		<td align="center"><%=Operate.toFix(vat17Repair,2)%></td>
	</tr>
	<tr>
		<td  align="center" colspan="7">
		<p align="right">维修合计：</td>
		<td align="center"><%=Operate.toFix(quoteAllRepair,2)%></td>
	</tr>
	<%} %>
	
	<tr>
		<td  align="center" colspan="7">
		<p align="right"><strong>总计：</strong></td>
		<td align="center"><strong><%=Operate.toFix(sif.getTotalQuteWithTax(),2) %></strong></td>
	</tr>
</table>

<p><font size="4">如蒙订购，请签字回传确认。</font></p>
<p align="right"><font size="4">经办人签名： <%=userName%> </font><font size="4">&nbsp; </font>&nbsp;&nbsp;&nbsp;&nbsp;
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


