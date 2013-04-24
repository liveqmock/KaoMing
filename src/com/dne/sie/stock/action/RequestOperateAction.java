package com.dne.sie.stock.action;

//Java ������

//Java ��չ��
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

//��������
import org.apache.struts.action.ActionForm;
import org.apache.log4j.Logger;

//�Զ�����
import com.dne.sie.common.exception.VersionException;
import com.dne.sie.reception.form.SaleDetailForm;
import com.dne.sie.stock.bo.StockOutBo;
import com.dne.sie.util.action.ControlAction;

public class RequestOperateAction extends ControlAction{
	

	/**
	 * �������ó����б�
	 * @param request HttpServletRequest
	 * @param form ActionForm ������
	 * @return �������ó���ҳ��
	 */
	public String saleOutList(HttpServletRequest request, ActionForm form){
		String forward = "saleOutList";
	
		try{
			SaleDetailForm sdf=(SaleDetailForm)form;
			//�ѷ������ȡ
			sdf.setPartStatus("L");
			StockOutBo sib = StockOutBo.getInstance();
			request.setAttribute("saleList",sib.outOperateList(sdf));
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
	}
	

	   /**
	   * ����ȷ��
	   * @param request HttpServletRequest
	   * @param form  ������
	   * @return ҳ��
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
