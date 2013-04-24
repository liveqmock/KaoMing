package com.dne.sie.stock.form;

import java.util.Date;
import com.dne.sie.util.form.CommForm;


/**
 * 库存盘点Form处理类
 * @author xt
 * @version 1.1.5.6
 * @see StockTakeForm.java <br>
 */
public class StockTakeForm extends CommForm {
	
	private Long stockTakeId     ;
	private Date beginDate        ;
	private Date endDate          ;
	private Long operater          ;
	private Double lowPrice         ;
	private String skuType          ;
	private String stockTakeResult ;
	private String delFlag;
	
	private String reqDateCtrl;
	private String tempDate1 ;
	private String tempDate2 ;
	private String takeStatus;
	
	private Double upPrice;
	private String binCodeBegin;
	private String binCodeEnd;

    private Integer catNum;
    private Integer catRealNum;
	private Integer skuNum;
	private Integer skuRealNum;
	private Double  price;
	private Double  realPrice;
    

	private Long createBy;
	private Date createDate;
	private Long updateBy;
	private Date updateDate;
	
	private String remark;
			   
   
   
	/**
	 * @return
	 */
	public Date getBeginDate() {
		return beginDate;
	}

	/**
	 * @return
	 */
	public Long getCreateBy() {
		return createBy;
	}

	/**
	 * @return
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @return
	 */
	public String getDelFlag() {
		return delFlag;
	}

	/**
	 * @return
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @return
	 */
	public Double getLowPrice() {
		return lowPrice;
	}

	/**
	 * @return
	 */
	public Long getOperater() {
		return operater;
	}

	
	/**
	 * @return
	 */
	public String getSkuType() {
		return skuType;
	}

	

	/**
	 * @return
	 */
	public Long getStockTakeId() {
		return stockTakeId;
	}

	/**
	 * @return
	 */
	public String getStockTakeResult() {
		return stockTakeResult;
	}

	/**
	 * @return
	 */
	public Long getUpdateBy() {
		return updateBy;
	}

	/**
	 * @return
	 */
	public java.util.Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param date
	 */
	public void setBeginDate(Date date) {
		beginDate = date;
	}

	/**
	 * @param long1
	 */
	public void setCreateBy(Long long1) {
		createBy = long1;
	}

	/**
	 * @param date
	 */
	public void setCreateDate(Date date) {
		createDate = date;
	}

	/**
	 * @param string
	 */
	public void setDelFlag(String string) {
		delFlag = string;
	}

	/**
	 * @param date
	 */
	public void setEndDate(Date date) {
		endDate = date;
	}

	/**
	 * @param double1
	 */
	public void setLowPrice(Double double1) {
		lowPrice = double1;
	}

	/**
	 * @param long1
	 */
	public void setOperater(Long long1) {
		operater = long1;
	}

	
	/**
	 * @param string
	 */
	public void setSkuType(String string) {
		skuType = string;
	}

	

	/**
	 * @param string
	 */
	public void setStockTakeId(Long string) {
		stockTakeId = string;
	}

	/**
	 * @param string
	 */
	public void setStockTakeResult(String string) {
		stockTakeResult = string;
	}

	/**
	 * @param long1
	 */
	public void setUpdateBy(Long long1) {
		updateBy = long1;
	}

	/**
	 * @param date
	 */
	public void setUpdateDate(java.util.Date date) {
		updateDate = date;
	}

	/**
	 * @return
	 */
	public String getTempDate1() {
		return tempDate1;
	}

	/**
	 * @return
	 */
	public String getTempDate2() {
		return tempDate2;
	}

	/**
	 * @param string
	 */
	public void setTempDate1(String string) {
		tempDate1 = string;
	}

	/**
	 * @param string
	 */
	public void setTempDate2(String string) {
		tempDate2 = string;
	}

	/**
	 * @return
	 */
	public String getReqDateCtrl() {
		return reqDateCtrl;
	}

	/**
	 * @param string
	 */
	public void setReqDateCtrl(String string) {
		reqDateCtrl = string;
	}

	/**
	 * @return
	 */
	public String getTakeStatus() {
		return takeStatus;
	}

	/**
	 * @param string
	 */
	public void setTakeStatus(String string) {
		takeStatus = string;
	}

	/**
	 * @return
	 */
	public String getBinCodeBegin() {
		return binCodeBegin;
	}

	/**
	 * @return
	 */
	public String getBinCodeEnd() {
		return binCodeEnd;
	}

	/**
	 * @return
	 */
	public Double getUpPrice() {
		return upPrice;
	}

	/**
	 * @param string
	 */
	public void setBinCodeBegin(String string) {
		binCodeBegin = string;
	}

	/**
	 * @param string
	 */
	public void setBinCodeEnd(String string) {
		binCodeEnd = string;
	}

	/**
	 * @param double1
	 */
	public void setUpPrice(Double double1) {
		upPrice = double1;
	}

	/**
	 * @return
	 */
	public Integer getCatNum() {
		return catNum;
	}

	/**
	 * @return
	 */
	public Integer getCatRealNum() {
		return catRealNum;
	}

	/**
	 * @return
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * @return
	 */
	public Double getRealPrice() {
		return realPrice;
	}

	/**
	 * @return
	 */
	public Integer getSkuNum() {
		return skuNum;
	}

	/**
	 * @return
	 */
	public Integer getSkuRealNum() {
		return skuRealNum;
	}

	/**
	 * @param integer
	 */
	public void setCatNum(Integer integer) {
		catNum = integer;
	}

	/**
	 * @param integer
	 */
	public void setCatRealNum(Integer integer) {
		catRealNum = integer;
	}

	/**
	 * @param double1
	 */
	public void setPrice(Double double1) {
		price = double1;
	}

	/**
	 * @param double1
	 */
	public void setRealPrice(Double double1) {
		realPrice = double1;
	}

	/**
	 * @param integer
	 */
	public void setSkuNum(Integer integer) {
		skuNum = integer;
	}

	/**
	 * @param integer
	 */
	public void setSkuRealNum(Integer integer) {
		skuRealNum = integer;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	

}
