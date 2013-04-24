package com.dne.sie.stock.form;

import com.dne.sie.util.form.CommForm;
import java.util.Date;


/**
 * 出入库流水Form处理类
 * @author xt
 * @version 1.1.5.6
 * @see StockFlowForm.java <br>
 */
public class StockFlowForm extends CommForm{

	//pk
	private Long flowId;
	//零件名称
	private String skuCode;
	//	简称
	private String shortCode;
	//	规格
	private String standard;
	//	料号
	private String stuffNo;
	//单位
	private String skuUnit;
	//剩余数量（出入之后的现存数量）		
	private Integer restNum;
	//零件数量		
	private Integer skuNum;
	
	//成本单价(RMB)	
	private Float perCost;

    //进价($)
    private Float orderDollar;
    //TW运费(RMB)
    private Float freightTW;

    //运输方式
    private String transportMode;
    
	//销售总价
	private Float salePrice;
	//零件属性		
	private String skuType;
	//备注			
	private String remark;
	//客户			
	private String customerName;
	//仓位
	private String binCode;
	//出入库类型		
	private String flowType;
	//出入库项目		
	private String flowItem;
	//出入库日期		
	private Date createDate;
	//发票号
	private String invoiceNo;
	
	private Long updateBy;
	private Long createBy;
	private Date updateDate;
	
	private Long inFlowNo;
	private String feeType;
	private Long requestId;
	private String formNo;
	
	private Integer restNumTax;
	

	/****************for flowQuery start ************/
     
     private String dateScope;
     //入库起始日期
     private String inOutDate1;
     //入库终止日期
 	 private String inOutDate2;
 	 
 	private String strSkuNum;
 	private String strPerCost;
 	
 	/****************for flowQuery end ************/
 	
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
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getDateScope() {
		return dateScope;
	}
	public void setDateScope(String dateScope) {
		this.dateScope = dateScope;
	}
	public Long getFlowId() {
		return flowId;
	}
	public void setFlowId(Long flowId) {
		this.flowId = flowId;
	}
	public String getFlowItem() {
		return flowItem;
	}
	public void setFlowItem(String flowItem) {
		this.flowItem = flowItem;
	}
	public String getFlowType() {
		return flowType;
	}
	public void setFlowType(String flowType) {
		this.flowType = flowType;
	}
	public Long getInFlowNo() {
		return inFlowNo;
	}
	public void setInFlowNo(Long inFlowNo) {
		this.inFlowNo = inFlowNo;
	}
	public String getInOutDate1() {
		return inOutDate1;
	}
	public void setInOutDate1(String inOutDate1) {
		this.inOutDate1 = inOutDate1;
	}
	public String getInOutDate2() {
		return inOutDate2;
	}
	public void setInOutDate2(String inOutDate2) {
		this.inOutDate2 = inOutDate2;
	}
	public Float getPerCost() {
		return perCost;
	}
	public void setPerCost(Float perCost) {
		this.perCost = perCost;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public String getStrPerCost() {
		return strPerCost;
	}
	public void setStrPerCost(String strPerCost) {
		this.strPerCost = strPerCost;
	}
	public String getStrSkuNum() {
		return strSkuNum;
	}
	public void setStrSkuNum(String strSkuNum) {
		this.strSkuNum = strSkuNum;
	}
	public String getStuffNo() {
		return stuffNo;
	}
	public void setStuffNo(String stuffNo) {
		this.stuffNo = stuffNo;
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
	public Integer getRestNum() {
		return restNum;
	}
	public void setRestNum(Integer restNum) {
		this.restNum = restNum;
	}
	public Float getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(Float salePrice) {
		this.salePrice = salePrice;
	}
	public String getFeeType() {
		return feeType;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	public Long getRequestId() {
		return requestId;
	}
	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}
	public String getBinCode() {
		return binCode;
	}
	public void setBinCode(String binCode) {
		this.binCode = binCode;
	}
	public String getFormNo() {
		return formNo;
	}
	public void setFormNo(String formNo) {
		this.formNo = formNo;
	}
	public Float getOrderDollar() {
		return orderDollar;
	}
	public void setOrderDollar(Float orderDollar) {
		this.orderDollar = orderDollar;
	}
	public Float getFreightTW() {
		return freightTW;
	}
	public void setFreightTW(Float freightTW) {
		this.freightTW = freightTW;
	}
	public String getTransportMode() {
		return transportMode;
	}
	public void setTransportMode(String transportMode) {
		this.transportMode = transportMode;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public Integer getRestNumTax() {
		return restNumTax;
	}
	public void setRestNumTax(Integer restNumTax) {
		this.restNumTax = restNumTax;
	}
	
	

}
