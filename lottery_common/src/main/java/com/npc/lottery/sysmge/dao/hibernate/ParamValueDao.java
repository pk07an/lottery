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
import com.npc.lottery.sysmge.dao.interf.IParamValueDao;
import com.npc.lottery.sysmge.entity.ParamValue;
import com.npc.lottery.util.HibernateTool;

/**
 * 参数值表
 *
 *
 */
public class ParamValueDao extends HibernateDaoSupport implements
		IParamValueDao {

	/**
	 * 查询满足指定查询条件的功能信息数据记录
	 * 
	 * @param conditionData
	 *            查询条件信息
	 * @param firstResult
	 *            需要查询的第一个记录数
	 * @param maxResults
	 *            需要查询的记录数目
	 * @return  ParamValue 类型的 List
	 */
	public List findParamValueList(final ConditionData conditionData,
			final int firstResult, final int maxResults) {

		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {

				Criteria criteria = session.createCriteria(ParamValue.class);

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
	public long findAmountParamValue(final ConditionData conditionData) {
		long amount = 0;

		amount = ((Long) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {

						Criteria criteria = session
								.createCriteria(ParamValue.class);

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
	 * 根据id查询
	 * @param id
	 */
	public ParamValue getParamValueByID(Long id) {
		return (ParamValue) getHibernateTemplate().get(ParamValue.class, id);
	}

	/**
	 * 根据父节点ID查询
	 * 
	 * @param id
	 */
	public List getParamValueByParentParamID(Long id) {
		String hql = "from ParamValue where Param.ID=" + id;
		return getHibernateTemplate().find(hql);
	}

	/**
	 * 保存或更新对象
	 * @param entity
	 */
	public void saveOrUpdate(ParamValue entity) {
		getHibernateTemplate().saveOrUpdate(entity);
	}

	/**
	 * 删除对象
	 * @param entity
	 */
	public void delete(ParamValue entity) {
	    getHibernateTemplate().clear();
		getHibernateTemplate().delete(entity);
	}

}
