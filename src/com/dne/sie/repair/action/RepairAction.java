package com.dne.sie.repair.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.dne.sie.common.tools.EscapeUnescape;
import com.dne.sie.common.tools.FormNumberBuilder;
import com.dne.sie.common.tools.Operate;
import com.dne.sie.maintenance.bo.EmployeeInfoBo;
import com.dne.sie.reception.bo.SaleInfoBo;
import com.dne.sie.repair.bo.RepairListBo;
import com.dne.sie.repair.form.RepairFeeInfoForm;
import com.dne.sie.repair.form.RepairSearchForm;
import com.dne.sie.repair.form.RepairServiceForm;
import com.dne.sie.util.action.ControlAction;

public class RepairAction extends ControlAction {
	
//	public final static ConcurrentHashMap RepairDetailWarnMap = new ConcurrentHashMap();
	
	/**
	 * “维修”->“维修单查询”
	 * @param request HttpServletRequest
	 * @param form ActionForm
	 * @return String 返回需要forward至的地址
	 */
	public String repairQueryList(HttpServletRequest request,ActionForm form) throws Exception{  
		String forward = "repairQueryList";
		
		RepairSearchForm rsForm = (RepairSearchForm) form;
		
		RepairListBo rlBo = RepairListBo.getInstance();
		request.setAttribute("repairQueryList",rlBo.getRepairQueryList(rsForm));
	
		return forward;
	}
	

	/**
	 * “维修”->“维修单查询”->“维修单明细”
	 * @param request HttpServletRequest
	 * @param form ActionForm
	 * @return String 返回需要forward至的地址
	 */
	public String repairQueryDetail(HttpServletRequest request,ActionForm form) throws Exception{ 
		String forward = "repairQueryCDetail";
		
		String repairNo = request.getParameter("repairNo");
		
		RepairListBo rlBo = RepairListBo.getInstance();
		RepairServiceForm rsf = rlBo.getRepairDetail(new Long(repairNo));
//		CustomerInfoForm rcif = rsf.getCustomInfoForm();
		
		request.setAttribute("repairServiceForm", rsf);
		request.setAttribute("repairSearchForm", rsf);


		//取出零件信息列表
		request.setAttribute("partsList", rlBo.getRepairPartsList(rsf.getRepairNo(),"complete"));
		request.setAttribute("loanList", rlBo.getloanPartsList(rsf.getRepairNo(),"complete"));
		request.setAttribute("toolsList", rlBo.getToolsList(rsf.getRepairNo(),"complete"));
		if(rsf.getCurrentStatus().equals("F")||rsf.getCurrentStatus().equals("R")){
			request.setAttribute("irisTree",rlBo.getIrisTree(rlBo.getRepairIrisList(rsf.getRepairNo())));
		}
		RepairSearchForm rsForm = new RepairSearchForm();
		rsForm.setSerialNo(rsf.getSerialNo());
		rsForm.setTxtNumPerPage("1000");
		rsForm.setRr90("serial");
		rsForm.setRepairNo(rsf.getRepairNo());
		
		request.setAttribute("historyRepairList",rlBo.getRepairQueryList(rsForm));

		//取此维修单的附件
		request.setAttribute("repairAttachment", rlBo.getRepairAttachment(new Long(repairNo)));
		
		if(rsf.getWarrantyType().equals("A")){
			forward="repairQueryJwDetail";
		}else if(rsf.getWarrantyType().equals("B")){
			forward="repairQueryAtDetail";
		}else if(rsf.getWarrantyType().equals("C")){
			forward="repairQueryJcDetail";
		}
			
		return forward;
	}
	

	/**
	 *	初始化，“维修”->“维修登记”
	 *	@param request HttpServletRequest
	 *	@param form ActionForm
	 *	@return 
	 */
	public String receiveInit(HttpServletRequest request, ActionForm form) throws Exception{
		String forward = "receiveInit";
		
		//返回显示下个维修单号
		request.setAttribute("serviceSheetNo",FormNumberBuilder.findNewServiveSheetNo());
			
		return forward;
	}
	
	/**
	 * 该方法显示修理列表“维修”->“电话诊断”
	 * @param request HttpServletRequest
	 * @param form ActionForm
	 * @return String 返回需要forward至的地址
	 */
	public String repairList(HttpServletRequest request,ActionForm form) throws Exception{ 
		String forward = "repairList";
		RepairSearchForm rsForm = (RepairSearchForm) form;
		rsForm.setCurrentStatus("DZ");		//电诊中
		rsForm.setRepairProperites("repair");
		
		RepairListBo rlBo = RepairListBo.getInstance();
		request.setAttribute("repairList",rlBo.getRepairList(rsForm));
		return forward;
	}
	
	

