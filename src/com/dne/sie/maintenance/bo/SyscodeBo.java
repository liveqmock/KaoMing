package com.dne.sie.maintenance.bo;

//Java ������
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

//Java ��չ��

//��������
//import org.apache.log4j.Logger;

//�Զ�����
import com.dne.sie.common.tools.DicInit;
import com.dne.sie.maintenance.form.TsSystemCodeForm;
import com.dne.sie.maintenance.queryBean.SyscodeQuery;

import com.dne.sie.util.bo.CommBo;

/**�ֵ����Ϣ��
 *@version 1.1.5.6
 */

public class SyscodeBo extends CommBo {
	//private static Logger logger = Logger.getLogger(SyscodeBo.class);

	private static final SyscodeBo INSTANCE = new SyscodeBo();
		
	private SyscodeBo(){
	}
	
	public static final SyscodeBo getInstance() {
	   return INSTANCE;
	}
   
   /**
	* �ֵ����Ϣ��ѯ��
	* @param part TsSystemCodeForm  
	* @return ArrayList  ������������TsSystemCodeForm������ϳɵ�String���鼯�ϡ�
	*/
   public ArrayList list(TsSystemCodeForm part) {
	   List dataList = null;
	   ArrayList alData = new ArrayList();
	   SyscodeQuery uq = new SyscodeQuery(part);
       int count=0;
	   try {
		   dataList=uq.doListQuery();
		   //count=uq.doCountQuery();
		   count=dataList.size();
		   TsSystemCodeForm pif=null;

		   for (int i=0;i<dataList.size();i++) {
			   String[] data = new String[5];
			   pif = (TsSystemCodeForm)dataList.get(i);
			   data[0] = pif.getSystemId().toString();
			   data[1] = pif.getSystemDesc()==null?"":pif.getSystemDesc();
			   data[2] = pif.getSystemType()==null?"":pif.getSystemType();
			   data[3] = pif.getSystemCode()==null?"":pif.getSystemCode();
			   data[4] = pif.getSystemName()==null?"":pif.getSystemName();
			  
			   alData.add(data);
		   }
		   alData.add(0,count+"");
	   } catch(Exception e) {
		   e.printStackTrace();
	   } 
	   return alData;
	  }
		
   
   /**
	 * ��ѯ�����ֵ����Ϣ
	 * @param id  String   idΪ�ֵ����Ϣ�������
	 * @return TsSystemCodeForm
	 */ 
	public TsSystemCodeForm find(String id) throws Exception{
		return  (TsSystemCodeForm)this.getDao().findById(TsSystemCodeForm.class,new Long(id));
		    	
	} 
		
   
	/**
	 * ��ӵ����ֵ����Ϣ
	 * @param uf  TsSystemCodeForm   ufΪ���ݿ���س���ʵ�������TsSystemCodeForm����
	 * @return int 1Ϊ�ɹ���-1Ϊʧ�ܡ�
	 */
	public int add(TsSystemCodeForm uf) throws Exception{
		int tag=-1;
		if (this.getDao().insert(uf)) {
			tag = 1;
			DicInit.SYS_CODE_MAP=new HashMap();
			DicInit dic=new DicInit();
			dic.makeSystemCode();
		}
		return tag;	   	
	}
    
	
	/**
	 * �޸��ֵ����Ϣ
	 * @param uf  TsSystemCodeForm   ufΪ���ݿ���س���ʵ�������TsSystemCodeForm����
	 * @return int 1Ϊ�ɹ���-1Ϊʧ��
	 */
   public int modify(TsSystemCodeForm uf) throws Exception{
		int tag=-1;
		
		if (this.getDao().update(uf)) {
			tag = 1;
			DicInit.SYS_CODE_MAP=new HashMap();	
			DicInit dic=new DicInit();
			dic.makeSystemCode();
		}
		return tag;	   	
	}
	

	/**
	 * ɾ���ֵ����Ϣ
	 * @param uf  TsSystemCodeForm   ufΪ���ݿ���س���ʵ�������TsSystemCodeForm����
	 * @return int 1Ϊ�ɹ���-1Ϊʧ��
	 */
  public int delete(String ids) throws Exception{
	  int tag=this.getDao().execute("delete from TsSystemCodeForm as sc where sc.systemId in ("+ids+")");
	  if(tag>0){
	  	DicInit.SYS_CODE_MAP=new HashMap();	 
	  	DicInit dic=new DicInit();
		dic.makeSystemCode();
	  }
	return tag;
	}
	
}
