<!--
�Զ���������
���Ǿ�����ҵ���޹صĴ����װ�����������ڸ�����������ԣ��Զ����ҳ����Ϣ��ǧ���򻯵ģ�����
����������ҳ���в�����һЩ�����Ҫ���ҵ���д����������ݣ�����������ֵ���ڲ�ֵ�����ǻ���
����Ĵ����б�������Щ�ط��Ǳ���ģ���Щ�ǿ�ѡ��
��ʾ������һ����ʾ����ʾҵ����ص���Ϣ��θ�������ϣ��������Ŀ�����ǻ��ṩ����֧�֣���
��ҳ��ҵ����صĲ���Ӧ���ɶ��ο������
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
			// �����������ִ�е����ȷ����ť�Ĳ���������ġ���Ҫ������ҵ����ص�����ֵ���ڲ�ֵ
			// ��ʱ��Ҫ���ο����п�������ֵ����ʾֵ��ȡֵ
			function fConfirm(){
                // ��������ͨ�����Ǳ�������ͬ��,���ο���ʱ��ֵ���Ⱥŵ���߲������ұ߷ֱ�ȡӦ����ص��ڲ�ֵ����ʾֵ
				// �ڲ�ֵ���ύֵ��
				window.parent.document.getElementById(window.parent._dropdown_box.editor.id.replace("_displayValue","")).value = tree.getCheckedValues();
				// ��ʾֵ
				window.parent.document.getElementById(window.parent._dropdown_box.editor.id.replace("_displayValue","")).displayValue = tree.getCheckedTexts();
				// ʵ����ʾ�ڸ�ҳ���е�������input����ע���input��idΪԭ����id��"_displayValue"������ʾֵ������
				window.parent._dropdown_box.editor.value = tree.getCheckedTexts();
				

				// ����Ᵽ�����������ȷ���Ժ����������򣬱����
				hideDropDown();
			}
		//-->
		</SCRIPT>
	</head>
	<body  language="javascript"  style="background-color: whitesmoke;">
	    <!-- ��div��id�����ܸı䣬����ֵ -->
		<div id="_dropdown_div" >			
			<table width="100%" cellspacing="0" cellpadding="0">
				<!-- ������ҵ����ص�, ����Ϊ��ʽ�����Ľ�����������ѡ�����б�� -->
				<tr>
					<td>
						<SCRIPT LANGUAGE="JavaScript">
						<!--
							var tree = new Tree("��","0");	
							<%
							String strTree=(String)request.getAttribute("strTree");
							if(strTree!=null) out.println(strTree);
							%>
							document.write(tree);
						//-->
						</SCRIPT>
					</TD>
				</TR>
				<!-- end ҵ����ص� -->
				<TR>
					<TD align=right>
						<input type="button" onclick="fConfirm();" value="ȷ ��">&nbsp;&nbsp;
						
					</TD>
				</TR>		
			</table>
		</div>
	</body>
</html>
