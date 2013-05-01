package com.dne.sie.repair.form;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.dne.sie.maintenance.form.CustomerInfoForm;
import com.dne.sie.util.form.CommForm;

public class RepairServiceForm extends CommForm{
	
	private Long repairNo;						//pk			
	private String serviceSheetNo;            //维修单号
	private String saleNo;
//	private String customerId;
	private String linkman;                		//联系人
	private String phone;                   	//电话
	private String fax;                     	//传真
	private String modelCode;                 //机型
	private String serialNo;                  //机身编号
	private String manufacture;                 //生产厂商
	private String repairProperites;          //维修性质
	private String warrantyType;              //保修类型
//	private String invoiceNo;                 //发票号
//	private Double invoiceFee;                //发票金额
	private String warrantyCardNo;            //保卡号
	private Date purchaseDate;                //购机日期
	private Date extendedWarrantyDate;
	private String confirmSymptom;            //维修员确认故障
	private String repairContent;             //维修内容
	private String unRepairReason;            //不修理原因
	private String unQuickReason;             //不能即时维修原因
	private String unCompleteQuickStatus;     //未完成即时修复状态
	private String crReason;                  //再送修原因
	private Long lastRepairNo;                //上次维修号
	private Date customerVisitDate;          	//客户要求上门日期
//	private Date customerRepairDate;          //客户要求修复日期
	private Date estimateRepairDate;          //预定修复日期
	private Date actualOnsiteDate; 				//实际上门日期
	private Date actualRepairedDate;			//实际修复日期
	
	private String customerTrouble;      	    //客户描述故障
//	private String accessoryList;             //随机附件信息
//	private String repairSource;              //维修来源
	private String rr90;                      //原故障返修（90天）
	private String receptionRemark;           //备注
	private Long operaterId;                  //登记人
	
	private String purchaseDateStr;              //验收日期
	private String customerVisitDateStr;          	//客户要求到达日期
	private String estimateRepairDateStr;          //预定修复日期
	private String actualOnsiteDateStr; 				//实际上门日期
	private String actualRepairedDateStr;			//实际修复日期
	private String extendedWarrantyDateStr;
	private String turningDateStr;
	
	private String currentStatus;			  //维修单状态
	private Double repairFee;                 //维修费
	private Double partsFee;                 //零件费
	private Double vat;                 		//增值税
	private Double totalFee;                 	//总金额
	private String paymentContent;				//付款方式
	private String alarmNo;						//报警号
	private String troublePlace;				//故障位置
	

	private String dzReason;				//电诊原因
	private String dzLinkman;				//电诊联系人
	private String dzPhone;					//电诊电话
	private String dzResult;				//电诊结果
	private Integer expectedDuration;		//预计工期
	private Integer actualDuration;			//实际工期
	private String dzRemark;				//电诊备注
	private String carryParts;				//携带零件
	private String carryTools;				//携带工具
	
	private String leaveProblem;			//遗留问题
	private String tobeMatter;				//未尽事宜
//	private String workTime;				//时间
//	private String workContent;				//工作内容
//	private String workPerformance;			//完成情况
//	private String parameterModify;			//参数修改
	

	private String jwFoundation;
	private String jwDoc;
	private String jwDocMissing;
	private Date turningDate ;
	private Double jwServiceCharge;
	private Double jwCarfare;
	
	private String atPlc;			//PLC(质量问题)
	private String atMissParts;     //机械缺失配件(质量问题)
	private String atCircuit;       //电路(质量问题)
	private String atOthers;        //其他(质量问题)
	private String atPrecision;     //几何精度
	private String atUHW0;          //上母线
	private String atSHW0;          //侧母线
	private String atUHW90;         //
	private String atSHW90;         //
	private String atUHW180;        //
	private String atSHW180;        //
	private String atUHW270;        //
	private String atSHW270;        //
	private String atTrain;         //培训
	private String atSign;          //签订
	private String atUnsignRemark;  //未签说明
	
	private Long delFlag;                     
	private Long createBy;      	            
	private java.util.Date createDate;                  
	private Long updateBy;                    
	private java.util.Date updateDate;    
	
	private int version;               
	                                  
	private CustomerInfoForm customInfoForm = new CustomerInfoForm();

	private Set<RepairManInfoForm> repairManSetInfo = new HashSet<RepairManInfoForm>();
	private Set<RepairServiceStatusForm> serviceStatusSet = new HashSet<RepairServiceStatusForm>();
	
