package com.npc.lottery.replenish.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class CommissionVO implements Serializable {
 
   private static final long serialVersionUID = 1L;
   private String playFinalType;
   private String commissionType;
   private BigDecimal commissionA;
   private BigDecimal commissionB; //此处设为String类型是因为在查询报表时此字段的格式会存放字符型，如?|?|?
   private BigDecimal commissionC;
   
	public String getPlayFinalType() {
		return playFinalType;
	}
	public String getCommissionType() {
		return commissionType;
	}
	public BigDecimal getCommissionA() {
		return commissionA;
	}
	public BigDecimal getCommissionB() {
		return commissionB;
	}
	public BigDecimal getCommissionC() {
		return commissionC;
	}
	public void setPlayFinalType(String playFinalType) {
		this.playFinalType = playFinalType;
	}
	public void setCommissionType(String commissionType) {
		this.commissionType = commissionType;
	}
	public void setCommissionA(BigDecimal commissionA) {
		this.commissionA = commissionA;
	}
	public void setCommissionB(BigDecimal commissionB) {
		this.commissionB = commissionB;
	}
	public void setCommissionC(BigDecimal commissionC) {
		this.commissionC = commissionC;
	}
   
}
