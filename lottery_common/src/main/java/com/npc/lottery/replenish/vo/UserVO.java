package com.npc.lottery.replenish.vo;

import java.io.Serializable;

import com.npc.lottery.user.entity.ChiefStaffExt;

public class UserVO implements Serializable {
 
   private static final long serialVersionUID = 1L;
   private String account;
   private String userType;
	private String rateUser;
	private String nextRateColumn;
	private String commissionUser;
	private String outCommissionUser;
	private String inCommissionUser;
	private String nextColumn;
	
	private String replenishMent;//是否充許補貨
	private String report;//公司占  //是否开放报表 0显示 1，禁止
	private Integer chiefID; //总监ID
	private ChiefStaffExt chiefStaffExt;   //多个分公司对应一个总监
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getUserType() {
		return userType;
	}
	public String getRateUser() {
		return rateUser;
	}
	public String getNextRateColumn() {
		return nextRateColumn;
	}
	public void setNextRateColumn(String nextRateColumn) {
		this.nextRateColumn = nextRateColumn;
	}
	public String getCommissionUser() {
		return commissionUser;
	}
	public String getOutCommissionUser() {
		return outCommissionUser;
	}
	public String getInCommissionUser() {
		return inCommissionUser;
	}
	public String getNextColumn() {
		return nextColumn;
	}
	public void setNextColumn(String nextColumn) {
		this.nextColumn = nextColumn;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public void setRateUser(String rateUser) {
		this.rateUser = rateUser;
	}
	public void setCommissionUser(String commissionUser) {
		this.commissionUser = commissionUser;
	}
	public void setOutCommissionUser(String outCommissionUser) {
		this.outCommissionUser = outCommissionUser;
	}
	public void setInCommissionUser(String inCommissionUser) {
		this.inCommissionUser = inCommissionUser;
	}
	public String getReplenishMent() {
		return replenishMent;
	}
	public String getReport() {
		return report;
	}
	public void setReplenishMent(String replenishMent) {
		this.replenishMent = replenishMent;
	}
	public void setReport(String report) {
		this.report = report;
	}
	public Integer getChiefID() {
		return chiefID;
	}
	public ChiefStaffExt getChiefStaffExt() {
		return chiefStaffExt;
	}
	public void setChiefStaffExt(ChiefStaffExt chiefStaffExt) {
		this.chiefStaffExt = chiefStaffExt;
	}
	public void setChiefID(Integer chiefID) {
		this.chiefID = chiefID;
	}

   
}
