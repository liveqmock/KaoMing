package com.dne.sie.support.action;

//Java 基础类
//import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;

import com.dne.sie.support.bo.AccountStatReport;
import com.dne.sie.support.bo.AccountStatisticsBo;
import com.dne.sie.support.form.AccountStatisticsForm;
import com.dne.sie.util.action.ControlAction;

/**
 * 费用统计Action处理类
 * @author xt
 * @version 1.1.5.6
 */
public class AccountStatisticsAction extends ControlAction {


    /**
     * 费用统计信息 列表页面
     * @param request HttpServletRequest
     * @param form 表单数据
     * @return 页面
     */
    public String statList(HttpServletRequest request, ActionForm form) throws Exception{
        String forward = "statList";

        AccountStatisticsForm asf=(AccountStatisticsForm)form;
        AccountStatisticsBo asb = AccountStatisticsBo.getInstance();

        asb.initCurrentMonthStatistics();
        request.setAttribute("statList",asb.statList(asf));
//		request.setAttribute("monthList",AccountStatisticsBo.getMonthList());


        return forward;
    }



    /**
     * 费用统计信息 列表页面
     * @param request HttpServletRequest
     * @param form 表单数据
     * @return 页面
     */
    public String statDetail(HttpServletRequest request, ActionForm form) {
        String forward = "statDetail";
        try{
            String accountMonth=request.getParameter("accountMonth");
            AccountStatisticsBo asb = AccountStatisticsBo.getInstance();

            Object[] obj=asb.getStatDetail(accountMonth);
            if(obj!=null&&obj.length==4){
                request.setAttribute("accountStatisticsForm",obj[0]);
                request.setAttribute("subStatList",obj[1]);
                request.setAttribute("empStatList",obj[2]);
                request.setAttribute("xfSubList",obj[3]);

                request.setAttribute("accountMonth",accountMonth);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return forward;
    }

    /**
     * 重新统计某月费用信息
     * @param request HttpServletRequest
     * @param form 表单数据
     * @return 页面
     */
    public String recountStat(HttpServletRequest request, ActionForm form) throws Exception{
        String forward = "resultMessage";

        String accountMonth=request.getParameter("accountMonth");
        AccountStatisticsBo asb = AccountStatisticsBo.getInstance();

        int tag=-1;
        if(asb.emptyStatistics(accountMonth)){
            if(asb.accountStatistics(accountMonth)){
                tag=1;
                request.getSession().removeAttribute("accountStatistics");
            }
        }
        request.setAttribute("tag", tag + "");
        request.setAttribute("businessFlag", "recountStat");

        return forward;
    }

    /**
     * 导出统计Excel报表
     * @param request HttpServletRequest
     * @param form 表单数据
     * @return 页面
     */
    public String exportStatExcel(HttpServletRequest request, ActionForm form) {
        String forward = "statList";
        try {
            HttpSession session = request.getSession();
            String accountMonth=request.getParameter("accountMonth");
            AccountStatReport asr=AccountStatReport.getInstance();

            String filePath=(String)session.getAttribute("accountStatistics");

            if(filePath!=null){
                String fileM=filePath.substring(0,filePath.indexOf("~"));
                if(!accountMonth.equals(fileM)){
                    filePath=null;
                    session.removeAttribute("accountStatistics");
                }
            }
            if(filePath==null){
                String[] query = {accountMonth};
                filePath=asr.createReportFile(query);
                session.setAttribute("accountStatistics",accountMonth+"~"+filePath);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return forward;
    }


    /**
     * 费用表信息 删除操作
     * @param request HttpServletRequest
     * @param form 表单数据
     * @return 页面
     */
    public String deleteAccountStat(HttpServletRequest request, ActionForm form) {
        String forward = "resultMessage";
        try{
            String accountMonth=request.getParameter("accountMonth");
            int tag=AccountStatisticsBo.getInstance().delete(accountMonth);
            request.setAttribute("tag", tag + "");
            request.setAttribute("businessFlag", "deleteAccountStat");
        }catch(Exception e){
            e.printStackTrace();
        }
        return forward;
    }



}
