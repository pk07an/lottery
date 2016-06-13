package com.npc.lottery.statreport.dao.interf;

import com.npc.lottery.common.dao.ILotteryBaseDao;
import com.npc.lottery.statreport.entity.DeliveryReportRList;

public interface ISettledReportRListDao extends ILotteryBaseDao<Long, DeliveryReportRList>{
	public abstract void flush();

	public void insertSettledReportRList(DeliveryReportRList reportInfo, String schema);

	public void deleteSettledReportRList(String date, String schema);

}
