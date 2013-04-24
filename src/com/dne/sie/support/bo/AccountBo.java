package com.dne.sie.support.bo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;

import com.dne.sie.common.tools.Operate;
import com.dne.sie.support.form.SubjectTreeForm;
import com.dne.sie.util.bo.CommBo;
import com.dne.sie.util.query.QueryParameter;

/**
 * ���˵�BO������
 * @author xt
 * @version 1.1.5.6
 * @see AccountBo.java <br>
 */
public class AccountBo extends CommBo{
	private static Logger logger = Logger.getLogger(AccountBo.class);

	private static final AccountBo INSTANCE = new AccountBo();
		
	private AccountBo(){
	}
	
	public static final AccountBo getInstance() {
	   return INSTANCE;
	}

	/**
	 * ȡ�����˵�����Ϣ
	 * @param String ���˵�id��"all"��ʾȫ��
	 * @return ��װjs����List
	 */
	 public String writeTree(String subId) throws Exception{
		String strRet="";
		String strLink="";
		
		String where =" where delFlag=0 ";
		if(!"all".equals(subId)){
			where=" and parentId="+subId;
		}
		ArrayList dataList=(ArrayList)this.getDao().list("from SubjectTreeForm as pojos "+where+" order by pojos.parentId,pojos.orderId ");
//		System.out.println("---dataList="+dataList.size());
		
		for(int i=0;i<dataList.size();i++){
			SubjectTreeForm st=(SubjectTreeForm)dataList.get(i);
			String strId=st.getSubjectId()+"";		
			String strName=st.getSubjectName();	
			if(st.getReportFlag()==1){
				strName+=" *";
			}
			String parentId=st.getParentId()+"";	
			String strTitle=transferLayer(st.getLayer().toString());
			
			if(i!=0) strLink="accountAction.do?method=subDetail&id="+strId;
			
			strRet+="data["+i+"] = new Array('"+strId+"','"+strName+"','"+strId+"','"+parentId+"','"+strLink+"','','',false,'main','"+strTitle+"');\n";
			
		}
		
		return strRet;
	 	
	 }
	 

