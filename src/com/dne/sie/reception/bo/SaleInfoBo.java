package com.dne.sie.reception.bo;

//Java 基础类

import com.dne.sie.common.exception.ComException;
import com.dne.sie.common.exception.VersionException;
import com.dne.sie.common.tools.AtomRoleCheck;
import com.dne.sie.common.tools.CommonSearch;
import com.dne.sie.common.tools.DicInit;
import com.dne.sie.common.tools.Operate;
import com.dne.sie.reception.form.SaleDetailForm;
import com.dne.sie.reception.form.SaleInfoForm;
import com.dne.sie.reception.form.SalePaymentForm;
import com.dne.sie.reception.queryBean.SaleInfoQuery;
import com.dne.sie.repair.bo.RepairHandleBo;
import com.dne.sie.repair.form.RepairPartForm;
import com.dne.sie.repair.form.RepairServiceForm;
import com.dne.sie.repair.form.RepairServiceStatusForm;
import com.dne.sie.stock.bo.ReqAllocateBo;
import com.dne.sie.stock.bo.StockOutBo;
import com.dne.sie.stock.form.StockInfoForm;
import com.dne.sie.support.userRole.bo.RoleBo;
import com.dne.sie.util.bo.CommBo;
import com.dne.sie.util.query.QueryParameter;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;


/**
 * 零件销售BO处理类
 * @author xt
 * @version 1.1.5.6
 * @see RoleBo <br>
 */
public class SaleInfoBo extends CommBo {
    private static Logger logger = Logger.getLogger(SaleInfoBo.class);

    private static final SaleInfoBo INSTANCE = new SaleInfoBo();

    private SaleInfoBo(){
    }

    public static final SaleInfoBo getInstance() {
        return INSTANCE;
    }

    /**
     * 列表查询拼装
     * @param dept 查询条件
     * @return ArrayList 查询结果
     */
    public ArrayList list(SaleInfoForm dept) throws Exception {
        List dataList = new ArrayList();
        ArrayList alData = new ArrayList();
        SaleInfoQuery dq = new SaleInfoQuery(dept);

        int count=0;

        dataList=dq.doListQuery(dept.getFromPage(),dept.getToPage());

        count=dq.doCountQuery();
        CommonSearch cs = CommonSearch.getInstance();
        for (int i=0;i<dataList.size();i++) {
            String[] data = new String[17];
            SaleInfoForm df = (SaleInfoForm)dataList.get(i);

            data[0] = df.getSaleNo();
            data[1] = cs.getCustomerName(df.getCustomerId());
            data[2] = df.getCustomerPhone();
            data[3] = df.getCreateDate().toLocaleString();
            data[4] = df.getTotalQuote()==null?"":df.getTotalQuote()+"";
            data[5] = df.getCostReal()==null?"":df.getCostReal()+"";
            data[6] = df.getProfitReal()==null?"":df.getProfitReal()+"";
            data[7] = DicInit.getSystemName("SALE_STATUS", df.getSaleStatus());
            data[8] = DicInit.getSystemName("PAY_STATUS", df.getPayStatus());
            data[9] = DicInit.getSystemName("BILLING_STATUS", df.getBillingStatus());
            data[10] = df.getShippingAddress();
            data[11] = cs.findUserNameByUserId(df.getCreateBy());
            data[12] = df.getServiceSheetNo();
            data[13] = DicInit.getSystemName("WARRANTY_TYPE", df.getWarrantyType());
            data[14] = df.getSaleStatus();

            data[15] = df.getTotalQuote()==null?"":Operate.toFix(df.getTotalQuote()*1.17,2);
            data[16] = df.getRepairQuote()==null?"":Operate.toFix(df.getRepairQuote(),2);

            alData.add(data);
        }
        alData.add(0,count+"");


        return alData;
    }


    /**
     * 销售明细 TD_SALES_DETAIL查询拼装
     * @param saleNo 查询条件
     * @return ArrayList 查询结果
     */
    public ArrayList detailList(String saleNo) throws Exception {

        List detailList = this.findDetailList(saleNo);
        ArrayList<String[]> formList=new ArrayList<String[]>();
        CommonSearch cs = CommonSearch.getInstance();
        StockOutBo sob = StockOutBo.getInstance();
        for(int i=0;i<detailList.size();i++){
            SaleDetailForm psf = (SaleDetailForm) detailList.get(i);

            String[] temp = new String[31];
            temp[0] = psf.getSaleDetailId()+"";
            temp[1] = psf.getModelCode()==null?"":psf.getModelCode();
            temp[2] = psf.getModelSerialNo()==null?"":psf.getModelSerialNo();
            temp[3] = psf.getSkuCode();
            temp[4] = psf.getStuffNo();
            temp[5] = psf.getPartNum()+"";
            temp[6] = psf.getPerQuote()==null?"":psf.getPerQuote().toString();
            temp[7] = psf.getDeliveryTimeStart()==null?"":psf.getDeliveryTimeStart();
            temp[8] = psf.getDeliveryTimeEnd()==null?"":psf.getDeliveryTimeEnd();
            temp[9] = psf.getOrderType();
            temp[10]=psf.getPurchasePrice()==null?"":psf.getPurchasePrice().toString();
            //报价核算时，海关增税默认为进价的17%
            temp[11]=psf.getVat()==null?"":psf.getVat().toString();
            if(psf.getVat()==null&&psf.getPurchasePrice()!=null){
                temp[11]=psf.getVat()==null?(psf.getPurchasePrice()*0.17f)+"":psf.getVat().toString();
            }
            temp[12]=psf.getCustomsChargesPlan()==null?"":psf.getCustomsChargesPlan().toString();
            temp[13]=psf.getCustomsChargesReal()==null?"":psf.getCustomsChargesReal().toString();
            temp[14]=psf.getDomesticFreightPlan()==null?"":psf.getDomesticFreightPlan().toString();
            temp[15]=psf.getDomesticFreightReal()==null?"":psf.getDomesticFreightReal().toString();
            temp[16]=psf.getCostPlan()==null?"":psf.getCostPlan().toString();
            temp[17]=psf.getCostReal()==null?"":psf.getCostReal().toString();

            temp[18]=psf.getSalePerPrice()==null?"":psf.getSalePerPrice().toString();
            temp[19]=psf.getProfitPlan()==null?"":psf.getProfitPlan().toString();
            temp[20]=psf.getProfitReal()==null?"":psf.getProfitReal().toString();
            temp[21]=psf.getPartStatus()==null?"":psf.getPartStatus();
            temp[22]=psf.getPurchaseDollar()==null?"":psf.getPurchaseDollar().toString();
            temp[23]=psf.getVersion()+"";
            temp[24]=psf.getTariffAmount()==null?"":psf.getTariffAmount().toString();
            temp[25]=psf.getTariffRat()==null?"":psf.getTariffRat().toString();
            temp[26] = psf.getWarrantyType();
            temp[27] = psf.getPurchaseDollar()==null?"":psf.getPurchaseDollar().toString();
            temp[28] = psf.getDeliveryTimeStart();
            temp[29] = psf.getDeliveryTimeEnd();
            if(psf.getPartStatus().equals("T")||psf.getPartStatus().equals("Z")){	//已发货 or 已复核
                //入库成本单价（RMB）
                Float cost = null;
                if("X".equals(psf.getOrderType())){	//携带转销售
                    cost = sob.findStockCostTransfer(psf.getPartsId());
                }else{
                    cost = sob.findStockCost(psf.getSaleDetailId());
                }

                temp[30] = cost==null?"0":cost.toString();
            }

            formList.add(temp);
        }
        return formList;
    }




