package com.dne.sie.common.mail;


import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Date;
import javax.activation.*;
//import java.io.*;


public class SendMail {

	private MimeMessage mimeMsg; //MIME邮件对象
	
	private Session session; //邮件会话对象
	private Properties props; //系统属性
	//private boolean needAuth = false; //smtp是否需要认证
	
	private String username = ""; //smtp认证用户名和密码
	private String password = "";
	
	private Multipart mp; //Multipart对象,邮件内容,标题,附件等内容均添加到其中后再生成MimeMessage对象




	//public SendMail() {
	//setSmtpHost(getConfig.mailHost);//如果没有指定邮件服务器,就从getConfig类中获取
	//createMimeMessage();
	//}



	/**
	* @param hostName String
	*/
	public void setSmtpHost(String hostName) {
		//System.out.println("设置系统属性：mail.smtp.host = "+hostName);
		if(props == null)props = System.getProperties(); //获得系统属性对象
		
		props.put("mail.smtp.host",hostName); //设置SMTP主机
		createMimeMessage();
	}


	/**
	* @return boolean
	*/
	public boolean createMimeMessage(){
		try{
		 // modified by xt	System.out.println("准备获取邮件会话对象！");
			session = Session.getDefaultInstance(props,null); //获得邮件会话对象
		}catch(Exception e){
			System.err.println("获取邮件会话对象时发生错误！"+e);
			return false;
		}

	 //System.out.println("准备创建MIME邮件对象！");
		try{
			mimeMsg = new MimeMessage(session); //创建MIME邮件对象
			mp = new MimeMultipart();
			return true;
		}
		catch(Exception e){
			System.err.println("创建MIME邮件对象失败！"+e);
			return false;
		}
	}



	/**
	* @param need boolean
	*/
	public void setNeedAuth(boolean need) {
	 // modified by xt	System.out.println("设置smtp身份认证：mail.smtp.auth = "+need);
		if(props == null)props = System.getProperties();
		
		if(need){
			props.put("mail.smtp.auth","true");
		}else{
			props.put("mail.smtp.auth","false");
		}
	}



	/**
	* @param name String
	* @param pass String
	*/
	public void setNamePass(String name,String pass) {
		username = name;
		password = pass;
	}


	/**
	* @param mailSubject String
	* @return boolean
	*/
	public boolean setSubject(String mailSubject) {
	 // modified by xt	System.out.println("设置邮件主题！");
		try{
			mimeMsg.setSubject(mailSubject);
			return true;
		}
		catch(Exception e) {
			System.err.println("设置邮件主题发生错误！");
			return false;
		}
	}



	/**
	* @param mailBody String
	*/
	public boolean setBody(String mailBody) {
		try{
			BodyPart bp = new MimeBodyPart();
			bp.setContent("<meta http-equiv=Content-Type content=text/html; charset=gb2312>"+mailBody,"text/html;charset=GB2312");
			mp.addBodyPart(bp);
		
			return true;
		}
		catch(Exception e){
			System.err.println("设置邮件正文时发生错误！"+e);
		return false;
		}
	}


	/**
	* @param name String
	* @param pass String
	*/
	public boolean addFileAffix(String filename) {
	
	 // modified by xt	System.out.println("增加邮件附件："+filename);
		
		try{
			BodyPart bp = new MimeBodyPart();
			FileDataSource fileds = new FileDataSource(filename);
			bp.setDataHandler(new DataHandler(fileds));
			bp.setFileName(fileds.getName());
			
			mp.addBodyPart(bp);
		
			return true;
		}catch(Exception e){
			System.err.println("增加邮件附件："+filename+"发生错误！"+e);
			return false;
		}
	}



	/**
	* @param name String
	* @param pass String
	*/
	public boolean setFrom(String from) {
	 // modified by xt	System.out.println("设置发信人！");
		try{
			mimeMsg.setFrom(new InternetAddress(from)); //设置发信人
			return true;
		}
		catch(Exception e){ 
			return false; 
		}
	}


	/**
	* @param name String
	* @param pass String
	*/
	public boolean setTo(String to){
		if(to == null)return false;
	
		try{
			mimeMsg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
			return true;
		}
		catch(Exception e){ 
			return false; 
		}
	
	}


	/**
	* @param name String
	* @param pass String
	*/
	public boolean sendout(){
		try{
			mimeMsg.setContent(mp);
			mimeMsg.saveChanges();
			//System.out.println("正在发送邮件....");
			
			Session mailSession = Session.getInstance(props,null);
			//mailSession.setDebug(true);
			Transport transport = mailSession.getTransport("smtp");
			
			transport.connect((String)props.get("mail.smtp.host"),username,password);
			transport.sendMessage(mimeMsg,mimeMsg.getRecipients(Message.RecipientType.TO));
			//transport.send(mimeMsg);
			
			//System.out.println("发送邮件成功！");
			transport.close();
			
			return true;
		}catch(Exception e){
			System.err.println("邮件发送失败！");
			e.printStackTrace();
			return false;
		}
	}
	
	
	/*
	ServerIP=43.82.54.27
	Subject=NeWSIS application email alert
	From=NeWSIS@sony.com.cn
	username=scnnewsis
	password=qwe123
	*/
	public void sendEmail(String subject,String message,String ip,String mailAddress){
		try{
			Date date=new Date();
			//String strFlag=null;
			
			
			String mailbody = "<html><head>"+
			"<meta http-equiv=Content-Type content=text/html; charset=gb2312>"+
			"<title>新密码邮件通知</title></head>"
			+"<span style=\"font-size: 11.0pt; font-family: 宋体\">您的新密码为:" 
			+message+"</span></p>"
			+"<span style=\"font-size: 10.0pt; font-family: 宋体\">本邮件为系统自动发送，请勿转发或回复此邮件</span></p>"+
			"</body>"+		
			"</html>";
			SendMail themail = new SendMail();
			//themail.setSmtpHost("43.82.54.27");
			themail.setSmtpHost(ip);
			themail.setNeedAuth(true);
			if(themail.setSubject(subject) == false) return;
			if(themail.setBody(mailbody) == false) return;
		
			if(themail.setFrom("NeWSIS@sony.com.cn") == false) return;
			themail.setNamePass("scnnewsis","abcd1234");
			ArrayList emails = new ArrayList();
			
			//emails.add("newsismail@126.com");
//			emails.add("haoshuang@dne.com.cn");
			emails.add(mailAddress);
			for(int i=0;i<emails.size();i++){
				try{
					//System.out.println("------------emails["+i+"]="+emails.get(i));
					themail.setTo((String)emails.get(i));
					themail.sendout();
				}catch(Exception e){
					System.err.println("----err="+(String)emails.get(i));
					e.printStackTrace();
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	


	/**
	* Just do it as this
	*/
	public static void main(String[] args) {
		SendMail sm=new SendMail();
		sm.sendEmail("sub1","test......","10.10.40.5","xuetao2009@gmail.com");
		
	}		
}
