package com.dne.sie.maintenance.form;

import java.util.Date;

import com.dne.sie.util.form.CommForm;

public class IrisCodeForm extends CommForm{
	
	private Long irisCodeId;
	private String irisName;
	private Long parentCode;
	private String irisType;
	private String inputFlag;
	private String layer;
	private Float orderId;
	private String irisDesc;
	

	private Date createDate;
	private Long updateBy;
	private Long createBy;
	private Date updateDate;
	
	
	
	public Long getIrisCodeId() {
		return irisCodeId;
	}
	public void setIrisCodeId(Long irisCodeId) {
		this.irisCodeId = irisCodeId;
	}
	public String getIrisName() {
		return irisName;
	}
	public void setIrisName(String irisName) {
		this.irisName = irisName;
	}
	public Long getParentCode() {
		return parentCode;
	}
	public void setParentCode(Long parentCode) {
		this.parentCode = parentCode;
	}
	public String getIrisType() {
		return irisType;
	}
	public void setIrisType(String irisType) {
		this.irisType = irisType;
	}
	public String getInputFlag() {
		return inputFlag;
	}
	public void setInputFlag(String inputFlag) {
		this.inputFlag = inputFlag;
	}
	public String getLayer() {
		return layer;
	}
	public void setLayer(String layer) {
		this.layer = layer;
	}
	public Float getOrderId() {
		return orderId;
	}
	public void setOrderId(Float orderId) {
		this.orderId = orderId;
	}
	public String getIrisDesc() {
		return irisDesc;
	}
	public void setIrisDesc(String irisDesc) {
		this.irisDesc = irisDesc;
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
	
	
	
	
	

}
