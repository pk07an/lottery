package com.npc.lottery.odds.entity;

import java.io.Serializable;


public class OpenPlayOdds implements Serializable{
	private static final long serialVersionUID = -2474457381634761707L;
	
	private Long ID;
	private java.lang.String shopsCode;
	private java.lang.Integer autoOddsQuotas;
	private java.math.BigDecimal autoOdds;
	private java.lang.String oddsType;
	private java.math.BigDecimal lowestOdds;
	private java.math.BigDecimal openingOdds;
	private java.util.Date openingUpdateDate;
	private java.lang.Long openingUpdateUser;
	private java.math.BigDecimal bigestOdds;
	private java.math.BigDecimal cutOddsB;
	private java.math.BigDecimal cutOddsC;
	private java.lang.Long createUser;
	private java.util.Date createTime;
	
	public Long getID() {
		return ID;
	}
	public void setID(Long ID) {
		this.ID = ID;
	}
	public java.lang.String getShopsCode() {
		return shopsCode;
	}
	public java.lang.Integer getAutoOddsQuotas() {
		return autoOddsQuotas;
	}
	public java.math.BigDecimal getAutoOdds() {
		return autoOdds;
	}
	public java.lang.String getOddsType() {
		return oddsType;
	}
	public java.math.BigDecimal getLowestOdds() {
		return lowestOdds;
	}
	public java.math.BigDecimal getOpeningOdds() {
		return openingOdds;
	}
	public java.util.Date getOpeningUpdateDate() {
		return openingUpdateDate;
	}
	public java.lang.Long getOpeningUpdateUser() {
		return openingUpdateUser;
	}
	public java.math.BigDecimal getBigestOdds() {
		return bigestOdds;
	}
	public java.math.BigDecimal getCutOddsB() {
		return cutOddsB;
	}
	public java.math.BigDecimal getCutOddsC() {
		return cutOddsC;
	}
	public java.lang.Long getCreateUser() {
		return createUser;
	}
	public java.util.Date getCreateTime() {
		return createTime;
	}
	public void setShopsCode(java.lang.String shopsCode) {
		this.shopsCode = shopsCode;
	}
	public void setAutoOddsQuotas(java.lang.Integer autoOddsQuotas) {
		this.autoOddsQuotas = autoOddsQuotas;
	}
	public void setAutoOdds(java.math.BigDecimal autoOdds) {
		this.autoOdds = autoOdds;
	}
	public void setOddsType(java.lang.String oddsType) {
		this.oddsType = oddsType;
	}
	public void setLowestOdds(java.math.BigDecimal lowestOdds) {
		this.lowestOdds = lowestOdds;
	}
	public void setOpeningOdds(java.math.BigDecimal openingOdds) {
		this.openingOdds = openingOdds;
	}
	public void setOpeningUpdateDate(java.util.Date openingUpdateDate) {
		this.openingUpdateDate = openingUpdateDate;
	}
	public void setOpeningUpdateUser(java.lang.Long openingUpdateUser) {
		this.openingUpdateUser = openingUpdateUser;
	}
	public void setBigestOdds(java.math.BigDecimal bigestOdds) {
		this.bigestOdds = bigestOdds;
	}
	public void setCutOddsB(java.math.BigDecimal cutOddsB) {
		this.cutOddsB = cutOddsB;
	}
	public void setCutOddsC(java.math.BigDecimal cutOddsC) {
		this.cutOddsC = cutOddsC;
	}
	public void setCreateUser(java.lang.Long createUser) {
		this.createUser = createUser;
	}
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

}