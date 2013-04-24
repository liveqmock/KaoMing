package com.dne.sie.support.action;

//Java 基础类
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.dne.sie.common.tools.EscapeUnescape;
import com.dne.sie.support.bo.AccountBo;
import com.dne.sie.support.form.SubjectTreeForm;
import com.dne.sie.util.action.ControlAction;

/**
 * 对账单Action处理类
 * @author xt
 * @version 1.1.5.6
 * @see AccountAction.java <br>
 */
public class AccountAction  extends ControlAction{
	

	/**
	* 科目树frame
	* @param request HttpServletRequest
	* @param form 表单数据
	* @return 页面
	*/
	public String subjectTree(HttpServletRequest request, ActionForm form) {
		
		return "treeFrame";
	}

	/**
	 * 科目树初始化页面
	 * @param request HttpServletRequest
	 * @param form 表单数据
	 * @return 页面
	 */
	public String treeInit(HttpServletRequest request,ActionForm form) {
		String forward = "treeInit";	
		
		try{
			AccountBo abo = AccountBo.getInstance();
			
			request.setAttribute("tree",abo.writeTree("all"));
			
		}catch(Exception e){
			e.printStackTrace();
		}
	 	return forward;
	}

	/**
	 * 科目树新增页面
	 * @param request HttpServletRequest
	 * @param form 表单数据
	 * @return 页面
	 */
	public String addInit(HttpServletRequest request,ActionForm form) {
		String forward = "subjectEdit";	
		
		try{
			String parentId=request.getParameter("parentId");
			String treeName=request.getParameter("treeName");
			String layer=request.getParameter("layer");
			String title= AccountBo.transferLayer(layer);
			
			if(treeName!=null&&!"".equals(treeName)){
				char split1=0x0001;
				treeName=EscapeUnescape.unescape(treeName.replaceAll("@","%u"));
			}
			
			
			request.setAttribute("parentId",parentId);
			request.setAttribute("treeName",treeName);
			request.setAttribute("layer",layer);
			request.setAttribute("title",title);
			
			request.setAttribute("flag","init");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	 	return forward;
	}
	

	/**
	 * 科目树修改和详细的显示页面
	 * @param request HttpServletRequest
	 * @param form  表单数据
	 * @return 页面
	 */	
	public String subDetail(HttpServletRequest request,ActionForm form) {
		String forward = "subjectEdit";

		try{
			String strId = request.getParameter("id");
			
			AccountBo abo = AccountBo.getInstance();
			SubjectTreeForm stf = abo.findById(new Long(strId));
			
			request.setAttribute("subjectTreeForm",stf);	
			request.setAttribute("state","detail");
			
			request.setAttribute("parentId",stf.getParentId().toString());
			request.setAttribute("subId",stf.getSubjectId().toString());
			request.setAttribute("treeName",stf.getSubjectName());
			request.setAttribute("title",AccountBo.transferLayer(stf.getLayer().toString()));
			request.setAttribute("reportFlag",stf.getReportFlag().toString());
			
			request.setAttribute("flag","modify");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
	}


	   
	/**
	 * 科目添加数据
	 * @param request HttpServletRequest
	 * @param form  表单数据
	 * @return 页面
	 */	
	public String subAdd(HttpServletRequest request,ActionForm form) {
		String forward = "subAlert";
		int tag =-1;
	
		try{
			SubjectTreeForm stf=(SubjectTreeForm)form;
			
			AccountBo abo = AccountBo.getInstance();
			tag = abo.add(stf);
			request.setAttribute("tag",tag+"");
			request.setAttribute("state","add");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
	}

	

	/**
	 * 科目树修改数据
	 * @param request HttpServletRequest
	 * @param form  表单数据
	 * @return 页面
	 */	
	public String subModify(HttpServletRequest request,ActionForm form) {
		String forward = "subAlert";
		int tag=-1;
		try{
			SubjectTreeForm stf=(SubjectTreeForm)form;
			AccountBo abo = AccountBo.getInstance();
			
			SubjectTreeForm stfOld=abo.findById(stf.getSubjectId());
			stfOld.setSubjectName(stf.getSubjectName());
			stfOld.setReportFlag(stf.getReportFlag());
			stfOld.setRemark(stf.getRemark());
//			System.out.println("----getSubjectName="+stfOld.getSubjectName());
			
			tag=abo.modify(stfOld);
			request.setAttribute("tag",tag+"");
			request.setAttribute("state","modify");
			request.setAttribute("id",stf.getSubjectId().toString());
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
	}
	

	/**
	 * 科目记录删除
	 * @param request HttpServletRequest
	 * @param form  表单数据
	 * @return 页面
	 */	
	public String subDelete(HttpServletRequest request,ActionForm form) {
		String forward = "subAlert";
		int tag =-1;
		try{
			String strId = request.getParameter("id");
			
			AccountBo abo = AccountBo.getInstance();
			
			tag = abo.deleteSubject(new Long(strId));
			
			request.setAttribute("tag",tag+"");
			request.setAttribute("state","delete");
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
	}	
	
	/**
	 * 科目记录删除
	 * @param request HttpServletRequest
	 * @param form  表单数据
	 * @return 页面
	 */	
	public String circleTree(HttpServletRequest request,ActionForm form) {
		String forward = "subAlert";
		
		try{
			String strId = request.getParameter("id");
			String flag = request.getParameter("flag");
			
			AccountBo abo = AccountBo.getInstance();
			
			int tag=abo.circleTree(new Long(strId),flag);
			request.setAttribute("tag",tag+"");
			request.setAttribute("state","order");
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
	}	
	
	

	/**
	 * 校验科目名称是否存在
	 * @param request HttpServletRequest
	 * @param form  表单数据
	 * @return 页面
	 */	
	public void ajaxChk(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
	
		try{
			String subId=request.getParameter("subId");
			String subName=request.getParameter("subName");
			if(subName!=null) subName=EscapeUnescape.unescape(subName);
			
			AccountBo abo = AccountBo.getInstance();
			if("null".equals(subId)) subId=null;
			boolean ifUse=abo.chkSubName(subId, subName);
	
			PrintWriter writer = response.getWriter();				  //将从数据库中取来的数据放入XML字符串中
			response.setContentType("text/xml");						  //将从数据库中取来的数据放入XML字符串中
			response.setHeader("Cache-Control", "no-cache");             //将从数据库中取来的数据放入XML字符串中
			writer.println("<xml>");
		
			writer.println("<ifUse>"+ifUse+"</ifUse>");
			
			writer.println("</xml>");
			writer.flush();
			writer.close();
	
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
	
	
}
