/*
 * Created on 2005-4-12
 *
 * 用于管理开关hibernate Session
 */
package com.dne.sie.util.hibernate;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.dne.sie.util.hibernate.HbConn;


/**
 * Hibernate过滤器
 * @author xt
 * @version 1.1.5.6
 * @see HbFilter.java <br>
 */
public class HbFilter implements Filter {

	/** 
	 * 初始化
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

	/**
	 * 过滤器实现
	 * @param request
	 * @param response
	 * @param chain
	 * @throws IOException, ServletException
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		try{
			chain.doFilter(request, response);
		}catch(Exception e){
		 // modified by xt	System.out.println(e);
			e.printStackTrace();
		}finally{
			try{
				//HbConn.closeSession();
			}catch(Exception ex){
			 // modified by xt	System.out.println("Error when closing Session...");
				ex.printStackTrace();
			}
		}

	}

	/**
	 * 销毁
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
