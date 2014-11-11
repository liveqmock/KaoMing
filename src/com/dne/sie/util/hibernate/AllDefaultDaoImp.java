package com.dne.sie.util.hibernate;

import com.dne.sie.common.exception.ComException;
import com.dne.sie.common.exception.VersionException;
import com.dne.sie.util.dao.DefaultDao;
import com.dne.sie.util.dao.DefaultDaoBatch;
import com.dne.sie.util.query.QueryParameter;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * hibernate基本操作实现类
 * @author xt
 * @version 1.1.5.6
 * @see AllDefaultDaoImp <br>
 */
public class AllDefaultDaoImp implements DefaultDao, DefaultDaoBatch {
    private static Logger logger = Logger.getLogger(AllDefaultDaoImp.class);
    public AllDefaultDaoImp() {
    }

    /**
     * 删除对象
     * @param pojo Object 需要删除的对象
     * @return boolean 是否成功
     * @throws ComException
     */
    public boolean delete(Object pojo) throws ComException {
        boolean result = false;
        HbConn hbConn=new HbConn();
        try {
            hbConn.getSessionWithTransaction().delete(pojo);
            result = hbConn.commit();

        } catch (Exception e) {
            result = false;
            hbConn.rollback();
            throw new ComException(e);
        } finally {
            hbConn.closeSession();
        }
        return result;
    }

    /**
     * 删除对象，根据id
     * @param hql String 唯一标识
     * @return boolean 是否成功
     * @throws ComException
     */
    public int execute(String hql) throws ComException {
        int tag=-1;
        HbConn hbConn=new HbConn();
        try {
            tag=hbConn.getSessionWithTransaction().createQuery(hql).executeUpdate();
            hbConn.commit();
        } catch (Exception e) {
            tag=-1;
            hbConn.rollback();
            throw new ComException(e);
        } finally {
            hbConn.closeSession();
        }
        return tag;

    }

    public Object execute(String strHql,Object...obj) throws ComException, Exception{
        Object result=null;
        if(strHql!=null){
            HbConn hbConn=new HbConn();
            try{

                Query q = hbConn.getSessionWithTransaction().createQuery(strHql);
                for(int i=0;i<obj.length;i++){
                    q=this.setParam(q,obj[i],i);
                }
                result = q.executeUpdate();
                hbConn.commit();
            } catch (Exception e) {
                throw new Exception(e);
            } finally {
                hbConn.closeSession();
            }
        }
        return result;
    }

    /**
     * 查询对象，根据String类型id
     * @param id String 唯一标识
     * @param pojoClass Class pojo类
     * @return Object pojo对象
     * @throws ComException
     */
    public Object findById(Class pojoClass, String id) throws ComException {
        Object pojo = null;
        if(id!=null){
            HbConn hbConn=new HbConn();
            try {
                pojo = hbConn.getSession().get(pojoClass,id);

            } catch (HibernateException hbe) {
                throw new ComException(hbe);
            } finally {
                hbConn.closeSession();
            }
        }
        return pojo;

    }


    /**
     * 查询对象，根据Long类型id
     * @param id String 唯一标识
     * @param pojoClass Class pojo类
     * @return Object pojo对象
     * @throws ComException
     */
    public Object findById(Class pojoClass, Long id) throws ComException {
        Object pojo = null;
        if(id!=null){
            HbConn hbConn=new HbConn();
            try {
                pojo = hbConn.getSession().get(pojoClass, id);

            } catch (HibernateException hbe) {
                throw new ComException(hbe);
            } finally {
                hbConn.closeSession();
            }
        }
        return pojo;

    }




    /**
     * 新增对象
     * @param pojo Object 新对象
     * @return boolean 是否成功
     * @throws ComException
     */
    public boolean insert(Object pojo) throws ComException {
        boolean result = false;
        HbConn hbConn=new HbConn();
        try {
            hbConn.getSessionWithTransaction().save(pojo);
            result=hbConn.commit();

        } catch (Exception e) {
            result = false;
            hbConn.rollback();
            throw new ComException(e);
        } finally {
            hbConn.closeSession();
        }
        return result;

    }

    /**
     * 新增或修改对象（根据对象本身是否被持久化）
     * @param pojo Object 新对象
     * @return boolean 是否成功
     * @throws ComException
     */
    public boolean saveOrUpdate(Object pojo) throws ComException {
        boolean result = false;
        HbConn hbConn=new HbConn();
        try {
            hbConn.getSessionWithTransaction().saveOrUpdate(pojo);
            result = hbConn.commit();
        } catch (Exception e) {
            result = false;
            hbConn.rollback();
            throw new ComException(e);
        } finally {
            hbConn.closeSession();
        }
        return result;

    }