	/**
	 * ����id��ѯ�ÿ�Ŀ��Ϣ
	 * @param String ��Ŀ��¼pk
	 * @return SubjectTreeForm
	 */
	public SubjectTreeForm findById(Long id) {
		SubjectTreeForm df = null;
		try {
			df = (SubjectTreeForm)this.getDao().findById(SubjectTreeForm.class,id);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return df;		    	
	} 	
	

	/**
	 * ����һ����Ŀ��Ϣ
	 * @param SubjectTreeForm ��Ŀform
	 * @return �Ƿ�ɹ�
	 */
	public int add(SubjectTreeForm stf) {
		int tag=-1;
		try {
			String orderSql="select max(st.orderId) from SubjectTreeForm st where st.parentId= :parentId";
			QueryParameter param = new QueryParameter();
			param.setName("parentId");
			param.setValue(stf.getParentId());
			param.setHbType(Hibernate.LONG);
			List al=this.getDao().parameterQuery(orderSql, param);
			if(al!=null && !al.isEmpty()){
				Long ord=(Long)al.get(0);
				stf.setOrderId(ord==null?0:ord + 1);
			}else{
				stf.setOrderId(new Long(1));
			}
			
			if(this.getDao().insert(stf)){
				tag=1;
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return tag;		    	
	} 	
	
	/**
	 * �޸�һ����Ŀ��Ϣ
	 * @param SubjectTreeForm ��Ŀform
	 * @return �Ƿ�ɹ�
	 */
	public int modify(SubjectTreeForm stf) {
		int tag=-1;
		try {
			if(this.getDao().update(stf)){
				tag=1;
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return tag;		    	
	} 	

	/**
	 * ɾ��һ����Ŀ��Ϣ������Ϣ(�߼�ɾ��)
	 * @param Long ��Ŀid
	 * @return �Ƿ�ɹ�
	 */
	public int deleteSubject(Long subId) {
		int tag=-1;
		try {
			List<SubjectTreeForm> subList=this.findChild(subId,0);
			subList.add(this.findById(subId));
			StringBuffer sb = new StringBuffer();
			for(int i=0;i<subList.size();i++){
				SubjectTreeForm stf = subList.get(i);
				if(i==0) sb.append(stf.getSubjectId());
				else sb.append(",").append(stf.getSubjectId());
			}
			String updateHql="update SubjectTreeForm set delFlag=1 where subjectId in("+sb+")";
			
			tag=this.getDao().execute(updateHql);
				
		} catch(Exception e) {
			e.printStackTrace();
		}
		return tag;		    	
	} 	
	
	
	/**
	 * �޸�ĳ��֦˳��
	 * @param Long ��Ŀid
	 */
	public int circleTree(Long subId,String flag) {
		int tag=1;
		try {
			SubjectTreeForm st = this.findById(subId);
			String searchSql=null;
			if("up".equals(flag)){
				searchSql="from SubjectTreeForm as st where st.delFlag=0 and " +
					"st.parentId= :parentId and st.orderId< :orderId order by st.orderId desc";
			}else if("down".equals(flag)){
				searchSql="from SubjectTreeForm as st where st.delFlag=0 and " +
					"st.parentId= :parentId and st.orderId> :orderId order by st.orderId";
			}
			
			ArrayList<QueryParameter> paramList = new ArrayList<QueryParameter>();
			QueryParameter param = new QueryParameter();
			param.setName("parentId");
			param.setValue(st.getParentId());
			param.setHbType(Hibernate.LONG);
			paramList.add(param);
			QueryParameter param2 = new QueryParameter();
			param2.setName("orderId");
			param2.setValue(st.getOrderId());
			param2.setHbType(Hibernate.LONG);
			paramList.add(param2);
			
			ArrayList dataList=(ArrayList)this.getDao().parameterQuery(searchSql,paramList);
			if(dataList!=null && !dataList.isEmpty()){
				SubjectTreeForm stObj = (SubjectTreeForm)dataList.get(0);
				
				ArrayList updateList = new ArrayList();
				Long objOrder = stObj.getOrderId();
				stObj.setOrderId(st.getOrderId());
				st.setOrderId(objOrder);
				
				updateList.add(st);
				updateList.add(stObj);
				
				this.getBatchDao().updateBatch(updateList);
				
			}
		} catch(Exception e) {
			tag=-1;
			e.printStackTrace();
		}
		return tag;    	
	} 	

  /**
	* ����ĳ��Ŀid��ѯ���������Ŀ��Ϣ
	* @param Long pk��int ���ݹ���
	* @return List �����Ŀform����
	*/
	private ArrayList<SubjectTreeForm> findChild(Long subId,int childNum) {
		ArrayList<SubjectTreeForm> childList =new ArrayList<SubjectTreeForm>();
		
		try{
			
			ArrayList dataList=(ArrayList)this.getDao().list("from SubjectTreeForm as df "+
				" where df.delFlag=0 and df.parentId="+subId);
			
			if(dataList!=null&&dataList.size()!=0){
				childNum++;
				
				for(int i=0;i<dataList.size();i++){
					SubjectTreeForm df=(SubjectTreeForm)dataList.get(i);
					if(childNum<50){
						childList=this.findChild(df.getSubjectId(),childNum);
						childList.addAll(0,dataList);
					}else{
						throw new Exception("~~~~~~~findChild  childNum="+childNum);
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}	 
		return childList;	
	} 

	/**
	 * У��ÿ�Ŀ�����Ƿ����
	 * @param String ��Ŀ��¼pk����Ŀ����
	 * @return true-����
	 */
	public boolean chkSubName(String subId,String subName) {
		boolean booRet=false;
		try {
			if(subId==null){	//add
				Long count=(Long)this.getDao().uniqueResult("select count(*) " +
					"from SubjectTreeForm st where st.delFlag=0 and st.subjectName='"+subName+"'");
				if(count>0) booRet=true;
				
			}else{	//modify
				Long count=(Long)this.getDao().uniqueResult("select count(*) " +
					"from SubjectTreeForm st where st.delFlag=0 and st.subjectName='"+subName+"' " +
					" and st.subjectId!="+subId);
				if(count>0) booRet=true;
				
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return booRet;
	} 	
	
	public String getReportStatIds() throws Exception{
		String allSubIds=null;
		String strHql="select st.subjectId from SubjectTreeForm st where st.reportFlag=1 and st.delFlag=0";
		ArrayList al = (ArrayList)this.getDao().list(strHql);
		HashSet<Long> hsAll = new HashSet<Long>();
		for(int i=0;al!=null&&i<al.size();i++){
			Long temp=(Long)al.get(i);
			if(!hsAll.contains(temp)){
				hsAll.addAll(this.getAllSubIds(temp));
			}
		}
		allSubIds=Operate.setToString(hsAll);
		
		return allSubIds;
	}
	
	public boolean checkFather(Long subId){
		boolean isFa=false;
		
		return isFa;
	}
	
	public HashSet<Long> findFather(Long subId,int childNum) {
		HashSet<Long> hsFa=new HashSet<Long>();
		
		try{
			
			Long parentId=(Long)this.getDao().uniqueResult("select df.parentId from SubjectTreeForm as df "+
				" where df.subjectId="+subId);
			
			hsFa.add(parentId);
			
			if(parentId>0){
				childNum++;
				
				if(childNum<50){
					hsFa.addAll(this.findFather(parentId,childNum));
				}else{
					throw new Exception("~~~~~~~findFather  faNum="+childNum);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}	 
		return hsFa;	
	} 
	

	public Long findChkFather(Long subId,int childNum) {
		Long fId=null;
		try{
			
			ArrayList al=(ArrayList)this.getDao().list("select df.parentId,df.reportFlag " +
					"from SubjectTreeForm as df where df.subjectId="+subId);
			Object[] obj=(Object[])al.get(0);
			if((Integer)obj[1]==1){
				fId = subId;
				if(childNum>0)return fId;
			}
			
			if((Long)obj[0]>0){
				childNum++;
				
				if(childNum<50){
					fId=this.findChkFather((Long)obj[0],childNum);
				}else{
					throw new Exception("~~~~~~~findFather  faNum="+childNum);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}	 
		return fId;
	} 
	
	public HashSet<Long> getAllSubIds(Long id) throws Exception{
		
		HashSet<Long> hs = new HashSet<Long>();
		hs.add(id);
		ArrayList<SubjectTreeForm> childList = this.findChild(id, 0);
		for(int i=0;i<childList.size();i++){
			Long subId=childList.get(i).getSubjectId();
			hs.add(subId);
		}
		
		return hs;
	}
	
		
	 
	 public static String transferLayer(String layer){
		 return layer+" ����Ŀ";
	 }
	 
	 
	 public static void main(String[] args) {
		 try{
			 AccountBo ab = AccountBo.getInstance();
//			 String xx=ab.getReportStatIds();
//			 System.out.println(xx);
			 
			 Long xx=ab.findChkFather(36L,0);
			 System.out.println("xx="+xx);
			 
		 } catch(Exception e) {
			e.printStackTrace();
		 }	
	}

}
