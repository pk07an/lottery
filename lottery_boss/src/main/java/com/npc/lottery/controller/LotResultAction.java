package com.npc.lottery.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.npc.lottery.boss.logic.interf.IBJSCPeriodsInfoBossLogic;
import com.npc.lottery.boss.logic.interf.ICQPeriodsInfoBossLogic;
import com.npc.lottery.boss.logic.interf.IGDPeriodsInfoBossLogic;
import com.npc.lottery.boss.logic.interf.INCPeriodsInfoBossLogic;
import com.npc.lottery.common.Constant;
import com.npc.lottery.common.action.BaseLotteryAction;
import com.npc.lottery.common.service.ShopSchemeService;
import com.npc.lottery.member.logic.interf.ILotteryResultLogic;
import com.npc.lottery.periods.entity.BJSCPeriodsInfo;
import com.npc.lottery.periods.entity.CQPeriodsInfo;
import com.npc.lottery.periods.entity.GDPeriodsInfo;
import com.npc.lottery.periods.entity.JSSBPeriodsInfo;
import com.npc.lottery.periods.entity.NCPeriodsInfo;
import com.npc.lottery.periods.logic.interf.IJSSBPeriodsInfoLogic;
import com.npc.lottery.statreport.entity.ReportStatus;
import com.npc.lottery.statreport.logic.interf.IClassReportEricLogic;
import com.npc.lottery.statreport.logic.interf.IReportStatusLogic;
import com.npc.lottery.statreport.logic.interf.ISettledReportEricLogic;
import com.npc.lottery.util.Page;

public class LotResultAction extends BaseLotteryAction {

	private static final long serialVersionUID = -9164690503809710500L;
	private static Logger logger = Logger.getLogger(LotResultAction.class);
	private IGDPeriodsInfoBossLogic gdPeriodsInfoBossLogic;
	private ICQPeriodsInfoBossLogic icqPeriodsInfoBossLogic;
	private IJSSBPeriodsInfoLogic jssbPeriodsInfoLogic;
	private IBJSCPeriodsInfoBossLogic bjscPeriodsInfoBossLogic;
	private INCPeriodsInfoBossLogic ncPeriodsInfoBossLogic;
	private ILotteryResultLogic lotteryResultLogic;
	private ISettledReportEricLogic settledReportEricLogic = null;// 报表统计
	private IClassReportEricLogic classReportEricLogic = null;// 报表统计
	private IReportStatusLogic reportStatusLogic;
	private GDPeriodsInfo gdPeriodsInfo;
	private NCPeriodsInfo ncPeriodsInfo;
	private CQPeriodsInfo cqPeriodsInfo;
	private BJSCPeriodsInfo bjscPeriodsInfo;
	private JSSBPeriodsInfo jssbPeriodsInfo;
	private String subType = "GDKLSF";
	private String state = "7";
	private ShopSchemeService shopSchemeService;

	public String enter() {
		if (subType.indexOf("CQSSC") != -1) {
			initCQSSCLotHistoryData(state);
			return "cqlotResultHistory";
		} else if (subType.indexOf("GDKLSF") != -1) {
			initGDKLSFLotHistoryData(state);
			return "gdlotResultHistory";
		} else if (subType.indexOf("NC") != -1) {
			initNCLotHistoryData(state);
			return "nclotResultHistory";
		} else if (Constant.LOTTERY_TYPE_BJSC.equals(subType)) {
			initBJSCLotHistoryData(state);
			return "bjlotResultHistory";
		} else {
			initJSSBLotHistoryData(state);
			return "k3lotResultHistory";
		}
	}

	private void initJSSBLotHistoryData(String state) {
		Page<JSSBPeriodsInfo> page = new Page<JSSBPeriodsInfo>();
		page.setPageSize(15);
		int pageNo = 1;
		if (this.getRequest().getParameter("pageNo") != null)
			pageNo = this.findParamInt("pageNo");
		page.setPageNo(pageNo);
		page = jssbPeriodsInfoLogic.queryHistoryPeriodsForBoss(page, state);
		this.getRequest().setAttribute("page", page);

	}

	private void initGDKLSFLotHistoryData(String state) {
		Page<GDPeriodsInfo> page = new Page<GDPeriodsInfo>(15);
		page.setPageSize(20);
		int pageNo = 1;
		if (this.getRequest().getParameter("pageNo") != null)
			pageNo = this.findParamInt("pageNo");
		page.setPageNo(pageNo);
		page = gdPeriodsInfoBossLogic.queryHistoryPeriodsForBoss(page, state);
		this.getRequest().setAttribute("page", page);
	}

	private void initNCLotHistoryData(String state) {
		Page<NCPeriodsInfo> page = new Page<NCPeriodsInfo>(15);
		page.setPageSize(20);
		int pageNo = 1;
		if (this.getRequest().getParameter("pageNo") != null)
			pageNo = this.findParamInt("pageNo");
		page.setPageNo(pageNo);
		page = ncPeriodsInfoBossLogic.queryHistoryPeriodsForBoss(page, state);
		this.getRequest().setAttribute("page", page);
	}

