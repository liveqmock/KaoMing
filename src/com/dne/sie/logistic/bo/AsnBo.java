package com.dne.sie.logistic.bo;

//Java 基础类
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;

import com.dne.sie.common.tools.CommonSearch;
import com.dne.sie.common.tools.DicInit;
import com.dne.sie.common.tools.FormNumberBuilder;
import com.dne.sie.common.tools.Operate;
import com.dne.sie.logistic.form.AsnForm;
import com.dne.sie.logistic.queryBean.AsnApproveQuery;
import com.dne.sie.logistic.queryBean.AsnQuery;
import com.dne.sie.reception.bo.SaleInfoBo;
import com.dne.sie.reception.form.SaleDetailForm;
import com.dne.sie.reception.form.SaleInfoForm;
import com.dne.sie.util.bo.CommBo;
import com.dne.sie.util.query.QueryParameter;


public class AsnBo extends CommBo {
    private static Logger logger = Logger.getLogger(AsnBo.class);

    private static final AsnBo INSTANCE = new AsnBo();

    private AsnBo(){
    }

    public static final AsnBo getInstance() {
        return INSTANCE;
    }


    /**
     * 根据id查询AsnForm信息
     * @param String 记录pk
     * @return SaleInfoForm
     */
    public AsnForm findById(String id) throws Exception {
        AsnForm  af = (AsnForm)this.getDao().findById(AsnForm.class,id);

        return af;
    }

    /**
     * 销售单列表查询拼装
     * @param SaleInfoForm 查询条件
     * @return ArrayList 查询结果
     */
    public ArrayList asnReadyList(SaleInfoForm dept) throws Exception {

        ArrayList alData = new ArrayList();
        AsnQuery dq = new AsnQuery(dept);

        List dataList=dq.doListQuery(dept.getFromPage(),dept.getToPage());

        int count=dq.doCountQuery();
        CommonSearch cs = CommonSearch.getInstance();

        for (int i=0;i<dataList.size();i++) {
            String[] data = new String[12];
            Object[] obj = (Object[])dataList.get(i);

            data[0] = obj[0].toString()+DicInit.SPLIT1+obj[9];
            data[1] = obj[1]==null?"":obj[1].toString();
            data[2] = obj[2]==null?"":obj[2].toString();
            data[3] = obj[3]==null?"":obj[3].toString();
            data[4] = obj[4]==null?"":obj[4].toString();
            data[5] = obj[5]==null?"":obj[5].toString();
            data[6] = DicInit.getSystemName("PAY_STATUS", (String)obj[6]);
            data[7] = DicInit.getSystemName("BILLING_STATUS", (String)obj[7]);
            data[8] = cs.findUserNameByUserId((Long)obj[8]);
            data[9] = obj[10]==null?"":obj[10].toString();
            data[10] = DicInit.getSystemName("SALE_STATUS", (String)obj[11]);
            data[11] = obj[12]==null?"":obj[12].toString();

            alData.add(data);
        }
        alData.add(0,count+"");

        return alData;
    }


    /**
     * 查询客户信息
     * @param customerId
     * @return Object[]
     */
    public Object[] getAsnInfo(String custId) throws Exception {
        Object[] custInfo=null;
        String strHql="select cif.address,cif.linkman,cif.customerName,cif.phone " +
                "from CustomerInfoForm as cif where cif.customerId = :customerId";

        ArrayList<QueryParameter> paramList = new ArrayList<QueryParameter>();
        QueryParameter param = new QueryParameter();
        param.setName("customerId");
        param.setValue(custId);
        param.setHbType(Hibernate.STRING);
        paramList.add(param);

        List payList = this.getDao().parameterQuery(strHql, paramList);
        if(payList!=null&&payList.size()>0){
            custInfo=(Object[])payList.get(0);
        }

        return custInfo;
    }

