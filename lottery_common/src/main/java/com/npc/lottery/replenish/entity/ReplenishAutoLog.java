package com.npc.lottery.replenish.entity;

import java.util.Date;

import com.npc.lottery.boss.entity.ShopsInfo;
import com.npc.lottery.member.entity.PlayType;
import com.npc.lottery.sysmge.entity.ManagerStaff;


public class ReplenishAutoLog{

	
	private Long ID;
	private Long shopID;
	private String type;
	private String typeBH;
	private String typeCode;
	private Integer money = 0;
	private Integer createUserID;
	private java.lang.String periodsNum;
	private Date createTime = new Date(System.currentTimeMillis());
	public PlayType playType;
	public ShopsInfo shopsInfo;
	
	/**
	 * 記錄人對應的登錄帳號 Eric
	 */
	public ManagerStaff userAccount;
	
	public ReplenishAutoLog()
	{
	}
	public ReplenishAutoLog(Long shopID,String type,String typeCode,Integer money,Integer createUserID,String periodsNum,String typeBH)
	{
		this.setShopID(shopID);
		this.setType(type);
		this.setTypeCode(typeCode);
		this.setMoney(money);
		this.setCreateUserID(createUserID);
		this.setPeriodsNum(periodsNum);
		this.setTypeBH(typeBH);
	}
	public Long getID() {
		return ID;
	}
	public void setID(Long iD) {
		ID = iD;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public Integer getMoney() {
		return money;
	}
	public void setMoney(Integer money) {
		this.money = money;
	}
	public Integer getCreateUserID() {
		return createUserID;
	}
	public void setCreateUserID(Integer createUserID) {
		this.createUserID = createUserID;
	}
	public java.lang.String getPeriodsNum() {
		return periodsNum;
	}
	public void setPeriodsNum(java.lang.String periodsNum) {
		this.periodsNum = periodsNum;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getShopID() {
		return shopID;
	}
	public void setShopID(Long shopID) {
		this.shopID = shopID;
	}
	public PlayType getPlayType() {
		return playType;
	}
	public void setPlayType(PlayType playType) {
		this.playType = playType;
	}
	public ShopsInfo getShopsInfo() {
		return shopsInfo;
	}
	public void setShopsInfo(ShopsInfo shopsInfo) {
		this.shopsInfo = shopsInfo;
	}
	public ManagerStaff getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(ManagerStaff userAccount) {
		this.userAccount = userAccount;
	}
	public String getTypeBH() {
		return typeBH;
	}
	public void setTypeBH(String typeBH) {
		this.typeBH = typeBH;
	}
	
	
}