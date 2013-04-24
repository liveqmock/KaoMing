package com.dne.sie.maintenance.action;

//Java ������
//import java.util.List;

//Java ��չ��
import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;


//��������
import org.apache.struts.action.ActionForm;
//import org.apache.struts.action.ActionMapping;

//�Զ�����
import com.dne.sie.util.action.ControlAction;
import com.dne.sie.maintenance.bo.SyscodeBo;
import com.dne.sie.maintenance.form.TsSystemCodeForm;


/**
 * �ֵ����ϢAction������
 * @author xt
 * @version 1.1.5.6
 */
public class SyscodeAction extends ControlAction {
	
	
	/**
	* �ֵ����Ϣ �б�ҳ��
	* @param request HttpServletRequest
	* @param form ������
	* @return ҳ��
	*/
   public String syscodeList(HttpServletRequest request, ActionForm form) throws Exception{
		String forward = "syscodeList";
		TsSystemCodeForm pif=(TsSystemCodeForm)form;
		SyscodeBo pib = SyscodeBo.getInstance();
		request.setAttribute("syscodeList",pib.list(pif));
		
		return forward;
   }
   
   /**
	* �ֵ����Ϣ ����ҳ��
	* @param request HttpServletRequest
	* @param form ������
	* @return ҳ��
	*/
  public String addSyscode(HttpServletRequest request, ActionForm form) throws Exception {
		String forward = "syscodeAdd";
		
		return forward;
  }
  
  /**
	* �ֵ����Ϣ �޸�ҳ��
	* @param request HttpServletRequest
	* @param form ������
	* @return ҳ��
	*/
  public String editSyscode(HttpServletRequest request, ActionForm form) {
		String forward = "syscodeEdit";
		try{
			String sysId=request.getParameter("ids");
			request.setAttribute("syscodeForm", SyscodeBo.getInstance().find(sysId));
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
  }

  /**
	* �ֵ����Ϣ �������
	* @param request HttpServletRequest
	* @param form ������
	* @return ҳ��
	*/
  public String insertSyscode(HttpServletRequest request, ActionForm form) {
		String forward = "syscodeList";
		try{
			TsSystemCodeForm pif=(TsSystemCodeForm)form;
			SyscodeBo.getInstance().add(pif);
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
  }
  

  /**
	* �ֵ����Ϣ �޸Ĳ���
	* @param request HttpServletRequest
	* @param form ������
	* @return ҳ��
	*/
  public String updateSyscode(HttpServletRequest request, ActionForm form) {
		String forward = "syscodeList";
		try{
			TsSystemCodeForm pif=(TsSystemCodeForm)form;
			SyscodeBo.getInstance().modify(pif);
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
  }
  

  /**
	* �ֵ����Ϣ ɾ������
	* @param request HttpServletRequest
	* @param form ������
	* @return ҳ��
	*/
  public String deleteSyscode(HttpServletRequest request, ActionForm form) {
		String forward = "syscodeList";
		try{
			String sysId=request.getParameter("ids");
			SyscodeBo.getInstance().delete(sysId);
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
  }


}

