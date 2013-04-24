package com.dne.sie.support.bo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.write.Label;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;

import com.dne.sie.common.dbo.DBOperation;
import com.dne.sie.common.exception.ReportException;
import com.dne.sie.common.tools.Operate;
import com.dne.sie.support.form.AccountStatisticsForm;
import com.dne.sie.support.queryBean.AccountStatisticsQuery;
import com.dne.sie.util.hibernate.HbConn;
import com.dne.sie.util.report.ReportForm;

/**
 * 费用统计报表
 * @author xt
 * @version Version 1.1.5.6
 */
public class AccountStatReport {
	private static Logger logger = Logger.getLogger(AccountStatReport.class);

	private static final AccountStatReport INSTANCE = new AccountStatReport();
		
	private AccountStatReport(){
	}
	
	public static final AccountStatReport getInstance() {
	   return INSTANCE;
	}
	
	private static final String[] reportsPath=Operate.getReportPath();
	
	private static final String[] SheetNames={"现金日记账","支出明细账","收支明细账","银行存款收支表","银行日记账"};
	
	
	public String createReportFile(String[] query) throws Exception{
//		System.out.println("---month="+query[0]);
		return reportQuery(query);
	}
	



	/**
	 * 根据sql查询出的数据,通过jxl包写成xls文件
	 * @param query - where条件
	 * @return fileName - 生成文件名
	 */
	private String reportQuery(String[] query) throws ReportException,Exception{
	
		WritableWorkbook workbook=null;
		OutputStream os=null;
		String writePath=null;
		String downLoadPath=null;
		WorkbookSettings ws=null;

		String fileName="account"+query[0];
		
		try{
			AccountStatisticsForm asf = new AccountStatisticsForm();
			asf.setAccountMonth(new Integer(query[0]));
			
			AccountStatisticsQuery uq = new AccountStatisticsQuery(asf);
			List asfList=uq.doListQuery();
			asf = (AccountStatisticsForm)asfList.get(0);
			
			String bgDay=query[0].substring(0,4)+"-"+query[0].substring(4,6)+"-01";
			String endDay=Operate.trimDate(Operate.getLastDayOfMonth(bgDay));
			
			writePath=reportsPath[0]+"accountStat/";
			downLoadPath=reportsPath[1]+"accountStat/";
			File f=new File(writePath);
			if(!f.exists()){
				f.mkdirs();
			}
			fileName=fileName+Operate.getSectTime()+".xls";

			ws=new WorkbookSettings();
			
			os=new FileOutputStream(writePath+fileName);
			workbook=Workbook.createWorkbook(os,ws); 
			
			//sheet对象数组，长度为页的个数
			WritableSheet[] sheets = new WritableSheet[SheetNames.length];
			
			//设置粗体样式,标题加粗
			WritableFont wfT1 = new WritableFont(WritableFont.createFont("楷体_GB2312"),14,WritableFont.BOLD);
			WritableCellFormat wcfT1 = new WritableCellFormat(wfT1);
			wcfT1.setAlignment(jxl.format.Alignment.CENTRE); 		//居中对齐
			wcfT1.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);

			WritableFont wfT2 = new WritableFont(WritableFont.createFont("楷体_GB2312"),10,WritableFont.BOLD);
			WritableCellFormat wcfT2 = new WritableCellFormat(wfT2);
			wcfT2.setAlignment(jxl.format.Alignment.LEFT); 			//居左对齐
			
			WritableFont wfT3 = new WritableFont(WritableFont.createFont("楷体"),12,WritableFont.BOLD);
			WritableCellFormat wcfT3 = new WritableCellFormat(wfT3);
			wcfT3.setAlignment(jxl.format.Alignment.CENTRE); 		//居中对齐
			wcfT3.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			
			
			NumberFormat nf = new NumberFormat("0.00");

			WritableFont wf1 = new WritableFont(WritableFont.createFont("楷体_GB2312"),10);
			WritableFont wf2 = new WritableFont(WritableFont.createFont("楷体"),10);
			WritableFont wf3 = new WritableFont(WritableFont.createFont("楷体_GB2312"),10,WritableFont.BOLD);

			WritableCellFormat wcfY1 = new WritableCellFormat(wf2);	//居中、细框
			wcfY1.setAlignment(jxl.format.Alignment.CENTRE); 
			wcfY1.setBorder(Border.ALL, BorderLineStyle.THIN);
			wcfY1.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			
			WritableCellFormat wcfY2 = new WritableCellFormat(wf2);	//居左
			wcfY2.setAlignment(jxl.format.Alignment.LEFT); 
			
			WritableCellFormat wcfY3 = new WritableCellFormat(wf2);	//居左、细框
			wcfY3.setBorder(Border.ALL, BorderLineStyle.THIN);
			wcfY3.setAlignment(jxl.format.Alignment.RIGHT); 
			
			WritableCellFormat wcfY4 = new WritableCellFormat(wf2);	//居左、细框
			wcfY4.setBorder(Border.ALL, BorderLineStyle.THIN);
			wcfY4.setAlignment(jxl.format.Alignment.LEFT); 
			
			WritableCellFormat wcf1 = new WritableCellFormat(wf1);	//居中、细框
			wcf1.setAlignment(jxl.format.Alignment.CENTRE); 
			wcf1.setBorder(Border.ALL, BorderLineStyle.THIN);
			
			WritableCellFormat wcf2 = new WritableCellFormat(wf1);	//居左、细框
			wcf2.setAlignment(jxl.format.Alignment.LEFT); 
			wcf2.setBorder(Border.ALL, BorderLineStyle.THIN);
			
			WritableCellFormat wcf3 = new WritableCellFormat(wf1,nf);	//居右、细框
			wcf3.setAlignment(jxl.format.Alignment.RIGHT); 
			wcf3.setBorder(Border.ALL, BorderLineStyle.THIN);
			
			WritableCellFormat wcf4 = new WritableCellFormat(wf1,nf);	//居右、无框
			wcf4.setAlignment(jxl.format.Alignment.RIGHT);

			WritableCellFormat wcf5 = new WritableCellFormat(wf1);	//居左、无框
			wcf5.setAlignment(jxl.format.Alignment.LEFT);
			
			WritableCellFormat wcf6 = new WritableCellFormat(wf1);	//横竖居中、细框
			wcf6.setAlignment(jxl.format.Alignment.CENTRE); 
			wcf6.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			wcf6.setBorder(Border.ALL, BorderLineStyle.THIN);
			
			WritableCellFormat wcf7 = new WritableCellFormat(wf3);	//居中、细框
			wcf7.setAlignment(jxl.format.Alignment.CENTRE); 
			wcf7.setBorder(Border.ALL, BorderLineStyle.THIN);

			
			Label labelTitle = null;
			Label labelData = null;
			jxl.write.Number labelNumber=null;
			
			int sheetCount=0;
			
			/*
			 * sheet1:现金日记账
			 */
			if(true){
				//根据sheet名称，创建不同的sheet对象
				String sheetName=SheetNames[sheetCount];
				sheets[sheetCount]=workbook.createSheet(sheetName, 0); 
				//设置sheet名
				//sheets[sheetCount].setName(me.getKey().toString());
				
				//将第一行的高度设为46
				sheets[sheetCount].setRowView(0,700); 
			
				//设置列宽，取最长数据的长度为列宽
				sheets[sheetCount].setColumnView(0,6);
				sheets[sheetCount].setColumnView(1,6);
				sheets[sheetCount].setColumnView(2,7); 
				sheets[sheetCount].setColumnView(3,14); 
				sheets[sheetCount].setColumnView(4,14); 
				sheets[sheetCount].setColumnView(5,20); 
				sheets[sheetCount].setColumnView(6,14); 
				sheets[sheetCount].setColumnView(7,12);
				sheets[sheetCount].setColumnView(8,12);
				sheets[sheetCount].setColumnView(9,12);

				//设置自动换行 
				//wcf1.setWrap(true);
				

				//指定合并区域,左上角到右下角,mergeCells(int startCol,int startRow,int endCol,int endRow)
				sheets[sheetCount].mergeCells(0,0,9,0);
				//创建单元格对象,Label(列号,行号 ,内容,字体 )
				labelTitle = new Label(0, 0,"上海办现金（"+query[0].substring(4,6)+"月份）",wcfT1);
				sheets[sheetCount].addCell(labelTitle);
				
				sheets[sheetCount].mergeCells(0,1,2,1);
				sheets[sheetCount].addCell(new Label(0, 1,query[0].substring(2,4)+"年",wcf1));
				sheets[sheetCount].addCell(new Label(0, 2,"月份",wcf1));
				sheets[sheetCount].addCell(new Label(1, 2,"日期",wcf1));
				sheets[sheetCount].addCell(new Label(2, 2,"凭证",wcf1));
				
				sheets[sheetCount].mergeCells(3,1,6,2);
				sheets[sheetCount].addCell(new Label(3, 1,"摘要",wcf1));

				sheets[sheetCount].mergeCells(7,1,7,2);
				sheets[sheetCount].addCell(new Label(7, 1,"收入金额",wcf1));
				sheets[sheetCount].mergeCells(8,1,8,2);
				sheets[sheetCount].addCell(new Label(8, 1,"支出金额",wcf1));
				sheets[sheetCount].mergeCells(9,1,9,2);
				sheets[sheetCount].addCell(new Label(9, 1,"结存金额",wcf1));
				
				String strSql="SELECT  month(ai.fee_date) m,dayofmonth(ai.fee_date) d,ai.voucher_no vn,st.subject_name sn,"+
					" (select ec.employee_name from ts_employee_info ec where ec.employee_code=ai.employee_code) emp,"+
					" ai.place pl,ai.summary sm,(case when pay_type='XS' then ai.money else '' end) xs,"+
					" (case when pay_type='XF' then ai.money else '' end) xf"+
					" from td_account_info as ai,td_subject_tree as st"+
					" where ai.subject_id=st.subject_id and ai.pay_type in('XS','XF')"+
					" and ai.fee_date >= '"+bgDay+"' and ai.fee_date<= '"+endDay+"'" +
					" order by ai.fee_date,ai.subject_id";
				
				System.out.println("--tab1--strSql="+strSql);
				ArrayList<String[]> dataList = DBOperation.select(strSql);
				double xs=0d,xf=0d;
				for(int i=0;i<dataList.size();i++) {
					String[] temp=(String[])dataList.get(i);
					for (int j = 0; j < temp.length; j++) {
						String value = temp[j]==null?"":temp[j];
						//wcf2 = new WritableCellFormat(wf2);
						//设置自动换行 
						//wcf2.setWrap(true);
						//labelData = new Label(j,i,strTemp==null?"":strTemp,wcf2);
						
						if((j==7||j==8)&&!value.equals("")){
							labelNumber = new jxl.write.Number(j,i+3,Double.parseDouble(value),wcf3);
							sheets[sheetCount].addCell(labelNumber); 
						}else{
							if(j==0||j==1) labelData = new Label(j,i+3,value,wcf1);
							else labelData = new Label(j,i+3,value,wcf2);
							sheets[sheetCount].addCell(labelData); 
						}
						
						
						
						
						if(j==7&&!value.equals("")){
							xs+=Double.parseDouble(value);
						}else if(j==8&&!value.equals("")){
							xf+=Double.parseDouble(value);
						}
					}
					sheets[sheetCount].addCell(new Label(9,i+3,"",wcf1)); 
					
				}
				
				//总计
				sheets[sheetCount].addCell(new Label(3, dataList.size()+3,"上月结存",wcf5));
				sheets[sheetCount].addCell(new jxl.write.Number(9, dataList.size()+3,asf.getPriorCash(),wcf4));
				
				sheets[sheetCount].addCell(new Label(3, dataList.size()+4,"本月合计",wcf5));
				sheets[sheetCount].addCell(new jxl.write.Number(7, dataList.size()+4,asf.getCashReceipt(),wcf4));
				sheets[sheetCount].addCell(new jxl.write.Number(8, dataList.size()+4,asf.getCashPayment(),wcf4));
				sheets[sheetCount].addCell(new jxl.write.Number(9, dataList.size()+4,asf.getCashReceipt()-asf.getCashPayment(),wcf4));
				
				sheets[sheetCount].addCell(new Label(3, dataList.size()+5,"本年累计",wcf5));
				sheets[sheetCount].addCell(new jxl.write.Number(9, dataList.size()+5,asf.getCurrentCash(),wcf4));
				
			} //end sheet1
			
			

			/*
			 * sheet2:现金支出明细账
			 */
			if(true){
				sheetCount=1;
				//根据sheet名称，创建不同的sheet对象
				String sheetName=SheetNames[sheetCount];
				sheets[sheetCount]=workbook.createSheet(sheetName, sheetCount); 
				
			
				//设置列宽，取最长数据的长度为列宽
				sheets[sheetCount].setColumnView(0,5); 
				sheets[sheetCount].setColumnView(1,5); 
				sheets[sheetCount].setColumnView(2,6); 
				sheets[sheetCount].setColumnView(3,15); 
				sheets[sheetCount].setColumnView(4,15); 
				sheets[sheetCount].setColumnView(5,15); 
				sheets[sheetCount].setColumnView(6,15); 
				sheets[sheetCount].setColumnView(7,15); 

				//Title:现金支出明细
				sheets[sheetCount].mergeCells(0,0,7,0);
				labelTitle = new Label(0, 0,query[0].substring(4,6)+"月份支出明细",wcfT1);
				sheets[sheetCount].addCell(labelTitle);
				
				AccountBo ab = AccountBo.getInstance();
				String statIds=ab.getReportStatIds();
				
				/*
				 *	subject统计
				 *	 快递费							
					1	4	5	主营业务	快递费	张长青	工具					336.00 
					1	18	75	主营业务	快递费	捷特		刀套/夹爪/文件合同	715.00 
					1	21	83	主营业务	快递费	捷特		凸轮/接近开关		1,505.00 
																			2,556.00 
				 */
				String strSqlSub="SELECT  month(ai.fee_date) m,dayofmonth(ai.fee_date) d,ai.voucher_no vn," +
					"(select tr1.subject_name from td_subject_tree tr1 where tr1.subject_id=st.parent_id ) sn0,"+
					"st.subject_name sn,"+
					" (select ec.employee_name from ts_employee_info ec where ec.employee_code=ai.employee_code) emp," +
					" ai.place,ai.money,st.report_flag,st.subject_id,st.layer "+
					" from td_account_info as ai,td_subject_tree as st"+
					" where ai.subject_id=st.subject_id and st.subject_id in("+statIds+") and ai.pay_type ='XF'"+
					" and ai.fee_date >= '"+bgDay+"' and ai.fee_date<= '"+endDay+"'" +
					" order by st.parent_id,ai.subject_id,ai.fee_date";
				
				System.out.println("--tab2--strSqlSub="+strSqlSub);
				ArrayList<String[]> dataList = DBOperation.select(strSqlSub);
				double xs=0d;
				String subName=null;
				Long lastId=null;
				String lastReportFlag=null;
				int tit=1;
				//行
				for(int i=0;i<dataList.size();i++) {
					String[] temp=(String[])dataList.get(i);
					if(subName==null){	//subject begin
						sheets[sheetCount].addCell(new Label(0,i+tit,temp[4],wcf5));
						tit++;
					}else if(!subName.equals(temp[4])){	//subject end
						
						Long a1=ab.findChkFather(new Long(temp[9]),0);
						Long a2=ab.findChkFather(lastId,0);
						
						if("1".equals(temp[8])|| "1".equals(lastReportFlag)|| "1".equals(temp[10])|| (a1!=null&&a2!=null&& a1.intValue()!=a2.intValue())){		
//							System.out.println("====="+a1+"---"+a2);
//							System.out.println(temp[4]+"~~~~~~="+temp[9]+"---"+lastId+"---"+temp[10]);
							
							//统计上一subject_name总费用
							sheets[sheetCount].addCell(new jxl.write.Number(7,i+tit,xs,wcf3));
							xs=0;
							tit++;
							
							//当前subject_name
							if("1".equals(temp[8])){
								sheets[sheetCount].addCell(new Label(0,i+tit,temp[4],wcf5));
							}else{
								sheets[sheetCount].addCell(new Label(0,i+tit,temp[3],wcf5));
							}
							
							tit++;
						}
					}
					//列
					for (int j = 0; j < temp.length-3; j++) {
						String value = temp[j]==null?"":temp[j];
						
						if(j==7&&!value.equals("")){
							labelNumber = new jxl.write.Number(j,i+tit,Double.parseDouble(value),wcf3);
							sheets[sheetCount].addCell(labelNumber); 
						}else{
							labelData = new Label(j,i+tit,value,wcf1);
							sheets[sheetCount].addCell(labelData); 
						}
						
						
						
						if(j==7&&!value.equals("")){
							xs+=Double.parseDouble(value);
						}
					}
					subName=temp[4];
					lastId= new Long(temp[9]);
					lastReportFlag = temp[8];
				}
				tit+=dataList.size();
				sheets[sheetCount].addCell(new jxl.write.Number(7,tit,xs,wcf3));
				System.out.println("----tit="+tit);

				/*
				 * employee统计统计
				 * 刘旭东							
					1	20	80	经营费	差旅费	刘旭东	苏州道森		405.00 
					1	20	81	经营费	市内车资	刘旭东	南翔			410.00 
					1	20	82	经营费	差旅费	刘旭东	昆山梅塞尔	1,343.00 
																	2,158.00 
				 */
				String ten=reportsPath[2].replaceAll(",","','");
				String strSqlEmp="SELECT  month(ai.fee_date) m,dayofmonth(ai.fee_date) d,ai.voucher_no vn," +
					" (select tr1.subject_name from td_subject_tree tr1 where tr1.subject_id=st.parent_id ) parentName,"+
					" st.subject_name sn,"+
					" (select ec.employee_name from ts_employee_info ec where ec.employee_code=ai.employee_code) emp," +
					" ai.place,ai.money "+
					" from td_account_info as ai,td_subject_tree as st"+
					" where ai.subject_id=st.subject_id and ai.pay_type ='XF'" +
					" and st.subject_name in('"+ten+"')"+
					" and ai.fee_date >= '"+bgDay+"' and ai.fee_date<= '"+endDay+"'" +
					" order by ai.employee_code,ai.fee_date";
				
				System.out.println("--tab2--strSqlEmp="+strSqlEmp);
				dataList = DBOperation.select(strSqlEmp);
				xs=0d;
				tit=tit+3;
				String empName=null;
				
				//Title:差旅费
				sheets[sheetCount].mergeCells(0,tit,7,tit);
				labelTitle = new Label(0, tit,"差旅费",wcfT1);
				sheets[sheetCount].addCell(labelTitle);
				tit++;
				
				//行
				for(int i=0;i<dataList.size();i++) {
					String[] temp=(String[])dataList.get(i);
					if(empName==null){	//subject begin
						sheets[sheetCount].addCell(new Label(0,i+tit,temp[5],wcf5));
						tit++;
					}else if(!empName.equals(temp[5])){	//subject end
						sheets[sheetCount].addCell(new jxl.write.Number(7,i+tit,xs,wcf3));
						xs=0;
						tit++;
						
						sheets[sheetCount].addCell(new Label(0,i+tit,temp[5],wcf5));
						tit++;
					}
					//列
					for (int j = 0; j < temp.length; j++) {
						String value = temp[j]==null?"":temp[j];
						
						if(j==7&&!value.equals("")){
							labelNumber = new jxl.write.Number(j,i+tit,Double.parseDouble(value),wcf3);
							sheets[sheetCount].addCell(labelNumber); 
						}else{
							labelData = new Label(j,i+tit,value,wcf1);
							sheets[sheetCount].addCell(labelData); 
						}
						
						
						
						if(j==7&&!value.equals("")){
							xs+=Double.parseDouble(value);
						}
					}
					empName=temp[5];
				}
				tit+=dataList.size();
				sheets[sheetCount].addCell(new jxl.write.Number(7,tit,xs,wcf3));
				System.out.println("----tit="+tit);
				
				//现金支出总计
				String monXF=DBOperation.selectOne("SELECT cash_payment FROM td_account_statistics t where account_month="+query[0]);
				
				//sheets[sheetCount].addCell(new Label(6, tit+2,query[0]+"现金支出",wcf5));
				sheets[sheetCount].addCell(new jxl.write.Number(7, tit+2,Double.parseDouble(monXF),wcf4));
				
			} //end sheet2
			
			
			

			/*
			 * sheet3:收支明细账
			 */
			if(true){
				sheetCount=2;
				//根据sheet名称，创建不同的sheet对象
				String sheetName=SheetNames[sheetCount];
				sheets[sheetCount]=workbook.createSheet(sheetName, sheetCount);

				//设置列宽，取最长数据的长度为列宽
				sheets[sheetCount].setColumnView(0,6); 
				sheets[sheetCount].setColumnView(1,11); 
				sheets[sheetCount].setColumnView(2,14); 
				sheets[sheetCount].setColumnView(3,11); 
				sheets[sheetCount].setColumnView(4,14); 
				sheets[sheetCount].setColumnView(5,10); 
				sheets[sheetCount].setColumnView(6,16); 
				sheets[sheetCount].setColumnView(7,16); 
				sheets[sheetCount].setColumnView(8,11); 
				sheets[sheetCount].setColumnView(9,14); 
				
				
				//Title:一月份收支明细表
				sheets[sheetCount].mergeCells(0,0,9,0);
				labelTitle = new Label(0, 0,query[0]+"收支明细表",wcfT3);
				sheets[sheetCount].addCell(labelTitle);
				
				
				String xfStat="select ss.subject_name,ss.money from td_account_statistics_subject ss"+
					" where ss.pay_type='XF' and  ss.account_month = "+query[0];
				
				System.out.println("--tab3--xfStat="+xfStat);
				ArrayList<String[]> xfStatList = DBOperation.select(xfStat);
				
				
				//row2
				sheets[sheetCount].mergeCells(0,2,0,xfStatList.size()+4);
				sheets[sheetCount].addCell(new Label(0, 2,"上海",wcfY1));
				sheets[sheetCount].addCell(new Label(1, 2,"期初金额",wcfY1));
				sheets[sheetCount].addCell(new jxl.write.Number(2, 2,asf.getPriorCash()+asf.getPriorBank(),wcf3));
				sheets[sheetCount].mergeCells(3,2,7,2);
				sheets[sheetCount].addCell(new Label(3, 2,"本期发生额",wcfY1));
				sheets[sheetCount].addCell(new Label(8, 2,"期末金额 ",wcfY1));
				sheets[sheetCount].addCell(new jxl.write.Number(9, 2,asf.getTotalAmount(),wcf3));
				
				//row3
				sheets[sheetCount].addCell(new Label(1, 3,"帐户",wcfY1));
				sheets[sheetCount].addCell(new Label(2, 3,"金额",wcfY1));
				sheets[sheetCount].mergeCells(3,3,4,3);
				sheets[sheetCount].addCell(new Label(3, 3,"收入",wcfY1));
				sheets[sheetCount].mergeCells(5,3,7,3);
				sheets[sheetCount].addCell(new Label(5, 3,"支出",wcfY1));
				sheets[sheetCount].addCell(new Label(8, 3,"帐户",wcfY1));
				sheets[sheetCount].addCell(new Label(9, 3,"金额",wcfY1));
				
				//row4
				sheets[sheetCount].addCell(new Label(1, 4,"上/现",wcfY1));
				sheets[sheetCount].addCell(new jxl.write.Number(2, 4,asf.getPriorCash(),wcf3));
				sheets[sheetCount].addCell(new Label(3, 4,"项目",wcfY1));
				sheets[sheetCount].addCell(new Label(4, 4,"金额",wcfY1));
				sheets[sheetCount].addCell(new Label(5, 4,"科目",wcfY1));
				sheets[sheetCount].addCell(new Label(6, 4,"分类",wcfY1));
				sheets[sheetCount].addCell(new Label(7, 4,"小计",wcfY1));
				sheets[sheetCount].addCell(new Label(8, 4,"上/现",wcfY1));
				sheets[sheetCount].addCell(new jxl.write.Number(9, 4,asf.getCurrentCash(),wcf3));
				
				int tit=5;
				
				//row5 ~ row 5+xfStatList.size()
				for(int i=0;i<xfStatList.size();i++) {
					String[] temp=(String[])xfStatList.get(i);
					if(i==0){
						sheets[sheetCount].addCell(new Label(1, i+tit,"",wcfY1));
						sheets[sheetCount].addCell(new Label(2, i+tit,"",wcfY1));
						sheets[sheetCount].addCell(new Label(3, i+tit,"收入现金",wcfY1));
						sheets[sheetCount].addCell(new jxl.write.Number(4, i+tit,asf.getCashReceipt(),wcf3));
						sheets[sheetCount].mergeCells(5,i+tit,5,xfStatList.size()+i+tit-1);
						sheets[sheetCount].addCell(new Label(5, i+tit,"管销费",wcfY1));
						sheets[sheetCount].addCell(new Label(6, i+tit,temp[0],wcfY1));
						sheets[sheetCount].addCell(new jxl.write.Number(7, i+tit,Double.parseDouble(temp[1]),wcf3));
						sheets[sheetCount].addCell(new Label(8, i+tit,"",wcfY1));
						sheets[sheetCount].addCell(new Label(9, i+tit,"",wcfY1));
					}else{
						sheets[sheetCount].addCell(new Label(1, i+tit,"",wcfY1));
						sheets[sheetCount].addCell(new Label(2, i+tit,"",wcfY1));
						sheets[sheetCount].addCell(new Label(3, i+tit,"",wcfY1));
						sheets[sheetCount].addCell(new Label(4, i+tit,"",wcfY1));
						sheets[sheetCount].addCell(new Label(6, i+tit,temp[0],wcfY1));
						sheets[sheetCount].addCell(new jxl.write.Number(7, i+tit,Double.parseDouble(temp[1]),wcf3));
						sheets[sheetCount].addCell(new Label(8, i+tit,"",wcfY1));
						sheets[sheetCount].addCell(new Label(9, i+tit,"",wcfY1));
					}
				}
				tit+=xfStatList.size();
				
				//row 银行
				sheets[sheetCount].addCell(new Label(0, tit,"晨冉",wcfY1));
				sheets[sheetCount].addCell(new Label(1, tit,"晨冉",wcfY1));
				sheets[sheetCount].addCell(new jxl.write.Number(2, tit,asf.getPriorBank(),wcf3));
				sheets[sheetCount].addCell(new Label(3, tit,"晨冉收入",wcfY1));
				sheets[sheetCount].addCell(new jxl.write.Number(4, tit,asf.getBankReceipt(),wcf3));
				sheets[sheetCount].addCell(new Label(5, tit,"",wcfY1));
				sheets[sheetCount].addCell(new Label(6, tit,"晨冉支出",wcfY1));
				sheets[sheetCount].addCell(new jxl.write.Number(7, tit,asf.getBankPayment(),wcf3));
				sheets[sheetCount].addCell(new Label(8, tit,"晨冉",wcfY1));
				sheets[sheetCount].addCell(new jxl.write.Number(9, tit,asf.getCurrentBank(),wcf3));
				
				//合计
				tit++;
				sheets[sheetCount].addCell(new Label(3, tit,"合计",wcfY1));
				sheets[sheetCount].addCell(new jxl.write.Number(4, tit,(asf.getCashReceipt()+asf.getBankReceipt()),wcf3));
				sheets[sheetCount].addCell(new Label(6, tit,"合计",wcfY1));
				sheets[sheetCount].addCell(new jxl.write.Number(7, tit,(asf.getCashPayment()+asf.getBankPayment()),wcf3));
				
				//说明
				tit=tit+2;
				
				sheets[sheetCount].addCell(new Label(0, tit,"说明一:",wcfY2));
				sheets[sheetCount].addCell(new Label(1, tit,"现金日记帐（2页）",wcfY2));
				sheets[sheetCount].addCell(new Label(6, tit,"说明五:",wcfY2));
				sheets[sheetCount].addCell(new Label(7, tit,"至1月底应付未付款明细(1)页",wcfY2));
				tit++;
				sheets[sheetCount].addCell(new Label(0, tit,"说明二:",wcfY2));
				sheets[sheetCount].addCell(new Label(1, tit,"晨冉银行收支明细(3页)",wcfY2));
				sheets[sheetCount].addCell(new Label(6, tit,"说明六:",wcfY2));
				sheets[sheetCount].addCell(new Label(7, tit,"与高明应收应付结算",wcfY2));
				tit++;
				sheets[sheetCount].addCell(new Label(0, tit,"说明三:",wcfY2));
				sheets[sheetCount].addCell(new Label(1, tit,"现金支出分类明细(3页)",wcfY2));
				sheets[sheetCount].addCell(new Label(6, tit,"说明七:",wcfY2));
				sheets[sheetCount].addCell(new Label(7, tit,"本月还款明细(1)页",wcfY2));
				tit++;
				sheets[sheetCount].addCell(new Label(0, tit,"说明四:",wcfY2));
				sheets[sheetCount].addCell(new Label(1, tit,"至1月底应收未收款明细(1)页",wcfY2));
				sheets[sheetCount].addCell(new Label(6, tit,"说明八:",wcfY2));
				sheets[sheetCount].addCell(new Label(7, tit,"本月欠款明细",wcfY2));
				
			} //end sheet3
			
			

			/*
			 * sheet4:银行存款收支表
			 */
			if(true){
				sheetCount=3;
				//根据sheet名称，创建不同的sheet对象
				String sheetName=SheetNames[sheetCount];
				sheets[sheetCount]=workbook.createSheet(sheetName, sheetCount); 
				
			
				//设置列宽，取最长数据的长度为列宽
				sheets[sheetCount].setColumnView(0,14); 
				sheets[sheetCount].setColumnView(1,20); 
				sheets[sheetCount].setColumnView(2,17); 
				sheets[sheetCount].setColumnView(3,17); 
				sheets[sheetCount].setColumnView(4,14); 
				sheets[sheetCount].setColumnView(5,12); 
				sheets[sheetCount].setColumnView(6,19); 

				//Title:现金支出明细
				sheets[sheetCount].setRowView(0,700); 
				sheets[sheetCount].mergeCells(0,0,6,0);
				labelTitle = new Label(0, 0,query[0].substring(4,6)+"月份收支明细表-晨冉",wcfT3);
				sheets[sheetCount].addCell(labelTitle);
				
				
				/*
				 *	收入统计
				 *
				 *	 代上海办-收入							
				 *  付款日期	付款人		实付金额 	 	机床型号	 		款项内容		款项比列		备注	
					1月4日	以勒精密		2,925.00 								维修费		100%	
					1月4日	苏州道森		2,400,000.00 		 		2000SV*2 	机床款		90%	
					1月4日	爱迪克		1,056,000.00 							机床款		80%	
				 */
				String strSqlYS="SELECT ai.fee_date,ci.CUSTOMER_NAME,ai.money,'' dl," +
					" st.subject_name,CONCAT(round(ai.ratio),'%'),ai.summary "+
					" from td_account_info ai ,td_customer_info ci,td_subject_tree st"+
					" where ai.subject_id=st.subject_id and ai.CUSTOMER_ID=ci.CUSTOMER_ID"+
					" and ai.pay_type='YS' " +
					" and ai.fee_date >= '"+bgDay+"' and ai.fee_date<= '"+endDay+"'" +
					" order by ai.fee_date,ai.CUSTOMER_ID,ai.subject_id";
				
//				System.out.println("--tab4--strSqlYS="+strSqlYS);
				ArrayList<String[]> dataList = DBOperation.select(strSqlYS);
				double ys=0d;
				
				//Title:收入
				sheets[sheetCount].addCell(new Label(0, 1,"代上海办-收入",wcfT2));
				sheets[sheetCount].addCell(new Label(0, 2,"付款日期",wcfY1));
				sheets[sheetCount].addCell(new Label(1, 2,"付款人",wcfY1));
				sheets[sheetCount].addCell(new Label(2, 2,"实付金额 ",wcfY1));
				sheets[sheetCount].addCell(new Label(3, 2,"机床型号",wcfY1));
				sheets[sheetCount].addCell(new Label(4, 2,"款项内容",wcfY1));
				sheets[sheetCount].addCell(new Label(5, 2,"款项比列",wcfY1));
				sheets[sheetCount].addCell(new Label(6, 2,"备注",wcfY1));
				
				int tit=3;
				//行
				for(int i=0;i<dataList.size();i++) {
					String[] temp=(String[])dataList.get(i);
					
					//列
					for (int j = 0; j < temp.length; j++) {
						String value = temp[j]==null?"":temp[j];
						
						if(j==5){
							if(value.indexOf(".00")!=-1){
								value=value.substring(0,value.indexOf(".00"));
							}
						}
						if(j==2&&!value.equals("")){
							labelNumber = new jxl.write.Number(j,i+tit,Double.parseDouble(value),wcfY3);
							sheets[sheetCount].addCell(labelNumber); 
						}else if(j==1||j==6){
							labelData = new Label(j,i+tit,value,wcfY4);
							sheets[sheetCount].addCell(labelData); 
						}else{
							labelData = new Label(j,i+tit,value,wcfY1);
							sheets[sheetCount].addCell(labelData); 
						}
						
						if(j==2&&!value.equals("")){
							ys+=Double.parseDouble(value);
						}
					}
				}
				tit+=dataList.size();
				sheets[sheetCount].addCell(new jxl.write.Number(2,tit,ys,wcfY3));
//				System.out.println("--4--tit1="+tit);

				/*
				 * 支出统计
				 * 
				 * 代上海办-支出						
				 *  付款日期	收款人		实付金额 		合同客户		款项明细		货款		备注
					1月4日	上海办		40000								取现		
					1月4日	上海泛太		805,545.00 				爱迪克		货款		90%	
					1月4日	深发展		1.00 					道森压力		手续费	100%	
				 */
				String strSqlYF="SELECT ai.fee_date,ci.CUSTOMER_NAME,ai.money," +
					" ai.place,st.subject_name,CONCAT(round(ai.ratio),'%'),ai.summary"+
					" from td_account_info ai ,td_customer_info ci,td_subject_tree st"+
					" where ai.subject_id=st.subject_id and ai.CUSTOMER_ID=ci.CUSTOMER_ID"+
					" and ai.pay_type='YF'" +
					" and ai.fee_date >= '"+bgDay+"' and ai.fee_date<= '"+endDay+"'" +
					" order by ai.employee_code,ai.fee_date";
				
//				System.out.println("--tab4--strSqlYF="+strSqlYF);
				dataList = DBOperation.select(strSqlYF);
				ys=0d;
				tit=tit+3;
				
				sheets[sheetCount].setColumnView(7,20); 
				
				//Title:支出
				labelTitle = new Label(0, tit,"代上海办-支出",wcfT2);
				sheets[sheetCount].addCell(labelTitle);
				
				tit++;
				sheets[sheetCount].addCell(new Label(0, tit,"付款日期",wcfY1));
				sheets[sheetCount].addCell(new Label(1, tit,"付款人",wcfY1));
				sheets[sheetCount].addCell(new Label(2, tit,"实付金额 ",wcfY1));
				sheets[sheetCount].addCell(new Label(3, tit,"合同客户",wcfY1));
				sheets[sheetCount].addCell(new Label(4, tit,"款项明细",wcfY1));
				sheets[sheetCount].addCell(new Label(5, tit,"货款",wcfY1));
				sheets[sheetCount].addCell(new Label(6, tit,"备注",wcfY1));
				
				tit++;
				//行
				for(int i=0;i<dataList.size();i++) {
					String[] temp=(String[])dataList.get(i);
					
					//列
					for (int j = 0; j < temp.length; j++) {
						String value = temp[j]==null?"":temp[j];
						
						if(j==5){
							if(value.indexOf(".00")!=-1){
								value=value.substring(0,value.indexOf(".00"));
							}
						}
						if(j==2&&!value.equals("")){
							labelNumber = new jxl.write.Number(j,i+tit,Double.parseDouble(value),wcfY3);
							sheets[sheetCount].addCell(labelNumber); 
						}else if(j==1||j==6){
							labelData = new Label(j,i+tit,value,wcfY4);
							sheets[sheetCount].addCell(labelData); 
						}else{
							labelData = new Label(j,i+tit,value,wcfY1);
							sheets[sheetCount].addCell(labelData); 
						}
						
						if(j==2&&!value.equals("")){
							ys+=Double.parseDouble(value);
						}
					}
				}
				tit+=dataList.size();
				sheets[sheetCount].addCell(new jxl.write.Number(2,tit,ys,wcfY3));
//				System.out.println("--4--tit2="+tit);
				
				//收支结余
				tit=tit+3;
				
				sheets[sheetCount].mergeCells(0,tit,2,tit);
				sheets[sheetCount].addCell(new Label(0, tit,"晨冉"+query[0]+"代上海办收支结余：",wcfT3));
				
				tit=tit+3;
				sheets[sheetCount].mergeCells(1,tit,1,tit+1);
				sheets[sheetCount].addCell(new Label(1, tit,"前期结余",wcfY1));
				sheets[sheetCount].mergeCells(2,tit,2,tit+1);
				sheets[sheetCount].addCell(new Label(2, tit,"本期收支",wcfY1));
				sheets[sheetCount].mergeCells(3,tit,3,tit+1);
				sheets[sheetCount].addCell(new Label(3, tit,"期末结余",wcfY1));
				tit=tit+2;
				sheets[sheetCount].mergeCells(1,tit,1,tit+1);
				sheets[sheetCount].addCell(new jxl.write.Number(1, tit,asf.getPriorBank(),wcfY3));
				sheets[sheetCount].mergeCells(2,tit,2,tit+1);
				sheets[sheetCount].addCell(new jxl.write.Number(2, tit,asf.getBankReceipt()-asf.getBankPayment(),wcfY3));
				sheets[sheetCount].mergeCells(3,tit,3,tit+1);
				sheets[sheetCount].addCell(new jxl.write.Number(3, tit,asf.getCurrentBank(),wcfY3));
				
			} //end sheet4
			

			/*
			 * sheet5:银行日记账
			 */
			if(true){
				sheetCount=4;
				//根据sheet名称，创建不同的sheet对象
				String sheetName=SheetNames[sheetCount];
				sheets[sheetCount]=workbook.createSheet(sheetName, sheetCount); 
				//设置sheet名
				//sheets[sheetCount].setName(me.getKey().toString());
				
			
				//设置列宽，取最长数据的长度为列宽
				sheets[sheetCount].setColumnView(0,6); 
				sheets[sheetCount].setColumnView(1,6); 
				sheets[sheetCount].setColumnView(2,6); 
				sheets[sheetCount].setColumnView(3,14); 
				sheets[sheetCount].setColumnView(4,14); 
				sheets[sheetCount].setColumnView(5,30); 
				sheets[sheetCount].setColumnView(6,20); 
				sheets[sheetCount].setColumnView(7,14);
				sheets[sheetCount].setColumnView(8,14);
				sheets[sheetCount].setColumnView(9,14);

				sheets[sheetCount].setRowView(0,600); 
				//指定合并区域,左上角到右下角,mergeCells(int startCol,int startRow,int endCol,int endRow)
				sheets[sheetCount].mergeCells(0,0,9,0);
				
				//创建单元格对象,Label(列号,行号 ,内容,字体 )
				labelTitle = new Label(0, 0,query[0].substring(0,4)+"年度公司帐目（晨冉）",wcfT1);
				sheets[sheetCount].addCell(labelTitle);
				
				
				sheets[sheetCount].mergeCells(0,1,1,1);
				sheets[sheetCount].addCell(new Label(0, 1,"时间",wcf1));
				sheets[sheetCount].addCell(new Label(0, 2,"月份",wcf1));
				sheets[sheetCount].addCell(new Label(1, 2,"日期",wcf1));
				sheets[sheetCount].mergeCells(2,1,2,2);
				sheets[sheetCount].addCell(new Label(2, 1,"凭证",wcf1));
				sheets[sheetCount].mergeCells(3,1,6,1);
				sheets[sheetCount].addCell(new Label(3, 1,"摘要",wcf1));
				sheets[sheetCount].addCell(new Label(3, 2,"一级科目",wcf7));
				sheets[sheetCount].addCell(new Label(4, 2,"二级科目",wcf7));
				sheets[sheetCount].addCell(new Label(5, 2,"三级科目",wcf7));
				sheets[sheetCount].addCell(new Label(6, 2,"四级科目",wcf7));
				
				sheets[sheetCount].mergeCells(7,1,9,1);
				sheets[sheetCount].addCell(new Label(7, 1,"金额",wcf1));
				sheets[sheetCount].addCell(new Label(7, 2,"收入",wcf1));
				sheets[sheetCount].addCell(new Label(8, 2,"支出",wcf1));
				sheets[sheetCount].addCell(new Label(9, 2,"结存",wcf1));
				
				String strSql="SELECT  month(ai.fee_date) m,dayofmonth(ai.fee_date) d," +
					" ai.voucher_no vn,ai.subject_all_name sn,st.subject_name,"+
					" (select ec.CUSTOMER_NAME from td_customer_info ec " +
					"		where ec.CUSTOMER_ID=ai.CUSTOMER_ID) emp,"+
					" ai.place pl,(case when pay_type='YS' then ai.money else '' end) ys,"+
					" (case when pay_type='YF' then ai.money else '' end) yf,pay_type "+
					" from td_account_info as ai,td_subject_tree as st"+
					" where ai.subject_id=st.subject_id and ai.pay_type in('YS','YF')"+
					" and ai.fee_date >= '"+bgDay+"' and ai.fee_date<= '"+endDay+"'" +
					" order by pay_type desc,ai.fee_date,ai.subject_id";
				
				System.out.println("--tab5--strSql="+strSql);
				ArrayList<String[]> dataList = DBOperation.select(strSql);
				double xs=0d,xf=0d;
				boolean flag = false; 
				int row = 0;
				for(int i=0;i<dataList.size();i++) {
					String[] temp=(String[])dataList.get(i);
					
					if("YS".equals(temp[temp.length-1])){
						row = i + 3;
					}else{
						if(!flag){
							flag = true;
							sheets[sheetCount].addCell(new jxl.write.Number(7, i+3, xs,wcf3));
						}
						row = i + 6;
					}
					
					for (int j = 0; j < temp.length-1; j++) {
						String value = temp[j]==null?"":temp[j];
						if((j==7||j==8)&&!value.equals("")){
							labelNumber = new jxl.write.Number(j,row,Double.parseDouble(value),wcf3);
							sheets[sheetCount].addCell(labelNumber); 
						}else if(j==3){
							if(value.indexOf(" - ")!=-1){
								value=value.substring(0,value.lastIndexOf(" - "));
							}
							labelData = new Label(j,row,value,wcf2);
							sheets[sheetCount].addCell(labelData); 
						}else{
							if(j==0||j==1) labelData = new Label(j,row,value,wcf1);
							else labelData = new Label(j,row,value,wcf2);
							sheets[sheetCount].addCell(labelData); 
						}
						if(j==7&&!value.equals("")){
							xs+=Double.parseDouble(value);
						}else if(j==8&&!value.equals("")){
							xf+=Double.parseDouble(value);
						}
					}
					sheets[sheetCount].addCell(new Label(9,row,"",wcf1)); 
					
				}
				sheets[sheetCount].addCell(new jxl.write.Number(8, dataList.size()+6,xf,wcf3));
				
				
				//总计
				sheets[sheetCount].addCell(new Label(3, dataList.size()+7,"上月结存",wcf5));
				sheets[sheetCount].addCell(new jxl.write.Number(9, dataList.size()+7,asf.getPriorBank(),wcf4));
				
				sheets[sheetCount].addCell(new Label(3, dataList.size()+8,"本月合计",wcf5));
				sheets[sheetCount].addCell(new jxl.write.Number(7, dataList.size()+8,asf.getBankReceipt(),wcf4));
				sheets[sheetCount].addCell(new jxl.write.Number(8, dataList.size()+8,asf.getBankPayment(),wcf4));
				sheets[sheetCount].addCell(new jxl.write.Number(9, dataList.size()+8,asf.getBankReceipt()-asf.getBankPayment(),wcf4));
				
				sheets[sheetCount].addCell(new Label(3, dataList.size()+9,"本年累计",wcf5));
				sheets[sheetCount].addCell(new jxl.write.Number(9, dataList.size()+9,asf.getCurrentBank(),wcf4));
				
			} //end sheet5
			
			
			
			workbook.write(); 
			
			fileName=downLoadPath+fileName;
			//System.out.println("--------fileName="+fileName);
		
		}catch(Exception e){
			throw e;
		} finally {
			try{
				if(workbook!=null) workbook.close();
			}catch(Exception e1){
				e1.printStackTrace();
			}
			try{
				if(os!=null) os.close();
			}catch(Exception e1){
				e1.printStackTrace();
			}
			
		}
		return fileName;
	}
	
}
