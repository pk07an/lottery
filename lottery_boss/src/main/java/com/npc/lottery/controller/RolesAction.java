package com.npc.lottery.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.npc.lottery.common.action.BaseAction;
import com.npc.lottery.common.ConditionData;
import com.npc.lottery.common.Constant;
import com.npc.lottery.sysmge.entity.Roles;
import com.npc.lottery.sysmge.logic.interf.IRolesLogic;
import com.npc.lottery.util.Tool;

/**
 * 角色信息
 * 
 * @author none
 *
 */
public class RolesAction extends BaseAction {

    private static Logger log = Logger.getLogger(RolesAction.class);

    private IRolesLogic rolesLogic = null;

    public void setRolesLogic(IRolesLogic rolesLogic) {
        this.rolesLogic = rolesLogic;
    }

    /**
     * 角色信息查看：列表功能
     * 
     * @return
     * @throws Exception
     */
    public String list() throws Exception {
        log.info("list");

        //调用查询方法
        this.searchRoles();

        return "list";
    }

    /**
     * 角色信息：新增
     * 
     * @return
     * @throws Exception
     */
    public String addRoles() throws Exception {
        log.info("addRoles");
        HttpServletRequest request = ServletActionContext.getRequest();
        Roles roles = new Roles();
        roles.setRoleLevel(Roles.ROLE_LEVEL_MANAGE);
        roles.setRoleType(Roles.ROLE_TYPE_RES);
        request.setAttribute("roles", roles);
        return "addRoles";
    }

    /**
     * 角色信息：详细信息
     * 
     * @return
     * @throws Exception
     */
    public String viewRoles() throws Exception {
        log.info("viewRoles");
        HttpServletRequest request = ServletActionContext.getRequest();
        Roles roles = rolesLogic.findByID(ID);
        request.setAttribute("roles", roles);
        return "viewRoles";
    }

    /**
     * 角色信息：编辑
     * 
     * @return
     * @throws Exception
     */
    public String modifyRoles() throws Exception {
        log.info("modifyRoles");
        viewRoles();// 调用查看
        return "modifyRoles";
    }

    /**
     * 删除
     * 
     * @return
     * @throws Exception
     */
    public String del() throws Exception {
        log.info("del");

        Roles roles = rolesLogic.findByID(ID);

        rolesLogic.delete(roles);
        request.setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                + Constant.COLOR_GREEN + "'>数据删除成功.</font>");

        return "del";
    }

    /**
     * 角色信息：查询
     * 
     * @return
     * @throws Exception
     */
    public String searchRoles() throws Exception {
        log.info("searchRoles");
        //查询条件
        //log.info("roleName = " + roleName + "  roleCode = " + roleCode
        //		+ " roleType = " + roleType);

        HttpServletRequest request = ServletActionContext.getRequest();

        //获取请求的页码
        String pageCurrentNoStr = Tool.getParameter(request,
                Constant.PAGETAG_CURRENT, "1");
        int pageCurrentNo = 1;
        try {
            pageCurrentNo = Integer.parseInt(pageCurrentNoStr);
        } catch (Exception ex) {

        }

        //构造查询条件
        ConditionData conditionData = new ConditionData();
        conditionData.addLike("roleName", roleName);//设置名称的模糊查询条件
        conditionData.addLike("roleCode", roleCode);//设置编码的模糊查询条件
        conditionData.addOrder("ID", ConditionData.ORDER_TYPE_ASC);
        //判断是否需要增加状态查询条件
        if (null != roleType && 0 != roleType.trim().length()) {
            conditionData.addEqual("roleType", roleType);//设置状态的查询条件
        } else {
            conditionData.addIn("roleType", Roles.ROLE_TYPE_RES);//查询角色类型为功能权限角色的数据
            conditionData.addIn("roleType", Roles.ROLE_TYPE_SIGN);//查询角色类型为数据权限角色的数据
            conditionData.addIn("roleType", Roles.ROLE_TYPE_INIT);//查询角色类型为内置角色的数据
        }
        //判断是否需要增加角色等级查询条件
        if (null != roleLevel && 0 != roleLevel.trim().length()) {
            conditionData.addEqual("roleLevel", roleLevel);//设置状态的查询条件
        }

        //获取记录总数
        long recordAmount = rolesLogic.findAmountRecord(conditionData);
        log.info("记录总数：" + recordAmount);
        long sumPages = (recordAmount - 1) / Constant.LIST_PAGE_SIZE + 1;

        //查询记录列表
        List rolesList = rolesLogic.findRolesList(conditionData, pageCurrentNo,
                Constant.LIST_PAGE_SIZE);

        //保存返回的数据记录集
        request.setAttribute("sumPages", sumPages + "");
        request.setAttribute("recordAmount", recordAmount + "");
        request.setAttribute("pageCurrentNo", pageCurrentNo + "");
        request.setAttribute("resultList", rolesList);
        request.setAttribute("roleTypeList", Roles.getRoleTypeList(true));//状态的页面下拉列表数据
        request.setAttribute("roleLevelList", Roles.getRoleLevelList(true));

        return "searchRoles";
    }

    /**
     * 修改角色内容，接收来自前台内容
     * 
     * @return
     */
    public Roles receiveRoles() {

        Roles roles = new Roles();
        roles.setID(ID);
        roles.setRemark(remark);
        roles.setRoleCode(roleCode);
        roles.setRoleLevel(roleLevel);
        roles.setRoleName(roleName);
        roles.setRoleType(roleType);

        return roles;
    }

    /**
     * 新增 提交处理
     * 
     * @return
     * @throws Exception
     */

    public String submitAddRoles() throws Exception {

        log.info("submitSaveRoles");
        Roles roles = receiveRoles();

        rolesLogic.saveOrUpdate(roles);
        // 保存提示信息
        ID = roles.getID();

        return "saveRoles";
    }

    /**
     * 信息修改提交
     * 
     * @return
     */
    public String submitModifyRoles() {
        log.info("submitModifyRoles");
        Roles roles = receiveRoles();
        // 保存提示信息

        rolesLogic.saveOrUpdate(roles);
        ID = roles.getID();
        return "saveRoles";
    }

    private Long ID;
    private String roleCode;// 角色编码
    private String roleName;// 角色名称
    private String roleLevel;// 角色等级
    private String roleType;// 角色类型
    private String remark;// 备注

    public Long getID() {
        return ID;
    }

    public void setID(Long id) {
        ID = id;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleLevel() {
        return roleLevel;
    }

    public void setRoleLevel(String roleLevel) {
        this.roleLevel = roleLevel;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public IRolesLogic getRolesLogic() {
        return rolesLogic;
    }

}
