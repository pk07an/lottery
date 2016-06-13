package com.npc.lottery.replenish.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import com.npc.lottery.common.Constant;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.util.PlayTypeUtils;

public class ReplenishHis implements Serializable, Comparable<ReplenishHis> {
    private static final long serialVersionUID = 1L;

    public static final String WIN_STATE_NOT_PRESENT = "0";//中奖状态：未开奖

    public static final String WIN_STATE_WIN = "1";//中奖状态：中奖

    public static final String WIN_STATE_NOT_WIN = "2";//中奖状态：未中奖

    public static final String WIN_STATE_PRIZE = "3";//中奖状态：已兑奖
    
    public static final String WIN_STATE_CANCEL = "4"; //已结算补货单状态为注消
    
    public static final String WIN_STATE_STOP = "5"; //已结算补货单状态为注消
    
    public static final String WIN_STATE_CANCEL_UNSETTLED = "6"; //未结算补货单状态为注消
    
    public static final String WIN_STATE_STOP_UNSETTLED = "7"; //未结算补货单状态为停开

    private java.lang.Long ID;
    private java.lang.String orderNo;
    private java.lang.String typeCode;
    private java.lang.Integer money;
    private long replenishUserId; //补货人ID
    private String replenishUserType;//补货人用户类型
    private long replenishAccUserId; //接受补货人ID
    private java.lang.String periodsNum;
    private java.lang.String plate;
    private java.util.Date bettingDate;
    private java.lang.String winState;
    private String winStateName;//状态名称
    private java.math.BigDecimal winAmount;
    private java.math.BigDecimal odds;
    private java.math.BigDecimal commission;
    private java.math.BigDecimal rate;
    private long updateUser;
    private java.util.Date updateDate;
    private java.lang.String remark;
    private String attribute; //纪录连码投注时所选择的球
    private String selectedOdds; //用来存五不中、过关、生肖连和尾数连的每个选择球的赔率
    private Long chiefStaff;
    private Long branchStaff;
    private Long stockHolderStaff;
    private Long genAgenStaff;
    private Long agentStaff;
    private java.math.BigDecimal rateChief = BigDecimal.ZERO;
    private java.math.BigDecimal rateBranch = BigDecimal.ZERO;
    private java.math.BigDecimal rateStockHolder = BigDecimal.ZERO;
    private java.math.BigDecimal rateGenAgent = BigDecimal.ZERO;
    private java.math.BigDecimal rateAgent = BigDecimal.ZERO;

    private java.math.BigDecimal commissionChief = BigDecimal.ZERO;
    private java.math.BigDecimal commissionBranch = BigDecimal.ZERO;
    private java.math.BigDecimal commissionStockHolder = BigDecimal.ZERO;
    private java.math.BigDecimal commissionGenAgent = BigDecimal.ZERO;
    private java.math.BigDecimal commissionAgent = BigDecimal.ZERO;
    private java.math.BigDecimal commissionMember = BigDecimal.ZERO;
    private java.math.BigDecimal odds2;
    private String commissionType;//拥金类型

    private String stringOdd; //显示在补货页面上的赔率
    
    private String message; //存临时信息

    private static final HashMap<String, String> MA_CODE = new HashMap<String, String>();
    private static final HashMap<String, String> BET_CODE = new HashMap<String, String>();
    static {
        MA_CODE.put("ZM1", "正碼一");
        MA_CODE.put("ZM2", "正碼二");
        MA_CODE.put("ZM3", "正碼三");
        MA_CODE.put("ZM4", "正碼四");
        MA_CODE.put("ZM5", "正碼五");
        MA_CODE.put("ZM6", "正碼六");
        BET_CODE.put("DAN", "單");
        BET_CODE.put("S", "雙");
        BET_CODE.put("DA", "大");
        BET_CODE.put("X", "小");
        BET_CODE.put("RED", "紅波");
        BET_CODE.put("GREEN", "綠波");
        BET_CODE.put("BLUE", "藍波");

    }