	private void initBJSCLotHistoryData(String state) {
		Page<BJSCPeriodsInfo> page = new Page<BJSCPeriodsInfo>();
		page.setPageSize(15);
		int pageNo = 1;
		if (this.getRequest().getParameter("pageNo") != null)
			pageNo = this.findParamInt("pageNo");
		page.setPageNo(pageNo);
		page = bjscPeriodsInfoBossLogic.queryHistoryPeriodsForBoss(page, state);
		this.getRequest().setAttribute("page", page);

	}

	private void initCQSSCLotHistoryData(String state) {
		Page<CQPeriodsInfo> page = new Page<CQPeriodsInfo>();
		page.setPageSize(15);
		int pageNo = 1;
		if (this.getRequest().getParameter("pageNo") != null)
			pageNo = this.findParamInt("pageNo");
		page.setPageNo(pageNo);
		page = icqPeriodsInfoBossLogic.queryHistoryPeriodsForBoss(page, state);
		this.getRequest().setAttribute("page", page);

	}

	public String queryGdklsfLotResult() {
		String periodsNum = "";
		if (getRequest().getParameter("periodsNum") != null) {
			periodsNum = (String) getRequest().getParameter("periodsNum");
		}
		gdPeriodsInfo = periodsInfoLogic.queryByPeriods("periodsNum", periodsNum);
		return SUCCESS;
	}

	public String queryNCLotResult() {
		String periodsNum = "";
		if (getRequest().getParameter("periodsNum") != null) {
			periodsNum = (String) getRequest().getParameter("periodsNum");
		}
		ncPeriodsInfo = ncPeriodsInfoLogic.queryByPeriods("periodsNum", periodsNum);
		return SUCCESS;
	}

