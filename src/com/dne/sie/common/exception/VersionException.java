package com.dne.sie.common.exception;

/*
* �������������ֹ�������İ汾��ͻ����
*/

public class VersionException extends Exception{
	
   private ComMessage message;//ʹ�ùؼ�����������Ϣ�����ݣ�����ʹ�����������ļ�װ����Ϣ���ݡ�
   private Throwable cause;//�����쳣������ԭ��
 //  private String  errorCode; //�����
   private String msgStr;//�쳣��Ϣ

   /** ���캯��
    */
   public VersionException(){
       super();
   }

   /** ���캯��<br>
    * ���쳣�м���һ���ַ�����Ϣ��
    * @param msgStr �쳣��Ϣ
    */
   public VersionException(String msgStr) {
       super(msgStr);
       this.msgStr = msgStr;
   }

   /**
    * ���캯��<br>
    * ���쳣�м������ź��ַ�����Ϣ��<br>
    * ����ʹ��XMLMessages������������Ϣ�����磺<br>
    *        XMLMessages messages = new XMLMessages();<br>
    *        throw new ComException(messages.getErrorCode("fileNotFound"),messages.getErrorText("fileNotFound"));
    * @param errorCode �����
    * @param msgStr �쳣��Ϣ
    * @see XMLMessages.java
    */
   public VersionException (String errorCode,String msgStr){
       super(msgStr);
       this.msgStr = errorCode+":"+msgStr;
   }

   /** ���캯��<br>
    * ���쳣�м�����������쳣��ԭ��
    * ���ԭ������catch����еĵ���excetption��
    * ������Exception�ĸ���Throwable��Ϊ���������͡�
    * @param cause �����쳣������ԭ��
    */
  public VersionException(Throwable cause) {
      super(cause.getLocalizedMessage());
      this.cause = cause;
  }

  /** ���캯��<br>
    * ���쳣�м���һ���ַ�����Ϣ�����ҽ���������쳣��ԭ����롣<br>
    * �����Ҫ������ţ�����ʹ��XMLMessages��get��������msgStr�����磺<br>
    *        XMLMessages messages = new XMLMessages();<br>
    *        throw new ComException(messages.get("fileNotFound"),ex);
    * @param msgStr �쳣��Ϣ
    * @param cause �����쳣������ԭ��
    * @see XMLMessages.java
    */
   public VersionException(String msgStr, Throwable cause) {
        super(msgStr);
        this.msgStr = msgStr;
        this.cause = cause;
   }

   /** ���캯��<br>
    * ���쳣�м���CESMessage����
    * �������ʹ�ùؼ�����������Ϣ�����ݣ�����ʹ�����������ļ�װ����Ϣ���ݡ�
    * ��ϸ��Ϣ�����CESMessage�ĵ���
    * @deprecated replaced by ComException (String errorCode,String msgStr)
    * @param message �쳣��Ϣ��Ϣ
    */
   public VersionException(ComMessage message){
        this.message = message;
   }

   /** ���캯��
    * ���쳣�м���ComMessage���󣬲��ҽ���������쳣��ԭ����롣
    * @param message �쳣��Ϣ��Ϣ
    * @param cause �����쳣������ԭ��
    * @deprecated replaced by ComException (String msgStr��Throwable cause)
    */
   public VersionException(ComMessage message,Throwable cause) {
        this.message = message;
        this.cause = cause;
   }


   /** �õ��쳣��ԭ��
    * @return java.lang.Throwable
    */
   public Throwable getCause() {
    return cause;
   }


   /** �õ�ComMessage����
    * @return ces.dvp.exception.ComMessage
    */
   public ComMessage getComMessage() {
    return message;
   }


   /** ���������쳣��Ϣ
    * ���ｫ������װ�ص����ж��󶼷��س�������Ϊ������Ϣ��
    * SUPER INFO ���ظ�����쳣��Ϣ��
    * MESSAGE STRING INFO �����ַ�����Ϣ����Ϣ��
    * ComMessage OBJECT INFO ����ComMessage������Ϣ��
    * CAUSE INFO �����쳣�����ԭ��
    * @return String
    */
   public String toString() {
    StringBuffer buf = new StringBuffer();
    buf.append("�쳣���ƣ�");
    buf.append(super.getClass().getName());
    buf.append("\n");
    if(msgStr != null){
        buf.append("�쳣��Ϣ:");
        buf.append(msgStr);
        buf.append("\n");
    }
    if(message != null){
       buf.append("�쳣��Ϣ��ComMessage��:");
       buf.append(message.getText());
       buf.append("\n");
    }
    if(cause != null){
       buf.append("�쳣ԭ��:");
       buf.append(cause.toString());
       buf.append("\n");
    }
    return buf.toString();
   }
}
