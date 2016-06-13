package com.npc.lottery.sysmge.logic.spring;

import java.util.ArrayList;
import java.util.HashMap;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.sysmge.dao.interf.IRoleFuncDao;
import com.npc.lottery.sysmge.entity.RoleFunc;
import com.npc.lottery.sysmge.logic.interf.IRoleFuncLogic;
import com.npc.lottery.util.Tool;

/**
 * 角色所拥有的功能逻辑处理类
 *
 * @author none
 *
 */
public class RoleFuncLogic implements IRoleFuncLogic {

	private IRoleFuncDao roleFuncDao = null;

	public void setRoleFuncDao(IRoleFuncDao roleFuncDao) {
		this.roleFuncDao = roleFuncDao;
	}

	/**
	 * 根据角色ID和功能ID，查询对应的数据记录
	 * 
	 * @param roleID    角色ID
	 * @param funcID    功能ID
	 * 
	 * @return  不存在则返回 null
	 */
	public RoleFunc findRoleFunc(Long roleID, Long funcID) {

		RoleFunc entity = null;

		ConditionData conditionData = new ConditionData();
		conditionData.addEqual("roleID", roleID);
		conditionData.addEqual("funcID", funcID);

		ArrayList<RoleFunc> tempList = this.findRoleFuncList(conditionData, 1,
				100);
		if (null != tempList && tempList.size() > 0) {
			entity = tempList.get(0);
		}

		return entity;
	}

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
			int pageCurrentNo, int pageSize) {
		ArrayList<RoleFunc> resultList = null;

		// 根据页码和页面大小，获得需要查询的开始数据和数量
		int firstResult = (pageCurrentNo - 1) * pageSize;
		int maxResults = pageSize;

		// 查询数据
		try {
			resultList = (ArrayList<RoleFunc>) roleFuncDao.findRoleFuncList(
					conditionData, firstResult, maxResults);
		} catch (Exception ex) {
			Tool.printExceptionStack(ex);
		}

		return resultList;
	}

	/**
	 * 统计满足指定查询条件的记录数目
	 * 
	 * @param conditionData
	 *            查询条件信息     
	 * @return
	 */
	public long findAmountRoleFuncList(ConditionData conditionData) {
		long amount = 0;

		try {
			amount = roleFuncDao.findAmountDemoList(conditionData);
		} catch (Exception ex) {
			Tool.printExceptionStack(ex);
		}

		return amount;
	}

	/**
	 * 保存信息
	 * 
	 * @param entity    待保存的信息
	 * @return  此信息记录所对应的ID，Long类型
	 */
	public Long saveRoleFunc(RoleFunc entity) {

		Long result = null;

		result = roleFuncDao.saveRoleFunc(entity);

		return result;
	}

	/**
	 * 删除信息
	 * 
	 * @param entity
	 */
	public void delete(RoleFunc entity) {
		roleFuncDao.delete(entity);
	}

	/**
	 * 根据角色ID和功能ID，删除对应的数据记录
	 * 
	 * @param roleID    角色ID
	 * @param funcID    功能ID
	 */
	public void delete(Long roleID, Long funcID) {
		//定位待查询的数据
		RoleFunc entity = this.findRoleFunc(roleID, funcID);

		if (null != entity) {
			this.delete(entity);
		}
	}

	/**
	 * 根据角色ID，查询对应的授权信息
	 * 
	 * @param roleID
	 * @return
	 */
	public ArrayList<RoleFunc> findRoleFuncByRoleID(Long roleID) {
		ArrayList<RoleFunc> result = null;

		ConditionData conditionData = new ConditionData();
		conditionData.addEqual("roleID", roleID);

		result = this.findRoleFuncList(conditionData, 1, 10000);

		return result;
	}

	/**
	 * 更新角色的授权信息
	 * 
	 * @param roleID		角色ID
	 * @param newFuncIDList		角色新的功能权限ID列表
	 * @param originFuncIDList	角色原来的功能权限ID列表
	 * @return
	 * 			key = create	value中存放新增的功能权限数目，Long类型
	 * 			key = delete	value中存放删除的功能权限数目，Long类型
	 */
	public HashMap updateRoleFunc(Long roleID, ArrayList<Long> newFuncIDList,
			ArrayList<Long> originFuncIDList) {
		HashMap resultMap = new HashMap();
		long createNum = 0;
		long deleteNum = 0;

		if (null == newFuncIDList) {
			newFuncIDList = new ArrayList<Long>();
		}
		if (null == originFuncIDList) {
			originFuncIDList = new ArrayList<Long>();
		}

		ArrayList<Long> createList = new ArrayList<Long>();
		ArrayList<Long> deleteList = new ArrayList<Long>();

		boolean isEqual = false;
		for (int outer = 0; outer < newFuncIDList.size(); outer++) {
			isEqual = false;
			for (int inner = 0; inner < originFuncIDList.size(); inner++) {
				if (newFuncIDList.get(outer).longValue() == originFuncIDList
						.get(inner).longValue()) {
					//如果数据在newList和originList中都存在，则说明这条数据无需变更
					originFuncIDList.remove(inner);
					isEqual = true;
					break;
				}
			}
			if (!isEqual) {
				//如果数据不存在于 originList 中，则说明需要新增此数据
				createList.add(newFuncIDList.get(outer));
			}
		}
		deleteList = originFuncIDList;

		RoleFunc entity = null;
		//根据create列表增加数据
		for (int i = 0; i < createList.size(); i++) {
			entity = new RoleFunc();
			entity.setRoleID(roleID);
			entity.setFuncID(createList.get(i));
			entity.setAuthorizType(RoleFunc.AUTHORIZTYPE_ALONE);
			this.saveRoleFunc(entity);
		}

		//删除delete列表中的数据
		for (int i = 0; i < deleteList.size(); i++) {
			//删除数据
			this.delete(roleID, deleteList.get(i));
		}

		resultMap.put("create", createList.size());
		resultMap.put("delete", deleteList.size());
		return resultMap;
	}

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
			ArrayList<Long> createFuncIDList, ArrayList<Long> deleteFuncIDList) {
		HashMap resultMap = new HashMap();
		long createNum = 0;
		long deleteNum = 0;

		if (null == createFuncIDList) {
			createFuncIDList = new ArrayList<Long>();
		}
		if (null == deleteFuncIDList) {
			deleteFuncIDList = new ArrayList<Long>();
		}

		//1、查询角色当前的授权信息
		ArrayList<RoleFunc> entityList = this.findRoleFuncByRoleID(roleID);
		ArrayList<Long> roleFuncIDList = new ArrayList<Long>();
		if (null != entityList && entityList.size() > 0) {
			for (int i = 0; i < entityList.size(); i++) {
				//获取功能ID列表
				roleFuncIDList.add(entityList.get(i).getFuncID());
			}
		}

		//2、从增加的功能权限ID列表中过滤掉已经存在的信息
		if (roleFuncIDList.size() > 0) {
			for (int i = 0; i < createFuncIDList.size(); i++) {
				if (roleFuncIDList.contains(createFuncIDList.get(i))) {
					createFuncIDList.remove(i);
					i--;
					continue;
				}
			}
		}

		//3、从删除的功能权限ID列表中过滤掉当前并未授权的信息
		if (roleFuncIDList.size() > 0) {
			for (int i = 0; i < deleteFuncIDList.size(); i++) {
				if (!roleFuncIDList.contains(deleteFuncIDList.get(i))) {
					deleteFuncIDList.remove(i);
					i--;
					continue;
				}
			}
		} else {
			//如果之前没有授权，则直接清空删除列表
			deleteFuncIDList = new ArrayList<Long>();
		}

		//4、根据create列表增加数据
		RoleFunc entity = null;
		for (int i = 0; i < createFuncIDList.size(); i++) {
			entity = new RoleFunc();
			entity.setRoleID(roleID);
			entity.setFuncID(createFuncIDList.get(i));
			entity.setAuthorizType(RoleFunc.AUTHORIZTYPE_ALONE);
			this.saveRoleFunc(entity);
		}

		//5、删除delete列表中的数据
		for (int i = 0; i < deleteFuncIDList.size(); i++) {
			//删除数据
			this.delete(roleID, deleteFuncIDList.get(i));
		}

		resultMap.put("create", createFuncIDList.size());
		resultMap.put("delete", deleteFuncIDList.size());
		return resultMap;
	}

	/**
	 * 更新角色的授权信息
	 * 
	 * @param roleID		角色ID
	 * @param newFuncIDList		角色新的功能权限ID列表
	 * @return
	 * 			key = create	value中存放新增的功能权限数目，Long类型
	 * 			key = delete	value中存放删除的功能权限数目，Long类型
	 */
	public HashMap updateRoleFunc(Long roleID, ArrayList<Long> newFuncIDList) {

		//查询角色ID对应的当前功能权限列表
		ArrayList<RoleFunc> originList = this.findRoleFuncByRoleID(roleID);
		ArrayList<Long> originFuncIDList = new ArrayList<Long>();
		for (int i = 0; i < originList.size(); i++) {
			originFuncIDList.add(originList.get(i).getFuncID());
		}

		return this.updateRoleFunc(roleID, newFuncIDList, originFuncIDList);
	}
}
