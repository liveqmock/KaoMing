<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri=  "/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri=  "/WEB-INF/tlds/common.tld" prefix="comtld" %>
<%@ page import="java.util.ArrayList"%> 
<%@ page import="com.dne.sie.common.tools.DicInit"%>
 
<html>
<head>
<title>stockOutOperate_list</title>
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/styles2.css">
<script language=javascript src="js/popPartInfo.js"></script>
<script language=javascript src="js/popCalendar.js"></script>
<script language=javascript src="js/common.js"></script>
<script language=javascript src="js/inputValidation.js"></script>

</head>
<%
    ArrayList stockReprotList = (ArrayList)request.getAttribute("stockReprotList");
    int count=0;
    if(stockReprotList!=null){
				count=Integer.parseInt((String)stockReprotList.get(0));
    }

    ArrayList resultList = (ArrayList)DicInit.SYS_CODE_MAP.get("STOCK_TAKE_RESULT");
    System.out.println("---resultList="+resultList);
%>

<body bgcolor="#ffffff" leftmargin="0" topmargin="0" rightmargin="0" bottommargin="0" marginwidth="0" marginheight="0" onload="showQueryDateTR('reqDateCtrl',3);change0();">

 <html:form action="stockReportAction.do?method=takeReport" method="post" >
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
             
                    <br>
                    <table width="100%" border="0" cellpadding="0" cellspacing="1" class="content12">
                      <tr> 
                        <td width="80">盘点流水号：</td>
	                  		<td width="110"><html:text styleClass="form" property="stockTakeId" size="14" /></td>
                        
	      		   
	      		  			<td width="60">盘点人：</td>
	                  <td width="110"><html:text styleClass="form" property="operater" size="12" /></td>
                      </tr>
                      <tr> 
                        <td width="60">盘点差异： </td>
                        <td width="110"><html:select property="stockTakeResult" styleClass="form" style="width:100" >
                        	<html:option value="">全部</html:option>
				<%for(int i=0;resultList!=null&&i<resultList.size();i++){
                  			String[] temp=(String[])resultList.get(i);
                  		%>
                  		<html:option value="<%=temp[0]%>"><%=temp[1]%></html:option>
                  		<%}%>
      			</html:select></td>
                        
                        <td >&nbsp;</td>
                        <td>&nbsp;</td>
                      </tr>
                      <tr> 
                        <td width="60">日期范围：</td>
        		<td width="110"><html:select property="reqDateCtrl" styleClass="form" style="width:100" onchange="changeDateDisplay(this,'startDate','closeDate')">
                            	<html:option value="0">当日</html:option>
                            	<html:option value="3">日期范围</html:option>
            			<html:option value="1">当月</html:option>
            			<html:option value="2">本年</html:option>
            			<html:option value="4">全部</html:option>
                           </html:select>  </td>
                        <td width="60">起始日期： </td>
                        <td width="110"><html:text property="tempDate1" styleId="startDate" styleClass="form" size="12"/> 
           			<a onclick="event.cancelBubble=true;" href="javascript:showCalendar('imageOne',true,'tempDate1');">
            			<img src="<%= request.getContextPath()%>/images/i_colock.gif" id="imageOne" width="18" height="18" border="0" align="absmiddle">
           			</a>
        		</td>
                        
                        <td width="60">终止日期：</td>
                        <td width="110"><html:text property="tempDate2" styleId="closeDate" styleClass="form" size="12"/> 
          			<a onclick="event.cancelBubble=true;" href="javascript:showCalendar('imageTwo',true,'tempDate2');">  
             			<img src="<%= request.getContextPath()%>/images/i_colock.gif" id="imageTwo" width="18" height="18" border="0" align="absmiddle">
           			</a>
        		</td>
                       </tr>
                      
                      
                      
                      <tr> 
                        <td colspan="6"> <input  name="query" type="button" class="button2" value="查询" onclick="f_submit()"></td>
                      </tr>
                    </table>
                    <br>
                    <table width="100%" border="0" cellpadding="0" cellspacing="1" class="content12">
                      <tr bgcolor="#CCCCCC"> 
                        <td><strong> 盘点流水号</strong></td>
                        <td><strong> 盘点人</strong></td>
                        <td><strong> 开始盘点日期</strong></td>
                        <td><strong> 结束盘点日期</strong></td>
                        <td><strong> 盘点差异</strong></td>
                        
                      </tr>
         
       <%if(stockReprotList!=null){
       	    String strTr="";
      	    for(int i=1;i<stockReprotList.size();i++){
      	        if(i%2==0) strTr="tableback2";
      	        else strTr="tableback1";
      		String[] temp=(String[])stockReprotList.get(i);
      %>
      
      <tr class="<%=strTr%>"> 
          <td ><A href="javascript:f_detail('<%=temp[1]%>')"><%=temp[1]==null?"":temp[1]%></a></td>
          <td ><%=temp[2]==null?"":temp[2]%></td>
       	  <td ><%=temp[3]==null?"":temp[3]%></td>
       	  <td ><%=temp[4]==null?"":temp[4]%></td>
       	  <td ><%=temp[5]==null?"":temp[5]%></td>
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
              
                </table></TD>
		</tr>
            </table></td>
  </tr>
</table>
</html:form>
</body>

</html>
 
<SCRIPT language=JAVASCRIPT1.2>
function f_submit(){
    if(chk_page_num(100)){
	document.forms[0].query.disabled = true;
	document.forms[0].submit();
    }
}


	var WLeft=(window.screen.width-800)/2;
	var WTop=(window.screen.height-400)/2;

function f_detail(id)
{
		window.open("stockReportAction.do?method=takeReportDetail&stockTakeId=" + id,"","scrollbars=yes, left="+WLeft +",top=" +WTop +",width=800,height=450,resizable=yes,status:= Yes;");
}

function change0(){
	var stockTakeId=document.forms[0].stockTakeId.value;
	var operater=document.forms[0].operater.value;
	if(stockTakeId=='0'){
		document.forms[0].stockTakeId.value='';
	}
	if(operater=='0'){
		document.forms[0].operater.value='';
	}
}

</SCRIPT>