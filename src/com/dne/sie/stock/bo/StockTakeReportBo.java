package com.dne.sie.stock.bo;

import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.dne.sie.common.dbo.DBOperation;
import com.dne.sie.common.tools.Operate;
import com.dne.sie.util.report.*;
import com.dne.sie.common.exception.ReportException;
import org.apache.struts.action.ActionForm;


/**
 * ���������������ࡣ
 * @author xt
 * @version Version 1.1.5.6
 */
public class StockTakeReportBo extends BaseReportQuery{

	private static final StockTakeReportBo INSTANCE = new StockTakeReportBo();
		
	private StockTakeReportBo(){
	}
	
	public static final StockTakeReportBo getInstance() {
	   return INSTANCE;
	}
	
	protected ReportForm getReportSql(ActionForm form) throws ReportException{return null;}
	
	/**
	 * ����������sql
	 * @param query ��ѯ���� String[]
	 * @return 	sql
	 */
	protected ReportForm getReportSql(String[] query) throws ReportException{
		ReportForm rf=new ReportForm();
		String strSql[]=new String[1];


		try {
			String takeId=query[query.length-2];
			String strSqlCount="select count(*) from TD_STOCK_TAKE_RPT rpt";
			//���ݲ�ѯ��������������sql
			if(!takeId.equals("")){
				strSqlCount +=	" where  rpt.STOCK_TAKE_ID="+takeId;
			}
//			System.out.println("---------strSqlCount="+strSqlCount);
			int count=Integer.parseInt(DBOperation.selectOne(strSqlCount));
			
			if(count>20000){
				throw new ReportException("��ѯ������ݶ���20000��,���޸Ĳ�ѯ����,���²�ѯ");
			}else{
				//���ݲ�ѯ��������������sql
				/*
				 select sc.system_name, case sc.system_code when 'A' then 'һ���̵�' when 'B' then '�����̵�' else sc.system_code END  AS aa
					from ts_system_code sc where sc.system_type='TAKE_STATUS'
				 */
				if(!takeId.equals("")){
					DBOperation.execute("set @takeCnt = 0");
					strSql[0] =	"select (@takeCnt := @takeCnt  + 1) as ROWNUM, rpt.BIN_CODE,rpt.STUFF_NO,pi.SKU_CODE," +
						"(select sc.system_name from ts_system_code sc where sc.system_type='DIFF_TYPE' and sc.system_code=rpt.DIFF_typ) DIFF_typ," +
						"rpt.DIFF_NUM,if(rpt.DIFF_NUM = 0 ,0,round(rpt.diff_Price/rpt.DIFF_NUM)) diff_Price,rpt.diff_Price from TS_PART_INFO pi," +
						"TD_STOCK_TAKE_RPT rpt where pi.STUFF_NO=rpt.STUFF_NO and rpt.STOCK_TAKE_ID="+ takeId;
				}else{
					strSql[0] =	"select (@takeCnt := @takeCnt  + 1) as ROWNUM, rpt.BIN_CODE,rpt.STUFF_NO,pi.SKU_CODE," +
						"(select sc.system_name from ts_system_code sc where sc.system_type='DIFF_TYPE' and sc.system_code=rpt.DIFF_typ) DIFF_typ," +
						"rpt.DIFF_NUM,if(rpt.DIFF_NUM = 0 ,0,round(rpt.diff_Price/rpt.DIFF_NUM)) diff_Price,rpt.diff_Price from TS_PART_INFO pi," +
						"TD_STOCK_TAKE_RPT rpt where pi.STUFF_NO=rpt.STUFF_NO " ;
				}
//				System.out.println("---------strSql[0]="+strSql[0]);
				rf.setSql(strSql);
				rf.setSqlCount(count);
				rf.setQuery(query);
			}
			
		} catch (ReportException re) {
			throw re;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rf;
	}

	/**
	 * ƴװ����Title
	 * @param rf	ReportForm 
	 * @return ReportForm
	 */
	protected ReportForm getTitles(ReportForm rf){
		try{
			TreeMap hmpData=new TreeMap();
			String[][] titles=new String[15][];
			
			String[] statData=rf.getQuery();
			String [] row1={"","","","","","","","",""};			
			String [] row2={"  �̵㱨��@1@0@1@8"};
			String [] row21={"������:",statData[statData.length-1],"","","","","","",""};
			String [] row22={"��������:",Operate.getDate("yyyy-MM-dd"),"","","","","","",""};
			String [] row23={"","","","","","","","",""};
			String [] row3={"","","","","","","","",""};
			String [] row4={"","","","","","","","",""};
			String [] row5={"","","�̵�����@7@2@7@3","@7@5@7@8"};
			String [] row6={"","�̵���","ʵ���ִ�","ά��Ա��ͷ�������","�ܲ���","��ȱ","��ʣ","��λ����","׼ȷ��"};		
			String [] row7={"����",statData[0],statData[3],statData[6],statData[9],statData[12],statData[15],statData[18],statData[21]};				
			String [] row8={"����",statData[1],statData[4],statData[7],statData[10],statData[13],statData[16],statData[19],statData[22]};				
			String [] row9={"���",statData[2],statData[5],statData[8],statData[11],statData[14],statData[17],statData[20],statData[23]};				
			String [] row10={"","","","","","","","",""};
			String [] row11={"   �̵������ϸ@13@0@13@8"};
			String [] row12={"NO","bin_cd","item_cd","item_desc","��������","Qty Discrepancy","unit price","Total"};
			
			
			titles[0]=row1;
			titles[1]=row2;
			titles[2]=row21;
			titles[3]=row22;
			titles[4]=row23;
			titles[5]=row3;
			titles[6]=row4;
			titles[7]=row5;
			titles[8]=row6;
			titles[9]=row7;
			titles[10]=row8;
			titles[11]=row9;
			titles[12]=row10;
			titles[13]=row11;
			titles[14]=row12;
			
			
			//key-sheet��,value-������
			hmpData.put("����̵㵼������",titles);

			rf.setHsTitles(hmpData);
			//�ļ���
			rf.setFileName("stockTakeReport");
			rf.setColumnCount(row12.length);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rf;
	}



}
