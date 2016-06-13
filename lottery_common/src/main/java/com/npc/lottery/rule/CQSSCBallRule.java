package com.npc.lottery.rule;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;

import com.google.common.collect.Lists;
import com.npc.lottery.member.entity.PlayType;

import edu.emory.mathcs.backport.java.util.Collections;


public class CQSSCBallRule {
	
	// 大
	public static final List<Integer> DA = new ArrayList<Integer>();
	// 小
	public static final List<Integer> X = new ArrayList<Integer>();
	// 單
	public static final List<Integer> DAN = new ArrayList<Integer>();
	// 雙
	public static final List<Integer> SHUANG = new ArrayList<Integer>();
	
	static{
		// 重慶 0 - 9
		for(int i=0;i<=9;i++)
		{
			if(DA(i)) DA.add(i);
			if(X(i)) X.add(i);
			if(DAN(i)) DAN.add(i);
			if(S(i)) SHUANG.add(i);
		}
		
	}
	public static List<Integer> getMatchList(String type)
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
		}else{
			return new ArrayList<Integer>();
		}
	}
	public  static boolean  DA(Integer ballNum)
	{
		if(ballNum>=5)
			return true;			
		else return false;
		
	}
	public  static boolean  X(Integer ballNum)
	{
		
		if(ballNum<=4)
			return true;			
		else return false;
		
		
	}
	
	public  static boolean  DAN(Integer ballNum)
	{
		if(ballNum.intValue()%2!=0)
			return true;			
		else return false;
		
	}
	public  static boolean  S(Integer ballNum)
	{
		
		if(ballNum.intValue()%2==0)
			return true;			
		else return false;
		
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
	
		return false;
	}
	
	
	
	
	public  static boolean  ZHDA(List<Integer> winNums)
	{
	  Integer sum=0;
		for (int i = 0; i < winNums.size(); i++) {
			sum=sum+winNums.get(i);
	}
		
			if(sum>=23)
		   return true;
			else return false;
		
	}
	public  static boolean  ZHX(List<Integer> winNums)
	{
		 Integer sum=0;
			for (int i = 0; i < winNums.size(); i++) {
				sum=sum+winNums.get(i);
		}
			
				if(sum<=22)
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
				{
			      

						return true;
				}
				else
					{

					return false;
					}
		
	}
	public  static boolean  LONG(List<Integer> winNums)
	{
		Integer first=winNums.get(0);
		Integer fifth=winNums.get(4);
		if(first>fifth)
		   return true;
		else 
			return false;
		
	}
	
	public  static boolean  HU(List<Integer> winNums)
	{
		Integer first=winNums.get(0);
		Integer fifth=winNums.get(4);
		if(first<fifth)
		   return true;
		else 
			return false;
	}
	
	
	public  static boolean  HE(List<Integer> winNums)
	{
		Integer first=winNums.get(0);
		Integer fifth=winNums.get(4);
		if(first.intValue()==fifth.intValue())
		   return true;
		else 
			return false;
	}
	
	public  static boolean  BAOZI(List<Integer> winNums)
	{
		Integer one=winNums.get(0);
		Integer two=winNums.get(1);
		Integer three=winNums.get(2);
		if(one.intValue()==two.intValue()&&two.intValue()==three.intValue())
		   return true;
		else 
			return false;
	}
	
	public  static boolean  SHUNZI(final List<Integer> winNums)
	{
		List<Integer> winNum=Lists.newArrayList(winNums);
		String refers="0123456789012";
		Collections.sort(winNum);
		String winStr=StringUtils.join(winNum, "");
		if(refers.indexOf(winStr)!=-1)
		   return true;
		else 
			return false;
	}
	
	public  static boolean  DUIZI(List<Integer> winNums)
	{
		if(BAOZI(winNums))
			return false;
		Integer one=winNums.get(0);
		Integer two=winNums.get(1);
		Integer three=winNums.get(2);
		
		if(one.intValue()==two.intValue()||two.intValue()==three.intValue()||one.intValue()==three.intValue())
		   return true;
		else 
			return false;
	}
	
	public  static boolean  BANSHUN(List<Integer> winNums)
	{
		List<Integer> winNum=Lists.newArrayList(winNums);
		if(SHUNZI(winNum))
			return false;
		if(DUIZI(winNum))
			return false;
		String refers="0123456789012";
		Collections.sort(winNum);
		String winStr=StringUtils.join(winNum, "");
	  
		if(refers.indexOf(winStr.substring(0,2))!=-1||refers.indexOf(winStr.substring(1,3))!=-1)
		   return true;
		else 
			return false;
	}
	
	
	public  static boolean  ZALIU(List<Integer> winNums)
	{
		if(!BAOZI(winNums)&&!SHUNZI(winNums)&&!DUIZI(winNums)&&!BANSHUN(winNums))
			return true;
			else return false;
	}
	
	
	public static boolean allBallBetResult(List<Integer> winNums,String betType)
	{
		
		if("ZHDA".equals(betType))
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
		
		else if("LONG".equals(betType))
		{
			return LONG(winNums);
		}
		else if("HU".equals(betType))
		{
			return HU(winNums);
		}
		
		else if("HE".equals(betType))
		{
			return HE(winNums);
		}
			
		return false;
	}
	
	
	
	
	

	public static boolean getBallBetResult(PlayType playType,List<Integer> winNums)
	{
		//String typeCode=playType.getTypeCode();
		//String subType=playType.getPlaySubType();
		//String finalType=playType.getPlayFinalType();
		String playSubType=playType.getPlaySubType();
		String playFinalType=playType.getPlayFinalType();
		Integer ballNum=0;
		boolean betResult=false;
		if("BALL_FIRST".equals(playSubType))
		{
			ballNum=winNums.get(0);
			betResult=ballBetResult(ballNum,playFinalType);
		}
		else if("BALL_SECOND".equals(playSubType))
		{
			ballNum=winNums.get(1);
			betResult=ballBetResult(ballNum,playFinalType);
		}
		else if("BALL_THIRD".equals(playSubType))
		{
			ballNum=winNums.get(2);
			betResult=ballBetResult(ballNum,playFinalType);
		}
		else if("BALL_FORTH".equals(playSubType))
		{
			ballNum=winNums.get(3);
			betResult=ballBetResult(ballNum,playFinalType);
		}
		else if("BALL_FIFTH".equals(playSubType))
		{
			ballNum=winNums.get(4);
			betResult=ballBetResult(ballNum,playFinalType);
			
		}
		else if ("FRONT".equals(playSubType))
		{
			 List<Integer> subList=winNums.subList(0, 3);
			if("BZ".equals(playFinalType))
			{
				betResult=BAOZI(subList);
			}
			if("SZ".equals(playFinalType))
			{
				betResult=SHUNZI(subList);
			}
			if("DZ".equals(playFinalType))
			{
				betResult=DUIZI(subList);
			}
			if("BS".equals(playFinalType))
			{
				betResult=BANSHUN(subList);
			}
			if("ZL".equals(playFinalType))
			{
				betResult=ZALIU(subList);
			}
			  
		}
		else if ("MID".equals(playSubType))
		{
			 List<Integer> subList =winNums.subList(1, 4);
			 if("BZ".equals(playFinalType))
				{
				 betResult=BAOZI(subList);
				}
				if("SZ".equals(playFinalType))
				{
					betResult=SHUNZI(subList);
				}
				if("DZ".equals(playFinalType))
				{
					betResult=DUIZI(subList);
				}
				if("BS".equals(playFinalType))
				{
					betResult=BANSHUN(subList);
				}
				if("ZL".equals(playFinalType))
				{
					betResult=ZALIU(subList);
				}
		}
		else if ("LAST".equals(playSubType))
		{
			 List<Integer> subList=winNums.subList(2, 5);
			 if("BZ".equals(playFinalType))
			{
				 betResult=BAOZI(subList);
			}
				if("SZ".equals(playFinalType))
				{
					betResult=SHUNZI(subList);
				}
				if("DZ".equals(playFinalType))
				{
					betResult=DUIZI(subList);
				}
				if("BS".equals(playFinalType))
				{
					betResult=	BANSHUN(subList);
				}
				if("ZL".equals(playFinalType))
				{
					betResult=	ZALIU(subList);
				}
			
		}
		else 
		{
			betResult=allBallBetResult( winNums,playFinalType);
		}
		//else if("STRAIGHTTHROUGH".equals(playSubType))
		
		
		
		return betResult;
		
		
		
		
	}
	
	public static void main(String[] args) {
		

   
   
  
	String typeCode="CQSSC_DZ_FRONT";
	String subType="FRONT";
	String finnalType="DZ";
	PlayType pt=new PlayType();
	pt.setTypeCode(typeCode);
	pt.setPlaySubType(subType);
	pt.setPlayFinalType(finnalType);
	 List winNum=Lists.newArrayList(3,3,7,8,4);
	System.out.println( CQSSCBallRule.getBallBetResult(pt, winNum));
	
  
		
		
	}
	

}
