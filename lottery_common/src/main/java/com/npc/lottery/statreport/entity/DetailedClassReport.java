package com.npc.lottery.statreport.entity;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.npc.lottery.common.Constant;
import com.npc.lottery.util.PlayTypeUtils;

/**
 * 分类报表的明细信息对象
 * 
 * @author laixiangfeng
 *
 */
public class DetailedClassReport {

    private String orderNo;//注单号

    private Date bettingDate;//投注时间

    private String playTypeName;

    private String periodsNum;//投注期数

    private Long bettingUserID;//投注会员

    private String plate;//投注盘面

    private String typeCodeName2;//玩法类型

    private BigDecimal odds;//赔率

    private String attribute2;//所选球

    private Integer moneyTotal;//投注总额

    private double winAmountTotal;//会员输赢

    private BigDecimal rateChief;//总监占成

    private BigDecimal rateBranch;//分公司占成

    private BigDecimal rateGenAgent;//总代理占成

    private BigDecimal rateStockholder;//股东占成

    private BigDecimal rateAgent;//代理占成

    private String rateAgentName;//代理占成显示（如果是直属会员，所赋值的不是代理）

    private BigDecimal commissionBranch;//分公司佣金

    private BigDecimal commissionGenAgent;//总代理佣金

    private BigDecimal commissionStockholder;//股东佣金

    private BigDecimal commissionAgent;//代理佣金

    private BigDecimal commissionMember;//会员佣金

    private double resultTotal;//您的结果总和

    private double resultTotalNoRate;//您的结果总和（未计算占成）

    private String typeCode;//投注类型

    private String attribute;//所选球

    private BigDecimal odds2;//赔率2

    private String selectOdds;//所有球的赔率

    private long recordNum;//复式记录数

    private String winState = null;

    /**
     * 根据广东快乐十分历史数据构造报表明细数据
     * 
     * @param gdklsfHisEntity
     */
    public DetailedClassReport(GdklsfHis gdklsfHisEntity) {
        //构造数据
        this.orderNo = gdklsfHisEntity.getOrderNo();
        this.bettingDate = gdklsfHisEntity.getBettingDate();
        this.playTypeName = gdklsfHisEntity.getPlayTypeName();
        this.periodsNum = gdklsfHisEntity.getPeriodsNum();
        this.bettingUserID = gdklsfHisEntity.getBettingUserID();
        this.plate = gdklsfHisEntity.getPlate();
        this.typeCodeName2 = gdklsfHisEntity.getTypeCodeName2();
        this.odds = gdklsfHisEntity.getOdds();
        this.attribute2 = gdklsfHisEntity.getAttribute2();
        this.moneyTotal = gdklsfHisEntity.getMoneyTotal();
        this.winAmountTotal = gdklsfHisEntity.getWinAmountTotal();
        this.rateChief = gdklsfHisEntity.getRateChief();
        this.rateBranch = gdklsfHisEntity.getRateBranch();
        this.rateStockholder = gdklsfHisEntity.getRateStockholder();
        this.rateGenAgent = gdklsfHisEntity.getRateGenAgent();
        this.rateAgent = gdklsfHisEntity.getRateAgent();
        this.commissionBranch = gdklsfHisEntity.getCommissionBranch();
        this.commissionGenAgent = gdklsfHisEntity.getCommissionGenAgent();
        this.commissionStockholder = gdklsfHisEntity.getCommissionStockholder();
        this.commissionAgent = gdklsfHisEntity.getCommissionAgent();
        this.commissionMember = gdklsfHisEntity.getCommissionMember();
        this.resultTotal = gdklsfHisEntity.getResultTotal();
        this.resultTotalNoRate = gdklsfHisEntity.getResultTotalNoRate();
        this.typeCode = gdklsfHisEntity.getTypeCode();
        this.attribute = gdklsfHisEntity.getAttribute();
        this.recordNum = gdklsfHisEntity.getRecordNum();
        this.winState = gdklsfHisEntity.getWinState();
    }

