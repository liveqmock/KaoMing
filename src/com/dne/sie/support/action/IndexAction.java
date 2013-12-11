package com.dne.sie.support.action;


import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.dne.sie.maintenance.bo.StationBinBo;
import com.dne.sie.support.bo.IndexBo;
import com.dne.sie.util.action.ControlAction;

/**
 * 首页管理Action处理类
 * @author xt
 * @version 1.1.5.6
 * @see IndexAction.java <br>
 */
public class IndexAction extends ControlAction {
   
   
   /**
	 * 首页初始显示页面
	 * 包括显示：1.待办事宜
	 * @param request HttpServletRequest
	 * @param form 表单数据
	 * @return 页面
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
	 * 登陆
	 * 
	 * @param mapping 
	 * @param form 
	 * @param request 
	 * @param response 
	 * @return 首页
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