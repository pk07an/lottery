package com.npc.lottery.member.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.npc.lottery.common.Constant;

import edu.emory.mathcs.backport.java.util.Collections;

public class PlayType implements Serializable, Comparable<PlayType> {
	private static final long serialVersionUID = 1L;

	// primary key
	private Long id;

	// fields
	private String typeCode;
	private String typeName = "";
	private String playType;
	private String playSubType;
	private String playFinalType;
	private String state;
	private String remark;
	private String subTypeName = "";
	private String finalTypeName = "";
	private String oddsType;
	private String commissionType;
	private String autoReplenishType;
	private Integer displayOrder;

	private int count = 0;
	
	private Integer commissionTypeDisplayOrder;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public java.lang.String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(java.lang.String typeCode) {
		this.typeCode = typeCode;
	}

	public java.lang.String getTypeName() {
		return typeName;
	}

	public void setTypeName(java.lang.String typeName) {
		this.typeName = typeName;
	}

	public java.lang.String getPlayType() {
		return playType;
	}

	public void setPlayType(java.lang.String playType) {
		this.playType = playType;
	}

	public java.lang.String getPlaySubType() {
		return playSubType;
	}

	public void setPlaySubType(java.lang.String playSubType) {
		this.playSubType = playSubType;
	}

	public java.lang.String getPlayFinalType() {
		return playFinalType;
	}

	public void setPlayFinalType(java.lang.String playFinalType) {
		this.playFinalType = playFinalType;
	}

	public java.lang.String getState() {
		return state;
	}

	public void setState(java.lang.String state) {
		this.state = state;
	}

	public java.lang.String getRemark() {
		return remark;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}

	public java.lang.String getSubTypeName() {
		return subTypeName;
	}

	public void setSubTypeName(java.lang.String subTypeName) {
		this.subTypeName = subTypeName;
	}

	public java.lang.String getFinalTypeName() {
		return finalTypeName;
	}

