<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri=  "/WEB-INF/tlds/common.tld" prefix="comtld" %>
<%@ page import="java.util.ArrayList"%> 

<HTML>
<HEAD>
<title>����ά��</title>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/styles2.css" rel="stylesheet" type="text/css" />
<script language=javascript src="js/commonSelect.js"></script>

</HEAD>
<%
	try{
		ArrayList factoryList = (ArrayList)request.getAttribute("factoryList");
		int count=0;
		if(factoryList!=null){
			count=Integer.parseInt((String)factoryList.get(0));
		}


%>
<body >
<html:form action="factoryInfoAction.do?method=factoryList" method="post" >
<input name="ids" type="hidden">

 <table width="100%" border="0" cellpadding="0" cellspacing="1" class="content12">  
   
                <tr class="tableback1"> 
                  <td width="98"> ����ID��</td>
                  <td width="126"><html:text property="factoryId"  styleClass="form" size="16" maxlength="3" /></td>
                   
                  <td width="98">�������ƣ�</td>
                  <td width="121"><html:text property="factoryName"  styleClass="form" size="16" maxlength="32" /></td>
                   <td width="98"> ���У�</td>
                  <td width="126"><html:text property="cityName"  styleClass="form" size="16" maxlength="16" /></td>
                  
                </tr>
               
                <tr class="tableback2"> 
                 <td width="98"> ��ϵ�ˣ�</td>
                  <td width="126"><html:text property="linkman"  styleClass="form" size="16" maxlength="16" /></td>
                  
                  <td width="98"> ��ϵ�绰��</td>
                  <td width="126"><html:text property="phone"  styleClass="form" size="16" maxlength="32" /></td>
                   
                  <td width="98">������ַ��</td>
                  <td width="121"><html:text property="address"  styleClass="form" size="16" maxlength="200" /></td>
                  
                </tr>
      

                </table>
	<h3 class="underline"><p align="left">&nbsp;&nbsp;<input name="query" type="submit" value="�� ѯ" ></p></h3>
<br>
  <table width="100%" border="0" cellpadding="0" cellspacing="1" class="content12">
      <tr bgcolor="#CCCCCC"> 
       <td width="10"><input name="allbox" type="checkbox" onClick="checkAll(this);"></td>
        <td  ><strong>����ID</strong></td>
  		<td><strong>�������� </strong></td>
  		<td><strong>��ϵ��</strong></td>
  		<td><strong>��ϵ�绰</strong></td>
  		<td><strong>����</strong></td>
  		<td><strong>������ַ</strong></td>
        <td><strong>��������</strong></td>
        <td><strong>ʡ��</strong></td>
        <td><strong>����</strong></td>
      </tr>

	 <%
       if(factoryList!=null){
       	    String strTr="";
      	    for(int i=1;i<factoryList.size();i++){
      	        if(i%2==0) strTr="tableback2";
      	        else strTr="tableback1";
      		String[] temp=(String[])factoryList.get(i);
      %>      
      <tr class="<%=strTr%>"> 
		  <td align=center><input type="checkbox" name="chk" value="<%=temp[0]%>"></td>           
		  		<td style="cursor: hand"><A href="javascript:f_modify('<%=temp[0]%>')"><%=temp[0]%></A></td>
          <td ><%=temp[1]==null?"":temp[1]%></td>
          <td ><%=temp[2]==null?"":temp[2]%></td>
          <td ><%=temp[3]==null?"":temp[3]%></td>
          <td ><%=temp[4]==null?"":temp[4]%></td>
          <td ><%=temp[5]==null?"":temp[5]%></td>
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
    
              <tr align="left">
                    <td class="content_yellow">
                     <input type="button"  value='����' onclick="f_add()">
                     <input type="button"  value='ɾ��' onclick="f_delete()">
                    </td>
                  </tr>
			  

	</html:form>

</BODY>


<SCRIPT >
function f_add(){
	document.forms[0].action="factoryInfoAction.do?method=factoryInit"; 
	document.forms[0].submit();
}
function f_modify(chkVal){
	
		document.forms[0].action="factoryInfoAction.do?method=factoryEdit";
		document.forms[0].ids.value=chkVal; 
		document.forms[0].submit();
	
}

function f_delete(){
	var chkVal=chk();
	if(chkVal==null||chkVal==''){
		alert('����ѡ���¼!');
	}else{
		if(window.confirm("ȷ��ɾ���ü�¼��")){
			document.forms[0].action="factoryInfoAction.do?method=deleteFactory";
			document.forms[0].ids.value=chkVal; 
			document.forms[0].submit();
		}
	}

}


</SCRIPT>

<%
}catch(Exception e){
	e.printStackTrace();
}%>
</HTML>