package com.dne.sie.support.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;

import com.dne.sie.common.dbo.DBOperation;
import com.dne.sie.common.tools.Operate;
import com.dne.sie.support.form.AccountStatisticsEmployeeForm;
import com.dne.sie.support.form.AccountStatisticsForm;
import com.dne.sie.support.form.AccountStatisticsSubjectForm;
import com.dne.sie.support.queryBean.AccountStatisticsQuery;
import com.dne.sie.util.bo.CommBo;
import com.dne.sie.util.query.QueryParameter;

/**
 * 费用统计BO处理类
 * @author xt
 * @version 1.1.5.6
 * @see AccountStatisticsBo.java <br>
 */
public class AccountStatisticsBo extends CommBo{
	private static Logger logger = Logger.getLogger(AccountStatisticsBo.class);

	private static final AccountStatisticsBo INSTANCE = new AccountStatisticsBo();
		
	private AccountStatisticsBo(){
	}
	
	public static final AccountStatisticsBo getInstance() {
	   return INSTANCE;
	}
	
	private static final String BeginMonth="201103";
	

   /**
	* 费用统计信息查询。
	* @param part AccountStatisticsForm  
	* @return ArrayList  
	*/
   public ArrayList statList(AccountStatisticsForm part) {
	   List dataList = null;
	   ArrayList alData = new ArrayList();
	   AccountStatisticsQuery uq = new AccountStatisticsQuery(part);
       int count=0;
	   try {
		   dataList=uq.doListQuery(part.getFromPage(),part.getToPage());
		   count=uq.doCountQuery();
		   AccountStatisticsForm pif=null;

		   for (int i=0;i<dataList.size();i++) {
			   String[] data = new String[9];
			   pif = (AccountStatisticsForm)dataList.get(i);
			   data[0] = pif.getStatId().toString();
			   data[1] = pif.getAccountMonth().toString();
			   data[2] = pif.getCashReceipt().toString();
			   data[3] = pif.getCashPayment().toString();
			   data[4] = pif.getBankReceipt().toString();
			   data[5] = pif.getBankPayment().toString();
			   data[6] = pif.getCurrentCash().toString();
			   data[7] = pif.getCurrentBank().toString();
			   data[8] = Operate.toFix(pif.getTotalAmount(), 2);
			   
			   alData.add(data);
		   }
		   alData.add(0,count+"");
	   } catch(Exception e) {
		   e.printStackTrace();
	   } 
	   return alData;
	}
   
   /**
    * 获取月份select框
    * @return
    * @throws Exception
    */
   public static ArrayList getMonthList() throws Exception{
	   ArrayList<String[]> monthList = new ArrayList<String[]>();
	   
	   String strDate=Operate.getNowDate("yyyyMM");
	   int nowYear=Integer.parseInt(strDate.substring(0,4));
	   int nowMonth=Integer.parseInt(strDate.substring(4,6));
	   int bgYear=Integer.parseInt(BeginMonth.substring(0,4));
	   int bgMonth=Integer.parseInt(BeginMonth.substring(4,6));
	   
	   if(bgYear < nowYear){
		   for(int i=bgYear;i<nowYear;i++){
			   for(int mon=bgMonth;mon<=12;mon++){
				   String[] temp = new String[2];
				   String strMon=mon+"";
				   if(mon<=9)  strMon="0"+strMon;
				   temp[0]=bgYear+strMon;
				   temp[1]=bgYear+"-"+strMon;
				   
				   monthList.add(temp);
			   }
		   }
		   for(int mon=1;mon<=nowMonth;mon++){
			   String[] temp = new String[2];
			   String strMon=mon+"";
			   if(mon<=9)  strMon="0"+strMon;
			   temp[0]=bgYear+strMon;
			   temp[1]=bgYear+"-"+strMon;
			   
			   monthList.add(temp);
		   }
	   }else{
		   for(int mon=bgMonth;mon<=nowMonth;mon++){
			   String[] temp = new String[2];
			   String strMon=mon+"";
			   if(mon<=9)  strMon="0"+strMon;
			   temp[0]=bgYear+strMon;
			   temp[1]=bgYear+"-"+strMon;
			   
			   monthList.add(temp);
		   }
	   }
	   
	   return monthList;
   }
   
