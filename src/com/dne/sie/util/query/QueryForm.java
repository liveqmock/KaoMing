package com.dne.sie.util.query;

import java.util.*;

//import com.dne.sie.util.form.GeneralDynaForm;
import org.apache.struts.action.ActionForm;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class QueryForm {
        /*   ��¼��ʼ��   */
    private String start;
    /*   ��¼������   */
    private String end;
    /*   ��ѯ�������ҵ��ʵ���FormBean    */
    private ActionForm formBean;

    /*   ��ҳ��ѯ����б�Ԫ��Ϊform   */
    private java.util.List pageList;
    /*   ���������Ĳ�ѯ�������   */
    private int allCount;
    
    private double allSum;
    
    public int getAllCount() {
        return allCount;
    }

    public String getEnd() {
        return end;
    }

    public ActionForm getFormBean() {
        return formBean;
    }

    public List getPageList() {
        return pageList;
    }

    public String getStart() {
        return start;
    }

    public void setAllCount(int allCount) {
        this.allCount = allCount;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void setFormBean(ActionForm formBean) {
        this.formBean = formBean;
    }

    public void setPageList(List pageList) {
        this.pageList = pageList;
    }

    public void setStart(String start) {
        this.start = start;
    }

	public double getAllSum() {
		return allSum;
	}

	public void setAllSum(double allSum) {
		this.allSum = allSum;
	}
 
}
