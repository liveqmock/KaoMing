package com.dne.sie.stock.action;

//Java ������
//import java.util.ArrayList;

//Java ��չ��
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.dne.sie.common.tools.CommonSearch;
import com.dne.sie.stock.bo.StockInBo;
import com.dne.sie.stock.form.StockFlowForm;
import com.dne.sie.util.action.ControlAction;
//��������
//�Զ�����



/**
 * ������¼Action������
 * @author xt
 * @version 1.1.5.6
 * @see StockFlowAction.java <br>
 */
public class StockFlowAction extends ControlAction{
	

	/**
	* �����ˮ �б�ҳ��
	* @param request HttpServletRequest
	* @param form ������
	* @return ҳ��
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
	* ������ˮ �б�ҳ��
	* @param request HttpServletRequest
	* @param form ������
	* @return ҳ��
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
   * ��ˮ��ϸ
   * @param request
   * @param form
   * @return
   */
  public String flowDetail(HttpServletRequest request, ActionForm form) throws Exception {
		String forward = "stockFlowDetail";
		
		String id = request.getParameter("id");
		StockFlowForm sff = StockInBo.getInstance().flowDetail(new Long(id));
		request.setAttribute("stockFlowForm",sff);
		request.setAttribute("createByName", CommonSearch.getInstance().findUserNameByUserId(sff.getCreateBy()));
		
		return forward;
  }
  
  
  public String updateInvoiceNo(HttpServletRequest request, ActionForm form) throws Exception {
	  StockFlowForm sff = (StockFlowForm)form;
	  HttpSession session = request.getSession();
	  Long userId = (Long) session.getAttribute("userId");
	  sff.setUpdateBy(userId);
	  sff.setUpdateDate(new Date());
		
	  StockInBo.getInstance().updateInvoiceNo(sff);
	  request.setAttribute("businessFlag", "updateInvoiceNo");
	  request.setAttribute("tag", "1");
	  
	  return "resultMessage";
  }
  
  /**
	* ������ӡ �б�ҳ��
	* @param request HttpServletRequest
	* @param form ������
	* @return ҳ��
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
	 * ��������ݵ���
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
				strTitle="������ˮ��Ϣ";
			}else if("I".equals(flowType)){
				strSource=sib.stockFlowTxt(sff);
				strTitle="�����ˮ��Ϣ";
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
