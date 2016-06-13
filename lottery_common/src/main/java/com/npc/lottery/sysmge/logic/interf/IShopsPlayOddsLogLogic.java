package com.npc.lottery.sysmge.logic.interf;

import org.hibernate.criterion.Criterion;

import com.npc.lottery.sysmge.entity.ShopsPlayOddsLog;
import com.npc.lottery.util.Page;

public interface IShopsPlayOddsLogLogic {

	public void saveLog(ShopsPlayOddsLog log);
	public Page<ShopsPlayOddsLog> queryLogByPage(Page<ShopsPlayOddsLog> page,
			Criterion[] criterions);
	public void saveLogByScheme(ShopsPlayOddsLog log,String scheme);
}
