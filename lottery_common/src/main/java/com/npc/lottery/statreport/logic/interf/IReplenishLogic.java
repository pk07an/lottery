package com.npc.lottery.statreport.logic.interf;

import java.util.ArrayList;
import com.npc.lottery.common.ConditionData;
import com.npc.lottery.replenish.entity.Replenish;

/**
 * 补货逻辑处理类接口
 * 
 */
public interface IReplenishLogic {

    /**
     * 查询满足指定查询条件的数据记录
     * 
     * @param conditionData
     *            查询条件信息
     * @param pageCurrentNo 第一页为 1
     *            需要查询的页码
     * @param pageSize
     *            页面大小
     * @return  Replenish 类型的 ArrayList
     */
    public ArrayList<Replenish> findReplenishList(ConditionData conditionData,
            int pageCurrentNo, int pageSize);

    /**
     * 查询满足指定查询条件的数据记录
     * 
     * @param replenishUserId
     *            补货人ID
     * @param pageCurrentNo 第一页为 1
     *            需要查询的页码
     * @param pageSize
     *            页面大小
     * @return  Replenish 类型的 ArrayList
     */
    public ArrayList<Replenish> findReplenishList(Long replenishUserId);

    /**
     * 根据ID查询数据
     * 
     * @return
     */
    public Replenish findReplenishByID(long ID);
}
