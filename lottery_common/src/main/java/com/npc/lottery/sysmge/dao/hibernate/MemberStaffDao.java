package com.npc.lottery.sysmge.dao.hibernate;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.sysmge.dao.interf.IMemberStaffDao;
import com.npc.lottery.sysmge.entity.MemberStaff;
import com.npc.lottery.util.HibernateTool;

/**
 * 基础会员用户所对应的数据库实现类
 * 
 * @author none
 *
 */
public class MemberStaffDao extends HibernateDaoSupport implements IMemberStaffDao {


    /**
     * 统计满足指定查询条件的人员信息数目
     * 
     * @param conditionData
     *            查询条件信息
     * @return
     */
    public long findAmountMemberStaffList(final ConditionData conditionData) {
        long amount = 0;

        amount = ((Long) getHibernateTemplate().execute(
                new HibernateCallback() {
                    public Object doInHibernate(Session session)
                            throws HibernateException, SQLException {

                        Criteria criteria = session
                                .createCriteria(MemberStaff.class);

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
     * 查询满足指定查询条件的人员信息数据记录
     * 
     * @param conditionData
     *            查询条件信息
     * @param firstResult
     *            需要查询的第一个记录数
     * @param maxResults
     *            需要查询的记录数目
     * @return  MemberStaff 类型的 List
     */
    public List findMemberStaffList(final ConditionData conditionData,
            final int firstResult, final int maxResults) {
        return getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {

                Criteria criteria = session.createCriteria(MemberStaff.class);

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
     * 保存信息
     * 
     * @param entity    待保存的信息
     * @return  此信息记录所对应的ID，Long类型
     */
    public Long saveMemberStaff(MemberStaff entity) {

        Long result = null;

        result = (Long) getHibernateTemplate().save(entity);

        return result;
    }

    /**
     * 更新信息
     * 
     * @param entity 待更新的信息
     */
    public void updateMemberStaff(MemberStaff entity) {
        getHibernateTemplate().update(entity);
    }

    /**
     * 删除对象
     * @param entity
     */
    public void delMemberStaff(MemberStaff entity) {
        getHibernateTemplate().delete(entity);
    }
}
