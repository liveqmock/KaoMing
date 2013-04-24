package com.dne.sie.reception.form;

import java.util.Date;

import com.dne.sie.common.tools.Operate;
import com.dne.sie.util.form.CommForm;

public class SaleInfoForm extends CommForm{
	
	private String saleNo;             			//ѯ�۵���			
	private String customerId;         			//�ͻ�ID				
	private String customerName;       			//�ͻ�����	
	private String customerPhone;      			//�ͻ��绰	
	private String linkman;						//��ϵ��
	private String shippingAddress;    			//�����ص�			
	private Integer partNum;            		//���������			
	private Float purchasePrice;      			//����		
	private Float tariffAmount;             	//��˰_���		

	private Float vat;                			//������ֵ˰_���		
	private Float customsChargesPlan; 			//���ط�_�ƻ�(�ѷ���)		
	private Float customsChargesReal; 			//�����	
	private Float domesticFreightPlan;			//�����˷�_�ƻ�	
	private Float domesticFreightReal;			//�����˷�_ʵ��	
	private Float costPlan;           			//�ɱ�_�ƻ�			
	private Float costReal;           			//�ɱ�_ʵ��			
	private Float totalQuote;         			//����ܱ���				
//	private Float saleTotalPrice;     			//�����ܼ�			
	private Float repairFeePlan;      			//ά�޷�_�ƻ�		
	private Float repairFeeReal;      			//ά�޷�_ʵ��		
	private Float repairQuote;      			//ά�޷�_����	
	private Float repairProfit;					//ά�޷�_����
	
	private Float profitPlan;         			//����_�ƻ�			
	private Float profitReal;         			//����_ʵ��	
	
	private String deliveryPlaceTw;    			//̨�彻���ص�	

	private String invoiceType;         		//��Ʊ����
	private String saleStatus;         			//���۵�״̬		
	private String payStatus;          			//����״̬			
	private String billingStatus;      			//��Ʊ״̬			
	private Float billingMoney;       			//��Ʊ���			
	private Float totalPay;           			//�ͻ��Ѹ��ܶ�	
	private String shippingRemark;     			//������ע			
	private String remark;             			//��ע					
	private int version;
	private Integer delFlag;    
	private Float exchangeRate;             //��Ԫ����	
	private String factoryId;			//����ID 
	private String factoryName;			//��������    
	
	private Date createDate;
	private Long updateBy;
	private Long createBy;
	private Date updateDate;
	
	private String skuCode;
	private String stuffNo;
	private String modelCode;
	private String modelSerialNo;            //�������	
	private String orderMonth;  					//�����·�
	private String invoiceNo1;  			//��Ʊ��ʼ��
	private String invoiceNo2;  			//��Ʊ��ֹ��
	
	private String dsaleNo;
	
	private Long repairNo;		//FK
	private String serviceSheetNo;			//����ά�޵�
	private String warrantyType;			//��Ӧά�޵� ����/����
	private Float stockCost;				//���ɱ��ܼ�(RMB)
	
