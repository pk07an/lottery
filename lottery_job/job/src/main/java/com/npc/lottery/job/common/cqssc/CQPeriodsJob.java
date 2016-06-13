package com.npc.lottery.job.common.cqssc;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.npc.lottery.monitoring.entity.MonitoringInfo;
import com.npc.lottery.monitoring.logic.interf.IMonitoringInfoLogic;
import com.npc.lottery.periods.logic.interf.ICQPeriodsInfoLogic;

public class CQPeriodsJob extends QuartzJobBean{
    
    private Logger logger = Logger.getLogger(CQPeriodsJob.class);
    @Autowired
    private ICQPeriodsInfoLogic icqPeriodsInfoLogic;
    @Autowired
    private IMonitoringInfoLogic monitoringInfoLogc;
    /**
     * 定时设置重庆时时彩盘期
     */
    @Override
    protected void executeInternal(JobExecutionContext arg0)
            throws JobExecutionException {
    	
    	long star=System.currentTimeMillis();
            SimpleDateFormat ageFormat = new SimpleDateFormat("yyyyMMdd");
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE,1);
            String beginNumber = ageFormat.format(calendar.getTime())+"001";
            String endNumber = ageFormat.format(calendar.getTime())+"120";
            try {
                logger.info("删除重庆时时彩 盘期>>>>>>>>>>>>>>>>>>>>>>>>"+beginNumber+"");
                icqPeriodsInfoLogic.deletePeriods(beginNumber,endNumber);
                logger.info("删除>>>>>>>>>>>>>>>>>>>>>>>>");
           
                logger.info("生成重庆时时彩 盘期>>>>>>>>>>>>>>>>>>>>>>>>");
                icqPeriodsInfoLogic.saveCQInterimPeriods();
                icqPeriodsInfoLogic.saveCQPeriods();
                icqPeriodsInfoLogic.saveCQAfterPeriods();
                logger.info("生成重庆时时彩 盘期结束>>>>>>>>>>>>>>>>>>>>>>>>");
                
                monitoringInfoLogc.saveMonitoringInfo(new MonitoringInfo(0, "CQSSC", "0000", "00000000000", "生成盘期", 1, "生成重庆时时彩的盘期", (int)(System.currentTimeMillis()-star)));
            } catch (Exception e) {
                logger.info("重庆时时彩盘期出错");
                logger.error("执行" + this.getClass().getSimpleName()
                        + "中的方法executeInternal时出现错误 "
                        + e.getMessage());
                
                monitoringInfoLogc.saveMonitoringInfo(new MonitoringInfo(0, "CQSSC", "0000", "00000000000", "生成盘期", 0, "生成重庆时时彩的盘期", (int)(System.currentTimeMillis()-star)));
   
        		JobExecutionException e2=new JobExecutionException(e);
        		throw e2;
            }
    }
}
