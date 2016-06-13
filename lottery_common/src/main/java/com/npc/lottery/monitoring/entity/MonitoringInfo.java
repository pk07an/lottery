package com.npc.lottery.monitoring.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;

public class MonitoringInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5601702674770956708L;
	public MonitoringInfo() {
	}
	public MonitoringInfo(int id, String playType, String shops,
			String periods, String tranType, int status, String describe,
			int when) {
		super();
		this.id = id;
		this.playType = playType;
		this.shops = shops;
		this.periods = periods;
		this.tranType = tranType;
		this.status = status;
		this.describe = describe;
		this.createDate = new Date();
		this.when = when;
	}
	private int id;
	private String playType; //玩法类型
	private String shops;//商铺
	private String periods;//盘期
	private String tranType;//事务类型
	private int status;//状态   0失败  1成功
	private String describe;//描述
	private Date createDate;//创建时间
	private int when;//用时（秒）
	
	@Id
	@Column(name="ID",length=10)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Column(name="PLAY_TYPE",length=20)
	public String getPlayType() {
		return playType;
	}
	public void setPlayType(String playType) {
		this.playType = playType;
	}
	@Column(name="SHOPS",length=20)
	public String getShops() {
		return shops;
	}
	public void setShops(String shops) {
		this.shops = shops;
	}
	@Column(name="PERIODS",length=20)
	public String getPeriods() {
		return periods;
	}
	public void setPeriods(String periods) {
		this.periods = periods;
	}
	@Column(name="TRAN_TYPE",length=20)
	public String getTranType() {
		return tranType;
	}
	public void setTranType(String tranType) {
		this.tranType = tranType;
	}
	@Column(name="STATUS",length=1)
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Column(name="DESCRIBE",length=200)
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	@Column(name="CREATE_DATE",nullable=false)
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@Column(name="WHEN",length=10)
	public int getWhen() {
		return when;
	}
	public void setWhen(int when) {
		this.when = when;
	}

	
}
