package com.npc.lottery.replenish.dao.interf;

import com.npc.lottery.common.dao.ILotteryBaseDao;
import com.npc.lottery.replenish.entity.Replenish;

public interface IReplenishHibDao extends ILotteryBaseDao<Long, Replenish>{
	
	public void insertReplenish(Replenish entity,String scheme);
}
