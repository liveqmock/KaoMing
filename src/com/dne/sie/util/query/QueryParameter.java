package com.dne.sie.util.query;

import org.hibernate.type.*;

import com.dne.sie.common.tools.Operate;

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
public class QueryParameter {
    private String name;
    private Object value;
    private Type hbType;
    private int sqlType;
    public QueryParameter() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(Object value) {
    	if(value instanceof String){
    		this.value = Operate.fuzzyQuery((String)value);	
    	}else{
    		this.value = value;
    	}
        
    }

    public void setHbType(Type hbType) {

        this.hbType = hbType;
    }

    public void setSqlType(int sqlType) {
        this.sqlType = sqlType;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public Type getHbType() {

        return hbType;
    }

    public int getSqlType() {
        return sqlType;
    }
    
}
