<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri=  "/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri=  "/WEB-INF/tlds/common.tld" prefix="comtld" %>
<%@ page import="java.util.ArrayList"%> 

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>零件审批</title>

<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/styles2.css" rel="stylesheet" type="text/css" />

</head>
<%
	try{
		ArrayList saleList = (ArrayList)request.getAttribute("saleList");
		int count=0;
		if(saleList!=null){
			count=Integer.parseInt((String)saleList.get(0));
		}

%>
<body>
<html:form action="salePartsApproveAction.do?method=iwPartsList" method="post" >

<table align=center width="99%">
				
                <tr class="tableback1"> 
                  <td width="98"> 询价单号：</td>
                  <td width="126"><html:text property="saleNo"  styleClass="form" size="16" /></td>
                   
                 <td width="98">维修单号：</td>
                  <td width="121"><html:text property="serviceSheetNo"  styleClass="form" size="16" /></td>
                   <td width="98"> 客户名称：</td>
                  <td width="126"><html:text property="customerName"  styleClass="form" size="16" /></td>
                </tr>
               
                <tr class="tableback2"> 
                  <td width="98"> 料号：</td>
                  <td width="126"><html:text property="stuffNo"  styleClass="form" size="16" /></td>
                  <td width="98">零件名称：</td>
                  <td width="121"><html:text property="skuCode"  styleClass="form" size="16" /></td>
                  <td  </td>
				  <td  ></td>
                  
                </tr>
                </table>
	<h3 class="underline"><p align="left">&nbsp;&nbsp;<input type="submit" value="查 询"></p></h3>
	 <table width="99%" border="0" cellpadding="0" cellspacing="1" class="content12">
      		<tr bgcolor="#CCCCCC"> 
        	<td><strong>询价单号</strong></td>
  			  <td><strong>客户名称</strong></td>
  			  <td><strong>客户电话</strong></td>
  			  <td><strong>交货地点</strong></td>
  			  <td><strong>维修单号</strong></td>
  			  <td><strong>询价时间</strong></td>
  			  <td><strong>经办人</strong></td>
  		   </tr>
      
	 <%
       if(saleList!=null){
       	    String strTr="";
      	    for(int i=1;i<saleList.size();i++){
      	        if(i%2==0) strTr="tableback2";
      	        else strTr="tableback1";
      		String[] temp=(String[])saleList.get(i);
      %>      
      <tr class="<%=strTr%>"> 
		  <td style="cursor: hand"><A href="javascript:view('<%=temp[0]%>','<%=temp[14]%>')"><%=temp[0]%></A></td>
          <td ><%=temp[1]==null?"":temp[1]%></td>
          <td ><%=temp[2]==null?"":temp[2]%></td>
          <td ><%=temp[10]==null?"":temp[10]%></td>
          <td ><%=temp[12]==null?"":temp[12]%></td>
          <td ><%=temp[3]==null?"":temp[3]%></td>
          <td ><%=temp[11]==null?"":temp[11]%></td>
          
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
<%
}catch(Exception e){
	e.printStackTrace();
}%>
</html>
<script language="JavaScript">

	
	function view(id,status){
		if(status != 'B'){
			alert("status error!");
		}else{
		
			document.forms[0].saleNo.value=id;
			document.forms[0].action="salePartsApproveAction.do?method=iwPartsDetail";
			document.forms[0].submit();
		}
	}
	
</script>