package com.dne.sie.common.splitpage;

import javax.servlet.jsp.tagext.*;
import org.apache.log4j.Logger;

/**
* <p>文件名:PageControlExtraInfo.java </p>
* <p>功能描述: PageControlTag 的TEI类</p>
* <p>Copyright: Copyright (c) 2005</p>
* <p>Company: 
* @author xt
* @version
*/

public class PageControlExtraInfo extends TagExtraInfo {

 Logger logger = Logger.getLogger(this.getClass());
 public PageControlExtraInfo() {
 }

 public VariableInfo[] getVariableInfo(TagData tagdata) {
//		int numOfRcds = 0; //总的记录数
//		int rcdPerPage = 10; //每页显示的记录数
//		int currentPage = 1; //当前页的index
     String pageNum = tagdata.getAttributeString("idPage");
     String first = tagdata.getAttributeString("idFirst");
     String last = tagdata.getAttributeString("idLast");

     if (pageNum == null) {
         pageNum = "pageNum";
     }
     if (first == null) {
         first = "first";
     }
     if (last == null) {
         last = "last";
     }
     VariableInfo pageNumInfo = new VariableInfo(pageNum, "java.lang.Integer", true,
         VariableInfo.AT_BEGIN);
     VariableInfo firstInfo = new VariableInfo(first, "java.lang.Integer", true,
                                               VariableInfo.AT_END);
     VariableInfo lastInfo = new VariableInfo(last, "java.lang.Integer", true,
                                              VariableInfo.AT_END);
     VariableInfo avariableinfo[] = {firstInfo, lastInfo};
     return avariableinfo;
 }
}