    /**
     * 发货确认
     * @param SaleInfoForm
     * @return tag
     */
    public int saleConsign(String chkIds,AsnForm af) throws Exception{
        int tag=-1;

        String versionId = Operate.toVersionData(chkIds);
        String strHql="from SaleDetailForm as sdf where CONCAT(sdf.saleDetailId,',',sdf.version) " +
                "in ("+versionId+") order by sdf.saleNo,sdf.stuffNo ";
        List sdList=this.listVersion(strHql, chkIds.split(",").length);
        if(sdList!=null&&sdList.size()>0){
            SaleInfoForm sif=null;
            String payStatus=null,billingStatus=null;
            String asnStatus="T";
            ArrayList al = new ArrayList();
            ArrayList<String> saleNoList=new ArrayList<String>();
            ArrayList<Long> partsIdList=new ArrayList<Long>();
            String asnNo = FormNumberBuilder.getNewAsnFormNumber(af.getCustomerId());

            for(int i=0;i<sdList.size();i++){
                SaleDetailForm sdf = (SaleDetailForm)sdList.get(i);
                if(!saleNoList.contains(sdf.getSaleNo())){
                    saleNoList.add(sdf.getSaleNo());
                    sif=(SaleInfoForm)this.getDao().findById(SaleInfoForm.class, sdf.getSaleNo());
                }
                partsIdList.add(sdf.getPartsId());

                sdf.setAsnNo(asnNo);
                sdf.setUpdateBy(af.getCreateBy());
                sdf.setUpdateDate(new Date());


                double totalQuteWithTax = sif.getTotalQuteWithTax();
                if(sdf.getPartStatus().equals("Q") ){	//发货审批同意
                    sdf.setPartStatus("T");	//已发货
                }else{
                    Float totalPay=sif.getTotalPay()==null?0F:+sif.getTotalPay();

                    System.out.println("----"+i+"---sif.getTotalPay()="+totalPay+"---totalQuteWithTax="+totalQuteWithTax);
                    //if(sif.getTotalPay()==null||sif.getTotalPay()<sif.getTotalQuote()){	//未达账or有尾款
                    if(totalPay<totalQuteWithTax){	//有尾款
                        sdf.setPartStatus("N");	//发货审批中
                        asnStatus="N";
                    }else{
                        sdf.setPartStatus("T");	//已发货
                    }
                }
                Object[] obj1={sdf,"u"};
                al.add(obj1);

                if(payStatus==null||payStatus.compareTo(sif.getPayStatus())<0){
                    payStatus=sif.getPayStatus();
                }
                if(billingStatus==null||billingStatus.compareTo(sif.getBillingStatus())<0){
                    billingStatus=sif.getBillingStatus();
                }
            }

            af.setAsnStatus(asnStatus);
            af.setAsnNo(asnNo);
            af.setPayStatus(payStatus);
            af.setBillingStatus(billingStatus);

            Object[] obj2={af,"i"};
            al.add(obj2);

            if(this.getBatchDao().allDMLBatch(al)){
                //更新销售大单状态、维修状态
                SaleInfoBo.getInstance().renewSaleForConsign(saleNoList,partsIdList);
                tag=1;
            }

        }

        return tag;
    }


    /**
     * 发货列表
     * @param SaleInfoForm
     * @return tag
     */
    public ArrayList asnList(AsnForm af) throws Exception {

        ArrayList alData = new ArrayList();
        AsnApproveQuery aaq = new AsnApproveQuery(af);
        List dataList=aaq.doListQuery(af.getFromPage(),af.getToPage());

        int count=aaq.doCountQuery();
        CommonSearch cs = CommonSearch.getInstance();
        for (int i=0;i<dataList.size();i++) {
            String[] data = new String[11];
            AsnForm asn = (AsnForm)dataList.get(i);
            data[0]=asn.getAsnNo();
            data[1]=asn.getCustomerName();
            data[2]=asn.getShippingAddress();
            data[3]=DicInit.getSystemName("PAY_STATUS", asn.getPayStatus());
            data[4]=DicInit.getSystemName("BILLING_STATUS", asn.getBillingStatus());
            data[5]=asn.getShippingRemark();
            data[6]=cs.findUserNameByUserId(asn.getCreateBy());
            data[7]=asn.getCreateDate().toLocaleString();
            data[8]=asn.getApproveRemark();
            data[9] = DicInit.getSystemName("SALE_STATUS", asn.getAsnStatus());
            data[10] = DicInit.getSystemName("ASN_TYPE", asn.getAsnType());
            alData.add(data);
        }
        alData.add(0,count+"");


        return alData;
    }



