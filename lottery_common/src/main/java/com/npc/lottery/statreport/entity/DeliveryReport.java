package com.npc.lottery.statreport.entity;

import java.io.Serializable;
import java.text.DecimalFormat;

import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.MemberStaff;

/**
 * 
 * 交收报表实体类
 * 
 */
public class DeliveryReport implements Serializable, Cloneable {

    public final static String RECORDTYPE_BETTING = "0";//记录类型：投注记录

    public final static String RECORDTYPE_REPLENISH = "1";//记录类型：补货记录

    private Long userID;//用户ID

    private String userType;//用户类型

    private Long loginUserID;//查看交收报表的当前登陆用户ID

    private String loginUserType;//查看交收报表的当前登陆用户类型（此数据需要填充，否则不能使用下线应收属性（subordinateValue和subordinateRealValue）

    private String recordType;//记录类型

    private String subordinate;//下级登陆账号

    private String userName;//用户名称

    private Long turnover;//成交笔数

    private Double amount;//投注总额

    private Double validAmount;//有效金额

    private Double memberAmount;//会员输赢

    private Double memberBackWater;//会员退水

    private Double subordinateAmount;//应收下线

    private Double winBackWater;//赚取退水

    private Double winBackWaterReple;//抵扣补货及赚水后结果

    private Double realResult;//实占结果

    private Double rateValidAmount;//实占注额（TODO 后续需要优化，存储过程中计算）

    private Double realResultPer;//占货比

    private Double realWin = null;//实占输赢

    private Double realBackWater = null;//实占退水

    private Double winBackWaterResult;//赚水后结果

    private Double paySuperior;//应付上级

    private Double offerSuperior = null;//贡献上级

    private Double rate;//占成

    //private Double rateChief;//总监占成

    //private Double rateBranch;//分公司占成

    //private Double rateStockholder;//股东占成

    //private Double rateGenAgent;//总代理占成

    //private Double rateAgent;//代理占成

    private Double rateChiefSet;//总监占成设置值

    private Double rateBranchSet;//分公司占成设置值

    private Double rateStockholderSet;//股东占成设置值

    private Double rateGenAgentSet;//总代理占成设置值

    private Double rateAgentSet;//代理占成设置值

    private Double moneyRateChief;//总监实占注额

    private Double moneyRateBranch;//分公司实占注额

    private Double moneyRateStockholder;//股东实占注额

    private Double moneyRateGenAgent;//总代理实占注额

    private Double moneyRateAgent;//代理实占注额

    private Double commissionBranchSet;//分公司退水设置值

    private Double commissionStockholderSet;//股东退水设置值

    private Double commissionGenAgentSet;//总代理退水设置值

    private Double commissionAgentSet;//代理退水设置值

    private Double commissionBranch;//分公司佣金

    private Double commissionStockholder;//股东佣金

    private Double commissionGenAgent;//总代理佣金

    private Double commissionAgent;//代理佣金

    private Double commissionMember;//会员佣金

    private Double commission = null;//赚取退水

    //TODO subordinateRealValue 的计算目前的计算方式当占成变化时有问题，后续需要修改成存储过程中计算
    private Double subordinateValue = 0D;//下线应收（此属性需要根据loginUserType判断取值，故使用的前提是loginUserType已经填充了数据）

    private Double subordinateRealValue = 0D;//下线应收弹出窗口的输赢（实占输赢，即不考虑退水的数值）（此属性需要根据loginUserType判断取值，故使用的前提是loginUserType已经填充了数据）

    private Double subordinateBackWater = 0D;//下线应收弹出窗口中的退水（此属性需要根据loginUserType判断取值，故使用的前提是loginUserType已经填充了数据）

    private Double winCommissionBranch = 0D;//分公司赚取佣金

    private Double winCommissionGenAgent = 0D;//总代理赚取佣金

    private Double winCommissionStockholder = 0D;//股东赚取佣金

    private Double winCommissionAgent = 0D;//代理赚取佣金

    private Double winCommissionMember = 0D;//会员赚取佣金