    /**
     * 根据id查询SaleInfoForm信息
     * @param id 记录pk
     * @return SaleInfoForm
     */
    public SaleInfoForm findById(String id) throws Exception {
        SaleInfoForm  rf = (SaleInfoForm)this.getDao().findById(SaleInfoForm.class,id);

        return rf;
    }

    /**
     * 根据id查询SaleDetailForm信息
     * @param id 记录pk
     * @return SaleDetailForm
     */
    public SaleDetailForm findByDetailId(Long id) throws Exception {
        SaleDetailForm  sdf = (SaleDetailForm)this.getDao().findById(SaleDetailForm.class,id);

        return sdf;
    }


    /**
     * 根据id查询该权限信息
     * @param id 权限记录pk
     * @return 权限Form
     */
    public List findDetailList(String id)  throws Exception {
        String strHql="from SaleDetailForm as sd where sd.saleNo=:saleNo and sd.delFlag=0 order by sd.saleDetailId ";
        ArrayList<QueryParameter> paramList = new ArrayList<QueryParameter>();
        QueryParameter param = new QueryParameter();
        param.setName("saleNo");
        param.setValue(id);
        param.setHbType(Hibernate.STRING);
        paramList.add(param);

        List detailList = (ArrayList)this.getDao().parameterQuery(strHql, paramList);

        return detailList;
    }

    /**
     * 插入一条权限信息
     * @param rf 权限信息Form
     * @return 是否成功标志
     */
    public int add(SaleInfoForm rf) throws Exception {
        int tag=-1;
        boolean  t = this.getDao().insert(rf);

        if (t) {
            tag = 1;
        }
        return tag;
    }


    /**
     * 修改一条权限信息
     * @param rf 权限信息Form
     * @return 是否成功标志
     */
    public int modify(SaleInfoForm rf) throws Exception {
        int tag=-1;
        boolean t= this.getDao().update(rf);

        if (t) {
            tag = 1;
        }
        return tag;
    }

    /**
     * 批量录入
     * @param dataList - 待插入form集合
     * @return int 是否成功标志 1成功，-1失败
     */
    public int sellRegisterAdd(ArrayList dataList) throws Exception {
        int tag = -1;

        if (this.getBatchDao().allDMLBatch(dataList))
            tag = 1;

        return tag;
    }


    /**
     * 询价回复确认
     * @param
     * @return ArrayList 查询结果
     */
    public int saleAskSave(String[][] para,String flag,Long updateBy,
                           String saleNo,String exchangeRate) throws Exception {
        int tag = -1;

        ArrayList al=new ArrayList();
        float totalPrice=0;
        SaleInfoForm sif=this.findById(saleNo);
        if(exchangeRate!=null&&!exchangeRate.equals("")){
            sif.setExchangeRate(new Float(exchangeRate));
            CommonSearch.ExchangeRate=Float.parseFloat(exchangeRate);
        }
        boolean iw = false;

        for(int i=0;i<para[0].length;i++){
            SaleDetailForm sdf=this.findByDetailId(new Long(para[0][i]));
            sdf.setPurchaseDollar(toFloat(para[1][i],flag));
            sdf.setDeliveryTimeStart(para[2][i]);
            sdf.setDeliveryTimeEnd(para[3][i]);
            if(!sdf.getOrderType().equals("R"))
                sdf.setWarrantyType(para[4][i]);

            if(sif.getExchangeRate()!=null&&sif.getExchangeRate().floatValue()!=0){
                sdf.setPurchasePrice(Operate.roundF(sdf.getPurchaseDollar()*sif.getExchangeRate(),2));
            }


            sdf.setUpdateBy(updateBy);
            sdf.setUpdateDate(new Date());

            if("save".equals(flag)){	//暂存

            }else if("confirm".equals(flag)){	//回复确认
                if(AtomRoleCheck.checkSaleIW(sdf.getWarrantyType())		//保内订购
                        || (sdf.getPurchasePrice()!=null&&sdf.getPurchasePrice() == 0)){	//0价格
                    sdf.setPartStatus("B");		//保内审批中
                    iw = true;
                }else{
                    sdf.setPartStatus("D");		//报价核算中
                }
                totalPrice+=sdf.getPurchaseDollar()*sif.getExchangeRate()*sdf.getPartNum();
            }else if("cancel".equals(flag)){	//取消
                sdf.setDelFlag(1);
            }

            al.add(sdf);
        }

        if("save".equals(flag)){	//暂存
            sif.setUpdateBy(updateBy);
            sif.setUpdateDate(new Date());
            al.add(sif);
        }else if("confirm".equals(flag)){	//回复确认
            sif.setUpdateBy(updateBy);
            sif.setUpdateDate(new Date());
            if(iw){
                sif.setSaleStatus("B");		//保内审批中
            }else{
                sif.setSaleStatus("D");		//报价核算中
            }

            sif.setPayStatus("C");
            sif.setBillingStatus("C");
            sif.setPurchasePrice(Operate.roundF(totalPrice,2));
            al.add(sif);
        }else if("cancel".equals(flag)){	//取消
            sif.setUpdateBy(updateBy);
            sif.setUpdateDate(new Date());
            sif.setDelFlag(1);
            al.add(sif);
        }

        if(this.getBatchDao().updateBatch(al)){
            tag=1;
        }

        return tag;
    }



