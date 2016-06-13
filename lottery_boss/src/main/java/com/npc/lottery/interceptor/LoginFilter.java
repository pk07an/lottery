package com.npc.lottery.interceptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.npc.lottery.common.Constant;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.sysmge.entity.MemberUser;
import com.npc.lottery.util.GetURL;

/**
 * 过滤器，验证用户是否已经登陆
 * 
 * @author none
 */
public class LoginFilter implements Filter {

	private Logger log = Logger.getLogger(LoginFilter.class);
	private static final int PASSWORD_EXPIRE_DAYS = 15;

	private final static List<String> suffixListNoNeedToLogin = new ArrayList<String>();
	static {
		suffixListNoNeedToLogin.add(".css");
		suffixListNoNeedToLogin.add(".png");
		suffixListNoNeedToLogin.add(".jpg");
		suffixListNoNeedToLogin.add(".gif");
	}

	// 定义不需要登陆的访问页面 TODO 这部分数据需要添加从数据库中读取的部分
	private static final ArrayList<String> dispenseWithLoginPageList = new ArrayList<String>();
	static {
		dispenseWithLoginPageList.add("index.jsp");// 登陆页面
		dispenseWithLoginPageList.add("choose.jsp");// 选择线路页面
		dispenseWithLoginPageList.add("managerLogin.jsp");// 管理登陆
		dispenseWithLoginPageList.add("memberLogin.jsp");// 会员登陆
		dispenseWithLoginPageList.add("loginOutByManager.jsp");// 管理被強制退出
		dispenseWithLoginPageList.add("loginOutByMember.jsp");// 會員被強制退出
		dispenseWithLoginPageList.add("style.css");// TODO
		                                           // 此文件导入目的是为了loginFailure.jsp文件，后续需要修改，避免导入
		dispenseWithLoginPageList.add("css.css");// TODO
		                                         // 此文件导入目的是为了loginFailure.jsp文件，后续需要修改，避免导入
		dispenseWithLoginPageList.add("login.css");// 新登录界面
		dispenseWithLoginPageList.add("login_manager.jpg");// 新登录界面
		dispenseWithLoginPageList.add("login_member.jpg");// 新登录界面
		dispenseWithLoginPageList.add("MEM_Login_top.jpg");// 新登录界面
		dispenseWithLoginPageList.add("MEM_login_2.jpg");// 新登录界面
		dispenseWithLoginPageList.add("MEM_login_1.jpg");// 新登录界面
		dispenseWithLoginPageList.add("MEM_login_3.jpg");// 新登录界面
		dispenseWithLoginPageList.add("ss1.gif");// 新登录界面
		dispenseWithLoginPageList.add("ss2.gif");// 新登录界面
		dispenseWithLoginPageList.add("M_login_1.jpg");// 新登录界面
		dispenseWithLoginPageList.add("M_login_3.jpg");// 新登录界面
		dispenseWithLoginPageList.add("M_login_2.jpg");// 新登录界面
		dispenseWithLoginPageList.add("login_1.jpg");// 大运版新管理登录界面
		dispenseWithLoginPageList.add("login_3.jpg");// 大运版新管理新登录界面
		dispenseWithLoginPageList.add("login_2.jpg");// 大运版新管理新登录界面
		dispenseWithLoginPageList.add("But.jpg");// 大运版新管理新登录界面
		dispenseWithLoginPageList.add("M_But.jpg");// 新登录界面
		dispenseWithLoginPageList.add("MEM_Login_but.jpg");// 新登录界面
		dispenseWithLoginPageList.add("btn_login.png");// 新登录界面
		dispenseWithLoginPageList.add("login_input_bg.gif");// 新登录界面
		dispenseWithLoginPageList.add("code.gif");// 新登录界面
		dispenseWithLoginPageList.add("choose.css");// 安全码界面样式
		dispenseWithLoginPageList.add("choose_bg.jpg");// 安全码界面图片
		dispenseWithLoginPageList.add("choose_background.jpg");// 安全码界面图片
		dispenseWithLoginPageList.add("side_current.jpg");// 安全码界面图片
		dispenseWithLoginPageList.add("code_bg.jpg");// 安全码界面图片
		dispenseWithLoginPageList.add("corner.gif");// 安全码界面图片
		dispenseWithLoginPageList.add("dot.gif");// 安全码界面图片
		dispenseWithLoginPageList.add("code_input.gif");// 安全码界面图片
		dispenseWithLoginPageList.add("code_login.gif");// 安全码界面图片

		dispenseWithLoginPageList.add("loginManager.action");// 管理登陆处理
		dispenseWithLoginPageList.add("loginFailureManager.action");// 管理登陆失败处理
		dispenseWithLoginPageList.add("safetyCodeLogin.action");// 安全码登陆处理
		dispenseWithLoginPageList.add("loginMember.action");// 会员登陆处理
		dispenseWithLoginPageList.add("loginFailureMember.action");// 会员登陆失败处理
		dispenseWithLoginPageList.add("logoutManager.action");// 管理退出
		dispenseWithLoginPageList.add("logoutMember.action");// 会员退出
		dispenseWithLoginPageList.add("code.jsp");// 验证码校验
		dispenseWithLoginPageList.add("codeAdmin.jsp");// 验证码校验
		dispenseWithLoginPageList.add("jquery-1.4.2.min.js");// 验证码校验
		dispenseWithLoginPageList.add("jquery-1.7.2.min.js");// 验证码校验
		dispenseWithLoginPageList.add("jquery.js");// 验证码校验
		dispenseWithLoginPageList.add("ajaxCheckCode.action");// 验证码校验

		// **五洲导航相关START***
		dispenseWithLoginPageList.add("chooseWz.jsp");// 选择线路页面
		dispenseWithLoginPageList.add("wz1.jpg");// 选择线路页面
		dispenseWithLoginPageList.add("wz1.png");// 选择线路页面
		dispenseWithLoginPageList.add("wzphone.png");// 选择线路页面
		// **五洲导航相关END***

		dispenseWithLoginPageList.add("testBalanceInfo.jsp");// 测试负载机器
		// add by peter
		dispenseWithLoginPageList.add("ajaxGetStopLotteryInfo.action");// 单前球号已封盘校验
		dispenseWithLoginPageList.add("loginOut.jsp");// 用户登录页面
		dispenseWithLoginPageList.add("checkCodeMember.jsp");// 验证码页面-会员
		dispenseWithLoginPageList.add("checkCodeManager.jsp");// 验证码页面-管理
		dispenseWithLoginPageList.add("jQuery.md5.js");// MD5 JS
		// dispenseWithLoginPageList.add("");//默认页面
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	/**
	 * 
	 * 校验用户是否登陆
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
	        ServletException {
		boolean isLogin = false;

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

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
				log.info("user request page " + pageName + " not need to login,pass");
				chain.doFilter(request, response);
				return;
			}
		}

		// 如果是请求不需要登陆的访问页面，则无需判断是否登陆
		for (int i = 0; i < dispenseWithLoginPageList.size(); i++) {
			if (((String) dispenseWithLoginPageList.get(i)).trim().equalsIgnoreCase(pageName)) {
				log.info("user request page  " + dispenseWithLoginPageList.get(i));
				chain.doFilter(request, response);
				return;
			}
		}

		// 授权的URL信息
		ArrayList<String> urlList = new ArrayList<String>();

		// 读取管理登陆信息
		ManagerUser userInfoManage = (ManagerUser) request.getSession(true).getAttribute(
		        Constant.MANAGER_LOGIN_INFO_IN_SESSION);

		if (null != userInfoManage) {
			isLogin = true;
			urlList.addAll(userInfoManage.getUrlList());
		}

		if (isLogin) {
			// 判断URL权限
			// 获取可以用来鉴权的URL
			String url = requestURL.replaceAll(request.getContextPath(), "");// 过滤掉工程名
			// 截取url最后一段，系统目前只鉴权Action
			String postfix = "";
			if (url.length() > 5) {
				url = url.trim();
				postfix = url.substring(url.length() - 6);
			}

			String[] urlArray = url.split("/");
			String lastAddress = "";
			String firstAddress = "";
			if (urlArray.length >= 2) {
				lastAddress = urlArray[urlArray.length - 1];
				firstAddress = urlArray[1];
			}
			// log.info("Last URL element = " + lastAddress);
			// log.info(urlArray[1]);

			if (null != userInfoManage && "admin".equals(firstAddress)) {
				// 管理登陆
				ManagerUser userInfo = (ManagerUser) request.getSession(true).getAttribute(
				        Constant.MANAGER_LOGIN_INFO_IN_SESSION);
				String redirectURL = this.getPasswordExpireUrl(userInfo, lastAddress, url, request.getContextPath());
				if (null != redirectURL) {
					response.sendRedirect(redirectURL);
					return;
				}
			}

			// 如果是aciton，则鉴权
			/*if ("action".equalsIgnoreCase(postfix)) {
				if (urlList.contains(url)) {
					// 鉴权成功
					log.info("URL 鉴权成功！");
				} else {
					log.error("URL(" + url + ")鉴权失败！");
					// 鉴权失败
					response.sendRedirect(request.getContextPath() + "/common/authenticationFailed.jsp");
					return;
				}
			} else {
				// TODO 其他后缀的访问处理
			}*/

			// 返回
			chain.doFilter(request, response);
		} else {
			log.info("user request " + pageName + " ; no login!");
			// 如果未登陆，则跳转到登陆页面
			// 如果是ajax 請求返回json 信息

			if (null != request.getHeader("X-Requested-With")
			        && "XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
				Map<String, String> errorMap = new HashMap<String, String>();
				errorMap.put("errorInfo", "notLogin");
				Map<String, Map<String, String>> retMap = new HashMap<String, Map<String, String>>();
				retMap.put("error", errorMap);
				String jsonOdd = JSONObject.toJSONString(retMap);
				ajaxJson(jsonOdd, response);
			} else {
				
				
				String mangerType = (String) request.getSession(true).getAttribute(
				        Constant.USER_TYPE_LOGIN_IN_SESSION_MANGER);
				 if (StringUtils.isNotEmpty(mangerType)) {//有其他人登录
					response.sendRedirect(request.getContextPath() + "/common/loginOutByBoss.jsp");
				} else {
					
					response.sendRedirect(request.getContextPath() + "/bossLogin.jsp");
				}
			}
		}
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {

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
	private String getPasswordExpireUrl(Object userInfo, String lastAddress, String url, String contextPath) {
		List<String> actionList = new ArrayList<String>();
		actionList.add("enterChangePassword.action");
		actionList.add("changePassword.action");
		actionList.add("updateFindPassword.action");
		actionList.add("updatePassword.action");
		actionList.add("ajaxQueryQianPassword.action");
		actionList.add("ajaxQueryPassword.action");
		actionList.add("enterChangePassword.do");
		actionList.add("changePassword.do");
		actionList.add("updateFindPassword.do");
		actionList.add("updatePassword.do");
		actionList.add("ajaxQueryQianPassword.do");
		actionList.add("ajaxQueryPassword.do");

		Date passwordExpireDate = null;
		String passwordResetFlag = null;
		String redirectUrl = null;
		if (null != userInfo) {
		
				passwordExpireDate = DateUtils.addDays(((ManagerUser) userInfo).getPasswordUpdateDate(),
				        PASSWORD_EXPIRE_DAYS);
				passwordResetFlag = ((ManagerUser) userInfo).getPasswordResetFlag();
				redirectUrl = contextPath + "/user/updateFindPassword.action";
			
			// add by peter,检测用户密码的更新时间是否超过15日,如果超过则强制用户必须要修改密码才能登陆
			if (null != passwordExpireDate && new Date().compareTo(passwordExpireDate) >= 0) {
				if (!actionList.contains(lastAddress) && (url.endsWith(".action") || url.endsWith(".do"))) {
					redirectUrl = redirectUrl + "?isPasswordExpire=Y";
					return redirectUrl;
				}
			} else if ("Y".equals(passwordResetFlag)) {
				if (!actionList.contains(lastAddress) && (url.endsWith(".action") || url.endsWith(".do"))) {
					redirectUrl = redirectUrl + "?isPasswordReset=Y";
					return redirectUrl;
				}
			}
		}
		return null;

	}
}
