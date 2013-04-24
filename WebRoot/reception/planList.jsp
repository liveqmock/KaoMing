<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri=  "/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri=  "/WEB-INF/tlds/common.tld" prefix="comtld" %>
<%@ page import="java.util.ArrayList"%> 
<%@ page import="java.util.List"%> 
<%@ page import="com.dne.sie.reception.bo.PartPoBo"%> 
<%@ page import="com.dne.sie.common.tools.CommonSearch"%> 
<%@ page import="com.dne.sie.common.tools.DicInit"%> 

<HTML>
<HEAD>
<title>�������</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/styles2.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/autocomplete.css" />
<script type="text/javascript" src="js/prototype.js"></script> 
<script type="text/javascript" src="js/autocomplete.js"></script>
<script language=javascript src="js/common.js"></script>
<script language=javascript src="js/commonSelect.js"></script>
<script language=javascript src="js/checkValid.js"></script>

</HEAD>
<%
		ArrayList poList = (ArrayList)request.getAttribute("poList");
		int count=0;
		if(poList!=null){
			count=Integer.parseInt((String)poList.get(0));
		}
		
		String orderNo = request.getAttribute("orderNo")==null?"":(String)request.getAttribute("orderNo");
		
		List monthList=PartPoBo.getInstance().getOrderMonthList();
		ArrayList orderTypeList = (ArrayList)DicInit.SYS_CODE_MAP.get("ORDER_TYPE");
		
		Float[] poPayAmounts = (Float[])request.getAttribute("poPayAmounts");
		List factoryList=CommonSearch.getInstance().getFactoryList();
		ArrayList transportModeList = (ArrayList)DicInit.SYS_CODE_MAP.get("TRANSPORT_MODE");	
		
%>
<body >

<html:form action="partPoAction.do?method=planList" method="post" >
<html:hidden property="poNo"/>
<input type="hidden" name="ids" >

