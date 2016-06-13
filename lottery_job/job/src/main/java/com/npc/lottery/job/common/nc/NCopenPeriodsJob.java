package com.npc.lottery.job.common.nc;

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
import com.npc.lottery.periods.logic.interf.INCPeriodsInfoLogic;

public class NCopenPeriodsJob extends QuartzJobBean {

	private Logger logger = Logger.getLogger(NCopenPeriodsJob.class);
	@Autowired
	private INCPeriodsInfoLogic ncPeriodsInfoLogic;
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
			logger.info("幸运农场开盘开始");
			//ncPeriodsInfoLogic.updateCommon("openQuotTime");
			shopOddsLogic.updateRealOddsFromOpenOddsForGD("NC");
	
			// 获取商铺对应的scheme列表
			Map<String, String> shopSchemeMap = shopSchemeService.getShopSchemeMap();
	
			// 每一个商铺去处理
			for (Entry<String, String> shopScheme : shopSchemeMap.entrySet()) {
				String scheme = shopScheme.getValue();
				playTypeLogic.updatePlayTypeAmountZero("NC", scheme);
				monitoringInfoLogc.saveMonitoringInfo(new MonitoringInfo(0, "NC", shopScheme.getKey(), "0000000000", "开盘结果", 1, "商铺："+shopScheme.getKey()+"幸运农场开盘", (int)(System.currentTimeMillis()-star)));
			}
			logger.info("幸运农场开盘结束");
		}catch (Exception e) {
			monitoringInfoLogc.saveMonitoringInfo(new MonitoringInfo(0, "NC", "0000", "0000000000", "开盘结果", 0, "幸运农场开盘", (int)(System.currentTimeMillis()-star)));
			
			JobExecutionException e2=new JobExecutionException(e);
			throw e2;
		}
	}
}
