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
		ArrayList partList = (ArrayList)request.getAttribute("partInfoList");
		String stockFlag = (String)request.getAttribute("stockFlag");
		
		int count=0;
		if(partList!=null){
			count=Integer.parseInt((String)partList.get(0));
		}

		String partType = (String)request.getAttribute("partType");

%>
<body>
<html:form action="partInfoAction.do?method=partInfoViewList" method="post" >
<input name="ids" type="hidden">
<input name="stockFlag" type="hidden" value="<%=stockFlag %>">
<table align=center width="99%">
				
                <tr class="tableback2"> 
                  <td width="98">料号：</td>
                  <td width="126"><html:text  styleClass="form" property="stuffNo"  maxlength="32"  size="16"  /></td>
                   
                  <td width="98">零件名称：</td>
                  <td width="121"><html:text  styleClass="form" property="skuCode"  maxlength="32"  size="16"  /></td>
                    
                  <td width="97">简称：</td>
                  <td width="172"><html:text  styleClass="form" property="shortCode"  maxlength="16"  size="16"  /></td>
                </tr>
                <tr class="tableback1"> 
                  <td width="98"> 规格：</td>
                  <td width="126"><html:text  styleClass="form" property="standard"  maxlength="32"  size="16"  /></td>
                   
                  <td width="98">进价：</td>
                  <td width="121"><html:text  styleClass="form" property="buyCost1"  maxlength="8"  size="16"  onkeydown="f_onlymoney()"/></td>
                    
                  <td width="97">报价：</td>
                  <td width="172"><html:text  styleClass="form" property="saleCost1"    maxlength="8" size="16" onkeydown="f_onlymoney()"/></td>
                </tr>
                
                </table>
	<h3 class="underline"><p align="left">&nbsp;&nbsp;<input name="query" type="submit" value="查 询" ></p></h3>
	 <table width="99%" border="0" cellpadding="0" cellspacing="1" class="content12">
                      <tr bgcolor="#CCCCCC"> 
      									<td width="10">&nbsp;</td>
                        <td width="12%"><strong>&nbsp;料号</strong></td>
                        <td width="18%"><strong>零件名称</strong></td>
                        <td><strong>简称</strong></td>
                        <td><strong>规格</strong></td>
                        <td><strong>单位</strong></td>
                        <td><strong>进价</strong></td>
                        <td><strong>报价</strong></td>
                      </tr>
         
        <%
       if(partList!=null){
       	    String strTr="";
      	    for(int i=1;i<partList.size();i++){
      	        if(i%2==0) strTr="tableback2";
      	        else strTr="tableback1";
      		String[] temp=(String[])partList.get(i);
      %>      
      <tr class="<%=strTr%>"> 
      <td align="center"><input type="radio" name="goback" value="<%=temp[0]+"~^"+temp[1]+"~^"+temp[4]%>" onclick="returnValue(this)"
      		<%if("0".equals(temp[7])){ %>disabled title="无库存"<%} %>></td>
      	  <td ><%=temp[0]==null?"":temp[0]%></td>
          <td ><%=temp[1]==null?"":temp[1]%></td>
          <td ><%=temp[3]==null?"":temp[2]%></td>
          <td ><%=temp[3]==null?"":temp[3]%></td>
          <td ><%=temp[4]==null?"":temp[4]%></td>
		  <td ><%=temp[5]==null?"":temp[5]%></td>
		  <td ><%=temp[6]==null?"":temp[6]%></td>
		  
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
		<%if("A".equals(stockFlag)){
			  if("P".equals(partType)){
		%>
		
		window.opener.document.forms[0].stuffNo2.value=temp[0];
		window.opener.document.forms[0].skuCode2.value=temp[1];
		window.opener.document.forms[0].skuUnit2.value=temp[2];
			<%}else{%>
		window.opener.document.forms[0].stuffNo3.value=temp[0];
		window.opener.document.forms[0].skuCode3.value=temp[1];
		window.opener.document.forms[0].skuUnit3.value=temp[2];
			<%}
		}else{%>
		window.opener.document.forms[0].stuffNo.value=temp[0];
		window.opener.document.forms[0].skuCode.value=temp[1];
		window.opener.document.forms[0].skuUnit.value=temp[2];
		<%}%>
		window.close();
}

</SCRIPT>

<%
}catch(Exception e){
	e.printStackTrace();
}%>