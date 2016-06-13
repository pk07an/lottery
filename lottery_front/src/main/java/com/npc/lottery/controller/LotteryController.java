package com.npc.lottery.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.npc.lottery.boss.entity.ShopsInfo;
import com.npc.lottery.common.Constant;
import com.npc.lottery.common.ResultObject;
import com.npc.lottery.common.service.ShopSchemeService;
import com.npc.lottery.member.entity.PlayType;
import com.npc.lottery.odds.entity.ShopsPlayOdds;
import com.npc.lottery.odds.logic.interf.IShopOddsLogic;
import com.npc.lottery.service.CommissionService;
import com.npc.lottery.service.CommonService;
import com.npc.lottery.service.CookieService;
import com.npc.lottery.service.UserService;
import com.npc.lottery.sysmge.entity.MemberUser;
import com.npc.lottery.user.entity.MemberStaffExt;
import com.npc.lottery.user.entity.UserCommission;
import com.npc.lottery.user.entity.UserCommissionDefault;
import com.npc.lottery.util.PlayTypeUtils;
import com.npc.lottery.util.Token;
import com.npc.lottery.util.WebTools;

import edu.emory.mathcs.backport.java.util.Collections;

@Controller
public class LotteryController {

	private Logger logger = Logger.getLogger(LotteryController.class);
	@Autowired
	private WebTools webTools;
	@Autowired
	private UserService userService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private IShopOddsLogic shopOddsLogic;
	@Autowired
	private CookieService cookieService;
	@Autowired
	private ShopSchemeService shopSchemeService;

