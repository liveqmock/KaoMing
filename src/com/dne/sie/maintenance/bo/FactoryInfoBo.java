package com.dne.sie.maintenance.bo;

//Java 基础类
import java.util.List;
import java.util.ArrayList;

//Java 扩展类

//第三方类
//import org.apache.log4j.Logger;

//自定义类
import com.dne.sie.maintenance.form.FactoryInfoForm;
import com.dne.sie.maintenance.queryBean.FactoryInfoQuery;

import com.dne.sie.util.bo.CommBo;

/**厂商表信息表
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
	* 厂商表信息查询。
	* @param part FactoryInfoForm  
	* @return ArrayList  返回数据是由TsSystemCodeForm属性组合成的String数组集合。
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
	 * 查询单条厂商表信息
	 * @param id  String   id为厂商表信息表的主键
	 * @return FactoryInfoForm
	 */ 
	public FactoryInfoForm find(String id) throws Exception{
		return  (FactoryInfoForm)this.getDao().findById(FactoryInfoForm.class,id);
		    	
	} 
		
   
	/**
	 * 添加单条厂商表信息
	 * @param uf  FactoryInfoForm 
	 * @return int 1为成功，-1为失败。
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
	 * 修改厂商表信息
	 * @param uf  FactoryInfoForm 
	 * @return int 1为成功，-1为失败
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
	 * 逻辑删除厂商表信息
	 * @param uf  厂商表
	 * @return int 1为成功，-1为失败
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
	 * 校验厂商id(PK)是否存在
	 * @param String 输入的用户id
	 * @return 该用户id是否可以输入
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
	 * 查询台湾高明询价地址信息
	 * @param 
	 * @return 高明信息
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
