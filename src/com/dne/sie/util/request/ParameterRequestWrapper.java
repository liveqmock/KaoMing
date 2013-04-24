package com.dne.sie.util.request;

import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;


/**
 * Implement HttpServletRequest interface in parent-class, used for replacing original HttpServletRequest object
 */
public class ParameterRequestWrapper extends HttpServletRequestWrapper {

 
	private Properties prop;

	public ParameterRequestWrapper(HttpServletRequest request,Properties prop) {   

	  super(request);   

	  this.prop=prop;   
	  
	}   
	


	/* (non-Javadoc)
	 * @see javax.servlet.ServletRequestWrapper#getParameterValues(java.lang.String)
	 */
	@Override
	public String[] getParameterValues(String name) { 
		String [] pValues = getRequest().getParameterValues(name);
		if(pValues == null){
			return pValues;
		} 
		for (int i = 0; i < pValues.length; i++) {
			pValues[i] = replaceKeyWord(pValues[i]);
		}
		return pValues;
	}   

	/* (non-Javadoc)
	 * @see javax.servlet.ServletRequestWrapper#getParameter(java.lang.String)
	 */
	@Override
	public String getParameter(String name) {   		
		String pValue = getRequest().getParameter(name);
		if(pValue == null || "".equals(pValue)){
			return pValue;
		}
		pValue = replaceKeyWord(pValue);
		return pValue;
	}
	
	/**
	 * Replace keywords
	 * @param oldStr String to be replaced
	 * @return
	 */
	private String replaceKeyWord(String oldStr){
		
		if(oldStr == null){
			return null;
		}
		String newStr = oldStr;
		Enumeration en = prop.propertyNames();
		while (en.hasMoreElements()) {
			String filterword = (String) en.nextElement();
			newStr = newStr.replaceAll(filterword, prop.getProperty(filterword));	
		}
		return newStr;
	}
}
