package com.dne.sie.reception.bo;

//Java ������
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//��������
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;

//�Զ�����
import com.dne.sie.common.tools.CommonSearch;
import com.dne.sie.common.tools.DicInit;
import com.dne.sie.common.tools.FormNumberBuilder;
import com.dne.sie.common.tools.Operate;
import com.dne.sie.reception.form.PoForm;
import com.dne.sie.reception.form.SaleInfoForm;
import com.dne.sie.reception.queryBean.PartPoQuery;
import com.dne.sie.util.bo.CommBo;
import com.dne.sie.util.hibernate.AllDefaultDaoImp;
import com.dne.sie.util.query.QueryParameter;

public class PartPoBo extends CommBo {
	private static Logger logger = Logger.getLogger(PartPoBo.class);

	private static final PartPoBo INSTANCE = new PartPoBo();
		
	private PartPoBo(){
	}
	
	public static final PartPoBo getInstance() {
	   return INSTANCE;
	}
	

   /**
	 * �б��ѯƴװ
	 * @param PoForm ��ѯ����
	 * @return ArrayList ��ѯ���
	 */
   public ArrayList planList(PoForm dept) throws Exception {
		List dataList = new ArrayList();
		ArrayList alData = new ArrayList();
		PartPoQuery dq = new PartPoQuery(dept);
		int count=0;
		
		dataList=dq.doListQuery(dept.getFromPage(),dept.getToPage());
		count=dq.doCountQuery();
		CommonSearch cs = CommonSearch.getInstance();
		for (int i=0;i<dataList.size();i++) {
			String[] data = new String[19];
			PoForm df = (PoForm)dataList.get(i);
			
			data[0] = df.getPoNo().toString();
			data[1] = df.getSaleNo();
			data[2] = cs.getCustomerName(df.getCustomerId());
			data[3] = df.getStuffNo();
			data[4] = df.getSkuCode();
			data[5] = df.getPerQuote()+"";	//PER_QUOTE��Ԫ���ۣ�ʵ��RMB����(PER_COST)���ջ�ʱ��ȷ��
			data[6] = df.getOrderNum()+"";
			data[7] = cs.findUserNameByUserId(df.getCreateBy());	//���ʵ�ʵľ�����
			data[8] = df.getCreateDate().toLocaleString();
			data[9] = DicInit.getSystemName("ORDER_STATUS", df.getOrderStatus());
			data[10] = DicInit.getSystemName("ORDER_TYPE", df.getOrderType());
			data[11] = df.getOrderNo();
			data[12] = df.getRemark();
			data[13] = df.getShippingAddress();
			data[14] = df.getOrderStatus();
			data[15] = df.getFactoryName();
			data[16] = DicInit.getSystemName("TRANSPORT_MODE", df.getTransportMode());
			data[17] = df.getOrderType();
			data[18] = df.getWarrantyType();
			alData.add(data);
		}
		alData.add(0,count+"");
		
		//��������嵥
		if("allList".equals(dept.getFlag())){
			//��ѯ����еķ����ܺ�
			double fee=Operate.roundD(dq.doSumQuery(),2);
			
			alData.add(1,fee+"");
		}
		
		return alData;
	}
   

	/**
	 * ����id��ѯPoForm��Ϣ
	 * @param String ��¼pk
	 * @return PoForm
	 */
	public PoForm findById(Long id) throws Exception {
		PoForm  rf = (PoForm)this.getDao().findById(PoForm.class,id);
		
		return rf;		    	
	} 

	/**
	 * POȷ��
	 * @param checked ids
	 * @return int 1Ϊ�ɹ���-1Ϊʧ��
	 */
   	public static synchronized int sendPo(String ids,Long userId,String orderNo,
   			String remark,String factoryId,String factoryName,String transportMode) throws Exception{
		int tag=-1;
		AllDefaultDaoImp adi=new AllDefaultDaoImp();	
		
		String strHql="from PoForm as pf where pf.poNo in ( "+ids+" ) and pf.orderStatus='A'";
		List<PoForm> planList=adi.list(strHql);
		List<PoForm> sendList = new ArrayList<PoForm>();
		for(int i=0;planList!=null&&i<planList.size();i++){
			PoForm po = planList.get(i);
			po.setOrderStatus("B");	//������
			po.setOrderNo(orderNo);
			po.setUpdateBy(userId);
			po.setUpdateDate(new Date());
			po.setRemark(remark);
			po.setFactoryId(factoryId);
			po.setFactoryName(factoryName);
			po.setTransportMode(transportMode);
			
			sendList.add(po);
			
		}
		if(adi.updateBatch(sendList)){
			tag=1;
		}
		
		return tag;	   	
   	}
   	
