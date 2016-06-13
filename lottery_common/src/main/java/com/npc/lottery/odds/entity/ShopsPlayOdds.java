package com.npc.lottery.odds.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


public class ShopsPlayOdds implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -2216849134593737556L;

	// primary key
	private Long id;
	private String shopsCode;
	private String playTypeCode;

	private java.math.BigDecimal realOdds;
	private Date realUpdateDate;
	private Long realUpdateUser;
	private String remark;
	private String state;
	private String oddsType;
	private String oddsTypeX;
	private java.math.BigDecimal realOddsB;
	private java.math.BigDecimal realOddsC;
	
	public String getShopsCode() {
		return shopsCode;
	}
	public void setShopsCode(String shopsCode) {
		this.shopsCode = shopsCode;
	}
	public String getPlayTypeCode() {
		return playTypeCode;
	}
	public void setPlayTypeCode(String playType) {
		this.playTypeCode = playType;
	}
	
	public java.math.BigDecimal getRealOdds() {
		return realOdds.doubleValue()>0?realOdds:BigDecimal.ZERO;
	}
	public void setRealOdds(java.math.BigDecimal realOdds) {
		this.realOdds = realOdds.doubleValue()>0?realOdds:BigDecimal.ZERO;
	}
	public Date getRealUpdateDate() {
		return realUpdateDate;
	}
	public void setRealUpdateDate(Date realUpdateDate) {
		this.realUpdateDate = realUpdateDate;
	}
	public Long getRealUpdateUser() {
		return realUpdateUser;
	}
	public void setRealUpdateUser(Long realUpdateUser) {
		this.realUpdateUser = realUpdateUser;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getOddsType() {
		return oddsType;
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
	public java.math.BigDecimal getRealOddsB() {
		return realOddsB.doubleValue()>0?realOddsB:BigDecimal.ZERO;
	}
	public void setRealOddsB(java.math.BigDecimal realOddsB) {
		this.realOddsB = realOddsB.doubleValue()>0?realOddsB:BigDecimal.ZERO;
	}
	public java.math.BigDecimal getRealOddsC() {
		return realOddsC.doubleValue()>0?realOddsC:BigDecimal.ZERO;
	}
	public void setRealOddsC(java.math.BigDecimal realOddsC) {
		this.realOddsC = realOddsC.doubleValue()>0?realOddsC:BigDecimal.ZERO;;
	}
	
	
	
	
	
	
	
	
	

}
