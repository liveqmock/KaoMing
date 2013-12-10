package com.dne.sie.stock.bo;

//Java 基础类
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import com.dne.sie.common.exception.VersionException;
import com.dne.sie.common.tools.CommonSearch;
import com.dne.sie.common.tools.DicInit;
import com.dne.sie.common.tools.FormNumberBuilder;
import com.dne.sie.common.tools.Operate;
import com.dne.sie.reception.bo.SaleInfoBo;
import com.dne.sie.reception.form.SaleDetailForm;
import com.dne.sie.reception.queryBean.ManualAllocateQuery;
import com.dne.sie.stock.form.StockFlowForm;
import com.dne.sie.stock.form.StockInfoForm;
import com.dne.sie.stock.queryBean.AdjustOutQuery;
import com.dne.sie.stock.queryBean.RequestOperateQuery;
import com.dne.sie.util.bo.CommBo;


/**
 * 出库操作BO处理类
 * @author xt
 * @version 1.1.5.6
 * @see StockOutBo.java <br>
 */
public class StockOutBo extends CommBo{
	//private static Logger logger = Logger.getLogger(StockOutBo.class);

	private static final StockOutBo INSTANCE = new StockOutBo();
		
	private StockOutBo(){
	}
	
	public static final StockOutBo getInstance() {
	   return INSTANCE;
	}
	

	

	/**
	* 查询可库调出库的数据（合并可用库存显示）
	* @param String 查询条件
	* @return 该申请记录Form
	*/
	public ArrayList adjustOutList(StockInfoForm sifQuery) throws Exception{

		ArrayList alData = new ArrayList();
		AdjustOutQuery uq = new AdjustOutQuery(sifQuery);
		int count = 0;
		
		List dataList = uq.doListQuery();
		//count = uq.doCountQuery();
		count=dataList.size();

		for (int i = 0; i < dataList.size(); i++) {
			StockInfoForm sif = (StockInfoForm)dataList.get(i);
			String[] data = new String[7];
			data[0] = sif.getStockId().toString();
			data[1] = sif.getStuffNo()==null?"":sif.getStuffNo();
			data[2] = sif.getSkuCode()==null?"":sif.getSkuCode();
			data[3] = sif.getSkuNum()==null?"":sif.getSkuNum()+"";
			data[4] = sif.getPerCost()==null?"":Operate.roundD(sif.getPerCost(),2)+"";
			data[5] = sif.getSkuType()==null?"":sif.getSkuType();
			data[6] = sif.getOrderDollar()==null?"":Operate.roundD(sif.getOrderDollar(),2)+"";
			alData.add(data);
		}
		alData.add(0, count + "");
	
		return alData;	  	
	} 
			

	/**
	* 库调出库
	* @param String 库存记录id
	* @param String[] 仓位号
	* @param Long 用户id
	* @return 是否成功
	*/
	public int ajustOut(String strIds,String[] outNums,String[] outRemarks,String customerName) throws Exception{
		int tag=-1;
		ArrayList dataList = new ArrayList();
	
		//注意此处order by要和AdjustOutQuery一致
		String strHql="from StockInfoForm as sif where  sif.stockId in ("+strIds+")" +
			" order by sif.stuffNo,sif.stockId ";
		//String strStuffNo="";
		ArrayList tempList=(ArrayList)this.getDao().list(strHql);
		for(int i=0;i<tempList.size();i++){
			StockInfoForm sif = (StockInfoForm)tempList.get(i);
			int outNum=Integer.parseInt(outNums[i]);
			int restNum=sif.getSkuNum().intValue()-outNum;
			sif.setSkuNum(new Integer(restNum));
			sif.setUpdateDate(new Date());
			sif.setFlowNo(FormNumberBuilder.getStockFlowId());
			
			if(restNum==0){
				//strStuffNo+="','"+sif.getStuffNo();
				Object[] obj1={sif,"d"};
				dataList.add(obj1);
			}else{
				Object[] obj1={sif,"u"};
				dataList.add(obj1);
			}
			
			StockFlowForm sff=StockInBo.getInstance().infoToFlow(sif);
			sff.setSkuNum(new Integer(outNum));
			sff.setRemark(outRemarks[i]);
			sff.setFlowItem("Z");
			sff.setFlowType("O");
			sff.setRestNum(this.getRestStock(sif.getStuffNo(), outNum, "O"));
			sff.setCustomerName(customerName);
			
			Object[] obj2={sff,"i"};
			dataList.add(obj2);
		}
		if(this.getBatchDao().allDMLBatch(dataList)) tag=1;
		//将重复的同一料号的0数量零件合并
//		if(!"".equals(strStuffNo)){
//			this.outMerge(strStuffNo);
//		}

		return tag;		    	
	} 
	
