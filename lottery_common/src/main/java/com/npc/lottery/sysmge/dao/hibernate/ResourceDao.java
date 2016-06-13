package com.npc.lottery.sysmge.dao.hibernate;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.sysmge.dao.interf.IResourceDao;
import com.npc.lottery.sysmge.entity.Resource;
import com.npc.lottery.util.HibernateTool;

/**
 * 资源信息
 * 
 * @author none
 *
 */
public class ResourceDao extends HibernateDaoSupport implements IResourceDao {

	/**
	 * 查询满足指定查询条件的资源信息数据记录
	 * 
	 * @param conditionData
	 *            查询条件信息
	 * @param firstResult
	 *            需要查询的第一个记录数
	 * @param maxResults
	 *            需要查询的记录数目
	 * @return  Resource 类型的 List
	 */
	public List findResourceList(final ConditionData conditionData,
			final int firstResult, final int maxResults) {

		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {

				Criteria criteria = session.createCriteria(Resource.class);

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
	 * 保存或更新对象
	 * @param entity
	 */
	public void saveOrUpdate(Resource entity) {
		getHibernateTemplate().saveOrUpdate(entity);
	}

	/**
	 * 删除对象
	 * @param entity
	 */
	public void delete(Resource entity) {
	    getHibernateTemplate().clear();
		getHibernateTemplate().delete(entity);
	}
}
