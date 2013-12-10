<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri=  "/WEB-INF/tlds/common.tld" prefix="comtld" %>
<%@ page import="java.util.ArrayList"%> 
<%@ page import="com.dne.sie.common.tools.DicInit"%> 

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>stockInList</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/styles2.css" rel="stylesheet" type="text/css" />
<SCRIPT language="javascript" src="js/commonSelect.js"></SCRIPT>
<script language=javascript src="js/popCalendar.js"></script>
<script language=javascript src="js/inputValidation.js"></script>
<script language=javascript src="js/common.js"></script>
</head>
<%
	try{
		ArrayList stockFlowList = (ArrayList)request.getAttribute("stockFlowList");
		int count=0;
		if(stockFlowList!=null){
			count=Integer.parseInt((String)stockFlowList.get(0));
		}
		ArrayList flowItemList = (ArrayList)DicInit.SYS_CODE_MAP.get("FLOW_ITEM");	

%>
<body onload="showQueryDateTR('dateScope',3);">
<html:form action="stockFlowAction.do?method=stockOutFlowList" method="post" >
<input name="flowType" type="hidden" value="O">

<table align=center width="99%">
				
                <tr class="tableback2"> 
                  <td width="98">�Ϻţ�</td>
                  <td width="126"><html:text  styleClass="form" property="stuffNo"  maxlength="16"  size="16"  /></td>
                   
                  <td width="98">������ƣ�</td>
                  <td width="121"><html:text  styleClass="form" property="skuCode"  maxlength="32"  size="16"  readonly="true"/></td>
                    
                  <td width="98">���۵�/ά�޵���</td>
                  <td width="121"><html:text  styleClass="form" property="formNo"  maxlength="32"  size="16"  /></td>
                    
                </tr>
                <tr class="tableback1"> 
                  <td width="98"> ������</td>
                  <td width="126"><html:text  styleClass="form" property="strSkuNum"  maxlength="6"  size="16"  onkeydown="f_onlynumber()" /></td>
                   
                  <td width="97">�۸�</td>
                  <td width="172"><html:text  styleClass="form" property="strPerCost"    maxlength="8" size="16" onkeydown="f_onlymoney()"/></td>
                
                  <td width="97">���ⷽʽ��</td>
                  <td width="172">
                  	<html:select  styleClass="form" property="flowItem"    style="width:100">
                  		<html:option value="">ȫ��</html:option>
                  		<%for(int i=0; flowItemList!=null&&i<flowItemList.size(); i++ ){
                            	String[] temp=(String[])flowItemList.get(i);
                            	if(temp[0].equals("S")||temp[0].equals("H")||temp[0].equals("Z")||temp[0].equals("U")||temp[0].equals("V")){
                        %>
	                        <html:option value="<%=temp[0]%>"><%=temp[1]%></html:option>
                        <%}}%>
                  	</html:select></td>
                  	
                </tr>
                
                <tr class="tableback2"> 
                  <td width="80">����ʱ�䣺</td>
        			<td width="120"><html:select property="dateScope" styleClass="form" style="width:100" onchange="changeDateDisplay(this,'inOutDate1','inOutDate2')">
                            	<html:option value="4">ȫ��</html:option>
                            	<html:option value="0">����</html:option>
                            	<html:option value="3">���ڷ�Χ</html:option>
            					<html:option value="1">����</html:option>
            					<html:option value="2">����</html:option>
                           </html:select>  </td>
                    <td width="80">��ʼ���ڣ� </td>
                    <td width="120"><html:text property="inOutDate1" styleId="inOutDate1" styleClass="form" readonly="true" size="14"/> 
           			<a onclick="event.cancelBubble=true;" href="javascript:showCalendar('imageOne',true,'inOutDate1');">
            			<img src="googleImg/icon/calendar.gif" id="imageOne" width="18" height="18" border="0" align="absmiddle">
           			</a>
        		</td>
                        
                        <td width="80">��ֹ���ڣ�</td>
                        <td width="120"><html:text property="inOutDate2" styleId="inOutDate2" styleClass="form" readonly="true" size="14"/> 
          			<a onclick="event.cancelBubble=true;" href="javascript:showCalendar('imageTwo',true,'inOutDate2');">  
             			<img src="googleImg/icon/calendar.gif" id="imageTwo" width="18" height="18" border="0" align="absmiddle">
           			</a>
        		</td>
                </tr>
                 <tr class="tableback1"> 
                  <td valign="top">��ע��</td>
                  <td colspan="5" valign="top"> 
                  	<html:textarea property="remark" rows="2" cols="8" styleClass="form" style="width:93%" ></html:textarea>
                  </td>
                </tr>  
                </table>
	<h3 class="underline"><p align="left">&nbsp;&nbsp;<input name="query" type="button" value="��ѯ" onclick="f_query()">
		&nbsp;&nbsp;<input type="button" name="excel" value="����Excel" onclick="f_excel()"></p></h3>
	 <table width="99%" border="0" cellpadding="0" cellspacing="1" class="content12">
                      <tr bgcolor="#CCCCCC"> 
          				<td align=center><input name="allbox" type="checkbox" onClick="checkAll(this);"></td>                      
                        <td><strong>&nbsp;�������</strong></td>
                        <td><strong>�Ϻ�</strong></td>
                        <td><strong>����</strong></td>
                        <td><strong>��λ</strong></td>
                        <td><strong>����</strong></td>
                        <td><strong>�ɱ�����(RMB)</strong></td>
                        <td><strong>�ͻ�����</strong></td>
                        <td><strong>���ⷽʽ</strong></td>
                        <td><strong>���۵�/ά�޵�</strong></td>
                        <td><strong>��ע</strong></td>
                        <td><strong>��������</strong></td>
                      </tr>
         
       
        <%
       if(stockFlowList!=null){
       	    String strTr="";
      	    for(int i=1;i<stockFlowList.size();i++){
      	        if(i%2==0) strTr="tableback2";
      	        else strTr="tableback1";
      		String[] temp=(String[])stockFlowList.get(i);
      %>      
      <tr class="<%=strTr%>"> 
		  <td align=center><input type="checkbox" name="chk" value="<%=temp[0]%>"></td>      
          <td ><A href="javascript:view('<%=temp[0]%>')"><%=temp[2]%></A></td>
          <td ><A href="javascript:view('<%=temp[0]%>')"><%=temp[3]%></A></td>
          <td ><%=temp[4]==null?"":temp[4]%></td>
		  <td ><%=temp[5]==null?"":temp[5]%></td>
		  <td ><%=temp[1]==null?"":temp[1]%></td>
		  <td ><%=temp[6]==null?"":temp[6]%></td>
		  <td ><%=temp[7]==null?"":temp[7]%></td>
		  <td ><%=temp[8]==null?"":temp[8]%></td>
		  <td ><%=temp[11]==null?"":temp[11]%></td>
		  <td ><%=temp[9]==null?"":temp[9]%></td>
		  <td ><%=temp[10]==null?"":temp[10]%></td>
        </tr>      
      <%}}else{%> 
	<tr class="tableback1"></tr>
      <%}%>
      
        <tr> 
      <td height="1" bgcolor="#677789" colspan="13"></td>
    </tr>
    <comtld:pageControl numOfRcds="<%=count%>">
	</comtld:pageControl>
    </table>
    
              <tr align="left">
                    <td class="content_yellow">
                     <input type="button"  value='��ӡ���ⵥ' onclick="f_print()">
                    </td>
                  </tr>

	
	  
</body>
</html:form>
</html>


<SCRIPT language=JAVASCRIPT1.2>
function f_query(){
	document.forms[0].query.disabled = true;
	document.forms[0].action="stockFlowAction.do?method=stockOutFlowList"; 
	document.forms[0].submit();
}

function f_print(){
	var ids=chk();
	if(ids!=''&&ids!=null){
		window.open("stockFlowAction.do?method=stockInOutPrint&flowType=O&ids="+ids,"","scrollbars=yes,width=700,height=600");
	}else{
		alert('����ѡ���¼!');
	}
}

function f_excel(){
    document.forms[0].excel.disabled = true;
	document.forms[0].action="stockFlowAction.do?method=stockFlowFile";
	document.forms[0].submit();
	setTimeout("doRefresh()",3000);
}

function doRefresh(){
	document.forms[0].excel.disabled = false;
}

function view(id){
    window.open("stockFlowAction.do?method=flowDetail&id="+id,"","width=800,height=300,left=50,top=10,menubar=no,toolbar=no,resizable=yes,scrolling=yes,scrollbars=yes");
   
}

</SCRIPT>

<%
}catch(Exception e){
	e.printStackTrace();
}%>