package com.dne.sie.support.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.dne.sie.support.bo.BugInfoBo;
import com.dne.sie.support.form.BugInfoForm;
import com.dne.sie.support.form.BugQaFlowForm;
import com.dne.sie.util.action.ControlAction;

public class BugInfoAction extends ControlAction {
	
	

	/**
	* �ͻ�����Ϣ �б�ҳ��
	* @param request HttpServletRequest
	* @param form ������
	* @return ҳ��
	*/
   public String bugInfoList(HttpServletRequest request, ActionForm form) throws Exception{
		String forward = "bugInfoList";
		BugInfoForm pif=(BugInfoForm)form;
		BugInfoBo pib = BugInfoBo.getInstance();
		request.setAttribute("bugInfoList",pib.list(pif));
		
		return forward;
   }

  
  /**
	* �ͻ�����Ϣ �޸�ҳ��
	* @param request HttpServletRequest
	* @param form ������
	* @return ҳ��
	*/
  public String bugInfoEdit(HttpServletRequest request, ActionForm form) {
		String forward = "bugInfoEdit";
		try{
			String id=request.getParameter("ids");
			if(id!=null&&!id.isEmpty()){
				request.setAttribute("bugInfoForm", BugInfoBo.getInstance().find(new Long(id)));
				request.setAttribute("flag","edit");
				
				request.setAttribute("replyList", BugInfoBo.getInstance().getBugReplyList(new Long(id)));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
  }
  
  

  /**
	* �ͻ�����Ϣ �������
	* @param request HttpServletRequest
	* @param form ������
	* @return ҳ��
	*/
  public String insertBugInfo(HttpServletRequest request, ActionForm form) {
		String forward = "resultMessage";
		try{
			BugInfoForm pif=(BugInfoForm)form;
			pif.setStatus("A");
		    pif.setCreateBy((Long)request.getSession().getAttribute("userId"));
			BugInfoBo cibo = BugInfoBo.getInstance();
			int tag=cibo.add(pif);
			request.setAttribute("tag", tag + "");
			request.setAttribute("businessFlag", "saveBugInfo");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
  }
  

  /**
	* �ͻ�����Ϣ �޸Ĳ���
	* @param request HttpServletRequest
	* @param form ������
	* @return ҳ��
	*/
  public String updateBugInfo(HttpServletRequest request, ActionForm form) {
		String forward = "resultMessage";
		try{
			BugInfoForm pif=(BugInfoForm)form;
			BugInfoBo cibo = BugInfoBo.getInstance();
			pif.setUpdateBy((Long)request.getSession().getAttribute("userId"));
			pif.setUpdateDate(new Date());
			int tag=cibo.modify(pif);
			request.setAttribute("tag", tag + "");
			request.setAttribute("businessFlag", "saveBugInfo");
		
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
  }
  

  /**
	* �ͻ�����Ϣ ɾ������
	* @param request HttpServletRequest
	* @param form ������
	* @return ҳ��
	*/
  public String deleteBugInfo(HttpServletRequest request, ActionForm form) {
		String forward = "resultMessage";
		try{
			String sysId=request.getParameter("ids");
			int tag=BugInfoBo.getInstance().delete(sysId);
			request.setAttribute("tag", tag + "");
			request.setAttribute("businessFlag", "saveBugInfo");
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
  }
  
  
  public String replyAdd(HttpServletRequest request, ActionForm form) throws Exception {
		String forward = "bugInfoEdit";
	
		String id=request.getParameter("id");
		String reply=request.getParameter("reply");
		BugQaFlowForm qa = new BugQaFlowForm();
		qa.setBugId(new Long(id));
		qa.setDescription(reply);
		qa.setCreateBy((Long)request.getSession().getAttribute("userId"));
		
		BugInfoBo.getInstance().replyAdd(qa);
		
		
		if(id!=null&&!id.isEmpty()){
			request.setAttribute("bugInfoForm", BugInfoBo.getInstance().find(new Long(id)));
			request.setAttribute("flag","edit");
			
			request.setAttribute("replyList", BugInfoBo.getInstance().getBugReplyList(new Long(id)));
		}
		
		return forward;
  }


}
