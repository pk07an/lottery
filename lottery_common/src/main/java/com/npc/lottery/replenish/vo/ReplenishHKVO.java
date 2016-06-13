package com.npc.lottery.replenish.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class ReplenishHKVO implements Serializable {
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1831043644910329576L;
	/**
	 * type_code
	 */
	private String playTypeCode;
	/**
	 * 投注總額
	 */
	private Integer totalMoney=0;
	/**
	 * 投注總額 用於計算盈虧
	 */
	private BigDecimal totalMoneyYK = BigDecimal.ZERO;
	/**
	 * 投注的總次數
	 */
	private Integer totalCount=0;
	
	/**
	 * 只對6肖 表的atttibute 
	 */
	private String attributes;
	/**
	 * 占成
	 */
	private String rate = "0";
	
	/**
	 * 當前類型賠率
	 */
	private BigDecimal odds = BigDecimal.ZERO;
	/**
	 * 當前類型退水
	 */
	private BigDecimal commission = BigDecimal.ZERO;
	/**
	 * 當前類型盈虧
	 */
	private BigDecimal moneyYingKui = BigDecimal.ZERO;
	/**
	 * 補貨額
	 */
	private BigDecimal moneyBuHuo = BigDecimal.ZERO;
	/**
	 * 用於生肖連
	 */
	private String splitAttribute;
	/**
	 * 用於生肖連
	 */
	private String splitAttribute_CN;
	
	/**
	 * 當前類型的分别投注额*分别佣金（每注*當前傭金）
	 */
	private BigDecimal tsMoney = BigDecimal.ZERO;
	/**
	 * 當前類型d 分別投注额*分别赔率
	 */
	private BigDecimal oddsMoney = BigDecimal.ZERO;
	
	public String getPlayTypeCode() {
		return playTypeCode;
	}
	public void setPlayTypeCode(String playTypeCode) {
		this.playTypeCode = playTypeCode;
	}
	public Integer getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(Integer totalMoney) {
		this.totalMoney = totalMoney;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public String getAttributes() {
		return attributes;
	}
	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public BigDecimal getOdds() {
		return odds;
	}
	public void setOdds(BigDecimal odds) {
		this.odds = odds;
	}
	public BigDecimal getCommission() {
		return commission;
	}
	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}
	public BigDecimal getMoneyYingKui() {
		return moneyYingKui;
	}
	public void setMoneyYingKui(BigDecimal moneyYingKui) {
		this.moneyYingKui = moneyYingKui;
	}
	public BigDecimal getMoneyBuHuo() {
		return moneyBuHuo;
	}
	public void setMoneyBuHuo(BigDecimal moneyBuHuo) {
		this.moneyBuHuo = moneyBuHuo;
	}
	public String getSplitAttribute() {
		return splitAttribute;
	}
	public void setSplitAttribute(String splitAttribute) {
		this.splitAttribute = splitAttribute;
	}
	public String getSplitAttribute_CN() {
		return splitAttribute_CN;
	}
	public void setSplitAttribute_CN(String splitAttribute_CN) {
		this.splitAttribute_CN = splitAttribute_CN;
	}
	public BigDecimal getTsMoney() {
		return tsMoney;
	}
	public void setTsMoney(BigDecimal tsMoney) {
		this.tsMoney = tsMoney;
	}
	public BigDecimal getOddsMoney() {
		return oddsMoney;
	}
	public void setOddsMoney(BigDecimal oddsMoney) {
		this.oddsMoney = oddsMoney;
	}
	public BigDecimal getTotalMoneyYK() {
		return totalMoneyYK;
	}
	public void setTotalMoneyYK(BigDecimal totalMoneyYK) {
		this.totalMoneyYK = totalMoneyYK;
	}
 	
   

}
