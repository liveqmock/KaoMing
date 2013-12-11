<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri=  "/WEB-INF/tlds/common.tld" prefix="comtld" %>
<%@ page import="java.util.ArrayList"%> 
<%@ page import="com.dne.sie.common.tools.DicInit"%>

<html>
<head>
<title>partInfo</title>

<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/styles2.css" rel="stylesheet" type="text/css" />
<script language=javascript src="js/inputValidation.js"></script>
<script language=javascript src="js/commonSelect.js"></script>

</head>
<%
	try{
		ArrayList partInfoList = (ArrayList)request.getAttribute("partInfoList");
		int count=0;
		if(partInfoList!=null){
			count=Integer.parseInt((String)partInfoList.get(0));
		}

		ArrayList partTypeList=(ArrayList) DicInit.SYS_CODE_MAP.get("PART_TYPE");
%>
<body>
<html:form action="partInfoAction.do?method=partInfoList" method="post" >
<input name="ids" type="hidden">

<table align=center width="99%">
				
                <tr class="tableback2"> 
                  <td width="98">料号：</td>
                  <td width="126"><html:text  styleClass="form" property="stuffNo"  maxlength="32"  size="16"  /></td>
                   
                  <td width="98">零件名称：</td>
                  <td width="121"><html:text  styleClass="form" property="skuCode"  maxlength="32"  size="16"  /></td>
                    
                  <td width="97">类型：</td>
                  <td width="172"><html:select property="partType" styleClass="form">
										<%
			  							for(int i=0;partTypeList!=null&&i<partTypeList.size();i++){
											String[] temp=(String[])partTypeList.get(i);
										%>
			 						<html:option value="<%=temp[0]%>"><%=temp[1]%></html:option>
										<%}%>
 											</html:select></td>
                </tr>
                <tr class="tableback1"> 
                  <td width="98"> 规格：</td>
                  <td width="126"><html:text  styleClass="form" property="standard"  maxlength="32"  size="16"  /></td>
                   
                  <td width="98">进价：</td>
                  <td width="121"><html:text  styleClass="form" property="buyCost1"  maxlength="8"  size="16"  onkeydown="f_onlymoney()"/></td>
                    
                  <td width="97">报价：</td>
                  <td width="172"><html:text  styleClass="form" property="saleCost1"    maxlength="8" size="16" onkeydown="f_onlymoney()"/></td>
                </tr>
                
                <tr class="tableback2"> 
                  <td valign="top">备注：</td>
                  <td colspan="5" valign="top"> 
                  	<html:textarea property="remark" rows="2" cols="8" styleClass="form" style="width:93%" ></html:textarea>
                  </td>
                </tr>  
                </table>
	<h3 class="underline"><p align="left">&nbsp;&nbsp;<input name="query" type="submit" value="查 询" ></p></h3>
	 <table width="99%" border="0" cellpadding="0" cellspacing="1" class="content12">
                      <tr bgcolor="#CCCCCC"> 
          				<td align=center width=10><input name="allbox" type="checkbox" onClick="checkAll(this);"></td>                                            
                        <td width="12%"><strong>&nbsp;料号</strong></td>
                        <td width="18%"><strong>零件名称</strong></td>
                        <td><strong>类型</strong></td>
                        <td><strong>规格</strong></td>
                        <td><strong>单位</strong></td>
                        <td><strong>进价</strong></td>
                        <td><strong>报价</strong></td>
                        <td><strong>备注</strong></td>
                      </tr>
         
        <%
       if(partInfoList!=null){
       	    String strTr="";
      	    for(int i=1;i<partInfoList.size();i++){
      	        if(i%2==0) strTr="tableback2";
      	        else strTr="tableback1";
      		String[] temp=(String[])partInfoList.get(i);
      %>      
      <tr class="<%=strTr%>"> 
		  <td align=center><input type="checkbox" name="chk" value="<%=temp[0]%>"></td>            
      	  <td ><%=temp[0]==null?"":temp[0]%></td>
          <td ><%=temp[1]==null?"":temp[1]%></td>
          <td ><%=temp[2]==null?"":temp[2]%></td>
          <td ><%=temp[3]==null?"":temp[3]%></td>
          <td ><%=temp[4]==null?"":temp[4]%></td>
		  <td ><%=temp[5]==null?"":temp[5]%></td>
		  <td ><%=temp[6]==null?"":temp[6]%></td>
		  <td ><%=temp[7]==null?"":temp[7]%></td>
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
                     <input type="button"  value='修改' onclick="f_modify()">
                     <input type="button"  title="上次更新:2008-2-11" value='更新使用率'  onclick="updateLv()">
                    </td>
                  </tr>
  

	</html:form>
	  
</body>
</html>

<SCRIPT >
function f_add(){
	document.forms[0].action="partInfoAction.do?method=addPartInfo"; 
	document.forms[0].submit();
}
function f_modify(){
	var chkVal=chk();
	if(chkVal==null||chkVal==''){
		alert('请先选择记录!');
	}else if(chkOnly()){
		document.forms[0].action="partInfoAction.do?method=editPartInfo";
		document.forms[0].ids.value=chkVal; 
		document.forms[0].submit();
	}
}

function updateLv(){
	//document.forms[0].updateLv.disabled = true;

}


</SCRIPT>

<%
}catch(Exception e){
	e.printStackTrace();
}%>