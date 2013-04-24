package com.dne.sie.maintenance.queryBean;

import java.util.*;

import com.dne.sie.util.query.*;
import org.hibernate.Hibernate;
import org.apache.struts.action.ActionForm;
import com.dne.sie.maintenance.form.TsSystemCodeForm;

/**
 * 字典表Query处理类
 * @author xt
 * @version 1.1.5.6
 * @see StockFlowQuery.java <br>
 */
public class SyscodeQuery extends QueryBean{

	public SyscodeQuery(ActionForm form){
		super(form);
	}

	public SyscodeQuery(ActionForm form, String start, String end) {
		super(form, start, end);
	}
    
   

	private String where = null;
	private ArrayList queryCondition(TsSystemCodeForm form){
		ArrayList paramList = new ArrayList();
		where = " where 1=1 ";
		if(form!=null){
			if (form.getSystemType() != null && !form.getSystemType().equals("")) {
				where = where + " and pa.systemType like :systemType";
				QueryParameter param = new QueryParameter();
				param.setName("systemType");
				param.setValue(form.getSystemType());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			if (form.getSystemName() != null && !form.getSystemName().equals("")) {
				where = where + " and pa.systemName like :systemName";
				QueryParameter param = new QueryParameter();
				param.setName("systemName");
				param.setValue(form.getSystemName());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			if (form.getSystemDesc() != null && !form.getSystemDesc().equals("")) {
				where = where + " and pa.systemDesc like :systemDesc";
				QueryParameter param = new QueryParameter();
				param.setName("systemDesc");
				param.setValue(form.getSystemDesc());
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
		TsSystemCodeForm form = (TsSystemCodeForm)aform;
		AdvQueryString countQuery = new AdvQueryString();
		
		String queryString = "select count(pa) from TsSystemCodeForm as pa ";
		
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
		TsSystemCodeForm form = (TsSystemCodeForm)aform;
		AdvQueryString listQuery = new AdvQueryString();
		
		String queryString = "select pa from TsSystemCodeForm as pa ";
		
		ArrayList paramList = this.queryCondition(form);
		
		queryString = queryString + where +" order by pa.systemDesc,pa.systemType ";
		listQuery.setQueryString(queryString);
		listQuery.setParameters(paramList);

		return listQuery;

	}
}