	/**
	 * “维修”->“电话诊断”->“维修单明细”
	 * @param request HttpServletRequest
	 * @param form ActionForm
	 * @return String 返回需要forward至的地址
	 */
	public String repairDetail(HttpServletRequest request,ActionForm form) throws Exception{ 
		String forward = "repairCDetail";
		
		String repairNo = request.getParameter("repairNo");
		String status = request.getParameter("status");
		
		RepairListBo rlBo = RepairListBo.getInstance();
		RepairServiceForm rsf = rlBo.getRepairDetail(new Long(repairNo));
		
		List feeList = rlBo.getRepairFeeList(rsf.getRepairNo());
		if(feeList!=null && feeList.size()==1){
			RepairFeeInfoForm rf = (RepairFeeInfoForm)feeList.get(0);
			rsf.setRepairmanNum(rf.getRepairmanNum());
			rsf.setWorkingHours(rf.getWorkingHours());
			rsf.setTicketsAllCosts(Operate.roundD(rf.getTicketsAllCosts(), 2));
			rsf.setLaborCosts(Operate.roundD(rf.getLaborCosts(), 2));
		}
		
		request.setAttribute("repairServiceForm", rsf);
		request.setAttribute("repairSearchForm", rsf);
		
		if(rsf.getDzReason()==null) rsf.setDzReason(rsf.getCustomerTrouble());
		if(rsf.getEstimateRepairDate()!=null && rsf.getExpectedDuration() == null){
			int day = Operate.getDateCompare(rsf.getEstimateRepairDate(),rsf.getCreateDate());
			if(day >=0) rsf.setExpectedDuration(day);
		}

		request.setAttribute("repairManList", EmployeeInfoBo.getInstance().getRepairManList());
		
		RepairSearchForm rsForm = new RepairSearchForm();
		rsForm.setSerialNo(rsf.getSerialNo());
		rsForm.setTxtNumPerPage("1000");
		rsForm.setRr90("serial");
		rsForm.setRepairNo(rsf.getRepairNo());
		request.setAttribute("historyRepairList",rlBo.getRepairQueryList(rsForm));

		//取出零件信息列表
		request.setAttribute("partsList", rlBo.getRepairPartsList(rsf.getRepairNo()));
		request.setAttribute("loanList", rlBo.getloanPartsList(rsf.getRepairNo()));
		request.setAttribute("toolsList", rlBo.getToolsList(rsf.getRepairNo()));
		request.setAttribute("feeList", feeList);

		//取此维修单的附件
		request.setAttribute("repairAttachment", rlBo.getRepairAttachment(new Long(repairNo)));
		
		if("S".equals(status)){	//零件销售中
			forward = "repairSaleDetail";
		}
		
		return forward;
	}
	
	
	/**
	 * 该方法显示修理列表“维修”->“维修派工”
	 * @param request HttpServletRequest
	 * @param form ActionForm
	 * @return String 返回需要forward至的地址
	 */
	public String repairDispatchList(HttpServletRequest request,ActionForm form) throws Exception{ 
		String forward = "repairDispatchList";
		RepairSearchForm rsForm = (RepairSearchForm) form;
		rsForm.setCurrentStatus("T");		//销售完成
		rsForm.setRepairProperites("repair");
		
		RepairListBo rlBo = RepairListBo.getInstance();
		request.setAttribute("repairList",rlBo.getRepairList(rsForm));
		return forward;
	}
	

	/**
	 * “维修”->“维修派工”->“明细”
	 * @param request HttpServletRequest
	 * @param form ActionForm
	 * @return String 返回需要forward至的地址
	 */
	public String repairDispatchDetail(HttpServletRequest request,ActionForm form) throws Exception{ 
		String forward = "repairDispatchDetail";
		
		String repairNo = request.getParameter("repairNo");
		//String status = request.getParameter("status");
		
		RepairListBo rlBo = RepairListBo.getInstance();
		RepairServiceForm rsf = rlBo.getRepairDetail(new Long(repairNo));
		
//		List feeList = rlBo.getRepairFeeList(rsf.getRepairNo());
//		if(feeList!=null && feeList.size()==1){
//			RepairFeeInfoForm rf = (RepairFeeInfoForm)feeList.get(0);
//			rsf.setRepairmanNum(rf.getRepairmanNum());
//			rsf.setWorkingHours(rf.getWorkingHours());
//			rsf.setTicketsAllCosts(Operate.roundD(rf.getTicketsAllCosts(), 2));
//			rsf.setLaborCosts(Operate.roundD(rf.getLaborCosts(), 2));
//		}
		
		request.setAttribute("repairServiceForm", rsf);
		request.setAttribute("repairSearchForm", rsf);
		
		if(rsf.getDzReason()==null) rsf.setDzReason(rsf.getCustomerTrouble());
		if(rsf.getEstimateRepairDate()!=null && rsf.getExpectedDuration() == null){
			int day = Operate.getDateCompare(rsf.getEstimateRepairDate(),rsf.getCreateDate());
			if(day >=0) rsf.setExpectedDuration(day);
		}

		request.setAttribute("repairManList", EmployeeInfoBo.getInstance().getRepairManList());

		//取出零件信息列表
		request.setAttribute("partsList", rlBo.getRepairPartsList(rsf.getRepairNo(),"T"));
		request.setAttribute("loanList", rlBo.getloanPartsList(rsf.getRepairNo()));
		request.setAttribute("toolsList", rlBo.getToolsList(rsf.getRepairNo()));
//		request.setAttribute("feeList", feeList);

		//取此维修单的附件
		request.setAttribute("repairAttachment", rlBo.getRepairAttachment(new Long(repairNo)));
		
	
		
		return forward;
	}
	

