package com.dne.sie.support.bo;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;

import com.dne.sie.common.dbo.DBOperation;
import com.dne.sie.common.tools.DicInit;
import com.dne.sie.common.tools.FormNumberBuilder;
import com.dne.sie.common.tools.Operate;
import com.dne.sie.support.form.AccountItemForm;
import com.dne.sie.support.form.SubjectTreeForm;
import com.dne.sie.support.queryBean.AccountItemQuery;
import com.dne.sie.util.bo.CommBo;
import com.dne.sie.util.query.QueryParameter;

public class AccountItemBo extends CommBo{
	private static Logger logger = Logger.getLogger(AccountItemBo.class);

	private static final AccountItemBo INSTANCE = new AccountItemBo();
		
	private AccountItemBo(){
	}
	
	public static final AccountItemBo getInstance() {
	   return INSTANCE;
	}
	

	   /**
		* 费用表信息查询。
		* @param part AccountItemForm  
		* @return ArrayList  
		*/
	   public ArrayList accountList(AccountItemForm part) {
		   List dataList = null;
		   ArrayList alData = new ArrayList();
		   AccountItemQuery uq = new AccountItemQuery(part);
	       int count=0;
		   try {
			   dataList=uq.doListQuery(part.getFromPage(),part.getToPage());
			   count=uq.doCountQuery();
			   AccountItemForm pif=null;

			   for (int i=0;i<dataList.size();i++) {
				   String[] data = new String[11];
				   pif = (AccountItemForm)dataList.get(i);
				   data[0] = pif.getAccountId().toString();
				   data[1] = pif.getVoucherNo();
				   data[2] = pif.getFeeDate().toString();
				   data[3] = pif.getSubjectName();
				   data[4] = pif.getEmployeeName();
				   data[5] = pif.getPlace();
				   data[6] = pif.getMoney()+"";
				   data[7] = DicInit.getSystemName("FEE_PAY_TYPE", pif.getPayType());
				   data[8] = pif.getSummary();
				   data[9] = pif.getSubjectAllName();
				   data[10] = pif.getCustomerName();
				   
				   alData.add(data);
			   }
			   alData.add(0,count+"");
		   } catch(Exception e) {
			   e.printStackTrace();
		   } 
		   return alData;
		  }
			
	   
	   /**
		 * 查询单条费用表信息
		 * @param id  String   id为费用表信息表的主键
		 * @return AccountItemForm
		 */ 
		public AccountItemForm find(Long id) throws Exception{
			return  (AccountItemForm)this.getDao().findById(AccountItemForm.class,id);
			    	
		} 
			
	   
		/**
		 * 添加单条费用表信息
		 * @param uf  AccountItemForm 
		 * @return int 1为成功，-1为失败。
		 */
		public int add(AccountItemForm uf) throws Exception{
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
		 * 修改费用表信息
		 * @param uf  AccountItemForm 
		 * @return int 1为成功，-1为失败
		 */
	   public int modify(AccountItemForm uf) throws Exception{
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
		 * 删除费用表信息
		 * @param uf  费用表pk
		 * @return int 1为成功，-1为失败
		 */
	   public int delete(String ids) throws Exception{
		  int tag=-1;
		  try{
			  tag=this.getDao().execute("delete from AccountItemForm as sc " +
		  		"where sc.accountId in ("+ids+")");
			  //删除后凭证号回滚，保持连续的号码
			  if(tag>0){
				  List aiList = DBOperation.select("select max(ai.voucher_no),CONCAT('V',LEFT(ai.pay_Type,1))," +
				  	" extract(year_month from ai.create_Date) from td_account_info as ai " +
				  	" where extract(year_month from ai.create_Date)>=extract(year_month from sysdate())" +
				  	" group by LEFT(ai.pay_Type,1) ");
				  for(int i=0;aiList!=null&&i<aiList.size();i++){
					  String[] obj = (String[])aiList.get(i);

					  this.getDao().execute("update FormNumber as fn set fn.formSeq="+obj[0]+
					    " where fn.formDate='"+obj[2]+"' and fn.formType='"+obj[1]+"'");
				  }
				  
			  }
			  
		  } catch(Exception e) {
			   e.printStackTrace();
		  } 
		  return tag;
		}
	   

	/**
	 * 取出对账单树信息
	 * @param String 对账单id，"all"表示全部
	 * @return 封装js树的List
	 */
	 public String writeTree(String subId) throws Exception{
		String strRet="";
		
		String where =" where delFlag=0 ";
		
		ArrayList dataList=(ArrayList)this.getDao().list("from SubjectTreeForm as pojos "+where+" order by pojos.parentId,pojos.orderId ");
		Long tmpParentId=new Long(0);
		String allName=null;
		for(int i=0;i<dataList.size();i++){
			SubjectTreeForm st=(SubjectTreeForm)dataList.get(i);
			String strId=st.getSubjectId()+"";		
			String strName=st.getSubjectName();		
			String parentId=st.getParentId()+"";	

			if(st.getReportFlag()==1){
				strName+=" *";
			}
			if(tmpParentId!=st.getParentId()){
				allName=this.getAllSubName(st.getSubjectId());
			}
			String chked="false";
			if(!"".equals(subId) && Long.parseLong(subId) == st.getSubjectId().longValue()){
				chked="true";
			}
			
			strRet+="data["+i+"] = new Array('"+strId+"','"+strName+"','"+allName+"','"+parentId+"','"+chked+"');\n";
			
			tmpParentId=st.getParentId();
		}
		
		return strRet;
	 	
	 }

	/**
	 * 从一个树枝节点取出树根到树枝的完整name信息
	 * @param 树枝id
	 * @return allName
	 */
	 public String getAllSubName(Long subId) throws Exception{
		 String allName = null;
		 String strHql="select st.subjectName,st.parentId from SubjectTreeForm as st where st.subjectId= :subjectId";
		 QueryParameter param = new QueryParameter();
		 param.setName("subjectId");
		 param.setValue(subId);
		 param.setHbType(Hibernate.LONG);
		 
		 List tmpList=this.getDao().parameterQuery(strHql, param);
		 if(tmpList!=null&&!tmpList.isEmpty()){
			 Object[] obj = (Object[])tmpList.get(0);
			 
			 if(allName==null) allName=(String)obj[0];
			 
			 if((Long)obj[1] > 1){
				 allName = this.getAllSubName((Long)obj[1]) + " - " + allName;
			 }
		 }
		 return allName;
	 }
	 

	/**
	 * 取出对账单树信息
	 * @param String 对账单id，"all"表示全部
	 * @return 封装js树的List
	 */
	 public String getDropdownTree() throws Exception{
		String strRet="";
		
		ArrayList dataList=(ArrayList)this.getDao().list("from SubjectTreeForm as pojos where delFlag=0 and parentId>0 order by pojos.parentId,pojos.orderId ");
		
		for(int i=0;i<dataList.size();i++){
			SubjectTreeForm st=(SubjectTreeForm)dataList.get(i);
			String strId=st.getSubjectId()+"";		
			String strName=st.getSubjectName();		
			
			strRet+="var node"+strId+" = new CheckBoxTreeItem('"+strName+"','"+strId+"');\r\n";
			if(st.getParentId()>1){
				strRet+="node"+st.getParentId()+".add(node"+strId+");\r\n";
				
			}else{
				strRet+="tree.add(node"+strId+");\r\n";
			}
		}
		
		return strRet;
	 	
	 }
	 
	 //For Test
	 public void initTestFlowMoney() throws Exception{
		 int loopNum= 10;
		 ArrayList<AccountItemForm> al = new ArrayList<AccountItemForm>();
		 for(int i=0;i<loopNum;i++){
			 AccountItemForm aif = new AccountItemForm();
			 aif.setSubjectId(new Long(Operate.getRandomNum(10,52)));
			 aif.setSubjectAllName("test"+i);
			 float m1=Operate.getRandomNum(1000,999999);
			 aif.setMoney(new Double(m1/100));
			 aif.setFeeDate(getRadFeeDate());
			 int type=Operate.getRandomNum(1,5);
			 String payType=null;
			 switch(type){
			 	case 1: payType="XS";break;
			 	case 2: payType="XF";break;
			 	case 3: payType="YS";break;
			 	case 4: payType="YF";break;
			 }
			 aif.setPayType(payType);
			 aif.setVoucherNo(FormNumberBuilder.getVoucherNo(payType,Operate.getFeeDate(aif.getFeeDate())));
			 if(type==1||type==2){
				 aif.setEmployeeCode(new Long(Operate.getRandomNum(0,7)));
			 }else{
				 aif.setCustomerId(getRadCustId());
			 }
			 aif.setSummary("initTestFlowMoney"+i);
			 
			 aif.setCreateBy(new Long(1));
			 aif.setDelFlag(new Integer(0));

			 al.add(aif);
		 }
		 System.out.println("---al.size()="+al.size());
		 this.getBatchDao().insertBatch(al);
		 
	 }
	 
	 private static Date getRadFeeDate() throws Exception{
		 Date fd = null;
		 int[] ymd=new int[3];
		 int radDateNum=Operate.getRandomNum(1, 6);
		 switch(radDateNum){
		 	case 1: ymd[0]=2010;ymd[1]=11;break;
		 	case 2: ymd[0]=2010;ymd[1]=12;break;
		 	case 3: ymd[0]=2011;ymd[1]=1;break;
		 	case 4: ymd[0]=2011;ymd[1]=2;break;
		 	case 5: ymd[0]=2011;ymd[1]=3;break;
		 }
		 ymd[2]=Operate.getRandomNum(1, 31);
		 fd=Date.valueOf(ymd[0]+"-"+ymd[1]+"-"+ymd[2]);
		 
		 return fd;
	 }
	 private static String getRadCustId() throws Exception{
		 String custId="JYLD";
		 int rad=Operate.getRandomNum(1, 10);
		 switch(rad){
		 	case 1: custId="JYLD";break;
		 	case 2: custId="YLJM";break;
		 	case 3: custId="SZDS";break;
		 	case 4: custId="ADK";break;
		 	case 5: custId="HYLJ";break;
		 	case 6: custId="SDW";break;
		 	case 7: custId="SHSL";break;
		 	case 8: custId="TZLJ";break;
		 	case 9: custId="WXHY";break;
		 }
		 return custId;
	 }
	 
	 public static void main(String[] args) {
		 try{
			 AccountItemBo.getInstance().initTestFlowMoney();
			 
//			 float m1=Operate.getRandomNum(1000,999999);
//			 float m2 = m1/100;
//			 System.out.println(m1/100);
//
//			 System.out.println(m2);
			 
			 
			 
		 } catch(Exception e) {
			 e.printStackTrace();
		 } 
	}
	 
	
	 
}
