<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>syscode</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/styles.css" rel="stylesheet" type="text/css" />

</head>
<%try{%>
<body>

<input name="ids" type="hidden">

<form name="fileUpload" action="apacheUpload.do?method=excelUpload" method="post" ENCTYPE="multipart/form-data">
<table align=center width="99%">
				
                 <tr class="tableback2"> 
                        <td > ��������ԭʼ����:
                          <input name="fileIn" type="file" class="form" size="32">
                        
                          <input name="uploadButton" type="button" value="������ݵ���" onclick="f_initial(0)">
                        </td>
                      </tr>
                  <tr><td>&nbsp;</td> </tr>
                  <tr class="tableback1"> 
                  <td>����excel������Ϊ��������ơ���ơ�����Ϻš���λ������������������(A,B)��<p> ����������ơ���ơ��Ϻš����������������Բ���Ϊ��</td>
                  <tr>     
        
        </table>
    </form>
    
    <br>
                    <table width="100%" border="0" cellpadding="2" cellspacing="1" class="content12"  >
                   
					  <tr> 
                        <td height="1" background="googleImg/icon/i_line_1.gif"></td>
                      </tr>
                    </table>
    <br>
<form name="fileUpload" action="apacheUpload.do?method=excelUpload" method="post" ENCTYPE="multipart/form-data">
<table align=center width="99%">
				
                 <tr class="tableback2"> 
                        <td > �������������Ϣ:
                          <input name="fileIn" type="file" class="form" size="32">
                        
                          <input name="uploadButton" type="button" value="������ݵ���" onclick="f_initial(1)">
                        </td>
                      </tr>
                  <tr><td>&nbsp;</td> </tr>
                  <tr class="tableback1"> 
                  <td>����excel������Ϊ���Ϻš�������ơ���ơ���񡢵�λ�����ۡ����ۡ���ע ��<p> �����Ϻš�������ơ���Ʋ���Ϊ��</td>
                  <tr>     
        
        </table>
    </form>
    <br>
                    <table width="100%" border="0" cellpadding="2" cellspacing="1" class="content12"  >
                   
					  <tr> 
                        <td height="1" background="googleImg/icon/i_line_1.gif"></td>
                      </tr>
                    </table>
    <br>
<form name="fileUpload" action="apacheUpload.do?method=excelUpload" method="post" ENCTYPE="multipart/form-data">
<table align=center width="99%">
				
                 <tr class="tableback2"> 
                        <td > ���������¼ԭʼ����:
                          <input name="fileIn" type="file" class="form" size="32">
                        
                          <input name="uploadButton" type="button" value="������ݵ���" onclick="f_initial(2)">
                        </td>
                      </tr>
                  <tr><td>&nbsp;</td> </tr>
                  <tr class="tableback1"> 
                  <td>����excel������Ϊ����š����ڡ���˾���ơ�������ơ�����Ϻš���������λ����ע ��<p> �������ڡ��ͻ���������Ʋ���Ϊ��</td>
                  <tr>     
        
        </table>
    </form>
</body>
<%}catch(Exception e){
	e.printStackTrace();
}%>
</html>

<SCRIPT >
function f_initial(flag){
	var filePath=document.forms[flag].fileIn.value;
	
	if(filePath!=null&&filePath!=''){
		var fileType=filePath.substring(filePath.lastIndexOf('.'));
		if(fileType.toLowerCase()!='.xls'){ 
			alert("��ѡ��xls��ʽ��excel�ļ�");
		}else{ 
			document.forms[flag].action="apacheUpload.do?method=excelUpload&flag="+flag;
    		document.forms[flag].uploadButton.disabled = true;
			document.forms[flag].submit();
		}
	}else{
		alert("����ѡ�����ļ�");
	}
}



</SCRIPT>
