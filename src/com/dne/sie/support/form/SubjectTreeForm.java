package com.dne.sie.support.form;

import java.util.Date;

import org.apache.struts.action.ActionForm;

public class SubjectTreeForm extends ActionForm {
	
	 private Long subjectId;
     private String subjectName;
     private Long parentId;
     private Long orderId;
     private Integer layer;
     private String remark;
     private Date createDate;
     private Integer delFlag;
     private Integer reportFlag;
     
     
	public Long getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Integer getLayer() {
		return layer;
	}
	public void setLayer(Integer layer) {
		this.layer = layer;
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
	public Integer getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	public Integer getReportFlag() {
		return reportFlag;
	}
	public void setReportFlag(Integer reportFlag) {
		this.reportFlag = reportFlag;
	}
    
     
}
