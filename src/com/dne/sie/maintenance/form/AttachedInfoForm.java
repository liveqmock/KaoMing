package com.dne.sie.maintenance.form;

import java.util.Date;

import com.dne.sie.util.form.CommForm;

public class AttachedInfoForm extends CommForm{
	
	private Long attachedId;
	private String attachedName;
	private String saveAttachedName;
	private Long fileLength;
	private String memo;
	private String fileType;
	private Long foreignId;

	private Date createDate;
	private Long updateBy;
	private Long createBy;
	private Date updateDate;
	
	
	public Long getAttachedId() {
		return attachedId;
	}
	public void setAttachedId(Long attachedId) {
		this.attachedId = attachedId;
	}
	public String getAttachedName() {
		return attachedName;
	}
	public void setAttachedName(String attachedName) {
		this.attachedName = attachedName;
	}
	public String getSaveAttachedName() {
		return saveAttachedName;
	}
	public void setSaveAttachedName(String saveAttachedName) {
		this.saveAttachedName = saveAttachedName;
	}
	public Long getFileLength() {
		return fileLength;
	}
	public void setFileLength(Long fileLength) {
		this.fileLength = fileLength;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public Long getForeignId() {
		return foreignId;
	}
	public void setForeignId(Long foreignId) {
		this.foreignId = foreignId;
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
	
	

}
