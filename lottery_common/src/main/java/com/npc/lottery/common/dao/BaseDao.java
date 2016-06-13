package com.npc.lottery.common.dao;



import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * DAO基类实现（Hibernate版本）
 * 
 * @author none
 *
 */
public class BaseDao extends HibernateDaoSupport implements IBaseDao {

	private static final long serialVersionUID = -51722025542184890L;

	/**
	 * 保存或更新对象
	 * @param entity
	 */
	public void saveOrUpdate(Object entity) {
		getHibernateTemplate().saveOrUpdate(entity);
	}

	/**
	 * 删除对象
	 * @param entity
	 */
	public void delete(Object entity) {
		getHibernateTemplate().delete(entity);
	}

	/**
	 * 根据ID查询指定的对象
	 * 
	 * @param obj   需要查询的对象，一般New一个对应POJO类即可
	 * @param ID
	 * @return
	 */
	public Object findByID(Object obj, long ID) {
		return (Object) getHibernateTemplate().get(obj.getClass(), ID);
	}

	/**
	 * 保存信息
	 * 
	 * @param entity    待保存的信息
	 * @return  此信息记录所对应的ID，Long类型
	 */
	public Long save(Object entity) {
		Long result = (Long) getHibernateTemplate().save(entity);
		return Long.parseLong("" + result);
	}
	

}
