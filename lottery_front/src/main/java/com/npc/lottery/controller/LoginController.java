package com.npc.lottery.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import com.npc.lottery.boss.entity.ShopsInfo;
import com.npc.lottery.common.Constant;
import com.npc.lottery.common.ResultObject;
import com.npc.lottery.common.service.ShopSchemeService;
import com.npc.lottery.manage.entity.ShopsDeclaratton;
import com.npc.lottery.manage.logic.interf.IShopsDeclarattonLogic;
import com.npc.lottery.service.CookieService;
import com.npc.lottery.service.LoginService;
import com.npc.lottery.service.MemCachedService;
import com.npc.lottery.service.OnlineMemberService;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.sysmge.entity.MemberUser;
import com.npc.lottery.util.Tools;
import com.npc.lottery.util.WebTools;

@Controller
public class LoginController {
	// @Autowired
	// private ILoginLogInfoLogic loginLogInfoLogic;
	@Autowired
	private CookieService cookieService;
	@Autowired
	private MemCachedService memCachedService;
	@Autowired
	private IShopsDeclarattonLogic shopsDeclarattonLogic;
	@Autowired
	private WebTools webTools;
	@Autowired
	private LoginService loginService;
	@Autowired
	private OnlineMemberService onlineMemberService;
	private static final Logger log = Logger.getLogger(LoginController.class);

	@RequestMapping(value = "/memberLogin.xhtml")
	public ModelAndView memberLogin(HttpServletResponse response, @RequestParam(value = "shopcode", defaultValue = "", required = true) String shopCode) {
		ModelAndView mv = new ModelAndView();
		if (StringUtils.isEmpty(shopCode)) {
			// 重定向到商铺选择页面
			mv.setView(new RedirectView("/"));
			return mv;
		}
		mv.addObject("shopCode", shopCode);
		mv.setViewName("memberLogin");
		return mv;
	}

	@Autowired
	private ShopSchemeService shopSchemeService;

