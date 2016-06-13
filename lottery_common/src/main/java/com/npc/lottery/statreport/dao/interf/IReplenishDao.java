package com.npc.lottery.statreport.dao.interf;

import java.util.List;

import com.npc.lottery.common.ConditionData;

/**
 * 补货数据库处理类接口
 * 
 */
public interface IReplenishDao {

    /**
     * 查询满足指定查询条件的数据记录
     * 
     * 
     * @param conditionData
     *            查询条件信息
     * @param firstResult
     *            需要查询的第一个记录数
     * @param maxResults
     *            需要查询的记录数目
     * @return  Replenish 类型的 List
     */
    public List findReplenishList(final ConditionData conditionData,
            final int firstResult, final int maxResults);
}
