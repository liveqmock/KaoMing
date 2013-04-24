<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ page import="java.util.List"%> 
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.dne.sie.common.tools.DicInit"%>

<html>
<head>
<title>kaoming</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/styles.css" rel="stylesheet" type="text/css" />
<script language=javascript src="js/inputValidation.js"></script>
<script language=javascript src="js/checkValid.js"></script>
</head>
<%
		
try{
	
	String flag = (String)request.getAttribute("flag");

	ArrayList statusList = (ArrayList)DicInit.SYS_CODE_MAP.get("BUG_STATUS");
	ArrayList typeList = (ArrayList)DicInit.SYS_CODE_MAP.get("BUG_TYPE");
	
	List replyList = (List)request.getAttribute("replyList");
%>
<body>
<html:form action="bugInfoAction.do?method=bugInfoEdit" method="post" >
<html:hidden  property="id" /> 
<br>
<table width="99%" border="0" align="center" cellpadding="2" cellspacing="1" class="content12">
  <tr>
    <td>��ѯ����&gt;���԰�&gt;���������ϸ</td>
  </tr>
</table>
<br>
<table width="99%" border="0" align="center" cellpadding="2" cellspacing="1" class="content12">
  <tr> 
    <td height="2" colspan="6" bgcolor="#677789"></td>
  </tr>
  <tr> 
    <td height="6"  colspan="6" bgcolor="#CECECE"></td>
  </tr> 
      
       
        <tr class="tableback1"> 
          <td width="10%"> ���⣺<font color='red'>*</font></td>
          <td width="30%"><html:text property="subject"  styleClass="form" size="50"   maxlength="100"/></td>
          
          <td width="7%"> ���ͣ�</td>
          <td width="12%"><html:select property="type"  styleClass="form">	
					<%for(int i=0;typeList!=null&&i<typeList.size();i++){
	                  String[] temp=(String[])typeList.get(i);
	                 %>
	                  <html:option value="<%=temp[0]%>"><%=temp[1]%></html:option>
	                <%}%>
      			   </html:select></td>
           
        
        <%if("edit".equals(flag)){ %>
          <td width="7%">״̬ ��</td>
          <td width="12%"><html:select property="status"  styleClass="form">	
					<%for(int i=0;statusList!=null&&i<statusList.size();i++){
	                  String[] temp=(String[])statusList.get(i);
	                 %>
	                  <html:option value="<%=temp[0]%>"><%=temp[1]%></html:option>
	                <%}%>
      			   </html:select></td>
           <%}else{ %>
           <td></td><td></td>
           <%} %>
        </tr>
        
       <tr class="tableback2"> 
	    <td valign="top">������</td>
	    <td colspan="5">
	    	<html:textarea property="description" style="width:90% " styleClass="form"></html:textarea> </td>
	   </tr>
	  
	   <tr> <td id="t1" colspan=6>&nbsp;</td> </tr>
  
  <tr> 
    <td height="2" colspan="6" bgcolor="#ffffff"></td>
  </tr>
  <tr> 
    <td height="1" colspan="6" bgcolor="#677789"></td>
  </tr>
  <tr> 
    <td  colspan="6"><div align="right"> 
        <input type="button" name="save" value="����"  onclick="f_save()">
      </div></td>
  </tr>
  

</table>


<table width="100%" border="0" align="center" cellpadding="2" cellspacing="1" class="content12">
 <tr> 
    <td height="2" colspan="16"  bgcolor="green"></td>
  </tr>

        <tr class="tableback2"> 
         <td width="10%">�ظ���</td>
         <td ><textarea name="reply" style="width:90% " class="form"></textarea> </td>
         
        </tr>
   
  			
        <tr >
         <td colspan="6"><input name="addPaymentButton" type="button"  value="ȷ��" onclick="addReply()"></td>
        </tr>
        
       </table>
	   <br>
   
	  
	<table  width="100%" border="0" align="center" cellspacing="2" cellpadding="1" class="content12" border="0" id="paidParentTable">
	 <tr>
    <td><B>��ʷ�ظ���Ϣ</B></td>
     </tr>
	<tr><td width="100%">
	<div class="scrollDiv" id="PAIDScrollDiv">
       <table width="100%" border="0" cellpadding="0" cellspacing="1" class="content12" id="paidTable">
	<THEAD>
        <tr bgcolor="#CCCCCC">
         <td width="75%"><B>����</B></td>
         <td><B>�ظ���</B></td>
         <td><B>����</B></td>
        </tr>
      </THEAD>
      <TBODY>
      <%for(int i=0;replyList!=null&&i<replyList.size();i++){
      		String[] temp=(String[])replyList.get(i);
      		String strTr="tableback1";
      		if(i%2==0) strTr="tableback2";
      %>
         <tr class="<%=strTr%>"> 
         <td><%=temp[1]%></td>
         <td><%=temp[2]%></td>
         <td><%=temp[3]%></td>
        </tr>
      <%}%>
	   </TBODY>
       </table>
       </div> <!--end of PAIDScrollDiv-->
</td></tr>
</table> <!--end of paidParentTable-->


</html:form>
</body>
<%
}catch(Exception e){
	e.printStackTrace();
}%>
</html>

<SCRIPT >
function f_save(){
	if(checkIsNull()){
		if(document.forms[0].id.value==null||document.forms[0].id.value==0){
			document.forms[0].action="bugInfoAction.do?method=insertBugInfo"; 
		}else{
			document.forms[0].action="bugInfoAction.do?method=updateBugInfo"; 
		}
		
		document.forms[0].submit();
	}
}


function checkIsNull() {
	
	if(f_isNull(document.forms[0].subject,'����')&&f_maxLength(document.forms[0].description,'����',1000)){
    	
    	return true;
	}else{
		return false;
	}
	
}

function addReply(){
	if(f_isNull(document.forms[0].reply,'�ظ�')&&f_maxLength(document.forms[0].reply,'�ظ�',1000)){
		document.forms[0].action="bugInfoAction.do?method=replyAdd"; 
		document.forms[0].submit();
	}
}


</script>

</SCRIPT>