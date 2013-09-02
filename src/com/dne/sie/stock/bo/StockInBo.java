package com.dne.sie.stock.bo;

//Java ������
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Hibernate;

import com.dne.sie.common.exception.IllegalPoException;
import com.dne.sie.common.tools.CommonSearch;
import com.dne.sie.common.tools.DicInit;
import com.dne.sie.common.tools.FormNumberBuilder;
import com.dne.sie.common.tools.Operate;
import com.dne.sie.maintenance.bo.PartInfoBo;
import com.dne.sie.maintenance.form.PartInfoForm;
import com.dne.sie.reception.bo.PartPoBo;
import com.dne.sie.reception.form.PoForm;
import com.dne.sie.stock.form.StockFlowForm;
import com.dne.sie.stock.form.StockInfoForm;
import com.dne.sie.stock.queryBean.StockFlowQuery;
import com.dne.sie.util.bo.CommBo;
import com.dne.sie.util.query.QueryParameter;


/**
 * ������BO������
 * @author xt
 * @version 1.1.5.6
 * @see StockInBo.java <br>
 */
public class StockInBo extends CommBo{
	//private static Logger logger = Logger.getLogger(StockInBo.class);

	private static final StockInBo INSTANCE = new StockInBo();
		
	private StockInBo(){
	}
	
	public static final StockInBo getInstance() {
	   return INSTANCE;
	}
	
	

	/**
	  * ����������ʾ����
	  * @param sif - �����Ϣ��
	  * @return ����List
	  */
	public ArrayList stockInOutList(StockFlowForm sffQuery)  throws Exception{

		ArrayList alData = new ArrayList();
		StockFlowQuery uq = new StockFlowQuery(sffQuery);
		int count = 0;
		
		List dataList = uq.doListQuery(sffQuery.getFromPage(),sffQuery.getToPage());
		count = uq.doCountQuery();
		
		StockFlowForm sff=null;
		for (int i = 0; i < dataList.size(); i++) {
			sff = (StockFlowForm)dataList.get(i);
			String[] data = new String[14];
			data[0] = sff.getFlowId().toString();
			data[1] = DicInit.getSystemName("SKU_TYPE",sff.getSkuType());
			data[2] = sff.getSkuCode();
			data[3] = sff.getStuffNo();
			data[4] = sff.getSkuNum()==null?"":sff.getSkuNum().toString();
			data[5] = sff.getSkuUnit();
			data[6] = sff.getPerCost()==null?"":sff.getPerCost().toString();
			data[7] = sff.getCustomerName();
			data[8] = DicInit.getSystemName("FLOW_ITEM",sff.getFlowItem());
			data[9] = sff.getRemark();
			data[10] = sff.getCreateDate()==null?"":Operate.trimDate(sff.getCreateDate()).toString();
			data[11] = sff.getFormNo()==null?"":sff.getFormNo();
			data[12] = sff.getOrderDollar()==null?"":sff.getOrderDollar().toString();
			data[13] = DicInit.getSystemName("TRANSPORT_MODE",sff.getTransportMode());
			
			alData.add(data);
		}
		alData.add(0, count + "");
	
		return alData;	     	
	}

	
	/**
	  * ���������ȷ��
	  * 	��������Ϣ��(TD_STOCK_INFO)��Ϣ��
	  * 	����������ˮ��(td_stock_flow)һ�������¼
	  * @param sif - �����Ϣ��
	  * @return �Ƿ�ɹ���־
	  */
	public int stockAdjustIn(StockFlowForm sff)  throws Exception{
		int tag=-1;
		StockInfoForm sif=flowToInfo(sff);
		StockOutBo sob=StockOutBo.getInstance();
		sff.setRestNum(sob.getRestStock(sif.getStuffNo(), sff.getSkuNum(), "I"));
		ArrayList al=new ArrayList();
		al.add(sif);
		al.add(sff);
		if(this.getBatchDao().insertBatch(al)){
			tag=1;
			//��ԭ��0�����ĸ����ɾ��
//			sob.inMerge(sff.getStuffNo());
		}
		
		return tag;	   	     	
	}

