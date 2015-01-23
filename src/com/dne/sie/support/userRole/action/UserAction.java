package com.dne.sie.support.userRole.action;

//Java 基础类
import java.util.ArrayList;
import java.util.Iterator;
import java.io.PrintWriter;
import java.util.Date;

//Java 扩展类
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//第三方类
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.log4j.Logger;

//自定义类
import com.dne.sie.maintenance.bo.EmployeeInfoBo;
import com.dne.sie.support.userRole.bo.UserBo;
import com.dne.sie.support.userRole.bo.RoleBo;
import com.dne.sie.support.userRole.form.UserForm;
//import com.dne.sie.common.tools.AtomRoleCheck;
import com.dne.sie.common.encrypt.MD5;
import com.dne.sie.common.tools.AtomRoleCheck;
import com.dne.sie.util.action.ControlAction;


/**
 * 用户管理Action类
 * @author xt
 * @version 1.1.5.6
 */
public class UserAction extends ControlAction {
    private static Logger logger = Logger.getLogger(UserAction.class);

    /**
     * 用户浏览页面
     * @param request HttpServletRequest
     * @param form 表单数据
     * @return 页面
     */
    public String userList(HttpServletRequest request, ActionForm form){
        String forward = "userList";

        try{
            UserForm uf=(UserForm)form;

            UserBo ubo = UserBo.getInstance();

            request.setAttribute("vtrData",ubo.list(uf));


        }catch(Exception e){
            e.printStackTrace();
        }
        return forward;
    }

    /**
     * 用户显示添加页面
     * @param request HttpServletRequest
     * @param form 表单数据
     * @return 页面
     */
    public String addInit(HttpServletRequest request,ActionForm form) throws Exception {
        String forward = "addInit";

        request.setAttribute("empList",EmployeeInfoBo.getInstance().getEmpSelectRegistList());
        request.setAttribute("roleList",RoleBo.getInstance().findAllRoleList());


        return forward;
    }


    /**
     * 添加用户数据
     * @param request HttpServletRequest
     * @param form  表单数据
     * @return 页面
     */
    public String userAdd(HttpServletRequest request,ActionForm form) {
        String forward = "resultMessage";
        int tag =-1;

        try{
            UserForm uf=(UserForm)form;
            HttpSession session=request.getSession();
            Long userId=(Long)session.getAttribute("userId");
            uf.setCreateBy(userId);
            uf.setCreateDate(new Date());

            UserBo ubo = UserBo.getInstance();


            tag = ubo.userAndDeptAdd(uf);


            if(tag==-2){
                forward="addInit";
                uf.setEmployeeCode("");
                request.setAttribute("userForm",uf);
                request.setAttribute("idRepeat","idRepeat");
            }else{
                request.setAttribute("tag",tag+"");
                request.setAttribute("businessFlag","userAdd");
            }


        }catch(Exception e){
            e.printStackTrace();
        }
        return forward;
    }


    /**
     * 用户记录删除
     * @param request HttpServletRequest
     * @param form  表单数据
     * @return 页面
     */
    public String userDelete(HttpServletRequest request,ActionForm form) {
        String forward = "resultMessage";
        int tag =-1;
        try{
            String strFlag = request.getParameter("flag")==null?"userDelete":request.getParameter("flag");

            String chkId = request.getParameter("id");

            if(chkId!=null&&!chkId.equals("")){
                HttpSession session=request.getSession();
                Long userId=(Long)session.getAttribute("userId");

                UserBo ubo = UserBo.getInstance();
                //逻辑删除，只是修改删除标志
                tag = ubo.modify(chkId,userId);

            }
            request.setAttribute("tag",tag+"");
            request.setAttribute("businessFlag",strFlag);


        }catch(Exception e){
            e.printStackTrace();
        }
        return forward;
    }

    /**
     * 用户修改和详细的初始界面
     * @param request HttpServletRequest
     * @param form  表单数据
     * @return 页面
     */
    public String userDetail(HttpServletRequest request,ActionForm form) {
        String forward = "userDetail";

        try{
            String strId = request.getParameter("id");

            UserBo ubo = UserBo.getInstance();
            UserForm uf=ubo.findUserAndGroup(strId);

            //uf.setDelFlag("0");

            request.setAttribute("userForm",uf);
            request.setAttribute("strUserId",strId);
            request.setAttribute("empList",EmployeeInfoBo.getInstance().getEmpSelectRegistList());
            request.setAttribute("roleAllList",RoleBo.getInstance().findAllRoleList());
            request.setAttribute("roleHadList",RoleBo.getInstance().findRoleIdList(uf.getId()));

        }catch(Exception e){
            e.printStackTrace();
        }
        return forward;
    }



