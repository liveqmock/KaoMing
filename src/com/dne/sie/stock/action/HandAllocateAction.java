package com.dne.sie.stock.action;

//Java ������
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//Java ��չ��
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//��������
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.log4j.Logger;

//�Զ�����
import com.dne.sie.common.exception.VersionException;
import com.dne.sie.reception.form.SaleDetailForm;
import com.dne.sie.stock.bo.StockOutBo;
import com.dne.sie.util.action.ControlAction;

/**
 * ��������ֹ���������ࡣ
 * @author xt
 * @version Version 1.1.5.6
 */
public class HandAllocateAction extends ControlAction{
	
	

	/**
	 * �ֹ����� �б�
	 * @param request HttpServletRequest
	 * @param form ActionForm ������
	 * @return ���۵���ϸҳ��
	 */
	public String requestList(HttpServletRequest request, ActionForm form){
		String forward = "requestList";
	
		try{
			SaleDetailForm sdf=(SaleDetailForm)form;
			StockOutBo sob = StockOutBo.getInstance();
			sdf.setPartStatus("H");	//������
			request.setAttribute("requestList",sob.requestList(sdf));
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
	}

	   /**
	   * �ֹ�����ȷ��
	   * @param request HttpServletRequest
	   * @param form  ������
	   * @return ҳ��
	   */	
	  public String manualAllcate(HttpServletRequest request,ActionForm form) {
		  String forward = "resultMessage";
		  int tag =-1;

		  try{
				String chkId = request.getParameter("ids");
				String[] idList = null;
				if(chkId!=null&&!chkId.equals("")){
					StockOutBo sob = StockOutBo.getInstance();
					idList = chkId.split(",");
					
					tag = sob.manualAllcate(idList);
				}
		
			  request.setAttribute("tag",tag+"");
			  request.setAttribute("businessFlag","manualAllcate");

		  }catch(VersionException ve){
			  forward="versionErr";
		  }catch(Exception e){
			  e.printStackTrace();
		  }
		  return forward;
	  }

}
