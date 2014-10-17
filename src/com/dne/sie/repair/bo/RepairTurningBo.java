package com.dne.sie.repair.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

import com.dne.sie.common.exception.ComException;
import com.dne.sie.common.exception.VersionException;
import com.dne.sie.common.tools.FormNumberBuilder;
import com.dne.sie.common.tools.Operate;
import com.dne.sie.maintenance.bo.MachineToolBo;
import com.dne.sie.maintenance.form.CustomerInfoForm;
import com.dne.sie.maintenance.form.MachineToolForm;
import com.dne.sie.repair.form.RepairManInfoForm;
import com.dne.sie.repair.form.RepairSearchForm;
import com.dne.sie.repair.form.RepairServiceForm;
import com.dne.sie.repair.form.RepairServiceStatusForm;
import com.dne.sie.util.bo.CommBo;

public class RepairTurningBo extends CommBo {
    private static Logger logger = Logger.getLogger(RepairTurningBo.class);

    private static final RepairTurningBo INSTANCE = new RepairTurningBo();

    private RepairTurningBo(){
    }

    public static final RepairTurningBo getInstance() {
        return INSTANCE;
    }

    public List getAtDispatchList(RepairSearchForm rsForm){

        return null;
    }


    /**
     * 维修单录入
     * @param searchForm
     * @return
     * @throws Exception
     */
    public RepairServiceForm addTurningService(RepairSearchForm searchForm) throws ComException,Exception {
        RepairServiceForm rsf = new RepairServiceForm();
        searchForm.setServiceSheetNo(FormNumberBuilder.getNewServiveSheetNo());
        PropertyUtils.copyProperties(rsf, searchForm);

        //设置维修状态
        RepairServiceStatusForm rssf = new RepairServiceStatusForm();
        rssf.setRepairStatus(rsf.getCurrentStatus());
        rssf.setBeginDate(new Date());
        rssf.setCreateBy(rsf.getCreateBy());
        rssf.setRepairServiceForm(rsf);
        rsf.getServiceStatusSet().add(rssf);

        CustomerInfoForm cif = (CustomerInfoForm)this.getDao().findById(CustomerInfoForm.class, searchForm.getCustomerId());
        rsf.setCustomInfoForm(cif);

        this.getDao().insert(rsf);

        //就位登记时，自动将机型机身号信息，同步到机床信息表
        if(MachineToolBo.getInstance().chkSerial(null, rsf.getSerialNo())){
            MachineToolForm mtf = new MachineToolForm();
            mtf.setMachineName(rsf.getServiceSheetNo());
            mtf.setCustomerId(cif.getCustomerId());
            mtf.setCustomerName(cif.getCustomerName());
            mtf.setModelCode(rsf.getModelCode());
            mtf.setSerialNo(rsf.getSerialNo());
            mtf.setWarrantyCardNo(rsf.getWarrantyCardNo());
            mtf.setCreateBy(rsf.getCreateBy());

            if(rsf.getPurchaseDate()!=null){
                mtf.setPurchaseDate(rsf.getPurchaseDate());
            }
            if(rsf.getExtendedWarrantyDate()!=null){
                mtf.setExtendedWarrantyDate(rsf.getExtendedWarrantyDate());
            }
            this.getDao().insert(mtf);

        }


        return rsf;
    }


