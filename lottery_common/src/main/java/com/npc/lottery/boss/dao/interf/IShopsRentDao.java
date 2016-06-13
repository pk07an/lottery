package com.npc.lottery.boss.dao.interf;

import com.npc.lottery.boss.entity.ShopsRent;
import com.npc.lottery.common.dao.ILotteryBaseDao;

public interface IShopsRentDao  extends ILotteryBaseDao<Long, ShopsRent>{

	public void update(ShopsRent entity);
	
}
