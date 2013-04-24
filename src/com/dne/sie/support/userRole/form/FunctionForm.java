package com.dne.sie.support.userRole.form;

import org.apache.struts.action.ActionForm;
import java.sql.Date;
import java.util.Set;
import java.util.HashSet;



/**
 * 功能点管理Form处理类
 * @author xt
 * @version 1.1.5.6
 * @see FunctionForm.java <br>
 */
public class FunctionForm extends ActionForm{
   private Long id;
   		
	private String functionName       	="";			
	private String functionDesc       	="";			
	private Long parentFunctionCode 	;
	private String functionType       	="";
	//private String delFlag          ="";
	private String selectRole       	="";
	private String link       	="";
	private Integer layer       	;
	private String orderId;
	
   private Long updateBy;
   private Date createDate;
   private Long createBy;
   private Date updateDate;
   
  
   
	public String getOrderId() {
	return orderId;
}
public void setOrderId(String orderId) {
	this.orderId = orderId;
}
	public String getFunctionName(){ return this.functionName; } 
	public String getFunctionDesc(){ return this.functionDesc; } 
	public Long getParentFunctionCode(){ return this.parentFunctionCode; } 
	public String getFunctionType() {  return this.functionType;  } 
 	public String getSelectRole() {  return this.selectRole;  }
	public String getLink() {  return this.link;  }
	public Integer getLayer() {  return this.layer;  }
	//public String getDelFlag() {  return this.delFlag;  }
	
	public void setFunctionName(String temp){ functionName = temp; } 
    public void setFunctionDesc(String temp){  functionDesc = temp; } 
    public void setParentFunctionCode(Long temp){  parentFunctionCode = temp; } 
	public void setFunctionType(String temp) {  functionType = temp;  } 
	public void setSelectRole(String temp) {  selectRole = temp;  } 
	public void setLink(String temp) {  link = temp;  } 
	public void setLayer(Integer temp) {  layer = temp;  } 
	//public void setDelFlag(String temp) {  delFlag = temp;  } 
   
//	   private Set roles = new HashSet();
//	   public void setRoles(Set temp) {  roles = temp;  } 
//	   public Set getRoles() {  return this.roles;  } 
	
	
			 
   /**
   Access method for the id property.
   
   @return   the current value of the id property
	*/
   public Long getId() 
   {
	  return id;
   }
   
   /**
   Sets the value of the id property.
   
   @param aId the new value of the id property
	*/
   public void setId(Long aId) 
   {
	  id = aId;
   }
   
  
   
   /**
   Access method for the updateBy property.
   
   @return   the current value of the updateBy property
	*/
   public Long getUpdateBy() 
   {
	  return updateBy;
   }
   
   /**
   Sets the value of the updateBy property.
   
   @param aUpdateBy the new value of the updateBy property
	*/
   public void setUpdateBy(Long aUpdateBy) 
   {
	  updateBy = aUpdateBy;
   }
   
   /**
   Access method for the createDate property.
   
   @return   the current value of the createDate property
	*/
   public Date getCreateDate() 
   {
	  return createDate;
   }
   
   /**
   Sets the value of the createDate property.
   
   @param aCreateDate the new value of the createDate property
	*/
   public void setCreateDate(Date aCreateDate) 
   {
	  createDate = aCreateDate;
   }
   
   /**
   Access method for the createBy property.
   
   @return   the current value of the createBy property
	*/
   public Long getCreateBy() 
   {
	  return createBy;
   }
   
   /**
   Sets the value of the createBy property.
   
   @param aCreateBy the new value of the createBy property
	*/
   public void setCreateBy(Long aCreateBy) 
   {
	  createBy = aCreateBy;
   }
   
   /**
   Access method for the updateDate property.
   
   @return   the current value of the updateDate property
	*/
   public Date getUpdateDate() 
   {
	  return updateDate;
   }
   
   /**
   Sets the value of the updateDate property.
   
   @param aUpdateDate the new value of the updateDate property
	*/
   public void setUpdateDate(Date aUpdateDate) 
   {
	  updateDate = aUpdateDate;
   }
	

}
