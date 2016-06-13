package com.npc.lottery.replenish.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class ReplenishLMVO implements Serializable {
 
   private static final long serialVersionUID = 1L;
   private String playFinalType;
   private java.math.BigDecimal realOdds = BigDecimal.ZERO;
   private Integer money = 0;
   private java.math.BigDecimal loseMoney =  BigDecimal.ZERO;  //亏盈     在連碼里虧盈就是注額*賠率
   private java.math.BigDecimal commission; 
   private java.math.BigDecimal rate;
   private Integer account;   //发生的笔数
   private String attribute; //投注組合
   private Integer RMoney = 0;//已補貨的金額
   private java.math.BigDecimal RLoseMoney = BigDecimal.ZERO;  //已補貨的派彩額
   
public String getPlayFinalType() {
	return playFinalType;
}
public Integer getMoney() {
	return money;
}
public java.math.BigDecimal getLoseMoney() {
	return loseMoney;
}
public void setPlayFinalType(String playFinalType) {
	this.playFinalType = playFinalType;
}
public java.math.BigDecimal getRealOdds() {
	return realOdds;
}
public void setRealOdds(java.math.BigDecimal realOdds) {
	this.realOdds = realOdds;
}
public void setMoney(Integer money) {
	this.money = money;
}
public void setLoseMoney(java.math.BigDecimal loseMoney) {
	this.loseMoney = loseMoney;
}
public java.math.BigDecimal getCommission() {
	return commission;
}
public java.math.BigDecimal getRate() {
	return rate;
}
public void setCommission(java.math.BigDecimal commission) {
	this.commission = commission;
}
public void setRate(java.math.BigDecimal rate) {
	this.rate = rate;
}
public Integer getAccount() {
	return account;
}
public void setAccount(Integer account) {
	this.account = account;
}
public String getAttribute() {
	return attribute;
}
public void setAttribute(String attribute) {
	this.attribute = attribute;
}
public Integer getRMoney() {
	return RMoney;
}
public java.math.BigDecimal getRLoseMoney() {
	return RLoseMoney;
}
public void setRMoney(Integer rMoney) {
	RMoney = rMoney;
}
public void setRLoseMoney(java.math.BigDecimal rLoseMoney) {
	RLoseMoney = rLoseMoney;
}

   
}
