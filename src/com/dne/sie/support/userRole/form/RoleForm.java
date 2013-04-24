package com.dne.sie.support.userRole.form;

import com.dne.sie.util.form.CommForm;

import java.util.Date;
import java.util.Set;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.Iterator;


/**
 * 权限管理Form处理类
 * @author xt
 * @version 1.1.5.6
 * @see RoleForm.java <br>
 */
public class RoleForm extends CommForm{
   private Long id;
   private String roleName;
//   private String roleType;
   private String remark;
   
   private String roleContainName;
   
   private Long updateBy;
   private Date createDate;
   private Long createBy;
   private Date updateDate;
   
   private Integer delFlag         ;
   private String userType;
   
   public void setDelFlag(Integer temp) {  delFlag = temp;  } 
   public Integer getDelFlag() {  return this.delFlag;  } 
   
   private String functionCodes      	="";
   public void setFunctionCodes(String temp) {  functionCodes = temp;  } 
   public String getFunctionCodes() {  return this.functionCodes;  }
   
   private String userCode ="";      //用户id  
   private String userName ="";      //用户名称  
	
   public String getUserCode(){ return this.userCode; } 
   public String getUserName(){ return this.userName; } 
	
   public void setUserCode(String temp) {  userCode = temp;  } 
   public void setUserName(String temp) {  userName = temp;  } 
   
   	   private Set users = new HashSet();
	   public void setUsers(Set temp) {  users = temp;  } 
	   public Set getUsers() {  return this.users;  } 
	   
//	   private Set functions = new HashSet();
//	   public void setFunctions(Set temp) {  functions = temp;  } 
//	   public Set getFunctions() {  return this.functions;  } 
	
	
		   public void setUserCodeAndName() {  
			   String tempCode="",tempName="";
			   try{
				   Iterator it=getUsers().iterator();
				   TreeSet setCode=new TreeSet();
				   TreeSet setName=new TreeSet();
				   while(it.hasNext()){
					   UserForm uf=(UserForm)it.next();
					   if(uf.getDelFlag().intValue()==0){
						   setCode.add(uf.getId().toString());
						   setName.add(uf.getUserName());
					   }
				   }
				   Iterator itCode=setCode.iterator();
				   Iterator itName=setName.iterator();
				   while(itCode.hasNext()) tempCode+=","+itCode.next();
				   while(itName.hasNext()) tempName+=","+itName.next();
				   if(tempCode.indexOf(',')!=-1) tempCode=tempCode.substring(1);
				   if(tempName.indexOf(',')!=-1) tempName=tempName.substring(1);
			   }catch(Exception e){
				   e.printStackTrace();
			   }
			   this.userCode = tempCode;  
			   this.userName = tempName;  
		   } 
		   
		   
	public void romoveUser(UserForm uf){
		if(uf!=null){
			Object[] obj=this.getUsers().toArray();
			for(int i=0;i<obj.length;i++){
				UserForm uf1=(UserForm)obj[i];
				if(uf1.getId().longValue()==uf.getId().longValue()){
					this.getUsers().remove(uf1);
				}
			}
		}
	}
   
			   
			   
	public void setRemark(String temp) {  remark = temp;  } 
	public String getRemark() { return this.remark;  } 
	
   
   /**
   Access method for the id property.
   
   @return   the current value of the id property
    */
   public Long getId() 
   {
      return id;
   }
   
   /**
   Sets the value of the id property.
   
   @param aId the new value of the id property
    */
   public void setId(Long aId) 
   {
      id = aId;
   }
   
   /**
   Access method for the roleName property.
   
   @return   the current value of the roleName property
    */
   public String getRoleName() 
   {
      return roleName;
   }
   
   /**
   Sets the value of the roleName property.
   
   @param aRoleName the new value of the roleName property
    */
   public void setRoleName(String aRoleName) 
   {
      roleName = aRoleName;
   }
   
   /**
   Access method for the updateBy property.
   
   @return   the current value of the updateBy property
    */
   public Long getUpdateBy() 
   {
      return updateBy;
   }
   
   /**
   Sets the value of the updateBy property.
   
   @param aUpdateBy the new value of the updateBy property
    */
   public void setUpdateBy(Long aUpdateBy) 
   {
      updateBy = aUpdateBy;
   }
   
   /**
   Access method for the createDate property.
   
   @return   the current value of the createDate property
    */
   public Date getCreateDate() 
   {
      return createDate;
   }
   
   /**
   Sets the value of the createDate property.
   
   @param aCreateDate the new value of the createDate property
    */
   public void setCreateDate(Date aCreateDate) 
   {
      createDate = aCreateDate;
   }
   
   /**
   Access method for the createBy property.
   
   @return   the current value of the createBy property
    */
   public Long getCreateBy() 
   {
      return createBy;
   }
   
   /**
   Sets the value of the createBy property.
   
   @param aCreateBy the new value of the createBy property
    */
   public void setCreateBy(Long aCreateBy) 
   {
      createBy = aCreateBy;
   }
   
   /**
   Access method for the updateDate property.
   
   @return   the current value of the updateDate property
    */
   public Date getUpdateDate() 
   {
      return updateDate;
   }
   
   /**
   Sets the value of the updateDate property.
   
   @param aUpdateDate the new value of the updateDate property
    */
   public void setUpdateDate(Date aUpdateDate) 
   {
      updateDate = aUpdateDate;
   }


///**
// * @return
// */
//public String getRoleType() {
//	return roleType;
//}
//
//
///**
// * @param string
// */
//public void setRoleType(String string) {
//	roleType = string;
//}

/**
 * @return
 */
public String getRoleContainName() {
	return roleContainName;
}

/**
 * @param string
 */
public void setRoleContainName(String string) {
	roleContainName = string;
}


/**
 * @return
 */
public String getUserType() {
	return userType;
}

/**
 * @param string
 */
public void setUserType(String string) {
	userType = string;
}

}
