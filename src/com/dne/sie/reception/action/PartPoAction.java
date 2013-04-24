package com.dne.sie.reception.action;

//Java 基础类
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;

import com.dne.sie.common.exception.IllegalPoException;
import com.dne.sie.common.tools.CommonSearch;
import com.dne.sie.common.tools.FormNumberBuilder;
import com.dne.sie.maintenance.bo.CustomerInfoBo;
import com.dne.sie.reception.bo.PartPoBo;
import com.dne.sie.reception.form.PoForm;
import com.dne.sie.support.userRole.action.RoleAction;
import com.dne.sie.util.action.ControlAction;

/**
 * 零件PO Action处理类
 * @author xt
 * @version 1.1.5.6
 * @see RoleAction.java <br>
 */
public class PartPoAction extends ControlAction{
	

	/**
	 * 零件订购待发列表
	 * @param request HttpServletRequest
	 * @param form ActionForm 表单数据
	 * @return 列表
	 */
	public String planList(HttpServletRequest request, ActionForm form){
		String forward = "planList";
	
		try{
			PoForm pof=(PoForm)form;
			//等待发送
			pof.setOrderStatus("A");
			//pof.setOrderType("out");	//非保内订购
			pof.setTransportMode(null);
			PartPoBo pob = PartPoBo.getInstance();
			request.setAttribute("poList",pob.planList(pof));
			request.setAttribute("orderNo",FormNumberBuilder.findOrderNo("SH"));
			request.setAttribute("poPayAmounts",pob.getPoPayAmounts());
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
	}

	/**
	* PO确认
	* @param request HttpServletRequest
	* @param form 表单数据
	* @return 页面
	*/
	public String sendPo(HttpServletRequest request, ActionForm form) {
		String forward = "resultMessage";
		int tag=-1;
		String flag = request.getParameter("flag");
		String orderNo = null;
		try{
			HttpSession session = request.getSession();
			Long userId = (Long) session.getAttribute("userId");
			
			String ids = request.getParameter("ids");
			String remark = request.getParameter("remark");
			String factoryId = request.getParameter("factoryId");
			String factoryName = request.getParameter("factoryName");
			String transportMode = request.getParameter("transportMode");
			
			orderNo = FormNumberBuilder.getOrderNo("SH");
			tag=PartPoBo.sendPo(ids,userId,orderNo,remark,factoryId,factoryName,transportMode);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			request.setAttribute("tag", tag + "");
			request.setAttribute("tempData", orderNo);
			if("manu".equals(flag)){
				request.setAttribute("businessFlag", "manualPlanAdd");
			}else{
				request.setAttribute("businessFlag", "sendPo");
			}
			
		
		}
		return forward;
	}
	
	/**
	 * PO取消
	 * @param request
	 * @param form
	 * @return
	 */
	public String cancelPo(HttpServletRequest request, ActionForm form) {
		String forward = "resultMessage";
		int tag=-1;
		String flag = request.getParameter("flag");
		String orderNo = null;
		try{
			HttpSession session = request.getSession();
			Long userId = (Long) session.getAttribute("userId");
			
			String ids = request.getParameter("ids");
			String remark = request.getParameter("remark");
			
			tag=PartPoBo.cancelPo(ids,userId,remark);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			request.setAttribute("tag", tag + "");
			request.setAttribute("tempData", orderNo);
			if("manu".equals(flag)){
				request.setAttribute("businessFlag", "manualPlanAdd");
			}else{
				request.setAttribute("businessFlag", "sendPo");
			}
			
		
		}
		return forward;
	}

	/**
	 * 手工订购列表
	 * @param request HttpServletRequest
	 * @param form ActionForm 表单数据
	 * @return 列表
	 */
	public String manualPlanList(HttpServletRequest request, ActionForm form){
		String forward = "manualPlanList";
	
		try{
			PoForm pof=new PoForm();
			
			pof.setOrderStatus("A");	//等待发送
			pof.setOrderType("I");	//保内订购
			//pof.setWarrantyType("O");
			
			PartPoBo pob = PartPoBo.getInstance();
			request.setAttribute("poList",pob.planList(pof));
			request.setAttribute("orderNo",FormNumberBuilder.findOrderNo("SH"));
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
	}
	

	/**
	* 手工订购新增
	* @param request HttpServletRequest
	* @param form 表单数据
	* @return 页面
	*/
	public String manualPlanAdd(HttpServletRequest request, ActionForm form) {
		String forward = "resultMessage";
		int tag=-1;
		try{
			HttpSession session = request.getSession();
			Long userId = (Long) session.getAttribute("userId");
			
			PoForm pof=(PoForm)form;
			if(CommonSearch.getInstance().getPartInfo(pof.getStuffNo())==null){
				throw new IllegalPoException(pof.getStuffNo());
			}
			pof.setOrderStatus("A");	//等待发送
			pof.setOrderType("I");	//手工订购
			pof.setWarrantyType("O");
			pof.setSaleNo("N");		
			
			
			pof.setCreateBy(userId);
			pof.setCreateDate(new Date());
			pof.setTransportMode(null);
			
			PartPoBo pob = PartPoBo.getInstance();
			tag=pob.manualPlanAdd(pof);

		}catch(IllegalPoException e1){
			request.setAttribute("errStuffNo", e1.getMessage());
			forward="IllegalPoException";	
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			request.setAttribute("tag", tag + "");
			request.setAttribute("businessFlag", "manualPlanAdd");
		
		}
		return forward;
	}
	

	/**
	* 手工订购删除
	* @param request HttpServletRequest
	* @param form 表单数据
	* @return 页面
	*/
	public String manuPoDel(HttpServletRequest request, ActionForm form) {
		String forward = "resultMessage";
		int tag=-1;
		try{
			String ids = request.getParameter("ids");
			
			PartPoBo pob = PartPoBo.getInstance();
			tag=pob.manuPoDel(ids);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			request.setAttribute("tag", tag + "");
			request.setAttribute("businessFlag", "manualPlanAdd");
		
		}
		return forward;
	}
	

	/**
	 * 零件订购清单
	 * @param request HttpServletRequest
	 * @param form ActionForm 表单数据
	 * @return 列表
	 */
	public String poList(HttpServletRequest request, ActionForm form){
		String forward = "poList";
	
		try{
			PoForm pof=(PoForm)form;
			
			PartPoBo pob = PartPoBo.getInstance();
			request.setAttribute("poList",pob.planList(pof));
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
	}
	

	/**
	 * 订购单打印
	 * @param request HttpServletRequest
	 * @param form ActionForm 表单数据
	 * @return 询价单打印页面
	 */
	public String poFormPrint(HttpServletRequest request, ActionForm form){
		String forward="poFormPrint";
	
		try{
			String orderNo = request.getParameter("orderNo");
			PartPoBo pob = PartPoBo.getInstance();
			ArrayList poList = pob.poFormPrint(orderNo);
			if(poList!=null&&poList.size()>0){
				request.setAttribute("poFormPrintList",poList);
				String[] kmInfo=CustomerInfoBo.getInstance().getKmInfo("TWKM");
				request.setAttribute("kmInfo",kmInfo);
			}else{
				request.setAttribute("tag", "-1");
				request.setAttribute("businessFlag", "poFormPrintNull");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
	}
	
	/**
	* 订购单取消
	* @param request HttpServletRequest
	* @param form 表单数据
	* @return 页面
	*/
	public String orderCancel(HttpServletRequest request, ActionForm form) {
		String forward = "resultMessage";
		int tag=-1;
		try{
			HttpSession session = request.getSession();
			Long userId = (Long) session.getAttribute("userId");
			
			String ids = request.getParameter("ids");
			
			PartPoBo pob = PartPoBo.getInstance();
			tag=pob.orderCancel(ids,userId);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			request.setAttribute("tag", tag + "");
			request.setAttribute("businessFlag", "orderCancel");
		
		}
		return forward;
	}
	

}
