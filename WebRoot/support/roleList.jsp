<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri=  "/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ page import="java.util.ArrayList"%> 
 <%@ page import="com.dne.sie.common.tools.DicInit"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>roleList</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/styles.css" rel="stylesheet" type="text/css" />
<SCRIPT language="JScript.Encode" src="js/commonSelect.js"></SCRIPT>
<script language=javascript src="js/common.js"></script>
</head>
<%
	ArrayList vtrData = (ArrayList)request.getAttribute("vtrData");
    int count=vtrData==null?0:Integer.parseInt((String)vtrData.get(0));
    
	ArrayList roleTypeList = (ArrayList)DicInit.SYS_CODE_MAP.get("ROLE_TYPE");
    Long userId=(Long)session.getAttribute("userId");

%>
<body onunload="close_child_win();">
 <html:form action="roleAction.do?method=roleList" method="post" >
 <html:hidden property="id"/>

<table align=center width="99%">
				
                <tr class="tableback2"> 
                	<tr>
                        <td >��ɫ����: &nbsp;&nbsp;<html:text property="roleName" styleClass="form" maxlength="25" size="15"/></td>
                                   
                        <td colspan="3">��<input type="submit" value="��ѯ"   ></td>
                </tr>
                
                </table>
	<h3 class="underline"><p align="left">&nbsp;&nbsp;</h3>
	 <table width="99%" border="0" cellpadding="0" cellspacing="1" class="content12">
                <tr bgcolor="#CCCCCC"> 
                        <td width="10"><input name="allbox" type="checkbox" onClick="checkAll(this);"></td>
                        <td width="20%"> <strong>��ɫ����</strong></td>
                        <td > <strong>��ע</strong></td>
                      </tr>            
      <%if(vtrData!=null){
       	    String strTr="";
       	    String strDisabled="";
      	    for(int i=1;i<vtrData.size();i++){
      	        if(i%2==0) strTr="tableback2";
      	        else strTr="tableback1";
      					String[] temp=(String[])vtrData.get(i);

						
		
      %>
      
      <tr class="<%=strTr%>"> 
          <td align=center><input type="checkbox" name="chk" <%=strDisabled%> value="<%=temp[0]%>"></td>
          <td ><A href="javascript:view('<%=temp[0]%>')"><%=temp[1]%></A></td>
         
       	  <td ><%=temp[2]==null?"":temp[2]%></td>
        </tr>
      
      <%}}%> 
      
        <tr> 
      <td height="1" bgcolor="#677789" colspan="11"></td>
    </tr>
    
    </table>
    
              <tr align="left">
                    <td class="content_yellow">
                     <input type="button"  value='����' onclick="f_new()">
                     <input type="button"  value='ɾ��' onclick="f_del()">
                    </td>
                  </tr>

	
	  </html:form>
</body>
</html>
<script language="JavaScript">

function f_new(){
	middleOpen("roleAction.do?method=addInit","","width=750,height=500,left=50,top=10,menubar=no,toolbar=no,resizable=yes,scrolling=yes,scrollbars=no");
}

function f_del(){
	if(chk()!=''&&chk()!=null){
    if(window.confirm("ȷ��ɾ���ü�¼��")){
    	document.forms[0].action="roleAction.do?method=roleDelete";
    	document.forms[0].id.value=chk();
			document.forms[0].submit();
    }
	}else{
		alert('����ѡ���¼!');
	}
}

 
function view(id){
    middleOpen("roleAction.do?method=roleDetail&id="+id,"","width=750,height=500,left=50,top=10,menubar=no,toolbar=no,resizable=yes,scrolling=yes,scrollbars=no");
   
}


</script>
