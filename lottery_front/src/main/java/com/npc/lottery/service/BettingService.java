package com.npc.lottery.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.npc.lottery.boss.entity.ShopsInfo;
import com.npc.lottery.common.Constant;
import com.npc.lottery.dao.BettingFrontDao;
import com.npc.lottery.dao.PlayAmountFrontDao;
import com.npc.lottery.dao.PlayBetFrontDao;
import com.npc.lottery.dao.ReplenishFrontDao;
import com.npc.lottery.dao.ShopOddsLogFrontDao;
import com.npc.lottery.dao.UserFrontDao;
import com.npc.lottery.member.entity.BalanceInfo;
import com.npc.lottery.member.entity.BaseBet;
import com.npc.lottery.member.entity.CQandGDReportInfo;
import com.npc.lottery.member.entity.PlayAmount;
import com.npc.lottery.member.entity.PlayType;
import com.npc.lottery.odds.entity.OpenPlayOdds;
import com.npc.lottery.odds.entity.ShopsPlayOdds;
import com.npc.lottery.odds.logic.interf.IShopOddsLogic;
import com.npc.lottery.replenish.logic.interf.IReplenishAutoLogic;
import com.npc.lottery.sysmge.entity.ShopsPlayOddsLog;
import com.npc.lottery.user.entity.MemberStaffExt;
import com.npc.lottery.util.Page;
import com.npc.lottery.util.PlayTypeUtils;

@Service
public class BettingService {

	@Autowired
	private BettingFrontDao bettingFrontDao;
	@Autowired
	private PlayBetFrontDao playBetFrontDao;
	@Autowired
	private PlayAmountFrontDao playAmountFrontDao;
	@Autowired
	private IShopOddsLogic shopOddsLogic;
	@Autowired
	private ReplenishFrontDao replenishFrontDao;
	@Autowired
	private ShopOddsLogFrontDao shopOddsLogFrontDao;
	@Autowired
	private TaskExecutor wcpTaskExecutor;
	@Autowired
	private UserFrontDao userFrontDao;
	@Autowired
	private IReplenishAutoLogic replenishAutoLogic;

	private static final Logger log = Logger.getLogger(BettingService.class);

	public Page getUserBetDetail(Page page, Long userId, String scheme) {
		return bettingFrontDao.getUserBetDetail(page, userId, scheme);
	}

	public Page<CQandGDReportInfo> getReport(Page page, Long userMemberID, Date startDate, String scheme) {
		List<CQandGDReportInfo> infoList = new ArrayList<CQandGDReportInfo>();
		infoList = bettingFrontDao.getCQandGDReportDao(userMemberID, startDate, scheme);
		// 合計
		double money = 0;
		double commissionResult = 0;// 今天一共退了多少钱
		for (int i = 0; i < infoList.size(); i++) {
			if (!"9".equals(infoList.get(i).getWinState()) && !"4".equals(infoList.get(i).getWinState()) && !"5".equals(infoList.get(i).getWinState())) {
				commissionResult += infoList.get(i).getRecessionResults();
			}
			if (!"4".equals(infoList.get(i).getWinState()) && !"5".equals(infoList.get(i).getWinState())) {
				money += infoList.get(i).getMoney();
			}
		}
		//

		int first = page.getFirst() - 1;
		int last = first + page.getPageSize();
		if (last > infoList.size())
			last = infoList.size();
		Collections.sort(infoList);
		page.setTotalCount(infoList.size());
		page.setResult(infoList.subList(first, last));
		page.setTotal1(money);
		page.setTotal2(commissionResult);
		return page;
	}

	public Map<String, BalanceInfo> getBalanceInfoByDate(Long userMemberID, Date startDate, Date endDate, String scheme) {
		List<BalanceInfo> balanceList = bettingFrontDao.getBalanceInfoByDate(userMemberID, startDate, endDate, scheme);
		Map<String, BalanceInfo> retMap = new HashMap<String, BalanceInfo>();
		for (int i = 0; i < balanceList.size(); i++) {

			BalanceInfo balance = balanceList.get(i);
			String TransactionType = balance.getTransactionType();

			Integer wagersOn = balance.getWagersOn();
			Double bunkoResults = balance.getBunkoResults();
			Double recession = balance.getRecession();
			Double wagering = balance.getWagering();

			BalanceInfo mapBalance = retMap.get(TransactionType);
			if (mapBalance != null) {
				Integer mapWagersOn = mapBalance.getWagersOn();
				Double mapBunkoResults = mapBalance.getBunkoResults();
				Double mapRecession = mapBalance.getRecession();
				Double mapWagering = mapBalance.getWagering();
				mapBalance.setWagersOn(mapWagersOn + wagersOn);
				mapBalance.setBunkoResults(mapBunkoResults + bunkoResults);
				mapBalance.setRecession(mapRecession + recession);
				mapBalance.setWagering(mapWagering + wagering);

			} else
				retMap.put(TransactionType, balance);
		}

		return retMap;

	}

