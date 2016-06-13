package com.npc.lottery.util.compare;

import java.io.Serializable;
import java.util.Comparator;

import com.npc.lottery.member.entity.PlayType;
import com.npc.lottery.util.PlayTypeUtils;

public class BJSCCompare implements Serializable,Comparator<String>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int compare(String typeCode1, String typeCode2) {
		// TODO Auto-generated method stub
		return getBJDoubleSideOrder(typeCode1)-getBJDoubleSideOrder(typeCode2);
	}

	public int getBJDoubleSideOrder(String typeCode)
    {
		PlayType playType=PlayTypeUtils.getPlayType(typeCode);
		if(playType==null)
			return 0;
		String playFinalType=playType.getPlayFinalType();
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
    		   ret=9000;          
			if (typeCode.indexOf("DOUBLESIDE_1") != -1&&typeCode.indexOf("DOUBLESIDE_10") == -1)
				ret= ret-100;
			else if (typeCode.indexOf("DOUBLESIDE_2") != -1)
				ret= ret-200;
			else if (typeCode.indexOf("DOUBLESIDE_3") != -1)
				ret= ret-300;
			else if (typeCode.indexOf("DOUBLESIDE_4") != -1)
				ret= ret-400;
			else if (typeCode.indexOf("DOUBLESIDE_5") != -1)
				ret= ret-500;
			else if (typeCode.indexOf("DOUBLESIDE_6") != -1)
				ret= ret-600;
			else if (typeCode.indexOf("DOUBLESIDE_7") !=- 1)
				ret= ret-700;
			else if (typeCode.indexOf("DOUBLESIDE_8") != -1)
				ret= ret-800;
			else if (typeCode.indexOf("DOUBLESIDE_9") != -1)
				ret= ret-900;
			else if (typeCode.indexOf("DOUBLESIDE_10") != -1)
				ret= ret-1000;
			if(playFinalType.indexOf("DA") != -1)
				ret= ret-1;
			else if(playFinalType.indexOf("X") != -1)
				ret= ret-2;
			else if(playFinalType.indexOf("DAN") != -1)
				ret= ret-3;
			else if(playFinalType.indexOf("S") != -1)
				ret= ret-4;
			else if(playFinalType.indexOf("LONG") != -1)
				ret= ret-5;
			else if(playFinalType.indexOf("HU") != -1)
				ret= ret-6;
			
			return ret;
    		
    		
    		
    		
    		
    	}



    }
}
