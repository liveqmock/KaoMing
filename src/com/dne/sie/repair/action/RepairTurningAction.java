package com.dne.sie.repair.action;

import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.dne.sie.common.exception.VersionException;
import com.dne.sie.common.tools.FormNumberBuilder;
import com.dne.sie.common.tools.Operate;
import com.dne.sie.maintenance.bo.EmployeeInfoBo;
import com.dne.sie.repair.bo.RepairHandleBo;
import com.dne.sie.repair.bo.RepairListBo;
import com.dne.sie.repair.bo.RepairTurningBo;
import com.dne.sie.repair.form.RepairSearchForm;
import com.dne.sie.repair.form.RepairServiceForm;
import com.dne.sie.util.action.ControlAction;

public class RepairTurningAction extends ControlAction {
	

	/**
	 *	初始化，“安调”->“安调登记”
	 *	@param request HttpServletRequest
	 *	@param form ActionForm
	 *	@return 
	 */
	public String jwReceiveInit(HttpServletRequest request, ActionForm form) throws Exception{
		String forward = "jwReceiveInit";
		
		//返回显示下个维修单号
		request.setAttribute("serviceSheetNo",FormNumberBuilder.findNewServiveSheetNo());
			
		return forward;
	}
	

	/**
	 *	就位登记
	 *	@param request HttpServletRequest
	 *	@param form ActionForm
	 *	@return 
	 */
	public String turningAdd(HttpServletRequest request, ActionForm form) throws Exception {
		String forward = "resultMessage";

		String tempAttache = request.getParameter("attacheIds");
		
		RepairTurningBo rtb = RepairTurningBo.getInstance();
		Long userId=(Long) request.getSession().getAttribute("userId");
		
		RepairSearchForm searchForm=(RepairSearchForm) form;
		searchForm.setCurrentStatus("T");	//准备派工
		searchForm.setRepairProperites("T");	//安调
		searchForm.setCreateBy(userId);
		searchForm.setCreateDate(new Date());
		searchForm.setOperaterId(userId);
		searchForm.setRr90("");
		searchForm.setPurchaseDate(Operate.toDate(searchForm.getPurchaseDateStr()));
		searchForm.setCustomerVisitDate(Operate.toDate(searchForm.getCustomerVisitDateStr()));
		searchForm.setEstimateRepairDate(Operate.toDate(searchForm.getEstimateRepairDateStr()));
		searchForm.setExtendedWarrantyDate(Operate.toDate(searchForm.getExtendedWarrantyDateStr()));
		
		RepairServiceForm rsf=null;
		
		rsf =rtb.addTurningService(searchForm);
	
		//保存单据
		Long repairNo = rsf.getRepairNo();
			
		RepairHandleBo rhb = RepairHandleBo.getInstance();
		if (tempAttache!=null&&!tempAttache.equals("")) {
			rhb.updateAttacheByAttacheIdsAndSheetNo(tempAttache.split(","),repairNo,userId);
		}
		
		request.setAttribute("tag", "1");
		request.setAttribute("businessFlag", "turningAdd");
		
		return forward;
	}
	
	/**
	 * “安调”->“就位派工查詢”
	 * @param request HttpServletRequest
	 * @param form ActionForm
	 * @return String 返回需要forward至的地址
	 */
	public String jwDispatchList(HttpServletRequest request,ActionForm form) throws Exception{  
		String forward = "jwDispatchList";
		
		RepairSearchForm rsForm = (RepairSearchForm) form;
		rsForm.setRepairProperites("T");
		rsForm.setCurrentStatus("T");	//准备派工
		rsForm.setWarrantyType("A"); //就位
		
		RepairListBo rlBo = RepairListBo.getInstance();
		request.setAttribute("atDispatchList",rlBo.getRepairList(rsForm));
	
		return forward;
	}
	
	
	
	public String jwDispatchDetail(HttpServletRequest request,ActionForm form) throws Exception{ 
		String forward = "jwDispatchDetail";
		
		String repairNo = request.getParameter("repairNo");
		
		RepairListBo rlBo = RepairListBo.getInstance();
		RepairServiceForm rsf = rlBo.getRepairDetail(new Long(repairNo));
		
		request.setAttribute("repairServiceForm", rsf);
		request.setAttribute("repairSearchForm", rsf);
		
		request.setAttribute("repairManList", EmployeeInfoBo.getInstance().getRepairManList());
		request.setAttribute("toolsList", rlBo.getToolsList(rsf.getRepairNo()));

		//取此维修单的附件
		request.setAttribute("repairAttachment", rlBo.getRepairAttachment(new Long(repairNo)));
		
		
		return forward;
	}
	
	

