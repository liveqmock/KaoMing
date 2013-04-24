package com.dne.sie.stock.queryBean;

import java.util.*;

import com.dne.sie.util.query.*;

import org.hibernate.Hibernate;
import org.apache.struts.action.ActionForm;
import com.dne.sie.stock.form.StockInfoForm;
import com.dne.sie.common.tools.Operate;

/**
 * 库存信息Query处理类
 * @author xt
 * @version 1.1.5.6
 * @see StockTaxInfoListQuery.java <br>
 */
public class StockTaxInfoListQuery extends QueryBean{

	public StockTaxInfoListQuery(ActionForm form){
		super(form);
	}

	public StockTaxInfoListQuery(ActionForm form, String start, String end) {
		super(form, start, end);
	}
    
	private String whereIn = null,strHaving=null;
	
	private ArrayList queryCondition(StockInfoForm form){
		ArrayList paramList = new ArrayList();
		whereIn = " where 1=1 ";
		strHaving=" having 1=1 ";
		if(form!=null){
		

			if (form.getSkuCode() != null && !form.getSkuCode().equals("")) {
				whereIn +=  " and si.skuCode like :skuCode";
				QueryParameter param = new QueryParameter();
				param.setName("skuCode");
				param.setValue(form.getSkuCode());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			
			if (form.getShortCode() != null && !form.getShortCode().equals("")) {
				whereIn +=  " and si.shortCode like :shortCode";
				QueryParameter param = new QueryParameter();
				param.setName("shortCode");
				param.setValue(form.getShortCode());
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
			if (form.getStrSkuNum() != null && !form.getStrSkuNum().equals("")) {
				strHaving +=  " and sum(si.skuNumTax) = "+form.getStrSkuNum();
				
			}

			if (form.getStrPerCost() != null && !form.getStrPerCost().equals("")) {
				strHaving +=  " and avg(si.perCostTax) = :strPerCost";
				QueryParameter param = new QueryParameter();
				param.setName("strPerCost");
				param.setValue(form.getStrPerCost());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			

			if (form.getSkuType() != null && !form.getSkuType().equals("")) {
				whereIn +=  " and si.skuType = :skuType";
				QueryParameter param = new QueryParameter();
				param.setName("skuType");
				param.setValue(form.getSkuType());
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
			
			if (form.getInDate1() != null && !form.getInDate1().equals("")) {
				whereIn += " and si.createDate >= :inDate1";
				QueryParameter param = new QueryParameter();
				param.setName("inDate1");
				param.setValue(Operate.toDate(form.getInDate1()));
				param.setHbType(Hibernate.DATE);
				paramList.add(param);
			}
			if (form.getInDate2() != null && !form.getInDate2().equals("")) {
				whereIn += " and si.createDate < :inDate2";
				QueryParameter param = new QueryParameter();
				param.setName("inDate2");
				param.setValue(Operate.getNextDate(form.getInDate2()));
				param.setHbType(Hibernate.DATE);
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
		StockInfoForm form = (StockInfoForm)aform;
		AdvQueryString countQuery = new AdvQueryString();
		ArrayList paramList = this.queryCondition(form);

		String queryString = "select count(distinct si.skuCode) " +
			"from StockInfoForm as si "+whereIn;
			
		countQuery.setQueryString(queryString);
		countQuery.setParameters(paramList);

		return countQuery;
	}


	protected AdvQueryString generateListQuery(ActionForm aform) {
		StockInfoForm form = (StockInfoForm)aform;
		AdvQueryString listQuery = new AdvQueryString();
		
		ArrayList paramList = this.queryCondition(form);
		String queryString = "select si.stuffNo,si.skuCode," +
			" sum(si.skuNumTax),sum(si.perCostTax * si.skuNumTax) " +
			" from StockInfoForm as si  "+whereIn+
			" group by si.stuffNo " +strHaving+
			" order by si.stockId";
			
		listQuery.setQueryString(queryString);
		listQuery.setParameters(paramList);

		return listQuery;

	}
}