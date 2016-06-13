package com.npc.lottery.job.common.gdklsf;

import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.npc.lottery.common.service.ShopSchemeService;
import com.npc.lottery.manage.logic.interf.ISystemLogic;
import com.npc.lottery.member.logic.interf.IPlayTypeLogic;
import com.npc.lottery.monitoring.entity.MonitoringInfo;
import com.npc.lottery.monitoring.logic.interf.IMonitoringInfoLogic;
import com.npc.lottery.odds.logic.interf.IShopOddsLogic;
import com.npc.lottery.periods.logic.interf.IGDPeriodsInfoLogic;
import com.npc.lottery.util.DatetimeUtil;

public class GDopenPeriodsJob extends QuartzJobBean {

	private Logger logger = Logger.getLogger(GDopenPeriodsJob.class);
	@Autowired
	private IGDPeriodsInfoLogic gdPeriodsInfoLogic = null;
	@Autowired
	private ISystemLogic systemLogic;
	@Autowired
	private IShopOddsLogic shopOddsLogic;
	@Autowired
	private IPlayTypeLogic playTypeLogic;
	@Autowired
	private ShopSchemeService shopSchemeService;
	@Autowired
	private IMonitoringInfoLogic monitoringInfoLogc;
	/**
	 * 定时改变开盘状态
	 */
	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		long star=System.currentTimeMillis();//开始时间（毫秒）
		try{
			logger.info("广东处理开盘业务" + DatetimeUtil.formatyyyyMMddHHmmss(new Date()));
			//gdPeriodsInfoLogic.updateCommon("openQuotTime");
			// 获取商铺对应的scheme列表
			Map<String, String> shopSchemeMap = shopSchemeService.getShopSchemeMap();
			shopOddsLogic.updateRealOddsFromOpenOddsForGD("GD");
			// 每一个商铺按每期进去兑奖
			for (Entry<String, String> shopScheme : shopSchemeMap.entrySet()) {
				String scheme = shopScheme.getValue();	
				playTypeLogic.updatePlayTypeAmountZero("GDKLSF", scheme);
				monitoringInfoLogc.saveMonitoringInfo(new MonitoringInfo(0, "GDKLSF", shopScheme.getKey(), "0000000000", "开盘结果", 1, "商铺："+shopScheme.getKey()+"广东快乐十分开盘", (int)(System.currentTimeMillis()-star)));

			}
			logger.info("广东开盘结束" + DatetimeUtil.formatyyyyMMddHHmmss(new Date()));
		} catch (Exception e) {
			monitoringInfoLogc.saveMonitoringInfo(new MonitoringInfo(0, "GDKLSF", "0000", "0000000000", "开盘结果", 0, "广东快乐十分开盘", (int)(System.currentTimeMillis()-star)));
			JobExecutionException e2=new JobExecutionException(e);
			throw e2;
		}
	}

}
