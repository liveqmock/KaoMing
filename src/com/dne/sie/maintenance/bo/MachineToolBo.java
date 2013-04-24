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
	* �ͻ�����Ϣ��ѯ��
	* @param part MachineToolForm  
	* @return ArrayList  ������������TsSystemCodeForm������ϳɵ�String���鼯�ϡ�
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
	 * ��ѯ�����ͻ�����Ϣ
	 * @param id  String   idΪ�ͻ�����Ϣ�������
	 * @return MachineToolForm
	 */ 
	public MachineToolForm find(Long id) throws Exception{
		MachineToolForm mtf = (MachineToolForm)this.getDao().findById(MachineToolForm.class,id);
		mtf.setPurchaseDateStr(Operate.trimDate(mtf.getPurchaseDate()));
		mtf.setExtendedWarrantyDateStr(Operate.trimDate(mtf.getExtendedWarrantyDate()));
		return  mtf;
		    	
	} 
		
   
	/**
	 * ��ӵ����ͻ�����Ϣ
	 * @param uf  MachineToolForm 
	 * @return int 1Ϊ�ɹ���-1Ϊʧ�ܡ�
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
	 * �޸Ŀͻ�����Ϣ
	 * @param uf  MachineToolForm 
	 * @return int 1Ϊ�ɹ���-1Ϊʧ��
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
	 * �߼�ɾ���ͻ�����Ϣ
	 * @param uf  �ͻ���
	 * @return int 1Ϊ�ɹ���-1Ϊʧ��
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
	 * У���������Ƿ����
	 * @param machineId,serialNo
	 * @return true ������
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
		String strHql="select modelCode,serialNo,warrantyCardNo,purchaseDate,extendedWarrantyDate " +
				"from MachineToolForm as mt where mt.serialNo like ?";
		
		return this.getDao().list(strHql,"%"+serialNo+"%");
	}
	

}
