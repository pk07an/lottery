package com.npc.lottery.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.npc.lottery.common.Constant;
import com.npc.lottery.common.service.ShopSchemeService;
import com.npc.lottery.dao.LotteryResultDao;
import com.npc.lottery.dao.ShopLotteryLogDao;
import com.npc.lottery.manage.entity.PeriodAutoOdds;
import com.npc.lottery.manage.logic.interf.ISystemLogic;
import com.npc.lottery.member.logic.interf.ILotteryResultLogic;
import com.npc.lottery.model.ShopLotteryLog;
import com.npc.lottery.monitoring.entity.MonitoringInfo;
import com.npc.lottery.monitoring.logic.interf.IMonitoringInfoLogic;
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
import com.npc.lottery.replenish.vo.ZhanDangVO;
import com.npc.lottery.statreport.logic.interf.IClassReportEricLogic;
import com.npc.lottery.statreport.logic.interf.IReportStatusLogic;
import com.npc.lottery.statreport.logic.interf.ISettledReportEricLogic;

/**
 * 功能逻辑处理类
 * 
 * @author none
 * 
 */
@Service
public class LotteryResultService {

	private static Logger log = Logger.getLogger(LotteryResultService.class);
	@Autowired
	private ICQPeriodsInfoLogic icqPeriodsInfoLogic;
	@Autowired
	private IGDPeriodsInfoLogic periodsInfoLogic;
	@Autowired
	protected IBJSCPeriodsInfoLogic bjscPeriodsInfoLogic;
	@Autowired
	protected IJSSBPeriodsInfoLogic jssbPeriodsInfoLogic;
	@Autowired
	protected INCPeriodsInfoLogic ncPeriodsInfoLogic;
	@Autowired
	private ILotteryResultLogic lotteryResultLogic;
	@Autowired
	private LotteryResultDao lotteryResultDao;
	@Autowired
	private IReportStatusLogic reportStatusLogic;

	@Autowired
	private ISettledReportEricLogic settledReportEricLogic = null;// 报表统计
	@Autowired
	private IClassReportEricLogic classReportEricLogic = null;// 报表统计
	@Autowired
	private TaskExecutor wcpTaskExecutor;// 异步执行

	@Autowired
	private ISystemLogic systemLogic;

	@Autowired
	private ShopSchemeService shopSchemeService;
	@Autowired
	private IMonitoringInfoLogic monitoringInfoLogic;
	@Autowired
	private ShopLotteryLogDao  shopLotteryLogDao;

	public Map<String, List<Integer>> lotteryForGDUpdateLotJob(Map<String, List<Integer>> resultMap) {
		long star=System.currentTimeMillis();
		Map<String, List<Integer>> lotteryMap = new HashMap<String, List<Integer>>();
		try {
			List<GDPeriodsInfo> gdPeriodsInfoList = periodsInfoLogic.findAllExceptionPeriodInfo();
			if (null == gdPeriodsInfoList || gdPeriodsInfoList.size() == 0) {
				// logger.info("广东异常job: 没有异常盘起 等待下次处理");
				return lotteryMap;
			}
	
			if (resultMap == null || resultMap.size() == 0) {
				log.info("广东异常job: 未能从网页中采集到盘起结果");
				return lotteryMap;
			}
	
			for (GDPeriodsInfo gdPeriodsInfo : gdPeriodsInfoList) {
				String periodNum = gdPeriodsInfo.getPeriodsNum();
				if (resultMap != null && resultMap.get(periodNum) != null) {
					List<Integer> nums = resultMap.get(periodNum);
					List<Integer> numsList = this.updateGDPeriodsInfo(gdPeriodsInfo, nums);
					if (!CollectionUtils.isEmpty(numsList)) {
						lotteryMap.put(periodNum, numsList);
					}
					monitoringInfoLogic.saveMonitoringInfo(new MonitoringInfo(0, "GDKLSF", "0000", periodNum, "开奖结果", 1, "广东快乐十分"+periodNum+"盘期开奖", (int)(System.currentTimeMillis()-star)));
				}
			}
		} catch (Exception e) {
			monitoringInfoLogic.saveMonitoringInfo(new MonitoringInfo(0, "GDKLSF", "0000", "0000000000", "开奖结果", 0, "广东快乐十分盘期开奖出现异常", (int)(System.currentTimeMillis()-star)));
		}
			return lotteryMap;
	}

	public void lotteryUpdateGDBet(final Map<String, List<Integer>> lotteryMap) {
		long startTime=System.currentTimeMillis();
		log.info("==============广东兑奖开始==================");
		try {		
			// 获取商铺对应的scheme列表
			Map<String, String> shopSchemeMap = shopSchemeService.getShopSchemeMap();
			for (Entry<String, List<Integer>> e : lotteryMap.entrySet()) {
				// 待处理盘期对象
				final String period = e.getKey();
				// 结果
				final List<Integer> resultList = e.getValue();
				log.info("==============广东兑奖,兑奖盘期: " + period + " 兑奖结果:" + resultList);
				lotteryResultLogic.updatePlayTypeWin(period, resultList, Constant.LOTTERY_TYPE_GDKLSF);
				//生成商铺兑奖记录
				log.info("==============江苏兑奖,兑奖盘期: " + period + "商铺兑奖记录开始生成");
				shopLotteryLogDao.batchInsertShopLotteryLog(this.initShopLotteryLogList(shopSchemeMap, period,Constant.LOTTERY_TYPE_GDKLSF));
				log.info("开始更新广东盘期状态为 已开奖:" + period);
				periodsInfoLogic.updatePeriodsStatusByPeriodsNum(period, Constant.SCAN_SUC_STATUS);
				
				// 每一个商铺按每期进去兑奖
				for (final Entry<String, String> shopScheme : shopSchemeMap.entrySet()) {
				
					wcpTaskExecutor.execute(new Runnable() {
						long start = System.currentTimeMillis();
						String scheme = shopScheme.getValue();
						String shopCode =  shopScheme.getKey();
						@Override
						public void run() {
							try {
								// 注单兑奖
								lotteryResultLogic.updateLotteryGD(period, resultList, scheme);
								//lotteryResultLogic.saveReportPeriod(period, Constant.LOTTERY_TYPE_GDKLSF, scheme);// 统计报表
								//shopLotteryLogDao.updateStatusByShopCodeAndPeriodNumAndPlayType("1", shopCode, period,"GDKLSF");
								monitoringInfoLogic.saveMonitoringInfo(new MonitoringInfo(0, "GDKLSF", shopCode, period, "兑奖结果", 1, "商铺："+shopScheme.getKey()+"广东快乐十分"+period+"盘期兑奖",(int)(System.currentTimeMillis()-start)));
								// 后续处理(异步)
								doAfterLotSuccessAsyn(period, shopCode, Constant.LOTTERY_TYPE_GDKLSF, scheme);
							} catch (Exception ex) {
								monitoringInfoLogic.saveMonitoringInfo(new MonitoringInfo(0, "GDKLSF", shopCode, period, "兑奖结果", 0, "商铺："+shopScheme.getKey()+"广东快乐十分"+period+"盘期兑奖出现异常", (int)(System.currentTimeMillis()-start)));
								log.error("商铺:" + shopScheme.getKey() + "兑奖异常,请检查", ex);
							}
							log.info("==============广东商铺:"+shopCode+"兑奖结束=====盘期:"+period+"=============耗时:"+(System.currentTimeMillis()-start)+ "ms");
						}
					});
					
				}
			}
			log.info("==============广东兑奖结束==================耗时:"+(System.currentTimeMillis()-startTime)+ "ms");
		} catch (Exception e) {
			monitoringInfoLogic.saveMonitoringInfo(new MonitoringInfo(0, "GDKLSF", "0000", "0000000000", "兑奖结果", 0, "广东快乐十分兑奖出现异常", (int)(System.currentTimeMillis()-startTime)));
		}
	}

