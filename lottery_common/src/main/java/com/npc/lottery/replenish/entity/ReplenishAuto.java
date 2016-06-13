package com.npc.lottery.replenish.entity;

import java.math.BigDecimal;
import java.util.Date;


public class ReplenishAuto{

	
	private Long ID;
	private Long shopsID;
	private String type;
	private Integer moneyLimit=0;
	private Integer createUser;
	private Date createTime=new Date();
	private String typeCode;
	private Integer moneyRep=2;
	private String state="0";
	private String createUserType;
	private BigDecimal trueMoney = BigDecimal.ZERO;
	
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
	public Integer getMoneyLimit() {
		return moneyLimit;
	}
	public void setMoneyLimit(Integer moneyLimit) {
		this.moneyLimit = moneyLimit;
	}
	public Integer getCreateUser() {
		return createUser;
	}
	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public Integer getMoneyRep() {
		return moneyRep;
	}
	public void setMoneyRep(Integer moneyRep) {
		this.moneyRep = moneyRep;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCreateUserType() {
		return createUserType;
	}
	public void setCreateUserType(String createUserType) {
		this.createUserType = createUserType;
	}
	public Long getShopsID() {
		return shopsID;
	}
	public void setShopsID(Long shopsID) {
		this.shopsID = shopsID;
	}
	public BigDecimal getTrueMoney() {
		return trueMoney.setScale(0,BigDecimal.ROUND_HALF_UP);
	}
	public void setTrueMoney(BigDecimal trueMoney) {
		this.trueMoney = trueMoney;
	}
	
	
	
}