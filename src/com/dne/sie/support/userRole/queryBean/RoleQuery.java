package com.dne.sie.support.userRole.queryBean;

import java.util.*;

import com.dne.sie.util.query.*;

import org.hibernate.Hibernate;
import org.apache.struts.action.ActionForm;
//import com.dne.sie.common.tools.AtomRoleCheck;
import com.dne.sie.support.userRole.form.RoleForm;

/**
 * 权限Query处理类
 * @author xt
 * @version 1.1.5.6
 * @see RoleQuery.java <br>
 */
public class RoleQuery extends QueryBean{

	public RoleQuery(ActionForm form){
		super(form);
	}

	public RoleQuery(ActionForm form, String start, String end) {
		super(form, start, end);
	}
    
   

	private String where = "";
	
    private ArrayList queryCondition(RoleForm form){
		ArrayList paramList = new ArrayList();
		where = " where pa.delFlag=0 and pa.id!=0 ";
		if(form!=null){
		
			
		
			if (form.getRoleName() != null && !form.getRoleName().equals("")) {
				where = where + " and pa.roleName like :roleName";
				QueryParameter param = new QueryParameter();
				param.setName("roleName");
				param.setValue(form.getRoleName());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
//			if (form.getRoleType() != null && !form.getRoleType().equals("")) {
//				where = where + " and pa.roleType = :roleType";
//				QueryParameter param = new QueryParameter();
//				param.setName("roleType");
//				param.setValue(form.getRoleType());
//				param.setHbType(Hibernate.STRING);
//				paramList.add(param);
//			}
						
			if (form.getRemark() != null && !form.getRemark().equals("")) {
				where = where + " and pa.remark like :remark";
				QueryParameter param = new QueryParameter();
				param.setName("remark");
				param.setValue(form.getRemark());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			
//			if (form.getOrganizationCode() != null && form.getOrganizationCode().longValue()!=0) {
//				where = where + " and (pa.organizationCode = :organizationCode and pa.roleType='C')";
//				QueryParameter param = new QueryParameter();
//				param.setName("organizationCode");
//				param.setValue(form.getOrganizationCode());
//				param.setHbType(Hibernate.LONG);
//				paramList.add(param);
//			}
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
		RoleForm form = (RoleForm)aform;
		AdvQueryString countQuery = new AdvQueryString();
		
		String queryString = "select count(pa) from RoleForm as pa ";
		
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
		RoleForm form = (RoleForm)aform;
		AdvQueryString listQuery = new AdvQueryString();
		
		String queryString = "from RoleForm as pa ";
		
		ArrayList paramList = this.queryCondition(form);
		
		queryString = queryString + where + " order by pa.id desc ";

		listQuery.setQueryString(queryString);
		listQuery.setParameters(paramList);

		return listQuery;

	}
}
