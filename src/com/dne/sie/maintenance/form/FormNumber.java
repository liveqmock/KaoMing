package com.dne.sie.maintenance.form;

import com.dne.sie.util.form.CommForm;

public class FormNumber extends CommForm{
	
	private Long FormNumberId;
	private String formType;
	private String formDate;
	private Integer formSeq;
	
	
	public Integer getFormSeq() {
		return formSeq;
	}
	public void setFormSeq(Integer formSeq) {
		this.formSeq = formSeq;
	}
	public Long getFormNumberId() {
		return FormNumberId;
	}
	public void setFormNumberId(Long formNumberId) {
		FormNumberId = formNumberId;
	}
	public String getFormType() {
		return formType;
	}
	public void setFormType(String formType) {
		this.formType = formType;
	}
	public String getFormDate() {
		return formDate;
	}
	public void setFormDate(String formDate) {
		this.formDate = formDate;
	}
	
	
	

}
