<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>

<html lang="zh-CN" dir="ltr"> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<style type=text/css>
<!--
body,td,div,p,a,font,span {font-family: arial,sans-serif}
body {margin-top:2}

.c {width: 4; height: 4}

.bubble {background-color:#C3D9FF}

.tl {padding: 0; width: 4; text-align: left; vertical-align: top}
.tr {padding: 0; width: 4; text-align: right; vertical-align: top}
.bl {padding: 0; width: 4; text-align: left; vertical-align: bottom}
.br {padding: 0; width: 4; text-align: right; vertical-align: bottom}

.caption {color:#000000; white-space:nowrap; background:#E8EEFA; text-align:center}

.form-noindent {background-color: #ffffff; border: #C3D9FF 1px solid}

.feature-image {padding: 15 0 0 0; vertical-align: top; text-align: right; }
.feature-description {padding: 15 0 0 10; vertical-align: top; text-align: left; }

// -->
</style>

</head>
<%
	Calendar cd = Calendar.getInstance();
	int[] orderPartNum = (int[])request.getAttribute("orderPartNum");
	int[] stockPartNum = (int[])request.getAttribute("stockPartNum");
	int[] stockDiffNum = (int[])request.getAttribute("stockDiffNum");

		Date date = new Date(); 
		SimpleDateFormat sdf=new SimpleDateFormat("E");
		String strWeek=sdf.format(date);	

		String userName=(String)session.getAttribute("userName");
%>
<body bgcolor=#ffffff link=#0000FF vlink=#0000FF >
<form method="post" name="form1">
<input name="serverYear" type="hidden" value="<%=cd.get(Calendar.YEAR)%>">
<input name="serverMonth" type="hidden" value="<%=cd.get(Calendar.MONTH)+1%>">
<input name="serverDate" type="hidden" value="<%=cd.get(Calendar.DATE)%>">
<input name="serverHour" type="hidden" value="<%=cd.get(Calendar.AM_PM)==1?cd.get(Calendar.HOUR)+12:cd.get(Calendar.HOUR)%>">
<input name="serverMinute" type="hidden" value="<%=cd.get(Calendar.MINUTE)%>">
<input name="serverSecond" type="hidden" value="<%=cd.get(Calendar.SECOND)%>">
<br>

<table width=94%  align=center cellpadding=5 cellspacing=1>
  <tr>
  <td valign=top style="text-align:left"><b>KaoMing    库存管理系统</b>
  <td valign=top>&nbsp;
  </tr>
  <tr>
  <td width=75% valign=top>
  <p style="margin-bottom: 0;text-align:left">&nbsp;</p>

<table border="0" cellpadding="0" cellspacing="0" width="90%"><tbody>
  
  <tr>
  <td class="feature-image"><img src="googleImg/cell.gif"></td>
  <td class="feature-description">
  <font size=-1><b>客户订单</b><p>
  询价零件:<b> <%=orderPartNum[0]%> </b><br>核算零件:<b> 4 </b> <br>待领取零件:<b> <%=orderPartNum[1]%> </b>
  </font>
  </td>
  </tr>
  <tr>
  <td class="feature-image"><img src="googleImg/storage.gif"></td>
  <td class="feature-description">
  <font size=-1><b>库存零件</b><p>
  库存零件:<b> <%=stockDiffNum[0]%> </b> <br>
  </font>
  </td>
  </tr>
  <tr>
  <td class="feature-image"><img src="googleImg/spam_new.gif"></td>
  <td class="feature-description">
  <font size=-1 ><b>库存补充提示</b><p>
  <font  title="B1134225233103">近接开关9982-2000:<b> 4 </b> </font>  <br>
  <font  title="B3023MM9118WF">进给倍率开关02-H:<b> 7 </b> </font>  <br>
  <font color="red" title="B708504Q0NS01">左右夹爪BT50:<b> 0 </b> </font>  <br>
  </font>
  </td>
  </tr>
  </noscript>
</tbody></table>


  </td>
  <td valign=top>
  <!-- login box -->

  <!-- end login box -->
  <br>
  <!-- links box (below login box) -->
  <table class=form-noindent cellpadding=0 width=100% bgcolor=#E8EEFA id=links>
  <tr bgcolor=#E8EEFA>
  <td valign=top>
  <br>
  <div align=center style="margin:10 0">
  <font size=+0 color=blue>
  <b>欢迎  <%=userName%></b>
  <br>
  <font size="-1">
  <div id="clock"  > </div>
   <%=strWeek%>
  </font>
  <br>
  
  <br>
  </font>
  </div>
  </noscript>
  </td></tr>
  <tr class="feature-image"><img src="images/main.jpg" ></tr>
  </table>
  <!-- end links box (below login box) -->

  </noscript>

</table>
<br>
<table width=95% align=center cellpadding=3 cellspacing=0 bgcolor=#C3D9FF style=margin-bottom:5>
  <tr>
  <td class=bubble rowspan=2 style=text-align:left>
  <div align=center>
  <font size=-1 color=#666666>&copy;2010 KaoMing 
  
  </font>
  </div>
  </td>
  </tr>

</table>
</form>
</body>


<script language="JavaScript" type="text/JavaScript">
 
//动态显示时间
var year =document.form1.serverYear.value;
var month =document.form1.serverMonth.value;
var date =document.form1.serverDate.value;
var hrs =  document.form1.serverHour.value;
var min =document.form1.serverMinute.value;
var sec =document.form1.serverSecond.value;
var nowww = new Date();
var secc = sec - nowww.getSeconds();
var ff2 = 1 ;
var ff3 = 1 ;
var ff4 = 0 ;

function THINPIGServerTime(){
var noww = new Date();
sec = (noww.getSeconds() + secc)%60;
if(sec<0)sec=60+sec;
if(ff4==0 && (sec == 00 || sec == 60)){sec = 0;min++;ff3=1;ff4=1;}
if(sec != 00 && sec != 60)ff4=0;//使在0秒或60秒一秒钟校正多次不出现分钟增加多次的现象
if(min == 60){min=00;hrs++;ff2=1}
if(hrs == 24){hrs=0;}
if(sec==0 && min==0 && hrs==0){window.location=("frame_content.jsp");}//新的一天的时候刷新页面主要是重新读入日期
if (hrs<=9&&ff2==1){ff2=0; hrs="0"+hrs;}
if (sec<=9) sec="0"+sec;
if (min<=9&&ff3==1) {ff3=0; min="0"+min;}
document.all.clock.innerHTML = year+"-"+month+"-"+date+" "+hrs+":"+min+":"+sec;
}
setInterval("THINPIGServerTime()",100);//一秒钟自校对10次消除跳秒现象

</script>
</html>
