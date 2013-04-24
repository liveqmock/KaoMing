package com.dne.sie.maintenance.action;

//Java ������
import java.util.List;
import sun.misc.BASE64Decoder;

//Java ��չ��
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//��������
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

//�Զ�����
import com.dne.sie.support.userRole.bo.UserBo;
import com.dne.sie.util.action.ControlAction;
import com.dne.sie.common.tools.EscapeUnescape;
import com.dne.sie.maintenance.bo.PartInfoBo;
import com.dne.sie.maintenance.form.PartInfoForm;


/**
 * �����ϢAction������
 * @author xt
 * @version 1.1.5.6
 */
public class PartInfoAction extends ControlAction {
	
	/**
	 * �õ���������Ϻź�����
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
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
			List partList = PartInfoBo.getInstance().findByStuff(inputValue);
			
			String stuffNo = "";		
			String skuCode = "";		
			String shortCode = "";		
			String standard = "";		
			String skuUnit = "";	
			String StrongDealerName = "";
			StringBuffer buffer = new StringBuffer();
			for (int i = 0; i < partList.size(); i++) {
				PartInfoForm pif = (PartInfoForm) partList.get(i);
				stuffNo = pif.getStuffNo();
				skuCode = pif.getSkuCode()==null?"":pif.getSkuCode();
				shortCode = pif.getShortCode()==null?"":pif.getShortCode();
				standard = pif.getStandard()==null?"":pif.getStandard();
				skuUnit = pif.getSkuUnit()==null?"":pif.getSkuUnit();
				if(stuffNo.indexOf(inputValue) != -1) {
					//�������ֵ�����ݿ�����ݱȽϺ�,�Ӵ�
					StrongDealerName = stuffNo.replaceAll(inputValue, "<span class=\"boldfont\">" + inputValue + "</span>");
					//StrongDealerName = "<font color=\"red\">" + StrongDealerName + "</font>";
					buffer.append("<div onselect=\"this.text.value = '")
						  .append(stuffNo)
						  .append("';$('skuCode').value = '")
						  .append(skuCode)
						  .append("';$('shortCode').value = '")
						  .append(shortCode)
						  .append("';$('standard').value = '")
						  .append(standard)
						  .append("';$('skuUnit').value = '")
						  .append(skuUnit)
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
	 * ά��Я�������������
	 * �˴���ѯֻ��ʾ�п�����
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String getPartInfo4Loan(ActionMapping mapping, ActionForm form,
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
			List partList = PartInfoBo.getInstance().findByStuffWithStock(inputValue);
			
			String stuffNo = "";		
			String skuCode = "";		
			String shortCode = "";		
			String standard = "";		
			String skuUnit = "";	
			String StrongDealerName = "";
			StringBuffer buffer = new StringBuffer();
			for (int i = 0; i < partList.size(); i++) {
				PartInfoForm pif = (PartInfoForm) partList.get(i);
				stuffNo = pif.getStuffNo();
				skuCode = pif.getSkuCode()==null?"":pif.getSkuCode();
				shortCode = pif.getShortCode()==null?"":pif.getShortCode();
				standard = pif.getStandard()==null?"":pif.getStandard();
				skuUnit = pif.getSkuUnit()==null?"":pif.getSkuUnit();
				if(stuffNo.indexOf(inputValue) != -1) {
					//�������ֵ�����ݿ�����ݱȽϺ�,�Ӵ�
					StrongDealerName = stuffNo.replaceAll(inputValue, "<span class=\"boldfont\">" + inputValue + "</span>");
					//StrongDealerName = "<font color=\"red\">" + StrongDealerName + "</font>";
					buffer.append("<div onselect=\"this.text.value = '")
						  .append(stuffNo)
						  .append("';$('skuCode2').value = '")
						  .append(skuCode)
						   .append("';$('skuUnit2').value = '")
						  .append(skuUnit)
						  .append("';$('shortCode2').value = '")
						  .append(shortCode)
						  .append("';$('standard2').value = '")
						  .append(standard)						 
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
	
	public String getToolInfo(ActionMapping mapping, ActionForm form,
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
			List partList = PartInfoBo.getInstance().findToolByStuff(inputValue);
			
			String stuffNo = "";		
			String skuCode = "";		
			String standard = "";		
			String skuUnit = "";	
			String StrongDealerName = "";
			StringBuffer buffer = new StringBuffer();
			for (int i = 0; i < partList.size(); i++) {
				PartInfoForm pif = (PartInfoForm) partList.get(i);
				stuffNo = pif.getStuffNo();
				skuCode = pif.getSkuCode()==null?"":pif.getSkuCode();
				standard = pif.getStandard()==null?"":pif.getStandard();
				skuUnit = pif.getSkuUnit()==null?"":pif.getSkuUnit();
				if(stuffNo.indexOf(inputValue) != -1) {
					//�������ֵ�����ݿ�����ݱȽϺ�,�Ӵ�
					StrongDealerName = stuffNo.replaceAll(inputValue, "<span class=\"boldfont\">" + inputValue + "</span>");
					//StrongDealerName = "<font color=\"red\">" + StrongDealerName + "</font>";
					buffer.append("<div onselect=\"this.text.value = '")
						  .append(stuffNo)
						  .append("';$('skuCode').value = '")
						  .append(skuCode)
						  .append("';$('standard').value = '")
						  .append(standard)
						  .append("';$('skuUnit').value = '")
						  .append(skuUnit)
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
	
	
	
	public String getToolInfo4Loan(ActionMapping mapping, ActionForm form,
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
			List partList = PartInfoBo.getInstance().findToolByStuffWithStock(inputValue);
			
			String stuffNo = "";		
			String skuCode = "";		
			String standard = "";		
			String skuUnit = "";	
			String StrongDealerName = "";
			StringBuffer buffer = new StringBuffer();
			for (int i = 0; i < partList.size(); i++) {
				PartInfoForm pif = (PartInfoForm) partList.get(i);
				stuffNo = pif.getStuffNo();
				skuCode = pif.getSkuCode()==null?"":pif.getSkuCode();
				standard = pif.getStandard()==null?"":pif.getStandard();
				skuUnit = pif.getSkuUnit()==null?"":pif.getSkuUnit();
				if(stuffNo.indexOf(inputValue) != -1) {
					//�������ֵ�����ݿ�����ݱȽϺ�,�Ӵ�
					StrongDealerName = stuffNo.replaceAll(inputValue, "<span class=\"boldfont\">" + inputValue + "</span>");
					//StrongDealerName = "<font color=\"red\">" + StrongDealerName + "</font>";
					buffer.append("<div onselect=\"this.text.value = '")
						  .append(stuffNo)
						  .append("';$('skuCode3').value = '")
						  .append(skuCode)
						  .append("';$('standard3').value = '")
						  .append(standard)
						  .append("';$('skuUnit3').value = '")
						  .append(skuUnit)
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
   public String partInfoList(HttpServletRequest request, ActionForm form) throws Exception{
		String forward = "partInfoList";
		PartInfoForm pif=(PartInfoForm)form;
		PartInfoBo pib = PartInfoBo.getInstance();
		request.setAttribute("partInfoList",pib.list(pif));
		
		return forward;
   }
   
   /**
	* �����Ϣ ����ҳ��
	* @param request HttpServletRequest
	* @param form ������
	* @return ҳ��
	*/
  public String addPartInfo(HttpServletRequest request, ActionForm form) throws Exception {
		String forward = "partInfoAdd";
		
		return forward;
  }
  
