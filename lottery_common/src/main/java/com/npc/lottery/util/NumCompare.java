package com.npc.lottery.util;

import org.apache.commons.lang.StringUtils;

public class NumCompare {

	public static void main(String[] args) {
		String str = getIndexNum("5", "4", "10", "6", "2", "10");
		String str2 = getIndexNum("10", "4", "", "", "4", "10");
		String str3 = getIndexNum("", "", "10", "6", "5", "10");
		//System.out.println("1----" + str);
		//System.out.println("2----" + str2);
		//System.out.println("3----" + str3);
	}

	/*
	 * lowerNum:下限值
	 * lowerLimit：阈值下限边界，取值如下：3 — 小于；4 — 小于等于；5 — 等于
	 * oppNum：上限值
	 * oppLimit：阈值上限边界，取值如下：5 — 等于；6 — 大于等于；7 — 大于
	 * thresholdValue：指数值
	 * num：比较值
	 */
	public static String getIndexNum(String lowerNum, String lowerLimit,
			String uppNum, String uppLimit, String thresholdValue, String num) {

		if (StringUtils.isEmpty(uppNum)) {//如果上线为空的话，就只有下线，那么下线是大于等于>=
			if ("4".equals(lowerLimit)) { //表示等于
				if (Integer.parseInt(lowerNum) >= Integer.parseInt(num)) {
					return thresholdValue;
				}
			} else {
				if (Integer.parseInt(lowerNum) > Integer.parseInt(num)) {
					return thresholdValue;
				}
			}

		} else if (StringUtils.isEmpty(lowerNum)) {//如果下线为空的话，就是上线 <=
			if ("6".equals(uppLimit)) { //表示等于
				if (Integer.parseInt(uppNum) <= Integer.parseInt(num)) {
					return thresholdValue;
				}
			} else {
				if (Integer.parseInt(uppNum) < Integer.parseInt(num)) {
					return thresholdValue;
				}
			}
		} else if (StringUtils.isNotEmpty(lowerNum)
				&& StringUtils.isNotEmpty(uppNum)) { //<最低值 >最高值
			if ("4".equals(lowerLimit) && "6".equals(uppLimit)) { //<=最低值 >=最高值
				if (Integer.parseInt(lowerNum) >= Integer.parseInt(num)
						&& Integer.parseInt(uppNum) <= Integer.parseInt(num)) {
					return thresholdValue;
				}
			} else if ("4".equals(lowerLimit) && !"6".equals(uppLimit)) {
				if (Integer.parseInt(lowerNum) >= Integer.parseInt(num)
						&& Integer.parseInt(uppNum) < Integer.parseInt(num)) {
					return thresholdValue;
				}
			} else if (!"4".equals(lowerLimit) && "6".equals(uppLimit)) {
				if (Integer.parseInt(lowerNum) > Integer.parseInt(num)
						&& Integer.parseInt(uppNum) <= Integer.parseInt(num)) {
					return thresholdValue;
				}
			} else {
				if (Integer.parseInt(lowerNum) > Integer.parseInt(num)
						&& Integer.parseInt(uppNum) < Integer.parseInt(num)) {
					return thresholdValue;
				}
			}
		} else {
			return "";
		}
		return "";
	}

}