	public void setFinalTypeName(java.lang.String finalTypeName) {
		this.finalTypeName = finalTypeName;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public int compareTo(PlayType o) {
        
		if(o.count==this.count)
		{
			if("BJ".equals(playType))
				return o.getBJDoubleSideOrder()-this.getBJDoubleSideOrder();
			else if("CQSSC".equals(playType))
				return o.getCQDoubleSideOrder()-this.getCQDoubleSideOrder();
			else
			return o.getGDDoubleSideOrder()-this.getGDDoubleSideOrder();
		}
		else
			
		return o.count - this.count;

	}

	public static void main(String[] args) {
		List<PlayType> li = new ArrayList<PlayType>();
		PlayType p1 = new PlayType();
		p1.setPlayFinalType("DA");
		p1.setTypeCode("BJ_DOUBLESIDE_1_DA");
		p1.setCount(2);

		PlayType p2 = new PlayType();
		p2.setCount(2);
		p2.setPlayFinalType("X");
		p2.setTypeCode("BJ_DOUBLESIDE_1_X");
		
		PlayType p3 = new PlayType();
		p3.setCount(5);
		p3.setPlayFinalType("X");
		p3.setTypeCode("BJ_DOUBLESIDE_2_X");
		
		li.add(p3);
		li.add(p1);
		li.add(p2);
		Collections.sort(li);
		for (int i = 0; i < li.size(); i++) {
			System.out.println(li.get(i).getTypeCode());
		}

	}

	public String getOddsType() {
		return oddsType;
	}

	public void setOddsType(String oddsType) {
		this.oddsType = oddsType;
	}

	public String getCommissionType() {
		return commissionType;
	}

	public void setCommissionType(String commissionType) {
		this.commissionType = commissionType;
	}

	public String getAutoReplenishType() {
		return autoReplenishType;
	}

	public void setAutoReplenishType(String autoReplenishType) {
		this.autoReplenishType = autoReplenishType;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	/**
	 * 根据commissionType获取对应的中文名称
	 * 
	 * @return
	 */
	public String getCommissionTypeName() {

		if (commissionType.trim().equals("GD_ONE_BALL")) {
			return "第一球";
		} else if (commissionType.trim().equals("GD_TWO_BALL")) {
			return "第二球";
		} else if (commissionType.trim().equals("GD_THREE_BALL")) {
			return "第三球";
		} else if (commissionType.trim().equals("GD_FOUR_BALL")) {
			return "第四球";
		} else if (commissionType.trim().equals("GD_FIVE_BALL")) {
			return "第五球";
		} else if (commissionType.trim().equals("GD_SIX_BALL")) {
			return "第六球";
		} else if (commissionType.trim().equals("GD_SEVEN_BALL")) {
			return "第七球";
		} else if (commissionType.trim().equals("GD_EIGHT_BALL")) {
			return "第八球";
		} else if (commissionType.trim().equals("GD_OEDX_BALL")) {
			return "第1-8大小";
		} else if (commissionType.trim().equals("GD_OEDS_BALL")) {
			return "第1-8單雙";
		} else if (commissionType.trim().equals("GD_OEWSDX_BALL")) {
			return "第1-8尾數大小";
		} else if (commissionType.trim().equals("GD_HSDS_BALL")) {
			return "第1-8合數單雙";
		} else if (commissionType.trim().equals("GD_FW_BALL")) {
			return "第1-8方位";
		} else if (commissionType.trim().equals("GD_ZF_BALL")) {
			return "第1-8中發";
		} else if (commissionType.trim().equals("GD_B_BALL")) {
			return "第1-8白";
		} else if (commissionType.trim().equals("GD_ZHDX_BALL")) {
			return "總和大小";
		} else if (commissionType.trim().equals("GD_ZHDS_BALL")) {
			return "總和單雙";
		} else if (commissionType.trim().equals("GD_ZHWSDX_BALL")) {
			return "總和尾數大小";
		} else if (commissionType.trim().equals("GD_LH_BALL")) {
			return "龍虎";
		} else if (commissionType.trim().equals("GD_RXH_BALL")) {
			return "任選二";
		} else if (commissionType.trim().equals("GD_RTLZ_BALL")) {
			return "選二連組";
		} else if (commissionType.trim().equals("GD_RELZ_BALL")) {
			return "選二連直 ";
		} else if (commissionType.trim().equals("GD_RXS_BALL")) {
			return "任選三";
		} else if (commissionType.trim().equals("GD_XSQZ_BALL")) {
			return "選三前直 ";
		} else if (commissionType.trim().equals("GD_XTQZ_BALL")) {
			return "選三前組";
		} else if (commissionType.trim().equals("GD_RXF_BALL")) {
			return "任選四";
		} else if (commissionType.trim().equals("GD_RXW_BALL")) {
			return "任選五";
		}else if (commissionType.trim().equals("NC_ONE_BALL")) {
			return "第一球";
		} else if (commissionType.trim().equals("NC_TWO_BALL")) {
			return "第二球";
		} else if (commissionType.trim().equals("NC_THREE_BALL")) {
			return "第三球";
		} else if (commissionType.trim().equals("NC_FOUR_BALL")) {
			return "第四球";
		} else if (commissionType.trim().equals("NC_FIVE_BALL")) {
			return "第五球";
		} else if (commissionType.trim().equals("NC_SIX_BALL")) {
			return "第六球";
		} else if (commissionType.trim().equals("NC_SEVEN_BALL")) {
			return "第七球";
		} else if (commissionType.trim().equals("NC_EIGHT_BALL")) {
			return "第八球";
		} else if (commissionType.trim().equals("NC_OEDX_BALL")) {
			return "第1-8大小";
		} else if (commissionType.trim().equals("NC_OEDS_BALL")) {
			return "第1-8單雙";
		} else if (commissionType.trim().equals("NC_OEWSDX_BALL")) {
			return "第1-8尾數大小";
		} else if (commissionType.trim().equals("NC_HSDS_BALL")) {
			return "第1-8合數單雙";
		} else if (commissionType.trim().equals("NC_FW_BALL")) {
			return "第1-8方位";
		} else if (commissionType.trim().equals("NC_ZF_BALL")) {
			return "第1-8中發";
		} else if (commissionType.trim().equals("NC_B_BALL")) {
			return "第1-8白";
		} else if (commissionType.trim().equals("NC_ZHDX_BALL")) {
			return "總和大小";
		} else if (commissionType.trim().equals("NC_ZHDS_BALL")) {
			return "總和單雙";
		} else if (commissionType.trim().equals("NC_ZHWSDX_BALL")) {
			return "總和尾數大小";
		} else if (commissionType.trim().equals("NC_LH_BALL")) {
			return "龍虎";
		} else if (commissionType.trim().equals("NC_RXH_BALL")) {
			return "任選二";
		} else if (commissionType.trim().equals("NC_RTLZ_BALL")) {
			return "選二連組";
		} else if (commissionType.trim().equals("NC_RELZ_BALL")) {
			return "選二連直 ";
		} else if (commissionType.trim().equals("NC_RXS_BALL")) {
			return "任選三";
		} else if (commissionType.trim().equals("NC_XSQZ_BALL")) {
			return "選三前直 ";
		} else if (commissionType.trim().equals("NC_XTQZ_BALL")) {
			return "選三前組";
		} else if (commissionType.trim().equals("NC_RXF_BALL")) {
			return "任選四";
		} else if (commissionType.trim().equals("NC_RXW_BALL")) {
			return "任選五";
		} else if (commissionType.trim().equals("CQ_ONE_BALL")) {
			return "第一球";
		} else if (commissionType.trim().equals("CQ_TWO_BALL")) {
			return "第二球";
		} else if (commissionType.trim().equals("CQ_THREE_BALL")) {
			return "第三球";
		} else if (commissionType.trim().equals("CQ_FOUR_BALL")) {
			return "第四球";
		} else if (commissionType.trim().equals("CQ_FIVE_BALL")) {
			return "第五球";
		} else if (commissionType.trim().equals("CQ_OFDX_BALL")) {
			return "第1-5大小";
		} else if (commissionType.trim().equals("CQ_OFDS_BALL")) {
			return "第1-5單雙";
		} else if (commissionType.trim().equals("CQ_ZHDX_BALL")) {
			return "總合大小";
		} else if (commissionType.trim().equals("CQ_ZHDS_BALL")) {
			return "總合單雙";
		} else if (commissionType.trim().equals("CQ_LH_BALL")) {
			return "龍虎和";
		} else if (commissionType.trim().equals("CQ_QS_BALL")) {
			return "前三 ";
		} else if (commissionType.trim().equals("CQ_ZS_BALL")) {
			return "中三";
		} else if (commissionType.trim().equals("CQ_HS_BALL")) {
			return "後三";
		} else if (commissionType.trim().equals("K3_SJ")) {
			return "三軍";
		} else if (commissionType.trim().equals("K3_WS")) {
			return "圍骰";
		} else if (commissionType.trim().equals("K3_QS")) {
			return "全骰 ";
		} else if (commissionType.trim().equals("K3_DS")) {
			return "點數";
		} else if (commissionType.trim().equals("K3_CP")) {
			return "長牌";	
		} else if (commissionType.trim().equals("K3_DP")) {
			return "短牌";	
		} else if (commissionType.trim().equals("K3_DX")) {
			return "大小";	
		}
		if(commissionType.trim().indexOf("BJ")!=-1){
			return Constant.BJSC_COMMISSION_TYPE.get(commissionType);
		}

		return "";
	}

	public int getGDDoubleSideOrder() {

	  if (playFinalType.indexOf("ZHDA") != -1)

			return 10000;
		else if (playFinalType.indexOf("ZHX") != -1)

			return 9900;
		else if (playFinalType.indexOf("ZHDAN") != -1)
			return 9800;
		else if (playFinalType.indexOf("ZHS") != -1)
			return 9700;
		else if(playFinalType.indexOf("ZHWD") != -1)
			return 9600;
		else if(playFinalType.indexOf("ZHWX") != -1)
			return 9500;
		else if(playFinalType.indexOf("LONG") != -1)
			return 9400;
		else if(playFinalType.indexOf("HU") != -1)
			return 9300;
		else 
		{   
//			int ret=9000;
//			if (typeCode.indexOf("DOUBLESIDE_1") != -1)
//				ret= 8000;
//			else if (typeCode.indexOf("DOUBLESIDE_2") != -1)
//				ret= 7000;
//			else if (typeCode.indexOf("DOUBLESIDE_3") != -1)
//				ret= 6000;
//			else if (typeCode.indexOf("DOUBLESIDE_4") != -1)
//				ret= 5000;
//			else if (typeCode.indexOf("DOUBLESIDE_5") != -1)
//				ret= 4000;
//			else if (typeCode.indexOf("DOUBLESIDE_6") != -1)
//				ret= 3000;
//			else if (typeCode.indexOf("DOUBLESIDE_7") != -1)
//				ret= 2000;
//			else if (typeCode.indexOf("DOUBLESIDE_8") != -1)
//				ret= 1000;
//			if(playFinalType.indexOf("DA") != -1)
//				ret=ret-10;
//			else if(playFinalType.indexOf("X") != -1)
//				ret=ret-20;
//			else if(playFinalType.indexOf("DAN") != -1)
//				ret=ret-30;
//			else if(playFinalType.indexOf("S") != -1)
//				ret=ret-40;
//			else if(playFinalType.indexOf("WD") != -1)
//				ret=ret-50;
//			else if(playFinalType.indexOf("WX") != -1)
//				ret=ret-60;
//			else if(playFinalType.indexOf("HSD") != -1)
//				ret=ret-70;
//			else if(playFinalType.indexOf("HSS") != -1)
//				ret=ret-80;
			//fixed by peter
			int ret = -displayOrder;
			return ret;
			
		}
			


	}

	public int getCQDoubleSideOrder() {

		if (playFinalType.indexOf("LONG") != -1)
			return 100;
		else if (playFinalType.indexOf("HU") != -1)
			return 99;
		else if (playFinalType.indexOf("ZHDA") != -1)

			return 98;
		else if (playFinalType.indexOf("ZHX") != -1)

			return 97;
		else if (playFinalType.indexOf("ZHDAN") != -1)
			return 96;
		else if (playFinalType.indexOf("ZHS") != -1)
			return 95;
		else 
		{
//			int ret=80;
//			if(typeCode.indexOf("DOUBLESIDE_1") != -1)
//			{
//				ret=ret-10;
//			}else if(typeCode.indexOf("DOUBLESIDE_2") != -1)
//			{
//				ret=ret-20;
//			}
//			else if(typeCode.indexOf("DOUBLESIDE_3") != -1)
//			{
//				ret=ret-30;
//			}
//			else if(typeCode.indexOf("DOUBLESIDE_4") != -1)
//			{
//				ret=ret-40;
//			}
//			else if(typeCode.indexOf("DOUBLESIDE_5") != -1)
//			{
//				ret=ret-50;
//			}
//			if(playFinalType.indexOf("DA")!=-1)
//			{
//				ret=ret-1;
//			}
//			else if(playFinalType.indexOf("X")!=-1)
//			{
//				ret=ret-2;
//			}
//			else if(playFinalType.indexOf("DAN")!=-1)
//			{
//				ret=ret-3;
//			}
//			else if(playFinalType.indexOf("S")!=-1)
//			{
//				ret=ret-4;
//			}
			//fixed by peter
			int ret = -displayOrder;
			return ret;
		}


	}

	public int getBJDoubleSideOrder()
    {
    	int ret=10000;
    	if( typeCode.indexOf("BJ_DOUBLESIDE_DA") != -1)
    	return ret-100;
    	else if( typeCode.indexOf("BJ_DOUBLESIDE_X") != -1)
        	return ret-200;
    	else if( typeCode.indexOf("BJ_DOUBLESIDE_DAN") != -1)
        	return ret-300;
    	else if( typeCode.indexOf("BJ_DOUBLESIDE_S") != -1)
        	return ret-400;
    	else
    	{
//    		   ret=9000;          
//			if (typeCode.indexOf("DOUBLESIDE_1") != -1&&typeCode.indexOf("DOUBLESIDE_10") == -1)
//				ret= ret-100;
//			else if (typeCode.indexOf("DOUBLESIDE_2") != -1)
//				ret= ret-200;
//			else if (typeCode.indexOf("DOUBLESIDE_3") != -1)
//				ret= ret-300;
//			else if (typeCode.indexOf("DOUBLESIDE_4") != -1)
//				ret= ret-400;
//			else if (typeCode.indexOf("DOUBLESIDE_5") != -1)
//				ret= ret-500;
//			else if (typeCode.indexOf("DOUBLESIDE_6") != -1)
//				ret= ret-600;
//			else if (typeCode.indexOf("DOUBLESIDE_7") !=- 1)
//				ret= ret-700;
//			else if (typeCode.indexOf("DOUBLESIDE_8") != -1)
//				ret= ret-800;
//			else if (typeCode.indexOf("DOUBLESIDE_9") != -1)
//				ret= ret-900;
//			else if (typeCode.indexOf("DOUBLESIDE_10") != -1)
//				ret= ret-1000;
//			if(playFinalType.indexOf("DA") != -1)
//				ret= ret-1;
//			else if(playFinalType.indexOf("X") != -1)
//				ret= ret-2;
//			else if(playFinalType.indexOf("DAN") != -1)
//				ret= ret-3;
//			else if(playFinalType.indexOf("S") != -1)
//				ret= ret-4;
//			else if(playFinalType.indexOf("LONG") != -1)
//				ret= ret-5;
//			else if(playFinalType.indexOf("HU") != -1)
//				ret= ret-6;
    		//fixed by peter
    		ret = -displayOrder;
			
			return ret;
    		
    		
    		
    		
    		
    	}
    	
    	
    	
    }

	/**
	 * 根据commissionType获取对应的中文名称 + 玩法名称
	 * 
	 * @return
	 */
	public String getCommissionTypeName2() {
		return typeName + " -- " + this.getCommissionTypeName();
	}

	public Integer getCommissionTypeDisplayOrder() {
		return commissionTypeDisplayOrder;
	}

	public void setCommissionTypeDisplayOrder(Integer commissionTypeDisplayOrder) {
		this.commissionTypeDisplayOrder = commissionTypeDisplayOrder;
	}

	
}