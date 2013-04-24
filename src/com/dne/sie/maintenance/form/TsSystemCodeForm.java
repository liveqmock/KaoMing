package com.dne.sie.maintenance.form;

import org.apache.struts.action.ActionForm;


/**
 * 系统代码字典表form
 * @version 1.1.5.6
 */
public class TsSystemCodeForm extends ActionForm {


    // Fields    
     /*
      * 系统代码ID
      */
     private Long systemId;
     /*
      * 代码类型
      */
     private String systemType;
     /*
      * 代码类型说明
      */
     private String systemTypeDesc;
     /*
      * 系统代码
      */
     private String systemCode;
     /*
      * 代码中文描述
      */
     private String systemName;
     /*
      * 代码英文描述
      */
     private String systemDesc;




   
    // Property accessors

	/**
	 * @return Long 系统代码ID
	 */
    public Long getSystemId() {
        return this.systemId;
    }

	/**
	 * @param systemId Long 系统代码ID
	 */
    public void setSystemId(Long systemId) {
        this.systemId = systemId;
    }

	/**
	 * @return String 代码类型
	 */
    public String getSystemType() {
        return this.systemType;
    }

	/**
	 * @param systemType String 代码类型
	 */
    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }

	/**
	 * @return String 代码类型说明
	 */
    public String getSystemTypeDesc() {
        return this.systemTypeDesc;
    }

	/**
	 * @param systemTypeDesc String 代码类型说明
	 */
    public void setSystemTypeDesc(String systemTypeDesc) {
        this.systemTypeDesc = systemTypeDesc;
    }

	/**
	 * @return String 系统代码
	 */
    public String getSystemCode() {
        return this.systemCode;
    }

	/**
	 * @param systemCode String 系统代码
	 */
    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

	/**
	 * @return String 代码中文描述
	 */
    public String getSystemName() {
        return this.systemName;
    }

	/**
	 * @param systemName String 代码中文描述
	 */
    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

	/**
	 * @return String 代码英文描述
	 */
    public String getSystemDesc() {
        return this.systemDesc;
    }

	/**
	 * @param systemDesc String 代码英文描述
	 */
    public void setSystemDesc(String systemDesc) {
        this.systemDesc = systemDesc;
    }



}