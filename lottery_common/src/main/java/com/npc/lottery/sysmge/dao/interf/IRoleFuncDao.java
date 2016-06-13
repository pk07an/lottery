package com.npc.lottery.sysmge.dao.interf;

import java.util.List;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.sysmge.entity.RoleFunc;

/**
 * 角色所拥有的功能数据库处理类接口
 *
 * @author none
 *
 */
public interface IRoleFuncDao {

	/**
	 * 查询满足指定查询条件的数据记录
	 * 
	 * @param conditionData
	 *            查询条件信息
	 * @param firstResult
	 *            需要查询的第一个记录数
	 * @param maxResults
	 *            需要查询的记录数目
	 * @return  RoleFunc 类型的 List
	 */
	public List findRoleFuncList(final ConditionData conditionData,
			final int firstResult, final int maxResults);

	/**
	 * 统计满足指定查询条件的信息数目
	 * 
	 * @param conditionData
	 *            查询条件信息
	 * @return
	 */
	public long findAmountDemoList(final ConditionData conditionData);

	/**
	 * 保存信息
	 * 
	 * @param entity    待保存的信息
	 * @return  此信息记录所对应的ID，Long类型
	 */
	public Long saveRoleFunc(RoleFunc entity);

	/**
	 * 更新信息
	 * 
	 * @param entity 待更新的信息
	 */
	public void update(RoleFunc entity);

	/**
	 * 删除
	 * 
	 * @param entity
	 */
	public void delete(RoleFunc entity);

}
