package com.dne.sie.stock.queryBean;

import java.util.*;

import com.dne.sie.util.query.*;

import org.hibernate.Hibernate;
import org.apache.struts.action.ActionForm;
import com.dne.sie.stock.form.StockInfoForm;

/**
 * ø‚¥ÊQuery¥¶¿Ì¿‡
 * @author xt
 * @version 1.1.5.6
 * @see StockInfoQuery.java <br>
 */
public class StockInfoQuery extends QueryBean{

	public StockInfoQuery(ActionForm form){
		super(form);
	}

	public StockInfoQuery(ActionForm form, String start, String end) {
		super(form, start, end);
	}
    
   

	private String where = null;
	
    private ArrayList queryCondition(StockInfoForm form){
		ArrayList paramList = new ArrayList();
		where = " where 1=1 ";
		if(form!=null){

			if (form.getStockId() != null) {
				where = where + " and pa.stockId = :stockId";
				QueryParameter param = new QueryParameter();
				param.setName("stockId");
				param.setValue(form.getStockId());
				param.setHbType(Hibernate.LONG);
				paramList.add(param);
			}
			if (form.getSkuCode() != null && !form.getSkuCode().equals("")) {
				where = where + " and pa.skuCode = :skuCode";
				QueryParameter param = new QueryParameter();
				param.setName("skuCode");
				param.setValue(form.getSkuCode());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			
			if (form.getShortCode() != null && !form.getShortCode().equals("")) {
				where = where + " and pa.shortCode like :shortCode";
				QueryParameter param = new QueryParameter();
				param.setName("shortCode");
				param.setValue(form.getShortCode());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			if (form.getStandard() != null && !form.getStandard().equals("")) {
				where = where + " and pa.standard like :standard";
				QueryParameter param = new QueryParameter();
				param.setName("standard");
				param.setValue(form.getStandard());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			if (form.getStuffNo() != null && !form.getStuffNo().equals("")) {
				where = where + " and pa.stuffNo like :stuffNo";
				QueryParameter param = new QueryParameter();
				param.setName("stuffNo");
				param.setValue(form.getStuffNo());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			
			
			if (form.getSkuUnit() != null && !form.getSkuUnit().equals("")) {
				where = where + " and pa.skuUnit = :skuUnit";
				QueryParameter param = new QueryParameter();
				param.setName("skuUnit");
				param.setValue(form.getSkuUnit());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			

			if (form.getSkuType() != null && !form.getSkuType().equals("")) {
				where = where + " and pa.skuType = :skuType";
				QueryParameter param = new QueryParameter();
				param.setName("skuType");
				param.setValue(form.getSkuType());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}

			if (form.getStockStatus() != null && !form.getStockStatus().equals("")) {
				where = where + " and pa.stockStatus = :stockStatus";
				QueryParameter param = new QueryParameter();
				param.setName("stockStatus");
				param.setValue(form.getStockStatus());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}

			if ("-1".equals(form.getStrSkuNum()) ) {
				where += " and pa.skuNum > 0";
			}

			
			
		}
		return paramList;
    }
    
   

	/**
	 *
	 * @todo Implement this ces.architect.util.QueryBean method
	 * @param actionform ActionForm
	 * @return AdvQueryString
	 */
	protected AdvQueryString generateCountQuery(ActionForm aform) {
		StockInfoForm form = (StockInfoForm)aform;
		AdvQueryString countQuery = new AdvQueryString();
		
		String queryString = "select count(pa) from StockInfoForm as pa ";
		
		ArrayList paramList = this.queryCondition(form);
		
		queryString += where;

		countQuery.setQueryString(queryString);
		countQuery.setParameters(paramList);

		return countQuery;
	}

	/**
	 *
	 * @todo Implement this ces.architect.util.QueryBean method
	 * @param actionform ActionForm
	 * @return AdvQueryString
	 */
	protected AdvQueryString generateListQuery(ActionForm aform) {
		StockInfoForm form = (StockInfoForm)aform;
		AdvQueryString listQuery = new AdvQueryString();
		
		String queryString = "from StockInfoForm as pa ";
		
		ArrayList paramList = this.queryCondition(form);
		
		queryString += where + " order by pa.stockId ";

		listQuery.setQueryString(queryString);
		listQuery.setParameters(paramList);

		return listQuery;

	}
}
