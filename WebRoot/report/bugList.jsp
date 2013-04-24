<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri=  "/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri=  "/WEB-INF/tlds/common.tld" prefix="comtld" %>
<%@ page import="java.util.ArrayList"%> 
<%@ page import="java.util.Date"%>
<%@ page import="java.util.List"%> 
<%@ page import="com.dne.sie.common.tools.DicInit"%>
<%@ page import="com.dne.sie.common.tools.Operate"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>userList</title>
<link href="<%= request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<link href="<%= request.getContextPath()%>/css/styles2.css" rel="stylesheet" type="text/css" />
<script language=javascript src="<%= request.getContextPath()%>/js/common.js"></script>
</head>

<body >
<%

	ArrayList vtrData = (ArrayList)request.getAttribute("bugInfoList");
    int count=vtrData==null?0:Integer.parseInt((String)vtrData.get(0));
    
	ArrayList statusList = (ArrayList)DicInit.SYS_CODE_MAP.get("BUG_STATUS");
	ArrayList typeList = (ArrayList)DicInit.SYS_CODE_MAP.get("BUG_TYPE");
	
	List monthList=Operate.getMonthList(Operate.toDate("2013-03-19"));

 %>
<html:form action="bugInfoAction.do?method=bugInfoList" method="post" >
<table align=center width="99%">
<input type="hidden" name="ids">				
                <tr class="tableback1"> 
                  <td width="10%">���⣺</td>
                  <td width="25%"><html:text property="subject"  styleClass="form" size="16" /></td>
                  <td width="10%">״̬��</td>
                  <td width="25%"><html:select property="status"  styleClass="form">	
                    <html:option value="init">ȫ��</html:option>
					<%for(int i=0;statusList!=null&&i<statusList.size();i++){
	                  String[] temp=(String[])statusList.get(i);
	                 %>
	                  <html:option value="<%=temp[0]%>"><%=temp[1]%></html:option>
	                <%}%>
      			   </html:select></td>
                  <td width="10%">���ͣ�</td>
                  <td ><html:select property="status"  styleClass="form">	
                  	<html:option value="">ȫ��</html:option>
					<%for(int i=0;typeList!=null&&i<typeList.size();i++){
	                  String[] temp=(String[])typeList.get(i);
	                 %>
	                  <html:option value="<%=temp[0]%>"><%=temp[1]%></html:option>
	                <%}%>
      			   </html:select></td>
                 
                   
                </tr>
                <tr class="tableback2"> 
                 <td width="10%">�����ˣ�</td>
                  <td  ><html:text property="createUserName"  styleClass="form" size="16" /></td>
                  <td >������</td>
                  <td ><html:text property="description"  styleClass="form" size="30" /></td>
                  <td width="98"> �����·ݣ�</td>
                  <td width="126"><html:select styleClass="form" property="createMonth"  >
	     		           <html:option value="">ȫ��</html:option>
	     		           <%for(int i=0;monthList!=null&&i<monthList.size();i++){
	                  	String temp=(String)monthList.get(i);
	                	%>
	                		<html:option value="<%=temp%>"><%=temp%></html:option>
	                 	<%}%>
	     		           </html:select></td>
                   
                </tr>
                
                </table>
	<h3 class="underline"><p align="left">&nbsp;&nbsp;<input name="query" type="button" value="�� ѯ" onclick="f_query()"></p></h3>
	 <table width="99%" border="0" cellpadding="0" cellspacing="1" class="content12">
                <tr bgcolor="#CCCCCC"> 
                        <td width="10">#</td>
                        <td><strong>����</strong></td>
                        <td><strong>������</strong></td>
                        <td><strong>״̬</strong></td>
                        <td><strong>����</strong></td>
                        <td width="60%"><strong>����</strong></td>
                        <td><strong>��������</strong></td>
                        <td><strong>��������</strong></td>
                      </tr>            

      
      <%if(vtrData!=null){
       	    String strTr="";
      	    for(int i=1;i<vtrData.size();i++){
      	        if(i%2==0) strTr="tableback2";
      	        else strTr="tableback1";
      		String[] temp=(String[])vtrData.get(i);
      		
      %>
      
      <tr class="<%=strTr%>"> 
          <td style="cursor: hand"><A href="javascript:view('<%=temp[0]%>')">#<%=temp[0]%></A></td>
          <td style="cursor: hand"><A href="javascript:view('<%=temp[0]%>')"><%=temp[1]%></A></td>
          <td ><%=temp[2]==null?"":temp[2]%></td>
          <td ><%=temp[3]==null?"":temp[3]%></td>
          <td ><%=temp[4]==null?"":temp[4]%></td>
          <td ><%=temp[5]==null?"":temp[5]%></td>
          <td ><%=temp[6]==null?"":temp[6]%></td>
          <td ><%=temp[7]==null?"":temp[7]%></td>
        </tr>
      
      <%}}%> 
      
      
        <tr> 
      <td height="1" bgcolor="#677789" colspan="11"></td>
    </tr>
     <comtld:pageControl numOfRcds="<%=count%>">
	</comtld:pageControl>
    </table>
    
              <tr align="left">
                    <td class="content_yellow">
                     <input type="button"  value='����' onclick="f_add()">
                    </td>
                  </tr>

	</html:form>
</body>
</html>
<script language="JavaScript">

function f_add(){
	document.forms[0].action="bugInfoAction.do?method=bugInfoEdit"; 
	document.forms[0].submit();
}
function view(chkVal){
	
		document.forms[0].action="bugInfoAction.do?method=bugInfoEdit";
		document.forms[0].ids.value=chkVal; 
		document.forms[0].submit();
	
}
function f_query(){
    if(chk_page_num(100)){
			document.forms[0].query.disabled = true;
			document.forms[0].action="bugInfoAction.do?method=bugInfoList";
			document.forms[0].submit();
    }
}

</script>
