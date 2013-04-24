<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri=  "/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri=  "/WEB-INF/tlds/common.tld" prefix="comtld" %>
<%@ page import="java.util.ArrayList"%> 
<%@ page import="java.util.List"%> 
<%@ page import="com.dne.sie.reception.bo.PartPoBo"%> 
<%@ page import="com.dne.sie.common.tools.DicInit"%> 
<HTML>
<HEAD>
<title>零件订购清单</title>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/styles2.css" rel="stylesheet" type="text/css" />
<script language=javascript src="js/common.js"></script>
<script language=javascript src="js/commonSelect.js"></script>

</HEAD>
<%
try{
		ArrayList poList = (ArrayList)request.getAttribute("poList");
		String poPayAmounts = null;
		int count=0;
		if(poList!=null){
			count=Integer.parseInt((String)poList.get(0));
			if(poList.size()>1) poPayAmounts = (String)poList.get(1);
		}
		
		List monthList=PartPoBo.getInstance().getOrderMonthList();
		ArrayList orderStatusList = (ArrayList)DicInit.SYS_CODE_MAP.get("ORDER_STATUS");
		ArrayList orderTypeList = (ArrayList)DicInit.SYS_CODE_MAP.get("ORDER_TYPE");
		
		
		
%>
<body >

<html:form action="partPoAction.do?method=poList" method="post" >
<html:hidden property="poNo"/>
<input type="hidden" name="ids" >
<input type="hidden" name="flag" value="allList">

<table align=center width="99%">
				
                <tr class="tableback1"> 
                  <td width="13%"> 订购单号：</td>
                  <td ><html:text property="orderNo"  styleClass="form" size="16" /></td>
                   
                  <td width="13%"> 询价单号：</td>
                  <td ><html:text property="saleNo"  styleClass="form" size="16" /></td>
                   
                  <td width="13%">客户名称：</td>
                  <td width="17%"><html:text property="customerName"  styleClass="form" size="16" /></td>
                  
                </tr>
               
                <tr class="tableback2"> 
                  <td > 料号：</td>
                  <td ><html:text property="stuffNo"  styleClass="form" size="16" /></td>
                  <td >零件名称：</td>
                  <td ><html:text property="skuCode"  styleClass="form" size="16" /></td>
                  <td > 机型：</td>
                  <td ><html:text property="modelCode"  styleClass="form" size="16" /></td>
                </tr>
      
				     <tr class="tableback1"> 
				     	 <td width="80">订购状态：</td>
		     		   <td width="120"><html:select styleClass="form" property="orderStatus"  >	
									<html:option  value="">全部</html:option>
									<%for(int i=0;orderStatusList!=null&&i<orderStatusList.size();i++){
                  	String[] temp=(String[])orderStatusList.get(i);
                	%>
                		<html:option value="<%=temp[0]%>" ><%=temp[1]%></html:option>
                 	<%}%>
      						</html:select>
     		   		</td>
     		   		<td width="80">订购类型：</td>
		     		   <td width="120"><html:select styleClass="form" property="orderType"  >	
									<html:option  value="">全部</html:option>
									<%for(int i=0;orderTypeList!=null&&i<orderTypeList.size();i++){
                  	String[] temp=(String[])orderTypeList.get(i);
                	%>
                		<html:option value="<%=temp[0]%>" ><%=temp[1]%></html:option>
                 	<%}%>
      						</html:select>
     		   		</td>
     		   		<td width="98"> 订单月份：</td>
                  <td width="126"><html:select styleClass="form" property="orderMonth"  >
	     		           <html:option value="">全部</html:option>
	     		           <%for(int i=0;monthList!=null&&i<monthList.size();i++){
	                  	String temp=(String)monthList.get(i);
	                	%>
	                		<html:option value="<%=temp%>"><%=temp%></html:option>
	                 	<%}%>
	     		           </html:select></td>
	     		      
          	</tr>
               
 </table>
 <h3 class="underline"><p align="left">&nbsp;&nbsp;<input type="submit" value="查 询"></p></h3>

  <table width="1100" border="0" cellpadding="0" cellspacing="1" class="content12">
  <%if(poPayAmounts!=null){%>
   		<tr>
   				<td colspan="4"><font color="blue">应付款总和： $<%=poPayAmounts%> </font></td>
   		</tr>
  <%}%>
      <tr bgcolor="#CCCCCC"> 
        <td width="10"><input name="allbox" type="checkbox" onClick="checkAll(this);"></td>
        <td><strong>订购单号</strong></td>
        <td><strong>询价单号</strong></td>
  			<td><strong>客户名称</strong></td>
  			<td><strong>厂商</strong></td>
  			<td><strong>料号</strong></td>
  			<td><strong>零件名称</strong></td>
  			<td><strong>单价(美金)</strong></td>
        <td><strong>数量</strong></td>
        <td><strong>经办人</strong></td>
        <td><strong>订购日期</strong></td>
        <td><strong>订购状态</strong></td>
        <td><strong>订购类型</strong></td>
        <td><strong>保固</strong></td>
        <td><strong>备注</strong></td>
        <td><strong>打印</strong></td>
      </tr>

	<%
       if(poList!=null){
       	    String strTr="";
      	    for(int i=2;i<poList.size();i++){
      	        if(i%2==0) strTr="tableback2";
      	        else strTr="tableback1";
      		String[] temp=(String[])poList.get(i);
      		String disb="";
      		if("C".equals(temp[14])||"X".equals(temp[14])){
      				disb="disabled";
      		}
      		String remark=temp[12]==null?"":temp[12];
      		if(remark.length()>6){
      				remark=remark.substring(0,6)+"......";
      		}
      %>      
      <tr class="<%=strTr%>"> 
          <td align=center><input type="checkbox" name="chk" value="<%=temp[0]%>" <%=disb%> ></td>
          <td ><%=temp[11]==null?"":temp[11]%></td> 
          <%if(!"N".equals(temp[1])){%>
		  		<td style="cursor: hand"><A href="javascript:view('<%=temp[1]%>')"><%=temp[1]%></A></td>
		  		<%}else{%>
		  		<td ><%=temp[1]%></td>
		  		<%}%>
          <td ><%=temp[2]==null?"":temp[2]%></td>
          <td ><%=temp[15]==null?"":temp[15]%></td>
          <td ><%=temp[3]==null?"":temp[3]%></td>
          <td >&nbsp;<%=temp[4]==null?"":temp[4]%></td>
          <td >&nbsp;<%=temp[5]==null?"":temp[5]%></td>
          <td ><%=temp[6]==null?"":temp[6]%></td>
          <td ><%=temp[7]==null?"":temp[7]%></td>
          <td ><%=temp[8]==null?"":temp[8]%></td>
          <td ><%=temp[9]==null?"":temp[9]%></td>
          <%if("I".equals(temp[17])||"N".equals(temp[17])){ %>
          <td ><font color="red"><%=DicInit.getSystemName("ORDER_TYPE",temp[17])%></font></td>
          <%}else{ %>
          <td ><%=DicInit.getSystemName("ORDER_TYPE",temp[17])%></td>
          <%} %>
           <%if("I".equals(temp[18])){ %>
          <td ><font color="red"><%=DicInit.getSystemName("WARRANTY_TYPE",temp[18])%></font></td>
          <%}else{ %>
          <td ><%=DicInit.getSystemName("WARRANTY_TYPE",temp[18])%></td>
          <%} %>
          <td title="<%=temp[12]==null?"":temp[12]%>"><%=remark%></td>
          <td align="center"><%if(temp[11]!=null&&!temp[11].equals("")){%><a href="javascript:order_print('<%=temp[11]%>')" style="cursor: hand"><img src="googleImg/icon/icon_4_doc.gif" border=0 title="附件" ></a><%}%></td>
        </tr>      
      <%}}else{%> 
	<tr class="tableback1"></tr>
      <%}%>
    <tr> 
      <td height="1" bgcolor="#677789" colspan="15"></td>
    </tr>
      <comtld:pageControl numOfRcds="<%=count%>">
	</comtld:pageControl>
    </table>
<br>
		<tr align="left">
      <td class="content_yellow">
      <input name="poC" type="button"  value="订购单取消" onclick="order_cancel()">
      </td>
    </tr>
			  
</html:form>
</BODY>
<%
}catch(Exception e){
	e.printStackTrace();
}%>
</HTML>
<script language="JavaScript">

	function view(id){
			middleOpen("saleInfoAction.do?method=saleInfoDetail&flag=view&saleNo="+id,"","width=750,height=500,left=50,top=10,menubar=no,toolbar=no,resizable=yes,scrolling=yes,scrollbars=yes");
	}
	
	function order_print(orderNo){
				window.open("partPoAction.do?method=poFormPrint&orderNo="+orderNo);
	}
	
	function order_cancel(){
			if(chk()!=''&&chk()!=null){
				if(confirm("是否确认取消订单？")){
	    		document.forms[0].ids.value=chk();
	    		document.forms[0].poC.disabled=true;
	    		document.forms[0].action="partPoAction.do?method=orderCancel";
					document.forms[0].submit();  		
				}
	    }else{
	  		alert("请先选择零件！");
	    }
	}
	
</script>
