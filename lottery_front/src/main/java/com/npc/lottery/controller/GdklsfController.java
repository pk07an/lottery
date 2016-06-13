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
import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.incrementer.OracleSequenceMaxValueIncrementer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.npc.lottery.common.Constant;
import com.npc.lottery.common.ResultObject;
import com.npc.lottery.common.service.ShopSchemeService;
import com.npc.lottery.member.entity.BaseBet;
import com.npc.lottery.odds.entity.ShopsPlayOdds;
import com.npc.lottery.service.BettingService;
import com.npc.lottery.service.CommonService;
import com.npc.lottery.service.GdklsfService;
import com.npc.lottery.service.UserService;
import com.npc.lottery.sysmge.entity.MemberUser;
import com.npc.lottery.user.entity.MemberStaffExt;
import com.npc.lottery.user.entity.UserCommission;
import com.npc.lottery.user.entity.UserCommissionDefault;
import com.npc.lottery.util.PlayTypeUtils;
import com.npc.lottery.util.SpringUtil;
import com.npc.lottery.util.Token;
import com.npc.lottery.util.Tool;
import com.npc.lottery.util.WebTools;

@Controller
public class GdklsfController {

	private static final Logger logger = Logger.getLogger(GdklsfController.class);
	@Autowired
	private WebTools webTools;
	@Autowired
	private GdklsfService gdklsfService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private ShopSchemeService shopSchemeService;
	@Autowired
	private UserService userService;
	@Autowired
	private BettingService bettingService;

