package com.dne.sie.logistic.action;

//Java ������
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

//Java ��չ��
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//��������
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.log4j.Logger;

//�Զ�����
import com.dne.sie.util.action.ControlAction;
import com.dne.sie.common.tools.EscapeUnescape;
import com.dne.sie.common.tools.FormNumberBuilder;
import com.dne.sie.logistic.bo.AsnBo;
import com.dne.sie.logistic.form.AsnForm;
import com.dne.sie.reception.form.SaleInfoForm;



/**
 * ����Action������
 * @author xt
 * @version 1.1.5.6
 * @see AsnAction.java <br>
 */
public class AsnAction  extends ControlAction{

	
	/**
	 * �������б�
	 * @param request HttpServletRequest
	 * @param form ActionForm ������
	 * @return ������ҳ��
	 */
	public String asnReadyList(HttpServletRequest request, ActionForm form){
		String forward = "asnReadyList";
	
		try{
			SaleInfoForm sif=(SaleInfoForm)form;
			//sif.setSaleStatus("M");			//�ѳ���δ����
			

			AsnBo ab = AsnBo.getInstance();
			request.setAttribute("asnList",ab.asnReadyList(sif));
			
			if(sif.getCustomerId()!=null&&!sif.getCustomerId().equals("")){
				request.setAttribute("asnNo",FormNumberBuilder.findNewAsnFormNumber(sif.getCustomerId()));
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
	}
	

	/**
	 * ����select�򷵻ؿͻ���Ϣ
	 * @param request HttpServletRequest
	 * @param form  ������
	 * @return ҳ��
	 */	
	public void getAsnInfo(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) {
		String strXml="false";
		Object[] asnInfo=null;
		StringBuffer sb=new StringBuffer("");
		try{
			String custId=request.getParameter("custId");
			AsnBo ab = AsnBo.getInstance();
			asnInfo=ab.getAsnInfo(custId);
			
			
			if(asnInfo!=null){
				strXml="true";
				if(asnInfo!=null){
					sb.append("<shippingAddress>").append("".equals(asnInfo[0])?"null":EscapeUnescape.escape((String)asnInfo[0])).append("</shippingAddress>")
					.append("<linkman>").append("".equals(asnInfo[1])?"null":EscapeUnescape.escape((String)asnInfo[1])).append("</linkman>")
					.append("<customerName>").append("".equals(asnInfo[2])?"null":EscapeUnescape.escape((String)asnInfo[2])).append("</customerName>")
					.append("<customerPhone>").append("".equals(asnInfo[3])?"null":(String)asnInfo[3]).append("</customerPhone>")
					.append("<customerId>").append(custId).append("</customerId>");
				}
			}
			
		}catch(Exception e){
			strXml="false";
			e.printStackTrace();
		}finally{
			try{
				PrintWriter writer = response.getWriter();			
				response.setContentType("text/xml");					
				response.setHeader("Cache-Control", "no-cache");      
				writer.println("<xml>");
				writer.println("<flag>"+strXml+"</flag>");
				if(asnInfo!=null){
					writer.println(sb.toString());
					
				}
				writer.println("</xml>");
				writer.flush();
				writer.close();
			}catch(Exception e1){
				e1.printStackTrace();
			}
		}
	}
	
	
	/**
	* ����ȷ��
	* @param request HttpServletRequest
	* @param form ������
	* @return ҳ��
	*/
	public String shipConfirm(HttpServletRequest request, ActionForm form){
		int tag=-1;
		String forward = "resultMessage";
		try{
			HttpSession session = request.getSession();
			Long userId = (Long) session.getAttribute("userId");
			
			String chkIds=request.getParameter("chkId");
			String asnNo=request.getParameter("asnNo");
			String shippingAddress=request.getParameter("shippingAddress");
			String linkman=request.getParameter("linkman");
			String customerName=request.getParameter("customerName");
			String customerPhone=request.getParameter("customerPhone");
			String shippingRemark=request.getParameter("shippingRemark");
			String customerId=request.getParameter("customerId");
			String asnType = request.getParameter("asnType");
			
			AsnForm af=new AsnForm();
			af.setAsnNo(asnNo);
			af.setShippingAddress(shippingAddress);
			af.setLinkman(linkman);
			af.setCustomerId(customerId);
			af.setCustomerName(customerName);
			af.setCustomerPhone(customerPhone);
			af.setShippingRemark(shippingRemark);
			af.setCreateBy(userId);
			af.setCreateDate(new Date());
			af.setShippingDate(af.getCreateDate());
			af.setAsnType(asnType);
			
			AsnBo ab = AsnBo.getInstance();
			tag=ab.saleConsign(chkIds,af);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			request.setAttribute("tag", tag + "");
			request.setAttribute("businessFlag", "shipConfirm");
		}
		return forward;
	}

	
	
}
