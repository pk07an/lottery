package com.npc.lottery.sysmge.dao.hibernate;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.sysmge.dao.interf.IRolesDao;
import com.npc.lottery.sysmge.entity.Roles;
import com.npc.lottery.util.HibernateTool;

public class RolesDao extends HibernateDaoSupport implements IRolesDao {

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
    public List findRolesList(final ConditionData conditionData,
            final int firstResult, final int maxResults) {

        return getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {

                Criteria criteria = session.createCriteria(Roles.class);

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
     * 获取总的记录数
     * @param conditionData 查询条件信息
     * @return
     */
    public long findAmountRecord(final ConditionData conditionData) {
        long amount = 0;

        amount = ((Long) getHibernateTemplate().execute(
                new HibernateCallback() {
                    public Object doInHibernate(Session session)
                            throws HibernateException, SQLException {

                        Criteria criteria = session.createCriteria(Roles.class);

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
     * 保存或更新对象
     * @param entity
     */
    public void saveOrUpdate(Roles entity) {
        getHibernateTemplate().saveOrUpdate(entity);
    }

    /**
     * 保存信息
     * 
     * @param entity    待保存的信息
     * @return  此信息记录所对应的ID，Long类型
     */
    public Long saveRoles(Roles entity) {
        
        //设置了openSessionInView，创建用户私有角色时需要设置session模式，否则会出错
        getSession(false).setFlushMode(FlushMode.AUTO);
        
        Long result = null;
        result = (Long) getHibernateTemplate().save(entity);
        
        getHibernateTemplate().flush();

        return result;
    }

    /**
     * 删除对象
     * @param entity
     */
    public void delete(Roles entity) {
        getHibernateTemplate().delete(entity);
    }

}
