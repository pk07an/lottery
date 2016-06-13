package com.npc.lottery.common.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;

import com.npc.lottery.util.Page;


/**
 * DAO基类接口
 * 
 * @author none
 *
 */
public interface ILotteryBaseDao <PK extends Serializable, T>{

	/**
	 * 保存或更新对象

	 * @param entity
	 */
	public void save(final T entity);
	
	public void update(final T entity);

	public void delete(final T entity);
	/**
	 * 按id删除对象.
	 */
	public void delete(final PK id);
	
	/**
	 * 按id获取对象.
	 */
	public T get(final PK id);
	/**
	 * 获取全部对象.
	 */
	public List<T> getAll();
	/**
	 * 获取全部对象,支持排序.
	 */
	public List<T> getAll(String orderBy, boolean isAsc);
	/**
	 * 按属性查找对象列表,匹配方式为相等.
	 */
	public List<T> findBy(final String propertyName, final Object value);
	
	/**
	 * 按属性查找唯一对象,匹配方式为相等.
	 */
	public T findUniqueBy(final String propertyName, final Object value);
	
	/**
	 * 按id列表获取对象.
	 */
	public List<T> findByIds(List<PK> ids) ;
	
	/**
	 * 按HQL查询对象列表.
	 * 
	 * @param values
	 *            数量可变的参数,按顺序绑定.
	 */
	public <X> List<X> find(final String hql, final Object... values);
	
	/**
	 * 按HQL查询对象列表.
	 * 
	 * @param values
	 *            命名参数,按名称绑定.
	 */
	public <X> List<X> find(final String hql, final Map<String, ?> values);
	
	/**
	 * 按HQL查询唯一对象.
	 * 
	 * @param values
	 *            数量可变的参数,按顺序绑定.
	 */
	public <X> X findUnique(final String hql, final Object... values);
	
	
	/**
	 * 按HQL查询唯一对象.
	 * 
	 * @param values
	 *            命名参数,按名称绑定.
	 */
	public <X> X findUnique(final String hql, final Map<String, ?> values);
	
	/**
	 * 执行HQL进行批量修改/删除操作.
	 */
	public int batchExecute(final String hql, final Object... values);
	
	/**
	 * 执行HQL进行批量修改/删除操作.
	 * 
	 * @return 更新记录数.
	 */
	public int batchExecute(final String hql, final Map<String, ?> values);
	
	
	// -- 分页查询函数 --//
		/**
		 * 分页获取全部对象.
		 */
		public Page<T> getAll(final Page<T> page);
	
	/**
	 * 按HQL分页查询.
	 * 
	 * @param page
	 *            分页参数.不支持其中的orderBy参数.
	 * @param hql
	 *            hql语句.
	 * @param values
	 *            数量可变的查询参数,按顺序绑定.
	 * 
	 * @return 分页查询结果, 附带结果列表及所有查询时的参数.
	 */
	public Page<T> findPage(final Page<T> page, final String hql,
			final Object... values);
	
	
	
	/**
	 * 按HQL分页查询.
	 * 
	 * @param page
	 *            分页参数.
	 * @param hql
	 *            hql语句.
	 * @param values
	 *            命名参数,按名称绑定.
	 * 
	 * @return 分页查询结果, 附带结果列表及所有查询时的参数.
	 */
	public Page<T> findPage(final Page<T> page, final String hql,
			final Map<String, ?> values) ;
	
	
	
	/**
	 * 按Criteria分页查询.
	 * 
	 * @param page
	 *            分页参数.
	 * @param criterions
	 *            数量可变的Criterion.
	 * 
	 * @return 分页查询结果.附带结果列表及所有查询时的参数.
	 */
	public Page<T> findPage(final Page<T> page, final Criterion... criterions);

	/**
	 * 按Criteria查询对象列表.
	 * 
	 * @param criterions
	 *            数量可变的Criterion.
	 */
	public List<T> find(final Criterion... criterions);
	
	
	/**
	 * 按Criteria查询唯一对象.
	 * 
	 * @param criterions
	 *            数量可变的Criterion.
	 */
	public T findUnique(final Criterion... criterions);
	
}
