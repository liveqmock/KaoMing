package com.dne.sie.util.report;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import jxl.write.*;
import jxl.*;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;

import com.dne.sie.common.exception.ReportException;
import com.dne.sie.common.tools.Operate;
import com.dne.sie.util.hibernate.HbConn;

/**
 * ��������࣬���д��������ı������̳д���
 * @author xt
 * @version 1.1.5.6
 * @see BaseReportQuery.java <br>
 */
public abstract class BaseReportQuery {
	private static Logger logger = Logger.getLogger(BaseReportQuery.class);
	
	protected static char split1=0x0001;
	
	//public static final String downLoadPath="reports/";
	private static final String[] reportsPath=Operate.getReportPath();
	public String strMonth=Operate.getMonth();
	

	/**
	 * ��ѯ�����⣬��ȡ���
	 * @param query - where����
	 * @return fileName - �����ļ���
	 */
	public String createReportFile(String[] query) throws ReportException,Exception{
		ReportForm rf=getReportSql(query);
		rf=getTitles(rf);
		String fileName=reportQuery(rf);
		
		return fileName;
	}
	

	/**
	 * ��ѯ�����⣬��ȡ���
	 * @param ids - where����
	 * @return fileName - �����ļ���
	 */
	public String createReportFile(ActionForm form) throws ReportException,Exception{
		ReportForm rf=getReportSql(form);
		rf=getTitles(rf);
		String fileName=reportQuery(rf);
	
		return fileName;
	}
	

	/**
	 * ��ѯ�������ݿ⣬��ȡ���
	 * @param ids - where����
	 * @return fileName - �����ļ���
	 */
	public String createTxtFile(String ids) throws ReportException,Exception{
		String[] query={ids};

		ReportForm rf=getReportSql(query);
		rf=getTitles(rf);
		
		String fileName=reportTxtQuery(rf,"hsc",null);

		return fileName;
	}

	/**
	 * ��ѯ�������ݿ⣬��ȡ���
	 * @param query - where����
	 * @return fileName - �����ļ���
	 */
	public String createTxtFile(String[] query) throws ReportException,Exception{

		ReportForm rf=getReportSql(query);
		rf=getTitles(rf);
		
		String fileName=reportTxtQuery(rf,"hsc",null);

		return fileName;
	}
	




	/**
	 * ��ѯ�����⣬��ȡ���
	 * @param ids - where����
	 * @return fileName - �����ļ���
	 */
	public String createTxtFile(ActionForm form) throws ReportException,Exception{
		ReportForm rf=getReportSql(form);
		rf=getTitles(rf);
		String fileName=reportTxtQuery(rf,"hsc",null);
		
		
		return fileName;
	}