	public Map<String, List<Integer>> lotteryForNCUpdateLotJob(Map<String, List<Integer>> resultMap) {
		MonitoringInfo monInfo=null;
		long star=System.currentTimeMillis();
		Map<String, List<Integer>> lotteryMap = new HashMap<String, List<Integer>>();
		try {
			List<NCPeriodsInfo> ncPeriodsInfoList = ncPeriodsInfoLogic.findAllExceptionPeriodInfo();
			if (null == ncPeriodsInfoList || ncPeriodsInfoList.size() == 0) {
				return lotteryMap;
			}
	
			if (resultMap == null || resultMap.size() == 0) {
				log.info("农场异常job: 未能从网页中采集到盘起结果");
				return lotteryMap;
			}
	
			for (NCPeriodsInfo ncPeriodsInfo : ncPeriodsInfoList) {
				String periodNum = ncPeriodsInfo.getPeriodsNum();
				if (resultMap != null && resultMap.get(periodNum) != null) {
					List<Integer> nums = resultMap.get(periodNum);
					List<Integer> numsList = this.updateNCPeriodsInfo(ncPeriodsInfo, nums);
					if (!CollectionUtils.isEmpty(numsList)) {
						lotteryMap.put(periodNum, numsList);
					}
					monInfo=new MonitoringInfo(0, "NC", "0000", periodNum, "开奖结果", 1, "幸运农场"+periodNum+"盘期开奖", (int)(System.currentTimeMillis()-star));
					monitoringInfoLogic.saveMonitoringInfo(monInfo);
				}
				
			}
		}catch (Exception e) {
			monInfo=new MonitoringInfo(0, "NC", "0000", "0000000000", "开奖结果", 0, "幸运农场开奖", (int)(System.currentTimeMillis()-star));
			monitoringInfoLogic.saveMonitoringInfo(monInfo);
		}
		return lotteryMap;
	}

	public void lotteryUpdateNCBet(final Map<String, List<Integer>> lotteryMap) {
		long startTime=System.currentTimeMillis();
		
		try {		
		log.info("==============农场兑奖开始==================");
		// 获取商铺对应的scheme列表
		Map<String, String> shopSchemeMap = shopSchemeService.getShopSchemeMap();
		for (Entry<String, List<Integer>> e : lotteryMap.entrySet()) {
			// 待处理盘期对象
			final String period = e.getKey();
			// 结果
			final List<Integer> resultList = e.getValue();
			log.info("==============农场兑奖,兑奖盘期: " + period + " 兑奖结果:" + resultList);
			lotteryResultLogic.updatePlayTypeWin(period, resultList, Constant.LOTTERY_TYPE_NC);
			//生成商铺兑奖记录
			log.info("==============农场兑奖,兑奖盘期: " + period + "商铺兑奖记录开始生成");
			shopLotteryLogDao.batchInsertShopLotteryLog(this.initShopLotteryLogList(shopSchemeMap, period,Constant.LOTTERY_TYPE_NC));
			log.info("开始更新幸运农场为 已开奖");
			ncPeriodsInfoLogic.updatePeriodsStatusByPeriodsNum(period, Constant.SCAN_SUC_STATUS);
			
			// 每一个商铺按每期进去兑奖
			for (final Entry<String, String> shopScheme : shopSchemeMap.entrySet()) {
				
			
				wcpTaskExecutor.execute(new Runnable() {
					final String scheme = shopScheme.getValue();
					final String shopCode = shopScheme.getKey();
					long start = System.currentTimeMillis();
					@Override
					public void run() {
						try {
							// 注单兑奖
							lotteryResultLogic.updateLotteryNC(period, resultList, scheme);
							monitoringInfoLogic.saveMonitoringInfo(new MonitoringInfo(0, "NC",shopCode, period, "兑奖结果", 1, "商铺："+shopCode+"幸运农场"+period+"盘期兑奖", (int)(System.currentTimeMillis()-start)));
							// 后续处理(异步)
							doAfterLotSuccessAsyn(period, shopCode, Constant.LOTTERY_TYPE_NC, scheme);
						} catch (Exception ex) {
							monitoringInfoLogic.saveMonitoringInfo(new MonitoringInfo(0, "NC", shopCode, period, "兑奖结果", 0, "商铺："+shopCode+"幸运农场"+period+"盘期兑奖", (int)(System.currentTimeMillis()-start)));
							log.error("商铺:" + shopScheme.getKey() + "兑奖异常,请检查", ex);
						}
						log.info("==============农场商铺:"+shopCode+"兑奖结束======盘期:"+period+"=============耗时:"+(System.currentTimeMillis()-start)+ "ms");
					}
				});
				
			}
		}
		log.info("==============农场兑奖结束==================耗时:"+(System.currentTimeMillis()-startTime)+ "ms");
		} catch (Exception e) {
			monitoringInfoLogic.saveMonitoringInfo(new MonitoringInfo(0, "NC", "0000", "0000000000", "兑奖结果", 1, "幸运农场兑奖", (int)(System.currentTimeMillis()-startTime)));
		}

	}

