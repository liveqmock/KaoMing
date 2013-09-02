<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri=  "/WEB-INF/tlds/common.tld" prefix="comtld" %>
<%@ page import="java.util.ArrayList"%> 

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>partInfo</title>

<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/styles2.css" rel="stylesheet" type="text/css" />
<script language=javascript src="js/inputValidation.js"></script>
<script language=javascript src="js/commonSelect.js"></script>

</head>
<%
	try{
		ArrayList customerList = (ArrayList)request.getAttribute("custInfoList");
		int count=0;
		if(customerList!=null){
			count=Integer.parseInt((String)customerList.get(0));
		}


%>
<body>
<html:form action="customerInfoAction.do?method=popupCustList" method="post" >
<input name="flag" type="hidden" value="query">

 <table width="100%" border="0" cellpadding="0" cellspacing="1" class="content12">  
   
                <tr class="tableback1"> 
                  <td width="98"> 客户ID：</td>
                  <td width="126"><html:text property="customerId"  styleClass="form" size="16" maxlength="4" /></td>
                   
                  <td width="98">客户名称：</td>
                  <td width="121"><html:text property="customerName"  styleClass="form" size="16" maxlength="32" /></td>
                   <td width="98"> 城市：</td>
                  <td width="126"><html:text property="cityName"  styleClass="form" size="16" maxlength="16" /></td>
                  
                </tr>
               
                <tr class="tableback2"> 
                 <td width="98"> 联系人：</td>
                  <td width="126"><html:text property="linkman"  styleClass="form" size="16" maxlength="16" /></td>
                  
                  <td width="98"> 联系电话：</td>
                  <td width="126"><html:text property="phone"  styleClass="form" size="16" maxlength="32" /></td>
                   
                  <td width="98">工厂地址：</td>
                  <td width="121"><html:text property="address"  styleClass="form" size="16" maxlength="200" /></td>
                  
                </tr>
      

                </table>
	<h3 class="underline"><p align="left">&nbsp;&nbsp;<input name="query" type="submit" value="查 询" ></p></h3>
<br>
  <table width="100%" border="0" cellpadding="0" cellspacing="1" class="content12">
      <tr bgcolor="#CCCCCC"> 
       <td width="10"><input name="allbox" type="radio" ></td>
        <td><strong>客户ID</strong></td>
  		<td><strong>客户名称 </strong></td>
  		<td><strong>联系人</strong></td>
  		<td><strong>联系电话</strong></td>
  		<td><strong>传真</strong></td>
  		<td><strong>工厂地址</strong></td>
        <td><strong>邮编</strong></td>
        <td><strong>省份</strong></td>
        <td><strong>城市</strong></td>
      </tr>

	 <%
       if(customerList!=null){
       	    String strTr="";
      	    for(int i=1;i<customerList.size();i++){
      	        if(i%2==0) strTr="tableback2";
      	        else strTr="tableback1";
      		String[] temp=(String[])customerList.get(i);
      		String title="";
      		if(temp[5]!=null&&temp[5].length()>6){
      			title="title='"+temp[5]+"'";
      			temp[5]=temp[5].substring(0,6)+"...";
      		}
      %>      
      <tr class="<%=strTr%>"> 
		  <td align=center><input type="radio" name="goback" value="<%=temp[0]+"~^"+temp[1]%>" onclick="returnValue(this)"></td>
          <td ><%=temp[0]==null?"":temp[0]%></td>
          <td ><%=temp[1]==null?"":temp[1]%></td>
          <td ><%=temp[2]==null?"":temp[2]%></td>
          <td ><%=temp[3]==null?"":temp[3]%></td>
          <td ><%=temp[4]==null?"":temp[4]%></td>
          <td <%=title%>><%=temp[5]==null?"":temp[5]%></td>
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
    
	</html:form>
	  
</body>
</html>

<SCRIPT >

function returnValue(oElement){
		var temp = oElement.value.split('~^');
		window.opener.document.forms[0].customerId.value=temp[0];
		window.opener.document.forms[0].customerName.value=temp[1];
		
		window.close();
}

</SCRIPT>

<%
}catch(Exception e){
	e.printStackTrace();
}%>