package com.dne.sie.maintenance.action;

//Java 基础类
//import java.util.List;

//Java 扩展类
import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;


//第三方类
import org.apache.struts.action.ActionForm;
//import org.apache.struts.action.ActionMapping;

//自定义类
import com.dne.sie.util.action.ControlAction;
import com.dne.sie.maintenance.bo.SyscodeBo;
import com.dne.sie.maintenance.form.TsSystemCodeForm;


/**
 * 字典表信息Action处理类
 * @author xt
 * @version 1.1.5.6
 */
public class SyscodeAction extends ControlAction {
	
	
	/**
	* 字典表信息 列表页面
	* @param request HttpServletRequest
	* @param form 表单数据
	* @return 页面
	*/
   public String syscodeList(HttpServletRequest request, ActionForm form) throws Exception{
		String forward = "syscodeList";
		TsSystemCodeForm pif=(TsSystemCodeForm)form;
		SyscodeBo pib = SyscodeBo.getInstance();
		request.setAttribute("syscodeList",pib.list(pif));
		
		return forward;
   }
   
   /**
	* 字典表信息 新增页面
	* @param request HttpServletRequest
	* @param form 表单数据
	* @return 页面
	*/
  public String addSyscode(HttpServletRequest request, ActionForm form) throws Exception {
		String forward = "syscodeAdd";
		
		return forward;
  }
  
  /**
	* 字典表信息 修改页面
	* @param request HttpServletRequest
	* @param form 表单数据
	* @return 页面
	*/
  public String editSyscode(HttpServletRequest request, ActionForm form) {
		String forward = "syscodeEdit";
		try{
			String sysId=request.getParameter("ids");
			request.setAttribute("syscodeForm", SyscodeBo.getInstance().find(sysId));
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
  }

  /**
	* 字典表信息 插入操作
	* @param request HttpServletRequest
	* @param form 表单数据
	* @return 页面
	*/
  public String insertSyscode(HttpServletRequest request, ActionForm form) {
		String forward = "syscodeList";
		try{
			TsSystemCodeForm pif=(TsSystemCodeForm)form;
			SyscodeBo.getInstance().add(pif);
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
  }
  

  /**
	* 字典表信息 修改操作
	* @param request HttpServletRequest
	* @param form 表单数据
	* @return 页面
	*/
  public String updateSyscode(HttpServletRequest request, ActionForm form) {
		String forward = "syscodeList";
		try{
			TsSystemCodeForm pif=(TsSystemCodeForm)form;
			SyscodeBo.getInstance().modify(pif);
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
  }
  

  /**
	* 字典表信息 删除操作
	* @param request HttpServletRequest
	* @param form 表单数据
	* @return 页面
	*/
  public String deleteSyscode(HttpServletRequest request, ActionForm form) {
		String forward = "syscodeList";
		try{
			String sysId=request.getParameter("ids");
			SyscodeBo.getInstance().delete(sysId);
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
  }


}

