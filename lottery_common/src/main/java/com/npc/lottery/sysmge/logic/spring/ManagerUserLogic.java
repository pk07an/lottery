package com.npc.lottery.sysmge.logic.spring;

import java.util.ArrayList;
import java.util.List;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.sysmge.logic.interf.IManagerStaffLogic;
import com.npc.lottery.sysmge.logic.interf.IManagerUserLogic;

/**
 * 用户信息的逻辑处理类
 * 
 * 此类涉及系统用户相关的业务逻辑，包括人员信息（ManagerStaff）、人员权限等
 * 
 * @author none
 *
 */
public class ManagerUserLogic implements IManagerUserLogic {

    private IManagerStaffLogic managerStaffLogic;//人员信息逻辑处理类

    public void setManagerStaffLogic(IManagerStaffLogic managerStaffLogic) {
        this.managerStaffLogic = managerStaffLogic;
    }

    /**
     * 根据ID查询人员信息
     * 
     * @param userID
     *            人员信息ID
     * @return  数据不存在则返回 null
     */
    public ManagerUser findManagerUserByID(Long userID) {
        ManagerStaff entity = managerStaffLogic.findManagerStaffByID(userID);//查询 staff

        if (null == entity) {
            return null;
        }

        ManagerUser user = new ManagerUser(entity);//根据 ManagerStaff 构造 user

        return user;
    }

    /**
     * 根据用户名查询用户信息
     * 
     * @param userName  用户名
     * @return  数据不存在则返回 null
     */
    public ManagerUser findManagerUserByName(String userName) {

        //判断查询条件是否为空
        if (null == userName) {
            return null;
        }

        ConditionData conditionData = new ConditionData();
        conditionData.addEqual("account", userName);

        List<ManagerUser> userList = this.findManagerUserList(conditionData, 1,
                10);
        if (null == userList || 0 == userList.size()) {
            return null;
        }

        return userList.get(0);
    }

    /**
     * 统计满足指定查询条件的人员信息记录数目
     * 
     * @param conditionData
     *            查询条件信息       
     * @return
     */
    public long findAmountManagerUserList(ConditionData conditionData) {
        return managerStaffLogic.findAmountManagerStaffList(conditionData);
    }

    /**
     * 保存信息
     * 
     * @param entity    待保存的信息
     * @return  此信息记录所对应的ID，Long类型
     */
    public Long saveManagerStaff(ManagerStaff entity) {
        return managerStaffLogic.saveManagerStaff(entity);
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
     * @return ManagerUser 类型的 List
     */
    public List<ManagerUser> findManagerUserList(ConditionData conditionData,
            int pageCurrentNo, int pageSize) {
        List<ManagerUser> resultList = null;

        //增加人员显示顺序的排序条件
        if (null == conditionData) {
            conditionData = new ConditionData();
        }
        conditionData.addOrder("ID", ConditionData.ORDER_TYPE_ASC);
        //调用staffLogic方法
        List staffList = managerStaffLogic.findManagerStaffList(conditionData,
                pageCurrentNo, pageSize);

        if (null != staffList) {
            resultList = new ArrayList<ManagerUser>(staffList.size());
            //将 staffList 中的数据填充到结果信息列表中
            for (int i = 0; i < staffList.size(); i++) {
                ManagerStaff staff = (ManagerStaff) staffList.get(i);
                String userOrgName = null;
                resultList.add(new ManagerUser(staff));
            }
        }

        return resultList;
    }

    /**
     * 更新信息
     * 
     * @param entity 待更新的信息
     */
    public void update(ManagerStaff entity) {
        managerStaffLogic.updateManagerStaff(entity);
    }

	@Override
	public ManagerUser queryManagerUserByName(String userName) {
		ManagerStaff staff = managerStaffLogic.queryManagerStaffByAccount(userName);
		ManagerUser user = new ManagerUser(staff);
		return user;
	}
}
