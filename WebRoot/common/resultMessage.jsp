<%@ page contentType="text/html;charset=GBK"%>
<html>

<%	
	String tag = (String)request.getAttribute("tag");
	String flag = (String)request.getAttribute("businessFlag");
	String custommessage = (String)request.getAttribute("custommessage");
	String tempData=(String)request.getAttribute("tempData")==null?"":(String)request.getAttribute("tempData");
	
	
	//System.out.println("**************custommessage=" + custommessage);	
%>
<head>
<script language="javascript"> 

var tag='<%=tag%>';
var flag='<%=flag%>';
var message="";
var custommessage = '<%=custommessage==null?"":custommessage%>';
var myurl="";
var closeFlag="";
var buttonClose="�ر�";
var buttonReturn="����";
var buttonConfirm="ȷ��";

var childWindowType="";
function doUnLoad(){
	if(!window.opener==undefined && window.opener!=null)
	window.opener.childWindowUnloadNotification(childWindowType);
}

function myOnload(){
    if(tag=="-1"||tag=="null"){
    		message="����ʧ��";
    		switch(flag){
    		
			
				case "userAdd" :	//�û�ע��
				    <%if(tempData.equals("idRepeat")){%>
					message="�û�id�Ѿ���ʹ��";
					document.form1.mybutton.value=buttonClose;
					closeFlag="1";
				    <%}%>
					break;
					
				case "inquiryConfirmed" : 	// ѯ��ȷ��
					message="����ʧ��";
					myurl="window.location='saleInfoAction.do?method=saleRegistert'";
					document.form1.mybutton.value=buttonReturn;		
					break;
				
				case "saveCustomer" : 	// �ͻ�����Ϣ ����or�޸�
					message="����ʧ��";
					myurl="window.location='customerInfoAction.do?method=customerList'";
					document.form1.mybutton.value=buttonConfirm;		
					break;
					
				case "saveMachineTool" : 	
					message="����ʧ��";
					myurl="window.location='machineToolAction.do?method=machineToolList'";
					document.form1.mybutton.value=buttonConfirm;		
					break;
					
				case "saveEmployee" : 	// Ա������Ϣ ����or�޸�
					message="����ʧ��";
					myurl="window.location='employeeInfoAction.do?method=employeeList'";
					document.form1.mybutton.value=buttonConfirm;		
					break;
				case "saveFactory" : 	// ���̱���Ϣ ����or�޸�
					message="����ʧ��";
					myurl="window.location='factoryInfoAction.do?method=factoryList'";
					document.form1.mybutton.value=buttonConfirm;		
					break;
					
				case "saleAskSave" : 	
					myurl="window.location='saleInfoAction.do?method=saleAskConfirmList'";
					document.form1.mybutton.value=buttonConfirm;		
					break;	
					
				case "saleCheckSave" : 	
					myurl="window.location='saleInfoAction.do?method=inquiryDetail&flag=check&saleNo=<%=tempData%>'";
					document.form1.mybutton.value=buttonConfirm;		
					break;	
					
				case "saleInfoCancel" : 	
					myurl="window.location='saleInfoAction.do?method=saleInfoList'";
					document.form1.mybutton.value=buttonConfirm;		
					break;	
					
			case "partCancel" : 	
					message="ȡ��ʧ��";
					<%if(!"".equals(tempData)){%>
					myurl="window.location='saleInfoAction.do?method=saleInfoDetail&&saleNo=<%=tempData%>'";
					<%}%>
					document.form1.mybutton.value=buttonConfirm;		
					break;	
					
			case "sendPo" :
					myurl="window.location='partPoAction.do?method=planList'";
					document.form1.mybutton.value=buttonConfirm;		
					break;	
						
			case "manualPlanAdd" :
					myurl="window.location='partPoAction.do?method=manualPlanList'";
					document.form1.mybutton.value=buttonConfirm;		
					break;	
					
			case "poFormPrintNull" : 	
					message="�ö���δ�������޷���ӡ";
					myurl="window.location='partPoAction.do?method=poList&queryFlag=N'";
					document.form1.mybutton.value=buttonConfirm;		
					break;	
					
			case "orderCancel" : 	
					message="����ȡ��ʧ��";
					myurl="window.location='partPoAction.do?method=poList&queryFlag=N'";
					document.form1.mybutton.value=buttonConfirm;		
					break;	
					
			case "orderInReceive" : 	
					myurl="window.location='stockInOperateAction.do?method=orderInList'";
					document.form1.mybutton.value=buttonConfirm;		
					break;	
					
			case "stockOutConfirm" : 	
					myurl="window.location='requestOperateAction.do?method=saleOutList'";
					document.form1.mybutton.value=buttonConfirm;		
					break;	
					
			case "manualAllcate" : 	
					myurl="window.location='handAllocateAction.do?method=requestList'";
					document.form1.mybutton.value=buttonConfirm;		
					break;	
					
			case "consignApprove" : 	
					myurl="window.location='asnListAction.do?method=consignApproveList&queryFlag=N'";
					document.form1.mybutton.value=buttonConfirm;		
					break;	
					
			case "stockAdjustOutConfirm" : 	
					myurl="window.location='stockOutOperateAction.do?method=stockAdjustOutList'";
					document.form1.mybutton.value=buttonConfirm;		
					break;	
			
			case "takeExcel" :	//�̵��ʼ��ӡ
				message="û���������";
				myurl="location='stockTakeAction.do?method=takeInit'";
				document.form1.mybutton.value=buttonReturn;
				break;
						
			case "takeStart" :	//�̵������ظ�
				message="����ά��վ�Ѿ���һ���̵����̣�����ɻ�ȡ��֮���ٿ�ʼ�µ��̵�";
				myurl="window.location='stockTakeAction.do?method=takeInit'";
				document.form1.mybutton.value=buttonReturn;
				break;		
				
			case "takeLock" :	//�̵������ظ�
				message="������зǿ���״̬����������������ٿ�ʼ�̵�";
				myurl="window.location='stockTakeAction.do?method=takeInit'";
				document.form1.mybutton.value=buttonReturn;
				break;
			
			case "shipConfirm" :	
				message="����ʧ��";
				myurl="window.location='asnAction.do?method=asnReadyList'";
				document.form1.mybutton.value=buttonReturn;
				break;
			
			case "consignAgain" :	
				myurl="window.location='asnListAction.do?method=asnResultList&queryFlag=N'";
				document.form1.mybutton.value=buttonReturn;
				break;					
			
			case "insertAccount" : 	
					message="����ʧ��";
					myurl="window.location='accountItemAction.do?method=accountInit'";
					document.form1.mybutton.value=buttonConfirm;
					break;
			case "updateAccount" : 	
					message="�޸�ʧ��";
					myurl="window.location='accountItemAction.do?method=accountList'";
					document.form1.mybutton.value=buttonConfirm;
					break;
					
			case "deleteAccount" : 	
					message="ɾ��ʧ��";
					myurl="window.location='accountItemAction.do?method=accountList'";
					document.form1.mybutton.value=buttonConfirm;
					break;
										
			case "recountStat" : 	
					message="����ͳ��ʧ��";
					myurl="window.location='accountStatisticsAction.do?method=statList'";
					document.form1.mybutton.value=buttonConfirm;
					break;	
					
			case "stockBackOperate":
					message="�ؿ�ʧ��";
					myurl="window.location='stockBackAction.do?method=stockBackList&queryFlag=N'";
					document.form1.mybutton.value=buttonConfirm;
					break;	
					
			case "repairDispatch":
					message="�ɹ�ʧ��";
					myurl="window.opener.location='repairAction.do?method=repairDispatchList&queryFlag=N'";
					document.form1.mybutton.value=buttonConfirm;
					closeFlag="1";
					break;	
					
				case "repairReturnEnd":
					message="����ʧ��";
					myurl="window.opener.location='repairAction.do?method=repairReturnList&queryFlag=N'";
					document.form1.mybutton.value=buttonConfirm;
					closeFlag="1";
					break;	
					
				case "repairComplete":
					message="�޸�ʧ��";
					myurl="window.opener.location='repairAction.do?method=repairCompleteList&queryFlag=N'";
					//myurl="window.opener.location.reload()";
					document.form1.mybutton.value=buttonConfirm;
					closeFlag="1";
					break;	
				
				case "receiveAdd" : 	
					message="�Ǽ�ʧ��";
					myurl="window.location='repairAction.do?method=receiveInit'";
					document.form1.mybutton.value=buttonConfirm;
					break;
				
				case "turningAdd" : 	
					message="�Ǽ�ʧ��";
					myurl="window.location='repairTurningAction.do?method=jwReceiveInit'";
					document.form1.mybutton.value=buttonConfirm;
					break;	
					
						
				case "RR90Err" : 	
					message="<%=tempData%>";
					myurl="window.location='repairAction.do?method=receiveInit'";
					document.form1.mybutton.value=buttonConfirm;
					break;
					
			default :
				document.form1.mybutton.value=buttonClose;
				closeFlag="1";

			}
    	
    }else if(tag==-2){
        switch(flag){    		
			
				case "reserveAdd" :	//ԤԼ�����
					message=custommessage;
					myurl="window.location='reserveAction.do?method=initAdd'";
					document.form1.mybutton.value=buttonReturn;
					break;
		   
	
			}
    }else{
    		message="�����ɹ�";
				switch(flag){
		



				case "userAdd" :	//�û�����
					message="�����û��ɹ�";
					myurl="window.opener.location='userAction.do?method=userList'";
					//myurl="window.opener.location.reload()";
					document.form1.mybutton.value=buttonClose;
					closeFlag="1";
					break;
				case "userModify" :	//�û��޸�
					message="�޸��û��ɹ�";
					//myurl="window.opener.location='userAction.do?method=userList'";
					myurl="window.opener.location.reload()";
					document.form1.mybutton.value=buttonClose;	
					closeFlag="1";
					break;
				case "userDelete" : 	//�û�ɾ��
					message="ɾ���ɹ�";
					myurl="window.location='userAction.do?method=userList'";
					document.form1.mybutton.value=buttonReturn;		
					break;
				case "userRenew" :	//�û��ָ�
					message="�ָ��ɹ�";
					myurl="window.location='userAction.do?method=userList'";
					document.form1.mybutton.value=buttonReturn;
					break;
				case "userMove" : 	//�û��Ƴ�
					message="�Ƴ��ɹ�";
					myurl="window.opener.location.reload()";
					document.form1.mybutton.value=buttonReturn;	
					closeFlag="1";	
					break;
				case "roleAdd" :	//Ȩ������
					message="����Ȩ�޳ɹ�";
					myurl="window.opener.location='roleAction.do?method=roleList'";
					document.form1.mybutton.value=buttonClose;
					closeFlag="1";
					break;
				case "roleModify" :	//Ȩ���޸�
					message="�޸�Ȩ�޳ɹ�";
					//myurl="window.opener.location='roleAction.do?method=roleList'";
					myurl="window.opener.location.reload()";
					document.form1.mybutton.value=buttonClose;	
					closeFlag="1";
					break;
				case "roleDelete" : 	//Ȩ��ɾ��
					message="ɾ���ɹ�";
					myurl="location='roleAction.do?method=roleList'";
					document.form1.mybutton.value=buttonReturn;		
					break;
			
			
				case "inquiryConfirmed" : 	// ѯ��ȷ��
						message="�����ɹ�";
						myurl="window.location='saleInfoAction.do?method=inquiryDetail&saleNo=<%=tempData%>'";
						document.form1.mybutton.value=buttonConfirm;
						break;
						
				case "saveCustomer" : 	// �ͻ�����or�޸�ȷ��
						message="�����ɹ�";
						myurl="window.location='customerInfoAction.do?method=customerList'";
						document.form1.mybutton.value=buttonConfirm;
						break;
						
						
				case "saveMachineTool" : 	
						message="�����ɹ�";
						myurl="window.location='machineToolAction.do?method=machineToolList'";
						document.form1.mybutton.value=buttonConfirm;		
						break;
						
				case "saveBugInfo" : 	
						message="�����ɹ�";
						myurl="window.location='bugInfoAction.do?method=bugInfoList&status=init'";
						document.form1.mybutton.value=buttonConfirm;		
						break;
							
				case "saveEmployee" : 	// Ա������or�޸�ȷ��
						message="�����ɹ�";
						myurl="window.location='employeeInfoAction.do?method=employeeList'";
						document.form1.mybutton.value=buttonConfirm;
						break;
						
				case "deleteCustomer" : 	// �ͻ�ɾ��ȷ��
						message="ɾ���ɹ�";
						myurl="window.location='customerInfoAction.do?method=customerList'";
						document.form1.mybutton.value=buttonConfirm;
						break;
				case "deleteEmployee" : 	// Ա��ɾ��ȷ��
						message="ɾ���ɹ�";
						myurl="window.location='employeeInfoAction.do?method=employeeList'";
						document.form1.mybutton.value=buttonConfirm;
						break;
				case "saveFactory" : 	// ���̲���or�޸�ȷ��
						message="�����ɹ�";
						myurl="window.location='factoryInfoAction.do?method=factoryList'";
						document.form1.mybutton.value=buttonConfirm;
						break;
				case "deleteFactory" : 	// ����ɾ��ȷ��
						message="ɾ���ɹ�";
						myurl="window.location='factoryInfoAction.do?method=factoryList'";
						document.form1.mybutton.value=buttonConfirm;
						break;					
								
				case "saleAskSave" : 	
						message="�ݴ����";
						myurl="window.location='saleInfoAction.do?method=inquiryDetail&flag=list&saleNo=<%=tempData%>'";
						document.form1.mybutton.value=buttonConfirm;		
						break;			
						
				case "saleAskCancel" : 	
						message="ȡ���ɹ�";
						myurl="window.location='saleInfoAction.do?method=saleAskConfirmList'";
						document.form1.mybutton.value=buttonConfirm;		
						break;			
						
				case "saleCheckList" : 	
						message="�������";
						myurl="window.location='saleInfoAction.do?method=inquiryDetail&flag=check&saleNo=<%=tempData%>'";
						document.form1.mybutton.value=buttonConfirm;		
						break;			
						
				case "saleCheckSave" : 	
						myurl="window.location='saleInfoAction.do?method=inquiryDetail&flag=check&saleNo=<%=tempData%>'";
						document.form1.mybutton.value=buttonConfirm;		
						break;	
					
				case "saleInfoCancel" : 	
						myurl="window.location='saleInfoAction.do?method=saleInfoList'";
						document.form1.mybutton.value=buttonConfirm;		
						break;	
						
				case "saleCheckConfirm" : 	
						myurl="window.location='saleInfoAction.do?method=saleInfoList&saleNo=<%=tempData%>'";
						document.form1.mybutton.value=buttonConfirm;		
						break;	
						
				case "saveEnd" : 	
						myurl="window.location='saleInfoAction.do?method=inquiryDetail&flag=review&saleNo=<%=tempData%>'";
						document.form1.mybutton.value=buttonConfirm;		
						break;	
				case "confirmEnd" : 	
						myurl="window.location='saleInfoAction.do?method=inquiryDetail&flag=review&saleNo=<%=tempData%>'";
						document.form1.mybutton.value=buttonConfirm;		
						break;		
				case "partCancel" : 	
						message="ȡ�����";
						myurl="window.location='saleInfoAction.do?method=saleInfoDetail&&saleNo=<%=tempData%>'";
						document.form1.mybutton.value=buttonConfirm;		
						break;	
						
				case "sendPo" : 	
						<%if(!tempData.equals("")){%>
						window.open('partPoAction.do?method=poFormPrint&orderNo=<%=tempData%>');
						message="������ȷ�ϣ��뱣���ӡ����";
						<%}%>
						myurl="window.location='partPoAction.do?method=planList'";
						document.form1.mybutton.value=buttonConfirm;		
						break;	
							
				case "manualPlanAdd" : 	
						<%if(!tempData.equals("")){%>
						window.open('partPoAction.do?method=poFormPrint&orderNo=<%=tempData%>');
						message="������ȷ�ϣ��뱣���ӡ����";
						<%}%>
						myurl="window.location='partPoAction.do?method=manualPlanList'";
						document.form1.mybutton.value=buttonConfirm;		
						break;	
						
				case "orderCancel" : 	
						message="����ȡ�����";
						myurl="window.location='partPoAction.do?method=poList&queryFlag=N'";
						document.form1.mybutton.value=buttonConfirm;		
						break;	
						
				case "orderInReceive" : 	
						myurl="window.location='stockInOperateAction.do?method=orderInList'";
						document.form1.mybutton.value=buttonConfirm;		
						break;	
							
				case "stockOutConfirm" : 	
						myurl="window.location='requestOperateAction.do?method=saleOutList'";
						document.form1.mybutton.value=buttonConfirm;		
						break;	
						
				case "manualAllcate" : 	
						message="�ֹ��������";
						myurl="window.location='handAllocateAction.do?method=requestList'";
						document.form1.mybutton.value=buttonConfirm;		
						break;	
						
				case "consignApprove" : 	
						message="�����������";
						myurl="window.location='asnListAction.do?method=consignApproveList&queryFlag=N'";
						document.form1.mybutton.value=buttonConfirm;		
						break;	
						
				case "stockAdjustOutConfirm" : 	
						message="�������������";
						myurl="window.location='stockOutOperateAction.do?method=stockAdjustOutList'";
						document.form1.mybutton.value=buttonConfirm;		
						break;	
				case "stockAdjustTaxOutConfirm" : 	
						message="˰����������";
						myurl="window.location='stockOutOperateAction.do?method=stockTaxAdjustOutList'";
						document.form1.mybutton.value=buttonConfirm;		
						break;	
				
				case "takeReport" : 	//�̵㱨��
					message="�̵�ɹ�";
					myurl="location='stockReportAction.do?method=takeReport&takeId=<%=tempData%>'";
					document.form1.mybutton.value=buttonReturn;
					break;
				
				case "takeConfirm" : 	//�̵�ȷ��
					message="һ���̵�ɹ�";
					myurl="location='stockTakeAction.do?method=takeInit'";
					document.form1.mybutton.value=buttonReturn;
					break;	
				
				case "takeCancel" : 	//�̵�ȡ��
					message="�̵���ȡ��";
					myurl="location='stockTakeAction.do?method=takeInit'";
					document.form1.mybutton.value=buttonReturn;
					break;
				
				case "shipConfirm" :	
					message="�����ɹ�";
					myurl="window.location='asnAction.do?method=asnReadyList'";
					document.form1.mybutton.value=buttonReturn;
					break;					
									
				case "consignAgain" :	
					myurl="window.location='asnListAction.do?method=asnResultList&queryFlag=N'";
					document.form1.mybutton.value=buttonReturn;
					break;					
								
				case "insertAccount" : 	
						message="�����ɹ�";
						myurl="window.location='accountItemAction.do?method=accountInit'";
						document.form1.mybutton.value=buttonConfirm;
						break;
				case "updateAccount" : 	
						message="�޸ĳɹ�";
						myurl="window.location='accountItemAction.do?method=accountList'";
						document.form1.mybutton.value=buttonConfirm;
						break;
						
				case "deleteAccount" : 	
						message="ɾ���ɹ�";
						myurl="window.location='accountItemAction.do?method=accountList'";
						document.form1.mybutton.value=buttonConfirm;
						break;
							
				case "recountStat" : 	
						message="����ͳ�����";
						myurl="window.location='accountStatisticsAction.do?method=statList'";
						document.form1.mybutton.value=buttonConfirm;
						break;	
					
				case "deleteAccountStat" : 	
						message="ɾ�����";
						myurl="window.location='accountStatisticsAction.do?method=statList'";
						document.form1.mybutton.value=buttonConfirm;
						break;	
				
				case "receiveAdd" : 	
						message="�Ǽǳɹ�";
						myurl="window.location='repairAction.do?method=receiveInit'";
						document.form1.mybutton.value=buttonConfirm;
						break;
				
				case "turningAdd" : 	
						message="�Ǽǳɹ�";
						myurl="window.location='repairTurningAction.do?method=jwReceiveInit'";
						document.form1.mybutton.value=buttonConfirm;
						break;	
					
				case "toolsInOperate":
						message="����ɹ�";
						myurl="window.location='stockToolsAction.do?method=stockToolsList'";
						document.form1.mybutton.value=buttonConfirm;
						break;
						
				case "loanPartOutList":
						message="����ɹ�";
						myurl="window.location='repairOperateAction.do?method=loanPartOutList'";
						document.form1.mybutton.value=buttonConfirm;
						break;
						
				case "loanToolOutList":
						message="����ɹ�";
						myurl="window.location='repairOperateAction.do?method=loanToolOutList'";
						document.form1.mybutton.value=buttonConfirm;
						break;
						
						
				case "transferSale" :	
					message="ת���۳ɹ�";
					myurl="window.opener.location='repairAction.do?method=repairList'";
					//myurl="window.opener.location.reload()";
					document.form1.mybutton.value=buttonClose;	
					closeFlag="1";
					break;		
					
					
				case "iwPartApprove":
						message="�������";
						myurl="window.location='salePartsApproveAction.do?method=iwPartsList'";
						document.form1.mybutton.value=buttonConfirm;
						break;
						
				case "stockBackOperate":
					message="�ؿ����";
					myurl="window.location='stockBackAction.do?method=stockBackList&queryFlag=N'";
					document.form1.mybutton.value=buttonConfirm;
					break;
					
				
				case "repairDispatch":
					message="�ɹ����";
					myurl="window.opener.location='repairAction.do?method=repairDispatchList&queryFlag=N'";
					document.form1.mybutton.value=buttonConfirm;
					closeFlag="1";
					break;	
					
				case "repairReturnEnd":
					message="�������";
					myurl="window.opener.location='repairAction.do?method=repairReturnList&queryFlag=N'";
					document.form1.mybutton.value=buttonConfirm;
					closeFlag="1";
					break;	
					
				case "repairComplete":
					message="�޸����";
					myurl="window.opener.location='repairAction.do?method=repairCompleteList&queryFlag=N'";
					//myurl="window.opener.location.reload()";
					document.form1.mybutton.value=buttonConfirm;
					closeFlag="1";
					break;	
					
				
				case "turningList":
					message="�ɹ����";
					myurl="window.opener.location='repairTurningAction.do?method=<%=tempData%>&queryFlag=N'";
					document.form1.mybutton.value=buttonConfirm;
					closeFlag="1";
					break;	
					
						
				default :
					document.form1.mybutton.value=buttonClose;
					closeFlag="1";
			}
    }
}


