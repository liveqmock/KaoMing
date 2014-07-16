<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ page import="com.dne.sie.common.tools.DicInit"%>
<%@ page import="com.dne.sie.common.tools.CommonSearch"%> 
<%@ page import="java.util.ArrayList"%> 
<%@ page import="java.util.List"%> 
<html>
<head>
<title>accountInfo</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/styles.css" rel="stylesheet" type="text/css" />
<script language=javascript src="js/inputValidation.js"></script>
<script language=javascript src="js/checkValid.js"></script>
<script language=javascript src="js/popCalendar.js"></script>
<script language=javascript src="js/popCustomerInfo.js"></script>
</head>
<%
	List customerList=CommonSearch.getInstance().getCustomerList();
	ArrayList payTypeList = (ArrayList)DicInit.SYS_CODE_MAP.get("FEE_PAY_TYPE");
	ArrayList empList = (ArrayList)request.getAttribute("empList");
	String flag=(String)request.getAttribute("flag")==null?"":(String)request.getAttribute("flag");
	
	String tag=(String)request.getAttribute("tag");
	
%>
<body>
<html:form action="accountItemAction.do?method=accountInit" method="post" >
<html:hidden property="accountId"/>
<html:hidden property="subjectAllName"/>
<html:hidden property="subjectId"/>
<html:hidden property="delFlag"/>

<br>
<table width="99%" border="0" align="center" cellpadding="2" cellspacing="1" class="content12">
  <tr>
    <td>����&gt;���ù���&gt;������ϸ</td>
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
    <td>�������ڣ�</td>
    <td ><html:text  styleClass="form" property="feeDate1"   size="16" />
    <a onclick="event.cancelBubble=true;" href="javascript:showCalendar('imageOne',true,'feeDate1');">
		<img src="googleImg/icon/calendar.gif" id="imageOne" width="18" height="18" border="0" align="absmiddle">
	</a>
    </td>
    
    <td>�������ͣ�</td>
    <td ><html:select property="payType"  styleClass="form">	
		<%for(int i=0;i<payTypeList.size();i++){
                  String[] temp=(String[])payTypeList.get(i);
                 %>
                  <html:option value="<%=temp[0]%>"><%=temp[1]%></html:option>
                <%}%>
      	</html:select> </td>
      	
    <%if("edit".equals(flag)){%>
    <td>ƾ֤�ţ�</td>
    <td ><html:text  styleClass="form" property="voucherNo"  maxlength="32"  size="24" disabled="true" /></td>
    <%}else{%>
		<td></td><td></td>
	<%}%>
  </tr>

  <tr class="tableback2"> 
  <td>��Ŀ��</td>
   <td ><html:text  styleClass="form" property="subjectName" size="16" readonly="true"/>
   		<a href="javascript:f_querySub()"><img src="googleImg/icon/search.gif" align="absmiddle" border="0"></a>
   </td>
   <td>��Ա��</td>
   <td ><html:select property="employeeCode"  styleClass="form">
   		<html:option value="">===��ѡ��===</html:option>
		<%for(int i=0;i<empList.size();i++){
                  String[] temp=(String[])empList.get(i);
                 %>
                  <html:option value="<%=temp[0]%>"><%=temp[1]%></html:option>
                <%}%>
      	</html:select></td>
   <td>����</td>
    <td ><html:text  styleClass="form" property="money"  maxlength="12"  size="16" onkeydown="f_onlymoney()" /></td>
  </tr>
  
  <tr class="tableback2">
  	<td>���������</td>
    <td ><html:text  styleClass="form" property="strRatio"  maxlength="12"  size="10" onkeydown="f_onlymoney()" />%</td>
    <td >�ͻ���</td>
    <td >
    	<html:hidden property="customerId"/>
    	<html:text  styleClass="form" property="customerName"  size="24"  />&nbsp;
    	<a href="javascript:showCustomerInfo('customerName')"><img src="googleImg/icon/search.gif" align="absmiddle" border="0"></a>    
    </td>
    <td >�ص㣺</td>
    <td ><html:text  styleClass="form" property="place"  maxlength="250"  size="24" /></td>
  </tr>
  <tr class="tableback1"> 
    <td valign="top">ժҪ��</td>
    <td colspan="5">
    	<html:textarea property="summary" style="width:90% " styleClass="form"></html:textarea> </td>
  </tr>
  <tr> 
    <td height="2" colspan="6" bgcolor="#ffffff"></td>
  </tr>
  <tr> 
    <td height="1" colspan="6" bgcolor="#677789"></td>
  </tr>
  <tr> 
    <td  colspan="6"><div align="right"> 
        <input type="button" name="save" value="����"  onclick="f_save()">
        <input type="button"  value="����"  onclick="f_back()">
      </div></td>
  </tr>
</table>
</body>
</html:form>
</html>

<SCRIPT >
function f_save(){
	
	if(checkIsNull()){
		<%if("init".equals(flag)){%>
		document.forms[0].action="accountItemAction.do?method=insertAccount"; 
		<%}else{%>
		document.forms[0].action="accountItemAction.do?method=updateAccount"; 
		<%}%>
		document.forms[0].submit();
	}
}


function checkIsNull() {
	if(f_isNull(document.forms[0].feeDate1,'��������')&&f_isNull(document.forms[0].subjectId,'��Ŀ')
		&&f_isNull(document.forms[0].money,'���')
    	&&f_maxLength(document.forms[0].summary,'ժҪ',256)){
    	
    		var payType=document.forms[0].payType.value;
    		var empCode=document.forms[0].employeeCode.value;
    		var custId=document.forms[0].customerId.value;
    		
    		if((payType=="XF")&&empCode==""){
    			alert("��ѡ����Ա");
    			return false;
    		}
    		if((payType=="YS"||payType=="YF")&&custId==""){
    			alert("��ѡ�񣺿ͻ�");
    			return false;
    		}
    		
    		var place=document.forms[0].place.value;
			if(place!=null&&place!="") document.forms[0].place.value=place.trim();
    		return true;
	}else{
			return false;
	}
	
}
function f_querySub(){
	var treeInfo=window.showModalDialog("accountItemAction.do?method=treeItemRadio&subId="+document.forms[0].subjectId.value+"&Rnd="+Math.random(),"","dialogHeight: 500px; dialogWidth: 200px; edge: Sunken; center: Yes; help: No; resizable: No; status: Yes;");
	if(treeInfo!=null){
		
		document.forms[0].subjectId.value=treeInfo[0];
		document.forms[0].subjectName.value=treeInfo[1];
		document.forms[0].subjectAllName.value=treeInfo[2];
		
		if(treeInfo[1]=='н��'){
			document.forms[0].place.value="����";
		}
	}
	
}

function f_back(){
	location="accountItemAction.do?method=accountList";
}

String.prototype.trim = function()
{
	return this.replace( /(^\s*)|(\s*$)/g, '' ) ;
}

</SCRIPT>
<%
if(tag!=null){
%>
	<SCRIPT>
		var tag="<%=tag%>";
		if(tag=="-1"){
				alert("��������ʧ�ܣ�");
		}else{
				alert("�����������");
				document.forms[0].money.value="";
		}
	</SCRIPT>
<%			
	}

%>