package com.dne.sie.stock.action;

//Java 基础类
//import java.util.ArrayList;

//Java 扩展类
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//第三方类
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

//自定义类
import com.dne.sie.stock.bo.StockInBo;
import com.dne.sie.stock.form.StockFlowForm;
import com.dne.sie.util.action.ControlAction;



/**
 * 出入库记录Action处理类
 * @author xt
 * @version 1.1.5.6
 * @see StockFlowAction.java <br>
 */
public class StockFlowAction extends ControlAction{
	

	/**
	* 入库流水 列表页面
	* @param request HttpServletRequest
	* @param form 表单数据
	* @return 页面
	*/
   public String stockInFlowList(HttpServletRequest request, ActionForm form) {
		String forward = "stockInFlowList";
		
		try{
			StockFlowForm sff=(StockFlowForm)form;
			sff.setFlowType("I");
			StockInBo sio = StockInBo.getInstance();
			request.setAttribute("stockFlowList",sio.stockInOutList(sff));
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
   }
   

	/**
	* 出库流水 列表页面
	* @param request HttpServletRequest
	* @param form 表单数据
	* @return 页面
	*/
  public String stockOutFlowList(HttpServletRequest request, ActionForm form) {
		String forward = "stockOutFlowList";
		
		try{
			StockFlowForm sff=(StockFlowForm)form;
			sff.setFlowType("O");
			StockInBo sio = StockInBo.getInstance();
			request.setAttribute("stockFlowList",sio.stockInOutList(sff));
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
  }
  
  /**
	* 出入库打印 列表页面
	* @param request HttpServletRequest
	* @param form 表单数据
	* @return 页面
	*/
	public String stockInOutPrint(HttpServletRequest request, ActionForm form) {
		String forward = null;

		try{
			String chkId = request.getParameter("ids");
			String flowType = request.getParameter("flowType");
			if("I".equals(flowType)){
				forward = "stockInPrint";
			}else{
				forward = "stockOutPrint";
			}
			if(chkId!=null&&!chkId.equals("")){
				StockInBo sio = StockInBo.getInstance();
				request.setAttribute("printList",sio.getFlowPrint(chkId));
			}

		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
	}
   

	/**
	 * 出入库数据导出
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return
	 */
	public void stockFlowFile(ActionMapping mapping,ActionForm form,
		HttpServletRequest request,HttpServletResponse response) {
		
		try {
			StockFlowForm sff=(StockFlowForm)form;
			String flowType =sff.getFlowType();
			StockInBo sib = StockInBo.getInstance();
			
			String strSource="",strTitle="";
			if("O".equals(flowType)){
				strSource=sib.stockFlowTxt(sff);
				strTitle="出库流水信息";
			}else if("I".equals(flowType)){
				strSource=sib.stockFlowTxt(sff);
				strTitle="入库流水信息";
			}

			if(strSource!=null){ 
				byte readFromFile[] = strSource.getBytes("GBK");
				
				//response.setContentType("text/html;charset=GBK");
				//response.setCharacterEncoding("GBK");
				response.setHeader("Content-disposition", "attachment; filename="+
					java.net.URLEncoder.encode(strTitle,"utf-8")+".xls");
				response.setBufferSize(readFromFile.length + 4096);
				response.getOutputStream().write(readFromFile);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
   
}