    /**
     * 根据重庆时时彩历史数据构造报表明细数据
     * 
     * @param cqsscHisEntity
     */
    public DetailedClassReport(CqsscHis cqsscHisEntity) {
        //构造数据
        this.orderNo = cqsscHisEntity.getOrderNo();
        this.bettingDate = cqsscHisEntity.getBettingDate();
        this.playTypeName = cqsscHisEntity.getPlayTypeName();
        this.periodsNum = cqsscHisEntity.getPeriodsNum();
        this.bettingUserID = cqsscHisEntity.getBettingUserID();
        this.plate = cqsscHisEntity.getPlate();
        this.typeCodeName2 = cqsscHisEntity.getTypeCodeName2();
        this.odds = cqsscHisEntity.getOdds();
        this.attribute2 = cqsscHisEntity.getAttribute2();
        this.moneyTotal = cqsscHisEntity.getMoneyTotal();
        this.winAmountTotal = cqsscHisEntity.getWinAmountTotal();
        this.rateChief = cqsscHisEntity.getRateChief();
        this.rateBranch = cqsscHisEntity.getRateBranch();
        this.rateStockholder = cqsscHisEntity.getRateStockholder();
        this.rateGenAgent = cqsscHisEntity.getRateGenAgent();
        this.rateAgent = cqsscHisEntity.getRateAgent();
        this.commissionBranch = cqsscHisEntity.getCommissionBranch();
        this.commissionGenAgent = cqsscHisEntity.getCommissionGenAgent();
        this.commissionStockholder = cqsscHisEntity.getCommissionStockholder();
        this.commissionAgent = cqsscHisEntity.getCommissionAgent();
        this.commissionMember = cqsscHisEntity.getCommissionMember();
        this.resultTotal = cqsscHisEntity.getResultTotal();
        this.resultTotalNoRate = cqsscHisEntity.getResultTotalNoRate();
        this.typeCode = cqsscHisEntity.getTypeCode();
        this.attribute = cqsscHisEntity.getAttribute();
        this.recordNum = cqsscHisEntity.getRecordNum();
        this.winState = cqsscHisEntity.getWinState();
    }

    /**
     * 根据香港六合彩历史数据构造报表明细数据
     * 
     * @param cqsscHisEntity
     */
    public DetailedClassReport(HklhcHis hklhcHisEntity) {
        //构造数据
        this.orderNo = hklhcHisEntity.getOrderNo();
        this.bettingDate = hklhcHisEntity.getBettingDate();
        this.playTypeName = hklhcHisEntity.getPlayTypeName();
        this.periodsNum = hklhcHisEntity.getPeriodsNum();
        this.bettingUserID = hklhcHisEntity.getBettingUserID();
        this.plate = hklhcHisEntity.getPlate();
        this.typeCodeName2 = hklhcHisEntity.getTypeCodeName2();
        this.odds = hklhcHisEntity.getOdds();
        this.attribute2 = hklhcHisEntity.getAttribute2();
        this.moneyTotal = hklhcHisEntity.getMoneyTotal();
        this.winAmountTotal = hklhcHisEntity.getWinAmountTotal();
        this.rateChief = hklhcHisEntity.getRateChief();
        this.rateBranch = hklhcHisEntity.getRateBranch();
        this.rateStockholder = hklhcHisEntity.getRateStockholder();
        this.rateGenAgent = hklhcHisEntity.getRateGenAgent();
        this.rateAgent = hklhcHisEntity.getRateAgent();
        this.commissionBranch = hklhcHisEntity.getCommissionBranch();
        this.commissionGenAgent = hklhcHisEntity.getCommissionGenAgent();
        this.commissionStockholder = hklhcHisEntity.getCommissionStockholder();
        this.commissionAgent = hklhcHisEntity.getCommissionAgent();
        this.commissionMember = hklhcHisEntity.getCommissionMember();
        this.resultTotal = hklhcHisEntity.getResultTotal();
        this.resultTotalNoRate = hklhcHisEntity.getResultTotalNoRate();
        this.typeCode = hklhcHisEntity.getTypeCode();
        this.attribute = hklhcHisEntity.getAttribute();
        this.odds2 = hklhcHisEntity.getOdds2();
        this.selectOdds = hklhcHisEntity.getSelectOdds();
        this.recordNum = hklhcHisEntity.getRecordNum();
        this.winState = hklhcHisEntity.getWinState();
    }

