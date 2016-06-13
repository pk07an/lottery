package com.npc.lottery.statreport.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.npc.lottery.util.PlayTypeUtils;

/**
 * 功能实体类（投注历史（香港六合彩）表 TB_HKLHC_HIS）
 * 
 *
 */
public class HklhcHis implements Serializable {
    public static final String NOT_LOTTERY = "0"; //未开奖

    public static final String WINNING_LOTTERY = "1"; //中奖

    public static final String NOT_WINNING_LOTTERY = "2";//未中奖

    public static final String ALERADY_LOTTERY = "3";//已兑奖

    public static final String HARMONY_LOTTERY = "9";//打和

    private Long ID;//ID

    private Date createDate;//记录入库时间

    private String originTbName;//原数据表名称

    private Long originID;//原数据表ID

    private String orderNo;//注单号

    private String typeCode;//投注类型

    private String typeCodeName;//投注类型名称

    private Integer money;//投注金额

    private Integer moneyTotal;//投注总额

    private Integer compoundNum;//复式笔数

    private Long bettingUserID;//投注会员

    private Long chiefstaff;//总监

    private Long branchstaff;//分公司

    private Long stockholderstaff;//股东

    private Long genagenstaff;//总代理

    private Long agentstaff;//代理

    private BigDecimal commissionBranch;//分公司佣金

    private BigDecimal commissionGenAgent;//总代理佣金

    private BigDecimal commissionStockholder;//股东佣金

    private BigDecimal commissionAgent;//代理佣金

    private BigDecimal commissionMember;//会员佣金

    private BigDecimal rateChief;//总监占成

    private BigDecimal rateBranch;//分公司占成

    private BigDecimal rateGenAgent;//总代理占成

    private BigDecimal rateStockholder;//股东占成

    private BigDecimal rateAgent;//代理占成

    private String attribute;//所选球

    private String periodsNum;//投注期数

    private String plate;//投注盘面

    private Date bettingDate;//投注时间

    private String winState;//中奖状态

    private Integer winAmount;//中奖金额

    private Integer winAmountTotal;//会员输赢

    private BigDecimal odds;//赔率

    private Date updateDate;//更新时间

    private String remark;//备注

    private long recordNum;//连码记录数目

    private double resultTotal;//您的结果总和

    private double resultTotalNoRate;//您的结果总和（未计算占成）

    private BigDecimal odds2;//赔率2

    private String selectOdds;//所有球的赔率

    private String commissionType;//佣金类型，对应页面上的投注类型

    public Long getID() {
        return ID;
    }

    public void setID(Long iD) {
        ID = iD;
    }

