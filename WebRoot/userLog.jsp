<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.dne.sie.common.tools.DicInit"%>
<%@ page import="com.dne.sie.common.tools.Operate"%>
<%@ page import="java.util.*"%> 
<%
try{
%>
<html>
<head>
<title>��������ǰ�û�״̬��</title>
<SCRIPT language="JScript.Encode" src="js/screen.js"></SCRIPT>
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
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/styles2.css">

</head>
<body bgcolor="#ffffff" leftmargin="0" topmargin="0" rightmargin="0" bottommargin="0" marginwidth="0" marginheight="0" >

  <table  border="0" align="center" cellpadding="2" cellspacing="1" class="content12">
  <tr>
    <td>userLog�鿴 (<%=Operate.userMap.size()%>) </td>
  </tr>
</table>

<br>
<%if(Operate.userMap.size()==0){%>
<tr>
<p><font color="red">��</p>
</tr>
<%}else{%>
<table width="100%" border="0"  cellpadding="0" cellspacing="1" class="content12" id="tableid">
        <thead>
  <tr bgcolor="#CCCCCC"> 
    <TH nowrap  sorttype="number" onclick="doInit()" style="cursor:hand">Id</TH>
    <TH nowrap  sorttype="string" onclick="doInit()" style="cursor:hand">��¼��</TH>
    <TH nowrap  sorttype="string" onclick="doInit()" style="cursor:hand">��¼ʱ��</TH>
    <TH nowrap  sorttype="string" onclick="doInit()" style="cursor:hand">�������ʱ��</TH>
    <TH nowrap  sorttype="number" onclick="doInit()" style="cursor:hand">���ʴ���</TH>
    <TH nowrap  sorttype="string" onclick="doInit()" style="cursor:hand">�Ƿ�����</TH>
    <TH nowrap  sorttype="string" onclick="doInit()" style="cursor:hand">����</TH>
    
  </tr>
		</thead>
		<tbody>
       <%
        Iterator it=Operate.userMap.entrySet().iterator();
        int activeUserCount = 0;
        long actionCount = 0;
	while(it.hasNext()){
		StringBuffer sb=new StringBuffer();
		Map.Entry me=(Map.Entry)it.next();
		Long userId=(Long)me.getKey();
		Object[] userVal=(Object[])me.getValue();
		if("1".equals(userVal[3].toString())){
			activeUserCount++;
		
		}
		actionCount = actionCount+Long.parseLong(userVal[2].toString());
	
      		
      %>
      
      <tr <%="1".equals(userVal[3].toString())?"bgcolor=\"#C1FFDC\"":""%>> 
          <td nowrap ><%=userId%></td>
          <td nowrap ><%=userVal[5]==null?"":userVal[5].toString()%></td>
          <td style="word-wrap:break-word" >  <%=userVal[0].toString()%>  </td>
       	  <td nowrap ><%=((Date)userVal[1]).toLocaleString()%></td>
       	  <td nowrap ><%=userVal[2].toString()%></td>
       	  <td nowrap ><%="1".equals(userVal[3].toString())?"����":"����"%></td>
       	  <td nowrap ><%=userVal[4]==null?"":userVal[4].toString()%></td>
        </tr>
      
      <%}%>
		</tbody>
      <tr> 
    <td height="2" colspan="9" bgcolor="#ffffff"></td>
  </tr>
  <tr> 
    <td colspan="9"  bgcolor="#CDCADB">������������������<%=Integer.toString(Operate.userMap.size())%><br/>
    ��ǰ�������<%=Integer.toString(activeUserCount)%><br/>
    ��ǰ�ǻ����:<%=Integer.toString(Operate.userMap.size()-activeUserCount)%><br/>
    �ܵķ�����ˮ����<%=Long.toString(actionCount)%><br/>
    <br/>
    ��ǰ�����ռ����������:<script language="javascript">document.write(formatNumber((<%=Integer.toString(activeUserCount)%>/<%=Integer.toString(Operate.userMap.size())%>)*100)+"%")</script>
    <br/>
  </td>
  </tr>
  <form method="post" name="fileDown" action="attachedInfoAction.do?method=fileResponse">
  <input name="fileName" type="hidden">
   <input name="filePath" type="hidden">
  <tr align="left"> 
    <td colspan="2">
    	<!--<input name="fileDate" class="form" >&nbsp;(��ʽ:2007-06-21)&nbsp;
    	<input type="button" class="button4" value="��־����" onclick="f_down()">-->
    </td>
    </tr>
    </form>
  <tr> 
    <td height="1" colspan="9"  bgcolor="#677789"></td>
  </tr>
</table>
<%}%>
</body>
</html>
<script language="javascript">
function f_down(){
	var path="D:/IBM/wsappdevie51/workspace/newsisWeb/WebContent/affix/";
	
	var fileDate=document.fileDown.fileDate.value;
	document.fileDown.filePath.value=path+"log_"+fileDate+"/statLog.txt";
	document.fileDown.fileName.value="statLog"+fileDate+".txt";
	document.fileDown.action="attachedInfoAction.do?method=fileResponse";
	document.fileDown.target="_blank";
	document.fileDown.submit();
}
</script>

<%

}catch( Exception e){
	e.printStackTrace();
}%>