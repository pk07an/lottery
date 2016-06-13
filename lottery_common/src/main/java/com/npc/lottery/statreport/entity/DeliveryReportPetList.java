package com.npc.lottery.statreport.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 
 * 交收报表实体类
 * 
 */
public class DeliveryReportPetList implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

    private Long ID;
    
    private Long userID;//用户ID

    private String userType;//用户类型
    
    private Long bettingUserID;//用户ID
    
    private String bettingUserType;//用户类型
    
    private String parentUserType;//上级用户类型

    private String subordinate;//下级登陆账号

    private String userName;//用户名称

    private Long turnover;//成交笔数

    private Double amount;//投注总额

    private Double memberAmount;//会员输赢

    private Double subordinateAmountWin;//应收下线的输赢
    
    private Double subordinateAmountBackWater;//应收下线的退水

    private Double realWin;//实占输赢

    private Double realBackWater;//实占退水

    private Double moneyRateChief;//总监实占注额

    private Double moneyRateBranch;//分公司实占注额

    private Double moneyRateStockholder;//股东实占注额

    private Double moneyRateGenAgent;//总代理实占注额

    private Double moneyRateAgent;//代理实占注额

    private Double commission = null;//赚取退水
    
    private String commissionType;

    private Double rateMoney = 0D;//实占金额
    
    private java.sql.Date bettingDate;
    
    private Double realResultPer = (double) 0;//占货比


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

	public Long getBettingUserID() {
		return bettingUserID;
	}

	public void setBettingUserID(Long bettingUserID) {
		this.bettingUserID = bettingUserID;
	}

	public String getBettingUserType() {
		return bettingUserType;
	}

	public void setBettingUserType(String bettingUserType) {
		this.bettingUserType = bettingUserType;
	}

	public String getParentUserType() {
		return parentUserType;
	}

	public void setParentUserType(String parentUserType) {
		this.parentUserType = parentUserType;
	}

	public String getSubordinate() {
		return subordinate;
	}

	public void setSubordinate(String subordinate) {
		this.subordinate = subordinate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public String getCommissionType() {
		return commissionType;
	}

	public void setCommissionType(String commissionType) {
		this.commissionType = commissionType;
	}

	public Double getRateMoney() {
		return rateMoney;
	}

	public void setRateMoney(Double rateMoney) {
		this.rateMoney = rateMoney;
	}

	public java.sql.Date getBettingDate() {
		return bettingDate;
	}

	public void setBettingDate(java.sql.Date bettingDate) {
		this.bettingDate = bettingDate;
	}

	public Double getRealResultPer() {
		return realResultPer;
	}

	public void setRealResultPer(Double realResultPer) {
		this.realResultPer = realResultPer;
	}
	/**
     * 设置占货比
     * 
     * @param totalRealResult 实占注额的合计
     */
    public void calRealResultPer(Double totalRealResult) {
    	if(rateMoney==0 && totalRealResult==0){
    		this.realResultPer = 0D;
    	}else{
    		this.realResultPer = BigDecimal.valueOf(rateMoney).divide(BigDecimal.valueOf(totalRealResult),5,BigDecimal.ROUND_HALF_UP).doubleValue();
    	}
    }

    /**
     * 占货比
     * 
     * 实占注额/实占注额的合计
     * 此方法需要先执行calRealResultPerValue方法才能取到正确的值
     * 
     * @return
     */
    public String getRealResultPerDis() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(realResultPer * 100);
    }
   
}
