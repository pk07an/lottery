package com.npc.lottery.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.npc.lottery.boss.entity.ShopsInfo;
import com.npc.lottery.common.Constant;
import com.npc.lottery.dao.BettingFrontDao;
import com.npc.lottery.dao.ReplenishFrontDao;
import com.npc.lottery.manage.entity.ShopsDeclaratton;
import com.npc.lottery.manage.logic.interf.IShopsDeclarattonLogic;
import com.npc.lottery.manage.logic.interf.ISystemLogic;
import com.npc.lottery.member.entity.BaseBet;
import com.npc.lottery.member.entity.PlayType;
import com.npc.lottery.odds.entity.ShopsPlayOdds;
import com.npc.lottery.odds.logic.interf.IShopOddsLogic;
import com.npc.lottery.periods.entity.BJSCPeriodsInfo;
import com.npc.lottery.periods.entity.CQPeriodsInfo;
import com.npc.lottery.periods.entity.GDPeriodsInfo;
import com.npc.lottery.periods.entity.JSSBPeriodsInfo;
import com.npc.lottery.periods.entity.NCPeriodsInfo;
import com.npc.lottery.periods.logic.interf.IBJSCPeriodsInfoLogic;
import com.npc.lottery.periods.logic.interf.ICQPeriodsInfoLogic;
import com.npc.lottery.periods.logic.interf.IGDPeriodsInfoLogic;
import com.npc.lottery.periods.logic.interf.IJSSBPeriodsInfoLogic;
import com.npc.lottery.periods.logic.interf.INCPeriodsInfoLogic;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.MemberStaff;
import com.npc.lottery.sysmge.entity.MemberUser;
import com.npc.lottery.user.entity.MemberStaffExt;
import com.npc.lottery.user.entity.UserCommission;
import com.npc.lottery.user.entity.UserCommissionDefault;
import com.npc.lottery.util.PlayTypeUtils;

@Service
public class CommonService {

	@Autowired
	private IGDPeriodsInfoLogic periodsInfoLogic;
	@Autowired
	private IJSSBPeriodsInfoLogic jssbPeriodsInfoLogic;
	@Autowired
	private ISystemLogic systemLogic;
	@Autowired
	private IBJSCPeriodsInfoLogic bjscPeriodsInfoLogic;
	@Autowired
	private ICQPeriodsInfoLogic icqPeriodsInfoLogic;
	@Autowired
	private INCPeriodsInfoLogic ncPeriodsInfoLogic;
	@Autowired
	private IShopsDeclarattonLogic shopsDeclarattonLogic;
	@Autowired
	private IShopOddsLogic shopOddsLogic;
	@Autowired
	private BettingFrontDao bettingFrontDao;
	@Autowired
	private CommissionService commissionService;

	@Autowired
	private ReplenishFrontDao replenishFrontDao;
	@Autowired
	private BettingService bettingService;

	/**
	 * 创建投注对象
	 * 
	 * @param playTypeCode
	 * @param price
	 * @param periodsNum
	 * @param rateMap
	 * @param currentUser
	 * @return
	 */
	public BaseBet assemblyBet(String playTypeCode, String price, String periodsNum, Map<String, Integer> rateMap, MemberUser currentUser, String scheme) {

		String plate = currentUser.getMemberStaffExt().getPlate();

		BaseBet ballBet = new BaseBet();
		this.setBetUserTree(ballBet, playTypeCode, rateMap, currentUser, scheme);
		ballBet.setMoney(new Integer(price));
		ballBet.setPeriodsNum(periodsNum);
		ballBet.setPlate(plate);
		ballBet.setPlayType(playTypeCode);
		ShopsInfo shopInfo = currentUser.getShopsInfo();
		ballBet.setShopCode(shopInfo.getShopsCode());
		ballBet.setShopInfo(shopInfo);
		return ballBet;

	}

	public Map<String, Double> getUserTreeCommission(List<Long> userTree, String commissionType, String plate, String scheme) {
		Map<String, Double> commissionsMap = new HashMap<String, Double>();

		if (userTree == null || userTree.size() == 0) {
			return commissionsMap;
		}

		// 获取该用户改玩法类型下各上线退水的值
		List<UserCommission> commisionList = commissionService.getUserCommissionListByPlayFinalTypeAndIds(commissionType, userTree, scheme);

		for (int i = 0; i < commisionList.size(); i++) {
			UserCommission uc = commisionList.get(i);
			Double userCommission = null;
			if ("A".equals(plate)) {
				userCommission = uc.getCommissionA();
			} else if ("B".equals(plate)) {
				userCommission = uc.getCommissionB();
			} else if ("C".equals(plate)) {
				userCommission = uc.getCommissionC();
			}

			if (uc.getUserType().equals(MemberStaff.USER_TYPE_MEMBER)) {
				commissionsMap.put(MemberStaff.USER_TYPE_MEMBER, userCommission);
			} else if (uc.getUserType().equals(ManagerStaff.USER_TYPE_AGENT)) {
				commissionsMap.put(ManagerStaff.USER_TYPE_AGENT, userCommission);
			} else if (uc.getUserType().equals(ManagerStaff.USER_TYPE_GEN_AGENT)) {
				commissionsMap.put(ManagerStaff.USER_TYPE_GEN_AGENT, userCommission);
			} else if (uc.getUserType().equals(ManagerStaff.USER_TYPE_STOCKHOLDER)) {
				commissionsMap.put(ManagerStaff.USER_TYPE_STOCKHOLDER, userCommission);
			} else if (uc.getUserType().equals(ManagerStaff.USER_TYPE_BRANCH)) {
				commissionsMap.put(ManagerStaff.USER_TYPE_BRANCH, userCommission);
			}
		}
		return commissionsMap;
	}

