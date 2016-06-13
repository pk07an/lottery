package com.npc.lottery.model;

import java.util.Date;

public class ShopLotteryLog {

	private long id;
	private String shopCode;
	private String periodNum;
	private String status;
	private Date createDate;

	private String playType;

	public long getId() {
		return id;
	}

	public String getShopCode() {
		return shopCode;
	}

	public String getPeriodNum() {
		return periodNum;
	}

	public String getStatus() {
		return status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	public void setPeriodNum(String periodNum) {
		this.periodNum = periodNum;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getPlayType() {
		return playType;
	}

	public void setPlayType(String playType) {
		this.playType = playType;
	}
}