	public Map<String, List<Integer>> lotteryForCQUpdateLotJob(Map<String, List<Integer>> resultMap) {
		long star=System.currentTimeMillis();
		Map<String, List<Integer>> lotteryMap = new HashMap<String, List<Integer>>();
		try {
		List<CQPeriodsInfo> cqPeriodsInfoList = icqPeriodsInfoLogic.findAllExceptionPeriodInfo();
		if (null == cqPeriodsInfoList || cqPeriodsInfoList.size() == 0) {
			log.info("重庆异常job: 没有异常盘起 等待下次");
			return lotteryMap;
		}

		if (resultMap == null || resultMap.size() == 0) {

			log.info("重慶异常job: 未能从网页中采集到盘起结果");
			return lotteryMap;

		}

		for (CQPeriodsInfo cqPeriodsInfo : cqPeriodsInfoList) {
			String periodNum = cqPeriodsInfo.getPeriodsNum();
			if (resultMap != null && resultMap.get(periodNum) != null) {
				List<Integer> nums = resultMap.get(periodNum);
				List<Integer> numsList = this.updateCQPeriodsInfo(cqPeriodsInfo, nums);
				if (!CollectionUtils.isEmpty(numsList)) {
					lotteryMap.put(periodNum, numsList);
				}
				monitoringInfoLogic.saveMonitoringInfo(new MonitoringInfo(0, "CQSSC", "0000", periodNum, "开奖结果", 1, "重庆时时彩"+periodNum+"盘期开奖", (int)(System.currentTimeMillis()-star)));
			}
			
		}
	} catch (Exception e) {
		monitoringInfoLogic.saveMonitoringInfo(new MonitoringInfo(0, "CQSSC", "0000", "00000000000", "开奖结果", 0, "重庆时时彩开奖", (int)(System.currentTimeMillis()-star)));
	}
		return lotteryMap;
	}
	public void lotteryUpdateCQBet(final Map<String, List<Integer>> lotteryMap) {
		long startTime=System.currentTimeMillis();
		
		try {		
		log.info("==============重庆兑奖开始==================");
		// 获取商铺对应的scheme列表
		Map<String, String> shopSchemeMap = shopSchemeService.getShopSchemeMap();

		for (Entry<String, List<Integer>> e : lotteryMap.entrySet()) {

			// 待处理盘期对象
			final String period = e.getKey();
			// 结果
			final List<Integer> resultList = e.getValue();
			// 注单兑奖
			log.info("==============重庆兑奖,兑奖盘期: " + period + " 兑奖结果:" + resultList);
			lotteryResultLogic.updatePlayTypeWin(period, resultList, Constant.LOTTERY_TYPE_CQSSC);
			//生成商铺兑奖记录
			log.info("==============重庆兑奖,兑奖盘期: " + period + "商铺兑奖记录开始生成");
			shopLotteryLogDao.batchInsertShopLotteryLog(this.initShopLotteryLogList(shopSchemeMap, period,Constant.LOTTERY_TYPE_CQSSC));
			log.info("开始更新重庆盘起状态为 已开奖");
			icqPeriodsInfoLogic.updatePeriodsStatusByPeriodsNum(period, Constant.SCAN_SUC_STATUS);
			
			// 每一个商铺按每期进去兑奖
			for (final Entry<String, String> shopScheme : shopSchemeMap.entrySet()) {
			
				
					wcpTaskExecutor.execute(new Runnable() {
						final String scheme = shopScheme.getValue();
						final String shopCode = shopScheme.getKey();
						final long start = System.currentTimeMillis();

						@Override
						public void run() {
							try {
								lotteryResultLogic.updateLotteryCQ(period, resultList, scheme);
								monitoringInfoLogic.saveMonitoringInfo(new MonitoringInfo(0, "CQSSC", shopCode, period, "兑奖结果", 1, "商铺：" + shopCode + "重庆时时彩" + period + "盘期兑奖", (int) (System
								        .currentTimeMillis() - start)));

								// 后续处理(异步)
								doAfterLotSuccessAsyn(period, shopCode, Constant.LOTTERY_TYPE_CQSSC, scheme);
							} catch (Exception ex) {
								monitoringInfoLogic.saveMonitoringInfo(new MonitoringInfo(0, "CQSSC", shopCode, period, "兑奖结果", 0, "商铺：" + shopCode + "重庆时时彩" + period + "盘期兑奖", (int) (System
								        .currentTimeMillis() - start)));
								log.error("商铺:" + shopCode + "兑奖异常,请检查", ex);
							}
							log.info("==============重庆商铺:" + shopCode + "兑奖结束=========盘期:" + period + "==========耗时:" + (System.currentTimeMillis() - start) + "ms");
						}
					});
			}
		}
		log.info("==============重庆兑奖结束==================耗时:"+(System.currentTimeMillis()-startTime)+ "ms");
		}catch (Exception e) {
			monitoringInfoLogic.saveMonitoringInfo(new MonitoringInfo(0, "CQSSC", "0000", "00000000000", "兑奖结果", 0, "重庆时时彩兑奖", (int)(System.currentTimeMillis()-startTime)));
		}
	}

	public Map<String, List<Integer>> lotteryForBJUpdateLotJob(Map<String, List<Integer>> resultMap) {
		
		long star=System.currentTimeMillis();
		Map<String, List<Integer>> lotteryMap = new HashMap<String, List<Integer>>();
		try{
			List<BJSCPeriodsInfo> bjPeriodsInfoList = bjscPeriodsInfoLogic.findAllExceptionPeriodInfo();
			if (null == bjPeriodsInfoList || bjPeriodsInfoList.size() == 0) {
				// logger.info("北京异常job: 没有异常盘起 等待下次处理");
				return lotteryMap;
			}
			for (BJSCPeriodsInfo bjSCPeriodsInfo : bjPeriodsInfoList) {
				String periodNum = bjSCPeriodsInfo.getPeriodsNum();
				if (resultMap != null && resultMap.get(periodNum) != null) {
					List<Integer> nums = resultMap.get(periodNum);
					log.info("北京job：本次獲得盘期   " + periodNum + "  的开奖信息,開獎結果:" + nums);
					List<Integer> numsList = this.updateBJPeriodsInfo(bjSCPeriodsInfo, nums);
					if (!CollectionUtils.isEmpty(numsList)) {
						lotteryMap.put(periodNum, numsList);
					}
					monitoringInfoLogic.saveMonitoringInfo(new MonitoringInfo(0, "BJSC", "0000", periodNum, "开奖结果", 1, "北京赛车"+periodNum+"盘期开奖", (int)(System.currentTimeMillis()-star)));
					
				}
				
			}
		}catch (Exception e) {
			monitoringInfoLogic.saveMonitoringInfo(new MonitoringInfo(0, "BJSC", "0000", "000000", "开奖结果", 0, "北京赛车开奖", (int)(System.currentTimeMillis()-star)));
		}
		return lotteryMap;
	}

