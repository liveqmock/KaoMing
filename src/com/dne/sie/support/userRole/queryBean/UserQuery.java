package com.dne.sie.support.userRole.queryBean;

import java.util.*;

import com.dne.sie.util.query.*;

import org.hibernate.Hibernate;
import org.apache.struts.action.ActionForm;
import com.dne.sie.support.userRole.form.UserForm;



/**
 * 用户管理Query类
 * @author xt
 * @version 1.1.5.6
 * @see UserQuery.java <br>
 */
public class UserQuery  extends QueryBean{

	public UserQuery(ActionForm form){
		super(form);
	}

	public UserQuery(ActionForm form, String start, String end) {
		super(form, start, end);
	}
    
	private String where = "";
	
	private ArrayList queryCondition(UserForm form){
		ArrayList paramList = new ArrayList();
		where = " where pa.delFlag=0 and pa.employeeCode!='admin'";
		if(form!=null){
			
			if (form.getEmployeeCode() != null && !form.getEmployeeCode().equals("")) {
				where = where + " and pa.employeeCode like :employeeCode";
				QueryParameter param = new QueryParameter();
				param.setName("employeeCode");
				param.setValue(form.getEmployeeCode());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			if (form.getUserName() != null && !form.getUserName().equals("")) {
				where = where + " and pa.userName like :userName";
				QueryParameter param = new QueryParameter();
				param.setName("userName");
				param.setValue(form.getUserName());
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
		UserForm form = (UserForm)aform;
		AdvQueryString countQuery = new AdvQueryString();
		
		String queryString = "select count(pa) from UserForm as pa ";
		
		ArrayList paramList = this.queryCondition(form);
		
			
		queryString = queryString + where;

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
		UserForm form = (UserForm)aform;
		AdvQueryString listQuery = new AdvQueryString();
		
		//String queryString = "from UserForm as pa left outer join fetch pa.groups ";
		String queryString = "from UserForm as pa ";
		
		ArrayList paramList = this.queryCondition(form);
		
		queryString = queryString + where + " order by pa.id desc ";

		listQuery.setQueryString(queryString);
		listQuery.setParameters(paramList);

		return listQuery;

	}
    
    

}
