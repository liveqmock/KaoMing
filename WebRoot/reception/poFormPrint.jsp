<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.util.ArrayList"%> 
<%@ page import="com.dne.sie.common.tools.Operate"%> 
<%@ page import="com.dne.sie.reception.form.PoForm"%> 
<html>
<head>
<title>������</title>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">

<style type="text/css">
<!--
table
{
	font-size: 12pt;
    font-family:'����Ҧ��';
}

@font-face
{
    font-family:'����Ҧ��';
    mso-font-alt:'����Ҧ��';
    text-align:right;text-indent:249.95pt;
    mso-char-indent-count:22.0
}

.border1 {
    border:solid windowtext 1.0pt;
    mso-border-alt:solid windowtext .5pt;
    padding:0cm 1.4pt 0cm 1.4pt;
}
.borderrow1{
    border:solid windowtext 1.0pt;
    border-left:none;
    mso-border-left-alt:solid windowtext .5pt;
    mso-border-alt: solid windowtext .5pt;
    padding:0cm 1.4pt 0cm 1.4pt;
}
.bordercol1{
    border:solid windowtext 1.0pt;
    border-top:none;
    mso-border-top-alt:solid windowtext .5pt;
    mso-border-alt:solid windowtext .5pt;
    padding:0cm 1.4pt 0cm 1.4pt;
}
.border2{
    border-top:none;
    /*border-left: none;*/
    border-bottom:solid windowtext 1.0pt;
    border-right:solid windowtext 1.0pt;
    mso-border-top-alt:solid windowtext .5pt;
    /*mso-border-left-alt:solid windowtext .5pt;*/
    mso-border-alt:solid windowtext .5pt;
    padding:0cm 1.4pt 0cm 1.4pt;
}
-->
</style>

