package com.npc.lottery.statreport.entity;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * 报表上显示的补货信息
 * 
 */
public class ReplenishReport implements Serializable {

    private Long replenishUserId;//补货人

    private Long turnover = 0L;//笔数

    private Double replenishAmount = 0.0;//补货金额

    private Double replenishValidAmount = 0.0;//有效金额

    private Double replenishWin = 0.0;//补货输赢

    private Double backWater = 0.0;//退水

    private Double backWaterResult = 0.0;//退水后结果

    private String userType;//补货人类型

    public Long getReplenishUserId() {
        return replenishUserId;
    }

    public void setReplenishUserId(Long replenishUserId) {
        this.replenishUserId = replenishUserId;
    }

    public Long getTurnover() {
        return turnover;
    }

    public void setTurnover(Long turnover) {
        this.turnover = turnover;
    }

    public Double getReplenishAmount() {
        return replenishAmount;
    }

    public String getReplenishAmountDis() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(replenishAmount);
    }

    public void setReplenishAmount(Double replenishAmount) {
        this.replenishAmount = replenishAmount;
    }

    public Double getReplenishWin() {
        return ((long) (replenishWin * 100)) / 100.0;
    }

    public String getReplenishWinDis() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(replenishWin);
    }

    public void setReplenishWin(Double replenishWin) {
        this.replenishWin = replenishWin;
    }

    public Double getBackWater() {
        return ((long) (backWater * 100)) / 100.0;
    }

    public String getBackWaterDis() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(backWater);
    }

    public void setBackWater(Double backWater) {
        this.backWater = backWater;
    }

    public Double getBackWaterResult() {
        return backWaterResult;
    }

    public String getBackWaterResultDis() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(backWaterResult);
    }

    public void setBackWaterResult(Double backWaterResult) {
        this.backWaterResult = backWaterResult;
    }

    public Double getReplenishValidAmount() {
        return replenishValidAmount;
    }

    public String getReplenishValidAmountDis() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(replenishValidAmount);
    }

    public void setReplenishValidAmount(Double replenishValidAmount) {
        this.replenishValidAmount = replenishValidAmount;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
