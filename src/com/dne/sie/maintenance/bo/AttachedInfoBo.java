package com.dne.sie.maintenance.bo;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;

import com.dne.sie.common.tools.Operate;
import com.dne.sie.maintenance.form.AttachedInfoForm;
import com.dne.sie.util.bo.CommBo;
import com.dne.sie.util.hibernate.AllDefaultDaoImp;
import com.dne.sie.util.query.QueryParameter;

public class AttachedInfoBo  extends CommBo {
	private static Logger logger = Logger.getLogger(AttachedInfoBo.class);

	private static final AttachedInfoBo INSTANCE = new AttachedInfoBo();
		
	private AttachedInfoBo(){
	}
	
	public static final AttachedInfoBo getInstance() {
	   return INSTANCE;
	}
	

	   /**
		 * ������Ϣ��ѯ
		 * @param id
		 * @return ArrayList ��ѯ���
		 */
	   public ArrayList getAffixList(Long foreignId) throws Exception {
		   
		   String strHql="from AttachedInfoForm as af where af.foreignId= :foreignId";
			AllDefaultDaoImp dao = new AllDefaultDaoImp();
			ArrayList<QueryParameter> paramList = new ArrayList<QueryParameter>();
			QueryParameter param = new QueryParameter();
			param.setName("foreignId");
			param.setValue(foreignId);
			param.setHbType(Hibernate.LONG);
			paramList.add(param);
			List aList = dao.parameterQuery(strHql, paramList);
			
			ArrayList<String[]> affixList=new ArrayList<String[]>();
			for(int i=0;i<aList.size();i++){
				AttachedInfoForm af = (AttachedInfoForm) aList.get(i);
				
				String[] temp = new String[3];
				temp[0] = af.getAttachedId()+"";
				temp[1] = af.getAttachedName();
				temp[2] = af.getSaveAttachedName();

				affixList.add(temp);
			}
		   return affixList;
	   }
	
	   /**
		 * ���Ҹ�����Ϣ������
		 * @param id Long
		 * @return AttachedInfoForm 
		 */
		public AttachedInfoForm find(Long id) throws Exception {
			
			AttachedInfoForm uf = (AttachedInfoForm)this.getDao().findById(AttachedInfoForm.class,id);
			
			return uf;		    	
		} 
	
	/**
	 * ��Ӹ�����Ϣ������
	 * @param uf  AttachedInfoForm  uf��ʵ�������AttachedInfoForm����
	 * @return int 1Ϊ�ɹ���-1Ϊʧ�ܡ�
	 */
	public int add(AttachedInfoForm uf) throws Exception {
		int tag=-1;
		boolean t = this.getDao().insert(uf);
		
		if (t) {
			tag = 1;
		}
		return tag;	   	
	}
	
	/**
	 * ɾ��������Ϣ���ļ�
	 * @param attacheId  String  attacheIdΪ������Ϣid��  
	 * @param flag String
	 * @return boolean false���ɹ�,true�ɹ���
	 */	
	public boolean fileDel(String attacheId,String flag) {
		boolean tag=false;
			
		try {
			AttachedInfoForm aif=find(new Long(attacheId));
			String affixName="";
			if(flag.equals("virtualName")) affixName=aif.getSaveAttachedName();
			else if(flag.equals("trueName")) affixName=aif.getAttachedName();
			
			if(this.getDao().delete(aif)){ 
				String path=Operate.getSysPath()+"affix/fileInfo/"+affixName;
				
				String[] filePath={path};
				if(Operate.fileDelete(filePath)) tag=true; 
			}


				
		} catch(Exception e) {
			e.printStackTrace();
		}
		return tag;	   	
	}

}
