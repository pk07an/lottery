package com.npc.lottery.statreport.entity;

import java.io.Serializable;

/**
 * 
 * 交收报表实体类
 * 
 */
public class DeliveryReportRList implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

    private Long ID;
    
    private Long userID;//用户ID

    private String userType;//用户类型
    
    private Long turnover;//成交笔数

    private Double amount;//投注总额

    private Double memberAmount;//会员输赢

    private Double winBackWater;//赚取退水--用于统计补出
    
    private Double backWaterResult;//退水后结果--用于统计补出
    
    private java.sql.Date bettingDate;
    
	public Long getID() {
		return ID;
	}

	public void setID(Long iD) {
		ID = iD;
	}

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Long getTurnover() {
		return turnover;
	}

	public void setTurnover(Long turnover) {
		this.turnover = turnover;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getMemberAmount() {
		return memberAmount;
	}

	public void setMemberAmount(Double memberAmount) {
		this.memberAmount = memberAmount;
	}

	public Double getWinBackWater() {
		return winBackWater;
	}

	public void setWinBackWater(Double winBackWater) {
		this.winBackWater = winBackWater;
	}

	public Double getBackWaterResult() {
		return backWaterResult;
	}

	public void setBackWaterResult(Double backWaterResult) {
		this.backWaterResult = backWaterResult;
	}

	public java.sql.Date getBettingDate() {
		return bettingDate;
	}

	public void setBettingDate(java.sql.Date bettingDate) {
		this.bettingDate = bettingDate;
	}

   
}
