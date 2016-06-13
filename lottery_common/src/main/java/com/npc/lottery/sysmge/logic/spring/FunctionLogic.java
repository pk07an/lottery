package com.npc.lottery.sysmge.logic.spring;

import java.util.List;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.sysmge.dao.interf.IFunctionDao;
import com.npc.lottery.sysmge.entity.Function;
import com.npc.lottery.sysmge.logic.interf.IFunctionLogic;
import com.npc.lottery.util.Tool;

/**
 * 功能逻辑处理类
 *
 * @author none
 *
 */
public class FunctionLogic implements IFunctionLogic {

	private IFunctionDao functionDao = null;

	public void setFunctionDao(IFunctionDao functionDao) {
		this.functionDao = functionDao;
	}

	public IFunctionDao getFunctionDao() {
		return functionDao;
	}

	/**
	 * 查询满足指定查询条件的功能信息数据记录
	 * 
	 * @param conditionData
	 *            查询条件信息
	 * @param pageCurrentNo 第一页为 1
	 *            需要查询的页码
	 * @param pageSize
	 *            页面大小
	 * @return  Function 类型的 List
	 */
	public List findFunctionList(ConditionData conditionData,
			int pageCurrentNo, int pageSize) {
		List resultList = null;

		// 根据页码和页面大小，获得需要查询的开始数据和数量
		int firstResult = (pageCurrentNo - 1) * pageSize;
		int maxResults = pageSize;

		// 查询数据
		try {
			resultList = functionDao.findFunctionList(conditionData,
					firstResult, maxResults);
		} catch (Exception ex) {
			Tool.printExceptionStack(ex);
		}

		return resultList;
	}

	/**
	 * 根据ID查询功能数据
	 * 
	 * @param ID
	 * @return
	 */
	public Function findFunctionByID(long ID) {

		Function entity = null;

		ConditionData conditionData = new ConditionData();

		conditionData.addEqual("ID", new Long(ID));//增加ID的查询条件

		List resultList = this.findFunctionList(conditionData, 1, 999);

		if (null != resultList && 0 < resultList.size()) {
			entity = (Function) resultList.get(0);
		}

		return entity;
	}

	/**
	 * 查询所有的功能信息
	 * 
	 * @return Function 类型的 List
	 */
	public List findAllFunction() {
	    
	    ConditionData conditionData = new ConditionData();
	    //增加排序条件
	    conditionData.addOrder("sortNum", ConditionData.ORDER_TYPE_ASC);
	    
		return this.findFunctionList(conditionData, 1, 999999);
	}

	/**
	 * 根据id获取其子节点
	 */
	public List getChildNodeByID(Long id) {
		return functionDao.getChildNodeByID(id);
	}

	/**
	 * 保存或更新对象
	 * @param entity
	 */
	public void saveOrUpdate(Function entity) {
		functionDao.saveOrUpdate(entity);
	}

	/**
	 * 删除对象
	 * @param entity
	 */
	public void deleteFunction(Function entity) {
		functionDao.deleteFunction(entity);
	}

}
