package com.npc.lottery.periods.entity;

import java.io.Serializable;

public class HKPeriodsInfo implements Serializable{

 /**
     * 
     */
    private static final long serialVersionUID = 2856492147716310373L;

    // primary key
    private java.lang.Long ID;

    // fields
    private java.lang.String periodsNum; // 投注期数，形如20120203001，具有唯一性，编号规则为年月日+玩法当日期数序号
    private java.util.Date openQuotTime; // 开盘时间
    private java.util.Date lotteryTime; // 开奖时间
    private java.util.Date stopQuotTime; // 封盘时间
    private java.lang.Integer result1; // 第一球
    private java.lang.Integer result2;
    private java.lang.Integer result3;
    private java.lang.Integer result4;
    private java.lang.Integer result5;
    private java.lang.Integer result6;
    private java.lang.Integer result7;
    private java.lang.String state; // 盘期状态 盘期状态，取值如下：0 — 已禁用；1 — 未开盘；2 — 已开盘；3— 已封盘；4 — 已开奖。默认值为 1 — 未开盘
    private java.lang.Long createUser; // 创建人ID，对应人员表（TB_FRAME_STAFF）中的主键
    private java.util.Date createTime; // 创建时间
    private String periodsInfo;     //期数
    private String lotteryTimeInfo; //开奖时间
    private Integer autoStopOuot;//提前封盘的时间
    
    
    public java.lang.Long getID() {
        return ID;
    }
    public void setID(java.lang.Long iD) {
        ID = iD;
    }
    public java.lang.String getPeriodsNum() {
        return periodsNum;
    }
    public void setPeriodsNum(java.lang.String periodsNum) {
        this.periodsNum = periodsNum;
    }
    public java.util.Date getOpenQuotTime() {
        return openQuotTime;
    }
    public void setOpenQuotTime(java.util.Date openQuotTime) {
        this.openQuotTime = openQuotTime;
    }
    public java.util.Date getLotteryTime() {
        return lotteryTime;
    }
    public void setLotteryTime(java.util.Date lotteryTime) {
        this.lotteryTime = lotteryTime;
    }
    public java.util.Date getStopQuotTime() {
        return stopQuotTime;
    }
    public void setStopQuotTime(java.util.Date stopQuotTime) {
        this.stopQuotTime = stopQuotTime;
    }
    public java.lang.Integer getResult1() {
        return result1;
    }
    public void setResult1(java.lang.Integer result1) {
        this.result1 = result1;
    }
    public java.lang.Integer getResult2() {
        return result2;
    }
    public void setResult2(java.lang.Integer result2) {
        this.result2 = result2;
    }
    public java.lang.Integer getResult3() {
        return result3;
    }
    public void setResult3(java.lang.Integer result3) {
        this.result3 = result3;
    }
    public java.lang.Integer getResult4() {
        return result4;
    }
    public void setResult4(java.lang.Integer result4) {
        this.result4 = result4;
    }
    public java.lang.Integer getResult5() {
        return result5;
    }
    public void setResult5(java.lang.Integer result5) {
        this.result5 = result5;
    }
    public java.lang.Integer getResult6() {
        return result6;
    }
    public void setResult6(java.lang.Integer result6) {
        this.result6 = result6;
    }
    public java.lang.Integer getResult7() {
        return result7;
    }
    public void setResult7(java.lang.Integer result7) {
        this.result7 = result7;
    }
    public java.lang.String getState() {
        return state;
    }
    public void setState(java.lang.String state) {
        this.state = state;
    }
    public java.lang.Long getCreateUser() {
        return createUser;
    }
    public void setCreateUser(java.lang.Long createUser) {
        this.createUser = createUser;
    }
    public java.util.Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }
    public String getPeriodsInfo() {
        return periodsInfo;
    }
    public void setPeriodsInfo(String periodsInfo) {
        this.periodsInfo = periodsInfo;
    }
    public String getLotteryTimeInfo() {
        return lotteryTimeInfo;
    }
    public void setLotteryTimeInfo(String lotteryTimeInfo) {
        this.lotteryTimeInfo = lotteryTimeInfo;
    }
    public Integer getAutoStopOuot() {
        return autoStopOuot;
    }
    public void setAutoStopOuot(Integer autoStopOuot) {
        this.autoStopOuot = autoStopOuot;
    }
    
}
