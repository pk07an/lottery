package com.npc.lottery.sysmge.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.npc.lottery.util.ListEntity;

/**
 * TbFRAMERoles entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Roles implements java.io.Serializable {

    //此处定义常量，即对应数据表中相关字段的取值常量
    public static final String ROLE_TYPE_RES = "0";//资源角色

    public static final String ROLE_TYPE_SIGN = "1";//标志角色

    public static final String ROLE_TYPE_PRIVATE = "2";//私有角色

    public static final String ROLE_TYPE_INIT = "3";//内置角色

    public static final String ROLE_LEVEL_ADMIN = "0";//系统管理员

    public static final String ROLE_LEVEL_MANAGE = "1";//管理用户

    public static final String ROLE_LEVEL_MEMBER = "2";//会员用户

    public static final String SELECT_ROLEID_ROLENAME_SPLIT = "&&";//页面上字符串中，角色ID和角色名称之间的分隔符

    public static final String SELECT_MULTI_ROLES_SPLIT = "!!";//页面上字符串中，多个角色之间的分隔符

    //记录系统初始化角色，key = roleType；value = roleCode
    public static final HashMap<String, String> SYS_INIT_ROLES = new HashMap<String, String>();
    static {
        //填充系统初始化角色
        SYS_INIT_ROLES.put(ManagerStaff.USER_TYPE_SYS, "SYS_DEFAULT");//系统管理员
        SYS_INIT_ROLES.put(ManagerStaff.USER_TYPE_MANAGER, "MANAGER_DEFAULT");//总管默认角色
        SYS_INIT_ROLES.put(ManagerStaff.USER_TYPE_CHIEF, "CHIEF_DEFAULT");//总监默认角色
        SYS_INIT_ROLES.put(ManagerStaff.USER_TYPE_BRANCH, "BRANCH_DEFAULT");//分公司默认角色
        SYS_INIT_ROLES.put(ManagerStaff.USER_TYPE_STOCKHOLDER,
                "STOCKHOLDER_DEFAULT");//股东默认角色
        SYS_INIT_ROLES.put(ManagerStaff.USER_TYPE_GEN_AGENT,
                "GEN_AGENT_DEFAULT");//总代理默认角色
        SYS_INIT_ROLES.put(ManagerStaff.USER_TYPE_AGENT, "AGENT_DEFAULT");//代理默认角色
        SYS_INIT_ROLES.put(MemberStaff.USER_TYPE_MEMBER, "MEMBER_DEFAULT");//会员默认角色
        
        //填充子账号初始化角色
        SYS_INIT_ROLES.put("SUB_" + ManagerStaff.USER_TYPE_MANAGER, "MANAGER_SUB_DEFAULT");//总管默认角色
        SYS_INIT_ROLES.put("SUB_" + ManagerStaff.USER_TYPE_CHIEF, "CHIEF_SUB_DEFAULT");//总监默认角色
        SYS_INIT_ROLES.put("SUB_" + ManagerStaff.USER_TYPE_BRANCH, "BRANCH_SUB_DEFAULT");//分公司默认角色
        SYS_INIT_ROLES.put("SUB_" + ManagerStaff.USER_TYPE_STOCKHOLDER,
                "STOCKHOLDER_SUB_DEFAULT");//股东默认角色
        SYS_INIT_ROLES.put("SUB_" + ManagerStaff.USER_TYPE_GEN_AGENT,
                "GEN_AGENT_SUB_DEFAULT");//总代理默认角色
        SYS_INIT_ROLES.put("SUB_" + ManagerStaff.USER_TYPE_AGENT, "AGENT_SUB_DEFAULT");//代理默认角色
    }

    // Fields
    private Long ID;

    private String roleCode;

    private String roleName;

    private String roleLevel;

    private String roleType;

    private String remark;

    private boolean isUserAuthoriz = false;//此角色是否已经被用户授权

    private Set staffRoleSet = new HashSet();//用户与角色关系，主要用来级联删除（角色所拥有的用户）

    // Property accessors

    public Long getID() {
        return ID;
    }

    public void setID(Long id) {
        ID = id;
    }

    public String getRoleCode() {
        return this.roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleLevel() {
        return roleLevel;
    }

    /**
     * 根据用户类型，查询对应的角色等级列表
     * 
     * @param userType
     * @return
     */
    public static ArrayList<String> getRoleLevel(String userType) {

        ArrayList<String> levelList = new ArrayList<String>();

        int userTypeInt = -1;
        try {
            userTypeInt = Integer.parseInt(userType);
        } catch (Exception ex) {

        }

        //判断用户类型是否合法
        if (userTypeInt < 0
                || userTypeInt > Integer.parseInt(MemberStaff.USER_TYPE_MEMBER)) {
            return levelList;
        }

        //根据用户类型，返回对应的角色等级
        if (userTypeInt < Integer.parseInt(ManagerStaff.USER_TYPE_MANAGER)) {
            //用户类型是系统管理员，则用户等级可对应所有
            levelList.add(Roles.ROLE_LEVEL_ADMIN);
            levelList.add(Roles.ROLE_LEVEL_MANAGE);
            levelList.add(Roles.ROLE_LEVEL_MEMBER);
        } else if (userTypeInt > Integer
                .parseInt(ManagerStaff.USER_TYPE_MANAGER)
                && userTypeInt < Integer.parseInt(MemberStaff.USER_TYPE_MEMBER)) {
            //用户类型是管理员，则用户等级可对应如下等级
            levelList.add(Roles.ROLE_LEVEL_MANAGE);
            levelList.add(Roles.ROLE_LEVEL_MEMBER);
        } else if (userTypeInt > Integer.parseInt(ManagerStaff.USER_TYPE_SUB)) {
            //用户类型是普通会员，则用户等级可对应如下等级
            levelList.add(Roles.ROLE_LEVEL_MEMBER);
        }

        return levelList;
    }

    /**
     * 根据用户类型，查询对应的角色等级
     * 
     * @param userType
     * @return
     */
    public static String getLevelByUserType(String userType) {

        int userTypeInt = -1;
        try {
            userTypeInt = Integer.parseInt(userType);
        } catch (Exception ex) {

        }

        //判断用户类型是否合法
        if (userTypeInt < 0
                || userTypeInt > Integer.parseInt(MemberStaff.USER_TYPE_MEMBER)) {
            return "";
        }

        //根据用户类型，返回对应的角色等级
        if (userTypeInt < Integer.parseInt(ManagerStaff.USER_TYPE_MANAGER)) {
            //用户类型是系统管理员，则用户等级为系统管理员
            return Roles.ROLE_LEVEL_ADMIN;
        } else if (userTypeInt > Integer
                .parseInt(ManagerStaff.USER_TYPE_MANAGER)
                && userTypeInt < Integer.parseInt(MemberStaff.USER_TYPE_MEMBER)) {
            //用户类型是管理用户，则用户等级为管理用户
            return Roles.ROLE_LEVEL_MANAGE;
        } else if (userTypeInt > Integer.parseInt(ManagerStaff.USER_TYPE_SUB)) {
            //用户类型是普通会员，则用户等级为会员用户
            return Roles.ROLE_LEVEL_MEMBER;
        }

        return "";
    }

    public void setRoleLevel(String roleLevel) {
        this.roleLevel = roleLevel;
    }

    public String getRoleType() {
        return this.roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public HashMap getRoleTypeMap() {
        return getRoleTypeMap(false);
    }

    public String getRoleTypeName() {
        String roleTypeName = "";
        if (roleType.equals(Roles.ROLE_TYPE_RES)) {
            roleTypeName = "资源角色";
        } else if (roleType.equals(Roles.ROLE_TYPE_SIGN)) {
            roleTypeName = "标志角色";
        } else if (roleType.equals(Roles.ROLE_TYPE_PRIVATE)) {
            roleTypeName = "私有角色";
        } else if (roleType.equals(Roles.ROLE_TYPE_INIT)) {
            roleTypeName = "<font color='#939393'>内置角色</font>";
        }

        return roleTypeName;
    }

    public static HashMap getRoleTypeMap(boolean canBlank) {
        HashMap result = new HashMap();
        if (canBlank) {
            result.put(null, null);
        }
        result.put(Roles.ROLE_TYPE_RES, "资源角色");
        result.put(Roles.ROLE_TYPE_SIGN, "标志角色");
        return result;
    }

    /**
     * 获取页面下拉列表中的显示数据
     * 
     * @param canBlank
     * @return
     */
    public static ArrayList<ListEntity> getRoleTypeList(boolean canBlank) {

        ArrayList<ListEntity> resultList = new ArrayList<ListEntity>();
        ListEntity entity;

        if (canBlank) {
            entity = new ListEntity();
            entity.setKey("");
            entity.setName("");
            resultList.add(entity);
        }

        entity = new ListEntity();
        entity.setKey(Roles.ROLE_TYPE_RES);
        entity.setName("资源角色");
        resultList.add(entity);

        entity = new ListEntity();
        entity.setKey(Roles.ROLE_TYPE_SIGN);
        entity.setName("标志角色");
        resultList.add(entity);

        entity = new ListEntity();
        entity.setKey(Roles.ROLE_TYPE_INIT);
        entity.setName("内置角色");
        resultList.add(entity);

        return resultList;
    }

    /**
     * 获取页面下拉列表中的显示数据
     * 
     * @param canBlank
     * @return
     */
    public ArrayList<ListEntity> getRoleTypeList() {
        return Roles.getRoleTypeList(false);
    }

    public HashMap getRoleLevelMap() {
        return getRoleLevelMap(false);
    }

    public String getRoleLevelName() {
        String roleLevelName = "";
        if (roleLevel.equals(Roles.ROLE_LEVEL_ADMIN))
            roleLevelName = "系统管理员";
        else if (roleLevel.equals(Roles.ROLE_LEVEL_MANAGE))
            roleLevelName = "管理用户";
        else if (roleLevel.equals(Roles.ROLE_LEVEL_MEMBER))
            roleLevelName = "会员用户";
        return roleLevelName;
    }

    public static HashMap getRoleLevelMap(boolean canBlank) {
        HashMap result = new HashMap();
        if (canBlank) {
            result.put(null, null);
        }
        result.put(Roles.ROLE_LEVEL_ADMIN, "系统管理员");
        result.put(Roles.ROLE_LEVEL_MANAGE, "管理用户");
        result.put(Roles.ROLE_LEVEL_MEMBER, "会员用户");
        return result;
    }

    /**
     * 获取页面下拉列表中的显示数据
     * 
     * @param canBlank
     * @return
     */
    public static ArrayList<ListEntity> getRoleLevelList(boolean canBlank) {

        ArrayList<ListEntity> resultList = new ArrayList<ListEntity>();
        ListEntity entity;

        if (canBlank) {
            entity = new ListEntity();
            entity.setKey("");
            entity.setName("");
            resultList.add(entity);
        }

        entity = new ListEntity();
        entity.setKey(Roles.ROLE_LEVEL_ADMIN);
        entity.setName("系统管理员");
        resultList.add(entity);

        entity = new ListEntity();
        entity.setKey(Roles.ROLE_LEVEL_MANAGE);
        entity.setName("管理用户");
        resultList.add(entity);

        entity = new ListEntity();
        entity.setKey(Roles.ROLE_LEVEL_MEMBER);
        entity.setName("会员用户");
        resultList.add(entity);

        return resultList;
    }

    /**
     * 判断是否允许删除
     * 
     * @return
     */
    public boolean getDelFlag() {

        boolean result = false;

        if (SYS_INIT_ROLES.containsValue(roleCode)) {
            //初始化角色不允许删除
            result = false;
        } else {
            result = true;
        }

        return result;
    }

    /**
     * 获取页面下拉列表中的显示数据
     * 
     * @param canBlank
     * @return
     */
    public ArrayList<ListEntity> getRoleLevelList() {
        return Roles.getRoleLevelList(false);
    }

    public Set getStaffRoleSet() {
        return staffRoleSet;
    }

    public void setStaffRoleSet(Set staffRoleSet) {
        this.staffRoleSet = staffRoleSet;
    }

    public boolean getIsUserAuthoriz() {
        return isUserAuthoriz;
    }

    public void setIsUserAuthoriz(boolean isUserAuthoriz) {
        this.isUserAuthoriz = isUserAuthoriz;
    }
}