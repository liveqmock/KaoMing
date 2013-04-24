package com.dne.sie.maintenance.action;

//Java 基础类
//import java.util.List;

//Java 扩展类
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpServletResponse;


//第三方类
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import sun.misc.BASE64Decoder;
//import org.apache.struts.action.ActionMapping;

//自定义类
import com.dne.sie.util.action.ControlAction;
import com.dne.sie.common.tools.DicInit;
import com.dne.sie.common.tools.EscapeUnescape;
import com.dne.sie.maintenance.bo.CustomerInfoBo;
import com.dne.sie.maintenance.bo.PartInfoBo;
import com.dne.sie.maintenance.form.CustomerInfoForm;
import com.dne.sie.maintenance.form.PartInfoForm;


/**
 * 客户信息Action处理类
 * @author xt
 * @version 1.1.5.6
 */
public class CustomerInfoAction extends ControlAction {
	
	
	/**
	* 客户表信息 列表页面
	* @param request HttpServletRequest
	* @param form 表单数据
	* @return 页面
	*/
   public String customerList(HttpServletRequest request, ActionForm form) throws Exception{
		String forward = "customerList";
		CustomerInfoForm pif=(CustomerInfoForm)form;
		CustomerInfoBo pib = CustomerInfoBo.getInstance();
		request.setAttribute("customerList",pib.list(pif));
		
		return forward;
   }
   
   /**
	* 客户表信息 新增页面
	* @param request HttpServletRequest
	* @param form 表单数据
	* @return 页面
	*/
  public String customerInit(HttpServletRequest request, ActionForm form) throws Exception {
		String forward = "customerInit";
		
		request.setAttribute("Rnd",request.getParameter("Rnd"));
		return forward;
  }
  
