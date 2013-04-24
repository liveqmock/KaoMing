package com.dne.sie.support.userRole.form;

import com.dne.sie.util.form.CommForm;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import com.dne.sie.common.tools.Operate;


/**
 * 用户管理UserForm类
 * @author xt
 * @version 1.1.5.6
 * @see UserForm.java <br>
 */
public class UserForm  extends CommForm {
	
 private Long id           	  ;      //用户ID               
 private String userName         ="";      //用户名称             
 private String password         ="";       //用户密码            
 private String email            ="";       //电子邮件            
 private String address          ="";       //地址                
 private String phone            ="";       //电话                
 private String employeeCode     ="";      //login             
 private String sex              ="";       //性别                
 private Integer delFlag         ;      	//删除标志 
 private String remark           ="";       //备注     
 private String  userRoleCode    ="";   
 
 private Long employeeId ;      //职员id     FK
	
	private String roleCode ="";      //权限id  
	private String roleName ="";      //权限名称  
	
	public String getRoleCode(){ return this.roleCode; } 
	public String getRoleName(){ return this.roleName; } 
	
	public void setRoleCode(String temp) {  roleCode = temp;  } 
	public void setRoleName(String temp) {  roleName = temp;  } 
	
	
	private Set roles = new HashSet();
	
	public void setRoles(Set temp) {  roles = temp;  } 
	public Set getRoles() {  return this.roles;  } 
	
		public void setRoleCodeAndName() {  
			String tempCode="",tempName="";
			try{
				Iterator it=getRoles().iterator();
				while(it.hasNext()){
					RoleForm rf=(RoleForm)it.next();
					if(rf.getDelFlag().intValue()==0){
						tempCode+=","+rf.getId();
						tempName+=","+rf.getRoleName();
					}
				}
				if(tempCode.indexOf(',')!=-1) tempCode=tempCode.substring(1);
				if(tempName.indexOf(',')!=-1) tempName=tempName.substring(1);
			}catch(Exception e){
				e.printStackTrace();
			}
			this.roleCode = tempCode;  
			this.roleName = tempName;  
		} 
		
		
   private Long createBy;
   private Date createDate;
   private Long updateBy;
   private Date updateDate;

public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getUserName() {
	return userName;
}
public void setUserName(String userName) {
	this.userName = userName;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public String getPhone() {
	return phone;
}
public void setPhone(String phone) {
	this.phone = phone;
}
public String getEmployeeCode() {
	return employeeCode;
}
public void setEmployeeCode(String employeeCode) {
	this.employeeCode = employeeCode;
}
public String getSex() {
	return sex;
}
public void setSex(String sex) {
	this.sex = sex;
}
public Integer getDelFlag() {
	return delFlag;
}
public void setDelFlag(Integer delFlag) {
	this.delFlag = delFlag;
}
public String getRemark() {
	return remark;
}
public void setRemark(String remark) {
	this.remark = remark;
}
public String getUserRoleCode() {
	return userRoleCode;
}
public void setUserRoleCode(String userRoleCode) {
	this.userRoleCode = userRoleCode;
}
public Long getCreateBy() {
	return createBy;
}
public void setCreateBy(Long createBy) {
	this.createBy = createBy;
}
public Date getCreateDate() {
	return createDate;
}
public void setCreateDate(Date createDate) {
	this.createDate = createDate;
}
public Long getUpdateBy() {
	return updateBy;
}
public void setUpdateBy(Long updateBy) {
	this.updateBy = updateBy;
}
public Date getUpdateDate() {
	return updateDate;
}
public void setUpdateDate(Date updateDate) {
	this.updateDate = updateDate;
}
public Long getEmployeeId() {
	return employeeId;
}
public void setEmployeeId(Long employeeId) {
	this.employeeId = employeeId;
}
   
	
   
   
   		
}
