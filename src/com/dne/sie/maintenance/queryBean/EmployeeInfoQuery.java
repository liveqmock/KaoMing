package com.dne.sie.maintenance.queryBean;

import java.util.*;

import com.dne.sie.util.query.*;
import org.hibernate.Hibernate;
import org.apache.struts.action.ActionForm;
import com.dne.sie.maintenance.form.EmployeeInfoForm;

/**
 * 员工Query处理类
 * @author xt
 * @version 1.1.5.6
 * @see EmployeeInfoQuery.java <br>
 */
public class EmployeeInfoQuery extends QueryBean{

	public EmployeeInfoQuery(ActionForm form){
		super(form);
	}

	public EmployeeInfoQuery(ActionForm form, String start, String end) {
		super(form, start, end);
	}
    
   

	private String where = null;
	private ArrayList queryCondition(EmployeeInfoForm form){
		ArrayList paramList = new ArrayList();
		where = " where pa.delFlag=0 ";
		if(form!=null){
			
			if (form.getEmployeeName() != null && !form.getEmployeeName().equals("")) {
				where = where + " and pa.employeeName like :employeeName";
				QueryParameter param = new QueryParameter();
				param.setName("employeeName");
				param.setValue(form.getEmployeeName());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			if (form.getSex() != null && !form.getSex().equals("")) {
				where = where + " and pa.sex = :sex";
				QueryParameter param = new QueryParameter();
				param.setName("sex");
				param.setValue(form.getSex());
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
			if (form.getEmail() != null && !form.getEmail().equals("")) {
				where = where + " and pa.email like :email";
				QueryParameter param = new QueryParameter();
				param.setName("email");
				param.setValue(form.getEmail());
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
		EmployeeInfoForm form = (EmployeeInfoForm)aform;
		AdvQueryString countQuery = new AdvQueryString();
		
		String queryString = "select count(pa) from EmployeeInfoForm as pa ";
		
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
		EmployeeInfoForm form = (EmployeeInfoForm)aform;
		AdvQueryString listQuery = new AdvQueryString();
		
		String queryString = "select pa from EmployeeInfoForm as pa ";
		
		ArrayList paramList = this.queryCondition(form);
		
		queryString = queryString + where +" order by pa.employeeCode ";
		listQuery.setQueryString(queryString);
		listQuery.setParameters(paramList);

		return listQuery;

	}
}