    /**
     * 克隆对象
     */
    public DeliveryReport clone() throws CloneNotSupportedException {
        return (DeliveryReport) super.clone();
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getUserType() {
        return userType;
    }

    public static String getUserTypeName(String userType) {

        String result = "";

        if (ManagerStaff.USER_TYPE_MANAGER.equalsIgnoreCase(userType)) {
            result = "总管";
        } else if (ManagerStaff.USER_TYPE_CHIEF.equalsIgnoreCase(userType)) {
            result = "总监";
        } else if (ManagerStaff.USER_TYPE_BRANCH.equalsIgnoreCase(userType)) {
            result = "分公司";
        } else if (ManagerStaff.USER_TYPE_STOCKHOLDER
                .equalsIgnoreCase(userType)) {
            result = "股东";
        } else if (ManagerStaff.USER_TYPE_GEN_AGENT.equalsIgnoreCase(userType)) {
            result = "总代理";
        } else if (ManagerStaff.USER_TYPE_AGENT.equalsIgnoreCase(userType)) {
            result = "代理";
        }

        return result;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getSubordinate() {
        return subordinate;
    }

    public void setSubordinate(String subordinate) {
        this.subordinate = subordinate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getTurnover() {
        return turnover;
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

    public Double getValidAmount() {
        return validAmount;
    }

    public String getValidAmountDis() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(validAmount);
    }

    public void setValidAmount(Double validAmount) {
        this.validAmount = validAmount;
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

    public Double getMemberBackWater() {
        return memberBackWater;
    }

    public String getMemberBackWaterDis() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(memberBackWater);
    }

    //退水后结果
    public Double getMemberBackWaterResult() {
        return memberBackWater + memberAmount;
    }

    public String getMemberBackWaterResultDis() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(memberBackWater + memberAmount);
    }

    public void setMemberBackWater(Double memberBackWater) {
        this.memberBackWater = memberBackWater;
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

    public Double getWinBackWater() {
        return winBackWater;
    }

    public String getWinBackWaterDis() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(winBackWater * -1);
    }

    public void setWinBackWater(Double winBackWater) {
        this.winBackWater = winBackWater;
    }

    public Double getRealResult() {
        return realResult;
    }

    public String getRealResultDis() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(realResult);
    }

    /**
     * 设置占货比
     * 
     * @param totalRealResult 实占注额的合计
     */
    public void calRealResultPer(Double totalRealResult) {
        this.realResultPer = realResult / totalRealResult;
    }

    /**
     * 占货比
     * 
     * 实占注额/实占注额的合计
     * 此方法需要先执行calRealResultPerValue方法才能取到正确的值
     * 
     * @return
     */
    public String getRealResultPerDis() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(realResultPer * 100);
    }

    /**
     * 根据新公式获取到到实占结果值
     * 
     * 实占输赢+实占退水
     * 
     * @return
     */
    public Double getRealResultNew() {
        return this.getRealWin() + this.getRealBackWater();
    }

