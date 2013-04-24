<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="comtld" %>
<%@ page import="java.util.Calendar"%>

<%
	Calendar today = Calendar.getInstance();
	int thisYear = today.get(Calendar.YEAR);
	int thisMonth = today.get(Calendar.MONTH);
	int thisDay = today.get(Calendar.DATE);
%>
<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=gb2312">
<STYLE type="text/css">
	TD {FONT-SIZE: 12px; FONT-FAMILY: arial; TEXT-ALIGN: center}
	TD.dt {FONT-SIZE: 11px; FONT-FAMILY: arial; TEXT-ALIGN: center}
	A {COLOR: blue}
	A:hover {COLOR: red}
	A.bt {COLOR: #888888}
</STYLE>
<link rel="stylesheet" href="css/style.css">

<SCRIPT language="javascript">
<!--//
var str='',i,j,yy,mm,openbound,callback;
var disableFutureDate,limitDate; // 是否禁用未来日期，限制limitDate之前日期
var fld1,fld2;
var wp=window.parent;
var cf=wp.document.getElementById("CalFrame");
var fld,curday,today=new Date(<%=thisYear+","+thisMonth+","+thisDay%>);
today.setHours(0);today.setMinutes(0);today.setSeconds(0);today.setMilliseconds(0);
//var lastyear=today.getYear(),lastmonth=today.getMonth();
function parseDate(s){
	var reg=new RegExp("[^0-9-]","")
	if(s.search(reg)>=0)return today;
	var ss=s.split("-");
	if(ss.length!=3)return today;
	if(isNaN(ss[0])||isNaN(ss[1])||isNaN(ss[2]))return today;
	return new Date(parseFloat(ss[0]),parseFloat(ss[1])-1,parseFloat(ss[2]));
}
function resizeCalendar(){cf.width=189;cf.height=202;}// if you need adjust calendar iframe size189/200, change here. sunhj 2006-3-15
function initCalendar(){
	if(fld1&&fld1.value.length>0){curday=parseDate(fld1.value);}
	else if(fld2&&fld2.value.length>0){curday=parseDate(fld2.value);}
	else curday=today;
	drawCalendar(curday.getFullYear(),curday.getMonth());
}

//设定特定的日期，为了区别当前日期，给需要指定日期时使用，如生日地方，默认1980年1月1日 add by hao
function setSpeDate(year,month,day){
	fld,curday,today=new Date(year,month,day);
}
function setCurrentDate(){
	fld,curday,today=new Date(<%=thisYear+","+thisMonth+","+thisDay%>);
}

function drawCalendar(y,m){
	var x=new Date(y,m,1),mv=x.getDay(),d=x.getDate(),de;
	yy=x.getFullYear();mm=x.getMonth();
	//document.getElementById("yyyymm").innerHTML=yy+"."+(mm+1>9?mm+1:"0"+(mm+1));
	document.getElementById("yearSelector").value = yy;
	document.getElementById("monthSelector").value = mm;
	for(var i=1;i<=mv;i++){
		de=document.getElementById("d"+i);
		de.innerHTML="";
		de.bgColor="";
	}
	while(x.getMonth()==mm){
		de=document.getElementById("d"+(d+mv));
		if(x.getTime()==curday.getTime())
			de.bgColor="#dddddd";
		else
			de.bgColor="white";
		if(x.getTime()==today.getTime())
			de.innerHTML="<a href=javascript:setDate("+d+");><font color=red>"+d+"</font></a>";
		else if(x.getTime()<today.getTime())
			if(openbound){de.innerHTML="<a href=javascript:setDate("+d+"); class=bt>"+d+"</a>";}
			else{de.innerHTML="<font color=#888888>"+d+"</font>";}
		else{
			de.innerHTML=disableFutureDate?"<a href=javascript:setDate("+d+");>"+d+"</a>":"<font color=#888888>"+d+"</font>";
		}
		x.setDate(++d);
	}
	while(d+mv<=42){
		de=document.getElementById("d"+(d+mv));
		de.innerHTML="";
		de.bgColor="";
		d++;
	}
}
function setDate(d){
	wp.hideCalendar();
	var dstr=yy+"-"+(mm+1)+"-"+d;
	if(callback&&callback.length>0){eval("wp."+callback+"(\""+dstr+"\")");}
	else{fld1.value=dstr;fld1.select();}
}
function returnBlank(){
	wp.hideCalendar();
	fld1.value="";
}
//-->
</SCRIPT>
</HEAD>

<BODY bottomMargin="0" bgColor="#63758C" leftMargin="0" topMargin="0" onload="resizeCalendar();" rightMargin="0">
<TABLE id="tbl0" cellSpacing="0" cellPadding="1" bgColor="#336699" border="0">
  <TBODY>
  <TR>
    <TD>
      <TABLE cellSpacing="1" cellPadding="2" width="100%" bgColor="white" border="0">
        <TBODY>
        <TR bgColor="gray">
          <TD id="prev" width="16">
		    <A href="javascript:drawCalendar(yy,mm-1);"><IMG height="16" src="../images/prev.gif" width="16" border="0"></A>
		  </TD>
          <!--<TD id="yyyymm" style="FONT-SIZE: 11px; COLOR: white" width="99%"></TD>-->
		  <TD>
		  <select name="yearSelector" id="yearSelector" onchange="drawCalendar(this.value,document.getElementById('monthSelector').value)" class="form">
		  		<%
		  			for(int i=1970;i<2021;i++){
		  				out.println("<option value=\""+i+"\">"+i+"</option>");
		  			}
		  		%>
			</select>
		  </TD>
		  <TD>
			<select name="monthSelector" id="monthSelector" onchange="drawCalendar(document.getElementById('yearSelector').value,this.value)" class="form">
				<option value="0">一  月</option>
				<option value="1">二  月</option>
				<option value="2">三  月</option>
				<option value="3">四  月</option>
				<option value="4">五  月</option>
				<option value="5">六  月</option>
				<option value="6">七  月</option>
				<option value="7">八  月</option>
				<option value="8">九  月</option>
				<option value="9">十  月</option>
				<option value="10">十一月</option>
				<option value="11">十二月</option>
			</select>
		  </TD>
          <TD id="next" width="16">
		    <A href="javascript:drawCalendar(yy,mm+1);"><IMG height="16" src="../images/next.gif" width="16" border="0"></A>
		  </TD>
		</TR>
		</TBODY>
	  </TABLE>
      <TABLE cellSpacing="2" cellPadding="0" width="187" bgColor="white" border="0">
        <TBODY>
        <TR height="23">
          <TD width="23"><FONT color=red><bean:message key="calendar.sunday"/></FONT></TD>
          <TD width="23"><bean:message key="calendar.monday"/></TD>
          <TD width="23"><bean:message key="calendar.tuesday"/></TD>
          <TD width="23"><bean:message key="calendar.wednesday"/></TD>
          <TD width="23"><bean:message key="calendar.thursday"/></TD>
          <TD width="23"><bean:message key="calendar.friday"/></TD>
          <TD width="23"><FONT color="green"><bean:message key="calendar.saturday"/></FONT></TD>
		</TR>
        <TR height="1">
          <TD bgColor="gray" colSpan="7"></TD>
		</TR>
<SCRIPT language=javascript>
<!--//
for(i=0;i<6;i++){
	str+="<tr height=18>";
	for(j=1;j<=7;j++)
	  str+="<td id=d"+(i*7+j)+" class=dt></td>";
	str+="</tr>";
}
document.write(str);
//-->
</SCRIPT>

        <TR height="1">
          <TD bgColor="gray" colSpan="7"></TD></TR>
        <TR height="18">
          <TD colSpan="7">
		    <A href="javascript:wp.hideCalendar();"><bean:message key="calendar.close"/></A>
			&nbsp;&nbsp;
		    <A href="javascript:returnBlank();">空</A>
		  </TD>
		</TR>
		</TBODY>
	  </TABLE>
	</TD>
  </TR>
  </TBODY>
</TABLE>
<SCRIPT language=javascript>
<!--//
	var bCalLoaded=true;
//-->
</SCRIPT>
</BODY>
</HTML>