	private void setBetUserTree(BaseBet bet, String playTypeCode, Map<String, Integer> rateMap, MemberUser currentUser, String scheme) {
		PlayType playType = PlayTypeUtils.getPlayType(playTypeCode);
		Long memberStaff = currentUser.getID();

		MemberStaffExt memberStaffExt = currentUser.getMemberStaffExt();
		String plate = memberStaffExt.getPlate();
		Long chiefStaff = memberStaffExt.getChiefStaff() == null ? 0L : memberStaffExt.getChiefStaff();
		Long branchStaff = memberStaffExt.getBranchStaff() == null ? 0L : memberStaffExt.getBranchStaff();
		Long stockholderStaff = memberStaffExt.getStockholderStaff() == null ? 0L : memberStaffExt.getStockholderStaff();
		Long genAgenStaff = memberStaffExt.getGenAgentStaff() == null ? 0L : memberStaffExt.getGenAgentStaff();
		Long agentStaff = memberStaffExt.getAgentStaff() == null ? 0L : memberStaffExt.getAgentStaff();
		List<Long> userList = Lists.newArrayList();
		userList.add(memberStaffExt.getID());
		if (agentStaff != null) {
			userList.add(agentStaff);
		}
		if (genAgenStaff != null) {
			userList.add(genAgenStaff);
		}
		if (stockholderStaff != null) {
			userList.add(stockholderStaff);
		}
		if (branchStaff != null) {
			userList.add(branchStaff);
		}
		Map<String, Double> commissionMap = this.getUserTreeCommission(userList, playType.getCommissionType(), plate, scheme);
		bet.setBranchCommission(commissionMap.get(ManagerStaff.USER_TYPE_BRANCH));
		bet.setGenAgenCommission(commissionMap.get(ManagerStaff.USER_TYPE_GEN_AGENT));
		bet.setStockHolderCommission(commissionMap.get(ManagerStaff.USER_TYPE_STOCKHOLDER));
		bet.setAgentStaffCommission(commissionMap.get(ManagerStaff.USER_TYPE_AGENT));
		bet.setMemberCommission(commissionMap.get(MemberStaff.USER_TYPE_MEMBER));

		bet.setChiefRate(rateMap.get("chiefRate"));
		bet.setBranchRate(rateMap.get("branchRate"));
		bet.setStockHolderRate(rateMap.get("shareholderRate"));
		bet.setGenAgenRate(rateMap.get("genAgentRate"));
		bet.setAgentStaffRate(rateMap.get("agentRate"));

		bet.setBettingUserId(memberStaff);
		bet.setAgentStaff(agentStaff);
		bet.setGenAgenStaff(genAgenStaff);
		bet.setStockholderStaff(stockholderStaff);
		bet.setBranchStaff(branchStaff);
		bet.setChiefStaff(chiefStaff);
		bet.setCommissionType(playType.getCommissionType());
	}

	/**
	 * 根据玩法获取盘期信息
	 * 
	 * @param playType
	 *            玩法类型
	 * @param shopCode
	 *            商铺号
	 * @return
	 */
	public Map<String, String> getRunningPeriods(String playType, String shopCode) {
		Map<String, String> result = new HashMap<String, String>();
		if ("GDKLSF".equals(playType)) {
			result = getGDKLSFRunningPeriods();
		} else if ("CQSSC".equals(playType)) {
			result = getCQSSCRunningPeriods();
		} else if ("BJSC".equals(playType)) {
			result = getBJRunningPeriods(shopCode);
		} else if ("K3".equals(playType)) {
			result = getJSRunningPeriods(shopCode);
		} else if ("NC".equals(playType)) {
			result = getNCRunningPeriods(shopCode);
		}
		return result;
	}

