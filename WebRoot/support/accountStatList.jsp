<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri=  "/WEB-INF/tlds/common.tld" prefix="comtld" %>
<%@ page import="java.util.ArrayList"%> 
<%@ page import="com.dne.sie.common.tools.DicInit"%>

<HTML>
<HEAD>
<title>����ͳ��List</title>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/styles2.css" rel="stylesheet" type="text/css" />
<script language=javascript src="js/common.js"></script>
<script language=javascript src="js/inputValidation.js"></script>
</HEAD>
<%
	try{
	
		ArrayList statList = (ArrayList)request.getAttribute("statList");
		int count=0;
		if(statList!=null){
			count=Integer.parseInt((String)statList.get(0));
		}
		String filePath = (String) session.getAttribute("accountStatistics");
		
%>
<body >
<html:form action="accountStatisticsAction.do?method=statList" method="post" >
<input name="accountMonth" type="hidden">

 <table width="100%" border="0" cellpadding="0" cellspacing="1" class="content12">  
   
                <tr class="tableback1"> 
                  <td width="10%" title="��ʽ��201103">�����·ݣ�</td>
                  <td ><html:text property="accountMonth1" styleClass="form" size="8" maxlength="6" onkeydown="f_onlynumber()"/>
                  	 - <html:text property="accountMonth2" styleClass="form" size="8" maxlength="6" onkeydown="f_onlynumber()"/></td>
                  
                </tr>
                

                </table>
	<h3 class="underline"><p align="left">&nbsp;&nbsp;<input name="query" type="button" onclick="f_query()" value="�� ѯ" ></p></h3>
<br>
  <table width="100%" border="0" cellpadding="0" cellspacing="1" class="content12">
      <tr bgcolor="#CCCCCC"> 
       <td width="10">&nbsp;</td>
        <td><strong>�·�</strong></td>
	  		<td><strong>���� </strong></td>
	  		<td><strong>�ָ�</strong></td>
	  		<td><strong>����</strong></td>
	  		<td><strong>����</strong></td>
	  		<td><strong>���ڽ��ࣨ�֣�</strong></td>
        <td><strong>���ڽ��ࣨ����</strong></td>
        <td><strong>��δ��� </strong></td>
      </tr>

	 <%
       if(statList!=null){
       	    String strTr="";
      	    for(int i=1;i<statList.size();i++){
      	        if(i%2==0) strTr="tableback2";
      	        else strTr="tableback1";
      		String[] temp=(String[])statList.get(i);
      		
      		
      %>      
      <tr class="<%=strTr%>">
      <td align=center><input type="radio" name="chk" value="<%=temp[1]%>" onclick="setVal('<%=temp[1]%>')"></td>            
		  <td style="cursor: hand"><A href="javascript:f_detail('<%=temp[1]%>')"><%=temp[1]%></A></td>
          <td ><%=temp[2]%></td>
          <td ><%=temp[3]%></td>
          <td ><%=temp[4]%></td>
          <td ><%=temp[5]%></td>
          <td ><%=temp[6]%></td>
          <td ><%=temp[7]%></td>
          <td ><%=temp[8]%></td>
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
                    <td> <input type="button"  value='����Excel' onclick="f_excel()"> </td>
                    <td> <input type="button"  value='����ͳ��' onclick="f_recountStat()"> </td>
                    <td> <input type="button"  value='ɾ��' onclick="f_delete()"> </td>
       <%if (filePath != null) {
    		String[] fileInfo = filePath.split("~");
    %>
						<TD colspan="15" id="downLoadFile" style="display: block"><a
							href="<%=fileInfo[1]%>"><font color="blue"> �����ļ��� report<%=fileInfo[0]%> &nbsp;&nbsp;</font></a></TD>
					<%}%>
              </tr>
              
 
			  

	</html:form>

</BODY>


<SCRIPT >

function f_query(){
	document.forms[0].query.disabled=true;
	document.forms[0].action="accountStatisticsAction.do?method=statList"; 
	document.forms[0].submit();
}


	var month='';
	function setVal(no){
			month=no;
	}

function f_recountStat(){
	if(month!=''){
		if(confirm("ȷ��Ҫ����ͳ����")){
			document.forms[0].accountMonth.value=month;
			document.forms[0].action="accountStatisticsAction.do?method=recountStat"; 
			document.forms[0].submit();
		}
	}else{
			var m1=document.forms[0].accountMonth1.value;
			var m2=document.forms[0].accountMonth2.value;
			if(m1!=''&&m1==m2){
				if(confirm("ȷ��Ҫ����ͳ��"+m1+"��")){
						document.forms[0].accountMonth.value=m1;
						document.forms[0].action="accountStatisticsAction.do?method=recountStat"; 
						document.forms[0].submit();
				}
			}else{
				alert("����ѡ������");
			}
	}
}


function f_detail(chkMon){
		
		middleOpen("accountStatisticsAction.do?method=statDetail&accountMonth="+chkMon,"","width=850,height=600,left=50,top=10,menubar=no,toolbar=no,resizable=yes,scrolling=yes,scrollbars=yes");

}

function f_excel(){
	if(month!=''){
			document.forms[0].accountMonth.value=month;
			document.forms[0].action="accountStatisticsAction.do?method=exportStatExcel"; 
			document.forms[0].submit();
	}else{
			alert("����ѡ������");
	}

}

function f_delete(){
	if(month!=''){
		if(confirm("ȷ��Ҫɾ����")){
			document.forms[0].accountMonth.value=month;
			document.forms[0].action="accountStatisticsAction.do?method=deleteAccountStat"; 
			document.forms[0].submit();
		}
	}else{
		alert('����ѡ���¼!');
	}

}
</SCRIPT>

<%
}catch(Exception e){
	e.printStackTrace();
}%>
</HTML>