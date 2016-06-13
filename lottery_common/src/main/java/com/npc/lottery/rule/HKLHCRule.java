package com.npc.lottery.rule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.validator.GenericValidator;

import com.google.common.collect.Lists;
import com.npc.lottery.common.ConstantType;
import com.npc.lottery.member.entity.PlayType;
import com.npc.lottery.util.Tool;


public class HKLHCRule {
	
	// 大
	public static final List<Integer> DA = new ArrayList<Integer>();
	// 小
	public static final List<Integer> X = new ArrayList<Integer>();
	// 單
	public static final List<Integer> DAN = new ArrayList<Integer>();
	// 雙
	public static final List<Integer> SHUANG = new ArrayList<Integer>();
	// 尾大
	public static final List<Integer> WD = new ArrayList<Integer>();
	// 尾小
	public static final List<Integer> WX = new ArrayList<Integer>();
	// 和單
	public static final List<Integer> HSD = new ArrayList<Integer>();
	// 和雙
	public static final List<Integer> HSS = new ArrayList<Integer>();
	//紅色
	public static final List<Integer> RED= Lists.newArrayList(1,2,7,8,12,13,18,19,23,24,29,30,34,35,40,45,46);
	//紅單
	public static final List<Integer> REDDAN = Lists.newArrayList(1,7,13,19,23,29,35,45);
	//紅雙
	public static final List<Integer> REDSHUANG = Lists.newArrayList(2,8,12,18,24,30,34,40,46);
	//紅大
	public static final List<Integer> REDDA = Lists.newArrayList(29,30,34,35,40,45,46);
	//紅小
	public static final List<Integer> REDXIAO = Lists.newArrayList(1,2,7,8,12,13,18,19,23,24);
	//藍色
	public static final List<Integer> BLUE = Lists.newArrayList(3,4,9,10,14,15,20,25,26,31,36,37,41,42,47,48);
	//藍單
	public static final List<Integer> BLUEDAN = Lists.newArrayList(3,9,15,25,31,37,41,47);
	//藍雙
	public static final List<Integer> BLUESHUANG = Lists.newArrayList(4,10,14,20,26,36,42,48);
	//藍大
	public static final List<Integer> BLUEDA = Lists.newArrayList(25,26,31,36,37,41,42,47,48);
	//藍小
	public static final List<Integer> BLUEXIAO = Lists.newArrayList(3,4,9,10,14,15,20);
	//綠色
	public static final List<Integer> GREEN = Lists.newArrayList(5,6,11,16,17,21,22,27,28,32,33,38,39,43,44,49);
	//綠單
	public static final List<Integer> GREENDAN = Lists.newArrayList(5,11,17,21,27,33,39,43,49);
	//綠雙
	public static final List<Integer> GREENSHUANG = Lists.newArrayList(6,16,22,28,32,38,44);
	//綠大
	public static final List<Integer> GREENDA = Lists.newArrayList(27,28,32,33,38,39,43,44,49);
	//綠小
	public static final List<Integer> GREENXIAO = Lists.newArrayList(5,6,11,16,17,21,22);
	//鼠
	public static final List<Integer> SHU = getSXBalls("SHU");//Lists.newArrayList(5,17,29,41);
	//牛
	public static final List<Integer> NIU =getSXBalls("NIU");// Lists.newArrayList(4,16,28,40);
	//虎
	public static final List<Integer> HU =getSXBalls("HU");// Lists.newArrayList(3,15,27,39);
	//兔
	public static final List<Integer> TU = getSXBalls("TU");//Lists.newArrayList(2,14,26,38);
	//龍
	public static final List<Integer> LONG = getSXBalls("LONG");//Lists.newArrayList(1,13,25,37,49);
	//蛇
	public static final List<Integer> SHE = getSXBalls("SHE");//Lists.newArrayList(12,24,36,48);
	//嗎
	public static final List<Integer> MA = getSXBalls("MA");//Lists.newArrayList(11,23,35,47);
	//羊
	public static final List<Integer> YANG =getSXBalls("YANG");// Lists.newArrayList(10,22,34,46);
	//猴
	public static final List<Integer> HOU =getSXBalls("HOU");// Lists.newArrayList(9,21,33,45);
	//雞
	public static final List<Integer> JI = getSXBalls("JI");//Lists.newArrayList(8,20,32,44);
	//狗
	public static final List<Integer> GOU = getSXBalls("GOU");//Lists.newArrayList(7,9,31,43);
	//豬
	public static final List<Integer> ZHU = getSXBalls("ZHU");//Lists.newArrayList(6,18,30,42);
	
