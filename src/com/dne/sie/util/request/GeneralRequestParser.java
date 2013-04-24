package com.dne.sie.util.request;

import com.dne.sie.util.form.*;
import com.dne.sie.util.request.xmlhttp.XmlHttpParser;
import com.dne.sie.util.request.httpRequest.HttpRequestParser;
import org.apache.struts.action.*;
import javax.servlet.http.*;

/**
 * 通用request解析<br>
 * 根据form与request统一解析xmlhttp请求、简单httprequest请求和form提交
 * @author xt
 * @version 1.1.5.6
 * @see GeneralRequestParser.java <br>
 */
public class GeneralRequestParser {
    public GeneralRequestParser() {
    }

    /**
     * 从form和request中解析获得GeneralDynaForm
     * @param form ActionForm form源
     * @param request HttpServletRequest request请求
     * @return GeneralDynaForm 解析后得出的动态form
     * @throws Exception
     */
    public static GeneralDynaForm getFormBean(ActionForm form,
                                              HttpServletRequest request) throws
            Exception {
        if (form instanceof GeneralDynaForm) {
            if (form instanceof XmlHttpForm) {//目前暂定为所有的xmlhttp请求页面都要以xmlhttpForm打包
                return new XmlHttpParser().getFormBean(form, request);
            }
            if (form instanceof HttpRequestForm) {//所有简单请求的页面都要使用HttpRequestForm
                return new HttpRequestParser().getFormBean(form, request);
            }
            return (GeneralDynaForm) form;
        } else {
            throw new Exception("解析form时出错：formbean不是GeneralDynaForm！");
        }
    }

    public static void main(String[] args) {
        GeneralRequestParser generalrequestparser = new GeneralRequestParser();
    }
}
