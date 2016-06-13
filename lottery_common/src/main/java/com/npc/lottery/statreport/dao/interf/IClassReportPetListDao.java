package com.npc.lottery.statreport.dao.interf;

import com.npc.lottery.common.dao.ILotteryBaseDao;
import com.npc.lottery.statreport.entity.ClassReportPetList;

public interface IClassReportPetListDao extends ILotteryBaseDao<Long, ClassReportPetList>{
	public abstract void flush();

	public void insertClassReportPetPet(ClassReportPetList reportInfo, String schema);

	public void deleteClassReportList(String date, String schema);

}
