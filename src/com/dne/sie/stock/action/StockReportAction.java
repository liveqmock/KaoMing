package com.dne.sie.stock.action;

//Java 基础类
import java.util.ArrayList;

//Java 扩展类
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


//第三方类
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.log4j.Logger;


//自定义类
import com.dne.sie.util.action.ControlAction;
import com.dne.sie.maintenance.action.AttachedInfoAction;
import com.dne.sie.common.tools.CommonSearch;
import com.dne.sie.common.tools.Operate;
import com.dne.sie.stock.form.StockTakeDetailForm;
import com.dne.sie.stock.form.StockTakeForm;
import com.dne.sie.stock.form.StockTakeReportForm;
import com.dne.sie.stock.bo.StockTakeBo;
import com.dne.sie.stock.bo.StockInBo;
import com.dne.sie.stock.bo.StockInfoListBo;
import com.dne.sie.stock.form.StockInfoForm;
import com.dne.sie.common.exception.ReportException;


/**
 * 库存报表Action处理类
 * @author xt
 * @version 1.1.5.6
 * @see StockReportAction.java <br>
 */
public class StockReportAction extends ControlAction{
	
	
	/**
	* 出库操作 列表页面
	* @param request HttpServletRequest
	* @param form 表单数据
	* @return 页面
	*/
	public String takeReport(HttpServletRequest request, ActionForm form) {
		String forward = "reportList";
	
		try{
			StockTakeForm stf=(StockTakeForm)form;
			StockTakeBo stb = StockTakeBo.getInstance();
			request.setAttribute("stockTakeForm",stf);
			request.setAttribute("stockReprotList",stb.stockReprotList(stf));

		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
	}
	
	/**
	* 盘点报表查询 
	* @param request HttpServletRequest
	* @param form 表单数据
	* @return 页面
	*/
	public String takeReportDetail(HttpServletRequest request, ActionForm form) {
		String forward = "reportDetailList";
	
		try{
			StockTakeForm tf=(StockTakeForm)form;
			
			StockTakeReportForm stf=new StockTakeReportForm();

			String stockTakeId=request.getParameter("stockTakeId");
			if(stockTakeId!=null&&!stockTakeId.equals("")){
				stf.setStockTakeId(new Long(stockTakeId));
				HttpSession session=request.getSession();
				Long userId=(Long)session.getAttribute("userId");
					
				String userName=CommonSearch.getInstance().findUserNameByUserId(userId);
			
				StockTakeBo stb = StockTakeBo.getInstance();
				String [] mainTake=stb.stockReportStat(stf);

				ArrayList  rptList=stb.stockReportDetailList(stf,tf);
				request.setAttribute("userName",userName);
				request.setAttribute("curDate",Operate.getDate());
				request.setAttribute("stockReprotList",rptList);
				request.setAttribute("stockReportStat",mainTake);
				request.setAttribute("stockTakeId",stockTakeId);
			}
	    
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
	}
	

	/**
	 * 库存盘点报表excel生成
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return 
	 */
	public String takeReportExcel(ActionMapping mapping,ActionForm form,
		HttpServletRequest request,HttpServletResponse response) throws ReportException {

		String forward="reportDetailList";
		try {

			StockTakeReportForm stf=new StockTakeReportForm();
			String stockTakeId=request.getParameter("stockTakeId")==null?"0":request.getParameter("stockTakeId");

			stf.setStockTakeId(new Long(stockTakeId));
			StockTakeBo bo = StockTakeBo.getInstance();
			
			HttpSession session=request.getSession();
			Long userId=(Long)session.getAttribute("userId");
					
			String userName=CommonSearch.getInstance().findUserNameByUserId(userId);

			String filePath = bo.takeReportExcel(stf,userName);
			request.setAttribute("filePath",filePath);

		} catch (ReportException re) {
			forward="reportErr";
			request.setAttribute("reportErr",re.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return forward;
	}
   
}
