package com.dne.sie.reception.queryBean;

import java.util.*;

import com.dne.sie.util.query.*;

import org.hibernate.Hibernate;
import org.apache.struts.action.ActionForm;

import com.dne.sie.common.tools.Operate;
import com.dne.sie.reception.form.SaleDetailForm;

/**
 * 手工分配查询Query处理类
 * @author xt
 * @version 1.1.5.6
 * @see ManualAllocateQuery.java <br>
 */
public class ManualAllocateQuery extends QueryBean{

	public ManualAllocateQuery(ActionForm form){
		super(form);
	}

	public ManualAllocateQuery(ActionForm form, String start, String end) {
		super(form, start, end);
	}
    
   

	private String where = "";
	
    private ArrayList queryCondition(SaleDetailForm form){
		ArrayList paramList = new ArrayList();
		where = " where pa.delFlag=0 and sif.stockStatus = 'A' and sif.stuffNo=pa.stuffNo";
		if(form!=null){
		
			if (form.getSaleNo() != null && !form.getSaleNo().equals("")) {
				where = where + " and pa.saleNo like :saleNo";
				QueryParameter param = new QueryParameter();
				param.setName("saleNo");
				param.setValue(form.getSaleNo());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}

			if (form.getStuffNo() != null && !form.getStuffNo().equals("")) {
				where = where + " and pa.stuffNo like :stuffNo ";
				QueryParameter param = new QueryParameter();
				param.setName("stuffNo");
				param.setValue(form.getStuffNo());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			if (form.getSkuCode() != null && !form.getSkuCode().equals("")) {
				where = where + " and pa.skuCode like :skuCode ";
				QueryParameter param = new QueryParameter();
				param.setName("skuCode");
				param.setValue(form.getSkuCode());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			if (form.getModelCode() != null && !form.getModelCode().equals("")) {
				where = where + " and pa.modelCode like :modelCode ";
				QueryParameter param = new QueryParameter();
				param.setName("modelCode");
				param.setValue(form.getModelCode());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			if (form.getModelSerialNo() != null && !form.getModelSerialNo().equals("")) {
				where = where + " and pa.modelSerialNo like :modelSerialNo ";
				QueryParameter param = new QueryParameter();
				param.setName("modelSerialNo");
				param.setValue(form.getModelSerialNo());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}

			if (form.getCreateBy() != null && form.getCreateBy()!=0) {
				where = where + " and pa.createBy = :createBy";
				QueryParameter param = new QueryParameter();
				param.setName("createBy");
				param.setValue(form.getCreateBy());
				param.setHbType(Hibernate.LONG);
				paramList.add(param);
			}
			
			if (form.getPartStatus() != null && !form.getPartStatus().equals("")) {
				where = where + " and pa.partStatus = :partStatus";
				QueryParameter param = new QueryParameter();
				param.setName("partStatus");
				param.setValue(form.getPartStatus());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			if (form.getInDate1() != null && !form.getInDate1().equals("")) {
				where += " and pa.createDate >= :inDate1";
				QueryParameter param = new QueryParameter();
				param.setName("inDate1");
				param.setValue(Operate.toDate(form.getInDate1()));
				param.setHbType(Hibernate.DATE);
				paramList.add(param);
			}
			if (form.getInDate2() != null && !form.getInDate2().equals("")) {
				where += " and pa.createDate < :inDate2";
				QueryParameter param = new QueryParameter();
				param.setName("inDate2");
				param.setValue(Operate.getNextDate(form.getInDate2()));
				param.setHbType(Hibernate.DATE);
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
		SaleDetailForm form = (SaleDetailForm)aform;
		AdvQueryString countQuery = new AdvQueryString();
		
		String queryString = "select count(distinct pa.saleDetailId) from SaleDetailForm as pa,StockInfoForm as sif ";
		
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
		SaleDetailForm form = (SaleDetailForm)aform;
		AdvQueryString listQuery = new AdvQueryString();
		
		String queryString = "select distinct pa.saleDetailId,pa.saleNo,pa.stuffNo,pa.skuCode," +
				"pa.skuUnit,pa.partNum,pa.modelCode,pa.modelSerialNo,pa.salePerPrice," +
				"(sif.skuNum),pa.createBy,pa.createDate,pa.version " +
				"from SaleDetailForm as pa,StockInfoForm as sif ";
		
		ArrayList paramList = this.queryCondition(form);
		
		queryString = queryString + where + " order by pa.saleNo,pa.stuffNo ";

		listQuery.setQueryString(queryString);
		listQuery.setParameters(paramList);

		return listQuery;

	}
}
