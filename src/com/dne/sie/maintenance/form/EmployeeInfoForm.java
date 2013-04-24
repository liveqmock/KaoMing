package com.dne.sie.maintenance.form;

import java.util.Date;

import com.dne.sie.util.form.CommForm;

public class EmployeeInfoForm extends CommForm{
	
	private Long employeeCode;		//员工ID      
	private String employeeName;    	//员工名称    
	private String birthday;         	//生日        
	private String sex;         		//性别      
	private String email;        		//邮箱    
	private String address;           	//地址     
	private String phone;             	//电话       
	private String employedDate;        //入职日期      
	private String identityCard;        //身份证号
	private String remark;           	//备注  
	private String position;           	//职位
	
	private Integer delFlag;        		      
	private Date createDate;
	private Long createBy;
	private String qq;
	private String msn;
	
	
	public Long getEmployeeCode() {
		return employeeCode;
	}
	public void setEmployeeCode(Long employeeCode) {
		this.employeeCode = employeeCode;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
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
	public String getEmployedDate() {
		return employedDate;
	}
	public void setEmployedDate(String employedDate) {
		this.employedDate = employedDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
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
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getMsn() {
		return msn;
	}
	public void setMsn(String msn) {
		this.msn = msn;
	}
	public String getIdentityCard() {
		return identityCard;
	}
	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}

	
	
}
