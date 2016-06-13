package com.npc.lottery.util;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;

import com.google.common.collect.Maps;
import com.npc.lottery.common.Constant;
import com.npc.lottery.member.entity.PlayType;
import com.npc.lottery.member.logic.interf.IPlayTypeLogic;


public class PlayTypeUtils {
	
	private static final Map<String,PlayType>  playTypes=Maps.newHashMap();
	
	private static final  Map<String,Integer> commissionTypesDisplayOrderMap = Maps.newHashMap();
	
	static 
	{
		 IPlayTypeLogic playTypeLogic=(IPlayTypeLogic)SpringUtil.getBean("playTypeLogic");
		 List<Criterion> filtersPlayType = new ArrayList<Criterion>();		
		
		 List<PlayType> types=playTypeLogic.findPlayType(filtersPlayType.toArray(new Criterion[filtersPlayType.size()]));
		 for (int i = 0; i < types.size(); i++) {
			 PlayType type=types.get(i);
			 playTypes.put(type.getTypeCode(), type);
			 
			// add by peter
			if (!commissionTypesDisplayOrderMap.containsKey(type.getCommissionType())) {
				commissionTypesDisplayOrderMap.put(type.getCommissionType(), type.getCommissionTypeDisplayOrder());
			}
			
		}
		
	}
	
	public static  PlayType getPlayType(String typeCode)
	{
		
		return playTypes.get(typeCode);
		
		
	}
	
	public static  String getAutoReplenishType(String typeCode)
	{
		String autoReplinishType = "";
		if(playTypes.get(typeCode)!=null) autoReplinishType = playTypes.get(typeCode).getAutoReplenishType();
		return autoReplinishType;
	}
	
	public static String getPlayTypeSubName(String typeCode)
	{
		
		String subTypeName="";
		String finalType="";
		PlayType playType= playTypes.get(typeCode);
		if(playType!=null&&playType.getSubTypeName()!=null)
		{
			subTypeName=playType.getSubTypeName();
			finalType=playType.getPlayFinalType();
		}
		 if(("ZHDA".equals(finalType)||"ZHX".equals(finalType)||"ZHDAN".equals(finalType)||"ZHS".equals(finalType)))
			 subTypeName="";
		return subTypeName;
		
		
	}
	
	public static String  getPlayTypeFinalName(String typeCode)
	{
		
		String finalTypeName="";
		PlayType playType= playTypes.get(typeCode);
		if(playType!=null&&playType.getFinalTypeName()!=null)
			finalTypeName=playType.getFinalTypeName();
		return finalTypeName;
		
		
	}
	/**
	 * 此方法是获取tb_play_type
	 * @param typeCode
	 * @return
	 */
	public static PlayType getPlayFinalType(String typeCode)
	{
		
		PlayType playType= playTypes.get(typeCode);
		return playType;
		
		
	}
	
	public static String getPlayTypeCommissionTypeName(String typeCode)
	{
		Map<String, String> bjMap=Constant.BJSC_COMMISSION_TYPE;
		Map<String, String> cqMap=Constant.CQSSC_COMMISSION_TYPE;
		Map<String, String> gdMap=Constant.GDKLSF_COMMISSION_TYPE;
		Map<String, String> k3Map=Constant.K3_COMMISSION_TYPE;
		String ret="";
		PlayType playType=getPlayType(typeCode);
		String commType=playType.getCommissionType();
		if(typeCode.indexOf("CQSSC")!=-1)
			ret=cqMap.get(commType);
		else if(typeCode.indexOf("GDKLSF")!=-1)
			ret=gdMap.get(commType);
		else if(typeCode.indexOf("BJ")!=-1)
			ret=bjMap.get(commType);
		else if(typeCode.indexOf("K3")!=-1)
			ret=k3Map.get(commType);
		
		
		return ret;
		
		
	}
	
