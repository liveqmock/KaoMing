package com.dne.sie.support.bo;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.dne.sie.common.tools.Operate;
import com.dne.sie.util.bo.CommBo;

/**
 * 首页管理Action处理类
 * @author xt
 * @version 1.1.5.6
 * @see IndexBo.java <br>
 */
public class IndexBo extends CommBo{
	private static Logger logger = Logger.getLogger(IndexBo.class);

	private static final IndexBo INSTANCE = new IndexBo();
		
	private IndexBo(){
	}
	
	public static final IndexBo getInstance() {
	   return INSTANCE;
	}

	/**
	 * 首页:客户订单
	 * @param 
	 * @return String 询价回复 and 报价核算 and 本月销售单number
	 */
	public int[] getOrderPartNum(){
		int[] retNum=new int[3];
		try{		
			Long skuNum=(Long)this.getDao().uniqueResult("select count(pa) from SaleInfoForm as pa where saleStatus=? and pa.delFlag=0 ","A");
			retNum[0]=skuNum.intValue();
			skuNum=(Long)this.getDao().uniqueResult("select count(pa) from SaleInfoForm as pa where saleStatus=? and pa.delFlag=0","D");
			retNum[1]=skuNum.intValue();
			
			skuNum=(Long)this.getDao().uniqueResult("select count(pa) from SaleInfoForm as pa where pa.delFlag=0 and createDate >= '"+Operate.trimDate(Operate.getFirstDayOfMonth(null))+"' ");
			retNum[2]=skuNum.intValue();
			
			
		}catch(Exception e){
			e.printStackTrace();
		}		
		return retNum;
	}

	/**
	 * 首页:库存零件
	 * @param 
	 * @return String 结存零件+待出库零件+ 订购零件
	 */
	public int[] getStockPartNum(){
		int[] retNum=new int[3];
		try{		
			Long skuNum=(Long)this.getDao().uniqueResult("select sum(skuNum) from StockInfoForm as si ");
			retNum[0]=skuNum==null?0:skuNum.intValue();
			
			skuNum=(Long)this.getDao().uniqueResult("select sum(skuNum) from StockInfoForm as si where stockStatus = ? ","R");
			retNum[1]=skuNum==null?0:skuNum.intValue();
			
			skuNum=(Long)this.getDao().uniqueResult("select sum(orderNum) from PoForm as po where orderStatus = ? ","B");
			retNum[2]=skuNum==null?0:skuNum.intValue();
			
		}catch(Exception e){
			e.printStackTrace();
		}		
		return retNum;
	}
	

	/**
	 * 首页:库存误差
	 * @param 
	 * @return String A零件个数+B零件个数
	 */
	public int[] getStockDiffNum(){
		int[] retNum=new int[2];
		try{		
			ArrayList al=(ArrayList)this.getDao().list("select si.skuType,sum(si.skuNum) from StockInfoForm as si group by si.skuType");
			for(int i=0;al!=null&&i<al.size();i++){
				Object[] obj=(Object[])al.get(i);
				if("A".equals((String)obj[0])) retNum[0]=obj[1]==null?0:((Long)obj[1]).intValue();
				else if("B".equals((String)obj[0])) retNum[1]=obj[1]==null?0:((Long)obj[1]).intValue();
			}
		}catch(Exception e){
			e.printStackTrace();
		}		
		return retNum;
	}
	
	public int[] getRepairNum(){
		int[] retNum=new int[3];
		try{		
			ArrayList al=(ArrayList)this.getDao().list("select rsf.currentStatus,count(*) from RepairSearchForm as rsf group by rsf.currentStatus");
			for(int i=0;al!=null&&i<al.size();i++){
				Object[] obj=(Object[])al.get(i);
				//电话诊断中
				if("A".equals((String)obj[0])) retNum[0]=obj[1]==null?0:((Long)obj[1]).intValue();
				//在修
				else if("D".equals((String)obj[0])) retNum[1]=obj[1]==null?0:((Long)obj[1]).intValue();
				//不修理
				else if("N".equals((String)obj[0])) retNum[2]=obj[1]==null?0:((Long)obj[1]).intValue();
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}		
		return retNum;
	}
	
	public int[] getDipatchRemindNum(){
		int[] retNum=new int[2];
		try{		
			ArrayList al=(ArrayList)this.getDao().list("select rsf.warrantyType,count(*) from RepairSearchForm as rsf " +
					" where rsf.currentStatus='T' and rsf.repairProperites='T' and rsf.warrantyType in('B','C') " +
					" and to_days(now()) - to_days(rsf.customerVisitDate) <=2" +
					" group by rsf.warrantyType");
			for(int i=0;al!=null&&i<al.size();i++){
				Object[] obj=(Object[])al.get(i);
				//安调
				if("B".equals((String)obj[0])) retNum[0]=obj[1]==null?0:((Long)obj[1]).intValue();
				//维修
				else if("C".equals((String)obj[0])) retNum[1]=obj[1]==null?0:((Long)obj[1]).intValue();
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}		
		return retNum;
	}
	
	
	public int[] logisticApproveNum(){
		int[] retNum=new int[2];
		try{		
			ArrayList al=(ArrayList)this.getDao().list("select sd.partStatus,count(*) from SaleDetailForm as sd,SaleInfoForm as sif " +
					"where sif.delFlag=0 and sd.delFlag=0  and sd.saleNo=sif.saleNo and sd.partStatus in('Q','O')" +
					" group by sd.partStatus ");
			for(int i=0;al!=null&&i<al.size();i++){
				Object[] obj=(Object[])al.get(i);
				//发货同意
				if("Q".equals((String)obj[0])) retNum[0]=obj[1]==null?0:((Long)obj[1]).intValue();
				//发货不同意
				else if("O".equals((String)obj[0])) retNum[1]=obj[1]==null?0:((Long)obj[1]).intValue();
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}		
		return retNum;
	}
	
	public String getPartSaleNos(){
		String partSaleNos = "";
		try{
			ArrayList al=(ArrayList)this.getDao().list("select distinct saleNo from SaleDetailForm as rsf where partStatus=?","L");
			for(int i=0;al!=null&&i<al.size();i++){
				String obj=(String)al.get(i);
				if(i==0) partSaleNos=obj;
				else if(i<4) partSaleNos+="<br>"+obj;
				else {
					partSaleNos+="<br>......";
					break;
				}
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}		
		return partSaleNos;
	}

	public String[] getStockSupplyParts(){
		String[] stockSupply = new String[2];
		try{
			ArrayList al=(ArrayList)this.getDao().list("select distinct stuffNo,skuCode,sum(skuNum) from StockFlowForm " +
					"where flowType=? and flowItem=? and createDate<sysdate()-30 group by stuffNo,skuCode " +
					"order by sum(skuNum) desc ","O","S");
			for(int i=0;al!=null&&i<al.size()&&i<4;i++){
				Object[] obj=(Object[])al.get(i);
				if(i==0){
					stockSupply[0]=(String)obj[0] +" ("+obj[2]+") ";
					stockSupply[1]=(String)obj[1];
				}else { 
					stockSupply[0]+="<br>"+(String)obj[0]+" ("+obj[2]+") ";
					stockSupply[1]+=","+(String)obj[1];
				}
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}		
		return stockSupply;
	}
	
	

	
	        
}
