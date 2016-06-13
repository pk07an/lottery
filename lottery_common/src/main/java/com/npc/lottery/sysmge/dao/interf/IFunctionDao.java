package com.npc.lottery.sysmge.dao.interf;

import java.util.List;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.sysmge.entity.Function;

/**
 * 功能信息
 *
 * @author none
 *
 */
public interface IFunctionDao {

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
			final int firstResult, final int maxResults);

	/**
	 * 根据id获取子节点列表
	 * @param id
	 * @return
	 */
	public List getChildNodeByID(Long id);

	/**
	 * 保存或更新对象
	 * @param function
	 */
	public void saveOrUpdate(Function entity);

	/**
	 * 删除对象
	 * @param entity
	 */
	public void deleteFunction(Function entity);
}