	private void updatePlayAmountAndShopRealOdds(List<BaseBet> ballList, String shopCode, String playTypeCode, String scheme) {

		List<PlayAmount> playAmountList = new ArrayList<PlayAmount>();
		Map<String, PlayAmount> playAmountMap = this.getPlayAmountByShopCodeAndPlayType(shopCode, playTypeCode, scheme);
		Map<String, OpenPlayOdds> openShopOddsMap = shopOddsLogic.queryOpenPlayOddsMapByShopCode(shopCode);
		for (int i = 0; i < ballList.size(); i++) {
			BaseBet bet = ballList.get(i);
			Integer money = bet.getMoney();
			Long userID = bet.getBettingUserId();
			String typeCode = bet.getPlayType();
			PlayType playType = PlayTypeUtils.getPlayType(typeCode);
			String oddType = playType.getOddsType();
			Integer chiefRate = bet.getChiefRate();
			String periodsNum = bet.getPeriodsNum();
			OpenPlayOdds openOdd = openShopOddsMap.get(oddType);
			Integer quotas = openOdd.getAutoOddsQuotas();
			BigDecimal autoMoney = openOdd.getAutoOdds();

			PlayAmount playAmount = playAmountMap.get(typeCode);

			Double moneyAmount = playAmount.getMoneyAmount();

			// fixed by peter 修改自动降赔阙值错误问题
			double replenishMoney = 0;
			ShopsInfo shopsInfo = userFrontDao.getShopInfoByShopCode(shopCode);
			if (null != shopsInfo) {
				if (null != shopsInfo.getChiefStaffExt()) {

					replenishMoney = replenishFrontDao.getChiefReplenishMoneyForBetCheck(shopsInfo.getChiefStaffExt().getID(), bet.getPlayType(), periodsNum, scheme);
				}
			}

			int moneysAfterBet = (int) (moneyAmount + replenishMoney + money * chiefRate / 100) / quotas;
			int moneyBeforeBet = (int) ((moneyAmount + replenishMoney) / quotas);
			if (moneysAfterBet > moneyBeforeBet) {
				// fixed by peter 增加补货的计算
				int quotaCount = moneysAfterBet - moneyBeforeBet;
				ShopsPlayOdds shopOdds = shopOddsLogic.queryShopPlayOdds(shopCode, typeCode);
				BigDecimal odds = shopOdds.getRealOdds();
				float realOdds = odds.floatValue() - autoMoney.floatValue() * quotaCount;
				if (realOdds < 0) {
					realOdds = 0f;
				}
				shopOdds.setRealOdds(new BigDecimal(realOdds));
				shopOddsLogic.updateShopOdds(shopOdds);
				// 增加赔率变化日志记录
				ShopsPlayOddsLog log = new ShopsPlayOddsLog();
				log.setRealOddsOrigin(odds);
				log.setShopCode(shopCode);
				log.setRealUpdateDateNew(new Date(System.currentTimeMillis()));
				log.setPlayTypeCode(typeCode);
				log.setRealOddsNew(new BigDecimal(realOdds));
				log.setRealUpdateUserNew(Integer.valueOf(userID + ""));
				log.setRealUpdateDateOrigin(shopOdds.getRealUpdateDate());
				log.setRealUpdateUserOrigin(shopOdds.getRealUpdateUser().intValue());
				log.setRemark("");

				log.setOddsType(shopOdds.getOddsType());
				log.setOddsTypeX(shopOdds.getOddsTypeX());
				log.setPeriodsNum(periodsNum);
				// 获取修改赔率者的IP地址
				String ip = "";
				if (getSpringRequest().getHeader("REQ_REAL_IP") == null) {
					ip = getSpringRequest().getRemoteAddr();
				} else {
					ip = getSpringRequest().getHeader("REQ_REAL_IP");
				}
				log.setIp(ip);
				log.setType(Constant.ODD_LOG_AUTO);
				shopOddsLogFrontDao.insert(log, scheme);
			}

			BigDecimal moneyAmountUpdate = BigDecimal.valueOf(playAmount.getMoneyAmount()).add(BigDecimal.valueOf(money * chiefRate).divide(BigDecimal.valueOf(100)));
			playAmount.setMoneyAmount(moneyAmountUpdate.doubleValue());

			playAmountList.add(playAmount);

		}
		// 批量更新playamount
		if (!CollectionUtils.isEmpty(playAmountList)) {
			playAmountFrontDao.updatePlayAmountBatchById(playAmountList, scheme);
		}
	}

