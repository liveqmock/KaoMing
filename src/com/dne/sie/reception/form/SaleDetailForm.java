package com.dne.sie.reception.form;

import java.util.Date;

import com.dne.sie.util.form.CommForm;

public class SaleDetailForm extends CommForm{

    private Long saleDetailId;             //明细ID
    private String saleNo;                   //询价单号
    private String stuffNo;                  //料号
    private String skuCode;                  //零件名称
    private String skuCodeT;
    private String shortCode;                //简称
    private String standard;                 //规格
    private String skuUnit;                  //单位
    private Integer partNum;                  //数量
    private String modelCode;                //机型
    private String modelSerialNo;            //机身编码
    private Float purchasePrice;            //台湾报价(RMB)	(是根据purchaseDollar和汇率exchangeRate转换后的台湾RMB报价)
    private Float tariffAmount;             //关税_金额
    private Float tariffRat;                //关税_税率
    private Float vat;                      //海关增值税_金额
    private Float customsChargesPlan;       //报关费_计划(已废弃)
    private Float customsChargesReal;       //代理费
    private Float domesticFreightPlan;      //国内运费_计划
    private Float domesticFreightReal;      //国内运费_实际
    private Float costPlan;                 //成本_计划
    private Float costReal;                 //成本_实际
    private Float perQuote;                 //报价单价(RMB)	(对客户)
    private Float salePerPrice;             //实际销售单价(RMB) (对客户)
    private Float profitPlan;               //利润_计划
    private Float profitReal;               //利润_实际
    private String partStatus;               //零件状态
    private String deliveryTimeStart;        //交货起始时间
    private String deliveryTimeEnd;          //交货截至时间
    private String annexIds;                 //附件IDs
    private Long poNo;                     //订购单号
    private Float purchaseDollar;             //台湾报价（美元）
    private String orderType;                //订购类型
    private String warrantyType;              	//保内/外
    private String asnNo; 					//发货单号
    private Float stockCost;				//库存成本单价(RMB)

    private Long partsId;

    private Long rootId;
    private int version;
    private Integer delFlag;

    private Date createDate;
    private Long updateBy;
    private Long createBy;
    private Date updateDate;

    private String dateScope;
    private String inDate1;
    private String inDate2;

    public Long getSaleDetailId() {
        return saleDetailId;
    }
    public void setSaleDetailId(Long saleDetailId) {
        this.saleDetailId = saleDetailId;
    }
    public String getSaleNo() {
        return saleNo;
    }
    public void setSaleNo(String saleNo) {
        this.saleNo = saleNo;
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
    public String getShortCode() {
        return shortCode;
    }
    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }
    public String getStandard() {
        return standard;
    }
    public void setStandard(String standard) {
        this.standard = standard;
    }
    public String getSkuUnit() {
        return skuUnit;
    }
    public void setSkuUnit(String skuUnit) {
        this.skuUnit = skuUnit;
    }
    public Integer getPartNum() {
        return partNum;
    }
    public void setPartNum(Integer partNum) {
        this.partNum = partNum;
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
    public Float getPerQuote() {
        return perQuote;
    }
    public void setPerQuote(Float perQuote) {
        this.perQuote = perQuote;
    }
    public Float getSalePerPrice() {
        return salePerPrice;
    }
    public void setSalePerPrice(Float salePerPrice) {
        this.salePerPrice = salePerPrice;
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
    public String getPartStatus() {
        return partStatus;
    }
    public void setPartStatus(String partStatus) {
        this.partStatus = partStatus;
    }
    public String getDeliveryTimeStart() {
        return deliveryTimeStart;
    }
    public void setDeliveryTimeStart(String deliveryTimeStart) {
        this.deliveryTimeStart = deliveryTimeStart;
    }
    public String getDeliveryTimeEnd() {
        return deliveryTimeEnd;
    }
    public void setDeliveryTimeEnd(String deliveryTimeEnd) {
        this.deliveryTimeEnd = deliveryTimeEnd;
    }
    public String getAnnexIds() {
        return annexIds;
    }
    public void setAnnexIds(String annexIds) {
        this.annexIds = annexIds;
    }
    public Long getPoNo() {
        return poNo;
    }
    public void setPoNo(Long poNo) {
        this.poNo = poNo;
    }

    public Float getPurchaseDollar() {
        return purchaseDollar;
    }
    public void setPurchaseDollar(Float purchaseDollar) {
        this.purchaseDollar = purchaseDollar;
    }
    public int getVersion() {
        return version;
    }
    public void setVersion(int version) {
        this.version = version;
    }
    public Integer getDelFlag() {
        return delFlag;
    }
    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
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
    public String getOrderType() {
        return orderType;
    }
    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
    public Long getRootId() {
        return rootId;
    }
    public void setRootId(Long rootId) {
        this.rootId = rootId;
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
    public String getDateScope() {
        return dateScope;
    }
    public void setDateScope(String dateScope) {
        this.dateScope = dateScope;
    }
    public Float getTariffAmount() {
        return tariffAmount;
    }
    public void setTariffAmount(Float tariff) {
        this.tariffAmount = tariff;
    }
    public Float getTariffRat() {
        return tariffRat;
    }
    public void setTariffRat(Float tariffRat) {
        this.tariffRat = tariffRat;
    }
    public String getAsnNo() {
        return asnNo;
    }
    public void setAsnNo(String asnNo) {
        this.asnNo = asnNo;
    }
    public String getSkuCodeT() {
        return skuCodeT;
    }
    public void setSkuCodeT(String skuCodeT) {
        this.skuCodeT = skuCodeT;
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
    public Long getPartsId() {
        return partsId;
    }
    public void setPartsId(Long partsId) {
        this.partsId = partsId;
    }


}
