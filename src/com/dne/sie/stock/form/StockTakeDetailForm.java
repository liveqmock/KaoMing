package com.dne.sie.stock.form;

import java.util.Date;

import com.dne.sie.util.form.CommForm;


/**
 * 库存盘点明细Form处理类
 * @author xt
 * @version 1.1.5.6
 * @see StockTakeDetailForm.java <br>
 */
public class StockTakeDetailForm extends CommForm {
	
		private Long stockTakeDetailId ;
		private Long stockTakeId        ;
		private String skuCode             ;
		private String stuffNo             ;
		private Integer diffNum             ;
		private String binCode             ;
		private String adjustFlag          ;
		private String delFlag             ;
		private String skuUnit;
		
	private Integer stockNum;
	private Integer takeNum;
	private Long stockId;
		
		private Long createBy;
		private Date createDate;
		private Long updateBy;
		private Date updateDate;
		
   
	
		/**
		 * @return
		 */
		public String getAdjustFlag() {
			return adjustFlag;
		}

		/**
		 * @return
		 */
		public String getBinCode() {
			return binCode;
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
		public Integer getDiffNum() {
			return diffNum;
		}

		/**
		 * @return
		 */
		public String getSkuCode() {
			return skuCode;
		}

		/**
		 * @return
		 */
		public Long getStockTakeDetailId() {
			return stockTakeDetailId;
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
		public Long getUpdateBy() {
			return updateBy;
		}

		/**
		 * @return
		 */
		public Date getUpdateDate() {
			return updateDate;
		}

		/**
		 * @param string
		 */
		public void setAdjustFlag(String string) {
			adjustFlag = string;
		}

		/**
		 * @param string
		 */
		public void setBinCode(String string) {
			binCode = string;
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
		 * @param integer
		 */
		public void setDiffNum(Integer integer) {
			diffNum = integer;
		}

		/**
		 * @param string
		 */
		public void setSkuCode(String string) {
			skuCode = string;
		}


		/**
		 * @param long1
		 */
		public void setStockTakeDetailId(Long long1) {
			stockTakeDetailId = long1;
		}

		/**
		 * @param long1
		 */
		public void setStockTakeId(Long long1) {
			stockTakeId = long1;
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
		public void setUpdateDate(Date date) {
			updateDate = date;
		}

	/**
	 * @return
	 */
	public Integer getStockNum() {
		return stockNum;
	}

	/**
	 * @return
	 */
	public Integer getTakeNum() {
		return takeNum;
	}

	/**
	 * @param integer
	 */
	public void setStockNum(Integer integer) {
		stockNum = integer;
	}

	/**
	 * @param integer
	 */
	public void setTakeNum(Integer integer) {
		takeNum = integer;
	}

	/**
	 * @return
	 */
	public Long getStockId() {
		return stockId;
	}

	/**
	 * @param long1
	 */
	public void setStockId(Long long1) {
		stockId = long1;
	}


	public String getStuffNo() {
		return stuffNo;
	}

	public void setStuffNo(String stuffNo) {
		this.stuffNo = stuffNo;
	}

	public String getSkuUnit() {
		return skuUnit;
	}

	public void setSkuUnit(String skuUnit) {
		this.skuUnit = skuUnit;
	}



}