	// 存放屬性的名稱 對應庫中tb_play_type.type_code
	public static final List<String> SHUXING = Lists.newArrayList("SHU","NIU","HU","TU","LONG","SHE","MA","YANG","HOU","JI","GOU","ZHU");
	// 存放半波的名稱 對應庫中tb_play_type.type_code
	public static final List<String> BANBO = Lists.newArrayList("BLUE_DA","BLUE_DAN","BLUE_SHUANG","BLUE_XIAO","GREEN_DA","GREEN_DAN","GREEN_SHUANG","GREEN_XIAO","RED_DA","RED_DAN","RED_SHUANG","RED_XIAO");
	
	static{
		for(int i=1;i<=49;i++)
		{
			if(DA(i)) DA.add(i);
			if(X(i)) X.add(i);
			if(DAN(i)) DAN.add(i);
			if(S(i)) SHUANG.add(i);
			if(WD(i)) WD.add(i);
			if(WX(i)) WX.add(i);
			if(HSD(i)) HSD.add(i);
			if(HSS(i)) HSS.add(i);
		}

	}
	public static  List<Integer> getMatchList(String type)
	{
		if(type == null || "".equals(type)) return new ArrayList<Integer>();
		
		if("DA".equalsIgnoreCase(type)){
			return DA;
		}else if("X".equalsIgnoreCase(type)){
			return X;
		}else if("XIAO".equalsIgnoreCase(type)){
			return X;
		}else if("DAN".equalsIgnoreCase(type)){
			return DAN;
		}else if("S".equalsIgnoreCase(type)){
			return SHUANG;
		}else if("TWD".equalsIgnoreCase(type)){
			return WD;
		}else if("TWX".equalsIgnoreCase(type)){
			return WX;
		}else if("HSD".equalsIgnoreCase(type)){
			return HSD;
		}else if("HSS".equalsIgnoreCase(type)){
			return HSS;
		}else if("RED_DAN".equalsIgnoreCase(type)){
			return REDDAN;
		}else if("RED_S".equalsIgnoreCase(type)){
			return REDSHUANG;
		}else if("RED_SHUANG".equalsIgnoreCase(type)){
			return REDSHUANG;
		}else if("RED_DA".equalsIgnoreCase(type)){
			return REDDA;
		}else if("RED_XIAO".equalsIgnoreCase(type)){
			return REDXIAO;
		}else if("BLUE_DA".equalsIgnoreCase(type)){
			return BLUEDA;
		}else if("BLUE_DAN".equalsIgnoreCase(type)){
			return BLUEDAN;
		}else if("BLUE_S".equalsIgnoreCase(type)){
			return BLUESHUANG;
		}else if("BLUE_SHUANG".equalsIgnoreCase(type)){
			return BLUESHUANG;
		}else if("BLUE_XIAO".equalsIgnoreCase(type)){
			return BLUEXIAO;
		}else if("GREEN_DA".equalsIgnoreCase(type)){
			return GREENDA;
		}else if("GREEN_DAN".equalsIgnoreCase(type)){
			return GREENDAN;
		}else if("GREEN_S".equalsIgnoreCase(type)){
			return GREENSHUANG;
		}else if("GREEN_SHUANG".equalsIgnoreCase(type)){
			return GREENSHUANG;
		}else if("GREEN_XIAO".equalsIgnoreCase(type)){
			return GREENXIAO;
		}else if("HOU".equalsIgnoreCase(type)){
			return HOU;
		}else if("SHE".equalsIgnoreCase(type)){
			return SHE;
		}else if("ZHU".equalsIgnoreCase(type)){
			return ZHU;
		}else if("NIU".equalsIgnoreCase(type)){
			return NIU;
		}else if("HU".equalsIgnoreCase(type)){
			return HU;
		}else if("TU".equalsIgnoreCase(type)){
			return TU;
		}else if("YANG".equalsIgnoreCase(type)){
			return YANG;
		}else if("JI".equalsIgnoreCase(type)){
			return JI;
		}else if("MA".equalsIgnoreCase(type)){
			return MA;
		}else if("GOU".equalsIgnoreCase(type)){
			return GOU;
		}else if("SHU".equalsIgnoreCase(type)){
			return SHU;
		}else if("LONG".equalsIgnoreCase(type)){
			return LONG;
		}else if("BLUE".equalsIgnoreCase(type)){
			return BLUE;
		}else if("GREEN".equalsIgnoreCase(type)){
			return GREEN;
		}else if("RED".equalsIgnoreCase(type)){
			return RED;
		}else{
			return new ArrayList<Integer>();
		}
		
	}
	
   public static boolean HE(Integer ballNum)
   {
	   if(ballNum.intValue()==49)
		   return true;
	   else 
		   return false;
	   
   }
   public static boolean HE(List<Integer> ballNums)
   {
	   Integer ballNum=ballNums.get(6);
	   if(ballNum.intValue()==49)
		   return true;
	   else 
		   return false;
	   
   }
	public  static boolean  DA(Integer ballNum)
	{
		
		if(!HE(ballNum)&&ballNum>=25)
			return true;			
		else return false;
		
	}
	public  static boolean  X(Integer ballNum)
	{
		
		if(!HE(ballNum)&&ballNum<=24)
			return true;			
		else return false;
		
		
	}
	
