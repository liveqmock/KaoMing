package com.dne.sie.repair.form;

import com.dne.sie.util.form.CommForm;

public class RepairIrisInfoForm extends CommForm{
	
	private Long irisId;
	private Long repairNo;
	private Long irisCodeId;
	private String irisContent;

	private Long createBy;      	            
	private java.util.Date createDate;
	
	
	public Long getIrisId() {
		return irisId;
	}
	public void setIrisId(Long irisId) {
		this.irisId = irisId;
	}
	public Long getRepairNo() {
		return repairNo;
	}
	public void setRepairNo(Long repairNo) {
		this.repairNo = repairNo;
	}
	public Long getIrisCodeId() {
		return irisCodeId;
	}
	public void setIrisCodeId(Long irisCodeId) {
		this.irisCodeId = irisCodeId;
	}
	
	
	public String getIrisContent() {
		return irisContent;
	}
	public void setIrisContent(String irisContent) {
		this.irisContent = irisContent;
	}
	public Long getCreateBy() {
		return createBy;
	}
	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}
	public java.util.Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}
	
	

}
