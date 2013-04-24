package com.dne.sie.util;

import java.util.Date;
import java.text.DateFormat;


/**
 * ¸ñÊ½×ª»»
 * @author xt
 * @version 1.1.5.6
 * @see FormatChange.java <br>
 */
public class FormatChange {
    public FormatChange() {
    }

    public static Date str2date(String strDate) throws Exception {
        Date date = null;
        if(strDate!=null && !strDate.equals("")){
            try{
                date = DateFormat.getDateInstance().parse(strDate);
            }catch(Exception e){
                throw e;
            }
        }
        return date;
    }

    public static String date2str(Date date) throws Exception {
        String strDate = "";
        if(date!=null){
            try{
                strDate = DateFormat.getDateInstance().format(date);
            }catch(Exception e){
                throw e;
            }
        }
        return strDate;
    }

    public static String int2str(int intValue) throws Exception {
        String strDate = "" + intValue;
        return strDate;
    }

    public static int str2int(String strValue) throws Exception {
        int intValue = 0;
        if(strValue!=null && !strValue.equals("")){
            try{
                intValue = Integer.parseInt(strValue);
            }catch(Exception e){
                throw e;
            }
        }
        return intValue;
    }



    public static void main(String[] args) {
        try {
            Date date = FormatChange.str2date("2002-1-10");
            // modified by xt  System.out.println(FormatChange.date2str(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
