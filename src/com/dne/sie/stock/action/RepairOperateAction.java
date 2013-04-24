package com.dne.sie.stock.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;

import com.dne.sie.common.exception.VersionException;
import com.dne.sie.repair.form.RepairPartForm;
import com.dne.sie.stock.bo.RepairOperateBo;
import com.dne.sie.util.action.ControlAction;


/**
 * 销售领用出库列表
 * @param request HttpServletRequest
 * @param form ActionForm 表单数据
 * @return 销售领用出库页面
 */
public class RepairOperateAction extends ControlAction{
	
	
	/**
	 * 维修携带零件出库
	 * @param request
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public String loanPartOutList(HttpServletRequest request, ActionForm form) throws Exception{
		String forward = "loanPartOutList";
	
		RepairPartForm sdf=(RepairPartForm)form;
		//已分配待领取
		sdf.setRepairPartStatus("L");
		sdf.setRepairPartType("X");
		RepairOperateBo sib = RepairOperateBo.getInstance();
		request.setAttribute("loanPartOutList",sib.loanOutList(sdf));
			
		
		return forward;
	}
	
	/**
	 * 维修携带工具出库
	 * @param request
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public String loanToolOutList(HttpServletRequest request, ActionForm form) throws Exception{
		String forward = "loanToolOutList";
	
		RepairPartForm sdf=(RepairPartForm)form;
		//已分配待领取
		sdf.setRepairPartStatus("L");
		sdf.setRepairPartType("T");
		RepairOperateBo sib = RepairOperateBo.getInstance();
		request.setAttribute("loanToolOutList",sib.loanOutList(sdf));
			
		
		return forward;
	}
	
	
	 /**
	   * 出库确认
	   * @param request HttpServletRequest
	   * @param form  表单数据
	   * @return 页面
	   */	
	  public String stockOutConfirm(HttpServletRequest request,ActionForm form) throws Exception{
		  String forward = "resultMessage";
		  int tag =-1;

		  try{
				HttpSession session=request.getSession();
				Long userId=(Long)session.getAttribute("userId");
				String chkId = request.getParameter("ids");
				String partType = request.getParameter("partType");
				
				if(chkId!=null&&!chkId.equals("")){
					RepairOperateBo sob = RepairOperateBo.getInstance();
					if("T".equals(partType)){
						tag = sob.stockToolOut(chkId,userId);
					}else if("X".equals(partType)){
						tag = sob.stockPartOut(chkId,userId);
					}
				}
		
		
			  request.setAttribute("tag",tag+"");
			  if("T".equals(partType)){
				  request.setAttribute("businessFlag","loanToolOutList");
			  }else if("X".equals(partType)){
				  request.setAttribute("businessFlag","loanPartOutList");
			  }

		  }catch(VersionException ve){
			  forward="versionErr";
		  }catch(Exception e){
			  throw e;
		  }
		  return forward;
	  }

}
