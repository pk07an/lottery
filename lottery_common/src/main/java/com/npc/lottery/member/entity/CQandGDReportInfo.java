package com.npc.lottery.member.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CQandGDReportInfo implements Serializable,Comparable<CQandGDReportInfo>{

    /**
     * 
     */
    private static final long serialVersionUID = -2651409925153965335L;
    
    private String typeCode;//类型
    private Date bettingDate;//投注时间
    //投注日期只截取'星期一'的'一'
    private String bettingDateStr;
    private String periodsNum;//期数
    private Integer money;//投注金额
    private String orderNo;//注单号
    private String attribute;
    private String selectOdds;
    private Double  recessionResults;   //退水後結果 
    private Double odds;
    private Double oddsTwo;
    private String winState;
    private int count;
    
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public String getWinState() {
        return winState;
    }
    public void setWinState(String winState) {
        this.winState = winState;
    }
    public Double getOddsTwo() {
        return oddsTwo;
    }
    public void setOddsTwo(Double oddsTwo) {
        this.oddsTwo = oddsTwo;
    }
    public Double getOdds() {
        return odds;
    }
    public void setOdds(Double odds) {
        this.odds = odds;
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
    public Date getBettingDate() {
        return bettingDate;
    }
    public void setBettingDate(Date bettingDate) {
        this.bettingDate = bettingDate;
    }
    public String getBettingDateStr() {
    	DateFormat format = new SimpleDateFormat("MM-dd HH:mm:ss E");
        Date date = this.bettingDate;
        String str = format.format(date);
        str = str.replace("星期", "");
        System.out.println(str);
    	return str;
    }
    public String getTypeCode() {
        return typeCode;
    }
    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }
    public String getPeriodsNum() {
        return periodsNum;
    }
    public void setPeriodsNum(String periodsNum) {
        this.periodsNum = periodsNum;
    }
    public Integer getMoney() {
    	
        return money;
    }
    public void setMoney(Integer money) {
        this.money = money;
    }
    public Double getRecessionResults() {
    	if(recessionResults!=null)
    	{
    		//return new BigDecimal(recessionResults).setScale(1, BigDecimal.ROUND_DOWN).doubleValue();
    	}
        return recessionResults;
    }
    public void setRecessionResults(Double recessionResults) {
        this.recessionResults = recessionResults;
    }
    
    @Override
    public int compareTo(CQandGDReportInfo cqandGDReportInfo) {
        return cqandGDReportInfo.orderNo.compareTo(this.orderNo);
    }
	public String getSelectOdds() {
		return selectOdds;
	}
	public void setSelectOdds(String selectOdds) {
		this.selectOdds = selectOdds;
	}
    
}