	private HttpServletRequest getSpringRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	private Map<String, PlayAmount> getPlayAmountByShopCodeAndPlayType(String shopCode, String playType, String scheme) {
		Map<String, PlayAmount> playAmountMap = new HashMap<String, PlayAmount>();
		List<PlayAmount> playAmountList = playAmountFrontDao.getPlayAmountByShopCodeAndPlayType(shopCode, playType, scheme);
		for (PlayAmount playAmount : playAmountList) {
			playAmountMap.put(playAmount.getTypeCode(), playAmount);
		}
		return playAmountMap;
	}

	/**
	 * 江苏投注
	 * 
	 * @param ballList
	 * @param memberStaff
	 * @param shopCode
	 */
	public void saveJSSBBet(List<BaseBet> ballList, MemberStaffExt memberStaff, String shopCode, String scheme) {
		playBetFrontDao.batchInsert(ballList, Constant.K3_TABLE_NAME, false, scheme);
		updatePlayAmountAndShopRealOdds(ballList, shopCode, Constant.LOTTERY_TYPE_K3, scheme);

	}

	/**
	 * 农场投注保存方法
	 */
	public void saveNCBet(List<BaseBet> ballList, MemberStaffExt memberStaff, boolean insertAttr, String shopCode, String scheme) {
		playBetFrontDao.batchInsert(ballList, Constant.NC_TABLE_NAME, insertAttr, scheme);
		updatePlayAmountAndShopRealOdds(ballList, shopCode, Constant.LOTTERY_TYPE_NC, scheme);

	}