    /**
     * 新增或修改对象（根据对象本身是否被持久化）
     * @param pojo Object 新对象
     * @return boolean 是否成功
     * @throws ComException
     */
    public boolean merge(Object pojo) throws ComException {
        boolean result = false;
        HbConn hbConn=new HbConn();
        try {
            hbConn.getSessionWithTransaction().merge(pojo);
            result = hbConn.commit();
        } catch (Exception e) {
            result = false;
            hbConn.rollback();
            throw new ComException(e);
        } finally {
            hbConn.closeSession();
        }
        return result;

    }

    /**
     * 查询对象
     * @param pojoClass Class pojo的类
     * @param id String 唯一标识
     * @return Object pojo对象
     * @throws ComException
     * @todo Implement this com.dne.sie.util.dao.DefaultDao method
     */
    public Object loadById(Class pojoClass, String id) throws ComException {
        Object pojo = null;
        HbConn hbConn=new HbConn();
        try {
            pojo = hbConn.getSession().get(pojoClass, id);

        } catch (HibernateException hbe) {
            throw new ComException(hbe);
        } finally {
            hbConn.closeSession();
        }
        return pojo;

    }

    /**
     * 修改对象
     * @param pojo Object 需要更新的对象
     * @return boolean 是否成功
     * @throws ComException
     * @todo Implement this com.dne.sie.util.dao.DefaultDao method
     */
    public boolean update(Object pojo) throws ComException,VersionException {
        boolean result = false;
        HbConn hbConn=new HbConn();
        try {
            hbConn.getSessionWithTransaction().update(pojo);
            result = hbConn.commit();
        } catch (HibernateException he) {
            result = false;
            hbConn.rollback();
            throw new ComException(he);
        } finally {
            hbConn.closeSession();
        }
        return result;

    }


    /**
     * 创建新对象，用于事务处理
     * @param pojo Object 新对象
     * @return boolean 是否成功
     * @throws ComException
     */
    private boolean insertT(Object pojo, HbConn hbConn) throws Exception {
        boolean result = false;
        try {

            hbConn.getSessionWithTransaction().save(pojo);
            result = true;

        } catch (Exception e) {
            result = false;
            throw e;
        }
        return result;
    }

    /**
     * 创建新对象，用于事务处理
     * @param hql Object 新对象
     * @return boolean 是否成功
     * @throws ComException
     */
    private boolean excuteT(Object hql, HbConn hbConn) throws Exception {
        boolean result = false;
        try {
            hbConn.getSessionWithTransaction().createQuery(hql.toString()).executeUpdate();
            result = true;
        } catch (Exception e) {
            throw e;
        }
        return result;
    }

    /**
     * 更新已有对象，用于事务处理
     * @param pojo Object 需要更新的对象
     * @return boolean 是否成功
     * @throws ComException
     */
    private boolean updateT(Object pojo,HbConn hbConn) throws Exception {
        boolean result = false;
        try {
            hbConn.getSessionWithTransaction().update(pojo);
            result = true;
        } catch (Exception e) {
            result = false;
            throw e;
        }
        return result;

    }

    /**
     * 删除对象，用于事务处理
     * @param pojo Object 需要删除的对象
     * @return boolean 是否成功
     * @throws ComException
     */
    private boolean deleteT(Object pojo,HbConn hbConn) throws Exception {
        boolean result = false;
        try {
            hbConn.getSessionWithTransaction().delete(pojo);
            result = true;
        } catch (Exception e) {
            result = false;
            throw e;
        }
        return result;

    }


    /**
     * 新增或更新已有对象，用于事务处理
     * @param pojo Object 需要更新的对象
     * @return boolean 是否成功
     * @throws ComException
     */
    private boolean saveOrUpdateT(Object pojo,HbConn hbConn) throws Exception {
        boolean result = false;
        try {
            hbConn.getSessionWithTransaction().saveOrUpdate(pojo);
            result = true;
        } catch (Exception e) {
            result = false;
            throw e;
        }
        return result;

    }



    /**
     * 批量删除对象
     * @param pojos String 唯一标识
     * @return boolean 是否成功
     * @throws ComException
     */
    public boolean deleteBatch(List pojos) throws ComException {
        boolean result = false;
        HbConn hbConn=new HbConn();
        try{
            if(pojos!=null&&pojos.size()!=0){

                for(int i=0;i<pojos.size();i++){
                    Object obj = pojos.get(i);
                    deleteT(obj,hbConn);
                }
                result = hbConn.commit();
            }else{
                result = true;
            }
        }catch(Exception e){
            result = false;
            hbConn.rollback();
            throw new ComException(e);
        }finally {
            hbConn.closeSession();
        }
        return result;
    }









