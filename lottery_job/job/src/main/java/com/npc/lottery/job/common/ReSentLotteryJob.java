package com.npc.lottery.job.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.util.CollectionUtils;

import com.npc.lottery.common.Constant;
import com.npc.lottery.dao.LotteryResultDao;
import com.npc.lottery.disruptor.DisruptorService;
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

public class ReSentLotteryJob extends QuartzJobBean {
	private Logger logger = Logger.getLogger(ReSentLotteryJob.class);
	@Autowired
	private LotteryResultDao lotteryResultDao;
	@Autowired
	private DisruptorService disruptorService;

	@Autowired
	private IBJSCPeriodsInfoLogic bjscPeriodsInfoLogic;
	@Autowired
	private ICQPeriodsInfoLogic cqPeriodsInfoLogic;

	@Autowired
	private IGDPeriodsInfoLogic gdPeriodsInfoLogic;
	@Autowired
	private IJSSBPeriodsInfoLogic jssbPeriodsInfoLogic;
	@Autowired
	private INCPeriodsInfoLogic ncPeriodsInfoLogic;

	@Autowired
	private TaskExecutor wcpTaskExecutor;
	@Autowired
	private IMonitoringInfoLogic monitoringInfoLogc;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		try {
			wcpTaskExecutor.execute(new Runnable() {
	
				@Override
				public void run() {
					doBJ();
	
				}
			});
	
			wcpTaskExecutor.execute(new Runnable() {
	
				@Override
				public void run() {
					doCQ();
	
				}
			});
	
			wcpTaskExecutor.execute(new Runnable() {
	
				@Override
				public void run() {
					doGD();
	
				}
			});
	
			wcpTaskExecutor.execute(new Runnable() {
	
				@Override
				public void run() {
					doJS();
	
				}
			});
	
			wcpTaskExecutor.execute(new Runnable() {
	
				@Override
				public void run() {
					doNC();
	
				}
			});
		}catch (Exception e) {
			JobExecutionException e2=new JobExecutionException(e);
			throw e2;
		}
	}

	private void doBJ() {
    	try {
			logger.info("北京  开始  补发开奖>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			// 找出异常需要获取开奖结果的盘期
			List<BJSCPeriodsInfo> bjPeriodsInfoList = bjscPeriodsInfoLogic.findAllExceptionPeriodInfo();
			if (!CollectionUtils.isEmpty(bjPeriodsInfoList)) {
				List<String> periodList = new ArrayList<String>();
				for (int i = 0; i < bjPeriodsInfoList.size(); i++) {
					if (i < 20) {
						periodList.add(bjPeriodsInfoList.get(i).getPeriodsNum());
					}
				}
				logger.info("需要获取北京:" + periodList);
	
				Map<String, List<Integer>> resultMap = lotteryResultDao.getResultListByPeriodNumAndPlayType(Constant.LOTTERY_TYPE_BJSC, periodList);
				if (MapUtils.isNotEmpty(resultMap)) {
					disruptorService.publishEvent(Constant.LOTTERY_TYPE_BJSC, resultMap);
				}
				
		       
			}
			logger.info("北京  结束   补发开奖>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    	} catch (Exception e) {
    		logger.error(e);
		}
	}

	private void doCQ() {
    	try {
			logger.info("重庆  开始  补发开奖>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			// 找出异常需要获取开奖结果的盘期
			List<CQPeriodsInfo> cqPeriodsInfoList = cqPeriodsInfoLogic.findAllExceptionPeriodInfo();
			if (!CollectionUtils.isEmpty(cqPeriodsInfoList)) {
	
				List<String> periodList = new ArrayList<String>();
				for (int i = 0; i < cqPeriodsInfoList.size(); i++) {
					if (i < 20) {
						periodList.add(cqPeriodsInfoList.get(i).getPeriodsNum());
					}
				}
				logger.info("需要获取重庆:" + periodList);
				Map<String, List<Integer>> resultMap = lotteryResultDao.getResultListByPeriodNumAndPlayType(Constant.LOTTERY_TYPE_CQSSC, periodList);
				if (MapUtils.isNotEmpty(resultMap)) {
					disruptorService.publishEvent(Constant.LOTTERY_TYPE_CQSSC, resultMap);
				}
			}
	
			logger.info("重庆  结束   补发开奖>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    	}catch (Exception e) {
    		logger.error(e);
    	}

	}

	private void doGD() {
		try{
			logger.info("广东  开始  补发开奖>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	
			// 找出异常需要获取开奖结果的盘期
			List<GDPeriodsInfo> gdPeriodsInfoList = gdPeriodsInfoLogic.findAllExceptionPeriodInfo();
			if (!CollectionUtils.isEmpty(gdPeriodsInfoList)) {
	
				List<String> periodList = new ArrayList<String>();
				for (int i = 0; i < gdPeriodsInfoList.size(); i++) {
					if (i < 20) {
						periodList.add(gdPeriodsInfoList.get(i).getPeriodsNum());
					}
				}
	
				logger.info("需要获取广东:" + periodList);
				Map<String, List<Integer>> resultMap = lotteryResultDao.getResultListByPeriodNumAndPlayType(Constant.LOTTERY_TYPE_GDKLSF, periodList);
				if (MapUtils.isNotEmpty(resultMap)) {
					disruptorService.publishEvent(Constant.LOTTERY_TYPE_GDKLSF, resultMap);
				}
			}
	
			logger.info("广东 结束   补发开奖>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    	}catch (Exception e) {
    		logger.error(e);
    	}
	}

	private void doJS() {
		try{
			logger.info("江苏  开始  补发开奖>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			// 找出异常需要获取开奖结果的盘期
			List<JSSBPeriodsInfo> jsPeriodsInfoList = jssbPeriodsInfoLogic.findAllExceptionPeriodInfo();
			if (!CollectionUtils.isEmpty(jsPeriodsInfoList)) {
	
				List<String> periodList = new ArrayList<String>();
				for (int i = 0; i < jsPeriodsInfoList.size(); i++) {
					if (i < 20) {
						periodList.add(jsPeriodsInfoList.get(i).getPeriodsNum());
					}
				}
				logger.info("需要获取江苏:" + periodList);
				Map<String, List<Integer>> resultMap = lotteryResultDao.getResultListByPeriodNumAndPlayType(Constant.LOTTERY_TYPE_K3, periodList);
				if (MapUtils.isNotEmpty(resultMap)) {
					disruptorService.publishEvent(Constant.LOTTERY_TYPE_K3, resultMap);
				}
			}
			logger.info("江苏 结束   补发开奖>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    	}catch (Exception e) {
    		logger.error(e);
    	}
	}

	private void doNC() {
		try{
			logger.info("农场  开始  补发开奖>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			List<NCPeriodsInfo> ncPeriodsInfoList = ncPeriodsInfoLogic.findAllExceptionPeriodInfo();
			if (!CollectionUtils.isEmpty(ncPeriodsInfoList)) {
	
				List<String> periodList = new ArrayList<String>();
				for (int i = 0; i < ncPeriodsInfoList.size(); i++) {
					if (i < 20) {
						periodList.add(ncPeriodsInfoList.get(i).getPeriodsNum());
					}
				}
				logger.info("需要获取农场:" + periodList);
				Map<String, List<Integer>> resultMap = lotteryResultDao.getResultListByPeriodNumAndPlayType(Constant.LOTTERY_TYPE_NC, periodList);
				if (MapUtils.isNotEmpty(resultMap)) {
					disruptorService.publishEvent(Constant.LOTTERY_TYPE_NC, resultMap);
				}
			}
			logger.info("农场 结束   补发开奖>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		}catch (Exception e) {
			logger.error(e);
		}
	}
}
