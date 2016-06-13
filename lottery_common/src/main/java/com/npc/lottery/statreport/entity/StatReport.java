package com.npc.lottery.statreport.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 报表查询条件所对应的实体类
 * 
 * @author LXF
 *
 */
public class StatReport implements Serializable {

    public static final String PLAY_TYPE_ALL = "ALL";//所有
    
    public static final String PLAY_TYPE_GDKLSF = "GDKLSF";//玩法：广东快乐十分

    public static final String PLAY_TYPE_CQSSC = "CQSSC";//玩法：重庆时时彩

    public static final String PLAY_TYPE_K3 = "K3";//玩法：K3
    
    public static final String PLAY_TYPE_NC = "NC";//玩法：NC
    
    public static final String PLAY_TYPE_BJSC = "BJSC";//玩法：北京赛车
    
    public static final String PLAY_TYPE_BJ = "BJ";//玩法：北京赛车

    private String lotteryType;//彩票种类

    private String playType;//下注类型

    private String typePeriod;//按期数

    private String typeTime;//按时间

    private String periodsNum;//期数信息

    private Timestamp bettingDateStart;//开始时间

    private Timestamp bettingDateEnd;//结束时间

    private String reportType;//报表类型

    public String getLotteryType() {
        return lotteryType;
    }

    public void setLotteryType(String lotteryType) {
        this.lotteryType = lotteryType;
    }

    public String getPlayType() {
        return playType;
    }

    public void setPlayType(String playType) {
        this.playType = playType;
    }

    public String getTypePeriod() {
        return typePeriod;
    }

    public void setTypePeriod(String typePeriod) {
        this.typePeriod = typePeriod;
    }

    public String getTypeTime() {
        return typeTime;
    }

    public void setTypeTime(String typeTime) {
        this.typeTime = typeTime;
    }

    public String getPeriodsNum() {
        return periodsNum;
    }

    public void setPeriodsNum(String periodsNum) {
        this.periodsNum = periodsNum;
    }

    public Timestamp getBettingDateStart() {
        return bettingDateStart;
    }

    public void setBettingDateStart(Timestamp bettingDateStart) {
        this.bettingDateStart = bettingDateStart;
    }

    public Timestamp getBettingDateEnd() {
        return bettingDateEnd;
    }

    public void setBettingDateEnd(Timestamp bettingDateEnd) {
        this.bettingDateEnd = bettingDateEnd;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }
}
