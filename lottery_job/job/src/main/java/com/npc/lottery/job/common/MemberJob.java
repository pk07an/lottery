package com.npc.lottery.job.common;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.npc.lottery.common.service.ShopSchemeService;
import com.npc.lottery.monitoring.entity.MonitoringInfo;
import com.npc.lottery.monitoring.logic.interf.IMonitoringInfoLogic;
import com.npc.lottery.user.logic.interf.IMemberStaffExtLogic;

public class MemberJob extends QuartzJobBean {
	@Autowired
	private IMemberStaffExtLogic memberStaffExtLogic;
	private Logger logger = Logger.getLogger(MemberJob.class);
	@Autowired
	private ShopSchemeService shopSchemeService;
	@Autowired
	private IMonitoringInfoLogic monitoringInfoLogc;
	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		long star=System.currentTimeMillis();
		try{
			logger.info("开始执行恢复用户信用额度job");
			// 获取商铺对应的scheme列表
			Map<String, String> shopSchemeMap = shopSchemeService.getShopSchemeMap();
			for (Entry<String, String> shopScheme : shopSchemeMap.entrySet()) {

				String scheme = shopScheme.getValue();
				int result = memberStaffExtLogic.updateMemberAvailableCreditToTotal(scheme);
				logger.info("SCHEME:"+scheme+"恢复了"+result+"条用户信用额度");
				monitoringInfoLogc.saveMonitoringInfo(new MonitoringInfo(0, "MEMBER", shopScheme.getKey(), "00000000000", "恢复信用额度", 1, "商铺："+shopScheme.getKey()+"恢复了"+result+"条用户信用额度",
						(int)(System.currentTimeMillis()-star)));
			}
		}catch (Exception e) {
			monitoringInfoLogc.saveMonitoringInfo(new MonitoringInfo(0, "MEMBER", "0000", "00000000000", "恢复信用额度", 0, "恢复用户信用额度",
					(int)(System.currentTimeMillis()-star)));
			JobExecutionException e2=new JobExecutionException(e);
			throw e2;
		}
	}

}
