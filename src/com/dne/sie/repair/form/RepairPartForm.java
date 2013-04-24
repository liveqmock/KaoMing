package com.dne.sie.repair.form;

import java.util.Date;

import com.dne.sie.util.form.CommForm;

public class RepairPartForm extends CommForm{
	
	private Long partsId;
	private Long repairNo;
	private String stuffNo;                  	//料号		
	private String repairPartType;              //维修零件类型(维修申请/携带零件/携带工具/携带转销售)
	private String warrantyType;              	//保内/外
	private Integer applyQty;					//申请数量
	
	private String repairPartStatus;			//维修零件状态(申请中、订购中、已分配待领取、已携带待返还、已返还、转销售)
	
	private String skuCode;                  //零件名称				
	private String standard;                 //规格							
	private String skuUnit;                  //单位	
	
	private String owner;
	private String serviceSheetNo;
	
	private int version;
	
	private Long createBy;      	
	private Date createDate;
	private Long updateBy;
	private Date updateDate;
	
	
	public Long getPartsId() {
		return partsId;
	}
	public void setPartsId(Long partsId) {
		this.partsId = partsId;
	}
	public Long getRepairNo() {
		return repairNo;
	}
	public void setRepairNo(Long repairNo) {
		this.repairNo = repairNo;
	}
	public String getStuffNo() {
		return stuffNo;
	}
	public void setStuffNo(String stuffNo) {
		this.stuffNo = stuffNo;
	}
	public String getRepairPartType() {
		return repairPartType;
	}
	public void setRepairPartType(String repairPartType) {
		this.repairPartType = repairPartType;
	}
	public String getWarrantyType() {
		return warrantyType;
	}
	public void setWarrantyType(String warrantyType) {
		this.warrantyType = warrantyType;
	}
	public Integer getApplyQty() {
		return applyQty;
	}
	public void setApplyQty(Integer applyQty) {
		this.applyQty = applyQty;
	}
	public String getRepairPartStatus() {
		return repairPartStatus;
	}
	public void setRepairPartStatus(String repairPartStatus) {
		this.repairPartStatus = repairPartStatus;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public Long getCreateBy() {
		return createBy;
	}
	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
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
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	public String getSkuUnit() {
		return skuUnit;
	}
	public void setSkuUnit(String skuUnit) {
		this.skuUnit = skuUnit;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getServiceSheetNo() {
		return serviceSheetNo;
	}
	public void setServiceSheetNo(String serviceSheetNo) {
		this.serviceSheetNo = serviceSheetNo;
	}
	
	
	
	
	
}
