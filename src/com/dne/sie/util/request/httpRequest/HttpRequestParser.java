package com.dne.sie.util.request.httpRequest;

import javax.servlet.http.*;

import com.dne.sie.util.form.*;
import com.dne.sie.util.request.*;
import org.apache.struts.action.*;
import java.util.Map;
import java.util.Enumeration;

/**
 * request½âÎö
 * @author xt
 * @version 1.1.5.6
 * @see HttpRequestParser.java <br>
 */
public class HttpRequestParser implements RequestParser {
    public HttpRequestParser() {
    }

    /**
     * getFormBean
     *
     * @param form ActionForm
     * @param request HttpServletRequest
     * @return GeneralDynaForm
     * @todo Implement this com.dne.sie.util.request.RequestParser method
     */
    public GeneralDynaForm getFormBean(ActionForm form,
                                       HttpServletRequest request) {
            GeneralDynaForm generalForm = (GeneralDynaForm) form;
            Map propMap = generalForm.getMap();
            Enumeration paramNames = request.getParameterNames();
            while (paramNames.hasMoreElements()) {
                String paramName = (String) paramNames.nextElement();
                String paramValue = request.getParameter(paramName);
                propMap.put(paramName, paramValue);
            }
            return generalForm;

        }

        public static void main(String[] args) {
        }
    }
