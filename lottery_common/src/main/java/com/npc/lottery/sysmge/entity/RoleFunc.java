package com.npc.lottery.sysmge.entity;

import java.io.Serializable;

/**
 * 角色所拥有的功能实体类，对应数据表（TB_FRAME_ROLE_FUNC）
 *
 * @author none
 *
 */
public class RoleFunc implements Serializable {

	public static final String AUTHORIZTYPE_ASSOCIATE = "0";//授权类型：级联授权

	public static final String AUTHORIZTYPE_ALONE = "1";//授权类型：独立授权

	private Long ID;

	private Long roleID;

	private Long funcID;

	private String authorizType;

	private String remark;

	public String getAuthorizType() {
		return authorizType;
	}

	public void setAuthorizType(String authorizType) {
		this.authorizType = authorizType;
	}

	public Long getFuncID() {
		return funcID;
	}

	public void setFuncID(Long funcID) {
		this.funcID = funcID;
	}

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

	public Long getRoleID() {
		return roleID;
	}

	public void setRoleID(Long roleID) {
		this.roleID = roleID;
	}

}
