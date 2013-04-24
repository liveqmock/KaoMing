package com.dne.sie.maintenance.action;

//Java ������
//import java.util.List;

//Java ��չ��
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


//��������
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

//�Զ�����
import com.dne.sie.util.action.ControlAction;
import com.dne.sie.common.tools.EscapeUnescape;
import com.dne.sie.maintenance.bo.EmployeeInfoBo;
import com.dne.sie.maintenance.form.EmployeeInfoForm;


/**
 * Ա����ϢAction������
 * @author xt
 * @version 1.1.5.6
 */
public class EmployeeInfoAction extends ControlAction {
	
	
	/**
	* Ա������Ϣ �б�ҳ��
	* @param request HttpServletRequest
	* @param form ������
	* @return ҳ��
	*/
   public String employeeList(HttpServletRequest request, ActionForm form) throws Exception{
		String forward = "employeeList";
		EmployeeInfoForm pif=(EmployeeInfoForm)form;
		EmployeeInfoBo pib = EmployeeInfoBo.getInstance();
		request.setAttribute("employeeList",pib.list(pif));
		
		return forward;
   }
   
   /**
	* Ա������Ϣ ����ҳ��
	* @param request HttpServletRequest
	* @param form ������
	* @return ҳ��
	*/
  public String employeeInit(HttpServletRequest request, ActionForm form) throws Exception {
		String forward = "employeeInit";
		request.setAttribute("flag","init");
		return forward;
  }
  
  /**
	* Ա������Ϣ �޸�ҳ��
	* @param request HttpServletRequest
	* @param form ������
	* @return ҳ��
	*/
  public String employeeEdit(HttpServletRequest request, ActionForm form) {
		String forward = "employeeEdit";
		try{
			String sysId=request.getParameter("ids");
			request.setAttribute("employeeInfoForm", EmployeeInfoBo.getInstance().find(new Long(sysId)));
			request.setAttribute("flag","edit");
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
  }

  /**
	* Ա������Ϣ �������
	* @param request HttpServletRequest
	* @param form ������
	* @return ҳ��
	*/
  public String insertEmployee(HttpServletRequest request, ActionForm form) {
		String forward = "resultMessage";
		try{

			HttpSession session = request.getSession();
			Long userId = (Long) session.getAttribute("userId");

			EmployeeInfoForm pif=(EmployeeInfoForm)form;
			pif.setCreateBy(userId);
			EmployeeInfoBo cibo = EmployeeInfoBo.getInstance();
			if(cibo.chkEmpName(pif.getEmployeeName())){
				int tag=cibo.add(pif);
				request.setAttribute("tag", tag + "");
				request.setAttribute("businessFlag", "saveEmployee");
			}else{
				request.setAttribute("idRepeat", "idRepeat");
				forward="employeeInit";
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
  }
  

  /**
	* Ա������Ϣ �޸Ĳ���
	* @param request HttpServletRequest
	* @param form ������
	* @return ҳ��
	*/
  public String updateEmployee(HttpServletRequest request, ActionForm form) {
		String forward = "resultMessage";
		try{
			EmployeeInfoForm pif=(EmployeeInfoForm)form;
			EmployeeInfoBo cibo = EmployeeInfoBo.getInstance();
			
			if(cibo.chkEmpName(pif.getEmployeeName(),pif.getEmployeeCode())){
				int tag=cibo.modify(pif);
				request.setAttribute("tag", tag + "");
				request.setAttribute("businessFlag", "saveEmployee");
			}else{
				request.setAttribute("idRepeat", "idRepeat");
				forward="employeeInit";
			}
		
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
  }
  

  /**
	* Ա������Ϣ ɾ������
	* @param request HttpServletRequest
	* @param form ������
	* @return ҳ��
	*/
  public String deleteEmployee(HttpServletRequest request, ActionForm form) {
		String forward = "resultMessage";
		try{
			String sysId=request.getParameter("ids");
			int tag=EmployeeInfoBo.getInstance().delete(sysId);
			request.setAttribute("tag", tag + "");
			request.setAttribute("businessFlag", "deleteEmployee");
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
	public void ajaxChkName(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		
		try{
			String empName=request.getParameter("empName");
			empName=EscapeUnescape.unescape(empName);
			
			EmployeeInfoBo ubo = EmployeeInfoBo.getInstance();
			String strXml="false";
			if(ubo.chkEmpName(empName)){
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

