<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.util.ArrayList"%> 
<%@ page import="com.dne.sie.common.tools.Operate"%> 
<%@ page import="com.dne.sie.reception.form.SaleDetailForm"%> 
<%@ page import="com.dne.sie.reception.form.SaleInfoForm"%> 
<html>
<head>
<title>ѯ�۵�</title>
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
		<p align="right" style="line-height:3px;">����������ҵ�ɷ����޹�˾�Ϻ�Ӫҵ��&nbsp; </p>
		<p align="right" style="line-height:3px;">�Ϻ���Ƚ�������޹�˾&nbsp;</p>
		<p align="right" style="line-height:3px;"><b>E-MAIL��kaoming2005@163.com</b></p>
		<p align="right" style="line-height:3px;">�绰:<b>021-62128864</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
		����:<b>021-62128854</b></p>
		<p align="right" style="line-height:15px;">�Ϻ��о���������·��<b> 200 </b>�� ����<b> 9A </b>��
		</td>
	</tr>
	 <tr> 
    <td height="2" colspan="6" bgcolor="#677789"></td>
  </tr>
</table>

<table border="0" width="98%" height="117">


	<tr>
		<td height="111" width="388"><font  color="purple">
		
		<p style="line-height:18px;"><b>&nbsp;To:</b>&nbsp;&nbsp;&nbsp; ����������ҵ�ɷ����޹�˾</p>
		<p style="line-height:8px;"><b>&nbsp;Attn:</b>&nbsp;&nbsp;&nbsp;<%=kmInfo[2]%></p>
		<p style="line-height:8px;"><b>&nbsp;Cc:</b>&nbsp;&nbsp;&nbsp; <%=kmInfo[0]%></p>
		<p style="line-height:8px;"><b>&nbsp;Fax:</b>&nbsp;&nbsp; <%=kmInfo[1]%></font></td>
		
		<td height="111" width="460"><font  color="purple">
		<p style="line-height:18px;"><b>From:</b>&nbsp; �Ϻ���Ƚ�������޹�˾</p>
		<p style="line-height:8px;"><b>Date:</b>&nbsp;&nbsp; <%=Operate.toSqlDate()%></p>
		<p style="line-height:8px;"><b>Page:</b>&nbsp;&nbsp; 1</p>
		<p style="line-height:8px;">��ּ:&nbsp;&nbsp; ѯ��</font></td>
		
	</tr>
	
	<tr> 
    <td height="1" colspan="6" bgcolor="#677789"></td>
  </tr>
</table>
 
<p align="center"><b><font size="6">ѯ�۵�</font></b></p>
<p>ѯ �� �� �ţ� <%=sif.getSaleNo()%></p>
<p>�� �� �� �ƣ� <%=sif.getCustomerName()%></p>
<p>�� �� ʱ �䣺 </p>
<p>�� �� �� �㣺 <%=sif.getDeliveryPlaceTw()==null?"�Ϻ���":sif.getDeliveryPlaceTw()%></p>
<table border="1" width="96%" id="table1">
	<tr>
		<td width="59" align="center">���</td>
		<td width="15%" align="center">����</td>
		<td width="15%" align="center">�������</td>
		<td width="35%"align="center">�������</td>
		<td width="160" align="center">�Ϻ�</td>
		<td align="center">����</td>
	</tr>
	
	<%for(int i=0;i<partsList.size();i++){
            SaleDetailForm sdf=(SaleDetailForm)partsList.get(i);
	%>
	<tr>
		<td align="center"><%=(i+1)%></td>
		<td align="left"><%=sdf.getModelCode()%></td>
		<td align="left"><%=sdf.getModelSerialNo()%></td>
		<td ><%=sdf.getSkuCode()%></td>
		<td ><%=sdf.getStuffNo()%></td>
		<td align="center"><%=sdf.getPartNum()%><%=sdf.getSkuUnit()%></td>
	</tr>
	<%}%>
	
	
</table>
<p>��</p>
<p>��</p>
<p align="right"><font size="4">�Ϻ��죺 <%=userName%> </font><font size="4">&nbsp; </font>&nbsp;&nbsp;&nbsp;&nbsp;
</p>
 


  
  
    <table>
    
  <tr align="center">
      <td >
  	<input type="button" onclick="f_print1()" value='ҳ������' class="NOPRINT">
  	<input type="button" onclick="f_print2()" value='��ӡԤ��' class="NOPRINT">
  	<input type="button" onclick="f_print3()" value='ֱ�Ӵ�ӡ'  class="NOPRINT">
      </td>
  </tr>
  
</table>
  
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

function PageSetup_Null() { //���ҳü��ҳ��
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
