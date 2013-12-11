<%@ page contentType="text/html; charset=GBK" %>
<html>
 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>newsis</title><SCRIPT language="javascript" src="js/screen.js"></SCRIPT>
</head>
 
<FRAMESET ROWS="69,0,*" id="fSet1" border="0" frameborder="NO" >
	<FRAME SRC="indexAction.do?method=top" NAME="title" noResize="true" frameborder="no" scrolling="no" >
	<FRAME SRC="topImg.jsp" NAME="title1" noResize="true" frameborder="no" scrolling="no" >
	<FRAMESET id="fSet2" COLS="0,*">
		<FRAME SRC="left.jsp" NAME="left" marginheight="0" marginwidth="0" align="middle" scrolling="auto" noresize>
		<FRAME SRC="indexAction.do?method=frameContent" NAME="content" frameborder="no" scrolling="auto">
	</FRAMESET>
</FRAMESET><noframes></noframes>
</html> 