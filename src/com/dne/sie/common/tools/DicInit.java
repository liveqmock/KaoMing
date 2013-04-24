package com.dne.sie.common.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import com.dne.sie.maintenance.form.TsSystemCodeForm;
import com.dne.sie.util.dao.DefaultDao;
import com.dne.sie.util.hibernate.AllDefaultDaoImp;


/**
 * <p>Title:字典表初始化 </p>
 * <p>Description:将表ts_system_code中的数据作静态初始化，在服务器启动的时候加载 </p>
 * <p>Copyright: Copyright (c) 2008.8.2</p>
 * @author xt
 * @version 1.0
 */

public class DicInit extends HttpServlet{
	
	private static Logger logger = Logger.getLogger(DicInit.class);
	
	private DefaultDao dao;
	public static HashMap SYS_CODE_MAP = new HashMap();	// SYSTEM_CODE代码字典表
	public static List<String> pageHTML = null; 
	public final static long DEFAULT_DELAY = 24*3600000;
	
	public static char SPLIT1=0x0001;
	public static char SPLIT2=0x0002;
	public static String SPLIT3="^~^";
	

	public void init()   throws ServletException {
		super.init();
	}
	
	public void destroy() {
		super.destroy();
	}
	
	public DicInit() {
		AllDefaultDaoImp daoImp = new AllDefaultDaoImp();
		dao = daoImp;

		try{
			makeSystemCode();  
			threadInit();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	
	public void makeSystemCode(){
		try{
			if(SYS_CODE_MAP.size()==0){
				HashSet typeSet = new HashSet();
				ArrayList tempList = (ArrayList)dao.list("from TsSystemCodeForm as tsc order by tsc.systemType,tsc.systemId");
				logger.info("------------systemCodeList.size="+tempList.size());
				
				for(int i=0;i<tempList.size();i++){
					String systemType=((TsSystemCodeForm)tempList.get(i)).getSystemType().trim();
					typeSet.add(systemType);
				}
				Iterator itType = typeSet.iterator();
				
				TsSystemCodeForm tsc = new TsSystemCodeForm();
				while(itType.hasNext()){
					String type=(String)itType.next();
					ArrayList showList = new ArrayList();
					//System.out.println("------------type="+type);
					for(int i=0;i<tempList.size();i++){
						tsc=(TsSystemCodeForm)tempList.get(i);
						
						//String systemType=tsc.getSystemType().trim();
						String[] temp = new String[2];
						//System.out.println("------xx------systemType="+systemType);
						if(tsc.getSystemType().equals(type)){
							temp[0]=tsc.getSystemCode();
							temp[1]=tsc.getSystemName();
							//System.out.println("------------temp[1]="+temp[1]);
							showList.add(temp);
						}
						
					}
					SYS_CODE_MAP.put(type,showList);
				}
			}
		}catch( Exception e){
			e.printStackTrace();
		}
	}
	

	/**
	 *  根据system_type和system_code查询system_name
	 * 
	 */
	public static String getSystemName(String type,String code){
		if(code==null||"null".equals(code)) return "";
		
		String sysName=code;
		if(type != null && code != null ){
			try{
				ArrayList alTemp=(ArrayList)SYS_CODE_MAP.get(type);
				if(alTemp != null){
					for(int i=0;i<alTemp.size();i++){
						String[] temp=(String[])alTemp.get(i);
						if(temp[0].equals(code)){ 
							sysName=temp[1];
							break;
						}
					}
				}
			}catch( Exception e){
				e.printStackTrace();
			}
		}

		return sysName;
	}
	
	public void threadInit() throws Exception {
		if(Operate.getSysPath().startsWith("/home")){
		
			ProcessWatchDog dog = new ProcessWatchDog();
			dog.setName("Daemon Server");
			dog.start();
		}
	}
	
	
	
}
