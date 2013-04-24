<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="java.util.ArrayList"%> 
<%@ page import="com.dne.sie.common.tools.Operate"%> 
<%@ page import="com.dne.sie.stock.bo.StockInBo"%> 
<%@ page import="com.dne.sie.stock.form.StockFlowForm"%> 

<html>
<head>
<title>t_print</title>
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
		ArrayList printList = (ArrayList)request.getAttribute("printList");

		String strTime=Operate.getDate();
  		StockInBo sio = StockInBo.getInstance();
		int sum=0;
%>


<body >
<form>
<OBJECT  id=WebBrowser  classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2  height=0  width=0 VIEWASTEXT>
 </OBJECT> 


<table align="center" >

<%
		if(printList!=null){
			String lastSkuCode="";
     		for(int i=0;i<printList.size();i++){
     			StockFlowForm sff=(StockFlowForm)printList.get(i);
     			if(!lastSkuCode.equals(sff.getSkuCode())){
     				sum=sio.getNowSkuNum(sff.getSkuCode());
     			}
     			lastSkuCode=sff.getSkuCode();
%>

  <TBODY>  
    
    <TR> 
      <TD align="center">出库打印单</TD>
    </TR>
    
    <TR> 
      <TD>&nbsp;</TD>
    </TR>
    <TR> 
      <TD>打印日期:<%=strTime%></TD>
    </TR>
    
    <TR> 
      <TD>出库方式:<%=sff.getFlowItem()==null?"":sff.getFlowItem()%></TD>
    </TR>
    
    <TR> 
      <TD>公司名称:<%=sff.getCustomerName()==null?"":sff.getCustomerName()%></TD>
    </TR>
    
    <TR> 
      <TD>&nbsp;</TD>
    </TR>
   
     <TR> 
      <TD>料号:<B><%=sff.getStuffNo()==null?"":sff.getStuffNo()%></B></TD>
    </TR>
    <TR> 
      <TD>零件名称:<%=sff.getSkuCode()==null?"":sff.getSkuCode()%></TD>
    </TR>
     <TR> 
      <TD>简称:<B><%=sff.getShortCode()==null?"":sff.getShortCode()%></B></TD>
    </TR>
     <TR> 
      <TD>规格:<%=sff.getStandard()==null?"":sff.getStandard()%></TD>
    </TR>
    <TR> 
      <TD>数量:<%=sff.getSkuNum()==null?"":sff.getSkuNum()%></TD>
    </TR>
    <TR> 
      <TD>单位:<%=sff.getSkuUnit()==null?"":sff.getSkuUnit()%></TD>
    </TR>
    <TR> 
      <TD>价格:<%=sff.getPerCost()==null?"":sff.getPerCost()%></TD>
    </TR>
     <TR> 
      <TD>结存库存:<%=sum%></TD>
    </TR>
    <TR> 
      <TD>&nbsp;</TD>
    </TR>
 
    
     <TR> 
      <TD>- - - - - - - - - - - - - - </TD>
    </TR>
    
    <TR> 
      <TD>&nbsp;</TD>
    </TR>
    <TR> 
      <TD>签名（出库人）:__________</TD>
    </TR>
    
    <TR> 
      <TD>&nbsp;</TD>
    </TR>
    <TR> 
      <TD>&nbsp;</TD>
    </TR>

  </TBODY>
  
  <%}}%>
    
    
  <tr align="left">
      <td >
  	<input type="button" onclick="f_print1()" value='页面设置' class="NOPRINT">
  	<input type="button" onclick="f_print2()" value='打印预览' class="NOPRINT">
  	<input type="button" onclick="f_print3()" value='直接打印'  class="NOPRINT">
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
<%
}catch(Exception e){
	e.printStackTrace();
}%>