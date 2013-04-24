package com.dne.sie.util.dao;

import com.dne.sie.common.exception.ComException;
import java.util.List;
import java.util.Map;


/**
 * <p>��������Ļ���DAO</p>
 * <p>ȫ�������ͽ�������commit��rollback</p>
 * @author xt
 * @version 1.1.5.6
 */
public interface DefaultDaoBatch {
	
    
	/**
	 * ����idֵ����ɾ������List �ӿڣ�
	 * @param pojoClass ���������
	 * @param ids һ��id 
	 * @return �Ƿ�ɾ���ɹ�
	 * @throws CESException
	 */
	public boolean deleteBatch(List ids) throws ComException;
    

    
	/**
	 * �����޸Ķ���List�ӿڣ�
	 * @param pojoClass ���������
	 * @param ids һ��form
	 * @return �Ƿ��޸ĳɹ�
	 * @throws ComException
	 */
	public boolean updateBatch(List al) throws ComException;
	
	/**
	 * ������������List�ӿڣ�
	 * @param pojoClass ���������
	 * @param ids һ��form
	 * @return �Ƿ��޸ĳɹ�
	 * @throws ComException
	 */
	public boolean insertBatch(List al) throws ComException;
	
	/**
	 * �����������޸Ķ���List�ӿڣ�
	 * @param pojoClass ���������
	 * @param ids һ��form
	 * @return �Ƿ��޸ĳɹ�
	 * @throws ComException
	 */
	public boolean saveOrUpdateBatch(List al) throws ComException;
	
	
	/**
	 * �����������޸Ļ�ɾ������List�ӿڣ�
	 * @param al - list���󣬷�װObject[]��
	 * 	Object[0]Ϊ������form��Object[1]Ϊ������־��"i"-insert,"u"-update,"d"-delete
	 * @return �Ƿ��޸ĳɹ�
	 * @throws ComException
	 */
	public boolean allDMLBatch(List<Object[]> al) throws ComException;
	
	public boolean excuteBatch(List al) throws ComException;
	

}