    /**
     * 报价核算确认
     * @param
     * @return tag
     */
    public int saleCheckConfirm(ArrayList detailList,String[] ids,String flag) throws VersionException,Exception {
        int tag = -1;

        boolean t=this.getBatchDao().updateBatch(detailList);

        if(t&&"confirm".equals(flag)){
            SaleInfoForm sif = (SaleInfoForm)detailList.get(0);
            if(!sif.getSaleStatus().equals("W")&&ids!=null){
                System.out.println("===========ReqAllocateBo begin============ids:"+ids);
                //调用分配逻辑
                new ReqAllocateBo().allocate(ids);
            }else if(sif.getRepairNo()!=null&&sif.getRepairNo().longValue()!=0){
                RepairServiceForm rsf = (RepairServiceForm)this.getDao().findById(RepairServiceForm.class, sif.getRepairNo());
                rsf.setCurrentStatus("T");
                rsf.setUpdateBy(sif.getUpdateBy());
                rsf.setUpdateDate(sif.getUpdateDate());

                RepairServiceStatusForm rssf = new RepairServiceStatusForm();
                rssf.setRepairStatus("T");		//销售完成
                rssf.setBeginDate(new Date());
                rssf.setCreateBy(rsf.getUpdateBy());
                rssf.setRepairServiceForm(rsf);
                rsf.getServiceStatusSet().add(rssf);

                this.getDao().update(rsf);
            }
            tag=1;
        }else if(t){
            tag=1;
        }

        return tag;
    }


    /**
     * String的金额转Float
     * @param
     * @return Float
     */
    public static Float toFloat(String money,String flag) throws Exception,ComException{
        if("confirm".equals(flag)&&(money==null||money.trim().equals(""))){
            throw new ComException();
        }
        return (money==null||money.trim().equals(""))?null:new Float(money);
    }

    /**
     * 合并
     * @param
     * @return Float
     */
    public int mergeDetailConfirm(String saleNoF,String saleNoT,String customerId) throws Exception{
        int tag=-1;
        List<String> al=new ArrayList<String>();
        String strUpdate1="update SaleDetailForm sdf set sdf.saleNo='"+saleNoT+"' " +
                "where sdf.saleNo='"+saleNoF+"' and sdf.saleNo like '%"+customerId+"'";

        String strUpdate2="update SaleInfoForm sif set sif.delFlag=1 where sif.saleNo='"+saleNoF+"'";
        String strUpdate3="update SaleInfoForm sif set " +
                "sif.partNum=(select sum(sd.partNum) " +
                " from SaleDetailForm as sd where sd.saleNo='"+saleNoT+"' and sd.delFlag=0)," +
                "sif.purchasePrice=(select sum(sd.purchasePrice*sd.partNum) " +
                " from SaleDetailForm as sd where sd.saleNo='"+saleNoT+"' and sd.delFlag=0), " +
                "sif.tariffAmount=(select sum(sd.tariffAmount*sd.partNum) " +
                " from SaleDetailForm as sd where sd.saleNo='"+saleNoT+"' and sd.delFlag=0), " +
                "sif.vat=(select sum(sd.vat*sd.partNum) " +
                " from SaleDetailForm as sd where sd.saleNo='"+saleNoT+"' and sd.delFlag=0), " +
                "sif.customsChargesReal=(select sum(sd.customsChargesReal*sd.partNum) " +
                " from SaleDetailForm as sd where sd.saleNo='"+saleNoT+"' and sd.delFlag=0), " +
                "sif.domesticFreightPlan=(select sum(sd.domesticFreightPlan*sd.partNum) " +
                " from SaleDetailForm as sd where sd.saleNo='"+saleNoT+"' and sd.delFlag=0), " +
                "sif.costPlan=(select sum(sd.costPlan*sd.partNum) " +
                " from SaleDetailForm as sd where sd.saleNo='"+saleNoT+"' and sd.delFlag=0), " +
                "sif.totalQuote=(select sum(sd.perQuote*sd.partNum) " +
                " from SaleDetailForm as sd where sd.saleNo='"+saleNoT+"' and sd.delFlag=0), " +
                "sif.profitPlan=(select sum(sd.profitPlan*sd.partNum) " +
                " from SaleDetailForm as sd where sd.saleNo='"+saleNoT+"' and sd.delFlag=0) " +
                "where sif.saleNo='"+saleNoT+"'";

        String strUpdate4="update SaleInfoForm sif set sif.profitPlan=sif.profitPlan-sif.repairFeePlan " +
                "where sif.saleNo='"+saleNoT+"'";
        al.add(strUpdate1);
        al.add(strUpdate2);
        al.add(strUpdate3);
        al.add(strUpdate4);
        if(this.getBatchDao().excuteBatch(al)){
            tag=1;
        }
        return tag;
    }


