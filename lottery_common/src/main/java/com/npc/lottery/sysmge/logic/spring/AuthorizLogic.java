package com.npc.lottery.sysmge.logic.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.common.ConstantBusiness;
import com.npc.lottery.sysmge.entity.Function;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.sysmge.entity.RoleFunc;
import com.npc.lottery.sysmge.entity.Roles;
import com.npc.lottery.sysmge.entity.StaffRole;
import com.npc.lottery.sysmge.logic.interf.IAuthorizLogic;
import com.npc.lottery.sysmge.logic.interf.IFunctionLogic;
import com.npc.lottery.sysmge.logic.interf.IManagerUserLogic;
import com.npc.lottery.sysmge.logic.interf.IRoleFuncLogic;
import com.npc.lottery.sysmge.logic.interf.IRolesLogic;
import com.npc.lottery.sysmge.logic.interf.IStaffRoleLogic;
import com.npc.lottery.user.entity.SubAccountInfo;
import com.npc.lottery.user.logic.interf.ISubAccountInfoLogic;

/**
 * 授权逻辑类
 *
 * @author none
 *
 */
public class AuthorizLogic implements IAuthorizLogic {
    private IFunctionLogic functionLogic;

    private IRoleFuncLogic roleFuncLogic;

    private IStaffRoleLogic staffRoleLogic;

    private IRolesLogic rolesLogic;

    private ISubAccountInfoLogic subAccountInfoLogic;

    private IManagerUserLogic managerUserLogic;

    public void setFunctionLogic(IFunctionLogic functionLogic) {
        this.functionLogic = functionLogic;
    }

    public void setRoleFuncLogic(IRoleFuncLogic roleFuncLogic) {
        this.roleFuncLogic = roleFuncLogic;
    }

    public void setStaffRoleLogic(IStaffRoleLogic staffRoleLogic) {
        this.staffRoleLogic = staffRoleLogic;
    }

    public void setRolesLogic(IRolesLogic rolesLogic) {
        this.rolesLogic = rolesLogic;
    }

    public void setSubAccountInfoLogic(ISubAccountInfoLogic subAccountInfoLogic) {
        this.subAccountInfoLogic = subAccountInfoLogic;
    }

    public void setManagerUserLogic(IManagerUserLogic managerUserLogic) {
        this.managerUserLogic = managerUserLogic;
    }

    /**
     * 查询所有的功能信息
     * 
     * @return
     */
    public ArrayList<Function> findAllFunction() {

        ArrayList<Function> funcList = (ArrayList<Function>) functionLogic
                .findAllFunction();

        //判断是否是叶子节点（没有子节点）
        ArrayList<Long> parentFuncIDList = new ArrayList<Long>();
        for (int i = 0; i < funcList.size(); i++) {
            parentFuncIDList.add(funcList.get(i).getParentFunc().getID());
        }
        for (int i = 0; i < funcList.size(); i++) {
            if (parentFuncIDList.contains(funcList.get(i).getID())) {
                funcList.get(i).setLeaf(false);
            } else {
                funcList.get(i).setLeaf(true);
            }
        }

        return funcList;
    }

    /**
     * 查询指定角色用来授权的功能信息（即授权页面中所显示的可用来授权的功能列表）
     * 此功能信息中包含了已经拥有的授权信息
     * 
     * @param roleID    角色ID
     * @return
     */
    public ArrayList<Function> findAllFunction(Long roleID) {
        //1、查询所有的功能信息列表
        ArrayList<Function> funcList = this.findAllFunction();

        //2、查询角色授权信息
        ArrayList<RoleFunc> roleFuncList = this.findRoleAuthoriz(roleID);
        if (null == roleFuncList || 0 == roleFuncList.size()) {
            return funcList;
        }

        ArrayList<Long> roleFuncIDList = new ArrayList<Long>();
        for (int i = 0; i < roleFuncList.size(); i++) {
            roleFuncIDList.add(roleFuncList.get(i).getFuncID());
        }

        //3、设置功能实体的授权状态
        Function funEntity = null;
        for (int i = 0; i < funcList.size(); i++) {
            funEntity = funcList.get(i);
            if (roleFuncIDList.contains(funEntity.getID())) {
                funEntity.setAuthoriz(true);
            }
        }

        return funcList;
    }

