<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ page import="java.util.ArrayList"%> 
<%@ page import="java.util.List"%> 
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
		ArrayList skuTypeList = (ArrayList)DicInit.SYS_CODE_MAP.get("SKU_TYPE");
		ArrayList stockInfoList = (ArrayList)request.getAttribute("stockInfoList");
		int count=0;
		if(stockInfoList!=null){
			count=Integer.parseInt((String)stockInfoList.get(0));
		}
		ArrayList binCodes = (ArrayList)session.getAttribute("binCodes");


%>
<body onload="showQueryDateTR('dateScope',3);">
<html:form action="stockOutOperateAction.do?method=stockBinMoveList" method="post" >

<table align=center width="99%">
				
                <tr class="tableback2"> 
                  <td width="98">�Ϻţ�</td>
                  <td width="121"><html:text  styleClass="form" property="stuffNo"  maxlength="32"  size="16"  /></td>
                  <td width="98">������ƣ�</td>
                  <td width="170"><html:text  styleClass="form" property="skuCode"  maxlength="32"  size="22"   />&nbsp;
                        	<a href="javascript:showPartInfo('skuCode')"><img src="googleImg/icon/search.gif" align="absmiddle" border="0"></a></td>
                  <td width="98">��λ��</td>
                  <td width="121"><html:text  styleClass="form" property="skuUnit"  maxlength="4"  size="16"  /></td>
                </tr>
                <tr class="tableback1"> 
                  <td width="98">��ʼ��λ��</td>
                  <td width="121"><html:select styleClass="form" property="binCode1"    style="width:100">
		  				<option value="">==��ѡ��==</option>
                        <%for(int j=0; binCodes!=null&&j<binCodes.size(); j++ ){
                            	String bin=(String)binCodes.get(j);
                        %>
	                        <html:option value="<%=bin%>"><%=bin%></html:option>
                        <%}%>
	          		</html:select></td>
                  
                  <td width="98">��ֹ��λ��</td>
                  <td width="121"><html:select styleClass="form" property="binCode2"    style="width:100">
		  				<option value="">==��ѡ��==</option>
                        <%for(int j=0; binCodes!=null&&j<binCodes.size(); j++ ){
                            	String bin=(String)binCodes.get(j);
                        %>
	                        <html:option value="<%=bin%>"><%=bin%></html:option>
                        <%}%>
	          		</html:select></td>
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
                        <td width="13%"><strong>&nbsp;�Ϻ�</strong></td>
                        <td width="18%"><strong>�������</strong></td>
                        <td><strong>����</strong></td>
                        <td><strong>����</strong></td>
                        <td><strong>�������</strong></td>
                        <td width="12%"><strong>��λ</strong></td>
                        <td width="5%"><strong>����</strong></td>
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
          <td ><%=temp[1]==null?"":temp[1]%></td>
          <td ><%=temp[2]==null?"":temp[2]%></td>
          <td ><font <%if("0".equals(temp[3])){%>size="3" color="red"<%}%>><%=temp[3]==null?"":temp[3]%></font></td><input type="hidden" name="skuNowNum" value="<%=temp[3]==null?"0":temp[3]%>">
          <td ><%=temp[4]==null?"":temp[4]%></td>
          <td ><%=temp[5]==null?"":temp[5]%></td>
       	  <td ><select styleClass="form" property="binCode"    style="width:100" id="s_<%=temp[0]%>">
		  				<option value="">==��ѡ��==</option>
                        <%for(int j=0; binCodes!=null&&j<binCodes.size(); j++ ){
                            	String bin=(String)binCodes.get(j);
                        %>
	                        <option value="<%=bin%>" <%if(temp[6].equals(bin)){ %> selected <%} %>><%=bin%></option>
                        <%}%>
	          </select>
    	  </td>
    	  <td ><input type="button" value="�ƶ�" onclick="f_move(<%=temp[0]%>)"></td>
        </tr>      
        
      <%}}else{%> 
	<tr class="tableback1"></tr>
      <%}%>
      
   
        
        <tr> 
      <td height="1" bgcolor="#677789" colspan="11"></td>
    </tr>
        <comtld:pageControl numOfRcds="<%=count%>"></comtld:pageControl>
</table>


	
</html:form>
</body>
</html>
       
      
<script language="JavaScript">
function f_query(){
	document.forms[0].query.disabled = true;
	document.forms[0].action="stockOutOperateAction.do?method=stockBinMoveList"; 
	document.forms[0].submit();
}
	
function f_move(id){
	var nowBin = document.getElementById("s_"+id).value;
	if(nowBin == ''){
		alert("����ѡ���λ");
		return;
	}

	ajaxMove(id,nowBin);
}

var ajax = new sack(); // ����ajax����

function ajaxMove(id,nowBin){
	ajax.setVar("id",id);
	ajax.setVar("binCode", nowBin);
	ajax.setVar("method", "ajaxMoveBin");
	ajax.requestFile = "stockOutOperateAction.do";
	ajax.method = "GET";
	ajax.onCompletion = selectResult;
	
	ajax.runAJAX();
}


function selectResult(){
	
	var returnXml = ajax.responseXML;
	var myisEnable = returnXml.getElementsByTagName("result")[0].firstChild.nodeValue;
			
	if((eval(myisEnable))){
		alert("�ƶ��ɹ�");
	}else{
		alert("�ƶ�ʧ��");
	}
		
		
}

new AutoTip.AutoComplete("stuffNo", function() {
	 return "partInfoAction.do?method=getPartInfo&inputValue=" + escape(this.text.value);
});
	
</script>


<%
}catch(Exception e){
	e.printStackTrace();
}%>