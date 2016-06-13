package com.npc.lottery.replenish.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.npc.lottery.util.PlayTypeUtils;

public class DetailVO implements Serializable,Comparable<DetailVO> {
 
   private static final long serialVersionUID = 1L;
   //注单号
   private String orderNo;
   //投注日期
   private Timestamp bettingDate = new Timestamp(System.currentTimeMillis()); 
   //投注日期只截取'星期一'的'一'
   private String bettingDateStr;
   //如果是总监就写总监出货，如果是管理就是:"某某角色+走飞"
   private String userType;
   
   private String parentUserType;//上级用户类型
   
   private String whoReplenish;
   //玩法大类
   private String playTypeName;
   //期数
   private String periodsNum;
   //会员帐号
   private String userName;
   //盘口
   private String plate;
   
   private String typeCode;
   
   private Double winAmount = (double) 0;//會員輸贏
   
   private Double rateWinAmount = (double) 0;//你的結果里的實占輸贏
   
   private Double rateBackWater = (double) 0;//你的結果里的實占退水
   //赔率
   private java.math.BigDecimal odds = BigDecimal.ZERO;
   //赔率2
   private java.math.BigDecimal odds2 = BigDecimal.ZERO;
   //下注金额
   private Integer money = 0;
   
   private Integer count = 0;
   
   private java.math.BigDecimal chiefOutCommission = BigDecimal.ZERO; //记录总监出货时的拥金
   private java.math.BigDecimal chiefCommission = BigDecimal.ZERO; //拥金
   private java.math.BigDecimal branchCommission = BigDecimal.ZERO; //拥金
   private java.math.BigDecimal stockCommission = BigDecimal.ZERO; //拥金
   private java.math.BigDecimal genAgentCommission = BigDecimal.ZERO; //拥金
   private java.math.BigDecimal agentCommission = BigDecimal.ZERO; //拥金
   
   //显示在界面上用的，为了把2转换为98显示出来
   private Double chiefOutCommissionDisplay; //记录总监出货时的拥金
   private Double chiefCommissionDisplay; //拥金
   private Double branchCommissionDisplay; //拥金
   private Double stockCommissionDisplay; //拥金
   private Double genAgentCommissionDisplay; //拥金
   private Double agentCommissionDisplay; //拥金
   
   private java.math.BigDecimal chiefRate = BigDecimal.ZERO; //占成
   private java.math.BigDecimal branchRate = BigDecimal.ZERO; //占成
   private java.math.BigDecimal stockRate = BigDecimal.ZERO; //占成
   private java.math.BigDecimal genAgentRate = BigDecimal.ZERO; //占成
   private java.math.BigDecimal agentRate = BigDecimal.ZERO; //占成
   
   private String chief; 
   private String branch; 
   private String stock; 
   private String genAgent; 
   private String agent; 
   
   private String attribute;   //纪录连码投注时所选择的球
   private String attributeChinaName;   //纪录连码投注时所选择的排列的中文翻譯
   
   private String attributeGroupMoney; //显示5x5组
   
   private String selectedOdds;
   //你的占成     注额*占成
   private java.math.BigDecimal rateMoney = BigDecimal.ZERO;
   
