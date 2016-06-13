package com.npc.lottery.statreport.dao.interf;

import com.npc.lottery.common.dao.ILotteryBaseDao;
import com.npc.lottery.statreport.entity.ClassReportRList;

public interface IClassReportRListDao extends ILotteryBaseDao<Long, ClassReportRList>{
	public abstract void flush();

	public void insertClassReportRList(ClassReportRList reportInfo, String schema);

	public void deleteClassReportRList(String date, String schema);

}
