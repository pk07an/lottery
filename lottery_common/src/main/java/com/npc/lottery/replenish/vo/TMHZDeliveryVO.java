package com.npc.lottery.replenish.vo;

import java.math.BigDecimal;

/**
 *	特碼匯總 投注總額 bean 
 * @author Administrator
 *
 */
public class TMHZDeliveryVO {

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
	private Integer moneyTeMaA=0;
	private Integer moneyTeMaB=0;
	/**
	 * 單雙
	 */
	private Integer moneyTeMaDS=0;
	/**
	 * 大小
	 */
	private Integer moneyTeMaDX=0;
	/**
	 * 合數單雙
	 */
	private Integer moneyTeMaHSDS=0;
	/**
	 * 特尾大小
	 */
	private Integer moneyTeMaTWDX=0;
	/**
	 * 色波
	 */
	private Integer moneyTeMaSB=0;
	/**
	 * 生肖
	 */
	private Integer moneyTeMaSX=0;
	/**
	 * 半波
	 */
	private Integer moneyTeMaBB=0;
	/**
	 * 六肖
	 */
	private Integer moneyTeMaLX=0;
	/**
	 * 盈虧
	 */
	private BigDecimal moneyYingKui = BigDecimal.ZERO;
	/**
	 * 特碼A 的賠率 ：用於計算補貨
	 */
	private BigDecimal oddsRealTMA;
	/**
	 * 補貨額
	 */
	private BigDecimal moneyBuHuo = BigDecimal.ZERO;
	
	/**
	 * 开此球可赢金额
	 */
	private BigDecimal canWinMoney = BigDecimal.ZERO;
	
	/**
	 * 开此球可赢退水
	 */
	private BigDecimal canWinTs = BigDecimal.ZERO;
	
	public String getBallNum() {
		return ballNum;
	}
	public void setBallNum(String ballNum) {
		this.ballNum = ballNum;
	}
	public Integer getDeliveryCount() {
		return deliveryCount;
	}
	public void setDeliveryCount(Integer deliveryCount) {
		this.deliveryCount = deliveryCount;
	}
	public Integer getMoneyTeMaA() {
		return moneyTeMaA;
	}
	public void setMoneyTeMaA(Integer moneyTeMaA) {
		this.moneyTeMaA = moneyTeMaA;
	}
	public Integer getMoneyTeMaB() {
		return moneyTeMaB;
	}
	public void setMoneyTeMaB(Integer moneyTeMaB) {
		this.moneyTeMaB = moneyTeMaB;
	}
	public Integer getMoneyTeMaDS() {
		return moneyTeMaDS;
	}
	public void setMoneyTeMaDS(Integer moneyTeMaDS) {
		this.moneyTeMaDS = moneyTeMaDS;
	}
	public Integer getMoneyTeMaDX() {
		return moneyTeMaDX;
	}
	public void setMoneyTeMaDX(Integer moneyTeMaDX) {
		this.moneyTeMaDX = moneyTeMaDX;
	}
	public Integer getMoneyTeMaHSDS() {
		return moneyTeMaHSDS;
	}
	public void setMoneyTeMaHSDS(Integer moneyTeMaHSDS) {
		this.moneyTeMaHSDS = moneyTeMaHSDS;
	}
	public Integer getMoneyTeMaTWDX() {
		return moneyTeMaTWDX;
	}
	public void setMoneyTeMaTWDX(Integer moneyTeMaTWDX) {
		this.moneyTeMaTWDX = moneyTeMaTWDX;
	}
	public Integer getMoneyTeMaSB() {
		return moneyTeMaSB;
	}
	public void setMoneyTeMaSB(Integer moneyTeMaSB) {
		this.moneyTeMaSB = moneyTeMaSB;
	}
	public Integer getMoneyTeMaSX() {
		return moneyTeMaSX;
	}
	public void setMoneyTeMaSX(Integer moneyTeMaSX) {
		this.moneyTeMaSX = moneyTeMaSX;
	}
	public Integer getMoneyTeMaBB() {
		return moneyTeMaBB;
	}
	public void setMoneyTeMaBB(Integer moneyTeMaBB) {
		this.moneyTeMaBB = moneyTeMaBB;
	}
	public Integer getMoneyTeMaLX() {
		return moneyTeMaLX;
	}
	public void setMoneyTeMaLX(Integer moneyTeMaLX) {
		this.moneyTeMaLX = moneyTeMaLX;
	}
	public BigDecimal getMoneyYingKui() {
		return moneyYingKui;
	}
	public void setMoneyYingKui(BigDecimal moneyYingKui) {
		this.moneyYingKui = moneyYingKui;
	}
	public String getBallNumColour() {
		return ballNumColour;
	}
	public void setBallNumColour(String ballNumColour) {
		this.ballNumColour = ballNumColour;
	}
	public BigDecimal getOddsRealTMA() {
		return oddsRealTMA;
	}
	public void setOddsRealTMA(BigDecimal oddsRealTMA) {
		this.oddsRealTMA = oddsRealTMA;
	}
	public BigDecimal getMoneyBuHuo() {
		return moneyBuHuo;
	}
	public void setMoneyBuHuo(BigDecimal moneyBuHuo) {
		this.moneyBuHuo = moneyBuHuo;
	}
	public BigDecimal getCanWinMoney() {
		return canWinMoney;
	}
	public void setCanWinMoney(BigDecimal canWinMoney) {
		this.canWinMoney = canWinMoney;
	}
	public BigDecimal getCanWinTs() {
		return canWinTs;
	}
	public void setCanWinTs(BigDecimal canWinTs) {
		this.canWinTs = canWinTs;
	}
	
	
	
}