    /**
     * 获取订单所有年月
     * @return Float
     */
    public List<String> getSaleDateList() {

        return Operate.getMonthList();
    }


    /**
     * 获取经办人
     * @return Float
     */
    public List getCreateByList() {
        List al=new ArrayList();
        try{
            String hql="select distinct sif.createBy,uf.userName from SaleInfoForm as sif," +
                    "UserForm as uf where uf.id=sif.createBy ";
            al=this.getDao().list(hql);

        }catch(Exception e){
            e.printStackTrace();
        }
        return al;
    }

    /**
     * 获取经办人
     * @return Float
     */
    public List getPartCreateByList() {
        List al=new ArrayList();
        try{
            String hql="select distinct sif.createBy,uf.userName from SaleDetailForm as sif," +
                    "UserForm as uf where uf.id=sif.createBy ";
            al=this.getDao().list(hql);

        }catch(Exception e){
            e.printStackTrace();
        }
        return al;
    }

    /**
     * 根据销售单号，查询该单付费信息
     * @param saleNo String 销售单号
     * @return List 付费信息列表+客户已付总额
     */
    public List[] getSalePayedList(String saleNo) throws Exception{
        String strHql="from SalePaymentForm as spf where spf.saleNo=:saleNo order by spf.feeId ";
        ArrayList<QueryParameter> paramList = new ArrayList<QueryParameter>();
        QueryParameter param = new QueryParameter();
        param.setName("saleNo");
        param.setValue(saleNo);
        param.setHbType(Hibernate.STRING);
        paramList.add(param);

        List[] payedList={new ArrayList<String[]>(),new ArrayList<String[]>(),new ArrayList<Float>()};
        List<SalePaymentForm> payList = this.getDao().parameterQuery(strHql, paramList);
        float allPayAmount=0;
        for(int i=0;i<payList.size();i++){
            SalePaymentForm spf=payList.get(i);
            if(spf.getDataType().equals("P")){	//付款
                String[] tempP=new String[7];
                tempP[0]=spf.getFeeId().toString();
                tempP[1]=spf.getCreateDate().toString();
                tempP[2]=spf.getPayAmount().toString();
                tempP[3]=DicInit.getSystemName("PAY_TYPE", spf.getPayType());
                tempP[4]=spf.getClientBank()==null?"":spf.getClientBank();
                tempP[5]=spf.getPaymentCardNo()==null?"":spf.getPaymentCardNo();
                tempP[6]=DicInit.getSystemName("PAY_VARIETY", spf.getPayVariety());
                payedList[0].add(tempP);

                allPayAmount+=spf.getPayAmount();

            }else if(spf.getDataType().equals("V")){	//发票
                String[] tempV=new String[6];
                tempV[0]=spf.getFeeId().toString();
                tempV[1]=spf.getCreateDate().toString();
                tempV[2]=spf.getBillingMoney().toString();
                tempV[3]=DicInit.getSystemName("INVOICE_TYPE", spf.getInvoiceType());
                tempV[4]=spf.getInvoiceNo();
                tempV[5]=spf.getStuffNo();
                payedList[1].add(tempV);
            }
        }
        payedList[2].add(new Float(allPayAmount));

        return payedList;
    }


    /**
     * 获取客户客户已付总额
     * @param saleNo
     * @return Float 客户已付总额
     */
    public Float getBalanceDue(String saleNo) throws Exception {
        Float allPayAmount=null;
        String strHql="select sum(spf.payAmount) from SalePaymentForm as spf " +
                "where spf.saleNo=:saleNo and spf.dataType='P'";
        ArrayList<QueryParameter> paramList = new ArrayList<QueryParameter>();
        QueryParameter param = new QueryParameter();
        param.setName("saleNo");
        param.setValue(saleNo);
        param.setHbType(Hibernate.STRING);
        paramList.add(param);

        List<Double> payList = this.getDao().parameterQuery(strHql, paramList);
        Double d=payList.get(0);
        if(d!=null){
            allPayAmount=d.floatValue();
        }else{
            allPayAmount=0f;
        }

        return allPayAmount;
    }

    /**
     * 插入一行销售单付费金额\开票金额
     * @param spf
     * @return Float 客户已付总额
     */
    public Float addPayment(SalePaymentForm spf) throws Exception {
        boolean t=false;
        Float allPayAmount=null;
        ArrayList al=new ArrayList();
        if("P".equals(spf.getDataType())){	//付费金额
            Float havePay=this.getBalanceDue(spf.getSaleNo());
            //System.out.println("---havePay="+havePay);
            if(havePay!=null){	//有过付款记录
                spf.setBalanceDue(spf.getSaleTotalPrice()-havePay-spf.getPayAmount());
            }else{
                spf.setBalanceDue(spf.getSaleTotalPrice()-spf.getPayAmount());
            }
            Object[] obj={spf,"i"};
            al.add(obj);

            SaleInfoForm sif = this.findById(spf.getSaleNo());
//			double totalQuteWithTax = sif.getTotalQuteWithTax();
            //客户已付总额
            sif.setTotalPay(spf.getSaleTotalPrice()-spf.getBalanceDue());

            //付款状态
            if(Operate.roundD(new Double(sif.getTotalPay()),2) < sif.getTotalQuteWithTax()){
//			if(sif.getTotalPay().doubleValue()<totalQuteWithTax){
                sif.setPayStatus("B");	//有尾款
            }else{
                sif.setPayStatus("A");	//已达账
            }
            sif.setUpdateBy(spf.getCreateBy());
            sif.setUpdateDate(new Date());
            Object[] obj2={sif,"u"};
            al.add(obj2);

            if(this.getBatchDao().allDMLBatch(al)){
                allPayAmount=spf.getSaleTotalPrice()-spf.getBalanceDue();
            }

        }else if("V".equals(spf.getDataType())){	//开票金额
            Object[] obj={spf,"i"};
            al.add(obj);

            SaleInfoForm sif = this.findById(spf.getSaleNo());

            //开票金额
            sif.setBillingMoney((sif.getBillingMoney()==null?0:sif.getBillingMoney())
                    + (spf.getBillingMoney()==null?0:spf.getBillingMoney()));
            //开票状态
//			if(sif.getBillingMoney()<totalQuteWithTax){
            if(Operate.roundD(new Double(sif.getBillingMoney()),2)<sif.getTotalQuteWithTax()){
                sif.setBillingStatus("B");	//部分开票
            }else{
                sif.setBillingStatus("A");	//已开票
            }
            sif.setUpdateBy(spf.getCreateBy());
            sif.setUpdateDate(new Date());
            Object[] obj2={sif,"u"};
            al.add(obj2);

            if(this.getBatchDao().allDMLBatch(al)){
                allPayAmount=spf.getBillingMoney();
            }
        }
        return allPayAmount;
    }



