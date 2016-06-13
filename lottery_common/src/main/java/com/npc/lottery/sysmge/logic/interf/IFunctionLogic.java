package com.npc.lottery.sysmge.logic.interf;

import java.util.List;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.sysmge.entity.Function;

/**
 * 功能逻辑处理类
 * 
 * @author none
 * 
 */
public interface IFunctionLogic {

	/**
	 * 查询满足指定查询条件的功能信息数据记录
	 * 
	 * @param conditionData
	 *            查询条件信息
	 * @param pageCurrentNo
	 *            第一页为 1 需要查询的页码
	 * @param pageSize
	 *            页面大小
	 * @return Function 类型的 List
	 */
	public List findFunctionList(ConditionData conditionData,
			int pageCurrentNo, int pageSize);

	/**
	 * 查询所有的功能信息
	 * 
	 * @return Function 类型的 List
	 */
	public List findAllFunction();

	/**
	 * 根据ID查询功能数据
	 * 
	 * @param ID
	 * @return
	 */
	public Function findFunctionByID(long ID);

	/**
	 * 根据id获取相关子节点
	 * 
	 * @param entity
	 * @return
	 */
	public List getChildNodeByID(Long id);

	/**
	 * 保存或更新对象
	 * @param entity
	 */
	public void saveOrUpdate(Function entity);

	/**
	 * 删除对象
	 * @param entity
	 */
	public void deleteFunction(Function entity);

}
