<!--
自定义下拉框：
我们尽量将业务无关的代码封装起来，但由于该组件的特殊性（自定义的页面信息是千变万化的，不可
避免地在这个页面中搀杂着一些组件库要求从业务中传递来的数据（这里是字面值和内部值。我们会在
下面的代码中标明，哪些地方是必须的，哪些是可选的
此示例更象一个演示，演示业务相关的信息如何跟组件库结合，具体的项目中我们会提供更多支持，但
该页面业务相关的部分应该由二次开发完成
-->
<%@ page contentType="text/html; charset=gb2312" %>
<html>
	<head>
<link rel="stylesheet" href="css/treeStyle.css">	
<SCRIPT language="javascript" src="js/tree/client_ini.js"></SCRIPT>
<SCRIPT language="javascript" src="js/tree/basic.js"></SCRIPT>
<SCRIPT language="javascript" src="js/tree/control.js"></SCRIPT>
<SCRIPT language="javascript" src="js/tree/editor.js"></SCRIPT>
<SCRIPT language="javascript" src="js/tree/dropdown.js"></SCRIPT>
<SCRIPT  src="js/tree/menu.js"></SCRIPT>
<SCRIPT  src="js/tree/xtree.js"></SCRIPT> 
<script event="onload" for="window">initDocument();</SCRIPT>

		<SCRIPT language="javascript">
		var Ces_Library_path="";
		var Ces_Library_Common_path="js/tree/";

		<!--
			// 这个方法用来执行点击了确定按钮的操作，必须的。需要将传递业务相关的字面值和内部值
			// 此时需要二次开发中控制字面值和显示值的取值
			function fConfirm(){
                // 以下三句通常都是必须且雷同的,二次开发时赋值语句等号的左边不动，右边分别取应用相关的内部值和显示值
				// 内部值（提交值）
				window.parent.document.getElementById(window.parent._dropdown_box.editor.id.replace("_displayValue","")).value = tree.getCheckedValues();
				// 显示值
				window.parent.document.getElementById(window.parent._dropdown_box.editor.id.replace("_displayValue","")).displayValue = tree.getCheckedTexts();
				// 实际显示在父页面中的下拉框input对象，注意该input的id为原来的id加"_displayValue"，将显示值赋给它
				window.parent._dropdown_box.editor.value = tree.getCheckedTexts();
				

				// 组件库保留方法。点击确定以后隐藏下拉框，必须的
				hideDropDown();
			}
		//-->
		</SCRIPT>
	</head>
	<body  language="javascript"  style="background-color: whitesmoke;">
	    <!-- 该div的id名不能改变，保留值 -->
		<div id="_dropdown_div" >			
			<table width="100%" cellspacing="0" cellpadding="0">
				<!-- 下面是业务相关的, 可以为各式各样的交互输入框，如多选树，列表等 -->
				<tr>
					<td>
						<SCRIPT LANGUAGE="JavaScript">
						<!--
							var tree = new Tree("根","0");	
							<%
							String strTree=(String)request.getAttribute("strTree");
							if(strTree!=null) out.println(strTree);
							%>
							document.write(tree);
						//-->
						</SCRIPT>
					</TD>
				</TR>
				<!-- end 业务相关的 -->
				<TR>
					<TD align=right>
						<input type="button" onclick="fConfirm();" value="确 定">&nbsp;&nbsp;
						
					</TD>
				</TR>		
			</table>
		</div>
	</body>
</html>
