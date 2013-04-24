package com.dne.sie.reception.action;

//Java ������
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.dne.sie.common.exception.IllegalPoException;
import com.dne.sie.common.exception.VersionException;
import com.dne.sie.common.tools.CommonSearch;
import com.dne.sie.common.tools.DicInit;
import com.dne.sie.common.tools.EscapeUnescape;
import com.dne.sie.common.tools.FormNumberBuilder;
import com.dne.sie.common.tools.Operate;
import com.dne.sie.maintenance.bo.CustomerInfoBo;
import com.dne.sie.reception.bo.SaleInfoBo;
import com.dne.sie.reception.form.SaleDetailForm;
import com.dne.sie.reception.form.SaleInfoForm;
import com.dne.sie.reception.form.SalePaymentForm;
import com.dne.sie.repair.bo.RepairHandleBo;
import com.dne.sie.repair.bo.RepairListBo;
import com.dne.sie.repair.form.RepairFeeInfoForm;
import com.dne.sie.repair.form.RepairPartForm;
import com.dne.sie.repair.form.RepairSearchForm;
import com.dne.sie.support.userRole.action.RoleAction;
import com.dne.sie.util.action.ControlAction;



/**
 * �������Action������
 * @author xt
 * @version 1.1.5.6
 * @see RoleAction.java <br>
 */
public class SaleInfoAction extends ControlAction{

   
	/**
	 * ������۳�ʼҳ��
	 * @param request HttpServletRequest
	 * @param form ������
	 * @return ҳ��
	 */
	public String saleRegistert(HttpServletRequest request, ActionForm form){
	
		return  "saleRegistert";
	}
	

	/**
	 * ѯ��ȷ��
	 * @param request HttpServletRequest
	 * @param form ActionForm ������
	 * @return  resultMessage 
	 */
	public String inquiryConfirmed(HttpServletRequest request,ActionForm form) {
		String forward = "resultMessage";
		int tag=-1;
		try {
			HttpSession session = request.getSession();
			Long userId = (Long) session.getAttribute("userId");

			//���ձ��ύ����
			String[] stuffNoT = request.getParameterValues("stuffNoT"); //�Ϻ�	
			String[] skuCodeT = request.getParameterValues("skuCodeT"); //SKU��
			String[] skuUnitT = request.getParameterValues("skuUnitT"); //��λ
			String[] partNumT = request.getParameterValues("partNumT");//����
			String[] modelCodeT = request.getParameterValues("modelCodeT");//����
			String[] modelSerialNoT = request.getParameterValues("modelSerialNoT");//�������
			
			SaleInfoBo sibo = SaleInfoBo.getInstance();
			ArrayList dataList = new ArrayList();
			
			//������Ϣ�� TD_SALES_INFO
			SaleInfoForm sif = (SaleInfoForm) form;
			String saleNo = FormNumberBuilder.getNewSaleFormNumber(sif.getCustomerId());
			//���۵����
			sif.setSaleNo(saleNo);
			sif.setCreateDate(new Date());
			sif.setCreateBy(userId);
			sif.setSaleStatus("A");
			sif.setWarrantyType("O");

			//Set Service form
			RepairSearchForm rsf  = null;
			if(sif.getRepairNo()!=null && sif.getRepairNo().intValue()!=0){
				//�й���Job��������FK
				rsf  = RepairHandleBo.getInstance().findById(sif.getRepairNo());
				if(rsf!=null){
					rsf.setSaleNo(sif.getSaleNo());
					rsf.setUpdateDate(new Date());
					rsf.setUpdateBy(userId);
					dataList.add(new Object[]{rsf,"u"});
					
					sif.setWarrantyType(rsf.getWarrantyType());
				}
			}
			
			int count=0;
			CommonSearch cs =CommonSearch.getInstance();
			//������ϸ TD_SALES_DETAIL
			for (int i = 0; stuffNoT!=null&&i < stuffNoT.length; i++) {
				SaleDetailForm psf = new SaleDetailForm();
				if(cs.getPartInfo(stuffNoT[i])==null){
					throw new IllegalPoException(stuffNoT[i]);
				}
				psf.setSaleNo(saleNo);
				
				if(rsf!=null){
					psf.setOrderType("R");
				}else{
					psf.setOrderType("S");
				}
				psf.setWarrantyType(sif.getWarrantyType());
				psf.setStuffNo(stuffNoT[i]);
				psf.setSkuCodeT(skuCodeT[i]);
				psf.setSkuUnit(skuUnitT[i]);
				psf.setModelCode(modelCodeT[i]);
				psf.setModelSerialNo(modelSerialNoT[i]);
				psf.setPartStatus("A");
				psf.setPartNum(new Integer(partNumT[i]));
				count+=new Integer(partNumT[i]);
				psf.setCreateBy(userId);
				psf.setCreateDate(new Date());
				
				dataList.add(new Object[]{psf,"i"});

			}
			sif.setPartNum(count);
			dataList.add(0,new Object[]{sif,"i"});
			
			tag=sibo.sellRegisterAdd(dataList);
			request.setAttribute("tempData", saleNo);
			
		}catch(IllegalPoException e1){
			request.setAttribute("errStuffNo", e1.getMessage());
			forward="IllegalPoException";	
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			request.setAttribute("tag", tag + "");
			request.setAttribute("businessFlag", "inquiryConfirmed");
		}
		return forward;
	}
	

