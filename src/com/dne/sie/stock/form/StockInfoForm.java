package com.dne.sie.stock.form;

import java.util.Date;
import com.dne.sie.util.form.CommForm;



/**
 * �����ϢForm������
 * @author xt
 * @version 1.1.5.6
 * @see StockInfoForm.java <br>
 */
public class StockInfoForm   extends CommForm  {


/****************Fields ************   */ 
	
	 //pk
     private Long stockId;
     //�������
     private String skuCode;
     //���
     private String shortCode;
     //���
     private String standard;
     //�Ϻ�
     private String stuffNo;
     //��λ
     private String skuUnit;
     //�������
     private Integer skuNum;
     //�ɱ�����(RMB)
     private Float perCost;
     
     //����($)
     private Float orderDollar;
     //TW�˷�(RMB)
     private Float freightTW;

     //���䷽ʽ
     private String transportMode;
     
     //�������(A,B)
     private String skuType;
     //��ע
     private String remark;
     //��λ
     private String binCode;
     //���״̬
     private String stockStatus;
     //�����ˮ��
     private Long flowNo;
     //��Ʊ��
     private String invoiceNo;
     
     private Integer  skuNumTax;
     private Float perCostTax;
    
	 
	 //�����
     private Long createBy;
     //��������
     private Date createDate;
     private Long updateBy;
     private Date updateDate;
     
     
	//****************for stockInfoListQuery start ************
     
     private String dateScope;
     //�����ʼ����
     private String inDate1;
     //�����ֹ����
 	 private String inDate2;
 	 
 	private Double realCost1;	//��ͳɱ�
	private Double realCost2;	//��߳ɱ�
	
	private String binCode1;	//��ʼ��λ��
	private String binCode2;	//��ֹ��λ��
	 
 	 
 	private String strSkuNum;
 	private String strPerCost;
 	private Long requestId;
 	
 	 //****************for stockInfoListQuery end ************
 	 
 	 
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
	public Long getFlowNo() {
		return flowNo;
	}
	public void setFlowNo(Long flowNo) {
		this.flowNo = flowNo;
	}
	public String getInDate1() {
		return inDate1;
	}
	public void setInDate1(String inDate1) {
		this.inDate1 = inDate1;
	}
	public String getInDate2() {
		return inDate2;
	}
	public void setInDate2(String inDate2) {
		this.inDate2 = inDate2;
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
	
	public String getSkuUnit() {
		return skuUnit;
	}
	public void setSkuUnit(String skuUnit) {
		this.skuUnit = skuUnit;
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
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	public Long getStockId() {
		return stockId;
	}
	public void setStockId(Long stockId) {
		this.stockId = stockId;
	}
	public String getStockStatus() {
		return stockStatus;
	}
	public void setStockStatus(String stockStatus) {
		this.stockStatus = stockStatus;
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
	public String getDateScope() {
		return dateScope;
	}
	public void setDateScope(String dateScope) {
		this.dateScope = dateScope;
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
	public String getSkuType() {
		return skuType;
	}
	public void setSkuType(String skuType) {
		this.skuType = skuType;
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
	public Double getRealCost1() {
		return realCost1;
	}
	public void setRealCost1(Double realCost1) {
		this.realCost1 = realCost1;
	}
	public Double getRealCost2() {
		return realCost2;
	}
	public void setRealCost2(Double realCost2) {
		this.realCost2 = realCost2;
	}
	public String getBinCode1() {
		return binCode1;
	}
	public void setBinCode1(String binCode1) {
		this.binCode1 = binCode1;
	}
	public String getBinCode2() {
		return binCode2;
	}
	public void setBinCode2(String binCode2) {
		this.binCode2 = binCode2;
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