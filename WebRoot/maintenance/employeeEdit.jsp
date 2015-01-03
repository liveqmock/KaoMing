<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ page import="com.dne.sie.common.tools.DicInit"%>
<%@ page import="java.util.ArrayList"%> 

<html>
<head>
<title>employeeInfo</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/styles.css" rel="stylesheet" type="text/css" />
<script language=javascript src="js/inputValidation.js"></script>
<script language=javascript src="js/checkValid.js"></script>
<script language=javascript src="js/popCalendar.js"></script>
<script language=javascript src="js/ajax.js"></script>
</head>
<%
	String idRepeat=(String)request.getAttribute("idRepeat")==null?"":(String)request.getAttribute("idRepeat");
	ArrayList sexList = (ArrayList)DicInit.SYS_CODE_MAP.get("SEX");
	ArrayList positionList = (ArrayList)DicInit.SYS_CODE_MAP.get("POSITION");
	String flag=(String)request.getAttribute("flag")==null?"":(String)request.getAttribute("flag");
	
	
%>
<body>
<html:form action="employeeInfoAction.do?method=employeeInit" method="post" >
<html:hidden property="employeeCode"/>
<html:hidden property="delFlag"/>
<br>
<table width="99%" border="0" align="center" cellpadding="2" cellspacing="1" class="content12">
  <tr>
    <td>管理&gt;基础信息&gt;员工信息</td>
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
   <tr class="tableback2">
    
    <td >员工姓名：<font color="red">*</font></td>
    <td colspan="4" ><html:text  styleClass="form" property="employeeName"  maxlength="45"  size="20" onkeydown="f_filt()"/> 
    	<A href="javascript:checkId()">[校验]</a>
    	<%if(idRepeat.equals("idRepeat")){%><font color='red'>该姓名已存在</font> <%}%></td>
    <td id="t1">&nbsp;</td>
    
    
  </tr>
 <tr class="tableback1"> 
    <td>生日 ：</td>
    <td><html:text  styleClass="form" property="birthday" styleId="birthday" readonly="true" size="14"  />
    <a onclick="event.cancelBubble=true;" href="javascript:showCalendar('imageOne',true,'birthday');">
		<img src="googleImg/icon/calendar.gif" id="imageOne" width="18" height="18" border="0" align="absmiddle">
	</a>
    </td>
    <td>入职日期：</td>
    <td ><html:text  styleClass="form" property="employedDate" styleId="employedDate" readonly="true" size="14" />
    <a onclick="event.cancelBubble=true;" href="javascript:showCalendar('imageOne',true,'employedDate');">
		<img src="googleImg/icon/calendar.gif" id="imageOne" width="18" height="18" border="0" align="absmiddle">
	</a>
    </td>
  	<td>性别：<font color="red">*</font></td>
    <td ><html:select property="sex"  styleClass="form">	
		<%for(int i=0;i<sexList.size();i++){
                  String[] temp=(String[])sexList.get(i);
                 %>
                  <html:option value="<%=temp[0]%>"><%=temp[1]%></html:option>
                <%}%>
      	</html:select> </td>
  </tr>
  <tr class="tableback2"> 
  <td>QQ：</td>
   <td ><html:text  styleClass="form" property="qq"  maxlength="32"  size="16" onkeydown="f_onlynumber()" /></td>
   <td>MSN：</td>
   <td ><html:text  styleClass="form" property="msn"  maxlength="45"  size="20"  /></td>
   <td>邮箱：</td>
    <td ><html:text  styleClass="form" property="email"  maxlength="45"  size="24"  /></td>
  </tr>
  
  <tr class="tableback1"> 
   <td>电话：</td>
   <td ><html:text  styleClass="form" property="phone"  maxlength="32"  size="16"  /></td>
   <td>身份证号：</td>
   <td ><html:text  styleClass="form" property="identityCard"  maxlength="32"  size="20"  /></td>
   <td>职务：</td>
   <td ><html:select property="position"  styleClass="form">	
		<%for(int i=0;i<positionList.size();i++){
                  String[] temp=(String[])positionList.get(i);
                 %>
                  <html:option value="<%=temp[0]%>"><%=temp[1]%></html:option>
                <%}%>
      	</html:select></td>
   
  </tr>
  <tr class="tableback2"> 
  <td>地址 ：</td>
    <td colspan="5"><html:text  styleClass="form" property="address"  maxlength="256"  size="24"  /></td>
    </tr>
  <tr class="tableback1"> 
    <td valign="top">备注：</td>
    <td colspan="5">
    	<html:textarea property="remark" style="width:90% " styleClass="form"></html:textarea> </td>
  </tr>
  <tr> 
    <td height="2" colspan="6" bgcolor="#ffffff"></td>
  </tr>
  <tr> 
    <td height="1" colspan="6" bgcolor="#677789"></td>
  </tr>
  <tr> 
    <td  colspan="6"><div align="right"> 
        <input type="button" name="save" value="保存"  onclick="f_save()">
      </div></td>
  </tr>
</table>
</body>
</html:form>
</html>

<SCRIPT >
function f_save(){
	
	if(checkIsNull()){
		<%if("init".equals(flag)){%>
		document.forms[0].action="employeeInfoAction.do?method=insertEmployee"; 
		<%}else{%>
		document.forms[0].action="employeeInfoAction.do?method=updateEmployee"; 
		<%}%>
		document.forms[0].submit();
	}
}


function checkIsNull() {
	
	if(f_isNull(document.forms[0].employeeName,'员工姓名')
    	&&verifyEmail(document.forms[0].email)&&f_maxLength(document.forms[0].remark,'备注',256)){
    		var empName=document.forms[0].employeeName.value;
			document.forms[0].employeeName.value=empName.trim();
    		return true;
	}else{
			return false;
	}
	
}


function checkId(){
	var empName=document.forms[0].employeeName.value;
	if(empName!=null&&empName!=''){
		ajaxChk(empName);
	}else{
		alert("请先输入员工姓名");
	}
}


var ajax = new sack(); // 创建ajax对象

function ajaxChk(code){ // 调用ajax
	ajax.setVar("empName",escape(code)); 			//设置需要传到后台的参数
	ajax.setVar("method", "ajaxChkName");		//调用Action中的方法
	ajax.requestFile = "employeeInfoAction.do";		//调用Action
	ajax.method = "GET";				 //提交类型
	ajax.onCompletion = selectResult;	 	//ajax交互完需要执行的函数
	
	ajax.runAJAX();                                    //启动ajax
}


function selectResult(){  //ajax从后台传回数据后的处理函数
	
	var returnXml = ajax.responseXML;			//ajax返回的xml格式字符串，如果只需要传回无格式文本，则是responseText
	
	var myisEnable = returnXml.getElementsByTagName("ifUse")[0].firstChild.nodeValue;
			
	if((eval(myisEnable))){
		t1.innerHTML="<font color='blue'>OK</font>";
	}else{
		t1.innerHTML="<font color='red'>该姓名已存在</font>";
	}
		
		
}

function f_filt(){
	var src = event.srcElement;
	var key = event.keyCode;

	//不能输入空格，tab  (tabspace = 9,space = 32)
	if (key<8||key==9||key==32){
		event.returnValue = false;
	}

}

String.prototype.trim = function()
{
	return this.replace( /(^\s*)|(\s*$)/g, '' ) ;
}
</SCRIPT>