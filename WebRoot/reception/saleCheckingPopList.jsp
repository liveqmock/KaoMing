<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri=  "/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri=  "/WEB-INF/tlds/common.tld" prefix="comtld" %>
<%@ taglib uri=  "/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ page import="java.util.ArrayList"%> 
<%@ page import="com.dne.sie.common.tools.CommonSearch"%> 

<html>
<head>
<base target='_self'>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>orderIn</title>

<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/styles2.css" rel="stylesheet" type="text/css" />

</head>
<%
	try{
		ArrayList saleList = (ArrayList)request.getAttribute("saleList");
		//String customerName = (String)request.getAttribute("customerName");
		//String createDate = (String)request.getAttribute("createDate");
		int count=0;
		if(saleList!=null){
			count=Integer.parseInt((String)saleList.get(0));
		}


%>
<body onload="pageInit()">
<html:form action="saleInfoAction.do?method=saleCheckList" method="post">
<html:hidden property="customerId"/>
<html:hidden property="dsaleNo"/>
<html:hidden property="saleStatus"/>
<input type="hidden" name="flag" value="pop">
<table border="0" cellpadding="0" cellspacing="0" width="99%">
<tr>
	
	<td width="99%" >
	<table cellpadding="0" cellspacing="0" width="100%"><tr><td bgcolor="#3399cc"><img alt="" height="1" width="1"></td></tr></table>
	<table cellspacing="0" cellpadding="2" border="0" width="100%">
   	<tr>
   		<td bgcolor="#e8f4f7"><font size="3"><b>&nbsp;���ۺ���ϲ�</b></font></td>
   	</tr>
   	</table>	
	</td>
</tr>
</table>

<br>
<table align=center width="99%">
				
                <tr class="tableback1"> 
                  <td width="98"> ѯ�۵��ţ�</td>
                  <td width="126"><html:text property="saleNo"  styleClass="form" size="16" /></td>
                  
                  <td width="98">�ͻ����ƣ�</td>
                  <td width="121"><bean:write property="customerName" name="salesInfoForm" /></td>
                   <td width="98">ѯ��ʱ�䣺</td>
                  <td width="126"><bean:write property="createDate" name="salesInfoForm" /></td>
                  
                </tr>
               
                <tr class="tableback2"> 
                  <td width="98"> �Ϻţ�</td>
                  <td width="126"><html:text property="stuffNo"  styleClass="form" size="16" /></td>
                  <td width="98">������ƣ�</td>
                  <td width="121"><html:text property="skuCode"  styleClass="form" size="16" /></td>
                   <td width="98"> ���ͣ�</td>
                  <td width="126"><html:text property="modelCode"  styleClass="form" size="16" /></td>
                  
                </tr>
                </table>
	<h3 class="underline"><p align="left">&nbsp;&nbsp;<input name="query" type="button" value="�� ѯ" onclick="f_query()"></p></h3>
	 <table width="99%" border="0" cellpadding="0" cellspacing="1" class="content12">
      		<tr bgcolor="#CCCCCC"> 
      		<td width="10">&nbsp;</td>
        	<td><strong>ѯ�۵���</strong></td>
  			  <td><strong>�ͻ�����</strong></td>
  			  <td><strong>�ͻ��绰</strong></td>
  			  <td><strong>�����ص�</strong></td>
  			  <td><strong>ѯ��ʱ��</strong></td>
  			  <td><strong>������</strong></td>
  			  <td><strong>�������</strong></td>
  		   </tr>
      
	 <%
       if(saleList!=null){
       			CommonSearch cs = CommonSearch.getInstance();
       	    String strTr="";
      	    for(int i=1;i<saleList.size();i++){
      	        if(i%2==0) strTr="tableback2";
      	        else strTr="tableback1";
      		String[] temp=(String[])saleList.get(i);
      %>      
      <tr class="<%=strTr%>"> 
      		<td align="center"><input type="radio" name="goback" value="<%=temp[0]%>" onclick="setValue(this.value)"></td>
		  		<td ><%=temp[0]%></td>
          <td ><%=temp[1]==null?"":temp[1]%></td>
          <td ><%=temp[2]==null?"":temp[2]%></td>
          <td ><%=temp[10]==null?"":temp[10]%></td>
          <td ><%=temp[3]==null?"":temp[3]%></td>
          <td ><%=temp[11]==null?"":temp[11]%></td>
          <td ><%=cs.getStuffNos(temp[0])%></td>
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
         <input type="button"  value='�ϲ�' onclick="f_returnValue()">
         <input type="button"  value='�ر�' onclick="self.close()">
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

	function pageInit(){
		document.forms[0].txtNumPerPage.value = 3;
		document.forms[0].txtNumPerPage.readOnly = true;
	}
	
	var saleNo='';
	function setValue(val){
			saleNo=val;
	
	}
	
	function f_returnValue(){
		if(saleNo==''){
			alert("����ѡ��ѯ�۵�");
		}else if(confirm("�ϲ���2����Ϊ1�����޷��������Ƿ�ȷ�ϣ�")){
			returnValue=saleNo;
	  	window.close();
	  }
	}
	
	function f_query(){
			document.forms[0].query.disabled=true;
			document.forms[0].submit();
	}
	
</script>