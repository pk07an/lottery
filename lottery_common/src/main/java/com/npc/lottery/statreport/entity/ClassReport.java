package com.npc.lottery.statreport.entity;

import java.io.Serializable;
import java.text.DecimalFormat;

import com.npc.lottery.common.Constant;
import com.npc.lottery.sysmge.entity.ManagerStaff;

/**
 * 分类报表
 * 
 * @author User
 *
 */
public class ClassReport implements Serializable, Cloneable {

    public final static String RECORDTYPE_BETTING = "0";//记录类型：投注记录

    public final static String RECORDTYPE_REPLENISH = "1";//记录类型：补货记录

    private Long loginUserID;//查看分类报表的当前登陆用户ID

    private String loginUserType;//查看分类报表的当前登陆用户类型（此数据需要填充，否则不能使用下线应收属性（subordinate和subordinateReal）

    private String typeCode;//下注类型，非页面上的下注类型

    private String commissionType;//佣金类型，对应页面上的投注类型

    private Long turnover;//成交笔数

    private Double amount;//投注总额

    private Double validAmount;//有效金额

    private Double memberAmount;//会员输赢

    private Double subordinateAmount;//应收下线

    private Double rate;//占成

    private Double realAmount;//实占注额

    private Double ratePerTotal;//占货比%

    private Double realWin;//实占输赢

    private Double realBackWater;//实占退水

    private Double realResult;//实占结果

    private Double winBackWater;//赚取退水

    private Double winBackWaterResult;//赚水后结果

    private Double offerSuperior;//贡献上级

    private Double paySuperior;//应付上级

    private Double winBackWaterReple;//抵扣补货及赚水后结果

    Double subordinate = 0D;//下线应收（此属性需要根据loginUserType判断取值，故使用的前提是loginUserType已经填充了数据）

    Double subordinateChief = 0D; //下线应收（总监）

    Double subordinateBranch = 0D; //下线应收（分公司）

    Double subordinateStockholder = 0D; //下线应收（股东）

    Double subordinateGenAgent = 0D; //下线应收（总代理）

    Double subordinateAgent = 0D; //下线应收（代理）

    Double subordinateReal = 0D;//下线应收（实占输赢，即不考虑退水的数值）（此属性需要根据loginUserType判断取值，故使用的前提是loginUserType已经填充了数据）

    Double subordinateChiefReal = 0D; //下线应收（总监）（实占输赢，即不考虑退水的数值）

    Double subordinateBranchReal = 0D; //下线应收（分公司）（实占输赢，即不考虑退水的数值）

    Double subordinateStockholderReal = 0D; //下线应收（股东）（实占输赢，即不考虑退水的数值）

    Double subordinateGenAgentReal = 0D; //下线应收（总代理）（实占输赢，即不考虑退水的数值）

    Double subordinateAgentReal = 0D; //下线应收（代理）（实占输赢，即不考虑退水的数值）

    /**
     * 克隆对象
     */
    public ClassReport clone() throws CloneNotSupportedException {
        return (ClassReport) super.clone();
    }

    public Long getLoginUserID() {
        return loginUserID;
    }

    public void setLoginUserID(Long loginUserID) {
        this.loginUserID = loginUserID;
    }

    public String getLoginUserType() {
        return loginUserType;
    }

    public void setLoginUserType(String loginUserType) {
        this.loginUserType = loginUserType;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public Long getTurnover() {
        return turnover;
    }

    public Double getValidAmount() {
        return validAmount;
    }

    public void setValidAmount(Double validAmount) {
        this.validAmount = validAmount;
    }

    public void setTurnover(Long turnover) {
        this.turnover = turnover;
    }

    public Double getAmount() {
        return amount;
    }

    public String getAmountDis() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(amount);
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getMemberAmount() {
        return memberAmount;
    }

    public String getMemberAmountDis() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(memberAmount);
    }

    public void setMemberAmount(Double memberAmount) {
        this.memberAmount = memberAmount;
    }

    public Double getSubordinateAmount() {
        return subordinateAmount;
    }

