package com.npc.lottery.job.common.nc;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.npc.lottery.monitoring.entity.MonitoringInfo;
import com.npc.lottery.monitoring.logic.interf.IMonitoringInfoLogic;
import com.npc.lottery.periods.logic.interf.INCPeriodsInfoLogic;

public class NCPeriodsJob extends QuartzJobBean
{

    private Logger logger = Logger.getLogger(NCPeriodsJob.class);
    @Autowired
    private INCPeriodsInfoLogic ncPeriodsInfoLogic = null;
    @Autowired
    private IMonitoringInfoLogic monitoringInfoLogc;


    /**
     * 定时设置幸运农场盘期
     */
    @Override
    protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException
    {
    	long star=System.currentTimeMillis();
        
        SimpleDateFormat ageFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        String beginNumber = ageFormat.format(calendar.getTime()) + "01";
        String endNumber = ageFormat.format(calendar.getTime()) + "97";
        try
        {
            logger.info("删除幸运农场 盘期>>>>>>>>>>>>>>>>>>>>>>>>" + beginNumber + "");
            ncPeriodsInfoLogic.deletePeriods(beginNumber, endNumber);
            logger.info("删除>>>>>>>>>>>>>>>>>>>>>>>>");

            logger.info("生成幸运农场 盘期>>>>>>>>>>>>>>>>>>>>>>>>");
            Calendar dateCalender = Calendar.getInstance();
            dateCalender.setTime(new Date());
            dateCalender.add(Calendar.DATE, 1);
            ncPeriodsInfoLogic.saveNCPeriods(dateCalender.getTime());
            logger.info("生成幸运农场 盘期结束>>>>>>>>>>>>>>>>>>>>>>>>");
            
            monitoringInfoLogc.saveMonitoringInfo(new MonitoringInfo(0, "NC", "0000", "0000000000", "生成盘期", 1, "幸运农场生成盘期", (int)(System.currentTimeMillis()-star)));
        }
        catch (Exception e)
        {
            logger.info("幸运农场盘期出错");
            logger.error("执行" + this.getClass().getSimpleName() + "中的方法executeInternal时出现错误 " + e.getMessage());

            monitoringInfoLogc.saveMonitoringInfo(new MonitoringInfo(0, "NC", "0000", "0000000000", "生成盘期", 0, "幸运农场生成盘期", (int)(System.currentTimeMillis()-star)));
			JobExecutionException e2=new JobExecutionException(e);
			throw e2;
        }
    }
}
