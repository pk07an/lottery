package com.npc.lottery.rule;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.validator.GenericValidator;
import com.npc.lottery.member.entity.PlayType;
public class BJSCRule {


	
	// 大
	public static final List<Integer> DA = new ArrayList<Integer>();
	// 小
	public static final List<Integer> X = new ArrayList<Integer>();
	// 單
	public static final List<Integer> DAN = new ArrayList<Integer>();
	// 雙
	public static final List<Integer> SHUANG = new ArrayList<Integer>();
	// 龙
	public static final List<Integer> LONG = new ArrayList<Integer>();
	// 虎
	public static final List<Integer> HU = new ArrayList<Integer>();
	// 冠亚大
	public static final List<Integer> GYDA = new ArrayList<Integer>();
	// 冠亚小
	public static final List<Integer> GYX = new ArrayList<Integer>();
	// 冠亚单
	public static final List<Integer> GYDAN = new ArrayList<Integer>();
	// 冠亚双
	public static final List<Integer> GYS = new ArrayList<Integer>();
		
	static {

		
		for(int i=1;i<=20;i++)
		{
			if(DA(i)) DA.add(i);
			if(X(i)) X.add(i);
			if(DAN(i)) DAN.add(i);
			if(S(i)) SHUANG.add(i);
			/*if(WD(i)) WD.add(i);
			if(WX(i)) WX.add(i);
			if(HSD(i)) HSD.add(i);
			if(HSS(i)) HSS.add(i);*/
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
		}/*else if("WD".equalsIgnoreCase(type)){
			return WD;
		}else if("WX".equalsIgnoreCase(type)){
			return WX;
		}else if("HSD".equalsIgnoreCase(type)){
			return HSD;
		}else if("HSS".equalsIgnoreCase(type)){
			return HSS;
		}*/else{
			return new ArrayList<Integer>();
		}
		
	}
	public static boolean DA(Integer ballNum) {
		if (ballNum >= 6)
			return true;
		else
			return false;

	}

	public static boolean X(Integer ballNum) {

		if (ballNum <6)
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

	

	

	
	

	public static boolean ballBetResult(Integer ballNum, String betType,Integer lhCompareNum) {
		if (GenericValidator.isInt(betType)) {
			if (Integer.valueOf(betType).intValue() == ballNum.intValue())
				return true;
			else
				return false;

		}  else if ("DA".equals(betType)) {
			return DA(ballNum);
		} else if ("X".equals(betType)) {
			return X(ballNum);
		} else if ("DAN".equals(betType)) {
			return DAN(ballNum);
		} else if ("S".equals(betType)) {
			return S(ballNum);
		} else if ("LONG".equals(betType)) {
			return LONG(ballNum,lhCompareNum);
		} else if ("HU".equals(betType)) {
			return HU(ballNum,lhCompareNum);
		} 
		return false;
	}

	
	public static boolean GYHBetResult(Integer ballNum, String betType) {
		if (GenericValidator.isInt(betType)) {
			if (Integer.valueOf(betType).intValue() == ballNum.intValue())
				return true;
			else
				return false;

		}  else if ("DA".equals(betType)) {
			return GYDA(ballNum);
		} else if ("X".equals(betType)) {
			return GYX(ballNum);
		} else if ("DAN".equals(betType)) {
			return GYDAN(ballNum);
		} else if ("S".equals(betType)) {
			return GYS(ballNum);
		}
		return false;
	}
	


	
	
	public static boolean GYDA(Integer GYNum) {
		
		if (GYNum.intValue()>11)
			return true;
		else
			return false;

	}

	public static boolean GYX(Integer GYNum) {
		
		if (GYNum.intValue()<=11)
			return true;
		else
			return false;

	}

	public static boolean GYDAN(Integer GYNum) {

		if (GYNum % 2 != 0)
			return true;
		else
			return false;

	}

	public static boolean GYS(Integer GYNum) {
		if (GYNum % 2 == 0)
			return true;
		else
			return false;

	}

	public static boolean LONG(Integer ballNum,Integer lhCompareNum) {
		if (ballNum.intValue() > lhCompareNum.intValue())
			return true;
		else
			return false;

	}

	public static boolean HU(Integer ballNum,Integer lhCompareNum) {
		if (ballNum.intValue() < lhCompareNum.intValue())
			return true;
		else
			return false;
	}

	public static boolean getBetResult(PlayType type, List<Integer> winNums) {

		String playSubType = type.getPlaySubType();
		String playFinalType = type.getPlayFinalType();
		Integer ballNum = null;
		Integer lhCompareNum=null;
		boolean betResult = false;
		if ("BALL_FIRST".equals(playSubType)) {
			ballNum = winNums.get(0);
			lhCompareNum=winNums.get(9);
			betResult = ballBetResult(ballNum, playFinalType,lhCompareNum);
		}
		if ("BALL_SECOND".equals(playSubType)) {
			ballNum = winNums.get(1);
			lhCompareNum=winNums.get(8);
			betResult = ballBetResult(ballNum, playFinalType,lhCompareNum);
		} else if ("BALL_THIRD".equals(playSubType)) {
			ballNum = winNums.get(2);
			lhCompareNum=winNums.get(7);
			betResult = ballBetResult(ballNum, playFinalType,lhCompareNum);
		} else if ("BALL_FORTH".equals(playSubType)) {
			ballNum = winNums.get(3);
			lhCompareNum=winNums.get(6);
			betResult = ballBetResult(ballNum, playFinalType,lhCompareNum);
		} else if ("BALL_FIFTH".equals(playSubType)) {
			ballNum = winNums.get(4);
			lhCompareNum=winNums.get(5);
			betResult = ballBetResult(ballNum, playFinalType,lhCompareNum);
		} else if ("BALL_SIXTH".equals(playSubType)) {
			ballNum = winNums.get(5);

			betResult = ballBetResult(ballNum, playFinalType,lhCompareNum);
		} else if ("BALL_SEVENTH".equals(playSubType)) {
			ballNum = winNums.get(6);

			betResult = ballBetResult(ballNum, playFinalType,lhCompareNum);
		} else if ("BALL_EIGHTH".equals(playSubType)) {
			ballNum = winNums.get(7);

			betResult = ballBetResult(ballNum, playFinalType,lhCompareNum);
		}
		else if ("BALL_NINTH".equals(playSubType)) {
			ballNum = winNums.get(8);

			betResult = ballBetResult(ballNum, playFinalType,lhCompareNum);
		}
		else if ("BALL_TENTH".equals(playSubType)) {
			ballNum = winNums.get(9);

			betResult = ballBetResult(ballNum, playFinalType,lhCompareNum);
		}
		else if ("GROUP".equals(playSubType)) {
			ballNum=winNums.get(0)+winNums.get(1);
			betResult = GYHBetResult(ballNum, playFinalType);
		}

		return betResult;

	}

	

	public static void main(String[] args) {
		List a=new ArrayList();
		a.add("0");
		System.out.println(a.get(0));
		
	}
	
	
}
