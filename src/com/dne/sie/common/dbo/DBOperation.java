package com.dne.sie.common.dbo;

import java.sql.*;
import com.dne.sie.util.hibernate.*;
import org.apache.log4j.Logger;
import java.util.ArrayList;
import java.io.*;
import com.dne.sie.common.tools.LayOut;

/**
 * <b>功能描述:由于hibernate2.1无法实现批量更新和批量删除等操作，
 * 	  需要绕过Hibernate API，直接通过JDBC API来执行这些SQL语句
 * 	  提供业务逻辑层与数据库之间的数据访问接口，
 * 	  封装数据库连接池的具体实现，根据传入的SQL语句返回ResultSet或ArrayList集合对象,
 *    增删改返回int型，批量操作(删除,修改)返回int[]，用户无需关心连接对象的关闭和弃放。</b>
 * <br>
 * @author	xt
 * @version 1.1.5.6
 */
public class DBOperation {
	private static Logger logger = Logger.getLogger(DBOperation.class);
	
	private static final int MAXLISTLEN=10000;

	
	/**
	 * 功能：将查询生产库数据的结果集用ArrayList对象返回,集中结果集中的每一行对应一个String[]对象
	 * @param String sql语句
	 * @return ArrayList 返回ArrayList对象的结果集
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
			logger.error("执行select方法（" + strSQL + "）失败！");
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
	 * 获取唯一的生产库查询值。
	 * 当查询结果只有一行一列时用此方法较方便。
	 * @param String sql语句
	 * @return String 返回查询结果
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
	 * 主要作用是对生产库执行增删改操作
	 * @param String sql语句
	 * @return int  执行结果（－１：表示操作失败;否则成功);
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
			logger.error("执行execute方法（" + strSQL + "）失败！");
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
	 * 生产库批量操作
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
			logger.error("executeBatch（）失败！");
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