	/**
	 * 广东投注新方法，性能优化
	 * 
	 * @param request
	 * @param periodsNum
	 * @param cachedOdd
	 * @return
	 */
	@RequestMapping("/{path}/gdklsf/submitBet.json")
	@ResponseBody
	@Token(remove = true)
	public ResultObject submitBet(HttpServletRequest request, String periodsNum, String cachedOdd, @PathVariable String path) {
		long startTime = System.currentTimeMillis();
		ResultObject rs = new ResultObject();

		Map<String, String> gdp = commonService.getRunningPeriods(Constant.LOTTERY_TYPE_GDKLSF, "");

		if (null != gdp && gdp.size() > 0) {
			if (Constant.OPEN_STATUS.equals(gdp.get("State"))) {
				periodsNum = gdp.get("PeriondsNum");
			} else if (Constant.STOP_STATUS.equals(gdp.get("State")) && StringUtils.isEmpty(periodsNum)) {
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
			Map<String, String> messageMap = new HashMap<String, String>();
			String shopCode = currentUser.getShopsInfo().getShopsCode();
			MemberStaffExt memberStaffExt = currentUser.getMemberStaffExt();

			double avalilableCredit = bettingService.getMemberAvailableCreditLineRealTime(memberStaffExt.getTotalCreditLine().doubleValue(), memberStaffExt.getID(), scheme);

			Map<String, Integer> rateMap = userService.getUserRateLine(memberStaffExt, scheme);
			List<BaseBet> betList = new ArrayList<BaseBet>();
			Map<String, ShopsPlayOdds> shopMap = commonService.initShopOdds(shopCode, Constant.LOTTERY_TYPE_GDKLSF, memberStaffExt.getPlate());
			JSONObject cacheJson = JSONObject.parseObject(cachedOdd);

			Enumeration<String> names = request.getParameterNames();
			ArrayList<String> plist = Collections.list(names);

			// 获得清理后待处理的投注数据
			Map<String, String> handlerMap = gdklsfService.clearData(plist, request);

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

				Map<String, UserCommissionDefault> commissionDefaultMap = commonService.queryDefaultCommissionMap("GD%", currentUser.getShopsInfo().getChiefStaffExt().getID(), scheme);
				Map<String, UserCommission> userCommissionMap = commonService.queryUserCommissionMap("GD%", currentUser.getID(), scheme);
				Map<String, BigDecimal> useritemQuotasMoneyMap = commonService.queryUseritemQuotasMoneyMap(Constant.LOTTERY_TYPE_GDKLSF, gdp.get("PeriondsNum"), currentUser.getID(), scheme);
				Map<String, BigDecimal> exsitTotalQuatasMap = commonService.queryTotalCommissionMoneyMap(shopCode, "GDKLSF%", scheme);

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
			logger.info("广东投注初始化校验结束,投注用户:" + currentUser.getAccount() + " 投注条数:" + betList.size() + " 耗时:" + (System.currentTimeMillis() - startTime) + "ms");
			if (!messageMap.containsKey("errorMessage") && !messageMap.containsKey("errorType")) {
				long startTime2 = System.currentTimeMillis();
				Integer remainPrice = Double.valueOf(avalilableCredit).intValue() - totalBetPrice;
				messageMap.put("remainPrice", remainPrice.toString());
				bettingService.saveGDBet(betList, memberStaffExt, shopCode, scheme);
				logger.info("广东投注下单成功,投注用户:" + currentUser.getAccount() + " 投注条数:" + betList.size() + " 耗时:" + (System.currentTimeMillis() - startTime2) + "ms");
				long startTime3 = System.currentTimeMillis();
				// 異步自動補貨
				bettingService.autoReplenish(betList, memberStaffExt, scheme);
				userService.updateMemberAvailableCreditLineById(new BigDecimal(avalilableCredit).subtract(new BigDecimal(totalBetPrice)).doubleValue(), currentUser.getID(), scheme);
				logger.info("广东投注异步自动补货与更新用户可用额度成功,投注用户:" + currentUser.getAccount() + " 投注条数:" + betList.size() + " 耗时:" + (System.currentTimeMillis() - startTime3) + "ms");

			}
			Collections.reverse(betList);
			messageMap.put("success", gdklsfService.ajaxGDSubmitResult(periodsNum, betList, totalBetPrice, messageMap));
			rs.setErrorCode(0);
			rs.setData(messageMap);
			return rs;

		}
	}

	@RequestMapping("/{path}/gdklsf/submitBetLM.json")
	@ResponseBody
	@Token(remove = true)
	public ResultObject submitBetLM(HttpServletRequest request, String periodsNum, String cachedOdd, String checkType, @RequestParam(value = "price") String money,
	        @RequestParam(value = "BALL") String[] checkedBalls, @PathVariable String path) {
		ResultObject rs = new ResultObject();

		Map<String, String> gdp = commonService.getRunningPeriods(Constant.LOTTERY_TYPE_GDKLSF, "");

		if (null != gdp && gdp.size() > 0) {
			if (Constant.OPEN_STATUS.equals(gdp.get("State"))) {
				periodsNum = gdp.get("PeriondsNum");
			} else if (Constant.STOP_STATUS.equals(gdp.get("State")) && StringUtils.isEmpty(periodsNum)) {
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
			Integer assemblySize = 0;
			List<String> assemblyList = new ArrayList<String>();
			List<String> ballList = Lists.newArrayList(checkedBalls);

			int size = checkedBalls.length;
			String type = "";

			String shopCode = currentUser.getShopsInfo().getShopsCode();
			MemberStaffExt memberStaff = currentUser.getMemberStaffExt();

			double avalilableCredit = bettingService.getMemberAvailableCreditLineRealTime(memberStaff.getTotalCreditLine(), memberStaff.getID(), scheme);
			Map<String, ShopsPlayOdds> shopMap = commonService.initShopOdds(shopCode, Constant.LOTTERY_TYPE_GDKLSF, memberStaff.getPlate());
			ShopsPlayOdds odds = shopMap.get(checkType);
			Map<String, Integer> rateMap = userService.getUserRateLine(memberStaff, scheme);
			String playTypeCode = checkType;

			if (!GenericValidator.isInt(money) || checkType == null) {

				rs.setErrorCode(-7);
				rs.setErrorMsg("非法参数");
				return rs;
			}

			if (Constant.SHOP_PLAY_ODD_STATUS_INVALID.equals(odds.getState())) {
				rs.setErrorCode(-3);
				rs.setErrorMsg("已经封号不能投注");
				return rs;
			}

			if ("GDKLSF_STRAIGHTTHROUGH_RX2".equals(checkType) && size >= 2) {
				type = "任選二";
				assemblyList = Tool.assembly(ballList, 2);
				assemblySize = assemblyList.size();

			} else if ("GDKLSF_STRAIGHTTHROUGH_R2LZ".equals(checkType) && size >= 2) {
				type = "選二連組";
				assemblyList = Tool.assembly(ballList, 2);
				assemblySize = assemblyList.size();
			} else if ("GDKLSF_STRAIGHTTHROUGH_RX3".equals(checkType) && size >= 3) {
				type = "任選三";
				assemblyList = Tool.assembly(ballList, 3);
				assemblySize = assemblyList.size();
			} else if ("GDKLSF_STRAIGHTTHROUGH_R3LZ".equals(checkType) && size >= 3) {
				type = "選三前組";
				assemblyList = Tool.assembly(ballList, 3);
				assemblySize = assemblyList.size();
			} else if ("GDKLSF_STRAIGHTTHROUGH_RX4".equals(checkType) && size >= 4) {
				type = "任選四";
				assemblyList = Tool.assembly(ballList, 4);
				assemblySize = assemblyList.size();
			} else if ("GDKLSF_STRAIGHTTHROUGH_RX5".equals(checkType) && size >= 5) {
				type = "任選五";
				assemblyList = Tool.assembly(ballList, 5);
				assemblySize = assemblyList.size();
			}
			Map<String, UserCommissionDefault> commissionDefaultMap = commonService.queryDefaultCommissionMap("GD%", currentUser.getShopsInfo().getChiefStaffExt().getID(), scheme);
			Map<String, UserCommission> userCommissionMap = commonService.queryUserCommissionMap("GD%", currentUser.getID(), scheme);
			Map<String, BigDecimal> useritemQuotasMoneyMap = commonService.queryUseritemQuotasMoneyMap(Constant.LOTTERY_TYPE_GDKLSF, gdp.get("PeriondsNum"), currentUser.getID(), scheme);
			Map<String, BigDecimal> exsitTotalQuatasMap = commonService.queryTotalCommissionMoneyMap(shopCode, "GDKLSF%", scheme);
			Map<String, String> errorMap = commonService.betCheck(checkType, money, assemblySize.intValue(), periodsNum, rateMap, currentUser, commissionDefaultMap, userCommissionMap,
			        useritemQuotasMoneyMap, exsitTotalQuatasMap, scheme);
			String errorType = errorMap.get("errorType");
			if (errorType != null && "ExceedMinMax".equals(errorType)) {
				rs.setErrorCode(-4);
				rs.setErrorMsg(errorMap.get("errorMessage"));
				return rs;
			} else if (errorType != null && "ExceedChief".equals(errorType)) {
				rs.setErrorCode(-4);
				rs.setErrorMsg(errorMap.get("errorMessage"));
				return rs;

			} else if (errorType != null && "ExceedItem".equals(errorType)) {
				rs.setErrorCode(-4);
				rs.setErrorMsg(errorMap.get("errorMessage"));
				return rs;

			} else if (assemblySize * Integer.parseInt(money) > avalilableCredit) {
				rs.setErrorCode(-5);
				rs.setErrorMsg("投注超过账户余额");
				return rs;

			}

			Map<String, String> messageMap = new HashMap<String, String>();
			messageMap.putAll(errorMap);

			List<BaseBet> betList = new ArrayList<BaseBet>();

			if (checkType != null) {
				OracleSequenceMaxValueIncrementer orderNoGener = (OracleSequenceMaxValueIncrementer) SpringUtil.getBean("betOrderNoGenerator");
				String orderNo = orderNoGener.nextStringValue();

				for (int i = 0; i < assemblyList.size(); i++) {
					String splitAttribute = assemblyList.get(i);

					splitAttribute = splitAttribute.replace(',', '|');
					BaseBet ballBet = commonService.assemblyBet(playTypeCode, money, periodsNum, rateMap, currentUser, scheme);
					ballBet.setOrderNo(orderNo);
					ballBet.setOdds(odds.getRealOdds());
					ballBet.setAttribute(StringUtils.join(checkedBalls, "|"));
					ballBet.setSplitAttribute(splitAttribute);
					betList.add(ballBet);

				}
				Map<String, String> itemMap = commonService.checkLMItemQuotas(betList, PlayTypeUtils.getPlayType(checkType).getCommissionType(), currentUser, userCommissionMap, Constant.LOTTERY_TYPE_GDKLSF, scheme);
				if (!itemMap.isEmpty()) {

					rs.setErrorCode(-4);
					rs.setErrorMsg(errorMap.get("errorMessage"));
					return rs;
				}
			}
			if (!messageMap.containsKey("errorMessage") && !messageMap.containsKey("errorType")) {
				Integer remainPrice = Double.valueOf(avalilableCredit).intValue() - assemblySize * Integer.parseInt(money);
				messageMap.put("remainPrice", remainPrice.toString());
				bettingService.saveGDBet(betList, memberStaff, shopCode, scheme);
				// 異步自動補貨
				bettingService.autoReplenish(betList, memberStaff, scheme);
				userService.updateMemberAvailableCreditLineById(new BigDecimal(avalilableCredit).subtract(new BigDecimal(assemblySize * Integer.parseInt(money))).doubleValue(), memberStaff.getID(),
				        scheme);
			}
			messageMap.put("success", gdklsfService.ajaxHtmlResultForBet(betList, type, ballList, assemblyList, messageMap));

			rs.setErrorCode(0);
			rs.setData(messageMap);
			return rs;

		}
	}

}
