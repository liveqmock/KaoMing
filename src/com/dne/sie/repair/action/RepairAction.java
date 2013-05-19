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
	 * ��ά�ޡ�->��ά�޵���ѯ��
	 * @param request HttpServletRequest
	 * @param form ActionForm
	 * @return String ������Ҫforward���ĵ�ַ
	 */
	public String repairQueryList(HttpServletRequest request,ActionForm form) throws Exception{  
		String forward = "repairQueryList";
		
		RepairSearchForm rsForm = (RepairSearchForm) form;
		
		RepairListBo rlBo = RepairListBo.getInstance();
		request.setAttribute("repairQueryList",rlBo.getRepairQueryList(rsForm));
	
		return forward;
	}
	

	/**
	 * ��ά�ޡ�->��ά�޵���ѯ��->��ά�޵���ϸ��
	 * @param request HttpServletRequest
	 * @param form ActionForm
	 * @return String ������Ҫforward���ĵ�ַ
	 */
	public String repairQueryDetail(HttpServletRequest request,ActionForm form) throws Exception{ 
		String forward = "repairQueryCDetail";
		
		String repairNo = request.getParameter("repairNo");
		
		RepairListBo rlBo = RepairListBo.getInstance();
		RepairServiceForm rsf = rlBo.getRepairDetail(new Long(repairNo));
//		CustomerInfoForm rcif = rsf.getCustomInfoForm();
		
		request.setAttribute("repairServiceForm", rsf);
		request.setAttribute("repairSearchForm", rsf);


		//ȡ�������Ϣ�б�
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

		//ȡ��ά�޵��ĸ���
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
	 *	��ʼ������ά�ޡ�->��ά�޵Ǽǡ�
	 *	@param request HttpServletRequest
	 *	@param form ActionForm
	 *	@return 
	 */
	public String receiveInit(HttpServletRequest request, ActionForm form) throws Exception{
		String forward = "receiveInit";
		
		//������ʾ�¸�ά�޵���
		request.setAttribute("serviceSheetNo",FormNumberBuilder.findNewServiveSheetNo());
			
		return forward;
	}
	
	/**
	 * �÷�����ʾ�����б�ά�ޡ�->���绰��ϡ�
	 * @param request HttpServletRequest
	 * @param form ActionForm
	 * @return String ������Ҫforward���ĵ�ַ
	 */
	public String repairList(HttpServletRequest request,ActionForm form) throws Exception{ 
		String forward = "repairList";
		RepairSearchForm rsForm = (RepairSearchForm) form;
		rsForm.setCurrentStatus("DZ");		//������
		rsForm.setRepairProperites("repair");
		
		RepairListBo rlBo = RepairListBo.getInstance();
		request.setAttribute("repairList",rlBo.getRepairList(rsForm));
		return forward;
	}
	
	

	/**
	 * ��ά�ޡ�->���绰��ϡ�->��ά�޵���ϸ��
	 * @param request HttpServletRequest
	 * @param form ActionForm
	 * @return String ������Ҫforward���ĵ�ַ
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

		//ȡ�������Ϣ�б�
		request.setAttribute("partsList", rlBo.getRepairPartsList(rsf.getRepairNo()));
		request.setAttribute("loanList", rlBo.getloanPartsList(rsf.getRepairNo()));
		request.setAttribute("toolsList", rlBo.getToolsList(rsf.getRepairNo()));
		request.setAttribute("feeList", feeList);

		//ȡ��ά�޵��ĸ���
		request.setAttribute("repairAttachment", rlBo.getRepairAttachment(new Long(repairNo)));
		
		if("S".equals(status)){	//���������
			forward = "repairSaleDetail";
		}
		
		return forward;
	}
	
	
	/**
	 * �÷�����ʾ�����б�ά�ޡ�->��ά���ɹ���
	 * @param request HttpServletRequest
	 * @param form ActionForm
	 * @return String ������Ҫforward���ĵ�ַ
	 */
	public String repairDispatchList(HttpServletRequest request,ActionForm form) throws Exception{ 
		String forward = "repairDispatchList";
		RepairSearchForm rsForm = (RepairSearchForm) form;
		rsForm.setCurrentStatus("T");		//�������
		rsForm.setRepairProperites("repair");
		
		RepairListBo rlBo = RepairListBo.getInstance();
		request.setAttribute("repairList",rlBo.getRepairList(rsForm));
		return forward;
	}
	

	/**
	 * ��ά�ޡ�->��ά���ɹ���->����ϸ��
	 * @param request HttpServletRequest
	 * @param form ActionForm
	 * @return String ������Ҫforward���ĵ�ַ
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

		//ȡ�������Ϣ�б�
		request.setAttribute("partsList", rlBo.getRepairPartsList(rsf.getRepairNo(),"T"));
		request.setAttribute("loanList", rlBo.getloanPartsList(rsf.getRepairNo()));
		request.setAttribute("toolsList", rlBo.getToolsList(rsf.getRepairNo()));
//		request.setAttribute("feeList", feeList);

		//ȡ��ά�޵��ĸ���
		request.setAttribute("repairAttachment", rlBo.getRepairAttachment(new Long(repairNo)));
		
	
		
		return forward;
	}
	

	/**
	 * �÷�����ʾ�����б�ά�ޡ�->��ά�޷�����
	 * @param request HttpServletRequest
	 * @param form ActionForm
	 * @return String ������Ҫforward���ĵ�ַ
	 */
	public String repairReturnList(HttpServletRequest request,ActionForm form) throws Exception{ 
		String forward = "repairReturnList";
		RepairSearchForm rsForm = (RepairSearchForm) form;
		rsForm.setCurrentStatus("D");		//���ɹ�
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
	 * �÷�����ʾ�����б�ά�ޡ�->��ά�޷����� ->����ϸ��
	 * @param request HttpServletRequest
	 * @param form ActionForm
	 * @return String ������Ҫforward���ĵ�ַ
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

		//ȡ�������Ϣ�б�
		request.setAttribute("partsList", rlBo.getRepairPartsList(rsf.getRepairNo(),"return"));
		request.setAttribute("loanList", rlBo.getloanPartsList(rsf.getRepairNo(),"return"));
		request.setAttribute("toolsList", rlBo.getToolsList(rsf.getRepairNo(),"return"));

		//ȡ��ά�޵��ĸ���
		request.setAttribute("repairAttachment", rlBo.getRepairAttachment(new Long(repairNo)));
		
	
		
		return forward;
	}
	

	
	/**
	 * �÷�����ʾ�����б�ά�ޡ�->��ά�ޱ��桱
	 * @param request HttpServletRequest
	 * @param form ActionForm
	 * @return String ������Ҫforward���ĵ�ַ
	 */
	public String repairCompleteList(HttpServletRequest request,ActionForm form) throws Exception{ 
		String forward = "repairCompleteList";
		RepairSearchForm rsForm = (RepairSearchForm) form;
		rsForm.setCurrentStatus("report");		//ά�ޱ���
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
	 * ��ά�ޡ�->��ά�ޱ��桱->����ϸ��
	 * @param request HttpServletRequest
	 * @param form ActionForm
	 * @return String ������Ҫforward���ĵ�ַ
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
	
		//ȡ�������Ϣ�б�
		request.setAttribute("partsList", rlBo.getRepairPartsList(rsf.getRepairNo(),"complete"));
		request.setAttribute("loanList", rlBo.getloanPartsList(rsf.getRepairNo(),"complete"));
		request.setAttribute("toolsList", rlBo.getToolsList(rsf.getRepairNo(),"complete"));
	
		request.setAttribute("irisTree",rlBo.getIrisTree(rlBo.getRepairIrisList(rsf.getRepairNo())));

		//ȡ��ά�޵��ĸ���
		request.setAttribute("repairAttachment", rlBo.getRepairAttachment(new Long(repairNo)));
		
		if("approve".equals(flag)){
			forward = "repairEndApproveCDetail";
		}
		return forward;
	}
	
	public String repairEndApproveList(HttpServletRequest request,ActionForm form) throws Exception{ 
		String forward = "repairEndApproveList";
		RepairSearchForm rsForm = (RepairSearchForm) form;
		rsForm.setCurrentStatus("F");		//�޸��ύ
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
