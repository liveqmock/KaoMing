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
                        <td > 导入库存结存原始数据:
                          <input name="fileIn" type="file" class="form" size="32">
                        
                          <input name="uploadButton" type="button" value="库存数据导入" onclick="f_initial(0)">
                        </td>
                      </tr>
                  <tr><td>&nbsp;</td> </tr>
                  <tr class="tableback1"> 
                  <td>导入excel列依次为：零件名称、简称、规格、料号、单位、零件数量、零件属性(A,B)，<p> 其中零件名称、简称、料号、零件数量、零件属性不能为空</td>
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
                        <td > 导入零件基础信息:
                          <input name="fileIn" type="file" class="form" size="32">
                        
                          <input name="uploadButton" type="button" value="零件数据导入" onclick="f_initial(1)">
                        </td>
                      </tr>
                  <tr><td>&nbsp;</td> </tr>
                  <tr class="tableback1"> 
                  <td>导入excel列依次为：料号、零件名称、简称、规格、单位、进价、报价、备注 ，<p> 其中料号、零件名称、简称不能为空</td>
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
                        <td > 导入出入库记录原始数据:
                          <input name="fileIn" type="file" class="form" size="32">
                        
                          <input name="uploadButton" type="button" value="零件数据导入" onclick="f_initial(2)">
                        </td>
                      </tr>
                  <tr><td>&nbsp;</td> </tr>
                  <tr class="tableback1"> 
                  <td>导入excel列依次为：序号、日期、公司名称、零件名称、零件料号、数量、单位、备注 ，<p> 其中日期、客户、零件名称不能为空</td>
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
			alert("请选择xls格式的excel文件");
		}else{ 
			document.forms[flag].action="apacheUpload.do?method=excelUpload&flag="+flag;
    		document.forms[flag].uploadButton.disabled = true;
			document.forms[flag].submit();
		}
	}else{
		alert("请先选择导入文件");
	}
}



</SCRIPT>
