package com.npc.lottery.statreport.dao.interf;

import java.util.ArrayList;
import java.util.List;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.statreport.entity.BjscHis;

public interface IBJSCHisDao {

    /**
     * 根据查询条件查询对应北京赛车历史信息
     * 
     * @param conditionStr
     * @return
     */
    public ArrayList<BjscHis> findBjscHis(String conditionStr);
    
	public void updateBatch(String hql, Object[] values);

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
     * @return  GdklsfHis 类型的 List
     */
    public List findBjscHisList(final ConditionData conditionData,
            final int firstResult, final int maxResults);
    /**
     * 删除对象
     * @param entity
     */
    public void delete(BjscHis entity);
    
    /**
     * add by peter 获取北京历史表里最大和最小的投注日期
     * @return
     */
	public Object[] findBjHisMaxAndMinBetDate();
}
