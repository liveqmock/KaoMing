package com.dne.sie.stock.bo;

import java.util.ArrayList;
import java.util.Date;

import org.hibernate.Hibernate;

import com.dne.sie.reception.bo.SaleInfoBo;
import com.dne.sie.reception.form.SaleDetailForm;
import com.dne.sie.stock.form.StockInfoForm;
import com.dne.sie.util.bo.CommBo;
import com.dne.sie.util.query.QueryParameter;

public class ReceiveAllocateBo extends CommBo{




    private ArrayList<SaleDetailForm> reqAll=new ArrayList<SaleDetailForm>();
    private String tempStat;
    private ArrayList<String> updateSaleList=new ArrayList<String>();
    /**
     * 到货分配。
     * @param stockList 到货接收入库的form
     * @return int
     */
    public synchronized int allocate(ArrayList stockList){
        int flag=-1;

        try{
            for (int i=0;i<stockList.size();i++) {
                StockInfoForm sif = (StockInfoForm)stockList.get(i);
                if(!"X".equals(sif.getStockStatus())) continue;

                //收货零件必须在PartInfoForm中有效
                //			PartInfoForm pi=PartInfoBo.getInstance().find(sif.getStuffNo());
                //			if(pi==null){
                //				continue;
                //			}

				/* PO对PO 的分配 */
                SaleDetailForm sdfQuery=new SaleDetailForm();
                sdfQuery.setSaleDetailId(sif.getRequestId());
                sdfQuery.setStuffNo(sif.getStuffNo());
                reqAll = this.getDetailForm(sdfQuery);

                flag = this.allocateJob(sif);
                if(flag == 0){
                    continue;
                }else{
                    sdfQuery.setSaleDetailId(null);
                    sif.setSkuNum(new Integer(flag));
                }
				/* PO对PO 分配结束 */


				/* 多余库存 的分配 */
                //sdfQuery.setReqType("repair");
                reqAll = this.getDetailForm(sdfQuery);

                flag = this.allocateJob(sif);
                if(flag == 0){
                    continue;
                }else{
                    sif.setSkuNum(new Integer(flag));
                }
				/* 多余库存 分配结束 */

				/* 把临时状态未分配的库存改为可用库存 */
                this.getDao().execute("update StockInfoForm as si set si.stockStatus='A' " +
                        " where si.stockStatus='X' and si.stockId="+sif.getStockId());

            }

            SaleInfoBo.getInstance().renewSaleStatus(updateSaleList);
        }catch(Exception e){
            e.printStackTrace();
        }

        return flag;
    }


    /**
     * 查询对应的request
     * @param sdfQuery
     * @return SaleDetailForm
     */
    private ArrayList<SaleDetailForm> getDetailForm(SaleDetailForm sdfQuery) throws Exception{
        ArrayList<SaleDetailForm> requestList = null;
        String where="";
        ArrayList<QueryParameter> paramList = new ArrayList<QueryParameter>();

        //查询订购中的销售零件
        String strHql="from SaleDetailForm as pa where pa.partStatus='H' ";

        where += " and pa.stuffNo = :stuffNo ";
        QueryParameter param0 = new QueryParameter();
        param0.setName("stuffNo");
        param0.setValue(sdfQuery.getStuffNo());
        param0.setHbType(Hibernate.STRING);
        paramList.add(param0);

        if(sdfQuery.getSaleDetailId()!=null){
            where += " and pa.saleDetailId = :saleDetailId ";
            QueryParameter param = new QueryParameter();
            param.setName("saleDetailId");
            param.setValue(sdfQuery.getSaleDetailId());
            param.setHbType(Hibernate.LONG);
            paramList.add(param);
        }

        strHql += where+" order by pa.saleDetailId ";
//		System.out.println("--xx---strHql="+strHql);
        requestList = (ArrayList)this.getDao().parameterQuery(strHql, paramList);
        //获取所分配的销售单号
        for(int j=0;j<requestList.size();j++){
            String saleNo=requestList.get(j).getSaleNo();
            if(!updateSaleList.contains(saleNo)){
                updateSaleList.add(saleNo);
            }
        }
        return requestList;
    }

