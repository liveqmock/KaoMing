package com.dne.sie.reception.form;

import java.util.Date;

import com.dne.sie.util.form.CommForm;

public class SalePaymentForm extends CommForm{
	
	private Long feeId;         			//����ID							
	private String saleNo;        			//ѯ�۵���						
	private Float payAmount;     			//���ѽ��						
	private String payType;       			//���ѷ�ʽ		
	private String payVariety;				//��������						
	private String clientBank;    			//�ͻ�����						
	private String paymentCardNo;			//�ͻ�֧Ʊ��ת�˺���	
	private Float balanceDue;    			//����֧�����	
	private String invoiceNo;  					//��Ʊ��
	private String invoiceType;         		//��Ʊ����
	private String dataType;         		//��������
	private Float billingMoney;    			//��Ʊ���
	private Float saleTotalPrice;			//�����ܼ�
	
	private String stuffNo;
	
	private Date createDate;
	private Long updateBy;
	private Long createBy;
	private Date updateDate;
	
	
	public Long getFeeId() {
		return feeId;
	}
	public void setFeeId(Long feeId) {
		this.feeId = feeId;
	}
	public String getSaleNo() {
		return saleNo;
	}
	public void setSaleNo(String saleNo) {
		this.saleNo = saleNo;
	}
	public Float getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(Float payAmount) {
		this.payAmount = payAmount;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getClientBank() {
		return clientBank;
	}
	public void setClientBank(String clientBank) {
		this.clientBank = clientBank;
	}
	
	public Float getBalanceDue() {
		return balanceDue;
	}
	public void setBalanceDue(Float balanceDue) {
		this.balanceDue = balanceDue;
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
	public String getPaymentCardNo() {
		return paymentCardNo;
	}
	public void setPaymentCardNo(String paymentCardNo) {
		this.paymentCardNo = paymentCardNo;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getInvoiceType() {
		return invoiceType;
	}
	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public Float getBillingMoney() {
		return billingMoney;
	}
	public void setBillingMoney(Float billingMoney) {
		this.billingMoney = billingMoney;
	}
	public Float getSaleTotalPrice() {
		return saleTotalPrice;
	}
	public void setSaleTotalPrice(Float saleTotalPrice) {
		this.saleTotalPrice = saleTotalPrice;
	}
	public String getStuffNo() {
		return stuffNo;
	}
	public void setStuffNo(String stuffNo) {
		this.stuffNo = stuffNo;
	}
	public String getPayVariety() {
		return payVariety;
	}
	public void setPayVariety(String payVariety) {
		this.payVariety = payVariety;
	}
	
	

}