   public void initCurrentMonthStatistics() throws Exception{
	   
	   String nowMonth=Operate.getNowDate("yyyyMM");
	   this.accountStatistics(nowMonth);
	   
   }
   
   
    /**
     * 根据费用流水表统计某月费用支出和收入数据
     * 统计td_account_statistics，td_account_statistics_subject，td_account_statistics_employee
     * @param accountMonth "yyyyMM"
     * @return
     */
   	public boolean accountStatistics(String accountMonth) {
   		boolean succ=true;
   		try{
   			//没有该月的数据时，计算并插入统计数据
   			if(!this.chkStatOfMonth(accountMonth)){
   				ArrayList statInsList = new ArrayList();
   				
   				
   				//获取指定月的月初月末日期
   				String feeDate1=accountMonth.substring(0,4)+"-"+accountMonth.substring(4)+"-01";
   				String feeDate2=Operate.trimDate(Operate.getLastDayOfMonth(feeDate1));
   					System.out.println("----feeDate1="+feeDate1+"    feeDate2="+feeDate2);
   				//从费用流水表里查询
   				String hqlFrom="from AccountItemForm aif where aif.delFlag=0 and aif.feeDate >= :feeDate1 and aif.feeDate < :feeDate2";
   				
   				ArrayList<QueryParameter> paramList = new ArrayList<QueryParameter>();
				QueryParameter param1 = new QueryParameter();
				param1.setName("feeDate1");
				param1.setValue(Operate.toDate(feeDate1));
				param1.setHbType(Hibernate.DATE);
				paramList.add(param1);
				
				QueryParameter param2 = new QueryParameter();
				param2.setName("feeDate2");
				param2.setValue(Operate.getNextDate(feeDate2));
				param2.setHbType(Hibernate.DATE);
				paramList.add(param2);

   				/*
   				 * 统计td_account_statistics
   				 */
   				String statHql="select aif.payType,sum(aif.money) "+hqlFrom+" group by aif.payType";
   				
				List statList = this.getDao().parameterQuery(statHql, paramList);
   				if(statList!=null&&!statList.isEmpty()){
   					System.out.println("----statList.size()="+statList.size());
   	   				AccountStatisticsForm asf = new AccountStatisticsForm();
   	   				asf.setAccountMonth(new Integer(accountMonth));
   	   				
   					for(int i=0;i<statList.size();i++){
   						Object[] obj = (Object[])statList.get(i);
   						String payType=(String)obj[0];
   						Double money=(Double)obj[1];
   						if("XS".equals(payType)){
   							asf.setCashReceipt(money);		//现收
   						}else if("XF".equals(payType)){
   							asf.setCashPayment(money);		//现付
   						}else if("YS".equals(payType)){
   							asf.setBankReceipt(money);		//银收
   						}else if("YF".equals(payType)){
   							asf.setBankPayment(money);		//银付
   						}
   					}
   					
   					//查询上月余额
   					String priorMonth=Operate.getPriorMonth(accountMonth);
   					Object[] obj=(Object[])this.getDao().uniqueResult("select asf.currentCash,asf.currentBank from AccountStatisticsForm as asf where asf.accountMonth="+priorMonth);
   					if(obj==null){
   						obj=new Object[2];
   						obj[0]=0D;
   						obj[1]=0D;
   					}
   					
   					asf.setPriorCash((Double)obj[0]);	//上月余额（现）
   					asf.setPriorBank((Double)obj[1]);	//上月余额（银）
   					
   					Double currentCash = (asf.getPriorCash()==null?0D:asf.getPriorCash())
   									  + (asf.getCashReceipt()==null?0D:asf.getCashReceipt())
   									  - (asf.getCashPayment()==null?0D:asf.getCashPayment());
   					Double currentBank = (asf.getPriorBank()==null?0D:asf.getPriorBank())
   									  + (asf.getBankReceipt()==null?0D:asf.getBankReceipt())
   									  - (asf.getBankPayment()==null?0D:asf.getBankPayment());
   					
   					asf.setCurrentCash(currentCash);
   					asf.setCurrentBank(currentBank);
   					
   					System.out.println("----getCashReceipt()="+asf.getCashReceipt());
   					System.out.println("----getCashPayment()="+asf.getCashPayment());
   					System.out.println("----getBankPayment()="+asf.getBankPayment());
   					System.out.println("----getBankReceipt()="+asf.getBankReceipt());
   					
   					statInsList.add(asf);
   				}
   				
   				

   				/*
   				 * 统计td_account_statistics_subject
   				 */
   				String subHql="select aif.payType,aif.subjectId,sum(aif.money) ," +
   						"(select st.subjectName from SubjectTreeForm st where st.subjectId= aif.subjectId)  "+
   						hqlFrom+" group by aif.payType,aif.subjectId ";
				List subList = this.getDao().parameterQuery(subHql, paramList);
				if(subList!=null&&!subList.isEmpty()){
					System.out.println("----subList.size()="+subList.size());
   					for(int i=0;i<subList.size();i++){
   						AccountStatisticsSubjectForm assf = new AccountStatisticsSubjectForm();
   						assf.setAccountMonth(new Integer(accountMonth));
   						
   						Object[] obj = (Object[])subList.get(i);
   						assf.setPayType((String)obj[0]);
   						assf.setSubjectId((Long)obj[1]);
   						assf.setMoney((Double)obj[2]);
   						assf.setSubjectName((String)obj[3]);
//   					assf.setSubjectName(CommonSearch.getInstance().getSubjectTreeName((Long)obj[1]));
   						
   						statInsList.add(assf);
   					}
				}
				
				/*
				 * 统计td_account_statistics_employee
				 */
				String empHql="select aif.payType,aif.employeeCode,sum(aif.money) ," +
					"(select ei.employeeName from EmployeeInfoForm ei where ei.employeeCode=aif.employeeCode)  "+
					hqlFrom+" and aif.employeeCode is not null and aif.payType in('XS','XF')" +
					" group by aif.payType,aif.employeeCode ";
				List empList = this.getDao().parameterQuery(empHql, paramList);
				if(empList!=null&&!empList.isEmpty()){
					System.out.println("----empList.size()="+empList.size());
   					for(int i=0;i<empList.size();i++){
   						AccountStatisticsEmployeeForm asef = new AccountStatisticsEmployeeForm();
   						asef.setAccountMonth(new Integer(accountMonth));
   						
   						Object[] obj = (Object[])empList.get(i);
   						asef.setPayType((String)obj[0]);
   						asef.setEmployeeCode((Long)obj[1]);
   						asef.setMoney((Double)obj[2]);
   						asef.setEmployeeName((String)obj[3]);
   						
   						statInsList.add(asef);
   					}
				}

				String custHql="select aif.payType,aif.customerId,sum(aif.money) ," +
					"(select ci.customerName from CustomerInfoForm ci where ci.customerId=aif.customerId)  "+
					hqlFrom+" and aif.customerId is not null and aif.payType in('YS','YF')" +
					" group by aif.payType,aif.customerId ";
				List custList = this.getDao().parameterQuery(custHql, paramList);
				if(custList!=null&&!custList.isEmpty()){
					System.out.println("----custList.size()="+custList.size());
   					for(int i=0;i<custList.size();i++){
   						AccountStatisticsEmployeeForm asef = new AccountStatisticsEmployeeForm();
   						asef.setAccountMonth(new Integer(accountMonth));
   						
   						Object[] obj = (Object[])custList.get(i);
   						asef.setPayType((String)obj[0]);
   						asef.setCustomerId((String)obj[1]);
   						asef.setMoney((Double)obj[2]);
   						asef.setCustomerName((String)obj[3]);
   						
   						statInsList.add(asef);
   					}
				}
				
				
				succ = this.getBatchDao().insertBatch(statInsList);
   			}
   			
   		} catch(Exception e) {
   			succ=false;
   			e.printStackTrace();
   		} 
   		return succ;
   	}
   	
