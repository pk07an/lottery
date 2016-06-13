package com.npc.lottery.sysmge.logic.interf;

import java.util.ArrayList;
import java.util.HashMap;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.sysmge.entity.Function;
import com.npc.lottery.sysmge.entity.Roles;

/**
 * 授权逻辑类接口
 *
 * @author none
 *
 */
public interface IAuthorizLogic {

    /**
     * 查询所有的功能信息
     * 
     * @return 
     */
    public ArrayList<Function> findAllFunction();

    /**
     * 更新角色的授权信息
     * 
     * @param roleID		角色ID
     * @param newFuncIDList		角色新的功能权限ID列表
     * @param originFuncIDList	角色原来的功能权限ID列表
     * @return
     * 			key = create	value中存放新增的功能权限数目，Long类型
     * 			key = delete	value中存放删除的功能权限数目，Long类型
     */
    public HashMap updateRoleAuthoriz(Long roleID,
            ArrayList<Long> newFuncIDList, ArrayList<Long> originFuncIDList);

    /**
     * 更新角色的授权信息
     * 
     * @param roleID		    角色ID
     * @param newFuncIDList		角色新的功能权限ID列表
     * @return
     * 			key = create	value中存放新增的功能权限数目，Long类型
     * 			key = delete	value中存放删除的功能权限数目，Long类型
     */
    public HashMap updateRoleAuthoriz(Long roleID, ArrayList<Long> newFuncIDList);

    /**
     * 更新用户的私有功能授权信息
     * 
     * @param userID            用户ID
     * @param userType          用户类型
     * @param createFuncIDList  增加的功能权限ID列表
     * @param deleteFuncIDList  删除的功能权限ID列表
     * 
     * @return
     *          key = create    value中存放新增的功能权限数目，Long类型
     *          key = delete    value中存放删除的功能权限数目，Long类型
     */
    public HashMap updateUserPrivateFuncAuthoriz(Long userID, String userType,
            ArrayList<Long> createFuncIDList, ArrayList<Long> deleteFuncIDList);

    /**
     * 更新角色的授权信息
     * 
     * @param roleID            角色ID
     * @param createFuncIDList  增加的功能权限ID列表
     * @param deleteFuncIDList  删除的功能权限ID列表
     * 
     * @return
     *          key = create    value中存放新增的功能权限数目，Long类型
     *          key = delete    value中存放删除的功能权限数目，Long类型
     */
    public HashMap updateRoleAuthoriz2(Long roleID,
            ArrayList<Long> createFuncIDList, ArrayList<Long> deleteFuncIDList);

    /**
     * 查询指定角色用来授权的功能信息（即授权页面中所显示的可用来授权的功能列表）
     * 此功能信息中包含了已经拥有的授权信息
     * 
     * @param roleID    角色ID
     * @return
     */
    public ArrayList<Function> findAllFunction(Long roleID);

    /**
     * 查询角色已授予的功能列表（包含功能对应的父节点信息）
     * 
     * @param roleID        角色ID
     * @return  不存在授权信息则返回 null
     */
    public ArrayList<Function> findRoleAuthorizFunc(Long roleID);

    /**
     * 查询用户已授予的功能列表（包含功能对应的父节点信息）
     * 返回的功能对象实体中相关信息如下：
     * isLeaf = true，则表示节点是叶子节点
     * isAuthoriz = true，则表示节点已经通过角色授权方式授予给了用户
     * isPrivateAuthoriz = true，则表示节点已经通过私有授权方式授予给了用户
     * 
     * @param userID
     * @param userType  用户类型
     * @return
     */
    public ArrayList<Function> findUserAuthorizFunc(Long userID, String userType);

    /**
     * 更新用户的角色授权信息
     * 
     * @param userID            用户ID
     * @param userType          用户类型
     * @param newRoleIDList     角色新的功能权限ID列表
     * @param originRoleIDList  角色原来的功能权限ID列表
     * @return
     *          key = create    value中存放新增的功能权限数目，Long类型
     *          key = delete    value中存放删除的功能权限数目，Long类型
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
     *  根据用户ID，查询所有的可用来对此用户授权角色信息
     * （角色信息中存放了此用户是否已经授予了此角色的标志）
     * 
     * @param userID        用户ID   
     * @param userType      用户类型
     * @param conditionData
     *            查询条件信息
     * @param pageCurrentNo 第一页为 1
     *            需要查询的页码
     * @param pageSize
     *            页面大小
     * @return
     */
    public ArrayList<Roles> findAllRolesByUserID(Long userID, String userType,
            ConditionData conditionData, int pageCurrentNo, int pageSize);

    /**
     * 根据用户ID，查询给指定用户授权的角色列表
     * 
     * @param userID
     * @param userType          用户类型
     * @return
     */
    public ArrayList<Roles> findAuthorizRolesByUserID(Long userID,
            String userType);

    /**
     * 查询可以用来给指定用户授予私有权限的功能列表
     * 数据进行了如下处理：
     * 1、是否是叶节点（isLeaf）
     * 2、是否已授权（从普通角色授权中所继承），这部分数据按非叶子节点数据记录（isLeaf = false）
     * 3、用户的私有授权（isAuthoriz）信息
     * 
     * @param userID
     * @param userType          用户类型
     * @return
     */
    public ArrayList<Function> findUserPrivateAuthorizFunc(Long userID,
            String userType);

    /**
     * 更新用户的角色授权信息
     * 
     * @param userID            用户ID
     * @param userType          用户类型
     * @param newRoleIDList     用户新的角色权限ID列表
     * @return
     *          key = create    value中存放新增的用户角色数目，Long类型
     *          key = delete    value中存放删除的用户角色数目，Long类型
     */
    public HashMap updateSubRole(Long userID, String userType,
            ArrayList<String> newRoleNameList);

    /**
     * 查询子账号对应的授权信息
     * 
     * @param userID        用户ID
     * @param userType      用户类型
     * @return
     */
    public ArrayList<String> findSubRole(Long userID, String userType);
}
