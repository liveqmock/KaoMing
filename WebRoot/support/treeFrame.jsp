<%@ page contentType="text/html;charset=GBK"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>treeFrame</title>
</head>

<frameset cols="400,*" frameborder="yes" border="0" framespacing="0">
  <frame src="accountAction.do?method=treeInit" name="leftFrame" scrolling="auto" >
  <frame src="accountAction.do?method=addInit" name="main" id="rightFrame" scrolling="auto" >
</frameset>
<noframes><body>
</body></noframes>
</html>
