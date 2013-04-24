package com.dne.sie.common.tools;

import java.io.BufferedReader; 
import java.io.InputStreamReader; 
import java.net.HttpURLConnection; 
import java.net.URL; 
import java.util.ArrayList;
import java.util.List;

public class HTMLSpirit { 
	
	public final static String NewsUrl="http://www.kaomingcn.com.cn/china/home.php";
	public final static String Encoding="big5";
	
    public static List<String> getHTML(String pageURL, String encoding) { 
    	List<String> pageHTML = new ArrayList<String>(); 
    	HttpURLConnection connection=null;
        try { 
            URL url = new URL(pageURL); 
            connection = (HttpURLConnection) url.openConnection(); 
            connection.setRequestProperty("User-Agent", "MSIE 7.0"); 
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), encoding)); 
            String line = null; 
            while ((line = br.readLine()) != null) { 
            	if(line.indexOf("<a href=\"news")!=-1 && line.indexOf(" onMouse")==-1){
            		line = line.replaceFirst("<td ", "<td class=\"content12\" ");
            		line = line.replaceFirst("<a href=\"", "<a href=\"http://www.kaomingcn.com.cn/china/");
            		pageHTML.add(line);
            		if(pageHTML.size()>5) break;
            	}
            } 
            
        } catch (Exception e) { 
            e.printStackTrace(); 
        } finally{
        	if(connection!=null) connection.disconnect(); 
        }
        return pageHTML; 
    } 
     
    public static void main(String args[]){ 
        System.out.println(getHTML(NewsUrl, Encoding));
    	
    } 
} 