	/**
	 * ����sql��ѯ��������,ͨ��jxl��д��xls�ļ�
	 * @param query - where����
	 * @return fileName - �����ļ���
	 */
	private String reportQuery(ReportForm rf) throws ReportException,Exception{
		String fileName=null;
		//System.out.println("start:"+Operate.getDate());
		HbConn hbConn=new HbConn();
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		WritableWorkbook workbook=null;
		OutputStream os=null;
		String writePath=null;
		String downLoadPath=null;
		WorkbookSettings ws=null;

		try{
			conn = hbConn.getSession().connection();
			
			TreeMap hmp=(TreeMap)rf.getHsTitles();
			Iterator it=hmp.entrySet().iterator();

			writePath=reportsPath[0]+strMonth+"/";
			downLoadPath=reportsPath[1]+strMonth+"/";
			File f=new File(writePath);
			if(!f.exists()){
				f.mkdirs();
			}
			fileName=rf.getFileName()+Operate.getSectTime()+".xls";

			ws=new WorkbookSettings();
			
			int initSize=rf.getSqlCount()*rf.getColumnCount()*20;
			
			//System.out.println("--------initSize="+initSize);
			if(initSize<1024*64) initSize=1024*64;
			else if(initSize>1024*1024*40){
				throw new ReportException("��ѯ������ݹ���,���޸Ĳ�ѯ����,���²�ѯ");
			}
			
			ws.setInitialFileSize(initSize);
			
			
			os=new FileOutputStream(writePath+fileName);
			workbook=Workbook.createWorkbook(os,ws); 
			
			//sheet�������飬����Ϊҳ�ĸ���
			WritableSheet[] sheets = new WritableSheet[hmp.size()];
			
			//add by ghx:����һ�б���Confidential������Ӵ�һ�ţ�������ʾ
			//WritableFont wf2 = new WritableFont(WritableFont.TIMES,13,WritableFont.BOLD);
			//WritableCellFormat wcf2 = new WritableCellFormat(wf2);
			//add end
			
			//���ô�����ʽ,����Ӵ�
			WritableFont wf1 = new WritableFont(WritableFont.TIMES,16,WritableFont.BOLD);
			WritableCellFormat wcf1 = new WritableCellFormat(wf1);

			//WritableFont wf2 = new WritableFont(WritableFont.TIMES);
			//WritableCellFormat wcf2 = new WritableCellFormat(wf2);
			
			Label labelTitle = null;
			Label labelData = null;
			
			int sheetCount=0;
			//System.out.println("----------hmp.size()="+hmp.size());
			while(it.hasNext()){
				Map.Entry me=(Map.Entry)it.next();

				//����sheet���ƣ�������ͬ��sheet����
				String sheetName=me.getKey().toString();
				if(sheetName.indexOf(split1)!=-1) sheetName=sheetName.substring(sheetName.indexOf(split1)+1);
				sheets[sheetCount]=workbook.createSheet(sheetName, sheetCount); 
				//System.out.println("-------"+sheetCount+"---me.getKey()="+me.getKey());
				//����sheet��
				//sheets[sheetCount].setName(me.getKey().toString());
				
				//ȡ��ÿҳ�еı�����
				String[][] titles = (String[][])me.getValue();
				// System.out.println("----------titles.length="+titles.length);
				 
				String[] sql=rf.getSql();
				pstmt = conn.prepareStatement(sql[sheetCount]);
				rs = pstmt.executeQuery();
				int intColumns = rs.getMetaData().getColumnCount();
				String colValue=null;
				String startCol=null;
				String endCol=null;
				String startRow=null;
				String endRow=null;
				int colNum=0;
				
				/*
				//����Confidential
				labelTitle = new Label(colNum, 0,"Confidential",wcf2);
				sheets[sheetCount].mergeCells(0,0,2,0);
				sheets[sheetCount].addCell(labelTitle);
				//add end
				*/				
				for(int i=0;i<titles.length;i++){
					String[] colNames=titles[i];

					colValue="";
					startCol="";
					endCol="";
					startRow="";
					endRow="";
					colNum=0;
					for (int j = 0; j < colNames.length; j++) {
						String[] regionList=colNames[j].split("@");
						if(regionList!=null&&regionList.length>0){
							
							if(regionList.length>=1){
								colValue=regionList[0];
							}
							if(regionList.length>=2){
								startRow=regionList[1];
							}							
							if(regionList.length>=3){
								startCol=regionList[2];
							}
							if(regionList.length>=4){
								endRow=regionList[3];
							}	
							if(regionList.length>=5){
								endCol=regionList[4];
							}		
						}
						if("".equals(endRow) && !"".equals(startRow)) endRow=startRow;
						if("".equals(endCol) && !"".equals(startCol)) endCol=startCol;
						

						if(!"".equals(startRow)  && !"".equals(startCol) && !"".equals(endCol)){
							//ָ���ϲ�����,���Ͻǵ����½�,mergeCells(int col1,int row1,int col2,int row2)
							sheets[sheetCount].mergeCells(Integer.parseInt(startCol),Integer.parseInt(startRow),
								Integer.parseInt(endCol),Integer.parseInt(endRow));
						}
						
						//�����п�ȡ����ݵĳ���Ϊ�п�
						//int sh=Operate.getStringLength(colNames[j]);
						//sheets[sheetCount].setColumnView(colNum,sh); 

						//�����Զ����� 
						//wcf1.setWrap(true);
						//���ж���
						//wcf1.setAlignment(jxl.format.Alignment.CENTRE); 
						//������Ԫ�����,Label(�к�,�к� ,����,���� )
						labelTitle = new Label(colNum, i,colValue,wcf1);
						sheets[sheetCount].addCell(labelTitle);

						if("".equals(endCol)){
							colNum=colNum+1;
						}else{
							colNum=Integer.parseInt(endCol)+1;
						}
					}
				}

				
				
				int i=titles.length;
				
				while (rs.next()) {
					//������i�ж���
					//rowData = sheets[sheetCount].createRow((short)i);
					for (int j = 0; j < intColumns; j++) {
						String strTemp = rs.getString(j + 1);
						
						//wcf2 = new WritableCellFormat(wf2);
						//�����Զ����� 
						//wcf2.setWrap(true);
						//labelData = new Label(j,i,strTemp==null?"":strTemp,wcf2);
						labelData = new Label(j,i+1,strTemp==null?"":strTemp);
						sheets[sheetCount].addCell(labelData); 
						//������Ԫ�����
						//cell = rowData.createCell((short)j);
						
					}
					i++;
				}
				sheetCount++;
				//excel��sheetҳ���255��������oracle�α�Ĭ�����300
				if(sheetCount>255){
					break;
				}
			}

			workbook.write(); 
			
			fileName=downLoadPath+fileName;
			//System.out.println("--------fileName="+fileName);
		} catch (ReportException re) {
			throw re;
		}catch(Exception e){
			e.printStackTrace();
		
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
			try{
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
			}catch(Exception e2){
				e2.printStackTrace();
			}finally{
				hbConn.closeSession();
				
			}
		}
		//System.out.println("end:"+Operate.getDate());
		return fileName;
	}

