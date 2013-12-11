package com.dne.sie.stock.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dne.sie.common.exception.ComException;
import com.dne.sie.common.exception.VersionException;
import com.dne.sie.common.tools.AtomRoleCheck;
import com.dne.sie.common.tools.CommonSearch;
import com.dne.sie.common.tools.DicInit;
import com.dne.sie.common.tools.FormNumberBuilder;
import com.dne.sie.common.tools.Operate;
import com.dne.sie.reception.form.SaleDetailForm;
import com.dne.sie.reception.queryBean.SaleDetailQuery;
import com.dne.sie.repair.form.RepairPartForm;
import com.dne.sie.repair.queryBean.RepairPartQuery;
import com.dne.sie.stock.form.StockBackForm;
import com.dne.sie.stock.form.StockFlowForm;
import com.dne.sie.stock.form.StockInfoForm;
import com.dne.sie.util.bo.CommBo;

public class StockBackBo extends CommBo{
	

	private static final StockBackBo INSTANCE = new StockBackBo();
		
	private StockBackBo(){
	}
	
	public static final StockBackBo getInstance() {
	   return INSTANCE;
	}
	
	public List<String[]> stockBackList(StockBackForm sbf) throws Exception,ComException{
		List<String[]> backList = new ArrayList<String[]>();
		CommonSearch cs = CommonSearch.getInstance();
		
		if(sbf.getStockBackItem().equals("S")){	//����ȡ��
			SaleDetailForm dtQuery = new SaleDetailForm();
			dtQuery.setSaleNo(sbf.getFormNo());
			dtQuery.setPartStatus("X");
			dtQuery.setStuffNo(sbf.getStuffNo());
			dtQuery.setSkuCode(sbf.getSkuCode());
			dtQuery.setUpdateBy(sbf.getApplyUser());
			
			SaleDetailQuery sdq = new SaleDetailQuery(dtQuery);
			List dataList = sdq.doListQuery();
			for (int i = 0; dataList!=null&&i < dataList.size(); i++) {
				SaleDetailForm rpf = (SaleDetailForm) dataList.get(i);
				String[] data = new String[9];
				data[0] = rpf.getSaleDetailId().toString() + DicInit.SPLIT1 + rpf.getVersion();
				data[1] = rpf.getSaleNo();
				data[2] = rpf.getStuffNo();
				data[3] = rpf.getSkuCode();
				data[4] = rpf.getPartNum().toString();
				data[5] = rpf.getSkuUnit();
				data[6] = cs.findUserNameByUserId(rpf.getUpdateBy());
				data[7] = rpf.getUpdateDate()==null?"":rpf.getUpdateDate().toLocaleString();
				data[8] = sbf.getStockBackItem();
				
				backList.add(data);
			}
			
		}else{
			RepairPartForm prQuery = new RepairPartForm();
			if(sbf.getStockBackItem().equals("X")){	//Я�����
				prQuery.setRepairPartStatus("X");
				prQuery.setRepairPartType("X");
			}else if(sbf.getStockBackItem().equals("T")){	//Я������
				prQuery.setRepairPartStatus("X");
				prQuery.setRepairPartType("T");
			}else if(sbf.getStockBackItem().equals("B")){	//��������
				prQuery.setRepairPartStatus("B");
				prQuery.setRepairPartType("W");
				prQuery.setWarrantyType("I");
			}else{
				throw new ComException("undefined StockBackItem:"+sbf.getStockBackItem());
			}
			
			prQuery.setStuffNo(sbf.getStuffNo());
			prQuery.setSkuCode(sbf.getSkuCode());
			prQuery.setUpdateBy(sbf.getApplyUser());
			prQuery.setServiceSheetNo(sbf.getFormNo());
			
			RepairPartQuery sooq = new RepairPartQuery(prQuery);
			List dataList = sooq.doListQuery();
			for (int i = 0; dataList!=null&&i < dataList.size(); i++) {
				RepairPartForm rpf = (RepairPartForm) dataList.get(i);
				String[] data = new String[9];
				data[0] = rpf.getPartsId().toString() + DicInit.SPLIT1 + rpf.getVersion();
				data[1] = rpf.getServiceSheetNo();
				data[2] = rpf.getStuffNo();
				data[3] = rpf.getSkuCode();
				data[4] = rpf.getApplyQty().toString();
				data[5] = rpf.getSkuUnit();
				data[6] = cs.findUserNameByUserId(rpf.getUpdateBy());
				data[7] = rpf.getUpdateDate()==null?"":rpf.getUpdateDate().toLocaleString();
				data[8] = sbf.getStockBackItem();
				
				backList.add(data);
			}
			
			
		}
		
		return backList;
	}
	
