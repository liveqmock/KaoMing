package com.dne.sie.logistic.queryBean;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;
import org.hibernate.Hibernate;

import com.dne.sie.reception.form.SaleInfoForm;
import com.dne.sie.util.query.AdvQueryString;
import com.dne.sie.util.query.QueryBean;
import com.dne.sie.util.query.QueryParameter;

/**
 * 出库操作Query处理类
 * @author xt
 * @version 1.1.5.6
 * @see AsnQuery.java <br>
 */
public class AsnQuery extends QueryBean{

	public AsnQuery(ActionForm form){
		super(form);
	}

	public AsnQuery(ActionForm form, String start, String end) {
		super(form, start, end);
	}
    
   

	private String where=null;
	
    private ArrayList queryCondition(SaleInfoForm form){
		ArrayList paramList = new ArrayList();
		try{
			where = " where sif.delFlag=0 and sd.delFlag=0  and sd.saleNo=sif.saleNo and sd.partStatus  in('M','Q','O') ";
			if(form!=null){
	
				if (form.getSaleNo() != null && !form.getSaleNo().equals("")) {
					where = where + " and sif.saleNo like :saleNo";
					QueryParameter param = new QueryParameter();
					param.setName("saleNo");
					param.setValue(form.getSaleNo());
					param.setHbType(Hibernate.STRING);
					paramList.add(param);
				}
				if (form.getStuffNo() != null && !form.getStuffNo().equals("")) {
					where = where + " and sd.stuffNo like :stuffNo";
					QueryParameter param = new QueryParameter();
					param.setName("stuffNo");
					param.setValue(form.getStuffNo());
					param.setHbType(Hibernate.STRING);
					paramList.add(param);
				}
				if (form.getSkuCode() != null && !form.getSkuCode().equals("")) {
					where = where + " and sd.skuCode like :skuCode";
					QueryParameter param = new QueryParameter();
					param.setName("skuCode");
					param.setValue(form.getSkuCode());
					param.setHbType(Hibernate.STRING);
					paramList.add(param);
				}
				
				if (form.getCustomerId() != null && !form.getCustomerId().equals("")) {
					where = where + " and sif.customerId = :customerId";
					QueryParameter param = new QueryParameter();
					param.setName("customerId");
					param.setValue(form.getCustomerId());
					param.setHbType(Hibernate.STRING);
					paramList.add(param);
				}
				if (form.getPayStatus() != null && !form.getPayStatus().equals("")) {
					where = where + " and sif.payStatus = :payStatus";
					QueryParameter param = new QueryParameter();
					param.setName("payStatus");
					param.setValue(form.getPayStatus());
					param.setHbType(Hibernate.STRING);
					paramList.add(param);
				}
				if (form.getBillingStatus() != null && !form.getBillingStatus().equals("")) {
					where = where + " and sif.billingStatus = :billingStatus";
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
		SaleInfoForm form = (SaleInfoForm)aform;
		AdvQueryString countQuery = new AdvQueryString();
		
		String queryString = "select count(sd) from SaleDetailForm as sd,SaleInfoForm as sif  ";
		
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
		SaleInfoForm form = (SaleInfoForm)aform;
		AdvQueryString listQuery = new AdvQueryString();
		
		String queryString = "select sd.saleDetailId,sd.saleNo,sd.stuffNo,sd.skuCode,"+
			"sd.partNum,sd.createDate,sif.payStatus,sif.billingStatus,sif.createBy,sd.version," +
			"sif.customerName,sd.partStatus,sd.asnNo "+
			" from SaleDetailForm as sd,SaleInfoForm as sif  ";
		
		ArrayList paramList = this.queryCondition(form);
		
		queryString = queryString + where + " order by sif.saleNo,sd.stuffNo";

		listQuery.setQueryString(queryString);
		listQuery.setParameters(paramList);

		return listQuery;

	}
}
