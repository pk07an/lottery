package com.npc.lottery.util.compare;

import java.io.Serializable;
import java.util.Comparator;

import com.npc.lottery.member.entity.PlayType;
import com.npc.lottery.util.PlayTypeUtils;


public class GDKLSFCompare implements Serializable,Comparator<String>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int compare(String typeCode1, String typeCode2) {
		// TODO Auto-generated method stub
		return getGDDoubleSideOrder(typeCode1)-getGDDoubleSideOrder(typeCode2);
	}
	
	public int getGDDoubleSideOrder(String typeCode) {
		PlayType playType=PlayTypeUtils.getPlayType(typeCode);
		if(playType==null)
			return 0;
		String playFinalType=playType.getPlayFinalType();
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
			{   int ret=9000;
				if (typeCode.indexOf("DOUBLESIDE_1") != -1)
					ret= 8000;
				else if (typeCode.indexOf("DOUBLESIDE_2") != -1)
					ret= 7000;
				else if (typeCode.indexOf("DOUBLESIDE_3") != -1)
					ret= 6000;
				else if (typeCode.indexOf("DOUBLESIDE_4") != -1)
					ret= 5000;
				else if (typeCode.indexOf("DOUBLESIDE_5") != -1)
					ret= 4000;
				else if (typeCode.indexOf("DOUBLESIDE_6") != -1)
					ret= 3000;
				else if (typeCode.indexOf("DOUBLESIDE_7") != -1)
					ret= 2000;
				else if (typeCode.indexOf("DOUBLESIDE_8") != -1)
					ret= 1000;
				if(playFinalType.indexOf("DA") != -1)
					ret=ret-10;
				else if(playFinalType.indexOf("X") != -1)
					ret=ret-20;
				else if(playFinalType.indexOf("DAN") != -1)
					ret=ret-30;
				else if(playFinalType.indexOf("S") != -1)
					ret=ret-40;
				else if(playFinalType.indexOf("WD") != -1)
					ret=ret-50;
				else if(playFinalType.indexOf("WX") != -1)
					ret=ret-60;
				else if(playFinalType.indexOf("HSD") != -1)
					ret=ret-70;
				else if(playFinalType.indexOf("HSS") != -1)
					ret=ret-80;
				return ret;
				
			}
				


		}

}
