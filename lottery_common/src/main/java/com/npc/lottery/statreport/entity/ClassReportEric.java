package com.npc.lottery.statreport.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.npc.lottery.common.Constant;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.MemberStaff;

/**
 * 
 * 交收报表实体类
 * 
 */
public class ClassReportEric implements Serializable, Cloneable {
	
	private static Logger log = Logger.getLogger(ClassReportEric.class);

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
    
    private Double subordinateAmountWin;//应收下线的输赢
    
    private Double subordinateAmountBackWater;//应收下线的退水

    private Double winBackWater;//赚取退水
    
    private Double backWaterResult;//退水后结果--用于统计补出

    private Double winBackWaterReple;//抵扣补货及赚水后结果

    private Double realResult;//实占结果

    private Double rateValidAmount;//实占注额（TODO 后续需要优化，存储过程中计算）

    private Double realResultPer;//占货比

    private Double realWin = null;//实占输赢

    private Double realBackWater = null;//实占退水

    private Double winBackWaterResult;//赚水后结果  实占结果+赚取退水的总和 

    private Double paySuperior;//应付上级 =表格中的应收下线－表格中的赚水后结果      

    private Double offerSuperior = null;//贡献上级

    private Double rate= (double) 0;//占成

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
    
    private Double rateMoney = 0D;//实占金额
    
    private String commissionType;
    
    private String commissionTypeName;
    
    private String commissionTypeShortName;
    
    private String periodNum;//盘期
    
    private java.sql.Date bettingDate;

    /**
     * 克隆对象
     */
    public ClassReportEric clone() throws CloneNotSupportedException {
        return (ClassReportEric) super.clone();
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
            result = "總管";
        } else if (ManagerStaff.USER_TYPE_CHIEF.equalsIgnoreCase(userType)) {
            result = "总監";
        } else if (ManagerStaff.USER_TYPE_BRANCH.equalsIgnoreCase(userType)) {
            result = "分公司";
        } else if (ManagerStaff.USER_TYPE_STOCKHOLDER
                .equalsIgnoreCase(userType)) {
            result = "股東";
        } else if (ManagerStaff.USER_TYPE_GEN_AGENT.equalsIgnoreCase(userType)) {
            result = "總代理";
        } else if (ManagerStaff.USER_TYPE_AGENT.equalsIgnoreCase(userType)) {
            result = "代理";
        } else if (MemberStaff.USER_TYPE_MEMBER.equalsIgnoreCase(String.valueOf(Integer.valueOf(userType)+2))) {
        	result = "會員";
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
        DecimalFormat df = new DecimalFormat("0.0");
        return df.format(BigDecimal.valueOf(this.getAmount()).setScale(1,BigDecimal.ROUND_HALF_UP));
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
        DecimalFormat df = new DecimalFormat("0.0");
        return df.format(BigDecimal.valueOf(this.getMemberAmount()).setScale(1,BigDecimal.ROUND_HALF_UP));
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
    	winBackWaterResult = memberBackWater + memberAmount;
        return winBackWaterResult;
    }

