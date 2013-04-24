package com.dne.sie.stock.action;

//Java ������
//import java.util.ArrayList;

//Java ��չ��
import javax.servlet.http.HttpServletRequest;


//��������
import org.apache.struts.action.ActionForm;


//�Զ�����
import com.dne.sie.util.action.ControlAction;
import com.dne.sie.stock.bo.StockOutBo;
import com.dne.sie.stock.form.StockInfoForm;


/**
 * �������Action������
 * @author xt
 * @version 1.1.5.6
 * @see StockOutOperateAction.java <br>
 */
public class StockOutOperateAction extends ControlAction{


	/**
	* ���������� �б�ҳ��
	* @param request HttpServletRequest
	* @param form ������
	* @return ҳ��
	*/
	public String stockAdjustOutList(HttpServletRequest request, ActionForm form) {
		String forward = "stockAdjustOutList";
		try{
			StockInfoForm sif=(StockInfoForm)form;
			
			StockOutBo sob = StockOutBo.getInstance();
			request.setAttribute("stockInfoList",sob.adjustOutList(sif));
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
	}
	
	/**
	 * ���������� ȷ��
	 * @param request HttpServletRequest
	 * @param form ������
	 * @return ҳ��
	 */
	public String stockAdjustOutConfirm(HttpServletRequest request, ActionForm form) {
		String forward = "resultMessage";
	
		int tag =-1;
		try{
			
			String strIds= request.getParameter("ids");
			String outNum = request.getParameter("adOutNum");
			String outRemark = request.getParameter("adOutRemart");
			String customerName = request.getParameter("customerName");
			String[] outNums = outNum.split(",");
			String[] outRemarks = outRemark.split(",");
			
			StockOutBo sob = StockOutBo.getInstance();
	 		if(strIds!=null){
				tag = sob.ajustOut(strIds,outNums,outRemarks,customerName);
	 		}
			
	   }catch(Exception e){
		   e.printStackTrace();
	   }finally{
			request.setAttribute("tag", tag + "");
			request.setAttribute("businessFlag", "stockAdjustOutConfirm");
		}
	   return forward;
	}
	

	
	public String stockTaxAdjustOutList(HttpServletRequest request, ActionForm form) throws Exception{
		
		StockInfoForm sif=(StockInfoForm)form;
		
		StockOutBo sob = StockOutBo.getInstance();
		request.setAttribute("stockInfoList",sob.adjustOutTaxList(sif));
		
		return "stockTaxAdjustOutList";
	}
	
	public String stockAdjustTaxOutConfirm(HttpServletRequest request, ActionForm form) {
		String forward = "resultMessage";
	
		int tag =-1;
		try{
			
			String strIds= request.getParameter("ids");
			String outNum = request.getParameter("adOutNum");
			String adOutCost = request.getParameter("adOutCost");
			
			String[] outNums = outNum.split(",");
			String[] adOutCosts = adOutCost.split(",");
			
			StockOutBo sob = StockOutBo.getInstance();
	 		if(strIds!=null){
				tag = sob.ajustTaxOut(strIds,outNums,adOutCosts);
	 		}
			
	   }catch(Exception e){
		   e.printStackTrace();
	   }finally{
			request.setAttribute("tag", tag + "");
			request.setAttribute("businessFlag", "stockAdjustTaxOutConfirm");
		}
	   return forward;
	}
	
	
	
	
}
