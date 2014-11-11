package com.dne.sie.maintenance.bo;

import com.dne.sie.maintenance.form.CustomerInfoForm;
import com.dne.sie.maintenance.queryBean.CustomerInfoQuery;
import com.dne.sie.util.bo.CommBo;

import java.util.ArrayList;
import java.util.List;


/**
 * 客户表信息表
 *@version 1.1.5.6
 */

public class CustomerInfoBo extends CommBo {

    private static final CustomerInfoBo INSTANCE = new CustomerInfoBo();

    private CustomerInfoBo(){
    }

    public static final CustomerInfoBo getInstance() {
        return INSTANCE;
    }

    /**
     * 客户表信息查询。
     * @param part CustomerInfoForm
     * @return ArrayList  返回数据是由TsSystemCodeForm属性组合成的String数组集合。
     */
    public ArrayList list(CustomerInfoForm part) {
        List dataList = null;
        ArrayList alData = new ArrayList();
        CustomerInfoQuery uq = new CustomerInfoQuery(part);
        int count=0;
        try {
            dataList=uq.doListQuery(part.getFromPage(),part.getToPage());
            count=uq.doCountQuery();
            CustomerInfoForm pif=null;

            for (int i=0;i<dataList.size();i++) {
                String[] data = new String[9];
                pif = (CustomerInfoForm)dataList.get(i);
                data[0] = pif.getCustomerId();
                data[1] = pif.getCustomerName();
                data[2] = pif.getLinkman();
                data[3] = pif.getPhone();
                data[4] = pif.getFax();
                data[5] = pif.getAddress();
                data[6] = pif.getPostCode();
                data[7] = pif.getProvinceName();
                data[8] = pif.getCityName();

                alData.add(data);
            }
            alData.add(0,count+"");
        } catch(Exception e) {
            e.printStackTrace();
        }
        return alData;
    }


    /**
     * 查询单条客户表信息
     * @param id  String   id为客户表信息表的主键
     * @return CustomerInfoForm
     */
    public CustomerInfoForm find(String id) throws Exception{
        return  (CustomerInfoForm)this.getDao().findById(CustomerInfoForm.class,id);

    }


    /**
     * 添加单条客户表信息
     * @param uf  CustomerInfoForm
     * @return int 1为成功，-1为失败。
     */
    public int add(CustomerInfoForm uf) throws Exception{
        int tag=-1;
        try{
            if (this.getDao().insert(uf)) {
                tag=1;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return tag;
    }


    /**
     * 修改客户表信息
     * @param uf  CustomerInfoForm
     * @return int 1为成功，-1为失败
     */
    public int modify(CustomerInfoForm uf) throws Exception{
        int tag=-1;
        try{
            if (this.getDao().update(uf)) {
                tag = 1;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return tag;
    }

    /**
     * 逻辑删除客户表信息
     * @param ids  客户表
     * @return int 1为成功，-1为失败
     */
    public int delete(String ids) throws Exception{
        int tag=-1;
        try{
            tag=this.getDao().execute("update from CustomerInfoForm as sc " +
                    "set sc.delFlag=1 where sc.customerId in ('"+ids.replaceAll(",", "','")+"')");
        } catch(Exception e) {
            e.printStackTrace();
        }
        return tag;
    }

    /**
     * 校验客户id(PK)是否存在
     * @param id 输入的用户id
     * @return 该用户id是否可以输入
     */
    public boolean chkCustId(String id) {
        boolean retBoo = false;

        try {
            Object obj=this.getDao().uniqueResult("select count(uf) from CustomerInfoForm as uf " +
                    "where uf.customerId='"+id.toUpperCase()+"'");
            int count=((Long)obj).intValue();

            if(count==0) retBoo=true;

        } catch(Exception e) {
            e.printStackTrace();
        }
        return retBoo;
    }


    /**
     * 查询台湾高明询价地址信息
     * @param
     * @return 高明信息
     */
    public String[] getKmInfo(String custId) {
        String[] kmInfo=new String[5];

        try {
            Object[] obj=(Object[])this.getDao().uniqueResult("select ci.customerName," +
                    "ci.fax,ci.linkman,ci.bank,ci.bankAccount from CustomerInfoForm as ci where ci.customerId='"+custId+"'");
            if(obj!=null){
                kmInfo[0]=obj[0]==null?"":(String)obj[0];
                kmInfo[1]=obj[1]==null?"":(String)obj[1];
                kmInfo[2]=obj[2]==null?"":(String)obj[2];
                kmInfo[3]=obj[3]==null?"":(String)obj[3];
                kmInfo[4]=obj[4]==null?"":(String)obj[4];
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return kmInfo;
    }


    /**
     * 查询客户信息
     * @param
     * @return List
     */
    public List getCustomerListByName(String custName) throws Exception{

        String strHql="select cif.customerId,cif.customerName,cif.linkman,cif.phone,cif.cityName,cif.fax," +
                "cif.address,cif.mobile,cif.provinceName,remark,email " +
                "from CustomerInfoForm as cif where cif.delFlag=0 and cif.customerName like ?";

        List aList = (ArrayList)this.getDao().list(strHql,"%"+custName+"%");


        return aList;
    }


}
