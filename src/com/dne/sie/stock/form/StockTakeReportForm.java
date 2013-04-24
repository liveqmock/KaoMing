package com.dne.sie.stock.form;

import org.apache.struts.action.ActionForm;
import java.util.Date;


/**
 * 库存盘点报表Form处理类
 * @author xt
 * @version 1.1.5.6
 * @see StockTakeReportForm.java <br>
 */
public class StockTakeReportForm extends ActionForm {
	
		private Long takeReportId;
		private Long stockTakeReportId ;
		private Long stockTakeId        ;
		private String skuCode             ;
		private String binCode             ;
		private Integer diffNum             ;
		private String diffType             ;
		private Double  diffPrice             ;
		private Integer lendNum             ;
		private String stuffNo;
		private String partMajorCat;
		private String partMinorCat;

		private Long createBy;
		private Date createDate;
		private Long updateBy;
		private Date updateDate;
		
		private	String currentPage="1";
		private	String txtNumPerPage="10";
		private	String txtGoto="1";
	
		public String getCurrentPage() {
			return this.currentPage;
		}
		
		public void setCurrentPage(String currentPage) {
			this.currentPage = currentPage;
		}
	
		public String getTxtNumPerPage() {
			return this.txtNumPerPage;
		}
		
		public void setTxtNumPerPage(String txtNumPerPage) {
			this.txtNumPerPage = txtNumPerPage;
		}
	
		public String getTxtGoto() {
			return this.txtGoto;
		}
		
		public void setTxtGoto(String txtGoto) {
			this.txtGoto = txtGoto;
		}
	
		public String getFromPage(){
			int cp=Integer.parseInt(getCurrentPage().equals("")?getTxtGoto():getCurrentPage());
			int tnp=Integer.parseInt(getTxtNumPerPage());
			int intFrom=(cp-1)*tnp+1;
		
			return intFrom+"";		
		}
	
		public String getToPage(){
			int cp=Integer.parseInt(getCurrentPage().equals("")?getTxtGoto():getCurrentPage());
			int tnp=Integer.parseInt(getTxtNumPerPage());
			int intTo=cp*tnp;
			return intTo+"";		
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
		public Integer getDiffNum() {
			return diffNum;
		}

		/**
		 * @return
		 */
		public Double getDiffPrice() {
			return diffPrice;
		}



		/**
		 * @return
		 */
		public Integer getLendNum() {
			return lendNum;
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
		public Long getStockTakeId() {
			return stockTakeId;
		}

		/**
		 * @return
		 */
		public Long getStockTakeReportId() {
			return stockTakeReportId;
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
		 * @param integer
		 */
		public void setDiffNum(Integer integer) {
			diffNum = integer;
		}

		/**
		 * @param double1
		 */
		public void setDiffPrice(Double double1) {
			diffPrice = double1;
		}



		/**
		 * @param integer
		 */
		public void setLendNum(Integer integer) {
			lendNum = integer;
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
		public void setStockTakeId(Long long1) {
			stockTakeId = long1;
		}

		/**
		 * @param long1
		 */
		public void setStockTakeReportId(Long long1) {
			stockTakeReportId = long1;
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
		public Long getTakeReportId() {
			return takeReportId;
		}

		/**
		 * @param long1
		 */
		public void setTakeReportId(Long long1) {
			takeReportId = long1;
		}

		/**
		 * @return
		 */
		public String getDiffType() {
			return diffType;
		}

		/**
		 * @param string
		 */
		public void setDiffType(String string) {
			diffType = string;
		}

		/**
		 * @return
		 */
		public String getPartMajorCat() {
			return partMajorCat;
		}

		/**
		 * @return
		 */
		public String getPartMinorCat() {
			return partMinorCat;
		}

		/**
		 * @param string
		 */
		public void setPartMajorCat(String string) {
			partMajorCat = string;
		}

		/**
		 * @param string
		 */
		public void setPartMinorCat(String string) {
			partMinorCat = string;
		}

		public String getStuffNo() {
			return stuffNo;
		}

		public void setStuffNo(String stuffNo) {
			this.stuffNo = stuffNo;
		}

}
