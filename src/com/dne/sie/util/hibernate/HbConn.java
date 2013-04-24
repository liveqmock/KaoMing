package com.dne.sie.util.hibernate;

import java.sql.Connection;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StaleObjectStateException;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import com.dne.sie.common.exception.ComException;
import com.dne.sie.common.exception.VersionException;
import org.apache.log4j.Logger;


/**
 * Hibernate��session�����͹�����<br>
 * 	������ͨ���ݺͱ������ݵ�session<br>
 * @author xt
 * @version 1.1.5.6
 * @see HbConn.java <br>
 */
public class HbConn {
	private static Logger logger = Logger.getLogger(HbConn.class);
	
	private static SessionFactory sessionFactory1 = HbInitServlet.sessionFactory1;
	private static Configuration hbConf1 = HbInitServlet.hbConf1;
	
	private final ThreadLocal sessionThread1 =new ThreadLocal();
	private final ThreadLocal transactionThread1 = new ThreadLocal();

	//Session session=null ;
	//Transaction transaction=null;

	/**
	 * ��ʼ����ͨ���ݵ�SessionFactory
	 * ��̬���߳�ͬ��
	 */
	public synchronized static void setSessionFactory1(){
		if(sessionFactory1==null){
			try{  
				sessionFactory1 = HbInitServlet.init1();
			}catch(Exception e){
				logger.error(e);
			}
		}
	}
	
	/**
	 * ������ͨ���ݵ�Session������������
	 * @return Session
	 * @throws ComException
	 */
	public Session getSession() throws ComException {
		if(sessionFactory1==null){
			setSessionFactory1();
		}
		Session session = (Session) sessionThread1.get();
		if (session == null||!session.isOpen()) {
			try {
				session = sessionFactory1.openSession();
			} catch (HibernateException hbe) {
				throw new ComException(hbe);
			}
			sessionThread1.set(session);
		} 
		return session;
	}
	



	/**
	 * ������ͨ���ݵ�Session����������
	 * @return Session
	 * @throws ComException
	 */
	public Session getSessionWithTransaction() throws ComException {
		Session sess = getSession();
		Transaction transaction = (Transaction) transactionThread1.get();
		if (transaction == null) {
			try {
				transaction = sess.beginTransaction();
			} catch (HibernateException hbe) {
				throw new ComException(hbe);
			}
			transactionThread1.set(transaction);
		}
		return sess;
	}
	

	/**
	 * �ر���ͨ���ݵ�Session
	 * @return 
	 * @throws ComException
	 */
	public void closeSession() throws ComException {
		Session session = (Session) sessionThread1.get();
		sessionThread1.set(null);
		transactionThread1.set(null);

		if (session != null) {
			try {
				Connection conn=session.close();
				if (conn!=null){
					try{
						System.err.println("newsis: attention!!!!!!!!!!, connection is still open even after you called session.close, we will close it now");						
						conn.close();
						
					}catch(Exception dbE){
						System.err.println("newsis: failed to close the connection by ourselves, details are ");
						dbE.printStackTrace(System.err);
					}
				}				

			} catch (HibernateException hbe) {
				throw new ComException(hbe);

			}finally{
				session=null;
			}
		}
	}
	

	/**
	 * �ύ��ͨ���ݵ�����
	 * @return boolean �Ƿ�ɹ�
	 * @throws ComException,VersionException
	 */
	public boolean commit() throws ComException,VersionException {
		boolean t = false;
		Session session = (Session) sessionThread1.get();
		Transaction transaction = (Transaction) transactionThread1.get();
		if (session != null) {
			try {
				if (transaction != null) {
					transaction.commit();
					
					t = true;
					
					transaction = null;
					transactionThread1.set(null); 
				} else {
					throw new ComException("newsis: transaction has not been found when commiting!");
				}
			}catch (StaleObjectStateException sose) {
				System.err.println("sose="+sose);
				throw new VersionException("newsis: version message has been changed by other transaction��");
			} catch (Exception e) {
				throw new ComException(e);
			} finally {
				//����ʱ������transaction,����rollback�Ҳ���transaction
				//transaction = null;
				//transactionThread.set(null); 
			}
		} else {
			throw new ComException("session has not been found when commiting!");
		}
		return t;
	}
	
	/**
	 * �ع���ͨ���ݵ�����
	 * @return 
	 * @throws ComException
	 */
	public void rollback() throws ComException {
		Session session = (Session) sessionThread1.get();
		Transaction transaction = (Transaction) transactionThread1.get();
		if (session != null) {
			try {
				if (transaction != null) {
					transaction.rollback();
				} else {
					throw new ComException("newsis: transaction has not been found when commiting!");
				}
			} catch (Exception e) {
				throw new ComException(e);
			} finally {
				transaction = null;
				transactionThread1.set(null);
			}

		} else {
			throw new ComException("newsis: session has not been found when commiting!");
		}

	}
	
	
	
	public static Configuration getConf() {
		return hbConf1;
	}


}

