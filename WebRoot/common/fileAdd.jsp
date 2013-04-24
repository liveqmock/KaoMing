<%@ page contentType="text/html; charset=GBK" %>	
	
<html>
<head>
<link rel="stylesheet" href="<%= request.getContextPath()%>/css/style.css">
<title>upload文件</title><SCRIPT language="JScript.Encode" src="<%= request.getContextPath()%>/js/screen.js"></SCRIPT>
<SCRIPT language="JScript.Encode" src="<%= request.getContextPath()%>/js/common.js"></SCRIPT>
</head>

<%
	String tag=request.getAttribute("tag")==null?"":(String)request.getAttribute("tag");
	String strPath=request.getAttribute("path")==null?"":(String)request.getAttribute("path");
	String attacheId=request.getAttribute("attacheId")==null?"":(String)request.getAttribute("attacheId");
	String createDate=request.getAttribute("createDate")==null?"":(String)request.getAttribute("createDate");
	String filePath=request.getAttribute("filePath")==null?"":(String)request.getAttribute("filePath");
	String affixType=request.getAttribute("affixType")==null?"":(String)request.getAttribute("affixType");

%>

<body>
<form method="post" name="form1" action="<%= request.getContextPath()%>/apacheUpload.do?method=fileUpload" ENCTYPE="multipart/form-data">

	<input name="myfileName" type="file" class="form" size="40" onkeydown="f_filt()"> 
	<SCRIPT language=JAVASCRIPT1.2>
	<%if(tag.equals("1")){%>
	var tempPath='<%=strPath%>';
	parent.selectResult(unescape(replaceAll0(tempPath,unescape('%01'),"%u")),"<%=attacheId%>","<%=createDate%>","<%=filePath%>","<%=affixType%>");
	<%}else if(tag.equals("-1")){%>
	alert("上传失败，请重试或联系系统管理员！");
	parent.fileAddFailed("-1");
	<%}else if(tag.equals("1015")){%>
	alert("此类文件上传被拒绝，请与管理员联系！");
	parent.fileAddFailed("1015");
	<%}else if(tag.equals("1010")){%>
	alert("此类文件不能上传，请与管理员联系！");
	parent.fileAddFailed("1010");
	<%}else if(tag.equals("1105")){%>
	alert("您上传的文件:单个文件超过最大尺寸限制！");
	parent.fileAddFailed("1105");
	<%}else if(tag.equals("1110")){%>
	alert("您上传的文件超过最大尺寸限制！");
	parent.fileAddFailed("1110");
	<%}%>
	</SCRIPT>

</form>
</body>
</html>	

<SCRIPT language=JAVASCRIPT1.2>

function doSave(maxSize){ 
	//var maxSize;
	if (maxSize == null || maxSize == "") {
		maxSize = "104857600";
	}
	
	var path=document.forms[0].myfileName.value;
	
	//alert("path="+path);
	var temp="";
	if(path.lastIndexOf('/')!=-1){
		temp=path.substring(path.lastIndexOf('/')+1);
	}else if(path.lastIndexOf('\\')!=-1){
		temp=path.substring(path.lastIndexOf('\\')+1);
	}

	if(temp.indexOf('.')==-1){
		alert("上传文件必须有后缀名");
	}else{
		path=replaceAll0(escape(path),"%u",unescape('%01'));
		document.forms[0].action="<%= request.getContextPath()%>/apacheUpload.do?method=fileUpload&path="+path+"&fileType="+parent.getFileType()+"&fileMaxSize="+maxSize;
		document.forms[0].submit();
	}
}

function doSaveWithSize(maxSize){ 
	var path=document.forms[0].myfileName.value;
	path=replaceAll0(escape(path),"%u",unescape('%01'));
	document.forms[0].action="<%= request.getContextPath()%>/apacheUpload.do?method=fileUpload&path="+path+"&fileType="+parent.getFileType()+"&fileMaxSize="+maxSize;
	document.forms[0].submit();
}

function doSaveWithForeignKey(id){ 
	var path=document.forms[0].myfileName.value;
	path=replaceAll0(escape(path),"%u",unescape('%01'));
	document.forms[0].action="<%= request.getContextPath()%>/apacheUpload.do?method=fileUpload&path="+path+"&fileType="+parent.getFileType()+"&id="+id;
	document.forms[0].submit();
}

function doSaveWithForeignKey2(id,fileType){ 
	var path=document.forms[0].myfileName.value;
	path=replaceAll0(escape(path),"%u",unescape('%01'));
	document.forms[0].action="<%= request.getContextPath()%>/apacheUpload.do?method=fileUpload&path="+path+"&fileType="+fileType+"&id="+id;
	document.forms[0].submit();
}

function getMyfileName(){ 
	return document.forms[0].myfileName.value;
}
function changeBodyColorClass(className){
	document.body.className = className;
}

function f_filt(){
	event.returnValue = false;

}
</SCRIPT>