	/**
	 * 幸运农场NC
	 * 
	 * @return
	 */
	private Map<String, String> getNCRunningPeriods(String shopCode) {
		// 找出当前盘期
		NCPeriodsInfo runningPeriods = ncPeriodsInfoLogic.findCurrentPeriod();
		Map<String, String> retMap = null;
		if (runningPeriods != null) {
			Date now = new Date();
			retMap = new HashMap<String, String>();
			long lotteryTime = runningPeriods.getLotteryTime().getTime() - now.getTime();
			long stopTime = runningPeriods.getStopQuotTime().getTime() - now.getTime();
			retMap.put("PeriondsNum", runningPeriods.getPeriodsNum());
			// modify by peter
			if (Constant.CLOSE.equals(systemLogic.findPeriodState(Constant.LOTTERY_TYPE_NC, shopCode))) {
				retMap.put("State", "3");
			} else {
				String state = "1";
				if (now.after(runningPeriods.getStopQuotTime())) {
					// 当前时间在封盘时间之后,状态为封盘
					state = "3";
				} else {
					// 当前时间在封盘时间之前,状态为开盘
					state = "2";
				}
				retMap.put("State", state);
			}
			retMap.put("KjTime", Long.valueOf(lotteryTime).toString());
			retMap.put("StopTime", Long.valueOf(stopTime).toString());
			return retMap;
		}
		return retMap;
	}

	/**
	 * 重慶時時彩CQSSC
	 * 
	 * @return
	 */
	private Map<String, String> getCQSSCRunningPeriods() {
		// 找出当前盘期
		CQPeriodsInfo runningPeriods = icqPeriodsInfoLogic.findCurrentPeriod();
		Map<String, String> retMap = null;
		if (runningPeriods != null) {
			Date now = new Date();
			retMap = new HashMap<String, String>();
			long lotteryTime = runningPeriods.getLotteryTime().getTime() - now.getTime();
			long stopTime = runningPeriods.getStopQuotTime().getTime() - now.getTime();
			retMap.put("PeriondsNum", runningPeriods.getPeriodsNum());

			String lastThreePeriod = runningPeriods.getPeriodsNum().substring(runningPeriods.getPeriodsNum().length()-3);
			String state = "1";
			//重庆第24期不开盘
			if (now.after(runningPeriods.getStopQuotTime()) || "024".equals(lastThreePeriod)) {
				// 当前时间在封盘时间之后,状态为封盘
				state = "3";
			} else {
				// 当前时间在封盘时间之前,状态为开盘
				state = "2";
			}
			retMap.put("State", state);
			retMap.put("KjTime", Long.valueOf(lotteryTime).toString());
			retMap.put("StopTime", Long.valueOf(stopTime).toString());
		}
		return retMap;
	}

	/**
	 * 北京賽車BJSC
	 * 
	 * @return
	 */
	private Map<String, String> getBJRunningPeriods(String shopCode) {
		// 找出当前盘期
		BJSCPeriodsInfo runningPeriods = bjscPeriodsInfoLogic.findCurrentPeriod();
		Map<String, String> retMap = null;
		if (runningPeriods != null) {
			Date now = new Date();
			retMap = new HashMap<String, String>();
			long lotteryTime = runningPeriods.getLotteryTime().getTime() - now.getTime();
			long stopTime = runningPeriods.getStopQuotTime().getTime() - now.getTime();
			retMap.put("PeriondsNum", runningPeriods.getPeriodsNum());
			// modify by peter
			if (Constant.CLOSE.equals(systemLogic.findPeriodState("BJ", shopCode))) {
				retMap.put("State", "3");
			} else {
				String state = "1";
				if (now.after(runningPeriods.getStopQuotTime())) {
					// 当前时间在封盘时间之后,状态为封盘
					state = "3";
				} else {
					// 当前时间在封盘时间之前,状态为开盘
					state = "2";
				}
				retMap.put("State", state);
			}
			retMap.put("KjTime", Long.valueOf(lotteryTime).toString());
			retMap.put("StopTime", Long.valueOf(stopTime).toString());
		}
		return retMap;
	}

	/**
	 * 江苏骰寶K3
	 * 
	 * @return
	 */
	private Map<String, String> getJSRunningPeriods(String shopCode) {
		// 找出当前盘期
		JSSBPeriodsInfo runningPeriods = jssbPeriodsInfoLogic.findCurrentPeriod();
		Map<String, String> retMap = null;
		if (runningPeriods != null) {
			Date now = new Date();
			retMap = new HashMap<String, String>();
			long lotteryTime = runningPeriods.getLotteryTime().getTime() - now.getTime();
			long stopTime = runningPeriods.getStopQuotTime().getTime() - now.getTime();
			retMap.put("PeriondsNum", runningPeriods.getPeriodsNum());
			// modify by peter
			if (Constant.CLOSE.equals(systemLogic.findPeriodState("K3", shopCode))) {
				retMap.put("State", "3");
			} else {
				String state = "1";
				if (now.after(runningPeriods.getStopQuotTime())) {
					// 当前时间在封盘时间之后,状态为封盘
					state = "3";
				} else {
					// 当前时间在封盘时间之前,状态为开盘
					state = "2";
				}
				retMap.put("State", state);
			}
			retMap.put("KjTime", Long.valueOf(lotteryTime).toString());
			retMap.put("StopTime", Long.valueOf(stopTime).toString());

		}
		return retMap;
	}

