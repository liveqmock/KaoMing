package com.dne.sie.repair.queryBean;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;
import org.hibernate.Hibernate;

import com.dne.sie.common.tools.AtomRoleCheck;
import com.dne.sie.common.tools.Operate;
import com.dne.sie.repair.form.RepairSearchForm;
import com.dne.sie.util.query.AdvQueryString;
import com.dne.sie.util.query.QueryBean;
import com.dne.sie.util.query.QueryParameter;

/**
 * 维修Query处理类
 * @author xt
 * @version 1.1.5.6
 * @see RepairListQuery.java <br>
 */
public class RepairListQuery extends QueryBean{

	public RepairListQuery(ActionForm form){
		super(form);
	}

	public RepairListQuery(ActionForm form, String start, String end) {
		super(form, start, end);
	}
    
   

	private StringBuffer whereStrBuffer = null;
	
    private ArrayList queryCondition(RepairSearchForm form){
		ArrayList paramList = new ArrayList();
		whereStrBuffer = new StringBuffer(" where 1=1 ");
		if(form!=null){

			QueryParameter param = null;
			if (form.getServiceSheetNo() != null && !"".equals(form.getServiceSheetNo())) {
				whereStrBuffer.append(" and (rsf.serviceSheetNo like :serviceSheetNoUPCASE");
				param = new QueryParameter();
				param.setName("serviceSheetNoUPCASE");
				param.setValue(form.getServiceSheetNo().toUpperCase());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
				whereStrBuffer.append(")");
			}
			if (form.getRepairManStr() != null && !"".equals(form.getRepairManStr())) {
				whereStrBuffer.append(" and rsf.repairNo in (select mi.repairNo from RepairManInfoForm mi where mi.repairManName like :repairManStr )");
				param = new QueryParameter();
				param.setName("repairManStr");
				param.setValue(form.getRepairManStr());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			
			if (form.getCurrentUserId() != null && form.getCurrentUserId().longValue()!=0
						&& form.getRoleIds().indexOf(AtomRoleCheck.REPAIRMANAGER+"")==-1) {
				whereStrBuffer.append(" and exists (select 1 from RepairManInfoForm mi where mi.repairNo=rsf.repairNo and mi.repairMan = :currentUserId )");
				param = new QueryParameter();
				param.setName("currentUserId");
				param.setValue(form.getCurrentUserId());
				param.setHbType(Hibernate.LONG);
				paramList.add(param);
			}

			if (form.getSaleNo() != null && !"".equals(form.getSaleNo())) {
				whereStrBuffer.append(" and rsf.repairNo in (select si.repairNo from SaleInfoForm si where si.saleNo like :saleNo )");
				param = new QueryParameter();
				param.setName("saleNo");
				param.setValue(form.getSaleNo());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			if (form.getModelCode() != null && !"".equals(form.getModelCode())) {
				whereStrBuffer.append(" and (rsf.modelCode like :modelCodeUPCASE");
				param = new QueryParameter();
				param.setName("modelCodeUPCASE");
				param.setValue(form.getModelCode());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
				whereStrBuffer.append(")");
			}
			if (form.getSerialNo() != null && !"".equals(form.getSerialNo())) {
				whereStrBuffer.append(" and (rsf.serialNo like :serialNo");
				param = new QueryParameter();
				param.setName("serialNo");
				param.setValue(form.getSerialNo());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
				whereStrBuffer.append(")");
			}
			if (form.getWarrantyType() != null && !"".equals(form.getWarrantyType())) {
				whereStrBuffer.append(" and rsf.warrantyType = :warrantyType");
				param = new QueryParameter();
				param.setName("warrantyType");
				param.setValue(form.getWarrantyType());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			
			if (form.getCustomerName() != null && !"".equals(form.getCustomerName())) {
				whereStrBuffer.append(" and rsf.customerName like :customerName ");
				param = new QueryParameter();
				param.setName("customerName");
				param.setValue(form.getCustomerName());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
				
			}
			if (form.getLinkman() != null && !"".equals(form.getLinkman())) {
				whereStrBuffer.append(" and rsf.linkman like :linkman ");
				param = new QueryParameter();
				param.setName("linkman");
				param.setValue(form.getLinkman());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			if (form.getPhone() != null && !"".equals(form.getPhone())) {
				whereStrBuffer.append(" and rsf.phone like :phone ");
				param = new QueryParameter();
				param.setName("phone");
				param.setValue(form.getPhone());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			
			if (form.getCurrentStatus() != null && !"".equals(form.getCurrentStatus())) {
				if(form.getCurrentStatus().equals("DZ")){
					//电诊中,零件销售中
					whereStrBuffer.append(" and rsf.currentStatus in ('A','S') ");
				}else{
					whereStrBuffer.append(" and rsf.currentStatus = :currentStatus ");
					param = new QueryParameter();
					param.setName("currentStatus");
					param.setValue(form.getCurrentStatus());
					param.setHbType(Hibernate.STRING);
					paramList.add(param);
				}
			}
			
			if (form.getRepairProperites() != null && !"".equals(form.getRepairProperites())) {
				whereStrBuffer.append(" and rsf.repairProperites = :repairProperites ");
				param = new QueryParameter();
				param.setName("repairProperites");
				param.setValue(form.getRepairProperites());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			
			if (form.getRr90() != null && !"".equals(form.getRr90())) {
				if(form.getRr90().equals("serial")&&form.getRepairNo()!=null){
					//电诊中,零件销售中
					whereStrBuffer.append(" and rsf.repairNo!= :repairNo ");
					param = new QueryParameter();
					param.setName("repairNo");
					param.setValue(form.getRepairNo());
					param.setHbType(Hibernate.LONG);
					paramList.add(param);
				}else{
					whereStrBuffer.append(" and rsf.rr90 = :rr90 ");
					param = new QueryParameter();
					param.setName("rr90");
					param.setValue(form.getRr90());
					param.setHbType(Hibernate.STRING);
					paramList.add(param);
				}
			}
			
			//创建日期
			if (form.getStartDate() != null && !"".equals(form.getStartDate())) {
				whereStrBuffer.append(" and rsf.createDate >= :startDate"); 
				param = new QueryParameter();
				param.setName("startDate");
				param.setValue(Operate.toDate(form.getStartDate()));
				param.setHbType(Hibernate.DATE);
				paramList.add(param);
			}
			if (form.getEndDate() != null && !"".equals(form.getEndDate())) {
				whereStrBuffer.append(" and rsf.createDate < :endDate"); 
				param = new QueryParameter();
				param.setName("endDate");
				param.setValue(Operate.getNextDate(form.getEndDate()));
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
		RepairSearchForm form = (RepairSearchForm)aform;
		AdvQueryString countQuery = new AdvQueryString();
		
		String queryString = "select count(rsf) from RepairSearchForm as rsf ";
		
		ArrayList paramList = this.queryCondition(form);
		
		queryString += whereStrBuffer.toString();

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
		RepairSearchForm form = (RepairSearchForm)aform;
		AdvQueryString listQuery = new AdvQueryString();
		
		String queryString = "from RepairSearchForm as rsf ";
		
		ArrayList paramList = this.queryCondition(form);
		
		queryString += whereStrBuffer.toString() + " order by rsf.repairNo desc ";

		listQuery.setQueryString(queryString);
		listQuery.setParameters(paramList);

		return listQuery;

	}
}
