package com.npc.lottery.sysmge.dao.hibernate;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.sysmge.dao.interf.IFunctionDao;
import com.npc.lottery.sysmge.entity.Function;
import com.npc.lottery.util.HibernateTool;

/**
 * 功能信息
 *
 * @author none
 *
 */
public class FunctionDao extends HibernateDaoSupport implements IFunctionDao {

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
	public List findFunctionList(final ConditionData conditionData,
			final int firstResult, final int maxResults) {

		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {

				Criteria criteria = session.createCriteria(Function.class);

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
	 * 根据id获取子节点列表
	 * @param id
	 * @return
	 */
	public List getChildNodeByID(Long id) {
		String hql = "from Function where parentFunc.id =" + id;
		List lst = getHibernateTemplate().find(hql);
		return lst;
	}

	/**
	 * 保存或更新对象
	 * @param entity
	 */
	public void saveOrUpdate(Function entity) {
		getHibernateTemplate().saveOrUpdate(entity);
	}

	/**
	 * 删除对象
	 * @param entity
	 */
	public void deleteFunction(Function entity) {
		getHibernateTemplate().delete(entity);
	}

}
