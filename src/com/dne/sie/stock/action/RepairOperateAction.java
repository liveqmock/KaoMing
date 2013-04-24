package com.dne.sie.stock.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;

import com.dne.sie.common.exception.VersionException;
import com.dne.sie.repair.form.RepairPartForm;
import com.dne.sie.stock.bo.RepairOperateBo;
import com.dne.sie.util.action.ControlAction;


/**
 * �������ó����б�
 * @param request HttpServletRequest
 * @param form ActionForm ������
 * @return �������ó���ҳ��
 */
public class RepairOperateAction extends ControlAction{
	
	
	/**
	 * ά��Я���������
	 * @param request
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public String loanPartOutList(HttpServletRequest request, ActionForm form) throws Exception{
		String forward = "loanPartOutList";
	
		RepairPartForm sdf=(RepairPartForm)form;
		//�ѷ������ȡ
		sdf.setRepairPartStatus("L");
		sdf.setRepairPartType("X");
		RepairOperateBo sib = RepairOperateBo.getInstance();
		request.setAttribute("loanPartOutList",sib.loanOutList(sdf));
			
		
		return forward;
	}
	
	/**
	 * ά��Я�����߳���
	 * @param request
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public String loanToolOutList(HttpServletRequest request, ActionForm form) throws Exception{
		String forward = "loanToolOutList";
	
		RepairPartForm sdf=(RepairPartForm)form;
		//�ѷ������ȡ
		sdf.setRepairPartStatus("L");
		sdf.setRepairPartType("T");
		RepairOperateBo sib = RepairOperateBo.getInstance();
		request.setAttribute("loanToolOutList",sib.loanOutList(sdf));
			
		
		return forward;
	}
	
	
	 /**
	   * ����ȷ��
	   * @param request HttpServletRequest
	   * @param form  ������
	   * @return ҳ��
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
