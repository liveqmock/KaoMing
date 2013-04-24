package com.dne.sie.util.hibernate;

import java.io.File;
import java.io.IOException;

import com.dne.sie.util.init.*;
import org.hibernate.*;
import org.hibernate.cfg.*;
import com.dne.sie.common.tools.Operate;


/**
 * Hibernate的SessionFactory创建和管理类<br>
 * 	区分普通数据和报表数据的SessionFactory<br>
 * @author xt
 * @version 1.1.5.6
 * @see HbConn.java <br>
 */
public class HbInitServlet {
	//private static final String CONTENT_TYPE = "text/html; charset=GBK";
	//private Context ctx;
	protected static SessionFactory sessionFactory1;
	protected static Configuration hbConf1;

	/**
	 * 根据配置文件加载普通数据SessionFactory
	 * @return SessionFactory
	 */
	public static SessionFactory init1() {
		try {
			//String prefix = Operate.getSysPath();
			//String pathNewsis = prefix.substring(0,prefix.lastIndexOf("WebContent"))+"JavaSource/hibernate.cfg.xml";
		 	//System.out.println("................hibernate pathNewsis="+pathNewsis);
			//File file1 = new File(pathNewsis);
			hbConf1 = new Configuration().configure();
			sessionFactory1 = hbConf1.buildSessionFactory();
		} catch (HibernateException ex) {
			ex.printStackTrace();
		}
		return sessionFactory1;

	}

	
	
	/**
	 * 释放所有SessionFactory
	 */
	public void destroy() {
		if (sessionFactory1 != null) {
			try {
				sessionFactory1.close();
			} catch (HibernateException e) {
				e.printStackTrace();
			}
			sessionFactory1 = null;
		}
		

	}
}

