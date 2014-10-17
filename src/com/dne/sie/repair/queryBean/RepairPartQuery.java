package com.dne.sie.repair.queryBean;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;
import org.hibernate.Hibernate;

import com.dne.sie.repair.form.RepairPartForm;
import com.dne.sie.util.query.AdvQueryString;
import com.dne.sie.util.query.QueryBean;
import com.dne.sie.util.query.QueryParameter;

/**
 * @author xt
 * @version 1.1.5.6
 * @see RepairPartQuery <br>
 */
public class RepairPartQuery extends QueryBean{

    public RepairPartQuery(ActionForm form){
        super(form);
    }

    public RepairPartQuery(ActionForm form, String start, String end) {
        super(form, start, end);
    }



    private String whereIn = " ";

    private ArrayList queryCondition(RepairPartForm form){
        whereIn = " where 1=1 ";
        ArrayList paramList = new ArrayList();
        if(form!=null){

            if (form.getServiceSheetNo() != null && !"".equals(form.getServiceSheetNo())) {
                whereIn +=  " and rsf.serviceSheetNo like :serviceSheetNo";
                QueryParameter param = new QueryParameter();
                param.setName("serviceSheetNo");
                param.setValue(form.getServiceSheetNo());
                param.setHbType(Hibernate.STRING);
                paramList.add(param);
            }



            if (form.getSkuCode() != null && !form.getSkuCode().equals("")) {
                whereIn +=  " and rsf.skuCode like :skuCode";
                QueryParameter param = new QueryParameter();
                param.setName("skuCode");
                param.setValue(form.getSkuCode());
                param.setHbType(Hibernate.STRING);
                paramList.add(param);
            }

            if (form.getStandard() != null && !form.getStandard().equals("")) {
                whereIn +=  " and rsf.standard like :standard";
                QueryParameter param = new QueryParameter();
                param.setName("standard");
                param.setValue(form.getStandard());
                param.setHbType(Hibernate.STRING);
                paramList.add(param);
            }
            if (form.getStuffNo() != null && !form.getStuffNo().equals("")) {
                whereIn +=  " and rsf.stuffNo like :stuffNo";
                QueryParameter param = new QueryParameter();
                param.setName("stuffNo");
                param.setValue(form.getStuffNo());
                param.setHbType(Hibernate.STRING);
                paramList.add(param);
            }


            if (form.getSkuUnit() != null && !form.getSkuUnit().equals("")) {
                whereIn +=  " and rsf.skuUnit = :skuUnit";
                QueryParameter param = new QueryParameter();
                param.setName("skuUnit");
                param.setValue(form.getSkuUnit());
                param.setHbType(Hibernate.STRING);
                paramList.add(param);
            }

            if (form.getRepairPartType() != null && !form.getRepairPartType().equals("")) {
                whereIn +=  " and rsf.repairPartType = :repairPartType";
                QueryParameter param = new QueryParameter();
                param.setName("repairPartType");
                param.setValue(form.getRepairPartType());
                param.setHbType(Hibernate.STRING);
                paramList.add(param);
            }

            if (form.getRepairPartStatus() != null && !form.getRepairPartStatus().equals("")) {
                whereIn +=  " and rsf.repairPartStatus = :repairPartStatus";
                QueryParameter param = new QueryParameter();
                param.setName("repairPartStatus");
                param.setValue(form.getRepairPartStatus());
                param.setHbType(Hibernate.STRING);
                paramList.add(param);
            }
            if (form.getWarrantyType() != null && !form.getWarrantyType().equals("")) {
                whereIn +=  " and rsf.warrantyType = :warrantyType";
                QueryParameter param = new QueryParameter();
                param.setName("warrantyType");
                param.setValue(form.getWarrantyType());
                param.setHbType(Hibernate.STRING);
                paramList.add(param);
            }

            if (form.getCreateBy() != null && form.getCreateBy().longValue()!=0) {
                whereIn +=  " and rsf.createBy = :createBy";
                QueryParameter param = new QueryParameter();
                param.setName("createBy");
                param.setValue(form.getCreateBy());
                param.setHbType(Hibernate.LONG);
                paramList.add(param);
            }


            if (form.getUpdateBy() != null && form.getUpdateBy().longValue()!=0) {
                whereIn +=  " and rsf.updateBy = :updateBy";
                QueryParameter param = new QueryParameter();
                param.setName("updateBy");
                param.setValue(form.getUpdateBy());
                param.setHbType(Hibernate.LONG);
                paramList.add(param);
            }


        }
        return paramList;
    }



    /**
     *
     * @todo Implement this ces.architect.util.QueryBean method
     * @param aform ActionForm
     * @return AdvQueryString
     */
    protected AdvQueryString generateCountQuery(ActionForm aform) {
        RepairPartForm form = (RepairPartForm)aform;
        AdvQueryString countQuery = new AdvQueryString();

        ArrayList paramList = this.queryCondition(form);
        String queryString = "select count(rsf) from RepairPartForm as rsf "+whereIn;

        countQuery.setQueryString(queryString);
        countQuery.setParameters(paramList);

        return countQuery;
    }

    /**
     *
     * @todo Implement this ces.architect.util.QueryBean method
     * @param aform ActionForm
     * @return AdvQueryString
     */
    protected AdvQueryString generateListQuery(ActionForm aform) {
        RepairPartForm form = (RepairPartForm)aform;
        AdvQueryString listQuery = new AdvQueryString();

        ArrayList paramList = this.queryCondition(form);
        String queryString = "from RepairPartForm as rsf "+ whereIn + " order by rsf.partsId desc ";

        listQuery.setQueryString(queryString);
        listQuery.setParameters(paramList);

        return listQuery;

    }
}