    /**
     * 发货审批确认
     * @param SaleInfoForm
     * @return tag
     */
    public int consignApprove(String asnNos,String approveFlag,Long userId,String remark) throws Exception{
        int tag=-1;

        String status=null;
        if(approveFlag.equals("Y")){
            status="Q";		//发货同意
            remark="同意:"+remark;
        }else if(approveFlag.equals("N")){
            status="O";		//发货不同意
            remark="不同意:"+remark;
        }
        CommonSearch cs = CommonSearch.getInstance();
        remark+=" "+Operate.trimDate(new Date())+" "+cs.findUserNameByUserId(userId);

        asnNos=asnNos.replaceAll(",", "','");
        String strHql1="update SaleDetailForm as sdf set sdf.partStatus='"+status+"'" +
                " where sdf.asnNo in ('"+asnNos+"')";
        String strHql2="update AsnForm as af set af.asnStatus='"+status+"'," +
                "af.approveRemark='"+remark+"' where af.asnNo in ('"+asnNos+"')";
        ArrayList udList=new ArrayList();
        udList.add(strHql1);
        udList.add(strHql2);

        if(this.getBatchDao().excuteBatch(udList)){
            List al=this.getDao().list("select distinct sdf.saleNo from SaleDetailForm as sdf where sdf.asnNo in ('"+asnNos+"')");
            if(al!=null&&al.size()>0){
                SaleInfoBo.getInstance().renewSaleStatus(al);
            }
            tag=1;
        }


        return tag;
    }

//	/**
//	 * 发货再次提交确认
//	 * @param SaleInfoForm
//	 * @return tag
//	 */
//	public int consignAgain(String asnNos,String flag,Long userId,String remark) throws Exception{
//		int tag=-1;
//		
//		String status=null;
//		String strHql2=null;
//		asnNos=asnNos.replaceAll(",", "','");
//		
//		if(flag.equals("Q")){
//			status="T";		//已发货
//			strHql2="update AsnForm as af set af.asnStatus='"+status+"' " +
//				" where af.asnNo in ('"+asnNos+"')";
//		}else if(flag.equals("O")){
//			status="N";		//发货审批中
//			remark="再次提交:"+remark;
//			CommonSearch cs = CommonSearch.getInstance();
//			remark += " "+Operate.trimDate(new Date())+" "+cs.findUserNameByUserId(userId);
//			
//			strHql2="update AsnForm as af set af.asnStatus='"+status+"'," +
//				"af.shippingRemark='"+remark+"' where af.asnNo in ('"+asnNos+"')";
//		}
//		String strHql1="update SaleDetailForm as sdf set sdf.partStatus='"+status+"'" +
//			" where sdf.asnNo in ('"+asnNos+"')";
//		
//		ArrayList udList=new ArrayList();
//		udList.add(strHql1);
//		udList.add(strHql2);
//		
//		if(this.getBatchDao().excuteBatch(udList)){
//			List al=this.getDao().list("select distinct sdf.saleNo from SaleDetailForm as sdf where sdf.asnNo in ('"+asnNos+"')");
//			if(al!=null&&al.size()>0){
//				SaleInfoBo.getInstance().renewSaleStatus(al);
//			}
//			tag=1;
//		}
//		
//		
//		return tag;
//	}

    /**
     * 发货零件明细
     * @param SaleInfoForm
     * @return tag
     */
    public ArrayList<String[]> saleDetailList(String asnNo) throws Exception{
        String strHql="from SaleDetailForm as sdf where sdf.asnNo = :asnNo";

        ArrayList<QueryParameter> paramList = new ArrayList<QueryParameter>();
        QueryParameter param = new QueryParameter();
        param.setName("asnNo");
        param.setValue(asnNo);
        param.setHbType(Hibernate.STRING);
        paramList.add(param);

        List al = this.getDao().parameterQuery(strHql, paramList);
        ArrayList<String[]> dList = null;
        if(al!=null&&al.size()>0){
            dList = new ArrayList<String[]>();
            for(int i=0;i<al.size();i++){
                SaleDetailForm sdf = (SaleDetailForm)al.get(i);
                String[] temp=new String[10];
                temp[0]=sdf.getSaleNo();
                temp[1]=sdf.getStuffNo();
                temp[2]=sdf.getSkuCode();
                temp[3]=sdf.getModelCode();
                temp[4]=sdf.getModelSerialNo();
                temp[5]=sdf.getPartNum()+"";
                temp[6]=sdf.getCostReal()==null?"":sdf.getCostReal().toString();
                temp[7]=sdf.getSalePerPrice()==null?"":sdf.getSalePerPrice().toString();
                temp[8]=sdf.getProfitPlan()==null?"":sdf.getProfitPlan().toString();
                temp[9]=DicInit.getSystemName("SALE_STATUS", sdf.getPartStatus());

                dList.add(temp);
            }
        }

        return dList;
    }


}
