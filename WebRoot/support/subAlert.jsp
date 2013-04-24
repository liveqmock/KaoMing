<%@ page contentType="text/html;charset=GBK"%>
<%
String strResult=(String)request.getAttribute("tag");
String strState=(String)request.getAttribute("state");

String strId=(String)request.getAttribute("id");
%>
<script language=javascript>
var strTag=<%=strResult%>;
var flag="<%=strState%>";

    
if(strTag=="-1"){
    if(flag=="add") alert("添加失败！");
    else if(flag=="delete") alert("删除失败！");
    else if(flag=="modify") alert("修改失败！");
    else if(flag=="order") alert("排序失败！");
    else alert("操作失败！");

}else{
    if(flag=="add"){
    	alert("添加成功！");
    	window.parent.location.reload();	
    }else if(flag=="delete"){
		alert("删除成功！");
		window.parent.location.reload();
    }else if(flag=="order"){
		alert("排序成功！");
		window.parent.location.reload();
    }else if(flag=="modify"){
		alert("修改成功！");
		window.parent.leftFrame.location="accountAction.do?method=treeInit";
		window.parent.main.location="accountAction.do?method=subDetail&id=<%=strId%>";
    }
    
}
	
</script> 
