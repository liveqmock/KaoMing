package com.dne.sie.maintenance.bo;

//Java ������
import java.util.List;
import java.util.ArrayList;

//Java ��չ��

//��������
//import org.apache.log4j.Logger;

//�Զ�����
import com.dne.sie.maintenance.form.FactoryInfoForm;
import com.dne.sie.maintenance.queryBean.FactoryInfoQuery;

import com.dne.sie.util.bo.CommBo;

/**���̱���Ϣ��
 *@version 1.1.5.6
 */

public class FactoryInfoBo extends CommBo {
	//private static Logger logger = Logger.getLogger(FactoryInfoBo.class);

	private static final FactoryInfoBo INSTANCE = new FactoryInfoBo();
		
	private FactoryInfoBo(){
	}
	
	public static final FactoryInfoBo getInstance() {
	   return INSTANCE;
	}
   
   /**
	* ���̱���Ϣ��ѯ��
	* @param part FactoryInfoForm  
	* @return ArrayList  ������������TsSystemCodeForm������ϳɵ�String���鼯�ϡ�
	*/
   public ArrayList list(FactoryInfoForm part) {
	   List dataList = null;
	   ArrayList alData = new ArrayList();
	   FactoryInfoQuery uq = new FactoryInfoQuery(part);
       int count=0;
	   try {
		   dataList=uq.doListQuery(part.getFromPage(),part.getToPage());
		   count=uq.doCountQuery();
		   FactoryInfoForm pif=null;

		   for (int i=0;i<dataList.size();i++) {
			   String[] data = new String[9];
			   pif = (FactoryInfoForm)dataList.get(i);
			   data[0] = pif.getFactoryId();
			   data[1] = pif.getFactoryName();
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
	 * ��ѯ�������̱���Ϣ
	 * @param id  String   idΪ���̱���Ϣ�������
	 * @return FactoryInfoForm
	 */ 
	public FactoryInfoForm find(String id) throws Exception{
		return  (FactoryInfoForm)this.getDao().findById(FactoryInfoForm.class,id);
		    	
	} 
		
   
	/**
	 * ��ӵ������̱���Ϣ
	 * @param uf  FactoryInfoForm 
	 * @return int 1Ϊ�ɹ���-1Ϊʧ�ܡ�
	 */
	public int add(FactoryInfoForm uf) throws Exception{
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
	 * �޸ĳ��̱���Ϣ
	 * @param uf  FactoryInfoForm 
	 * @return int 1Ϊ�ɹ���-1Ϊʧ��
	 */
   public int modify(FactoryInfoForm uf) throws Exception{
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
	 * �߼�ɾ�����̱���Ϣ
	 * @param uf  ���̱�
	 * @return int 1Ϊ�ɹ���-1Ϊʧ��
	 */
   public int delete(String ids) throws Exception{
	  int tag=-1;
	  try{
		  tag=this.getDao().execute("update from FactoryInfoForm as sc " +
	  		"set sc.delFlag=1 where sc.factoryId in ('"+ids.replaceAll(",", "','")+"')");
	  } catch(Exception e) {
		   e.printStackTrace();
	  } 
	  return tag;
	}

	/**
	 * У�鳧��id(PK)�Ƿ����
	 * @param String ������û�id
	 * @return ���û�id�Ƿ��������
	 */
	public boolean chkFactoryId(String id) {
		boolean retBoo = false;
		
		try {
			Object obj=this.getDao().uniqueResult("select count(uf) from FactoryInfoForm as uf " +
					"where uf.factoryId='"+id.toUpperCase()+"'");
			int count=((Long)obj).intValue();
			
			if(count==0) retBoo=true;
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return retBoo;		    	
	} 
	

	/**
	 * ��ѯ̨�����ѯ�۵�ַ��Ϣ
	 * @param 
	 * @return ������Ϣ
	 */
	public String[] getKmInfo(String custId) {
		String[] kmInfo=new String[3];
		
		try {
			Object[] obj=(Object[])this.getDao().uniqueResult("select ci.factoryName," +
					"ci.fax,ci.linkman from FactoryInfoForm as ci where ci.factoryId='"+custId+"'");
			if(obj!=null){
				kmInfo[0]=obj[0]==null?"":(String)obj[0];
				kmInfo[1]=obj[1]==null?"":(String)obj[1];
				kmInfo[2]=obj[2]==null?"":(String)obj[2];
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return kmInfo;		    	
	} 
	
	
}
