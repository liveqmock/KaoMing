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
	* 客户表信息 列表页面
	* @param request HttpServletRequest
	* @param form 表单数据
	* @return 页面
	*/
   public String bugInfoList(HttpServletRequest request, ActionForm form) throws Exception{
		String forward = "bugInfoList";
		BugInfoForm pif=(BugInfoForm)form;
		BugInfoBo pib = BugInfoBo.getInstance();
		request.setAttribute("bugInfoList",pib.list(pif));
		
		return forward;
   }

  
  /**
	* 客户表信息 修改页面
	* @param request HttpServletRequest
	* @param form 表单数据
	* @return 页面
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
	* 客户表信息 插入操作
	* @param request HttpServletRequest
	* @param form 表单数据
	* @return 页面
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
	* 客户表信息 修改操作
	* @param request HttpServletRequest
	* @param form 表单数据
	* @return 页面
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
	* 客户表信息 删除操作
	* @param request HttpServletRequest
	* @param form 表单数据
	* @return 页面
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
