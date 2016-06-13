package com.npc.lottery.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.npc.lottery.common.action.BaseAction;
import com.npc.lottery.common.ConditionData;
import com.npc.lottery.common.Constant;
import com.npc.lottery.sysmge.entity.Function;
import com.npc.lottery.sysmge.entity.Roles;
import com.npc.lottery.sysmge.entity.StaffRole;
import com.npc.lottery.sysmge.logic.interf.IAuthorizLogic;
import com.npc.lottery.sysmge.logic.interf.IRolesLogic;
import com.npc.lottery.sysmge.logic.interf.IStaffRoleLogic;
import com.npc.lottery.util.Tool;

/**
 * 
 * 授权
 *
 * @author none
 *
 */
public class AuthorizAction extends BaseAction {

    private static Logger log = Logger.getLogger(AuthorizAction.class);

    private IAuthorizLogic authorizLogic;

    private IRolesLogic rolesLogic;

    private IStaffRoleLogic staffRoleLogic;

    public void setAuthorizLogic(IAuthorizLogic authorizLogic) {
        this.authorizLogic = authorizLogic;
    }

    public void setRolesLogic(IRolesLogic rolesLogic) {
        this.rolesLogic = rolesLogic;
    }

    public void setStaffRoleLogic(IStaffRoleLogic staffRoleLogic) {
        this.staffRoleLogic = staffRoleLogic;
    }

    /**
     * 角色授权，主页面
     * 
     * @return
     * @throws Exception
     */
    public String authorizRoleMain() throws Exception {
        log.info("authorizRoleMain");

        log.info("roleID = " + roleID);

        return "authorizRoleMain";
    }

    /**
     * 显示待授权的功能信息
     * 
     * @return
     * @throws Exception
     */
    public String selectFunc() throws Exception {
        log.info("selectFunc");

        ArrayList<Function> resultList = (ArrayList<Function>) authorizLogic
                .findAllFunction(roleID);

        HttpServletRequest request = ServletActionContext.getRequest();
        request.setAttribute("resultList", resultList);

        return "selectFunc";
    }

    /**
     * 角色授权，提交
     * 
     * @return
     * @throws Exception
     */
    public String authorizRoleSubmit() throws Exception {
        log.info("authorizRoleSubmit");

        HttpServletRequest request = ServletActionContext.getRequest();

        log.info("roleID = " + roleID);
        log.info("selectFuncID = " + selectFuncID);

        //解析出所选择的 funcID
        ArrayList<Long> funcIDList = new ArrayList<Long>();
        String[] funcIDGroup = selectFuncID.split(Function.ID_GROUP_SPLIT);
        String funcIDTemp = null;
        Long funcID = null;
        for (int i = 0; i < funcIDGroup.length; i++) {
            funcIDTemp = funcIDGroup[i];
            if (null == funcIDTemp || 0 == funcIDTemp.trim().length()) {
                continue;
            }
            try {
                funcID = new Long(funcIDTemp);
                funcIDList.add(funcID);
            } catch (Exception ex) {
                log.error(ex);
            }
        }

        //更新权限信息
        HashMap resultMap = authorizLogic
                .updateRoleAuthoriz(roleID, funcIDList);
        Integer createNum = (Integer) resultMap.get("create");
        Integer deleteNum = (Integer) resultMap.get("delete");

        request.setAttribute(Constant.INFO_PAGE_TYPE_CLOSE, "true");
        request.setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                + Constant.COLOR_GREEN + "'>授权成功，此次授权新增权限【" + createNum
                + "】个，取消权限【" + deleteNum + "】个！</font>");

