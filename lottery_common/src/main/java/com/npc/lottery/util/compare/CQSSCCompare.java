package com.npc.lottery.util.compare;

import java.io.Serializable;
import java.util.Comparator;

import com.npc.lottery.member.entity.PlayType;
import com.npc.lottery.util.PlayTypeUtils;

public class CQSSCCompare implements Serializable,Comparator<String>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	
	public int getCQDoubleSideOrder(String typeCode) {

		
		PlayType playType=PlayTypeUtils.getPlayType(typeCode);
		if(playType==null)
			return 0;
		String playFinalType=playType.getPlayFinalType();
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
			int ret=80;
			if(typeCode.indexOf("DOUBLESIDE_1") != -1)
			{
				ret=ret-10;
			}else if(typeCode.indexOf("DOUBLESIDE_2") != -1)
			{
				ret=ret-20;
			}
			else if(typeCode.indexOf("DOUBLESIDE_3") != -1)
			{
				ret=ret-30;
			}
			else if(typeCode.indexOf("DOUBLESIDE_4") != -1)
			{
				ret=ret-40;
			}
			else if(typeCode.indexOf("DOUBLESIDE_5") != -1)
			{
				ret=ret-50;
			}
			if(playFinalType.indexOf("DA")!=-1)
			{
				ret=ret-1;
			}
			else if(playFinalType.indexOf("X")!=-1)
			{
				ret=ret-2;
			}
			else if(playFinalType.indexOf("DAN")!=-1)
			{
				ret=ret-3;
			}
			else if(playFinalType.indexOf("S")!=-1)
			{
				ret=ret-4;
			}
			return ret;
		}


	}



	@Override
	public int compare(String typeCode1, String typeCode2) {
		// TODO Auto-generated method stub
		return getCQDoubleSideOrder(typeCode1)-getCQDoubleSideOrder(typeCode2);
	}

}
