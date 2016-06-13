package com.npc.lottery.member.entity;

import java.io.Serializable;
import java.sql.Date;
public class PlayAmount  implements Serializable{
	private static final long serialVersionUID = 1L;

	    //primary key
		private Long id;

		private String typeCode;
		private String playType;
		private String periodsNum;
		private Double moneyAmount;
		private Date  updateTime;
		private String shopCode;
		private String commissionType;
		
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public java.lang.String getTypeCode() {
			return typeCode;
		}
		public void setTypeCode(java.lang.String typeCode) {
			this.typeCode = typeCode;
		}
		public String getPlayType() {
			return playType;
		}
		public void setPlayType(String playType) {
			this.playType = playType;
		}
		public String getPeriodsNum() {
			return periodsNum;
		}
		public void setPeriodsNum(String periodsNum) {
			this.periodsNum = periodsNum;
		}
		
		public Date getUpdateTime() {
			return updateTime;
		}
		public void setUpdateTime(Date updateTime) {
			this.updateTime = updateTime;
		}
		public Double getMoneyAmount() {
			return moneyAmount;
		}
		public void setMoneyAmount(Double moneyAmount) {
			this.moneyAmount = moneyAmount;
		}
		public String getShopCode() {
			return shopCode;
		}
		public void setShopCode(String shopCode) {
			this.shopCode = shopCode;
		}
		public String getCommissionType() {
			return commissionType;
		}
		public void setCommissionType(String commissionType) {
			this.commissionType = commissionType;
		}
		
		
			
			
		}
	
		
		