<table align=center width="99%">
				
                <tr class="tableback1"> 
                  <td width="98"> ѯ�۵��ţ�</td>
                  <td width="126"><html:text property="saleNo"  styleClass="form" size="16" /></td>
                   
                  <td width="80">�ͻ����ƣ�</td>
                  <td width="150"><input type="hidden" name=customerId> 
                  	<input name="customerName" type="text" class="form" size="30" >
                  </td>
                 </tr>
                 <tr class="tableback2"> 
                  
                   	<td width="98"> �����·ݣ�</td>
                  	<td width="126"><html:select styleClass="form" property="orderMonth"  >
	     		           <html:option value="">ȫ��</html:option>
	     		           <%for(int i=0;monthList!=null&&i<monthList.size();i++){
	                  	String temp=(String)monthList.get(i);
	                	%>
	                		<html:option value="<%=temp%>"><%=temp%></html:option>
	                 	<%}%>
	     		           </html:select></td>
                  
                	<td width="98"> �������ͣ�</td>
                  	<td width="126"><html:select styleClass="form" property="orderType"  >
	     		        <html:option value="">ȫ��</html:option>
	     		        <%for(int i=0;orderTypeList!=null&&i<orderTypeList.size();i++){
	                  	String[] temp=(String[])orderTypeList.get(i);
                	%>
                		<html:option value="<%=temp[0]%>" ><%=temp[1]%></html:option>
                 	<%}%>
	     		           </html:select></td>
                  
                </tr>
               
                </table>   
	<h3 class="underline"><p align="left">&nbsp;&nbsp;<input type="submit" value="�� ѯ"></p></h3>
               <table width="100%"  border="0" cellspacing="4" cellpadding="0">
                <tr>
                   <td height="1" background="images/i_line.gif"></td>
                </tr>
               </table> 
  <table width="100%" border="0" cellpadding="0" cellspacing="1" class="content12">

      <tr bgcolor="#CCCCCC"> 
        <td width="10"><input name="allbox" type="checkbox" onClick="checkAll(this);"></td>
        <td><strong>ѯ�۵���</strong></td>
  			<td><strong>�ͻ�����</strong></td>
  			<td><strong>�Ϻ�</strong></td>
  			<td><strong>�������</strong></td>
  			<td><strong>����(����)</strong></td>
        <td><strong>����</strong></td>
        <td><strong>������</strong></td>
        <td><strong>����</strong></td>
        <td><strong>����</strong></td>
        <td><strong>��������</strong></td>
        <td><strong>������ַ</strong></td>
      </tr>

	<%
	   
       if(poList!=null){
       	    String strTr="";
      	    for(int i=1;i<poList.size();i++){
      	        if(i%2==0) strTr="tableback2";
      	        else strTr="tableback1";
      		String[] temp=(String[])poList.get(i);
      		
      %>      
      <tr class="<%=strTr%>"> 
          <td align=center><input type="checkbox" name="chk" value="<%=temp[0]%>"></td>      
		  		<td style="cursor: hand"><A href="javascript:view('<%=temp[1]%>')"><%=temp[1]%></A></td>
          <td ><%=temp[2]==null?"":temp[2]%></td>
          <td> <%=temp[3]==null?"":temp[3]%> </td>
          
          <td >&nbsp;<%=temp[4]==null?"":temp[4]%></td>
          <td >&nbsp;<%=temp[5]==null?"":temp[5]%></td>
          <td ><%=temp[6]==null?"":temp[6]%></td>
          <td ><%=temp[7]==null?"":temp[7]%></td>
          
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
          <td ><%=temp[8]==null?"":temp[8]%></td>
          <td ><%=temp[13]==null?"":temp[13]%></td>
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
<br>
			<table width="100%" border="0" cellpadding="0" cellspacing="1" class="content12">
          		
          <tr>
						<td width="10%">�������ţ���</td>
						<td  width="25%"><input name="orderNo" size=20 value="<%=orderNo%>" disabled > </td> 
          	<td  width="30%">�������̣�<font color='red'>*</font>
	          <html:select styleClass="form" property="factoryId"  onchange="javascript:setFactoryName(this)" value="TWKM">
	              	 
	              	 <%for(int i=0;factoryList!=null&&i<factoryList.size();i++){
	                  Object[] temp=(Object[])factoryList.get(i);
	                 %>
	                  <html:option value="<%=(String)temp[0]%>" ><%=(String)temp[1]%></html:option>
	                <%}%>
	          	</html:select><input name="factoryName" type="hidden">
	          </td>
	          <td  width="35%">���䷽ʽ��<font color='red'>*</font>
	          <html:select styleClass="form" property="transportMode"  >
	              	 
	              	 <%for(int i=0;transportModeList!=null&&i<transportModeList.size();i++){
	                  	String[] temp=(String[])transportModeList.get(i);
	                 %>
	                  <html:option value="<%=temp[0]%>"><%=temp[1]%></html:option>
	                <%}%>
	          	</html:select>
	          </td>
					</tr>
					
					<tr>
						<td width="10%">��ע��</td>
						<td colspan="6"><textarea name="remark" cols="8" rows="2"  style="width:80%"></textarea></td>
					</tr>
					
  		</table>
  <br>
  		<tr align="left">
        <td class="content_yellow">
				<input name="poConfirm" type="button"  value="����ȷ��" onclick="f_po_confirm()">
				<input name="poConfirm" type="button"  value="����ȡ��" onclick="f_po_cancel()">
        </td>
      </tr>
			  
</html:form>
</BODY>
</HTML>
<script language="JavaScript">
new AutoTip.AutoComplete("customerName", function() {
	 return "customerInfoAction.do?method=getCustInfo&inputValue=" + escape(this.text.value);
});

	function view(id){
			middleOpen("saleInfoAction.do?method=saleInfoDetail&flag=view&saleNo="+id,"","width=950,height=500,left=50,top=10,menubar=no,toolbar=no,resizable=yes,scrolling=yes,scrollbars=yes");
	}
	
	function f_po_confirm(){
		var cId=chk();
		if(f_isNull(document.forms[0].factoryId,'��������')){
		    if(cId!=''&&cId!=null){
		    	if(document.forms[0].remark.value==''||f_maxLength(document.forms[0].remark,'��ע',250)){
		    		
		    		document.forms[0].ids.value=cId;
		    		document.forms[0].poConfirm.disabled=true;
		    		document.forms[0].action="partPoAction.do?method=sendPo";  
					document.forms[0].submit();  		
				}
		    }else{
		  		alert("����ѡ�������");
		    }
	    }
	}
	
	function f_po_cancel(){
		var cId=chk();
	    if(cId!=''&&cId!=null){
	    	if(document.forms[0].remark.value==''||f_maxLength(document.forms[0].remark,'��ע',250)){
	    		
	    		document.forms[0].ids.value=cId;
	    		document.forms[0].poConfirm.disabled=true;
	    		document.forms[0].action="partPoAction.do?method=cancelPo";  
				document.forms[0].submit();  		
			}
	    }else{
	  		alert("����ѡ�������");
	    }
	    
	}
	
	function setFactoryName(obj){
			
			for (var i=0; i<obj.options.length; i++){
				if(obj.options[i].selected){
						document.forms[0].factoryName.value=obj.options[i].text;
						break;
				}
			}
	}
	
</script>
