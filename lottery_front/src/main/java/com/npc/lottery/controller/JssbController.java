package com.npc.lottery.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.npc.lottery.common.Constant;
import com.npc.lottery.common.ResultObject;
import com.npc.lottery.common.service.ShopSchemeService;
import com.npc.lottery.member.entity.BaseBet;
import com.npc.lottery.odds.entity.ShopsPlayOdds;
import com.npc.lottery.periods.logic.interf.IJSSBPeriodsInfoLogic;
import com.npc.lottery.service.BettingService;
import com.npc.lottery.service.CommonService;
import com.npc.lottery.service.JssbService;
import com.npc.lottery.service.UserService;
import com.npc.lottery.sysmge.entity.MemberUser;
import com.npc.lottery.user.entity.MemberStaffExt;
import com.npc.lottery.user.entity.UserCommission;
import com.npc.lottery.user.entity.UserCommissionDefault;
import com.npc.lottery.util.LotteryComparator;
import com.npc.lottery.util.Token;
import com.npc.lottery.util.WebTools;

@Controller
public class JssbController {

	private static final Logger logger = Logger.getLogger(JssbController.class);

	@Autowired
	private WebTools webTools;
	@Autowired
	private IJSSBPeriodsInfoLogic jssbPeriodsInfoLogic;
	@Autowired
	private JssbService jssbService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private ShopSchemeService shopSchemeService;
	@Autowired
	private UserService userService;
	@Autowired
	private BettingService bettingService;

