package com.dne.sie.reception.form;

import java.util.Date;
import com.dne.sie.util.form.CommForm;

public class PoForm extends CommForm{
	
	private Long poNo;			//PO编号
	private String stuffNo;			//料号
	private String skuCode;			//零件名称
	private String skuUnit;			//单位
	private Float perQuote;			//报价单价(美金)(询价时的台湾报价)
	private Float perCost;			//实际单价(RMB)(收货时再确认)
	private Integer orderNum;		//订购数量
	private String modelCode;		//机型
	private String modelSerialNo;	//机身编码
	private String orderType;		//订单类型(保内保外)
	private Date sendDate;			//发送日期
	private Date receiveDate;		//接收日期
	private String orderStatus;		//订单状态
	private String saleNo;			//单号
	private String customerId;		//客户ID
	private String customerName;	//客户名称
	private String remark;			//备注
	int version;
	private Long requestId;
	private String orderNo;			//订购单号
	private String deliveryTime;			//交货时间
	private String shippingAddress;			//交货地点
	private String factoryId;			//厂商ID 
	private String factoryName;			//厂商名称    
	private String warrantyType; 	
	
	private String  transportMode;		//运输方式

	private Date createDate;
	private Long updateBy;
	private Long createBy;
	private Date updateDate;
	
	private String orderMonth;  					//订单月份
	private String flag; 	
	
	
	public Long getPoNo() {
		return poNo;
	}
	public void setPoNo(Long poNo) {
		this.poNo = poNo;
	}
	public String getStuffNo() {
		return stuffNo;
	}
	public void setStuffNo(String stuffNo) {
		this.stuffNo = stuffNo;
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
	public Float getPerCost() {
		return perCost;
	}
	public void setPerCost(Float perCost) {
		this.perCost = perCost;
	}
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	public String getModelCode() {
		return modelCode;
	}
	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}
	
	public String getModelSerialNo() {
		return modelSerialNo;
	}
	public void setModelSerialNo(String modelSerialNo) {
		this.modelSerialNo = modelSerialNo;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public Date getSendDate() {
		return sendDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	public Date getReceiveDate() {
		return receiveDate;
	}
	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getSaleNo() {
		return saleNo;
	}
	public void setSaleNo(String saleNo) {
		this.saleNo = saleNo;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public Float getPerQuote() {
		return perQuote;
	}
	public void setPerQuote(Float perQuote) {
		this.perQuote = perQuote;
	}
	public Long getRequestId() {
		return requestId;
	}
	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}
	public String getOrderMonth() {
		return orderMonth;
	}
	public void setOrderMonth(String orderMonth) {
		this.orderMonth = orderMonth;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	public String getShippingAddress() {
		return shippingAddress;
	}
	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getFactoryId() {
		return factoryId;
	}
	public void setFactoryId(String factoryId) {
		this.factoryId = factoryId;
	}
	public String getFactoryName() {
		return factoryName;
	}
	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}
	public String getTransportMode() {
		return transportMode;
	}
	public void setTransportMode(String transportMode) {
		this.transportMode = transportMode;
	}
	public String getWarrantyType() {
		return warrantyType;
	}
	public void setWarrantyType(String warrantyType) {
		this.warrantyType = warrantyType;
	}
	
	

}
