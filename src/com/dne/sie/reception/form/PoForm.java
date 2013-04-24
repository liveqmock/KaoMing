package com.dne.sie.reception.form;

import java.util.Date;
import com.dne.sie.util.form.CommForm;

public class PoForm extends CommForm{
	
	private Long poNo;			//PO���
	private String stuffNo;			//�Ϻ�
	private String skuCode;			//�������
	private String skuUnit;			//��λ
	private Float perQuote;			//���۵���(����)(ѯ��ʱ��̨�屨��)
	private Float perCost;			//ʵ�ʵ���(RMB)(�ջ�ʱ��ȷ��)
	private Integer orderNum;		//��������
	private String modelCode;		//����
	private String modelSerialNo;	//�������
	private String orderType;		//��������(���ڱ���)
	private Date sendDate;			//��������
	private Date receiveDate;		//��������
	private String orderStatus;		//����״̬
	private String saleNo;			//����
	private String customerId;		//�ͻ�ID
	private String customerName;	//�ͻ�����
	private String remark;			//��ע
	int version;
	private Long requestId;
	private String orderNo;			//��������
	private String deliveryTime;			//����ʱ��
	private String shippingAddress;			//�����ص�
	private String factoryId;			//����ID 
	private String factoryName;			//��������    
	private String warrantyType; 	
	
	private String  transportMode;		//���䷽ʽ

	private Date createDate;
	private Long updateBy;
	private Long createBy;
	private Date updateDate;
	
	private String orderMonth;  					//�����·�
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
