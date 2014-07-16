<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri=  "/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ page import="java.util.ArrayList"%> 
<%@ page import="com.dne.sie.common.tools.DicInit"%> 

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>收货入库</title>

<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/styles.css" rel="stylesheet" type="text/css" />
<script language=javascript src="js/inputValidation.js"></script>

</head>
<%
		ArrayList orderInList = (ArrayList)request.getAttribute("orderInList");
		String orderNo = request.getAttribute("orderNo")==null?"":(String)request.getAttribute("orderNo");
		ArrayList transportModeList = (ArrayList)DicInit.SYS_CODE_MAP.get("TRANSPORT_MODE");
		String tranModeCode=null;
		ArrayList binCodes = (ArrayList)session.getAttribute("binCodes");
%>
<body>
<html:form action="stockInOperateAction.do?method=orderInList" method="post" >

<table width="100%" border="0" cellpadding="0" cellspacing="1" class="content12">
                      <tr> 
                        <td width="80">订购单号：</td>
                        <td width="200" colspan="2"> <input name="orderNo"  class="form"  size="16" value="<%=orderNo%>">
                          &nbsp;&nbsp;&nbsp;<input name="btnQuery" type="submit"  value="查询" > 
                        </td>
                        <td width="20"></td>
                        <td width="20"></td>
                      </tr>
                    </table>
	<h3 class="underline"></h3>

	<table width="99%" border="0" align="center" cellpadding="2" cellspacing="1" class="content12">
           	<tr bgcolor="#CCCCCC"> 
           	  <td><strong>料号</strong></td>
           	  <td><strong>名称</strong></td>
		  			  <td><strong>机型</strong></td>
		  			  <td><strong>销售单</strong></td>
		  			  <td><strong>客户名称</strong></td>
		  			  <td><strong>交货时间</strong></td>
		  			  <td><strong>发货地点</strong></td>
		  			  <td><strong>订购数量</strong></td>
		  			  <td><strong>实收数量</strong></td>
		  			  <td><strong>单价$</strong></td>
		  			  <td><strong>运费￥</strong></td>
		  			  <td><strong>关税￥</strong></td>
		  			  <td><strong>进价￥</strong></td>
		  			  <td><strong>发票号</strong></td>
		  			  <td><strong>仓位</strong></td>
		  			  <td><strong>订购日期</strong></td>
		  		   </tr>
      
      		<%
       if(orderInList!=null){
       	    String strTr="";
      	    for(int i=0;i<orderInList.size();i++){
      	        if(i%2==0) strTr="tableback2";
      	        else strTr="tableback1";
      		String[] temp=(String[])orderInList.get(i);
      		if(i==0) tranModeCode=temp[11];
      %>      
      <tr class="<%=strTr%>"> 
     	 		<input type="hidden" name="poNo" value="<%=temp[0]%>">
      		<td ><%=temp[1]==null?"":temp[1]%></td>
      		<td ><%=temp[12]==null?"":temp[12]%></td>
          <td ><%=temp[2]==null?"":temp[2]%></td>
          <td ><%=temp[3]==null?"":temp[3]%></td>
          <td ><%=temp[4]==null?"":temp[4]%></td>
          <td ><%=temp[5]==null?"":temp[5]%></td>
          <td ><%=temp[6]==null?"":temp[6]%></td>
          <td ><%=temp[7]==null?"":temp[7]%></td>
          <td ><input name="receiveNum" size="5" onkeydown="f_onlynumber()"></td >
          <td ><%=temp[8]==null?"":temp[8]%><input type="hidden" name="orderDollar" value="<%=temp[8]==null?"":temp[8]%>"></td>
          <td ><input name="freightTW" size="5" value="" onkeydown="javascript:f_onlymoney();" ></td >
          <td ><input name="tariff" size="5" value="" onkeydown="javascript:f_onlymoney();" ></td >
          <td ><input name="perCost" size="5" value="<%=temp[10]==null?"":temp[10]%>"  ><input type="hidden" name="perCostInit" value="<%=temp[10]==null?"":temp[10]%>"></td >
          <td ><input name="invoiceNo" size="15" maxlength="44" ></td>
          <td><select name="binCode" style="width:100" class="form">
		  				<option value="">==请选择==</option>
                        <%for(int j=0; binCodes!=null&&j<binCodes.size(); j++ ){
                            	String bin=(String)binCodes.get(j);
                        %>
	                        <option value="<%=bin%>"><%=bin%></option>
                        <%}%>
	          </select>
	      </td>
          <td ><%=temp[9]==null?"":temp[9]%></td>
        </tr>      
      <%}}else{%> 
	<tr class="tableback1"></tr>
      <%}%>
      
      
      
        <tr> 
      <td height="1" bgcolor="#677789" colspan="16"></td>
    </tr>
        
        </table>
         <tr align="left">
         	<td  >运输方式：<font color='red'>*</font>
	          <select class="form" name="transportMode"  >
	              	 <%for(int i=0;transportModeList!=null&&i<transportModeList.size();i++){
	                  	String[] temp=(String[])transportModeList.get(i);
	                 %>
	                  <option value="<%=temp[0]%>" <%if(temp[0].equals(tranModeCode)){ %> selected <%}%>><%=temp[1]%></option>
	                <%}%>
	          	</select>
	          </td>
	          
           	<td class="content_yellow"> <input type="button"  value='确认收货' onclick="f_receive()"> </td>
         </tr>