	public  static boolean  DAN(Integer ballNum)
	{
		if(!HE(ballNum)&&ballNum.intValue()%2!=0)
			return true;			
		else return false;
		
	}
	public  static boolean  S(Integer ballNum)
	{
		
		if(!HE(ballNum)&&ballNum.intValue()%2==0)
			return true;			
		else return false;
		
	}
	public  static boolean  WD(Integer ballNum)
	{
		if(HE(ballNum))
			return false;
		Integer ws=ballNum%10;
		if(ws>=5)
		   return true;
		else return false;
		
	}
	public  static boolean  WX(Integer ballNum)
	{
		if(HE(ballNum))
			return false;
		Integer ws=ballNum%10;
		if(ws<=4)
		   return true;
		else return false;
		 
		
	}
	
	public  static boolean  HSD(Integer ballNum)
	{
		if(HE(ballNum))
			return false;
		if(ballNum <10) 
    	{
    		if (ballNum.intValue() % 2 != 0)
    			return true;
    	}
    	if(ballNum >= 10)
    	{
    		Integer gw=ballNum%10;
    		Integer sw = (ballNum/10);
    		if((gw+sw)%2!=0)
    			return true;
    		
    	}
			return false;
		
	}
	
	public  static boolean  HSS(Integer ballNum)
	{
		if(HE(ballNum))
			return false;
		return !HSD(ballNum);
		
	}
	
	public  static boolean  RED(Integer ballNum)
	{
		
			
		if(ConstantType.RED.contains(ballNum))
		   return true;
		   else return false;
		
	}
	
	public  static boolean  GREEN(Integer ballNum)
	{
		
		if(ConstantType.GREEN.contains(ballNum))
			   return true;
			   else return false;
		
	}
	
	public  static boolean  BLUE(Integer ballNum)
	{
		
		if(ConstantType.BLUE.contains(ballNum))
			   return true;
			   else return false;
		
	}
	
	public  static boolean  ZHDA(List<Integer> winNums)
	{
		
		  Integer sum=0;
			for (int i = 0; i < winNums.size(); i++) {
				sum=sum+winNums.get(i);
		}
			
				if(sum>=175)
			   return true;
				else return false;
		
	}
	
	public  static boolean  ZHX(List<Integer> winNums)
	{
		
		  Integer sum=0;
			for (int i = 0; i < winNums.size(); i++) {
				sum=sum+winNums.get(i);
		}
			
				if(sum<=174)
			   return true;
				else return false;
		
	}
	public  static boolean  ZHDAN(List<Integer> winNums)
	{
		
		  Integer sum=0;
			for (int i = 0; i < winNums.size(); i++) {
				sum=sum+winNums.get(i);
		}
			
				if(sum%2!=0)
			   return true;
				else return false;
		
	}
	
	public  static boolean  ZHS(List<Integer> winNums)
	{
		  Integer sum=0;
			for (int i = 0; i < winNums.size(); i++) {
				sum=sum+winNums.get(i);
		}
			
			if(sum%2==0)
			   return true;
				else return false;
		
	}
	