    /**
     * 查询角色已授予的功能列表（包含功能对应的父节点信息）
     * 
     * @param roleID        角色ID
     * @return  不存在授权信息则返回 null
     */
    public ArrayList<Function> findRoleAuthorizFunc(Long roleID) {

        //查询角色对应的功能授权信息
        ArrayList<RoleFunc> roleFuncList = this.findRoleAuthoriz(roleID);
        if (null == roleFuncList || 0 == roleFuncList.size()) {
            return null;
        }

        HashSet<Long> funcIDSet = new HashSet<Long>();
        for (int i = 0; i < roleFuncList.size(); i++) {
            //保存对应的 funcID
            funcIDSet.add(roleFuncList.get(i).getFuncID());
        }

        return this.findRoleAuthorizFunc(funcIDSet);
    }

    /**
     * 查询用户已授予的功能列表（包含功能对应的父节点信息）
     * 返回的功能对象实体中相关信息如下：
     * isLeaf = true，则表示节点是叶子节点
     * isAuthoriz = true，则表示节点已经通过角色授权方式授予给了用户
     * isPrivateAuthoriz = true，则表示节点已经通过私有授权方式授予给了用户
     * 
     * @param userID
     * @param userType          用户类型
     * @return
     */
    public ArrayList<Function> findUserAuthorizFunc(Long userID, String userType) {
        //1、根据用户ID查询所授予的普通功能角色信息
        ArrayList<StaffRole> userAuthorizList = staffRoleLogic.findFuncRole(
                userID, userType);
        ArrayList<Long> roleIDList = new ArrayList<Long>();
        if (null != userAuthorizList && userAuthorizList.size() > 0) {
            for (int i = 0; i < userAuthorizList.size(); i++) {
                roleIDList.add(userAuthorizList.get(i).getRoleID().getID());
            }
        }

        //1.1 根据角色类型，查询所对应的匹配的默认角色（管理账号）
        String roleCodeTypeInit = Roles.SYS_INIT_ROLES.get(userType);
        if (roleCodeTypeInit != null && roleCodeTypeInit.trim().length() > 0) {
            Roles initEntity = rolesLogic.findRolesByCode(roleCodeTypeInit);
            //增加ID到roleIDList中
            roleIDList.add(initEntity.getID());
        }

        //1.2 根据子账号，查询所对应的匹配的默认角色 TODO
        //查询对应的子账号信息
        SubAccountInfo entity = subAccountInfoLogic.querySubAccountInfo(
                "managerStaffID", userID);
        if (null != entity) {
            //查询对应的父账号信息
            Long parentID = entity.getParentStaff();
            ManagerUser parentUserInfo = managerUserLogic
                    .findManagerUserByID(parentID);
            //根据父账号信息查询对应的子账号角色
            String parentUserType = parentUserInfo.getUserType();
            //根据父账号类型查询对应的子账号默认角色
            String subRoleCodeTypeInit = Roles.SYS_INIT_ROLES.get("SUB_"
                    + parentUserType);

            if (subRoleCodeTypeInit != null
                    && subRoleCodeTypeInit.trim().length() > 0) {
                Roles initEntity = rolesLogic
                        .findRolesByCode(subRoleCodeTypeInit);
                //增加ID到roleIDList中
                roleIDList.add(initEntity.getID());
            }
        }

        //2、查询角色对应的功能授权信息
        HashSet<Long> funcIDSet = new HashSet<Long>();
        ArrayList<RoleFunc> roleFuncList = null;
        for (int outer = 0; outer < roleIDList.size(); outer++) {
            //查询角色对应的功能授权信息
            roleFuncList = this.findRoleAuthoriz(roleIDList.get(outer));
            if (null == roleFuncList || 0 == roleFuncList.size()) {
                continue;
            }
            for (int inner = 0; inner < roleFuncList.size(); inner++) {
                //保存对应的 funcID
                funcIDSet.add(roleFuncList.get(inner).getFuncID());
            }
        }

        //3、根据用户ID查询所对应的私有功能角色信息
        Roles privateRoles = rolesLogic.findPrivateFuncRole(userID, userType,
                false);

        //4、查询私有角色对应的功能授权信息
        HashSet<Long> privateFuncIDSet = new HashSet<Long>();
        if (null != privateRoles) {
            //查询角色对应的功能授权信息
            roleFuncList = this.findRoleAuthoriz(privateRoles.getID());
            if (null != roleFuncList && 0 != roleFuncList.size()) {
                for (int inner = 0; inner < roleFuncList.size(); inner++) {
                    //保存对应的 funcID
                    privateFuncIDSet.add(roleFuncList.get(inner).getFuncID());
                }
            }
        }

        //5、解析功能ID集合对应的功能列表数据（包含功能对应的父节点信息）
        HashSet<Long> allFuncIDSet = new HashSet<Long>();
        allFuncIDSet.addAll(funcIDSet);
        allFuncIDSet.addAll(privateFuncIDSet);
        ArrayList<Function> resultList = this
                .findRoleAuthorizFunc(allFuncIDSet);

        //6、根据用户角色授权所对应的功能ID，设置对应的功能状态（isAuthoriz = true; isLeaf = true）
        //7、根据用户私有权限所对应的功能ID，设置对应的功能状态（isPrivateAuthoriz = true; isLeaf = true）
        Function funcEntity = null;
        for (int first = 0; first < resultList.size(); first++) {
            funcEntity = resultList.get(first);
            //角色授权对应的功能
            if (funcIDSet.contains(funcEntity.getID())) {
                funcEntity.setAuthoriz(true);
                funcEntity.setLeaf(true);
            }
            //私有权限对应的功能
            if (privateFuncIDSet.contains(funcEntity.getID())) {
                funcEntity.setPrivateAuthoriz(true);
                funcEntity.setLeaf(true);
            }
            funcEntity = null;
        }

        //排序
        resultList = Function.sorBySortNum(resultList);

        return resultList;
    }

