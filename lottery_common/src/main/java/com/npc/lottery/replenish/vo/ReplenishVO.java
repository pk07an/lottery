package com.npc.lottery.replenish.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.npc.lottery.common.Constant;
import com.npc.lottery.member.entity.PlayType;
import com.npc.lottery.util.PlayTypeUtils;

public class ReplenishVO implements Serializable {
 
   private static final long serialVersionUID = 1L;
   private String playFinalType;
   private java.math.BigDecimal realOdds = BigDecimal.valueOf(0);
   private Integer money = 0;
   private BigDecimal lmMoney = BigDecimal.ZERO;//连码补货时的下框的注额是要显示小数的。
   private BigDecimal winMoney = BigDecimal.ZERO;//可赢金额(派彩额)
   private java.math.BigDecimal oddsMoney = BigDecimal.valueOf(0);  //赔额
   private java.math.BigDecimal tsMoney = BigDecimal.valueOf(0);  //退水
   private java.math.BigDecimal loseMoney = BigDecimal.valueOf(0);  //亏盈
   private java.math.BigDecimal commission; //拥金
   private String attribute;   //纪录连码投注时所选择的球
   private String attributeChinaName;   //纪录连码投注时所选择的排列的中文翻譯
   private Integer RMoney = 0;  //已补货的金额
   private Integer computeMoney = 0 ; //用于存注额，用于临时统计用
   private java.math.BigDecimal commissionMoney = BigDecimal.ZERO;//实占注额-实占注额*拥金=实占退水后结果
   private java.math.BigDecimal totalCommissionMoney = BigDecimal.ZERO;//实占退水后结果合计，这里已经加入补进减去补出了。
   private java.math.BigDecimal rateMoney = BigDecimal.ZERO;//注额*占成
   private java.math.BigDecimal rate; 
   private Integer total; //总笔数
   private Long userID;
   private Long orderNo;//补货的单号
   private String winState; //状态
   private String fpState;  //封盘状态
   private String playTypeName; //玩法中文名称
   private String finalPlayTypeName; //玩法中文名称
   
   private Integer sortNo; //用户排序,方便在界面按规定的顺序显示
   /**
    * 補貨筆數
    */
   private Integer totalCount;
   private int avLose = 0;//查询得出的平均盈亏值
   
