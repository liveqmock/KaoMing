package com.dne.sie.common.encrypt;

import java.util.StringTokenizer;
/**
 * <p>Title: �����ע����</p>
 * <p>Description: �����ע����</p>
 * @version 1.0
 */
public class CheckSQL {
 
 private String strSQL;
 
 private String badwords = "select|from|update|delete|count|*|sum|master|script|'|declare|or|execute|alter|statement|executeQuery|count|executeUpdate";
 
 public CheckSQL(String strSQL){
  
  this.strSQL = strSQL;
 }
 /**
  * �����ѯ�����������Ƿ������дʽ��
  * @param str
  * @return
  */
 public String isBadwords(String str){
  str = str.toLowerCase();
  //System.out.println(str);
  String[] data = split(badwords,"|");
  for (int i=0 ; i < data.length ; i++ ){
    if (str.indexOf(data[i])>=0){
     return data[i];
    }
  }
  return null;
 }
 /**
  * �ָstr1|str2|str3|str4|str5 ��ʽ������Ϊһά����
  * @param str ��str1|str2|str3|str4|str5
  * @param sign ���ָ��
  * @return
  */
 public static String[] split(String str, String sign) {
  String[] strData = null;
  StringTokenizer st1 = new StringTokenizer(str, sign);
  //�������鳤��
  strData = new String[st1.countTokens()];
  int i = 0;
  while (st1.hasMoreTokens()) {
   strData[i] = st1.nextToken().trim();
   i++;
  }
  return strData;
 } 
 public static void main(String[] args){
  //String query = "select * from user";
//	 String query = "aaabbfro'bb";
//	 CheckSQL get = new CheckSQL(query);
//	 String illegal =get.isBadwords(query);
//	 if(illegal!=null){
//	   System.err.println("�Ƿ��ַ�:"+illegal);
//	 }else{
//		 System.out.println("ok");
//	 }
	 
 }
}
