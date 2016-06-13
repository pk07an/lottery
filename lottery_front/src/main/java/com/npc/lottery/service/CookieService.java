/**
 * 
 */
package com.npc.lottery.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npc.lottery.common.Constant;
import com.npc.lottery.common.service.ShopSchemeService;
import com.npc.lottery.sysmge.entity.MemberUser;
import com.npc.lottery.util.EncryptUtils;
import com.npc.lottery.util.Tools;
import com.npc.lottery.util.WebTools;

/**
 * Cookie操作服务类
 * 
 * @author c1panx 2014-12-3 下午5:53:56
 */
@Service
public class CookieService {

	private Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private MemCachedService memCachedService;
	@Autowired
	private UserService userService;
	@Autowired
	private ShopSchemeService shopSchemeService;

	/**
	 * 将cookie中的所有对象读取到Map中返回
	 * 
	 * @param request
	 * @return
	 */
	public Map<String, Cookie> getCookieMap(HttpServletRequest request) {
		return this.getCookieMap(request.getCookies());
	}

	/**
	 * cookie数组转换成map
	 * 
	 * @param cookies
	 * @return
	 */
	public Map<String, Cookie> getCookieMap(Cookie[] cookies) {
		Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		return cookieMap;
	}

	/**
	 * 根据key返回cookie中值
	 * 
	 * @param key
	 * @param request
	 * @return
	 */
	public String getCookieValue(String key, HttpServletRequest request) {
		Map<String, Cookie> cookieMap = getCookieMap(request);
		return getCookieValue(key, cookieMap);
	}

	/**
	 * 获取cookie中指定的value值
	 * 
	 * @param key
	 * @param cookieMap
	 * @return
	 */
	public String getCookieValue(String key, Map<String, Cookie> cookieMap) {
		if (null == cookieMap) {
			return null;
		}
		String value = null;
		Cookie cookie = cookieMap.get(key);
		if (null != cookie) {
			value = cookie.getValue();
		}
		return value;
	}

	/**
	 * 前端登录成功后根据用户模型内容注入cookie<br>
	 * 需加密处理<br>
	 * 
	 * @param response
	 * @param memberStaff
	 * @param type
	 * @param shopCode
	 */
	public void injectMemberUser(HttpServletRequest request, HttpServletResponse response, MemberUser memberUser, String shopCode,String date) {
		if (null == memberUser) {
			return;
		}
		/*
		 * cookies 注入内容： uid:会员中心用户ID un: 用户名/昵称，页面显示用； token: 令牌
		 */
		String uid = memberUser.getID() != 0 ? String.valueOf(memberUser.getID()) : "";
		String pathPrefix = "F_";
		String pathContext = date + "_" + uid + ":shopcode:" + shopCode;
		Cookie uidCookie = this.getUIDCookie(uid);
		Cookie ckCookie = this.getCKCookie(uid, shopCode);
		Cookie tokenCookie = this.getTokenCookie(uid, ckCookie, shopCode, request);
		Cookie pathCookie = this.getPathCookie(pathPrefix, pathContext);
		// 设置cookies注入的路径
		String addPath = request.getContextPath() + "/" + pathPrefix + Tools.encodeWithKey(pathContext) + "/";
		if (null != uidCookie) {
			this.addCookie(response, uidCookie, addPath);
		}
		if (null != ckCookie) {
			this.addCookie(response, ckCookie, addPath);
		}
		if (null != tokenCookie) {
			this.addCookie(response, tokenCookie, addPath);
		}

		if (null != pathCookie) {
			this.addCookie(response, pathCookie, addPath);
		}

	}

	private void addCookie(HttpServletResponse response, Cookie cookie, String path) {
		cookie.setPath(path);
		// cookie.setDomain(cookieDomain);
		cookie.setSecure(false);
		response.addCookie(cookie);
	}

	/**
	 * 生成UID（客户编号） cookie
	 * 
	 * @param uid
	 * @return
	 */
	private Cookie getUIDCookie(String uid) {
		Cookie uidCookie = null;
		if (!StringUtils.isBlank(uid)) {
			uidCookie = new Cookie(Constant.COOKIE_UID, uid);
		}
		logger.info("add cookie[" + Constant.COOKIE_UID + "/" + uid + "]");
		return uidCookie;
	}