	public void updateAutoOddsByLotterMap(Map<String, List<Integer>> lotteryMap,String playType) {
		// 获取商铺对应的scheme列表
		try{
		if (Constant.LOTTERY_TYPE_BJSC.equals(playType)) {
			BJSCPeriodsInfo periodInfo = bjscPeriodsInfoLogic.findCurrentPeriod();
			if (null != periodInfo) {

				final String lastLotteryPeriodNum = String.valueOf(Long.valueOf(periodInfo.getPeriodsNum()) - 1);
				if (lotteryMap.containsKey(lastLotteryPeriodNum)) {
					log.info("北京自动降赔开始执行,基于统计最新盘期号为:" + lastLotteryPeriodNum);
					
					// 快樂十分 兩面盤 有多个店铺
					List<PeriodAutoOdds> pOList = systemLogic.queryAutoOddsByTypeName(Constant.BJSC_DOUBLESIDE, "STATE");
					if (pOList == null || pOList.size() == 0) {
						return;
					}
					// 初始化
					final Map<String, String> initMap = systemLogic.BjAutoInit();
					log.info("<----北京兩面盤自動降賠 --开始处理商铺--");
					for (final PeriodAutoOdds periodAutoOdds : pOList) {
						wcpTaskExecutor.execute(new Runnable() {
							@Override
							public void run() {
								long star=System.currentTimeMillis();
								try {
								systemLogic.updateBJAutoOdds(periodAutoOdds, initMap);
								monitoringInfoLogic.saveMonitoringInfo(new MonitoringInfo(0, "BJSC", periodAutoOdds.getShopCode(), lastLotteryPeriodNum, "自动降赔", 1, "商铺："+periodAutoOdds.getShopCode()+"北京赛车"+lastLotteryPeriodNum+"盘期自动降赔",
										(int)(System.currentTimeMillis()-star)));
								} catch (Exception e) {
									monitoringInfoLogic.saveMonitoringInfo(new MonitoringInfo(0, "BJSC", periodAutoOdds.getShopCode(), lastLotteryPeriodNum, "自动降赔", 0, "商铺："+periodAutoOdds.getShopCode()+"北京赛车"+lastLotteryPeriodNum+"盘期自动降赔",
											(int)(System.currentTimeMillis()-star)));
								}

							}
						});
					}

				}
			}
		} else if (Constant.LOTTERY_TYPE_GDKLSF.equals(playType)) {
			GDPeriodsInfo periodInfo = periodsInfoLogic.findCurrentPeriod();
			if (null != periodInfo) {

				final String lastLotteryPeriodNum = String.valueOf(Long.valueOf(periodInfo.getPeriodsNum()) - 1);
				if (lotteryMap.containsKey(lastLotteryPeriodNum)) {
					log.info("广东自动降赔开始执行,基于统计最新盘期号为:" + lastLotteryPeriodNum);

					// 执行自动降赔
					List<PeriodAutoOdds> pOList1 = systemLogic.queryAutoOddsByTypeName(Constant.GDKLSF_DOUBLESIDE, "STATE");
					// 执行遗漏
					List<PeriodAutoOdds> pOList2 = systemLogic.queryAutoOddsByTypeName(Constant.GDKLSF_YILOU, "STATE");

					// 待处理的自动降赔对象
					List<PeriodAutoOdds> pOList = new ArrayList<PeriodAutoOdds>();
					Map<String, List<PeriodAutoOdds>> handlerMap = new HashMap<String, List<PeriodAutoOdds>>();
					pOList.addAll(pOList1);
					pOList.addAll(pOList2);

					for (PeriodAutoOdds periodAutoOdds : pOList) {
						List<PeriodAutoOdds> handlerList = handlerMap.get(periodAutoOdds.getShopCode());
						if (CollectionUtils.isEmpty(handlerList)) {
							handlerList = new ArrayList<PeriodAutoOdds>();
						}
						handlerList.add(periodAutoOdds);
						handlerMap.put(periodAutoOdds.getShopCode(), handlerList);
					}

					if (MapUtils.isNotEmpty(handlerMap)) {
						for (final Entry<String, List<PeriodAutoOdds>> entry : handlerMap.entrySet()) {
							wcpTaskExecutor.execute(new Runnable() {
								@Override
								public void run() {
									long star=System.currentTimeMillis();
									try {
									List<PeriodAutoOdds> tmpList = entry.getValue();
									for (PeriodAutoOdds tmpPeriodAutoOdds : tmpList) {
										if (Constant.GDKLSF_YILOU.equals(tmpPeriodAutoOdds.getType())) {
											final Map<String, ZhanDangVO> initYLMap = systemLogic.initGDYL();
											systemLogic.updateGDYLOdds(tmpPeriodAutoOdds, initYLMap);
										} else if (Constant.GDKLSF_DOUBLESIDE.equals(tmpPeriodAutoOdds.getType())) {
											final Map<String, String> initMap = systemLogic.initGDAutoOdds();
											systemLogic.updateGDAutoOdds(tmpPeriodAutoOdds, initMap);
										}
									}
									monitoringInfoLogic.saveMonitoringInfo(new MonitoringInfo(0, "GDKLSF", entry.getKey(), lastLotteryPeriodNum, "自动降赔", 1, "商铺："+entry.getKey()+"广东快乐十分"+lastLotteryPeriodNum+"盘期自动降赔",
											(int)(System.currentTimeMillis()-star)));
									} catch (Exception e) {
										monitoringInfoLogic.saveMonitoringInfo(new MonitoringInfo(0, "GDKLSF", entry.getKey(), lastLotteryPeriodNum, "自动降赔", 0, "商铺："+entry.getKey()+"广东快乐十分"+lastLotteryPeriodNum+"盘期自动降赔",
												(int)(System.currentTimeMillis()-star)));
									}
								}
							});
						}
					}
				}
			}
		} else if (Constant.LOTTERY_TYPE_CQSSC.equals(playType)) {
			CQPeriodsInfo periodInfo = icqPeriodsInfoLogic.findCurrentPeriod();
			if (null != periodInfo) {
				final String lastLotteryPeriodNum = String.valueOf(Long.valueOf(periodInfo.getPeriodsNum()) - 1);
				if (lotteryMap.containsKey(lastLotteryPeriodNum)) {
					log.info("重庆自动降赔开始执行,基于统计最新盘期号为:" + lastLotteryPeriodNum);

					List<PeriodAutoOdds> pOList = systemLogic.queryAutoOddsByTypeName(Constant.CQSSC_DOUBLESIDE, "STATE");
					if (pOList == null || pOList.size() == 0) {
						return;
					}
					// 初始化
					final Map<String, String> initMap = systemLogic.initCQAutoOdds();
					log.info("<----重庆兩面盤自動降賠 --开始处理商铺--");
					for (final PeriodAutoOdds periodAutoOdds : pOList) {
						wcpTaskExecutor.execute(new Runnable() {
							@Override
							public void run() {
								long star=System.currentTimeMillis();
								try {
								// 执行制动降赔
								systemLogic.updateCQAutoOdds(periodAutoOdds, initMap);
								monitoringInfoLogic.saveMonitoringInfo(new MonitoringInfo(0, "CQSSC", periodAutoOdds.getShopCode(), lastLotteryPeriodNum, "自动降赔", 1, "商铺："+periodAutoOdds.getShopCode()+"重庆时时彩"+lastLotteryPeriodNum+"盘期自动降赔",
										(int)(System.currentTimeMillis()-star)));
								} catch (Exception e) {
									monitoringInfoLogic.saveMonitoringInfo(new MonitoringInfo(0, "CQSSC", periodAutoOdds.getShopCode(), lastLotteryPeriodNum, "自动降赔", 0, "商铺："+periodAutoOdds.getShopCode()+"重庆时时彩"+lastLotteryPeriodNum+"盘期自动降赔",
											(int)(System.currentTimeMillis()-star)));
								}
							}
						});
					}
				}
			}
		} else if (Constant.LOTTERY_TYPE_NC.equals(playType)) {
			NCPeriodsInfo periodInfo = ncPeriodsInfoLogic.findCurrentPeriod();
			if (null != periodInfo) {

				final String lastLotteryPeriodNum = String.valueOf(Long.valueOf(periodInfo.getPeriodsNum()) - 1);
				if (lotteryMap.containsKey(lastLotteryPeriodNum)) {
					log.info("农场自动降赔开始执行,基于统计最新盘期号为:" + lastLotteryPeriodNum);

					// 执行自动降赔
					List<PeriodAutoOdds> pOList1 = systemLogic.queryAutoOddsByTypeName(Constant.NC_DOUBLESIDE, "STATE");
					// 执行遗漏
					List<PeriodAutoOdds> pOList2 = systemLogic.queryAutoOddsByTypeName(Constant.NC_YILOU, "STATE");

					// 待处理的自动降赔对象
					List<PeriodAutoOdds> pOList = new ArrayList<PeriodAutoOdds>();
					Map<String, List<PeriodAutoOdds>> handlerMap = new HashMap<String, List<PeriodAutoOdds>>();
					pOList.addAll(pOList1);
					pOList.addAll(pOList2);

					for (PeriodAutoOdds periodAutoOdds : pOList) {
						List<PeriodAutoOdds> handlerList = handlerMap.get(periodAutoOdds.getShopCode());
						if (CollectionUtils.isEmpty(handlerList)) {
							handlerList = new ArrayList<PeriodAutoOdds>();
						}
						handlerList.add(periodAutoOdds);
						handlerMap.put(periodAutoOdds.getShopCode(), handlerList);
					}

					if (MapUtils.isNotEmpty(handlerMap)) {
						for (final Entry<String, List<PeriodAutoOdds>> entry : handlerMap.entrySet()) {
							wcpTaskExecutor.execute(new Runnable() {
								@Override
								public void run() {
									long star=System.currentTimeMillis();
									try {
									List<PeriodAutoOdds> tmpList = entry.getValue();
									for (PeriodAutoOdds tmpPeriodAutoOdds : tmpList) {
										if (Constant.NC_YILOU.equals(tmpPeriodAutoOdds.getType())) {
											final Map<String, ZhanDangVO> initYLMap = systemLogic.initNCYL();
											systemLogic.updateNCYLOdds(tmpPeriodAutoOdds, initYLMap);
										} else if (Constant.NC_DOUBLESIDE.equals(tmpPeriodAutoOdds.getType())) {
											final Map<String, String> initMap = systemLogic.initNCAutoOdds();
											systemLogic.updateNCAutoOdds(tmpPeriodAutoOdds, initMap);
										}
									}
									monitoringInfoLogic.saveMonitoringInfo(new MonitoringInfo(0, "NC", entry.getKey(), lastLotteryPeriodNum, "自动降赔", 1, "商铺："+entry.getKey()+"幸运农场"+lastLotteryPeriodNum+"盘期自动降赔",(int)(System.currentTimeMillis()-star)));
									} catch (Exception e) {
										monitoringInfoLogic.saveMonitoringInfo(new MonitoringInfo(0, "NC", entry.getKey(), lastLotteryPeriodNum, "自动降赔", 0, "商铺："+entry.getKey()+"幸运农场"+lastLotteryPeriodNum+"盘期自动降赔",(int)(System.currentTimeMillis()-star)));
										
									}
								}
							});
						}
					}
				}
			}
		}
		}catch (Exception ex){
		}

	}

