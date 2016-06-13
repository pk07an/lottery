package com.npc.lottery.sysmge.logic.interf;

import java.util.List;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.sysmge.entity.Resource;

/**
 * 资源的逻辑处理类
 *
 * @author none
 *
 */
public interface IResourceLogic {

    /**
     * 查询满足指定查询条件的资源信息数据记录
     * 
     * @param conditionData
     *            查询条件信息
     * @param pageCurrentNo 第一页为 1
     *            需要查询的页码
     * @param pageSize
     *            页面大小
     * @return  Resource 类型的 List
     */
    public List findResourceList(ConditionData conditionData,
            int pageCurrentNo, int pageSize);

    /**
     * 查询所有的功能信息
     * 
     * @return Resource 类型的 List
     */
    public List findAll();

    /**
     * 根据ID查询功能数据
     * 
     * @param ID
     * @return
     */
    public Resource findByID(Long ID);

    /**
     * 保存或更新对象
     * @param entity
     */
    public void saveOrUpdate(Resource entity);

    /**
     * 删除对象
     * @param entity
     */
    public void delete(Resource entity);

    /**
     * 查询指定功能所对应的资源信息
     * 
     * @param funcID
     * @return
     */
    public List findAllByFunc(Long funcID);
}
