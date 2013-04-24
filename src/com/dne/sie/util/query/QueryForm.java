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
        /*   记录开始行   */
    private String start;
    /*   记录结束行   */
    private String end;
    /*   查询条件相关业务实体的FormBean    */
    private ActionForm formBean;

    /*   分页查询结果列表，元素为form   */
    private java.util.List pageList;
    /*   符合条件的查询结果总数   */
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
