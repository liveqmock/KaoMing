package com.dne.sie.maintenance.form;

import org.apache.struts.action.ActionForm;


/**
 * ϵͳ�����ֵ��form
 * @version 1.1.5.6
 */
public class TsSystemCodeForm extends ActionForm {


    // Fields    
     /*
      * ϵͳ����ID
      */
     private Long systemId;
     /*
      * ��������
      */
     private String systemType;
     /*
      * ��������˵��
      */
     private String systemTypeDesc;
     /*
      * ϵͳ����
      */
     private String systemCode;
     /*
      * ������������
      */
     private String systemName;
     /*
      * ����Ӣ������
      */
     private String systemDesc;




   
    // Property accessors

	/**
	 * @return Long ϵͳ����ID
	 */
    public Long getSystemId() {
        return this.systemId;
    }

	/**
	 * @param systemId Long ϵͳ����ID
	 */
    public void setSystemId(Long systemId) {
        this.systemId = systemId;
    }

	/**
	 * @return String ��������
	 */
    public String getSystemType() {
        return this.systemType;
    }

	/**
	 * @param systemType String ��������
	 */
    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }

	/**
	 * @return String ��������˵��
	 */
    public String getSystemTypeDesc() {
        return this.systemTypeDesc;
    }

	/**
	 * @param systemTypeDesc String ��������˵��
	 */
    public void setSystemTypeDesc(String systemTypeDesc) {
        this.systemTypeDesc = systemTypeDesc;
    }

	/**
	 * @return String ϵͳ����
	 */
    public String getSystemCode() {
        return this.systemCode;
    }

	/**
	 * @param systemCode String ϵͳ����
	 */
    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

	/**
	 * @return String ������������
	 */
    public String getSystemName() {
        return this.systemName;
    }

	/**
	 * @param systemName String ������������
	 */
    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

	/**
	 * @return String ����Ӣ������
	 */
    public String getSystemDesc() {
        return this.systemDesc;
    }

	/**
	 * @param systemDesc String ����Ӣ������
	 */
    public void setSystemDesc(String systemDesc) {
        this.systemDesc = systemDesc;
    }



}