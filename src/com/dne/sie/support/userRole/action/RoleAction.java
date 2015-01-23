package com.dne.sie.support.userRole.action;

//Java 基础类
import java.util.ArrayList;
import java.util.Date;

//Java 扩展类
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

//第三方类
import org.apache.struts.action.ActionForm;
import org.apache.log4j.Logger;

//自定义类
import com.dne.sie.support.userRole.bo.RoleBo;
import com.dne.sie.support.userRole.form.RoleForm;
//import com.dne.sie.common.tools.AtomRoleCheck;
import com.dne.sie.util.action.ControlAction;


/**
 * 权限管理Action处理类
 * @author xt
 * @version 1.1.5.6
 */
public class RoleAction extends ControlAction{


    /**
     * 权限浏览页面
     * @param request HttpServletRequest
     * @param form 表单数据
     * @return 页面
     */
    public String roleList(HttpServletRequest request, ActionForm form){
        String forward = "roleList";

        try{
            RoleForm rf=(RoleForm)form;
            RoleBo rbo = RoleBo.getInstance();

//			HttpSession session=request.getSession();
//			Long userId=(Long)session.getAttribute("userId");
//			if(AtomRoleCheck.checkRole(userId,"admin")){
//				rf.setRoleType("A");
//			}else{
//				rf.setRoleType("C");
//			}
            request.setAttribute("vtrData",rbo.list(rf));

        }catch(Exception e){
            e.printStackTrace();
        }
        return forward;
    }

    /**
     * 显示权限添加页面
     * @param request HttpServletRequest
     * @param form 表单数据
     * @return 页面
     */
    public String addInit(HttpServletRequest request,ActionForm form) {
        String forward = "addInit";

        try{



        }catch(Exception e){
            e.printStackTrace();
        }

        return forward;
    }


    /**
     * 添加权限数据
     * @param request HttpServletRequest
     * @param form  表单数据
     * @return 页面
     */
    public String roleAdd(HttpServletRequest request,ActionForm form) {
        String forward = "resultMessage";
        int tag =-1;

        try{
            RoleForm rf=(RoleForm)form;
//			rf.setRoleType("C");
            HttpSession session=request.getSession();
            Long userId=(Long)session.getAttribute("userId");
            rf.setCreateBy(userId);
            rf.setCreateDate(new Date());

            RoleBo rbo = RoleBo.getInstance();


            tag = rbo.add(rf);
            request.setAttribute("tag",tag+"");
            request.setAttribute("businessFlag","roleAdd");

        }catch(Exception e){
            e.printStackTrace();
        }
        return forward;
    }


    /**
     * 权限记录删除
     * @param request HttpServletRequest
     * @param form  表单数据
     * @return 页面
     */
    public String roleDelete(HttpServletRequest request,ActionForm form) {
        String forward = "resultMessage";
        int tag =-1;
        try{
            String chkId = request.getParameter("id");


            if(chkId!=null&&!chkId.equals("")){
                RoleBo ubo = RoleBo.getInstance();
                tag = ubo.deleteList(chkId);
            }
            request.setAttribute("tag",tag+"");
            request.setAttribute("businessFlag","roleDelete");

        }catch(Exception e){
            e.printStackTrace();
        }
        return forward;
    }

    /**
     * 权限修改和详细界面
     * @param request HttpServletRequest
     * @param form  表单数据
     * @return 页面
     */
    public String roleDetail(HttpServletRequest request,ActionForm form) {
        String forward = "roleDetail";

        try{
            String strId = request.getParameter("id");

            RoleBo rbo = RoleBo.getInstance();
            RoleForm rf=rbo.find(strId);


            request.setAttribute("roleForm",rf);
            request.setAttribute("state","detail");


        }catch(Exception e){
            e.printStackTrace();
        }
        return forward;
    }



    /**
     * 修改权限数据
     * @param request HttpServletRequest
     * @param form  表单数据
     * @return 页面
     */
    public String roleModify(HttpServletRequest request,ActionForm form) {
        String forward = "resultMessage";
        int tag=-1;
        try{
            RoleBo rbo = RoleBo.getInstance();
            RoleForm rf=(RoleForm)form;
            HttpSession session=request.getSession();
            Long userId=(Long)session.getAttribute("userId");
            rf.setUpdateBy(userId);
            rf.setUpdateDate(new java.util.Date());

            RoleForm oldRf=rbo.findById(rf.getId().toString());

            rf.setUsers(oldRf.getUsers());

            tag=rbo.modify(rf);
			/*
			if(tag==1&&!newRoleContain.equals(oldRoleContain)){
				FunctionBo fbo = new FunctionBo();
				fbo.modelBuild(rf.getId().toString());
			}*/
            request.setAttribute("tag",tag+"");
            request.setAttribute("businessFlag","roleModify");
        }catch(Exception e){
            e.printStackTrace();
        }
        return forward;
    }


    /**
     * 某用户拥有的权限集合
     * @param request HttpServletRequest
     * @param form  表单数据
     * @return 页面
     */
    public String userRole(HttpServletRequest request,ActionForm form) {
        String forward = "roleSelect";
        try{
            String strUserId = request.getParameter("id");

            HttpSession session=request.getSession();
            Integer orgType=(Integer)session.getAttribute("orgType");

            RoleBo rbo = RoleBo.getInstance();
            ArrayList[] roleList=new ArrayList[2];

            //if(AtomRoleCheck.checkRole(userId,"admin")){
            if(false){
                roleList=rbo.userRole(strUserId,orgType,"admin");
            }else{
                roleList=rbo.userRole(strUserId,orgType,"common");
            }
			
			/*else if(AtomRoleCheck.checkRole(userId,"station")&&orgCode!=null){
				roleList=rbo.userRole(strUserId,orgCode.toString());
			}else{
				forward = "roleFalse";
			}*/

            request.setAttribute("userList",roleList);

        }catch(Exception e){
            e.printStackTrace();
        }
        return forward;
    }


    /**
     * 查询某个部门和某个角色下的用户信息
     * @param request HttpServletRequest
     * @param form  表单数据
     * @return 页面
     */
//		  public String roleContainSelect(HttpServletRequest request, ActionForm form) {
//			String forward = "roleContainSelect";
//			
//			try{
//				String roleId = request.getParameter("roleId")==null?"":request.getParameter("roleId");
//				
//				RoleBo rbo = RoleBo.getInstance();
//				HttpSession session=request.getSession();
//				Long userId=(Long)session.getAttribute("userId");
//				Integer orgType=(Integer)session.getAttribute("orgType");
//				
//				ArrayList[] roleList=null;
//				
//				String[] strRoles={"admin","BASETABLE"};
//				//if(AtomRoleCheck.checkRoleContains(userId,strRoles)){
//				if(true){
//					roleList=rbo.roleContainSelect(roleId,0);
//				}else{
//					roleList=rbo.roleContainSelect(roleId,orgType.intValue());
//				}
//				
//				
//				request.setAttribute("roleList",roleList);
//			
//
//			}catch(Exception e){
//				e.printStackTrace();
//			}
//	  	
//			return forward;
//		  }


}