	/**
	 * 广东快乐十分
	 * 
	 * @return
	 */
	private Map<String, String> getGDKLSFRunningPeriods() {
		// 找出当前盘期
		GDPeriodsInfo runningPeriods = periodsInfoLogic.findCurrentPeriod();
		Map<String, String> retMap = null;
		if (runningPeriods != null) {
			Date now = new Date();
			retMap = new HashMap<String, String>();
			long lotteryTime = runningPeriods.getLotteryTime().getTime() - now.getTime();
			long stopTime = runningPeriods.getStopQuotTime().getTime() - now.getTime();
			retMap.put("PeriondsNum", runningPeriods.getPeriodsNum());

			String state = "1";
			if (now.after(runningPeriods.getStopQuotTime())) {
				// 当前时间在封盘时间之后,状态为封盘
				state = "3";
			} else {
				// 当前时间在封盘时间之前,状态为开盘
				state = "2";
			}
			retMap.put("State", state);
			retMap.put("KjTime", Long.valueOf(lotteryTime).toString());
			retMap.put("StopTime", Long.valueOf(stopTime).toString());
		}
		return retMap;
	}

	public Map<String, String> getShopsDeclarattonContent(MemberUser currentUser) {
		String ret = "";
		ShopsDeclaratton shopsDeclaratton = shopsDeclarattonLogic.queryByMemberShopsDeclaratton(new Date(), currentUser);
		if (shopsDeclaratton != null) {
			ret = shopsDeclaratton.getContentInfo();
		}
		Map<String, String> retMap = new HashMap<String, String>();
		retMap.put("marquee", ret);
		return retMap;
	}

	public Map<String, String> getTodayWinMoney(long userID, String scheme) {
		double winMoney = bettingFrontDao.getTodayWinMoney(userID, scheme);
		BigDecimal winBigDecimal = new BigDecimal(winMoney).setScale(1, BigDecimal.ROUND_DOWN);
		Map<String, String> retMap = new HashMap<String, String>();
		retMap.put("todayWin", winBigDecimal.toString());
		return retMap;
	}

	public Map<String, ShopsPlayOdds> initShopOdds(String shopCode, String playType, String plate) {
		Map<String, ShopsPlayOdds> resultMap = new HashMap<String, ShopsPlayOdds>();
		if (Constant.LOTTERY_TYPE_BJSC.equals(playType)) {
			resultMap = this.initBJSCShopOdds(shopCode, plate);
		} else if (Constant.LOTTERY_TYPE_CQSSC.equals(playType)) {
			resultMap = this.initCQShopOdds(shopCode, plate);
		} else if (Constant.LOTTERY_TYPE_GDKLSF.equals(playType)) {
			resultMap = this.initGDShopOdds(shopCode, plate);
		} else if (Constant.LOTTERY_TYPE_K3.equals(playType)) {
			resultMap = this.initJSSBShopOdds(shopCode, plate);
		} else if (Constant.LOTTERY_TYPE_NC.equals(playType)) {
			resultMap = this.initNCShopOdds(shopCode, plate);
		}
		return resultMap;
	}

	private Map<String, ShopsPlayOdds> initBJSCShopOdds(String shopCode, String plate) {
		List<ShopsPlayOdds> cqoddList = shopOddsLogic.queryCQSSCRealOdds(shopCode, "BJ");
		Map<String, ShopsPlayOdds> shopOddMap = new HashMap<String, ShopsPlayOdds>();
		for (int i = 0; i < cqoddList.size(); i++) {
			ShopsPlayOdds shopodds = cqoddList.get(i);
			if ("B".equals(plate))
				shopodds.setRealOdds(shopodds.getRealOddsB());
			else if ("C".equals(plate))
				shopodds.setRealOdds(shopodds.getRealOddsC());
			shopOddMap.put(shopodds.getPlayTypeCode(), shopodds);
		}
		return shopOddMap;

	}

	private Map<String, ShopsPlayOdds> initCQShopOdds(String shopCode, String plate) {
		List<ShopsPlayOdds> cqoddList = shopOddsLogic.queryCQSSCRealOdds(shopCode, "CQSSC");
		Map<String, ShopsPlayOdds> shopOddMap = new HashMap<String, ShopsPlayOdds>();
		for (int i = 0; i < cqoddList.size(); i++) {
			ShopsPlayOdds shopodds = cqoddList.get(i);
			if ("B".equals(plate))
				shopodds.setRealOdds(shopodds.getRealOddsB());
			else if ("C".equals(plate))
				shopodds.setRealOdds(shopodds.getRealOddsC());
			shopOddMap.put(shopodds.getPlayTypeCode(), shopodds);
		}
		return shopOddMap;

	}

