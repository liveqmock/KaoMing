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
	private int reqFlag = 0;				//0Ϊ�Զ�����1Ϊ�ֹ�����

	private String oldReqStat;
	
	public int allocate(Object[] requestID)  throws VersionException,Exception{
		//default sales
		return this.allocate(requestID, "S");
		
	}
	
	  /**
	   * ������䡣
	   * @param requestID   Long[] request���
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
			//System.out.println("����������仹���ֹ�����====:"+reqFlag);
			//System.out.println("----dataList.size()="+dataList.size());
			for (int i=0;i<dataList.size();i++) {
				
				reqForm =(SaleDetailForm)dataList.get(i);
				if(i==0) saleNo=reqForm.getSaleNo();
				
				oldNum = reqForm.getPartNum().intValue();
				reqID = reqForm.getSaleDetailId();
				partsNum = oldNum;
				
				
//					System.out.println("��ʼ�����request====="+reqForm.getId());
//					System.out.println("��request��״̬��======"+reqForm.getPartStatus());
				
				if(reqForm.getPartStatus().equals("H")||reqForm.getPartStatus().equals("F")){
					
					reqAll.add(reqForm);
					oldReqStat = reqForm.getPartStatus();
					
					//ֱ�����������ֱ����̨����
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
						
					}else{	//��ͨ����
						for(int x=0;x<3;x++){
							StockInfoForm siForm = new StockInfoForm();
						
							if(partsNum  <= 0) break;
							if(x==1){
								//����������
								break;
							}else if(x==2){
								//���佫��������
								break;
							}else {
								//�����������
								
								siForm.setStuffNo(reqForm.getStuffNo());
								siForm.setStockStatus("A");
//									System.out.println("�����������======="+siForm.getStuffNo());
								partsNum=this.allocateStart(siForm,partsNum);	
//									System.out.println("�������������ʣ������======"+partsNum);
							}
							
							
							if(partsNum > 0){
								if(partsNum == oldNum){
									if(flag == 5 || flag == 0) flag = 5;
									else flag = 4;
								}else{
									flag = 4;
								}
								if(this.reqFlag == 0){	//�ж��Ƿ�Ϊ�������
									
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
			//���´�״̬
			SaleInfoBo.getInstance().renewSaleStatus(saleNo);
			
			return flag;
		}
		
		private List getReqList(String ids) throws VersionException,Exception {
			String versionId = Operate.toVersionData(ids);
			String strHql="from SaleDetailForm as sdf where CONCAT(sdf.saleDetailId,',',sdf.version) in ("+versionId+")";
			
			return this.listVersion(strHql, ids.split(",").length);  	
		} 
		
		/**
		 * ��������ƻ���׼������PO��
		 * @param requestForm   SaleDetailForm
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
			   
			//purchaseDollar̨����Ԫ����(purchasePrice�Ǹ��ݻ���exchangeRateת�����̨��RMB����)��ʵ��RMB����(PER_COST)���ջ�ʱ��ȷ��
			tempPlan.setPerQuote(sdf.getPurchaseDollar());
			tempPlan.setOrderNum(sdf.getPartNum());
			tempPlan.setModelCode(sdf.getModelCode());
			tempPlan.setModelSerialNo(sdf.getModelSerialNo());
			tempPlan.setOrderStatus("A");	//�ȴ�����
			tempPlan.setSaleNo(sdf.getSaleNo());
			
			SaleInfoForm sif=SaleInfoBo.getInstance().findById(sdf.getSaleNo());
			tempPlan.setCustomerId(sif.getCustomerId());
			tempPlan.setCustomerName(sif.getCustomerName());
			tempPlan.setDeliveryTime((sdf.getDeliveryTimeStart()==null?"":sdf.getDeliveryTimeStart())+"~"+(sdf.getDeliveryTimeEnd()==null?"":sdf.getDeliveryTimeEnd()));
			tempPlan.setShippingAddress(sif.getShippingAddress());
			
			tempPlan.setCreateDate(new Date());
			tempPlan.setCreateBy(sdf.getCreateBy());	//���ʵ�ʵľ�����
			
			Object[] obj1={tempPlan,"i"};
			al.add(obj1);
			Object[] obj2={sdf,"u"};
			al.add(obj2);
			flag = this.getBatchDao().allDMLBatch(al);
			
			return flag;
		}
		

		/**
		 * ���俪ʼ���ղ�ͬ���������ͽ�����Ӧ�Ĳ�λ˳����䣬��󷵻ط����ʣ��������������
		 * @param siForm   StockInfoForm    �����Ŀ����Ϣ
		 * @param partsNum   int            �����������
		 * @return int  �����ʣ��������������
		 */
		private int allocateStart(StockInfoForm siForm,int partsNum ) throws Exception{
								
			//���������������߼�(S=�����������)
			if(!reqForm.getOrderType().equals("D") ){
				//�ȷ���UN���
//					siForm.setStockType("R");
				
				partsNum = this.allocateJob(siForm);
				if(partsNum > 0){
					//�绹�����������,����NN
//						siForm.setStockType("N");
//						partsNum = this.allocateJob(siForm);	
					
				}
			//����ά�޽�������������
			}else{
				
			}
			
			return partsNum;
		}
		

		/**
		 * ���������Ŀ����Ϣ�Ĳ�ѯ����form�����з���
		 * @param queryForm   StockInfoForm   
		 * @return int  ������������
		 */
		private int allocateJob(StockInfoForm queryForm) throws Exception{ 
			int flag = 0 ;
			int count = 0;
			int partsNum = 0 ;
			
			queryForm.setStrSkuNum("-1");	//����������Ϊ0�Ŀ�����
			
			StockInfoQuery prq = new StockInfoQuery(queryForm);
		
			partsNum =  reqForm.getPartNum();
			
			stockAll = prq.doListQuery();	
			count = prq.doCountQuery();
			
			//�����ѯ�Ŀ���¼��Ϊ0�򷵻��������������������Է�����н������ķ���flag�ʹ�����Ҫ���з��������
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
		 * �ṩ�ض������ŷ��ϲ�λ�����ģ�N,U�֣������Ϣ��arraylist�Լ���Ҫ�����������<br>
		 * ����������䣨arraylist�Ѱ����ʱ�����������
		 * @param reqNum   int   
		 * @param dataArr    ArrayList
		 * @return int 1=�ɹ�,-1=ʧ��
		 */
		private int reqAllocate(int reqNum,ArrayList dataArr) throws Exception{
			int leftNum = reqNum;
			int rang;
			//String allocatePartCode="";		//ʵ�ʷ������
			boolean t = false;
			
			for (int i=0;i<dataArr.size();i++) {
				StockInfoForm temp = (StockInfoForm)dataArr.get(i);
				//allocatePartCode=temp.getSkuCode();
				reqID = reqForm.getSaleDetailId();
				rang = temp.getSkuNum().intValue() - leftNum;
				//�������������ڻ������������
				if( rang >= 0 ){
					//�������������������
					if(rang > 0 ){
						//�޸Ŀ������Ϊ�����ʣ�������
						temp.setSkuNum(new Integer(rang));
						temp.setUpdateBy(new Long(-1));
						temp.setUpdateDate(new Date());
						temp.setCreateBy(null);
						t = this.getDao().update(temp);
						//����һ���µĿ���¼�����״̬Ϊ����״̬(R=����״̬)						
						temp.setSkuNum(new Integer(leftNum));
						temp.setStockStatus("R");
						temp.setRequestId(reqID);		//��������ǰ��������request
						temp.setSkuType("S");
						temp.setStockId(null);
						temp.setUpdateBy(null);
						temp.setUpdateDate(null);
						temp.setCreateBy(new Long(-1));
						t = t && this.getDao().insert(temp);
						//�������������������
					}else{
						//�����״̬��Ϊ����״̬(R=����״̬)			
						temp.setStockStatus("R");
						temp.setRequestId(reqID);		//��������ǰ��������request
						temp.setSkuType("S");
						temp.setCreateBy(null);
						temp.setUpdateBy(new Long(-1));
						temp.setUpdateDate(new Date());
						t = this.getDao().update(temp);
					}
						
					if(t){
						//�������Ǳ��ڶ���������һ����棬����Ҫ�ٲ���һ������
						if(AtomRoleCheck.checkSaleIW(reqForm.getWarrantyType())){
							if(this.createIWPlan(leftNum)){
								System.err.println("ERROR TO CREATE INTERNAL PO!  REQUSET ID:" + reqID + "   PARTS NUM:" + leftNum);
							}
						}							
						leftNum = 0;
					}
					//�����Ѿ������������ٽ��з��䣬���о�break��
					break;
				//��������������������	
				}else{
					//���ܿ���ǲ��Ǳ���������Ȱѿ��Է�����ȷ���			
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
						//���leftNum�ʹ��������������з������ȱ�ٵ�����
						leftNum = -(rang);
					}
				}
			}

			//reqNum��������-leftNumȱ�ٻ��߻�����������=�Ѿ����������
			reqForm.setPartNum(new Integer(reqNum - leftNum));
			//L : �ѷ������ȡ
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
		 * ���ڶ���������һ����棬����Ҫ�ٲ���һ������
		 * @param partsNum   int   �������
		 * @return boolean
		 */
		private boolean createIWPlan(int partsNum){
			boolean flag = false;
			try{
				
				
				
				PoForm tempPlan = new PoForm();
				tempPlan.setOrderType("N");	//���ڲ����Զ�����
				tempPlan.setWarrantyType("I");
				tempPlan.setRequestId(reqForm.getSaleDetailId());
				
				tempPlan.setStuffNo(reqForm.getStuffNo());
				tempPlan.setSkuCode(reqForm.getSkuCode());
				tempPlan.setSkuUnit(reqForm.getSkuUnit());
				   
				//purchaseDollar̨����Ԫ����(purchasePrice�Ǹ��ݻ���exchangeRateת�����̨��RMB����)��ʵ��RMB����(PER_COST)���ջ�ʱ��ȷ��
				tempPlan.setPerQuote(reqForm.getPurchaseDollar());
				tempPlan.setOrderNum(partsNum);
				tempPlan.setModelCode(reqForm.getModelCode());
				tempPlan.setModelSerialNo(reqForm.getModelSerialNo());
				tempPlan.setOrderStatus("A");	//�ȴ�����
				tempPlan.setSaleNo(reqForm.getSaleNo());
				
				SaleInfoForm sif=SaleInfoBo.getInstance().findById(reqForm.getSaleNo());
				tempPlan.setCustomerId(sif.getCustomerId());
				tempPlan.setCustomerName(sif.getCustomerName());
				tempPlan.setDeliveryTime((reqForm.getDeliveryTimeStart()==null?"":reqForm.getDeliveryTimeStart())+"~"+(reqForm.getDeliveryTimeEnd()==null?"":reqForm.getDeliveryTimeEnd()));
				tempPlan.setShippingAddress(sif.getShippingAddress());
				
				tempPlan.setCreateDate(new Date());
				tempPlan.setCreateBy(reqForm.getCreateBy());	//���ʵ�ʵľ�����
				
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
							//�������������ڻ������������
							if( rang >= 0 ){
								//�������������������
								if(rang > 0 ){
									//�޸Ŀ������Ϊ�����ʣ�������
									temp.setSkuNum(new Integer(rang));
									temp.setUpdateBy(new Long(-1));
									temp.setUpdateDate(new Date());
									temp.setCreateBy(null);
									this.getDao().update(temp);
									//����һ���µĿ���¼�����״̬Ϊ����״̬(R=����״̬)						
									temp.setSkuNum(new Integer(leftNum));
									temp.setStockStatus("R");
									temp.setRequestId(reqID);		//��������ǰ��������request
									temp.setSkuType("L");
									temp.setStockId(null);
									temp.setUpdateBy(null);
									temp.setUpdateDate(null);
									temp.setCreateBy(new Long(-1));
									this.getDao().insert(temp);
									//�������������������
								}else{
									//�����״̬��Ϊ����״̬(R=����״̬)			
									temp.setStockStatus("R");
									temp.setRequestId(reqID);		//��������ǰ��������request
									temp.setSkuType("L");
									temp.setCreateBy(null);
									temp.setUpdateBy(new Long(-1));
									temp.setUpdateDate(new Date());
									this.getDao().update(temp);
								}
									
								//�����Ѿ������������ٽ��з��䣬���о�break��
								break;
							//��������������������	
							}else{
								//���ܿ���ǲ��Ǳ���������Ȱѿ��Է�����ȷ���			
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
							//�������������ڻ������������
							if( rang >= 0 ){
								//�������������������
								if(rang > 0 ){
									//�޸Ŀ������Ϊ�����ʣ�������
									temp.setSkuNum(new Integer(rang));
									temp.setUpdateBy(new Long(-1));
									temp.setUpdateDate(new Date());
									temp.setCreateBy(null);
									this.getDao().update(temp);
									//����һ���µĿ���¼�����״̬Ϊ����״̬(R=����״̬)						
									temp.setSkuNum(new Integer(leftNum));
									temp.setStockStatus("R");
									temp.setRequestId(reqID);		//��������ǰ��������request
									temp.setSkuType("L");
									temp.setStockId(null);
									temp.setUpdateBy(null);
									temp.setUpdateDate(null);
									temp.setCreateBy(new Long(-1));
									this.getDao().insert(temp);
									//�������������������
								}else{
									//�����״̬��Ϊ����״̬(R=����״̬)			
									temp.setStockStatus("R");
									temp.setRequestId(reqID);		//��������ǰ��������request
									temp.setSkuType("L");
									temp.setCreateBy(null);
									temp.setUpdateBy(new Long(-1));
									temp.setUpdateDate(new Date());
									this.getDao().update(temp);
								}
									
								//�����Ѿ������������ٽ��з��䣬���о�break��
								break;
							//��������������������	
							}else{
								//���ܿ���ǲ��Ǳ���������Ȱѿ��Է�����ȷ���			
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
		 * @param i  ��������
		 */
		public void setReqFlag(int i) {
			reqFlag = i;
		}
	

}