	/**
	 * 江苏投注新方法，性能优化
	 * 
	 * @param request
	 * @param periodsNum
	 * @param cachedOdd
	 * @param path
	 * @return
	 */
	@RequestMapping("/{path}/jssb/submitBet.json")
	@ResponseBody
	@Token(remove = true)
	public ResultObject submitBet(HttpServletRequest request, String periodsNum, String cachedOdd, @PathVariable String path) {
		long startTime = System.currentTimeMillis();
		ResultObject rs = new ResultObject();
		Map<String, String> messageMap = new HashMap<String, String>();
		String shopCode = WebTools.getShopCodeByPath(path);
		Map<String, String> jsp = commonService.getRunningPeriods(Constant.LOTTERY_TYPE_K3, shopCode);

		if (null != jsp && jsp.size() > 0) {
			if (Constant.OPEN_STATUS.equals(jsp.get("State"))) {
				periodsNum = jsp.get("PeriondsNum");
			} else if (Constant.STOP_STATUS.equals(jsp.get("State")) && StringUtils.isEmpty(periodsNum)) {
				rs.setErrorCode(-1);
				rs.setErrorMsg("封盘状态不能投注");
				return rs;

			}

		} else {
			rs.setErrorCode(-2);
			rs.setErrorMsg("还未开盘");
			return rs;

		}
		MemberUser currentUser = webTools.getCurrentMemberUserByCookieUid(request);

		if (null == currentUser) {
			rs.setErrorCode(-6);
			rs.setErrorMsg("获取用户失败");
			return rs;
		} else {
			String scheme = shopSchemeService.getSchemeByShopCode(WebTools.getShopCodeByPath(path));
			if (StringUtils.isEmpty(scheme)) {
				rs.setErrorCode(-7);
				rs.setErrorMsg("用户标识获取失败");
				return rs;
			}

			MemberStaffExt memberStaff = currentUser.getMemberStaffExt();

			Map<String, Integer> rateMap = userService.getUserRateLine(memberStaff, scheme);

			double avalilableCredit = bettingService.getMemberAvailableCreditLineRealTime(memberStaff.getTotalCreditLine().doubleValue(), memberStaff.getID(), scheme);
			Map<String, ShopsPlayOdds> shopMap = commonService.initShopOdds(shopCode, Constant.LOTTERY_TYPE_K3, memberStaff.getPlate());
			Enumeration<String> names = request.getParameterNames();
			List<BaseBet> betList = new ArrayList<BaseBet>();
			String subType = request.getParameter("subType");
			ArrayList<String> plist = Collections.list(names);
			// 排序
			if (subType != null && Constant.LOTTERY_TYPE_K3.equals(subType)) {
				Collections.sort(plist, new LotteryComparator());
			}

			JSONObject cacheJson = JSONObject.parseObject(cachedOdd);

			// 获得清理后待处理的投注数据
			Map<String, String> handlerMap = jssbService.clearData(plist, request);

			// 当次投注总额
			int totalBetPrice = commonService.getTotelBetMoney(handlerMap);
			// 校验投注数据是否超过账户总额
			if (totalBetPrice > avalilableCredit) {
				rs.setErrorCode(-5);
				rs.setErrorMsg("投注超过账户余额");
				return rs;
			}

			if (MapUtils.isEmpty(handlerMap)) {
				rs.setErrorCode(-6);
				rs.setErrorMsg("当前没有待处理的投注数据");
				return rs;
			} else {

				Map<String, UserCommissionDefault> commissionDefaultMap = commonService.queryDefaultCommissionMap("K3%", currentUser.getShopsInfo().getChiefStaffExt().getID(), scheme);
				Map<String, UserCommission> userCommissionMap = commonService.queryUserCommissionMap("K3%", currentUser.getID(), scheme);
				Map<String, BigDecimal> useritemQuotasMoneyMap = commonService.queryUseritemQuotasMoneyMap(Constant.LOTTERY_TYPE_K3, jsp.get("PeriondsNum"), currentUser.getID(), scheme);
				Map<String, BigDecimal> exsitTotalQuatasMap = commonService.queryTotalCommissionMoneyMap(shopCode, "K3%", scheme);

				for (Entry<String, String> entry : handlerMap.entrySet()) {
					String playTypeCode = entry.getKey();
					String price = entry.getValue();
					ShopsPlayOdds shopOdds = shopMap.get(playTypeCode);
					if (Constant.SHOP_PLAY_ODD_STATUS_INVALID.equals(shopOdds.getState())) {
						rs.setErrorCode(-3);
						rs.setErrorMsg("已经封号不能投注");
						return rs;
					}
					BaseBet ballBet = commonService.assemblyBet(playTypeCode, price, periodsNum, rateMap, currentUser, scheme);
					double cahceOdd = cacheJson.getDouble(playTypeCode);
					Map<String, String> errorMap = commonService.betCheck(playTypeCode, price, 1, periodsNum, rateMap, currentUser, commissionDefaultMap, userCommissionMap, useritemQuotasMoneyMap,
					        exsitTotalQuatasMap, scheme);

					if (shopOdds.getRealOdds().doubleValue() != cahceOdd) {
						ballBet.setBetError("OddChanged");
						messageMap.put("errorType", "OddChanged");
					} else {

						String errorType = errorMap.get("errorType");
						if (errorType != null && "ExceedMinMax".equals(errorType)) {
							rs.setErrorCode(-4);
							rs.setErrorMsg(errorMap.get("errorMessage"));
							return rs;
						} else if (errorType != null && "ExceedChief".equals(errorType)) {
							// 总监单期限额
							rs.setErrorCode(-4);
							rs.setErrorMsg(errorMap.get("errorMessage"));
							return rs;
						} else if (errorType != null && "ExceedItem".equals(errorType)) {
							// 超过单期
							ballBet.setBetError("ExceedItem");
						}

					}
					messageMap.putAll(errorMap);

					ballBet.setOdds(shopOdds.getRealOdds());
					betList.add(ballBet);
				}
			}
			logger.info("江苏投注初始化校验结束,投注用户:" + memberStaff.getAccount() + " 投注条数:" + betList.size() + " 耗时:" + (System.currentTimeMillis() - startTime) + "ms");
			if (!messageMap.containsKey("errorMessage") && !messageMap.containsKey("errorType")) {
				long startTime2 = System.currentTimeMillis();
				Integer remainPrice = Double.valueOf(avalilableCredit).intValue() - totalBetPrice;
				messageMap.put("remainPrice", remainPrice.toString());
				bettingService.saveJSSBBet(betList, memberStaff, shopCode, scheme);
				logger.info("江苏投注下单成功,投注用户:" + memberStaff.getAccount() + " 投注条数:" + betList.size() + " 耗时:" + (System.currentTimeMillis() - startTime2) + "ms");
				long startTime3 = System.currentTimeMillis();
				// 異步自動補貨
				bettingService.autoReplenish(betList, memberStaff, scheme);
				userService.updateMemberAvailableCreditLineById(new BigDecimal(avalilableCredit).subtract(new BigDecimal(totalBetPrice)).doubleValue(), memberStaff.getID(), scheme);
				logger.info("江苏投注异步自动补货与更新用户可用额度成功,投注用户:" + memberStaff.getAccount() + " 投注条数:" + betList.size() + " 耗时:" + (System.currentTimeMillis() - startTime3) + "ms");

			}
			Collections.reverse(betList);
			messageMap.put("success", jssbService.ajaxJSSBSubmitResult(periodsNum, betList, totalBetPrice, messageMap));

			rs.setErrorCode(0);
			rs.setData(messageMap);
			return rs;
		}
	}
}
