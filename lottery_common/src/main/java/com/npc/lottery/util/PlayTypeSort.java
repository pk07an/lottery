package com.npc.lottery.util;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class PlayTypeSort {

	public static Map<String, ?> mapSortByKeyPlayType(Map<String, ?> unsort_map) {
		TreeMap<String, Object> result = new TreeMap<String, Object>(new LotteryComparator());
		result.putAll(unsort_map);
		return result;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SpringUtil sp = new SpringUtil();
		ApplicationContext context = new FileSystemXmlApplicationContext("F:/JAVA/workspace/Lottery20121004/WEB-INF/applicationContext.xml");
		sp.setApplicationContext(context);
		Map<String, Object> testMap = new HashMap<String, Object>();
		testMap.put("GDKLSF_BALL_THIRD_20", "");
		testMap.put("GDKLSF_DOUBLESIDE_6_DA", "");
		testMap.put("GDKLSF_BALL_THIRD_2", "");
		System.out.println(testMap);
		System.out.println(PlayTypeSort.mapSortByKeyPlayType(testMap));

	}
}