	public List<String[]> backApplyUserList() throws Exception{
		Integer[] roles = {AtomRoleCheck.RECEPTION,AtomRoleCheck.REPAIRMAN,AtomRoleCheck.REPAIRMANAGER};
		return AtomRoleCheck.getAllUserByRole(roles);
	}
	
	
	public int stockBackOperate(StockBackForm sbf,Long userId) throws VersionException,ComException,Exception{
		int tag=-1;
		ArrayList updateList = new ArrayList();
		if(sbf.getStockBackItemBak().equals("S")){	//����ȡ��
			
			String versionId = Operate.toVersionData(sbf.getPartsIds());
			String strHql = "from SaleDetailForm as prf where CONCAT(prf.saleDetailId,',',prf.version) in (" + versionId + ")";
			ArrayList dateList = (ArrayList) this.listVersion(strHql, sbf.getPartsIds().split(",").length);
			if(dateList!=null&&!dateList.isEmpty()){
				for(int i=0;i<dateList.size();i++){
					SaleDetailForm sdf= (SaleDetailForm)dateList.get(i);
		
					StockInfoForm sif=new StockInfoForm();
					//�Զ�����ԭ������
					sif.setStuffNo(sdf.getStuffNo());
					sif.setSkuCode(sdf.getSkuCode());
					sif.setSkuUnit(sdf.getSkuUnit());
					sif.setSkuNum(sdf.getPartNum());
					sif.setPerCost(sdf.getPurchasePrice());
					sif.setSkuType("S");
					sif.setStockStatus("A");
					sif.setFlowNo(FormNumberBuilder.getStockFlowId());
					sif.setCreateBy(userId);
					sif.setCreateDate(new Date());
					sif.setBinCode(sbf.getBinCode());
					
					StockFlowForm sff=StockInBo.getInstance().infoToFlow(sif);
					sff.setSkuNum(sdf.getPartNum());
					sff.setRestNum(StockOutBo.getInstance().getRestStock(sif.getStuffNo(), sdf.getPartNum(), "I"));
					sff.setFlowType("I");		//���
					sff.setFlowItem("A"); 		//�����˻�
					sff.setFeeType(sdf.getOrderType());
					sff.setFormNo(sdf.getSaleNo());
					sff.setRequestId(sdf.getSaleDetailId());
					sff.setBinCode(sbf.getBinCode());
					
					sdf.setPartStatus("Y");	//���˻ؿ�
					
					updateList.add(sif);
					updateList.add(sff);
					updateList.add(sdf);
				}
			}
			
		}else{
			String flowItem=null,skuType=null;
			if(sbf.getStockBackItemBak().equals("X")){	//Я�����
				flowItem="X";
				skuType="L";
			}else if(sbf.getStockBackItemBak().equals("T")){	//Я������
				flowItem="T";
				skuType="T";
			}else if(sbf.getStockBackItemBak().equals("B")){	//��������
				flowItem="B";
				skuType="X";
			}else{
				throw new ComException("undefined StockBackItem:"+sbf.getStockBackItem());
			}
			String versionId = Operate.toVersionData(sbf.getPartsIds());
			String strHql = "from RepairPartForm as prf where CONCAT(prf.id,',',prf.version) in (" + versionId + ")";
			ArrayList dateList = (ArrayList) this.listVersion(strHql, sbf.getPartsIds().split(",").length);
			if(dateList!=null&&!dateList.isEmpty()){
				for(int i=0;i<dateList.size();i++){
					RepairPartForm sdf= (RepairPartForm)dateList.get(i);
					sdf.setRepairPartStatus("R");	//�ѷ���
					sdf.setUpdateBy(userId);
					sdf.setUpdateDate(new Date());
					updateList.add(sdf);
					
					StockInfoForm sif=new StockInfoForm();
					//�Զ�����ԭ������
					sif.setStuffNo(sdf.getStuffNo());
					sif.setSkuCode(sdf.getSkuCode());
					sif.setSkuUnit(sdf.getSkuUnit());
					sif.setSkuNum(sdf.getApplyQty());
					sif.setPerCost(0F);
					sif.setSkuType(skuType);
					sif.setStockStatus("A");
					sif.setFlowNo(FormNumberBuilder.getStockFlowId());
					sif.setCreateBy(userId);
					sif.setUpdateDate(new Date());
					sif.setBinCode(sbf.getBinCode());
					
					StockFlowForm sff=StockInBo.getInstance().infoToFlow(sif);
					sff.setSkuNum(sdf.getApplyQty());
					sff.setRestNum(StockOutBo.getInstance().getRestStock(sif.getStuffNo(), sdf.getApplyQty(), "I"));
					sff.setFlowType("I");		//���
					sff.setFlowItem(flowItem); 	
					sff.setFeeType("R");
					sff.setFormNo(sdf.getServiceSheetNo());
					sff.setRequestId(sdf.getPartsId());
					sff.setBinCode(sbf.getBinCode());
					
					updateList.add(sif);
					updateList.add(sff);
				}
			}
			
			
			
		}
		
		if(this.getBatchDao().saveOrUpdateBatch(updateList)){
			tag = 1;
		}
		return tag;
	}
}
