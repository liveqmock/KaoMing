package com.dne.sie.maintenance.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.dne.sie.common.tools.EscapeUnescape;
import com.dne.sie.maintenance.bo.StationBinBo;
import com.dne.sie.maintenance.form.StationBinForm;
import com.dne.sie.util.action.ControlAction;

public class StationBinAction extends ControlAction {
	
	public String getPartInfo(ActionMapping mapping, ActionForm form,
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
			List<StationBinForm> partList = StationBinBo.getInstance().findByCode(inputValue);
			
			String binCode = "";		
			String binType = "";		
			String StrongDealerName = "";
			StringBuffer buffer = new StringBuffer();
			for (int i = 0; i < partList.size(); i++) {
				StationBinForm pif = partList.get(i);
				binCode = pif.getBinCode();
				binType = pif.getBinType()==null?"":pif.getBinType();
				
				if(binCode.indexOf(inputValue) != -1) {
					//�������ֵ�����ݿ�����ݱȽϺ�,�Ӵ�
					StrongDealerName = binCode.replaceAll(inputValue, "<span class=\"boldfont\">" + inputValue + "</span>");
					//StrongDealerName = "<font color=\"red\">" + StrongDealerName + "</font>";
					buffer.append("<div onselect=\"this.text.value = '")
						  .append(binCode)
						  .append("';$('binType').value = '")
						  .append(binType)
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
	
	/**
	* �����Ϣ �б�ҳ��
	* @param request HttpServletRequest
	* @param form ������
	* @return ҳ��
	*/
   public String stationBinList(HttpServletRequest request, ActionForm form) throws Exception{
		String forward = "stationBinList";
		StationBinForm pif=(StationBinForm)form;
		StationBinBo pib = StationBinBo.getInstance();
		request.setAttribute("stationBinList",pib.list(pif));
		
		return forward;
   }
   
   /**
	* �����Ϣ ����ҳ��
	* @param request HttpServletRequest
	* @param form ������
	* @return ҳ��
	*/
  public String stationBinInit(HttpServletRequest request, ActionForm form) throws Exception {
		String forward = "stationBinEdit";
		request.setAttribute("action","insertStationBin");
		return forward;
  }
  
  /**
	* �����Ϣ �޸�ҳ��
	* @param request HttpServletRequest
	* @param form ������
	* @return ҳ��
	*/
  public String stationBinEdit(HttpServletRequest request, ActionForm form) throws Exception{
		String forward = "stationBinEdit";
		String stationBin=request.getParameter("ids");
		request.setAttribute("partInfoForm", StationBinBo.getInstance().find(stationBin));
		request.setAttribute("stationBin",stationBin);
		request.setAttribute("action","updatePartInfo");
		return forward;
  }
  

	/**
	 * У���Ϻ��Ƿ����
	 * @param request HttpServletRequest
	 * @param form  ������
	 * @return ҳ��
	 */	
	public void chkStationBin(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		try{
			String stationBin=request.getParameter("stationBin");
			StationBinBo pib = StationBinBo.getInstance();
			String strXml="false";
			if(pib.chkStationBin(stationBin)){
				strXml="true";
			}
		
			PrintWriter writer = response.getWriter();
			response.setContentType("text/xml");			
			response.setHeader("Cache-Control", "no-cache"); 
			writer.println("<xml>");
			
			writer.println("<flag>"+strXml+"</flag>");
		
			writer.println("</xml>");
			writer.flush();
			writer.close();
		
		}catch(Exception e){
			e.printStackTrace();
		}
	
	}

  /**
	* �����Ϣ �������
	* @param request HttpServletRequest
	* @param form ������
	* @return ҳ��
	*/
  public String insertStationBin(HttpServletRequest request, ActionForm form) throws Exception{
		String forward = "stationBinList";
		StationBinForm pif=(StationBinForm)form;
		StationBinBo.getInstance().add(pif);
		request.setAttribute("stationBinList",StationBinBo.getInstance().list(new StationBinForm()));
		return forward;
  }
  

  /**
	* �����Ϣ �޸Ĳ���
	* @param request HttpServletRequest
	* @param form ������
	* @return ҳ��
	*/
  public String updatePartInfo(HttpServletRequest request, ActionForm form) throws Exception{
		String forward = "stationBinList";
		StationBinForm pif=(StationBinForm)form;
		StationBinBo.getInstance().modify(pif);
		request.setAttribute("stationBinList",StationBinBo.getInstance().list(new StationBinForm()));
		
		return forward;
  }
  
  public String deletePartInfo(HttpServletRequest request, ActionForm form) throws Exception{
		String forward = "stationBinList";
		String stationBins=request.getParameter("ids");
		StationBinBo.getInstance().delete(stationBins);
		request.setAttribute("stationBinList",StationBinBo.getInstance().list(new StationBinForm()));
		
		return forward;
  }

}
