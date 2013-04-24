package com.dne.sie.stock.form;

import java.util.Date;

import com.dne.sie.util.form.CommForm;

public class StockToolsInfoForm extends CommForm  {
	

	 //pk
    private Long stockId;
    //零件名称
    private String skuCode;
    //规格
    private String standard;
    //料号
    private String stuffNo;
    //单位
    private String skuUnit;
    //零件数量
    private Integer skuNum;
   
    
    //零件属性
    private String skuType;
    //备注
    private String remark;
    //仓位
    private String binCode;
    //库存状态
    private String stockStatus;
    //入库流水号
    private Long flowNo;
    
    private Long requestId;
    private String owner;
    
	 //入库人
    private Long createBy;
    private Date createDate;
    private Long updateBy;
    private Date updateDate;
    
    
	public Long getStockId() {
		return stockId;
	}
	public void setStockId(Long stockId) {
		this.stockId = stockId;
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
	public String getStuffNo() {
		return stuffNo;
	}
	public void setStuffNo(String stuffNo) {
		this.stuffNo = stuffNo;
	}
	public String getSkuUnit() {
		return skuUnit;
	}
	public void setSkuUnit(String skuUnit) {
		this.skuUnit = skuUnit;
	}
	public Integer getSkuNum() {
		return skuNum;
	}
	public void setSkuNum(Integer skuNum) {
		this.skuNum = skuNum;
	}
	public String getSkuType() {
		return skuType;
	}
	public void setSkuType(String skuType) {
		this.skuType = skuType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getBinCode() {
		return binCode;
	}
	public void setBinCode(String binCode) {
		this.binCode = binCode;
	}
	public String getStockStatus() {
		return stockStatus;
	}
	public void setStockStatus(String stockStatus) {
		this.stockStatus = stockStatus;
	}
	public Long getFlowNo() {
		return flowNo;
	}
	public void setFlowNo(Long flowNo) {
		this.flowNo = flowNo;
	}
	public Long getRequestId() {
		return requestId;
	}
	public void setRequestId(Long requestId) {
		this.requestId = requestId;
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
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
    
	

}