	private Map<String, ShopsPlayOdds> initGDShopOdds(String shopCode, String plate) {
		List<ShopsPlayOdds> gdoddList = shopOddsLogic.queryGDKLSFRealOdds(shopCode, "GDKLSF");

		Map<String, ShopsPlayOdds> shopOddMap = new HashMap<String, ShopsPlayOdds>();
		for (int i = 0; i < gdoddList.size(); i++) {
			ShopsPlayOdds shopodds = gdoddList.get(i);
			if ("B".equals(plate))
				shopodds.setRealOdds(shopodds.getRealOddsB());
			else if ("C".equals(plate))
				shopodds.setRealOdds(shopodds.getRealOddsC());
			shopOddMap.put(shopodds.getPlayTypeCode(), shopodds);
		}

		return shopOddMap;

	}

	/**
	 * add by peter K3 project 初始化江苏骰宝的赔率
	 * 
	 * @param shopCode
	 * @return
	 */
	private Map<String, ShopsPlayOdds> initJSSBShopOdds(String shopCode, String plate) {
		List<ShopsPlayOdds> cqoddList = shopOddsLogic.queryCQSSCRealOdds(shopCode, Constant.LOTTERY_TYPE_K3);
		Map<String, ShopsPlayOdds> shopOddMap = new HashMap<String, ShopsPlayOdds>();
		for (int i = 0; i < cqoddList.size(); i++) {
			ShopsPlayOdds shopodds = cqoddList.get(i);
			if ("B".equals(plate))
				shopodds.setRealOdds(shopodds.getRealOddsB());
			else if ("C".equals(plate))
				shopodds.setRealOdds(shopodds.getRealOddsC());
			shopOddMap.put(shopodds.getPlayTypeCode(), shopodds);
		}
		return shopOddMap;

	}

	private Map<String, ShopsPlayOdds> initNCShopOdds(String shopCode, String plate) {
		List<ShopsPlayOdds> gdoddList = shopOddsLogic.queryGDKLSFRealOdds(shopCode, "NC");

		Map<String, ShopsPlayOdds> shopOddMap = new HashMap<String, ShopsPlayOdds>();
		for (int i = 0; i < gdoddList.size(); i++) {
			ShopsPlayOdds shopodds = gdoddList.get(i);
			if ("B".equals(plate))
				shopodds.setRealOdds(shopodds.getRealOddsB());
			else if ("C".equals(plate))
				shopodds.setRealOdds(shopodds.getRealOddsC());
			shopOddMap.put(shopodds.getPlayTypeCode(), shopodds);
		}

		return shopOddMap;

	}

	public Map<String, String> coventMap(Map<String, ShopsPlayOdds> oddMap, String playType, String type) {
		Map<String, String> covertMap = new HashMap<String, String>();
		if (Constant.LOTTERY_TYPE_BJSC.equals(playType)) {
			covertMap = this.bjCoventMap(oddMap, type);

		} else if (Constant.LOTTERY_TYPE_CQSSC.equals(playType)) {
			covertMap = this.cqCoventMap(oddMap, type);
		} else if (Constant.LOTTERY_TYPE_GDKLSF.equals(playType)) {
			covertMap = this.gdCoventMap(oddMap, type);
		} else if (Constant.LOTTERY_TYPE_K3.equals(playType)) {
			covertMap = this.jssbCoventMap(oddMap, type);
		} else if (Constant.LOTTERY_TYPE_NC.equals(playType)) {
			covertMap = this.ncCoventMap(oddMap, type);
		}
		return covertMap;
	}

	private Map<String, String> bjCoventMap(Map<String, ShopsPlayOdds> oddMap, String type) {
		String typeFilter = "";
		if ("1".equals(type)) {
			typeFilter = "_DOUBLESIDE";
		} else if ("2".equals(type)) {
			typeFilter = "_GROUP|_FIRST|_SECOND|_1_X|_1_DA|_1_DAN|_1_S|_1_LONG|_1_HU|_2_X|_2_DA|_2_DAN|_2_S|_2_LONG|_2_HU|_DOUBLESIDE_X|_DOUBLESIDE_DA|_DOUBLESIDE_S|_DOUBLESIDE_DAN";
		} else if ("3".equals(type)) {
			typeFilter = "_THIRD|_FORTH|_FIFTH|_SIXTH|_3|_4|_5|_6";
		} else if ("4".equals(type)) {
			typeFilter = "_SEVENTH|_EIGHTH|_NINTH|_TENTH|_7|_8|_9|_10";
		}
		Map<String, String> covertMap = new HashMap<String, String>();
		for (ShopsPlayOdds ent : oddMap.values()) {
			String typeCode = ent.getPlayTypeCode();
			String[] fileters = StringUtils.split(typeFilter, "|");
			for (int i = 0; i < fileters.length; i++) {
				if (typeCode.indexOf(fileters[i]) != -1) {
					covertMap.put(typeCode, ent.getRealOdds().toString());
					break;
				}
			}
		}
		oddMap.clear();
		return covertMap;
	}

