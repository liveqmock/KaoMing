package com.dne.sie.util.dao;

import com.dne.sie.common.exception.ComException;
import com.dne.sie.common.exception.VersionException;
import com.dne.sie.util.query.QueryParameter;

import java.util.List;
import java.util.Map;

/**
 * <p>查询和单独事务的基本DAO</p>
 * <p>作为单独处理的DAO，本身处理后就进行事务commit或rollback</p>
 * @author xt
 * @version 1.1.5.6
 */
public interface DefaultDao {
    /**
     * 根据id从数据库装载相应对象
     * @param pojoClass Class pojo类
     * @param id String 唯一标识
     * @return Object pojo对象
     * @throws ComException
     */
    public Object findById(Class pojoClass,String id) throws ComException;

    public Object findById(Class pojoClass,Long id) throws ComException;



    /**
     * 找到已关联到session的pojo对象
     * @param pojoClass Class pojo的类
     * @param id String 唯一标识
     * @return Object pojo对象
     * @throws ComException
     */
    public Object loadById(Class pojoClass,String id) throws ComException;

    /**
     * 创建新对象
     * @param pojo Object 新对象
     * @return boolean 是否成功
     * @throws ComException
     */
    public boolean insert(Object pojo) throws ComException;

    public boolean saveOrUpdate(Object pojo) throws ComException;

    /**
     * 更新已有对象
     * @param pojo Object 需要更新的对象
     * @return boolean 是否成功
     * @throws ComException
     */
    public boolean update(Object pojo) throws ComException,VersionException;

    public boolean merge(Object pojo) throws ComException,VersionException;

    /**
     * 删除对象
     * @param pojo Object 需要删除的对象
     * @return boolean 是否成功
     * @throws ComException
     */
    public boolean delete(Object pojo) throws ComException;

    /**
     * 增删改对象，根据hql语句
     * @param hql
     * @return boolean 成功条数
     * @throws ComException
     */
    public int execute(String hql) throws ComException;

    public Object execute(String strHql,Object...obj) throws ComException, Exception;

    public int updatePara(final String hql, final Map params) throws ComException;

    /**
     * 得到对象列表
     * @param pojoClass 对象class
     * @return 列表
     * @throws ComException
     */
    public List list(String strHql) throws ComException,Exception;
    public List list(String strHql,Object...obj) throws ComException,Exception;

    public List list(String strHql,int rownum) throws ComException,Exception;

    public List listAll(String strHql) throws ComException,Exception;

    public Object uniqueResult(String strHql) throws ComException,Exception;
    public Object uniqueResult(String strHql,Object...obj) throws ComException,Exception;
    public Object parameterUnique(String strHql,QueryParameter param) throws ComException,Exception;

//	public boolean lock(Object obj) throws ComException,VersionException;


    public List parameterQuery(String strHql,List paramList) throws ComException;
    public List parameterQuery(String strHql,QueryParameter param) throws ComException;

    int attribute1 = 0;
}
