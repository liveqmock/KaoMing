package com.dne.sie.stock.queryBean;

import java.util.*;

import com.dne.sie.util.query.*;

import org.hibernate.Hibernate;
import org.apache.struts.action.ActionForm;
import com.dne.sie.stock.form.StockToolsInfoForm;

/**
 * 库存Tools信息Query处理类
 * @author xt
 * @version 1.1.5.6
 * @see StockToolsListQuery.java <br>
 */
public class StockToolsListQuery extends QueryBean{

	public StockToolsListQuery(ActionForm form){
		super(form);
	}

	public StockToolsListQuery(ActionForm form, String start, String end) {
		super(form, start, end);
	}
    
	private String whereIn = null;
	
	private ArrayList queryCondition(StockToolsInfoForm form){
		ArrayList paramList = new ArrayList();
		whereIn = " where si.skuType='T' ";
		
		if(form!=null){
		

			if (form.getSkuCode() != null && !form.getSkuCode().equals("")) {
				whereIn +=  " and si.skuCode like :skuCode";
				QueryParameter param = new QueryParameter();
				param.setName("skuCode");
				param.setValue(form.getSkuCode());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			
			if (form.getStandard() != null && !form.getStandard().equals("")) {
				whereIn +=  " and si.standard like :standard";
				QueryParameter param = new QueryParameter();
				param.setName("standard");
				param.setValue(form.getStandard());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			if (form.getStuffNo() != null && !form.getStuffNo().equals("")) {
				whereIn +=  " and si.stuffNo like :stuffNo";
				QueryParameter param = new QueryParameter();
				param.setName("stuffNo");
				param.setValue(form.getStuffNo());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			
			
			if (form.getSkuUnit() != null && !form.getSkuUnit().equals("")) {
				whereIn +=  " and si.skuUnit = :skuUnit";
				QueryParameter param = new QueryParameter();
				param.setName("skuUnit");
				param.setValue(form.getSkuUnit());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			
			

			if (form.getStockStatus() != null && !form.getStockStatus().equals("")) {
				whereIn +=  " and si.stockStatus = :stockStatus";
				QueryParameter param = new QueryParameter();
				param.setName("stockStatus");
				param.setValue(form.getStockStatus());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			
		

			if (form.getRemark() != null && !form.getRemark().equals("")) {
				whereIn +=  " and si.remark like :remark";
				QueryParameter param = new QueryParameter();
				param.setName("remark");
				param.setValue(form.getRemark());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			
			
		}
		return paramList;
	}
    
   

	protected AdvQueryString generateCountQuery(ActionForm aform) {
		StockToolsInfoForm form = (StockToolsInfoForm)aform;
		AdvQueryString countQuery = new AdvQueryString();
		ArrayList paramList = this.queryCondition(form);

		String queryString = "select count(distinct si.stockId) " +
			"from StockToolsInfoForm as si "+whereIn;
			
		countQuery.setQueryString(queryString);
		countQuery.setParameters(paramList);

		return countQuery;
	}


	protected AdvQueryString generateListQuery(ActionForm aform) {
		StockToolsInfoForm form = (StockToolsInfoForm)aform;
		AdvQueryString listQuery = new AdvQueryString();
		
		ArrayList paramList = this.queryCondition(form);
		String queryString = "from StockToolsInfoForm as si  "+whereIn+
			" order by si.stockId";
			
		listQuery.setQueryString(queryString);
		listQuery.setParameters(paramList);

		return listQuery;

	}
}