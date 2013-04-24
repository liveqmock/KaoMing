package com.dne.sie.maintenance.bo;

//Java ������
import java.util.List;
import java.util.ArrayList;

//Java ��չ��
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

//��������
import org.apache.struts.action.ActionForm;
import org.apache.log4j.Logger;

//�Զ�����
import com.dne.sie.common.tools.DicInit;
import com.dne.sie.common.tools.Operate;
import com.dne.sie.maintenance.form.PartInfoForm;
import com.dne.sie.maintenance.queryBean.PartInfoQuery;

import com.dne.sie.util.bo.CommBo;

/**�����Ϣ��
 *@version 1.1.5.6
 */

public class PartInfoBo extends CommBo {
	private static Logger logger = Logger.getLogger(PartInfoBo.class);

	private static final PartInfoBo INSTANCE = new PartInfoBo();
		
	private PartInfoBo(){
	}
	
	public static final PartInfoBo getInstance() {
	   return INSTANCE;
	}
   
   /**
	* �����Ϣ��ѯ��
	* @param part PartInfoForm  
	* @return ArrayList  ������������PartInfoForm������ϳɵ�String���鼯�ϡ�
	*/
   public ArrayList list(PartInfoForm part) {
	   List dataList = null;
	   ArrayList alData = new ArrayList();
	   PartInfoQuery uq = new PartInfoQuery(part);
       int count=0;
	   try {
		   dataList=uq.doListQuery(part.getFromPage(),part.getToPage());
		   count=uq.doCountQuery();
		   PartInfoForm pif=null;

		   for (int i=0;i<dataList.size();i++) {
			   String[] data = new String[8];
			   pif = (PartInfoForm)dataList.get(i);
			   data[0] = pif.getStuffNo()==null?"":pif.getStuffNo();
			   data[1] = pif.getSkuCode()==null?"":pif.getSkuCode();
			   data[2] = DicInit.getSystemName("PART_TYPE",pif.getPartType());
			   data[3] = pif.getStandard()==null?"":pif.getStandard();
			   data[4] = pif.getSkuUnit()==null?"":pif.getSkuUnit();
			   data[5] = pif.getBuyCost()==null?"":Operate.roundD(pif.getBuyCost(), 2)+"";
			   data[6] = pif.getSaleCost()==null?"":Operate.roundD(pif.getSaleCost(),2)+"";
			   data[7] = pif.getRemark()==null?"":pif.getRemark();
			   
			   alData.add(data);
		   }
		   alData.add(0,count+"");
	   } catch(Exception e) {
		   e.printStackTrace();
	   } 
	   return alData;
	  }
		
   /**
	 * �����ϺŲ�ѯ�����Ϣ
	 * @param stuffNo  �Ϻ� pk
	 * @return PartInfoForm
	 */ 
	public List findByStuff(String stuffNo) throws Exception{
		List list=this.getDao().list("from PartInfoForm as pi where pi.stuffNo like ? and pi.partType='P'","%"+stuffNo+"%");
		
		return list;		    	
	} 
	
	public List findByStuffWithStock(String stuffNo) throws Exception{
		List list=this.getDao().list("select pi from PartInfoForm as pi where pi.stuffNo like ? and pi.partType='P' and " +
			"exists (SELECT si.stuffNo FROM StockInfoForm as si where  si.stockStatus ='A' and si.stuffNo=pi.stuffNo)","%"+stuffNo+"%");
		return list;		    	
	} 
	
	
	public List findToolByStuff(String stuffNo) throws Exception{
		List list=this.getDao().list("select pi from PartInfoForm as pi where pi.stuffNo like ? and pi.partType='T'","%"+stuffNo+"%");
		return list;		    	
	} 
	
	public List findToolByStuffWithStock(String stuffNo) throws Exception{
		List list=this.getDao().list("select pi from PartInfoForm as pi where pi.stuffNo like ? and pi.partType='T' and " +
			"exists (SELECT si.stuffNo FROM StockToolsInfoForm as si where  si.stockStatus ='A' and si.stuffNo=pi.stuffNo)","%"+stuffNo+"%");
		return list;		    	
	}
	
