package com.npc.lottery.statreport.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import com.npc.lottery.common.Constant;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.MemberStaff;

/**
 * 
 * 交收报表实体类
 * 
 */
public class UnClassReport implements Serializable, Cloneable {

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

    private Double amount;//投注总额，与amountCompute对比，差别在于这里不用合计下级补入的金额
    
    private Double realResultPer;//占货比

    private Double offerSuperior = null;//贡献上级
    
    private Double rate = (double) 0;//占成
    
    private Double rateMoney = 0D;//实占金额

    private Double moneyRateChief;//总监实占注额

    private Double moneyRateBranch;//分公司实占注额

    private Double moneyRateStockholder;//股东实占注额

    private Double moneyRateGenAgent;//总代理实占注额

    private Double moneyRateAgent;//代理实占注额
    
    private String commissionType;
    
    private String commissionTypeName;
    
    private String commissionTypeShortName;

    /**
     * 克隆对象
     */
    public UnClassReport clone() throws CloneNotSupportedException {
        return (UnClassReport) super.clone();
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
        return df.format(amount);
    }

    public void setAmount(Double amount) {
        this.amount = amount;
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


    public void setOfferSuperior(Double offerSuperior) {
        this.offerSuperior = offerSuperior;
    }
    

	/**
     * 贡献上级
     * 
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
        if (ManagerStaff.USER_TYPE_BRANCH.equalsIgnoreCase(userType)) {
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
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(this.getOfferSuperior());
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
        return df.format(rateMoney/amount*100);
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getRateMoney() {
		return rateMoney;
	}

	public void setRateMoney(Double rateMoney) {
		this.rateMoney = rateMoney;
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

    public Double getRealResultPer() {
        return realResultPer;
    }

    public void setRealResultPer(Double realResultPer) {
        this.realResultPer = realResultPer;
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


    /**
     * 实占注额
     * 
     * TODO 此处通过计算获取，后续需要修改成从存储过程中读取
     * 
     * @return
     */
    public String getRateValidAmountDis() {
        DecimalFormat df = new DecimalFormat("0.0");
        return df.format(BigDecimal.valueOf(this.rateMoney).setScale(1,BigDecimal.ROUND_HALF_UP));
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

	public String getCommissionType() {
		return commissionType;
	}

	public String getCommissionTypeName() {
		Map<String, String> map = new HashMap<String, String>();
		if(this.commissionType.indexOf("GD")!=-1){
			map = Constant.GDKLSF_COMMISSION_TYPE;
			return Constant.LOTTERY_TYPE_GDKLSF_NAME + " " + map.get(this.commissionType);
		}else if(this.commissionType.indexOf("CQ")!=-1){
			map = Constant.CQSSC_COMMISSION_TYPE;
			return Constant.LOTTERY_TYPE_CQSSC_NAME + " " + map.get(this.commissionType);
		}else if(this.commissionType.indexOf("K3")!=-1){
			map = Constant.K3_COMMISSION_TYPE;
			return Constant.LOTTERY_TYPE_K3_NAME + " " + map.get(this.commissionType);
		}else if(this.commissionType.indexOf("NC")!=-1){
			map = Constant.NC_COMMISSION_TYPE;
			System.out.println(Constant.LOTTERY_TYPE_NC_NAME + " " + map.get(this.commissionType)); 
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
}
