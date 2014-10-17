package com.dne.sie.stock.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.dne.sie.common.exception.VersionException;
import com.dne.sie.common.tools.AtomRoleCheck;
import com.dne.sie.common.tools.Operate;
import com.dne.sie.reception.bo.SaleInfoBo;
import com.dne.sie.reception.form.PoForm;
import com.dne.sie.reception.form.SaleDetailForm;
import com.dne.sie.reception.form.SaleInfoForm;
import com.dne.sie.repair.form.RepairPartForm;
import com.dne.sie.stock.form.StockInfoForm;
import com.dne.sie.stock.form.StockToolsInfoForm;
import com.dne.sie.stock.queryBean.StockInfoQuery;
import com.dne.sie.util.bo.CommBo;

public class ReqAllocateBo extends CommBo {
    private static Logger logger = Logger.getLogger(ReqAllocateBo.class);

    private ArrayList reqAll=new ArrayList();
    private List stockAll=null;
    private Long reqID;
    private SaleDetailForm reqForm;
    private int reqFlag = 0;				//0为自动分配1为手工分配

    private String oldReqStat;

    public int allocate(Object[] requestID)  throws VersionException,Exception{
        //default sales
        return this.allocate(requestID, "S");

    }

    /**
     * 需求分配。
     * @param requestID   Long[] request编号
     * @return int
     */
    public synchronized int allocate(Object[] requestID,String type)  throws VersionException,Exception{
        int flag = 0;
        int partsNum;
        int oldNum;

        boolean t = false;
        String saleNo=null;

        String reqIds=Operate.arrayToString(requestID);

        List dataList=this.getReqList(reqIds);
        //System.out.println("是新需求分配还是手工分配====:"+reqFlag);
        //System.out.println("----dataList.size()="+dataList.size());
        for (int i=0;i<dataList.size();i++) {

            reqForm =(SaleDetailForm)dataList.get(i);
            if(i==0) saleNo=reqForm.getSaleNo();

            oldNum = reqForm.getPartNum().intValue();
            reqID = reqForm.getSaleDetailId();
            partsNum = oldNum;


//					System.out.println("开始分配此request====="+reqForm.getId());
//					System.out.println("此request的状态是======"+reqForm.getPartStatus());

            if(reqForm.getPartStatus().equals("H")||reqForm.getPartStatus().equals("F")){

                reqAll.add(reqForm);
                oldReqStat = reqForm.getPartStatus();

                //直购，跳过库存直接向台订购
                if("D".equals(reqForm.getOrderType())){
//							System.out.println("reqFlag>>>>>>>>>>"+reqFlag);
//							System.out.println("partsNum========="+partsNum);
                    if(this.reqFlag == 0 && partsNum > 0){
                        reqForm.setPartStatus("H");
                        reqForm.setUpdateBy(new Long(-1));
                        reqForm.setUpdateDate(new Date());
                        //t = this.getDao().update(reqForm);
//								
                        t=this.createPlan(reqForm);
                        if(!t){
                            flag = 3;
                        }

                    }else{
                        if(flag == 5 || flag == 0) flag = 5;
                        else flag = 4;
                    }

                }else{	//普通订购
                    for(int x=0;x<3;x++){
                        StockInfoForm siForm = new StockInfoForm();

                        if(partsNum  <= 0) break;
                        if(x==1){
                            //分配替代零件
                            break;
                        }else if(x==2){
                            //分配将来替代零件
                            break;
                        }else {
                            //分配自身零件

                            siForm.setStuffNo(reqForm.getStuffNo());
                            siForm.setStockStatus("A");
//									System.out.println("分配自身零件======="+siForm.getStuffNo());
                            partsNum=this.allocateStart(siForm,partsNum);
//									System.out.println("分配自身零件还剩余数量======"+partsNum);
                        }


                        if(partsNum > 0){
                            if(partsNum == oldNum){
                                if(flag == 5 || flag == 0) flag = 5;
                                else flag = 4;
                            }else{
                                flag = 4;
                            }
                            if(this.reqFlag == 0){	//判断是否为需求分配

                                reqForm.setPartStatus("H");
                                reqForm.setUpdateBy(new Long(-1));
                                reqForm.setUpdateDate(new Date());
                                //t = this.getDao().update(reqForm);
                                if(this.createPlan(reqForm)){
                                    continue;
                                }else{
                                    flag = 3;
                                }
                            }
                        }else{
                            if(flag == 0 || flag ==1 ){
                                flag = 1;
                            }else{
                                flag = 4;
                            }
                        }
                    }
                }

            }else{
                flag = 2;
            }
        }
        //更新大单状态
        SaleInfoBo.getInstance().renewSaleStatus(saleNo);

        return flag;
    }

