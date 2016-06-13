package com.npc.lottery.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.npc.lottery.common.Constant;
import com.npc.lottery.common.ResultObject;
import com.npc.lottery.common.service.ShopSchemeService;
import com.npc.lottery.service.CookieService;
import com.npc.lottery.service.UserService;
import com.npc.lottery.sysmge.entity.MemberStaff;
import com.npc.lottery.sysmge.entity.MemberUser;
import com.npc.lottery.util.WebTools;
import com.opensymphony.oscache.util.StringUtil;

@Controller
public class PasswordChangeController {

	private Logger logger = Logger.getLogger(PasswordChangeController.class);
	@Autowired
	private WebTools webTools;
	@Autowired
	private UserService userService;
	@Autowired
	private CookieService cookieService;
	@Autowired
	private ShopSchemeService shopSchemeService;

	@RequestMapping("{path}/enterChangePassword.xhtml")
	public String enter(HttpServletRequest request, @PathVariable String path) {
		if (!StringUtil.isEmpty(request.getParameter("isPasswordExpire"))) {
			request.setAttribute("isPasswordExpire", request.getParameter("isPasswordExpire"));
		}
		if (!StringUtil.isEmpty(request.getParameter("isPasswordReset"))) {
			request.setAttribute("isPasswordReset", request.getParameter("isPasswordReset"));
		}
		request.setAttribute("path", path);
		String shopCode = WebTools.getShopCodeByPath(path);
		if (StringUtils.isNotEmpty(shopCode)) {
			request.setAttribute("shopCode", shopCode);
		}
		return "passwordChange";
	}

	// 前台用户密码
	@ResponseBody
	@RequestMapping("{path}/ajaxQueryQianPassword.xhtml")
	public ResultObject ajaxQueryQianPassword(HttpServletRequest request, HttpServletResponse response, @PathVariable String path, String pwd) {
		ResultObject resultObject = null;
		request.setAttribute("path", path);
		MemberStaff memberStaff = webTools.getCurrentMemberUserByCookieUid(request);
		String userPwdOrignMd5 = pwd;
		Map<String, String> map = new HashMap<String, String>();
		String sCount;
		if (userPwdOrignMd5.equals(memberStaff.getUserPwd())) {
			sCount = "0";
		} else {
			sCount = "1";
		}
		map.put("count", sCount);
		resultObject = new ResultObject(map);
		return resultObject;
	}

	/**
	 * 修改密碼
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("{path}/changePassword.xhtml")
	public ResultObject change(HttpServletRequest request, @PathVariable String path, String newPassword, String newPasswordOne, String userOldPassword) {
		ResultObject resultObject = new ResultObject();
		try {
			if (!newPassword.equals(newPasswordOne)) {
				resultObject.setErrorCode(-1);
				resultObject.setErrorMsg("兩次密碼不一樣！");
			}
			String uid = cookieService.getCookieValue(Constant.COOKIE_UID, request);
			String scheme = shopSchemeService.getSchemeByShopCode(WebTools.getShopCodeByPath(path));
			MemberStaff memberStaff = userService.getMemberUserStaffById(Long.valueOf(uid), scheme);

			if (null != memberStaff) {
				String userOldPwdOrignMd5 = userOldPassword;
				// 與數據庫對比舊
				if (!memberStaff.getUserPwd().equals(userOldPwdOrignMd5)) {
					resultObject.setErrorCode(-1);
					resultObject.setErrorMsg("舊密碼不一樣！");
				} else {
					userService.updateMemberStaffPasswordById(memberStaff.getID(), newPasswordOne, scheme);
				}
				resultObject.setErrorCode(0);
				resultObject.setErrorMsg("密码修改成功,请重新登录");
			}
		} catch (Exception e) {
			logger.error("执行" + this.getClass().getSimpleName() + "中的方法updatePassword时出现错误 " + e.getMessage());
			resultObject.setErrorCode(-1);
			resultObject.setErrorMsg("密码修改失败");
		}
		return resultObject;
	}
}
