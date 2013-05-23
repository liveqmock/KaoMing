<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri=  "/WEB-INF/tlds/struts-html.tld" prefix="html" %>

<html>
<head>
<title>position</title>
<link rel="stylesheet" href="<%= request.getContextPath()%>/css/style.css">
<SCRIPT  src="<%= request.getContextPath()%>/js/tree/client_ini.js"></SCRIPT>
<SCRIPT  src="<%= request.getContextPath()%>/js/tree/xtree.js"></SCRIPT> 
</head>
<%
	String[] irisTree = (String[])request.getAttribute("irisTree");

%>
<body>
<form>

<table width="90%" border="0" align="center" cellpadding="2" cellspacing="1" class="content12">
  <tr> 
    <td>故障位置查询</td>
  </tr>
</table>
<br>
<table HEIGHT="380" width="100%" cellspacing="0" cellpadding="0" border="0" id="irisTree">

                <tr > 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0" >
                    <tr><td colspan="6">
						    	<input type="button" class="button2" value="确认" onclick="f_position()">
						    </td> </tr>
						    <tr> 
                              <td colspan="2" height="5" bgcolor="#ffffff"></td>
                            </tr>
                      
                      <tr> 
                        <td width="350" height="129" valign="top"> <table width="90%" border="0" cellpadding="0" cellspacing="1" class="content12">
	                      
                            <tr> 
                              <td height="4" bgcolor="#677789"></td>
                            </tr>
                            <tr bgcolor="#CCCCCC"> 
                              <td><strong>电气</strong></td>
                            </tr>
                            <tr class="tableback2" > <td >
		<SCRIPT LANGUAGE="JavaScript">
			treeConfig.openRootIcon='images/xp/rootIcon.png';
			
                            <%
                            out.println(irisTree[0]);
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
                        <td width="350" align="center" valign="top"> <table width="90%" border="0" cellpadding="0" cellspacing="1" class="content12">
                            <tr> 
                              <td height="4" bgcolor="#677789"></td>
                            </tr>
                            <tr bgcolor="#CCCCCC"> 
                              <td><strong>机械</strong></td>
                            </tr>
                            <tr class="tableback2"> <td >
		<SCRIPT LANGUAGE="JavaScript">
			
                            <%
                            out.println(irisTree[1]);
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
                          
                      </tr>
	</table>
		
     </tr>
    </table>
</form>
</body>
<SCRIPT language=JAVASCRIPT1.2>
function f_position(){
	var allValue="";
  	 <%for(int i=0;i<irisTree.length-1;i++){   %>
		var retValue<%=i%>=tree<%=i%>.getCheckedValues(); 
  	 	if(retValue<%=i%>!='') allValue+=","+retValue<%=i%>;
  	 <%}%>
  	 //alert(allValue);
  	 if(allValue!=''){
  	 	allValue=allValue.substring(1);
  	 	returnValue=allValue;
		self.close();
  	 }else{
  	 	alert("请选先选择障位置！");
  	 	return false;
  	 }
	
}
	
</SCRIPT>
</html>