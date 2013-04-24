<%@ page contentType="text/html; charset=GBK" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>left1</title>
<link href="css/styles.css" rel="stylesheet" type="text/css" />
<link href="css/style.css" rel="stylesheet" type="text/css" />

</head>

<body background="googleImg/nav_back.gif">



<table border="0" cellpadding="0" cellspacing="0" width="100%" height="96%" >
<tr>
    <td nowrap="nowrap" valign="top" width="160">
<p><br>

 <img src='googleImg/icon/rc1.png' align='absmiddle'>&nbsp;&nbsp;<b>库存信息</b><br>
 &nbsp;&nbsp;&nbsp;&nbsp;<img src='googleImg/icon/rc.png' align='absmiddle'>&nbsp;&nbsp;<a href='stockInfoListAction.do?method=stockInfoList' target='content'>库存查询</a><br ><br >
 
 <img src='googleImg/icon/rc1.png' align='absmiddle'>&nbsp;&nbsp;<b>入库信息</b><br >
 &nbsp;&nbsp;&nbsp;&nbsp;<img src='googleImg/icon/rc.png' align='absmiddle'>&nbsp;&nbsp;<a href='stock/saleOrderIn.html' target='content'>收货入库</a><br >
 &nbsp;&nbsp;&nbsp;&nbsp;<img src='googleImg/icon/rc.png' align='absmiddle'>&nbsp;&nbsp;<a href='stockFlowAction.do?method=stockInFlowList' target='content'>入库流水清单</a><br >
 <br >
 <img src='googleImg/icon/rc1.png' align='absmiddle'>&nbsp;&nbsp;<b>出库信息</b><br >
 &nbsp;&nbsp;&nbsp;&nbsp;<img src='googleImg/icon/rc.png' align='absmiddle'>&nbsp;&nbsp;<a href='stock/saleOut.html' target='content'>销售领用出库</a><br >
 &nbsp;&nbsp;&nbsp;&nbsp;<img src='googleImg/icon/rc.png' align='absmiddle'>&nbsp;&nbsp;<a href='stockOutOperateAction.do?method=stockAdjustOutList' target='content'>库存调整出库</a><br >
 &nbsp;&nbsp;&nbsp;&nbsp;<img src='googleImg/icon/rc.png' align='absmiddle'>&nbsp;&nbsp;<a href='stockFlowAction.do?method=stockOutFlowList' target='content'>出库流水清单</a><br >
 <br >

  <img src='googleImg/icon/rc1.png' align='absmiddle'>&nbsp;&nbsp;<b>库存盘点</b><br >
 &nbsp;&nbsp;&nbsp;&nbsp;<img src='googleImg/icon/rc.png' align='absmiddle'>&nbsp;&nbsp;<a href='stock/stockTakeInit.html' target='content'>盘点操作</a><br >
 &nbsp;&nbsp;&nbsp;&nbsp;<img src='googleImg/icon/rc.png' align='absmiddle'>&nbsp;&nbsp;<a href='stock/stockTakeList.html' target='content'>盘点报表</a><br >
 <br >

 <img src='googleImg/icon/rc1.png' align='absmiddle'>&nbsp;&nbsp;<b>零件信息</b><br >
  &nbsp;&nbsp;&nbsp;&nbsp;<img src='googleImg/icon/rc.png' align='absmiddle'>&nbsp;&nbsp;<a href='partInfoAction.do?method=partInfoList' target='content'>零件维护</a><br >
  &nbsp;&nbsp;&nbsp;&nbsp;<img src='googleImg/icon/rc.png' align='absmiddle'>&nbsp;&nbsp;<a href='stockInfoListAction.do?method=stockInitial' target='content'>库存初始化</a><br >

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
<SCRIPT LANGUAGE="JavaScript">
<!--
function logout(){
	window.parent.location = "login.jsp";

}
//-->
</SCRIPT>