</head>
<%
try{
		String[] kmInfo = (String[])request.getAttribute("kmInfo");
		ArrayList poFormPrintList = (ArrayList)request.getAttribute("poFormPrintList");
		PoForm pf=(PoForm)poFormPrintList.get(0);
		
		String userName = (String) session.getAttribute("userName");
		
%>
<body >
<form>
<OBJECT  id=WebBrowser  classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2  height=0  width=0 VIEWASTEXT> </OBJECT> 

<table border="0" width="98%" height="162">
    <tr>
        <td height="150" width="182"><b><i><font size="8" color="purple">&nbsp;&nbsp;KAO</font></i></b><p style="line-height:10px;">
            <b><i><font size="8" color="purple">&nbsp;&nbsp;MING</font></i></b></td>
        <td height="150" width="575">
            <p>&nbsp;</p>
            <p align="right" style="line-height:3px;"><b><font size="4">KAO MING MACHINERY INDUSTRIAL CO., LTD.</font></b></p>
            <p align="right" style="line-height:3px;">����������ҵ�ɷ����޹�˾�Ϻ�Ӫҵ��&nbsp; </p>
            <p align="right" style="line-height:3px;">�Ϻ���Ƚ�������޹�˾&nbsp;</p>
            <p align="right" style="line-height:3px;"><b>E-MAIL��kaoming2005@163.com</b></p>
            <p align="right" style="line-height:3px;">�绰:<b>021-62128864</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                ����:<b>021-62128854</b></p>
            <p align="right" style="line-height:15px;">��ַ���Ϻ��о���������·<b> 200 </b>�� ����<b> 9 </b>¥<b>A</b>��
        </td>
    </tr>
	<tr>
        <td height="2" colspan="6" bgcolor="#677789" style='width:37.4pt;border:none;border-bottom:solid windowtext 1.0pt;
  mso-border-bottom-alt:solid windowtext .5pt;padding:0cm 1.4pt 0cm 1.4pt'></td>
    </tr>
</table>

<table border="0" width="98%" height="117">
	<tr>
		<td height="111" width="388"><font  color="purple">
		<p style="line-height:18px;"><b>&nbsp;To:</b>&nbsp;&nbsp;&nbsp; ����������ҵ�ɷ����޹�˾</p>
		<p style="line-height:8px;"><b>&nbsp;Attn:</b>&nbsp;&nbsp;&nbsp;</p>
		<p style="line-height:8px;"><b>&nbsp;Cc:</b>&nbsp;&nbsp;&nbsp; <%=kmInfo[2]%></p>
		<p style="line-height:8px;"><b>&nbsp;Fax:</b>&nbsp;&nbsp; <%=kmInfo[1]%></font></td>
		
		<td height="111" width="460"><font  color="purple">
		<p style="line-height:18px;"><b>From:</b>&nbsp; �Ϻ���Ƚ�������޹�˾</p>
		<p style="line-height:8px;"><b>Date:</b>&nbsp;&nbsp; <%=Operate.toSqlDate()%></p>
		<p style="line-height:8px;"><b>Page:</b>&nbsp;&nbsp; 1</p>
		<p style="line-height:8px;">��ּ:&nbsp;&nbsp; ������</font></td>
	</tr>
	<tr> 
    <td height="1" colspan="6" bgcolor="#677789" style='width:37.4pt;border:none;border-bottom:solid windowtext 1.0pt;
  mso-border-bottom-alt:solid windowtext .5pt;padding:0cm 1.4pt 0cm 1.4pt'></td>
  </tr>
</table>
 
<p align="center"><b><font size="6">������</font></b></p>
<p>�� �� �� �ţ� <%=pf.getOrderNo()%></p>
<p>�� �� �� �ƣ� <%=pf.getCustomerName()%></p>
<p>�� �� ʱ �䣺 <%=pf.getDeliveryTime()==null?"15��":pf.getDeliveryTime()%></p></p>
<p>�� �� �� �㣺 <%=pf.getShippingAddress()==null?"�Ϻ���":pf.getShippingAddress()%></p>
<table  width="96%" id="table1" border=0 cellspacing=0 cellpadding=0>
	<tr >
		<td class="border1" width="5%" align="center" >���</td>
		<td class="borderrow1" width="20%" align="center">�������</td>
		<td class="borderrow1" width="15%" align="center">�������</td>
        <td class="borderrow1" width="5%"align="center">����</td>
        <td class="borderrow1" width="10%"align="center">�۸�$�����ۣ�</td>
		<td class="borderrow1" width="20%" >����</td>
		<td class="borderrow1" width="15%" >�������</td>

	</tr>
	
	<%
    int allNum =0;
    float allPrice=0F;
    for(int i=0;i<poFormPrintList.size();i++){
		PoForm pof=(PoForm)poFormPrintList.get(i);
        allNum += pof.getOrderNum();
        allPrice += pof.getPerQuote()*pof.getOrderNum();
	%>
	<tr >
		<td class="bordercol1" align="center"><%=(i+1)%></td>
		<td class="border2" align="left"><%=pof.getSkuCode()%></td>
		<td class="border2" align="left"><%=pof.getStuffNo()%></td>
        <td class="border2" align="center"><%=pof.getOrderNum()%></td>
        <td class="border2" align="center"><%=Operate.toFix(pof.getPerQuote(),2)%></td>
		<td class="border2" align="left"><%=pof.getModelCode()%></td>
		<td class="border2" align="left"><%=pof.getModelSerialNo()%></td>

	</tr>

	<%}%>
    <tr >
        <td class="bordercol1" align="center">�ϼ�</td>
        <td class="border2" align="left"></td>
        <td class="border2" align="left"></td>
        <td class="border2" align="center"><%=allNum%></td>
        <td class="border2" align="center"><%=Operate.toFix(allPrice, 2)%></td>
        <td class="border2" align="left"></td>
        <td class="border2" align="left"></td>

    </tr>
	
</table>
<p>��</p>
<p>��</p>
<p align="right"><font size="5">�Ϻ��죺 <%=userName%> </font><font size="4">&nbsp; </font>&nbsp;&nbsp;&nbsp;&nbsp;
</p>
 


  
</form>
</body>
<%
}catch(Exception e){
	e.printStackTrace();
}%>
</html>