	private Map<String, String> cqCoventMap(Map<String, ShopsPlayOdds> oddMap, String type) {
		String typeFilter = "";

		if ("1".equals(type)) {
			typeFilter = "_FIRST|_DOUBLESIDE|_FRONT|_MID|_LAST";
		} else if ("2".equals(type)) {

			typeFilter = "_SECOND|_DOUBLESIDE|_FRONT|_MID|_LAST";
		} else if ("3".equals(type)) {

			typeFilter = "_THIRD|_DOUBLESIDE|_FRONT|_MID|_LAST";
		} else if ("4".equals(type)) {

			typeFilter = "_FORTH|_DOUBLESIDE|_FRONT|_MID|_LAST";
		} else if ("5".equals(type)) {

			typeFilter = "_FIFTH|_DOUBLESIDE|_FRONT|_MID|_LAST";
		}

		else if ("0".equals(type)) {

			typeFilter = "_DOUBLESIDE";
		}
		Map<String, String> covertMap = new HashMap<String, String>();
		for (ShopsPlayOdds ent : oddMap.values()) {
			String typeCode = ent.getPlayTypeCode();

			String[] fileters = StringUtils.split(typeFilter, "|");
			for (int i = 0; i < fileters.length; i++) {
				if (typeCode.indexOf(fileters[i]) != -1) {
					covertMap.put(typeCode, ent.getRealOdds().toString());
					break;
				}
			}
		}
		oddMap.clear();
		return covertMap;
	}

	private Map<String, String> gdCoventMap(Map<String, ShopsPlayOdds> oddMap, String type) {
		String typeFilter = "";
		if ("0".equals(type)) {
			typeFilter = "DOUBLESIDE";
		} else if ("1".equals(type)) {
			typeFilter = "GDKLSF_BALL_FIRST|DOUBLESIDE_1";
		} else if ("2".equals(type)) {
			typeFilter = "GDKLSF_BALL_SECOND|DOUBLESIDE_2";
		} else if ("3".equals(type)) {
			typeFilter = "GDKLSF_BALL_THIRD|DOUBLESIDE_3";
		} else if ("4".equals(type)) {
			typeFilter = "GDKLSF_BALL_FORTH|DOUBLESIDE_4";
		} else if ("5".equals(type)) {
			typeFilter = "GDKLSF_BALL_FIFTH|DOUBLESIDE_5";
		} else if ("6".equals(type)) {
			typeFilter = "GDKLSF_BALL_SIXTH|DOUBLESIDE_6";
		} else if ("7".equals(type)) {
			typeFilter = "GDKLSF_BALL_SEVENTH|DOUBLESIDE_7";
		} else if ("8".equals(type)) {
			typeFilter = "GDKLSF_BALL_EIGHTH|DOUBLESIDE_8";
		} else if ("9".equals(type)) {
			typeFilter = "GDKLSF_DOUBLESIDE_ZH|GDKLSF_DOUBLESIDE_LONG|GDKLSF_DOUBLESIDE_HU";
		} else if ("10".equals(type)) {
			typeFilter = "GDKLSF_STRAIGHTTHROUGH";
		}

		Map<String, String> covertMap = new HashMap<String, String>();
		for (ShopsPlayOdds ent : oddMap.values()) {
			String typeCode = ent.getPlayTypeCode();

			String[] fileters = StringUtils.split(typeFilter, "|");
			for (int i = 0; i < fileters.length; i++) {
				if (typeCode.indexOf(fileters[i]) != -1) {
					covertMap.put(typeCode, ent.getRealOdds().toString());
					break;
				}
			}

		}
		oddMap.clear();
		return covertMap;
	}

	private Map<String, String> jssbCoventMap(Map<String, ShopsPlayOdds> oddMap, String type) {
		String typeFilter = "";

		if ("1".equals(type)) {
			typeFilter = "K3_";
		}

		Map<String, String> covertMap = new HashMap<String, String>();
		for (ShopsPlayOdds ent : oddMap.values()) {
			String typeCode = ent.getPlayTypeCode();

			String[] fileters = StringUtils.split(typeFilter, "|");
			for (int i = 0; i < fileters.length; i++) {
				if (typeCode.indexOf(fileters[i]) != -1) {
					covertMap.put(typeCode, ent.getRealOdds().toString());
					break;
				}
			}

		}
		oddMap.clear();
		return covertMap;
	}

