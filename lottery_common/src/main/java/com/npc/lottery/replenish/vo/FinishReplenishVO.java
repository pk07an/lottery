package com.npc.lottery.replenish.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.npc.lottery.replenish.entity.Replenish;

public class FinishReplenishVO implements Serializable {
 
   private static final long serialVersionUID = 1L;
   private String orderNo;
   private String detail;
   private Integer money;
   private BigDecimal winMoney;
   private String result;
   private String winState; //状态
   private String winStateName;//状态名称
   private String quickNo;//快速補貨的序號
   
public String getOrderNo() {
	return orderNo;
}
public String getDetail() {
	return detail;
}
public Integer getMoney() {
	return money;
}
public BigDecimal getWinMoney() {
	return winMoney;
}
public String getResult() {
	return result;
}
public void setOrderNo(String orderNo) {
	this.orderNo = orderNo;
}
public void setDetail(String detail) {
	this.detail = detail;
}
public void setMoney(Integer money) {
	this.money = money;
}
public void setWinMoney(BigDecimal winMoney) {
	this.winMoney = winMoney;
}
public void setResult(String result) {
	this.result = result;
}
public String getWinState() {
	return winState;
}
public void setWinState(String winState) {
	this.winState = winState;
}
public String getWinStateName() {
	if(Replenish.WIN_STATE_CANCEL.equals(this.winState) || Replenish.WIN_STATE_CANCEL_UNSETTLED.equals(this.winState)){
		winStateName="<font color='red'>已注銷</font>";
	}else if(Replenish.WIN_STATE_STOP.equals(this.winState) || Replenish.WIN_STATE_STOP_UNSETTLED.equals(this.winState)){
		winStateName="<font color='red'>停開</font>";
	}else{
		winStateName="成功補出";
	}
	return winStateName;
}
public void setWinStateName(String winStateName) {
	this.winStateName = winStateName;
}
public String getQuickNo() {
	return quickNo;
}
public void setQuickNo(String quickNo) {
	this.quickNo = quickNo;
}

   

   
}
