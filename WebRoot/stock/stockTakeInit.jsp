<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri=  "/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.dne.sie.common.tools.Operate"%> 

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>stockTake</title>

<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/styles.css" rel="stylesheet" type="text/css" />

</head>
<%   
try{

    String userName = (String)session.getAttribute("userName");
    ArrayList binCodeList=(ArrayList)request.getAttribute("binCode");
%>
<body>
 <html:form action="stockTakeAction.do?method=takeStart" method="post" >

<br>
<table align=center width="99%">
				
              
                <tr class="tableback1"> 
                  <td width="98"> �̵��ˣ�</td>
                  <td width="126"><%=userName%></td>
                   
                  <td width="98">�̵����ڣ�</td>
                  <td width="121"><%=Operate.getDate()%></td>
                  
                </tr>
                
              
                <tr class="tableback2"> 
                  <td width="98">��ͳɱ���</td>
                  <td width="126"><input name="realCost1" type="text" class="form" size="16"></td>
                  <td width="98">��߳ɱ���</td>
                  <td width="121"><input name="realCost2" type="text" class="form" size="16"></td>
                  
                </tr>
                <tr class="tableback1"> 
                  <td width="98">�Ϻţ�</td>
                  <td width="121"><input name="stuffNo" type="text" class="form" size="16"></td>
                  <td width="98">������ƣ�</td>
                  <td width="121"><input name="skuCode" type="text" class="form" size="16"></td>
                </tr>
                <tr class="tableback2"> 
                  <td width="98">��ʼ��λ��</td>
                  <td width="126"><select class="form" style="width:80" name="binCode1">
							<option value="">===ȫ��===</option>
							<%for(int i=0;binCodeList!=null&&i<binCodeList.size();i++){
              						String temp=(String)binCodeList.get(i);
              				%>
                				<option value="<%=temp%>"><%=temp%></option>
                			<%}%>
      		  				</select></td>
                  <td width="98">������λ��</td>
                  <td width="121"><select class="form" style="width:80" name="binCode2">
							<option value="">===ȫ��===</option>
							<%for(int i=0;binCodeList!=null&&i<binCodeList.size();i++){
              						String temp=(String)binCodeList.get(i);
              				%>
                				<option value="<%=temp%>"><%=temp%></option>
                			<%}%>
      		  				</select></td>
                  
                </tr>
                </table>
	<h3 class="underline"></p></h3>
	 <p align="left">&nbsp;&nbsp;<input type="button" value="�̵��ʼEXCEL" onclick="f_excel()">
	&nbsp;&nbsp;<input name="take" type="button" value="��ʼ�̵�"  onclick="f_submit()">

	
	  </html:form>
</body>
<%
}catch(Exception e){
	e.printStackTrace();
}
%>
</html>

<SCRIPT language=JAVASCRIPT1.2>

function f_submit(){
	document.forms[0].action="stockTakeAction.do?method=takeStart";
	document.forms[0].take.disabled=true;
	document.forms[0].submit();
}

function f_excel(){
	document.forms[0].action="stockTakeAction.do?method=takeExcelCreate";
	document.forms[0].submit();
}
</SCRIPT>