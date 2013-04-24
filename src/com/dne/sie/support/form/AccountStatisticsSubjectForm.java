package com.dne.sie.support.form;

import java.util.Date;

public class AccountStatisticsSubjectForm {

	private Long statSubId;
	private Long statId;
	private Integer accountMonth;
	private String payType;
	private Double money;
	private Long subjectId;
	private String subjectName;
	private Date createDate;
	
	
	public Long getStatSubId() {
		return statSubId;
	}
	public void setStatSubId(Long statSubId) {
		this.statSubId = statSubId;
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
	public Long getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
}