    /**
     * 根据北京赛车历史数据构造报表明细数据
     * 
     * @param bjscHisEntity
     */
    public DetailedClassReport(BjscHis bjscHisEntity) {
        //构造数据
        this.orderNo = bjscHisEntity.getOrderNo();
        this.bettingDate = bjscHisEntity.getBettingDate();
        this.playTypeName = bjscHisEntity.getPlayTypeName();
        this.periodsNum = bjscHisEntity.getPeriodsNum();
        this.bettingUserID = bjscHisEntity.getBettingUserID();
        this.plate = bjscHisEntity.getPlate();
        this.typeCodeName2 = bjscHisEntity.getTypeCodeName2();
        this.odds = bjscHisEntity.getOdds();
        this.attribute2 = bjscHisEntity.getAttribute2();
        this.moneyTotal = bjscHisEntity.getMoneyTotal();
        this.winAmountTotal = bjscHisEntity.getWinAmountTotal();
        this.rateChief = bjscHisEntity.getRateChief();
        this.rateBranch = bjscHisEntity.getRateBranch();
        this.rateStockholder = bjscHisEntity.getRateStockholder();
        this.rateGenAgent = bjscHisEntity.getRateGenAgent();
        this.rateAgent = bjscHisEntity.getRateAgent();
        this.commissionBranch = bjscHisEntity.getCommissionBranch();
        this.commissionGenAgent = bjscHisEntity.getCommissionGenAgent();
        this.commissionStockholder = bjscHisEntity.getCommissionStockholder();
        this.commissionAgent = bjscHisEntity.getCommissionAgent();
        this.commissionMember = bjscHisEntity.getCommissionMember();
        this.resultTotal = bjscHisEntity.getResultTotal();
        this.resultTotalNoRate = bjscHisEntity.getResultTotalNoRate();
        this.typeCode = bjscHisEntity.getTypeCode();
        this.attribute = bjscHisEntity.getAttribute();
        this.recordNum = bjscHisEntity.getRecordNum();
        this.winState = bjscHisEntity.getWinState();
    }

