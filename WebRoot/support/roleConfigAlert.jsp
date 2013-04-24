<%@ page contentType="text/html;charset=GBK"%>
<%
String strResult=(String)request.getAttribute("tag");
String roleId=(String)request.getAttribute("roleId")==null?"":(String)request.getAttribute("roleId");
String pendingIds=(String)request.getAttribute("pendingIds")==null?"":(String)request.getAttribute("pendingIds");
String strLink=(String)request.getAttribute("link")==null?"":(String)request.getAttribute("link");

%>
<script language=javascript>
var strTag=<%=strResult%>;

    
if(strTag=="-1"){
    alert("保存失败！");
    
}else{
    alert("保存成功！");
    <%if(!strLink.equals("")){%>
	location="functionAction.do?method=functionTree&link=<%=strLink%>";
    <%}else if(!roleId.equals("")){%>
	location="functionAction.do?method=functionTree&selectRole=<%=roleId%>";
    <%}else{%>
    	location="functionAction.do?method=pendingTree&pendingIds=<%=pendingIds%>";
    <%}%>
    
}
	
</script> 
