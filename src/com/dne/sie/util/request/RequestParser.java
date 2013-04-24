package com.dne.sie.util.request;

import org.apache.struts.action.ActionForm;
import com.dne.sie.util.form.GeneralDynaForm;
import javax.servlet.http.HttpServletRequest;

/**
 * 通用request接口
 * @author xt
 * @version 1.1.5.6
 * @see RequestParser.java <br>
 */
public interface RequestParser {
    public GeneralDynaForm getFormBean(ActionForm form,HttpServletRequest request);
}
