package com.npc.lottery.statreport.dao.interf;

import com.npc.lottery.common.dao.ILotteryBaseDao;
import com.npc.lottery.statreport.entity.ClassReportPetPeriod;

public interface IClassReportPetPeriodDao extends ILotteryBaseDao<Long, ClassReportPetPeriod>{
	public abstract void flush();

	public void insertClassReportPetPeriods(ClassReportPetPeriod reportInfo,String schema);

	public void deleteClassReportPetPeriods(String periodsNum, String lotteryType,
			String schema);

}
