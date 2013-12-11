<%@ page contentType="text/html; charset=GBK" %>

<html>
<%
	String userName=(String)session.getAttribute("userName");
%>
<head>
<title>newsis</title><SCRIPT language="javascript" src="js/screen.js"></SCRIPT>

<link href="<%= request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css">
<script LANGUAGE="JAVASCRIPT">
<!--
function highlightButton(s) {
    if ("INPUT"==event.srcElement.tagName)
      event.srcElement.className=s
  }

function MM_openBrWindow(theURL,winName,features) { //v2.0
  window.open(theURL,winName,features);
}
function doRefresh(){
	self.location="top.jsp"
	setTimeout("doRefresh()",900000) // 15 minutes
}
//window.onload = doRefresh
//-->
</script>
<!-- InstanceEndEditable --> 
<script language="JavaScript" type="text/JavaScript">


function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}

function f_location(module){
	t1.innerHTML="&nbsp;&nbsp;<%=userName%>";	
	window.parent.content.location="loading.html"; 
	window.parent.fSet1.rows="69,118,*";  
    	window.parent.fSet2.cols="217,*";  
    if('null'=='<%=userName%>'){
    	window.parent.left.location="login.jsp";
    }else if(module=='management'){
    	window.parent.title1.document.getElementById("page_local").innerHTML="<IMG height=38 src='images/page_5.gif' width=217 border=0 >";    
		window.parent.title1.document.getElementById("titleName").innerHTML="<font size=5><strong>&nbsp;&nbsp;管理</strong></font>";
    	
    	window.parent.left.location="userAction.do?method=leftCreat&typeId=3";
    }else if(module=='repair'){
    	window.parent.title1.document.getElementById("page_local").innerHTML="<IMG height=38 src='images/page_2.gif' width=217 border=0 >";    
		window.parent.title1.document.getElementById("titleName").innerHTML="<font size=5><strong>&nbsp;&nbsp;维修</strong></font>";
    	
    	window.parent.left.location="userAction.do?method=leftCreat&typeId=6";
    }else if(module=='reception'){
    	window.parent.title1.document.getElementById("page_local").innerHTML="<IMG height=38 src='images/page_1.gif' width=217 border=0 >"; 
		window.parent.title1.document.getElementById("titleName").innerHTML="<font size=5><strong>&nbsp;&nbsp;前台</strong></font>";
    	   
    	window.parent.left.location="userAction.do?method=leftCreat&typeId=1";
    }else if(module=='stock'){
    	window.parent.title1.document.getElementById("page_local").innerHTML="<IMG height=38 src='images/page_3.gif' width=217 border=0 >";
		window.parent.title1.document.getElementById("titleName").innerHTML="<font size=5><strong>&nbsp;&nbsp;库存</strong></font>";
    	
    	window.parent.left.location="userAction.do?method=leftCreat&typeId=2";
    }else if(module=='support'){
    	//window.parent.left.location="userAction.do?method=leftCreat&typeId=7";
    
    }else if(module=='report'){
    	window.parent.title1.document.getElementById("page_local").innerHTML="<IMG height=38 src='images/page_4.gif' width=217 border=0 >";
		window.parent.title1.document.getElementById("titleName").innerHTML="<font size=5><strong>&nbsp;&nbsp;报表</strong></font>";
		
		window.parent.left.location="userAction.do?method=leftCreat&typeId=5";
    }else if(module='logistics'){
    	window.parent.title1.document.getElementById("page_local").innerHTML="<IMG height=38 src='images/page_6.gif' width=217 border=0 >";
		window.parent.title1.document.getElementById("titleName").innerHTML="<font size=5><strong>&nbsp;&nbsp;物流</strong></font>";
			
    	window.parent.left.location="userAction.do?method=leftCreat&typeId=4";
    }
}

function f_index(){
    //window.parent.content.location="loading.html";
    window.parent.fSet1.rows="69,0,*";  
    window.parent.fSet2.cols="0,*";
    window.parent.left.location="left.jsp"; 
    window.parent.content.location="indexAction.do?method=frameContent";
}



</script>
</head>
<body leftmargin="0" topmargin="0" rightmargin="0" bottommargin="0" marginwidth="0" marginheight="0" > 
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" class="content12">
  <!-- fwtable fwsrc="未命名" fwbase="page.jpg" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
  <tr>
    <td width="217"><a href="javascript:f_index()"><img name="page_r1_c1" src="imgage_km/logo2_bak.png"  border="0" alt=""></a></td>
    <td height="69" background="images/menu_r1_c13.gif"> <table border="0" cellpadding="0" cellspacing="0">
        <!-- fwtable fwsrc="未命名" fwbase="menu.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
        <tr> 
          <td><a onclick="f_location('reception')"><img src="images/menu_1.gif" alt="" name="menu_r1_c1" width="100" height="69" border="0" onMouseOver="MM_swapImage('menu_r1_c1','','images/menu_1h.gif',1)" onMouseOut="MM_swapImgRestore()"></a></td>
          <td><a onclick="f_location('repair')"><img src="images/menu_2.gif" alt="" name="menu_r1_c2" width="100" height="69" border="0" onMouseOver="MM_swapImage('menu_r1_c2','','images/menu_2h.gif',1)" onMouseOut="MM_swapImgRestore()"></a></td>
          <td><a onclick="f_location('stock')"><img src="images/menu_3.gif" alt="" name="menu_r1_c3" width="100" height="69" border="0" onMouseOver="MM_swapImage('menu_r1_c3','','images/menu_3h.gif',1)" onMouseOut="MM_swapImgRestore()"></a></td>
          <td><a onclick="f_location('logistics')"><img src="images/menu_6.gif" alt="" name="menu_r1_c14" width="100" height="69" border="0" onMouseOver="MM_swapImage('menu_r1_c14','','images/menu_6h.gif',1)" onMouseOut="MM_swapImgRestore()"></a></td>
          <td><a onclick="f_location('report')"><img src="images/menu_4.gif" alt="" name="menu_r1_c4" width="100" height="69" border="0" onMouseOver="MM_swapImage('menu_r1_c4','','images/menu_4h.gif',1)" onMouseOut="MM_swapImgRestore()"></a></td>
          <td><a onclick="f_location('management')"><img src="images/menu_5.gif" alt="" name="menu_r1_c15" width="100" height="69" border="0" onMouseOver="MM_swapImage('menu_r1_c15','','images/menu_5h.gif',1)" onMouseOut="MM_swapImgRestore()"></a></td>
          <td id="t1" class="content_yellow14">&nbsp;</td>
        </tr>
      </table></td>
  </tr>
</table>
</body>
<!-- InstanceEnd --></html>
 