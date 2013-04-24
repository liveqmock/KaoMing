package com.dne.sie.stock.bo;

//Java 基础类
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Date;

//第三方类
import org.apache.log4j.Logger;

//自定义类
import com.dne.sie.util.bo.CommBo;

import com.dne.sie.stock.form.StockTakeForm;
import com.dne.sie.stock.form.StockTakeDetailForm;
import com.dne.sie.stock.form.StockInfoForm;
//import com.dne.sie.stock.form.StockAdjustForm;
import com.dne.sie.stock.form.StockTakeReportForm;

import com.dne.sie.stock.queryBean.StockTakeQuery;
import com.dne.sie.stock.queryBean.StockTakeDetailQuery;
import com.dne.sie.stock.queryBean.StockTakeReportQuery;

import com.dne.sie.common.tools.DicInit;
import com.dne.sie.common.tools.Operate;
import com.dne.sie.common.tools.CommonSearch;
import com.dne.sie.common.exception.ReportException;


/**
 * 库存盘点BO处理类
 * @author xt
 * @version 1.1.5.6
 * @see StockTakeBo.java <br>
 */
public class StockTakeBo extends CommBo{
	private static Logger logger = Logger.getLogger(StockTakeBo.class);
	

	private static final StockTakeBo INSTANCE = new StockTakeBo();
		
	private StockTakeBo(){
	}
	
	public static final StockTakeBo getInstance() {
	   return INSTANCE;
	}
	


	/**
	 * 校验该维修站库存零件是否都是可用状态
	 * @param StockInfoForm 查询条件
	 * @return 是否可用
	 */
	public boolean checkAvailable(StockInfoForm sif) {
		boolean booRet=true;
			
		try{
			String where=getStockInfoWhere(sif);
			
			String strHql="select count(*) from StockInfoForm as sif " +
				"where sif.stockStatus!='A' and sif.skuNum>0 "+where;
				
			ArrayList tempList=(ArrayList)this.getDao().list(strHql);
				
			int count = ((Long)tempList.get(0)).intValue();		
			if(count!=0) booRet=false;
								
		}catch(Exception e) {
			e.printStackTrace();
		} 
		return booRet;
	}
	
	/**
	 * 根据上次的盘点记录，查询一次盘点的列表
	 * @param StockTakeDetailForm - 盘点表pk
	 * @param Long 部门id
	 * @return 一次盘点纪录的list
	 */
	public ArrayList[] takeFirstList(StockTakeDetailForm stdfQuery) {
		ArrayList[] stockInfoLists=new ArrayList[3];
		ArrayList stockInfoList=new ArrayList();
//		char spliter = 0x0001;
		try{
			StockTakeDetailQuery stq = new StockTakeDetailQuery(stdfQuery);
			List detailList=stq.doListQuery();
			
			StockTakeDetailForm stdf=new StockTakeDetailForm();
//			HashSet stockBin=new HashSet();
			for(int i=0;i<detailList.size();i++){
				stdf=(StockTakeDetailForm)detailList.get(i);
				String[] data = new String[7];

				data[0] = stdf.getStockTakeDetailId().toString();
				data[1] = stdf.getBinCode()==null?"":stdf.getBinCode();
				
				data[2] = stdf.getStuffNo();
				data[3] = stdf.getSkuCode();
				data[4] = stdf.getStockNum()==null?"":stdf.getStockNum().toString();
				data[5] = stdf.getTakeNum()==null?"":stdf.getTakeNum().toString();
				data[6] = stdf.getSkuUnit()==null?"":stdf.getSkuUnit().toString();
				
//				stockBin.add(data[1]);
				stockInfoList.add(data);
			}
			
//			StockTakeForm stf=this.getTakeInfo(stdfQuery.getStockTakeId());
//			String strBinCode1="",strBinCode2="";
//			
//			if(stf.getBinCodeBegin()!=null&&!stf.getBinCodeBegin().equals("")){
//				strBinCode1=" and sbf.binCode>='"+stf.getBinCodeBegin()+"'";
//			}
//			if(stf.getBinCodeEnd()!=null&&!stf.getBinCodeEnd().equals("")){
//				strBinCode2=" and sbf.binCode<='"+stf.getBinCodeEnd()+"'";
//			}
			
			//查询没有放零附件的仓位号
//			String strBinHql="select distinct sbf.binCode " +
//				"from StationBinForm as sbf where sbf.delFlag=0  "+
//				 strBinCode1 + strBinCode2;
//			ArrayList allBinList=(ArrayList)this.getDao().list(strBinHql);
//			ArrayList noneBinList=new ArrayList();
//			for(int i=0;i<allBinList.size();i++){
//				Object obj=(Object)allBinList.get(i);
//				String strBin=(String)obj;
//				if(!stockBin.contains(strBin)){
//					noneBinList.add(strBin);
//				}
//			}
			
			//仓库仓位的select框
//			StockInBo sib = StockInBo.getInstance();
//			ArrayList stockList=sib.getStockList();
//			
			stockInfoLists[0]=stockInfoList;
//			stockInfoLists[1]=stockList;
//			stockInfoLists[2]=noneBinList;
						
		}catch(Exception e) {
			e.printStackTrace();
		} 
		return stockInfoLists;
	}
	
	/**
	 * 将盘点初始信息插入td_stock_take表，将详细盘点零件信息插入td_stock_take_detail表
	 * @param StockInfoForm - 盘点的条件
	 * @return 是否成功
	 */
	public int takeStartInsert(StockInfoForm siQuery) {
		int takeId=-1;
		try{
			StockTakeForm stf = new StockTakeForm();
			
			stf.setBeginDate(new Date());
			stf.setOperater(siQuery.getCreateBy());
			stf.setLowPrice(siQuery.getRealCost1());
			stf.setSkuType(siQuery.getSkuType());
			stf.setCreateBy(siQuery.getCreateBy());
			stf.setUpPrice(siQuery.getRealCost2());
			stf.setBinCodeBegin(siQuery.getBinCode1());
			stf.setBinCodeEnd(siQuery.getBinCode2());
			stf.setTakeStatus("A");
			
			if(this.getDao().insert(stf)){
				siQuery.setCreateBy(null);
				String strWhere=getStockInfoWhere(siQuery);
				
				String strHql="select min(sif.stockId),sif.skuCode,sif.stuffNo," +
					"sum(sif.skuNum),sif.skuUnit " +
					"from StockInfoForm as sif where sif.skuNum>0 "+strWhere+
					" group by sif.stuffNo,sif.skuUnit "+
					" order by  sif.stuffNo";
				ArrayList dataList=(ArrayList)this.getDao().list(strHql);
				
				ArrayList addList=new ArrayList();
				//StockInfoForm sif=new StockInfoForm();
				for (int i=0;i<dataList.size();i++) {
					
					Object[] obj = (Object[])dataList.get(i);
					
					StockTakeDetailForm stdf=new StockTakeDetailForm();
					stdf.setStockId((Long)obj[0]);
					stdf.setStockTakeId(stf.getStockTakeId());
					stdf.setSkuCode((String)obj[1]);
					stdf.setStockNum(new Integer(obj[3].toString()));
					stdf.setCreateBy(siQuery.getCreateBy());
					stdf.setCreateDate(siQuery.getCreateDate());
					stdf.setStuffNo((String)obj[2]);
					stdf.setSkuUnit((String)obj[4]);
					
					addList.add(stdf);
				}
				if(this.getBatchDao().insertBatch(addList)){
					takeId=stf.getStockTakeId().intValue();
					//将该维修站的库存锁住，盘点结束再放开
					lockStock(siQuery);
				}
			}
			
						
		}catch(Exception e) {
			e.printStackTrace();
		} 
		return takeId;
	}
	