    /**
     * 更新SaleInfoForm大单状态，为小单的最旧状态
     * @param    saleNo
     * @return boolean
     */
    public int renewSaleStatus(String saleNo) throws Exception{
        String strUpdate="update SaleInfoForm sif set sif.saleStatus= " +
                " (select min(sd.partStatus) from SaleDetailForm as sd " +
                "	where sd.saleNo='"+saleNo+"' and sd.delFlag=0 )" +
                " where sif.saleNo='"+saleNo+"'";

        return this.getDao().execute(strUpdate);
    }

    /**
     * 更新SaleInfoForm大单状态，为小单的最旧状态
     * @param saleNoList
     * @return boolean
     */
    public boolean renewSaleStatus(List saleNoList) throws Exception{
        ArrayList<String> updateList=new ArrayList<String>();
        for(int i=0;i<saleNoList.size();i++){
            String saleNo=(String)saleNoList.get(i);
            String strUpdate="update SaleInfoForm sif set sif.saleStatus= " +
                    " (select min(sd.partStatus) from SaleDetailForm as sd where sd.saleNo='"+saleNo+"')" +
                    " where sif.saleNo='"+saleNo+"'";
            updateList.add(strUpdate);
        }
        return this.getBatchDao().excuteBatch(updateList);
    }

    /**
     * 更新SaleInfoForm大单状态，为小单的最旧状态
     * @param    saleNo
     * @return boolean
     */
    public int renewSaleInfo(String saleNo) throws Exception{

        String strUpdate="update SaleInfoForm sif set " +
                "sif.saleStatus= (select min(sd.partStatus) " +
                " from SaleDetailForm as sd where sd.saleNo='"+saleNo+"' and sd.delFlag=0)," +
                "sif.partNum=(select sum(sd.partNum) " +
                " from SaleDetailForm as sd where sd.saleNo='"+saleNo+"' and sd.delFlag=0)," +
                "sif.purchasePrice=(select sum(sd.purchasePrice*sd.partNum) " +
                " from SaleDetailForm as sd where sd.saleNo='"+saleNo+"' and sd.delFlag=0), " +
                "sif.tariffAmount=(select sum(sd.tariffAmount*sd.partNum) " +
                " from SaleDetailForm as sd where sd.saleNo='"+saleNo+"' and sd.delFlag=0), " +
                "sif.vat=(select sum(sd.vat*sd.partNum) " +
                " from SaleDetailForm as sd where sd.saleNo='"+saleNo+"' and sd.delFlag=0), " +
                "sif.customsChargesReal=(select sum(sd.customsChargesReal*sd.partNum) " +
                " from SaleDetailForm as sd where sd.saleNo='"+saleNo+"' and sd.delFlag=0), " +
                "sif.domesticFreightPlan=(select sum(sd.domesticFreightPlan*sd.partNum) " +
                " from SaleDetailForm as sd where sd.saleNo='"+saleNo+"' and sd.delFlag=0), " +
                "sif.costPlan=(select sum(sd.costPlan*sd.partNum) " +
                " from SaleDetailForm as sd where sd.saleNo='"+saleNo+"' and sd.delFlag=0), " +
                "sif.totalQuote=(select sum(sd.perQuote*sd.partNum) " +
                " from SaleDetailForm as sd where sd.saleNo='"+saleNo+"' and sd.delFlag=0), " +
                "sif.profitPlan=(select sum(sd.profitPlan*sd.partNum) " +
                " from SaleDetailForm as sd where sd.saleNo='"+saleNo+"' and sd.delFlag=0) " +
                "where sif.saleNo='"+saleNo+"'";

        String strUpdate4="update SaleInfoForm sif set sif.profitPlan=sif.profitPlan-sif.repairFeePlan " +
                "where sif.saleNo='"+saleNo+"'";

        List<String> al=new ArrayList<String>();
        al.add(strUpdate);
        al.add(strUpdate4);

        int tag=-1;
        if(this.getBatchDao().excuteBatch(al)){
            tag=1;
        }

        return tag;

    }


