<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri=  "/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri=  "/WEB-INF/tlds/common.tld" prefix="comtld" %>
<%@ page import="java.util.ArrayList"%> 


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>userList</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/styles2.css" rel="stylesheet" type="text/css" />
<SCRIPT language="JScript.Encode" src="js/commonSelect.js"></SCRIPT>
<SCRIPT language="JScript.Encode" src="js/ascii.js"></SCRIPT>
<script language=javascript src="js/common.js"></script>
</head>
<%
		ArrayList vtrData = (ArrayList)request.getAttribute("vtrData");
    int count=vtrData==null?0:Integer.parseInt((String)vtrData.get(0));
    


%>
<body onunload="close_child_win();">
 <html:form action="userAction.do?method=userList" method="post" >
 <html:hidden property="id"/>

<table align=center width="99%">
				
                <tr class="tableback2"> 
                	<td width="98">��½ID��</td>
                  <td width="121"><html:text property="employeeCode"  styleClass="form" size="16" maxlength="20" /></td>
                  <td width="98">�û�����</td>
                  <td width="126"><html:text property="userName"  styleClass="form" size="16" maxlength="20" /></td>
                   
                  <td width="98">Ȩ�ޣ�</td>
                  <td ><html:text property="userRoleCode"  styleClass="form" size="16"  /></td>
                   
                </tr>
                
                
                </table>
	<h3 class="underline"><p align="left">&nbsp;&nbsp;<input type="button" name="query" value="��ѯ"  onclick="f_query()" ></h3>
	 <table width="99%" border="0" cellpadding="0" cellspacing="1" class="content12">
                <tr bgcolor="#CCCCCC"> 
                        <td width="10"><input name="allbox" type="checkbox" onClick="checkAll(this);"></td>
                        <td><strong>��½ID</strong></td>
                        <td><strong>�û���</strong></td>
                        <td><strong>Ȩ��</strong></td>
                        <td><strong>����</strong></td>
                        <td><strong>�绰</strong></td>
                        <td><strong>��ע</strong></td>
                      </tr>            
      <%if(vtrData!=null){
       	    String strTr="";
      	    for(int i=1;i<vtrData.size();i++){
      	        if(i%2==0) strTr="tableback2";
      	        else strTr="tableback1";
      		String[] temp=(String[])vtrData.get(i);
      		
      %>
      
      <tr class="<%=strTr%>"> 
          <td align=center><input type="checkbox" name="chk" value="<%=temp[0]%>"></td>
          <td style="cursor: hand"><A href="javascript:view('<%=temp[0]%>')"><%=temp[1]%></A></td>
          <td ><%=temp[2]==null?"":temp[2]%></td>
          <td ><%=temp[3]==null?"":temp[3]%></td>
          <td ><%=temp[4]==null?"":temp[4]%></td>
          <td ><%=temp[5]==null?"":temp[5]%></td>
          <td ><%=temp[6]==null?"":temp[6]%></td>
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
                     <input type="button"  value='����' onclick="f_new()">
                     <input type="button"  value='ɾ��' onclick="f_del()">
                    </td>
                  </tr>

	
	  </html:form>
</body>
</html>
<script language="JavaScript">

function f_new(){
	child=middleOpen("userAction.do?method=addInit","","width=750,height=300,left=50,top=10,menubar=no,toolbar=no,resizable=yes,scrolling=yes,scrollbars=no");
}
	
	
function f_del(){
	if(chk()!=''&&chk()!=null){
    if(window.confirm("ȷ��ɾ���ü�¼��")){
    	document.forms[0].action="userAction.do?method=userDelete";
    	document.forms[0].id.value=chk();
			document.forms[0].submit();
    }
	}else{
		alert("����ѡ���û�");
	}
}

function view(id){
    child= middleOpen("userAction.do?method=userDetail&id="+id,"","width=750,height=300,left=50,top=10,menubar=no,toolbar=no,resizable=yes,scrolling=yes,scrollbars=no");
   
}

function f_query(){
    if(chk_page_num(100)){
			document.forms[0].query.disabled = true;
			document.forms[0].action="userAction.do?method=userList";
			document.forms[0].submit();
    }
}

</script>
