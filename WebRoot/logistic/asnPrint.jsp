<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri=  "/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri=  "/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ page import="java.util.ArrayList"%> 
<%@ page import="com.dne.sie.common.tools.DicInit"%> 
<%@ page import="com.dne.sie.common.tools.Operate"%> 

<html>
<head>
<title>asn_print</title>
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
		ArrayList detailList = (ArrayList)request.getAttribute("detailList");
		int count=0;

		if(detailList!=null)	count=detailList.size();
		
		String payStatus = (String)request.getAttribute("payStatus");
		String billingStatus = (String)request.getAttribute("billingStatus");

		String userName = (String) session.getAttribute("userName");

%>


<body >
<form>
<OBJECT  id=WebBrowser  classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2  height=0  width=0 VIEWASTEXT> </OBJECT> 

<p class=MsoNormal><b style='mso-bidi-font-weight:normal'><u><span 
style='font-size:14.0pt;mso-bidi-font-size:10.0pt;font-family:宋体;mso-ascii-font-family:
"Times New Roman";mso-hansi-font-family:"Times New Roman";mso-ansi-language:
EN-GB'>收件人：<bean:write property="linkman" name="asnForm" /></span></u></b></p>



<p class=MsoNormal align=center style='text-align:center'><b style='mso-bidi-font-weight:
normal'><u><span style='font-size:14.0pt;mso-bidi-font-size:10.0pt;font-family:
宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>高明上海办用户出货单</span></u></b></p>






<table class=MsoNormalTable border=0 cellspacing=0 cellpadding=0 style='border-collapse:collapse;mso-padding-alt:0cm 5.4pt 0cm 5.4pt'>
 <tr>
  <td width=119 valign=top style='width:89.4pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal><span style='font-size:12.0pt;mso-bidi-font-size:10.0pt;
  font-family:宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:
  "Times New Roman"'>出货单号：</span></p>
  </td>
  <td width=575 valign=top style='width:431.6pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal><span lang=EN-US style='font-size:12.0pt;mso-bidi-font-size:
  10.0pt'><bean:write property="asnNo" name="asnForm" /><o:p></o:p></span></p>
  </td>
 </tr>
 <tr style='height:13.35pt'>
  <td width=119 valign=top style='width:89.4pt;padding:0cm 5.4pt 0cm 5.4pt;
  height:13.35pt'>
  <p class=MsoNormal><span style='font-size:12.0pt;mso-bidi-font-size:10.0pt;
  font-family:宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:
  "Times New Roman"'>客户名称：</span></p>
  </td>
  <td width=575 valign=top style='width:431.6pt;padding:0cm 5.4pt 0cm 5.4pt;
  height:13.35pt'>
  <p class=MsoNormal><span lang=EN-US style='font-size:12.0pt;mso-bidi-font-size:
  10.0pt'><bean:write property="customerName" name="asnForm" /><o:p></o:p></span></p>
  </td>
 </tr>
 <tr>
  <td width=119 valign=top style='width:89.4pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal><span style='font-size:12.0pt;mso-bidi-font-size:10.0pt;
  font-family:宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:
  "Times New Roman"'>出货时间：</span></p>
  </td>
  <td width=575 valign=top style='width:431.6pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal><span lang=EN-US style='font-size:12.0pt;mso-bidi-font-size:
  10.0pt'><%=Operate.toSqlDate()%><o:p></o:p></span></p>
  </td>
 </tr>
 <tr style='mso-yfti-lastrow:yes'>
  <td width=119 valign=top style='width:89.4pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal><span style='font-size:12.0pt;mso-bidi-font-size:10.0pt;
  font-family:宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:
  "Times New Roman"'>出货地点：</span></p>
  </td>
  <td width=575 valign=top style='width:431.6pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal><span style='font-size:12.0pt;mso-bidi-font-size:10.0pt;
  font-family:宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:
  "Times New Roman"'>快递至: <bean:write property="shippingAddress" name="asnForm" /></span></p>
  </td>
 </tr>
</table>



<p class=MsoNormal></p>

<p class=MsoNormal><span lang=EN-GB style='mso-ansi-language:EN-GB'><o:p>&nbsp;</o:p></span></p>

