package com.dne.sie.maintenance.action;

//Java 基础类
//import java.util.List;

//Java 扩展类
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//第三方类
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

//自定义类
import com.dne.sie.util.action.ControlAction;
import com.dne.sie.maintenance.bo.AttachedInfoBo;

/**
 * 附件信息Action处理类
 * @author xt
 * @version 1.1.5.6
 */
public class AttachedInfoAction extends ControlAction {
	
	
	/**
	* 附件 列表页面
	* @param request HttpServletRequest
	* @param form 表单数据
	* @return 页面
	*/
   public String affixList(HttpServletRequest request, ActionForm form) throws Exception{
		String forward = "affixList";
		try{
			String foreignId=request.getParameter("saleDetailId");
			AttachedInfoBo aib = AttachedInfoBo.getInstance();
			
			request.setAttribute("affixList",aib.getAffixList(new Long(foreignId)));
			request.setAttribute("foreignId",foreignId);
			request.setAttribute("viewFlag",request.getParameter("viewFlag"));
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
   }
   
	
	/**
	 * 删除附件信息表中的文件
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 */	
	public void fileDel(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) {


		try{
			String attacheId=request.getParameter("attacheId");
			AttachedInfoBo aibo = AttachedInfoBo.getInstance();
			String strXml="false";
			if(aibo.fileDel(attacheId,"virtualName")){
				strXml="true";
			}

			/*
			PrintWriter writer = response.getWriter();				  
			response.setContentType("text/xml");						 
			response.setHeader("Cache-Control", "no-cache");          
			writer.println("<xml>");
	
			writer.println("<ifUse>"+strXml+"</ifUse>");

			writer.println("</xml>");
			writer.flush();
			writer.close();
			*/
			
		}catch(Exception e){
			e.printStackTrace();
		}

	}
   


}

