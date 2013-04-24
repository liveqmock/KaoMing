package com.dne.sie.support.queryBean;

import java.util.*;

import com.dne.sie.common.tools.Operate;
import com.dne.sie.support.form.BugInfoForm;
import com.dne.sie.util.query.*;

import org.hibernate.Hibernate;
import org.apache.struts.action.ActionForm;

/**
 * 客户Query处理类
 * @author xt
 * @version 1.1.5.6
 * @see BugInfoQuery.java <br>
 */
public class BugInfoQuery extends QueryBean{

	public BugInfoQuery(ActionForm form){
		super(form);
	}

	public BugInfoQuery(ActionForm form, String start, String end) {
		super(form, start, end);
	}
    
   

	private String where = null;
	private ArrayList queryCondition(BugInfoForm form){
		ArrayList paramList = new ArrayList();
		where = " where 1=1 ";
		if(form!=null){
			if (form.getSubject() != null && !form.getSubject().equals("")) {
				where = where + " and pa.subject like :subject";
				QueryParameter param = new QueryParameter();
				param.setName("subject");
				param.setValue(form.getSubject());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			if (form.getDescription() != null && !form.getDescription().equals("")) {
				where = where + " and pa.description like :description";
				QueryParameter param = new QueryParameter();
				param.setName("description");
				param.setValue(form.getDescription());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			if (form.getType() != null && !form.getType().equals("")) {
				where = where + " and pa.type like :type";
				QueryParameter param = new QueryParameter();
				param.setName("type");
				param.setValue(form.getType());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			if (form.getStatus()!= null && !form.getStatus().equals("")) {
				if(form.getStatus().equals("init")){
					where = where + " and pa.status in ('A','B','E')";
				}else{
					where = where + " and pa.status like :status";
					QueryParameter param = new QueryParameter();
					param.setName("status");
					param.setValue(form.getStatus());
					param.setHbType(Hibernate.STRING);
					paramList.add(param);
				}
			}
			if (form.getCreateUserName() != null && !form.getCreateUserName().equals("")) {
				where = where + " and pa.createUserName like :createUserName";
				QueryParameter param = new QueryParameter();
				param.setName("createUserName");
				param.setValue(form.getCreateUserName());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			
			if (form.getCreateMonth()!= null && !form.getCreateMonth().equals("")) {
				where = where + " and pa.createDate >= :createDate1 and pa.createDate <= :createDate2";
				QueryParameter param = new QueryParameter();
				param.setName("createDate1");
				param.setValue(Operate.getFirstDayOfMonth(form.getCreateMonth()));
				param.setHbType(Hibernate.DATE);
				paramList.add(param);

				QueryParameter param2 = new QueryParameter();
				param2.setName("createDate2");
				param2.setValue(Operate.getLastDayOfMonth(form.getCreateMonth()));
				param2.setHbType(Hibernate.DATE);
				paramList.add(param2);		
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
		BugInfoForm form = (BugInfoForm)aform;
		AdvQueryString countQuery = new AdvQueryString();
		
		String queryString = "select count(pa) from BugInfoForm as pa ";
		
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
		BugInfoForm form = (BugInfoForm)aform;
		AdvQueryString listQuery = new AdvQueryString();
		
		String queryString = "select pa from BugInfoForm as pa ";
		
		ArrayList paramList = this.queryCondition(form);
		
		queryString = queryString + where +" order by pa.id desc ";
		listQuery.setQueryString(queryString);
		listQuery.setParameters(paramList);

		return listQuery;

	}
}