	/**2012-12-14 by Eric
	 * 以下赔率对应类型是用于在实时滚单的左栏里显示大类用的。
	 */
	public static String getOddTypeName(String typeCode)
	{
		Map<String, String> bjMap=Constant.BJSC_ODDS_TYPE;
		Map<String, String> cqMap=Constant.CQSSC_ODDS_TYPE;
		Map<String, String> gdMap=Constant.GDKLSF_ODDS_TYPE;
		Map<String, String> k3Map=Constant.K3_ODDS_TYPE;
		String ret="";
		if(typeCode.indexOf("CQSSC")!=-1)
			ret=cqMap.get(typeCode);
		else if(typeCode.indexOf("GDKLSF")!=-1)
			ret=gdMap.get(typeCode);
		else if(typeCode.indexOf("BJ")!=-1)
			ret=bjMap.get(typeCode);
		else if(typeCode.indexOf("K3")!=-1)
			ret=k3Map.get(typeCode);
		
		
		return ret;
		
		
	}
	/**
	 * 操盤記錄 頁面 主要顯示name
	 * @param typeCode
	 * @return
	 */
	public static String getOddTypeNameLog(String oddType)
	{
		Map<String, String> bjMap=Constant.BJSC_ODDSLOG_TYPE;
		Map<String, String> cqMap=Constant.CQSSC_ODDSLOG_TYPE;
		Map<String, String> gdMap=Constant.GDKLSF_ODDSLOG_TYPE;
		Map<String, String> k3Map=Constant.K3_ODDSLOG_TYPE;
		String ret="";
		if(oddType.indexOf("CQSSC")!=-1)
			if(oddType.indexOf("_FRONT")!=-1){
				ret=cqMap.get("CQSSC_%_FRONT");
			}else if(oddType.indexOf("_MID")!=-1){
				ret=cqMap.get("CQSSC_%_MID");
			}else if(oddType.indexOf("_LAST")!=-1){
				ret=cqMap.get("CQSSC_%_LAST");
			}else if(oddType.indexOf("CQSSC_DOUBLESIDE_HE")!=-1 || oddType.indexOf("CQSSC_DOUBLESIDE_LH")!=-1){
				ret=cqMap.get("CQSSC_DOUBLESIDE_%H%");
			}else{
				ret=cqMap.get(oddType);
			}
		else if(oddType.indexOf("GDKLSF")!=-1)
			ret=gdMap.get(oddType);
		else if(oddType.indexOf("K3")!=-1)
			ret=k3Map.get(oddType);
		else if(oddType.indexOf("BJ")!=-1||oddType.indexOf("BALL_")!=-1){
			if(oddType.equals("BJ_DOUBLESIDE_X")||oddType.equals("BJ_DOUBLESIDE_DA")){
				ret=bjMap.get("BJ_DOUBLESIDE_DX");
			}else if(oddType.equals("BJ_DOUBLESIDE_S")||oddType.equals("BJ_DOUBLESIDE_DAN")){
				ret=bjMap.get("BJ_DOUBLESIDE_DS");
			}else if(oddType.indexOf("BJ_GROUP")!=-1){
				ret=bjMap.get("BJ_GROUP");
			}else{
				ret=bjMap.get(oddType);
			}
				
		}
		
		
		return ret;
		
		
	}
	
	public static String getPlayTypeName(String typeCode) {

	    String playTypeName = "错误数据";

	    if (null != typeCode) {
	        if (typeCode.startsWith("HK")) {
	            playTypeName = "香港六合彩";
	        } else if (typeCode.startsWith("GDKLSF")) {
	            playTypeName = "廣東快樂十分";
	        } else if (typeCode.startsWith("CQSSC")) {
	            playTypeName = "重慶時時彩";
		    } else if (typeCode.startsWith("BJ")) {
		    	playTypeName = "北京賽車(PK10)";
		    } else if (typeCode.startsWith("K3")) {
		    	playTypeName = "江苏骰寶(K3)";
		    }else if (typeCode.startsWith("NC")) {//add by peter for 幸运农场
		    	playTypeName = "幸运农场";
		    }
	    }

	    return playTypeName;
	}
	
