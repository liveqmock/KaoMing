package com.dne.sie.maintenance.bo;

//Java 基础类
import java.util.List;
import java.util.ArrayList;

//Java 扩展类

//第三方类
//import org.apache.log4j.Logger;

//自定义类
import com.dne.sie.common.tools.DicInit;
import com.dne.sie.common.tools.Operate;
import com.dne.sie.maintenance.form.EmployeeInfoForm;
import com.dne.sie.maintenance.queryBean.EmployeeInfoQuery;

import com.dne.sie.util.bo.CommBo;

/**员工表信息表
 *@version 1.1.5.6
 */

public class EmployeeInfoBo extends CommBo {

	private static final EmployeeInfoBo INSTANCE = new EmployeeInfoBo();
		
	private EmployeeInfoBo(){
	}
	
	public static final EmployeeInfoBo getInstance() {
	   return INSTANCE;
	}
   
   /**
	* 员工表信息查询。
	* @param part EmployeeInfoForm  
	* @return ArrayList  返回数据是由TsSystemCodeForm属性组合成的String数组集合。
	*/
   public ArrayList list(EmployeeInfoForm part) {
	   List dataList = null;
	   ArrayList alData = new ArrayList();
	   EmployeeInfoQuery uq = new EmployeeInfoQuery(part);
       int count=0;
	   try {
		   dataList=uq.doListQuery(part.getFromPage(),part.getToPage());
		   count=uq.doCountQuery();
		   EmployeeInfoForm pif=null;

		   for (int i=0;i<dataList.size();i++) {
			   String[] data = new String[9];
			   pif = (EmployeeInfoForm)dataList.get(i);
			   data[0] = pif.getEmployeeCode().toString();
			   data[1] = pif.getEmployeeName();
			   data[2] = (pif.getBirthday()==null||pif.getBirthday().equals(""))?"":Operate.getAge(pif.getBirthday(),"yyyy-MM-dd")+"";
			   data[3] = DicInit.getSystemName("SEX", pif.getSex());
			   data[4] = pif.getEmail();
			   data[5] = pif.getPhone();
			   data[6] = pif.getQq();
			   data[7] = pif.getMsn();
			   data[8] = pif.getIdentityCard();
			   
			   alData.add(data);
		   }
		   alData.add(0,count+"");
	   } catch(Exception e) {
		   e.printStackTrace();
	   } 
	   return alData;
	  }
		
   
   /**
	 * 查询单条员工表信息
	 * @param id  String   id为员工表信息表的主键
	 * @return EmployeeInfoForm
	 */ 
	public EmployeeInfoForm find(Long id) throws Exception{
		return  (EmployeeInfoForm)this.getDao().findById(EmployeeInfoForm.class,id);
		    	
	} 
		
   
	/**
	 * 添加单条员工表信息
	 * @param uf  EmployeeInfoForm 
	 * @return int 1为成功，-1为失败。
	 */
	public int add(EmployeeInfoForm uf) throws Exception{
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
	 * 修改员工表信息
	 * @param uf  EmployeeInfoForm 
	 * @return int 1为成功，-1为失败
	 */
   public int modify(EmployeeInfoForm uf) throws Exception{
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
	 * 逻辑删除员工表信息
	 * @param uf  员工表
	 * @return int 1为成功，-1为失败
	 */
   public int delete(String ids) throws Exception{
	  int tag=-1;
	  try{
		  tag=this.getDao().execute("update from EmployeeInfoForm as sc " +
	  		"set sc.delFlag=1 where sc.employeeCode in ("+ids+")");
	  } catch(Exception e) {
		   e.printStackTrace();
	  } 
	  return tag;
	}

	/**
	 * 校验员工id(PK)是否存在
	 * @param String 输入的用户id
	 * @return 该用户id是否可以输入
	 */
	public boolean chkEmpName(String empName) {
		boolean retBoo = false;
		
		try {
			Object obj=this.getDao().uniqueResult("select count(uf) from EmployeeInfoForm as uf " +
					"where uf.delFlag=0 and uf.employeeName='"+empName+"'");
			int count=((Long)obj).intValue();
			
			if(count==0) retBoo=true;
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return retBoo;		    	
	} 
	public boolean chkEmpName(String empName,Long empCode) {
		boolean retBoo = false;
		
		try {
			Object obj=this.getDao().uniqueResult("select count(uf) from EmployeeInfoForm as uf " +
				"where uf.delFlag=0 and uf.employeeName='"+empName+"' and uf.employeeCode !="+empCode);
			int count=((Long)obj).intValue();
			
			if(count==0) retBoo=true;
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return retBoo;		    	
	} 
	

	/**
	 * 获取员工select框信息
	 * @return 
	 */
   public ArrayList<String[]> getEmpSelectList() throws Exception{
	  ArrayList<String[]> empList = new ArrayList<String[]>();
	  try{
		  ArrayList al=(ArrayList)this.getDao().list("select ei.employeeCode,ei.employeeName " +
		  		"from EmployeeInfoForm as ei where ei.delFlag=0");
		  for(int i=0;al!=null&&i<al.size();i++){
			  Object[] obj=(Object[])al.get(i);
			  String[] temp=new String[2];
			  temp[0]=obj[0].toString();
			  temp[1]=obj[1].toString();
			  empList.add(temp);
		  }
	  } catch(Exception e) {
		   e.printStackTrace();
	  } 
	  return empList;
	}
   

	/**
	 * 获取员工select框信息
	 * @return 
	 */
  public ArrayList<String[]> getEmpSelectRegistList() throws Exception{
	  ArrayList<String[]> empList = new ArrayList<String[]>();
	  try{
		  ArrayList al=(ArrayList)this.getDao().list("select ei.employeeCode,ei.employeeName " +
		  		"from EmployeeInfoForm as ei where ei.delFlag=0 and ei.employeeCode not in " +
		  		"(select uf.employeeId from UserForm as uf where uf.delFlag=0 and uf.employeeId is not null)");
		  for(int i=0;al!=null&&i<al.size();i++){
			  Object[] obj=(Object[])al.get(i);
			  String[] temp=new String[2];
			  temp[0]=obj[0].toString();
			  temp[1]=obj[1].toString();
			  empList.add(temp);
		  }
	  } catch(Exception e) {
		   e.printStackTrace();
	  } 
	  return empList;
	}
   
   public ArrayList<String[]> getRepairManList() throws Exception{
	   ArrayList<String[]> empList = new ArrayList<String[]>();
		  try{
			  ArrayList al=(ArrayList)this.getDao().list("select ei.employeeCode,ei.employeeName " +
			  		"from EmployeeInfoForm as ei where ei.delFlag=0 and ei.position='W'");
			  for(int i=0;al!=null&&i<al.size();i++){
				  Object[] obj=(Object[])al.get(i);
				  String[] temp=new String[2];
				  temp[0]=obj[0].toString();
				  temp[1]=obj[1].toString();
				  empList.add(temp);
			  }
		  } catch(Exception e) {
			   e.printStackTrace();
		  } 
		  return empList;
   }
	
	
}
