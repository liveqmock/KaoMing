<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.util.ArrayList"%> 
<%@ page import="com.dne.sie.common.tools.Operate"%> 
<%@ page import="com.dne.sie.reception.form.PoForm"%> 
<html>
<head>
<title>订购单</title>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">

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
		ArrayList poFormPrintList = (ArrayList)request.getAttribute("poFormPrintList");
		PoForm pf=(PoForm)poFormPrintList.get(0);
		
		String userName = (String) session.getAttribute("userName");
		
%>
<body >
<form>
<OBJECT  id=WebBrowser  classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2  height=0  width=0 VIEWASTEXT> </OBJECT> 

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
		<p align="right" style="line-height:15px;">上海市静安区镇宁路号<b> 200 </b>号 东峰<b> 9A </b>座
		</td>
	</tr>
	 <tr> 
    <td height="2" colspan="6" bgcolor="#677789"></td>
  </tr>
</table>

<table border="0" width="98%" height="117">
	<tr>
		<td height="111" width="388"><font  color="purple">
		<p style="line-height:18px;"><b>&nbsp;To:</b>&nbsp;&nbsp;&nbsp; 高明精机工业股份有限公司</p>
		<p style="line-height:8px;"><b>&nbsp;Attn:</b>&nbsp;&nbsp;&nbsp;<%=kmInfo[2]%></p>
		<p style="line-height:8px;"><b>&nbsp;Cc:</b>&nbsp;&nbsp;&nbsp; <%=kmInfo[0]%></p>
		<p style="line-height:8px;"><b>&nbsp;Fax:</b>&nbsp;&nbsp; <%=kmInfo[1]%></font></td>
		
		<td height="111" width="460"><font  color="purple">
		<p style="line-height:18px;"><b>From:</b>&nbsp; 上海晨冉机电有限公司</p>
		<p style="line-height:8px;"><b>Date:</b>&nbsp;&nbsp; <%=Operate.toSqlDate()%></p>
		<p style="line-height:8px;"><b>Page:</b>&nbsp;&nbsp; 1</p>
		<p style="line-height:8px;">主旨:&nbsp;&nbsp; 订购单</font></td>
	</tr>
	<tr> 
    <td height="1" colspan="6" bgcolor="#677789"></td>
  </tr>
</table>
 
<p align="center"><b><font size="6">订购单</font></b></p>
<p>订 购 单 号： <%=pf.getOrderNo()%></p>
<p>客 户 名 称： <%=pf.getCustomerName()%></p>
<p>交 货 时 间： <%=pf.getDeliveryTime()==null?"":pf.getDeliveryTime()%></p></p>
<p>交 货 地 点： <%=pf.getShippingAddress()==null?"上海办":pf.getShippingAddress()%></p>
<table border="1" width="96%" id="table1">
	<tr>
		<td width="59" align="center">项次</td>
		<td align="center">零件名称</td>
		<td width="160" align="center">料号</td>
		<td >机型</td>
		<td >机身编码</td>
		<td align="center">数量</td>
		<td align="center">价格$（单价）</td>
	</tr>
	
	<%for(int i=0;i<poFormPrintList.size();i++){
			PoForm pof=(PoForm)poFormPrintList.get(i);
	%>
	<tr>
		<td align="center"><%=(i+1)%></td>
		<td align="left"><%=pof.getSkuCode()%></td>
		<td align="left"><%=pof.getStuffNo()%></td>
		<td align="left"><%=pof.getModelCode()%></td>
		<td align="left"><%=pof.getModelSerialNo()%></td>
		<td align="center"><%=pof.getOrderNum()%></td>
		<td align="center"><%=Operate.toFix(pof.getPerQuote(),2)%></td>
	</tr>
	<%}%>
	
	
</table>
<p>　</p>
<p>　</p>
<p align="right"><font size="5">上海办： <%=userName%> </font><font size="4">&nbsp; </font>&nbsp;&nbsp;&nbsp;&nbsp;
</p>
 


  
  
    <table>
    
  <tr align="center">
      <td >
  	<input type="button" onclick="f_print1()" value='页面设置' class="NOPRINT">
  	<input type="button" onclick="f_print2()" value='打印预览' class="NOPRINT">
  	<input type="button" onclick="f_print3()" value='直接打印'  class="NOPRINT">
      </td>
  </tr>
  
</table>
  
</form>
</body>
<%
}catch(Exception e){
	e.printStackTrace();
}%>
</html>


<SCRIPT language=JAVASCRIPT1.2>

function f_print3(){
    document.all.WebBrowser.ExecWB(6,1);  
       
}


function f_print1(){
   
   document.all.WebBrowser.ExecWB(8,1);  
       
}

function f_print2(){
  
  document.all.WebBrowser.ExecWB(7,1);
       
}

function PageSetup_Null() { //清空页眉和页脚
  var HKEY_Root,HKEY_Path,HKEY_Key; 
  HKEY_Root="HKEY_CURRENT_USER"; 
  HKEY_Path="\\Software\\Microsoft\\Internet Explorer\\PageSetup\\"; 
  
  try{
  	var Wsh=new ActiveXObject("WScript.Shell"); 
  	HKEY_Key="header"; 
  	Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,"");
  	HKEY_Key="footer"; 
  	Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,""); 
  }catch(e){} 
} 


</SCRIPT>
