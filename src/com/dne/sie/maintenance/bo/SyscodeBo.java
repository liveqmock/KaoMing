package com.dne.sie.maintenance.bo;

//Java 基础类
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

//Java 扩展类

//第三方类
//import org.apache.log4j.Logger;

//自定义类
import com.dne.sie.common.tools.DicInit;
import com.dne.sie.maintenance.form.TsSystemCodeForm;
import com.dne.sie.maintenance.queryBean.SyscodeQuery;

import com.dne.sie.util.bo.CommBo;

/**字典表信息表
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
	* 字典表信息查询。
	* @param part TsSystemCodeForm  
	* @return ArrayList  返回数据是由TsSystemCodeForm属性组合成的String数组集合。
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
	 * 查询单条字典表信息
	 * @param id  String   id为字典表信息表的主键
	 * @return TsSystemCodeForm
	 */ 
	public TsSystemCodeForm find(String id) throws Exception{
		return  (TsSystemCodeForm)this.getDao().findById(TsSystemCodeForm.class,new Long(id));
		    	
	} 
		
   
	/**
	 * 添加单条字典表信息
	 * @param uf  TsSystemCodeForm   uf为数据库加载出来实例化后的TsSystemCodeForm对象
	 * @return int 1为成功，-1为失败。
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
	 * 修改字典表信息
	 * @param uf  TsSystemCodeForm   uf为数据库加载出来实例化后的TsSystemCodeForm对象
	 * @return int 1为成功，-1为失败
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
	 * 删除字典表信息
	 * @param uf  TsSystemCodeForm   uf为数据库加载出来实例化后的TsSystemCodeForm对象
	 * @return int 1为成功，-1为失败
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
