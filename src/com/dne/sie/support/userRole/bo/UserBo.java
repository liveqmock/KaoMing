package com.dne.sie.support.userRole.bo;

//Java 基础类
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
//Java 扩展类

//第三方类
import org.apache.log4j.Logger;

//自定义类
import com.dne.sie.support.userRole.form.UserForm;
import com.dne.sie.support.userRole.form.RoleForm;

import com.dne.sie.support.userRole.queryBean.UserQuery;
//import com.dne.sie.common.tools.AtomRoleCheck;
import com.dne.sie.common.encrypt.MD5;
import com.dne.sie.util.bo.CommBo;


/**
 * 用户管理BO处理类
 * @author xt
 * @version 1.1.5.6
 */
public class UserBo extends CommBo {
    private static Logger logger = Logger.getLogger(UserBo.class);

    private static final UserBo INSTANCE = new UserBo();

    private UserBo(){
    }

    public static final UserBo getInstance() {
        return INSTANCE;
    }

    /**
     * 用户列表查询拼装
     * @param UserForm 查询条件
     * @return ArrayList 查询结果,封装String[]
     */
    public ArrayList list(UserForm user) {
        List dataList = null;
        ArrayList alData = new ArrayList();
        UserQuery uq = new UserQuery(user);

        int count=0;
        try {
            dataList=uq.doListQuery(user.getFromPage(),user.getToPage());

            count=uq.doCountQuery();

            for (int i=0;i<dataList.size();i++) {
                String[] data = new String[7];
                UserForm uf = (UserForm)dataList.get(i);
                uf.setRoleCodeAndName();
                data[0] = uf.getId()+"";
                data[1] = uf.getEmployeeCode();
                data[2] = uf.getUserName();
                data[3] = uf.getRoleName();
                data[4] = uf.getEmail();
                data[5] = uf.getPhone();
                data[6] = uf.getRemark();
                alData.add(data);

            }
            alData.add(0,count+"");

        } catch(Exception e) {
            e.printStackTrace();
        } finally {
        }
        return alData;
    }

