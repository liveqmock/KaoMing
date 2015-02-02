<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="com.dne.sie.repair.form.RepairServiceForm"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.dne.sie.maintenance.form.CustomerInfoForm"%>
<%@ page import="com.dne.sie.common.tools.Operate"%>
<%@ page import="com.dne.sie.common.tools.ConvertMoney"%>
<%
try {
	RepairServiceForm rsf = (RepairServiceForm) request.getAttribute("repair");
	ArrayList partsList = (ArrayList) request.getAttribute("partsList");
	CustomerInfoForm cif = rsf.getCustomInfoForm();
    CustomerInfoForm shb2 = (CustomerInfoForm) request.getAttribute("shb2");
    Double quote = (Double) request.getAttribute("quote");
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>电诊单</title>
<link href="<%= request.getContextPath()%>/repair/css/css.css" rel="stylesheet" />
</head>

<body>

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
            <p align="right" style="line-height:15px;">地址：上海市静安区镇宁路<b> 200 </b>号 东峰<b> 9 </b>楼<b>A</b>座
        </td>
    </tr>
    <tr>
        <td height="2" colspan="6" bgcolor="#677789" style='width:37.4pt;border:none;border-bottom:solid windowtext 1.0pt;
  mso-border-bottom-alt:solid windowtext .5pt;padding:0cm 1.4pt 0cm 1.4pt'></td>
    </tr>
</table>

<div class="main" >

	<div class="content">
		<h1>维 修 服 务 报 价 单</h1>
		<table border="1" cellspacing="1">
			<tr>
				<td width="65"><b>客户</b></td>
				<td colspan="5"><%=cif.getCustomerName()%></td>
				<td width="65"><b>机型</b></td>
				<td width="65"><%=rsf.getModelCode()%></td>
			</tr>
			<tr>
				<td width="65"><b>联系人</b></td>
				<td width="80"><%=rsf.getLinkman() == null ? "&nbsp;" : rsf.getLinkman()%></td>
				<td width="65"><b>电话</b></td>
				<td>&nbsp;<%=rsf.getPhone() == null ? "&nbsp;" : rsf.getPhone()%></td>
				<td width="65"><b>传真</b></td>
				<td><%=rsf.getFax() == null ? "&nbsp;" : rsf.getFax()%></td>
				<td width="65"><b>机身编号</b></td>
				<td><%=rsf.getSerialNo() == null ? "&nbsp;" : rsf.getSerialNo()%></td>
			</tr>
			<tr>
				<td width="65" rowspan="2"><b>报价</b></td>
				<td colspan="7"><%=shb2.getRemark()==null?"":shb2.getRemark()%></td>
			</tr>
			<tr>
				<td colspan="7"><%=shb2.getCustomerName()==null?"":shb2.getCustomerName()%></td>
			</tr>
			<tr>
				<td width="65"><b>联系人</b></td>
				<td width="80"><%=shb2.getLinkman()==null?"":shb2.getLinkman()%></td>
				<td width="65"><b>电话</b></td>
				<td><%=shb2.getPhone()==null?"":shb2.getPhone()%></td>
				<td width="65"><b>传真</b></td>
				<td><%=shb2.getFax()==null?"":shb2.getFax()%></td>
				<td width="65"><b>报价单号</b></td>
				<td><%=rsf.getServiceSheetNo()%></td>
			</tr>
		</table>
		<div class="root">
			<span class="fyy-fl">报修内容：</span>
			<span class="fyy-fr">报修时间：<%=Operate.formatYMDDate(new java.util.Date())%></span>
		</div>
		<table border="1" cellspacing="1">
			<tr><td><%=rsf.getCustomerTrouble() == null ? "" : rsf.getCustomerTrouble()%></td></tr>
			<tr><td></td></tr>
		</table>
        <!--
		<div class="root">
			<span class="fyy-fl">维修内容：</span>
		</div>
		<table border="1" cellspacing="1">
			<tr height="100" class="height1"><td style="height:68px;"><%=rsf.getRepairContent() == null ? "&nbsp;" : rsf.getRepairContent()%></td></tr>
		</table>
        -->
		<div class="root">
			<span class="fyy-fl">更换零件（若现场更换零件另计费）</span>
		</div>
		<table border="1" cellspacing="1">
			<tr>
				<td width="210"><b>零件名称</b></td>
				<td width="160"><b>数量</b></td>
				<td width="140"><b>单价</b></td>
                <td width="140"><b>总价</b></td>
			</tr>
			 <%
                 double totleQuote = 0;
				 if(partsList!=null){
				 	for(int i=0;i<partsList.size();i++){
				 		String[] obj = (String[])partsList.get(i);
                        double tmp = Integer.parseInt(obj[5]) * Double.parseDouble(obj[6]);
                        totleQuote += tmp;
			  %>
			    <tr><td><%=obj[4] %></td><td><%=obj[5] %></td><td><%=obj[6] %></td><td><%=tmp %></td></tr>
			<%}} %>
		</table>
		<div class="root"></div>
		<table border="1" cellspacing="1">
			<tr>
				<td width="210"><b>维修费</b></td>
				<td width=""><%="￥"+quote %>元</td>
			</tr>
			<tr>
				<td width="210"><b>零件金额</b></td>
				<td><%=totleQuote%>元</td>
			</tr>
			<tr>
				<td width="210"><b>17%增值税</b></td>
				<td><%=Operate.toFix((quote+totleQuote) * 0.17,2)%>元</td>
			</tr>
			<tr>
				<td width="210"><b>总计金额（小写）</b></td>
				<td><%=Operate.toFix((quote+totleQuote) * 1.17,2)%>元</td>
			</tr>
			<tr>
				<td width="210"><b>总计金额（大写）</b></td>
				<td>人民币：<%=(quote+totleQuote)==0?"":ConvertMoney.convertMoneyFormat((quote+totleQuote) * 1.17) %></td>
			</tr>
		</table>
		<div class="root"></div>
		<table border="1" cellspacing="1">
			<tr>
				<td width="210"><b>付款方式</b></td>
				<td colspan="2"><%=rsf.getPaymentContent()==null?"":rsf.getPaymentContent()+"," %>预收100%款项。款到派人。</td>
			</tr>
			<tr>
				<td width="210" rowspan="4"><b>银行资料</b></td>
				<td>单位名称</td>
				<td><%=shb2.getCustomerName()==null?"":shb2.getCustomerName()%></td>
			</tr>
			<tr>
				<td>银行账号</td>
				<td><%=shb2.getBankAccount()==null?"":shb2.getBankAccount()%></td>
			</tr>
			<tr>
				<td>开户行：</td>
				<td><%=shb2.getBank()==null?"":shb2.getBank()%></td>
			</tr>
		</table>
		<div class="root"></div>
		<div class="root">请贵公司确认并于此处签字：</div>
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