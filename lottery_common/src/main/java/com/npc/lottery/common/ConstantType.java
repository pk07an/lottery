package com.npc.lottery.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

/**
 * 非业务数据的常量信息
 * 
 * @author none
 *
 */
public class ConstantType {

	//红波
	public static List<Integer> RED=Lists.newArrayList(1,2,7,8,12,13,18,19,23,24,29,30,34,35,40,45,46);
	//蓝波
	public static List<Integer> BLUE=Lists.newArrayList(3,4,9,10,14,15,20,25,26,31,36,37,41,42,47,48);
	//绿波
	public static List<Integer> GREEN=Lists.newArrayList(5,6,11,16,17,21,22,27,28,32,33,38,39,43,44,49);
	//生肖
	public static List<String> SXList  =Lists.newArrayList("SHU", "NIU", "HU", "TU", "LONG", "SHE", "MA", "YANG", "HOU", "JI", "GOU", "ZHU");
	
	public static List<String> WSList  =Lists.newArrayList("W0", "W1", "W2", "W3", "W4", "W5", "W6", "W7", "W8", "W9");
	
	//单
	public static List<Integer> DAN=Lists.newArrayList(1,3,5,7,9,11,13,15,17,19,21,23,25,27,29,31,33,35,37,39,41,43,45,47,49);
	//双
	public static List<Integer> SHUANG=Lists.newArrayList(2,4,6,8,10,12,14,16,18,20,22,24,26,28,30,32,34,36,38,40,42,44,46,48);
	
	//合单
	public static List<Integer> HSD=Lists.newArrayList(1,3,5,7,9,10,12,14,16,18,21,23,25,27,29,30,32,34,36,38,41,43,45,47,49);
	//合双
	public static List<Integer> HSS=Lists.newArrayList(2,4,6,8,11,13,15,17,19,20,22,24,26,28,31,33,35,37,39,40,42,44,46,48);
	