    /**
     * 批量删除对象
     * @param alData String 唯一标识
     * @return boolean 是否成功
     * @throws ComException
     */
    public boolean updateBatch(List alData)	throws ComException {
        boolean result = false;
        HbConn hbConn=new HbConn();
        try{
            if(alData!=null&&alData.size()!=0){
                for(int i=0;i<alData.size();i++){
                    updateT(alData.get(i),hbConn);
                }
                result = hbConn.commit();
            }else{
                result = true;
            }
        }catch(Exception e){
            hbConn.rollback();
            result = false;
            throw new ComException(e);
        }finally {
            hbConn.closeSession();
        }
        return result;
    }


    /**
     * 批量新增对象
     * @param alData
     * @return boolean 是否成功
     * @throws ComException
     */
    public boolean insertBatch(List alData)	throws ComException {
        boolean result = false;
        HbConn hbConn=new HbConn();
        try{
            if(alData!=null&&alData.size()!=0){
                for(int i=0;i<alData.size();i++){
                    insertT(alData.get(i),hbConn);
                }
                result = hbConn.commit();
            }else{
                result = true;
            }
        }catch(Exception e){
            hbConn.rollback();
            result = false;
            throw new ComException(e);
        }finally {
            hbConn.closeSession();
        }
        return result;
    }



    /**
     * 批量新增或修改对象
     * @param alData
     * @return boolean 是否成功
     * @throws ComException
     */
    public boolean saveOrUpdateBatch(List alData)	throws ComException {
        boolean result = false;
        HbConn hbConn=new HbConn();

        try{
            if(alData!=null&&alData.size()!=0){
                for(int i=0;i<alData.size();i++){
                    saveOrUpdateT(alData.get(i),hbConn);
                }
                result = hbConn.commit();
            }else{
                result = true;
            }
        }catch(Exception e){
            hbConn.rollback();
            result = false;
            throw new ComException(e);
        }finally {
            hbConn.closeSession();
        }
        return result;
    }

    /**
     * 批量操作对象，根据操作标志
     * @param alData
     * @return boolean 是否成功
     * @throws ComException
     */
    public boolean allDMLBatch(List alData) throws ComException {
        boolean result = false;
        HbConn hbConn=new HbConn();

        try{
            if(alData!=null&&alData.size()!=0){
                for(int i=0;i<alData.size();i++){
                    Object[] obj=(Object[])alData.get(i);
                    String flag=obj[1].toString();
                    if(flag.equals("i")) insertT(obj[0],hbConn);
                    else if(flag.equals("u")) updateT(obj[0],hbConn);
                    else if(flag.equals("d")) deleteT(obj[0],hbConn);
                    else if(flag.equals("e")) excuteT(obj[0],hbConn);
                    else if(flag.equals("s")) saveOrUpdateT(obj[0],hbConn);
                }
                result = hbConn.commit();
            }else{
                result = true;
            }
        }catch(Exception e){
            hbConn.rollback();
            result = false;
            throw new ComException(e);
        }finally {
            hbConn.closeSession();
        }
        return result;

    }



    /**
     * 查询对象，根据完整的hql语句，最多查询1000条
     * @param strHql
     * @return List
     * @throws ComException, Exception
     */
    public List list(String strHql) throws ComException, Exception{
        List pojoList =  null;
        if(strHql!=null){
            HbConn hbConn=new HbConn();
            try{
                pojoList = hbConn.getSession().createQuery(strHql).setMaxResults(60000).list();

            } catch (Exception e) {
                throw e;
            } finally {
                hbConn.closeSession();
            }
        }
        return pojoList;
    }


    /**
     * 查询对象，根据完整的hql语句
     * @param strHql
     * @param rownum 查询最大行数
     * @return List
     * @throws ComException, Exception
     */
    public List list(String strHql,int rownum) throws ComException, Exception{
        List pojoList =  null;
        if(strHql!=null){
            HbConn hbConn=new HbConn();
            try{
                if(rownum<=0||rownum>60000) rownum=60000;
                pojoList = hbConn.getSession().createQuery(strHql).setMaxResults(rownum).list();

            } catch (Exception e) {
                throw e;
            } finally {
                hbConn.closeSession();
            }
        }
        return pojoList;
    }
    public List list(String strHql,Object...obj) throws ComException, Exception{
        List pojoList =  null;
        if(strHql!=null){
            HbConn hbConn=new HbConn();
            try{

                Query q = hbConn.getSession().createQuery(strHql).setMaxResults(1000);
                for(int i=0;i<obj.length;i++){
                    q=this.setParam(q,obj[i],i);
                }
                pojoList = q.list();

            } catch (Exception e) {
                throw new Exception(e);
            } finally {
                hbConn.closeSession();
            }
        }
        return pojoList;
    }

