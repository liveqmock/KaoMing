package com.dne.sie.stock.queryBean;

import java.util.*;

import com.dne.sie.util.query.*;

import org.hibernate.Hibernate;
import org.apache.struts.action.ActionForm;
import com.dne.sie.stock.form.StockFlowForm;
import com.dne.sie.common.tools.Operate;

/**
 * 出入库流水Query处理类
 * @author xt
 * @version 1.1.5.6
 * @see StockFlowQuery.java <br>
 */
public class StockFlowQuery extends QueryBean{

	public StockFlowQuery(ActionForm form){
		super(form);
	}

	public StockFlowQuery(ActionForm form, String start, String end) {
		super(form, start, end);
	}
    
   

	private String where = null;
	private String strFrom = null;
	
	//流水报表复用此方法
	public Object[] queryCondition(StockFlowForm form){
		Object[] obj=new Object[3];
		ArrayList paramList = new ArrayList();
		where = " where  1=1  ";
		strFrom = " StockFlowForm as pa ";
		try{
		
		if(form!=null){
				
						
			if (form.getFlowType() != null && !form.getFlowType().equals("")) {
				where = where + " and pa.flowType = :flowType";
				QueryParameter param = new QueryParameter();
				param.setName("flowType");
				param.setValue(form.getFlowType());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			
			if (form.getFlowId() != null && !form.getFlowId().toString().equals("0")) {
				where = where + " and pa.flowId = :flowId";
				QueryParameter param = new QueryParameter();
				param.setName("flowId");
				param.setValue(form.getFlowId());
				param.setHbType(Hibernate.LONG);
				paramList.add(param);
			}
			
			if (form.getFlowItem() != null && !form.getFlowItem().equals("")) {
				where = where + " and pa.flowItem = :flowItem";
				QueryParameter param = new QueryParameter();
				param.setName("flowItem");
				param.setValue(form.getFlowItem());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
				
			}
			
			if (form.getStuffNo() != null && !form.getStuffNo().equals("")) {
				where = where + " and pa.stuffNo like :stuffNo";
				QueryParameter param = new QueryParameter();
				param.setName("stuffNo");
				param.setValue(form.getStuffNo());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			
			if (form.getSkuCode() != null && !form.getSkuCode().equals("")) {
				where = where + " and pa.skuCode like :skuCode";
				QueryParameter param = new QueryParameter();
				param.setName("skuCode");
				param.setValue(form.getSkuCode());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			if (form.getStrSkuNum() != null && !form.getStrSkuNum().equals("")) {
				where = where + " and pa.skuNum = :strSkuNum";
				QueryParameter param = new QueryParameter();
				param.setName("strSkuNum");
				param.setValue(form.getStrSkuNum());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
				
			}
			if (form.getStrPerCost() != null && !form.getStrPerCost().equals("")) {
				where = where + " and pa.perCost = :strPerCost";
				QueryParameter param = new QueryParameter();
				param.setName("strPerCost");
				param.setValue(form.getStrPerCost());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
				
			}
			
			if (form.getInOutDate1() != null && !form.getInOutDate1().equals("")) {
				where = where + " and pa.createDate >= :inOutDate1";
				QueryParameter param = new QueryParameter();
				param.setName("inOutDate1");
				param.setValue(Operate.toDate(form.getInOutDate1()));
				param.setHbType(Hibernate.DATE);
				paramList.add(param);
			}
			if (form.getInOutDate2() != null && !form.getInOutDate2().equals("")) {
				where = where + " and pa.createDate < :inOutDate2";
				QueryParameter param = new QueryParameter();
				param.setName("inOutDate2");
				param.setValue(Operate.getNextDate(form.getInOutDate2()));
				param.setHbType(Hibernate.DATE);
				paramList.add(param);
			}
			
			if (form.getRemark() != null && !form.getRemark().equals("")) {
				where = where + " and pa.remark like :remark";
				QueryParameter param = new QueryParameter();
				param.setName("remark");
				param.setValue(form.getRemark());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			
			if (form.getFormNo() != null && !form.getFormNo().equals("")) {
				where = where + " and pa.formNo like :formNo";
				QueryParameter param = new QueryParameter();
				param.setName("formNo");
				param.setValue(form.getFormNo());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			
			if (form.getBinCode() != null && !form.getBinCode().equals("")) {
				where +=  " and pa.binCode like :binCode";
				QueryParameter param = new QueryParameter();
				param.setName("binCode");
				param.setValue(form.getBinCode());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			
			obj[0]=where;
			obj[1]=paramList;
			obj[2]=strFrom;
				
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return obj;
    }
    
   

	/**
	 *
	 * @todo Implement this ces.architect.util.QueryBean method
	 * @param actionform ActionForm
	 * @return AdvQueryString
	 */
	protected AdvQueryString generateCountQuery(ActionForm aform) {
		StockFlowForm form = (StockFlowForm)aform;
		AdvQueryString countQuery = new AdvQueryString();
		
		Object[] obj=this.queryCondition(form);
		String queryString = "select count(pa) from  "+obj[2];
		
		ArrayList paramList = (ArrayList)obj[1];
		
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
		StockFlowForm form = (StockFlowForm)aform;
		AdvQueryString listQuery = new AdvQueryString();
		
		Object[] obj=this.queryCondition(form);
		
		String queryString = "select pa from "+obj[2];
					
		ArrayList paramList = (ArrayList)obj[1];
		
		queryString = queryString + where + " order by pa.flowId desc ";

		listQuery.setQueryString(queryString);
		listQuery.setParameters(paramList);

		return listQuery;

	}
}
