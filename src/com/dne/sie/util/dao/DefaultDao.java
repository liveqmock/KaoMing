package com.dne.sie.util.dao;

import com.dne.sie.common.exception.ComException;
import com.dne.sie.common.exception.VersionException;
import com.dne.sie.util.query.QueryParameter;

import java.util.List;
import java.util.Map;

/**
 * <p>��ѯ�͵�������Ļ���DAO</p>
 * <p>��Ϊ���������DAO���������ͽ�������commit��rollback</p>
 * @author xt
 * @version 1.1.5.6
 */
public interface DefaultDao {
    /**
     * ����id�����ݿ�װ����Ӧ����
     * @param pojoClass Class pojo��
     * @param id String Ψһ��ʶ
     * @return Object pojo����
     * @throws CESException
     */
    public Object findById(Class pojoClass,String id) throws ComException;
    
	public Object findById(Class pojoClass,Long id) throws ComException;

	

    /**
     * �ҵ��ѹ�����session��pojo����
     * @param pojoClass Class pojo����
     * @param id String Ψһ��ʶ
     * @return Object pojo����
     * @throws ComException
     */
    public Object loadById(Class pojoClass,String id) throws ComException;

    /**
     * �����¶���
     * @param pojo Object �¶���
     * @return boolean �Ƿ�ɹ�
     * @throws ComException
     */
    public boolean insert(Object pojo) throws ComException;
    
	public boolean saveOrUpdate(Object pojo) throws ComException;

    /**
     * �������ж���
     * @param pojo Object ��Ҫ���µĶ���
     * @return boolean �Ƿ�ɹ�
     * @throws ComException
     */
    public boolean update(Object pojo) throws ComException,VersionException;
    
    public boolean merge(Object pojo) throws ComException,VersionException;

    /**
     * ɾ������
     * @param pojo Object ��Ҫɾ���Ķ���
     * @return boolean �Ƿ�ɹ�
     * @throws ComException
     */
    public boolean delete(Object pojo) throws ComException;

    /**
     * ��ɾ�Ķ��󣬸���hql���
     * @param hql
     * @return boolean �ɹ�����
     * @throws ComException
     */
    public int execute(String hql) throws ComException;
    
    public int updatePara(final String hql, final Map params) throws ComException;
    
    /**
     * �õ������б�
     * @param pojoClass ����class
     * @return �б�
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
