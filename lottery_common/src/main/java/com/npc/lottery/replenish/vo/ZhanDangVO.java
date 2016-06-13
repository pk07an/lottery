package com.npc.lottery.replenish.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import com.npc.lottery.common.Constant;
import com.npc.lottery.util.PlayTypeUtils;

public class ZhanDangVO implements Serializable {
 
   private static final long serialVersionUID = 1L;
   private String typeCode;
   
   //注额*拥金*占成=退水  (备注:在帐单里面同时把这项显示在退水后结果处，因为退水后结果=实占输赢+退水,而实占输赢在这个界面上是开奖前的，所以为0
   private java.math.BigDecimal commissionMoney = BigDecimal.ZERO;
   private java.math.BigDecimal totalMoney = BigDecimal.ZERO;//注额*占成=实占金额
   private Integer turnover; //单项笔数  
   private Integer total = 0; //总笔数
   private String playTypeName; //玩法中文名称
   private String playFinalType; 
   
   private Integer sortNo; //用户排序,方便在界面按规定的顺序显示
  
   private String commissionType;
   private String commissionTypeName;
   
   private String attribute; //用于连码
   
   private Integer YLsum; //统计遗漏
   
   private java.math.BigDecimal rightBarMoney = BigDecimal.ZERO;//注额*占成=实占金额

public java.math.BigDecimal getCommissionMoney() {
	return commissionMoney.setScale(1,BigDecimal.ROUND_HALF_UP);
}
public void setCommissionMoney(java.math.BigDecimal commissionMoney) {
	this.commissionMoney = commissionMoney;
}
public Integer getTotal() {
	return total;
}
public String getPlayTypeName() {
	/*if(this.typeCode.indexOf(Constant.LOTTERY_TYPE_CQSSC)!=-1){
		String[] list = new String[] {"FIRST","SECOND","THIRD","FORTH","FIFTH"};
		for(int j=0;j<list.length;j++){
			for(int i=1;i<=9;i++){
				if(("CQSSC_BALL_" + list[j] + "_" + i).equals(this.typeCode)){
					String typeCode = PlayTypeUtils.getPlayTypeFinalName(this.typeCode);
					typeCode = typeCode.substring(typeCode.length()-1,typeCode.length());
					return typeCode;
				}
			}
		}
	}else if(this.typeCode.indexOf(Constant.LOTTERY_TYPE_BJ)!=-1){
		if("BJ_DOUBLESIDE_DA".equals(this.typeCode)){
			return "大";
		}
		if("BJ_DOUBLESIDE_X".equals(this.typeCode)){
			return "小";
		}
		if("BJ_DOUBLESIDE_DAN".equals(this.typeCode)){
			return "單";
		}
		if("BJ_DOUBLESIDE_S".equals(this.typeCode)){
			return "雙";
		}
		
	}*/ 
	
	if(this.typeCode.indexOf("_DA")!=-1){	//这个条件下只会查出大、单、总和大、总和单
		//广东和重庆有总和单和，总和大，把这两项排除,北京是DOUBLESIDE_DA和DOUBLESIDE_DAN
		if(this.typeCode.indexOf("DOUBLESIDE_ZHDA")==-1 || this.typeCode.indexOf("DOUBLESIDE_DA")==-1){
			return PlayTypeUtils.getPlayTypeSubName(this.typeCode) + "【" +PlayTypeUtils.getPlayTypeFinalName(this.typeCode) + "】";
		}
	}else if("BJ".equals(PlayTypeUtils.getPlayType(typeCode).getPlayType()) && "LONG".equals(PlayTypeUtils.getPlayType(typeCode).getPlayFinalType()) 
			|| "BJ".equals(PlayTypeUtils.getPlayType(typeCode).getPlayType()) && "HU".equals(PlayTypeUtils.getPlayType(typeCode).getPlayFinalType())){
		
		return PlayTypeUtils.getPlayTypeSubName(this.typeCode) + "【" +PlayTypeUtils.getPlayTypeFinalName(this.typeCode) + "】";
	}else if(typeCode.indexOf("_DONG")!=-1 || typeCode.indexOf("_NAN")!=-1 || typeCode.indexOf("_XI")!=-1 || typeCode.indexOf("_W")!=-1
			|| typeCode.indexOf("_BEI")!=-1  || typeCode.indexOf("_B")!=-1 || typeCode.indexOf("_Z")!=-1  || typeCode.indexOf("_F")!=-1){
		return PlayTypeUtils.getPlayTypeSubName(this.typeCode) + "【" +PlayTypeUtils.getPlayTypeFinalName(this.typeCode) + "】";
		
	}else if(this.typeCode.indexOf("_X")!=-1){
		//排除总和小和北京的冠亚军小
		if(this.typeCode.indexOf("DOUBLESIDE_ZHX")==-1 || this.typeCode.indexOf("DOUBLESIDE_X")==-1){
			return PlayTypeUtils.getPlayTypeSubName(this.typeCode) + "【" +PlayTypeUtils.getPlayTypeFinalName(this.typeCode) + "】";
		}
		//双的处理
	}else if("S".equals(PlayTypeUtils.getPlayType(typeCode).getPlayFinalType()) || "BJ_DOUBLESIDE_S".equals(this.typeCode)){
		return PlayTypeUtils.getPlayTypeSubName(this.typeCode) + "【" +PlayTypeUtils.getPlayTypeFinalName(this.typeCode) + "】";
		//前三、 中三、后三
	}else if("FRONT".equals(PlayTypeUtils.getPlayType(typeCode).getPlaySubType()) || "MID".equals(PlayTypeUtils.getPlayType(typeCode).getPlaySubType()) || "LAST".equals(PlayTypeUtils.getPlayType(typeCode).getPlaySubType())){
		return PlayTypeUtils.getPlayTypeSubName(this.typeCode) + "【" +PlayTypeUtils.getPlayTypeFinalName(this.typeCode) + "】";
	}else if("HSD".equals(PlayTypeUtils.getPlayType(typeCode).getPlayFinalType()) || "HSS".equals(PlayTypeUtils.getPlayType(typeCode).getPlayFinalType())
			|| "WD".equals(PlayTypeUtils.getPlayType(typeCode).getPlayFinalType()) || "WX".equals(PlayTypeUtils.getPlayType(typeCode).getPlayFinalType())){
		return PlayTypeUtils.getPlayTypeSubName(this.typeCode) + "【" +PlayTypeUtils.getPlayTypeFinalName(this.typeCode) + "】";
		//前三、 中三、后三
	}
	return PlayTypeUtils.getPlayTypeFinalName(this.typeCode);
		
}

public String getPlayFinalType() {
	return playFinalType;
}
public void setPlayFinalType(String playFinalType) {
	this.playFinalType = playFinalType;
}
public Integer getSortNo() {
	return sortNo;
}
public void setSortNo(Integer sortNo) {
	this.sortNo = sortNo;
}
public String getCommissionType() {
	return commissionType;
}
public void setCommissionType(String commissionType) {
	this.commissionType = commissionType;
}
public String getCommissionTypeName() {
	if(this.commissionType.indexOf("GD")!=-1){
		return Constant.GDKLSF_COMMISSION_TYPE.get(this.commissionType); 
	}else if(this.commissionType.indexOf("CQ")!=-1){
		return Constant.GDKLSF_COMMISSION_TYPE.get(this.commissionType); 
	}else{
		return Constant.GDKLSF_COMMISSION_TYPE.get(this.commissionType); 
	}
}
public Integer getYLsum() {
	return YLsum;
}
public void setYLsum(Integer yLsum) {
	YLsum = yLsum;
}
public void setTotal(Integer total) {
	this.total = total;
}
public String getTypeCode() {
	return typeCode;
}
public java.math.BigDecimal getTotalMoney() {
	return totalMoney.setScale(1,BigDecimal.ROUND_HALF_UP);
}
public Integer getTurnover() {
	return turnover;
}
public void setTypeCode(String typeCode) {
	this.typeCode = typeCode;
}
public void setTotalMoney(java.math.BigDecimal totalMoney) {
	this.totalMoney = totalMoney;
}
public void setTurnover(Integer turnover) {
	this.turnover = turnover;
}
public java.math.BigDecimal getRightBarMoney() {
	return totalMoney.setScale(0,BigDecimal.ROUND_HALF_UP);
}
public String getAttribute() {
	return attribute;
}
public void setAttribute(String attribute) {
	this.attribute = attribute;
}
   
}
