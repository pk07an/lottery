package com.npc.lottery.sysmge.logic.spring;

import java.util.ArrayList;
import java.util.List;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.sysmge.dao.interf.IOrgDao;
import com.npc.lottery.sysmge.entity.Org;
import com.npc.lottery.sysmge.logic.interf.IOrgExtLogic;
import com.npc.lottery.sysmge.logic.interf.IOrgLogic;
import com.npc.lottery.util.Tool;

/**
 * 组织机构的业务处理类
 *
 * @author none
 *
 */
public class OrgLogic implements IOrgLogic {
	private IOrgDao orgDao = null;//数据库处理类

	private IOrgExtLogic orgExtLogic = null;//机构扩展数据逻辑处理类

	public void setOrgDao(IOrgDao orgDao) {
		this.orgDao = orgDao;
	}

	public void setOrgExtLogic(IOrgExtLogic orgExtLogic) {
		this.orgExtLogic = orgExtLogic;
	}
	
	public IOrgDao getOrgDao() {
        return orgDao;
    }

    public IOrgExtLogic getOrgExtLogic() {
        return orgExtLogic;
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
	 * @return  Org 类型的 ArrayList
	 */
	public ArrayList<Org> findOrgList(ConditionData conditionData,
			int pageCurrentNo, int pageSize) {
		ArrayList<Org> resultList = null;

		//增加机构显示顺序的排序条件
		if (null == conditionData) {
			conditionData = new ConditionData();
		}
		conditionData.addOrder("showOrder", ConditionData.ORDER_TYPE_ASC);

		// 根据页码和页面大小，获得需要查询的开始数据和数量
		int firstResult = (pageCurrentNo - 1) * pageSize;
		int maxResults = pageSize;

		// 查询数据
		try {
			resultList = (ArrayList<Org>) orgDao.findOrgList(conditionData,
					firstResult, maxResults);
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
	public long findAmountOrgList(ConditionData conditionData) {
		long amount = 0;

		try {
			amount = orgDao.findAmountOrgList(conditionData);
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
	public Org findOrgByID(long ID) {
		Org entity = null;

		ConditionData conditionData = new ConditionData();

		conditionData.addEqual("orgID", new Long(ID));//增加ID的查询条件

		List resultList = this.findOrgList(conditionData, 1, 999);

		if (null != resultList && 0 < resultList.size()) {
			entity = (Org) resultList.get(0);
		}

		return entity;
	}

	/**
	 * 根据ID查询数据
	 * 
	 * @deprecated 此方法即将被删除，请使用 findOrgByID 方法
	 * @param ID
	 * @return
	 */
	public Org findOrgExtByID(long ID) {
		return this.findOrgByID(ID);
	}

	/**
	 * 查询所有的机构信息
	 * 
	 * @return Org 类型的 List
	 */
	public ArrayList<Org> findAllOrg() {
		//TODO 暂时取1000数据，以后根据实际情况调整
		ConditionData conditionData = new ConditionData();
		conditionData.addOrder("orgID", ConditionData.ORDER_TYPE_ASC);
		conditionData.addOrder("orgType", ConditionData.ORDER_TYPE_ASC);

		return this.findOrgList(conditionData, 1, 1000);
	}

	/**
	 * 查询指定 orgType 所对应的营销机构数据（包含扩展信息）
	 * 
	 * @param orgType 的取值参考 com.npc.lottery.sysmge.entity.Org 中关于ORG_TYPE的常量定义
	 * @return
	 */
	public ArrayList<Org> findSaleOrgList(Long orgType) {
		return orgDao.findSaleOrgList(orgType);
	}
}