    @Override
    public int compareTo(ReplenishHis o) {
        return o.bettingDate.compareTo(this.bettingDate);
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public java.lang.Long getID() {
        return ID;
    }

    public java.lang.String getOrderNo() {
        return orderNo;
    }

    public java.lang.String getTypeCode() {
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

    /**
     * 获取报表上所显示的玩法类型+赔率组合
     * 
     * @return
     */
    public String getTypeCodeNameOdd() {
        String result = "";

        result = PlayTypeUtils.getPlayTypeSubName(this.typeCode);

        if ("HK_STRAIGHTTHROUGH_3Z2".equals(this.typeCode)) {
            result = result + "『"
                    + PlayTypeUtils.getPlayTypeFinalName(this.typeCode)
                    + "』@<font color='red'>" + this.odds
                    + "</font> 中叁 @<font color='red'>" + this.odds2
                    + "</font><br/>" + this.attribute.replace("|", ",");

        } else if ("HK_STRAIGHTTHROUGH_2ZT".equals(this.typeCode)) {
            result = result + "『"
                    + PlayTypeUtils.getPlayTypeFinalName(this.typeCode)
                    + "』@<font color='red'>" + this.odds
                    + "</font> 中二 @<font color='red'>" + this.odds2
                    + "</font><br/>" + this.attribute.replace("|", ",");

        } else if ("HK_GG".equals(this.typeCode)) {
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
                        + StringUtils.substringBetween(selectedOdds + "|",
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
                            + StringUtils.substringBetween(selectedOdds + "|",
                                    dan[j] + "&", "|") + "</font>";
                }
                for (int k = 0; k < sx.length; k++) {
                    sx[k] = Constant.HK_SX_MAP.get(sx[k])
                            + "@<font color='red'>"
                            + StringUtils.substringBetween(selectedOdds + "|",
                                    sx[k] + "&", "|") + "</font>";
                }
                result = result + "『"
                        + PlayTypeUtils.getPlayTypeFinalName(this.typeCode)
                        + "』<br/>" + StringUtils.join(dan, ",")
                        + StringUtils.join(sx, ",");

            } else if (danAndSX.length == 1) {
                sx = StringUtils.split(danAndSX[0], "\\|");
                for (int k = 0; k < sx.length; k++) {
                    sx[k] = Constant.HK_SX_MAP.get(sx[k])
                            + "@<font color='red'>"
                            + StringUtils.substringBetween(selectedOdds + "|",
                                    sx[k] + "&", "|") + "</font>";
                }
                result = result + "『"
                        + PlayTypeUtils.getPlayTypeFinalName(this.typeCode)
                        + "』<br/>" + StringUtils.join(sx, ",");
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
                            + StringUtils.substringBetween(selectedOdds + "|",
                                    dan[j] + "&", "|") + "</font>";
                }
                for (int k = 0; k < ws.length; k++) {
                    ws[k] = Constant.HK_WS_MAP.get(ws[k])
                            + "@<font color='red'>"
                            + StringUtils.substringBetween(selectedOdds + "|",
                                    ws[k] + "&", "|") + "</font>";
                }
                result = result + "『"
                        + PlayTypeUtils.getPlayTypeFinalName(this.typeCode)
                        + "』<br/>" + StringUtils.join(dan, ",")
                        + " <font color=\"red\">拖</font> "
                        + StringUtils.join(ws, ",");

            } else if (danAndWS.length == 1) {
                ws = StringUtils.split(danAndWS[0], "\\|");
                for (int k = 0; k < ws.length; k++) {
                    ws[k] = Constant.HK_WS_MAP.get(ws[k])
                            + "@<font color='red'>"
                            + StringUtils.substringBetween(selectedOdds + "|",
                                    ws[k] + "&", "|") + "</font>";
                }
                result = result + "『"
                        + PlayTypeUtils.getPlayTypeFinalName(this.typeCode)
                        + "』<br/>" + StringUtils.join(ws, ",");
            }

        } else if (typeCode.indexOf("HK_WBZ") != -1) {
            String[] ballNum = attribute.split("\\|");
            for (int j = 0; j < ballNum.length; j++) {
                String ballName = StringUtils.leftPad(ballNum[j], 2, '0');
                ballNum[j] = ballName
                        + "@<font color='red'>"
                        + StringUtils.substringBetween(selectedOdds + "|",
                                ballName + "&", "|") + "</font>";
                ;
            }
            result = result + "『"
                    + PlayTypeUtils.getPlayTypeFinalName(this.typeCode)
                    + "』<br/>" + StringUtils.join(ballNum, ",");

        } else if (this.attribute == null) {
            if (typeCode.indexOf("HK_ZM_ZH") != -1) {
                result = "『"
                        + PlayTypeUtils.getPlayTypeFinalName(this.typeCode)
                        + "』@<font color='red'>" + this.odds + "</font><br/>";
            } else {
                result = result + "『"
                        + PlayTypeUtils.getPlayTypeFinalName(this.typeCode)
                        + "』@<font color='red'>" + this.odds + "</font><br/>";
            }
        } else {
            result = result + "『"
                    + PlayTypeUtils.getPlayTypeFinalName(this.typeCode)
                    + "』@<font color='red'>" + this.odds + "</font><br/>"
                    + this.attribute.replace("|", ",");

        }

        return result;
    }

