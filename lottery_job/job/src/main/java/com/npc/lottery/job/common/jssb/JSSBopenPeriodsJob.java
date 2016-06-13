package com.npc.lottery.job.common.jssb;

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
import com.npc.lottery.periods.logic.interf.IJSSBPeriodsInfoLogic;
import com.npc.lottery.util.DatetimeUtil;

public class JSSBopenPeriodsJob extends QuartzJobBean {

	private Logger logger = Logger.getLogger(JSSBopenPeriodsJob.class);
	@Autowired
	private IJSSBPeriodsInfoLogic jssbPeriodsInfoLogic;
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
			
			logger.info("江苏处理开盘业务" + DatetimeUtil.formatyyyyMMddHHmmss(new Date()));
	
			shopOddsLogic.updateRealOddsFromOpenOddsForGD("K3");
			// 获取商铺对应的scheme列表
			Map<String, String> shopSchemeMap = shopSchemeService.getShopSchemeMap();
	
			// 每一个商铺按每期进去兑奖
			for (Entry<String, String> shopScheme : shopSchemeMap.entrySet()) {
				String scheme = shopScheme.getValue();
				playTypeLogic.updatePlayTypeAmountZero("K3", scheme);
				monitoringInfoLogc.saveMonitoringInfo(new MonitoringInfo(0, "JSSB", shopScheme.getKey(), "00000000000", "开盘结果", 1, "商铺："+shopScheme.getKey()+"江苏骰宝开盘", (int)(System.currentTimeMillis()-star)));
			}
			logger.info("江苏开盘结束" + DatetimeUtil.formatyyyyMMddHHmmss(new Date()));
		}catch (Exception e) {
			monitoringInfoLogc.saveMonitoringInfo(new MonitoringInfo(0, "JSSB", "0000", "00000000000", "开盘结果", 0, "江苏骰宝开盘", (int)(System.currentTimeMillis()-star)));
			
			JobExecutionException e2=new JobExecutionException(e);
			throw e2;
		}
	}
}
