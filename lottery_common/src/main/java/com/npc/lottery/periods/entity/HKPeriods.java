package com.npc.lottery.periods.entity;

import java.io.Serializable;
import java.util.Date;

public class HKPeriods implements Serializable{

    
 /**
     * 记录各个商铺各自所对应的盘期信息（香港六合彩）内容
     */
    private static final long serialVersionUID = -912167839564657829L;
// primary key
    private java.lang.Long ID;          //
    private Long periodsInfoID;         //盘期ID
    private String shopsCode;           //商铺号码
    private String  periodsState;       //状态
    private Long modifyUser;            //编辑人员
    private Date modifyTime;            //编辑时间
    private java.util.Date openQuotTime; // 开盘时间
    private java.util.Date stopQuotTime; // 开奖时间
    private java.lang.String periodsNum;
    private Integer autoStopOuot;//提前封盘的时间
    
    
    
    public Integer getAutoStopOuot() {
        return autoStopOuot;
    }
    public void setAutoStopOuot(Integer autoStopOuot) {
        this.autoStopOuot = autoStopOuot;
    }
    public java.util.Date getOpenQuotTime() {
        return openQuotTime;
    }
    public void setOpenQuotTime(java.util.Date openQuotTime) {
        this.openQuotTime = openQuotTime;
    }
    public java.util.Date getStopQuotTime() {
        return stopQuotTime;
    }
    public void setStopQuotTime(java.util.Date stopQuotTime) {
        this.stopQuotTime = stopQuotTime;
    }
    public java.lang.Long getID() {
        return ID;
    }
    public void setID(java.lang.Long iD) {
        ID = iD;
    }
    public Long getPeriodsInfoID() {
        return periodsInfoID;
    }
    public void setPeriodsInfoID(Long periodsInfoID) {
        this.periodsInfoID = periodsInfoID;
    }
    public String getShopsCode() {
        return shopsCode;
    }
    public void setShopsCode(String shopsCode) {
        this.shopsCode = shopsCode;
    }
    public String getPeriodsState() {
        return periodsState;
    }
    public void setPeriodsState(String periodsState) {
        this.periodsState = periodsState;
    }
    public Long getModifyUser() {
        return modifyUser;
    }
    public void setModifyUser(Long modifyUser) {
        this.modifyUser = modifyUser;
    }
    public Date getModifyTime() {
        return modifyTime;
    }
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
	public java.lang.String getPeriodsNum() {
		return periodsNum;
	}
	public void setPeriodsNum(java.lang.String periodsNum) {
		this.periodsNum = periodsNum;
	}
    
    
}