	public String getSaleNo() {
		return saleNo;
	}
	public void setSaleNo(String saleNo) {
		this.saleNo = saleNo;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerPhone() {
		return customerPhone;
	}
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}
	public String getShippingAddress() {
		return shippingAddress;
	}
	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public Integer getPartNum() {
		return partNum;
	}
	public void setPartNum(Integer partNum) {
		this.partNum = partNum;
	}
	public Float getPurchasePrice() {
		return purchasePrice;
	}
	public void setPurchasePrice(Float purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	public Float getVat() {
		return vat;
	}
	public void setVat(Float vat) {
		this.vat = vat;
	}
	public Float getCustomsChargesPlan() {
		return customsChargesPlan;
	}
	public void setCustomsChargesPlan(Float customsChargesPlan) {
		this.customsChargesPlan = customsChargesPlan;
	}
	public Float getCustomsChargesReal() {
		return customsChargesReal;
	}
	public void setCustomsChargesReal(Float customsChargesReal) {
		this.customsChargesReal = customsChargesReal;
	}
	public Float getDomesticFreightPlan() {
		return domesticFreightPlan;
	}
	public void setDomesticFreightPlan(Float domesticFreightPlan) {
		this.domesticFreightPlan = domesticFreightPlan;
	}
	public Float getDomesticFreightReal() {
		return domesticFreightReal;
	}
	public void setDomesticFreightReal(Float domesticFreightReal) {
		this.domesticFreightReal = domesticFreightReal;
	}
	public Float getCostPlan() {
		return costPlan;
	}
	public void setCostPlan(Float costPlan) {
		this.costPlan = costPlan;
	}
	public Float getCostReal() {
		return costReal;
	}
	public void setCostReal(Float costReal) {
		this.costReal = costReal;
	}
	public Float getTotalQuote() {
		return totalQuote;
	}
	public void setTotalQuote(Float totalQuote) {
		this.totalQuote = totalQuote;
	}
//	public Float getSaleTotalPrice() {
//		return saleTotalPrice;
//	}
//	public void setSaleTotalPrice(Float saleTotalPrice) {
//		this.saleTotalPrice = saleTotalPrice;
//	}
	public Float getRepairFeePlan() {
		return repairFeePlan;
	}
	public void setRepairFeePlan(Float repairFeePlan) {
		this.repairFeePlan = repairFeePlan;
	}
	public Float getRepairFeeReal() {
		return repairFeeReal;
	}
	public void setRepairFeeReal(Float repairFeeReal) {
		this.repairFeeReal = repairFeeReal;
	}
	public Float getProfitPlan() {
		return profitPlan;
	}
	public void setProfitPlan(Float profitPlan) {
		this.profitPlan = profitPlan;
	}
	public Float getProfitReal() {
		return profitReal;
	}
	public void setProfitReal(Float profitReal) {
		this.profitReal = profitReal;
	}
	public String getDeliveryPlaceTw() {
		return deliveryPlaceTw;
	}
	public void setDeliveryPlaceTw(String deliveryPlaceTw) {
		this.deliveryPlaceTw = deliveryPlaceTw;
	}
	
	public String getInvoiceType() {
		return invoiceType;
	}
	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}
	public String getSaleStatus() {
		return saleStatus;
	}
	public void setSaleStatus(String saleStatus) {
		this.saleStatus = saleStatus;
	}
	public String getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	public String getBillingStatus() {
		return billingStatus;
	}
	public void setBillingStatus(String billingStatus) {
		this.billingStatus = billingStatus;
	}
	public Float getBillingMoney() {
		return billingMoney;
	}
	public void setBillingMoney(Float billingMoney) {
		this.billingMoney = billingMoney;
	}
	public Float getTotalPay() {
		return totalPay;
	}
	public void setTotalPay(Float totalPay) {
		this.totalPay = totalPay;
	}
	public String getShippingRemark() {
		return shippingRemark;
	}
	public void setShippingRemark(String shippingRemark) {
		this.shippingRemark = shippingRemark;
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
	public String getModelCode() {
		return modelCode;
	}
	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}
	
	
	public String getInvoiceNo1() {
		return invoiceNo1;
	}
	public void setInvoiceNo1(String invoiceNo1) {
		this.invoiceNo1 = invoiceNo1;
	}
	public String getInvoiceNo2() {
		return invoiceNo2;
	}
	public void setInvoiceNo2(String invoiceNo2) {
		this.invoiceNo2 = invoiceNo2;
	}
	public String getOrderMonth() {
		return orderMonth;
	}
	public void setOrderMonth(String orderMonth) {
		this.orderMonth = orderMonth;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public Integer getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	public String getDsaleNo() {
		return dsaleNo;
	}
	public void setDsaleNo(String dsaleNo) {
		this.dsaleNo = dsaleNo;
	}
	public Float getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(Float exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	public Float getTariffAmount() {
		return tariffAmount;
	}
	public void setTariffAmount(Float tariffAmount) {
		this.tariffAmount = tariffAmount;
	}
	
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
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
	public Long getRepairNo() {
		return repairNo;
	}
	public void setRepairNo(Long repairNo) {
		this.repairNo = repairNo;
	}
	public String getServiceSheetNo() {
		return serviceSheetNo;
	}
	public void setServiceSheetNo(String serviceSheetNo) {
		this.serviceSheetNo = serviceSheetNo;
	}
	public String getWarrantyType() {
		return warrantyType;
	}
	public void setWarrantyType(String warrantyType) {
		this.warrantyType = warrantyType;
	}
	public Float getStockCost() {
		return stockCost;
	}
	public void setStockCost(Float stockCost) {
		this.stockCost = stockCost;
	}
	
	public String getModelSerialNo() {
		return modelSerialNo;
	}
	public void setModelSerialNo(String modelSerialNo) {
		this.modelSerialNo = modelSerialNo;
	}
	
	
	public Float getRepairQuote() {
		return repairQuote;
	}
	public void setRepairQuote(Float repairQuote) {
		this.repairQuote = repairQuote;
	}
	
	public void setRepairProfit(Float repairProfit) {
		this.repairProfit = repairProfit;
	}
	public Double getRepairProfit() throws Exception{
		return Operate.roundD((this.getRepairQuote()==null?0:this.getRepairQuote()) - (this.getRepairFeePlan()==null?0:this.getRepairFeePlan()),2);
	}
	public Double getTotalQuteWithTax() throws Exception{
		return Operate.roundD((this.getTotalQuote()==null?0:this.getTotalQuote()*1.17) + (this.getRepairQuote()==null?0:this.getRepairQuote()*1.17),2);
	}
	
	
	
}
