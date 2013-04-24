package com.dne.sie.util.bo;

import org.apache.commons.beanutils.BeanUtils;

import com.dne.sie.util.dao.DefaultDao;
import com.dne.sie.util.dao.DefaultDaoBatch;
import com.dne.sie.util.hibernate.AllDefaultDaoImp;
//import com.dne.sie.util.pojo.GeneralPojoAnalyzer;
import com.dne.sie.common.exception.VersionException;
import com.dne.sie.common.exception.ComException;
import java.util.List;

/**
 * ����bo��Ļ�����
 * @author xt
 * @version 1.1.5.6
 */
public class CommBo {

    private DefaultDao dao;
    private DefaultDaoBatch batchDao;
    
    protected DefaultDao getDao(){
    	return this.dao;
    }
    
    
    protected DefaultDaoBatch getBatchDao(){
    	return this.batchDao;
    }


	/**
	 * ���캯��
	 */
    public CommBo() {
        AllDefaultDaoImp daoImp = new AllDefaultDaoImp();
        dao = daoImp;
        batchDao = daoImp;
    }

    /**
     * ���棬�������½��͸��£�
     * �ȸ���id���������ݿ⣬�ж��Ƿ�Ϊ�¼�¼
     * @param pojo Object pojo����
     * @param id String �����id
     * @return boolean �Ƿ񱣴�ɹ�
     */
    protected boolean save(Object pojo, String id) {
        boolean result = false;
        try {
            if (id != null && !id.equals("")) {
                Object origPojo = dao.findById(pojo.getClass(), id);
                if (origPojo == null) {
                    result = dao.insert(pojo);
                } else {
                    BeanUtils.copyProperties(origPojo, pojo);
                    result = dao.update(origPojo);
                }
            }
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        return result;

    }



    /**
     * ���ض���
     * @param pojo Object ����
     * @return Object �����Ķ���
     */
//    public boolean loadVersion(Object pojo) throws ComException,VersionException{
//    	
//		boolean booRet = dao.lock(pojo);
//		if(!booRet) throw new VersionException("��Ϣ�ѱ����������޸ģ�");
//        
//        return booRet;
//    }

	/**
	 * �������ض���
	 * @param strHql-hql��䣻len-ԭʼ��������
	 * @return ����汾ȫ�����������ز�ѯ���������throw VersionException
	 */
	public List listVersion(String strHql,int len) throws Exception{
		List vList=null;
		
		List tempList=null;
		int len2=0,flag=0;
		try{
			tempList=dao.list(strHql);
			len2=tempList==null?0:tempList.size();
			flag=1;
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
		if(len2==len){
			vList=tempList;
		}else if(flag==1){
			throw new VersionException(len-len2+"�����ݰ汾�쳣");
		}
		
		return vList;
	}

    /**
     * ���ض���
     * ���ݶ���id�Ͷ��������ҵ�ƥ��Ķ���
     * @param pojoClass Class ��������
     * @param id String �����ʶ
     * @return Object ����
     */
    public Object load(Class pojoClass, String id) {
        Object pojo = null;
        try {
            if (id != null && !id.equals("")) {
                pojo = dao.findById(pojoClass, id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pojo;
    }


}
