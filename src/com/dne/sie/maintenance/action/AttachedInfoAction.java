package com.dne.sie.maintenance.action;

//Java ������
//import java.util.List;

//Java ��չ��
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//��������
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

//�Զ�����
import com.dne.sie.util.action.ControlAction;
import com.dne.sie.maintenance.bo.AttachedInfoBo;

/**
 * ������ϢAction������
 * @author xt
 * @version 1.1.5.6
 */
public class AttachedInfoAction extends ControlAction {
	
	
	/**
	* ���� �б�ҳ��
	* @param request HttpServletRequest
	* @param form ������
	* @return ҳ��
	*/
   public String affixList(HttpServletRequest request, ActionForm form) throws Exception{
		String forward = "affixList";
		try{
			String foreignId=request.getParameter("saleDetailId");
			AttachedInfoBo aib = AttachedInfoBo.getInstance();
			
			request.setAttribute("affixList",aib.getAffixList(new Long(foreignId)));
			request.setAttribute("foreignId",foreignId);
			request.setAttribute("viewFlag",request.getParameter("viewFlag"));
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
   }
   
	
	/**
	 * ɾ��������Ϣ���е��ļ�
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 */	
	public void fileDel(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) {


		try{
			String attacheId=request.getParameter("attacheId");
			AttachedInfoBo aibo = AttachedInfoBo.getInstance();
			String strXml="false";
			if(aibo.fileDel(attacheId,"virtualName")){
				strXml="true";
			}

			/*
			PrintWriter writer = response.getWriter();				  
			response.setContentType("text/xml");						 
			response.setHeader("Cache-Control", "no-cache");          
			writer.println("<xml>");
	
			writer.println("<ifUse>"+strXml+"</ifUse>");

			writer.println("</xml>");
			writer.flush();
			writer.close();
			*/
			
		}catch(Exception e){
			e.printStackTrace();
		}

	}
   


}