	/**
	* 出库时，将重复的同一料号的0数量零件合并
	* 已废弃，现做delete操作
	* @param String 料号
	* @return 
	*/
	public void outMerge(String strStuffNo) throws Exception{
		/*
		 mysql不支持如此嵌套。。。。。。
		String strDel="delete from StockInfoForm as t where t.stockId not in("+
		" select max(t2.stockId) from StockInfoForm as t2 where t2.skuNum=0"+ 
		" and t2.stuffNo in('"+strStuffNo.substring(1)+"') group by t2.stuffNo"+ 
		" ) and  t.skuNum=0 and t.stuffNo in('"+strStuffNo.substring(1)+"')";
		*/
//		String strHql=" select max(t2.stockId) from StockInfoForm as t2 where t2.skuNum=0"+ 
//		" and t2.stuffNo in('"+strStuffNo.substring(3)+"') group by t2.stuffNo";
//		String stockId=(this.getDao().uniqueResult(strHql)).toString();
//		String strDel="delete from StockInfoForm as t where t.stockId not in("+
//			stockId+
//		" ) and  t.skuNum=0 and t.stuffNo in('"+strStuffNo.substring(3)+"')";
//		this.getDao().execute(strDel);
	} 
	

	/**
	* 入库时，把原先为零的零件删除
	* 已废弃
	* @param String 料号
	* @return 
	*/
	public void inMerge(String strStuffNo) throws Exception{
//		String strDel="delete from StockInfoForm as t where t.skuNum=0 " +
//				" and t.stuffNo in('"+strStuffNo+"')";
//		this.getDao().execute(strDel);
	} 
	

	/**
	* 统计剩余库存
	* @param String 料号
	* @return 
	*/
	public Integer getRestStock(String stuffNo,Integer flowNum,String type) throws Exception{
		Integer restNum=null;
		String queryString = "select sum(si.skuNum) from StockInfoForm as si  " +
				"where si.stuffNo='"+stuffNo+"' " ;
		Long skuNum=(Long)this.getDao().uniqueResult(queryString);
		if(skuNum==null) skuNum=new Long(0);
		if(type.equals("O")){
			restNum=skuNum.intValue()-flowNum;
		}else if(type.equals("I")){
			restNum=skuNum.intValue()+flowNum;
		}
		
		return restNum;
	}
	
	
	/**
	* 根据id查询该流水记录信息
	* @param String 申请记录pk
	* @return 该申请记录Form
	*/
	public StockFlowForm findById(String id) throws Exception{
		StockFlowForm sff = null;
	
		sff = (StockFlowForm)this.getDao().findById(StockFlowForm.class,new Long(id));
		
		return sff;		    	
	} 

	/**
	 * 出库操作列表查询拼装
	 * @param PartsRequestForm 查询条件
	 * @return 查询结果
	 */
	public ArrayList outOperateList(SaleDetailForm prQuery) {
		ArrayList dataList = null;
		ArrayList alData = new ArrayList();
		RequestOperateQuery sooq = new RequestOperateQuery(prQuery);

		int count = 0;
		try {
			dataList = (ArrayList) sooq.doListQuery(prQuery.getFromPage(),prQuery.getToPage());

			count = sooq.doCountQuery();
			CommonSearch cs = CommonSearch.getInstance();
			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = (Object[]) dataList.get(i);
				String[] data = new String[10];
				data[0] = obj[0].toString() + DicInit.SPLIT1 + obj[9].toString();
				data[1] = obj[1] == null ? "" : obj[1].toString();
				data[2] = obj[2] == null ? "" : obj[2].toString();
				data[3] = obj[3] == null ? "" : obj[3].toString();
				data[4] = obj[4] == null ? "" : obj[4].toString();
				data[5] = obj[5] == null ? "" : obj[5].toString();
				data[6] = obj[6] == null ? "" : obj[6].toString();
				data[7] = cs.findUserNameByUserId((Long) obj[7]);
				data[8] = obj[8] == null ? "" : obj[8].toString();
				data[9] = obj[9] == null ? "" : obj[9].toString();
				
				alData.add(data);
			}
			alData.add(0, count + "");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return alData;
	}

