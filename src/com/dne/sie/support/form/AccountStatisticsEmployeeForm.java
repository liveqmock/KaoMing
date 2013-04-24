package com.dne.sie.support.form;

import java.util.Date;

import com.dne.sie.util.form.CommForm;

public class AccountStatisticsEmployeeForm extends CommForm {
	
	private Long statEmpId;
	private Long statId;
	private Integer accountMonth;
	private String payType;
	private Double money;
	private Long employeeCode;
	private String employeeName;
	private Date createDate;
	private String customerId;
    private String customerName;
	
	
	public Long getStatEmpId() {
		return statEmpId;
	}
	public void setStatEmpId(Long statEmpId) {
		this.statEmpId = statEmpId;
	}
	public Long getStatId() {
		return statId;
	}
	public void setStatId(Long statId) {
		this.statId = statId;
	}
	public Integer getAccountMonth() {
		return accountMonth;
	}
	public void setAccountMonth(Integer accountMonth) {
		this.accountMonth = accountMonth;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
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
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	
	
}
