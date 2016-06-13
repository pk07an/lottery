package com.npc.lottery.sysmge.dao.interf;

import java.util.List;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.sysmge.entity.OrgExt;

/**
 * 组织机构扩展表的数据库处理类接口
 *
 * @author none
 *
 */
public interface IOrgExtDao {

	/**
	 * 查询满足指定查询条件的数据记录
	 * 
	 * @param conditionData
	 *            查询条件信息
	 * @param firstResult
	 *            需要查询的第一个记录数
	 * @param maxResults
	 *            需要查询的记录数目
	 * @return  OrgExt 类型的 List
	 */
	public List findOrgExtList(final ConditionData conditionData,
			final int firstResult, final int maxResults);

	/**
	 * 统计满足指定查询条件的信息数目
	 * 
	 * @param conditionData
	 *            查询条件信息
	 * @return
	 */
	public long findAmountOrgExtList(final ConditionData conditionData);

	/**
	 * 根据ID获取对象
	 * @param ID
	 * @return
	 */
	public OrgExt getOrgExtByID(final Long ID);
}
