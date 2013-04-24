package com.dne.sie.stock.action;

//Java 基础类
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//Java 扩展类
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//第三方类
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.log4j.Logger;

//自定义类
import com.dne.sie.common.exception.VersionException;
import com.dne.sie.reception.form.SaleDetailForm;
import com.dne.sie.stock.bo.StockOutBo;
import com.dne.sie.util.action.ControlAction;

/**
 * 零件需求手工分配控制类。
 * @author xt
 * @version Version 1.1.5.6
 */
public class HandAllocateAction extends ControlAction{
	
	

	/**
	 * 手工分配 列表
	 * @param request HttpServletRequest
	 * @param form ActionForm 表单数据
	 * @return 销售单明细页面
	 */
	public String requestList(HttpServletRequest request, ActionForm form){
		String forward = "requestList";
	
		try{
			SaleDetailForm sdf=(SaleDetailForm)form;
			StockOutBo sob = StockOutBo.getInstance();
			sdf.setPartStatus("H");	//订购中
			request.setAttribute("requestList",sob.requestList(sdf));
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
	}

	   /**
	   * 手工分配确认
	   * @param request HttpServletRequest
	   * @param form  表单数据
	   * @return 页面
	   */	
	  public String manualAllcate(HttpServletRequest request,ActionForm form) {
		  String forward = "resultMessage";
		  int tag =-1;

		  try{
				String chkId = request.getParameter("ids");
				String[] idList = null;
				if(chkId!=null&&!chkId.equals("")){
					StockOutBo sob = StockOutBo.getInstance();
					idList = chkId.split(",");
					
					tag = sob.manualAllcate(idList);
				}
		
			  request.setAttribute("tag",tag+"");
			  request.setAttribute("businessFlag","manualAllcate");

		  }catch(VersionException ve){
			  forward="versionErr";
		  }catch(Exception e){
			  e.printStackTrace();
		  }
		  return forward;
	  }

}