	/**
	 * 会员登陆
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/login.xhtml")
	public ModelAndView login(String userName, String userPwd, @RequestParam(value = "shopcode", defaultValue = "", required = true) String shopCode,
	        @RequestParam(value = "code", defaultValue = "", required = true) String code, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String contextPath = request.getContextPath();
		ModelAndView mv = new ModelAndView();

		String scheme = shopSchemeService.getSchemeByShopCode(shopCode);
		if (StringUtils.isEmpty(shopCode) || StringUtils.isEmpty(scheme)) {
			log.error("安全码不存在，登陆失败！");
			mv.setView(new RedirectView(contextPath + "/loginFailure.xhtml?ERROR_TYPE=safetyCodeUnexist&shopcode=" + shopCode));
			return mv;
		}

		// 读取session中验证码
		String codeNO = (String) request.getSession(true).getAttribute(Constant.LOGIN_CODE);
		if (StringUtils.isEmpty(code) || (StringUtils.isNotEmpty(codeNO) && !codeNO.equals(code))) {
			log.error("验证码为空，登陆失败！");
			mv.setView(new RedirectView(contextPath + "/loginFailure.xhtml?ERROR_TYPE=checkCodeUnexist&shopcode=" + shopCode));
			return mv;
		}

		// 登录---这里有bug，需要通过传的shopCode＋用户account去找用户信息，并匹配密码，带用户表增加shopCode字段后优化
		MemberUser user = loginService.verifyLogin(userName, userPwd, scheme);

		// 判断是否登录成功
		if (user.isLogin()) {
			ShopsInfo shopsInfo = user.getShopsInfo();
			if (null != shopsInfo) {
				String shopState = shopsInfo.getState();
				if (!shopsInfo.getShopsCode().equals(shopCode)) {
					// 如果用户查出来的商铺号与前端传过来的不一致，认为是非法的登录请求
					log.error("用户与安全码不匹配，登陆失败！");
					mv.setView(new RedirectView(contextPath + "/loginFailure.xhtml?ERROR_TYPE=safetyCodeError&shopcode=" + shopCode));
				} else if (Constant.SHOP_FREEZE.equalsIgnoreCase(shopState)) {
					mv.setView(new RedirectView(contextPath + "/loginFailure.xhtml?ERROR_TYPE=shopFreeze&shopcode=" + shopCode));
				} else if (Constant.SHOP_CLOSE.equalsIgnoreCase(shopState)) {
					mv.setView(new RedirectView(contextPath + "/loginFailure.xhtml?ERROR_TYPE=shopClose&shopcode=" + shopCode));

				} else {
					// 1.注入cookie
					String date = new SimpleDateFormat("yyyyMMdd:HH:MM:ss:SSS").format(new Date());
					cookieService.injectMemberUser(request, response, user, shopsInfo.getShopsCode().trim(), date);
					// 2.保存用户信息到memchached
					memCachedService.setMemberUserModel(user);
					// TODO 登陆日志，填充其他更完善的信息
					loginService.logLoginInfo(user.getID(), user.getAccount(), user.getUserType(), request, scheme);

					// 登陆成功
					String account = user.getAccount();
					log.info("【" + account + "（" + shopCode + "） ip:" + WebTools.getClientIpAddr(request) + "登录成功！】");

					View successView = new RedirectView(contextPath + "/" + "F_" + Tools.encodeWithKey(date + "_" + user.getID() + ":shopcode:" + shopCode) + "/queryMemberWarmomgStatent.xhtml");
					mv.setView(successView);
				}
			} else {
				// 商铺不存在
				log.error("商铺不存在，登陆失败！");
				mv.setView(new RedirectView(contextPath + "/loginFailure.xhtml?ERROR_TYPE=safetyCodeUnLogin&shopcode=" + shopCode));

			}
		} else {
			View errorView = this.getErrorView(user.getLoginState(), user.getAccount(), shopCode, contextPath);
			mv.setView(errorView);
		}

		return mv;
	}

	/**
	 * 会员登出
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/{path}/logout.xhtml")
	public String logout(HttpServletRequest request, HttpServletResponse response, @PathVariable String path) throws Exception {
		String shopCode = WebTools.getShopCodeByPath(path);
		String uid = cookieService.getCookieValue(Constant.COOKIE_UID, request);
		if (StringUtils.isNotEmpty(uid)) {
			// 清理缓存
			// 清理memcache的用户信息
			memCachedService.deleteCache(MemCachedService.Key_Prefix_UID_MEMBER + uid);
			// 清理memcache用户的token信息
			memCachedService.deleteCache(MemCachedService.Key_Prefix_TOKEN + uid + "_" + shopCode);
			// 清理memcache用户的ckcv信息
			memCachedService.deleteCache(MemCachedService.Key_Prefix_CKCV + "_" + uid);
			// 清理memcached里的登录状态
			onlineMemberService.logoutOnlineMemberInCache(uid, shopCode);
			log.error("【" + uid + "）退出！】");
		}

		// 清理cookies
		cookieService.clear(request, response);
		// 清理session
		WebTools.setSessionToken(uid, shopCode, request, "");

		return "redirect:/memberLogin.xhtml?shopcode=" + shopCode;
	}

	/**
	 * 会员踢出
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/{path}/kickout.xhtml")
	public String kickout(HttpServletRequest request, HttpServletResponse response, @PathVariable String path) throws Exception {

		String shopCode = WebTools.getShopCodeByPath(path);
		String uid = cookieService.getCookieValue(Constant.COOKIE_UID, request);
		// 清理cookies
		cookieService.clear(request, response);
		// 清理session
		WebTools.setSessionToken(uid, shopCode, request, "");
		request.setAttribute("shopCode", shopCode);
		return "kickout";
	}

	/**
	 * 会员踢出
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/{path}/kickout.json")
	@ResponseBody
	public ResultObject kickoutJson(HttpServletRequest request, HttpServletResponse response, @PathVariable String path) throws Exception {
		String shopCode = WebTools.getShopCodeByPath(path);
		String uid = cookieService.getCookieValue(Constant.COOKIE_UID, request);
		// 清理cookies
		cookieService.clear(request, response);
		// 清理session
		WebTools.setSessionToken(uid, shopCode, request, "");

		ResultObject rs = new ResultObject();
		Map<String, String> errorMap = new HashMap<String, String>();
		errorMap.put("errorInfo", "kickout");
		Map<String, Map<String, String>> retMap = new HashMap<String, Map<String, String>>();
		retMap.put("error", errorMap);
		rs.setData(retMap);
		return rs;
	}

	/**
	 * 
	 * 功能描述：登陆后跳出提示信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/{path}/queryMemberPopupMenus.xhtml")
	public String queryMemberPopupMenus(HttpServletRequest request, @PathVariable String path) {
		MemberUser memberUser = webTools.getCurrentMemberUserByCookieUid(request);
		if (null != memberUser) {
			ShopsDeclaratton declaratton = shopsDeclarattonLogic.queryByMemberPopupMenusDeclaratton(new Date(), memberUser);
			String content = declaratton.getContentInfo();
			if (content != null) {
				content = content.replace("\r", "\\r").replace("\n", "\\n");
			}
			request.setAttribute("contentInfo", content);
		} else {
			request.setAttribute("contentInfo", "没有找到该用户信息");
		}
		request.setAttribute("path", path);
		return "queryMemberPopupMenus";
	}

	/**
	 * 
	 * 功能描述：登陆后提醒页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/{path}/queryMemberWarmomgStatent.xhtml")
	public ModelAndView queryMemberWarmomgStatent(@PathVariable String path, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("path", path);
		MemberUser memberUser = webTools.getCurrentMemberUserByCookieUid(request);
		if (null != memberUser) {
			ShopsInfo shop = memberUser.getShopsInfo();
			if (null != shop) {
				mv.addObject("managerName", shop.getShopsName());
			}
		}

		mv.addObject("shopCode", WebTools.getShopCodeByPath(path));
		mv.setViewName("queryMemberWarmomgStatent");
		return mv;
	}

	/**
	 * 
	 * 功能描述：登陆失败异常
	 * 
	 * @param ERROR_TYPE
	 * @param userName
	 * @return
	 */
	@RequestMapping(value = "/loginFailure.xhtml")
	public String loginFailure(String ERROR_TYPE, String userName, HttpServletRequest request, @RequestParam(value = "shopcode", defaultValue = "", required = true) String shopCode) {

		log.error("ERROR_TYPE = " + ERROR_TYPE);

		if ("userUnexist".equalsIgnoreCase(ERROR_TYPE)) {
			// 设置返回页面提示信息
			request.setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
			request.setAttribute(Constant.INFO_PAGE_MESSAGE, "'抱歉！你輸入帳號或密碼錯誤，請查正后重新登錄。'");
		} else if ("pwdIncorrect".equalsIgnoreCase(ERROR_TYPE)) {
			// 设置返回页面提示信息
			request.setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
			request.setAttribute(Constant.INFO_PAGE_MESSAGE, "'抱歉！你輸入帳號或密碼錯誤，請查正后重新登錄。'");
		}

		else if ("userForbid".equalsIgnoreCase(ERROR_TYPE)) {
			// 设置返回页面提示信息
			request.setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
			request.setAttribute(Constant.INFO_PAGE_MESSAGE, "'抱歉！您的帳戶已被禁止使用，請和您的上線聯繫。'");
		} else if ("userFreeze".equalsIgnoreCase(ERROR_TYPE)) {
			// 设置返回页面提示信息
			request.setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
			request.setAttribute(Constant.INFO_PAGE_MESSAGE, "'抱歉！您的帳戶已被凍結（只限查帳功能可用），請和代理商聯繫。'");
			String path = cookieService.getCookieValue(Constant.COOKIE_PATH, request);
			request.setAttribute(Constant.COOKIE_PATH, path);
			request.setAttribute("userState", "userFreeze"); // 用户被冻结后是可以登录的，但限制部份功能
		} else if ("userAlreadyLoginLocal".equalsIgnoreCase(ERROR_TYPE)) {
			// 设置返回页面提示信息
			request.setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
			request.setAttribute(Constant.INFO_PAGE_MESSAGE, "'抱歉！当前用户： " + request.getParameter("userName") + " 已经登录,请退出或者新建会话再登录'");
		}
		// 商铺状态异常
		else if ("safetyCodeUnexist".equalsIgnoreCase(ERROR_TYPE)) {
			// 设置返回页面提示信息
			request.setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
			request.setAttribute(Constant.INFO_PAGE_MESSAGE, "安全码不存在，请与系统管理员联系！");
			request.setAttribute("shopState", "shopException");
		} else if ("safetyCodeUnLogin".equalsIgnoreCase(ERROR_TYPE)) {
			// 设置返回页面提示信息
			request.setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
			request.setAttribute(Constant.INFO_PAGE_MESSAGE, "请使用安全码登陆，安全码可通过系统管理员处获取！");
			request.setAttribute("shopState", "shopException");
		} else if ("safetyCodeError".equalsIgnoreCase(ERROR_TYPE)) {
			// 设置返回页面提示信息
			request.setAttribute(Constant.INFO_PAGE_TYPE_RETURN, "true");
			request.setAttribute(Constant.INFO_PAGE_MESSAGE, "用户与安全码不匹配，登陆失败！");
			request.setAttribute("shopState", "shopException");
		} else if ("shopFreeze".equalsIgnoreCase(ERROR_TYPE)) {
			// 设置返回页面提示信息
			request.setAttribute(Constant.INFO_PAGE_TYPE_RETURN, "true");
			request.setAttribute(Constant.INFO_PAGE_MESSAGE, "安全码已被冻结，登陆失败！");
			request.setAttribute("shopState", "shopException");
		} else if ("shopClose".equalsIgnoreCase(ERROR_TYPE)) {
			// 设置返回页面提示信息
			request.setAttribute(Constant.INFO_PAGE_TYPE_RETURN, "true");
			request.setAttribute(Constant.INFO_PAGE_MESSAGE, "安全码已关闭，登陆失败！");
			request.setAttribute("shopState", "shopException");
		} else if ("checkCodeUnexist".equalsIgnoreCase(ERROR_TYPE)) {
			request.setAttribute(Constant.INFO_PAGE_TYPE_RETURN, "true");
			request.setAttribute(Constant.INFO_PAGE_MESSAGE, "验证码错误");
		}

		if (StringUtils.isNotEmpty(shopCode)) {
			request.setAttribute("shopCode", shopCode);
		}
		return "loginFailure";
	}