   	/**
   	 * 清空指定月的统计记录
   	 * @param accountMonth "yyyyMM"
   	 * @return
   	 */
   	public boolean emptyStatistics(String accountMonth) {
   		boolean succ=true;
   		try{
   			String delStat="delete from AccountStatisticsForm as asf where asf.accountMonth="+accountMonth;
   			String delSub="delete from AccountStatisticsSubjectForm as asf where asf.accountMonth="+accountMonth;
   			String delEmp="delete from AccountStatisticsEmployeeForm as asf where asf.accountMonth="+accountMonth;
   			ArrayList<String> delList=new ArrayList<String>();
   			delList.add(delStat);
   			delList.add(delSub);
   			delList.add(delEmp);
   			
   			succ=this.getBatchDao().excuteBatch(delList);
   			
   		} catch(Exception e) {
   			succ=false;
   			e.printStackTrace();
   		} 
   		return succ;
   	}
   	
   	/**
   	 * 查询指定月份的3种费用统计信息
   	 * @param accountMonth "yyyyMM"
   	 * @return
   	 * @throws Exception
   	 */
   	public Object[] getStatDetail(String accountMonth) throws Exception{
   		Object[] objStat = null;
   		
		
		//有该月的数据时，查询详细统计数据
		if(this.chkStatOfMonth(accountMonth)){
			objStat = new Object[4];

			QueryParameter param = new QueryParameter();
			param.setName("accountMonth");
			param.setValue(new Integer(accountMonth));
			param.setHbType(Hibernate.INTEGER);
			
			//td_account_statistics		tab1
			String statHql="from AccountStatisticsForm asf where asf.accountMonth = :accountMonth";
				
			ArrayList<AccountStatisticsForm> statList = (ArrayList)this.getDao().parameterQuery(statHql, param);
			AccountStatisticsForm statForm = statList.get(0);
			objStat[0]=statForm;
			
			//td_account_statistics_subject		tab2
			String subHql="from AccountStatisticsSubjectForm ss where ss.accountMonth = :accountMonth order by ss.payType";
			ArrayList<AccountStatisticsSubjectForm> subStatList = (ArrayList)this.getDao().parameterQuery(subHql, param);
			ArrayList[] typeListSub = new ArrayList[4];
			if(subStatList!=null&&!subStatList.isEmpty()){
				ArrayList<Object[]> xsList=new ArrayList<Object[]>();
				ArrayList<Object[]> xfList=new ArrayList<Object[]>();
				ArrayList<Object[]> ysList=new ArrayList<Object[]>();
				ArrayList<Object[]> yfList=new ArrayList<Object[]>();
				for(int i=0;i<subStatList.size();i++){
					AccountStatisticsSubjectForm assf = subStatList.get(i);
					Object[] temp=new Object[2];
					temp[0]=assf.getSubjectName();
					temp[1]=assf.getMoney();
					
					if(assf.getPayType().equals("XS")){
						xsList.add(temp);
					}else if(assf.getPayType().equals("XF")){
						xfList.add(temp);
					}else if(assf.getPayType().equals("YS")){
						ysList.add(temp);
					}else if(assf.getPayType().equals("YF")){
						yfList.add(temp);
					}
				}
				typeListSub[0]=xsList;
				typeListSub[1]=xfList;
				typeListSub[2]=ysList;
				typeListSub[3]=yfList;
			}
			objStat[1]=typeListSub;
			
			//td_account_statistics_employee	tab3
			String empHql="from AccountStatisticsEmployeeForm se where se.accountMonth = :accountMonth order by se.payType";
			ArrayList<AccountStatisticsEmployeeForm> empStatList = (ArrayList)this.getDao().parameterQuery(empHql, param);
			ArrayList[] typeListEmp = new ArrayList[4];
			if(empStatList!=null&&!empStatList.isEmpty()){
				ArrayList<Object[]> xsList=new ArrayList<Object[]>();
				ArrayList<Object[]> xfList=new ArrayList<Object[]>();
				ArrayList<Object[]> ysList=new ArrayList<Object[]>();
				ArrayList<Object[]> yfList=new ArrayList<Object[]>();
				for(int i=0;i<empStatList.size();i++){
					AccountStatisticsEmployeeForm assf = empStatList.get(i);
					Object[] temp=new Object[2];
					temp[1]=assf.getMoney();
					
					if(assf.getPayType().equals("XS")){
						temp[0]=assf.getEmployeeName();
						xsList.add(temp);
					}else if(assf.getPayType().equals("XF")){
						temp[0]=assf.getEmployeeName();
						xfList.add(temp);
					}else if(assf.getPayType().equals("YS")){
						temp[0]=assf.getCustomerName();
						ysList.add(temp);
					}else if(assf.getPayType().equals("YF")){
						temp[0]=assf.getCustomerName();
						yfList.add(temp);
					}
				}
				typeListEmp[0]=xsList;
				typeListEmp[1]=xfList;
				typeListEmp[2]=ysList;
				typeListEmp[3]=yfList;
				
			}
			objStat[2]=typeListEmp;

			//td_account_statistics_subject for tab1
			String xfHql="select ss.subjectName,ss.money,ss.subjectName " +
						"from AccountStatisticsSubjectForm ss where ss.accountMonth = :accountMonth and ss.payType='XF' ";
			ArrayList<Object[]> xfStatList = (ArrayList)this.getDao().parameterQuery(xfHql, param);
			if(xfStatList==null||xfStatList.isEmpty()){
				xfStatList= new ArrayList<Object[]>();
				Object[] obj = new Object[3];
				obj[0]="无";
				obj[1]="0.00";
				obj[2]="";
				xfStatList.add(obj);
			}
			objStat[3]=xfStatList;
			
			
		}
   		
   		return objStat;
   	}
   	
