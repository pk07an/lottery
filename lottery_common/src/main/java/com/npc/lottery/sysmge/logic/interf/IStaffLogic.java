package com.npc.lottery.sysmge.logic.interf;

import java.util.List;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.sysmge.entity.Staff;

/**
 * 人员信息的逻辑处理类
 * 
 * @author none
 * 
 */
public interface IStaffLogic {

	/**
	 * 统计满足指定查询条件的人员信息记录数目
	 * 
	 * @param conditionData
	 *            查询条件信息        
	 * @return
	 */
	public long findAmountStaffList(ConditionData conditionData);

	/**
	 * 查询满足指定查询条件的人员信息数据记录
	 * 
	 * @param conditionData
	 *            查询条件信息        
	 * @param pageCurrentNo
	 *            第一页为 1 需要查询的页码
	 * @param pageSize
	 *            页面大小
	 * @return Staff 类型的 List
	 */
	public List findStaffList(ConditionData conditionData, int pageCurrentNo,
			int pageSize);

	/**
	 * 根据ID查询人员信息
	 * 
	 * @param userID
	 *            人员信息ID
	 * @return
	 */
	public Staff findStaffByID(Long userID);

	/**
	 * 保存信息
	 * 
	 * @param entity    待保存的信息
	 * @return  此信息记录所对应的ID，Long类型
	 */
	public Long saveStaff(Staff entity);

	/**
	 * 更新信息
	 * 
	 * @param entity 待更新的信息
	 */
	public void update(Staff entity);
}
