package com.dne.sie.reception.queryBean;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;
import org.hibernate.Hibernate;

import com.dne.sie.reception.form.SaleDetailForm;
import com.dne.sie.util.query.AdvQueryString;
import com.dne.sie.util.query.QueryBean;
import com.dne.sie.util.query.QueryParameter;

/**
 * 维修零件Query处理类
 * @author xt
 * @version 1.1.5.6
 * @see SaleDetailQuery.java <br>
 */
public class SaleDetailQuery extends QueryBean{

	public SaleDetailQuery(ActionForm form){
		super(form);
	}

	public SaleDetailQuery(ActionForm form, String start, String end) {
		super(form, start, end);
	}
    
   

	private String whereIn = null;
	
    private ArrayList queryCondition(SaleDetailForm form){
    	 whereIn = " where 1=1 "; //delFlag 可以是1
		ArrayList paramList = new ArrayList();
		if(form!=null){

			if (form.getSaleNo() != null && !"".equals(form.getSaleNo())) {
				whereIn +=  " and rsf.saleNo like :saleNo";
				QueryParameter param = new QueryParameter();
				param.setName("saleNo");
				param.setValue(form.getSaleNo());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			


			if (form.getSkuCode() != null && !form.getSkuCode().equals("")) {
				whereIn +=  " and rsf.skuCode like :skuCode";
				QueryParameter param = new QueryParameter();
				param.setName("skuCode");
				param.setValue(form.getSkuCode());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			
			if (form.getStandard() != null && !form.getStandard().equals("")) {
				whereIn +=  " and rsf.standard like :standard";
				QueryParameter param = new QueryParameter();
				param.setName("standard");
				param.setValue(form.getStandard());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			if (form.getStuffNo() != null && !form.getStuffNo().equals("")) {
				whereIn +=  " and rsf.stuffNo like :stuffNo";
				QueryParameter param = new QueryParameter();
				param.setName("stuffNo");
				param.setValue(form.getStuffNo());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			
			
			if (form.getSkuUnit() != null && !form.getSkuUnit().equals("")) {
				whereIn +=  " and rsf.skuUnit = :skuUnit";
				QueryParameter param = new QueryParameter();
				param.setName("skuUnit");
				param.setValue(form.getSkuUnit());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			
			if (form.getOrderType() != null && !form.getOrderType().equals("")) {
				whereIn +=  " and rsf.orderType = :orderType";
				QueryParameter param = new QueryParameter();
				param.setName("orderType");
				param.setValue(form.getOrderType());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}

			if (form.getPartStatus() != null && !form.getPartStatus().equals("")) {
				whereIn +=  " and rsf.partStatus = :partStatus";
				QueryParameter param = new QueryParameter();
				param.setName("partStatus");
				param.setValue(form.getPartStatus());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			
			if (form.getCreateBy() != null && form.getCreateBy().longValue()!=0) {
				whereIn +=  " and rsf.createBy = :createBy";
				QueryParameter param = new QueryParameter();
				param.setName("createBy");
				param.setValue(form.getCreateBy());
				param.setHbType(Hibernate.LONG);
				paramList.add(param);
			}
			

			if (form.getUpdateBy() != null && form.getUpdateBy().longValue()!=0) {
				whereIn +=  " and rsf.updateBy = :updateBy";
				QueryParameter param = new QueryParameter();
				param.setName("updateBy");
				param.setValue(form.getUpdateBy());
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
		SaleDetailForm form = (SaleDetailForm)aform;
		AdvQueryString countQuery = new AdvQueryString();
		
		ArrayList paramList = this.queryCondition(form);
		String queryString = "select count(rsf) from SaleDetailForm as rsf "+whereIn;
		
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
		SaleDetailForm form = (SaleDetailForm)aform;
		AdvQueryString listQuery = new AdvQueryString();
		
		ArrayList paramList = this.queryCondition(form);
		String queryString = "from SaleDetailForm as rsf "+ whereIn + " order by rsf.saleDetailId desc ";
		
		listQuery.setQueryString(queryString);
		listQuery.setParameters(paramList);

		return listQuery;

	}
}
