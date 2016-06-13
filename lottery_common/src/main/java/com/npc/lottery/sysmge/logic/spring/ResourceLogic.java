package com.npc.lottery.sysmge.logic.spring;

import java.util.List;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.sysmge.dao.interf.IResourceDao;
import com.npc.lottery.sysmge.entity.Resource;
import com.npc.lottery.sysmge.logic.interf.IResourceLogic;
import com.npc.lottery.util.Tool;

/**
 * 资源逻辑处理类
 *
 * @author none
 *
 */
public class ResourceLogic implements IResourceLogic {

    private IResourceDao resourceDao = null;

    public void setResourceDao(IResourceDao resourceDao) {
        this.resourceDao = resourceDao;
    }

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
            int pageCurrentNo, int pageSize) {
        List resultList = null;

        // 根据页码和页面大小，获得需要查询的开始数据和数量
        int firstResult = (pageCurrentNo - 1) * pageSize;
        int maxResults = pageSize;

        // 查询数据
        try {
            resultList = resourceDao.findResourceList(conditionData,
                    firstResult, maxResults);
        } catch (Exception ex) {
            Tool.printExceptionStack(ex);
        }

        return resultList;
    }

    /**
     * 根据ID查询功能数据
     * 
     * @param ID
     * @return
     */
    public Resource findByID(Long ID) {

        Resource entity = null;

        ConditionData conditionData = new ConditionData();

        conditionData.addEqual("ID", new Long(ID));//增加ID的查询条件

        List resultList = this.findResourceList(conditionData, 1, 999);

        if (null != resultList && 0 < resultList.size()) {
            entity = (Resource) resultList.get(0);
        }

        return entity;
    }

    /**
     * 查询所有的功能信息
     * 
     * @return Resource 类型的 List
     */
    public List findAll() {
        return this.findResourceList(null, 1, 999999);
    }

    /**
     * 保存或更新对象
     * @param entity
     */
    public void saveOrUpdate(Resource entity) {
        resourceDao.saveOrUpdate(entity);
    }

    /**
     * 删除对象
     * @param entity
     */
    public void delete(Resource entity) {
        resourceDao.delete(entity);
    }

    /**
     * 查询指定功能所对应的资源信息
     * 
     * @param funcID
     * @return
     */
    public List findAllByFunc(Long funcID) {
        ConditionData conditionData = new ConditionData();

        conditionData.addEqual("function.id", new Long(funcID));//增加ID的查询条件

        List resultList = this.findResourceList(conditionData, 1, 999);

        return resultList;
    }

}
