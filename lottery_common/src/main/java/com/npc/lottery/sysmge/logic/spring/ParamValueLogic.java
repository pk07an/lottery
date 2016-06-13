package com.npc.lottery.sysmge.logic.spring;

import java.util.ArrayList;
import java.util.List;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.sysmge.dao.interf.IParamValueDao;
import com.npc.lottery.sysmge.entity.ParamValue;
import com.npc.lottery.sysmge.logic.interf.IParamValueLogic;
import com.npc.lottery.util.Tool;

/**
 * 功能逻辑处理类
 *
 * @author none
 *
 */
public class ParamValueLogic implements IParamValueLogic {

	private IParamValueDao paramValueDao = null;

	public void setParamValueDao(IParamValueDao paramValueDao) {
		this.paramValueDao = paramValueDao;
	}

	public IParamValueDao getParamValueDao() {
		return paramValueDao;
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
	 * @return  ParamValue 类型的 List
	 */
	public ArrayList<ParamValue> findParamValueList(
			ConditionData conditionData, int pageCurrentNo, int pageSize) {
		ArrayList<ParamValue> resultList = null;

		// 根据页码和页面大小，获得需要查询的开始数据和数量
		int firstResult = (pageCurrentNo - 1) * pageSize;
		int maxResults = pageSize;

		//增加排序条件
		if (null == conditionData) {
			conditionData = new ConditionData();
		}
		conditionData.addOrder("sortNum", ConditionData.ORDER_TYPE_ASC);

		// 查询数据
		try {
			resultList = (ArrayList<ParamValue>) paramValueDao
					.findParamValueList(conditionData, firstResult, maxResults);
		} catch (Exception ex) {
			Tool.printExceptionStack(ex);
		}

		return resultList;
	}

	/**
	 * 统计满足指定查询条件的Param记录数目
	 * 
	 * @param conditionData
	 *            查询条件信息        
	 * @return
	 */
	public long findAmountParamValue(ConditionData conditionData) {
		long amount = 0;

		try {
			amount = paramValueDao.findAmountParamValue(conditionData);
		} catch (Exception ex) {
			Tool.printExceptionStack(ex);
		}

		return amount;
	}

	/**
	 * 根据ID查询功能数据
	 * 
	 * @param ID
	 * @return
	 */
	public ParamValue getParamValueByID(long ID) {

		return paramValueDao.getParamValueByID(ID);
	}

	/**
	 * 根据父节点ID查询，此方法返回的数据未排序
	 * 
	 * @param id
	 */
	public List getParamValueByParentParamID(Long id) {
		return paramValueDao.getParamValueByParentParamID(id);
	}

	/**
	 * 保存或更新对象
	 * @param entity
	 */
	public void saveOrUpdate(ParamValue entity) {
		paramValueDao.saveOrUpdate(entity);
	}

	/**
	 * 删除对象
	 * @param entity
	 */
	public void delete(ParamValue entity) {
		paramValueDao.delete(entity);
	}

}
