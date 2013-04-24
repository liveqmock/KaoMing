<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri=  "/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri=  "/WEB-INF/tlds/common.tld" prefix="comtld" %>

<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="com.dne.sie.stock.bo.RepairOperateBo"%>  

<HTML>
<HEAD>
<title>维修携带工具出库</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/styles2.css" rel="stylesheet" type="text/css" />
<script language=javascript src="js/common.js"></script>
<script language=javascript src="js/commonSelect.js"></script>
<script language=javascript src="js/popCalendar.js"></script>
</HEAD>

<body >
<%
	try{
		ArrayList loanToolOutList = (ArrayList)request.getAttribute("loanToolOutList");
		int count=0;
		if(loanToolOutList!=null){
			count=Integer.parseInt((String)loanToolOutList.get(0));
		}
		List createByList=RepairOperateBo.getInstance().getPartCreateByList();

%>
<html:form action="repairOperateAction.do?method=loanToolOutList" method="post" >
<input type="hidden" name="ids">

 <table width="100%" border="0" cellpadding="0" cellspacing="1" class="content12">  
      <tr class="tableback1"> 
        <td width="80">维修单号：</td>
        <td width="140"><html:text property="serviceSheetNo"  styleClass="form" size="16" /></td>
        <td width="80">工具料号：</td>
        <td width="120"><html:text property="stuffNo"  styleClass="form" size="16" /></td>
        <td width="80">工具名称：</td>
        <td width="120"><html:text property="skuCode"  styleClass="form" size="16" /></td>
      </tr>
      <tr class="tableback2"> 
        
        <td width="80">申请人：</td>
        <td colspan=5><html:select styleClass="form" property="createBy"  >
	     		           <html:option value="">全部</html:option>
	     		           <%for(int i=0;createByList!=null&&i<createByList.size();i++){
	                  	Object[] temp=(Object[])createByList.get(i);
	                	%>
	                		<html:option value="<%=temp[0].toString()%>"><%=temp[1]%></html:option>
	                 	<%}%>
	     		           </html:select></td>
      </tr>
		
  </table>
	<h3 class="underline"><p align="left">&nbsp;&nbsp;<input type="submit" value="查询"></p></h3>
<br>
  <table width="100%" border="0" cellpadding="0" cellspacing="1" class="content12">
      <tr bgcolor="#CCCCCC"> 
        <td align=center width="10"><input name="allbox" type="checkbox" onClick="checkAll(this);"></td>
        <td><strong> 维修单号</strong></td>
        <td><strong> 工具料号</strong></td>
        <td><strong> 工具名称</strong></td>
        <td><strong> 单位</strong></td>
        <td><strong> 规格</strong></td>
        <td><strong> 数量</strong></td>
        <td><strong> 申请人</strong></td>
        <td><strong> 申请日期</strong></td>
                
      </tr>

	<%
       if(loanToolOutList!=null){
       	    String strTr="";
      	    for(int i=1;i<loanToolOutList.size();i++){
      	        if(i%2==0) strTr="tableback2";
      	        else strTr="tableback1";
      		String[] temp=(String[])loanToolOutList.get(i);
      %>      
      <tr class="<%=strTr%>"> 
          <td align=center><input type="checkbox" name="chk" value="<%=temp[0]%>"></td>      
		  		<td ><%=temp[1]%></td>
          <td ><%=temp[2]==null?"":temp[2]%></td>
          <td >&nbsp;<%=temp[3]==null?"":temp[3]%></td>
          <td ><%=temp[4]==null?"":temp[4]%></td>
          <td >&nbsp;<%=temp[5]==null?"":temp[5]%></td>
          <td ><%=temp[6]==null?"":temp[6]%></td>
          <td ><%=temp[7]==null?"":temp[7]%></td>
          <td ><%=temp[8]==null?"":temp[8]%></td>
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
                    <!--<input type="button"  value='打印出库单' onclick="f_print()">-->
                     <input name="sout" type="button"  value='确认出库' onclick="f_out()">
                    </td>
                  </tr>
			  

</html:form>
<%
}catch(Exception e){
	e.printStackTrace();
}%>
</BODY>
</HTML>
<script language="JavaScript">

	function f_print(){
		//window.open("stockOutPrint.html","","scrollbars=yes,width=700,height=600");
	}
	
		function f_out(){
	    if(chk()!=''&&chk()!=null){
	    		document.forms[0].ids.value=chk();
	    		document.forms[0].sout.disabled=true;
	    		document.forms[0].action="repairOperateAction.do?method=stockOutConfirm&partType=T";  
					document.forms[0].submit();  		
				
	    }else{
	  		alert("请先选择零件！");
	    }
	}
	
</script>