    /**
     * 根据投注信息历史记录实体类数据构造报表明细数据
     * 
     * @param lotInfoHisEntity
     */
    public DetailedClassReport(LotInfoHis lotInfoHisEntity) {
        //构造数据
        this.orderNo = lotInfoHisEntity.getOrderNo();
        this.bettingDate = lotInfoHisEntity.getBettingDate();
        this.playTypeName = lotInfoHisEntity.getPlayTypeName();
        this.periodsNum = lotInfoHisEntity.getPeriodsNum();
        this.bettingUserID = lotInfoHisEntity.getBettingUserID();
        this.plate = lotInfoHisEntity.getPlate();
        this.typeCodeName2 = lotInfoHisEntity.getTypeCodeName2();
        this.odds = lotInfoHisEntity.getOdds();
        this.attribute2 = lotInfoHisEntity.getAttribute2();
        this.moneyTotal = lotInfoHisEntity.getMoneyTotal();
        this.winAmountTotal = lotInfoHisEntity.getWinAmountTotal();
        this.rateChief = lotInfoHisEntity.getRateChief();
        this.rateBranch = lotInfoHisEntity.getRateBranch();
        this.rateStockholder = lotInfoHisEntity.getRateStockholder();
        this.rateGenAgent = lotInfoHisEntity.getRateGenAgent();
        this.rateAgent = lotInfoHisEntity.getRateAgent();
        this.commissionBranch = lotInfoHisEntity.getCommissionBranch();
        this.commissionGenAgent = lotInfoHisEntity.getCommissionGenAgent();
        this.commissionStockholder = lotInfoHisEntity
                .getCommissionStockholder();
        this.commissionAgent = lotInfoHisEntity.getCommissionAgent();
        this.commissionMember = lotInfoHisEntity.getCommissionMember();
        this.resultTotal = lotInfoHisEntity.getResultTotal();
        this.resultTotalNoRate = lotInfoHisEntity.getResultTotalNoRate();
        this.typeCode = lotInfoHisEntity.getTypeCode();
        this.attribute = lotInfoHisEntity.getAttribute();
        this.recordNum = lotInfoHisEntity.getRecordNum();
        this.winState = lotInfoHisEntity.getWinState();
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

    public String getPlayTypeName() {
        return playTypeName;
    }

    public void setPlayTypeName(String playTypeName) {
        this.playTypeName = playTypeName;
    }

    public String getPeriodsNum() {
        return periodsNum;
    }

    public void setPeriodsNum(String periodsNum) {
        this.periodsNum = periodsNum;
    }

    public Long getBettingUserID() {
        return bettingUserID;
    }

    public void setBettingUserID(Long bettingUserID) {
        this.bettingUserID = bettingUserID;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getTypeCodeName2() {
        return typeCodeName2;
    }

    /**
     * 获取报表上所显示的玩法类型+赔率组合
     * 
     * @return
     */
    public String getTypeCodeNameOdd() {
        String result = "";
        String fs = "";
        if (this.getRecordNum() > 1)
            fs = "複式『" + this.getRecordNum() + "組』</br>";
        result = PlayTypeUtils.getPlayTypeSubName(this.typeCode);
        String displayAttr = "";
        if (typeCode.indexOf("HK_STRAIGHTTHROUGH") != -1) {
            String[] typeAndBet = StringUtils.split(attribute, "\\*");
            if (typeAndBet.length >= 2) {
                String type = typeAndBet[0];
                String bet = typeAndBet[1];
                if ("DZ".equals(type)) {
                    if (this.getRecordNum() == 1)
                        displayAttr = "單註" + bet.replace("|", ",");
                    else
                        displayAttr = "複式" + bet.replace("|", ",");

                } else if ("T1D".equals(type)) {
                    String[] danAndBall = StringUtils.split(bet, "\\&");
                    String dan = danAndBall[0];
                    //String[] balls=StringUtils.split(danAndBall[1],"\\|");
                    displayAttr = "膽  " + dan + " <font color=\"red\">拖</font>"
                            + danAndBall[1].replace("|", ",");
                } else if ("T2D".equals(type)) {
                    String[] danAndBall = StringUtils.split(bet, "\\&");
                    String[] dan = StringUtils.split(danAndBall[0], "\\|");
                    String[] balls = StringUtils.split(danAndBall[1], "\\|");
                    displayAttr = "膽  " + danAndBall[0].replace("|", ",")
                            + " <font color=\"red\">拖</font>"
                            + danAndBall[1].replace("|", ",");
                } else if ("SXDP".equals(type)) {
                    String[] sxs = StringUtils.split(bet, "\\|");
                    displayAttr = "生肖對碰  " + Constant.HK_SX_MAP.get(sxs[0])
                            + " <font color=\"red\">碰</font> "
                            + Constant.HK_SX_MAP.get(sxs[1]);
                } else if ("WSDP".equals(type)) {
                    String[] wss = StringUtils.split(bet, "\\|");
                    displayAttr = "尾数對碰  " + Constant.HK_WS_MAP.get(wss[0])
                            + " <font color=\"red\">碰</font> "
                            + Constant.HK_WS_MAP.get(wss[1]);
                }

            }
            if ("HK_STRAIGHTTHROUGH_3Z2".equals(this.typeCode)) {
                result = result + "『"
                        + PlayTypeUtils.getPlayTypeFinalName(this.typeCode)
                        + "』@<font color='red'>" + this.odds
                        + "</font> 中叁 @<font color='red'>" + this.odds2
                        + "</font><br/>" + fs;

            } else if ("HK_STRAIGHTTHROUGH_2ZT".equals(this.typeCode)) {
                result = result + "『"
                        + PlayTypeUtils.getPlayTypeFinalName(this.typeCode)
                        + "』@<font color='red'>" + this.odds
                        + "</font> 中二 @<font color='red'>" + this.odds2
                        + "</font><br/>" + fs;

            } else {
                result = result + "『"
                        + PlayTypeUtils.getPlayTypeFinalName(this.typeCode)
                        + "』@<font color='red'>" + this.odds
                        + "</font> 中二 @<font color='red'>" + this.odds2
                        + "</font><br/>" + fs;

            }

        }

        else if ("HK_GG".equals(this.typeCode)) {
            String[] guan = StringUtils.split(this.attribute, "\\|");
            for (int z = 0; z < guan.length; z++) {
                String maCode = "";
                String betCode = "";
                String ballName = guan[z];
                if (ballName.indexOf("ZM1") != -1)
                    maCode = "正碼一";
                else if (ballName.indexOf("ZM2") != -1)
                    maCode = "正碼二";
                else if (ballName.indexOf("ZM3") != -1)
                    maCode = "正碼三";
                else if (ballName.indexOf("ZM4") != -1)
                    maCode = "正碼四";
                else if (ballName.indexOf("ZM5") != -1)
                    maCode = "正碼五";
                else if (ballName.indexOf("ZM6") != -1)
                    maCode = "正碼六";
                if (ballName.indexOf("DAN") != -1)
                    betCode = "單";
                else if (ballName.indexOf("DA") != -1)
                    betCode = "大";
                else if (ballName.indexOf("S") != -1)
                    betCode = "雙";
                else if (ballName.indexOf("X") != -1)
                    betCode = "小";
                else if (ballName.indexOf("RED") != -1)
                    betCode = "紅";
                else if (ballName.indexOf("GREEN") != -1)
                    betCode = "綠";
                else if (ballName.indexOf("BLUE") != -1)
                    betCode = "藍";
                guan[z] = maCode
                        + "-"
                        + betCode
                        + "@<font color='red'>"
                        + StringUtils.substringBetween(selectOdds + "|",
                                ballName + "&", "|") + "</font>";

            }
            result = result + "『"
                    + PlayTypeUtils.getPlayTypeFinalName(this.typeCode)
                    + "』<br/>" + StringUtils.join(guan, ",");

        } else if (this.typeCode.indexOf("HK_SXL") != -1) {
            String[] danAndSX = StringUtils.split(this.attribute, "\\&");
            String[] dan = null;
            String[] sx = null;
            if (danAndSX.length == 2) {
                dan = StringUtils.split(danAndSX[0], "\\|");
                sx = StringUtils.split(danAndSX[1], "\\|");

                for (int j = 0; j < dan.length; j++) {
                    dan[j] = Constant.HK_SX_MAP.get(dan[j])
                            + "@<font color='red'>"
                            + StringUtils.substringBetween(selectOdds + "|",
                                    dan[j] + "&", "|") + "</font>";
                }
                for (int k = 0; k < sx.length; k++) {
                    sx[k] = Constant.HK_SX_MAP.get(sx[k])
                            + "@<font color='red'>"
                            + StringUtils.substringBetween(selectOdds + "|",
                                    sx[k] + "&", "|") + "</font>";
                }
                result = result + "『"
                        + PlayTypeUtils.getPlayTypeFinalName(this.typeCode)
                        + "』<br/>" + fs + StringUtils.join(dan, ",")
                        + StringUtils.join(sx, ",");

            } else if (danAndSX.length == 1) {
                sx = StringUtils.split(danAndSX[0], "\\|");
                for (int k = 0; k < sx.length; k++) {
                    sx[k] = Constant.HK_SX_MAP.get(sx[k])
                            + "@<font color='red'>"
                            + StringUtils.substringBetween(selectOdds + "|",
                                    sx[k] + "&", "|") + "</font>";
                }
                result = result + "『"
                        + PlayTypeUtils.getPlayTypeFinalName(this.typeCode)
                        + "』<br/>" + fs + StringUtils.join(sx, ",");
            }

        } else if (typeCode.indexOf("HK_WSL") != -1) {
            String[] danAndWS = StringUtils.split(attribute, "\\&");
            String[] dan = null;
            String[] ws = null;
            if (danAndWS.length == 2) {
                dan = StringUtils.split(danAndWS[0], "\\|");
                ws = StringUtils.split(danAndWS[1], "\\|");

                for (int j = 0; j < dan.length; j++) {
                    dan[j] = Constant.HK_WS_MAP.get(dan[j])
                            + "@<font color='red'>"
                            + StringUtils.substringBetween(selectOdds + "|",
                                    dan[j] + "&", "|") + "</font>";
                }
                for (int k = 0; k < ws.length; k++) {
                    ws[k] = Constant.HK_WS_MAP.get(ws[k])
                            + "@<font color='red'>"
                            + StringUtils.substringBetween(selectOdds + "|",
                                    ws[k] + "&", "|") + "</font>";
                }
                result = result + "『"
                        + PlayTypeUtils.getPlayTypeFinalName(this.typeCode)
                        + "』<br/>" + fs + StringUtils.join(dan, ",")
                        + " <font color=\"red\">拖</font> "
                        + StringUtils.join(ws, ",");

            } else if (danAndWS.length == 1) {
                ws = StringUtils.split(danAndWS[0], "\\|");
                for (int k = 0; k < ws.length; k++) {
                    ws[k] = Constant.HK_WS_MAP.get(ws[k])
                            + "@<font color='red'>"
                            + StringUtils.substringBetween(selectOdds + "|",
                                    ws[k] + "&", "|") + "</font>";
                }
                result = result + "『"
                        + PlayTypeUtils.getPlayTypeFinalName(this.typeCode)
                        + "』<br/>" + fs + StringUtils.join(ws, ",");
            }

        } else if (typeCode.indexOf("HK_WBZ") != -1) {
            String[] ballNum = attribute.split("\\|");
            for (int j = 0; j < ballNum.length; j++) {
                String ballName = StringUtils.leftPad(ballNum[j], 2, '0');
                ballNum[j] = ballName
                        + "@<font color='red'>"
                        + StringUtils.substringBetween(selectOdds + "|",
                                ballName + "&", "|") + "</font>";
                ;
            }
            result = result + "『"
                    + PlayTypeUtils.getPlayTypeFinalName(this.typeCode)
                    + "』<br/>" + StringUtils.join(ballNum, ",");

        } else if (typeCode.indexOf("HK_LX") != -1) {
            String[] sxs = attribute.split("\\|");
            for (int j = 0; j < sxs.length; j++) {
                sxs[j] = Constant.HK_SX_MAP.get(sxs[j]);
            }
            result = result + "『"
                    + PlayTypeUtils.getPlayTypeFinalName(this.typeCode)
                    + "』@<font color='red'>" + this.odds + "</font><br/>";

            displayAttr = StringUtils.join(sxs, ",");

        }

        else if (this.attribute == null) {
            result = result + "『"
                    + PlayTypeUtils.getPlayTypeFinalName(this.typeCode)
                    + "』@<font color='red'>" + this.odds + "</font><br/>";
        } else {
            result = result + "『"
                    + PlayTypeUtils.getPlayTypeFinalName(this.typeCode)
                    + "』@<font color='red'>" + this.odds + "</font><br/>" + fs
                    + this.attribute.replace("|", ",");

        }
        result = result + displayAttr;
        return result;
    }

    public void setTypeCodeName2(String typeCodeName2) {
        this.typeCodeName2 = typeCodeName2;
    }

    public BigDecimal getOdds() {
        return odds;
    }

    public void setOdds(BigDecimal odds) {
        this.odds = odds;
    }

    public String getAttribute2() {
        return attribute2;
    }

    public void setAttribute2(String attribute2) {
        this.attribute2 = attribute2;
    }

    public Integer getMoneyTotal() {
        return moneyTotal;
    }

    public String getMoneyTotalDis() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(moneyTotal);
    }

    public void setMoneyTotal(Integer moneyTotal) {
        this.moneyTotal = moneyTotal;
    }

    public double getWinAmountTotal() {
        if ("9".equals(this.getWinState()))
            return 0;
        return winAmountTotal;
    }

    public String getWinAmountTotalDis() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(this.getWinAmountTotal());
    }