  /**
	* �����Ϣ �޸�ҳ��
	* @param request HttpServletRequest
	* @param form ������
	* @return ҳ��
	*/
  public String editPartInfo(HttpServletRequest request, ActionForm form) throws Exception{
		String forward = "partInfoEdit";
		String stuffNo=request.getParameter("ids");
		request.setAttribute("partInfoForm", PartInfoBo.getInstance().find(stuffNo));
		request.setAttribute("stuffNo",stuffNo);
		
		return forward;
  }
  

	/**
	 * У���Ϻ��Ƿ����
	 * @param request HttpServletRequest
	 * @param form  ������
	 * @return ҳ��
	 */	
	public void chkStuffNo(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		try{
			String stuffNo=request.getParameter("stuffNo");
			PartInfoBo pib = PartInfoBo.getInstance();
			String strXml="false";
			if(pib.chkStuffNo(stuffNo)){
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
  public String insertPartInfo(HttpServletRequest request, ActionForm form) throws Exception{
		String forward = "partInfoList";
		PartInfoForm pif=(PartInfoForm)form;
		PartInfoBo.getInstance().add(pif);
		
		return forward;
  }
  

  /**
	* �����Ϣ �޸Ĳ���
	* @param request HttpServletRequest
	* @param form ������
	* @return ҳ��
	*/
  public String updatePartInfo(HttpServletRequest request, ActionForm form) throws Exception{
		String forward = "partInfoList";
		PartInfoForm pif=(PartInfoForm)form;
		PartInfoBo.getInstance().modify(pif);
		
		return forward;
  }
  
  /**
	* ��ҵ��ģ���У��������ѯ��ť���󴥷����¼�
	*	@param request HttpServletRequest
	*	@param form ActionForm
	*	@return String ����forwardҳ��	popupPartStart ͨ�������Ϣ��ѯҳ��	
	*/
  public String popupPartList(HttpServletRequest request, ActionForm form){
		String forward="popupPartStart";
		try{
			PartInfoForm pif=(PartInfoForm)form;
			
			if(pif.getSkuCode()!=null&&!pif.getSkuCode().equals("")){
				BASE64Decoder decoder = new BASE64Decoder(); 
				byte[] b = decoder.decodeBuffer(pif.getSkuCode()); 
				pif.setSkuCode(EscapeUnescape.unescape(EscapeUnescape.unescape(new String(b).toString())).trim());
				
			}
			request.setAttribute("partInfoList",PartInfoBo.getInstance().popList(pif));
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
	}
  
  
  public String popupPartListWithStock(HttpServletRequest request, ActionForm form){
		String forward="popupPartStart";
		try{
			PartInfoForm pif=(PartInfoForm)form;
			//��ʾ���ÿ��
			pif.setStockFlag("A");
			
			if(pif.getSkuCode()!=null&&!pif.getSkuCode().equals("")){
				BASE64Decoder decoder = new BASE64Decoder(); 
				byte[] b = decoder.decodeBuffer(pif.getSkuCode()); 
				pif.setSkuCode(EscapeUnescape.unescape(EscapeUnescape.unescape(new String(b).toString())).trim());
				
			}
			request.setAttribute("partInfoList",PartInfoBo.getInstance().popList(pif));
			request.setAttribute("stockFlag",pif.getStockFlag());
			request.setAttribute("partType",pif.getPartType());
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
	}
  
  public String partInfoViewList(HttpServletRequest request, ActionForm form) throws Exception{
		String forward = "popupPartStart";
		PartInfoForm pif=(PartInfoForm)form;
		request.setAttribute("partInfoList",PartInfoBo.getInstance().popList(pif));
		request.setAttribute("stockFlag",pif.getStockFlag());
		return forward;
  }
  

  /**
	* �����Ϣ �޸Ĳ���
	* @param request HttpServletRequest
	* @param form ������
	* @return ҳ��
	*/
  public String updateStuffNo(HttpServletRequest request, ActionForm form) {
	String forward = "partInfoList";
	try{
		String oldStuffNo=request.getParameter("oldStuffNo");
		String stuffNo=request.getParameter("stuffNo");
		PartInfoBo.getInstance().updateStuffNo(stuffNo,oldStuffNo);
	}catch(Exception e){
		e.printStackTrace();
	}
	return forward;
  }
  
  
  
	
}

