package com.npc.lottery.util;

import org.apache.log4j.Logger;

public class GetURL {
	private static Logger log = Logger.getLogger(GetURL.class);
	
	//获取商铺号
	public static String getSafetyCodeFromUrl(String url){
		String safetyCode = "";
		try {
			String[] splite = url.split("\\.");
			safetyCode = splite[splite.length-3];
		} catch (Exception e) {
			log.info("从网页路径获取商铺号出错，错误信息："+e.getMessage());
		}
		return safetyCode;
	}
	
	/**
	 * 从网址分析获取登录的类型
	 * @param 如果hh1,就是会员，如果是kk1就是管理，这个规定主要是跟大运走
	 * @return
	 */
	public static String getUserTypeFromUrl(String url){
		String safetyCode = "";
		try {
			String[] splite = url.split("\\.");
			safetyCode = splite[splite.length-4];
		} catch (Exception e) {
			log.info("从网页路径获取商铺号出错，错误信息："+e.getMessage());
		}
		return safetyCode;
	}

}
