package com.dne.sie.maintenance.form;

import com.dne.sie.util.form.CommForm;
import java.util.Date;

/**
 * ≤÷Œªform
 * @version 1.1.5.6
 */
public class StationBinForm extends CommForm  {
	
	 private String binCode;
	 private String binType;
     private Long createBy;
     private Date createDate;
     private Long updateBy;
     private Date updateDate;
	 private Long delFlag;
	 private String isSysCtl;
	 
	public String getBinCode() {
		return binCode;
	}
	public void setBinCode(String binCode) {
		this.binCode = binCode;
	}
	public String getBinType() {
		return binType;
	}
	public void setBinType(String binType) {
		this.binType = binType;
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
	public Long getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Long delFlag) {
		this.delFlag = delFlag;
	}
	public String getIsSysCtl() {
		return isSysCtl;
	}
	public void setIsSysCtl(String isSysCtl) {
		this.isSysCtl = isSysCtl;
	}	
	
	 
	 
	 
}
