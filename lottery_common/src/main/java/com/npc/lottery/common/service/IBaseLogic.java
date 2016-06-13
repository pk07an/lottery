package com.npc.lottery.common.service;

/**
 * Logic的基类接口
 * 
 * @author none
 *
 */
public interface IBaseLogic {

	/**
	 * 保存或更新对象
	 * @param entity
	 */
	public void saveOrUpdate(Object entity);

	/**
	 * 删除对象
	 * @param entity
	 */
	public void delete(Object entity);

	/**
	 * 根据ID查询指定的对象
	 * 
	 * @param obj   需要查询的对象，一般New一个对应POJO类即可
	 * @param ID
	 * @return
	 */
	public Object findByID(Object obj, long ID);

	/**
	 * 保存信息
	 * 
	 * @param entity    待保存的信息
	 * @return  此信息记录所对应的ID，Long类型
	 */
	public Long save(Object entity);
}
