package com.npc.lottery.replenish.logic.interf;

import java.util.List;

import org.hibernate.criterion.Criterion;

import com.npc.lottery.replenish.entity.ReplenishAutoSetLog;
import com.npc.lottery.util.Page;

public interface IReplenishAutoSetLogLogic {

	public List<ReplenishAutoSetLog> queryReplenishAutoSetLogList(Criterion... criterions);
	public void updateReplenishAutoSet(ReplenishAutoSetLog entity);
	public void saveReplenishLogSet(ReplenishAutoSetLog entity);
	
	public Page<ReplenishAutoSetLog> queryLogByPage(Page<ReplenishAutoSetLog> page,
			String userId);
	
	/**
	 * 异步执行用户退水日志记录
	 * @param userCommissionLogList
	 */
	public abstract void saveUserCommissionLog(List<ReplenishAutoSetLog> userCommissionLogList,final IReplenishAutoSetLogLogic replenishAutoSetLogLogic);
	
}