    /**
     * 维修派工
     * @param searchForm
     * @throws Exception
     */
    public void dispatch(RepairSearchForm searchForm,String repairMan) throws Exception {
        ArrayList al = new ArrayList();
        RepairServiceForm rsf = (RepairServiceForm)this.getDao().findById(RepairServiceForm.class, searchForm.getRepairNo());

        rsf.setDzLinkman(searchForm.getDzLinkman());
        rsf.setDzPhone(searchForm.getDzPhone());
        rsf.setExpectedDuration(searchForm.getExpectedDuration());
        rsf.setDzRemark(searchForm.getDzRemark());
        rsf.setCurrentStatus(searchForm.getCurrentStatus()); //已派工
        rsf.setUpdateBy(searchForm.getUpdateBy());
        rsf.setUpdateDate(searchForm.getUpdateDate());
        rsf.setOperaterId(searchForm.getUpdateBy());
//		if(rsf.getWarrantyType().equals("B") || rsf.getWarrantyType().equals("C")){	//安调,维修
//			RepairSearchForm lastRsf = RepairListBo.getInstance().findById(rsf.getLastRepairNo());
//			lastRsf.setUnCompleteQuickStatus("Y");
//			lastRsf.setUpdateBy(rsf.getUpdateBy());
//			lastRsf.setUpdateDate(rsf.getUpdateDate());
//			al.add(lastRsf);
//		}
        //设置维修状态
        RepairServiceStatusForm rssf = new RepairServiceStatusForm();
        rssf.setRepairStatus(rsf.getCurrentStatus());
        rssf.setBeginDate(new Date());
        rssf.setCreateBy(rsf.getUpdateBy());
        rssf.setRepairServiceForm(rsf);
        rsf.getServiceStatusSet().add(rssf);

        //派工维修员
        if(repairMan!=null && !repairMan.equals("")){
            String[] rows = repairMan.split("@");
            //设置维修员
            for(String row : rows){
                //System.out.println("--row="+row);
                String[] repairManInfo = row.split("#");
                RepairManInfoForm rmif =  new RepairManInfoForm();
                rmif.setRepairNo(rsf.getRepairNo());
                rmif.setRepairMan(Long.parseLong(repairManInfo[0]));
                rmif.setDepartDate(Operate.toSqlDate(repairManInfo[1]));
                if(repairManInfo[2]!=null&&!repairManInfo[2].equals(""))
                    rmif.setWorkingHours(new Integer(repairManInfo[2]));
                if(repairManInfo[3]!=null&&!repairManInfo[3].equals(""))
                    rmif.setTravelFee(new Double(repairManInfo[3]));
                if(repairManInfo[4]!=null&&!repairManInfo[4].equals(""))
                    rmif.setLaborCosts(new Double(repairManInfo[4]));

                if(repairManInfo.length>=6) rmif.setRemark(repairManInfo[5]);
                rmif.setCreateDate(new Date());
                rmif.setCreateBy(rsf.getUpdateBy());

                rmif.setRepairServiceForm(rsf);
                rsf.getRepairManSetInfo().add(rmif);

            }
        }
        al.add(rsf);


        this.getBatchDao().saveOrUpdateBatch(al);

    }

