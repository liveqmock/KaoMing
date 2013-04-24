package com.dne.sie.repair.form;

import java.util.Date;

import com.dne.sie.util.form.CommForm;

public class RepairFeeInfoForm extends CommForm{
	
	private Long accountId;								//pk
	private int version;								
	private Long repairNo;								//fk
	private String feeType;								//�������ͣ��ƻ�/ʵ�ʣ�
	
	private String warrantyType;							//���ڱ���
	private Integer repairmanNum;							//����
	private Integer workingHours;							//��ʱ(��)
	
	private Double travelCosts;								//���÷ѳɱ�
	private Double ticketsAllCosts;							//����Ʊ
	private Double laborCosts;								//�˹���
	private Double taxes;									//˰��
	private Double totalCost;								//�ܳɱ�
	private Double repairQuote;								//�ܱ���
	private Double repairProfit;							//����
	
	
	private Long createBy;      	            
	private Date createDate;                  
	private Long updateBy;                    
	private Date updateDate;
	
	
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	public Long getRepairNo() {
		return repairNo;
	}
	public void setRepairNo(Long repairNo) {
		this.repairNo = repairNo;
	}
	public String getFeeType() {
		return feeType;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	public String getWarrantyType() {
		return warrantyType;
	}
	public void setWarrantyType(String warrantyType) {
		this.warrantyType = warrantyType;
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
	public Double getTravelCosts() {
		return travelCosts;
	}
	public void setTravelCosts(Double travelCosts) {
		this.travelCosts = travelCosts;
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
	public Double getTaxes() {
		return taxes;
	}
	public void setTaxes(Double taxes) {
		this.taxes = taxes;
	}
	public Double getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}
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
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public Double getRepairQuote() {
		return repairQuote;
	}
	public void setRepairQuote(Double repairQuote) {
		this.repairQuote = repairQuote;
	}
	public Double getRepairProfit() {
		return repairProfit;
	}
	public void setRepairProfit(Double repairProfit) {
		this.repairProfit = repairProfit;
	}
	
	
	
	
}
