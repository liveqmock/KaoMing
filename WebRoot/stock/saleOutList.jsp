<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri=  "/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri=  "/WEB-INF/tlds/common.tld" prefix="comtld" %>

<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="com.dne.sie.reception.bo.SaleInfoBo"%>  

<HTML>
<HEAD>
<title>�������ó���</title>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/styles2.css" rel="stylesheet" type="text/css" />
<script language=javascript src="js/common.js"></script>
<script language=javascript src="js/commonSelect.js"></script>
<script language=javascript src="js/popCalendar.js"></script>
</HEAD>

<body >
<%
	try{
		ArrayList saleList = (ArrayList)request.getAttribute("saleList");
		int count=0;
		if(saleList!=null){
			count=Integer.parseInt((String)saleList.get(0));
		}
		List createByList=SaleInfoBo.getInstance().getPartCreateByList();

%>
<html:form action="requestOperateAction.do?method=saleOutList" method="post" >
<input type="hidden" name="ids">

 <table width="100%" border="0" cellpadding="0" cellspacing="1" class="content12">  
      <tr class="tableback1"> 
        <td width="80">ѯ�۵��ţ�</td>
        <td width="140"><html:text property="saleNo"  styleClass="form" size="16" /></td>
        <td width="80">�Ϻţ�</td>
        <td width="120"><html:text property="stuffNo"  styleClass="form" size="16" /></td>
        <td width="80">������ƣ�</td>
        <td width="120"><html:text property="skuCode"  styleClass="form" size="16" /></td>
      </tr>
      <tr class="tableback2"> 
        <td width="80">���ͣ�</td>
        <td width="140"><html:text property="modelCode"  styleClass="form" size="16" /></td>
        <td width="80">������룺</td>
        <td width="120"><html:text property="modelSerialNo"  styleClass="form" size="16" /></td>
        <td width="80">�����ˣ�</td>
        <td width="120"><html:select styleClass="form" property="createBy"  >
	     		           <html:option value="">ȫ��</html:option>
	     		           <%for(int i=0;createByList!=null&&i<createByList.size();i++){
	                  	Object[] temp=(Object[])createByList.get(i);
	                	%>
	                		<html:option value="<%=temp[0].toString()%>"><%=temp[1]%></html:option>
	                 	<%}%>
	     		           </html:select></td>
      </tr>
			<tr class="tableback1"> 
      		<td width="80">����ʱ�䣺</td>
        	<td width="120"><html:select property="dateScope" styleClass="form" style="width:100" onchange="changeDateDisplay(this,'inDate1','inDate2')">
						        	<html:option value="4">ȫ��</html:option>
						        	<html:option value="0">����</html:option>
						        	<html:option value="3">���ڷ�Χ</html:option>
											<html:option value="1">����</html:option>
											<html:option value="2">����</html:option>
                      </html:select>  </td>
          <td width="80">��ʼ���ڣ� </td>
          <td width="120"><html:text property="inDate1" styleId="inDate1" styleClass="form" readonly="true" size="14"/> 
		     			<a onclick="event.cancelBubble=true;" href="javascript:showCalendar('imageOne',true,'inDate1');">
		      			<img src="googleImg/icon/calendar.gif" id="imageOne" width="18" height="18" border="0" align="absmiddle">
		     			</a>
        	</td>
	        <td width="80">��ֹ���ڣ�</td>
	        <td width="120"><html:text property="inDate2" styleId="inDate2" styleClass="form" readonly="true" size="14"/> 
        			<a onclick="event.cancelBubble=true;" href="javascript:showCalendar('imageTwo',true,'inDate2');">  
           			<img src="googleImg/icon/calendar.gif" id="imageTwo" width="18" height="18" border="0" align="absmiddle">
         			</a>
        		</td>
       </tr>
  </table>
	<h3 class="underline"><p align="left">&nbsp;&nbsp;<input type="submit" value="��ѯ"></p></h3>
<br>
  <table width="100%" border="0" cellpadding="0" cellspacing="1" class="content12">
      <tr bgcolor="#CCCCCC"> 
        <td align=center width="10"><input name="allbox" type="checkbox" onClick="checkAll(this);"></td>
        <td><strong> �Ϻ�</strong></td>
        <td><strong> �������</strong></td>
        <td><strong> ����</strong></td>
        <td><strong> ��λ</strong></td>
        <td><strong> ��λ</strong></td>
        <td><strong> �۸�</strong></td>
        <td><strong> ���۵�����</strong></td>
        <td><strong> ������</strong></td>
        <td><strong> ��������</strong></td>
                
      </tr>

	<%
       if(saleList!=null){
       	    String strTr="";
      	    for(int i=1;i<saleList.size();i++){
      	        if(i%2==0) strTr="tableback2";
      	        else strTr="tableback1";
      		String[] temp=(String[])saleList.get(i);
      %>      
      <tr class="<%=strTr%>"> 
          <td align=center><input type="checkbox" name="chk" value="<%=temp[0]%>"></td>      
		  		<td ><%=temp[1]%></td>
          <td ><%=temp[2]==null?"":temp[2]%></td>
          <td >&nbsp;<%=temp[3]==null?"":temp[3]%></td>
          <td ><%=temp[4]==null?"":temp[4]%></td>
          <td ><%=temp[10]==null?"":temp[10]%></td>
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
                    <!--<input type="button"  value='��ӡ���ⵥ' onclick="f_print()">-->
                     <input name="sout" type="button"  value='ȷ�ϳ���' onclick="f_out()">
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
	    		document.forms[0].action="requestOperateAction.do?method=stockOutConfirm";  
					document.forms[0].submit();  		
				
	    }else{
	  		alert("����ѡ�������");
	    }
	}
	
</script>
