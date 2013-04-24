package com.dne.sie.repair.form;

import java.sql.Date;

import com.dne.sie.util.form.CommForm;

public class RepairManInfoForm extends CommForm{
	
	private Long travelId;								//pk
	private Long repairNo;								//fk
	private Long repairMan;								//ά��Ա
	private Date departDate;							//��������
	private Date arrivalDate;							//��������
	private Date returnDate;							//��������
	private Double travelFee;							//���÷�
	private String repairCondition;						//ά�����
	private String remark;								//��ע
	private Integer workingHours;						//Ԥ�ƹ�ʱ
	private Double laborCosts;							//Ԥ���˹���
	private Integer workingHoursActual;					//ʵ�ʹ�ʱ
	private Double laborCostsActual;					//ʵ���˹���
	
	private String repairManName;		
	private String repairManPhone;
	
	private RepairServiceForm repairServiceForm = new RepairServiceForm();
	
	private Long createBy;      	            
	private java.util.Date createDate;                  
	private Long updateBy;                    
	private java.util.Date updateDate;
	
	
	public Long getTravelId() {
		return travelId;
	}
	public void setTravelId(Long travelId) {
		this.travelId = travelId;
	}
	public Long getRepairNo() {
		return repairNo;
	}
	public void setRepairNo(Long repairNo) {
		this.repairNo = repairNo;
	}
	public Long getRepairMan() {
		return repairMan;
	}
	public void setRepairMan(Long repairMan) {
		this.repairMan = repairMan;
	}
	public Date getDepartDate() {
		return departDate;
	}
	public void setDepartDate(Date departDate) {
		this.departDate = departDate;
	}
	public Date getArrivalDate() {
		return arrivalDate;
	}
	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	public Date getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	public Double getTravelFee() {
		return travelFee;
	}
	public void setTravelFee(Double travelFee) {
		this.travelFee = travelFee;
	}
	public String getRepairCondition() {
		return repairCondition;
	}
	public void setRepairCondition(String repairCondition) {
		this.repairCondition = repairCondition;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public Long getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}
	public java.util.Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(java.util.Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getRepairManName() {
		return repairManName;
	}
	public void setRepairManName(String repairManName) {
		this.repairManName = repairManName;
	}
	public RepairServiceForm getRepairServiceForm() {
		return repairServiceForm;
	}
	public void setRepairServiceForm(RepairServiceForm repairServiceForm) {
		this.repairServiceForm = repairServiceForm;
	}
	public String getRepairManPhone() {
		return repairManPhone;
	}
	public void setRepairManPhone(String repairManPhone) {
		this.repairManPhone = repairManPhone;
	}
	public Integer getWorkingHours() {
		return workingHours;
	}
	public void setWorkingHours(Integer workingHours) {
		this.workingHours = workingHours;
	}
	public Double getLaborCosts() {
		return laborCosts;
	}
	public void setLaborCosts(Double laborCosts) {
		this.laborCosts = laborCosts;
	}
	public Integer getWorkingHoursActual() {
		return workingHoursActual;
	}
	public void setWorkingHoursActual(Integer workingHoursActual) {
		this.workingHoursActual = workingHoursActual;
	}
	public Double getLaborCostsActual() {
		return laborCostsActual;
	}
	public void setLaborCostsActual(Double laborCostsActual) {
		this.laborCostsActual = laborCostsActual;
	}
	
	
	
}
