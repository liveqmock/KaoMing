package com.dne.sie.util.query;

import java.sql.*;
import java.util.*;
import java.io.*;

import com.dne.sie.util.hibernate.*;
import org.hibernate.*;
//import com.dne.sie.util.form.*;
import org.apache.struts.action.ActionForm;
import com.dne.sie.common.tools.LayOut;

/**
 * <p>Title: QUERY父类</p>
 *
 * <p>Description: 所有的query类都要继承该类，并实现三个方法<br>
 *    generateCountQuery 生成统计查询结果的查询语句
 *    generateListQuery  生成查询列表的查询语句
 *    mapping2FormList   将查询结果转为相应的form的列表
 *    需要设置标志位connType，用于判断是用hibernate或直接用JDBC
 * </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public abstract class QueryBean extends HbConn{

    /*   记录开始行   */
//    private String strStart;
    /*   记录结束行   */
//    private String strEnd;
    /*   查询条件存放的FormBean    */
//    private ActionForm formBean;

    /*   分页查询结果列表，元素为form   */
//    private java.util.ArrayList pageList;
    /*   符合条件的查询结果总数   */
//    private int allCount;

    /*   查询结果   */
    private QueryForm queryForm;
    /*   查询语句   */
    private AdvQueryString listQuery;
    /*   统计语句   */
    private AdvQueryString countQuery;
    
    private AdvQueryString sumQuery;
    /*   数据库访问方式，通过hibernate，或者直接用JDBC   */
    private int connType;
    public static final int JDBC = 1;
    public static final int HIBERNATE = 2;


	public QueryBean() {
		
		this.queryForm = new QueryForm();

	}    
    
    /**
    *
    * @param form GeneralDynaForm
    */
   public QueryBean(ActionForm form) {
       if (this.connType != QueryBean.HIBERNATE && this.connType != QueryBean.JDBC) {
           this.connType = QueryBean.HIBERNATE;
       }
       this.queryForm = new QueryForm();
       this.queryForm.setFormBean(form);
   }

   public QueryBean(ActionForm form, String start, String end) {
       this(form);
       doCountQuery();
       doListQuery(start,end);
   }

    /**
     * 生成查询列表语句
     * @param form GeneralDynaForm
     * @return AdvQueryString 查询列表语句
     */
    protected abstract AdvQueryString generateListQuery(ActionForm form);

    /**
     * 生成统计总数的语句
     * @param form GeneralDynaForm
     * @return AdvQueryString 统计条件语句
     */
    protected abstract AdvQueryString generateCountQuery(ActionForm form);
    
    protected AdvQueryString getSumFeeHql(ActionForm form){
    	return null;
    }

    /**
     * mapping2FormList
     * 将查询结果转为form列表，处理映射关系
     * @param list List
     * @return ArrayList
     */
    //protected abstract ArrayList mapping2FormList(List list);

    /**
     * 统计总记录数
     * @return int 记录总数
     */
    public int doCountQuery(){
        this.countQuery = generateCountQuery(this.queryForm.getFormBean());
        try{
            if (this.connType == QueryBean.JDBC) {
                countAllWithJDBC();
            } else {
                countAllWithHb();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return this.getAllCount();
    }

	/**
	 * 查询sum结果数
	 * @param start String 起始记录
	 * @param end String 终止记录
	 * @return ArrayList 结果列表
	 */
	public double doSumQuery(){
		this.sumQuery = getSumFeeHql(this.queryForm.getFormBean());
		try{
			loadSumWithHb();
			
		}catch(Exception e){
			e.printStackTrace();
		}

		return this.getAllSum();
	}

    /**
     * 查询获取结果列表
     * @param start String 起始记录
     * @param end String 终止记录
     * @return ArrayList 结果列表
     */
    public List doListQuery(String start,String end){
        this.queryForm.setStart(start);
        this.queryForm.setEnd(end);
        this.listQuery = generateListQuery(this.queryForm.getFormBean());
        try{
            if (this.connType == QueryBean.JDBC) {
                loadPageListWithJDBC();
            } else {
                loadPageListWithHb();
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return this.getAllList();
    }


	
	/**
	 * 查询获取结果列表
	 * @param start String 起始记录
	 * @param end String 终止记录
	 * @return ArrayList 结果列表
	 */
	public List doListQueryReverse(String start,String end,int count){
		int[] reverse=queryReverse(Integer.parseInt(start),Integer.parseInt(end),count);
		this.queryForm.setStart(reverse[0]+"");
		this.queryForm.setEnd(reverse[1]+"");
		this.listQuery = generateListQuery(this.queryForm.getFormBean());
		try{
			if (this.connType == QueryBean.JDBC) {
				loadPageListWithJDBC();
			} else {
				loadPageListWithHb();
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		return this.getAllList();
	}

	/**
	 * 翻页的时候将查询结果反向取分页数
	 * 等效于将order by 加desc，再分页
	 */
	public int[] queryReverse(int start,int end,int count){
		int[] intRet=new int[2];
		try{
			int tempStart=count-end+1;
			int tempEnd=count-start+1;
			intRet[0]=tempStart;
			intRet[1]=tempEnd;
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return intRet;
	}

	/**
	 * 用JDBC和SQL方式来统计总记录数
	 * @return int 记录总数
	 */
	public int doCountQuery(String sql){
		if(!"".equals(sql)){
			this.listQuery = new AdvQueryString();
			this.listQuery.setQueryString(sql) ;
			ArrayList noUseList = new ArrayList();
			this.listQuery.setParameters(noUseList);
		}else{
			this.countQuery = generateCountQuery(this.queryForm.getFormBean());
		}
		
		try{
			this.connType = QueryBean.JDBC;
			countAllWithJDBC();
		}catch(Exception e){
			e.printStackTrace();
		}
		return this.getAllCount();
	}

	/**
	 * 用JDBC和SQL方式查询获取结果列表
	 * @param start String 起始记录
	 * @param end String 终止记录
	 * @return ArrayList 结果列表
	 */
	public List doListQuery(String start,String end,String sql){
		if(!"".equals(sql)){
			this.queryForm.setStart(start);
			this.queryForm.setEnd(end);
			this.listQuery = new AdvQueryString();
			this.listQuery.setQueryString(sql) ;
			ArrayList noUseList = new ArrayList();
			this.listQuery.setParameters(noUseList);
		}else{
			this.queryForm.setStart(start);
			this.queryForm.setEnd(end);
			this.listQuery = generateListQuery(this.queryForm.getFormBean());
		}
		try{			
			this.connType = QueryBean.JDBC;
			loadPageListWithJDBC();			
		}catch(Exception e){
			e.printStackTrace();
		}

		return this.getAllList();
	}    
	/**
	 * 查询获取结果列表
	 * @param start String 起始记录
	 * @param end String 终止记录
	 * @return ArrayList 结果列表
	 */
	public List doListQuery(){
		this.listQuery = generateListQuery(this.queryForm.getFormBean());
		try{
			if (this.connType == QueryBean.JDBC) {
				loadPageListWithJDBC();
			} else {
				loadPageListWithHb();
			}

		}catch(Exception e){
			e.printStackTrace();
		}

		return this.getAllList();
	}

	 

    /**
     * loadPageListWithJDBC
     *
     */
    private void loadPageListWithJDBC() throws Exception {
        List list = new ArrayList();
        Connection conn;
        PreparedStatement pstmt = null;
		HbConn hbConn=new HbConn();
	
		
        ResultSet rs = null;
        try {
			//System.out.println("-----------------selectSql="+this.listQuery.getQueryString());
            conn = hbConn.getSession().connection();            
            pstmt = conn.prepareStatement(this.listQuery.getQueryString(),ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            for (int i = 0; i < this.listQuery.getParameters().size(); i++) {
                QueryParameter param = (QueryParameter)this.listQuery.getParameters().get(i);
                pstmt.setObject(i, param.getValue(), param.getSqlType());
            }
            rs = pstmt.executeQuery();
            ResultSetMetaData rsmt = rs.getMetaData();
			
            //System.out.println("this.queryForm.getStart()======"+this.queryForm.getStart());
			//System.out.println("this.queryForm.getEnd()======"+this.queryForm.getEnd());
			
            if(rs.absolute(Integer.parseInt(this.queryForm.getStart()))){            
	            int rowLeft = Integer.parseInt(this.queryForm.getEnd()) -
	                          Integer.parseInt(this.queryForm.getStart() )+ 1;
				//System.out.println("rowLeft======"+rowLeft);
				
				
				while ( !rs.isAfterLast() && rowLeft > 0) {
					HashMap row = new HashMap();
					//System.out.println("rsmt.getColumnCount() ============================= "+rsmt.getColumnCount());
					for (int i = 1; i <= rsmt.getColumnCount(); i++) {                	
						String colName = rsmt.getColumnName(i);
						String colValue = rs.getString(colName);
						if(colValue==null) colValue="";
						//System.out.println("第"+i+"行 rsmt.getColumnName(i)================ "+rsmt.getColumnName(i));
						//System.out.println("第"+i+"行 colValue================ "+colValue);

						//row.put(colName, colValue);
						row.put( i+"" , colValue);
					}
					list.add(row);
					rowLeft--;
					rs.next();
				}
				
            }
            /*
            if(list!=null){
				int count=list.size();
				if(count>500){
					try{
						throw new Exception("loadPageListWithJDBC more:"+count);
					}catch (Exception e) {
						LayOut lo=new LayOut("affix/more100list.log");
						lo.outTrace(e);
					}
	
				}
            }
			*/
			//将查询结果转为form列表
			this.queryForm.setPageList(list);
            
        } catch (Exception e) {
            throw e;
        } finally {
        	try{
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
        	}catch(Exception e2){
        		e2.printStackTrace();
        	}finally{
				hbConn.closeSession();
        	}
        }
      

    }

    /**
     * countAllWithJDBC
     */
    private void countAllWithJDBC() throws Exception {
        int count = 0;
        HbConn hbConn=new HbConn();
        Connection conn;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = hbConn.getSession().connection();
			
            pstmt = conn.prepareStatement(this.countQuery.getQueryString());
            for (int i = 0; i < this.listQuery.getParameters().size(); i++) {
                QueryParameter param = (QueryParameter)this.listQuery.
                                       getParameters().get(i);
                pstmt.setObject(i, param.getValue(), param.getSqlType());
            }
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);   
            }

        } catch (Exception e) {
            throw e;
        } finally {
			try{
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
			}catch(Exception e2){
				e2.printStackTrace();
			}finally{
				hbConn.closeSession();
			}
        }

        this.queryForm.setAllCount(count);

    }

    /**
     * loadPageListWithHb 执行查询，获取列表
     */
    private void loadPageListWithHb() throws Exception {
        List list;
		HbConn hbConn=new HbConn();
        try {
        	String temp=null;
            Query q = hbConn.getSession().createQuery(this.listQuery.getQueryString());
            for (int i = 0; i < this.listQuery.getParameters().size(); i++) {
                QueryParameter param = (QueryParameter)this.listQuery.
                                       getParameters().get(i);
                
				if(param.getValue()!=null&&param.getHbType().equals(Hibernate.STRING)){
					temp=(String)param.getValue();
					if(temp.endsWith("%")){
						temp=temp.substring(0,temp.length()-1).trim()+"%";
					}else{
						temp=temp.trim();
					}
					q.setParameter(param.getName(), temp,param.getHbType());
				}else{
					q.setParameter(param.getName(), param.getValue(),param.getHbType());
				}
            }
			
            if(this.queryForm.getStart()!=null&&this.queryForm.getEnd()!=null
            	&&!this.queryForm.getStart().equals("")&&!this.queryForm.getEnd().equals("")){
            	q.setFirstResult(Integer.parseInt(this.queryForm.getStart())-1);
           		int maxReust=Integer.parseInt(this.queryForm.getEnd()) -Integer.parseInt(this.queryForm.getStart()) + 1;
           		if(maxReust>10000) maxReust=10000;
            	q.setMaxResults(maxReust);
            	
       		}else{
				q.setMaxResults(60000);
       		}
            list = q.list();
           
        } catch (HibernateException e) {
            throw e;
        } finally {
            hbConn.closeSession();
        }
        //将查询结果转为form列表
        this.queryForm.setPageList(list);
    }


	

    /**
     * countAllWithHb 执行查询，获取总数
     */
    private void countAllWithHb() throws Exception {
        int count = 0;
		HbConn hbConn=new HbConn();
        try {
            Query q = hbConn.getSession().createQuery(this.countQuery.getQueryString());
            String temp=null;
            for (int i = 0; i < this.countQuery.getParameters().size(); i++) {
                QueryParameter param = (QueryParameter)this.countQuery.
                                       getParameters().get(i);
                
				if(param.getValue()!=null&&param.getHbType().equals(Hibernate.STRING)){
					temp=(String)param.getValue();
					if(temp.endsWith("%")){
						temp=temp.substring(0,temp.length()-1).trim()+"%";
					}else{
						temp=temp.trim();
					}
					q.setParameter(param.getName(), temp,param.getHbType());
				}else{
					q.setParameter(param.getName(), param.getValue(),param.getHbType());
				}
				
            }
            ArrayList result = new ArrayList(q.list());

            for (int i = 0; i < result.size(); i++) {
                count = ((Long) result.get(i)).intValue();
            }

        } catch (HibernateException e) {
            throw e;
        } finally {
            hbConn.closeSession();
        }

        this.queryForm.setAllCount(count);

    }
    

    /**
     * countAllWithHb 执行查询，获取总数
     */
    private void loadSumWithHb() throws Exception {
        double allSum = 0;
		HbConn hbConn=new HbConn();
        try {
            Query q = hbConn.getSession().createQuery(this.sumQuery.getQueryString());
            String temp=null;
            for (int i = 0; i < this.sumQuery.getParameters().size(); i++) {
                QueryParameter param = (QueryParameter)this.sumQuery.getParameters().get(i);
                
				if(param.getValue()!=null&&param.getHbType().equals(Hibernate.STRING)){
					temp=(String)param.getValue();
					if(temp.endsWith("%")){
						temp=temp.substring(0,temp.length()-1).trim()+"%";
					}else{
						temp=temp.trim();
					}
					q.setParameter(param.getName(), temp,param.getHbType());
				}else{
					q.setParameter(param.getName(), param.getValue(),param.getHbType());
				}
				
            }
            ArrayList result = new ArrayList(q.list());
            if(result!=null&&result.size()>0){
            	allSum = result.get(0)==null?0:((Double) result.get(0)).doubleValue();
            }

        } catch (HibernateException e) {
            throw e;
        } finally {
            hbConn.closeSession();
        }

        this.queryForm.setAllSum(allSum);

    }


    /**
     * 得到符合查询条件的记录数，如果无符合的记录，返回0
     * @return int型，查询的记录数
     */
    public int getAllCount() {
        return this.queryForm.getAllCount();
    }
    public double getAllSum() {
        return this.queryForm.getAllSum();
    }

    /**
     * 得到符合查询条件的记录，放于ArrayList内，ArrayList内的元素为form对象。如果无符合查询条件的记录，返回大小为0的ArrayList
     * @return ArrayList对象，存放符合查询条件的记录
     */
    public List getAllList() {
        return this.queryForm.getPageList();
    }

    /**
     * 设置查询条件的FormBean，使查询条件可以在Jsp中直接生成并赋值
     * @param formBean
     */
    public void setFormBean(ActionForm formBean) {
        this.queryForm.setFormBean(formBean);
    }

    public ActionForm getFormBean() {
        return this.queryForm.getFormBean();
    }

    public void setStart(String start) {
        this.queryForm.setStart(start);
    }

    public String getStart() {
        return this.queryForm.getStart();
    }

    public void setEnd(String end) {
        this.queryForm.setEnd(end);
    }

    public String getEnd() {
        return this.queryForm.getEnd();
    }

    public java.util.List getPageList() {

        return this.queryForm.getPageList();
    }

    public AdvQueryString getCountQuery() {
        return countQuery;
    }

    public AdvQueryString getListQuery() {
        return listQuery;
    }

    public int getConnType() {
        return connType;
    }

    public QueryForm getQueryForm() {
        return queryForm;
    }

    public void setPageList(java.util.ArrayList pageList) {

        this.queryForm.setPageList(pageList);
    }

    public void setCountQuery(AdvQueryString countQuery) {
        this.countQuery = countQuery;
    }

    public void setListQuery(AdvQueryString listQuery) {
        this.listQuery = listQuery;
    }

    public void setConnType(int connType) {
        this.connType = connType;
    }

    public void setQueryForm(QueryForm queryForm) {
        this.queryForm = queryForm;
    }

    
}
