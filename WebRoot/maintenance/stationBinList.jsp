<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri=  "/WEB-INF/tlds/common.tld" prefix="comtld" %>
<%@ page import="java.util.ArrayList"%> 
<%@ page import="com.dne.sie.common.tools.DicInit"%>

<html>
<head>
<title>stationBin</title>

<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/styles2.css" rel="stylesheet" type="text/css" />
<script language=javascript src="js/inputValidation.js"></script>
<script language=javascript src="js/commonSelect.js"></script>

</head>
<%
	try{
		ArrayList stationBinList = (ArrayList)request.getAttribute("stationBinList");
		int count=0;
		if(stationBinList!=null){
			count=Integer.parseInt((String)stationBinList.get(0));
		}

%>
<body>
<html:form action="stationBinAction.do?method=stationBinList" method="post" >
<input name="ids" type="hidden">

<table align=center width="99%">
				
                
                <tr class="tableback1"> 
                    <td width="98">仓位：</td>
   					<td width="126"><html:text  styleClass="form" property="binCode"  maxlength="32"  size="40"  /></td>
                </tr>
                
                
                </table>
	<h3 class="underline"><p align="left">&nbsp;&nbsp;<input name="query" type="submit" value="查 询" ></p></h3>
	 <table width="99%" border="0" cellpadding="0" cellspacing="1" class="content12">
                      <tr bgcolor="#CCCCCC"> 
          				<td align=center width=10><input name="allbox" type="checkbox" onClick="checkAll(this);"></td>                                            
                        <td width="12%"><strong>&nbsp;仓位</strong></td>
                        <td width="18%"><strong>类型</strong></td>
                        <td><strong>创建日期</strong></td>
                        
                      </tr>
         
        <%
       if(stationBinList!=null){
       	    String strTr="";
      	    for(int i=1;i<stationBinList.size();i++){
      	        if(i%2==0) strTr="tableback2";
      	        else strTr="tableback1";
      		String[] temp=(String[])stationBinList.get(i);
      %>      
      <tr class="<%=strTr%>"> 
		  <td align=center><input type="checkbox" name="chk" value="<%=temp[0]%>"></td>            
      	  <td ><%=temp[0]==null?"":temp[0]%></td>
          <td ><%=temp[1]==null?"":temp[1]%></td>
          <td ><%=temp[2]==null?"":temp[2]%></td>
          
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
                    <td class="content_yellow">
                     <input type="button"  value='新增' onclick="f_add()">
                     <!-- <input type="button"  value='修改' onclick="f_modify()"> -->
                     <input type="button"  value='删除' onclick="f_delete()">
                    </td>
                  </tr>
  

	</html:form>
	  
</body>
</html>

<SCRIPT >
function f_add(){
	document.forms[0].action="stationBinAction.do?method=stationBinInit"; 
	document.forms[0].submit();
}
function f_modify(){
	var chkVal=chk();
	if(chkVal==null||chkVal==''){
		alert('请先选择记录!');
	}else if(chkOnly()){
		document.forms[0].action="stationBinAction.do?method=stationBinEdit";
		document.forms[0].ids.value=chkVal; 
		document.forms[0].submit();
	}
}


function f_delete(){
	var chkVal=chk();
	if(chkVal==null||chkVal==''){
		alert('请先选择记录!');
	}else {
		document.forms[0].action="stationBinAction.do?method=deletePartInfo";
		document.forms[0].ids.value=chkVal; 
		document.forms[0].submit();
	}
	
}

</SCRIPT>

<%
}catch(Exception e){
	e.printStackTrace();
}%>