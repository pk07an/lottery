package com.npc.lottery.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npc.lottery.boss.entity.ShopsInfo;
import com.npc.lottery.sysmge.entity.LoginLogInfo;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.sysmge.entity.MemberStaff;
import com.npc.lottery.sysmge.entity.MemberUser;
import com.npc.lottery.user.entity.ChiefStaffExt;
import com.npc.lottery.user.entity.MemberStaffExt;
import com.npc.lottery.util.WebTools;

@Service
public class LoginService {
	@Autowired
	private UserService userService;

	/**
	 * 校验用户登录结果
	 * 
	 * @param safetyCode
	 *            安全码
	 * @param userName
	 *            登录用户名
	 * @param userPwd
	 *            登录密码
	 * @return 返回 MemberUser 对象，根据 MemberUser.loginState 值判断登陆结果
	 *         MemberUser.LOGIN_STATE_SUCCESS_NORMAL 正常登录成功
	 *         MemberUser.LOGIN_STATE_FAILURE_INEXIST 用户信息不存在
	 *         MemberUser.LOGIN_STATE_FAILURE_PWD_INCORRECT 用户密码不正确
	 */
	public MemberUser verifyLogin(String userName, String userPwd, String scheme) {
		// 1、校验用户是否存在

		// 读取用户信息
		MemberUser memberUser = userService.getMemberUserByName(userName, scheme);
		if (null == memberUser) {
			memberUser = new MemberUser();
			// 设置登陆状态并返回
			memberUser.setLoginState(MemberUser.LOGIN_STATE_FAILURE_USER_INEXIST);
			return memberUser;
		} else {

			String userPwdDb = memberUser.getUserPwd();
			// 判断用户的状态是否为禁用
			if (MemberStaff.FLAG_FORBID.equalsIgnoreCase(memberUser.getFlag())) {
				// 用户禁用
				memberUser.setLoginState(MemberUser.LOGIN_STATE_FAILURE_USER_FORBID);
			}
			// 2、校验用户密码
			else if (StringUtils.isEmpty(userPwdDb) || StringUtils.isEmpty(userPwd)) {
				memberUser.setLoginState(MemberUser.LOGIN_STATE_FAILURE_PWD_INCORRECT);
			} else if (!(userPwdDb.trim().equalsIgnoreCase(userPwd.trim()))) {
				userPwdDb = userPwdDb.trim();
				memberUser.setLoginState(MemberUser.LOGIN_STATE_FAILURE_PWD_INCORRECT);
			} else {
				// 3、登陆成功，初始化用户信息
				MemberStaffExt memberStaffExt = userService.getMemberStaffExtById(memberUser.getID(), scheme);
				memberUser.setMemberStaffExt(memberStaffExt);
				ChiefStaffExt chief = userService.getChiefStaffExtById(memberStaffExt.getChiefStaff(), scheme);
				if (null != chief) {
					ShopsInfo shopinfo = userService.getShopInfoByChiefStaffExt(chief);
					memberUser.setShopsInfo(shopinfo);
				}
				// 判断用户的状态是否为冻结
				if (ManagerStaff.FLAG_FREEZE.equalsIgnoreCase(memberUser.getFlag())) {
					// 用户冻结
					memberUser.setLoginState(ManagerUser.LOGIN_STATE_FAILURE_USER_FREEZE);

				} else {
					// 设置登陆状态为正常登陆
					memberUser.setLoginState(MemberUser.LOGIN_STATE_SUCCESS_NORMAL);
				}
			}
		}
		return memberUser;
	}

	public void logLoginInfo(long userID, String account, String userType, HttpServletRequest request, String scheme) {
		LoginLogInfo logLogInfo = new LoginLogInfo();
		logLogInfo.setUserId(userID);// 用户ID
		logLogInfo.setAccount(account);// 用户账号
		String userIP = WebTools.getClientIpAddr(request);

		logLogInfo.setLoginIp(userIP);// 登陆IP
		logLogInfo.setUserType(userType);// 用户类型
		logLogInfo.setLoginDate(new Date(System.currentTimeMillis()));// 登陆时间
		userService.saveLoginLogInfo(logLogInfo, scheme);
	}

}
