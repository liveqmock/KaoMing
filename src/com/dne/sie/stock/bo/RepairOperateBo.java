package com.dne.sie.stock.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import com.dne.sie.common.exception.VersionException;
import com.dne.sie.common.tools.CommonSearch;
import com.dne.sie.common.tools.DicInit;
import com.dne.sie.common.tools.FormNumberBuilder;
import com.dne.sie.common.tools.Operate;
import com.dne.sie.repair.form.RepairPartForm;
import com.dne.sie.repair.queryBean.RepairPartQuery;
import com.dne.sie.stock.form.StockFlowForm;
import com.dne.sie.stock.form.StockInfoForm;
import com.dne.sie.stock.form.StockToolsInfoForm;
import com.dne.sie.util.bo.CommBo;

public class RepairOperateBo extends CommBo{
	//private static Logger logger = Logger.getLogger(RepairOperateBo.class);

	private static final RepairOperateBo INSTANCE = new RepairOperateBo();
		
	private RepairOperateBo(){
	}
	
	public static final RepairOperateBo getInstance() {
	   return INSTANCE;
	}
	
	/**
	 * ά�����/���߽����б�
	 * @param RepairPartForm ��ѯ����
	 * @return ��ѯ���
	 */
	public ArrayList loanOutList(RepairPartForm prQuery) {
		ArrayList dataList = null;
		ArrayList alData = new ArrayList();
		RepairPartQuery sooq = new RepairPartQuery(prQuery);

		int count = 0;
		try {
			dataList = (ArrayList) sooq.doListQuery(prQuery.getFromPage(),prQuery.getToPage());

			count = sooq.doCountQuery();
			CommonSearch cs = CommonSearch.getInstance();
			for (int i = 0; dataList!=null&&i < dataList.size(); i++) {
				RepairPartForm rpf = (RepairPartForm) dataList.get(i);
				String[] data = new String[11];
				data[0] = rpf.getPartsId().toString() + DicInit.SPLIT1 + rpf.getVersion();
				data[1] = rpf.getServiceSheetNo();
				data[2] = rpf.getStuffNo();
				data[3] = rpf.getSkuCode();
				data[4] = rpf.getSkuUnit();
				data[5] = rpf.getStandard();
				data[6] = rpf.getApplyQty().toString();
				data[7] = cs.findUserNameByUserId(rpf.getCreateBy());
				data[8] = rpf.getCreateDate().toLocaleString();
				data[9] = rpf.getRepairPartStatus();
				data[10] = rpf.getRepairPartType();
				
				alData.add(data);
			}
			alData.add(0, count + "");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return alData;
	}
	
	
	/**
	 * ��ȡ������
	 * @param String 
	 * @return Float
	 */
   public List getPartCreateByList() {
	   List al=new ArrayList();
	   	try{
	   		String hql="select distinct sif.createBy,uf.userName from RepairPartForm as sif," +
	   				"UserForm as uf where uf.id=sif.createBy ";
	   		al=this.getDao().list(hql);
	   		
		}catch(Exception e){
			e.printStackTrace();
		}
		return al;
   }
   
   

	/**
	  * ����ȷ�ϣ��޸�ά����������(td_repair_parts_info)��Ϣ��
	  * 	�޸Ŀ����Ϣ��(TD_STOCK_INFO)����Ϊ0����delete ���߱�(TD_STOCK_TOOLS_INFO)����
	  * 	����������ˮ��(td_stock_flow)һ�������¼
	  * @param String �������¼id��
	  * @param Long ������
	  * @return �Ƿ�ɹ���־
	  */
	public int stockPartOut(String ids, Long userId) throws VersionException {
		int tag = -1;

		ArrayList opList = new ArrayList();
		
		try {
			StockInfoListBo sil=StockInfoListBo.getInstance();
			StockInBo sib=StockInBo.getInstance();
			StockOutBo sob = StockOutBo.getInstance();
			
			String[] temp = ids.split(",");
			HashSet hs = new HashSet();
			for (int i = 0; i < temp.length; i++) {
				hs.add(temp[i]);
			}
			ids = Operate.arrayListToString(new ArrayList(hs));
			String versionId = Operate.toVersionData(ids);
			String strHql = "from RepairPartForm as prf where CONCAT(prf.id,',',prf.version) in (" + versionId + ")";
			ArrayList dateList = (ArrayList) this.listVersion(strHql, ids.split(",").length);
			for (int i = 0; i < dateList.size(); i++) { 
				RepairPartForm prf = (RepairPartForm) dateList.get(i);
				prf.setRepairPartStatus("X");		//��Я��������
				prf.setUpdateBy(userId);
				Object prfO = new Object[]{prf,"u"};
				opList.add(prfO);
				
				ArrayList stockInfoList =  (ArrayList) this.getDao().list( "from StockInfoForm as sif where sif.requestId=? and sif.stockStatus='R'", prf.getPartsId());
				
				for (int j = 0; j < stockInfoList.size(); j++) {
					StockInfoForm sif = (StockInfoForm) stockInfoList.get(j);
					sif.setSkuNum(new Integer(0));
					sif.setUpdateBy(userId);
					sif.setUpdateDate(new Date());
					sif.setFlowNo(FormNumberBuilder.getStockFlowId());
					sif.setTransportMode(null);
					
					StockFlowForm sff = sib.infoToFlow(sif);
					
					sff.setSkuNum(prf.getApplyQty());	//��������
					sff.setRestNum(sob.getRestStock(sif.getStuffNo(), prf.getApplyQty(), "O"));	//�������
//					if(sff.getRestNum()==0){
//						strStuffNo+="','"+sif.getStuffNo();
//					}
					
					sff.setInFlowNo(sif.getFlowNo());
					sff.setFlowType("O");		//����
					sff.setFlowItem("V"); 		//Я���������
					
					sff.setFeeType(prf.getWarrantyType());
					sff.setFormNo(prf.getServiceSheetNo());
					sff.setCreateBy(userId);
					sff.setRequestId(prf.getPartsId());
					
					
					sff.setCustomerName((String)(sil.getRepairFormNo(prf.getPartsId()))[1]);
					
					Object sffO = new Object[]{sff,"i"};
					opList.add(sffO);
					Object sifO = new Object[]{sif,"d"};
					opList.add(sifO);


				} //end for

			} //end for
			if (this.getBatchDao().allDMLBatch(opList)) {
				tag = 1;
			}
		

		} catch (VersionException ve) {
			throw ve;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return tag;
	}
	
	

	/**
	  * ����ȷ�ϣ��޸�ά����������(td_repair_parts_info)��Ϣ��
	  * 	�޸Ŀ����Ϣ��(TD_STOCK_INFO)����Ϊ0����delete ���߱�(TD_STOCK_TOOLS_INFO)����
	  * 	����������ˮ��(td_stock_flow)һ�������¼
	  * @param String �������¼id��
	  * @param Long ������
	  * @return �Ƿ�ɹ���־
	  */
	public int stockToolOut(String ids, Long userId) throws VersionException {
		int tag = -1;

		ArrayList opList = new ArrayList();
		
		try {
			StockInfoListBo sil=StockInfoListBo.getInstance();
			StockInBo sib=StockInBo.getInstance();
			StockToolsBo stb = StockToolsBo.getInstance();
			StockOutBo sob = StockOutBo.getInstance();
			
			String[] temp = ids.split(",");
			HashSet hs = new HashSet();
			for (int i = 0; i < temp.length; i++) {
				hs.add(temp[i]);
			}
			ids = Operate.arrayListToString(new ArrayList(hs));
			String versionId = Operate.toVersionData(ids);
			String strHql = "from RepairPartForm as prf where CONCAT(prf.id,',',prf.version) in (" + versionId + ")";
			ArrayList dateList = (ArrayList) this.listVersion(strHql, ids.split(",").length);
			for (int i = 0; i < dateList.size(); i++) { 
				RepairPartForm prf = (RepairPartForm) dateList.get(i);
				prf.setRepairPartStatus("X");		//��Я��������
				prf.setUpdateBy(userId);
				
				Object prfO = new Object[]{prf,"u"};
				opList.add(prfO);
				
				ArrayList stockInfoList =  (ArrayList) this.getDao().list( "from StockToolsInfoForm as sif where sif.requestId=? and sif.stockStatus='R'", prf.getPartsId());
				
				for (int j = 0; j < stockInfoList.size(); j++) {
					StockToolsInfoForm sif = (StockToolsInfoForm) stockInfoList.get(j);
					sif.setSkuNum(new Integer(0));
					sif.setUpdateBy(userId);
					sif.setUpdateDate(new Date());
					sif.setFlowNo(FormNumberBuilder.getStockFlowId());
					
					StockFlowForm sff = stb.infoToFlow(sif);
					
					sff.setSkuNum(prf.getApplyQty());	//��������
					sff.setRestNum(sob.getRestStock(sif.getStuffNo(), prf.getApplyQty(), "O"));	//�������
//					if(sff.getRestNum()==0){
//						strStuffNo+="','"+sif.getStuffNo();
//					}
					
					sff.setInFlowNo(sif.getFlowNo());
					sff.setFlowType("O");		//����
					sff.setFlowItem("U"); 		//Я���������
					
					sff.setFeeType(prf.getWarrantyType());
					sff.setFormNo(prf.getServiceSheetNo());
					sff.setCreateBy(userId);
					sff.setRequestId(prf.getPartsId());
					
					sff.setCustomerName((String)(sil.getRepairFormNo(prf.getPartsId()))[1]);
					
					Object sffO = new Object[]{sff,"i"};
					opList.add(sffO);
					Object sifO = new Object[]{sif,"d"};
					opList.add(sifO);



				} //end for

			} //end for
			if (this.getBatchDao().allDMLBatch(opList)) {
				tag = 1;
			}
		

		} catch (VersionException ve) {
			throw ve;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return tag;
	}
	

}
