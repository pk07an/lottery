package com.npc.lottery.sysmge.logic.spring;

import java.util.List;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.sysmge.dao.interf.IManagerStaffDao;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.logic.interf.IManagerStaffLogic;
import com.npc.lottery.util.Tool;

/**
 * 基础管理用户所对应的逻辑实现类
 * 
 * @author none
 *
 */
public class ManagerStaffLogic implements IManagerStaffLogic {

    private IManagerStaffDao managerStaffDao;

    public void setManagerStaffDao(IManagerStaffDao managerStaffDao) {
        this.managerStaffDao = managerStaffDao;
    }

    /**
     * 统计满足指定查询条件的人员信息记录数目
     * 
     * @param conditionData
     *            查询条件信息     
     * @return
     */
    public long findAmountManagerStaffList(ConditionData conditionData) {
        long amount = 0;

        try {
            amount = managerStaffDao.findAmountManagerStaffList(conditionData);
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
     * @return ManagerStaff 类型的 List
     */
    public List findManagerStaffList(ConditionData conditionData,
            int pageCurrentNo, int pageSize) {

        List resultList = null;

        // 根据页码和页面大小，获得需要查询的开始数据和数量
        int firstResult = (pageCurrentNo - 1) * pageSize;
        int maxResults = pageSize;

        // 查询数据
        try {
            resultList = managerStaffDao.findManagerStaffList(conditionData,
                    firstResult, maxResults);
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
    public ManagerStaff findManagerStaffByID(Long userID) {

        ManagerStaff entity = null;

        ConditionData conditionData = new ConditionData();

        conditionData.addEqual("ID", userID);

        List resultList = this.findManagerStaffList(conditionData, 1, 99999);

        if (null != resultList && resultList.size() > 0) {
            entity = (ManagerStaff) resultList.get(0);
        }

        return entity;
    }
    
    /**
     * 根据登陆账号查询人员信息
     * 
     * @param account
     *            登陆账号
     * @return
     */
    public ManagerStaff findManagerStaffByAccount(String account) {

        ManagerStaff entity = null;

        ConditionData conditionData = new ConditionData();

        //modify by peter
        conditionData.addEqual("account", account);

        List resultList = this.findManagerStaffList(conditionData, 1, 99999);

        if (null != resultList && resultList.size() > 0) {
            entity = (ManagerStaff) resultList.get(0);
        }

        return entity;
    }
    
    /**
     * 根据中文名查询人员信息
     * 
     * @param account
     *            登陆账号
     * @return
     */
    public ManagerStaff findManagerStaffByChsName(String chsName) {

        ManagerStaff entity = null;

        ConditionData conditionData = new ConditionData();

        conditionData.addEqual("chsName", chsName);

        List resultList = this.findManagerStaffList(conditionData, 1, 99999);

        if (null != resultList && resultList.size() > 0) {
            entity = (ManagerStaff) resultList.get(0);
        }

        return entity;
    }


    /**
     * 保存信息
     * 
     * @param entity    待保存的信息
     * @return  此信息记录所对应的ID，Long类型
     */
    public Long saveManagerStaff(ManagerStaff entity) {

        Long result = null;

        result = managerStaffDao.saveManagerStaff(entity);

        return result;
    }

    /**
     * 更新信息
     * 
     * @param entity 待更新的信息
     */
    public void updateManagerStaff(ManagerStaff entity) {
        managerStaffDao.updateManagerStaff(entity);
    }

    /**
     * 删除对象
     * @param entity
     */
    public void delManagerStaff(ManagerStaff entity) {
        managerStaffDao.delManagerStaff(entity);
    }

	@Override
	public ManagerStaff queryManagerStaffByAccount(String account) {
		
		return managerStaffDao.findManagerStaffByAccount(account);
	}

}
