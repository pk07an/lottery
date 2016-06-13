package com.npc.lottery.sysmge.dao.interf;

import java.util.ArrayList;
import java.util.List;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.sysmge.entity.Org;

/**
 * 组织机构的数据库处理类接口
 *
 * @author none
 *
 */
public interface IOrgDao {

	/**
	 * 查询满足指定查询条件的数据记录
	 * 
	 * @param conditionData
	 *            查询条件信息
	 * @param firstResult
	 *            需要查询的第一个记录数
	 * @param maxResults
	 *            需要查询的记录数目
	 * @return  Org 类型的 List
	 */
	public List findOrgList(final ConditionData conditionData,
			final int firstResult, final int maxResults);

	/**
	 * 统计满足指定查询条件的信息数目
	 * 
	 * @param conditionData
	 *            查询条件信息
	 * @return
	 */
	public long findAmountOrgList(final ConditionData conditionData);

	/**
	 * 根据id获取对象
	 * @param id
	 * @return
	 */
	public Object getOrgByID(final Long id);

	public void saveOrUpdate(Org entity);

	/**
	 * 查询指定 orgType 所对应的营销机构数据（包含扩展信息）
	 * 
	 * @param orgType
	 * @return
	 */
	public ArrayList<Org> findSaleOrgList(Long orgType);
}
