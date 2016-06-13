package com.npc.lottery.sysmge.dao.interf;

import java.util.List;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.sysmge.entity.Param;

/**
 * 参数类别表
 *
 *
 */
public interface IParamDao {

	/**
	 * 查询满足指定查询条件的功能信息数据记录
	 * 
	 * @param conditionData
	 *            查询条件信息
	 * @param firstResult
	 *            需要查询的第一个记录数
	 * @param maxResults
	 *            需要查询的记录数目
	 * @return  Param 类型的 List
	 */
	public List findParamList(final ConditionData conditionData,
			final int firstResult, final int maxResults);

	/**
	 * 统计满足指定查询条件的信息数目
	 * 
	 * @param conditionData
	 *            查询条件信息
	 * @return
	 */
	public long findAmountParam(final ConditionData conditionData);

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public Param getParamByID(Long id);

	/**
	 * 保存或更新对象
	 * @param entity
	 */
	public void saveOrUpdate(Param entity);

	/**
	 * 删除对象
	 * @param entity
	 */
	public void delete(Param entity);
}
