package com.dne.sie.maintenance.bo;

//Java 基础类
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.dne.sie.common.tools.Operate;
import com.dne.sie.maintenance.form.StationBinForm;
import com.dne.sie.maintenance.queryBean.StationBinQuery;
import com.dne.sie.util.bo.CommBo;

/**
 * 仓位信息表
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
	* 仓位信息查询。
	* @param part StationBinForm  
	* @return ArrayList  返回数据是由PartInfoForm属性组合成的String数组集合。
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
	 * 查询单条零件信息
	 * @param id  String   id为零件信息表的主键
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
	 * 添加单条零件信息
	 * @param uf  StationBinForm   uf为数据库加载出来实例化后的PartInfoForm对象
	 * @return int 1为成功，-1为失败。
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
	 * 修改零件信息
	 * @param uf  StationBinForm   uf为数据库加载出来实例化后的PartInfoForm对象
	 * @return int 1为成功，-1为失败
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
