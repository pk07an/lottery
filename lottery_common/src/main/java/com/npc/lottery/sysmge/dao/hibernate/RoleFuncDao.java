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
import com.npc.lottery.sysmge.dao.interf.IRoleFuncDao;
import com.npc.lottery.sysmge.entity.RoleFunc;
import com.npc.lottery.util.HibernateTool;

/**
 * 角色所拥有的功能数据库处理类
 *
 * @author none
 *
 */
public class RoleFuncDao extends HibernateDaoSupport implements IRoleFuncDao {

	/**
	 * 查询满足指定查询条件的数据记录
	 * 
	 * @param conditionData
	 *            查询条件信息
	 * @param firstResult
	 *            需要查询的第一个记录数
	 * @param maxResults
	 *            需要查询的记录数目
	 * @return  RoleFunc 类型的 List
	 */
	public List findRoleFuncList(final ConditionData conditionData,
			final int firstResult, final int maxResults) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {

				Criteria criteria = session.createCriteria(RoleFunc.class);

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
	public long findAmountDemoList(final ConditionData conditionData) {
		long amount = 0;

		amount = ((Long) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {

						Criteria criteria = session
								.createCriteria(RoleFunc.class);

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
	 * @param entity    待保存的信息
	 * @return  此信息记录所对应的ID，Long类型
	 */
	public Long saveRoleFunc(RoleFunc entity) {

		Long result = null;

		result = (Long) getHibernateTemplate().save(entity);

		return result;
	}

	/**
	 * 更新信息
	 * 
	 * @param entity 待更新的信息
	 */
	public void update(RoleFunc entity) {
		getHibernateTemplate().update(entity);
	}

	/**
	 * 删除
	 * 
	 * @param entity
	 */
	public void delete(RoleFunc entity) {
		getHibernateTemplate().delete(entity);
	}
}
