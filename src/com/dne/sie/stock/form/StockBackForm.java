package com.dne.sie.stock.form;

import java.util.Date;

import org.apache.struts.action.ActionForm;

/**
 * 待回库数据表单，纯VO
 * @author XT
 *
 */
public class StockBackForm extends ActionForm {
	
	private Long partsId;
	
	private String formNo;
	//零件名称
	private String skuCode;
	
	private String standard;
	//	料号
	private String stuffNo;
	//单位
	private String skuUnit;
	//零件数量		
	private Integer skuNum;
	//回库类型
	private String stockBackItem;
	//回库申请人
	private Long applyUser;
	//申请日期
	private Date applyDate;
	
	private String binCode;
	
	private String partsIds;
	private String stockBackItemBak;

	public Long getPartsId() {
		return partsId;
	}

	public void setPartsId(Long partsId) {
		this.partsId = partsId;
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

	public String getStockBackItem() {
		return stockBackItem;
	}

	public void setStockBackItem(String stockBackItem) {
		this.stockBackItem = stockBackItem;
	}

	public Long getApplyUser() {
		return applyUser;
	}

	public void setApplyUser(Long applyUser) {
		this.applyUser = applyUser;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public String getFormNo() {
		return formNo;
	}

	public void setFormNo(String formNo) {
		this.formNo = formNo;
	}

	public String getPartsIds() {
		return partsIds;
	}

	public void setPartsIds(String partsIds) {
		this.partsIds = partsIds;
	}

	public String getStockBackItemBak() {
		return stockBackItemBak;
	}

	public void setStockBackItemBak(String stockBackItemBak) {
		this.stockBackItemBak = stockBackItemBak;
	}

	public String getBinCode() {
		return binCode;
	}

	public void setBinCode(String binCode) {
		this.binCode = binCode;
	}
	
	
	
	

}
