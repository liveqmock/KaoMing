<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri=  "/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri=  "/WEB-INF/tlds/common.tld" prefix="comtld" %>
<%@ page import="java.util.ArrayList"%> 
<%@ page import="com.dne.sie.common.tools.DicInit"%> 

<HTML>
<HEAD>
<title>发货待审批查询</title>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/styles2.css" rel="stylesheet" type="text/css" />
<script language=javascript src="js/commonSelect.js"></script>

</HEAD>
<%
	try{
		ArrayList asnApproveList = (ArrayList)request.getAttribute("asnApproveList");
		int count=0;
		if(asnApproveList!=null){
			count=Integer.parseInt((String)asnApproveList.get(0));
		}
		ArrayList payStatusList = (ArrayList)DicInit.SYS_CODE_MAP.get("PAY_STATUS");
		ArrayList billingStatusList = (ArrayList)DicInit.SYS_CODE_MAP.get("BILLING_STATUS");

%>
<body >

<html:form action="asnListAction.do?method=consignApproveList" method="post" >
<input type="hidden" name="ids">

 <table width="100%" border="0" cellpadding="0" cellspacing="1" class="content12">  
   
                <tr class="tableback1"> 
                
                  <td width="13%">出货单号：</td>
                  <td ><html:text property="asnNo"  styleClass="form" size="16" /></td>
                   
                  <td width="13%">询价单号：</td>
                  <td ><html:text property="saleNo"  styleClass="form" size="16" /></td>
                   
                  <td width="13%">客户名称：</td>
                  <td width="17%"><html:text property="customerName"  styleClass="form" size="16" /></td>
                  
                </tr>
               
                
     				 <tr class="tableback2"> 
     				 		<td >零件名称：</td>
                <td ><html:text property="skuCode"  styleClass="form" size="16" /></td>
                <td > 付款状态：</td>
                  <td ><html:select styleClass="form" property="payStatus"  >	
									<html:option  value="">全部</html:option>
									<%for(int i=0;payStatusList!=null&&i<payStatusList.size();i++){
                  	String[] temp=(String[])payStatusList.get(i);
                	%>
                		<html:option value="<%=temp[0]%>"><%=temp[1]%></html:option>
                 	<%}%>
      						</html:select>
	     		   		</td>
	     		   		<td > 票据状态：</td>
	                  <td ><html:select styleClass="form" property="billingStatus"  >	
										<html:option  value="">全部</html:option>
										<%for(int i=0;billingStatusList!=null&&i<billingStatusList.size();i++){
	                  	String[] temp=(String[])billingStatusList.get(i);
	                	%>
	                		<html:option value="<%=temp[0]%>"><%=temp[1]%></html:option>
	                 	<%}%>
	      						</html:select>
	     		   		</td>
              </tr>

                </table>
	<h3 class="underline"><p align="left">&nbsp;&nbsp;<input type="submit" value="查 询"></p></h3>
<br>
  <table width="100%" border="0" cellpadding="0" cellspacing="1" class="content12">
      <tr bgcolor="#CCCCCC"> 
        <td align=center width="10"><input name="allbox" type="checkbox" onClick="checkAll(this);"></td>      
        <td><strong>出货单号</strong></td>
  			<td><strong>客户名称</strong></td>
  			<td><strong>发货地址</strong></td>
        <td><strong>付款状态</strong></td>
        <td><strong>票据状态</strong></td>
        <td><strong>发货备注</strong></td>
        <td><strong>申请人</strong></td>
        <td><strong>申请时间</strong></td>
                
      </tr>

	<%
       if(asnApproveList!=null){
       	    String strTr="";
      	    for(int i=1;i<asnApproveList.size();i++){
      	        if(i%2==0) strTr="tableback2";
      	        else strTr="tableback1";
      		String[] temp=(String[])asnApproveList.get(i);
      		String shipRm = temp[5]==null?"":temp[5];
      		if(shipRm.length()>8){
      				shipRm=shipRm.substring(0,6)+"......";
      		}
      %>      
      <tr class="<%=strTr%>"> 
          <td align=center><input type="checkbox" name="chk" value="<%=temp[0]%>"></td>            
		  		<td style="cursor: hand"><A href="javascript:view('<%=temp[0]%>')"><%=temp[0]%></A></td>
          <td ><%=temp[1]==null?"":temp[1]%></td>
          <td ><%=temp[2]==null?"":temp[2]%></td>
          <td ><%=temp[3]==null?"":temp[3]%></td>
          <td ><%=temp[4]==null?"":temp[4]%></td>
          <td title="<%=temp[5]%>"><%=shipRm%></td>
          <td ><%=temp[6]==null?"":temp[6]%></td>
          <td ><%=temp[7]==null?"":temp[7]%></td>
        </tr>      
      <%}}else{%> 
	<tr class="tableback1"></tr>
      <%}%>
      
    <tr> 
      <td height="1" bgcolor="#677789" colspan="12"></td>
    </tr>
    <comtld:pageControl numOfRcds="<%=count%>">
	</comtld:pageControl>
    </table>
    
    <tr align="left">
        <td class="content_yellow"><br>
        备注: <textarea cols="8" rows="2" class="form" name="approveRemark" style="width:70%" ></textarea>
         <br>
         <input name="sout1" type="button"  value='同意发货' onclick="f_approve('Y')">
         <input name="sout2" type="button"  value='不同意发货' onclick="f_approve('N')">
         
        </td>
      </tr>
      
</html:form>
<script language="JavaScript">

	function view(id){
			window.open("asnListAction.do?method=asnDetail&asnNo="+id,"","scrollbars=yes,width=700,height=400");

	}
	
	function f_approve(flag){
		if(chk()!=''&&chk()!=null){
			document.forms[0].ids.value=chk();
			document.forms[0].action="asnListAction.do?method=consignApprove&flag="+flag;
			document.forms[0].target="_self";
			document.forms[0].sout1.disabled=true;
			document.forms[0].sout2.disabled=true;
			document.forms[0].submit();
		}else{
	  		alert("请先选择零件！");
	    }
	}

	
</script>
</BODY>
<%
}catch(Exception e){
	e.printStackTrace();
}%>
</HTML>