</html:form>
</body>
<SCRIPT language=JAVASCRIPT1.2>
	
	function f_receive(){
			if(f_chk()){
					return; 
			}
			document.forms[0].action="stockInOperateAction.do?method=orderInReceive";
			document.forms[0].submit();
	}
	
	function f_chk(){
    	var len = document.forms[0].receiveNum.length;
			if(len>1){
					for(var i=0;i<len;i++){
							if(document.forms[0].receiveNum[i].value==''){
									document.forms[0].receiveNum[i].focus();
									alert("请输入实收数量！");
									return true;
							}
							if(document.forms[0].perCost[i].value==''){
									document.forms[0].perCost[i].focus();
									alert("请输入实际成本！");
									return true;
							}
							if(document.forms[0].invoiceNo[i].value==''){
									document.forms[0].invoiceNo[i].focus();
									alert("请输入发票号！");
									return true;
							}
							if(document.forms[0].binCode[i].value==''){
								document.forms[0].binCode[i].focus();
								alert("请选择仓位！");
								return true;
							}
					}
			} else {
					if(document.forms[0].receiveNum.value==''){
							document.forms[0].receiveNum.focus();
							alert("请输入实收数量！");
							return true;
					}
					if(document.forms[0].perCost.value==''){
							document.forms[0].perCost.focus();
							alert("请输入实际成本！");
							return true;
					}
					if(document.forms[0].invoiceNo.value==''){
							document.forms[0].invoiceNo.focus();
							alert("请输入发票号！");
							return true;
					}
					if(document.forms[0].binCode.value==''){
						document.forms[0].binCode.focus();
						alert("请选择仓位！");
						return true;
					}
			}
			return false;
	
	}
	
	
	/**
	 * RMB进价计算  进价￥ = 单价$ × 汇率（从对应询价单中自动获取）+ 台湾运费（手工输入）
	 * @version 1.0.2011.0305
 	 */
	function f_math(obj){
		if(obj.value==null||obj.value=='') return;
		
		var len = document.forms[0].orderDollar.length;
		if(len>1){
			for(var i=0;i<len;i++){
				tObj=document.forms[0].freightTW[i];
				//同一行
				if(tObj==obj){
					var freightTWVal=new Number(tObj.value);
					var perCostVal=new Number(document.forms[0].perCostInit[i].value);
					
					document.forms[0].perCost[i].value=(perCostVal+freightTWVal).toFixed(2);
					break;
				}	
			}
		}else{
			var freightTWVal=new Number(document.forms[0].freightTW.value);
			var perCostVal=new Number(document.forms[0].perCostInit.value);
			
			document.forms[0].perCost.value=(perCostVal+freightTWVal).toFixed(2);
		}	
	}

</SCRIPT>
</html>