	/**
	 * 该方法显示修理列表“维修”->“维修返还”
	 * @param request HttpServletRequest
	 * @param form ActionForm
	 * @return String 返回需要forward至的地址
	 */
	public String repairReturnList(HttpServletRequest request,ActionForm form) throws Exception{ 
		String forward = "repairReturnList";
		RepairSearchForm rsForm = (RepairSearchForm) form;
		rsForm.setCurrentStatus("D");		//已派工
		rsForm.setRepairProperites("repair");
		
		Long employeeId = (Long)request.getSession().getAttribute("employeeId");
		String roleIds = (String)request.getSession().getAttribute("sessionRoleIds");
		rsForm.setCurrentUserId(employeeId);
		rsForm.setRoleIds(roleIds);
		
		RepairListBo rlBo = RepairListBo.getInstance();
		request.setAttribute("repairList",rlBo.getRepairList(rsForm));
		return forward;
	}

	/**
	 * 该方法显示修理列表“维修”->“维修返还” ->“明细”
	 * @param request HttpServletRequest
	 * @param form ActionForm
	 * @return String 返回需要forward至的地址
	 */
	public String repairReturnDetail(HttpServletRequest request,ActionForm form) throws Exception{ 
		String forward = "repairReturnDetail";
		String repairNo = request.getParameter("repairNo");
		
		RepairListBo rlBo = RepairListBo.getInstance();
		RepairServiceForm rsf = rlBo.getRepairDetail(new Long(repairNo));
		
		
		request.setAttribute("repairServiceForm", rsf);
		
		if(rsf.getDzReason()==null) rsf.setDzReason(rsf.getCustomerTrouble());
		if(rsf.getEstimateRepairDate()!=null && rsf.getExpectedDuration() == null){
			int day = Operate.getDateCompare(rsf.getEstimateRepairDate(),rsf.getCreateDate());
			if(day >=0) rsf.setExpectedDuration(day);
		}

		request.setAttribute("repairManList", EmployeeInfoBo.getInstance().getRepairManList());

		//取出零件信息列表
		request.setAttribute("partsList", rlBo.getRepairPartsList(rsf.getRepairNo(),"return"));
		request.setAttribute("loanList", rlBo.getloanPartsList(rsf.getRepairNo(),"return"));
		request.setAttribute("toolsList", rlBo.getToolsList(rsf.getRepairNo(),"return"));

		//取此维修单的附件
		request.setAttribute("repairAttachment", rlBo.getRepairAttachment(new Long(repairNo)));
		
	
		
		return forward;
	}
	

	
	/**
	 * 该方法显示修理列表“维修”->“维修报告”
	 * @param request HttpServletRequest
	 * @param form ActionForm
	 * @return String 返回需要forward至的地址
	 */
	public String repairCompleteList(HttpServletRequest request,ActionForm form) throws Exception{ 
		String forward = "repairCompleteList";
		RepairSearchForm rsForm = (RepairSearchForm) form;
		rsForm.setCurrentStatus("report");		//维修报告
		rsForm.setRepairProperites("repair");
		
		Long employeeId = (Long)request.getSession().getAttribute("employeeId");
		rsForm.setCurrentUserId(employeeId);
		String roleIds = (String)request.getSession().getAttribute("sessionRoleIds");
		rsForm.setRoleIds(roleIds);
		
		RepairListBo rlBo = RepairListBo.getInstance();
		request.setAttribute("repairList",rlBo.getRepairList(rsForm));
		return forward;
	}
	
	
	
	
	/**
	 * “维修”->“维修报告”->“明细”
	 * @param request HttpServletRequest
	 * @param form ActionForm
	 * @return String 返回需要forward至的地址
	 */
	public String repairCompleteCDetail(HttpServletRequest request,ActionForm form) throws Exception{ 
		String forward = "repairCompleteCDetail";
		
		String repairNo = request.getParameter("repairNo");
		String flag = request.getParameter("flag");
		
		RepairListBo rlBo = RepairListBo.getInstance();
		RepairServiceForm rsf = rlBo.getRepairDetail(new Long(repairNo));
		rsf.setActualOnsiteDateStr(Operate.formatYMDDate(rsf.getActualOnsiteDate()));
		rsf.setActualRepairedDateStr(Operate.formatYMDDate(rsf.getActualRepairedDate()));
		//if(rsf.getConfirmSymptom()!=null) rsf.setConfirmSymptom(rsf.getConfirmSymptom().replaceAll("\r\n", "<br>"));
		
		request.setAttribute("repairServiceForm", rsf);
		request.setAttribute("repairSearchForm", rsf);
	
		//取出零件信息列表
		request.setAttribute("partsList", rlBo.getRepairPartsList(rsf.getRepairNo(),"complete"));
		request.setAttribute("loanList", rlBo.getloanPartsList(rsf.getRepairNo(),"complete"));
		request.setAttribute("toolsList", rlBo.getToolsList(rsf.getRepairNo(),"complete"));
	
		request.setAttribute("irisTree",rlBo.getIrisTree(rlBo.getRepairIrisList(rsf.getRepairNo())));

		//取此维修单的附件
		request.setAttribute("repairAttachment", rlBo.getRepairAttachment(new Long(repairNo)));
		
		if("approve".equals(flag)){
			forward = "repairEndApproveCDetail";
		}
		return forward;
	}
	
