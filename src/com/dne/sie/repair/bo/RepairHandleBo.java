package com.dne.sie.repair.bo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

import com.dne.sie.common.exception.ComException;
import com.dne.sie.common.exception.VersionException;
import com.dne.sie.common.tools.AtomRoleCheck;
import com.dne.sie.common.tools.FormNumberBuilder;
import com.dne.sie.common.tools.Operate;
import com.dne.sie.maintenance.form.AttachedInfoForm;
import com.dne.sie.maintenance.form.CustomerInfoForm;
import com.dne.sie.reception.form.SaleDetailForm;
import com.dne.sie.reception.form.SaleInfoForm;
import com.dne.sie.repair.form.RepairFeeInfoForm;
import com.dne.sie.repair.form.RepairIrisInfoForm;
import com.dne.sie.repair.form.RepairManInfoForm;
import com.dne.sie.repair.form.RepairPartForm;
import com.dne.sie.repair.form.RepairSearchForm;
import com.dne.sie.repair.form.RepairServiceForm;
import com.dne.sie.repair.form.RepairServiceStatusForm;
import com.dne.sie.stock.bo.ReqAllocateBo;
import com.dne.sie.stock.bo.StockInfoListBo;
import com.dne.sie.stock.form.StockToolsInfoForm;
import com.dne.sie.util.bo.CommBo;
import com.dne.sie.util.form.CommForm;

public class RepairHandleBo extends CommBo {
	private static Logger logger = Logger.getLogger(RepairHandleBo.class);
	
	private static final String[] reportsPath=Operate.getReportPath();

	private static final RepairHandleBo INSTANCE = new RepairHandleBo();
		
	private RepairHandleBo(){
	}
	
	public static final RepairHandleBo getInstance() {
	   return INSTANCE;
	}
	
	public RepairSearchForm findById(Long repairNo) throws Exception {
		
		return (RepairSearchForm)this.getDao().findById(RepairSearchForm.class, repairNo);
		
	}

	public RepairSearchForm findBySheetNo(String serviceSheetNo) throws Exception {
		
		return (RepairSearchForm)this.getDao().uniqueResult("from RepairSearchForm sf where sf.serviceSheetNo=?", serviceSheetNo);
		
	}
	
	/**
	 * ά�޵�¼��
	 * @param searchForm
	 * @return
	 * @throws Exception
	 */
	public RepairServiceForm addService(RepairSearchForm searchForm) throws ComException,Exception {
		RepairServiceForm rsf = new RepairServiceForm();
		searchForm.setServiceSheetNo(FormNumberBuilder.getNewServiveSheetNo());
		PropertyUtils.copyProperties(rsf, searchForm);
		
		if(!"T".equals(searchForm.getRr90())&&!"N".equals(searchForm.getRr90())){
			rsf.setRr90(this.getRr90Status(searchForm));
		}
		//����ά��״̬
		RepairServiceStatusForm rssf = new RepairServiceStatusForm();
		rssf.setRepairStatus("A");
		rssf.setBeginDate(new Date());
		rssf.setCreateBy(rsf.getCreateBy());
		rssf.setRepairServiceForm(rsf);
		rsf.getServiceStatusSet().add(rssf);
		
		CustomerInfoForm cif = (CustomerInfoForm)this.getDao().findById(CustomerInfoForm.class, searchForm.getCustomerId());
		rsf.setCustomInfoForm(cif);
		
		this.getDao().insert(rsf);
		
//		ArrayList al = new ArrayList();
//		//�й������۵�������FK
//		if(searchForm.getSaleNo()!=null && !searchForm.getSaleNo().equals("")){
//			String[] saleNos = searchForm.getSaleNo().split(",");
//			for(String saleNo : saleNos){
//				SaleInfoForm sif  = (SaleInfoForm)this.getDao().findById(SaleInfoForm.class, saleNo);
//				sif.setRepairNo(rsf.getRepairNo());
//				
//				al.add(sif);
//			}
//			this.getBatchDao().updateBatch(al);
//		}
		
		return rsf;
	}
	