	public  static boolean  SX(List<Integer> winNums,String sx)
	{
		List<Integer> balls=getSXBalls(sx);
		return !Collections.disjoint(winNums,balls) ;
		
	}
	/**
	 * 傳入好球  返回屬於半波數組,只能返回兩種半波
	 * @return
	 */
	public  static String[] BANBO(Integer ballNum)
	{
		String[] str=new String[2];
		if(BLUE.contains(ballNum))
		{
			if(DA.contains(ballNum))
				str[0] = "BLUE_DA";
			else str[0] = "BLUE_XIAO";
			if(DAN.contains(ballNum))
				str[1] = "BLUE_DAN";
			else str[1] = "BLUE_SHUANG";
		}else if(GREEN.contains(ballNum))
		{
			if(ballNum==49) // 49 號球強制判斷 
			{
				str[0] = "GREEN_DA";
				str[1] = "GREEN_DAN";
			}else{
				if(DA.contains(ballNum))
					str[0] = "GREEN_DA";
				else str[0] = "GREEN_XIAO";
				if(DAN.contains(ballNum))
					str[1] = "GREEN_DAN";
				else str[1] = "GREEN_SHUANG";
			}
		}else if(RED.contains(ballNum))
		{
			if(DA.contains(ballNum))
				str[0] = "RED_DA";
			else str[0] = "RED_XIAO";
			if(DAN.contains(ballNum))
				str[1] = "RED_DAN";
			else str[1] = "RED_SHUANG";
		}
		return str;
	}
	/**
	 * 傳入好球  返回生肖字符串
	 * @return
	 */
	public  static String  SENGXIAO(Integer ballNum)
	{
		if(SHU.contains(ballNum))
			return "SHU";
		else if(NIU.contains(ballNum))
			return "NIU";
		else if(HU.contains(ballNum))
			return "HU";
		else if(TU.contains(ballNum))
			return "TU";
		else if(LONG.contains(ballNum))
			return "LONG";
		else if(SHE.contains(ballNum))
			return "SHE";
		else if(MA.contains(ballNum))
			return "MA";
		else if(YANG.contains(ballNum))
			return "YANG";
		else if(HOU.contains(ballNum))
			return "HOU";
		else if(JI.contains(ballNum))
			return "JI";
		else if(ZHU.contains(ballNum))
			return "ZHU";
		else if(GOU.contains(ballNum))
			return "GOU";
		else{
			return "";
		}
		
	}
	
	
	public  static boolean  WS(List<Integer> winNums,String WS)
	{
		if("W0".equals(WS))
			return !Collections.disjoint(winNums,ConstantType.W0);
		else if("W1".equals(WS))
			return !Collections.disjoint(winNums,ConstantType.W1);
		
		else if("W2".equals(WS))
			return !Collections.disjoint(winNums,ConstantType.W2);
		else if("W3".equals(WS))
			return !Collections.disjoint(winNums,ConstantType.W3);
		else if("W4".equals(WS))
			return !Collections.disjoint(winNums,ConstantType.W4);
		else if("W5".equals(WS))
			return !Collections.disjoint(winNums,ConstantType.W5);
		else if("W6".equals(WS))
			return !Collections.disjoint(winNums,ConstantType.W6);
		else if("W7".equals(WS))
			return !Collections.disjoint(winNums,ConstantType.W7);
		else if("W8".equals(WS))
			return !Collections.disjoint(winNums,ConstantType.W8);
		else if("W9".equals(WS))
			return !Collections.disjoint(winNums,ConstantType.W9);
		return false;
		
	}
	
	public static boolean ballBetResult(Integer ballNum,String betType)
	{
		if(GenericValidator.isInt(betType))
		{
			if(Integer.valueOf(betType).intValue()==ballNum.intValue())
				return true;
			else 
				return false;
			
		}
		else if("DA".equals(betType))
		{
			return DA(ballNum);
		}
		else if("X".equals(betType))
		{
			return X(ballNum);
		}
		else if("DAN".equals(betType))
		{
			return DAN(ballNum);
		}
		else if("S".equals(betType))
		{
			return S(ballNum);
		}
		else if("HSD".equals(betType))
		{
			return HSD(ballNum);
		}
		else if("HSS".equals(betType))
		{
			return HSS(ballNum);
		}
		else if("RED".equals(betType))
		{
			return RED(ballNum);
		}
		else if("BLUE".equals(betType))
		{
			return BLUE(ballNum);
		}
		else if("GREEN".equals(betType))
		{
			return GREEN(ballNum);
		}
		
		else if("TWD".equals(betType))
		{
			return WD(ballNum);
		}
		else if("TWX".equals(betType))
		{
			return WX(ballNum);
		}
		else if(SHUXING.contains(betType))
		{
			List<Integer> balls=getSXBalls(betType);
			if(balls.contains(ballNum))
				return true;
			else return false;
			
		}
		
	
		return false;
	}
	
