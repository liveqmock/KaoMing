package com.dne.sie.reception.queryBean;

import java.util.*;

import com.dne.sie.util.query.*;

import org.hibernate.Hibernate;
import org.apache.struts.action.ActionForm;

import com.dne.sie.common.tools.Operate;
import com.dne.sie.reception.form.PoForm;

/**
 * 零件PO Query处理类
 * @author xt
 * @version 1.1.5.6
 * @see PartPoQuery <br>
 */
public class PartPoQuery extends QueryBean{

    public PartPoQuery(ActionForm form){
        super(form);
    }

    public PartPoQuery(ActionForm form, String start, String end) {
        super(form, start, end);
    }



    private String where = "";

    private ArrayList queryCondition(PoForm form){
        ArrayList paramList = new ArrayList();
        where = " where 1=1 ";
        if(form!=null){

            if (form.getOrderNo() != null && !form.getOrderNo().equals("")) {
                where = where + " and pa.orderNo like :orderNo";
                QueryParameter param = new QueryParameter();
                param.setName("orderNo");
                param.setValue(form.getOrderNo());
                param.setHbType(Hibernate.STRING);
                paramList.add(param);
            }
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

            if (form.getOrderStatus() != null && !form.getOrderStatus().equals("")) {
                where = where + " and pa.orderStatus = :orderStatus";
                QueryParameter param = new QueryParameter();
                param.setName("orderStatus");
                param.setValue(form.getOrderStatus());
                param.setHbType(Hibernate.STRING);
                paramList.add(param);
            }else{
                where+=" and pa.orderStatus !='X' ";
            }

            if (form.getOrderType() != null && !form.getOrderType().equals("")) {
                if(form.getOrderType().equals("out")){
                    where = where + " and pa.orderType != 'I'";
                }else{
                    where = where + " and pa.orderType = :orderType";
                    QueryParameter param = new QueryParameter();
                    param.setName("orderType");
                    param.setValue(form.getOrderType());
                    param.setHbType(Hibernate.STRING);
                    paramList.add(param);
                }
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
        PoForm form = (PoForm)aform;
        AdvQueryString countQuery = new AdvQueryString();

        String queryString = "select count(pa) from PoForm as pa ";

        ArrayList paramList = this.queryCondition(form);

        queryString = queryString + where;

        countQuery.setQueryString(queryString);
        countQuery.setParameters(paramList);

        return countQuery;
    }

    /**
     *
     * @todo Implement this ces.architect.util.QueryBean method
     * @param aform
     * @return AdvQueryString
     */
    protected AdvQueryString generateListQuery(ActionForm aform) {
        PoForm form = (PoForm)aform;
        AdvQueryString listQuery = new AdvQueryString();

        String queryString = "from PoForm as pa ";

        ArrayList paramList = this.queryCondition(form);

        if(form.getOrderStatus()==null||form.getOrderStatus().equals("")){	//零件订购清单
            queryString = queryString + where + " order by pa.orderNo desc,pa.stuffNo ";
        }else{
            queryString = queryString + where + " order by pa.saleNo,pa.stuffNo ";
        }
        listQuery.setQueryString(queryString);
        listQuery.setParameters(paramList);

        return listQuery;

    }


    /**
     * 用于统计每一种费用的费用和,根据查询条件生成相应的统计语句.
     * @param aform ActionForm 组装了用于结算单查询的查询条件
     * @return String 组装的查询语句
     */
    protected AdvQueryString getSumFeeHql(ActionForm aform){
        PoForm form = (PoForm)aform;
        AdvQueryString sumQuery = new AdvQueryString();
        ArrayList paramList = this.queryCondition(form);
        String queryString = "select sum(pa.perQuote * pa.orderNum) from PoForm as pa "+where;
        sumQuery.setQueryString(queryString);
        sumQuery.setParameters(paramList);

        return sumQuery;
    }
}