	public static String getTypeCodeNameOdd(String typeCode,BigDecimal odds,BigDecimal odds2,String attribute,String selectedOdds,Integer count){
		String result = "";
        result = PlayTypeUtils.getPlayTypeSubName(typeCode);

        if ("HK_STRAIGHTTHROUGH_3Z2".equals(typeCode)) {
            result = result + "『"
                    + PlayTypeUtils.getPlayTypeFinalName(typeCode)
                    + "』@<font color='red'>" + odds
                    + "</font> 中叁 @<font color='red'>" + odds2
                    + "</font><br/>" + attribute.replace("|", ",");

        } else if ("HK_STRAIGHTTHROUGH_2ZT".equals(typeCode)) {
            result = result + "『"
                    + PlayTypeUtils.getPlayTypeFinalName(typeCode)
                    + "』@<font color='red'>" + odds
                    + "</font> 中二 @<font color='red'>" + odds2
                    + "</font><br/>" + attribute.replace("|", ",");

        } else if ("HK_GG".equals(typeCode)) {
            String[] guan = StringUtils.split(attribute, "\\|");
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
                    + PlayTypeUtils.getPlayTypeFinalName(typeCode)
                    + "』<br/>" + StringUtils.join(guan, ",");

        } else if (typeCode.indexOf("HK_SXL") != -1) {
            String[] danAndSX = StringUtils.split(attribute, "\\&");
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
                        + PlayTypeUtils.getPlayTypeFinalName(typeCode)
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
                        + PlayTypeUtils.getPlayTypeFinalName(typeCode)
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
                        + PlayTypeUtils.getPlayTypeFinalName(typeCode)
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
                        + PlayTypeUtils.getPlayTypeFinalName(typeCode)
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
                    + PlayTypeUtils.getPlayTypeFinalName(typeCode)
                    + "』<br/>" + StringUtils.join(ballNum, ",");

        } else if (attribute == null) {
            if (typeCode.indexOf("HK_ZM_ZH") != -1) {
                result = "『"
                        + PlayTypeUtils.getPlayTypeFinalName(typeCode)
                        + "』@<font color='red'>" + odds + "</font><br/>";
            } else {
                result = "<font color='blue'>" + result + "『"
                        + PlayTypeUtils.getPlayTypeFinalName(typeCode)
                        + "』</font>@<font color='red'>" + odds + "</font><br/>";
            }
        } else {
            result = "<font color='blue'>" + result + "『"
                    + PlayTypeUtils.getPlayTypeFinalName(typeCode)
                    + "』</font>@<font color='red'>" + odds + "</font><br/>";
                    if(count>1){
                    	result +=  "復式『 &nbsp;" + count + "&nbsp;組』<br/>";
                    }
                    result +=  attribute.replace("|", ",");

        }

        return result;
	}
	
