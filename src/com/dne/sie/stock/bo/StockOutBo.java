package com.dne.sie.stock.bo;

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



public class StockOutBo extends CommBo{
	//private static Logger logger = Logger.getLogger(StockOutBo.class);

	private static final StockOutBo INSTANCE = new StockOutBo();
		
	private StockOutBo(){
	}
	
	public static final StockOutBo getInstance() {
	   return INSTANCE;
	}
	

	public ArrayList adjustOutList(StockInfoForm sifQuery) throws Exception{

		ArrayList alData = new ArrayList();
		AdjustOutQuery uq = new AdjustOutQuery(sifQuery);
		int count = 0;
		
		List dataList = uq.doListQuery();
		//count = uq.doCountQuery();
		count=dataList.size();

		for (int i = 0; i < dataList.size(); i++) {
			StockInfoForm sif = (StockInfoForm)dataList.get(i);
			String[] data = new String[8];
			data[0] = sif.getStockId().toString();
			data[1] = sif.getStuffNo()==null?"":sif.getStuffNo();
			data[2] = sif.getSkuCode()==null?"":sif.getSkuCode();
			data[3] = sif.getSkuNum()==null?"":sif.getSkuNum()+"";
			data[4] = sif.getPerCost()==null?"":Operate.roundD(sif.getPerCost(),2)+"";
			data[5] = DicInit.getSystemName("SKU_TYPE",sif.getSkuType());
			data[6] = sif.getOrderDollar()==null?"":Operate.roundD(sif.getOrderDollar(),2)+"";
            data[7] = sif.getBinCode()==null?"":sif.getBinCode();
			alData.add(data);
		}
		alData.add(0, count + "");
	
		return alData;	  	
	} 

	public ArrayList<?> stockBinMoveList(StockInfoForm sifQuery) throws Exception{

		ArrayList alData = new ArrayList();
		AdjustOutQuery uq = new AdjustOutQuery(sifQuery);
		
		List dataList = uq.doListQuery(sifQuery.getFromPage(),sifQuery.getToPage());
		int count = uq.doCountQuery();

		for (int i = 0; i < dataList.size(); i++) {
			StockInfoForm sif = (StockInfoForm)dataList.get(i);
			String[] data = new String[7];
			data[0] = sif.getStockId().toString();
			data[1] = sif.getStuffNo()==null?"":sif.getStuffNo();
			data[2] = sif.getSkuCode()==null?"":sif.getSkuCode();
			data[3] = sif.getSkuNum()==null?"":sif.getSkuNum()+"";
			data[4] = DicInit.getSystemName("SKU_TYPE",sif.getSkuType());
			data[5] = sif.getCreateDate()==null?"":Operate.trimDate(sif.getCreateDate()).toString();
			data[6] = sif.getBinCode()==null?"":sif.getBinCode();
			alData.add(data);
		}
		alData.add(0, count + "");
	
		return alData;	  	
	} 	

	public int ajustOut(String strIds,String[] outNums,String[] outRemarks,String customerName) throws Exception{
		int tag=-1;
		ArrayList dataList = new ArrayList();
	
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
            sff.setBinCode(sif.getBinCode());
			
			Object[] obj2={sff,"i"};
			dataList.add(obj2);
		}
		if(this.getBatchDao().allDMLBatch(dataList)) tag=1;

//		if(!"".equals(strStuffNo)){
//			this.outMerge(strStuffNo);
//		}

		return tag;		    	
	} 

	public void outMerge(String strStuffNo) throws Exception{
		/*
		 mysql��֧�����Ƕ�ס�����������
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
	

	public void inMerge(String strStuffNo) throws Exception{
//		String strDel="delete from StockInfoForm as t where t.skuNum=0 " +
//				" and t.stuffNo in('"+strStuffNo+"')";
//		this.getDao().execute(strDel);
	} 
	

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
	

	public StockFlowForm findById(String id) throws Exception{
		StockFlowForm sff = null;
	
		sff = (StockFlowForm)this.getDao().findById(StockFlowForm.class,new Long(id));
		
		return sff;		    	
	} 

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
				String[] data = new String[11];
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
                data[10] = obj[10] == null ? "" : obj[10].toString();
				
				alData.add(data);
			}
			alData.add(0, count + "");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return alData;
	}

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
				prf.setPartStatus("M");
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
					sff.setSkuNum(sif.getSkuNum());
					sff.setRestNum(this.getRestStock(sif.getStuffNo(), sif.getSkuNum(), "O"));	//�������
//					if(sff.getRestNum()==0){
//						strStuffNo+="','"+sif.getStuffNo();
//					}
					
					sff.setInFlowNo(sif.getFlowNo());
					sff.setFlowType("O");
					sff.setFlowItem("S");
					sff.setFeeType(prf.getOrderType());
					sff.setFormNo(prf.getSaleNo());
					sff.setCreateBy(userId);
					sff.setRequestId(prf.getSaleDetailId());
                    sff.setBinCode(sif.getBinCode());
					
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

	public int manualAllcate(String[] requestID) throws VersionException,Exception{
		
		ReqAllocateBo rao =  new ReqAllocateBo();
		rao.setReqFlag(1);
		return rao.allocate(requestID);
		
	}	
	

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
	
	
	public boolean moveBin(Long stockId,String binCode) throws Exception{
		this.getDao().execute("update StockInfoForm si set si.binCode=?,updateDate=now() where si.id=? ", binCode,stockId);
		return true;
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