	/**
	 * 广东
	 * 
	 * @param betlList
	 * @param memberStaff
	 * @param shopCode
	 * @param scheme
	 */
	public void saveGDBet(List<BaseBet> betlList, MemberStaffExt memberStaff, String shopCode, String scheme) {

		List<BaseBet> first = new ArrayList<BaseBet>();
		List<BaseBet> second = new ArrayList<BaseBet>();
		List<BaseBet> third = new ArrayList<BaseBet>();
		List<BaseBet> forth = new ArrayList<BaseBet>();
		List<BaseBet> fifth = new ArrayList<BaseBet>();
		List<BaseBet> sixth = new ArrayList<BaseBet>();
		List<BaseBet> seventh = new ArrayList<BaseBet>();
		List<BaseBet> eighth = new ArrayList<BaseBet>();
		List<BaseBet> doubleSide = new ArrayList<BaseBet>();
		List<BaseBet> straightthrough = new ArrayList<BaseBet>();

		String typeCode = null;
		for (int i = 0; i < betlList.size(); i++) {
			BaseBet bet = betlList.get(i);
			typeCode = bet.getPlayType();
			bet.setRemark("double");
			if (typeCode.indexOf("BALL_FIRST_") != -1) {
				first.add(bet);
			} else if (typeCode.indexOf("BALL_SECOND_") != -1) {
				second.add(bet);
			} else if (typeCode.indexOf("BALL_THIRD_") != -1) {
				third.add(bet);
			} else if (typeCode.indexOf("BALL_FORTH_") != -1) {
				forth.add(bet);
			} else if (typeCode.indexOf("BALL_FIFTH_") != -1) {
				fifth.add(bet);
			} else if (typeCode.indexOf("BALL_SIXTH_") != -1) {
				sixth.add(bet);
			} else if (typeCode.indexOf("BALL_SEVENTH_") != -1) {
				seventh.add(bet);
			} else if (typeCode.indexOf("BALL_EIGHTH_") != -1) {
				eighth.add(bet);
			} else if (typeCode.indexOf("DOUBLESIDE_") != -1) {
				doubleSide.add(bet);

			} else if (typeCode.indexOf("STRAIGHTTHROUGH_") != -1) {
				straightthrough.add(bet);

			}

		}

		if (first.size() != 0) {
			playBetFrontDao.batchInsert(first, Constant.GDKLSF_BALL_FIRST_TABLE_NAME, false, scheme);
		}
		if (second.size() != 0) {
			playBetFrontDao.batchInsert(second, Constant.GDKLSF_BALL_SECOND_TABLE_NAME, false, scheme);
		}
		if (third.size() != 0) {
			playBetFrontDao.batchInsert(third, Constant.GDKLSF_BALL_THIRD_TABLE_NAME, false, scheme);
		}
		if (forth.size() != 0) {
			playBetFrontDao.batchInsert(forth, Constant.GDKLSF_BALL_FORTH_TABLE_NAME, false, scheme);
		}
		if (fifth.size() != 0) {
			playBetFrontDao.batchInsert(fifth, Constant.GDKLSF_BALL_FIFTH_TABLE_NAME, false, scheme);
		}
		if (sixth.size() != 0) {
			playBetFrontDao.batchInsert(sixth, Constant.GDKLSF_BALL_SIXTH_TABLE_NAME, false, scheme);
		}
		if (seventh.size() != 0) {
			playBetFrontDao.batchInsert(seventh, Constant.GDKLSF_BALL_SEVENTH_TABLE_NAME, false, scheme);
		}
		if (eighth.size() != 0) {
			playBetFrontDao.batchInsert(eighth, Constant.GDKLSF_BALL_EIGHTH_TABLE_NAME, false, scheme);
		}
		if (doubleSide.size() != 0) {
			playBetFrontDao.batchInsert(doubleSide, Constant.GDKLSF_DOUBLESIDE_TABLE_NAME, false, scheme);
		}

		if (straightthrough.size() != 0) {
			playBetFrontDao.batchInsert(straightthrough, Constant.GDKLSF_STRAIGHTTHROUGH_TABLE_NAME, true, scheme);
		}

		updatePlayAmountAndShopRealOdds(betlList, shopCode, Constant.LOTTERY_TYPE_GDKLSF, scheme);
	}

	/**
	 * 重庆投注
	 * 
	 * @param ballList
	 * @param memberStaff
	 * @param shopCode
	 * @param scheme
	 */
	public void saveCQBet(List<BaseBet> ballList, MemberStaffExt memberStaff, String shopCode, String scheme) {
		List<BaseBet> first = new ArrayList<BaseBet>();
		List<BaseBet> second = new ArrayList<BaseBet>();
		List<BaseBet> third = new ArrayList<BaseBet>();
		List<BaseBet> forth = new ArrayList<BaseBet>();
		List<BaseBet> fifth = new ArrayList<BaseBet>();

		String typeCode = null;
		for (int i = 0; i < ballList.size(); i++) {
			BaseBet bet = ballList.get(i);
			typeCode = bet.getPlayType();
			bet.setRemark("double");
			if (typeCode.indexOf("BALL_FIRST_") != -1 || typeCode.indexOf("DOUBLESIDE_1_") != -1) {
				first.add(bet);
			} else if (typeCode.indexOf("BALL_SECOND_") != -1 || typeCode.indexOf("DOUBLESIDE_2_") != -1) {
				second.add(bet);
			} else if (typeCode.indexOf("BALL_THIRD_") != -1 || typeCode.indexOf("DOUBLESIDE_3_") != -1) {
				third.add(bet);
			} else if (typeCode.indexOf("BALL_FORTH_") != -1 || typeCode.indexOf("DOUBLESIDE_4_") != -1) {
				forth.add(bet);
			} else if (typeCode.indexOf("BALL_FIFTH_") != -1 || typeCode.indexOf("DOUBLESIDE_5_") != -1) {
				fifth.add(bet);
			} else {
				first.add(bet);

			}
		}
		// memberStaffExtDao.save(memberStaff);
		if (fifth.size() != 0) {
			playBetFrontDao.batchInsert(fifth, Constant.CQSSC_BALL_FIFTH_TABLE_NAME, false, scheme);
		}
		if (forth.size() != 0) {
			playBetFrontDao.batchInsert(forth, Constant.CQSSC_BALL_FORTH_TABLE_NAME, false, scheme);
		}
		if (third.size() != 0) {
			playBetFrontDao.batchInsert(third, Constant.CQSSC_BALL_THIRD_TABLE_NAME, false, scheme);
		}
		if (second.size() != 0) {
			playBetFrontDao.batchInsert(second, Constant.CQSSC_BALL_SECOND_TABLE_NAME, false, scheme);
		}
		if (first.size() != 0) {
			playBetFrontDao.batchInsert(first, Constant.CQSSC_BALL_FIRST_TABLE_NAME, false, scheme);
		}
		updatePlayAmountAndShopRealOdds(ballList, shopCode, Constant.LOTTERY_TYPE_CQSSC, scheme);
	}