	@RequestMapping("/{path}/enterBet.xhtml")
	public ModelAndView enterBet(@PathVariable String path) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("path", path);
		mv.setViewName("frame/main");
		return mv;
	}

	@RequestMapping("/{path}/getToken.xhtml")
	@Token(save = true)
	public String getToken() {
		return "token";
	}

	@RequestMapping("/{path}/leftUser.xhtml")
	public String getleftUser(HttpServletRequest request, @PathVariable String path) {
		String uid = cookieService.getCookieValue(Constant.COOKIE_UID, request);
		String shopCode = WebTools.getShopCodeByPath(path);
		String scheme = shopSchemeService.getSchemeByShopCode(shopCode);
		if (StringUtils.isNotEmpty(uid) && StringUtils.isNotEmpty(scheme)) {
			MemberStaffExt memberStaffExt = userService.getMemberStaffExtById(Long.valueOf(uid), scheme);
			Integer avalilableCredit = memberStaffExt.getAvailableCreditLine();
			Integer totalCredit = memberStaffExt.getTotalCreditLine();
			request.setAttribute("totalCredit", totalCredit);
			request.setAttribute("avalilableCredit", avalilableCredit);
			request.setAttribute("plate", memberStaffExt.getPlate());
			request.setAttribute("account", memberStaffExt.getAccount());
			request.setAttribute("status", memberStaffExt.getFlag());
		}
		request.setAttribute("path", path);
		return "frame/leftUser";
	}

	@RequestMapping("/{path}/topMenu.xhtml")
	public String getTopMenu(HttpServletRequest request, @PathVariable String path) {
		request.setAttribute("path", path);
		request.setAttribute("shopCode", WebTools.getShopCodeByPath(path));
		return "frame/topMenu";
	}

	/**
	 * 获取个人信息
	 * 
	 * @param request
	 * @param path
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/{path}/getSomeOtherAssist.json")
	public ResultObject getSomeOtherAssist(HttpServletRequest request, @PathVariable String path, String onlyMarquee) {
		ResultObject resultObject = new ResultObject();
		MemberUser currentUser = webTools.getCurrentMemberUserByCookieUid(request);
		Map<String, String> retMap = new HashMap<String, String>();
		if (onlyMarquee == null || "0".equals(onlyMarquee)) {
			Map<String, String> marquee = commonService.getShopsDeclarattonContent(currentUser);
			retMap.putAll(marquee);
		}
		String scheme = shopSchemeService.getSchemeByShopCode(WebTools.getShopCodeByPath(path));
		if (StringUtils.isNotEmpty(scheme)) {
			Map<String, String> todayWin = commonService.getTodayWinMoney(currentUser.getID(), scheme);
			retMap.putAll(todayWin);
			MemberStaffExt memberStaff = userService.getMemberStaffExtById(currentUser.getID(), scheme);
			retMap.put("availabalCred", memberStaff.getAvailableCreditLine().toString());
			// add by peter
			retMap.put("totalCredit", memberStaff.getTotalCreditLine().toString());
		}

		resultObject.setData(retMap);
		return resultObject;

	}

	/**
	 * 同步ajax获取盘期信息
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/{path}/getPeriodsInfo.json")
	public ResultObject getPeriodsInfo(HttpServletRequest request, @PathVariable String path, String playType) {
		ResultObject resultObject = new ResultObject();
		MemberUser currentUser = webTools.getCurrentMemberUserByCookieUid(request);
		if (currentUser == null) {
			Map<String, String> errorMap = new HashMap<String, String>();
			errorMap.put("errorInfo", "notLogin");
			Map<String, Map<String, String>> retMap = new HashMap<String, Map<String, String>>();
			retMap.put("error", errorMap);
			resultObject.setData(retMap);
			return resultObject;
		}
		Map<String, String> runningPeriods = new HashMap<String, String>();
		runningPeriods = commonService.getRunningPeriods(playType, WebTools.getShopCodeByPath(path));
		if (MapUtils.isEmpty(runningPeriods)) {
			Map<String, String> noPeriods = new HashMap<String, String>();
			noPeriods.put("runningPeriods", "no");
			resultObject.setData(noPeriods);
			return resultObject;
		}
		Map<String, Map<String, String>> retMap = new HashMap<String, Map<String, String>>();
		Map<String, String> userInfo = new HashMap<String, String>();
		String scheme = shopSchemeService.getSchemeByShopCode(WebTools.getShopCodeByPath(path));
		if (StringUtils.isNotEmpty(scheme)) {
			userInfo.put("userStatus", userService.getMemberFlagById(currentUser.getID(), scheme));
		} else {
			userInfo.put("userStatus", "1");
		}
		retMap.put("userInfo", userInfo);
		retMap.put("runningPeriods", runningPeriods);
		resultObject.setData(retMap);
		return resultObject;
	}

	/**
	 * 快乐十分 1,两面盘，2总和龙虎，3连码4，1-8球 时时彩 1-5球 获取最新赔率
	 * 
	 * @param request
	 * @param path
	 * @param typeCodes
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/{path}/getCurrentRealOdd.json")
	public ResultObject getCurrentRealOdd(HttpServletRequest request, @PathVariable String path, String typeCodes, @RequestParam(value = "playType", required = true) String playTypeCode) {
		ResultObject resultObject = new ResultObject();
		String shopCode = WebTools.getShopCodeByPath(path);
		MemberUser currentUser = webTools.getCurrentMemberUserByCookieUid(request);
		String playTypeCodes = typeCodes;
		String plate = currentUser.getMemberStaffExt().getPlate();
		StringBuffer preAlert = new StringBuffer();
		StringBuffer alert = new StringBuffer();
		int count = 0;
		int totalMoney = 0;
		HashMap<String, String> messageMap = new HashMap<String, String>();
		String[] typeCodesArray = StringUtils.split(playTypeCodes, "*");
		ArrayList<PlayType> playTypes = new ArrayList<PlayType>();
		Map<PlayType, String> moneys = new HashMap<PlayType, String>();
		for (int i = 0; i < typeCodesArray.length; i++) {
			String typeCode = typeCodesArray[i].split("\\@")[0];
			PlayType playType = PlayTypeUtils.getPlayType(typeCode);
			String money = typeCodesArray[i].split("\\@")[1];
			playTypes.add(playType);
			moneys.put(playType, money);
		}
		Collections.sort(playTypes, new PlayTypeCompare());
		Map<String, BigDecimal> plateRealOddsMap = shopOddsLogic.queryPlateRealOddsMap(shopCode, playTypeCode, plate);
		for (int i = 0; i < playTypes.size(); i++) {
			PlayType playType = playTypes.get(i);
			String typeCode = playType.getTypeCode();
			String money = moneys.get(playType);
			String finalType = playType.getPlayFinalType();
			String subName = playType.getSubTypeName();
			if (subName == null)
				subName = "";
			if ("ZM".equals(playType.getPlaySubType()) && ("ZHDA".equals(finalType) || "ZHX".equals(finalType) || "ZHDAN".equals(finalType) || "ZHS".equals(finalType))) {
				subName = "";
			}
			if (("GROUP".equals(playType.getPlaySubType()) && ("DA".equals(finalType) || "X".equals(finalType) || "DAN".equals(finalType) || "S".equals(finalType)))) {
				subName = "";
			}
			String finalName = playType.getFinalTypeName();
			if (subName.length() != 0)
				finalName = "[" + finalName + "]";

			totalMoney = totalMoney + Integer.valueOf(money);
			String realOdds = String.valueOf(plateRealOddsMap.get(typeCode));
			alert.append(" " + subName + finalName + " @ " + realOdds + " × " + " ￥ " + money + "\r\n");
			count++;
		}
		preAlert.append("共 ￥ " + totalMoney + " / " + count + " 笔，确定下注吗？\r\n\r\n下注明细如下:\r\n\r\n");
		messageMap.put("message", preAlert.toString() + alert.toString());
		resultObject.setData(messageMap);
		return resultObject;
	}

	private class PlayTypeCompare implements Comparator<PlayType> {
		@Override
		public int compare(PlayType o1, PlayType o2) {
			try {
				return o1.getDisplayOrder() - o2.getDisplayOrder();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return 0;
		}
	}

	@Autowired
	private CommissionService commissionService;

	/**
	 * 最高派彩
	 * 
	 * @param request
	 * @param path
	 * @param typeCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/{path}/getTopWinPrice.json")
	public ResultObject getTopWinPrice(HttpServletRequest request, @PathVariable String path, String typeCode) {
		ResultObject resultObject = new ResultObject();
		String shopsCode = WebTools.getShopCodeByPath(path);
		MemberUser currentUser = webTools.getCurrentMemberUserByCookieUid(request);
		HashMap<String, String> messageMap = new HashMap<String, String>();
		PlayType playType = PlayTypeUtils.getPlayType(typeCode);

		String scheme = shopSchemeService.getSchemeByShopCode(shopsCode);
		if (StringUtils.isNotEmpty(scheme)) {
			UserCommissionDefault userCommsionDefault = commissionService.getDefaultCommissionByPlayFinalType(playType.getCommissionType(), currentUser.getMemberStaffExt().getChiefStaff());
			UserCommission userCommsion = commissionService.getUserCommissionByPlayFinalType(playType.getCommissionType(), currentUser.getID(), scheme);
			if (null != userCommsion && null != userCommsionDefault) {
				Integer betQuotas = userCommsion.getBettingQuotas();
				Integer winQuatas = userCommsionDefault.getWinQuatas();
				if (winQuatas != null) {
					messageMap.put("success", "true");
					messageMap.put("winQuatas", winQuatas.toString());
				}
				if (betQuotas != null) {
					messageMap.put("betQuotas", betQuotas.toString());
				}
			}
		}
		resultObject.setData(messageMap);
		return resultObject;
	}

	/**
	 * 前台获取当期球数已封盘信息
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/{path}/getStopLotteryInfo.json")
	public ResultObject getStopLotteryInfo(HttpServletRequest request, @PathVariable String path, String playTypePerfix) {
		ResultObject resultObject = new ResultObject();
		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();

		ShopsInfo shopsInfo = webTools.getCurrentMemberUserByCookieUid(request).getShopsInfo();
		if (null != shopsInfo && StringUtils.isNotEmpty(shopsInfo.getShopsCode()) && StringUtils.isNotEmpty(playTypePerfix)) {
			String shopCode = WebTools.getShopCodeByPath(path);
			// 找出已经封盘的球号
			List<ShopsPlayOdds> shopsPlayOddsList = shopOddsLogic.getStopShopsPlayOddsByShopCodeAndPlayTypePerfix(shopCode, playTypePerfix);
			for (ShopsPlayOdds shopsPlayOdds : shopsPlayOddsList) {
				Map<String, String> resultMap = new HashMap<String, String>();
				resultMap.put("key", shopsPlayOdds.getPlayTypeCode());
				resultMap.put("value", "Y");
				resultList.add(resultMap);
			}

		}
		resultObject.setData(resultList);
		return resultObject;
	}

	/**
	 * 异步方法，获取赔率
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/{path}/getRealOdd.json")
	public ResultObject getRealOdds(HttpServletRequest request, String type, @RequestParam(value = "playType", defaultValue = "GDKLSF") String playType, @PathVariable String path) {
		ResultObject resultObject = new ResultObject();

		String shopCode = WebTools.getShopCodeByPath(path);
		playType = playType.toUpperCase();
		Map<String, Object> retMap = new HashMap<String, Object>();
		Map<String, String> jsonOddMap = new HashMap<String, String>();
		String state = "-1";
		if (StringUtils.isNotEmpty(type)) {
			MemberUser user = webTools.getCurrentMemberUserByCookieUid(request);
			if (null != user) {
				Map<String, String> runningPeriods = commonService.getRunningPeriods(playType, WebTools.getShopCodeByPath(path));
				if (MapUtils.isNotEmpty(runningPeriods)) {
					state = runningPeriods.get("State");
					if (!"3".equals(state)) {
						//如果不是封盘的,查询当前的赔率
						Map<String, ShopsPlayOdds> oddMap = commonService.initShopOdds(shopCode, playType, user.getMemberStaffExt().getPlate());
						jsonOddMap = commonService.coventMap(oddMap, playType, type);
					}
				}
			}
		}
		retMap.put("realOdd", jsonOddMap);
		retMap.put("state", state);
		resultObject.setData(retMap);

		return resultObject;
	}
}
