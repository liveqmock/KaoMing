package com.dne.sie.common.tools;

import com.dne.sie.maintenance.form.IrisCodeForm;
import com.dne.sie.maintenance.form.PartInfoForm;
import com.dne.sie.util.bo.CommBo;
import com.dne.sie.util.query.QueryParameter;
import org.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * NeWSIS 中一些通用的查询方法集合类，该类为单子模式
 * @author HaoShuang
 */
public class CommonSearch extends CommBo{

    private static HashMap userNameMap= new HashMap();
    public static List<IrisCodeForm> IrisInfoList = null;


    private static final CommonSearch INSTANCE = new CommonSearch();

    /**
     * 构造函数
     */
    private CommonSearch(){
    }


    /**
     * 静态方法，供外部人员拿到该类实例
     */
    public static final CommonSearch getInstance() {
        return INSTANCE;
    }

    public static float ExchangeRate = 0;


    /**
     * 根据用户ID查询用户名称
     * @param userid Long 用户ID
     * @return String 用户名称
     */
    public String findUserNameByUserId(Long userid){//根据用户ID查询用户名称
        String result = "";
        try{
            if(userid!=null){
                if(userNameMap.containsKey(userid)){
                    result=(String)userNameMap.get(userid);
                }else{
                    String strHql="select uf.userName from UserForm as uf where uf.id=?";
                    Object obj= this.getDao().uniqueResult(strHql,userid);
                    if(obj!=null){
                        result = (String)obj;
                        userNameMap.put(userid,result);
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }



    /**
     * 查询历史流水记录的零件成本
     * @param formNo-单号
     * @param skuCode-零件
     * @param inOutType-出入库标志
     * @return Double 成本
     */
    public Double getFlowCost(String formNo,String skuCode,String inOutType){
        Double perCost = null;
        try{
            Object obj=this.getDao().uniqueResult("select sff.realCost from StockFlowForm as sff " +
                    "where sff.formNo='"+formNo+"' and sff.skuCode='"+skuCode+"'and sff.inOutType='"+inOutType+"' " +
                    " order by sff.createDate desc ");
            if(obj!=null){
                perCost=(Double)obj;
            }else{
                System.err.println("单号:"+formNo+",零件:"+skuCode+",在流水表("+inOutType+")中的成本有误");
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return perCost;
    }
    /**
     * 根据零件代码取得零件的stdCost
     * @param stuffNo 零件编号
     * @return Double stdCost
     */
    public Float getPartPerCost(String stuffNo){
        Float perCost = 0f;
        try{
            Object obj=this.getDao().uniqueResult("select pif.buyCost from PartInfoForm as pif where pif.stuffNo=?",stuffNo);
            if(obj!=null) perCost=(Float)obj;
        }catch(Exception e){
            e.printStackTrace();
        }
        return perCost;
    }

    /**
     * 根据附件ids查询相关
     * @param aIds
     * @return Double stdCost
     */
    public List getAttachedList(String aIds) throws Exception{

        String strHql="from AttachedInfoForm as aif where aif.attachedId in (: aIds)";
        ArrayList<QueryParameter> paramList = new ArrayList<QueryParameter>();
        QueryParameter param = new QueryParameter();
        param.setName("aIds");
        param.setValue(aIds);
        param.setHbType(Hibernate.STRING);
        paramList.add(param);

        List aList = (ArrayList)this.getDao().parameterQuery(strHql, paramList);


        return aList;
    }


    /**
     * 查询客户信息
     * @param
     * @return List
     */
    public List getCustomerList() throws Exception{

        String strHql="select cif.customerId,cif.customerName,cif.linkman,cif.phone," +
                "cif.cityName,cif.fax,cif.address from CustomerInfoForm as cif where cif.delFlag=0";

        List aList = (ArrayList)this.getDao().list(strHql);


        return aList;
    }

    public String getCustomerName(String custId) throws Exception{

        String strHql="select cif.customerName from CustomerInfoForm as cif where cif.customerId=?";

        return (String)this.getDao().uniqueResult(strHql,custId);
    }

    /**
     * 查询厂商信息
     * @param
     * @return List
     */
    public List getFactoryList() throws Exception{

        String strHql="select cif.factoryId,cif.factoryName,cif.linkman,cif.phone," +
                "cif.cityName,cif.fax,cif.address from FactoryInfoForm as cif where cif.delFlag=0";

        List aList = (ArrayList)this.getDao().list(strHql);


        return aList;
    }


    /**
     * 查询待发货客户信息
     * @param
     * @return List
     */
    public List getAsnCustomerList() throws Exception{

        String strHql="select distinct sif.customerId,sif.customerName from SaleInfoForm as sif,SaleDetailForm as sd " +
                "where sd.saleNo=sif.saleNo and sif.delFlag=0 and sd.partStatus in('M','Q','O')";


        return this.getDao().list(strHql);
    }


    /**
     * 根据料号查询零件表
     * @param stuffNo 料号
     * @return PartInfoForm
     */
    public PartInfoForm getPartInfo(String stuffNo) throws Exception{
        PartInfoForm pi=null;
        String strHql="from PartInfoForm as pi where pi.stuffNo=:stuffNo";

        ArrayList<QueryParameter> paramList = new ArrayList<QueryParameter>();
        QueryParameter param = new QueryParameter();
        param.setName("stuffNo");
        param.setValue(stuffNo);
        param.setHbType(Hibernate.STRING);
        paramList.add(param);

        List<PartInfoForm> aList = this.getDao().parameterQuery(strHql, paramList);
        if(aList!=null&&aList.size()>0) pi=aList.get(0);

        return pi;
    }


    /**
     * 根据saleNo返回所有申请的StuffNo
     * @param saleNo 零saleNo编号
     * @return String StuffNo拼装
     */
    public String getStuffNos(String saleNo) throws Exception{
        String stuffNos=null;
        String strHql="select sif.stuffNo,sum(sif.partNum) from SaleDetailForm as sif where sif.saleNo = :saleNo " +
                "and sif.delFlag=0 group by sif.stuffNo";
        ArrayList<QueryParameter> paramList = new ArrayList<QueryParameter>();
        QueryParameter param = new QueryParameter();
        param.setName("saleNo");
        param.setValue(saleNo);
        param.setHbType(Hibernate.STRING);
        paramList.add(param);

        List aList = (ArrayList)this.getDao().parameterQuery(strHql, paramList);
        for(int i=0;aList!=null&&i<aList.size();i++){
            Object[] obj=(Object[])aList.get(i);
            if(i==0) stuffNos=obj[0]+" * "+obj[1];
            else stuffNos+="; "+obj[0]+" * "+obj[1];

        }

        return stuffNos;
    }


    /**
     * 计算最新的美元汇率
     * @return exchangeRate
     */
    public float getExchangeRate() throws Exception{
        if(ExchangeRate==0){
            String strHql="select sif.exchangeRate from SaleInfoForm as sif " +
                    "where sif.exchangeRate is not null and sif.delFlag=0 order by sif.saleNo desc";
            Float ecr=(Float)this.getDao().uniqueResult(strHql);
            if(ecr!=null) ExchangeRate=ecr.floatValue();
        }
        return ExchangeRate;
    }

    /**
     * 计算对应销售单的美元汇率
     * @param saleNo 零saleNo编号
     * @return exchangeRate
     */
    public float getExchangeRate(String saleNo) throws Exception{
        Float ecr = null;
        if(ExchangeRate==0){
            String strHql="select sif.exchangeRate from SaleInfoForm as sif " +
                    "where sif.saleNo = :saleNo ";

            ArrayList<QueryParameter> paramList = new ArrayList<QueryParameter>();
            QueryParameter param = new QueryParameter();
            param.setName("saleNo");
            param.setValue(saleNo);
            param.setHbType(Hibernate.STRING);
            paramList.add(param);

            List aList = (ArrayList)this.getDao().parameterQuery(strHql, paramList);
            if(aList!=null&&aList.size()>0){
                ecr= (Float)aList.get(0);
            }else{
                ecr = this.getExchangeRate();
            }

            if(ecr!=null) ExchangeRate=ecr.floatValue();
        }
        return ExchangeRate;
    }

    /**
     * 获取科目树id对应的name
     * @return
     * @throws Exception
     */
    public String getSubjectTreeName(Long subId) throws Exception{

        String strHql="select st.subjectName from SubjectTreeForm st where st.subjectId= :subjectId";
        QueryParameter param = new QueryParameter();
        param.setName("subjectId");
        param.setValue(subId);
        param.setHbType(Hibernate.LONG);

        String subName = (String)this.getDao().parameterUnique(strHql, param);


        return subName;
    }

    public Long getStockCount() throws Exception{
        String strHql="select count(*) from StockInfoForm ";
        return (Long)this.getDao().uniqueResult(strHql);
    }

    public List<IrisCodeForm> getIrisInfoList() throws Exception{
        if(IrisInfoList==null){
            IrisInfoList = (List<IrisCodeForm>)this.getDao().list("from IrisCodeForm as ff order by ff.parentCode,ff.orderId ");
        }
        return IrisInfoList;
    }

    public static void  main(String[] args){

    }
}