    private int allocateJob(StockInfoForm sif) throws Exception{
        int restStockNum = sif.getSkuNum().intValue();
        int rang = 0 ;

        boolean t = false;

        for (int j=0;j<reqAll.size();j++) {
            SaleDetailForm reqForm = reqAll.get(j);
            rang = restStockNum - reqForm.getPartNum().intValue();

            if(rang > 0){ 	//分配后有剩余，拆分库存
                restStockNum = rang;

                reqForm.setPartStatus("L");		//已分配待领取
                reqForm.setUpdateBy(new Long(-1));
                reqForm.setUpdateDate(new Date());
                t = this.getDao().update(reqForm);

                if(t){
                    sif.setSkuNum(reqForm.getPartNum());
                    sif.setStockStatus("R");	//保留状态
                    sif.setRequestId(reqForm.getSaleDetailId());
                    sif.setSkuType("S");
                    sif.setUpdateBy(new Long(-1));
                    sif.setUpdateDate(new Date());
                    t = this.getDao().update(sif);

                    if(t){
                        sif.setSkuNum(new Integer(restStockNum));
                        sif.setStockStatus("A");
                        sif.setStockId(null);
                        sif.setRequestId(null);
                        sif.setUpdateBy(null);
                        sif.setUpdateDate(null);
                        //sif.setCreateBy(new Long(-1));
                        sif.setCreateDate(new Date());
                        t = this.getDao().insert(sif);
                    }
                }

            }else if(rang < 0 ){	//库存不够分配，拆分request
                tempStat = reqForm.getPartStatus();
                reqForm.setPartStatus("L");
                reqForm.setPartNum(new Integer(restStockNum));
                reqForm.setUpdateBy(new Long(-1));
                reqForm.setUpdateDate(new Date());

                t = this.getDao().update(reqForm);

                if(t){
                    sif.setStockStatus("R");
                    sif.setRequestId(reqForm.getSaleDetailId());
                    sif.setUpdateBy(new Long(-1));
                    sif.setUpdateDate(new Date());
                    //sif.setCreateBy(null);
                    t = this.getDao().update(sif);

                    if(t){
                        reqForm.setPartStatus(tempStat);

                        reqForm.setPartNum(new Integer(-rang));
                        reqForm.setUpdateBy(null);
                        reqForm.setUpdateDate(null);
                        //reqForm.setCreateBy(new Long(-1));

                        if(reqForm.getRootId()==null||reqForm.getRootId().longValue() == 0){
                            reqForm.setRootId(reqForm.getSaleDetailId());
                            reqForm.setSaleDetailId(null);
                        }else{
                            reqForm.setSaleDetailId(null);
                        }
                        t = this.getDao().insert(reqForm);
                    }
                }
                restStockNum = 0;


            }else{	//库存正好分配
                reqForm.setPartStatus("L");
                reqForm.setUpdateBy(new Long(-1));
                reqForm.setUpdateDate(new Date());
                t = this.getDao().update(reqForm);

                if(t){
                    sif.setStockStatus("R");
                    sif.setRequestId(reqForm.getSaleDetailId());
                    sif.setUpdateBy(new Long(-1));
                    sif.setUpdateDate(new Date());
                    //sif.setCreateBy(null);
                    t = this.getDao().update(sif);

                }
                restStockNum= 0;

            }
        }


        return restStockNum;
    }

    public ArrayList getStockList() throws Exception{
        //sale_detail_id in(7,8,9,17,18,20,22);
        ArrayList stockList = (ArrayList)this.getDao().list("from StockInfoForm as sif where sif.stockId in (200,201,202)");

        return stockList;
    }

    public static void main(String[] args) {
        try{

            ReceiveAllocateBo rab=new ReceiveAllocateBo();

            rab.allocate(rab.getStockList());
        }catch(Exception e){
            e.printStackTrace();
        }
    }


}
