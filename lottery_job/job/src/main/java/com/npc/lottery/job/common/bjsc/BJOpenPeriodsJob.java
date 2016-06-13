package com.npc.lottery.job.common.bjsc;

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
import com.npc.lottery.periods.logic.interf.IBJSCPeriodsInfoLogic;
import com.npc.lottery.util.DatetimeUtil;

public class BJOpenPeriodsJob extends QuartzJobBean {

	private Logger logger = Logger.getLogger(BJOpenPeriodsJob.class);
	@Autowired
	protected IBJSCPeriodsInfoLogic bjscPeriodsInfoLogic;
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
	
	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		//监控实体
        long star=System.currentTimeMillis();
		try {
			logger.info("北京处理开盘逻辑" + DatetimeUtil.formatyyyyMMddHHmmss(new Date()));
			// 获取商铺对应的scheme列表
			Map<String, String> shopSchemeMap = shopSchemeService.getShopSchemeMap();
			shopOddsLogic.updateRealOddsFromOpenOddsForGD("BJ");
			// 每一个商铺按每期进去兑奖
			for (Entry<String, String> shopScheme : shopSchemeMap.entrySet()) {
				String scheme = shopScheme.getValue();
				playTypeLogic.updatePlayTypeAmountZero("BJ", scheme);
				monitoringInfoLogc.saveMonitoringInfo(new MonitoringInfo(0, "BJSC", shopScheme.getKey(), "000000", "开盘结果", 1,"商铺："+shopScheme.getKey()+"北京赛车开盘" , (int)(System.currentTimeMillis()-star)));
				
			}

		} catch (Exception e) {
			monitoringInfoLogc.saveMonitoringInfo(new MonitoringInfo(0, "BJSC", "0000", "000000", "开盘结果", 0,"北京赛车开盘" , (int)(System.currentTimeMillis()-star)));
			
			JobExecutionException e2=new JobExecutionException(e);
			throw e2;
		}
	}

}
