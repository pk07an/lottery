package com.npc.lottery.statreport.dao.interf;

import java.util.ArrayList;
import java.util.List;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.common.dao.ILotteryBaseDao;
import com.npc.lottery.statreport.entity.GdklsfHis;

/**
 * 投注历史（广东快乐十分）数据库处理类接口
 * 
 */
public interface IGdklsfHisDao{
    
    /**
     * 根据查询条件查询对应广东快乐十分历史信息
     * 
     * @param conditionStr
     * @return
     */
    public ArrayList<GdklsfHis> findGdklsfHis(String conditionStr);

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
    public List findGdklsfHisList(final ConditionData conditionData,
            final int firstResult, final int maxResults);

    /**
     * 统计满足指定查询条件的信息数目
     * 
     * @param conditionData
     *            查询条件信息
     * @return
     */
    public long findAmountGdklsfHisList(final ConditionData conditionData);

    /**
     * 保存信息
     * 
     * 
     * @param entity    待保存的信息
     * @return  此信息记录所对应的ID，Long类型
     */
    public Long saveGdklsfHis(GdklsfHis entity);

    /**
     * 更新信息
     * 
     * @param entity 待更新的信息
     */
    public void update(GdklsfHis entity);
    
    /**
     * 删除对象
     * @param entity
     */
    public void delete(GdklsfHis entity);

    public void updateBatch(String hql, Object[] values);
}
