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
 * ����ͳ�Ʊ���
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
	
	private static final String[] SheetNames={"�ֽ��ռ���","֧����ϸ��","��֧��ϸ��","���д����֧��","�����ռ���"};
	
	
	public String createReportFile(String[] query) throws Exception{
//		System.out.println("---month="+query[0]);
		return reportQuery(query);
	}
	



	/**
	 * ����sql��ѯ��������,ͨ��jxl��д��xls�ļ�
	 * @param query - where����
	 * @return fileName - �����ļ���
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
			
			//sheet�������飬����Ϊҳ�ĸ���
			WritableSheet[] sheets = new WritableSheet[SheetNames.length];
			
			//���ô�����ʽ,����Ӵ�
			WritableFont wfT1 = new WritableFont(WritableFont.createFont("����_GB2312"),14,WritableFont.BOLD);
			WritableCellFormat wcfT1 = new WritableCellFormat(wfT1);
			wcfT1.setAlignment(jxl.format.Alignment.CENTRE); 		//���ж���
			wcfT1.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);

			WritableFont wfT2 = new WritableFont(WritableFont.createFont("����_GB2312"),10,WritableFont.BOLD);
			WritableCellFormat wcfT2 = new WritableCellFormat(wfT2);
			wcfT2.setAlignment(jxl.format.Alignment.LEFT); 			//�������
			
			WritableFont wfT3 = new WritableFont(WritableFont.createFont("����"),12,WritableFont.BOLD);
			WritableCellFormat wcfT3 = new WritableCellFormat(wfT3);
			wcfT3.setAlignment(jxl.format.Alignment.CENTRE); 		//���ж���
			wcfT3.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			
			
			NumberFormat nf = new NumberFormat("0.00");

			WritableFont wf1 = new WritableFont(WritableFont.createFont("����_GB2312"),10);
			WritableFont wf2 = new WritableFont(WritableFont.createFont("����"),10);
			WritableFont wf3 = new WritableFont(WritableFont.createFont("����_GB2312"),10,WritableFont.BOLD);

			WritableCellFormat wcfY1 = new WritableCellFormat(wf2);	//���С�ϸ��
			wcfY1.setAlignment(jxl.format.Alignment.CENTRE); 
			wcfY1.setBorder(Border.ALL, BorderLineStyle.THIN);
			wcfY1.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			
			WritableCellFormat wcfY2 = new WritableCellFormat(wf2);	//����
			wcfY2.setAlignment(jxl.format.Alignment.LEFT); 
			
			WritableCellFormat wcfY3 = new WritableCellFormat(wf2);	//����ϸ��
			wcfY3.setBorder(Border.ALL, BorderLineStyle.THIN);
			wcfY3.setAlignment(jxl.format.Alignment.RIGHT); 
			
			WritableCellFormat wcfY4 = new WritableCellFormat(wf2);	//����ϸ��
			wcfY4.setBorder(Border.ALL, BorderLineStyle.THIN);
			wcfY4.setAlignment(jxl.format.Alignment.LEFT); 
			
			WritableCellFormat wcf1 = new WritableCellFormat(wf1);	//���С�ϸ��
			wcf1.setAlignment(jxl.format.Alignment.CENTRE); 
			wcf1.setBorder(Border.ALL, BorderLineStyle.THIN);
			
			WritableCellFormat wcf2 = new WritableCellFormat(wf1);	//����ϸ��
			wcf2.setAlignment(jxl.format.Alignment.LEFT); 
			wcf2.setBorder(Border.ALL, BorderLineStyle.THIN);
			
			WritableCellFormat wcf3 = new WritableCellFormat(wf1,nf);	//���ҡ�ϸ��
			wcf3.setAlignment(jxl.format.Alignment.RIGHT); 
			wcf3.setBorder(Border.ALL, BorderLineStyle.THIN);
			
			WritableCellFormat wcf4 = new WritableCellFormat(wf1,nf);	//���ҡ��޿�
			wcf4.setAlignment(jxl.format.Alignment.RIGHT);

			WritableCellFormat wcf5 = new WritableCellFormat(wf1);	//�����޿�
			wcf5.setAlignment(jxl.format.Alignment.LEFT);
			
			WritableCellFormat wcf6 = new WritableCellFormat(wf1);	//�������С�ϸ��
			wcf6.setAlignment(jxl.format.Alignment.CENTRE); 
			wcf6.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			wcf6.setBorder(Border.ALL, BorderLineStyle.THIN);
			
			WritableCellFormat wcf7 = new WritableCellFormat(wf3);	//���С�ϸ��
			wcf7.setAlignment(jxl.format.Alignment.CENTRE); 
			wcf7.setBorder(Border.ALL, BorderLineStyle.THIN);

			
			Label labelTitle = null;
			Label labelData = null;
			jxl.write.Number labelNumber=null;
			
			int sheetCount=0;
			
			/*
			 * sheet1:�ֽ��ռ���
			 */
			if(true){
				//����sheet���ƣ�������ͬ��sheet����
				String sheetName=SheetNames[sheetCount];
				sheets[sheetCount]=workbook.createSheet(sheetName, 0); 
				//����sheet��
				//sheets[sheetCount].setName(me.getKey().toString());
				
				//����һ�еĸ߶���Ϊ46
				sheets[sheetCount].setRowView(0,700); 
			
				//�����п�ȡ����ݵĳ���Ϊ�п�
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

				//�����Զ����� 
				//wcf1.setWrap(true);
				

				//ָ���ϲ�����,���Ͻǵ����½�,mergeCells(int startCol,int startRow,int endCol,int endRow)
				sheets[sheetCount].mergeCells(0,0,9,0);
				//������Ԫ�����,Label(�к�,�к� ,����,���� )
				labelTitle = new Label(0, 0,"�Ϻ����ֽ�"+query[0].substring(4,6)+"�·ݣ�",wcfT1);
				sheets[sheetCount].addCell(labelTitle);
				
				sheets[sheetCount].mergeCells(0,1,2,1);
				sheets[sheetCount].addCell(new Label(0, 1,query[0].substring(2,4)+"��",wcf1));
				sheets[sheetCount].addCell(new Label(0, 2,"�·�",wcf1));
				sheets[sheetCount].addCell(new Label(1, 2,"����",wcf1));
				sheets[sheetCount].addCell(new Label(2, 2,"ƾ֤",wcf1));
				
				sheets[sheetCount].mergeCells(3,1,6,2);
				sheets[sheetCount].addCell(new Label(3, 1,"ժҪ",wcf1));

				sheets[sheetCount].mergeCells(7,1,7,2);
				sheets[sheetCount].addCell(new Label(7, 1,"������",wcf1));
				sheets[sheetCount].mergeCells(8,1,8,2);
				sheets[sheetCount].addCell(new Label(8, 1,"֧�����",wcf1));
				sheets[sheetCount].mergeCells(9,1,9,2);
				sheets[sheetCount].addCell(new Label(9, 1,"�����",wcf1));
				
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
						//�����Զ����� 
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
				
				//�ܼ�
				sheets[sheetCount].addCell(new Label(3, dataList.size()+3,"���½��",wcf5));
				sheets[sheetCount].addCell(new jxl.write.Number(9, dataList.size()+3,asf.getPriorCash(),wcf4));
				
				sheets[sheetCount].addCell(new Label(3, dataList.size()+4,"���ºϼ�",wcf5));
				sheets[sheetCount].addCell(new jxl.write.Number(7, dataList.size()+4,asf.getCashReceipt(),wcf4));
				sheets[sheetCount].addCell(new jxl.write.Number(8, dataList.size()+4,asf.getCashPayment(),wcf4));
				sheets[sheetCount].addCell(new jxl.write.Number(9, dataList.size()+4,asf.getCashReceipt()-asf.getCashPayment(),wcf4));
				
				sheets[sheetCount].addCell(new Label(3, dataList.size()+5,"�����ۼ�",wcf5));
				sheets[sheetCount].addCell(new jxl.write.Number(9, dataList.size()+5,asf.getCurrentCash(),wcf4));
				
			} //end sheet1
			
			

			/*
			 * sheet2:�ֽ�֧����ϸ��
			 */
			if(true){
				sheetCount=1;
				//����sheet���ƣ�������ͬ��sheet����
				String sheetName=SheetNames[sheetCount];
				sheets[sheetCount]=workbook.createSheet(sheetName, sheetCount); 
				
			
				//�����п�ȡ����ݵĳ���Ϊ�п�
				sheets[sheetCount].setColumnView(0,5); 
				sheets[sheetCount].setColumnView(1,5); 
				sheets[sheetCount].setColumnView(2,6); 
				sheets[sheetCount].setColumnView(3,15); 
				sheets[sheetCount].setColumnView(4,15); 
				sheets[sheetCount].setColumnView(5,15); 
				sheets[sheetCount].setColumnView(6,15); 
				sheets[sheetCount].setColumnView(7,15); 

				//Title:�ֽ�֧����ϸ
				sheets[sheetCount].mergeCells(0,0,7,0);
				labelTitle = new Label(0, 0,query[0].substring(4,6)+"�·�֧����ϸ",wcfT1);
				sheets[sheetCount].addCell(labelTitle);
				
				AccountBo ab = AccountBo.getInstance();
				String statIds=ab.getReportStatIds();
				
				/*
				 *	subjectͳ��
				 *	 ��ݷ�							
					1	4	5	��Ӫҵ��	��ݷ�	�ų���	����					336.00 
					1	18	75	��Ӫҵ��	��ݷ�	����		����/��צ/�ļ���ͬ	715.00 
					1	21	83	��Ӫҵ��	��ݷ�	����		͹��/�ӽ�����		1,505.00 
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
				//��
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
							
							//ͳ����һsubject_name�ܷ���
							sheets[sheetCount].addCell(new jxl.write.Number(7,i+tit,xs,wcf3));
							xs=0;
							tit++;
							
							//��ǰsubject_name
							if("1".equals(temp[8])){
								sheets[sheetCount].addCell(new Label(0,i+tit,temp[4],wcf5));
							}else{
								sheets[sheetCount].addCell(new Label(0,i+tit,temp[3],wcf5));
							}
							
							tit++;
						}
					}
					//��
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
				 * employeeͳ��ͳ��
				 * ����							
					1	20	80	��Ӫ��	���÷�	����	���ݵ�ɭ		405.00 
					1	20	81	��Ӫ��	���ڳ���	����	����			410.00 
					1	20	82	��Ӫ��	���÷�	����	��ɽ÷����	1,343.00 
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
				
				//Title:���÷�
				sheets[sheetCount].mergeCells(0,tit,7,tit);
				labelTitle = new Label(0, tit,"���÷�",wcfT1);
				sheets[sheetCount].addCell(labelTitle);
				tit++;
				
				//��
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
					//��
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
				
				//�ֽ�֧���ܼ�
				String monXF=DBOperation.selectOne("SELECT cash_payment FROM td_account_statistics t where account_month="+query[0]);
				
				//sheets[sheetCount].addCell(new Label(6, tit+2,query[0]+"�ֽ�֧��",wcf5));
				sheets[sheetCount].addCell(new jxl.write.Number(7, tit+2,Double.parseDouble(monXF),wcf4));
				
			} //end sheet2
			
			
			

			/*
			 * sheet3:��֧��ϸ��
			 */
			if(true){
				sheetCount=2;
				//����sheet���ƣ�������ͬ��sheet����
				String sheetName=SheetNames[sheetCount];
				sheets[sheetCount]=workbook.createSheet(sheetName, sheetCount);

				//�����п�ȡ����ݵĳ���Ϊ�п�
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
				
				
				//Title:һ�·���֧��ϸ��
				sheets[sheetCount].mergeCells(0,0,9,0);
				labelTitle = new Label(0, 0,query[0]+"��֧��ϸ��",wcfT3);
				sheets[sheetCount].addCell(labelTitle);
				
				
				String xfStat="select ss.subject_name,ss.money from td_account_statistics_subject ss"+
					" where ss.pay_type='XF' and  ss.account_month = "+query[0];
				
				System.out.println("--tab3--xfStat="+xfStat);
				ArrayList<String[]> xfStatList = DBOperation.select(xfStat);
				
				
				//row2
				sheets[sheetCount].mergeCells(0,2,0,xfStatList.size()+4);
				sheets[sheetCount].addCell(new Label(0, 2,"�Ϻ�",wcfY1));
				sheets[sheetCount].addCell(new Label(1, 2,"�ڳ����",wcfY1));
				sheets[sheetCount].addCell(new jxl.write.Number(2, 2,asf.getPriorCash()+asf.getPriorBank(),wcf3));
				sheets[sheetCount].mergeCells(3,2,7,2);
				sheets[sheetCount].addCell(new Label(3, 2,"���ڷ�����",wcfY1));
				sheets[sheetCount].addCell(new Label(8, 2,"��ĩ��� ",wcfY1));
				sheets[sheetCount].addCell(new jxl.write.Number(9, 2,asf.getTotalAmount(),wcf3));
				
				//row3
				sheets[sheetCount].addCell(new Label(1, 3,"�ʻ�",wcfY1));
				sheets[sheetCount].addCell(new Label(2, 3,"���",wcfY1));
				sheets[sheetCount].mergeCells(3,3,4,3);
				sheets[sheetCount].addCell(new Label(3, 3,"����",wcfY1));
				sheets[sheetCount].mergeCells(5,3,7,3);
				sheets[sheetCount].addCell(new Label(5, 3,"֧��",wcfY1));
				sheets[sheetCount].addCell(new Label(8, 3,"�ʻ�",wcfY1));
				sheets[sheetCount].addCell(new Label(9, 3,"���",wcfY1));
				
				//row4
				sheets[sheetCount].addCell(new Label(1, 4,"��/��",wcfY1));
				sheets[sheetCount].addCell(new jxl.write.Number(2, 4,asf.getPriorCash(),wcf3));
				sheets[sheetCount].addCell(new Label(3, 4,"��Ŀ",wcfY1));
				sheets[sheetCount].addCell(new Label(4, 4,"���",wcfY1));
				sheets[sheetCount].addCell(new Label(5, 4,"��Ŀ",wcfY1));
				sheets[sheetCount].addCell(new Label(6, 4,"����",wcfY1));
				sheets[sheetCount].addCell(new Label(7, 4,"С��",wcfY1));
				sheets[sheetCount].addCell(new Label(8, 4,"��/��",wcfY1));
				sheets[sheetCount].addCell(new jxl.write.Number(9, 4,asf.getCurrentCash(),wcf3));
				
				int tit=5;
				
				//row5 ~ row 5+xfStatList.size()
				for(int i=0;i<xfStatList.size();i++) {
					String[] temp=(String[])xfStatList.get(i);
					if(i==0){
						sheets[sheetCount].addCell(new Label(1, i+tit,"",wcfY1));
						sheets[sheetCount].addCell(new Label(2, i+tit,"",wcfY1));
						sheets[sheetCount].addCell(new Label(3, i+tit,"�����ֽ�",wcfY1));
						sheets[sheetCount].addCell(new jxl.write.Number(4, i+tit,asf.getCashReceipt(),wcf3));
						sheets[sheetCount].mergeCells(5,i+tit,5,xfStatList.size()+i+tit-1);
						sheets[sheetCount].addCell(new Label(5, i+tit,"������",wcfY1));
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
				
				//row ����
				sheets[sheetCount].addCell(new Label(0, tit,"��Ƚ",wcfY1));
				sheets[sheetCount].addCell(new Label(1, tit,"��Ƚ",wcfY1));
				sheets[sheetCount].addCell(new jxl.write.Number(2, tit,asf.getPriorBank(),wcf3));
				sheets[sheetCount].addCell(new Label(3, tit,"��Ƚ����",wcfY1));
				sheets[sheetCount].addCell(new jxl.write.Number(4, tit,asf.getBankReceipt(),wcf3));
				sheets[sheetCount].addCell(new Label(5, tit,"",wcfY1));
				sheets[sheetCount].addCell(new Label(6, tit,"��Ƚ֧��",wcfY1));
				sheets[sheetCount].addCell(new jxl.write.Number(7, tit,asf.getBankPayment(),wcf3));
				sheets[sheetCount].addCell(new Label(8, tit,"��Ƚ",wcfY1));
				sheets[sheetCount].addCell(new jxl.write.Number(9, tit,asf.getCurrentBank(),wcf3));
				
				//�ϼ�
				tit++;
				sheets[sheetCount].addCell(new Label(3, tit,"�ϼ�",wcfY1));
				sheets[sheetCount].addCell(new jxl.write.Number(4, tit,(asf.getCashReceipt()+asf.getBankReceipt()),wcf3));
				sheets[sheetCount].addCell(new Label(6, tit,"�ϼ�",wcfY1));
				sheets[sheetCount].addCell(new jxl.write.Number(7, tit,(asf.getCashPayment()+asf.getBankPayment()),wcf3));
				
				//˵��
				tit=tit+2;
				
				sheets[sheetCount].addCell(new Label(0, tit,"˵��һ:",wcfY2));
				sheets[sheetCount].addCell(new Label(1, tit,"�ֽ��ռ��ʣ�2ҳ��",wcfY2));
				sheets[sheetCount].addCell(new Label(6, tit,"˵����:",wcfY2));
				sheets[sheetCount].addCell(new Label(7, tit,"��1�µ�Ӧ��δ������ϸ(1)ҳ",wcfY2));
				tit++;
				sheets[sheetCount].addCell(new Label(0, tit,"˵����:",wcfY2));
				sheets[sheetCount].addCell(new Label(1, tit,"��Ƚ������֧��ϸ(3ҳ)",wcfY2));
				sheets[sheetCount].addCell(new Label(6, tit,"˵����:",wcfY2));
				sheets[sheetCount].addCell(new Label(7, tit,"�����Ӧ��Ӧ������",wcfY2));
				tit++;
				sheets[sheetCount].addCell(new Label(0, tit,"˵����:",wcfY2));
				sheets[sheetCount].addCell(new Label(1, tit,"�ֽ�֧��������ϸ(3ҳ)",wcfY2));
				sheets[sheetCount].addCell(new Label(6, tit,"˵����:",wcfY2));
				sheets[sheetCount].addCell(new Label(7, tit,"���»�����ϸ(1)ҳ",wcfY2));
				tit++;
				sheets[sheetCount].addCell(new Label(0, tit,"˵����:",wcfY2));
				sheets[sheetCount].addCell(new Label(1, tit,"��1�µ�Ӧ��δ�տ���ϸ(1)ҳ",wcfY2));
				sheets[sheetCount].addCell(new Label(6, tit,"˵����:",wcfY2));
				sheets[sheetCount].addCell(new Label(7, tit,"����Ƿ����ϸ",wcfY2));
				
			} //end sheet3
			
			

			/*
			 * sheet4:���д����֧��
			 */
			if(true){
				sheetCount=3;
				//����sheet���ƣ�������ͬ��sheet����
				String sheetName=SheetNames[sheetCount];
				sheets[sheetCount]=workbook.createSheet(sheetName, sheetCount); 
				
			
				//�����п�ȡ����ݵĳ���Ϊ�п�
				sheets[sheetCount].setColumnView(0,14); 
				sheets[sheetCount].setColumnView(1,20); 
				sheets[sheetCount].setColumnView(2,17); 
				sheets[sheetCount].setColumnView(3,17); 
				sheets[sheetCount].setColumnView(4,14); 
				sheets[sheetCount].setColumnView(5,12); 
				sheets[sheetCount].setColumnView(6,19); 

				//Title:�ֽ�֧����ϸ
				sheets[sheetCount].setRowView(0,700); 
				sheets[sheetCount].mergeCells(0,0,6,0);
				labelTitle = new Label(0, 0,query[0].substring(4,6)+"�·���֧��ϸ��-��Ƚ",wcfT3);
				sheets[sheetCount].addCell(labelTitle);
				
				
				/*
				 *	����ͳ��
				 *
				 *	 ���Ϻ���-����							
				 *  ��������	������		ʵ����� 	 	�����ͺ�	 		��������		�������		��ע	
					1��4��	���վ���		2,925.00 								ά�޷�		100%	
					1��4��	���ݵ�ɭ		2,400,000.00 		 		2000SV*2 	������		90%	
					1��4��	���Ͽ�		1,056,000.00 							������		80%	
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
				
				//Title:����
				sheets[sheetCount].addCell(new Label(0, 1,"���Ϻ���-����",wcfT2));
				sheets[sheetCount].addCell(new Label(0, 2,"��������",wcfY1));
				sheets[sheetCount].addCell(new Label(1, 2,"������",wcfY1));
				sheets[sheetCount].addCell(new Label(2, 2,"ʵ����� ",wcfY1));
				sheets[sheetCount].addCell(new Label(3, 2,"�����ͺ�",wcfY1));
				sheets[sheetCount].addCell(new Label(4, 2,"��������",wcfY1));
				sheets[sheetCount].addCell(new Label(5, 2,"�������",wcfY1));
				sheets[sheetCount].addCell(new Label(6, 2,"��ע",wcfY1));
				
				int tit=3;
				//��
				for(int i=0;i<dataList.size();i++) {
					String[] temp=(String[])dataList.get(i);
					
					//��
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
				 * ֧��ͳ��
				 * 
				 * ���Ϻ���-֧��						
				 *  ��������	�տ���		ʵ����� 		��ͬ�ͻ�		������ϸ		����		��ע
					1��4��	�Ϻ���		40000								ȡ��		
					1��4��	�Ϻ���̫		805,545.00 				���Ͽ�		����		90%	
					1��4��	�չ		1.00 					��ɭѹ��		������	100%	
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
				
				//Title:֧��
				labelTitle = new Label(0, tit,"���Ϻ���-֧��",wcfT2);
				sheets[sheetCount].addCell(labelTitle);
				
				tit++;
				sheets[sheetCount].addCell(new Label(0, tit,"��������",wcfY1));
				sheets[sheetCount].addCell(new Label(1, tit,"������",wcfY1));
				sheets[sheetCount].addCell(new Label(2, tit,"ʵ����� ",wcfY1));
				sheets[sheetCount].addCell(new Label(3, tit,"��ͬ�ͻ�",wcfY1));
				sheets[sheetCount].addCell(new Label(4, tit,"������ϸ",wcfY1));
				sheets[sheetCount].addCell(new Label(5, tit,"����",wcfY1));
				sheets[sheetCount].addCell(new Label(6, tit,"��ע",wcfY1));
				
				tit++;
				//��
				for(int i=0;i<dataList.size();i++) {
					String[] temp=(String[])dataList.get(i);
					
					//��
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
				
				//��֧����
				tit=tit+3;
				
				sheets[sheetCount].mergeCells(0,tit,2,tit);
				sheets[sheetCount].addCell(new Label(0, tit,"��Ƚ"+query[0]+"���Ϻ�����֧���ࣺ",wcfT3));
				
				tit=tit+3;
				sheets[sheetCount].mergeCells(1,tit,1,tit+1);
				sheets[sheetCount].addCell(new Label(1, tit,"ǰ�ڽ���",wcfY1));
				sheets[sheetCount].mergeCells(2,tit,2,tit+1);
				sheets[sheetCount].addCell(new Label(2, tit,"������֧",wcfY1));
				sheets[sheetCount].mergeCells(3,tit,3,tit+1);
				sheets[sheetCount].addCell(new Label(3, tit,"��ĩ����",wcfY1));
				tit=tit+2;
				sheets[sheetCount].mergeCells(1,tit,1,tit+1);
				sheets[sheetCount].addCell(new jxl.write.Number(1, tit,asf.getPriorBank(),wcfY3));
				sheets[sheetCount].mergeCells(2,tit,2,tit+1);
				sheets[sheetCount].addCell(new jxl.write.Number(2, tit,asf.getBankReceipt()-asf.getBankPayment(),wcfY3));
				sheets[sheetCount].mergeCells(3,tit,3,tit+1);
				sheets[sheetCount].addCell(new jxl.write.Number(3, tit,asf.getCurrentBank(),wcfY3));
				
			} //end sheet4
			

			/*
			 * sheet5:�����ռ���
			 */
			if(true){
				sheetCount=4;
				//����sheet���ƣ�������ͬ��sheet����
				String sheetName=SheetNames[sheetCount];
				sheets[sheetCount]=workbook.createSheet(sheetName, sheetCount); 
				//����sheet��
				//sheets[sheetCount].setName(me.getKey().toString());
				
			
				//�����п�ȡ����ݵĳ���Ϊ�п�
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
				//ָ���ϲ�����,���Ͻǵ����½�,mergeCells(int startCol,int startRow,int endCol,int endRow)
				sheets[sheetCount].mergeCells(0,0,9,0);
				
				//������Ԫ�����,Label(�к�,�к� ,����,���� )
				labelTitle = new Label(0, 0,query[0].substring(0,4)+"��ȹ�˾��Ŀ����Ƚ��",wcfT1);
				sheets[sheetCount].addCell(labelTitle);
				
				
				sheets[sheetCount].mergeCells(0,1,1,1);
				sheets[sheetCount].addCell(new Label(0, 1,"ʱ��",wcf1));
				sheets[sheetCount].addCell(new Label(0, 2,"�·�",wcf1));
				sheets[sheetCount].addCell(new Label(1, 2,"����",wcf1));
				sheets[sheetCount].mergeCells(2,1,2,2);
				sheets[sheetCount].addCell(new Label(2, 1,"ƾ֤",wcf1));
				sheets[sheetCount].mergeCells(3,1,6,1);
				sheets[sheetCount].addCell(new Label(3, 1,"ժҪ",wcf1));
				sheets[sheetCount].addCell(new Label(3, 2,"һ����Ŀ",wcf7));
				sheets[sheetCount].addCell(new Label(4, 2,"������Ŀ",wcf7));
				sheets[sheetCount].addCell(new Label(5, 2,"������Ŀ",wcf7));
				sheets[sheetCount].addCell(new Label(6, 2,"�ļ���Ŀ",wcf7));
				
				sheets[sheetCount].mergeCells(7,1,9,1);
				sheets[sheetCount].addCell(new Label(7, 1,"���",wcf1));
				sheets[sheetCount].addCell(new Label(7, 2,"����",wcf1));
				sheets[sheetCount].addCell(new Label(8, 2,"֧��",wcf1));
				sheets[sheetCount].addCell(new Label(9, 2,"���",wcf1));
				
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
				
				
				//�ܼ�
				sheets[sheetCount].addCell(new Label(3, dataList.size()+7,"���½��",wcf5));
				sheets[sheetCount].addCell(new jxl.write.Number(9, dataList.size()+7,asf.getPriorBank(),wcf4));
				
				sheets[sheetCount].addCell(new Label(3, dataList.size()+8,"���ºϼ�",wcf5));
				sheets[sheetCount].addCell(new jxl.write.Number(7, dataList.size()+8,asf.getBankReceipt(),wcf4));
				sheets[sheetCount].addCell(new jxl.write.Number(8, dataList.size()+8,asf.getBankPayment(),wcf4));
				sheets[sheetCount].addCell(new jxl.write.Number(9, dataList.size()+8,asf.getBankReceipt()-asf.getBankPayment(),wcf4));
				
				sheets[sheetCount].addCell(new Label(3, dataList.size()+9,"�����ۼ�",wcf5));
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
