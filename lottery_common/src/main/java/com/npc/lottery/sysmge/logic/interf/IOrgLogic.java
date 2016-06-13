package com.npc.lottery.sysmge.logic.interf;

import java.util.ArrayList;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.sysmge.entity.Org;

/**
 * 组织机构业务处理类接口
 *
 * @author none
 *
 */
public interface IOrgLogic {

	/**
	 * 查询满足指定查询条件的数据记录
	 * 
	 * @param conditionData
	 *            查询条件信息
	 * @param pageCurrentNo 第一页为 1
	 *            需要查询的页码
	 * @param pageSize
	 *            页面大小
	 * @return  Org 类型的 ArrayList
	 */
	public ArrayList<Org> findOrgList(ConditionData conditionData,
			int pageCurrentNo, int pageSize);

	/**
	 * 统计满足指定查询条件的记录数目
	 * 
	 * @param conditionData
	 *            查询条件信息     
	 * @return
	 */
	public long findAmountOrgList(ConditionData conditionData);

	/**
	 * 根据ID查询数据
	 * 
	 * @return
	 */
	public Org findOrgByID(long ID);

	/**
	 * 根据ID查询数据
	 * 
	 * @deprecated 此方法即将被删除，请使用 findOrgByID 方法
	 * @param ID
	 * @return
	 */
	public Org findOrgExtByID(long ID);

	/**
	 * 查询所有的机构信息
	 * 
	 * @return Org 类型的 List
	 */
	public ArrayList<Org> findAllOrg();

	/**
	 * 查询指定 orgType 所对应的营销机构数据（包含扩展信息）
	 * 
	 * @param orgType 的取值参考 com.npc.lottery.sysmge.entity.Org 中关于ORG_TYPE的常量定义
	 * @return
	 */
	public ArrayList<Org> findSaleOrgList(Long orgType);
}
