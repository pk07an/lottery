package com.npc.lottery.replenish.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class AutoReplenishSetVO implements Serializable {
 
   private static final long serialVersionUID = 1L;
   private java.math.BigDecimal totalMoney = BigDecimal.ZERO;//注额*占成=实占金额
   private Integer lowestMoney = 0;//起补额度
   private String autoReplenishType;
   private String autoReplenishTypeName;
   private String userType;
   private Long userId;
   private Integer moneyLimit=0; 
   private String typeCode;
   private String attribute;
   
public java.math.BigDecimal getTotalMoney() {
	return totalMoney;
}
public String getAutoReplenishType() {
	return autoReplenishType;
}
public String getAutoReplenishTypeName() {
	return autoReplenishTypeName;
}
public String getUserType() {
	return userType;
}
public Long getUserId() {
	return userId;
}
public Integer getMoneyLimit() {
	return moneyLimit;
}
public String getTypeCode() {
	return typeCode;
}
public void setTypeCode(String typeCode) {
	this.typeCode = typeCode;
}
public String getAttribute() {
	return attribute;
}
public void setAttribute(String attribute) {
	this.attribute = attribute;
}
public void setUserType(String userType) {
	this.userType = userType;
}
public void setUserId(Long userId) {
	this.userId = userId;
}
public void setMoneyLimit(Integer moneyLimit) {
	this.moneyLimit = moneyLimit;
}
public void setTotalMoney(java.math.BigDecimal totalMoney) {
	this.totalMoney = totalMoney;
}
public void setAutoReplenishType(String autoReplenishType) {
	this.autoReplenishType = autoReplenishType;
}
public Integer getLowestMoney() {
	return lowestMoney;
}
public void setLowestMoney(Integer lowestMoney) {
	this.lowestMoney = lowestMoney;
}
}