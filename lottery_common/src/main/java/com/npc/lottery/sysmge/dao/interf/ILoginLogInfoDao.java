package com.npc.lottery.sysmge.dao.interf;

import java.util.List;
import com.npc.lottery.common.ConditionData;
import com.npc.lottery.sysmge.entity.LoginLogInfo;

/**
 *  登陆日志数据库处理类接口
 * 
 */
public interface ILoginLogInfoDao {

    /**
    * 查询满足指定查询条件的功能信息数据记录
    * 
    * @param conditionData
    *            查询条件信息
    * @param firstResult
    *            需要查询的第一个记录数
    * @param maxResults
    *            需要查询的记录数目
    * @return  Function 类型的 List
    */
    public List findLoginLogInfoList(final ConditionData conditionData,
            final int firstResult, final int maxResults);

    /**
     * 获取总的记录数
     * @param conditionData 查询条件信息
     * @return
     */
    public long findAmountLoginLogInfo(final ConditionData conditionData);

    /**
     * 保存或更新对象
     * @param entity
     */
    public void saveOrUpdate(LoginLogInfo entity);

    /**
     * 保存信息
     * 
     * @param entity    待保存的信息
     * @return  此信息记录所对应的ID，Long类型
     */
    public Long saveLoginLogInfo(LoginLogInfo entity);

    /**
     * 删除对象
     * @param entity
     */
    public void delete(LoginLogInfo entity);

}