	public static boolean allBallBetResult(List<Integer> winNums,String betType)
	{
		
		if(GenericValidator.isInt(betType))
		{
			if(winNums.contains(Integer.valueOf(betType)))
				return true;
			else 
				return false;
			
		}
		else if("ZHDA".equals(betType))
		{
			return ZHDA(winNums);
		}
		else if("ZHX".equals(betType))
		{
			return ZHX(winNums);
		}
		else if("ZHDAN".equals(betType))
		{
			return ZHDAN(winNums);
		}
		else if("ZHS".equals(betType))
		{
			return ZHS(winNums);
		}
		else if (ConstantType.SXList.contains(betType))
		{
			return SX(winNums,betType);
			
		}
		else if (ConstantType.WSList.contains(betType))
		{
			return WS(winNums,betType);
			
		}
		
		
		
		return false;
	}
	
	
   public static boolean BB(List<Integer> winNums,String betType)
   {
	  
	   List<Integer> winNumTM=Lists.newArrayList(winNums.get(6));
	   if("BLUE_DA".equals(betType))
	   {	   
		  return !Collections.disjoint(winNumTM,ConstantType.BDA) ;
	   }
	   else if("BLUE_XIAO".equals(betType))
	   {
		   return !Collections.disjoint(winNumTM,ConstantType.BX) ;
	   }
	   else if("BLUE_DAN".equals(betType))
	   {
		   return !Collections.disjoint(winNumTM,ConstantType.BDAN) ;
	   }
	   else  if("BLUE_S".equals(betType))
	   {
		   return !Collections.disjoint(winNumTM,ConstantType.BS) ;
	   }
	   else  if("RED_DA".equals(betType))
	   {
		   return !Collections.disjoint(winNumTM,ConstantType.RDA) ; 
	   }
	   else  if("RED_XIAO".equals(betType))
	   {
		   return !Collections.disjoint(winNumTM,ConstantType.RX) ; 
	   }
	   else  if("RED_DAN".equals(betType))
	   {
		   return !Collections.disjoint(winNumTM,ConstantType.RDAN) ; 
	   }
	   else  if("RED_S".equals(betType))
	   {
		   return !Collections.disjoint(winNumTM,ConstantType.RS) ; 
	   }
	   
	   else if("GREEN_DA".equals(betType))
	   {
		   return !Collections.disjoint(winNumTM,ConstantType.GDA) ; 
	   }
	   else if("GREEN_XIAO".equals(betType))
	   {
		   return !Collections.disjoint(winNumTM,ConstantType.GX) ; 
	   }
	   else  if("GREEN_DAN".equals(betType))
	   {
		   return !Collections.disjoint(winNumTM,ConstantType.GDAN) ; 
	   }
	   else  if("GREEN_S".equals(betType))
	   {
		   return !Collections.disjoint(winNumTM,ConstantType.GS) ; 
	   }
	   
	   return false;
   }
	
	
	
	
	/*
	 * 直接通过扫描临时表 然后跟投注表管理update
	 */
	public static boolean getBetResult(PlayType type,List<Integer> winNums)
	{
		
		String playSubType=type.getPlaySubType();
		String playFinalType=type.getPlayFinalType();
		Integer ballNum=null;
		boolean betResult=false;
		if("TMA".equals(playSubType)||"TMB".equals(playSubType)||"TM".equals(playSubType))
		{
			ballNum=winNums.get(6);
			betResult=ballBetResult(ballNum,playFinalType);
		}
		else if("ZM".equals(playSubType))
		{
			if("ZHDA".equals(playFinalType)||"ZHX".equals(playFinalType)||"ZHDAN".equals(playFinalType)||"ZHS".equals(playFinalType))
			   betResult=allBallBetResult( winNums,playFinalType);
			else
			{
				winNums=winNums.subList(0, 6);
				betResult=allBallBetResult( winNums,playFinalType);
			}
		}
		else if("ZM6".equals(playSubType)||"ZM5".equals(playSubType)||"ZM4".equals(playSubType)||"ZM3".equals(playSubType)||"ZM2".equals(playSubType)||"ZM1".equals(playSubType))
		{
			String ball=playSubType.substring(2, 3);
			ballNum=winNums.get(Integer.parseInt(ball)-1);
			betResult=ballBetResult(ballNum,playFinalType);
		}
		else if("Z1T".equals(playSubType)||"Z2T".equals(playSubType)||"Z3T".equals(playSubType)||"Z4T".equals(playSubType)||"Z5T".equals(playSubType)||"Z6T".equals(playSubType))
		{
			String ball=playSubType.substring(1, 2);
			ballNum=winNums.get(Integer.parseInt(ball)-1);
			betResult=ballBetResult(ballNum,playFinalType);
		}
		else if("TMSX".equals(playSubType))
		{
			ballNum=winNums.get(6);
			betResult=ballBetResult(ballNum,playFinalType);
		}
		else if("SXWS".equals(playSubType))
		{
			//winNums=winNums.subList(0, 6);
			betResult=allBallBetResult( winNums,playFinalType);
		}
		
		else if("BB".equals(playSubType))
		{
			
			betResult=BB(winNums,playFinalType);
		}

		return betResult;
		
	}
	