	//尾大
	public static List<Integer> WD=Lists.newArrayList(5,6,7,8,9,15,16,17,18,19,25,26,27,28,29,35,36,37,38,39,45,46,47,48,49);
	//尾小
	public static List<Integer> WX=Lists.newArrayList(1,2,3,4,10,11,12,13,14,20,21,22,23,24,30,31,32,33,34,40,41,42,43,44);
	//大
	public static List<Integer> DA=Lists.newArrayList(25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49);
	//小
	public static List<Integer> X=Lists.newArrayList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24);
	//尾数1-9
	public static List<Integer> W0  =Lists.newArrayList(10,20,30,40);
	public static List<Integer> W1  =Lists.newArrayList(1,11,21,31,41);
	public static List<Integer> W2  =Lists.newArrayList(2,12,22,32,42);
	public static List<Integer> W3  =Lists.newArrayList(3,13,23,33,43);
	public static List<Integer> W4  =Lists.newArrayList(4,14,24,34,44);
	public static List<Integer> W5  =Lists.newArrayList(5,15,25,35,45);
	public static List<Integer> W6  =Lists.newArrayList(6,16,26,36,46);
	public static List<Integer> W7  =Lists.newArrayList(7,17,27,37,47);
	public static List<Integer> W8  =Lists.newArrayList(8,18,28,38,48);
	public static List<Integer> W9  =Lists.newArrayList(9,19,29,39,49);
	//頭0-4
	public static List<Integer> T0  =Lists.newArrayList(1,2,3,4,5,6,7,8,9);
	public static List<Integer> T1  =Lists.newArrayList(10,11,12,13,14,15,16,17,18,19);
	public static List<Integer> T2  =Lists.newArrayList(20,21,22,23,24,25,26,27,28,29);
	public static List<Integer> T3  =Lists.newArrayList(30,31,32,33,34,35,36,37,38,39);
	public static List<Integer> T4  =Lists.newArrayList(40,41,42,43,44,45,46,47,48,49);
	
	//红单
	public static List<Integer> RDAN  =Lists.newArrayList(1,7,13,19,23,29,35,45);
	//红双
	public static List<Integer> RS  =Lists.newArrayList(2,8,12,18,24,30,34,40,46);
	//红大
	public static List<Integer> RDA  =Lists.newArrayList(29,30,34,35,40,45,46);
	//红小
	public static List<Integer> RX  =Lists.newArrayList(1,2,7,8,12,13,18,19,23,24);
	
	public static List<Integer> GDAN  =Lists.newArrayList(5,11,17,21,27,33,39,43,49);
	public static List<Integer> GS  =Lists.newArrayList(6,16,22,28,32,38,44);
	public static List<Integer> GDA  =Lists.newArrayList(27,28,32,33,38,39,43,44,49);
	public static List<Integer> GX  =Lists.newArrayList(5,6,11,16,17,21,22);
	
	public static List<Integer> BDAN  =Lists.newArrayList(3,9,15,25,31,37,41,47);
	public static List<Integer> BS  =Lists.newArrayList(4,10,14,20,26,36,42,48);
	public static List<Integer> BDA  =Lists.newArrayList(25,26,31,36,37,41,42,47,48);
	public static List<Integer> BX  =Lists.newArrayList(3,4,9,10,14,15,20);
	
	//存储页面上 所以属性，减少action中if逻辑
	public static Map<String,List<Integer>> CONSTANTTYPEMAP = new HashMap<String,List<Integer>>();
	
	static
	{
		CONSTANTTYPEMAP.put("RDAN", RDAN);
		CONSTANTTYPEMAP.put("RS", RS);
		CONSTANTTYPEMAP.put("RDA", RDA);
		CONSTANTTYPEMAP.put("RX", RX);
		CONSTANTTYPEMAP.put("GDAN", GDAN);
		CONSTANTTYPEMAP.put("GS", GS);
		CONSTANTTYPEMAP.put("GDA", GDA);
		CONSTANTTYPEMAP.put("GX", GX);
		CONSTANTTYPEMAP.put("BDAN", BDAN);
		CONSTANTTYPEMAP.put("BS", BS);
		CONSTANTTYPEMAP.put("BDA", BDA);
		CONSTANTTYPEMAP.put("BX", BX);
		
		CONSTANTTYPEMAP.put("W0", W0);
		CONSTANTTYPEMAP.put("W1", W1);
		CONSTANTTYPEMAP.put("W2", W2);
		CONSTANTTYPEMAP.put("W3", W3);
		CONSTANTTYPEMAP.put("W4", W4);
		CONSTANTTYPEMAP.put("W5", W5);
		CONSTANTTYPEMAP.put("W6", W6);
		CONSTANTTYPEMAP.put("W7", W7);
		CONSTANTTYPEMAP.put("W8", W8);
		CONSTANTTYPEMAP.put("W9", W9);
		
		CONSTANTTYPEMAP.put("X", X);
		CONSTANTTYPEMAP.put("DA", DA);
		CONSTANTTYPEMAP.put("WD", WD);
		CONSTANTTYPEMAP.put("WX", WX);
		
		CONSTANTTYPEMAP.put("RED", RED);
		CONSTANTTYPEMAP.put("BLUE", BLUE);
		CONSTANTTYPEMAP.put("GREEN", GREEN);
		
		CONSTANTTYPEMAP.put("HSD", HSD);
		CONSTANTTYPEMAP.put("HSS", HSS);
		CONSTANTTYPEMAP.put("BDA", BDA);
		
		CONSTANTTYPEMAP.put("DAN", DAN);
		CONSTANTTYPEMAP.put("SHUANG", SHUANG);
		
		CONSTANTTYPEMAP.put("T0", T0);
		CONSTANTTYPEMAP.put("T1", T1);
		CONSTANTTYPEMAP.put("T2", T2);
		CONSTANTTYPEMAP.put("T3", T3);
		CONSTANTTYPEMAP.put("T4", T4);
		
	}
	
	public static final HashMap<String, String> GD_MAP = new HashMap<String, String>();
    static {
    	GD_MAP.put("1", "FIRST");
    	GD_MAP.put("2", "SECOND");
    	GD_MAP.put("3", "THIRD");
    	GD_MAP.put("4", "FORTH");
    	GD_MAP.put("5", "FIFTH");
    	GD_MAP.put("6", "SIXTH");
    	GD_MAP.put("7", "SEVENTH");
    	GD_MAP.put("8", "EIGHTH");

    }
	
	
	
	
}