	/**
	 * 生成CK（动态Key） cookie
	 * 
	 * @return
	 */
	private Cookie getCKCookie(String uid, String shopCode) {
		UUID uuid = UUID.randomUUID();
		String ck = uuid.toString();
		ck = ck.replaceAll("-", "");
		String Encryptck = EncryptUtils.encryptByMD5(ck + uid + shopCode);
		logger.info("add cookie[" + Constant.COOKIE_CK + "/" + Encryptck + "]");
		return new Cookie(Constant.COOKIE_CK, Encryptck);
	}

	/**
	 * 生成Token（动态密钥） cookie
	 * 
	 * @param uid
	 * @param ckCookie
	 * @return
	 */
	private Cookie getTokenCookie(String uid, Cookie ckCookie, String ShopCode, HttpServletRequest request) {
		String ck = ckCookie.getValue();

		String token = this.createToken(uid, ck, ShopCode, request);

		Cookie tokenCookie = null;
		if (!StringUtils.isBlank(token)) {
			tokenCookie = new Cookie(Constant.COOKIE_TOKEN, token);
		}
		logger.info("add cookie[" + Constant.COOKIE_TOKEN + "/" + token + "]");
		return tokenCookie;
	}

	/**
	 * 生成 PATH cookie
	 * 
	 * @param uid
	 * @return
	 */
	private Cookie getPathCookie(String pathPrefix, String pathContext) {
		Cookie pathCookie = null;
		if (!StringUtils.isBlank(pathPrefix) && !StringUtils.isBlank(pathContext)) {
			String path = pathPrefix + Tools.encodeWithKey(pathContext);
			pathCookie = new Cookie(Constant.COOKIE_PATH, path);
			logger.info("add cookie[" + Constant.COOKIE_PATH + "/" + path + "]");
		}

		return pathCookie;
	}

	/**
	 * 根据用户ID生成token内容<br>
	 * 组成：UID+注册时间+上级ID+CV<br>
	 * CV保存于MemCached，Token保存于cookie
	 * 
	 * @param uid
	 * @return
	 */
	private String createToken(String uid, String ck, String shopCode, HttpServletRequest request) {
		String ckcv = memCachedService.setCKCV(ck, uid);

		String registerDateAndParentStaffQryStr = this.getRegisterDateAndParentStaffQryStr(uid, shopCode);
		if (StringUtils.isEmpty(registerDateAndParentStaffQryStr)) {
			return "";
		}
		String token = uid + registerDateAndParentStaffQryStr + this.getCKCVMapfromCached(ckcv).get("cv");

		String encryptToken = EncryptUtils.encryptByMD5(token);
		encryptToken = StringUtils.isBlank(encryptToken) ? token : encryptToken;
		memCachedService.setToken(Long.valueOf(uid), encryptToken, shopCode);
		// 存一份到session中,以便memcache故障时使用
		WebTools.setSessionToken(String.valueOf(uid), shopCode, request, encryptToken);
		return encryptToken;
	}

	/**
	 * cookie有效性校验 会员端调用
	 * 
	 * @param request
	 * @return true: cookie认证通过 <br>
	 *         false: 无完整cookie或cookie被篡改
	 */
	public boolean checkToken(HttpServletRequest request) {
		logger.debug("start to check member token ......");
		Map<String, Cookie> cookieMap = this.getCookieMap(request);
		String uid = this.getCookieValue(Constant.COOKIE_UID, cookieMap);
		String ck = this.getCookieValue(Constant.COOKIE_CK, cookieMap);
		String cookieToken = this.getCookieValue(Constant.COOKIE_TOKEN, cookieMap);
		String path = this.getCookieValue(Constant.COOKIE_PATH, request);

		if (StringUtils.isBlank(uid)) {
			logger.info("UID does not exist.");
			return false;
		}
		if (StringUtils.isBlank(cookieToken)) {
			logger.info("TOKEN does not exist.");
			return false;
		}
		logger.debug("[" + uid + "] cookie token: " + cookieToken);

		String registerDateAndParentStaffQryStr = this.getRegisterDateAndParentStaffQryStr(uid, WebTools.getShopCodeByPath(path));
		if (StringUtils.isEmpty(registerDateAndParentStaffQryStr)) {
			logger.error("[" + uid + "] cookie has been juggled,registerDateStr is blank");
			return false;
		}

		String ckcv = memCachedService.getCKCV(ck, uid);
		Map<String, String> ckcvMap = getCKCVMapfromCached(ckcv);
		String memcachedCk = ckcvMap.get("ck");
		String memcachedCv = ckcvMap.get("cv");
		if (!memcachedCk.equals(ck)) {
			logger.error("[" + uid + "] cookie has been juggled");
			return false;
		}
		String token = uid + registerDateAndParentStaffQryStr + memcachedCv;
		String encryptToken = EncryptUtils.encryptByMD5(token);
		encryptToken = StringUtils.isBlank(encryptToken) ? token : encryptToken;
		logger.debug("[" + uid + "] encrypt token: " + encryptToken);

		if (StringUtils.isBlank(cookieToken) || !cookieToken.equals(encryptToken)) {
			logger.error("[" + uid + "] cookie has been juggled");
			return false;
		}
		return true;
	}