<table class=MsoNormalTable border=1 cellspacing=0 cellpadding=0
 style='border-collapse:collapse;border:none;mso-border-alt:solid windowtext .5pt;
 mso-padding-alt:0cm 5.4pt 0cm 5.4pt;mso-border-insideh:.5pt solid windowtext;
 mso-border-insidev:.5pt solid windowtext'>
 <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes;page-break-inside:avoid'>
  <td width=49 valign=top style='width:36.9pt;border:solid windowtext 1.0pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><span
  style='font-size:12.0pt;mso-bidi-font-size:10.0pt;font-family:宋体;mso-ascii-font-family:
  "Times New Roman";mso-hansi-font-family:"Times New Roman"'>项次</span> </p>
  </td>
  <td width=213 valign=top style='width:159.9pt;border:solid windowtext 1.0pt;
  border-left:none;mso-border-left-alt:solid windowtext .5pt;mso-border-alt:
  solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><span
  style='font-size:12.0pt;mso-bidi-font-size:10.0pt;font-family:宋体;mso-ascii-font-family:
  "Times New Roman";mso-hansi-font-family:"Times New Roman"'>零件名称</span> </p>
  </td>
  <td width=236 valign=top style='width:177.15pt;border:solid windowtext 1.0pt;
  border-left:none;mso-border-left-alt:solid windowtext .5pt;mso-border-alt:
  solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><span
  style='font-size:12.0pt;mso-bidi-font-size:10.0pt;font-family:宋体;mso-ascii-font-family:
  "Times New Roman";mso-hansi-font-family:"Times New Roman"'>零件料号</span> </p>
  </td>
  <td width=189 valign=top style='width:5.0cm;border:solid windowtext 1.0pt;
  border-left:none;mso-border-left-alt:solid windowtext .5pt;mso-border-alt:
  solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><span
  style='font-size:12.0pt;mso-bidi-font-size:10.0pt;font-family:宋体;mso-ascii-font-family:
  "Times New Roman";mso-hansi-font-family:"Times New Roman"'>数量</span> </p>
  </td>
 </tr>
 <%
       if(detailList!=null){
       	    String strTr="";
      	    for(int i=0;i<detailList.size();i++){
      	        if(i%2==0) strTr="tableback2";
      	        else strTr="tableback1";
      		String[] temp=(String[])detailList.get(i);
      %>      
 <tr style='mso-yfti-irow:1;page-break-inside:avoid;height:23.1pt'>
  <td width=49 align="center" style='width:36.9pt;border:solid windowtext 1.0pt;border-top:
  none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:23.1pt'>
  <p class=MsoNormal> <%=i+1%></p>
  </td>
  <td width=213 style='width:159.9pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt;height:23.1pt'>
  <p class=MsoNormal><%=temp[2]==null?"":temp[2]%></p>
  </td>
  <td width=236 style='width:177.15pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt;height:23.1pt'>
  <p class=MsoNormal ><%=temp[1]==null?"":temp[1]%></p>
  </td>
  <td width=189 align="center" valign=top style='width:5.0cm;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt;height:23.1pt'>
  <p class=MsoNormal align=center style='text-align:center'><%=temp[5]==null?"":temp[5]%></p>
  </td>
 </tr>
 <%}}%>
 <tr style='mso-yfti-irow:2;page-break-inside:avoid;height:22.75pt'>
  <td width=49 style='width:36.9pt;border:solid windowtext 1.0pt;border-top:
  none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:22.75pt'>
  <p class=MsoNormal></p>
  </td>
  <td width=213 style='width:159.9pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt;height:22.75pt'>
  <p class=MsoNormal></p>
  </td>
  <td width=236 style='width:177.15pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt;height:22.75pt'>
  <p class=MsoNormal align=center style='text-align:center'></p>
  </td>
  <td width=189 valign=top style='width:5.0cm;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt;height:22.75pt'>
  <p class=MsoNormal align=center style='text-align:center'></p>
  </td>
 </tr>
 <tr style='mso-yfti-lastrow:yes;height:56.75pt'>
  <td width=693 colspan=4 valign=top style='width:519.9pt;border:solid windowtext 1.0pt;
  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:56.75pt'>
  <p class=MsoNormal><span style='font-family:宋体;mso-ascii-font-family:"Times New Roman";
  mso-hansi-font-family:"Times New Roman"'>备注：1、 <%=DicInit.getSystemName("PAY_STATUS", payStatus)%> </span></p>
  <p class=MsoNormal><span  style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>2、 <%=DicInit.getSystemName("BILLING_STATUS", billingStatus)%></p>
  </td>
 </tr>
</table>




<p class=MsoNormal><span lang=EN-US><o:p>&nbsp;</o:p></span></p>



<p class=MsoNormal align=center style='text-align:center'><span
style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</span></span><span style='font-size:
14.0pt;mso-bidi-font-size:10.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";
mso-hansi-font-family:"Times New Roman"'>上海晨冉机电有限公司</span><span lang=EN-US
style='font-size:14.0pt;mso-bidi-font-size:10.0pt'><o:p></o:p></span></p>

<p class=MsoNormal align=center style='text-align:center'><span lang=EN-US
style='font-size:14.0pt;mso-bidi-font-size:10.0pt'><span
style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</span></span><span style='font-size:14.0pt;mso-bidi-font-size:10.0pt;
font-family:宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:
"Times New Roman"'>　　</span><span style='font-size:14.0pt;mso-bidi-font-size:
10.0pt'> </span><span style='font-size:14.0pt;mso-bidi-font-size:10.0pt;
font-family:宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:
"Times New Roman"'><%=userName%></span><span lang=EN-US style='font-size:14.0pt;
mso-bidi-font-size:10.0pt'><o:p></o:p></span></p>


    
    
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