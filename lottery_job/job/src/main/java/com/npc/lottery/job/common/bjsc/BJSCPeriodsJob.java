package com.npc.lottery.job.common.bjsc;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.npc.lottery.monitoring.entity.MonitoringInfo;
import com.npc.lottery.monitoring.logic.interf.IMonitoringInfoLogic;
import com.npc.lottery.periods.logic.interf.IBJSCPeriodsInfoLogic;


public class BJSCPeriodsJob extends QuartzJobBean{

    Logger logger = Logger.getLogger(BJSCPeriodsJob.class);
    @Autowired 
    private IBJSCPeriodsInfoLogic bjscPeriodsInfoLogic;
    @Autowired
    private IMonitoringInfoLogic monitoringInfoLogc;
    /**
     * 定时设置一天的期数
     */
    @Override
    protected void executeInternal(JobExecutionContext arg0)
            throws JobExecutionException {
    	long star=System.currentTimeMillis();
        try {
        	
            logger.info("生成北京赛车 盘期开始");
            bjscPeriodsInfoLogic.saveBJSCPeriods();
            monitoringInfoLogc.saveMonitoringInfo(new MonitoringInfo(0, "BJSC", "0000", "000000", "生成盘期", 1, "生成北京赛车的盘期", (int)(System.currentTimeMillis()-star)));
            
           
        } catch (Exception e) {
            logger.info("生成北京赛车   盘期");
            logger.error("执行" + this.getClass().getSimpleName()
                    + "中的方法executeInternal时出现错误 "
                    + e.getMessage());
            
            monitoringInfoLogc.saveMonitoringInfo(new MonitoringInfo(0, "BJSC", "0000", "000000", "生成盘期", 0, "生成北京赛车的盘期", (int)(System.currentTimeMillis()-star)));
            
    		JobExecutionException e2=new JobExecutionException(e);
    		throw e2;
        }
    }
}
