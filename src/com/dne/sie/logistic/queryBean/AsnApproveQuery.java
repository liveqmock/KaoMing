package com.dne.sie.logistic.queryBean;

import java.util.*;

import com.dne.sie.logistic.form.AsnForm;
import com.dne.sie.util.query.*;

import org.hibernate.Hibernate;
import org.apache.struts.action.ActionForm;

/**
 * 出库操作Query处理类
 * @author xt
 * @version 1.1.5.6
 * @see AsnApproveQuery.java <br>
 */
public class AsnApproveQuery extends QueryBean{

	public AsnApproveQuery(ActionForm form){
		super(form);
	}

	public AsnApproveQuery(ActionForm form, String start, String end) {
		super(form, start, end);
	}
    
   

	private String where=null;
	
    private ArrayList queryCondition(AsnForm form){
		ArrayList paramList = new ArrayList();
		try{
			where = " where 1=1 ";
			if(form!=null){
	
				if (form.getAsnNo() != null && !form.getAsnNo().equals("")) {
					where = where + " and pa.asnNo like :asnNo";
					QueryParameter param = new QueryParameter();
					param.setName("asnNo");
					param.setValue(form.getAsnNo());
					param.setHbType(Hibernate.STRING);
					paramList.add(param);
				}

				if (form.getAsnStatus() != null && !form.getAsnStatus().equals("")) {
					where = where + " and pa.asnStatus = :asnStatus";
					QueryParameter param = new QueryParameter();
					param.setName("asnStatus");
					param.setValue(form.getAsnStatus());
					param.setHbType(Hibernate.STRING);
					paramList.add(param);
				}
				if (form.getCustomerId() != null && !form.getCustomerId().equals("")) {
					where = where + " and pa.customerId = :customerId";
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

				if (form.getSaleNo() != null && !form.getSaleNo().equals("")) {
					where +=" and pa.asnNo in (select sdf.asnNo from SaleDetailForm as sdf " +
							" where sdf.saleNo like :saleNo )" ;
					QueryParameter param = new QueryParameter();
					param.setName("saleNo");
					param.setValue(form.getSaleNo());
					param.setHbType(Hibernate.STRING);
					paramList.add(param);
				}
				if (form.getSkuCode() != null && !form.getSkuCode().equals("")) {
					where +=" and pa.asnNo in (select sdf.asnNo from SaleDetailForm as sdf " +
					" where sdf.skuCode like :skuCode )";
					QueryParameter param = new QueryParameter();
					param.setName("skuCode");
					param.setValue(form.getSkuCode());
					param.setHbType(Hibernate.STRING);
					paramList.add(param);
				}

				if (form.getPayStatus() != null && !form.getPayStatus().equals("")) {
					where = where + " and pa.payStatus = :payStatus";
					QueryParameter param = new QueryParameter();
					param.setName("payStatus");
					param.setValue(form.getPayStatus());
					param.setHbType(Hibernate.STRING);
					paramList.add(param);
				}
				if (form.getBillingStatus() != null && !form.getBillingStatus().equals("")) {
					where = where + " and pa.billingStatus = :billingStatus";
					QueryParameter param = new QueryParameter();
					param.setName("billingStatus");
					param.setValue(form.getBillingStatus());
					param.setHbType(Hibernate.STRING);
					paramList.add(param);
				}

				
			}
		}catch(Exception e){
			e.printStackTrace();
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
		AsnForm form = (AsnForm)aform;
		AdvQueryString countQuery = new AdvQueryString();
		
		String queryString = "select count(*) from AsnForm as pa ";
		
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
		AsnForm form = (AsnForm)aform;
		AdvQueryString listQuery = new AdvQueryString();
		
		String queryString = "from AsnForm as pa ";
		
		ArrayList paramList = this.queryCondition(form);
		
		queryString = queryString + where + " order by pa.asnNo desc";

		listQuery.setQueryString(queryString);
		listQuery.setParameters(paramList);

		return listQuery;

	}
}
