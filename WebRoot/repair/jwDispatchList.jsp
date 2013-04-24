<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri=  "/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri=  "/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri=  "/WEB-INF/tlds/common.tld" prefix="comtld" %>
<%@ page import="java.util.*"%>
<%@ page import="com.dne.sie.common.tools.*"%>

<html:html>
<head>
<title>jwDispatchList.jsp</title>
<link rel="stylesheet" href="<%= request.getContextPath()%>/css/style.css">
<link rel="stylesheet" href="<%= request.getContextPath()%>/css/styles2.css">
<script language=javascript src="js/common.js"></script>
<SCRIPT language="JScript.Encode" src="<%= request.getContextPath()%>/js/commonSelect.js"></SCRIPT>
<script language=javascript src="<%= request.getContextPath()%>/js/popCalendar.js"></script>
<script language=javascript src="<%= request.getContextPath()%>/js/checkValid.js"></script>
<script language=javascript src="js/popSortTable.js"></script>
<script language="JavaScript" type="text/JavaScript">
<!--
function view(id,status){
	middleOpen("repairTurningAction.do?method=jwDispatchDetail&repairNo="+id+"&status="+status,"","scrollbars=yes,width=1000,height=700,status=yes");    
}

function doSubmit(){

		event.srcElement.disabled = true;
		document.forms[0].submit();
	
}

document.onkeydown = keyDown ;
function keyDown(e){
	if(event.keyCode==13){
		doSubmit();
	}
}

function f_cancel(){
	if(chk()!=''&&chk()!=null){
	    if(window.confirm("确定取消该维修单吗？")){
	    	document.forms[0].action="repairHandleAction.do?method=cancelRepair";
	    	document.forms[0].repairNos.value=chk();
	    	
			document.forms[0].submit();
	    }
	}else{
		alert("请先选择维修单");
	}

}

//-->
</script>
</head>
<%
 try{	
    ArrayList jwDispatchList = (ArrayList)request.getAttribute("atDispatchList");
    
    int count=jwDispatchList == null?0:Integer.parseInt((String)jwDispatchList.get(0));
%>
<body bgcolor="#ffffff" leftmargin="0" topmargin="0" rightmargin="0" bottommargin="0" marginwidth="0" marginheight="0" >
<html:form action="repairTurningAction.do?method=jwDispatchList" method="post">
<input type="hidden" name="repairNos" >
<html:hidden property="repairProperites"/>
<html:hidden property="currentStatus"/>
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
                        <td class="content12"><strong>就位派工查询</strong></td>
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
                        <td colspan="6"><html:button property="button" value=" 查 询 " styleClass="button2" onclick="doSubmit()"/></td>
                      </tr>
                    </table>
                    <br> 
                    <table  width="1100" border="0" cellpadding="0" cellspacing="1" class="content12" id="tableid">
                      <thead><tr bgcolor="#CCCCCC"> 
                        <td width="10"><input name="allbox" type="checkbox" onClick="checkAll(this);"></td>
                        <TH ><b>维修单号</b></TH>
						<TH ><b>客户</b></TH>
						<TH ><b>联系人</b></TH>
						<TH ><b>联系电话</b></TH>
						<TH ><b>机型</b></TH>
						<TH ><b>机身号</b></TH> 
						<TH ><b>客户要求到达日期</b></TH>
						<TH ><b>预定就位日期</b></TH>
						<TH ><b>登记人</b></TH>
                      </tr></thead>
                      <tbody>
<%
	if(jwDispatchList!=null){
   	    String strTr="";
  	    for(int i=1;i<jwDispatchList.size();i++){
  	        if(i%2==0) strTr="tableback2";
  	        else strTr="tableback1";
  			String[] temp=(String[])jwDispatchList.get(i);
  %>
	<tr class="<%=strTr%>" style="cursor: hand"> 
	<td align=center><input type="checkbox" name="chk" value="<%=temp[0]%>"></td>
    <td onmouseover="this.style.color='orange'" onmouseout="this.style.color='black'" style="cursor: hand" align=center onclick="javascript:view('<%=temp[0]%>','<%=temp[12]%>')"><%=temp[1]%></td>
      <td ><%=temp[2]==null?"":temp[2]%></td>
      <td ><%=temp[13]==null?"":temp[13]%></td>
      <td ><%=temp[3]==null?"":temp[3]%></td>
      <td ><%=temp[4]==null?"":temp[4]%></td>
      <td ><%=temp[5]==null?"":temp[5]%></td>
      
       <td ><%=temp[9]==null?"":temp[9]%></td>
        <td ><%=temp[10]==null?"":temp[10]%></td>
        <td ><%=temp[17]==null?"":temp[17]%></td>
    </tr>
   
     
<%}}%>   </tbody>
<comtld:pageControl numOfRcds="<%=count%>"></comtld:pageControl>   
    <tr> 
      <td height="2" bgcolor="#ffffff" colspan="20"></td>
    </tr>
    <tr> 
      <td height="1" bgcolor="#677789" colspan="20"></td>
    </tr>
    <tr align="left">
                    <td class="content_yellow">
                     <input type="button"  value='取消' class="button2" onclick="f_cancel()">
                    </td>
                  </tr>
    
                  
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