	private Map<String, String> ncCoventMap(Map<String, ShopsPlayOdds> oddMap, String type) {
		String typeFilter = "";
		if ("0".equals(type)) {
			typeFilter = "DOUBLESIDE";
		} else if ("1".equals(type)) {
			typeFilter = "NC_BALL_FIRST|DOUBLESIDE_1";
		} else if ("2".equals(type)) {
			typeFilter = "NC_BALL_SECOND|DOUBLESIDE_2";
		} else if ("3".equals(type)) {
			typeFilter = "NC_BALL_THIRD|DOUBLESIDE_3";
		} else if ("4".equals(type)) {
			typeFilter = "NC_BALL_FORTH|DOUBLESIDE_4";
		} else if ("5".equals(type)) {
			typeFilter = "NC_BALL_FIFTH|DOUBLESIDE_5";
		} else if ("6".equals(type)) {
			typeFilter = "NC_BALL_SIXTH|DOUBLESIDE_6";
		} else if ("7".equals(type)) {
			typeFilter = "NC_BALL_SEVENTH|DOUBLESIDE_7";
		} else if ("8".equals(type)) {
			typeFilter = "NC_BALL_EIGHTH|DOUBLESIDE_8";
		} else if ("9".equals(type)) {
			typeFilter = "NC_DOUBLESIDE_ZH|NC_DOUBLESIDE_LONG|NC_DOUBLESIDE_HU";
		} else if ("10".equals(type)) {
			typeFilter = "NC_STRAIGHTTHROUGH";
		}
		Map<String, String> covertMap = new HashMap<String, String>();
		for (ShopsPlayOdds ent : oddMap.values()) {
			String typeCode = ent.getPlayTypeCode();
			String[] fileters = StringUtils.split(typeFilter, "|");
			for (int i = 0; i < fileters.length; i++) {
				if (typeCode.indexOf(fileters[i]) != -1) {
					covertMap.put(typeCode, ent.getRealOdds().toString());
					break;
				}
			}
		}
		oddMap.clear();
		return covertMap;
	}

	/**
	 * 新方法,优化投注时校验的性能 投注业务逻辑校验
	 * 
	 * @param playTypeCode
	 * @param price
	 * @param playCount
	 * @param periodsNum
	 * @param rateMap
	 * @param currentUser
	 * @return
	 */
	public Map<String, String> betCheck(String playTypeCode, String price, int playCount, String periodsNum, Map<String, Integer> rateMap, MemberUser currentUser,
	        Map<String, UserCommissionDefault> commissionDefaultMap, Map<String, UserCommission> userCommissionMap, Map<String, BigDecimal> useritemQuotasMoneyMap,
	        Map<String, BigDecimal> exsitTotalQuatasMap, String scheme) {

		HashMap<String, String> messageMap = new HashMap<String, String>();
		PlayType playType = PlayTypeUtils.getPlayType(playTypeCode);
		// 校验单注最低投注額，单注最高限额
		UserCommissionDefault userCommsionDefault = commissionDefaultMap.get(playType.getCommissionType());
		//
		UserCommission userCommsion = userCommissionMap.get(playType.getCommissionType());
		// 总监总的投注受额
		Integer totalQuatas = userCommsionDefault.getTotalQuatas();
		// 单注最低
		Integer lowestQuatas = userCommsionDefault.getLowestQuatas();

		// 单注限额
		Integer bettingQuotas = userCommsion.getBettingQuotas();

		// 广东/农场连码 单独处理单项限额校验
		if (playTypeCode.indexOf("GDKLSF_STRAIGHTTHROUGH") == -1 || playTypeCode.indexOf("NC_STRAIGHTTHROUGH") == -1)

		{
			// 单期限额
			Integer itemQuotas = userCommsion.getItemQuotas();

			Integer userTotalMoney = useritemQuotasMoneyMap.get(playTypeCode) == null ? 0 : useritemQuotasMoneyMap.get(playTypeCode).intValue();
			if (userTotalMoney + Integer.valueOf(price) * playCount > itemQuotas) {
				messageMap.put("errorMessage", "超过单项限额!" + itemQuotas);
				messageMap.put("errorType", "ExceedItem");
				return messageMap;
			}
		}

		if (Integer.parseInt(price) < lowestQuatas || Integer.parseInt(price) > bettingQuotas) {
			String errorMes = "";
			if (Integer.parseInt(price) < lowestQuatas) {
				errorMes = "下注金额，低於最低投注金额，请更改。\n\n";
			}
			if (Integer.parseInt(price) > bettingQuotas) {
				errorMes = "下注金额，超过最高金额，请更改。\n\n";
			}

			String subName = playType.getSubTypeName();
			String finalName = playType.getFinalTypeName();
			if (StringUtils.isNotEmpty(subName)) {
				finalName = subName;
			}
			messageMap.put("errorType", "ExceedMinMax");
			messageMap.put("errorMessage", errorMes + finalName + "\n\n单注最低投注額: " + lowestQuatas + "\n\n单注最高限额: " + bettingQuotas);
			return messageMap;

		}

		// 已经投注实占+当前投注实占 >总监实占总金额
		double replenishMoney = 0;
		ShopsInfo shopsInfo = currentUser.getShopsInfo();
		if (null != shopsInfo) {
			if (null != shopsInfo.getChiefStaffExt()) {
				replenishMoney = replenishFrontDao.getChiefReplenishMoneyForBetCheck(shopsInfo.getChiefStaffExt().getID(), playTypeCode, periodsNum, scheme);
			}
		}

		// 统计 店铺下 所有用户的投注总额
		Double exsitTotalQuatas = exsitTotalQuatasMap.get(playTypeCode) == null ? 0 : exsitTotalQuatasMap.get(playTypeCode).doubleValue();
		Integer chiefRate = rateMap.get("chiefRate");
		BigDecimal moneyResult = BigDecimal.valueOf(exsitTotalQuatas).add(BigDecimal.valueOf((Double.valueOf(price) * playCount) * chiefRate).divide(BigDecimal.valueOf(100)))
		        .add(BigDecimal.valueOf(replenishMoney));
		if (moneyResult.compareTo(BigDecimal.valueOf(totalQuatas)) > 0) {
			messageMap.put("errorMessage", "超过总监单项受注额!");
			messageMap.put("errorType", "ExceedChief");
			return messageMap;
		}

		return messageMap;
	}

