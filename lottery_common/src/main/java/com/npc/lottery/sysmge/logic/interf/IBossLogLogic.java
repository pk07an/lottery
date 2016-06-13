package com.npc.lottery.sysmge.logic.interf;

import org.hibernate.criterion.Criterion;

import com.npc.lottery.sysmge.entity.BossLog;
import com.npc.lottery.util.Page;

public interface IBossLogLogic {

	public void saveLog(BossLog log);
	public Page<BossLog> queryLogByPage(Page<BossLog> page,
			Criterion... criterions);
}
