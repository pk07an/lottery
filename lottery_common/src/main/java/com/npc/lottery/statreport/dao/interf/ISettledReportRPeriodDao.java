package com.npc.lottery.statreport.dao.interf;

import com.npc.lottery.common.dao.ILotteryBaseDao;
import com.npc.lottery.statreport.entity.DeliveryReportRPeriod;

public interface ISettledReportRPeriodDao extends ILotteryBaseDao<Long, DeliveryReportRPeriod>{
	public abstract void flush();

	public void insertSettledReportRPeriods(DeliveryReportRPeriod reportInfo,String schema);

	public void deleteSettledReportR(String periodsNum, String lotteryType,
			String schema);

}
