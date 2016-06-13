package com.npc.lottery.replenish.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.util.PlayTypeUtils;

public class ZhanDangLMDetailVO implements Serializable,Comparable<ZhanDangLMDetailVO> {
 
   private static final long serialVersionUID = 1L;
   //注单号
   private String orderNo;
   //投注日期
   private Timestamp bettingDate;
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
   
   //赔率
   private java.math.BigDecimal odds = BigDecimal.valueOf(0);
   //赔率2
   private java.math.BigDecimal odds2 = BigDecimal.valueOf(0);
   //實占金額
   private java.math.BigDecimal money = BigDecimal.valueOf(0);
   //退水(因为没有出开奖结果，未知输赢，所以退水后结果与退水一样)
   private java.math.BigDecimal commissionMoney = BigDecimal.valueOf(0);
   
   private Integer count = 0;
   
   private String attribute;   //纪录连码投注时所选择的球
   private String attributeChinaName;   //纪录连码投注时所选择的排列的中文翻譯
   
   private String attributeGroupMoney; //显示5x5组
   
   private String selectedOdds;
   
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
	if(ManagerStaff.USER_TYPE_CHIEF.equals(userType) || ManagerStaff.USER_TYPE_OUT_REPLENISH.equals(userType)){
		return "總監出貨";
	}
	if(ManagerStaff.USER_TYPE_BRANCH.equals(userType)){
		return "分公司走飛";
	}
	if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(userType)){
		return"股東走飛";
	}
	if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(userType)){
		return "總代理走飛";
	}
	if(ManagerStaff.USER_TYPE_AGENT.equals(userType)){
		return "代理走飛";
	}
	return whoReplenish;
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
public java.math.BigDecimal getMoney() {
	return money;
}
public java.math.BigDecimal getCommissionMoney() {
	return commissionMoney;
}
public String getCommissionMoneyDis() {
	DecimalFormat df = new DecimalFormat("0.0");
    String moneyStr =  df.format(this.commissionMoney.setScale(1,BigDecimal.ROUND_HALF_UP));
	return moneyStr;
}
public void setCommissionMoney(java.math.BigDecimal commissionMoney) {
	this.commissionMoney = commissionMoney;
}
public String getMoneyDis() {
	String str = null;
	DecimalFormat df = new DecimalFormat("0.0");
    String moneyStr =  df.format(this.money.setScale(1,BigDecimal.ROUND_HALF_UP));
    BigDecimal perMoney = money.divide(BigDecimal.valueOf(count),0,BigDecimal.ROUND_HALF_UP);
    if(count>1){
    	str = perMoney + "x" + count + "組</br>" + moneyStr;
    }else{
    	str = "" + moneyStr;
    }
    return str;
}
public void setPlayTypeName(String playTypeName) {
	this.playTypeName = playTypeName;
}
public String getAttribute() {
	return attribute;
}
public String getAttributeChinaName() {
	return attributeChinaName;
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
public void setMoney(java.math.BigDecimal money) {
	this.money = money;
}
public Integer getCount() {
	return count;
}
public void setCount(Integer count) {
	this.count = count;
}
public void setAttribute(String attribute) {
	this.attribute = attribute;
}
public void setAttributeChinaName(String attributeChinaName) {
	this.attributeChinaName = attributeChinaName;
}
public String getSelectedOdds() {
	return selectedOdds;
}
public void setSelectedOdds(String selectedOdds) {
	this.selectedOdds = selectedOdds;
}
@Override
public int compareTo(ZhanDangLMDetailVO o) {
	return o.orderNo.compareTo(this.orderNo);
}
   
}
