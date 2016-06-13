package com.npc.lottery.sysmge.dao.hibernate;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.sysmge.dao.interf.IOrgExtDao;
import com.npc.lottery.sysmge.entity.OrgExt;
import com.npc.lottery.util.HibernateTool;

/**
 * 组织机构扩展表的数据库处理类接口
 *
 * @author none
 *
 */
public class OrgExtDao extends HibernateDaoSupport implements IOrgExtDao {

	/**
	 * 查询满足指定查询条件的数据记录
	 * 
	 * @param conditionData
	 *            查询条件信息
	 * @param firstResult
	 *            需要查询的第一个记录数
	 * @param maxResults
	 *            需要查询的记录数目
	 * @return  OrgExt 类型的 List
	 */
	public List findOrgExtList(final ConditionData conditionData,
			final int firstResult, final int maxResults) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {

				Criteria criteria = session.createCriteria(OrgExt.class);

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
	public long findAmountOrgExtList(final ConditionData conditionData) {
		long amount = 0;

		amount = ((Long) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {

						Criteria criteria = session
								.createCriteria(OrgExt.class);

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
	 * 根据ID获取对象
	 * @param ID
	 * @return
	 */
	public OrgExt getOrgExtByID(final Long ID) {

		OrgExt orgExt = null;

		Object temp = getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {

				Criteria criteria = session.createCriteria(OrgExt.class);

				criteria.add(Restrictions.eq("orgID", ID));
				Object orgExt = criteria.uniqueResult();

				return orgExt;
			}
		});

		if (null != temp) {
			orgExt = (OrgExt) temp;
		}

		return orgExt;
	}
}
