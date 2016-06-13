package com.npc.lottery.sysmge.logic.spring;

import java.util.ArrayList;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.sysmge.dao.interf.IOrgExtDao;
import com.npc.lottery.sysmge.entity.OrgExt;
import com.npc.lottery.sysmge.logic.interf.IOrgExtLogic;
import com.npc.lottery.util.Tool;

/**
 * 组织机构扩展业务处理类
 *
 * @author none
 *
 */
public class OrgExtLogic implements IOrgExtLogic {

	private IOrgExtDao orgExtDao = null;

	public void setOrgExtDao(IOrgExtDao orgExtDao) {
		this.orgExtDao = orgExtDao;
	}
	
	public IOrgExtDao getOrgExtDao() {
        return orgExtDao;
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
	 * @return  OrgExt 类型的 ArrayList
	 */
	public ArrayList<OrgExt> findOrgExtList(ConditionData conditionData,
			int pageCurrentNo, int pageSize) {
		ArrayList<OrgExt> resultList = null;

		//增加机构显示顺序的排序条件
		if (null == conditionData) {
			conditionData = new ConditionData();
		}

		// 根据页码和页面大小，获得需要查询的开始数据和数量
		int firstResult = (pageCurrentNo - 1) * pageSize;
		int maxResults = pageSize;

		// 查询数据
		try {
			resultList = (ArrayList<OrgExt>) orgExtDao.findOrgExtList(
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
	public long findAmountOrgExtList(ConditionData conditionData) {
		long amount = 0;

		try {
			amount = orgExtDao.findAmountOrgExtList(conditionData);
		} catch (Exception ex) {
			Tool.printExceptionStack(ex);
		}

		return amount;
	}

	/**
	 * 根据ID查询数据
	 * 
	 * @return
	 */
	public OrgExt findOrgExtByID(long ID) {
		OrgExt entity = null;

		entity = orgExtDao.getOrgExtByID(ID);

		return entity;
	}

	/**
	 * 查询所有的机构扩展信息
	 * 
	 * @return OrgExt 类型的 List
	 */
	public ArrayList<OrgExt> findAllOrg() {
		//TODO 暂时取1000数据，以后根据实际情况调整
		ConditionData conditionData = new ConditionData();

		return this.findOrgExtList(conditionData, 1, 1000);
	}
}
