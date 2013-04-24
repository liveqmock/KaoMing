package com.dne.sie.repair.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.dne.sie.common.exception.ComException;
import com.dne.sie.common.exception.VersionException;
import com.dne.sie.common.tools.DicInit;
import com.dne.sie.common.tools.EscapeUnescape;
import com.dne.sie.common.tools.Operate;
import com.dne.sie.maintenance.bo.PartInfoBo;
import com.dne.sie.repair.bo.RepairHandleBo;
import com.dne.sie.repair.form.RepairPartForm;
import com.dne.sie.repair.form.RepairSearchForm;
import com.dne.sie.repair.form.RepairServiceForm;
import com.dne.sie.util.action.ControlAction;

public class RepairHandleAction extends ControlAction {
	

	/**
	 *	ά�޵�¼��
	 *	@param request HttpServletRequest
	 *	@param form ActionForm
	 *	@return 
	 */
	public String serviceAdd(HttpServletRequest request, ActionForm form) throws Exception {
		String forward = "resultMessage";

		String tempAttache = request.getParameter("attacheIds");
		
		RepairHandleBo rhb = RepairHandleBo.getInstance();
		Long userId=(Long) request.getSession().getAttribute("userId");
		
		RepairSearchForm searchForm=(RepairSearchForm) form;
		searchForm.setCurrentStatus("A");
		//searchForm.setOperaterId(userId);
		searchForm.setCreateBy(userId);
		searchForm.setCreateDate(new Date());
		
		searchForm.setPurchaseDate(Operate.toDate(searchForm.getPurchaseDateStr()));
		searchForm.setCustomerVisitDate(Operate.toDate(searchForm.getCustomerVisitDateStr()));
		searchForm.setEstimateRepairDate(Operate.toDate(searchForm.getEstimateRepairDateStr()));
		searchForm.setActualOnsiteDate(Operate.toDate(searchForm.getActualOnsiteDateStr()));
		searchForm.setActualRepairedDate(Operate.toDate(searchForm.getActualRepairedDateStr()));
		//searchForm.setExtendedWarrantyDate(Operate.toDate(searchForm.getExtendedWarrantyDateStr()));
		
		RepairServiceForm rsf=null;
		try{
			rsf =rhb.addService(searchForm);
		}catch (ComException ce) {
			ce.printStackTrace();
			request.setAttribute("tag", "-1");
			request.setAttribute("tempData", ce.getMessage());
			request.setAttribute("businessFlag", "RR90Err");
			return forward;
		}
		//���浥��
		Long repairNo = rsf.getRepairNo();
			
		if (tempAttache!=null&&!tempAttache.equals("")) {
			rhb.updateAttacheByAttacheIdsAndSheetNo(tempAttache.split(","),repairNo,userId);
		}
		
		
		//�ύ������ʾ��ӡҲҳ��
		//request.setAttribute("partsList",SaleInfoBo.getInstance().getSalePartsListByNo(searchForm.getSaleNo()));
		//request.setAttribute("repair", rsf);
		request.setAttribute("tag", "1");
		request.setAttribute("businessFlag", "receiveAdd");
		
		return forward;
	}
	
	
	/**
	 * ����ת����
	 * @param request
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public String transferSale(HttpServletRequest request, ActionForm form) throws Exception {
		String forward = "resultMessage";
		
		RepairSearchForm searchForm=(RepairSearchForm) form;
		RepairHandleBo rhb = RepairHandleBo.getInstance();
		Long userId=(Long) request.getSession().getAttribute("userId");
		
		searchForm.setCurrentStatus("S"); //���������
		searchForm.setUpdateBy(userId);
		searchForm.setUpdateDate(new Date());
		
	
		rhb.transferSale(searchForm);
		
		String tempAttache = request.getParameter("attacheIds");
		if (tempAttache!=null&&!tempAttache.equals("")) {
			rhb.updateAttacheByAttacheIdsAndSheetNo(tempAttache.split(","),searchForm.getRepairNo(),userId);
		}
		request.setAttribute("tag", "1");
		request.setAttribute("businessFlag", "transferSale");
		
		return forward;
	}
	
	/**
	 * �޸�
	 * @param request
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public String repairEnd(HttpServletRequest request, ActionForm form) throws Exception {
		String forward = "resultMessage";
		
		RepairSearchForm searchForm=(RepairSearchForm) form;
		RepairHandleBo rhb = RepairHandleBo.getInstance();
		Long userId=(Long) request.getSession().getAttribute("userId");
	
		searchForm.setCurrentStatus("F"); //�޸�
		searchForm.setUpdateBy(userId);
		searchForm.setUpdateDate(new Date());
		
		try{
			rhb.repairEnd(searchForm);
		}catch(VersionException ve){
			return "versionErr";
		}
		String tempAttache = request.getParameter("attacheIds");
		if (tempAttache!=null&&!tempAttache.equals("")) {
			rhb.updateAttacheByAttacheIdsAndSheetNo(tempAttache.split(","),searchForm.getRepairNo(),userId);
		}
		request.setAttribute("tag", "1");
		request.setAttribute("businessFlag", "repairEnd");
		
		return forward;
	}
	
	/**
	 * ����--������
	 * @param request
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public String doNotRepair(HttpServletRequest request, ActionForm form) throws Exception {

		RepairSearchForm searchForm=(RepairSearchForm) form;
		RepairHandleBo rhb = RepairHandleBo.getInstance();
		Long userId=(Long) request.getSession().getAttribute("userId");
		
		searchForm.setCurrentStatus("N");
		searchForm.setUpdateBy(userId);
		searchForm.setUpdateDate(new Date());
		
		rhb.repairOperate(searchForm);
		
		String tempAttache = request.getParameter("attacheIds");
		if (tempAttache!=null&&!tempAttache.equals("")) {
			rhb.updateAttacheByAttacheIdsAndSheetNo(tempAttache.split(","),searchForm.getRepairNo(),userId);
		}
		request.setAttribute("tag", "1");
		request.setAttribute("businessFlag", "doNotRepair");
		
		
		return "resultMessage";
	}
	

	/**
	 * ���� -- �ݴ�
	 * @param request
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public String saveRepair(HttpServletRequest request, ActionForm form) throws Exception {

		RepairSearchForm searchForm=(RepairSearchForm) form;
		RepairHandleBo rhb = RepairHandleBo.getInstance();
		Long userId=(Long) request.getSession().getAttribute("userId");
		
		searchForm.setUpdateBy(userId);
		searchForm.setUpdateDate(new Date());
		
		try{
			rhb.repairOperate(searchForm);
		}catch(VersionException ve){
			return "versionErr";
		}
		String tempAttache = request.getParameter("attacheIds");
		if (tempAttache!=null&&!tempAttache.equals("")) {
			rhb.updateAttacheByAttacheIdsAndSheetNo(tempAttache.split(","),searchForm.getRepairNo(),userId);
		}
		request.setAttribute("tag", "1");
		request.setAttribute("businessFlag", "doNotRepair");
		
		
		return "resultMessage";
	}
	
	

	/**
	 * Cancel repair sheet
	 * @param request HttpServletRequest
	 * @param form ActionForm
	 * @return String Return forward path,resultMessage Message page
	 */
	public String cancelRepair(HttpServletRequest request, ActionForm form) throws Exception{
		String forward = "resultMessage";
		int tag = -1;
		
		RepairHandleBo rhBo = RepairHandleBo.getInstance();
		
		Long userId = (Long)request.getSession().getAttribute("userId");
		String strRepairNos = request.getParameter("repairNos");
//		String remark = request.getParameter("cancelRemark");
		
		tag=rhBo.cancelRepair(strRepairNos,userId);
		

		request.setAttribute("tag",Integer.toString(tag));
		request.setAttribute("businessFlag","cancelRepair");
		
		return forward;
	}	
	
