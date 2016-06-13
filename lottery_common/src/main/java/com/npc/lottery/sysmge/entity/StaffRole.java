package com.npc.lottery.sysmge.entity;

import java.io.Serializable;

/**
 * 角色所拥有的角色实体类，对应数据表（TB_FRAME_STAFF_ROLE）
 *
 * @author none
 *
 */
public class StaffRole implements Serializable {

    private Long ID;

    private Roles roleID;

    private Long userID;

    private String userType;//用户类型

    private String remark;

    public Long getID() {
        return ID;
    }

    public void setID(Long id) {
        ID = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Roles getRoleID() {
        return roleID;
    }

    public void setRoleID(Roles roleID) {
        this.roleID = roleID;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
    
    public static String getUserTypeAutho(String userType){
        
        String result = "";
        
        return result;
    }
}