	/**
	 * ����sql��ѯ��������,ͨ���ļ�������ʽд��xls�ļ�
	 * @param ReportForm ��ѯ����,�ļ�����title��
	 * @param String wPath - hsc:�ļ�·��+hashCode������:�ļ���+����
	 * @param String wFile - �ļ�����title��־
	 * @return fileName - �����ļ���
	 */
	private String reportTxtQuery(ReportForm report,String wPath,String wFile) throws Exception{
		String fileName=null;
		HbConn hbConn=new HbConn();
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		RandomAccessFile rf=null;
		String writePath=null;
		String downLoadPath=null;
		
		try{
			conn = hbConn.getSession().connection();
			
			if("hsc".equals(wPath)){
				fileName=report.getFileName()+Operate.getSectTime()+".xls";
				
				downLoadPath=reportsPath[1]+strMonth+"/";
				writePath=reportsPath[0]+strMonth+"/";
				//System.out.println("---------fileName="+fileName);
				//System.out.println("---------writePath="+writePath);
			}else{
				fileName=report.getFileName()+wFile+".xls";
				writePath=Operate.getSysPath()+wPath;
				//System.out.println("---------fileName="+fileName);
				//System.out.println("---------writePath="+writePath);
			}

			File f=new File(writePath);
			if(!f.exists()){
				f.mkdirs();
			}

			pstmt = conn.prepareStatement(report.getSql()[0]);
			rs = pstmt.executeQuery();
			int intColumns = rs.getMetaData().getColumnCount();
			
			String[] title=null;
			if("hsc".equals(wPath)&&"title".equals(wFile)){
				HashMap hmp=(HashMap)report.getHsTitles();
				Iterator it=hmp.entrySet().iterator();
				Map.Entry me=(Map.Entry)it.next();
				String[][] titles = (String[][])me.getValue();
				title=titles[0];
			}else{
				title=report.getStrTitles();
			}
			
			//����һ����RandomAccessFile�Ķ��󣬲�ʵ����
			rf=new RandomAccessFile(writePath+fileName,"rw");
			if("hsc".equals(wPath)) fileName=downLoadPath+fileName;
			
			String titleTmp="Confidential\r\n";
			for(int i=0;i<title.length;i++){
				if(i==0) titleTmp+=title[i];
				else{ 
					titleTmp+="\t"+title[i]; 
				}
			}
			//��ָ���ƶ����ļ�ĩβ
			rf.seek(rf.length());
			//rf.writeUTF(titleTmp+"\r\n");
			rf.write((titleTmp+"\r\n").getBytes("GBK"));

			String strTemp=null;
			while (rs.next()) {
				strTemp=null;
				for (int i = 0; i < intColumns; i++) {
					String val=rs.getString(i + 1);
					if(val==null||"".equals(val)) val="";
					else if(val.indexOf("\r\n")!=-1){ 
						val=val.replaceAll("\r\n"," ");
					}
										
					if(i==0) strTemp=val;
					else strTemp+="\t"+val;
				}
				//��ָ���ƶ����ļ�ĩβ
				rf.seek(rf.length());
				//rf.writeBytes(strTemp+"\r\n");
				rf.write((strTemp+"\r\n").getBytes("GBK"));
			}

		}catch(Exception e){
			e.printStackTrace();
			
		} finally {
			try{
				if(rf!=null) rf.close();
			}catch(IOException ioe){
				ioe.printStackTrace();
			}
			
			try{
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
			}catch(Exception e2){
				e2.printStackTrace();
			}finally{
				hbConn.closeSession();
			}
		}
		return fileName;
	}
	

	/**
	 * ��job����,ÿ��5�Զ�������ܵı����ļ���
	 */
	public static void deleteHistoryFile(){
		try{
			//������ǰĿ¼���ļ���File����
			File d=new File(reportsPath[0]);
			
			//ȡ��Ŀ¼�������ļ���File��������
			File[] list=d.listFiles();
			if(list!=null){
				for(int i=0;i<list.length;i++){
					//������ļ���
					if(list[i].isDirectory()){
						String tempName=list[i].getName();
						//������Ǳ������ɵ��ļ���
						if(!tempName.equals(Operate.getWeek2())){
							Operate.folderDelete(list[i],0);
						}
					}
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	
	protected abstract ReportForm getReportSql(String[] query)  throws ReportException;
	protected abstract ReportForm getReportSql(ActionForm form)  throws ReportException;
	
	protected abstract ReportForm getTitles(ReportForm rf);


	public static void main(String args[]){
		try{
			//BaseReportQuery.deleteHistoryFile();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		

	}
	
}
