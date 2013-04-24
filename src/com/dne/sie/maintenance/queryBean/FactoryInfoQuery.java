package com.dne.sie.maintenance.queryBean;

import java.util.*;

import com.dne.sie.util.query.*;
import org.hibernate.Hibernate;
import org.apache.struts.action.ActionForm;
import com.dne.sie.maintenance.form.FactoryInfoForm;

/**
 * 厂商Query处理类
 * @author xt
 * @version 1.1.5.6
 * @see FactoryInfoQuery.java <br>
 */
public class FactoryInfoQuery extends QueryBean{

	public FactoryInfoQuery(ActionForm form){
		super(form);
	}

	public FactoryInfoQuery(ActionForm form, String start, String end) {
		super(form, start, end);
	}
    
   

	private String where = null;
	private ArrayList queryCondition(FactoryInfoForm form){
		ArrayList paramList = new ArrayList();
		where = " where pa.delFlag=0 ";
		if(form!=null){
			if (form.getFactoryId() != null && !form.getFactoryId().equals("")) {
				where = where + " and pa.factoryId like :factoryId";
				QueryParameter param = new QueryParameter();
				param.setName("factoryId");
				param.setValue(form.getFactoryId());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			if (form.getFactoryName() != null && !form.getFactoryName().equals("")) {
				where = where + " and pa.factoryName like :factoryName";
				QueryParameter param = new QueryParameter();
				param.setName("factoryName");
				param.setValue(form.getFactoryName());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			if (form.getCityName() != null && !form.getCityName().equals("")) {
				where = where + " and pa.cityName like :cityName";
				QueryParameter param = new QueryParameter();
				param.setName("cityName");
				param.setValue(form.getCityName());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			if (form.getLinkman()!= null && !form.getLinkman().equals("")) {
				where = where + " and pa.linkman like :linkman";
				QueryParameter param = new QueryParameter();
				param.setName("linkman");
				param.setValue(form.getLinkman());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			if (form.getPhone() != null && !form.getPhone().equals("")) {
				where = where + " and pa.phone like :phone";
				QueryParameter param = new QueryParameter();
				param.setName("phone");
				param.setValue(form.getPhone());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			if (form.getAddress() != null && !form.getAddress().equals("")) {
				where = where + " and pa.address like :address";
				QueryParameter param = new QueryParameter();
				param.setName("address");
				param.setValue(form.getAddress());
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
		FactoryInfoForm form = (FactoryInfoForm)aform;
		AdvQueryString countQuery = new AdvQueryString();
		
		String queryString = "select count(pa) from FactoryInfoForm as pa ";
		
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
		FactoryInfoForm form = (FactoryInfoForm)aform;
		AdvQueryString listQuery = new AdvQueryString();
		
		String queryString = "select pa from FactoryInfoForm as pa ";
		
		ArrayList paramList = this.queryCondition(form);
		
		queryString = queryString + where +" order by pa.factoryId ";
		listQuery.setQueryString(queryString);
		listQuery.setParameters(paramList);

		return listQuery;

	}
}