	/**
	  * ��ȡ������ӡ����
	  * @param sif - ��ˮid��
	  * @return �Ƿ�ɹ���־
	  */
	public ArrayList getFlowPrint(String ids)  throws Exception{
		String strHql="from StockFlowForm as sff where sff.flowId in ("+ids+")";
		ArrayList al=(ArrayList)this.getDao().list(strHql);
		return al;
	}
	
	/**
	  * ��ȡ��ǰ�����
	  * @param sif - ��ˮid��
	  * @return �Ƿ�ɹ���־
	  */
	public int getNowSkuNum(String skuCode)  throws Exception{
		String strHql="select sum(sif.skuNum) from StockInfoForm as sif where sif.skuCode='"+skuCode+"'";
		Long sum=(Long)this.getDao().uniqueResult(strHql);
		return sum==null?0:sum.intValue();
	}
	

	/**
	 * ��ˮ�����ĵ���
	 * @param StockFlowForm ��ѯ����
	 * @return �����������
	 */
	public String stockFlowTxt(StockFlowForm sfQuery){
		String strRet=null;

		try {
			StockFlowQuery sfq = new StockFlowQuery(sfQuery);
			Object[] paraObj=sfq.queryCondition(sfQuery);
			String strHql="select trim(pa.createDate),pa.customerName," +
				"pa.shortCode,pa.skuCode,pa.standard,pa.skuNum,pa.skuUnit," +
				"pa.perCost,pa.remark from StockFlowForm as pa "+(String)paraObj[0];
			
			ArrayList alData = new ArrayList(this.getDao().parameterQuery(strHql,(ArrayList)paraObj[1]));
			
			String[] colName =
				{
					"���",
					"����",
					"��˾����",
					"���",
					"�������",
					"���",
					"����",
					"��λ",
					"����",
					"��ע"
				};
			alData.add(0, colName);

			StringBuffer strSource=new StringBuffer();
							
			for(int i=0;i<alData.size();i++){
				Object[] temp=(Object[])alData.get(i);
				if(i>0) strSource.append(i).append("\t");
				for(int j=0;j<temp.length;j++){
					if(j==0) strSource.append(temp[j]);
					else strSource.append("\t").append(temp[j]==null?"":temp[j]);
				}
				strSource.append("\r\n");
			}
			strRet=strSource.toString();
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return strRet;

	}
	
	/**
	 * ��ȡ��λ��
	 * @return ��λ��list
	 */
	public ArrayList getStockList() throws Exception{
		ArrayList binCode = (ArrayList) this.getDao().listAll(
				"select sbf.binCode from StationBinForm as sbf " +
				"where sbf.delFlag=0 order by  sbf.binCode");
		
		return binCode;
	}
	
	

	/**
	 * ��������б��ѯ
	 * @param orderNo
	 * @return List ��ѯ���
	 */
	public List orderInList(String orderNo) {
		List<String[]> orderInfoList = new ArrayList<String[]>();
		try {
			//��ѯ�����е�ĳ�������PO��Ϣ
			String strHql ="from PoForm as po where po.orderNo= :orderNo and po.orderStatus='B'";
			ArrayList<QueryParameter> paramList = new ArrayList<QueryParameter>();
			QueryParameter param = new QueryParameter();
			param.setName("orderNo");
			param.setValue(orderNo);
			param.setHbType(Hibernate.STRING);
			paramList.add(param);
		
			List<PoForm> orderList = this.getDao().parameterQuery(strHql, paramList);
			for(int i=0;orderList!=null&&i<orderList.size();i++){
				PoForm pf=orderList.get(i);
				String[] temp = new String[12];
				temp[0]=pf.getPoNo().toString();
				temp[1]=pf.getStuffNo();
				temp[2]=pf.getModelCode();
				temp[3]=pf.getSaleNo();
				temp[4]=pf.getCustomerName();
				temp[5]=pf.getDeliveryTime();
				temp[6]=pf.getShippingAddress();
				temp[7]=pf.getOrderNum().toString();
				temp[8]=Operate.toFix(pf.getPerQuote(), 2);
				temp[9]=pf.getCreateDate().toLocaleString();
				temp[10]=Operate.toFix(pf.getPerQuote() * CommonSearch.getInstance().getExchangeRate(pf.getSaleNo()), 2);
				temp[11]=pf.getTransportMode();
				
				orderInfoList.add(temp);
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderInfoList;
	}
	
	
	/**
	 * ������� ����
	 * 1.�Ƚ�ʵ���յ����ȫ����⣨��ʱ���X��������StockInfoForm��StockFlowForm����recevie����
	 * 2.�޸�PO״̬���Լ����PO
	 * 		����PO�����͵���recevie����:
	 * 		a. PO<=recevie	: 
	 * 			����ӦPOֱ���޸�״̬���ѵ���/�ѷ������ȡ����
	 * 		b. PO>recevie	: 
	 * 			���PO��ԭ����¼��num��ΪPO-recevie��״̬���䣻
	 * 			����recevie�������޸�״̬���ѵ���/�ѷ������ȡ����
	 * 3.���÷����߼�
	 * 		a. �������������״̬�޸Ļ��֣����ж����۵�״̬�Ƿ���Ҫ�޸ģ�
	 * 		b. �����������detailId��ֵ�����requestId�������ÿ��������ѷ������ȡ�� ��
	 * 		c. �������ʣ�࣬��ʣ������ٷ����������Ҫ������ĵ��ӣ�
	 * @param �ջ���Ϣ�����۵��ţ������� 
	 * @return tag
	 */
   public synchronized int orderInReceive(String[][] para,String saleNo,Long userId,String transportMode) throws Exception {
	   int tag = -1;

	   ArrayList poList=new ArrayList();
	   ArrayList<StockInfoForm> stockList=new ArrayList<StockInfoForm>();
	   PartPoBo ppb=PartPoBo.getInstance();
	   PartInfoBo pib=PartInfoBo.getInstance();
	   //SaleInfoBo sib=SaleInfoBo.getInstance();
	   StockOutBo sob=StockOutBo.getInstance();
	   String inStuffNo="";
	   for(int i=0;i<para[0].length;i++){
		   PoForm pf=ppb.findById(new Long(para[0][i]));
		   PartInfoForm pi=pib.find(pf.getStuffNo());
		   //����ϺŷǷ�
		   if(pi==null){
			   throw new IllegalPoException(pf.getStuffNo());
		   }
		   
		   int intPoNum = pf.getOrderNum();
		   int intReceiveNum = Integer.parseInt(para[1][i]);
		   
		   if(intReceiveNum>0){
			    //��������Ϣ��
			    StockInfoForm sif = new StockInfoForm();
			    
				sif.setCreateBy(userId);
				sif.setStuffNo(pf.getStuffNo());
				sif.setSkuCode(pf.getSkuCode());
				sif.setShortCode(pi.getShortCode());
				sif.setStandard(pi.getStandard());
				sif.setSkuUnit(pf.getSkuUnit());
				sif.setSkuNum(intReceiveNum);
				sif.setPerCost(calculatePerCost(new Float(para[2][i]),new Float(para[4][i]),new Float(para[6][i])));
				sif.setOrderDollar(new Float(para[3][i]));
				sif.setFreightTW(new Float(para[4][i]));
				sif.setInvoiceNo(para[5][i]);
				sif.setSkuType("A");	//Ĭ��
				sif.setStockStatus("X");	//��ʱ״̬���ȴ���������ʱ�޸�
				sif.setRequestId(pf.getRequestId());	//�������ʱ��saleDetailId����������ʱ����
				sif.setSkuType("S");
				sif.setCreateBy(userId);
				sif.setCreateDate(new Date());
				sif.setFlowNo(FormNumberBuilder.getStockFlowId());		//��ˮ��fk
				sif.setTransportMode(transportMode);
				stockList.add(sif);
				
				inStuffNo+=","+sif.getStuffNo();
				
				//����������ˮ��
				StockFlowForm sff = this.infoToFlow(sif);
				sff.setSkuNum(intReceiveNum);
				sff.setRestNum(sob.getRestStock(sif.getStuffNo(), intReceiveNum, "I"));
				
				sff.setCustomerName(pf.getCustomerName());
				sff.setFlowType("I");
				sff.setFlowItem("D");	//�������
				sff.setFeeType(pf.getOrderType());
				sff.setRequestId(pf.getPoNo());
				sff.setFormNo(pf.getSaleNo());
				sff.setOrderDollar(sif.getOrderDollar());
				sff.setFreightTW(sif.getFreightTW());
				sff.setInvoiceNo(sif.getInvoiceNo());
				sff.setTransportMode(sif.getTransportMode());
				
				poList.add(sff);
				
				if(intPoNum<=intReceiveNum){	//a. PO<=recevie
					pf.setOrderStatus("C");	//�ѵ���
					pf.setUpdateBy(userId);
					pf.setUpdateDate(new Date());
					poList.add(pf);
				}else{	//b. PO>recevie
					//ԭ����¼��num��ΪPO-recevie����Ȼ�ȴ�PO
					pf.setOrderNum(intPoNum-intReceiveNum);
					pf.setUpdateBy(userId);
					pf.setUpdateDate(new Date());
					poList.add(pf);
					
					//��ֳ�recevie����״̬�ѵ���
					PoForm newPo=new PoForm();
					BeanUtils.copyProperties(newPo, pf);
					newPo.setPoNo(null);
					newPo.setOrderNum(intReceiveNum);
					newPo.setOrderStatus("C");	//�ѵ���
					newPo.setCreateBy(userId);
					newPo.setCreateDate(new Date());
					poList.add(newPo);
					
				}
		   }
	   }
	   
	   if(poList.size()>0&&this.getBatchDao().saveOrUpdateBatch(poList)){
		   if (this.getBatchDao().insertBatch(stockList)) {
			   tag = ReceiveAllocateBo.getInstance().allocate(stockList);
//			   sob.inMerge(inStuffNo.substring(1).replace(",", "','"));
		   }
	   }
	   
	   return tag;
   }
   
   private static Float calculatePerCost(Float cost,Float freightTW,Float tariff){
	   return cost + freightTW + tariff;
   }
   

	public StockFlowForm infoToFlow(StockInfoForm sif)  throws Exception{
		StockFlowForm sff=new StockFlowForm();
		sff.setFlowId(sif.getFlowNo());
		sff.setSkuCode(sif.getSkuCode());
		sff.setShortCode(sif.getShortCode());
		sff.setStandard(sif.getStandard());
		sff.setStuffNo(sif.getStuffNo());
		sff.setSkuUnit(sif.getSkuUnit());
		sff.setSkuNum(sif.getSkuNum());
		sff.setPerCost(sif.getPerCost());
		sff.setSkuType(sif.getSkuType());
		sff.setRemark(sif.getRemark());
		sff.setCreateBy(sif.getCreateBy());
		sff.setCreateDate(new Date());
		sff.setTransportMode(sif.getTransportMode());
		
		sff.setOrderDollar(sif.getOrderDollar());
		sff.setFreightTW(sif.getFreightTW());
		
		return sff;	   	     	
	}

	public StockInfoForm flowToInfo(StockFlowForm sff)  throws Exception{

		StockInfoForm sif=new StockInfoForm();
		sif.setFlowNo(sff.getFlowId());
		sif.setSkuCode(sff.getSkuCode());
		sif.setShortCode(sff.getShortCode());
		sif.setStandard(sff.getStandard());
		sif.setStuffNo(sff.getStuffNo());
		sif.setSkuUnit(sff.getSkuUnit());
		sif.setSkuNum(sff.getSkuNum());
		sif.setPerCost(sff.getPerCost());
		sif.setSkuType(sff.getSkuType());
		sif.setRemark(sff.getRemark());
		sif.setCreateBy(sff.getCreateBy());
		sif.setCreateDate(new Date());
		sif.setUpdateDate(sff.getUpdateDate());

		sif.setOrderDollar(sff.getOrderDollar());
		sif.setFreightTW(sff.getFreightTW());
		sif.setTransportMode(sff.getTransportMode());
		
		return sif;	   	     	
	}
	
	
	
	
	
	
	public static void main(String[] args) {
		//test
		StockFlowForm sif=new StockFlowForm();
		sif.setFlowId(new Long(-10));
		sif.setStuffNo("test1");
		sif.setSkuCode("aaaa1");
		try{
			StockFlowForm newPo=new StockFlowForm();
			BeanUtils.copyProperties(newPo, sif);
			
			System.out.println(newPo.getFlowId());
			System.out.println(newPo.getStuffNo());
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	
}
