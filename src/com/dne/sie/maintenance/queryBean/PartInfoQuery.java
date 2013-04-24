package com.dne.sie.maintenance.queryBean;

import java.util.*;

import com.dne.sie.util.query.*;
import org.hibernate.Hibernate;
import org.apache.struts.action.ActionForm;
import com.dne.sie.maintenance.form.PartInfoForm;
import com.dne.sie.common.tools.Operate;

/**
 * 出入库流水Query处理类
 * @author xt
 * @version 1.1.5.6
 * @see StockFlowQuery.java <br>
 */
public class PartInfoQuery extends QueryBean{

	public PartInfoQuery(ActionForm form){
		super(form);
	}

	public PartInfoQuery(ActionForm form, String start, String end) {
		super(form, start, end);
	}
    
   

	private String where = null;
	private ArrayList queryCondition(PartInfoForm form){
		ArrayList paramList = new ArrayList();
		where = " where 1=1 ";
		if(form!=null){
			if (form.getStuffNo() != null && !form.getStuffNo().equals("")) {
				where = where + " and pa.stuffNo like :stuffNo";
				QueryParameter param = new QueryParameter();
				param.setName("stuffNo");
				param.setValue(form.getStuffNo());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			if (form.getSkuCode() != null && !form.getSkuCode().equals("")) {
				where = where + " and pa.skuCode like :skuCode";
				QueryParameter param = new QueryParameter();
				param.setName("skuCode");
				param.setValue(form.getSkuCode());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			if(form.getPartType() == null || form.getPartType().equals("")){
				//default part
				where = where + " and pa.partType ='P' ";
			}else{
				where = where + " and pa.partType = :partType";
				QueryParameter param = new QueryParameter();
				param.setName("partType");
				param.setValue(form.getPartType());
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
			
			if (form.getBuyCost1() != null && !form.getBuyCost1().equals("")) {
				where = where + " and pa.buyCost = :buyCost1";
				QueryParameter param = new QueryParameter();
				param.setName("buyCost1");
				param.setValue(form.getBuyCost1());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			if (form.getSaleCost1() != null && !form.getSaleCost1().equals("")) {
				where = where + " and pa.saleCost = :saleCost1";
				QueryParameter param = new QueryParameter();
				param.setName("saleCost1");
				param.setValue(form.getSaleCost1());
				param.setHbType(Hibernate.STRING);
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
		PartInfoForm form = (PartInfoForm)aform;
		AdvQueryString countQuery = new AdvQueryString();
		
		String queryString = "select count(pa) from PartInfoForm as pa ";
		
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
		PartInfoForm form = (PartInfoForm)aform;
		AdvQueryString listQuery = new AdvQueryString();
		
		String queryString = "select pa from PartInfoForm as pa ";
		
		ArrayList paramList = this.queryCondition(form);
		
		queryString = queryString + where +" order by pa.stuffNo ";
		listQuery.setQueryString(queryString);
		listQuery.setParameters(paramList);

		return listQuery;

	}
}
