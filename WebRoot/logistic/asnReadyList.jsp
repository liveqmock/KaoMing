<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri=  "/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri=  "/WEB-INF/tlds/common.tld" prefix="comtld" %>
<%@ page import="java.util.ArrayList"%> 
<%@ page import="java.util.List"%> 
<%@ page import="com.dne.sie.common.tools.CommonSearch"%> 
<%@ page import="com.dne.sie.common.tools.DicInit"%> 

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>asnReady</title>

<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/styles2.css" rel="stylesheet" type="text/css" />

<script language=javascript src="js/ajax.js"></script>
<script language=javascript src="js/inputValidation.js"></script>
<script language=javascript src="js/checkValid.js"></script>
<SCRIPT language="javascript" src="js/commonSelect.js"></SCRIPT>

</head>
<%
	try{
		ArrayList asnList = (ArrayList)request.getAttribute("asnList");
		
		int count=0;
		if(asnList!=null){
			count=Integer.parseInt((String)asnList.get(0));
		}

		List customerList=CommonSearch.getInstance().getAsnCustomerList();
		ArrayList payStatusList = (ArrayList)DicInit.SYS_CODE_MAP.get("PAY_STATUS");
		ArrayList billingStatusList = (ArrayList)DicInit.SYS_CODE_MAP.get("BILLING_STATUS");
		
		String asnNo = request.getAttribute("asnNo")==null?"":(String)request.getAttribute("asnNo");
%>
<body onload="javascript:getAsnInfo()">
<html:form action="asnAction.do?method=asnReadyList" method="post" >
<input type="hidden" name="chkId">
<table align=center width="99%">
				
				<tr class="tableback1"> 
                  
            <td width="120">�ͻ����ƣ�<FONT color="red">*</FONT></td>
            <td width="140"><html:select styleClass="form" property="customerId"  onchange="f_change()" >
            	 	<html:option  value=""> ==��ѡ��== </html:option>
        	 			<%for(int i=0;customerList!=null&&i<customerList.size();i++){
                	Object[] temp=(Object[])customerList.get(i);
              	%>
              		<html:option value="<%=(String)temp[0]%>"><%=(String)temp[1]%></html:option>
               	<%}%>
                
              </html:select>
            </td>
            <td > ����״̬��</td>
                  <td ><html:select styleClass="form" property="payStatus"  >	
									<html:option  value="">ȫ��</html:option>
									<%for(int i=0;payStatusList!=null&&i<payStatusList.size();i++){
                  	String[] temp=(String[])payStatusList.get(i);
                	%>
                		<html:option value="<%=temp[0]%>"><%=temp[1]%></html:option>
                 	<%}%>
      						</html:select>
     		   		</td>
     		   		<td > Ʊ��״̬��</td>
                  <td ><html:select styleClass="form" property="billingStatus"  >	
									<html:option  value="">ȫ��</html:option>
									<%for(int i=0;billingStatusList!=null&&i<billingStatusList.size();i++){
                  	String[] temp=(String[])billingStatusList.get(i);
                	%>
                		<html:option value="<%=temp[0]%>"><%=temp[1]%></html:option>
                 	<%}%>
      						</html:select>
     		   		</td>
          </tr>
         
          <tr class="tableback2"> 
          	<td width="98">ѯ�۵��ţ�</td>
            <td width="126"><html:text property="saleNo"  styleClass="form" size="16" /></td>
            <td width="98">�Ϻţ�</td>
            <td width="126"><html:text property="stuffNo"  styleClass="form" size="16" /></td>
            <td width="98">������ƣ�</td>
            <td width="121"><html:text property="skuCode"  styleClass="form" size="16" /></td>
          </tr>
          </table>
	<h3 class="underline"><p align="left">&nbsp;&nbsp;<input name="query" type="button" value="��ѯ" onclick="f_query()"></p></h3>
	
							<table width="100%"  border="0" cellspacing="4" cellpadding="0">
                  <tr>
                     <td height="1" background="images/i_line.gif"></td>
                  </tr>
               </table>      

                <table width="100%" border="0" cellpadding="0" cellspacing="1" class="content12">
                   <tr>  
                    	<td width="80">�������ţ�<FONT color="red">*</FONT></td>
                    	<td ><input name="asnNo" class="form" readonly value="<%=asnNo%>" ></td>
                    	<td >�����ص㣺</td>
                    	<td ><html:text property="shippingAddress"  styleClass="form"  /></td>
                    	<td >��ϵ�ˣ�</td>
                    	<td ><html:text property="linkman"  styleClass="form"  /></td>
                   </tr>
                   <tr>
					    				<td >�ͻ����ƣ�</td>
                    	<td ><html:text property="customerName"  styleClass="form"  /></td>
                      <td >�ͻ��绰��</td>
                    	<td ><html:text property="customerPhone"  styleClass="form"  /></td>
                      <td >������ע��</td>
                    	<td ><html:text property="shippingRemark"  styleClass="form"  /></td>
                   </tr>
            	</table>
               <table width="100%"  border="0" cellspacing="4" cellpadding="0">
                <tr>
                   <td height="1" background="images/i_line.gif"></td>
                </tr>
               </table> 
	 <table  id="hard" width="99%" border="0" cellpadding="0" cellspacing="1" class="content12">
      		<tr bgcolor="#CCCCCC"> 
          <td align=center><input name="allbox" type="checkbox" onClick="checkAll(this);"></td>                            		
        	<td><strong>ѯ�۵���</strong></td>
        	<td><strong>�ͻ�</strong></td>
  			  <td><strong>�Ϻ�</strong></td>
  			  <td><strong>�������</strong></td>
  			  <td><strong>����</strong></td>
  			  <td><strong>ѯ��ʱ��</strong></td>
  			  <td><strong>����״̬</strong></td>
  			  <td><strong>��Ʊ״̬</strong></td>
  			  <td><strong>���״̬</strong></td>
  			  <td><strong>������</strong></td>
  		   </tr>
      
	 <%
       if(asnList!=null){
       	    String strTr="";
      	    for(int i=1;i<asnList.size();i++){
      	        if(i%2==0) strTr="tableback2";
      	        else strTr="tableback1";
      		String[] temp=(String[])asnList.get(i);
      %>      
      <tr class="<%=strTr%>"> 
		  <td align=center><input type="checkbox" name="chk" value="<%=temp[0]%>"></td>            
		  <td style="cursor: hand"><A href="javascript:view('<%=temp[1]%>')"><%=temp[1]%></A></td>
          <td ><%=temp[9]==null?"":temp[9]%></td>
          <td ><%=temp[2]==null?"":temp[2]%></td>
          <td ><%=temp[3]==null?"":temp[3]%></td>
          <td ><%=temp[4]==null?"":temp[4]%></td>
          <td ><%=temp[5]==null?"":temp[5]%></td>
          <td ><%=temp[6]==null?"":temp[6]%></td>
          <td ><%=temp[7]==null?"":temp[7]%></td>
          <td ><%=temp[10]==null?"":temp[10]%></td>
          <td ><%=temp[8]==null?"":temp[8]%></td>
        </tr>      
      <%
      }}else{%> 
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
                     <input name="consign" type="button"  value='��������' onclick="f_submit('A')">
                     <!-- <input name="consign" type="button"  value='ά��Я��' onclick="f_submit('B')"> -->
                    </td>
                  </tr>
  
</html:form>
	  
</body>
<%
}catch(Exception e){
	e.printStackTrace();
}%>
</html>
<script language="JavaScript">

	function f_query(){
			//if(f_isNull(document.forms[0].customerId,'�ͻ�����')){
					document.forms[0].submit();
			//}
	
	}

	function chkAsn(){
			var chk=false;
			if(f_isNull(document.forms[0].shippingAddress,'�����ص�')&&f_isNull(document.forms[0].linkman,'��ϵ��')
				&&f_isNull(document.forms[0].customerName,'�ͻ�����')&&f_isNull(document.forms[0].asnNo,'��������')
    		&&f_maxLength(document.forms[0].shippingRemark,'������ע',256)){
		    	chk=true;
		    	
    	}
			return chk;
	}
	
	function view(id){
			window.open("saleInfoAction.do?method=saleInfoDetail&flag=view&saleNo="+id,"","scrollbars=yes,width=700,height=600");
	}
	
	var ajax = new sack();
	function getAsnInfo(){
		var custId = document.forms[0].customerId.value;
		if(custId==""){
				setInfo("","","","");
		}else{
			//deleteRow();
			document.forms[0].query.disabled=true;
			ajax.setVar("custId", custId); 
			ajax.setVar("method", "getAsnInfo");							 
			ajax.requestFile = "asnAction.do";					   
			ajax.method = "GET";											   
			ajax.onCompletion = setAsnInfo;			  
			ajax.runAJAX();	
		}		
	}
	function setAsnInfo(){
		var returnXml = ajax.responseXML;
		var flag = returnXml.getElementsByTagName("flag");									
		if(eval(flag)) {
			var shippingAddress=unescape(returnXml.getElementsByTagName("shippingAddress")[0].firstChild.nodeValue);
			var linkman=unescape(returnXml.getElementsByTagName("linkman")[0].firstChild.nodeValue);
			var customerName=unescape(returnXml.getElementsByTagName("customerName")[0].firstChild.nodeValue);
			var customerPhone=returnXml.getElementsByTagName("customerPhone")[0].firstChild.nodeValue;
			//var customerId=unescape(returnXml.getElementsByTagName("customerId")[0].firstChild.nodeValue);
		
			setInfo(shippingAddress,linkman,customerName,customerPhone);
			
		}else{
			alert("�ͻ���Ϣ��������ϵ����Ա");
		}	
		document.forms[0].query.disabled=false;
	}
	
	function setInfo(shippingAddress,linkman,customerName,customerPhone){
		if(shippingAddress=="null")  shippingAddress="";
		if(linkman=="null")  linkman="";
		if(customerName=="null")  customerName="";
		if(customerPhone=="null")  customerPhone="";
		//if(customerId=="null")  customerPhone="";
		
		document.all.shippingAddress.value=shippingAddress;
		document.all.linkman.value=linkman;
		document.all.customerName.value=customerName;
		document.all.customerPhone.value=customerPhone;
		//document.all.customerId.value=customerId;
		
	}
	function deleteRow(){
		var thetable=document.getElementById('hard');
		var tbls=thetable.rows;
		for(var i=0;i<tbls.length-3;i++)
		{
			thetable.deleteRow(i+1);
			i=i-1;
			
		}
	}
	
	function f_submit(type){
		if(f_isNull(document.forms[0].customerId,'�ͻ�����')){
			if(chkAsn()&&confirm("ȷ�ϳ�����")){
				var ids=chk();
				if(ids!=''&&ids!=null){
						document.forms[0].chkId.value=ids;
						document.forms[0].consign.disabled=true;
						document.forms[0].action="asnAction.do?method=shipConfirm&asnType="+type;
						document.forms[0].submit();
				
				}
			}
		}
	}
	
	function f_change(){
		f_query();
	
	}
	
	
	
</script>