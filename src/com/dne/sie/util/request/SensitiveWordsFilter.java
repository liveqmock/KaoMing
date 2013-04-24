package com.dne.sie.util.request;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;


/**
 * This filter is used for defencing script attack and SQL Injection,
 * filtering parameters in request and replacing harmful characters with harmless characters.
 * In this situation, handling parameterMap built in HttpServletRequest object needed,
 * So replace HttpServletRequest with ParameterRequestWrapper because ParamterMap extends
 * org.apache.catalina.util.ParameterMap(extends HashMap) in tomcat and this Map is locked.
 */
public class SensitiveWordsFilter implements Filter {

	Properties prop = new Properties();

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletRequestWrapper prw = new ParameterRequestWrapper(request, prop);
																					
		chain.doFilter(prw, resp); // 替换HttpServletRequest
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		try {
			prop.load(this.getClass().getResourceAsStream(
					"/filterwords.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
