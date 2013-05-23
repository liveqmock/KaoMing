<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri=  "/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri=  "/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri=  "/WEB-INF/tlds/common.tld" prefix="comtld" %>
<%@ page import="java.util.*"%>
<%@ page import="com.dne.sie.common.tools.*"%>
 
<html:html>
<head>
<title>repairQueryList.jsp</title>
<link rel="stylesheet" href="<%= request.getContextPath()%>/css/style.css">
<link rel="stylesheet" href="<%= request.getContextPath()%>/css/styles2.css">
<script language=javascript src="js/common.js"></script>
<SCRIPT language="JScript.Encode" src="<%= request.getContextPath()%>/js/commonSelect.js"></SCRIPT>
<script language=javascript src="<%= request.getContextPath()%>/js/popCalendar.js"></script>
<script language=javascript src="<%= request.getContextPath()%>/js/checkValid.js"></script>
<script language=javascript src="js/popSortTable.js"></script>
<script language="JavaScript" type="text/JavaScript">
<!--
function view(id){
	middleOpen("repairAction.do?method=repairQueryDetail&repairNo="+id,"","scrollbars=yes,width=1000,height=700,status=yes");    
}

function doSubmit(){
		document.forms[0].action="repairAction.do?method=repairQueryList";
		event.srcElement.disabled = true;
		document.forms[0].submit();
}

document.onkeydown = keyDown ;
function keyDown(e){
	if(event.keyCode==13){
		doSubmit();
	}
}

function doReset(){
	document.forms[0].reset();
}

function f_position(){
	var irisIds=window.showModalDialog("repairAction.do?method=positionQuery","","dialogHeight: 300px; dialogWidth: 420px; edge: Sunken; center: Yes; help: No; resizable: No; status: Yes;");
	//alert(irisIds);
	if(irisIds!=null&&irisIds!=''){
		document.forms[0].irisIds.value=irisIds;
		doSubmit();
	}
	
}

