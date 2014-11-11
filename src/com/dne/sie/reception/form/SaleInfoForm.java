package com.dne.sie.reception.form;

import java.util.Date;

import com.dne.sie.common.tools.Operate;
import com.dne.sie.util.form.CommForm;

public class SaleInfoForm extends CommForm{

    private String saleNo;             			//询价单号
    private String customerId;         			//客户ID
    private String customerName;       			//客户名称
    private String customerPhone;      			//客户电话
    private String linkman;						//联系人
    private String shippingAddress;    			//交货地点
    private Integer partNum;            		//零件总数量
    private Float purchasePrice;      			//进价
    private Float tariffAmount;             	//关税_金额

    private Float vat;                			//海关增值税_金额
    private Float customsChargesPlan; 			//报关费_计划(已废弃)
    private Float customsChargesReal; 			//代理费
    private Float domesticFreightPlan;			//国内运费_计划
    private Float domesticFreightReal;			//国内运费_实际
    private Float costPlan;           			//成本_计划
    private Float costReal;           			//成本_实际
    private Float totalQuote;         			//零件总报价
    //	private Float saleTotalPrice;     			//销售总价
    private Float repairFeePlan;      			//维修费_计划
    private Float repairFeeReal;      			//维修费_实际
    private Float repairQuote;      			//维修费_报价
    private Float repairProfit;					//维修费_利润

    private Float profitPlan;         			//利润_计划
    private Float profitReal;         			//利润_实际

    private String deliveryPlaceTw;    			//台湾交货地点

    private String invoiceType;         		//发票类型
    private String saleStatus;         			//销售单状态
    private String payStatus;          			//付款状态
    private String billingStatus;      			//开票状态
    private Float billingMoney;       			//开票金额
    private Float totalPay;           			//客户已付总额
    private String shippingRemark;     			//发货备注
    private String remark;             			//备注
    private int version;
    private Integer delFlag;
    private Float exchangeRate;             //美元汇率
    private String factoryId;			//厂商ID
    private String factoryName;			//厂商名称

    private Date createDate;
    private Long updateBy;
    private Long createBy;
    private Date updateDate;

    private String skuCode;
    private String stuffNo;
    private String modelCode;
    private String modelSerialNo;            //机身编码
    private String orderMonth;  					//订单月份
    private String invoiceNo1;  			//发票起始号
    private String invoiceNo2;  			//发票终止号

    private String dsaleNo;

    private Long repairNo;		//FK
    private String serviceSheetNo;			//关联维修单
    private String warrantyType;			//对应维修单 保内/保外
    private Float stockCost;				//库存成本总价(RMB)

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
