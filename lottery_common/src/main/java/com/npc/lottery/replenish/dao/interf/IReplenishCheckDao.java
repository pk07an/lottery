package com.npc.lottery.replenish.dao.interf;

import java.util.List;

import com.npc.lottery.common.dao.ILotteryBaseDao;
import com.npc.lottery.replenish.entity.ReplenishCheck;

public interface IReplenishCheckDao extends ILotteryBaseDao<Long, ReplenishCheck>{

	public List<ReplenishCheck> queryReplenishCheckList(String periodsNum,
			String schema);
	
	
}
