package com.dne.sie.reception.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.dne.sie.common.tools.Operate;
import com.dne.sie.reception.bo.SaleInfoBo;
import com.dne.sie.reception.form.SaleInfoForm;
import com.dne.sie.util.action.ControlAction;

public class SalePartsApproveAction extends ControlAction{
	
	/**
	 * ����������б�
	 * @param request
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public String iwPartsList(HttpServletRequest request, ActionForm form) throws Exception{
		
		SaleInfoForm pif=(SaleInfoForm)form;
		
		pif.setSaleStatus("B");
		SaleInfoBo pib = SaleInfoBo.getInstance();
		request.setAttribute("saleList",pib.list(pif));
	
		
		return  "iwPartsList";
	}
	
	/**
	 * �����������ϸ
	 * @param request
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public String iwPartsDetail(HttpServletRequest request,ActionForm form) throws Exception{

		String saleNo = request.getParameter("saleNo");
		//������Ϣ��
		SaleInfoBo bo = SaleInfoBo.getInstance();
		SaleInfoForm sif = bo.findById(saleNo);
		
		request.setAttribute("detailList", bo.detailList(saleNo));
		
		request.setAttribute("salesInfoForm", sif);

		return "iwPartsDetail";
	}
	
	/**
	 * �������
	 * @param request
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public String iwPartApprove(HttpServletRequest request,ActionForm form) throws Exception{

		SaleInfoForm sif=(SaleInfoForm)form;
		sif.setUpdateBy((Long)request.getSession().getAttribute("userId"));
		
		//������Ϣ��
		SaleInfoBo bo = SaleInfoBo.getInstance();

		request.setAttribute("businessFlag", "iwPartApprove");
		request.setAttribute("tag", bo.iwPartApprove(sif)+"");
		
		return "resultMessage";
	}

}
