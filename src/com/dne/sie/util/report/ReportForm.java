package com.dne.sie.util.report;

import java.util.Map;

/**
 * 报表的form类
 * @author xt
 * @version 1.1.5.6
 * @see BaseReportQuery.java <br>
 */
public class ReportForm {
	
	private String[] sql;
	private String fileName;
	private Map hsTitles;
	private int sqlCount;
	private int columnCount;
	private String[] query;
	private String[] strTitles;
	

	/**
	 * @return
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @return
	 */
	public String[] getSql() {
		return sql;
	}

	/**
	 * @return
	 */
	public Map getHsTitles() {
		return hsTitles;
	}

	/**
	 * @param string
	 */
	public void setFileName(String string) {
		fileName = string;
	}

	/**
	 * @param strings
	 */
	public void setSql(String[] strings) {
		sql = strings;
	}

	/**
	 * @param map
	 */
	public void setHsTitles(Map map) {
		hsTitles = map;
	}

	/**
	 * @return
	 */
	public int getSqlCount() {
		return sqlCount;
	}

	/**
	 * @param i
	 */
	public void setSqlCount(int i) {
		sqlCount = i;
	}

	/**
	 * @return
	 */
	public String[] getQuery() {
		return query;
	}

	/**
	 * @param strings
	 */
	public void setQuery(String[] strings) {
		query = strings;
	}

	/**
	 * @return
	 */
	public String[] getStrTitles() {
		return strTitles;
	}

	/**
	 * @param strings
	 */
	public void setStrTitles(String[] strings) {
		strTitles = strings;
	}

	/**
	 * @return
	 */
	public int getColumnCount() {
		return columnCount;
	}

	/**
	 * @param i
	 */
	public void setColumnCount(int i) {
		columnCount = i;
	}

}
