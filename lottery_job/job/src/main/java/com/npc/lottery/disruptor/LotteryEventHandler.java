package com.npc.lottery.disruptor;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lmax.disruptor.EventHandler;
import com.npc.lottery.common.Constant;
import com.npc.lottery.service.LotteryResultService;

@Component
public class LotteryEventHandler implements EventHandler<LotteryEvent> {

	private static final Logger log = Logger.getLogger(LotteryEventHandler.class);
	@Autowired
	private LotteryResultService lotteryResultService;

	@Override
	public void onEvent(LotteryEvent lotteryEvent, long sequence, boolean endOfBatch) throws Exception {
		String playType = lotteryEvent.getPlayType();
		Map<String, List<Integer>> resultMap = lotteryEvent.getResultMap();

		long startTime = System.currentTimeMillis();
		log.info("===================开始处理Disruptor事件,playType:" + playType);

		if (StringUtils.isEmpty(playType)) {
			log.error("Disruptor 事件异常,playtype传入为空");
		} else {
			if (MapUtils.isNotEmpty(resultMap)) {
				if (Constant.LOTTERY_TYPE_BJSC.equalsIgnoreCase(playType)) {
					doBJ(resultMap);
				} else if (Constant.LOTTERY_TYPE_CQSSC.equalsIgnoreCase(playType)) {
					doCQ(resultMap);
				} else if (Constant.LOTTERY_TYPE_GDKLSF.equalsIgnoreCase(playType)) {
					doGD(resultMap);
				} else if (Constant.LOTTERY_TYPE_K3.equalsIgnoreCase(playType)) {
					doJS(resultMap);
				} else if (Constant.LOTTERY_TYPE_NC.equalsIgnoreCase(playType)) {
					doNC(resultMap);
				} else {
					log.error("Disruptor 事件异常,playtype传入错误" + playType);
				}
			}

		}
		log.info("===================结束处理Disruptor事件,playType:" + playType + "耗时:" + (System.currentTimeMillis() - startTime) + "ms");

	}

	private void doBJ(Map<String, List<Integer>> resultMap) {

		log.info("开始处理北京开奖线程:" + resultMap);
		if (MapUtils.isNotEmpty(resultMap)) {
			log.info("===============北京开始开奖================");
			// 更新开奖信息
			Map<String, List<Integer>> lotteryMap = lotteryResultService.lotteryForBJUpdateLotJob(resultMap);
			log.info("===============北京结束开奖================");
			if (!MapUtils.isEmpty(lotteryMap)) {
				log.info("===============北京开始自动降赔================");
				long startTime = System.currentTimeMillis();
				// 自动降赔
				lotteryResultService.updateAutoOddsByLotterMap(lotteryMap, Constant.LOTTERY_TYPE_BJSC);
				log.info("===============北京结束自动降赔================" + (System.currentTimeMillis() - startTime) / 1000 + "S");
				log.info("===============北京开始兑奖================");
				long startTime2 = System.currentTimeMillis();
				// 兑奖
				lotteryResultService.lotteryUpdateBJBet(lotteryMap);
				log.info("===============北京结束兑奖================" + (System.currentTimeMillis() - startTime2) / 1000 + "S");
			}
		}
	}

	private void doCQ(Map<String, List<Integer>> resultMap) {
		log.info("开始处理重庆开奖线程:" + resultMap);
		if (MapUtils.isNotEmpty(resultMap)) {
			log.info("===============重庆开始开奖================");
			// 更新开奖信息
			Map<String, List<Integer>> lotteryMap = lotteryResultService.lotteryForCQUpdateLotJob(resultMap);
			log.info("===============重庆结束开奖================");
			if (!MapUtils.isEmpty(lotteryMap)) {
				log.info("===============重庆开始自动降赔================");
				long startTime = System.currentTimeMillis();
				// 自动降赔
				lotteryResultService.updateAutoOddsByLotterMap(lotteryMap, Constant.LOTTERY_TYPE_CQSSC);
				log.info("===============重庆结束自动降赔================" + (System.currentTimeMillis() - startTime) / 1000 + "S");
				log.info("===============重庆开始兑奖================");
				long startTime2 = System.currentTimeMillis();
				// 兑奖
				lotteryResultService.lotteryUpdateCQBet(lotteryMap);
				log.info("===============重庆结束兑奖================" + (System.currentTimeMillis() - startTime2) / 1000 + "S");
			}

		}

	}

	private void doGD(Map<String, List<Integer>> resultMap) {
		log.info("开始处理广东开奖线程:" + resultMap);
		if (MapUtils.isNotEmpty(resultMap)) {
			log.info("===============广东开始开奖================");
			// 更新开奖信息
			Map<String, List<Integer>> lotteryMap = lotteryResultService.lotteryForGDUpdateLotJob(resultMap);
			log.info("===============广东结束开奖================");
			if (!MapUtils.isEmpty(lotteryMap)) {
				log.info("===============广东开始自动降赔================");
				long startTime = System.currentTimeMillis();
				// 自动降赔
				lotteryResultService.updateAutoOddsByLotterMap(lotteryMap, Constant.LOTTERY_TYPE_GDKLSF);
				log.info("===============广东结束自动降赔================" + (System.currentTimeMillis() - startTime) / 1000 + "S");
				log.info("===============广东开始兑奖================");
				long startTime2 = System.currentTimeMillis();
				// 兑奖
				lotteryResultService.lotteryUpdateGDBet(lotteryMap);
				log.info("===============广东结束兑奖================" + (System.currentTimeMillis() - startTime2) / 1000 + "S");
			}
		}
	}

	private void doJS(Map<String, List<Integer>> resultMap) {
		log.info("开始处理江苏开奖线程:" + resultMap);
		if (MapUtils.isNotEmpty(resultMap)) {
			log.info("===============江苏开始开奖================");
			// 更新开奖信息
			Map<String, List<Integer>> lotteryMap = lotteryResultService.lotteryForJSUpdateLotJob(resultMap);
			log.info("===============江苏结束开奖================");
			if (!MapUtils.isEmpty(lotteryMap)) {
				log.info("===============江苏开始兑奖================");
				long startTime2 = System.currentTimeMillis();
				// 兑奖
				lotteryResultService.lotteryUpdateJsBet(lotteryMap);
				log.info("===============江苏结束兑奖================" + (System.currentTimeMillis() - startTime2) / 1000 + "S");
			}
		}

	}

	private void doNC(Map<String, List<Integer>> resultMap) {
		log.info("开始处理农场开奖线程:" + resultMap);
		if (MapUtils.isNotEmpty(resultMap)) {
			log.info("===============农场开始开奖================");
			// 更新开奖信息
			Map<String, List<Integer>> lotteryMap = lotteryResultService.lotteryForNCUpdateLotJob(resultMap);
			log.info("===============农场结束兑奖================");
			if (!MapUtils.isEmpty(lotteryMap)) {
				long startTime = System.currentTimeMillis();
				log.info("===============农场开始自动降赔================");
				// 自动降赔
				lotteryResultService.updateAutoOddsByLotterMap(lotteryMap, Constant.LOTTERY_TYPE_NC);
				log.info("===============农场结束自动降赔================" + (System.currentTimeMillis() - startTime) / 1000 + "S");
				log.info("===============农场开始兑奖================");
				long startTime2 = System.currentTimeMillis();
				// 兑奖
				lotteryResultService.lotteryUpdateNCBet(lotteryMap);
				log.info("===============农场结束结束兑奖================" + (System.currentTimeMillis() - startTime2) / 1000 + "S");
			}
		}
	}
}