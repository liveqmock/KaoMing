<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ page import="java.util.ArrayList"%> 

<html>
<head>
<title>��̨ѯ��</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/autocomplete.css" /> 
<SCRIPT language=javascript src="js/commonSelect.js"></SCRIPT>
<script language=javascript src="js/common.js"></script>
<script type="text/javascript" src="js/prototype.js"></script>
<script type="text/javascript" src="js/autocomplete.js"></script>
<script language=javascript src="js/inputValidation.js"></script>
<script language=javascript src="js/checkValid.js"></script>
<script language=javascript src="js/popPartInfo.js"></script>

<%
	try{
		

%>
<SCRIPT>

	function f_submit(the_table){
		
		if(the_table.rows.length<=2){
			alert("���������һ����¼��");
			return false;
		}else{
			document.forms[0].submit();
		}
	}
	
	function add(){
 		addToTable(tblsales);
		//closeCustomer();  
		
	}
	
	

	var cur_row    = null;
	
	
	var trId=0;
	function addToTable(the_table){
		if(f_isNull(document.forms[0].stuffNo,'�Ϻ�')&&f_isNull(document.forms[0].partNum,'����')&&f_isNull(document.forms[0].skuCode,'�������')
    	&&f_isNull(document.forms[0].modelCode,'����') &&f_maxLength(document.forms[0].remark,'��ע',250)){
    	
    	if(document.forms[0].partNum.value==0){
				alert("��������Ϊ0");
				return;
			}
			tbls=tblsales.rows;
			var the_row,the_cell;
			the_row = cur_row==null?1:-1;
			var newrow=the_table.insertRow(the_row);
		        newrow.insertCell(0).innerHTML="<input type='checkbox' name='chk' value='"+trId+"'>";
		        newrow.insertCell(1).innerHTML=document.all.stuffNo.value+
		        	"<input type='hidden' name='stuffNoT' value='"+(document.all.stuffNo.value)+"'>";
						newrow.insertCell(2).innerHTML=document.all.skuCode.value+
							"<input type='hidden' name='skuCodeT' value='"+(document.all.skuCode.value)+"'>";
						newrow.insertCell(3).innerHTML=document.all.partNum.value+
							"<input type='hidden' name='partNumT' value='"+(document.all.partNum.value)+"'>";
						newrow.insertCell(4).innerHTML=document.all.skuUnit.value+
							"<input type='hidden' name='skuUnitT' value='"+(document.all.skuUnit.value)+"'>";
						newrow.insertCell(5).innerHTML=document.all.modelCode.value+
							"<input type='hidden' name='modelCodeT' value='"+(document.all.modelCode.value)+"'>";
						newrow.insertCell(6).innerHTML=document.all.modelSerialNo.value+
							"<input type='hidden' name='modelSerialNoT' value='"+(document.all.modelSerialNo.value)+"'>";
	
			trId++;
	
			
			document.forms[0].stuffNo.value="";
			document.forms[0].skuCode.value="";
			document.forms[0].partNum.value="";
			document.forms[0].skuUnit.value="";
			document.forms[0].modelCode.value="";
			document.forms[0].modelSerialNo.value="";
		
		}
	}
	
	
	function delrows(the_table){
	  var tbls=the_table.rows;
	  if(chk()!=''&&chk()!=null){
			var chkLength=chk().split(",");
			if(chkLength==undefined){
				alert("������ѡ��һ����¼��");
			}else{
				for(var i=1;i<tbls.length-1;i++){
					if (tbls[i].all("chk").checked==true){
						document.all("tblsales").deleteRow(i);
						i=i-1;
					}
				}
			
			}
    }else{
			alert("������ѡ��һ����¼��");
    }
	}


	function f_queryC(){
		var customerId=document.forms[0].customerId.value;
		if(customerId!=''){
			window.open("saleInfoAction.do?method=custPrice&customerId="+customerId,"","width=700,height=300,left=50,top=10,menubar=no,toolbar=no,resizable=yes,scrolling=yes,scrollbars=yes");
		}else{
			alert("����ѡ��ͻ�");
		}
	}
	function f_queryP(){
		var stuffNo=document.forms[0].stuffNo.value;
		if(stuffNo!=''){
			window.open("saleInfoAction.do?method=historyPrice&stuffNo="+stuffNo,"","width=700,height=300,left=50,top=10,menubar=no,toolbar=no,resizable=yes,scrolling=yes,scrollbars=yes");
		}else{
			alert("���������Ϻ�");
		}
	}


</script>
</head>
<body bgcolor="#ffffff" leftmargin="0" topmargin="0" rightmargin="0" bottommargin="0" marginwidth="0" marginheight="0" >
<html:form method="post" action="saleInfoAction.do?method=inquiryConfirmed">


<table align=center width="99%" height="96%" border="0" cellpadding="0" >

                <td valign="top"> <br>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">                   
                    <table width="100%" border="0" cellpadding="2" cellspacing="1" class="content12">
                      <tr> 
                        <td class="content12_bold"> <img src="googleImg/icon/i_arrow.gif" width="7" height="7" align="absmiddle"> 
                         	 �ͻ���Ϣ</td>
                      </tr>
					  <tr> 
                        <td height="1" background="googleImg/icon/i_line_1.gif"></td>
                      </tr>
                    </table>
                    <table width="100%" border="0" cellpadding="0" cellspacing="1" class="content12">
          			  <tr> 
                        <td width="120">�ͻ����ƣ�<font color='red'>*</font></td>
                        <input type="hidden" name=customerId>
                        <td ><input name="customerName" type="text" class="form" size="30" >
					        &nbsp;
                        	<a href="javascript:f_queryC()"><img src="googleImg/icon/search.gif" align="absmiddle" border="0"></a>
                        </td>
                        <td width="120">����ά�޵��ţ�</td>
                        <input type="hidden" name="repairNo">
                        <td colspan="3"><input name="serviceSheetNo" type="text" class="form" size="30" ></td>
                      </tr>
                      <tr>
                      	<td width="100">��ϵ�ˣ�</td>
                        <td width="200"><input name="linkman" type="text" class="form" size="16" maxlength="20" readonly > 
                        <td width="100">��ϵ�绰��</td>
                        <td width="150"><input name="phone" type="text" class="form" size="16" maxlength="20" readonly >
                        <td width="80">���У�</td>
                        <td width="200"><input name="cityName" type="text" class="form" size="16" readonly > </td> 
                      </tr>
                      <tr> 
                        <td width="120">���棺</td>
                        <td width="200"><input name="fax" type="text" class="form" size="16" readonly > </td>
                        <td width="100">�����ص㣺</td>
                        <td colspan="3"><input name="shippingAddress" type="text" class="form" size="60" maxlength="64"> </td>
                      </tr>
                	<tr> 
                        <td >��ע��</td>
                        <td colspan="5" valign="top"> 
                        	<textarea name="remark" cols="8" rows="2" class="form" style="width:80%"></textarea>
                        </td>
                      </tr>
                    </table>
                    <br>
                    <table width="100%" border="0" cellpadding="2" cellspacing="1" class="content12"  >
                      <tr> 
                        <td class="content12_bold"> <img src="googleImg/icon/i_arrow.gif" width="7" height="7" align="absmiddle"> 
                          �����Ϣ</td>
                      </tr>
					  <tr> 
                        <td height="1" background="googleImg/icon/i_line_1.gif"></td>
                      </tr>
                    </table>
                    <table width="100%" border="0" cellpadding="0" cellspacing="1" class="content12">
                      <tr> 
                        <td width="160">�Ϻţ�<font color='red'>*</font></td>
                        <td width="200"><input name="stuffNo" type="text" class="form" size="16" > &nbsp;
                        	<a href="javascript:f_queryP()"><img src="googleImg/icon/search.gif" align="absmiddle" border="0"></a></td>	
                        <td width="120">������ƣ�<font color='red'>*</font></td>
                        <td width="140"><input name="skuCode" type="text" class="form" size="16"  >&nbsp;
                        	<a href="javascript:showPartInfo('skuCode')"><img src="googleImg/icon/search.gif" align="absmiddle" border="0"></a></td>
                        <td width="100">��λ��</td>
                        <td width="400"><input name="skuUnit" type="text" class="form" size="16" readonly > </td>
                        <input name="shortCode" type="hidden"><input name="standard" type="hidden"><input name="deliveryPlaceTw" type="hidden" value="�Ϻ���">
                      </tr>
                	  <tr> 
                	  		<td width="160">������<font color='red'>*</font></td>
                        <td width="200"><input name="partNum" type="text" class="form" size="16" maxlength="20" onkeydown="f_onlynumber()"> 
                	  		<td width="160">���ͣ�<font color='red'>*</font></td>
                        <td width="200"><input name="modelCode" type="text" class="form" size="16" maxlength="20"> 
                        <td width="160">������룺</td>
                        <td width="200"><input name="modelSerialNo" type="text" class="form" size="16"> 
                        
                      </tr>
                      
                    </table>
                    <h3 class="underline"><p align="left">&nbsp;&nbsp;<input type="button" value="���" onClick="javascript:add()"></p></h3>
                    
                    <table width="100%" border="0" cellpadding="0" cellspacing="1" class="content12" id="tblsales" name="tblsales">
                      <tr> 
                        <td width="10" bgcolor="#CCCCCC"> <input type="checkbox" name="allbox" value="checkbox" onClick="checkAll(this)"> 
                        </td>
                        <td bgcolor="#CCCCCC"><strong>�Ϻ�</strong></td>
                        <td bgcolor="#CCCCCC"><strong>�������</strong></td>
                        <td bgcolor="#CCCCCC"><strong>����</strong></td>
                        <td bgcolor="#CCCCCC"><strong>��λ</strong></td>
                        <td bgcolor="#CCCCCC"><strong>����</strong></td>
                        <td bgcolor="#CCCCCC"><strong>�������</strong></td>
                      </tr>
                      <tr bgcolor="#CCCCCC"> 
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                      </tr>

                    </table></td>
              </tr>
              <tr>
                <td><table width="100%"  border="0" cellspacing="0" cellpadding="0">
                    <tr><td height="2" colspan="9" bgcolor="#ffffff"></td></tr>
                      <tr><td height="1" colspan="9"  bgcolor="#677789"></td></tr>
                    <tr>
                        <td><span class="content_yellow"> 
                          <input name="btnSave" type="button"  onClick="javascript:f_submit(tblsales)" value="����">
                          <input name="btnDelete" type="button"  value="ɾ��" onclick='javascript:delrows(tblsales)' >
                          </span></td>
                    </tr>
                </table></td>
              </tr>
            </table></td>
          </tr>
        </table>
      </div>
      </td>
  </tr>
</table>
</html:form>

<script>
new AutoTip.AutoComplete("stuffNo", function() {
	 return "partInfoAction.do?method=getPartInfo&inputValue=" + escape(this.text.value);
});
	
new AutoTip.AutoComplete("customerName", function() {
	 return "customerInfoAction.do?method=getCustInfo&inputValue=" + escape(this.text.value);
});

new AutoTip.AutoComplete("serviceSheetNo", function() {
	 return "saleInfoAction.do?method=getRepairInfo&inputValue=" + (this.text.value);
});
</script>
</body>


<%
}catch(Exception e){
	e.printStackTrace();
}%>

</html>