<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri=  "/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ page import="java.util.ArrayList"%> 
<%@ page import="java.util.List"%> 
<%@ page import="com.dne.sie.common.tools.CommonSearch"%> 
<%@ page import="com.dne.sie.common.tools.DicInit"%> 

<HTML>
<HEAD>
<title>手工订购</title>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/autocomplete.css" />
<script type="text/javascript" src="js/prototype.js"></script> 
<script type="text/javascript" src="js/autocomplete.js"></script>
<script language=javascript src="js/common.js"></script>
<script language=javascript src="js/commonSelect.js"></script>
<script language=javascript src="js/inputValidation.js"></script>
<script language=javascript src="js/checkValid.js"></script>
<script language=javascript src="js/popPartInfo.js"></script>
</HEAD>
<%
		ArrayList poList = (ArrayList)request.getAttribute("poList");
		int count=0;
		if(poList!=null){
			count=Integer.parseInt((String)poList.get(0));
		}
		
		String orderNo = request.getAttribute("orderNo")==null?"":(String)request.getAttribute("orderNo");
		
		List factoryList=CommonSearch.getInstance().getFactoryList();
		ArrayList transportModeList = (ArrayList)DicInit.SYS_CODE_MAP.get("TRANSPORT_MODE");
		
%>
<body >

<html:form action="partPoAction.do?method=manualPlanList" method="post" >
<html:hidden property="poNo"/>
<input type="hidden" name="ids" >

<table align=center width="99%" class="content12">
				
        <tr class="tableback1"> 
          <td width="80">客户名称：<font color='red'>*</font></td>
          <td  id="cust"> <input type="hidden" name=customerId> 
               <input name="customerName" type="text" class="form" size="30" >
          </td>
          
          <td width="80"> 机型：<font color='red'>*</font></td>
          <td width="170"><input name="modelCode" type="text" class="form" size="22" maxlength="32"> </td>
          <td width="80">机身编码：<font color='red'>*</font></td>
          <td width="121"><input name="modelSerialNo" type="text" class="form" size="16" maxlength="32"> </td>
          
        </tr>
        <tr class="tableback2"> 
        	<td width="80">订购厂商：<font color='red'>*</font></td>
          <td width="120" id="fac"><html:select styleClass="form" property="factoryId"  
          	onchange="javascript:setFactoryName(this)" value="TWKM">
              	 
              	 <%for(int i=0;factoryList!=null&&i<factoryList.size();i++){
                  Object[] temp=(Object[])factoryList.get(i);
                  
                 %>
                  <html:option value="<%=(String)temp[0]%>"><%=(String)temp[1]%></html:option>
                <%}%>
          	</html:select><input name="factoryName" type="hidden">
          </td>
          <td width="80">料号：<font color='red'>*</font></td>
          <td width="140"><input name="stuffNo" type="text" class="form" size="16" maxlength="32" ></td>
          <td width="80">零件名称：<font color='red'>*</font></td>
          <td width="180"><input name="skuCode" type="text" class="form" size="22"  >&nbsp;
                        	<a href="javascript:showPartInfo('skuCode')"><img src="googleImg/icon/search.gif" align="absmiddle" border="0"></a></td>
          <input name="shortCode" type="hidden"><input name="standard" type="hidden">
          
        </tr>
        <tr class="tableback1"> 
          <td width="60">数量：<font color='red'>*</font></td>
          <td width="172"><input name="orderNum" type="text" class="form" size="16" maxlength="4" onkeydown="f_onlynumber()"></td>
          <td width="80"> 单价$：<font color='red'>*</font></td>
          <td width="170"><input name="perQuote" type="text" class="form" size="22" maxlength="32"> </td>
          <td width="60">零件单位：</td>
          <td width="172"><input name="skuUnit" type="text" class="form" size="16" maxlength="4" ></td>
        </tr>
       	<tr class="tableback2"> 
            <td valign="top">备注：</td>
            <td colspan="5" valign="top"> 
            	<textarea name="remark" cols="8" rows="2" class="form" style="width:93%"></textarea>
            </td>
        </tr>
