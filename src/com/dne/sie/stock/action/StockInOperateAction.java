package com.dne.sie.stock.action;

//Java ������import
//import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;

import com.dne.sie.common.exception.IllegalPoException;
import com.dne.sie.stock.bo.StockInBo;
import com.dne.sie.util.action.ControlAction;


/**
 * ������Action������
 * @author xt
 * @version 1.1.5.6
 * @see StockOutOperateAction.java <br>
 */
public class StockInOperateAction extends ControlAction{


	/**
	* �ջ����ҳ��
	* @param request HttpServletRequest
	* @param form ������
	* @return ҳ��
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
	 * �ջ����ȷ��
	 * @param request HttpServletRequest
	 * @param form ActionForm ������
	 * @return �ջ����ҳ��
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
			String[] receiveNum = request.getParameterValues("receiveNum"); //ʵ������
			String[] perCost = request.getParameterValues("perCost"); //ʵ�ʵ���(RMB)
			String[] orderDollar = request.getParameterValues("orderDollar"); //��������($)
			String[] freightTW = request.getParameterValues("freightTW"); //̨���˷�(RMB)
			String[] invoiceNo = request.getParameterValues("invoiceNo"); //̨���˷�(RMB)
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
//	* ��������� �б�ҳ��
//	* @param request HttpServletRequest
//	* @param form ������
//	* @return ҳ��
//	*/
//   public String stockAdjustInList(HttpServletRequest request, ActionForm form) {
//		String forward = "stockAdjustInList";
//		
//		try{
//			StockFlowForm sff=(StockFlowForm)form;
//			//�������
//			String[] days=Operate.getDayOfWeek();
//			sff.setInOutDate1(days[0]);
//			sff.setInOutDate2(days[1]);
//			//���
//			sff.setFlowType("I");
//			//���
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
//   * ���������ȷ��
//   * @param request HttpServletRequest
//   * @param form  ������
//   * @return ҳ��
//   */	
//	  public String stockAdjustInConfirm(HttpServletRequest request,ActionForm form) {
//		  
//		  int tag =-1;
//
//		  try{
//			  StockFlowForm sff=(StockFlowForm)form;
//			  //���
//			  sff.setFlowType("I");
//			  //�����
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
