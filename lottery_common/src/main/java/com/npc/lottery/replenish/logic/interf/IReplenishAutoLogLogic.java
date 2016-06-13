package com.npc.lottery.replenish.logic.interf;

import java.util.List;

import org.hibernate.criterion.Criterion;

import com.npc.lottery.replenish.entity.ReplenishAutoLog;
import com.npc.lottery.sysmge.entity.ShopsPlayOddsLog;
import com.npc.lottery.util.Page;

public interface IReplenishAutoLogLogic {

	public List<ReplenishAutoLog> queryReplenishLogList(Criterion... criterions);
	public void updateReplenishAuto(ReplenishAutoLog entity);
	public void saveReplenishLog(ReplenishAutoLog entity);
	public Page<ReplenishAutoLog> queryLogByPage(Page<ReplenishAutoLog> page,
			Criterion[] criterions);
	/**
	 * 新方法，使用scheme操作
	 * @param entity
	 * @param scheme
	 */
	public void saveReplenishLogByScheme(ReplenishAutoLog entity,String scheme);
	
}
