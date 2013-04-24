package com.dne.sie.repair.form;

import java.util.Date;

import com.dne.sie.util.form.CommForm;

public class RepairServiceStatusForm  extends CommForm{
	
	private Long statusId;
	private Long repairNo;
	private String repairStatus;
	private Date beginDate;
	private Long createBy;
	
	private String statusRemark;
	
	private RepairServiceForm repairServiceForm = new RepairServiceForm();
	
	
	public Long getStatusId() {
		return statusId;
	}
	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}
	public Long getRepairNo() {
		return repairNo;
	}
	public void setRepairNo(Long repairNo) {
		this.repairNo = repairNo;
	}
	public String getRepairStatus() {
		return repairStatus;
	}
	public void setRepairStatus(String repairStatus) {
		this.repairStatus = repairStatus;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public String getStatusRemark() {
		return statusRemark;
	}
	public void setStatusRemark(String statusRemark) {
		this.statusRemark = statusRemark;
	}
	public RepairServiceForm getRepairServiceForm() {
		return repairServiceForm;
	}
	public void setRepairServiceForm(RepairServiceForm repairServiceForm) {
		this.repairServiceForm = repairServiceForm;
	}
	public Long getCreateBy() {
		return createBy;
	}
	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}
	
	
	
}
