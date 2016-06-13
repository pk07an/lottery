package com.npc.lottery.user.dao.interf;

import com.npc.lottery.common.dao.ILotteryBaseDao;
import com.npc.lottery.user.entity.AgentStaffExt;

public interface IAgentStaffExtDao  extends ILotteryBaseDao<Long, AgentStaffExt>{
	public AgentStaffExt findById(long userId,String scheme);
}
