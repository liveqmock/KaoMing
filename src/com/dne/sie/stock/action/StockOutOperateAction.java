package com.dne.sie.stock.action;


import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.dne.sie.stock.bo.StockOutBo;
import com.dne.sie.stock.form.StockInfoForm;
import com.dne.sie.util.action.ControlAction;


/**
 * 出库操作Action处理类
 * @author xt
 * @version 1.1.5.6
 * @see StockOutOperateAction <br>
 */
public class StockOutOperateAction extends ControlAction{


    /**
     * 库存调整出库 列表页面
     * @param request HttpServletRequest
     * @param form 表单数据
     * @return 页面
     */
    public String stockAdjustOutList(HttpServletRequest request, ActionForm form) {
        String forward = "stockAdjustOutList";
        try{
            StockInfoForm sif=(StockInfoForm)form;

            StockOutBo sob = StockOutBo.getInstance();
            request.setAttribute("stockInfoList",sob.adjustOutList(sif));
//            request.setAttribute("binList",sob.adjustOutList(sif));

        }catch(Exception e){
            e.printStackTrace();
        }
        return forward;
    }

    public String stockBinMoveList(HttpServletRequest request, ActionForm form) {
        String forward = "stockBinMoveList";
        try{
            StockInfoForm sif=(StockInfoForm)form;

            StockOutBo sob = StockOutBo.getInstance();
            request.setAttribute("stockInfoList",sob.stockBinMoveList(sif));

        }catch(Exception e){
            e.printStackTrace();
        }
        return forward;
    }

    /**
     * 库存调整出库 确认
     * @param request HttpServletRequest
     * @param form 表单数据
     * @return 页面
     */
    public String stockAdjustOutConfirm(HttpServletRequest request, ActionForm form) {
        String forward = "resultMessage";

        int tag =-1;
        try{

            String strIds= request.getParameter("ids");
            String outNum = request.getParameter("adOutNum");
            String outRemark = request.getParameter("adOutRemart");
            String customerName = request.getParameter("customerName");
            String[] outNums = outNum.split(",");
            String[] outRemarks = outRemark.split(",");

            StockOutBo sob = StockOutBo.getInstance();
            if(strIds!=null){
                tag = sob.ajustOut(strIds,outNums,outRemarks,customerName);
            }

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            request.setAttribute("tag", tag + "");
            request.setAttribute("businessFlag", "stockAdjustOutConfirm");
        }
        return forward;
    }



    public String stockTaxAdjustOutList(HttpServletRequest request, ActionForm form) throws Exception{

        StockInfoForm sif=(StockInfoForm)form;

        StockOutBo sob = StockOutBo.getInstance();
        request.setAttribute("stockInfoList",sob.adjustOutTaxList(sif));

        return "stockTaxAdjustOutList";
    }

    public String stockAdjustTaxOutConfirm(HttpServletRequest request, ActionForm form) {
        String forward = "resultMessage";

        int tag =-1;
        try{

            String strIds= request.getParameter("ids");
            String outNum = request.getParameter("adOutNum");
            String adOutCost = request.getParameter("adOutCost");

            String[] outNums = outNum.split(",");
            String[] adOutCosts = adOutCost.split(",");

            StockOutBo sob = StockOutBo.getInstance();
            if(strIds!=null){
                tag = sob.ajustTaxOut(strIds,outNums,adOutCosts);
            }

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            request.setAttribute("tag", tag + "");
            request.setAttribute("businessFlag", "stockAdjustTaxOutConfirm");
        }
        return forward;
    }



    public void ajaxMoveBin(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) {
        PrintWriter writer = null;

        try{
            String id=request.getParameter("id");
            String binCode=request.getParameter("binCode");
            StockOutBo sob = StockOutBo.getInstance();
            String strXml="false";
            if(sob.moveBin(new Long(id),binCode)){
                strXml="true";
            }

            writer = response.getWriter();
            response.setContentType("text/xml");
            response.setHeader("Cache-Control", "no-cache");
            writer.println("<xml>");

            writer.println("<result>"+strXml+"</result>");

            writer.println("</xml>");


        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(writer!=null) writer.flush();
            if(writer!=null) writer.close();
        }

    }

}
