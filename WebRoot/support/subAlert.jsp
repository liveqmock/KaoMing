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
    if(flag=="add") alert("���ʧ�ܣ�");
    else if(flag=="delete") alert("ɾ��ʧ�ܣ�");
    else if(flag=="modify") alert("�޸�ʧ�ܣ�");
    else if(flag=="order") alert("����ʧ�ܣ�");
    else alert("����ʧ�ܣ�");

}else{
    if(flag=="add"){
    	alert("��ӳɹ���");
    	window.parent.location.reload();	
    }else if(flag=="delete"){
		alert("ɾ���ɹ���");
		window.parent.location.reload();
    }else if(flag=="order"){
		alert("����ɹ���");
		window.parent.location.reload();
    }else if(flag=="modify"){
		alert("�޸ĳɹ���");
		window.parent.leftFrame.location="accountAction.do?method=treeInit";
		window.parent.main.location="accountAction.do?method=subDetail&id=<%=strId%>";
    }
    
}
	
</script> 
