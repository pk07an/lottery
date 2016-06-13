package com.npc.lottery.member.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.npc.lottery.boss.entity.ShopsInfo;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.util.PlayTypeUtils;


public  class BaseBet  implements Serializable,Comparable<BaseBet>  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// primary key
	private Integer id;
    private String  typeCode;
	// fields
	private String playType;
	private String subTypeName;
	private String finalTypeName;
	private Integer money;
	private BigDecimal winMoney;
	private Long bettingUserId;
	private String bettingUserType;//补货人的类型
	private String periodsNum;
	private String plate;
	private Date bettingDate;
	private String winState;
	private BigDecimal winAmount;
	private BigDecimal odds;
	private BigDecimal odds2;
	private Date updateDate;
	private String remark;
	private String attribute;
	private String orderNo;
	private Long chiefStaff;
	private Long branchStaff;
	private Long stockholderStaff;
	private Long genAgenStaff;
	private Long agentStaff;
	private String chiefStaffName;
	private String branchStaffName;
	private String stockholderStaffName;
	private String genAgenStaffName;
	private String agentStaffName;
	private String bettingUserName;
	private String rate;
	private Double branchCommission;
	private Double stockHolderCommission;
	private Double genAgenCommission;
	private Double agentStaffCommission;
	private Double memberCommission;
	
	private Integer chiefRate;
	private Integer branchRate;
	private Integer stockHolderRate;
	private Integer genAgenRate;
	private Integer agentStaffRate;
	private Integer compoundNum=1;
	
	private String shopCode;
	
	private String splitAttribute;
	
	private String selectedOdds;
	
	private Integer count=1;
	
	private String commissionType;
	
	private String tableName="";
	
	private String userName; //用户名  ERIC
	
	private String winStateName;//状态名称
	/**
	 * 注單種類  1 会员 0 捕获 作为条件
	 */
	private String billType;
	/**
	 * 狀態 1 未結算 0 已結算 作为条件
	 */
	private String lotteryType;
	
	private String betError;
	
	private ShopsInfo shopInfo;
	
	public String getWinStateName() {
		if("4".equals(this.winState)){
			winStateName="已注銷";
		}else if("5".equals(this.winState)){
			winStateName="停開";
		}else{
			winStateName="--";
		}
		return winStateName;
	}
	public void setWinStateName(String winStateName) {
		this.winStateName = winStateName;
	}
	
	private String hklmOdd;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPlayType() {
		return playType;
	}
	public void setPlayType(String playType) {
		this.playType = playType;
	}
	public Integer getMoney() {
		return money;
	}
	public void setMoney(Integer money) {
		this.money = money;
	}
	public BigDecimal getWinMoney() {
		return winMoney;
	}
	public void setWinMoney(BigDecimal winMoney) {
		this.winMoney = winMoney;
	}
	public Long getBettingUserId() {
		return bettingUserId;
	}
	public void setBettingUserId(Long bettingUserId) {
		this.bettingUserId = bettingUserId;
	}
	public String getBettingUserType() {
		return bettingUserType;
	}
	public void setBettingUserType(String bettingUserType) {
		this.bettingUserType = bettingUserType;
	}
	public String getPeriodsNum() {
		return periodsNum;
	}
	public void setPeriodsNum(String periodsNum) {
		this.periodsNum = periodsNum;
	}
	public String getPlate() {
		return plate;
	}
	public void setPlate(String plate) {
		this.plate = plate;
	}
	public Date getBettingDate() {
		return bettingDate;
	}
	public void setBettingDate(Date bettingDate) {
		this.bettingDate = bettingDate;
	}
	public String getWinState() {
		return winState;
	}
	public void setWinState(String winState) {
		this.winState = winState;
	}
	public BigDecimal getWinAmount() {
		return winAmount;
	}
	public void setWinAmount(BigDecimal winAmount) {
		this.winAmount = winAmount;
	}
	public BigDecimal getOdds() {
		return odds;
	}
	public void setOdds(BigDecimal odds) {
		this.odds = odds;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getAttribute() {
		return attribute;
	}
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getSubTypeName() {
		return subTypeName;
	}
	public void setSubTypeName(String subTypeName) {
		this.subTypeName = subTypeName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFinalTypeName() {
		return finalTypeName;
	}
	public void setFinalTypeName(String finalTypeName) {
		this.finalTypeName = finalTypeName;
	}
	
	
	@Override
	public int compareTo(BaseBet o) {
		return o.orderNo.compareTo(this.orderNo);
	}
	public Long getChiefStaff() {
		return chiefStaff;
	}
	public void setChiefStaff(Long chiefStaff) {
		this.chiefStaff = chiefStaff;
	}
	public Long getBranchStaff() {
		return branchStaff;
	}
	public void setBranchStaff(Long branchStaff) {
		this.branchStaff = branchStaff;
	}
	public Long getStockholderStaff() {
		return stockholderStaff;
	}
	public void setStockholderStaff(Long stockholderStaff) {
		this.stockholderStaff = stockholderStaff;
	}
	public Long getGenAgenStaff() {
		return genAgenStaff;
	}
	public void setGenAgenStaff(Long genAgenStaff) {
		this.genAgenStaff = genAgenStaff;
	}
	public Long getAgentStaff() {
		return agentStaff;
	}
	public void setAgentStaff(Long agentStaff) {
		this.agentStaff = agentStaff;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getHklmOdd() {
		return hklmOdd;
	}
	public void setHklmOdd(String hklmOdd) {
		this.hklmOdd = hklmOdd;
	}
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public Double getBranchCommission() {
		return branchCommission;
	}
	public void setBranchCommission(Double branchCommission) {
		this.branchCommission = branchCommission;
	}
	public Double getStockHolderCommission() {
		return stockHolderCommission;
	}
	public void setStockHolderCommission(Double stockHolderCommission) {
		this.stockHolderCommission = stockHolderCommission;
	}
	public Double getGenAgenCommission() {
		return genAgenCommission;
	}
	public void setGenAgenCommission(Double genAgenCommission) {
		this.genAgenCommission = genAgenCommission;
	}
	public Double getAgentStaffCommission() {
		return agentStaffCommission;
	}
	public void setAgentStaffCommission(Double agentStaffCommission) {
		this.agentStaffCommission = agentStaffCommission;
	}
	public Double getMemberCommission() {
		return memberCommission;
	}
	public void setMemberCommission(Double memberCommission) {
		this.memberCommission = memberCommission;
	}
	public Integer getChiefRate() {
		return chiefRate;
	}
	public void setChiefRate(Integer chiefRate) {
		this.chiefRate = chiefRate;
	}
	public Integer getBranchRate() {
		if(ManagerStaff.USER_TYPE_BRANCH.equals(this.bettingUserType)){
			return -100;
		}else{
			return branchRate;
		}
	}
	public void setBranchRate(Integer branchRate) {
		this.branchRate = branchRate;
	}
	public Integer getStockHolderRate() {
		if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(this.bettingUserType)){
			return -100;
		}else if(ManagerStaff.USER_TYPE_BRANCH.equals(this.bettingUserType)){
			return 0;
		}else{
			return stockHolderRate;
		}
	}
	public void setStockHolderRate(Integer stockHolderRate) {
		this.stockHolderRate = stockHolderRate;
	}
	public Integer getGenAgenRate() {
		if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(this.bettingUserType)){
			return -100;
		}else if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(this.bettingUserType)){
			return 0;
		}else if(ManagerStaff.USER_TYPE_BRANCH.equals(this.bettingUserType)){
			return 0;
		}else{
			return genAgenRate;
		}
	}
	public void setGenAgenRate(Integer genAgenRate) {
		this.genAgenRate = genAgenRate;
	}
	public Integer getAgentStaffRate() {
		if(ManagerStaff.USER_TYPE_AGENT.equals(this.bettingUserType)){
			return -100;
		}else if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(this.bettingUserType)){
			return 0;
		}else if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(this.bettingUserType)){
			return 0;
		}else if(ManagerStaff.USER_TYPE_BRANCH.equals(this.bettingUserType)){
			return 0;
		}else{
			return agentStaffRate;
		}
	}
	public void setAgentStaffRate(Integer agentStaffRate) {
		this.agentStaffRate = agentStaffRate;
	}
	public String getShopCode() {
		return shopCode;
	}
	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}
	public BigDecimal getOdds2() {
		return odds2;
	}
	public void setOdds2(BigDecimal odds2) {
		this.odds2 = odds2;
	}
	public Integer getCompoundNum() {
		return compoundNum;
	}
	public void setCompoundNum(Integer compoundNum) {
		this.compoundNum = compoundNum;
	}
	public String getSplitAttribute() {
		return splitAttribute;
	}
	public void setSplitAttribute(String splitAttribute) {
		this.splitAttribute = splitAttribute;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getSelectedOdds() {
		return selectedOdds;
	}
	public void setSelectedOdds(String selectedOdds) {
		this.selectedOdds = selectedOdds;
	}
	public String getCommissionType() {
		return commissionType;
	}
	public void setCommissionType(String commissionType) {
		this.commissionType = commissionType;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getChiefStaffName() {
		return chiefStaffName;
	}
	public void setChiefStaffName(String chiefStaffName) {
		this.chiefStaffName = chiefStaffName;
	}
	public String getBranchStaffName() {
		return branchStaffName;
	}
	public void setBranchStaffName(String branchStaffName) {
		this.branchStaffName = branchStaffName;
	}
	public String getStockholderStaffName() {
		return stockholderStaffName;
	}
	public void setStockholderStaffName(String stockholderStaffName) {
		this.stockholderStaffName = stockholderStaffName;
	}
	public String getGenAgenStaffName() {
		return genAgenStaffName;
	}
	public void setGenAgenStaffName(String genAgenStaffName) {
		this.genAgenStaffName = genAgenStaffName;
	}
	public String getAgentStaffName() {
		return agentStaffName;
	}
	public void setAgentStaffName(String agentStaffName) {
		this.agentStaffName = agentStaffName;
	}
	public String getBettingUserName() {
		return bettingUserName;
	}
	public void setBettingUserName(String bettingUserName) {
		this.bettingUserName = bettingUserName;
	}
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
	public String getLotteryType() {
		return lotteryType;
	}
	public void setLotteryType(String lotteryType) {
		this.lotteryType = lotteryType;
	}
	public String getBetError() {
		return betError;
	}
	public void setBetError(String betError) {
		this.betError = betError;
	}
	public ShopsInfo getShopInfo() {
		return shopInfo;
	}
	public void setShopInfo(ShopsInfo shopInfo) {
		this.shopInfo = shopInfo;
	}
	

		
	
	

}