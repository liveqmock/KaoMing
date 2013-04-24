<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ page import="java.util.ArrayList"%> 

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>orderIn</title>

<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/styles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/autocomplete.css" /> 
<script type="text/javascript" src="js/prototype.js"></script>
<script type="text/javascript" src="js/autocomplete.js"></script>
<script language=javascript src="js/inputValidation.js"></script>
<script language=javascript src="js/commonSelect.js"></script>

</head>
<%
	try{
		String tag = request.getAttribute("tag")==null?"":(String)request.getAttribute("tag");
%>
<script>		
var tag="<%=tag%>";
if(tag=="1") alert("�����ɹ�");
else if(tag=="-1") alert("����ʧ��");
</script>	
<%
		ArrayList stockAdjustInList = (ArrayList)request.getAttribute("stockAdjustInList");
		int count=0;
		if(stockAdjustInList!=null){
			count=Integer.parseInt((String)stockAdjustInList.get(0));
		}


%>
<body>
<html:form action="stockInOperateAction.do?method=stockAdjustInList" method="post" >

<table align=center width="99%">
				
                <tr class="tableback2"> 
                  <td width="98">�Ϻţ�</td>
                  <td width="126"><html:text  styleClass="form" property="stuffNo"  maxlength="16"  size="16"  onkeydown="f_chg()"/></td>
                   
                  <td width="98">������ƣ�</td>
                  <td width="121"><html:text  styleClass="form" property="skuCode"  maxlength="32"  size="16"  readonly="true"/></td>
                    
                  <td width="97">��ƣ�</td>
                  <td width="172"><html:text  styleClass="form" property="shortCode"  maxlength="32"  size="16" readonly="true" /></td>
                </tr>
                <tr class="tableback1"> 
                 <td width="98"> ���</td>
                  <td width="126"><html:text  styleClass="form" property="standard"  maxlength="32"  size="16" readonly="true" /></td>
                  
                  <td width="98">��λ��</td>
                  <td width="121"><html:text  styleClass="form" property="skuUnit"  maxlength="4"  size="16" readonly="true" /></td>
                  <td width="98"> ������</td>
                  <td width="126"><html:text  styleClass="form" property="skuNum"  maxlength="6"  size="16"  onkeydown="f_onlynumber()" /></td>
                   
                </tr>
                <tr class="tableback2"> 
                 <td width="97">�۸�</td>
                  <td width="172"><html:text  styleClass="form" property="perCost"    maxlength="8" size="16" onkeydown="f_onlymoney()"/></td>
                  <td width="97">������ԣ�</td>
                  <td width="172">
                  	<html:select  styleClass="form" property="skuType"    style="width:100">
                  		<html:option value="A">A</html:option>
                  		<html:option value="B">B</html:option>
                  	</html:select></td>
                  	<td></td><td></td>
                </tr>
                  <td></td>
                <tr class="tableback1"> 
                        <td valign="top">��ע��</td>
                        <td colspan="5" valign="top"> 
                        	<html:textarea property="remark" rows="2" cols="8" styleClass="form" style="width:93%" ></html:textarea>
                        </td>
                </table>
	<h3 class="underline"><p align="left">&nbsp;&nbsp;<input name="query" type="button" value="�� ��" onclick="f_add()"></p></h3>
	 <table width="99%" border="0" cellpadding="0" cellspacing="1" class="content12">
	 <tr><font color="blue">��¼������ <%=count%></font>
                      <tr bgcolor="#CCCCCC"> 
          				<td align=center><input name="allbox" type="checkbox" onClick="checkAll(this);"></td>                      
                        <td><strong>&nbsp;�������</strong></td>
                        <td><strong>���</strong></td>
                        <td><strong>�Ϻ�</strong></td>
                        <td><strong>����</strong></td>
                        <td><strong>��λ</strong></td>
                        <td><strong>����</strong></td>
                        <td><strong>��ⷽʽ</strong></td>
                        <td><strong>��ע</strong></td>
                        <td><strong>�������</strong></td>
                      </tr>
         
       
        <%
       if(stockAdjustInList!=null){
       	    String strTr="";
      	    for(int i=1;i<stockAdjustInList.size();i++){
      	        if(i%2==0) strTr="tableback2";
      	        else strTr="tableback1";
      		String[] temp=(String[])stockAdjustInList.get(i);
      %>      
      <tr class="<%=strTr%>"> 
		  <td align=center><input type="checkbox" name="chk" value="<%=temp[0]%>"></td>      
          <td ><%=temp[1]==null?"":temp[1]%></td>
          <td ><%=temp[3]==null?"":temp[2]%></td>
          <td ><%=temp[3]==null?"":temp[3]%></td>
          <td ><%=temp[4]==null?"":temp[4]%></td>
		  <td ><%=temp[5]==null?"":temp[5]%></td>
		  <td ><%=temp[6]==null?"":temp[6]%></td>
		  <td ><%=temp[8]==null?"":temp[8]%></td>
		  <td ><%=temp[9]==null?"":temp[9]%></td>
		  <td ><%=temp[10]==null?"":temp[10]%></td>
        </tr>      
      <%}}else{%> 
	<tr class="tableback1"></tr>
      <%}%>
      
          <tr> 
      <td height="1" bgcolor="#677789" colspan="11"></td>
    </tr>
        
        </table>
<tr align="left">
           		<td class="content_yellow">
                   <input type="button"  value='��ӡ��ⵥ' onclick="f_print()">
                    </td>
                  </tr>
  

	</html:form>
	  
</body>
</html>

<SCRIPT >
function f_add(){
  if(checkIsNull()){
	document.forms[0].query.disabled = true;
	document.forms[0].action="stockInOperateAction.do?method=stockAdjustInConfirm"; 
	document.forms[0].submit();
  }
}


function checkIsNull() {
	var stuffNo = document.all.stuffNo.value;
	var skuNum = document.all.skuNum.value;
	var perCost = document.all.perCost.value;
	var skuCode = document.all.skuCode.value;
	
	if (stuffNo==null||stuffNo.trim() == "") {
		alert("�ϺŲ���Ϊ��!");
		return false;
	}
	if (skuCode==null||skuCode.trim() == "") {
		alert("������Ʋ���Ϊ��!");
		return false;
	}
	if (skuNum==null||skuNum.trim() == "") {
		alert("��������Ϊ��!");
		return false;
	}
	if (perCost==null||perCost.trim() == "") {
		alert("�۸���Ϊ��!");
		return false;
	}

	return true;
}

function f_print(){
		var ids=chk();
	if(ids!=''&&ids!=null){
		window.open("stockFlowAction.do?method=stockInOutPrint&flowType=I&ids="+ids,"","scrollbars=yes,width=700,height=600");
	}else{
		alert('����ѡ���¼!');
	}
}

String.prototype.trim = function()
{
	return this.replace( /(^\s*)|(\s*$)/g, '' ) ;
}


new AutoTip.AutoComplete("stuffNo", function() {
	 return "partInfoAction.do?method=getPartInfo&inputValue=" + escape(this.text.value);
});

function f_chg(){
	document.all.skuCode.value="";
	document.all.shortCode.value="";
	document.all.standard.value="";
	document.all.skuUnit.value="";
}
	
</SCRIPT>

<%
}catch(Exception e){
	e.printStackTrace();
}%>