	/**
	 * ���ﱣ������
	 * @param searchForm
	 * @throws Exception
	 */
	public void repairOperate(RepairSearchForm searchForm) throws VersionException,Exception {
		ArrayList<Object[]> al = new ArrayList<Object[]>();
		RepairServiceForm rsf = (RepairServiceForm)this.getDao().findById(RepairServiceForm.class, searchForm.getRepairNo());
		if(rsf.getVersion()!=searchForm.getVersion()){
			throw new VersionException("�����ѱ������û����¹��������´򿪺����ύ��");
		}
		rsf = (RepairServiceForm)this.copyBeans(rsf, searchForm);
	
		rsf.setUpdateDate(new Date());
		rsf.setUpdateBy(searchForm.getUpdateBy());
		//����Ա
		rsf.setOperaterId(searchForm.getUpdateBy());
		
		if(searchForm.getTempActualOnsiteDate()!=null&&!searchForm.getTempActualOnsiteDate().equals(""))
			rsf.setActualOnsiteDate(Operate.toDate(searchForm.getTempActualOnsiteDate()));
		if(searchForm.getTempActualRepairedDate()!=null&&!searchForm.getTempActualRepairedDate().equals(""))
			rsf.setActualRepairedDate(Operate.toDate(searchForm.getTempActualRepairedDate()));
		
		
		
		if(searchForm.getFlag()==null||!searchForm.getFlag().endsWith("Save")){
			//����ά��״̬
			RepairServiceStatusForm rssf = new RepairServiceStatusForm();
			rssf.setRepairStatus(searchForm.getCurrentStatus());
			rssf.setBeginDate(new Date());
			rssf.setCreateBy(rsf.getUpdateBy());
			rssf.setRepairServiceForm(rsf);
			rsf.getServiceStatusSet().add(rssf);
			
		}
		if("dzSave".equals(searchForm.getFlag())){
			if(searchForm.getRepairmanNum()!=null || searchForm.getWorkingHours()!=null 
					|| (searchForm.getTicketsAllCosts()!=null&&searchForm.getTicketsAllCosts()!=0 )
					|| (searchForm.getLaborCosts()!=null)&&searchForm.getLaborCosts()!=0){
				List feeList = this.getDao().list("from RepairFeeInfoForm sfi where sfi.repairNo=? and feeType='P'",rsf.getRepairNo());
				if(feeList!=null && feeList.size()>0){
					RepairFeeInfoForm sfi = (RepairFeeInfoForm)feeList.get(0);
					sfi.setRepairmanNum(rsf.getRepairmanNum());
					sfi.setWorkingHours(rsf.getWorkingHours());
					sfi.setTicketsAllCosts(rsf.getTicketsAllCosts());
					sfi.setLaborCosts(rsf.getLaborCosts());
					sfi.setUpdateBy(rsf.getCreateBy());
					sfi.setUpdateDate(new Date());
					
					al.add(new Object[]{sfi,"u"});
				}else{
					RepairFeeInfoForm sfi = new RepairFeeInfoForm();
					sfi.setRepairNo(rsf.getRepairNo());
					sfi.setWarrantyType(rsf.getWarrantyType());
					sfi.setFeeType("P");	//plan
					sfi.setRepairmanNum(rsf.getRepairmanNum());
					sfi.setWorkingHours(rsf.getWorkingHours());
					sfi.setTicketsAllCosts(rsf.getTicketsAllCosts());
					sfi.setLaborCosts(rsf.getLaborCosts());
					sfi.setCreateBy(rsf.getCreateBy());
					sfi.setCreateDate(new Date());
					
					al.add(new Object[]{sfi,"i"});
				}
			}
		}else if("cpSave".equals(searchForm.getFlag())){
			if(searchForm.getActualOnsiteDateStr()!=null)
				rsf.setActualOnsiteDate((Operate.toDate(searchForm.getActualOnsiteDateStr())));
			if(searchForm.getActualRepairedDateStr()!=null) 
				rsf.setActualRepairedDate((Operate.toDate(searchForm.getActualRepairedDateStr())));
			
			if(searchForm.getIrisIds()!=null&&!searchForm.getIrisIds().isEmpty()){
				String delIris = "delete from RepairIrisInfoForm as ii where ii.repairNo="+rsf.getRepairNo();
				al.add(new Object[]{delIris,"e"});
				
				String[] irisId = searchForm.getIrisIds().split(",");
				//{ "iristree": [{"14": "p1111", "15":"p222", "49": "1", "50": "8", "62": "5", "63": "2" }]}
				JSONObject jsonObject = JSONObject.fromObject(searchForm.getIrisValues());
				JSONArray ja = jsonObject.getJSONArray("iristree");
				JSONObject irisValues = (JSONObject)ja.get(0);
				 Map<String, Object> irisMap = (Map) irisValues;
				 
				 
				for(String id : irisId){
					RepairIrisInfoForm iif = new RepairIrisInfoForm();
					iif.setRepairNo(rsf.getRepairNo());
					iif.setIrisCodeId(new Long(id));
					
					iif.setIrisContent(irisMap.get(id)==null?"":irisMap.get(id).toString());
					
					iif.setCreateBy(searchForm.getUpdateBy());
					iif.setCreateDate(searchForm.getUpdateDate());
					al.add(new Object[]{iif,"i"});
				}
			}
		}
		
//		if(repairMan!=null && !repairMan.equals("")){
//			String[] rows = repairMan.split("@");
//			//����ά��Ա
//			for(String row : rows){
//				System.out.println("--row="+row);
//				String[] repairManInfo = row.split("#");
//				RepairManInfoForm rmif =  new RepairManInfoForm();
//				rmif.setRepairNo(rsf.getRepairNo());
//				rmif.setRepairMan(Long.parseLong(repairManInfo[0]));
//				rmif.setDepartDate(Operate.toSqlDate(repairManInfo[1]));
//				if(repairManInfo[2]!=null&&!repairManInfo[2].equals(""))
//					rmif.setArrivalDate(Operate.toSqlDate(repairManInfo[2]));
//				if(repairManInfo[3]!=null&&!repairManInfo[3].equals(""))
//					rmif.setReturnDate(Operate.toSqlDate(repairManInfo[3]));
//				if(repairManInfo[4]!=null&&!repairManInfo[4].equals(""))
//					rmif.setTravelFee(Double.parseDouble(repairManInfo[4]));
//				if(repairManInfo[5]!=null&&!repairManInfo[5].equals(""))
//					rmif.setRepairCondition(repairManInfo[5]);
//				
//				if(repairManInfo.length>=7) rmif.setRemark(repairManInfo[6]);
//				rmif.setCreateDate(new Date());
//				rmif.setCreateBy(rsf.getUpdateBy());
//				
//				rmif.setRepairServiceForm(rsf);
//				rsf.getRepairManSetInfo().add(rmif);
//			
//			}
//		}
		al.add(new Object[]{rsf,"s"});
		
		this.getBatchDao().allDMLBatch(al);
		
	}
	
	
	/**
	 * ����ת���۲���
	 * 1.������Ӧ���۴�
	 * 2.ά�����ת�����������������С��
	 * 3.����Ԥ���ɹ���Ϣ������ά�޳ɱ�
	 * @param searchForm
	 * @throws Exception
	 */
	public void transferSale(RepairSearchForm searchForm) throws Exception,ComException {
		ArrayList al = new ArrayList();
		RepairServiceForm rsf = (RepairServiceForm)this.getDao().findById(RepairServiceForm.class, searchForm.getRepairNo());
		String warrantyType = rsf.getWarrantyType();
		rsf = (RepairServiceForm)this.copyBeans(rsf, searchForm);
	
		rsf.setUpdateDate(new Date());
		//����Ա
		rsf.setOperaterId(searchForm.getUpdateBy());

		//����ά��״̬
		RepairServiceStatusForm rssf = new RepairServiceStatusForm();
		rssf.setRepairStatus(searchForm.getCurrentStatus());
		rssf.setBeginDate(new Date());
		rssf.setCreateBy(rsf.getUpdateBy());
		rssf.setRepairServiceForm(rsf);
		rsf.getServiceStatusSet().add(rssf);
		
		//�������۴�
		SaleInfoForm sif = new SaleInfoForm();
		String saleNo = FormNumberBuilder.getNewSaleFormNumber(rsf.getCustomInfoForm().getCustomerId());
		sif.setSaleNo(saleNo);
		sif.setServiceSheetNo(rsf.getServiceSheetNo());
		sif.setWarrantyType(warrantyType);
		sif.setCreateDate(new Date());
		sif.setCreateBy(searchForm.getUpdateBy());
		sif.setSaleStatus("A");	//��̨ѯ����
		sif.setCustomerId(rsf.getCustomInfoForm().getCustomerId());
		sif.setCustomerName(rsf.getCustomInfoForm().getCustomerName());
		sif.setRepairNo(rsf.getRepairNo());
		
		rsf.setSaleNo(saleNo);
		
		List<RepairPartForm> partsList = this.findRepairPartList(rsf.getRepairNo(), "W");
		//��������С��
		int count=0;
		if(partsList!=null&&!partsList.isEmpty()){
			for (RepairPartForm rpf : partsList) {
				SaleDetailForm psf = new SaleDetailForm();
				
				psf.setSaleNo(saleNo);
				psf.setOrderType("R");
				psf.setWarrantyType(rpf.getWarrantyType());
				psf.setStuffNo(rpf.getStuffNo());
				psf.setSkuCodeT(rpf.getSkuCode());
				psf.setSkuUnit(rpf.getSkuUnit());
				psf.setModelCode(rsf.getModelCode());
				psf.setModelSerialNo(rsf.getSerialNo());
				psf.setPartStatus("A");
				psf.setPartNum(rpf.getApplyQty());
				count+= psf.getPartNum();
				psf.setCreateBy(rsf.getUpdateBy());
				psf.setCreateDate(new Date());
				psf.setPartsId(rpf.getPartsId());
				
				al.add(new Object[]{psf,"i"});
				
				//�޸�ά�����״̬
				rpf.setRepairPartStatus("S"); //ת����
				rpf.setUpdateBy(rsf.getUpdateBy());
				rpf.setUpdateDate(new Date());
				al.add(new Object[]{rpf,"u"});
			}
		}else{
			sif.setSaleStatus("D");	//���ۺ�����
		}
		sif.setPartNum(count);
		al.add(0,new Object[]{sif,"i"});
		
		
		
		//����Ԥ���ɹ���Ϣ������ά�޳ɱ�
		if(searchForm.getRepairmanNum()!=null && searchForm.getWorkingHours()!=null 
			&& searchForm.getTicketsAllCosts()!=null && searchForm.getLaborCosts()!=null){
				//����Ԥ���ɹ���Ϣ
				RepairFeeInfoForm sfi = this.findPlanRepairFee(rsf.getRepairNo());
				Double[] costs = getRepairCosts(searchForm.getRepairmanNum(), searchForm.getWorkingHours(),
						searchForm.getTicketsAllCosts(), searchForm.getLaborCosts());
				if(sfi!=null){
					sfi.setRepairmanNum(rsf.getRepairmanNum());
					sfi.setWorkingHours(rsf.getWorkingHours());
					sfi.setTicketsAllCosts(rsf.getTicketsAllCosts());
					sfi.setLaborCosts(rsf.getLaborCosts());
					sfi.setTravelCosts(costs[0]);
					sfi.setTaxes(costs[1]);
					sfi.setTotalCost(costs[2]);
					sfi.setUpdateBy(rsf.getCreateBy());
					sfi.setUpdateDate(new Date());
					
					al.add(new Object[]{sfi,"u"});
				}else{
					sfi = new RepairFeeInfoForm();
					sfi.setRepairNo(rsf.getRepairNo());
					sfi.setWarrantyType(warrantyType);
					sfi.setFeeType("P");	//plan
					sfi.setRepairmanNum(rsf.getRepairmanNum());
					sfi.setWorkingHours(rsf.getWorkingHours());
					sfi.setTicketsAllCosts(rsf.getTicketsAllCosts());
					sfi.setLaborCosts(rsf.getLaborCosts());
					sfi.setTravelCosts(costs[0]);
					sfi.setTaxes(costs[1]);
					sfi.setTotalCost(costs[2]);
					sfi.setCreateBy(rsf.getCreateBy());
					sfi.setCreateDate(new Date());
					
					al.add(new Object[]{sfi,"i"});
				}
				
		}else{
			throw new ComException("Ԥ���ɹ���Ϣ������");
		}
		
		al.add(new Object[]{rsf,"u"});
		
		this.getBatchDao().allDMLBatch(al);
		
	}
	
