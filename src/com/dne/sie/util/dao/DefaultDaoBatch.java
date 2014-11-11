package com.dne.sie.util.dao;

import com.dne.sie.common.exception.ComException;
import java.util.List;
import java.util.Map;


/**
 * <p>批量事务的基本DAO</p>
 * <p>全部处理后就进行事务commit或rollback</p>
 * @author xt
 * @version 1.1.5.6
 */
public interface DefaultDaoBatch {


    /**
     * 根据id值批量删除对象（List 接口）
     * @param ids 一批id
     * @return 是否删除成功
     * @throws ComException
     */
    public boolean deleteBatch(List ids) throws ComException;



    /**
     * 批量修改对象（List接口）
     * @param al 一批form
     * @return 是否修改成功
     * @throws ComException
     */
    public boolean updateBatch(List al) throws ComException;

    /**
     * 批量新增对象（List接口）
     * @param al 一批form
     * @return 是否修改成功
     * @throws ComException
     */
    public boolean insertBatch(List al) throws ComException;

    /**
     * 批量新增或修改对象（List接口）
     * @param al 一批form
     * @return 是否修改成功
     * @throws ComException
     */
    public boolean saveOrUpdateBatch(List al) throws ComException;


    /**
     * 批量新增或修改或删除对象（List接口）
     * @param al - list对象，封装Object[]，
     * 	Object[0]为待操作form，Object[1]为操作标志，"i"-insert,"u"-update,"d"-delete
     * @return 是否修改成功
     * @throws ComException
     */
    public boolean allDMLBatch(List<Object[]> al) throws ComException;

    public boolean excuteBatch(List al) throws ComException;


}
