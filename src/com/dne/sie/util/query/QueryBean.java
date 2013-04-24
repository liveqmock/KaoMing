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
 * <p>Title: QUERY����</p>
 *
 * <p>Description: ���е�query�඼Ҫ�̳и��࣬��ʵ����������<br>
 *    generateCountQuery ����ͳ�Ʋ�ѯ����Ĳ�ѯ���
 *    generateListQuery  ���ɲ�ѯ�б�Ĳ�ѯ���
 *    mapping2FormList   ����ѯ���תΪ��Ӧ��form���б�
 *    ��Ҫ���ñ�־λconnType�������ж�����hibernate��ֱ����JDBC
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

    /*   ��¼��ʼ��   */
//    private String strStart;
    /*   ��¼������   */
//    private String strEnd;
    /*   ��ѯ������ŵ�FormBean    */
//    private ActionForm formBean;

    /*   ��ҳ��ѯ����б�Ԫ��Ϊform   */
//    private java.util.ArrayList pageList;
    /*   ���������Ĳ�ѯ�������   */
//    private int allCount;

    /*   ��ѯ���   */
    private QueryForm queryForm;
    /*   ��ѯ���   */
    private AdvQueryString listQuery;
    /*   ͳ�����   */
    private AdvQueryString countQuery;
    
    private AdvQueryString sumQuery;
    /*   ���ݿ���ʷ�ʽ��ͨ��hibernate������ֱ����JDBC   */
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
     * ���ɲ�ѯ�б����
     * @param form GeneralDynaForm
     * @return AdvQueryString ��ѯ�б����
     */
    protected abstract AdvQueryString generateListQuery(ActionForm form);

    /**
     * ����ͳ�����������
     * @param form GeneralDynaForm
     * @return AdvQueryString ͳ���������
     */
    protected abstract AdvQueryString generateCountQuery(ActionForm form);
    
    protected AdvQueryString getSumFeeHql(ActionForm form){
    	return null;
    }

    /**
     * mapping2FormList
     * ����ѯ���תΪform�б�����ӳ���ϵ
     * @param list List
     * @return ArrayList
     */
    //protected abstract ArrayList mapping2FormList(List list);

    /**
     * ͳ���ܼ�¼��
     * @return int ��¼����
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
	 * ��ѯsum�����
	 * @param start String ��ʼ��¼
	 * @param end String ��ֹ��¼
	 * @return ArrayList ����б�
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
     * ��ѯ��ȡ����б�
     * @param start String ��ʼ��¼
     * @param end String ��ֹ��¼
     * @return ArrayList ����б�
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
	 * ��ѯ��ȡ����б�
	 * @param start String ��ʼ��¼
	 * @param end String ��ֹ��¼
	 * @return ArrayList ����б�
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
	 * ��ҳ��ʱ�򽫲�ѯ�������ȡ��ҳ��
	 * ��Ч�ڽ�order by ��desc���ٷ�ҳ
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
	 * ��JDBC��SQL��ʽ��ͳ���ܼ�¼��
	 * @return int ��¼����
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
	 * ��JDBC��SQL��ʽ��ѯ��ȡ����б�
	 * @param start String ��ʼ��¼
	 * @param end String ��ֹ��¼
	 * @return ArrayList ����б�
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
	 * ��ѯ��ȡ����б�
	 * @param start String ��ʼ��¼
	 * @param end String ��ֹ��¼
	 * @return ArrayList ����б�
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
						//System.out.println("��"+i+"�� rsmt.getColumnName(i)================ "+rsmt.getColumnName(i));
						//System.out.println("��"+i+"�� colValue================ "+colValue);

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
			//����ѯ���תΪform�б�
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
     * loadPageListWithHb ִ�в�ѯ����ȡ�б�
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
        //����ѯ���תΪform�б�
        this.queryForm.setPageList(list);
    }


	

    /**
     * countAllWithHb ִ�в�ѯ����ȡ����
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
     * countAllWithHb ִ�в�ѯ����ȡ����
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
     * �õ����ϲ�ѯ�����ļ�¼��������޷��ϵļ�¼������0
     * @return int�ͣ���ѯ�ļ�¼��
     */
    public int getAllCount() {
        return this.queryForm.getAllCount();
    }
    public double getAllSum() {
        return this.queryForm.getAllSum();
    }

    /**
     * �õ����ϲ�ѯ�����ļ�¼������ArrayList�ڣ�ArrayList�ڵ�Ԫ��Ϊform��������޷��ϲ�ѯ�����ļ�¼�����ش�СΪ0��ArrayList
     * @return ArrayList���󣬴�ŷ��ϲ�ѯ�����ļ�¼
     */
    public List getAllList() {
        return this.queryForm.getPageList();
    }

    /**
     * ���ò�ѯ������FormBean��ʹ��ѯ����������Jsp��ֱ�����ɲ���ֵ
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
