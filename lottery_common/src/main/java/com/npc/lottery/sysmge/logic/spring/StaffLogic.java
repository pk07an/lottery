package com.npc.lottery.sysmge.logic.spring;

import java.util.List;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.sysmge.dao.interf.IStaffDao;
import com.npc.lottery.sysmge.entity.Staff;
import com.npc.lottery.sysmge.logic.interf.IStaffLogic;
import com.npc.lottery.util.Tool;

/**
 * 人员信息的逻辑处理类
 * 
 * @author none
 * 
 */
public class StaffLogic implements IStaffLogic {

	private IStaffDao staffDao = null;

	public void setStaffDao(IStaffDao staffDao) {
		this.staffDao = staffDao;
	}


	/**
	 * 统计满足指定查询条件的人员信息记录数目
	 * 
	 * @param conditionData
	 *            查询条件信息     
	 * @return
	 */
	public long findAmountStaffList(ConditionData conditionData) {
		long amount = 0;

		try {
			amount = staffDao.findAmountStaffList(conditionData);
		} catch (Exception ex) {
			Tool.printExceptionStack(ex);
		}

		return amount;
	}

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
			int pageSize) {

		List resultList = null;

		// 根据页码和页面大小，获得需要查询的开始数据和数量
		int firstResult = (pageCurrentNo - 1) * pageSize;
		int maxResults = pageSize;

		// 查询数据
		try {
			resultList = staffDao.findStaffList(conditionData, firstResult,
					maxResults);
		} catch (Exception ex) {
			Tool.printExceptionStack(ex);
		}

		return resultList;
	}

	/**
	 * 根据ID查询人员信息
	 * 
	 * @param userID
	 *            人员信息ID
	 * @return
	 */
	public Staff findStaffByID(Long userID) {

		Staff entity = null;

		ConditionData conditionData = new ConditionData();

		conditionData.addEqual("ID", userID);

		List resultList = this.findStaffList(conditionData, 1, 99999);

		if (null != resultList && resultList.size() > 0) {
			entity = (Staff) resultList.get(0);
		}

		return entity;
	}

	/**
	 * 保存信息
	 * 
	 * @param entity    待保存的信息
	 * @return  此信息记录所对应的ID，Long类型
	 */
	public Long saveStaff(Staff entity) {

		Long result = null;

		result = staffDao.saveStaff(entity);

		return result;
	}

	/**
	 * 更新信息
	 * 
	 * @param entity 待更新的信息
	 */
	public void update(Staff entity) {
		staffDao.update(entity);
	}

}
