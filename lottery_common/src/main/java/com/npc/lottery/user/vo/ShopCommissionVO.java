package com.npc.lottery.user.vo;

import java.io.Serializable;

public class ShopCommissionVO implements Serializable {
 
   private static final long serialVersionUID = 1L;
   private java.lang.String finalPlayType; 
   private java.math.BigDecimal commission;
   
	public java.lang.String getFinalPlayType() {
		return finalPlayType;
	}
	public java.math.BigDecimal getCommission() {
		return commission;
	}
	public void setFinalPlayType(java.lang.String finalPlayType) {
		this.finalPlayType = finalPlayType;
	}
	public void setCommission(java.math.BigDecimal commission) {
		this.commission = commission;
	}       

   
}
