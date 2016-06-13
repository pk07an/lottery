package com.npc.lottery.replenish.vo;

import java.math.BigDecimal;

/**
 *	正特碼 投注總額 bean 
 * @author Administrator
 *
 */
public class ZTMDeliveryVO {

	/**
	 * 號球
	 */
	private String ballNum;
	/**
	 * 號球 顏色 加入標籤
	 */
	private String ballNumColour;
	/**
	 * 投注筆數
	 */
	private Integer deliveryCount=0;
	/**
	 * 單雙
	 */
	private Integer moneyZTMDS=0;
	/**
	 * 大小
	 */
	private Integer moneyZTMDX=0;
	/**
	 * 合數單雙
	 */
	private Integer moneyZTMHSDS=0;
	/**
	 * 色波
	 */
	private Integer moneyZTMSB=0;
	/**
	 * 特碼
	 */
	private Integer moneyZTMTM=0;
	/**
	 * 特碼  賠率
	 */
	private BigDecimal oddsRealZTM = BigDecimal.ZERO;
	/**
	 * 盈虧
	 */
	private BigDecimal moneyYingKui = BigDecimal.ZERO;
	/**
	 * 補貨額
	 */
	private BigDecimal moneyBuHuo = BigDecimal.ZERO;

	public String getBallNum() {
		return ballNum;
	}
	public void setBallNum(String ballNum) {
		this.ballNum = ballNum;
	}
	public String getBallNumColour() {
		return ballNumColour;
	}
	public void setBallNumColour(String ballNumColour) {
		this.ballNumColour = ballNumColour;
	}
	public Integer getDeliveryCount() {
		return deliveryCount;
	}
	public void setDeliveryCount(Integer deliveryCount) {
		this.deliveryCount = deliveryCount;
	}
	public Integer getMoneyZTMDS() {
		return moneyZTMDS;
	}
	public void setMoneyZTMDS(Integer moneyZTMDS) {
		this.moneyZTMDS = moneyZTMDS;
	}
	public Integer getMoneyZTMDX() {
		return moneyZTMDX;
	}
	public void setMoneyZTMDX(Integer moneyZTMDX) {
		this.moneyZTMDX = moneyZTMDX;
	}
	public Integer getMoneyZTMHSDS() {
		return moneyZTMHSDS;
	}
	public void setMoneyZTMHSDS(Integer moneyZTMHSDS) {
		this.moneyZTMHSDS = moneyZTMHSDS;
	}
	public Integer getMoneyZTMSB() {
		return moneyZTMSB;
	}
	public void setMoneyZTMSB(Integer moneyZTMSB) {
		this.moneyZTMSB = moneyZTMSB;
	}
	public BigDecimal getMoneyYingKui() {
		return moneyYingKui;
	}
	public void setMoneyYingKui(BigDecimal moneyYingKui) {
		this.moneyYingKui = moneyYingKui;
	}
	public BigDecimal getOddsRealZTM() {
		return oddsRealZTM;
	}
	public void setOddsRealZTM(BigDecimal oddsRealZTM) {
		this.oddsRealZTM = oddsRealZTM;
	}
	public BigDecimal getMoneyBuHuo() {
		return moneyBuHuo;
	}
	public void setMoneyBuHuo(BigDecimal moneyBuHuo) {
		this.moneyBuHuo = moneyBuHuo;
	}
	public Integer getMoneyZTMTM() {
		return moneyZTMTM;
	}
	public void setMoneyZTMTM(Integer moneyZTMTM) {
		this.moneyZTMTM = moneyZTMTM;
	}
}
