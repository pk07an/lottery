package com.npc.lottery.statreport.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.npc.lottery.util.PlayTypeUtils;

/**
 * 投注信息历史记录实体类
 * 此实体类无实际数据表对应，只是用来存储各种投注类型的公用数据
 * 
 * @author User
 *
 */
public class LotInfoHis implements Serializable {

    public static final String NOT_LOTTERY = "0"; //未开奖

    public static final String WINNING_LOTTERY = "1"; //中奖

    public static final String NOT_WINNING_LOTTERY = "2";//未中奖

    public static final String ALERADY_LOTTERY = "3";//已兑奖

    public static final String HARMONY_LOTTERY = "9";//打和

    private String playType;//玩法类型，广东快乐十分，重庆时时彩等

    private Long ID;//ID

    private Date createDate;//记录入库时间

    private String originTbName;//原数据表名称

    private Long originID;//原数据表ID

    private String orderNo;//注单号

    private String typeCode;//投注类型，非页面上的投注类型

    private String commissionType;//佣金类型，对应页面上的投注类型

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

    private double winAmountTotal;//会员输赢

    private BigDecimal odds;//赔率

    private Date updateDate;//更新时间

    private String remark;//备注

    private long recordNum;//连码记录数目

    private double resultTotal;//您的结果总和

    private double resultTotalNoRate;//您的结果总和（未计算占成）

    /**
     * 构造函数
     * 
     * 根据广东快乐十分实体类构造
     * 
     * @param gdklsfHisEntity
     */
    public LotInfoHis(GdklsfHis gdklsfHisEntity) {

        if (null == gdklsfHisEntity) {
            return;
        }

        this.playType = StatReport.PLAY_TYPE_GDKLSF;

        this.ID = gdklsfHisEntity.getID();//ID

        this.createDate = gdklsfHisEntity.getCreateDate();//记录入库时间

        this.originTbName = gdklsfHisEntity.getOriginTbName();//原数据表名称

        this.originID = gdklsfHisEntity.getOriginID();//原数据表ID

        this.orderNo = gdklsfHisEntity.getOrderNo();//注单号

        this.typeCode = gdklsfHisEntity.getTypeCode();//投注类型

        this.typeCodeName = gdklsfHisEntity.getTypeCodeName();//投注类型名称

        this.money = gdklsfHisEntity.getMoney();//投注金额

        this.moneyTotal = gdklsfHisEntity.getMoneyTotal();//投注总额

        this.compoundNum = gdklsfHisEntity.getCompoundNum();//复式笔数

        this.bettingUserID = gdklsfHisEntity.getBettingUserID();//投注会员

        this.chiefstaff = gdklsfHisEntity.getChiefstaff();//总监

        this.branchstaff = gdklsfHisEntity.getBranchstaff();//分公司

        this.stockholderstaff = gdklsfHisEntity.getStockholderstaff();//股东

        this.genagenstaff = gdklsfHisEntity.getGenagenstaff();//总代理

        this.agentstaff = gdklsfHisEntity.getAgentstaff();//代理

        this.commissionBranch = gdklsfHisEntity.getCommissionBranch();//分公司佣金

        this.commissionGenAgent = gdklsfHisEntity.getCommissionGenAgent();//总代理佣金

        this.commissionStockholder = gdklsfHisEntity.getCommissionStockholder();//股东佣金

        this.commissionAgent = gdklsfHisEntity.getCommissionAgent();//代理佣金

        this.commissionMember = gdklsfHisEntity.getCommissionMember();//会员佣金

        this.rateChief = gdklsfHisEntity.getRateChief();//总监占成

        this.rateBranch = gdklsfHisEntity.getRateBranch();//分公司占成

        this.rateGenAgent = gdklsfHisEntity.getRateGenAgent();//总代理占成

        this.rateStockholder = gdklsfHisEntity.getRateStockholder();//股东占成

        this.rateAgent = gdklsfHisEntity.getRateAgent();//代理占成

        this.attribute = gdklsfHisEntity.getAttribute();//所选球

        this.periodsNum = gdklsfHisEntity.getPeriodsNum();//投注期数

        this.plate = gdklsfHisEntity.getPlate();//投注盘面

        this.bettingDate = gdklsfHisEntity.getBettingDate();//投注时间

        this.winState = gdklsfHisEntity.getWinState();//中奖状态

        this.winAmount = gdklsfHisEntity.getWinAmount();//中奖金额

        this.winAmountTotal = gdklsfHisEntity.getWinAmountTotal();//会员输赢

        this.odds = gdklsfHisEntity.getOdds();//赔率

        this.updateDate = gdklsfHisEntity.getUpdateDate();//更新时间

        this.remark = gdklsfHisEntity.getRemark();//备注

        this.recordNum = gdklsfHisEntity.getRecordNum();//连码记录数目

        this.resultTotal = gdklsfHisEntity.getResultTotal();//您的结果总和

        this.resultTotalNoRate = gdklsfHisEntity.getResultTotalNoRate();//您的结果总和（未计算占成）

        this.commissionType = gdklsfHisEntity.getCommissionType();
    }