    /**
     * 就位结束操作
     * @param searchForm
     * @throws Exception
     */
    public void jwComplete(RepairSearchForm searchForm,ArrayList<String[]> repairManInfo) throws VersionException,Exception {
        ArrayList<Object[]> al = new ArrayList<Object[]>();
        RepairServiceForm rsf = (RepairServiceForm)this.getDao().findById(RepairServiceForm.class, searchForm.getRepairNo());
        if(rsf.getVersion()!=searchForm.getVersion()){
            throw new VersionException("数据已被其他用户更新过，请重新打开后再提交！");
        }

//		rsf.setActualRepairedDate((Operate.toDate(searchForm.getActualRepairedDateStr())));
        rsf.setJwFoundation(searchForm.getJwFoundation());
        rsf.setJwDoc(searchForm.getJwDoc());
        rsf.setJwDocMissing(searchForm.getJwDocMissing());
        rsf.setTurningDate(searchForm.getTurningDate());
        rsf.setJwServiceCharge(searchForm.getJwServiceCharge());
        rsf.setJwCarfare(searchForm.getJwCarfare());

        rsf.setCurrentStatus(searchForm.getCurrentStatus()); //已派工
        rsf.setUpdateBy(searchForm.getUpdateBy());
        rsf.setUpdateDate(searchForm.getUpdateDate());
        rsf.setActualRepairedDate(searchForm.getUpdateDate());

        //设置维修状态
        RepairServiceStatusForm rssf = new RepairServiceStatusForm();
        rssf.setRepairStatus(rsf.getCurrentStatus());
        rssf.setBeginDate(new Date());
        rssf.setCreateBy(rsf.getUpdateBy());
        rssf.setRepairServiceForm(rsf);
        rsf.getServiceStatusSet().add(rssf);


        String[] travelId = repairManInfo.get(0);
        String[] arrivalDate = repairManInfo.get(1);
        String[] returnDate = repairManInfo.get(2);
        String[] travelFee = repairManInfo.get(3);
        String[] laborCosts = repairManInfo.get(4);
        String[] repairCondition = repairManInfo.get(5);

        Set rmiSet = rsf.getRepairManSetInfo();
        ArrayList rimList = new ArrayList(rmiSet);
        int repairmanNum=0,workhour=0;
        double travelFeeAll=0,laborCostsAll=0;
        //派工维修员
        for(int i=0;i<rimList.size();i++){
            //设置维修员
            RepairManInfoForm rmif = (RepairManInfoForm)rimList.get(i);
            for(int j=0;j<travelId.length;j++){
                if(rmif.getTravelId().longValue() == new Long(travelId[j]).longValue()){
                    rsf.getRepairManSetInfo().remove(rmif);

                    rmif.setArrivalDate(Operate.toSqlDate(arrivalDate[j]));
                    rmif.setReturnDate(Operate.toSqlDate(returnDate[j]));
                    rmif.setWorkingHoursActual(Operate.getSpacingDay(rmif.getArrivalDate(), rmif.getReturnDate()));
                    rmif.setTravelFee(new Double(travelFee[j]));
                    rmif.setLaborCostsActual(new Double(laborCosts[j]));
                    rmif.setRepairCondition(repairCondition[j]);

                    rmif.setUpdateDate(new Date());
                    rmif.setUpdateBy(rsf.getUpdateBy());

                    //rmif.setRepairServiceForm(rsf);

                    rsf.getRepairManSetInfo().add(rmif);

                    repairmanNum++;
                    if(rmif.getWorkingHoursActual() > workhour) workhour = rmif.getWorkingHoursActual();
                    travelFeeAll+=rmif.getTravelFee();
                    laborCostsAll+=rmif.getLaborCostsActual();

                    break;
                }
            }
        }
        al.add(new Object[]{rsf,"u"});


        //创建安调单
        RepairServiceForm atRsf = new RepairServiceForm();
        atRsf.setServiceSheetNo(FormNumberBuilder.getNewServiveSheetNo("turning"));
        atRsf.setLastRepairNo(rsf.getRepairNo());
        atRsf.setOperaterId(rsf.getOperaterId());
        atRsf.setCreateBy(rsf.getUpdateBy());
        atRsf.setCreateDate(new Date());
        atRsf.setCurrentStatus("T");		//准备派工
        atRsf.setRepairProperites("T");		//安调
        atRsf.setWarrantyType("B");			//安调
        atRsf.setModelCode(rsf.getModelCode());
        atRsf.setSerialNo(rsf.getSerialNo());
        atRsf.setPurchaseDate(rsf.getPurchaseDate());
        atRsf.setWarrantyCardNo(rsf.getWarrantyCardNo());
        atRsf.setManufacture(rsf.getManufacture());
        atRsf.setCustomerTrouble(rsf.getCustomerTrouble());
        atRsf.setLinkman(rsf.getLinkman());
        atRsf.setPhone(rsf.getPhone());
        atRsf.setCustomerVisitDate(rsf.getTurningDate());
        atRsf.setTurningDate(rsf.getTurningDate());

        RepairServiceStatusForm atRssf = new RepairServiceStatusForm();
        atRssf.setRepairStatus(atRsf.getCurrentStatus());
        atRssf.setBeginDate(atRsf.getCreateDate());
        atRssf.setCreateBy(rsf.getCreateBy());
        atRssf.setRepairServiceForm(atRsf);
        atRsf.getServiceStatusSet().add(atRssf);

        atRsf.setCustomInfoForm(rsf.getCustomInfoForm());

        al.add(new Object[]{atRsf,"i"});

        this.getBatchDao().allDMLBatch(al);

    }