	/**
	 * 广东/农场连码检查
	 * 
	 * @param betList
	 * @param playTypeCode
	 * @param currentUser
	 * @param userCommissionMap
	 * @param scheme
	 * @return
	 */
	public Map<String, String> checkLMItemQuotas(List<BaseBet> betList, String playTypeCode, MemberUser currentUser, Map<String, UserCommission> userCommissionMap, String playType, String scheme) {
		Map<String, String> messageMap = new HashMap<String, String>();
		UserCommission userCommsion = userCommissionMap.get(playTypeCode);
		Integer itemQuotas = userCommsion.getItemQuotas();
		for (int i = 0; i < betList.size(); i++) {
			BaseBet bet = betList.get(i);
			String attr = bet.getSplitAttribute();
			String periodsNum = bet.getPeriodsNum();
			Integer price = bet.getMoney();
			Integer userTotalMoney = bettingService.getUserLMitemQuotasMoney(periodsNum, playTypeCode, currentUser.toString(), attr, playType, scheme);
			if (userTotalMoney + Integer.valueOf(price) > itemQuotas) {
				messageMap.put("errorMessage", "超过单项限额!" + itemQuotas);
				messageMap.put("errorType", "ExceedItem");
				return messageMap;
			}
		}
		return messageMap;
	}

	/**
	 * 通过总监的id获取玩法类型的map
	 * 
	 * @param playFinalType
	 * @param chiefId
	 * @return
	 */
	public Map<String, UserCommissionDefault> queryDefaultCommissionMap(String playFinalType, Long chiefId, String scheme) {
		Map<String, UserCommissionDefault> commissionDefaultMap = new HashMap<String, UserCommissionDefault>();
		List<UserCommissionDefault> commissionDefaultList = commissionService.getDefaultCommissionListByPlayFinalTypeLike(playFinalType, chiefId);
		if (!CollectionUtils.isEmpty(commissionDefaultList)) {
			for (UserCommissionDefault commissionDefault : commissionDefaultList) {
				commissionDefaultMap.put(commissionDefault.getPlayFinalType(), commissionDefault);
			}
		}
		return commissionDefaultMap;
	}

	/**
	 * 通过用户的id获取玩法类型的map
	 * 
	 * @param playFinalType
	 * @param chiefId
	 * @return
	 */
	public Map<String, UserCommission> queryUserCommissionMap(String playFinalType, Long userId, String scheme) {
		Map<String, UserCommission> userCommissionMap = new HashMap<String, UserCommission>();
		List<UserCommission> commissionList = commissionService.getUserCommissionListByPlayFinalTypeLike(playFinalType, userId, scheme);
		if (!CollectionUtils.isEmpty(commissionList)) {
			for (UserCommission userCommission : commissionList) {
				userCommissionMap.put(userCommission.getPlayFinalType(), userCommission);
			}
		}
		return userCommissionMap;
	}

	public Map<String, BigDecimal> queryUseritemQuotasMoneyMap(String playType, String periodNum, Long userId, String scheme) {
		return bettingFrontDao.getUseritemQuotasMoneyMap(playType, periodNum, userId, scheme);
	}

	public Map<String, BigDecimal> queryTotalCommissionMoneyMap(String shopCode, String playType, String scheme) {
		return bettingFrontDao.getTotalCommissionMoneyMap(shopCode, playType, scheme);
	}

	public int getTotelBetMoney(Map<String, String> handlerMap) {
		int totalBetPrice = 0;
		for (Entry<String, String> entry : handlerMap.entrySet()) {
			String price = entry.getValue();
			totalBetPrice = totalBetPrice + Integer.parseInt(price);
		}
		return totalBetPrice;
	}
}