   	/**
   	 * 校验某月份是否已统计费用
   	 * @param accountMonth
   	 * @return
   	 * @throws Exception
   	 */
   	public boolean chkStatOfMonth(String accountMonth) throws Exception{
   		boolean exist = false;
   		String chkData="select count(*) from AccountStatisticsForm as asf where asf.accountMonth="+accountMonth;
		Long chkStatCount = (Long)this.getDao().uniqueResult(chkData);
		if(chkStatCount>0)  exist=true;
		return exist;
   	}
   	
   	public void test() throws Exception{
   		
		String hql="select cashPayment from AccountStatisticsForm where accountMonth=201102";
		Double fc=(Double)this.getDao().uniqueResult(hql);
//		String sql="SELECT cash_payment FROM td_account_statistics where account_Month=201102";
//		String fc=DBOperation.selectOne(sql);
//		
		System.out.println("fc1="+fc);
   		
   	}
   	

	/**
	 * 删除费用表信息
	 * @param uf  费用表pk
	 * @return int 1为成功，-1为失败
	 */
   public int delete(String accountMonth) throws Exception{
	  int tag=-1;
	  try{
		  tag=this.getDao().execute("delete from AccountStatisticsForm as sc " +
	  		"where sc.accountMonth = "+accountMonth);
	  } catch(Exception e) {
		   e.printStackTrace();
	  } 
	  return tag;
	}
	
   	
   	public static void main(String[] args) {
		try{
//			AccountStatisticsBo.getInstance().emptyStatistics("201011");
//			AccountStatisticsBo.getInstance().accountStatistics("201011");
			
//			AccountStatisticsBo.getInstance().test();
			

			
		}catch(Exception e) {
 		   e.printStackTrace();
   		} 
	}
}
