package com.dne.sie.logistic.action;

//Java 基础类
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;

import com.dne.sie.logistic.bo.AsnBo;
import com.dne.sie.logistic.form.AsnForm;
import com.dne.sie.util.action.ControlAction;



public class AsnListAction  extends ControlAction{

	

	/**
	 * 发货待审批列表
	 * @param request HttpServletRequest
	 * @param form ActionForm 表单数据
	 * @return 发货审批页面
	 */
	public String consignApproveList(HttpServletRequest request, ActionForm form){
		String forward = "consignApproveList";
	
		try{
			AsnForm af = (AsnForm)form;
			af.setAsnStatus("N");
			AsnBo ab = AsnBo.getInstance();
			request.setAttribute("asnApproveList", ab.asnList(af));
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
	}

	/**
	* 发货审批确认
	* @param request HttpServletRequest
	* @param form 表单数据
	* @return 页面
	*/
	public String consignApprove(HttpServletRequest request, ActionForm form) {
		String forward = "resultMessage";
		int tag=-1;
		try{
			HttpSession session = request.getSession();
			Long userId = (Long) session.getAttribute("userId");
			
			String flag = request.getParameter("flag");
			String asnNos = request.getParameter("ids");
			String remark=request.getParameter("approveRemark");
			
			AsnBo ab = AsnBo.getInstance();
			tag=ab.consignApprove(asnNos,flag,userId,remark);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			request.setAttribute("tag", tag + "");
			request.setAttribute("businessFlag", "consignApprove");
		
		}
		return forward;
	}
	

//	/**
//	* 审批结果确认
//	* @param request HttpServletRequest
//	* @param form 表单数据
//	* @return 页面
//	*/
//	public String consignAgain(HttpServletRequest request, ActionForm form) {
//		String forward = "resultMessage";
//		int tag=-1;
//		try{
//			HttpSession session = request.getSession();
//			Long userId = (Long) session.getAttribute("userId");
//			
//			String flag = request.getParameter("flag");
//			String asnNos = request.getParameter("ids");
//			String remark=request.getParameter("shippingRemark");
//			
//			AsnBo ab = AsnBo.getInstance();
//			tag=ab.consignAgain(asnNos,flag,userId,remark);
//			
//		}catch(Exception e){
//			e.printStackTrace();
//		}finally{
//			request.setAttribute("tag", tag + "");
//			request.setAttribute("businessFlag", "consignAgain");
//		
//		}
//		return forward;
//	}
	

	/**
	 * 审批结果记录列表(已废弃)
	 * @param request HttpServletRequest
	 * @param form ActionForm 表单数据
	 * @return 待发货页面
	 */
	public String asnResultList(HttpServletRequest request, ActionForm form){
		String forward = "asnResultList";
	
		try{
			AsnForm af = (AsnForm)form;
			AsnBo ab = AsnBo.getInstance();
			request.setAttribute("asnList", ab.asnList(af));
			request.setAttribute("asnStatus", request.getParameter("asnStatus"));
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
	}
	
	/**
	 * 发货记录列表
	 * @param request HttpServletRequest
	 * @param form ActionForm 表单数据
	 * @return 待发货页面
	 */
	public String asnList(HttpServletRequest request, ActionForm form){
		String forward = "asnList";
	
		try{
			AsnForm af = (AsnForm)form;
			AsnBo ab = AsnBo.getInstance();
			request.setAttribute("asnList", ab.asnList(af));
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
	}
	
	/**
	 * 出货零件明细
	 * @param request HttpServletRequest
	 * @param form ActionForm 表单数据
	 * @return 
	 */
	public String asnDetail(HttpServletRequest request,ActionForm form) {
		String forward = "asnDetail";
		try {
			String asnNo = request.getParameter("asnNo");
			
			AsnBo ab = AsnBo.getInstance();
			request.setAttribute("detailList", ab.saleDetailList(asnNo));
			request.setAttribute("asnForm", ab.findById(asnNo));
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return forward;
	}
	

	/**
	 * 出货单打印
	 * @param request HttpServletRequest
	 * @param form ActionForm 表单数据
	 * @return 
	 */
	public String asnPrint(HttpServletRequest request,ActionForm form) {
		String forward = "asnPrint";
		try {
			String asnNo = request.getParameter("asnNo");
			
			AsnBo ab = AsnBo.getInstance();
			request.setAttribute("detailList", ab.saleDetailList(asnNo));
			AsnForm af=ab.findById(asnNo);
			request.setAttribute("asnForm", af);
			request.setAttribute("payStatus", af.getPayStatus());
			request.setAttribute("billingStatus", af.getBillingStatus());
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return forward;
	}
	
}