	/**
	 * 将某维修站的库存锁住
	 * @param Long 部门id
	 * @return 
	 */
	public void lockStock(StockInfoForm siQuery) {
		try{
//			ArrayList updateList=new ArrayList();
//			String strHql="from StockInfoForm as sif where sif.stockStatus='A' " +
//				getStockInfoWhere(siQuery);
//			ArrayList dataList=(ArrayList)this.getDao().list(strHql);
//			for(int i=0;i<dataList.size();i++){
//				StockInfoForm sif=(StockInfoForm)dataList.get(i);
//				sif.setStockStatus("T");	//盘点状态
//				updateList.add(sif);
//			}
//			this.getBatchDao().updateBatch(updateList);
			
			String strHql="update StockInfoForm as sif set sif.stockStatus='T' " +
					"where sif.stockStatus='A' and sif.skuNum>0 " + getStockInfoWhere(siQuery);
			this.getDao().execute(strHql);
			
		}catch(Exception e) {
			e.printStackTrace();
		} 
	}

	/**
	 * 将某维修站的库存解锁
	 * @param Long 部门id
	 * @return 
	 */
	public void unLockStock() {
		try{
//			ArrayList updateList=new ArrayList();
//			String strHql="from StockInfoForm as sif where sif.stockStatus='T' " ;
//			ArrayList dataList=(ArrayList)this.getDao().list(strHql);
//			for(int i=0;i<dataList.size();i++){
//				StockInfoForm sif=(StockInfoForm)dataList.get(i);
//				sif.setStockStatus("A");	//可用库存
//				updateList.add(sif);
//			}
//			this.getBatchDao().updateBatch(updateList);
//			
			String strHql="update StockInfoForm as sif set sif.stockStatus='A' where sif.stockStatus='T' ";
			this.getDao().execute(strHql);
			
		}catch(Exception e) {
			e.printStackTrace();
		} 
	}

	/**
	 * 一次盘点新增，将补充盘点信息插入td_stock_take_detail表
	 * @param StockTakeDetailForm - 盘点的条件
	 * @return 是否成功
	 */
	public int firstAdd(StockTakeDetailForm stdf) {
		int pkId=-1;
		try{
			if(this.getDao().insert(stdf)){
				pkId=stdf.getStockTakeDetailId().intValue();
			}
		}catch(Exception e) {
			e.printStackTrace();
		} 
		return pkId;
	}
	

