package com.npc.lottery.sysmge.logic.interf;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.sysmge.entity.Param;
import com.npc.lottery.sysmge.entity.ParamValue;

/**
 * 参数类别表 业务处理类接口
 *
 */
public interface IParamLogic {

	/**
	 * 查询满足指定查询条件的数据记录
	 * 
	 * @param conditionData
	 *            查询条件信息
	 * @param pageCurrentNo 第一页为 1
	 *            需要查询的页码
	 * @param pageSize
	 *            页面大小
	 * @return  Param 类型的 ArrayList
	 */
	public List findParamList(ConditionData conditionData, int pageCurrentNo,
			int pageSize);

	/**
	 * 根据编码，查询对应的参数值
	 * 
	 * @param code
	 * @return  不存在数据则返回null
	 */
	public ArrayList<ParamValue> findParamValueByCode(String code);

	/**
	 * 统计满足指定查询条件的记录数目
	 * 
	 * @param conditionData
	 *            查询条件信息     
	 * @return
	 */
	public long findAmountParam(ConditionData conditionData);

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public Param getParamByID(Long id);

	/**
	 * 保存或更新对象
	 * @param entity
	 */
	public void saveOrUpdate(Param entity);

	/**
	 * 删除对象
	 * @param entity
	 */
	public void delete(Param entity);

	/**
	 * 根据tb_frame_param的任意字段取tb_frame_param_value的记录
	 * @param code
	 */
	public LinkedHashMap<String, String> getParamValue(String fieldName,
			String fieldValue);
}