   /**
	 * ��ѯ���������Ϣ
	 * @param id  String   idΪ�����Ϣ�������
	 * @return PartInfoForm
	 */ 
	public PartInfoForm find(String id) {
		PartInfoForm uf = new PartInfoForm();
		try {
			uf = (PartInfoForm)this.getDao().findById(uf.getClass(),id);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return uf;		    	
	} 
		
   
	/**
	 * ��ӵ��������Ϣ
	 * @param uf  PartInfoForm   ufΪ���ݿ���س���ʵ�������PartInfoForm����
	 * @return int 1Ϊ�ɹ���-1Ϊʧ�ܡ�
	 */
	public int add(PartInfoForm uf) {
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
    
	
	/**
	 * �޸������Ϣ
	 * @param uf  PartInfoForm   ufΪ���ݿ���س���ʵ�������PartInfoForm����
	 * @return int 1Ϊ�ɹ���-1Ϊʧ��
	 */
   public int modify(PartInfoForm uf) {
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
	  * ͨ�������ѯ
	  * @param partInfo PartInfoForm ͨ�������ѯform
	  * @return ArrayList ͨ�������Ϣ��ѯ��� ÿ��Ԫ��
	  */
	public ArrayList popList(PartInfoForm partInfo) {
		List dataList = null;
		ArrayList alData = new ArrayList();
		PartInfoQuery pq = new PartInfoQuery(partInfo);

		int count=0;
		try {
			dataList=pq.doListQuery(partInfo.getFromPage(),partInfo.getToPage());
			count=pq.doCountQuery();
			PartInfoForm pif=null;
			for (int i=0;i<dataList.size();i++) {
			   String[] data = new String[8];
			   pif = (PartInfoForm)dataList.get(i);
			   data[0] = pif.getStuffNo()==null?"":pif.getStuffNo();
			   data[1] = pif.getSkuCode()==null?"":pif.getSkuCode();
			   data[2] = pif.getShortCode()==null?"":pif.getShortCode();
			   data[3] = pif.getStandard()==null?"":pif.getStandard();
			   data[4] = pif.getSkuUnit()==null?"":pif.getSkuUnit();
			   data[5] = pif.getBuyCost()==null?"":Operate.toFix(pif.getBuyCost(), 2);
			   data[6] = pif.getSaleCost()==null?"":Operate.toFix(pif.getSaleCost(), 2);
			   
			   data[7] = "";
			   if("A".equals(partInfo.getStockFlag())){
				   Long avaQty = 0L;
				   if("P".equals(partInfo.getPartType())){
					   avaQty = (Long)this.getDao().uniqueResult("SELECT sum(si.skuNum) FROM StockInfoForm si " +
							   "where si.stockStatus ='A' and si.stuffNo=?",pif.getStuffNo());
				   }else if("T".equals(partInfo.getPartType())){
					   avaQty = (Long)this.getDao().uniqueResult("SELECT sum(si.skuNum) FROM StockToolsInfoForm si " +
						   		"where si.stockStatus ='A' and si.stuffNo=?",pif.getStuffNo());
				   }
				   data[7] = avaQty==null?"0":avaQty+"";
			   }
			   
			   alData.add(data);
			}
			alData.add(0,count+"");
		 } catch(Exception e) {
			e.printStackTrace();
		 }
		 return alData;
	 }
	
	public boolean chkStuffNo(String stuffNo) throws Exception {
		boolean t = false;
		String hql="select count(*) from PartInfoForm as pi where pi.stuffNo=? and pi.partType='P'";
		Long count=(Long)this.getDao().uniqueResult(hql,stuffNo);
		if(count==0) t=true;
		
		return t;	   	
	}
	

	public boolean updateStuffNo(String stuffNo,String oldStuffNo) throws Exception {
		boolean t = false;
		
		String strHql1="update PartInfoForm pi set pi.stuffNo='"+stuffNo+"' where pi.stuffNo='"+oldStuffNo+"'";
		String strHql2="update SaleDetailForm sdf set sdf.stuffNo='"+stuffNo+"' where sdf.stuffNo='"+oldStuffNo+"'";
		String strHql3="update StockInfoForm si set si.stuffNo='"+stuffNo+"' where si.stuffNo='"+oldStuffNo+"'";
		String strHql4="update PoForm po set po.stuffNo='"+stuffNo+"' where po.stuffNo='"+oldStuffNo+"'";
		
		ArrayList<String> al = new ArrayList<String>();
		al.add(strHql1);
		al.add(strHql2);
		al.add(strHql3);
		al.add(strHql4);
		
		t=this.getBatchDao().excuteBatch(al);
		
		return t;	   	
	}
	
	
}