    /**
     * 修改用户数据
     * @param request HttpServletRequest
     * @param form  表单数据
     * @return 页面
     */
    public String userModify(HttpServletRequest request,ActionForm form) {
        String forward = "resultMessage";
        int tag=-1;
        try{
            UserBo ubo = UserBo.getInstance();
            UserForm uf=(UserForm)form;


            HttpSession session=request.getSession();
            Long userId=(Long)session.getAttribute("userId");
            uf.setUpdateBy(userId);
            uf.setUpdateDate(new java.util.Date());

            tag=ubo.userAndDeptModify(uf);


            request.setAttribute("tag",tag+"");
            request.setAttribute("businessFlag","userModify");
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
    public String userSelect(HttpServletRequest request, ActionForm form) {
        String forward = "userSelect";
        ArrayList selectList=new ArrayList();

        try{
            String flag = request.getParameter("flag")==null?"":request.getParameter("flag");
            String id=request.getParameter("id");
            //System.out.println("---------flag="+flag);
            //System.out.println("---------id="+id);

            Iterator userIt=null;
            if(id!=null&&flag.equals("role")){
                RoleBo rbo=RoleBo.getInstance();
                userIt=rbo.findRoleUser(id).getUsers().iterator();
            }

            while(userIt!=null&&userIt.hasNext()){
                UserForm uf = (UserForm)userIt.next();
                if(uf.getDelFlag().intValue()==0){
                    String[] data = new String[5];
                    data[0] = uf.getId()+"";
                    data[1] = uf.getEmployeeCode();
                    data[2] = uf.getUserName();
                    data[3] = uf.getRoleName();
                    selectList.add(data);
                }
            }
            request.setAttribute("vtrData",selectList);
            request.setAttribute("flag",flag);
            request.setAttribute("deptRoleId",id);


        }catch(Exception e){
            e.printStackTrace();
        }

        return forward;
    }


    /**
     * 移除某些用户 从某部门或某权限
     * @param request HttpServletRequest
     * param form  表单数据
     * @return 页面
     */
    public String userMove(HttpServletRequest request, ActionForm form) {
        String forward = "resultMessage";
        int tag =-1;

        try{
            String flag = request.getParameter("flag");
            String chkId = request.getParameter("id");
            String deptRoleId = request.getParameter("deptRoleId");

            UserBo ubo = UserBo.getInstance();
            if(flag.equals("role")){
                Object obj=ubo.removeUser(chkId,flag,deptRoleId);
                if(obj!=null){
                    tag=1;
                }
            }

            request.setAttribute("tag",tag+"");
            request.setAttribute("businessFlag","userMove");

        }catch(Exception e){
            e.printStackTrace();
        }
        return forward;
    }





    /**
     * 根据条件显示权限-用户左右赋值框的内容
     * @param request HttpServletRequest
     * param form  表单数据
     * @return 页面
     */
    public String roleUser(HttpServletRequest request, ActionForm form) {
        String forward = "lrSelect";
        try{
            String strRoleId = request.getParameter("id");

            UserBo ubo = UserBo.getInstance();
            ArrayList[] userList=ubo.roleUser(strRoleId);

            request.setAttribute("userList",userList);

        }catch(Exception e){
            e.printStackTrace();
        }
        return forward;
    }


    /**
     * 校验用户id是否存在
     * @param request HttpServletRequest
     * @param form  表单数据
     * @return 页面
     */
    public void ajaxChk(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) {


        try{
            String empCode=request.getParameter("empCode");
            UserBo ubo = UserBo.getInstance();
            String strXml="false";
            if(ubo.chkName(empCode)){
                strXml="true";
            }

            PrintWriter writer = response.getWriter();				  //将从数据库中取来的数据放入XML字符串中
            response.setContentType("text/xml");						  //将从数据库中取来的数据放入XML字符串中
            response.setHeader("Cache-Control", "no-cache");             //将从数据库中取来的数据放入XML字符串中
            writer.println("<xml>");

            writer.println("<ifUse>"+strXml+"</ifUse>");

            writer.println("</xml>");
            writer.flush();
            writer.close();

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 校验用户password，由上级权限修改
     * @param request HttpServletRequest
     * @param form  表单数据
     * @return 页面
     */
    public void ajaxChkPw(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) {

        try{
            HttpSession session=request.getSession();
            Long userId=(Long)session.getAttribute("userId");
            String updateId=request.getParameter("id");

            String oldPw=request.getParameter("oldPw");
            String newPw=request.getParameter("newPw");
            UserBo ubo = UserBo.getInstance();
            String strXml="false";
            String strPw="0";


            if(AtomRoleCheck.checkRole(userId,"admin")
                    ||ubo.chkPw(new Long(updateId),oldPw)){
                strXml="true";
                MD5 md=new MD5();
                strPw=md.getMD5ofStr(newPw);
                ubo.modifyPw(updateId,userId,strPw);

            }

            PrintWriter writer = response.getWriter();				  //将从数据库中取来的数据放入XML字符串中
            response.setContentType("text/xml");						  //将从数据库中取来的数据放入XML字符串中
            response.setHeader("Cache-Control", "no-cache");             //将从数据库中取来的数据放入XML字符串中
            writer.println("<xml>");

            writer.println("<ifUse>"+strXml+"</ifUse>");

            writer.println("</xml>");
            writer.flush();
            writer.close();

        }catch(Exception e){
            e.printStackTrace();
        }

    }




    /**
     * 显示左边菜单
     * @param request HttpServletRequest
     * @param form 表单数据
     * @return 页面
     */
    public String leftCreat(HttpServletRequest request,ActionForm form) {
        String forward = "leftCreat";
        String typeId = request.getParameter("typeId");

        request.setAttribute("typeId",typeId);
        return forward;
    }




}
