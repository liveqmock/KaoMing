<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri=  "/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri=  "/WEB-INF/tlds/common.tld" prefix="comtld" %>
<%@ page import="java.util.ArrayList"%> 
<%@ page import="com.dne.sie.common.tools.DicInit"%>
 
<html>
<head>
<title>stockOutOperate_list</title><SCRIPT language="JScript.Encode" src="js/screen.js"></SCRIPT>
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/styles2.css">

<script language=javascript src="js/common.js"></script>

</head>
<%
try{
    String filePath=(String)request.getAttribute("filePath");
    ArrayList stockReprotList = (ArrayList)request.getAttribute("stockReprotList");
    int count=stockReprotList==null?0:Integer.parseInt((String)stockReprotList.get(0));
    String[] stat = (String [])request.getAttribute("stockReportStat");
    String stockTakeId=(String)request.getAttribute("stockTakeId");
    String userName=(String)request.getAttribute("userName");
    String curDate=(String)request.getAttribute("curDate");
    //ArrayList resultList = (ArrayList)DicInit.SYS_CODE_MAP.get("STOCK_TAKE_RESULT");
    
    
    
%>

<%if(filePath!=null){%>
<body onload="f_downLoan()">
<%}else{%>
<body bgcolor="#ffffff" leftmargin="0" topmargin="0" rightmargin="0" bottommargin="0" marginwidth="0" marginheight="0" >
<jsp:include page=  "/common/waitDiv.jsp" flush="true" />

 <html:form action="stockReportAction.do?method=takeReportDetail" method="post" >

        <table width="98%" height="90%" border="0" cellpadding="0" cellspacing="6" class="content12">
          <tr> 
            <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0" class="content12">
              <tr>
                <td height="2" bgcolor="#677789"></td>
              </tr>
              <tr>
                <td height="6" bgcolor="#CECECE"></td>
              </tr>
              <tr>
                <td valign="top"> <br>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr >
                        <td class="content12"  align="middle"><strong>盘点报表<input type="hidden" name="stockTakeId" value="<%=stockTakeId%>"></strong></td>
                      </tr>
                    </table>
                    <br>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                   
                      <tr >
                        <td class="content12" width="80" >报告日期:</td><td class="content12" ><%=curDate%></td>
                      </tr>
                      <tr >
                        <td class="content12" width="80" >报告人:</td><td class="content12"  ><%=userName%></td>
                      </tr>
                    </table>
                    
                    <table width="100%"  border="0" cellpadding="0" cellspacing="1" class="content12">
                      <tr class="tableback2"> 
                        <td width="80"></td><td  colspan="4"  align="middle">盘点内容</td><td  colspan="4"  align="middle">盘点结果</td>
                      </tr>
                      <tr class="tableback2">
	                  	<td ></td><td colspan="4" align="middle">实物库存合计</td><td colspan="4"></td>
                      </tr>
                      <tr class="tableback2">
                      <td></td><td>盘点库存</td><td>实物现存</td><td>维修员手头借用零件</td><td>总差异</td><td>短缺</td><td>过剩</td><td>仓位差异</td><td>准确率</td>
	                  </tr>
                      <tr class="tableback2">
                      <td>种类</td><td><%=stat[0]%></td><td><%=stat[3]%></td><td><%=stat[6]%></td><td><%=stat[9]%></td><td><%=stat[12]%></td><td><%=stat[15]%></td><td><%=stat[18]%></td><td><%=stat[21]%></td>
	                  </tr>	                  
                     <tr class="tableback2"> 
                      <td>数量</td><td><%=stat[1]%></td><td><%=stat[4]%></td><td><%=stat[7]%></td><td><%=stat[10]%></td><td><%=stat[13]%></td><td><%=stat[16]%></td><td><%=stat[19]%></td><td><%=stat[22]%></td>
	                  </tr>	                  
                     <tr class="tableback2">
                      <td>金额</td><td><%=stat[2]%></td><td><%=stat[5]%></td><td><%=stat[8]%></td><td><%=stat[11]%></td><td><%=stat[14]%></td><td><%=stat[17]%></td><td><%=stat[20]%></td><td></td>
	                  </tr>	                  
                      
                      <tr> 
                        <td colspan="9"> </td>
                      </tr>
                    </table>
                    <br>
                    <table width="100%" border="0" cellpadding="0" cellspacing="1" class="content12">
                      <tr bgcolor="#CCCCCC"> 
                        <td><strong> 流水号</strong></td>
                        <td><strong> 仓位号</strong></td>
                        <td><strong> SKU号</strong></td>
                        <td><strong> SKU描述</strong></td>
                        <td><strong> 差异类型</strong></td>
                        <td><strong>差异数量</strong></td>
                        <td><strong>单价</strong></td>
                        <td><strong>金额</strong></td>            
                        
                      </tr>
         
       <%if(stockReprotList!=null){
       	    String strTr="";
      	    for(int i=1;i<stockReprotList.size();i++){
      	        if(i%2==0) strTr="tableback2";
      	        else strTr="tableback1";
      		String[] temp=(String[])stockReprotList.get(i);
      %>
      
      <tr class="<%=strTr%>"> 
      	  <td ><%=temp[0]==null?"":temp[0]%></td>
          <td ><%=temp[1]==null?"":temp[1]%></td>
          <td ><%=temp[2]==null?"":temp[2]%></td>
       	  <td ><%=temp[3]==null?"":temp[3]%></td>
       	  <td ><%=temp[4]==null?"":temp[4]%></td>
       	  <td ><%=temp[5]==null?"":temp[5]%></td>
       	  <td ><%=temp[6]==null?"":temp[6]%></td>
       	  <td ><%=temp[7]==null?"":temp[7]%></td>
        </tr>
      
      <%}}%> 
      
      <tr> 
                  <td height="2" colspan="8" bgcolor="#ffffff"></td>
                </tr>
                <tr> 
                  <td height="1" colspan="8" bgcolor="#677789"></td>
                </tr>
    <comtld:pageControl numOfRcds="<%=count%>">
	</comtld:pageControl>
     </table></td>
              </tr>
              </html:form>
              <form name="excelForm" action="stockReportAction.do?method=takeReportExcel&stockTakeId=<%=stockTakeId%>"  method="post">
               <tr> 
                  <td  colspan="6" align="right"> 
                    <input  name="btn" type="button" onclick="createExcel()" class="button4" value="生成Excel"> 
                  </td>
                </tr>
              </form>
                
                </table></TD>
		</tr>
            </table></td>
  </tr>
</table>
<%}%>
</body>
<!-- InstanceEnd --></html>
 
<SCRIPT language=JAVASCRIPT1.2>
function createExcel(){
	//showWaitDiv(400,700);
 	document.excelForm.submit();
 	document.excelForm.btn.disabled="true";
	setTimeout("reUse();",15000);
}
function reUse(){
	document.excelForm.btn.disabled=false;
}

function f_downLoan(){
	location="<%=filePath%>";
}
</SCRIPT>
<%}catch(Exception e){}%>