	private Map<String, String> getCKCVMapfromCached(String ckcv) {
		Map<String, String> ckcvMap = new HashMap<String, String>();
		String ck = "";
		String cv = "";
		String[] cvckArray = ckcv.split(":ckcv:");
		if (null != cvckArray && cvckArray.length == 2) {
			ck = cvckArray[0];
			cv = cvckArray[1];
		}
		ckcvMap.put("ck", ck);
		ckcvMap.put("cv", cv);
		return ckcvMap;
	}

	/**
	 * 获取用户注册时间
	 * 
	 * @param uid
	 * @return
	 */
	private String getRegisterDateAndParentStaffQryStr(String uid, String shopCode) {
		Date createDate = new Date();
		MemberUser memberUser = memCachedService.getMemberUserModel(Long.valueOf(uid), shopCode);

		if (null == memberUser) { // 如果memcached里没有,从数据库获取
			String scheme = shopSchemeService.getSchemeByShopCode(shopCode);
			memberUser = userService.getMemberUserById(Long.valueOf(uid), scheme);
		}
		createDate = null == memberUser ? null : memberUser.getCreateDate();
		DateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
		String createDateStr = null;
		if (null != createDate) {
			createDateStr = sf.format(createDate);
		}
		String parentStaffQryStr = String.valueOf(memberUser.getParentStaffQry());
		return createDateStr + parentStaffQryStr;
	}

	/**
	 * 清空cookie和session
	 * 
	 * @param request
	 * @param response
	 */
	public void clear(HttpServletRequest request, HttpServletResponse response) {
		logger.debug("start to clear cookies ......");
		Cookie[] cookieArray = request.getCookies();
		if (null == cookieArray) {
			return;
		}
		for (Cookie cookie : cookieArray) {
			String name = cookie.getName();
			if (Constant.COOKIE_UID.equalsIgnoreCase(name)) {
				this.clearCookie(cookie, response);
			}

			if (Constant.COOKIE_CK.equalsIgnoreCase(name)) {
				this.clearCookie(cookie, response);
			}
			if (Constant.COOKIE_TOKEN.equalsIgnoreCase(name)) {
				this.clearCookie(cookie, response);
			}
			if (Constant.COOKIE_PATH.equalsIgnoreCase(name)) {
				this.clearCookie(cookie, response);
			}

		}
	}

	/**
	 * 通过设置cookie有效时间清除cookie<br>
	 * setMaxAge()/getMaxAge()—>设置或获取cookie对象有效时间<br>
	 * 
	 * @param cookie
	 * @param response
	 */
	private void clearCookie(Cookie cookie, HttpServletResponse response) {
		logger.info("clear cookie: " + cookie.getName() + "/" + cookie.getValue());
		cookie.setMaxAge(0);
		cookie.setPath("/");
		cookie.setValue("");
		response.addCookie(cookie);
	}

	/**
	 * 如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值， 那么真
	 * 正的用户端的真实IP则是取X-Forwarded-For中第一个非unknown的有效IP字符串。
	 * 
	 * @param request
	 *            请求对象
	 * @return 真实IP
	 */
	@SuppressWarnings("unused")
	private String getClientIpAddr(HttpServletRequest request) {
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