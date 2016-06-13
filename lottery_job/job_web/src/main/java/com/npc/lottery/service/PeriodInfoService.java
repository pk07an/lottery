package com.npc.lottery.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.npc.lottery.common.Constant;
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

@Service
public class PeriodInfoService {
	@Autowired
	private IGDPeriodsInfoLogic gdPeriodsInfoLogic;
	@Autowired
	private ICQPeriodsInfoLogic cqPeriodsInfoLogic;
	@Autowired
	private IBJSCPeriodsInfoLogic bjscPeriodsInfoLogic;
	@Autowired
	private INCPeriodsInfoLogic ncPeriodsInfoLogic;
	@Autowired
	private IJSSBPeriodsInfoLogic jssbPeriodsInfoLogic;

	@Autowired
	private MemCachedService memCachedService;

	private static final String TODAY_PERIOD_GDKLSF = "_PERIOD_GDKLSF";
	private static final String TODAY_PERIOD_BJSC = "_PERIOD_BJSC";
	private static final String TODAY_PERIOD_K3 = "_PERIOD_K3";
	private static final String TODAY_PERIOD_CQSSC = "_PERIOD_CQSSC";
	private static final String TODAY_PERIOD_NC = "_PERIOD_NC";

	private static final Logger log = Logger.getLogger(PeriodInfoService.class);

