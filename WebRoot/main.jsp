<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="com.dne.sie.common.tools.DicInit"%>

<html>
<head>

<title>kaoming</title><SCRIPT language="JScript.Encode" src="js/screen.js"></SCRIPT>

<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link href="css/style.css" rel="stylesheet" type="text/css">
<script language=javascript src="<%= request.getContextPath()%>/js/ajax.js"></script>
<script language=javascript src="js/common.js"></script>
<style type="text/css">
<!--
.buttontask {
	font-family: "Arial", "Helvetica", "sans-serif";
	font-size: 12px;
	color: #333333;
	background-color: #5F6592;
	background-image: url(images/task.gif);
	background-repeat: no-repeat;
	vertical-align: baseline;
	margin: 0px;
	padding: 45px 0px 0px;
	height: 68px;
	width: 68px;
	border: 0px none;
	cursor: hand;
	
}
-->
</style>

</head>

<%
try{
	Calendar c = Calendar.getInstance();
	int[] orderPartNum = (int[])request.getAttribute("orderPartNum");
	int[] stockPartNum = (int[])request.getAttribute("stockPartNum");
	//int[] stockDiffNum = (int[])request.getAttribute("stockDiffNum");
	int[] repairNum = (int[])request.getAttribute("repairNum");
	int[] dipatchRemindNum = (int[])request.getAttribute("dipatchRemindNum");
	int[] logisticApproveNum = (int[])request.getAttribute("logisticApproveNum");
	
	
	
	String partSaleNos = (String)request.getAttribute("partSaleNos") == null?"":(String)request.getAttribute("partSaleNos");
 	String stockSupplyParts = (String)request.getAttribute("stockSupplyParts") == null?"":(String)request.getAttribute("stockSupplyParts");
 	String stockSupplyPartNames = (String)request.getAttribute("stockSupplyPartNames") == null?"":(String)request.getAttribute("stockSupplyPartNames");
 	
 	List<String> pageHTML = DicInit.pageHTML;
 	String scrollTitle="";
 	if(pageHTML!=null&&pageHTML.size()>=1){
 		scrollTitle = pageHTML.get(0);
 		scrollTitle = scrollTitle.substring(scrollTitle.lastIndexOf("\">")+2,scrollTitle.lastIndexOf("</a>"));
 	}

%>

<body bgcolor="#ffffff" leftmargin="0" topmargin="0" rightmargin="0" bottommargin="0" marginwidth="0" marginheight="0" >
<form method="post" name="form1">
<input name="repairAllNo" type="hidden">
<input name="serverYear" type="hidden" value="<%=c.get(Calendar.YEAR)%>">
<input name="serverMonth" type="hidden" value="<%=c.get(Calendar.MONTH)+1%>">
<input name="serverDate" type="hidden" value="<%=c.get(Calendar.DATE)%>">
<input name="serverHour" type="hidden" value="<%=c.get(Calendar.AM_PM)==1?c.get(Calendar.HOUR)+12:c.get(Calendar.HOUR)%>">
<input name="serverMinute" type="hidden" value="<%=c.get(Calendar.MINUTE)%>">
<input name="serverSecond" type="hidden" value="<%=c.get(Calendar.SECOND)%>">
<input name="filePath" type="hidden">
<input name="fileName" type="hidden">

<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" class="content12">
 <tr> 
    <td height="100" background="images/index_r2_c1.jpg">&nbsp;</td>
  </tr>
              <tr>
                <td valign="top"> <br>
                    <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="683" height="100%" valign="top"> <table border="0" cellpadding="0" cellspacing="0" class="content12">
              <tr> 
                <td width="683" height="32" background="images/index_r3_c1.jpg" class="content12"><marquee width="98%" direction="left" scrollamount="1" scrolldelay="50" onMouseOut="this.start()" onMouseOver="this.stop()">
                  <span class="content14"> 公告通知：</span><a href="javascript:viewBulletin()"><%=scrollTitle %></a> </marquee></td>
              </tr>
              <tr> 
                <td valign="top"> <table width="100%" border="0" cellpadding="0" cellspacing="16" class="content12">
                    <tr> 
                      <td height="120" valign="top"><table width="100%" border="0" cellpadding="5" cellspacing="0" class="content12">
                          <tr> 
                            <td height="34" valign="bottom" background="images/tit_dbsy.gif"> 
                              　</td>
                          </tr>
                          <tr> 
                            <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td width="120"><div align="center"><img src="images/pic_dbsy.gif" width="108" height="108"></div></td>
                                  <td><table width="100%" border="0" cellspacing="0" cellpadding="2">
                                  	<tr><td colspan=2 class="content12"><strong>客户订单</strong></td></tr>
									  									<tr> 
                                        <td width="20"><div align="center"><img src="images/i_arrow.gif"></div></td>
                                        <td class="content12">
																					询价回复:<b> <%=orderPartNum[0]%></b> <br>
                                        </td>
                                      </tr>
                                      <tr> 
                                        <td width="20"><div align="center"><img src="images/i_arrow.gif"></div></td>
                                        <td class="content12">
																					报价核算:<b> <%=orderPartNum[1]%></b> <br>
                                        </td>
                                      </tr>
                                      <tr> 
                                        <td width="20"><div align="center"><img src="images/i_arrow.gif"></div></td>
                                        <td class="content12">
																					本月销售单:<b> <%=orderPartNum[2]%></b> <br>
                                        </td>
                                      </tr>
                                    </table></td>
                                  <td><table width="100%" border="0" cellspacing="0" cellpadding="2">
                                  <tr><td colspan=2 class="content12"><strong>库存零件</strong></td></tr>
									  									<tr> 
                                        <td width="20"><div align="center"><img src="images/i_arrow.gif"></div></td>
                                        <td class="content12">
																					结存零件:<b> <%=stockPartNum[0]%> </b> <br>
                                        </td>
                                      </tr>
                                      <tr> 
                                        <td width="20"><div align="center"><img src="images/i_arrow.gif"></div></td>
                                        <td class="content12">
																					待出库零件:<b> <%=stockPartNum[1]%> </b> <br>
                                        </td>
                                      </tr>
                                      <tr> 
                                        <td width="20"><div align="center"><img src="images/i_arrow.gif"></div></td>
                                        <td class="content12">
																					订购在途零件:<b><%=stockPartNum[2]%> </b> <br>
                                        </td>
                                      </tr>
                                    </table></td>
                                    <td><table width="100%" border="0" cellspacing="0" cellpadding="2">
                                  <tr><td colspan=2 class="content12"><strong>维修记录</strong></td></tr>
									  									<tr> 
                                        <td width="20"><div align="center"><img src="images/i_arrow.gif"></div></td>
                                        <td class="content12">
																					电话诊断中:<b> <%=repairNum[0]%> </b> <br>
                                        </td>
                                      </tr>
                                      <tr> 
                                        <td width="20"><div align="center"><img src="images/i_arrow.gif"></div></td>
                                        <td class="content12">
																					在修数:<b> <%=repairNum[1]%> </b> <br>
                                        </td>
                                      </tr>
                                      <tr> 
                                        <td width="20"><div align="center"><img src="images/i_arrow.gif"></div></td>
                                        <td class="content12">
																					不修理:<b> <%=repairNum[2]%> </b> <br>
                                        </td>
                                      </tr>
                                    </table></td>
                                    <td><table width="100%" border="0" cellspacing="0" cellpadding="2">
                                  <tr><td colspan=2 class="content12"><strong>工作提醒</strong></td></tr>
									  	<tr> 
                                        <td width="20"><div align="center"><img src="images/i_arrow.gif"></div></td>
                                        <td class="content12">
														<%if(dipatchRemindNum[0]>0){ %>
														 <font color="red"> 安调派工：<b><%=dipatchRemindNum[0]%></b></font> 
														 <%}else{ %>
														 安调派工：<b><%=dipatchRemindNum[0]%></b>
														 <%} %> <br>
                                        </td>
                                      </tr>
                                      <tr> 
                                        <td width="20"><div align="center"><img src="images/i_arrow.gif"></div></td>
                                        <td class="content12">
														
														<%if(dipatchRemindNum[1]>0){ %>
														 <font color="red"> 检测派工：<b><%=dipatchRemindNum[1]%></b></font> 
														 <%}else{ %>
														 检测派工：<b><%=dipatchRemindNum[1]%></b>
														 <%} %>
												  <br>
                                        </td>
                                      </tr>
                                      <tr> 
                                        <td width="20"><div align="center"><img src="images/i_arrow.gif"></div></td>
                                        <td class="content12">
														
														<%if(logisticApproveNum[0]>0){ %>
														 <font color="red">物流发货同意：<b><%=logisticApproveNum[0]%></b></font> 
														 <%}else{ %>
														 物流发货同意：<b><%=logisticApproveNum[0]%></b>
														 <%} %>
												  <br>
                                        </td>
                                      </tr>
                                      <tr> 
                                        <td width="20"><div align="center"><img src="images/i_arrow.gif"></div></td>
                                        <td class="content12">
														
														<%if(logisticApproveNum[1]>0){ %>
														 <font color="red">物流发货拒绝：<b><%=logisticApproveNum[1]%></b></font> 
														 <%}else{ %>
														 物流发货拒绝：<b><%=logisticApproveNum[1]%></b>
														 <%} %>
												  <br>
                                        </td>
                                      </tr>
                                    </table></td>
                                    
                                </tr>
                              </table></td>
                          </tr>
                          <tr >   
                            <td colspan="2" height="1"background="images/line.gif"></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr> 
                      <td height="154" valign="top"> 
                        <table width="100%" border="0" cellpadding="5" cellspacing="0" class="content12">
                          <tr> 
                            <td height="34" valign="bottom" background="images/tit_zytx.gif"> 
                              　　</td>
                          </tr>
                          <tr> 
                            <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td width="120"><div align="center"><img src="images/pic_zytx.gif" width="108" height="108"></div></td>
                                  <td><table width="100%" border="0" cellspacing="0" cellpadding="2">
                                      <tr> 
                                        <td width="20"><div align="center"><img src="images/i_arrow.gif"></div></td>
                                        <td class="content12" id="np">
                                        	待领零件销售单：<br><b> <%=partSaleNos %> </b> </font>
                                        </td>
                                      </tr>
																	  </table>
																 </td>
                                  <td><table width="100%" border="0" cellspacing="0" cellpadding="2">
                                      <tr> 
                                        <td width="20"><div align="center"><img src="images/i_arrow.gif"></div></td>
                                        <td class="content12" id="npo">
                                        	库存补充提示：<br><font  title="<%=stockSupplyPartNames %>"><b><%=stockSupplyParts %></b></font>
                                        </td>
                                      </tr>
                                    </table></td>
                                </tr>
                              </table></td>
                          </tr>
                          <tr > 
                            <td colspan="2" height="1"background="images/line.gif"></td>
                          </tr>
                        </table></td>
                    </tr>
                  </table></td>
              </tr>
            </table>
           </td>
          <td width="7" rowspan="2" valign="top" background="images/bg_right_2.gif"><img src="images/bg_right_1.gif" width="7" height="247"></td>
          <td width="330" rowspan="2" valign="top" background="images/table_back.gif"> 
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
              <tr> 
                <td valign="top"> <div align="center"><img src="images/blank.gif" width="329" height="1"><br>
                    <table width="330" border="0" cellspacing="0" cellpadding="2">
                      <tr> 
                        <td class="content_yellow14">&nbsp;<%=session.getAttribute("userName")%>&nbsp;您好!&nbsp;&nbsp;</td>
                      </tr>
                      <tr> 
                        <td class="content12"><div id="clock"  > </div></td>
                      </tr>
                    </table>
                    <br>
                    <table width="330" height="300" border="0" cellspacing="0" cellpadding="0" >
                      <tr> 
                        <td><img src="images/i_news.gif" width="268" height="42"></td>
                      </tr>
                      <tr> 
                        <td valign="top"> <img src="images/i_space.gif"> <table width="100%" border="0" cellspacing="0" cellpadding="2" >
							<%
							if(pageHTML!=null){
							for(String url:pageHTML){
								
							%>
                            <tr> 
                              <td width="20"><div align="center"><img src="images/i_arrow.gif"></div></td>
                              <%=url %>
                            </tr>
                           
							<%}}%>
                          </table>
                          <table width="80%" border="0" cellspacing="0" cellpadding="4">
                            <tr align="right"> 
                              <td colspan="2" ><a  href="javascript:viewBulletinMore()"><img src="images/i_more.gif" border="0" alt="更多一般公告"></a></td>
                            </tr>
                          </table></td>
                      </tr>
                    </table>
                    <br>
             
                  </div>
				<table width="330" border="0" cellspacing="0" cellpadding="0">
					  <tr>
						<td>&nbsp</td>
						 <td width=29><img src="images/i_1.gif" width="29" height="25" align="absmiddle"></td>
				        	<td> <a href="javascript:f_ajaxpw()" ><font color="#FF6600" size="2"> 修改密码 </font></a></td>
					 
						<td>&nbsp</td>
						 <td width=29><img src="images/i_login.gif" width="29" height="25" align="absmiddle"></td>
				        	<td> <a href="login.jsp"  target="_parent"><font color="#FF6600" size="2"> 退出登录 </font></a></td>
					  </tr>
					</table>
				  </td>
              </tr>
            </table></td>
          <td rowspan="2" valign="top"> <table border="0" cellpadding="0" cellspacing="0" width="100%">
              <tr> 
                <td height="42" background="images/index_r3_c3.jpg">&nbsp;</td>
              </tr>
              <tr> 
                <td height="16">&nbsp;</td>
              </tr>
            </table></td>
        </tr>     
      </table>
    </td>
  </tr>
  <tr>
    <td height="20" bgcolor="#575C8C"><div align="center"><span class="content12"><font color="#FFFFFF">Copyright &copy;2013 上海晨冉机电有限公司 &nbsp;&nbsp; <a href="http://www.miitbeian.gov.cn">沪ICP备12016544号</a></font></span></div></td>
  </tr>
</table>
</form>



<script language="javascript">
function openPending(url,module){
		
}

function viewBulletin(){
    	
}
function viewBulletinMore(){
    middleOpen("http://www.kaomingcn.com.cn/china/news/news.php","news","scrollbars=yes,width=1000,height=700,status=yes");    
}

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


function notRepairLink(flag){
    document.forms[0].action="repairAction.do?method=importRemind&flag="+flag;
    document.forms[0].target="_blank";
    document.forms[0].submit(); 

}



function f_ajaxpw(){ 
	var varPassword=window.showModalDialog("support/passwordEdit.jsp?Rnd="+Math.random(),"","dialogHeight: 300px; dialogWidth: 420px; edge: Sunken; center: Yes; help: No; resizable: No; status: Yes;");
	if(varPassword!=undefined){ 
		alert("密码已重置");
		location="login.jsp";
	}
}


</script>	  
</body>
</html>

<%}catch(Exception e){
		  e.printStackTrace();
	  }%>