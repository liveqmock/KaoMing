package com.dne.sie.util.request.xmlhttp;

import javax.servlet.http.*;

import com.dne.sie.util.request.*;
import com.dne.sie.util.form.GeneralDynaForm;
import org.apache.struts.action.*;
import org.apache.commons.beanutils.BeanUtils;
import java.util.Enumeration;
import java.util.Map;


/**
 * Õ®”√XmlHttpΩ‚Œˆ<br>
 * @author xt
 * @version 1.1.5.6
 * @see GeneralRequestParser.java <br>
 */
public class XmlHttpParser implements RequestParser {
    public XmlHttpParser() {
    }

    /**
     * getFormBean
     *
     * @param formClass Class
     * @param request HttpServletRequest
     * @return ActionForm
     * @todo Implement this com.dne.sie.util.request.RequestParser method
     */
    public GeneralDynaForm getFormBean(ActionForm form, HttpServletRequest request) {
        GeneralDynaForm generalForm = (GeneralDynaForm) form;
        Map propMap = generalForm.getMap();
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String paramValue = request.getParameter(paramName);
//            try {
                propMap.put(paramName,paramValue);
//                BeanUtils.setProperty(form,paramName,paramValue);
//            } catch (Exception e) {
//                // modified by xt  System.out.println("param " + paramNames + " is not prop of " +
//                                   form.getClass().getName() + ".");
//            }
        }

        return generalForm;
    }

    public static void main(String[] args) {
    }
}
