package com.npc.lottery.job.common;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.npc.lottery.monitoring.entity.MonitoringInfo;
import com.npc.lottery.monitoring.logic.interf.IMonitoringInfoLogic;
import com.npc.lottery.service.LotteryResultService;

public class DeleteTempTableJob extends QuartzJobBean{

	@Autowired
	private  LotteryResultService lotteryResultServicce;
	@Autowired
	private IMonitoringInfoLogic monitoringInfoLogc;
	private Logger logger = Logger.getLogger(DeleteTempTableJob.class);
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		long star=System.currentTimeMillis();

		try {
			logger.info("删除临时表数据开始>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			lotteryResultServicce.deleteTypeWinInfo();
			logger.info("删除临时表数据结束>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			monitoringInfoLogc.saveMonitoringInfo(new MonitoringInfo(0, "ALL", "0000", "00000000000", "删除临时数据", 1, "每天凌晨4点删除前一天的临时数据", (int)(System.currentTimeMillis()-star)));
		}catch (Exception e) {
			monitoringInfoLogc.saveMonitoringInfo(new MonitoringInfo(0, "ALL", "0000", "00000000000", "删除临时数据", 0, "每天凌晨4点删除前一天的临时数据", (int)(System.currentTimeMillis()-star)));
			JobExecutionException e2=new JobExecutionException(e);
			throw e2;
		}
	}

}