	//用于备份打印到文件时用,与上面方法的区别在于去除了网页的格式控制
	public static String getTypeCodeNameOddForFile(String typeCode,BigDecimal odds,BigDecimal odds2,String attribute,String selectedOdds){
		String result = "";
		result = PlayTypeUtils.getPlayTypeSubName(typeCode);
		
		if ("HK_STRAIGHTTHROUGH_3Z2".equals(typeCode)) {
			result = result + "『"
					+ PlayTypeUtils.getPlayTypeFinalName(typeCode)
					+ "』@" + odds
					+ " 中叁 @" + odds2
					+ "" + attribute.replace("|", ",");
			
		} else if ("HK_STRAIGHTTHROUGH_2ZT".equals(typeCode)) {
			result = result + "『"
					+ PlayTypeUtils.getPlayTypeFinalName(typeCode)
					+ "』@" + odds
					+ " 中二 @" + odds2
					+ "" + attribute.replace("|", ",");
			
		} else if ("HK_GG".equals(typeCode)) {
			String[] guan = StringUtils.split(attribute, "\\|");
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
						+ "@"
						+ StringUtils.substringBetween(selectedOdds + "|",
								ballName + "&", "|") + "";
				
			}
			result = result + "『"
					+ PlayTypeUtils.getPlayTypeFinalName(typeCode)
					+ "』" + StringUtils.join(guan, ",");
			
		} else if (typeCode.indexOf("HK_SXL") != -1) {
			String[] danAndSX = StringUtils.split(attribute, "\\&");
			String[] dan = null;
			String[] sx = null;
			if (danAndSX.length == 2) {
				dan = StringUtils.split(danAndSX[0], "\\|");
				sx = StringUtils.split(danAndSX[1], "\\|");
				
				for (int j = 0; j < dan.length; j++) {
					dan[j] = Constant.HK_SX_MAP.get(dan[j])
							+ "@"
							+ StringUtils.substringBetween(selectedOdds + "|",
									dan[j] + "&", "|") + "";
				}
				for (int k = 0; k < sx.length; k++) {
					sx[k] = Constant.HK_SX_MAP.get(sx[k])
							+ "@"
							+ StringUtils.substringBetween(selectedOdds + "|",
									sx[k] + "&", "|") + "";
				}
				result = result + "『"
						+ PlayTypeUtils.getPlayTypeFinalName(typeCode)
						+ "』" + StringUtils.join(dan, ",")
						+ StringUtils.join(sx, ",");
				
			} else if (danAndSX.length == 1) {
				sx = StringUtils.split(danAndSX[0], "\\|");
				for (int k = 0; k < sx.length; k++) {
					sx[k] = Constant.HK_SX_MAP.get(sx[k])
							+ "@"
							+ StringUtils.substringBetween(selectedOdds + "|",
									sx[k] + "&", "|") + "";
				}
				result = result + "『"
						+ PlayTypeUtils.getPlayTypeFinalName(typeCode)
						+ "』" + StringUtils.join(sx, ",");
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
							+ "@"
							+ StringUtils.substringBetween(selectedOdds + "|",
									dan[j] + "&", "|") + "";
				}
				for (int k = 0; k < ws.length; k++) {
					ws[k] = Constant.HK_WS_MAP.get(ws[k])
							+ "@"
							+ StringUtils.substringBetween(selectedOdds + "|",
									ws[k] + "&", "|") + "";
				}
				result = result + "『"
						+ PlayTypeUtils.getPlayTypeFinalName(typeCode)
						+ "』" + StringUtils.join(dan, ",")
						+ " 拖 "
						+ StringUtils.join(ws, ",");
				
			} else if (danAndWS.length == 1) {
				ws = StringUtils.split(danAndWS[0], "\\|");
				for (int k = 0; k < ws.length; k++) {
					ws[k] = Constant.HK_WS_MAP.get(ws[k])
							+ "@"
							+ StringUtils.substringBetween(selectedOdds + "|",
									ws[k] + "&", "|") + "";
				}
				result = result + "『"
						+ PlayTypeUtils.getPlayTypeFinalName(typeCode)
						+ "』" + StringUtils.join(ws, ",");
			}
			
		} else if (typeCode.indexOf("HK_WBZ") != -1) {
			String[] ballNum = attribute.split("\\|");
			for (int j = 0; j < ballNum.length; j++) {
				String ballName = StringUtils.leftPad(ballNum[j], 2, '0');
				ballNum[j] = ballName
						+ "@"
						+ StringUtils.substringBetween(selectedOdds + "|",
								ballName + "&", "|") + "";
				;
			}
			result = result + "『"
					+ PlayTypeUtils.getPlayTypeFinalName(typeCode)
					+ "』" + StringUtils.join(ballNum, ",");
			
		} else if (attribute == null) {
			if (typeCode.indexOf("HK_ZM_ZH") != -1) {
				result = "『"
						+ PlayTypeUtils.getPlayTypeFinalName(typeCode)
						+ "』@" + odds + "";
			} else {
				result = result + "『"
						+ PlayTypeUtils.getPlayTypeFinalName(typeCode)
						+ "』 @ " + odds + "";
			}
		} else {
			result = result + "『"
					+ PlayTypeUtils.getPlayTypeFinalName(typeCode)
					+ "』 @ " + odds + " "
					+ attribute.replace("|", "、");
			
		}
		
		return result;
	}
	
	/**
	 * 获取大类的排列顺序
	 * @param commissionType
	 * @return
	 */
	public static int getCommissionTypeDisplayOrder(String commissionType) {
		return commissionTypesDisplayOrderMap.get(commissionType) != null ? commissionTypesDisplayOrderMap.get(commissionType) : 99999;
	}
}
