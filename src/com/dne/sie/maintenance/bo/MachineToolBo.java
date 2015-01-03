package com.dne.sie.maintenance.bo;

import java.util.ArrayList;
import java.util.List;

import com.dne.sie.common.tools.Operate;
import com.dne.sie.maintenance.form.MachineToolForm;
import com.dne.sie.maintenance.queryBean.MachineToolQuery;
import com.dne.sie.util.bo.CommBo;

public class MachineToolBo extends CommBo {


    private static final MachineToolBo INSTANCE = new MachineToolBo();

    private MachineToolBo(){
    }

    public static final MachineToolBo getInstance() {
        return INSTANCE;
    }

    /**
     * 客户表信息查询。
     * @param part MachineToolForm
     * @return ArrayList  返回数据是由TsSystemCodeForm属性组合成的String数组集合。
     */
    public ArrayList list(MachineToolForm part) {
        List dataList = null;
        ArrayList alData = new ArrayList();
        MachineToolQuery uq = new MachineToolQuery(part);
        int count=0;
        try {
            dataList=uq.doListQuery(part.getFromPage(),part.getToPage());
            count=uq.doCountQuery();
            MachineToolForm pif=null;

            for (int i=0;i<dataList.size();i++) {
                String[] data = new String[9];
                pif = (MachineToolForm)dataList.get(i);

                data[0] = pif.getMachineId().toString();
                data[1] = pif.getMachineName();
                data[2] = pif.getCustomerId();
                data[3] = pif.getCustomerName();
                data[4] = pif.getModelCode();
                data[5] = pif.getSerialNo();
                data[6] = pif.getWarrantyCardNo();
                data[7] = Operate.trimDate(pif.getPurchaseDate());
                data[8] = Operate.trimDate(pif.getExtendedWarrantyDate());


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
     * @return MachineToolForm
     */
    public MachineToolForm find(Long id) throws Exception{
        MachineToolForm mtf = (MachineToolForm)this.getDao().findById(MachineToolForm.class,id);
        mtf.setPurchaseDateStr(Operate.trimDate(mtf.getPurchaseDate()));
        mtf.setExtendedWarrantyDateStr(Operate.trimDate(mtf.getExtendedWarrantyDate()));
        return  mtf;

    }


    /**
     * 添加单条客户表信息
     * @param uf  MachineToolForm
     * @return int 1为成功，-1为失败。
     */
    public int add(MachineToolForm uf) throws Exception{
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
     * @param uf  MachineToolForm
     * @return int 1为成功，-1为失败
     */
    public int modify(MachineToolForm uf) throws Exception{
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
     * @param ids
     * @return int 1为成功，-1为失败
     */
    public int delete(String ids) throws Exception{
        int tag=-1;
        try{
            tag=this.getDao().execute("update from MachineToolForm as sc " +
                    "set sc.delFlag=1 where sc.machineId in ('"+ids.replaceAll(",", "','")+"')");
        } catch(Exception e) {
            e.printStackTrace();
        }
        return tag;
    }


    /**
     * 校验机身编码是否存在
     * @param machineId,serialNo
     * @return true 不存在
     */
    public boolean chkSerial(String machineId,String serialNo) {
        boolean retBoo = false;

        try {
            String where="";
            if(machineId!=null&&!machineId.isEmpty()){
                where = " and uf.machineId !="+machineId;
            }
            Object obj=this.getDao().uniqueResult("select count(uf) from MachineToolForm as uf " +
                    "where uf.serialNo=? "+where,serialNo);
            int count=((Long)obj).intValue();

            if(count==0) retBoo=true;

        } catch(Exception e) {
            e.printStackTrace();
        }
        return retBoo;
    }

    public List getSerialListByName(String serialNo)  throws Exception{
        String strHql="select modelCode,serialNo,warrantyCardNo,purchaseDate,extendedWarrantyDate,customerId,customerName " +
                "from MachineToolForm as mt where mt.serialNo like ?";

        return this.getDao().list(strHql,"%"+serialNo+"%");
    }


}
