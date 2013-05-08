<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri=  "/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri=  "/WEB-INF/tlds/common.tld" prefix="comtld" %>
<%@ page import="java.util.ArrayList"%> 
<%@ page import="java.util.List"%> 
<%@ page import="com.dne.sie.reception.bo.SaleInfoBo"%> 

<HTML>
<HEAD>
<title>手工分配</title>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/styles2.css" rel="stylesheet" type="text/css" />
<script language=javascript src="js/common.js"></script>
<script language=javascript src="js/commonSelect.js"></script>
<script language=javascript src="js/popCalendar.js"></script>

</HEAD>
<%
		ArrayList requestList = (ArrayList)request.getAttribute("requestList");
		int count=0;
		if(requestList!=null){
			count=Integer.parseInt((String)requestList.get(0));
		}
		List createByList=SaleInfoBo.getInstance().getPartCreateByList();
	
		
		
%>
<body >

<html:form action="handAllocateAction.do?method=requestList" method="post" >
<html:hidden property="poNo"/>
<input type="hidden" name="ids" >

<table align=center width="99%">
			<tr class="tableback1"> 
        <td width="80">询价单号：</td>
        <td width="140"><html:text property="saleNo"  styleClass="form" size="16" /></td>
        <td width="80">料号：</td>
        <td width="120"><html:text property="stuffNo"  styleClass="form" size="16" /></td>
        <td width="80">零件名称：</td>
        <td width="120"><html:text property="skuCode"  styleClass="form" size="16" /></td>
      </tr>
      <tr class="tableback2"> 
        <td width="80">机型：</td>
        <td width="140"><html:text property="modelCode"  styleClass="form" size="16" /></td>
        <td width="80">机身编码：</td>
        <td width="120"><html:text property="modelSerialNo"  styleClass="form" size="16" /></td>
        <td width="80">经办人：</td>
        <td width="120"><html:select styleClass="form" property="createBy"  >
	     		           <html:option value="">全部</html:option>
	     		           <%for(int i=0;createByList!=null&&i<createByList.size();i++){
	                  	Object[] temp=(Object[])createByList.get(i);
	                	%>
	                		<html:option value="<%=temp[0].toString()%>"><%=temp[1]%></html:option>
	                 	<%}%>
	     		           </html:select></td>
      </tr>
			<tr class="tableback1"> 
      		<td width="80">申请时间：</td>
        	<td width="120"><html:select property="dateScope" styleClass="form" style="width:100" onchange="changeDateDisplay(this,'inDate1','inDate2')">
						        	<html:option value="4">全部</html:option>
						        	<html:option value="0">当日</html:option>
						        	<html:option value="3">日期范围</html:option>
											<html:option value="1">当月</html:option>
											<html:option value="2">本年</html:option>
                      </html:select>  </td>
          <td width="80">起始日期： </td>
          <td width="120"><html:text property="inDate1" styleId="inDate1" styleClass="form" readonly="true" size="14"/> 
		     			<a onclick="event.cancelBubble=true;" href="javascript:showCalendar('imageOne',true,'inDate1');">
		      			<img src="googleImg/icon/calendar.gif" id="imageOne" width="18" height="18" border="0" align="absmiddle">
		     			</a>
        	</td>
	        <td width="80">终止日期：</td>
	        <td width="120"><html:text property="inDate2" styleId="inDate2" styleClass="form" readonly="true" size="14"/> 
        			<a onclick="event.cancelBubble=true;" href="javascript:showCalendar('imageTwo',true,'inDate2');">  
           			<img src="googleImg/icon/calendar.gif" id="imageTwo" width="18" height="18" border="0" align="absmiddle">
         			</a>
        		</td>
       </tr>
</table>
 <h3 class="underline"><p align="left">&nbsp;&nbsp;<input type="submit" value="查 询"></p></h3>
  <table width="100%" border="0" cellpadding="0" cellspacing="1" class="content12">
      <tr bgcolor="#CCCCCC"> 
        <td width="10"><input name="allbox" type="checkbox" onClick="checkAll(this);"></td>
        <td><strong>询价单号</strong></td>
  			<td><strong>料号</strong></td>
  			<td><strong>零件名称</strong></td>
  			<td><strong>单位</strong></td>
  			<td><strong>数量</strong></td>
  			<td><strong>机型</strong></td>
  			<td><strong>机身编码</strong></td>
  			<td><strong>销售单价</strong></td>
        <td><strong>库存数量</strong></td>
        <td><strong>经办人</strong></td>
        <td><strong>订购日期</strong></td>
      </tr>

	<%
       if(requestList!=null){
       	    String strTr="";
      	    for(int i=1;i<requestList.size();i++){
      	        if(i%2==0) strTr="tableback2";
      	        else strTr="tableback1";
      		String[] temp=(String[])requestList.get(i);
      %>      
      <tr class="<%=strTr%>"> 
          <td align=center><input type="checkbox" name="chk" value="<%=temp[0]%>"></td>      
		  		<td ><%=temp[1]%></td>
          <td ><%=temp[2]==null?"":temp[2]%></td>
          <td ><%=temp[3]==null?"":temp[3]%></td>
          <td >&nbsp;<%=temp[4]==null?"":temp[4]%></td>
          <td >&nbsp;<%=temp[5]==null?"":temp[5]%></td>
          <td ><%=temp[6]==null?"":temp[6]%></td>
          <td ><%=temp[7]==null?"":temp[7]%></td>
          <td ><%=temp[8]==null?"":temp[8]%></td>
          <td ><%=temp[9]==null?"":temp[9]%></td>
          <td ><%=temp[10]==null?"":temp[10]%></td>
          <td ><%=temp[11]==null?"":temp[11]%></td>
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
<br>
              <tr align="left">
                    <td class="content_yellow">
                    <input name="hand" type="button"  value="手工分配" onclick="f_allocate()" >
                    </td>
                  </tr>
			  
</html:form>
</BODY>
</HTML>
<script language="JavaScript">
	
	function f_allocate(){
	    if(chk()!=''&&chk()!=null){
	    		document.forms[0].ids.value=chk();
	    		document.forms[0].hand.disabled=true;
	    		document.forms[0].action="handAllocateAction.do?method=manualAllcate";  
					document.forms[0].submit();  		
				
	    }else{
	  		alert("请先选择零件！");
	    }
	}

	
</script>
