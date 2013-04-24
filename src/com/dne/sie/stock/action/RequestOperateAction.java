package com.dne.sie.stock.action;

//Java 基础类

//Java 扩展类
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

//第三方类
import org.apache.struts.action.ActionForm;
import org.apache.log4j.Logger;

//自定义类
import com.dne.sie.common.exception.VersionException;
import com.dne.sie.reception.form.SaleDetailForm;
import com.dne.sie.stock.bo.StockOutBo;
import com.dne.sie.util.action.ControlAction;

public class RequestOperateAction extends ControlAction{
	

	/**
	 * 销售领用出库列表
	 * @param request HttpServletRequest
	 * @param form ActionForm 表单数据
	 * @return 销售领用出库页面
	 */
	public String saleOutList(HttpServletRequest request, ActionForm form){
		String forward = "saleOutList";
	
		try{
			SaleDetailForm sdf=(SaleDetailForm)form;
			//已分配待领取
			sdf.setPartStatus("L");
			StockOutBo sib = StockOutBo.getInstance();
			request.setAttribute("saleList",sib.outOperateList(sdf));
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
	}
	

	   /**
	   * 出库确认
	   * @param request HttpServletRequest
	   * @param form  表单数据
	   * @return 页面
	   */	
	  public String stockOutConfirm(HttpServletRequest request,ActionForm form) {
		  String forward = "resultMessage";
		  int tag =-1;

		  try{
				HttpSession session=request.getSession();
				Long userId=(Long)session.getAttribute("userId");
				//String outStockRemark=request.getParameter("outStockRemark");
				String chkId = request.getParameter("ids");
				
				if(chkId!=null&&!chkId.equals("")){
					StockOutBo sob = StockOutBo.getInstance();
					tag = sob.stockOut(chkId,userId);
				}
		
		
			  request.setAttribute("tag",tag+"");
			  request.setAttribute("businessFlag","stockOutConfirm");

		  }catch(VersionException ve){
			  forward="versionErr";
		  }catch(Exception e){
			  e.printStackTrace();
		  }
		  return forward;
	  }
		  

}
