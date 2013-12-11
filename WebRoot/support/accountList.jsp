<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri=  "/WEB-INF/tlds/common.tld" prefix="comtld" %>
<%@ page import="java.util.ArrayList"%> 
<%@ page import="com.dne.sie.common.tools.DicInit"%>

<HTML>
<HEAD>
<title>费用List</title>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/styles2.css" rel="stylesheet" type="text/css" />
<script language=javascript src="js/commonSelect.js"></script>
<script language=javascript src="js/popCalendar.js"></script>
<script language=javascript src="js/common.js"></script>
<SCRIPT language="javascript" src="js/tree/client_ini.js"></SCRIPT>
<SCRIPT language="javascript" src="js/tree/basic.js"></SCRIPT>
<SCRIPT language="javascript" src="js/tree/control.js"></SCRIPT>
<SCRIPT language="javascript" src="js/tree/editor.js"></SCRIPT>
<SCRIPT language="javascript" src="js/tree/dropdown.js"></SCRIPT>
<script event="onload" for="window">initDocument();</SCRIPT>
</HEAD>
<%
	try{
		ArrayList accountList = (ArrayList)request.getAttribute("accountList");
		int count=0;
		if(accountList!=null){
			count=Integer.parseInt((String)accountList.get(0));
		}
		ArrayList payTypeList = (ArrayList)DicInit.SYS_CODE_MAP.get("FEE_PAY_TYPE");
		ArrayList empList = (ArrayList)request.getAttribute("empList");
		
%>
<body >
<html:form action="accountItemAction.do?method=accountList" method="post" >
<input name="ids" type="hidden">
<input name="subIds" type="hidden">

 <table width="100%" border="0" cellpadding="0" cellspacing="1" class="content12">  
   
                <tr class="tableback1"> 
                  <td width="10%">凭证号：</td>
                  <td width="20%"><html:text property="voucherNo"  styleClass="form" size="16" maxlength="32" /></td>
                  
                  <td width="10%">类型：</td>
                  <td width="20%"><html:select property="payType"  styleClass="form">	
                  	<html:option value="">===全部===</html:option>
					<%for(int i=0;i<payTypeList.size();i++){
	                  String[] temp=(String[])payTypeList.get(i);
	                 %>
	                  <html:option value="<%=temp[0]%>"><%=temp[1]%></html:option>
	                <%}%>
			      	</html:select></td>
                   
                  <td width="10%">金额：</td>
                  <td ><html:text property="money1"  styleClass="form" size="8" maxlength="12" />
                  	- <html:text property="money2"  styleClass="form" size="8" maxlength="12" /></td>
                  
                </tr>
                <tr class="tableback2"> 
                  <td width="10%">科目：</td>
                  <td width="20%">
                  	<INPUT id="subjectName" name="subjectName" attrib="editor" dropdown_mode="custom" dropdown_url="accountItemAction.do?method=dropdownTree" width="16">
                  </td>
                  
                  <td width="10%">人员：</td>
                  <td width="20%"><html:select property="employeeCode"  styleClass="form">	
                  	<html:option value="">===全部===</html:option>
					<%for(int i=0;empList!=null&&i<empList.size();i++){
	                  String[] temp=(String[])empList.get(i);
	                 %>
	                  <html:option value="<%=temp[0]%>"><%=temp[1]%></html:option>
	                <%}%>
      				</html:select></td>
                   
                  <td width="70">客户：</td>
                  <td ><html:text property="customerName"  styleClass="form" size="30" maxlength="64" /></td>
                  
                </tr>
      			<tr class="tableback1"> 
                  <td width="10%">费用日期：</td>
        			<td width="20%"><html:select property="dateScope" styleClass="form" style="width:100" onchange="changeDateDisplay(this,'feeDate1','feeDate2')">
                            	<html:option value="4">全部</html:option>
                            	<html:option value="0">当日</html:option>
                            	<html:option value="3">日期范围</html:option>
            					<html:option value="1">当月</html:option>
            					<html:option value="2">本年</html:option>
                           </html:select>  </td>
                    <td width="10%">起始日期： </td>
                    <td ><html:text property="feeDate1" styleId="feeDate1" styleClass="form" readonly="true" size="14"/> 
           			<a onclick="event.cancelBubble=true;" href="javascript:showCalendar('imageOne',true,'feeDate1');">
            			<img src="googleImg/icon/calendar.gif" id="imageOne" width="18" height="18" border="0" align="absmiddle">
           			</a>
        		</td>
                <td width="10%">终止日期：</td>
                    <td ><html:text property="feeDate2" styleId="feeDate2" styleClass="form" readonly="true" size="14"/> 
          			<a onclick="event.cancelBubble=true;" href="javascript:showCalendar('imageTwo',true,'feeDate2');">  
             			<img src="googleImg/icon/calendar.gif" id="imageTwo" width="18" height="18" border="0" align="absmiddle">
           			</a>
        		</td>
                </tr>
                 <tr class="tableback2"> 
                  <td >地点：</td>
                  <td colspan="2"> 
                  	<html:textarea property="place" rows="2" cols="4" styleClass="form" style="width:93%" ></html:textarea>
                  </td>
                  <td align="right">摘要：</td>
                  <td colspan="2" > 
                  	<html:textarea property="summary" rows="2" cols="4" styleClass="form" style="width:93%" ></html:textarea>
                  </td>
                </tr>  

                </table>
	<h3 class="underline"><p align="left">&nbsp;&nbsp;<input name="query" type="button" onclick="f_query()" value="查 询" ></p></h3>
<br>
  <table width="100%" border="0" cellpadding="0" cellspacing="1" class="content12">
      <tr bgcolor="#CCCCCC"> 
       <td width="10"><input name="allbox" type="checkbox" onClick="checkAll(this);"></td>
        <td width="50"><strong>凭证号</strong></td>
  		<td><strong>日期 </strong></td>
  		<td><strong>科目</strong></td>
  		<td><strong>人员</strong></td>
  		<td><strong>客户</strong></td>
  		<td><strong>金额￥</strong></td>
        <td><strong>类型</strong></td>
        <td><strong>摘要</strong></td>
      </tr>

	 <%
       if(accountList!=null){
       	    String strTr="";
      	    for(int i=1;i<accountList.size();i++){
      	        if(i%2==0) strTr="tableback2";
      	        else strTr="tableback1";
      		String[] temp=(String[])accountList.get(i);
      		String titleSum="";
      		if(temp[8]!=null&&temp[8].length()>6){
      			titleSum="title='"+temp[8]+"'";
      			temp[8]=temp[8].substring(0,6)+"...";
      		}
      		
      %>      
      <tr class="<%=strTr%>"> 
		  <td align=center><input type="checkbox" name="chk" value="<%=temp[0]%>"></td>           
		  <td style="cursor: hand"><A href="javascript:f_modify('<%=temp[0]%>')"><%=temp[1]%></A></td>
          <td ><%=temp[2]==null?"":temp[2]%></td>
          <td title="<%=temp[9]%>"><%=temp[3]%></td>
          <td title="<%=temp[5]%>"><%=temp[4]==null?"":temp[4]%></td>
          <td ><%=temp[10]==null?"":temp[10]%></td>
          <td ><%=temp[6]==null?"":temp[6]%></td>
          <td ><%=temp[7]==null?"":temp[7]%></td>
          <td <%=titleSum%>><%=temp[8]==null?"":temp[8]%></td>
        </tr>      
      <%}}else{%> 
	<tr class="tableback1"></tr>
      <%}%>
      
      
    <tr> 
      <td height="1" bgcolor="#677789" colspan="11"></td>
    </tr>
    <comtld:pageControl numOfRcds="<%=count%>">
	</comtld:pageControl>
    </table>
    
              <tr align="left">
                    <td class="content_yellow">
                     <input type="button"  value='新增' onclick="f_add()">
                     <input type="button"  value='删除' onclick="f_delete()">
                    </td>
                  </tr>
			  

	</html:form>

</BODY>


<SCRIPT >
var Ces_Library_path="";
var Ces_Library_Common_path="js/tree/";

function f_query(){
	var subIds=document.getElementById('subjectName').value
	document.forms[0].subIds.value=subIds;
	document.forms[0].query.disabled=true;
	document.forms[0].action="accountItemAction.do?method=accountList"; 
	document.forms[0].submit();
}

function f_add(){
	document.forms[0].action="accountItemAction.do?method=accountInit"; 
	document.forms[0].submit();
}
function f_modify(chkVal){
	
		document.forms[0].action="accountItemAction.do?method=accountEdit";
		document.forms[0].ids.value=chkVal; 
		document.forms[0].submit();
	
}

function f_delete(){
	var chkVal=chk();
	if(chkVal==null||chkVal==''){
		alert('请先选择记录!');
	}else{
		if(window.confirm("确定删除该记录吗？")){
			document.forms[0].action="accountItemAction.do?method=deleteAccount";
			document.forms[0].ids.value=chkVal; 
			document.forms[0].submit();
		}
	}

}


</SCRIPT>

<%
}catch(Exception e){
	e.printStackTrace();
}%>
</HTML>