	public void lotteryUpdateBJBet(final Map<String, List<Integer>> lotteryMap) {
		long startTime=System.currentTimeMillis();
		
		try {		
		log.info("==============北京兑奖开始==================");
		// 获取商铺对应的scheme列表
		Map<String, String> shopSchemeMap = shopSchemeService.getShopSchemeMap();
		for (Entry<String, List<Integer>> e : lotteryMap.entrySet()) {

			// 待处理盘期对象
			final String period = e.getKey();
			// 结果
			final List<Integer> resultList = e.getValue();
			log.info("==============北京兑奖,兑奖盘期: " + period + " 兑奖结果:" + resultList);
			lotteryResultLogic.updatePlayTypeWin(period, resultList, Constant.LOTTERY_TYPE_BJSC);
			
			//生成商铺兑奖记录
			log.info("==============北京兑奖,兑奖盘期: " + period + "商铺兑奖记录开始生成");
			shopLotteryLogDao.batchInsertShopLotteryLog(this.initShopLotteryLogList(shopSchemeMap, period,Constant.LOTTERY_TYPE_BJ));
			log.info("开始更新北京盘起状态为 已开奖");
			bjscPeriodsInfoLogic.updatePeriodsStatusByPeriodsNum(period, Constant.SCAN_SUC_STATUS);
			
			
			log.info("=======开始北京每个商铺兑奖-===============");
			// 每一个商铺按每期进去兑奖
			for (final Entry<String, String> shopScheme : shopSchemeMap.entrySet()) {
				
					wcpTaskExecutor.execute(new Runnable() {

						@Override
						public void run() {
							final String scheme = shopScheme.getValue();
							final String shopCode = shopScheme.getKey();
							long star = System.currentTimeMillis();
							try {
								// 注单兑奖
								lotteryResultLogic.updateLotteryBJSC(period, resultList, scheme);
								//lotteryResultLogic.saveReportPeriod(period, Constant.LOTTERY_TYPE_BJ, scheme);// 统计报表
								//shopLotteryLogDao.updateStatusByShopCodeAndPeriodNumAndPlayType("1", shopCode, period,"BJSC");
								monitoringInfoLogic.saveMonitoringInfo(new MonitoringInfo(0, "BJSC", shopCode, period, "兑奖结果", 1, "商铺：" + shopCode + "北京赛车" + period + "盘期兑奖", (int) (System
								        .currentTimeMillis() - star)));
								//后续处理(异步)
								doAfterLotSuccessAsyn(period, shopCode, Constant.LOTTERY_TYPE_BJ, scheme);
							} catch (Exception ex) {
								monitoringInfoLogic.saveMonitoringInfo(new MonitoringInfo(0, "BJSC", shopCode, period, "兑奖结果", 0, "商铺：" + shopCode + "北京赛车" + period + "盘期兑奖", (int) (System
								        .currentTimeMillis() - star)));
								log.error("商铺:" + shopCode + "兑奖异常,请检查", ex);
							}
							log.info("=======结束北京商铺:"+shopCode+"兑奖-=======盘期:"+period+"=========耗时:" + (System.currentTimeMillis() - star) + "ms");
						}
					});
			
			}
		}
		log.info("==============北京兑奖结束==================耗时:"+ (System.currentTimeMillis() - startTime) + "ms");
		}catch (Exception e) {
			monitoringInfoLogic.saveMonitoringInfo(new MonitoringInfo(0, "BJSC", "0000", "000000", "兑奖结果", 0, "北京赛车兑奖",(int)(System.currentTimeMillis()-startTime)));
		}
	}

