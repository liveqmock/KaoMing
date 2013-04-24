package com.dne.sie.stock.queryBean;

import java.util.ArrayList;

import com.dne.sie.util.query.*;

import org.hibernate.Hibernate;
import org.apache.struts.action.ActionForm;
import com.dne.sie.stock.form.StockTakeDetailForm;
import com.dne.sie.common.tools.Operate;

/**
 * 库存盘点明细Query处理类
 * @author xt
 * @version 1.1.5.6
 * @see StockTakeDetailQuery.java <br>
 */
public class StockTakeDetailQuery extends QueryBean{

	public StockTakeDetailQuery(ActionForm form){
		super(form);
	}

	public StockTakeDetailQuery(ActionForm form, String start, String end) {
		super(form, start, end);
	}
    
   

	private String where = " pa.delFlag=0 ";
	
	private ArrayList queryCondition(StockTakeDetailForm form){
		ArrayList paramList = new ArrayList();
		where = " where pa.delFlag=0 ";
		if(form!=null){
		
			if (form.getStockTakeId() != null && form.getStockTakeId().intValue()!=0) {
				where = where + " and pa.stockTakeId = :stockTakeId";
				QueryParameter param = new QueryParameter();
				param.setName("stockTakeId");
				param.setValue(form.getStockTakeId());
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
		StockTakeDetailForm form = (StockTakeDetailForm)aform;
		AdvQueryString countQuery = new AdvQueryString();
		
		String queryString = "select count(pa) from StockTakeDetailForm as pa ";
		
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
		StockTakeDetailForm form = (StockTakeDetailForm)aform;
		AdvQueryString listQuery = new AdvQueryString();
		
		String queryString = "from StockTakeDetailForm as pa ";
		
		ArrayList paramList = this.queryCondition(form);
		
		queryString = queryString + where + " order by pa.stockTakeDetailId";

		listQuery.setQueryString(queryString);
		listQuery.setParameters(paramList);

		return listQuery;

	}
}
