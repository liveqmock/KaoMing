<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.HashSet"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.dne.sie.maintenance.form.TsSystemCodeForm"%>
<%@ page import="com.dne.sie.common.tools.DicInit"%>
<%@ page import="com.dne.sie.common.tools.Operate"%>
<%@ page import="com.dne.sie.common.tools.CommonSearch"%>

<%@ page import="com.dne.sie.support.form.AccountItemForm"%>
<%@ page import="com.dne.sie.util.dao.DefaultDao"%>
<%@ page import="com.dne.sie.util.hibernate.AllDefaultDaoImp"%>


<html>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=gb2312">
<link href="<%= request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="<%= request.getContextPath()%>/css/styles2.css">
<title>newsis</title>

<%
		String flag =	request.getParameter("flag") ;
		boolean tag=true;
		try{
				out.println("------------SYS_CODE_MAP="+DicInit.SYS_CODE_MAP.size());
				if("sysTable".equals(flag)){
							AllDefaultDaoImp daoImp = new AllDefaultDaoImp();
							DefaultDao dao = daoImp;
							
								HashMap SYS_CODE_MAP = new HashMap();
								if(SYS_CODE_MAP.size()==0){
									HashSet typeSet = new HashSet();
									ArrayList tempList = (ArrayList)dao.listAll("from TsSystemCodeForm as tsc order by tsc.systemType,tsc.systemId");
									//System.out.println("------------tempList="+tempList.size());
									
									for(int i=0;i<tempList.size();i++){
										String systemType=((TsSystemCodeForm)tempList.get(i)).getSystemType().trim();
										typeSet.add(systemType);
									}
									Iterator itType = typeSet.iterator();
									
									TsSystemCodeForm tsc = new TsSystemCodeForm();
									while(itType.hasNext()){
										String type=(String)itType.next();
										ArrayList showList = new ArrayList();
										//System.out.println("------------type="+type);
										for(int i=0;i<tempList.size();i++){
											tsc=(TsSystemCodeForm)tempList.get(i);
											
											String systemType=tsc.getSystemType().trim();
											String[] temp = new String[2];
											//System.out.println("------xx------systemType="+systemType);
											if(tsc.getSystemType().equals(type)){
												temp[0]=tsc.getSystemCode();
												temp[1]=tsc.getSystemName();
												//System.out.println("------------temp[1]="+temp[1]);
												showList.add(temp);
											}
											
										}
										SYS_CODE_MAP.put(type,showList);
									}
								}
								DicInit.SYS_CODE_MAP = SYS_CODE_MAP;
							
				}else if("diy".equals(flag)){
					ArrayList xxList = new ArrayList();
					AllDefaultDaoImp daoImp = new AllDefaultDaoImp();
					ArrayList tempList = (ArrayList)daoImp.listAll("from AccountItemForm where   feeDate  >= '20120701' and  feeDate< '20120801' and payType like 'X_' order by accountId ");
					System.out.println("------------tempList="+tempList.size());
					
					for(int i=0;i<tempList.size();i++){
						AccountItemForm aif=(AccountItemForm)tempList.get(i);
						if(i<9) aif.setVoucherNo("00"+(i+1));
						else if(i<99) aif.setVoucherNo("0"+(i+1));
						else aif.setVoucherNo(""+(i+1));
						xxList.add(aif);
					}
					
					ArrayList tempList2 = (ArrayList)daoImp.listAll("from AccountItemForm where  feeDate  >= '20120701' and  feeDate< '20120801' and payType like 'Y_' order by accountId ");
					System.out.println("------------tempList2="+tempList2.size());
					
					for(int i=0;i<tempList2.size();i++){
						AccountItemForm aif=(AccountItemForm)tempList2.get(i);
						if(i<9) aif.setVoucherNo("00"+(i+1));
						else if(i<99) aif.setVoucherNo("0"+(i+1));
						else aif.setVoucherNo(""+(i+1));
						xxList.add(aif);
					}
					ArrayList tempList3 = (ArrayList)daoImp.listAll("from AccountItemForm where   feeDate  >= '20120801' and payType like 'X_' order by accountId ");
					System.out.println("------------tempList3="+tempList3.size());
					
					for(int i=0;i<tempList3.size();i++){
						AccountItemForm aif=(AccountItemForm)tempList3.get(i);
						if(i<9) aif.setVoucherNo("00"+(i+1));
						else if(i<99) aif.setVoucherNo("0"+(i+1));
						else aif.setVoucherNo(""+(i+1));
						xxList.add(aif);
					}
					
					ArrayList tempList4 = (ArrayList)daoImp.listAll("from AccountItemForm where  feeDate  >= '20120801' and payType like 'Y_' order by accountId ");
					System.out.println("------------tempList4="+tempList4.size());
					
					for(int i=0;i<tempList4.size();i++){
						AccountItemForm aif=(AccountItemForm)tempList4.get(i);
						if(i<9) aif.setVoucherNo("00"+(i+1));
						else if(i<99) aif.setVoucherNo("0"+(i+1));
						else aif.setVoucherNo(""+(i+1));
						xxList.add(aif);
					}
					daoImp.updateBatch(xxList);
					
				}else if("iris".equals(flag)){
					CommonSearch.IrisInfoList = null;
					CommonSearch.getInstance().getIrisInfoList();
					if(CommonSearch.IrisInfoList!=null){
						out.println("------------IrisInfoList.size="+CommonSearch.IrisInfoList.size());
					}
				}
				
				
		}catch( Exception e){
				tag=false;
				e.printStackTrace();
		}
%>
<body bgcolor="#ffffff" leftmargin="0" topmargin="0" rightmargin="0" bottommargin="0" marginwidth="0" marginheight="0">
<form method="post">
<input type="hidden" name="flag">
	<jsp:include page=  "/common/waitDiv.jsp" flush="true" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0"		class="content12">
		<tr>
			<td background="<%= request.getContextPath()%>/images/page_r3_c2.jpg"></td>
		</tr>
	</table>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="content12">
		
		<tr><td>
				<input name="sysTable" type="button" class="button6" value="初始字典表"	onclick="javascript:f_submit('sysTable')" /> 
				<input name="iris" type="button" class="button4" value="iris"	onclick="javascript:f_submit('iris')" /> 
				<input name="sysTable" type="button" class="button6" value="DIY"	onclick="javascript:f_submit('diy')" /> 
		</td></tr>
		

		
		
		<tr>
		<%if(flag!=null){%>
			<%if (tag) {%>
			<td><font color="blue"><%=flag%>初始成功！</font></td>
			<%} else {%>
			<td><font color="red"><%=flag%>初始失败！</font></td>
			<%}}%>
			
		</tr>
		</table>
		
</form>
</body>
</html>

<script LANGUAGE="JAVASCRIPT">

function f_submit(flag){
	eval("document.forms[0].flag.value='"+flag+"';");
	showWaitDiv(document.body.scrollHeight,document.body.scrollWidth);
	document.forms[0].submit();
}
</script>