    private List getReqList(String ids) throws VersionException,Exception {
        String versionId = Operate.toVersionData(ids);
        String strHql="from SaleDetailForm as sdf where CONCAT(sdf.saleDetailId,',',sdf.version) in ("+versionId+")";

        return this.listVersion(strHql, ids.split(",").length);
    }

    /**
     * 生成零件计划，准备发出PO。
     * @param sdf
     * @return boolean
     */
    private boolean createPlan(SaleDetailForm sdf) throws Exception{
        boolean flag = false;
        if(reqFlag != 0 ) return true;
        ArrayList al=new ArrayList();
        PoForm tempPlan = new PoForm();
        tempPlan.setRequestId(sdf.getSaleDetailId());
        tempPlan.setOrderType(sdf.getOrderType());
        tempPlan.setWarrantyType(sdf.getWarrantyType());

        tempPlan.setStuffNo(sdf.getStuffNo());
        tempPlan.setSkuCode(sdf.getSkuCode());
        tempPlan.setSkuUnit(sdf.getSkuUnit());

        //purchaseDollar台湾美元报价(purchasePrice是根据汇率exchangeRate转换后的台湾RMB报价)，实际RMB单价(PER_COST)在收货时再确认
        tempPlan.setPerQuote(sdf.getPurchaseDollar());
        tempPlan.setOrderNum(sdf.getPartNum());
        tempPlan.setModelCode(sdf.getModelCode());
        tempPlan.setModelSerialNo(sdf.getModelSerialNo());
        tempPlan.setOrderStatus("A");	//等待发送
        tempPlan.setSaleNo(sdf.getSaleNo());

        SaleInfoForm sif=SaleInfoBo.getInstance().findById(sdf.getSaleNo());
        tempPlan.setCustomerId(sif.getCustomerId());
        tempPlan.setCustomerName(sif.getCustomerName());
        tempPlan.setDeliveryTime((sdf.getDeliveryTimeStart()==null?"":sdf.getDeliveryTimeStart())+"~"+(sdf.getDeliveryTimeEnd()==null?"":sdf.getDeliveryTimeEnd()));
        tempPlan.setShippingAddress(sif.getShippingAddress());

        tempPlan.setCreateDate(new Date());
        tempPlan.setCreateBy(sdf.getCreateBy());	//零件实际的经办人

        Object[] obj1={tempPlan,"i"};
        al.add(obj1);
        Object[] obj2={sdf,"u"};
        al.add(obj2);
        flag = this.getBatchDao().allDMLBatch(al);

        return flag;
    }


    /**
     * 分配开始按照不同的申请类型进行相应的仓位顺序分配，最后返回分配后剩余的零件申请数量
     * @param siForm   StockInfoForm    需分配的库存信息
     * @param partsNum   int            零件申请数量
     * @return int  分配后剩余的零件申请数量
     */
    private int allocateStart(StockInfoForm siForm,int partsNum ) throws Exception{

        //销售零件需求分配逻辑(S=销售零件需求)
        if(!reqForm.getOrderType().equals("D") ){
            //先分配UN零件
//					siForm.setStockType("R");

            partsNum = this.allocateJob(siForm);
            if(partsNum > 0){
                //如还有需分配的零件,分配NN
//						siForm.setStockType("N");
//						partsNum = this.allocateJob(siForm);	

            }
            //其他维修借用零件需求分配
        }else{

        }

        return partsNum;
    }