    /**
     * 根据新公式获取到到实占结果值
     * 
     * 实占输赢+实占退水
     * 
     * @return
     */
    public String getRealResultNewDis() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(this.getRealResultNew());
    }

    /**
     * 获取实占输赢
     * 
     * 此处的获取方式根据已有数据计算，如有问题，则考虑在存储过程中计算
     * 
     * @return
     */
    public Double getRealWin() {

        //如果已经赋值，则不计算（目前主要是合计数据对象中会存在此处之外的赋值操作）
        if (null != this.realWin) {
            return this.realWin;
        }

        //实占输赢：会员投注结果*帐号对应上级占成  总和
        Double realWin = 0.0;
        Double useParentRate = 0.0;//帐号对应上级占成
        if (ManagerStaff.USER_TYPE_BRANCH.equalsIgnoreCase(userType)) {
            //分公司，取总监的占成
            useParentRate = this.rateChiefSet;
        } else if (ManagerStaff.USER_TYPE_STOCKHOLDER
                .equalsIgnoreCase(userType)) {
            //股东，取分公司的占成
            useParentRate = this.rateBranchSet;
        } else if (ManagerStaff.USER_TYPE_GEN_AGENT.equalsIgnoreCase(userType)) {
            //总代理，取股东的占成
            useParentRate = this.rateStockholderSet;
        } else if (ManagerStaff.USER_TYPE_AGENT.equalsIgnoreCase(userType)) {
            //代理，取总代理的占成
            useParentRate = this.rateGenAgentSet;
        } else if (MemberStaff.USER_TYPE_MEMBER.equalsIgnoreCase(userType)) {
            //普通会员（需要判断是否是直属会员，故从最底层的占成开始判断
            if (this.rateAgentSet > 0) {
                useParentRate = this.rateAgentSet;
            } else if (this.rateGenAgentSet > 0) {
                useParentRate = this.rateGenAgentSet;
            } else if (this.rateStockholderSet > 0) {
                useParentRate = this.rateStockholderSet;
            } else if (this.rateBranchSet > 0) {
                useParentRate = this.rateBranchSet;
            } else if (this.rateChiefSet > 0) {
                useParentRate = this.rateChiefSet;
            }
        }

        //实占输赢：会员投注结果*帐号对应上级占成 
        realWin = memberAmount * useParentRate / 100;

        return realWin;
    }

    /**
     * 获取实占输赢
     * 
     * 此处的获取方式根据已有数据计算，如有问题，则考虑在存储过程中计算
     * 
     * @return
     */
    public String getRealWinDis() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(this.getRealWin());
    }

    /**
     * 获取实占退水
     * 
     * 此处的获取方式根据已有数据计算，如有问题，则考虑在存储过程中计算
     * 
     * @return
     */
    public Double getRealBackWater() {

        //如果已经赋值，则不计算（目前主要是合计数据对象中会存在此处之外的赋值操作）
        if (null != this.realBackWater) {
            return this.realBackWater;
        }

        //实占退水：会员有效投注额*帐号对应上级占成*帐号对应上级退水率  总和
        Double realBackWater = 0.0;
        Double useParentRate = 0.0;//帐号对应上级占成
        Double useParentCommission = 0.0;//帐号对应上级的退水率
        if (ManagerStaff.USER_TYPE_BRANCH.equalsIgnoreCase(userType)) {
            //分公司，取总监的占成
            useParentRate = this.rateChiefSet;
            useParentCommission = 0.0;//总监不退水
        } else if (ManagerStaff.USER_TYPE_STOCKHOLDER
                .equalsIgnoreCase(userType)) {
            //股东，取分公司的占成
            useParentRate = this.rateBranchSet;
            useParentCommission = this.commissionBranchSet;
        } else if (ManagerStaff.USER_TYPE_GEN_AGENT.equalsIgnoreCase(userType)) {
            //总代理，取股东的占成
            useParentRate = this.rateStockholderSet;
            useParentCommission = this.commissionStockholderSet;
        } else if (ManagerStaff.USER_TYPE_AGENT.equalsIgnoreCase(userType)) {
            //代理，取总代理的占成
            useParentRate = this.rateGenAgentSet;
            useParentCommission = this.commissionGenAgentSet;
        } else if (MemberStaff.USER_TYPE_MEMBER.equalsIgnoreCase(userType)) {
            //普通会员（需要判断是否是直属会员，故从最底层的占成开始判断
            if (this.rateAgentSet > 0) {
                useParentRate = this.rateAgentSet;
                useParentCommission = this.commissionAgentSet;
            } else if (this.rateGenAgentSet > 0) {
                useParentRate = this.rateGenAgentSet;
                useParentCommission = this.commissionGenAgentSet;
            } else if (this.rateStockholderSet > 0) {
                useParentRate = this.rateStockholderSet;
                useParentCommission = this.commissionStockholderSet;
            } else if (this.rateBranchSet > 0) {
                useParentRate = this.rateBranchSet;
                useParentCommission = this.commissionBranchSet;
            } else if (this.rateChiefSet > 0) {
                useParentRate = this.rateChiefSet;
                useParentCommission = 0.0;
            }
        }

        //实占退水：会员有效投注额*帐号对应上级占成*帐号对应上级退水率
        realBackWater = validAmount * useParentRate / 100 * useParentCommission
                / 100;

        return realBackWater;
    }

    /**
     * 获取赚取退水
     * 
     * 需要根据当前登陆用户类型判断具体的返回值
     * TODO 直属会员可能有问题
     * 
     * @return
     */
    public Double getCommission() {

        //如果已经赋值，则不计算（目前主要是合计数据对象中会存在此处之外的赋值操作）
        if (null != this.commission) {
            return this.commission;
        }
        
        if (ManagerStaff.USER_TYPE_CHIEF.equalsIgnoreCase(loginUserType)) {
            //股东，取分公司退水
            //this.commission = this.winCommissionBranch;
            this.commission = 0D;
        } else if (ManagerStaff.USER_TYPE_BRANCH.equalsIgnoreCase(loginUserType)) {
            //分公司，取股东退水
            this.commission = this.winCommissionBranch;
        } else if (ManagerStaff.USER_TYPE_STOCKHOLDER
                .equalsIgnoreCase(loginUserType)) {
            //股东，取总代理退水
            this.commission = this.winCommissionStockholder;
        } else if (ManagerStaff.USER_TYPE_GEN_AGENT.equalsIgnoreCase(loginUserType)) {
            //总代理，取代理退水
            this.commission = this.winCommissionGenAgent;
        } else if (ManagerStaff.USER_TYPE_AGENT.equalsIgnoreCase(loginUserType)) {
            //代理，取会员退水
            this.commission = this.winCommissionAgent;
        }

        return this.commission;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    /**
     * 获取赚取退水
     * 
     * 需要根据当前登陆用户类型判断具体的返回值
     * TODO 直属会员可能有问题
     * 
     * @return
     */
    public String getCommissionDis() {

        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(-1 * this.getCommission());
    }

    /**
     * 获取实占退水
     * 
     * 此处的获取方式根据已有数据计算，如有问题，则考虑在存储过程中计算
     * 
     * @return
     */
    public String getRealBackWaterDis() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(this.getRealBackWater());
    }

    public void setOfferSuperior(Double offerSuperior) {
        this.offerSuperior = offerSuperior;
    }

    /**
     * 贡献上级
     * 
     * 有效金额*（1－对应帐号本身的上级及以下管理占成的总和） 总和
     * 
     * @return
     */
    public Double getOfferSuperior() {

        //如果已经赋值，则不计算（目前主要是合计数据对象中会存在此处之外的赋值操作）
        if (null != this.offerSuperior) {
            return this.offerSuperior;
        }

        //贡献上级：有效金额*（1－对应帐号本身的上级及以下管理占成的总和）
        Double offerSuperior = 0.0;
        Double useRate = 0.0;//帐号对应上级及其下级别的占成和
        if (ManagerStaff.USER_TYPE_BRANCH.equalsIgnoreCase(userType)) {
            //分公司，取总监的占成及以下的占成的和，即为1
            useRate = 1.0;
        } else if (ManagerStaff.USER_TYPE_STOCKHOLDER
                .equalsIgnoreCase(userType)) {
            //股东，取分公司及其之下的占成
            useRate = this.rateBranchSet + this.rateStockholderSet
                    + this.rateGenAgentSet + this.rateAgentSet;
        } else if (ManagerStaff.USER_TYPE_GEN_AGENT.equalsIgnoreCase(userType)) {
            //总代理，取股东及其之下的占成
            useRate = this.rateStockholderSet + this.rateGenAgentSet
                    + this.rateAgentSet;
        } else if (ManagerStaff.USER_TYPE_AGENT.equalsIgnoreCase(userType)) {
            //代理，取总代理及其之下的占成
            useRate = this.rateGenAgentSet + this.rateAgentSet;
        } else if (MemberStaff.USER_TYPE_MEMBER.equalsIgnoreCase(userType)) {
            //普通会员（需要判断是否是直属会员，故从最底层的占成开始判断
            if (this.rateAgentSet > 0) {
                useRate = this.rateAgentSet;
            } else if (this.rateGenAgentSet > 0) {
                useRate = this.rateGenAgentSet;
            } else if (this.rateStockholderSet > 0) {
                useRate = this.rateStockholderSet;
            } else if (this.rateBranchSet > 0) {
                useRate = this.rateBranchSet;
            } else if (this.rateChiefSet > 0) {
                useRate = this.rateChiefSet;
            }
        }

        //贡献上级：有效金额*（1－对应帐号本身的上级及以下管理占成的总和）
        offerSuperior = validAmount * (1 - (useRate / 100));

        return offerSuperior;
    }

    /**
     * 贡献上级
     * 
     * 有效金额*（1－对应帐号本身的上级及以下管理占成的总和） 总和
     * 
     * @return
     */
    public String getOfferSuperiorDis() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(this.getOfferSuperior());
    }

    public void setRealResult(Double realResult) {
        this.realResult = realResult;
    }

    public Double getWinBackWaterResult() {
        return winBackWaterResult;
    }

    public String getWinBackWaterResultDis() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(winBackWaterResult);
    }

    public void setWinBackWaterResult(Double winBackWaterResult) {
        this.winBackWaterResult = winBackWaterResult;
    }

    public Double getPaySuperior() {
        return paySuperior;
    }

    public String getPaySuperiorDis() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(paySuperior);
    }

    public void setPaySuperior(Double paySuperior) {
        this.paySuperior = paySuperior;
    }

    public Double getRate() {
        return rate;
    }

    public String getRateDis() {
        DecimalFormat df = new DecimalFormat("0.000");
        return df.format(rate);
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    //获取rate的显示值
    public String getRateName() {

        String result = null;

        result = this.getRateDis();

        return result;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
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

    public Double getCommissionBranch() {
        return commissionBranch;
    }

    public void setCommissionBranch(Double commissionBranch) {
        this.commissionBranch = commissionBranch;
    }

    public Double getCommissionStockholder() {
        return commissionStockholder;
    }

    public void setCommissionStockholder(Double commissionStockholder) {
        this.commissionStockholder = commissionStockholder;
    }

    public Double getCommissionGenAgent() {
        return commissionGenAgent;
    }

    public void setCommissionGenAgent(Double commissionGenAgent) {
        this.commissionGenAgent = commissionGenAgent;
    }

    public Double getCommissionAgent() {
        return commissionAgent;
    }

    public void setCommissionAgent(Double commissionAgent) {
        this.commissionAgent = commissionAgent;
    }

    public Double getCommissionMember() {
        return commissionMember;
    }

    public void setCommissionMember(Double commissionMember) {
        this.commissionMember = commissionMember;
    }

    public void setRealWin(Double realWin) {
        this.realWin = realWin;
    }

    public void setRealBackWater(Double realBackWater) {
        this.realBackWater = realBackWater;
    }

    public Double getRealResultPer() {
        return realResultPer;
    }

    public void setRealResultPer(Double realResultPer) {
        this.realResultPer = realResultPer;
    }

    public Double getRateChiefSet() {
        return rateChiefSet;
    }

    public void setRateChiefSet(Double rateChiefSet) {
        this.rateChiefSet = rateChiefSet;
    }

    public Double getRateBranchSet() {
        return rateBranchSet;
    }

    public void setRateBranchSet(Double rateBranchSet) {
        this.rateBranchSet = rateBranchSet;
    }

    public Double getRateStockholderSet() {
        return rateStockholderSet;
    }

    public void setRateStockholderSet(Double rateStockholderSet) {
        this.rateStockholderSet = rateStockholderSet;
    }

    public Double getRateGenAgentSet() {
        return rateGenAgentSet;
    }

    public void setRateGenAgentSet(Double rateGenAgentSet) {
        this.rateGenAgentSet = rateGenAgentSet;
    }

    public Double getRateAgentSet() {
        return rateAgentSet;
    }

    public void setRateAgentSet(Double rateAgentSet) {
        this.rateAgentSet = rateAgentSet;
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

    public Double getSubordinateValue() {
        return subordinateValue;
    }

    public void setSubordinateValue(Double subordinateValue) {
        this.subordinateValue = subordinateValue;
    }

    /**
     * 下线应收（实占输赢，即不考虑退水的数值）
     * （此属性需要根据loginUserType判断取值，故使用的前提是loginUserType已经填充了数据）
     * 
     * @return
     */
    public Double getSubordinateRealValue() {
        //会员输赢*(100%-下级的占成和 )
        if (ManagerStaff.USER_TYPE_CHIEF.equalsIgnoreCase(this.loginUserType)) {
            //总监
            subordinateRealValue = memberAmount * (this.rateChiefSet) / 100.0;
        } else if (ManagerStaff.USER_TYPE_BRANCH
                .equalsIgnoreCase(this.loginUserType)) {
            //分公司
            subordinateRealValue = memberAmount
                    * (100 - this.rateAgentSet - this.rateGenAgentSet - this.rateStockholderSet)
                    / 100.0;
        } else if (ManagerStaff.USER_TYPE_STOCKHOLDER
                .equalsIgnoreCase(this.loginUserType)) {
            //股东
            subordinateRealValue = memberAmount
                    * (100 - this.rateAgentSet - this.rateGenAgentSet) / 100.0;
        } else if (ManagerStaff.USER_TYPE_GEN_AGENT
                .equalsIgnoreCase(this.loginUserType)) {
            //总代理
            subordinateRealValue = memberAmount * (100 - this.rateAgentSet)
                    / 100.0;
        } else if (ManagerStaff.USER_TYPE_AGENT
                .equalsIgnoreCase(this.loginUserType)) {
            //代理
            subordinateRealValue = memberAmount * (100 - 0) / 100.0;
        }

        return subordinateRealValue;
    }

    /**
     * 下线应收（实占输赢，即不考虑退水的数值）
     * （此属性需要根据loginUserType判断取值，故使用的前提是loginUserType已经填充了数据）
     * 
     * @return
     */
    public String getSubordinateRealValueDis() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(this.getSubordinateRealValue());
    }

    /**
     * 下线应收弹出窗口中的退水
     * （此属性需要根据loginUserType判断取值，故使用的前提是loginUserType已经填充了数据）
     * 
     * @return
     */
    public Double getSubordinateBackWater() {
        //有效投注金额*给下级的退水率*(100%-下级占成和 )
        //        if (ManagerStaff.USER_TYPE_CHIEF.equalsIgnoreCase(this.loginUserType)) {
        //            //总监
        //            subordinateBackWater = validAmount * this.commissionBranch / 100.0
        //                    * (this.rateChiefSet) / 100.0;
        //        } else if (ManagerStaff.USER_TYPE_BRANCH
        //                .equalsIgnoreCase(this.loginUserType)) {
        //            //分公司
        //            subordinateBackWater = validAmount
        //                    * this.commissionStockholder
        //                    / 100.0
        //                    * (100 - this.rateAgentSet - this.rateGenAgentSet - this.rateStockholderSet)
        //                    / 100.0;
        //        } else if (ManagerStaff.USER_TYPE_STOCKHOLDER
        //                .equalsIgnoreCase(this.loginUserType)) {
        //            //股东
        //            subordinateBackWater = validAmount * this.commissionGenAgent
        //                    / 100.0 * (100 - this.rateAgentSet - this.rateGenAgentSet)
        //                    / 100.0;
        //        } else if (ManagerStaff.USER_TYPE_GEN_AGENT
        //                .equalsIgnoreCase(this.loginUserType)) {
        //            //总代理
        //            subordinateBackWater = validAmount * this.commissionAgent / 100.0
        //                    * (100 - this.rateAgentSet) / 100.0;
        //        } else if (ManagerStaff.USER_TYPE_AGENT
        //                .equalsIgnoreCase(this.loginUserType)) {
        //            //代理
        //            subordinateBackWater = validAmount * this.commissionMember / 100.0
        //                    * (100 - 0) / 100.0;
        //        }

        subordinateBackWater = subordinateAmount
                - this.getSubordinateRealValue();

        return subordinateBackWater;
    }

    /**
     * 下线应收弹出窗口中的退水
     * （此属性需要根据loginUserType判断取值，故使用的前提是loginUserType已经填充了数据）
     * 
     * @return
     */
    public String getSubordinateBackWaterDis() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(this.getSubordinateBackWater());
    }

    public void setSubordinateBackWater(Double subordinateBackWater) {
        this.subordinateBackWater = subordinateBackWater;
    }

    public void setSubordinateRealValue(Double subordinateRealValue) {
        this.subordinateRealValue = subordinateRealValue;
    }

    public Double getCommissionBranchSet() {
        return commissionBranchSet;
    }

    public void setCommissionBranchSet(Double commissionBranchSet) {
        this.commissionBranchSet = commissionBranchSet;
    }

    public Double getCommissionStockholderSet() {
        return commissionStockholderSet;
    }

    public void setCommissionStockholderSet(Double commissionStockholderSet) {
        this.commissionStockholderSet = commissionStockholderSet;
    }

    public Double getCommissionGenAgentSet() {
        return commissionGenAgentSet;
    }

    public void setCommissionGenAgentSet(Double commissionGenAgentSet) {
        this.commissionGenAgentSet = commissionGenAgentSet;
    }

    public Double getCommissionAgentSet() {
        return commissionAgentSet;
    }

    public void setCommissionAgentSet(Double commissionAgentSet) {
        this.commissionAgentSet = commissionAgentSet;
    }

    /**
     * 实占注额
     * 
     * @return
     */
    public Double getRateValidAmount() {

        //实占注额=有效的投注金额*自已的占成
        if (ManagerStaff.USER_TYPE_CHIEF.equalsIgnoreCase(this.loginUserType)) {
            //总监
            rateValidAmount = this.moneyRateChief;
        } else if (ManagerStaff.USER_TYPE_BRANCH
                .equalsIgnoreCase(this.loginUserType)) {
            //分公司
            rateValidAmount = this.moneyRateBranch;
        } else if (ManagerStaff.USER_TYPE_STOCKHOLDER
                .equalsIgnoreCase(this.loginUserType)) {
            //股东
            rateValidAmount = this.moneyRateStockholder;
        } else if (ManagerStaff.USER_TYPE_GEN_AGENT
                .equalsIgnoreCase(this.loginUserType)) {
            //总代理
            rateValidAmount = this.moneyRateGenAgent;
        } else if (ManagerStaff.USER_TYPE_AGENT
                .equalsIgnoreCase(this.loginUserType)) {
            //代理
            rateValidAmount = this.moneyRateAgent;
        }

        return rateValidAmount;

    }

    /**
     * 实占注额
     * 
     * TODO 此处通过计算获取，后续需要修改成从存储过程中读取
     * 
     * @return
     */
    public String getRateValidAmountDis() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(this.getRateValidAmount());
    }

    public void setRateValidAmount(Double rateValidAmount) {
        this.rateValidAmount = rateValidAmount;
    }

    public Double getMoneyRateChief() {
        return moneyRateChief;
    }

    public void setMoneyRateChief(Double moneyRateChief) {
        this.moneyRateChief = moneyRateChief;
    }

    public Double getMoneyRateBranch() {
        return moneyRateBranch;
    }

    public void setMoneyRateBranch(Double moneyRateBranch) {
        this.moneyRateBranch = moneyRateBranch;
    }

    public Double getMoneyRateStockholder() {
        return moneyRateStockholder;
    }

    public void setMoneyRateStockholder(Double moneyRateStockholder) {
        this.moneyRateStockholder = moneyRateStockholder;
    }

    public Double getMoneyRateGenAgent() {
        return moneyRateGenAgent;
    }

    public void setMoneyRateGenAgent(Double moneyRateGenAgent) {
        this.moneyRateGenAgent = moneyRateGenAgent;
    }

    public Double getMoneyRateAgent() {
        return moneyRateAgent;
    }

    public void setMoneyRateAgent(Double moneyRateAgent) {
        this.moneyRateAgent = moneyRateAgent;
    }

    public Double getWinCommissionBranch() {
        return winCommissionBranch;
    }

    public void setWinCommissionBranch(Double winCommissionBranch) {
        this.winCommissionBranch = winCommissionBranch;
    }

    public Double getWinCommissionGenAgent() {
        return winCommissionGenAgent;
    }

    public void setWinCommissionGenAgent(Double winCommissionGenAgent) {
        this.winCommissionGenAgent = winCommissionGenAgent;
    }

    public Double getWinCommissionStockholder() {
        return winCommissionStockholder;
    }

    public void setWinCommissionStockholder(Double winCommissionStockholder) {
        this.winCommissionStockholder = winCommissionStockholder;
    }

    public Double getWinCommissionAgent() {
        return winCommissionAgent;
    }

    public void setWinCommissionAgent(Double winCommissionAgent) {
        this.winCommissionAgent = winCommissionAgent;
    }

    public Double getWinCommissionMember() {
        return winCommissionMember;
    }

    public void setWinCommissionMember(Double winCommissionMember) {
        this.winCommissionMember = winCommissionMember;
    }
}
