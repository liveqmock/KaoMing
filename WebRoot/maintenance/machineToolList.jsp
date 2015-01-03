<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri=  "/WEB-INF/tlds/common.tld" prefix="comtld" %>
<%@ page import="java.util.ArrayList"%> 

<HTML>
<HEAD>
<title>客户维护</title>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/styles2.css" rel="stylesheet" type="text/css" />
<script language=javascript src="js/commonSelect.js"></script>

</HEAD>
<%
	try{
		ArrayList machineToolList = (ArrayList)request.getAttribute("machineToolList");
		int count=0;
		if(machineToolList!=null){
			count=Integer.parseInt((String)machineToolList.get(0));
		}


%>
<body >
<html:form action="machineToolAction.do?method=machineToolList" method="post" >
<input type="hidden" name="ids">

 <table width="100%" border="0" cellpadding="0" cellspacing="1" class="content12">  
   
                <tr class="tableback1"> 
                  <td width="98"> 客户ID：</td>
                  <td width="126"><html:text property="customerId"  styleClass="form" size="20"  /></td>
                   
                  <td width="98">客户名称：</td>
                  <td width="121"><html:text property="customerName"  styleClass="form" size="20"  /></td>
                   <td width="98">保固书编号：</td>
                  <td width="126"><html:text property="warrantyCardNo"  styleClass="form" size="20"  /></td>
                  
                </tr>
               
                <tr class="tableback2"> 
                 <td width="98"> 机器名称：</td>
                  <td width="126"><html:text property="machineName"  styleClass="form" size="20"   maxlength="50"/></td>
                  
                  <td width="98"> 机型：</td>
                  <td width="126"><html:text property="modelCode"  styleClass="form" size="20" maxlength="50" /></td>
                   
                  <td width="98">机身编码：</td>
                  <td width="121"><html:text property="serialNo"  styleClass="form" size="16" maxlength="50" /></td>
                  
                </tr>
      
                </table>
	<h3 class="underline"><p align="left">&nbsp;&nbsp;<input name="query" type="submit" value="查 询" ></p></h3>
<br>
  <table width="100%" border="0" cellpadding="0" cellspacing="1" class="content12">
      <tr bgcolor="#CCCCCC"> 
       <td width="10"><input name="allbox" type="checkbox" onClick="checkAll(this);"></td>
          <td><strong>机器名称</strong></td>
        <td><strong>客户ID</strong></td>
  		<td><strong>客户名称 </strong></td>

  		<td><strong>机型</strong></td>
  		<td><strong>机身编码</strong></td>
  		<td><strong>保固书编号</strong></td>
        <td><strong>验收日期</strong></td>
        <td><strong>延保日期</strong></td>
      </tr>

	 <%
       if(machineToolList!=null){
       	    String strTr="";
      	    for(int i=1;i<machineToolList.size();i++){
      	        if(i%2==0) strTr="tableback2";
      	        else strTr="tableback1";
      		String[] temp=(String[])machineToolList.get(i);
     
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
	document.forms[0].action="machineToolAction.do?method=machineToolEdit"; 
	document.forms[0].submit();
}
function f_modify(chkVal){
	
		document.forms[0].action="machineToolAction.do?method=machineToolEdit";
		document.forms[0].ids.value=chkVal; 
		document.forms[0].submit();
	
}

function f_delete(){
	var chkVal=chk();
	if(chkVal==null||chkVal==''){
		alert('请先选择记录!');
	}else{
		if(window.confirm("确定删除该记录吗？")){
			document.forms[0].action="machineToolAction.do?method=deleteMachineTool";
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