    /**
     * 更新SaleInfoForm大单状态，为小单的最旧状态
     * 触发发货逻辑，更新对应维修单状态
     * @param saleNoList
     * @return boolean
     */
    public boolean renewSaleForConsign(List<String> saleNoList,List<Long> partsIdList) throws Exception{
        ArrayList<String> updateList=new ArrayList<String>();
        for(int i=0;i<saleNoList.size();i++){
            String saleNo=(String)saleNoList.get(i);
            String strUpdate="update SaleInfoForm sif set sif.saleStatus= " +
                    " (select min(sd.partStatus) from SaleDetailForm as sd where sd.saleNo='"+saleNo+"' and sd.delFlag = 0)" +
                    " where sif.saleNo='"+saleNo+"'";
            updateList.add(strUpdate);
        }
        if(this.getBatchDao().excuteBatch(updateList)){
            //String repairNos = "";
            HashSet<Long> statusSet = new HashSet<Long>();
            for(int i=0;i<saleNoList.size();i++){
                String saleNo=(String)saleNoList.get(i);
                String hql = "select sif.saleStatus,sif.repairNo  from SaleInfoForm as sif where sif.saleNo=?";
                List tempList = (List)this.getDao().list(hql,saleNo);
                Object[] obj = (Object[])tempList.get(0);

                if("T".equals((String)obj[0]) && obj[1]!=null && (Long)obj[1]!=0){
                    //repairNos+=","+obj[1];
                    statusSet.add((Long)obj[1]);
                }

            }
            if(statusSet.size()>0){
                //String update1 = "update RepairSearchForm rsf set rsf.currentStatus='T' where rsf.repairNo in ("+repairNos.substring(1)+") ";
                String update2 = "update RepairPartForm rp set rp.repairPartStatus='T' " +
                        "where rp.repairPartType='W' and rp.partsId in ("+Operate.arrayListToString(partsIdList)+")";
                ArrayList<Object[]> repairList=new ArrayList<Object[]>();

                //repairList.add(new Object[]{update1,"e"});
                repairList.add(new Object[]{update2,"e"});

                for(Long repairNo : statusSet){
                    RepairServiceForm rsf = (RepairServiceForm)this.getDao().findById(RepairServiceForm.class, repairNo);
                    rsf.setCurrentStatus("T");
                    rsf.setUpdateDate(new Date());

                    //设置维修状态
                    RepairServiceStatusForm rssf = new RepairServiceStatusForm();
                    rssf.setRepairStatus("T");		//销售完成
                    rssf.setBeginDate(new Date());
                    rssf.setCreateBy(0L);
                    rssf.setRepairServiceForm(rsf);
                    rsf.getServiceStatusSet().add(rssf);

                    repairList.add(new Object[]{rsf,"s"});
                }

                this.getBatchDao().allDMLBatch(repairList);
            }
        }
        return true;
    }

