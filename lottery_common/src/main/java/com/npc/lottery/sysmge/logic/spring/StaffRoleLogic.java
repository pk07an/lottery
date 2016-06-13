package com.npc.lottery.sysmge.logic.spring;

import java.util.ArrayList;
import java.util.HashMap;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.sysmge.dao.interf.IStaffRoleDao;
import com.npc.lottery.sysmge.entity.Roles;
import com.npc.lottery.sysmge.entity.StaffRole;
import com.npc.lottery.sysmge.logic.interf.IStaffRoleLogic;
import com.npc.lottery.util.Tool;

/**
 * 用户所拥有的角色逻辑处理类
 *
 * @author none
 *
 */
public class StaffRoleLogic implements IStaffRoleLogic {

    private IStaffRoleDao staffRoleDao = null;

    public void setStaffRoleDao(IStaffRoleDao staffRoleDao) {
        this.staffRoleDao = staffRoleDao;
    }

    /**
     * 根据用户ID和角色ID，查询对应的数据记录
     * 
     * @param userID	用户ID   
     * @param userType  用户类型
     * @param roleID    角色ID
     * 
     * @return  不存在则返回 null
     */
    public StaffRole findStaffRole(Long userID, String userType, Long roleID) {

        StaffRole entity = null;

        ConditionData conditionData = new ConditionData();
        conditionData.addEqual("userID", userID);
        conditionData.addEqual("roleID.ID", roleID);

        ArrayList<StaffRole> tempList = this.findStaffRoleList(conditionData,
                1, 100);
        if (null != tempList && tempList.size() > 0) {
            entity = tempList.get(0);
        }

        return entity;
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
     * @return  
     */
    public ArrayList<StaffRole> findStaffRoleList(ConditionData conditionData,
            int pageCurrentNo, int pageSize) {
        ArrayList<StaffRole> resultList = null;

        // 根据页码和页面大小，获得需要查询的开始数据和数量
        int firstResult = (pageCurrentNo - 1) * pageSize;
        int maxResults = pageSize;

        // 查询数据
        try {
            resultList = (ArrayList<StaffRole>) staffRoleDao.findStaffRoleList(
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
    public long findAmountStaffRoleList(ConditionData conditionData) {
        long amount = 0;

        try {
            amount = staffRoleDao.findAmountDemoList(conditionData);
        } catch (Exception ex) {
            Tool.printExceptionStack(ex);
        }

        return amount;
    }

    /**
     * 保存信息
     * 
     * @param entity    待保存的信息
     * @return  此信息记录所对应的ID，Long类型
     */
    public Long saveStaffRole(StaffRole entity) {

        Long result = null;

        result = staffRoleDao.saveStaffRole(entity);

        return result;
    }

    /**
     * 删除信息
     * 
     * @param entity
     */
    public void delete(StaffRole entity) {
        staffRoleDao.delete(entity);
    }

    /**
     * 根据用户ID和角色ID，删除对应的数据记录
     * 
     * @param userID    用户ID
     * @param userType  用户类型
     * @param roleID    角色ID
     */
    public void delete(Long userID, String userType, Long roleID) {
        //定位待查询的数据
        StaffRole entity = this.findStaffRole(userID, userType, roleID);

        if (null != entity) {
            this.delete(entity);
        }
    }

    /**
     * 根据用户ID，查询对应的功能角色授权信息
     * 
     * @param userID
     * @return
     */
    //	public ArrayList<StaffRole> findFuncRoleByUserID(Long userID) {
    //
    //		if (null == userID) {
    //			return null;
    //		}
    //
    //		ArrayList<StaffRole> result = (ArrayList<StaffRole>) staffRoleDao
    //				.findFuncRoleByUserID(userID);
    //
    //		return result;
    //	}

    /**
     * 根据用户ID及用户类型查询对应的授权信息
     * 
     * @param userID        用户ID
     * @param userType      用户类型
     * @return
     */
    public ArrayList<StaffRole> findFuncRole(Long userID, String userType) {

        ConditionData conditionData = new ConditionData();
        conditionData.addEqual2("userID", userID);
        conditionData.addEqual2("userType", userType);
        ArrayList<StaffRole> result = (ArrayList<StaffRole>) this
                .findStaffRoleList(conditionData, 1, 999);

        return result;
    }

    /**
     * 查询子账号对应的授权信息
     * 
     * @param userID        用户ID
     * @param userType      用户类型
     * @return
     */
    public ArrayList<String> findSubRole(Long userID, String userType) {

        ConditionData conditionData = new ConditionData();
        conditionData.addEqual2("userID", userID);
        conditionData.addEqual2("userType", userType);
        ArrayList<StaffRole> staffRoleList = (ArrayList<StaffRole>) this
                .findStaffRoleList(conditionData, 1, 999);

        ArrayList<String> roleCodeList = new ArrayList<String>();

        Roles role = null;
        if (null != staffRoleList && staffRoleList.size() > 0) {
            for (int i = 0; i < staffRoleList.size(); i++) {
                role = ((StaffRole) staffRoleList.get(i)).getRoleID();
                if (null != role
                        && Roles.ROLE_TYPE_INIT.equalsIgnoreCase(role
                                .getRoleType())) {
                    //只过滤出角色类型为内置角色的数据
                    roleCodeList.add(role.getRoleCode());
                }
            }
        }

        return roleCodeList;
    }

    /**
     * 更新用户的角色授权信息
     * 
     * @param userID			用户ID
     * @param userType          用户类型
     * @param newRoleIDList		角色新的功能权限ID列表
     * @param originRoleIDList	角色原来的功能权限ID列表
     * @return
     * 			key = create	value中存放新增的功能权限数目，Long类型
     * 			key = delete	value中存放删除的功能权限数目，Long类型
     */
    public HashMap updateStaffRole(Long userID, String userType,
            ArrayList<Long> newRoleIDList, ArrayList<Long> originRoleIDList) {
        HashMap resultMap = new HashMap();
        long createNum = 0;
        long deleteNum = 0;

        ArrayList<Long> createList = new ArrayList<Long>();
        ArrayList<Long> deleteList = new ArrayList<Long>();

        boolean isEqual = false;
        for (int outer = 0; outer < newRoleIDList.size(); outer++) {
            isEqual = false;
            for (int inner = 0; inner < originRoleIDList.size(); inner++) {
                if (newRoleIDList.get(outer).longValue() == originRoleIDList
                        .get(inner).longValue()) {
                    //如果数据在newList和originList中都存在，则说明这条数据无需变更
                    originRoleIDList.remove(inner);
                    isEqual = true;
                    break;
                }
            }
            if (!isEqual) {
                //如果数据不存在于 originList 中，则说明需要新增此数据
                createList.add(newRoleIDList.get(outer));
            }
        }
        deleteList = originRoleIDList;

        StaffRole entity = null;
        Roles roleEntity = null;
        //根据create列表增加数据
        for (int i = 0; i < createList.size(); i++) {
            roleEntity = new Roles();
            roleEntity.setID(createList.get(i));

            entity = new StaffRole();
            entity.setUserID(userID);
            entity.setRoleID(roleEntity);
            entity.setUserType(userType);
            this.saveStaffRole(entity);
        }

        //删除delete列表中的数据
        for (int i = 0; i < deleteList.size(); i++) {
            //删除数据
            this.delete(userID, userType, deleteList.get(i));
        }

        resultMap.put("create", createList.size());
        resultMap.put("delete", deleteList.size());
        return resultMap;
    }

    /**
     * 更新用户的角色授权信息
     * 
     * @param userID			用户ID
     * @param userType          用户类型
     * @param newRoleIDList		用户新的角色权限ID列表
     * @return
     * 			key = create	value中存放新增的角色数目，Long类型
     * 			key = delete	value中存放删除的角色数目，Long类型
     */
    public HashMap updateStaffRole(Long userID, String userType,
            ArrayList<Long> newRoleIDList) {

        //查询角色ID对应的当前功能权限列表
        ArrayList<StaffRole> originList = this.findFuncRole(userID, userType);
        ArrayList<Long> originRoleIDList = new ArrayList<Long>();
        for (int i = 0; i < originList.size(); i++) {
            originRoleIDList.add(originList.get(i).getRoleID().getID());
        }

        return this.updateStaffRole(userID, userType, newRoleIDList,
                originRoleIDList);
    }

}