	public Map<String, List<Integer>> lotteryForJSUpdateLotJob(Map<String, List<Integer>> resultMap) {
		long star=System.currentTimeMillis();
		Map<String, List<Integer>> lotteryMap = new HashMap<String, List<Integer>>();
		try{
			List<JSSBPeriodsInfo> jsPeriodsInfoList = jssbPeriodsInfoLogic.findAllExceptionPeriodInfo();
			if (null == jsPeriodsInfoList || jsPeriodsInfoList.size() == 0) {
				// logger.info("江蘇异常job: 没有异常盘起 等待下次处理");
				return lotteryMap;
			}
	
			if (resultMap == null || resultMap.size() == 0) {
				log.info("江蘇异常job: 未能从网页中采集到盘起结果");
				return lotteryMap;
			}
	
			for (JSSBPeriodsInfo jsPeriodsInfo : jsPeriodsInfoList) {
				String periodNum = jsPeriodsInfo.getPeriodsNum();
				if (resultMap != null && resultMap.get(periodNum) != null) {
					star=System.currentTimeMillis();
					List<Integer> nums = resultMap.get(periodNum);
					List<Integer> numsList = this.updateJSPeriodsInfo(jsPeriodsInfo, nums);
					if (!CollectionUtils.isEmpty(numsList)) {
						lotteryMap.put(periodNum, numsList);
					}
					int when = (int)(System.currentTimeMillis()-star);
					monitoringInfoLogic.saveMonitoringInfo(new MonitoringInfo(0, "JSSB", "0000", periodNum, "开奖结果", 1, "江苏骰宝"+periodNum+"盘期开奖",when));
				}
				
			}
		}catch (Exception e) {
			monitoringInfoLogic.saveMonitoringInfo(new MonitoringInfo(0, "JSSB", "0000", "00000000000", "开奖结果", 0, "江苏骰宝开奖出现异常",(int)(System.currentTimeMillis()-star)));
		}
		return lotteryMap;
	}

