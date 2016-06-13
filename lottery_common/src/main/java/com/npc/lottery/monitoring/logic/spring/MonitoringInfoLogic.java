package com.npc.lottery.monitoring.logic.spring;

import org.springframework.core.task.TaskExecutor;

import com.npc.lottery.monitoring.dao.interf.IMonitoringInfoDao;
import com.npc.lottery.monitoring.entity.MonitoringInfo;
import com.npc.lottery.monitoring.logic.interf.IMonitoringInfoLogic;

public class MonitoringInfoLogic implements IMonitoringInfoLogic {

	private IMonitoringInfoDao monitoringInfoDao;

	private TaskExecutor wcpTaskExecutor;// 异步执行

	public void saveMonitoringInfo(final MonitoringInfo monInfo) {
		try {
			wcpTaskExecutor.execute(new Runnable() {

				@Override
				public void run() {
					monitoringInfoDao.saveMonitoringInfo(monInfo);
				}
			});
		} catch (Exception ex) {
		}
	}

	public IMonitoringInfoDao getMonitoringInfoDao() {
		return monitoringInfoDao;
	}

	public void setMonitoringInfoDao(IMonitoringInfoDao monitoringInfoDao) {
		this.monitoringInfoDao = monitoringInfoDao;
	}

	public TaskExecutor getWcpTaskExecutor() {
		return wcpTaskExecutor;
	}

	public void setWcpTaskExecutor(TaskExecutor wcpTaskExecutor) {
		this.wcpTaskExecutor = wcpTaskExecutor;
	}

}
