package com.npc.lottery.statreport.entity;

import java.io.Serializable;

/**
 * 
 * 分类报表实体类
 * 
 */
public class ClassReportPetList implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	private Long ID;
	
	private Long userID;//用户ID

    private String userType;//用户类型

    private Long turnover;//成交笔数

    private Double amount;//投注总额

    private Double memberAmount;//会员输赢

    private Double subordinateAmountWin;//应收下线的输赢
    
    private Double subordinateAmountBackWater;//应收下线的退水

    private Double realWin = null;//实占输赢

    private Double realBackWater = null;//实占退水

    private Double moneyRateChief;//总监实占注额

    private Double moneyRateBranch;//分公司实占注额

    private Double moneyRateStockholder;//股东实占注额

    private Double moneyRateGenAgent;//总代理实占注额

    private Double moneyRateAgent;//代理实占注额

    private Double commission = null;//赚取退水
    
    private Double rateMoney = 0D;//实占金额
    
    private String commissionType;
    
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

	public Double getSubordinateAmountWin() {
		return subordinateAmountWin;
	}

	public void setSubordinateAmountWin(Double subordinateAmountWin) {
		this.subordinateAmountWin = subordinateAmountWin;
	}

	public Double getSubordinateAmountBackWater() {
		return subordinateAmountBackWater;
	}

	public void setSubordinateAmountBackWater(Double subordinateAmountBackWater) {
		this.subordinateAmountBackWater = subordinateAmountBackWater;
	}

	public Double getRealWin() {
		return realWin;
	}

	public void setRealWin(Double realWin) {
		this.realWin = realWin;
	}

	public Double getRealBackWater() {
		return realBackWater;
	}

	public void setRealBackWater(Double realBackWater) {
		this.realBackWater = realBackWater;
	}

	public Double getMoneyRateChief() {
		return moneyRateChief;
	}

	public void setMoneyRateChief(Double moneyRateChief) {
		this.moneyRateChief = moneyRateChief;
	}

	public Double getMoneyRateBranch() {
		return moneyRateBranch;
	}

	public void setMoneyRateBranch(Double moneyRateBranch) {
		this.moneyRateBranch = moneyRateBranch;
	}

	public Double getMoneyRateStockholder() {
		return moneyRateStockholder;
	}

	public void setMoneyRateStockholder(Double moneyRateStockholder) {
		this.moneyRateStockholder = moneyRateStockholder;
	}

	public Double getMoneyRateGenAgent() {
		return moneyRateGenAgent;
	}

	public void setMoneyRateGenAgent(Double moneyRateGenAgent) {
		this.moneyRateGenAgent = moneyRateGenAgent;
	}

	public Double getMoneyRateAgent() {
		return moneyRateAgent;
	}

	public void setMoneyRateAgent(Double moneyRateAgent) {
		this.moneyRateAgent = moneyRateAgent;
	}

	public Double getCommission() {
		return commission;
	}

	public void setCommission(Double commission) {
		this.commission = commission;
	}

	public Double getRateMoney() {
		return rateMoney;
	}

	public void setRateMoney(Double rateMoney) {
		this.rateMoney = rateMoney;
	}

	public String getCommissionType() {
		return commissionType;
	}

	public void setCommissionType(String commissionType) {
		this.commissionType = commissionType;
	}

	public java.sql.Date getBettingDate() {
		return bettingDate;
	}

	public void setBettingDate(java.sql.Date bettingDate) {
		this.bettingDate = bettingDate;
	}
    

}