    /**
     * 安调结束操作
     * @param searchForm
     * @throws Exception
     */
    public void atComplete(RepairSearchForm searchForm,ArrayList<String[]> repairManInfo) throws VersionException,Exception {
        ArrayList<Object[]> al = new ArrayList<Object[]>();
        RepairServiceForm rsf = (RepairServiceForm)this.getDao().findById(RepairServiceForm.class, searchForm.getRepairNo());
        if(rsf.getVersion()!=searchForm.getVersion()){
            throw new VersionException("数据已被其他用户更新过，请重新打开后再提交！");
        }

//		rsf.setActualRepairedDate((Operate.toDate(searchForm.getActualRepairedDateStr())));
        rsf.setAtPlc(searchForm.getAtPlc());
        rsf.setAtMissParts(searchForm.getAtMissParts());
        rsf.setAtCircuit(searchForm.getAtCircuit());
        rsf.setAtOthers(searchForm.getAtOthers());
        rsf.setAtPrecision(searchForm.getAtPrecision());
        rsf.setAtUHW0(searchForm.getAtUHW0());
        rsf.setAtSHW0(searchForm.getAtSHW0());
        rsf.setAtUHW90(searchForm.getAtUHW90());
        rsf.setAtSHW90(searchForm.getAtSHW90());
        rsf.setAtUHW180(searchForm.getAtUHW180());
        rsf.setAtSHW180(searchForm.getAtSHW180());
        rsf.setAtUHW270(searchForm.getAtUHW270());
        rsf.setAtSHW270(searchForm.getAtSHW270());
        rsf.setAtTrain(searchForm.getAtTrain());
        rsf.setAtSign(searchForm.getAtSign());
        rsf.setAtUnsignRemark(searchForm.getAtUnsignRemark());
        rsf.setActualDuration(searchForm.getActualDuration());


        rsf.setCurrentStatus(searchForm.getCurrentStatus()); //已派工
        rsf.setUpdateBy(searchForm.getUpdateBy());
        rsf.setUpdateDate(searchForm.getUpdateDate());
        rsf.setActualRepairedDate(searchForm.getUpdateDate());

        //设置维修状态
        RepairServiceStatusForm rssf = new RepairServiceStatusForm();
        rssf.setRepairStatus(rsf.getCurrentStatus());
        rssf.setBeginDate(new Date());
        rssf.setCreateBy(rsf.getUpdateBy());
        rssf.setRepairServiceForm(rsf);
        rsf.getServiceStatusSet().add(rssf);


        String[] travelId = repairManInfo.get(0);
        String[] arrivalDate = repairManInfo.get(1);
        String[] returnDate = repairManInfo.get(2);
        String[] travelFee = repairManInfo.get(3);
        String[] laborCosts = repairManInfo.get(4);
        String[] repairCondition = repairManInfo.get(5);

        Set rmiSet = rsf.getRepairManSetInfo();
        ArrayList rimList = new ArrayList(rmiSet);
        int repairmanNum=0,workhour=0;
        double travelFeeAll=0,laborCostsAll=0;
        //派工维修员
        for(int i=0;i<rimList.size();i++){
            //设置维修员
            RepairManInfoForm rmif = (RepairManInfoForm)rimList.get(i);
            for(int j=0;j<travelId.length;j++){
                if(rmif.getTravelId().longValue() == new Long(travelId[j]).longValue()){
                    rsf.getRepairManSetInfo().remove(rmif);

                    rmif.setArrivalDate(Operate.toSqlDate(arrivalDate[j]));
                    rmif.setReturnDate(Operate.toSqlDate(returnDate[j]));
                    rmif.setWorkingHoursActual(Operate.getSpacingDay(rmif.getArrivalDate(), rmif.getReturnDate()));
                    rmif.setTravelFee(new Double(travelFee[j]));
                    rmif.setLaborCostsActual(new Double(laborCosts[j]));
                    rmif.setRepairCondition(repairCondition[j]);

                    rmif.setUpdateDate(new Date());
                    rmif.setUpdateBy(rsf.getUpdateBy());

                    //rmif.setRepairServiceForm(rsf);

                    rsf.getRepairManSetInfo().add(rmif);

                    repairmanNum++;
                    if(rmif.getWorkingHoursActual() > workhour) workhour = rmif.getWorkingHoursActual();
                    travelFeeAll+=rmif.getTravelFee();
                    laborCostsAll+=rmif.getLaborCostsActual();

                    break;
                }
            }
        }
        al.add(new Object[]{rsf,"u"});


        //创建安调单
        RepairServiceForm atRsf = new RepairServiceForm();
        atRsf.setServiceSheetNo(FormNumberBuilder.getNewServiveSheetNo("turning"));
        atRsf.setLastRepairNo(rsf.getRepairNo());
        atRsf.setOperaterId(rsf.getOperaterId());
        atRsf.setCreateBy(rsf.getUpdateBy());
        atRsf.setCreateDate(new Date());
        atRsf.setCurrentStatus("T");		//准备派工
        atRsf.setRepairProperites("T");		//安调
        atRsf.setWarrantyType("C");			//检测
        atRsf.setModelCode(rsf.getModelCode());
        atRsf.setSerialNo(rsf.getSerialNo());
        atRsf.setPurchaseDate(rsf.getPurchaseDate());
        atRsf.setWarrantyCardNo(rsf.getWarrantyCardNo());
        atRsf.setManufacture(rsf.getManufacture());
        atRsf.setCustomerTrouble(rsf.getCustomerTrouble());
        atRsf.setLinkman(rsf.getLinkman());
        atRsf.setPhone(rsf.getPhone());
        atRsf.setCustomerVisitDate(rsf.getTurningDate());
        atRsf.setTurningDate(rsf.getTurningDate());

        RepairServiceStatusForm atRssf = new RepairServiceStatusForm();
        atRssf.setRepairStatus(atRsf.getCurrentStatus());
        atRssf.setBeginDate(atRsf.getCreateDate());
        atRssf.setCreateBy(rsf.getCreateBy());
        atRssf.setRepairServiceForm(atRsf);
        atRsf.getServiceStatusSet().add(atRssf);

        atRsf.setCustomInfoForm(rsf.getCustomInfoForm());

        al.add(new Object[]{atRsf,"i"});

        this.getBatchDao().allDMLBatch(al);

    }

