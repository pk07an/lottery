package com.npc.lottery.replenish.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class FrmReplenishVO implements Serializable {
 
   private static final long serialVersionUID = 1L;
   private String playFinalType;
   private java.math.BigDecimal realOdds = BigDecimal.valueOf(0);
   private String money = "0";
   private String loseMoney = "0"; //赔额和亏盈
   private java.math.BigDecimal commission; //此处设为String类型是因为在查询报表时此字段的格式会存放字符型，如?|?|?
   private java.math.BigDecimal rate;
   private Integer account;  //发生的笔数
   
public String getPlayFinalType() {
	return playFinalType;
}
public String getMoney() {
	return money;
}
public String getLoseMoney() {
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
public void setMoney(String money) {
	this.money = money;
}
public void setLoseMoney(String loseMoney) {
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
   
}
