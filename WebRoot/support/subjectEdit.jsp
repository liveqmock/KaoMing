<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri=  "/WEB-INF/tlds/struts-html.tld" prefix="html" %>

<html>
<head>
<title>subjectEdit</title>
<link rel="stylesheet" href="css/style.css">
<script language=javascript src="js/checkValid.js"></script>
<script language=javascript src="js/ajax.js"></script>
</head>
<%
	String subId=(String)request.getAttribute("subId");	
	String parentId=(String)request.getAttribute("parentId");
	String parentName=(String)request.getAttribute("treeName");
	String title=(String)request.getAttribute("title");
	String layer=(String)request.getAttribute("layer");
	String strFlag=(String)request.getAttribute("flag");
	String reportFlag=(String)request.getAttribute("reportFlag");
	
	if("init".equals(strFlag)) parentName+=" 添加";
	else parentName+=" 信息修改";

%>
 
<body bgcolor="#ffffff" leftmargin="0" topmargin="0" rightmargin="0" bottommargin="0" marginwidth="0" marginheight="0" >

<html:form method="post" action="accountAction.do?method=addInit">
<input type="hidden" name="subjectId" value="<%=subId %>">
<input type="hidden" name="parentId" value="<%=parentId %>">
<input type="hidden" name="title" value="<%=title %>">
<input type="hidden" name="layer" value="<%=layer %>">
<input type="hidden" name="reportFlag" value="0" >
<br>
<table width="90%" border="0" align="center" cellpadding="2" cellspacing="1" class="content12">
  <%if(parentId!=null){ %>
  <tr> 
    <td><%=parentName%> &nbsp;——&nbsp; <%=title%></td>
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
    <td width="25%"> 科目名称 ：<font color="red">*</font></td>
    <td id="t1"> <html:text property="subjectName" styleClass="form" size="32" maxlength="16"/> </td>
  </tr>
  <tr class="tableback2"> 
    <td width="35%"> 报表统计字段 ：</td>
    <td > <input type="checkbox" name="chk" <%if("1".equals(reportFlag)){%> checked <%}%>> </td>
    
  </tr>
  <tr class="tableback1"> 
    <td width="25%"> 备注 ：</td>
    <td colspan="2"> <html:textarea property="remark" rows="2" cols="8" styleClass="form" style="width:80%" > </html:textarea></td>
    
  </tr>
  
  
  <tr> 
    <td height="2" colspan="6" bgcolor="#ffffff"></td>
  </tr>
  <tr> 
    <td height="1" colspan="6" bgcolor="#677789"></td>
  </tr>
  <tr align="left"> 
    <td colspan="6">

    	<input type="button"  value="保存" onclick="f_submit()">
    	
    </td>
  </tr>
  <tr> 
    <td height="10"></td>
  </tr>
 <%}else{%>
	<p>请点击具体科目</p>
 <%}%>
</table>
</html:form>
</body>
<!-- InstanceEnd --></html>

<SCRIPT language=JAVASCRIPT1.2>

function f_submit(){
    if(f_isNull(document.forms[0].subjectName,'科目名称')
    	&&f_maxLength(document.forms[0].remark,'备注',255)){
    	
    	var subId="<%=subId%>";
    	if(subId=='0'||subId=='') subId="null";
    	f_chk(subId);
    }
}



	var ajax = new sack(); // 创建ajax对象
	function f_chk(subId) {
	    
		ajax.setVar("subId",subId); 			//设置需要传到后台的参数
		ajax.setVar("subName",escape(document.forms[0].subjectName.value)); 	
		ajax.setVar("method", "ajaxChk");		//调用Action中的方法
		ajax.requestFile = "accountAction.do";		//调用Action
		ajax.method = "GET";				 //提交类型
		ajax.onCompletion = selectResult;	 	//ajax交互完需要执行的函数
		
		ajax.runAJAX();   
	    
	}
	
	function selectResult(){  
		
		var returnXml = ajax.responseXML;	
		var myisEnable = returnXml.getElementsByTagName("ifUse")[0].firstChild.nodeValue;
		
		if(!(eval(myisEnable))){
			if("<%=strFlag%>" == "init"){
	    		document.forms[0].action="accountAction.do?method=subAdd";
	    	}else if("<%=strFlag%>" == "modify"){
	    		document.forms[0].action="accountAction.do?method=subModify";
	    	}
	    	if(document.forms[0].chk.checked){
	    		document.forms[0].reportFlag.value=1;
	    	}
	    	
			document.forms[0].submit();
		}else{
			document.forms[0].subjectName.focus();
			t1.innerHTML="<font color='red'>名称已存在，请重新输入</font><input name='subjectName' class='form' size=32 maxlength=16 />";
		}
		//alert(strCode);
			
	}



</SCRIPT>