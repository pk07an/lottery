package com.npc.lottery.statreport.logic.spring;

import java.util.ArrayList;
import java.util.List;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.replenish.entity.Replenish;
import com.npc.lottery.statreport.dao.interf.IReplenishDao;
import com.npc.lottery.statreport.logic.interf.IReplenishLogic;
import com.npc.lottery.util.Tool;

/**
 * 补货逻辑处理类
 * 
 */
public class ReplenishLogic implements IReplenishLogic {

    private IReplenishDao replenishDao2 = null;//数据库处理类，由spring框架注入实例

    public void setReplenishDao2(IReplenishDao replenishDao2) {
        this.replenishDao2 = replenishDao2;
    }

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
            int pageCurrentNo, int pageSize) {
        ArrayList<Replenish> resultList = null;

        // 根据页码和页面大小，获得需要查询的开始数据和数量
        int firstResult = (pageCurrentNo - 1) * pageSize;
        int maxResults = pageSize;

        // 查询数据
        try {
            resultList = (ArrayList<Replenish>) replenishDao2
                    .findReplenishList(conditionData, firstResult, maxResults);
        } catch (Exception ex) {
            Tool.printExceptionStack(ex);
        }

        return resultList;
    }

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
    public ArrayList<Replenish> findReplenishList(Long replenishUserId) {
        ArrayList<Replenish> resultList = null;

        ConditionData conditionData = new ConditionData();

        conditionData.addEqual("replenishUserId", replenishUserId);//增加补货人ID的查询条件
        conditionData.addIn("winState", Replenish.WIN_STATE_WIN);
        conditionData.addIn("winState", Replenish.WIN_STATE_NOT_WIN);
        conditionData.addIn("winState", Replenish.WIN_STATE_PRIZE);
        
        //按投注时间排序
        conditionData.addOrder("bettingDate", ConditionData.ORDER_TYPE_DESC);

        resultList = this.findReplenishList(conditionData, 1, 999);

        return resultList;
    }

    /**
     * 根据ID查询数据
     * 
     * @return
     */
    public Replenish findReplenishByID(long ID) {
        Replenish entity = null;

        ConditionData conditionData = new ConditionData();

        conditionData.addEqual("ID", new Long(ID));//增加ID的查询条件

        List resultList = this.findReplenishList(conditionData, 1, 999);

        if (null != resultList && 0 < resultList.size()) {
            entity = (Replenish) resultList.get(0);
        }

        return entity;
    }
}