	/**
	 * 派工 -- 就位派工 or 安调派工 or 就位派工
	 * @param request
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public String dispatch(HttpServletRequest request, ActionForm form) throws Exception {
		String forward = "resultMessage";
		
		RepairSearchForm searchForm=(RepairSearchForm) form;
		RepairHandleBo rhb = RepairHandleBo.getInstance();
		Long userId=(Long) request.getSession().getAttribute("userId");
		
		searchForm.setCurrentStatus("D"); //已派工
		searchForm.setUpdateBy(userId);
		searchForm.setUpdateDate(new Date());
		
		String tempData="jwDispatchList";
		if(searchForm.getWarrantyType().equals("B")){
			tempData="atDispatchList";
		}else if(searchForm.getWarrantyType().equals("C")){
			tempData="jcDispatchList";
		}
		
		String repairMans = request.getParameter("repairMans");
		
		if(repairMans!=null && repairMans.startsWith("@")){
			repairMans=repairMans.substring(1)+" ";
		}
		RepairTurningBo.getInstance().dispatch(searchForm,repairMans);
		
		String tempAttache = request.getParameter("attacheIds");
		if (tempAttache!=null&&!tempAttache.equals("")) {
			rhb.updateAttacheByAttacheIdsAndSheetNo(tempAttache.split(","),searchForm.getRepairNo(),userId);
		}
		request.setAttribute("tag", "1");
		request.setAttribute("businessFlag", "turningList");
		request.setAttribute("tempData",tempData);
		
		return forward;
	}
	
	
	public String jwCompleteList(HttpServletRequest request,ActionForm form) throws Exception{  
		String forward = "jwCompleteList";
		
		RepairSearchForm rsForm = (RepairSearchForm) form;
		rsForm.setRepairProperites("T");
		rsForm.setCurrentStatus("D");	//已派工
		rsForm.setWarrantyType("A"); //就位
		
		Long employeeId = (Long)request.getSession().getAttribute("employeeId");
		String roleIds = (String)request.getSession().getAttribute("sessionRoleIds");
		rsForm.setCurrentUserId(employeeId);
		rsForm.setRoleIds(roleIds);
		
		RepairListBo rlBo = RepairListBo.getInstance();
		request.setAttribute("jwCompleteList",rlBo.getRepairList(rsForm));
	
		return forward;
	}
	
	
	
	public String jwCompleteDetail(HttpServletRequest request,ActionForm form) throws Exception{ 
		String forward = "jwCompleteDetail";
		
		String repairNo = request.getParameter("repairNo");
		
		RepairListBo rlBo = RepairListBo.getInstance();
		RepairServiceForm rsf = rlBo.getRepairDetail(new Long(repairNo));
		
		request.setAttribute("repairServiceForm", rsf);
		request.setAttribute("repairSearchForm", rsf);
		
		request.setAttribute("toolsList", rlBo.getToolsList(rsf.getRepairNo()));

		//取此维修单的附件
		request.setAttribute("repairAttachment", rlBo.getRepairAttachment(new Long(repairNo)));
		
		
		return forward;
	}
	

	/**
	 * 派工 -- 就位派工 or 安调派工 or 就位派工
	 * @param request
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public String atComplete(HttpServletRequest request, ActionForm form) throws Exception {
		String forward = "resultMessage";
		
		RepairSearchForm searchForm=(RepairSearchForm) form;
		RepairHandleBo rhb = RepairHandleBo.getInstance();
		RepairTurningBo rtb = RepairTurningBo.getInstance();
		Long userId=(Long) request.getSession().getAttribute("userId");
	
		searchForm.setCurrentStatus("F"); //修复
		searchForm.setUpdateBy(userId);
		searchForm.setUpdateDate(new Date());
		
		String tempData="jwCompleteList";
		if(searchForm.getWarrantyType().equals("B")){
			tempData="atCompleteList";
		}else if(searchForm.getWarrantyType().equals("C")){
			tempData="jcCompleteList";
		}else if(searchForm.getWarrantyType().equals("A")){
			if(searchForm.getExtendedWarrantyDateStr()!=null)
				searchForm.setExtendedWarrantyDate(Operate.toDate(searchForm.getExtendedWarrantyDateStr()));
		}
		

		String[] arrivalDate = request.getParameterValues("arrivalDate");
		String[] returnDate = request.getParameterValues("returnDate");
		String[] travelFee = request.getParameterValues("travelFee");
		String[] laborCosts = request.getParameterValues("laborCosts");
		String[] repairCondition = request.getParameterValues("repairCondition");
		String[] travelId = request.getParameterValues("travelId");
		
		if(travelId==null){
			throw new Exception("travelId null!");
		}
		
		ArrayList<String[]> repairManInfo = new ArrayList<String[]>();
		repairManInfo.add(travelId);
		repairManInfo.add(arrivalDate);
		repairManInfo.add(returnDate);
		repairManInfo.add(travelFee);
		repairManInfo.add(laborCosts);
		repairManInfo.add(repairCondition);
		
		try{
			if(searchForm.getWarrantyType().equals("A")){
				rtb.jwComplete(searchForm,repairManInfo);
			}else if(searchForm.getWarrantyType().equals("B")){
				rtb.atComplete(searchForm,repairManInfo);
			}else if(searchForm.getWarrantyType().equals("C")){
				rtb.jcComplete(searchForm,repairManInfo);
			}
		}catch(VersionException ve){
			return "versionErr";
		}
		String tempAttache = request.getParameter("attacheIds");
		if (tempAttache!=null&&!tempAttache.equals("")) {
			rhb.updateAttacheByAttacheIdsAndSheetNo(tempAttache.split(","),searchForm.getRepairNo(),userId);
		}
		request.setAttribute("tag", "1");
		request.setAttribute("businessFlag", "turningList");
		request.setAttribute("tempData",tempData);
		
		return forward;
	}
	

	/**
	 * “安调”->“安调派工查詢”
	 * @param request HttpServletRequest
	 * @param form ActionForm
	 * @return String 返回需要forward至的地址
	 */
	public String atDispatchList(HttpServletRequest request,ActionForm form) throws Exception{  
		String forward = "atDispatchList";
		
		RepairSearchForm rsForm = (RepairSearchForm) form;
		rsForm.setRepairProperites("T");
		rsForm.setCurrentStatus("T");	//准备派工
		rsForm.setWarrantyType("B"); //安调
		
		RepairListBo rlBo = RepairListBo.getInstance();
		request.setAttribute("atDispatchList",rlBo.getRepairList(rsForm));
		
	
		return forward;
	}
	
	
	
