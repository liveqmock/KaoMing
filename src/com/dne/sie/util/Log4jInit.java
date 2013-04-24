package com.dne.sie.util;

import org.apache.log4j.PropertyConfigurator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  Log4j»’÷æ¿‡
 * @author xt
 * @version 1.1.5.6
 * @see Log4jInit.java <br>
 */
public class Log4jInit extends HttpServlet {
	public void init() {
		String prefix = getServletContext().getRealPath("/");
		String file = getInitParameter("log4j");
		// if the log4j-init-file is not set, then no point in trying
	System.out.println("................log4j start "+prefix+file);
		if (file != null) {
			PropertyConfigurator.configure(prefix + file);
		}
	}
	public void doGet(HttpServletRequest req, HttpServletResponse res) {
	}
}