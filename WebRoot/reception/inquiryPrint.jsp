<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.util.ArrayList"%> 
<%@ page import="com.dne.sie.common.tools.Operate"%> 
<%@ page import="com.dne.sie.reception.form.SaleDetailForm"%> 
<%@ page import="com.dne.sie.reception.form.SaleInfoForm"%> 
<html>
<head>
<title>询价单</title>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">

<style type="text/css">
    <!--
    table
    {
        font-size: 12pt;
        font-family:'方正姚体';
    }

    @font-face
    {
        font-family:'方正姚体';
        mso-font-alt:'方正姚体';
        text-align:right;text-indent:249.95pt;
        mso-char-indent-count:22.0
    }

    .border1 {
        border:solid windowtext 1.0pt;
        mso-border-alt:solid windowtext .5pt;
        padding:0cm 1.4pt 0cm 1.4pt;
    }
    .borderrow1{
        border:solid windowtext 1.0pt;
        border-left:none;
        mso-border-left-alt:solid windowtext .5pt;
        mso-border-alt: solid windowtext .5pt;
        padding:0cm 1.4pt 0cm 1.4pt;
    }
    .bordercol1{
        border:solid windowtext 1.0pt;
        border-top:none;
        mso-border-top-alt:solid windowtext .5pt;
        mso-border-alt:solid windowtext .5pt;
        padding:0cm 1.4pt 0cm 1.4pt;
    }
    .border2{
        border-top:none;
        /*border-left: none;*/
        border-bottom:solid windowtext 1.0pt;
        border-right:solid windowtext 1.0pt;
        mso-border-top-alt:solid windowtext .5pt;
        /*mso-border-left-alt:solid windowtext .5pt;*/
        mso-border-alt:solid windowtext .5pt;
        padding:0cm 1.4pt 0cm 1.4pt;
    }
    -->
</style>

</head>
<%
		String[] kmInfo = (String[])request.getAttribute("kmInfo");
		ArrayList[] salePartsInfoList = (ArrayList[])request.getAttribute("salePartsInfoList");
//		Object[] obj=(Object[])salePartsInfoList.get(0);
		SaleInfoForm sif=(SaleInfoForm)(salePartsInfoList[0].get(0));
        ArrayList partsList = salePartsInfoList[1];
		
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
		<p align="right" style="line-height:15px;">地址：上海市静安区镇宁路<b> 200 </b>号 东峰<b> 9 </b>楼<b>A</b>座
		</td>
	</tr>
    <tr>
        <td height="2" colspan="6" bgcolor="#677789" style='width:37.4pt;border:none;border-bottom:solid windowtext 1.0pt;
  mso-border-bottom-alt:solid windowtext .5pt;padding:0cm 1.4pt 0cm 1.4pt'></td>
    </tr>
</table>

<table border="0" width="98%" height="117">
	<tr>
		<td height="111" width="388"><font  color="purple">
		<p style="line-height:18px;"><b>&nbsp;To:</b>&nbsp;&nbsp;&nbsp; 高明精机工业股份有限公司</p>
		<p style="line-height:8px;"><b>&nbsp;Attn:</b>&nbsp;&nbsp;&nbsp;</p>
		<p style="line-height:8px;"><b>&nbsp;Cc:</b>&nbsp;&nbsp;&nbsp; <%=kmInfo[2]%></p>
		<p style="line-height:8px;"><b>&nbsp;Fax:</b>&nbsp;&nbsp; <%=kmInfo[1]%></font></td>
		
		<td height="111" width="460"><font  color="purple">
		<p style="line-height:18px;"><b>From:</b>&nbsp; 上海晨冉机电有限公司</p>
		<p style="line-height:8px;"><b>Date:</b>&nbsp;&nbsp; <%=Operate.toSqlDate()%></p>
		<p style="line-height:8px;"><b>Page:</b>&nbsp;&nbsp; 1</p>
		<p style="line-height:8px;">主旨:&nbsp;&nbsp; 询价</font></td>
	</tr>
    <tr>
        <td height="1" colspan="6" bgcolor="#677789" style='width:37.4pt;border:none;border-bottom:solid windowtext 1.0pt;
  mso-border-bottom-alt:solid windowtext .5pt;padding:0cm 1.4pt 0cm 1.4pt'></td>
    </tr>
</table>
 
<p align="center"><b><font size="6">询价单</font></b></p>
<p>询 价 单 号： <%=sif.getSaleNo()%></p>
<p>客 户 名 称： <%=sif.getCustomerName()%></p>
<p>交 货 时 间： 15天</p>
<p>交 货 地 点： <%=sif.getDeliveryPlaceTw()==null?"上海办":sif.getDeliveryPlaceTw()%></p>
<table  width="96%" id="table1" border=0 cellspacing=0 cellpadding=0>
	<tr>
		<td class="border1" width="59" align="center">项次</td>
        <td class="borderrow1" width="35%"align="center">零件名称</td>
        <td class="borderrow1" width="160" align="center">零件号码</td>
        <td class="borderrow1" align="center" width="80">数量</td>
		<td class="borderrow1" width="15%" align="center">机型</td>
		<td class="borderrow1" width="15%" align="center">机身编码</td>

	</tr>
	
	<%for(int i=0;i<partsList.size();i++){
            SaleDetailForm sdf=(SaleDetailForm)partsList.get(i);
	%>
	<tr>
		<td class="bordercol1" align="center"><%=(i+1)%></td>
        <td class="border2" align="left"><%=sdf.getSkuCode()%></td>
        <td class="border2" align="left"><%=sdf.getStuffNo()%></td>
        <td class="border2" align="center"><%=sdf.getPartNum()%><%=sdf.getSkuUnit()%></td>
		<td class="border2" align="left"><%=sdf.getModelCode()%></td>
		<td class="border2" align="left"><%=sdf.getModelSerialNo()%></td>
	</tr>
	<%}%>
	
	
</table>
<p><font color="#8b0000">沟通事项：</font></p>
<p>　</p>
<p align="right"><font size="4">上海办： <%=userName%> </font><font size="4">&nbsp; </font>&nbsp;&nbsp;&nbsp;&nbsp;
</p>

  
</form>
</body>
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
