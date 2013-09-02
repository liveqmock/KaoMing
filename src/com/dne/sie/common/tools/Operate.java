package com.dne.sie.common.tools;


import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import org.apache.log4j.Logger;

import com.dne.sie.common.exception.ComException;

/**
 * <p>Title:ͨ�ò����� </p>
 * <p>Description:�ۺϸ���ģ���ͨ�ò�������ϵͳʱ��,����ת��ȡ� </p>
 * <p>Copyright: Copyright (c) 2005.10.11</p>
 * <p>Company: dne</p>
 * @author xt
 */

public class Operate {
	private static Logger logger = Logger.getLogger(Operate.class);
	private static String filePath=null;
	private static int[] logData=null;
	private static String[] reportPath=null;
	public static HashMap actionMap=new HashMap();
	public static HashMap userMap=new HashMap();
	
   /**
	* ��ȡjavaϵͳʱ��
	* @return ��ʽ����:2005-10-26 15:51:11
	*/
	public static String getDate(){
		Date date = new Date(); 
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDate=sdf.format(date);
		return strDate;
	}
	
	public static String getFeeDate(Date feeDate){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMM");
		String strDate=sdf.format(feeDate);
		return strDate;
	}

	public static String getDate2(){
		Date date = new Date(); 
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		String strDate=sdf.format(date);
		return strDate;
	}
	public static String getNowDate(String format){
		Date date = new Date(); 
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		String strDate=sdf.format(date);
		return strDate;
	}
	public static String trimDate(Date date){
		if(date==null) return "";
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String strDate=sdf.format(date);
		return strDate;
	}
	public static String trimDate(java.sql.Date date){
		if(date==null) return "";
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String strDate=sdf.format(date);
		return strDate;
	}
	
	public static String[] getDayOfWeek(){
		String[] days=new String[2];
		try{
			Date date = new Date(); 
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			
			days[0]=sdf.format(Operate.seekDate(-7));
			days[1]=sdf.format(date);
		}catch(Exception e){
			e.printStackTrace();
		}
		return days;

	}
	
	public static String[] getDayOfMonth(){
		String[] days=new String[2];
		try{
			
			Calendar a=Calendar.getInstance(); 
			int year = a.get(Calendar.YEAR); 
			int month = a.get(Calendar.MONTH)+1; 
			//int day = a.get(Calendar.DATE); 
			
			a.set(Calendar.DATE, 1);//����������Ϊ���µ�һ�� 
			a.roll(Calendar.DATE, -7);//���ڻع�7�죬Ҳ�������һ�� 
			int MaxDate=a.get(Calendar.DATE); 
			
			days[0]=year+"-"+month+"-1";
			days[1]=year+"-"+month+"-"+MaxDate;
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return days;

	}
	/**
	 * ��ȡָ��ʱ��͵��������������������ڵ���֮ǰ
	 * @param ָ��������
	 * @return ����
	 */
	public static int getDateCompare(Date date){
		int diffDays=(int)((new Date().getTime()-date.getTime())/(1000*3600*24));
		
		return diffDays;
	}
	public static int getDateCompare(Date date1,Date date2){
		int diffDays=(int)((date1.getTime()-date2.getTime())/(1000*3600*24));
		
		return diffDays;
	}
	
	
	/**
	 * ����������ַ���,���ظ�ʽ��������
	 * @param format �������ַ���
	 * @return ��ʽ��������
	 * @throws Exception
	 */
	public static String getDate(String format) throws Exception{
		Date date = new Date(); 
		SimpleDateFormat sdf = null;
		try{
			sdf = new SimpleDateFormat(format);
		}catch(NullPointerException e){
			logger.debug("NullPointerException in Operate.getDate(String format)");
			throw e;
		}catch(IllegalArgumentException e){
			logger.debug("IllegalArgumentException in Operate.getDate(String format)");
			throw e; 	
		}
		String strDate=sdf.format(date);
		return strDate;
	}
   
   /**
	* ��ȡjavaϵͳʱ��
	* @return ��ʽ����:2005�� ʮ�� 26�� ������
	*/
	public static String getWeek(){
		Date date = new Date(); 
		SimpleDateFormat sdf=new SimpleDateFormat("E MMM dd yyyy");
		String strWeek=sdf.format(date);		
		return strWeek;
	}
	

	/**
	 * ��ȡjavaϵͳʱ��,һ���еĵڼ�������
	 * @return 
	 */
	public static String getWeek2(){
		Date date = new Date(); 
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy_w");
		String strWeek=sdf.format(date);		
		return strWeek;
	}

	/**
	 * �ж�num����ǰ������һ�ľ�������
	 * @return 
	 */
	public static Date getWeek3(int num){
		int diff=0;
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("E");
		String strWeek=sdf.format(date);	
		if(strWeek.equals("���ڶ�")||strWeek.equals("Tue")){
			diff=1;
		}else if(strWeek.equals("������")||strWeek.equals("Wed")){
			diff=2;
		}else if(strWeek.equals("������")||strWeek.equals("Thu")){
			diff=3;
		}else if(strWeek.equals("������")||strWeek.equals("Fri")){
			diff=4;
		}else if(strWeek.equals("������")||strWeek.equals("Sat")){
			diff=5;
		}else if(strWeek.equals("������")||strWeek.equals("Sun")){
			diff=6;
		}
		date=seekDate(-7*num-diff);
			
		return date;
	}
	

	/**
	 * ��ȡjavaϵͳʱ��,�����
	 * @return ��ʽ����:yyyy_MM
	 */
	public static String getMonth(){
		String strMonth=null;
		try{
			Date date = new Date(); 
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy_MM");
			strMonth=sdf.format(date);	
		}catch( Exception e){
			e.printStackTrace();
		}
		return	strMonth;
	}
	

	/**
	 * ȡ��ĳ�µĵ����һ�� 
	 * @param year 
	 * @param month 
	 * @return 
	 */ 
	public static Date getLastDayOfMonth(int year, int month) { 
	    Calendar cal = Calendar.getInstance(); 
	    cal.set(Calendar.YEAR, year);// �� 
	    cal.set(Calendar.MONTH, month - 1);// �£���ΪCalendar������Ǵ�0��ʼ������Ҫ��1 
	    cal.set(Calendar.DATE, 1);// �գ���Ϊһ�� 
	    cal.add(Calendar.MONTH, 1);// �·ݼ�һ���õ��¸��µ�һ�� 
	    cal.add(Calendar.DATE, -1);// ��һ���¼�һΪ�������һ�� 
	    return cal.getTime();// �����ĩ�Ǽ��� 
	} 
	

	/**
	 * ȡ��ĳ�µĵĵ�һ�� 
	 * @param date
	 * @return 
	 */ 
	public static Date getFirstDayOfMonth(String str) {
		Date d=null;
		try{
		    Calendar cal = Calendar.getInstance(); 
		    if(str != null){
				if(str.split("-").length==2){
					str+="-01";
				}
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			    cal.setTime(sdf.parse(str));
		    }else{
		    	Date date = new Date();
		    	date.setHours(0);
				date.setMinutes(0);
				date.setSeconds(0);
		    	cal.setTime(date);
		    }
		    cal.set(Calendar.DATE, 1);// �գ���Ϊһ�� 
		    d=cal.getTime();
		}catch( Exception e){
			e.printStackTrace();
		}
	    return d;
	} 
	
	/**
	 * ȡ��ĳ�µĵ����һ�� 
	 * @param date
	 * @return 
	 */ 
	public static Date getLastDayOfMonth(String str) {
		Date d=null;
		try{
		    Calendar cal = Calendar.getInstance(); 
		    
			if(str.split("-").length==2){
				str+="-01";
			}
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			
		    cal.setTime(sdf.parse(str));
		    cal.set(Calendar.DATE, 1);// �գ���Ϊһ�� 
		    cal.add(Calendar.MONTH, 1);// �·ݼ�һ���õ��¸��µ�һ�� 
		    cal.add(Calendar.DATE, -1);// ��һ���¼�һΪ�������һ�� 
		    d=cal.getTime();
		}catch( Exception e){
			e.printStackTrace();
		}
	    return d;
	} 
	

	/**
	 * ȡ��ָ���µ��ϸ��·� 
	 * @param yyyyMM
	 * @return 
	 */ 
	public static String getPriorMonth(String strMonth) throws Exception{
		
		if(strMonth==null) strMonth= getNowDate("yyyyMM");
		
		Calendar cal = Calendar.getInstance(); 
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMM");
		cal.setTime(sdf.parse(strMonth));
		cal.add(Calendar.MONTH, -1);
		Date d=cal.getTime();
		String strDate=sdf.format(d);
		
		return strDate;
	}
	
	/**
	 * ����������
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public static int getSpacingDay(Date beginDate,Date endDate) throws Exception {
		Long day=new Long((endDate.getTime()-beginDate.getTime())/(24*60*60*1000) + 1);
		return day.intValue();
	}
	
	/** 
	 * ���ݳ������ڼ������� 
	 *  
	 * @param birthDay 
	 * @return δ�����ڷ���0 
	 * @throws Exception 
	 */  
	public static int getAge(Date birthDay) throws Exception {  
	  
	    Calendar cal = Calendar.getInstance();  
	  
	    if (cal.before(birthDay)) {  
	        return 0;  
	    }  
	  
	    int yearNow = cal.get(Calendar.YEAR);  
	    int monthNow = cal.get(Calendar.MONTH);  
	    int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);  
	    cal.setTime(birthDay);  
	  
	    int yearBirth = cal.get(Calendar.YEAR);  
	    int monthBirth = cal.get(Calendar.MONTH);  
	    int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);  
	  
