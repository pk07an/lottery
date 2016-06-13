package com.npc.lottery.sysmge.dao.interf;

import java.util.List;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.sysmge.entity.ParamValue;

/**
 * 参数类别表
 *
 *
 */
public interface IParamValueDao {

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
	public List findParamValueList(final ConditionData conditionData,
			final int firstResult, final int maxResults);

	/**
	 * 统计满足指定查询条件的信息数目
	 * 
	 * @param conditionData
	 *            查询条件信息
	 * @return
	 */
	public long findAmountParamValue(final ConditionData conditionData);

	/**
	 * 根据id查询
	 * @param id
	 */
	public ParamValue getParamValueByID(Long id);

	/**
	 * 根据父节点ID查询
	 * 
	 * @param id
	 */
	public List getParamValueByParentParamID(Long id);

	/**
	 * 保存或更新对象
	 * @param entity
	 */
	public void saveOrUpdate(ParamValue entity);

	/**
	 * 删除对象
	 * @param entity
	 */
	public void delete(ParamValue entity);
}
