package com.dne.sie.logistic.form;

import java.util.Date;

import com.dne.sie.util.form.CommForm;

public class AsnForm extends CommForm{
	
	private String asnNo;                   //出货单号
	private String customerId;				//客户ID
	private String customerName;			//客户名称
	private String customerPhone;			//客户电话
	private String shippingAddress;			//出货地址
	private Date shippingDate;				//出货时间
	private String payStatus;				//付款状态
	private String billingStatus;			//开票状态
	private String shippingRemark;			//出货备注
	private String linkman;					//联系人
	private String asnStatus;				//出货状态
	private String asnType;					
	private int version; 
	private String approveRemark;			//审批意见
	

	private Date createDate;
	private Long updateBy;
	private Long createBy;
	private Date updateDate;
	
	
	/*----查询条件----*/
	private String saleNo; 
	private String skuCode; 
	
	
	public String getAsnNo() {
		return asnNo;
	}
	public void setAsnNo(String asnNo) {
		this.asnNo = asnNo;
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
	public String getCustomerPhone() {
		return customerPhone;
	}
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}
	public String getShippingAddress() {
		return shippingAddress;
	}
	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	public Date getShippingDate() {
		return shippingDate;
	}
	public void setShippingDate(Date shippingDate) {
		this.shippingDate = shippingDate;
	}
	public String getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	public String getBillingStatus() {
		return billingStatus;
	}
	public void setBillingStatus(String billingStatus) {
		this.billingStatus = billingStatus;
	}
	public String getShippingRemark() {
		return shippingRemark;
	}
	public void setShippingRemark(String shippingRemark) {
		this.shippingRemark = shippingRemark;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
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
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	public String getAsnStatus() {
		return asnStatus;
	}
	public void setAsnStatus(String asnStatus) {
		this.asnStatus = asnStatus;
	}
	public String getSaleNo() {
		return saleNo;
	}
	public void setSaleNo(String saleNo) {
		this.saleNo = saleNo;
	}
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	public String getApproveRemark() {
		return approveRemark;
	}
	public void setApproveRemark(String approveRemark) {
		this.approveRemark = approveRemark;
	}
	public String getAsnType() {
		return asnType;
	}
	public void setAsnType(String asnType) {
		this.asnType = asnType;
	}
	
	
	

}
