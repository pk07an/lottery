package com.npc.lottery.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.npc.lottery.common.Constant;
import com.npc.lottery.common.service.ShopSchemeService;
import com.npc.lottery.service.CommissionService;
import com.npc.lottery.service.UserService;
import com.npc.lottery.sysmge.entity.MemberUser;
import com.npc.lottery.user.entity.UserCommission;
import com.npc.lottery.util.WebTools;

@Controller
public class CreditInfoController {

	private Logger logger = Logger.getLogger(CreditInfoController.class);
	@Autowired
	private WebTools webTools;
	@Autowired
	private UserService userService;
	@Autowired
	private CommissionService commissionService;
	@Autowired
	private ShopSchemeService shopSchemeService;

	/**
	 * 信用资料
	 * 
	 * @param request
	 * @return
	 */

	@RequestMapping("/{path}/enterCreditInfo.xhtml")
	public String enter(HttpServletRequest request, @PathVariable String path) {
		// 获取当前用户
		MemberUser memberUser = webTools.getCurrentMemberUserByCookieUid(request);
		Long userId = memberUser.getID();

		String scheme = shopSchemeService.getSchemeByShopCode(WebTools.getShopCodeByPath(path));
		List<UserCommission> userCommission = new ArrayList<UserCommission>();
		if (StringUtils.isNotEmpty(scheme)) {
			MemberUser MemberUserDB = userService.getMemberUserById(userId, scheme);
			String account = MemberUserDB.getAccount();
			String plate = memberUser.getMemberStaffExt().getPlate();
			Integer avalilableCredit = MemberUserDB.getMemberStaffExt().getAvailableCreditLine();
			Integer totalCredit = MemberUserDB.getMemberStaffExt().getTotalCreditLine();
			request.setAttribute("account", account);
			request.setAttribute("totalCredit", totalCredit);
			request.setAttribute("avalilableCredit", avalilableCredit);
			request.setAttribute("plate", plate);
			userCommission = commissionService.getUserCommissionListByUserId(userId, scheme);
		}

		List<UserCommission> gdCommissionless15 = new ArrayList<UserCommission>();
		List<UserCommission> cqCommission1 = new ArrayList<UserCommission>();
		List<UserCommission> bjCommission1 = new ArrayList<UserCommission>();
		// add by peter for K3
		List<UserCommission> jsCommission1 = new ArrayList<UserCommission>();
		// add by peter for NC
		List<UserCommission> ncCommission1 = new ArrayList<UserCommission>();
		for (int i = 0; i < userCommission.size(); i++) {
			UserCommission uc = userCommission.get(i);
			if (Constant.COMMISSION_GD.equals(uc.getPlayType())) {
				gdCommissionless15.add(uc);

			} else if (Constant.COMMISSION_CQ.equals(uc.getPlayType())) {
				cqCommission1.add(uc);

			} else if (Constant.COMMISSION_BJ.equals(uc.getPlayType())) {
				bjCommission1.add(uc);

			} else if (Constant.COMMISSION_JS.equals(uc.getPlayType())) {
				jsCommission1.add(uc);

			} else if (Constant.COMMISSION_NC.equals(uc.getPlayType())) {
				ncCommission1.add(uc);
			}
		}
		request.setAttribute("gdCommissionless15", gdCommissionless15);
		request.setAttribute("cqCommission1", cqCommission1);
		request.setAttribute("bjCommission1", bjCommission1);
		request.setAttribute("jsCommission1", jsCommission1);
		request.setAttribute("ncCommission1", ncCommission1);
		return "creditInformation";
	}

}