    public String getMemberBackWaterResultDis() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(memberBackWater + memberAmount);
    }

    public void setMemberBackWater(Double memberBackWater) {
        this.memberBackWater = memberBackWater;
    }

    //应收下线=应收下线输赢+应收下线退水
    public Double getSubordinateAmount() {
    	subordinateAmount = subordinateAmountWin + subordinateAmountBackWater;
        return subordinateAmount;
    }

    public Double getSubordinateAmountWin() {
		return subordinateAmountWin;
	}
    public String getSubordinateAmountWinDis() {
    	DecimalFormat df = new DecimalFormat("0.0");
    	return df.format(BigDecimal.valueOf(this.subordinateAmountWin).setScale(1,BigDecimal.ROUND_HALF_UP));
    }

	public Double getSubordinateAmountBackWater() {
		return subordinateAmountBackWater;
	}
	public String getSubordinateAmountBackWaterDis() {
		DecimalFormat df = new DecimalFormat("0.0");
		return df.format(BigDecimal.valueOf(this.subordinateAmountBackWater).setScale(1,BigDecimal.ROUND_HALF_UP));
	}

	public void setSubordinateAmountWin(Double subordinateAmountWin) {
		this.subordinateAmountWin = subordinateAmountWin;
	}

	public void setSubordinateAmountBackWater(Double subordinateAmountBackWater) {
		this.subordinateAmountBackWater = subordinateAmountBackWater;
	}

	public String getSubordinateAmountDis() {
        DecimalFormat df = new DecimalFormat("0.0");
        return df.format(BigDecimal.valueOf(this.getSubordinateAmount()).setScale(1,BigDecimal.ROUND_HALF_UP));
    }

    public void setSubordinateAmount(Double subordinateAmount) {
        this.subordinateAmount = subordinateAmount;
    }

    public Double getWinBackWater() {
        return winBackWater;
    }

    public Double getBackWaterResult() {
		return backWaterResult;
	}
    
    public String getBackWaterResultDis() {
    	DecimalFormat df = new DecimalFormat("0.0");
        return df.format(BigDecimal.valueOf(this.backWaterResult).setScale(1,BigDecimal.ROUND_HALF_UP));
    }

	public void setBackWaterResult(Double backWaterResult) {
		this.backWaterResult = backWaterResult;
	}

	public String getWinBackWaterDis() {
        DecimalFormat df = new DecimalFormat("0.0");
        return df.format(BigDecimal.valueOf(this.winBackWater).setScale(1,BigDecimal.ROUND_HALF_UP));
    }

    public void setWinBackWater(Double winBackWater) {
        this.winBackWater = winBackWater;
    }
    
    /*实占结果=实占输赢+实占退水*/
    public Double getRealResult() {
    	realResult = this.realWin + this.realBackWater;
        return realResult;
    }

    public String getRealResultDis() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(this.realWin + this.realBackWater);
    }

    /**
     * 设置占货比
     * 
     * @param totalRealResult 实占注额的合计
     */
    public void calRealResultPer(Double totalRealResult) {
        if(rateMoney==0 && totalRealResult==0){
    		this.realResultPer = 0D;
    	}else{
    		this.realResultPer = BigDecimal.valueOf(rateMoney).divide(BigDecimal.valueOf(totalRealResult),5,BigDecimal.ROUND_HALF_UP).doubleValue();
    	}
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
        DecimalFormat df = new DecimalFormat("0.0");
        return df.format(BigDecimal.valueOf(this.getRealResultNew()).setScale(1,BigDecimal.ROUND_HALF_UP));
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
        DecimalFormat df = new DecimalFormat("0.0");
        return df.format(BigDecimal.valueOf(this.getRealWin()).setScale(1,BigDecimal.ROUND_HALF_UP));
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

        DecimalFormat df = new DecimalFormat("0.0");
        return df.format(BigDecimal.valueOf(this.getCommission()).setScale(1,BigDecimal.ROUND_HALF_UP));
    }

    /**
     * 获取实占退水
     * 
     * 此处的获取方式根据已有数据计算，如有问题，则考虑在存储过程中计算
     * 
     * @return
     */
    public String getRealBackWaterDis() {
        DecimalFormat df = new DecimalFormat("0.0");
        return df.format(BigDecimal.valueOf(this.getRealBackWater()).setScale(1,BigDecimal.ROUND_HALF_UP));
    }

    public void setOfferSuperior(Double offerSuperior) {
        this.offerSuperior = offerSuperior;
    }

    /**
     * 贡献上级
     * @return
     */
    public Double getOfferSuperior() {

        //如果已经赋值，则不计算（目前主要是合计数据对象中会存在此处之外的赋值操作）
        if (null != this.offerSuperior) {
            return this.offerSuperior;
        }

        //贡献上级：
        Double offerSuperior = 0.0;
        Double useRate = 0.0;//帐号对应本级及其下级的占成和
        if (ManagerStaff.USER_TYPE_BRANCH
                .equalsIgnoreCase(userType)) {
            useRate = this.moneyRateChief;
        } else if (ManagerStaff.USER_TYPE_STOCKHOLDER.equalsIgnoreCase(userType)) {
            useRate = this.moneyRateChief + this.moneyRateBranch;
        } else if (ManagerStaff.USER_TYPE_GEN_AGENT.equalsIgnoreCase(userType)) {
            useRate = this.moneyRateChief + this.moneyRateBranch + this.moneyRateStockholder;
        } else if (ManagerStaff.USER_TYPE_AGENT.equalsIgnoreCase(userType)) {
        	useRate = this.moneyRateChief + this.moneyRateBranch + this.moneyRateStockholder + this.moneyRateGenAgent;
        }

        offerSuperior = useRate;

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
        DecimalFormat df = new DecimalFormat("0.0");
        return df.format(BigDecimal.valueOf(this.getOfferSuperior()).setScale(1,BigDecimal.ROUND_HALF_UP));
    }

    public void setRealResult(Double realResult) {
        this.realResult = realResult;
    }

    public Double getWinBackWaterResult() {
        return this.getRealResult()+commission;  /*实占结果+赚取退水的总和*/
    }

    public String getWinBackWaterResultDis() {
        DecimalFormat df = new DecimalFormat("0.0");
        return df.format(BigDecimal.valueOf(this.getWinBackWaterResult()).setScale(1,BigDecimal.ROUND_HALF_UP));
    }

    public void setWinBackWaterResult(Double winBackWaterResult) {
        this.winBackWaterResult = winBackWaterResult;
    }

    public Double getPaySuperior() {
        return  this.getSubordinateAmount() - this.getWinBackWaterResult();  /*应付上级 =表格中的应收下线－表格中的赚水后结果     */
    }

    public String getPaySuperiorDis() {
        DecimalFormat df = new DecimalFormat("0.0");
        return df.format(BigDecimal.valueOf(this.getPaySuperior()).setScale(1,BigDecimal.ROUND_HALF_UP));
    }

    public void setPaySuperior(Double paySuperior) {
        this.paySuperior = paySuperior;
    }

    public Double getRate() {
    	if(rateMoney==0 && amount==0){
    		rate = 0D;
    	}else{
    		rate = rateMoney/amount*100;
    	}
        return rate;
    }

    public String getRateDis() {
        DecimalFormat df = new DecimalFormat("0.000");
        return df.format(BigDecimal.valueOf(this.getRate()).setScale(3,BigDecimal.ROUND_HALF_UP));
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
        /*//会员输赢*(100%-下级的占成和 )
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
        }*/

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

        /*//实占注额=有效的投注金额*自已的占成
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
        }*/

        return rateMoney;

    }

    /**
     * 实占注额
     * @return
     */
    public String getRateValidAmountDis() {
        DecimalFormat df = new DecimalFormat("0.0");
        return df.format(BigDecimal.valueOf(this.rateMoney).setScale(1,BigDecimal.ROUND_HALF_UP));
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

	public Double getRateMoney() {
		return rateMoney;
	}

	public void setRateMoney(Double rateMoney) {
		this.rateMoney = rateMoney;
	}
	
	public String getCommissionType() {
		return commissionType;
	}

	public String getCommissionTypeName() {
		Map<String, String> map = new HashMap<String, String>();
		if(this.commissionType.indexOf("GD")!=-1){
			map = Constant.GDKLSF_COMMISSION_TYPE;
			log.info("~~~~~~~~~~~~~~~~~~~~"+this.commissionType);
			return Constant.LOTTERY_TYPE_GDKLSF_NAME + " " + map.get(this.commissionType);
		}else if(this.commissionType.indexOf("CQ")!=-1){
			map = Constant.CQSSC_COMMISSION_TYPE;
			return Constant.LOTTERY_TYPE_CQSSC_NAME + " " + map.get(this.commissionType);
		}else if(this.commissionType.indexOf("K3")!=-1){
			map = Constant.K3_COMMISSION_TYPE;
			return Constant.LOTTERY_TYPE_K3_NAME + " " + map.get(this.commissionType);
		}else if(this.commissionType.indexOf("NC")!=-1){
			map = Constant.NC_COMMISSION_TYPE;
			return Constant.LOTTERY_TYPE_NC_NAME + " " + map.get(this.commissionType);
		}else{
			map = Constant.BJSC_COMMISSION_TYPE;
			return Constant.LOTTERY_TYPE_BJSC_NAME + " " + map.get(this.commissionType);
		}
		
	}
	
	public String getCommissionTypeShortName() {
		Map<String, String> map = new HashMap<String, String>();
		if(this.commissionType.indexOf("GD")!=-1){
			map = Constant.GDKLSF_COMMISSION_TYPE;
			return map.get(this.commissionType);
		}else if(this.commissionType.indexOf("CQ")!=-1){
			map = Constant.CQSSC_COMMISSION_TYPE;
			return map.get(this.commissionType);
		}else if(this.commissionType.indexOf("K3")!=-1){
			map = Constant.K3_COMMISSION_TYPE;
			return map.get(this.commissionType);
		}else if(this.commissionType.indexOf("NC")!=-1){
			map = Constant.NC_COMMISSION_TYPE;
			return map.get(this.commissionType);
		}else{
			map = Constant.BJSC_COMMISSION_TYPE;
			return map.get(this.commissionType);
		}
		
	}

	public void setCommissionType(String commissionType) {
		this.commissionType = commissionType;
	}

	public void setCommissionTypeName(String commissionTypeName) {
		this.commissionTypeName = commissionTypeName;
	}

	public String getPeriodNum() {
		return periodNum;
	}

	public void setPeriodNum(String periodNum) {
		this.periodNum = periodNum;
	}

	public java.sql.Date getBettingDate() {
		return this.bettingDate;
	}

	public void setBettingDate(java.sql.Date bettingDate) {
		this.bettingDate = bettingDate;
	}
}
