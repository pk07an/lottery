package com.npc.lottery.sysmge.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.npc.lottery.common.action.BaseLotteryAction;
import com.npc.lottery.member.entity.PlayType;
import com.npc.lottery.util.PlayTypeUtils;

public class ShopsPlayOddsLog extends BaseLotteryAction {

	/**
	 * ID
	 */
	public Long ID;
	/**
	 * 店鋪號碼
	 */
	public String shopCode;
	/**
	 * 賠率的類型編碼
	 */
	public String playTypeCode;
	public String playTypeCodeName;
	public String oddsType;
	public String oddsTypeName;
	public String oddsTypeNameLog;
	public String oddsTypeX;
	/**
	 * 更新前賠率
	 */
	public BigDecimal realOddsOrigin;
	/**
	 * 更新前時間
	 */
	public Date realUpdateDateOrigin;
	/**
	 * 之前的更新人
	 */
	public Integer realUpdateUserOrigin;
	/**
	 * 更新后賠率
	 */
	public BigDecimal realOddsNew;
	/**
	 * 記錄日誌時間
	 */
	public Date realUpdateDateNew;
	/**
	 * 日誌對應的用戶
	 */
	public Integer realUpdateUserNew;
	/**
	 * 備註
	 */
	public String remark;
	
	/**
	 * 當前盤期
	 */
	public String periodsNum;
	/**
	 * IP地址
	 */
	public String ip;
	/**
	 * IP归属地
	 */
	public String ipBelongTo;
	
	
	public PlayType playType;
	/**
	 * 上次更新人對應的登錄帳號
	 */
	public ManagerStaff realUpdateUserOriginAccount;
	/**
	 * 日志記錄人對應的登錄帳號
	 */
	public ManagerStaff realUpdateUserNewAccount;
	/**
	 * 1:自动降配日志，2手动修改日志
	 */
	public String type;
	
	public PlayType getPlayType() {
		return playType;
	}
	public void setPlayType(PlayType playType) {
		this.playType = playType;
	}
	public Long getID() {
		return ID;
	}
	public void setID(Long iD) {
		ID = iD;
	}
	public String getShopCode() {
		return shopCode;
	}
	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}
	public String getPlayTypeCode() {
		return playTypeCode;
	}
	public String getPlayTypeCodeName() {
		return PlayTypeUtils.getTypeCodeNameOdd(playTypeCode, realOddsNew, BigDecimal.ZERO, "", "",0);
	}
	public void setPlayTypeCodeName(String playTypeCodeName) {
		this.playTypeCodeName = playTypeCodeName;
	}
	public void setPlayTypeCode(String playTypeCode) {
		this.playTypeCode = playTypeCode;
	}
	public String getOddsType() {
		//return PlayTypeUtils.getOddTypeName(oddsType);
		return oddsType;
	}
	public String getOddsTypeName() {
		return PlayTypeUtils.getOddTypeName(oddsType);
	}
	
	public void setOddsType(String oddsType) {
		this.oddsType = oddsType;
	}
	public String getOddsTypeX() {
		return oddsTypeX;
	}
	public void setOddsTypeX(String oddsTypeX) {
		this.oddsTypeX = oddsTypeX;
	}
	public BigDecimal getRealOddsOrigin() {
		return realOddsOrigin;
	}
	public void setRealOddsOrigin(BigDecimal realOddsOrigin) {
		this.realOddsOrigin = realOddsOrigin;
	}
	public Date getRealUpdateDateOrigin() {
		return realUpdateDateOrigin;
	}
	public void setRealUpdateDateOrigin(Date realUpdateDateOrigin) {
		this.realUpdateDateOrigin = realUpdateDateOrigin;
	}
	public Integer getRealUpdateUserOrigin() {
		return realUpdateUserOrigin;
	}
	public void setRealUpdateUserOrigin(Integer realUpdateUserOrigin) {
		this.realUpdateUserOrigin = realUpdateUserOrigin;
	}
	public BigDecimal getRealOddsNew() {
		return realOddsNew;
	}
	public void setRealOddsNew(BigDecimal realOddsNew) {
		this.realOddsNew = realOddsNew;
	}
	public Date getRealUpdateDateNew() {
		return realUpdateDateNew;
	}
	public void setRealUpdateDateNew(Date realUpdateDateNew) {
		this.realUpdateDateNew = realUpdateDateNew;
	}
	public Integer getRealUpdateUserNew() {
		return realUpdateUserNew;
	}
	public void setRealUpdateUserNew(Integer realUpdateUserNew) {
		this.realUpdateUserNew = realUpdateUserNew;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getPeriodsNum() {
		return periodsNum;
	}
	public void setPeriodsNum(String periodsNum) {
		this.periodsNum = periodsNum;
	}
	public String getIp() {
		return ip;
	}
	public String getIpBelongTo() {
		return this.getIpAddress(ip);
	}
	public ManagerStaff getRealUpdateUserNewAccount() {
		return realUpdateUserNewAccount;
	}
	public void setRealUpdateUserNewAccount(ManagerStaff realUpdateUserNewAccount) {
		this.realUpdateUserNewAccount = realUpdateUserNewAccount;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public ManagerStaff getRealUpdateUserOriginAccount() {
		return realUpdateUserOriginAccount;
	}
	public void setRealUpdateUserOriginAccount(
			ManagerStaff realUpdateUserOriginAccount) {
		this.realUpdateUserOriginAccount = realUpdateUserOriginAccount;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOddsTypeNameLog() {
		String a = PlayTypeUtils.getOddTypeNameLog(oddsType);
		return a;
	}
	public void setOddsTypeNameLog(String oddsTypeNameLog) {
		this.oddsTypeNameLog = oddsTypeNameLog;
	}
	
}
