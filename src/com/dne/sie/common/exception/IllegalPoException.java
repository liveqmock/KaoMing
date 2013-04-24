/*
 * 创建日期 2007-3-22
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package com.dne.sie.common.exception;

/**
 * @author chenlin
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class IllegalPoException extends Exception {
	private ComMessage message;//使用关键字来索引消息的内容，并能使用属性配置文件装载消息内容。
	private Throwable cause;//引起异常的真正原因
    private String  poNo; //异常的Po号
	private String msgStr;//异常信息

	/** 构造函数
	 */
	public IllegalPoException()
	{
		super();
	}

	/** 构造函数<br>
	 * 在异常中加入一条字符串消息。
	 * @param msgStr 异常信息
	 */
	public IllegalPoException(String msgStr)
	{
		super(msgStr);
		this.msgStr = msgStr;
	}

	/**
	 * 构造函数<br>
	 * 在异常中加入错误号和字符串消息。<br>
	 * 建议使用XMLMessages对象来传递消息，例如：<br>
	 *        XMLMessages messages = new XMLMessages();<br>
	 *        throw new ComException(messages.getErrorCode("fileNotFound"),messages.getErrorText("fileNotFound"));
	 * @param errorCode 错误号
	 * @param msgStr 异常信息
	 * @see XMLMessages.java
	 */
	public IllegalPoException (String errorCode,String msgStr){
		super(msgStr);
		this.msgStr = errorCode+":"+msgStr;
	}

	/** 构造函数<br>
	 * 在异常中加入引起这个异常的原因。
	 * 这个原因是在catch语句中的到的excetption。
	 * 这里用Exception的父类Throwable作为参数的类型。
	 * @param cause 引起异常的真正原因
	 */
   public IllegalPoException(Throwable cause) {
	   super(cause.getLocalizedMessage());
	   this.cause = cause;
   }

   /** 构造函数<br>
	 * 在异常中加入一条字符串消息，并且将引起这个异常的原因加入。<br>
	 * 如果需要带错误号，可以使用XMLMessages的get方法传入msgStr，例如：<br>
	 *        XMLMessages messages = new XMLMessages();<br>
	 *        throw new ComException(messages.get("fileNotFound"),ex);
	 * @param msgStr 异常信息
	 * @param cause 引起异常的真正原因
	 * @see XMLMessages.java
	 */
	public IllegalPoException(String msgStr, Throwable cause)
	{
		 super(msgStr);
		 this.msgStr = msgStr;
		 this.cause = cause;
	}

	/** 构造函数<br>
	 * 在异常中加入CESMessage对象。
	 * 这个对象使用关键字来索引消息的内容，并能使用属性配置文件装载消息内容。
	 * 详细信息请参阅CESMessage文档。
	 * @deprecated replaced by ComException (String errorCode,String msgStr)
	 * @param message 异常消息信息
	 */
	public IllegalPoException(ComMessage message)
	{
		 this.message = message;
	}

	/** 构造函数
	 * 在异常中加入ComMessage对象，并且将引起这个异常的原因加入。
	 * @param message 异常消息信息
	 * @param cause 引起异常的真正原因
	 * @deprecated replaced by ComException (String msgStr，Throwable cause)
	 */
	public IllegalPoException(ComMessage message,Throwable cause)
	{
		 this.message = message;
		 this.cause = cause;
	}


	/** 得到异常的原因
	 * @return java.lang.Throwable
	 */
	public Throwable getCause()
	{
	 return cause;
	}


	/** 得到ComMessage对象
	 * @return ces.dvp.exception.ComMessage
	 */
	public ComMessage getComMessage()
	{
	 return message;
	}


	/** 返回所有异常信息
	 * 这里将本对象装载的所有对象都返回出来，分为四类信息。
	 * SUPER INFO 返回父类的异常信息；
	 * MESSAGE STRING INFO 返回字符串消息的信息；
	 * ComMessage OBJECT INFO 返回ComMessage对象信息；
	 * CAUSE INFO 返回异常引起的原因。
	 * @return String
	 */
	public String toString()
	{
	 StringBuffer buf = new StringBuffer();
	 buf.append("异常名称：");
	 buf.append(super.getClass().getName());
	 buf.append("\n");
	 if(msgStr != null){
		 buf.append("异常信息:");
		 buf.append(msgStr);
		 buf.append("\n");
	 }
	 if(message != null){
		buf.append("异常信息（ComMessage）:");
		buf.append(message.getText());
		buf.append("\n");
	 }
	 if(cause != null){
		buf.append("异常原因:");
		buf.append(cause.toString());
		buf.append("\n");
	 }
	 return buf.toString();
	}
	/**
	 * @return
	 */
	public String getPoNo() {
		return poNo;
	}

	/**
	 * @param string
	 */
	public void setPoNo(String string) {
		poNo = string;
	}

}


