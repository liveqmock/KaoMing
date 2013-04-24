package com.dne.sie.maintenance.action;

import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.dne.sie.common.tools.EscapeUnescape;
import com.dne.sie.common.tools.Operate;
import com.dne.sie.maintenance.bo.MachineToolBo;
import com.dne.sie.maintenance.form.MachineToolForm;
import com.dne.sie.util.action.ControlAction;

public class MachineToolAction extends ControlAction {
	
	

	/**
	* �ͻ�����Ϣ �б�ҳ��
	* @param request HttpServletRequest
	* @param form ������
	* @return ҳ��
	*/
   public String machineToolList(HttpServletRequest request, ActionForm form) throws Exception{
		String forward = "machineToolList";
		MachineToolForm pif=(MachineToolForm)form;
		MachineToolBo pib = MachineToolBo.getInstance();
		request.setAttribute("machineToolList",pib.list(pif));
		
		return forward;
   }

  
  /**
	* �ͻ�����Ϣ �޸�ҳ��
	* @param request HttpServletRequest
	* @param form ������
	* @return ҳ��
	*/
  public String machineToolEdit(HttpServletRequest request, ActionForm form) {
		String forward = "machineToolEdit";
		try{
			String machineId=request.getParameter("ids");
			if(machineId!=null&&!machineId.isEmpty()){
				request.setAttribute("machineToolForm", MachineToolBo.getInstance().find(new Long(machineId)));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
  }
  
  

  /**
	* �ͻ�����Ϣ �������
	* @param request HttpServletRequest
	* @param form ������
	* @return ҳ��
	*/
  public String insertMachineTool(HttpServletRequest request, ActionForm form) {
		String forward = "resultMessage";
		try{
			MachineToolForm pif=(MachineToolForm)form;
		    pif.setCreateBy((Long)request.getSession().getAttribute("userId"));
			MachineToolBo cibo = MachineToolBo.getInstance();
			pif.setPurchaseDate(Operate.toDate(pif.getPurchaseDateStr()));
			pif.setExtendedWarrantyDate(Operate.toDate(pif.getExtendedWarrantyDateStr()));
			int tag=cibo.add(pif);
			request.setAttribute("tag", tag + "");
			request.setAttribute("businessFlag", "saveMachineTool");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
  }
  

  /**
	* �ͻ�����Ϣ �޸Ĳ���
	* @param request HttpServletRequest
	* @param form ������
	* @return ҳ��
	*/
  public String updateMachineTool(HttpServletRequest request, ActionForm form) {
		String forward = "resultMessage";
		try{
			MachineToolForm pif=(MachineToolForm)form;
			MachineToolBo cibo = MachineToolBo.getInstance();
			pif.setUpdateBy((Long)request.getSession().getAttribute("userId"));
			pif.setUpdateDate(new Date());
			pif.setPurchaseDate(Operate.toDate(pif.getPurchaseDateStr()));
			pif.setExtendedWarrantyDate(Operate.toDate(pif.getExtendedWarrantyDateStr()));
			int tag=cibo.modify(pif);
			request.setAttribute("tag", tag + "");
			request.setAttribute("businessFlag", "saveMachineTool");
		
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
  }
  

  /**
	* �ͻ�����Ϣ ɾ������
	* @param request HttpServletRequest
	* @param form ������
	* @return ҳ��
	*/
  public String deleteMachineTool(HttpServletRequest request, ActionForm form) {
		String forward = "resultMessage";
		try{
			String sysId=request.getParameter("ids");
			int tag=MachineToolBo.getInstance().delete(sysId);
			request.setAttribute("tag", tag + "");
			request.setAttribute("businessFlag", "saveMachineTool");
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
  }
  
  

	/**
	 * У���û�id�Ƿ����
	 * @param request HttpServletRequest
	 * @param form  ������
	 * @return ҳ��
	 */	
	public void ajaxChkSerial(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		PrintWriter writer = null;
		try{
			String machineId=request.getParameter("machineId");
			String serialNo=request.getParameter("serialNo");
			MachineToolBo ubo = MachineToolBo.getInstance();
			String strXml="false";
			if(ubo.chkSerial(machineId,serialNo)){
				strXml="true";
			}
		
			writer = response.getWriter();			
			response.setContentType("text/xml");					
			response.setHeader("Cache-Control", "no-cache");      
			writer.println("<xml>");
			
			writer.println("<ifUse>"+strXml+"</ifUse>");
		
			writer.println("</xml>");
			
		
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(writer!=null){
				writer.flush();
				writer.close();
			}
		}
	
	}
	
	
	public String getSerialInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		try{
			response.setContentType("text/html;charset=UTF-8");
			
			//diable cache
	        // Set to expire far in the past.
	        response.setHeader("Expires", "Sat, 6 May 1995 12:00:00 GMT");
	
	        // Set standard HTTP/1.1 no-cache headers.
	        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
	
	        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
	        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
	
	        // Set standard HTTP/1.0 no-cache header.
	        response.setHeader("Pragma", "no-cache");
			
			//�õ�ҳ���������ֵ
			String inputValue = request.getParameter("inputValue");
			//������Ҫת����ʹ��javascript��escape���룬�����ַ���������
			inputValue = EscapeUnescape.unescape(inputValue);
			//�õ����о����̵����ֺ�id
			List machineList = MachineToolBo.getInstance().getSerialListByName(inputValue);
			
			String modelCode = "";		
			String serialNo = "";		
			String warrantyCardNo = "";		
			String purchaseDate = "";		
			String extendedWarrantyDate = "";	
			
			String StrongDealerName = "";
			
			StringBuffer buffer = new StringBuffer();
			for (int i = 0; i < machineList.size(); i++) {
				Object[] obj = (Object[]) machineList.get(i);
			
				modelCode = obj[0].toString();
				serialNo = obj[1].toString();
				warrantyCardNo = obj[2]==null?"":obj[2].toString();
				purchaseDate = obj[3]==null?"":Operate.trimDate((Date)obj[3]);
				extendedWarrantyDate = obj[4]==null?"":Operate.trimDate((Date)obj[4]);
				
				if(serialNo.indexOf(inputValue) != -1) {
					//�������ֵ�����ݿ�����ݱȽϺ�,�Ӵ�
					StrongDealerName = serialNo.replaceAll(inputValue, "<span class=\"boldfont\">" + inputValue + "</span>");
					
					buffer.append("<div onselect=\"this.text.value = '")
						  .append(serialNo)
						  .append("';$('modelCode').value = '")
						  .append(modelCode)
						  .append("';$('warrantyCardNo').value = '")
						  .append(warrantyCardNo)
						  .append("';$('purchaseDateStr').value = '")
						  .append(purchaseDate)
						  .append("';$('extendedWarrantyDate').value = '")
						  .append(extendedWarrantyDate)
						  .append("'\">")
						  .append(StrongDealerName)
						  .append("</div>");
				}
			}
			PrintWriter out = response.getWriter();
			out.println(buffer.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	

}
