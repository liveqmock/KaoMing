package com.dne.sie.common.dbo;

import java.sql.*;
import com.dne.sie.util.hibernate.*;
import org.apache.log4j.Logger;
import java.util.ArrayList;
import java.io.*;
import com.dne.sie.common.tools.LayOut;

/**
 * <b>��������:����hibernate2.1�޷�ʵ���������º�����ɾ���Ȳ�����
 * 	  ��Ҫ�ƹ�Hibernate API��ֱ��ͨ��JDBC API��ִ����ЩSQL���
 * 	  �ṩҵ���߼��������ݿ�֮������ݷ��ʽӿڣ�
 * 	  ��װ���ݿ����ӳصľ���ʵ�֣����ݴ����SQL��䷵��ResultSet��ArrayList���϶���,
 *    ��ɾ�ķ���int�ͣ���������(ɾ��,�޸�)����int[]���û�����������Ӷ���Ĺرպ����š�</b>
 * <br>
 * @author	xt
 * @version 1.1.5.6
 */
public class DBOperation {
	private static Logger logger = Logger.getLogger(DBOperation.class);
	
	private static final int MAXLISTLEN=10000;

	
	/**
	 * ���ܣ�����ѯ���������ݵĽ������ArrayList���󷵻�,���н�����е�ÿһ�ж�Ӧһ��String[]����
	 * @param String sql���
	 * @return ArrayList ����ArrayList����Ľ����
	 */
	public static ArrayList select(String strSQL) throws Exception {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		ArrayList vtDataRow = new ArrayList();
		HbConn hbConn=new HbConn();
		try {
//			strSQL="select * from("+strSQL+") where limit "+MAXLISTLEN;
			conn = hbConn.getSession().connection();
			pstmt = conn.prepareStatement(strSQL);
			rs = pstmt.executeQuery();
			ResultSetMetaData metadata = rs.getMetaData();
			int intColumns = metadata.getColumnCount();
			String[] rowData;
			String strTemp = "";
			while (rs.next()) {
				rowData = new String[intColumns];
				for (int i = 0; i < intColumns; i++) {
					strTemp = rs.getString(i + 1);
					if (strTemp == null) strTemp = "";
					rowData[i] = strTemp;
				}
				vtDataRow.add(rowData);
			}
			
		}catch (Exception e) {
			logger.error("ִ��select������" + strSQL + "��ʧ�ܣ�");
			throw e;
		} finally {
			try{
				try{
					if (rs != null) rs.close();
				}catch(Exception e1){e1.printStackTrace();}
				try{
					if (pstmt != null) pstmt.close();
				}catch(Exception e1){e1.printStackTrace();}
				
			}catch(Exception e2){
				e2.printStackTrace();
			}finally{
				hbConn.closeSession();
			}
		}
		return vtDataRow;
	}
	

	
	/**
	 * ��ȡΨһ���������ѯֵ��
	 * ����ѯ���ֻ��һ��һ��ʱ�ô˷����Ϸ��㡣
	 * @param String sql���
	 * @return String ���ز�ѯ���
	 */
	public static String selectOne(String strSql) throws Exception {
		String strRet="";
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		ArrayList vtDataRow = new ArrayList();
		HbConn hbConn=new HbConn();
		
		try{
			conn = hbConn.getSession().connection();
			pstmt = conn.prepareStatement(strSql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				strRet = rs.getString(1);
			}
		}catch(Exception e){
			throw e;
		}finally {
			try{
				try{
					if (rs != null) rs.close();
				}catch(Exception e1){e1.printStackTrace();}
				try{
					if (pstmt != null) pstmt.close();
				}catch(Exception e1){e1.printStackTrace();}
				
			}catch(Exception e2){
				e2.printStackTrace();
			}finally{
				hbConn.closeSession();
			}
		}

		return strRet;
	}
	

	
	/**
	 * ��Ҫ�����Ƕ�������ִ����ɾ�Ĳ���
	 * @param String sql���
	 * @return int  ִ�н������������ʾ����ʧ��;����ɹ�);
	 */
	public static int execute(String strSQL) throws Exception{
		int intReturn = -1;
		HbConn hbConn=new HbConn();
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			conn = hbConn.getSessionWithTransaction().connection();
			pstmt = conn.prepareStatement(strSQL);

			intReturn = pstmt.executeUpdate();
			hbConn.commit();
		}catch (Exception e) {
			logger.error("ִ��execute������" + strSQL + "��ʧ�ܣ�");
			hbConn.rollback();
			throw e;
		} finally {
			try{
				if (pstmt != null) {
					pstmt.close();
				}
			}catch(Exception e2){
				e2.printStackTrace();
			}finally{
				hbConn.closeSession();
			}
		}
		return intReturn;
	}
	

	
	/**
	 * ��������������
	 * @param batchSql String[]
	 * @return int[]
	 */
	private static void executeBatch(ArrayList batchSql) throws Exception{
		
		HbConn hbConn=new HbConn();
		Connection conn = null;
		ResultSet rs = null;
		Statement stmt = null;
		try {
			conn = hbConn.getSessionWithTransaction().connection();
			stmt = conn.createStatement();
			for(int i=0;i<batchSql.size();i++){
				//System.out.println("-----------batchSql["+i+"]="+batchSql[i]);
				stmt.addBatch((String)batchSql.get(i));
			}
			stmt.executeBatch();
			hbConn.commit();
		}catch (Exception e) {
			logger.error("executeBatch����ʧ�ܣ�");
			hbConn.rollback();
			throw e;
		} finally {
			if (stmt != null) {
				stmt.close();
			}
			hbConn.closeSession();
		}
	}
	

	public static void main(String args[]){
		try{
			//DBOperation.reportExecuteDDL("alter index PK_USER_ID rebuild");
			String[] testDDL={"analyze index PK_TS_ZONE compute statistics",
							  "analyze index PK_TS_WORKFLOW_CONFIG compute statistics",
							  "analyze index PK_MODEL_WARNING compute statistics"};
			
			//DBOperation.executeBatch(testDDL);
		}catch(Exception e){
			e.printStackTrace();
		}
		

	}
	

		
		

}
