package com.dne.sie.maintenance.form;

import java.util.Date;

import com.dne.sie.util.form.CommForm;

public class EmployeeInfoForm extends CommForm{
	
	private Long employeeCode;		//Ա��ID      
	private String employeeName;    	//Ա������    
	private String birthday;         	//����        
	private String sex;         		//�Ա�      
	private String email;        		//����    
	private String address;           	//��ַ     
	private String phone;             	//�绰       
	private String employedDate;        //��ְ����      
	private String identityCard;        //���֤��
	private String remark;           	//��ע  
	private String position;           	//ְλ
	
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
