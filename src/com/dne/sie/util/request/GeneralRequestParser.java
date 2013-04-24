package com.dne.sie.util.request;

import com.dne.sie.util.form.*;
import com.dne.sie.util.request.xmlhttp.XmlHttpParser;
import com.dne.sie.util.request.httpRequest.HttpRequestParser;
import org.apache.struts.action.*;
import javax.servlet.http.*;

/**
 * ͨ��request����<br>
 * ����form��requestͳһ����xmlhttp���󡢼�httprequest�����form�ύ
 * @author xt
 * @version 1.1.5.6
 * @see GeneralRequestParser.java <br>
 */
public class GeneralRequestParser {
    public GeneralRequestParser() {
    }

    /**
     * ��form��request�н������GeneralDynaForm
     * @param form ActionForm formԴ
     * @param request HttpServletRequest request����
     * @return GeneralDynaForm ������ó��Ķ�̬form
     * @throws Exception
     */
    public static GeneralDynaForm getFormBean(ActionForm form,
                                              HttpServletRequest request) throws
            Exception {
        if (form instanceof GeneralDynaForm) {
            if (form instanceof XmlHttpForm) {//Ŀǰ�ݶ�Ϊ���е�xmlhttp����ҳ�涼Ҫ��xmlhttpForm���
                return new XmlHttpParser().getFormBean(form, request);
            }
            if (form instanceof HttpRequestForm) {//���м������ҳ�涼Ҫʹ��HttpRequestForm
                return new HttpRequestParser().getFormBean(form, request);
            }
            return (GeneralDynaForm) form;
        } else {
            throw new Exception("����formʱ����formbean����GeneralDynaForm��");
        }
    }

    public static void main(String[] args) {
        GeneralRequestParser generalrequestparser = new GeneralRequestParser();
    }
}