	private Map<String, String> setValue(String periodsNum, long openQuoteTime, long stopQuoteTime, long lotteryTime) {
		Map<String, String> resultMap = new HashMap<String, String>();

		resultMap.put("periodsNum", String.valueOf(periodsNum));
		resultMap.put("openQuotTime", String.valueOf(openQuoteTime));
		resultMap.put("stopQuotTime", String.valueOf(stopQuoteTime));
		resultMap.put("lotteryTime", String.valueOf(lotteryTime));
		return resultMap;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getTodayPeridoInfoByPlayType(String playType) {
		String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
		if (Constant.LOTTERY_TYPE_GDKLSF.equalsIgnoreCase(playType)) {

			// 先从缓存里取
			List<GDPeriodsInfo> todayPeriodList = (List<GDPeriodsInfo>) memCachedService.getTodayPeriodInfoListByKey(date + TODAY_PERIOD_GDKLSF);
			if (CollectionUtils.isEmpty(todayPeriodList)) {
				log.debug("广东在cached获取当天盘期失败,从数据库获取");
				// 如果没有,从数据库里取,并更新缓存
				todayPeriodList = gdPeriodsInfoLogic.queryTodayAllPeriods();
				if (!CollectionUtils.isEmpty(todayPeriodList)) {
					// 数据库获取成功
					log.debug("广东从数据库获取当天盘期成功");
					// 更新缓存
					memCachedService.setTodayPeriodInfoList(date + TODAY_PERIOD_GDKLSF, todayPeriodList);
				}
			}
			for (GDPeriodsInfo periodInfo : todayPeriodList) {
				Map<String, String> resultMap = this.setValue(periodInfo.getPeriodsNum(), periodInfo.getOpenQuotTime().getTime(), periodInfo.getStopQuotTime().getTime(), periodInfo.getLotteryTime()
				        .getTime());
				resultList.add(resultMap);

			}
		} else if (Constant.LOTTERY_TYPE_BJSC.equalsIgnoreCase(playType)) {
			// 先从缓存里取
			List<BJSCPeriodsInfo> todayPeriodList = (List<BJSCPeriodsInfo>) memCachedService.getTodayPeriodInfoListByKey(date + TODAY_PERIOD_BJSC);
			if (CollectionUtils.isEmpty(todayPeriodList)) {
				log.debug("北京在cached获取当天盘期失败,从数据库获取");
				// 如果没有,从数据库里取,并更新缓存
				todayPeriodList = bjscPeriodsInfoLogic.queryTodayAllPeriods();
				if (!CollectionUtils.isEmpty(todayPeriodList)) {
					// 数据库获取成功
					log.debug("北京从数据库获取当天盘期成功");
					// 更新缓存
					memCachedService.setTodayPeriodInfoList(date + TODAY_PERIOD_BJSC, todayPeriodList);
				}
			}
			for (BJSCPeriodsInfo periodInfo : todayPeriodList) {
				Map<String, String> resultMap = this.setValue(periodInfo.getPeriodsNum(), periodInfo.getOpenQuotTime().getTime(), periodInfo.getStopQuotTime().getTime(), periodInfo.getLotteryTime()
				        .getTime());
				resultList.add(resultMap);

			}
		} else if (Constant.LOTTERY_TYPE_CQSSC.equalsIgnoreCase(playType)) {
			// 先从缓存里取
			List<CQPeriodsInfo> todayPeriodList = (List<CQPeriodsInfo>) memCachedService.getTodayPeriodInfoListByKey(date + TODAY_PERIOD_CQSSC);
			if (CollectionUtils.isEmpty(todayPeriodList)) {
				log.debug("重庆在cached获取当天盘期失败,从数据库获取");
				// 如果没有,从数据库里取,并更新缓存
				todayPeriodList = cqPeriodsInfoLogic.queryTodayAllPeriods();
				if (!CollectionUtils.isEmpty(todayPeriodList)) {
					// 数据库获取成功
					log.debug("重庆从数据库获取当天盘期成功");
					// 更新缓存
					memCachedService.setTodayPeriodInfoList(date + TODAY_PERIOD_CQSSC, todayPeriodList);
				}
			}

			for (CQPeriodsInfo periodInfo : todayPeriodList) {
				Map<String, String> resultMap = this.setValue(periodInfo.getPeriodsNum(), periodInfo.getOpenQuotTime().getTime(), periodInfo.getStopQuotTime().getTime(), periodInfo.getLotteryTime()
				        .getTime());
				resultList.add(resultMap);
			}
		} else if (Constant.LOTTERY_TYPE_K3.equalsIgnoreCase(playType)) {
			// 先从缓存里取
			List<JSSBPeriodsInfo> todayPeriodList = (List<JSSBPeriodsInfo>) memCachedService.getTodayPeriodInfoListByKey(date + TODAY_PERIOD_K3);
			if (CollectionUtils.isEmpty(todayPeriodList)) {
				log.debug("江苏在cached获取当天盘期失败,从数据库获取");
				// 如果没有,从数据库里取,并更新缓存
				todayPeriodList = jssbPeriodsInfoLogic.queryTodayAllPeriods();
				if (!CollectionUtils.isEmpty(todayPeriodList)) {
					// 数据库获取成功
					log.debug("江苏从数据库获取当天盘期成功");
					// 更新缓存
					memCachedService.setTodayPeriodInfoList(date + TODAY_PERIOD_K3, todayPeriodList);
				}
			}
			for (JSSBPeriodsInfo periodInfo : todayPeriodList) {
				Map<String, String> resultMap = this.setValue(periodInfo.getPeriodsNum(), periodInfo.getOpenQuotTime().getTime(), periodInfo.getStopQuotTime().getTime(), periodInfo.getLotteryTime()
				        .getTime());
				resultList.add(resultMap);

			}
		} else if (Constant.LOTTERY_TYPE_NC.equalsIgnoreCase(playType)) {
			// 先从缓存里取
			List<NCPeriodsInfo> todayPeriodList = (List<NCPeriodsInfo>) memCachedService.getTodayPeriodInfoListByKey(date + TODAY_PERIOD_NC);
			if (CollectionUtils.isEmpty(todayPeriodList)) {
				log.debug("农场在cached获取当天盘期失败,从数据库获取");
				// 如果没有,从数据库里取,并更新缓存
				todayPeriodList = ncPeriodsInfoLogic.queryTodayAllPeriods();
				if (!CollectionUtils.isEmpty(todayPeriodList)) {
					// 数据库获取成功
					log.debug("农场从数据库获取当天盘期成功");
					// 更新缓存
					memCachedService.setTodayPeriodInfoList(date + TODAY_PERIOD_NC, todayPeriodList);
				}
			}

			for (NCPeriodsInfo periodInfo : todayPeriodList) {
				Map<String, String> resultMap = this.setValue(periodInfo.getPeriodsNum(), periodInfo.getOpenQuotTime().getTime(), periodInfo.getStopQuotTime().getTime(), periodInfo.getLotteryTime()
				        .getTime());
				resultList.add(resultMap);

			}
		}

		return resultList;
	}
}