    private Query setParam(Query q,Object obj,int i) throws Exception{
        if(obj instanceof String){
            q.setParameter(i,(String)obj,Hibernate.STRING);
        }else if(obj instanceof Long){
            q.setParameter(i,(Long)obj,Hibernate.LONG);
        }else if(obj instanceof Integer){
            q.setParameter(i,(Integer)obj,Hibernate.INTEGER);
        }else{
            throw new Exception("undefine Hibernate type : "+obj.getClass().getName());
        }

        return q;
    }

    /**
     * 查询对象，根据完整的hql语句，返回全部结果
     * @param strHql
     * @return List
     * @throws ComException, Exception
     */
    public List listAll(String strHql) throws ComException, Exception{
        List pojoList =  null;
        if(strHql!=null){
            HbConn hbConn=new HbConn();
            try{
                pojoList = hbConn.getSession().createQuery(strHql).list();

            } catch (Exception e) {
                throw e;
            } finally {
                hbConn.closeSession();
            }
        }
        return pojoList;
    }

    /**
     * 查询对象，根据完整的hql语句，返回唯一结果
     * @param strHql
     * @return Object
     * @throws ComException, Exception
     */
    public Object uniqueResult(String strHql) throws ComException, Exception{
        Object pojo = null;
        HbConn hbConn=new HbConn();
        try{
            pojo = hbConn.getSession().createQuery(strHql).setMaxResults(1).uniqueResult();
        } catch (Exception e) {
            throw e;
        } finally {
            hbConn.closeSession();
        }

        return pojo;
    }
    public Object uniqueResult(String strHql,Object...obj) throws ComException, Exception{
        Object result=null;
        if(strHql!=null){
            HbConn hbConn=new HbConn();
            try{

                Query q = hbConn.getSession().createQuery(strHql).setMaxResults(1);
                for(int i=0;i<obj.length;i++){
                    q=this.setParam(q,obj[i],i);
                }
                result = q.uniqueResult();

            } catch (Exception e) {
                throw new Exception(e);
            } finally {
                hbConn.closeSession();
            }
        }
        return result;
    }

    public Object parameterUnique(String strHql,QueryParameter param) throws ComException {
        Object pojo = null;
        HbConn hbConn=new HbConn();
        try {
            Query q = hbConn.getSession().createQuery(strHql).setMaxResults(1);

            q.setParameter(param.getName(), param.getValue(),param.getHbType());

            pojo=q.uniqueResult();

        } catch (Exception e) {
            throw new ComException(e);
        } finally {
            hbConn.closeSession();
        }

        return pojo;
    }


    /**
     * 查询对象，根据完整的hql语句和查询参数，最多1000行
     * @param strHql
     * @param paramList
     * @return Object
     * @throws ComException, Exception
     */
    public List parameterQuery(String strHql,List paramList) throws ComException {
        List pojoList =  null;
        HbConn hbConn=new HbConn();
        try {
            Query q = hbConn.getSession().createQuery(strHql).setMaxResults(2000);

            for (int i = 0; paramList!=null&&i < paramList.size(); i++) {
                QueryParameter param = (QueryParameter)paramList.get(i);
                q.setParameter(param.getName(), param.getValue(),param.getHbType());
            }

            pojoList  =  q.list();

        } catch (Exception e) {
            throw new ComException(e);
        } finally {
            hbConn.closeSession();
        }

        return pojoList;
    }
    public List parameterQuery(String strHql,QueryParameter param) throws ComException {
        List pojoList =  new ArrayList();
        HbConn hbConn=new HbConn();
        try {
            Query q = hbConn.getSession().createQuery(strHql).setMaxResults(1000);

            q.setParameter(param.getName(), param.getValue(),param.getHbType());


            pojoList  = (List) q.list();

        } catch (Exception e) {
            throw new ComException(e);
        } finally {
            hbConn.closeSession();
        }

        return pojoList;
    }



    public int updatePara(final String hql, final Map params) throws ComException {
        int tag=-1;
        HbConn hbConn=new HbConn();
        try {
            Query q = hbConn.getSessionWithTransaction().createQuery(hql);

            if (params != null) {
                q.setProperties(params);
            }
            q.executeUpdate();
            hbConn.commit();
        } catch (Exception e) {
            tag=-1;
            hbConn.rollback();
            throw new ComException(e);
        } finally {
            hbConn.closeSession();
        }
        return tag;

    }


    public boolean excuteBatch(List alData) throws ComException {
        boolean result = false;
        HbConn hbConn=new HbConn();

        try{
            if(alData!=null&&alData.size()!=0){
                for(int i=0;i<alData.size();i++){
                    excuteT(alData.get(i),hbConn);
                }
                result = hbConn.commit();
            }else{
                result = true;
            }
        }catch(Exception e){
            hbConn.rollback();
            result = false;
            throw new ComException(e);
        }finally {
            hbConn.closeSession();
        }
        return result;

    }


}

