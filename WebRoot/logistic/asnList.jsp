<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri=  "/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri=  "/WEB-INF/tlds/common.tld" prefix="comtld" %>
<%@ page import="java.util.ArrayList"%> 
<%@ page import="com.dne.sie.common.tools.DicInit"%> 

<HTML>
<HEAD>
<title>出货列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/styles2.css" rel="stylesheet" type="text/css" />

</HEAD>
<%
	try{
		ArrayList asnList = (ArrayList)request.getAttribute("asnList");
		int count=0;
		if(asnList!=null){
			count=Integer.parseInt((String)asnList.get(0));
		}
		
		String asnStatus = (String)request.getAttribute("asnStatus")==null?"Q":(String)request.getAttribute("asnStatus");
		ArrayList payStatusList = (ArrayList)DicInit.SYS_CODE_MAP.get("PAY_STATUS");
		ArrayList billingStatusList = (ArrayList)DicInit.SYS_CODE_MAP.get("BILLING_STATUS");

%>
<body >

<html:form action="asnListAction.do?method=asnList" method="post" >
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
	<h3 class="underline"><p align="left">&nbsp;&nbsp;<input name="query" type="button" value="查 询" onclick="f_query()"></p></h3>
<br>
  <table width="100%" border="0" cellpadding="0" cellspacing="1" class="content12">
      <tr bgcolor="#CCCCCC"> 
        <td align=center width="10">&nbsp;</td>      
        <td><strong>出货单号</strong></td>
  			<td><strong>客户名称</strong></td>
  			<td><strong>发货地址</strong></td>
        <td><strong>付款状态</strong></td>
        <td><strong>票据状态</strong></td>
        <td><strong>发货备注</strong></td>
        <td><strong>审批意见</strong></td>
        <td><strong>申请人</strong></td>
        <td><strong>申请时间</strong></td>
        <td><strong>发货方式</strong></td>
        <td><strong>出货状态</strong></td>
      </tr>

	<%
       if(asnList!=null){
       	    String strTr="";
      	    for(int i=1;i<asnList.size();i++){
      	        if(i%2==0) strTr="tableback2";
      	        else strTr="tableback1";
      		String[] temp=(String[])asnList.get(i);
      		String shipRm = temp[5]==null?"":temp[5];
      		String approveRm = temp[8]==null?"":temp[8];
      		if(shipRm.length()>8){
      				shipRm=shipRm.substring(0,6)+"......";
      		}
      		if(approveRm.length()>8){
      				approveRm=approveRm.substring(0,6)+"......";
      		}
      		String strDis="disabled";
      		if("已发货".equals(temp[9])){
      			strDis="";
      		}
      %>      
      <tr class="<%=strTr%>"> 
          <td align=center><input type="radio" name="chk" value="<%=temp[0]%>" onclick="setVal('<%=temp[0]%>')" <%=strDis%>></td>            
		  		<td style="cursor: hand"><A href="javascript:view('<%=temp[0]%>')"><%=temp[0]%></A></td>
          <td ><%=temp[1]==null?"":temp[1]%></td>
          <td ><%=temp[2]==null?"":temp[2]%></td>
          <td ><%=temp[3]==null?"":temp[3]%></td>
          <td ><%=temp[4]==null?"":temp[4]%></td>
          <td title="<%=temp[5]%>"><%=shipRm%></td>
          <td title="<%=temp[8]%>"><%=approveRm%></td>
          <td ><%=temp[6]==null?"":temp[6]%></td>
          <td ><%=temp[7]==null?"":temp[7]%></td>
          <td ><%=temp[10]==null?"":temp[10]%></td>
          <td ><%=temp[9]==null?"":temp[9]%></td>
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
<br>
		<tr align="left">
      <td class="content_yellow">
      <input name="poC" type="button"  value="出货单打印" onclick="asn_print()">
      </td>
    </tr>
</html:form>
<script language="JavaScript">

	function f_query(){
			document.forms[0].query.disabled=true;
			document.forms[0].action="asnListAction.do?method=asnList";
			document.forms[0].submit();
	}

	function view(id){
			window.open("asnListAction.do?method=asnDetail&asnNo="+id,"","scrollbars=yes,width=700,height=400");

	}
	var asn_no='';
	function setVal(no){
			asn_no=no;
	}
	
	function asn_print(){
		if(asn_no!=''){
			window.open("asnListAction.do?method=asnPrint&asnNo="+asn_no);
		}else{
			alert("请先选择出货单");
		}
	}


	
</script>
</BODY>
<%
}catch(Exception e){
	e.printStackTrace();
}%>
</HTML>

