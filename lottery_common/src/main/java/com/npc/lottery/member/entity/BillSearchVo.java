package com.npc.lottery.member.entity;


public class BillSearchVo {

	
	private String subType="HKLHC";
	private String playType="";
	private String bettingDateStart;
	private String bettingDateEnd;
	private String orderNum;
	private String lotteryTypeOff;
	private String lotteryTypeOn;
	private String lotteryType;
	
	private String memberID;
	/**
	 * 分級篩選 name
	 */
	private String memberName;
	
	private String eduMin;
	
	private String eduMax;
	private String resMin;
	private String resMax;
	private String winState;
	private String periodsNum; // 期數 如：GDKLSF_20121012001
	private String radioBox; // 按日期 按時間選項
	/**
	 * 分級篩選 用户：会员、股东、分公司
	 */
	private String userType;
	private String createTime;
	
	private String billType = "1";//会员注单:1,补货注单:0
	
	private Long chiefID; //只有总监才有注单搜索功能，一个商铺只有一个总监，为了只查询本商铺的注单
	
	
	public String getSubType() {
		return subType;
	}
	public void setSubType(String subType) {
		this.subType = subType;
	}
	public String getPlayType() {
		return playType;
	}
	public void setPlayType(String playType) {
		this.playType = playType;
	}
	public String getBettingDateStart() {
		return bettingDateStart;
	}
	public void setBettingDateStart(String bettingDateStart) {
		this.bettingDateStart = bettingDateStart;
	}
	public String getBettingDateEnd() {
		return bettingDateEnd;
	}
	public void setBettingDateEnd(String bettingDateEnd) {
		this.bettingDateEnd = bettingDateEnd;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getLotteryTypeOff() {
		return lotteryTypeOff;
	}
	public void setLotteryTypeOff(String lotteryTypeOff) {
		this.lotteryTypeOff = lotteryTypeOff;
	}
	public String getLotteryTypeOn() {
		return lotteryTypeOn;
	}
	public void setLotteryTypeOn(String lotteryTypeOn) {
		this.lotteryTypeOn = lotteryTypeOn;
	}
	public String getLotteryType() {
		return lotteryType;
	}
	public void setLotteryType(String lotteryType) {
		this.lotteryType = lotteryType;
	}
	public String getMemberID() {
		return memberID;
	}
	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}
	public String getEduMin() {
		return eduMin;
	}
	public void setEduMin(String eduMin) {
		this.eduMin = eduMin;
	}
	public String getEduMax() {
		return eduMax;
	}
	public void setEduMax(String eduMax) {
		this.eduMax = eduMax;
	}
	public String getResMin() {
		return resMin;
	}
	public void setResMin(String resMin) {
		this.resMin = resMin;
	}
	public String getResMax() {
		return resMax;
	}
	public void setResMax(String resMax) {
		this.resMax = resMax;
	}
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
	public Long getChiefID() {
		return chiefID;
	}
	public void setChiefID(Long chiefID) {
		this.chiefID = chiefID;
	}
	public String getWinState() {
		return winState;
	}
	public void setWinState(String winState) {
		this.winState = winState;
	}
	public String getPeriodsNum() {
		return periodsNum;
	}
	public void setPeriodsNum(String periodsNum) {
		this.periodsNum = periodsNum;
	}
	public String getRadioBox() {
		return radioBox;
	}
	public void setRadioBox(String radioBox) {
		this.radioBox = radioBox;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
}
