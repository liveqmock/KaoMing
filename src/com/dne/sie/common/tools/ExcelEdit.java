/*
 * �������� 2005-10-19
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package com.dne.sie.common.tools;

import java.io.*;

import jxl.*;
//import jxl.write.*;

import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;

import org.apache.log4j.Logger;

public class ExcelEdit {
	private static Logger logger = Logger.getLogger(ExcelEdit.class);
	public static final String[] reportPath=Operate.getReportPath();
	
	

	/**
	 * ���ݻ�ȡ�����ݶ��󴴽�excel����
	 * @param strPath excel�ļ�����·��
	 * @return ArrayList ��Ϻ������
	 */	
	
	public ArrayList[] excelParse(String strPath) {
		//System.out.println("------xx-----strPath="+strPath);
		ArrayList[] aryList=null;
		FileInputStream fis=null;
		Workbook book = null;
		try{
			WorkbookSettings ws=new WorkbookSettings();
			ws.setInitialFileSize(512*1024);
			
			fis=new FileInputStream(strPath);
			book =Workbook.getWorkbook(fis,ws);
			
			//��ȡ��sheet������
			int intSheets=book.getNumberOfSheets();
			System.out.println("-------intSheets="+intSheets);
			//sheetҳѭ��
			aryList=new ArrayList[intSheets];
			for(int i=0;i<intSheets;i++){
				aryList[i]=new ArrayList();
				//ȡ����iҳ����
				Sheet sheet = book.getSheet(i);
				int rowcnt = sheet.getRows();
				int intCol=sheet.getColumns();
				//System.out.println("-----rowcnt["+i+"]="+rowcnt);
				//System.out.println("-----intCol="+intCol);
				//ÿҳ�еļ�¼��ѭ�������е�һ��Ϊ����������Ҫ��ȡ
				for(int j=1;j<rowcnt;j++){
					String[] strCol=new String[intCol];
					//��ѭ��
					for(int k=0;k<intCol;k++){
						//ȡ��j��k��
						String temp = sheet.getCell(k, j).getContents();
						//System.out.println("--"+i+"--"+j+"----temp"+k+"="+temp);
						strCol[k]=temp==null?"":temp.trim();
					}
					aryList[i].add(strCol);
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				fis.close();
			}catch(IOException ie){
				ie.printStackTrace();
			}
			
		}
		return aryList;
	}
	
	/*
	public ArrayList jxlReadExcel(int sheetid, int column, int fromrowno,String strPath) {
		ArrayList aryList=new ArrayList();
		FileInputStream fis=null;
		Workbook book = null;

		try{
			WorkbookSettings ws=new WorkbookSettings();
			ws.setInitialFileSize(256*1024);
			
			fis=new FileInputStream(strPath);
			book =Workbook.getWorkbook(fis,ws);
		
			//��ȡ sheet ����
			Sheet sheet = book.getSheet(sheetid);
			String result="";
			
			//��ȡ��sheet������
			int rowcnt = sheet.getRows();
			for (int i = 0; i < rowcnt - fromrowno; i++) {
				  Cell cell = sheet.getCell(column, i + fromrowno);
				  result = cell.getContents();
				  result = result.replace('\n', ' ');
				  result = result.trim();
				  // modified by xt  System.out.println("-------"+i+"-----result="+result);
				  if (!result.equals("")) {
					aryList.add(result);
				  }
			}
		
		}catch (IOException ex) {
		 // modified by xt	System.out.println("Read Excel file failed!");
		 // modified by xt	System.out.println("File name: " + strPath);
			System.exit(0);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				fis.close();
			}catch(Exception e){}
			try{
				book.close();
			}catch(Exception e){}
		}
		return aryList;

	}*/

		
	public static void main(String[] args) {
		/*
		ExcelEdit ej=new ExcelEdit();
		//ej.jxlReadExcel(0,0,12,"D:/share/���߽ӻ���.xls");
		
		ArrayList al=ej.excelParse("D:/��Ŀ�ĵ�/sony/newSIS/qiuyan_report/temp2.xls");
		HashMap hs=new HashMap();
		hs.put("�ӻ���",al);
		ej.createFile(hs);
		
		for(int i=0;i<al.size();i++){
			String[] temp=(String[])al.get(i);
			for(int j=0;j<temp.length;j++){
				System.out.println("--"+i+"--"+j+"----temp="+temp[j]);
			}
		}
		*/
	}

}