	/**
	  * 出库确认，修改零件申请表(td_sales_detail)信息，
	  * 	修改库存信息表(TD_STOCK_INFO)数量为0，
	  * 	插入出入库流水表(td_stock_flow)一条出库记录
	  * @param String 待出库记录id；
	  * @param Long 出库人
	  * @return 是否成功标志
	  */
	public int stockOut(String ids, Long userId) throws VersionException {
		int tag = -1;

		ArrayList opList = new ArrayList();
		
		try {
//			String strStuffNo="";
			ArrayList<String> saleNoList=new ArrayList<String>();
			StockInfoListBo sil=StockInfoListBo.getInstance();
			StockInBo sib=StockInBo.getInstance();
			SaleInfoBo sBo=SaleInfoBo.getInstance();
			String[] temp = ids.split(",");
			HashSet hs = new HashSet();
			for (int i = 0; i < temp.length; i++) {
				hs.add(temp[i]);
			}
			ids = Operate.arrayListToString(new ArrayList(hs));
			String versionId = Operate.toVersionData(ids);
			String strHql = "from SaleDetailForm as prf where CONCAT(prf.id,',',prf.version) in (" + versionId + ")";
			ArrayList dateList = (ArrayList) this.listVersion(strHql, ids.split(",").length);
			for (int i = 0; i < dateList.size(); i++) { 
				SaleDetailForm prf = (SaleDetailForm) dateList.get(i);
				prf.setPartStatus("M");		//已出库未发货
				prf.setUpdateBy(userId);
				
				Object prfO = new Object[]{prf,"u"};
				opList.add(prfO);
				
				if(!saleNoList.contains(prf.getSaleNo())){
					saleNoList.add(prf.getSaleNo());
				}

				ArrayList stockInfoList =  (ArrayList) this.getDao().list( "from StockInfoForm as sif " +
						"where sif.requestId="+ prf.getSaleDetailId());
				
				for (int j = 0; j < stockInfoList.size(); j++) {
					StockInfoForm sif = (StockInfoForm) stockInfoList.get(j);
			
					StockFlowForm sff = sib.infoToFlow(sif);
					sff.setFlowId(FormNumberBuilder.getStockFlowId());
					sff.setSkuNum(sif.getSkuNum());	//出库数量
					sff.setRestNum(this.getRestStock(sif.getStuffNo(), sif.getSkuNum(), "O"));	//结存数量
//					if(sff.getRestNum()==0){
//						strStuffNo+="','"+sif.getStuffNo();
//					}
					
					sff.setInFlowNo(sif.getFlowNo());
					sff.setFlowType("O");		//出库
					sff.setFlowItem("S"); 		//销售出库
					sff.setFeeType(prf.getOrderType());
					sff.setFormNo(prf.getSaleNo());
					sff.setCreateBy(userId);
					sff.setRequestId(prf.getSaleDetailId());
					
					Object[] temps = sil.getFormNo(prf.getSaleDetailId());
					sff.setCustomerName((String)temps[1]);
					
					Object sffO = new Object[]{sff,"i"};
					opList.add(sffO);
					Object sifO = new Object[]{sif,"d"};
					opList.add(sifO);


				} //end for

			} //end for
			if (this.getBatchDao().allDMLBatch(opList)) {
				tag = 1;
//				if(!"".equals(strStuffNo)) this.outMerge(strStuffNo);
				if(saleNoList.size()>0) sBo.renewSaleStatus(saleNoList);
			}
		

		} catch (VersionException ve) {
			throw ve;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return tag;
	}
	

	/**
	* 手工分配信息查询
	* @param String 查询条件
	* @return 该申请记录Form
	*/
	public ArrayList requestList(SaleDetailForm sdfQuery) throws Exception{

		ArrayList alData = new ArrayList();
		ManualAllocateQuery uq = new ManualAllocateQuery(sdfQuery);
		int count = 0;
		
		List dataList = uq.doListQuery(sdfQuery.getFromPage(),sdfQuery.getToPage());
		count = uq.doCountQuery();
		
		for (int i = 0; i < dataList.size(); i++) {
			Object[] obj = (Object[])dataList.get(i);
			String[] data = new String[12];
			data[0] = obj[0].toString()+DicInit.SPLIT1+obj[12].toString();
			data[1] = obj[1].toString();
			data[2] = obj[2].toString();
			data[3] = obj[3]==null?"":obj[3].toString();
			data[4] = obj[4]==null?"":obj[4].toString();
			data[5] = obj[5]==null?"":obj[5].toString();
			data[6] = obj[6]==null?"":obj[6].toString();
			data[7] = obj[7]==null?"":obj[7].toString();
			data[8] = obj[8]==null?"":obj[8].toString();
			data[9] = obj[9]==null?"0":obj[9].toString();
			data[10] = obj[10].toString();
			data[11] = obj[11].toString();
			alData.add(data);
		}
		alData.add(0, count + "");
	
		return alData;	  	
	} 
	
	/**
	 * 手工分配。
	 * @param requestID String[]
	 * @return int   分配结果
	 */	
	public int manualAllcate(String[] requestID) throws VersionException,Exception{
		
		ReqAllocateBo rao =  new ReqAllocateBo();
		rao.setReqFlag(1);
		return rao.allocate(requestID);
		
	}	
	

	
	/**
	* 查询可库调出库的数据（合并可用库存显示）
	* @param String 查询条件
	* @return 该申请记录Form
	*/
	public ArrayList adjustOutTaxList(StockInfoForm sifQuery) throws Exception{

		ArrayList alData = new ArrayList();
		AdjustOutQuery uq = new AdjustOutQuery(sifQuery);
		int count = 0;
		
		List dataList = uq.doListQuery();
		//count = uq.doCountQuery();
		count=dataList.size();

		for (int i = 0; i < dataList.size(); i++) {
			StockInfoForm sif = (StockInfoForm)dataList.get(i);
			String[] data = new String[7];
			data[0] = sif.getStockId().toString();
			data[1] = sif.getStuffNo()==null?"":sif.getStuffNo();
			data[2] = sif.getSkuCode()==null?"":sif.getSkuCode();
			data[3] = sif.getSkuNum()==null?"":sif.getSkuNum()+"";
			data[4] = sif.getPerCost()==null?"":Operate.roundD(sif.getPerCost(),2)+"";
			data[5] = sif.getSkuNumTax()==null?"":sif.getSkuNumTax()+"";
			data[6] = sif.getPerCostTax()==null?"":Operate.roundD(sif.getPerCostTax(),2)+"";
			alData.add(data);
		}
		alData.add(0, count + "");
	
		return alData;	  	
	} 
			
	public int ajustTaxOut(String strIds,String[] outNums,String[] outCosts) throws Exception{
		int tag=-1;
		ArrayList dataList = new ArrayList();
	
		//注意此处order by要和AdjustOutQuery一致
		String strHql="from StockInfoForm as sif where  sif.stockId in ("+strIds+")" +
			" order by sif.stuffNo,sif.stockId ";
		
		ArrayList tempList=(ArrayList)this.getDao().list(strHql);
		for(int i=0;i<tempList.size();i++){
			StockInfoForm sif = (StockInfoForm)tempList.get(i);
					
			sif.setSkuNumTax(Integer.parseInt(outNums[i]));
			sif.setPerCostTax(Float.parseFloat(outCosts[i]));
			
			dataList.add(sif);
			
		}
		if(this.getBatchDao().updateBatch(dataList)) tag=1;
		

		return tag;		    	
	}
	
	public Float findStockCost(Long requestId) throws Exception{
		String hql="select sff.perCost from StockFlowForm as sff where sff.requestId = ? and sff.perCost is not null ";
		
		return (Float)this.getDao().uniqueResult(hql,requestId);
	}
	public Float findStockCostTransfer(Long partsId) throws Exception{
		String hql="select sff.perCost from StockFlowForm as sff where sff.requestId = ? and sff.perCost is not null ";
		
		return (Float)this.getDao().uniqueResult(hql,partsId);
	}
	
	
}
