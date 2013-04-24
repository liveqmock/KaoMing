package com.dne.sie.maintenance.form;

import com.dne.sie.util.form.CommForm;

/**
 * ¡„º˛–≈œ¢form
 * @version 1.1.5.6
 */

public class PartInfoForm extends CommForm  {

	private String stuffNo;
	private String skuCode;
	private String shortCode;
	private String standard;
	private String buyCost1;
	private String saleCost1;
	private Double buyCost;
	private Double saleCost;
	private String remark;
	private String skuUnit;
	private String stockFlag;
	private String partType;
	
	
	public Double getBuyCost() {
		return buyCost;
	}
	public void setBuyCost(Double buyCost) {
		this.buyCost = buyCost;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Double getSaleCost() {
		return saleCost;
	}
	public void setSaleCost(Double saleCost) {
		this.saleCost = saleCost;
	}
	public String getShortCode() {
		return shortCode;
	}
	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	public String getSkuUnit() {
		return skuUnit;
	}
	public void setSkuUnit(String skuUnit) {
		this.skuUnit = skuUnit;
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
	public String getBuyCost1() {
		return buyCost1;
	}
	public void setBuyCost1(String buyCost1) {
		this.buyCost1 = buyCost1;
	}
	public String getSaleCost1() {
		return saleCost1;
	}
	public void setSaleCost1(String saleCost1) {
		this.saleCost1 = saleCost1;
	}
	public String getStockFlag() {
		return stockFlag;
	}
	public void setStockFlag(String stockFlag) {
		this.stockFlag = stockFlag;
	}
	public String getPartType() {
		return partType;
	}
	public void setPartType(String partType) {
		this.partType = partType;
	}
	
	

	 

}