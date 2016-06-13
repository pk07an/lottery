package com.npc.lottery.sysmge.dao.interf;

import java.util.List;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.sysmge.entity.Resource;

/**
 * 资源信息
 * 
 * @author none
 *
 */
public interface IResourceDao {

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
			final int firstResult, final int maxResults);

	/**
	 * 保存或更新对象
	 * @param function
	 */
	public void saveOrUpdate(Resource entity);

	/**
	 * 删除对象
	 * @param entity
	 */
	public void delete(Resource entity);
}
