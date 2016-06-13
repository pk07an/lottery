package com.npc.lottery.sysmge.logic.interf;

import java.util.ArrayList;
import java.util.List;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.sysmge.entity.ParamValue;

/**
 *参数值表 业务处理类接口
 *
 *
 */
public interface IParamValueLogic {

	/**
	 * 查询满足指定查询条件的数据记录
	 * 
	 * @param conditionData
	 *            查询条件信息
	 * @param pageCurrentNo 第一页为 1
	 *            需要查询的页码
	 * @param pageSize
	 *            页面大小
	 * @return  ParamValue 类型的 ArrayList
	 */
	public ArrayList<ParamValue> findParamValueList(
			ConditionData conditionData, int pageCurrentNo, int pageSize);

	/**
	 * 统计满足指定查询条件的记录数目
	 * 
	 * @param conditionData
	 *            查询条件信息     
	 * @return
	 */
	public long findAmountParamValue(ConditionData conditionData);

	/**
	 * 根据ID查询数据
	 * 
	 * @return
	 */
	public ParamValue getParamValueByID(long ID);

	/**
	 * 根据父节点ID查询，此方法返回的数据未排序
	 * 
	 * @param id
	 */
	public List getParamValueByParentParamID(Long id);

	/**
	 * 保存或更新对象
	 * @param entity
	 */
	public void saveOrUpdate(ParamValue entity);

	/**
	 * 删除对象
	 * @param entity
	 */
	public void delete(ParamValue entity);
}
