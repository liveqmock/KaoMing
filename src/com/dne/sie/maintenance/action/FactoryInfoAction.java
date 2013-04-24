package com.dne.sie.maintenance.action;

//Java ������
//import java.util.List;

//Java ��չ��
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpServletResponse;


//��������
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
//import org.apache.struts.action.ActionMapping;

//�Զ�����
import com.dne.sie.util.action.ControlAction;
import com.dne.sie.maintenance.bo.FactoryInfoBo;
import com.dne.sie.maintenance.form.FactoryInfoForm;


/**
 * ������ϢAction������
 * @author xt
 * @version 1.1.5.6
 */
public class FactoryInfoAction extends ControlAction {
	
	
	/**
	* ���̱���Ϣ �б�ҳ��
	* @param request HttpServletRequest
	* @param form ������
	* @return ҳ��
	*/
   public String factoryList(HttpServletRequest request, ActionForm form) throws Exception{
		String forward = "factoryList";
		FactoryInfoForm pif=(FactoryInfoForm)form;
		FactoryInfoBo pib = FactoryInfoBo.getInstance();
		request.setAttribute("factoryList",pib.list(pif));
		
		return forward;
   }
   
   /**
	* ���̱���Ϣ ����ҳ��
	* @param request HttpServletRequest
	* @param form ������
	* @return ҳ��
	*/
  public String factoryInit(HttpServletRequest request, ActionForm form) throws Exception {
		String forward = "factoryInit";
		
		return forward;
  }
  
  /**
	* ���̱���Ϣ �޸�ҳ��
	* @param request HttpServletRequest
	* @param form ������
	* @return ҳ��
	*/
  public String factoryEdit(HttpServletRequest request, ActionForm form) {
		String forward = "factoryEdit";
		try{
			String sysId=request.getParameter("ids");
			request.setAttribute("factoryInfoForm", FactoryInfoBo.getInstance().find(sysId));
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
  }

  /**
	* ���̱���Ϣ �������
	* @param request HttpServletRequest
	* @param form ������
	* @return ҳ��
	*/
  public String insertFactory(HttpServletRequest request, ActionForm form) {
		String forward = "resultMessage";
		try{
			FactoryInfoForm pif=(FactoryInfoForm)form;
			FactoryInfoBo cibo = FactoryInfoBo.getInstance();
			if(cibo.chkFactoryId(pif.getFactoryId())){
				int tag=cibo.add(pif);
				request.setAttribute("tag", tag + "");
				request.setAttribute("businessFlag", "saveFactory");
			}else{
				forward="factoryInit";
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
  }
  

  /**
	* ���̱���Ϣ �޸Ĳ���
	* @param request HttpServletRequest
	* @param form ������
	* @return ҳ��
	*/
  public String updateFactory(HttpServletRequest request, ActionForm form) {
		String forward = "resultMessage";
		try{
			FactoryInfoForm pif=(FactoryInfoForm)form;
			FactoryInfoBo cibo = FactoryInfoBo.getInstance();
			int tag=cibo.modify(pif);
			request.setAttribute("tag", tag + "");
			request.setAttribute("businessFlag", "saveFactory");
		
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
  }
  

  /**
	* ���̱���Ϣ ɾ������
	* @param request HttpServletRequest
	* @param form ������
	* @return ҳ��
	*/
  public String deleteFactory(HttpServletRequest request, ActionForm form) {
		String forward = "resultMessage";
		try{
			String sysId=request.getParameter("ids");
			int tag=FactoryInfoBo.getInstance().delete(sysId);
			request.setAttribute("tag", tag + "");
			request.setAttribute("businessFlag", "deleteFactory");
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
  }

	/**
	 * У���û�id�Ƿ����
	 * @param request HttpServletRequest
	 * @param form  ������
	 * @return ҳ��
	 */	
	public void ajaxChkId(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		
		try{
			String custId=request.getParameter("custId");
			FactoryInfoBo ubo = FactoryInfoBo.getInstance();
			String strXml="false";
			if(ubo.chkFactoryId(custId)){
				strXml="true";
			}
		
			PrintWriter writer = response.getWriter();			
			response.setContentType("text/xml");					
			response.setHeader("Cache-Control", "no-cache");      
			writer.println("<xml>");
			
			writer.println("<ifUse>"+strXml+"</ifUse>");
		
			writer.println("</xml>");
			writer.flush();
			writer.close();
		
		}catch(Exception e){
			e.printStackTrace();
		}
	
	}

}

