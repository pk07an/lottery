package com.npc.lottery.statreport.dao.interf;

import com.npc.lottery.common.dao.ILotteryBaseDao;
import com.npc.lottery.statreport.entity.ClassReportRPeriod;

public interface IClassReportRPeriodDao extends ILotteryBaseDao<Long, ClassReportRPeriod>{
	public abstract void flush();

	public void insertClassReportRPeriods(ClassReportRPeriod reportInfo,String schema);

	public void deleteClassReportRPeriods(String periodsNum, String lotteryType,
			String schema);

}
