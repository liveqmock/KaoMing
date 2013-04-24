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
var buttonClose="关闭";
var buttonReturn="返回";
var buttonConfirm="确定";

var childWindowType="";
function doUnLoad(){
	if(!window.opener==undefined && window.opener!=null)
	window.opener.childWindowUnloadNotification(childWindowType);
}

function myOnload(){
    if(tag=="-1"||tag=="null"){
    		message="操作失败";
    		switch(flag){
    		
			
				case "userAdd" :	//用户注册
				    <%if(tempData.equals("idRepeat")){%>
					message="用户id已经被使用";
					document.form1.mybutton.value=buttonClose;
					closeFlag="1";
				    <%}%>
					break;
					
				case "inquiryConfirmed" : 	// 询价确认
					message="保存失败";
					myurl="window.location='saleInfoAction.do?method=saleRegistert'";
					document.form1.mybutton.value=buttonReturn;		
					break;
				
				case "saveCustomer" : 	// 客户表信息 插入or修改
					message="操作失败";
					myurl="window.location='customerInfoAction.do?method=customerList'";
					document.form1.mybutton.value=buttonConfirm;		
					break;
					
				case "saveMachineTool" : 	
					message="操作失败";
					myurl="window.location='machineToolAction.do?method=machineToolList'";
					document.form1.mybutton.value=buttonConfirm;		
					break;
					
				case "saveEmployee" : 	// 员工表信息 插入or修改
					message="操作失败";
					myurl="window.location='employeeInfoAction.do?method=employeeList'";
					document.form1.mybutton.value=buttonConfirm;		
					break;
				case "saveFactory" : 	// 厂商表信息 插入or修改
					message="操作失败";
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
					message="取消失败";
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
					message="该订单未发货，无法打印";
					myurl="window.location='partPoAction.do?method=poList&queryFlag=N'";
					document.form1.mybutton.value=buttonConfirm;		
					break;	
					
			case "orderCancel" : 	
					message="订单取消失败";
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
			
			case "takeExcel" :	//盘点初始打印
				message="没有零件数据";
				myurl="location='stockTakeAction.do?method=takeInit'";
				document.form1.mybutton.value=buttonReturn;
				break;
						
			case "takeStart" :	//盘点流程重复
				message="您的维修站已经有一个盘点流程，请完成或取消之后再开始新的盘点";
				myurl="window.location='stockTakeAction.do?method=takeInit'";
				document.form1.mybutton.value=buttonReturn;
				break;		
				
			case "takeLock" :	//盘点流程重复
				message="库存中有非可用状态的零件，请整理完再开始盘点";
				myurl="window.location='stockTakeAction.do?method=takeInit'";
				document.form1.mybutton.value=buttonReturn;
				break;
			
			case "shipConfirm" :	
				message="出货失败";
				myurl="window.location='asnAction.do?method=asnReadyList'";
				document.form1.mybutton.value=buttonReturn;
				break;
			
			case "consignAgain" :	
				myurl="window.location='asnListAction.do?method=asnResultList&queryFlag=N'";
				document.form1.mybutton.value=buttonReturn;
				break;					
			
			case "insertAccount" : 	
					message="新增失败";
					myurl="window.location='accountItemAction.do?method=accountInit'";
					document.form1.mybutton.value=buttonConfirm;
					break;
			case "updateAccount" : 	
					message="修改失败";
					myurl="window.location='accountItemAction.do?method=accountList'";
					document.form1.mybutton.value=buttonConfirm;
					break;
					
			case "deleteAccount" : 	
					message="删除失败";
					myurl="window.location='accountItemAction.do?method=accountList'";
					document.form1.mybutton.value=buttonConfirm;
					break;
										
			case "recountStat" : 	
					message="重新统计失败";
					myurl="window.location='accountStatisticsAction.do?method=statList'";
					document.form1.mybutton.value=buttonConfirm;
					break;	
					
			case "stockBackOperate":
					message="回库失败";
					myurl="window.location='stockBackAction.do?method=stockBackList&queryFlag=N'";
					document.form1.mybutton.value=buttonConfirm;
					break;	
					
			case "repairDispatch":
					message="派工失败";
					myurl="window.opener.location='repairAction.do?method=repairDispatchList&queryFlag=N'";
					document.form1.mybutton.value=buttonConfirm;
					closeFlag="1";
					break;	
					
				case "repairReturnEnd":
					message="返还失败";
					myurl="window.opener.location='repairAction.do?method=repairReturnList&queryFlag=N'";
					document.form1.mybutton.value=buttonConfirm;
					closeFlag="1";
					break;	
					
				case "repairComplete":
					message="修复失败";
					myurl="window.opener.location='repairAction.do?method=repairCompleteList&queryFlag=N'";
					//myurl="window.opener.location.reload()";
					document.form1.mybutton.value=buttonConfirm;
					closeFlag="1";
					break;	
				
				case "receiveAdd" : 	
					message="登记失败";
					myurl="window.location='repairAction.do?method=receiveInit'";
					document.form1.mybutton.value=buttonConfirm;
					break;
				
				case "turningAdd" : 	
					message="登记失败";
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
			
				case "reserveAdd" :	//预约单添加
					message=custommessage;
					myurl="window.location='reserveAction.do?method=initAdd'";
					document.form1.mybutton.value=buttonReturn;
					break;
		   
	
			}
    }else{
    		message="操作成功";
				switch(flag){
		



				case "userAdd" :	//用户新增
					message="新增用户成功";
					myurl="window.opener.location='userAction.do?method=userList'";
					//myurl="window.opener.location.reload()";
					document.form1.mybutton.value=buttonClose;
					closeFlag="1";
					break;
				case "userModify" :	//用户修改
					message="修改用户成功";
					//myurl="window.opener.location='userAction.do?method=userList'";
					myurl="window.opener.location.reload()";
					document.form1.mybutton.value=buttonClose;	
					closeFlag="1";
					break;
				case "userDelete" : 	//用户删除
					message="删除成功";
					myurl="window.location='userAction.do?method=userList'";
					document.form1.mybutton.value=buttonReturn;		
					break;
				case "userRenew" :	//用户恢复
					message="恢复成功";
					myurl="window.location='userAction.do?method=userList'";
					document.form1.mybutton.value=buttonReturn;
					break;
				case "userMove" : 	//用户移出
					message="移除成功";
					myurl="window.opener.location.reload()";
					document.form1.mybutton.value=buttonReturn;	
					closeFlag="1";	
					break;
				case "roleAdd" :	//权限新增
					message="新增权限成功";
					myurl="window.opener.location='roleAction.do?method=roleList'";
					document.form1.mybutton.value=buttonClose;
					closeFlag="1";
					break;
				case "roleModify" :	//权限修改
					message="修改权限成功";
					//myurl="window.opener.location='roleAction.do?method=roleList'";
					myurl="window.opener.location.reload()";
					document.form1.mybutton.value=buttonClose;	
					closeFlag="1";
					break;
				case "roleDelete" : 	//权限删除
					message="删除成功";
					myurl="location='roleAction.do?method=roleList'";
					document.form1.mybutton.value=buttonReturn;		
					break;
			
			
				case "inquiryConfirmed" : 	// 询价确认
						message="操作成功";
						myurl="window.location='saleInfoAction.do?method=inquiryDetail&saleNo=<%=tempData%>'";
						document.form1.mybutton.value=buttonConfirm;
						break;
						
				case "saveCustomer" : 	// 客户插入or修改确认
						message="操作成功";
						myurl="window.location='customerInfoAction.do?method=customerList'";
						document.form1.mybutton.value=buttonConfirm;
						break;
						
						
				case "saveMachineTool" : 	
						message="操作成功";
						myurl="window.location='machineToolAction.do?method=machineToolList'";
						document.form1.mybutton.value=buttonConfirm;		
						break;
						
				case "saveBugInfo" : 	
						message="操作成功";
						myurl="window.location='bugInfoAction.do?method=bugInfoList&status=init'";
						document.form1.mybutton.value=buttonConfirm;		
						break;
							
				case "saveEmployee" : 	// 员工插入or修改确认
						message="操作成功";
						myurl="window.location='employeeInfoAction.do?method=employeeList'";
						document.form1.mybutton.value=buttonConfirm;
						break;
						
				case "deleteCustomer" : 	// 客户删除确认
						message="删除成功";
						myurl="window.location='customerInfoAction.do?method=customerList'";
						document.form1.mybutton.value=buttonConfirm;
						break;
				case "deleteEmployee" : 	// 员工删除确认
						message="删除成功";
						myurl="window.location='employeeInfoAction.do?method=employeeList'";
						document.form1.mybutton.value=buttonConfirm;
						break;
				case "saveFactory" : 	// 厂商插入or修改确认
						message="操作成功";
						myurl="window.location='factoryInfoAction.do?method=factoryList'";
						document.form1.mybutton.value=buttonConfirm;
						break;
				case "deleteFactory" : 	// 厂商删除确认
						message="删除成功";
						myurl="window.location='factoryInfoAction.do?method=factoryList'";
						document.form1.mybutton.value=buttonConfirm;
						break;					
								
				case "saleAskSave" : 	
						message="暂存完成";
						myurl="window.location='saleInfoAction.do?method=inquiryDetail&flag=list&saleNo=<%=tempData%>'";
						document.form1.mybutton.value=buttonConfirm;		
						break;			
						
				case "saleAskCancel" : 	
						message="取消成功";
						myurl="window.location='saleInfoAction.do?method=saleAskConfirmList'";
						document.form1.mybutton.value=buttonConfirm;		
						break;			
						
				case "saleCheckList" : 	
						message="操作完成";
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
						message="取消完成";
						myurl="window.location='saleInfoAction.do?method=saleInfoDetail&&saleNo=<%=tempData%>'";
						document.form1.mybutton.value=buttonConfirm;		
						break;	
						
				case "sendPo" : 	
						<%if(!tempData.equals("")){%>
						window.open('partPoAction.do?method=poFormPrint&orderNo=<%=tempData%>');
						message="订单已确认，请保存打印单据";
						<%}%>
						myurl="window.location='partPoAction.do?method=planList'";
						document.form1.mybutton.value=buttonConfirm;		
						break;	
							
				case "manualPlanAdd" : 	
						<%if(!tempData.equals("")){%>
						window.open('partPoAction.do?method=poFormPrint&orderNo=<%=tempData%>');
						message="订单已确认，请保存打印单据";
						<%}%>
						myurl="window.location='partPoAction.do?method=manualPlanList'";
						document.form1.mybutton.value=buttonConfirm;		
						break;	
						
				case "orderCancel" : 	
						message="订单取消完成";
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
						message="手工分配完成";
						myurl="window.location='handAllocateAction.do?method=requestList'";
						document.form1.mybutton.value=buttonConfirm;		
						break;	
						
				case "consignApprove" : 	
						message="发货审批完成";
						myurl="window.location='asnListAction.do?method=consignApproveList&queryFlag=N'";
						document.form1.mybutton.value=buttonConfirm;		
						break;	
						
				case "stockAdjustOutConfirm" : 	
						message="库存调整出库完成";
						myurl="window.location='stockOutOperateAction.do?method=stockAdjustOutList'";
						document.form1.mybutton.value=buttonConfirm;		
						break;	
				case "stockAdjustTaxOutConfirm" : 	
						message="税务库存调整完成";
						myurl="window.location='stockOutOperateAction.do?method=stockTaxAdjustOutList'";
						document.form1.mybutton.value=buttonConfirm;		
						break;	
				
				case "takeReport" : 	//盘点报表
					message="盘点成功";
					myurl="location='stockReportAction.do?method=takeReport&takeId=<%=tempData%>'";
					document.form1.mybutton.value=buttonReturn;
					break;
				
				case "takeConfirm" : 	//盘点确认
					message="一次盘点成功";
					myurl="location='stockTakeAction.do?method=takeInit'";
					document.form1.mybutton.value=buttonReturn;
					break;	
				
				case "takeCancel" : 	//盘点取消
					message="盘点已取消";
					myurl="location='stockTakeAction.do?method=takeInit'";
					document.form1.mybutton.value=buttonReturn;
					break;
				
				case "shipConfirm" :	
					message="出货成功";
					myurl="window.location='asnAction.do?method=asnReadyList'";
					document.form1.mybutton.value=buttonReturn;
					break;					
									
				case "consignAgain" :	
					myurl="window.location='asnListAction.do?method=asnResultList&queryFlag=N'";
					document.form1.mybutton.value=buttonReturn;
					break;					
								
				case "insertAccount" : 	
						message="新增成功";
						myurl="window.location='accountItemAction.do?method=accountInit'";
						document.form1.mybutton.value=buttonConfirm;
						break;
				case "updateAccount" : 	
						message="修改成功";
						myurl="window.location='accountItemAction.do?method=accountList'";
						document.form1.mybutton.value=buttonConfirm;
						break;
						
				case "deleteAccount" : 	
						message="删除成功";
						myurl="window.location='accountItemAction.do?method=accountList'";
						document.form1.mybutton.value=buttonConfirm;
						break;
							
				case "recountStat" : 	
						message="重新统计完成";
						myurl="window.location='accountStatisticsAction.do?method=statList'";
						document.form1.mybutton.value=buttonConfirm;
						break;	
					
				case "deleteAccountStat" : 	
						message="删除完成";
						myurl="window.location='accountStatisticsAction.do?method=statList'";
						document.form1.mybutton.value=buttonConfirm;
						break;	
				
				case "receiveAdd" : 	
						message="登记成功";
						myurl="window.location='repairAction.do?method=receiveInit'";
						document.form1.mybutton.value=buttonConfirm;
						break;
				
				case "turningAdd" : 	
						message="登记成功";
						myurl="window.location='repairTurningAction.do?method=jwReceiveInit'";
						document.form1.mybutton.value=buttonConfirm;
						break;	
					
				case "toolsInOperate":
						message="调入成功";
						myurl="window.location='stockToolsAction.do?method=stockToolsList'";
						document.form1.mybutton.value=buttonConfirm;
						break;
						
				case "loanPartOutList":
						message="出库成功";
						myurl="window.location='repairOperateAction.do?method=loanPartOutList'";
						document.form1.mybutton.value=buttonConfirm;
						break;
						
				case "loanToolOutList":
						message="出库成功";
						myurl="window.location='repairOperateAction.do?method=loanToolOutList'";
						document.form1.mybutton.value=buttonConfirm;
						break;
						
						
				case "transferSale" :	
					message="转销售成功";
					myurl="window.opener.location='repairAction.do?method=repairList'";
					//myurl="window.opener.location.reload()";
					document.form1.mybutton.value=buttonClose;	
					closeFlag="1";
					break;		
					
					
				case "iwPartApprove":
						message="审批完成";
						myurl="window.location='salePartsApproveAction.do?method=iwPartsList'";
						document.form1.mybutton.value=buttonConfirm;
						break;
						
				case "stockBackOperate":
					message="回库完成";
					myurl="window.location='stockBackAction.do?method=stockBackList&queryFlag=N'";
					document.form1.mybutton.value=buttonConfirm;
					break;
					
				
				case "repairDispatch":
					message="派工完成";
					myurl="window.opener.location='repairAction.do?method=repairDispatchList&queryFlag=N'";
					document.form1.mybutton.value=buttonConfirm;
					closeFlag="1";
					break;	
					
				case "repairReturnEnd":
					message="返还完成";
					myurl="window.opener.location='repairAction.do?method=repairReturnList&queryFlag=N'";
					document.form1.mybutton.value=buttonConfirm;
					closeFlag="1";
					break;	
					
				case "repairComplete":
					message="修复完成";
					myurl="window.opener.location='repairAction.do?method=repairCompleteList&queryFlag=N'";
					//myurl="window.opener.location.reload()";
					document.form1.mybutton.value=buttonConfirm;
					closeFlag="1";
					break;	
					
				
				case "turningList":
					message="派工完成";
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