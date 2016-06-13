package com.npc.lottery.replenish.vo;

import java.io.Serializable;

public class PlayTypeTotalVO implements Serializable {
 
   private static final long serialVersionUID = 1L;
   private String playFinalType;
   private java.math.BigDecimal loseMoneyTotal;
   private Integer betMoneyTotal;
   private java.math.BigDecimal commission; //此处设为String类型是因为在查询报表时此字段的格式会存放字符型，如?|?|?
   private java.math.BigDecimal rate;
   
	public String getPlayFinalType() {
		return playFinalType;
	}
	public java.math.BigDecimal getLoseMoneyTotal() {
		return loseMoneyTotal;
	}
	public Integer getBetMoneyTotal() {
		return betMoneyTotal;
	}
	public void setPlayFinalType(String playFinalType) {
		this.playFinalType = playFinalType;
	}
	public void setLoseMoneyTotal(java.math.BigDecimal loseMoneyTotal) {
		this.loseMoneyTotal = loseMoneyTotal;
	}
	public void setBetMoneyTotal(Integer betMoneyTotal) {
		this.betMoneyTotal = betMoneyTotal;
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

   
}
