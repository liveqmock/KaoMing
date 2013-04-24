<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="java.util.ArrayList"%> 
<%@ page import="com.dne.sie.support.form.AccountStatisticsForm"%>
<%@ page import="com.dne.sie.support.form.AccountStatisticsSubjectForm"%>
<%@ page import="com.dne.sie.support.form.AccountStatisticsEmployeeForm"%>
<%@ page import="com.dne.sie.common.tools.Operate"%>

<html>
<head>
<title>统计明细</title>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/styles2.css" rel="stylesheet" type="text/css" />
<script language=javascript src="js/common.js"></script>
<script language=javascript src="js/inputValidation.js"></script>

<script language="JavaScript" type="text/JavaScript">
<!--
function highlightButton(s) {
	if (event.srcElement.tagName=="INPUT")
		event.srcElement.className=s;
}
function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}
function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
function MM_openBrWindow(theURL,winName,features) { //v2.0
  window.open(theURL,winName,features);
}
function MM_showHideLayers() { //v6.0
  var i,p,v,obj,args=MM_showHideLayers.arguments;
  for (i=0; i<(args.length-2); i+=3) if ((obj=MM_findObj(args[i]))!=null) { v=args[i+2];
    if (obj.style) { obj=obj.style; v=(v=='show')?'visible':(v=='hide')?'hidden':v; }
    obj.visibility=v; }
}

function init(){
		MM_showHideLayers('Layer1','','show','Layer2','','hide','Layer3','','hide');

}

//-->
</script>
</head>