    /**
     * 解析功能ID集合对应的功能列表数据（包含功能对应的父节点信息）
     * 
     * @param roleFuncID
     * @return
     */
    private ArrayList<Function> findRoleAuthorizFunc(HashSet<Long> funcIDSet) {

        //1、查询所有的功能信息
        ArrayList<Function> functionList = this.findAllFunction();
        HashMap<Long, Long> functionMap = new HashMap<Long, Long>();
        for (int i = 0; i < functionList.size(); i++) {
            functionMap.put(functionList.get(i).getID(), functionList.get(i)
                    .getParentFunc().getID());
        }

        //2、循环处理，获取所有相关的父ID信息
        HashSet IDSet = new HashSet();
        IDSet.addAll(funcIDSet);
        HashSet parentIDSet = null;

        //父节点是根节点时，则退出循环
        if (IDSet.size() > 0) {
            //计数器，防止数据错误时导致的死循环
            long counter = 0;
            long counterMax = 50;//设置最大的功能树层级
            do {
                counter++;//计数器
                parentIDSet = new HashSet();
                Iterator iter = IDSet.iterator();
                while (iter.hasNext()) {
                    //检索父节点ID
                    parentIDSet.add(functionMap.get(iter.next()));
                }
                funcIDSet.addAll(IDSet);//保存父节点ID信息
                IDSet = parentIDSet;//为下一次循环准备数据
            } while ((counter < counterMax)
                    && (!(IDSet.size() == 1 && IDSet.contains(new Long(
                            ConstantBusiness.FUNCTION_ROOT_ID)))));
        }

        funcIDSet.addAll(IDSet);

        //3、过滤需要返回的数据
        ArrayList<Function> resultList = new ArrayList<Function>();
        for (int i = 0; i < functionList.size(); i++) {
            //判断数据是否需要保存到返回结果集中
            if (funcIDSet.contains(functionList.get(i).getID())) {
                resultList.add(functionList.get(i));
            }
        }

        return resultList;
    }

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
            ArrayList<Long> newFuncIDList, ArrayList<Long> originFuncIDList) {
        return roleFuncLogic.updateRoleFunc(roleID, newFuncIDList,
                originFuncIDList);
    }

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
            ArrayList<Long> createFuncIDList, ArrayList<Long> deleteFuncIDList) {
        return roleFuncLogic.updateRoleFunc2(roleID, createFuncIDList,
                deleteFuncIDList);
    }

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
            ArrayList<Long> createFuncIDList, ArrayList<Long> deleteFuncIDList) {
        //1、查询用户对应的私有功能角色信息
        //如果之前不存在，则创建
        Roles privateRoles = rolesLogic.findPrivateFuncRole(userID, userType,
                true);
        if (null == privateRoles) {
            return null;
        }

        //2、调用方法更新私有权限对应的功能
        return this.updateRoleAuthoriz2(privateRoles.getID(), createFuncIDList,
                deleteFuncIDList);
    }

    /**
     * 更新角色的授权信息
     * 
     * @param roleID		角色ID
     * @param newFuncIDList		角色新的功能权限ID列表
     * @return
     * 			key = create	value中存放新增的功能权限数目，Long类型
     * 			key = delete	value中存放删除的功能权限数目，Long类型
     */
    public HashMap updateRoleAuthoriz(Long roleID, ArrayList<Long> newFuncIDList) {
        return roleFuncLogic.updateRoleFunc(roleID, newFuncIDList);
    }

    /**
     * 查询角色所对应的功能授权信息
     * 
     * @param roleID
     * @return
     */
    public ArrayList<RoleFunc> findRoleAuthoriz(Long roleID) {
        if (null == roleID) {
            return null;
        }

        return roleFuncLogic.findRoleFuncByRoleID(roleID);
    }

    /**
     * 更新用户的角色授权信息
     * 
     * @param userID            用户ID
     * @param userType          用户类型
     * @param newRoleIDList     角色新的功能权限ID列表
     * @param originRoleIDList  角色原来的功能权限ID列表
     * @return
     *          key = create    value中存放新增的用户角色数目，Long类型
     *          key = delete    value中存放删除的用户角色数目，Long类型
     */
    public HashMap updateStaffRole(Long userID, String userType,
            ArrayList<Long> newRoleIDList, ArrayList<Long> originRoleIDList) {
        return staffRoleLogic.updateStaffRole(userID, userType, newRoleIDList,
                originRoleIDList);
    }

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
    public HashMap updateStaffRole(Long userID, String userType,
            ArrayList<Long> newRoleIDList) {
        return staffRoleLogic.updateStaffRole(userID, userType, newRoleIDList);
    }

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
            ArrayList<String> newRoleNameList) {

        ArrayList<Long> newRoleIDList = new ArrayList<Long>();
        //根据角色编号，查询对应的编号ID
        if (null != newRoleNameList && newRoleNameList.size() > 0) {
            for (int i = 0; i < newRoleNameList.size(); i++) {
                newRoleIDList.add(rolesLogic.findRolesByCode(
                        newRoleNameList.get(i)).getID());
            }
        }

        //更新授权信息
        return this.updateStaffRole(userID, userType, newRoleIDList);
    }

    /**
     * 查询子账号对应的授权信息
     * 
     * @param userID        用户ID
     * @param userType      用户类型
     * @return
     */
    public ArrayList<String> findSubRole(Long userID, String userType) {
        return staffRoleLogic.findSubRole(userID, userType);
    }

    /**
     * 根据用户ID，查询给指定用户授权的角色列表
     * 
     * @param userID
     * @param userType          用户类型
     * @return
     */
    public ArrayList<Roles> findAuthorizRolesByUserID(Long userID,
            String userType) {

        //查询用户所授予的功能角色信息
        ArrayList<StaffRole> userAuthorizList = staffRoleLogic.findFuncRole(
                userID, userType);

        ArrayList<Roles> resultList = new ArrayList<Roles>();

        //解析角色列表
        if (null != userAuthorizList && userAuthorizList.size() > 0) {
            for (int i = 0; i < userAuthorizList.size(); i++) {
                resultList.add(userAuthorizList.get(i).getRoleID());
            }
        }

        return resultList;
    }

    /**
     *  根据用户ID，查询所有的可用来对此用户授权角色信息
     * （角色信息中存放了此用户是否已经授予了此角色的标志）
     * 
     * @param userID
     *            用户ID   
     * @param userType          用户类型
     * @param conditionData
     *            查询条件信息
     * @param pageCurrentNo 第一页为 1
     *            需要查询的页码
     * @param pageSize
     *            页面大小
     * @return
     */
    public ArrayList<Roles> findAllRolesByUserID(Long userID, String userType,
            ConditionData conditionData, int pageCurrentNo, int pageSize) {

        //查询所有的功能角色信息
        ArrayList<Roles> rolesList = rolesLogic.findAllRolesRoleTypeFunc(
                conditionData, pageCurrentNo, pageSize);

        //查询用户所授予的功能角色信息
        ArrayList<StaffRole> userAuthorizList = staffRoleLogic.findFuncRole(
                userID, userType);
        if (null != userAuthorizList && userAuthorizList.size() > 0) {
            //解析用户所授权的角色ID
            ArrayList<Long> userRolesIDList = new ArrayList<Long>();
            for (int i = 0; i < userAuthorizList.size(); i++) {
                userRolesIDList
                        .add(userAuthorizList.get(i).getRoleID().getID());
            }

            if (userRolesIDList.size() > 0) {
                //匹配已经授权的角色信息，匹配成功的设置对应的标志
                Roles rolesEntity = null;
                for (int outter = 0; outter < rolesList.size(); outter++) {
                    rolesEntity = rolesList.get(outter);
                    for (int inner = 0; inner < userRolesIDList.size(); inner++) {
                        if (userRolesIDList.get(inner).longValue() == rolesEntity
                                .getID().longValue()) {
                            rolesEntity.setIsUserAuthoriz(true);
                            break;
                        }
                    }
                }
            }
        }

        return rolesList;
    }

    /**
     * 查询可以用来给指定用户授予私有权限的功能列表
     * 数据进行了如下处理：
     * 1、是否是叶节点（isLeaf）
     * 2、普通角色授权信息（isAuthoriz）
     * 3、用户的私有授权信息（isPrivateAuthoriz）
     * 
     * @param userID
     * @param userType          用户类型
     * @return
     */
    public ArrayList<Function> findUserPrivateAuthorizFunc(Long userID,
            String userType) {

        //1、获取用户的私有功能角色
        //如果之前不存在，则创建
        Roles privateRoles = rolesLogic.findPrivateFuncRole(userID, userType,
                true);
        if (null == privateRoles) {
            return null;
        }

        //2、获取用户的私有功能角色所授予的功能信息
        ArrayList<RoleFunc> roleFuncList = this.findRoleAuthoriz(privateRoles
                .getID());
        ArrayList<Long> privateFuncIDList = new ArrayList<Long>();//私有功能角色所授予的功能信息ID集合
        for (int i = 0; i < roleFuncList.size(); i++) {
            privateFuncIDList.add(roleFuncList.get(i).getFuncID());
        }
        roleFuncList = null;

        //3、获取此用户已经授予的功能角色
        ArrayList<StaffRole> userRolesList = staffRoleLogic.findFuncRole(
                userID, userType);

        //3.1、根据角色类型，查询所对应的匹配的默认角色
        String roleCodeTypeInit = Roles.SYS_INIT_ROLES.get(userType);
        if (roleCodeTypeInit != null && roleCodeTypeInit.trim().length() > 0) {
            Roles initEntity = rolesLogic.findRolesByCode(roleCodeTypeInit);
            //增加数据到userRolesList中
            StaffRole staffRoleEntity = new StaffRole();
            staffRoleEntity.setRoleID(initEntity);

            userRolesList.add(staffRoleEntity);
        }

        //4、根据用户授予的角色，获取此用户所有的功能
        roleFuncList = new ArrayList<RoleFunc>();
        if (null != userRolesList && userRolesList.size() > 0) {
            ArrayList<RoleFunc> tempList = null;
            for (int i = 0; i < userRolesList.size(); i++) {
                //获取角色对应的角色功能信息
                tempList = this.findRoleAuthoriz(userRolesList.get(i)
                        .getRoleID().getID());
                if (null != tempList && tempList.size() > 0) {
                    roleFuncList.addAll(tempList);
                    tempList = null;
                }
            }
        }
        //用户角色所对应的功能信息ID集合
        ArrayList<Long> funcIDList = new ArrayList<Long>();
        if (roleFuncList.size() > 0) {
            for (int i = 0; i < roleFuncList.size(); i++) {
                funcIDList.add(roleFuncList.get(i).getFuncID());
            }
        }

        //5、获取用来给用户授予私有权限的功能列表
        //查询所有的功能信息列表
        ArrayList<Function> funcList = this.findAllFunction();
        //设置角色对应的功能实体的授权状态
        Function funEntity = null;
        for (int i = 0; i < funcList.size(); i++) {
            funEntity = funcList.get(i);
            //普通角色对应的功能实体的授权状态
            if (funcIDList.contains(funEntity.getID())) {
                funEntity.setAuthoriz(true);
            }
            //私有角色对应的功能实体的授权状态
            if (privateFuncIDList.contains(funEntity.getID())) {
                funEntity.setPrivateAuthoriz(true);
            }
        }

        //返回处理后的功能列表
        return funcList;
    }
}
