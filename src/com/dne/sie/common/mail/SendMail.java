package com.dne.sie.common.mail;


import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Date;
import javax.activation.*;
//import java.io.*;


public class SendMail {

	private MimeMessage mimeMsg; //MIME�ʼ�����
	
	private Session session; //�ʼ��Ự����
	private Properties props; //ϵͳ����
	//private boolean needAuth = false; //smtp�Ƿ���Ҫ��֤
	
	private String username = ""; //smtp��֤�û���������
	private String password = "";
	
	private Multipart mp; //Multipart����,�ʼ�����,����,���������ݾ���ӵ����к�������MimeMessage����




	//public SendMail() {
	//setSmtpHost(getConfig.mailHost);//���û��ָ���ʼ�������,�ʹ�getConfig���л�ȡ
	//createMimeMessage();
	//}



	/**
	* @param hostName String
	*/
	public void setSmtpHost(String hostName) {
		//System.out.println("����ϵͳ���ԣ�mail.smtp.host = "+hostName);
		if(props == null)props = System.getProperties(); //���ϵͳ���Զ���
		
		props.put("mail.smtp.host",hostName); //����SMTP����
		createMimeMessage();
	}


	/**
	* @return boolean
	*/
	public boolean createMimeMessage(){
		try{
		 // modified by xt	System.out.println("׼����ȡ�ʼ��Ự����");
			session = Session.getDefaultInstance(props,null); //����ʼ��Ự����
		}catch(Exception e){
			System.err.println("��ȡ�ʼ��Ự����ʱ��������"+e);
			return false;
		}

	 //System.out.println("׼������MIME�ʼ�����");
		try{
			mimeMsg = new MimeMessage(session); //����MIME�ʼ�����
			mp = new MimeMultipart();
			return true;
		}
		catch(Exception e){
			System.err.println("����MIME�ʼ�����ʧ�ܣ�"+e);
			return false;
		}
	}



	/**
	* @param need boolean
	*/
	public void setNeedAuth(boolean need) {
	 // modified by xt	System.out.println("����smtp�����֤��mail.smtp.auth = "+need);
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
	 // modified by xt	System.out.println("�����ʼ����⣡");
		try{
			mimeMsg.setSubject(mailSubject);
			return true;
		}
		catch(Exception e) {
			System.err.println("�����ʼ����ⷢ������");
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
			System.err.println("�����ʼ�����ʱ��������"+e);
		return false;
		}
	}


	/**
	* @param name String
	* @param pass String
	*/
	public boolean addFileAffix(String filename) {
	
	 // modified by xt	System.out.println("�����ʼ�������"+filename);
		
		try{
			BodyPart bp = new MimeBodyPart();
			FileDataSource fileds = new FileDataSource(filename);
			bp.setDataHandler(new DataHandler(fileds));
			bp.setFileName(fileds.getName());
			
			mp.addBodyPart(bp);
		
			return true;
		}catch(Exception e){
			System.err.println("�����ʼ�������"+filename+"��������"+e);
			return false;
		}
	}



	/**
	* @param name String
	* @param pass String
	*/
	public boolean setFrom(String from) {
	 // modified by xt	System.out.println("���÷����ˣ�");
		try{
			mimeMsg.setFrom(new InternetAddress(from)); //���÷�����
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
			//System.out.println("���ڷ����ʼ�....");
			
			Session mailSession = Session.getInstance(props,null);
			//mailSession.setDebug(true);
			Transport transport = mailSession.getTransport("smtp");
			
			transport.connect((String)props.get("mail.smtp.host"),username,password);
			transport.sendMessage(mimeMsg,mimeMsg.getRecipients(Message.RecipientType.TO));
			//transport.send(mimeMsg);
			
			//System.out.println("�����ʼ��ɹ���");
			transport.close();
			
			return true;
		}catch(Exception e){
			System.err.println("�ʼ�����ʧ�ܣ�");
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
			"<title>�������ʼ�֪ͨ</title></head>"
			+"<span style=\"font-size: 11.0pt; font-family: ����\">����������Ϊ:" 
			+message+"</span></p>"
			+"<span style=\"font-size: 10.0pt; font-family: ����\">���ʼ�Ϊϵͳ�Զ����ͣ�����ת����ظ����ʼ�</span></p>"+
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
