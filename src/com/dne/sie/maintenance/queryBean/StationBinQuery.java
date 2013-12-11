package com.dne.sie.maintenance.queryBean;

import java.util.*;

import com.dne.sie.util.query.*;
import org.hibernate.Hibernate;
import org.apache.struts.action.ActionForm;
import com.dne.sie.maintenance.form.StationBinForm;

/**
 * 仓位Query处理类
 * @author xt
 * @version 1.1.5.6
 * @see StationBinQuery.java <br>
 */
public class StationBinQuery extends QueryBean{

	public StationBinQuery(ActionForm form){
		super(form);
	}

	public StationBinQuery(ActionForm form, String start, String end) {
		super(form, start, end);
	}
    
   

	private String where = null;
	private ArrayList queryCondition(StationBinForm form){
		ArrayList paramList = new ArrayList();
		where = " where pa.delFlag=0 ";
		if(form!=null){
			if (form.getBinCode()!= null && !form.getBinCode().equals("")) {
				where = where + " and pa.binCode like :binCode";
				QueryParameter param = new QueryParameter();
				param.setName("binCode");
				param.setValue(form.getBinCode());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			
			if (form.getBinType()!= null && !form.getBinType().equals("")) {
				where = where + " and pa.binType = :binType";
				QueryParameter param = new QueryParameter();
				param.setName("binType");
				param.setValue(form.getBinType());
				param.setHbType(Hibernate.STRING);
				paramList.add(param);
			}
			
			if (form.getIsSysCtl()!= null && !form.getIsSysCtl().equals("")) {
				where = where + " and pa.isSysCtl = :isSysCtl";
				QueryParameter param = new QueryParameter();
				param.setName("isSysCtl");
				param.setValue(form.getIsSysCtl());
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
		StationBinForm form = (StationBinForm)aform;
		AdvQueryString countQuery = new AdvQueryString();
		
		String queryString = "select count(pa) from StationBinForm as pa ";
		
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
		StationBinForm form = (StationBinForm)aform;
		AdvQueryString listQuery = new AdvQueryString();
		
		String queryString = "select pa from StationBinForm as pa ";
		
		ArrayList paramList = this.queryCondition(form);
		
		queryString = queryString + where +" order by pa.binCode ";
		listQuery.setQueryString(queryString);
		listQuery.setParameters(paramList);

		return listQuery;

	}
}