   	/**
   	 * POȡ��
   	 * @param ids
   	 * @param userId
   	 * @param orderNo
   	 * @param remark
   	 * @return
   	 * @throws Exception
   	 */
   	public static synchronized int cancelPo(String ids,Long userId,String remark) throws Exception{
		int tag=-1;
		AllDefaultDaoImp adi=new AllDefaultDaoImp();	
		
		String strHql="from PoForm as pf where pf.poNo in ( "+ids+" ) and pf.orderStatus='A'";
		List<PoForm> planList=adi.list(strHql);
		List<PoForm> cancelList = new ArrayList<PoForm>();
		for(int i=0;planList!=null&&i<planList.size();i++){
			PoForm po = planList.get(i);
			po.setOrderStatus("X");	//����ȡ��
			po.setUpdateBy(userId);
			po.setUpdateDate(new Date());
			po.setRemark(remark);
			
			cancelList.add(po);
			
		}
		if(adi.updateBatch(cancelList)){
			tag=1;
		}
		
		return tag;	   	
   	}
   	

	/**
	 * ��ȡ������̨Ӧ����ͱ�����̨Ӧ����
	 * @return Float Ӧ����
	 */
	 public Float[] getPoPayAmounts() throws Exception {
		Float allPayAmount[]=new Float[2];
		//������̨Ӧ����
		String sqlYear="select sum(pf.perQuote*pf.orderNum) from PoForm as pf where year(pf.createDate)=year(now()) " +
				"and pf.orderStatus!='X'" ;
		//������̨Ӧ����
		String sqlMonth="select sum(pf.perQuote*pf.orderNum) from PoForm as pf where MONTH(pf.createDate)=MONTH(NOW()) " +
				"and year(pf.createDate)=year(now()) and pf.orderStatus!='X'" ;
		
		Double yearPay=(Double)this.getDao().uniqueResult(sqlYear);
		Double monthPay=(Double)this.getDao().uniqueResult(sqlMonth);
		
		if(yearPay!=null) allPayAmount[0]=Operate.roundF(yearPay.floatValue(),2);
		else allPayAmount[0]=0f;
		if(monthPay!=null) allPayAmount[1]=Operate.roundF(monthPay.floatValue(),2);
		else allPayAmount[1]=0f;
	
		return allPayAmount;
	 }
	 
	 
	 

	/**
	 * ���ڶ�������
	 * @param PoForm
	 * @return int 1Ϊ�ɹ���-1Ϊʧ��
	 */
   	public int manualPlanAdd(PoForm pof) throws Exception{
		int tag=-1;
		
		if(this.getDao().insert(pof)){
			tag=1;
		}
		
		return tag;	   	
   	}
   	

	/**
	 * ���ڶ���ɾ��
	 * @param checked ids
	 * @return int 1Ϊ�ɹ���-1Ϊʧ��
	 */
   	public int manuPoDel(String ids) throws Exception{
		int tag=this.getDao().execute("delete from PoForm as pof where pof.poNo in ("+ids+")");
		
		return tag;
	}

	/**
	 * ������ȡ��
	 * @param checked ids
	 * @return int 1Ϊ�ɹ���-1Ϊʧ��
	 */
   	public int orderCancel(String ids,Long userId) throws Exception{
		int tag=this.getDao().execute("update PoForm as pof set pof.orderStatus='X',updateDate=sysdate()," +
				"updateBy="+userId+" where pof.poNo in ("+ids+")");
		
		return tag;
	}

   /**
	 * ��ȡ������������
	 * @param String 
	 * @return Float
	 */
   public List<String> getOrderMonthList() {
	   return Operate.getMonthList();
   }
   

	/**
	 * ��������ӡ
	 * @param poNo 
	 * @return 
	 */
	 public ArrayList poFormPrint(String orderNo) throws Exception {
		
		String strHql="from PoForm as pf where pf.orderNo = '"+orderNo.replaceAll(",", "','")+"' order by pf.stuffNo";
		
		ArrayList poList = (ArrayList)this.getDao().list(strHql);
		
		return poList;
	 }
   	
	 
 
	 public static void main(String[] args) {
		try{
			ArrayList saleList = PartPoBo.getInstance().poFormPrint("aaa");
			
			System.out.println(saleList.size());
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
