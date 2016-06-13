package com.npc.lottery.util;

import javax.servlet.http.HttpServletRequest;

public class WebTools {
	/**
	 * 如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值， 那么真
	 * 正的用户端的真实IP则是取X-Forwarded-For中第一个非unknown的有效IP字符串。
	 * 
	 * @param request
	 *            请求对象
	 * @return 真实IP
	 */
	public static String getClientIpAddr(HttpServletRequest request) {
		String ip = "";
		if (request.getHeader("REQ_REAL_IP") != null) {
			ip = request.getHeader("REQ_REAL_IP");
		} else {
			ip = request.getHeader("x-forwarded-for");
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
		}
		return ip;
	}
}
