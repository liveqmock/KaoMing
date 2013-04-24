package com.dne.sie.support.userRole.bo;

//Java ������
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
//Java ��չ��

//��������
import org.apache.log4j.Logger;

//�Զ�����
import com.dne.sie.support.userRole.form.UserForm;
import com.dne.sie.support.userRole.form.RoleForm;

import com.dne.sie.support.userRole.queryBean.UserQuery;
//import com.dne.sie.common.tools.AtomRoleCheck;
import com.dne.sie.common.encrypt.MD5;
import com.dne.sie.util.bo.CommBo;


/**
 * �û�����BO������
 * @author xt
 * @version 1.1.5.6
 * @see UserBo.java <br>
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
	 * �û��б��ѯƴװ
	 * @param UserForm ��ѯ����
	 * @return ArrayList ��ѯ���,��װString[]
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
	* ����id��ѯ���û���Ϣ
	* @param String �û����¼pk
	* @return ���û�Form
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
	 * ����id��ѯ���û���Ϣ�������������Ϣ
	 * @param String �û����¼pk
	 * @return ���û�Form
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
	 * У���û�id(��¼id)�Ƿ����
	 * @param String ������û�id
	 * @return ���û�id�Ƿ��������
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
	* У���û�password�Ƿ����
	* @param Long ������û�id��String password
	* @return ���û�id�Ƿ��������
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
		* �޸��û��Լ�������
		* @param Long ������û�id��String password
		* @return ���û�id�Ƿ��������
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
	  * ����һ���û���Ϣ<br>
	  * passwordͨ��MD5ת��
	  * @param UserForm �û���ϢForm
	  * @return �Ƿ�ɹ���־
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
	 * �޸�һ���û���Ϣ
	 * @param UserForm �û���ϢForm
	 * @return �Ƿ�ɹ���־��
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
	 * �߼�ɾ�������û���Ϣ
	 * @param String ɾ���û�id�����ŷָ�����Long �����û�id
	 * @return �Ƿ�ɹ���־��
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
	* ����һ���û���Ϣ����ز��ź�Ȩ��
	* @param UserForm �û���ϢForm
	* @return �Ƿ�ɹ���־��
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
		* �޸�һ���û���Ϣ����ز��ź�Ȩ��
		* @param UserForm �û���ϢForm
		* @return �Ƿ�ɹ���־��
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
	* ��ѯĳȨ���µ��û���δ��Ȩ�޵��û�
	* @param strDeptId ����id��strReptIdά��վid
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
	* ��ѯĳȨ���µ��û���δ��Ȩ�޵��û�
	* @param String ����id��String ά��վid
	* @return Object ���Ż�Ȩ�޵�form
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
					//�޸���˵���������
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
	* ĳ�û���id��name�������Ĳ���asc_level��unit_Code
	* @param String �û���¼id
	* @return Object[] ��������
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
