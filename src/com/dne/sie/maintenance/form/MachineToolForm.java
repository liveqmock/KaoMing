package com.dne.sie.maintenance.form;

import java.util.Date;
import com.dne.sie.util.form.CommForm;

public class MachineToolForm extends CommForm{
	
	private Long machineId;
	private String machineName;
	private String customerId;
	private String customerName;
	private String modelCode;
	private String serialNo;
	private String warrantyCardNo;
	private Date purchaseDate;
	private Date extendedWarrantyDate;
	
	private String linkman;         //ÁªÏµÈË      formula

	private java.util.Date createDate;
	private Long updateBy;
	private Long createBy;
	private java.util.Date updateDate;
	
	private Integer delFlag;
	
	private String purchaseDateStr;
	private String extendedWarrantyDateStr;
	
	public Long getMachineId() {
		return machineId;
	}
	public void setMachineId(Long machineId) {
		this.machineId = machineId;
	}
	public String getMachineName() {
		return machineName;
	}
	public void setMachineName(String machineName) {
		this.machineName = machineName;
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
	public String getModelCode() {
		return modelCode;
	}
	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getWarrantyCardNo() {
		return warrantyCardNo;
	}
	public void setWarrantyCardNo(String warrantyCardNo) {
		this.warrantyCardNo = warrantyCardNo;
	}
	public Date getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public Date getExtendedWarrantyDate() {
		return extendedWarrantyDate;
	}
	public void setExtendedWarrantyDate(Date extendedWarrantyDate) {
		this.extendedWarrantyDate = extendedWarrantyDate;
	}
	public java.util.Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(java.util.Date createDate) {
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
	public java.util.Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(java.util.Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	public Integer getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	public String getPurchaseDateStr() {
		return purchaseDateStr;
	}
	public void setPurchaseDateStr(String purchaseDateStr) {
		this.purchaseDateStr = purchaseDateStr;
	}
	public String getExtendedWarrantyDateStr() {
		return extendedWarrantyDateStr;
	}
	public void setExtendedWarrantyDateStr(String extendedWarrantyDateStr) {
		this.extendedWarrantyDateStr = extendedWarrantyDateStr;
	}
	
	
	

}
