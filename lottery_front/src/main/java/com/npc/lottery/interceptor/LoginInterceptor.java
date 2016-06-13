package com.npc.lottery.interceptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.npc.lottery.common.Constant;
import com.npc.lottery.common.ResultObject;
import com.npc.lottery.common.service.ShopSchemeService;
import com.npc.lottery.service.CookieService;
import com.npc.lottery.service.MemCachedService;
import com.npc.lottery.service.OnlineMemberService;
import com.npc.lottery.service.UserService;
import com.npc.lottery.sysmge.entity.MemberUser;
import com.npc.lottery.util.WebTools;

/**
 * 登陆拦截器
 * 
 * @author 888
 * 
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

	private Logger log = Logger.getLogger(LoginInterceptor.class);
	private static final int PASSWORD_EXPIRE_DAYS = 30;

	private final static List<String> suffixListNoNeedToLogin = new ArrayList<String>();
	static {
		suffixListNoNeedToLogin.add(".css");
		suffixListNoNeedToLogin.add(".png");
		suffixListNoNeedToLogin.add(".jpg");
		suffixListNoNeedToLogin.add(".gif");
		suffixListNoNeedToLogin.add(".ico");
	}

	// 定义不需要登陆的访问页面 TODO 这部分数据需要添加从数据库中读取的部分
	private final static ArrayList<String> dispenseWithLoginPageList = new ArrayList<String>();
	static {
		dispenseWithLoginPageList.add("memberLogin.xhtml");// 会员登陆
		dispenseWithLoginPageList.add("login.xhtml");// 登陆处理
		dispenseWithLoginPageList.add("loginFailure.xhtml");// 管理登陆失败处理
		dispenseWithLoginPageList.add("kickout.xhtml");// 退出
		dispenseWithLoginPageList.add("kickout.json");// 退出json
		dispenseWithLoginPageList.add("checkCode.json");// 验证码校验
		dispenseWithLoginPageList.add("");// 默认页面
		dispenseWithLoginPageList.add("checkCodePage.xhtml");// 默认页面
		dispenseWithLoginPageList.add("index.jsp");// 默认页面
		dispenseWithLoginPageList.add("notLogin.xhtml");// 默认页面
	}

	@Autowired
	private CookieService cookieService;
	@Autowired
	private MemCachedService memCachedService;

	@Autowired
	private WebTools webTools;
	@Autowired
	private ShopSchemeService shopSchemeService;

	@Autowired
	private OnlineMemberService onlineMemberService;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		// 读取请求页面的URL
		String requestURL = request.getRequestURI();
		// TODO "/"可能在不同的系统中有区别，待处理
		String pageName = "";
		try {
			pageName = requestURL.substring(requestURL.lastIndexOf("/") + 1).trim();
			// add by peter 去掉jsession的尾巴
			String[] tmpNames = pageName.split(";jsessionid");
			if (null != tmpNames && tmpNames.length > 1) {
				pageName = tmpNames[0];
			}
		} catch (Exception ex) {

		}

		// 校验是否类似CSS等文件可以直接访问,不需要登录
		for (String suffix : suffixListNoNeedToLogin) {
			if (pageName.endsWith(suffix)) {
				log.debug("user request page " + pageName + " not need to login,pass");
				return true;
			}
		}

		// 如果是请求不需要登陆的访问页面，则无需判断是否登陆
		for (int i = 0; i < dispenseWithLoginPageList.size(); i++) {
			if (((String) dispenseWithLoginPageList.get(i)).trim().equalsIgnoreCase(pageName)) {
				log.info("user request page  " + dispenseWithLoginPageList.get(i));
				return true;
			}
		}

		// 0.从cookies获取值
		String uid = cookieService.getCookieValue(Constant.COOKIE_UID, request);
		String path = cookieService.getCookieValue(Constant.COOKIE_PATH, request);
		String ck = cookieService.getCookieValue(Constant.COOKIE_CK, request);
		String token = cookieService.getCookieValue(Constant.COOKIE_TOKEN, request);
		String shopCode = WebTools.getShopCodeByPath(path);
		// 1.判断是否正常注入cookie
		if (!this.checkCookieSuccessInject(uid, path, ck, token)) {
			log.debug("user request " + pageName + " ; no login!");
			return this.unLoginHandler(request, response, shopCode);
		} else {

			// 2.判断cookie里的token与缓存里的一致性
			String sessionToken = WebTools.getSessionToken(uid, shopCode, request);
			String memCachedToken = memCachedService.getToken(Long.valueOf(uid), shopCode);

			// 代表有别人登陆，重定向到提示页面
			if (StringUtils.isNotEmpty(memCachedToken) && !token.equals(memCachedToken)) {
				if (requestURL.endsWith(".json")) {
					response.sendRedirect(request.getContextPath() + "/" + path + "/kickout.json");
				} else {
					response.sendRedirect(request.getContextPath() + "/" + path + "/kickout.xhtml");
				}
				return false;
			}
			if (StringUtils.isNotEmpty(sessionToken)) {
				if (token.equals(sessionToken)) {
					// 判断用户的shopcode能否找到对应的scheme
					String scheme = shopSchemeService.getSchemeByShopCode(shopCode);
					if (StringUtils.isEmpty(scheme)) {
						log.error("shopCode:" + shopCode + "----scheme找不到异常");
						return unLoginHandler(request, response, shopCode);
					} else {
						if (StringUtils.isEmpty(memCachedToken)) {
							memCachedService.setToken(Long.valueOf(uid), sessionToken, shopCode);
						}
						return LoginHandler(request, requestURL, uid, response, shopCode, path, scheme);
					}
				}

			}
		}
		if (!cookieService.checkToken(request))// 3.判断cookies的有效性
		{
			// 如果token与算法计算不一致,则不通过
			log.debug("user request " + pageName + " ; no login!");
			return this.unLoginHandler(request, response, shopCode);
		} else {
			// 判断用户的shopcode能否找到对应的scheme
			String scheme = shopSchemeService.getSchemeByShopCode(shopCode);
			if (StringUtils.isEmpty(scheme)) {
				log.error("shopCode:" + shopCode + "----scheme找不到异常");
				return unLoginHandler(request, response, shopCode);
			} else {
				WebTools.setSessionToken(uid, shopCode, request, token);
				return LoginHandler(request, requestURL, uid, response, shopCode, path, scheme);
			}
		}

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}

	private boolean checkCookieSuccessInject(String uid, String path, String ck, String token) {
		boolean flag = true;

		if (StringUtils.isEmpty(uid)) {
			flag = false;
			log.error("cookie key:uid not inject");
		}

		if (StringUtils.isEmpty(path)) {
			flag = false;
			log.error("cookie key:path not inject");
		}

		if (StringUtils.isEmpty(ck)) {
			flag = false;
			log.error("cookie key:ck not inject");
		}

		if (StringUtils.isEmpty(token)) {
			flag = false;
			log.error("cookie key:token not inject");
		}
		return flag;
	}

	@Autowired
	private UserService userService;

	private boolean LoginHandler(HttpServletRequest request, String requestURL, String uid, HttpServletResponse response, String shopCode, String path, String scheme) throws IOException,
	        ServletException {

		// 判断URL权限
		// 获取可以用来鉴权的URL
		String url = requestURL.replaceAll(request.getContextPath(), "");// 过滤掉工程名
		// 截取url最后一段，系统目前只鉴权Action
		if (url.length() > 5) {
			url = url.trim();
		}
		// 获取用户信息后，校验每个请求是否用户有修改密码的要求
		String[] urlArray = url.split("/");
		String lastAddress = "";
		if (urlArray.length >= 2) {
			lastAddress = urlArray[urlArray.length - 1];
		}

		MemberUser memberUser = memCachedService.getMemberUserModel(Long.valueOf(uid), shopCode);
		if (null == memberUser) {
			memberUser = userService.getMemberUserById(Long.valueOf(uid), scheme);
			memCachedService.setMemberUserModel(memberUser);
		}
		if (null != memberUser) {
			
			//更新在线用户统计
			onlineMemberService.updateOnlineMemberDate(uid, shopCode);
			
			String redirectURL = this.getPasswordExpireUrl(memberUser.getPasswordUpdateDate(), memberUser.getPasswordResetFlag(), lastAddress, url, request.getContextPath(), path);

			if (StringUtils.isNotEmpty(redirectURL)) {
				// 重定向到密码修改页面
				response.sendRedirect(redirectURL);
				return false;
			} else {
				return true;
			}
		} else {
			// 如果数据库都找不到用户信息,跳会首页
			response.sendRedirect(request.getContextPath() + "/memberLogin.xhtml?shopcode=" + shopCode);
			return false;
		}

	}

	private boolean unLoginHandler(HttpServletRequest request, HttpServletResponse response, String shopCode) throws IOException {

		// log.info("user request " + pageName + " ; no login!");
		// 如果未登陆，则跳转到登陆页面
		// 如果是ajax 請求返回json 信息

		try {
			if (null != request.getHeader("X-Requested-With") && "XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
				ResultObject rs = new ResultObject();
				Map<String, String> errorMap = new HashMap<String, String>();
				errorMap.put("errorInfo", "notLogin");
				Map<String, Map<String, String>> retMap = new HashMap<String, Map<String, String>>();
				retMap.put("error", errorMap);
				rs.setData(retMap);
				String jsonOdd = JSONObject.toJSONString(rs);
				ajaxJson(jsonOdd, response);
			} else {
				response.sendRedirect(request.getContextPath() + "/notLogin.xhtml?shopcode=" + shopCode);

			}
		} finally {
			cookieService.clear(request, response);
		}
		return false;
	}

	public String ajaxJson(Map<String, String> jsonMap, HttpServletResponse response) {
		return ajax(JSONObject.toJSONString(jsonMap), "text/html", response);
	}

	public String ajaxJson(String jsonString, HttpServletResponse response) {
		return ajax(jsonString, "text/html", response);
	}

	public String ajax(String content, String type, HttpServletResponse response) {
		try {
			response.setContentType(type + ";charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * add by peter 获得过期密码修改页面
	 * 
	 * @param userInfo
	 * @param lastAddress
	 * @param url
	 * @param contextPath
	 * @return
	 */
	private String getPasswordExpireUrl(Date passwordUpdateDate, String passwordResetFlag, String lastAddress, String url, String contextPath, String path) {
		List<String> actionList = new ArrayList<String>();
		actionList.add("enterChangePassword.xhtml");
		actionList.add("ajaxQueryQianPassword.xhtml");
		actionList.add("changePassword.xhtml");
		actionList.add("logout.xhtml");
		Date passwordExpireDate = DateUtils.addDays(passwordUpdateDate, PASSWORD_EXPIRE_DAYS);
		String redirectUrl = contextPath + "/" + path + "/enterChangePassword.xhtml";

		// add by peter,检测用户密码的更新时间是否超过15日,如果超过则强制用户必须要修改密码才能登陆
		if (null != passwordExpireDate && new Date().compareTo(passwordExpireDate) >= 0) {
			if (!actionList.contains(lastAddress) && (url.endsWith(".json") || url.endsWith(".xhtml"))) {
				redirectUrl = redirectUrl + "?isPasswordExpire=Y";
				return redirectUrl;
			}
		} else if ("Y".equals(passwordResetFlag)) {
			if (!actionList.contains(lastAddress) && (url.endsWith(".json") || url.endsWith(".xhtml"))) {
				redirectUrl = redirectUrl + "?isPasswordReset=Y";
				return redirectUrl;
			}
		}
		return null;
	}

}
