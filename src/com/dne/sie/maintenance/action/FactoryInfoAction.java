package com.dne.sie.maintenance.action;

//Java 基础类
//import java.util.List;

//Java 扩展类
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpServletResponse;


//第三方类
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
//import org.apache.struts.action.ActionMapping;

//自定义类
import com.dne.sie.util.action.ControlAction;
import com.dne.sie.maintenance.bo.FactoryInfoBo;
import com.dne.sie.maintenance.form.FactoryInfoForm;


/**
 * 厂商信息Action处理类
 * @author xt
 * @version 1.1.5.6
 */
public class FactoryInfoAction extends ControlAction {
	
	
	/**
	* 厂商表信息 列表页面
	* @param request HttpServletRequest
	* @param form 表单数据
	* @return 页面
	*/
   public String factoryList(HttpServletRequest request, ActionForm form) throws Exception{
		String forward = "factoryList";
		FactoryInfoForm pif=(FactoryInfoForm)form;
		FactoryInfoBo pib = FactoryInfoBo.getInstance();
		request.setAttribute("factoryList",pib.list(pif));
		
		return forward;
   }
   
   /**
	* 厂商表信息 新增页面
	* @param request HttpServletRequest
	* @param form 表单数据
	* @return 页面
	*/
  public String factoryInit(HttpServletRequest request, ActionForm form) throws Exception {
		String forward = "factoryInit";
		
		return forward;
  }
  
  /**
	* 厂商表信息 修改页面
	* @param request HttpServletRequest
	* @param form 表单数据
	* @return 页面
	*/
  public String factoryEdit(HttpServletRequest request, ActionForm form) {
		String forward = "factoryEdit";
		try{
			String sysId=request.getParameter("ids");
			request.setAttribute("factoryInfoForm", FactoryInfoBo.getInstance().find(sysId));
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
  }

  /**
	* 厂商表信息 插入操作
	* @param request HttpServletRequest
	* @param form 表单数据
	* @return 页面
	*/
  public String insertFactory(HttpServletRequest request, ActionForm form) {
		String forward = "resultMessage";
		try{
			FactoryInfoForm pif=(FactoryInfoForm)form;
			FactoryInfoBo cibo = FactoryInfoBo.getInstance();
			if(cibo.chkFactoryId(pif.getFactoryId())){
				int tag=cibo.add(pif);
				request.setAttribute("tag", tag + "");
				request.setAttribute("businessFlag", "saveFactory");
			}else{
				forward="factoryInit";
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
  }
  

  /**
	* 厂商表信息 修改操作
	* @param request HttpServletRequest
	* @param form 表单数据
	* @return 页面
	*/
  public String updateFactory(HttpServletRequest request, ActionForm form) {
		String forward = "resultMessage";
		try{
			FactoryInfoForm pif=(FactoryInfoForm)form;
			FactoryInfoBo cibo = FactoryInfoBo.getInstance();
			int tag=cibo.modify(pif);
			request.setAttribute("tag", tag + "");
			request.setAttribute("businessFlag", "saveFactory");
		
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
  }
  

  /**
	* 厂商表信息 删除操作
	* @param request HttpServletRequest
	* @param form 表单数据
	* @return 页面
	*/
  public String deleteFactory(HttpServletRequest request, ActionForm form) {
		String forward = "resultMessage";
		try{
			String sysId=request.getParameter("ids");
			int tag=FactoryInfoBo.getInstance().delete(sysId);
			request.setAttribute("tag", tag + "");
			request.setAttribute("businessFlag", "deleteFactory");
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
  }

	/**
	 * 校验用户id是否存在
	 * @param request HttpServletRequest
	 * @param form  表单数据
	 * @return 页面
	 */	
	public void ajaxChkId(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		
		try{
			String custId=request.getParameter("custId");
			FactoryInfoBo ubo = FactoryInfoBo.getInstance();
			String strXml="false";
			if(ubo.chkFactoryId(custId)){
				strXml="true";
			}
		
			PrintWriter writer = response.getWriter();			
			response.setContentType("text/xml");					
			response.setHeader("Cache-Control", "no-cache");      
			writer.println("<xml>");
			
			writer.println("<ifUse>"+strXml+"</ifUse>");
		
			writer.println("</xml>");
			writer.flush();
			writer.close();
		
		}catch(Exception e){
			e.printStackTrace();
		}
	
	}

}

