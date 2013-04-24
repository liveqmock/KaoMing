package com.dne.sie.support.queryBean;

import java.util.*;

import com.dne.sie.util.query.*;

import org.hibernate.Hibernate;
import org.apache.struts.action.ActionForm;

import com.dne.sie.support.form.AccountStatisticsForm;

/**
 * 费用Query处理类
 * @author xt
 * @version 1.1.5.6
 * @see AccountStatisticsQuery.java <br>
 */
public class AccountStatisticsQuery extends QueryBean{

	public AccountStatisticsQuery(ActionForm form){
		super(form);
	}

	public AccountStatisticsQuery(ActionForm form, String start, String end) {
		super(form, start, end);
	}
    
   

	private String where = null;
	@SuppressWarnings("unchecked")
	private ArrayList queryCondition(AccountStatisticsForm form){
		ArrayList paramList = new ArrayList();
		where = " where 1=1 ";
		if(form!=null){
			

			if (form.getAccountMonth() != null && form.getAccountMonth()!=0) {
				where += " and pa.accountMonth = :accountMonth";
				QueryParameter param = new QueryParameter();
				param.setName("accountMonth");
				param.setValue(form.getAccountMonth());
				param.setHbType(Hibernate.INTEGER);
				paramList.add(param);
			}

			if (form.getAccountMonth1() != null && !form.getAccountMonth1().equals("")) {
				where += " and pa.accountMonth >= :accountMonth1";
				QueryParameter param = new QueryParameter();
				param.setName("accountMonth1");
				param.setValue(new Integer(form.getAccountMonth1()));
				param.setHbType(Hibernate.INTEGER);
				paramList.add(param);
			}
			if (form.getAccountMonth2() != null && !form.getAccountMonth2().equals("")) {
				where += " and pa.accountMonth <= :accountMonth2";
				QueryParameter param = new QueryParameter();
				param.setName("accountMonth2");
				param.setValue(new Integer(form.getAccountMonth2()));
				param.setHbType(Hibernate.INTEGER);
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
		AccountStatisticsForm form = (AccountStatisticsForm)aform;
		AdvQueryString countQuery = new AdvQueryString();
		
		String queryString = "select count(pa) from AccountStatisticsForm as pa ";
		
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
		AccountStatisticsForm form = (AccountStatisticsForm)aform;
		AdvQueryString listQuery = new AdvQueryString();
		
		String queryString = "select pa from AccountStatisticsForm as pa ";
		
		ArrayList paramList = this.queryCondition(form);
		
		queryString = queryString + where +" order by pa.accountMonth desc ";
		listQuery.setQueryString(queryString);
		listQuery.setParameters(paramList);

		return listQuery;

	}
}
