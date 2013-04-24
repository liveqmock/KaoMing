<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ page import="java.util.ArrayList"%> 

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>syscode</title>

<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/styles.css" rel="stylesheet" type="text/css" />
<script language=javascript src="js/commonSelect.js"></script>

</head>
<%
	try{
		ArrayList syscodeList = (ArrayList)request.getAttribute("syscodeList");
		int count=0;
		if(syscodeList!=null){
			count=Integer.parseInt((String)syscodeList.get(0));
		}


%>
<body>
<html:form action="syscodeAction.do?method=syscodeList" method="post" >
<input name="ids" type="hidden">

<table align=center width="99%">
				
                <tr class="tableback2"> 
                  <td width="98">字段名：</td>
                  <td width="126"><html:text  styleClass="form" property="systemType"  maxlength="32"  size="16"  /></td>
                   
                  <td width="98">状态名：</td>
                  <td width="121"><html:text  styleClass="form" property="systemName"  maxlength="32"  size="16"  /></td>
                    
                  <td width="97">表名：</td>
                  <td width="172"><html:text  styleClass="form" property="systemDesc"  maxlength="16"  size="16"  /></td>
                </tr>
                
                </table>
	<h3 class="underline"><p align="left">&nbsp;&nbsp;<input name="query" type="submit" value="查 询" ></p></h3>
	 <table width="99%" border="0" cellpadding="0" cellspacing="1" class="content12">
	 <tr><font color="blue">记录条数： <%=count%></font>
                      <tr bgcolor="#CCCCCC"> 
          				<td width="10" align=center><input name="allbox" type="checkbox" onClick="checkAll(this);"></td>                                            
                        <td width="25%"><strong>&nbsp;表名</strong></td>
                        <td width="35%"><strong>字段名</strong></td>
                        <td width="10%"><strong>状态值</strong></td>
                        <td><strong>状态名</strong></td>
                        
                      </tr>
         
        <%
       if(syscodeList!=null){
       	    String strTr="";
      	    for(int i=1;i<syscodeList.size();i++){
      	        if(i%2==0) strTr="tableback2";
      	        else strTr="tableback1";
      		String[] temp=(String[])syscodeList.get(i);
      %>      
      <tr class="<%=strTr%>"> 
		  <td align=center><input type="checkbox" name="chk" value="<%=temp[0]%>"></td>            
          <td ><%=temp[1]==null?"":temp[1]%></td>
          <td ><%=temp[3]==null?"":temp[2]%></td>
          <td ><%=temp[3]==null?"":temp[3]%></td>
          <td ><%=temp[4]==null?"":temp[4]%></td>
        </tr>      
      <%}}else{%> 
	<tr class="tableback1"></tr>
      <%}%>
      
    <tr> 
      <td height="1" bgcolor="#677789" colspan="11"></td>
    </tr>
        
        </table>
  				<tr align="left">
                    <td class="content_yellow">
                     <input type="button"  value='新增' onclick="f_add()">
                     <input type="button"  value='修改' onclick="f_modify()">
                     <input type="button"  value='删除' onclick="f_delete()">
                    </td>
                  </tr>
  

	</html:form>
	  
</body>
</html>

<SCRIPT >
function f_add(){
	document.forms[0].action="syscodeAction.do?method=addSyscode"; 
	document.forms[0].submit();
}
function f_modify(){
	var chkVal=chk();
	if(chkVal==null||chkVal==''){
		alert('请先选择记录!');
	}else if(chkOnly()){
		document.forms[0].action="syscodeAction.do?method=editSyscode";
		document.forms[0].ids.value=chkVal; 
		document.forms[0].submit();
	}
}

function f_delete(){
	var chkVal=chk();
	if(chkVal==null||chkVal==''){
		alert('请先选择记录!');
	}else{
		document.forms[0].action="syscodeAction.do?method=deleteSyscode";
		document.forms[0].ids.value=chkVal; 
		document.forms[0].submit();
	}

}


</SCRIPT>

<%
}catch(Exception e){
	e.printStackTrace();
}%>