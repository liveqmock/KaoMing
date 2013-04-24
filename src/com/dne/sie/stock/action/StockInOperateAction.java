package com.dne.sie.stock.action;

//Java 基础类import
//import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;

import com.dne.sie.common.exception.IllegalPoException;
import com.dne.sie.stock.bo.StockInBo;
import com.dne.sie.util.action.ControlAction;


/**
 * 入库操作Action处理类
 * @author xt
 * @version 1.1.5.6
 * @see StockOutOperateAction.java <br>
 */
public class StockInOperateAction extends ControlAction{


	/**
	* 收货入库页面
	* @param request HttpServletRequest
	* @param form 表单数据
	* @return 页面
	*/
   public String orderInList(HttpServletRequest request, ActionForm form) {
		String forward = "orderInList";
		
		try{
			String orderNo=request.getParameter("orderNo");
			if(orderNo!=null){
				StockInBo sib = StockInBo.getInstance();
				request.setAttribute("orderInList",sib.orderInList(orderNo));
			}
			request.setAttribute("orderNo",orderNo);
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
   }
   
	/**
	 * 收货入库确认
	 * @param request HttpServletRequest
	 * @param form ActionForm 表单数据
	 * @return 收货入库页面
	 */
	public String orderInReceive(HttpServletRequest request, ActionForm form){
		String forward = "resultMessage";
		int tag=-1;
		try{
			HttpSession session = request.getSession();
			Long userId = (Long) session.getAttribute("userId");
			
			String orderNo = request.getParameter("orderNo");
			String transportMode = request.getParameter("transportMode");
			
			String[] poNo = request.getParameterValues("poNo"); //pk
			String[] receiveNum = request.getParameterValues("receiveNum"); //实收数量
			String[] perCost = request.getParameterValues("perCost"); //实际单价(RMB)
			String[] orderDollar = request.getParameterValues("orderDollar"); //订购单价($)
			String[] freightTW = request.getParameterValues("freightTW"); //台湾运费(RMB)
			String[] invoiceNo = request.getParameterValues("invoiceNo"); //台湾运费(RMB)
			String[][] para = {poNo,receiveNum,perCost,orderDollar,freightTW,invoiceNo};
			
			StockInBo sib = StockInBo.getInstance();
			tag=sib.orderInReceive(para,orderNo,userId,transportMode);
		}catch(IllegalPoException e1){
			request.setAttribute("errStuffNo", e1.getMessage());
			forward="IllegalPoException";
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			request.setAttribute("tag", tag + "");
			request.setAttribute("businessFlag", "orderInReceive");
		}
		return forward;
	}
   
   
   
//	/**
//	* 库存调整入库 列表页面
//	* @param request HttpServletRequest
//	* @param form 表单数据
//	* @return 页面
//	*/
//   public String stockAdjustInList(HttpServletRequest request, ActionForm form) {
//		String forward = "stockAdjustInList";
//		
//		try{
//			StockFlowForm sff=(StockFlowForm)form;
//			//本周入库
//			String[] days=Operate.getDayOfWeek();
//			sff.setInOutDate1(days[0]);
//			sff.setInOutDate2(days[1]);
//			//入库
//			sff.setFlowType("I");
//			//库调
//			sff.setFlowItem("K");
//			StockInBo sio = StockInBo.getInstance();
//			
//			request.setAttribute("stockAdjustInList",sio.stockInOutList(sff));
//			
//		    
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		return forward;
//   }
//   
//
//   
//   /**
//   * 库存调整入库确认
//   * @param request HttpServletRequest
//   * @param form  表单数据
//   * @return 页面
//   */	
//	  public String stockAdjustInConfirm(HttpServletRequest request,ActionForm form) {
//		  
//		  int tag =-1;
//
//		  try{
//			  StockFlowForm sff=(StockFlowForm)form;
//			  //入库
//			  sff.setFlowType("I");
//			  //库调入
//			  sff.setFlowItem("K");
//			  sff.setCreateDate(new Date());
//			  StockInBo sio = StockInBo.getInstance();
//			  tag= sio.stockAdjustIn(sff);
//			  request.setAttribute("tag",tag+"");
//			  
//		  }catch(Exception e){
//			  e.printStackTrace();
//		  }
//		  return this.stockAdjustInList(request, form);
//	  }
	  

   
		
}
