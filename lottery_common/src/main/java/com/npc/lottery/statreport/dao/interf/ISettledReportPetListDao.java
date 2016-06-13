package com.npc.lottery.statreport.dao.interf;

import com.npc.lottery.common.dao.ILotteryBaseDao;
import com.npc.lottery.statreport.entity.DeliveryReportPetList;

public interface ISettledReportPetListDao extends ILotteryBaseDao<Long, DeliveryReportPetList>{
	 public abstract void flush();

	public void insertSettledReportPetPet(DeliveryReportPetList reportInfo,
			String schema);

	public void deleteSettledReportList(String date, String schema);

}
