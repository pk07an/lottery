package com.npc.lottery.statreport.dao.hibernate;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.statreport.dao.interf.IHklhcHisDao;
import com.npc.lottery.statreport.entity.HklhcHis;
import com.npc.lottery.util.HibernateTool;

/**
 * 投注历史（香港六合彩）数据库处理类
 * 
 */
public class HklhcHisDao extends HibernateDaoSupport implements IHklhcHisDao {

    /**
     * 根据查询条件查询对应广东快乐十分历史信息
     * 
     * @param conditionStr
     * @return
     */
    public ArrayList<HklhcHis> findHklhcHis(String conditionStr) {

        StringBuffer sqlInfo = new StringBuffer();
        //SELECT * FROM (SELECT t.*, RANK() OVER(PARTITION BY order_no ORDER BY rownum DESC) rankNum, 
        //COUNT(*) OVER(PARTITION BY order_no) count FROM tb_gdklsf_his t WHERE t.betting_user_id=421) WHERE rankNum = 1
        sqlInfo.append("SELECT * FROM (SELECT t.*, RANK() OVER (PARTITION BY order_no ORDER BY rownum DESC) rankNum, ");
        sqlInfo.append("COUNT(*) OVER(PARTITION BY order_no) recordNum FROM TB_HKLHC_HIS t WHERE 1=1 ");
        if (null != conditionStr && conditionStr.trim().length() > 0) {
            sqlInfo.append(" AND ");
            sqlInfo.append(conditionStr);
        }
        sqlInfo.append(") WHERE rankNum = 1 ORDER BY BETTING_DATE DESC");

        //List result = getHibernateTemplate().find(sqlInfo.toString());
        //List result = getHibernateTemplate().

        Session session = getHibernateTemplate().getSessionFactory()
                .getCurrentSession();
        //List resultTemp = session.createSQLQuery(sqlInfo.toString()).addEntity(HklhcHis.class).list();
        List resultTemp = session.createSQLQuery(sqlInfo.toString())
                .addEntity("t", HklhcHis.class).addScalar("recordNum").list();

        ArrayList<HklhcHis> result = null;

        HklhcHis entity;
        BigDecimal recordNum;
        //将统计值填充如对象
        if (null == resultTemp || resultTemp.size() < 1) {
            result = null;
        } else {
            result = new ArrayList<HklhcHis>();
            for (int i = 0; i < resultTemp.size(); i++) {
                entity = (HklhcHis)((Object[])resultTemp.get(i))[0];
                recordNum = (BigDecimal)((Object[])resultTemp.get(i))[1];
                entity.setRecordNum(recordNum.longValue());//保存数量
                
                //保存数据
                result.add(entity);
            }
        }

        return result;
    }

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
     * @return  HklhcHis 类型的 List
     */
    public List findHklhcHisList(final ConditionData conditionData,
            final int firstResult, final int maxResults) {
        return getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {

                Criteria criteria = session.createCriteria(HklhcHis.class);

                //处理查询条件
                criteria = HibernateTool.parseMultiCondition(criteria,
                        conditionData);

                criteria.setFirstResult(firstResult);
                criteria.setMaxResults(maxResults);

                return criteria.list();
            }
        });
    }

    /**
     * 统计满足指定查询条件的信息数目
     * 
     * @param conditionData
     *            查询条件信息
     * @return
     */
    public long findAmountHklhcHisList(final ConditionData conditionData) {
        long amount = 0;
        amount = ((Long) getHibernateTemplate().execute(
                new HibernateCallback() {
                    public Object doInHibernate(Session session)
                            throws HibernateException, SQLException {

                        Criteria criteria = session
                                .createCriteria(HklhcHis.class);

                        //处理查询条件
                        criteria = HibernateTool.parseMultiCondition(criteria,
                                conditionData);

                        long count = ((Number) criteria.setProjection(
                                Projections.rowCount()).uniqueResult())
                                .intValue();

                        return count;
                    }
                })).longValue();

        return amount;
    }

    /**
     * 保存信息
     * 
     * 
     * @param entity    待保存的信息
     * @return  此信息记录所对应的ID，Long类型
     */
    public Long saveHklhcHis(HklhcHis entity) {

        Long result = null;

        result = (Long) getHibernateTemplate().save(entity);

        return result;
    }

    /**
     * 更新信息
     * 
     * @param entity 待更新的信息
     */
    public void update(HklhcHis entity) {
        getHibernateTemplate().update(entity);
    }

    /**
     * 删除对象
     * @param entity
     */
    public void delete(HklhcHis entity) {
        getHibernateTemplate().delete(entity);
    }
    /**
     * 批量更新
     * @param entity
     */
    @Override
    public void updateBatch(String hql,Object[] values) {
        getHibernateTemplate().bulkUpdate(hql, values);
    }
}
