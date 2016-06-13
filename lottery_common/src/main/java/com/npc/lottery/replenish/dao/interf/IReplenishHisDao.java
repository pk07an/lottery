package com.npc.lottery.replenish.dao.interf;

import java.util.List;

import com.npc.lottery.common.dao.ILotteryBaseDao;
import com.npc.lottery.replenish.entity.ReplenishHis;

public interface IReplenishHisDao extends ILotteryBaseDao<Long, ReplenishHis>{

	public List<ReplenishHis> queryReplenishHisList(String periodsNum, String schema);
	
	
}
