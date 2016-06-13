package com.npc.lottery.sysmge.entity;

import java.util.Date;

public class BossLog {

	private Long ID;
	/**
	 * 記錄日誌信息
	 */
	private String logMessage;
	/**
	 * 日誌狀態 0：初始化，1：已刪除（已處理）
	 */
	private String logState = "0";
	/**
	 * 日誌嚴重級別
	 */
	private String logLevel= "1";
	/**
	 * 日誌shijian
	 */
	private Date createDate = new Date();
	
	
	public Long getID() {
		return ID;
	}
	public void setID(Long iD) {
		ID = iD;
	}
	public String getLogMessage() {
		return logMessage;
	}
	public void setLogMessage(String logMessage) {
		this.logMessage = logMessage;
	}
	public String getLogState() {
		return logState;
	}
	public void setLogState(String logState) {
		this.logState = logState;
	}
	public String getLogLevel() {
		return logLevel;
	}
	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
	
}