	public String atDispatchDetail(HttpServletRequest request,ActionForm form) throws Exception{ 
		String forward = "atDispatchDetail";
		
		String repairNo = request.getParameter("repairNo");
		
		RepairListBo rlBo = RepairListBo.getInstance();
		RepairServiceForm rsf = rlBo.getRepairDetail(new Long(repairNo));
		
		request.setAttribute("repairServiceForm", rsf);
		request.setAttribute("repairSearchForm", rsf);
		
		request.setAttribute("repairManList", EmployeeInfoBo.getInstance().getRepairManList());
		request.setAttribute("toolsList", rlBo.getToolsList(rsf.getRepairNo()));

		//取此维修单的附件
		request.setAttribute("repairAttachment", rlBo.getRepairAttachment(new Long(repairNo)));
		
		RepairSearchForm lastForm = new RepairSearchForm();
		lastForm.setSerialNo(rsf.getSerialNo());
		lastForm.setTxtNumPerPage("1000");
		lastForm.setRr90("serial");
		lastForm.setRepairNo(rsf.getRepairNo());
		request.setAttribute("historyRepairList",rlBo.getRepairQueryList(lastForm));
		
		return forward;
	}
	
	

	public String atCompleteList(HttpServletRequest request,ActionForm form) throws Exception{  
		String forward = "atCompleteList";
		
		RepairSearchForm rsForm = (RepairSearchForm) form;
		rsForm.setRepairProperites("T");
		rsForm.setCurrentStatus("D");	//已派工
		rsForm.setWarrantyType("B"); //安调
		

		Long employeeId = (Long)request.getSession().getAttribute("employeeId");
		String roleIds = (String)request.getSession().getAttribute("sessionRoleIds");
		rsForm.setCurrentUserId(employeeId);
		rsForm.setRoleIds(roleIds);
		
		
		RepairListBo rlBo = RepairListBo.getInstance();
		request.setAttribute("atCompleteList",rlBo.getRepairList(rsForm));
	
		return forward;
	}
	
	
	
