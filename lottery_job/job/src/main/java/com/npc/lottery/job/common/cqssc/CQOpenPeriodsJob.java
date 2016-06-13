package com.npc.lottery.job.common.cqssc;

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
import com.npc.lottery.periods.logic.interf.ICQPeriodsInfoLogic;
import com.npc.lottery.util.DatetimeUtil;

public class CQOpenPeriodsJob extends QuartzJobBean {

	private Logger logger = Logger.getLogger(CQOpenPeriodsJob.class);
	@Autowired
	private ICQPeriodsInfoLogic icqPeriodsInfoLogic = null;
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
        long star=System.currentTimeMillis();
		try{
			logger.info("重庆处理开盘业务" + DatetimeUtil.formatyyyyMMddHHmmss(new Date()));
			//icqPeriodsInfoLogic.updateCommon("openQuotTime");
			shopOddsLogic.updateRealOddsFromOpenOddsForGD("CQ");
			// 获取商铺对应的scheme列表
			Map<String, String> shopSchemeMap = shopSchemeService.getShopSchemeMap();
	
			// 每一个商铺按每期进去兑奖
			for (Entry<String, String> shopScheme : shopSchemeMap.entrySet()) {
				String scheme = shopScheme.getValue();
				playTypeLogic.updatePlayTypeAmountZero("CQSSC", scheme);					
				monitoringInfoLogc.saveMonitoringInfo(new MonitoringInfo(0, "CQSSC", shopScheme.getKey(), "00000000000", "开盘结果", 1, "商铺："+shopScheme.getKey()+"重庆时时彩开盘", (int)(System.currentTimeMillis()-star)));
			}
			logger.info("重庆开盘结束" + DatetimeUtil.formatyyyyMMddHHmmss(new Date()));
		} catch (Exception e) {
			monitoringInfoLogc.saveMonitoringInfo(new MonitoringInfo(0, "CQSSC", "0000", "00000000000", "开盘结果", 0, "重庆时时彩开盘", (int)(System.currentTimeMillis()-star)));
			
			JobExecutionException e2=new JobExecutionException(e);
			throw e2;
		}
	}
}
