package com.npc.lottery.statreport.dao.interf;

import com.npc.lottery.common.dao.ILotteryBaseDao;
import com.npc.lottery.statreport.entity.DeliveryReportPetPeriod;

public interface ISettledReportPetPeriodDao extends ILotteryBaseDao<Long, DeliveryReportPetPeriod>{
	 public abstract void flush();

	public void insertSettledReportPetPeriods(DeliveryReportPetPeriod reportInfo,String schema);

	public void deleteSettledReportPet(String periodsNum, String lotteryType,
			String schema);

}
