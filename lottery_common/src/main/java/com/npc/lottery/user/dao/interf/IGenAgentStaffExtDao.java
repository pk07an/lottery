package com.npc.lottery.user.dao.interf;

import com.npc.lottery.common.dao.ILotteryBaseDao;
import com.npc.lottery.user.entity.GenAgentStaffExt;

public interface IGenAgentStaffExtDao extends ILotteryBaseDao<Long, GenAgentStaffExt>{

	public GenAgentStaffExt findById(long id,String scheme);
}
