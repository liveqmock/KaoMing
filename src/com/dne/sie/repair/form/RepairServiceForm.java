package com.dne.sie.repair.form;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.dne.sie.maintenance.form.CustomerInfoForm;
import com.dne.sie.util.form.CommForm;

public class RepairServiceForm extends CommForm{
	
	private Long repairNo;						//pk			
	private String serviceSheetNo;            //ά�޵���
	private String saleNo;
//	private String customerId;
	private String linkman;                		//��ϵ��
	private String phone;                   	//�绰
	private String fax;                     	//����
	private String modelCode;                 //����
	private String serialNo;                  //������
	private String manufacture;                 //��������
	private String repairProperites;          //ά������
	private String warrantyType;              //��������
//	private String invoiceNo;                 //��Ʊ��
//	private Double invoiceFee;                //��Ʊ���
	private String warrantyCardNo;            //������
	private Date purchaseDate;                //��������
	private Date extendedWarrantyDate;
	private String confirmSymptom;            //ά��Աȷ�Ϲ���
	private String repairContent;             //ά������
	private String unRepairReason;            //������ԭ��
	private String unQuickReason;             //���ܼ�ʱά��ԭ��
	private String unCompleteQuickStatus;     //δ��ɼ�ʱ�޸�״̬
	private String crReason;                  //������ԭ��
	private Long lastRepairNo;                //�ϴ�ά�޺�
	private Date customerVisitDate;          	//�ͻ�Ҫ����������
//	private Date customerRepairDate;          //�ͻ�Ҫ���޸�����
	private Date estimateRepairDate;          //Ԥ���޸�����
	private Date actualOnsiteDate; 				//ʵ����������
	private Date actualRepairedDate;			//ʵ���޸�����
	
	private String customerTrouble;      	    //�ͻ���������
//	private String accessoryList;             //���������Ϣ
//	private String repairSource;              //ά����Դ
	private String rr90;                      //ԭ���Ϸ��ޣ�90�죩
	private String receptionRemark;           //��ע
	private Long operaterId;                  //�Ǽ���
	
	private String purchaseDateStr;              //��������
	private String customerVisitDateStr;          	//�ͻ�Ҫ�󵽴�����
	private String estimateRepairDateStr;          //Ԥ���޸�����
	private String actualOnsiteDateStr; 				//ʵ����������
	private String actualRepairedDateStr;			//ʵ���޸�����
	private String extendedWarrantyDateStr;
	private String turningDateStr;
	
	private String currentStatus;			  //ά�޵�״̬
	private Double repairFee;                 //ά�޷�
	private Double partsFee;                 //�����
	private Double vat;                 		//��ֵ˰
	private Double totalFee;                 	//�ܽ��
	private String paymentContent;				//���ʽ
	private String alarmNo;						//������
	private String troublePlace;				//����λ��
	

	private String dzReason;				//����ԭ��
	private String dzLinkman;				//������ϵ��
	private String dzPhone;					//����绰
	private String dzResult;				//������
	private Integer expectedDuration;		//Ԥ�ƹ���
	private Integer actualDuration;			//ʵ�ʹ���
	private String dzRemark;				//���ﱸע
	private String carryParts;				//Я�����
	private String carryTools;				//Я������
	
	private String leaveProblem;			//��������
	private String tobeMatter;				//δ������
//	private String workTime;				//ʱ��
//	private String workContent;				//��������
//	private String workPerformance;			//������
//	private String parameterModify;			//�����޸�
	

	private String jwFoundation;
	private String jwDoc;
	private String jwDocMissing;
	private Date turningDate ;
	private Double jwServiceCharge;
	private Double jwCarfare;
	
	private String atPlc;			//PLC(��������)
	private String atMissParts;     //��еȱʧ���(��������)
	private String atCircuit;       //��·(��������)
	private String atOthers;        //����(��������)
	private String atPrecision;     //���ξ���
	private String atUHW0;          //��ĸ��
	private String atSHW0;          //��ĸ��
	private String atUHW90;         //
	private String atSHW90;         //
	private String atUHW180;        //
	private String atSHW180;        //
	private String atUHW270;        //
	private String atSHW270;        //
	private String atTrain;         //��ѵ
	private String atSign;          //ǩ��
	private String atUnsignRemark;  //δǩ˵��
	
	private Long delFlag;                     
	private Long createBy;      	            
	private java.util.Date createDate;                  
	private Long updateBy;                    
	private java.util.Date updateDate;    
	
	private int version;               
	                                  
	private CustomerInfoForm customInfoForm = new CustomerInfoForm();

	private Set<RepairManInfoForm> repairManSetInfo = new HashSet<RepairManInfoForm>();
	private Set<RepairServiceStatusForm> serviceStatusSet = new HashSet<RepairServiceStatusForm>();
	
	private Integer repairmanNum;							//Ԥ������
	private Integer workingHours;							//��ʱ(��)
	private Double ticketsAllCosts;							//����Ʊ
	private Double laborCosts;								//�˹���
	
	
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