	/**
	 * 北京投注
	 * 
	 * @param ballList
	 * @param memberStaff
	 * @param shopCode
	 * @param scheme
	 */
	public void saveBJSCBet(List<BaseBet> ballList, MemberStaffExt memberStaff, String shopCode, String scheme) {
		playBetFrontDao.batchInsert(ballList, Constant.BJSC_TABLE_NAME, false, scheme);
		updatePlayAmountAndShopRealOdds(ballList, shopCode, Constant.LOTTERY_TYPE_BJ, scheme);

	}

	/**
	 * 通过报表获取用户可用额度
	 * 
	 * @param totalCreditLine
	 * @param userId
	 * @param scheme
	 * @return
	 */
	public double getMemberAvailableCreditLineRealTime(double totalCreditLine, long userId, String scheme) {
		// 当前下注金额
		double betTotal = 0;
		// 今日输赢
		double todayWin = 0;
		Page page = new Page();
		page.setPageSize(9999);
		page = bettingFrontDao.getUserBetDetail(page, userId, scheme);
		if (null != page) {
			betTotal = page.getTotal1();
			List<BaseBet> betList = page.getResult();
			final SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
			for (BaseBet bet : betList) {
				Date bettingDate = bet.getBettingDate();
				bettingDate = DateUtils.addHours(bettingDate, -2);
				Date now = DateUtils.addHours(new Date(), -2);
				if (!sf.format(bettingDate).equals(sf.format(now))) {
					betTotal = betTotal - (bet.getMoney() * bet.getCount());
				}
			}

		}

		todayWin = bettingFrontDao.getTodayWinMoney(userId, scheme);

		// 用户可用额度 = 信用额度+今日输赢-当前下注金额
		double availableCreditLine = new BigDecimal(totalCreditLine).add(new BigDecimal(todayWin)).subtract(new BigDecimal(betTotal)).doubleValue();
		if (availableCreditLine <= 0) {
			// 如果小于0,设置当前可用额度为0
			availableCreditLine = 0;
		}
		log.info("用户 : " + userId + " 可用信用额度为 : " + availableCreditLine);
		return availableCreditLine;
	}

	public Integer getUserLMitemQuotasMoney(String periodNum, String typeCode, String userId, String attr, String playType, String scheme) {
		int itemQuotasMoney = 0;
		String tableName = "";
		if (Constant.LOTTERY_TYPE_GDKLSF.equals(playType)) {
			tableName = "TB_GDKLSF_STRAIGHTTHROUGH";
		} else if (Constant.LOTTERY_TYPE_NC.equals(playType)) {
			tableName = "TB_NC";
		}
		if (StringUtils.isNotEmpty(tableName)) {
			itemQuotasMoney = bettingFrontDao.getUserLMitemQuotasMoney(periodNum, typeCode, userId, attr, tableName, scheme);
		}
		return itemQuotasMoney;
	}

	/**
	 * 自动补货操作
	 * 
	 * @param ballList
	 * @param memberStaff
	 * @param playType
	 *            玩法大类 常量传GDKLSF,CQSSC,BJ
	 */
	public void autoReplenish(final List<BaseBet> ballList, MemberStaffExt memberStaff, final String scheme) {

		wcpTaskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				log.info("商铺,shcheme"+scheme+"开始执行异步补货");
				try {
					List<BaseBet> result = new ArrayList<BaseBet>();
					result.addAll(ballList);
					for (BaseBet betOrder : result) {
						replenishAutoLogic.updateReplenishAutoForUser(betOrder, scheme);
					}
				} catch (Throwable e) {
					log.info("商铺,shcheme"+scheme+"自动补货异步调出错！" + e.getMessage());

				}

			}

		});
	}
}