</table>
<h3 class="underline"><p align="left">&nbsp;&nbsp;<input name="add" type="button" value="新 增" onclick="f_add()"></p></h3>

  <table width="100%" border="0" cellpadding="0" cellspacing="1" class="content12">
      <tr bgcolor="#CCCCCC"> 
        <td width="10"><input name="allbox" type="checkbox" onClick="checkAll(this);"></td>
  			<td><strong>客户名称</strong></td>
  			<td><strong>订购厂商</strong></td>
  			<td><strong>料号</strong></td>
  			<td><strong>零件名称</strong></td>
  			<td><strong>单价(美金)</strong></td>
        <td><strong>数量</strong></td>
        <td><strong>经办人</strong></td>
        <td><strong>订购日期</strong></td>
        <td><strong>运输方式</strong></td>
      </tr>

	<%
       if(poList!=null){
       	    String strTr="";
      	    for(int i=1;i<poList.size();i++){
      	        if(i%2==0) strTr="tableback2";
      	        else strTr="tableback1";
      		String[] temp=(String[])poList.get(i);
      %>      
      <tr class="<%=strTr%>"> 
          <td align=center><input type="checkbox" name="chk" value="<%=temp[0]%>"></td>      
          <td ><%=temp[2]==null?"":temp[2]%></td>
          <td ><%=temp[15]==null?"":temp[15]%></td>
          <td ><%=temp[3]==null?"":temp[3]%></td>
          <td >&nbsp;<%=temp[4]==null?"":temp[4]%></td>
          <td >&nbsp;<%=temp[5]==null?"":temp[5]%></td>
          <td ><%=temp[6]==null?"":temp[6]%></td>
          <td ><%=temp[7]==null?"":temp[7]%></td>
          <td ><%=temp[8]==null?"":temp[8]%></td>
          <td ><%=temp[16]==null?"":temp[16]%></td>
        </tr>      
      <%}}else{%> 
	<tr class="tableback1"></tr>
      <%}%>
    <tr> 
      <td height="1" bgcolor="#677789" colspan="11"></td>
    </tr>

    </table>
    			<table width="100%" border="0" cellpadding="0" cellspacing="1" class="content12">
          		
          <tr>
						<td align=center rowspan="2" width="15%">订购单号：　</td>
						<td ><input name="orderNoT" size=20 value="<%=orderNo%>" disabled > </td> 
						
						<td >运输方式：<font color='red'>*</font>
	       			<html:select styleClass="form" property="transportMode"  >
	              	 
	              	 <%for(int i=0;transportModeList!=null&&i<transportModeList.size();i++){
	                  	String[] temp=(String[])transportModeList.get(i);
	                 %>
	                  <html:option value="<%=temp[0]%>"><%=temp[1]%></html:option>
	                <%}%>
	          	</html:select>
	          </td>
	          
					</tr>
					
             </table>
     <br>
		<tr align="left">
      <td class="content_yellow">
			<input name="poConfirm" type="button"  value="订单确认" onclick="f_po_confirm()">
			<input name="poConfirm" type="button"  value="删 除" onclick="f_delete()">
      </td>
    </tr>
			  
</html:form>
<script language="JavaScript">
new AutoTip.AutoComplete("stuffNo", function() {
	 return "partInfoAction.do?method=getPartInfo&inputValue=" + escape(this.text.value);
});
new AutoTip.AutoComplete("customerName", function() {
	 return "customerInfoAction.do?method=getCustInfo&inputValue=" + escape(this.text.value);
});		

	function f_po_confirm(){
	    if(chk()!=''&&chk()!=null){
	    		document.forms[0].ids.value=chk();
	    		document.forms[0].poConfirm.disabled=true;
	    		document.forms[0].action="partPoAction.do?method=sendPo&flag=manu";  
					document.forms[0].submit();  		
				
	    }else{
	  		alert("请先选择零件！");
	    }
	}
	
	function f_add(){
			if(f_isNull(document.forms[0].customerId,'客户名称')&&f_isNull(document.forms[0].modelCode,'机型')
				&&f_isNull(document.forms[0].modelSerialNo,'机身编码')&&f_isNull(document.forms[0].stuffNo,'料号')
				&&f_isNull(document.forms[0].skuCode,'零件名称')&&f_isNull(document.forms[0].perQuote,'单价')
				&&f_isNull(document.forms[0].orderNum,'数量')&&f_maxLength(document.forms[0].remark,'备注',255)){
			
				document.forms[0].add.disabled=true;
	    	document.forms[0].action="partPoAction.do?method=manualPlanAdd";
				document.forms[0].submit();
			}
	
	}
	
	

	
	function setFactoryName(obj){
			
			for (var i=0; i<obj.options.length; i++){
				if(obj.options[i].selected){
						document.forms[0].factoryName.value=obj.options[i].text;
						break;
				}
			}
	}
	
	function f_delete(){
			if(chk()!=''&&chk()!=null){
	    		document.forms[0].ids.value=chk();
	    		document.forms[0].poConfirm.disabled=true;
	    		document.forms[0].action="partPoAction.do?method=manuPoDel";  
					document.forms[0].submit();  		
				
	    }else{
	  		alert("请先选择零件！");
	    }
	
	}
	

	
</script>

</body>
</HTML>

