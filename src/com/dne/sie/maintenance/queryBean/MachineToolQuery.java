package com.dne.sie.maintenance.queryBean;

import java.util.*;

import com.dne.sie.util.query.*;
import org.hibernate.Hibernate;
import org.apache.struts.action.ActionForm;
import com.dne.sie.maintenance.form.MachineToolForm;

/**
 * 客户Query处理类
 * @author xt
 * @version 1.1.5.6
 * @see MachineToolQuery.java <br>
 */
public class MachineToolQuery extends QueryBean{

	public MachineToolQuery(ActionForm form){
		super(form);
	}

	public MachineToolQuery(ActionForm form, String start, String end) {
		super(form, start, end);
	}
    
   

	private String where = null;
	private ArrayList queryCondition(MachineToolForm form){
		ArrayList paramList = new ArrayList();
		where = " where pa.delFlag=0 ";
		if(form!=null){
			if (form.getCustomerId() != null && !form.getCustomerId().equals("")) {
				where = where + " and pa.customerId like :customerId";
				QueryParameter param = new QueryParameter();
				param.setName("customerId");
				param.setValue(form.getCustomerId());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			if (form.getCustomerName() != null && !form.getCustomerName().equals("")) {
				where = where + " and pa.customerName like :customerName";
				QueryParameter param = new QueryParameter();
				param.setName("customerName");
				param.setValue(form.getCustomerName());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			if (form.getMachineName() != null && !form.getMachineName().equals("")) {
				where = where + " and pa.machineName like :machineName";
				QueryParameter param = new QueryParameter();
				param.setName("machineName");
				param.setValue(form.getMachineName());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			if (form.getModelCode()!= null && !form.getModelCode().equals("")) {
				where = where + " and pa.modelCode like :modelCode";
				QueryParameter param = new QueryParameter();
				param.setName("modelCode");
				param.setValue(form.getModelCode());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			if (form.getSerialNo() != null && !form.getSerialNo().equals("")) {
				where = where + " and pa.serialNo like :serialNo";
				QueryParameter param = new QueryParameter();
				param.setName("serialNo");
				param.setValue(form.getSerialNo());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			if (form.getWarrantyCardNo() != null && !form.getWarrantyCardNo().equals("")) {
				where = where + " and pa.warrantyCardNo like :warrantyCardNo";
				QueryParameter param = new QueryParameter();
				param.setName("warrantyCardNo");
				param.setValue(form.getWarrantyCardNo());
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
		MachineToolForm form = (MachineToolForm)aform;
		AdvQueryString countQuery = new AdvQueryString();
		
		String queryString = "select count(pa) from MachineToolForm as pa ";
		
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
		MachineToolForm form = (MachineToolForm)aform;
		AdvQueryString listQuery = new AdvQueryString();
		
		String queryString = "select pa from MachineToolForm as pa ";
		
		ArrayList paramList = this.queryCondition(form);
		
		queryString = queryString + where +" order by pa.machineId desc";
		listQuery.setQueryString(queryString);
		listQuery.setParameters(paramList);

		return listQuery;

	}
}
