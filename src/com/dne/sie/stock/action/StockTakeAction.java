package com.dne.sie.stock.action;

//Java ������
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.dne.sie.common.tools.CommonSearch;
import com.dne.sie.common.tools.EscapeUnescape;
import com.dne.sie.stock.bo.StockInBo;
import com.dne.sie.stock.bo.StockTakeBo;
import com.dne.sie.stock.form.StockInfoForm;
import com.dne.sie.stock.form.StockTakeDetailForm;
import com.dne.sie.stock.form.StockTakeForm;
import com.dne.sie.util.action.ControlAction;


/**
 * ����̵�Action������
 * @author xt
 * @version 1.1.5.6
 * @see StockTakeAction.java <br>
 */
public class StockTakeAction extends ControlAction{
	
   
	/**
	* ��ʼ�̵�ҳ��
	* @param request HttpServletRequest
	* @param form ������
	* @return ҳ��
	*/
   public String takeInit(HttpServletRequest request, ActionForm form) {
		String forward = "";

		try{
			HttpSession session=request.getSession();
			//Long userId=(Long)session.getAttribute("userId");
			StockTakeBo stbo=StockTakeBo.getInstance();
			StockTakeForm stf=stbo.getUserTakeInfo();
			String takeStatus=stf.getTakeStatus();
			if(takeStatus==null||takeStatus.equals("")){//����δ�����������뿪ʼ�̵�ҳ��
				
				StockInBo sib = StockInBo.getInstance();
				request.setAttribute("binCode",sib.getStockList());
				
				forward = "takeInit";
				
			}else{//�����ϴ�����
				
				if(takeStatus.equals("A")){//һ���̵�
					StockTakeDetailForm stdf=new StockTakeDetailForm();
					stdf.setStockTakeId(stf.getStockTakeId());
					ArrayList[] temp = (ArrayList[])session.getAttribute("initList");
					if(temp==null){
						temp=(ArrayList[])stbo.takeFirstList(stdf);
						session.setAttribute("initList",temp);
					}
					request.setAttribute("stockInfoList",temp[0]);
//					request.setAttribute("stockCode",temp[1]);
//					request.setAttribute("binCode",temp[2]);
//					request.setAttribute("noneBin",temp[3]);
					request.setAttribute("takeId",stf.getStockTakeId()+"");
					
					forward = "takeFirst";
				}else if(takeStatus.equals("B")){//�����̵�
					ArrayList secondList=(ArrayList)session.getAttribute("initList");
					if(secondList==null){
						secondList=stbo.takeSecondList(stf.getStockTakeId());
						session.setAttribute("initList",secondList);
					}
					request.setAttribute("secondList",secondList);
					request.setAttribute("flowTime",stf.getBeginDate().toString());
					request.setAttribute("flowUser",stf.getOperater().toString());
					request.setAttribute("flowId",stf.getStockTakeId().toString());
					request.setAttribute("takeId",stf.getStockTakeId()+"");
					
					forward = "takeSecond";
				}
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
   }
   
   
   
   /**
   	* ��ʼ�̵�
   	* @param request HttpServletRequest
   	* @param form ������
   	* @return ҳ��
   	*/
	public String takeStart(HttpServletRequest request, ActionForm form) {
	   String forward = "takeFirst";
	   try{
			//��ȡ�̵�����sif
		   StockInfoForm sif=(StockInfoForm)form;
		   HttpSession session=request.getSession();
		   Long userId=(Long)session.getAttribute("userId");
		  
		   sif.setCreateBy(userId);
		   
			StockTakeBo stb = StockTakeBo.getInstance();
			StockTakeForm stf=stb.getUserTakeInfo();
			String takeStatus=stf.getTakeStatus()==null?"":stf.getTakeStatus();
			
			//ͬһ��ά��վֻ����һ���̵����̣�������ھͲ���������һ��
			if(!takeStatus.equals("")){
				forward = "resultMessage";
				request.setAttribute("tag","-1");
				request.setAttribute("businessFlag","takeStart");
			}else{
				//һ���̵㿪ʼ�������̵���Ϣ��
				if(stb.checkAvailable(sif)){
					int takeId=stb.takeStartInsert(sif);
					if(takeId!=-1){
						StockTakeDetailForm stdf=new StockTakeDetailForm();
						stdf.setStockTakeId(new Long(takeId));
						ArrayList temp[] = (ArrayList[])stb.takeFirstList(stdf);
						//����һ���̵������session
						session.setAttribute("initList",temp);
						request.setAttribute("stockInfoList",temp[0]);
						request.setAttribute("binCode",temp[1]);
						request.setAttribute("noneBin",temp[2]);
					}
					request.setAttribute("takeId",takeId+"");
				}else{
					forward = "resultMessage";
					request.setAttribute("tag","-1");
					request.setAttribute("businessFlag","takeLock");
				}
			}
	   }catch(Exception e){
		   e.printStackTrace();
	   }
	   return forward;
	}
	  
   

	/**
	 * һ���̵�����
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return ҳ��
	 */
	public void takeFirstAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		try{
			HttpSession session=request.getSession();
			Long userId=(Long)session.getAttribute("userId");
			
			String skuCode = request.getParameter("skuCode");
			String binCode = request.getParameter("binCode");
			String stuffNo = request.getParameter("stuffNo");
			String skuNum = request.getParameter("skuNum");
//			String snNo = request.getParameter("snNo");
//			String lotNo = request.getParameter("lotNo");
			String stockTakeId = request.getParameter("stockTakeId");
			
			StockTakeDetailForm stdf=new StockTakeDetailForm();
			stdf.setSkuCode(EscapeUnescape.unescape(skuCode));
			stdf.setStuffNo(stuffNo);
			stdf.setStockNum(new Integer(0));
			stdf.setTakeNum(new Integer(skuNum));
			stdf.setBinCode(binCode);
			stdf.setStockTakeId(new Long(stockTakeId));
			stdf.setAdjustFlag("A");	//������־���̵�����
			stdf.setCreateBy(userId);
			stdf.setStockId(new Long(0));
			stdf.setDiffNum(new Integer(-stdf.getTakeNum().intValue()));
			
			String tag="false";
			int pkId=-1;
			if(CommonSearch.getInstance().getPartInfo(stuffNo)==null){
				pkId=-2;
				tag="true"; 
			}else{
				StockTakeBo stbo = StockTakeBo.getInstance();
				pkId=stbo.firstAdd(stdf);
				if(pkId!=-1){ 
					tag="true"; 
					session.removeAttribute("initList");
				}
			}
			PrintWriter writer = response.getWriter();				  
			response.setContentType("text/xml");						  
			response.setHeader("Cache-Control", "no-cache");            
			writer.println("<xml>");
			writer.println("<addFlag>"+tag+"</addFlag>");
			writer.println("<pkId>"+pkId+"</pkId>");
			writer.println("</xml>");
			writer.flush();
			writer.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}			
				
	}
	

	/**
	 * �̵��ݴ�
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return ҳ��
	 */
	public void tempSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		try{
			String takeNum = request.getParameter("takeNumAll");
			String stockTakeDetailId = request.getParameter("stockTakeDetailId");
			
			StockTakeBo stbo = StockTakeBo.getInstance();
			String tag="false";
			if(stbo.tempSave(stockTakeDetailId,takeNum)==1){ 
				tag="true"; 
				HttpSession session=request.getSession();
				session.removeAttribute("initList");
			}
			
			PrintWriter writer = response.getWriter();				  
			response.setContentType("text/xml");						  
			response.setHeader("Cache-Control", "no-cache");            
			writer.println("<xml>");
			writer.println("<saveFlag>"+tag+"</saveFlag>");
			writer.println("</xml>");
			writer.flush();
			writer.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}			
				
	}
	
	
	/**
	* �̵����Ƚ�
	* @param request HttpServletRequest
	* @param form ������
	* @return ҳ��
	*/
	public String diffCompare(HttpServletRequest request, ActionForm form) {
	   String forward = "takeDiff";

	   try{
			String detailId = request.getParameter("idsAll");
			String takeNum = request.getParameter("takeNumAll");
			String stockTakeId = request.getParameter("stockTakeId");
			String flag = request.getParameter("flag");
		   
			String[] detailIds = detailId.split(",");
			String[] takeNums = takeNum.split(",");
			
			//��������ʾ�Ĳ�������
			StockTakeBo stb = StockTakeBo.getInstance();	
			ArrayList diffList = new ArrayList();
			if("1".equals(flag)){
				diffList=stb.takeDiffFirstList(stockTakeId,takeNums);
			}else if("2".equals(flag)){
				if(detailIds.length<1000) diffList=stb.takeDiffSecondList(new Long(stockTakeId),takeNums);
				else flag="out";
			}
			HttpSession session=request.getSession();
			session.removeAttribute("initList");
			session.setAttribute("takeDiffList",diffList);
						
			StockTakeForm stf=stb.getTakeInfo(new Long(stockTakeId));
			request.setAttribute("flag",flag);
			request.setAttribute("diffList",diffList);
			request.setAttribute("flowId",stockTakeId);
			request.setAttribute("flowUser",stf.getOperater().toString());
			request.setAttribute("flowTime",stf.getBeginDate().toString());
				
	   }catch(Exception e){
		   e.printStackTrace();
	   }
	   return forward;
	}
	
	
   /**
	* һ���̵�ȷ��
	* @param request HttpServletRequest
	* @param form ������
	* @return ҳ��
	*/
	public String takeFirstConfirm(HttpServletRequest request, ActionForm form) {
	   String forward = "resultMessage";
	   try{
			String stockTakeId = request.getParameter("stockTakeId");
			//String detailId = request.getParameter("idsAll");
			String takeNum = request.getParameter("takeNumAll");

			//String[] detailIds = detailId.split(",");
			String[] takeNums = takeNum.split(",");
			StockTakeBo stb = StockTakeBo.getInstance();	
			int tag=stb.firstConfirm(stockTakeId,takeNums);
			
			HttpSession session=request.getSession();
			session.removeAttribute("initList");
			
			if(tag==0){
				request.setAttribute("tag","1");
				request.setAttribute("tempData",stockTakeId);
				request.setAttribute("businessFlag","takeReport");
			}else{
				request.setAttribute("tag",tag+"");
				request.setAttribute("businessFlag","takeConfirm");
			}
				
	   }catch(Exception e){
		   e.printStackTrace();
	   }
	   return forward;
	}
	
	
	/**
	* �Զ��ƿ�
	* @param request HttpServletRequest
	* @param form ������
	* @return ҳ��
	*/
	public String autoTransfer(HttpServletRequest request, ActionForm form) {
		String forward = "resultMessage";
		
		try{
			String stockTakeId = request.getParameter("stockTakeId");
			String takeNum = request.getParameter("takeNumAll");
			String[] takeNums = takeNum.split(",");
			
			HttpSession session=request.getSession();
			
			StockTakeBo stb = StockTakeBo.getInstance();	
			int tag=stb.autoTransfer(new Long(stockTakeId),takeNums);

			
			session.removeAttribute("initList");
			
			request.setAttribute("tag",tag+"");
			request.setAttribute("businessFlag","autoTransfer");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
			
	}
	
	/**
	* ���������
	* @param request HttpServletRequest
	* @param form ������
	* @return ҳ��
	*/
	/*
	public String diffAdjust(HttpServletRequest request, ActionForm form) {
		String forward = "resultMessage";
	
		try{
			String stockTakeId = request.getParameter("stockTakeId");
			String takeNum = request.getParameter("takeNumAll");
			String[] takeNums = takeNum.split(",");
			
			HttpSession session=request.getSession();
			Long userId=(Long)session.getAttribute("userId");
			Long orgCode=(Long)session.getAttribute("orgCode");
			
			StockTakeBo stb = StockTakeBo.getInstance();	
			StockTakeForm stf=stb.getTakeInfo(new Long(stockTakeId));
			if("2".equals(stf.getDelFlag())){
				request.setAttribute("tag","-1");
				request.setAttribute("businessFlag","diffAdjust");
			}else{
				Long[] adjustIds=stb.diffAdjust(new Long(stockTakeId),takeNums,userId,orgCode);
				session.removeAttribute("initList");
				if(adjustIds!=null){
					request.setAttribute("tag","1");
					request.setAttribute("tempData",Operate.arrayToString(adjustIds));
					request.setAttribute("businessFlag","diffAdjust");
				}else{
					request.setAttribute("tag","-1");
					request.setAttribute("businessFlag","diffAdjust");
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
		
	}
	*/

	/**
	 * �����̵�ȷ��
	 * @param request HttpServletRequest
	 * @param form ������
	 * @return ҳ��
	 */
	 public String takeSecondConfirm(HttpServletRequest request, ActionForm form) {
		String forward = "resultMessage";
		try{
			 String stockTakeId = request.getParameter("stockTakeId");
			 String takeNum = request.getParameter("takeNumAll");

			 String[] takeNums = takeNum.split(",");
			 
			 StockTakeBo stb = StockTakeBo.getInstance();	
			 int tag=stb.secondConfirm(new Long(stockTakeId),takeNums);
			
			 HttpSession session=request.getSession();
			 session.removeAttribute("initList");
			
			 request.setAttribute("tag",tag+"");
			 request.setAttribute("tempData",stockTakeId);
			 request.setAttribute("businessFlag","takeReport");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
	 }
	
	/**
	* �̵�ȡ��
	* @param request HttpServletRequest
	* @param form ������
	* @return ҳ��
	*/
	public String takeCancel(HttpServletRequest request, ActionForm form) {
		String forward = "resultMessage";
		   
		try{
			String stockTakeId = request.getParameter("stockTakeId");
			
			StockTakeBo stb = StockTakeBo.getInstance();
			int tag=stb.takeCancel(new Long(stockTakeId));
			
			request.setAttribute("tag",tag+"");
			request.setAttribute("businessFlag","takeCancel");
	
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
		   
	}
	


	/**
	 * �̵��ʼexcel����
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return ҳ��
	 */
	public void takeExcelCreate(ActionMapping mapping,ActionForm form,
		HttpServletRequest request,HttpServletResponse response) {
		
		try{
			StockTakeBo stb = StockTakeBo.getInstance();
			
			StockInfoForm sifQuery=(StockInfoForm)form;
						
			String strSource = stb.initExcelCreate(sifQuery);
			if(strSource!=null){ 
				byte readFromFile[] = strSource.getBytes("GBK");
				response.setHeader("Content-disposition", "attachment; filename="+
					java.net.URLEncoder.encode("�̵�ǼǱ�","utf-8")+".xls");
				response.setBufferSize(readFromFile.length + 4096);
				response.getOutputStream().write(readFromFile);
			}

		}catch(Exception e){
			e.printStackTrace();
		}
		//return forward;
	}
	

	/**
	 * �����excel����
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return ҳ��
	 */
	public void diffExcelCreate(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		//String forward = "resultMessage";
		
		try{
			StockTakeBo stb = StockTakeBo.getInstance();
			
			HttpSession session=request.getSession();
			ArrayList diffList=(ArrayList)session.getAttribute("takeDiffList");
						
			String strSource=null;
			if(diffList!=null) strSource = stb.diffExcelCreate(diffList);
			if(strSource!=null){ 

				byte readFromFile[] = strSource.getBytes("GBK");
				response.setHeader("Content-disposition", "attachment; filename="+
					java.net.URLEncoder.encode("�̵�����","utf-8")+".xls");
				response.setBufferSize(readFromFile.length + 4096);
				response.getOutputStream().write(readFromFile);
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
   
}