	public String atCompleteDetail(HttpServletRequest request,ActionForm form) throws Exception{ 
		String forward = "atCompleteDetail";
		
		String repairNo = request.getParameter("repairNo");
		
		RepairListBo rlBo = RepairListBo.getInstance();
		RepairServiceForm rsf = rlBo.getRepairDetail(new Long(repairNo));
		
		request.setAttribute("repairServiceForm", rsf);
		request.setAttribute("repairSearchForm", rsf);
		
		request.setAttribute("toolsList", rlBo.getToolsList(rsf.getRepairNo()));

		//取此维修单的附件
		request.setAttribute("repairAttachment", rlBo.getRepairAttachment(new Long(repairNo)));
		
		
		return forward;
	}
	
	

	/**
	 * “安调”->“检测派工查詢”
	 * @param request HttpServletRequest
	 * @param form ActionForm
	 * @return String 返回需要forward至的地址
	 */
	public String jcDispatchList(HttpServletRequest request,ActionForm form) throws Exception{  
		String forward = "jcDispatchList";
		
		RepairSearchForm rsForm = (RepairSearchForm) form;
		rsForm.setRepairProperites("T");
		rsForm.setCurrentStatus("T");	//准备派工
		rsForm.setWarrantyType("C"); //检测
		
		RepairListBo rlBo = RepairListBo.getInstance();
		request.setAttribute("jcDispatchList",rlBo.getRepairList(rsForm));
	
		return forward;
	}
	
	
	
	public String jcDispatchDetail(HttpServletRequest request,ActionForm form) throws Exception{ 
		String forward = "jcDispatchDetail";
		
		String repairNo = request.getParameter("repairNo");
		
		RepairListBo rlBo = RepairListBo.getInstance();
		RepairServiceForm rsf = rlBo.getRepairDetail(new Long(repairNo));
		
		request.setAttribute("repairServiceForm", rsf);
		request.setAttribute("repairSearchForm", rsf);
		
		request.setAttribute("repairManList", EmployeeInfoBo.getInstance().getRepairManList());
		request.setAttribute("toolsList", rlBo.getToolsList(rsf.getRepairNo()));

		//取此维修单的附件
		request.setAttribute("repairAttachment", rlBo.getRepairAttachment(new Long(repairNo)));
		
		
		return forward;
	}
	
	

	public String jcCompleteList(HttpServletRequest request,ActionForm form) throws Exception{  
		String forward = "jcCompleteList";
		
		RepairSearchForm rsForm = (RepairSearchForm) form;
		rsForm.setRepairProperites("T");
		rsForm.setCurrentStatus("D");	//已派工
		rsForm.setWarrantyType("C"); //检测
		

		Long employeeId = (Long)request.getSession().getAttribute("employeeId");
		String roleIds = (String)request.getSession().getAttribute("sessionRoleIds");
		rsForm.setCurrentUserId(employeeId);
		rsForm.setRoleIds(roleIds);
		
		
		RepairListBo rlBo = RepairListBo.getInstance();
		request.setAttribute("jcCompleteList",rlBo.getRepairList(rsForm));
	
		return forward;
	}
	
	
	
	public String jcCompleteDetail(HttpServletRequest request,ActionForm form) throws Exception{ 
		String forward = "jcCompleteDetail";
		
		String repairNo = request.getParameter("repairNo");
		
		RepairListBo rlBo = RepairListBo.getInstance();
		RepairServiceForm rsf = rlBo.getRepairDetail(new Long(repairNo));
		
		request.setAttribute("repairServiceForm", rsf);
		request.setAttribute("repairSearchForm", rsf);
		
		request.setAttribute("toolsList", rlBo.getToolsList(rsf.getRepairNo()));

		//取此维修单的附件
		request.setAttribute("repairAttachment", rlBo.getRepairAttachment(new Long(repairNo)));
		
		
		return forward;
	}
	

}
