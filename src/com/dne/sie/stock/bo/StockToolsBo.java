package com.dne.sie.stock.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import com.dne.sie.common.exception.IllegalPoException;
import com.dne.sie.common.tools.DicInit;
import com.dne.sie.common.tools.FormNumberBuilder;
import com.dne.sie.common.tools.Operate;
import com.dne.sie.maintenance.bo.PartInfoBo;
import com.dne.sie.maintenance.form.PartInfoForm;
import com.dne.sie.reception.bo.PartPoBo;
import com.dne.sie.reception.form.PoForm;
import com.dne.sie.stock.form.StockFlowForm;
import com.dne.sie.stock.form.StockInfoForm;
import com.dne.sie.stock.form.StockToolsInfoForm;
import com.dne.sie.stock.queryBean.StockToolsListQuery;
import com.dne.sie.util.bo.CommBo;

public class StockToolsBo extends CommBo {
	
	private static Logger logger = Logger.getLogger(StockToolsBo.class);

	private static final StockToolsBo INSTANCE = new StockToolsBo();
		
	private StockToolsBo(){
	}
	
	public static final StockToolsBo getInstance() {
	   return INSTANCE;
	}
	
	/**
	 * 查询按照skuCode分类的记录
	 * @param StockToolsInfoForm 查询条件
	 * @return ArrayList 查询结果
	 */
	public ArrayList stockToolsList(StockToolsInfoForm sifQuery) throws Exception {
		
		ArrayList alData = new ArrayList();
		StockToolsListQuery uq = new StockToolsListQuery(sifQuery);
		
		List dataList = uq.doListQuery();
		//count = uq.doCountQuery();
		int count = 0;
		if(dataList!=null){
			count = dataList.size();
			for (int i = 0; i < dataList.size(); i++) {
				StockToolsInfoForm sti = (StockToolsInfoForm)dataList.get(i);
				String[] data = new String[9];
				data[0] = sti.getStockId().toString();
				data[1] = sti.getStuffNo();
				data[2] = sti.getSkuCode();
				data[3] = sti.getStandard()==null?"":sti.getStandard();
				data[4] = sti.getSkuNum().toString();
				data[5] = DicInit.getSystemName("STOCK_STATUS",sti.getStockStatus());
				data[6] = sti.getRemark();
				data[7] = sti.getOwner();
				data[8] = sti.getSkuUnit();
				
				alData.add(data);
			}
		}
		alData.add(0, count + "");

		return alData;
	}
	
	
	/**
	 * 工具调入
	 * @param siform
	 * @return
	 * @throws Exception
	 */
	public int toolsInOperate(StockToolsInfoForm siform) throws Exception {
		   int tag = -1;
		   ArrayList al = new ArrayList();
		   
		   Long flowNo = FormNumberBuilder.getStockFlowId();
		   siform.setFlowNo(flowNo);
		   al.add(siform);
		   		
		   //插入出入库流水表
			StockFlowForm sff = this.infoToFlow(siform);
			
			sff.setFlowType("I");
			sff.setFlowItem("B");	//工具调整入库
			
			al.add(sff);
			
			boolean boo = this.getBatchDao().insertBatch(al);
			if(boo){
				tag = 1;
			}
		    
		    return tag;
	   }
	
	
	public StockFlowForm infoToFlow(StockToolsInfoForm sif)  throws Exception{
		PartInfoBo pib=PartInfoBo.getInstance();
		PartInfoForm pi = pib.find(sif.getStuffNo());
		String remark = sif.getRemark()==null?"":sif.getRemark()+" ";
		
		StockFlowForm sff=new StockFlowForm();
		sff.setFlowId(sif.getFlowNo());
		sff.setStuffNo(sif.getStuffNo());
		sff.setSkuCode(pi.getSkuCode());
		sff.setSkuUnit(pi.getSkuUnit());
		sff.setStandard(pi.getStandard());
		sff.setShortCode(pi.getShortCode());
		sff.setSkuNum(sif.getSkuNum());
		sff.setRemark(remark + "Owner:"+sif.getOwner());
		sff.setCreateBy(sif.getCreateBy());
		sff.setCreateDate(new Date());
		
		return sff;	   	     	
	}
	
}
