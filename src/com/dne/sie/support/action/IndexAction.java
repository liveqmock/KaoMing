package com.dne.sie.support.action;


import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.dne.sie.maintenance.bo.StationBinBo;
import com.dne.sie.support.bo.IndexBo;
import com.dne.sie.util.action.ControlAction;

/**
 * ��ҳ����Action������
 * @author xt
 * @version 1.1.5.6
 * @see IndexAction.java <br>
 */
public class IndexAction extends ControlAction {
   
   
   /**
	 * ��ҳ��ʼ��ʾҳ��
	 * ������ʾ��1.��������
	 * @param request HttpServletRequest
	 * @param form ������
	 * @return ҳ��
	 */
   public String frameContent(HttpServletRequest request, ActionForm form){
	   String forward = "frameContent";
		try{
			IndexBo ibo=IndexBo.getInstance();
			request.setAttribute("orderPartNum",ibo.getOrderPartNum());
			request.setAttribute("stockPartNum",ibo.getStockPartNum());
//			request.setAttribute("stockDiffNum",ibo.getStockDiffNum());
			request.setAttribute("repairNum",ibo.getRepairNum());
			request.setAttribute("dipatchRemindNum",ibo.getDipatchRemindNum());
			request.setAttribute("logisticApproveNum",ibo.logisticApproveNum());
			
			request.setAttribute("partSaleNos",ibo.getPartSaleNos());
			String[] stockSupply = ibo.getStockSupplyParts();
			request.setAttribute("stockSupplyParts",stockSupply[0]);
			request.setAttribute("stockSupplyPartNames",stockSupply[1]);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
   }
   
   public String top(HttpServletRequest request, ActionForm form){
	   
	   return "top";
   }
   

	/**
	 * ��½
	 * 
	 * @param mapping 
	 * @param form 
	 * @param request 
	 * @param response 
	 * @return ��ҳ
	 */
	public String loginConfirm(HttpServletRequest request, ActionForm form){
		String forward = "indexFrame";
		try {
			request.getSession().setAttribute("binCodes",StationBinBo.getInstance().getAllBinCodes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return forward;	
	}
	
	
   

}