	public String repairEndApproveList(HttpServletRequest request,ActionForm form) throws Exception{ 
		String forward = "repairEndApproveList";
		RepairSearchForm rsForm = (RepairSearchForm) form;
		rsForm.setCurrentStatus("F");		//修复提交
		rsForm.setRepairProperites("repair");
		
		Long employeeId = (Long)request.getSession().getAttribute("employeeId");
		rsForm.setCurrentUserId(employeeId);
		String roleIds = (String)request.getSession().getAttribute("sessionRoleIds");
		rsForm.setRoleIds(roleIds);
		
		RepairListBo rlBo = RepairListBo.getInstance();
		request.setAttribute("repairList",rlBo.getRepairList(rsForm));
		return forward;
	}
	
	public String repeatedReceivePrint(HttpServletRequest request, ActionForm form) throws Exception{
		
		String repairNo = request.getParameter("repairNo");
		
		RepairServiceForm rsf = RepairListBo.getInstance().getRepairDetail(new Long(repairNo));
		
		request.setAttribute("partsList",SaleInfoBo.getInstance().getSalePartsListByNo(new Long(repairNo)));
		request.setAttribute("repair", rsf);
		
		return "repairPrint";
	}
	public String dispatchPrint(HttpServletRequest request, ActionForm form) throws Exception{
		
		String repairNo = request.getParameter("repairNo");
		RepairListBo rlBo = RepairListBo.getInstance();
		RepairServiceForm rsf =rlBo.getRepairDetail(new Long(repairNo));
		
//		request.setAttribute("partsList",SaleInfoBo.getInstance().getSalePartsListByNo(new Long(repairNo)));
		
		request.setAttribute("partsList", rlBo.getRepairPartsList(rsf.getRepairNo(),"complete"));
		request.setAttribute("loanList", rlBo.getloanPartsList(rsf.getRepairNo(),"complete"));
		request.setAttribute("toolsList", rlBo.getToolsList(rsf.getRepairNo(),"complete"));
		
		request.setAttribute("repair", rsf);
		
		return "dispatchPrint";
	}
	public String repairReportPrint(HttpServletRequest request, ActionForm form) throws Exception{
		
		String repairNo = request.getParameter("repairNo");
		
		RepairServiceForm rsf = RepairListBo.getInstance().getRepairDetail(new Long(repairNo));
		if(!rsf.getRepairProperites().equals("T")){
			String[] irisInfo = RepairListBo.getInstance().getIrisContent(new Long(repairNo));
			request.setAttribute("irisInfo", irisInfo);
		}
		request.setAttribute("repair", rsf);
		
		
		
		return "repairReportPrint";
	}
	
	
	public String irisContent(HttpServletRequest request, ActionForm form) throws Exception{
		String irisContent = request.getParameter("irisContent")==null?"":request.getParameter("irisContent");
		String desc = request.getParameter("desc");
		String name = request.getParameter("name")==null?"":request.getParameter("name");
		String rnd = request.getParameter("Rnd");
		request.setAttribute("irisContent", new String(irisContent.getBytes("iso-8859-1")));
		request.setAttribute("desc", desc);
		request.setAttribute("name", new String(name.getBytes("iso-8859-1")));
		request.setAttribute("Rnd", rnd);
		request.setAttribute("flag", request.getParameter("flag"));
		request.setAttribute("id", request.getParameter("id"));
		return "irisContent";
	}
	
	


}