	public void lotteryUpdateJsBet(final Map<String, List<Integer>> lotteryMap) {
		
		long startTime = System.currentTimeMillis();
		try {	
		log.info("==============江苏兑奖开始==================");
		// 获取商铺对应的scheme列表
		Map<String, String> shopSchemeMap = shopSchemeService.getShopSchemeMap();
		for (Entry<String, List<Integer>> e : lotteryMap.entrySet()) {
			// 待处理盘期对象
			final String period = e.getKey();
			// 结果
			final List<Integer> resultList = e.getValue();
			log.info("==============江苏兑奖,兑奖盘期: " + period + " 兑奖结果:" + resultList);
			lotteryResultLogic.updatePlayTypeWin(period, resultList, Constant.LOTTERY_TYPE_K3);
			//生成商铺兑奖记录
			log.info("==============江苏兑奖,兑奖盘期: " + period + "商铺兑奖记录开始生成");
			shopLotteryLogDao.batchInsertShopLotteryLog(this.initShopLotteryLogList(shopSchemeMap, period,Constant.LOTTERY_TYPE_K3));
			log.info("开始更新江苏盘期状态为 已开奖");
			jssbPeriodsInfoLogic.updatePeriodsStatusByPeriodsNum(period, Constant.SCAN_SUC_STATUS);
			
			// 每一个商铺按每期进去兑奖
			for (final Entry<String, String> shopScheme : shopSchemeMap.entrySet()) {
				
				wcpTaskExecutor.execute(new Runnable() {
					
					@Override
					public void run() {
						final String scheme = shopScheme.getValue();
						final String shopCode = shopScheme.getKey();
						final long start = System.currentTimeMillis();
						try {
							// 注单兑奖
							lotteryResultLogic.updateLotteryJSSB(period, resultList, scheme);
							//lotteryResultLogic.saveReportPeriod(period, Constant.LOTTERY_TYPE_K3, scheme);// 统计报表
							//shopLotteryLogDao.updateStatusByShopCodeAndPeriodNumAndPlayType("1", shopCode, period,"JSSB");
							monitoringInfoLogic.saveMonitoringInfo(new MonitoringInfo(0, "JSSB",shopCode, period, "兑奖结果", 1,"商铺："+shopCode+"江苏骰宝"+period+"盘期兑奖" , (int)(System.currentTimeMillis()-start)));
							//后续处理(异步)
							doAfterLotSuccessAsyn(period, shopCode,  Constant.LOTTERY_TYPE_K3, scheme);
						} catch (Exception ex) {
							monitoringInfoLogic.saveMonitoringInfo(new MonitoringInfo(0, "JSSB",shopCode, period, "兑奖结果", 0,"商铺："+shopCode+"江苏骰宝"+period+"盘期兑奖" , (int)(System.currentTimeMillis()-start)));
							log.error("商铺:" + shopCode + "兑奖异常,请检查", ex);
						}
						log.info("==============江苏商铺:"+shopCode+"兑奖结束=======盘期:"+period+"============耗时:"+(System.currentTimeMillis()-start)+ "ms");
					}
				});
			
			}
		}
		log.info("==============江苏兑奖结束==================耗时:"+(System.currentTimeMillis()-startTime)+ "ms");
		}catch (Exception e) {
			monitoringInfoLogic.saveMonitoringInfo(new MonitoringInfo(0, "JSSB", "0000", "00000000000", "兑奖结果", 0,"江苏骰宝兑奖", (int)(System.currentTimeMillis()-startTime)));
		}
	}

	private List<Integer> updateGDPeriodsInfo(GDPeriodsInfo gdPeriodsInfo, List<Integer> nums) {
		List<Integer> numsList = new ArrayList<Integer>();
		if (nums.size() == 8) {
			String periodNum = gdPeriodsInfo.getPeriodsNum();
			log.info("广东 异常job 更新盘期号码>>>>>>>>>>>>>>>>>>>>>>" + periodNum);
			gdPeriodsInfo.setResult1(nums.get(0));
			gdPeriodsInfo.setResult2(nums.get(1));
			gdPeriodsInfo.setResult3(nums.get(2));
			gdPeriodsInfo.setResult4(nums.get(3));
			gdPeriodsInfo.setResult5(nums.get(4));
			gdPeriodsInfo.setResult6(nums.get(5));
			gdPeriodsInfo.setResult7(nums.get(6));
			gdPeriodsInfo.setResult8(nums.get(7));
			gdPeriodsInfo.setState(Constant.LOTTERY_STATUS);
			periodsInfoLogic.update(gdPeriodsInfo);
			numsList.add(gdPeriodsInfo.getResult1());
			numsList.add(gdPeriodsInfo.getResult2());
			numsList.add(gdPeriodsInfo.getResult3());
			numsList.add(gdPeriodsInfo.getResult4());
			numsList.add(gdPeriodsInfo.getResult5());
			numsList.add(gdPeriodsInfo.getResult6());
			numsList.add(gdPeriodsInfo.getResult7());
			numsList.add(gdPeriodsInfo.getResult8());
		}

		return numsList;
	}

	private List<Integer> updateNCPeriodsInfo(NCPeriodsInfo ncPeriodsInfo, List<Integer> nums) {
		List<Integer> numsList = new ArrayList<Integer>();
		if (nums.size() == 8) {
			String periodNum = ncPeriodsInfo.getPeriodsNum();
			log.info("农场 异常job 更新 盘期号码>>>>>>>>>>>>>>>>>>>>>>" + periodNum);
			ncPeriodsInfo.setResult1(nums.get(0));
			ncPeriodsInfo.setResult2(nums.get(1));
			ncPeriodsInfo.setResult3(nums.get(2));
			ncPeriodsInfo.setResult4(nums.get(3));
			ncPeriodsInfo.setResult5(nums.get(4));
			ncPeriodsInfo.setResult6(nums.get(5));
			ncPeriodsInfo.setResult7(nums.get(6));
			ncPeriodsInfo.setResult8(nums.get(7));
			ncPeriodsInfo.setState(Constant.LOTTERY_STATUS);
			ncPeriodsInfoLogic.update(ncPeriodsInfo);
			numsList.add(ncPeriodsInfo.getResult1());
			numsList.add(ncPeriodsInfo.getResult2());
			numsList.add(ncPeriodsInfo.getResult3());
			numsList.add(ncPeriodsInfo.getResult4());
			numsList.add(ncPeriodsInfo.getResult5());
			numsList.add(ncPeriodsInfo.getResult6());
			numsList.add(ncPeriodsInfo.getResult7());
			numsList.add(ncPeriodsInfo.getResult8());
		}

		return numsList;
	}

	private List<Integer> updateCQPeriodsInfo(CQPeriodsInfo cqPeriodsInfo, List<Integer> nums) {

		List<Integer> numsList = new ArrayList<Integer>();
		if (nums.size() == 5) {
			String periodNum = cqPeriodsInfo.getPeriodsNum();
			log.info("重庆 异常job： 更新 盘期号码>>>>>>>>>>>>>>>>>>>>>>" + periodNum);
			cqPeriodsInfo.setResult1(nums.get(0));
			cqPeriodsInfo.setResult2(nums.get(1));
			cqPeriodsInfo.setResult3(nums.get(2));
			cqPeriodsInfo.setResult4(nums.get(3));
			cqPeriodsInfo.setResult5(nums.get(4));
			cqPeriodsInfo.setState(Constant.LOTTERY_STATUS);
			icqPeriodsInfoLogic.update(cqPeriodsInfo);

			numsList.add(cqPeriodsInfo.getResult1());
			numsList.add(cqPeriodsInfo.getResult2());
			numsList.add(cqPeriodsInfo.getResult3());
			numsList.add(cqPeriodsInfo.getResult4());
			numsList.add(cqPeriodsInfo.getResult5());
		}

		return numsList;
	}