    public void atNotComplete(RepairSearchForm searchForm,ArrayList<String[]> repairManInfo) throws VersionException,Exception {
        RepairServiceForm rsf = (RepairServiceForm)this.getDao().findById(RepairServiceForm.class, searchForm.getRepairNo());
        if(rsf.getVersion()!=searchForm.getVersion()){
            throw new VersionException("数据已被其他用户更新过，请重新打开后再提交！");
        }

//		rsf.setActualRepairedDate((Operate.toDate(searchForm.getActualRepairedDateStr())));
        rsf.setAtPlc(searchForm.getAtPlc());
        rsf.setAtMissParts(searchForm.getAtMissParts());
        rsf.setAtCircuit(searchForm.getAtCircuit());
        rsf.setAtOthers(searchForm.getAtOthers());
        rsf.setAtPrecision(searchForm.getAtPrecision());
        rsf.setAtUHW0(searchForm.getAtUHW0());
        rsf.setAtSHW0(searchForm.getAtSHW0());
        rsf.setAtUHW90(searchForm.getAtUHW90());
        rsf.setAtSHW90(searchForm.getAtSHW90());
        rsf.setAtUHW180(searchForm.getAtUHW180());
        rsf.setAtSHW180(searchForm.getAtSHW180());
        rsf.setAtUHW270(searchForm.getAtUHW270());
        rsf.setAtSHW270(searchForm.getAtSHW270());
        rsf.setAtTrain(searchForm.getAtTrain());
        rsf.setAtSign(searchForm.getAtSign());
        rsf.setAtUnsignRemark(searchForm.getAtUnsignRemark());
        rsf.setActualDuration(searchForm.getActualDuration());


        rsf.setCurrentStatus(searchForm.getCurrentStatus()); //已派工
        rsf.setUpdateBy(searchForm.getUpdateBy());
        rsf.setUpdateDate(searchForm.getUpdateDate());
        rsf.setActualRepairedDate(searchForm.getUpdateDate());

        //设置维修状态
        RepairServiceStatusForm rssf = new RepairServiceStatusForm();
        rssf.setRepairStatus(rsf.getCurrentStatus());
        rssf.setBeginDate(new Date());
        rssf.setCreateBy(rsf.getUpdateBy());
        rssf.setRepairServiceForm(rsf);
        rsf.getServiceStatusSet().add(rssf);


        String[] travelId = repairManInfo.get(0);
        String[] arrivalDate = repairManInfo.get(1);
        String[] returnDate = repairManInfo.get(2);
        String[] travelFee = repairManInfo.get(3);
        String[] laborCosts = repairManInfo.get(4);
        String[] repairCondition = repairManInfo.get(5);

        if(travelId!=null && travelId.length>0){
            Set rmiSet = rsf.getRepairManSetInfo();
            ArrayList rimList = new ArrayList(rmiSet);
            int repairmanNum=0,workhour=0;
            double travelFeeAll=0,laborCostsAll=0;
            //派工维修员
            for(int i=0;i<rimList.size();i++){
                //设置维修员
                RepairManInfoForm rmif = (RepairManInfoForm)rimList.get(i);
                for(int j=0;j<travelId.length;j++){
                    if(rmif.getTravelId().longValue() == new Long(travelId[j]).longValue()){
                        if(arrivalDate[j]==null || returnDate[j]==null || travelFee[j]==null || laborCosts[j]==null || repairCondition[j]==null
                                || arrivalDate[j].isEmpty() || returnDate[j].isEmpty() || travelFee[j].isEmpty() || laborCosts[j].isEmpty() || repairCondition[j].isEmpty()){
                            continue;
                        }

                        rsf.getRepairManSetInfo().remove(rmif);

                        rmif.setArrivalDate(Operate.toSqlDate(arrivalDate[j]));
                        rmif.setReturnDate(Operate.toSqlDate(returnDate[j]));
                        rmif.setWorkingHoursActual(Operate.getSpacingDay(rmif.getArrivalDate(), rmif.getReturnDate()));
                        rmif.setTravelFee(new Double(travelFee[j]));
                        rmif.setLaborCostsActual(new Double(laborCosts[j]));
                        rmif.setRepairCondition(repairCondition[j]);

                        rmif.setUpdateDate(new Date());
                        rmif.setUpdateBy(rsf.getUpdateBy());

                        //rmif.setRepairServiceForm(rsf);

                        rsf.getRepairManSetInfo().add(rmif);

                        repairmanNum++;
                        if(rmif.getWorkingHoursActual() > workhour) workhour = rmif.getWorkingHoursActual();
                        travelFeeAll+=rmif.getTravelFee();
                        laborCostsAll+=rmif.getLaborCostsActual();

                        break;
                    }
                }
            }
        }
        this.getDao().update(rsf);

    }


