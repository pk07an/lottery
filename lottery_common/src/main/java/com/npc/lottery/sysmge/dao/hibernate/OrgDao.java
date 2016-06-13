package com.npc.lottery.sysmge.dao.hibernate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.sysmge.dao.interf.IOrgDao;
import com.npc.lottery.sysmge.entity.Org;
import com.npc.lottery.sysmge.entity.OrgExt;
import com.npc.lottery.util.HibernateTool;

/**
 * 组织机构的数据库处理类
 *
 * @author none
 *
 */
public class OrgDao extends HibernateDaoSupport implements IOrgDao {

	/**
	 * 查询满足指定查询条件的数据记录
	 * 
	 * @param conditionData
	 *            查询条件信息
	 * @param firstResult
	 *            需要查询的第一个记录数
	 * @param maxResults
	 *            需要查询的记录数目
	 * @return  Org 类型的 List
	 */
	public List findOrgList(final ConditionData conditionData,
			final int firstResult, final int maxResults) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {

				Criteria criteria = session.createCriteria(Org.class);

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
	public long findAmountOrgList(final ConditionData conditionData) {
		long amount = 0;

		amount = ((Long) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {

						Criteria criteria = session.createCriteria(Org.class);

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
	 * 查询指定 orgType 所对应的营销机构数据（包含扩展信息）
	 * 
	 * @param orgType
	 * @return
	 */
	public ArrayList<Org> findSaleOrgList(Long orgType) {
		ArrayList<Org> resultList = new ArrayList<Org>();

		Session session = getSession(true);
		Query query = session
				.createQuery("SELECT org, orgExt FROM Org org LEFT JOIN org.orgExtEntity orgExt WHERE orgExt.saleType=:SALETYPE AND org.orgType=:ORGTYPE");
		//设置查询条件
		query.setString("SALETYPE", OrgExt.SALE_TYPE_YES);//营销机构
		query.setLong("ORGTYPE", orgType);//orgType

		List tempList = query.list();

		if (null != tempList) {
			for (int i = 0; i < tempList.size(); i++) {
				//取出Org对象
				resultList.add((Org) (((Object[]) tempList.get(i))[0]));
			}
		}

		session.close();

		return resultList;
	}

	/**
	 * 根据id获取对象
	 * @param id
	 * @return
	 */
	public Object getOrgByID(final Long id) {
		return getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {

				Criteria criteria = session.createCriteria(Org.class);

				criteria.add(Restrictions.eq("orgID", id));
				Object org = criteria.uniqueResult();
				return org;
			}
		});
	}

	public void saveOrUpdate(Org entity) {
		getHibernateTemplate().saveOrUpdate(entity);
	}
}
