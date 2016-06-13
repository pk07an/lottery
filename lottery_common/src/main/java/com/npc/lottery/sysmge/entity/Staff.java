package com.npc.lottery.sysmge.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.npc.lottery.common.Constant;

/**
 * 人员表所对应的实体类，对应数据表（TB_FRAME_STAFF）
 * 
 * @author none
 * 
 */
public class Staff implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String FLAG_USE = "0";//用户有效

	public final static String FLAG_FORBID = "1";//禁用

	public final static String FLAG_DELETE = "2";//删除

	private Long ID;//用户ID

	private Long orgID;//所属机构

	private String account;//登录账号

	private String flag;//状态

	private String userPwd;//用户密码

	private String chsName;//中文名字

	private String engName;//英文名字

	private String emailAddr;//eMail地址

	private String officePhone;//办公室电话

	private String mobilePhone;//移动电话

	private String fax;//传真

	private Timestamp createDate;//创建时间

	private Timestamp updateDate;//更新时间

	private Timestamp loginDate;//最后登录时间

	private String loginIp;//最后登录IP

	private String comments;//备注

	public Long getID() {
		return ID;
	}

	public void setID(Long id) {
		ID = id;
	}

	public Long getOrgID() {
		return orgID;
	}

	public void setOrgID(Long orgID) {
		this.orgID = orgID;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getFlag() {
		return flag;
	}

	/**
	 * 根据 flag 的取值，返回所对应的中文名称
	 * 
	 * @return
	 */
	public String getFlagName() {
		String result = "<font color='FF0000'>\u6570\u636e\u9519\u8bef</font>";//数据错误
		try {
			if (Staff.FLAG_USE.equalsIgnoreCase(flag.trim())) {
				result = "<font color='" + Constant.COLOR_GREEN
						+ "'>\u6709\u6548</font>";//有效
			} else if (Staff.FLAG_FORBID.equalsIgnoreCase(flag.trim())) {
				result = "<font color='" + Constant.COLOR_RED
						+ "'>\u7981\u7528</font>";//禁用
			} else if (Staff.FLAG_DELETE.equalsIgnoreCase(flag.trim())) {
				result = "<font color='" + Constant.COLOR_GREY
						+ "'>\u5df2\u5220\u9664</font>";//已删除
			}
		} catch (Exception ex) {

		}

		return result;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getChsName() {
		return chsName;
	}

	public void setChsName(String chsName) {
		this.chsName = chsName;
	}

	public String getEngName() {
		return engName;
	}

	public void setEngName(String engName) {
		this.engName = engName;
	}

	public String getEmailAddr() {
		return emailAddr;
	}

	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}

	public String getOfficePhone() {
		return officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public Timestamp getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Timestamp loginDate) {
		this.loginDate = loginDate;
	}
}