    /**
     * 构造函数
     * 
     * 根据重庆时时彩实体类构造
     * 
     * @param cqsscHisEntity
     */
    public LotInfoHis(CqsscHis cqsscHisEntity) {

        if (null == cqsscHisEntity) {
            return;
        }

        this.playType = StatReport.PLAY_TYPE_CQSSC;

        this.ID = cqsscHisEntity.getID();//ID

        this.createDate = cqsscHisEntity.getCreateDate();//记录入库时间

        this.originTbName = cqsscHisEntity.getOriginTbName();//原数据表名称

        this.originID = cqsscHisEntity.getOriginID();//原数据表ID

        this.orderNo = cqsscHisEntity.getOrderNo();//注单号

        this.typeCode = cqsscHisEntity.getTypeCode();//投注类型

        this.typeCodeName = cqsscHisEntity.getTypeCodeName();//投注类型名称

        this.money = cqsscHisEntity.getMoney();//投注金额

        this.moneyTotal = cqsscHisEntity.getMoneyTotal();//投注总额

        this.compoundNum = cqsscHisEntity.getCompoundNum();//复式笔数

        this.bettingUserID = cqsscHisEntity.getBettingUserID();//投注会员

        this.chiefstaff = cqsscHisEntity.getChiefstaff();//总监

        this.branchstaff = cqsscHisEntity.getBranchstaff();//分公司

        this.stockholderstaff = cqsscHisEntity.getStockholderstaff();//股东

        this.genagenstaff = cqsscHisEntity.getGenagenstaff();//总代理

        this.agentstaff = cqsscHisEntity.getAgentstaff();//代理

        this.commissionBranch = cqsscHisEntity.getCommissionBranch();//分公司佣金

        this.commissionGenAgent = cqsscHisEntity.getCommissionGenAgent();//总代理佣金

        this.commissionStockholder = cqsscHisEntity.getCommissionStockholder();//股东佣金

        this.commissionAgent = cqsscHisEntity.getCommissionAgent();//代理佣金

        this.commissionMember = cqsscHisEntity.getCommissionMember();//会员佣金

        this.rateChief = cqsscHisEntity.getRateChief();//总监占成

        this.rateBranch = cqsscHisEntity.getRateBranch();//分公司占成

        this.rateGenAgent = cqsscHisEntity.getRateGenAgent();//总代理占成

        this.rateStockholder = cqsscHisEntity.getRateStockholder();//股东占成

        this.rateAgent = cqsscHisEntity.getRateAgent();//代理占成

        this.attribute = cqsscHisEntity.getAttribute();//所选球

        this.periodsNum = cqsscHisEntity.getPeriodsNum();//投注期数

        this.plate = cqsscHisEntity.getPlate();//投注盘面

        this.bettingDate = cqsscHisEntity.getBettingDate();//投注时间

        this.winState = cqsscHisEntity.getWinState();//中奖状态

        this.winAmount = cqsscHisEntity.getWinAmount();//中奖金额

        this.winAmountTotal = cqsscHisEntity.getWinAmountTotal();//会员输赢

        this.odds = cqsscHisEntity.getOdds();//赔率

        this.updateDate = cqsscHisEntity.getUpdateDate();//更新时间

        this.remark = cqsscHisEntity.getRemark();//备注

        this.recordNum = cqsscHisEntity.getRecordNum();//连码记录数目

        this.resultTotal = cqsscHisEntity.getResultTotal();//您的结果总和

        this.resultTotalNoRate = cqsscHisEntity.getResultTotalNoRate();//您的结果总和（未计算占成）

        this.commissionType = cqsscHisEntity.getCommissionType();
    }

