package com.npc.lottery.boss.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.SessionStatInfo;
import com.npc.lottery.user.entity.ChiefStaffExt;

public class ShopsInfo implements Serializable {
	private static final long serialVersionUID = 433768869291963264L;
	private Long ID;
	private String shopsCode;
	private String shopsName;
	private String state;
	private Long createUser;
	private Date createTime;
	private String css;
	private String remark;
	private Set<ShopsRent> shopsRent;
	private Long shopsRentID;
	private ChiefStaffExt chiefStaffExt;
	private Long inLineNum; 
	//add by peter
	private String enableBetDelete;
	private String enableBetCancel;
	
	/*private String sendMailAccount;
	private String sendMailPassword;
	private String sendMailSMTP;
	private String sendMailAddress;
	private String getMailAddress1;
	private String getMailAddress2;
	private String getMailAddress3;*/

	/**
     * 判断对应商铺的在线人数
     * 
     * @param safeCode  商铺编号
     * @return
     */
    public Long getInLineNum() {
    	return SessionStatInfo.inLineNum(this.shopsCode);
	}
    
    /**
     * 判断總監是否在线
     * 
     * @return
     */
    public boolean isInLine(){
        return SessionStatInfo.isInLine(chiefStaffExt.getManagerStaff().getAccount(), ManagerStaff.USER_TYPE_CHIEF);
    }

	public ChiefStaffExt getChiefStaffExt() {
		return chiefStaffExt;
	}

	public void setChiefStaffExt(ChiefStaffExt chiefStaffExt) {
		this.chiefStaffExt = chiefStaffExt;
	}

	public Long getID() {
		return ID;
	}

	public void setID(Long iD) {
		ID = iD;
	}

	public String getShopsCode() {
		return shopsCode;
	}

	public void setShopsCode(String shopsCode) {
		this.shopsCode = shopsCode;
	}

	public String getShopsName() {
		return shopsName;
	}

	public void setShopsName(String shopsName) {
		this.shopsName = shopsName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Long getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
	   return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getExpityTime() {		
		return ((ShopsRent) shopsRent.iterator().next()).getExpityTime();
	}
	
	public Date getExpityWarningTime() {		
		return ((ShopsRent) shopsRent.iterator().next()).getExpityWarningTime();
	}

	public Set<ShopsRent> getShopsRent() {
		return shopsRent;
	}

	public void setShopsRent(Set<ShopsRent> shopsRent) {
		this.shopsRent = shopsRent;
	}

	public Long getShopsRentID() {
		return ((ShopsRent) shopsRent.iterator().next()).getID();
	}

	public void setShopsRentID(Long shopsRentID) {
		this.shopsRentID = shopsRentID;
	}

	public String getEnableBetDelete() {
		return enableBetDelete;
	}

	public void setEnableBetDelete(String enableBetDelete) {
		this.enableBetDelete = enableBetDelete;
	}

	public String getEnableBetCancel() {
		return enableBetCancel;
	}

	public void setEnableBetCancel(String enableBetCancel) {
		this.enableBetCancel = enableBetCancel;
	}

	/*public String getSendMailAccount() {
		return sendMailAccount;
	}

	public void setSendMailAccount(String sendMailAccount) {
		this.sendMailAccount = sendMailAccount;
	}

	public String getSendMailPassword() {
		return sendMailPassword;
	}

	public void setSendMailPassword(String sendMailPassword) {
		this.sendMailPassword = sendMailPassword;
	}

	public String getSendMailSMTP() {
		return sendMailSMTP;
	}

	public void setSendMailSMTP(String sendMailSMTP) {
		this.sendMailSMTP = sendMailSMTP;
	}

	public String getSendMailAddress() {
		return sendMailAddress;
	}

	public void setSendMailAddress(String sendMailAddress) {
		this.sendMailAddress = sendMailAddress;
	}

	public String getGetMailAddress1() {
		return getMailAddress1;
	}

	public void setGetMailAddress1(String getMailAddress1) {
		this.getMailAddress1 = getMailAddress1;
	}

	public String getGetMailAddress2() {
		return getMailAddress2;
	}

	public void setGetMailAddress2(String getMailAddress2) {
		this.getMailAddress2 = getMailAddress2;
	}

	public String getGetMailAddress3() {
		return getMailAddress3;
	}

	public void setGetMailAddress3(String getMailAddress3) {
		this.getMailAddress3 = getMailAddress3;
	}*/

}