	/*
	 * 需要单独表扫描处理的
	 */
	public static String LM(String attr,String playType,List<Integer> winNums)
	{
		String ret="1";
		Integer winCount=0;

		String bet=attr;
		
		
		if("HK_STRAIGHTTHROUGH_3QZ".equals(playType))
        {           	
        	
			ret="0";
			  winNums=winNums.subList(0, 6);
	        	List<String> combList=	Tool.assembly(Lists.newArrayList(bet.split("\\|")), 3);
	        	for (int i = 0; i < combList.size(); i++) {
	        		String[] oneBet = StringUtils.split(combList.get(i),"\\,");
	        		int pos1=winNums.indexOf(Integer.valueOf(oneBet[0]));
					int pos2=winNums.indexOf(Integer.valueOf(oneBet[1]));
					int pos3=winNums.indexOf(Integer.valueOf(oneBet[2]));
					if (pos1!=-1 && pos2!=-1&& pos3!=-1) {
						winCount++;
					}
	        		
					
				}
	        	ret=winCount.toString();
 
    		
        	
        		
        }
        else if("HK_STRAIGHTTHROUGH_3Z2".equals(playType))
        {
        	ret="0";
        	winNums=winNums.subList(0, 6);
        		Integer z3=0;
        		Integer z2=0;
        		List<String> combList=	Tool.assembly(Lists.newArrayList(bet.split("\\|")), 3);
	        	for (int i = 0; i < combList.size(); i++) {
	        		String[] oneBet = StringUtils.split(combList.get(i),"\\,");
	        		int pos1=winNums.indexOf(Integer.valueOf(oneBet[0]));
					int pos2=winNums.indexOf(Integer.valueOf(oneBet[1]));
					int pos3=winNums.indexOf(Integer.valueOf(oneBet[2]));
					if(pos1!=-1&&pos2!=-1&&pos3!=-1)
						z3++;
					else if ((pos1!=-1 && pos2!=-1)||(pos1!=-1 && pos3!=-1)||(pos2!=-1 && pos3!=-1)) {
						z2++;
					}
	        		
					
				}
	        	ret=z2.toString()+"|"+z3.toString();
	        	
    		
    
        	
        }
        else if("HK_STRAIGHTTHROUGH_2QZ".equals(playType))
        {    
        	ret="0";
        	winNums=winNums.subList(0, 6);		
        		List<String> combList=	Tool.assembly(Lists.newArrayList(bet.split("\\|")), 2);
        	 	for (int i = 0; i < combList.size(); i++) {
	        		String[] oneBet = StringUtils.split(combList.get(i),"\\,");
	        		int pos1=winNums.indexOf(Integer.valueOf(oneBet[0]));
					int pos2=winNums.indexOf(Integer.valueOf(oneBet[1]));

					if (pos1!=-1 && pos2!=-1) {
						winCount++;
					}	        		
					
				}
        	 	ret=winCount.toString();
    	
    	
        	
        }
        else if("HK_STRAIGHTTHROUGH_2ZT".equals(playType))
        {        	
        	ret="0";
        	Integer te=winNums.get(6);
        	Integer zt=0;
    		Integer z2=0;
        	
        		List<String> combList=	Tool.assembly(Lists.newArrayList(bet.split("\\|")), 2);
	        	for (int i = 0; i < combList.size(); i++) {
	        		String[] oneBet = StringUtils.split(combList.get(i),"\\,");
	        		int pos1=winNums.indexOf(Integer.valueOf(oneBet[0]));
					int pos2=winNums.indexOf(Integer.valueOf(oneBet[1]));
					if(pos1!=-1&&pos2!=-1&&(Integer.valueOf(oneBet[0]).intValue()==te.intValue()||Integer.valueOf(oneBet[1]).intValue()==te.intValue()))
						zt++;
					else if ((pos1!=-1 && pos2!=-1)) {
						z2++;
					}
	        		
					
				}	
	        	ret=zt.toString()+"|"+z2.toString();
    	
    	
        	
        }
        else if("HK_STRAIGHTTHROUGH_TC".equals(playType))
        {        	
        	ret="0";
        	Integer te=winNums.get(6);
        
        		List<String> combList=	Tool.assembly(Lists.newArrayList(bet.split("\\|")), 2);
        	 	for (int i = 0; i < combList.size(); i++) {
	        		String[] oneBet = StringUtils.split(combList.get(i),"\\,");
	        		int pos1=winNums.indexOf(Integer.valueOf(oneBet[0]));
	        		int pos2=winNums.indexOf(Integer.valueOf(oneBet[1]));
					if (pos1!=-1 &&pos2!=-1&&( Integer.valueOf(oneBet[1]).intValue()==te.intValue()||Integer.valueOf(oneBet[0]).intValue()==te.intValue())) {
						winCount++;
					}	        		
					
				}
        	 	ret=winCount.toString();
    		
    	
        	
        }
       
		
		
		
		return ret;
		
	}
	
	
	
	public static boolean LX(String attr,List<Integer> winNums)
	{
		
		
		if(HE(winNums))
			return false;
		Integer ballNum=winNums.get(6);
		boolean ret=false;
		String[] sxs=attr.split("\\|");
		for (int i = 0; i < sxs.length; i++) {
			
			List<Integer> sxBalls=getSXBalls(sxs[i]);

			if(sxBalls.contains(ballNum))
			{
				ret=true;
				break;
			}
					
			
		}
		
		
		return ret;
		
	}
	
	
	
