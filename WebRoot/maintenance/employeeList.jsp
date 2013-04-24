<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri=  "/WEB-INF/tlds/common.tld" prefix="comtld" %>
<%@ page import="java.util.ArrayList"%> 

<HTML>
<HEAD>
<title>员工维护</title>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/styles2.css" rel="stylesheet" type="text/css" />
<script language=javascript src="js/commonSelect.js"></script>

</HEAD>
<%
	try{
		ArrayList employeeList = (ArrayList)request.getAttribute("employeeList");
		int count=0;
		if(employeeList!=null){
			count=Integer.parseInt((String)employeeList.get(0));
		}


%>
<body >
<html:form action="employeeInfoAction.do?method=employeeList" method="post" >
<input name="ids" type="hidden">

 <table width="100%" border="0" cellpadding="0" cellspacing="1" class="content12">  
   
                <tr class="tableback1"> 
                  <td width="98">员工名称：</td>
                  <td width="121"><html:text property="employeeName"  styleClass="form" size="16" maxlength="45" /></td>
                  
                  <td width="98">联系电话：</td>
                  <td width="126"><html:text property="phone"  styleClass="form" size="16" maxlength="32" /></td>
                   
                  <td width="98">邮箱：</td>
                  <td width="121"><html:text property="email"  styleClass="form" size="16" maxlength="32" /></td>
                  
                </tr>
      

                </table>
	<h3 class="underline"><p align="left">&nbsp;&nbsp;<input name="query" type="submit" value="查 询" ></p></h3>
<br>
  <table width="100%" border="0" cellpadding="0" cellspacing="1" class="content12">
      <tr bgcolor="#CCCCCC"> 
       <td width="10"><input name="allbox" type="checkbox" onClick="checkAll(this);"></td>
  		<td><strong>姓名 </strong></td>
  		<td><strong>年龄</strong></td>
  		<td><strong>性别</strong></td>
  		<td><strong>邮箱</strong></td>
  		<td><strong>电话</strong></td>
  		<td><strong>qq</strong></td>
      <td><strong>msn</strong></td>
      <td><strong>身份证号</strong></td>
      </tr>

	 <%
       if(employeeList!=null){
       	    String strTr="";
      	    for(int i=1;i<employeeList.size();i++){
      	        if(i%2==0) strTr="tableback2";
      	        else strTr="tableback1";
      		String[] temp=(String[])employeeList.get(i);
      %>      
      <tr class="<%=strTr%>"> 
		  <td align=center><input type="checkbox" name="chk" value="<%=temp[0]%>"></td>           
		  <td style="cursor: hand"><A href="javascript:f_modify('<%=temp[0]%>')"><%=temp[1]%></A></td>
          <td ><%=temp[2]==null?"":temp[2]%></td>
          <td ><%=temp[3]==null?"":temp[3]%></td>
          <td ><%=temp[4]==null?"":temp[4]%></td>
          <td ><%=temp[5]==null?"":temp[5]%></td>
          <td ><%=temp[6]==null?"":temp[6]%></td>
          <td ><%=temp[7]==null?"":temp[7]%></td>
          <td ><%=temp[8]==null?"":temp[8]%></td>
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
function f_add(){
	document.forms[0].action="employeeInfoAction.do?method=employeeInit"; 
	document.forms[0].submit();
}
function f_modify(chkVal){
	
		document.forms[0].action="employeeInfoAction.do?method=employeeEdit";
		document.forms[0].ids.value=chkVal; 
		document.forms[0].submit();
	
}

function f_delete(){
	var chkVal=chk();
	if(chkVal==null||chkVal==''){
		alert('请先选择记录!');
	}else{
		if(window.confirm("确定删除该记录吗？")){
			document.forms[0].action="employeeInfoAction.do?method=deleteEmployee";
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