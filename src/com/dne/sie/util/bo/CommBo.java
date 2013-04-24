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
 * 所有bo类的基础类
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
	 * 构造函数
	 */
    public CommBo() {
        AllDefaultDaoImp daoImp = new AllDefaultDaoImp();
        dao = daoImp;
        batchDao = daoImp;
    }

    /**
     * 保存，包含了新建和更新，
     * 先根据id来检索数据库，判断是否为新记录
     * @param pojo Object pojo对象
     * @param id String 对象的id
     * @return boolean 是否保存成功
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
     * 加载对象
     * @param pojo Object 对象
     * @return Object 处理后的对象
     */
//    public boolean loadVersion(Object pojo) throws ComException,VersionException{
//    	
//		boolean booRet = dao.lock(pojo);
//		if(!booRet) throw new VersionException("信息已被其它事务修改！");
//        
//        return booRet;
//    }

	/**
	 * 批量加载对象
	 * @param strHql-hql语句；len-原始数据行数
	 * @return 如果版本全部正常，返回查询结果，否则throw VersionException
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
			throw new VersionException(len-len2+"条数据版本异常");
		}
		
		return vList;
	}

    /**
     * 加载对象
     * 根据对象id和对象类型找到匹配的对象
     * @param pojoClass Class 对象类型
     * @param id String 对象标识
     * @return Object 对象
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
