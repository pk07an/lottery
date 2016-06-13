package com.npc.lottery.periods.dao.interf;

import com.npc.lottery.common.dao.ILotteryBaseDao;
import com.npc.lottery.periods.entity.HKPeriods;

public interface IHKPeriodsDao extends ILotteryBaseDao<Long, HKPeriods>{
	
	public HKPeriods queryShopRunningPeriods(String shopsCode);
	

}
