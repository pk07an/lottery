package com.npc.lottery.job.report;

import java.sql.Date;
import java.text.ParseException;
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
import com.npc.lottery.statreport.logic.interf.IClassReportEricLogic;
import com.npc.lottery.statreport.logic.interf.IReportStatusLogic;
import com.npc.lottery.statreport.logic.interf.ISettledReportEricLogic;

/**
 * 报表统计Action
 * 
 */
public class SettledReportJob extends QuartzJobBean {
	static Logger logger = Logger.getLogger(SettledReportJob.class);
	@Autowired
	private ISettledReportEricLogic settledReportEricLogic = null;// 报表统计
	@Autowired
	private IClassReportEricLogic classReportEricLogic = null;// 报表统计
	@Autowired
	private IReportStatusLogic reportStatusLogic;

	@Autowired
	private ShopSchemeService shopSchemeService;
	@Autowired
	private IMonitoringInfoLogic monitoringInfoLogic;

	/**
	 * 自动生成报表列表,只生成昨天的报表
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		long star=System.currentTimeMillis();
		try{
			java.util.Date dd = new java.util.Date();
			Date today = new Date(dd.getYear(), dd.getMonth(), dd.getDate() - 1);
	
			String rStart = today.toString();
			String rEnd = today.toString();
	
			Map<String, String> shopSchemeMap = shopSchemeService.getShopSchemeMap();
			// 每一个商铺去结算报表
			for (Entry<String, String> shopScheme : shopSchemeMap.entrySet()) {
				
				// 获取商铺对应的scheme列表
				String scheme = shopScheme.getValue();
				try {
					// 计算前先把状态改为N,是为了不影响客户查询
					//reportStatusLogic.updateReportStatus("N", scheme);
		
					long startTime = System.currentTimeMillis();
						// 先把要该日期的报表统计数据删除
						settledReportEricLogic.deleteReportPetList(rStart, rEnd, scheme);
						settledReportEricLogic.deleteReportRList(rStart, rEnd, scheme);
						classReportEricLogic.deleteReportPetList(rStart, rEnd, scheme);
						classReportEricLogic.deleteReportRList(rStart, rEnd, scheme);
		
						logger.info("自动生成商铺："+scheme+"的统计报表数据 " + rStart + " 开始>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
						settledReportEricLogic.saveReportList(rStart, rEnd, scheme);
					
					long end = System.currentTimeMillis();
					logger.info("自动生成商铺："+scheme+"的统计报表数据结束,所用时间：" + (end - startTime) / 1000 + " 秒 -->");
		
					//reportStatusLogic.updateReportStatus("Y",scheme);
					monitoringInfoLogic.saveMonitoringInfo( new MonitoringInfo(0, "报表", shopScheme.getKey(), null, "统计报表", 1, "自动生成商铺："+shopScheme.getKey()+"的统计报表数据", (int)(System.currentTimeMillis()-star)));
				} catch (ParseException e) {
					//reportStatusLogic.updateReportStatus("N",scheme);
					monitoringInfoLogic.saveMonitoringInfo(new MonitoringInfo(0, "报表", shopScheme.getKey(), null, "统计报表", 0, "自动生成商铺："+shopScheme.getKey()+"的统计报表数据", (int)(System.currentTimeMillis()-star)));
					logger.info("自动生成商铺:"+scheme+"的统计报表数据 异常，提示错误：" + e.getMessage());
				}
			}
		}catch (Exception e) {
			JobExecutionException e2=new JobExecutionException(e);
			monitoringInfoLogic.saveMonitoringInfo(new MonitoringInfo(0, "报表", null, null, "统计报表", 0, "自动生成统计报表数据", (int)(System.currentTimeMillis()-star)/1000));
			throw e2;
		}
	}
}