	private Integer repairmanNum;							//预计人数
	private Integer workingHours;							//工时(单)
	private Double ticketsAllCosts;							//车船票
	private Double laborCosts;								//人工费
	
	
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
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getModelCode() {
		return modelCode;
	}
	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getManufacture() {
		return manufacture;
	}
	public void setManufacture(String manufacture) {
		this.manufacture = manufacture;
	}
	
	public String getRepairProperites() {
		return repairProperites;
	}
	public void setRepairProperites(String repairProperites) {
		this.repairProperites = repairProperites;
	}
	public String getWarrantyType() {
		return warrantyType;
	}
	public void setWarrantyType(String warrantyType) {
		this.warrantyType = warrantyType;
	}
	
	public String getWarrantyCardNo() {
		return warrantyCardNo;
	}
	public void setWarrantyCardNo(String warrantyCardNo) {
		this.warrantyCardNo = warrantyCardNo;
	}
	public Date getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public String getConfirmSymptom() {
		return confirmSymptom;
	}
	public void setConfirmSymptom(String confirmSymptom) {
		this.confirmSymptom = confirmSymptom;
	}
	public String getRepairContent() {
		return repairContent;
	}
	public void setRepairContent(String repairContent) {
		this.repairContent = repairContent;
	}
	public String getUnRepairReason() {
		return unRepairReason;
	}
	public void setUnRepairReason(String unRepairReason) {
		this.unRepairReason = unRepairReason;
	}
	public String getUnQuickReason() {
		return unQuickReason;
	}
	public void setUnQuickReason(String unQuickReason) {
		this.unQuickReason = unQuickReason;
	}
	public String getUnCompleteQuickStatus() {
		return unCompleteQuickStatus;
	}
	public void setUnCompleteQuickStatus(String unCompleteQuickStatus) {
		this.unCompleteQuickStatus = unCompleteQuickStatus;
	}
	public String getCrReason() {
		return crReason;
	}
	public void setCrReason(String crReason) {
		this.crReason = crReason;
	}
	public Long getLastRepairNo() {
		return lastRepairNo;
	}
	public void setLastRepairNo(Long lastRepairNo) {
		this.lastRepairNo = lastRepairNo;
	}
	public Date getCustomerVisitDate() {
		return customerVisitDate;
	}
	public void setCustomerVisitDate(Date customerVisitDate) {
		this.customerVisitDate = customerVisitDate;
	}

	public Date getEstimateRepairDate() {
		return estimateRepairDate;
	}
	public void setEstimateRepairDate(Date estimateRepairDate) {
		this.estimateRepairDate = estimateRepairDate;
	}
	public Date getActualOnsiteDate() {
		return actualOnsiteDate;
	}
	public void setActualOnsiteDate(Date actualOnsiteDate) {
		this.actualOnsiteDate = actualOnsiteDate;
	}
	public Date getActualRepairedDate() {
		return actualRepairedDate;
	}
	public void setActualRepairedDate(Date actualRepairedDate) {
		this.actualRepairedDate = actualRepairedDate;
	}
	public String getCustomerTrouble() {
		return customerTrouble;
	}
	public void setCustomerTrouble(String customerTrouble) {
		this.customerTrouble = customerTrouble;
	}
	
	public String getRr90() {
		return rr90;
	}
	public void setRr90(String rr90) {
		this.rr90 = rr90;
	}
	
