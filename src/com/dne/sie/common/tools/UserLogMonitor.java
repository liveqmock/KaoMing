/*
 * 创建日期 2007-5-17
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package com.dne.sie.common.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Date;

import java.io.File;

/**
 * @author xt
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class UserLogMonitor extends Thread{
	
	public static HashMap userMap=new HashMap();
	private boolean runFlag = true;
	public static int monitorTime=10;	//分钟
	private LayOut lo=null;
	private String prefix =null;
	//上一次记录的访问量
	private long lastActionCount =0;
	
	public UserLogMonitor(){
		lo=new LayOut();
		prefix = Operate.getSysPath();
	}
	
	public synchronized void run() {
		userLogMonitor();
	}
	

	
	private void userLogMonitor(){
		try{
			int i=0;
			while (runFlag) {
				if(i==10000000) i=0;
				//System.out.println("----i="+i+"  ---monitorTime="+monitorTime);
				if(i%5==0||i%monitorTime==0){
				 // modified by xt	System.out.println("----i="+i+"  ---monitorTime="+monitorTime);
					Date nowTime=new Date();
					Iterator it=userMap.entrySet().iterator();
					StringBuffer sb=new StringBuffer();
				
					//服务器访问总人数
					int allUserCount=userMap.size();
					//当前活动人数
					int activeUserCount=0;
					//总的访问流水数
					long actionCount = 0;
					//当前时间段流水数
					long phaseActionCount=0;
					
					while(it.hasNext()){
						Map.Entry me=(Map.Entry)it.next();
						Long userId=(Long)me.getKey();
						Object[] userVal=(Object[])me.getValue();
					
						if(i%5==0){
							Date lastTime=(Date)userVal[1];
							long diffTime=nowTime.getTime()-lastTime.getTime();
							if(diffTime>5*60*1000&&"1".equals(userVal[3])){
							 // modified by xt	System.out.println("---"+i+"-------me.getKey()="+me.getKey());
								userVal[3]="0";
								userMap.put(me.getKey(),userVal);
							}
						}
						if(i%monitorTime==0){
							sb.append(userId.toString()).append("\t").append(userVal[0].toString()).append("\t")
							.append("\"").append(((Date)userVal[1]).toLocaleString()).append("\"").append("\t").append("\"").append(userVal[2].toString())
							.append("\"").append("\t").append(userVal[3].toString());
							sb.append("\r\n");
							
							if("1".equals(userVal[3])){
								activeUserCount++;
							}
							actionCount+=Long.parseLong(userVal[2].toString());
							
						}
						
					} //end while
				
					if(i%monitorTime==0){
						String strDateTime=Operate.getDate();
						String[] temp=strDateTime.split(" ");
						String strDate="log_"+temp[0];
						String strTime=temp[1].replaceAll(":","");
						//LayOut lo=new LayOut("affix/"+strDate+"/userLog_"+strTime+".txt");
						lo.saveWithoutEscape(sb.toString(),prefix+"affix/"+strDate+"/userLog_"+strTime+".txt");
						
						phaseActionCount=actionCount-lastActionCount;
												
						String filePathName=prefix+"affix/"+strDate+"/statLog.txt";
						File f=new File(filePathName);
						String content=allUserCount+"\t"+activeUserCount+"\t"+phaseActionCount+"\t"+actionCount+"\t"+temp[1];
						lastActionCount=actionCount;
						if(!f.exists()){
							content="服务器访问总人数\t当前活动人数\t当前访问流水数\t总的访问流水数\t时间\r\n"+content;
						}
						lo.writeFile(content,filePathName);
						//System.out.println("----strTime="+strTime);
						//System.err.println("----sb="+sb);
					}
				} //end if
				Thread.sleep(1*60*1000);
				i++;
			}
		
		}catch( Exception e){
			e.printStackTrace();
		}
	}

	
	
	public void setRunFlag(boolean newState) {
		this.runFlag = newState;
	}
	
	

}