    /**
     * 根据typeCode获取玩法类型
     * 
     * @return
     */
    public String getPlayTypeName() {

        String playTypeName = "错误数据";

        if (null != typeCode) {
            if (typeCode.startsWith("BJ")) {
                playTypeName = "北京賽車(PK10)";
            } else if (typeCode.startsWith("GDKLSF")) {
                playTypeName = "廣東快樂十分";
            } else if (typeCode.startsWith("CQSSC")) {
                playTypeName = "重慶時時彩";
            } else if (typeCode.startsWith("K3")) {
            	playTypeName = "江蘇骰寶(快3)";
            } else if (typeCode.startsWith("NC")) {
            	playTypeName = "幸運農場";
            }
        }

        return playTypeName;
    }

    public java.lang.Integer getMoney() {
        return money;
    }

    /**
     * 退水
     * 
     * @return
     */
    public Float getBackWater() {

        //根据投注用户用户类型，确认所采用的佣金
        BigDecimal commissionUser = new BigDecimal(0);

        if (ManagerStaff.USER_TYPE_AGENT
                .equalsIgnoreCase(this.replenishUserType)) {
            //代理
            commissionUser = this.commissionAgent;
        } else if (ManagerStaff.USER_TYPE_GEN_AGENT
                .equalsIgnoreCase(this.replenishUserType)) {
            //总代理
            commissionUser = this.commissionGenAgent;
        } else if (ManagerStaff.USER_TYPE_STOCKHOLDER
                .equalsIgnoreCase(this.replenishUserType)) {
            //股东
            commissionUser = this.commissionStockHolder;
        } else if (ManagerStaff.USER_TYPE_BRANCH
                .equalsIgnoreCase(this.replenishUserType)) {
            //分公司
            commissionUser = this.commissionBranch;
        }else if (ManagerStaff.USER_TYPE_CHIEF
                .equalsIgnoreCase(this.replenishUserType)) {
            //总监
            commissionUser = this.commissionChief;
        }

        return money.intValue() * commissionUser.floatValue() / 100;
    }

    /**
     * 退水后结果
     * 
     * @return
     */
    public Float getBackWaterResult() {
        //退水后结果 = 退水 + 补货输赢
        return this.getBackWater() + this.getWinAmountResult();
    }

    public String getSelectedOdds() {
        return selectedOdds;
    }

    public void setSelectedOdds(String selectedOdds) {
        this.selectedOdds = selectedOdds;
    }

    public long getReplenishUserId() {
        return replenishUserId;
    }

    public long getReplenishAccUserId() {
        return replenishAccUserId;
    }

    public java.lang.String getPeriodsNum() {
        return periodsNum;
    }

    public java.lang.String getPlate() {
        return plate;
    }

    public java.util.Date getBettingDate() {
        return bettingDate;
    }

    public java.lang.String getWinState() {
        return winState;
    }

    /**
     * 补货输赢
     * 
     * @return
     */
    public Float getWinAmountResult() {

        Float winAmountResult = null;

        if (WIN_STATE_WIN.equals(winState)) {
            winAmountResult = this.winAmount.floatValue();
        } else if (WIN_STATE_NOT_WIN.equals(winState)) {
            winAmountResult = -1 * money.floatValue();
        }

        return winAmountResult;

    }

    public java.math.BigDecimal getOdds() {
        return odds;
    }

    public long getUpdateUser() {
        return updateUser;
    }

    public java.util.Date getUpdateDate() {
        return updateDate;
    }

    public java.lang.String getRemark() {
        return remark;
    }

    public void setID(java.lang.Long iD) {
        ID = iD;
    }

    public void setOrderNo(java.lang.String orderNo) {
        this.orderNo = orderNo;
    }

    public String getStringOdd() {
        return stringOdd;
    }

    public void setStringOdd(String stringOdd) {
        this.stringOdd = stringOdd;
    }

    public void setTypeCode(java.lang.String typeCode) {
        this.typeCode = typeCode;
    }

    public void setMoney(java.lang.Integer money) {
        this.money = money;
    }

    public void setReplenishUserId(long replenishUserId) {
        this.replenishUserId = replenishUserId;
    }

    public void setReplenishAccUserId(long replenishAccUserId) {
        this.replenishAccUserId = replenishAccUserId;
    }

    public void setPeriodsNum(java.lang.String periodsNum) {
        this.periodsNum = periodsNum;
    }

    public Long getChiefStaff() {
        return chiefStaff;
    }

    public java.math.BigDecimal getCommissionChief() {
        return commissionChief;
    }

    public void setCommissionChief(java.math.BigDecimal commissionChief) {
        this.commissionChief = commissionChief;
    }

    public Long getBranchStaff() {
        return branchStaff;
    }

    public Long getStockHolderStaff() {
        return stockHolderStaff;
    }

    public Long getGenAgenStaff() {
        return genAgenStaff;
    }

    public String getCommissionType() {
        return commissionType;
    }

    public void setCommissionType(String commissionType) {
        this.commissionType = commissionType;
    }

    public Long getAgentStaff() {
        return agentStaff;
    }

