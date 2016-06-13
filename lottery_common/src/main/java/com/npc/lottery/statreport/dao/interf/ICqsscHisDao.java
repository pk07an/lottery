package com.npc.lottery.statreport.dao.interf;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.common.dao.ILotteryBaseDao;
import com.npc.lottery.statreport.entity.CqsscHis;
import com.npc.lottery.util.HibernateTool;

/**
 * 投注历史（重庆时时彩）数据库处理类接口
 * 
 */
public interface ICqsscHisDao{

    /**
     * 根据查询条件查询对应广东快乐十分历史信息
     * 
     * @param conditionStr
     * @return
     */
    public ArrayList<CqsscHis> findCqsscHis(String conditionStr);

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
     * @return  CqsscHis 类型的 List
     */
    public List findCqsscHisList(final ConditionData conditionData,
            final int firstResult, final int maxResults);

    /**
     * 统计满足指定查询条件的信息数目
     * 
     * @param conditionData
     *            查询条件信息
     * @return
     */
    public long findAmountCqsscHisList(final ConditionData conditionData);

    /**
     * 保存信息
     * 
     * 
     * @param entity    待保存的信息
     * @return  此信息记录所对应的ID，Long类型
     */
    public Long saveCqsscHis(CqsscHis entity);

    /**
     * 更新信息
     * 
     * @param entity 待更新的信息
     */
    public void update(CqsscHis entity);

    /**
     * 删除对象
     * @param entity
     */
    public void delete(CqsscHis entity);

    /**
     * 批量更新
     * @param entity
     */
    public void updateBatch(String hql, Object[] values);
}
