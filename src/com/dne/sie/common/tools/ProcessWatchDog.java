package com.dne.sie.common.tools;

import java.util.Date;

import org.hibernate.exception.JDBCConnectionException;

public class ProcessWatchDog extends Thread {

	private void checkAndDispatch() {
		try{
			DicInit.pageHTML = HTMLSpirit.getHTML(HTMLSpirit.NewsUrl, HTMLSpirit.Encoding);
			System.out.println(new Date() + " pageHTML.size:" +DicInit.pageHTML.size());
		}catch (JDBCConnectionException je) {
			je.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void run() {
		while (true) {
			checkAndDispatch();
			try {
				Thread.currentThread().sleep(DicInit.DEFAULT_DELAY);
			} catch (InterruptedException e) {
				e.printStackTrace();
				return;
			}
			
		}
	}
	
}
