package com.dne.sie.support.userRole.action;

//Java 基础类
import java.util.ArrayList;

//Java 扩展类
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

//第三方类
import org.apache.struts.action.ActionForm;
import org.apache.log4j.Logger;

//自定义类
import com.dne.sie.common.tools.AtomRoleCheck;
import com.dne.sie.support.userRole.bo.FunctionBo;
import com.dne.sie.support.userRole.form.FunctionForm;
//import com.dne.sie.common.tools.AtomRoleCheck;
import com.dne.sie.util.action.ControlAction;



/**
 * 功能点管理Action处理类
 * @author xt
 * @version 1.1.5.6
 * @see FunctionAction.java <br>
 */
public class FunctionAction extends ControlAction {
    
	/**
	 * 显示功能树页面
	 * @param request HttpServletRequest
	 * @param form 表单数据
	 * @return 页面
	 */
	public String functionTree(HttpServletRequest request,ActionForm form) {
		String forward = "treeInit";	
		
		try{
			String roleId = request.getParameter("selectRole")==null?"":request.getParameter("selectRole");
			
			FunctionForm ff=new FunctionForm();
			ff.setSelectRole(roleId);
			
			HttpSession session=request.getSession();
			Long userId=(Long)session.getAttribute("userId");
						
			
			FunctionBo fbo = FunctionBo.getInstance();
			String[] functionTree=new String[5];
			
			ArrayList roleList=new ArrayList();
			
//			String[] strRoles={"admin"};
//			if(AtomRoleCheck.checkRoleContains(userId,strRoles)){
			if(true){
				roleList=fbo.findRoleList("all");
			
				functionTree=fbo.checkBoxTree(roleId,new Integer(0),"admin");
			}
//			else {
//				roleList=fbo.findRoleList("common");
//				functionTree=fbo.checkBoxTree(roleId,orgType,"common");
//			}
			
		
			request.setAttribute("functionTree",functionTree);
			request.setAttribute("roleList",roleList);
			request.setAttribute("functionForm",ff);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
	}
	
	/**
	 * 配置权限-功能点
	 * @param request HttpServletRequest
	 * @param form 表单数据
	 * @return 页面
	 */
	public String functionEdit(HttpServletRequest request,ActionForm form) {
		String forward = "functionEdit";	
		int tag=-1;
		try{
			String roleId = request.getParameter("selectRole")==null?"":request.getParameter("selectRole");
			
			String functionId = request.getParameter("id")==null?"":request.getParameter("id");
			
			FunctionBo fbo = FunctionBo.getInstance();
			if(!roleId.equals("")){
				tag=fbo.roleModify(roleId,functionId);
				
				//if(tag==1) fbo.modelBuild(roleId);
				request.setAttribute("roleId",roleId);
			}
				
			request.setAttribute("tag",tag+"");
			
	
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
	}
	
	
	
	
	
}