	@RequestMapping("/checkCodePage.xhtml")
	public String getCheckCodePage() {
		return "checkCodePage";
	}

	private View getErrorView(int loginState, String account, String shopCode, String contextPath) {
		View view = null;

		if (ManagerUser.LOGIN_STATE_FAILURE_USER_FREEZE == loginState) {
			// 用户已被冻结
			log.error("用户已被冻结，登陆成功！");
			view = new RedirectView(contextPath + "/loginFailure.xhtml?ERROR_TYPE=userFreeze&shopcode=" + shopCode);
		} else if (MemberUser.LOGIN_STATE_FAILURE_USER_INEXIST == loginState) {
			// 用户不存在
			log.error("用户不存在，登陆失败！");
			view = new RedirectView(contextPath + "/loginFailure.xhtml?ERROR_TYPE=userUnexist&shopcode=" + shopCode);
		} else if (MemberUser.LOGIN_STATE_FAILURE_PWD_INCORRECT == loginState) {
			// 密码校验错误
			log.error("用户密码错误，登陆失败！");
			view = new RedirectView(contextPath + "/loginFailure.xhtml?ERROR_TYPE=pwdIncorrect&shopcode=" + shopCode);
		} else if (ManagerUser.LOGIN_STATE_FAILURE_USER_FORBID == loginState) {
			// 用户已被禁用
			log.error("用户已被禁用，登陆失败！");
			view = new RedirectView(contextPath + "/loginFailure.xhtml?ERROR_TYPE=userForbid&shopcode=" + shopCode);
		} else {
			view = new RedirectView(contextPath + "/loginFailure.xhtml?ERROR_TYPE=userUnexist&shopcode=" + shopCode);
		}

		return view;

	}

	@RequestMapping("/notLogin.xhtml")
	public String noLogin(@RequestParam(value = "shopcode", defaultValue = "") String shopCode, HttpServletRequest request) {
		request.setAttribute("shopCode", shopCode);
		return "notLogin";
	}

}
