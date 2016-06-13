package com.npc.lottery.sysmge.dao.interf;

import java.util.List;

import com.npc.lottery.common.ConditionData;

/**
 * 系统监控数据接口
 * 
 * @author none
 *
 */
public interface IMonitorDao {

    /**
     * 查询满足指定查询条件的数据记录
     * 
     * @param conditionData
     *            查询条件信息
     * @param firstResult
     *            需要查询的第一个记录数
     * @param maxResults
     *            需要查询的记录数目
     * @return  Demo 类型的 List
     */
    public List findSessionStatInfoList(final ConditionData conditionData,
            final int firstResult, final int maxResults);

    /**
     * 统计满足指定查询条件的信息数目
     * 
     * @param conditionData
     *            查询条件信息
     * @return
     */
    public long findAmountSessionStatInfoList(final ConditionData conditionData);

}
