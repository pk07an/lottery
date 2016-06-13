package com.npc.lottery.rule;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;

import com.google.common.collect.Lists;
import com.npc.lottery.member.entity.PlayType;
import com.npc.lottery.util.Tool;

public class GDKLSFRule {

	private static List<Integer> DONG = new ArrayList<Integer>();
	private static List<Integer> NAN = new ArrayList<Integer>();
	private static List<Integer> XI = new ArrayList<Integer>();
	private static List<Integer> BEI = new ArrayList<Integer>();
	private static List<Integer> ZHONG = new ArrayList<Integer>();
	private static List<Integer> FA = new ArrayList<Integer>();
	private static List<Integer> BAI = new ArrayList<Integer>();
	
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
	// 龍
	public static final List<Integer> LONG = new ArrayList<Integer>();
	// 虎
	public static final List<Integer> HU = new ArrayList<Integer>();
		
	static {

		DONG.add(new Integer(1));
		DONG.add(new Integer(5));
		DONG.add(new Integer(9));
		DONG.add(new Integer(13));
		DONG.add(new Integer(17));
		NAN.add(new Integer(2));
		NAN.add(new Integer(6));
		NAN.add(new Integer(10));
		NAN.add(new Integer(14));
		NAN.add(new Integer(18));

		XI.add(new Integer(3));
		XI.add(new Integer(7));
		XI.add(new Integer(11));
		XI.add(new Integer(15));
		XI.add(new Integer(19));

		BEI.add(new Integer(4));
		BEI.add(new Integer(8));
		BEI.add(new Integer(12));
		BEI.add(new Integer(16));
		BEI.add(new Integer(20));

		ZHONG.add(new Integer(1));
		ZHONG.add(new Integer(2));
		ZHONG.add(new Integer(3));
		ZHONG.add(new Integer(4));
		ZHONG.add(new Integer(5));
		ZHONG.add(new Integer(6));
		ZHONG.add(new Integer(7));

		FA.add(new Integer(8));
		FA.add(new Integer(9));
		FA.add(new Integer(10));
		FA.add(new Integer(11));
		FA.add(new Integer(12));
		FA.add(new Integer(13));
		FA.add(new Integer(14));

		BAI.add(new Integer(15));
		BAI.add(new Integer(16));
		BAI.add(new Integer(17));
		BAI.add(new Integer(18));
		BAI.add(new Integer(19));
		BAI.add(new Integer(20));
		
		for(int i=1;i<=20;i++)
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
	public static Map<Integer,String> getNumberMatchEN()
	{
		Map<Integer,String> map = new HashMap<Integer,String>();
		map.put(1,"FIRST");
		map.put(2,"SECOND");
		map.put(3,"THIRD");
		map.put(4,"FORTH");
		map.put(5,"FIFTH");
		map.put(6,"SIXTH");
		map.put(7,"SEVENTH");
		map.put(8,"EIGHTH");
		return map;
	}
	public static Map<String,String> getENMatchNumber()
	{
		Map<String,String> map = new HashMap<String,String>();
		map.put("FIRST","1");
		map.put("SECOND","2");
		map.put("THIRD","3");
		map.put("FORTH","4");
		map.put("FIFTH","5");
		map.put("SIXTH","6");
		map.put("SEVENTH","7");
		map.put("EIGHTH","8");
		return map;
	}
	public static  List<Integer> getMatchList(String type)
	{
		if(type == null || "".equals(type)) return new ArrayList<Integer>();
		
		if("DA".equalsIgnoreCase(type)){
			return DA;
		}else if("X".equalsIgnoreCase(type)){
			return X;
		}else if("DAN".equalsIgnoreCase(type)){
			return DAN;
		}else if("S".equalsIgnoreCase(type)){
			return SHUANG;
		}else if("WD".equalsIgnoreCase(type)){
			return WD;
		}else if("WX".equalsIgnoreCase(type)){
			return WX;
		}else if("HSD".equalsIgnoreCase(type)){
			return HSD;
		}else if("HSS".equalsIgnoreCase(type)){
			return HSS;
		}else{
			return new ArrayList<Integer>();
		}
		
	}
	public static boolean DA(Integer ballNum) {
		if (ballNum >= 11)
			return true;
		else
			return false;

	}

	public static boolean X(Integer ballNum) {

		if (ballNum <= 10)
			return true;
		else
			return false;

	}

	public static boolean DAN(Integer ballNum) {
		if (ballNum % 2 != 0)
			return true;
		else
			return false;

	}

	public static boolean S(Integer ballNum) {

		if (ballNum % 2 == 0)
			return true;
		else
			return false;

	}

	public static boolean WD(Integer ballNum) {
		Integer ws = ballNum % 10;
		if (ws >= 5)
			return true;
		else
			return false;

	}

	public static boolean WX(Integer ballNum) {

		Integer ws = ballNum % 10;
		if (ws <= 4)
			return true;
		else
			return false;

	}

	public static boolean HSD(Integer ballNum) {
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

	public static boolean HSS(Integer ballNum) {

		Integer gw = ballNum % 10;
		Integer sw = (ballNum / 10) % 10;
		if ((gw + sw) % 2 == 0)
			return true;
		else
			return false;

	}

	public static boolean DONG(Integer ballNum) {

		if (DONG.contains(ballNum))
			return true;
		else
			return false;

	}

	public static boolean NAN(Integer ballNum) {

		if (NAN.contains(ballNum))
			return true;
		else
			return false;

	}

	public static boolean XI(Integer ballNum) {

		if (XI.contains(ballNum))
			return true;
		else
			return false;

	}

	public static boolean BEI(Integer ballNum) {

		if (BEI.contains(ballNum))
			return true;
		else
			return false;

	}

	public static boolean Z(Integer ballNum) {

		if (ZHONG.contains(ballNum))
			return true;
		else
			return false;

	}

	public static boolean F(Integer ballNum) {

		if (FA.contains(ballNum))
			return true;
		else
			return false;

	}

	public static boolean B(Integer ballNum) {
		if (BAI.contains(ballNum))
			return true;
		else
			return false;

	}

	public static boolean ballBetResult(Integer ballNum, String betType) {
		if (GenericValidator.isInt(betType)) {
			if (Integer.valueOf(betType).intValue() == ballNum.intValue())
				return true;
			else
				return false;

		} else if ("DONG".equals(betType)) {
			return DONG(ballNum);
		} else if ("NAN".equals(betType)) {
			return NAN(ballNum);
		} else if ("XI".equals(betType)) {
			return XI(ballNum);
		} else if ("BEI".equals(betType)) {
			return BEI(ballNum);
		} else if ("Z".equals(betType)) {
			return Z(ballNum);
		} else if ("F".equals(betType)) {
			return F(ballNum);
		} else if ("B".equals(betType)) {
			return B(ballNum);
		} else if ("DA".equals(betType)) {
			return DA(ballNum);
		} else if ("X".equals(betType)) {
			return X(ballNum);
		} else if ("DAN".equals(betType)) {
			return DAN(ballNum);
		} else if ("S".equals(betType)) {
			return S(ballNum);
		} else if ("WD".equals(betType)) {
			return WD(ballNum);
		} else if ("WX".equals(betType)) {
			return WX(ballNum);
		} else if ("HSD".equals(betType)) {
			return HSD(ballNum);
		} else if ("HSS".equals(betType)) {
			return HSS(ballNum);
		}
		return false;
	}

	public static boolean allBallBetResult(List<Integer> winNums, String betType) {

		if ("ZHDA".equals(betType)) {
			return ZHDA(winNums);
		} else if ("ZHX".equals(betType)) {
			return ZHX(winNums);
		} else if ("ZHDAN".equals(betType)) {
			return ZHDAN(winNums);
		} else if ("ZHS".equals(betType)) {
			return ZHS(winNums);
		} else if ("ZHWD".equals(betType)) {
			return ZHWD(winNums);
		} else if ("ZHWX".equals(betType)) {
			return ZHWX(winNums);
		} else if ("LONG".equals(betType)) {
			return LONG(winNums);
		} else if ("HU".equals(betType)) {
			return HU(winNums);
		}

		return false;
	}

	public static boolean HE(List<Integer> winNums)
	{
		
		Integer sum = 0;
		for (int i = 0; i < winNums.size(); i++) {
			sum = sum + winNums.get(i);
		}

		if (sum .intValue()==84)
			return true;
		else 
			return false;
		
	}
	
	
	public static boolean ZHDA(List<Integer> winNums) {
		Integer sum = 0;
		for (int i = 0; i < winNums.size(); i++) {
			sum = sum + winNums.get(i);
		}

		if (sum >= 85 && sum <= 132)
			return true;
		else
			return false;

	}

	public static boolean ZHX(List<Integer> winNums) {
		Integer sum = 0;
		for (int i = 0; i < winNums.size(); i++) {
			sum = sum + winNums.get(i);
		}

		if (sum >= 36 && sum <= 83)
			return true;
		else
			return false;

	}

	public static boolean ZHDAN(List<Integer> winNums) {

		Integer sum = 0;
		for (int i = 0; i < winNums.size(); i++) {
			sum = sum + winNums.get(i);
		}

		if (sum % 2 != 0)
			return true;
		else
			return false;

	}

	public static boolean ZHS(List<Integer> winNums) {

		Integer sum = 0;
		for (int i = 0; i < winNums.size(); i++) {
			sum = sum + winNums.get(i);
		}

		if (sum % 2 == 0)
			return true;
		else
			return false;

	}

	public static boolean ZHWD(List<Integer> winNums) {
		Integer sum = 0;
		for (int i = 0; i < winNums.size(); i++) {
			sum = sum + winNums.get(i);
		}

		if (sum % 10 >= 5)
			return true;
		else
			return false;
	}

	public static boolean ZHWX(List<Integer> winNums) {

		Integer sum = 0;
		for (int i = 0; i < winNums.size(); i++) {
			sum = sum + winNums.get(i);
		}

		if (sum % 10 <= 4)
			return true;
		else
			return false;

	}

	public static boolean LONG(List<Integer> winNums) {
		Integer first = winNums.get(0);
		Integer eighth = winNums.get(7);
		if (first > eighth)
			return true;
		else
			return false;

	}

	public static boolean HU(List<Integer> winNums) {
		Integer first = winNums.get(0);
		Integer eighth = winNums.get(7);
		if (first < eighth)
			return true;
		else
			return false;
	}

	public static boolean getBetResult(PlayType type, List<Integer> winNums) {

		String playSubType = type.getPlaySubType();
		String playFinalType = type.getPlayFinalType();
		Integer ballNum = 0;
		boolean betResult = false;
		if ("BALL_FIRST".equals(playSubType)) {
			ballNum = winNums.get(0);
			betResult = ballBetResult(ballNum, playFinalType);
		}
		if ("BALL_SECOND".equals(playSubType)) {
			ballNum = winNums.get(1);
			betResult = ballBetResult(ballNum, playFinalType);
		} else if ("BALL_THIRD".equals(playSubType)) {
			ballNum = winNums.get(2);
			betResult = ballBetResult(ballNum, playFinalType);
		} else if ("BALL_FORTH".equals(playSubType)) {
			ballNum = winNums.get(3);
			betResult = ballBetResult(ballNum, playFinalType);
		} else if ("BALL_FIFTH".equals(playSubType)) {
			ballNum = winNums.get(4);
			betResult = ballBetResult(ballNum, playFinalType);
		} else if ("BALL_SIXTH".equals(playSubType)) {
			ballNum = winNums.get(5);
			betResult = ballBetResult(ballNum, playFinalType);
		} else if ("BALL_SEVENTH".equals(playSubType)) {
			ballNum = winNums.get(6);
			betResult = ballBetResult(ballNum, playFinalType);
		} else if ("BALL_EIGHTH".equals(playSubType)) {
			ballNum = winNums.get(7);
			betResult = ballBetResult(ballNum, playFinalType);
		} else if ("DOUBLESIDE".equals(playSubType)) {
			betResult = allBallBetResult(winNums, playFinalType);
		}

		return betResult;

	}

	public static int straightthrough(String attr, String playTypeCode,
			List<Integer> winNums) {

		
		int winCount = 0;
		
		List<String> attrList = Lists.newArrayList(StringUtils.split(attr,"\\|"));
		if ("GDKLSF_STRAIGHTTHROUGH_R2LZ".equals(playTypeCode)) {
			
			
			
			List<String> comList = Tool.assembly(attrList, 2);
			for (int i = 0; i < comList.size(); i++) {
				String[] oneBet = StringUtils.split(comList.get(i),",");
				int pos1=winNums.indexOf(Integer.valueOf(oneBet[0]));
				int pos2=winNums.indexOf(Integer.valueOf(oneBet[1]));
				if (pos1!=-1 && pos2!=-1&&Math.abs(pos2-pos1)==1) {
					winCount++;
				}

			}
			
			
			
			

		} else if ("GDKLSF_STRAIGHTTHROUGH_R3LZ".equals(playTypeCode)) {
			
			

			
			List<String> comList = Tool.assembly(attrList, 3);
			for (int i = 0; i < comList.size(); i++) {
				String[] oneBet = StringUtils.split(comList.get(i),",");
				int pos1=winNums.indexOf(Integer.valueOf(oneBet[0]));
				int pos2=winNums.indexOf(Integer.valueOf(oneBet[1]));
				int pos3=winNums.indexOf(Integer.valueOf(oneBet[2]));
				if (pos1!=-1 && pos2!=-1&&pos3!=-1&&(pos1+pos2+pos3)==3) {
					winCount++;
				}

			}
			
			
			
			
			

		} else if ("GDKLSF_STRAIGHTTHROUGH_RX2".equals(playTypeCode)) {
			
			List<String> comList = Tool.assembly(attrList, 2);
			for (int i = 0; i < comList.size(); i++) {
				String[] oneBet = StringUtils.split(comList.get(i),",");
				if (winNums.contains(Integer.valueOf(oneBet[0])) && winNums.contains(Integer.valueOf(oneBet[1]))) {
					winCount++;
				}

			}
			

		} else if ("GDKLSF_STRAIGHTTHROUGH_RX3".equals(playTypeCode)) {
			
			List<String> comList = Tool.assembly(attrList, 3);
			for (int i = 0; i < comList.size(); i++) {
				
				String[] oneBet = StringUtils.split(comList.get(i),",");
				if (winNums.contains(Integer.valueOf(oneBet[0])) && winNums.contains(Integer.valueOf(oneBet[1]))&& winNums.contains(Integer.valueOf(oneBet[2]))) {
					winCount++;
				}

			}
			

		} else if ("GDKLSF_STRAIGHTTHROUGH_RX4".equals(playTypeCode)) {
			
			
			List<String> comList = Tool.assembly(attrList, 4);
			for (int i = 0; i < comList.size(); i++) {
				String[] oneBet = StringUtils.split(comList.get(i),",");
				if (winNums.contains(Integer.valueOf(oneBet[0])) && winNums.contains(Integer.valueOf(oneBet[1]))&& winNums.contains(Integer.valueOf(oneBet[2]))&& winNums.contains(Integer.valueOf(oneBet[3]))) {
					winCount++;
				}

			}
			

		} else if ("GDKLSF_STRAIGHTTHROUGH_RX5".equals(playTypeCode)) {
			
			
			
			List<String> comList = Tool.assembly(attrList, 5);
			for (int i = 0; i < comList.size(); i++) {
				String[] oneBet = StringUtils.split(comList.get(i),",");
				if (winNums.contains(Integer.valueOf(oneBet[0])) && winNums.contains(Integer.valueOf(oneBet[1]))&& winNums.contains(Integer.valueOf(oneBet[2]))&& winNums.contains(Integer.valueOf(oneBet[3]))&& winNums.contains(Integer.valueOf(oneBet[4]))) {
					winCount++;
				}

			}
			

		}
		return winCount;

	}

	public static void main(String[] args) {
		String playType = "GDKLSF_STRAIGHTTHROUGH_RX2";
		String attr = "4|16";
		List<Integer> balls = Lists.newArrayList(3, 4, 16, 11, 18, 8,15,0);
		long start=System.currentTimeMillis();
		WD(balls.get(0));
		 long end=System.currentTimeMillis();
		 System.out.println("開銷>>"+(end-start));
		//System.out.println(balls.indexOf(Inte));
		System.out.println(straightthrough(attr, playType, balls));

		// List<String>
		// attrList=Lists.newArrayList(StringUtils.split(attr,"\\|"));
		// System.out.println(attrList);
		/*
		 * Integer ballNum=2235; Integer gw=ballNum%10; Integer sw =
		 * (ballNum/10)%10; Date lotteryTime=new Date(); SimpleDateFormat sm=new
		 * SimpleDateFormat("MM-dd E H:m"); String lotteryStrTime=
		 * sm.format(lotteryTime); System.out.println(gw+">>>>"+lotteryStrTime);
		 */
		Integer a=new Integer(15);
		System.out.println(HSD(a));
	}
	
	public static int getGDLMMaxWinCount(int comSize)
    {
    	return Tool.assembly(Lists.newArrayList("1","2","3","4","5","6","7"),comSize).size();
    	
    }

}