    /**
     * 根据typeCode获取玩法类型
     * 
     * @return
     */
    public String getPlayTypeName() {

        String playTypeName = "香港六合彩";

        //        if (null != typeCode) {
        //            if (typeCode.startsWith("HK")) {
        //                playTypeName = "香港六合彩";
        //            } else if (typeCode.startsWith("GDKLSF")) {
        //                playTypeName = "廣東快樂十分";
        //            } else if (typeCode.startsWith("CQSSC")) {
        //                playTypeName = "重慶時時彩";
        //            }
        //        }

        return playTypeName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getOriginTbName() {
        return originTbName;
    }

    public void setOriginTbName(String originTbName) {
        this.originTbName = originTbName;
    }

    public Long getOriginID() {
        return originID;
    }

    public void setOriginID(Long originID) {
        this.originID = originID;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public String getTypeCodeName() {
        return typeCodeName;
    }

    /**
     * 获取报表上所显示的玩法类型
     * 
     * @return
     */
    public String getTypeCodeName2() {
        String result = "";

        result = PlayTypeUtils.getPlayTypeSubName(this.typeCode);

        result = result + "『"
                + PlayTypeUtils.getPlayTypeFinalName(this.typeCode) + "』";

        return result;
    }

    public void setTypeCodeName(String typeCodeName) {
        this.typeCodeName = typeCodeName;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getCompoundNum() {
        return compoundNum;
    }

    public void setCompoundNum(Integer compoundNum) {
        this.compoundNum = compoundNum;
    }

    public Long getBettingUserID() {
        return bettingUserID;
    }

    public void setBettingUserID(Long bettingUserID) {
        this.bettingUserID = bettingUserID;
    }

    public Long getChiefstaff() {
        return chiefstaff;
    }

    public void setChiefstaff(Long chiefstaff) {
        this.chiefstaff = chiefstaff;
    }

    public Long getBranchstaff() {
        return branchstaff;
    }

    public void setBranchstaff(Long branchstaff) {
        this.branchstaff = branchstaff;
    }

    public Long getStockholderstaff() {
        return stockholderstaff;
    }

    public void setStockholderstaff(Long stockholderstaff) {
        this.stockholderstaff = stockholderstaff;
    }

    public Long getGenagenstaff() {
        return genagenstaff;
    }

    public void setGenagenstaff(Long genagenstaff) {
        this.genagenstaff = genagenstaff;
    }

    public Long getAgentstaff() {
        return agentstaff;
    }

    public void setAgentstaff(Long agentstaff) {
        this.agentstaff = agentstaff;
    }

    public BigDecimal getCommissionBranch() {
        return commissionBranch;
    }

    public void setCommissionBranch(BigDecimal commissionBranch) {
        this.commissionBranch = commissionBranch;
    }

    public BigDecimal getCommissionGenAgent() {
        return commissionGenAgent;
    }

    public void setCommissionGenAgent(BigDecimal commissionGenAgent) {
        this.commissionGenAgent = commissionGenAgent;
    }

    public BigDecimal getCommissionStockholder() {
        return commissionStockholder;
    }

    public void setCommissionStockholder(BigDecimal commissionStockholder) {
        this.commissionStockholder = commissionStockholder;
    }

    public BigDecimal getCommissionAgent() {
        return commissionAgent;
    }

    public void setCommissionAgent(BigDecimal commissionAgent) {
        this.commissionAgent = commissionAgent;
    }

    public BigDecimal getCommissionMember() {
        return commissionMember;
    }

    public void setCommissionMember(BigDecimal commissionMember) {
        this.commissionMember = commissionMember;
    }

    public BigDecimal getRateChief() {
        return rateChief;
    }

    public void setRateChief(BigDecimal rateChief) {
        this.rateChief = rateChief;
    }

    public BigDecimal getRateBranch() {
        return rateBranch;
    }

    public void setRateBranch(BigDecimal rateBranch) {
        this.rateBranch = rateBranch;
    }

    public BigDecimal getRateGenAgent() {
        return rateGenAgent;
    }

    public void setRateGenAgent(BigDecimal rateGenAgent) {
        this.rateGenAgent = rateGenAgent;
    }

    public BigDecimal getRateStockholder() {
        return rateStockholder;
    }

    public void setRateStockholder(BigDecimal rateStockholder) {
        this.rateStockholder = rateStockholder;
    }

    public BigDecimal getRateAgent() {
        return rateAgent;
    }

    public String getRateAgentName() {
        //return rateAgent.longValue()/100.0 + "%";
        return rateAgent.longValue() + "%";
    }

    public void setRateAgent(BigDecimal rateAgent) {
        this.rateAgent = rateAgent;
    }

    public String getAttribute() {
        return attribute;
    }

    public String getAttribute2() {

        String result = "";

        if (null != attribute) {
            result = attribute.replace("|", ",");
        }

        return result;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
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

    public Integer getWinAmount() {
        return winAmount;
    }

    /**
     * 计算会员结果
     * 
     * @return
     */
    public Integer getWinAmount2() {

        double amount = 0;
        if (WINNING_LOTTERY.equalsIgnoreCase(winState)) {
            //中奖
            amount = winAmount;
            //增加退水
            amount += money * commissionMember.doubleValue() / 100;
        } else if (NOT_WINNING_LOTTERY.equalsIgnoreCase(winState)) {
            //未中奖则读取投注额
            amount = -1 * money;
            //增加退水
            amount += money * commissionMember.doubleValue() / 100;
        } else if (HARMONY_LOTTERY.equalsIgnoreCase(winState)) {
            //打和则读取投注额
            amount = money;
            //打和不计算退水
        }

        return new Double(amount).intValue();
    }

    /**
     * 获取会员输赢（总值）
     * 
     * @return
     */
    public Integer getWinAmountTotal() {

        //连码的计算方式与普通不同
        if (recordNum > 1) {
            //连码此处不计算，由其他逻辑直接填充 winAmountTotal
        } else {
            //非连码直接取会员输赢值
            winAmountTotal = this.getWinAmount2();
        }

        return winAmountTotal;
    }

    public void setWinAmountTotal(Integer winAmountTotal) {
        this.winAmountTotal = winAmountTotal;
    }

    public void setWinAmount(Integer winAmount) {
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

    /**
     * 报表明细上的“您的结果”
     * 
     * @return
     */
    public double getResult() {

        //rateAgent := rateAgent + (memberAmount_temp + (member_pos.MONEY * member_pos.COMMISSION_MEMBER / 100)) * member_pos.RATE_AGENT/100;
        //根据是否中奖，计算会员输赢金额
        double amount = 0;
        if (WINNING_LOTTERY.equalsIgnoreCase(winState)) {
            //中奖
            amount = winAmount;
        } else if (NOT_WINNING_LOTTERY.equalsIgnoreCase(winState)) {
            //未中奖则读取投注额
            amount = -1 * money;
        }

        double result = (amount + money.intValue()
                * commissionMember.intValue() / 100.0)
                * (rateAgent.longValue() / 100.0);

        return (new Double(result * 100)).intValue() / 100.0;
    }

    /**
     * 报表明细上的“您的结果”（未计算占成）
     * 
     * @return
     */
    public double getResultNoRate() {

        //rateAgent := rateAgent + (memberAmount_temp + (member_pos.MONEY * member_pos.COMMISSION_MEMBER / 100)) * member_pos.RATE_AGENT/100;
        //根据是否中奖，计算会员输赢金额
        double amount = 0;
        if (WINNING_LOTTERY.equalsIgnoreCase(winState)) {
            //中奖
            amount = winAmount;
        } else if (NOT_WINNING_LOTTERY.equalsIgnoreCase(winState)) {
            //未中奖则读取投注额
            amount = -1 * money;
        }

        double result = (amount + money.intValue()
                * commissionMember.intValue() / 100.0);

        return (new Double(result * 100)).intValue() / 100.0;
    }

    public void setResultTotal(double resultTotal) {
        this.resultTotal = resultTotal;
    }

    /**
     * 报表明细上的“您的结果”（总和）
     * 
     * @return
     */
    public double getResultTotal() {

        if (recordNum > 1) {
            //连码在其他地方计算
        } else {
            resultTotal = this.getResult();
        }

        return resultTotal;
    }

    /**
     * 报表明细上的“您的结果”（总和）
     * 
     * @return
     */
    public double getResultTotalNoRate() {

        if (recordNum > 1) {
            //连码在其他地方计算
        } else {
            resultTotalNoRate = this.getResultNoRate();
        }

        return resultTotalNoRate;
    }

    public void setResultTotalNoRate(double resultTotalNoRate) {
        this.resultTotalNoRate = resultTotalNoRate;
    }

    public long getRecordNum() {
        return recordNum;
    }

    public void setRecordNum(long recordNum) {
        this.recordNum = recordNum;
    }

    public Integer getMoneyTotal() {

        if (recordNum > 1) {
            //连码的计算在其他地方进行
        } else {
            moneyTotal = money;
        }

        return moneyTotal;
    }

    public void setMoneyTotal(Integer moneyTotal) {
        this.moneyTotal = moneyTotal;
    }

    public BigDecimal getOdds2() {
        return odds2;
    }

    public void setOdds2(BigDecimal odds2) {
        this.odds2 = odds2;
    }

    public String getSelectOdds() {
        return selectOdds;
    }

    public void setSelectOdds(String selectOdds) {
        this.selectOdds = selectOdds;
    }

    public String getCommissionType() {
        return commissionType;
    }

    public void setCommissionType(String commissionType) {
        this.commissionType = commissionType;
    }
}