	/**
	 * 盘点暂存，修改td_stock_take_detail表
	 * @param String - 明细id
	 * @param String 盘点数量
	 * @return 是否成功
	 */
	public int tempSave(String stockTakeDetailId,String takeNum) {
		int tag=-1;
		Operate op=new Operate();
		try{
			ArrayList updateList = new ArrayList();
			ArrayList dataList = new ArrayList();
			String[] takeNums=takeNum.split(",");
			String[] detailIds=op.arrayToString2(stockTakeDetailId.split(","));
			for(int i=0;i<detailIds.length;i++){
				String strHql="from StockTakeDetailForm as stdf where stdf.stockTakeDetailId in ("+detailIds[i]+") order by stdf.stockTakeDetailId";
				ArrayList tempList=(ArrayList)this.getDao().list(strHql);
				dataList.addAll(tempList);
			}
			
			StockTakeDetailForm stdf = new StockTakeDetailForm();
			for(int i=0;i<dataList.size();i++){
				stdf=(StockTakeDetailForm)dataList.get(i);
				int outNum=Integer.parseInt(takeNums[i]);
				int inNum=stdf.getTakeNum()==null?0:stdf.getTakeNum().intValue();
				//System.out.println("-------------inNum="+inNum);
				if(inNum==0||outNum!=inNum){
					//System.out.println("-------------outNum="+outNum);
					int stockNum=stdf.getStockNum()==null?0:stdf.getStockNum().intValue();
					stdf.setTakeNum(new Integer(outNum));
					stdf.setDiffNum(new Integer(stockNum-outNum));
					updateList.add(stdf);
				}
			}
			if(this.getBatchDao().updateBatch(updateList)) tag=1;
			
		}catch(Exception e) {
			e.printStackTrace();
		} 
		return tag;
	}
	
	
	/**
	 * 一次盘点,比较数据库中库存数量和输入的盘点数量，取出差异数据
	 * @param String 盘点记录id
	 * @param String[] 盘点真实数量数组
	 * @return 差异列表
	 */
	public ArrayList takeDiffFirstList(String stockTakeId,String[] takeNums) {
		
		ArrayList diffList = new ArrayList();
		ArrayList updateList = new ArrayList();
		try{
			String strHql="from StockTakeDetailForm as stdf where stdf.stockTakeId="+
				stockTakeId+" order by stdf.stockTakeDetailId";
			ArrayList allList=(ArrayList)this.getDao().list(strHql);
			StockTakeDetailForm stdf=new StockTakeDetailForm();
			for(int i=0;i<allList.size();i++){
				stdf=(StockTakeDetailForm)allList.get(i);
				String[] diffArray = new String[6];
								
				int stockNum=stdf.getStockNum()==null?0:stdf.getStockNum().intValue();
				int takeNum=Integer.parseInt(takeNums[i]);
				
				//一次盘点过程中新增的数据或库存数量和盘点数量不一致的数据
				if(stockNum==0||stockNum!=takeNum){	
					diffArray[0]=stdf.getStockTakeDetailId().toString();
					diffArray[1]=stdf.getBinCode();
					diffArray[2]=stdf.getStuffNo();
					diffArray[3]=stdf.getSkuCode();
					diffArray[4]=stockNum+"";
					diffArray[5]=takeNums[i];
					
					diffList.add(diffArray);
				}
				int inNum=stdf.getTakeNum()==null?0:stdf.getTakeNum().intValue();
				if(inNum==0||takeNum!=inNum){
					stdf.setTakeNum(new Integer(takeNum));
					stdf.setDiffNum(new Integer(stockNum-takeNum));
					updateList.add(stdf);
				}
				this.getBatchDao().updateBatch(updateList);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		} 
		return diffList;
	}
	


	/**
	 * 一次盘点确认，修改差异数据，判断是否进入二次盘点<br>
	 * 有差异时增加报表的统计字段数据
	 * @param String 盘点记录id
	 * @param String[] 盘点真实数量数组
	 * @return 
	 */
	public int firstConfirm(String stockTakeId,String[] takeNums) {
		int tag=-1;
		ArrayList modList = new ArrayList();
		HashSet hsTake=new HashSet();
		HashSet hsStock=new HashSet();
		int stockAllNum=0;
		int takeAllNum=0;
		double stockAllPrice=0;
		double takeAllPrice=0;
		
		int allDiffNum=0;
		CommonSearch cs = CommonSearch.getInstance();
		try{
			String strHql="from StockTakeDetailForm as stdf where stdf.stockTakeId="+
				stockTakeId+" order by stdf.stockTakeDetailId";
			ArrayList allList=(ArrayList)this.getDao().list(strHql);
			StockTakeDetailForm stdf=null;
			for(int i=0;i<allList.size();i++){
				stdf=(StockTakeDetailForm)allList.get(i);
				//String[] diffArray = new String[10];
				//库存数量
				int stockNum=stdf.getStockNum()==null?0:stdf.getStockNum().intValue();
				//本次盘点数量
				int takeNum=Integer.parseInt(takeNums[i]);
				//上次盘点数量
				int inNum=stdf.getTakeNum()==null?0:stdf.getTakeNum().intValue();
				//如果第一次输入盘点数量 或 上次数量和这次不同
				if(inNum==0||takeNum!=inNum){
					stdf.setTakeNum(new Integer(takeNum));
					System.out.println("---stockNum="+stockNum);
					System.out.println("---takeNum="+takeNum);
					stdf.setDiffNum(new Integer(stockNum-takeNum));
					modList.add(stdf);
				}
				//本次盘点数量和库存数量不符
				if(stockNum-takeNum!=0){
					allDiffNum++;
				}
				double stockPrice=0,takePrice=0;
				if(stockNum!=0){
					hsStock.add(stdf.getStuffNo());
					stockAllNum+=stockNum;
					//System.out.println("-------11-----stockAllNum="+stockAllNum);
					Float costTemp=getPerCostById(stdf.getStockId());
					stockPrice=costTemp==null?0:costTemp.floatValue();
					stockAllPrice+=stockPrice;
				}
				if(takeNum!=0){
					hsTake.add(stdf.getStuffNo());
					takeAllNum+=takeNum;
					//System.out.println("-----22-------takeAllNum="+takeAllNum);
					if(stockNum!=0&&stockNum==takeNum){ 
						takePrice=stockPrice; 
					}else if(stdf.getStockId()!=null&&stdf.getStockId().intValue()!=0){ 
						Float costTemp=getPerCostById(stdf.getStockId());
						takePrice=costTemp==null?0:costTemp.floatValue();
					}else{ 
						Float costTemp=cs.getPartPerCost(stdf.getStuffNo());
						takePrice=costTemp==null?0:costTemp.floatValue()*takeNum; 
					}
					takeAllPrice+=takePrice;
				}
			}
			if(allDiffNum==0){		//无差异，进入盘点报表
				StockTakeForm stf=this.getTakeInfo(new Long(stockTakeId));
				stf.setTakeStatus("D");		//盘点结束
				stf.setStockTakeResult("W");//无差异
				stf.setEndDate(new Date());
				stf.setUpdateDate(new Date());

				//增加报表的统计字段数据
				stf.setCatNum(new Integer(hsTake.size()));
				stf.setCatRealNum(new Integer(hsStock.size()));
				stf.setSkuNum(new Integer(takeAllNum));
				stf.setSkuRealNum(new Integer(stockAllNum));
				stf.setRealPrice(new Double(stockAllPrice));
				stf.setPrice(new Double(takeAllPrice));
				
				modList.add(stf);
				if(this.getBatchDao().updateBatch(modList)){
					unLockStock();
					tag=0;
				}
			}else{						//有差异，进入二次盘点
				StockTakeForm stf=this.getTakeInfo(new Long(stockTakeId));
				stf.setTakeStatus("B");		//二次盘点
				//增加报表的统计字段数据
				stf.setCatNum(new Integer(hsTake.size()));
				stf.setCatRealNum(new Integer(hsStock.size()));
				stf.setSkuNum(new Integer(takeAllNum));
				stf.setSkuRealNum(new Integer(stockAllNum));
				stf.setRealPrice(new Double(stockAllPrice));
				stf.setPrice(new Double(takeAllPrice));
				
				modList.add(stf);
				if(this.getBatchDao().updateBatch(modList)){
					tag=1;
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			//takeCancel(new Long(stockTakeId));
		} 
		return tag;
	}
	

	/**
	 * 二次盘点显示数据
	 * @param Long 盘点记录id
	 * @return 二次盘点纪录的list
	 */
	public ArrayList takeSecondList(Long takeId) {
		ArrayList secondList = new ArrayList();
		try{
			String strHql="from StockTakeDetailForm as stdf where stdf.stockTakeId="+
				takeId+" and stdf.diffNum!=0 order by stdf.stockTakeDetailId";
			ArrayList allList=(ArrayList)this.getDao().list(strHql);
			StockTakeDetailForm stdf=new StockTakeDetailForm();
			for(int i=0;i<allList.size();i++){
				stdf=(StockTakeDetailForm)allList.get(i);
				String[] diffArray = new String[6];

				diffArray[0]=stdf.getStockTakeDetailId().toString();
				diffArray[1]=stdf.getBinCode();
				diffArray[2]=stdf.getStuffNo();
				diffArray[3]=stdf.getSkuCode();
				diffArray[4]=stdf.getStockNum()==null?"":stdf.getStockNum().toString();
				diffArray[5]=stdf.getTakeNum()==null?"":stdf.getTakeNum().toString();
					
				secondList.add(diffArray);
			}
		}catch(Exception e) {
			e.printStackTrace();
		} 
		return secondList;
	}

	/**
	 * 二次盘点,比较数据库中库存数量和输入的盘点数量，取出差异数据
	 * @param String 盘点记录id
	 * @param String[] 盘点真实数量数组
	 * @return 差异列表
	 */
	public ArrayList takeDiffSecondList(Long takeId,String[] takeNums) {
		
		ArrayList diffList = new ArrayList();
		ArrayList updateList = new ArrayList();
		CommonSearch cs = CommonSearch.getInstance();
		try{
			String strHql="from StockTakeDetailForm as stdf where stdf.stockTakeId="+
				takeId+" and stdf.diffNum!=0 order by stdf.stockTakeDetailId";
			ArrayList allList=(ArrayList)this.getDao().list(strHql);
			StockTakeDetailForm stdf=new StockTakeDetailForm();
			StockTakeForm stf=getTakeInfo(takeId);
			int takeAllNum=stf.getSkuNum().intValue();
			double takeAllPrice=stf.getPrice().doubleValue();
			boolean flag=false;
			
			for(int i=0;i<allList.size();i++){
				stdf=(StockTakeDetailForm)allList.get(i);
				String[] diffArray = new String[8];
								
				int stockNum=stdf.getStockNum()==null?0:stdf.getStockNum().intValue();
				int takeNum=Integer.parseInt(takeNums[i]);
				
				//一次盘点过程中新增的数据或库存数量和盘点数量不一致的数据
				if(stockNum!=takeNum){	
					diffArray[0]=stdf.getStockTakeDetailId().toString();
					diffArray[1]=stdf.getBinCode();
					diffArray[2]=stdf.getStuffNo();
					diffArray[3]=stdf.getSkuCode();
					diffArray[4]=stockNum+"";
					diffArray[5]=takeNum+"";
					diffArray[6]=stdf.getSkuUnit()==null?"":stdf.getSkuUnit();
					diffArray[7]=stdf.getStockId()==null?"":stdf.getStockId().toString();
					
					
					diffList.add(diffArray);
				}
				int inNum=stdf.getTakeNum()==null?0:stdf.getTakeNum().intValue();
				if(takeNum!=inNum){
					stdf.setTakeNum(new Integer(takeNum));
					stdf.setDiffNum(new Integer(stockNum-takeNum));
					updateList.add(stdf);
					
					int tempNum=takeNum-inNum;
					takeAllNum+=tempNum;
					if(stdf.getStockId()!=null&&stdf.getStockId().intValue()!=0){
						takeAllPrice+=getPerCostById(stdf.getStockId()).doubleValue();
					}else{
						takeAllPrice+=cs.getPartPerCost(stdf.getStuffNo()).doubleValue()*tempNum;
					}
					flag=true;
				}//end if
			}//end for
			if(flag){
				stf.setSkuNum(new Integer(takeAllNum));
				stf.setPrice(new Double(takeAllPrice));
				updateList.add(stf);
				if(!this.getBatchDao().updateBatch(updateList)) diffList=null;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		} 
		return diffList;
	}
	
	
	/**
	 * 自动移库,同一仓库(STOCK_CODE)中的同一种零件（SKU_CODE），如果盘点数量差异总数为0（多的零件和少的零件数量一致），
	 * 	则将库存的零件记录的仓库仓位依次移到盘点记录的仓库仓位中，最后修改盘点明细表和库存信息表
	 * @param String 盘点记录id
	 * @param String[] 盘点真实数量数组
	 * @param Long 部门id
	 * @return 是否成功
	 */
	public int autoTransfer(Long takeId,String[] takeNums) {
		int tag=-1;
		
		try{
			ArrayList saveList=takeDiffSecondList(takeId,takeNums);
			
			if(saveList!=null&&saveList.size()>0){
				//System.out.println("-------------saveList="+saveList.size());
				HashSet hs=new HashSet();
				for(int i=0;i<saveList.size();i++){
					String[] temp=(String[])saveList.get(i);
					hs.add("('"+temp[1]+"','"+temp[3]+"')");
				}
				String diffPartCode=Operate.arrayListToString(new ArrayList(hs));
				
				//盘点数量差异总数为0的零件
				String strHql="select t1.skuCode,t1.stockCode from StockTakeDetailForm as t1 where t1.stockTakeId="+takeId+
					" and (t1.stockCode,t1.skuCode) in("+diffPartCode+") group by t1.stockCode,t1.skuCode having sum(t1.diffNum)=0 ";
				ArrayList skuCodeList=(ArrayList)this.getDao().list(strHql);
				
				if(skuCodeList.size()>0){		//如果有差异零件
					ArrayList editList=new ArrayList();

					for(int i=0;i<skuCodeList.size();i++){
						Object[] objTemp=(Object[])skuCodeList.get(i);
						String skuCode=objTemp[0].toString();
						String stockCode=objTemp[1].toString();

						//某种可移库零件的盘点信息
						ArrayList skuTakeList=(ArrayList)this.getDao().list("from StockTakeDetailForm as td where td.skuCode='"+
							skuCode+"' and td.stockTakeId="+takeId+" and td.stockCode='"+stockCode+"'");
						
						for(int j=0;j<skuTakeList.size();j++){
							StockTakeDetailForm stdf=(StockTakeDetailForm)skuTakeList.get(j);
							//修改盘点明细，将该种零件库存数量改为盘点数量，差异为0
							stdf.setStockNum(stdf.getTakeNum());
							stdf.setDiffNum(new Integer(0));
							editList.add(stdf);
							//修改库存信息
							ArrayList sifList=getInitStock(stdf.getStockId(),stdf.getTakeNum().intValue());
							editList.addAll(sifList);
						}
					}//end for skuCodeList

					//修改td_stock_take_detail和td_stock_info
					if(this.getBatchDao().updateBatch(editList)){
						//将库存数量为0的库存删除
						String strDel="from StockInfoForm as sif where  sif.skuNum=0";
						ArrayList deleteList=(ArrayList)this.getDao().list(strDel);
						if(this.getBatchDao().deleteBatch(deleteList))	tag=1;
					}
					/*
					for(int i=0;i<skuCodeList.size();i++){
						Object[] objTemp=(Object[])skuCodeList.get(i);
						String skuCode=objTemp[0].toString();
						String stockCode=objTemp[1].toString();
						//可移库零件的盘点信息
						ArrayList skuTakeList=(ArrayList)this.getDao().list("from StockTakeDetailForm as td where td.skuCode='"+
							skuCode+"' and td.stockTakeId="+takeId+" and td.stockCode='"+stockCode+"' order by td.binCode");
						//可移库零件的仓位信息
						ArrayList stockBinList=(ArrayList)this.getDao().list("select td.stockCode,td.binCode,sum(td.takeNum) " +
							"from StockTakeDetailForm as td group by td.stockCode,td.binCode,td.skuCode " +
							"having td.skuCode='"+skuCode+"' and td.stockCode='"+stockCode+"' and sum(td.stockNum)!=0 " +
							"order by td.binCode");
						
						for(int j=0;j<skuTakeList.size();j++){
							StockTakeDetailForm stdf=(StockTakeDetailForm)skuTakeList.get(j);
							int intStock=stdf.getStockNum().intValue();
							if(intStock==0) continue;
							StockInfoForm sif=getStockInfo(stdf.getStockId());
							stdf.setDiffNum(new Integer(0));
							String logBinCode=stdf.getBinCode();
							
							String editFlag="u";		//默认为修改仓库仓位
							
							while(stockBinList.size()>0){
								int sumTake=0,takeRest=0;
								Object[] obj=(Object[])stockBinList.get(0);
								String takeBinCode=(String)obj[1];
								sumTake=((Integer)obj[2]).intValue();
							 // modified by xt	System.out.println("---------takeBinCode="+takeBinCode+"   sumTake="+sumTake);
								if(intStock<=sumTake){	//逻辑库存<=物理库存，可以移入
									if(editFlag.equals("i")){					//如果新增
										StockTakeDetailForm stdf2=getNewDetailForm(stdf);
										stdf2.setStockCode((String)obj[0]);
										stdf2.setBinCode(takeBinCode);
										stdf2.setStockNum(new Integer(intStock));
										stdf2.setTakeNum(new Integer(intStock));
										
										StockInfoForm sif2=getNewStockForm(sif);
										sif2.setStockCode((String)obj[0]);
										sif2.setBinCode(takeBinCode);
										sif2.setSkuNum(new Integer(intStock));
										
										editList.add(stdf2);
										//editList.add(sif2);
									}else if(!logBinCode.equals(takeBinCode)){	//如果仓位不同
										stdf.setStockCode((String)obj[0]);
										stdf.setBinCode(takeBinCode);
										stdf.setStockNum(new Integer(intStock));
										stdf.setTakeNum(new Integer(intStock));
										
										sif.setStockCode((String)obj[0]);
										sif.setBinCode(takeBinCode);
										sif.setSkuNum(new Integer(intStock));
										
										editList.add(stdf);
										//editList.add(sif);
									}
									stockBinList.remove(0);					//先将第一行移除（物理）
									takeRest=sumTake-intStock;
									if(takeRest>0){						//如果该仓位还有剩余，再将剩余数量加入（物理）
										obj[2]=new Integer(takeRest);
										stockBinList.add(0,obj);
									}
									break;									//下一行盘点记录（逻辑）
								}else{					//逻辑库存>物理库存，需要做拆分
									if(editFlag.equals("i")){					//如果新增
										StockTakeDetailForm stdf2=getNewDetailForm(stdf);
										stdf2.setStockCode((String)obj[0]);
										stdf2.setBinCode(takeBinCode);
										stdf2.setStockNum(new Integer(sumTake));
										stdf2.setTakeNum(new Integer(sumTake));

										StockInfoForm sif2=getNewStockForm(sif);
										sif2.setStockCode((String)obj[0]);
										sif2.setBinCode(takeBinCode);
										sif2.setSkuNum(new Integer(sumTake));
										
										editList.add(stdf2);
										//editList.add(sif2);
									}else{
										stdf.setStockCode((String)obj[0]);
										stdf.setBinCode(takeBinCode);
										stdf.setStockNum(new Integer(sumTake));
										stdf.setTakeNum(new Integer(sumTake));
										
										sif.setStockCode((String)obj[0]);
										sif.setBinCode(takeBinCode);
										sif.setSkuNum(new Integer(sumTake));
										
										editList.add(stdf);
										//editList.add(sif);
									}
									
									intStock=intStock-sumTake;				//剩余数量继续移（逻辑）
									stockBinList.remove(0);					//将第一行已放满的仓位移除（物理）
									editFlag="i";							//由于做过拆分，下次记录为新增
								}
							}//end while
							
						}//end for skuTakeList
						
					}//end for skuCodeList
					
					//修改td_stock_take_detail和td_stock_info
					if(this.getBatchDao().saveOrUpdateBatch(editList)){
						tag=1;
					}
					*/
				}else{
					tag=0;
				}
			}else{
				tag=0;
			}
			
				
		}catch(Exception e) {
			e.printStackTrace();
		} 
		return tag;
	}
	

	
	
	
	/**
	 * 二次盘点确认，判断盘点结果
	 * @param String 盘点记录id
	 * @param String[] 盘点真实数量数组
	 * @return 是否成功
	 */
	public int secondConfirm(Long takeId,String[] takeNums){
		int tag=-1;
		ArrayList dataList=new ArrayList();
		try{
			ArrayList diffList=takeDiffSecondList(takeId,takeNums);
			if(diffList!=null){
				StockTakeForm stf=getTakeInfo(takeId);
				if(diffList.size()==0){
					stf.setStockTakeResult("W");	//无差异
				}else{
					ArrayList al=getReportData(stf,diffList);
					//Object[] ojb={strf,"i"};
					dataList.addAll(al);
					//盘点数量差异总数为0的零件
					String strHql="select sum(stdf.diffNum) from StockTakeDetailForm as stdf where stdf.stockTakeDetailId in " +
						"(select t1.stockTakeDetailId from StockTakeDetailForm as t1 where t1.diffNum!=0 and t1.stockTakeId="+
						takeId+") group by stdf.stuffNo  ";
					Long allDiffNum=(Long)this.getDao().uniqueResult(strHql);
					
					if(allDiffNum==null||allDiffNum.intValue()==0){
						stf.setStockTakeResult("C");//仓位差异
					}else{
						stf.setStockTakeResult("S");//数量差异
					}
				}
				stf.setEndDate(new Date());
				stf.setUpdateDate(new Date());
				stf.setTakeStatus("D");
				//Object[] obj2={stf,"u"};
				dataList.add(stf);
				
				if(this.getBatchDao().saveOrUpdateBatch(dataList)){
					deleteDetail(takeId);
					unLockStock();
					tag=1;
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		} 
		return tag;
		
	}
	

	
	/**
	 * 盘点报表生成
	 * @param 盘点记录form
	 * @param ArrayList 差异数据集合
	 * @return ArrayList 报表数据
	 */
	public ArrayList getReportData(StockTakeForm stf,ArrayList diffList) {
		ArrayList dataList = new ArrayList();
		
		CommonSearch cs = CommonSearch.getInstance();
		try{
			
			for(int i=0;i<diffList.size();i++){
				StockTakeReportForm strf=new StockTakeReportForm();
				strf.setStockTakeId(stf.getStockTakeId());
				strf.setCreateBy(stf.getCreateBy());
				
				String[] diffArray=(String[])diffList.get(i);
				strf.setStuffNo(diffArray[2]);
				strf.setSkuCode(diffArray[3]);
				int diffNum=Integer.parseInt(diffArray[5])-Integer.parseInt(diffArray[4]);
				
				if(diffNum==0){
					strf.setDiffType("C");
				}else if(diffNum>0){
					strf.setDiffType("A");
				}else if(diffNum<0){
					strf.setDiffType("B");
					diffNum=-1*diffNum;
				}
				
				Float perCost=null;
				double allCost=0;
				if("0".equals(diffArray[7])){
					perCost=cs.getPartPerCost(diffArray[2]);
				}else{
					perCost=getPerCostById(new Long(diffArray[7]));
				}
				if(perCost!=null) allCost=perCost==null?0:perCost.floatValue()*diffNum;
				strf.setDiffNum(new Integer(diffNum));
				strf.setDiffPrice(new Double(allCost));
				
				dataList.add(strf);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		} 
		return dataList;	
	}
	
	
	/**
	 * 盘点取消
	 * @param Long 盘点记录id
	 * @return 是否成功
	 */
	public int takeCancel(Long takeId) {
		int tag=-1;

		try{
			StockTakeForm stf =this.getTakeInfo(takeId);
			stf.setEndDate(new Date());
			stf.setTakeStatus("C");
			stf.setUpdateDate(new Date());
			
			if(this.getDao().update(stf)){ 
				deleteDetailAndRpt(takeId);
				unLockStock();
				tag=1;
			}
			
					
		}catch(Exception e) {
			e.printStackTrace();
		} 
		return tag;
	}
	

	/**
	 * 删除详细信息
	 * @param Long 盘点记录id
	 * @return 是否成功
	 */
	public boolean deleteDetail(Long takeId) {
		boolean tag=false;

		try{
//			ArrayList delList=(ArrayList)this.getDao().list("from StockTakeDetailForm as stdf where stdf.stockTakeId="+takeId);
//			tag=this.getBatchDao().deleteBatch(delList);
			String strHql="delete from StockTakeDetailForm as stdf where stdf.stockTakeId="+takeId;
			if(this.getDao().execute(strHql)>=0){
				tag=true;
			}
					
		}catch(Exception e) {
			e.printStackTrace();
		} 
		return tag;
	}

	/**
	 * 删除详细信息和报表信息
	 * @param Long 盘点记录id
	 * @return 是否成功
	 */
	public boolean deleteDetailAndRpt(Long takeId) {
		boolean tag=false;

		try{
//			ArrayList delList=(ArrayList)this.getDao().list("from StockTakeDetailForm as stdf where stdf.stockTakeId="+takeId);
//			ArrayList delList2=(ArrayList)this.getDao().list("from StockTakeReportForm as stdf where stdf.stockTakeId="+takeId);
//			delList.addAll(delList2);
//			tag=this.getBatchDao().deleteBatch(delList);
			
			ArrayList delList=new ArrayList();
			delList.add("delete from StockTakeDetailForm as stdf where stdf.stockTakeId="+takeId);
			delList.add("delete from StockTakeReportForm as stdf where stdf.stockTakeId="+takeId);
			this.getBatchDao().excuteBatch(delList);
					
		}catch(Exception e) {
			e.printStackTrace();
		} 
		return tag;
	}
	
	/**
	 * 查询显示库存盘点报表清单
	 * @param StockTakeForm 查询条件
	 * @return 返回的列表数据
	 */	
	public ArrayList stockReprotList(StockTakeForm stQuery) {
		List dataList = new ArrayList();
		ArrayList alData = new ArrayList();
		StockTakeQuery stq = new StockTakeQuery(stQuery);
		CommonSearch cs=CommonSearch.getInstance();
		int count=0;
		try {
			dataList=stq.doListQuery(stQuery.getFromPage(),stQuery.getToPage());
			
			count=stq.doCountQuery();

			StockTakeForm stf=new StockTakeForm();

			for (int i=0;i<dataList.size();i++) {
				String[] data = new String[6];
				stf = (StockTakeForm)dataList.get(i);
				data[0] = stf.getStockTakeId().toString();
				data[1] = stf.getStockTakeId().toString();
				data[2] = cs.findUserNameByUserId(stf.getOperater());
				data[3] = stf.getBeginDate().toString();
				data[4] = stf.getEndDate()==null?"":stf.getEndDate().toString();
				data[5] = DicInit.getSystemName("STOCK_TAKE_RESULT",stf.getStockTakeResult());
				
				alData.add(data);
			}
			alData.add(0,count+"");

		} catch(Exception e) {
			e.printStackTrace();
		}
		return alData;
	}

	/**
	 * 查询显示库存盘点报表清单
	 * @param StockTakeReportForm 查询条件
	 * @param StockTakeForm 翻页条件
	 * @return 返回的列表数据
	 */	
	public ArrayList stockReportDetailList(StockTakeReportForm stQuery,StockTakeForm tf) {
		List dataList = new ArrayList();
		ArrayList alData = new ArrayList();
		StockTakeReportQuery stq = new StockTakeReportQuery(stQuery);
		int count=0;
		try {
			//dataList=stq.doListQuery(stQuery.getFromPage(),stQuery.getToPage());
			dataList=stq.doListQuery(tf.getFromPage(),tf.getToPage());
			count=stq.doCountQuery();

			StockTakeReportForm stf=new StockTakeReportForm();

			for (int i=0;dataList!=null && i<dataList.size();i++) {
				String[] data = new String[8];
				stf = (StockTakeReportForm)dataList.get(i);
				data[0]=""+(i+1);
				data[1] = stf.getBinCode()==null?"":stf.getBinCode();
				data[2] = stf.getStuffNo()==null?"":stf.getStuffNo();
				data[3] = stf.getSkuCode()==null?"":stf.getSkuCode();
				
				if(stf.getDiffType()!=null)
				{
					if("A".equals(stf.getDiffType().toUpperCase()))
					{
						data[4]="过剩";
					}
					else if("B".equals(stf.getDiffType().toUpperCase()))
					{
						data[4]="短缺";
					}
					else if("C".equals(stf.getDiffType().toUpperCase()))
					{
						data[4]="位置差异";
					}
				}
				data[5] = stf.getDiffNum()==null?"":stf.getDiffNum().toString();
				if(stf.getDiffPrice()!=null && stf.getDiffPrice().doubleValue()!=0 && stf.getDiffNum()!=null && stf.getDiffNum().intValue()!=0)
				{
					data[6] = Math.round(stf.getDiffPrice().doubleValue()/stf.getDiffNum().intValue()) +"";
				}
				else
				{
				data[6] = "0";
				}
				data[7] =stf.getDiffPrice()==null?"":Operate.toFix(stf.getDiffPrice(), 2);
				alData.add(data);
			}
			alData.add(0,count+"");

		} catch(Exception e) {
			e.printStackTrace();
		}
		return alData;
	}
	/**
	 * 查询显示库存盘点报表时的相关统计数据清单
	 * @param StockTakeReportForm 查询条件
	 * @return 返回的列表数据
	 */		
	public String [] stockReportStat(StockTakeReportForm stQuery) {
		List dataList = new ArrayList();
		ArrayList alData = new ArrayList();
		StockTakeReportQuery stq = new StockTakeReportQuery(stQuery);
		String [] mainTake=new String [27];
		for(int i=0;i<24;i++)
		{
			mainTake[i]="0";
		}	
		
		int [] intStat=new int[12];
		try {
			dataList=stq.doListQuery();
			

			StockTakeReportForm stf=new StockTakeReportForm();
			
			for(int i=0;i<12;i++)
			{
				intStat[i]=0;
			}
			ArrayList dq=new ArrayList();
			ArrayList wj=new ArrayList();
			ArrayList gs=new ArrayList();
			ArrayList wc=new ArrayList();
			
			for (int i=0;dataList!=null && i<dataList.size();i++) {
				int flag=0;
				stf = (StockTakeReportForm)dataList.get(i);
				if(stf.getLendNum()!=null && stf.getLendNum().intValue()>0)
				{//外借
					intStat[0]+=1;
					if(wj!=null && wj.size()>0)
					{
						for(int k=0;k<wj.size();k++)
						{
							if(wj.get(k)!=null&&((String)wj.get(k)).equals(stf.getPartMajorCat()))
							{
								intStat[0]-=1;
								flag=1;
							}							
						}
						if(flag==0)
						{
							wj.add(stf.getPartMajorCat());
						}
					}
					else
					{
						wj.add(stf.getPartMajorCat());
					}
					intStat[1]+=stf.getLendNum()==null?0:stf.getLendNum().intValue();
					intStat[2]+=stf.getDiffPrice()==null?0:stf.getDiffPrice().intValue();
					
					//intStat[2]+=(stf.getLendNum()==null?0:stf.getLendNum().intValue())*(stf.getDiffPrice()==null?0:stf.getDiffPrice().intValue());
				}
				if(stf.getDiffType()!=null && !"".equals(stf.getDiffType()))
				{
					if("B".equals(stf.getDiffType()))
					{//短缺
						intStat[3]+=1;
						if(dq!=null && dq.size()>0)
						{
							for(int k=0;k<dq.size();k++)
							{
								if(dq.get(k)!=null&&((String)dq.get(k)).equals(stf.getPartMajorCat()))
								{
									intStat[3]-=1;
									flag=1;
								}							
							}
							if(flag==0)
							{
								dq.add(stf.getPartMajorCat());
							}

						}
						else
						{
							dq.add(stf.getPartMajorCat());
						}
						intStat[4]+=stf.getDiffNum()==null?0:stf.getDiffNum().intValue();
						intStat[5]+=stf.getDiffPrice()==null?0:stf.getDiffPrice().intValue();

						//intStat[5]+=(stf.getDiffNum()==null?0:stf.getDiffNum().intValue())*(stf.getDiffPrice()==null?0:stf.getDiffPrice().intValue());
					}
					if("A".equals(stf.getDiffType()))
					{//过剩
						intStat[6]+=1;
						if(gs!=null && gs.size()>0)
						{
							for(int k=0;k<gs.size();k++)
							{
								if(gs.get(k)!=null&&((String)gs.get(k)).equals(stf.getPartMajorCat()))
								{
									intStat[6]-=1;
									flag=1;
								}							
							}
							if(flag==0)
							{
								gs.add(stf.getPartMajorCat());
							}

						}
						else
						{
							gs.add(stf.getPartMajorCat());
						}
						intStat[7]+=stf.getDiffNum()==null?0:stf.getDiffNum().intValue();
						intStat[8]+=stf.getDiffPrice()==null?0:stf.getDiffPrice().intValue();

						//intStat[8]+=(stf.getDiffNum()==null?0:stf.getDiffNum().intValue())*(stf.getDiffPrice()==null?0:stf.getDiffPrice().intValue());
					}
					if("C".equals(stf.getDiffType()))
					{//仓位差异
						intStat[9]+=1;
						if(wc!=null && wc.size()>0)
						{
							for(int k=0;k<wc.size();k++)
							{
								if(wc.get(k)!=null&&((String)wc.get(k)).equals(stf.getPartMajorCat()))
								{
									intStat[9]-=1;
									flag=1;
								}							
							}
							if(flag==0)
							{
								wc.add(stf.getPartMajorCat());
							}

						}
						else
						{
							wc.add(stf.getPartMajorCat());
						}
						
						intStat[10]+=stf.getDiffNum()==null?0:stf.getDiffNum().intValue();
						intStat[11]+=stf.getDiffPrice()==null?0:stf.getDiffPrice().intValue();
						//intStat[11]+=(stf.getDiffNum()==null?0:stf.getDiffNum().intValue())*(stf.getDiffPrice()==null?0:stf.getDiffPrice().intValue());
					}
					
				}

			}

			StockTakeForm stockTake=getTakeInfo(stQuery.getStockTakeId());
			if(stockTake!=null)
			{

				mainTake[0]=stockTake.getCatNum()==null?"":stockTake.getCatNum().toString();
				mainTake[1]=stockTake.getSkuNum()==null?"":stockTake.getSkuNum().toString();
				//mainTake[2]=stockTake.getSkuNum()==null?"":(stockTake.getSkuNum().intValue()*stockTake.getPrice().longValue()) + "";
				mainTake[2]=stockTake.getPrice()==null?"":stockTake.getPrice().longValue() + "";
				mainTake[3]=stockTake.getCatRealNum()==null?"":stockTake.getCatRealNum().toString();
				mainTake[4]=stockTake.getSkuRealNum()==null?"":stockTake.getSkuRealNum().toString();
				//mainTake[5]=stockTake.getSkuRealNum()==null?"":(stockTake.getSkuRealNum().intValue()*stockTake.getRealPrice().longValue()) + "";
				mainTake[5]=stockTake.getRealPrice()==null?"":stockTake.getRealPrice().longValue() + "";
				mainTake[6]=intStat[0]==0?"":intStat[0]+"";
				mainTake[7]=intStat[1]==0?"":intStat[1]+"";
				mainTake[8]=intStat[2]==0?"":intStat[2]+"";
				mainTake[9]=(intStat[3]+intStat[6]+intStat[9])==0?"":intStat[3]+intStat[6]+intStat[9]+"";
				mainTake[10]=(intStat[4]+intStat[7]+intStat[10])==0?"":intStat[4]+intStat[7]+intStat[10]+"";
				mainTake[11]=(intStat[5]+intStat[8]+intStat[11])==0?"":intStat[5]+intStat[8]+intStat[11]+"";			
				mainTake[12]=intStat[3]==0?"":intStat[3]+"";
				mainTake[13]=intStat[4]==0?"":intStat[4]+"";
				mainTake[14]=intStat[5]==0?"":intStat[5]+"";
				mainTake[15]=intStat[6]==0?"":intStat[6]+"";
				mainTake[16]=intStat[7]==0?"":intStat[7]+"";
				mainTake[17]=intStat[8]==0?"":intStat[8]+"";
				mainTake[18]=intStat[9]==0?"":intStat[9]+"";
				mainTake[19]=intStat[10]==0?"":intStat[10]+"";
				mainTake[20]=intStat[11]==0?"":intStat[11]+"";
				if(stockTake.getCatNum()==null || stockTake.getCatNum().intValue()==0)
				{
					mainTake[21]="";
				}
				else
				{
					mainTake[21]=Math.round((stockTake.getCatNum().intValue()-intStat[3]-intStat[6]-intStat[9])*100/stockTake.getCatNum().intValue())+"%";
				}
				if(stockTake.getCatRealNum()==null || stockTake.getCatRealNum().intValue()==0)
				{
					mainTake[22]="";
				}
				else
				{
					mainTake[22]=Math.round((stockTake.getSkuRealNum().intValue()-intStat[4]-intStat[7]-intStat[10])*100/stockTake.getSkuRealNum().intValue())+"%";
				}
				if(stockTake.getSkuNum()==null || stockTake.getSkuNum().intValue()==0)
				{
					mainTake[23]="";			
				}
				else
				{
					if(stockTake.getRealPrice()==null||stockTake.getRealPrice().intValue()==0) mainTake[23]="0%";
					else mainTake[23]=Math.round((stockTake.getRealPrice().intValue()-intStat[5]-intStat[8]-intStat[11])*100/stockTake.getRealPrice().intValue())+"%";			
					
				}
				
			}	
		} catch(Exception e) {
			e.printStackTrace();
		}
		return mainTake;
	}
	/**
	 * 库存盘点报表时EXCEL的生成
	 * @param StockTakeReportForm 查询条件
	 * @param String 用户名
	 * @param String 部门名
	 * @return 返回的列表数据
	 */		
	public String takeReportExcel(StockTakeReportForm stQuery,String userName) throws ReportException {
		String fileName = null;

		HashMap hmpData = new HashMap();
		ArrayList alData = new ArrayList();


		try {

            //统计信息            
			String [] statData=stockReportStat(stQuery);
			StockTakeReportBo strp=StockTakeReportBo.getInstance();
			String[] query=new String[2];
			query[0]=stQuery==null?"":stQuery.getStockTakeId()+"";
			query[1]=userName;
			 //数组合并
			  String[] queryInfo=new String[statData.length+query.length];   
			  System.arraycopy(statData,0,queryInfo,0,statData.length);   
			  System.arraycopy(query,0,queryInfo,statData.length,query.length);
			  
			fileName=strp.createReportFile(queryInfo);

		} catch (ReportException re) {
			throw re;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileName;
	}	
	
	
	
		
	/**
	 * 察看某部门盘点信息
	 * @param Long 部门id
	 * @return 查询结果 
	 */
	public StockTakeForm getUserTakeInfo() {
		StockTakeForm stf=new StockTakeForm();

		try{
			String strHql="from StockTakeForm as stf where stf.takeStatus in ('A','B') " +
				" order by stf.createDate desc";
			ArrayList tempList=(ArrayList)this.getDao().list(strHql);
			if(tempList!=null&&tempList.size()>0) stf=(StockTakeForm)tempList.get(0);
				
		}catch(Exception e) {
			e.printStackTrace();
		} 
		return stf;
	}
	

	/**
	 * 根据id查询盘点记录信息
	 * @param Long 盘点记录id
	 * @return 盘点form
	 */
	public StockTakeForm getTakeInfo(Long takeId) {
		StockTakeForm stf=null;

		try{
			stf=(StockTakeForm)this.getDao().findById(StockTakeForm.class,takeId);
		}catch(Exception e) {
			e.printStackTrace();
		} 
		return stf;
	}
	
	/**
	 * 根据盘点id查询库存记录信息
	 * @param Long 盘点记录id
	 * @return 库存form
	 */
	public StockInfoForm getStockInfo(Long stockId) {
		StockInfoForm sif=null;
		try{
			sif=(StockInfoForm)this.getDao().findById(StockInfoForm.class,stockId);
		}catch(Exception e) {
			e.printStackTrace();
		} 
		return sif;
	}

	/**
	 * 根据minStockId查询相关库存id（回溯group by，把当时合并的零件分开）
	 * @param Long 最小库存id
	 * @return ArrayList 库存id和零件数量
	 */
	public ArrayList getStockIdsByMinId(Long minStockId) {
		ArrayList stockIds=new ArrayList();
		try{
			String strHql="select t1.stockId,t1.skuNum from StockInfoForm as t1,StockInfoForm as t2 "+ 
				"where t2.stockId="+minStockId+" and t1.stuffNo=t2.stuffNo order by t1.createDate";
			stockIds=(ArrayList)this.getDao().list(strHql);
			
		}catch(Exception e) {
			e.printStackTrace();
		} 
		return stockIds;
	}


	/**
	 * 根据StockInfoForm的查询条件拼装where语句
	 * @param StockInfoForm 查询条件
	 * @return where语句
	 */
	public static String getStockInfoWhere(StockInfoForm form) {
		String where="";
		try{
			

			if (form.getStuffNo() != null && !form.getStuffNo().equals("")) {
				where = where + " and sif.stuffNo like '"+Operate.fuzzyQuery(form.getStuffNo())+"'";
			}
			if (form.getSkuCode() != null && !form.getSkuCode().equals("")) {
				where = where + " and sif.skuCode like '"+Operate.fuzzyQuery(form.getSkuCode())+"'";
			}
//			if (form.getSkuType() != null && !form.getSkuType().equals("")) {
//				where = where + " and sif.skuType = '"+form.getSkuType()+"'";
//			}
			if (form.getRealCost1() != null && form.getRealCost1().intValue()!=0) {
				where = where + " and sif.perCost >="+form.getRealCost1();
			}
			if (form.getRealCost2() != null && form.getRealCost2().intValue()!=0) {
				where = where + " and sif.perCost <= "+form.getRealCost2();
			}
//			if (form.getBinCode1() != null && !form.getBinCode1().equals("")) {
//				where = where + " and sif.binCode >='"+form.getBinCode1()+"'";
//			}
//			if (form.getBinCode2() != null && !form.getBinCode2().equals("")) {
//				where = where + " and sif.binCode <= '"+form.getBinCode2()+"'";
//			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		} 
		return where;
	}
	

	/**
	 * 复制StockTakeDetailForm
	 * @param StockTakeDetailForm 原数据
	 * @return 复制数据
	 */
	public StockTakeDetailForm getNewDetailForm(StockTakeDetailForm stdf) {
		StockTakeDetailForm stdf2=new StockTakeDetailForm();
		
			stdf2.setStockTakeId(stdf.getStockTakeId());
			stdf2.setStuffNo(stdf.getStuffNo());
			stdf2.setSkuCode(stdf.getSkuCode());
			stdf2.setBinCode(stdf.getBinCode());
			stdf2.setCreateBy(stdf.getCreateBy());
			stdf2.setStockId(new Long(0));
			
		
		return stdf2;
	}
	
	

	/**
	 * 复制StockInfoForm
	 * @param StockInfoForm 原数据
	 * @return 复制数据
	 */
	public StockInfoForm getNewStockForm(StockInfoForm sif) {
		StockInfoForm sif2=new StockInfoForm();
		sif2.setBinCode(sif.getBinCode());
		sif2.setSkuCode(sif.getSkuCode());
		sif2.setSkuType(sif.getSkuType());
		sif2.setPerCost(sif.getPerCost());
		sif2.setStockStatus(sif.getStockStatus());
		sif2.setFlowNo(sif.getFlowNo());
		sif2.setRemark(sif.getRemark());
		sif2.setCreateBy(sif.getCreateBy());
		
		return sif2;
	}

	/**
	 * 根据某个stockId查询相关零件的价格总合（回溯group by，把当时合并的零件分开）
	 * @param Long 最小库存id
	 * @return 零件库存总价
	 */
	public Float getPerCostById(Long minStockId) {
		double realCost=0;
		try{
			//hql不支持乘法
			String strHql="select t1.perCost,t1.skuNum from StockInfoForm as t1,StockInfoForm as t2 "+ 
				" where t2.stockId="+minStockId+" and t1.stuffNo=t2.stuffNo ";
			ArrayList tempList=(ArrayList)this.getDao().list(strHql);
			
			for(int i=0;i<tempList.size();i++){ 
				Object[] obj=(Object[])tempList.get(i);
				float realCost1=obj[0]==null?0:((Float)obj[0]).floatValue(); 
				int skuNum1=obj[1]==null?0:((Integer)obj[1]).intValue();
				realCost+=realCost1*skuNum1;
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return new Float(realCost);
	}
	

	/**
	 * 根据某个stockId查询相关库存信息（回溯group by，把当时合并的零件分开），
	 * 	再根据盘点数量修改、删除库存信息<br>
	 *  原则上移库只修改仓位和拆分，不做合并，暂时先做到这一步。
	 * @param Long 最小库存id
	 * @param int 盘点数量
	 * @return ArrayList 库存信息
	 */
	public ArrayList getInitStock(Long minStockId,int takeNum) {
		ArrayList retList=new ArrayList();
		try{
			String strHql="from StockInfoForm as sif where sif.stuffNo " +
				" in (select sif2.stuffNo from StockInfoForm as sif2 " +
				" where sif2.stockId="+minStockId+")";
			ArrayList tempList=(ArrayList)this.getDao().list(strHql);
			for(int i=0;i<tempList.size();i++){
				StockInfoForm sif=(StockInfoForm)tempList.get(i);
				if(i==0&&sif.getSkuNum().intValue()!=takeNum){
					sif.setSkuNum(new Integer(takeNum));
					retList.add(sif);
				}
				if(i>=1){
					sif.setSkuNum(new Integer(0));
					retList.add(sif);
				}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return retList;
	}

	/**
	 * 将零件申请相关数据查出生成excle
	 * @param StockInfoForm 查询条件
	 * @return tag[0] - 是否成功，tag[1] - 导入数量
	 */	
	public String initExcelCreate(StockInfoForm siQuery){
			String strRet=null;
			
			try{
				String strWhere=getStockInfoWhere(siQuery);
				//String strHql="from StockInfoForm as sif where 1=1 "+strWhere+
				//" order by sif.stockCode,sif.binCode,sif.skuCode ";
				String strHql="select sif.stuffNo,sif.skuCode,sif.skuUnit " +
					"from StockInfoForm as sif where sif.skuNum>0 "+strWhere+
					" group by sif.stuffNo "+
					" order by sif.stuffNo";
				ArrayList alData=(ArrayList)this.getDao().list(strHql);
				
				if(alData!=null&&alData.size()>0){

					String[] colName={"序列","零件料号","零件名称","单位","盘点数量"};
					alData.add(0, colName);

					StringBuffer strSource=new StringBuffer();
				
					for(int i=0;i<alData.size();i++){
						Object[] temp=(Object[])alData.get(i);
						for(int j=0;j<temp.length;j++){
							if(i==0&&j==0) strSource.append(temp[j]);
							else if(i!=0&&j==0) strSource.append(i).append("\t").append(temp[j]);
							else{
								String value=temp[j]==null?"":temp[j].toString();
								value=value.replaceAll("\r"," ");
								value=value.replaceAll("\n"," ");
								strSource.append("\t").append(value);
							}
						}
						strSource.append("\r\n");
					}
					strRet=strSource.toString();
				}

			}catch (Exception e) {
				e.printStackTrace();
			}
			return strRet;
	}
	

	/**
	 * 差异数据打印
	 * @param ArrayList 差异数据
	 * @return tag[0] - 是否成功，tag[1] - 导入数量
	 */	
	public String diffExcelCreate(ArrayList diffList){
		String strRet=null;
		ArrayList alData = new ArrayList();
		
		int count=0;
		try{
			for(int i=0;i<diffList.size();i++){
				String[] temp=(String[])diffList.get(i);
				temp[0]=(i+1)+"";
				alData.add(temp);
			}
			
			count=alData.size();
			//System.out.println("--count="+count);
			
			if(count>0){
			
				String[] colName={"序列","仓位","零件料号","零件编号","库存数量","盘点数量","单位","ID"};
								  
				alData.add(0, colName);

				StringBuffer strSource=new StringBuffer();
			
				for(int i=0;i<alData.size();i++){
					String[] temp=(String[])alData.get(i);
					for(int j=0;j<temp.length;j++){
						
						if(j==0) strSource.append(temp[j]);
						//跳过binCode，id
						else if(j==1||j==temp.length-1) continue;
						else strSource.append("\t").append(temp[j]);
					}
					strSource.append("\r\n");
				}
				strRet=strSource.toString();
				
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return strRet;
	}
	
	
	
	
}