        return "authorizRoleSubmit";
    }

    /**
     * 显示已授权的功能信息
     * 
     * @return
     * @throws Exception
     */
    public String authorizRoleView() throws Exception {
        log.info("authorizRoleView");

        ArrayList<Function> resultList = (ArrayList<Function>) authorizLogic
                .findRoleAuthorizFunc(roleID);

        HttpServletRequest request = ServletActionContext.getRequest();

        if (null == resultList || 0 == resultList.size()) {
            request.setAttribute(Constant.INFO_PAGE_TYPE_CLOSE, "true");
            request.setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                    + Constant.COLOR_RED + "'>该角色目前尚无授权信息！</font>");
            return "failure";
        }

        request.setAttribute("resultList", resultList);

        return "authorizRoleView";
    }

    /**
     * 用户的角色授权，主页面
     * 
     * @return
     * @throws Exception
     */
    public String authorizUserRoleMain() throws Exception {
        log.info("authorizUserRoleMain");

        log.info("userID = " + userID);

        return "authorizUserRoleMain";
    }

    /**
     * 用户的角色授权，记录选择的角色数据页面
     * 
     * @return
     * @throws Exception
     */
    public String userRoleData() throws Exception {
        log.info("userRoleData");

        //查询用户已经授权的角色信息
        ArrayList<StaffRole> entityList = staffRoleLogic.findFuncRole(userID,
                userType);
        //构造用户已经授权的角色下拉列表
        StringBuilder rolesGroup = new StringBuilder();
        if (entityList == null) {
            entityList = new ArrayList();
        }
        for (int i = 0; i < entityList.size(); i++) {
            rolesGroup.append(entityList.get(i).getRoleID().getID());
            rolesGroup.append(Roles.SELECT_ROLEID_ROLENAME_SPLIT);//增加ID与名称之间分割符
            rolesGroup.append(entityList.get(i).getRoleID().getRoleName());
            rolesGroup.append(Roles.SELECT_MULTI_ROLES_SPLIT);//增加多个角色之间的分割符
        }
        if (rolesGroup.length() > 0) {
            //删除最后一个分隔符
            rolesGroup.delete(rolesGroup.length()
                    - Roles.SELECT_MULTI_ROLES_SPLIT.length(),
                    rolesGroup.length());
        }

        HttpServletRequest request = ServletActionContext.getRequest();
        request.setAttribute("rolesGroup", rolesGroup.toString());

        return "userRoleData";
    }

    /**
     * 用户的角色授权，列表可供选择的角色信息
     * 
     * @return
     * @throws Exception
     */
    public String userRoleSelectList() throws Exception {
        log.info("userRoleSelectList");

        HttpServletRequest request = ServletActionContext.getRequest();

        // 获取请求的页码
        String pageCurrentNoStr = Tool.getParameter(request,
                Constant.PAGETAG_CURRENT, "1");
        int pageCurrentNo = 1;
        try {
            pageCurrentNo = Integer.parseInt(pageCurrentNoStr);
        } catch (Exception ex) {

        }

        //查询条件
        ConditionData conditionData = new ConditionData();
        conditionData.addInList("roleLevel", Roles.getRoleLevel(userType));

        // 获取记录总数
        long recordAmount = rolesLogic
                .findAmountAllRolesRoleTypeFunc(conditionData);
        log.info("记录总数：" + recordAmount);
        long sumPages = (recordAmount - 1) / Constant.LIST_PAGE_SIZE + 1;

        // 查询记录列表
        ArrayList<Roles> resultList = authorizLogic.findAllRolesByUserID(
                userID, userType, conditionData, pageCurrentNo,
                Constant.LIST_PAGE_SIZE);

        // 保存返回的数据记录集
        request.setAttribute("sumPages", sumPages + "");
        request.setAttribute("recordAmount", recordAmount + "");
        request.setAttribute("pageCurrentNo", pageCurrentNo + "");

        request.setAttribute("resultList", resultList);

        return "userRoleSelectList";
    }

    /**
     * 用户的角色授权，提交
     * 
     * @return
     * @throws Exception
     */
    public String authorizUserRoleSubmit() throws Exception {
        log.info("authorizUserRoleSubmit");

        HttpServletRequest request = ServletActionContext.getRequest();

        log.info("userID = " + userID);
        log.info("rolesList = " + rolesList);
        log.info("userType = " + userType);

        //解析出所选择的 roleID
        ArrayList<Long> roleIDList = new ArrayList<Long>();
        String[] roleIDGroup = rolesList.split(Roles.SELECT_MULTI_ROLES_SPLIT);
        String roleIDTemp = null;
        Long roleID = null;
        for (int i = 0; i < roleIDGroup.length; i++) {
            roleIDTemp = roleIDGroup[i];
            if (null == roleIDTemp || 0 == roleIDTemp.trim().length()) {
                continue;
            }
            try {
                roleID = new Long(roleIDTemp);
                roleIDList.add(roleID);
            } catch (Exception ex) {
                log.error(ex);
            }
        }

        //更新用户的角色信息
        HashMap resultMap = authorizLogic.updateStaffRole(userID, userType,
                roleIDList);
        Integer createNum = (Integer) resultMap.get("create");
        Integer deleteNum = (Integer) resultMap.get("delete");

        request.setAttribute(Constant.INFO_PAGE_TYPE_CLOSE, "true");
        request.setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                + Constant.COLOR_GREEN + "'>授权成功，此次授权新增角色【" + createNum
                + "】个，取消角色【" + deleteNum + "】个！</font>");

        return "authorizUserRoleSubmit";
    }

    /**
     * 用户的私有权限授权：主界面
     * 
     * @return
     * @throws Exception
     */
    public String authorizUserPrivate() throws Exception {
        log.info("authorizUserPrivate");

        return "authorizUserPrivate";
    }

    /**
     * 用户的私有权限授权：待授权的功能信息列表
     * 
     * @return
     * @throws Exception
     */
    public String userPrivateSelectFunc() throws Exception {
        log.info("userPrivateSelectFunc");

        //查询用户授予私有权限的功能列表
        ArrayList<Function> resultList = authorizLogic
                .findUserPrivateAuthorizFunc(userID, userType);
        HttpServletRequest request = ServletActionContext.getRequest();
        request.setAttribute("resultList", resultList);

        return "userPrivateSelectFunc";
    }

    /**
     * 用户的私有权限授权，提交
     * 
     * @return
     * @throws Exception
     */
    public String authorizUserPrivateSubmit() throws Exception {
        log.info("authorizUserPrivateSubmit");

        HttpServletRequest request = ServletActionContext.getRequest();

        //解析选中的功能ID列表
        ArrayList<Long> createFuncIDList = new ArrayList<Long>();
        String[] funcIDGroup = selectFuncID.split(Function.ID_GROUP_SPLIT);
        String funcIDTemp = null;
        Long funcID = null;
        for (int i = 0; i < funcIDGroup.length; i++) {
            funcIDTemp = funcIDGroup[i];
            if (null == funcIDTemp || 0 == funcIDTemp.trim().length()) {
                continue;
            }
            try {
                funcID = new Long(funcIDTemp);
                createFuncIDList.add(funcID);
            } catch (Exception ex) {
                log.error(ex);
            }
        }

        //解析未选中的功能ID列表
        ArrayList<Long> deleteFuncIDList = new ArrayList<Long>();
        funcIDGroup = null;
        funcIDGroup = unSelectFuncID.split(Function.ID_GROUP_SPLIT);
        for (int i = 0; i < funcIDGroup.length; i++) {
            funcIDTemp = funcIDGroup[i];
            if (null == funcIDTemp || 0 == funcIDTemp.trim().length()) {
                continue;
            }
            try {
                funcID = new Long(funcIDTemp);
                deleteFuncIDList.add(funcID);
            } catch (Exception ex) {
                log.error(ex);
            }
        }

        //更新权限信息
        HashMap resultMap = authorizLogic.updateUserPrivateFuncAuthoriz(userID,
                userType, createFuncIDList, deleteFuncIDList);
        Integer createNum = (Integer) resultMap.get("create");
        Integer deleteNum = (Integer) resultMap.get("delete");

        request.setAttribute(Constant.INFO_PAGE_TYPE_CLOSE, "true");
        request.setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                + Constant.COLOR_GREEN + "'>私有功能权限授权成功，此次授权新增功能权限【" + createNum
                + "】个，取消功能权限【" + deleteNum + "】个！</font>");

        return "authorizUserPrivateSubmit";
    }

    /**
     * 用户授权查看：主界面
     * 
     * @return
     * @throws Exception
     */
    public String authorizUserViewMain() throws Exception {
        log.info("authorizUserViewMain");

        return "authorizUserViewMain";
    }

    /**
     * 用户授权查看：查看功能树
     * 
     * @return
     * @throws Exception
     */
    public String userViewFunc() throws Exception {
        log.info("userViewFunc");
        HttpServletRequest request = ServletActionContext.getRequest();

        //查询用户对应的授权功能信息
        ArrayList<Function> resultList = authorizLogic.findUserAuthorizFunc(
                userID, userType);

        if (null == resultList || 0 == resultList.size()) {
            //request.setAttribute(Constant.INFO_PAGE_TYPE_CLOSE, "true");
            request.setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                    + Constant.COLOR_RED + "'>该用户目前尚无相关的功能权限信息！</font>");
            return "failure";
        }

        request.setAttribute("resultList", resultList);
        return "userViewFunc";
    }

    /**
     * 用户授权查看：查看权限列表
     * 
     * @return
     * @throws Exception
     */
    public String userViewRole() throws Exception {
        log.info("userViewRole");

        HttpServletRequest request = ServletActionContext.getRequest();

        //查询用户所授予的功能角色信息
        ArrayList<StaffRole> userAuthorizList = staffRoleLogic.findFuncRole(
                userID, userType);
        //解析角色列表
        ArrayList<Roles> resultList = new ArrayList<Roles>();
        if (null != userAuthorizList && userAuthorizList.size() > 0) {
            for (int i = 0; i < userAuthorizList.size(); i++) {
                resultList.add(userAuthorizList.get(i).getRoleID());
            }
        }
        //根据角色类型，查询所对应的匹配的默认角色
        String roleCodeTypeInit = Roles.SYS_INIT_ROLES.get(userType);
        if (roleCodeTypeInit != null && roleCodeTypeInit.trim().length() > 0) {
            Roles initEntity = rolesLogic.findRolesByCode(roleCodeTypeInit);
            resultList.add(initEntity);
        }

        request.setAttribute("resultList", resultList);

        return "userViewRole";
    }

    private Long roleID;

    private Long userID;

    private String selectFuncID;//选中的功能ID

    private String unSelectFuncID;//未选中的功能ID

    private String rolesList;

    private String userType;

    public Long getRoleID() {
        return roleID;
    }

    public void setRoleID(Long roleID) {
        this.roleID = roleID;
    }

    public String getSelectFuncID() {
        return selectFuncID;
    }

    public void setSelectFuncID(String selectFuncID) {
        this.selectFuncID = selectFuncID;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getRolesList() {
        return rolesList;
    }

    public void setRolesList(String rolesList) {
        this.rolesList = rolesList;
    }

    public String getUnSelectFuncID() {
        return unSelectFuncID;
    }

    public void setUnSelectFuncID(String unSelectFuncID) {
        this.unSelectFuncID = unSelectFuncID;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
