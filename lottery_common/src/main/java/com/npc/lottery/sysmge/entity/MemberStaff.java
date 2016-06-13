package com.npc.lottery.sysmge.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import com.npc.lottery.util.Tools;

/**
 * 会员用户基础表所对应的实体类，对应的数据库表为（TB_FRAME_MEMBER_STAFF）
 * 
 * @author none
 *
 */
public class MemberStaff implements Serializable {
    
    public static final String USER_TYPE_MEMBER = "9";//用户类型：会员用户
    
    public final static String FLAG_USE = "0";//状态：用户有效

    public final static String FLAG_FORBID = "1";//状态：禁用

    public final static String FLAG_FREEZE = "2";//状态：冻结

    public final static String FLAG_DELETE = "3";//状态：删除

    private Long ID;//ID

    private String account;//登录账号

    private String flag;//状态
    
    private String userType;//用户类型

    private Long userExtInfoId;//用户扩展信息ID

    private String userPwd;//用户密码

    private String chsName;//中文名字

    private String engName;//英文名字

    private String emailAddr;//eMail地址

    private String officePhone;//办公室电话

    private String mobilePhone;//移动电话

    private String fax;//传真

    private Date createDate;//创建时间

    private Date updateDate;//更新时间

    private Date loginDate;//最后登录时间

    private String loginIp;//最后登录IP

    private String comments;//备注
    private java.lang.Long parentStaffQry;
    private String parentStaffTypeQry;
    
    //add by peter
    private Date passwordUpdateDate;
    
    private String passwordResetFlag;//密码是否需要重新设置

    public String getParentStaffTypeQry() {
		return parentStaffTypeQry;
	}

	public void setParentStaffTypeQry(String parentStaffTypeQry) {
		this.parentStaffTypeQry = parentStaffTypeQry;
	}

	public java.lang.Long getParentStaffQry() {
		return parentStaffQry;
	}

	public void setParentStaffQry(java.lang.Long parentStaffQry) {
		this.parentStaffQry = parentStaffQry;
	}

	public Long getID() {
        return ID;
    }

    public void setID(Long iD) {
        ID = iD;
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

    public void setFlag(String flag) {
        this.flag = flag;
    }

	public String getUserType() {
        return userType;
    }
    
    public String getUserTypeName() {

        String result = "<font color='FF0000'>\u6570\u636e\u9519\u8bef</font>";//数据错误

        try {
            if (MemberStaff.USER_TYPE_MEMBER.equalsIgnoreCase(userType)) {
                result = "会员用户";
            } 
        } catch (Exception ex) {

        }

        return result;

    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Long getUserExtInfoId() {
        return userExtInfoId;
    }

    public void setUserExtInfoId(Long userExtInfoId) {
        this.userExtInfoId = userExtInfoId;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
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
    
    /**
     * 判断用户是否在线
     * 
     * @return
     */
    public boolean isInLine(){
        return SessionStatInfo.isInLine(this.account, this.userType);
    }

	public String getEncodeAccount() {
		if(account!=null)
		return Tools.encodeWithKey(account);
		else 
			return null;
	}
	 public String getEncodeId() {
			if(ID!=null)
				return Tools.encodeWithKey(ID+"");
				else 
					return null;
		}

	public Date getPasswordUpdateDate() {
		return passwordUpdateDate;
	}

	/**
	 * 默认设置当天的日期
	 * @param passwordUpdateDate
	 */
	public void setPasswordUpdateDate(Date passwordUpdateDate) {
		if (null == passwordUpdateDate) {
			this.passwordUpdateDate = new Date();
		} else {
			this.passwordUpdateDate = passwordUpdateDate;
		}
	}

	public String getPasswordResetFlag() {
		return passwordResetFlag;
	}

	/**
	 * 默认为Y
	 * @param passwordResetFlag
	 */
	public void setPasswordResetFlag(String passwordResetFlag) {
		if (StringUtils.isEmpty(passwordResetFlag)) {
			this.passwordResetFlag = "Y";
		} else {
			this.passwordResetFlag = passwordResetFlag;
		}
	}

    
}