	/**
	 * ѯ�۵���ϸ
	 * @param request HttpServletRequest
	 * @param form ActionForm ������
	 * @return ���۵���ϸҳ��
	 */
	public String inquiryDetail(HttpServletRequest request,ActionForm form) {
		String forward = "inquiryDetail";
		try {
			String saleNo = request.getParameter("saleNo");
			String flag = request.getParameter("flag");
			
			//������Ϣ��
			SaleInfoBo bo = SaleInfoBo.getInstance();
			SaleInfoForm sif = bo.findById(saleNo);
			if(!"list".equals(flag)&& sif.getSaleStatus().equals("J")){
				return "roleFalse";
			}
			if(!"list".equals(flag)&& sif.getSaleStatus().equals("B")){
				return "saleCheckList";
			}
			request.setAttribute("detailList", bo.detailList(saleNo));
			request.setAttribute("salesInfoForm", sif);
			
			
			if("list".equals(flag)){
				forward="saleAskConfirmDetail";
			}else if("check".equals(flag)||"review".equals(flag)){
				if(sif.getRepairNo()!=null&&sif.getRepairNo().longValue()!=0){
					request.setAttribute("repairFeeList", RepairListBo.getInstance().getRepairFeeList(sif.getRepairNo()));
				}
				
				if("check".equals(flag)){
					forward="saleCheckDetail";
				}else{
					forward="saleCheckDetailEnd";
				}
			}else if("mergeComplete".equals(flag)){
				forward="saleCheckDetail";
				request.setAttribute("tag", "mergeComplete");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return forward;
	}
	

	/**
	 * ѯ�ۻظ��б�
	 * @param request HttpServletRequest
	 * @param form ActionForm ������
	 * @return ���۵���ϸҳ��
	 */
	public String saleAskConfirmList(HttpServletRequest request, ActionForm form){
		String forward = "saleAskConfirmList";
	
		try{
			SaleInfoForm pif=(SaleInfoForm)form;
			//��̨ѯ����
			pif.setSaleStatus("ask");
			SaleInfoBo pib = SaleInfoBo.getInstance();
			request.setAttribute("saleList",pib.list(pif));
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
	}
	

	/**
	 * ѯ�ۻظ�ȷ��
	 * @param request HttpServletRequest
	 * @param form ActionForm ������
	 * @return ���۵���ϸҳ��
	 */
	public String saleAskConfirm(HttpServletRequest request, ActionForm form){
		String forward = "resultMessage";
		int tag=-1;
		try{
			HttpSession session = request.getSession();
			Long userId = (Long) session.getAttribute("userId");
			
			String saleNo = request.getParameter("saleNo");
			String exchangeRate = request.getParameter("exchangeRate");
			//save:�ݴ�,confirm:�ظ�ȷ��,cancel:ȡ��
			String flag = request.getParameter("flag");
			
			String[] saleDetailId = request.getParameterValues("saleDetailId"); //pk
			String[] purchaseDollar = request.getParameterValues("purchaseDollar"); //tw����	
			String[] deliveryTimeStart = request.getParameterValues("deliveryTimeStart"); //������ʼʱ��
			String[] deliveryTimeEnd = request.getParameterValues("deliveryTimeEnd"); //��������ʱ��
			String[] warrantyType = request.getParameterValues("warrantyType");
			String[][] para = {saleDetailId,purchaseDollar,deliveryTimeStart,deliveryTimeEnd,warrantyType};
			
			SaleInfoBo pib = SaleInfoBo.getInstance();
			tag=pib.saleAskSave(para,flag,userId,saleNo,exchangeRate);
			
			request.setAttribute("tempData", saleNo);
			if("save".equals(flag)||tag==-1){
				request.setAttribute("businessFlag", "saleAskSave");
			}else if("confirm".equals(flag)){
				request.setAttribute("businessFlag", "saleCheckList");
			}else if("cancel".equals(flag)){
				request.setAttribute("businessFlag", "saleAskCancel");
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			request.setAttribute("tag", tag + "");
		}
		return forward;
	}

	/**
	 * ���ۺ����б�
	 * @param request HttpServletRequest
	 * @param form ActionForm ������
	 * @return ���۵���ϸҳ��
	 */
	public String saleCheckList(HttpServletRequest request, ActionForm form){
		String forward = "saleCheckList";
	
		try{
			String flag = request.getParameter("flag");
			SaleInfoForm sif=(SaleInfoForm)form;
			
			SaleInfoBo sib = SaleInfoBo.getInstance();
			
			
			if("merge".equals(flag)){
				sif.setSaleStatus("merge");
				forward="saleCheckListPop";
				request.setAttribute("salesInfoForm", sif);
			}
			
			request.setAttribute("saleList",sib.list(sif));
			if(sif.getSaleStatus().equals("review")||sif.getSaleStatus().equals("Z")){
				forward = "saleRecheckList";
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
	}

	/**
	 * ���ۺ���ȷ��
	 * @param request HttpServletRequest
	 * @param form ActionForm ������
	 * @return ���۵���ϸҳ��
	 */
	public String saleCheckConfirm(HttpServletRequest request, ActionForm form){
		String forward = "resultMessage";
		int tag=-1;
		SaleInfoForm siForm = (SaleInfoForm) form;
		SaleInfoBo sib = SaleInfoBo.getInstance();
		String flag = request.getParameter("flag");
		
		try{
			HttpSession session = request.getSession();
			Long userId = (Long) session.getAttribute("userId");
			
			SaleInfoForm sif=sib.findById(siForm.getSaleNo());
			
			
			String[] detailIds = request.getParameterValues("detailIds"); 
			String[] partNumT = request.getParameterValues("partNumT"); 
			String[] purchasePriceT       = request.getParameterValues("purchasePriceT");            //����							
			String[] vatT                 = request.getParameterValues("vatT");                      //������ֵ˰_���						
			String[] tariffAmountT        = request.getParameterValues("tariffAmountT");             //��˰_���					
			String[] tariffRatT           = request.getParameterValues("tariffRatT");          		 //��˰_˰��	
			String[] customsChargesRealT  = request.getParameterValues("customsChargesRealT");       //�����
			String[] domesticFreightPlanT = request.getParameterValues("domesticFreightPlanT");      //�����˷�_�ƻ�			
			String[] domesticFreightRealT = request.getParameterValues("domesticFreightRealT");      //�����˷�_ʵ��			
			String[] costPlanT            = request.getParameterValues("costPlanT");                 //�ɱ�_�ƻ�					
			String[] costRealT            = request.getParameterValues("costRealT");                 //�ɱ�_ʵ��					
			String[] perQuoteT            = request.getParameterValues("totalQuoteT");               //���ۣ�����				
			//String[] salePerPriceT        = request.getParameterValues("saleTotalPriceT");           //ʵ�����ۼۣ�����	
			String[] profitPlanT          = request.getParameterValues("profitPlanT");               //����_�ƻ�					
			//String[] profitRealT          = request.getParameterValues("profitRealT");               //����_ʵ��
			
			String[] version = request.getParameterValues("version"); 
			
			
			//repair fee
			String repairmanNum = request.getParameter("repairmanNum");
			String workingHours = request.getParameter("workingHours");
			String ticketsAllCosts = request.getParameter("ticketsAllCosts");
			String laborCosts = request.getParameter("laborCosts");
			String repairQuote = request.getParameter("repairQuote");
			String repairProfit = request.getParameter("repairProfit");
			
			
			
			String[] ids = null;
			ArrayList dataList=new ArrayList();
			if(detailIds!=null&&detailIds.length>0){
				ids = new String[detailIds.length];
				for (int i = 0; i < detailIds.length; i++) {
					SaleDetailForm sdf = sib.findByDetailId(new Long(detailIds[i]));
					ids[i]=detailIds[i]+DicInit.SPLIT1+(Integer.parseInt(version[i])+1);
					
					if("saveEnd".equals(flag)||"confirmEnd".equals(flag)){
						sdf.setTariffAmount(SaleInfoBo.toFloat(tariffAmountT[i],flag));
						sdf.setTariffRat(SaleInfoBo.toFloat(tariffRatT[i],flag));
						sdf.setVat(SaleInfoBo.toFloat(vatT[i],flag));
						sdf.setCustomsChargesReal(SaleInfoBo.toFloat(customsChargesRealT[i],flag));
						sdf.setDomesticFreightReal(SaleInfoBo.toFloat(domesticFreightRealT[i],flag));
						sdf.setCostReal(SaleInfoBo.toFloat(costRealT[i],flag));
					}else{
						sdf.setPartNum(new Integer(partNumT[i]));
						sdf.setPurchasePrice(SaleInfoBo.toFloat(purchasePriceT[i],flag));
						sdf.setVat(SaleInfoBo.toFloat(vatT[i],flag));
						sdf.setTariffAmount(SaleInfoBo.toFloat(tariffAmountT[i],flag));
						sdf.setTariffRat(SaleInfoBo.toFloat(tariffRatT[i],flag));
						sdf.setCustomsChargesReal(SaleInfoBo.toFloat(customsChargesRealT[i],flag));
						sdf.setDomesticFreightPlan(SaleInfoBo.toFloat(domesticFreightPlanT[i],flag));
						sdf.setCostPlan(SaleInfoBo.toFloat(costPlanT[i],flag));
						sdf.setPerQuote(SaleInfoBo.toFloat(perQuoteT[i],flag));
						sdf.setProfitPlan(SaleInfoBo.toFloat(profitPlanT[i],flag));
					}
					
					sdf.setUpdateBy(userId);
					sdf.setUpdateDate(new Date());
					
					if("save".equals(flag)){	//�ݴ�
						
					}else if("confirm".equals(flag)){	//����ȷ��
						//�����߼������޸�
						sdf.setPartStatus("F");
						if(i==0) sif.setSaleStatus("F");
					}else if("cancel".equals(flag)){	//ȡ��
						sdf.setDelFlag(1);
						if(i==0) sif.setDelFlag(1);
						
					}else if("confirmEnd".equals(flag)){	//�Ѹ���
						sdf.setPartStatus("Z");
						if(i==0) sif.setSaleStatus("Z");
					}
					
					dataList.add(sdf);
				}
			}else if(!"save".equals(flag)){
				sif.setSaleStatus("W");	//�����
			}

			RepairFeeInfoForm rfi = RepairListBo.getInstance().getRepairFeeInfo(sif.getRepairNo());
			if(rfi != null){
				if(repairmanNum!=null&&!repairmanNum.isEmpty()) rfi.setRepairmanNum(new Integer(repairmanNum));
				if(workingHours!=null&&!workingHours.isEmpty()) rfi.setWorkingHours(new Integer(workingHours));
				if(ticketsAllCosts!=null&&!ticketsAllCosts.isEmpty()) rfi.setTicketsAllCosts(new Double(ticketsAllCosts));
				if(laborCosts!=null&&!laborCosts.isEmpty()) rfi.setLaborCosts(new Double(laborCosts));
				if(repairQuote!=null&&!repairQuote.isEmpty()) rfi.setRepairQuote(new Double(repairQuote));
				if(repairProfit!=null&&!repairProfit.isEmpty()) rfi.setRepairProfit(new Double(repairProfit));
				
				Double[] costs = RepairHandleBo.getInstance().getRepairCosts(rfi.getRepairmanNum(), rfi.getWorkingHours(),
						rfi.getTicketsAllCosts(), rfi.getLaborCosts());
				rfi.setTravelCosts(costs[0]);
				rfi.setTaxes(costs[1]);
				rfi.setTotalCost(costs[2]);
				rfi.setUpdateBy(userId);
				rfi.setUpdateDate(new Date());
				dataList.add(rfi);
			}
			sif.setPartNum(siForm.getPartNum());
			sif.setPurchasePrice(siForm.getPurchasePrice());
			sif.setVat(siForm.getVat());
			sif.setTariffAmount(siForm.getTariffAmount());
			sif.setCustomsChargesReal(siForm.getCustomsChargesReal());
			sif.setDomesticFreightPlan(siForm.getDomesticFreightPlan());
			sif.setDomesticFreightReal(siForm.getDomesticFreightReal());
			sif.setCostPlan(siForm.getCostPlan());
			sif.setCostReal(siForm.getCostReal());
			sif.setTotalQuote(siForm.getTotalQuote());
			//sif.setSaleTotalPrice(siForm.getSaleTotalPrice());
			//sif.setRepairFeePlan(siForm.getRepairFeePlan());
			if(rfi != null){
				if(rfi.getTotalCost()!=null){
					sif.setRepairFeePlan(new Float(rfi.getTotalCost()));
				}
				if(rfi.getRepairQuote()!=null){
					sif.setRepairQuote(new Float(rfi.getRepairQuote()));
				}
			}
			sif.setRepairFeeReal(siForm.getRepairFeeReal());
			sif.setProfitPlan(siForm.getProfitPlan());
			sif.setProfitReal(siForm.getProfitReal());
			sif.setUpdateBy(userId);
			sif.setUpdateDate(new Date());
			dataList.add(0,sif);
			

			tag=sib.saleCheckConfirm(dataList,ids,flag);
			

		}catch(VersionException ve){
			  forward="versionErr";
		}catch(Exception e){
			request.setAttribute("salesInfoForm", siForm);
			try{
				request.setAttribute("detailList", sib.detailList(siForm.getSaleNo()));
			}catch(Exception e1){e1.printStackTrace();}
			
			forward="saleCheckDetail";
			e.printStackTrace();
		}finally{
			request.setAttribute("tag", tag + "");
			
			if("confirm".equals(flag)&&tag==1) request.setAttribute("businessFlag", "saleCheckConfirm");
			else if("cancel".equals(flag)) request.setAttribute("businessFlag", "saleInfoCancel");
			else if("save".equals(flag)) request.setAttribute("businessFlag", "saleCheckSave");
			else request.setAttribute("businessFlag",flag);
			request.setAttribute("tempData", siForm.getSaleNo());
		}
		return forward;
	}

	/**
	 * ���۵��б�
	 * @param request HttpServletRequest
	 * @param form ActionForm ������
	 * @return ���۵���ϸҳ��
	 */
	public String saleInfoList(HttpServletRequest request, ActionForm form){
		String forward = "saleInfoList";
	
		try{
			SaleInfoForm pif=(SaleInfoForm)form;
			
			SaleInfoBo pib = SaleInfoBo.getInstance();
			request.setAttribute("saleList",pib.list(pif));
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
	}

	/**
	 * ���۵���ϸ
	 * @param request HttpServletRequest
	 * @param form ActionForm ������
	 * @return ���۵���ϸҳ��
	 */
	public String saleInfoDetail(HttpServletRequest request,ActionForm form) {
		String forward = "saleInfoDetail";
		try {
			String saleNo = request.getParameter("saleNo");
			//������Ϣ��
			SaleInfoBo bo = SaleInfoBo.getInstance();
			SaleInfoForm sif = bo.findById(saleNo);
			
			sif.setOrderMonth(Operate.trimDate(sif.getCreateDate()));
			
			
			request.setAttribute("salePayedList", bo.getSalePayedList(saleNo));
			request.setAttribute("detailList", bo.detailList(saleNo));
			
			request.setAttribute("salesInfoForm", sif);
			request.setAttribute("flag", request.getParameter("flag"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return forward;
	}

	/**
	 * �ϲ����۵������ϸ
	 * @param request HttpServletRequest
	 * @param form  ������
	 * @return ҳ��
	 */	
	public void mergeDetail(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) {
		String strXml="false";
		try{
			String saleNoF=request.getParameter("saleNoF");
			String saleNoT=request.getParameter("saleNoT");
			String customerId=request.getParameter("customerId");
			SaleInfoBo sib = SaleInfoBo.getInstance();
			int tag=sib.mergeDetailConfirm(saleNoF,saleNoT,customerId);
			
			if(tag>0) strXml="true";
		}catch(Exception e){
			strXml="false";
			e.printStackTrace();
		}finally{
			try{
				PrintWriter writer = response.getWriter();			
				response.setContentType("text/xml");					
				response.setHeader("Cache-Control", "no-cache");      
				writer.println("<xml>");
				writer.println("<ifUse>"+strXml+"</ifUse>");
				writer.println("</xml>");
				writer.flush();
				writer.close();
			}catch(Exception e1){
				e1.printStackTrace();
			}
		}
	}
	
	

	/**
	 * ����һ�����۵����ѽ��
	 * @param request HttpServletRequest
	 * @param form  ������
	 * @return ҳ��
	 */	
	public void addPayment(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) {
		String strXml="false";
		Float allPayAmount=null;
		SalePaymentForm spf=new SalePaymentForm();
		
		try{
			HttpSession session = request.getSession();
			Long userId = (Long) session.getAttribute("userId");

			String totalQuote=request.getParameter("totalQuote");
			String payAmount=request.getParameter("payAmount");
			String clientBank=EscapeUnescape.unescape(request.getParameter("clientBank"));
			String paymentCardNo=EscapeUnescape.unescape(request.getParameter("paymentCardNo"));
			
			
			spf.setSaleNo(request.getParameter("saleNo"));
			spf.setPayAmount(new Float(payAmount));
			spf.setPayType(request.getParameter("payType"));
			spf.setPayVariety(request.getParameter("payVariety"));
			spf.setDataType(request.getParameter("dataType"));
			spf.setClientBank(clientBank);
			spf.setPaymentCardNo(paymentCardNo);
			spf.setSaleTotalPrice(new Float(totalQuote));
			spf.setCreateDate(new Date());
			spf.setCreateBy(userId);
			
			SaleInfoBo sib = SaleInfoBo.getInstance();
			allPayAmount=sib.addPayment(spf);
			if(allPayAmount!=null){
				strXml="true";
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				PrintWriter writer = response.getWriter();			
				response.setContentType("text/xml");					
				response.setHeader("Cache-Control", "no-cache");      
				writer.println("<xml>");
				writer.println("<flag>"+strXml+"</flag>");
				writer.println("<allPayAmount>"+allPayAmount+"</allPayAmount>");
				writer.println("<paymentDate>"+spf.getCreateDate().toLocaleString()+"</paymentDate>");
				writer.println("<payAmount>"+spf.getPayAmount()+"</payAmount>");
				writer.println("<payVariety>"+EscapeUnescape.escape((DicInit.getSystemName("PAY_VARIETY", spf.getPayVariety())))+"</payVariety>");
				
				writer.println("<payType>"+EscapeUnescape.escape((DicInit.getSystemName("PAY_TYPE", spf.getPayType())))+"</payType>");
				writer.println("</xml>");
				writer.flush();
				writer.close();
			}catch(Exception e1){
				e1.printStackTrace();
			}
		}
	}
	

	/**
	 * ����һ�����۵���Ʊ���
	 * @param request HttpServletRequest
	 * @param form  ������
	 * @return ҳ��
	 */	
	public void addInvoice(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) {
		String strXml="false";
		Float allPayAmount=null;
		SalePaymentForm spf=new SalePaymentForm();
		
		try{
			HttpSession session = request.getSession();
			Long userId = (Long) session.getAttribute("userId");

			String billingMoney=request.getParameter("billingMoney");
			
			spf.setSaleNo(request.getParameter("saleNo"));
			spf.setStuffNo(request.getParameter("stuffNo"));
			spf.setBillingMoney(new Float(billingMoney));
			spf.setInvoiceType(request.getParameter("invoiceType"));
			spf.setDataType(request.getParameter("dataType"));
			spf.setInvoiceNo(request.getParameter("invoiceNo"));
			
			spf.setCreateDate(new Date());
			spf.setCreateBy(userId);
			
			SaleInfoBo sib = SaleInfoBo.getInstance();
			allPayAmount=sib.addPayment(spf);
			if(allPayAmount!=null){
				strXml="true";
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				PrintWriter writer = response.getWriter();			
				response.setContentType("text/xml");					
				response.setHeader("Cache-Control", "no-cache");      
				writer.println("<xml>");
				writer.println("<flag>"+strXml+"</flag>");
				writer.println("<allPayAmount>"+allPayAmount+"</allPayAmount>");
				writer.println("<paymentDate>"+spf.getCreateDate().toLocaleString()+"</paymentDate>");
				writer.println("<billingMoney>"+spf.getBillingMoney()+"</billingMoney>");
				writer.println("<stuffNo>"+spf.getStuffNo()+"</stuffNo>");
				writer.println("<payType>"+EscapeUnescape.escape(DicInit.getSystemName("INVOICE_TYPE", spf.getInvoiceType()))+"</payType>");
				writer.println("</xml>");
				writer.flush();
				writer.close();
			}catch(Exception e1){
				e1.printStackTrace();
			}
		}
	}
	
	

	/**
	* �����������ȡ��
	* @param request HttpServletRequest
	* @param form ������
	* @return ҳ��
	*/
	public String partCancel(HttpServletRequest request, ActionForm form) {
		String forward = "resultMessage";
		String[] tag={"-1",null};
		try{
			HttpSession session = request.getSession();
			Long userId = (Long) session.getAttribute("userId");
			
			String saleDetailId = request.getParameter("saleDetailId");
			
			SaleInfoBo sib = SaleInfoBo.getInstance();
			tag=sib.partCancel(saleDetailId,userId);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			request.setAttribute("tag", tag[0]);
			request.setAttribute("businessFlag", "partCancel");
			request.setAttribute("tempData", tag[1]);
		}
		return forward;
	}

	
	
	/**
	 * ��ʷ�����б�
	 * @param request HttpServletRequest
	 * @param form ActionForm ������
	 * @return ���۵���ϸҳ��
	 */
	public String historyPrice(HttpServletRequest request, ActionForm form){
		String forward = "historyPrice";
	
		try{
			String stuffNo = request.getParameter("stuffNo");
			SaleInfoBo pib = SaleInfoBo.getInstance();
			request.setAttribute("historyPriceList",pib.getHistoryPriceList(stuffNo));
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
	}
	

	/**
	 * �ͻ���ʷ�����б�
	 * @param request HttpServletRequest
	 * @param form ActionForm ������
	 * @return ���۵���ϸҳ��
	 */
	public String custPrice(HttpServletRequest request, ActionForm form){
		String forward = "custPrice";
	
		try{
			String customerId = request.getParameter("customerId");
			SaleInfoBo pib = SaleInfoBo.getInstance();
			request.setAttribute("custPriceList",pib.getCustPriceList(customerId));
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
	}
	

	/**
	 * ѯ�۵���ӡ
	 * @param request HttpServletRequest
	 * @param form ActionForm ������
	 * @return ѯ�۵���ӡҳ��
	 */
	public String salePartsPrint(HttpServletRequest request, ActionForm form){
		String forward = null;
	
		try{
			String flag = request.getParameter("flag");
			String custId = request.getParameter("custId");
			String saleNo = request.getParameter("saleNo");
			String repairNo = request.getParameter("repairNo");
			
			SaleInfoBo pib = SaleInfoBo.getInstance();
			request.setAttribute("salePartsInfoList",pib.salePartsPrint(saleNo));
			String[] kmInfo=CustomerInfoBo.getInstance().getKmInfo(custId);
			String[] chenranInfo=CustomerInfoBo.getInstance().getKmInfo("CHRA");
			request.setAttribute("kmInfo",kmInfo);
			request.setAttribute("chenranInfo",chenranInfo);
			if("quotePrint".equals(flag)){	//���㵥
				if(repairNo!=null)
					request.setAttribute("repairFeeList", RepairListBo.getInstance().getRepairFeeList(new Long(repairNo)));
				forward="quotePrint";
			}else{	//ѯ�۵�
				forward="inquiryPrint";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
	}
	

	  
	  /**
		 * �õ����пͻ���Ϣ
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public String getSaleInfo(ActionMapping mapping, ActionForm form,
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
				
				//�õ����о����̵����ֺ�id
				List custList = SaleInfoBo.getInstance().getSaleListByNo(inputValue);
				
				String StrongDealerName="";
				
				String saleNo = "";		
				String partNum = "";		
				String totalQuote = "";		
				String createBy = "";	
				String saleStatus = "";
				String payStatus = "";
				String totalPay = "";
				String billingStatus = "";
				String billingMoney = "";
				String remark = "";
				String purchasePrice= "";
				
				StringBuffer buffer = new StringBuffer();
				for (int i = 0; i < custList.size(); i++) {
					Object[] obj = (Object[]) custList.get(i);
				
					saleNo = obj[0].toString();
					partNum = obj[1].toString();
					totalQuote = obj[2]==null?"":obj[2].toString();
					createBy = obj[3]==null?"":obj[3].toString();
					saleStatus = obj[4]==null?"":obj[4].toString();
					payStatus = obj[5]==null?"":obj[5].toString();
					totalPay = obj[6]==null?"":obj[6].toString();
					billingStatus = obj[7]==null?"":obj[7].toString();
					billingMoney = obj[8]==null?"":obj[8].toString();
					remark = obj[9]==null?"":obj[9].toString();
					purchasePrice = obj[10]==null?"":obj[10].toString();
					
					if(saleNo.indexOf(inputValue) != -1) {
						//�������ֵ�����ݿ�����ݱȽϺ�,�Ӵ�
						StrongDealerName = saleNo.replaceAll(inputValue, "<span class=\"boldfont\">" + inputValue + "</span>");
						
						buffer.append("<div onselect=\"this.text.value = '")
							  .append(saleNo)
							  .append("';$('partNum').value = '")
							  .append(partNum)
							  .append("';$('totalQuote').value = '")
							  .append(totalQuote)
							  .append("';$('createBy').value = '")
							  .append(createBy)
							  .append("';$('saleStatus').value = '")
							  .append(saleStatus)
							  .append("';$('payStatus').value = '")
							  .append(payStatus)
							  .append("';$('totalPay').value = '")
							  .append(totalPay)
							  .append("';$('billingStatus').value = '")
							  .append(billingStatus)
							  .append("';$('billingMoney').value = '")
							  .append(billingMoney)
							  .append("';$('remark').value = '")
							  .append(remark)
							   .append("';$('partsFee').value = '")
							  .append(purchasePrice)
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
		

		public String getRepairInfo(ActionMapping mapping, ActionForm form,
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
				
				String inputValue = request.getParameter("inputValue");
				
				List<Object[]> repairList = RepairHandleBo.getInstance().mapSaleList(inputValue);
				
				String repairNo = "";		
				String serviceSheetNo = "";	
				String StrongDealerName = "";
				
				String customerId = "";		
				String customerName = "";		
				String linkman = "";		
				String phone = "";		
				String cityName = "";	
				String fax = "";
				String address = "";
				String mobile= "";
				String provinceName= "";
				String modelCode= "";
				
				StringBuffer buffer = new StringBuffer();
				for (int i = 0; i < repairList.size(); i++) {
					Object[] repairInfo =  repairList.get(i);
			
					repairNo = repairInfo[0].toString();
					serviceSheetNo = repairInfo[1].toString();
					customerId = repairInfo[2].toString();
					customerName = repairInfo[3].toString();
					linkman = repairInfo[4]==null?"":repairInfo[4].toString();
					phone = repairInfo[5]==null?"":repairInfo[5].toString();
					cityName = repairInfo[6]==null?"":repairInfo[6].toString();
					fax = repairInfo[7]==null?"":repairInfo[7].toString();
					address = repairInfo[8]==null?"":repairInfo[8].toString();
					mobile = repairInfo[9]==null?"":repairInfo[9].toString();
					provinceName = repairInfo[10]==null?"":repairInfo[10].toString();
					modelCode = repairInfo[13]==null?"":repairInfo[13].toString();
					
					if(serviceSheetNo.toUpperCase().indexOf(inputValue.toUpperCase()) != -1) {
						StrongDealerName = serviceSheetNo.replaceAll(inputValue, "<span class=\"boldfont\">" + inputValue + "</span>");
						
						buffer.append("<div onselect=\"this.text.value = '")
							  .append(serviceSheetNo)
							  .append("';$('repairNo').value = '")
							  .append(repairNo)
							  .append("';$('customerId').value = '")
							  .append(customerId)
							  .append("';$('customerName').value = '")
							  .append(customerName)
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
//							  .append("';$('address').value = '")
//							  .append(address)
//							  .append("';$('mobile').value = '")
//							  .append(mobile)
//							  .append("';$('provinceName').value = '")
//							  .append(provinceName)
							  .append("';$('modelCode').value = '")
							  .append(modelCode)
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
		
		
		
		public void deleteSalePayment(ActionMapping mapping,ActionForm form,
				HttpServletRequest request,HttpServletResponse response)  throws Exception{
			PrintWriter writer = response.getWriter();
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			try{
				String feeId = request.getParameter("feeId");
						
				SalePaymentForm spf = new SalePaymentForm();
				spf.setFeeId(new Long(feeId));
				
				boolean deleteFlag = SaleInfoBo.getInstance().deleteSalePayment(spf);
				
				
				if(deleteFlag){
					writer.println("<xml>");
					writer.println("<flag>true</flag>");
					writer.println("<feeId>"+feeId+"</feeId>");
					writer.println("</xml>");
				}else{
					writer.println("<xml>");
					writer.println("<flag>false</flag>");
					writer.println("</xml>");
				}
				
				
			}catch(Exception e){
				e.printStackTrace();
				
				writer.println("<xml>");
				writer.println("<flag>false</flag>");
				writer.println("</xml>");
			}finally{
				writer.flush();
				writer.close();
			}
		}

}