	    int age = yearNow - yearBirth;  
	  
	    if (monthNow <= monthBirth) {  
	        if (monthNow == monthBirth) {  
	            if (dayOfMonthNow < dayOfMonthBirth) {  
	                age--;  
	            }  
	        } else {  
	            age--;  
	        }  
	    }  
	  
	    return age;  
	}  
	  
	/** 
	 * ���ݳ������ڼ������� 
	 *  
	 * @param strBirthDay 
	 *            �ַ��������� 
	 * @param format 
	 *            ���ڸ�ʽ 
	 * @return δ�����ڷ���0 
	 * @throws Exception 
	 */  
	public static int getAge(String strBirthDay, String format)  
	        throws Exception {  
	    DateFormat df = new SimpleDateFormat(format);  
	    Date birthDay = df.parse(strBirthDay);  
	    return getAge(birthDay);  
	}  
	
	
   /**
	* ��ȡʱ��Σ����Բ������ظ�������
	* @return
	*/
	public static String getSectTime(){
		Calendar mytime = Calendar.getInstance();
		String strTime = Long.toString(mytime.getTimeInMillis());
		return strTime;
	}
   /**
 	* ת�������ַ�
 	* @param strChinese
 	* @return
 	*/
	public String parseChinese(String strChinese){
		try{
			return new String(strChinese.trim().getBytes("8859_1"),"GBK");
		}catch( Exception e){
			return "";
        }
	}

	/**
	* �жϵ�ǰ�ַ����Ƿ�������,�ж϶���ַ���getChars()
	* @param strChar �ַ���
	* @return true���ǣ�false����
	*/
	public boolean isChineseChar(String strChar) {
		int intDoubleByteStart = 19968; //�����ַ���ʼ��λ��19968��
		//int intDoubleByteEnd; //�����ַ�������λ�ã�
		int intPlace=0;
		int count=strChar.length();
		//var intPlace=charCodeAt.charAt(0);//javascriptд��
		for(int i=0;i<count;i++){
	  		intPlace=strChar.charAt(i);//strChar�ĵ�i���ַ���λ�á�
	  		if (intPlace >= intDoubleByteStart) {
	    		return false;//�����ģ�����Ҫת��
	      	}
	    }
		return true;
	} //end isChineseChar

	/**
	 * Excel����ר��
	* �жϵ�ǰ�ַ�������ʾ���ȣ����ĳ���Ϊ2����д��ĸҲΪ2
	* �ж϶���ַ���getChars()
	* @param strChar �ַ���
	* @return true���ǣ�false����
	*/
	public static int getStringLength(String strChar) {
		int len = 0;
		if (strChar != null) {
			int intDoubleByteStart = 19968; //�����ַ���ʼ��λ��19968��
			int intDoubleByteEnd; //�����ַ�������λ�ã�
			int intPlace = 0;
			int count = strChar.length();
			//var intPlace=charCodeAt.charAt(0);//javascriptд��
			for (int i = 0; i < count; i++) {
				intPlace = strChar.charAt(i); //strChar�ĵ�i���ַ���λ�á�
				if (intPlace >= intDoubleByteStart || (intPlace<=90 && intPlace>=65)) {
					len += 2; //����+2,��д��ĸ+2
				} else {
					len++; //����+1
				}
			}
		}
		return len;
	} //end 
      
	/**
	* ��String���͵�����ת��ΪDate����
	* @param temp ����:2005-10-12
	* @return Date
	*/
	public static Date toDate(String temp){
		Date date = null; 
		try{
			if(temp!=null&&temp.indexOf('-')!=-1&&temp.indexOf('-')!=temp.lastIndexOf('-')){
				SimpleDateFormat sdf;
				if(temp.indexOf(':')!=-1){
					sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				}else{
					sdf=new SimpleDateFormat("yyyy-MM-dd");
				}
				date=sdf.parse(temp);
			}else if(temp!=null&&temp.indexOf('/')!=-1&&temp.indexOf('/')!=temp.lastIndexOf('/')){
				
				SimpleDateFormat sdf;
				if(temp.indexOf(':')!=-1){
					sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				}else{
					sdf=new SimpleDateFormat("yyyy/MM/dd");
				}
				date=sdf.parse(temp);
			}else{
//				date=new Date();
			}
			
		}catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return date;
	}
	
	public static java.sql.Date toSqlDate(String temp) throws Exception{
		Date date = toDate(temp); 
		return new java.sql.Date(date.getTime());
	}
	/**
	* ��String���͵�����ת��ΪDate����
	* @param temp ����:2005-10-12
	* @return Date
	*/
	public static Date getNextDate(String temp){
		Date date = null; 
		try{
			if(temp != null){
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				date = sdf.parse(temp);
				date = Operate.addDate(date,1);
				date.setHours(0);
				date.setMinutes(0);
				date.setSeconds(0);
			}
		}catch (Exception e) {
			//logger.error("-----temp="+temp);
			//logger.error(e);
			e.printStackTrace();
		}
		return date;
	}
	

	/**
	 * ��ǰ�������Ӷ�����
	 * @param dayNum ��������
	 * @return Date
	 */
	public static Date seekDate(int dayNum){
		Date date = new Date(); 
		try{
			
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			String nowDay=sdf.format(date);
			date = sdf.parse(nowDay);
			date = Operate.addDate(date,dayNum);
			
			date.setHours(0);
			date.setMinutes(0);
			date.setSeconds(0);
			
		}catch (Exception e) {
			//logger.error("-----temp="+temp);
			//logger.error(e);
			e.printStackTrace();
		}
		return date;
	}
	
	public static Date addDate(Date aDate,int amount){
		Date result = new Date(aDate.getTime() + (long)(amount*86400000));
		return result;
	}
	/**
	* ��String���͵�����ת��ΪDate����
	* @param temp ����:2005-10-12
	* @return Date
	*/
	public static String formatDate(java.util.Date temp){
		if(temp==null) return null;
		String date = null; 
		try{
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(temp != null)date=sdf.format(temp);
		}catch (Exception e) {
			logger.error(e);
		}
		return date;
	}
	/**
	* ��String���͵�����ת��ΪDate����
	* @param temp ����:2005-10-12
	* @return Date
	*/
	public static String formatYMDDate(java.util.Date temp){
		if(temp==null) return null;
		String date = null; 
		try{
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			if(temp != null)date=sdf.format(temp);
		}catch (Exception e) {
			logger.error(e);
		}
		return date;
	}
	
	/**
	* ��ȡjava.sql.Date���͵�ʱ��
	* @param year ��
	* @param month ��
	* @param day ��
	* @return Date
	*/
	public static java.sql.Date toSqlDate(int year, int month, int day){
		java.sql.Date date = null; 
		try{
			date = java.sql.Date.valueOf(year+"-"+month+"-"+day);
		}catch (Exception e) {
			logger.error(e);
		}
		return date;	
	}
	
	/**
	* ��ȡjava.sql.Date���͵ĵ�ǰʱ��
	* @return Date
	*/
	public static java.sql.Date toSqlDate(){
		java.sql.Date date = null; 
		try{
			Calendar mytime = Calendar.getInstance();
			date = new java.sql.Date(mytime.getTimeInMillis());
			
		}catch (Exception e) {
			logger.error(e);
		}
		return date;	
	}
	


   /**
	* ��ѧ��������ת��
	* @param strNum ��ѧ�������ı�ʾ
	* @return ���ֵ
	*/
	public static String numberTransfer(String strNum){
		String strResult=strNum;
		try{
			if(isNumeric(strNum)||(strNum.indexOf(".")==1&&strNum.indexOf("E")!=-1)){
				String zeroChar="";
				char[] temp=strNum.toCharArray();
				for(int i=0;i<temp.length;i++){
					if(temp[i]=='0'){
						zeroChar+=temp[i];
					}else{
						break;
					}
				}
				
				DecimalFormat df = (DecimalFormat) NumberFormat.getPercentInstance();
				df.applyPattern("#####0");
				//df.applyPattern("0.#####");
				double strCell = Double.parseDouble(strNum);
				strResult = df.format(strCell);
				if(!"".equals(zeroChar)){
					strResult=zeroChar+strResult;
				}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return strResult;

	}
	

	
	/**
	 * ��ʽ��Ϊ������
	 * @param strFloat �ַ���
	 * @return
	 */
	public static String formatFloat(String strFloat) {
		if(strFloat == null || strFloat.equals("")){
			return "";
		}else{	
			java.text.DecimalFormat df = new java.text.DecimalFormat("###0.00");
			double doubleDBPrice = Double.parseDouble(strFloat.trim());
			return df.format(doubleDBPrice);
		}
	}
	/**
	 * ��ʽ��Ϊ�۸���ʾ�ĸ�ʽ
	 * @param strFloat
	 * @return
	 */
	public static String formatPrice(String strFloat) {
		if(strFloat == null || strFloat.equals("")){
			return "";
		}else{	
			java.text.DecimalFormat df = new java.text.DecimalFormat("#,##0.00");
			double doubleDBPrice = Double.parseDouble(strFloat.trim());
			return df.format(doubleDBPrice);
		}
	}
	
   /**
	* �õ�properties�����ļ�ֵ��ϵͳ·��
 	* @param name Ҫ�õ�����������
 	* @return �õ����õ�����ֵ
 	*/
	public static String getSysPath(){
		try{
			if(filePath==null){
				filePath = initGet("system_root")+System.getProperty("file.separator");
				//logger.info("-----------------filePath:"+filePath);
				filePath=filePath.replace('\\','/');
			}
			//��Ŀ�ľ���·��
//			filePath = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
//			if(filePath.startsWith("/")) filePath = filePath.substring(1);
//			if(filePath.indexOf("KaoMing")!=-1){
//				filePath = filePath.substring(0,filePath.indexOf("KaoMing")+7)+"/";
//			}
			System.out.println("---filePath="+filePath);
		}catch (Exception e) {
			System.err.println("-----filePath="+filePath);
			e.printStackTrace();
		}
		return filePath;
		
	}
	

	/**
	 * ȡ�������ļ��е���Ϣ
	 * @return
	 */
	public static int[] getLogData(){
		
		try{
			if(logData==null){
				logData=new int[2];
				logData[0]=Integer.parseInt(initGet("grantAll"));
				logData[1]=Integer.parseInt(initGet("grantSD"));
				logger.info("-------logData[0]="+logData[0]+"    logData[1]="+logData[1]);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return logData;
	}
	
	/**
	 * ȡ�ñ��������ļ��е���Ϣ
	 * @return
	 */
	public static String[] getReportPath(){
		
		try{
//			if(reportPath==null){
//				reportPath=new String[3];
//				reportPath[0] = initGet("writePath");
//				reportPath[1] = initGet("downLoadPath");
//				reportPath[2] = initGet("TravelExpenseName");
//				//logger.info("-------reportPath[0]="+reportPath[0]+"    reportPath[1]="+reportPath[1]);
//			}
			
			if(reportPath==null){
				reportPath=new String[4];
				reportPath[0] = Operate.getSysPath()+"affix/reports/";
				reportPath[1] = initGet("downLoadPath");
				reportPath[2] = initGet("TravelExpenseName");
				reportPath[3] = initGet("travelAllowance");
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
		return reportPath;
	}

   /**
	* ��ʼ�������ļ�ApplicationResources.properties
	* @param con Ҫ�õ�����������
	* @return �õ����õ�����ֵ
	*/
	public static String initGet(String con) throws Exception{
		String str="";
		InputStream is = null;
		try{
			is = Operate.class.getResourceAsStream("/SystemPath.properties");
			//System.out.println("--------is="+is);
			Properties dbProps = new Properties();
			dbProps.load(new InputStreamReader(is,"GBK"));
			str=dbProps.getProperty(con);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(is!=null) is.close();
		}
		return str;
	}
	
   /**
	* ɾ��Ӳ���ϵ��ļ�
	* @param filePath Ҫɾ���ļ��������ַ������
	* @return �Ƿ�ɾ���ɹ�
	*/
	public static boolean fileDelete(String filePath[]){
		boolean bolDel=false;
		
		try{			
			for(int i=0;i<filePath.length;i++){
				
				String fileTemp=filePath[i];
				if(fileTemp!=null&&!fileTemp.equals("")){
					File myDelFile=new File(fileTemp);
					bolDel=myDelFile.delete();
					if(bolDel==false){
						throw new ComException("ɾ���ļ�"+fileTemp+"ʧ��");
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return bolDel;

	}
		

	
	/**
	 * ɾ��Ӳ���ϵ�Ŀ¼,��������Ŀ¼���ļ�
	 * @param delFolder Ҫɾ�����ļ�Ŀ¼
	 * @param num �ݹ����
	 * @return Ŀ¼�Ƿ���ɾ�� 
	 */
	public static boolean folderDelete(File delFolder,int num) throws Exception{ 
		//System.out.println("---"+num+"---"+delFolder.getName());
		//Ŀ¼�Ƿ���ɾ�� 
		boolean hasDeleted = true; 
		//�õ����ļ����µ������ļ��к��ļ����� 
		File[] allFiles = delFolder.listFiles(); 
		num++;
		for (int i = 0; i < allFiles.length; i++) { 
			  //Ϊtrueʱ���� 
			if (hasDeleted) { 
		 if (allFiles[i].isDirectory()) { 
			//�ݹ��������10�Σ��׳��쳣
			 if(num<10){
			 	//���Ϊ�ļ���,��ݹ����ɾ���ļ��еķ��� 
				hasDeleted = folderDelete(allFiles[i],num); 
			 }else{
				throw new Exception("~~~~~~~deleteFolder  num="+num);
			}
		 } else if (allFiles[i].isFile()) { 
			 try {//ɾ���ļ� 
				  if (!allFiles[i].delete()) { 
					//ɾ��ʧ��,����false 
				  	hasDeleted = false; 
				  } 
			  } catch (Exception e) { 
							 //�쳣,����false 
					hasDeleted = false; 
			  } 
		 } 
			  } else { 
				   //Ϊfalse,����ѭ�� 
		  break; 
			  } 
		 } 
		 if (hasDeleted) { 
				//���ļ�����Ϊ���ļ���,ɾ���� 
				delFolder.delete(); 
		 } 
		 return hasDeleted; 
	} 

	/**
	 * ������ʼ�ͽ����������
	 * @param beginDate ��ʼ����
	 * @param endDate ��������
	 * @return
	 */
	public static int calculateDiffDays(java.util.Date beginDate,java.util.Date endDate){
		if(beginDate ==null || endDate == null){
			return 0;
		}else{
			long bMillSeconds = beginDate.getTime();
			long eMillSeconds = endDate.getTime();
			long temp = eMillSeconds-bMillSeconds;
			double t1 = temp/1000/60/60/24;
			return (new Double(t1)).intValue();
			//return t1>=1?(new Double(t1)).intValue():(new Double(t1)).intValue()+1;
		}
	}

	public static int calculateDiffHours(java.util.Date beginDate,java.util.Date endDate){
		long bMillSeconds = beginDate.getTime();
		long eMillSeconds = endDate.getTime();
		long temp = eMillSeconds-bMillSeconds;
		double t1 = temp/1000/60/60;
		//return (new Double(t1)).intValue();
		return t1>=1?(new Double(t1)).intValue():(new Double(t1)).intValue()+1; // 061106 sunhj on PTM 2nd No.68
	}
	
	
	/**
	* ���ű���ͬһ��list����ʾ����ȡ���Է�ҳ��ѯ������;
	* @param count1 ���ű�ļ�¼��
	* @param count2 ���ű�ļ�¼��
	* @param fromPage ��ҳ��Χ(rownum<=toPage and rownum>=fromPage)
	* @param toPage ��ҳ��Χ(rownum<=toPage and rownum>=fromPage);
	* @return  intRet[0],intRet[1] - ��1��fromPage��toPage,
	* 			intRet[2],intRet[3] - ��2��fromPage��toPage;
	*/
	public static int[] getSplitPages(int count1,int count2,int fromPage,int toPage){
		int intRet[] = new int[4];
		
		try{
			if(count1==0){
				intRet[0]=0;
				intRet[1]=0;
				intRet[2]=fromPage;
				intRet[3]=toPage;
			}else if(count2==0){
				intRet[0]=fromPage;
				intRet[1]=toPage;
				intRet[2]=0;
				intRet[3]=0;
			}else if(toPage<=count1){//��1���Ӽ��ͱ�2�Ŀռ�
				intRet[0]=fromPage;
				intRet[1]=toPage;
				intRet[2]=0;
				intRet[3]=0;
			}else if(fromPage<=count1&&toPage>count1){//��1���Ӽ��ͱ�2���Ӽ�
				intRet[0]=fromPage;
				intRet[1]=count1;
				intRet[2]=1;
				intRet[3]=toPage-count1;
			}else if(fromPage>count1){//��1�Ŀռ��ͱ�2���Ӽ�
				intRet[0]=0;
				intRet[1]=0;
				intRet[2]=fromPage-count1;
				intRet[3]=toPage-count1;
			}else{
				intRet=null;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return intRet;
	}
	
	
	/**
	* ��Է�װ��String[]��ArrayList��ʽ���ݣ�����String[]�е�ĳ���ֶ�����
	* 	����order by ��java��ʽ
	* @param initList - ԭʼArrayList��
	* @param suffix - String[]�еĴ������ֶε�λ�ã�
	* @return ������ArrayList
	*/
	public static ArrayList getOrderList(ArrayList initList,int suffix){
		
		try{
			for(int i=1;i<initList.size();i++){
				String[] temp1=(String[])initList.get(i);
				String strOrder=temp1[suffix];
				//System.out.println("strOrder"+i+"="+strOrder);
				for(int j=0;j<i+1;j++){
					String[] temp2=(String[])initList.get(j);
					//����������strOrderС�ڵ�ǰԪ��
					//System.out.println(strOrder+" "+i+j+"  "+temp2[suffix]);
					if(strOrder.compareTo(temp2[suffix])<0){
						initList.remove(i);
						initList.add(j,temp1);
						break;
					}
					
				}
				
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return initList;
	}
	
	/**
	 * ��������,�������еĵ�i��Ԫ�أ���Ϊ��ǰ���i - 1���Ѿ�����ã�Ȼ�������뵽ǰ���i - 1��Ԫ����
	 */
	public static void sort(Comparable[] obj){
		int size = 1;
		while (size < obj.length){   
			insert(obj, size++, obj[size - 1]);
		}
	}
	/**
	 *���Ѿ�����õ������в���һ��Ԫ�أ�ʹ������������Ȼ����
	 *@param obj �Ѿ�����õ�����
	 *@param size �Ѿ�����õ�����Ĵ�С
	 *@param c �������Ԫ��
	 */
	private static void insert(Comparable[] obj, int size, Comparable c){
		for (int i = 0 ;i < size ;i++ ){
			if (c.compareTo(obj[i]) < 0){
				//System.out.println(obj[i]);
				//����������Ԫ��С�ڵ�ǰԪ�أ���ѵ�ǰԪ�غ����Ԫ�����κ���һλ
				for (int j = size ;j > i ;j-- ){
					obj[j] = obj[j - 1];
				}
				obj[i] = c;
				break;
			}
		}
	}
	
	
	/**
		* ��Է�װ��String[]��ArrayList��ʽ���ݣ�����String[]�е�ĳ���ֶ���distinct����
		* @param initList - ԭʼArrayList��
		* @param suffix - String[]�еĴ�distinct�ֶε�λ�ã�
		* @return distinct���ArrayList
		*/
	public static ArrayList getDistinctList(ArrayList initList,int suffix){
		ArrayList distList = new ArrayList();
		
		try{
			int count=initList.size();
			for(int i=0;i<count;i++){
				String[] temp1=(String[])initList.get(i);
				String strOrder=temp1[suffix];
				if(i==0) distList.add(strOrder);
	
				for(int j=i+1;j<count;j++){
					String[] temp2=(String[])initList.get(j);
					if(!temp2[suffix].equals(strOrder)&&!distList.contains(temp2[suffix])){
						distList.add(temp2[suffix]);
						
					}
		
				}
	
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return distList;
		
	}
	
	/**
	 * ��ArrayListתΪ���ŷָ����ַ���
	 * @param al �ַ�������
	 * @return ���ŷָ����ַ���
	 */
	public static String arrayListToString(List al){
		String orgs = "";
		if( null != al && al.size()>0 ){			
			for(int i=0;i<al.size();i++){
				orgs = orgs + ","+String.valueOf(al.get(i));
			}	
			if(orgs.length()>0) orgs=orgs.substring(1);
		}
		return orgs;
	}

	public static String setToString(Set hs){
		List al=new ArrayList(hs);
		
		return arrayListToString(al);
	}
	/**
	 * ��ArrayתΪ���ŷָ����ַ���
	 * @param al �ַ�������
	 * @return ���ŷָ����ַ���
	 */
	public static String arrayToString(Object[] al){
		String orgs = "";
		if( null != al && al.length>0 ){			
			for(int i=0;i<al.length;i++){
				orgs = orgs + ","+String.valueOf(al[i]);
			}	
			orgs=orgs.substring(1);
		}
		return orgs;
	}
	
		/**
		 * ��ArrayListתΪ���ŷָ����ַ���
		 * ���ǵ�oracle��in��䲻�ܳ���1000 (ORA-01795),���ַ���ÿ800����Ϊһ�Σ���Ϊ���鷵��
		 * @param al �ַ�������
		 * @return 
		 */
		public String[] arrayListToString2(ArrayList al){
			String orgs[] = null;
			if( null != al && al.size()>0 ){	
				int segment=800;					//ÿ�γ���
				int alSize=al.size();				//List�ܳ���
				if(alSize>segment){
					int quotient=alSize/segment;	
					int residue=alSize%segment;
					if(residue!=0) quotient++;		//�ֳ��Ķ���
					String strTemp[]=new String[quotient];
					for(int i=0;i<quotient;i++){
						if(i!=quotient-1){
							List tempList=al.subList(i*segment,(i+1)*segment);
							strTemp[i]=arrayListToString(tempList);
						}else{
							List tempList=al.subList(segment*i,alSize);		//���һ��
							strTemp[i]=arrayListToString(tempList);
						}
					}
					orgs=strTemp;
				}else{
					String strTemp[]={arrayListToString(al)};
					orgs=strTemp;
				}
				
				
			}
			return orgs;
		}
		

	/**
	 * ������תΪ���ŷָ����ַ���
	 * ���ǵ�oracle��in��䲻�ܳ���1000 (ORA-01795),���ַ���ÿ1000����Ϊһ�Σ���Ϊ���鷵��
	 * @param strTemp �ַ�������
	 * @return
	 */
	public String[] arrayToString2(String[] strTemp){
		String orgs[] = null;
		ArrayList tempList=new ArrayList();
		for(int i=0;i<strTemp.length;i++){
			tempList.add(strTemp[i]);
		}
		orgs=this.arrayListToString2(tempList);
		return orgs;
	}
		
	
		/**
		 * ���ݶ����Ƶ�λ�������Ƚ����������Ƿ�ƥ��
		 * ����Ȩ��У�飬�ж�ĳȨ�޴����Ƿ�������Χ��
		 * @param data1
		 * @param data2
		 * @return ����true��ʾȨ��������Χ��
		 */
		public static boolean bitwiseChk(int data1,int data2){
			boolean booRet=false;
			try{
				int result=data1&data2;
				if(result!=0)  booRet=true;
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
			
			return booRet;
		}
		
		
	/**
	 * ��orgCode��ֵ��������ת��
	 * @param orgCode
	 * @return 
	 */
	public static int bitwiseTansfer(int orgCode){
		int intRet=0;
		try{
			switch(orgCode){
				case 1:
					intRet=2;break;
				case 2:
					intRet=4;break;
				case 3:
					intRet=8;break;
				case 6:
					intRet=16;break;
			}
		
		}catch(Exception e){
			e.printStackTrace();
		}
	
	
		return intRet;
	}
	
	/**
	 * �ж�һ���ַ����������Ƿ�����ƶ����ַ���
	 * @param strArray �ַ�������
	 * @param strValue ָ�����ַ���
	 * @return true ��ʾ���� false ��ʾ������
	 */
	public static boolean arrayContains(String[] strArray, String strValue){
		boolean booRet=false;
		for(int i=0;i<strArray.length;i++){
			if(strArray[i].equals(strValue)){
			booRet=true;
			break;
			}
		}
		return booRet;
	}
	
	/**
	 * �Ƚ��������飬�ֱ�ȡ��������Ⱦ�������Ԫ�غ��ٵ�Ԫ��
	 * @param oldArray һ������
	 * @param newArray ��һ������
	 * @return ��������
	 */
	public static ArrayList[] arrayCompare(String[] oldArray,String[] newArray){
		ArrayList[] objRet=new ArrayList[2];
		ArrayList moreArray=new ArrayList();
		ArrayList lessArray=new ArrayList();
		try{
			for(int i=0;i<newArray.length;i++){
				if(!arrayContains(oldArray,newArray[i])){
					moreArray.add(newArray[i]);
				}
			}
			for(int i=0;i<oldArray.length;i++){
				if(!arrayContains(newArray,oldArray[i])){
					lessArray.add(oldArray[i]);
				}
			}
			objRet[0]=moreArray;
			objRet[1]=lessArray;
	
		}catch(Exception e){
			e.printStackTrace();
		}
	
	
		return objRet;
	}
	

	/**
	 * ���ظ���������ĳ����Set�е�����λ��
	 * @param intData ָ������
	 * @param dataSet ����
	 * @return λ��
	 */
	public static int compositorPlace(int intData,List dataSet){
		int place=0;
		try{
			Collections.sort(dataSet);
			int i=0;
			Iterator it=dataSet.iterator();
			while(it.hasNext()){
				int temp=((Integer)it.next()).intValue();
				if(intData<=temp){
					place=i;
					break;
				}
				i++;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return place;
	}
	
		/**
		  * support Numeric format:<br>
		  * "33" "+33" "033.30" "-.33" ".33" " 33." " 000.000 "
		  * @param str String
		  * @return boolean
		  */
		public static boolean isNumeric(String str) {
			int begin = 0;
			boolean once = true;
			if (str == null || str.trim().equals("")) {
				return false;
			}
			str = str.trim();
			if (str.startsWith("+") || str.startsWith("-")) {
				if (str.length() == 1) {
					// "+" "-"
					return false;
				}
				begin = 1;
			}
			for (int i = begin; i < str.length(); i++) {
				if (!Character.isDigit(str.charAt(i))) {
					if (str.charAt(i) == '.' && once) {
						// '.' can only once
						once = false;
					}
					else {
						return false;
					}
				}
			}
			if (str.length() == (begin + 1) && !once) {
				// "." "+." "-."
				return false;
			}
			return true;
		}

		/**
		  * support Integer format:<br>
		  * "33" "003300" "+33" " -0000 "
		  * @param str String
		  * @return boolean
		  */
		public static boolean isInteger(String str) {
			int begin = 0;
			if (str == null || str.trim().equals("")) {
				return false;
			}
			str = str.trim();
			if (str.startsWith("+") || str.startsWith("-")) {
				if (str.length() == 1) {
					// "+" "-"
					return false;
				}
				begin = 1;
			}
			for (int i = begin; i < str.length(); i++) {
				if (!Character.isDigit(str.charAt(i))) {
					return false;
				}
			}
			return true;
		}

		/**
		 * ԭʼ������ʽΪ201x2,202x2,203x3
		 * ��Ҫ...where CONCAT(sale_detail_id,',',version) in('5,2','6,2') ��sql��ʽ 	--mysql
		 * ...where (t.id,t.version) in ((201,2),(202,2),(203,3))   --oracle 
		 * @param initData
		 * @return
		 */
		public static String toVersionData(String initData){
			String versionData="";
			try{
				versionData=initData.replaceAll(",","','");
				versionData="'"+versionData.replaceAll(DicInit.SPLIT1+"",",")+"'";
			
			}catch(Exception e){
				e.printStackTrace();
			}
			return versionData;
		}
	/**
	 * ԭʼ������ʽΪ201x2,202x2,203x3
	 * 	--oracle 
	 * ��Ҫ...where (t.id,t.version) in ((201,2),(202,2),(203,3))��sql��ʽ	
	 * (saf.adjustId=4530 and saf.version=1)or(saf.adjustId=4530 and saf.version=2)
	 * @param initData
	 * @return
	 */
	public static String toVersionData(String id,String version,String initData){
		String versionData="";
		try{
			versionData=DicInit.SPLIT2+initData.replaceAll(",",") or "+DicInit.SPLIT2+"")+")";
			//System.out.println("--111---versionData="+versionData);
			versionData=versionData.replaceAll(DicInit.SPLIT1+""," and "+version+"=");
			//System.out.println("---222--versionData="+versionData);
			versionData=versionData.replaceAll(DicInit.SPLIT2+"","("+id+"=");

		
		}catch(Exception e){
			e.printStackTrace();
		}
		return versionData;
	}

	/**
	 * ԭʼ������ʽΪaa1x2,bb2x2,bb33x3
	 * ��Ҫ...where (t.id,t.version) in (('aa1',2),('bb2',2),('bb3',3))��sql��ʽ
	 * (saf.adjustId='4530' and saf.version=1)or(saf.adjustId='4530' and saf.version=2)
	 * @param initData
	 * @return
	 */
	public static String toVersionData2(String id,String version,String initData){
		String versionData="";
		try{
			versionData=DicInit.SPLIT2+initData.replaceAll(",",") or "+DicInit.SPLIT2+"")+")";
			//System.out.println("--111---versionData="+versionData);
			versionData=versionData.replaceAll(DicInit.SPLIT1+"","' and "+version+"=");
			//System.out.println("---222--versionData="+versionData);
			versionData=versionData.replaceAll(DicInit.SPLIT2+"","("+id+"='");

		
		}catch(Exception e){
			e.printStackTrace();
		}
		return versionData;
	}
	

	/**
	 * ��ԭ���ݸ�ʽ
	 * ԭʼ������ʽΪaa1x2,bb2x2,bb33x3
	 * ��ԭΪaa1,bb2,bb33
	 * @param initData
	 * @return
	 */
	public static String toBackData(String initData){
		String backData="";
		try{
			String temp[]=initData.split(",");
			for(int i=0;i<temp.length;i++){
				String temp1=temp[i].substring(0,temp[i].indexOf(DicInit.SPLIT1));
				backData+=","+temp1;
			}
			if(!backData.equals("")){
				backData=backData.substring(1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return backData;
	}
	
	/**
	  * support Integer format:<br>
	  * "33" "003300" 
	  * @param str String
	  * @return boolean
	  */
	public static boolean isPositiveInteger(String str) {
		
		if (str == null || str.trim().equals("")) {
			return false;
		}
		str = str.trim();
		if (str.startsWith("+") || str.startsWith("-")) {
			return false;
		}
		if(str.endsWith(".0")){
			str=str.substring(0,str.lastIndexOf(".0"));
		}
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	public static String htmlInjectionFilter(String str)  {
		String strRet=str;
		try{
			if(strRet!=null){
				strRet=strRet.replaceAll("<", "&lt;");
				strRet=strRet.replaceAll(">", "&gt;");
				strRet=strRet.replaceAll("'", "&#039;");
				strRet=strRet.replaceAll("\"", "&quot;");
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return strRet;
		
	}

	public static String fuzzyQuery(String str)  {
		String strRet=null;
		try{
			if(str!=null&&str.trim().length()>0){
				strRet=str.trim();
				if(strRet.substring(0,1).equals("*")){
					strRet="%"+strRet.substring(1);
				}
				if(strRet.substring(strRet.length()-1).equals("*")){
					strRet=strRet.substring(0,strRet.length()-1)+"%";
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return strRet;
		
	}
	
	/**
	 * ��������ȡ��ЧС��λ
	 * @param d double Ҫȡ��ЧС��λ������
	 * @param scale int ��ЧС��λ
	 * @return double ȡ����ЧС��λ������
	 */
	public static double roundD(double d, int scale) {
		long temp = 1;
		for (int i = scale; i > 0; i--) {
			temp *= 10;
		}
		d *= temp;
		long dl = Math.round(d);
		return (double) (dl) / temp;
	}
	public static float roundF(float d, int scale) {
		long temp = 1;
		for (int i = scale; i > 0; i--) {
			temp *= 10;
		}
		d *= temp;
		long dl = Math.round(d);
		return (float) (dl) / temp;
	}
	
	public static String toFix(float d, int scale) throws Exception{
		String temp=d+"";
		String t1=temp.substring(temp.indexOf(".")+1);
		if(t1.length()<scale){
			for(int i=0;i<scale-t1.length();i++){
				temp+="0";
			}
		}else if(t1.length()>scale){
			temp=roundF(d,scale)+"";
		}
		t1=temp.substring(temp.indexOf(".")+1);
		if(t1.length()<scale){
			for(int i=0;i<scale-t1.length();i++){
				temp+="0";
			}
		}
		return temp;
	}
	
	public static String toFixD(Double d, int scale) throws Exception{
		if(d==null) return "";
		else return toFix(d.doubleValue(), scale);
	}
	

	public static String toFix(double d, int scale) throws Exception{
		String temp=d+"";
		
		String t1=temp.substring(temp.indexOf(".")+1);
		
		if(t1.length()<scale){
			for(int i=0;i<scale-t1.length();i++){
				temp+="0";
			}
		}else if(t1.length()>scale){
			temp=roundD(d,scale)+"";
		}
		t1=temp.substring(temp.indexOf(".")+1);
		if(t1.length()<scale){
			for(int i=0;i<scale-t1.length();i++){
				temp+="0";
			}
		}
		
		return temp;
	}
	
	/**
	 * ��ȡ���������Χfrom ~ to
	 * @param from
	 * @param to
	 * @return
	 */
	 public static int getRandomNum(int from,int to){
		 int result=-1;
		 Random rd = new Random();
		 result = rd.nextInt(to - from) + from ;
		 
		 return result;
	 }

	/**
	 * ĩβ׷������ַ�
	 * @param value
	 * @param size
	 * @return
	 */
    public static String supplyString(String value,int size,String flag){
        
    	if(value==null){
    		value = "";
    	}
    	if(value.length()<size){
	    	StringBuffer ret = new StringBuffer(value);
			int len = size - value.length();
	        for(int i=0;i<len;i++){
	        	ret.append(flag);
	        }
	        return ret.toString();
	        
    	}else if(value.length()>size){
    		return value.substring(0,size);
    		
    	}else{
    		return value;
    	}
    	
        
    }
    
    public static String tranCRLF(String value){
    	if(value==null){
    		value = "";
    	}
    	value=value.replaceAll("\r\n", "<br>");
    	
    	return value;
    }
    
    public static List<String> getMonthList(){
    	List<String> monthList = new ArrayList<String>();
    	Date now = new Date();
    	
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
		String strDateNow=sdf.format(now);
		monthList.add(strDateNow);
    	
		Calendar cal = Calendar.getInstance(); 
		int month = cal.get(Calendar.MONTH); 
		   
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DATE, 1);// �գ���Ϊһ�� 
		    
    	for(int i=1;i<=24;i++){
    		cal.add(Calendar.MONTH, -1);// �·ݼ�1���õ��ϸ��µ�һ��
    		monthList.add(sdf.format(cal.getTime()));
    	}
    	
    	return monthList;
    }
    
    public static List<String> getMonthList(Date beginDate){
    	List<String> monthList = new ArrayList<String>();
    	Date now = new Date();
    	
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
		String strDateNow=sdf.format(now);
		monthList.add(strDateNow);
    	
		Calendar cal = Calendar.getInstance(); 
		int month = cal.get(Calendar.MONTH); 
		   
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DATE, 1);// �գ���Ϊһ�� 
		    
    	for(int i=1;i<=24;i++){
    		cal.add(Calendar.MONTH, -1);// �·ݼ�1���õ��ϸ��µ�һ��
    		monthList.add(sdf.format(cal.getTime()));
    		if(cal.getTime().before(beginDate)) break;
    	}
    	
    	return monthList;
    }
    
    /**
     * �˷�
     * @param value1
     * @param value2
     * @return
     * @throws Exception
     */
    public static double normalMultiply(Double value1,Double value2)throws Exception{
		BigDecimal b1 = new BigDecimal(value1.toString());		
		BigDecimal b2 = new BigDecimal(value2.toString());
		return b1.multiply(b2).doubleValue();
	}
	
    /**
     * �ӷ�
     * @param value1
     * @param value2
     * @return
     * @throws Exception
     */
	public static double normalAdd(Double value1,Double value2)throws Exception{
		BigDecimal b1 = new BigDecimal(value1.toString());		
		BigDecimal b2 = new BigDecimal(value2.toString());
		return b1.add(b2).doubleValue();
	}
	/**
	 * ����
	 * @param value1
	 * @param value2
	 * @return
	 * @throws Exception
	 */
	public static double normalSub(Double value1,Double value2)throws Exception{
		System.out.println(value1+"--------"+value2);
		BigDecimal b1 = new BigDecimal(value1.toString());		
		BigDecimal b2 = new BigDecimal(value2.toString());
		System.out.println("xx="+b1.subtract(b2));
		return b1.subtract(b2).doubleValue();
	}
	/**
	 * ����
	 * @param value1
	 * @param value2
	 * @return
	 * @throws Exception
	 */
	public static double normalDivide(Double value1,Double value2)throws Exception{
		BigDecimal b1 = new BigDecimal(value1.toString());		
		BigDecimal b2 = new BigDecimal(value2.toString());
		return b1.divide(b2).doubleValue();
	}
    

    public static void main(String[] args) {
    	try{
    		Double d1 = 88811.2;
    		Double d2 =88800.1;
    		System.out.println(normalSub(d1,d2));
    		
    	}catch(Exception e){
			e.printStackTrace();
		}
	}
		
		
}