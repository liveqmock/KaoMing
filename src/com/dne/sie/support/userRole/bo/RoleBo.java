package com.dne.sie.support.userRole.bo;

//Java 基础类
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.TreeSet;


//第三方类
import org.apache.log4j.Logger;

//自定义类
import com.dne.sie.support.userRole.queryBean.RoleQuery;
import com.dne.sie.support.userRole.form.RoleForm;
import com.dne.sie.support.userRole.form.UserForm;
//import com.dne.sie.common.tools.AtomRoleCheck;
import com.dne.sie.util.bo.CommBo;


/**
 * 权限管理BO处理类
 * @author xt
 * @version 1.1.5.6
 */
public class RoleBo extends CommBo {
    private static Logger logger = Logger.getLogger(RoleBo.class);

    private static final RoleBo INSTANCE = new RoleBo();

    private RoleBo(){
    }

    public static final RoleBo getInstance() {
        return INSTANCE;
    }

    /**
     * 权限列表查询拼装
     * @param dept 查询条件
     * @return ArrayList 查询结果
     */
    public ArrayList list(RoleForm dept) {
        List dataList = new ArrayList();
        ArrayList alData = new ArrayList();
        RoleQuery dq = new RoleQuery(dept);

        int count=0;
        try {
            dataList=dq.doListQuery(dept.getFromPage(),dept.getToPage());

            count=dq.doCountQuery();

            for (int i=0;i<dataList.size();i++) {
                String[] data = new String[3];
                RoleForm df = (RoleForm)dataList.get(i);

                data[0] = df.getId()+"";
                data[1] = df.getRoleName();
//						data[2] = df.getRoleType();
                data[2] = df.getRemark();

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
     * 根据id查询该权限信息，包含子权限信息
     * @param id 权限记录pk
     * @return 权限Form
     */
    public RoleForm find(String id) {
        RoleForm rf = null;
        try {

            ArrayList roleList=(ArrayList)this.getDao().list("from RoleForm as rf left outer join fetch rf.users where rf.id ="+id);
            rf=(RoleForm)roleList.get(0);
            rf.setUserCodeAndName();



        } catch(Exception e) {
            e.printStackTrace();
        }
        return rf;
    }



    /**
     * 根据id查询该权限信息，外关联user
     * @param id 权限记录pk
     * @return 权限Form
     */
    public RoleForm findRoleUser(String id) {
        RoleForm rf = null;
        try {
            ArrayList roleList=(ArrayList)this.getDao().list("from RoleForm as rf left outer join fetch rf.users where rf.id = ?",new Long(id));
            rf=(RoleForm)roleList.get(0);
            rf.setUserCodeAndName();

        } catch(Exception e) {
            e.printStackTrace();
        }
        return rf;
    }

    /**
     * 根据id查询该权限信息
     * @param id 权限记录pk
     * @return 权限Form
     */
    public RoleForm findById(String id) {
        RoleForm rf = null;
        try {
            rf = (RoleForm)this.getDao().findById(RoleForm.class,new Long(id));
        } catch(Exception e) {
            e.printStackTrace();
        }
        return rf;
    }


    /**
     * 插入一条权限信息
     * @param rf 权限信息Form
     * @return 是否成功标志
     */
    public int add(RoleForm rf) {
        int tag=-1;
        boolean t = false;
        try {
            t = this.getDao().insert(rf);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
        }
        if (t) {
            tag = 1;
        }
        return tag;
    }

    /**
     * 删除传入的权限信息
     * @param ids 待删除权限id的List
     * @return 是否成功标志
     */
    public int deleteList(String ids) {
        int tag=-1;

        try {
            String hql="delete from RoleForm where id in ("+ids+")";
            tag = this.getDao().execute(hql);
        } catch(Exception e) {
            e.printStackTrace();
        }

        return tag;
    }




    /**
     * 修改一条权限信息
     * @param rf 权限信息Form
     * @return 是否成功标志
     */
    public int modify(RoleForm rf) {
        int tag=-1;
        boolean t = false;
        try {
            t = this.getDao().update(rf);
        } catch(Exception e) {
        } finally {
        }
        if (t) {
            tag = 1;
        }
        return tag;
    }

    /**
     * 查询用户拥有的权限和其他可选择的权限
     * @param orgType 部门类型，flag 操作权限
     * @return  ArrayList[] 分别是已拥有权限，其他可选权限的集合
     */
    public ArrayList[] userRole(String strUserId,Integer orgType,String flag) {
        //BASETABLE权限拥有admin的其他功能，但不能把BASETABLE赋予别人
        //String strWhere = " where rf.delFlag=0 and rf.id!="+AtomRoleCheck.BASETABLE;
        String strWhere = " where rf.delFlag=0 and rf.id!=0";
        if(flag.equals("common")){
//			strWhere+=" and rf.roleType='C'  ";

        }else if(flag.equals("admin")){
//			strWhere+="  and rf.roleType!='C' ";
        }
        strWhere+=" order by rf.roleName ";

        ArrayList[] arrayRet=new ArrayList[2];
        ArrayList userRoleList=new ArrayList();
        ArrayList allRoleList=new ArrayList();

        ArrayList roleTemp=new ArrayList();
        ArrayList allTemp=new ArrayList();

        ArrayList idTemp=new ArrayList();

        try{

            //某用户下的权限
            if(strUserId!=null&&!strUserId.equals(""))
                userRoleList=(ArrayList)this.getDao().list("from UserForm uf where uf.id="+strUserId);

            //其他可选择的权限
            allRoleList=(ArrayList)this.getDao().list("from RoleForm rf "+strWhere);

            if(userRoleList!=null&&userRoleList.size()>0){
                UserForm uf=(UserForm)userRoleList.get(0);
                Iterator itRole=uf.getRoles().iterator();

                while(itRole.hasNext()){
                    RoleForm rf1=(RoleForm)itRole.next();
                    if(rf1.getDelFlag().intValue()==0){
                        String temp[]={rf1.getId().toString(),rf1.getRoleName(),""};
                        roleTemp.add(temp);
                        idTemp.add(rf1.getId().toString());
                    }
                }
            }

            for(int i=0;i<allRoleList.size();i++){
                RoleForm rf2=(RoleForm)allRoleList.get(i);
                if(rf2.getDelFlag()!=null&&rf2.getDelFlag().intValue()==0){
                    String temp[]={rf2.getId().toString(),rf2.getRoleName(),"",""};
                    if(!idTemp.contains(rf2.getId().toString())) allTemp.add(temp);
                }
            }

            arrayRet[0]=roleTemp;
            arrayRet[1]=allTemp;


        }catch(Exception e){
            e.printStackTrace();
        }

        return arrayRet;

    }

    public ArrayList<String[]> findAllRoleList() throws Exception{
        ArrayList<String[]> list = new ArrayList();
        List<RoleForm> allRoleList=this.getDao().list("from RoleForm rf where rf.delFlag=0 and rf.id!=0 order by rf.roleName");
        for(RoleForm rf:allRoleList){
            String[] temp = new String[2];
            temp[0] = rf.getId().toString();
            temp[1]=rf.getRoleName();
            list.add(temp);
        }
        return list;
    }

    public ArrayList<Long> findRoleIdList(Long userId) throws Exception{
        UserForm uf = (UserForm)this.getDao().findById(UserForm.class,new Long(userId));
        if(uf.getRoles()==null || uf.getRoles().isEmpty()){
            return new ArrayList<Long>();
        }

        ArrayList<Long> ids = new ArrayList();
        Iterator itRole=uf.getRoles().iterator();
        while(itRole.hasNext()){
            RoleForm rf=(RoleForm)itRole.next();
            ids.add(rf.getId());
        }
        return ids;
    }


    /**
     * 显示某个自定义权限可分配的原子权限
     * @param String 权限id；int 部门类型
     * @return ArrayList[]
     */
//		public ArrayList[] roleContainSelect(String roleId,int orgType) {
//			ArrayList retList[] = new ArrayList[2];
//			ArrayList roleLeft = new ArrayList();	//可选的的原子role
//			ArrayList roleRight = new ArrayList();	//已有的原子role
//			
//			ArrayList leftTemp=new ArrayList();
//			ArrayList rightTemp=new ArrayList();
//			ArrayList idTemp=new ArrayList();
//			
//			try{
//				String strWhere = " where rf.roleType ='A' ";
//				if(orgType!=0) strWhere+=" and rf.organizationCode = "+orgType;
//				
//				RoleForm rf = null;
//				String strRoleContain=null;
//				if(!roleId.equals("")){ 
//					rf=(RoleForm)this.getDao().findById(RoleForm.class,new Long(roleId)); 
//					strRoleContain = rf.getRoleContain();
//				}
//				 
//				ArrayList roleAll = (ArrayList)this.getDao().list("from RoleForm as rf "+strWhere);
//				
//				if(strRoleContain!=null) roleRight=(ArrayList)this.getDao().list("from RoleForm as rf where rf.id in ("+strRoleContain+") ");
//				
//				for(int i=0;i<roleRight.size();i++){
//					RoleForm rf1=(RoleForm)roleRight.get(i);
//					String temp[]={rf1.getId().toString(),rf1.getRoleName()};
//					leftTemp.add(temp);
//					idTemp.add(rf1.getId().toString());
//				}
//				
//				for(int i=0;i<roleAll.size();i++){
//					RoleForm rf2=(RoleForm)roleAll.get(i);
//					String temp[]={rf2.getId().toString(),rf2.getRoleName()};
//					if(!idTemp.contains(rf2.getId().toString())){
//						rightTemp.add(temp); 
//					}
//					
//				}
//
//				retList[0]=leftTemp;
//				retList[1]=rightTemp;
//				
//			}catch(Exception e){
//				e.printStackTrace();
//			}
//			return retList;
//		}
//		


    /**
     * 将原子role的功能点id赋给function_codes（新增）
     * @param strContainCode 权限ids
     * @return String 汇总后的链接id
     */
    public String getNewFunCode(String strContainCode) {
        String funCode="";

        try{
            ArrayList dataList = (ArrayList)this.getDao().list("from RoleForm as rf where rf.id in ("+strContainCode+") ");
            TreeSet ts = new TreeSet();
            for(int i=0;i<dataList.size();i++){
                RoleForm rf1=(RoleForm)dataList.get(i);
                String tempCodes = rf1.getFunctionCodes();

                if(tempCodes!=null&&!tempCodes.equals("")){
                    String[] tempFun=tempCodes.split(",");
                    for(int j=0;j<tempFun.length;j++){
                        ts.add(new Integer(tempFun[j]));
                    }
                }
            }
            Iterator it=ts.iterator();
            String strFunCode="";
            while(it.hasNext()){
                String tempCode=((Integer)it.next()).toString();
                strFunCode+=","+tempCode;
            }
            if(strFunCode.length()>1) strFunCode=strFunCode.substring(1);

            funCode=strFunCode;

        } catch(Exception e) {
            e.printStackTrace();
        }
        return funCode;
    }

}
