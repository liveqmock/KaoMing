package com.dne.sie.common.mail;

import javax.mail.Authenticator; 
import javax.mail.PasswordAuthentication; 
import java.io.UnsupportedEncodingException; 
import java.util.Date; 
import java.util.Properties; 

import javax.activation.DataHandler; 
import javax.activation.DataSource; 
import javax.activation.FileDataSource; 
import javax.mail.Address; 
import javax.mail.Authenticator; 
import javax.mail.BodyPart; 
import javax.mail.Message; 
import javax.mail.MessagingException; 
import javax.mail.Multipart; 
import javax.mail.Session; 
import javax.mail.Transport; 
import javax.mail.internet.InternetAddress; 
import javax.mail.internet.MimeBodyPart; 
import javax.mail.internet.MimeMessage; 
import javax.mail.internet.MimeMultipart; 

public class SendEmail {
	
	private final static String Email_Host="10.10.40.5"; 
	
	/** 
	* 邮件服务器认证 
	*/ 
	public class MailAuthenticator extends Authenticator { 
		private final static String username="scnnewsis"; 
		private final static String password="abcd1234"; 
	
		protected PasswordAuthentication getPasswordAuthentication(){ 
			return new PasswordAuthentication(username,password); 
		} 
	} 
	

	/** 
	* 发送普通邮件 
	* 
	* @throws Exception 
	*/ 
	public void doSendNormalMail(String Email_Subject,String Email_Body,String Email_Header,
			String Email_FROM,String Email_TO) throws Exception { 
	
		Properties prop = new Properties(); // 获取系统环境 
		Authenticator auth = new MailAuthenticator(); // 邮件服务器认证 
		prop.put("mail.smtp.host", Email_Host); 
		prop.put("mail.smtp.auth", "true"); 
		Session session = Session.getDefaultInstance(prop, auth);// 设置对话和邮件服务器进行通讯 
	
		Message message = new MimeMessage(session); 
		/* 
		* 设置邮件对象 
		*/ 
		try { 
			message.setSubject(Email_Subject + "Normal Email"); // 设置邮件主题 
			message.setContent("Hello", "text/plain"); // 设置邮件格式 
			message.setText(Email_Body); // 设置邮件体 
			message.setHeader("Header:", Email_Header); // 设置邮件标题 
			message.setSentDate(new Date()); // 设置发送时间 
			Address address = new InternetAddress(Email_FROM, "KaoMingSH"); // 设置发信人地址 
			message.setFrom(address); 
		
			/* 
			* 设置多个发件人地址 
			* 
			* Address address[]={new InternetAddress("","") new 
			* InternetAddress("","")}; message.addFrom(address); 
			*/ 
		
			Address toAddress = new InternetAddress(Email_TO); // 设置接收人地址 
			message.setRecipient(Message.RecipientType.TO, toAddress); 
		
			// 设置多个收件人地址 
			// message.addRecipient(Message.RecipientType.TO,new 
			// InternetAddress("zhf_0630@126.com")); 
		
			message.saveChanges(); 
			System.out.println("sendNormalEmail() 开始发送邮件……"); 
			Transport.send(message); // 发送邮件 
			System.out.println("发送成功！"); 
		
		} catch (MessagingException e) { 
			System.err.println("发送失败！"); 
			e.printStackTrace(); 
		} 

	} 
	

	/** 
	* 发送带附件的邮件 
	* @throws UnsupportedEncodingException 
	*/ 
	public void sendEmailWithAttachment(String Email_Subject,String Email_Body,String Email_Header,
			String Email_FROM,String Email_TO,String annexPath) throws UnsupportedEncodingException { 
		Properties prop = new Properties(); 
		prop.put("mail.smtp.host", Email_Host); 
		prop.put("mail.smtp.auth", "true"); 
		Authenticator auth = new MailAuthenticator(); 
		Session session = Session.getDefaultInstance(prop, auth); 
		Message message = new MimeMessage(session); 
	
		try { 
			message.setSubject(Email_Subject + "Email With Accatchment");// 设置邮件主题 
			message.setContent("Hello", "text/plain"); // 设置邮件格式 
			message.setText(Email_Body); // 设置邮件体 
			message.setHeader("Header:", Email_Header); // 设置邮件标题 
			message.setSentDate(new Date()); // 设置发送时间 
			Address address = new InternetAddress(Email_FROM, "Dave"); // 设置发信人地址 
			message.setFrom(address); 
		
			BodyPart messageBodyPart = new MimeBodyPart(); 
			messageBodyPart.setText("bodypart"); 
			Multipart multipart = new MimeMultipart(); 
			multipart.addBodyPart(messageBodyPart); 
			messageBodyPart = new MimeBodyPart(); 
			DataSource source = new FileDataSource(annexPath); 
			messageBodyPart.setDataHandler(new DataHandler(source)); 
			messageBodyPart.setFileName(annexPath); 
			multipart.addBodyPart(messageBodyPart);// Put parts in 
			message.setContent(multipart); 
			Address toAddress = new InternetAddress(Email_TO); 
			message.setRecipient(Message.RecipientType.TO, toAddress); 
			message.saveChanges(); 
			System.out.println("sendEmailWithHtml() 开始发送邮件……"); 
			Transport.send(message); 
			System.out.println("发送成功！"); 
		} catch (MessagingException e) { 
			System.err.println("发送失败！"); 
			e.printStackTrace(); 
		} 

	} 
	
	

	/** 
	* 发送带html的邮件 
	* @throws UnsupportedEncodingException 
	*/ 
/*
	public void sendEmailWithHtml(String Email_Subject,String Email_Body,String Email_Header,
			String Email_FROM,String Email_TO) throws UnsupportedEncodingException { 

		Properties prop = new Properties(); 
		Authenticator auth = new MailAuthenticator(); 
		prop.put("mail.smtp.host", Email_Host); 
		prop.put("mail.smtp.auth", "true"); 
		Session session = Session.getDefaultInstance(prop, auth); 
		Message message = new MimeMessage(session); 
	
		String htmlText = "<h1>Hello</h1>" 
		+ "<a href=\"http://news.sina.com.cn\" target=\"blank\">点击连接</a>"; 
		System.out.println(htmlText); 
		try { 
			message.setSubject(Email_Subject + "Email With Html"); 
			message.setContent(htmlText + Email_Body, 
			"text/html;charset=gb2312"); 
			// message.setText(Email.Email_Body); 
			message.setSentDate(new Date()); 
		
			Address address = new InternetAddress(Email_FROM, "Dave"); 
			Address toAddress = new InternetAddress(Email_TO); 
			message.setFrom(address); 
			message.setRecipient(Message.RecipientType.TO, toAddress); 
			message.saveChanges(); 
			System.out.println("sendEmailWithHtml() 开始发送邮件……"); 
			Transport.send(message); 
			System.out.println("发送成功！"); 
		} catch (MessagingException e) { 
			System.out.println("发送失败！"); 
			e.printStackTrace(); 
		} 
	} 
	*/

	/** 
	* 测试函数 
	* 
	* @param args 
	*/ 
	public static void main(String args[]) { 
		try { 
			SendEmail se=new SendEmail();
			// 发送普通邮件 
			//se.doSendNormalMail("sub1","test111......","Header:test","NeWSIS@sony.com.cn","xuetao2009@gmail.com"); 
		
			// 发送带附件的邮件 
			se.sendEmailWithAttachment("sub2","test222......","Header:test","NeWSIS@sony.com.cn","xuetao2009@gmail.com","d:/bak/testMail.txt"); 
		
		} catch (Exception e) { 
			e.printStackTrace(); 
		} 
	} 
	
	
}
