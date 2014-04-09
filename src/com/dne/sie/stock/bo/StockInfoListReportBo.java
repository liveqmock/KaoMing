package com.dne.sie.stock.bo;

import java.util.TreeMap;


import com.dne.sie.util.report.*;
import com.dne.sie.common.exception.ReportException;
import com.dne.sie.common.dbo.DBOperation;
import com.dne.sie.stock.form.StockInfoForm;
import org.apache.struts.action.ActionForm;


/**
 * 库存清单报表生成类。
 * @author xt
 * @version Version 1.1.5.6
 */
public class StockInfoListReportBo extends BaseReportQuery{

	private static final StockInfoListReportBo INSTANCE = new StockInfoListReportBo();
		
	private StockInfoListReportBo(){
	}
	
	public static final StockInfoListReportBo getInstance() {
	   return INSTANCE;
	}
	/**
	 * 库存清单bb
	 * @param ActionForm 查询条件
	 * @return 	ReportForm
	 */
	protected ReportForm getReportSql(ActionForm form) throws ReportException{
		String strSql[]=new String[1];
		ReportForm rf=new ReportForm();

		try {
			StockInfoForm siform=(StockInfoForm)form;
			String strWhere=this.getWhere(siform);
			
			
			String countSql="select count(*) from TD_STOCK_INFO si "+strWhere;
//			System.out.println("----------countSql="+countSql);
			int count=Integer.parseInt(DBOperation.selectOne(countSql));
			if(count==0){
				throw new ReportException("查询结果数据为0,请修改查询条件,重新查询");
			}else{
				DBOperation.execute("set @mycnt = 0");
				strSql[0] =  "select (@mycnt := @mycnt  + 1) as ROWNUM,"+
				 " SHORT_CODE,SKU_CODE,STUFF_NO,STANDARD,sum(per_Cost * sku_Num)/sum(sku_Num),"+
				 //" trim(max(if(sku_source='A',sku_Num,0))) A_num,"+
				 //" trim(max(if(sku_source='B',sku_Num,0))) B_num"+
				 "sku_Num "+
				 " from ("+
				 "  select SHORT_CODE,si.SKU_CODE,si.STUFF_NO,si.STANDARD,"+
				 "  sum(si.sku_Num) sku_Num,si.per_Cost from TD_STOCK_INFO si "+strWhere+
				 "  group by si.SKU_CODE,si.SHORT_CODE,si.STUFF_NO,si.STANDARD"+
				 ")as t1 group by SKU_CODE,SHORT_CODE,STUFF_NO,STANDARD";
//				System.out.println("----------strSql[0]="+strSql[0]);
				rf.setSql(strSql);
				rf.setSqlCount(count);
			}
			
		} catch (ReportException re) {
			throw re;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rf;
	}
	

	/**
	 * 获取查询where语句
	 * @param StockInfoForm 查询条件
	 * @return where语句
	 */
	public String getWhere(StockInfoForm form){
		String whereIn=" where 1=1 ";

		if (form.getSkuCode() != null && !form.getSkuCode().equals("")) {
			whereIn += " and si.SKU_CODE like '%"+form.getSkuCode()+"%'";
		}

		if (form.getShortCode() != null && !form.getShortCode().equals("")) {
			whereIn += " and si.SHORT_CODE like '%"+form.getShortCode()+"%'";
		}

		if (form.getStandard() != null && !form.getStandard().equals("")) {
			whereIn += " and si.STANDARD like '%"+form.getStandard()+"%'";
		}

		if (form.getStuffNo() != null && !form.getStuffNo().equals("")) {
			whereIn += " and si.STUFF_NO like '%"+form.getStuffNo()+"%'";
		}
		
		if (form.getSkuUnit() != null && !form.getSkuUnit().equals("")) {
			whereIn += " and si.SKU_UNIT =  '"+form.getSkuUnit()+"'";
		}

		if (form.getSkuNum() != null && !form.getSkuNum().equals("")) {
			whereIn += " and si.SKU_NUM =  "+form.getSkuNum();
		}

		if (form.getPerCost() != null && !form.getPerCost().equals("")) {
			whereIn += " and si.PER_COST =  "+form.getPerCost();
		}
		
		if (form.getStockStatus() != null && !form.getStockStatus().equals("")) {
			whereIn += " and si.STOCK_STATUS =  '"+form.getStockStatus()+"'";
		}
		
		if (form.getInDate1() != null && !form.getInDate1().equals("")) {
			whereIn += " and si.CREATE_DATE >=  to_date('"+form.getInDate1()+"','yyyy-mm-dd')";
		}
		if (form.getInDate2() != null && !form.getInDate2().equals("")) {
			whereIn += " and si.CREATE_DATE >=  to_date('"+form.getInDate2()+"','yyyy-mm-dd')";	
		}
		if (form.getRemark() != null && !form.getRemark().equals("")) {
			whereIn += " and si.REMARK like '%"+form.getRemark()+"%'";
		}

		return whereIn;
	}
	
	
	protected ReportForm getReportSql(String[] query) throws ReportException{return null;}

	/**
	 * 拼装报表Title
	 * @param rf	ReportForm 
	 * @return ReportForm
	 */
	protected ReportForm getTitles(ReportForm rf){
		
		try{
			TreeMap hmpData=new TreeMap();
			String[][] titles=new String[2][];
			
			String [] row1={"目 录@0@0@0@7"};			
			String [] row2={
					"",
					"简称",
					"零件名称",
					"规格",
					"料号",
					"平均成本",
					"总数量"};
			
			
			titles[0]=row1;
			titles[1]=row2;
			
			//key-sheet名,value-标题行
			hmpData.put("目录",titles);

			rf.setHsTitles(hmpData);
			//文件名
			rf.setFileName("StockReport");
			rf.setColumnCount(row2.length);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rf;
	}



}