	private List<Integer> updateBJPeriodsInfo(BJSCPeriodsInfo bjSCPeriodsInfo, List<Integer> nums) {
		List<Integer> numsList = new ArrayList<Integer>();
		if (nums.size() == 10) {
			String periodNum = bjSCPeriodsInfo.getPeriodsNum();
			log.info("北京 异常job 更新 盘期号码>>>>>>>>>>>>>>>>>>>>>>" + periodNum);
			bjSCPeriodsInfo.setResult1(nums.get(0));
			bjSCPeriodsInfo.setResult2(nums.get(1));
			bjSCPeriodsInfo.setResult3(nums.get(2));
			bjSCPeriodsInfo.setResult4(nums.get(3));
			bjSCPeriodsInfo.setResult5(nums.get(4));
			bjSCPeriodsInfo.setResult6(nums.get(5));
			bjSCPeriodsInfo.setResult7(nums.get(6));
			bjSCPeriodsInfo.setResult8(nums.get(7));
			bjSCPeriodsInfo.setResult9(nums.get(8));
			bjSCPeriodsInfo.setResult10(nums.get(9));
			bjSCPeriodsInfo.setState(Constant.LOTTERY_STATUS);
			bjscPeriodsInfoLogic.update(bjSCPeriodsInfo);
			numsList.add(bjSCPeriodsInfo.getResult1());
			numsList.add(bjSCPeriodsInfo.getResult2());
			numsList.add(bjSCPeriodsInfo.getResult3());
			numsList.add(bjSCPeriodsInfo.getResult4());
			numsList.add(bjSCPeriodsInfo.getResult5());
			numsList.add(bjSCPeriodsInfo.getResult6());
			numsList.add(bjSCPeriodsInfo.getResult7());
			numsList.add(bjSCPeriodsInfo.getResult8());
			numsList.add(bjSCPeriodsInfo.getResult9());
			numsList.add(bjSCPeriodsInfo.getResult10());
		}
		return numsList;
	}

	private List<Integer> updateJSPeriodsInfo(JSSBPeriodsInfo jssbPeriodsInfo, List<Integer> nums) {

		List<Integer> numsList = new ArrayList<Integer>();
		if (nums.size() == 3) {
			String periodNum = jssbPeriodsInfo.getPeriodsNum();
			log.info("江蘇 异常job 更新 盘期号码>>>>>>>>>>>>>>>>>>>>>>" + periodNum);
			jssbPeriodsInfo.setResult1(nums.get(0));
			jssbPeriodsInfo.setResult2(nums.get(1));
			jssbPeriodsInfo.setResult3(nums.get(2));
			jssbPeriodsInfo.setState(Constant.LOTTERY_STATUS);
			jssbPeriodsInfoLogic.update(jssbPeriodsInfo);
			numsList.add(jssbPeriodsInfo.getResult1());
			numsList.add(jssbPeriodsInfo.getResult2());
			numsList.add(jssbPeriodsInfo.getResult3());
		}

		return numsList;
	}

	/**
	 * 删除临时表的记录
	 * @author  wb
	 */
	public void deleteTypeWinInfo() {
		lotteryResultDao.deleteTypeWinInfo();
	}
	
	private List<ShopLotteryLog> initShopLotteryLogList(Map<String, String> shopSchemeMap, String period,String playType) {
		List<ShopLotteryLog> shopLotteryLogList = new ArrayList<ShopLotteryLog>();
		for (Entry<String, String> shopScheme : shopSchemeMap.entrySet()) {
			ShopLotteryLog shopLotteryLog = new ShopLotteryLog();
			shopLotteryLog.setShopCode(shopScheme.getKey());
			shopLotteryLog.setPeriodNum(period);
			shopLotteryLog.setPlayType(playType);
			shopLotteryLogList.add(shopLotteryLog);
		}
		return shopLotteryLogList;
	}
	
	private void doAfterLotSuccessAsyn(final String period, final String shopCode, final String playType, final String scheme) {
		// 异步执行迁移投注表数据/报表计算/
		wcpTaskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				String playMoniType = playType;
				if (Constant.LOTTERY_TYPE_BJ.equals(playType)) {
					playMoniType = "BJSC";
				} else if (Constant.LOTTERY_TYPE_K3.equals(playType)) {
					playMoniType = "JSSB";
				}

				long start = System.currentTimeMillis();
				try {
					// 1.迁移投注/补货表数据到历史表
					lotteryResultLogic.miragationLotDataToHistory(period, playType, scheme);
					// 2.更新商铺兑奖记录为已兑奖
					shopLotteryLogDao.updateStatusByShopCodeAndPeriodNumAndPlayType("1", shopCode, period, playType);
					monitoringInfoLogic.saveMonitoringInfo(new MonitoringInfo(0, playMoniType, shopCode, period, "兑奖移库", 1, "商铺：" + shopCode + playType + period + "盘期兑奖移库", (int) (System
					        .currentTimeMillis() - start)));

					// 3.计算统计报表
					long startReport = System.currentTimeMillis();
					boolean flag = lotteryResultLogic.saveReportPeriod(period, playType, scheme);// 统计报表
					if (flag) {
						monitoringInfoLogic.saveMonitoringInfo(new MonitoringInfo(0, playMoniType, shopCode, period, "报表计算", 1, "商铺：" + shopCode + playType + period + "报表计算", (int) (System
						        .currentTimeMillis() - startReport)));
					} else {
						monitoringInfoLogic.saveMonitoringInfo(new MonitoringInfo(0, playMoniType, shopCode, period, "报表计算", 0, "商铺：" + shopCode + playType + period + "报表计算", (int) (System
						        .currentTimeMillis() - startReport)));
						throw new Exception("报表计算异常");
					}
				} catch (Exception ex) {
					monitoringInfoLogic.saveMonitoringInfo(new MonitoringInfo(0, playMoniType, shopCode, period, "后续处理", 0, "商铺：" + shopCode + playType + period + "盘期兑奖", (int) (System
					        .currentTimeMillis() - start)));
					log.error(playMoniType + "商铺:" + shopCode + "兑奖后续处理异常,请检查", ex);
				}
				log.info(playMoniType + "商铺:" + shopCode + "兑奖后续处理完成,耗时" + (System.currentTimeMillis() - start) + "MS");
			}
		});
	}
}
