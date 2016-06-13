package com.npc.lottery.member.dao.interf;


import java.util.List;

import com.npc.lottery.common.dao.ILotteryBaseDao;
import com.npc.lottery.member.entity.CancelPetLog;


public interface ICancelPetLogDao extends ILotteryBaseDao<Long, CancelPetLog>
{

	public List<CancelPetLog> queryCancelPetLogList(String typeCode, String orderNo,
			String periodsNum, String billType);

}
