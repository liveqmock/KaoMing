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
			
			//得到页面上输入的值
			String inputValue = request.getParameter("inputValue");
			//中文需要转换，使用javascript的escape编码，所有字符集都可用
			inputValue = EscapeUnescape.unescape(inputValue);
			//得到所有经销商的名字和id
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
					//把输入的值和数据库的数据比较后,加粗
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
	* 零件信息 列表页面
	* @param request HttpServletRequest
	* @param form 表单数据
	* @return 页面
	*/
   public String stationBinList(HttpServletRequest request, ActionForm form) throws Exception{
		String forward = "stationBinList";
		StationBinForm pif=(StationBinForm)form;
		StationBinBo pib = StationBinBo.getInstance();
		request.setAttribute("stationBinList",pib.list(pif));
		
		return forward;
   }
   
   /**
	* 零件信息 新增页面
	* @param request HttpServletRequest
	* @param form 表单数据
	* @return 页面
	*/
  public String stationBinInit(HttpServletRequest request, ActionForm form) throws Exception {
		String forward = "stationBinEdit";
		request.setAttribute("action","insertStationBin");
		return forward;
  }
  
  /**
	* 零件信息 修改页面
	* @param request HttpServletRequest
	* @param form 表单数据
	* @return 页面
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
	 * 校验料号是否存在
	 * @param request HttpServletRequest
	 * @param form  表单数据
	 * @return 页面
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
	* 零件信息 插入操作
	* @param request HttpServletRequest
	* @param form 表单数据
	* @return 页面
	*/
  public String insertStationBin(HttpServletRequest request, ActionForm form) throws Exception{
		String forward = "stationBinList";
		StationBinForm pif=(StationBinForm)form;
		StationBinBo.getInstance().add(pif);
		request.setAttribute("stationBinList",StationBinBo.getInstance().list(new StationBinForm()));
		return forward;
  }
  

  /**
	* 零件信息 修改操作
	* @param request HttpServletRequest
	* @param form 表单数据
	* @return 页面
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
