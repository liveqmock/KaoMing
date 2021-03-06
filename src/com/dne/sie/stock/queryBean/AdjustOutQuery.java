package com.dne.sie.stock.queryBean;

import java.util.*;

import com.dne.sie.util.query.*;

import org.hibernate.Hibernate;
import org.apache.struts.action.ActionForm;
import com.dne.sie.stock.form.StockInfoForm;
import com.dne.sie.common.tools.Operate;

/**
 * 库调出库Query处理类
 * @author xt
 * @version 1.1.5.6
 * @see AdjustOutQuery.java <br>
 */
public class AdjustOutQuery extends QueryBean{

	public AdjustOutQuery(ActionForm form){
		super(form);
	}

	public AdjustOutQuery(ActionForm form, String start, String end) {
		super(form, start, end);
	}
    
	private String whereIn = null;
	
	private ArrayList queryCondition(StockInfoForm form){
		ArrayList paramList = new ArrayList();
		whereIn = " where si.stockStatus='A' and si.skuNum>0 ";
		
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
				whereIn +=  " and si.skuNum = "+form.getStrSkuNum();
			}

			if (form.getStrPerCost() != null && !form.getStrPerCost().equals("")) {
				whereIn +=  " and si.perCost = :strPerCost";
				QueryParameter param = new QueryParameter();
				param.setName("strPerCost");
				param.setValue(form.getStrPerCost());
				param.setHbType(Hibernate.DOUBLE);
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
			
			
			if (form.getBinCode1() != null && !form.getBinCode1().isEmpty()) {
				whereIn += " and si.binCode >= :binCode1";
				QueryParameter param = new QueryParameter();
				param.setName("binCode1");
				param.setValue(form.getBinCode1());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			if (form.getBinCode2() != null && !form.getBinCode2().isEmpty()) {
				whereIn += " and si.binCode <= :binCode2";
				QueryParameter param = new QueryParameter();
				param.setName("binCode2");
				param.setValue(form.getBinCode2());
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

		String queryString = "select count(*) from StockInfoForm as si "+whereIn;
			
		countQuery.setQueryString(queryString);
		countQuery.setParameters(paramList);

		return countQuery;
	}


	protected AdvQueryString generateListQuery(ActionForm aform) {
		StockInfoForm form = (StockInfoForm)aform;
		AdvQueryString listQuery = new AdvQueryString();
		
		ArrayList paramList = this.queryCondition(form);
		String queryString = "from StockInfoForm as si  "+whereIn+" order by si.stuffNo,si.stockId";
			
		listQuery.setQueryString(queryString);
		listQuery.setParameters(paramList);

		return listQuery;

	}
}