<%
	try{
	
			AccountStatisticsForm asf = (AccountStatisticsForm)request.getAttribute("accountStatisticsForm");
			ArrayList[] subStatList = (ArrayList[])request.getAttribute("subStatList");
			ArrayList[] empStatList = (ArrayList[])request.getAttribute("empStatList");
			ArrayList xfSubList = (ArrayList)request.getAttribute("xfSubList");

			String accountMonth = (String)request.getAttribute("accountMonth");
			
			Object[] xff = (Object[])xfSubList.get(0);
		
%>


<body bgcolor="#ffffff" leftmargin="0" topmargin="0" rightmargin="0" bottommargin="0" marginwidth="0" marginheight="0" onload="init()" >
<table width="90%" height="100%" border="0" align="center" cellpadding="0" cellspacing="6" class="content12">
  <tr> 
            
    <td height="556" align="right" valign="top"> 
    <div align="center" id="Layer1" style="position:absolute; left:35; top:80; width:912px; height:390px; z-index:1;"> 
        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="content12">
          <tr> 
            <td valign="bottom"> <table border="0" cellpadding="0" cellspacing="0">
                <tr> 
                  <td width="30"><input name="labelButton" type="button" class="button_b"  value="收支明细账"></td>
                  <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','show','Layer3','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="科目明细账"></td>
                  <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer3','','show')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="人员明细账"></td>
                  
                </tr>
              </table></td>
          </tr>
          <tr> 
            <td height="2" bgcolor="#677789"></td>
          </tr>
          <tr> 
            <td height="6" bgcolor="#CECECE"></td>
          </tr>
          <tr> 
            <td valign="top"> 
            
            
<table border="0" cellpadding="0" cellspacing="0" width="1028" >
	<colgroup>
		<col width="56" style="width: 42pt"><col width="95" style="width: 71pt">
		<col width="119" style="width: 89pt">
		<col width="115" style="width: 86pt">
		<col width="97" style="width: 73pt"><col width="83" style="width: 62pt">
		<col width="108" style="width: 81pt">
		<col width="128" style="width: 96pt">
		<col width="117" style="width: 88pt">
		<col width="110" style="width: 83pt">
	</colgroup>
	<tr height="25" style="height:18.75pt">
		<td height="25" width="56" style="height: 18.75pt; width: 42pt; font-family: 微软雅黑, sans-serif; vertical-align: bottom; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; text-align: general; white-space: nowrap; border-left: 1.0pt solid windowtext; border-right: medium none; border-top: 1.0pt solid windowtext; border-bottom: medium none; padding: 0px">
		　</td>
		<td width="95" style="width: 71pt; font-family: 微软雅黑, sans-serif; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: 1.0pt solid windowtext; border-right: .5pt solid windowtext; border-top: 1.0pt solid windowtext; border-bottom: .5pt solid windowtext; padding: 0px">
		期初金额</td>
		<td width="119" style="width: 89pt; font-family: 楷体, monospace; text-align: right; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: medium none; border-right: medium none; border-top: 1.0pt solid windowtext; border-bottom: .5pt solid windowtext; padding: 0px">
		<%=Operate.toFix((asf.getPriorCash() + asf.getPriorBank()),2)%>&nbsp;</td>
		<td colspan="5" width="531" style="width: 398pt; font-family: 微软雅黑, sans-serif; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: 1.0pt solid windowtext; border-right: 1.0pt solid black; border-top: 1.0pt solid windowtext; border-bottom: .5pt solid windowtext; padding: 0px">
		本期发生额</td>
		<td width="117" style="width: 88pt; font-family: 微软雅黑, sans-serif; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: medium none; border-right: .5pt solid windowtext; border-top: 1.0pt solid windowtext; border-bottom: .5pt solid windowtext; padding: 0px">
		期未金额&nbsp;</td>
		<td width="110" style="width: 83pt; font-family: 楷体, monospace; text-align: right; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: medium none; border-right: 1.0pt solid windowtext; border-top: 1.0pt solid windowtext; border-bottom: .5pt solid windowtext; padding: 0px">
		<%=Operate.toFix(asf.getTotalAmount(),2)%>&nbsp; </td>
	</tr>
	<tr height="25" style="height:18.75pt">
		<td height="25" style="height: 18.75pt; font-family: 微软雅黑, sans-serif; vertical-align: bottom; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; text-align: general; white-space: nowrap; border-left: 1.0pt solid windowtext; border-right: medium none; border-top: medium none; border-bottom: medium none; padding: 0px">
		　</td>
		<td style="font-family: 微软雅黑, sans-serif; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: 1.0pt solid windowtext; border-right: .5pt solid windowtext; border-top: medium none; border-bottom: .5pt solid windowtext; padding: 0px">
		帐户</td>
		<td style="font-size: 14.0pt; font-family: 楷体, monospace; text-align: center; color: windowtext; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: medium none; border-right: medium none; border-top: medium none; border-bottom: .5pt solid windowtext; padding: 0px">
		金额</td>
		<td colspan="2" style="font-family: 微软雅黑, sans-serif; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: 1.0pt solid windowtext; border-right: .5pt solid windowtext; border-top: .5pt solid windowtext; border-bottom: .5pt solid windowtext; padding: 0px">
		收入</td>
		<td colspan="3" style="font-family: 微软雅黑, sans-serif; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: medium none; border-right: 1.0pt solid black; border-top: .5pt solid windowtext; border-bottom: .5pt solid windowtext; padding: 0px">
		支出</td>
		<td style="font-family: 微软雅黑, sans-serif; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: medium none; border-right: .5pt solid windowtext; border-top: medium none; border-bottom: .5pt solid windowtext; padding: 0px">
		帐户</td>
		<td style="font-size: 14.0pt; font-family: 楷体, monospace; text-align: center; color: windowtext; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: medium none; border-right: 1.0pt solid windowtext; border-top: medium none; border-bottom: .5pt solid windowtext; padding: 0px">
		金额</td>
	</tr>
	<tr height="25" style="height:18.75pt">
		<td height="25" style="height: 18.75pt; font-family: 微软雅黑, sans-serif; vertical-align: bottom; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; text-align: center; white-space: nowrap; border-left: 1.0pt solid windowtext; border-right: medium none; border-top: medium none; border-bottom: medium none; padding: 0px; background: #FCD5B4">
		上</td>
		<td style="font-family: 微软雅黑, sans-serif; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: 1.0pt solid windowtext; border-right: .5pt solid windowtext; border-top: medium none; border-bottom: .5pt solid windowtext; padding: 0px; background: #FCD5B4">
		上/现</td>
		<td style="font-family: 楷体, monospace; text-align: right; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: medium none; border-right: medium none; border-top: medium none; border-bottom: .5pt solid windowtext; padding: 0px; background: #FCD5B4">
		<%=asf.getPriorCash()%>&nbsp; </td>
		<td style="font-family: 微软雅黑, sans-serif; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: 1.0pt solid windowtext; border-right: .5pt solid windowtext; border-top: medium none; border-bottom: .5pt solid windowtext; padding: 0px; background: #FCD5B4">
		项目</td>
		<td style="font-size: 14.0pt; font-family: 楷体, monospace; text-align: center; color: windowtext; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: medium none; border-right: .5pt solid windowtext; border-top: medium none; border-bottom: .5pt solid windowtext; padding: 0px; background: #FCD5B4">
		金额</td>
		<td style="font-family: 微软雅黑, sans-serif; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: medium none; border-right: .5pt solid windowtext; border-top: medium none; border-bottom: .5pt solid windowtext; padding: 0px; background: #FCD5B4">
		科目</td>
		<td style="font-family: 微软雅黑, sans-serif; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: medium none; border-right: .5pt solid windowtext; border-top: medium none; border-bottom: .5pt solid windowtext; padding: 0px; background: #FCD5B4">
		分类</td>
		<td style="font-size: 14.0pt; font-family: 楷体, monospace; text-align: center; color: windowtext; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: medium none; border-right: 1.0pt solid windowtext; border-top: medium none; border-bottom: .5pt solid windowtext; padding: 0px; background: #FCD5B4">
		小计</td>
		<td style="font-family: 微软雅黑, sans-serif; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: medium none; border-right: .5pt solid windowtext; border-top: medium none; border-bottom: .5pt solid windowtext; padding: 0px; background: #FCD5B4">
		上/现</td>
		<td style="font-family: 楷体, monospace; text-align: right; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: medium none; border-right: 1.0pt solid windowtext; border-top: medium none; border-bottom: .5pt solid windowtext; padding: 0px; background: #FCD5B4">
		<%if(asf.getCurrentCash() < 0){%>
		<font color="#FF0000"><%=asf.getCurrentCash()%></font>&nbsp;
		<%}else{%>
			<%=asf.getCurrentCash()%>&nbsp;
		<%}%>
		</td>
	</tr>
	<tr height="23" style="height:17.25pt">
		<td height="23" style="height: 17.25pt; font-family: 微软雅黑, sans-serif; vertical-align: bottom; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; text-align: center; white-space: nowrap; border-left: 1.0pt solid windowtext; border-right: medium none; border-top: medium none; border-bottom: medium none; padding: 0px; background: #FCD5B4">
		　</td>
		<td style="font-family: 微软雅黑, sans-serif; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: 1.0pt solid windowtext; border-right: .5pt solid windowtext; border-top: medium none; border-bottom: .5pt solid windowtext; padding: 0px; background: #FCD5B4">
		　</td>
		<td style="font-family: 楷体, monospace; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: medium none; border-right: medium none; border-top: medium none; border-bottom: .5pt solid windowtext; padding: 0px; background: #FCD5B4">
		　</td>
		<td style="font-family: 微软雅黑, sans-serif; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: 1.0pt solid windowtext; border-right: .5pt solid windowtext; border-top: medium none; border-bottom: .5pt solid windowtext; padding: 0px; background: #FCD5B4">
		收入现金</td>
		<td style="font-family: 楷体, monospace; text-align: right; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: medium none; border-right: .5pt solid windowtext; border-top: medium none; border-bottom: .5pt solid windowtext; padding: 0px; background: #FCD5B4">
		<%=asf.getCashReceipt()%>&nbsp; </td>
		<td rowspan="<%=xfSubList.size()%>" style="font-family: 微软雅黑, sans-serif; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: .5pt solid windowtext; border-right: .5pt solid windowtext; border-top: medium none; border-bottom: .5pt solid windowtext; padding: 0px; background: #FCD5B4">
		管销费<br><%=asf.getCashPayment()%></td>
		<td title="<%=xff[2]%>" style="font-family: 微软雅黑, sans-serif; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: medium none; border-right: .5pt solid windowtext; border-top: medium none; border-bottom: .5pt solid windowtext; padding: 0px; background: #FCD5B4">
		<%=xff[0]%></td>
		<td style="font-family: 楷体, monospace; text-align: right; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: medium none; border-right: 1.0pt solid windowtext; border-top: medium none; border-bottom: .5pt solid windowtext; padding: 0px; background: #FCD5B4">
		<%=xff[1]%>&nbsp; </td>
		<td style="font-family: 微软雅黑, sans-serif; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: medium none; border-right: .5pt solid windowtext; border-top: medium none; border-bottom: .5pt solid windowtext; padding: 0px; background: #FCD5B4">
		　</td>
		<td style="font-family: 楷体, monospace; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: medium none; border-right: 1.0pt solid windowtext; border-top: medium none; border-bottom: .5pt solid windowtext; padding: 0px; background: #FCD5B4">
		　</td>
	</tr>
	<%for(int subNum=1;subNum<xfSubList.size();subNum++){
			Object[] assfLoop = (Object[])xfSubList.get(subNum);
			
	%>
				<tr height="23" style="height:17.25pt">
					<td height="23" style="height: 17.25pt; font-family: 微软雅黑, sans-serif; text-align: center; vertical-align: bottom; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; white-space: nowrap; border-left: 1.0pt solid windowtext; border-right: medium none; border-top: medium none; border-bottom: medium none; padding: 0px; background: #FCD5B4">
					<%if(subNum==1){%>海<%}%></td>
					<td style="font-family: 微软雅黑, sans-serif; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: 1.0pt solid windowtext; border-right: .5pt solid windowtext; border-top: medium none; border-bottom: .5pt solid windowtext; padding: 0px; background: #FCD5B4">
					　</td>
					<td style="font-family: 楷体, monospace; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: medium none; border-right: medium none; border-top: medium none; border-bottom: .5pt solid windowtext; padding: 0px; background: #FCD5B4">
					　</td>
					<td style="font-family: 微软雅黑, sans-serif; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: 1.0pt solid windowtext; border-right: .5pt solid windowtext; border-top: medium none; border-bottom: .5pt solid windowtext; padding: 0px; background: #FCD5B4">
					　</td>
					<td style="font-family: 楷体, monospace; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: medium none; border-right: .5pt solid windowtext; border-top: medium none; border-bottom: .5pt solid windowtext; padding: 0px; background: #FCD5B4">
					　</td>
					<td title="<%=assfLoop[2]%>" style="font-family: 微软雅黑, sans-serif; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: medium none; border-right: .5pt solid windowtext; border-top: medium none; border-bottom: .5pt solid windowtext; padding: 0px; background: #FCD5B4">
					<%=assfLoop[0]%></td>
					<td style="font-family: 楷体, monospace; text-align: right; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: medium none; border-right: 1.0pt solid windowtext; border-top: medium none; border-bottom: .5pt solid windowtext; padding: 0px; background: #FCD5B4">
					<%=assfLoop[1]%>&nbsp; </td>
					<td style="font-family: 微软雅黑, sans-serif; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: medium none; border-right: .5pt solid windowtext; border-top: medium none; border-bottom: .5pt solid windowtext; padding: 0px; background: #FCD5B4">
					　</td>
					<td style="font-family: 楷体, monospace; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: medium none; border-right: 1.0pt solid windowtext; border-top: medium none; border-bottom: .5pt solid windowtext; padding: 0px; background: #FCD5B4">
					　</td>
				</tr>
	<%}%>
	<tr height="23" style="height:17.25pt">
		<td height="23" style="height: 17.25pt; font-family: 微软雅黑, sans-serif; text-align: center; vertical-align: bottom; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; white-space: nowrap; border: 1.0pt solid windowtext; padding: 0px; background: #B6DDE8">
		晨冉</td>
		<td style="font-family: 微软雅黑, sans-serif; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: medium none; border-right: .5pt solid windowtext; border-top: 1.0pt solid windowtext; border-bottom: 1.0pt solid windowtext; padding: 0px; background: #B6DDE8">
		晨冉</td>
		<td style="font-family: 楷体, monospace; text-align: right; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: medium none; border-right: medium none; border-top: 1.0pt solid windowtext; border-bottom: 1.0pt solid windowtext; padding: 0px; background: #B6DDE8">
		<%=asf.getPriorBank()%>&nbsp; </td>
		<td style="font-family: 微软雅黑, sans-serif; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: 1.0pt solid windowtext; border-right: .5pt solid windowtext; border-top: medium none; border-bottom: 1.0pt solid windowtext; padding: 0px; background: #B6DDE8">
		晨冉收入</td>
		<td style="font-size: 12.0pt; font-family: 楷体, monospace; text-align: right; color: windowtext; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: medium none; border-right: .5pt solid windowtext; border-top: medium none; border-bottom: 1.0pt solid windowtext; padding: 0px; background: #B6DDE8">
		<%=asf.getBankReceipt()%>&nbsp; </td>
		<td style="font-family: 微软雅黑, sans-serif; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: medium none; border-right: .5pt solid windowtext; border-top: medium none; border-bottom: 1.0pt solid windowtext; padding: 0px; background: #B6DDE8">
		　</td>
		<td style="font-family: 微软雅黑, sans-serif; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: medium none; border-right: .5pt solid windowtext; border-top: medium none; border-bottom: 1.0pt solid windowtext; padding: 0px; background: #B6DDE8">
		晨冉支出</td>
		<td style="font-size: 12.0pt; font-family: 楷体, monospace; text-align: right; color: windowtext; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: medium none; border-right: 1.0pt solid windowtext; border-top: medium none; border-bottom: 1.0pt solid windowtext; padding: 0px; background: #B6DDE8">
		<%=asf.getBankPayment()%>&nbsp; </td>
		<td style="font-family: 微软雅黑, sans-serif; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: medium none; border-right: .5pt solid windowtext; border-top: medium none; border-bottom: 1.0pt solid windowtext; padding: 0px; background: #B6DDE8">
		晨冉</td>
		<td style="font-family: 楷体, monospace; text-align: right; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: medium none; border-right: 1.0pt solid windowtext; border-top: medium none; border-bottom: 1.0pt solid windowtext; padding: 0px; background: #B6DDE8">
		<%=asf.getCurrentBank()%>&nbsp; </td>
	</tr>
	<tr height="25" style="height:18.75pt">
		<td height="25" style="height: 18.75pt; font-family: 微软雅黑, sans-serif; vertical-align: bottom; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; text-align: general; white-space: nowrap; border: medium none; padding: 0px">　</td>
		<td style="font-family: 微软雅黑, sans-serif; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border: medium none; padding: 0px">　</td>
		<td style="font-size: 14.0pt; font-family: 楷体, monospace; text-align: center; color: windowtext; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border: medium none; padding: 0px">　</td>
		<td style="font-family: 微软雅黑, sans-serif; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: 1.5pt solid windowtext; border-right: medium none; border-top: medium none; border-bottom: 1.5pt solid windowtext; padding: 0px">
		合计</td>
		<td style="font-size: 12.0pt; font-family: 楷体, monospace; text-align: right; color: windowtext; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: medium none; border-right: 1.5pt solid windowtext; border-top: medium none; border-bottom: 1.5pt solid windowtext; padding: 0px">
		<%=Operate.toFix((asf.getBankReceipt() + asf.getCashReceipt()),2)%>&nbsp; </td>
		<td style="font-family: 微软雅黑, sans-serif; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border: medium none; padding: 0px">　</td>
		<td style="font-family: 微软雅黑, sans-serif; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: 1.5pt solid windowtext; border-right: medium none; border-top: medium none; border-bottom: 1.5pt solid windowtext; padding: 0px">
		合计</td>
		<td style="font-size: 12.0pt; font-family: 楷体, monospace; text-align: center; color: windowtext; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: medium none; border-right: 1.5pt solid windowtext; border-top: medium none; border-bottom: 1.5pt solid windowtext; padding: 0px">
		　</td>
		<td style="font-family: 微软雅黑, sans-serif; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border: medium none; padding: 0px">　</td>
		<td style="font-size: 14.0pt; font-family: 楷体, monospace; text-align: center; color: windowtext; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border: medium none; padding: 0px">　</td>
	</tr>
</table>
            
            
						</td>
     			</tr>
         
     
    </table>
   </div>
  
   <div align="center" id="Layer2" style="position:absolute; left:35; top:80; width:912px; height:390px; z-index:1; visibility: hidden;"> 
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="content12">
     <tr> 
      <td valign="bottom"><table border="0" cellpadding="0" cellspacing="0">
		<tr> 
			<td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','show','Layer2','','hide','Layer3','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="收支明细账"></td>		
			<td width="30"><input name="labelButton" type="button" class="button_b" value="科目明细账"></td>
			<td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer3','','show')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="人员明细账"></td>
        </tr>
       </table></td>
     </tr>
     <tr> 
      <td height="2" colspan="2" bgcolor="#677789"></td>
     </tr>
     <tr> 
      <td height="6" colspan="2" bgcolor="#CECECE"></td>
     </tr>
     <tr> 
      <td valign="top" colspan="2">
      	
      	<table border="0" cellpadding="0" cellspacing="0" width="750" >
	<colgroup>
		<col width="150" style="width: 140pt">
		<col width="150" style="width: 100pt">
		<col width="150" style="width: 80pt">
		<col width="150" style="width: 140pt">
		<col width="150" style="width: 100pt">
	</colgroup>
	<tr height="33" style="height:24.75pt">
		<td height="33" colspan="2"  style="height: 24.75pt; width: 209pt; font-size: 18.0pt; font-family: 微软雅黑, sans-serif; vertical-align: bottom; color: windowtext; font-weight: 400; font-style: normal; text-decoration: none; text-align: general; white-space: nowrap; border: medium none; padding: 0px">
		现金收入</td>
		<td width="72" style="width: 54pt; vertical-align: bottom; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; font-family: 宋体; text-align: general; white-space: nowrap; border: medium none; padding: 0px">　</td>
		<td colspan="2"  style="width: 209pt; font-size: 18.0pt; font-family: 微软雅黑, sans-serif; vertical-align: bottom; color: windowtext; font-weight: 400; font-style: normal; text-decoration: none; text-align: general; white-space: nowrap; border: medium none; padding: 0px">
		现金支出</td>
	</tr>
	<tr height="23" style="height:17.25pt">
		<td height="23" style="height: 17.25pt; font-family: 微软雅黑, sans-serif; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border: .5pt solid windowtext; padding: 0px; background: white">
		科目名称</td>
		<td style="font-family: 微软雅黑, sans-serif; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: medium none; border-right: .5pt solid windowtext; border-top: .5pt solid windowtext; border-bottom: .5pt solid windowtext; padding: 0px; background: white">
		实付金额&nbsp;</td>
		<td style="vertical-align: bottom; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; font-family: 宋体; text-align: general; white-space: nowrap; border: medium none; padding: 0px">　</td>
		<td style="font-family: 微软雅黑, sans-serif; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border: .5pt solid windowtext; padding: 0px; background: white">
		科目名称</td>
		<td style="font-family: 微软雅黑, sans-serif; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: medium none; border-right: .5pt solid windowtext; border-top: .5pt solid windowtext; border-bottom: .5pt solid windowtext; padding: 0px; background: white"> 
		实付金额&nbsp;</td>
	</tr>
	
	<%
	if(subStatList!=null&&subStatList.length==4){
		int xsCount=0,xfCount=0,xMax=0;
		double xsTotle=0,xfTotle=0;
		String xsStrT="",xfStrT="";
		if(subStatList[0]!=null) xsCount=subStatList[0].size();
		if(subStatList[1]!=null) xfCount=subStatList[1].size();
		if(xsCount>=xfCount){
			xMax=xsCount;
		}else{	
			xMax=xfCount;
		}
		
		for(int subX=0;subX<=xMax;subX++){
				Object[] xsObj=null,xfObj=null;
				if(subX < xsCount){
					xsObj=(Object[])subStatList[0].get(subX);
					xsTotle += (Double)xsObj[1];
					
				}else if(subX == xsCount){
					xsObj=new Object[2];
					xsObj[0]="";xsObj[1]="";
					xsStrT=Operate.toFix(xsTotle,2);
				}else{ 
					xsObj=new Object[2];
					xsObj[0]="";xsObj[1]="";
					xsStrT="";
				}
				if(subX < xfCount){
					xfObj=(Object[])subStatList[1].get(subX);
					xfTotle += (Double)xfObj[1];
				}else if(subX == xfCount){
					xfObj=new Object[2];
					xfObj[0]="";xfObj[1]="";
					xfStrT=Operate.toFix(xfTotle,2);
				}else{
					xfObj=new Object[2];
					xfObj[0]="";xfObj[1]="";
					xfStrT="";
				}
	%>
	
				<tr height="23" style="height:17.25pt">
					<td height="23" style="height: 17.25pt; font-family: 微软雅黑, sans-serif; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: .5pt solid windowtext; border-right: .5pt solid windowtext; border-top: medium none; border-bottom: .5pt solid windowtext; padding: 0px; background: white">
					<%=xsObj[0]%></td>
					<%if(xsStrT.equals("")){%>
					<td style="font-family: 楷体, monospace; text-align: right; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: medium none; border-right: 1.0pt solid windowtext; border-top: medium none; border-bottom: .5pt solid windowtext; padding: 0px; background: white">
					<%=xsObj[1]%> </td>
					<%}else{%>
					<td style="font-size: 14.0pt; font-family: 楷体, monospace; text-align: right; vertical-align: bottom; color: windowtext; font-weight: 400; font-style: normal; text-decoration: none; white-space: nowrap; border-left: 1.0pt solid black; border-right: 1.0pt solid black; border-top: medium none; border-bottom: 1.0pt solid black; padding: 0px">
					&nbsp;￥ <%=xsStrT%> </td>
					<%}%>
					<td style="vertical-align: bottom; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; font-family: 宋体; text-align: general; white-space: nowrap; border: medium none; padding: 0px">　</td>
					<td style="font-family: 微软雅黑, sans-serif; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: .5pt solid windowtext; border-right: .5pt solid windowtext; border-top: medium none; border-bottom: .5pt solid windowtext; padding: 0px; background: white">
					<%=xfObj[0]%></td>
					<%if(xfStrT.equals("")){%>
					<td style="font-family: 楷体, monospace; text-align: right; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: medium none; border-right: 1.0pt solid windowtext; border-top: medium none; border-bottom: .5pt solid windowtext; padding: 0px; background: white">
					<%=xfObj[1]%> </td>
					<%}else{%>
					<td style="font-size: 14.0pt; font-family: 楷体, monospace; text-align: right; vertical-align: bottom; color: windowtext; font-weight: 400; font-style: normal; text-decoration: none; white-space: nowrap; border-left: 1.0pt solid black; border-right: 1.0pt solid black; border-top: medium none; border-bottom: 1.0pt solid black; padding: 0px">
					&nbsp;￥ <%=xfStrT%> </td>
					<%}%>
				</tr>
	<%}%>
	

	
	
	<tr height="19" style="height:14.25pt">
		<td height="19" style="height: 14.25pt; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; font-family: 宋体; text-align: general; vertical-align: middle; white-space: nowrap; border: medium none; padding: 0px">　</td>
		<td style="color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; font-family: 宋体; text-align: general; vertical-align: middle; white-space: nowrap; border: medium none; padding: 0px">　</td>
		<td style="color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; font-family: 宋体; text-align: general; vertical-align: middle; white-space: nowrap; border: medium none; padding: 0px">　</td>
		<td style="color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; font-family: 宋体; text-align: general; vertical-align: middle; white-space: nowrap; border: medium none; padding: 0px">　</td>
		<td style="color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; font-family: 宋体; text-align: general; vertical-align: middle; white-space: nowrap; border: medium none; padding: 0px">　</td>
	</tr>
	<tr height="19" style="height:14.25pt">
		<td height="19" style="height: 14.25pt; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; font-family: 宋体; text-align: general; vertical-align: middle; white-space: nowrap; border: medium none; padding: 0px">　</td>
		<td style="color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; font-family: 宋体; text-align: general; vertical-align: middle; white-space: nowrap; border: medium none; padding: 0px">　</td>
		<td style="color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; font-family: 宋体; text-align: general; vertical-align: middle; white-space: nowrap; border: medium none; padding: 0px">　</td>
		<td style="color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; font-family: 宋体; text-align: general; vertical-align: middle; white-space: nowrap; border: medium none; padding: 0px">　</td>
		<td style="color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; font-family: 宋体; text-align: general; vertical-align: middle; white-space: nowrap; border: medium none; padding: 0px">　</td>
	</tr>
	
	
	<tr height="33" style="height:24.75pt">
		<td height="33" colspan="2" style="height: 24.75pt; font-size: 18.0pt; font-family: 微软雅黑, sans-serif; vertical-align: bottom; color: windowtext; font-weight: 400; font-style: normal; text-decoration: none; text-align: general; white-space: nowrap; border: medium none; padding: 0px">
		银行收入</td>
		<td style="vertical-align: bottom; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; font-family: 宋体; text-align: general; white-space: nowrap; border: medium none; padding: 0px">　</td>
		<td colspan="2" style="font-size: 18.0pt; font-family: 微软雅黑, sans-serif; vertical-align: bottom; color: windowtext; font-weight: 400; font-style: normal; text-decoration: none; text-align: general; white-space: nowrap; border: medium none; padding: 0px">
		银行支出</td>
	</tr>
	<tr height="23" style="height:17.25pt">
		<td height="23" style="height: 17.25pt; font-family: 微软雅黑, sans-serif; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border: .5pt solid windowtext; padding: 0px; background: white">
		科目名称</td>
		<td style="font-family: 微软雅黑, sans-serif; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: medium none; border-right: .5pt solid windowtext; border-top: .5pt solid windowtext; border-bottom: .5pt solid windowtext; padding: 0px; background: white">
		实付金额&nbsp;</td>
		<td style="vertical-align: bottom; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; font-family: 宋体; text-align: general; white-space: nowrap; border: medium none; padding: 0px">　</td>
		<td style="font-family: 微软雅黑, sans-serif; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border: .5pt solid windowtext; padding: 0px; background: white">
		科目名称</td>
		<td style="font-family: 微软雅黑, sans-serif; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: medium none; border-right: .5pt solid windowtext; border-top: .5pt solid windowtext; border-bottom: .5pt solid windowtext; padding: 0px; background: white">
		实付金额&nbsp;</td>
	</tr>
	
	
	
	<%
		int ysCount=0,yfCount=0,yMax=0;
		double ysTotle=0,yfTotle=0;
		String ysStrT="",yfStrT="";
		if(subStatList[2]!=null) ysCount=subStatList[2].size();
		if(subStatList[3]!=null) yfCount=subStatList[3].size();
		if(ysCount>=yfCount){
			yMax=ysCount;
		}else{	
			yMax=yfCount;
		}
	
		for(int subY=0;subY<=yMax;subY++){
				Object[] ysObj=null,yfObj=null;
				if(subY < ysCount){
					ysObj=(Object[])subStatList[2].get(subY);
					ysTotle += (Double)ysObj[1];
					
				}else if(subY == ysCount){
					ysObj=new Object[2];
					ysObj[0]="";ysObj[1]="";
					ysStrT=Operate.toFix(ysTotle,2);
				}else{ 
					ysObj=new Object[2];
					ysObj[0]="";ysObj[1]="";
					ysStrT="";
				}
				if(subY < yfCount){
					yfObj=(Object[])subStatList[3].get(subY);
					yfTotle += (Double)yfObj[1];
				}else if(subY == yfCount){
					yfObj=new Object[2];
					yfObj[0]="";yfObj[1]="";
					yfStrT=Operate.toFix(yfTotle,2);
				}else{
					yfObj=new Object[2];
					yfObj[0]="";yfObj[1]="";
					yfStrT="";
				}
	%>
	
				<tr height="23" style="height:17.25pt">
					<td height="23" style="height: 17.25pt; font-family: 微软雅黑, sans-serif; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: .5pt solid windowtext; border-right: .5pt solid windowtext; border-top: medium none; border-bottom: .5pt solid windowtext; padding: 0px; background: white">
					<%=ysObj[0]%></td>
					<%if(ysStrT.equals("")){%>
					<td style="font-family: 楷体, monospace; text-align: right; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: medium none; border-right: 1.0pt solid windowtext; border-top: medium none; border-bottom: .5pt solid windowtext; padding: 0px; background: white">
					<%=ysObj[1]%> </td>
					<%}else{%>
					<td style="font-size: 14.0pt; font-family: 楷体, monospace; text-align: right; vertical-align: bottom; color: windowtext; font-weight: 400; font-style: normal; text-decoration: none; white-space: nowrap; border-left: 1.0pt solid black; border-right: 1.0pt solid black; border-top: medium none; border-bottom: 1.0pt solid black; padding: 0px">
					&nbsp;￥ <%=ysStrT%> </td>
					<%}%>
					<td style="vertical-align: bottom; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; font-family: 宋体; text-align: general; white-space: nowrap; border: medium none; padding: 0px">　</td>
					<td style="font-family: 微软雅黑, sans-serif; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: .5pt solid windowtext; border-right: .5pt solid windowtext; border-top: medium none; border-bottom: .5pt solid windowtext; padding: 0px; background: white">
					<%=yfObj[0]%></td>
					<%if(yfStrT.equals("")){%>
					<td style="font-family: 楷体, monospace; text-align: right; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: medium none; border-right: 1.0pt solid windowtext; border-top: medium none; border-bottom: .5pt solid windowtext; padding: 0px; background: white">
					<%=yfObj[1]%> </td>
					<%}else{%>
					<td style="font-size: 14.0pt; font-family: 楷体, monospace; text-align: right; vertical-align: bottom; color: windowtext; font-weight: 400; font-style: normal; text-decoration: none; white-space: nowrap; border-left: 1.0pt solid black; border-right: 1.0pt solid black; border-top: medium none; border-bottom: 1.0pt solid black; padding: 0px">
					&nbsp;￥ <%=yfStrT%> </td>
					<%}%>
				</tr>
	<%}%>
	<%}%>
	
</table>


      	
		</td>
     </tr>
     <tr> 
      <td height="2" colspan="2" bgcolor="#ffffff"></td>
     </tr>
     <tr> 
      <td height="1" colspan="2" bgcolor="#677789"></td>
     </tr>
    </table>
   </div>
   <div align="center" id="Layer3" style="position:absolute; left:35; top:80; width:912px; height:390px; z-index:1; visibility: hidden;"> 
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="content12">
     <tr> 
      <td valign="bottom"> <table border="0" cellpadding="0" cellspacing="0">
        <tr> 
         <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','show','Layer2','','hide','Layer3','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="收支明细账"></td>        
         <td width="30"><input name="labelButton" type="button" class="button_g" onClick="MM_showHideLayers('Layer1','','hide','Layer2','','show','Layer3','','hide')" onMouseOver="highlightButton('button_b')" onMouseOut="highlightButton('button_g')" value="科目明细账"></td>
         <td width="30"><input name="labelButton" type="button" class="button_b" value="人员明细账"></td>
        </tr>
       </table></td>
     </tr>
     <tr> 
      <td height="2" colspan="2" bgcolor="#677789"></td>
     </tr>
     <tr> 
      <td height="6" colspan="2" bgcolor="#CECECE"></td>
     </tr>
     <tr> 
      <td valign="top" colspan="2">
     
     <table border="0" cellpadding="0" cellspacing="0" width="750" >
	<colgroup>
		<col width="150" style="width: 140pt">
		<col width="150" style="width: 100pt">
		<col width="150" style="width: 80pt">
		<col width="150" style="width: 140pt">
		<col width="150" style="width: 100pt">
	</colgroup>
	<tr height="33" style="height:24.75pt">
		<td height="33" style="height: 24.75pt; font-size: 18.0pt; font-family: 微软雅黑, sans-serif; vertical-align: bottom; color: windowtext; font-weight: 400; font-style: normal; text-decoration: none; text-align: general; white-space: nowrap; border: medium none; padding: 0px">
		现金支出</td>
		<td style="color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; font-family: 宋体; text-align: general; vertical-align: middle; white-space: nowrap; border: medium none; padding: 0px">　</td>
		<td style="color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; font-family: 宋体; text-align: general; vertical-align: middle; white-space: nowrap; border: medium none; padding: 0px">　</td>
		<td style="font-size: 18.0pt; font-family: 微软雅黑, sans-serif; vertical-align: bottom; color: windowtext; font-weight: 400; font-style: normal; text-decoration: none; text-align: general; white-space: nowrap; border: medium none; padding: 0px">
		现金收入</td>
		<td style="color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; font-family: 宋体; text-align: general; vertical-align: middle; white-space: nowrap; border: medium none; padding: 0px">　</td>
	</tr>
	<tr height="23" style="height:17.25pt">
		<td height="23" style="height: 17.25pt; font-family: 微软雅黑, sans-serif; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border: .5pt solid windowtext; padding: 0px; background: white">
		人员</td>
		<td style="font-family: 微软雅黑, sans-serif; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: medium none; border-right: .5pt solid windowtext; border-top: .5pt solid windowtext; border-bottom: .5pt solid windowtext; padding: 0px; background: white">
		金额&nbsp;</td>
		<td style="color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; font-family: 宋体; text-align: general; vertical-align: middle; white-space: nowrap; border: medium none; padding: 0px">　</td>
		<td style="font-family: 微软雅黑, sans-serif; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border: .5pt solid windowtext; padding: 0px; background: white">
		人员</td>
		<td style="font-family: 微软雅黑, sans-serif; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: medium none; border-right: .5pt solid windowtext; border-top: .5pt solid windowtext; border-bottom: .5pt solid windowtext; padding: 0px; background: white">
		金额&nbsp;</td>
	</tr>
	
	
	
	<%
	if(empStatList!=null&&empStatList.length==4){
		int xsCount=0,xfCount=0,xMax=0;
		double xsTotle=0,xfTotle=0;
		String xsStrT="",xfStrT="";
		if(empStatList[0]!=null) xsCount=empStatList[0].size();
		if(empStatList[1]!=null) xfCount=empStatList[1].size();
		if(xsCount>=xfCount){
			xMax=xsCount;
		}else{	
			xMax=xfCount;
		}

		for(int subX=0;subX<=xMax;subX++){
				Object[] xsObj=null,xfObj=null;
				if(subX < xsCount){
					xsObj=(Object[])empStatList[0].get(subX);
					xsTotle += (Double)xsObj[1];
					
				}else if(subX == xsCount){
					xsObj=new Object[2];
					xsObj[0]="";xsObj[1]="";
					xsStrT=Operate.toFix(xsTotle,2);
				}else{ 
					xsObj=new Object[2];
					xsObj[0]="";xsObj[1]="";
					xsStrT="";
				}
				if(subX < xfCount){
					xfObj=(Object[])empStatList[1].get(subX);
					xfTotle += (Double)xfObj[1];
				}else if(subX == xfCount){
					xfObj=new Object[2];
					xfObj[0]="";xfObj[1]="";
					xfStrT=Operate.toFix(xfTotle,2);
				}else{
					xfObj=new Object[2];
					xfObj[0]="";xfObj[1]="";
					xfStrT="";
				}
	%>
	
				<tr height="23" style="height:17.25pt">
					<td height="23" style="height: 17.25pt; font-family: 微软雅黑, sans-serif; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: .5pt solid windowtext; border-right: .5pt solid windowtext; border-top: medium none; border-bottom: .5pt solid windowtext; padding: 0px; background: white">
					<%=xsObj[0]%></td>
					<%if(xsStrT.equals("")){%>
					<td style="font-family: 楷体, monospace; text-align: right; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: medium none; border-right: 1.0pt solid windowtext; border-top: medium none; border-bottom: .5pt solid windowtext; padding: 0px; background: white">
					<%=xsObj[1]%> </td>
					<%}else{%>
					<td style="font-size: 14.0pt; font-family: 楷体, monospace; text-align: right; vertical-align: bottom; color: windowtext; font-weight: 400; font-style: normal; text-decoration: none; white-space: nowrap; border-left: 1.0pt solid black; border-right: 1.0pt solid black; border-top: medium none; border-bottom: 1.0pt solid black; padding: 0px">
					&nbsp;￥ <%=xsStrT%> </td>
					<%}%>
					<td style="vertical-align: bottom; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; font-family: 宋体; text-align: general; white-space: nowrap; border: medium none; padding: 0px">　</td>
					<td style="font-family: 微软雅黑, sans-serif; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: .5pt solid windowtext; border-right: .5pt solid windowtext; border-top: medium none; border-bottom: .5pt solid windowtext; padding: 0px; background: white">
					<%=xfObj[0]%></td>
					<%if(xfStrT.equals("")){%>
					<td style="font-family: 楷体, monospace; text-align: right; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: medium none; border-right: 1.0pt solid windowtext; border-top: medium none; border-bottom: .5pt solid windowtext; padding: 0px; background: white">
					<%=xfObj[1]%> </td>
					<%}else{%>
					<td style="font-size: 14.0pt; font-family: 楷体, monospace; text-align: right; vertical-align: bottom; color: windowtext; font-weight: 400; font-style: normal; text-decoration: none; white-space: nowrap; border-left: 1.0pt solid black; border-right: 1.0pt solid black; border-top: medium none; border-bottom: 1.0pt solid black; padding: 0px">
					&nbsp;￥ <%=xfStrT%> </td>
					<%}%>
				</tr>
	<%}%>
	
	
	<tr height="19" style="height:14.25pt">
		<td height="19" style="height: 14.25pt; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; font-family: 宋体; text-align: general; vertical-align: middle; white-space: nowrap; border: medium none; padding: 0px">　</td>
		<td style="color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; font-family: 宋体; text-align: general; vertical-align: middle; white-space: nowrap; border: medium none; padding: 0px">　</td>
		<td style="color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; font-family: 宋体; text-align: general; vertical-align: middle; white-space: nowrap; border: medium none; padding: 0px">　</td>
		<td style="color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; font-family: 宋体; text-align: general; vertical-align: middle; white-space: nowrap; border: medium none; padding: 0px">　</td>
		<td style="color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; font-family: 宋体; text-align: general; vertical-align: middle; white-space: nowrap; border: medium none; padding: 0px">　</td>
	</tr>
	<tr height="19" style="height:14.25pt">
		<td height="19" style="height: 14.25pt; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; font-family: 宋体; text-align: general; vertical-align: middle; white-space: nowrap; border: medium none; padding: 0px">　</td>
		<td style="color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; font-family: 宋体; text-align: general; vertical-align: middle; white-space: nowrap; border: medium none; padding: 0px">　</td>
		<td style="color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; font-family: 宋体; text-align: general; vertical-align: middle; white-space: nowrap; border: medium none; padding: 0px">　</td>
		<td style="color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; font-family: 宋体; text-align: general; vertical-align: middle; white-space: nowrap; border: medium none; padding: 0px">　</td>
		<td style="color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; font-family: 宋体; text-align: general; vertical-align: middle; white-space: nowrap; border: medium none; padding: 0px">　</td>
	</tr>
	
	
	<tr height="33" style="height:24.75pt">
		<td height="33" style="height: 24.75pt; font-size: 18.0pt; font-family: 微软雅黑, sans-serif; vertical-align: bottom; color: windowtext; font-weight: 400; font-style: normal; text-decoration: none; text-align: general; white-space: nowrap; border: medium none; padding: 0px">
		银行支出</td>
		<td style="color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; font-family: 宋体; text-align: general; vertical-align: middle; white-space: nowrap; border: medium none; padding: 0px">　</td>
		<td style="color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; font-family: 宋体; text-align: general; vertical-align: middle; white-space: nowrap; border: medium none; padding: 0px">　</td>
		<td style="font-size: 18.0pt; font-family: 微软雅黑, sans-serif; vertical-align: bottom; color: windowtext; font-weight: 400; font-style: normal; text-decoration: none; text-align: general; white-space: nowrap; border: medium none; padding: 0px">
		银行收入</td>
		<td style="color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; font-family: 宋体; text-align: general; vertical-align: middle; white-space: nowrap; border: medium none; padding: 0px">　</td>
	</tr>
	<tr height="23" style="height:17.25pt">
		<td height="23" style="height: 17.25pt; font-family: 微软雅黑, sans-serif; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border: .5pt solid windowtext; padding: 0px; background: white">
		客户</td>
		<td style="font-family: 微软雅黑, sans-serif; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: medium none; border-right: .5pt solid windowtext; border-top: .5pt solid windowtext; border-bottom: .5pt solid windowtext; padding: 0px; background: white">
		金额&nbsp;</td>
		<td style="color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; font-family: 宋体; text-align: general; vertical-align: middle; white-space: nowrap; border: medium none; padding: 0px">　</td>
		<td style="font-family: 微软雅黑, sans-serif; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border: .5pt solid windowtext; padding: 0px; background: white">
		客户</td>
		<td style="font-family: 微软雅黑, sans-serif; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: medium none; border-right: .5pt solid windowtext; border-top: .5pt solid windowtext; border-bottom: .5pt solid windowtext; padding: 0px; background: white">
		金额&nbsp;</td>
	</tr>
	
	
	
	<%
		int ysCount=0,yfCount=0,yMax=0;
		double ysTotle=0,yfTotle=0;
		String ysStrT="",yfStrT="";
		if(empStatList[2]!=null) ysCount=empStatList[2].size();
		if(empStatList[3]!=null) yfCount=empStatList[3].size();
		if(ysCount>=yfCount){
			yMax=ysCount;
		}else{	
			yMax=yfCount;
		}
	
		for(int subY=0;subY<=yMax;subY++){
				Object[] ysObj=null,yfObj=null;
				if(subY < ysCount){
					ysObj=(Object[])empStatList[2].get(subY);
					ysTotle += (Double)ysObj[1];
					
				}else if(subY == ysCount){
					ysObj=new Object[2];
					ysObj[0]="";ysObj[1]="";
					ysStrT=Operate.toFix(ysTotle,2);
				}else{ 
					ysObj=new Object[2];
					ysObj[0]="";ysObj[1]="";
					ysStrT="";
				}
				if(subY < yfCount){
					yfObj=(Object[])empStatList[3].get(subY);
					yfTotle += (Double)yfObj[1];
				}else if(subY == yfCount){
					yfObj=new Object[2];
					yfObj[0]="";yfObj[1]="";
					yfStrT=Operate.toFix(yfTotle,2);
				}else{
					yfObj=new Object[2];
					yfObj[0]="";yfObj[1]="";
					yfStrT="";
				}
	%>
	
				<tr height="23" style="height:17.25pt">
					<td height="23" style="height: 17.25pt; font-family: 微软雅黑, sans-serif; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: .5pt solid windowtext; border-right: .5pt solid windowtext; border-top: medium none; border-bottom: .5pt solid windowtext; padding: 0px; background: white">
					<%=ysObj[0]%></td>
					<%if(ysStrT.equals("")){%>
					<td style="font-family: 楷体, monospace; text-align: right; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: medium none; border-right: 1.0pt solid windowtext; border-top: medium none; border-bottom: .5pt solid windowtext; padding: 0px; background: white">
					<%=ysObj[1]%> </td>
					<%}else{%>
					<td style="font-size: 14.0pt; font-family: 楷体, monospace; text-align: right; vertical-align: bottom; color: windowtext; font-weight: 400; font-style: normal; text-decoration: none; white-space: nowrap; border-left: 1.0pt solid black; border-right: 1.0pt solid black; border-top: medium none; border-bottom: 1.0pt solid black; padding: 0px">
					&nbsp;￥ <%=ysStrT%> </td>
					<%}%>
					<td style="vertical-align: bottom; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; font-family: 宋体; text-align: general; white-space: nowrap; border: medium none; padding: 0px">　</td>
					<td style="font-family: 微软雅黑, sans-serif; text-align: center; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: .5pt solid windowtext; border-right: .5pt solid windowtext; border-top: medium none; border-bottom: .5pt solid windowtext; padding: 0px; background: white">
					<%=yfObj[0]%></td>
					<%if(yfStrT.equals("")){%>
					<td style="font-family: 楷体, monospace; text-align: right; color: windowtext; font-size: 12.0pt; font-weight: 400; font-style: normal; text-decoration: none; vertical-align: middle; white-space: nowrap; border-left: medium none; border-right: 1.0pt solid windowtext; border-top: medium none; border-bottom: .5pt solid windowtext; padding: 0px; background: white">
					<%=yfObj[1]%> </td>
					<%}else{%>
					<td style="font-size: 14.0pt; font-family: 楷体, monospace; text-align: right; vertical-align: bottom; color: windowtext; font-weight: 400; font-style: normal; text-decoration: none; white-space: nowrap; border-left: 1.0pt solid black; border-right: 1.0pt solid black; border-top: medium none; border-bottom: 1.0pt solid black; padding: 0px">
					&nbsp;￥ <%=yfStrT%> </td>
					<%}%>
				</tr>
	<%}%>
	<%}%>
	
	
</table>


       
       
       
       </td>
     </tr>
     <tr> 
      <td height="2" colspan="2" bgcolor="#ffffff"></td>
     </tr>
     <tr> 
      <td height="1" colspan="2" bgcolor="#677789"></td>
     </tr>
    </table>
   </div>
   
   <br>
   <br>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="content12">
	    <tr>
	       <td width="30%">费用管理 &gt; 费用统计 &gt; 费用明细</td>
	       <td>
	     			<font color="blue"><%=accountMonth%>&nbsp;费用明细</font>
	       </td>
	    </tr>
	</table>

   <br>
  </td>
   </tr>
  </table>

</body>


<%
}catch(Exception e){
	e.printStackTrace();
}%>
</html>