    /**
     * 构造函数
     * 
     * 根据北京赛车实体类构造
     * 
     * @param bjscHisEntity
     */
    public LotInfoHis(BjscHis bjscHisEntity) {

        if (null == bjscHisEntity) {
            return;
        }

        this.playType = StatReport.PLAY_TYPE_BJSC;

        this.ID = bjscHisEntity.getID();//ID

        this.createDate = bjscHisEntity.getCreateDate();//记录入库时间

        this.originTbName = bjscHisEntity.getOriginTbName();//原数据表名称

        this.originID = bjscHisEntity.getOriginID();//原数据表ID

        this.orderNo = bjscHisEntity.getOrderNo();//注单号

        this.typeCode = bjscHisEntity.getTypeCode();//投注类型

        this.typeCodeName = bjscHisEntity.getTypeCodeName();//投注类型名称

        this.money = bjscHisEntity.getMoney();//投注金额

        this.moneyTotal = bjscHisEntity.getMoneyTotal();//投注总额

        this.compoundNum = bjscHisEntity.getCompoundNum();//复式笔数

        this.bettingUserID = bjscHisEntity.getBettingUserID();//投注会员

        this.chiefstaff = bjscHisEntity.getChiefstaff();//总监

        this.branchstaff = bjscHisEntity.getBranchstaff();//分公司

        this.stockholderstaff = bjscHisEntity.getStockholderstaff();//股东

        this.genagenstaff = bjscHisEntity.getGenagenstaff();//总代理

        this.agentstaff = bjscHisEntity.getAgentstaff();//代理

        this.commissionBranch = bjscHisEntity.getCommissionBranch();//分公司佣金

        this.commissionGenAgent = bjscHisEntity.getCommissionGenAgent();//总代理佣金

        this.commissionStockholder = bjscHisEntity.getCommissionStockholder();//股东佣金

        this.commissionAgent = bjscHisEntity.getCommissionAgent();//代理佣金

        this.commissionMember = bjscHisEntity.getCommissionMember();//会员佣金

        this.rateChief = bjscHisEntity.getRateChief();//总监占成

        this.rateBranch = bjscHisEntity.getRateBranch();//分公司占成

        this.rateGenAgent = bjscHisEntity.getRateGenAgent();//总代理占成

        this.rateStockholder = bjscHisEntity.getRateStockholder();//股东占成

        this.rateAgent = bjscHisEntity.getRateAgent();//代理占成

        this.attribute = bjscHisEntity.getAttribute();//所选球

        this.periodsNum = bjscHisEntity.getPeriodsNum();//投注期数

        this.plate = bjscHisEntity.getPlate();//投注盘面

        this.bettingDate = bjscHisEntity.getBettingDate();//投注时间

        this.winState = bjscHisEntity.getWinState();//中奖状态

        this.winAmount = bjscHisEntity.getWinAmount();//中奖金额

        this.winAmountTotal = bjscHisEntity.getWinAmountTotal();//会员输赢

        this.odds = bjscHisEntity.getOdds();//赔率

        this.updateDate = bjscHisEntity.getUpdateDate();//更新时间

        this.remark = bjscHisEntity.getRemark();//备注

        this.recordNum = bjscHisEntity.getRecordNum();//连码记录数目

        this.resultTotal = bjscHisEntity.getResultTotal();//您的结果总和

        this.resultTotalNoRate = bjscHisEntity.getResultTotalNoRate();//您的结果总和（未计算占成）

        this.commissionType = bjscHisEntity.getCommissionType();
    }

    public String getPlayType() {
        return playType;
    }

    /**
     * 获取对应的玩法类型
     * 
     * @return
     */
    public String getPlayTypeName() {

        String playTypeName = "<font color='#FF0000'>错误数据</font>";

        if (StatReport.PLAY_TYPE_GDKLSF.equalsIgnoreCase(playType)) {
            playTypeName = "广东快乐十分";
        } else if (StatReport.PLAY_TYPE_CQSSC.equalsIgnoreCase(playType)) {
            playTypeName = "重庆时时彩";
        } else if (StatReport.PLAY_TYPE_K3.equalsIgnoreCase(playType)) {
            playTypeName = "江苏骰寶(K3)";
        } else if (StatReport.PLAY_TYPE_BJSC.equalsIgnoreCase(playType)) {
            playTypeName = "北京赛车";
        }

        return playTypeName;
    }

    public void setPlayType(String playType) {
        this.playType = playType;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long iD) {
        ID = iD;
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

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeCodeName() {
        return typeCodeName;
    }

    public void setTypeCodeName(String typeCodeName) {
        this.typeCodeName = typeCodeName;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getMoneyTotal() {
        return moneyTotal;
    }

    public void setMoneyTotal(Integer moneyTotal) {
        this.moneyTotal = moneyTotal;
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

    public void setWinAmount(Integer winAmount) {
        this.winAmount = winAmount;
    }

    public double getWinAmountTotal() {
        return winAmountTotal;
    }

    public void setWinAmountTotal(double winAmountTotal) {
        this.winAmountTotal = winAmountTotal;
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

    public long getRecordNum() {
        return recordNum;
    }

    public void setRecordNum(long recordNum) {
        this.recordNum = recordNum;
    }

    public double getResultTotal() {
        return resultTotal;
    }

    public void setResultTotal(double resultTotal) {
        this.resultTotal = resultTotal;
    }

    public double getResultTotalNoRate() {
        return resultTotalNoRate;
    }

    public void setResultTotalNoRate(double resultTotalNoRate) {
        this.resultTotalNoRate = resultTotalNoRate;
    }

    public String getCommissionType() {
        return commissionType;
    }

    public void setCommissionType(String commissionType) {
        this.commissionType = commissionType;
    }
}
