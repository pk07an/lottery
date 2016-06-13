package com.npc.lottery.sysmge.logic.interf;

import java.util.ArrayList;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.sysmge.entity.OrgExt;

/**
 * 组织机构扩展业务处理类接口
 *
 * @author none
 *
 */
public interface IOrgExtLogic {

	/**
	 * 查询满足指定查询条件的数据记录
	 * 
	 * @param conditionData
	 *            查询条件信息
	 * @param pageCurrentNo 第一页为 1
	 *            需要查询的页码
	 * @param pageSize
	 *            页面大小
	 * @return  OrgExt 类型的 ArrayList
	 */
	public ArrayList<OrgExt> findOrgExtList(ConditionData conditionData,
			int pageCurrentNo, int pageSize);

	/**
	 * 统计满足指定查询条件的记录数目
	 * 
	 * @param conditionData
	 *            查询条件信息     
	 * @return
	 */
	public long findAmountOrgExtList(ConditionData conditionData);

	/**
	 * 根据ID查询数据
	 * 
	 * @return
	 */
	public OrgExt findOrgExtByID(long ID);

	/**
	 * 查询所有的机构扩展信息
	 * 
	 * @return OrgExt 类型的 List
	 */
	public ArrayList<OrgExt> findAllOrg();
}
