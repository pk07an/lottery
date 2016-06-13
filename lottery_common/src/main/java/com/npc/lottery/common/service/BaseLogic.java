package com.npc.lottery.common.service;

import com.npc.lottery.common.dao.IBaseDao;

/**
 * Logic的基类实现
 * 
 * @author none
 *
 */
public class BaseLogic implements IBaseLogic {

	private IBaseDao baseDao = null;//数据库处理类，由spring框架注入实例

	public void setBaseDao(IBaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public IBaseDao getBaseDao() {
		return baseDao;
	}

	/**
	 * 保存或更新对象

	 * @param entity
	 */
	public void saveOrUpdate(Object entity) {
		baseDao.saveOrUpdate(entity);
	}

	/**
	 * 删除对象
	 * @param entity
	 */
	public void delete(Object entity) {
		baseDao.delete(entity);
	}

	/**
	 * 根据ID查询指定的对象
	 * 
	 * @param obj   需要查询的对象，一般New一个对应POJO类即可
	 * @param ID
	 * @return
	 */
	public Object findByID(Object obj, long ID) {
		//System.out.println("Object = " + obj);
		//System.out.println("baseDao = " + baseDao);
		return (Object) baseDao.findByID(obj, ID);
	}

	/**
	 * 保存信息
	 * 
	 * @param entity    待保存的信息
	 * @return  此信息记录所对应的ID，Long类型
	 */
	public Long save(Object entity) {
		Long result = (Long) baseDao.save(entity);
		return result;
	}
}
