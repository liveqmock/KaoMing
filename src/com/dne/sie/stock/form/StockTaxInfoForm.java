package com.dne.sie.stock.form;

import com.dne.sie.util.form.CommForm;

public class StockTaxInfoForm  extends CommForm  {
	

	 //pk
    private Long stockId;
    //�������
    private String skuCode;
    //�Ϻ�
    private String stuffNo;
    //�������
    private Integer skuNumTax;
    //�ɱ�����(RMB)
    private Float perCostTax;
    
    
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
	public String getStuffNo() {
		return stuffNo;
	}
	public void setStuffNo(String stuffNo) {
		this.stuffNo = stuffNo;
	}
	public Integer getSkuNumTax() {
		return skuNumTax;
	}
	public void setSkuNumTax(Integer skuNumTax) {
		this.skuNumTax = skuNumTax;
	}
	public Float getPerCostTax() {
		return perCostTax;
	}
	public void setPerCostTax(Float perCostTax) {
		this.perCostTax = perCostTax;
	}
    
    
    

}
