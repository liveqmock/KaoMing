package com.dne.sie.stock.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.dne.sie.common.exception.VersionException;
import com.dne.sie.maintenance.bo.StationBinBo;
import com.dne.sie.stock.bo.StockBackBo;
import com.dne.sie.stock.form.StockBackForm;
import com.dne.sie.util.action.ControlAction;

public class StockBackAction extends ControlAction {
	


	public String stockBackList(HttpServletRequest request, ActionForm form) throws Exception{
		
		StockBackForm sbf= (StockBackForm)form;
		StockBackBo sbo = StockBackBo.getInstance();
		request.setAttribute("stockBackList",sbo.stockBackList(sbf));
		sbf.setStockBackItemBak(sbf.getStockBackItem());
		
		return "stockBackList";
	} 

	public String stockBackOperate(HttpServletRequest request, ActionForm form) throws Exception{
		String forward="resultMessage";
		try{
			StockBackForm sbf= (StockBackForm)form;
			Long userId = (Long)request.getSession().getAttribute("userId");
			
			StockBackBo sbo = StockBackBo.getInstance();
			
			int tag = sbo.stockBackOperate(sbf,userId);
			request.setAttribute("tag", tag+"");
			request.setAttribute("businessFlag", "stockBackOperate");
		}catch(VersionException ve){
			  forward="versionErr";
		}catch(Exception e){
			  throw e;
		}
		return forward;
	} 
}