    public void setWinAmountTotal(Integer winAmountTotal) {
        this.winAmountTotal = winAmountTotal;
    }

    public String getRateChiefName() {
        String rateChiefName = rateChief.longValue() + "%";
        return rateChiefName;
    }

    public String getRateBranchName() {
        String rateBranchName = rateBranch.longValue() + "%";
        return rateBranchName;
    }

    public String getRateStockholderName() {
        String rateStockholderName = rateStockholder.longValue() + "%";
        return rateStockholderName;
    }

    public String getRateGenAgentName() {
        String rateGenAgentName = rateGenAgent.longValue() + "%";
        return rateGenAgentName;
    }

    public String getRateAgentName() {
        if (null == rateAgentName) {
            rateAgentName = rateAgent.longValue() + "%";
            return rateAgentName;
        } else {
            return rateAgentName;
        }
    }

    public BigDecimal getCommissionBranch() {
        return commissionBranch;
    }

    public String getCommissionBranchValue() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(1.0 * moneyTotal * commissionBranch.doubleValue()
                / 100);
    }

    /**
     * 界面上的显示需要
     * 
     * @return
     */
    public String getCommissionBranchValue2() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(100 - commissionBranch.doubleValue());
    }

    public void setCommissionBranch(BigDecimal commissionBranch) {
        this.commissionBranch = commissionBranch;
    }

    public BigDecimal getCommissionGenAgent() {
        return commissionGenAgent;
    }

    public String getCommissionGenAgentValue() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(1.0 * moneyTotal * commissionGenAgent.doubleValue()
                / 100);
    }

    /**
     * 界面上的显示需要
     * 
     * @return
     */
    public String getCommissionGenAgentValue2() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(100 - commissionGenAgent.doubleValue());
    }

    public void setCommissionGenAgent(BigDecimal commissionGenAgent) {
        this.commissionGenAgent = commissionGenAgent;
    }

    public BigDecimal getCommissionStockholder() {
        return commissionStockholder;
    }

    public String getCommissionStockholderValue() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(1.0 * moneyTotal * commissionStockholder.doubleValue()
                / 100);
    }

    /**
     * 界面上的显示需要
     * 
     * @return
     */
    public String getCommissionStockholderValue2() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(100 - commissionStockholder.doubleValue());
    }

    public void setCommissionStockholder(BigDecimal commissionStockholder) {
        this.commissionStockholder = commissionStockholder;
    }

    public BigDecimal getCommissionAgent() {
        return commissionAgent;
    }

    public String getCommissionAgentValue() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df
                .format(1.0 * moneyTotal * commissionAgent.doubleValue() / 100);
    }

    /**
     * 界面上的显示需要
     * 
     * @return
     */
    public String getCommissionAgentValue2() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(100 - commissionAgent.doubleValue());
    }

    public void setCommissionAgent(BigDecimal commissionAgent) {
        this.commissionAgent = commissionAgent;
    }

    public BigDecimal getCommissionMember() {
        return commissionMember;
    }

    public String getCommissionMemberValue() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(1.0 * moneyTotal * commissionMember.doubleValue()
                / 100);
    }

    /**
     * 界面上的显示需要
     * 
     * @return
     */
    public String getCommissionMemberValue2() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(100 - commissionMember.doubleValue());
    }

    public void setCommissionMember(BigDecimal commissionMember) {
        this.commissionMember = commissionMember;
    }

    public double getResultTotal() {
        if ("9".equals(this.getWinState()))
            return 0;
        return resultTotal;
    }

    /**
     * 总监结果值
     * 
     * @return
     */
    public String getResultChief() {
        //计算结果值（会员输赢*占成）
        double result = this.winAmountTotal * (this.rateChief.doubleValue())
                / 100;
        DecimalFormat df = new DecimalFormat("0.0");
        //四舍五入
        return df.format(result + 0.05);
    }

    /**
     * 分公司结果值
     * 
     * @return
     */
    public String getResultBranch() {
        //计算结果值（会员结果*占成）
        double result = this.winAmountTotal * (this.rateBranch.doubleValue())
                / 100;
        DecimalFormat df = new DecimalFormat("0.0");
        return df.format(result + 0.05);
    }

    /**
     * 股东结果值
     * 
     * @return
     */
    public String getResultStockholder() {
        //计算结果值（会员结果*占成）
        double result = this.winAmountTotal
                * (this.rateStockholder.doubleValue()) / 100;
        DecimalFormat df = new DecimalFormat("0.0");
        return df.format(result + 0.05);
    }

    /**
     * 总代理结果值
     * 
     * @return
     */
    public String getResultGenAgent() {
        //计算结果值（会员结果*占成）
        double result = this.winAmountTotal * (this.rateGenAgent.doubleValue())
                / 100;
        DecimalFormat df = new DecimalFormat("0.0");
        return df.format(result + 0.05);
    }

    /**
     * 代理结果值
     * 
     * @return
     */
    public String getResultAgent() {
        //计算结果值（会员结果*占成）
        double result = this.winAmountTotal * (this.rateAgent.doubleValue())
                / 100;
        DecimalFormat df = new DecimalFormat("0.0");
        return df.format(result + 0.05);
    }

    /**
     * 总监退水结果
     * 
     * @return
     */
    public String getBackWaterResultChief() {
        //计算结果值（投注额*下一级的退水*占成）
        double result = this.moneyTotal
                * (this.commissionBranch.doubleValue() / 100)
                * (this.rateChief.doubleValue()) / 100;
        DecimalFormat df = new DecimalFormat("0.0");
        return df.format(result + 0.05);
    }

    /**
     * 分公司退水结果
     * 
     * @return
     */
    public String getBackWaterResultBranch() {
        //计算结果值（投注额*下一级的退水*占成）
        double result = this.moneyTotal
                * (this.commissionStockholder.doubleValue() / 100)
                * (this.rateBranch.doubleValue()) / 100;
        DecimalFormat df = new DecimalFormat("0.0");
        return df.format(result + 0.05);
    }

    /**
     * 股东退水结果
     * 
     * @return
     */
    public String getBackWaterResultStockholder() {
        //计算结果值（投注额*下一级的退水*占成）
        double result = this.moneyTotal
                * (this.commissionGenAgent.doubleValue() / 100)
                * (this.rateStockholder.doubleValue()) / 100;
        DecimalFormat df = new DecimalFormat("0.0");
        return df.format(result + 0.05);
    }

    /**
     * 总代理退水结果
     * 
     * @return
     */
    public String getBackWaterResultGenAgent() {
        //计算结果值（投注额*下一级的退水*占成）
        double result = this.moneyTotal
                * (this.commissionAgent.doubleValue() / 100)
                * (this.rateGenAgent.doubleValue()) / 100;
        DecimalFormat df = new DecimalFormat("0.0");
        return df.format(result + 0.05);
    }

    /**
     * 代理退水结果
     * 
     * @return
     */
    public String getBackWaterResultAgent() {
        //计算结果值（投注额*下一级的退水*占成）
        double result = this.moneyTotal
                * (this.commissionMember.doubleValue() / 100)
                * (this.rateAgent.doubleValue()) / 100;
        DecimalFormat df = new DecimalFormat("0.0");
        return df.format(result + 0.05);
    }

    public String getResultTotalDis() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(this.getResultTotal());
    }

    public void setResultTotal(double resultTotal) {
        this.resultTotal = resultTotal;
    }

    public double getResultTotalNoRate() {
        if ("9".equals(this.getWinState()))
            return 0;
        return resultTotalNoRate;
    }

    public String getResultTotalNoRateDis() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(this.getResultTotalNoRate());
    }

    public void setResultTotalNoRate(double resultTotalNoRate) {
        this.resultTotalNoRate = resultTotalNoRate;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
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

    public long getRecordNum() {
        return recordNum;
    }

    public void setRecordNum(long recordNum) {
        this.recordNum = recordNum;
    }

    public String getWinState() {
        return winState;
    }

    public void setWinState(String winState) {
        this.winState = winState;
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
}
