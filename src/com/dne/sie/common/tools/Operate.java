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
 * <p>Title:通用操作类 </p>
 * <p>Description:综合各个模块的通用操作，如系统时间,中文转码等。 </p>
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
	* 获取java系统时间
	* @return 格式样例:2005-10-26 15:51:11
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
			
			a.set(Calendar.DATE, 1);//把日期设置为当月第一天 
			a.roll(Calendar.DATE, -7);//日期回滚7天，也就是最后一天 
			int MaxDate=a.get(Calendar.DATE); 
			
			days[0]=year+"-"+month+"-1";
			days[1]=year+"-"+month+"-"+MaxDate;
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return days;

	}
	/**
	 * 获取指定时间和当天的天数间隔，正数是在当天之前
	 * @param 指定的日期
	 * @return 天数
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
	 * 根据输入的字符串,返回格式化的日期
	 * @param format 给定的字符串
	 * @return 格式化的日期
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
	* 获取java系统时间
	* @return 格式样例:2005年 十月 26日 星期三
	*/
	public static String getWeek(){
		Date date = new Date(); 
		SimpleDateFormat sdf=new SimpleDateFormat("E MMM dd yyyy");
		String strWeek=sdf.format(date);		
		return strWeek;
	}
	

	/**
	 * 获取java系统时间,一年中的第几个星期
	 * @return 
	 */
	public static String getWeek2(){
		Date date = new Date(); 
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy_w");
		String strWeek=sdf.format(date);		
		return strWeek;
	}

	/**
	 * 判断num周以前的星期一的具体日期
	 * @return 
	 */
	public static Date getWeek3(int num){
		int diff=0;
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("E");
		String strWeek=sdf.format(date);	
		if(strWeek.equals("星期二")||strWeek.equals("Tue")){
			diff=1;
		}else if(strWeek.equals("星期三")||strWeek.equals("Wed")){
			diff=2;
		}else if(strWeek.equals("星期四")||strWeek.equals("Thu")){
			diff=3;
		}else if(strWeek.equals("星期五")||strWeek.equals("Fri")){
			diff=4;
		}else if(strWeek.equals("星期六")||strWeek.equals("Sat")){
			diff=5;
		}else if(strWeek.equals("星期日")||strWeek.equals("Sun")){
			diff=6;
		}
		date=seekDate(-7*num-diff);
			
		return date;
	}
	

	/**
	 * 获取java系统时间,年和月
	 * @return 格式样例:yyyy_MM
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
	 * 取得某月的的最后一天 
	 * @param year 
	 * @param month 
	 * @return 
	 */ 
	public static Date getLastDayOfMonth(int year, int month) { 
	    Calendar cal = Calendar.getInstance(); 
	    cal.set(Calendar.YEAR, year);// 年 
	    cal.set(Calendar.MONTH, month - 1);// 月，因为Calendar里的月是从0开始，所以要减1 
	    cal.set(Calendar.DATE, 1);// 日，设为一号 
	    cal.add(Calendar.MONTH, 1);// 月份加一，得到下个月的一号 
	    cal.add(Calendar.DATE, -1);// 下一个月减一为本月最后一天 
	    return cal.getTime();// 获得月末是几号 
	} 
	

	/**
	 * 取得某月的的第一天 
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
		    cal.set(Calendar.DATE, 1);// 日，设为一号 
		    d=cal.getTime();
		}catch( Exception e){
			e.printStackTrace();
		}
	    return d;
	} 
	
	/**
	 * 取得某月的的最后一天 
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
		    cal.set(Calendar.DATE, 1);// 日，设为一号 
		    cal.add(Calendar.MONTH, 1);// 月份加一，得到下个月的一号 
		    cal.add(Calendar.DATE, -1);// 下一个月减一为本月最后一天 
		    d=cal.getTime();
		}catch( Exception e){
			e.printStackTrace();
		}
	    return d;
	} 
	

	/**
	 * 取得指定月的上个月份 
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
	 * 计算间隔天数
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
	 * 根据出生日期计算年龄 
	 *  
	 * @param birthDay 
	 * @return 未来日期返回0 
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
	 * 根据出生日期计算年龄 
	 *  
	 * @param strBirthDay 
	 *            字符串型日期 
	 * @param format 
	 *            日期格式 
	 * @return 未来日期返回0 
	 * @throws Exception 
	 */  
	public static int getAge(String strBirthDay, String format)  
	        throws Exception {  
	    DateFormat df = new SimpleDateFormat(format);  
	    Date birthDay = df.parse(strBirthDay);  
	    return getAge(birthDay);  
	}  
	
	
   /**
	* 获取时间段，用以产生不重复的序列
	* @return
	*/
	public static String getSectTime(){
		Calendar mytime = Calendar.getInstance();
		String strTime = Long.toString(mytime.getTimeInMillis());
		return strTime;
	}
   /**
 	* 转换汉字字符
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
	* 判断当前字符串是否含有中文,判断多个字符用getChars()
	* @param strChar 字符串
	* @return true－是；false－否；
	*/
	public boolean isChineseChar(String strChar) {
		int intDoubleByteStart = 19968; //中文字符开始的位置19968！
		//int intDoubleByteEnd; //中文字符结束的位置！
		int intPlace=0;
		int count=strChar.length();
		//var intPlace=charCodeAt.charAt(0);//javascript写法
		for(int i=0;i<count;i++){
	  		intPlace=strChar.charAt(i);//strChar的第i个字符的位置。
	  		if (intPlace >= intDoubleByteStart) {
	    		return false;//有中文，不需要转换
	      	}
	    }
		return true;
	} //end isChineseChar

	/**
	 * Excel报表专用
	* 判断当前字符串的显示长度，中文长度为2，大写字母也为2
	* 判断多个字符用getChars()
	* @param strChar 字符串
	* @return true－是；false－否；
	*/
	public static int getStringLength(String strChar) {
		int len = 0;
		if (strChar != null) {
			int intDoubleByteStart = 19968; //中文字符开始的位置19968！
			int intDoubleByteEnd; //中文字符结束的位置！
			int intPlace = 0;
			int count = strChar.length();
			//var intPlace=charCodeAt.charAt(0);//javascript写法
			for (int i = 0; i < count; i++) {
				intPlace = strChar.charAt(i); //strChar的第i个字符的位置。
				if (intPlace >= intDoubleByteStart || (intPlace<=90 && intPlace>=65)) {
					len += 2; //中文+2,大写字母+2
				} else {
					len++; //其它+1
				}
			}
		}
		return len;
	} //end 
      
	/**
	* 将String类型的日期转换为Date类型
	* @param temp 样例:2005-10-12
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
	* 将String类型的日期转换为Date类型
	* @param temp 样例:2005-10-12
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
	 * 当前日期增加多少天
	 * @param dayNum 增加天数
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
	* 将String类型的日期转换为Date类型
	* @param temp 样例:2005-10-12
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
	* 将String类型的日期转换为Date类型
	* @param temp 样例:2005-10-12
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
	* 获取java.sql.Date类型的时间
	* @param year 年
	* @param month 月
	* @param day 日
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
	* 获取java.sql.Date类型的当前时间
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
	* 科学计数法的转换
	* @param strNum 科学计数法的表示
	* @return 结果值
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
	 * 格式化为浮点数
	 * @param strFloat 字符串
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
	 * 格式化为价格显示的格式
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
	* 得到properties属性文件值中系统路径
 	* @param name 要得到的属性名称
 	* @return 得到设置的属性值
 	*/
	public static String getSysPath(){
		try{
			if(filePath==null){
				filePath = initGet("system_root")+System.getProperty("file.separator");
				//logger.info("-----------------filePath:"+filePath);
				filePath=filePath.replace('\\','/');
			}
			//项目的绝对路径
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
	 * 取得配置文件中的信息
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
	 * 取得报表配置文件中的信息
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
	* 初始化配置文件ApplicationResources.properties
	* @param con 要得到的属性名称
	* @return 得到设置的属性值
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
	* 删除硬盘上的文件
	* @param filePath 要删除文件的物理地址的数组
	* @return 是否删除成功
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
						throw new ComException("删除文件"+fileTemp+"失败");
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return bolDel;

	}
		

	
	/**
	 * 删除硬盘上的目录,包含的子目录和文件
	 * @param delFolder 要删除的文件目录
	 * @param num 递归次数
	 * @return 目录是否已删除 
	 */
	public static boolean folderDelete(File delFolder,int num) throws Exception{ 
		//System.out.println("---"+num+"---"+delFolder.getName());
		//目录是否已删除 
		boolean hasDeleted = true; 
		//得到该文件夹下的所有文件夹和文件数组 
		File[] allFiles = delFolder.listFiles(); 
		num++;
		for (int i = 0; i < allFiles.length; i++) { 
			  //为true时操作 
			if (hasDeleted) { 
		 if (allFiles[i].isDirectory()) { 
			//递归如果大于10次，抛出异常
			 if(num<10){
			 	//如果为文件夹,则递归调用删除文件夹的方法 
				hasDeleted = folderDelete(allFiles[i],num); 
			 }else{
				throw new Exception("~~~~~~~deleteFolder  num="+num);
			}
		 } else if (allFiles[i].isFile()) { 
			 try {//删除文件 
				  if (!allFiles[i].delete()) { 
					//删除失败,返回false 
				  	hasDeleted = false; 
				  } 
			  } catch (Exception e) { 
							 //异常,返回false 
					hasDeleted = false; 
			  } 
		 } 
			  } else { 
				   //为false,跳出循环 
		  break; 
			  } 
		 } 
		 if (hasDeleted) { 
				//该文件夹已为空文件夹,删除它 
				delFolder.delete(); 
		 } 
		 return hasDeleted; 
	} 

	/**
	 * 计算起始和结束相差天数
	 * @param beginDate 起始日期
	 * @param endDate 结束日期
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
	* 两张表在同一个list中显示，获取各自翻页查询的条数;
	* @param count1 两张表的记录数
	* @param count2 两张表的记录数
	* @param fromPage 翻页范围(rownum<=toPage and rownum>=fromPage)
	* @param toPage 翻页范围(rownum<=toPage and rownum>=fromPage);
	* @return  intRet[0],intRet[1] - 表1的fromPage和toPage,
	* 			intRet[2],intRet[3] - 表2的fromPage和toPage;
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
			}else if(toPage<=count1){//表1的子集和表2的空集
				intRet[0]=fromPage;
				intRet[1]=toPage;
				intRet[2]=0;
				intRet[3]=0;
			}else if(fromPage<=count1&&toPage>count1){//表1的子集和表2的子集
				intRet[0]=fromPage;
				intRet[1]=count1;
				intRet[2]=1;
				intRet[3]=toPage-count1;
			}else if(fromPage>count1){//表1的空集和表2的子集
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
	* 针对封装了String[]的ArrayList形式数据，根据String[]中的某个字段排序
	* 	类似order by 的java形式
	* @param initList - 原始ArrayList；
	* @param suffix - String[]中的待排序字段的位置；
	* @return 排序后的ArrayList
	*/
	public static ArrayList getOrderList(ArrayList initList,int suffix){
		
		try{
			for(int i=1;i<initList.size();i++){
				String[] temp1=(String[])initList.get(i);
				String strOrder=temp1[suffix];
				//System.out.println("strOrder"+i+"="+strOrder);
				for(int j=0;j<i+1;j++){
					String[] temp2=(String[])initList.get(j);
					//如果待插入的strOrder小于当前元素
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
	 * 插入排序,对数组中的第i个元素，认为它前面的i - 1个已经排序好，然后将它插入到前面的i - 1个元素中
	 */
	public static void sort(Comparable[] obj){
		int size = 1;
		while (size < obj.length){   
			insert(obj, size++, obj[size - 1]);
		}
	}
	/**
	 *在已经排序好的数组中插入一个元素，使插入后的数组仍然有序
	 *@param obj 已经排序好的数组
	 *@param size 已经排序好的数组的大小
	 *@param c 待插入的元素
	 */
	private static void insert(Comparable[] obj, int size, Comparable c){
		for (int i = 0 ;i < size ;i++ ){
			if (c.compareTo(obj[i]) < 0){
				//System.out.println(obj[i]);
				//如果待插入的元素小于当前元素，则把当前元素后面的元素依次后移一位
				for (int j = size ;j > i ;j-- ){
					obj[j] = obj[j - 1];
				}
				obj[i] = c;
				break;
			}
		}
	}
	
	
	/**
		* 针对封装了String[]的ArrayList形式数据，根据String[]中的某个字段做distinct操作
		* @param initList - 原始ArrayList；
		* @param suffix - String[]中的待distinct字段的位置；
		* @return distinct后的ArrayList
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
	 * 把ArrayList转为逗号分隔的字符串
	 * @param al 字符串集合
	 * @return 逗号分隔的字符串
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
	 * 把Array转为逗号分隔的字符串
	 * @param al 字符串集合
	 * @return 逗号分隔的字符串
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
		 * 把ArrayList转为逗号分隔的字符串
		 * 考虑到oracle的in语句不能超过1000 (ORA-01795),将字符串每800个分为一段，作为数组返回
		 * @param al 字符串集合
		 * @return 
		 */
		public String[] arrayListToString2(ArrayList al){
			String orgs[] = null;
			if( null != al && al.size()>0 ){	
				int segment=800;					//每段长度
				int alSize=al.size();				//List总长度
				if(alSize>segment){
					int quotient=alSize/segment;	
					int residue=alSize%segment;
					if(residue!=0) quotient++;		//分出的段数
					String strTemp[]=new String[quotient];
					for(int i=0;i<quotient;i++){
						if(i!=quotient-1){
							List tempList=al.subList(i*segment,(i+1)*segment);
							strTemp[i]=arrayListToString(tempList);
						}else{
							List tempList=al.subList(segment*i,alSize);		//最后一段
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
	 * 把数组转为逗号分隔的字符串
	 * 考虑到oracle的in语句不能超过1000 (ORA-01795),将字符串每1000个分为一段，作为数组返回
	 * @param strTemp 字符串数组
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
		 * 根据二进制的位运算来比较两个数字是否匹配
		 * 用于权限校验，判断某权限代码是否在允许范围里
		 * @param data1
		 * @param data2
		 * @return 返回true表示权限在允许范围里
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
	 * 将orgCode的值做二进制转换
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
	 * 判断一个字符串数组中是否包含制定的字符串
	 * @param strArray 字符串数组
	 * @param strValue 指定的字符串
	 * @return true 表示包含 false 表示不包含
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
	 * 比较两个数组，分别取出新数组比旧数组多的元素和少的元素
	 * @param oldArray 一个数组
	 * @param newArray 另一个数组
	 * @return 差异数组
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
	 * 返回给定数字在某数字Set中的排序位置
	 * @param intData 指定数字
	 * @param dataSet 集合
	 * @return 位置
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
		 * 原始数据形式为201x2,202x2,203x3
		 * 需要...where CONCAT(sale_detail_id,',',version) in('5,2','6,2') 的sql形式 	--mysql
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
	 * 原始数据形式为201x2,202x2,203x3
	 * 	--oracle 
	 * 需要...where (t.id,t.version) in ((201,2),(202,2),(203,3))的sql形式	
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
	 * 原始数据形式为aa1x2,bb2x2,bb33x3
	 * 需要...where (t.id,t.version) in (('aa1',2),('bb2',2),('bb3',3))的sql形式
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
	 * 还原数据格式
	 * 原始数据形式为aa1x2,bb2x2,bb33x3
	 * 还原为aa1,bb2,bb33
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
	 * 四舍五入取有效小数位
	 * @param d double 要取有效小数位的数字
	 * @param scale int 有效小数位
	 * @return double 取了有效小数位的数字
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
	 * 获取随机数，范围from ~ to
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
	 * 末尾追加填充字符
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
		cal.set(Calendar.DATE, 1);// 日，设为一号 
		    
    	for(int i=1;i<=24;i++){
    		cal.add(Calendar.MONTH, -1);// 月份减1，得到上个月的一号
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
		cal.set(Calendar.DATE, 1);// 日，设为一号 
		    
    	for(int i=1;i<=24;i++){
    		cal.add(Calendar.MONTH, -1);// 月份减1，得到上个月的一号
    		monthList.add(sdf.format(cal.getTime()));
    		if(cal.getTime().before(beginDate)) break;
    	}
    	
    	return monthList;
    }
    
    /**
     * 乘法
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
     * 加法
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
	 * 减法
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
	 * 除法
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