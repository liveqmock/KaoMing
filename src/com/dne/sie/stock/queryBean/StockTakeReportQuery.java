package com.dne.sie.stock.queryBean;

import java.util.ArrayList;

import com.dne.sie.util.query.*;

import org.hibernate.Hibernate;
import org.apache.struts.action.ActionForm;
import com.dne.sie.stock.form.StockTakeForm;
import com.dne.sie.stock.form.StockTakeReportForm;
import com.dne.sie.common.tools.Operate;

/**
 * 库存盘点报表Query处理类
 * @author xt
 * @version 1.1.5.6
 * @see StockTakeReportQuery.java <br>
 */
public class StockTakeReportQuery extends QueryBean{

	public StockTakeReportQuery(ActionForm form){
		super(form);
	}

	public StockTakeReportQuery(ActionForm form, String start, String end) {
		super(form, start, end);
	}
    
   

	private String where = "";
	
	private ArrayList queryCondition(StockTakeReportForm form){
		ArrayList paramList = new ArrayList();
		where = " where 1=1 ";
		if(form!=null){
			
			if (form.getStockTakeId() != null && !form.getStockTakeId().toString().equals("0")) {
				where = where + " and pa.stockTakeId = :stockTakeId";
				QueryParameter param = new QueryParameter();
				param.setName("stockTakeId");
				param.setValue(form.getStockTakeId());
				param.setHbType(Hibernate.LONG);
				paramList.add(param);
			}
			
			
			if (form.getBinCode() != null && !form.getBinCode().equals("")) {
				where = where + " and pa.binCode = :binCode";
				QueryParameter param = new QueryParameter();
				param.setName("binCode");
				param.setValue(form.getBinCode());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			
			if (form.getSkuCode() != null && !form.getSkuCode().toString().equals("0")) {
				where = where + " and pa.skuCode = :skuCode";
				QueryParameter param = new QueryParameter();
				param.setName("skuCode");
				param.setValue(form.getSkuCode());
				param.setHbType(Hibernate.LONG);
				paramList.add(param);
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
		StockTakeReportForm form = (StockTakeReportForm)aform;
		AdvQueryString countQuery = new AdvQueryString();
		
		String queryString = "select count(pa) from StockTakeReportForm as pa ";
		
		ArrayList paramList = this.queryCondition(form);
		
		queryString = queryString + where;

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
		StockTakeReportForm form = (StockTakeReportForm)aform;
		AdvQueryString listQuery = new AdvQueryString();
		
		String queryString = "from StockTakeReportForm as pa ";
		
		ArrayList paramList = this.queryCondition(form);
		
		queryString = queryString + where;

		listQuery.setQueryString(queryString);
		listQuery.setParameters(paramList);

		return listQuery;

	}
}