    /**
     * 销售零件单个取消，其中：
     * 	销售单状态：
     * 1."订购中"及之前：逻辑删除销售零件 （PO需人工判断取消）；
     * 2."已分配待领取"：还原库存零件状态为可用，逻辑删除销售零件；
     * 3."已出库未发货"及发货审批：逻辑删除销售零件，且设置取消状态，待库管处做回库；
     * 4.重算销售单总和数据。
     * @param saleDetailId
     * @return SaleNo为成功，null为失败
     */
    public String[] partCancel(String saleDetailId,Long userId) throws Exception{
        String[] tag={"-1",null};
        ArrayList updateList = new ArrayList();
        SaleDetailForm sdf=this.findByDetailId(new Long(saleDetailId));
        sdf.setDelFlag(new Integer(1));
        sdf.setUpdateBy(userId);
        sdf.setUpdateDate(new Date());
        tag[1]=sdf.getSaleNo();



        if(sdf.getPartStatus().equals("L")){	//已分配待领取
            //只取消销售保留零件 skuType='S'
            StockInfoForm sif=(StockInfoForm)this.getDao().uniqueResult("from StockInfoForm as sif " +
                    "where sif.stockStatus='R' and sif.skuType='S' and sif.requestId="+sdf.getSaleDetailId());
            if(sif!=null){
                //还原库存零件状态为可用
                sif.setStockStatus("A");
                sif.setRequestId(null);
                sif.setSkuType(null);
                sif.setUpdateBy(userId);
                sif.setUpdateDate(new Date());
                updateList.add(sif);
            }
        }else if(sdf.getPartStatus().equals("M")||sdf.getPartStatus().equals("N")
                ||sdf.getPartStatus().equals("O")||sdf.getPartStatus().equals("Q")){	//已出库未发货、发货审批

            sdf.setPartStatus("X");	//cancel

//			StockInfoForm sif=new StockInfoForm();
//			//自动插入原出库库存
//			sif.setStuffNo(sdf.getStuffNo());
//			sif.setSkuCode(sdf.getSkuCode());
//			sif.setSkuUnit(sdf.getSkuUnit());
//			sif.setSkuNum(sdf.getPartNum());
//			sif.setPerCost(sdf.getPurchasePrice());
//			sif.setSkuType("A");
//			sif.setStockStatus("A");
//			sif.setFlowNo(FormNumberBuilder.getStockFlowId());
//			sif.setCreateBy(userId);
//			sif.setUpdateDate(new Date());
//
//			StockFlowForm sff=StockInBo.getInstance().infoToFlow(sif);
//			sff.setSkuNum(sdf.getPartNum());
//			sff.setRestNum(StockOutBo.getInstance().getRestStock(sif.getStuffNo(), sdf.getPartNum(), "I"));
//			sff.setFlowType("I");		//入库
//			sff.setFlowItem("A"); 		//销售退回
//			sff.setFeeType(sdf.getOrderType());
//			sff.setFormNo(sdf.getSaleNo());
//			sff.setRequestId(sdf.getSaleDetailId());
//
//			updateList.add(sif);
//			updateList.add(sff);
        }
        updateList.add(0,sdf);

        try{
            if(this.getBatchDao().saveOrUpdateBatch(updateList)){
                String chkHql="select count(*) from SaleDetailForm as sdf " +
                        "where sdf.saleNo='"+sdf.getSaleNo()+"' and sdf.delFlag=0";
                Long count=(Long)this.getDao().uniqueResult(chkHql);
                SaleInfoForm sif=this.findById(sdf.getSaleNo());
                if(count==0){	//该单的所有零件都已取消
                    sif.setDelFlag(new Integer(1));
                    sif.setUpdateBy(userId);
                    sif.setUpdateDate(new Date());
                    this.getDao().update(sif);
                }else{	//更新大单状态
                    this.renewSaleInfo(sdf.getSaleNo());
                }

                //维修单关联取消
                if(sif.getRepairNo()!=null && sif.getRepairNo().longValue()!=0){
                    RepairPartForm returnForm = (RepairPartForm) this.getDao().findById(RepairPartForm.class,saleDetailId);
                    if(returnForm!=null){
                        returnForm.setRepairPartStatus("C");
                        returnForm.setUpdateDate(new Date());
                        returnForm.setUpdateBy(userId);
                        this.getDao().update(returnForm);
                    }
                }

                tag[0]="1";
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return tag;
    }

    /**
     * 已发货零件的退回
     * 	1.新增一行负销售零件(状态：退回)
     * 	2.修改客户应付总额及状态
     * 	3.产生待回库零件记录
     * @param saleDetailId
     * @param returnNum
     * @param userId
     * @throws Exception
     */
    public String partReturn(Long saleDetailId,Integer returnNum ,Long userId) throws Exception{
        SaleDetailForm sdf = findByDetailId(saleDetailId);
        List<Object[]> dataList = new ArrayList<Object[]>();

        if(sdf.getPartNum().intValue() == returnNum.intValue()){
            sdf.setPartStatus("X");
            sdf.setUpdateBy(userId);
            sdf.setUpdateDate(new Date());

            Object[] obj3={sdf,"u"};
            dataList.add(obj3);
        }else{

            //新增一行负销售零件
            SaleDetailForm returnSdf = new SaleDetailForm();
            returnSdf = (SaleDetailForm)RepairHandleBo.copyBeans(returnSdf, sdf);

            returnSdf.setStuffNo(sdf.getStuffNo());
            returnSdf.setPartNum(returnNum);

            //产生待回库零件记录
            returnSdf.setPartStatus("X");	//已退回

            returnSdf.setCreateBy(userId);
            returnSdf.setCreateDate(new Date());
            returnSdf.setUpdateBy(null);
            returnSdf.setUpdateDate(null);

            Object[] obj1={returnSdf,"i"};
            dataList.add(obj1);

            //修改原记录数量
            sdf.setPartNum(sdf.getPartNum() - returnNum);
            sdf.setUpdateBy(userId);
            sdf.setUpdateDate(new Date());

            Object[] obj3={sdf,"u"};
            dataList.add(obj3);
        }

        //修改客户应付总额及状态
        SaleInfoForm sif = this.findById(sdf.getSaleNo());
        if(sif.getTotalQuote()!=null){
            //客户应付总额
            sif.setTotalQuote(Operate.roundF(new Float(sif.getTotalQuote() - (sdf.getPerQuote()*returnNum)),2));

            //付款状态
            if(sif.getTotalPay()!=null){	//客户已付总额
                if(Operate.roundD(new Double(sif.getTotalPay()),2) < sif.getTotalQuteWithTax()){
                    sif.setPayStatus("B");	//有尾款
                }else{
                    sif.setPayStatus("A");	//已达账
                }
            }else{
                sif.setPayStatus("C");	//未达账
            }

            String remark=sif.getRemark()==null?"":sif.getRemark();
            sif.setRemark(remark+" ("+saleDetailId+")退回已发货零件:"+sdf.getPartNum()+",数量:"+returnNum+" "+Operate.getDate());
            sif.setUpdateBy(userId);
            sif.setUpdateDate(new Date());

            Object[] obj2={sif,"u"};
            dataList.add(obj2);
        }

        this.getBatchDao().allDMLBatch(dataList);

        return sdf.getSaleNo();
    }

    public void saleInfoCancel(String saleNo,Long userId) throws Exception{
        SaleInfoForm sif = this.findById(saleNo);
        sif.setDelFlag(1);
        sif.setUpdateBy(userId);
        sif.setUpdateDate(new Date());

        this.getDao().update(sif);

    }

    /**
     * 获取零件历史报价
     * @param stuffNo
     * @return
     */
    public ArrayList getHistoryPriceList(String stuffNo) throws Exception {
        ArrayList stuffList=new ArrayList();
        String strHql="select sdf,sif from SaleDetailForm as sdf,SaleInfoForm as sif where sdf.saleNo=sif.saleNo " +
                " and sdf.stuffNo=:stuffNo and sdf.partStatus>='H' and sdf.delFlag=0 order by sdf.saleDetailId ";
        ArrayList<QueryParameter> paramList = new ArrayList<QueryParameter>();
        QueryParameter param = new QueryParameter();
        param.setName("stuffNo");
        param.setValue(stuffNo);
        param.setHbType(Hibernate.STRING);
        paramList.add(param);

        List sdfList = this.getDao().parameterQuery(strHql, paramList);
        if(sdfList!=null&&sdfList.size()>0){
            CommonSearch cs = CommonSearch.getInstance();
            for(int i=0;i<sdfList.size();i++){
                Object[] obj=(Object[])sdfList.get(i);
                SaleDetailForm sdf = (SaleDetailForm)obj[0];
                SaleInfoForm sif = (SaleInfoForm)obj[1];
                String[] temp=new String[13];
                temp[0] = sdf.getStuffNo();
                temp[1] = sdf.getSkuCode();
                temp[2] = sif.getCustomerName();
                temp[3] = sdf.getModelCode();
                temp[4] = sdf.getModelSerialNo();
                temp[5] = sdf.getPartNum()+"";
                temp[6] = sdf.getPurchasePrice()==null?"":sdf.getPurchasePrice()+"";
                temp[7] = sdf.getPerQuote()==null?"":sdf.getPerQuote()+"";
                temp[8] = sdf.getProfitPlan()==null?"":sdf.getProfitPlan()+"";
                temp[9] = cs.findUserNameByUserId(sdf.getCreateBy());
                temp[10] = sdf.getCreateDate().toLocaleString();
                temp[11] = sdf.getSaleNo();
                temp[12] = DicInit.getSystemName("WARRANTY_TYPE",sdf.getWarrantyType());

                stuffList.add(temp);

            }
        }

        return stuffList;
    }



    /**
     * 获取客户历史报价
     * @param customerId
     * @return
     */
    public ArrayList getCustPriceList(String customerId) throws Exception {
        ArrayList custList=new ArrayList();
        String strHql="select sdf,sif from SaleDetailForm as sdf,SaleInfoForm as sif where sdf.saleNo=sif.saleNo " +
                " and sif.customerId=:customerId and sdf.partStatus>='H' and sdf.delFlag=0 order by sdf.saleNo,sdf.stuffNo";
        ArrayList<QueryParameter> paramList = new ArrayList<QueryParameter>();
        QueryParameter param = new QueryParameter();
        param.setName("customerId");
        param.setValue(customerId);
        param.setHbType(Hibernate.STRING);
        paramList.add(param);

        List sdfList = this.getDao().parameterQuery(strHql, paramList);
        if(sdfList!=null&&sdfList.size()>0){
            CommonSearch cs = CommonSearch.getInstance();
            for(int i=0;i<sdfList.size();i++){
                Object[] obj=(Object[])sdfList.get(i);
                SaleDetailForm sdf = (SaleDetailForm)obj[0];
                SaleInfoForm sif = (SaleInfoForm)obj[1];
                String[] temp=new String[13];
                temp[0] = sif.getCustomerName();
                temp[1] = sdf.getStuffNo();
                temp[2] = sdf.getSkuCode();
                temp[3] = sdf.getModelCode();
                temp[4] = sdf.getModelSerialNo();
                temp[5] = sdf.getPartNum()+"";
                temp[6] = sdf.getPurchasePrice()==null?"":sdf.getPurchasePrice()+"";
                temp[7] = sdf.getPerQuote()==null?"":sdf.getPerQuote()+"";
                temp[8] = sdf.getProfitPlan()==null?"":sdf.getProfitPlan()+"";
                temp[9] = cs.findUserNameByUserId(sdf.getCreateBy());
                temp[10] = sdf.getCreateDate().toLocaleString();
                temp[11] = sdf.getSaleNo();
                temp[12] = DicInit.getSystemName("WARRANTY_TYPE",sdf.getWarrantyType());

                custList.add(temp);

            }
        }

        return custList;
    }



    /**
     * 询价单打印
     * @param saleNo
     * @return
     */
    public List[] salePartsPrint(String saleNo) throws Exception {
        List[] saleList = new ArrayList[2];
        String strHql1="from SaleInfoForm as sif where sif.saleNo=?";
        saleList[0] = this.getDao().list(strHql1,saleNo);

        String strHql2="from SaleDetailForm as sdf where sdf.saleNo=? and sdf.delFlag=0 order by sdf.stuffNo";

        saleList[1] = this.getDao().list(strHql2,saleNo);


        return saleList;
    }




    /**
     * 查询销售单信息
     * @param
     * @return List
     */
    public List getSaleListByNo(String saleNo) throws Exception{

        String strHql="select saleNo,partNum,totalQuote," +
                "(select u.userName from UserForm u where u.id=sif.createBy)," +
                "(select sc.systemName from TsSystemCodeForm sc where sc.systemType='SALE_STATUS' and sc.systemCode=sif.saleStatus),  " +
                "(select sc.systemName from TsSystemCodeForm sc where sc.systemType='PAY_STATUS' and sc.systemCode=sif.payStatus),  " +
                " totalPay, " +
                "(select sc.systemName from TsSystemCodeForm sc where sc.systemType='BILLING_STATUS' and sc.systemCode=sif.billingStatus)," +
                " billingMoney,remark,purchasePrice " +
                " from SaleInfoForm as sif where sif.delFlag=0 and sif.repairNo is null and sif.saleNo like ?";

        return this.getDao().list(strHql,"%"+saleNo+"%");
    }


    /**
     * 查询销售零件信息
     * @param
     * @return List
     */
    public List getSalePartsListByNo(String saleNos) throws Exception{
        String strHql="select saleDetailId,saleNo,stuffNo,skuCode,partNum,purchasePrice" +
                " from SaleDetailForm sd where sd.saleNo in ('"+saleNos.replaceAll(",", "','")+"')" +
                " order by saleNo,stuffNo";

        return this.getDao().list(strHql);
    }

    /**
     * 查询销售零件信息
     * @param
     * @return List
     */
    public List getSalePartsListByNo(Long repairNo) throws Exception{
        String strHql="select sd.saleDetailId,sd.saleNo,sd.stuffNo,sd.skuCode," +
                "sd.partNum,sd.purchasePrice from SaleDetailForm sd,SaleInfoForm si where si.repairNo = ? " +
                " and sd.saleNo=si.saleNo order by si.saleNo,sd.stuffNo";

        return this.getDao().list(strHql,repairNo);
    }


    public int iwPartApprove(SaleInfoForm sif) throws Exception{
        int tag = -1;
        ArrayList al = new ArrayList();
        SaleInfoForm saleInfo = this.findById(sif.getSaleNo());
        saleInfo.setSaleStatus(sif.getSaleStatus());
        if(sif.getRemark()!=null && !sif.getRemark().trim().equals("")){
            saleInfo.setRemark((saleInfo.getRemark()==null?"":saleInfo.getRemark() )+"  审批备注："+sif.getRemark());
        }
        saleInfo.setUpdateBy(sif.getUpdateBy());
        saleInfo.setUpdateDate(new Date());
        al.add(saleInfo);

        List detailList = this.findDetailList(sif.getSaleNo());
        if(detailList!=null){
            for(int i=0;i<detailList.size();i++){
                SaleDetailForm sdf = (SaleDetailForm)detailList.get(i);
                sdf.setPartStatus(sif.getSaleStatus());
                sdf.setUpdateBy(sif.getUpdateBy());
                sdf.setUpdateDate(new Date());

                al.add(sdf);
            }

        }

        if(this.getBatchDao().updateBatch(al)){
            tag = 1;
        }

        return tag;
    }


    public boolean deleteSalePayment(SalePaymentForm spf) throws Exception{
        boolean ret = false;

        SalePaymentForm delRpf = (SalePaymentForm)this.getDao().findById(SalePaymentForm.class, spf.getFeeId());
        ret = this.getDao().delete(delRpf);

        return ret;
    }

}
