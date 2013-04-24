



<html>
<head>
<title>newsis</title>
<link href="css/style.css" rel="stylesheet" type="text/css">
<script language=javascript src="js/common.js"></script>
<SCRIPT LANGUAGE="JavaScript">

function hrefContent(){
	
			var firstHrefLinkObj = getChildByTagNameTimes(rightDIV,"A",1);
			if(firstHrefLinkObj == null){
				alert("您没有查看此模块的权限！");
			}else{
				window.parent.content.location = firstHrefLinkObj;
			}
	
}

function logout(){
	window.parent.location = "login.jsp";

}

</SCRIPT>
</head>



<body bgcolor="#ffffff" background="images/nav_back.gif" leftmargin="0" topmargin="0" rightmargin="0" bottommargin="0" marginwidth="0" marginheight="0" onload="hrefContent()">
<div align="right" id="rightDIV"> 


  <table width="92%" border="0" cellpadding="2" cellspacing="1" class="content12">
    <tr> 
      <td>&nbsp;</td>
    </tr>
    
<tr><td class='content14_bold'><img src='images/i_point1.gif' width='12' height='13' align='absmiddle'> 
&nbsp;&nbsp;零件销售</td></tr>
<tr> <td height='1' bgcolor='#CCCCCC'></td></tr>

<tr> <td> <img src='images/i_tran.gif' width='30' height='8' align='absmiddle'>
<img src='images/i_arrow4.gif' width='8' height='7' align='absmiddle'>
&nbsp;&nbsp;<a href='saleInfoAction.do?method=saleRegistert&left=1' target='content'>向台询价</a></td> </tr>
<tr> <td background='images/i_line.gif' height='1'></td> </tr>

<tr> <td> <img src='images/i_tran.gif' width='30' height='8' align='absmiddle'>
<img src='images/i_arrow4.gif' width='8' height='7' align='absmiddle'>
&nbsp;&nbsp;<a href='saleInfoAction.do?method=saleAskConfirmList&queryFlag=N&left=1' target='content'>询价回复</a></td> </tr>
<tr> <td background='images/i_line.gif' height='1'></td> </tr>
<tr> <td> <img src='images/i_tran.gif' width='30' height='8' align='absmiddle'>
<img src='images/i_arrow4.gif' width='8' height='7' align='absmiddle'>
&nbsp;&nbsp;<a href='saleInfoAction.do?method=saleCheckList&queryFlag=N&left=1' target='content'>报价核算</a></td> </tr>
<tr> <td background='images/i_line.gif' height='1'></td> </tr>
<tr> <td> <img src='images/i_tran.gif' width='30' height='8' align='absmiddle'>
<img src='images/i_arrow4.gif' width='8' height='7' align='absmiddle'>
&nbsp;&nbsp;<a href='saleInfoAction.do?method=saleInfoList&queryFlag=N&saleStatus=H&left=1' target='content'>客户订购查询</a></td> </tr>
<tr> <td background='images/i_line.gif' height='1'></td> </tr>
<tr><td class='content14_bold'><img src='images/i_point1.gif' width='12' height='13' align='absmiddle'> 
&nbsp;&nbsp;订购信息</td></tr>
<tr> <td height='1' bgcolor='#CCCCCC'></td></tr>
<tr> <td> <img src='images/i_tran.gif' width='30' height='8' align='absmiddle'>
<img src='images/i_arrow4.gif' width='8' height='7' align='absmiddle'>
&nbsp;&nbsp;<a href='partPoAction.do?method=planList&queryFlag=N&left=1' target='content'>销售订购</a></td> </tr>
<tr> <td background='images/i_line.gif' height='1'></td> </tr>
<tr> <td> <img src='images/i_tran.gif' width='30' height='8' align='absmiddle'>
<img src='images/i_arrow4.gif' width='8' height='7' align='absmiddle'>
&nbsp;&nbsp;<a href='partPoAction.do?method=manualPlanList&left=1' target='content'>保内订购</a></td> </tr>
<tr> <td background='images/i_line.gif' height='1'></td> </tr>
<tr> <td> <img src='images/i_tran.gif' width='30' height='8' align='absmiddle'>
<img src='images/i_arrow4.gif' width='8' height='7' align='absmiddle'>
&nbsp;&nbsp;<a href='partPoAction.do?method=poList&queryFlag=N&left=1' target='content'>零件订购清单</a></td> </tr>
<tr> <td background='images/i_line.gif' height='1'></td> </tr>
<tr><td class='content14_bold'><img src='images/i_point1.gif' width='12' height='13' align='absmiddle'> 
&nbsp;&nbsp;零件审核</td></tr>
<tr> <td height='1' bgcolor='#CCCCCC'></td></tr>
<tr> <td> <img src='images/i_tran.gif' width='30' height='8' align='absmiddle'>
<img src='images/i_arrow4.gif' width='8' height='7' align='absmiddle'>
&nbsp;&nbsp;<a href='saleInfoAction.do?method=consignApproveList&queryFlag=N&left=1' target='content'>发货审批</a></td> </tr>
<tr> <td background='images/i_line.gif' height='1'></td> </tr>
<tr><td class='content14_bold'><img src='images/i_point1.gif' width='12' height='13' align='absmiddle'> 
&nbsp;&nbsp;<a href='customerInfoAction.do?method=customerList&queryFlag=N&left=1' target='content'>客户信息维护</a></td></tr>
<tr> <td height='1' bgcolor='#CCCCCC'></td></tr>


    
 <br >
 
</p>
<table bgcolor="#3399cc" border="0" cellpadding="5" cellspacing="0">
<tr>
<td bgcolor="#e8f4f7">
<img src='googleImg/icon/aim.gif' align='absmiddle'>&nbsp;<a href='javascript:logout()'  ><b>退出登录</b></a><br>
<br >
</td>
</tr>
</table>

</td>
	<td id="dots" width="10"></td>
	

</table>
</body>
</html>

