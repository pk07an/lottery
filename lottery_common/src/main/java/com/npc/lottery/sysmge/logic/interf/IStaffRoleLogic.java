package com.npc.lottery.sysmge.logic.interf;

import java.util.ArrayList;
import java.util.HashMap;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.sysmge.entity.StaffRole;

/**
 * 用户所拥有的角色逻辑处理类接口
 *
 * @author none
 *
 */
public interface IStaffRoleLogic {
    /**
     * 根据用户ID和角色ID，查询对应的数据记录
     * 
     * @param userID	用户ID  
     * @param userType  用户类型
     * @param roleID    角色ID
     * 
     * @return  不存在则返回 null
     */
    public StaffRole findStaffRole(Long userID, String userType, Long roleID);

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
            int pageCurrentNo, int pageSize);

    /**
     * 统计满足指定查询条件的记录数目
     * 
     * @param conditionData
     *            查询条件信息     
     * @return
     */
    public long findAmountStaffRoleList(ConditionData conditionData);

    /**
     * 保存信息
     * 
     * @param entity    待保存的信息
     * @return  此信息记录所对应的ID，Long类型
     */
    public Long saveStaffRole(StaffRole entity);

    /**
     * 删除信息
     * 
     * @param entity
     */
    public void delete(StaffRole entity);

    /**
     * 根据用户ID和角色ID，删除对应的数据记录
     * @param userType  用户类型
     * @param userID    用户ID
     * @param roleID    角色ID
     */
    public void delete(Long userID, String userType, Long roleID);

    /**
     * 根据用户ID，查询对应的功能权限角色授权信息
     * 
     * @param userID
     * @return
     */
    //public ArrayList<StaffRole> findFuncRoleByUserID(Long userID);

    /**
     * 根据用户ID及用户类型查询对应的授权信息
     * 
     * @param userID        用户ID
     * @param userType      用户类型
     * @return
     */
    public ArrayList<StaffRole> findFuncRole(Long userID, String userType);

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
            ArrayList<Long> newRoleIDList, ArrayList<Long> originRoleIDList);

    /**
     * 更新用户的角色授权信息
     * 
     * @param userID            用户ID
     * @param userType          用户类型
     * @param newRoleIDList     用户新的角色权限ID列表
     * @return
     *          key = create    value中存放新增的角色数目，Long类型
     *          key = delete    value中存放删除的角色数目，Long类型
     */
    public HashMap updateStaffRole(Long userID, String userType,
            ArrayList<Long> newRoleIDList);
    
    /**
     * 查询子账号对应的授权信息
     * 
     * @param userID        用户ID
     * @param userType      用户类型
     * @return
     */
    public ArrayList<String> findSubRole(Long userID, String userType);

}
