package com.dne.sie.util.query;

import java.util.*;


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
public class AdvQueryString {
    private String queryString;
    private ArrayList parameters;
    public AdvQueryString() {
        super();
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public void setParameters(ArrayList parameters) {
        this.parameters = parameters;
    }

    public String getQueryString() {
        return queryString;
    }

    public ArrayList getParameters() {
        return parameters;
    }

}