    /**
     * 根据id查询该用户信息
     * @param String 用户表记录pk
     * @return 该用户Form
     */
    public UserForm find(String id) {
        UserForm uf =null;
        try {
            uf = (UserForm)this.getDao().findById(UserForm.class,new Long(id));

            uf.setRoleCodeAndName();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return uf;
    }


    /**
     * 根据id查询该用户信息，外关联部门信息
     * @param String 用户表记录pk
     * @return 该用户Form
     */
    public UserForm findUserAndGroup(String id) {
        UserForm uf =null;
        try {
            uf = (UserForm)this.getDao().findById(UserForm.class,new Long(id));

            uf.setRoleCodeAndName();

        } catch(Exception e) {
            e.printStackTrace();
        }
        return uf;
    }

    /**
     * 校验用户id(登录id)是否存在
     * @param String 输入的用户id
     * @return 该用户id是否可以输入
     */
    public boolean chkName(String empId) {
        boolean retBoo = false;

        try {
            Object obj=this.getDao().uniqueResult("select count(uf) from UserForm as uf where uf.employeeCode='"+empId+"'");
            int count=((Long)obj).intValue();

            if(count==0) retBoo=true;

        } catch(Exception e) {
            e.printStackTrace();
        }
        return retBoo;
    }


    /**
     * 校验用户password是否存在
     * @param Long 输入的用户id，String password
     * @return 该用户id是否可以输入
     */
    public boolean chkPw(Long userId,String pw) {
        boolean retBoo = false;

        try {
            MD5 md=new MD5();
            pw=md.getMD5ofStr(pw);
            Object obj=this.getDao().uniqueResult("select count(uf) from UserForm as uf where uf.id="+userId+" and uf.password='"+pw+"'");
            int count=((Long)obj).intValue();

            if(count==1) retBoo=true;

        } catch(Exception e) {
            e.printStackTrace();
        }
        return retBoo;
    }

    /**
     * 修改用户自己的密码
     * @param Long 输入的用户id，String password
     * @return 该用户id是否可以输入
     */
    public boolean modifyPw(String updateId,Long userId,String pw) {
        boolean retBoo = false;

        try {

            int tag=this.getDao().execute("update UserForm as uf set uf.password='"+pw+"'" +
                    ",uf.updateBy="+userId+",uf.updateDate=sysdate() where uf.id="+updateId);
            if(tag>0) retBoo=true;

        } catch(Exception e) {
            e.printStackTrace();
        }
        return retBoo;
    }


    /**
     * 插入一条用户信息<br>
     * password通过MD5转码
     * @param UserForm 用户信息Form
     * @return 是否成功标志
     */
    public int add(UserForm uf) {
        int tag=-1;

        try {
            if(chkName(uf.getEmployeeCode())){
                String password=uf.getPassword();
                MD5 md=new MD5();
                uf.setPassword(md.getMD5ofStr(password));
                if(this.getDao().insert(uf)) tag = 1;
            }else {
                tag=-2;
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
        }

        return tag;
    }



    /**
     * 修改一条用户信息
     * @param UserForm 用户信息Form
     * @return 是否成功标志，
     */
    public int modify(UserForm uf) {
        int tag=-1;
        boolean t = false;
        try {
            t = this.getDao().update(uf);
        } catch(Exception e) {
            e.printStackTrace();
        }
        if (t) {
            tag = 1;
        }
        return tag;
    }


    /**
     * 逻辑删除多条用户信息
     * @param String 删除用户id（逗号分隔）；Long 操作用户id
     * @return 是否成功标志，
     */
    public int modify(String ids,Long userId) {
        int tag=-1;


        ArrayList modList=new ArrayList();
        try {
            ArrayList dataList = (ArrayList)this.getDao().list("from UserForm as uf  where uf.id in ("+ids+")");
            for(int i=0;i<dataList.size();i++){
                UserForm uf=(UserForm)dataList.get(i);
                uf.setDelFlag(new Integer(1));
                uf.setUpdateBy(userId);
                uf.setUpdateDate(new java.util.Date());
                modList.add(uf);
            }


            if(this.getBatchDao().updateBatch(modList)) tag=1;
        } catch(Exception e) {
            e.printStackTrace();
        }

        return tag;
    }





    /**
     * 新增一条用户信息及相关部门和权限
     * @param UserForm 用户信息Form
     * @return 是否成功标志，
     */
    public int userAndDeptAdd(UserForm user) 	  {
        int tag=-1;
        try{

            String roleCode=user.getRoleCode();
            if(roleCode!=null&&!roleCode.equals("")){

                List roleList=this.getDao().list("from RoleForm rf where rf.id in ("+roleCode+")");
                Iterator it2=roleList.iterator();
                while(it2.hasNext()){
                    RoleForm rf=(RoleForm)it2.next();
                    user.getRoles().add(rf);
                }
            }
            tag=this.add(user);

        }catch(Exception e){
            e.printStackTrace();
        }

        return tag;
    }

    /**
     * 修改一条用户信息及相关部门和权限
     * @param UserForm 用户信息Form
     * @return 是否成功标志，
     */
    public int userAndDeptModify(UserForm user) {
        int tag=-1;
        try{
            String roleCode=user.getRoleCode();
            if(roleCode!=null&&!roleCode.equals("")){

                List roleList=this.getDao().list("from RoleForm rf where rf.id in ("+roleCode+")");
                Iterator it2=roleList.iterator();
                while(it2.hasNext()){
                    RoleForm rf=(RoleForm)it2.next();
                    user.getRoles().remove(rf);
                    user.getRoles().add(rf);
                }
            }
            tag=this.modify(user);

        }catch(Exception e){
            e.printStackTrace();
        }

        return tag;
    }



    /**
     * 查询某权限下的用户和未赋权限的用户
     * @param strDeptId 部门id，strReptId维修站id
     * @return ArrayList[]
     */
    public ArrayList[] roleUser(String strRole) {
        ArrayList[] arrayRet=new ArrayList[2];

        try{


        }catch(Exception e){
            e.printStackTrace();
        }

        return arrayRet;

    }


    /**
     * 查询某权限下的用户和未赋权限的用户
     * @param String 部门id，String 维修站id
     * @return Object 部门或权限的form
     */
    public Object removeUser(String userId,String flag,String deptRoleId) {
        Object objRet=null;

        try{
            ArrayList dataList = (ArrayList)this.getDao().list("from UserForm as uf where uf.id in ("+userId+")");

            if(flag.equals("role")){
                RoleBo rbo=RoleBo.getInstance();
                RoleForm rf=rbo.findRoleUser(deptRoleId);

                for(int i=0;i<dataList.size();i++){
                    UserForm uf =(UserForm)dataList.get(i);
                    rf.romoveUser(uf);
                }
                if(this.getDao().update(rf)){
                    objRet=rf;
					/*
					//修改左菜单访问链接
					FunctionBo fbo = new FunctionBo();
					ArrayList tempList = (ArrayList)this.getDao().list("from UserForm as uf where uf.id in ("+ids+")");
					for(int i=0;i<tempList.size();i++){
						UserForm uf = (UserForm)tempList.get(i);
						uf.setRoleCodeAndName();
						
						fbo.modelBuild(uf.getId().toString(),uf.getRoleCode(),"modify");
					}
					*/
                }

            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return objRet;

    }

    /**
     * 某用户的id，name和所属的部门asc_level，unit_Code
     * @param String 用户登录id
     * @return Object[] 返回数据
     */
    public Object[] getLogInfo(String empCode) {
        Object[] intRet=null;

        try{
            String strHql="select uf.id,uf.employeeCode,uf.userName from UserForm as uf " +
                    "where uf.employeeCode='"+empCode+"'";
            intRet=(Object[])this.getDao().uniqueResult(strHql);

        }catch(Exception e){
            e.printStackTrace();
        }

        return intRet;
    }


    public static void main(String args[]){
        try{
            UserBo ubo=new UserBo();
            Object[] obj=ubo.getLogInfo("dne_xt");

            // modified by xt	System.out.println((Long)obj[0]);
            // modified by xt	System.out.println((String)obj[3]);

        }catch(Exception e){
            e.printStackTrace();
        }


    }


}
