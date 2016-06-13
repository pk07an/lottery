package com.npc.lottery.sysmge.dao.interf;

import com.npc.lottery.common.dao.ILotteryBaseDao;
import com.npc.lottery.sysmge.entity.ShopsPlayOddsLog;

 
public interface IShopsPlayOddsLogDao extends ILotteryBaseDao<Long, ShopsPlayOddsLog>{

	public void saveLogByScheme(ShopsPlayOddsLog log, String scheme) ;
}