	/**
	 * ���� -- ������
	 * @param request
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public String repairDZComplete(HttpServletRequest request, ActionForm form) throws Exception {
		String forward = "resultMessage";
		
		RepairSearchForm searchForm=(RepairSearchForm) form;
		RepairHandleBo rhb = RepairHandleBo.getInstance();
		Long userId=(Long) request.getSession().getAttribute("userId");
		
		searchForm.setCurrentStatus("P"); //������
		searchForm.setOperaterId(userId);	//����Ա
		searchForm.setUpdateBy(userId);
		searchForm.setUpdateDate(new Date());
		

		rhb.repairOperate(searchForm);
		
//		String tempAttache = request.getParameter("attacheIds");
//		if (tempAttache!=null&&!tempAttache.equals("")) {
//			rhb.updateAttacheByAttacheIdsAndSheetNo(tempAttache.split(","),searchForm.getRepairNo(),userId);
//		}
		request.setAttribute("tag", "1");
		request.setAttribute("businessFlag", "repairDZComplete");
		
		return forward;
	}
	
	
	
	/**
	 * ��ά�ޡ�->���绰��ϡ�->����ϸ��->ά��������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void insertRepairPartInfo(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		try{
			Long userId = (Long)request.getSession().getAttribute("userId");
			
			String repairNo = request.getParameter("repairNo");
			String stuffNo = request.getParameter("stuffNo");
			String warrantyType = request.getParameter("warrantyType");
			String applyQty = request.getParameter("applyQty");
			
			RepairHandleBo rhBo=RepairHandleBo.getInstance();
			List<RepairPartForm> returnFormList = null;
			
			//������ʱ��������Ƿ����
			boolean chk = PartInfoBo.getInstance().chkStuffNo(stuffNo);
			if(!chk){
				
				//wubin at 20110401 �����µķ������RR90��Ĭ�ϸ�������շѵ�
//				if("R".equals(partFeeType)){
//					partFeeType = "Y";
//					isRR90PartType = "Y";
//				}
				
				
				//add end 
				List<RepairPartForm> rpfList = new ArrayList<RepairPartForm>();
				
						
				RepairPartForm rpf = new RepairPartForm();
				rpf.setRepairNo(new Long(repairNo));
				rpf.setStuffNo(stuffNo);
				rpf.setWarrantyType(warrantyType);
				rpf.setApplyQty(new Integer(applyQty));
				rpf.setRepairPartStatus("A");	//������
				rpf.setRepairPartType("W");		//ά������
				rpf.setCreateBy(userId);
				rpf.setCreateDate(new java.util.Date());
				
				rpfList.add(rpf);
						
				returnFormList = rhBo.insertPartInfo(rpfList);
			}

			PrintWriter writer = response.getWriter();
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			if(null !=returnFormList  && returnFormList.size()>0){
				writer.println("<xml>");
				writer.println("<flag>true</flag>");
				for(int i=0;i<returnFormList.size();i++){
					RepairPartForm aPartForm = (RepairPartForm)returnFormList.get(i);
					writer.println("<partRow id=\""+aPartForm.getPartsId()+"\">");
					writer.println("  <partsId>"+aPartForm.getPartsId()+"</partsId>");
					writer.println("  <stuffNo>"+aPartForm.getStuffNo()+"</stuffNo>");
					writer.println("  <skuCode>"+EscapeUnescape.escape(aPartForm.getSkuCode())+"</skuCode>");
					String standard = aPartForm.getStandard()==null?"":EscapeUnescape.escape(aPartForm.getStandard());
					writer.println("  <standard>"+(standard.equals("")?"..":standard)+"</standard>");
					writer.println("  <skuUnit>"+EscapeUnescape.escape(aPartForm.getSkuUnit())+"</skuUnit>");
					writer.println("  <warrantyType>"+EscapeUnescape.escape(DicInit.getSystemName("WARRANTY_TYPE",aPartForm.getWarrantyType()))+"</warrantyType>");
					writer.println("  <applyQty>"+aPartForm.getApplyQty()+"</applyQty>");
					
					writer.println("</partRow>");
				}
				writer.println("</xml>");
			}else{
				writer.println("<xml>");
				writer.println("<flag>false</flag>");
				if(chk){
					writer.println("<result>part</result>");
				}else{
					writer.println("<result>error</result>");
				}
				
				
				writer.println("</xml>");
			}
			writer.flush();
			writer.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * ��ά�ޡ�->���绰��ϡ�->����ϸ��->ά��Partɾ��
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void deletePartStatus(ActionMapping mapping,
										ActionForm form,
										HttpServletRequest request,
										HttpServletResponse response) throws Exception{
		PrintWriter writer = response.getWriter();
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		try{
			String partsId = request.getParameter("partsId");
					
			RepairPartForm rpf = new RepairPartForm();
			rpf.setPartsId(new Long(partsId));
			
			boolean deleteFlag = RepairHandleBo.getInstance().deletePartInfo(rpf);
			
			
			
			if(deleteFlag){
				writer.println("<xml>");
				writer.println("<flag>true</flag>");
				writer.println("<partsId>"+partsId+"</partsId>");
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
	
	

	/**
	 * ��ά�ޡ�->������->��������ϸ��->�ѱ������ȷ������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void insertLoanPartInfo(ActionMapping mapping,
									ActionForm form,
									HttpServletRequest request,
									HttpServletResponse response) throws Exception{
		
		PrintWriter writer = response.getWriter();
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		try{
			Long userId = (Long)request.getSession().getAttribute("userId");
			
			RepairHandleBo rhBo=RepairHandleBo.getInstance();
			
			String repairNo = request.getParameter("repairNo");
			String stuffNo = request.getParameter("stuffNo");
			String warrantyType = request.getParameter("warrantyType");
			String applyQty = request.getParameter("applyQty");
			
			RepairPartForm rpf = new RepairPartForm();
			
			rpf.setRepairNo(new Long(repairNo));
			rpf.setStuffNo(stuffNo);
			rpf.setWarrantyType(warrantyType);
			rpf.setApplyQty(new Integer(applyQty));
			rpf.setRepairPartStatus("L");	//�ѷ������ȡ
			rpf.setRepairPartType("X");		//Я�����
			rpf.setCreateBy(userId);
			rpf.setCreateDate(new java.util.Date());
			
		    RepairPartForm returnRpf = rhBo.submitLoanPart(rpf);
		    
		    
			
			if(returnRpf != null){
				String skuCode = returnRpf.getSkuCode()==null?"":EscapeUnescape.escape(returnRpf.getSkuCode());
				String standard = returnRpf.getStandard()==null?"":EscapeUnescape.escape(returnRpf.getStandard());
				
				writer.println("<xml>");
				writer.println("<flag>true</flag>");
				//System.out.println("-=-=-=-=sunhj returnRpf.getPartsId():"+returnRpf.getPartsId());
				writer.println("<partsId>"+returnRpf.getPartsId()+"</partsId>");
				//System.out.println("-=-=-=-=sunhj returnRpf.getStuffNo():"+returnRpf.getStuffNo());
				writer.println("<stuffNo>"+returnRpf.getStuffNo()+"</stuffNo>");
				//System.out.println("-=-=-=-=sunhj returnRpf.getSkuCode():"+returnRpf.getSkuCode());
				writer.println("<skuCode>"+(skuCode.equals("")?"..":skuCode)+"</skuCode>");
				//System.out.println("-=-=-=-=sunhj returnRpf.getStandard():"+returnRpf.getStandard());
				writer.println("<standard>"+(standard.equals("")?"..":standard)+"</standard>");
				//System.out.println("-=-=-=-=sunhj returnRpf.getSkuUnit():"+returnRpf.getSkuUnit());
				writer.println("  <skuUnit>"+EscapeUnescape.escape(returnRpf.getSkuUnit())+"</skuUnit>");
				//System.out.println("-=-=-=-=sunhj returnRpf.getRepairPartStatus():"+DicInit.getSystemName("LOAN_STATUS",returnRpf.getRepairPartStatus()));
				writer.println("<repairPartStatus>"+EscapeUnescape.escape(DicInit.getSystemName("LOAN_STATUS",returnRpf.getRepairPartStatus()))+"</repairPartStatus>");
				//System.out.println("-=-=-=-=sunhj returnRpf.getApplyQty():"+returnRpf.getApplyQty());
				writer.println("<applyQty>"+returnRpf.getApplyQty()+"</applyQty>");
				writer.println("<partVersion>"+returnRpf.getVersion()+"</partVersion>");
				
				writer.println("</xml>");
			}else{
				writer.println("<xml>");
				writer.println("<flag>false</flag>");
				writer.println("<result>part</result>");
				writer.println("</xml>");
			}
		
			
		}catch(Exception e){
			e.printStackTrace();
			
			writer.println("<xml>");
			writer.println("<flag>false</flag>");
			writer.println("<result>part</result>");
			writer.println("</xml>");
		}finally{
			writer.flush();
			writer.close();
		}
	}
	
	
	public void cancelLoanPart(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		PrintWriter writer = response.getWriter();
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		
		try{
			
			Long userId = (Long)request.getSession().getAttribute("userId");
			String partsId = request.getParameter("partsId");
			//String partStatusCode = request.getParameter("partStatusCode");
			String version = request.getParameter("version");
				
			RepairHandleBo rhBo=RepairHandleBo.getInstance();
				
			boolean flag = false;
			//����ȡ������İ汾У�� added by xt 2007-09-08
			if(partsId != null && rhBo.checkPartVersion(new Long(partsId),version)){
					
				RepairPartForm rpf = new RepairPartForm();
				rpf.setPartsId(new Long(partsId));
				rpf.setUpdateBy(userId);
				
				flag = rhBo.cancelLoanPart(rpf);
					
			}

			if(flag){
				writer.println("<xml>");
				writer.println("<flag>true</flag>");
				writer.println("<partsId>"+partsId+"</partsId>");
				writer.println("</xml>");
			}else{
				writer.println("<xml>");
				writer.println("<flag>false</flag>");
				writer.println("</xml>");
			}
			
			
		}catch(VersionException ve){
			ve.printStackTrace();
			
			writer.println("<xml>");
			writer.println("<flag>versionErr</flag>");
			writer.println("</xml>");
			
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
	
	public void insertLoanToolInfo(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
			
		PrintWriter writer = response.getWriter();
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		try{
			Long userId = (Long)request.getSession().getAttribute("userId");
			
			RepairHandleBo rhBo=RepairHandleBo.getInstance();
			
			String repairNo = request.getParameter("repairNo");
			String stuffNo = request.getParameter("stuffNo");
			String warrantyType = request.getParameter("warrantyType");
			String applyQty = request.getParameter("applyQty");
			
			RepairPartForm rpf = new RepairPartForm();
			
			rpf.setRepairNo(new Long(repairNo));
			rpf.setStuffNo(stuffNo);
			rpf.setWarrantyType(warrantyType);
			rpf.setApplyQty(new Integer(applyQty));
			rpf.setRepairPartStatus("L");	//�ѷ������ȡ
			rpf.setRepairPartType("T");		//Я������
			rpf.setCreateBy(userId);
			rpf.setCreateDate(new java.util.Date());
			
		    RepairPartForm returnRpf = rhBo.submitLoanTool(rpf);
		    
		    
			
			if(returnRpf != null){
				String skuCode = returnRpf.getSkuCode()==null?"":EscapeUnescape.escape(returnRpf.getSkuCode());
				String standard = returnRpf.getStandard()==null?"":EscapeUnescape.escape(returnRpf.getStandard());
				String owner = returnRpf.getOwner()==null?"":EscapeUnescape.escape(returnRpf.getOwner());
				
				writer.println("<xml>");
				writer.println("<flag>true</flag>");
				//System.out.println("-=-=-=-=sunhj returnRpf.getPartsId():"+returnRpf.getPartsId());
				writer.println("<partsId>"+returnRpf.getPartsId()+"</partsId>");
				//System.out.println("-=-=-=-=sunhj returnRpf.getStuffNo():"+returnRpf.getStuffNo());
				writer.println("<stuffNo>"+returnRpf.getStuffNo()+"</stuffNo>");
				//System.out.println("-=-=-=-=sunhj returnRpf.getSkuCode():"+returnRpf.getSkuCode());
				writer.println("<skuCode>"+(skuCode.equals("")?"..":skuCode)+"</skuCode>");
				//System.out.println("-=-=-=-=sunhj returnRpf.getStandard():"+returnRpf.getStandard());
				writer.println("<standard>"+(standard.equals("")?"..":standard)+"</standard>");
				//System.out.println("-=-=-=-=sunhj returnRpf.getSkuUnit():"+returnRpf.getSkuUnit());
				writer.println("<skuUnit>"+EscapeUnescape.escape(returnRpf.getSkuUnit())+"</skuUnit>");
				writer.println("<owner>"+(owner.equals("")?"..":standard)+"</owner>");
				//System.out.println("-=-=-=-=sunhj returnRpf.getRepairPartStatus():"+DicInit.getSystemName("LOAN_STATUS",returnRpf.getRepairPartStatus()));
				writer.println("<repairPartStatus>"+EscapeUnescape.escape(DicInit.getSystemName("LOAN_STATUS",returnRpf.getRepairPartStatus()))+"</repairPartStatus>");
				//System.out.println("-=-=-=-=sunhj returnRpf.getApplyQty():"+returnRpf.getApplyQty());
				writer.println("<applyQty>"+returnRpf.getApplyQty()+"</applyQty>");
				writer.println("<partVersion>"+returnRpf.getVersion()+"</partVersion>");
				
				writer.println("</xml>");
			}else{
				writer.println("<xml>");
				writer.println("<flag>false</flag>");
				writer.println("<result>part</result>");
				writer.println("</xml>");
			}
		
			
		}catch(Exception e){
			e.printStackTrace();
			
			writer.println("<xml>");
			writer.println("<flag>false</flag>");
			writer.println("<result>part</result>");
			writer.println("</xml>");
		}finally{
			writer.flush();
			writer.close();
		}
	}
	
	
	public void cancelLoanTool(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		PrintWriter writer = response.getWriter();
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		
		try{
			
			Long userId = (Long)request.getSession().getAttribute("userId");
			String partsId = request.getParameter("partsId");
			//String partStatusCode = request.getParameter("partStatusCode");
			String version = request.getParameter("version");
				
			RepairHandleBo rhBo=RepairHandleBo.getInstance();
				
			boolean flag = false;
			//����ȡ������İ汾У�� added by xt 2007-09-08
			if(partsId != null && rhBo.checkPartVersion(new Long(partsId),version)){
					
				RepairPartForm rpf = new RepairPartForm();
				rpf.setPartsId(new Long(partsId));
				rpf.setUpdateBy(userId);
				
				flag = rhBo.cancelLoanPart(rpf);
					
			}

			if(flag){
				writer.println("<xml>");
				writer.println("<flag>true</flag>");
				writer.println("<partsId>"+partsId+"</partsId>");
				writer.println("</xml>");
			}else{
				writer.println("<xml>");
				writer.println("<flag>false</flag>");
				writer.println("</xml>");
			}
			
			
		}catch(VersionException ve){
			ve.printStackTrace();
			
			writer.println("<xml>");
			writer.println("<flag>versionErr</flag>");
			writer.println("</xml>");
			
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

	

	/**
	 * �ɹ� -- ά���ɹ�
	 * @param request
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public String dispatch(HttpServletRequest request, ActionForm form) throws Exception {
		String forward = "resultMessage";
		
		RepairSearchForm searchForm=(RepairSearchForm) form;
		RepairHandleBo rhb = RepairHandleBo.getInstance();
		Long userId=(Long) request.getSession().getAttribute("userId");
		
		searchForm.setCurrentStatus("D"); //���ɹ�
		searchForm.setUpdateBy(userId);
		searchForm.setUpdateDate(new Date());
		
		String repairMans = request.getParameter("repairMans");
		
		if(repairMans!=null && repairMans.startsWith("@")){
			repairMans=repairMans.substring(1)+" ";
		}
		rhb.dispatch(searchForm,repairMans);
		
		String tempAttache = request.getParameter("attacheIds");
		if (tempAttache!=null&&!tempAttache.equals("")) {
			rhb.updateAttacheByAttacheIdsAndSheetNo(tempAttache.split(","),searchForm.getRepairNo(),userId);
		}
		request.setAttribute("tag", "1");
		request.setAttribute("businessFlag", "repairDispatch");
		
		return forward;
	}
	
	
	/**
	 * �ɹ�ǰУ�飬Я��������͹��߶��������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void checkDispatchPart(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		PrintWriter writer = response.getWriter();
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		
		try{
			
			String repairNo = request.getParameter("repairNo");
				
			RepairHandleBo rhBo=RepairHandleBo.getInstance();
				
			String flag = rhBo.checkDispatchPart(new Long(repairNo));

			
			writer.println("<xml>");
			writer.println("<flag>"+flag+"</flag>");
			writer.println("</xml>");
			
			
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
	
	
	

	/**
	 * Я�����ת����
	 * ά�޷���ʱ�ɲ���
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void transferLoanPart(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		PrintWriter writer = response.getWriter();
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		
		try{
			Long userId = (Long)request.getSession().getAttribute("userId");
			String partsId = request.getParameter("partsId");
			String version = request.getParameter("version");
				
			RepairHandleBo rhBo=RepairHandleBo.getInstance();
			String flag = "false";
			if(partsId != null && rhBo.checkPartVersion(new Long(partsId),version)){
				
				RepairPartForm rpf = new RepairPartForm();
				rpf.setPartsId(new Long(partsId));
				rpf.setUpdateBy(userId);
				rpf.setUpdateDate(new Date());
				
				if(rhBo.transferLoanPart(rpf)){
					flag="true";
				}
					
			}
			
			writer.println("<xml>");
			writer.println("<flag>"+flag+"</flag>");
			writer.println("<partsId>"+partsId+"</partsId>");
			writer.println("</xml>");
			

		}catch(VersionException ve){
			ve.printStackTrace();
			
			writer.println("<xml>");
			writer.println("<flag>versionErr</flag>");
			writer.println("</xml>");
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
	
	
	

	/**
	 * �������ǰУ�飬������Я��������͹��߶����뷵��
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void checkReturnPart(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		PrintWriter writer = response.getWriter();
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		
		try{
			
			String repairNo = request.getParameter("repairNo");
				
			RepairHandleBo rhBo=RepairHandleBo.getInstance();
				
			String flag = rhBo.checkReturnPart(new Long(repairNo));

			
			writer.println("<xml>");
			writer.println("<flag>"+flag+"</flag>");
			writer.println("</xml>");
			
			
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
	
	

	
	/**
	 * ���� -- �������
	 * @param request
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public String repairReturnEnd(HttpServletRequest request, ActionForm form) throws Exception {
		String forward = "resultMessage";
		
		RepairSearchForm searchForm=(RepairSearchForm) form;
		RepairHandleBo rhb = RepairHandleBo.getInstance();
		Long userId=(Long) request.getSession().getAttribute("userId");
		
		searchForm.setCurrentStatus("R"); //�ѷ���
		searchForm.setUpdateBy(userId);
		searchForm.setUpdateDate(new Date());
		
		String[] arrivalDate = request.getParameterValues("arrivalDate");
		String[] returnDate = request.getParameterValues("returnDate");
		String[] travelFee = request.getParameterValues("travelFee");
		String[] laborCosts = request.getParameterValues("laborCosts");
		String[] repairCondition = request.getParameterValues("repairCondition");
		String[] travelId = request.getParameterValues("travelId");
		
		if(travelId==null){
			throw new Exception("travelId null!");
		}
		
		ArrayList<String[]> repairManInfo = new ArrayList<String[]>();
		repairManInfo.add(travelId);
		repairManInfo.add(arrivalDate);
		repairManInfo.add(returnDate);
		repairManInfo.add(travelFee);
		repairManInfo.add(laborCosts);
		repairManInfo.add(repairCondition);
		
		rhb.returnEnd(searchForm,repairManInfo);
		
		
		request.setAttribute("tag", "1");
		request.setAttribute("businessFlag", "repairReturnEnd");
		
		return forward;
	}
	
}