	public String queryK3LotResult() {
		String periodsNum = "";
		if (getRequest().getParameter("periodsNum") != null) {
			periodsNum = (String) getRequest().getParameter("periodsNum");
		}
		List<Criterion> filtersPeriodInfo = new ArrayList<Criterion>();
		filtersPeriodInfo.add(Restrictions.eq("periodsNum", periodsNum));
		jssbPeriodsInfo = jssbPeriodsInfoLogic.queryByPeriods(filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo
		        .size()]));
		return SUCCESS;
	}

	public String queryCqsscLotResult() {
		String periodsNum = "";
		if (getRequest().getParameter("periodsNum") != null) {
			periodsNum = (String) getRequest().getParameter("periodsNum");
		}
		cqPeriodsInfo = icqPeriodsInfoLogic.queryByPeriods("periodsNum", periodsNum);
		return SUCCESS;
	}

	public String queryBjscLotResult() {
		String periodsNum = "";
		if (getRequest().getParameter("periodsNum") != null) {
			periodsNum = (String) getRequest().getParameter("periodsNum");
		}
		bjscPeriodsInfo = bjscPeriodsInfoLogic.queryByPeriods("periodsNum", periodsNum);
		return SUCCESS;
	}

	private void updateReportList(String periodNum, java.sql.Date quoteTime, String lotteryType, String schema) {
		// 重新计算报表START
		// 计算前先把状态改为N,是为了不影响客户查询
		ReportStatus reportStatus = reportStatusLogic.findReportStatus(schema);
		String status = "N";

		String today = new Date(new java.util.Date().getTime()).toString();
		String dateStr = quoteTime.toString();
		Boolean isToday = false;
		if (today.equals(dateStr)) {
			isToday = true;
		}

		try {
			// 计算前先把状态改为N,是为了不影响客户查询
			reportStatusLogic.updateReportStatus(status, schema);
			settledReportEricLogic.saveReportListForReCompute(periodNum, lotteryType, dateStr, isToday,
			        this.getSettledReportEricLogic(), this.getClassReportEricLogic(), this.getReportStatusLogic(),
			        schema);

			status = "Y";
			reportStatusLogic.updateReportStatus(status, schema);
		} catch (Exception e) {
			status = "N";
			reportStatusLogic.updateReportStatus(status, schema);
			logger.info("修改开奖结果时重新生成统计报表数据 异常，提示错误：" + e.getMessage());
		}
	}

	public String modifyGdklsfLotResult() {
		try {
			List<Integer> winNums = new ArrayList<Integer>();
			winNums.add(gdPeriodsInfo.getResult1());
			winNums.add(gdPeriodsInfo.getResult2());
			winNums.add(gdPeriodsInfo.getResult3());
			winNums.add(gdPeriodsInfo.getResult4());
			winNums.add(gdPeriodsInfo.getResult5());
			winNums.add(gdPeriodsInfo.getResult6());
			winNums.add(gdPeriodsInfo.getResult7());
			winNums.add(gdPeriodsInfo.getResult8());

			GDPeriodsInfo periods = periodsInfoLogic.queryByPeriods("periodsNum", gdPeriodsInfo.getPeriodsNum());
			/**
			 * 如果是没有获得开奖结果的，会把投注表数据移至历史表，并调整可用余额和今天输赢
			 * 如果是已经有开奖结果，而修改开奖结果的，对历史表操作，并调整可用余额和今天输赢
			 */
			lotteryResultLogic.updatePlayTypeWin(gdPeriodsInfo.getPeriodsNum(), winNums, Constant.LOTTERY_TYPE_GDKLSF);
			// 获取商铺对应的scheme列表
			Map<String, String> shopSchemeMap = shopSchemeService.getShopSchemeMap();

			// 每一个商铺按每期进去兑奖
			for (Entry<String, String> shopScheme : shopSchemeMap.entrySet()) {
				String scheme = shopScheme.getValue();
				lotteryResultLogic.updateGDForReLottery(gdPeriodsInfo.getPeriodsNum(), winNums,
				        periods.getOpenQuotTime(), scheme);
			}

			// 把该盘期的状态修改为兑奖成功
			gdPeriodsInfo.setState(Constant.SCAN_SUC_STATUS);
			periodsInfoLogic.updateLotResult(gdPeriodsInfo);

			// systemLogic.updateGDAutoOdds();//根据系统初始化里处理遗漏及相关
			// 重新计算报表
			for (Entry<String, String> shopScheme : shopSchemeMap.entrySet()) {
				String scheme = shopScheme.getValue();
				java.sql.Date quoteTime = new Date(periods.getOpenQuotTime().getTime());
				this.updateReportList(gdPeriodsInfo.getPeriodsNum(), quoteTime, Constant.LOTTERY_TYPE_GDKLSF, scheme);
			}
			subType = "";
			logger.info("修改廣東開獎結果成功。");
		} catch (Exception e) {
			logger.info("修改廣東開獎結果錯誤。");
			e.printStackTrace();
			return INPUT;
		}
		return SUCCESS;
	}

	public String modifyGdklsfHistoryResult() {
		try {
			List<Integer> winNums = new ArrayList<Integer>();
			winNums.add(gdPeriodsInfo.getResult1());
			winNums.add(gdPeriodsInfo.getResult2());
			winNums.add(gdPeriodsInfo.getResult3());
			winNums.add(gdPeriodsInfo.getResult4());
			winNums.add(gdPeriodsInfo.getResult5());
			winNums.add(gdPeriodsInfo.getResult6());
			winNums.add(gdPeriodsInfo.getResult7());
			winNums.add(gdPeriodsInfo.getResult8());

			GDPeriodsInfo periods = periodsInfoLogic.queryByPeriods("periodsNum", gdPeriodsInfo.getPeriodsNum());
			/**
			 * 如果是没有获得开奖结果的，会把投注表数据移至历史表，并调整可用余额和今天输赢
			 * 如果是已经有开奖结果，而修改开奖结果的，对历史表操作，并调整可用余额和今天输赢
			 */
			// 获取商铺对应的scheme列表
			Map<String, String> shopSchemeMap = shopSchemeService.getShopSchemeMap();
			lotteryResultLogic.updatePlayTypeWin(gdPeriodsInfo.getPeriodsNum(), winNums, Constant.LOTTERY_TYPE_GDKLSF);

			// 每一个商铺按每期进去兑奖
			for (Entry<String, String> shopScheme : shopSchemeMap.entrySet()) {
				String scheme = shopScheme.getValue();
				lotteryResultLogic.updateSecondLotteryGD(gdPeriodsInfo.getPeriodsNum(), winNums,
				        periods.getOpenQuotTime(), scheme);
			}

			// gdPeriodsInfo.setState(Constant.LOTTERY_STATUS);
			// periodsInfoLogic.updateLotResult(gdPeriodsInfo);

			// 把该盘期的状态修改为兑奖成功
			gdPeriodsInfo.setState(Constant.SCAN_SUC_STATUS);
			periodsInfoLogic.updateLotResult(gdPeriodsInfo);

			// systemLogic.updateGDAutoOdds();//根据系统初始化里处理遗漏及相关
			// 重新计算报表
			// 待峰处理
			for (Entry<String, String> shopScheme : shopSchemeMap.entrySet()) {
				String scheme = shopScheme.getValue();
				java.sql.Date quoteTime = new Date(periods.getOpenQuotTime().getTime());
				this.updateReportList(gdPeriodsInfo.getPeriodsNum(), quoteTime, Constant.LOTTERY_TYPE_GDKLSF, scheme);
			}
			subType = "";
			logger.info("修改廣東開獎結果成功。");
		} catch (Exception e) {
			logger.info("修改廣東開獎結果錯誤。");
			e.printStackTrace();
			return INPUT;
		}
		return SUCCESS;
	}

	public String modifyNCLotResult() {
		try {
			List<Integer> winNums = new ArrayList<Integer>();
			winNums.add(ncPeriodsInfo.getResult1());
			winNums.add(ncPeriodsInfo.getResult2());
			winNums.add(ncPeriodsInfo.getResult3());
			winNums.add(ncPeriodsInfo.getResult4());
			winNums.add(ncPeriodsInfo.getResult5());
			winNums.add(ncPeriodsInfo.getResult6());
			winNums.add(ncPeriodsInfo.getResult7());
			winNums.add(ncPeriodsInfo.getResult8());

			NCPeriodsInfo periods = ncPeriodsInfoLogic.queryByPeriods("periodsNum", ncPeriodsInfo.getPeriodsNum());
			/**
			 * 如果是没有获得开奖结果的，会把投注表数据移至历史表，并调整可用余额和今天输赢
			 * 如果是已经有开奖结果，而修改开奖结果的，对历史表操作，并调整可用余额和今天输赢
			 */
			// 获取商铺对应的scheme列表
			Map<String, String> shopSchemeMap = shopSchemeService.getShopSchemeMap();
			lotteryResultLogic.updatePlayTypeWin(ncPeriodsInfo.getPeriodsNum(), winNums, Constant.LOTTERY_TYPE_NC);

			// 每一个商铺按每期进去兑奖
			for (Entry<String, String> shopScheme : shopSchemeMap.entrySet()) {
				String scheme = shopScheme.getValue();
				lotteryResultLogic.updateNCForReLottery(ncPeriodsInfo.getPeriodsNum(), winNums,
				        periods.getOpenQuotTime(), scheme);
			}
			// 把该盘期的状态修改为兑奖成功
			ncPeriodsInfo.setState(Constant.SCAN_SUC_STATUS);
			ncPeriodsInfoLogic.updateLotResult(ncPeriodsInfo);

			// systemLogic.updateNCAutoOdds();//根据系统初始化里处理遗漏及相关

			// 重新计算报表
			for (Entry<String, String> shopScheme : shopSchemeMap.entrySet()) {
				String scheme = shopScheme.getValue();
				java.sql.Date quoteTime = new Date(periods.getOpenQuotTime().getTime());
				this.updateReportList(ncPeriodsInfo.getPeriodsNum(), quoteTime, Constant.LOTTERY_TYPE_NC, scheme);
			}
			subType = Constant.LOTTERY_TYPE_NC;
			logger.info("修改NC開獎結果成功。");
		} catch (Exception e) {
			logger.info("修改NC開獎結果錯誤。");
			e.printStackTrace();
			return INPUT;
		}
		return SUCCESS;
	}

	public String modifyNCHistoryResult() {
		try {
			List<Integer> winNums = new ArrayList<Integer>();
			winNums.add(ncPeriodsInfo.getResult1());
			winNums.add(ncPeriodsInfo.getResult2());
			winNums.add(ncPeriodsInfo.getResult3());
			winNums.add(ncPeriodsInfo.getResult4());
			winNums.add(ncPeriodsInfo.getResult5());
			winNums.add(ncPeriodsInfo.getResult6());
			winNums.add(ncPeriodsInfo.getResult7());
			winNums.add(ncPeriodsInfo.getResult8());

			NCPeriodsInfo periods = ncPeriodsInfoLogic.queryByPeriods("periodsNum", ncPeriodsInfo.getPeriodsNum());
			/**
			 * 如果是没有获得开奖结果的，会把投注表数据移至历史表，并调整可用余额和今天输赢
			 * 如果是已经有开奖结果，而修改开奖结果的，对历史表操作，并调整可用余额和今天输赢
			 */
			// 获取商铺对应的scheme列表
			Map<String, String> shopSchemeMap = shopSchemeService.getShopSchemeMap();
			lotteryResultLogic.updatePlayTypeWin(ncPeriodsInfo.getPeriodsNum(), winNums, Constant.LOTTERY_TYPE_NC);
			// 每一个商铺按每期进去兑奖
			for (Entry<String, String> shopScheme : shopSchemeMap.entrySet()) {
				String scheme = shopScheme.getValue();
				lotteryResultLogic.updateSecondLotteryNC(ncPeriodsInfo.getPeriodsNum(), winNums,
				        periods.getOpenQuotTime(), scheme);
			}
			// 把该盘期的状态修改为兑奖成功
			ncPeriodsInfo.setState(Constant.SCAN_SUC_STATUS);
			ncPeriodsInfoLogic.updateLotResult(ncPeriodsInfo);

			// systemLogic.updateNCAutoOdds();//根据系统初始化里处理遗漏及相关
			for (Entry<String, String> shopScheme : shopSchemeMap.entrySet()) {
				String scheme = shopScheme.getValue();
				// 重新计算报表
				java.sql.Date quoteTime = new Date(periods.getOpenQuotTime().getTime());
				this.updateReportList(ncPeriodsInfo.getPeriodsNum(), quoteTime, Constant.LOTTERY_TYPE_NC, scheme);
			}
			subType = Constant.LOTTERY_TYPE_NC;
			logger.info("修改NC開獎結果成功。");
		} catch (Exception e) {
			logger.info("修改NC開獎結果錯誤。");
			e.printStackTrace();
			return INPUT;
		}
		return SUCCESS;
	}

	public String modifyK3LotResult() {
		try {
			List<Integer> winNums = new ArrayList<Integer>();
			winNums.add(jssbPeriodsInfo.getResult1());
			winNums.add(jssbPeriodsInfo.getResult2());
			winNums.add(jssbPeriodsInfo.getResult3());
			JSSBPeriodsInfo periods = jssbPeriodsInfoLogic
			        .queryByPeriods("periodsNum", jssbPeriodsInfo.getPeriodsNum());
			/**
			 * 如果是没有获得开奖结果的，会把投注表数据移至历史表，并调整可用余额和今天输赢
			 * 如果是已经有开奖结果，而修改开奖结果的，对历史表操作，并调整可用余额和今天输赢
			 */
			// 获取商铺对应的scheme列表
			Map<String, String> shopSchemeMap = shopSchemeService.getShopSchemeMap();

			lotteryResultLogic.updatePlayTypeWin(jssbPeriodsInfo.getPeriodsNum(), winNums, Constant.LOTTERY_TYPE_K3);
			// 每一个商铺按每期进去兑奖
			for (Entry<String, String> shopScheme : shopSchemeMap.entrySet()) {
				String scheme = shopScheme.getValue();
				lotteryResultLogic.updateJSForReLottery(jssbPeriodsInfo.getPeriodsNum(), winNums,
				        periods.getOpenQuotTime(), scheme);
			}
			/*
			 * jssbPeriodsInfo.setState(Constant.LOTTERY_STATUS);
			 * jssbPeriodsInfoLogic.updateK3LotResult(jssbPeriodsInfo);
			 */

			// 把该盘期的状态修改为兑奖成功
			jssbPeriodsInfo.setState(Constant.SCAN_SUC_STATUS);
			jssbPeriodsInfoLogic.updateK3LotResult(jssbPeriodsInfo);

			// 根据系统初始化里处理遗漏及相关,K3没有这项功能
			for (Entry<String, String> shopScheme : shopSchemeMap.entrySet()) {
				String scheme = shopScheme.getValue();
				// 重新计算报表
				java.sql.Date quoteTime = new Date(periods.getOpenQuotTime().getTime());
				this.updateReportList(jssbPeriodsInfo.getPeriodsNum(), quoteTime, Constant.LOTTERY_TYPE_K3, scheme);
			}
			subType = Constant.LOTTERY_TYPE_K3;
			logger.info("修改快3開獎結果成功。");
		} catch (Exception e) {
			logger.info("修改快3開獎結果錯誤。");
			e.printStackTrace();
			return INPUT;
		}
		return SUCCESS;
	}

	public String modifyK3HistoryResult() {
		try {
			List<Integer> winNums = new ArrayList<Integer>();
			winNums.add(jssbPeriodsInfo.getResult1());
			winNums.add(jssbPeriodsInfo.getResult2());
			winNums.add(jssbPeriodsInfo.getResult3());

			JSSBPeriodsInfo periods = jssbPeriodsInfoLogic
			        .queryByPeriods("periodsNum", jssbPeriodsInfo.getPeriodsNum());
			/**
			 * 如果是没有获得开奖结果的，会把投注表数据移至历史表，并调整可用余额和今天输赢
			 * 如果是已经有开奖结果，而修改开奖结果的，对历史表操作，并调整可用余额和今天输赢
			 */
			// 获取商铺对应的scheme列表
			Map<String, String> shopSchemeMap = shopSchemeService.getShopSchemeMap();
			lotteryResultLogic.updatePlayTypeWin(jssbPeriodsInfo.getPeriodsNum(), winNums, Constant.LOTTERY_TYPE_K3);

			// 每一个商铺按每期进去兑奖
			for (Entry<String, String> shopScheme : shopSchemeMap.entrySet()) {
				String scheme = shopScheme.getValue();
				lotteryResultLogic.updateSecondLotteryJS(jssbPeriodsInfo.getPeriodsNum(), winNums,
				        periods.getOpenQuotTime(), scheme);
			}
			// 把该盘期的状态修改为兑奖成功
			jssbPeriodsInfo.setState(Constant.SCAN_SUC_STATUS);
			jssbPeriodsInfoLogic.updateK3LotResult(jssbPeriodsInfo);

			// 根据系统初始化里处理遗漏及相关,K3没有这项功能
			for (Entry<String, String> shopScheme : shopSchemeMap.entrySet()) {
				String scheme = shopScheme.getValue();
				// 重新计算报表
				java.sql.Date quoteTime = new Date(periods.getOpenQuotTime().getTime());
				this.updateReportList(jssbPeriodsInfo.getPeriodsNum(), quoteTime, Constant.LOTTERY_TYPE_K3, scheme);
			}
			subType = Constant.LOTTERY_TYPE_K3;
			logger.info("修改快3開獎結果成功。");
		} catch (Exception e) {
			logger.info("修改快3開獎結果錯誤。");
			e.printStackTrace();
			return INPUT;
		}
		return SUCCESS;
	}

	public String modifyBjscLotResult() {
		try {
			List<Integer> winNums = new ArrayList<Integer>();
			winNums.add(bjscPeriodsInfo.getResult1());
			winNums.add(bjscPeriodsInfo.getResult2());
			winNums.add(bjscPeriodsInfo.getResult3());
			winNums.add(bjscPeriodsInfo.getResult4());
			winNums.add(bjscPeriodsInfo.getResult5());
			winNums.add(bjscPeriodsInfo.getResult6());
			winNums.add(bjscPeriodsInfo.getResult7());
			winNums.add(bjscPeriodsInfo.getResult8());
			winNums.add(bjscPeriodsInfo.getResult9());
			winNums.add(bjscPeriodsInfo.getResult10());

			BJSCPeriodsInfo periods = bjscPeriodsInfoLogic
			        .queryByPeriods("periodsNum", bjscPeriodsInfo.getPeriodsNum());
			/**
			 * 如果是没有获得开奖结果的，会把投注表数据移至历史表，并调整可用余额和今天输赢
			 * 如果是已经有开奖结果，而修改开奖结果的，对历史表操作，并调整可用余额和今天输赢
			 */
			// 获取商铺对应的scheme列表
			Map<String, String> shopSchemeMap = shopSchemeService.getShopSchemeMap();

			lotteryResultLogic.updatePlayTypeWin(bjscPeriodsInfo.getPeriodsNum(), winNums, Constant.LOTTERY_TYPE_BJSC);
			// 每一个商铺按每期进去兑奖
			for (Entry<String, String> shopScheme : shopSchemeMap.entrySet()) {
				String scheme = shopScheme.getValue();
				lotteryResultLogic.updateBJForReLottery(bjscPeriodsInfo.getPeriodsNum(), winNums,
				        periods.getOpenQuotTime(), scheme);
			}

			// 把该盘期的状态修改为兑奖成功
			bjscPeriodsInfo.setState(Constant.SCAN_SUC_STATUS);
			bjscPeriodsInfoLogic.updateLotResult(bjscPeriodsInfo);
			for (Entry<String, String> shopScheme : shopSchemeMap.entrySet()) {
				String scheme = shopScheme.getValue();
				// 重新计算报表
				java.sql.Date quoteTime = new Date(periods.getOpenQuotTime().getTime());
				this.updateReportList(bjscPeriodsInfo.getPeriodsNum(), quoteTime, Constant.LOTTERY_TYPE_BJ, scheme);
			}
			subType = Constant.LOTTERY_TYPE_BJSC;
			logger.info("修改北京賽車開獎結果成功。");
		} catch (Exception e) {
			logger.info("修改北京賽車開獎結果錯誤。");
			e.printStackTrace();
			return INPUT;
		}
		return SUCCESS;
	}

	public String modifyBjscHistoryResult() {
		try {
			List<Integer> winNums = new ArrayList<Integer>();
			winNums.add(bjscPeriodsInfo.getResult1());
			winNums.add(bjscPeriodsInfo.getResult2());
			winNums.add(bjscPeriodsInfo.getResult3());
			winNums.add(bjscPeriodsInfo.getResult4());
			winNums.add(bjscPeriodsInfo.getResult5());
			winNums.add(bjscPeriodsInfo.getResult6());
			winNums.add(bjscPeriodsInfo.getResult7());
			winNums.add(bjscPeriodsInfo.getResult8());
			winNums.add(bjscPeriodsInfo.getResult9());
			winNums.add(bjscPeriodsInfo.getResult10());

			BJSCPeriodsInfo periods = bjscPeriodsInfoLogic
			        .queryByPeriods("periodsNum", bjscPeriodsInfo.getPeriodsNum());
			/**
			 * 如果是没有获得开奖结果的，会把投注表数据移至历史表，并调整可用余额和今天输赢
			 * 如果是已经有开奖结果，而修改开奖结果的，对历史表操作，并调整可用余额和今天输赢
			 */
			// 获取商铺对应的scheme列表
			Map<String, String> shopSchemeMap = shopSchemeService.getShopSchemeMap();
			lotteryResultLogic.updatePlayTypeWin(bjscPeriodsInfo.getPeriodsNum(), winNums, Constant.LOTTERY_TYPE_BJSC);
			// 每一个商铺按每期进去兑奖
			for (Entry<String, String> shopScheme : shopSchemeMap.entrySet()) {
				String scheme = shopScheme.getValue();
				lotteryResultLogic.updateSecondLotteryBJ(bjscPeriodsInfo.getPeriodsNum(), winNums,
				        periods.getOpenQuotTime(), scheme);
			}

			// bjscPeriodsInfo.setState(Constant.LOTTERY_STATUS);
			// bjscPeriodsInfoLogic.updateLotResult(bjscPeriodsInfo);

			// 把该盘期的状态修改为兑奖成功
			bjscPeriodsInfo.setState(Constant.SCAN_SUC_STATUS);
			bjscPeriodsInfoLogic.updateLotResult(bjscPeriodsInfo);
			for (Entry<String, String> shopScheme : shopSchemeMap.entrySet()) {
				String scheme = shopScheme.getValue();
				// 重新计算报表
				java.sql.Date quoteTime = new Date(periods.getOpenQuotTime().getTime());
				this.updateReportList(bjscPeriodsInfo.getPeriodsNum(), quoteTime, Constant.LOTTERY_TYPE_BJ, scheme);
			}
			subType = Constant.LOTTERY_TYPE_BJSC;
			logger.info("修改北京賽車開獎結果成功。");
		} catch (Exception e) {
			logger.info("修改北京賽車開獎結果錯誤。");
			e.printStackTrace();
			return INPUT;
		}
		return SUCCESS;
	}

	public String modifyCqsscLotResult() {
		try {
			List<Integer> winNums = new ArrayList<Integer>();
			winNums.add(cqPeriodsInfo.getResult1());
			winNums.add(cqPeriodsInfo.getResult2());
			winNums.add(cqPeriodsInfo.getResult3());
			winNums.add(cqPeriodsInfo.getResult4());
			winNums.add(cqPeriodsInfo.getResult5());

			CQPeriodsInfo periods = icqPeriodsInfoLogic.queryByPeriods("periodsNum", cqPeriodsInfo.getPeriodsNum());
			/**
			 * 如果是没有获得开奖结果的，会把投注表数据移至历史表，并调整可用余额和今天输赢
			 * 如果是已经有开奖结果，而修改开奖结果的，对历史表操作，并调整可用余额和今天输赢
			 */
			// 获取商铺对应的scheme列表
			Map<String, String> shopSchemeMap = shopSchemeService.getShopSchemeMap();

			lotteryResultLogic.updatePlayTypeWin(cqPeriodsInfo.getPeriodsNum(), winNums, Constant.LOTTERY_TYPE_CQSSC);
			// 每一个商铺按每期进去兑奖
			for (Entry<String, String> shopScheme : shopSchemeMap.entrySet()) {
				String scheme = shopScheme.getValue();

				lotteryResultLogic.updateCQForReLottery(cqPeriodsInfo.getPeriodsNum(), winNums,
				        periods.getOpenQuotTime(), scheme);
			}

			// 把该盘期的状态修改为兑奖成功
			cqPeriodsInfo.setState(Constant.SCAN_SUC_STATUS);
			icqPeriodsInfoLogic.updateLotResult(cqPeriodsInfo);
			for (Entry<String, String> shopScheme : shopSchemeMap.entrySet()) {
				String scheme = shopScheme.getValue();
				// 重新计算报表
				java.sql.Date quoteTime = new Date(periods.getOpenQuotTime().getTime());
				this.updateReportList(cqPeriodsInfo.getPeriodsNum(), quoteTime, Constant.LOTTERY_TYPE_CQSSC, scheme);
			}
			subType = "CQSSC";
			logger.info("修改重慶開獎結果成功。");
		} catch (Exception e) {
			logger.info("修改重慶開獎結果錯誤。");
			e.printStackTrace();
			return INPUT;
		}

		return SUCCESS;
	}

	public String modifyCqsscHistoryResult() {
		try {
			List<Integer> winNums = new ArrayList<Integer>();
			winNums.add(cqPeriodsInfo.getResult1());
			winNums.add(cqPeriodsInfo.getResult2());
			winNums.add(cqPeriodsInfo.getResult3());
			winNums.add(cqPeriodsInfo.getResult4());
			winNums.add(cqPeriodsInfo.getResult5());

			CQPeriodsInfo periods = icqPeriodsInfoLogic.queryByPeriods("periodsNum", cqPeriodsInfo.getPeriodsNum());
			/**
			 * 如果是没有获得开奖结果的，会把投注表数据移至历史表，并调整可用余额和今天输赢
			 * 如果是已经有开奖结果，而修改开奖结果的，对历史表操作，并调整可用余额和今天输赢
			 */
			// 获取商铺对应的scheme列表
			Map<String, String> shopSchemeMap = shopSchemeService.getShopSchemeMap();
			lotteryResultLogic.updatePlayTypeWin(cqPeriodsInfo.getPeriodsNum(), winNums, Constant.LOTTERY_TYPE_CQSSC);

			// 每一个商铺按每期进去兑奖
			for (Entry<String, String> shopScheme : shopSchemeMap.entrySet()) {
				String scheme = shopScheme.getValue();
				lotteryResultLogic.updateSecondLotteryCQ(cqPeriodsInfo.getPeriodsNum(), winNums,
				        periods.getOpenQuotTime(), scheme);
			}
			// cqPeriodsInfo.setState(Constant.LOTTERY_STATUS);
			// icqPeriodsInfoLogic.updateLotResult(cqPeriodsInfo);

			// 把该盘期的状态修改为兑奖成功
			cqPeriodsInfo.setState(Constant.SCAN_SUC_STATUS);
			icqPeriodsInfoLogic.updateLotResult(cqPeriodsInfo);
			for (Entry<String, String> shopScheme : shopSchemeMap.entrySet()) {
				String scheme = shopScheme.getValue();
				// 重新计算报表
				java.sql.Date quoteTime = new Date(periods.getOpenQuotTime().getTime());
				this.updateReportList(cqPeriodsInfo.getPeriodsNum(), quoteTime, Constant.LOTTERY_TYPE_CQSSC, scheme);
			}
			subType = "CQSSC";
			logger.info("修改重慶開獎結果成功。");
		} catch (Exception e) {
			logger.info("修改重慶開獎結果錯誤。");
			e.printStackTrace();
			return INPUT;
		}

		return SUCCESS;
	}

	public GDPeriodsInfo getGdPeriodsInfo() {
		return gdPeriodsInfo;
	}

	public void setGdPeriodsInfo(GDPeriodsInfo gdPeriodsInfo) {
		this.gdPeriodsInfo = gdPeriodsInfo;
	}

	public NCPeriodsInfo getNcPeriodsInfo() {
		return ncPeriodsInfo;
	}

	public void setNcPeriodsInfo(NCPeriodsInfo ncPeriodsInfo) {
		this.ncPeriodsInfo = ncPeriodsInfo;
	}

	public CQPeriodsInfo getCqPeriodsInfo() {
		return cqPeriodsInfo;
	}

	public void setCqPeriodsInfo(CQPeriodsInfo cqPeriodsInfo) {
		this.cqPeriodsInfo = cqPeriodsInfo;
	}

	public BJSCPeriodsInfo getBjscPeriodsInfo() {
		return bjscPeriodsInfo;
	}

	public void setBjscPeriodsInfo(BJSCPeriodsInfo bjscPeriodsInfo) {
		this.bjscPeriodsInfo = bjscPeriodsInfo;
	}

	public JSSBPeriodsInfo getJssbPeriodsInfo() {
		return jssbPeriodsInfo;
	}

	public void setJssbPeriodsInfo(JSSBPeriodsInfo jssbPeriodsInfo) {
		this.jssbPeriodsInfo = jssbPeriodsInfo;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public ICQPeriodsInfoBossLogic getIcqPeriodsInfoBossLogic() {
		return icqPeriodsInfoBossLogic;
	}

	public IGDPeriodsInfoBossLogic getGdPeriodsInfoBossLogic() {
		return gdPeriodsInfoBossLogic;
	}

	public void setGdPeriodsInfoBossLogic(IGDPeriodsInfoBossLogic gdPeriodsInfoBossLogic) {
		this.gdPeriodsInfoBossLogic = gdPeriodsInfoBossLogic;
	}

	public IBJSCPeriodsInfoBossLogic getBjscPeriodsInfoBossLogic() {
		return bjscPeriodsInfoBossLogic;
	}

	public void setIcqPeriodsInfoBossLogic(ICQPeriodsInfoBossLogic icqPeriodsInfoBossLogic) {
		this.icqPeriodsInfoBossLogic = icqPeriodsInfoBossLogic;
	}

	public IJSSBPeriodsInfoLogic getJssbPeriodsInfoLogic() {
		return jssbPeriodsInfoLogic;
	}

	public void setJssbPeriodsInfoLogic(IJSSBPeriodsInfoLogic jssbPeriodsInfoLogic) {
		this.jssbPeriodsInfoLogic = jssbPeriodsInfoLogic;
	}

	public void setBjscPeriodsInfoBossLogic(IBJSCPeriodsInfoBossLogic bjscPeriodsInfoBossLogic) {
		this.bjscPeriodsInfoBossLogic = bjscPeriodsInfoBossLogic;
	}

	public INCPeriodsInfoBossLogic getNcPeriodsInfoBossLogic() {
		return ncPeriodsInfoBossLogic;
	}

	public void setNcPeriodsInfoBossLogic(INCPeriodsInfoBossLogic ncPeriodsInfoBossLogic) {
		this.ncPeriodsInfoBossLogic = ncPeriodsInfoBossLogic;
	}

	public ILotteryResultLogic getLotteryResultLogic() {
		return lotteryResultLogic;
	}

	public void setLotteryResultLogic(ILotteryResultLogic lotteryResultLogic) {
		this.lotteryResultLogic = lotteryResultLogic;
	}

	public ISettledReportEricLogic getSettledReportEricLogic() {
		return settledReportEricLogic;
	}

	public void setSettledReportEricLogic(ISettledReportEricLogic settledReportEricLogic) {
		this.settledReportEricLogic = settledReportEricLogic;
	}

	public IClassReportEricLogic getClassReportEricLogic() {
		return classReportEricLogic;
	}

	public void setClassReportEricLogic(IClassReportEricLogic classReportEricLogic) {
		this.classReportEricLogic = classReportEricLogic;
	}

	public IReportStatusLogic getReportStatusLogic() {
		return reportStatusLogic;
	}

	public void setReportStatusLogic(IReportStatusLogic reportStatusLogic) {
		this.reportStatusLogic = reportStatusLogic;
	}

	public ShopSchemeService getShopSchemeService() {
		return shopSchemeService;
	}

	public void setShopSchemeService(ShopSchemeService shopSchemeService) {
		this.shopSchemeService = shopSchemeService;
	}

}
