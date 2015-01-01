package com.dne.sie.maintenance.action;

import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.dne.sie.common.tools.EscapeUnescape;
import com.dne.sie.common.tools.Operate;
import com.dne.sie.maintenance.bo.MachineToolBo;
import com.dne.sie.maintenance.form.MachineToolForm;
import com.dne.sie.util.action.ControlAction;

public class MachineToolAction extends ControlAction {



    /**
     * 客户表信息 列表页面
     * @param request HttpServletRequest
     * @param form 表单数据
     * @return 页面
     */
    public String machineToolList(HttpServletRequest request, ActionForm form) throws Exception{
        String forward = "machineToolList";
        MachineToolForm pif=(MachineToolForm)form;
        MachineToolBo pib = MachineToolBo.getInstance();
        request.setAttribute("machineToolList",pib.list(pif));

        return forward;
    }


    /**
     * 客户表信息 修改页面
     * @param request HttpServletRequest
     * @param form 表单数据
     * @return 页面
     */
    public String machineToolEdit(HttpServletRequest request, ActionForm form) {
        String forward = "machineToolEdit";
        try{
            String machineId=request.getParameter("ids");
            if(machineId!=null&&!machineId.isEmpty()){
                request.setAttribute("machineToolForm", MachineToolBo.getInstance().find(new Long(machineId)));
            }
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
    public String insertMachineTool(HttpServletRequest request, ActionForm form) {
        String forward = "resultMessage";
        try{
            MachineToolForm pif=(MachineToolForm)form;
            pif.setCreateBy((Long)request.getSession().getAttribute("userId"));
            MachineToolBo cibo = MachineToolBo.getInstance();
            pif.setPurchaseDate(Operate.toDate(pif.getPurchaseDateStr()));
            pif.setExtendedWarrantyDate(Operate.toDate(pif.getExtendedWarrantyDateStr()));
            int tag=cibo.add(pif);
            request.setAttribute("tag", tag + "");
            request.setAttribute("businessFlag", "saveMachineTool");

        }catch(Exception e){
            e.printStackTrace();
        }
        return forward;
    }


    /**
     * 客户表信息 修改操作
     * @param request HttpServletRequest
     * @param form 表单数据
     * @return 页面
     */
    public String updateMachineTool(HttpServletRequest request, ActionForm form) {
        String forward = "resultMessage";
        try{
            MachineToolForm pif=(MachineToolForm)form;
            MachineToolBo cibo = MachineToolBo.getInstance();
            pif.setUpdateBy((Long)request.getSession().getAttribute("userId"));
            pif.setUpdateDate(new Date());
            pif.setPurchaseDate(Operate.toDate(pif.getPurchaseDateStr()));
            pif.setExtendedWarrantyDate(Operate.toDate(pif.getExtendedWarrantyDateStr()));
            int tag=cibo.modify(pif);
            request.setAttribute("tag", tag + "");
            request.setAttribute("businessFlag", "saveMachineTool");


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
    public String deleteMachineTool(HttpServletRequest request, ActionForm form) {
        String forward = "resultMessage";
        try{
            String sysId=request.getParameter("ids");
            int tag=MachineToolBo.getInstance().delete(sysId);
            request.setAttribute("tag", tag + "");
            request.setAttribute("businessFlag", "saveMachineTool");
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
    public void ajaxChkSerial(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) {
        PrintWriter writer = null;
        try{
            String machineId=request.getParameter("machineId");
            String serialNo=request.getParameter("serialNo");
            MachineToolBo ubo = MachineToolBo.getInstance();
            String strXml="false";
            if(ubo.chkSerial(machineId,serialNo)){
                strXml="true";
            }

            writer = response.getWriter();
            response.setContentType("text/xml");
            response.setHeader("Cache-Control", "no-cache");
            writer.println("<xml>");

            writer.println("<ifUse>"+strXml+"</ifUse>");

            writer.println("</xml>");


        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(writer!=null){
                writer.flush();
                writer.close();
            }
        }

    }


    public String getSerialInfo(ActionMapping mapping, ActionForm form,
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
            List machineList = MachineToolBo.getInstance().getSerialListByName(inputValue);

            String modelCode = "";
            String serialNo = "";
            String warrantyCardNo = "";
            String purchaseDate = "";
            String extendedWarrantyDate = "";

            String StrongDealerName = "";

            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < machineList.size(); i++) {
                Object[] obj = (Object[]) machineList.get(i);

                modelCode = obj[0].toString();
                serialNo = obj[1].toString();
                warrantyCardNo = obj[2]==null?"":obj[2].toString();
                purchaseDate = obj[3]==null?"":Operate.trimDate((Date)obj[3]);
                extendedWarrantyDate = obj[4]==null?"":Operate.trimDate((Date)obj[4]);

                if(serialNo.indexOf(inputValue) != -1) {
                    //把输入的值和数据库的数据比较后,加粗
                    StrongDealerName = serialNo.replaceAll(inputValue, "<span class=\"boldfont\">" + inputValue + "</span>");

                    buffer.append("<div onselect=\"this.text.value = '")
                            .append(serialNo)
                            .append("';document.forms[0].modelCode.value = '")
                            .append(modelCode)
                            .append("';document.forms[0].warrantyCardNo.value = '")
                            .append(warrantyCardNo)
                            .append("';document.forms[0].purchaseDateStr.value = '")
                            .append(purchaseDate)
                            .append("';document.forms[0].extendedWarrantyDate.value = '")
                            .append(extendedWarrantyDate)
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