    /**
     * 输入待分配的库存信息的查询条件form，进行分配
     * @param queryForm   StockInfoForm
     * @return int  分配的零件数量
     */
    private int allocateJob(StockInfoForm queryForm) throws Exception{
        int flag = 0 ;
        int count = 0;
        int partsNum = 0 ;

        queryForm.setStrSkuNum("-1");	//不分配数量为0的库存零件

        StockInfoQuery prq = new StockInfoQuery(queryForm);

        partsNum =  reqForm.getPartNum();

        stockAll = prq.doListQuery();
        count = prq.doCountQuery();

        //如果查询的库存记录数为0则返回申请零件需求的数量，以方便进行接下来的分配flag就代表需要进行分配的数量
        if(count == 0) {
            flag = partsNum;
            return flag;
        }else{

            partsNum = this.reqAllocate(partsNum,(ArrayList)stockAll);
            reqForm.setPartNum(new Integer(partsNum));
            flag = partsNum;
        }
        return flag;
    }


    /**
     * 提供特定零件编号符合舱位条件的（N,U仓）库存信息的arraylist以及需要分配的数量，<br>
     * 进行零件分配（arraylist已按入库时间进行了排序）
     * @param reqNum   int
     * @param dataArr    ArrayList
     * @return int 1=成功,-1=失败
     */
    private int reqAllocate(int reqNum,ArrayList dataArr) throws Exception{
        int leftNum = reqNum;
        int rang;
        //String allocatePartCode="";		//实际分配零件
        boolean t = false;

        for (int i=0;i<dataArr.size();i++) {
            StockInfoForm temp = (StockInfoForm)dataArr.get(i);
            //allocatePartCode=temp.getSkuCode();
            reqID = reqForm.getSaleDetailId();
            rang = temp.getSkuNum().intValue() - leftNum;
            //如果库存数量大于或等于申请数量
            if( rang >= 0 ){
                //库存数量大于申请数量
                if(rang > 0 ){
                    //修改库存数量为分配后剩余的数量
                    temp.setSkuNum(new Integer(rang));
                    temp.setUpdateBy(new Long(-1));
                    temp.setUpdateDate(new Date());
                    temp.setCreateBy(null);
                    t = this.getDao().update(temp);
                    //插入一条新的库存记录，库存状态为保留状态(R=保留状态)
                    temp.setSkuNum(new Integer(leftNum));
                    temp.setStockStatus("R");
                    temp.setRequestId(reqID);		//保留给当前零件申请的request
                    temp.setSkuType("S");
                    temp.setStockId(null);
                    temp.setUpdateBy(null);
                    temp.setUpdateDate(null);
                    temp.setCreateBy(new Long(-1));
                    t = t && this.getDao().insert(temp);
                    //库存数量等于申请数量
                }else{
                    //将库存状态改为保留状态(R=保留状态)
                    temp.setStockStatus("R");
                    temp.setRequestId(reqID);		//保留给当前零件申请的request
                    temp.setSkuType("S");
                    temp.setCreateBy(null);
                    temp.setUpdateBy(new Long(-1));
                    temp.setUpdateDate(new Date());
                    t = this.getDao().update(temp);
                }

                if(t){
                    //如果零件是保内订购，用了一个库存，则需要再补定一个补库
                    if(AtomRoleCheck.checkSaleIW(reqForm.getWarrantyType())){
                        if(this.createIWPlan(leftNum)){
                            System.err.println("ERROR TO CREATE INTERNAL PO!  REQUSET ID:" + reqID + "   PARTS NUM:" + leftNum);
                        }
                    }
                    leftNum = 0;
                }
                //申请已经满足了无须再进行分配，所有就break了
                break;
                //库存数量少于申请的数量
            }else{
                //不管库存是不是比申请的少先把可以分配的先分配
                temp.setStockStatus("R");
                temp.setRequestId(reqID);
                temp.setUpdateBy(new Long(-1));
                temp.setUpdateDate(new Date());
                temp.setCreateBy(null);
                t = this.getDao().update(temp);
                if(t){
                    if(AtomRoleCheck.checkSaleIW(reqForm.getWarrantyType())){
                        if(this.createIWPlan(leftNum)){
                            System.err.println("ERROR TO CREATE INTERNAL PO!  REQUSET ID:" + reqID + "   PARTS NUM:" + leftNum);
                        }

                    }
                    //这个leftNum就代表是零件申请进行分配过后还缺少的数量
                    leftNum = -(rang);
                }
            }
        }

        //reqNum申请数量-leftNum缺少或者还须分配的数量=已经分配的数量
        reqForm.setPartNum(new Integer(reqNum - leftNum));
        //L : 已分配待领取
        reqForm.setPartStatus("L");
        reqForm.setUpdateBy(new Long(-1));
        reqForm.setUpdateDate(new Date());

        t = this.getDao().update(reqForm);

        if(leftNum > 0 ){
            reqForm.setPartNum(new Integer(leftNum));
            reqForm.setPartStatus(oldReqStat);

            reqForm.setUpdateBy(null);
            reqForm.setUpdateDate(null);

            if(reqForm.getRootId()==null||reqForm.getRootId().longValue() == 0){
                reqForm.setRootId(reqForm.getSaleDetailId()) ;
                reqForm.setSaleDetailId(null);
            }else{
                reqForm.setSaleDetailId(null);
            }
            t = t && this.getDao().insert(reqForm);

        }


        return  leftNum;
    }


