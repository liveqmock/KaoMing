package com.dne.sie.logistic.action;

//Java ������
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;

import com.dne.sie.logistic.bo.AsnBo;
import com.dne.sie.logistic.form.AsnForm;
import com.dne.sie.util.action.ControlAction;



public class AsnListAction  extends ControlAction{

	

	/**
	 * �����������б�
	 * @param request HttpServletRequest
	 * @param form ActionForm ������
	 * @return ��������ҳ��
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
	* ��������ȷ��
	* @param request HttpServletRequest
	* @param form ������
	* @return ҳ��
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
//	* �������ȷ��
//	* @param request HttpServletRequest
//	* @param form ������
//	* @return ҳ��
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
	 * ���������¼�б�(�ѷ���)
	 * @param request HttpServletRequest
	 * @param form ActionForm ������
	 * @return ������ҳ��
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
	 * ������¼�б�
	 * @param request HttpServletRequest
	 * @param form ActionForm ������
	 * @return ������ҳ��
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
	 * ���������ϸ
	 * @param request HttpServletRequest
	 * @param form ActionForm ������
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
	 * ��������ӡ
	 * @param request HttpServletRequest
	 * @param form ActionForm ������
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
