package com.dne.sie.support.form;

import java.util.Date;

import com.dne.sie.util.form.CommForm;

public class AccountStatisticsForm extends CommForm {
	
	private Long statId;
	private Double cashReceipt;
	private Double cashPayment;
	private Double bankReceipt;
	private Double bankPayment;
	private Integer accountMonth;
	private Double priorCash;
	private Double priorBank;
	private Double currentCash;
	private Double currentBank;
	private Date createDate;
	
	private String accountMonth1;
	private String accountMonth2;
	
	private Double totalAmount;
	
	public Long getStatId() {
		return statId;
	}
	public void setStatId(Long statId) {
		this.statId = statId;
	}
	public Double getCashReceipt() {
		return cashReceipt;
	}
	public void setCashReceipt(Double cashReceipt) {
		this.cashReceipt = cashReceipt;
	}
	public Double getCashPayment() {
		return cashPayment;
	}
	public void setCashPayment(Double cashPayment) {
		this.cashPayment = cashPayment;
	}
	public Double getBankReceipt() {
		return bankReceipt;
	}
	public void setBankReceipt(Double bankReceipt) {
		this.bankReceipt = bankReceipt;
	}
	public Double getBankPayment() {
		return bankPayment;
	}
	public void setBankPayment(Double bankPayment) {
		this.bankPayment = bankPayment;
	}
	public Integer getAccountMonth() {
		return accountMonth;
	}
	public void setAccountMonth(Integer accountMonth) {
		this.accountMonth = accountMonth;
	}
	public Double getPriorCash() {
		return priorCash;
	}
	public void setPriorCash(Double priorCash) {
		this.priorCash = priorCash;
	}
	public Double getPriorBank() {
		return priorBank;
	}
	public void setPriorBank(Double priorBank) {
		this.priorBank = priorBank;
	}
	public Double getCurrentCash() {
		return currentCash;
	}
	public void setCurrentCash(Double currentCash) {
		this.currentCash = currentCash;
	}
	public Double getCurrentBank() {
		return currentBank;
	}
	public void setCurrentBank(Double currentBank) {
		this.currentBank = currentBank;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getAccountMonth1() {
		return accountMonth1;
	}
	public void setAccountMonth1(String accountMonth1) {
		this.accountMonth1 = accountMonth1;
	}
	public String getAccountMonth2() {
		return accountMonth2;
	}
	public void setAccountMonth2(String accountMonth2) {
		this.accountMonth2 = accountMonth2;
	}
	
	
	
	
}