    /**
     * 保内订购，用了一个库存，则需要再补定一个补库
     * @param partsNum   int   零件数量
     * @return boolean
     */
    private boolean createIWPlan(int partsNum){
        boolean flag = false;
        try{



            PoForm tempPlan = new PoForm();
            tempPlan.setOrderType("N");	//保内补充自动订购
            tempPlan.setWarrantyType("I");
            tempPlan.setRequestId(reqForm.getSaleDetailId());

            tempPlan.setStuffNo(reqForm.getStuffNo());
            tempPlan.setSkuCode(reqForm.getSkuCode());
            tempPlan.setSkuUnit(reqForm.getSkuUnit());

            //purchaseDollar台湾美元报价(purchasePrice是根据汇率exchangeRate转换后的台湾RMB报价)，实际RMB单价(PER_COST)在收货时再确认
            tempPlan.setPerQuote(reqForm.getPurchaseDollar());
            tempPlan.setOrderNum(partsNum);
            tempPlan.setModelCode(reqForm.getModelCode());
            tempPlan.setModelSerialNo(reqForm.getModelSerialNo());
            tempPlan.setOrderStatus("A");	//等待发送
            tempPlan.setSaleNo(reqForm.getSaleNo());

            SaleInfoForm sif=SaleInfoBo.getInstance().findById(reqForm.getSaleNo());
            tempPlan.setCustomerId(sif.getCustomerId());
            tempPlan.setCustomerName(sif.getCustomerName());
            tempPlan.setDeliveryTime((reqForm.getDeliveryTimeStart()==null?"":reqForm.getDeliveryTimeStart())+"~"+(reqForm.getDeliveryTimeEnd()==null?"":reqForm.getDeliveryTimeEnd()));
            tempPlan.setShippingAddress(sif.getShippingAddress());

            tempPlan.setCreateDate(new Date());
            tempPlan.setCreateBy(reqForm.getCreateBy());	//零件实际的经办人

            flag = this.getDao().insert(tempPlan);
        }catch(Exception e) {
            e.printStackTrace();
        }

        return flag;
    }


