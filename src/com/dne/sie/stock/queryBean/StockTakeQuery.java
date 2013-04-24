package com.dne.sie.stock.queryBean;

import java.util.ArrayList;

import com.dne.sie.util.query.*;

import org.hibernate.Hibernate;
import org.apache.struts.action.ActionForm;
import com.dne.sie.stock.form.StockTakeForm;
import com.dne.sie.common.tools.Operate;

/**
 * 库存盘点Query处理类
 * @author xt
 * @version 1.1.5.6
 * @see StockTakeQuery.java <br>
 */
public class StockTakeQuery extends QueryBean{

	public StockTakeQuery(ActionForm form){
		super(form);
	}

	public StockTakeQuery(ActionForm form, String start, String end) {
		super(form, start, end);
	}
    
   

	private String where = "";
	private ArrayList queryCondition(StockTakeForm form){
		ArrayList paramList = new ArrayList();
		where = " where pa.takeStatus!='C' ";
		if(form!=null){
			
			
			if (form.getStockTakeId() != null && !form.getStockTakeId().toString().equals("0")) {
				where = where + " and pa.stockTakeId = :stockTakeId";
				QueryParameter param = new QueryParameter();
				param.setName("stockTakeId");
				param.setValue(form.getStockTakeId());
				param.setHbType(Hibernate.LONG);
				paramList.add(param);
			}
			
			
			if (form.getOperater() != null && !form.getOperater().toString().equals("0")) {
				where = where + " and pa.operater = :operater";
				QueryParameter param = new QueryParameter();
				param.setName("operater");
				param.setValue(form.getOperater());
				param.setHbType(Hibernate.LONG);
				paramList.add(param);
			}
			
	
			if (form.getStockTakeResult() != null && !form.getStockTakeResult().equals("")) {
				where = where + " and pa.stockTakeResult = :stockTakeResult";
				QueryParameter param = new QueryParameter();
				param.setName("stockTakeResult");
				param.setValue(form.getStockTakeResult());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			
					
			
			if (form.getTempDate1() != null && !form.getTempDate1().equals("")) {
				where = where + " and pa.beginDate >= :tempDate1";
				QueryParameter param = new QueryParameter();
				param.setName("tempDate1");
				param.setValue(Operate.toDate(form.getTempDate1()));
				param.setHbType(Hibernate.DATE);
				paramList.add(param);
			}
			
			if (form.getTempDate2() != null && !form.getTempDate2().equals("")) {
				where = where + " and pa.beginDate < :tempDate2";
				QueryParameter param = new QueryParameter();
				param.setName("tempDate2");
				param.setValue(Operate.getNextDate(form.getTempDate2()));
				param.setHbType(Hibernate.DATE);
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
		StockTakeForm form = (StockTakeForm)aform;
		AdvQueryString countQuery = new AdvQueryString();
		
		String queryString = "select count(pa) from StockTakeForm as pa ";
		
		ArrayList paramList = this.queryCondition(form);
		
		queryString = queryString  + where;

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
		StockTakeForm form = (StockTakeForm)aform;
		AdvQueryString listQuery = new AdvQueryString();
		
		String queryString = "select pa from StockTakeForm as pa ";
		
		ArrayList paramList = this.queryCondition(form);
		
		queryString = queryString + where +" order by pa.stockTakeId desc";
		listQuery.setQueryString(queryString);
		listQuery.setParameters(paramList);

		return listQuery;

	}
}
