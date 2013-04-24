package com.dne.sie.reception.queryBean;

import java.util.*;

import com.dne.sie.util.query.*;

import org.hibernate.Hibernate;
import org.apache.struts.action.ActionForm;

import com.dne.sie.common.tools.Operate;
import com.dne.sie.reception.form.SaleInfoForm;

/**
 * 零件销售Query处理类
 * @author xt
 * @version 1.1.5.6
 * @see SaleInfoQuery.java <br>
 */
public class SaleInfoQuery extends QueryBean{

	public SaleInfoQuery(ActionForm form){
		super(form);
	}

	public SaleInfoQuery(ActionForm form, String start, String end) {
		super(form, start, end);
	}
    
   

	private String where = "";
	
    private ArrayList queryCondition(SaleInfoForm form){
		ArrayList paramList = new ArrayList();
		where = " where pa.delFlag=0 ";
		if(form!=null){
		
			if (form.getSaleNo() != null && !form.getSaleNo().equals("")) {
				where = where + " and pa.saleNo like :saleNo";
				QueryParameter param = new QueryParameter();
				param.setName("saleNo");
				param.setValue(form.getSaleNo());
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
			if (form.getServiceSheetNo() != null && !form.getServiceSheetNo().equals("")) {
				where = where + " and pa.serviceSheetNo like :serviceSheetNo";
				QueryParameter param = new QueryParameter();
				param.setName("serviceSheetNo");
				param.setValue(form.getServiceSheetNo());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			
			
			if (form.getCustomerPhone() != null && !form.getCustomerPhone().equals("")) {
				where = where + " and pa.customerPhone like :customerPhone";
				QueryParameter param = new QueryParameter();
				param.setName("customerPhone");
				param.setValue(form.getCustomerPhone());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}

			if (form.getStuffNo() != null && !form.getStuffNo().equals("")) {
				where = where + " and pa.saleNo in(select sdf.saleNo from SaleDetailForm as sdf " +
						"where sdf.stuffNo like :stuffNo and sdf.delFlag=0)";
				QueryParameter param = new QueryParameter();
				param.setName("stuffNo");
				param.setValue(form.getStuffNo());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}

			if (form.getSkuCode() != null && !form.getSkuCode().equals("")) {
				where = where + " and pa.saleNo in(select sdf.saleNo from SaleDetailForm as sdf " +
						"where sdf.skuCode like :skuCode and sdf.delFlag=0)";
				QueryParameter param = new QueryParameter();
				param.setName("skuCode");
				param.setValue(form.getSkuCode());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			if (form.getModelCode() != null && !form.getModelCode().equals("")) {
				where = where + " and pa.saleNo in(select sdf.saleNo from SaleDetailForm as sdf " +
						"where sdf.modelCode like :modelCode and sdf.delFlag=0)";
				QueryParameter param = new QueryParameter();
				param.setName("modelCode");
				param.setValue(form.getModelCode());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			if (form.getModelSerialNo() != null && !form.getModelSerialNo().equals("")) {
				where = where + " and pa.saleNo in(select sdf.saleNo from SaleDetailForm as sdf " +
						"where sdf.modelSerialNo like :modelSerialNo and sdf.delFlag=0)";
				QueryParameter param = new QueryParameter();
				param.setName("modelSerialNo");
				param.setValue(form.getModelSerialNo());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}

			if (form.getSaleStatus() != null && !form.getSaleStatus().equals("")) {
				if(form.getSaleStatus().equals("review")){	//复核
					where = where + " and pa.saleStatus in ('W','T')";		//无零件、已发货
				}else if(form.getSaleStatus().equals("ask")){	//询价
					where = where + " and pa.saleStatus in ('A','J')";		//向台询价中、保内审批拒绝
				}else if(form.getSaleStatus().equals("check")){	//核算
					where = where + " and pa.saleStatus in('D','B')";			//报价核算中
				}else if(form.getSaleStatus().equals("merge")){	//订单合并(纯销售)
					where = where + " and pa.saleStatus ='D' and pa.repairNo is null ";			//报价核算中
				}else if(form.getSaleStatus().equals("bf_review")){	//核算前
					where = where + " and pa.saleStatus not in('A','D','T','Z')";			
				}else{
					where = where + " and pa.saleStatus = :saleStatus";
					QueryParameter param = new QueryParameter();
					param.setName("saleStatus");
					param.setValue(form.getSaleStatus());
					param.setHbType(Hibernate.STRING);
					paramList.add(param);
				}
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
			
			if (form.getOrderMonth() != null && !form.getOrderMonth().equals("")) {
				where = where + " and pa.createDate >= :createDate1 and pa.createDate <= :createDate2";
				QueryParameter param = new QueryParameter();
				param.setName("createDate1");
				param.setValue(Operate.getFirstDayOfMonth(form.getOrderMonth()));
				param.setHbType(Hibernate.DATE);
				paramList.add(param);

				QueryParameter param2 = new QueryParameter();
				param2.setName("createDate2");
				param2.setValue(Operate.getLastDayOfMonth(form.getOrderMonth()));
				param2.setHbType(Hibernate.DATE);
				paramList.add(param2);		
			}

			if (form.getInvoiceNo1() != null && !form.getInvoiceNo1().equals("")) {
				where = where + " and pa.invoiceNo >= :invoiceNo1";
				QueryParameter param = new QueryParameter();
				param.setName("invoiceNo1");
				param.setValue(form.getInvoiceNo1());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);

			}
			if (form.getInvoiceNo2() != null && !form.getInvoiceNo2().equals("")) {
				where = where + " and pa.invoiceNo <= :invoiceNo2";
				QueryParameter param = new QueryParameter();
				param.setName("invoiceNo2");
				param.setValue(form.getInvoiceNo2());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);

			}
			
			//全部都是增税，不再区分
//			if (form.getInvoiceType() != null && !form.getInvoiceType().equals("")) {
//				where = where + " and pa.invoiceType = :invoiceType";
//				QueryParameter param = new QueryParameter();
//				param.setName("invoiceType");
//				param.setValue(form.getInvoiceType());
//				param.setHbType(Hibernate.STRING);
//				paramList.add(param);
//			}
			if (form.getCreateBy() != null && form.getCreateBy()!=0) {
				where = where + " and pa.createBy = :createBy";
				QueryParameter param = new QueryParameter();
				param.setName("createBy");
				param.setValue(form.getCreateBy());
				param.setHbType(Hibernate.LONG);
				paramList.add(param);
			}

			
			
			if (form.getDsaleNo() != null && !form.getDsaleNo().equals("")) {
				where = where + " and pa.saleNo != :dsaleNo";
				QueryParameter param = new QueryParameter();
				param.setName("dsaleNo");
				param.setValue(form.getDsaleNo());
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
		SaleInfoForm form = (SaleInfoForm)aform;
		AdvQueryString countQuery = new AdvQueryString();
		
		String queryString = "select count(pa) from SaleInfoForm as pa ";
		
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
		
		String queryString = "from SaleInfoForm as pa ";
		
		ArrayList paramList = this.queryCondition(form);
		
		queryString = queryString + where + " order by pa.saleNo desc ";

		listQuery.setQueryString(queryString);
		listQuery.setParameters(paramList);

		return listQuery;

	}
}