  /**
	* 客户表信息 修改页面
	* @param request HttpServletRequest
	* @param form 表单数据
	* @return 页面
	*/
  public String customerEdit(HttpServletRequest request, ActionForm form) {
		String forward = "customerEdit";
		try{
			String sysId=request.getParameter("ids");
			request.setAttribute("customerInfoForm", CustomerInfoBo.getInstance().find(sysId));
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
  }

  /**
	* 客户表信息 插入操作
	* @param request HttpServletRequest
	* @param form 表单数据
	* @return 页面
	*/
  public String insertCustomer(HttpServletRequest request, ActionForm form) {
		String forward = "resultMessage";
		try{
			CustomerInfoForm pif=(CustomerInfoForm)form;
			CustomerInfoBo cibo = CustomerInfoBo.getInstance();
			if(cibo.chkCustId(pif.getCustomerId())){
				int tag=cibo.add(pif);
				request.setAttribute("tag", tag + "");
				request.setAttribute("businessFlag", "saveCustomer");
				
			}else{
				request.setAttribute("idRepeat", "idRepeat");
				forward="customerInit";
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
  }
  
  
  public void insertCustomerWithTurning(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		
		try{
			String strXml="false";
			CustomerInfoForm pif=(CustomerInfoForm)form;
			
			String customername= pif.getCustomerName(); 
			String linkman=      pif.getLinkman(); 
			String provincename= pif.getProvinceName(); 
			String cityname=     pif.getCityName(); 
			String address     = pif.getAddress();
			
			pif.setCustomerName(EscapeUnescape.unescape(customername));
			pif.setLinkman(EscapeUnescape.unescape(linkman));
			pif.setProvinceName(EscapeUnescape.unescape(provincename));
			pif.setCityName(EscapeUnescape.unescape(cityname));
			pif.setAddress(EscapeUnescape.unescape(address));
			pif.setCreateBy((Long)request.getSession().getAttribute("userId"));
			pif.setCreateDate(new Date());
			
			CustomerInfoBo cibo = CustomerInfoBo.getInstance();
			if(cibo.chkCustId(pif.getCustomerId())){
				int tag=cibo.add(pif);
				if(tag!=-1){
					strXml=pif.getCustomerId()
							+DicInit.SPLIT3+customername
							+DicInit.SPLIT3+linkman
							+DicInit.SPLIT3+(pif.getPhone())
							+DicInit.SPLIT3+pif.getFax()
							+DicInit.SPLIT3+pif.getMobile()
							+DicInit.SPLIT3+provincename
							+DicInit.SPLIT3+cityname
							+DicInit.SPLIT3+address;
				}	
			}
			
			PrintWriter writer = response.getWriter();			
			response.setContentType("text/xml");					
			response.setHeader("Cache-Control", "no-cache");      
			writer.println("<xml>");
			
			writer.println("<ifUse>"+strXml+"</ifUse>");
		
			writer.println("</xml>");
			writer.flush();
			writer.close();
		
		}catch(Exception e){
			e.printStackTrace();
		}
	
	}

  /**
	* 客户表信息 修改操作
	* @param request HttpServletRequest
	* @param form 表单数据
	* @return 页面
	*/
  public String updateCustomer(HttpServletRequest request, ActionForm form) {
		String forward = "resultMessage";
		try{
			CustomerInfoForm pif=(CustomerInfoForm)form;
			CustomerInfoBo cibo = CustomerInfoBo.getInstance();
			int tag=cibo.modify(pif);
			request.setAttribute("tag", tag + "");
			request.setAttribute("businessFlag", "saveCustomer");
		
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
  }
  

  /**
	* 客户表信息 删除操作
	* @param request HttpServletRequest
	* @param form 表单数据
	* @return 页面
	*/
  public String deleteCustomer(HttpServletRequest request, ActionForm form) {
		String forward = "resultMessage";
		try{
			String sysId=request.getParameter("ids");
			int tag=CustomerInfoBo.getInstance().delete(sysId);
			request.setAttribute("tag", tag + "");
			request.setAttribute("businessFlag", "deleteCustomer");
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
  }

	/**
	 * 校验用户id是否存在
	 * @param request HttpServletRequest
	 * @param form  表单数据
	 * @return 页面
	 */	
	public void ajaxChkId(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		
		try{
			String custId=request.getParameter("custId");
			CustomerInfoBo ubo = CustomerInfoBo.getInstance();
			String strXml="false";
			if(ubo.chkCustId(custId)){
				strXml="true";
			}
		
			PrintWriter writer = response.getWriter();			
			response.setContentType("text/xml");					
			response.setHeader("Cache-Control", "no-cache");      
			writer.println("<xml>");
			
			writer.println("<ifUse>"+strXml+"</ifUse>");
		
			writer.println("</xml>");
			writer.flush();
			writer.close();
		
		}catch(Exception e){
			e.printStackTrace();
		}
	
	}
	

	  /**
		* 在业务模块中，点击“查询按钮”后触发的事件
		*	@param request HttpServletRequest
		*	@param form ActionForm
		*	@return String 返回forward页面	popupPartStart 通用零件信息查询页面	
		*/
	  public String popupCustList(HttpServletRequest request, ActionForm form){
			String forward="popupCustStart";
			try{
				CustomerInfoForm cif=(CustomerInfoForm)form;
				String flag=request.getParameter("flag");
				if(!"query".equals(flag)){
					if(cif.getCustomerName()!=null&&!cif.getCustomerName().equals("")){
						BASE64Decoder decoder = new BASE64Decoder(); 
						byte[] b = decoder.decodeBuffer(cif.getCustomerName()); 
						cif.setCustomerName(EscapeUnescape.unescape(EscapeUnescape.unescape(new String(b).toString())).trim());
						
					}
				}
				request.setAttribute("custInfoList",CustomerInfoBo.getInstance().list(cif));
				
				
			}catch(Exception e){
				e.printStackTrace();
			}
			return forward;
		}
	  
	  
	  /**
		 * 得到所有客户信息
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public String getCustInfo(ActionMapping mapping, ActionForm form,
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
				List custList = CustomerInfoBo.getInstance().getCustomerListByName(inputValue);
				
				String customerId = "";		
				String customerName = "";		
				String linkman = "";		
				String phone = "";		
				String cityName = "";	
				String fax = "";
				String address = "";
				String mobile= "";
				String provinceName= "";
				
				String StrongDealerName = "";
				
				StringBuffer buffer = new StringBuffer();
				for (int i = 0; i < custList.size(); i++) {
					Object[] obj = (Object[]) custList.get(i);
					if("TWKM".equals(customerId)){
						continue;
					}
					customerId = obj[0].toString();
					customerName = obj[1].toString();
					linkman = obj[2]==null?"":obj[2].toString();
					phone = obj[3]==null?"":obj[3].toString();
					cityName = obj[4]==null?"":obj[4].toString();
					fax = obj[5]==null?"":obj[5].toString();
					address = obj[6]==null?"":obj[6].toString();
					mobile = obj[7]==null?"":obj[7].toString();
					provinceName = obj[8]==null?"":obj[8].toString();
					
					
					if(customerName.indexOf(inputValue) != -1) {
						//把输入的值和数据库的数据比较后,加粗
						StrongDealerName = customerName.replaceAll(inputValue, "<span class=\"boldfont\">" + inputValue + "</span>");
						
						buffer.append("<div onselect=\"this.text.value = '")
							  .append(customerName)
							  .append("';$('customerId').value = '")
							  .append(customerId)
							  .append("';$('linkman').value = '")
							  .append(linkman)
							  .append("';$('phone').value = '")
							  .append(phone)
							  .append("';$('cityName').value = '")
							  .append(cityName)
							  .append("';$('fax').value = '")
							  .append(fax)
							  .append("';$('shippingAddress').value = '")
							  .append(address)
							  .append("';$('address').value = '")
							  .append(address)
							  .append("';$('mobile').value = '")
							  .append(mobile)
							  .append("';$('provinceName').value = '")
							  .append(provinceName)
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

