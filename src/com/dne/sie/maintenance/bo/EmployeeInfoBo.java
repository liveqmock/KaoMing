package com.dne.sie.maintenance.bo;

//Java ������
import java.util.List;
import java.util.ArrayList;

//Java ��չ��

//��������
//import org.apache.log4j.Logger;

//�Զ�����
import com.dne.sie.common.tools.DicInit;
import com.dne.sie.common.tools.Operate;
import com.dne.sie.maintenance.form.EmployeeInfoForm;
import com.dne.sie.maintenance.queryBean.EmployeeInfoQuery;

import com.dne.sie.util.bo.CommBo;

/**Ա������Ϣ��
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
	* Ա������Ϣ��ѯ��
	* @param part EmployeeInfoForm  
	* @return ArrayList  ������������TsSystemCodeForm������ϳɵ�String���鼯�ϡ�
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
	 * ��ѯ����Ա������Ϣ
	 * @param id  String   idΪԱ������Ϣ�������
	 * @return EmployeeInfoForm
	 */ 
	public EmployeeInfoForm find(Long id) throws Exception{
		return  (EmployeeInfoForm)this.getDao().findById(EmployeeInfoForm.class,id);
		    	
	} 
		
   
	/**
	 * ��ӵ���Ա������Ϣ
	 * @param uf  EmployeeInfoForm 
	 * @return int 1Ϊ�ɹ���-1Ϊʧ�ܡ�
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
	 * �޸�Ա������Ϣ
	 * @param uf  EmployeeInfoForm 
	 * @return int 1Ϊ�ɹ���-1Ϊʧ��
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
	 * �߼�ɾ��Ա������Ϣ
	 * @param uf  Ա����
	 * @return int 1Ϊ�ɹ���-1Ϊʧ��
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
	 * У��Ա��id(PK)�Ƿ����
	 * @param String ������û�id
	 * @return ���û�id�Ƿ��������
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
	 * ��ȡԱ��select����Ϣ
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
	 * ��ȡԱ��select����Ϣ
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