	/**
	 * ά���ɹ�
	 * @param searchForm
	 * @throws Exception
	 */
	public void dispatch(RepairSearchForm searchForm,String repairMan) throws Exception {
		ArrayList al = new ArrayList();
		RepairServiceForm rsf = (RepairServiceForm)this.getDao().findById(RepairServiceForm.class, searchForm.getRepairNo());
			
		rsf.setCurrentStatus(searchForm.getCurrentStatus()); //���ɹ�
		rsf.setUpdateBy(searchForm.getUpdateBy());
		rsf.setUpdateDate(searchForm.getUpdateDate());

		//����ά��״̬
		RepairServiceStatusForm rssf = new RepairServiceStatusForm();
		rssf.setRepairStatus(rsf.getCurrentStatus());
		rssf.setBeginDate(new Date());
		rssf.setCreateBy(rsf.getUpdateBy());
		rssf.setRepairServiceForm(rsf);
		rsf.getServiceStatusSet().add(rssf);
		
		//�ɹ�ά��Ա
		if(repairMan!=null && !repairMan.equals("")){
			String[] rows = repairMan.split("@");
			//����ά��Ա
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
		
		//ά�����״̬�޸�
		List partList = this.getDao().list("from RepairPartForm rp where rp.repairNo=? and rp.repairPartStatus ='T' " +
				"and rp.repairPartType='W' and rp.warrantyType='I' ",rsf.getRepairNo());
		if(partList!=null&&!partList.isEmpty()){
			for(int i=0;i<partList.size();i++){
				RepairPartForm rpf = (RepairPartForm)partList.get(i);
				rpf.setRepairPartStatus("B");	//����������
				rpf.setUpdateBy(rsf.getUpdateBy());
				rpf.setUpdateDate(rsf.getUpdateDate());
				al.add(rpf);
			}
		}
		
		this.getBatchDao().saveOrUpdateBatch(al);
		
	}
	
	

	/**
	 * ά�޷�������
	 * @param searchForm
	 * @throws Exception
	 */
	public void returnEnd(RepairSearchForm searchForm,ArrayList<String[]> repairManInfo) throws Exception {
		ArrayList al = new ArrayList();
		RepairServiceForm rsf = (RepairServiceForm)this.getDao().findById(RepairServiceForm.class, searchForm.getRepairNo());
			
		rsf.setCurrentStatus(searchForm.getCurrentStatus()); //���ɹ�
		rsf.setUpdateBy(searchForm.getUpdateBy());
		rsf.setUpdateDate(searchForm.getUpdateDate());

		//����ά��״̬
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
		//�ɹ�ά��Ա
		for(int i=0;i<rimList.size();i++){
			//����ά��Ա
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
		al.add(rsf);
		
		
		//����ʵ��ά�޷���
		RepairFeeInfoForm sfi = new RepairFeeInfoForm();
		sfi.setRepairNo(rsf.getRepairNo());
		sfi.setWarrantyType(rsf.getWarrantyType());
		sfi.setFeeType("A");	//ʵ��
		sfi.setRepairmanNum(repairmanNum);
		sfi.setWorkingHours(workhour);
		sfi.setTicketsAllCosts(travelFeeAll);
		sfi.setLaborCosts(laborCostsAll);
		Double[] costs = getRepairCosts(sfi.getRepairmanNum(), sfi.getWorkingHours(),
				sfi.getTicketsAllCosts(), sfi.getLaborCosts());
		
		sfi.setTravelCosts(costs[0]);
		sfi.setTaxes(costs[1]);
		sfi.setTotalCost(costs[2]);
		sfi.setRepairQuote(this.getPlanQuote(rsf.getRepairNo()));
		sfi.setRepairProfit((sfi.getRepairQuote()==null?0:sfi.getRepairQuote()) - (sfi.getTotalCost()==null?0:sfi.getTotalCost()));
		sfi.setCreateBy(rsf.getUpdateBy());
		sfi.setCreateDate(rsf.getUpdateDate());
		
		al.add(sfi);
		
		this.getBatchDao().saveOrUpdateBatch(al);
		
	}
	
	public Double getPlanQuote(Long repairNo) throws Exception{
		return (Double)this.getDao().uniqueResult("select repairQuote from RepairFeeInfoForm where feeType='P' and repairNo=?",repairNo);
	}

	/**
	 * �����������
	 * @param searchForm
	 * @throws Exception
	 */
	public void repairEnd(RepairSearchForm searchForm) throws VersionException,Exception {
		ArrayList<Object[]> al = new ArrayList<Object[]>();
		RepairServiceForm rsf = (RepairServiceForm)this.getDao().findById(RepairServiceForm.class, searchForm.getRepairNo());
		if(rsf.getVersion()!=searchForm.getVersion()){
			throw new VersionException("�����ѱ������û����¹��������´򿪺����ύ��");
		}
		rsf.setActualOnsiteDate((Operate.toDate(searchForm.getActualOnsiteDateStr())));
		rsf.setActualRepairedDate((Operate.toDate(searchForm.getActualRepairedDateStr())));
		rsf.setRepairContent(searchForm.getRepairContent());
		rsf.setLeaveProblem(searchForm.getLeaveProblem());
		rsf.setTobeMatter(searchForm.getTobeMatter());
		
		rsf.setUnCompleteQuickStatus(searchForm.getUnCompleteQuickStatus());
		
		rsf.setCurrentStatus(searchForm.getCurrentStatus()); //���ɹ�
		rsf.setUpdateBy(searchForm.getUpdateBy());
		rsf.setUpdateDate(searchForm.getUpdateDate());

		//����ά��״̬
		RepairServiceStatusForm rssf = new RepairServiceStatusForm();
		rssf.setRepairStatus(rsf.getCurrentStatus());
		rssf.setBeginDate(new Date());
		rssf.setCreateBy(rsf.getUpdateBy());
		rssf.setRepairServiceForm(rsf);
		rsf.getServiceStatusSet().add(rssf);
		
		al.add(new Object[]{rsf,"u"});
		
		if(searchForm.getIrisIds()!=null&&!searchForm.getIrisIds().isEmpty()){
			String delIris = "delete from RepairIrisInfoForm as ii where ii.repairNo="+rsf.getRepairNo();
			al.add(new Object[]{delIris,"e"});
			
			String[] irisId = searchForm.getIrisIds().split(",");
			//{ "iristree": [{"14": "p1111", "15":"p222", "49": "1", "50": "8", "62": "5", "63": "2" }]}
			JSONObject jsonObject = JSONObject.fromObject(searchForm.getIrisValues());
			JSONArray ja = jsonObject.getJSONArray("iristree");
			JSONObject irisValues = (JSONObject)ja.get(0);
			 Map<String, Object> irisMap = (Map) irisValues;
			
			for(String id : irisId){
				RepairIrisInfoForm iif = new RepairIrisInfoForm();
				iif.setRepairNo(rsf.getRepairNo());
				iif.setIrisCodeId(new Long(id));
				
				iif.setIrisContent(irisMap.get(id)==null?"":irisMap.get(id).toString());
				
				iif.setCreateBy(searchForm.getUpdateBy());
				iif.setCreateDate(searchForm.getUpdateDate());
				al.add(new Object[]{iif,"i"});
			}
		}
		
		this.getBatchDao().allDMLBatch(al);
		
	}
	
	public String checkDispatchPart(Long repairNo) throws Exception{
		String partHql="select count(*) from RepairPartForm rp where rp.repairNo=? and rp.repairPartStatus ='L' and rp.repairPartType='X'";
		String toolHql="select count(*) from RepairPartForm rp where rp.repairNo=? and rp.repairPartStatus ='L' and rp.repairPartType='T'";
		Long pcount = (Long)this.getDao().uniqueResult(partHql,repairNo);
		
		if(pcount>0){
			return "part";
		}
		
		Long tcount = (Long)this.getDao().uniqueResult(toolHql,repairNo);
		if(tcount>0){
			return "tool";
		}else{
			return "succ";
		}
		
	}
	

	public String checkReturnPart(Long repairNo) throws Exception{
		String brokenHql=  "select count(*) from RepairPartForm rp where rp.repairNo=? and rp.repairPartStatus !='R' and rp.repairPartType='W' and rp.warrantyType='I'";
		String loanPartHql="select count(*) from RepairPartForm rp where rp.repairNo=? and rp.repairPartStatus not in('R','S') and rp.repairPartType='X'";
		String toolHql=    "select count(*) from RepairPartForm rp where rp.repairNo=? and rp.repairPartStatus !='R' and rp.repairPartType='T'";
		Long bcount = (Long)this.getDao().uniqueResult(brokenHql,repairNo);
		
		if(bcount>0){
			return "broken";
		}

		Long pcount = (Long)this.getDao().uniqueResult(loanPartHql,repairNo);
		if(pcount>0){
			return "loanPart";
		}
		
		Long tcount = (Long)this.getDao().uniqueResult(toolHql,repairNo);
		if(tcount>0){
			return "tool";
		}else{
			return "succ";
		}
		
	}
	
	
	public static Double[] getRepairCosts(Integer repairmanNum,Integer workingHours,
			Double ticketsAllCosts,Double laborCosts) throws Exception{
		
		Double[] costs = new Double[3];
		//���÷� = ���� * ��ʱ  * 250
		costs[0] = Operate.roundD(repairmanNum * workingHours * new Double(reportsPath[3]), 2);
		//˰�� = �����÷�+����Ʊ+�˹��ѣ�* 0.17
		costs[1] = Operate.roundD( (costs[0] + ticketsAllCosts + laborCosts)*0.17 , 2);
		//�ܳɱ� = ���÷�+����Ʊ+�˹��� +˰��
		costs[2] = Operate.roundD( costs[0] + ticketsAllCosts + laborCosts + costs[1], 2);
		
		return costs;
	}
	
	/**
	 * ���ݸ���ID��ά�޵��Ÿ��¸�����Ϣ
	 * @param attacheIds String[] ����ID�б�
	 * @param sheetNo Long ά�޵���
	 * @param updateBy Long ������
	 * @return boolean true �ɹ� False ʧ��
	 */
	public boolean updateAttacheByAttacheIdsAndSheetNo(String[] attacheIds,Long sheetNo,Long updateBy){
		boolean result = false;
		try{
			ArrayList al = new ArrayList();
			if(attacheIds != null){
				for(int i=0;i<attacheIds.length;i++){
					AttachedInfoForm aif = (AttachedInfoForm)this.getDao().findById(AttachedInfoForm.class,new Long(attacheIds[i]));
					aif.setForeignId(sheetNo);
					aif.setUpdateBy(updateBy);
					aif.setUpdateDate(new Date());
					al.add(aif);
				}				
			}
			result = this.getBatchDao().updateBatch(al);
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	

    public CommForm copyBeans(CommForm target, CommForm original) throws Exception{
//    	logger.info("NpcInterface: -----copyBeans---originalInfo="+original);
    	Field[] fds = original.getClass().getDeclaredFields();
		for(Field fd : fds){
			String fieldName = fd.getName();
			Method originalGetMethod=original.getClass().getMethod(getMethodGetName(fieldName));
			Object originalObj = originalGetMethod.invoke(original, null);
			//�ǿ����ݲ���set
			if(originalObj != null ){
				try{
					Class targetType = target.getClass().getDeclaredField(fieldName).getType();
					Method targetSetMethod=target.getClass().getMethod(getMethodSetName(fieldName),targetType);
					targetSetMethod.invoke(target,originalObj);
//					System.out.println(fieldName+"---"+originalObj);
				}catch(NoSuchFieldException fe){}
				
			}
			
		}
		return target;
    }
    
    protected static String getMethodGetName(String fieldName){
		return "get" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
	}
    protected static String getMethodSetName(String fieldName){
		return "set" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
	}
    private Object convertDataType(Class type,Object columnValue) throws Exception {
		Object field = null;
		
		if(Integer.class.equals(type)){
			field = new Integer(columnValue.toString());
		}else if(Long.class.equals(type)){
			field = new Long(columnValue.toString());
		}else if(Double.class.equals(type)){
			field = new Double(columnValue.toString());
		}else if(Date.class.equals(type)){
			field = formatDate(columnValue.toString());
		}else{
			field = columnValue;
		}
			
		return field;
	}
    private Date formatDate(String date) throws Exception{
    	
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.parse(date);
    	
    }
    
    /**
     * ȡ��ά�޵�
     * @param strRepairNos
     * @param userId
     * @return
     * @throws Exception
     */
    public int cancelRepair(String strRepairNos,Long userId) throws Exception{
    	int tag=-1;
    	
    	List<RepairServiceForm> repairList = this.getDao().list("from RepairServiceForm sf where sf.repairNo in ("+strRepairNos+")");
    	if(repairList==null){
    		return -1;
    	}
    	ArrayList<RepairServiceForm> updateList = new ArrayList<RepairServiceForm>();
    	for(RepairServiceForm rsf : repairList){
    		rsf.setCurrentStatus("C");	//ȡ��
    		rsf.setDelFlag(1L);
    		rsf.setUpdateDate(new Date());
    		rsf.setUpdateBy(userId);
    		
    		//����ά��״̬
    		RepairServiceStatusForm rssf = new RepairServiceStatusForm();
    		rssf.setRepairServiceForm(rsf);
    		rssf.setRepairStatus(rsf.getCurrentStatus());
    		rssf.setBeginDate(new Date());
    		rssf.setCreateBy(rsf.getUpdateBy());
    		rsf.getServiceStatusSet().add(rssf);
    		
    		updateList.add(rsf);
    		
    	}
    	this.getBatchDao().saveOrUpdateBatch(updateList);
    	tag=1;
    	
    	return tag;
    }
    
    /**
     * ���۵�initҳ�棬��ѯ������ά�޵��� ������������С��͡����ɹ�����
     * @param serviceSheetNo
     * @return
     * @throws Exception
     */
    public List<Object[]> mapSaleList(String serviceSheetNo) throws Exception{
    	
    	return this.getDao().list("select sf.repairNo,sf.serviceSheetNo,cif.customerId,cif.customerName,cif.linkman," +
			"cif.phone,cif.cityName,cif.fax,cif.address,cif.mobile,cif.provinceName,cif.remark,cif.email,sf.modelCode " +
			"from RepairSearchForm sf, CustomerInfoForm as cif " +
			"where cif.customerId=sf.customerId and sf.serviceSheetNo like ? " +
			"and (sf.saleNo is null or sf.saleNo='')  and sf.currentStatus in ('S','D')",serviceSheetNo+"%");
    	

		
    }
    
    
    /**
	 * �����У����������Ϣ
	 * @param rpfList �����Ϣ�б�
	 * @return �ɹ����ز���ɹ��������Ϣ�б�ʧ�ܷ���null
	 */
	public List<RepairPartForm> insertPartInfo(List<RepairPartForm> rpfList) {
		List<RepairPartForm> returnFormList = null;
		try {
			boolean t = this.getBatchDao().insertBatch(rpfList);
			if(t){
				returnFormList = rpfList;
				
			
				for(int i=0;i<returnFormList.size();i++){
					RepairPartForm rpf = (RepairPartForm)returnFormList.get(i);
					Long partsId = rpf.getPartsId();
					
					rpf = this.findById4RepairPart(partsId);
					
					returnFormList.set(i,rpf);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return returnFormList;	   	
	}
    

	/**
	 * �����У�ɾ�������Ϣ
	 * @param rpf �����Ϣ
	 * @return true��ʾɾ���ɹ���false��ʾɾ��ʧ��
	 */
	public boolean deletePartInfo(RepairPartForm rpf) throws Exception{
		boolean ret = false;
		
		RepairPartForm delRpf = this.findById4RepairPart(rpf.getPartsId());
		//versionУ��
		if(delRpf.getRepairPartStatus().equals("A") && delRpf.getRepairPartType().equals("W")){
			this.getDao().delete(delRpf);
			ret = true;
		}
		
		return ret;	   	
	}
	
	
	/**
	 * ά�޵���ʱ��Я���������
	 * @param rpf �����Ϣ
	 * @return �ɹ����������Ϣ��ʧ�ܷ���null
	 */
	public RepairPartForm submitLoanPart(RepairPartForm rpf) {
		
		try{
			Long stockNum=StockInfoListBo.getInstance().getAvailableStockNum(rpf.getStuffNo());
			//�п��
			if(stockNum >= rpf.getApplyQty()){
				boolean t = this.getDao().insert(rpf);
				
				if(t){
					if(new ReqAllocateBo().allocateLoan(rpf,stockNum)){
						return this.findById4RepairPart(rpf.getPartsId());
					}else{
						this.getDao().delete(rpf);
					}
				}
			}
			return null;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public RepairPartForm submitLoanTool(RepairPartForm rpf) {
		
		try{
			Long stockNum=StockInfoListBo.getInstance().getAvailableStockToolsNum(rpf.getStuffNo());
			//�п��
			if(stockNum >= rpf.getApplyQty()){
				boolean t = this.getDao().insert(rpf);
				
				if(t){
					if(new ReqAllocateBo().allocateLoan(rpf,stockNum)){
						return this.findById4RepairPart(rpf.getPartsId());
					}else{
						this.getDao().delete(rpf);
					}
				}
			}
			return null;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	/**
	 * �ж������Ϣ��ǰ�汾��Ϣ�Ƿ�����ƶ��İ汾���÷�������ȷ�������û�б��������Ȳ���
	 * @param partsId ���ID
	 * @param version �����İ汾��
	 * @return true ���ϣ�false������
	 * @throws VersionException �׳��汾��ͬ�쳣
	 * @throws Exception �����Ĵ���
	 */
	public boolean checkPartVersion(Long partsId,String version) throws VersionException,Exception{
		boolean booRet=false;
		
		String strHql="select count(*) from RepairPartForm as rpf where rpf.partsId=? and rpf.version=?" ;
		
		Object obj=this.getDao().uniqueResult(strHql,partsId,version);
		int count=((Long) obj).intValue();
		if(count==1){
			booRet=true;
		}else{
			booRet=false;
			throw new VersionException("��Ϣ�ѱ����������޸ģ�");
			
		}
			
		return booRet;
	}
	
	
	/**
	 * �����У�ȡ��Я�������������������
	 * @param rpf �����Ϣ
	 * @return ����ȡ�������Ϣ
	 */
	public boolean  cancelLoanPart(RepairPartForm rpf) {
		boolean  flag=false;
		ArrayList alData=new ArrayList();
		try {
			Long userId = rpf.getUpdateBy(); 
			
			rpf = this.findById4RepairPart(rpf.getPartsId());
			if(rpf != null){
				
				Object[] objrpf={rpf,"d"};
				alData.add(objrpf);
				
				//ֻȡ��Я��������� skuType='L'
				List<StockToolsInfoForm> sifList=(List<StockToolsInfoForm>)this.getDao().list("from StockToolsInfoForm as sif " +
						"where sif.stockStatus='R' and sif.requestId=?",rpf.getPartsId());
				if(sifList!=null){
					for(StockToolsInfoForm sif : sifList){
					
						//��ԭ������״̬Ϊ����
						sif.setStockStatus("A");
						sif.setRequestId(null);
						sif.setUpdateBy(userId);
						sif.setUpdateDate(new Date());
						Object[] sifObj={sif,"u"};
						alData.add(sifObj);
					
					}
				}
				flag=this.getBatchDao().allDMLBatch(alData);
				
				

			}
			
			
		} catch(Exception e) {
			flag=false;
			e.printStackTrace();
		}
		return flag;	   	
	}
	
	/**
	 * Я�����ת����
	 * ����ά�����״̬�����Զ��������۴󵥺�С��
	 * Ӧ�տ�,��������ϸ��������,��������
	 * @param rpf �����Ϣ
	 * @return ����ȡ�������Ϣ
	 */
	public boolean  transferLoanPart(RepairPartForm rpf) {
		boolean  flag=false;
		ArrayList<Object[]> alData=new ArrayList<Object[]>();
		try {
			Long userId = rpf.getUpdateBy(); 
			
			RepairPartForm rpfUd = this.findById4RepairPart(rpf.getPartsId());
			if(rpfUd != null){
				rpfUd.setRepairPartStatus("S");			//ת����
				rpfUd.setUpdateBy(rpf.getUpdateBy());
				rpfUd.setUpdateDate(rpf.getUpdateDate());
			
				alData.add(new Object[]{rpfUd,"u"});
			
				//�������۴�TD_SALES_INFO
				RepairSearchForm serviceForm = (RepairSearchForm)this.getDao().findById(RepairSearchForm.class, rpfUd.getRepairNo());
				
				
				
				//��Ӧͬһά�޵��ģ��ѷ��������۵���Я��ת���ۿ��Ժϲ�
				List sifList = this.getDao().list("from SaleInfoForm as sif where sif.repairNo=? and sif.saleStatus='T' ",rpfUd.getRepairNo());
				//���۵����
				SaleInfoForm sif = null;
				if(sifList==null||sifList.isEmpty()){
					sif = new SaleInfoForm();
					
					sif.setSaleNo(FormNumberBuilder.getNewSaleFormNumber(serviceForm.getCustomerId()));
					sif.setRepairNo(serviceForm.getRepairNo());
					//sif.setServiceSheetNo(serviceForm.getServiceSheetNo());
					sif.setWarrantyType(serviceForm.getWarrantyType());
					
					sif.setSaleStatus("T");		//�ѷ���
					sif.setCustomerId(serviceForm.getCustomerId());
					sif.setCustomerName(serviceForm.getCustomerName());
					sif.setModelCode(serviceForm.getModelCode());
					sif.setPartNum(rpfUd.getApplyQty());
					sif.setCreateDate(new Date());
					sif.setCreateBy(userId);
					
					alData.add(0,new Object[]{sif,"i"});
					
				}else{
					sif = (SaleInfoForm)sifList.get(0);
					sif.setSaleStatus("T");		//�ѷ���
					sif.setPartNum(sif.getPartNum() + rpfUd.getApplyQty());
					
					sif.setUpdateDate(new Date());
					sif.setUpdateBy(userId);
					
					alData.add(new Object[]{sif,"u"});
				}

				//Update Service form
				if(serviceForm!=null){
					serviceForm.setSaleNo(sif.getSaleNo());
					serviceForm.setUpdateDate(new Date());
					serviceForm.setUpdateBy(userId);
					alData.add(new Object[]{serviceForm,"u"});
				}
				
				//��������С��
				SaleDetailForm psf = new SaleDetailForm();
				
				psf.setSaleNo(sif.getSaleNo());
				psf.setPartsId(rpfUd.getPartsId());
				psf.setOrderType("X");	//Я��ת����
				psf.setWarrantyType(rpfUd.getWarrantyType());
				psf.setStuffNo(rpfUd.getStuffNo());
				psf.setSkuCodeT(rpfUd.getSkuCode());
				psf.setSkuUnit(rpfUd.getSkuUnit());
				psf.setModelCode(serviceForm.getModelCode());
				psf.setModelSerialNo(serviceForm.getSerialNo());
				psf.setPartStatus("T");	//�ѷ���
				psf.setPartNum(rpfUd.getApplyQty());
				psf.setCreateBy(userId);
				psf.setCreateDate(new Date());
				
				alData.add(new Object[]{psf,"i"});
				
				
				flag=this.getBatchDao().allDMLBatch(alData);
			}
			
			
		} catch(Exception e) {
			flag=false;
			e.printStackTrace();
		}
		return flag;	   	
	}
	
	
	
	public RepairPartForm findById4RepairPart(Long partsId) throws Exception{
		return (RepairPartForm)this.getDao().findById(RepairPartForm.class,partsId);
	}
	
	public List<RepairPartForm> findRepairPartList(Long repairNo,String repairPartType) throws Exception{
		return (List<RepairPartForm>)this.getDao().list("from RepairPartForm rp where rp.repairNo=? and rp.repairPartType=?",repairNo,repairPartType);
	}
	
	public RepairFeeInfoForm findPlanRepairFee(Long repairNo) throws Exception{
		List feeList = this.getDao().list("from RepairFeeInfoForm rf where rf.repairNo=? and rf.feeType='P'",repairNo);
		if(feeList == null||feeList.isEmpty()) return null; 
		
		else return (RepairFeeInfoForm)feeList.get(0);
	}
	
	/**
	 * ͬ���Ϸ��޵��ж��ڳ�ʼ�Ǽ�ʱ�����û���ŵ���ʷά�޼�¼��
	 * a.û�м�¼������ά��
	 * b.�м�¼��������ά����δ������������ʾ��Ӧ���ţ�
	 * c.�м�¼����������3����ǰ�����ޣ� 
	 * d.�м�¼����������3�����ڣ�90���ڷ��ޡ�
	 * @param searchForm
	 * @return
	 * @throws Exception
	 */
	public String getRr90Status(RepairSearchForm searchForm) throws ComException,Exception{
		String status=null;
		
		List repairList = this.getDao().list("from RepairSearchForm as rsf where rsf.serialNo=? and rsf.delFlag=0",searchForm.getSerialNo());
		
		if(repairList==null||repairList.isEmpty()){
			status = "N";
		}else{
			for(int i=0;i<repairList.size();i++){
				RepairSearchForm rsf = (RepairSearchForm)repairList.get(i);
				
				if(!rsf.getCurrentStatus().equals("F")||rsf.getActualRepairedDate()==null){
					throw new ComException("�û�������ά����:"+rsf.getServiceSheetNo());
				}else if(rsf.getCurrentStatus().equals("C")||rsf.getCurrentStatus().equals("N")||rsf.getCurrentStatus().equals("P")){	//ȡ��,������,������
					continue;
				}else if("R".equals(status)){	//ǰһ�ŵ��Ѿ���90���ڷ���
					continue;
				}else{
					int day = Operate.getSpacingDay(rsf.getActualRepairedDate(),new Date());
					if(day<=90){
						status = "R";
					}else{
						status = "C";
					}
				}
			}
			
		}
		
		return status;
	}
	
	public static void main(String[] args) {
		String xx = "{ \"iristree\": [{\"14\": \"p1111\", \"15\":\"p222\", \"49\": \"1\", \"50\": \"8\", \"62\": \"5\", \"63\": \"2\" }]}";
		JSONObject jsonObject = JSONObject.fromObject(xx);
		JSONArray ja = jsonObject.getJSONArray("iristree");
		JSONObject irisValues = (JSONObject)ja.get(0);
		 Map<String, Object> irisMap = (Map) irisValues;
		 
		 System.out.println(irisMap.get("14"));
		 System.out.println(irisMap.get("62"));
		
		
	}
	
}