    public synchronized boolean allocateLoan(RepairPartForm rpf,Long stockNum) {
        try{
            int leftNum = rpf.getApplyQty();

            if(stockNum - leftNum<0){
                return false;
            }else{
                int rang;
                String table=null;
                if(rpf.getRepairPartType().equals("X")){
                    table="StockInfoForm";
                }else if(rpf.getRepairPartType().equals("T")){
                    table="StockToolsInfoForm";
                }
                List stockInfoList = this.getDao().list("from "+table+" si where si.stuffNo=? and si.stockStatus='A' order by si.stockId",rpf.getStuffNo());
                reqID = rpf.getPartsId();
                for (int i=0;i<stockInfoList.size();i++) {
                    if(rpf.getRepairPartType().equals("X")){
                        StockInfoForm temp = (StockInfoForm)stockInfoList.get(i);

                        rang = temp.getSkuNum() - leftNum;
                        //如果库存数量大于或等于申请数量
                        if( rang >= 0 ){
                            //库存数量大于申请数量
                            if(rang > 0 ){
                                //修改库存数量为分配后剩余的数量
                                temp.setSkuNum(new Integer(rang));
                                temp.setUpdateBy(new Long(-1));
                                temp.setUpdateDate(new Date());
                                temp.setCreateBy(null);
                                this.getDao().update(temp);
                                //插入一条新的库存记录，库存状态为保留状态(R=保留状态)
                                temp.setSkuNum(new Integer(leftNum));
                                temp.setStockStatus("R");
                                temp.setRequestId(reqID);		//保留给当前零件申请的request
                                temp.setSkuType("L");
                                temp.setStockId(null);
                                temp.setUpdateBy(null);
                                temp.setUpdateDate(null);
                                temp.setCreateBy(new Long(-1));
                                this.getDao().insert(temp);
                                //库存数量等于申请数量
                            }else{
                                //将库存状态改为保留状态(R=保留状态)
                                temp.setStockStatus("R");
                                temp.setRequestId(reqID);		//保留给当前零件申请的request
                                temp.setSkuType("L");
                                temp.setCreateBy(null);
                                temp.setUpdateBy(new Long(-1));
                                temp.setUpdateDate(new Date());
                                this.getDao().update(temp);
                            }

                            //申请已经满足了无须再进行分配，所有就break了
                            break;
                            //库存数量少于申请的数量
                        }else{
                            //不管库存是不是比申请的少先把可以分配的先分配
                            temp.setStockStatus("R");
                            temp.setRequestId(reqID);
                            temp.setUpdateBy(new Long(-1));
                            temp.setUpdateDate(new Date());
                            temp.setCreateBy(null);
                            this.getDao().update(temp);
                        }

                    }else if(rpf.getRepairPartType().equals("T")){	//Tool
                        StockToolsInfoForm temp = (StockToolsInfoForm)stockInfoList.get(i);

                        rang = temp.getSkuNum() - leftNum;
                        //如果库存数量大于或等于申请数量
                        if( rang >= 0 ){
                            //库存数量大于申请数量
                            if(rang > 0 ){
                                //修改库存数量为分配后剩余的数量
                                temp.setSkuNum(new Integer(rang));
                                temp.setUpdateBy(new Long(-1));
                                temp.setUpdateDate(new Date());
                                temp.setCreateBy(null);
                                this.getDao().update(temp);
                                //插入一条新的库存记录，库存状态为保留状态(R=保留状态)
                                temp.setSkuNum(new Integer(leftNum));
                                temp.setStockStatus("R");
                                temp.setRequestId(reqID);		//保留给当前零件申请的request
                                temp.setSkuType("L");
                                temp.setStockId(null);
                                temp.setUpdateBy(null);
                                temp.setUpdateDate(null);
                                temp.setCreateBy(new Long(-1));
                                this.getDao().insert(temp);
                                //库存数量等于申请数量
                            }else{
                                //将库存状态改为保留状态(R=保留状态)
                                temp.setStockStatus("R");
                                temp.setRequestId(reqID);		//保留给当前零件申请的request
                                temp.setSkuType("L");
                                temp.setCreateBy(null);
                                temp.setUpdateBy(new Long(-1));
                                temp.setUpdateDate(new Date());
                                this.getDao().update(temp);
                            }

                            //申请已经满足了无须再进行分配，所有就break了
                            break;
                            //库存数量少于申请的数量
                        }else{
                            //不管库存是不是比申请的少先把可以分配的先分配
                            temp.setStockStatus("R");
                            temp.setRequestId(reqID);
                            temp.setUpdateBy(new Long(-1));
                            temp.setUpdateDate(new Date());
                            temp.setCreateBy(null);
                            this.getDao().update(temp);
                        }

                    }
                }

            }
            return true;
        }catch(Exception e) {
            e.printStackTrace();
            return false;
        }

    }


    public static void main(String[] args) {
        try{
            ReqAllocateBo rab=new ReqAllocateBo();
            //Long[] requestID={new Long(15)};
            SaleInfoBo.getInstance().renewSaleStatus("PI100619002BBB");

        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param i  订购类型
     */
    public void setReqFlag(int i) {
        reqFlag = i;
    }


}