	public static int SXL(String attr,String playType,List<Integer> winNums)
	{
			
		int winCount=0;
		String[] danAndSX=StringUtils.split(attr,"\\&");
		String[] dan=null;
		String[] sx=null;
		if(danAndSX.length==2)
		{
			dan=StringUtils.split(danAndSX[0],"\\|");
			sx=StringUtils.split(danAndSX[1],"\\|");
		
		}
		else if(danAndSX.length==1)
		{
			sx=StringUtils.split(danAndSX[0],"\\|");
		}
		int number=2;
		if("HK_SXL_2XL".equals(playType))
        {           	
        	number=2;     	
        		
        }
        else if("HK_SXL_3XL".equals(playType))
        {

        	number=3;
        	
        }
        else if("HK_SXL_4XL".equals(playType))
        {        	
        	number=4;
        	
        }
		int danSize=0;
		if(dan!=null)
			danSize=dan.length;			
   
	
		
	List<String>	assemblyList=Tool.assembly(Lists.newArrayList(sx),number-danSize); 
	
	if(dan!=null)
	{
		for (int i = 0; i < dan.length; i++) {
			String sxdan=dan[i];
			boolean hasDan=SX(winNums,sxdan);
			if(!hasDan)
			{
				winCount=0;
				return winCount;
			}
			
		}
		
	}
	for (int i = 0; i < assemblyList.size(); i++) {
		String[] oneBet = StringUtils.split(assemblyList.get(i),"\\,");
		boolean oneWin=true;
		for (int j = 0; j < oneBet.length; j++) {
			if(!SX(winNums,oneBet[j]))
				oneWin=false;
			
		}
		if(oneWin)
			winCount++;
	
		
	}
		return winCount;
		
	}
	
	public static int WSL(String attr,String playType,List<Integer> winNums)
	{
       
        int winCount=0;
		String[] danAndWS=StringUtils.split(attr,"\\&");
		String[] dan=null;
		String[] ws=null;
		if(danAndWS.length==2)
		{
			dan=StringUtils.split(danAndWS[0],"\\|");
			ws=StringUtils.split(danAndWS[1],"\\|");
		
		}
		else if(danAndWS.length==1)
		{
				ws=StringUtils.split(danAndWS[0],"\\|");
		}
		int number=2;
		if("HK_WSL_2WL".equals(playType))
        {           	
        	number=2;     	
        		
        }
        else if("HK_WSL_3WL".equals(playType))
        {

        	number=3;
        	
        }
        else if("HK_WSL_4WL".equals(playType))
        {        	
        	number=4;
        	
        }
		int danSize=0;
		if(dan!=null)
			danSize=dan.length;			
   
	
		
	List<String>	assemblyList=Tool.assembly(Lists.newArrayList(ws),number-danSize); 
	
	if(dan!=null)
	{
		for (int i = 0; i < dan.length; i++) {
			String wsdan=dan[i];
			boolean hasDan=WS(winNums,wsdan);
			if(!hasDan)
			{
				winCount=0;
				return winCount;
			}
			
		}
		
	}
	for (int i = 0; i < assemblyList.size(); i++) {
		String[] oneBet = StringUtils.split(assemblyList.get(i),"\\,");
		boolean oneWin=true;
		for (int j = 0; j < oneBet.length; j++) {
			if(!WS(winNums,oneBet[j]))
				oneWin=false;
			
		}
		if(oneWin)
			winCount++;
	
		
	}
		return winCount;
		
	}
	
	public static int WBZ(String attr,List<Integer> winNums)
	{
		
		
		List<String> attrList = Lists.newArrayList(StringUtils.split(attr,"\\|"));
		if(attrList.size()<5) return 0;
		int winCount = 0;
		List<String> comList = Tool.assembly(attrList, 5);
		for (int i = 0; i < comList.size(); i++) {
			String[] oneBet = StringUtils.split(comList.get(i),"\\,");
			int pos1=winNums.indexOf(Integer.valueOf(oneBet[0]));
			int pos2=winNums.indexOf(Integer.valueOf(oneBet[1]));
			int pos3=winNums.indexOf(Integer.valueOf(oneBet[2]));
			int pos4=winNums.indexOf(Integer.valueOf(oneBet[3]));
			int pos5=winNums.indexOf(Integer.valueOf(oneBet[4]));
			if (pos1==-1 && pos2==-1&& pos3==-1&& pos4==-1&& pos5==-1) {
				winCount++;
			}

		}
		
		
		return winCount;
		
	}
	
