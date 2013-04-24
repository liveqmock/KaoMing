<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.dne.sie.common.tools.DicInit"%>
<%@ page import="com.dne.sie.common.tools.Operate"%>
<%@ page import="java.util.*"%> 


<%
try{
	String strFlag=request.getParameter("flag");
	if(strFlag!=null&&strFlag.equals("reset")){
		out.println("************strFlag="+strFlag);
		Operate.actionMap=new HashMap();
	}
	
%>

<html>
<head>
<title>actionLog</title>
<link rel="stylesheet" href="css/style.css">
<SCRIPT LANGUAGE="JavaScript">
<!--
var owebbody;  // for array column
function doInit(){
	owebbody=tableid.tBodies[0];
	var oSrc=event.srcElement;
	var sSortType=oSrc.getAttribute("sorttype");
	bAcend=(oSrc.getAttribute("order")=="up") ? true : false;
	sortCols(oSrc.cellIndex,bAcend,sSortType);
	if (bAcend) {
	  oSrc.order="down";
	}
	else {
	  oSrc.order="up";
	}
}
function sortCols(iCol,bAcending,sType){
	var arrRows=owebbody.rows;
	var arrTemp=new Array();
	for (var i=0; i<arrRows.length; i++) { arrTemp[i]=arrRows[i]; }
	arrTemp.sort(compareByColumn(iCol,bAcending,sType));
	for (var i=0; i<arrTemp.length; i++) { owebbody.appendChild(arrTemp[i]); }
}
function compareByColumn(iCol,bAcending,sType) {
	var fTypeCast=String;
	if (sType == "number"){
		fTypeCast=Number;
		return function (n1,n2) {
			if (fTypeCast(getCellValue(n1.cells[iCol],","))<fTypeCast(getCellValue(n2.cells[iCol],","))) return bAcending ? -1 : +1;
			if (fTypeCast(getCellValue(n1.cells[iCol],","))>fTypeCast(getCellValue(n2.cells[iCol],","))) return bAcending ? +1 : -1;
			return 0;
		}
	}else{
		return function (n1,n2) {
			if (fTypeCast(getCellValue(n1.cells[iCol]))<fTypeCast(getCellValue(n2.cells[iCol]))) return bAcending ? -1 : +1;
			if (fTypeCast(getCellValue(n1.cells[iCol]))>fTypeCast(getCellValue(n2.cells[iCol]))) return bAcending ? +1 : -1;
			return 0;
		}
	}
}
function getCellValue(oCell){
	
	var sValue=oCell.innerText;
	if (sValue==null) sValue="";
	return sValue;
}
function getCellValue(oCell,replaceStr){
	
	var sValue=oCell.innerText;
	if (sValue==null) sValue="";
	sValue = sValue.replace(replaceStr,"");
	return sValue;
}

function formatNumber(number){
   var n = parseFloat(number);
   var l100 = 0;
   var l1000 = 0;
   l100 = n*100;
   l1000 = n*1000;


   var l = l1000%10;

   var ln = parseInt(l);
   if (ln>=5){
     l100 +=1;
   }

   var lReturn = l100/100;
   var  sReturn = lReturn + "";
   //
   if (sReturn.length>=5) {
      sReturn = sReturn.substr(0,5);

      if (sReturn.indexOf(".")<2){
          sReturn = sReturn.substr(0,4);
        }
   }
   //
   return sReturn;
}
//-->
</SCRIPT>
</head>
<body bgcolor="#ffffff" leftmargin="0" topmargin="0" rightmargin="0" bottommargin="0" marginwidth="0" marginheight="0" >
<form method="post">
<input type="hidden" name="flag">
  <table  border="0" align="center" cellpadding="2" cellspacing="1" class="content12">
  <tr>
    <td>actionLog查看 (<%=Operate.actionMap.size()%>) </td>
  </tr>
</table>

<br>
<%if(Operate.actionMap.size()==0){%>
<tr>
<p><font color="red">空</p>
</tr>
<%}else{%>
<table width="90%" border="0" align="center" cellpadding="0" cellspacing="1" class="content12" id="tableid">
<thead>
  <tr bgcolor="#CCCCCC"> 
    <TH nowrap  sorttype="string" onclick="doInit()" style="cursor:hand">link</TH>
    <TH nowrap  sorttype="number" onclick="doInit()" style="cursor:hand">count</TH>
    <TH nowrap  sorttype="number" onclick="doInit()" style="cursor:hand">平均时间(毫秒)</TH>
    <TH nowrap  sorttype="string" onclick="doInit()" style="cursor:hand">历史响应时间(毫秒)</TH>
  </tr>
         </thead>
		<tbody>
       <%
        Iterator it=Operate.actionMap.entrySet().iterator();
	while(it.hasNext()){
		Map.Entry me=(Map.Entry)it.next();
		String link=(String)me.getKey();
		Object[] obj=(Object[])me.getValue();
		Integer count=(Integer)obj[0];
		Long respTime=(Long)obj[1];
		ArrayList timeList=(ArrayList)obj[2];
		String historyTimes=Operate.arrayListToString(timeList);
		
      %>
      
      <tr > 
          <td nowrap><%=link%></td>
          <td nowrap><%=count.toString()%></td>
          <td nowrap><%=respTime.toString()%></td>
          <td nowrap><%=historyTimes%></td>
        </tr>
      
      <%}%>
      	</tbody>
      <tr> 
    <td height="2" colspan="9" bgcolor="#ffffff"></td>
  </tr>
  
  <tr> 
    <td height="1" colspan="9"  bgcolor="#677789"></td>
  </tr>
  <tr>
  <td colspan="2">
    	<input type="button" class="button2"  value="reset" onclick="f_reset()">
    </td>
    </tr>
</table>
<%}%>
</form>
</body>
</html>


<%

}catch( Exception e){
	e.printStackTrace();
}%>


<SCRIPT language=JAVASCRIPT1.2>


function f_reset(){
   	document.forms[0].flag.value="reset";
    	document.forms[0].submit(); 
}


</SCRIPT>