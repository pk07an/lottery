package com.npc.lottery.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.npc.lottery.common.Constant;
import com.npc.lottery.common.service.ShopSchemeService;
import com.npc.lottery.service.CookieService;
import com.npc.lottery.service.MemCachedService;
import com.npc.lottery.service.UserService;
import com.npc.lottery.sysmge.entity.MemberUser;

@Component
public class WebTools {

	@Autowired
	private CookieService cookieService;
	@Autowired
	private MemCachedService memCachedService;
	@Autowired
	private UserService userService;
	@Autowired
	private ShopSchemeService shopSchemeService;

	public MemberUser getCurrentMemberUserByCookieUid(HttpServletRequest request) {
		MemberUser memberUser = null;
		String uid = cookieService.getCookieValue(Constant.COOKIE_UID, request);
		String path = cookieService.getCookieValue(Constant.COOKIE_PATH, request);
		if (StringUtils.isNotEmpty(uid) && StringUtils.isNotEmpty(path)) {
			memberUser = memCachedService.getMemberUserModel(Long.valueOf(uid), WebTools.getShopCodeByPath(path));
			if (null == memberUser) {
				String shopCode = WebTools.getShopCodeByPath(path);
				String scheme = shopSchemeService.getSchemeByShopCode(shopCode);
				if (StringUtils.isNotEmpty(scheme)) {
					memberUser = userService.getMemberUserById(Long.valueOf(uid), scheme);
					if (null != memberUser) {
						memCachedService.setMemberUserModel(memberUser);
					}
				}
			}
		}
		return memberUser;
	}

	public static void setSessionToken(String uid, String shopCode, HttpServletRequest request, String token) {
		request.getSession(true).setAttribute(Constant.COOKIE_TOKEN + "_" + uid + "_" + shopCode, token);
	}

	public static String getSessionToken(String uid, String shopCode, HttpServletRequest request) {
		return (String) request.getSession(true).getAttribute(Constant.COOKIE_TOKEN + "_" + uid + "_" + shopCode);
	}

	public static String getShopCodeByPath(String path) {
		String shopCode = "";
		try {
			if (StringUtils.isNotEmpty(path)) {

				String pathContext = path.replaceFirst("F_", "");

				String pathContextDecode = Tools.decodeWithKey(pathContext);
				String[] shopCodeArray =pathContextDecode.split(":shopcode:");
				if (shopCodeArray.length == 2) {
					shopCode = shopCodeArray[1];
				}
			}
		} catch (Exception ex) {
			shopCode = "";
		}
		return shopCode;
	}

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
