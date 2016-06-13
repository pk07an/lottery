package com.npc.lottery.replenish.dao.interf;

import com.npc.lottery.common.dao.ILotteryBaseDao;
import com.npc.lottery.replenish.entity.ReplenishAutoLog;

public interface IReplenishAutoLog extends ILotteryBaseDao<Long, ReplenishAutoLog>{
	
	public void saveReplenishLogByScheme(ReplenishAutoLog entity,String scheme) ;

	
	
}