//-->
</script>
</head>
<%
 try{	
    ArrayList repairList = (ArrayList)request.getAttribute("repairQueryList");
    
    int count=repairList == null?0:Integer.parseInt((String)repairList.get(0));
    ArrayList repairStatusArr = (ArrayList)DicInit.SYS_CODE_MAP.get("CURRENT_STATUS");
   
    ArrayList repairSourceArr = (ArrayList)DicInit.SYS_CODE_MAP.get("REPAIR_SOURCE");
    ArrayList repairProperitesArr = (ArrayList)DicInit.SYS_CODE_MAP.get("REPAIR_PROPERITES");
    
    ArrayList warrantyTypeArr = (ArrayList)DicInit.SYS_CODE_MAP.get("WARRANTY_TYPE");

	
	
%>
<body bgcolor="#ffffff" leftmargin="0" topmargin="0" rightmargin="0" bottommargin="0" marginwidth="0" marginheight="0" onload="showQueryDateTR('receiveScope',1)">
<html:form action="repairAction.do?method=repairQueryList" method="post">
<input name="irisIds" type="hidden">
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" class="content12">
  <tr>
    <td valign="top">
        <table width="96%" height="100%" border="0" cellpadding="0" cellspacing="6" class="content12">
          <tr> 
            <td valign="top"> 
              <table width="100%" border="0" cellpadding="0" cellspacing="0" class="content12">
                <tr> 
                  <td height="2" bgcolor="#677789"></td>
                </tr>
                <tr> 
                  <td height="6" bgcolor="#CECECE"></td>
                </tr>
                <tr> 
                  <td valign="top">
                   <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr> 
                        <td class="content12"><strong>维修单查询</strong></td>
                      </tr>
                    </table>
                    <br>
                    <table width="750" border="0" cellpadding="0" cellspacing="1" class="content12">
                      <tr> 
                        <td width="100">维修单号：</td>
                        <td width="150"><html:text property="serviceSheetNo" styleClass="form" size="16"  style="text-transform:uppercase"/></td>
                        <td width="100">机型：</td>
                        <td width="150"> 
                        <html:text property="modelCode" styleClass="form" size="16" style="text-transform:uppercase" onblur="this.value=this.value.toUpperCase()" /> 
                        </td>
                        <td width="100">机身号：</td>
                        <td width="150"><html:text property="serialNo" styleClass="form" size="16" /></td>
                      </tr>
                      <tr> 
                        <td>客户名称：</td>
                        <td><html:text property="customerName" styleClass="form" size="16"/></td>
                        <td>联系人：</td>
                        <td><html:text property="linkman" styleClass="form" size="16"/></td>
                        <td width="90">联系电话：</td>
                        <td width="140"><html:text property="phone" styleClass="form" size="16"/></td>
                      </tr>
                
                      <tr>
                        <td>维修员：</td>
                        <td><html:text property="repairManStr" styleClass="form" size="16" /></td>
                        <td>销售单：</td>
                        <td><html:text property="saleNo" styleClass="form" size="16" /></td>
                        <td>返修：</td>
							<td>
									<html:select property="rr90" styleClass="form">
									<option value="">全部</option>
									<html:option value="Y">是</html:option>
			 						<html:option value="N">否</html:option>
 								    </html:select>
							</td>
                      </tr>
                      <tr>
						<td width="90">维修性质：</td>
                        <td>
							<html:select property="repairProperites" styleClass="form">
								<%
								  for(int i=0;repairProperitesArr!=null&&i<repairProperitesArr.size();i++){
									String[] temp=(String[])repairProperitesArr.get(i);
								%>
								 <html:option value="<%=temp[0]%>"><%=temp[1]%></html:option>
								<%}%>
      						</html:select>
                        </td>
                        <td>保修期类型：</td>
							<td>
									<html:select property="warrantyType" styleClass="form">
										<option value="">全部</option>
										<%
			  							for(int i=0;warrantyTypeArr!=null&&i<warrantyTypeArr.size();i++){
											String[] temp=(String[])warrantyTypeArr.get(i);
										%>
			 						<html:option value="<%=temp[0]%>"><%=temp[1]%></html:option>
										<%}%>
 											</html:select>
							</td>
                        
                        
                        <td >维修单状态：</td>
                        <td >
							<html:select property="currentStatus" styleClass="form">
								<option value="">全部</option>
								<%
								  for(int i=0;repairStatusArr!=null&&i<repairStatusArr.size();i++){
									String[] temp=(String[])repairStatusArr.get(i);
								%>
								 <html:option value="<%=temp[0]%>"><%=temp[1]%></html:option>
								<%}%>
      						</html:select>
                        </td>
                      </tr>
                    
               		 		
                      <tr> 
                        <td>登记日期：</td>
                        <td><html:select property="receiveScope" styleClass="form" onchange="changeDateDisplay(this,'startDate','endDate')">
							<html:option value="1">当月</html:option>
							<html:option value="0">当日</html:option>
							<html:option value="3">日期范围</html:option>
							<html:option value="2">本年</html:option>
							<html:option value="4">全部</html:option>
						</html:select></td>
						<td style="display:none">起始日期：</td>
						<td style="display:none"><html:text property="startDate" styleClass="form" size="16" readonly="true"/>
						    <a onclick="event.cancelBubble=true;" href="javascript:showCalendar('imageOne',true,'startDate');"> 
							<img src="<%= request.getContextPath()%>/images/i_colock.gif" id="imageOne" width="18" height="18" border="0" align="absmiddle"> </a></td>
						<td style="display:none">终止日期：</td>
						<td style="display:none"><html:text property="endDate" styleClass="form" size="16" readonly="true"/> 
						   <a onclick="event.cancelBubble=true;" href="javascript:showCalendar('imageTwo',true,'endDate');"> 
					 	   <img src="<%= request.getContextPath()%>/images/i_colock.gif" id="imageTwo" width="18" height="18" border="0" align="absmiddle"> </a></td>
                      </tr>
                      
                      <tr> 
                        <td colspan="6">
                        	<html:button property="button" value=" 查 询 " styleClass="button2" onclick="doSubmit()"/>&nbsp;
                        	<input type="button" value="故障位置查询" class="button6" onclick="f_position()"/>&nbsp;
                        	<input type="button" value=" 重 置 " class="button2" onclick="doReset()"/>
                        </td>
                      </tr>
                    </table>
                    <br> 
                    <table  width="1100" border="0" cellpadding="0" cellspacing="1" class="content12" id="tableid">
                      <thead><tr bgcolor="#CCCCCC"> 
                        <TH ><b>维修单号</b></TH>
						<TH ><b>客户</b></TH>
						<TH ><b>联系电话</b></TH>
						<TH ><b>机型</b></TH>
						<TH ><b>机身号</b></TH>
						<TH ><b>电诊员</b></TH> 
						<TH ><b>维修员</b></TH>
						<TH ><b>返修</b></TH>
						<TH ><b>保修类型</b></TH>
						<TH ><b>登记日期</b></TH>
						<TH ><b>更新日期</b></TH>
						<TH ><b>修复日期</b></TH>
						<TH ><b>遗留问题</b></TH>
						<TH ><b>维修状态</b></TH>
                      </tr></thead><tbody>
<%
	if(repairList!=null){
   	    String strTr="";
  	    for(int i=1;i<repairList.size();i++){
  	        if(i%2==0) strTr="tableback2";
  	        else strTr="tableback1";
  			String[] temp=(String[])repairList.get(i);
  			String leave = "";
  			if(!temp[13].isEmpty()){
  				leave = temp[13].equals("Y")?"有":"无";
  			}
  			
  %>
	<tr class="<%=strTr%>" style="cursor: hand"> 
    <td onmouseover="this.style.color='orange'" onmouseout="this.style.color='black'" style="cursor: hand" align=center onclick="javascript:view('<%=temp[0]%>')"><%=temp[1]%></td>
    
      <td ><%=temp[2]==null?"":temp[2]%></td>
      <td ><%=temp[3]==null?"":temp[3]%></td>
      <td ><%=temp[4]==null?"":temp[4]%></td>
      <td ><%=temp[5]==null?"":temp[5]%></td>
      <td ><%=temp[15]%></td>
      <td ><%=temp[6]==null?"":temp[6]%></td>
      <td ><%=DicInit.getSystemName("RR90",temp[7])%></td>
      <%if(temp[16].equals("T")){ %>
      <td ><%=DicInit.getSystemName("TUNING_TYPE",temp[8])%></td>
      <%}else{ %>
      <td ><%=DicInit.getSystemName("WARRANTY_TYPE",temp[8])%></td>
      <%} %>
      <td ><%=temp[14]%></td>
      <td ><%=temp[9]==null?"":temp[9]%></td>
      <td ><%=temp[10]==null?"":temp[10]%></td>
      <td ><%=leave%></td>
      <td ><%=DicInit.getSystemName("CURRENT_STATUS",temp[12])%></td>

    </tr>
   
     
<%}}%>   </tbody>
    <tr> 
      <td height="2" bgcolor="#ffffff" colspan="20"></td>
    </tr>
    <tr> 
      <td height="1" bgcolor="#677789" colspan="20"></td>
    </tr>
    
    <comtld:pageControl numOfRcds="<%=count%>"></comtld:pageControl>   
                  
					</table>
                    </td>
                </tr>
              </table> 
            </td>
          </tr>
        </table>
      </td>
  </tr>
</table>

</html:form>
<%}catch(Exception e){
 	e.printStackTrace();
}%>
</body>
</html:html>