package com.npc.lottery.odds.vo;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import com.npc.lottery.util.PlayTypeUtils;

public class OddDetail {
	
	public String playTypeCode;
	public String playTypeCodeName;
	public String oddsType;
	public String oddsTypeName;
	public String oddsTypeX;
	/**
	 * 更新后賠率
	 */
	public BigDecimal realOddsNew;
	/**
	 * 記錄日誌時間
	 */
	public Timestamp realUpdateDateNew;
	
	public String getPlayTypeCode() {
		return playTypeCode;
	}
	public String getPlayTypeCodeName() {
		return PlayTypeUtils.getPlayTypeFinalName(playTypeCode) + "@<font color='red'>" + realOddsNew.setScale(2,BigDecimal.ROUND_HALF_UP).toString() + "</font>";
	}
	public String getOddsType() {
		return oddsType;
	}
	public String getOddsTypeName() {
		return  PlayTypeUtils.getOddTypeName(oddsType);
		//return playTypeCode;
	}
	public String getOddsTypeX() {
		return oddsTypeX;
	}
	public void setPlayTypeCode(String playTypeCode) {
		this.playTypeCode = playTypeCode;
	}
	public void setPlayTypeCodeName(String playTypeCodeName) {
		this.playTypeCodeName = playTypeCodeName;
	}
	public void setOddsType(String oddsType) {
		this.oddsType = oddsType;
	}
	public void setOddsTypeName(String oddsTypeName) {
		this.oddsTypeName = oddsTypeName;
	}
	public void setOddsTypeX(String oddsTypeX) {
		this.oddsTypeX = oddsTypeX;
	}
	public BigDecimal getRealOddsNew() {
		return realOddsNew;
	}
	public Timestamp getRealUpdateDateNew() {
		return realUpdateDateNew;
	}
	public void setRealOddsNew(BigDecimal realOddsNew) {
		this.realOddsNew = realOddsNew;
	}
	public void setRealUpdateDateNew(Timestamp realUpdateDateNew) {
		this.realUpdateDateNew = realUpdateDateNew;
	}

}
