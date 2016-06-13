/**
 * 
 */
package com.npc.lottery.manage.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Administrator
 *  自动降赔率VO
 */
public class PeriodAutoOdds {

	
	public Long id;
	public String shopCode;
	public String type;
	public String name;
	public BigDecimal autoOdds;
	public Integer createUserID;
	public Date createDate;
	
	 
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getShopCode() {
		return shopCode;
	}
	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getCreateUserID() {
		return createUserID;
	}
	public void setCreateUserID(Integer createUserID) {
		this.createUserID = createUserID;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public BigDecimal getAutoOdds() {
		return autoOdds;
	}
	public void setAutoOdds(BigDecimal autoOdds) {
		this.autoOdds = autoOdds;
	}
	
	
}
