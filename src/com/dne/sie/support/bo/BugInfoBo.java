package com.dne.sie.support.bo;

import java.util.ArrayList;
import java.util.List;

import com.dne.sie.common.tools.DicInit;
import com.dne.sie.common.tools.Operate;
import com.dne.sie.support.form.BugInfoForm;
import com.dne.sie.support.form.BugQaFlowForm;
import com.dne.sie.support.queryBean.BugInfoQuery;
import com.dne.sie.util.bo.CommBo;

public class BugInfoBo extends CommBo {
	

	private static final BugInfoBo INSTANCE = new BugInfoBo();
		
	private BugInfoBo(){
	}
	
	public static final BugInfoBo getInstance() {
	   return INSTANCE;
	}
   
   /**
	* 客户表信息查询。
	* @param part BugInfoForm  
	* @return ArrayList  返回数据是由TsSystemCodeForm属性组合成的String数组集合。
	*/
   public ArrayList list(BugInfoForm part) {
	   List dataList = null;
	   ArrayList alData = new ArrayList();
	   BugInfoQuery uq = new BugInfoQuery(part);
       int count=0;
	   try {
		   dataList=uq.doListQuery(part.getFromPage(),part.getToPage());
		   count=uq.doCountQuery();
		   BugInfoForm pif=null;

		   for (int i=0;i<dataList.size();i++) {
			   String[] data = new String[8];
			   pif = (BugInfoForm)dataList.get(i);
	
			   data[0] = pif.getId().toString();     
			   data[1] = pif.getSubject();
			   data[2] = pif.getCreateUserName();
			   data[3] = DicInit.getSystemName("BUG_STATUS", pif.getStatus());
			   data[4] = DicInit.getSystemName("BUG_TYPE", pif.getType());
			   data[5] = pif.getDescription();
			   data[6] = Operate.trimDate(pif.getCreateDate());  
			   data[7] = Operate.trimDate(pif.getUpdateDate());

			  
			   alData.add(data);
		   }
		   alData.add(0,count+"");
	   } catch(Exception e) {
		   e.printStackTrace();
	   } 
	   return alData;
	  }
		
   
   /**
	 * 查询单条客户表信息
	 * @param id  String   id为客户表信息表的主键
	 * @return BugInfoForm
	 */ 
	public BugInfoForm find(Long id) throws Exception{
		return  (BugInfoForm)this.getDao().findById(BugInfoForm.class,id);
		    	
	} 
	
	public List<String[]> getBugReplyList(Long id) throws Exception{
		List<String[]> replyList = new ArrayList<String[]>();
		List qaList = this.getDao().list("from BugQaFlowForm bf where bf.bugId=? order by flowId",id);
		if(qaList!=null&&!qaList.isEmpty()){
			BugQaFlowForm qa = null;
			for(int i=0;i<qaList.size();i++){
				qa = (BugQaFlowForm)qaList.get(i);
				String[] temp = new String[4];
				temp[0] = qa.getFlowId().toString();
				temp[1] = qa.getDescription().replaceAll("\r\n","<br>");
				temp[2] = qa.getCreateUserName();
				temp[3] = Operate.formatDate(qa.getCreateDate());
				replyList.add(temp);
			}
		}
		return replyList;
	} 
		
   
	/**
	 * 添加单条客户表信息
	 * @param uf  BugInfoForm 
	 * @return int 1为成功，-1为失败。
	 */
	public int add(BugInfoForm uf) throws Exception{
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
	 * 修改客户表信息
	 * @param uf  BugInfoForm 
	 * @return int 1为成功，-1为失败
	 */
   public int modify(BugInfoForm uf) throws Exception{
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
	 * 逻辑删除客户表信息
	 * @param uf  客户表
	 * @return int 1为成功，-1为失败
	 */
   public int delete(String ids) throws Exception{
	  int tag=-1;
	  try{
		  tag=this.getDao().execute("delete from BugInfoForm as sc " +
	  		" where sc.id in ('"+ids.replaceAll(",", "','")+"')");
	  } catch(Exception e) {
		   e.printStackTrace();
	  } 
	  return tag;
	}
   
   
   public boolean replyAdd(BugQaFlowForm qa)throws Exception{
	   return this.getDao().insert(qa);
	   
   }
   
	

}