    public java.math.BigDecimal getRateChief() {
        return rateChief;
    }

    public java.math.BigDecimal getCommissionBranch() {
        return commissionBranch;
    }

    public java.math.BigDecimal getCommissionStockHolder() {
        return commissionStockHolder;
    }

    public java.math.BigDecimal getCommissionGenAgent() {
        return commissionGenAgent;
    }

    public java.math.BigDecimal getCommissionAgent() {
        return commissionAgent;
    }

    public java.math.BigDecimal getCommissionMember() {
        return commissionMember;
    }

    public void setCommissionBranch(java.math.BigDecimal commissionBranch) {
        this.commissionBranch = commissionBranch;
    }

    public void setCommissionStockHolder(
            java.math.BigDecimal commissionStockHolder) {
        this.commissionStockHolder = commissionStockHolder;
    }

    public void setCommissionGenAgent(java.math.BigDecimal commissionGenAgent) {
        this.commissionGenAgent = commissionGenAgent;
    }

    public void setCommissionAgent(java.math.BigDecimal commissionAgent) {
        this.commissionAgent = commissionAgent;
    }

    public void setCommissionMember(java.math.BigDecimal commissionMember) {
        this.commissionMember = commissionMember;
    }

    public java.math.BigDecimal getOdds2() {
        return odds2;
    }

    public void setOdds2(java.math.BigDecimal odds2) {
        this.odds2 = odds2;
    }

    public java.math.BigDecimal getRateBranch() {
        return rateBranch;
    }

    public java.math.BigDecimal getRateStockHolder() {
        return rateStockHolder;
    }

    public java.math.BigDecimal getRateGenAgent() {
        return rateGenAgent;
    }

    public java.math.BigDecimal getRateAgent() {
        return rateAgent;
    }

    public void setChiefStaff(Long chiefStaff) {
        this.chiefStaff = chiefStaff;
    }

    public void setBranchStaff(Long branchStaff) {
        this.branchStaff = branchStaff;
    }

    public void setStockHolderStaff(Long stockHolderStaff) {
        this.stockHolderStaff = stockHolderStaff;
    }

    public void setGenAgenStaff(Long genAgenStaff) {
        this.genAgenStaff = genAgenStaff;
    }

    public void setAgentStaff(Long agentStaff) {
        this.agentStaff = agentStaff;
    }

    public void setRateChief(java.math.BigDecimal rateChief) {
        this.rateChief = rateChief;
    }

    public void setRateBranch(java.math.BigDecimal rateBranch) {
        this.rateBranch = rateBranch;
    }

    public void setRateStockHolder(java.math.BigDecimal rateStockHolder) {
        this.rateStockHolder = rateStockHolder;
    }

    public void setRateGenAgent(java.math.BigDecimal rateGenAgent) {
        this.rateGenAgent = rateGenAgent;
    }

    public void setRateAgent(java.math.BigDecimal rateAgent) {
        this.rateAgent = rateAgent;
    }

    public void setPlate(java.lang.String plate) {
        this.plate = plate;
    }

    public void setBettingDate(java.util.Date bettingDate) {
        this.bettingDate = bettingDate;
    }

    public void setWinState(java.lang.String winState) {
        this.winState = winState;
    }

    public void setOdds(java.math.BigDecimal odds) {
        this.odds = odds;
    }

    public void setUpdateUser(long updateUser) {
        this.updateUser = updateUser;
    }

    public void setUpdateDate(java.util.Date updateDate) {
        this.updateDate = updateDate;
    }

    public java.math.BigDecimal getCommission() {
        return commission;
    }

    public java.math.BigDecimal getRate() {
        return rate;
    }

    public void setCommission(java.math.BigDecimal commission) {
        this.commission = commission;
    }

    public void setRate(java.math.BigDecimal rate) {
        this.rate = rate;
    }

    public void setRemark(java.lang.String remark) {
        this.remark = remark;
    }

    public java.math.BigDecimal getWinAmount() {
        return winAmount;
    }

    public void setWinAmount(java.math.BigDecimal winAmount) {
        this.winAmount = winAmount;
    }

    public String getReplenishUserType() {
        return replenishUserType;
    }

    public void setReplenishUserType(String replenishUserType) {
        this.replenishUserType = replenishUserType;
    }
    
    public String getWinStateName() {
    	if(this.WIN_STATE_CANCEL.equals(this.winState) || this.WIN_STATE_CANCEL_UNSETTLED.equals(this.winState)){
    		winStateName="已注銷";
    	}else if(this.WIN_STATE_STOP.equals(this.winState) || this.WIN_STATE_STOP_UNSETTLED.equals(this.winState)){
    		winStateName="停開";
    	}else{
    		winStateName="成功補出";
    	}
    	return winStateName;
    }
    public void setWinStateName(String winStateName) {
    	this.winStateName = winStateName;
    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}