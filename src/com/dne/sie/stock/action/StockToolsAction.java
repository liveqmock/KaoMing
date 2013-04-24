package com.dne.sie.stock.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.dne.sie.stock.bo.StockToolsBo;
import com.dne.sie.stock.form.StockToolsInfoForm;
import com.dne.sie.util.action.ControlAction;

/**
 * 库存工具信息Action处理类
 * @author xt
 * @version 1.0.0.0
 * @see StockToolsAction.java <br>
 */
public class StockToolsAction extends ControlAction {
	
	
	/**
	 * 库存信息 查询
	 * @param request
	 * @param form
	 * @return 页面
	 */
	public String stockToolsList(HttpServletRequest request, ActionForm form) throws Exception{
		
		StockToolsInfoForm siform= (StockToolsInfoForm)form;
		StockToolsBo sbo = StockToolsBo.getInstance();
		request.setAttribute("stockToolsInfoForm",siform);
		request.setAttribute("stockToolsList",sbo.stockToolsList(siform));
		
		return "stockToolsList";
	}
	
	public String toolsAdjustInDetail(HttpServletRequest request, ActionForm form) throws Exception{
		
		
		
		return "toolsAdjustInDetail";
	}
	
	public String toolsInOperate(HttpServletRequest request, ActionForm form) throws Exception{
		
		StockToolsInfoForm siform= (StockToolsInfoForm)form;
		Long userId = (Long)request.getSession().getAttribute("userId");
		siform.setStockStatus("A");
		siform.setSkuType("T");
		siform.setCreateBy(userId);
		siform.setCreateDate(new Date());
		
		StockToolsBo sbo = StockToolsBo.getInstance();
		
		int tag = sbo.toolsInOperate(siform);
		
		request.setAttribute("tag", tag + "");
		request.setAttribute("businessFlag", "toolsInOperate");
		
		return "resultMessage";
	}
	
	

}