   private String winState;
   
public String getOrderNo() {
	return orderNo;
}
public String getUserType() {
	return userType;
}
public String getParentUserType() {
	return parentUserType;
}
public void setParentUserType(String parentUserType) {
	this.parentUserType = parentUserType;
}
public String getWhoReplenish() {
	if(null!=whoReplenish){
		return whoReplenish;
	}else{
		return "";
	}
}
public void setWhoReplenish(String whoReplenish) {
	this.whoReplenish = whoReplenish;
}
public void setUserType(String userType) {
	this.userType = userType;
}
public String getPlayTypeName() {
    return PlayTypeUtils.getPlayTypeName(typeCode);
}
public String getTypeCodeNameOdd() {
    String result = PlayTypeUtils.getTypeCodeNameOdd(typeCode, odds, odds2, attribute, selectedOdds,count);
    
    return result;
}
public String getTypeCodeNameOddForFile() {
	String result = PlayTypeUtils.getTypeCodeNameOddForFile(typeCode, odds, odds2, attribute, selectedOdds);
	
	return result;
}
public String getPeriodsNum() {
	return periodsNum;
}
public String getUserName() {
	return userName;
}
public String getPlate() {
	return plate;
}
public String getTypeCode() {
	return typeCode;
}
public Double getWinAmount() {
	return winAmount;
}
public String getWinAmountDis() {
	DecimalFormat df = new DecimalFormat("0");
    return df.format(BigDecimal.valueOf(this.winAmount).setScale(0,BigDecimal.ROUND_HALF_UP));
}
public Double getRateWinAmount() {
	return rateWinAmount;
}
public String getRateWinAmountDis() {
	DecimalFormat df = new DecimalFormat("0.0");
    return df.format(BigDecimal.valueOf(this.rateWinAmount).setScale(1,BigDecimal.ROUND_HALF_UP));
}
public Double getRateBackWater() {
	return rateBackWater;
}
public String getRateBackWaterDis() {
	DecimalFormat df = new DecimalFormat("0.0");
    return df.format(BigDecimal.valueOf(this.rateBackWater).setScale(1,BigDecimal.ROUND_HALF_UP));
}
public void setWinAmount(Double winAmount) {
	this.winAmount = winAmount;
}
public void setRateWinAmount(Double rateWinAmount) {
	this.rateWinAmount = rateWinAmount;
}
public void setRateBackWater(Double rateBackWater) {
	this.rateBackWater = rateBackWater;
}
public void setTypeCode(String typeCode) {
	this.typeCode = typeCode;
}
public java.math.BigDecimal getOdds() {
	return odds;
}
public java.math.BigDecimal getOdds2() {
	return odds2;
}
public void setOdds2(java.math.BigDecimal odds2) {
	this.odds2 = odds2;
}
public Integer getMoney() {
	return money;
}
public String getMoneyDis() {
	String str = null;
	if(this.attribute!=null && count>1){
		str = money/count + "x" + count + "組</br>" + money;
	}else{
		str = "" + money;
	}
    return str;
}
public java.math.BigDecimal getChiefOutCommission() {
	return chiefOutCommission;
}
public void setChiefOutCommission(java.math.BigDecimal chiefOutCommission) {
	this.chiefOutCommission = chiefOutCommission;
}
public java.math.BigDecimal getChiefCommission() {
	return chiefCommission;
}
public java.math.BigDecimal getBranchCommission() {
	return branchCommission;
}
public java.math.BigDecimal getStockCommission() {
	return stockCommission;
}
public java.math.BigDecimal getGenAgentCommission() {
	return genAgentCommission;
}
public java.math.BigDecimal getAgentCommission() {
	return agentCommission;
}
public Double getChiefOutCommissionDisplay() {
	return chiefOutCommissionDisplay;
}
public Double getChiefCommissionDisplay() {
	return this.chiefCommission.setScale(3,BigDecimal.ROUND_DOWN).doubleValue();
}
public Double getBranchCommissionDisplay() {
	return this.branchCommission.setScale(3,BigDecimal.ROUND_DOWN).doubleValue();
}
public Double getStockCommissionDisplay() {
	return this.stockCommission.setScale(3,BigDecimal.ROUND_DOWN).doubleValue();
}
public Double getGenAgentCommissionDisplay() {
	return this.genAgentCommission.setScale(3,BigDecimal.ROUND_DOWN).doubleValue();
}
public Double getAgentCommissionDisplay() {
	return this.agentCommission.setScale(3,BigDecimal.ROUND_DOWN).doubleValue();
}
public java.math.BigDecimal getChiefRate() {
	return chiefRate;
}
public java.math.BigDecimal getBranchRate() {
	return branchRate;
}
public java.math.BigDecimal getStockRate() {
	return stockRate;
}
public java.math.BigDecimal getGenAgentRate() {
	return genAgentRate;
}
public java.math.BigDecimal getAgentRate() {
	return agentRate;
}
public String getChief() {
	return chief;
}
public String getBranch() {
	return branch;
}
public String getStock() {
	return stock;
}
public String getGenAgent() {
	return genAgent;
}
public String getAgent() {
	return agent;
}
public void setPlayTypeName(String playTypeName) {
	this.playTypeName = playTypeName;
}
public void setChief(String chief) {
	if(null!=chief){
		this.chief = chief;
	}else{
		this.chief = "";
	}
}
public void setBranch(String branch) {
	if(null!=branch){
		this.branch = branch;
	}else{
		this.branch = "";
	}
}
public void setStock(String stock) {
	if(null!=stock){
		this.stock = stock;
	}else{
		this.stock = "";
	}
}
public void setGenAgent(String genAgent) {
	if(null!=genAgent){
		this.genAgent = genAgent;
	}else{
		this.genAgent = "";
	}
}
public void setAgent(String agent) {
	if(null!=agent){
		this.agent = agent;
	}else{
		this.agent = "";
	}
}
public String getAttribute() {
	return attribute;
}
public String getAttributeChinaName() {
	return attributeChinaName;
}
public java.math.BigDecimal getRateMoney() {
	return rateMoney;
}
public String getRateMoneyDis() {
	DecimalFormat df = new DecimalFormat("0.0");
    return df.format(this.getRateMoney().setScale(1,BigDecimal.ROUND_HALF_UP));
}

public void setOrderNo(String orderNo) {
	this.orderNo = orderNo;
}
public Timestamp getBettingDate() {
	return bettingDate;
}
public void setBettingDate(Timestamp bettingDate) {
	this.bettingDate = bettingDate;
}
public String getBettingDateStr() {
	DateFormat format = new SimpleDateFormat("MM-dd HH:mm:ss E");
    Date date = this.bettingDate;
    String str = format.format(date);
    str = str.replace("星期", "");
    //System.out.println(str);
	return str;
}
public void setPeriodsNum(String periodsNum) {
	this.periodsNum = periodsNum;
}
public void setUserName(String userName) {
	this.userName = userName;
}
public void setPlate(String plate) {
	this.plate = plate;
}
public void setOdds(java.math.BigDecimal odds) {
	this.odds = odds;
}
public void setMoney(Integer money) {
	this.money = money;
}
public Integer getCount() {
	return count;
}
public void setCount(Integer count) {
	this.count = count;
}
public void setChiefCommission(java.math.BigDecimal chiefCommission) {
	this.chiefCommission = chiefCommission;
}
public void setBranchCommission(java.math.BigDecimal branchCommission) {
	this.branchCommission = branchCommission;
}
public void setStockCommission(java.math.BigDecimal stockCommission) {
	this.stockCommission = stockCommission;
}
public void setGenAgentCommission(java.math.BigDecimal genAgentCommission) {
	this.genAgentCommission = genAgentCommission;
}
public void setAgentCommission(java.math.BigDecimal agentCommission) {
	this.agentCommission = agentCommission;
}
public void setChiefRate(java.math.BigDecimal chiefRate) {
	this.chiefRate = chiefRate;
}
public void setBranchRate(java.math.BigDecimal branchRate) {
	this.branchRate = branchRate;
}
public void setStockRate(java.math.BigDecimal stockRate) {
	this.stockRate = stockRate;
}
public void setGenAgentRate(java.math.BigDecimal genAgentRate) {
	this.genAgentRate = genAgentRate;
}
public void setAgentRate(java.math.BigDecimal agentRate) {
	this.agentRate = agentRate;
}
public void setAttribute(String attribute) {
	this.attribute = attribute;
}
public void setAttributeChinaName(String attributeChinaName) {
	this.attributeChinaName = attributeChinaName;
}
public void setRateMoney(java.math.BigDecimal rateMoney) {
	this.rateMoney = rateMoney;
}
public String getWinState() {
	return winState;
}
public void setWinState(String winState) {
	this.winState = winState;
}
public String getSelectedOdds() {
	return selectedOdds;
}
public void setSelectedOdds(String selectedOdds) {
	this.selectedOdds = selectedOdds;
}
@Override
public int compareTo(DetailVO o) {
	return o.orderNo.compareTo(this.orderNo);
}
   
}
