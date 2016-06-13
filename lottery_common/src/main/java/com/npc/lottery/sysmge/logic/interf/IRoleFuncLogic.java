package com.npc.lottery.sysmge.logic.interf;

import java.util.ArrayList;
import java.util.HashMap;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.sysmge.entity.RoleFunc;

/**
 * 角色所拥有的功能逻辑处理类接口
 *
 * @author none
 *
 */
public interface IRoleFuncLogic {

	/**
	 * 查询满足指定查询条件的数据记录
	 *  
	 * @param conditionData
	 *            查询条件信息
	 * @param pageCurrentNo 第一页为 1
	 *            需要查询的页码
	 * @param pageSize
	 *            页面大小
	 * @return  
	 */
	public ArrayList<RoleFunc> findRoleFuncList(ConditionData conditionData,
			int pageCurrentNo, int pageSize);

	/**
	 * 统计满足指定查询条件的记录数目
	 * 
	 * @param conditionData
	 *            查询条件信息     
	 * @return
	 */
	public long findAmountRoleFuncList(ConditionData conditionData);

	/**
	 * 保存信息
	 * 
	 * @param entity    待保存的信息
	 * @return  此信息记录所对应的ID，Long类型
	 */
	public Long saveRoleFunc(RoleFunc entity);

	/**
	 * 删除信息
	 * 
	 * @param entity
	 */
	public void delete(RoleFunc entity);

	/**
	 * 根据角色ID，查询对应的授权信息
	 * 
	 * @param roleID
	 * @return
	 */
	public ArrayList<RoleFunc> findRoleFuncByRoleID(Long roleID);

	/**
	 * 更新角色的授权信息
	 * 
	 * @param roleID		角色ID
	 * @param newList		角色新的功能权限ID列表
	 * @param originList	角色原来的功能权限ID列表
	 * @return
	 * 			key = create	value中存放新增的功能权限数目，Long类型
	 * 			key = delete	value中存放删除的功能权限数目，Long类型
	 */
	public HashMap updateRoleFunc(Long roleID, ArrayList<Long> newList,
			ArrayList<Long> originList);

	/**
	 * 更新角色的授权信息
	 * 
	 * @param roleID		角色ID
	 * @param newList		角色新的功能权限列表
	 * @return
	 * 			key = create	value中存放新增的功能权限数目，Long类型
	 * 			key = delete	value中存放删除的功能权限数目，Long类型
	 */
	public HashMap updateRoleFunc(Long roleID, ArrayList<Long> newList);

	/**
	 * 更新角色的授权信息
	 * 
	 * @param roleID            角色ID
	 * @param createFuncIDList  增加的功能权限ID列表
	 * @param deleteFuncIDList  删除的功能权限ID列表
	 * 
	 * @return
	 *          key = create    value中存放新增的功能权限数目，Long类型
	 *          key = delete    value中存放删除的功能权限数目，Long类型
	 */
	public HashMap updateRoleFunc2(Long roleID,
			ArrayList<Long> createFuncIDList, ArrayList<Long> deleteFuncIDList);
}
