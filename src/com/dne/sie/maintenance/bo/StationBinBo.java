package com.dne.sie.maintenance.bo;

//Java ������
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.dne.sie.common.tools.Operate;
import com.dne.sie.maintenance.form.StationBinForm;
import com.dne.sie.maintenance.queryBean.StationBinQuery;
import com.dne.sie.util.bo.CommBo;

/**
 * ��λ��Ϣ��
 *@version 1.1.5.6
 */

public class StationBinBo extends CommBo {
	private static Logger logger = Logger.getLogger(StationBinBo.class);

	private static final StationBinBo INSTANCE = new StationBinBo();
		
	private StationBinBo(){
	}
	
	public static final StationBinBo getInstance() {
	   return INSTANCE;
	}
   
   /**
	* ��λ��Ϣ��ѯ��
	* @param part StationBinForm  
	* @return ArrayList  ������������PartInfoForm������ϳɵ�String���鼯�ϡ�
	*/
   public ArrayList list(StationBinForm binQuery) throws Exception{
	   ArrayList alData = new ArrayList();
	   StationBinQuery uq = new StationBinQuery(binQuery);
	   
       List dataList = uq.doListQuery(binQuery.getFromPage(),binQuery.getToPage());
       int count=uq.doCountQuery();
	   StationBinForm pif=null;

	   for (int i=0;i<dataList.size();i++) {
		   String[] data = new String[3];
		   pif = (StationBinForm)dataList.get(i);
		   data[0] = pif.getBinCode()==null?"":pif.getBinCode();
		   data[1] = pif.getBinType()==null?"":pif.getBinType();
		   data[2] = pif.getCreateDate()==null?"":Operate.trimDate(pif.getCreateDate()).toString();
		   
		   alData.add(data);
	   }
	   alData.add(0,count+"");
	   
	   return alData;
	  }
		
 
	public List<StationBinForm> findByCode(String binCode) throws Exception{
		return this.getDao().list("from StationBinForm as pi where pi.binCode like ? and delFlag=0",binCode+"%");
	}
	
   /**
	 * ��ѯ���������Ϣ
	 * @param id  String   idΪ�����Ϣ�������
	 * @return StationBinForm
	 */ 
	public StationBinForm find(String id) {
		StationBinForm uf = new StationBinForm();
		try {
			uf = (StationBinForm)this.getDao().findById(uf.getClass(),id);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return uf;		    	
	} 
		
   
	/**
	 * ��ӵ��������Ϣ
	 * @param uf  StationBinForm   ufΪ���ݿ���س���ʵ�������PartInfoForm����
	 * @return int 1Ϊ�ɹ���-1Ϊʧ�ܡ�
	 */
	public int add(StationBinForm uf) {
		int tag=-1;
		boolean t = false;
		try {
			t = this.getDao().insert(uf);
		} catch(Exception e) {
		} finally {
		}
		if (t) {
			tag = 1;
		}
		return tag;	   	
	}
	
	public int update(StationBinForm uf) {
		int tag=-1;
		boolean t = false;
		try {
			t = this.getDao().update(uf);
		} catch(Exception e) {
		} finally {
		}
		if (t) {
			tag = 1;
		}
		return tag;	   	
	}
    
	
	/**
	 * �޸������Ϣ
	 * @param uf  StationBinForm   ufΪ���ݿ���س���ʵ�������PartInfoForm����
	 * @return int 1Ϊ�ɹ���-1Ϊʧ��
	 */
   public int modify(StationBinForm uf) {
		int tag=-1;
		boolean t = false;
		try {
			t = this.getDao().update(uf);
		} catch(Exception e) {
		} finally {
		}
		if (t) {
			tag = 1;
		}
		return tag;	   	
	}
  
	
	public boolean chkStationBin(String binCode) throws Exception {
		boolean t = false;
		String hql="select count(*) from StationBinForm as pi where pi.binCode=?";
		Long count=(Long)this.getDao().uniqueResult(hql,binCode);
		if(count==0) t=true;
		
		return t;	   	
	}
	
	public boolean delete(String binCode) throws Exception {
		binCode = binCode.replaceAll(",", "','");
		String hql="update StationBinForm as pi set pi.delFlag = 1 where pi.binCode in('"+binCode+"')";
		this.getDao().execute(hql);
		return true;
	}
	
	
	public List<String> getAllBinCodes() throws Exception {
		List<StationBinForm> list = this.getDao().list("from StationBinForm as pi where delFlag=0");
		List<String> binCodes = new ArrayList<String>();
		if(list!=null&&!list.isEmpty()){
			for(StationBinForm sbf : list){
				binCodes.add(sbf.getBinCode());
			}
		}
		return binCodes;
	}
	
	
	
}
