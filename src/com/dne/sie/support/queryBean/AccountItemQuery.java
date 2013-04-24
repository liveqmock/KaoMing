package com.dne.sie.support.queryBean;

import java.util.*;

import com.dne.sie.util.query.*;

import org.hibernate.Hibernate;
import org.apache.struts.action.ActionForm;

import com.dne.sie.common.tools.Operate;
import com.dne.sie.support.form.AccountItemForm;

/**
 * 费用Query处理类
 * @author xt
 * @version 1.1.5.6
 * @see AccountItemQuery.java <br>
 */
public class AccountItemQuery extends QueryBean{

	public AccountItemQuery(ActionForm form){
		super(form);
	}

	public AccountItemQuery(ActionForm form, String start, String end) {
		super(form, start, end);
	}
    
   

	private String where = null;
	@SuppressWarnings("unchecked")
	private ArrayList queryCondition(AccountItemForm form){
		ArrayList paramList = new ArrayList();
		where = " where pa.delFlag=0 ";
		if(form!=null){
			

			if (form.getVoucherNo() != null && !form.getVoucherNo().equals("")) {
				where += " and pa.voucherNo like :voucherNo";
				QueryParameter param = new QueryParameter();
				param.setName("voucherNo");
				param.setValue(form.getVoucherNo());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			if (form.getPayType() != null && !form.getPayType().equals("")) {
				where += " and pa.payType = :payType";
				QueryParameter param = new QueryParameter();
				param.setName("payType");
				param.setValue(form.getPayType());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			if (form.getSubjectId() != null && form.getSubjectId()!=0) {
				where += " and pa.subjectId = :subjectId";
				QueryParameter param = new QueryParameter();
				param.setName("subjectId");
				param.setValue(form.getSubjectId());
				param.setHbType(Hibernate.LONG);
				paramList.add(param);
			}
			if (form.getSubjectAllName() != null && !form.getSubjectAllName().equals("")) {
				where += " and pa.subjectName like :subjectName";
				QueryParameter param = new QueryParameter();
				param.setName("subjectName");
				param.setValue(form.getSubjectAllName());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}

			if (form.getEmployeeCode() != null && form.getEmployeeCode()!=0) {
				where += " and pa.employeeCode = :employeeCode";
				QueryParameter param = new QueryParameter();
				param.setName("employeeCode");
				param.setValue(form.getEmployeeCode());
				param.setHbType(Hibernate.LONG);
				paramList.add(param);
			}
			if (form.getCustomerId() != null && !form.getCustomerId().equals("")) {
				where += " and pa.customerId = :customerId";
				QueryParameter param = new QueryParameter();
				param.setName("customerId");
				param.setValue(form.getCustomerId());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			if (form.getEmployeeName() != null && !form.getEmployeeName().equals("")) {
				where += " and pa.employeeName like :employeeName";
				QueryParameter param = new QueryParameter();
				param.setName("employeeName");
				param.setValue(form.getEmployeeName());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			if (form.getCustomerName() != null && !form.getCustomerName().equals("")) {
				where += " and pa.customerName like :customerName";
				QueryParameter param = new QueryParameter();
				param.setName("customerName");
				param.setValue(form.getCustomerName());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}

			if (form.getPlace() != null && !form.getPlace().equals("")) {
				where += " and pa.place like :place";
				QueryParameter param = new QueryParameter();
				param.setName("place");
				param.setValue(form.getPlace());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			if (form.getSummary() != null && !form.getSummary().equals("")) {
				where += " and pa.summary like :summary";
				QueryParameter param = new QueryParameter();
				param.setName("summary");
				param.setValue(form.getSummary());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			
			
			

			if (form.getMoney1() != null && !form.getMoney1().equals("")) {
				where += " and pa.money >= :money1";
				QueryParameter param = new QueryParameter();
				param.setName("money1");
				param.setValue(new Float(form.getMoney1()));
				param.setHbType(Hibernate.FLOAT);
				paramList.add(param);
			}
			if (form.getMoney2() != null && !form.getMoney2().equals("")) {
				where += " and pa.money <= :money2";
				QueryParameter param = new QueryParameter();
				param.setName("money2");
				param.setValue(new Float(form.getMoney2()));
				param.setHbType(Hibernate.FLOAT);
				paramList.add(param);
			}

			if (form.getFeeDate1() != null && !form.getFeeDate1().equals("")) {
				where += " and pa.feeDate >= :feeDate1";
				QueryParameter param = new QueryParameter();
				param.setName("feeDate1");
				param.setValue(Operate.toDate(form.getFeeDate1()));
				param.setHbType(Hibernate.DATE);
				paramList.add(param);
			}
			if (form.getFeeDate2() != null && !form.getFeeDate2().equals("")) {
				where += " and pa.feeDate < :feeDate2";
				QueryParameter param = new QueryParameter();
				param.setName("feeDate2");
				param.setValue(Operate.getNextDate(form.getFeeDate2()));
				param.setHbType(Hibernate.DATE);
				paramList.add(param);
			}

			if (form.getSubIds() != null && !form.getSubIds().equals("")) {
				where += " and pa.subjectId in ("+form.getSubIds()+")";
				
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
		AccountItemForm form = (AccountItemForm)aform;
		AdvQueryString countQuery = new AdvQueryString();
		
		String queryString = "select count(pa) from AccountItemForm as pa ";
		
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
		AccountItemForm form = (AccountItemForm)aform;
		AdvQueryString listQuery = new AdvQueryString();
		
		String queryString = "select pa from AccountItemForm as pa ";
		
		ArrayList paramList = this.queryCondition(form);
		
		queryString = queryString + where +" order by pa.accountId desc ";
		listQuery.setQueryString(queryString);
		listQuery.setParameters(paramList);

		return listQuery;

	}
}
