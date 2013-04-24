<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ page import="java.util.ArrayList"%> 
<%@ page import="java.util.List"%> 
<%@ page import="com.dne.sie.common.tools.CommonSearch"%> 
<%@ page import="com.dne.sie.common.tools.DicInit"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>stockQueryList</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/styles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/autocomplete.css" /> 
<script type="text/javascript" src="js/prototype.js"></script>
<script type="text/javascript" src="js/autocomplete.js"></script>
<SCRIPT language="javascript" src="js/commonSelect.js"></SCRIPT>
<script language=javascript src="js/popCalendar.js"></script>
<script language=javascript src="js/inputValidation.js"></script>
<script language=javascript src="js/common.js"></script>
<script language=javascript src="js/popPartInfo.js"></script>

</head>
<%
	try{
		String tag = request.getAttribute("tag")==null?"":(String)request.getAttribute("tag");
		ArrayList skuTypeList = (ArrayList)DicInit.SYS_CODE_MAP.get("SKU_TYPE");
		
%>
<script>		
var tag="<%=tag%>";
if(tag=="1") alert("�����ɹ�");
else if(tag=="-1") alert("����ʧ��");
</script>	
<%
		ArrayList stockInfoList = (ArrayList)request.getAttribute("stockInfoList");
		int count=0;
		if(stockInfoList!=null){
			count=Integer.parseInt((String)stockInfoList.get(0));
		}


%>
<body onload="showQueryDateTR('dateScope',3);">
<html:form action="stockOutOperateAction.do?method=stockAdjustOutList" method="post" >
  <input name="ids" type="hidden">
  <input name="adOutNum" type="hidden">
  <input name="adOutRemart" type="hidden">
  
<table align=center width="99%">
				
                <tr class="tableback2"> 
                  <td width="98">�Ϻţ�</td>
                  <td width="121"><html:text  styleClass="form" property="stuffNo"  maxlength="32"  size="16"  /></td>
                  <td width="98">������ƣ�</td>
                  <td width="170"><html:text  styleClass="form" property="skuCode"  maxlength="32"  size="22"   />&nbsp;
                        	<a href="javascript:showPartInfo('skuCode')"><img src="googleImg/icon/search.gif" align="absmiddle" border="0"></a></td>
                  <td width="98">���ۣ�</td>
                  <td width="121"><html:text  styleClass="form" property="strPerCost"    maxlength="8" size="16" onkeydown="f_onlymoney()"/></td>
                </tr>
                <tr class="tableback1"> 
                  <td width="98">��λ��</td>
                  <td width="121"><html:text  styleClass="form" property="skuUnit"  maxlength="4"  size="16"  /></td>
                  <td width="98">���������</td>
                  <td width="126"><html:text  styleClass="form" property="strSkuNum"  maxlength="6"  size="22"  onkeydown="f_onlynumber()" /></td>
                  <td width="98">���ͣ�</td>
                  <td width="121"><html:select property="skuType" styleClass="form">
	                          <option value="">ȫ��</option>
								<%
								  for(int i=0;skuTypeList!=null&&i<skuTypeList.size();i++){
									String[] temp=(String[])skuTypeList.get(i);
									if(!temp[0].equals("L")&&!temp[0].equals("S")){
								%>
								 <html:option value="<%=temp[0]%>"><%=temp[1]%></html:option>
								<%}}%>
      						</html:select></td>
                </tr>
               
                <tr class="tableback2"> 
                  	<td width="80">���ʱ�䣺</td>
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
	<h3 class="underline"><p align="left">&nbsp;&nbsp;<input name="query" type="button" value="��ѯ" onclick="f_query()"></p></h3>

	 <table width="99%" border="0" cellpadding="0" cellspacing="1" class="content12">
	 <tr><font color="blue">��¼������ <%=count%></font> </tr>
                      <tr bgcolor="#CCCCCC"> 
                        <td width="10"><input name="allbox" type="checkbox" onClick="checkAll(this);"></td>
                        <td width="15"><strong>��</strong></td>
                        <td width="13%"><strong>&nbsp;�Ϻ�</strong></td>
                        <td width="18%"><strong>�������</strong></td>
                        <td><strong>����</strong></td>
                        <td><strong>����</strong></td>
                        <td><strong>�ɱ�����(RMB)</strong></td>
                        <td><strong>��������($)</strong></td>
                        <td><strong>��������</strong></td>
                        <td width="12%"><strong>���ⱸע</strong></td>
                      </tr>
         
        <%
       if(stockInfoList!=null){
       		char SPLIT=0x0001;
       	    String strTr="";
      	    for(int i=1;i<stockInfoList.size();i++){
      	        if(i%2==0) strTr="tableback2";
      	        else strTr="tableback1";
      			String[] temp=(String[])stockInfoList.get(i);
      %>      
      <tr class="<%=strTr%>"> 
          <td align=center><input type="checkbox" name="chk" value="<%=temp[0]+SPLIT+i%>"></td>
          <td ><font color="blue"><%=i%>&nbsp;</font></td>
          <td ><%=temp[1]==null?"":temp[1]%></td>
          <td ><%=temp[2]==null?"":temp[2]%></td>
          <td ><font <%if("0".equals(temp[3])){%>size="3" color="red"<%}%>><%=temp[3]==null?"":temp[3]%></font></td><input type="hidden" name="skuNowNum" value="<%=temp[3]==null?"0":temp[3]%>">
          <td ><%=DicInit.getSystemName("SKU_TYPE",temp[5])%></td>
          <td ><%=temp[4]==null?"":temp[4]%></td>
          <td ><%=temp[6]==null?"":temp[6]%></td>
       	  <td > 
       	       <input name="outNum" class="form" size="5" value=""  maxlength="6" onkeydown="f_onlynumber()" onchange="f_ckbox('<%=temp[0]+SPLIT+i%>')">
    	  </td>
    	  <td > 
       	       <input name="outRemark" class="form" size="22" value="" >
    	  </td>
        </tr>      
        
      <%}}else{%> 
	<tr class="tableback1"></tr>
      <%}%>
      
   
        
        <tr> 
      <td height="1" bgcolor="#677789" colspan="11"></td>
    </tr>
        
        </table>
         <tr align="left">
           		<td class="content_yellow">
                     <input name="outConfirm" type="button"  value='ȷ�ϳ���' onclick="f_confirm()">
                    </td>
                  </tr>

	
</html:form>
</body>
</html>
       
      
<script language="JavaScript">
function f_query(){
	document.forms[0].query.disabled = true;
	document.forms[0].action="stockOutOperateAction.do?method=stockAdjustOutList"; 
	document.forms[0].submit();
}
	
function f_ckbox(val){
	var theForm = document.forms[0];
		for(var i = 0; i < theForm.length; i++) {
	      if (theForm[i].type == "checkbox" && theForm[i].disabled==false&&theForm[i].value==val) {
	         theForm[i].checked=true;
	         break;
		  }
	   }
	
}
	
function f_confirm(){
	var ids=chk();
	
	if(ids==null||ids==''){
		alert('����ѡ�����!');
	
	}else{
		if(getAdOutNums()){
			document.forms[0].outConfirm.disabled = true;
			document.forms[0].action="stockOutOperateAction.do?method=stockAdjustOutConfirm";
			document.forms[0].submit();
		}
	}
	
}


function getAdOutNums(){

 	<%
    String strChk="chk",tempSeq="";
    if(count>=2) {
    	strChk="chk[i]";
    	tempSeq="[tempSeq]";
    }
    %>
    
    var chkId = '';
    var chkOutNum = '';
    var chkOutRemark = '';
    var spliter = unescape('%01');
    var len = document.forms[0].chk.length;
	if(len>1){
	    var tag = 0;
		for(var i=0;i<len;i++){
		    if(document.forms[0].<%=strChk%>.checked){   
		        var chkVal=document.forms[0].<%=strChk%>.value;
		        var tempId=chkVal.substring(0,chkVal.indexOf(spliter));
		        var tempSeq=chkVal.substring(chkVal.indexOf(spliter)+1)-1;

		        var tempONum=new Number(document.forms[0].outNum<%=tempSeq%>.value);
		        var tempORemark=document.forms[0].outRemark<%=tempSeq%>.value;
		        var tempNowNum=new Number(document.forms[0].skuNowNum<%=tempSeq%>.value);
		        
		        if(tempONum==null||tempONum==''){
		            alert("�������"+(i+1)+"�м�¼�ĳ�������");
		            document.forms[0].outNum<%=tempSeq%>.focus();
		            return false;
		        }else if(tempORemark==null||tempORemark==''){
		            alert("�������"+(i+1)+"�м�¼�ĳ��ⱸע");
		            document.forms[0].outRemark<%=tempSeq%>.focus();
		            return false;
		        
		        }else{
		        	if(tempONum>tempNowNum){
		        		alert("��"+(i+1)+"�м�¼�����������ܴ����ִ�����");
		        		document.forms[0].outNum<%=tempSeq%>.focus();
		        		return false;
		        	}
		            if (tag==0) {
		            	chkId = tempId;
		            	chkOutNum = tempONum;
		            	chkOutRemark = tempORemark;
		            	tag = 1;
		            } else {
		            	chkId = chkId + ","+tempId;
		            	chkOutNum = chkOutNum + ","+tempONum;
		            	chkOutRemark = chkOutRemark + ","+tempORemark;
		            }           
		    	}				
		    }
		}
	} else {
		if(document.forms[0].chk.checked){
		    var chkVal=document.forms[0].<%=strChk%>.value;
		    var tempId=chkVal.substring(0,chkVal.indexOf(spliter));
		    var tempSeq=chkVal.substring(chkVal.indexOf(spliter)+1);
		    
		    var tempONum=new Number(document.forms[0].outNum<%=tempSeq%>.value);
		    var tempORemark=document.forms[0].outRemark<%=tempSeq%>.value;
		    var tempNowNum=new Number(document.forms[0].skuNowNum<%=tempSeq%>.value);
		    
		    if(tempONum==null||tempONum==''){
		        alert("�������������");
		        document.forms[0].outNum<%=tempSeq%>.focus();
		        return false;
		    }else if(tempORemark==null||tempORemark==''){
		   	 	document.forms[0].outRemark<%=tempSeq%>.focus();
		        alert("��������ⱸע");
		        return false;
		    }else{
		    	if(tempONum>tempNowNum){
		        	alert("�����������ܴ����ִ�����");
		        	document.forms[0].outNum<%=tempSeq%>.focus();
		        	return false;
		        }
				chkId=tempId;
		        chkOutNum = tempONum;
		        chkOutRemark = tempORemark;
		    }
	 	}
	}
	
	document.forms[0].ids.value=chkId;
	document.forms[0].adOutNum.value=chkOutNum;
	document.forms[0].adOutRemart.value=chkOutRemark;
	
	return true;
}

new AutoTip.AutoComplete("stuffNo", function() {
	 return "partInfoAction.do?method=getPartInfo&inputValue=" + escape(this.text.value);
});
	
</script>


<%
}catch(Exception e){
	e.printStackTrace();
}%>