   private String overLoseQuatas = "0";//把盈亏数对比交易设定里的负值超额警示值，如果小于警示值就赋于1,否则赋于0
  
   

public Integer getRMoney() {
	return RMoney;
}
public void setRMoney(Integer rMoney) {
	RMoney = rMoney;
}
public String getPlayFinalType() {
	return playFinalType;
}
public Integer getMoney() {
	return money;
}
public BigDecimal getLmMoney() {
	return lmMoney;
}
public void setLmMoney(BigDecimal lmMoney) {
	this.lmMoney = lmMoney;
}
public BigDecimal getWinMoney() {
	return winMoney;
}
public void setWinMoney(BigDecimal winMoney) {
	this.winMoney = winMoney;
}
public java.math.BigDecimal getLoseMoney() {
	return loseMoney;
}
public void setPlayFinalType(String playFinalType) {
	this.playFinalType = playFinalType;
}
public java.math.BigDecimal getRealOdds() {
	return realOdds;
}
public void setRealOdds(java.math.BigDecimal realOdds) {
	this.realOdds = realOdds;
}
public void setMoney(Integer money) {
	this.money = money;
}
public void setLoseMoney(java.math.BigDecimal loseMoney) {
	this.loseMoney = loseMoney;
}
public java.math.BigDecimal getCommission() {
	return commission;
}
public void setCommission(java.math.BigDecimal commission) {
	this.commission = commission;
}
public java.math.BigDecimal getOddsMoney() {
	return oddsMoney;
}
public void setOddsMoney(java.math.BigDecimal oddsMoney) {
	this.oddsMoney = oddsMoney;
}
public String getAttribute() {
	return attribute;
}
public void setAttribute(String attribute) {
	this.attribute = attribute;
}
public String getAttributeChinaName() {
	return attributeChinaName;
}
public void setAttributeChinaName(String attributeChinaName) {
	this.attributeChinaName = attributeChinaName;
}
public Integer getComputeMoney() {
	return computeMoney;
}
public void setComputeMoney(Integer computeMoney) {
	this.computeMoney = computeMoney;
}
public java.math.BigDecimal getCommissionMoney() {
	return commissionMoney;
}
public void setCommissionMoney(java.math.BigDecimal commissionMoney) {
	this.commissionMoney = commissionMoney;
}
public java.math.BigDecimal getTotalCommissionMoney() {
	return totalCommissionMoney;
}
public void setTotalCommissionMoney(java.math.BigDecimal totalCommissionMoney) {
	this.totalCommissionMoney = totalCommissionMoney;
}
public java.math.BigDecimal getRateMoney() {
	return rateMoney;
}
public void setRateMoney(java.math.BigDecimal rateMoney) {
	this.rateMoney = rateMoney;
}
public java.math.BigDecimal getRate() {
	return rate;
}
public void setRate(java.math.BigDecimal rate) {
	this.rate = rate;
}
public java.math.BigDecimal getTsMoney() {
	return tsMoney;
}
public void setTsMoney(java.math.BigDecimal tsMoney) {
	this.tsMoney = tsMoney;
}
public String getWinState() {
	if(null!=winState){
		return winState;
	}else{
		return "";
	}
}
public void setWinState(String winState) {
	this.winState = winState;
}
public Integer getTotal() {
	return total;
}
public Long getOrderNo() {
	return orderNo;
}
public void setOrderNo(Long orderNo) {
	this.orderNo = orderNo;
}
public Long getUserID() {
	return userID;
}
public void setUserID(Long userID) {
	this.userID = userID;
}
public void setTotal(Integer total) {
	this.total = total;
}
public Integer getTotalCount() {
	return totalCount;
}
public void setTotalCount(Integer totalCount) {
	this.totalCount = totalCount;
}
public String getFpState() {
	return fpState;
}
public String getPlayTypeName() {
	if(this.playFinalType.indexOf(Constant.LOTTERY_TYPE_CQSSC)!=-1){
		String[] list = new String[] {"FIRST","SECOND","THIRD","FORTH","FIFTH"};
		for(int j=0;j<list.length;j++){
			for(int i=1;i<=9;i++){
				if(("CQSSC_BALL_" + list[j] + "_" + i).equals(this.playFinalType)){
					String typeCode = PlayTypeUtils.getPlayTypeFinalName(this.playFinalType);
					typeCode = typeCode.substring(typeCode.length()-1,typeCode.length());
					return typeCode;
				}
			}
		}
		if("CQSSC_DOUBLESIDE_ZHDA".equals(this.playFinalType)){
			return "總大";
		}
		if("CQSSC_DOUBLESIDE_ZHX".equals(this.playFinalType)){
			return "總小";
		}
		if("CQSSC_DOUBLESIDE_ZHDAN".equals(this.playFinalType)){
			return "總單";
		}
		if("CQSSC_DOUBLESIDE_ZHS".equals(this.playFinalType)){
			return "總雙";
		}
	}else if(this.playFinalType.indexOf(Constant.LOTTERY_TYPE_BJ)!=-1){
		if("BJ_DOUBLESIDE_DA".equals(this.playFinalType)){
			return "大";
		}
		if("BJ_DOUBLESIDE_X".equals(this.playFinalType)){
			return "小";
		}
		if("BJ_DOUBLESIDE_DAN".equals(this.playFinalType)){
			return "單";
		}
		if("BJ_DOUBLESIDE_S".equals(this.playFinalType)){
			return "雙";
		}
		
	}
	return PlayTypeUtils.getPlayTypeFinalName(this.playFinalType);
}

//由于北京的号球在界面上显示的时候是有区色的。
public String getFinalPlayTypeName() {
	if(this.playFinalType.indexOf(Constant.LOTTERY_TYPE_BJ)!=-1){
		if("BJ_DOUBLESIDE_DA".equals(this.playFinalType)){
			return "<div>大</div>";
		}
		if("BJ_DOUBLESIDE_X".equals(this.playFinalType)){
			return "<div>小</div>";
		}
		if("BJ_DOUBLESIDE_DAN".equals(this.playFinalType)){
			return "<div>單</div>";
		}
		if("BJ_DOUBLESIDE_S".equals(this.playFinalType)){
			return "<div>雙</div>";
		}
		PlayType playType = PlayTypeUtils.getPlayFinalType(this.playFinalType);
		if("1".equals(playType.getPlayFinalType()) && !"GROUP".equals(playType.getPlaySubType())){
			return "<div class=\"f15 c1\">1</div>";
		}else if("2".equals(playType.getPlayFinalType()) && !"GROUP".equals(playType.getPlaySubType())){
			return "<div class=\"f15 c2\">2</div>";
		}else if("3".equals(playType.getPlayFinalType()) && !"GROUP".equals(playType.getPlaySubType())){
			return "<div class=\"f15 c3\">3</div>";
		}else if("4".equals(playType.getPlayFinalType()) && !"GROUP".equals(playType.getPlaySubType())){
			return "<div class=\"f15 c4\">4</div>";
		}else if("5".equals(playType.getPlayFinalType()) && !"GROUP".equals(playType.getPlaySubType())){
			return "<div class=\"f15 c5\">5</div>";
		}else if("6".equals(playType.getPlayFinalType()) && !"GROUP".equals(playType.getPlaySubType())){
			return "<div class=\"f15 c6\">6</div>";
		}else if("7".equals(playType.getPlayFinalType()) && !"GROUP".equals(playType.getPlaySubType())){
			return "<div class=\"f15 c7\">7</div>";
		}else if("8".equals(playType.getPlayFinalType()) && !"GROUP".equals(playType.getPlaySubType())){
			return "<div class=\"f15 c8\">8</div>";
		}else if("9".equals(playType.getPlayFinalType()) && !"GROUP".equals(playType.getPlaySubType())){
			return "<div class=\"f15 c9\">9</div>";
		}else if("10".equals(playType.getPlayFinalType()) && !"GROUP".equals(playType.getPlaySubType())){
			return "<div class=\"f15 c10\">10</div>";
		}
		return "<div>"+PlayTypeUtils.getPlayTypeFinalName(this.playFinalType)+"</div>";
	}
	return PlayTypeUtils.getPlayTypeFinalName(this.playFinalType);
}

public void setFpState(String fpState) {
	this.fpState = fpState;
}
public void setPlayTypeName(String playTypeName) {
	this.playTypeName = playTypeName;
}
public Integer getSortNo() {
	return sortNo;
}
public void setSortNo(Integer sortNo) {
	this.sortNo = sortNo;
}
public int getAvLose() {
	return avLose;
}
public void setAvLose(int avLose) {
	this.avLose = avLose;
}
public String getOverLoseQuatas() {
	return overLoseQuatas;
}
public void setOverLoseQuatas(String overLoseQuatas) {
	this.overLoseQuatas = overLoseQuatas;
}
   
}