	public Long getOperaterId() {
		return operaterId;
	}
	public void setOperaterId(Long operaterId) {
		this.operaterId = operaterId;
	}
	public String getCurrentStatus() {
		return currentStatus;
	}
	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}
	public Double getRepairFee() {
		return repairFee;
	}
	public void setRepairFee(Double repairFee) {
		this.repairFee = repairFee;
	}
	public Double getPartsFee() {
		return partsFee;
	}
	public void setPartsFee(Double partsFee) {
		this.partsFee = partsFee;
	}
	public Double getVat() {
		return vat;
	}
	public void setVat(Double vat) {
		this.vat = vat;
	}
	public Double getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}
	public String getPaymentContent() {
		return paymentContent;
	}
	public void setPaymentContent(String paymentContent) {
		this.paymentContent = paymentContent;
	}
	public Long getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Long delFlag) {
		this.delFlag = delFlag;
	}
	public Long getCreateBy() {
		return createBy;
	}
	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}
	public java.util.Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}
	public Long getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}
	public java.util.Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(java.util.Date updateDate) {
		this.updateDate = updateDate;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public CustomerInfoForm getCustomInfoForm() {
		return customInfoForm;
	}
	public void setCustomInfoForm(CustomerInfoForm customInfoForm) {
		this.customInfoForm = customInfoForm;
	}


//	public String getCustomerId() {
//		return customerId;
//	}
//	public void setCustomerId(String customerId) {
//		this.customerId = customerId;
//	}
	public Set getRepairManSetInfo() {
		return repairManSetInfo;
	}
	public void setRepairManSetInfo(Set repairManSetInfo) {
		this.repairManSetInfo = repairManSetInfo;
	}
	public Set getServiceStatusSet() {
		return serviceStatusSet;
	}
	public void setServiceStatusSet(Set serviceStatusSet) {
		this.serviceStatusSet = serviceStatusSet;
	}
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	public String getAlarmNo() {
		return alarmNo;
	}
	public void setAlarmNo(String alarmNo) {
		this.alarmNo = alarmNo;
	}
	public String getTroublePlace() {
		return troublePlace;
	}
	public void setTroublePlace(String troublePlace) {
		this.troublePlace = troublePlace;
	}
	public String getPurchaseDateStr() {
		return purchaseDateStr;
	}
	public void setPurchaseDateStr(String purchaseDateStr) {
		this.purchaseDateStr = purchaseDateStr;
	}
	public String getCustomerVisitDateStr() {
		return customerVisitDateStr;
	}
	public void setCustomerVisitDateStr(String customerVisitDateStr) {
		this.customerVisitDateStr = customerVisitDateStr;
	}
	public String getEstimateRepairDateStr() {
		return estimateRepairDateStr;
	}
	public void setEstimateRepairDateStr(String estimateRepairDateStr) {
		this.estimateRepairDateStr = estimateRepairDateStr;
	}
	public String getActualOnsiteDateStr() {
		return actualOnsiteDateStr;
	}
	public void setActualOnsiteDateStr(String actualOnsiteDateStr) {
		this.actualOnsiteDateStr = actualOnsiteDateStr;
	}
	public String getActualRepairedDateStr() {
		return actualRepairedDateStr;
	}
	public void setActualRepairedDateStr(String actualRepairedDateStr) {
		this.actualRepairedDateStr = actualRepairedDateStr;
	}
	public String getDzReason() {
		return dzReason;
	}
	public void setDzReason(String dzReason) {
		this.dzReason = dzReason;
	}
	public String getDzLinkman() {
		return dzLinkman;
	}
	public void setDzLinkman(String dzLinkman) {
		this.dzLinkman = dzLinkman;
	}
	public String getDzPhone() {
		return dzPhone;
	}
	public void setDzPhone(String dzPhone) {
		this.dzPhone = dzPhone;
	}
	public String getDzResult() {
		return dzResult;
	}
	public void setDzResult(String dzResult) {
		this.dzResult = dzResult;
	}
	public Integer getExpectedDuration() {
		return expectedDuration;
	}
	public void setExpectedDuration(Integer expectedDuration) {
		this.expectedDuration = expectedDuration;
	}
	public Integer getActualDuration() {
		return actualDuration;
	}
	public void setActualDuration(Integer actualDuration) {
		this.actualDuration = actualDuration;
	}
	public String getDzRemark() {
		return dzRemark;
	}
	public void setDzRemark(String dzRemark) {
		this.dzRemark = dzRemark;
	}
	public String getCarryParts() {
		return carryParts;
	}
	public void setCarryParts(String carryParts) {
		this.carryParts = carryParts;
	}
	public String getCarryTools() {
		return carryTools;
	}
	public void setCarryTools(String carryTools) {
		this.carryTools = carryTools;
	}
	public String getLeaveProblem() {
		return leaveProblem;
	}
	public void setLeaveProblem(String leaveProblem) {
		this.leaveProblem = leaveProblem;
	}
	public String getTobeMatter() {
		return tobeMatter;
	}
	public void setTobeMatter(String tobeMatter) {
		this.tobeMatter = tobeMatter;
	}
	
	public String getSaleNo() {
		return saleNo;
	}
	public void setSaleNo(String saleNo) {
		this.saleNo = saleNo;
	}
	public Integer getRepairmanNum() {
		return repairmanNum;
	}
	public void setRepairmanNum(Integer repairmanNum) {
		this.repairmanNum = repairmanNum;
	}
	public Integer getWorkingHours() {
		return workingHours;
	}
	public void setWorkingHours(Integer workingHours) {
		this.workingHours = workingHours;
	}
	public Double getTicketsAllCosts() {
		return ticketsAllCosts;
	}
	public void setTicketsAllCosts(Double ticketsAllCosts) {
		this.ticketsAllCosts = ticketsAllCosts;
	}
	public Double getLaborCosts() {
		return laborCosts;
	}
	public void setLaborCosts(Double laborCosts) {
		this.laborCosts = laborCosts;
	}
	public String getJwFoundation() {
		return jwFoundation;
	}
	public void setJwFoundation(String jwFoundation) {
		this.jwFoundation = jwFoundation;
	}
	public String getJwDoc() {
		return jwDoc;
	}
	public void setJwDoc(String jwDoc) {
		this.jwDoc = jwDoc;
	}
	public String getJwDocMissing() {
		return jwDocMissing;
	}
	public void setJwDocMissing(String jwDocMissing) {
		this.jwDocMissing = jwDocMissing;
	}
	public Date getTurningDate() {
		return turningDate;
	}
	public void setTurningDate(Date turningDate) {
		this.turningDate = turningDate;
	}
	public Double getJwServiceCharge() {
		return jwServiceCharge;
	}
	public void setJwServiceCharge(Double jwServiceCharge) {
		this.jwServiceCharge = jwServiceCharge;
	}
	public Double getJwCarfare() {
		return jwCarfare;
	}
	public void setJwCarfare(Double jwCarfare) {
		this.jwCarfare = jwCarfare;
	}
	public String getAtPlc() {
		return atPlc;
	}
	public void setAtPlc(String atPlc) {
		this.atPlc = atPlc;
	}
	public String getAtMissParts() {
		return atMissParts;
	}
	public void setAtMissParts(String atMissParts) {
		this.atMissParts = atMissParts;
	}
	public String getAtCircuit() {
		return atCircuit;
	}
	public void setAtCircuit(String atCircuit) {
		this.atCircuit = atCircuit;
	}
	public String getAtOthers() {
		return atOthers;
	}
	public void setAtOthers(String atOthers) {
		this.atOthers = atOthers;
	}
	public String getAtPrecision() {
		return atPrecision;
	}
	public void setAtPrecision(String atPrecision) {
		this.atPrecision = atPrecision;
	}
	public String getAtUHW0() {
		return atUHW0;
	}
	public void setAtUHW0(String atUHW0) {
		this.atUHW0 = atUHW0;
	}
	public String getAtSHW0() {
		return atSHW0;
	}
	public void setAtSHW0(String atSHW0) {
		this.atSHW0 = atSHW0;
	}
	public String getAtUHW90() {
		return atUHW90;
	}
	public void setAtUHW90(String atUHW90) {
		this.atUHW90 = atUHW90;
	}
	public String getAtSHW90() {
		return atSHW90;
	}
	public void setAtSHW90(String atSHW90) {
		this.atSHW90 = atSHW90;
	}
	public String getAtUHW180() {
		return atUHW180;
	}
	public void setAtUHW180(String atUHW180) {
		this.atUHW180 = atUHW180;
	}
	public String getAtSHW180() {
		return atSHW180;
	}
	public void setAtSHW180(String atSHW180) {
		this.atSHW180 = atSHW180;
	}
	public String getAtUHW270() {
		return atUHW270;
	}
	public void setAtUHW270(String atUHW270) {
		this.atUHW270 = atUHW270;
	}
	public String getAtSHW270() {
		return atSHW270;
	}
	public void setAtSHW270(String atSHW270) {
		this.atSHW270 = atSHW270;
	}
	public String getAtTrain() {
		return atTrain;
	}
	public void setAtTrain(String atTrain) {
		this.atTrain = atTrain;
	}
	public String getAtSign() {
		return atSign;
	}
	public void setAtSign(String atSign) {
		this.atSign = atSign;
	}
	public String getAtUnsignRemark() {
		return atUnsignRemark;
	}
	public void setAtUnsignRemark(String atUnsignRemark) {
		this.atUnsignRemark = atUnsignRemark;
	}
	public Date getExtendedWarrantyDate() {
		return extendedWarrantyDate;
	}
	public void setExtendedWarrantyDate(Date extendedWarrantyDate) {
		this.extendedWarrantyDate = extendedWarrantyDate;
	}
	public String getExtendedWarrantyDateStr() {
		return extendedWarrantyDateStr;
	}
	public void setExtendedWarrantyDateStr(String extendedWarrantyDateStr) {
		this.extendedWarrantyDateStr = extendedWarrantyDateStr;
	}
	public String getTurningDateStr() {
		return turningDateStr;
	}
	public void setTurningDateStr(String turningDateStr) {
		this.turningDateStr = turningDateStr;
	}
	public String getReceptionRemark() {
		return receptionRemark;
	}
	public void setReceptionRemark(String receptionRemark) {
		this.receptionRemark = receptionRemark;
	}


	
	
 
	
}
