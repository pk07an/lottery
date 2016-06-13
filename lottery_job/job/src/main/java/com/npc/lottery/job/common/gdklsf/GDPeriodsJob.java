package com.npc.lottery.job.common.gdklsf;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.npc.lottery.monitoring.entity.MonitoringInfo;
import com.npc.lottery.monitoring.logic.interf.IMonitoringInfoLogic;
import com.npc.lottery.periods.logic.interf.IGDPeriodsInfoLogic;

public class GDPeriodsJob extends QuartzJobBean{

    Logger logger = Logger.getLogger(GDPeriodsJob.class);
    @Autowired
    private IGDPeriodsInfoLogic gdPeriodsInfoLogic = null;
    @Autowired
    private IMonitoringInfoLogic monitoringInfoLogc;
    /**
     * 定时设置一天的期数
     */
    @Override
    protected void executeInternal(JobExecutionContext arg0)
            throws JobExecutionException {
    	long star=System.currentTimeMillis();
        SimpleDateFormat ageFormat = new SimpleDateFormat("yyyyMMdd");
        String beginNumber = ageFormat.format(new Date())+"01";
        String endNumber = ageFormat.format(new Date())+"84";
        try {
            gdPeriodsInfoLogic.deletePeriods(beginNumber,endNumber);
            logger.info("生成广东快乐十分 盘期开始>>>>>>>>>>>>>>>>>>>>>>>>");
            gdPeriodsInfoLogic.saveGDPeriods();
            logger.info("生成广东快乐十分  盘期结束>>>>>>>>>>>>>>>>>>>>>>>>");
            
            monitoringInfoLogc.saveMonitoringInfo(new MonitoringInfo(0, "GDKLSF", "0000", "0000000000", "生成盘期", 1, "生成广东快乐十分的盘期", (int)(System.currentTimeMillis()-star)));
            
        } catch (Exception e) {
            logger.info("生成广东快乐十分 盘期");
            logger.error("执行" + this.getClass().getSimpleName()
                    + "中的方法executeInternal时出现错误 "
                    + e.getMessage());
            
            monitoringInfoLogc.saveMonitoringInfo(new MonitoringInfo(0, "GDKLSF", "0000", "0000000000", "生成盘期", 0, "生成广东快乐十分的盘期", (int)(System.currentTimeMillis()-star)));
            
    		JobExecutionException e2=new JobExecutionException(e);
    		throw e2;
        }
    }

}