	public static boolean GG(String attr,List<Integer> winNums)
	{
		boolean ret=true;
		String[] guan=StringUtils.split(attr,"\\|");
		
		for (int i = 0; i < guan.length; i++) {
			
			String maNum=guan[i].substring(2,3);
			String guanDetail=guan[i].substring(4,guan[i].length());
			if(GenericValidator.isInt(maNum)&&Integer.valueOf(maNum)>0)			
			{	
				Integer winNum=winNums.get(Integer.valueOf(maNum)-1);
			    
				 if("DAN".equals(guanDetail))
				{
					 if(!DAN(winNum)&&!HE(winNum))
					 {
					   ret=false;break;
					 }
				}
				 else if("S".equals(guanDetail))
				{
					 if(!S(winNum)&&!HE(winNum))
					 {
						ret=false;break;
					 }
				}
				 else if("DA".equals(guanDetail))
				{
					if(!DA(winNum)&&!HE(winNum))
					{
					 ret=false;break;
					}
				}
				else if("X".equals(guanDetail))
				{
					if(!X(winNum)&&!HE(winNum))
					{
					ret=false;break;
					}
				}
				
				
				else if("RED".equals(guanDetail))
				{
					if(!RED(winNum))
					{
					ret=false;break;
					}
				}
				else if("GREEN".equals(guanDetail))
				{
					if(!GREEN(winNum))
					{
					ret=false;break;
					}
				}
				else if("BLUE".equals(guanDetail))
				{
					if(!BLUE(winNum))
					{
					ret=false;break;
					}
				}
				else 
				{
					ret=false;break;
				}
					
				
			}
			else
				ret=false;
		}
	
		
		return ret;
		
	}
	
	
	
	
	
	public static void main(String[] args) {
		

		List winNums=Lists.newArrayList(26,27,28,29,30,31,49);
    	String typeCode="HK_GG";
    	String subType="ZM";
    	String finnalType="ZHS";
    	PlayType pt=new PlayType();   
    	pt.setTypeCode(typeCode);
    	pt.setPlaySubType(subType);
    	pt.setPlayFinalType(finnalType);
    	System.out.println(HKLHCRule.GG("ZM1_S|ZM2_DAN|ZM3_S|ZM4_DAN|ZM5_S|ZM6_DAN", winNums));
    	
    	//System.out.println(HKLHCRule.LM("1|2", "HK_STRAIGHTTHROUGH_TC", winNums));
    	
/*    	System.out.println(getBetResult(pt, winNum));
    	System.out.println(getThisYearSX());
    	System.out.println(getHKLMMaxWinCount(4));*/
    	
    	
    		
	
	
	}
	
	public static List<Integer> getWSBalls(String ws)
	{
		List<Integer> wsBalls=new ArrayList<Integer>();
		int w=0;
		if("W0".equals(ws))
			w=0;
		else if("W1".equals(ws))
			w=1;
		
		else if("W2".equals(ws))
			w=2;
		else if("W3".equals(ws))
			w=3;
		else if("W4".equals(ws))
			w=4;
		else if("W5".equals(ws))
			w=5;
		else if("W6".equals(ws))
			w=6;
		else if("W7".equals(ws))
			w=7;
		else if("W8".equals(ws))
			w=8;
		else if("W9".equals(ws))
			w=9;
		for (int i = 1; i <=49; i++) {
			if(i<10&&i==w)
				wsBalls.add(Integer.valueOf(i));
			else if(i>=10&&i%10==w)
			{
				wsBalls.add(Integer.valueOf(i));
				
			}
			
		}
		
		return wsBalls;
		
	}
	
	
	
	public static String getThisYearSX()
	{
		String year=DateFormatUtils.format(new Date(), "yyyy");
		
		 String thisYearSX=ConstantType.SXList.get((Integer.valueOf(year) - 4) % 12);
		return thisYearSX;
		
		
	}
	
    public static List<Integer> getSXBalls(String sx) {  
    	
     
     
      String thisYearSX=getThisYearSX();
      List<Integer> ret=Lists.newArrayList();
      Integer beginNum=0;
      int size=4;
      if(thisYearSX.equals(sx))
      {
    	  beginNum=1;
    	  size=5;
    	   	  
      }
      else
      {
	      int sxPostion=ConstantType.SXList.indexOf(sx);
	      int thisYearSXPos=ConstantType.SXList.indexOf(thisYearSX);
	      int distince=thisYearSXPos-sxPostion;
	      if(distince<0)
	    	  distince=distince+12;
	      beginNum=1+distince;
	      size=4;
      }
      for(int i=0;i<size;i++)
      {
    	  ret.add(beginNum);
    	  beginNum=beginNum+12;
      }
        return ret; 
    }  
    
    
    public static int getHKLMMaxWinCount(int comSize)
    {
    	return Tool.assembly(Lists.newArrayList("1","2","3","4","5","6"),comSize).size();
    	
    }
    public static int getSXLMaxWinCount(int comSize)
    {
    	return Tool.assembly(Lists.newArrayList("1","2","3","4","5","6","7"),comSize).size();
    	
    }
    public static int getWSLMaxWinCount(int comSize)
    {
    	return Tool.assembly(Lists.newArrayList("1","2","3","4","5","6","7"),comSize).size();
    	
    }
    public static int getWBZMaxWinCount(int comSize)
    {
    	return Tool.assembly(Lists.newArrayList("1","2","3","4","5","6","7"),comSize).size();
    	
    }
   
}
