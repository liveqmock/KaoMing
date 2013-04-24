package com.dne.sie.support.form;

import java.util.Date;

import com.dne.sie.util.form.CommForm;

public class BugQaFlowForm extends CommForm{
	
	private Long flowId;
	private Long bugId;
	private String description;
	private String status;
	
	private Date createDate;
	private Long createBy;
	
	private String createUserName;

	public Long getFlowId() {
		return flowId;
	}

	public void setFlowId(Long flowId) {
		this.flowId = flowId;
	}

	public Long getBugId() {
		return bugId;
	}

	public void setBugId(Long bugId) {
		this.bugId = bugId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	
	
	
	
	

}
