package com.dne.sie.stock.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;

import com.dne.sie.common.exception.ReportException;
import com.dne.sie.stock.bo.StockInfoListBo;
import com.dne.sie.stock.bo.StockInfoListReportBo;
import com.dne.sie.stock.form.StockFlowForm;
import com.dne.sie.stock.form.StockInfoForm;
import com.dne.sie.util.action.ControlAction;


/**
 * �����ϢAction������
 * @author xt
 * @version 1.1.5.6
 * @see StockInfoListAction.java <br>
 */
public class StockInfoListAction extends ControlAction {
	
	/**
	 * �����Ϣ ��ѯ
	 * @param request
	 * @param form
	 * @return ҳ��
	 */
	public String stockInfoList(HttpServletRequest request, ActionForm form){
		
		String forward = "stockInfoList";
		try{
			
			//HttpSession session = request.getSession();
			//Long userId = (Long)session.getAttribute("userId");
			StockInfoForm siform= (StockInfoForm)form;
			StockInfoListBo sbo = StockInfoListBo.getInstance();
			request.setAttribute("stockInfoForm",siform);
			request.setAttribute("stockInfoList",sbo.stockInfoList(siform));
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
	}
	

	/**
	 * �����Ϣ��ϸ ��ѯ
	 * @param request
	 * @param form
	 * @return ҳ��
	 */
	public String stockInfoView(HttpServletRequest request, ActionForm form){
		
		String forward = "stockInfoView";
		try{
			StockInfoListBo sbo = StockInfoListBo.getInstance();
			StockInfoForm sif=new StockInfoForm();
			sif.setStuffNo(request.getParameter("stuffNo"));
			
			ArrayList[] tempList=(ArrayList[])sbo.skuViewList(sif);
			request.setAttribute("skuViewList",tempList[0]);
			request.setAttribute("skuInfoList",tempList[1]);
			
			if("tax".equals(request.getParameter("flag"))){
				forward = "stockTaxInfoView";
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
	}

	/**
	 * �����ˮ��Ϣ ��ѯ
	 * @param request
	 * @param form
	 * @return ҳ��
	 */
	public String stockFlowView(HttpServletRequest request, ActionForm form){
		
		String forward = "stockDetailFlow";
		try{
			StockInfoListBo sbo = StockInfoListBo.getInstance();
			StockFlowForm sff=new StockFlowForm();
			String stuffNo=request.getParameter("stuffNo");
			sff.setStuffNo(stuffNo);
			
			ArrayList[] tempList=(ArrayList[])sbo.skuFlowList(sff);
			request.setAttribute("skuFlowList",tempList[0]);
			request.setAttribute("skuInfoList",tempList[1]);
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
	}
	

	/**
	 * �����Ϣ ��������
	 * @param mapping	ActionMapping
	 * @param form		ActionForm
	 * @param request	HttpServletRequest
	 * @param response	HttpServletResponse
	 * @return ҳ��
	 */
	public String stockInfoListCreate(HttpServletRequest request, ActionForm form){
		String forward = "stockInfoList";
		try {
			StockInfoForm siform= (StockInfoForm)form;
			StockInfoListReportBo bo=StockInfoListReportBo.getInstance();
			String fileName=bo.createReportFile(siform);
			
			request.setAttribute("reportFileName",fileName);

		} catch (ReportException re) {
			forward="reportErr";
			request.setAttribute("reportErr",re.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return forward;
	}
	


	/**
	 * ������ݳ�ʼ��ҳ��
	 * @param request
	 * @param form
	 * @return ҳ��
	 */
	public String stockInitial(HttpServletRequest request, ActionForm form){
		
		return "stockInitial";
	}
	
	

	/**
	 * ������ݳ�ʼ������
	 * @param request
	 * @param form
	 * @return ҳ��
	 */
	public String stockInitialParse(HttpServletRequest request, ActionForm form){
		String forward = "stockParseMessage";
		try {
			String filePath = request.getParameter("filePath");
			HttpSession session = request.getSession();
			Long userId = (Long) session.getAttribute("userId");
			StockInfoListBo sibo = StockInfoListBo.getInstance();
			ArrayList dataList = sibo.stockInitialParse(filePath, userId);
			request.setAttribute("dataList", dataList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return forward;
	}

	/**
	 * �����ˮ��ʼ������
	 * @param request
	 * @param form
	 * @return ҳ��
	 */
	public String stockFlowInitialParse(HttpServletRequest request, ActionForm form){
		String forward = "stockFlowParseMessage";
		try {
			String filePath = request.getParameter("filePath");
			HttpSession session = request.getSession();
			Long userId = (Long) session.getAttribute("userId");
			StockInfoListBo sibo = StockInfoListBo.getInstance();
			ArrayList dataList = sibo.stockFlowInitialParse(filePath, userId);
			request.setAttribute("dataList", dataList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return forward;
	}

	/**
	 * ���������Ϣ��ʼ������
	 * @param request
	 * @param form
	 * @return ҳ��
	 */
	public String partInitialParse(HttpServletRequest request, ActionForm form){
		String forward = "partInitialParse";
		try {
			String filePath = request.getParameter("filePath");
			StockInfoListBo sibo = StockInfoListBo.getInstance();
			ArrayList dataList = sibo.partInitialParse(filePath);
			request.setAttribute("dataList", dataList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return forward;
	}
	
	
	public String stockTaxInfoList(HttpServletRequest request, ActionForm form){
		
		String forward = "stockTaxInfoList";
		try{
			StockInfoForm siform= (StockInfoForm)form;
			StockInfoListBo sbo = StockInfoListBo.getInstance();
			request.setAttribute("stockInfoForm",siform);
			request.setAttribute("stockInfoList",sbo.stockInfoTaxList(siform));
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
	}

}