    /**
     * 检测结束操作
     * @param searchForm
     * @throws Exception
     */
    public void jcComplete(RepairSearchForm searchForm,ArrayList<String[]> repairManInfo) throws VersionException,Exception {
        ArrayList<Object[]> al = new ArrayList<Object[]>();
        RepairServiceForm rsf = (RepairServiceForm)this.getDao().findById(RepairServiceForm.class, searchForm.getRepairNo());
        if(rsf.getVersion()!=searchForm.getVersion()){
            throw new VersionException("数据已被其他用户更新过，请重新打开后再提交！");
        }

//		rsf.setActualRepairedDate((Operate.toDate(searchForm.getActualRepairedDateStr())));
        rsf.setCrReason(searchForm.getCrReason());


        rsf.setCurrentStatus(searchForm.getCurrentStatus()); //已派工
        rsf.setUpdateBy(searchForm.getUpdateBy());
        rsf.setUpdateDate(searchForm.getUpdateDate());
        rsf.setActualRepairedDate(searchForm.getUpdateDate());

        //设置维修状态
        RepairServiceStatusForm rssf = new RepairServiceStatusForm();
        rssf.setRepairStatus(rsf.getCurrentStatus());
        rssf.setBeginDate(new Date());
        rssf.setCreateBy(rsf.getUpdateBy());
        rssf.setRepairServiceForm(rsf);
        rsf.getServiceStatusSet().add(rssf);


        String[] travelId = repairManInfo.get(0);
        String[] arrivalDate = repairManInfo.get(1);
        String[] returnDate = repairManInfo.get(2);
        String[] travelFee = repairManInfo.get(3);
        String[] laborCosts = repairManInfo.get(4);
        String[] repairCondition = repairManInfo.get(5);

        Set rmiSet = rsf.getRepairManSetInfo();
        ArrayList rimList = new ArrayList(rmiSet);
        int repairmanNum=0,workhour=0;
        double travelFeeAll=0,laborCostsAll=0;
        //派工维修员
        for(int i=0;i<rimList.size();i++){
            //设置维修员
            RepairManInfoForm rmif = (RepairManInfoForm)rimList.get(i);
            for(int j=0;j<travelId.length;j++){
                if(rmif.getTravelId().longValue() == new Long(travelId[j]).longValue()){
                    rsf.getRepairManSetInfo().remove(rmif);

                    rmif.setArrivalDate(Operate.toSqlDate(arrivalDate[j]));
                    rmif.setReturnDate(Operate.toSqlDate(returnDate[j]));
                    rmif.setWorkingHoursActual(Operate.getSpacingDay(rmif.getArrivalDate(), rmif.getReturnDate()));
                    rmif.setTravelFee(new Double(travelFee[j]));
                    rmif.setLaborCostsActual(new Double(laborCosts[j]));
                    rmif.setRepairCondition(repairCondition[j]);

                    rmif.setUpdateDate(new Date());
                    rmif.setUpdateBy(rsf.getUpdateBy());

                    //rmif.setRepairServiceForm(rsf);

                    rsf.getRepairManSetInfo().add(rmif);

                    repairmanNum++;
                    if(rmif.getWorkingHoursActual() > workhour) workhour = rmif.getWorkingHoursActual();
                    travelFeeAll+=rmif.getTravelFee();
                    laborCostsAll+=rmif.getLaborCostsActual();

                    break;
                }
            }
        }
        al.add(new Object[]{rsf,"u"});



        this.getBatchDao().allDMLBatch(al);

    }


}
