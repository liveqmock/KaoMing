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
	* �ʼ���������֤ 
	*/ 
	public class MailAuthenticator extends Authenticator { 
		private final static String username="scnnewsis"; 
		private final static String password="abcd1234"; 
	
		protected PasswordAuthentication getPasswordAuthentication(){ 
			return new PasswordAuthentication(username,password); 
		} 
	} 
	

	/** 
	* ������ͨ�ʼ� 
	* 
	* @throws Exception 
	*/ 
	public void doSendNormalMail(String Email_Subject,String Email_Body,String Email_Header,
			String Email_FROM,String Email_TO) throws Exception { 
	
		Properties prop = new Properties(); // ��ȡϵͳ���� 
		Authenticator auth = new MailAuthenticator(); // �ʼ���������֤ 
		prop.put("mail.smtp.host", Email_Host); 
		prop.put("mail.smtp.auth", "true"); 
		Session session = Session.getDefaultInstance(prop, auth);// ���öԻ����ʼ�����������ͨѶ 
	
		Message message = new MimeMessage(session); 
		/* 
		* �����ʼ����� 
		*/ 
		try { 
			message.setSubject(Email_Subject + "Normal Email"); // �����ʼ����� 
			message.setContent("Hello", "text/plain"); // �����ʼ���ʽ 
			message.setText(Email_Body); // �����ʼ��� 
			message.setHeader("Header:", Email_Header); // �����ʼ����� 
			message.setSentDate(new Date()); // ���÷���ʱ�� 
			Address address = new InternetAddress(Email_FROM, "KaoMingSH"); // ���÷����˵�ַ 
			message.setFrom(address); 
		
			/* 
			* ���ö�������˵�ַ 
			* 
			* Address address[]={new InternetAddress("","") new 
			* InternetAddress("","")}; message.addFrom(address); 
			*/ 
		
			Address toAddress = new InternetAddress(Email_TO); // ���ý����˵�ַ 
			message.setRecipient(Message.RecipientType.TO, toAddress); 
		
			// ���ö���ռ��˵�ַ 
			// message.addRecipient(Message.RecipientType.TO,new 
			// InternetAddress("zhf_0630@126.com")); 
		
			message.saveChanges(); 
			System.out.println("sendNormalEmail() ��ʼ�����ʼ�����"); 
			Transport.send(message); // �����ʼ� 
			System.out.println("���ͳɹ���"); 
		
		} catch (MessagingException e) { 
			System.err.println("����ʧ�ܣ�"); 
			e.printStackTrace(); 
		} 

	} 
	

	/** 
	* ���ʹ��������ʼ� 
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
			message.setSubject(Email_Subject + "Email With Accatchment");// �����ʼ����� 
			message.setContent("Hello", "text/plain"); // �����ʼ���ʽ 
			message.setText(Email_Body); // �����ʼ��� 
			message.setHeader("Header:", Email_Header); // �����ʼ����� 
			message.setSentDate(new Date()); // ���÷���ʱ�� 
			Address address = new InternetAddress(Email_FROM, "Dave"); // ���÷����˵�ַ 
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
			System.out.println("sendEmailWithHtml() ��ʼ�����ʼ�����"); 
			Transport.send(message); 
			System.out.println("���ͳɹ���"); 
		} catch (MessagingException e) { 
			System.err.println("����ʧ�ܣ�"); 
			e.printStackTrace(); 
		} 

	} 
	
	

	/** 
	* ���ʹ�html���ʼ� 
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
		+ "<a href=\"http://news.sina.com.cn\" target=\"blank\">�������</a>"; 
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
			System.out.println("sendEmailWithHtml() ��ʼ�����ʼ�����"); 
			Transport.send(message); 
			System.out.println("���ͳɹ���"); 
		} catch (MessagingException e) { 
			System.out.println("����ʧ�ܣ�"); 
			e.printStackTrace(); 
		} 
	} 
	*/

	/** 
	* ���Ժ��� 
	* 
	* @param args 
	*/ 
	public static void main(String args[]) { 
		try { 
			SendEmail se=new SendEmail();
			// ������ͨ�ʼ� 
			//se.doSendNormalMail("sub1","test111......","Header:test","NeWSIS@sony.com.cn","xuetao2009@gmail.com"); 
		
			// ���ʹ��������ʼ� 
			se.sendEmailWithAttachment("sub2","test222......","Header:test","NeWSIS@sony.com.cn","xuetao2009@gmail.com","d:/bak/testMail.txt"); 
		
		} catch (Exception e) { 
			e.printStackTrace(); 
		} 
	} 
	
	
}
