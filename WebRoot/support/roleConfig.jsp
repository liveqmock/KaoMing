<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri=  "/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ page import="java.util.ArrayList"%> 
 
<html>
<head>
<title>office_list</title><SCRIPT language="JScript.Encode" src="js/screen.js"></SCRIPT>
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/treeStyle.css">
<SCRIPT  src="js/tree/client_ini.js"></SCRIPT>
<SCRIPT  src="js/tree/xtree.js"></SCRIPT> 
</head>
<%
    String[] functionTree = (String[])request.getAttribute("functionTree");
    ArrayList roleList = (ArrayList)request.getAttribute("roleList");

%>

<body bgcolor="#ffffff" leftmargin="0" topmargin="0" rightmargin="0" bottommargin="0" marginwidth="0" marginheight="0" >
 <html:form action="functionAction.do?method=functionTree" method="post" >
 <jsp:include page=  "../common/waitDiv.jsp" flush="true" />
  <html:hidden property="id"/>

        <table width="96%" height="100%" border="0" cellpadding="0" cellspacing="6" class="content12" >
          <tr> 
            <td height="556" valign="top"> 
              <table width="100%" border="0" cellspacing="0" cellpadding="0" class="content12">
                <tr> 
                  <td height="2" bgcolor="#677789"></td>
                </tr>
                <tr> 
                  <td height="6"   bgcolor="#CECECE"></td>
                </tr>
                <tr> 
                  <td >
					
                    <br> <table width="100%" border="0" cellpadding="0" cellspacing="0" class="content12">
                      <tr> 
                        <td width="80">权限列表：</td>
                        <td> <html:select property="selectRole"  onchange="f_query1()">	
                        	<html:option value="">==请选择==</html:option>
			<%for(int i=0;i<roleList.size();i++){
				String[] temp=(String[])roleList.get(i);
				if(!"0".equals(temp[0])){
			%> 
				<html:option value="<%=temp[0]%>"><%=temp[1]%></html:option>
			<%}}%>
      	    		</html:select>  </td>
      	    		
                          <td><input type="button" class="button2" value="保存" onclick="f_submit()"></td>
                      </tr>
                    </table>
					<br>
                    </td>
                </tr>
			
			<!--   前台、库存、物流   -->	
				
                <tr > 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0" >
                      <tr> 
                        <td width="250" height="129" valign="top"> <table width="90%" border="0" cellpadding="0" cellspacing="1" class="content12">
                            <tr> 
                              <td height="4" bgcolor="#677789"></td>
                            </tr>
                            <tr bgcolor="#CCCCCC"> 
                              <td><strong>前台权限</strong></td>
                            </tr>
                            <tr class="tableback2" > <td >
		<SCRIPT LANGUAGE="JavaScript">
			treeConfig.openRootIcon='images/xp/rootIcon.png';
			
                            <%
                            out.println(functionTree[0]);
                            %>
                            
		</SCRIPT>
                            </td></tr>
                            <tr> 
                              <td height="2" bgcolor="#ffffff"></td>
                            </tr>
                            <tr> 
                              <td height="1" bgcolor="#677789"></td>
                            </tr>
                          </table></td>
                          <td width="1" background="images/i_line_2.gif"></td>
                        <td width="250" align="center" valign="top"> <table width="90%" border="0" cellpadding="0" cellspacing="1" class="content12">
                            <tr> 
                              <td height="4" bgcolor="#677789"></td>
                            </tr>
                            <tr bgcolor="#CCCCCC"> 
                              <td><strong>库存权限</strong></td>
                            </tr>
                            <tr class="tableback2"> <td >
		<SCRIPT LANGUAGE="JavaScript">
			
                            <%
                            out.println(functionTree[1]);
                            %>
                            
		</SCRIPT>
                            </td></tr>
                            <tr> 
                              <td height="2" bgcolor="#ffffff"></td>
                            </tr>
                            <tr> 
                              <td height="1" bgcolor="#677789"></td>
                            </tr>
                          </table></td>
                          
                        <td width="1" background="images/i_line_2.gif"></td>
                        <td width="250" align="center" valign="top"> <table width="90%" border="0" cellpadding="0" cellspacing="1" class="content12">
                            <tr> 
                              <td height="4" bgcolor="#677789"></td>
                            </tr>
                            <tr bgcolor="#CCCCCC"> 
                              <td><strong>维修权限</strong></td>
                            </tr>
                            <tr class="tableback2"> <td >
		<SCRIPT LANGUAGE="JavaScript">
			
                            <%
                            out.println(functionTree[5]);
                            %>
                            
		</SCRIPT>
                            </td></tr>
                            <tr> 
                              <td height="2" bgcolor="#ffffff"></td>
                            </tr>
                            <tr> 
                              <td height="1" bgcolor="#677789"></td>
                            </tr>
                          </table></td>
                          
                        <td align="center" valign="top">&nbsp;</td>
                      </tr>
                      
                    <!--  支持   -->
			<br><br>
			<tr > 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0" >
                      <tr> 
                      <td width="250" align="center" valign="top"> <table width="90%" border="0" cellpadding="0" cellspacing="1" class="content12">
                            <tr> 
                              <td height="4" bgcolor="#677789"></td>
                            </tr>
                            <tr bgcolor="#CCCCCC"> 
                              <td><strong>物流权限</strong></td>
                            </tr>
                            <tr class="tableback2"> <td >
		<SCRIPT LANGUAGE="JavaScript">
			
                            <%
                            out.println(functionTree[3]);
                            %>
                            
		</SCRIPT>
                            </td></tr>
                            <tr> 
                              <td height="2" bgcolor="#ffffff"></td>
                            </tr>
                            <tr> 
                              <td height="1" bgcolor="#677789"></td>
                            </tr>
                          </table></td>
                          <td width="1" background="images/i_line_2.gif"></td>
                          
                        <td width="250" height="129" valign="top"> <table width="90%" border="0" cellpadding="0" cellspacing="1" class="content12">
                            <tr> 
                              <td height="4" bgcolor="#677789"></td>
                            </tr>
                            <tr bgcolor="#CCCCCC"> 
                              <td><strong>管理权限</strong></td>
                            </tr>
                            <tr class="tableback2"> <td >
		<SCRIPT LANGUAGE="JavaScript">
			
                            <%
                            out.println(functionTree[2]);
                            %>
                            
		</SCRIPT>
                            </td></tr>
                            <tr> 
                              <td height="2" bgcolor="#ffffff"></td>
                            </tr>
                            <tr> 
                              <td height="1" bgcolor="#677789"></td>
                            </tr>
                          </table></td>
                          
                          
                           <td width="1" background="images/i_line_2.gif"></td>
                        <td width="250" align="center" valign="top"> <table width="90%" border="0" cellpadding="0" cellspacing="1" class="content12">
                            <tr> 
                              <td height="4" bgcolor="#677789"></td>
                            </tr>
                            <tr bgcolor="#CCCCCC"> 
                              <td><strong>报表权限</strong></td>
                            </tr>
                            <tr class="tableback2"> <td >
		<SCRIPT LANGUAGE="JavaScript">
			
                            <%
                            out.println(functionTree[4]);
                            %>
                            
		</SCRIPT>
                            </td></tr>
                            <tr> 
                              <td height="2" bgcolor="#ffffff"></td>
                            </tr>
                            <tr> 
                              <td height="1" bgcolor="#677789"></td>
                            </tr>
                          </table></td>
                          
                          
                        <td align="center" valign="top">&nbsp;</td>
                      </tr>
			<br><br>
          
        </table>
</html:form>
</body>
<!-- InstanceEnd --></html>
 
<SCRIPT language=JAVASCRIPT1.2>


function f_submit(){
    var selectRole=document.forms[0].selectRole.value;

    if(selectRole==''){
    	alert("请先选择权限");
    }else{
   	var allValue="";
   	
   	 <%for(int i=0;i<functionTree.length;i++){   %>
		var retValue<%=i%>=tree<%=i%>.getCheckedValues(); 
   	 	if(retValue<%=i%>!='') allValue+=","+retValue<%=i%>;
   	 <%}%>
   	 if(allValue!='') allValue=allValue.substring(1);
   	 //alert(allValue);
   	 document.forms[0].id.value=allValue;
   	 document.forms[0].action="functionAction.do?method=functionEdit";
	 showWaitDiv(180,1000);
   	 document.forms[0].submit(); 
    }
}

function f_query1(){
	document.forms[0].submit();
}

</SCRIPT>