function doLocation(url,cFlag){
    if(url!=null&&url!=''){
			eval(url);
    }
    if(cFlag=='1') self.close();
    else if(cFlag=='2') history.back();
}

function f_write(){
		messageView.innerText=message;
}

</script>
<title>newsis</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
</head>

<body leftmargin="0" topmargin="0" rightmargin="0" bottommargin="0" marginwidth="0" marginheight="0"  onLoad="myOnload();f_write();">
<form action="" method="post" name="form1">
<table width="100%" height="100%" border="0" align="center" cellpadding="10" cellspacing="10" bgcolor="#B6C4E3">
  <tr >
    <td align="center" bgcolor="#FFFFFF" ><img src="images/<%="-1".equals(tag)?"i_error.gif":"p_write.gif"%>"><br>
      <br>
      <table width="50%" border="0" cellspacing="2" cellpadding="4" class="content12">
        <tr> 
          <td height="1" background="images/i_line.gif"></td>
        </tr>
		<tr> 
          <td class="tableback1" ><div id="messageView"></div></td>
        </tr>
        <tr> 
          <td height="1" background="images/i_line.gif"></td>
        </tr>
      </table>
      <br>
      <input name="mybutton" type="button" class="button2" value="" onClick="doLocation(myurl,closeFlag)" />
       </td>
  </tr>
</table>
</form>
</body>

</html>