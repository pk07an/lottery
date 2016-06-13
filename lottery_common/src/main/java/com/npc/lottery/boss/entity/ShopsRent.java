package com.npc.lottery.boss.entity;

import java.io.Serializable;
import java.util.Date;

public class ShopsRent implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1155757072697915497L;
	private Long ID;
	private ShopsInfo shopsInfo;
	private Date expityTime;
	private Date expityWarningTime;
	private Long lastModifyUser;
	private Date lastModifyDate;
	private String remark;
	
	public Long getID() {
		return ID;
	}
	public void setID(Long iD) {
		ID = iD;
	}

	public Date getExpityTime() {
		return expityTime;
	}
	public void setExpityTime(Date expityTime) {
		this.expityTime = expityTime;
	}
	public Date getExpityWarningTime() {
		return expityWarningTime;
	}
	public void setExpityWarningTime(Date expityWarningTime) {
		this.expityWarningTime = expityWarningTime;
	}
	public Long getLastModifyUser() {
		return lastModifyUser;
	}
	public void setLastModifyUser(Long lastModifyUser) {
		this.lastModifyUser = lastModifyUser;
	}
	public Date getLastModifyDate() {
		return lastModifyDate;
	}
	public void setLastModifyDate(Date lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public ShopsInfo getShopsInfo() {
		return shopsInfo;
	}
	public void setShopsInfo(ShopsInfo shopsInfo) {
		this.shopsInfo = shopsInfo;
	}

}
