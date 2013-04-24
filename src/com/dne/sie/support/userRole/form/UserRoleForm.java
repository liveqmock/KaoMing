package com.dne.sie.support.userRole.form;

import com.dne.sie.util.form.CommForm;

import java.util.Date;


/**
 * 权限管理Form处理类
 * @author xt
 * @version 1.1.5.6
 * @see UserRoleForm.java <br>
 */
public class UserRoleForm extends CommForm{
   private Long roleId;
   private Long userId;
   
   private Long updateBy;
   private Date createDate;
   private Long createBy;
   private Date updateDate;
   
public Long getRoleId() {
	return roleId;
}
public void setRoleId(Long roleId) {
	this.roleId = roleId;
}
public Long getUserId() {
	return userId;
}
public void setUserId(Long userId) {
	this.userId = userId;
}
public Long getUpdateBy() {
	return updateBy;
}
public void setUpdateBy(Long updateBy) {
	this.updateBy = updateBy;
}
public Date getCreateDate() {
	return createDate;
}
public void setCreateDate(Date createDate) {
	this.createDate = createDate;
}
public Long getCreateBy() {
	return createBy;
}
public void setCreateBy(Long createBy) {
	this.createBy = createBy;
}
public Date getUpdateDate() {
	return updateDate;
}
public void setUpdateDate(Date updateDate) {
	this.updateDate = updateDate;
}
   
   
   
}
