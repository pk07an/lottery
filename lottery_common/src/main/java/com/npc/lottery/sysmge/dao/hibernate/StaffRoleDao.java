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
import com.npc.lottery.sysmge.dao.interf.IStaffRoleDao;
import com.npc.lottery.sysmge.entity.StaffRole;
import com.npc.lottery.util.HibernateTool;

/**
 * 用户所拥有的角色数据库处理类
 *
 * @author none
 *
 */
public class StaffRoleDao extends HibernateDaoSupport implements IStaffRoleDao {

	/**
	 * 查询满足指定查询条件的数据记录
	 * 
	 * @param conditionData
	 *            查询条件信息
	 * @param firstResult
	 *            需要查询的第一个记录数
	 * @param maxResults
	 *            需要查询的记录数目
	 * @return  StaffRole 类型的 List
	 */
	public List findStaffRoleList(final ConditionData conditionData,
			final int firstResult, final int maxResults) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {

				Criteria criteria = session.createCriteria(StaffRole.class);

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
	 * 根据用户ID，查询对应的资源角色授权信息
	 * 
	 * @param userID
	 * @return
	 */
    //	public List findFuncRoleByUserID2(Long userID) {
    //
    //		Session session = getSession(true);
    //		Query query = session
    //				.createQuery("SELECT autho FROM StaffRole autho LEFT JOIN autho.roleID roles WHERE autho.userID=:USERID AND roles.roleType=:ROLETYPE");
    //		query.setLong("USERID", userID);
    //		query.setString("ROLETYPE", Roles.ROLE_TYPE_RES);
    //
    //		List resultList = query.list();
    //
    //		session.close();
    //
    //		return resultList;
    //	}

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
								.createCriteria(StaffRole.class);

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
	public Long saveStaffRole(StaffRole entity) {

		Long result = null;

		result = (Long) getHibernateTemplate().save(entity);

		return result;
	}

	/**
	 * 更新信息
	 * 
	 * @param entity 待更新的信息
	 */
	public void update(StaffRole entity) {
		getHibernateTemplate().update(entity);
	}

	/**
	 * 删除
	 * 
	 * @param entity
	 */
	public void delete(StaffRole entity) {
		getHibernateTemplate().delete(entity);
	}
}