    public String getSubordinateAmountDis() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(subordinateAmount);
    }

    public void setSubordinateAmount(Double subordinateAmount) {
        this.subordinateAmount = subordinateAmount;
    }

    public Double getRate() {
        return rate;
    }

    public String getRateDis() {
        DecimalFormat df = new DecimalFormat("0.000");

        String result = df.format(rate);

        return (result);
    }

    /**
     * 通过实占注额除以有效金额获取到占成
     * 
     * @return
     */
    public String getRateDis2() {
        DecimalFormat df = new DecimalFormat("0.000");

        String result = df.format((realAmount / validAmount) * 100);

        return (result);
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getRealAmount() {
        return realAmount;
    }

    public String getRealAmountDis() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(realAmount);
    }

    public void setRealAmount(Double realAmount) {
        this.realAmount = realAmount;
    }

    public Double getRatePerTotal() {
        return ratePerTotal;
    }

    public String getRatePerTotalDis() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(ratePerTotal);
    }

    public void setRatePerTotal(Double ratePerTotal) {
        this.ratePerTotal = ratePerTotal;
    }

    public Double getRealWin() {
        return realWin;
    }

    public String getRealWinDis() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(realWin);
    }

    public void setRealWin(Double realWin) {
        this.realWin = realWin;
    }

    public Double getRealBackWater() {
        return realBackWater;
    }

    public String getRealBackWaterDis() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(realBackWater);
    }

    public void setRealBackWater(Double realBackWater) {
        this.realBackWater = realBackWater;
    }

    public Double getRealResult() {
        return realResult;
    }

    public String getRealResultDis() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(realResult);
    }

    public void setRealResult(Double realResult) {
        this.realResult = realResult;
    }

    public Double getWinBackWater() {
        return winBackWater;
    }

    public String getWinBackWaterDis() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(-1 * winBackWater);
    }

    public void setWinBackWater(Double winBackWater) {
        this.winBackWater = winBackWater;
    }

    public Double getWinBackWaterResult() {
        return winBackWaterResult;
    }

    /**
     * 赚水后结果，通过实占结果+赚取退水计算获取
     * 
     * @return
     */
    public String getWinBackWaterResultDis2() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(realResult - winBackWater);
    }

    public String getWinBackWaterResultDis() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(winBackWaterResult);
    }

    public void setWinBackWaterResult(Double winBackWaterResult) {
        this.winBackWaterResult = winBackWaterResult;
    }

    public Double getOfferSuperior() {
        return offerSuperior;
    }

    public String getOfferSuperiorDis() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(offerSuperior);
    }

    public void setOfferSuperior(Double offerSuperior) {
        this.offerSuperior = offerSuperior;
    }

    public Double getPaySuperior() {
        return paySuperior;
    }

    public String getPaySuperiorDis() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(paySuperior);
    }

    /**
     * 通过 应收下线－赚水后结果计算获得
     * 
     * @return
     */
    public String getPaySuperiorDis2() {
        DecimalFormat df = new DecimalFormat("0.00");

        //执行方法填充数据
        this.getSubordinate();
        //注意：winBackWaterResult = realWin + winBackWater
        return df.format(subordinate - (realResult - winBackWater));
    }

    public void setPaySuperior(Double paySuperior) {
        this.paySuperior = paySuperior;
    }

    public Double getWinBackWaterReple() {
        return winBackWaterReple;
    }

    public String getWinBackWaterRepleDis() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(winBackWaterReple);
    }

    public void setWinBackWaterReple(Double winBackWaterReple) {
        this.winBackWaterReple = winBackWaterReple;
    }

    public String getCommissionType() {
        return commissionType;
    }

    public String getCommissionTypeName() {

        String result = "<font color='#FF0000'>錯誤資料</font>";

        String temp = null;

        temp = Constant.GDKLSF_COMMISSION_TYPE.get(commissionType);
        if (null != temp && temp.trim().length() > 0) {
            result = "廣東快樂十分&nbsp;&nbsp;" + temp;
        }
        temp = Constant.CQSSC_COMMISSION_TYPE.get(commissionType);
        if (null != temp && temp.trim().length() > 0) {
            result = "重慶時時彩&nbsp;&nbsp;" + temp;
        }
        temp = Constant.HKLHC_COMMISSION_TYPE.get(commissionType);
        if (null != temp && temp.trim().length() > 0) {
            result = "香港六合彩&nbsp;&nbsp;" + temp;
        }
        temp = Constant.BJSC_COMMISSION_TYPE.get(commissionType);
        if (null != temp && temp.trim().length() > 0) {
            result = "北京赛车&nbsp;&nbsp;" + temp;
        }
        temp = Constant.NC_COMMISSION_TYPE.get(commissionType);
        if (null != temp && temp.trim().length() > 0) {
        	result = "幸运农场&nbsp;&nbsp;" + temp;
        }

        return result;
    }

    public String getCommissionTypeName2() {

        String result = "<font color=#FF0000>錯誤資料</font>";

        String temp = null;

        temp = Constant.GDKLSF_COMMISSION_TYPE.get(commissionType);
        if (null != temp && temp.trim().length() > 0) {
            result = temp;
        }
        temp = Constant.CQSSC_COMMISSION_TYPE.get(commissionType);
        if (null != temp && temp.trim().length() > 0) {
            result = temp;
        }
        temp = Constant.HKLHC_COMMISSION_TYPE.get(commissionType);
        if (null != temp && temp.trim().length() > 0) {
            result = temp;
        }
        temp = Constant.BJSC_COMMISSION_TYPE.get(commissionType);
        if (null != temp && temp.trim().length() > 0) {
            result = temp;
        }
        temp = Constant.NC_COMMISSION_TYPE.get(commissionType);
        if (null != temp && temp.trim().length() > 0) {
        	result = temp;
        }

        return result;
    }

    public void setCommissionType(String commissionType) {
        this.commissionType = commissionType;
    }

    public Double getSubordinateChief() {
        return subordinateChief;
    }

    public String getSubordinateChiefDis() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(subordinateChief);
    }

    public void setSubordinateChief(Double subordinateChief) {
        this.subordinateChief = subordinateChief;
    }

    public Double getSubordinateBranch() {
        return subordinateBranch;
    }

    public String getSubordinateBranchDis() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(subordinateBranch);
    }

    public void setSubordinateBranch(Double subordinateBranch) {
        this.subordinateBranch = subordinateBranch;
    }

    public Double getSubordinateStockholder() {
        return subordinateStockholder;
    }

    public String getSubordinateStockholderDis() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(subordinateStockholder);
    }

    public void setSubordinateStockholder(Double subordinateStockholder) {
        this.subordinateStockholder = subordinateStockholder;
    }

    public Double getSubordinateGenAgent() {
        return subordinateGenAgent;
    }

    public String getSubordinateGenAgentDis() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(subordinateGenAgent);
    }

    public void setSubordinateGenAgent(Double subordinateGenAgent) {
        this.subordinateGenAgent = subordinateGenAgent;
    }

    public Double getSubordinateAgent() {
        return subordinateAgent;
    }

    public String getSubordinateAgentDis() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(subordinateAgent);
    }

    public void setSubordinateAgent(Double subordinateAgent) {
        this.subordinateAgent = subordinateAgent;
    }

    public Double getSubordinateChiefReal() {
        return subordinateChiefReal;
    }

    public String getSubordinateChiefRealDis() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(subordinateChiefReal);
    }

    public void setSubordinateChiefReal(Double subordinateChiefReal) {
        this.subordinateChiefReal = subordinateChiefReal;
    }

    public Double getSubordinateBranchReal() {
        return subordinateBranchReal;
    }

    public String getSubordinateBranchRealDis() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(subordinateBranchReal);
    }

    public void setSubordinateBranchReal(Double subordinateBranchReal) {
        this.subordinateBranchReal = subordinateBranchReal;
    }

    public Double getSubordinateStockholderReal() {
        return subordinateStockholderReal;
    }

    public String getSubordinateStockholderRealDis() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(subordinateStockholderReal);
    }

    public void setSubordinateStockholderReal(Double subordinateStockholderReal) {
        this.subordinateStockholderReal = subordinateStockholderReal;
    }

    public Double getSubordinateGenAgentReal() {
        return subordinateGenAgentReal;
    }

    public String getSubordinateGenAgentRealDis() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(subordinateGenAgentReal);
    }

    public void setSubordinateGenAgentReal(Double subordinateGenAgentReal) {
        this.subordinateGenAgentReal = subordinateGenAgentReal;
    }

    public Double getSubordinateAgentReal() {
        return subordinateAgentReal;
    }

    public String getSubordinateAgentRealDis() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(subordinateAgentReal);
    }

    public void setSubordinateAgentReal(Double subordinateAgentReal) {
        this.subordinateAgentReal = subordinateAgentReal;
    }

    /**
     * 下线应收
     * （此属性需要根据loginUserType判断取值，故使用的前提是loginUserType已经填充了数据）
     * 
     * @return
     */
    public Double getSubordinate() {

        if (ManagerStaff.USER_TYPE_CHIEF.equalsIgnoreCase(this.loginUserType)) {
            //总监
            subordinate = this.subordinateChief;
        } else if (ManagerStaff.USER_TYPE_BRANCH
                .equalsIgnoreCase(this.loginUserType)) {
            //分公司
            subordinate = this.subordinateBranch;
        } else if (ManagerStaff.USER_TYPE_STOCKHOLDER
                .equalsIgnoreCase(this.loginUserType)) {
            //股东
            subordinate = this.subordinateStockholder;
        } else if (ManagerStaff.USER_TYPE_GEN_AGENT
                .equalsIgnoreCase(this.loginUserType)) {
            //总代理
            subordinate = this.subordinateGenAgent;
        } else if (ManagerStaff.USER_TYPE_AGENT
                .equalsIgnoreCase(this.loginUserType)) {
            //代理
            subordinate = this.subordinateAgent;
        }

        return subordinate;
    }

    /**
     * 下线应收
     * （此属性需要根据loginUserType判断取值，故使用的前提是loginUserType已经填充了数据）
     * 
     * @return
     */
    public String getSubordinateDis() {

        //执行方法填充数据
        this.getSubordinate();

        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(subordinate);
    }

    /**
     * 下线应收（实占输赢，即不考虑退水的数值）
     * （此属性需要根据loginUserType判断取值，故使用的前提是loginUserType已经填充了数据）
     * 
     * @return
     */
    public Double getSubordinateReal() {

        if (ManagerStaff.USER_TYPE_CHIEF.equalsIgnoreCase(this.loginUserType)) {
            //总监
            subordinateReal = this.subordinateChiefReal;
        } else if (ManagerStaff.USER_TYPE_BRANCH
                .equalsIgnoreCase(this.loginUserType)) {
            //分公司
            subordinateReal = this.subordinateBranchReal;
        } else if (ManagerStaff.USER_TYPE_STOCKHOLDER
                .equalsIgnoreCase(this.loginUserType)) {
            //股东
            subordinateReal = this.subordinateStockholderReal;
        } else if (ManagerStaff.USER_TYPE_GEN_AGENT
                .equalsIgnoreCase(this.loginUserType)) {
            //总代理
            subordinateReal = this.subordinateGenAgentReal;
        } else if (ManagerStaff.USER_TYPE_AGENT
                .equalsIgnoreCase(this.loginUserType)) {
            //代理
            subordinateReal = this.subordinateAgentReal;
        }

        return subordinateReal;
    }

    /**
     * 下线应收（实占输赢，即不考虑退水的数值）
     * （此属性需要根据loginUserType判断取值，故使用的前提是loginUserType已经填充了数据）
     * 
     * @return
     */
    public String getSubordinateRealDis() {

        //执行方法填充数据
        this.getSubordinateReal();

        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(subordinateReal);
    }

    /**
     * 下线所对应的退水
     * 
     * @return
     */
    public Double getSubordinateCommision() {

        //执行方法填充数据
        this.getSubordinate();
        this.getSubordinateReal();

        return (subordinate - subordinateReal);
    }

    /**
     * 下线所对应的退水
     * 
     * @return
     */
    public String getSubordinateCommisionDis() {

        //执行方法填充数据
        this.getSubordinateCommision();

        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(subordinate - subordinateReal);
    }
}
